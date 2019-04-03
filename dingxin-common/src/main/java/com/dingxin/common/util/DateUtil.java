package com.dingxin.common.util;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * 
 * Create By qinzhw
 * 2018年4月12日下午6:41:30
 */
public class DateUtil {
	/**
	 * 中文日期 yyyy-MM-dd E
	 */
	private static String YYYY_MM_DD_E = "yyyy-MM-dd E";

	/**
	 * yyyy-MM-dd
	 */
	private static String YYYY_MM_DD = "yyyy-MM-dd";
	/**
	 * yyyyMMdd
	 */
	private static String yyyy_mm_dd = "yyyyMMdd";

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	private static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	/**
	 * yyyy-MM-dd HH:mm
	 */
	private static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	/**
	 * yyyyMMddHHmm
	 */
	private static String yyyy_mm_dd_hh_mm = "yyyyMMddHHmm";
	/**
	 * HH:mm
	 */
	private static String HH_MM = "HH:mm";
	/**
	 * HHmm
	 */
	private static String hh_mm = "HHmm";

	/**
	 * MMddHHmmss
	 */
	private static String mm_dd_hh_mm_ss = "MMddHHmmss";

	private static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public static FastDateFormat YYYYMMDDE() {
		FastDateFormat faseDF = FastDateFormat.getInstance(YYYY_MM_DD_E, Locale.CHINESE);
		return faseDF;
	}

	public static FastDateFormat YYYYMMDD() {
		FastDateFormat faseDF = FastDateFormat.getInstance(YYYY_MM_DD);
		return faseDF;
	}

	public static FastDateFormat yyyymmdd() {
		FastDateFormat faseDF = FastDateFormat.getInstance(yyyy_mm_dd);
		return faseDF;
	}

	public static FastDateFormat YYYYMMDDHHMMSS() {
		FastDateFormat faseDF = FastDateFormat.getInstance(YYYY_MM_DD_HH_MM_SS);
		return faseDF;
	}

	public static FastDateFormat YYYYMMDDHHMM() {
		FastDateFormat faseDF = FastDateFormat.getInstance(YYYY_MM_DD_HH_MM);
		return faseDF;
	}

	public static FastDateFormat yyyymmddhhmm() {
		FastDateFormat faseDF = FastDateFormat.getInstance(yyyy_mm_dd_hh_mm);
		return faseDF;
	}

	public static FastDateFormat HHMM() {
		FastDateFormat faseDF = FastDateFormat.getInstance(HH_MM);
		return faseDF;
	}

	public static FastDateFormat hhmm() {
		FastDateFormat faseDF = FastDateFormat.getInstance(hh_mm);
		return faseDF;
	}

	public static FastDateFormat mmddhhmmss() {
		FastDateFormat faseDF = FastDateFormat.getInstance(mm_dd_hh_mm_ss);
		return faseDF;
	}

	public static String getNowYYYYMMDDHHMMSS() {
		return FastDateFormat.getInstance(YYYY_MM_DD_HH_MM_SS).format(new Date());
	}

	public static String getNowYYYYMMDDHHMM() {
		return FastDateFormat.getInstance(YYYY_MM_DD_HH_MM).format(new Date());
	}

	public static String getNowYYYYMMDD() {
		return FastDateFormat.getInstance(YYYY_MM_DD).format(new Date());
	}

	public static String getNowYYYYMMDDHHMMSS2(){
		return FastDateFormat.getInstance(YYYYMMDDHHMMSS).format(new Date());
	}

	/**
	 * 通过时间秒毫秒数判断两个时间的间隔
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differentDaysByMillisecond(Date date1,Date date2)
	{
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
		return days;
	}

	/**
	 * 得到前后天数的日期
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getBeforeAfterDay(Date date, int days){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// add方法中的第二个参数n中，正数表示该日期后n天，负数表示该日期的前n天
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}

	public static double getBetweenDays(Date date1, Date date2){
		double days = (double) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
		return days;
	}

//	public static void main(String[] args) {
//		String dateStr = "2008-1-1 1:21:28";
//		String dateStr2 = "2008-1-2 12:22:28";
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		try
//		{
//			Date date2 = format.parse(dateStr2);
//			Date date = format.parse(dateStr);
//
//			System.out.println("两个日期的差距：" + getBetweenDays(date,date2));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//	}
}
