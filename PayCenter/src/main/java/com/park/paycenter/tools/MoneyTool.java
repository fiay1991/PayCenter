package com.park.paycenter.tools;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liuyang 时间：2015年10月28日 功能：设计货币的工具 备注：
 */

public class MoneyTool {
	/**
	 * 分转换为元.
	 * 
	 * @param fen
	 *            分
	 * @return 元
	 */
	public static String fromFenToYuan(final String fen) {
		if (fen.equals("0")) {
			return "0";
		}
		String yuan = "";
		final int MULTIPLIER = 100;
		Pattern pattern = Pattern.compile("^[1-9][0-9]*{1}");
		Matcher matcher = pattern.matcher(fen);
		if (matcher.matches()) {
			yuan = new BigDecimal(fen).divide(new BigDecimal(MULTIPLIER)).setScale(2).toString();
		} else {
			System.out.println("参数格式不正确!");
		}
		return yuan;
	}

}
