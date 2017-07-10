package com.park.paycenter.service.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.park.paycenter.domain.ScheduleJob;

/**
 * 任务工厂类
 */
public class QuartzJobFactory implements Job {

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
		Task.invokMethod(scheduleJob);
	}

}
