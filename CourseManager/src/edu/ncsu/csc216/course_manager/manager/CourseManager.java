package edu.ncsu.csc216.course_manager.manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc216.course_manager.courses.Course;
import edu.ncsu.csc216.course_manager.io.CourseRecordIO;
import edu.ncsu.csc216.course_manager.io.StudentRecordIO;
import edu.ncsu.csc216.course_manager.users.Student;
import edu.ncsu.csc216.course_manager.users.User;

/**
 * Manages the enrollment of Students in Courses.
 * @author Manaka Green
 */
public class CourseManager {
	//Instead of handling it inside the readCourseRecords() method, 
	//we want the exception to propagate to the client (in this case the CourseManager class),
	//which will handle it in a more appropriate manner for the rest of the requirements.

	//Client of CourseManager is the CourseManagerGUI
	/** CourseManager singleton instance. */
	private static CourseManager manager;
	/** List of all Courses in the system. */
	private ArrayList<Course> courses;
	/** List of all Students in the system. */
	private ArrayList<Student> students;
	/** Currently logged in User. */
	private User currentUser;
	/** Course records file name. */
	private String courseFileName;
	/** Student records file name. */
	private String studentFileName;
	/** Hashing algorithm. */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Constructor for CourseManager. 
	 * It's private so that it can only be created inside of CourseManager and we can 
	 * ensure only a single instance of the class is created.  
	 * This makes it very easy to work with the CourseManager throughout the system.
	 */
	private CourseManager() {
		courses = new ArrayList<Course>();
		students = new ArrayList<Student>();
	}

	/**
	 * Returns the singleton instance of CourseManager.  
	 * If the instance doesn't exist, it will be created.
	 * @return singleton Creates instance of CourseManger
	 */
	public static CourseManager getInstance() {
		if (manager == null) {
			manager = new CourseManager();
		}
		return manager;
	}
	
	/**
	 * Logs user into the system if there is no one else logged in.
	 * @param id User's id
	 * @param password User's password
	 * @return true If user is logged in
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
						//^^You're logged in yay
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
	 * Logs current user out of the system.
	 */
	public void logout() {
		currentUser = null;
	}
	
	/**
	 * Returns the current logged in user or null if there is 
	 * no logged in user.
	 * @return currentUser Logged in user
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Returns a list of all Courses associated with the current User.
	 * @return list Of user's courses
	 */
	public Course[] listUserCourses() {
		if (currentUser == null) {
			throw new IllegalArgumentException("User is not logged in.");
		}
		return currentUser.getCourses();
	}

	/**
	 * Returns a list of all Courses in the system.
	 * @return list Of all Courses
	 */
	public Course[] listAllCourses() {
		Course [] allCourses = new Course[courses.size()];
		return courses.toArray(allCourses);
	}
	
	/**
	 * Returns true if the Course is added to the current User's
	 * list of courses.
	 * @param course Course to add
	 * @return true If added to the User
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
	 * @return true If removed from the User
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
	
	/**
	 * Loads the list of Courses from the given file.
	 * @param fileName Name of file containing courses
	 */
	public void loadCourses(String fileName) {
		this.courseFileName = fileName;
		try {
			List<Course> coursesFromFile = CourseRecordIO.readCourseRecords(courseFileName);
			for (Course c : coursesFromFile) {
				addCourse(c);
			}
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * Adds a course to the list of courses.
	 * Checks that the Course doesn't already exist in the list of Courses. 
	 * @param course Course to add
	 */
	public void addCourse(Course course) {
		for (Course c: courses) {
			if (c.equals(course)) {
				return;
			}
		}
		courses.add(course);
	}
	
	/**
	 * Writes the list of Courses to the courseFileName.
	 */
	public void saveCourses() {
		try {
			CourseRecordIO.writeCourseRecords(courseFileName, courses);
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * Finds a Course object in CourseManager's courses list by a name.
	 * Compares list of all courses from CourseManager's list to each student's list.
	 * @param courseFileName Course records file name
	 * @return c Course from student's list that equals the CourseManager's course list 
	 */
	public Course getCourseByName(String courseFileName) {
//look through arraylist of courses (instance field)...for loop 
//check through the course list to the fileName - compare between them
		for (Course c: courses) {
			if (c.getName().equals(courseFileName)) {
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Accepts the file name for the Student records file and saves it to the 
	 * field studentFileName.
	 * @param fileName Name of file from which you load students
	 */
	 public void loadStudents(String fileName) {
		 this.studentFileName = fileName;
		 try {
			List<Student> studentsFromFile = StudentRecordIO.readStudentRecords(studentFileName);
			for (Student s : studentsFromFile) {
				addStudents(s);
			}
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	 }

	 /**
	  * Checks that the Student doesn't already exist in the list of Students.
	  * (private Helper method)
	  * Ensures that you do not add a Student with the same id to the students list.
	  * @param student
	  */
	 private void addStudents(Student student) {
			for (Student s: students) {
				if (s.equals(student)) {
					return;
				}
			}
			students.add(student);
	 }

	 /**
	  * Writes the list of Students to the studentFileName.
	  */
	 public void saveStudents() { 
		 try {
				StudentRecordIO.writeStudentRecords(studentFileName, students);
			} catch (IOException e) {
				throw new IllegalArgumentException(e.getMessage());
			}
	 }
}