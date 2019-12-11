package com.htf.bigdata.invest.indicatormanage.component.util;

import com.htf.bigdata.invest.indicatormanage.config.enums.IndicatorFrequencyEnum;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 日期工具类
 * @author: panpei
 * @date: 2019-05-31 17:22:00
 */
public final class TimeUtil {
	private static final Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {
		{
			put("\\d{4}$", "yyyy");
			put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
			put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
			put("^\\d{4}-\\d{1,2}$", "yyyy-MM");
			put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
			put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
			put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
			put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
			put("^d{12}$", "yyyyMMddHHmm");
			put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
			put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
			put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
			put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
			put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
			put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
			put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
			put("^d{14}$", "yyyyMMddHHmmss");
			put("^d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
			put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
			put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
			put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
			put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
			put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
			put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
		}
	};

	/**
	 * 根据传入的日期字符串返回日期格式
	 * @param dateString
	 * @return
	 */
	public static String determineDateFormat(String dateString) {
		for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
			if (dateString.toLowerCase().matches(regexp)) {
				return DATE_FORMAT_REGEXPS.get(regexp);
			}
		}

		// unknown format
		return null;
	}

	/**
	 * 判断传入的日期是否满足DATE_FORMAT_REGEXPS中的格式
	 * @param dateString
	 * @return
	 */
	public static boolean isDate(String dateString) {
		for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
			if (dateString.toLowerCase().matches(regexp)) {
				return true;
			}
		}

