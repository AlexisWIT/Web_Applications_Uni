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
		    .ok { color: green; }
	    	.interface { padding: 50px 100px; }
	    	td { padding: 5px 10px; }
	    </style>
	    
	    <script><%@ include file="../scripts/jquery.min.js" %></script>
	    <script><%@ include file="../scripts/jquery.validate.min.js" %></script>
	    
	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>  
	    <script src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.7/jquery.validate.min.js"></script>
	    
	    <script src="../scripts/login.js"></script>
	</head>
	
	<body><div class="interface">
		<h1>USER LOGIN</h1>
		<hr />
		
		<form:form id="loginForm" name="loginForm" action="/accessCheck" modelAttribute="user" method="POST" >
			
			<table>
			<tr>
				<td><label for="email">Email: </label></td>
				<td><input type="email" id="email" name="email" placeholder="example@domain.com" /></td>
				<td><span class="error" id="emailError"></span><span class="ok" id="emailOk"> </span></td>
			</tr>
			<tr>
				<td><label for="password">Password: </label><br></td>
				<td><input type="password" id="password" name="password" placeholder="********" /></td>
				<td><span class="error" id="passwordError"></span><span class="ok" id="passwordOk"> </span></td>	
			</tr>
			<tr>
				<td><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/></td>
				<td><button id="LoginButton" type="submit" class="btn" disabled>Login</button>
				<p id="ErrorMsg" class="error"></p></td>
				<td></td>
			</tr>
			<tr>
				<td colspan="2"><i><a href="/signup/">Create an account for vote</i></p></td>
			</tr>
			</table>
			
		</form:form>
	</div>
	</body>
	
</html>
