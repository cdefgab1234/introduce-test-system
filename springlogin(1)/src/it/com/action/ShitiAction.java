package it.com.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;

import com.jspsmart.upload.SmartUpload;

import it.com.dao.HttpRequest;
import it.com.dao.InterviewDao;
import it.com.dao.PapersDao;
import it.com.dao.StaticticDao;
import it.com.dao.UserDao;
import it.com.excel.InputOutput;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class ShitiAction implements ServletConfigAware,ServletContextAware{

    @Resource
	PapersDao paper;
	@Resource
	StaticticDao sta;
	@Resource
	InterviewDao interviewDao;
	@Resource
	PapersDao pDao;
	@Resource
	UserDao ud;
	@Resource
	HttpRequest phone;
	@Resource
	InputOutput importExcel;
	
	
	

	public PapersDao getPaper() {
		return paper;
	}

	public void setPaper(PapersDao paper) {
		this.paper = paper;
	}

	public StaticticDao getSta() {
		return sta;
	}

	public void setSta(StaticticDao sta) {
		this.sta = sta;
	}

	public InputOutput getImportExcel() {
		return importExcel;
	}

	public void setImportExcel(InputOutput importExcel) {
		this.importExcel = importExcel;
	}

	public HttpRequest getPhone() {
		return phone;
	}

	public void setPhone(HttpRequest phone) {
		this.phone = phone;
	}
	public UserDao getuDao() {
		return ud;
	}

	public void setuDao(UserDao uDao) {
		this.ud = uDao;
	}

	public PapersDao getpDao() {
		return pDao;
	}

	public void setpDao(PapersDao pDao) {
		this.pDao = pDao;
	}

	public InterviewDao getInterviewDao() {
		return interviewDao;
	}

	public void setInterviewDao(InterviewDao interviewDao) {
		this.interviewDao = interviewDao;
	}
	
	private ServletContext servletContext;  
    @Override  
    public void setServletContext(ServletContext arg0) {  
        this.servletContext = arg0;  
    }  
    private ServletConfig servletConfig;  
    @Override  
    public void setServletConfig(ServletConfig arg0) {  
        this.servletConfig = arg0;  
    } 

	
	//考生的试题
	@RequestMapping(value="getQuestions.action")
	public void findtype(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String data=request.getParameter("data");
		System.out.println(data);
		JSONObject json=JSONObject.fromObject(data);
		String diffculty  = (String) json.get("questionsdifficulty");
		String position= (String) json.get("position");
		String qusetionCount= (String) json.get("qusetionCount");
	   // Map<String, Integer> map = (Map<String,Integer>) mypos.get("qusetionType");
	    List myqusetions=pDao.findtype(position, diffculty,null,qusetionCount);
	    String  otherPos = (String) json.get("otherPos");
	    System.out.println(otherPos);
	    System.out.println(otherPos.equals("0"));
	   
		
		  PrintWriter out=response.getWriter();
	         
	      JSONArray json1=JSONArray.fromObject(myqusetions);
	      	out.print("{");
			out.print("\"myqusetions\":");
			out.println(json1);
			if (!otherPos.equals("0")) {
				JSONObject otherPosjson=JSONObject.fromObject(otherPos);
				System.out.println(otherPosjson);
			    String otherdiffculty  = (String) otherPosjson.get("thisdiffcult");
				String otherposition= (String) otherPosjson.get("position");
				String otherqusetionCount= (String) otherPosjson.get("count");
				List otherqusetions=pDao.findtype(otherposition, otherdiffculty,null,otherqusetionCount);
				System.out.println();
				JSONArray json2=JSONArray.fromObject(otherqusetions);
				out.print(",\"otherqusetions\":");
				out.println(json2);
			}

	        out.print("}"); 


      
		
	}
	
	
	//考生的登录验证
	@RequestMapping(value="start.action")
	public void logoin(HttpServletResponse response,HttpServletRequest request,HttpSession session) throws IOException {
		PrintWriter out= response.getWriter();
		String phonenum=request.getParameter("userphone");
		System.out.println(phonenum);
		List list= ud.selectuserid(phonenum);
		System.out.println(list.size());
		if (list.size()==0) {
			out.println("false");
		}else {
			out.println("success");
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
	
	}
	
	@RequestMapping(value="comfireyzm.action")
	public void comfire(HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException {
		String phonenum=request.getParameter("userphone");
		String rightyzm=session.getAttribute(phonenum).toString();
		System.out.println(rightyzm);
		String yzm =request.getParameter("yzm");
		PrintWriter out = response.getWriter();
		System.out.println(phonenum);
		List list= ud.selectuserid(phonenum);
		/*session.removeAttribute(phonenum);*/
		if(yzm.equals(rightyzm)) {
	
			
			JSONArray jsonArray=JSONArray.fromObject(list);
			out.print(jsonArray);
		}else {
			out.print("failure");
		}
	}
	
	@RequestMapping(value="nophoneyzm.action")
	public void nophoneyzm(HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException {
		String phonenum=request.getParameter("userphone");

		PrintWriter out = response.getWriter();
		System.out.println(phonenum);
		List list= ud.selectuserid(phonenum);
		System.out.println(list.size());
		if (list.size()==0) {
			out.println("false");
		}else {
			List msg= ud.selectuserid(phonenum);
			JSONArray jsonArray=JSONArray.fromObject(msg);
			out.print(jsonArray);
		}
	}
	//考试结果插入到表中
	@RequestMapping(value="insertdata.action")
	public void insertparper(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		System.out.println("===============");
		JSONObject json=JSONObject.fromObject(request.getParameter("data"));
		
		System.out.println(json);
		/*List<Map<String,String>> answerbyself=(List<Map<String, String>>) json.getJSONArray("answerbyself");*/
		List<Map<String,String>> myanswer=(List<Map<String, String>>) json.getJSONArray("myanswer");
		/*List<Map<String,String>> quesid=(List<Map<String, String>>) json.getJSONArray("quesid");*/
		int userid=(int)json.get("userid");
		int myscore=(int)json.get("myscore");
		String costTime=(String) json.get("time");
		System.out.println(costTime);
		System.out.println("用户名"+userid);
		System.out.println("分数"+myscore);
		System.out.println("考试用时"+costTime);
		System.out.println("答卷"+myanswer);
		PrintWriter out= response.getWriter();
		for (Map<String, String> map : myanswer) {
			JSONObject thislist=JSONObject.fromObject(map);
			int quesid=(int)thislist.get("quesid");
			String answerbyself=(String)thislist.get("answerbyself");
			String myanswerWords=(String) thislist.get("myanswerWords");
			System.out.println(quesid+"--"+answerbyself);
			pDao.insertparper(userid,quesid,myanswerWords,answerbyself);
		}
		interviewDao.inserttime(myscore, costTime,userid); 
		out.print("aaaaaaa");
		
	}
	
	
	//导入excel表信息
	@RequestMapping(value="ImportExcel.action")
	public void ImportExcel(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		System.out.println("进入到servlet");
		request.setCharacterEncoding("utf-8");
		//设置上传文件保存路径
		String filePath =request.getSession().getServletContext().getRealPath("/") + "excle";
		System.out.println(filePath);
		File file = new File(filePath);
		if(!file.exists()){
			file.mkdir();
		}
		
		SmartUpload su = new SmartUpload();
		//初始化对象
		su.initialize(servletConfig, request, response);
		//设置上传文件大小
		su.setMaxFileSize(1024*1024*100);
		//设置所有文件的大小
		su.setTotalMaxFileSize(1024*1024*100);
		//设置允许上传文件类型
		su.setAllowedFilesList("xls");
		String result = "上传成功！";
		//设置禁止上传的文件类型
		try {
			su.setDeniedFilesList("rar,jsp,js");
			//上传文件
			su.upload();
			
			int count = su.save(filePath);
			System.out.println("上传成功" +  count + "个文件！");
		} catch (Exception e) {
			result = "上传失败！";
			e.printStackTrace();
		}
		String filenameString ="";
		for(int i =0; i < su.getFiles().getCount(); i++){
			com.jspsmart.upload.File tempFile = su.getFiles().getFile(i);
			System.out.println("---------------------------");
			System.out.println("表单当中name属性值：" + tempFile.getFieldName());
			System.out.println("上传文件名：" + tempFile.getFieldName());
			System.out.println("上传文件长度:" + tempFile.getSize());
			System.out.println("上传文件的拓展名：" + tempFile.getFileExt());
			filenameString=tempFile.getFilePathName();
			System.out.println("上传文件的全名：" + tempFile.getFilePathName());
			System.out.println("---------------------------");
		}
				StringBuffer realpath = new StringBuffer();
		realpath.append(filePath).append("\\").append(filenameString);
		System.out.println(realpath);
		//realpath.toString().replaceAll("\\", "\\\\");
		String finalpath =realpath.toString();
		System.out.println(finalpath.replace("\\", "\\\\"));
		//String realpath = filePath+"\"+filenameString;
	/*	InputOutput test1 = new InputOutput();*/
		//test1.in2(finalpath.replace("\\", "\\\\"));
		List<Map> list=importExcel.in2(finalpath.replace("\\", "\\\\"));
        System.out.println(list);
        int count=0;
        List num=new  ArrayList();
        StringBuffer str=new StringBuffer();
        List<Map>  pos= sta.findAllPosition();
        for (Map map : pos) {
		str.append(map.get("pos"));
		}
        System.out.println("list:"+list.toString());
    	PrintWriter out =response.getWriter();
        for (Map map : list) {
        	  System.out.println("次数");
        	  System.out.println(map.get("A")==null);
        	count++;
			if(map.get("detail")==null||map.get("quesanswer")==null||map.get("diffculty")==null||map.get("position")==null||map.get("kpoint")==null||map.get("type")==null)
			{
				num.add(count);
			     continue;
			}
			
			System.out.println(str.toString());
			System.out.println(map.get("position").toString());
			System.out.println(str.toString().indexOf(map.get("position").toString()));
			if((str.toString().indexOf(map.get("position").toString()))==-1)
			{
				num.add(count);
			     continue;
			}
			System.out.println("222");
			if(map.get("type").equals("选择题"))
			{
				
				if(map.get("A")==null||map.get("B")==null||map.get("C")==null||map.get("D")==null)
				{
					 num.add(count);
					  continue;
				}
				
				if(map.get("quesanswer").toString().length()!=1||"ABCD".indexOf(map.get("quesanswer").toString())==-1)
				{
					num.add(count);
					 continue;
				}
				paper.insertAll(map.get("diffculty").toString(), map.get("position").toString(), map.get("kpoint").toString(), map.get("type").toString(), map.get("A").toString(),  map.get("B").toString(),  map.get("C").toString(),  map.get("D").toString(), map.get("detail").toString(), map.get("quesanswer").toString());
			}
			System.out.println("333");
			if(map.get("type").equals("多选题"))
			{
				
				if(map.get("A")==null||map.get("B")==null||map.get("C")==null||map.get("D")==null)
				{
					num.add(count);
					 continue;
				}
				boolean rs=Pattern.compile("^[A-D]{1,4}$").matcher(map.get("quesanswer").toString()).matches();
				if (!rs) {
					num.add(count);
					 continue;
				}

				paper.insertAll(map.get("diffculty").toString(), map.get("position").toString(), map.get("kpoint").toString(), map.get("type").toString(), map.get("A").toString(),  map.get("B").toString(),  map.get("C").toString(),  map.get("D").toString(), map.get("detail").toString(), map.get("quesanswer").toString());
			}
		
			if(map.get("type").equals("判断题"))
			{
				
				if(map.get("A")!=null||map.get("B")!=null||map.get("C")!=null||map.get("D")!=null)
				{
					num.add(count);
					 continue;
				}
				
				System.out.println(map.get("diffculty")+"-++++++++++++++++++-"+map.get("position"));
				//String diffculty,String position,String kpoint,String type,String A,String B,String C,String D,String detail,String quesanswer
				paper.insertAll(map.get("diffculty").toString(), map.get("position").toString(), map.get("kpoint").toString(), map.get("type").toString(), null,  null,  null,  null, map.get("detail").toString(), map.get("quesanswer").toString());
				
			}
		}
		JSONArray jsonArray=JSONArray.fromObject(num);
		out.print(jsonArray);
        
	}
}
