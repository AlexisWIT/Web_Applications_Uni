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
	    
	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	    <script type="text/javascript">
	    	function checkEmail(inputEmail) {
	    		var emailFormat = /^(([^<>()\[\]\\.,;:\s@"]+
	    							(\.[^<>()\[\]\\.,;:\s@"]+)*)|
	    							(".+"))@((\[[0-9]{1,3}\.[0-9]
	    							{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])
	    							|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	    	}
	    
	    </script>
	    
	</head>
<body><div class="interface">
<h1>Voter Sign up</h1>


<form:form action="/signup/cancel" modelAttribute="user" method="POST">
    <p> <input type="submit" value="Cancel" class="btn"/> </p>
</form:form>

<form:form action="/signup/register" modelAttribute="user" method="POST" >
   <table>
    <tr>
        <td><form:label path="email">Email</form:label></td>
        <td><form:input path="email" placeholder="example@domain.com"/></td>
        <td><form:errors path="email" class="error" /></td>
    </tr>
    <tr>
        <td><form:label path="familyName">Family Name</form:label></td>
        <td><form:input path="familyName" placeholder="John"/></td>
        <td><form:errors path="familyName" class="error" /></td>
    </tr>
    <tr>
        <td><form:label path="givenName">Given Name</form:label></td>
        <td><form:input path="givenName" placeholder="Doe"/></td>
        <td><form:errors path="givenName" class="error" /></td>
    </tr>
    <tr>
        <td><form:label path="dateOfBirthString">Date of Birth</form:label></td>
        <td><form:input type="date" path="dateOfBirthString" /></td>
        <td><form:errors path="dateOfBirthString" class="error" /></td>
    </tr>
    <tr>
        <td><form:label path="address">Address</form:label></td>
        <td><form:input path="address" placeholder="Road, District"/></td>
        <td><form:errors path="address" class="error" /></td>
    </tr>
    <tr>
        <td><form:label path="password">Password</form:label></td>
        <td><form:input type="password" path="password" placeholder="********"/></td>
        <td><form:errors path="password" class="error" /></td>
    </tr>
    <tr>
        <td><form:label path="passwordCheck">Verify Password</form:label></td>
        <td><form:input type="password" path="passwordCheck" placeholder="********"/></td>
        <td><form:errors path="passwordCheck" class="error" /></td>
    </tr>
    <tr>
        <td><form:label path="bioIdCode">BIC Code</form:label></td>
        <td><form:input path="bioIdCode" placeholder="AAAA-BBBB-CCCC-DDDD"/></td>
        <td><form:errors path="bioIdCode" class="error" /></td>
    </tr>
    <tr>
    	<td colspan="1">
            
        </td>
        <td colspan="1">
            <input type="reset" id="reset" name="reset" class="btn"/>
        </td>
        <td colspan="1">
            <input type="submit" id="register" name="register" class="btn"/>
        </td>
    </tr>
</table>  
</form:form>

</div>
</body>
</html>