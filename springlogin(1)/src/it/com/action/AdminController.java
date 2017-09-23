package it.com.action;


import it.com.dao.AdminDao;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class AdminController{
@Resource
AdminDao ad;


	public AdminDao getUd() {
	return ad;
}
public void setUd(AdminDao ud) {
	this.ad = ad;
}
	
/*======================================�û�����  ��ʼ======================================================*/
		@RequestMapping(value="deletebyAdmin.action")
		public void  deletebyAdmin(HttpServletRequest request,HttpServletResponse arg1) throws IOException {
			//��ȡҪɾ������Ա��name,�ռ�����Աadmin���ܱ�ѡȡ
			int nameid =Integer.parseInt(request.getParameter("nameid"));
			System.out.println("==============="+nameid);
			//������������λ�ò��ܵߵ���������ɾ���������������ɾ������
			ad.deleteAllByAdmin(nameid);
			ad.deletePowersByadmin(nameid);
			ad.deletebyAdmin(nameid);	
			PrintWriter out=arg1.getWriter();
			out.println("success");
		}
		
		@RequestMapping(value="updatePowerstate.action")
		public void updatePowerstate(HttpServletRequest arg0,HttpServletResponse arg1) throws IOException {
			int nameid =Integer.parseInt(arg0.getParameter("nameid"));
			String phone=arg0.getParameter("phone");
			String email=arg0.getParameter("email");
			String sfzid=arg0.getParameter("sfzid");
			String logo=arg0.getParameter("logo");
			
			ad.updatePowerstate(nameid,phone, email, sfzid, logo);
			PrintWriter out=arg1.getWriter();
			out.println("success");
		}
		//�ռ�����Ա����Ȩ��
		@RequestMapping(value="editPowerstate.action")
		public void editPowerstate(HttpServletRequest request,HttpServletResponse response) throws IOException {
			int nameid=Integer.parseInt(request.getParameter("nameid"));
			int powerstate = Integer.parseInt(request.getParameter("powerstate"));
			System.out.println(powerstate+"     xxxxx");
			if (powerstate!=0) {
				ad.editPowerstate(nameid, powerstate);
			}
			PrintWriter out= response.getWriter();
			out.println("success");
		}
		
	
}
