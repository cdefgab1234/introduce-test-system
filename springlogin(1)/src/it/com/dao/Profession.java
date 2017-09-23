package it.com.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import org.springframework.stereotype.Repository;

@Repository
public class Profession {
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
				list=session.selectList("profession.findtotal");
				session.commit();
				session.close();
				return list;
			}
		public List<Map> findpAll(int begin,int size){
			SqlSession session = sf.openSession();
			Map map = new HashMap();
			map.put("begin", begin);
			map.put("size", size);
			List list=session.selectList("profession.findAll",map);
			
			session.commit();
			session.close();
			return list;
		}
	
	public int insertpAll(String position){
		SqlSession session = sf.openSession();
		int i=session.insert("profession.insertAll",position);
		session.commit();
		session.close();
		return i;
	}
	
	public int deletep(String position){
		SqlSession session = sf.openSession();
		int i=session.delete("profession.delete",position);
		session.commit();
		session.close();
		return i;
	}
	//删专业的试题
		public void deleteChoices(String position) {
			SqlSession session=sf.openSession();
			session.selectList("deleteChoices",position);
			session.commit();
			session.close();
		}
	//遍历专业录入的试题总数
	public int findQuesNums(String position){
		SqlSession session =sf.openSession();
		List<Map> list = null;
		list=session.selectList("profession.selectNumofQuestion",position);
		int num=Integer.parseInt(list.get(0).get("num").toString());
		session.commit();
		session.close();
		return num;
	}

}
