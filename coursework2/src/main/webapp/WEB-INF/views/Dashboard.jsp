<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

	<head>
	    <title>Admin Dashboard</title> 
	    <style> 
		    .error { color: red; }
		    .ok { color: green; }
		    .unknown { color: orange; } 
	    	.interface { padding: 50px 100px; }
	    	.VoteItem { padding: 10px 20px; }
	    	.VoteOption { padding: 10px 20px; }
	    	.ButtonZone { text-align: center; }
	    </style>
	</head>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
	    	
	    	var questionStatusCode = $("#statusCode").html();
	    	$("#statusCode").hide();
	    	console.log("Vote status ["+questionStatusCode+"]");
	    	
	    	if (questionStatusCode==1) { // Status 1 means vote is currently open
	    		$("#statusOpen").html("OPEN");
	    		$("#changeStatusButton").val("Close");
	    	} else if (questionStatusCode==0) { // Status 0 means vote is closed
	    		$("#statusClosed").html("CLOSED");
	    		$("#changeStatusButton").val("Open");
	    	} else { // Button will be disabled in case of unknown status (which normally not gonna happen)
	    		$("#statusUnknown").html("UNKNOWN");
	    		$("#changeStatusButton").prop('disabled', true);
	    	}
	    	
	    	$("#changeStatusButton").click(function(){
	    		var buttonLabel = $("#changeStatusButton").val();
	    		var statusCode = $("#statusCode").html();
	    		if (buttonLabel == "Close") {
	    			$("#statusOpen").html("");
		    		$("#statusClosed").html("CLOSED")
		    		
		    		$.ajax({
		    			type: "POST",
		    			url: "",
		    			data: "status="+statusCode,
		    			success: function(response){
		    				console.log("Question closed successfully")
		    			}
		    		});
		    		
		    		
		    		$("#changeStatusButton").val("Open");
	    		} else {
	    			$("#statusOpen").html("OPEN");
		    		$("#statusClosed").html("")
		    		
		    		
		    		$("#changeStatusButton").val("Close");
	    		}
	    		
	    	});
	    	
	    });
	</script>
	
	<body><div class="interface">
		<h1>Admin Dashboard - Election Commission</h1>	
		<hr />

		<p>Welcome Admin! Below is/are the current referendum(s).</p>
		
		<form:form action="/dashboard/logout">
			<p><button id='AdminSignOutButton' type="submit" class="btn">Sign Out</button></p>
		</form:form>
			
		<table>
		<tr>
			<th>Item</th>
			<th>Options</th>
			<th>Status</th>
		</tr>
		
		<form:form action="/dashboard/edit" modelAttribute="question" method="POST">
		<core:forEach items="${questionList}" var="question">
		<tr>
			<td class="VoteItem">
				<p>
				(<span id=""><core:out value="${question.getRefId()}"/></span>) 
				 <core:out value="${question.getTitle()}"/>
				</p>
			</td>
			<td class="VoteOption">
				<core:forEach items="${optionList}" var="option">
				<p>
				<core:out value="${option.getOptId()}"/>. 
				<core:out value="${option.getOption()}"/>
				</p>
				</core:forEach>
			</td>
			<td id="VoteStatus" class="VoteStatus">
			
				<span id="statusCode">
					<core:out value="${question.getStatus()}"/>
				</span>
				<span id="statusString">
					<span id="statusClosed" class="error"></span>
					<span id="statusOpen" class="ok"></span>
					<span id="statusUnknown" class="unknown"></span>
				</span>
				<p>
					<input type="submit" id="changeStatusButton" name="changeStatusButton" value="Close" class="btn">
				</p>
				
			</td>
		</tr>
		</core:forEach>
		<tr>
			<td></td>
			<td class="ButtonZone">
				<input type="submit" id="changeStatusButton" name="startEdit" value="Edit" class="btn"/>
				<input type="submit" name="confirmEdit" value="Confirm" class="btn"/>
			</td>
		</tr>
		</form:form>
		
		</table>
			
	</div>	
	</body>
	
</html>