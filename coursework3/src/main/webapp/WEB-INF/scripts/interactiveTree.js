/**
 * 
 */


function showPersonKey(personKey) {
	
	$("#personInfo").html(personKey);
	$("#editPersonBtn").show();
	$("#deletePersonBtn").show();
	$("#showAncestorBtn").show();
	$("#showDescendantBtn").show();
	
}

function editPersonDetail(personData) {
	
	$("#editPersonModal").modal("show");
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
	
	$("#editToken").html(personData.key);
}
