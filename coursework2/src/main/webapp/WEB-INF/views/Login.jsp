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

		<form action="/accessCheck" modelAttribute="appDataTransferObject" method="POST" >
		   
			<core:if test="${param.error != null}">        
				<p class="error">Invalid Email or Password.</p>
			</core:if>
			<core:if test="${param.logout != null}">       
				<p class="error">You have been logged out.</p>
			</core:if>
			<table>
			<tr>
				<td><label for="email">Email</label></td>
				<td><input type="email" id="email" name="email"/></td>	
			</tr>
			<tr>
				<td><label for="password">Password</label><br></td>
				<td><input type="password" id="password" name="password"/></td>	
			</tr>
			</table>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<button id='LoginButton' type="submit" class="btn">Login</button>
		</form>	
		<a href="/signup/">Create an account for vote</a>
	</div>
	</body>
	
</html>
