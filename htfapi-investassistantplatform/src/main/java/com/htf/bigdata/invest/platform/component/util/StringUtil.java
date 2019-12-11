package com.htf.bigdata.invest.platform.component.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @description: 字符串工具类
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public class StringUtil {

    static Logger logger = LogManager.getLogger(StringUtil.class);

    /**
     * 按长度切割字符串
     * @param str
     * @param beginIndex
     * @param length
     * @param remainLess 是否保留少于指定长度的字串
     * @return
     */
    public static List<String> lengthSplit(String str, Integer beginIndex,Integer length,Boolean remainLess){
        List<String> result = new ArrayList<>();
        if (str == null || str.trim().isEmpty()){
            return result;
        }
        Integer i = beginIndex;
        String sub;
        try{
            do{
                sub = str.substring(i,Integer.min(i+length,str.length()));
                i += length;
                if (sub.length() < length && !remainLess){
                    continue;
                }
                result.add(sub);
            }while(sub.length() >= length);
        }catch (StringIndexOutOfBoundsException e){
            logger.error("StringUtil.lengthSplit StringIndexOutOfBoundsException for "+str+"["+i+","+Integer.min(i+length,str.length())+"]");
        }
        return result;
    }

    /**
     * 字符串是否全是数字
     * @param str
     * @return
     */
    public static Boolean isNumeric(String str){
        return str.matches("\\d+");
    }

    /**
     * 是否是空的，为null或空字符串或空列表
     * @param target
     * @return
     */
    public static Boolean isEmptyExceptZero(Object target){
        if (target == null){
            return true;
        }else if (target.getClass().equals(String.class)){
            return ((String) target).isEmpty() || ((String) target).replaceAll("\\s","").isEmpty();
        }else if (target.getClass().equals(List.class)){
            return ((List) target).isEmpty();
        }
        return false;
    }

    /**
     * 是否是空的或为0，为null或空字符串或空列表或为0
     * @param target
     * @return
     */
    public static Boolean isEmpty(Object target){
        if (target == null){
            return true;
        }else if (target.getClass().equals(Integer.class)){
            return ((Integer) target) == 0;
        }else if (target.getClass().equals(String.class)){
            return ((String) target).isEmpty() || ((String) target).replaceAll("\\s","").isEmpty();
//        }else if (target.getClass().equals(List.class)){
        }else if (target.getClass().equals(String[].class)){
            return ((String[]) target).length == 0;
        }else if (target instanceof List){
            return ((List) target).isEmpty();
        }else if (target instanceof Map){
            return ((Map) target).isEmpty();
        }
        return false;
    }

    /**
     * 是否是英文字母
     * @param str string
     * @return boolean
     */
    public static boolean isAlpha(String str) {
        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            if (!isAlpha(aChar)) {
                return false;
            }
        }
        return true;
    }
    public static boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    /**
     * 是否是英文字母/数字
     * @param str
     * @return
     */
    public static boolean isWord(String str) {
        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            if (!isWord(aChar)) {
                return false;
            }
        }
        return true;
    }
    public static boolean isWord(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9');
    }

    /**
     * 是否是标点符号
     * @return
     */
    public static boolean isPunct(char c){
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (Pattern.matches("\\p{Punct}",String.valueOf(c)) || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
                || ub == Character.UnicodeBlock.VERTICAL_FORMS) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取文本中的中文
     * @param message
     * @return
     */
    public static List<String> getChinese(String message) {
        List<String> result = new ArrayList<>();
        String chinese = "";
        for (char c : message.toCharArray())
        {
            if (c > 255) {
                chinese += c;
            }else{
                if (!chinese.isEmpty()){
                    result.add(chinese);
                    chinese = "";
                }
            }
        }
        if (!chinese.isEmpty()){
            result.add(chinese);
        }
        return result;
    }

    /**
     * 去掉字符串里面的html代码。
     * 要求数据要规范，比如大于小于号要配套,否则会被集体误杀。
     * @param content
     * @return
     */
    public static String stripHtml(String content) {
        // <p>段落替换为换行
        content = content.replaceAll("<p .*?>", "\r\n");
        // <br><br/>替换为换行
        content = content.replaceAll("<br\\s*/?>", "\r\n");
        // 去掉其它的<>之间的东西
        content = content.replaceAll("\\<.*?>", "");
        // 还原HTML
        // content = HTMLDecoder.decode(content);
        return content;
    }

    /**
     * 比较字符串是否相当
     * @param source
     * @param comparer
     * @return
     */
    public static Boolean equals(String source, String comparer){
        if (source == null){
            return false;
        }
        return source.equals(comparer);
    }

    /**
     * 如果值是null，返回默认值
     * @param value
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T> T nullOrDefault(T value,T defaultValue){
        return value != null ? value : defaultValue;
    }

    /**
     * 获取指定字符串出现的次数
     *
     * @param srcText 源字符串
     * @param findText 要查找的字符串
     * @return
     */
    public static Integer appearNumber(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }

    /**
     * @Description: 将公告行业分类code由solrweb标准转数据库标准
     * @Date: 2018/5/28 12:27
     * @param code  solrweb标准行业code
     * @return 数据库标准行业code(abc_industry.indu_code)
     */
    public static String solrIndu2DbIndu(String code){
        if(code==null || code==""){
            return "";
        }

        String firstLetter = code.substring(0, 1);
        String last = code.substring(1);

        if ( firstLetter.equals("F") ) {
            return last + "000";
        } else if ( firstLetter.equals("S") ) {
            return last + "00";
        } else if ( firstLetter.equals("T") ) {
            return last;
        } else {
            return last;
        }
    }

    /**
     * @Description: 将公告行业分类induCode转换成solrweb标准code
     * @Date: 2018/5/28 12:27
     * @param code  数据库标准行业code(abc_industry.indu_code)
     * @return solrweb标准行业code
     */
    public static String recoverIndustry(String code){
        if (!StringUtils.isNumeric(code)){
            return code;
        }
        if (code.endsWith("000")){
            return "F"+code.substring(0,3);
        }else if (code.endsWith("00")){
            return "S"+code.substring(0,4);
        }else {
            return "T"+code;
        }
    }
}
