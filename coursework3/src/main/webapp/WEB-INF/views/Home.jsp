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
	
	<!-- Bootstrap css -->
	<link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet"
		href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css">
		
	<!-- Home css -->
	<link rel="stylesheet" href="../css/home.css">
		
</head>

<body>
<div class="site-wrapper">

	<div class="site-wrapper-inner">

        <div class="cover-container">

          <div class="masthead clearfix">
            <div class="inner">
              <h3 class="masthead-brand">Genealogy Explorer</h3>
              <nav>
                <ul class="nav masthead-nav">
                  <li class="active"><a href="/GE/">Home</a></li>
                  <li><a href="/GE/person/">Member</a></li>
                  <li><a href="#">Family Tree</a></li>
                </ul>
              </nav>
            </div>
          </div>

          <div class="inner cover">
	          <h1 class="cover-heading">Find your family</h1>
	          <p class="lead">Genealogy Explorer is an online tool for building a family tree and tracking ancestry.</p>
	          <p class="lead">
	            <a href="/GE/person/" class="btn btn-lg btn-default">Start</a>
	          </p>
          </div>

          <div class="mastfoot">
            <div class="inner">
              <p>Cover template for <a href="http://getbootstrap.com">Bootstrap</a>, by Yifan Tang (yt120)</a>.</p>
            </div>
          </div>

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