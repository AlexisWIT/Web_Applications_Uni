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
	<script src="https://www.gstatic.com/charts/loader.js"></script>
	
	<script><%@ include file="../scripts/dashboard.js" %></script>
	
	<body><div class="interface">
		<h1>ADMIN DASHBOARD - ELECTION COMMISSION</h1>	
		<hr />

		<p>Welcome Admin! Below is/are the current referendum(s).</p>
		
		<form:form action="/dashboard/logout">
			<p><button id='AdminSignOutButton' type="submit" class="btn">Sign Out</button></p>
		</form:form>
			
		<h2>Referendum Content</h2>
		<table>
		<tr>
			<th>Item</th>
			<th>Options</th>
			<th>Status</th>
			<th></th>
		</tr>
		

		<core:forEach items="${questionList}" var="question">
		<tr>
			<td class="VoteItem">
			<form:form modelAttribute="questions">
				<p>
				 (<form:label path="refId" id="questionRefId"><core:out value="${question.getRefId()}"/></form:label>) 
				  <form:label path="title" id="questionTitle"><span id="editableQuestionTitle"><core:out value="${question.getTitle()}"/></span></form:label>
				</p>
			</form:form>
			</td>
			<td class="VoteOption">
			<form:form modelAttribute="options">
				<core:forEach items="${optionList}" var="option">
				<p>
				<form:label path="id" id="optionOptId-${option.getId()}"><core:out value="${option.getId()}"/></form:label>. 
				<form:label path="option" id="optionOpt-${option.getId()}"><span id="editableOption-${option.getId()}"><core:out value="${option.getOption()}"/></span></form:label>
				</p>
				</core:forEach>
			</form:form>
			</td>
			<td id="VoteStatus" class="VoteStatus">
				<span id="statusCode"><core:out value="${question.getStatus()}"/></span>
				<span id="statusString">
					<span id="statusClosed" class="error"></span>
					<span id="statusOpen" class="ok"></span>
					<span id="statusUnknown" class="unknown"></span></span>
			</td>
			<td class="ButtonZone">
				<p><input type="button" id="changeStatusButton" name="changeStatus" value="Close" class="btn"></p>
			</td>
		</tr>
		</core:forEach>
		<tr>
			<td class="ButtonZone">
				<input type="button" id="editQuestionButton" name="editQuestion" value="Edit Question" class="btn"/>
			</td>
			<td class="ButtonZone">
				<br><input type="button" id="editOptionButton" name="editOption" value="Edit Options" class="btn"/>
			</td>
			
			<td>
			</td>
			<td>
			
			</td>
		</tr>
		</table>
		
		<h2>Statistics</h2>
		<table>
		<tr>
			<td colspan="2">
				<input type="button" id="ResultSheetButton" name="ResultSheet" value="View Results" class="btn"/>
			</td>
		</tr>
		<tr id="Sheets">
			<td colspan="2">
			<table id="Stats-Table">
				<tr><th colspan="2" id="Stats-QuestionTitle"></th></tr>
				
				<tr><td id="Stats-Option-1"></td><td id="Stats-Option-1-Count"></td>
				</tr>
				<tr><td id="Stats-Option-2"></td><td id="Stats-Option-2-Count"></td>
				</tr>
				<tr><td id="Stats-Option-3"></td><td id="Stats-Option-3-Count"></td>
				</tr>	
			</table>
			<br>
			<input type="button" id="PieChartButton" name="showPieChart" value="View Pie Chart" class="btn" style="display: none;"/>
			<input type="button" id="BarChartButton" name="showBarChart" value="View Bar Chart" class="btn" style="display: none;"/>	
			</td>
		</tr>
		<tr id="Charts">
			<td id="PieChartZone">
				<div id="GooglePieChart"></div>
			</td>
			<td id="BarChartZone">
				<div id="GoogleBarChart"></div>
			</td>
		</tr>

		
		</table>
			
	</div>	
	</body>
	
</html>