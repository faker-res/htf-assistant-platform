package com.htf.bigdata.invest.platform.config.enums;

import java.util.HashMap;
import java.util.Map;

public class NumberUnitEnum {
     public static String getNumUnit(Long num) {
         return NUM_UNIT.get(num);
     }

     public static final Map<Long, String> NUM_UNIT = new HashMap<Long, String>() {
         {
             put(1000000000000000l, "千万亿");
             put(100000000000000l, "百万亿");
             put(10000000000000l, "十万亿");
             put(1000000000000l, "万亿");
             put(100000000000l, "千亿");
             put(10000000000l, "百亿");
             put(1000000000l, "十亿");
             put(100000000l, "亿");
             put(10000000l, "千万");
             put(1000000l, "百万");
             put(100000l, "十万");
             put(10000l, "万");
             put(1000l, "千");
             put(100l, "百");
             put(10l, "十");
             put(1l, "");
         }
     };
}
