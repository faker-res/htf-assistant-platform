package com.htf.bigdata.invest.indicatormanage.service.impl;

import com.htf.bigdata.invest.indicatormanage.component.context.HtfContext;
import com.htf.bigdata.invest.indicatormanage.config.enums.IndicatorCommentTypeEnum;
import com.htf.bigdata.invest.indicatormanage.config.enums.QueryBoardTypeEnum;
import com.htf.bigdata.invest.indicatormanage.dao.invest.*;
import com.htf.bigdata.invest.indicatormanage.model.domain.*;
import com.htf.bigdata.invest.indicatormanage.model.response.dto.*;
import com.htf.bigdata.invest.indicatormanage.model.vo.BoardSearchRequest;
import com.htf.bigdata.invest.indicatormanage.model.vo.IndustryItem;
import com.htf.bigdata.invest.indicatormanage.model.vo.PublishIndicatorRequest;
import com.htf.bigdata.invest.indicatormanage.model.vo.StockItem;
import com.htf.bigdata.invest.indicatormanage.service.IndicatorBoardService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.htf.bigdata.invest.indicatormanage.config.enums.SelectStatusEnum.SELECTED;
import static java.util.Comparator.comparing;

/**
 * @author xuyali
 * @date 2019/6/13 11:32
 */
@Service
public class IndicatorBoardServiceImpl implements IndicatorBoardService {
    @Autowired
    private IndicatorBoardMapper indicatorBoardMapper;
    @Autowired
    private InvestnewIndexIndicatorStockMapper indicatorStockMapper;

    @Autowired
    private InvestnewIndexIndicatorMapper indicatorMapper;

    @Autowired
    private InvestnewIndexIndicatorAnalystMapper indicatorAnalystMapper;

    @Autowired
    private InvestnewIndexIndicatorIndustryMapper indicatorIndustryMapper;

    @Autowired
    private InvestnewIndexGroupRelationMapper groupRelationMapper;

    @Autowired
    private IInvestnewIndexGroupPublishMapper groupPublishMapper;

    @Autowired
    private InvestnewIndexIndicatorDataMapper indexIndicatorDataMapper;

    @Autowired
    private InvestnewIndexIndicatorCommentMapper commentMapper;

    @Autowired
    private InvestnewIndexIndicatorTextMapper indicatorTextMapper;

    private static final int indicatorGroup = 2;//指标组

    private static final int specialGroup = 4;//特殊指标组

    private static final int specialIndicator = 5;//特殊指标

    @Override
    public long queryTotalCount(BoardSearchRequest searchRequest) {
        searchRequest.setCurrentPage(0);//不分页
        List<IndicatorSummaryDto> summaryDtoList = querySummaryIndicatorList(searchRequest);
        return summaryDtoList.size();
    }

    private List<IndicatorSummaryDto> querySummaryIndicatorList(BoardSearchRequest searchRequest) {
        String userId = HtfContext.htfContext.get().getUserId();
        QueryBoardTypeEnum type = searchRequest.getType();
        List<IndicatorSummaryDto> summaryDtoList = new ArrayList<>();
        int pageSize = searchRequest.getPageSize();
        int currentPage = searchRequest.getCurrentPage();
        int start = (currentPage - 1) * pageSize;
        switch (type) {
            case MANAGER:
                summaryDtoList = queryManageIndicator(userId, searchRequest.getOrderByUpdate(), start, pageSize);
                break;
            case ATTENTION:
                summaryDtoList = queryAttentionIndicator(userId, searchRequest.getOrderByUpdate(), start, pageSize);
                break;
            case ALL:
                summaryDtoList = queryAllIndicator(searchRequest.getOrderByUpdate(), start, pageSize);
                break;
            case INDUSTRY:
                summaryDtoList = queryIndustryIndicator(searchRequest.getSecondIndustryName(), searchRequest.getOrderByUpdate(), start, pageSize);
                break;
            default:
                summaryDtoList = queryKeyWordIndicator(searchRequest.getKeyWord(), searchRequest.getOrderByUpdate(), start, pageSize);
        }
        return summaryDtoList;
    }

