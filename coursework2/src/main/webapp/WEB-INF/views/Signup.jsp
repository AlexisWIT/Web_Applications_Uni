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
		    .weak { color: red }
		    .medium { color: orange }
		    .strong { color: lime }
		    .veryStrong { color: green }
		    .wait { color: orange; }
		    #passwordMeter { font-size: 10px; }
		    #passwordTips { color: red; display:none; font-size: 10px; }
		    #dateOfBirthString{ width: 98%; } 
	    	.interface { padding: 50px 100px; }
	    	.ButtonCover { padding: 1px; }
	    	td { padding: 5px 10px; }
	    </style>
	    
	    <script><%@ include file="../scripts/jquery.min.js" %></script>
	    <script><%@ include file="../scripts/jquery.validate.min.js" %></script>
	    
	    <script><%@ include file="../scripts/signup.js" %></script>
	      
	</head>
	
<body><div id="interface" class="interface">
<h1>VOTER SIGN UP</h1>
<hr />

<form:form id="cancelForm" name="cancelForm" action="/signup/cancel" modelAttribute="user" method="POST">
    <p> <input type="submit" value="Cancel" class="btn"/> </p>
</form:form>

<form:form id="regForm" name="regForm" action="/signup/register" modelAttribute="user" method="POST" >
   <table>
    <tr>
        <td><form:label path="email">Email: </form:label></td>
        <td><form:input path="email" id="email" name="email" placeholder="example@domain.com" type="email" size="30" /></td>
        <td><form:errors path="email" class="error"/><span id="emailInfo"></span></td>
    </tr>
    <tr>
        <td><form:label path="familyName">Family Name: </form:label></td>
        <td><form:input path="familyName" id="familyName" name="familyName" placeholder="John" size="30" /></td>
        <td><form:errors path="familyName" class="error"/><span id="familyNameInfo"></span></td>
    </tr>
    <tr>
        <td><form:label path="givenName">Given Name: </form:label></td>
        <td><form:input path="givenName" id="givenName" placeholder="Doe" size="30" /></td>
        <td><form:errors path="givenName" class="error"/><span id="givenNameInfo"></span></td>
    </tr>
    <tr>
        <td><form:label path="dateOfBirthString">Date of Birth: </form:label></td>
        <td><form:input path="dateOfBirthString" id="dateOfBirthString" name="dateOfBirthString" type="date" min="1900-01-01" max="9999-12-31"/></td>
        <td><form:errors path="dateOfBirthString" class="error"/><span id="dateOfBirthStringInfo"></span></td>
    </tr>
    <tr>
        <td><form:label path="address">Address: </form:label></td>
        <td><form:input path="address" id="address" name="address" placeholder="Road, District" size="30" /></td>
        <td><form:errors path="address" class="error"/><span id="addressInfo"></span></td>
    </tr>
    <tr>
        <td><form:label path="password">Password: </form:label></td>
        <td><form:input path="password" id="password" name="password" type="password" placeholder="********" size="30" />
        	<p id="passwordTips">Password must be 6-16 characters.<br><u>Must contains:</u><br>- 1 lowercase alphabetical character,
        	<br>- 1 uppercase alphabetical character,<br>- 1 numeric character.<br><u>May contains:</u><br>- Special characters [ -!@#$%_ ]</p></td>
        <td><form:errors path="password" class="error"/><span id="passwordInfo"></span><br><span id="passwordMeter"></span></td>
    </tr>
    <tr>
        <td><form:label path="passwordCheck">Verify Password: </form:label></td>
        <td><form:input path="passwordCheck" id="passwordCheck" name="passwordCheck" type="password" placeholder="********" size="30" /></td>
        <td><form:errors path="passwordCheck" class="error"/><span id="passwordCheckInfo"></span></td>
    </tr>
    <tr>
        <td><form:label path="bioIdCodeString">BIC Code: </form:label></td>
        <td><form:input path="bioIdCodeString" id="bioIdCodeString" name="bioIdCodeString" placeholder="AAAA-BBBB-CCCC-DDDD" size="30" /></td>
        <td><form:errors path="bioIdCodeString" class="error"/><span id="bioIdCodeStringInfo"></span></td>
    </tr>
    <tr>
    	<td>
            
        </td>
        <td>
        	<div id="resetButtonArea" class="ButtonArea">
        		<div id="resetButtonCover" class="ButtonCover">
            		<input type="reset" id="reset" name="reset" class="btn"/>
            	</div>
            </div>
        </td>
        <td>
            <div id="registerButtonArea" class="ButtonArea">
            	<div id="registerButtonCover" class="ButtonCover">
            	<input type="submit" id="register" name="register" class="btn" disabled="true"/>
            	</div>
            </div>
        </td>
    </tr>
</table>  
</form:form>

</div>
</body>
</html>