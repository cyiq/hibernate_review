package cyiq.session;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import cyiq.relation.onetomany.single.bean.Student;

public class SessionTest {
	private static SessionFactory sessionFactory;
	static{
		Configuration configuration = new Configuration();
		configuration.configure("cyiq/session/hibernate.cfg.xml");
		sessionFactory = configuration.buildSessionFactory();
	}
	
	@Test
	public void testAddStudent(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		
		Student student = new Student();
		student.setSname("65666");
		student.setDescription("677676");
		session.save(student);
		
		transaction.commit();
		session.close();
	}
	
}
