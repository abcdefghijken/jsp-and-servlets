package com.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private StudentDBUtil studentDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		try {
			studentDbUtil = new StudentDBUtil(dataSource);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			String command = request.getParameter("command");
			
			if (command == null) {
				command = "LIST";
			}
			
			switch (command) {
				case "LIST":
					listStudents(request, response);
					break;
					
				case "ADD":
					addStudent(request, response);
					break;
					
				case "LOAD":
					loadStudent(request, response);
					break;
					
				case "UPDATE":
					updateStudent(request, response);
					break;
					
				case "DELETE":
					deleteStudent(request, response);
					break;
					
				default:
					listStudents(request, response);	
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String studentId = request.getParameter("studentId");
		
		studentDbUtil.deleteStudent(studentId);
		
		listStudents(request, response);
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		Student student = new Student(id, firstName, lastName, email);
		
		studentDbUtil.updateStudent(student);
		
		listStudents(request, response);	
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String studentId = request.getParameter("studentId");
		
		Student student = studentDbUtil.getStudent(studentId);
		
		request.setAttribute("LOAD_STUDENT", student);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		
		dispatcher.forward(request, response);
		
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		Student newStudent = new Student(firstName, lastName, email);
		
		studentDbUtil.addStudent(newStudent);
		
		listStudents(request, response);	
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Student> students = studentDbUtil.getStudents();
		request.setAttribute("STUDENT_LIST", students);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		
		dispatcher.forward(request, response);
		
	}
}
