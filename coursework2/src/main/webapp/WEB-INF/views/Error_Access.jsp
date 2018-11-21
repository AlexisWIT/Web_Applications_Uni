<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>
	<meta charset="UTF-8">
	<title>Access Denied</title>
	<style> 
		.error { color: red; } 
	    .interface { padding: 50px 100px; }
	</style>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>

<body>
	<div class="interface">

		<h1>ACCESS DENIED</h1>
		<hr />
		
		<div>
		<p>You don't have permission to view this page.</p>
		</div>
		
		<div><a href="/userLogin">Back</a></div>
	
	</div>
</body>
</html>