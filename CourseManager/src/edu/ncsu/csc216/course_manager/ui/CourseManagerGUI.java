package edu.ncsu.csc216.course_manager.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.ncsu.csc216.course_manager.courses.Course;
import edu.ncsu.csc216.course_manager.manager.CourseManager;

/**
 * Creates the GUI for the CourseManager system.
 * @author SarahHeckman
 */
public class CourseManagerGUI extends JFrame {
	
	/** ID number used for object serialization. */
	private static final long serialVersionUID = 1L;
	/** Title for top of GUI. */
	private static final String APP_TITLE = "Course Manager";
	/** Panel for holding major screens */
	private JPanel panel;
	/** CardLayout for the different panels */
	private CardLayout cardLayout;
	/** Constant to identify InitPanel for CardLayout. */
	private static final String INIT_PANEL = "InitPanel";
	/** Constant to identify LoginPanel for CardLayout. */
	private static final String LOGIN_PANEL = "LoginPanel";
	/** Constant to identify StudentPanel for CardLayout. */
	private static final String STUDENT_PANEL = "StudentPanel";
	/** Init panel - we only need one instance so it's final */
	private final InitPanel initPanel = new InitPanel();
	/** Login panel - we only need one instance so it's final */
	private final LoginPanel loginPanel = new LoginPanel();
	/** Student panel - we only need one instance so it's final */
	private final StudentPanel studentPanel = new StudentPanel();
	/** Reference to the CourseManager */
	private CourseManager manager;

