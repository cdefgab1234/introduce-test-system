package it.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

		@Repository
		public class PowerControl {
		@Resource
		SqlSessionFactory sf;
		
		public SqlSessionFactory getSf() {
			return sf;
		}
		
		public void setSf(SqlSessionFactory sf) {
			this.sf = sf;
		}
		//查询当前nameid没有的权限
		public List<Map> findpowerNotHave(int nameid) {
			SqlSession session =sf.openSession();
			List<Map> list = null;
			list=session.selectList("powerState.selectNotHave",nameid);
			session.commit();
			session.close();
			return list;
		}
		//遍历所有的权限
		public List<Map> findAllPowers(){
			SqlSession session =sf.openSession();
			List<Map> list=null;
			list = session.selectList("selectAllPower");
			session.commit();
			session.close();
			return list;
		}
		//查询当前用户的权限
		public List<Map> findOnePower(int nameid){
			SqlSession session=sf.openSession();
			List<Map> list= null;
			list=session.selectList("selectonePower",nameid);
			session.commit();
			session.close();
			return list;
		}
		public void clearPowersByNameid(int nameid) {
			SqlSession session =sf.openSession();
			session.selectList("deletepowerBynameid",nameid);
			session.commit();
			session.close();
		}

		public void changepowersByNameid(int nameid,int powerid) {
			SqlSession session =sf.openSession();
			Map map = new HashMap();
			map.put("nameid", nameid);
			map.put("powerid", powerid);
			session.selectList("insertpowersBynameid",map);
			session.commit();
			session.close();
		}
}
