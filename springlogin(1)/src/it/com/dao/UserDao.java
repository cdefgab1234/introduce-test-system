package it.com.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;





@Repository(value="ud")
public class UserDao {
@Resource
	SqlSessionFactory sf;
	public SqlSessionFactory getSf() {
		return sf;
	}
	public void setSf(SqlSessionFactory sf) {
		this.sf = sf;
	}
	
/*===========================登录注册，忘记密码==================================================*/
		//登陆验证
		public int login(String name, String pass){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			List list = iud.login(name);
			int n=0;//用户名不存在
			if(list.size()!=0){
					String up=((Map)list.get(0)).get("pass").toString();
				    if(pass.equals(up)){
				    	n=1;//登陆成功
				    }else{
				    	n=2; //密码错误
				    }
				}
			session.commit();
			session.close();
			return n;
		}
		//验证用户是否存在
		public List selectuserOn(String name){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			List list = iud.selectuserOn(name);
			session.commit();
			session.close();
			return list;
		}
		//获取所有密保问题
		public List GetQusetion(){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			List list = iud.GetQusetion();
			session.commit();
			session.close();
			return list;
		}	
		//注册新用户
		public void InsertUser( String name,String pass, String sfzid, String phone,String email,String logo){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			iud.InsertUser(name,pass,sfzid,phone,email,logo);
			session.commit();
			session.close();
		}	
		//注册新用户密保
		public void InsertSecurity( String answer, int yzid,int nameid){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			iud.InsertSecurity(answer,yzid,nameid);
			session.commit();
			session.close();
		}	
		//查询用户信息byname
		public List<Map> FindMsg( String nameid){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			List list = iud.FindMsg(nameid);
			session.commit();
			session.close();
			return list;
		}
		//根据用户id查询用户密保问题
		public List<Map> FindQusetionByNameid( int nameid){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			List list = iud.FindQusetionByNameid(nameid);
			session.commit();
			session.close();
			return list;
		}
		//根据用户id查询用户密保问题
		public List<Map> FindAnswerByNameid( int nameid,int yzid){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			List list = iud.FindAnswerByNameid(nameid,yzid);
			session.commit();
			session.close();
			return list;
		}
		//UpdateUserMssgByYzid修改用户信息
		public void UpdateUserMsgyByYzid(String name,String sfzid,String logo,String phone,String email){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			iud.UpdateUserMsgyByYzid(name,sfzid,logo,phone,email);
			session.commit();
			session.close();
		}
		
/*=================================================模板==============================================*/
		//增加新密保问题
		public void InsertSecurityByAdmin( String security,String yzid){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			iud.InsertSecurityByAdmin(security,yzid);
			session.commit();
			session.close();
		}
		
		//删除密保问题
		public void DeleteSecurityByYzid(int yzid){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			iud.DeleteSecurityByYzid(yzid);
			session.commit();
			session.close();
		}
		//UpdateSecurityByYzid修改密保问题内容
		public void UpdateSecurityByYzid(int yzid,String security,String type){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			iud.UpdateSecurityByYzid(yzid,security,type);
			session.commit();
			session.close();
		}
/*=================================================模板==============================================*/		
		
/*===========================用户管理==================================================*/
		//遍历所有用户信息
		public  List<Map> SelectAllUsers(){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			List list=iud.SelectAllUsers();
			session.commit();
			session.close();
			return list;
		}
		//遍历所有用户信息bylimit
		public  List<Map> SelectAllUsersByLimit(int begin,int size){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			List list=iud.SelectAllUsersByLimit(begin,size);
			session.commit();
			session.close();
			return list;
		}
		public List selectuserid(String phone){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			List list = iud.selectuserid(phone);
			session.commit();
			session.close();
			return list;
		}
		//根据用户名查询手机号码
		public String selectphoneByname(String name) {
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			List<Map> list =iud.selectphoneByname(name);
			String phone =list.get(0).get("phone").toString();
			session.commit();
			session.close();
			return phone;
		}
		//根据用户名查询邮箱账号
		public String selectemailByname(String name) {
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			List<Map> list =iud.selectemailByname(name);
			String email =list.get(0).get("email").toString();
			session.commit();
			session.close();
			return email;
		}
		//找回密码修改页
		public void updatePasswordByname(String pass,String phone,String email,String sfzid,String name) {
			SqlSession session =sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			iud.updatePasswordByname(pass, phone, email, sfzid, name);
			session.commit();
			session.close();
		}
		
		public String md5(String inputStr){
	    	
			  BigInteger bigInteger=null;//高精度数据类型
			try {
				MessageDigest md = MessageDigest.getInstance("md5");//说明加密类型
				 byte[] inputData = inputStr.getBytes();//字符转换成字节
			     md.update(inputData); //加密
			     bigInteger = new BigInteger(md.digest());//转换成高精度数字类型 
			    // System.out.println("MD加密后:" + bigInteger.toString()); //输出
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	    }
			return bigInteger.toString();
	    }
}
