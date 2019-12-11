package com.htf.bigdata.invest.platform.service.impl;

import com.htf.bigdata.invest.platform.dao.invest.*;
import com.htf.bigdata.invest.platform.model.invest.*;
import com.htf.bigdata.invest.platform.config.AccessConfig;
import com.htf.bigdata.invest.platform.config.code.AccountEnumCodeConfig;
import com.htf.bigdata.invest.platform.config.enums.StatusEnum;
import com.htf.bigdata.invest.platform.dao.invest.*;
import com.htf.bigdata.invest.platform.model.bo.account.AccessForChooseBO;
import com.htf.bigdata.invest.platform.model.bo.account.AccessTreeBO;
import com.htf.bigdata.invest.platform.model.bo.account.RoleBO;
import com.htf.bigdata.invest.platform.model.bo.data.CompanyBO;
import com.htf.bigdata.invest.platform.model.invest.*;
import com.htf.bigdata.invest.platform.model.request.access.AccessAddRoleRequest;
import com.htf.bigdata.invest.platform.model.request.access.AccessModifyRoleRequest;
import com.htf.bigdata.invest.platform.service.IAccessService;
import com.htf.bigdata.invest.platform.service.IAccountService;
import com.htf.bigdata.invest.platform.service.IDataService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccessServiceImpl implements IAccessService {

    private final static Logger logger = LogManager.getLogger(AccessServiceImpl.class);

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IDataService dataService;

    @Autowired
    private IInvestnewAccessDao investnewAccessDao;

    @Autowired
    private IInvestnewRoleDao investnewRoleDao;

    @Autowired
    private IInvestnewRoleAccessDao investnewRoleAccessDao;

    @Autowired
    private IInvestnewUserDao investnewUserDao;

    @Autowired
    private IInvestnewUserRoleDao investnewUserRoleDao;

    @Override
    public Integer init(String companyName, String managerUserId, List<AccessTreeBO> authTree) throws Exception {
        InvestnewUserModel investnewUserModel = accountService.userModelByUserId(managerUserId);
        if (investnewUserModel == null){
            throw new Exception(AccountEnumCodeConfig.USER_NO_EXISTS.getMessage());
        }
        CompanyBO companyBO = dataService.findCompany(companyName);
        if (companyBO == null){
            throw new Exception(AccountEnumCodeConfig.CANNOT_FIND_COMPANY.getMessage());
        }
        Long companyId = companyBO.getCompany_id();
        if (!companyId.equals(investnewUserModel.getCompany_id())){
            investnewUserModel.setCompany_id(companyId);
            investnewUserModel.setCompany_type(companyBO.getType().name().toLowerCase());
            investnewUserDao.updateByPrimaryKeySelective(investnewUserModel);
        }

        investnewAccessDao.deleteByCompanyId(companyId);

        List<InvestnewAccessModel> investnewAccessModelList = new ArrayList<>();
        investnewAccessModelList.addAll(getAccessList(companyId, AccessConfig.ACCESS_DEFAULT_PARENT_ID,authTree));
        int success = investnewAccessDao.insertMulti(investnewAccessModelList);
        if (success <= 0){
            throw new Exception("添加权限失败");
        }

        InvestnewRoleModel investnewRoleModel = investnewRoleDao.selectByName(companyId,AccessConfig.MANAGER_ROLE_NAME);
        if (investnewRoleModel == null){
            investnewRoleModel = new InvestnewRoleModel();
            investnewRoleModel.setCompany_id(companyId);
            investnewRoleModel.setName(AccessConfig.MANAGER_ROLE_NAME);
            investnewRoleModel.setDescription(AccessConfig.MANAGER_ROLE_NAME);
            investnewRoleDao.insertSelectiveSelectId(investnewRoleModel);
        }
        Integer roleId = investnewRoleModel.getRole_id();

        if (roleId != null){
            List<InvestnewRoleAccessKey> investnewRoleAccessKeyList = new ArrayList<>();
            for (InvestnewAccessModel item : investnewAccessModelList){
                InvestnewRoleAccessKey model = new InvestnewRoleAccessKey();
                model.setRole_id(roleId);
                model.setAccess_id(item.getAccess_id());
                investnewRoleAccessKeyList.add(model);
            }
            investnewRoleAccessDao.insertMulti(investnewRoleAccessKeyList);

            grantAddRole(managerUserId,roleId);
        }

        return success;
    }

    private List<InvestnewAccessModel> getAccessList(Long companyId, String parentId, List<AccessTreeBO> childTree) {
        List<InvestnewAccessModel> childList = new ArrayList<>();
        for (AccessTreeBO item : childTree){
            InvestnewAccessModel model = new InvestnewAccessModel();
            model.setCompany_id(companyId);
            model.setAccess_id(item.getId());
            model.setParent_id(parentId);
            model.setName(item.getName());
            model.setDescription(item.getName());
            childList.add(model);

            if (!ObjectUtils.isEmpty(item.getChild())){
                childList.addAll(getAccessList(companyId,item.getId(),item.getChild()));
            }
        }
        return childList;
    }

    @Override
    public List<String> accessIdsByUserId(String userId) {
        List<String> accessIds = new ArrayList<>();

        Long companyId = accountService.getUserCompanyId(userId);
        if (companyId == null){
            return accessIds;
        }

        List<RoleBO> roleBOList = getUserRole(userId);
        if (ObjectUtils.isEmpty(roleBOList)){
            return accessIds;
        }

        List<Integer> roleIds = new ArrayList<>();
        for (RoleBO item : roleBOList){
            if (item.getStatus().equals(StatusEnum.DISABLE.getStatus())){
                continue;
            }
            roleIds.add(item.getRole_id());
        }
        List<InvestnewRoleAccessKey> investnewRoleAccessKeyList = investnewRoleAccessDao.selectByRoleIds(roleIds);
        for (InvestnewRoleAccessKey item : investnewRoleAccessKeyList){
            accessIds.add(item.getAccess_id());
        }
        List<InvestnewAccessModel> investnewAccessModelList = investnewAccessDao.selectByAccessIds(companyId,accessIds);
        accessIds.clear();
        investnewAccessModelList.forEach((item)->{
            accessIds.add(item.getAccess_id());
        });
        return accessIds;
    }

    @Override
    public List<AccessTreeBO> accessTreeByUserId(String userId) {
        List<AccessTreeBO> accessTree = new ArrayList<>();

        Long companyId = accountService.getUserCompanyId(userId);
        if (companyId == null){
            return accessTree;
        }

        List<String> accessIds = accessIdsByUserId(userId);
        if (ObjectUtils.isEmpty(accessIds)){
            return accessTree;
        }
        List<InvestnewAccessModel> investnewAccessModelList = investnewAccessDao.selectByAccessIds(companyId,accessIds);

        Map<String,List<InvestnewAccessModel>> investnewAccessModelParentMap = new HashMap<>();
        Boolean hasTopAccess = false;
        for (InvestnewAccessModel item : investnewAccessModelList){
            List<InvestnewAccessModel> childList = investnewAccessModelParentMap.getOrDefault(item.getParent_id(),new ArrayList<>());
            childList.add(item);
            investnewAccessModelParentMap.put(item.getParent_id(),childList);

            if (AccessConfig.ACCESS_DEFAULT_PARENT_ID.equals(item.getParent_id())){
                hasTopAccess = true;
            }
        }
        if (!hasTopAccess){
            return accessTree;
        }

        accessTree.addAll(getChildTree(AccessConfig.ACCESS_DEFAULT_PARENT_ID,"",investnewAccessModelParentMap));
        return accessTree;
    }

    @Override
    public List<AccessTreeBO> accessTreeByCompanyId(Long companyId) {
        List<AccessTreeBO> accessTree = new ArrayList<>();
        Map<String,List<InvestnewAccessModel>> investnewAccessModelParentMap = new HashMap<>();
        List<InvestnewAccessModel> investnewAccessModelList = investnewAccessDao.selectByCompanyId(companyId);
        for (InvestnewAccessModel item : investnewAccessModelList){
            List<InvestnewAccessModel> childList = investnewAccessModelParentMap.getOrDefault(item.getParent_id(),new ArrayList<>());
            childList.add(item);
            investnewAccessModelParentMap.put(item.getParent_id(),childList);
        }

        accessTree.addAll(getChildTree(AccessConfig.ACCESS_DEFAULT_PARENT_ID,"",investnewAccessModelParentMap));
        return accessTree;
    }

    private List<AccessTreeBO> getChildTree(String parentId, String preName, Map<String,List<InvestnewAccessModel>> investnewAccessModelParentMap){
        if (!investnewAccessModelParentMap.containsKey(parentId)){
            return null;
        }
        List<AccessTreeBO> result = new ArrayList<>();
        List<InvestnewAccessModel> childList = investnewAccessModelParentMap.get(parentId);
        for (InvestnewAccessModel item : childList){
            AccessTreeBO bo = new AccessTreeBO();
            bo.setId(item.getAccess_id());
            bo.setName(item.getName());
            String description = preName+item.getName();
            bo.setDescription(description);
            if (investnewAccessModelParentMap.containsKey(item.getAccess_id())){
                bo.setChild(getChildTree(item.getAccess_id(),description+"/",investnewAccessModelParentMap));
            }
            result.add(bo);
        }
        return result;
    }

    @Override
    public List<AccessForChooseBO> accessListForChoose(String userId, Integer roleId) throws Exception {
        List<AccessForChooseBO> accessForChooseBOList = new ArrayList<>();
        Long companyId = accountService.getUserCompanyId(userId);
        if (companyId == null){
            throw new Exception(AccountEnumCodeConfig.MISS_COMPANY.getMessage());
        }

        List<AccessTreeBO> accessList = new ArrayList<>();
        List<AccessTreeBO> accessTree = accessTreeByCompanyId(companyId);
        accessList.addAll(getChildList(accessTree));

        List<String> accessIds = new ArrayList<>();
        if (roleId != null){
            accessIds = investnewRoleAccessDao.getAccessIdsByRoleId(roleId);
        }
        for (AccessTreeBO item : accessList){
            AccessForChooseBO bo = new AccessForChooseBO();
            bo.setAccess_id(item.getId());
            bo.setName(item.getName());
            bo.setDescription(item.getDescription());
            bo.setChosen(accessIds.contains(item.getId()));
            accessForChooseBOList.add(bo);
        }

        return accessForChooseBOList;
    }

    private List<AccessTreeBO> getChildList(List<AccessTreeBO> accessTreeBOList){
        List<AccessTreeBO> accessList = new ArrayList<>();
        for (AccessTreeBO item : accessTreeBOList){
            List<AccessTreeBO> child = item.getChild();
            item.setChild(null);
            accessList.add(item);

            if (!ObjectUtils.isEmpty(child)){
                accessList.addAll(getChildList(child));
            }
        }
        return accessList;
    }

    @Override
    public Integer addRole(AccessAddRoleRequest param) throws Exception {
        Long companyId = accountService.getUserCompanyId(param.getUserId());
        if (companyId == null){
            throw new Exception(AccountEnumCodeConfig.MISS_COMPANY.getMessage());
        }
        if (AccessConfig.MANAGER_ROLE_NAME.equals(param.getName())){
            throw new Exception(AccountEnumCodeConfig.MANAGER_NOTALLOW_CHANGE.getMessage());
        }
        InvestnewRoleModel investnewRoleModel = new InvestnewRoleModel();
        investnewRoleModel.setCompany_id(companyId);
        investnewRoleModel.setName(param.getName());
        investnewRoleModel.setDescription(param.getDescription());
        investnewRoleModel.setStatus(param.getStatus());
        investnewRoleDao.insertSelectiveSelectId(investnewRoleModel);
        Integer roleId = investnewRoleModel.getRole_id();

        if (roleId != null && StringUtils.isNotEmpty(param.getAccess_ids())){
            grantAccess(roleId,param.getAccess_ids());
        }
        return roleId;
    }

    @Override
    public Boolean modifyRole(AccessModifyRoleRequest param) throws Exception {
        InvestnewRoleModel investnewRoleModel = investnewRoleDao.selectByPrimaryKey(param.getRole_id());
        if (investnewRoleModel == null){
            throw new Exception(AccountEnumCodeConfig.USER_NO_EXISTS.getMessage());
        }
        if (AccessConfig.MANAGER_ROLE_NAME.equals(investnewRoleModel.getName())){
            throw new Exception(AccountEnumCodeConfig.MANAGER_NOTALLOW_MODIFY.getMessage());
        }
        if (AccessConfig.MANAGER_ROLE_NAME.equals(param.getName())){
            throw new Exception(AccountEnumCodeConfig.MANAGER_NOTALLOW_CHANGE.getMessage());
        }

        InvestnewRoleModel model = new InvestnewRoleModel();
        model.setRole_id(investnewRoleModel.getRole_id());
        model.setName(param.getName());
        model.setDescription(param.getDescription());
        model.setStatus(param.getStatus());

        Boolean ret = investnewRoleDao.updateByPrimaryKeySelective(model) > 0;

        grantAccess(investnewRoleModel.getRole_id(),param.getAccess_ids());

        return ret;
    }

    @Override
    public Boolean delRole(String userId, Integer roleId) throws Exception {
        Long companyId = accountService.getUserCompanyId(userId);
        if (companyId == null){
            throw new Exception(AccountEnumCodeConfig.MISS_COMPANY.getMessage());
        }
        InvestnewRoleModel investnewRoleModel = investnewRoleDao.selectByPrimaryKey(roleId);
        if (investnewRoleModel == null){
            throw new Exception(AccountEnumCodeConfig.ROLE_NO_EXISTS.getMessage());
        }
        if (!companyId.equals(investnewRoleModel.getCompany_id())){
            throw new Exception(AccountEnumCodeConfig.ROLE_NOTALLOW_MODIFY.getMessage());
        }
        if (AccessConfig.MANAGER_ROLE_NAME.equals(investnewRoleModel.getName())){
            throw new Exception(AccountEnumCodeConfig.MANAGER_NOTALLOW_MODIFY.getMessage());
        }
        Boolean delRet = investnewRoleDao.deleteByPrimaryKey(roleId) > 0;
        if (delRet){
            investnewUserRoleDao.deleteByRoleId(roleId);
        }
        return delRet;
    }

    @Override
    public List<RoleBO> getUserRole(String userId) {
        List<RoleBO> list = new ArrayList<>();

        List<Integer> roleIds = investnewUserRoleDao.getRoleIdsByUserId(userId);
        if (ObjectUtils.isEmpty(roleIds)){
            return list;
        }
        List<InvestnewRoleModel> investnewRoleModelList = investnewRoleDao.selectByRoleIds(roleIds);
        for (InvestnewRoleModel item : investnewRoleModelList){
            RoleBO roleBO = new RoleBO();
            roleBO.setRole_id(item.getRole_id());
            roleBO.setName(item.getName());
            roleBO.setStatus(item.getStatus());
            list.add(roleBO);
        }
        return list;
    }

    @Override
    public List<RoleBO> getCompanyRole(Long companyId, Boolean includeDisable) {
        List<RoleBO> list = new ArrayList<>();

        List<InvestnewRoleModel> investnewRoleModelList = investnewRoleDao.selectByCompanyId(companyId);
        for (InvestnewRoleModel item : investnewRoleModelList){
//            if (AccessConfig.MANAGER_ROLE_NAME.equals(item.getName())){
//                continue;
//            }
            if (!includeDisable && item.getStatus().equals(StatusEnum.DISABLE.getStatus())){
                continue;
            }

            RoleBO roleBO = new RoleBO();
            roleBO.setRole_id(item.getRole_id());
            roleBO.setName(item.getName());
            roleBO.setStatus(item.getStatus());
            list.add(roleBO);
        }
        return list;
    }

    @Override
    public Map<String, List<RoleBO>> getUsersRole(List<String> userIds) {
        Map<String, List<RoleBO>> roleBOMap = new HashMap<>();

        List<InvestnewUserRoleKey> investnewUserRoleKeyList = investnewUserRoleDao.selectByUserIds(userIds);
        if (investnewUserRoleKeyList.isEmpty()){
            return roleBOMap;
        }

        List<Integer> roleIds = new ArrayList<>();
        for (InvestnewUserRoleKey item : investnewUserRoleKeyList){
            roleIds.add(item.getRole_id());
        }
        List<InvestnewRoleModel> investnewRoleModelList = investnewRoleDao.selectByRoleIds(roleIds);
        if (investnewRoleModelList.isEmpty()){
            return roleBOMap;
        }
        Map<Integer,InvestnewRoleModel> roles = new HashMap<>();
        investnewRoleModelList.forEach((item)->{
            roles.put(item.getRole_id(),item);
        });

        for (InvestnewUserRoleKey item : investnewUserRoleKeyList){
            InvestnewRoleModel role = roles.get(item.getRole_id());
            if (role == null){
                continue;
            }

            RoleBO bo = new RoleBO();
            bo.setRole_id(item.getRole_id());
            bo.setName(role.getName());
            bo.setStatus(role.getStatus());

            List<RoleBO> roleBOList= roleBOMap.getOrDefault(item.getUser_id(),new ArrayList<>());
            roleBOList.add(bo);
            roleBOMap.put(item.getUser_id(),roleBOList);
        }
        return roleBOMap;
    }

    @Override
    public Integer grantAccess(Integer roleId, String accessIds) throws Exception {
        List<String> accessIdsList = new ArrayList<>();
        if (StringUtils.isNotEmpty(accessIds)){
            String[] accessIdsSplit = accessIds.split(",");
            for (String item : accessIdsSplit){
                accessIdsList.add(item);
            }
        }
        return grantAccess(roleId,accessIdsList);
    }
    @Override
    public Integer grantAccess(Integer roleId, List<String> accessIds) throws Exception {
        InvestnewRoleModel investnewRoleModel = investnewRoleDao.selectByPrimaryKey(roleId);
        if (investnewRoleModel == null){
            throw new Exception(AccountEnumCodeConfig.ROLE_NO_EXISTS.getMessage());
        }
        if (AccessConfig.MANAGER_ROLE_NAME.equals(investnewRoleModel.getName())){
            throw new Exception(AccountEnumCodeConfig.MANAGER_NOTALLOW_MODIFY.getMessage());
        }

        investnewRoleAccessDao.deleteByRoleId(roleId);
        if (accessIds.isEmpty()){
            return 0;
        }

        List<InvestnewRoleAccessKey> investnewRoleAccessKeyList = new ArrayList<>();
        for (String item : accessIds){
            InvestnewRoleAccessKey model = new InvestnewRoleAccessKey();
            model.setRole_id(roleId);
            model.setAccess_id(item);
            investnewRoleAccessKeyList.add(model);
        }
        return investnewRoleAccessDao.insertMulti(investnewRoleAccessKeyList);
    }

    @Override
    public Boolean grantRole(String userId, String roleIds) {
        List<Integer> roleIdsList = new ArrayList<>();
        if (StringUtils.isNotEmpty(roleIds)){
            String[] roleIdsSplit = roleIds.split(",");
            for (String item : roleIdsSplit){
                roleIdsList.add(Integer.valueOf(item));
            }
        }
        return grantRole(userId,roleIdsList);
    }
    @Override
    public Boolean grantRole(String userId, List<Integer> roleIds) {
        investnewUserRoleDao.deleteByUserId(userId);
        if (roleIds.isEmpty()){
            return true;
        }

        List<InvestnewUserRoleKey> investnewUserRoleKeyList = new ArrayList<>();
        for (Integer item : roleIds){
            InvestnewUserRoleKey model = new InvestnewUserRoleKey();
            model.setUser_id(userId);
            model.setRole_id(item);
            investnewUserRoleKeyList.add(model);
        }
        return investnewUserRoleDao.insertMulti(investnewUserRoleKeyList) > 0;
    }

    @Override
    public Boolean grantAddRole(String userId, Integer roleId) {
        InvestnewUserRoleKey model = new InvestnewUserRoleKey();
        model.setUser_id(userId);
        model.setRole_id(roleId);
        return investnewUserRoleDao.insertIgnore(model) > 0;
    }

    @Override
    public Boolean changeStatus(Integer roleId,String status) throws Exception {
        InvestnewRoleModel investnewRoleModel = investnewRoleDao.selectByPrimaryKey(roleId);
        if (investnewRoleModel == null){
            throw new Exception(AccountEnumCodeConfig.ROLE_NO_EXISTS.getMessage());
        }
        if (AccessConfig.MANAGER_ROLE_NAME.equals(investnewRoleModel.getName())){
            throw new Exception(AccountEnumCodeConfig.MANAGER_NOTALLOW_MODIFY.getMessage());
        }
        if (!status.equals(StatusEnum.ENABLE.getStatus()) && !status.equals(StatusEnum.DISABLE.getStatus())){
            throw new Exception(AccountEnumCodeConfig.STATUS_WRONG.getMessage());
        }
        investnewRoleModel.setStatus(status);
        return investnewRoleDao.updateByPrimaryKey(investnewRoleModel) > 0;
    }
}
