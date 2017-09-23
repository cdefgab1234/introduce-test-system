package it.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.SessionScope;

@Repository
public class StaticticDao {
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
		list=session.selectList("Statictic.findtotal");
		session.commit();
		session.close();
		return list;
	}
	/*
	 * 遍历所有提交成绩的面试者信息
	 * 就是score不为null
	 */
	public List<Map> selectInterview(int begin,int size){
		SqlSession session = sf.openSession();
		List<Map> list=null;
		Map map = new HashMap();
		map.put("begin", begin);
		map.put("size", size);
		list=session.selectList("Statictic.selectInterview",map);
		session.commit();
		session.close();
		return list;
	}
	 /*
	  * 遍历所有面试者信息,用于点击查看
	  */
	public List<Map> selectAllInterview() {
		SqlSession session=sf.openSession();
		List<Map> list = null;
		list=session.selectList("Statictic.selectAllInterview");
		session.commit();
		session.close();
		return list;
	}
	
	/*
	 * 动态sql三合一测试版
	 * 查询 score，position，usersource三选几
	 * 当不选择某一项是让其等于null即可
	 */
	public List<Map> selectAll(int score,String position,String usersource,String username){
		SqlSession session=sf.openSession();
		List<Map> list = null;
		Map map = new HashMap();
		map.put("score", score);
		map.put("position", position);
		map.put("usersource", usersource);
		map.put("username", username);
		list=session.selectList("Statictic.selectAll",map);
		session.commit();
		session.close();
		return list;
	}
	/*
	 * 遍历面试者所有的来源,去重后
	 */
	public List<Map> findAllSource(){
		SqlSession session=sf.openSession();
		List<Map> list=null;
		list=session.selectList("Statictic.selectBySource");
		session.commit();
		session.close();
		return list;
	}
	/*
	 * 遍历所有的专业，去重后
	 */
	public List<Map> findAllPosition(){
		SqlSession session = sf.openSession();
		List<Map> list = null;
		list=session.selectList("Statictic.selectAllPosition");
		session.commit();
		session.close();
		return list;
	}

}
