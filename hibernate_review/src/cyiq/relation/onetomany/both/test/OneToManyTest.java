package cyiq.relation.onetomany.both.test;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;	
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import cyiq.relation.onetomany.both.bean.Classes;
import cyiq.relation.onetomany.both.bean.Student;


public class OneToManyTest {
	private static SessionFactory sessionFactory;
	static{
		Configuration configuration = new Configuration();
		configuration.configure("cyiq/relation/onetomany/both/bean/hibernate.cfg.xml");
		sessionFactory = configuration.buildSessionFactory();
	}
	
	@Test
	/*
	 *	保存班级的同时保存学生 
	 *		由班级来维护关系
	 */
	public void testSaveClassesAndStudent(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Classes classes = new Classes();
		classes.setCname("6666班");
		classes.setDescription("6666666666");
		
		Student student = new Student();
		student.setSname("aaaa");
		student.setDescription("777777777777");
		
		Student student2 = new Student();
		student2.setSname("bbbb");
		student2.setDescription("8888888");
		
		Set<Student> students = new HashSet<Student>();
		students.add(student);
		students.add(student2);
		classes.setStudents(students);
		
		session.save(classes);
		
		transaction.commit();
		session.close();
	}
	
	@Test
	/*
	 *	保存学生的同时保存班级 
	 *		由学生来维护关系
	 */
	public void testSaveStudentAndClasses(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Classes classes = new Classes();
		classes.setCname("6666班");
		classes.setDescription("6666666666");
		
		Student student = new Student();
		student.setSname("aaaa");
		student.setDescription("777777777777");
		
		Student student2 = new Student();
		student2.setSname("bbbb");
		student2.setDescription("8888888");
		
		Set<Student> students = new HashSet<Student>();
		students.add(student);
		students.add(student2);
		classes.setStudents(students);
		
		student.setClasses(classes);
		student2.setClasses(classes);
		
		session.save(classes);
		session.save(student);
		session.save(student2);
		
		transaction.commit();
		session.close();
	}
	
	
	@Test
	/*
	 *	已经存在班级，新建学生，建立该学生与该班级之间的关系 
	 *		由学生来维护关系(更改外键)
	 */
	public void testSaveStudentIntoExsitClasses(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Student student = (Student) session.get(Student.class, 6L);
		
		Classes classes = new Classes();
		classes.setCname("8888班");
		classes.setDescription("888888888");
		
		Set<Student> students = new HashSet<Student>();
		students.add(student);
		classes.setStudents(students);
		
		student.setClasses(classes);
		
		session.save(classes);
		session.save(student);
		
		transaction.commit();
		session.close();
	}
	
	@Test
	/*
	 *	已经存在学生，新建班级，建立该学生与该班级之间的关系 
	 *		由学生来维护关系
	 */
	public void testSaveClassesIntoExsitStudent(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Classes classes = (Classes) session.get(Classes.class, 1L);
		
		Student student = new Student();
		student.setSname("aaaa");
		student.setDescription("777777777777");
		
		Student student2 = new Student();
		student2.setSname("bbbb");
		student2.setDescription("8888888");
		student.setClasses(classes);
		student2.setClasses(classes);
		session.save(student);
		session.save(student2);
		
		transaction.commit();
		session.close();
	}
	
	@Test
	/*
	 *	已经存在学生，存在班级，解除学生与原来班级之间的关系，建立该学生与其他班级之间的关系 
	 *		由学生来维护关系
	 */
	public void testSaveExsitStudentIntoExsitClasses(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Classes classes = (Classes) session.get(Classes.class, 1L);
		
		Student student = (Student) session.get(Student.class, 6L);
		
		student.setClasses(classes);
		
		transaction.commit();
		session.close();
	}
	
	@Test
	/*
	 *	已经存在学生,解除学生与原来班级之间的关系
	 *		由学生来维护关系
	 */
	public void testDeleteRelationExsitStudentWithClasses(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Student student = (Student) session.get(Student.class, 6L);
		student.setClasses(null);
		
		transaction.commit();
		session.close();
	}
	
	@Test
	/*
	 *	解除班级和所有学生之间的关系，建立班级和其他新学生之间的关系。
	 *		由学生来维护关系
	 */
	public void testDeleteRelationExsitClassesWithStudentNew(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Classes classes = (Classes) session.get(Classes.class, 1L);
		Student student = new Student();
		student.setSname("wwwwwwww");
		student.setDescription("wwwwwwww");
		for(Student s : classes.getStudents()){
			s.setClasses(null);
		}
		student.setClasses(classes);
		session.save(student);
		transaction.commit();
		session.close();
	}
	
}


