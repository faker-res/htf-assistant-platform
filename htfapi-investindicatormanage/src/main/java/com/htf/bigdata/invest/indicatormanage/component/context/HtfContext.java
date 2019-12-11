package com.htf.bigdata.invest.indicatormanage.component.context;

import lombok.Data;

/**
 * @author xuyali
 * @date 2019/6/13 13:58
 */
@Data
public class HtfContext extends AbstactContext{
    public final static ThreadLocal<HtfContext> htfContext = new ThreadLocal<HtfContext>();
}
