/**
 * 
 */
package edu.ncsu.csc216.course_manager.users;

import java.util.ArrayList;

import edu.ncsu.csc216.course_manager.courses.Course;

/**
 * @author Manaka Green
 *
 */
public class Student extends User {

	/**  */
	private ArrayList<Course> courses;
	private String name;
	private int credits;
	private int capacity;
	public static final int MAX_CREDITS = 18;
	private int maxCredits;
	
	/**
	 * @param firstName
	 * @param lastName
	 * @param id
	 * @param email
	 * @param password
	 */
	public Student(String firstName, String lastName, String id, String email, String password) {
		this(firstName, lastName, id, email, password, MAX_CREDITS);
	}
	
	/**
	 * @param firstName
	 * @param lastName
	 * @param id
	 * @param email
	 * @param password
	 */
	public Student(String firstName, String lastName, String id, String email, String password, int maxCredits) {
		super(firstName, lastName, id, email, password);
		setMaxCredits(maxCredits);
	}
	
	/**
	 * @return the maxCredits
	 */
	public int getMaxCredits() {
		return maxCredits;
	}


	/**
	 * @param maxCredits the maxCredits to set
	 */
	public void setMaxCredits(int maxCredits) {
		if(maxCredits < 0 || maxCredits > MAX_CREDITS) {
			throw new IllegalArgumentException();
		}
		this.maxCredits = maxCredits;
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
