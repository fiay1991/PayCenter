package com.park.paycenter.bean.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import org.springframework.util.StringUtils;

/**
 * SMTP认证
 * @author fangct
 *
 */
public class SMTPAuthenticator extends Authenticator {
	private String pass;
	private String user;

	public SMTPAuthenticator(String user, String pass) {
		this.user = user;
		this.pass = pass;
	}

	public PasswordAuthentication getPasswordAuthentication() {
		if (StringUtils.isEmpty(user) || StringUtils.isEmpty(pass))
			return null;
		return new PasswordAuthentication(user, pass);
	}
}
