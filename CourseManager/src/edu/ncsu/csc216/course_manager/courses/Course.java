package edu.ncsu.csc216.course_manager.courses;

import java.util.ArrayList;

import edu.ncsu.csc216.course_manager.users.Student;
import edu.ncsu.csc216.course_manager.users.User;

/**
 * Creates a course.
 * @author Manaka Green
 */
public class Course implements Enrollable {
	
	/** Students enrolled in the course */
	private ArrayList<User> enrolledStudents;
	/**Name of course. */
	private String name;
	/**The amount of credits a class is worth. */
	private int credits;
	/**How many students the class can enroll. */
	private int capacity;
	/**The minimum hours a course can be. */
	public static final int MIN_HOURS = 1;
	/**The maximum hours a course can be. */
	public static final int MAX_HOURS = 4;
	
	/**
	 * Creates a Course with the given name and credit hours.
	 * @param name Course name
	 * @param credits Course credit hours
	 * @param capacity Course capacity
	 */
	public Course(String name, int credits, int capacity) {
		super();
		//constructing an array list: start with an empty list vvv
		enrolledStudents = new ArrayList<User>();
		//now we can implement the methods from enrollable
		setName(name);
		setCredits(credits);
		setCapacity(capacity);
	}

	/**
	 * Gets course name.
	 * @return the name of the course
	 */
	public String getName() {
		return name;		
	}
	
	/**
	 * Sets course name.
	 * @param name The name to set
	 */
	public void setName(String name) {
		if (name == null || name.length() == 0) {
			throw new IllegalArgumentException();
		}
		this.name = name;
	}
	
	/**
	 * Gets course credits.
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}
	/**
	 * Sets course credits.
	 * @param credits The credits to set
	 */
	public void setCredits(int credits) {
		if (credits < MIN_HOURS || credits > MAX_HOURS) {
			throw new IllegalArgumentException();
		}
		this.credits = credits;
	}
	
	/**
	 * Gets course capacity.
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}
	
	/**
	 * Sets course capacity.
	 * @param capacity The capacity to set
	 */
	public void setCapacity(int capacity) {
	//We should not be able to set the capacity to a value 
	//less than the current number of enrolled students	
		if (capacity <= 0 || capacity < enrolledStudents.size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}

	/**
	 * HashCode that overrides Object's hashCode method.
	 * For firstName, lastName, id, email, and password.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Equals that overrides Object's equals method for name.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/** 
	 * ToString that overrides Object's toString.
	 * Returns course: name, credits, and capacity separated by commas
	 */
	@Override
	public String toString() {
		return name + "," + credits + "," + capacity;
	}

	/**
	 * Returns the enrolled students as an array.
	 * @return enrolled students
	 */
	public Student [] getEnrolledStudents() {
		Student [] s = new Student[enrolledStudents.size()];
		return enrolledStudents.toArray(s);
	}

	/**
	 * Returns true if there is capacity to add a user to the course and the 
	 * user is not already enrolled.
	 * @param user User to add to the course
	 * @return true if there is capacity
	 */
	public boolean canEnroll(User user) {
		if (enrolledStudents.size() < capacity) {
			if (user instanceof Student) {
			//checks to see if user is student	
				Student s = (Student) user;
				for (int i = 0; i < enrolledStudents.size(); i++) {
					if (enrolledStudents.get(i).equals(s)) {
						return false;
					}
				}
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * Enroll the user in the course if there is room.
	 * @param user User to enroll
	 * @return true if user is enrolled
	 */
	public boolean enroll(User user) {
		return canEnroll(user) && enrolledStudents.add(user);
	}

	/**
	 * Drops the student from the course.
	 * @param user User student to drop
	 * @return true if the student is dropped
	 */
	public boolean drop(User user) {
		return enrolledStudents.remove(user);
	}
}