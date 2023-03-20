package com.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDBUtil {
	private DataSource dataSource;

	public StudentDBUtil(DataSource ds) {
		dataSource = ds;
	}
	
	public List<Student> getStudents() throws Exception {
		List<Student> students = new ArrayList<>();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			
			String sql = "SELECT * FROM student ORDER BY last_name";
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String email = resultSet.getString("email");
				
				Student tempStudent = new Student(id, firstName, lastName, email);
				
				students.add(tempStudent);
			}
			
			return students;
			
		} finally {
			close(connection, statement, resultSet);
		}
	}

	private void close(Connection connection, Statement statement, ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addStudent(Student newStudent) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = dataSource.getConnection();
			String sql = "INSERT into STUDENT (first_name, last_name, email) " +
					"values (?, ?, ?)";
			statement = connection.prepareStatement(sql);
			statement.setString(1, newStudent.getFirstName());
			statement.setString(2, newStudent.getLastName());
			statement.setString(3, newStudent.getEmail());
			
			statement.execute();
			
		} finally {
			close(connection, statement, null);
		}
		
	}

	public Student getStudent(String studentId) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int id = Integer.parseInt(studentId);
		Student student = null;
		
		try {
		connection = dataSource.getConnection();
		
		String sql = "SELECT * FROM student WHERE id = ?";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			String firstName = resultSet.getString("first_name");
			String lastName = resultSet.getString("last_name");
			String email = resultSet.getString("email");
			
			student = new Student(id, firstName, lastName, email);
		} else {
			throw new Exception("Could not find Student ID: " + studentId);
		}
		
		return student;
	} finally {
		close(connection, statement, resultSet);
		}
	}

	public void updateStudent(Student student) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = dataSource.getConnection();
			
			String sql = "UPDATE student SET first_name = ?, " +
					"last_name = ?, email = ? WHERE id = ?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, student.getFirstName());
			statement.setString(2, student.getLastName());
			statement.setString(3, student.getEmail());
			statement.setInt(4, student.getId());
			
			statement.execute();
			
		} finally {
			close(connection, statement, null);
		}
	}

	public void deleteStudent(String studentId) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		int id = Integer.parseInt(studentId);
		
		try {
			connection = dataSource.getConnection();
			
			String sql = "DELETE FROM student WHERE id = ?";
			
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			
			statement.execute();
			
		} finally {
			close(connection, statement, null);
		}
		
	}
}
