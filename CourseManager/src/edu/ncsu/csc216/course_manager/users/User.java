/**
 * 
 */
package edu.ncsu.csc216.course_manager.users;

import edu.ncsu.csc216.course_manager.courses.Course;

/**
 * Generic representation of user for CourseManager.
 * @author Manaka Green
 */
public abstract class User {
	
	/** User's first name. */
	private String firstName;
	/** User's last name. */
	private String lastName;
	/** User's id. */
	private String id;
	/** User's email. */
	private String email;
	/** User's hashed password. */
	private String password;	
	
	/**
	 * Creates a new user with the following information:  
	 * If any of the inputs are null or an empty string an IllegalArgumentException is thrown.  
	 * If the email does not contain one @ and one '.' after the @, 
	 * an IllegalArgumentException is thrown.  
	 * If any other error occurs when creating a User, an IllegalArgumentException is thrown.
	 * @param firstName User's first name
	 * @param lastName Users' last name
	 * @param id User's id
	 * @param email User's email
	 * @param password User's plaintext password
	 */
	public User(String firstName, String lastName, String id, String email, String password) {
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(password);
	}

	/**
	 * Sets the user's first name.
	 * @param firstName the new first name for the user
	 */
	public void setFirstName(String firstName) {
		if (firstName == null || firstName.length() == 0) {
			throw new IllegalArgumentException();
		}
		this.firstName = firstName;
	}

	/**
	 * Sets the user's last name.
	 * @param lastName the new last name for the user
	 */
	public void setLastName(String lastName) {
		if (lastName == null || lastName.length() == 0) {
			throw new IllegalArgumentException();
		}
		this.lastName = lastName;
	}

	/**
	 * Sets the user's id.  The method is private because the user's id
	 * cannot be changed once set.
	 * @param id the user's id.
	 */
	private void setId(String id) {
		if (id == null || id.length() == 0) {
			throw new IllegalArgumentException();
		}
		this.id = id;
	}

	/**
	 * Sets the user's email to the given string.
	 * @param email new email for the user
	 */
	public void setEmail(String email) {
		if (email == null || email.length() == 0) {
			throw new IllegalArgumentException();
		}
		int atIdx = email.indexOf("@");
		int dotIdx = email.lastIndexOf(".");
		//This is a very naive email checker
		if (atIdx == -1 || dotIdx == -1 || dotIdx <= atIdx) {
			throw new IllegalArgumentException();
		}
		this.email = email;
	}

	/**
	 * Stores a hashed password.  It is the responsibility of the client to 
	 * hash the password coming into the system.  
	 * @param password hashed password to save.
	 */
	public void setPassword(String password) {
		if (password == null || password.length() == 0) {
			throw new IllegalArgumentException();
		}
		this.password = password;
	}
	
	/**
	 * Gets the user's first name.
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Gets the user's last name.
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Gets the user's id.
	 * @return id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Gets the user's email address.
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Gets the user's password.
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Returns true if the student can add the course to their schedule.
	 * @param c Course to check
	 * @return true If the course can be added
	 */
	public abstract boolean canAddCourse(Course c);

	/**
	 * Adds a course to the user. 
	 * Returns false if the course cannot be added.
	 * @param c Course to add
	 * @return true If the course is added
	 */
	public abstract boolean addCourse(Course c);

	/**
	 * Removes a course from the user.  
	 * Returns false if the course cannot be removed.
	 * @param c Course to remove
	 * @return true If the course is removed
	 */
	public abstract boolean removeCourse(Course c);

	/**
	 * Returns a list of courses for the user.
	 * @return user's courses
	 */
	public abstract Course[] getCourses();
	
	/** 
	 * HashCode that overrides Object's hashCode method.
	 * For firstName, lastName, id, email, and password. 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	/**
	 * Equals that overrides Object's equals method.
	 * For firstName, lastName, id, email, and password.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	/**
	 * ToString that overrides Object's toString.
	 * Returns firstName, lastName, id, email, and password separated by commas
	 */
	@Override
	public String toString() {
		return firstName + "," + lastName + "," + id + "," + email + "," + password;
	}   	
}