    @Override
    public List<IndicatorBoardDto> queryIndicatorBoardList(BoardSearchRequest searchRequest) {
        List<IndicatorSummaryDto> summaryDtoList = querySummaryIndicatorList(searchRequest);
        List<IndicatorBoardDto> indicatorBoardDtoList = new ArrayList<>();
        //查询看板信息
        for (IndicatorSummaryDto summaryDto : summaryDtoList) {
            IndicatorBoardDto indicatorBoardDto = new IndicatorBoardDto();
            IndicatorBaseInfoDto baseInfoDto = queryBaseinfoById(summaryDto.getIndicatorId(), true);
            indicatorBoardDto.setBaseInfoDto(baseInfoDto);
            if (baseInfoDto.getIndicatorType() == indicatorGroup || baseInfoDto.getIndicatorType() == specialGroup) {
                List<PublishIndicatorDto> publishIndicatorList = new ArrayList<>();
                List<Long> indicatorIdList = new ArrayList<>();
                //先查询发布表中有没有,没有的话从取关系表中查询
                InvestnewIndexGroupPublishExample example = new InvestnewIndexGroupPublishExample();
                example.createCriteria().andIndicatorGroupIdEqualTo(summaryDto.getIndicatorId());
                List<InvestnewIndexGroupPublish> groupPublishList = groupPublishMapper.selectByExampleWithBLOBs(example);
                if(CollectionUtils.isNotEmpty(groupPublishList)){
                    indicatorIdList = groupPublishList.stream().map(g->g.getIndicatorId()).collect(Collectors.toList());
                    for (InvestnewIndexGroupPublish groupPublish : groupPublishList) {
                        PublishIndicatorDto publishIndicator = new PublishIndicatorDto();
                        publishIndicator.setIndicatorId(groupPublish.getIndicatorId());
                        publishIndicator.setIndicatorName(indicatorMapper.selectByPrimaryKey(groupPublish.getIndicatorId()).getIndicatorName());
                        List<IndicatorValueDto> valueDtoList = queryIndicatorValue(groupPublish.getIndicatorId(), groupPublish.getStartTime(), groupPublish.getEndTime());
                        if (CollectionUtils.isNotEmpty(valueDtoList)) {
                            publishIndicator.setIndicatorValueDtoList(valueDtoList);
                        }
                        publishIndicatorList.add(publishIndicator);
                    }
                }else{
                    //从取关系表中查询
                    //查询指标组下的未删除的各指标的指标值列表
                    List<InvestnewIndexGroupRelation> groupRelationList = groupRelationMapper.selectIndicatorByGroupId(summaryDto.getIndicatorId(), "0","");
                    indicatorIdList = groupRelationList.stream().map(s->s.getIndicatorId()).collect(Collectors.toList());
                    for (InvestnewIndexGroupRelation groupRelation : groupRelationList) {
                        PublishIndicatorDto publishIndicator = new PublishIndicatorDto();
                        publishIndicator.setIndicatorId(groupRelation.getIndicatorId());
                        publishIndicator.setIndicatorName(indicatorMapper.selectByPrimaryKey(groupRelation.getIndicatorId()).getIndicatorName());
                        publishIndicator.setChartSetting(groupRelation.getChartSetting());
                        List<IndicatorValueDto> valueList = queryIndicatorValue(groupRelation.getIndicatorId(), baseInfoDto.getStartTime(), baseInfoDto.getEndTime());
                        publishIndicator.setIndicatorValueDtoList(valueList);
                        publishIndicatorList.add(publishIndicator);
                    }
                }

                indicatorBoardDto.setPublishIndicatorList(publishIndicatorList);
                //查询某指标下的最新点评
                List<IndicatorCommentDto> commentDtoList = new ArrayList<>();
                indicatorIdList.forEach(indictId -> {
                    IndicatorCommentDto commentDto = queryIndicatorComment(indictId, IndicatorCommentTypeEnum.INDICATOR_DATA);
                    if (commentDto != null) {
                        commentDtoList.add(commentDto);
                    }
                });
                if (CollectionUtils.isNotEmpty(commentDtoList)) {
                    indicatorBoardDto.setLastComment(commentDtoList.stream().sorted(comparing(IndicatorCommentDto::getCommentTime).reversed()).findFirst().orElse(null));
                }
            } else if (baseInfoDto.getIndicatorType() == specialIndicator) {
                //查询特殊指标的信息
                InvestnewIndexIndicatorText indicatorText = indicatorTextMapper.selectByIndicatorId(summaryDto.getIndicatorId());
                indicatorBoardDto.setContent(indicatorText.getIndicatorTextData());
                indicatorBoardDto.setLastComment(queryIndicatorComment(summaryDto.getIndicatorId(), IndicatorCommentTypeEnum.SPECIAL_INDICATOR));
            }
            indicatorBoardDtoList.add(indicatorBoardDto);
        }
        return indicatorBoardDtoList;
    }

    //我的管理
    private List<IndicatorSummaryDto> queryManageIndicator(String userId, Boolean orderByUpdate, int start, int currentPage) {
        return indicatorBoardMapper.selectManageIndicator(userId, orderByUpdate, start, currentPage);
    }

    //我的关注
    private List<IndicatorSummaryDto> queryAttentionIndicator(String userId, Boolean orderByUpdate, int start, int currentPage) {
        return indicatorBoardMapper.selectAttentionIndicator(userId, orderByUpdate, start, currentPage);
    }

