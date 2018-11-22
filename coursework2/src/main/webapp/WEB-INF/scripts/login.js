
$("#ErrorMsg").slideUp();

$(document).ready(function () {
	
	// (1) USE COOKIES TO SAVE EMAIL & PASSWORD & LOGIN STATUS
	var emailInCookies = getCookies("email");
	console.log("Email get from cookie ["+emailInCookies+"]")
	var passwordInCookies = getCookies("password");
	console.log("Password get from cookie ["+passwordInCookies+"]")
	
	// If found email & password saved in cookies
	if(emailInCookies && passwordInCookies) {
		$("#email").val(emailInCookies);
		$("#password").val(passwordInCookies);
		$("#LoginButton").prop('disabled', false);
		$("#rememberLogin").prop('checked', true); 
	}
	
	// If unchecked "Remember Login Credentials"
	$("#rememberLogin").change(function(){ 
		if (!$("#rememberLogin").prop('checked')) {
			delCookies("email");
			delCookies("password");
		}
		
	});
	
	

	// (2) EMAIL VALIDITY
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
			if ($("#password").val() == passwordInCookies) {
				$("#passwordOk").html("OK");
				$("#LoginButton").prop('disabled', false);
			}
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
	
	// (3) PASSWORD VALIDITY
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
		} else {
			$("#passwordOk").html("OK");
			$("#passwordError").html("");
			if ($("#email").val() == emailInCookies) {
				$("#emailOk").html("OK");
				$("#LoginButton").prop('disabled', false);
			}
			if ($("#emailOk").html()=="OK") {
				$("#LoginButton").prop('disabled', false);
			}
		}
		
	});
	
	
	
	// (4) FINAL CHECK FOR ALL CREDENTIALS BEFORE SUBMIT
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
				required: "",
				email: "Please enter a valid email!"
			},
			"password": {
				required: ""
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
				// If login with "Remember Login Credentials" checked
				if ($("#rememberLogin").prop('checked')) {
					setCookies("email",emailInput,5);
					console.log("Save Email to cookie ["+emailInput+"]");
					setCookies("password",passwordInput,5);
					console.log("Save Password to cookie ["+passwordInput+"]")
				}
				form.submit();
			}
		
		}

	});
	
});

function getCookies(cookieName) {
	var cookiePattern = RegExp(cookieName+'=([^;]+)');
	var cookieData = document.cookie;
	var dataArray = cookieData.match(cookiePattern);
	if (!dataArray) {
		return null;
	} else if (cookieData.includes(cookieName)){
		console.log("Found ["+cookieName+"] in cookie Name=["+dataArray[0]+"] Value=["+dataArray[1]+"]")
		return dataArray[1]; // 0 = cookieName, 1 = cookieValue
	}
}

function setCookies(cookieName, cookieValue, expireDays) {
	var expireDate = new Date();
	expireDate.setDate(expireDate.getDate()+expireDays);
	document.cookie = cookieName+"="+cookieValue+";expire="+expireDate;
	console.log(document.cookie);
};

function delCookies(cookieName) {
	setCookies (cookieName, "", 0)
	
};



