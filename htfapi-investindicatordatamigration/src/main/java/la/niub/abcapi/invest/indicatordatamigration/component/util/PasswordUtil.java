package la.niub.abcapi.invest.indicatordatamigration.component.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author wangtonghe
 * @since 2018/3/21 13:50
 */
public final class PasswordUtil {

    private static final Logger logger = LoggerFactory.getLogger(PasswordUtil.class);

    private static final int MAX_UNIT = 10;

    private PasswordUtil() {

    }

    public static String getCodeMd5(Map<String, Object> param) {

        StringBuilder codeStr = new StringBuilder();
        param.forEach((k, v) -> codeStr.append(k).append("=").append(v).append("&"));
        codeStr.append("time").append("=").append(getCurrentTime());
        logger.info("传来param={}", codeStr);

        return PasswordUtil.md5Password(codeStr.toString());
    }

    public static String getCurrentTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String curTime = localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        String hourStr = String.valueOf(hour), minuteStr = String.valueOf(minute);
        if (hour < MAX_UNIT) {
            hourStr = "0" + hour;
        }
        if (minute < MAX_UNIT) {
            minuteStr = "0" + minute;
        }
        curTime += " " + hourStr + ":" + minuteStr;
        return curTime;
    }

    public static String md5Password(String password) {

        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算 加盐
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }
}
