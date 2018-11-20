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
	
	<script><%@ include file="../scripts/jquery.min.js" %></script>
    <script><%@ include file="../scripts/jquery.validate.min.js" %></script>
    <script><%@ include file="../scripts/jquery.validate.additional.min.js" %></script>
	    
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	
	<script><%@ include file="../scripts/dashboard.js" %></script>
	
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
			<th> </th>
		</tr>
		
		<!--<form:form action="/dashboard/edit" modelAttribute="question" method="POST">-->
		<core:forEach items="${questionList}" var="question">
		<tr>
			<td class="VoteItem">
			
				<p>
				 (<form:label path="refId" id="questionRefId"><core:out value="${question.getRefId()}"/></form:label>) 
				  <form:label path="title" id="questionTitle"><span id="editableQuestionTitle"><core:out value="${question.getTitle()}"/></span></form:label>
				</p>
			</td>
			<td class="VoteOption">
				<core:forEach items="${optionList}" var="option">
				<p>
				<form:label path="optId" id="optionOptId"><core:out value="${option.getOptId()}"/></form:label>. 
				<form:label path="option" id="optionOpt"><span id="editableOption"><core:out value="${option.getOption()}"/></span></form:label>
				</p>
				</core:forEach>
			</td>
			<td id="VoteStatus" class="VoteStatus">
				<span id="statusCode"><core:out value="${question.getStatus()}"/></span>
				<span id="statusString">
					<span id="statusClosed" class="error"></span>
					<span id="statusOpen" class="ok"></span>
					<span id="statusUnknown" class="unknown"></span></span>
			</td>
			<td>
				<p><input type="button" id="changeStatusButton" name="changeStatus" value="Close" class="btn"></p>
				
				
				
				
				<!--  To do: edit question and options -->
				
				
				
				
				
			</td>
		</tr>
		</core:forEach>
		<tr>
			<td>
				<input type="button" id="editQuestionButton" name="editQuestion" value="Edit Question" class="btn"/>
			</td>
			<td colspan="2" class="ButtonZone">
				<input type="button" id="editOptionButton" name="editOption" value="Edit Options" class="btn"/>
				<input type="button" id="showChartButton" name="showChart" value="Chart" class="btn"/>
			</td>
		</tr>
		<!--</form:form>-->
		
		</table>
			
	</div>	
	</body>
	
</html>