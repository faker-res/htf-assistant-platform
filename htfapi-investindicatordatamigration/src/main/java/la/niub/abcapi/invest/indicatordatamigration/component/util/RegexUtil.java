package la.niub.abcapi.invest.indicatordatamigration.component.util;

import java.util.regex.Pattern;

/**
 * 正则工具类
 *
 * @author wangtonghe
 * @since 2018/4/2 10:42
 */
public class RegexUtil {

    private static final Pattern MOBILE_PATTERN = Pattern.compile("1[35789][0-9]{9}");

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$");

    /**
     * 验证手机
     *
     * @param mobile 待验证手机号
     * @return bool
     */
    public static boolean isMobileNumber(String mobile) {
        return MOBILE_PATTERN.matcher(mobile).matches();
    }

    /**
     * 验证邮箱地址
     *
     * @param email 邮箱地址
     * @return 如果是合法的邮箱地址，返回true
     */
    public static boolean isEmailAddress(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
