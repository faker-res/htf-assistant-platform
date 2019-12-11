package com.htf.bigdata.invest.platform.controller;

import com.htf.bigdata.invest.platform.model.bo.account.AccessForChooseBO;
import com.htf.bigdata.invest.platform.model.bo.account.AccessTreeBO;
import com.htf.bigdata.invest.platform.model.bo.account.RoleBO;
import com.htf.bigdata.invest.platform.model.request.access.AccessAddRoleRequest;
import com.htf.bigdata.invest.platform.model.request.access.AccessInitRequest;
import com.htf.bigdata.invest.platform.model.request.access.AccessModifyRoleRequest;
import com.htf.bigdata.invest.platform.model.response.ErrorResponse;
import com.htf.bigdata.invest.platform.model.response.Response;
import com.htf.bigdata.invest.platform.service.IAccessService;
import com.htf.bigdata.invest.platform.service.IAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限
 */
@RestController
@RequestMapping(path = "/access")
public class AccessController {

    private final static Logger logger = LogManager.getLogger(AccessController.class);

    @Autowired
    private IAccessService accessService;

    @Autowired
    private IAccountService accountService;

    /**
     * 初始化公司权限
     * @return
     */
    @PostMapping(path = "/init")
    Response init(@RequestBody AccessInitRequest param) {
        try{
            Integer ret = accessService.init(param.getCompany_name(), param.getManager_user_id(), param.getAuth_tree());
            return new Response(ret);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new ErrorResponse(e.getMessage());
        }
    }

    /**
     * 供角色关联的公司权限列表
     * @param userId
     * @return
     */
    @GetMapping(path = "/forchoose")
    Response forChoose(@RequestParam("userId") String userId, @RequestParam(value = "role_id",required = false) Integer roleId) {
        try{
            List<AccessForChooseBO> ret = accessService.accessListForChoose(userId,roleId);
            return new Response(ret);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new ErrorResponse(e.getMessage());
        }
    }

    /**
     * 用户权限菜单树
     * @param userId
     * @return
     */
    @GetMapping(path = "/user/tree")
    Response userAccessTree(@RequestParam("userId") String userId) {
        List<AccessTreeBO> ret = accessService.accessTreeByUserId(userId);
        return new Response(ret);
    }

    /**
     * 用户权限id列表
     * @param userId
     * @return
     */
    @GetMapping(path = "/user/ids")
    Response userAccessIds(@RequestParam("userId") String userId) {
        List<String> ret = accessService.accessIdsByUserId(userId);
        return new Response(ret);
    }

    /**
     * 添加角色
     * @param param
     * @return
     */
    @PostMapping(path = "/addrole")
    Response addRole(@RequestBody AccessAddRoleRequest param) {
        try{
            Integer ret = accessService.addRole(param);
            return new Response(ret);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new ErrorResponse(e.getMessage());
        }
    }

    /**
     * 修改角色
     * @param param
     * @return
     */
    @PostMapping(path = "/modifyrole")
    Response modifyRole(@RequestBody AccessModifyRoleRequest param) {
        if (param.getRole_id() == null){
            return new ErrorResponse("角色id是空");
        }

        try{
            Boolean ret = accessService.modifyRole(param);
            return new Response(ret);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new ErrorResponse(e.getMessage());
        }
    }

    /**
     * 删除角色
     * @param userId
     * @param roleId
     * @return
     */
    @GetMapping(path = "/delrole")
    Response delRole(@RequestParam("userId") String userId, @RequestParam("role_id") Integer roleId) {
        try{
            Boolean ret = accessService.delRole(userId,roleId);
            return new Response(ret);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new ErrorResponse(e.getMessage());
        }
    }

    /**
     * 获取用户个人的角色列表
     * @param userId
     * @return
     */
    @GetMapping(path = "/userrole")
    Response userRole(@RequestParam("userId") String userId) {
        List<RoleBO> ret = accessService.getUserRole(userId);
        return new Response(ret);
    }

    /**
     * 获取公司的角色列表
     * @param userId
     * @return
     */
    @GetMapping(path = "/companyrole")
    Response companyRole(@RequestParam("userId") String userId, @RequestParam(value = "include_disable",defaultValue = "1") Boolean includeDisable) {
        Long companyId = accountService.getUserCompanyId(userId);
        if (companyId == null){
            return new ErrorResponse();
        }
        List<RoleBO> ret = accessService.getCompanyRole(companyId,includeDisable);
        return new Response(ret);
    }

    /**
     * 给角色赋予权限
     * @param roleId
     * @param accessIds
     * @return
     */
    @RequestMapping(path = "/grantaccess")
    Response grantAccess(@RequestParam("role_id") Integer roleId, @RequestParam("access_ids") String accessIds) {
        try{
            Integer ret = accessService.grantAccess(roleId, accessIds);
            return new Response(ret);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new ErrorResponse(e.getMessage());
        }
    }

    /**
     * 给用户赋予角色
     * @param userId
     * @param roleIds
     * @return
     */
    @RequestMapping(path = "/grantrole")
    Response grantRole(@RequestParam("userId") String userId, @RequestParam("role_ids") String roleIds) {
        Boolean ret = accessService.grantRole(userId, roleIds);
        return new Response(ret);
    }

    /**
     * 修改角色状态
     * @param roleId
     * @return
     */
    @GetMapping(path = "/status")
    Response disable(@RequestParam("role_id") Integer roleId, @RequestParam("status") String status) {
        try{
            Boolean ret = accessService.changeStatus(roleId,status);
            return new Response(ret);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new ErrorResponse(e.getMessage());
        }
    }
}
