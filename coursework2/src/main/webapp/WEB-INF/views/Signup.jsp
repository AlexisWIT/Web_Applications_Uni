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
		    .ok { color: green; }
		    .wait { color: orange; }
	    	.interface { padding: 50px 100px; }
	    </style>
	    
	    <script><%@ include file="../scripts/jquery.min.js" %></script>
	    <script><%@ include file="../scripts/jquery.validate.min.js" %></script>
	    <script><%@ include file="../scripts/jquery.validate.additional.min.js" %></script>
	    
	    <script><%@ include file="../scripts/signup.js" %></script>
	      
	</head>
	
<body><div id="interface" class="interface">
<h1>Voter Sign up</h1>


<form:form id="cancelForm" name="cancelForm" action="/signup/cancel" modelAttribute="user" method="POST">
    <p> <input type="submit" value="Cancel" class="btn"/> </p>
</form:form>

<form:form id="regForm" name="regForm" action="/signup/register" modelAttribute="user" method="POST" >
   <table>
    <tr>
        <td><form:label path="email">Email: </form:label></td>
        <td><form:input path="email" id="email" name="email" placeholder="example@domain.com" type="email" /></td>
        <td><form:errors path="email" class="error"/><span id="emailInfo"></span></td>
    </tr>
    <tr>
        <td><form:label path="familyName">Family Name: </form:label></td>
        <td><form:input path="familyName" id="familyName" name="familyName" placeholder="John"/></td>
        <td><form:errors path="familyName" class="error"/><span id="familyNameInfo"></span></td>
    </tr>
    <tr>
        <td><form:label path="givenName">Given Name: </form:label></td>
        <td><form:input path="givenName" id="givenName" placeholder="Doe"/></td>
        <td><form:errors path="givenName" class="error"/><span id="givenNameInfo"></span></td>
    </tr>
    <tr>
        <td><form:label path="dateOfBirthString">Date of Birth: </form:label></td>
        <td><form:input path="dateOfBirthString" id="dateOfBirthString" name="dateOfBirthString" type="date" min="1900-01-01" max="9999-12-31"/></td>
        <td><form:errors path="dateOfBirthString" class="error"/><span id="dateOfBirthStringInfo"></span></td>
    </tr>
    <tr>
        <td><form:label path="address">Address: </form:label></td>
        <td><form:input path="address" id="address" name="address" placeholder="Road, District"/></td>
        <td><form:errors path="address" class="error"/><span id="addressInfo"></span></td>
    </tr>
    <tr>
        <td><form:label path="password">Password: </form:label></td>
        <td><form:input path="password" id="password" name="password" type="password" placeholder="********"/></td>
        <td><form:errors path="password" class="error"/><span id="passwordInfo"></span></td>
    </tr>
    <tr>
        <td><form:label path="passwordCheck">Verify Password: </form:label></td>
        <td><form:input path="passwordCheck" id="passwordCheck" name="passwordCheck" type="password" placeholder="********"/></td>
        <td><form:errors path="passwordCheck" class="error"/><span id="passwordCheckInfo"></span></td>
    </tr>
    <tr>
        <td><form:label path="bioIdCodeString">BIC Code: </form:label></td>
        <td><form:input path="bioIdCodeString" id="bioIdCodeString" name="bioIdCodeString" placeholder="AAAA-BBBB-CCCC-DDDD"/></td>
        <td><form:errors path="bioIdCodeString" class="error"/><span id="bioIdCodeStringInfo"></span></td>
    </tr>
    <tr>
    	<td colspan="1">
            
        </td>
        <td colspan="1">
            <input type="reset" id="reset" name="reset" class="btn"/>
        </td>
        <td colspan="1">
            <input type="submit" id="register" name="register" class="btn" disabled="true"/>
        </td>
    </tr>
</table>  
</form:form>

</div>
</body>
</html>