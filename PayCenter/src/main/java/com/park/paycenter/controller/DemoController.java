package com.park.paycenter.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.paycenter.domain.Demo;
import com.park.paycenter.service.DemoService;

/**
 * @author liuyang 时间：2016年3月24日 功能： 备注：
 */
@Controller
@RequestMapping("DemoController")
public class DemoController {

	@Resource(name = "DemoServiceImpl")
	private DemoService demoService;

	@ResponseBody
	@RequestMapping("/findAll")
	public List<Demo> findAll() {
		return demoService.findAll();
	}

	@ResponseBody
	@RequestMapping("/scan")
	public void scan(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// String contents = "http://192.168.1.199:8080/DemoController/dencoder?code=测试";
		//
		// Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		//
		// hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		//
		// BitMatrix matrix = null;
		//
		// try {
		//
		// matrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, 300, 300, hints);
		//
		// } catch (WriterException e) {
		//
		// e.printStackTrace();
		//
		// }
		//
		// //BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);
		//
		// MatrixToImageWriter.writeToPath(matrix, "png", FileSystems.getDefault().getPath("/Users/liuyang/Desktop/", "ceshi.png"));
		// File file = new File("/Users/liuyang/Desktop/ceshi.png");
		// FileInputStream inputStream = new FileInputStream(file);
		// byte[] data = new byte[(int)file.length()];
		// inputStream.read(data);
		// inputStream.close();
		// response.setContentType("image/jpeg");
		// OutputStream stream;
		// stream = response.getOutputStream();
		// stream.write(data);
		// stream.flush();
		// stream.close();
	}

	@ResponseBody
	@RequestMapping(value = "/dencoder", produces = "text/html;charset=UTF-8")
	public String dencoder(@RequestParam("code") String code) {
		System.out.println(code);
		return code;
	}
}
