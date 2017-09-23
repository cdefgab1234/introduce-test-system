package it.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;



/*=============================用户管理 dao========================*/
@Repository
public class AdminDao {
@Resource
SqlSessionFactory sf;

public SqlSessionFactory getSf() {
	return sf;
}

public void setSf(SqlSessionFactory sf) {
	this.sf = sf;
}				
	public void deletebyAdmin(int nameid) {
	   SqlSession session=sf.openSession();
	   session.selectList("deletebyadmin",nameid);
	   session.commit();
	   session.close();
	}
	public void deleteAllByAdmin(int nameid) {
		SqlSession session = sf.openSession();
		session.selectList("deleteAllByAdmin",nameid);
		session.commit();
		session.close();
	}
	public void deletePowersByadmin(int nameid) {
		SqlSession session =sf.openSession();
		session.selectList("deletePowersByadmin",nameid);
		session.commit();
		session.close();
	}
		//终极管理员修改管理员信息
		public void updatePowerstate(int nameid,String phone,String email,String sfzid,String logo) {
			SqlSession session=sf.openSession();
			Map map = new HashMap();
			map.put("nameid", nameid);
			map.put("phone", phone);
			map.put("email", email);
			map.put("sfzid", sfzid);
			map.put("logo", logo);
			session.selectList("updatePowerstate",map);
			session.commit();
			session.close();
		}
        //终极管理员修改管理员信息
		public void editPowerstate(int nameid,int powerstate) {
			SqlSession session=sf.openSession();
			Map map = new HashMap();
			map.put("nameid", nameid);
			map.put("powerstate", powerstate);
			session.selectList("it.com.dao.editPowerstate",map);
			session.commit();
			session.close();
		}
		
		
}
