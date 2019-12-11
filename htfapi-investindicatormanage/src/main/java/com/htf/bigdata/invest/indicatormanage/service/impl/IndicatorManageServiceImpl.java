package com.htf.bigdata.invest.indicatormanage.service.impl;

import com.htf.bigdata.invest.indicatormanage.component.exception.CustomException;
import com.htf.bigdata.invest.indicatormanage.component.exception.ValidatorException;
import com.htf.bigdata.invest.indicatormanage.component.util.StringUtil;
import com.htf.bigdata.invest.indicatormanage.component.util.TimeUtil;
import com.htf.bigdata.invest.indicatormanage.config.code.IndicatorManageCodeConfig;
import com.htf.bigdata.invest.indicatormanage.config.code.SystemEnumCodeConfig;
import com.htf.bigdata.invest.indicatormanage.config.constant.Constant;
import com.htf.bigdata.invest.indicatormanage.config.enums.*;
import com.htf.bigdata.invest.indicatormanage.dao.invest.*;
import com.htf.bigdata.invest.indicatormanage.model.domain.*;
import com.htf.bigdata.invest.indicatormanage.model.response.dto.*;
import com.htf.bigdata.invest.indicatormanage.model.vo.*;
import com.htf.bigdata.invest.indicatormanage.service.IIndicatorManageService;
import com.htf.bigdata.invest.indicatormanage.service.IUserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: 指标管理service实现
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
@Service
public class IndicatorManageServiceImpl implements IIndicatorManageService {

    @Autowired
    private InvestnewIndexStockMapper stockMapper;

    @Autowired
    private InvestnewIndexIndustryMapper industryMapper;

    @Autowired
    private InvestnewIndexIndicatorMapper indicatorMapper;

    @Autowired
    private InvestnewIndexIndicatorAnalystMapper indicatorAnalystMapper;

    @Autowired
    private InvestnewIndexIndicatorIndustryMapper indicatorIndustryMapper;

    @Autowired
    private InvestnewIndexIndicatorStockMapper indexIndicatorStockMapper;

    @Autowired
    private InvestnewIndexGroupRelationMapper indexGroupRelationMapper;

    @Autowired
    private InvestnewIndexIndicatorDataMapper indexIndicatorDataMapper;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private InvestnewIndexIndicatorCommentMapper indicatorCommentMapper;

    @Override
    public QueryIndicatorCountResponse queryIndicatorCount(String userId) throws Exception {
        QueryIndicatorCountResponse result = new QueryIndicatorCountResponse();
        String queryFlag = IndicatorQueryFlagEnum.UNCONFIRMED.getQueryFlag();
        int indicatorType = IndicatorTypeEnum.SPECIAL_INDICATOR_GROUP.getIndicatorType();
        // 查询待确认的指标数量
        int indicatorDataAuto = IndicatorDataAutoEnum.AUTO.getIndicatorDataAuto();
        List<Long> unconfirmedIndicatorIdList = indicatorMapper.selectIdListByAutoAndQueryFlag(userId, indicatorDataAuto, queryFlag, indicatorType);
        result.setUnconfirmedIndicatorCount(unconfirmedIndicatorIdList == null ? 0 : unconfirmedIndicatorIdList.size());

        // 查询待更新的指标数量
        indicatorDataAuto = IndicatorDataAutoEnum.NOT_AUTO.getIndicatorDataAuto();
        List<Long> notUpdatedIndicatorIdList = indicatorMapper.selectIdListByAutoAndQueryFlag(userId, indicatorDataAuto, queryFlag, indicatorType);
        result.setNotUpdatedIndicatorCount(notUpdatedIndicatorIdList == null ? 0 : notUpdatedIndicatorIdList.size());

        return result;
    }

    @Override
    public List<IndicatorSummaryDto> queryMyManageIndicator(String userId, String keyword, QueryIndicatorTypeEnum type) throws Exception {
        Integer isAuto;
        int indicatorType;
        String queryFlag;
        switch (type) {
            // 我管理的所有
            case INDICATOR_ALL:
                indicatorType = IndicatorTypeEnum.SPECIAL_INDICATOR_GROUP.getIndicatorType();
                return indicatorMapper.selectMyManageIndicator(userId, keyword, indicatorType, null, null);
            // 我管理的待确认
            case INDICATOR_UNCONFIRMED:
                indicatorType = IndicatorTypeEnum.SPECIAL_INDICATOR_GROUP.getIndicatorType();
                queryFlag = IndicatorQueryFlagEnum.UNCONFIRMED.getQueryFlag();
                isAuto = IndicatorDataAutoEnum.AUTO.getIndicatorDataAuto();
                return indicatorMapper.selectMyManageIndicator(userId, keyword, indicatorType, isAuto, queryFlag);
            // 我管理的待更新
            case INDICATOR_NOT_UPDATED:
                indicatorType = IndicatorTypeEnum.SPECIAL_INDICATOR_GROUP.getIndicatorType();
                queryFlag = IndicatorQueryFlagEnum.UNCONFIRMED.getQueryFlag();
                isAuto = IndicatorDataAutoEnum.NOT_AUTO.getIndicatorDataAuto();
                return indicatorMapper.selectMyManageIndicator(userId, keyword, indicatorType, isAuto, queryFlag);
            // 组合模板
            case INDICATOR_GROUP:
                indicatorType = IndicatorTypeEnum.INDICATOR_GROUP.getIndicatorType();
                List<IndicatorSummaryDto> indicatorSummaryDtoList = indicatorMapper.selectMyManageIndicator(userId, keyword, indicatorType, null, null);
                if (indicatorSummaryDtoList != null && !indicatorSummaryDtoList.isEmpty()) {
                    for (IndicatorSummaryDto indicatorSummaryDto : indicatorSummaryDtoList) {
                        List<Long> indicatorIdList = indexGroupRelationMapper.selectIndicatorIdByGroupId(indicatorSummaryDto.getIndicatorId(), DeleteStatusEnum.UNDELETE.getDeleteStatus());
                        List<IndicatorSummaryDto> indicatorList = indicatorMapper.selectIndicatorSummaryByIdList(indicatorIdList);
                        indicatorSummaryDto.setIndicatorList(indicatorList);
                    }
                }
                return indicatorSummaryDtoList;
            default:
                throw new ValidatorException(SystemEnumCodeConfig.ERROR_VALIDATOR_PARAMETER);
        }
    }

    @Override
    public List<QueryUpdatedIndicatorDataResponse> queryUpdatedIndicatorData(String userId, Integer page, Integer limit) throws Exception {
        String queryFlag = IndicatorQueryFlagEnum.UNCONFIRMED.getQueryFlag();
        int indicatorDataAuto = IndicatorDataAutoEnum.NOT_AUTO.getIndicatorDataAuto();
        int indicatorType = IndicatorTypeEnum.SPECIAL_INDICATOR_GROUP.getIndicatorType();
        List<Long> indicatorIdList = indicatorMapper.selectIdListByAutoAndQueryFlag(userId, indicatorDataAuto, queryFlag, indicatorType);
        if (indicatorIdList == null || indicatorIdList.isEmpty()) {
            return new ArrayList<>();
        }
        Integer offset = (page - 1) * limit;
        return indexIndicatorDataMapper.selectUpdatedIndicatorData(indicatorIdList, offset, limit);
    }

