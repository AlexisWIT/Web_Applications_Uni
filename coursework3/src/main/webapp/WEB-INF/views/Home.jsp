<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <meta name="description" content="A homepage for Genealogy Explorer">
    <meta name="author" content="Yifan Tang (yt120)">
    
	<title>Home | Genealogy Explorer</title>
	
	<!-- Home css -->
	<link href="<core:url value="resources/css/home.css" />" rel="stylesheet" type="text/css" />
	
	<!-- Bootstrap css -->
	<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet"
		href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css">

		
</head>

<body>
	<div class="site-wrapper">
	<div class="main-container">
		<div class="inner">
			<h3 class="site-title">Genealogy Explorer</h3>
			<nav class="nav-bar">
			<ul class="nav nav-bar-list">
				<li class="active"><a href="/">Home</a></li>
				<li><a href="/GE/person/">Member</a></li>
				<li><a href="/GE/tree">Family Tree</a></li>
			</ul>
			</nav>
		</div>

		<div class="inner cover">
			<h1 class="headline">Find your family</h1>
			<p class="headline-desc">Genealogy Explorer is an online tool for building a family tree and tracking ancestry.</p>
			<p class="headline-btn">
				<a href="/GE/person/" class="btn btn-lg btn-default">Start</a>
			</p>
		</div>
	</div>
    </div>
    
    <!-- Bootstrap scripts -->
	<!-- <script src="/bootstrap/js/bootstrap.js"></script> -->
	<script src="/bootstrap/js/bootstrap.min.js"></script>
	<script src="/bootstrap/js/npm.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    
    <!-- JQuery scripts -->
	<script><%@ include file="../scripts/jquery.min.js" %></script>
	<script><%@ include file="../scripts/jquery.validate.min.js" %></script>
	<script><%@ include file="../scripts/jquery.validate.additional.min.js" %></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

</body>
</html>