package com.park.paycenter.requset;

/**
 * @author liuyang
 * 时间：2015年6月5日
 * 功能：响应移动端
 * 备注：
 */

public class Response {
	private Object object;
	
	private int result_code;//提示的错误代码
	
	private String token;//为了访问安全添加token标示符

	private String content;//返回提示话语给对方
	
	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public int getResult_code() {
		return result_code;
	}

	public void setResult_code(int result_code) {
		this.result_code = result_code;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
