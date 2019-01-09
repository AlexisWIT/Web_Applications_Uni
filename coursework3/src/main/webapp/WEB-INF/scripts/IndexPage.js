/**
 * Scripts for Index page
 */

$(document).ready(function () {
	
	
	$("#showFamilyTree").click(function(){
		
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
				
		});
		
	});
	
	
	$("#showAncestorBtn").click(function(){
		
		console.log("Request for Ancestor tree");
		
		$.ajax({
			type:"GET",
			url:"/GE/AncestorTree",
			data:"key="+11,
			success:function(ancestorTreeResponse) {
				
				console.log("Received Ancestor tree");
				var ancestorData = ancestorTreeResponse.memberList;
				
				console.log("Type: "+typeof ancestorData);
				console.log(ancestorTreeResponse.memberList);
				initFamilyTree(ancestorData);
				
			}
				
		});
		
	});
	
	$("#showDescendantBtn").click(function(){
		
		console.log("Request for Descendant tree");
		
		$.ajax({
			type:"GET",
			url:"/GE/DescendantTree",
			data:"key="+2,
			success:function(descendantTreeResponse) {
				
				console.log("Received Descendant tree");
				var descendantData = descendantTreeResponse.memberList;
				
				console.log("Type: "+typeof descendantData);
				console.log(descendantTreeResponse.memberList);
				initFamilyTree(descendantData);
				
			}
				
		});
		
	});
	
	
	
	
	
});