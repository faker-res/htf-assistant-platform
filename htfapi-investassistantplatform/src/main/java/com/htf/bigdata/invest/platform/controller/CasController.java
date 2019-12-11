package com.htf.bigdata.invest.platform.controller;

import com.htf.bigdata.invest.platform.component.util.RedisUtil;
import com.htf.bigdata.invest.platform.dao.invest.IInvestnewUserDao;
import com.htf.bigdata.invest.platform.model.bo.account.TokenBO;
import com.htf.bigdata.invest.platform.model.invest.InvestnewUserModel;
import com.htf.bigdata.invest.platform.model.response.ErrorResponse;
import com.htf.bigdata.invest.platform.model.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * cas登陆
 * @author wb-wuxiao
 */
@RestController
@RequestMapping(path = "/cas")
public class CasController {

    private final static Logger logger = LogManager.getLogger(CasController.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IInvestnewUserDao investnewUserDao;

    /**
     * 设置cas用户token
     * @return
     */
	@PostMapping(path = "/set-token")
    Response setToken(@RequestParam("username") String username, @RequestParam("ticket") String ticket) {
        try{
            //根据cas的username获取用户id
            InvestnewUserModel item = investnewUserDao.selectByAccount(username);
            if (item == null){
                logger.error("user {} login failed not exists", username);
                return new ErrorResponse("user "+username+" login failed not exists");
            }
            String userId = item.getUser_id();
            String token = ticket;

            //用户信息缓存下来
            TokenBO tokenBO = new TokenBO();
            tokenBO.setUserId(userId);
            tokenBO.setToken(token);
            redisUtil.set(token,tokenBO,7200);//过期时间2小时

            return new Response((Object) userId);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new ErrorResponse(e.getMessage());
        }
	}

    /**
     * 移除token
     * @param token
     * @return
     */
    @DeleteMapping(path = "/remove-token")
    Response removeToken(@RequestParam("token") String token) {
        try{
            redisUtil.delete(token);
            return new Response(true);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new ErrorResponse(e.getMessage());
        }
    }

    /**
     * 验证cas用户token
     * @return
     */
    @GetMapping(path = "/verify-token")
    Response verifyToken(@RequestParam("userId") String userId, @RequestParam("token") String token) {
        try{
            //获取用户信息缓存
            TokenBO tokenBO = redisUtil.get(token,TokenBO.class);
            if (tokenBO == null){
                return new Response(false);
            }
            if (!userId.equals(tokenBO.getUserId())){
                return new ErrorResponse("userId not equal");
            }
            return new Response(true);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new ErrorResponse(e.getMessage());
        }
    }
}
