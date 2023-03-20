<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>
<head>
<title>Student Tracker App</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
<link type="text/css" rel="stylesheet" href="css/add-student-style.css">
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>Foobar University</h2>
		</div>
	</div>
	
	<div id="container">
		<h3>Update Student</h3>
		
		<form action="StudentControllerServlet" method="GET">
			<input type="hidden" name="command" value="UPDATE"/>
			<input type="hidden" name="studentId" value="${LOAD_STUDENT.id}"/>
			
		<table>
			<tr>
				<td><label>First Name:</label></td>
				<td><input type="text" name="firstName" value="${LOAD_STUDENT.firstName}"/></td>
			</tr>
			<tr>
				<td><label>Last Name:</label></td>
				<td><input type="text" name="lastName" value="${LOAD_STUDENT.lastName}"/></td>
			</tr>
			<tr>
				<td><label>Email:</label></td>
				<td><input type="text" name="email" value="${LOAD_STUDENT.email}"/></td>
			</tr>
			
			<tr>
				<td><label></label></td>
				<td><input type="submit" value="Update!" class="save"/></td>
			</tr>
		</table>
		</form>
		
		<div style="clear: both;"></div>
		
		<p>
			<a href="StudentControllerServlet">Back to List</a>
		</p>
	</div>

</body>
</html>