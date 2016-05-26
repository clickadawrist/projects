package edu.ncsu.csc216.course_manager.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.course_manager.courses.Course;

/**
 * 
 * @author Manaka Green
 *
 */
public class CourseRecordIO {
	//The client of CourseRecordIO is the CourseManager class.
	//There are two methods in CourseManager that interact with CourseRecordIO: 
	//loadCourses() and saveCourses().
	/**
	 * Supposed to be empty apparently.
	 */
	public CourseRecordIO() {
		//super();
	}
 
	/**
	 * Reads Course records from the given file.  If the file doesn't exist
	 * a FileNotFoundException is thrown.  A line with a format error will be
	 * ignored.
	 * @param fileName name of file to read
	 * @return Course records
	 * @throws FileNotFoundException if the file doesn't exist
	 */
	public static List<Course> readCourseRecords(String fileName) throws FileNotFoundException {
		List<Course> courses = new ArrayList<Course>();
			
		Scanner fileScanner = new Scanner(new File(fileName));
		while (fileScanner.hasNextLine()) {
			try {
				courses.add(processCourse(fileScanner.nextLine()));
			} catch (IllegalArgumentException e) {
				//if the exception is thrown, ignore the Student line.  
			}
		}
		fileScanner.close();
		return courses;
	}
	
	/**
	 * Creates a Course from the String record.  An IllegalArgumentException is thrown
	 * if one of the items is missing or if the Course cannot be constructed.
	 * @param courseLine the line to process
	 * @return a valid Course
	 */
	private static Course processCourse(String courseLine) {
		//',' character as a delimiter: that means that the String is broken up
		//into tokens that are separated by a ','.
		Scanner lineScanner = new Scanner(courseLine);
		try {
			lineScanner.useDelimiter(",");
			String name = lineScanner.next();
			int credits = lineScanner.nextInt();
			int capacity = lineScanner.nextInt();
			lineScanner.close();
			return new Course(name, credits, capacity);
		} catch (NoSuchElementException e) {
			lineScanner.close();
			throw new IllegalArgumentException();
		} 
	}

	/**
	 * Writes the information about the courses to the given file.
	 * @param fileName file name to record data
	 * @param courses list of courses
	 * @throws IOException if cannot write to file
	 */
	public static void writeCourseRecords(String fileName, List<Course> courses) throws IOException {
		//The PrintWriter will automatically overwrite the file if it already exists. 
		
		//PrintWriter fileOut = new PrintWriter(new FileWriter(fileName));
		//^^ or vv
		PrintStream fileOut = new PrintStream(fileName); 
		
		for (Course c: courses) {
			fileOut.println(c.toString());
		}
		fileOut.close();
	}
}