package la.niub.abcapi.invest.indicatordatamigration.component.util;



import org.apache.commons.lang3.time.FastDateFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类
 * @author bo.sun
 *
 */
public final class TimeUtil {

	private static final FastDateFormat dateFormat = FastDateFormat.getInstance("yyyyMMdd");

	private static final FastDateFormat timeFormat = FastDateFormat.getInstance("yyyyMMddHHmmss");

	public static final long ONE_DAY_MILLIS = 1000 * 60 * 60 * 24;

	public static final int DATE_TO_TIME_MULTIPLE = 1000000;

	private TimeUtil() {
	}

	/**
	 * convert int yyyyMMddHHmmss to String yyyy-MM-dd HH:mm:ss
	 * @param time
	 * @return
	 */
	public static String longTimeToString(long time) {
		
		StringBuilder builder = new StringBuilder(19 + 1);
		
		/*
		 * 
		 * +---------------------------------+
		 * |       yyyy-MM-dd HH:mm:ss       |
		 * +---------------------------------+
		 *             ^  ^  ^  ^  ^
		 *             |  |  |  |  |
		 *             4  7  10 13 16
		 * 
		 */
		
		builder.append(time).insert(4, '-').insert(7, '-').insert(10, ' ').insert(13, ':').insert(16, ':');
		
		return builder.toString();
		
	}
	
	/**
	 * convert timeMillis to String yyyyMMddHHmmss
	 * @param time
	 * @return
	 */
	public static String dateTimeToString(Date time) {
		
		return timeFormat.format(time);
		
	}

	/**
	 * convert timeMillis to long yyyyMMddHHmmss
	 * @param time
	 * @return
	 */
	public static long dateTimeToLong(Date time) {
		
		return Long.parseLong(dateTimeToString(time));
		
	}

	/**
	 * convert timeMillis to String yyyyMMddHHmmss
	 * @param timeMillis
	 * @return
	 */
	public static String millisTimeToString(long timeMillis) {
		
		if(timeMillis == 0) {
			return "0";
		}
		
		return dateTimeToString(new Date(timeMillis));
		
	}

	/**
	 * convert timeMillis to int yyyyMMddHHmmss
	 * @param timeMillis
	 * @return
	 */
	public static long millisTimeToLong(long timeMillis) {
		
		if(timeMillis == 0) {
			return 0;
		}
		
		return Long.parseLong(millisTimeToString(timeMillis));
		
	}

	/**
	 * convert int yyyyMMdd to String yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static String intDateToString(int date) {
		
		StringBuilder builder = new StringBuilder(10 + 1);
		
		/*
		 * 
		 * +------------------------+
		 * |       yyyy-MM-dd       |
		 * +------------------------+
		 *             ^  ^
		 *             |  |
		 *             4  7
		 * 
		 */
		
		builder.append(date).insert(4, '-').insert(7, '-');
		
