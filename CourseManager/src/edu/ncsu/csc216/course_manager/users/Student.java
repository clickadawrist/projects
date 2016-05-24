package edu.ncsu.csc216.course_manager.users;

import java.util.ArrayList;

import edu.ncsu.csc216.course_manager.courses.Course;

/**
 * @author Manaka Green
 *
 */
public class Student extends User {
// A Student is considered "equal" if 1) the User parts of 
// Student are the same and 2) the list of courses and maxCredits are the same.
// So I just implemented the name, courses, and maxCredits 
	
	/**  */
	private ArrayList<Course> courses;
	/**Name of the course.*/
	private String name;
	/**How many credits the course is.*/
	private int credits;
	/**The amount of seats for students.*/
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
		courses = new ArrayList<Course>();
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
///////HELP
		if(maxCredits < 0 || maxCredits > MAX_CREDITS || maxCredits < this.maxCredits) {
			//third statement^^ supposed to throw an Illegal Argument Exception if new maxCredits is less than the total credits a student is enrolled in
			//it's either capacity or getCurrentCredits()...
			throw new IllegalArgumentException();
		}
		this.maxCredits = maxCredits;
	}

	/**
	 * Returns the sum of all of the credits for courses the in the student's schedule.
	 * @return 
	 */
	public int getCurrentCredits() {
////////have no idea how to do this as of now
		//sum =+ whatever?
		
		for (int i = 0; i < courses.size(); i++) {
		
			}
		return credits;
	}
	
	/** 
	 * Returns true if credits don't exceed Student's max credits AND 
	 * the student isn't already enrolled into the course 
	 * @param c Course 
	 * @return true if don't exceed maxCredits and not already enrolled
	 */
	@Override
	public boolean canAddCourse(Course c) {
		if (credits > maxCredits) {	
			return false;
		}
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).equals(c)) {
				return false;
			}
		}
		return true;
	}

	/** 
	 * Adds course to student's course list.
	 * @param c Course to add
	 * @return true if student can add course and adds course
	 */
	@Override
	public boolean addCourse(Course c) {
		if (canAddCourse(c) == true) {
			courses.add(c);
		}
		return false;
	}

	/** 
	 * Removes course from courses list.
	 * @param c Course to remove
	 * @return true if course is removed from list
	 */
	@Override
	public boolean removeCourse(Course c) {
		return courses.remove(c);
	}

	/**
	 * Returns the list of courses as an array.
	 * @return list of student's courses
	 */
	@Override
	public Course[] getCourses() {
		Course [] c = new Course[courses.size()];
		return courses.toArray(c);
	}
	
	/** 
	 * hashCode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((courses == null) ? 0 : courses.hashCode());
		result = prime * result + maxCredits;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/** 
	 * equals
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (courses == null) {
			if (other.courses != null)
				return false;
		} else if (!courses.equals(other.courses))
			return false;
		if (maxCredits != other.maxCredits)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/** 
	 * 
	 */
	@Override
	public String toString() {
		return super.toString() + "," + maxCredits; 
	}	
}