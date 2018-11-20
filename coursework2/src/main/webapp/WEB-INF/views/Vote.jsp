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
		    .ok { color: green; }
		    .unknown { color: orange; }
	    	.interface { padding: 50px 100px; }
	    	.VoteItem { padding: 10px 20px; }
	    	.VoteOption { padding: 10px 20px; }
	    	.VoteButton { text-align: center }
	    </style>
	    
	    <script><%@ include file="../scripts/jquery.min.js" %></script>
	    <script><%@ include file="../scripts/jquery.validate.min.js" %></script>
	    <script><%@ include file="../scripts/jquery.validate.additional.min.js" %></script>
	    
	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	    <script>
	    $(document).ready(function(){
	    	
	    	var questionStatusCode = $("#statusCode").html();
	    	$("#statusCode").hide();
	    	console.log("Vote status ["+questionStatusCode+"]");
	    	
	    	if (questionStatusCode==1) {
	    		$("#statusOpen").html("OPEN");
	    	} else if (questionStatusCode==0) {
	    		$("#statusClosed").html("CLOSED");
	    	} else {
	    		$("#statusUnknown").html("UNKNOWN");
	    	}
	    	
	    });
	    
	    </script>
	</head>
	
	<body><div class="interface">
	<h1>Vote for Your Future</h1>
	<hr />
	
	<div><a href="/home/">Back</a></div>
	<table class="VoteTable">
	<tr>
		<th>Item</th>
		<th>Options</th>
		<th>Status</th>
	</tr>
	
	<form:form id="voteForm" name="voteForm" action="/vote/confirm" modelAttribute="     "  method="POST">
	<core:forEach items="${questionList}" var="question">
	<tr>
		<td id="VoteItem" class="VoteItem">
			<p>
			(<core:out value="${question.getRefId()}"/>) 
			 <core:out value="${question.getTitle()}"/>
			</p>
		</td>
		<td id="VoteOption" class="VoteOption">
			<core:forEach items="${optionList}" var="option">
			<p><input type="radio" id="options" name="options" value="${option.getOptId()}">
			<core:out value="${option.getOptId()}"/>. 
			<core:out value="${option.getOption()}"/>
			</p>
			</core:forEach>
		</td>
		<td id="VoteStatus" class="VoteStatus">
			<span id="statusCode"><core:out value="${question.getStatus()}"/></span>
			<span id="statusString">
				<span id="statusClosed" class="error"></span>
				<span id="statusOpen" class="ok"></span>
				<span id="statusUnknown" class="unknown"></span>
			</span>
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
