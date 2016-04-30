package cyiq.relation.manytomany.test;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import cyiq.relation.manytomany.bean.Course;
import cyiq.relation.manytomany.bean.Student;

public class ManyToManyTest {
	private static SessionFactory sessionFactory;
	static{
		Configuration configuration = new Configuration();
		configuration.configure("cyiq/relation/manytomany/bean/hibernate.cfg.xml");
		sessionFactory = configuration.buildSessionFactory();
	}
	
	@Test
	/**
	 * 保存课程
	 */
	public void testSaveCourse(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Course course = new Course();
		course.setCname("javase");
		course.setDescription("java基础");
		session.save(course);
		transaction.commit();
		session.close();
	}
	
	@Test
	/**
	 * 保存学生
	 */
	public void testSaveStudent(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Student student = new Student();
		student.setSname("aaaa");
		student.setDescription("666666666666");
		
		session.save(student);
		transaction.commit();
		session.close();
	}
	
	@Test
	/**
	 * 保存学生的同时保存课程
	 */
	public void testSaveStudentAndCourse(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Course course = new Course();
		course.setCname("javaweb");
		course.setDescription("new world");
		
		Student student = new Student();
		student.setSname("aaaa");
		student.setDescription("666666666666");
		Set<Course> courses = new HashSet<Course>();
		courses.add(course);
		student.setCourses(courses);
		session.save(course);
		session.save(student);
		transaction.commit();
		session.close();
	}
	
	@Test
	/**
	 * 保存课程的同时保存学生
	 */
	public void testSaveCourseAndStudent(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Course course = new Course();
		course.setCname("javaee");
		course.setDescription("great");
		
		Student student = new Student();
		student.setSname("bbbb");
		student.setDescription("666666666666");
		
		Set<Student> students = new HashSet<Student>();
		students.add(student);
		course.setStudents(students);
		session.save(course);
		session.save(student);
		transaction.commit();
		session.close();
	}
	
	@Test
	/**
	 * 新建学生，与已存在的课程关系
	 */
	public void testSaveStudentIntoExsitCourse(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Course course = (Course) session.get(Course.class, 3L);
		
		Student student = new Student();
		student.setSname("cccc");
		student.setDescription("666666666666");
		Set<Course> courses = new HashSet<Course>();
		courses.add(course);
		student.setCourses(courses);
		
		session.save(student);
		transaction.commit();
		session.close();
	}
	
	@Test
	/**
	 * 新建课程，与已存在的学生建立关系
	 */
	public void testSaveCourseIntoExsitStudent(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		
		Course course = new Course();
		course.setCname("javaee");
		course.setDescription("great");
		
		Student student = (Student)session.get(Student.class, 4L);
		
		Set<Student> students = new HashSet<Student>();
		students.add(student);
		course.setStudents(students);
		
		session.save(course);
		transaction.commit();
		session.close();
	}
	
	@Test
	/**
	 * 已存在学生，解除该课程与原来所有课程之间的关系，建立与新课程之间的关系。
	 */
	public void testSaveExsitStudentIntoNewCourse(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Course course = new Course();
		course.setCname("j2ee");
		course.setDescription("difficult");
		
		Student student = (Student)session.get(Student.class, 4L);
		student.setCourses(null);
		Set<Course> courses = new HashSet<Course>();
		courses.add(course);
		student.setCourses(courses);
		
		session.save(course);
		transaction.commit();
		session.close();
	}
	
	@Test
	/**
	 * 已存在学生，解除该课程与原来一门课程之间的关系，建立与新一门课程之间的关系。
	 */
	public void testDeleteRelationAndSaveExsitStudentIntoNewCourse(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Course course = new Course();
		course.setCname("php");
		course.setDescription("don't know");
		
		Course course1 = (Course) session.get(Course.class, 2L);
		
		Student student = (Student)session.get(Student.class, 2L);
		Set<Course> courses = student.getCourses();
		for(Course c: courses){
			if(c.getCid() == 2L){
				courses.remove(course1);
			}
		}
		courses.add(course);
		student.setCourses(courses);
		session.save(course);
		transaction.commit();
		session.close();
	}
	@Test
	/**
	 * 存在一个学生，解除该学生所有课程之间的联系
	 */
	public void testDeleteStudentAllCourse(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Student student = (Student) session.get(Student.class, 2L);
		student.setCourses(null);
		
		transaction.commit();
		session.close();
	}
	
	@Test
	/**
	 * 解除已存在课程与学生的所有关系，再建立该课程与一些新学生之间的关系
	 */
	public void testDeleteCAllCourse(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Student student1 = new Student();
		Student student2 = new Student();
		student1.setSname("a1");
		student2.setSname("a2");
		
		Course course = (Course) session.get(Course.class,2L);
		course.setStudents(null);
		
		Set<Student> students = new HashSet<Student>();
		students.add(student1);
		students.add(student2);
		course.setStudents(students);
		session.save(student1);
		session.save(student2);
		
		transaction.commit();
		session.close();
	}
	
	@Test
	/**
	 * 删除课程，并删除与该课程有关的学生
	 */
	public void testDeleteCourseAndStudent(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Course course = (Course) session.get(Course.class, 2L);
		for(Student s : course.getStudents()){
			session.delete(s);
		}
		session.delete(course);
		
		transaction.commit();
		session.close();
	}
	
}	
