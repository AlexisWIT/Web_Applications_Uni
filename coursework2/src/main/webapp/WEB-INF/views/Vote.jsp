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
	    	
	    	var userStatus = $("#userStatus").html();
	    	var questionStatusCode = $("#statusCode").html();
	    	$("#statusCode").hide();
	    	console.log("Vote status ["+questionStatusCode+"]");
	    	
	    	if (questionStatusCode==1 && userStatus!="VOTED") {
	    		$("#statusOpen").html("OPEN");
	    		$("#greeting").html("Vote for your future.")
	    		$("#confirmButton").prop('disabled', false);
	    	} else if (questionStatusCode==0) {
	    		$("#statusClosed").html("CLOSED");
	    		$("#greeting").html("Vote is closed, you can't vote now.")
	    		$("#confirmButton").prop('disabled', true);
	    	} else {
	    		$("#statusUnknown").html("UNKNOWN");
	    		$("#greeting").html("Vote is currently unavilable.")
	    		$("#confirmButton").prop('disabled', true);
	    	}
	    	
	    	
	    	
	    });
	    
	    </script>
	</head>
	
	<body><div class="interface">
	<h1>VOTE CENTRE</h1>
	<hr />
	
	<core:forEach items="${userList}" var="user">
		<p>Welcome to vote page, <i><core:out value="${user.getGivenName()}"/> <core:out value="${user.getFamilyName()}"/></i> ! <span id="greeting">Vote for your future.</span></p>
		<p><span style="visibility:hidden" id="userStatus"><core:out value="${user.getUserRemark()}"/></span></p>
	</core:forEach>
		
	<div><a href="/home/">Back</a></div>
	<table class="VoteTable">
	<tr>
		<th>Item</th>
		<th>Options</th>
		<th>Status</th>
	</tr>
	
	<form:form id="voteForm" name="voteForm" action="/vote/confirm" modelAttribute="option"  method="POST">
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
			<p><form:radiobutton path="id" id="options" name="options" value="${option.getId()}"/>
			<core:out value="${option.getId()}"/>. 
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
			<input type="submit" id="confirmButton" name="confirm" class="btn"/>
		</td>
	</tr>
	</form:form>
	
	</table>
	
	
	</div>
	</body>
</html>
