
$("#ErrorMsg").slideUp();

$(document).ready(function () {

	// EMAIL VALIDITY
	$("#email").keyup(function(){
		$("#ErrorMsg").slideUp();
		
		// Check validation of the email entered
		var emailInput = $("#email").val();
		var emailPattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		
		// If empty value
		if (emailInput.length == 0) {
			$("#emailOk").html("");
			$("#emailError").html("Please enter your email!");
			$("#LoginButton").prop('disabled', true); //disable the submit button

		// If email entered with CORRECT pattern
		} else if (emailInput.match(emailPattern)) {
			$("#emailOk").html("OK");
			$("#emailError").html("");
			if ($("#passwordOk").html()=="OK") {
				$("#LoginButton").prop('disabled', false);
			}
		
		// Wrong pattern
		} else {
			$("#emailOk").html("");
			$("#emailError").html("Please enter a valid email!");
			$("#LoginButton").prop('disabled', true);
		}
		
	})
	
	// PASSWORD VALIDITY
	$("#password").keyup(function(){
		$("#ErrorMsg").slideUp();
		
		var passwordInput = $("#password").val();
		// must be 6-12 characters, contains at least 1 lowercase and 1 uppercase alphabetical character 
		// or has at least 1 lowercase and 1 numeric character 
		// or has at least 1 uppercase and 1 numeric character.
		var passwordPattern = /^(?=.*[\d])(?=.*[A-Z])(?=.*[a-z])[\w\d!@#$%_]{6,16}$/;
		// Check validation of password
		
		// If empty value
		if (passwordInput.length == 0) {
			$("#passwordOk").html("");
			$("#passwordError").html("Please enter your password!");
			$("#LoginButton").prop('disabled', true);

		// if valid	
		} else if (passwordInput.match(passwordPattern)) {
			$("#passwordOk").html("OK");
			$("#passwordError").html("");
			if ($("#emailOk").html()=="OK") {
				$("#LoginButton").prop('disabled', false);
			}
			
		// Prevent SQL Injection only [A-Za-z0-9-!@#$%_] allowed
		} else {
			$("#passwordOk").html("");
			$("#passwordError").html("Invalid password!");
			$("#LoginButton").prop('disabled', true);
		}
		
	});
	
	
	

	$("#loginForm").validate({				
		errorClass: 'error',
		rules: { 
			"email": {
				required: true,
				email: true,
			}, 
			"password": {
				required: true
			}
			
		}, messages: {
			"email": {
				email: "Please enter a valid email!"
			}
		
		}, submitHandler: function (form) {
			var emailInput = $("#email").val();
			var passwordInput = $("#password").val();
			var result = "";
			
			$("#passwordOk").html("");
			$("#passwordError").html("");
			$("#emailOk").html("");
			$("#emailError").html("");

			$.ajax({
				async: false,
				type: "GET",
				url: "/credentialCheck",
				data: "email="+emailInput+"&password="+passwordInput,
				success: function (response) {
					console.log("Received report ["+response.status+"]");
					if (response.status == "CHECKED") {
						result = response.status;
						success = true;
					} else {
						result = response.status;
						$("#ErrorMsg").text("["+result+"] "+response.result[0].code);
						$("#ErrorMsg").slideDown(500);
						
					}
				},

			});

			if (result == "CHECKED") {
				form.submit();
			}
		
		}

	});
	
});

function getCookies () {
	var cookiePattern = 
	
}

function setCookies (cookieName, cookieValue, expireDays) {
	var expireDate = new Date();
	expireDate.setDate(expireDate.getDate()+expireDays);
	document.cookie = cookieName+","+cookieValue+","expireDate;
};

function delCookies (cookieName) {
	setCookies (cookieName, "", 0)
	
};



