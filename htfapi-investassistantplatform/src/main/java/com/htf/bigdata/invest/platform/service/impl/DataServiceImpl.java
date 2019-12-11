package com.htf.bigdata.invest.platform.service.impl;

import com.htf.bigdata.invest.platform.config.enums.CompanyTypeEnum;
import com.htf.bigdata.invest.platform.dao.invest.IInvestnewAnalystDao;
import com.htf.bigdata.invest.platform.dao.invest.IInvestnewBrokerDao;
import com.htf.bigdata.invest.platform.dao.invest.IInvestnewFundDao;
import com.htf.bigdata.invest.platform.dao.invest.IInvestnewIndustryDao;
import com.htf.bigdata.invest.platform.model.bo.data.AnalystBO;
import com.htf.bigdata.invest.platform.model.bo.data.CompanyBO;
import com.htf.bigdata.invest.platform.model.bo.data.IndustryBO;
import com.htf.bigdata.invest.platform.model.invest.InvestnewAnalystModel;
import com.htf.bigdata.invest.platform.model.invest.InvestnewBrokerModel;
import com.htf.bigdata.invest.platform.model.invest.InvestnewFundModel;
import com.htf.bigdata.invest.platform.model.invest.InvestnewIndustryModel;
import com.htf.bigdata.invest.platform.service.IDataService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataServiceImpl implements IDataService {

    private final static Logger logger = LogManager.getLogger(DataServiceImpl.class);

    @Autowired
    private IInvestnewFundDao investnewFundDao;

    @Autowired
    private IInvestnewBrokerDao investnewBrokerDao;

    @Autowired
    private IInvestnewAnalystDao investnewAnalystDao;

    @Autowired
    private IInvestnewIndustryDao investnewIndustryDao;

    @Override
    public List<CompanyBO> getFund() {
        List<CompanyBO> list = new ArrayList<>();
        List<InvestnewFundModel> investnewFundModelList = investnewFundDao.getAll();
        for (InvestnewFundModel item : investnewFundModelList){
            CompanyBO companyBO = new CompanyBO();
            companyBO.setCompany_id(item.getOrg_uni_code());
            companyBO.setSname(item.getOrg_sname());
            companyBO.setFullname(item.getOrg_name());
            companyBO.setType(CompanyTypeEnum.FUND);
            list.add(companyBO);
        }
        return list;
    }

    @Override
    public List<CompanyBO> getBroker() {
        List<CompanyBO> list = new ArrayList<>();
        List<InvestnewBrokerModel> investnewBrokerModelList = investnewBrokerDao.getAll();
        for (InvestnewBrokerModel item : investnewBrokerModelList){
            CompanyBO companyBO = new CompanyBO();
            companyBO.setCompany_id(item.getOrg_uni_code());
            companyBO.setSname(item.getOrg_sname());
            companyBO.setFullname(item.getOrg_name());
            companyBO.setType(CompanyTypeEnum.BROKER);
            list.add(companyBO);
        }
        return list;
    }

    @Override
    public List<IndustryBO> getIndustry() {
        List<IndustryBO> list = new ArrayList<>();
        List<InvestnewIndustryModel> investnewIndustryModelList = investnewIndustryDao.getAll();
        for (InvestnewIndustryModel item : investnewIndustryModelList){
            IndustryBO industryBO = new IndustryBO();
            industryBO.setIndustry_id(item.getIndex_uni_code());
            industryBO.setSname(item.getSec_name());
            list.add(industryBO);
        }
        return list;
    }

    @Override
    public CompanyBO findCompany(String companyName) {
        List<CompanyBO> fundList = getFund();
        for (CompanyBO item : fundList){
            if ((item.getSname() != null && item.getSname().equals(companyName))
                    || (item.getFullname() != null && item.getFullname().equals(companyName))){
                return item;
            }
        }
        List<CompanyBO> brokerList = getBroker();
        for (CompanyBO item : brokerList){
            if ((item.getSname() != null && item.getSname().equals(companyName))
                    || (item.getFullname() != null && item.getFullname().equals(companyName))){
                return item;
            }
        }
        return null;
    }

    @Override
    public CompanyBO findCompany(Long companyId, String companyType) {
        if (companyId == null || StringUtils.isEmpty(companyType)){
            return null;
        }
        try{
            CompanyTypeEnum companyTypeEnum = CompanyTypeEnum.valueOf(companyType.toUpperCase());
            if (companyTypeEnum.equals(CompanyTypeEnum.FUND)){
                List<CompanyBO> fundList = getFund();
                for (CompanyBO item : fundList){
                    if (item.getCompany_id().equals(companyId)){
                        return item;
                    }
                }
            }else if (companyTypeEnum.equals(CompanyTypeEnum.BROKER)){
                List<CompanyBO> brokerList = getBroker();
                for (CompanyBO item : brokerList){
                    if (item.getCompany_id().equals(companyId)){
                        return item;
                    }
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    @Override
    public CompanyBO findCompany(Long companyId) {
        CompanyBO companyBO = findCompany(companyId,CompanyTypeEnum.BROKER.name());
        if (companyBO == null){
            companyBO = findCompany(companyId,CompanyTypeEnum.FUND.name());
        }
        return companyBO;
    }

    @Override
    public AnalystBO findAnalyst(String analystName, String companyName) {
        List<InvestnewAnalystModel> investnewAnalystModelList = investnewAnalystDao.selectByName(analystName);
        if (investnewAnalystModelList.isEmpty()){
            return null;
        }
        InvestnewAnalystModel model = null;
        if (StringUtils.isNotEmpty(companyName)){
            for (InvestnewAnalystModel item : investnewAnalystModelList){
                if (item.getOrg_sname().equals(companyName)){
                    model = item;
                    break;
                }
            }
        }
        if (model == null){
            model = investnewAnalystModelList.get(0);
        }
        AnalystBO analystBO = new AnalystBO();
        analystBO.setPeo_uni_code(model.getPeo_uni_code());
        analystBO.setName(model.getName());
        analystBO.setAnalyst_code(model.getAnalyst_code());

        CompanyBO companyBO = null;
        if (StringUtils.isNotEmpty(model.getOrg_sname())){
            companyBO = findCompany(model.getOrg_sname());
        }else if (model.getOrg_uni_code() != null){
            companyBO = findCompany(model.getOrg_uni_code());
        }
        analystBO.setCompany(companyBO);

        return analystBO;
    }
}
