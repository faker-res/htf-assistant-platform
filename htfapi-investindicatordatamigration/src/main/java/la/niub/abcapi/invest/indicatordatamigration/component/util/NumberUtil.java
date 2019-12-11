package la.niub.abcapi.invest.indicatordatamigration.component.util;

import com.alibaba.fastjson.JSONObject;

import la.niub.abcapi.invest.indicatordatamigration.config.enums.NumberUnitEnum;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumberUtil {

    public static JSONObject getNumberPatition(List<BigDecimal> numberList) {
        JSONObject result = new JSONObject();
        Long firstValue = 0l;
        Long interval = 0l;
        BigDecimal max = new BigDecimal(0);
        BigDecimal min = new BigDecimal(0);
        int flag = 0;
        for (BigDecimal number : numberList) {
            BigDecimal value = number.setScale(0 , BigDecimal.ROUND_HALF_UP);

            if (flag == 0) {
                max = value;
                min = value;
                flag = 1;
            }

            if (max.compareTo(value) < 0) {
                max = value;
            }

            if (min.compareTo(value) > 0) {
                min = value;
            }
        }

        Integer maxValueLength = max.toString().length();
        Integer minValueLength = min.toString().length();
        int num = maxValueLength - minValueLength;
        Long unitNum;

        if (maxValueLength < 5) {
            unitNum = 1l;
        } else if (maxValueLength >= 5 && maxValueLength < 9) {
            unitNum = 10000l;
        } else if (maxValueLength >= 9 && maxValueLength < 13) {
            unitNum = 100000000l;
        } else if (maxValueLength >= 13 && maxValueLength < 17){
            unitNum = 1000000000000l;
        } else {
            unitNum = 10000000000000000l;
        }

        Integer firstNum = Integer.parseInt(max.toString().substring(0, 1));
        if (num == 0) {
            Integer charPos = 0;
            Integer maxIntervalLength = max.divide(new BigDecimal(unitNum)).setScale(0, BigDecimal.ROUND_HALF_UP).toString().length();
            for (int i = 0; i < maxIntervalLength; i++) {
                String charInMax = (max.divide(new BigDecimal(unitNum)).setScale(0, BigDecimal.ROUND_HALF_UP)).toString().substring(i, i + 1);
                String charInMin = (min.divide(new BigDecimal(unitNum)).setScale(0, BigDecimal.ROUND_HALF_UP)).toString().substring(i, i + 1);
                if (!charInMax.equals(charInMin)) {
                    charPos = i;
                    break;
                }
            }

            Integer firstDifNum = Integer.parseInt(max.toString().substring(charPos, charPos + 1));

            if (firstDifNum >= 5) {
                interval = (long)Math.pow(10, maxIntervalLength - charPos - 2) * 5;
            } else {
                interval = (long)Math.pow(10, maxIntervalLength - charPos - 1);
            }

            firstValue = Long.parseLong(max.toString().substring(0, charPos) + StringUtils.repeat("0", maxIntervalLength - charPos));
        } else {
            if (firstNum >= 5) {
                firstValue = 0l;
                int count = new BigDecimal(max.doubleValue() / unitNum).setScale(0, BigDecimal.ROUND_HALF_UP).toString().length();
                interval = (long)Math.pow(10, count - 1);
            } else {
                firstValue = 0l;
                int count = new BigDecimal(max.doubleValue() / unitNum).setScale(0, BigDecimal.ROUND_HALF_UP).toString().length();
                interval = (long)Math.pow(10, count - 1) / 2 < 1 ? 1 : (long)Math.pow(10, count - 1) / 2;
            }
        }

        Integer sectionCount0 = 0;
        Integer sectionCount1 = 0;
        Integer sectionCount2 = 0;
        Integer sectionCount3 = 0;
        Integer sectionCount4 = 0;
        Integer sectionCount5 = 0;
        Integer sectionCount6 = 0;
        Integer sectionCount7 = 0;
        Integer sectionCount8 = 0;
        Integer sectionCount9 = 0;
        Integer sectionCount10 = 0;

        for (BigDecimal item : numberList) {
            Double value = item.doubleValue() / unitNum;
            if (value < firstValue) {
                sectionCount0++;
            } else if (value >= firstValue && value < firstValue + interval) {
                sectionCount1++;
            } else if (value >= firstValue + interval && value < firstValue + 2 * interval) {
                sectionCount2++;
            } else if (value >= firstValue + 2 * interval && value < firstValue + 3 * interval) {
                sectionCount3++;
            } else if (value >= firstValue + 3 * interval && value < firstValue + 4 * interval) {
                sectionCount4++;
            } else if (value >= firstValue + 4 * interval && value < firstValue + 5 * interval) {
                sectionCount5++;
            } else if (value >= firstValue + 5 * interval && value < firstValue + 6 * interval) {
                sectionCount6++;
            } else if (value >= firstValue + 6 * interval && value < firstValue + 7 * interval) {
                sectionCount7 ++;
            } else if (value >= firstValue + 7 * interval && value < firstValue + 8 * interval) {
                sectionCount8++;
            } else if (value >= firstValue + 8 * interval && value < firstValue + 9 * interval) {
                sectionCount9++;
            } else {
                sectionCount10++;
            }
        }

        Map<String, Integer>  data = new HashMap<>();

        if (firstValue != 0) {
            data.put(0 + "-" + firstValue, sectionCount0);
            data.put(firstValue + "-" + (firstValue + interval), sectionCount1);
        } else {
            data.put(0 + "-" + (firstValue + interval), sectionCount1);
        }

        data.put(firstValue + interval + "-" + (firstValue + 2 * interval), sectionCount2);
        data.put(firstValue + 2 * interval + "-" + (firstValue + 3 * interval), sectionCount3);
        data.put(firstValue + 3 * interval + "-" + (firstValue + 4 * interval), sectionCount4);
        data.put(firstValue + 4 * interval + "-" + (firstValue + 5 * interval), sectionCount5);
        data.put(firstValue + 5 * interval + "-" + (firstValue + 6 * interval), sectionCount6);
        data.put(firstValue + 6 * interval + "-" + (firstValue + 7 * interval), sectionCount7);
        data.put(firstValue + 7 * interval + "-" + (firstValue + 8 * interval), sectionCount8);
        data.put(firstValue + 8 * interval + "-" + (firstValue + 9 * interval), sectionCount9);
        data.put(firstValue + 9 * interval + "以上", sectionCount10);

        result.put("data", data);
        result.put("unit", NumberUnitEnum.getNumUnit(unitNum));

        Integer maxCount = 0;
        String maxInterval = "";

        for (Map.Entry iterator : data.entrySet()) {
            String key = iterator.getKey().toString();
            Integer value = Integer.parseInt(iterator.getValue().toString());

            if (value > maxCount) {
                maxCount = value;
                maxInterval = key;
            }
        }

        result.put("maxCount", maxCount);
        result.put("maxInterval", maxInterval);

        return result;
    }

    public static void main(String[] args) {
        List<BigDecimal> numberList = new ArrayList<>();
        numberList.add(new BigDecimal(2234256));
        numberList.add(new BigDecimal(2235445l));
        numberList.add(new BigDecimal(2236321));
        numberList.add(new BigDecimal(2233455.16));
        numberList.add(new BigDecimal(2232146));
        System.out.println(getNumberPatition(numberList));
    }
}
