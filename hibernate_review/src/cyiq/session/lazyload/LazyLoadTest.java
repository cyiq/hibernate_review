package cyiq.session.lazyload;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import cyiq.relation.onetomany.single.bean.Student;

public class LazyLoadTest {
	private static SessionFactory sessionFactory;
	static{
		Configuration configuration = new Configuration();
		configuration.configure("cyiq/session/hibernate.cfg.xml");
		sessionFactory = configuration.buildSessionFactory();
	}
	
	@Test
	//直到student.getSname时才向数据库发出select语句
	public void testLoad(){
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		Student student = (Student) session.load(Student.class, 1L);
		System.out.println("after load");
		student.getSname();
		
		transaction.commit();
	}
	
	@Test
	public void testLoadClass(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Student student = (Student) session.load(Student.class, 1L);
		
		transaction.commit();
		session.close();
		
		System.out.println("after session close");
		System.out.println(student.getSname());	//LazyInitializationException
	}
	
}
