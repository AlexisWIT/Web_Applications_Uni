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
	    <script src="../scripts/signup.js"></script>
	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script> 
	    <script type="text/javascript">
	    	$(document).ready(function(){
	    		
	    		$("#email").keyup(function(){
	    			// check validation of the email entered
	    			var emailInput = $("#email").val();
	    			var emailPattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	    			if (emailInput.length==0) {
	    				
	    			} else if (emailInput.match(emailPattern)) {
	    				
	    			} else {
	    				
	    			}
	    			
	    		});
	    		
	    		$("#email").focusout(function(){
	    			// use ajax to check the input email with database record
	    			
	    			
	    		});
	    		
	    		$("#familyName").focusout(function(){
	    			// check if contains number or exceeds the max length
	    			
	    		});
	    		
	    		$("#givenName").focusout(function(){
	    			// check if contains number or exceeds the max length
	    			
	    		});
	    		
	    		$("#dateOfBirthString").focusout(function(){
	    			// check if less than 18 years old
	    			
	    		});
	    		
	    		$("#address").keyup(function(){
	    			// check if empty or contains space only
	    			
	    		});
	    		
	    		$("#password").keyup(function(){
	    			// check password strength
	    			var passwordInput = $("#password").val();
	    			// must be 6-12 characters, contains at least 1 lowercase and 1 uppercase alphabetical character 
	    			// or has at least 1 lowercase and 1 numeric character 
	    			// or has at least 1 uppercase and 1 numeric character. (Use .html() to add <br> tags inside)
	    			var passwordPattern = /^(((?=.*[a-z])(?=.*[A-Z]))|((?=.*[a-z])(?=.*[0-9]))|((?=.*[A-Z])(?=.*[0-9]))).{6,12}$/;
	    			if (passwordInput.length==0) {
	    				
	    			} else if (passwordInput.match(emailPattern)) {
	    				
	    			} else {
	    				
	    			}
	    			
	    		});
	    		
	    		$("#passwordCheck").keyup(function(){
	    			// check if match with #password
	    			
	    		});
	    		
	    		$("#bioIdCodeString").keyup(function(){
	    			// check if matched with pattern, if used, if in the database record
	    			
	    		});
	    		
	    	});

	    </script>
	    
	</head>
<body><div class="interface">
<h1>Voter Sign up</h1>


<form:form id="cancelForm" name="cancelForm" action="/signup/cancel" modelAttribute="user" method="POST">
    <p> <input type="submit" value="Cancel" class="btn"/> </p>
</form:form>

<form:form id="registerForm" name="registerForm" action="/signup/register" modelAttribute="user" method="POST" >
   <table>
    <tr>
        <td><form:label path="email">Email: </form:label></td>
        <td><form:input path="email" id="email" name="email" placeholder="example@domain.com"/></td>
        <td><form:errors path="email" class="error" /></td>
    </tr>
    <tr>
        <td><form:label path="familyName">Family Name: </form:label></td>
        <td><form:input path="familyName" id="familyName" name="familyName" placeholder="John"/></td>
        <td><form:errors path="familyName" class="error" /></td>
    </tr>
    <tr>
        <td><form:label path="givenName">Given Name: </form:label></td>
        <td><form:input path="givenName" id="givenName" name="givenName" placeholder="Doe"/></td>
        <td><form:errors path="givenName" class="error" /></td>
    </tr>
    <tr>
        <td><form:label path="dateOfBirthString">Date of Birth: </form:label></td>
        <td><form:input path="dateOfBirthString" id="dateOfBirthString" name="dateOfBirthString" type="date" /></td>
        <td><form:errors path="dateOfBirthString" class="error" /></td>
    </tr>
    <tr>
        <td><form:label path="address">Address: </form:label></td>
        <td><form:input path="address" id="address" name="address" placeholder="Road, District"/></td>
        <td><form:errors path="address" class="error" /></td>
    </tr>
    <tr>
        <td><form:label path="password">Password: </form:label></td>
        <td><form:input path="password" id="password" name="password" type="password" placeholder="********"/></td>
        <td><form:errors path="password" class="error" /></td>
    </tr>
    <tr>
        <td><form:label path="passwordCheck">Verify Password: </form:label></td>
        <td><form:input path="passwordCheck" id="passwordCheck" name="passwordCheck" type="password" placeholder="********"/></td>
        <td><form:errors path="passwordCheck" class="error" /></td>
    </tr>
    <tr>
        <td><form:label path="bioIdCodeString">BIC Code: </form:label></td>
        <td><form:input path="bioIdCodeString" id="bioIdCodeString" name="bioIdCodeString" placeholder="AAAA-BBBB-CCCC-DDDD"/></td>
        <td><form:errors path="bioIdCodeString" class="error" /></td>
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