	/**
	 * Constructs the GUI an sets up the screens into a CardLayout.
	 */
	public CourseManagerGUI() {
		super();
		
		//Set up general GUI info
		setSize(700, 500);
		setLocation(50, 50);
		setTitle(APP_TITLE);
		addWindowListener(new WindowAdapter() {
			/**
			 * Overrides the behavior of the window closing to save the course
			 * and student records.
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				if (manager.getCurrentUser() != null) {
					try {
						manager.saveCourses();
					
					} catch (Exception exc) {
						//There's not much we can do at this point.
					}
					try {
						manager.saveStudents();
					} catch (Exception exc) {
						//There's not much we can do at this point.
					}
				}
				System.exit(0);
			}
			
		});
		
		//Create JPanel that will hold rest of GUI information.
		//The JPanel utilizes a CardLayout, which stack several different
		//JPanels.  User actions lead to switching which "Card" is visible.
		panel = new JPanel();
		cardLayout = new CardLayout();
		panel.setLayout(cardLayout);
		panel.add(initPanel, INIT_PANEL);
		panel.add(loginPanel, LOGIN_PANEL);
		panel.add(studentPanel, STUDENT_PANEL);
		cardLayout.show(panel, INIT_PANEL);
		
		//Gets the CourseManager instance.
		manager = CourseManager.getInstance();
		
		//Add panel to the container
		Container c = getContentPane();
		c.add(panel, BorderLayout.CENTER);
		
		//Set the GUI visible
		setVisible(true);
	}
	
	/**
	 * Creates a panel for initializing the CourseManager by requesting the
	 * CourseRecords and StudentRecords files.
	 * @author SarahHeckman
	 */
	private class InitPanel extends JPanel implements ActionListener {

		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		
		/** Label for CourseRecords file. */
		private JLabel lblCourseRecords;
		/** TextField for CourseRecords file path. */
		private JTextField txtCourseRecords;
		/** Browse button for CourseRecords file path. */
		private JButton btnBrowseCourseRecords;
		/** Load button for CourseRecords file. */
		private JButton btnLoadCourseRecords;
		
		/** Label for StudentRecords file. */
		private JLabel lblStudentRecords;
		/** TextField for StudentRecords file path. */
		private JTextField txtStudentRecords;
		/** Browse button for StudentRecords file path. */
		private JButton btnBrowseStudentRecords;
		/** Load button for StudentRecords file. */
		private JButton btnLoadStudentRecords;
		
		/** Reset Button */
		private JButton btnReset;
		
		/** FileName for course records file */
		private String courseFileName;
		/** FileName for student records file */
		private String studentFileName;
		
		/**
		 * Constructs the initialization panel.
		 */
		public InitPanel() {
			super(new GridBagLayout());
			
			GridBagConstraints c = new GridBagConstraints();
			
			//Create CourseRecords components
			lblCourseRecords = new JLabel("Course Record File:");
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 1;
			c.weightx = 1.0;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(lblCourseRecords, c);
			
			txtCourseRecords = new JTextField(20);
			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 2;
			c.weightx = 0.0;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			add(txtCourseRecords, c);
			
			btnBrowseCourseRecords = new JButton("Browse");
			c.gridx = 0;
			c.gridy = 2;
			c.gridwidth = 1;
			c.weightx = 0.5;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(btnBrowseCourseRecords, c);
			
			btnLoadCourseRecords = new JButton("Load");
			c.gridx = 1;
			c.gridy = 2;
			c.gridwidth = 1;
			c.weightx = 0.5;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(btnLoadCourseRecords, c);
			
			//Add ActionListeners to Course buttons
			btnBrowseCourseRecords.addActionListener(this);
			btnLoadCourseRecords.addActionListener(this);
			
			//Place holder label for spacing
			JLabel lblPlaceHolder = new JLabel(" ");
			c.gridx = 0;
			c.gridy = 3;
			c.gridwidth = 1;
			c.weightx = 1.0;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(lblPlaceHolder, c);
			
			//Create StudentRecords components
			lblStudentRecords = new JLabel("Student Record File:");
			c.gridx = 0;
			c.gridy = 4;
			c.gridwidth = 1;
			c.weightx = 1.0;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(lblStudentRecords, c);
			
			txtStudentRecords = new JTextField(20);
			c.gridx = 0;
			c.gridy = 5;
			c.gridwidth = 2;
			c.weightx = 0.0;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			add(txtStudentRecords, c);
			
			btnBrowseStudentRecords = new JButton("Browse");
			c.gridx = 0;
			c.gridy = 6;
			c.gridwidth = 1;
			c.weightx = 0.5;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(btnBrowseStudentRecords, c);
			
			btnLoadStudentRecords = new JButton("Load");
			c.gridx = 1;
			c.gridy = 6;
			c.gridwidth = 1;
			c.weightx = 0.5;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(btnLoadStudentRecords, c);
			
			//Add ActionListencers to Student buttons
			btnBrowseStudentRecords.addActionListener(this);
			btnLoadStudentRecords.addActionListener(this);
			
			//Set button enabled/disabled as appropriate
			btnBrowseCourseRecords.setEnabled(true);
			btnLoadCourseRecords.setEnabled(false);
			btnBrowseStudentRecords.setEnabled(false);
			btnLoadStudentRecords.setEnabled(false);
			
			//Place holder label for spacing
			JLabel lblPlaceHolder2 = new JLabel(" ");
			c.gridx = 0;
			c.gridy = 7;
			c.gridwidth = 1;
			c.weightx = 1.0;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(lblPlaceHolder2, c);
			
			//Reset button
			btnReset = new JButton("Reset");
			c.gridx = 0;
			c.gridy = 8;
			c.gridwidth = 1;
			c.weightx = 1.0;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(btnReset, c);
			btnReset.addActionListener(this);
			
		}

		/**
		 * Performs actions when any component with an action listener is selected.
		 * @param e ActionEvent representing the user action
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnBrowseCourseRecords) {
				courseFileName = getFileName();
				txtCourseRecords.setText(courseFileName);
				
				//Reset buttons
				btnBrowseCourseRecords.setEnabled(false);
				btnLoadCourseRecords.setEnabled(true);
				btnBrowseStudentRecords.setEnabled(false);
				btnLoadStudentRecords.setEnabled(false);
			} else if (e.getSource() == btnLoadCourseRecords) {
				try {
					manager.loadCourses(courseFileName);
					
					//Reset buttons
					btnBrowseCourseRecords.setEnabled(false);
					btnLoadCourseRecords.setEnabled(false);
					btnBrowseStudentRecords.setEnabled(true);
					btnLoadStudentRecords.setEnabled(false);
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(this, iae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					
					//Reset buttons
					btnBrowseCourseRecords.setEnabled(true);
					btnLoadCourseRecords.setEnabled(false);
					btnBrowseStudentRecords.setEnabled(false);
					btnLoadStudentRecords.setEnabled(false);
					
					txtCourseRecords.setText("");
				}				
			} else if (e.getSource() == btnBrowseStudentRecords) {
				studentFileName = getFileName();
				txtStudentRecords.setText(studentFileName);
				
				//Reset buttons
				btnBrowseCourseRecords.setEnabled(false);
				btnLoadCourseRecords.setEnabled(false);
				btnBrowseStudentRecords.setEnabled(false);
				btnLoadStudentRecords.setEnabled(true);
			} else if (e.getSource() == btnLoadStudentRecords) {
				try {
					manager.loadStudents(studentFileName);
				
					cardLayout.show(panel, LOGIN_PANEL);
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(this, iae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					
					//Reset buttons
					btnBrowseCourseRecords.setEnabled(false);
					btnLoadCourseRecords.setEnabled(false);
					btnBrowseStudentRecords.setEnabled(true);
					btnLoadStudentRecords.setEnabled(false);
					
					txtStudentRecords.setText("");
				}
			} else if (e.getSource() == btnReset) {
				manager.clearData();
				txtCourseRecords.setText("");
				txtStudentRecords.setText("");
				
				//Reset buttons
				btnBrowseCourseRecords.setEnabled(true);
				btnLoadCourseRecords.setEnabled(false);
				btnBrowseStudentRecords.setEnabled(false);
				btnLoadStudentRecords.setEnabled(false);
			}
		}
		
		/**
		 * Returns a file name generated through interactions with a {@link JFileChooser}
		 * object.
		 * @return the file name selected through {@link JFileChooser}
		 */
		private String getFileName() {
			JFileChooser fc = new JFileChooser("./");  //Open JFileChoose to current working directory
			int returnVal = fc.showOpenDialog(this);
			if (returnVal != JFileChooser.APPROVE_OPTION) {
				//Error or user canceled, either way no file name.
				throw new IllegalStateException();
			}
			File gameFile = fc.getSelectedFile();
			return gameFile.getAbsolutePath();
		}
		
	}
	
	/**
	 * Creates a panel for user authentication into the system.
	 * @author SarahHeckman
	 */
	private class LoginPanel extends JPanel implements ActionListener {

		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		
		/** JLabel for id */
		private JLabel lblId;
		/** JTextField for id */
		private JTextField txtId;
		
		/** JLabel for password */
		private JLabel lblPassword;
		/** JTextField for password */
		private JPasswordField txtPassword;
		
		/** JButton to Login */
		private JButton btnLogin;
		/** JButton to Clear */
		private JButton btnClear;
		
		/**
		 * Constructs the LoginPanel.
		 */
		public LoginPanel() {
			super(new GridBagLayout());
			
			GridBagConstraints c = new GridBagConstraints();
			
			//Create ID components
			lblId = new JLabel("User ID:");
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 1;
			c.weightx = 0.5;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(lblId, c);
			
			txtId = new JTextField(20);
			c.gridx = 1;
			c.gridy = 0;
			c.gridwidth = 1;
			c.weightx = 0.5;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(txtId, c);
			
			//Create Password components
			lblPassword = new JLabel("Password:");
			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 1;
			c.weightx = 0.5;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(lblPassword, c);
			
			txtPassword = new JPasswordField(20);
			c.gridx = 1;
			c.gridy = 1;
			c.gridwidth = 1;
			c.weightx = 0.5;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(txtPassword, c);
			
			//Create Buttons
			btnClear = new JButton("Clear");
			c.gridx = 0;
			c.gridy = 2;
			c.gridwidth = 1;
			c.weightx = 0.5;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(btnClear, c);
			
			btnLogin = new JButton("Login");
			c.gridx = 1;
			c.gridy = 2;
			c.gridwidth = 1;
			c.weightx = 0.5;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(btnLogin, c);
						
			//Add ActionListeners
			btnLogin.addActionListener(this);
			btnClear.addActionListener(this);
		}

		/**
		 * Performs actions when any component with an action listener is selected.
		 * @param e ActionEvent representing the user action
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnLogin) {
				String id = txtId.getText();
				String password = new String(txtPassword.getPassword());
				
				if (manager.login(id, password)) {
					cardLayout.show(panel, STUDENT_PANEL);
					studentPanel.updateLists();
				} else {
					JOptionPane.showMessageDialog(this, "Invalid id and password.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else if (e.getSource() == btnClear) {
				txtId.setText("");
				txtPassword.setText("");
			}
		}
		
	}
	
	/**
	 * Creates a panel for all Student functionality.
	 * @author SarahHeckman
	 */
	private class StudentPanel extends JPanel implements ActionListener {

		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		
		/** JLabel for My Courses */
		private JLabel lblMyCourses;
		/** JList for My Courses */
		private JList<Course> listMyCourses;
		/** ListModel for my courses */
		private DefaultListModel<Course> listModelMyCourses;
		
		/** JLabel for All Courses */
		private JLabel lblAllCourses;
		/** JList for All Courses */
		private JList<Course> listAllCourses;
		/** ListModel for all courses */
		private DefaultListModel<Course> listModelAllCourses;
		/** JScrollPane for all courses */
		private JScrollPane listScrollerAllCourses;
				
		/** JButton for add */
		private JButton btnAddCourse;
		/** JButton for remove */
		private JButton btnRemoveCourse;
		
		/** JLabel for Course Details */
		private JLabel lblCourseDetails;
		/** JTextField for Course Details */
		private JTextArea txtCourseDetails;
		
		/**
		 * Constructs the student panel.
		 */
		public StudentPanel() {
			super(new GridBagLayout());
			
			GridBagConstraints c = new GridBagConstraints();
			
			//Sets up My Courses
			lblMyCourses = new JLabel("My Courses:");
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 1;
			c.weightx = 0.4;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(lblMyCourses, c);
			
			//Sets up the My Courses List Model
			listModelMyCourses = new DefaultListModel<Course>();
						
			//Sets up the My Courses List
			listMyCourses = new JList<Course>(listModelMyCourses);
			listMyCourses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listMyCourses.setLayoutOrientation(JList.VERTICAL);
			listMyCourses.addListSelectionListener(new MyCoursesListSelectionListener());
			listMyCourses.setCellRenderer(new NameCellRenderer());
			c.gridx = 0;
			c.gridy = 1;
			c.gridheight = 7;
			c.weightx = 0.4;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(listMyCourses, c);
			
			//Sets up add and remove buttons
			btnAddCourse = new JButton("Add");
			c.gridx = 1;
			c.gridy = 1;
			c.gridwidth = 1;
			c.gridheight = 1;
			c.weightx = 0.2;
			c.anchor = GridBagConstraints.CENTER;
			c.fill = GridBagConstraints.RELATIVE;
			add(btnAddCourse, c);
			btnAddCourse.addActionListener(this);
			
			btnRemoveCourse = new JButton("Remove");
			c.gridx = 1;
			c.gridy = 3;
			c.gridwidth = 1;
			c.gridheight = 1;
			c.weightx = 0.2;
			c.anchor = GridBagConstraints.CENTER;
			c.fill = GridBagConstraints.RELATIVE;
			add(btnRemoveCourse, c);
			btnRemoveCourse.addActionListener(this);
			
			//Sets up All Courses
			lblAllCourses = new JLabel("All Courses:");
			c.gridx = 2;
			c.gridy = 0;
			c.gridwidth = 1;
			c.weightx = 0.4;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(lblAllCourses, c);
			
			//Sets up the My Courses List Model
			listModelAllCourses = new DefaultListModel<Course>();
						
			//Sets up the My Courses List
			listAllCourses = new JList<Course>(listModelAllCourses);
			listAllCourses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listAllCourses.setLayoutOrientation(JList.VERTICAL);
			listAllCourses.addListSelectionListener(new AllCoursesListSelectionListener());
			listAllCourses.setCellRenderer(new NameCellRenderer());
			listScrollerAllCourses = new JScrollPane(listAllCourses, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			c.gridx = 2;
			c.gridy = 1;
			c.gridheight = 7;
			c.weightx = 0.4;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(listScrollerAllCourses, c);
			
			//Sets up the Course Details Section
			lblCourseDetails = new JLabel("Selected Course Details:");
			c.gridx = 0;
			c.gridy = 8;
			c.gridwidth = 1;
			c.gridheight = 1;
			c.weightx = 1.0;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.VERTICAL;
			add(lblCourseDetails, c);
			
			txtCourseDetails = new JTextArea(5, 50);
			c.gridx = 0;
			c.gridy = 9;
			c.gridwidth = 3;
			c.weightx = 1.0;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			add(txtCourseDetails, c);
			
		}

		/**
		 * Performs actions when any component with an action listener is selected.
		 * @param e ActionEvent representing the user action
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnAddCourse) {
				try {
					Course c = listAllCourses.getSelectedValue();
					if (c == null) {
						JOptionPane.showMessageDialog(this, "Select a course from the All Courses list.", "Error", JOptionPane.ERROR_MESSAGE);
					} else if (manager.addUserToCourse(c)) {
						JOptionPane.showMessageDialog(this, "Course successfully added.", "Success", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(this, "Course cannot be added.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(this, iae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else if (e.getSource() == btnRemoveCourse) {
				try {
					Course c = listMyCourses.getSelectedValue();
					if (c == null) {
						JOptionPane.showMessageDialog(this, "Select a course from the My Courses list.", "Error", JOptionPane.ERROR_MESSAGE);
					} else if (manager.removeUserFromCourse(listMyCourses.getSelectedValue())) {
						JOptionPane.showMessageDialog(this, "Course successfully removed.", "Success", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(this, "Course cannot be added.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(this, iae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			updateLists();
		}
		
		/**
		 * Updates the lists with the latest from the CourseManager.
		 */
		public void updateLists() {
			listModelMyCourses.clear();
			Course [] courses = manager.listUserCourses();
			for (int i = 0; i < courses.length; i++) {
				listModelMyCourses.addElement(courses[i]);
			}
			
			listModelAllCourses.clear();
			Course [] allCourses = manager.listAllCourses();
			for (int i = 0; i < allCourses.length; i++) {
				listModelAllCourses.addElement(allCourses[i]);
			}
		}
		
		/**
		 * Updates the course details text field with the course information.
		 * @param c course to pull details from
		 */
		private void updateCourseDetails(Course c) {
			if (c == null) {
				txtCourseDetails.setText("");
			} else {
				StringBuilder builder = new StringBuilder();
				builder.append("Course Name: " + c.getName() + "\n");
				builder.append("Credits: " + c.getCredits() + "\n");
				builder.append("# Enrolled: " + c.getEnrolledStudents().length + "\n");
				builder.append("Capacity: " + c.getCapacity());
				txtCourseDetails.setText(builder.toString());
			}
			
		}
		
		/**
		 * ListSelectionListener for MyCourses components.  Will update the txtCourseDetails
		 * component when a course is selected from listMyCourses.
		 * @author SarahHeckman
		 */
		private class MyCoursesListSelectionListener implements ListSelectionListener {

			/**
			 * Method executes any time the selection in the list changes.
			 * The course details text area will update with the selected course info.
			 */
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					updateCourseDetails(listMyCourses.getSelectedValue());
				}
			}
			
		}
		
		/**
		 * ListSelectionListener for AllCourses components.  Will update the txtCourseDetails
		 * component when a course is selected from listAllCourses.
		 * @author SarahHeckman
		 */
		private class AllCoursesListSelectionListener implements ListSelectionListener {

			/**
			 * Method executes any time the selection in the list changes.
			 * The course details text area will update with the selected course info.
			 */
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					updateCourseDetails(listAllCourses.getSelectedValue());
				}
			}
			
		}
		
		/**
		 * Custom ListCellRenderer that only displays the course name and not the credits
		 * or max enrollment.
		 */
		private class NameCellRenderer extends JLabel implements ListCellRenderer<Course> {

			/** Default serial id */
			private static final long serialVersionUID = 1L;
			
			/**
			 * Constructs the cell renderer and sets it to opaque.
			 */
			public NameCellRenderer() {
				setOpaque(true);
			}

			/**
			 * Overrides the cell renderer for the lists so that only the course's name displays.
			 */
			@Override
			public Component getListCellRendererComponent(
					JList<? extends Course> list, Course value, int index,
					boolean isSelected, boolean cellHasFocus) {
				setText(value.getName());
				
				if (isSelected) {
					setBackground(list.getSelectionBackground());
					setForeground(list.getSelectionForeground());
				} else {
					setBackground(list.getBackground());
					setForeground(list.getForeground());
				}
				return this;
			}
		}
	}
	
	/**
	 * Starts the program by constructing the GUI.
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		new CourseManagerGUI();
	}
	
}
