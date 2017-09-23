package it.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class InterviewDao {
	@Resource
	SqlSessionFactory sf;

	public SqlSessionFactory getSf() {
		return sf;
	}

	public void setSf(SqlSessionFactory sf) {
		this.sf = sf;
	}
	//初始化个数分页total的总个数
		public List<Map> findtotal() {
			SqlSession session=sf.openSession();
			List<Map> list=null;
			list=session.selectList("interview.findtotal");
			session.commit();
			session.close();
			return list;
		}
		public List<Map> findAll(String username,String userphone,String usersource,String email,int userid,String position,String score,String costtime,int begin,int size){
			SqlSession session= sf.openSession();
			Map map=new HashMap();
			map.put("username", username);
			map.put("userphone", userphone);
			map.put("usersource", usersource);
		    map.put("questionstype", null);
			map.put("email", email);
			map.put("userid", userid);
			map.put("position", position);
			map.put("score", score);
			map.put("costtime", costtime);
			map.put("userid", userid);
			map.put("begin", begin);
			map.put("size", size);
		
			List list=session.selectList("interview.findAll",map);	
			session.commit();
			session.close();

			return list;
		}
	
	public int insertAll(String username,String userphone,String usersource,String email,String position,String questionsdifficulty,String qusetionCount,String otherPos){
		SqlSession session= sf.openSession();
		Map map=new HashMap();
		map.put("username", username);
		map.put("userphone", userphone);
		map.put("usersource", usersource);
		map.put("email", email);
		map.put("position", position);
		map.put("questionsdifficulty", questionsdifficulty);
		map.put("qusetionCount", qusetionCount);
		map.put("otherPos", otherPos);
		int i=session.insert("interview.insertAll",map);
		session.commit();
		session.close();
	
		return i;
	}
	
	public int delete(int userid){
		SqlSession session= sf.openSession();
		int i=session.delete("interview.delete",userid);
		session.commit();
		session.close();
	
		return i;
	}
	public List findtype(String questionstype,String username,String userphone,String usersource,String email,String position,int userid) {
		SqlSession session= sf.openSession();
		Map map=new HashMap();
		map.put("username", username);
		map.put("userphone", userphone);
		map.put("usersource", usersource);
		map.put("email", email);
		map.put("position", position);
		map.put("questionstype", questionstype);
		map.put("userid", userid);
		List list=session.selectList("interview.findAll",map);
		session.commit();
		session.close();
		return list;
	}
	
	public void inserttime(int score,String costtime,int userid) {
		Map map=new HashMap();
		map.put("score",score);
		map.put("costtime", costtime);
		map.put("userid", userid);
		SqlSession session= sf.openSession();
		session.insert("interview.inserttime",map);
		session.commit();
		session.close();
	}
	//修改面试者的信息
	public void updateInterviews(String userphone,String usersource,String email,int score,int userid) {
		SqlSession session =sf.openSession();
		Map map = new HashMap();
		map.put("userphone", userphone);
		map.put("usersource", usersource);
		map.put("email", email);
		map.put("score", score);
		map.put("userid", userid);
		session.selectList("updateInterviews",map);
		session.commit();
		session.close();
	}
	//在线测试手机验证码开关状态 查询
	public String findPhoneYzState() {
		SqlSession session =sf.openSession();
		List<Map> list=session.selectList("findPhoneYzState");
		String stringState=list.get(0).get("phoneYzState").toString();
		session.commit();
		session.close();
		return stringState;
	}
	
	//在线测试手机验证码开关状态 设置
	public void updatePhoneYzState(String phoneYzState) {
		SqlSession session =sf.openSession();
		session.selectList("updatePhoneYzState",phoneYzState);
		session.commit();
		session.close();
	}
}
