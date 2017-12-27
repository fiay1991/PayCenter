package com.park.paycenter.tools;

import com.park.base.common.HttpTools;
import com.park.base.common.constants.PublicKeyConstants;

public class Tools {

	/**
	 * 访问其他项目
	 * @param param
	 * @param requestURL
	 * @param project
	 * @return
	 */
	public static String requestBrotherProject(Object param, String requestURL, PublicKeyConstants project){
		return HttpTools.pidPost(param, requestURL, project, PublicKeyConstants.PayCenter.toString());
	}
}
