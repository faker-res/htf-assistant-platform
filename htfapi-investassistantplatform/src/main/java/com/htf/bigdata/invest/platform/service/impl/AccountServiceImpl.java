package com.htf.bigdata.invest.platform.service.impl;

import com.htf.bigdata.invest.platform.component.util.RandomUtil;
import com.htf.bigdata.invest.platform.model.invest.InvestnewUserModel;
import com.htf.bigdata.invest.platform.model.request.account.AccountAddRequest;
import com.htf.bigdata.invest.platform.model.request.account.AccountCompleteInfoRequest;
import com.htf.bigdata.invest.platform.model.request.account.AccountModifyRequest;
import com.htf.bigdata.invest.platform.model.request.account.AccountRegisterRequest;
import com.htf.bigdata.invest.platform.model.response.account.AccountListResponse;
import com.htf.bigdata.invest.platform.service.IAccessService;
import com.htf.bigdata.invest.platform.service.IAccountService;
import com.htf.bigdata.invest.platform.component.util.RsaUtil;
import com.htf.bigdata.invest.platform.config.code.AccountEnumCodeConfig;
import com.htf.bigdata.invest.platform.config.enums.CompanyTypeEnum;
import com.htf.bigdata.invest.platform.dao.invest.IInvestnewUserDao;
import com.htf.bigdata.invest.platform.model.bo.account.AccountBO;
import com.htf.bigdata.invest.platform.model.bo.account.RoleBO;
import com.htf.bigdata.invest.platform.model.bo.data.CompanyBO;
import com.htf.bigdata.invest.platform.service.IDataService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountServiceImpl implements IAccountService {

    private final static Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    @Autowired
    private IDataService dataService;

    @Autowired
    private IAccessService accessService;

    @Autowired
    private IInvestnewUserDao investnewUserDao;

    @Override
    public Boolean deleteUser(String userId) throws Exception {
        InvestnewUserModel investnewUserModel = userModelByUserId(userId);
        if (investnewUserModel == null){
            throw new Exception(AccountEnumCodeConfig.USER_NO_EXISTS.getMessage());
        }
        return investnewUserDao.deleteByPrimaryKey(investnewUserModel.getId()) > 0;
    }

    @Override
    public Boolean completeInfo(AccountCompleteInfoRequest param) throws Exception {
        InvestnewUserModel investnewUserModel = userModelByUserId(param.getUserId());
        if (investnewUserModel == null){
            throw new Exception(AccountEnumCodeConfig.USER_NO_EXISTS.getMessage());
        }

        InvestnewUserModel model = new InvestnewUserModel();
        model.setId(investnewUserModel.getId());
        model.setUser_id(investnewUserModel.getUser_id());

        if (StringUtils.isNotEmpty(param.getNickname())){
            model.setNickname(param.getNickname());
        }
        if (StringUtils.isNotEmpty(param.getEmail())){
            model.setEmail(param.getEmail());
        }
        model.setCompany_id(param.getCompany_id());
        model.setCompany_type(param.getCompany_type().name().toLowerCase());
        return investnewUserDao.updateByPrimaryKeySelective(model) > 0;
    }

    @Override
    public AccountBO login(String account, String password) throws Exception {
        InvestnewUserModel item = investnewUserDao.selectByAccount(account);
        if (item == null){
            throw new Exception(AccountEnumCodeConfig.USER_NO_EXISTS.getMessage());
        }
        String decryptPassword = RsaUtil.decryptData(item.getPassword());
        if (decryptPassword == null){
            throw new Exception(AccountEnumCodeConfig.PASSWORD_FAIL.getMessage());
        }
        if (!decryptPassword.equals(password)){
            throw new Exception(AccountEnumCodeConfig.PASSWORD_FAIL.getMessage());
        }

        CompanyBO companyBO = dataService.findCompany(item.getCompany_id(),item.getCompany_type());
        List<RoleBO> roleBOList = accessService.getUserRole(item.getUser_id());

        AccountBO accountBO = new AccountBO();
//        accountBO.setId(item.getId());
        accountBO.setUser_id(item.getUser_id());
        accountBO.setCompany(companyBO);
        accountBO.setUsername(item.getUsername());
        accountBO.setEmail(item.getEmail());
        accountBO.setMobile(item.getMobile());
        accountBO.setNickname(item.getNickname());
        accountBO.setAvatar(item.getAvatar());
        accountBO.setStatus(item.getStatus());
        accountBO.setRoles(roleBOList);
        accountBO.setCreate_time(item.getCreate_time());
        accountBO.setDepartment(item.getDepartment());
        accountBO.setPosition(item.getPosition());

        return accountBO;
    }

    @Override
    public String register(AccountRegisterRequest param) throws Exception {
        String userId = RandomUtil.uuid();

        if (StringUtils.isNotEmpty(userId)){
            InvestnewUserModel model = new InvestnewUserModel();
            model.setUser_id(userId);
            model.setUsername(param.getNickname());
            String encryptPassword = RsaUtil.encryptData(param.getPassword());
            model.setPassword(encryptPassword);
            model.setEmail(param.getEmail());
            model.setMobile(param.getMobile());
            model.setNickname(param.getNickname());
            investnewUserDao.insertSelective(model);
        }

        return userId;
    }

    @Override
    public String addUser(AccountAddRequest param) throws Exception {
        InvestnewUserModel currentUser = userModelByUserId(param.getUserId());
        if (currentUser == null){
            throw new Exception("找不到当前用户信息");
        }
        CompanyBO companyBO = dataService.findCompany(currentUser.getCompany_id(),currentUser.getCompany_type());
        if (companyBO == null){
            throw new Exception("找不到当前用户公司信息");
        }
        String userId = RandomUtil.uuid();

        if (StringUtils.isNotEmpty(userId)){
            InvestnewUserModel model = new InvestnewUserModel();
            model.setUser_id(userId);
            model.setCompany_id(currentUser.getCompany_id());
            model.setCompany_type(currentUser.getCompany_type());
            model.setUsername(param.getNickname());
            String encryptPassword = RsaUtil.encryptData(param.getPassword());
            model.setPassword(encryptPassword);
            model.setEmail(param.getEmail());
            model.setMobile(param.getMobile());
            model.setNickname(param.getNickname());
            model.setStatus(param.getStatus());
            investnewUserDao.insertSelective(model);

            if (StringUtils.isNotEmpty(param.getRole_ids())){
                accessService.grantRole(userId,param.getRole_ids());
            }

        }

        return userId;
    }

    @Override
    public Boolean modifyUser(AccountModifyRequest param) throws Exception {
        InvestnewUserModel investnewUserModel = userModelByUserId(param.getUserId());
        if (investnewUserModel == null){
            throw new Exception(AccountEnumCodeConfig.USER_NO_EXISTS.getMessage());
        }

        InvestnewUserModel model = new InvestnewUserModel();
        model.setId(investnewUserModel.getId());
        model.setNickname(param.getNickname());
        model.setStatus(param.getStatus());

        if (StringUtils.isNotEmpty(param.getPassword())){
            String encryptPassword = RsaUtil.encryptData(param.getPassword());
            model.setPassword(encryptPassword);
        }

        Boolean ret = investnewUserDao.updateByPrimaryKeySelective(model) > 0;

        accessService.grantRole(investnewUserModel.getUser_id(),param.getRole_ids());

        return ret;
    }

    @Override
    public InvestnewUserModel userModelByUserId(String userId) {
        return investnewUserDao.selectByUserId(userId);
    }

    @Override
    public AccountListResponse companyUserList(Long companyId, String keyword, Integer page, Integer limit) {
        AccountListResponse accountListResponse = new AccountListResponse();

        Integer offset = Math.max((page - 1) * limit,0);
        Integer total = investnewUserDao.getCount(companyId,null,keyword);
        accountListResponse.setTotal(total);
        if (total.equals(0)){
            return accountListResponse;
        }

        List<AccountBO> accountBOList = new ArrayList<>();
        List<InvestnewUserModel> investnewUserModelList = investnewUserDao.getByPage(companyId,null,keyword,offset,limit);
        List<String> userIds = new ArrayList<>();
        investnewUserModelList.forEach((item)->{
            userIds.add(item.getUser_id());
        });
        Map<String, List<RoleBO>> roleBOMap = accessService.getUsersRole(userIds);

        for (InvestnewUserModel item : investnewUserModelList){
            AccountBO accountBO = new AccountBO();
//            accountBO.setId(item.getId());
            accountBO.setUser_id(item.getUser_id());
            accountBO.setUsername(item.getUsername());
            accountBO.setEmail(item.getEmail());
            accountBO.setMobile(item.getMobile());
            accountBO.setNickname(item.getNickname());
            accountBO.setAvatar(item.getAvatar());
            accountBO.setStatus(item.getStatus());
            accountBO.setRoles(roleBOMap.get(item.getUser_id()));
            accountBO.setCreate_time(item.getCreate_time());
            accountBO.setDepartment(item.getDepartment());
            accountBO.setPosition(item.getPosition());
            accountBOList.add(accountBO);
        }

        accountListResponse.setList(accountBOList);
        return accountListResponse;
    }

    @Override
    public List<AccountBO> companyUserList(Long companyId, String companyType) {
        List<AccountBO> accountBOList = new ArrayList<>();
        List<InvestnewUserModel> investnewUserModelList = investnewUserDao.getByPage(companyId,companyType.toLowerCase(),null,0,10000);
        List<String> userIds = new ArrayList<>();
        investnewUserModelList.forEach((item)->{
            userIds.add(item.getUser_id());
        });
        Map<String, List<RoleBO>> roleBOMap = accessService.getUsersRole(userIds);

        for (InvestnewUserModel item : investnewUserModelList){
            AccountBO accountBO = new AccountBO();
//            accountBO.setId(item.getId());
            accountBO.setUser_id(item.getUser_id());
            accountBO.setUsername(item.getUsername());
            accountBO.setEmail(item.getEmail());
            accountBO.setMobile(item.getMobile());
            accountBO.setNickname(item.getNickname());
            accountBO.setAvatar(item.getAvatar());
            accountBO.setStatus(item.getStatus());
            accountBO.setRoles(roleBOMap.get(item.getUser_id()));
            accountBO.setCreate_time(item.getCreate_time());
            accountBO.setDepartment(item.getDepartment());
            accountBO.setPosition(item.getPosition());
            accountBOList.add(accountBO);
        }

        return accountBOList;
    }

    @Override
    public AccountBO userInfoByUserId(String userId) throws Exception {
        InvestnewUserModel item = investnewUserDao.selectByUserId(userId);
        if (item == null){
            return null;
        }

        CompanyBO companyBO = dataService.findCompany(item.getCompany_id(),item.getCompany_type());
        List<RoleBO> roleBOList = accessService.getUserRole(item.getUser_id());

        AccountBO accountBO = new AccountBO();
//        accountBO.setId(item.getId());
        accountBO.setUser_id(item.getUser_id());
        accountBO.setCompany(companyBO);
        accountBO.setUsername(item.getUsername());
        accountBO.setEmail(item.getEmail());
        accountBO.setMobile(item.getMobile());
        accountBO.setNickname(item.getNickname());
        accountBO.setAvatar(item.getAvatar());
        accountBO.setStatus(item.getStatus());
        accountBO.setRoles(roleBOList);
        accountBO.setCreate_time(item.getCreate_time());
        accountBO.setDepartment(item.getDepartment());
        accountBO.setPosition(item.getPosition());

        return accountBO;
    }

    @Override
    public Map<String, AccountBO> userInfoByUserIds(List<String> userIds) {
        Map<String, AccountBO> accountBOMap = new HashMap<>();

        List<InvestnewUserModel> investnewUserModelList = investnewUserDao.selectByUserIds(userIds);
        if (investnewUserModelList.isEmpty()){
            return accountBOMap;
        }

        for (InvestnewUserModel item : investnewUserModelList){
            AccountBO accountBO = new AccountBO();
//            accountBO.setId(item.getId());
            accountBO.setUser_id(item.getUser_id());
//            accountBO.setCompany(companyBO);
            accountBO.setUsername(item.getUsername());
            accountBO.setEmail(item.getEmail());
            accountBO.setMobile(item.getMobile());
            accountBO.setNickname(item.getNickname());
            accountBO.setAvatar(item.getAvatar());
            accountBO.setStatus(item.getStatus());
//            accountBO.setRoles(roleBOList);
            accountBO.setCreate_time(item.getCreate_time());
            accountBO.setDepartment(item.getDepartment());
            accountBO.setPosition(item.getPosition());
            accountBOMap.put(item.getUser_id(),accountBO);
        }
        return accountBOMap;
    }

    @Override
    public Long getUserCompanyId(String userId) {
        InvestnewUserModel investnewUserModel = userModelByUserId(userId);
        if (investnewUserModel == null){
            return null;
        }
        if (investnewUserModel.getCompany_id() == null || investnewUserModel.getCompany_id().equals(0)){
            return null;
        }
        return investnewUserModel.getCompany_id();
    }

    @Override
    public List<CompanyBO> getFundUserCompanyList() {
        List<CompanyBO> companyBOList = new ArrayList<>();
        List<Long> companyIds = investnewUserDao.getFundCompanyIds();
        companyIds.forEach((item)->{
            CompanyBO companyBO = dataService.findCompany(item, CompanyTypeEnum.FUND.name().toLowerCase());
            if (companyBO != null){
                companyBOList.add(companyBO);
            }
        });
        return companyBOList;
    }

    @Override
    public List<CompanyBO> getBrokerUserCompanyList() {
        List<CompanyBO> companyBOList = new ArrayList<>();
        List<Long> companyIds = investnewUserDao.getFundCompanyIds();
        companyIds.forEach((item)->{
            CompanyBO companyBO = dataService.findCompany(item, CompanyTypeEnum.BROKER.name().toLowerCase());
            if (companyBO != null){
                companyBOList.add(companyBO);
            }
        });
        return companyBOList;
    }
}
