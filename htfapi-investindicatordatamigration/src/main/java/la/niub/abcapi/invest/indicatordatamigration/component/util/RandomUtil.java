package la.niub.abcapi.invest.indicatordatamigration.component.util;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 取随机数的工具
 * @author huangping<br />
 * 2013-2-20
 */
public class RandomUtil {

	/**
	 * 生成随机ID
	 * @return
	 */
	public static String generateId(){
		return TimeUtil.dateTimeToString(new Date()) + getRandomStr(8,"NUMBER");
	}

	/**
	 * 取随机数(0-1)
	 * 
	 * @return
	 */
	public static double getRandom() {
		Random rd = new Random();
		return rd.nextDouble();
	}

	/**
	 * 取min 到 max 之间的随机整数
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static long getRandom(long min, long max) {
		if (min < 0 || max <= 0) {
			return 0;
		}
		if ((max - min) < 0) {
			return 0;
		}
		if (max == min) {
			return min;
		}
		max++;
		Random rd = new Random();
		long tmp = rd.nextInt((int) (max - min));
		return tmp + min;
	}

	public static String getRandom(int size) {
		if (size <= 0) {
			return null;
		}
		double tmp = getRandom();
		tmp = Math.pow(10, size) * tmp;
		String result = String.valueOf((int) tmp);
		while (result.length() < size) {
			result = "0" + result;
		}
		return result;
	}

    /**
     * 取指定长度的随机数
     * @author wuxiao
     * @return
     */
	public static String getRandomStr(Integer len, String format) {
		String chars;
		switch (format.toUpperCase()) {
			case "ALL" :
				chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
				break;
			case "CHAR" :
				chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
				break;
			case "NUMBER" :
				chars = "0123456789";
				break;
			case "PNUMBER" :
				chars = "123456789";
				break;
			default :
				chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
				break;
		}

		String string = "";
		Random rd = new Random();
		Integer pos;
		while ( string.length() < len ){
			pos = Math.abs(rd.nextInt()) % chars.length();
			string += chars.substring(pos,pos+1);
		}
		return string;
	}

	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-","");
	}
}
