package cyiq.session.cache;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import cyiq.relation.onetomany.single.bean.Classes;

public class OneCacheTest {
	private static SessionFactory sessionFactory;
	static{
		Configuration configuration = new Configuration();
		configuration.configure("cyiq/session/hibernate.cfg.xml");
		sessionFactory = configuration.buildSessionFactory();
	}
	
	@Test
	//只有一条select语句产生，因为第二次get没有对数据库进行操作，而是从session中取出了持久化对象
	public void testGet(){
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Classes c = (Classes) session.get(Classes.class, 1L);
		session.get(Classes.class, 1L);
		
		transaction.commit();
	}
	
	@Test
	//检测修改，发出update语句
	public void testFlush(){
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Classes c1 = (Classes) session.get(Classes.class, 1L);
		Classes c2 = (Classes) session.get(Classes.class, 2L);
		Classes c3 = (Classes) session.get(Classes.class, 3L);
		c1.setCname("3");
		session.flush();
		transaction.commit();
	}
}
