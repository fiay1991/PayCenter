package com.park.paycenter.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.park.paycenter.bean.mail.Table;
import com.park.paycenter.bean.mail.Td;
import com.park.paycenter.bean.mail.Th;
import com.park.paycenter.bean.mail.Tr;
import com.park.paycenter.constants.MailConstants;

/**
 * HTML工具类
 * @author fangct
 *
 */
public class HtmlTools {
	private static Logger logger = LoggerFactory.getLogger(HtmlTools.class);
	/**
	 * 生成表格的HTML格式文本
	 * @param objectList
	 * 					数据集合
	 * @param heads
	 * 					表格头部
	 * @param params
	 * 					集合的key
	 * @return
	 */
	public static String buildTableHtml(List<Object> objectList, String[] heads, String[] params){
		List<Tr> trs = new ArrayList<Tr>();
		Tr headTR = new Tr();
		List<Object> ths = new ArrayList<Object>();
		//默认增加"序号"列
		Th xh = new Th("序号");
		ths.add(xh);
		for(int i=0; i<heads.length; i++){
			Th th = new Th(heads[i]);
			ths.add(th);
		}
		
		headTR.setTds(ths);
		trs.add(headTR);
		for(int i=0; i<objectList.size(); i++){
			Object record = objectList.get(i);
			/**
			 * 发送邮件
			 */
			Tr tr = new Tr();
			try {
				@SuppressWarnings("unchecked")
				Map<String, Object> map = ObjectTool.convertBean(record);
				List<Object> tds = new ArrayList<Object>();
				Td xhTd = new Td(""+(i+1));
				tds.add(xhTd);
				for(int j=0; j<params.length; j++){
					Td td = new Td(""+map.get(params[j]));
					tds.add(td);
				}
				tr.setTds(tds);
			} catch (Exception e) {
				logger.error("对象转换Map时异常,原因：{}", e);
			}
			trs.add(tr);
		}
		Table table = new Table();
		table.setTrs(trs);
		return convertHtml(table);
	}
	
	/**
	 * 生成表格的html文本内容
	 * @param tableVO
	 * @return
	 */
	public static String convertHtml(Table tableVO){
		StringBuffer tableHtml = new StringBuffer();
		tableHtml.append("<table border=\"1px\" style=\"border-collapse:collapse;font-size:"+
						MailConstants.TABLE_FONT_SIZE+";margin-left:"+
						MailConstants.TABLE_MARGIN_LEFT+"\">");
		List<Tr> trs = tableVO.getTrs();
		for(Tr tr : trs){
			List<Object> tds = tr.getTds();
			tableHtml.append("<tr>");
			for(Object obj : tds){
				if(obj instanceof Th){
					Th th = (Th)obj;
					tableHtml.append("<th>");
					tableHtml.append(th.getText());
					tableHtml.append("</th>");
				}else{
					Td td = (Td)obj;
					tableHtml.append("<td>");
					tableHtml.append(td.getText());
					tableHtml.append("</td>");
				}
			}
			tableHtml.append("</tr>");
		}
		tableHtml.append("</table>");
		return tableHtml.toString();
	}
}
