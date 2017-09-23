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

import it.com.dao.PowerControl;
import it.com.dao.UserDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class PowerControlAction {
	@Resource
	PowerControl power;
	@Resource
	UserDao ud;
	
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
	//遍历所有的权限
	@RequestMapping(value="powerEditInit.action")
	public void findAllPowers(HttpServletRequest arg0,HttpSession session,HttpServletResponse arg1) throws IOException {
		int begin=Integer.parseInt(arg0.getParameter("begin"));
		int size=Integer.parseInt(arg0.getParameter("size"));
		int beginNum=(begin-1)*size;
		
		String name=arg0.getParameter("name");
		List<Map> nameidList=ud.selectuserOn(name);
		//通过当前登录的用户名获得当前登录的nameid
		int nameid=(int)nameidList.get(0).get("nameid");
		
		PrintWriter out=arg1.getWriter();
		int total=ud.SelectAllUsers().size();
		List users=ud.SelectAllUsersByLimit(beginNum, size);
		
		List mypower=power.findOnePower(nameid);
		
		JSONArray json1 = JSONArray.fromObject(users);
		JSONArray json3 = JSONArray.fromObject(mypower);
		
		out.print("{\"total\":"+total+",\"users\":");
		out.println(json1);
		out.print(",\"mypower\":");
		out.println(json3);
        out.print("}"); 
		

	}
	//查询当前用户的权限
	@RequestMapping(value="findOnePower.action")
	public void findOnePower(HttpServletResponse response,HttpServletRequest request) throws IOException {
		int name =Integer.parseInt(request.getParameter("nameid"));
		List<Map> list = null;
		list=power.findOnePower(name);
		PrintWriter out = response.getWriter();
		JSONArray json1 = JSONArray.fromObject(list);
		List Nopowers=power.findpowerNotHave(name);
		JSONArray json2 = JSONArray.fromObject(Nopowers);
		out.print("{");
		out.print("\"Nopowers\":");
		out.println(json2);
		out.print(",\"mypower\":");
		out.println(json1);
        out.print("}"); 
	}
	
	
	@RequestMapping(value="changepowersByNameid.action")
	public void changepowersByNameid(HttpServletRequest request,HttpServletResponse response) throws IOException {
	
		JSONObject json=JSONObject.fromObject(request.getParameter("data"));
		
		System.out.println(json);
		int nameid=(int)json.get("nameid");
		List<Map<String,Object>> myanswer=(List<Map<String, Object>>) json.getJSONArray("thispower");
		power.clearPowersByNameid(nameid);
		System.out.println(myanswer);
		if (myanswer.size()!=0) {
			
			for (Map<String, Object> map : myanswer) {
				System.out.println(map.get("powerids"));
				JSONObject json2=JSONObject.fromObject(map);
				int  thispowerid=(int) map.get("powerids");
				power.changepowersByNameid(nameid, thispowerid);
			}
		}
		
		PrintWriter out = response.getWriter();
		out.print("success");
	}
}
