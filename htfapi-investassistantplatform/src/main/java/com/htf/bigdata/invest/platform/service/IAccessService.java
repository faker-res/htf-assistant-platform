package com.htf.bigdata.invest.platform.service;

import com.htf.bigdata.invest.platform.model.request.access.AccessAddRoleRequest;
import com.htf.bigdata.invest.platform.model.request.access.AccessModifyRoleRequest;
import com.htf.bigdata.invest.platform.model.bo.account.AccessForChooseBO;
import com.htf.bigdata.invest.platform.model.bo.account.AccessTreeBO;
import com.htf.bigdata.invest.platform.model.bo.account.RoleBO;

import java.util.List;
import java.util.Map;

/**
 * 权限控制
 */
public interface IAccessService {

    /**
     * 初始化权限
     * @return
     */
    Integer init(String companyName, String managerUserId, List<AccessTreeBO> authTree) throws Exception;

    /**
     * 获取个人的权限id列表
     * @param userId
     * @return
     */
    List<String> accessIdsByUserId(String userId);

    /**
     * 获取个人的菜单权限树
     * @param userId
     * @return
     */
    List<AccessTreeBO> accessTreeByUserId(String userId);

    /**
     * 获取公司的菜单权限树
     * @param companyId
     * @return
     */
    List<AccessTreeBO> accessTreeByCompanyId(Long companyId);

    /**
     * 获取公司权限供角色关联的列表
     * @param userId
     * @return
     */
    List<AccessForChooseBO> accessListForChoose(String userId, Integer roleId) throws Exception;

    /**
     * 添加角色
     * @param param
     * @return
     */
    Integer addRole(AccessAddRoleRequest param) throws Exception;

    /**
     * 修改角色
     * @param param
     * @return
     */
    Boolean modifyRole(AccessModifyRoleRequest param) throws Exception;

    /**
     * 删除角色
     * @param userId
     * @param roleId
     * @return
     */
    Boolean delRole(String userId, Integer roleId) throws Exception;

    /**
     * 获取个人的角色列表
     * @param userId
     * @return
     */
    List<RoleBO> getUserRole(String userId);

    /**
     * 获取公司的角色列表
     * @param companyId
     * @return
     */
    List<RoleBO> getCompanyRole(Long companyId, Boolean includeDisable);

    /**
     * 获取多个个人的角色列表
     * @param userIds
     * @return
     */
    Map<String,List<RoleBO>> getUsersRole(List<String> userIds);

    /**
     * 给角色赋权
     * @param roleId
     * @param accessIds
     * @return
     */
    Integer grantAccess(Integer roleId, String accessIds) throws Exception;
    Integer grantAccess(Integer roleId, List<String> accessIds) throws Exception;

    /**
     * 给用户赋角色
     * @param userId
     * @param roleIds
     * @return
     */
    Boolean grantRole(String userId, String roleIds);
    Boolean grantRole(String userId, List<Integer> roleIds);

    /**
     * 给用户添加一个角色
     * @param userId
     * @param roleId
     * @return
     */
    Boolean grantAddRole(String userId, Integer roleId);

    /**
     * 禁用角色
     * @param roleId
     * @return
     */
    Boolean changeStatus(Integer roleId, String status) throws Exception;
}
