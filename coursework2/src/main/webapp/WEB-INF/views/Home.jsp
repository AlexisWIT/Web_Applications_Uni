<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

	<head>
	    <title>Home</title> 
	    <style> 
		    .error { color: red; } 
	    	.interface { padding: 50px 100px; }
	    </style>
	</head>
	
	<body><div class="interface">
		<h1>Voter Home</h1>
		<p> Hello _____ you can vote now</p>
		
		<table>
		<tr>
			<form:form action="/home/logout">
			<td>
				<button id='SignOutButton' type="submit" class="btn">Sign Out</button>
			</td>
			</form:form>	
	
			<form:form action="/home/vote">
			<td>
				<button id='GoToVoteButton' type="submit" class="btn">Vote Now</button>
			</td >
			</form:form>
		</tr>
		</table>
	
	</div>
	</body>
</html>