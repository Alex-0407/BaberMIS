package com.cqut.baber.action;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cqut.genhoo.annotation.Action;
import com.cqut.genhoo.annotation.RequestMapping;
import com.cqut.genhoo.annotation.ResponseBody;
import com.cqut.genhoo.util.RandomValidateCode;

@Action
@RequestMapping("/admin/SecurityCode")
public class SecurityCodeAction {

	
	private ByteArrayInputStream inputStream;

	@RequestMapping("/code")
	@ResponseBody
	  public void code(HttpServletRequest request,
	            HttpServletResponse response,HttpSession httpSession) {
	    	RandomValidateCode  randCode = new RandomValidateCode();
	    /*	String codeString=randCode.getRandomString(num)
	    	System.out.println("验证码："+codeString);*/
	    	//取得随机字符串放入HttpSession
		/*	httpSession.putValue("random", codeString);*/
	  
	    	randCode.getRandcode(request, response);
	       	System.out.println("验证码："+httpSession.getAttribute("RANDOMCODEKEY"));
	    }

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}
	


}
