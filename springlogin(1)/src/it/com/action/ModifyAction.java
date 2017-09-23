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
import it.com.dao.PapersDao;
import it.com.dao.StaticticDao;
import it.com.dao.modifyDao;
import net.sf.json.JSONArray;





@Controller
public class ModifyAction {
    @Resource
    modifyDao modify;

	public modifyDao getModify() {
		return modify;
	}

	public void setModify(modifyDao modify) {
		this.modify = modify;
	}
    @RequestMapping("searchInit.action")
	public void SearchInit(HttpServletResponse response,HttpServletRequest request) throws IOException {
		PrintWriter out= response.getWriter();
		//遍历所有专业
		List ipos= modify.FindAllPos();
		JSONArray json1=JSONArray.fromObject(ipos);
		//遍历所有试题类型
		List itypes= modify.FindAllQusetionType();
		JSONArray json2=JSONArray.fromObject(itypes);
		//遍历所有试题类型
		/*List itypes= modify.FindAllQusetionType();
		JSONArray json2=JSONArray.fromObject(itypes);
		out.print(jsonArray);*/
	
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
