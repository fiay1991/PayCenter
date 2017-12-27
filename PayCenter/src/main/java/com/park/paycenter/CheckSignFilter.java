package com.park.paycenter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.park.base.common.RSATools;
import com.park.paycenter.constants.PrivateKeyConstants;
import com.park.paycenter.enums.ErrorCode;
import com.park.paycenter.tools.BackResultTools;
/**
 * 签名过滤器
 * @author fangct
 *
 */
public class CheckSignFilter implements Filter{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response =(HttpServletResponse)resp;
		response.setContentType("text/html; charset=UTF-8");
		String url = request.getRequestURI();
        
		String params = request.getParameter("params");
		if (StringUtils.isBlank(params)) {
			logger.info("没有获取到params参数");
			response.getWriter().print(
					BackResultTools.response(ErrorCode.传入参数错误.getCode(), ErrorCode.传入参数错误.getContent(), null, ""));
			return;
		}

		String pid = request.getHeader("PID");
		boolean verify = false;
        try {
            String decryptParams = RSATools.decrypt(params, PrivateKeyConstants.THIS.getPrivateKey());
            request.setAttribute("params", decryptParams);
            verify = true;
        } catch (Exception e) {
            e.printStackTrace();
			logger.error("解密环节出现错误：URL：" + url + "错误信息" + e.toString());
        }
        if (verify) {
       		chain.doFilter(request,response);
       		return;
       	}else {
			logger.info("来自" + pid + "的访问,认证失败，请求url:" + url + "***参数：" + params);
			response.getWriter().print(
					BackResultTools.response(ErrorCode.签名验证失败.getCode(), ErrorCode.签名验证失败.getContent(), null, ""));
       		return;
       	}
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
