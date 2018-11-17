
	function checkEmail(inputEmail) {
		    		
		var emailFormat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
		
		if (inputEmail.length==0) {
			document.getElementById("emailError").innerHTML="Please enter your email!";
			document.getElementById("emailOk").innerHTML=" ";
			document.getElementById("LoginButton").disabled=true;
		} else if (!emailFormat.test(inputEmail)) {
			document.getElementById("emailError").innerHTML="Please enter a valid email!";
			document.getElementById("emailOk").innerHTML=" ";
			document.getElementById("LoginButton").disabled=true;
		} else {
			document.getElementById("emailError").innerHTML="";
			document.getElementById("emailOk").innerHTML="OK";
			document.getElementById("LoginButton").disabled=true;
			if (document.getElementById("passwordOk").innerHTML!=" ") {
				document.getElementById("LoginButton").disabled=false;
			}
		}
		
	}
    	
    function checkPassword(inputPassword) {
    	
		if (inputPassword.length==0) {
			document.getElementById("passwordError").innerHTML="Please enter your password!";
			document.getElementById("passwordOk").innerHTML=" ";
			document.getElementById("LoginButton").disabled=true;
		} else {
			document.getElementById("passwordError").innerHTML=" ";
			document.getElementById("passwordOk").innerHTML="OK";
			document.getElementById("LoginButton").disabled=true;
			if (document.getElementById("emailOk").innerHTML!=" ") {
				document.getElementById("LoginButton").disabled=false;
			}
		}
		
	}