package com.park.paycenter.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 通知的频率
 * @author fangct
 *
 */
public class NotifyFrequencyConstants {
	
	//最大通知次数
	public static final int MAX_NOTIFY_TIMES = 8;
	
	//25小时以内完成8次通知（通知的间隔频率一般是：4m,10m,10m,1h,2h,6h,15h）,
	//第1次已经在支付宝/微信通知时完成了
	public static Map< String, Integer>  timesMap =new HashMap<String, Integer>(){
		//KEY 代表第几次通知，VALUE 代表下次执行增加的分钟数
		private static final long serialVersionUID = 1L;
		{
			put("1", 4);//4分钟
			put("2", 10);//10分钟
			put("3", 10);//10分钟
			put("4", 60);//1小时
			put("5", 120);//2小时
			put("6", 360);//6小时
			put("7", 900);//15小时
		}
	};
}
