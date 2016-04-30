package cyiq.relation.onetomany.single.test;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import cyiq.relation.onetomany.single.bean.Classes;
import cyiq.relation.onetomany.single.bean.Student;
/**
 * 一对多单向关联
 * 		由classes维护关系
 * 		解除关系就是将student的外键删除，inverse="false"
 * 		改变关系就是改变student的外键的值
 *		建立关系就是设置student的外键的值 
 * @author cyiq
 */
public class OneToManyTest {
	private static SessionFactory sessionFactory;
	static{
		Configuration configuration = new Configuration();
		configuration.configure("cyiq/relation/onetomany/bean/hibernate.cfg.xml");
		sessionFactory = configuration.buildSessionFactory();
	}
	
	@Test
	/*
	 * 只存储班级
	 */
	public void testSaveClasses(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Classes classes = new Classes();
		classes.setCname("java");
		classes.setDescription("6666666666");
		session.save(classes);
		
		transaction.commit();
		session.close();
	}
	
	@Test
	/*
	 * 存储学生（此时外键没有赋值）
	 */
	public void testSaveStudent(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Student student = new Student();
		student.setSname("aaaa");
		student.setDescription("777777777777");
		session.save(student); 
		
		transaction.commit();
		session.close();
	}
	
	@Test
	/*
	 * 保存班级的同时保存学生。
	 */
	public void testSaveStudentByClasses(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Student student = new Student();
		student.setSname("aaaa");
		student.setDescription("777777777777");
		
		Classes classes = new Classes();
		classes.setCname("java");
		classes.setDescription("6666666666");
		Set<Student> students = new HashSet<Student>();
		students.add(student);
		classes.setStudents(students);
		session.save(classes);
		
		transaction.commit();
		session.close();
	}
	@Test
	/*
	 * 保存学生到已经存在的班级中。
	 */
	public void testSaveStudentByExsitClasses(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Student student = new Student();
		student.setSname("cccc");
		student.setDescription("777777777777");
		
		Classes classes = (Classes) session.get(Classes.class, 2L);
		classes.getStudents().add(student);
		
		transaction.commit();
		session.close();
	}
	
	@Test
	/*
	 * 保存已存在的学生到新建的班级中。
	 */
	public void testSaveExsitStudentInNewClasses(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Student student = (Student) session.get(Student.class, 1L);
		
		Classes classes = new Classes();
		classes.setCname("java");
		classes.setDescription("6666666666");
		Set<Student> students = new HashSet<Student>();
		students.add(student);
		classes.setStudents(students);
		session.save(classes);
		
		transaction.commit();
		session.close();
	}

	@Test
	/*
	 * 保存已存在的学生到已存在的班级中。
	 */
	public void testSaveExsitStudentInExsitClasses(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Student student = (Student) session.get(Student.class, 1L);
		
		Classes classes = (Classes) session.get(Classes.class, 1L);
		classes.getStudents().add(student);
		session.save(classes);
		
		transaction.commit();
		session.close();
	}
	
	@Test
	/*
	 * 删除学生与其班级之间的关系
	 */
	public void testDeleteRelationWithExsitStudentClasses(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Student student = (Student) session.get(Student.class, 1L);
		Classes classes = (Classes) session.get(Classes.class, 1L);
		classes.getStudents().remove(student);
		
		transaction.commit();
		session.close();
	}
	
	@Test
	/*
	 * 删除班级与所有学生之间的关系，建立新的学生关系。
	 */
	public void testClassesDeleteRelationWithAllStudent(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Student student1 = (Student) session.get(Student.class, 3L);
		Student student2 = (Student) session.get(Student.class, 4L);
		
		Classes classes = (Classes) session.get(Classes.class, 1L);
		Set<Student> students = new HashSet<Student>();
		students.add(student1);
		students.add(student2);
		classes.setStudents(students);
		transaction.commit();
		session.close();
	}
	
	@Test
	/*
	 * 删除班级与所有学生之间的关系，建立新的学生关系。
	 */
	public void testDeleteClasses(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Classes classes = (Classes) session.get(Classes.class, 2L);
		classes.setStudents(null);
		session.delete(classes);	
		//inverse="false"（默认）时才可以进行关系维护，删除学生外键，否则外键约束报错
		
		transaction.commit();
		session.close();
	}
	@Test
	/*
	 * 删除班级以及班级内的学生
	 * 	Hibernate: select classes from Classes
		Hibernate: select students from Student 
		Hibernate: update Student set cid=null where cid=?
		Hibernate: delete from Student where sid=?
		Hibernate: delete from Student where sid=?
		Hibernate: delete from Classes where cid=?
	 */
	public void testDeleteClassesAndStudent(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Classes classes = (Classes) session.get(Classes.class, 3L);
		session.delete(classes);	
		//cascade="all"删除时某个classes时会级联操作，设置student外键为null，删除student
		
		transaction.commit();
		session.close();
	}
}