    @Override
    @Transactional(transactionManager = "investTransactionManager", rollbackFor = Exception.class)
    public Long saveIndicator(AddIndicatorRequest request) throws Exception {
        String userId = request.getUserId();
        String indicatorName = request.getIndicatorName();
        String indicatorUnit = request.getIndicatorUnit();
        String indicatorFrequency = request.getIndicatorFrequency();
        String dataSource = request.getDataSource();
        List<IndustryItem> industries = request.getIndustries();
        List<StockItem> stocks = request.getStocks();
        int isAuto = IndicatorDataAutoEnum.NOT_AUTO.getIndicatorDataAuto();

        // 插入基础指标
        Long indicatorId = insertIndicator(userId, indicatorName, indicatorUnit, indicatorFrequency, dataSource,
                isAuto, IndicatorTypeEnum.BASIC_INDICATOR.getIndicatorType(), industries, stocks);

        // 历史数据入库
        List<InvestnewIndexIndicatorData> indexIndicatorDataList = new ArrayList<>();
        List<List<String>> dataList = request.getDataList();
        for (List<String> datas : dataList) {
            if (datas != null && !datas.isEmpty() && datas.size() == 2) {
                String dateStr = datas.get(0);
                String valueStr = datas.get(1);
                Date date = TimeUtil.parseDateStr(dateStr, Constant.DEFAULT_DATE_FORMAT);
                if (date == null) {
                    throw new CustomException(SystemEnumCodeConfig.ERROR_VALIDATOR_PARAMETER);
                }
                BigDecimal value = new BigDecimal(valueStr);
                InvestnewIndexIndicatorData indexIndicatorData = new InvestnewIndexIndicatorData();
                indexIndicatorData.setIndicatorId(indicatorId);
                indexIndicatorData.setIndicatorTime(date);
                indexIndicatorData.setIndicatorData(value);
                indexIndicatorData.setIsAuto(isAuto);
                indexIndicatorData.setStatus(IndicatorDataStatusEnum.CONFIRMED.getStatus());
                indexIndicatorData.setCreator(userId);
                indexIndicatorData.setEditor(userId);

                indexIndicatorDataList.add(indexIndicatorData);
            }
        }

        if (indexIndicatorDataList.isEmpty()) {
            throw new CustomException(SystemEnumCodeConfig.ERROR_VALIDATOR_PARAMETER);
        }
        indexIndicatorDataMapper.insertBatch(indexIndicatorDataList);

        // 创建特殊指标组
        Long specialGroupId = insertIndicator(userId, indicatorName, indicatorUnit, indicatorFrequency, dataSource,
                isAuto, IndicatorTypeEnum.SPECIAL_INDICATOR_GROUP.getIndicatorType(), industries, stocks);

        // 创建特殊指标组和基础指标关联关系
        Set<Long> indicatorIdList = new HashSet<>();
        indicatorIdList.add(indicatorId);
        insertGroupRelation(indicatorIdList, specialGroupId, userId);

        return specialGroupId;
    }

    /**
     * 插入指标信息及关联股票、行业、研究员
     * @param userId
     * @param indicatorName
     * @param indicatorUnit
     * @param indicatorFrequency
     * @param dataSource
     * @param isAuto
     * @param indicatorType
     * @param industries
     * @param stocks
     * @return
     * @throws Exception
     */
    private Long insertIndicator(String userId, String indicatorName, String indicatorUnit, String indicatorFrequency, String dataSource,
                                 Integer isAuto, Integer indicatorType, List<IndustryItem> industries, List<StockItem> stocks) throws Exception {
        InvestnewIndexIndicator investnewIndexIndicator = new InvestnewIndexIndicator();
        investnewIndexIndicator.setIndicatorName(indicatorName);
        investnewIndexIndicator.setIndicatorUnit(indicatorUnit);
        investnewIndexIndicator.setIndicatorFrequency(indicatorFrequency);
        investnewIndexIndicator.setDataSource(dataSource);
        investnewIndexIndicator.setIsAuto(isAuto);
        investnewIndexIndicator.setIndicatorType(indicatorType);
        investnewIndexIndicator.setUpdateStatus(IndicatorUpdateStatusEnum.OPEN.getUpdateStatus());
        investnewIndexIndicator.setNoticeStatus(IndicatorNoticeStatusEnum.OPEN.getNoticeStatus());
        investnewIndexIndicator.setCreator(userId);
        investnewIndexIndicator.setEditor(userId);
        investnewIndexIndicator.setQueryFlag(IndicatorQueryFlagEnum.CONFIRMED.getQueryFlag());
        investnewIndexIndicator.setPushTime(new Date());

        try {
            indicatorMapper.insertSelective(investnewIndexIndicator);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                throw new CustomException(IndicatorManageCodeConfig.ERROR_INDICATOR_NAME_EXIST);
            } else {
                throw new RuntimeException(e);
            }
        }

        Long indicatorId = investnewIndexIndicator.getId();

        // 填充指标关联研究员关系表
        insertIndicatorAnalyst(indicatorId, userId);

        // 填充指标行业关系表
        insertIndicatorIndustry(industries, indicatorId, userId);

        // 填充指标股票关系表
        insertIndicatorStock(stocks, indicatorId, userId);

