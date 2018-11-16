<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

	<head>
	    <title>User Login</title> 
	    <style> 
		    .error { color: red; }
		    .ok { color: green; }
	    	.interface { padding: 50px 100px; }
	    </style>
	    
	    <script type="text/javascript">
	    	function checkEmail(inputEmail) {
	    		
	    		var emailFormat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	    		var strPatt = /OK/g;
	    		
	    		if (inputEmail.length==0) {
	    			document.getElementById("emailError").innerHTML="Please enter your email!";
	    			document.getElementById("emailOk").innerHTML="";
	    			document.getElementById("LoginButton").disabled=true;
	    		} else if (!emailFormat.test(inputEmail)) {
	    			document.getElementById("emailError").innerHTML="Please enter a valid email!";
	    			document.getElementById("emailOk").innerHTML="";
	    			document.getElementById("LoginButton").disabled=true;
	    		} else {
	    			document.getElementById("emailError").innerHTML="";
	    			document.getElementById("emailOk").innerHTML="OK";
	    			document.getElementById("LoginButton").disabled=true;
	    			var passwordStatus = document.getElementById("passwordOk").value;
	    			if (strPatt.test(passwordStatus)) {
	    				document.getElementById("LoginButton").disabled=false;
	    			}
	    		}
	    		
	    	}
	    	
		    function checkPassword(inputPassword) {
		    	
		    	var strPatt = /OK/g;
	    		if (inputPassword.length==0) {
	    			document.getElementById("passwordError").innerHTML="Please enter your password!";
	    			document.getElementById("passwordOk").innerHTML="";
	    			document.getElementById("LoginButton").disabled=true;
	    		} else {
	    			document.getElementById("passwordError").innerHTML="";
	    			document.getElementById("passwordOk").innerHTML="OK";
	    			document.getElementById("LoginButton").disabled=true;
	    			var emailStatus = document.getElementById("emailOk").value;
	    			if (strPatt.test(emailStatus)) {
	    				document.getElementById("LoginButton").disabled=false;
	    			}
	    		}
			
	    	} 
		    
	    </script>
	</head>
	
	<body><div class="interface">
		<h1>User Login</h1>

		<form:form action="/accessCheck" modelAttribute="appDataTransferObject" method="POST" >
			
			<table>
			<tr>
				<td><label for="email">Email</label></td>
				<td><input type="email" id="email" name="email" placeholder="example@domain.com" onkeyup="checkEmail(this.value)"/></td>
				<td><span class="error" id="emailError"></span><span class="ok" id="emailOk"></span></td>
			</tr>
			<tr>
				<td><label for="password">Password</label><br></td>
				<td><input type="password" id="password" name="password" placeholder="********" onkeyup="checkPassword(this.value)"/></td>
				<td><span class="error" id="passwordError"></span><span class="ok" id="passwordOk"></span></td>	
			</tr>
			<tr>
				<td><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/></td>
				<td><button id='LoginButton' type="submit" class="btn" disabled>Login</button></td>
				<td></td>
			</tr>
			</table>
			
		</form:form>	
		<p><a href="/signup/">Create an account for vote</a></p>
	</div>
	</body>
	
</html>
