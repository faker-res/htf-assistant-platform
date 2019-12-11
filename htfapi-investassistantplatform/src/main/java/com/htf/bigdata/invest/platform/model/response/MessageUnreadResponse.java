package com.htf.bigdata.invest.platform.model.response;

import com.htf.bigdata.invest.platform.model.bo.MessageBO;
import lombok.Data;

import java.util.List;

/**
 * 未读消息
 * @author wb-wuxiao
 */
@Data
public class MessageUnreadResponse {

    private Integer totalCount;

    private List<MessageBO> list;
}
