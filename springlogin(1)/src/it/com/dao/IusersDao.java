package it.com.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface IusersDao {
	
	//登录注册，忘记密码
	//登陆验证
	@Select("select * from user where name=#{name}")
	public List <Map> login(@Param("name") String name);
	//获取所有验证问题
	@Select("select * from security")
	public List <Map> GetQusetion();
	//验证用户名是否已被注册
	@Select("select * from user where name=#{name}")
	public List <Map> selectuserOn(@Param("name") String name);
	//注册新用户
	@Insert("insert into user (pass,name,sfzid,phone,email,logo) values(#{pass},#{name},#{sfzid},#{phone},#{email},#{logo})")
	public void InsertUser(@Param("name") String name,@Param("pass") String pass,@Param("sfzid") String sfzid,@Param("phone") String phone,@Param("email") String email,@Param("logo") String logo);
	//注册新用户密保问题
	@Insert("insert into securityuser (answer,yzid,nameid) values(#{answer},#{yzid},#{nameid})")
	public void InsertSecurity(@Param("answer") String answer,@Param("yzid") int yzid,@Param("nameid") int nameid);
	//根据用户名查询用户信息
	@Select("select * from user where name=#{name}")
	public List <Map> FindMsg(@Param("name") String name);
	//根据用户id查询用户密保问题和答案
	@Select("SELECT su.yzid,s.Security FROM securityuser su INNER JOIN SECURITY s ON  s.yzid=su.yzid WHERE nameid=#{nameid}")
	public List <Map> FindQusetionByNameid(@Param("nameid") int nameid);
	//根据用户id查询用户密保问题和答案
	@Select("SELECT su.answer FROM securityuser su INNER JOIN SECURITY s ON  s.yzid=su.yzid WHERE nameid=#{nameid} and su.yzid=#{yzid}")
	public List <Map> FindAnswerByNameid(@Param("nameid") int nameid,@Param("yzid") int yzid);
	//增加新密保问题
	@Insert("insert into security (Security,type) values(#{Security},#{type})")
	public void InsertSecurityByAdmin(@Param("Security") String Security,@Param("type") String type);
	//删除密保问题
	@Delete("delete from security where yzid=#{yzid}")
	public void DeleteSecurityByYzid(@Param("yzid") int yzid);
	//修改密保问题
	@Update("update security set Security=#{Security},type=#{type} where yzid=#{yzid}  ")
	public void UpdateSecurityByYzid(@Param("yzid") int yzid,@Param("Security") String Security,@Param("type") String type);
	
/*	========================================//模块界面用户信息初始化=====================*/
	//修改密保问题
	@Update("update security set sfzid=#{sfzid},logo=#{logo},email=#{email},phone =#{phone} where name=#{name}")
	public void UpdateUserMsgyByYzid(@Param("name") String name,@Param("sfzid") String sfzid,@Param("logo") String logo,@Param("phone") String phone,@Param("email") String email);

	//用户管理
	//查询所有用户
	@Select("select * from user where powerstate!=3")
	public List <Map> SelectAllUsers();
	
	//查询所有用户
	@Select("select * from user where powerstate!=3 limit #{begin},#{size}")
	public List <Map> SelectAllUsersByLimit(@Param("begin") int begin,@Param("size") int size);
	@Select("select * from interview where userphone=#{phone}")
	public List <Map> selectuserid(@Param("phone") String phone);
	
	//根据用户名查询手机号码
	@Select("SELECT phone FROM user WHERE name=#{name}")
	public List<Map> selectphoneByname(@Param("name") String name); 
	//根据用户名查询邮箱账号
	@Select("SELECT email FROM user WHERE name=#{name}")
	public List<Map> selectemailByname(@Param("name") String name);
	//找回密码修改页
	@Update("UPDATE user SET pass=#{pass},phone=#{phone},email=#{email},sfzid=#{sfzid} WHERE name=#{name}")
	public void updatePasswordByname(@Param("pass") String pass,@Param("phone") String phone,@Param("email") String email,@Param("sfzid") String sfzid,@Param("name") String name);
	
}