        return indicatorId;
    }

    @Override
    public List<StockDto> queryStock(String keyword, Integer limit) throws Exception {
        return stockMapper.selectByKeywordAndLimit(keyword, limit);
    }

    @Override
    public List<IndustryDto> queryIndustry() throws Exception {
        List<IndustryDto> result = new ArrayList<>();
        // 查询一级行业
        List<InvestnewIndexIndustry> firstIndustryList = industryMapper.selectIndustryByParentId(0L);
        firstIndustryList.forEach(firstIndustry -> {
            IndustryDto industryDto = new IndustryDto();
            List<IndustryItem> secondIndustryNameList = new ArrayList<>();
            // 查询各一级行业下的二级行业
            List<InvestnewIndexIndustry> secondIndustryList = industryMapper.selectIndustryByParentId(firstIndustry.getId());
            if (secondIndustryList != null && !secondIndustryList.isEmpty()) {
                secondIndustryList.forEach(secondIndustry -> {
                    IndustryItem industryItem = new IndustryItem();
                    industryItem.setId(secondIndustry.getId());
                    industryItem.setIndustryCode(secondIndustry.getIndustryCode());
                    industryItem.setIndustryName(secondIndustry.getIndustryName());
                    secondIndustryNameList.add(industryItem);
                });
                industryDto.setFirstIndustryName(firstIndustry.getIndustryName());
                industryDto.setSecondIndustryList(secondIndustryNameList);
            }
            result.add(industryDto);
        });

        return result;
    }

    @Override
    public ParseIndicatorDataResponse parseIndicatorData(String importContent) throws Exception {
        // 换行和空格解析出所有数据
        List<String> list = new ArrayList<>();
        String[] rows = importContent.split("\\n");
        for (String row : rows) {
            String normalized = row.trim();
            if (!StringUtil.isEmpty(normalized)) {
                List<String> strings = Arrays.asList(normalized.split("\\s+"));
                list.addAll(strings);
            }
        }

        // 数据两两一组
        List<List<String>> groupList = new ArrayList<>();
        int toIndex = 2;
        for(int i = 0; i < list.size(); i += 2) {
            if (i + 2 > list.size()) {
                toIndex = list.size() - i;
            }
            groupList.add(list.subList(i, i + toIndex));
        }

        // 解析数据
        List<List<String>> successList = new ArrayList<>();
        List<List<String>> failList = new ArrayList<>();
        for (List<String> dataList : groupList) {
            if (dataList.size() >= 2) {
                String data1 = dataList.get(0);
                String data2 = dataList.get(1);
                if (StringUtil.isEmpty(data1) || StringUtil.isEmpty(data2)) {
                    failList.add(dataList);
                } else {
                    // 校验是否是数字
                    String regexp = "^(-?\\d+)(\\.\\d+)?";
                    Pattern compile = Pattern.compile(regexp);
                    Matcher matcher1 = compile.matcher(data1);
                    Matcher matcher2 = compile.matcher(data2);

                    Boolean checkData1 = TimeUtil.isDate(data1) && matcher2.find();
                    Boolean checkData2 = TimeUtil.isDate(data2) && matcher1.find();
                    if (checkData1 || checkData2) {
                        String dateStr = checkData1 ? data1 : data2;
                        Matcher matcher = checkData1 ? matcher2 : matcher1;
                        Date date = TimeUtil.string2date(dateStr, TimeUtil.determineDateFormat(dateStr));
                        dataList.set(0, TimeUtil.date2String(date, Constant.DEFAULT_DATE_FORMAT));
                        dataList.set(1, matcher.group(0));
                        successList.add(dataList);
                    } else {
                        failList.add(dataList);
                    }
                }
            } else {
                failList.add(dataList);
            }
        }

        ParseIndicatorDataResponse result = new ParseIndicatorDataResponse();
        result.setSuccess(successList);
        result.setFail(failList);
        return result;
    }

    @Override
    public IndicatorBaseInfoDto queryIndicatorBasicInfo(String userId, Long indicatorId) throws Exception {
        InvestnewIndexIndicator indexIndicator = indicatorMapper.selectByPrimaryKey(indicatorId);
        if (indexIndicator == null) {
            throw new CustomException(IndicatorManageCodeConfig.ERROR_INDICATOR_NOT_EXIST);
        }
        IndicatorBaseInfoDto result = new IndicatorBaseInfoDto();
        BeanUtils.copyProperties(indexIndicator,result);

        // 查询关联行业
        List<InvestnewIndexIndicatorIndustry> indicatorIndustryList = indicatorIndustryMapper.selectByIndicatorId(indicatorId);
        List<IndustryItem> industries = new ArrayList<>();
        if (indicatorIndustryList != null && !indicatorIndustryList.isEmpty()) {
            indicatorIndustryList.forEach(item -> {
                IndustryItem industryItem = new IndustryItem();
                industryItem.setId(item.getId());
                industryItem.setIndustryName(item.getSecondIndustryName());

                industries.add(industryItem);
            });
        }
        result.setIndustries(industries);

        // 查询关联股票
        List<InvestnewIndexIndicatorStock> indicatorStockList = indexIndicatorStockMapper.selectByIndicatorId(indicatorId);
        List<StockItem> stocks = new ArrayList<>();
        if (indicatorStockList != null && !indicatorStockList.isEmpty()) {
            indicatorStockList.forEach(item -> {
                StockItem stockItem = new StockItem();
                stockItem.setId(item.getId());
                stockItem.setStockCode(item.getStockCode());
                stockItem.setStockName(item.getStockName());

                stocks.add(stockItem);
            });
        }
        result.setStocks(stocks);

        // 查询该用户是否是指标管理员
        result.setManager(checkIndicatorManager(indicatorId, userId));

        // 如果是特殊指标组 查询最近数据更新时间
        if (indexIndicator.getIndicatorType().equals(IndicatorTypeEnum.SPECIAL_INDICATOR_GROUP.getIndicatorType())) {
            List<Long> indicatorIdList = indexGroupRelationMapper.selectIndicatorIdByGroupId(indicatorId, DeleteStatusEnum.UNDELETE.getDeleteStatus());
            Long basicIndicatorId = indicatorMapper.selectIndicatorIdByIdListAndType(indicatorIdList, IndicatorTypeEnum.BASIC_INDICATOR.getIndicatorType());
            Date date = indexIndicatorDataMapper.selectRecentTimeByIndicatorIdAndType(basicIndicatorId);
            result.setDataRecentTime(date);
        }

        return result;
    }

    @Override
    @Transactional(transactionManager = "investTransactionManager", rollbackFor = Exception.class)
    public void updateIndicatorBasicInfo(UpdateIndicatorRequest request) throws Exception {
        Long indicatorId = request.getIndicatorId();
        String userId = request.getUserId();
        // 判断指标是否存在
        InvestnewIndexIndicator indexIndicator = indicatorMapper.selectByPrimaryKey(request.getIndicatorId());
        if (indexIndicator == null) {
            throw new CustomException(IndicatorManageCodeConfig.ERROR_INDICATOR_NOT_EXIST);
        }
        // 判断是否有修改该指标的权限
        Boolean isManager = checkIndicatorManager(indicatorId, userId);
        if (!isManager) {
            throw new CustomException(SystemEnumCodeConfig.ERROR_AUTHORIZED);
        }

        boolean isSpecialGroup = indexIndicator.getIndicatorType().equals(IndicatorTypeEnum.SPECIAL_INDICATOR_GROUP.getIndicatorType());

        if (!StringUtil.isEmpty(request.getIndicatorName())
                || !StringUtil.isEmpty(request.getNoticeStatus())
                || !StringUtil.isEmpty(request.getUpdateStatus())) {
            InvestnewIndexIndicator updateIndicator = new InvestnewIndexIndicator();
            updateIndicator.setId(indicatorId);
            updateIndicator.setIndicatorName(request.getIndicatorName());
            // 如果是特殊指标组可以更新指标的通知状态和更新状态
            if (isSpecialGroup) {
                String updateStatus = request.getUpdateStatus();
                if (!StringUtil.isEmpty(updateStatus)) {
                    // 校验参数
                    if (IndicatorUpdateStatusEnum.check(updateStatus)) {
                        updateIndicator.setUpdateStatus(updateStatus);
                    } else {
                        throw new ValidatorException(SystemEnumCodeConfig.ERROR_VALIDATOR_PARAMETER);
                    }
                }
                String noticeStatus = request.getNoticeStatus();
                if (!StringUtil.isEmpty(noticeStatus)) {
                    // 校验参数
                    if (IndicatorNoticeStatusEnum.check(noticeStatus)) {
                        updateIndicator.setNoticeStatus(noticeStatus);
                    } else {
                        throw new ValidatorException(SystemEnumCodeConfig.ERROR_VALIDATOR_PARAMETER);
                    }
                }
            }

            updateIndicator(updateIndicator);

            if (isSpecialGroup) {
                // 如果是特殊指标组，同步更新基础指标
                // 查询特殊指标组对应的基础指标
                List<Long> indicatorIdList = indexGroupRelationMapper.selectIndicatorIdByGroupId(indicatorId, DeleteStatusEnum.UNDELETE.getDeleteStatus());
                Long basicIndicatorId = indicatorMapper.selectIndicatorIdByIdListAndType(indicatorIdList, IndicatorTypeEnum.BASIC_INDICATOR.getIndicatorType());

                // 同步更新基础指标信息
                updateIndicator.setId(basicIndicatorId);
                updateIndicator(updateIndicator);

                // 如果是更新指标的更新状态和通知状态，同步更新衍生指标的状态
                indicatorIdList.remove(basicIndicatorId);
                for (Long deriveIndicatorId : indicatorIdList) {
                    InvestnewIndexIndicator deriveIndicator = new InvestnewIndexIndicator();
                    deriveIndicator.setId(deriveIndicatorId);
                    deriveIndicator.setUpdateStatus(updateIndicator.getUpdateStatus());
                    deriveIndicator.setNoticeStatus(updateIndicator.getNoticeStatus());

                    updateIndicator(deriveIndicator);
                }

                // 如果更新状态为恢复更新，需要将数据表investnew_index_indicator_data中对应的数据从停止更新状态变为待确认(由2-0)
                if (IndicatorUpdateStatusEnum.OPEN.getUpdateStatus().equals(updateIndicator.getUpdateStatus())) {
                    indexIndicatorDataMapper.updateStatusByIndicatorIdListAndStatus(indicatorIdList, IndicatorDataStatusEnum.UNCONFIRMED.getStatus(), IndicatorDataStatusEnum.STOP.getStatus(), userId);
                }
            }
        }

        List<StockItem> stocks = request.getStocks();
        if (stocks != null && !stocks.isEmpty()) {
            indexIndicatorStockMapper.deleteByIndicatorId(indicatorId);
            insertIndicatorStock(stocks, indicatorId, userId);

            if (isSpecialGroup) {
                // 如果是特殊指标组，同步更新基础指标
                // 查询特殊指标组对应的基础指标
                List<Long> indicatorIdList = indexGroupRelationMapper.selectIndicatorIdByGroupId(indicatorId, DeleteStatusEnum.UNDELETE.getDeleteStatus());
                Long basicIndicatorId = indicatorMapper.selectIndicatorIdByIdListAndType(indicatorIdList, IndicatorTypeEnum.BASIC_INDICATOR.getIndicatorType());

                // 同步股票信息
                indexIndicatorStockMapper.deleteByIndicatorId(basicIndicatorId);
                insertIndicatorStock(stocks, basicIndicatorId, userId);
            }
        }
    }

    @Override
    public List<IndicatorChartDetailDto> queryIndicatorGroupChart(String startTime, String endTime, Long indicatorId) throws Exception {
        // indicatorId是否存在
        InvestnewIndexIndicator indexIndicator = indicatorMapper.selectByPrimaryKey(indicatorId);
        if (indexIndicator == null) {
            throw new CustomException(IndicatorManageCodeConfig.ERROR_INDICATOR_NOT_EXIST);
        }

        Integer indicatorType = indexIndicator.getIndicatorType();
        List<Long> indicatorIdList;
        if (indicatorType.equals(IndicatorTypeEnum.SPECIAL_INDICATOR_GROUP.getIndicatorType())
                || indicatorType.equals(IndicatorTypeEnum.INDICATOR_GROUP.getIndicatorType())) {
            // 如果是特殊指标组/指标组,查询关联的指标信息
            indicatorIdList = indexGroupRelationMapper.selectIndicatorIdByGroupId(indicatorId, DeleteStatusEnum.UNDELETE.getDeleteStatus());
        } else {
            throw new ValidatorException(SystemEnumCodeConfig.ERROR_VALIDATOR_PARAMETER);
        }

        if (indicatorIdList == null || indicatorIdList.isEmpty()) {
            return new ArrayList<>();
        }

        return queryIndicatorOrGroupChart(indicatorIdList, startTime, endTime, indicatorId, null);
    }


    @Override
    public List<IndicatorChartDetailDto> queryIndicatorChart(String indicatorIds) throws Exception {
        String[] indicatorIdStrArray = indicatorIds.split(",");

        List<Long> indicatorIdList = new ArrayList<>();
        for (String indicatorIdStr : indicatorIdStrArray) {
            indicatorIdList.add(Long.parseLong(indicatorIdStr));
        }
        return queryIndicatorOrGroupChart(indicatorIdList, null, null, null, IndicatorDataStatusEnum.CONFIRMED.getStatus());
    }

    @Override
    @Transactional(transactionManager = "investTransactionManager", rollbackFor = Exception.class)
    public Long addIndicatorGroup(AddIndicatorGroupRequest request) throws Exception {
        // 校验关联的基础指标
        Set<Long> indicatorIdList = request.getIndicatorIdList();
        Boolean result = checkIndicatorId(indicatorIdList, IndicatorTypeEnum.BASIC_INDICATOR.getIndicatorType());
        if (!result) {
            throw new ValidatorException(SystemEnumCodeConfig.ERROR_VALIDATOR_PARAMETER);
        }

        String userId = request.getUserId();

        // 插入指标组
        Long groupId = insertIndicator(userId, request.getIndicatorName(), null, null, null,
                null, IndicatorTypeEnum.INDICATOR_GROUP.getIndicatorType(), request.getIndustries(), request.getStocks());

        // 创建指标组和基础指标关联关系
        insertGroupRelation(indicatorIdList, groupId, userId);
        return groupId;
    }

    @Override
    public List<QueryAllBasicIndicatorDto> queryAllBasicIndicator(String userId, Long indicatorId) throws Exception {
        List<Long> indicatorIdList = queryIndicatorIdByIndicatorGroupId(indicatorId);

        int indicatorType = IndicatorTypeEnum.BASIC_INDICATOR.getIndicatorType();
        // 查询我管理的基础指标id
        List<QueryAllBasicIndicatorInfoDto> myManageIndicatorList = indicatorMapper.selectByAnalystIdAndType(userId, indicatorType);
        checkBasicIndicatorSelected(myManageIndicatorList, indicatorIdList);

        Map<String, List<QueryAllBasicIndicatorInfoDto>> map = new LinkedHashMap<>();
        map.put("我的管理", myManageIndicatorList);

        // 查询各一级行业下的基础指标
        List<QueryAllBasicIndicatorInfoDto> industryIndicatorList = indicatorMapper.selectAllIndustryIndicatorByType(indicatorType);
        if (industryIndicatorList != null && !industryIndicatorList.isEmpty()) {
            // 判断是否是选中,根据行业分类
            for (QueryAllBasicIndicatorInfoDto indicatorInfoDto : industryIndicatorList) {
                indicatorInfoDto.setSelected(indicatorIdList != null && indicatorIdList.contains(indicatorInfoDto.getId()));

                // 根据行业分类
                String firstIndustryName = indicatorInfoDto.getFirstIndustryName();
                List<QueryAllBasicIndicatorInfoDto> list = map.getOrDefault(firstIndustryName, new ArrayList<>());
                list.add(indicatorInfoDto);
                map.put(firstIndustryName, list);
            }
        }

        // 构造返回值
        List<QueryAllBasicIndicatorDto> result = new ArrayList<>();
        map.forEach((key, value) -> {
            QueryAllBasicIndicatorDto queryAllBasicIndicatorDto = new QueryAllBasicIndicatorDto();
            queryAllBasicIndicatorDto.setName(key);
            queryAllBasicIndicatorDto.setIndicatorInfoList(value);

            result.add(queryAllBasicIndicatorDto);
        });

        return result;
    }

    @Override
    public List<QueryAllBasicIndicatorInfoDto> queryBasicIndicatorByKeyword(String keyword, Long indicatorId) throws Exception {
        int indicatorType = IndicatorTypeEnum.BASIC_INDICATOR.getIndicatorType();
        List<QueryAllBasicIndicatorInfoDto> result = indicatorMapper.selectByIndicatorTypeAndKeyword(indicatorType, keyword);
        List<Long> indicatorIdList = queryIndicatorIdByIndicatorGroupId(indicatorId);
        checkBasicIndicatorSelected(result, indicatorIdList);
        return result;
    }

    @Override
    @Transactional(transactionManager = "investTransactionManager", rollbackFor = Exception.class)
    public void addIndicatorForGroup(AddIndicatorForGroupRequest request) throws Exception {
        Long indicatorGroupId = request.getIndicatorGroupId();
        String userId = request.getUserId();
        checkAddOrDeleteInidcatorForGroup(indicatorGroupId, userId);
        // 校验添加的指标id是否是基础指标
        Set<Long> indicatorIdList = request.getIndicatorIdList();
        Boolean result = checkIndicatorId(indicatorIdList, IndicatorTypeEnum.BASIC_INDICATOR.getIndicatorType());
        if (!result) {
            throw new ValidatorException(SystemEnumCodeConfig.ERROR_VALIDATOR_PARAMETER);
        }

        // 查询添加的指标之前是否被删除过,如果被删除过更新删除状态为未删除,图表配置置为null
        List<Long> deleteIndicatorIdList = indexGroupRelationMapper.selectByGroupIdAndIndicatorIdAndDeleteStatus(indicatorGroupId, indicatorIdList, DeleteStatusEnum.DELETE.getDeleteStatus());
        if (deleteIndicatorIdList != null && !deleteIndicatorIdList.isEmpty()) {
            deleteIndicatorIdList.forEach(deleteIndicatorId -> {
                indicatorIdList.remove(deleteIndicatorId);
                indexGroupRelationMapper.updateDeleteStatusByGroupIdAndIndicatorId(indicatorGroupId, deleteIndicatorId, DeleteStatusEnum.UNDELETE.getDeleteStatus());
            });
        }
        // 剩下的基础指标id新增
        if (!indicatorIdList.isEmpty()) {
            insertGroupRelation(indicatorIdList, indicatorGroupId, userId);
        }
    }

    @Override
    @Transactional(transactionManager = "investTransactionManager", rollbackFor = Exception.class)
    public void deleteIndicatorForGroup(DeleteIndicatorForGroupRequest request) throws Exception {
        Long indicatorGroupId = request.getIndicatorGroupId();
        String userId = request.getUserId();
        checkAddOrDeleteInidcatorForGroup(indicatorGroupId, userId);

        indexGroupRelationMapper.updateDeleteStatusByGroupIdAndIndicatorId(indicatorGroupId, request.getIndicatorId(), DeleteStatusEnum.DELETE.getDeleteStatus());
    }

    @Override
    @Transactional(transactionManager = "investTransactionManager", rollbackFor = Exception.class)
    public void updateIndicatorData(UpdateIndicatorDataRequest request) throws Exception {
        Long indicatorDataId = request.getIndicatorDataId();
        // 校验数据id是否存在,数据来源是否是手动
        InvestnewIndexIndicatorData indexIndicatorData = indexIndicatorDataMapper.selectByPrimaryKey(indicatorDataId);
        if (indexIndicatorData == null || IndicatorDataAutoEnum.NOT_AUTO.getIndicatorDataAuto() != indexIndicatorData.getIsAuto()) {
            throw new ValidatorException(SystemEnumCodeConfig.ERROR_VALIDATOR_PARAMETER);
        }
        // 判断用户是否是指标管理者
        Long indicatorId = indexIndicatorData.getIndicatorId();
        String userId = request.getUserId();
        Boolean isManager = checkIndicatorManager(indicatorId, userId);
        if (!isManager) {
            throw new CustomException(SystemEnumCodeConfig.ERROR_AUTHORIZED);
        }
        // 指标的数据来源是自动还是手动
        InvestnewIndexIndicator indexIndicator = indicatorMapper.selectByPrimaryKey(indicatorId);
        String indicatorDataTime = request.getIndicatorDataTime();
        // 校验日期格式
        Date date = null;
        if (!StringUtil.isEmpty(indicatorDataTime)) {
            date = TimeUtil.string2date(indicatorDataTime, Constant.DEFAULT_DATE_FORMAT);
            if (date == null) {
                throw new ValidatorException(SystemEnumCodeConfig.ERROR_VALIDATOR_PARAMETER);
            }
        }

        // 要更新的指标数据
        InvestnewIndexIndicatorData updateIndicatorData = new InvestnewIndexIndicatorData();
        updateIndicatorData.setId(indicatorDataId);
        updateIndicatorData.setIndicatorData(request.getIndicatorData());
        updateIndicatorData.setStatus(IndicatorDataStatusEnum.CONFIRMED.getStatus());
        updateIndicatorData.setEditor(userId);

        if (IndicatorDataAutoEnum.NOT_AUTO.getIndicatorDataAuto() == indexIndicator.getIsAuto() && date != null) {
            // 如果指标数据来源是手动且需要修改指标数据日期,校验指标数据日期是否在当前指标数据的更新频率内
            // 获取当前指标数据的开始时间和结束时间
            IndicatorFrequencyEnum indicatorFrequencyEnum = IndicatorFrequencyEnum.valueOfString(indexIndicator.getIndicatorFrequency());
            Date startDate = TimeUtil.getIndicatorFrequencyStartTime(indicatorFrequencyEnum, indexIndicatorData.getIndicatorTime());
            Date endDate = TimeUtil.getIndicatorFrequencyEndTime(indicatorFrequencyEnum, indexIndicatorData.getIndicatorTime());
            // 如果周期为不定期则更新;如果日期超出范围,则报错;其他情况更新
            if (startDate != null && endDate != null
                    && (date.getTime() < startDate.getTime() || date.getTime() > endDate.getTime())) {
                throw new CustomException(IndicatorManageCodeConfig.ERROR_INDICATOR_DATA_TIME_BEYOND);
            } else {
                updateIndicatorData.setIndicatorTime(date);
            }
        }

        // 更新数据
        try {
            indexIndicatorDataMapper.updateByPrimaryKeySelective(updateIndicatorData);
        } catch (Exception e) {
            // 唯一约束,时间存在
            if (e instanceof DuplicateKeyException) {
                throw new CustomException(IndicatorManageCodeConfig.ERROR_INDICATOR_DATA_TIME_EXIST);
            } else {
                throw new RuntimeException(e);
            }
        }

        // TODO 发送消息通知
    }

    @Override
    @Transactional(transactionManager = "investTransactionManager", rollbackFor = Exception.class)
    public Long addIndicatorData(AddIndicatorDataRequest request) throws Exception {
        Long indicatorId = request.getIndicatorId();
        // 校验日期格式
        Date date = TimeUtil.string2date(request.getIndicatorDataTime(), Constant.DEFAULT_DATE_FORMAT);
        if (date == null) {
            throw new ValidatorException(SystemEnumCodeConfig.ERROR_VALIDATOR_PARAMETER);
        }
        // 校验指标id是否是特殊指标组
        InvestnewIndexIndicator indexIndicator = indicatorMapper.selectByPrimaryKey(indicatorId);
        if (indexIndicator == null || IndicatorTypeEnum.SPECIAL_INDICATOR_GROUP.getIndicatorType() != indexIndicator.getIndicatorType()) {
            throw new ValidatorException(SystemEnumCodeConfig.ERROR_VALIDATOR_PARAMETER);
        }
        // 获取基础指标id
        List<Long> indicatorIdList = indexGroupRelationMapper.selectIndicatorIdByGroupId(indicatorId, DeleteStatusEnum.UNDELETE.getDeleteStatus());
        if (indicatorIdList == null || indicatorIdList.isEmpty()) {
            throw new ValidatorException(SystemEnumCodeConfig.ERROR_VALIDATOR_PARAMETER);
        }
        Long basicIndicatorId = indicatorMapper.selectIndicatorIdByIdListAndType(indicatorIdList, IndicatorTypeEnum.BASIC_INDICATOR.getIndicatorType());
        if (basicIndicatorId == null) {
            throw new ValidatorException(SystemEnumCodeConfig.ERROR_VALIDATOR_PARAMETER);
        }
        InvestnewIndexIndicator basicIndicator = indicatorMapper.selectByPrimaryKey(basicIndicatorId);
        // 校验用户是否是指标管理制
        String userId = request.getUserId();
        Boolean isManager = checkIndicatorManager(basicIndicatorId, userId);
        if (!isManager) {
            throw new CustomException(SystemEnumCodeConfig.ERROR_AUTHORIZED);
        }

        InvestnewIndexIndicatorData saveOrUpdateData = new InvestnewIndexIndicatorData();

        // 查询添加的指标日期的周期内是否存在数据
        IndicatorFrequencyEnum indicatorFrequencyEnum = IndicatorFrequencyEnum.valueOfString(basicIndicator.getIndicatorFrequency());
        Date startDate = TimeUtil.getIndicatorFrequencyStartTime(indicatorFrequencyEnum, date);
        Date endDate = TimeUtil.getIndicatorFrequencyEndTime(indicatorFrequencyEnum, date);
        if (startDate != null && endDate != null) {
            // 如果周期不为不定期,查询该时间周期内是否存在数据
            InvestnewIndexIndicatorData indexIndicatorData = indexIndicatorDataMapper.selectByIndicatorIdAndStartTimeAndEndTime(basicIndicatorId, startDate, endDate);
            // 如果数据存在则抛出异常
            if (indexIndicatorData != null && indexIndicatorData.getIndicatorData() != null) {
                throw new CustomException(IndicatorManageCodeConfig.ERROR_INDICATOR_DATA_TIME_EXIST);
            }
            // 如果指标为手动更新且数据为null,则更新数据
            if (IndicatorDataAutoEnum.NOT_AUTO.getIndicatorDataAuto() == basicIndicator.getIndicatorType()
                    && indexIndicatorData != null && indexIndicatorData.getIndicatorData() == null) {
                saveOrUpdateData.setId(indexIndicatorData.getId());
            }
        }
        saveOrUpdateData.setIndicatorId(basicIndicatorId);
        saveOrUpdateData.setIndicatorTime(date);
        saveOrUpdateData.setIndicatorData(request.getIndicatorData());
        saveOrUpdateData.setIsAuto(IndicatorDataAutoEnum.NOT_AUTO.getIndicatorDataAuto());
        saveOrUpdateData.setStatus(IndicatorDataStatusEnum.CONFIRMED.getStatus());
        saveOrUpdateData.setEditor(userId);
        if (saveOrUpdateData.getId() == null) {
            saveOrUpdateData.setCreator(userId);
            indexIndicatorDataMapper.insertSelective(saveOrUpdateData);
        } else {
            indexIndicatorDataMapper.updateByPrimaryKeySelective(saveOrUpdateData);
        }

        // TODO 发送消息通知
        return basicIndicatorId;
    }

    /**
     * 校验指标组添加或者删除指标时的参数
     * @param indicatorGroupId
     * @param userId
     * @throws Exception
     */
    private void checkAddOrDeleteInidcatorForGroup(Long indicatorGroupId, String userId) throws Exception {
        // 校验indicatorId是否是指标组
        Boolean result = checkIndicatorType(indicatorGroupId, IndicatorTypeEnum.INDICATOR_GROUP.getIndicatorType());
        if (!result) {
            throw new ValidatorException(SystemEnumCodeConfig.ERROR_VALIDATOR_PARAMETER);
        }
        // 校验用户是否有权限
        result = checkIndicatorManager(indicatorGroupId, userId);
        if (!result) {
            throw new ValidatorException(SystemEnumCodeConfig.ERROR_VALIDATOR_PARAMETER);
        }
    }

    /**
     * 校验用户是否是指定管理者
     * @param indicatorId
     * @param userId
     * @return
     * @throws Exception
     */
    private Boolean checkIndicatorManager(Long indicatorId, String userId) throws Exception {
        InvestnewIndexIndicatorAnalyst indexIndicatorAnalyst = indicatorAnalystMapper.selectByIndicatorIdAndAnalystId(indicatorId, userId);
        return indexIndicatorAnalyst != null;
    }

    /**
     * 校验指标是否是指定类型
     * @param indicatorId
     * @param indicatorType
     * @throws Exception
     */
    private Boolean checkIndicatorType(Long indicatorId, Integer indicatorType) throws Exception {
        InvestnewIndexIndicator investnewIndexIndicator = indicatorMapper.selectByPrimaryKey(indicatorId);
        return investnewIndexIndicator != null && investnewIndexIndicator.getIndicatorType().equals(indicatorType);
    }

    /**
     * 校验指标idList是否全是指定类型
     * @param indicatorIdList
     * @param indicatorType
     * @throws Exception
     */
    private Boolean checkIndicatorId(Set<Long> indicatorIdList, Integer indicatorType) throws Exception {
        int count = indicatorMapper.selectCountByIdListAndType(indicatorIdList, indicatorType);
        return indicatorIdList.size() == count;
    }

    /**
     * 插入组和基础指标关系
     * @param indicatorIdList
     * @param groupId
     * @param userId
     * @throws Exception
     */
    private void insertGroupRelation(Set<Long> indicatorIdList, Long groupId, String userId) throws Exception {
        List<InvestnewIndexGroupRelation> list = new ArrayList<>();
        indicatorIdList.forEach(indicatorId -> {
            InvestnewIndexGroupRelation indexGroupRelation = new InvestnewIndexGroupRelation();
            indexGroupRelation.setIndicatorGroupId(groupId);
            indexGroupRelation.setIndicatorId(indicatorId);
            indexGroupRelation.setCreator(userId);
            indexGroupRelation.setEditor(userId);

            list.add(indexGroupRelation);
        });

        indexGroupRelationMapper.insertBatch(list);
    }

    /**
     * 修改指标信息
     * @param updateIndicator
     * @throws Exception
     */
    private void updateIndicator(InvestnewIndexIndicator updateIndicator) throws Exception {
        try {
            indicatorMapper.updateByPrimaryKeySelective(updateIndicator);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                throw new CustomException(IndicatorManageCodeConfig.ERROR_INDICATOR_NAME_EXIST);
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     *
     * @param indicatorIdList
     * @param startTime
     * @param endTime
     * @param groupIndicatorId 不为null，表示查特殊指标组和指标组的图
     * @param dataStatus 指定查询的数据状态，为null，自动的查待确认和已确认，手动的查已更新
     * @return
     * @throws Exception
     */
    private List<IndicatorChartDetailDto> queryIndicatorOrGroupChart(List<Long> indicatorIdList, String startTime, String endTime, Long groupIndicatorId, Integer dataStatus) throws Exception {
        Map<Long, InvestnewIndexIndicator> indexIndicatorMap = new HashMap<>();
        // 找出最高频率
        IndicatorFrequencyEnum maxIndicatorFrequencyEnum = IndicatorFrequencyEnum.DAY;
        for (Long id : indicatorIdList) {
            InvestnewIndexIndicator investnewIndexIndicator = indicatorMapper.selectByPrimaryKey(id);
            indexIndicatorMap.put(id, investnewIndexIndicator);
            IndicatorFrequencyEnum indicatorFrequencyEnum = IndicatorFrequencyEnum.valueOfString(investnewIndexIndicator.getIndicatorFrequency());
            if (indicatorFrequencyEnum.getSort() > maxIndicatorFrequencyEnum.getSort()) {
                maxIndicatorFrequencyEnum = indicatorFrequencyEnum;
            }
        }

        // 开始时间和结束时间
        Date startDate;
        Date endDate = null;
        if (StringUtil.isEmpty(startTime) ||  StringUtil.isEmpty(endTime)) {
            startDate = getIndicatorChartDate(maxIndicatorFrequencyEnum.getFrequency());
        } else {
            // 校验时间参数
            startDate = TimeUtil.string2date(startTime, Constant.DEFAULT_DATE_FORMAT);
            endDate = TimeUtil.string2date(endTime, Constant.DEFAULT_DATE_FORMAT);
            if (startDate == null || endDate == null) {
                throw new ValidatorException(SystemEnumCodeConfig.ERROR_VALIDATOR_PARAMETER);
            }
        }

        // 查询指标信息和指标数据信息
        List<IndicatorChartDetailDto> result = new ArrayList<>();
        for (Long id : indicatorIdList) {
            IndicatorChartDetailDto indicatorChartDetailDto = new IndicatorChartDetailDto();

            IndicatorBaseInfoDto indicatorBaseInfoDto = new IndicatorBaseInfoDto();

            InvestnewIndexIndicator investnewIndexIndicator = indexIndicatorMap.get(id);
            BeanUtils.copyProperties(investnewIndexIndicator,indicatorBaseInfoDto);

            // 查询图表配置
            if (groupIndicatorId != null) {
                InvestnewIndexGroupRelation indexGroupRelation = indexGroupRelationMapper.selectByGroupIdAndIndicatorId(groupIndicatorId, id);
                indicatorBaseInfoDto.setChartSetting(indexGroupRelation.getChartSetting());
            }

            // 查询数据
            List<Integer> statusList = new ArrayList<>();
            if (dataStatus == null) {
                if (investnewIndexIndicator.getIsAuto().equals(IndicatorDataAutoEnum.AUTO.getIndicatorDataAuto())) {
                    statusList.add(IndicatorDataStatusEnum.UNCONFIRMED.getStatus());
                    statusList.add(IndicatorDataStatusEnum.CONFIRMED.getStatus());
                } else {
                    statusList.add(IndicatorDataStatusEnum.CONFIRMED.getStatus());
                }
            } else {
                statusList.add(dataStatus);
            }

            List<InvestnewIndexIndicatorData> indicatorDataList = indexIndicatorDataMapper.selectByIndicatorIdAndTime(id, startDate, endDate, statusList);
            List<IndicatorValueDto> indicatorValueDtoList = new ArrayList<>();
            if (indicatorDataList != null && !indicatorDataList.isEmpty()) {
                for (InvestnewIndexIndicatorData indicatorData : indicatorDataList) {
                    IndicatorValueDto indicatorValueDto = new IndicatorValueDto();
                    Long indicatorDataId = indicatorData.getId();
                    indicatorValueDto.setId(indicatorDataId);
                    indicatorValueDto.setIndicatorId(id);
                    indicatorValueDto.setIndicatorTime(indicatorData.getIndicatorTime());
                    indicatorValueDto.setIndicatorData(indicatorData.getIndicatorData());
                    indicatorValueDto.setStatus(indicatorData.getStatus());
                    indicatorValueDto.setCreateTime(indicatorData.getCreateTime());
                    indicatorValueDto.setUpdateTime(indicatorData.getUpdateTime());

                    // 查询该数据点是否有点评
                    int count = indicatorCommentMapper.selectCountByObjectIdAndTypeAndPid(indicatorDataId, IndicatorCommentTypeEnum.INDICATOR_DATA.getType(), 0);
                    indicatorValueDto.setIsComment(count > 0);

                    indicatorValueDtoList.add(indicatorValueDto);
                }
            }
            BigDecimal rate = null;
            if (!indicatorValueDtoList.isEmpty()) {
                int size = indicatorValueDtoList.size();
                if (size >= 2) {
                    rate = indicatorValueDtoList.get(size - 1).getIndicatorData().subtract(indicatorValueDtoList.get(size - 2).getIndicatorData());
                } else {
                    rate = indicatorValueDtoList.get(size - 1).getIndicatorData();
                }
            }
            indicatorBaseInfoDto.setRate(rate);

            indicatorChartDetailDto.setBaseInfoDto(indicatorBaseInfoDto);
            indicatorChartDetailDto.setIndicatorValueList(indicatorValueDtoList);
            result.add(indicatorChartDetailDto);
        }

        return result;
    }

    /**
     * 获取数据图默认的开始时间
     * @param indicatorFrequency
     * @return
     * @throws Exception
     */
    private Date getIndicatorChartDate(String indicatorFrequency) throws Exception {
        IndicatorFrequencyEnum indicatorFrequencyEnum = IndicatorFrequencyEnum.valueOfString(indicatorFrequency);
        Calendar calendar = Calendar.getInstance();
        switch (indicatorFrequencyEnum) {
            case DAY:
                calendar.add(Calendar.MONTH, -1);
                return calendar.getTime();
            case WEEK:
                calendar.add(Calendar.MONTH, -3);
                return calendar.getTime();
            case MONTH:
            case QUARTER:
                calendar.add(Calendar.YEAR, -3);
                return calendar.getTime();
            case HALF_YEAR:
            case YEAR:
            default:
                return null;
        }
    }

    /**
     * 填充指标研究员关系表
     * @param indicatorId
     * @param userId
     * @throws Exception
     */
    private void insertIndicatorAnalyst(Long indicatorId, String userId) throws Exception {
        List<InvestnewIndexIndicatorAnalyst> analysts = new ArrayList<>();
        InvestnewIndexIndicatorAnalyst analyst = new InvestnewIndexIndicatorAnalyst();
        analyst.setIndicatorId(indicatorId);
        analyst.setAnalystId(userId);
        analyst.setAnalystName(userInfoService.getUserInfo(userId).getNickname());
        analyst.setCreator(userId);
        analyst.setEditor(userId);
        analysts.add(analyst);

        indicatorAnalystMapper.insertBatch(analysts);
    }

    /**
     * 填充指标行业关系表
     * @param list
     * @param indicatorId
     * @param userId
     * @throws Exception
     */
    private void insertIndicatorIndustry(List<IndustryItem> list, Long indicatorId, String userId) throws Exception {
        List<InvestnewIndexIndicatorIndustry> indexIndicatorIndustryList = new ArrayList<>();
        for (IndustryItem item : list) {
            InvestnewIndexIndicatorIndustry indexIndicatorIndustry = new InvestnewIndexIndicatorIndustry();
            indexIndicatorIndustry.setIndicatorId(indicatorId);
            InvestnewIndexIndustry secondIndustry = industryMapper.selectSecondByIndustry(item.getIndustryName());
            if (secondIndustry == null) {
                throw new CustomException(SystemEnumCodeConfig.ERROR_VALIDATOR_PARAMETER);
            }
            InvestnewIndexIndustry firstIndustry = industryMapper.selectByPrimaryKey(secondIndustry.getParentId());
            indexIndicatorIndustry.setIndicatorId(indicatorId);
            indexIndicatorIndustry.setFirstIndustryName(firstIndustry.getIndustryName());
            indexIndicatorIndustry.setSecondIndustryName(item.getIndustryName());
            indexIndicatorIndustry.setCreator(userId);
            indexIndicatorIndustry.setEditor(userId);
            indexIndicatorIndustryList.add(indexIndicatorIndustry);
        }

        indicatorIndustryMapper.insertBatch(indexIndicatorIndustryList);
    }

    /**
     * 填充指标股票关系表
     * @param list
     * @param indicatorId
     * @param userId
     * @throws Exception
     */
    private void insertIndicatorStock(List<StockItem> list, Long indicatorId, String userId) throws Exception {
        List<InvestnewIndexIndicatorStock> indexIndicatorStockList = new ArrayList<>();
        for (StockItem item : list) {
            InvestnewIndexIndicatorStock indexIndicatorStock = new InvestnewIndexIndicatorStock();
            indexIndicatorStock.setIndicatorId(indicatorId);

            InvestnewIndexStock investnewIndexStock = stockMapper.selectByCodeAndName(item.getStockCode(), item.getStockName());
            if (investnewIndexStock == null) {
                throw new CustomException(SystemEnumCodeConfig.ERROR_VALIDATOR_PARAMETER);
            }
            indexIndicatorStock.setIndicatorId(indicatorId);
            indexIndicatorStock.setStockCode(item.getStockCode());
            indexIndicatorStock.setStockName(item.getStockName());
            indexIndicatorStock.setCreator(userId);
            indexIndicatorStock.setEditor(userId);
            indexIndicatorStockList.add(indexIndicatorStock);
        }

        indexIndicatorStockMapper.insertBatch(indexIndicatorStockList);
    }

    /**
     * 判断基础指标是否已选中
     * @param indicatorInfoList
     * @param indicatorIdList
     * @throws Exception
     */
    private void checkBasicIndicatorSelected(List<QueryAllBasicIndicatorInfoDto> indicatorInfoList, List<Long> indicatorIdList) throws Exception {
        if (indicatorInfoList != null && !indicatorInfoList.isEmpty() && indicatorIdList != null && !indicatorIdList.isEmpty()) {
            // 判断是否是选中
            for (QueryAllBasicIndicatorInfoDto indicatorInfoDto : indicatorInfoList) {
                indicatorInfoDto.setSelected(indicatorIdList.contains(indicatorInfoDto.getId()));
            }
        }
    }

    /**
     * 查询指标组关联的基础指标id
     * @param indicatorGroupId
     * @return
     * @throws Exception
     */
    private List<Long> queryIndicatorIdByIndicatorGroupId(Long indicatorGroupId) throws Exception {
        List<Long> indicatorIdList = null;
        if (indicatorGroupId != null) {
            // 判断是否是指标组
            InvestnewIndexIndicator investnewIndexIndicator = indicatorMapper.selectByPrimaryKey(indicatorGroupId);
            if (!investnewIndexIndicator.getIndicatorType().equals(IndicatorTypeEnum.INDICATOR_GROUP.getIndicatorType())) {
                throw new ValidatorException(SystemEnumCodeConfig.ERROR_VALIDATOR_PARAMETER);
            }
            // 查询指标组下的关联基础指标
            indicatorIdList = indexGroupRelationMapper.selectIndicatorIdByGroupId(indicatorGroupId, DeleteStatusEnum.UNDELETE.getDeleteStatus());
        }
        return indicatorIdList;
    }
}
