<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

	<head>
	    <title>User Login</title> 
	    <style> 
		    .error { color: red; } 
	    	.interface { padding: 50px 100px; }
	    </style>
	</head>
	
	<body><div class="interface">
		<h1>User Login</h1>

		<form:form action="/accessCheck" modelAttribute="appDataTransferObject" method="POST" >
			
			<table>
			<tr>
				<td><label for="email">Email</label></td>
				<td><input type="email" id="email" name="email" placeholder="example@domain.com"/></td>	
			</tr>
			<tr>
				<td><label for="password">Password</label><br></td>
				<td><input type="password" id="password" name="password" placeholder="******"/></td>	
			</tr>
			<tr>
				<td><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/></td>
				<td><button id='LoginButton' type="submit" class="btn">Login</button></td>
			</tr>
			</table>
			
		</form:form>	
		<a href="/signup/">Create an account for vote</a>
	</div>
	</body>
	
</html>
