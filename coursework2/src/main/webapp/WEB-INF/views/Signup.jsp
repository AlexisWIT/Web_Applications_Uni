<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Sign Up</title>
		<style> 
		    .error { color: red; } 
	    	.interface { padding: 50px 100px; }
	    </style>
	</head>
<body><div class="interface">
<h1>Voter Sign up</h1>

<form:form action="/signup/register" commandName="user" method="POST" >
   <table>
   <tr>
        <td><form:label path="email">Email</form:label></td>
        <td><form:input path="email" size="15" /></td>
        <td><form:errors path="email"  cssClass="error" /></td>
    </tr>
   <tr>
        <td><form:label path="familyName">Family Name</form:label></td>
        <td><form:input path="familyName" size="15" /></td>
        <td><form:errors path="familyName"  cssClass="error" /></td>
    </tr>
    <tr>
        <td><form:label path="givenName">Given Name</form:label></td>
        <td><form:input path="givenName" size="15" /></td>
        <td><form:errors path="givenName"  cssClass="error" /></td>
    </tr>
    <tr>
        <td><form:label path="dateOfBirthString">Date of Birth</form:label></td>
        <td><form:input type="date" path="dateOfBirthString" /></td>
        <td><form:errors path="dateOfBirthString"  cssClass="error" /></td>
    </tr>
    <tr>
        <td><form:label path="address">Address</form:label></td>
        <td><form:input path="address" size="15" /></td>
        <td><form:errors path="address"  cssClass="error" /></td>
    </tr>
    <tr>
        <td><form:label path="password">Password</form:label></td>
        <td><form:input type="password" path="password" size="15" /></td>
        <td><form:errors path="password"  cssClass="error" /></td>
    </tr>
    <tr>
        <td><form:label path="passwordCheck">Verify Password</form:label></td>
        <td><form:input type="password" path="passwordCheck" size="15" /></td>
        <td><form:errors path="passwordCheck"  cssClass="error" /></td>
    </tr>
    <tr>
    	<td colspan="1">
            
        </td>
        <td colspan="1">
            <input type="reset" name="reset" class="btn"/>
        </td>
        <td colspan="1">
            <input type="submit" name="register" class="btn"/>
        </td>
    </tr>
</table>  
</form:form>

<form:form action="/signup/cancel" modelAttribute="user" method="POST">
     <input type="submit" value="Cancel" class="btn"/> 
</form:form>

</div>
</body>
</html>