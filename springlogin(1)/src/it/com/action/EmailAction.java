package it.com.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.annotation.Resource;
import javax.mail.Session;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.com.dao.UserDao;
import it.com.service.EmailUser;

@Controller
public class EmailAction {
@Resource
EmailUser ee;
@Resource(name="ud")
UserDao ud;


public UserDao getUd() {
	return ud;
}

public void setUd(UserDao ud) {
	this.ud = ud;
}

public EmailUser getEe() {
	return ee;
}

public void setEe(EmailUser ee) {
	this.ee = ee;
}


//∏˘æ›” œ‰’“ªÿ√‹¬Î  ∑¢ÀÕ” º˛
@RequestMapping(value="emailtest.action")
public void email(HttpServletResponse response,HttpServletRequest request,HttpSession session) throws IOException{
	String name=request.getParameter("username");
	String email = ud.selectemailByname(name);
	session.setAttribute("emailyanzheng", name);
	session.setMaxInactiveInterval(60*10);
	
    ee.setKey(name);
	ee.smtpToEmail(email);
	PrintWriter out=response.getWriter();
	out.println("success");
}


//≈–∂œ
@RequestMapping(value="emailyanzheng.action")
public ModelAndView emailyanzheng(HttpSession session,HttpServletRequest request,HttpServletResponse response) {
	String emailyanzheng=session.getAttribute("emailyanzheng").toString();
	
	String emailparam=request.getParameter("key");
	System.out.println(emailyanzheng+"hhh");
	System.out.println(emailparam+"llll");
	if(emailparam.toString().equals(emailyanzheng))
	{
		System.out.println("==============");
		return new ModelAndView("modifyPassword.html?name="+emailparam);
	}
	else {
		System.out.println("**************");
		return new ModelAndView("zzz.html");
	}
	
}
	
}
