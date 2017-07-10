package com.park.paycenter.config;

import java.io.IOException;
import java.util.Properties;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.park.paycenter.profile.QuartzProfile;
/**
 * 设置定时任务的配置参数
 * @author fangct
 * 2017年6月19日
 */
@Configuration
public class QuartzConfig {

	@Autowired
	QuartzProfile quartzProfile;
	
	@Bean
	public Scheduler scheduler() throws IOException, SchedulerException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory(quartzProperties());
		Scheduler scheduler = schedulerFactory.getScheduler();
		scheduler.start();
		return scheduler;
	}
	
	/**
	 * 设置quartz属性
	 * @throws IOException
	 * 
	 */
	public Properties quartzProperties() throws IOException {
		Properties prop = new Properties();
		prop.put("quartz.scheduler.instanceName", quartzProfile.getInstanceName());
		prop.put("org.quartz.scheduler.instanceId", quartzProfile.getInstanceId());
		prop.put("org.quartz.scheduler.threadsInheritContextClassLoaderOfInitializer", quartzProfile.getThreadsInheritContextClassLoaderOfInitializer());
		prop.put("org.quartz.scheduler.skipUpdateCheck", quartzProfile.getSkipUpdateCheck());
		prop.put("org.quartz.scheduler.batchTriggerAcquisitionMaxCount", quartzProfile.getBatchTriggerAcquisitionMaxCount());
		prop.put("org.quartz.scheduler.dbFailureRetryInterval", quartzProfile.getDbFailureRetryInterval());
		prop.put("org.quartz.threadPool.threadCount", quartzProfile.getThreadCount());
		prop.put("org.quartz.threadPool.class", quartzProfile.getThreadPoolClass());
		prop.put("org.quartz.jobStore.class", quartzProfile.getJobStoreClass());
		prop.put("org.quartz.jobStore.dataSource", quartzProfile.getDataSource());
		prop.put("org.quartz.jobStore.tablePrefix", quartzProfile.getTablePrefix());
		prop.put("org.quartz.jobStore.isClustered", quartzProfile.getIsClustered());
		prop.put("org.quartz.jobStore.acquireTriggersWithinLock", quartzProfile.getAcquireTriggersWithinLock());
        prop.put("org.quartz.jobStore.clusterCheckinInterval", quartzProfile.getClusterCheckinInterval());
       
		prop.put("org.quartz.dataSource.myDS.driver", quartzProfile.getDriver());
		prop.put("org.quartz.dataSource.myDS.URL", quartzProfile.getUrl());
		prop.put("org.quartz.dataSource.myDS.user", quartzProfile.getUser());
		prop.put("org.quartz.dataSource.myDS.password", quartzProfile.getPassword());
		prop.put("org.quartz.dataSource.myDS.validationQuery", quartzProfile.getValidationQuery());
		return prop;
	}
}
