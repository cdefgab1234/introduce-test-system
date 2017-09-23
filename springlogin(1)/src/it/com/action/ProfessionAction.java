package it.com.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.com.dao.PowerControl;
import it.com.dao.Profession;
import it.com.dao.UserDao;
import net.sf.json.JSONArray;

@Controller
public class ProfessionAction {
	@Resource
	Profession pro;
	@Resource
	UserDao ud;
	@Resource
	PowerControl power;
	
	public PowerControl getPower() {
		return power;
	}

	public void setPower(PowerControl power) {
		this.power = power;
	}

	public UserDao getUd() {
		return ud;
	}

	public void setUd(UserDao ud) {
		this.ud = ud;
	}

	public Profession getPro() {
		return pro;
	}

	public void setPro(Profession pro) {
		this.pro = pro;
	}


	@RequestMapping(value="profind.action")
	public void findAll(HttpServletResponse response,HttpServletRequest request) throws IOException{
		//获取分页参数
		int begin=Integer.parseInt(request.getParameter("begin"));
		int size=Integer.parseInt(request.getParameter("size"));
		int beginNum=(begin-1)*size;
		
		List<Map> list= pro.findpAll(beginNum, size);
		System.out.println(list);
		int total=Integer.parseInt(pro.findtotal().get(0).get("total").toString());
		List<Map> aa=new ArrayList();
		Map professionNum = new HashMap();
		
		//当前管理员不具备的权限
		String name=request.getParameter("name");
		List<Map> nameidList=ud.selectuserOn(name);
		//通过当前登录的用户名获得当前登录的nameid
		int nameid=(int)nameidList.get(0).get("nameid");
		//通过id查到当前登录用户不具备的权限
		List<Map> powerNull = power.findpowerNotHave(nameid);
		boolean flag1=true;//创建时间
		boolean flag2=true;//试题数量
		boolean insert=true;
		boolean delete=true;
		//遍历没有的权限
		for (Map power : powerNull) {
			if(power.get("powerid").toString().equals("13")) {
				flag1=false;
			}
			if(power.get("powerid").toString().equals("14")) {
				flag2=false;
			}
			if(power.get("powerid").toString().equals("11")) {
				insert=false;
			}
			if(power.get("powerid").toString().equals("12")) {
				delete=false;
			}
		}
		System.out.println(list);
		for (Map map : list) {
			if(flag1==false) {
				map.put("edittime", "******");
			}
		}
		
		for (Map<String,String> map1 : list) {
			Map map = new HashMap();
			map.put("Quescounts", pro.findQuesNums(map1.get("pos")));
			aa.add(map);
		}

		for (Map map : aa) {
			if(flag2==false) {
				map.put("Quescounts", "******");
			}
		}
        
		PrintWriter out=response.getWriter();
		JSONArray json1=JSONArray.fromObject(list);
		JSONArray json2=JSONArray.fromObject(aa);
		System.out.println(json1);
		out.print("{\"total\":"+total+",\"pos\":");
		out.println(json1);
		out.print(",\"posNums\":");
		out.println(json2);
		out.print(",\"insert\":");
		out.println(insert);
		out.print(",\"delete\":");
		out.println(delete);
        out.print("}"); 
	}
	
	
	
	@RequestMapping(value="proinsert.action")
	public void insertpAll(HttpServletResponse response,HttpServletRequest request) throws IOException{
		
		String pos=request.getParameter("pos");
		System.out.println(""+pos);
		int i=pro.insertpAll(pos);
		PrintWriter out=response.getWriter();
		out.print(i);		
	}
	
	@RequestMapping(value="prodelect.action")
	public void delete(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String pos=request.getParameter("pos");
		pro.deleteChoices(pos);
		int i= pro.deletep(pos);
		PrintWriter out=response.getWriter();
		JSONArray jsonArray=JSONArray.fromObject(i);	
		System.out.println(pos);
		out.print(jsonArray);
	}

	
	
}