    //全部指标
    private List<IndicatorSummaryDto> queryAllIndicator(Boolean orderByUpdate, int start, int currentPage) {
        return indicatorBoardMapper.selectAllIndicator(orderByUpdate, start, currentPage);
    }

    //根据行业查询指标
    private List<IndicatorSummaryDto> queryIndustryIndicator(String secondIndustryName, Boolean orderByUpdate, int start, int currentPage) {
        return indicatorBoardMapper.selectIndustryIndicator(secondIndustryName, orderByUpdate, start, currentPage);
    }

    //根据关键词查询指标
    private List<IndicatorSummaryDto> queryKeyWordIndicator(String keyWord, Boolean orderByUpdate, int start, int currentPage) {
        return indicatorBoardMapper.selectKeyWordIndicator(keyWord, orderByUpdate, start, currentPage);
    }

    //根据指标(组)id查询看板信息
    @Override
    public IndicatorBaseInfoDto queryBaseinfoById(Long indicatorId, boolean queryStock) {
        InvestnewIndexIndicator indexIndicator = indicatorMapper.selectByPrimaryKey(indicatorId);
        IndicatorBaseInfoDto baseInfoDto = new IndicatorBaseInfoDto();
        BeanUtils.copyProperties(indexIndicator, baseInfoDto);
        baseInfoDto.setIndustries(queryIndustryItems(indicatorId));
        baseInfoDto.setResearchers(queryResearchers(indicatorId));
        if (queryStock) {
            baseInfoDto.setStocks(queryStockItems(indicatorId));
        }
        return baseInfoDto;
    }

    //查询股票关联的研究员列表
    private List<String> queryResearchers(Long indicatorId) {
        List<InvestnewIndexIndicatorAnalyst> indicatorAnalysts = indicatorAnalystMapper.selectByIndicatorId(indicatorId);
        return indicatorAnalysts.stream().map(s -> s.getAnalystName()).collect(Collectors.toList());
    }

    //查看指标关联的股票列表
    private List<StockItem> queryStockItems(Long indicatorId) {
        List<InvestnewIndexIndicatorStock> indicatorStockList = indicatorStockMapper.selectByIndicatorId(indicatorId);
        List<StockItem> stockItemList =
                indicatorStockList.stream().map(s -> {
                    StockItem stockItem = new StockItem();
                    BeanUtils.copyProperties(s, stockItem);
                    return stockItem;
                }).collect(Collectors.toList());
        return stockItemList;
    }

    //查看指标关联的行业列表
    private List<IndustryItem> queryIndustryItems(Long indicatorId) {
        List<InvestnewIndexIndicatorIndustry> indicatorIndustryList = indicatorIndustryMapper.selectByIndicatorId(indicatorId);
        List<IndustryItem> industryItemList =
                indicatorIndustryList.stream().map(s -> {
                    IndustryItem industryItem = new IndustryItem();
                    industryItem.setIndustryName(s.getSecondIndustryName());
                    return industryItem;
                }).collect(Collectors.toList());
        return industryItemList;
    }

    //查看指标点评
    private IndicatorCommentDto queryIndicatorComment(Long indicatorId, IndicatorCommentTypeEnum type) {
        IndicatorCommentDto indicatorCommentDto = null;
        switch (type) {
            case INDICATOR_DATA:
                InvestnewIndexIndicatorComment indicatorComment = commentMapper.selectLastComment(indicatorId);
                if (indicatorComment != null) {
                    indicatorCommentDto = new IndicatorCommentDto();
                    BeanUtils.copyProperties(indicatorComment, indicatorCommentDto);
                    indicatorCommentDto.setCommentTime(indicatorComment.getUpdateTime());
                    Long objectId = indicatorCommentDto.getObjectId();
                    //根据指标数值id查询指标数值信息
                    InvestnewIndexIndicatorData indicatorData = indexIndicatorDataMapper.selectByPrimaryKey(objectId);
                    indicatorCommentDto.setIndicatorTime(indicatorData.getIndicatorTime());
                    indicatorCommentDto.setIndicatorData(indicatorData.getIndicatorData());
                }
                break;
            case SPECIAL_INDICATOR:
                InvestnewIndexIndicatorComment specialIndicatorComment = commentMapper.selectSpecialIndicatorComment(indicatorId);
                if (specialIndicatorComment != null) {
                    indicatorCommentDto = new IndicatorCommentDto();
                    BeanUtils.copyProperties(specialIndicatorComment, indicatorCommentDto);
                    indicatorCommentDto.setCommentTime(specialIndicatorComment.getUpdateTime());
                }
                break;
            default:
                return null;
        }
        return indicatorCommentDto;
    }

