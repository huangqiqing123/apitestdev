package test.tool.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTool {

	/*
	 * 获取当前年份
	 */
	public static String getCurrentYear(){
		
		Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR)+"";
	}
	/*
	 * 获取当前月份
	 */
	public static String getCurrentMonth(){
	
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.MONTH) + 1+"";
	}
	/*
	 * 获取当前日
	 */
	public static String getCurrentDayOfMonth(){		
		Calendar now = Calendar.getInstance();
		return  now.get(Calendar.DAY_OF_MONTH)+"";
	}
	/*
	 * 获取日期时间（不带秒）
	 * 2008-04-29  08:56
	 */
	public static String getDateTime()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));//如果不指定时区，在有些机器上会出现时间误差。  
		return sdf.format(new Date());
	}
	/*
	 * 获取日期时间（带秒）
	 * 2008-04-29  08:56:51
	 */
	public static String getDateTimeWithSeconds()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));//如果不指定时区，在有些机器上会出现时间误差。  
		return sdf.format(new Date());
	}
}