		// unknown format
		return false;
	}

	/**
	 * 日期字符串转成date
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date string2date(String date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		try {
			return df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
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
	 * 校验日期字符串是否是传入的格式
	 * @param date
	 * @param format
	 * @return
	 */
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
	 * 日期格式化
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2String(Date date, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取当天开始时间
	 * @return
	 */
	public static Date getCurrentDayStartTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		setDayStartTime(calendar);
		return calendar.getTime();
	}

	/**
	 * 获取当天结束时间
	 * @return
	 */
	public static Date getCurrentDayEndTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		setDayEndTime(calendar);
		return calendar.getTime();
	}

	/**
	 * 获取当周开始时间
	 * @return
	 */
	public static Date getCurrentWeekStartTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		setDayStartTime(calendar);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return calendar.getTime();
	}

	/**
	 * 获取当周结束时间
	 * @return
	 */
	public static Date getCurrentWeekEndTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		setDayEndTime(calendar);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return calendar.getTime();
	}

	/**
	 * 获取当月开始时间
	 * @return
	 */
	public static Date getCurrentMonthStartTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		setMonthStartTime(calendar);
		return calendar.getTime();
	}

	/**
	 * 获取当月结束时间
	 * @return
	 */
	public static Date getCurrentMonthEndTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		setMonthEndTime(calendar);
		return calendar.getTime();
	}

	/**
	 * 获取当前季度开始时间
	 * @return
	 */
	public static Date getCurrentQuarterStartTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		if (currentMonth >= 1 && currentMonth <=3) {
			calendar.set(Calendar.MONTH, 0);
		} else if (currentMonth >= 4 && currentMonth <= 6) {
			calendar.set(Calendar.MONTH, 3);
		} else if (currentMonth >= 7 && currentMonth <= 9) {
			calendar.set(Calendar.MONTH, 6);
		} else {
			calendar.set(Calendar.MONTH, 10);
		}
		setMonthStartTime(calendar);
		return calendar.getTime();
	}

	/**
	 * 获取当前季度结束时间
	 * @return
	 */
	public static Date getCurrentQuarterEndTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		if (currentMonth >= 1 && currentMonth <=3) {
			calendar.set(Calendar.MONTH, 2);
		} else if (currentMonth >= 4 && currentMonth <= 6) {
			calendar.set(Calendar.MONTH, 5);
		} else if (currentMonth >= 7 && currentMonth <= 9) {
			calendar.set(Calendar.MONTH, 8);
		} else {
			calendar.set(Calendar.MONTH, 11);
		}
		setMonthEndTime(calendar);
		return calendar.getTime();
	}

	/**
	 * 获取当前半年开始时间
	 * @return
	 */
	public static Date getCurrentHalfYearStartTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		if (currentMonth >= 1 && currentMonth <= 6) {
			calendar.set(Calendar.MONTH, 0);
		} else {
			calendar.set(Calendar.MONTH, 6);
		}
		setMonthStartTime(calendar);
		return calendar.getTime();
	}

	/**
	 * 获取当前半年结束时间
	 * @return
	 */
	public static Date getCurrentHalfYearEndTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		if (currentMonth >= 1 && currentMonth <= 6) {
			calendar.set(Calendar.MONTH, 5);
		} else {
			calendar.set(Calendar.MONTH, 11);
		}
		setMonthEndTime(calendar);
		return calendar.getTime();
	}

	/**
	 * 获取当年开始时间
	 * @return
	 */
	public static Date getCurrentYearStartTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		setDayStartTime(calendar);
		return calendar.getTime();
	}

	/**
	 * 获取当年结束时间
	 * @return
	 */
	public static Date getCurrentYearEndTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		calendar.add(Calendar.YEAR, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		setDayEndTime(calendar);
		return calendar.getTime();
	}

	/**
	 * 设置日开始时间
	 * @param calendar
	 */
	private static void setDayStartTime(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	/**
	 * 设置日结束时间
	 * @param calendar
	 */
	private static void setDayEndTime(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
	}

	/**
	 * 设置月开始时间
	 * @param calendar
	 */
	private static void setMonthStartTime(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		setDayStartTime(calendar);
	}

	/**
	 * 设置月结束时间
	 * @param calendar
	 */
	private static void setMonthEndTime(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH , 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		setDayEndTime(calendar);
	}

	/**
	 * 根据指标更新频率获取开始时间
	 * @param indicatorFrequencyEnum
	 * @return
	 */
	public static Date getIndicatorFrequencyStartTime(IndicatorFrequencyEnum indicatorFrequencyEnum, Date date) {
		switch (indicatorFrequencyEnum) {
			case DAY:
				return TimeUtil.getCurrentDayStartTime(date);
			case WEEK:
				return TimeUtil.getCurrentWeekStartTime(date);
			case MONTH:
				return TimeUtil.getCurrentMonthStartTime(date);
			case QUARTER:
				return TimeUtil.getCurrentQuarterStartTime(date);
			case HALF_YEAR:
				return TimeUtil.getCurrentHalfYearStartTime(date);
			case YEAR:
				return TimeUtil.getCurrentYearStartTime(date);
			default:
				return null;
		}
	}

	/**
	 * 根据指标更新频率获取结束时间
	 * @param indicatorFrequencyEnum
	 * @return
	 */
	public static Date getIndicatorFrequencyEndTime(IndicatorFrequencyEnum indicatorFrequencyEnum, Date date) {
		switch (indicatorFrequencyEnum) {
			case DAY:
				return TimeUtil.getCurrentDayEndTime(date);
			case WEEK:
				return TimeUtil.getCurrentWeekEndTime(date);
			case MONTH:
				return TimeUtil.getCurrentMonthEndTime(date);
			case QUARTER:
				return TimeUtil.getCurrentQuarterEndTime(date);
			case HALF_YEAR:
				return TimeUtil.getCurrentHalfYearEndTime(date);
			case YEAR:
				return TimeUtil.getCurrentYearEndTime(date);
			default:
				return null;
		}
	}

	public static void main(String[] args) {
		Date date = new Date();
		System.out.println("当天开始时间:" + date2String(getCurrentDayStartTime(date), "yyyy-MM-dd HH:mm:ss"));
		System.out.println("当天结束时间:" + date2String(getCurrentDayEndTime(date), "yyyy-MM-dd HH:mm:ss"));
		System.out.println("当周开始时间:" + date2String(getCurrentWeekStartTime(date), "yyyy-MM-dd HH:mm:ss"));
		System.out.println("当周结束时间:" + date2String(getCurrentWeekEndTime(date), "yyyy-MM-dd HH:mm:ss"));
		System.out.println("当月开始时间:" + date2String(getCurrentMonthStartTime(date), "yyyy-MM-dd HH:mm:ss"));
		System.out.println("当月结束时间:" + date2String(getCurrentMonthEndTime(date), "yyyy-MM-dd HH:mm:ss"));
		System.out.println("当前季度开始时间:" + date2String(getCurrentQuarterStartTime(date), "yyyy-MM-dd HH:mm:ss"));
		System.out.println("当前季度结束时间:" + date2String(getCurrentQuarterEndTime(date), "yyyy-MM-dd HH:mm:ss"));
		System.out.println("当前半年开始时间:" + date2String(getCurrentHalfYearStartTime(date), "yyyy-MM-dd HH:mm:ss"));
		System.out.println("当前半年结束时间:" + date2String(getCurrentHalfYearEndTime(date), "yyyy-MM-dd HH:mm:ss"));
		System.out.println("当前年开始时间:" + date2String(getCurrentYearStartTime(date), "yyyy-MM-dd HH:mm:ss"));
		System.out.println("当前年结束时间:" + date2String(getCurrentYearEndTime(date), "yyyy-MM-dd HH:mm:ss"));
	}
}
