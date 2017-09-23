package it.com.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.com.dao.PowerControl;
import it.com.dao.StaticticDao;
import it.com.dao.UserDao;
import net.sf.json.JSONArray;

@Controller
public class StaticticAction {
	@Resource
	StaticticDao sta;
	@Resource
	UserDao ud;
	@Resource
	PowerControl power;
	/*
	 * 遍历所有提交成绩的面试者信息
	 * 就是score不为null
	 */

		public StaticticDao getSta() {
		return sta;
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

	public void setSta(StaticticDao sta) {
		this.sta = sta;
	}
	/*
	 * 动态sql三合一测试版
	 * 查询 score，position，usersource三选几
	 * 当不选择某一项是让其等于null即可
	 */
	@RequestMapping(value="selectInterview.action")
    public void selectInterview(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String score1=request.getParameter("score");
		String position = request.getParameter("position");
		String usersource = request.getParameter("usersource");
		String username = request.getParameter("username");
		int score;
		if (score1=="") {
			score=0;
		}else {
			score=Integer.parseInt(score1);
		}
		if(position=="") {
			position=null;
		}
		if(username=="") {
			username=null;
		}
		
		if(usersource=="") {
			usersource=null;
		}
		System.out.println("===================================");
		System.out.println(score+"------------ "+position+"----- "+usersource);
		List<Map> list=null;
		list=sta.selectAll(score, position, usersource,username);
		JSONArray jsonArray = JSONArray.fromObject(list);
		PrintWriter out=response.getWriter();
		out.println(jsonArray);
	}
	/*
	 * 遍历面试者所有的来源,去重后
	 */
	@RequestMapping(value="findAllSource.action")
	public void findAllSource(HttpServletRequest request,HttpServletResponse response) throws IOException {
		List<Map> list = null;
		list=sta.findAllSource();
		JSONArray jsonArray = JSONArray.fromObject(list);
		PrintWriter out = response.getWriter();
		out.println(jsonArray);
	}
	/*
	 * 遍历所有可选的专业,去重后
	 */
	@RequestMapping(value="findAllPosition.action")
	public void findAllPosition(HttpServletRequest request,HttpServletResponse response) throws IOException {
		List<Map> list=null;
		list=sta.findAllPosition();
		JSONArray jsonArray = JSONArray.fromObject(list);
		PrintWriter out = response.getWriter();
		out.println(jsonArray);
	}
	/*
     * 初始化
     */
	@RequestMapping(value="selectNotNull.action")
	public void findNotNull(HttpServletRequest request,HttpServletResponse response) throws IOException {
		List<Map> list=null;
		//获取分页参数
		int begin=Integer.parseInt(request.getParameter("begin"));
		int size=Integer.parseInt(request.getParameter("size"));
		int beginNum=(begin-1)*size;
		list=sta.selectInterview(beginNum,size);
		/*	专业*/
		List<Map> pos=sta.findAllPosition();
		/*来源*/
		List<Map> from=sta.findAllSource();
		
		int total=Integer.parseInt(sta.findtotal().get(0).get("total").toString());
		System.out.println(total+"-----------------");
		
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
		boolean select=true;
		boolean outExcel=true;
		//遍历没有的权限
		for (Map power : powerNull) {
			if(power.get("powerid").toString().equals("22")) {
				flag1=false;
			}
			if(power.get("powerid").toString().equals("23")) {
				flag2=false;
			}
			if(power.get("powerid").toString().equals("24")) {
				flag3=false;
			}
			if(power.get("powerid").toString().equals("25")) {
				flag4=false;
			}
			if(power.get("powerid").toString().equals("21")) {
				select=false;
			}	
			if(power.get("powerid").toString().equals("29")) {
				outExcel=false;
			}
		}
		for (Map map : list) {
			if(flag1==false) {
				map.put("usersource", "******");
			}
			if(flag2==false) {
				map.put("position", "******");
			}
			if(flag3==false) {
				map.put("score", "******");
			}
			if(flag4==false) {
				map.put("costtime", "******");
			}
		}				
		PrintWriter out=response.getWriter();
		JSONArray json1=JSONArray.fromObject(list);
		JSONArray json2=JSONArray.fromObject(pos);
		JSONArray json3=JSONArray.fromObject(from);
		System.out.println(json1);
		out.print("{\"total\":"+total+",\"statics\":");
		out.println(json1);
		out.print(",\"pos\":");
		out.println(json2);
		out.print(",\"source\":");
		out.println(json3);
		out.print(",\"select\":");
		out.println(select);
		out.print(",\"outExcel\":");
		out.println(outExcel);
        out.print("}");
	}
	/*
     * 遍历所有面试者信息,用于点击查看
     */
	@RequestMapping(value="findinformation.action")
	public void findinformation(HttpServletRequest request,HttpServletResponse response) throws IOException {
		List<Map> list=null;
		list=sta.selectAllInterview();
		JSONArray jsonArray = JSONArray.fromObject(list);
		PrintWriter out=response.getWriter();
		out.println(jsonArray);
	}
}