    @Override
    public List<IndicatorValueDto> queryIndicatorValue(Long indicatorId, Date startTime, Date endTime) {
        List<InvestnewIndexIndicatorData> indicatorDataList = indexIndicatorDataMapper.selectConfirmedIndicatorValue(indicatorId, startTime, endTime);
        if (CollectionUtils.isEmpty(indicatorDataList)) {
            return null;
        }
        List<IndicatorValueDto> indicatorValueDtoList = indicatorDataList.stream().map(s -> {
            IndicatorValueDto indicatorValueDto = new IndicatorValueDto();
            BeanUtils.copyProperties(s, indicatorValueDto);
            return indicatorValueDto;
        }).collect(Collectors.toList());
        return indicatorValueDtoList;
    }

    @Override
    @Transactional
    public boolean publishIndicator(PublishIndicatorRequest publishRequest) {
        saveDraft(publishRequest);
        String userId = HtfContext.htfContext.get().getUserId();
        InvestnewIndexIndicator indexIndicator = indicatorMapper.selectByPrimaryKey(publishRequest.getIndicatorGroupId());
        indexIndicator.setStartTime(publishRequest.getStartTime());
        indexIndicator.setEndTime(publishRequest.getEndTime());
        indicatorMapper.updateByPrimaryKey(indexIndicator);
        //先删除组里面的发布，再插入
        InvestnewIndexGroupPublishExample example = new InvestnewIndexGroupPublishExample();
        InvestnewIndexGroupPublishExample.Criteria criteria = example.createCriteria();
        criteria.andIndicatorGroupIdEqualTo(publishRequest.getIndicatorGroupId());
        groupPublishMapper.deleteByExample(example);
        //插入每个指标发布的图和时间
        for (PublishIndicatorRequest.IndicatorInfo indicatorInfo : publishRequest.getIndicatorInfoList()) {
            //groupPublishMapper
            if(indicatorInfo.getStatusEnum() == SELECTED){
                InvestnewIndexGroupPublish indexGroupPublish = new InvestnewIndexGroupPublish();
                indexGroupPublish.setIndicatorGroupId(publishRequest.getIndicatorGroupId());
                indexGroupPublish.setIndicatorId(indicatorInfo.getIndicatorId());
                indexGroupPublish.setChartSetting(indicatorInfo.getChartSetting());
                indexGroupPublish.setStartTime(publishRequest.getStartTime());
                indexGroupPublish.setEndTime(publishRequest.getEndTime());
                indexGroupPublish.setCreator(userId);
                indexGroupPublish.setCreateTime(new Date());
                indexGroupPublish.setUpdateTime(new Date());
                groupPublishMapper.insertSelective(indexGroupPublish);
            }
        }
        return true;
    }

    @Override
    public boolean saveDraft(PublishIndicatorRequest publishRequest) {
        //更新发布的图配置
        for (PublishIndicatorRequest.IndicatorInfo indicatorInfo : publishRequest.getIndicatorInfoList()) {
            InvestnewIndexGroupRelation groupRelation = groupRelationMapper.selectByGroupIdAndIndicatorId(publishRequest.getIndicatorGroupId(), indicatorInfo.getIndicatorId());
            groupRelation.setIsSelect(indicatorInfo.getStatusEnum().getSelectStatus());
            groupRelation.setChartSetting(indicatorInfo.getChartSetting());
            groupRelation.setUpdateTime(new Date());
            groupRelationMapper.updateByPrimaryKeyWithBLOBs(groupRelation);
        }
        return true;
    }

    @Override
    public List<Long> attentionIndicatorIdList(String keyWord, String firstIndustryName) {
        String userId = HtfContext.htfContext.get().getUserId();
        return indicatorBoardMapper.selectAttentionIds(userId, keyWord, firstIndustryName);

    }

    @Override
    public long queryTotalCount(String keyWord, String firstIndustryName) {
        List<Long> allIndicatorIlds = indicatorBoardMapper.selectAllIndicatorIds(keyWord, firstIndustryName, -1, 0);
        return allIndicatorIlds.size();
    }

    @Override
    public List<IndicatorBaseInfoDto> allIndicatorList(String keyWord, String firstIndustryName, int pageSize, int currentPage) {
        int start = (currentPage - 1) * pageSize;
        List<Long> allIndicatorIlds = indicatorBoardMapper.selectAllIndicatorIds(keyWord, firstIndustryName, start, pageSize);
        List<IndicatorBaseInfoDto> baseInfoDtoList = allIndicatorIlds.stream().map(id -> {
            IndicatorBaseInfoDto baseInfoDto = queryBaseinfoById(id, false);
            return baseInfoDto;
        }).collect(Collectors.toList());
        return baseInfoDtoList;
    }
}