		return builder.toString();
		
	}

	/**
	 * convert String yyyyMMdd to int yyyyMMdd
	 * or String yyyyMMddHHmmss to int yyyyMMddHHmmss
	 * @param date
	 * @return
	 */
	public static long stringDateTimeToLong(String date) {
		
		long l = 0;
		
		char ch = ' ';
		
		for(int i = 0, len = date.length(); i < len; i++) {
			
			ch = date.charAt(i);
			
			if(ch >= 0x30 && ch <= 0x39) {
				l = l * 10 + ch - 0x30;
			}
			
		}
		
		return l;
		
	}

	/**
	 * convert timeMillis to String yyyyMMdd
	 * @param date
	 * @return
	 */
	public static String dateDateToString(Date date) {
		
		return dateFormat.format(date);
		
	}

	/**
	 * convert timeMillis to int yyyyMMdd
	 * @param date
	 * @return
	 */
	public static int dateDateToInt(Date date) {
		
		return Integer.parseInt(dateDateToString(date));
		
	}

	/**
	 * convert timeMillis to String yyyyMMdd
	 * @param timeMillis
	 * @return
	 */
	public static String millisDateToString(long timeMillis) {
		
		if(timeMillis == 0) {
			return "0";
		}
		
		return dateDateToString(new Date(timeMillis));
		
	}

	/**
	 * convert timeMillis to int yyyyMMdd
	 * @param timeMillis
	 * @return
	 */
	public static int millisDateToInt(long timeMillis) {
		
		if(timeMillis == 0) {
			return 0;
		}
		
		return dateDateToInt(new Date(timeMillis));
		
	}
	
	
	/**
	 * 将mysql unixtimestamp 转换成java timestamp
	 * @param unixtime
	 * @return
	 * @author Tina
	 */
	public static Long toJavaTimeStamp(int unixtime){
		return 1000L * unixtime;
	}

	/**
	 * 同mysql的from_unixtime函数
	 * @author bo.sun
	 * @param unixtime
	 * @return
	 */
	public static Date fromUnixtime(int unixtime) {
		return new Date(toJavaTimeStamp(unixtime));
	}

	/**
	 * 同mysql的unix_timestamp函数
	 * @author bo.sun
	 * @return
	 */
	public static int unixTimestamp() {
		return unixTimestamp(System.currentTimeMillis());
	}
	
	/**
	 * 同mysql的unix_timestamp函数
	 * @author bo.sun
	 * @param date
	 * @return
	 */
	public static int unixTimestamp(Date date) {
		if(date == null) {
			return 0;
		}
		return unixTimestamp(date.getTime());
	}

	/**
	 * 同mysql的unix_timestamp函数
	 * @author bo.sun
	 * @param ts
	 * @return
	 */
	public static int unixTimestamp(long ts) {
		return (int) (ts / 1000);
	}

	/**
	 * java的long毫秒数转成yyyyMMddHHmmss
	 * @author bo.sun
	 * @param millis
	 * @return
	 */
	public static long millisToLong(long millis) {
		return dateToLong(new Date(millis));
	}

	/**
	 * Date转成yyyyMMddHHmmss
	 * @author bo.sun
	 * @param date
	 * @return
	 */
	public static long dateToLong(Date date) {
		long result = 0;
		if(date != null) {
			LocalDateTime dt = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			result = dt.getYear();
			result = result * 100 + dt.getMonthValue();
			result = result * 100 + dt.getDayOfMonth();
			result = result * 100 + dt.getHour();
			result = result * 100 + dt.getMinute();
			result = result * 100 + dt.getSecond();
		}
		return result;
	}

	public static Date longToDate(long l) {
		long temp = l;
		int second = (int) (temp % 100);
		temp /= 100;
		int minute = (int) (temp % 100);
		temp /= 100;
		int hour = (int) (temp % 100);
		temp /= 100;
		int dayOfMonth = (int) (temp % 100);
		temp /= 100;
		int month = (int) (temp % 100);
		temp /= 100;
		int year = (int) (temp);
		LocalDateTime dt = LocalDateTime.of(year, month, dayOfMonth, hour, minute, second);
		return Date.from(dt.atZone(ZoneId.systemDefault()).toInstant());
	}

    /**
     * 解析字符串到时间戳
     * @param dateStr
     * @return
     */
	public static Integer strtotime(String dateStr){
		return strtotime(dateStr,"yyyy-MM-dd HH:mm:ss");
	}
	public static Integer strtotime(String dateStr,String pattern){
		DateFormat format = new SimpleDateFormat(pattern);
		try {
			return (int)(format.parse(dateStr).getTime()/1000);
		} catch (ParseException e) {
//                e.printStackTrace();
			return null;
		}
	}

    /**
     * 解析字符串到date对象
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date parseDateStr(String dateStr,String pattern){
        DateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
//            e.printStackTrace();
            return null;
        }
    }

    /**
     * 日期到字符串
     * @param date
     * @param pattern yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String toString(Date date,String pattern){
        return FastDateFormat.getInstance(pattern).format(date);
    }

	public static java.util.Date getBeginDayOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getNowYear());
	    cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DATE, 1);

		return getDayStartTime(cal.getTime());
	}

	public static Integer getNowYear() {
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
	    return Integer.valueOf(gc.get(1));
    }

	public static Date getDayStartTime(Date d) {
    	Calendar calendar = Calendar.getInstance();
    	if(null != d) calendar.setTime(d);
    	calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),    calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
    	calendar.set(Calendar.MILLISECOND, 0);
    	return calendar.getTime();
    }

	public static boolean checkDateFormat(String date, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			sdf.parse(date);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 *  获取上个月
	 * @author zhairp createDate: 2018-11-23
	 * @param date 2018-11
	 * @param format yyyy-MM
	 * @return
	 * @throws Exception
	 */
	public static String getLastMonth(String date, String format) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date month = sdf.parse(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(month);
		calendar.add(Calendar.MONTH, -1);
		return sdf.format(calendar.getTime());
	}

	/**
	 *  获取上个月
	 * @author zhairp createDate: 2018-11-23
	 * @param date 2018-11
	 * @param format yyyy-MM
	 * @return
	 * @throws Exception
	 */
	public static String getLastMonth(Date date, String format) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		return sdf.format(calendar.getTime());
	}


	public static void main(String[] args) {
		System.out.println(getBeginDayOfYear());
	}

}
