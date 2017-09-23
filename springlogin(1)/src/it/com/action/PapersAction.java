package it.com.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.com.dao.InterviewDao;
import it.com.dao.PapersDao;
import it.com.dao.PowerControl;
import it.com.dao.StaticticDao;
import it.com.dao.UserDao;
import net.sf.json.JSONArray;





@Controller
public class PapersAction {
    @Resource
	PapersDao paper;
    @Resource
	PowerControl power;
    @Resource
	UserDao ud;
    @Resource
    InterviewDao interview;
    
    
	public InterviewDao getInterview() {
		return interview;
	}

	public void setInterview(InterviewDao interview) {
		this.interview = interview;
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

	public PapersDao getPaper() {
		return paper;
	}

	public void setPaper(PapersDao paper) {
		this.paper = paper;
	}
	
	@Resource
	StaticticDao sta;
		public StaticticDao getSta() {
		return sta;
	}

	public void setSta(StaticticDao sta) {
		this.sta = sta;
	}
		
		
	//初始化遍历
			@RequestMapping(value="papersfind.action")
			public void findAll(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
				int num=20;
				System.out.println("jinru");
				//获取分页参数
				int begin=Integer.parseInt(request.getParameter("begin"));
				int size=Integer.parseInt(request.getParameter("size"));
				int beginNum=(begin-1)*size;
			 
				List<Map> list= paper.findAll(null,null,null,null,beginNum,size);
				int total=Integer.parseInt(paper.findtotal().get(0).get("total").toString());
				System.out.println(list);
				//当前管理员不具备的权限
				String name=request.getParameter("name");
				List<Map> nameidList=ud.selectuserOn(name);
				//通过当前登录的用户名获得当前登录的nameid
				int nameid=(int)nameidList.get(0).get("nameid");
				//通过id查到当前登录用户不具备的权限
				List<Map> powerNull = power.findpowerNotHave(nameid);
				boolean flag1=true;
				boolean flag2=true;
				boolean insert=true;
				boolean delete=true;
				boolean select=true;
				boolean update=true;
				boolean ImportExcel=true;
				boolean outExcel=true;
				//遍历没有的权限
				for (Map power : powerNull) {
					if(power.get("powerid").toString().equals("19")) {
						flag1=false;
					}
					if(power.get("powerid").toString().equals("20")) {
						flag2=false;
					}
					if(power.get("powerid").toString().equals("15")) {
						insert=false;
					}
					if(power.get("powerid").toString().equals("16")) {
						update=false;
					}
					if(power.get("powerid").toString().equals("17")) {
						delete=false;
					}
					if(power.get("powerid").toString().equals("18")) {
						select=false;
					}
					if(power.get("powerid").toString().equals("27")) {
						ImportExcel=false;
					}
					if(power.get("powerid").toString().equals("28")) {
						outExcel=false;
					}
				}
				System.out.println(list);
				for (Map map : list) {
					if(flag1==false) {
						map.put("detail", "******");
					}
					if(flag2==false) {
						map.put("type", "******");
					}
				}
				PrintWriter out=response.getWriter();
				JSONArray json1=JSONArray.fromObject(list);
				System.out.println(json1);
				List papers=paper.findDiffcults();
				JSONArray json2 = JSONArray.fromObject(papers);
				List type=paper.findType();
				JSONArray json3 = JSONArray.fromObject(type);
				List poslist=sta.findAllPosition();
				JSONArray json4 = JSONArray.fromObject(poslist);
				
				out.print("{\"total\":"+total+",\"questions\":");
				out.println(json1);
				out.print(",\"diffcults\":");
				out.println(json2);
				out.print(",\"type\":");
				out.println(json3);
				out.print(",\"pos\":");
				out.println(json4);
				out.print(",\"insert\":");
				out.println(insert);
				out.print(",\"update\":");
				out.println(update);
				out.print(",\"delete\":");
				out.println(delete);
				out.print(",\"select\":");
				out.println(select);
				out.print(",\"ImportExcel\":");
				out.println(ImportExcel);
				out.print(",\"outExcel\":");
				out.println(outExcel);
		        out.print("}"); 
			}
	@RequestMapping(value="papersinsert.action")
	public void insertAll(HttpServletResponse response,HttpServletRequest request) throws IOException{
		String detail=request.getParameter("detail");
		String A=request.getParameter("A");
		String B=request.getParameter("B");
		String C=request.getParameter("C");
		String D=request.getParameter("D");
		String quesanswer=request.getParameter("quesanswer");
		String diffculty=request.getParameter("diffculty");
		String position=request.getParameter("position");
		String kpoint=request.getParameter("kpoint");
		String type=request.getParameter("type");
		System.out.println(detail+"==================");
		int ss=paper.insertAll(diffculty, position, kpoint, type, A, B, C, D, detail, quesanswer);
		PrintWriter out=response.getWriter();
		out.print(ss);		
	}
	
	@RequestMapping(value="papersdelect.action")
	public void delete(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String quesid=request.getParameter("quesid");
		System.out.println(quesid);
		int i= paper.delete(quesid);
		PrintWriter out=response.getWriter();
		JSONArray jsonArray=JSONArray.fromObject(i);	
		System.out.println(quesid);
		out.print(jsonArray);
	}
	
	//更新考试试题
	@RequestMapping(value="papersupdate.action")
	public void update(HttpServletResponse response,HttpServletRequest request) throws IOException{
		System.out.println("进入"+"====================================");
		String detail=request.getParameter("detail");
		String A=request.getParameter("A");
		String B=request.getParameter("B");
		String C=request.getParameter("C");
		String D=request.getParameter("D");
		String quesanswer=request.getParameter("quesanswer");
		String diffculty=request.getParameter("diffculty");
		String position=request.getParameter("position");
		String kpoint=request.getParameter("kpoint");
		String type=request.getParameter("type");	
		String quesid=request.getParameter("quesid");
		System.out.println("==============="+quesid);
		int i=paper.update(diffculty, position, kpoint, type, A, B, C, diffculty, detail, quesanswer, quesid);
		PrintWriter out=response.getWriter();
		out.println(i);
		
	}
	
	
	//动态查询试题
	@RequestMapping(value="searchPaper.action")
	public void SearchPaper(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
		int num=20;
		String diffculty=request.getParameter("diffculty");
		String position=request.getParameter("position");
		String kpoint=request.getParameter("kpoint");
		String type=request.getParameter("type");
		if (diffculty=="") {
			diffculty=null;
		}
		if (position=="") {
			position=null;
		}
		if (kpoint=="") {
			kpoint=null;
		}
		if (type=="") {
			type=null;
		}
		//获取分页参数
		int begin=Integer.parseInt(request.getParameter("begin"));
		int size=Integer.parseInt(request.getParameter("size"));
		int beginNum=(begin-1)*size;
	    System.out.println(begin+"################"+size);
		List list= paper.findAll(diffculty,position,kpoint,type,beginNum,size);
		int total=list.size();
		System.out.println(total+"查到记录");
		PrintWriter out=response.getWriter();
		JSONArray jsonArray=JSONArray.fromObject(list);
	/*	System.out.println(jsonArray);
		out.print(jsonArray);*/
		
		out.print("{\"searchtotal\":"+total+",\"searchQusetions\":");
		out.println(jsonArray);
        out.print("}"); 
	}

	//遍历考点,这里考点和专业是二级联动
	@RequestMapping(value="findKpoint.action")
	public void findKpoint(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String position = request.getParameter("pos");
		PrintWriter out=response.getWriter();
		if(position=="") {
			position=null;
			out.print(" 空");
		}else {
			List<Map> list=paper.findKpoint(position);
			JSONArray jsonArray = JSONArray.fromObject(list);
			
			out.print(jsonArray);
		}
		
	}

	//在线考试发送验证码状态查询
	@RequestMapping(value="selectyzmState.action")
	public void selectyzmState(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//在线测试手机验证初始化状态 
		String yzmState=interview.findPhoneYzState();
		PrintWriter out=response.getWriter();
		out.print(yzmState);
	}
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
