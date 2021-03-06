package com.park.paycenter.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.park.paycenter.bean.mail.MailInfo;
import com.park.paycenter.bean.mail.SMTPAuthenticator;
import com.park.paycenter.constants.MailConstants;
import com.park.paycenter.profile.MailProfile;

/**
 * 邮件发送工具
 * 
 * @author fangct
 * created at 20170623
 */
public class MailHandlerTools {
	private static Logger logger = LoggerFactory.getLogger(MailHandlerTools.class);

	//邮件配置参数
	private static MailProfile mailProfile;
	static {
		//初始化邮件配置参数
		mailProfile = Spring.getBean(MailProfile.class);
	}

	/**
	 * 发送邮件
	 * 
	 * @param paramList
	 *            邮件对象集合
	 */
	public static void sendMail(List<MailInfo> paramList) {
		for (int i = 0; i < paramList.size(); i++) {
			MailInfo mailInfo = (MailInfo) paramList.get(i);
			//如果没有指定收件人地址，则以默认配置作为收件人
			String toAddrs = mailInfo.getTo();
			String title = ("[PayCenter支付中心]" + mailInfo.getTitle());
	        StringBuffer contentSB = new StringBuffer();
	        contentSB.append("<div style=\"font-size:"+MailConstants.DIV_FONT_SIZE+";\">");
	        contentSB.append("<font color=\"blue\"><b>PayCenter通知,请不要回复此消息!</b></font><br>\t\n\t\n");
	        //sb.append("点击查看详情:    " + str + "    <br>\t\n\t\n");
	        contentSB.append("-----------------------------------------------------------<br>\t\n");
	        contentSB.append("内容:" + "您好！具体情况如下：<br><br>"+mailInfo.getContent() + "<br>");
	        contentSB.append("触发人:系统<br>");
	        contentSB.append("来自<b>[<font color=\"red\">" + mailProfile.getEnvironment() + "</font>]</b>环境");
	        contentSB.append("</div>");
			sendMail(title, contentSB.toString(), toAddrs);
		}
	}
	
	/**
	 * 发送邮件
	 * 
	 * @param user
	 *            用户名
	 * @param password
	 *            密码
	 * @param address
	 *            邮件接收地址
	 * @param paramList
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 */
	public static void sendMail(String user, String password, String from, List<Object> toAddrsList, String subject,
			String content) {
		if ((toAddrsList == null) || (toAddrsList.size() == 0))
			return;
		try {
			Properties properties = new Properties();
			properties.setProperty("mail.smtp.host", mailProfile.getMail_smtp_host());
			properties.setProperty("mail.smtp.port", mailProfile.getMail_smtp_port());
			properties.setProperty("mail.smtp.auth", mailProfile.getMail_smtp_auth());
			properties.setProperty("username", mailProfile.getUsername());
			properties.setProperty("pswd", mailProfile.getPswd());
			properties.setProperty("from", mailProfile.getFromaddr());
			properties.setProperty("to", mailProfile.getToaddr());
			properties.setProperty("flag", mailProfile.getMailflag());
			if ((mailProfile.getMailflag() == null) || (mailProfile.getMailflag().equals(""))
					|| (mailProfile.getMailflag().equals("false")))
				return;
			Session session = Session.getInstance(properties, new SMTPAuthenticator(user, password));
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			InternetAddress[] arrayOfInternetAddress = new InternetAddress[toAddrsList.size()];
			for (int i = 0; i < arrayOfInternetAddress.length; i++)
				arrayOfInternetAddress[i] = new InternetAddress((String) toAddrsList.get(i));
			message.addRecipients(Message.RecipientType.TO, arrayOfInternetAddress);
			message.setSubject(subject, "utf-8");
			message.setContent(content, "text/html; charset=utf-8");
			Transport.send(message);
			logger.info("邮件发送完毕！");
		} catch (Exception e) {
			logger.error("邮件发送时出错，原因：{}", e);
		}
	}

	/**
	 * 邮件发送
	 * 
	 * @param subject
	 *            主题
	 * @param content
	 *            邮件内容
	 * @param toAddrs
	 *            接收人( 可以多个 )
	 */
	public static void sendMail(String subject, String content, String toAddrs) {
		//如果没有指定邮件接收人，则以默认配置为收件人
		if(toAddrs == null || toAddrs.trim().length() == 0)
			toAddrs = mailProfile.getToaddr();
		logger.info("mail.flag:[" + mailProfile.getMailflag() + "]");
		if ((mailProfile.getMailflag() == null) || (mailProfile.getMailflag().equals(""))
				|| (mailProfile.getMailflag().equals("false")))
			return;
		logger.info("mail.from:[" + mailProfile.getFromaddr() + "]");
		logger.info("mail.to:[" + toAddrs + "]");
		String username = mailProfile.getUsername();
		String pswd = mailProfile.getPswd();
		String from = mailProfile.getFromaddr();
		ArrayList<Object> toAddrslist = new ArrayList<Object>();
		if ((toAddrs != null) && (!toAddrs.trim().equals("")) && (!toAddrs.equals("null"))) {
			int i = 0;
			for (int j = toAddrs.indexOf(59); j != -1; j = toAddrs.indexOf(59, i)) {
				if ((toAddrs.substring(i, j).matches("\\S+@(\\w+\\.)+[a-z]{2,3}"))
						&& (!toAddrslist.contains(toAddrs.substring(i, j))))
					toAddrslist.add(toAddrs.substring(i, j));
				i = j + 1;
			}
			if ((i < toAddrs.length()) && (toAddrs.substring(i).matches("\\S+@(\\w+\\.)+[a-z]{2,3}"))
					&& (!toAddrslist.contains(toAddrs.substring(i))))
				toAddrslist.add(toAddrs.substring(i));
		}
		if ((toAddrslist != null) && (!toAddrslist.isEmpty()))
			sendMail(username, pswd, from, toAddrslist, subject, content);
	}

}
