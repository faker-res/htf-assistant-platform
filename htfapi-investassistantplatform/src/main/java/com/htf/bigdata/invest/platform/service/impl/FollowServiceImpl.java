package com.htf.bigdata.invest.platform.service.impl;

import com.htf.bigdata.invest.platform.dao.invest.IInvestnewIndexIndicatorFollowDao;
import com.htf.bigdata.invest.platform.model.invest.InvestnewIndexIndicatorFollowModel;
import com.htf.bigdata.invest.platform.model.request.FollowAddRequest;
import com.htf.bigdata.invest.platform.model.request.FollowRemoveRequest;
import com.htf.bigdata.invest.platform.service.IFollowService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements IFollowService {

    private final static Logger logger = LogManager.getLogger(FollowServiceImpl.class);

    @Autowired
    private IInvestnewIndexIndicatorFollowDao investnewIndexIndicatorFollowDao;

    @Override
    public Boolean addFollow(FollowAddRequest request) {
        String[] objectIds = request.getObject_id().split(",");
        for (String item : objectIds){
            try{
                Long objectId = Long.valueOf(item);
                InvestnewIndexIndicatorFollowModel model = new InvestnewIndexIndicatorFollowModel();
                model.setUser_id(request.getUserId());
                model.setObject_id(objectId);
                model.setType(request.getType());
                investnewIndexIndicatorFollowDao.insertSelective(model);
            }catch (Exception e){
                logger.error(e);
                continue;
            }
        }
        return true;
    }

    @Override
    public Boolean removeFollow(FollowRemoveRequest request) {
        String[] objectIds = request.getObject_id().split(",");
        for (String item : objectIds){
            try{
                Long objectId = Long.valueOf(item);
                investnewIndexIndicatorFollowDao.deleteByObjectIdAndType(request.getUserId(),objectId,request.getType());
            }catch (Exception e){
                logger.error(e);
                continue;
            }
        }
        return true;
    }
}
