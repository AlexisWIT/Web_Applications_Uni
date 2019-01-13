/**
 * 
 */


function confirmSelect(personData) {
	
	$("#personInfo").val(personData.key);
	$("#personName").val(personData.name);
	$("#personDob").val(personData.dob);
	$("#personGender").val(personData.g);
	$("#personMkey").val(personData.m);
	$("#personFkey").val(personData.f);
	
	$("#editPersonBtn").show();
	$("#deletePersonBtn").show();
	$("#showAncestorBtn").show();
	$("#showDescendantBtn").show();
	
}

function editPersonByClickObject(personData) {
	
	$("#editErrorAlert").fadeOut();
	$("#editCompleteAlert").fadeOut();
	$("#editInProgressAlert").fadeOut();
	
	// Initialise edit inputs
	$("#editToken").val('');
	$("#edit-birthday").val('');
	$("#edit-gender").val('');
	$("#edit-motherKey").val('');
	$("#edit-fatherKey").val('');
	
	console.log("Start editing person ["+personData.key+"]");
	$("#edit-personKey").val(personData.key);
	$("#edit-name").val(personData.name);
	if (personData.dob !== undefined && personData.dob != null)
	$("#edit-birthday").val(personData.dob);
	if (personData.g !== undefined && personData.g != null)
	$("#edit-gender").val(personData.g);
	if (personData.m !== undefined && personData.m != null)
	$("#edit-motherKey").val(personData.m);
	if (personData.f !== undefined && personData.f != null)
	$("#edit-fatherKey").val(personData.f);
	
	$("#editToken").val(personData.key);
	
	$("#editPersonModal").modal("show");
}

function cancelSelect() {
	$("#personInfo").val('');
	$("#personName").val('');
	$("#personDob").val('');
	$("#personGender").val('');
	$("#personMkey").val('');
	$("#personFkey").val('');
	
	$("#showDescendantBtn").hide();
	$("#showAncestorBtn").hide();
	$("#deletePersonBtn").hide();
	$("#editPersonBtn").hide();
}
