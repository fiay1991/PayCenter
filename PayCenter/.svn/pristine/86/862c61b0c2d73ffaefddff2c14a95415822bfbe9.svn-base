package com.park.paycenter.profile;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "MailProfile")
public class MailProfile {
	//服务器(SMTP)
	private String mail_smtp_host;
	// (SMTP)端口
	private String mail_smtp_port;
	// 是否验证
	private String mail_smtp_auth;
	// 用户名
	private String username;
	// 密码
	private String pswd;
	// 电子邮件帐户
	private String fromaddr;
	// 回复邮件地址
	private String toaddr;
	// 是否启用
	private String mailflag;
	//项目环境
	private String environment;
	public String getMail_smtp_host() {
		return mail_smtp_host;
	}
	public void setMail_smtp_host(String mail_smtp_host) {
		this.mail_smtp_host = mail_smtp_host;
	}
	public String getMail_smtp_port() {
		return mail_smtp_port;
	}
	public void setMail_smtp_port(String mail_smtp_port) {
		this.mail_smtp_port = mail_smtp_port;
	}
	public String getMail_smtp_auth() {
		return mail_smtp_auth;
	}
	public void setMail_smtp_auth(String mail_smtp_auth) {
		this.mail_smtp_auth = mail_smtp_auth;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPswd() {
		return pswd;
	}
	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	public String getFromaddr() {
		return fromaddr;
	}
	public void setFromaddr(String fromaddr) {
		this.fromaddr = fromaddr;
	}
	public String getToaddr() {
		return toaddr;
	}
	public void setToaddr(String toaddr) {
		this.toaddr = toaddr;
	}
	public String getMailflag() {
		return mailflag;
	}
	public void setMailflag(String mailflag) {
		this.mailflag = mailflag;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
}
