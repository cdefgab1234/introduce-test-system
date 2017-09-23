package it.com.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import org.springframework.stereotype.Repository;


/*======================================试题管理dao================================== */
@Repository
public class PapersDao {
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
			list=session.selectList("paper.findtotal");
			session.commit();
			session.close();
			return list;
		}
		public List<Map> findAll(String diffculty,String position,String kpoint,String type,int begin,int size){
			Map map=new HashMap();
			SqlSession session = sf.openSession();
			map.put("diffculty", diffculty);
			map.put("position", position);
			map.put("kpoint", kpoint);
			map.put("type", type);
			map.put("begin", begin);
			map.put("size", size);
			List list=session.selectList("paper.findAll",map);	
			session.commit();
			session.close();
			return list;
		}
	
	public int insertAll(String diffculty,String position,String kpoint,String type,String A,String B,String C,String D,String detail,String quesanswer){
		SqlSession session = sf.openSession();
		Map map=new HashMap();		
		map.put("detail", detail);
		map.put("A", A);
		map.put("B", B);
		map.put("C", C);
		map.put("D", D);
		map.put("quesanswer", quesanswer);
		map.put("diffculty", diffculty);
		map.put("position", position);
		map.put("kpoint", kpoint);	
		map.put("type", type);
		int i=session.insert("paper.insertAll",map);
		session.commit();
		session.close();
		return i;
	}
	
	public int delete(String quesid){
		SqlSession session = sf.openSession();
		int i=sf.openSession().delete("paper.delete",quesid);
		session.commit();
		session.close();
		return i;
	}
	
	
	public int update(String diffculty,String position,String kpoint,String type,String A,String B,String C,String D,String detail,String quesanswer,String quesid) {
		Map map=new HashMap();
		SqlSession session = sf.openSession();
		map.put("detail", detail);
		map.put("A", A);
		map.put("B", B);
		map.put("C", C);
		map.put("D", D);
		map.put("quesanswer", quesanswer);
		map.put("diffculty", diffculty);
		map.put("position", position);
		map.put("kpoint", kpoint);	
		map.put("type", type);
		map.put("quesid", quesid);
		int i=session.update("paper.update", map);
		session.commit();
		session.close();
		return i;
	}
	//遍历录入试题的难度
	public List<Map> findDiffcults(){
		SqlSession session=sf.openSession();
		List<Map> list=null;
		list=session.selectList("paper.selectDiffcult");
		session.commit();
		session.close();
		return list;
	}
	/*
	 * 遍历考点,这里考点和专业是二级联动
	 */
     public List<Map> findKpoint(String position){
    	 SqlSession session=sf.openSession();
    	 List<Map> list = null;
    	 list=session.selectList("paper.selectKpoint", position);
    	 session.commit();
    	 session.close();
    	 return list;
     }
     //遍历录入试题类型
     public List<Map> findType(){
    	 SqlSession session=sf.openSession();
    	 List<Map> list = null;
    	 list = session.selectList("paper.selectType");
    	 session.commit();
    	 session.close();
    	 return list;
     }
     
     public List<Map> findtype(String position,String diffculty,String type,String num) {
 		SqlSession session=sf.openSession();
 		Map map=new HashMap();
 		map.put("diffculty", diffculty);
 		map.put("position", position);
 		map.put("type", type);
 		map.put("num", num);
 		List list= session.selectList("paper.findtype",map);
 		session.commit();
 		session.close();
 		return list;
 	}
 	
 	
 	public void insertparper(int userid,int quesid,String useranswer,String answerKey) {
 		SqlSession session=sf.openSession();
 		Map map=new HashMap();
 		map.put("userid", userid);
 		map.put("quesid", quesid);
 		map.put("useranswer", useranswer);
 		map.put("answerResult", answerKey);
		/*map.put("time", time);*/
 		session.insert("insertparper",map);
 		session.commit();
 		session.close();
 	
 	}
     
}
