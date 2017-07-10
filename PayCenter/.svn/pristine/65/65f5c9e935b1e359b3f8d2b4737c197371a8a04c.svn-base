package com.park.paycenter.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTools {
	
	public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static SimpleDateFormat df_date = new SimpleDateFormat("MM-dd");
	
	public static SimpleDateFormat df_time = new SimpleDateFormat("HH:mm");
	/**
	 * 计算时间差 
	 * @param date1 
	 * @param date2
	 * @return  相差的分钟数
	 */
	public  static long  time_interval(String date1,String date2){
		long interval=0;
		try {
			interval = df.parse(date2).getTime()-df.parse(date1).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long min =interval/60/1000; 
		return min;
	}
	/**
	 * 时间转换 
	 * 将秒转换成日期  XXXX-XX-XX XX:XX:XX
	 */
	public static String secondTostring(int time){
		
	    return df.format(time *1000L);
	}

	/**
	 * 时间转换 
	 * 将秒转换成日期  XX-XX
	 */
	public static String secondTostringDate(int time){
        return df_date.format(time *1000L);
	}
	
	/**
	 * 时间转换 
	 * 将秒转换成时间   XX:XX
	 */
	public static String secondTostringTime(int time){
        return df_time.format(time *1000L);
	}
	/**
	 * 时间转换   
	 * 将秒转换成XX天XX小时XX分钟
	 * @param time
	 * @return
	 */
	public static String secondTotime(int time){
		String timeStr = null;  
		int days=time/(60*60*24);//换成天
		int hours=(time-(60*60*24*days))/3600;//总秒数-换算成天的秒数=剩余的秒数    剩余的秒数换算为小时
		/*分钟采用进一法，因此增加59秒*/
		int minutes=(time-60*60*24*days-3600*hours + 59)/60;//总秒数-换算成天的秒数-换算成小时的秒数=剩余的秒数    剩余的秒数换算为分
		if (days>0) {
			timeStr =unitFormat(days)+ "天"+unitFormat(hours)+ "小时"+unitFormat(minutes)+ "分钟";
		}else if(hours>0){
			timeStr =unitFormat(hours)+ "小时"+unitFormat(minutes)+ "分钟";
		}else {
			timeStr = unitFormat(minutes)+ "分钟";
		} 
        return timeStr;
	}
	/**
	 * 时间转换   
	 * 将分钟转换成XX天XX小时XX分钟
	 * @param time
	 * @return
	 */
	public static String minuteTotime(long time){
		String timeStr = null; 
        Integer days =(int) (time/(60*24));
        Integer hours = (int) (time/(60)-days*24);
        Integer minutes = (int) (time-hours*60-days*24*60);
        if (days>0) {
			timeStr =unitFormat(days)+ "天"+unitFormat(hours)+ "小时"+unitFormat(minutes)+ "分钟";
		}else if(hours>0){
			timeStr =unitFormat(hours)+ "小时"+unitFormat(minutes)+ "分钟";
		}else {
			timeStr = unitFormat(minutes)+ "分钟";
		}
        return timeStr;
	}
	public static String unitFormat(int i) {  
        String retStr = null;  
        if (i >= 0 && i < 10)  
            retStr = "0" + Integer.toString(i);  
        else  
            retStr = "" + i;  
        return retStr;  
    }  
	/**
	 * 将时间增加一定的分钟数
	 */
	public static String addTime(String date,int min){
		
		try {
			return df.format((df.parse(date)).getTime()+60000*min);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return nowDate();
		}
	}
	/**
	 * 将时间增加一定的分钟数
	 */
	public static String addTime(Date date,int min){
		
		return df.format(date.getTime()+60000*min);
	}
	/**
	 * 获取当前时间戳
	 * @return
	 */
	public static String  nowDate(){
		
		return Long.toString(System.currentTimeMillis());
	}
	
	public static int phpNowDate(){
		Long dateLong= System.currentTimeMillis();
		return (int) (dateLong/1000);
	}
}
