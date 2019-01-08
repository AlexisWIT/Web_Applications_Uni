/**
 * Scripts for Index page
 */

$(document).ready(function () {
	
	console.log("Request for family tree");
	
	$.ajax({
		type:"GET",
		url:"/GE/FamilyTree",
		success:function(familyTreeResponse) {
			
			console.log("Received family tree");
			var familyData = familyTreeResponse.memberList;
			
			console.log("Type: "+typeof familyData);
			console.log(familyTreeResponse.memberList);
			initFamilyTree(familyData);
			
			
		}
			
	})
	
	
	
	
	
});