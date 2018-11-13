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
		<div>
			<a href="/vote/" class="btn btn-default">Vote now</a>
		</div>
		<br>
		<div>
			<a href="/login/" class="btn btn-default">Sign out</a>
		</div>
		<br>
		<div>
			<a href="/login/" class="btn btn-default">Admin login</a>
		</div>
	</body>
</html>