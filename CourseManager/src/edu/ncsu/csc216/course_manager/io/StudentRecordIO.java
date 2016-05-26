package edu.ncsu.csc216.course_manager.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.course_manager.courses.Course;
import edu.ncsu.csc216.course_manager.manager.CourseManager;
import edu.ncsu.csc216.course_manager.users.Student;

/**
 * 
 * @author Manaka Green
 *
 */
public class StudentRecordIO {
	
	/**
	 * Supposed to be empty apparently.
	 */
	public StudentRecordIO() {
		//super();
	}

	/**
	 * Reads the file line by line and adds the Student to the list if the Student is valid.
	 * @param fileName name of file to read
	 * @return Course records
	 * @throws FileNotFoundException if the file doesn't exist
	 */
	public static List<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		List<Student> students = new ArrayList<Student>();

		Scanner fileScanner = new Scanner(new FileInputStream(fileName));
		while (fileScanner.hasNextLine()) {
			try {
				students.add(processStudent(fileScanner.nextLine()));
			} catch (IllegalArgumentException e) {
				//if the exception is thrown, ignore the Student line.  
			}
		}
		fileScanner.close();
		return students;
	}
	
	/**
	 * Creates a Student from the String record.  An IllegalArgumentException is thrown
	 * if one of the items is missing or if the Student cannot be constructed.
	 * @param courseLine the line to process
	 * @return a valid Course
	 */
	private static Student processStudent(String studentLine) {
		//',' character as a delimiter: that means that the String is broken up
		//into tokens that are separated by a ','.
		Scanner lineScanner = new Scanner(studentLine);
		try {
			lineScanner.useDelimiter(",");
			//why isn't this working
			String firstName = lineScanner.next();
			String lastName = lineScanner.next();
			String id = lineScanner.next(); 
			String email = lineScanner.next();
			String password = lineScanner.next();
			int maxCredits = lineScanner.nextInt();
			//#1 creates a new Student object
			Student s = new Student(firstName, lastName, id, email, password, maxCredits);
			//#2 we're going to use this as we're looping through different courses
			while (lineScanner.hasNext()) {
				Course c = CourseManager.getInstance().getCourseByName(lineScanner.next());
				if(c == null) {
					lineScanner.close();
					throw new IllegalArgumentException();		
				}
				else if (c.canEnroll(s) == false) {
					lineScanner.close();
					throw new IllegalArgumentException();
				} 
					s.addCourse(c); 
					c.enroll(s);
			}
			lineScanner.close();
			return s;
		} catch (NoSuchElementException e) {
			lineScanner.close();
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Opens and writes the information about the student records to the given file.
	 * @param fileName file name to record data
	 * @param courses list of courses
	 * @throws IOException if cannot write to file
	 */
	public static void writeStudentRecords(String fileName, List<Student> students) throws IOException {
		//The PrintWriter will automatically overwrite the file if it already exists. 

		//PrintWriter fileOut = new PrintWriter(new FileWriter(fileName));
		//^^ or vv
		PrintStream fileOut = new PrintStream(fileName); 
		
		for (Student s: students) {
			fileOut.println(s.toString());
		}
		fileOut.close();
	}
}