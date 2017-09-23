package it.com.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import it.com.dao.InterviewDao;
import it.com.dao.PapersDao;
import it.com.dao.PowerControl;
import it.com.dao.StaticticDao;
import it.com.dao.UserDao;
import net.sf.json.JSONArray;






@Controller
public class InterviewAction {
	@Resource
    InterviewDao interview;
    @Resource
	PowerControl power;
    @Resource
	UserDao ud;
    @Resource
  	PapersDao paper;
    @Resource
	StaticticDao sta;
		public StaticticDao getSta() {
		return sta;
	}

	public void setSta(StaticticDao sta) {
		this.sta = sta;
	}
    
	public PapersDao getPaper() {
		return paper;
	}

	public void setPaper(PapersDao paper) {
		this.paper = paper;
	}

	public UserDao getUd() {
		return ud;
	}

	public void setUd(UserDao ud) {
		this.ud = ud;
	}

	public PowerControl getPower() {
		return power;
	}

	public void setPower(PowerControl power) {
		this.power = power;
	}

	public InterviewDao getInterview() {
		return interview;
	}

	public void setInterview(InterviewDao interview) {
		this.interview = interview;
	}

	@RequestMapping(value="interviewfind.action")
	public void findAll(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
		int userid=1;
		System.out.println("jinru");
		String ss=request.getParameter("userid");
		if(ss==null)
		{
			userid=0;
		}
		
		String username=request.getParameter("username");
		String userphone=request.getParameter("userphone");
		String usersource=request.getParameter("usersource");
		String position=request.getParameter("position");
		String email=request.getParameter("email");
		String score=request.getParameter("score");
		String costtime=request.getParameter("costtime");
		//获取分页所需的元素
		int begin=Integer.parseInt(request.getParameter("begin"));
		int size=Integer.parseInt(request.getParameter("size"));
		int beginNum=(begin-1)*size;
		
		//当前管理员不具备的权限
		String name=request.getParameter("name");
		List<Map> nameidList=ud.selectuserOn(name);
		//通过当前登录的用户名获得当前登录的nameid
		int nameid=(int)nameidList.get(0).get("nameid");
		//通过id查到当前登录用户不具备的权限
		List<Map> powerNull = power.findpowerNotHave(nameid);
		boolean flag1=true;
		boolean flag2=true;
		boolean flag3=true;
		boolean flag4=true;
		Boolean insert=true;
		Boolean delete=true;
		Boolean update=true;
		for (Map power : powerNull) {
			if(power.get("powerid").toString().equals("7")) {
				flag1=false;
			}
			if(power.get("powerid").toString().equals("8")) {
				flag2=false;
			}
			if(power.get("powerid").toString().equals("9")) {
				flag3=false;
			}
			if(power.get("powerid").toString().equals("10")) {
				flag4=false;
			}
			if(power.get("powerid").toString().equals("5")) {
				insert=false;
			}
			if(power.get("powerid").toString().equals("6")) {
				delete=false;
			}
			if(power.get("powerid").toString().equals("26")) {
				update=false;
			}
		}
		
		List<Map> list= interview.findAll(username, userphone, usersource, email, userid, position, score, costtime,beginNum,size);
		System.out.println(list);
		for (Map map : list) {
			if(flag1==false) {
				map.put("userphone", "******");
			}
			if(flag2==false) {
				map.put("usersource", "******");
			}
			if(flag3==false) {
				map.put("email", "******");
			}
			if(flag4==false) {
				map.put("position", "******");
			}
		}
		int total=Integer.parseInt(interview.findtotal().get(0).get("total").toString());
		
		//在线测试手机验证初始化状态 
		
		String yzmState=interview.findPhoneYzState();
		PrintWriter out=response.getWriter();
		JSONArray json1=JSONArray.fromObject(list);
		
		List papers=paper.findDiffcults();
		JSONArray json2 = JSONArray.fromObject(papers);
		List type=paper.findType();
		JSONArray json3 = JSONArray.fromObject(type);
		List poslist=sta.findAllPosition();
		JSONArray json4 = JSONArray.fromObject(poslist);
		out.print("{\"total\":"+total+",\"interview\":");
		out.println(json1);
		out.print(",\"diffcults\":");
		out.println(json2);
		out.print(",\"type\":");
		out.println(json3);
		out.print(",\"pos\":");
		out.println(json4);
		out.print(",\"delete\":"+delete);
		out.print(",\"insert\":"+insert);
		out.print(",\"update\":"+update);
		out.print(",\"yzmState\":"+yzmState);
        out.print("}");
	}
	@RequestMapping(value="updateyzmState.action")
	public void updateyzmState(HttpServletResponse response,HttpServletRequest request) throws IOException {
		String yzmstate=request.getParameter("yzmstate");
		interview.updatePhoneYzState(yzmstate);
	}
	@RequestMapping(value="interviewinsert.action")
	public void insertAll(HttpServletResponse response,HttpServletRequest request) throws IOException {
		String username=request.getParameter("username");
		String userphone=request.getParameter("userphone");
		String usersource=request.getParameter("usersource");
		String position=request.getParameter("position");
		String email=request.getParameter("email");
		String count=request.getParameter("count");
		String diffcult=request.getParameter("thisdiffcult");
		String otherpos=request.getParameter("data");
		System.out.println(username+userphone+usersource+position+email);
		System.out.println(otherpos);
		interview.insertAll(username, userphone, usersource, email, position,diffcult,count,otherpos);
		PrintWriter out=response.getWriter();
		out.print("success");		
	}
	
	@RequestMapping(value="interviewdelect.action")
	public void delete(HttpServletRequest request,HttpServletResponse response) throws IOException {
		int userid=Integer.parseInt(request.getParameter("userid"));
		int i= interview.delete(userid);
		PrintWriter out=response.getWriter();
		JSONArray jsonArray=JSONArray.fromObject(i);	
		out.print(jsonArray);
	}
	//修改面试者的信息
	@RequestMapping(value="changeInterviews.action")
	public void changeInterviews(HttpServletRequest request,HttpServletResponse response) throws IOException {
		int userid=Integer.parseInt(request.getParameter("userid"));
		String userphone=request.getParameter("userphone");
		String usersource=request.getParameter("usersource");
		String email=request.getParameter("email");
		int score=Integer.parseInt(request.getParameter("score"));
		interview.updateInterviews(userphone, usersource, email, score,userid);
		PrintWriter out=response.getWriter();	
		out.print("success");
	}
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
