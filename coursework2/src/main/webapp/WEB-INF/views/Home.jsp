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
		    .ok { color: green }
	    	.interface { padding: 50px 100px; }
	    	th { padding: 20px 10px 0px 10px; 
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
	    		
	    		// auto check for vote eligibility
	    		var voteRecord = $(this).closest("#VoteList").children("#VoteQuestion").text();
	    		console.log("Vote record found: ["+voteRecord+"]");
	    		if (voteRecord != '') { // user havs made vote
	    			console.log("Vote prohibited");
	    			$("#GoToVoteButton").prop('disabled', true); // disable the button to vote page
	    			$("#VoteProhibited").html("Unable to vote again, you have made your choice.");
	    			$("#VoteAvailable").html("");
	    		} else {
	    			console.log("Vote available");
	    			$("#GoToVoteButton").prop('disabled', false);
	    			$("#VoteProhibited").html("");
	    			$("#VoteAvailable").html("You can vote now.")
	    		}
	    		
	    		// advanced check for vote eligibility
//	    		$("#GoToVoteButton").click(function({
//	    			var userEmail = $("#userEmail").html();
//Optional Todo	    			
//	    			$.ajax({
//	    				
//	    			});
//	    			
//	    		});
	    		
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
				<td><span id="userEmail"><core:out value="${user.getEmail()}"/></span></td>
				<td><span id="userBirthday"><core:out value="${user.getDateOfBirthForHome()}"/></span></td>
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
			<core:forEach items="${questionList}" var="question">
			<tr id="VoteList">
				<td id="VoteQuestion">
				(<core:out value="${question.getRefId()}"/>)
				 <core:out value="${question.getTitle()}"/>
				</td>
				<td id="VoteOptions">
				<core:forEach items="${optionList}" var="option">
				<core:out value="${option.getOptId()}"/>. 
				<core:out value="${option.getOption()}"/>
				</core:forEach>
				</td>	
			</tr>
			</core:forEach>
			
			<tr>
				<td colspan="2">
					<form:form action="/home/vote">
					<p><button id="GoToVoteButton" type="submit" class="btn" disabled="true">Vote Now</button></p>
					</form:form>
					<span id="VoteProhibited" class="error"></span>
					<span id="VoteAvailable" class="ok"></span>
				</td >
			</tr>
			
			</table>
		</core:forEach>
		
	</div>
	</body>
</html>