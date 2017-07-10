package com.park.paycenter.profile;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "QuartzProfile")
public class QuartzProfile {
	String instanceName;
	// 每个 Quartz Scheduler 必须指定一个唯一的 ID
	String instanceId;
	String threadsInheritContextClassLoaderOfInitializer;
	// 再定时检查版本更新
	String skipUpdateCheck;
	// 当检查某个Trigger应该触发时，默认每次只Acquire一个Trigger
	String batchTriggerAcquisitionMaxCount;
	// 检测到 JobStore 到某处的连接(比如到数据库的连接) 断开后，再次尝试连接所等待的毫秒数
	String dbFailureRetryInterval;
	// 线程池里的线程数
	String threadCount;
	// ThreadPool 实现的类名
	String threadPoolClass;
	// Configure JDBC-JobStoreTX
	String jobStoreClass;
	String dataSource;
	// 表前缀
	String tablePrefix;
	// 是否是应用在集群中，当应用在集群中时必须设置为TRUE，否则会出错
	String isClustered;
	// 触发job时是否需要拥有锁
	String acquireTriggersWithinLock;
	// scheduler的checkin时间，时间长短影响failure scheduler的发现速度
	String clusterCheckinInterval;
	String url;
	// Configure DataSources
	String driver;
	String user;
	String password;
	String validationQuery;
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getThreadsInheritContextClassLoaderOfInitializer() {
		return threadsInheritContextClassLoaderOfInitializer;
	}
	public void setThreadsInheritContextClassLoaderOfInitializer(String threadsInheritContextClassLoaderOfInitializer) {
		this.threadsInheritContextClassLoaderOfInitializer = threadsInheritContextClassLoaderOfInitializer;
	}
	public String getSkipUpdateCheck() {
		return skipUpdateCheck;
	}
	public void setSkipUpdateCheck(String skipUpdateCheck) {
		this.skipUpdateCheck = skipUpdateCheck;
	}
	public String getBatchTriggerAcquisitionMaxCount() {
		return batchTriggerAcquisitionMaxCount;
	}
	public void setBatchTriggerAcquisitionMaxCount(String batchTriggerAcquisitionMaxCount) {
		this.batchTriggerAcquisitionMaxCount = batchTriggerAcquisitionMaxCount;
	}
	public String getDbFailureRetryInterval() {
		return dbFailureRetryInterval;
	}
	public void setDbFailureRetryInterval(String dbFailureRetryInterval) {
		this.dbFailureRetryInterval = dbFailureRetryInterval;
	}
	public String getThreadCount() {
		return threadCount;
	}
	public void setThreadCount(String threadCount) {
		this.threadCount = threadCount;
	}
	public String getThreadPoolClass() {
		return threadPoolClass;
	}
	public void setThreadPoolClass(String threadPoolClass) {
		this.threadPoolClass = threadPoolClass;
	}
	public String getJobStoreClass() {
		return jobStoreClass;
	}
	public void setJobStoreClass(String jobStoreClass) {
		this.jobStoreClass = jobStoreClass;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public String getTablePrefix() {
		return tablePrefix;
	}
	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}
	public String getIsClustered() {
		return isClustered;
	}
	public void setIsClustered(String isClustered) {
		this.isClustered = isClustered;
	}
	public String getAcquireTriggersWithinLock() {
		return acquireTriggersWithinLock;
	}
	public void setAcquireTriggersWithinLock(String acquireTriggersWithinLock) {
		this.acquireTriggersWithinLock = acquireTriggersWithinLock;
	}
	public String getClusterCheckinInterval() {
		return clusterCheckinInterval;
	}
	public void setClusterCheckinInterval(String clusterCheckinInterval) {
		this.clusterCheckinInterval = clusterCheckinInterval;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getValidationQuery() {
		return validationQuery;
	}
	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

}
