package com.park.paycenter.timetask;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.park.paycenter.bean.mail.MailInfo;
import com.park.paycenter.dao.FailNotifyRecordDao;
import com.park.paycenter.domain.FailNotifyRecord;
import com.park.paycenter.service.TaskPlanService;
import com.park.paycenter.tools.HtmlTools;
import com.park.paycenter.tools.MailHandlerTools;

/**
 * 发送邮件的一些情况处理
 * @author fangct
 * created at 20170623
 */
@Component
public class SomeCaseSendMailTask  implements TaskPlanService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="FailNotifyRecordDaoImpl")
	private FailNotifyRecordDao failNotifyRecordDao;

	//@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void executeInternal(){
		logger.info("****邮件提醒: 支付后通知失败的订单...");
		/**
		 * 1. 支付成功后，把通知H5返回失败的记录进行及时的邮件提醒
		 */
		String[] heads = {"车场ID", "订单号", "交易类型", "已轮询次数", "通知失败原因", "首次通知失败时间", "下次轮询时间"};
		String[] params = {"park_id", "out_trade_no", "trade_type", "notify_times", "cause_mark", "create_time", "next_notify_time"};
		
		try{
			//查询仍未将失败情况通知给相关负责人的记录
			List<FailNotifyRecord> records = failNotifyRecordDao.findNoSendMail();
			//如果没有查询到结果 ，则直接结束此次任务
			if(records == null || records.size() == 0) return;
			
			/*生成表单的HTML文本*/
			List<Object> objList = new ArrayList<Object>();
			for(FailNotifyRecord record : records){
				objList.add((Object)record);
			}
			String tableHtml = HtmlTools.buildTableHtml(objList, heads, params);	
			
			/*更新相关记录的邮件发送状态*/
			for(FailNotifyRecord record : records){
				//修改邮件是否发送的状态
				record.setSend_status(2);
				int n = failNotifyRecordDao.update(record);
				if(n == 0) logger.info("订单号：{}，更新邮件发送状态字段未成功！", record.getOut_trade_no());
			}
			
			/*发送邮件*/
			List<MailInfo> mailInfoList = new ArrayList<MailInfo>();
			MailInfo mailInfo = new MailInfo();
			mailInfo.setContent(tableHtml);
			mailInfo.setTitle("支付后通知失败的订单");
			mailInfoList.add(mailInfo);
			MailHandlerTools.sendMail(mailInfoList);
		}catch(Exception e){
			logger.error("定时任务邮件通知出错异常，原因：{}", e);
			//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//如有错误，则回滚
		}
	}
	
}
