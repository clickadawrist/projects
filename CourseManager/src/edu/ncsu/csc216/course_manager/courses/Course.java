/**
 * 
 */
package edu.ncsu.csc216.course_manager.courses;

/**
 * @author Manaka Green
 *
 */
public class Course {
	
	/**
	 * @param name
	 * @param credits
	 * @param capacity
	 */
	public Course(String name, int credits, int capacity) {
		super();
		this.name = name;
		this.credits = credits;
		this.capacity = capacity;
	}

	private String name;
	private int credits;
	private int capacity;
	public static final int MIN_HOURS = 1;
	public static final int MAX_HOURS = 4;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;		
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		if (name == null || name.length() == 0) {
			throw new IllegalArgumentException();
		}
		this.name = name;
	}
	
	/**
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}
	/**
	 * @param credits the credits to set
	 */
	public void setCredits(int credits) {
		if (credits < MIN_HOURS || credits > MAX_HOURS) {
			throw new IllegalArgumentException();
		}
		this.credits = credits;
	}
	
	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}
	
	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(int capacity) {
		if (capacity <= 0) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
	 * toString
	 */
	@Override
	public String toString() {
		return name + "," + credits + "," + capacity;
	}	
}