$(document).ready(function () {

	// EMAIL VALIDITY + AVAILABLITY
	$("#email").keyup(function () {
		// check validation of the email entered
		var emailInput = $("#email").val();
		var emailPattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

		// if empty value
		if (emailInput.length == 0) {
			$("#emailInfo").html("<span class='error'>Please enter your email!</span>");
			$("#register").prop('disabled', true); //disable the submit button

			// if email entered with CORRECT pattern
		} else if (emailInput.match(emailPattern)) {
			console.log("Sent request for checking [" + emailInput + "]")
			// use ajax to check the input email with database record
			$("#emailInfo").html("<span class='wait'>Checking your Email. Please wait...</span>");

			$.ajax({ // check class file 'SignupController'
				type: "POST",
				url: "/signup/emailCheck",
				data: "email=" + emailInput,
				success: function (response) {
					console.log("Received emailReport for [" + emailInput + "]")
					$("#emailInfo").html(response);
					// if email available, response should be "\<span id='emailOk' class='ok'\>OK\</span\>"
					// otherwise, should be "\<span class='error'\>Email aready in use!\</span\>"
					if (!response.includes("OK")) {
						$("#register").prop('disabled', true);
					}
				},
				error: function (error) {
					alert("Error occured: " + error);
				}

			});
			//$("#emailInfo").html("<span id='emailOk' class='ok'>OK</span>");

			// else (must be wrong pattern)
		} else {
			$("#emailInfo").html("<span class='error'>Please enter a valid email!</span>");
			$("#register").prop('disabled', true);
		}

	});

	//	// EMAIL AVAILABLILITY
	//	$("#email").focusout(function(){
	//		
	//		var emailInput = $("#email").val();
	//	});

	// FAMILYNAME VALIDITY
	var namePattern = /^(?:[\u4e00-\u9fa5]+)(?:·[\u4e00-\u9fa5]+)*$|^[a-zA-Z0-9]+\s?[\.·\-()a-zA-Z]*[a-zA-Z]+$/;
	$("#familyName").keyup(function () {
		// check if contains number or exceeds the max length
		var familyNameInput = $("#familyName").val();

		// if empty value
		if (familyNameInput.length == 0) {
			$("#familyNameInfo").html("<span class='error'>Please enter your family name!</span>");
			$("#register").prop('disabled', true);

			// if family name entered with valid characters	(which is OK)
		} else if (familyNameInput.match(namePattern)) {
			$("#familyNameInfo").html("<span id='familyNameOk' class='ok'>OK</span>");

			// else (invalid characters detected)
		} else {
			$("#familyNameInfo").html("<span class='error'>Invalid input!</span>");
			$("#register").prop('disabled', true);
		}

	});

	// GIVENNAME VALIDITY
	$('#givenName').keyup(function () {
		// check if contains number or exceeds the max length
		var givenNameInput = $("#givenName").val();

		// if empty value
		if (givenNameInput.length == 0) {
			$("#givenNameInfo").html("<span class='error'>Please enter your given name!</span>");
			$("#register").prop('disabled', true);

			// if given name entered with valid characters (which is OK)
		} else if (givenNameInput.match(namePattern)) {
			$("#givenNameInfo").html("<span id='givenNameOk' class='ok'>OK</span>");

			// else (invalid characters detected)
		} else {
			$("#givenNameInfo").html("<span class='error'>Invalid input!</span>")
			$("#register").prop('disabled', true);
		}
	});

	// BIRTHDAY VALIDITY
	$("#dateOfBirthString").focusout(function () {
		// check if less than 16 years old
		var ageLimit = 16;
		var todayDate = new Date();
		var dateOfBirthStringInput = $("#dateOfBirthString").val();
		var dateOfBirthInput = new Date($("#dateOfBirthString").val());
		var yearDiff = todayDate.getFullYear() - dateOfBirthInput.getFullYear();
		var monthDiff = todayDate.getMonth() - dateOfBirthInput.getMonth();
		var dateDiff = todayDate.getDate() - dateOfBirthInput.getDate();

		// if empty value
		if (dateOfBirthStringInput.length == 0) {
			console.log("Age=NULL: Failed");
			$("#dateOfBirthStringInfo").html("<span class='error'>Please enter your birthday!</span>");
			$("#register").prop('disabled', true);

			// if 16+ years old	
		} else if (yearDiff > ageLimit) {
			$("#dateOfBirthStringInfo").html("<span id='dateOfBirthStringOk' class='ok'>OK</span>");

			// if 16- years old 
		} else if (yearDiff < ageLimit) {
			$("#dateOfBirthStringInfo").html("<span class='error'>You must be at least 16 years old!</span>");
			$("#register").prop('disabled', true);

			// if depends on months or dates
		} else if ((monthDiff === 0 && dateDiff >= 0) || monthDiff > 0) {
			$("#dateOfBirthStringInfo").html("<span id='dateOfBirthStringOk' class='ok'>OK</span>");

			// else less than 15 years old
		} else {
			$("#dateOfBirthStringInfo").html("<span class='error'>You must be at least 16 years old!</span>");
			$("#register").prop('disabled', true);
		}

	});


	// ADDRESS VALIDITY
	$("#address").keyup(function () {
		// check if empty or contains space only
		var addressLengthLimit = 96;
		var addressInput = $("#address").val();

		// if empty value
		if (addressInput.length == 0) {
			$("#addressInfo").html("<span class='error'>Please enter your address!</span>");
			$("#register").prop('disabled', true);

			// if input address has more than 96 chars	
		} else if (addressInput.length > addressLengthLimit) {
			$("#addressInfo").html("<span class='error'>No more than 96 characters!</span>");
			$("#register").prop('disabled', true);

			// else (valid address)
		} else {
			$("#addressInfo").html("<span id='addressOk' class='ok'>OK</span>");
		}

	});

	// PASSWORD VALIDITY
	$("#password").keyup(function () {
		// check password strength
		var passwordInput = $("#password").val();
		// must be 6-12 characters, contains at least 1 lowercase and 1 uppercase alphabetical character 
		// or has at least 1 lowercase and 1 numeric character 
		// or has at least 1 uppercase and 1 numeric character.
		var passwordPattern = /^(((?=.*[a-z])(?=.*[A-Z]))|((?=.*[a-z])(?=.*[0-9]))|((?=.*[A-Z])(?=.*[0-9]))).{6,12}$/;

		// if empty value
		if (passwordInput.length == 0) {
			$("#passwordInfo").html("<span class='error'>Please enter your password!</span>");
			$("#register").prop('disabled', true);

			// if valid	
		} else if (passwordInput.match(passwordPattern)) {
			$("#passwordInfo").html("<span id='passwordOk' class='ok'>OK</span>");

			// else (invalid input characters or exceed the max char number)	
		} else {
			$("#passwordInfo").html("<span class='error'>Invalid password!</span>");
			$("#register").prop('disabled', true);
		}

	});

	// PASSWORD CHECK
	$("#passwordCheck").keyup(function () {
		// check if match with #password
		var passwordInput = $("#password").val();
		var passwordInputCheck = $("#passwordCheck").val();

		// if empty value
		if (passwordInputCheck.length == 0) {
			$("#passwordCheckInfo").html("<span class='error'>Please verify your password!</span>");
			$("#register").prop('disabled', true);

			// if match	
		} else if (passwordInputCheck == passwordInput) {
			$("#passwordCheckInfo").html("<span id='passwordCheckOk' class='ok'>OK</span>");

			// if mismatch	
		} else {
			$("#passwordCheckInfo").html("<span class='error'>Password doesn't match!</span>");
			$("#register").prop('disabled', true);
		}

	});

	// BIC VALIDITY + AVAILABLITY
	$("#bioIdCodeString").keyup(function () {
		var bicInput = $("#bioIdCodeString").val();
		// check if matched with pattern, if used (ajax), if in the database record (ajax)
		var bicPattern = /^[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}$/;

		// if empty value
		if (bicInput.length == 0) {
			$("#register").prop('disabled', true);
			$("#bioIdCodeStringInfo").html("<span class='error'>Please enter your BIC!</span>");

			// if mismatch the pattern
		} else if (!bicInput.match(bicPattern)) {
			$("#register").prop('disabled', true);
			$("#bioIdCodeStringInfo").html("<span class='error'>Wrong format!</span>");

			// else use ajax to check with database
		} else {
			$("#register").prop('disabled', true);
			console.log("Sent request for checking [" + bicInput + "]")
			$("#bioIdCodeStringInfo").html("<span class='wait'>Checking your BIC. Please wait...</span>");

			$.ajax({ // check class file 'SignupController'
				type: "POST",
				url: "/signup/bicCheck",
				data: "bioIdCodeString=" + bicInput,
				success: function (response) {
					console.log("Received bicReport for [" + bicInput + "]");
					$("#bioIdCodeStringInfo").html(response);
					// if BIC available, response should be "\<span id='bicOk' class='ok'\>OK\</span\>"
					// otherwise, should be "\<span class='error'\>BIC aready in use!\</span\>"
					if (!response.includes("OK")) {
						$("#register").prop('disabled', true);
					}
				},
				error: function (error) {
					alert("Error occured: " + error + "\n Please check your database connection.");
				}

			});

			//$("#bioIdCodeStringInfo").html("<span id='bioIdCodeStringOk' class='ok'>OK</span>");
		}

	});


	// Disabled button can't fire mouse event, so use surrounding div to detect mouse activity
	$("#registerButtonCover").hover(function (evt) {
		var emailInfo = $("#emailInfo span").html();
		var familyNameInfo = $("#familyNameInfo span").html();
		var givenNameInfo = $("#givenNameInfo span").html();
		var dateOfBirthStringInfo = $("#dateOfBirthStringInfo span").html();
		var addressInfo = $("#addressInfo span").html();
		var passwordInfo = $("#passwordInfo span").html();
		var passwordCheckInfo = $("#passwordCheckInfo span").html();
		var bioIdCodeStringInfo = $("#bioIdCodeStringInfo span").html();

		console.log("==== Checking infos ====");
		console.log("Email = [" + emailInfo + "]");
		console.log("Family Name = [" + familyNameInfo + "]");
		console.log("Given Name = [" + givenNameInfo + "]");
		console.log("Date of Birth = [" + dateOfBirthStringInfo + "]");
		console.log("Address = [" + addressInfo + "]");
		console.log("Password = [" + passwordInfo + "]");
		console.log("Verify Password = [" + passwordCheckInfo + "]");
		console.log("BIC Code = [" + bioIdCodeStringInfo + "]");

		if ((emailInfo === "OK") &&
			(familyNameInfo === "OK") &&
			(givenNameInfo === "OK") &&
			(dateOfBirthStringInfo === "OK") &&
			(addressInfo === "OK") &&
			(passwordInfo === "OK") &&
			(passwordCheckInfo === "OK") &&
			(bioIdCodeStringInfo === "OK")) {
			console.log("==== All Done, Unlock the Button ====");
			$("#register").prop('disabled', false);
		}

	}, function () { // mouse out function
		if ((emailInfo === "OK") &&
			(familyNameInfo === "OK") &&
			(givenNameInfo === "OK") &&
			(dateOfBirthStringInfo === "OK") &&
			(addressInfo === "OK") &&
			(passwordInfo === "OK") &&
			(passwordCheckInfo === "OK") &&
			(bioIdCodeStringInfo === "OK")) {
			console.log("==== All Done, Unlock the Button ====");
			$("#register").prop('disabled', false);
		}

	});

});