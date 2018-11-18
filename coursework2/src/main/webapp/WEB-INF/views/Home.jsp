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
	    	th { padding: 15px 10px 0px 10px; 
	    			text-align: left; }
	    	td { padding: 0px 10px;
	    			text-align: center; }
	    </style>
	    
	    <script><%@ include file="../scripts/jquery.min.js" %></script>
	    <script><%@ include file="../scripts/jquery.validate.min.js" %></script>
	    <script><%@ include file="../scripts/jquery.validate.additional.min.js" %></script>
	    
	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script> 
	    <script>
	    	$(document).ready(function(){
	    		
	    		
	    		
	    		
	    		
	    	});
	    
	    
	    </script>
	</head>
	
	<body><div class="interface">
		<h1>Voter Home</h1>
		<hr />
		
		<core:forEach items="${userList}" var="user">
			<p>Hello <i><core:out value="${user.getGivenName()}"/> <core:out value="${user.getFamilyName()}"/></i> ! Welcome to your homepage.</p>
			
			<form:form action="/home/logout">
				<p><button id='SignOutButton' type="submit" class="btn">Sign Out</button></p>
			</form:form>
				
			<table>
			<tr>
				<th>[ Email ]</th>
				<th>[ Date of Birth ]</th>	
			</tr>
			<tr>
				<td><core:out value="${user.getEmail()}"/></td>
				<td><core:out value="${user.getDateOfBirthForHome()}"/></td>
			</tr>
			<tr>
				<th colspan="2">[ Address ]</th>
			</tr>
			<tr>
				<td colspan="2"><core:out value="${user.getAddress()}"/></td>
			</tr>
			<tr>
				<th colspan="2">[ Your Vote ]</th>
			</tr>
			<tr>
				<td><i>Question</i></td>
				<td><i>Your Choice</i></td>
			</tr>
			<tr id="VoteList">
				<core:forEach items="${questionList}" var="question">
					<td>(<core:out value="${question.getRefId()}"/>) <core:out value="${question.getTitle()}"/></td>
					<core:forEach items="${optionList}" var="option">
					<td><core:out value="${option.getOptId()}"/>. <core:out value="${option.getOption()}"/></td>
					</core:forEach>
				</core:forEach>
			</tr>
			<tr>
				<td></td>
			</tr>
			
			<tr>
				<form:form action="/home/vote">
				<td colspan="2"><p><button id='GoToVoteButton' type="submit" class="btn">Vote Now</button></p></td >
				</form:form>
			</tr>
			</table>
		</core:forEach>
		
	
	</div>
	</body>
</html>