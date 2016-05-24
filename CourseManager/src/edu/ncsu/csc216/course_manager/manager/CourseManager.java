package edu.ncsu.csc216.course_manager.manager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import edu.ncsu.csc216.course_manager.courses.Course;
import edu.ncsu.csc216.course_manager.users.Student;
import edu.ncsu.csc216.course_manager.users.User;

/**
 *
 * @author Manaka Green
 *
 */
public class CourseManager {
	/** CourseManager singleton instance */
	private static CourseManager manager;

	/** List of all Courses in the system */
	private ArrayList<Course> courses;
	/** List of all Students in the system */
	private ArrayList<Student> students;
	/** Currently logged in User */
	private User currentUser;
	/** Course records file name */
	private String courseFileName;
	/** Student records file name */
	private String studentFileName;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	
	/**
	 * Constructor for CourseManager.  It's private so that it can
	 * only be created inside of CourseManager and we can ensure
	 * only a single instance of the class is created.  This makes it 
	 * very easy to work with the CourseManager throughout the system.
	 */
	private CourseManager() {
		courses = new ArrayList<Course>();
		students = new ArrayList<Student>();
	}

	/**
	 * Returns the singleton instance of CourseManager.  If the instance 
	 * doesn't exist, it will be created.
	 * @return singleton instance
	 */
	public static CourseManager getInstance() {
		if (manager == null) {
			manager = new CourseManager();
		}
		return manager;
	}
	
	/**
	 * Log user into the system if there is no one else logged in.
	 * @param id user's id
	 * @param password user's password
	 * @return true if user is logged in
	 */
	public boolean login(String id, String password) {
		//If currentUser is null, that means no one is logged in.
		//If the currentUser is not null, then someone else 
		//cannot login since we are creating a single user system.		
		if (currentUser != null) {
			return false;
		}
		for (Student s: students) {
			//Check if the ids are the same. 
			//If they are not, we check the next Student in the list.
			if (s.getId().equals(id)) {
			//If the ids are the same, we create a hash of the plaintext 
			//password passed as a parameter.
				try {
					MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
					digest.update(password.getBytes());
					String hashPW = new String(digest.digest());
					//Once the password is hashed, we compare it 
					//with the current Student's password.
					//If the passwords match, we return true.
					if (s.getPassword().equals(hashPW)) {
						currentUser = s;
						return true;
						//^^You're logged in.
					}
					return false;
				} catch (NoSuchAlgorithmException e) {
					throw new IllegalArgumentException();
				}	
			}
		}
		//If no Student is found with a matching id or password, the method returns false.
		return false;
	}

	/**
	 * Log current user out of the system.
	 */
	public void logout() {
		currentUser = null;
	}
	
	/**
	 * Returns the current logged in user or null if there is 
	 * no logged in user.
	 * @return logged in user
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Returns a list of all Courses associated with the current User.
	 * @return list of User's courses
	 */
	public Course[] listUserCourses() {
		if (currentUser == null) {
			throw new IllegalArgumentException("User is not logged in.");
		}
		return currentUser.getCourses();
	}

	/**
	 * Returns a list of all Courses in the system.
	 * @return list of all Courses
	 */
	public Course[] listAllCourses() {
		Course [] allCourses = new Course[courses.size()];
		return courses.toArray(allCourses);
	}
	
	/**
	 * Returns true if the Course is added to the current User's
	 * list of courses.
	 * @param course Course to add
	 * @return true if added to the User
	 */
	public boolean addUserToCourse(Course course) {
		if (currentUser == null) {
			throw new IllegalArgumentException("User is not logged in.");
		}
		if (currentUser.canAddCourse(course) && currentUser instanceof Student) {
			Student s = (Student)currentUser;
			if (course.canEnroll(s)) {
				currentUser.addCourse(course);
				course.enroll(s);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns true if the Course is removed from the current User's
	 * list of courses.
	 * @param course Course to remove
	 * @return true if removed from the User
	 */
	public boolean removeUserFromCourse(Course course) {
		if (currentUser == null) {
			throw new IllegalArgumentException("User is not logged in.");
		}
		course.drop(currentUser);
		return currentUser.removeCourse(course);
	}
	
	/**
	 * Clears all course and student data from the Course manager 
	 * without saving.
	 */
	public void clearData() {
		courses = new ArrayList<Course>();
		students = new ArrayList<Student>();
		currentUser = null;
		courseFileName = null;
		studentFileName = null;
	}
}