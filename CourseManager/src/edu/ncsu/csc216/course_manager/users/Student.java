package edu.ncsu.csc216.course_manager.users;

import java.util.ArrayList;

import edu.ncsu.csc216.course_manager.courses.Course;

/**
 * 
 * @author Manaka Green
 */
public class Student extends User {
// A Student is considered "equal" if 1) the User parts of 
// Student are the same and 2) the list of courses and maxCredits are the same.
// So I just implemented the name, courses, and maxCredits...
	
	/**List of courses that the student is enrolled in. */
	private ArrayList<Course> courses;
	/**Name of the course. */
	private String name;
	/**How many credits the course is. */
	private int credits;
	
	//vv unused apparently
	///**The amount of seats for students.*/
	//private int capacity;
	
	/**Maximum credits any student can have. */
	public static final int MAX_CREDITS = 18;
	/**Maximum credits a specific student can have (unique to each student). */
	private int maxCredits;
	
	/**
	 * 
	 * @param firstName First name of student
	 * @param lastName Last name of student
	 * @param id Id of student
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
	 * If the max credits is less than 0, more than the 18 credits, or the new maxCredits 
	 * would be less than the total credits a Student is enrolled in, 
	 * an IllegalArgumentException is thrown.
	 * @param maxCredits the maxCredits to set
	 */
	public void setMaxCredits(int maxCredits) {
		//if(maxCredits < 0 || maxCredits > MAX_CREDITS || maxCredits < this.maxCredits) {
		  if(maxCredits < 0 || maxCredits > MAX_CREDITS || maxCredits < getCurrentCredits()) {	
			throw new IllegalArgumentException();
		}
		this.maxCredits = maxCredits;
	}

	/**
	 * Returns the sum of all of the credits for courses the in the student's schedule.
	 * @return currentCredits
	 */
	public int getCurrentCredits() {
		//courses arraylist: course objects
		int currentCredits = 0; 
		for (int i = 0; i < courses.size(); i++) { 
			Course amountOfCredits = courses.get(credits);
			currentCredits = currentCredits + amountOfCredits.getCredits();
			}
		return currentCredits;
	}
	
	/** 
	 * Returns true if credits don't exceed Student's max credits AND 
	 * the student isn't already enrolled into the course 
	 * @param c Course 
	 * @return true if don't exceed maxCredits and not already enrolled
	 */
	@Override
	public boolean canAddCourse(Course c) {
		if (getCurrentCredits() + c.getCredits() > maxCredits) {	
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
		if (canAddCourse(c)) {
			return canAddCourse(c) && courses.add(c);
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
	 * toString
	 */
	@Override
	public String toString() {
		name = "";
		for (Course s: courses) { 
			name += "," + s.getName();
		}
		return super.toString() + "," + maxCredits + name; 
	}	
}