package cyiq.hql;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class HQLTest {
	private static SessionFactory sessionFactory;
	static{
		Configuration configuration = new Configuration();
		configuration.configure("cyiq/hql/hibernate.cfg.xml");
		sessionFactory = configuration.buildSessionFactory();
	}
	@Test
	public void testQueryAllClasses(){
		Session session = sessionFactory.openSession();
		
		List<Classes> classesList = session.createQuery("from Classes").list();
		for(Classes c : classesList){
			System.out.println(c.getCname());
		}
		session.close();
	}
	@Test
	public void testQuerySelectClasses(){
		Session session = sessionFactory.openSession();
		
		List<Classes> classesList = session.createQuery("select new cyiq.hql.Classes(cid,cname) from Classes").list();
		for(Classes c : classesList){
			System.out.println(c.getCid());
			System.out.println(c.getCname());
			System.out.println(c.getDescription());
		}
		session.close();
	}
	@Test
	public void testQueryOrderClasses(){
		Session session = sessionFactory.openSession();
		
		List<Classes> classesList = session.createQuery("select new cyiq.hql.Classes(cid,cname) from Classes order by cid desc").list();
		for(Classes c : classesList){
			System.out.println(c.getCid());
			System.out.println(c.getCname());
			System.out.println(c.getDescription());
		}
		session.close();
	}
	/*
	 * 一对多
	 */
	@Test
	//等值连接
	public void testSelect_Classes_student_EQ(){
		Session session = sessionFactory.openSession();
		
		List<Classes> classesList = session.createQuery("from Classes c , student s where c.cid=s.classes.cid").list();
		for(Classes c : classesList){
			System.out.println(c.getCid());
			System.out.println(c.getCname());
			System.out.println(c.getDescription());
		}
		session.close();
	}
	@Test
	//迫切等值连接
	public void testSelect_Classes_student_Fetch_EQ(){  
		Session session = sessionFactory.openSession();
		
		List<Classes> classesList = session.createQuery("from Classes c inner join fetch c.students s").list();
		for(Classes c : classesList){
			System.out.println(c.getCid());
			System.out.println(c.getCname());
			System.out.println(c.getDescription());
		}
		session.close();
	}
	
	@Test
	//迫切等值连接
	public void testSelect_student_Classes_Fetch_EQ(){  
		Session session = sessionFactory.openSession();
		
		List<Student> studentList = session.createQuery("from Student s inner join fetch s.classes c").list();
		for(Student s : studentList){
			System.out.println(s.getSid());
			System.out.println(s.getSname());
			System.out.println(s.getDescription());
		}
		session.close();
	}
	
	@Test
	//左外连接
	public void testSelect_student_Classes_LEFTJOIN(){  
		Session session = sessionFactory.openSession();
		
		List<Classes> classesList = session.createQuery("from Classes c left outer join fetch c.students").list();
		for(Classes c : classesList){
			System.out.println(c.getCid());
			System.out.println(c.getCname());
			System.out.println(c.getDescription());
		}
		session.close();
	}
}
