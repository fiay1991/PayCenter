package com.park.paycenter.bean.mail;

/**
 * 邮件发送对象
 * @author fangct
 * created at 20170626
 */
public class MailInfo {
	private String title;//主题
	private String content;//内容
	private String to;//接收人，多个接收人间用";"隔开

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

}
