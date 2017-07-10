package com.park.paycenter.service.quartz;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.park.paycenter.domain.ScheduleJob;

public class Task {
	public final static Logger log = LoggerFactory.getLogger(Task.class);

	/**
	 * 通过反射调用scheduleJob中定义的方法
	 * 
	 * @param scheduleJob
	 */
	@PostConstruct
	public static void invokMethod(ScheduleJob scheduleJob) {
		Object object = null;
		Class<?> clazz = null;
		if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
			object = Spring.getBean(scheduleJob.getSpringId());
		} 
//		else if (StringUtils.isNotBlank(scheduleJob.getBeanClass())) {
//			try {
//				clazz = Class.forName(scheduleJob.getBeanClass());
//				object = clazz.newInstance();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//		}
		if (object == null) {
			log.error("任务 = [" + scheduleJob.getJobName()+":"+ scheduleJob.getDescription()+ "]---------------未启动成功，请检查是否配置正确！！！");
			return;
		}
		clazz = object.getClass();
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(scheduleJob.getMethodName());
		} catch (NoSuchMethodException e) {
			log.error("任务 = [" + scheduleJob.getJobName()+":"+ scheduleJob.getDescription() + "]---------------未启动成功，方法名设置错误！！！");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (method != null) {
			try {
				method.invoke(object);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		log.info("任务 = [" + scheduleJob.getJobName() +":"+ scheduleJob.getDescription()+ "]----------启动成功");
	}
}
