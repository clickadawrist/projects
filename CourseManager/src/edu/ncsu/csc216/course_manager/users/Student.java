/**
 * 
 */
package edu.ncsu.csc216.course_manager.users;

import edu.ncsu.csc216.course_manager.courses.Course;

/**
 * @author Manaka Green
 *
 */
public class Student extends User {

	/**
	 * @param firstName
	 * @param lastName
	 * @param id
	 * @param email
	 * @param password
	 */
	public Student(String firstName, String lastName, String id, String email, String password) {
		super(firstName, lastName, id, email, password);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.course_manager.users.User#canAddCourse(edu.ncsu.csc216.course_manager.courses.Course)
	 */
	@Override
	public boolean canAddCourse(Course c) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.course_manager.users.User#addCourse(edu.ncsu.csc216.course_manager.courses.Course)
	 */
	@Override
	public boolean addCourse(Course c) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.course_manager.users.User#removeCourse(edu.ncsu.csc216.course_manager.courses.Course)
	 */
	@Override
	public boolean removeCourse(Course c) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.course_manager.users.User#getCourses()
	 */
	@Override
	public Course[] getCourses() {
		// TODO Auto-generated method stub
		return null;
	}

}
