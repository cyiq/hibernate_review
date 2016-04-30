package cyiq.test;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;



import cyiq.domain.Person;

public class PersonTest {
	private static  SessionFactory sessionFactory;
	static{
		Configuration cfg = new Configuration();
		cfg.configure();
		sessionFactory = cfg.buildSessionFactory();
	}
	@Test
	public void savaPerson(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Person person = new Person();
		person.setPname("aaaa");
		person.setPsex("n");
		session.save(person);
		transaction.commit();
		session.close();
	}
	@Test
	public void updatePerson(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Person person = (Person) session.get(Person.class, 2L);
		person.setPname("bbbb");
		session.update(person);
		transaction.commit();
		session.close();
	}
	
	@Test
	public void queryPerson(){
		Session session = sessionFactory.openSession();
		List<Person> personList = session.createQuery("from Person").list();
		System.out.println(personList.size());
	}
	
	@Test
	public void deletePerson(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
//		Person person = (Person) session.get(Person.class, 1L);
		Person person = new Person();
		person.setPid(2L);
		session.delete(person);
		transaction.commit();
		session.close();
	}
}
