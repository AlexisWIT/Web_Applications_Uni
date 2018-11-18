<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

	<head>
	    <title>Vote</title> 
	    <style> 
		    .error { color: red; } 
	    	.interface { padding: 50px 100px; }
	    	.VoteItem { padding: 10px 20px; }
	    	.VoteOption { padding: 10px 20px; }
	    	.VoteButton { text-align: center }
	    </style>
	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script> 
	</head>
	
	<body><div class="interface">
	<h1>Vote</h1>
	<hr />
	
	<div><a href="/home/">Back</a></div>
	<table class="VoteTable">
	<tr>
		<th>Item</th>
		<th>Options</th>
	</tr>
	
	<form:form id="voteForm" name="voteForm" action="/vote/confirm" modelAttribute="     "  method="POST">
	<core:forEach items="${questionList}" var="question">
	<tr>
		<td class="VoteItem">
			<p>
			(<core:out value="${question.getRefId()}"/>) 
			 <core:out value="${question.getTitle()}"/>
			</p>
		</td>
		<td class="VoteOption">
			<core:forEach items="${optionList}" var="option">
			<p><input type="radio" name="options" value="${option.getOptId()}">
			<core:out value="${option.getOptId()}"/>. 
			<core:out value="${option.getOption()}"/>
			</p>
			</core:forEach>
		</td>
	</tr>
	</core:forEach>
	<tr>
		<td class="VoteButton" colspan="2">
			<input type="submit" name="confirm" class="btn"/>
		</td>
	</tr>
	</form:form>
	
	</table>
	
	
	</div>
	</body>
</html>
