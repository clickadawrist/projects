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
// A Student is considered "equal" if 1) the User parts of 
// Student are the same and 2) the list of courses and maxCredits are the same.
// So I just implemented the name, courses, and maxCredits 
	
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
		if (credits > maxCredits || name.equals(name)) {
//name.equals(name) might be wrong...			
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.course_manager.users.User#addCourse(edu.ncsu.csc216.course_manager.courses.Course)
	 */
	@Override
	public boolean addCourse(Course c) {
		if (canAddCourse(c) == true) {
//it's supposed to do something else I think...figure it out tomorrow...
			return true;
		}
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
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

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return courses + "," + maxCredits + "," + super.toString();
	}
}
