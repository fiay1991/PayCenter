package com.park.paycenter.tools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author liuyang
 * 时间：2015年8月15日
 * 功能：处理XML的相关方法
 * 备注：
 */

public class XMLTool {
	/**
	 * 把xml字符串转成map
	 * @param xml
	 * @return
	 */
	public static Map<String, Object>xmlToMap(String xml){
		Document document;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();// 获取根节点
			for (Iterator<Element> iterator = root.elementIterator(); iterator.hasNext();) {
				Element element = iterator.next();
				if(!element.getText().equals("")){
					map.put(element.getName(), element.getText());
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return map;
	}
}
