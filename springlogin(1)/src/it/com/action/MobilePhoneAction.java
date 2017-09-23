package it.com.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import it.com.dao.HttpRequest;
import it.com.dao.UserDao;

@Controller
public class MobilePhoneAction {
@Resource
HttpRequest phone;
@Resource(name="ud")
UserDao ud;

public UserDao getUd() {
	return ud;
}

public void setUd(UserDao ud) {
	this.ud = ud;
}

public HttpRequest getPhone() {
	return phone;
}

public void setPhone(HttpRequest phone) {
	this.phone = phone;
}
//短信密码找回
@RequestMapping(value="yanzhengByphone.action")
public void yanzhengByphone(HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException {
	System.out.println("进去短信验证：");
	PrintWriter out= response.getWriter();
	String name=request.getParameter("username");
	String phonenum=ud.selectphoneByname(name);
	//String phonenum=request.getParameter("myphone");

	Random random = new Random();
	StringBuffer randomnums = new StringBuffer();
	for(int i=0;i<6;i++) {
		randomnums.append(random.nextInt(9));
	}
	session.setAttribute(phonenum, randomnums);
	session.setMaxInactiveInterval(60*10);
	/*StringBuffer last = new StringBuffer();
	last.append("验证码：").append(randomnums).append(",仅10分钟内有效请勿泄露给他人！");*/
	phone.sendPost("http://service2.winic.org/Service.asmx/SendMessages", "uid=shanxihuaxin&pwd=hxzy2008&tos="+phonenum+"&msg="+randomnums+"&otime=");  
	  
}
@RequestMapping(value="comfirefindpass.action")
public void comfire(HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException {
	String name=request.getParameter("username");
	String phonenum=ud.selectphoneByname(name);
	
	String rightyzm=session.getAttribute(phonenum).toString();
	session.removeAttribute("phonenum");
	System.out.println(rightyzm);
	
	String yzm =request.getParameter("yzm");
	
	PrintWriter out = response.getWriter();
	if(yzm.equals(rightyzm)) {
		out.print("success");
	}else {
		out.print("failure");
	}
}
}
