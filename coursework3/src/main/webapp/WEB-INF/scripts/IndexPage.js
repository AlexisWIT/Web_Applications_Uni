/**
 * Scripts for Index page
 */

$(document).ready(function () {
	
	
	$("#showFamilyTree").click(function(){

		$("#homeTab").removeClass("active");
		$("#personsTab").removeClass("active");
		$("#treeTab").addClass("active");
		$("#intro-content").hide();
		$("#familyTreeSection").show();
		
		console.log("Request for family tree");
		
		$.ajax({
			type:"GET",
			url:"/GE/FamilyTree",
			success:function(familyTreeResponse) {
				var familyData = familyTreeResponse.memberList;
				
				console.log("Received Data Type: "+typeof familyData);
				console.log(familyTreeResponse.memberList);
				initFamilyTree(familyData);
				
			}
				
		});
		
	});
	
	
	$("#showAncestorBtn").click(function(){
		
		var selectedPerson = $("#personInfo").html();
		console.log("Request for Ancestor tree of ["+selectedPerson+"]");
		$("#personInfo").html("");
		$("#showDescendantBtn").hide();
		$("#showAncestorBtn").hide();
		$("#deletePersonBtn").hide();
		$("#editPersonBtn").hide();
		
		$.ajax({
			type:"GET",
			url:"/GE/AncestorTree",
			data:"key="+selectedPerson,
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
		
		var selectedPerson = $("#personInfo").html();
		console.log("Request for Descendant tree of ["+selectedPerson+"]");
		$("#personInfo").html("");
		$("#showDescendantBtn").hide();
		$("#showAncestorBtn").hide();
		$("#deletePersonBtn").hide();
		$("#editPersonBtn").hide();
		
		$.ajax({
			type:"GET",
			url:"/GE/DescendantTree",
			data:"key="+selectedPerson,
			success:function(descendantTreeResponse) {
				
				console.log("Received Descendant tree");
				var descendantData = descendantTreeResponse.memberList;
				
				console.log("Type: "+typeof descendantData);
				console.log(descendantTreeResponse.memberList);
				initFamilyTree(descendantData);
				
			}
				
		});
		
	});
	
	
	$("#refreshTreeBtn").click(function(){
		$("#showAncestorBtn").hide();
		$("#showDescendantBtn").hide();
		console.log("Request for family tree");
		
		$.ajax({
			type:"GET",
			url:"/GE/FamilyTree",
			success:function(familyTreeResponse) {
				var familyData = familyTreeResponse.memberList;
				
				console.log("Received Data Type: "+typeof familyData);
				console.log(familyTreeResponse.memberList);
				initFamilyTree(familyData);
				
			}
				
		});
		
		
	});
	
	$("#addMultiplePersonLink").click(function(){
		$("#personKey").val('');
		$("#name").val('');
		$("#birthday").val('');
		$("#gender").val('');
		$("#motherKey").val('');
		$("#fatherKey").val('');
		$("#addSinglePerson").hide();
		$("#addMultiplePerson").show();
		
	});
	
	$("#addSinglePersonLink").click(function(){
		$("#mutipleAddArea").val('');
		$("#addMultiplePerson").hide();
		$("#addSinglePerson").show();
		
	});
	
	$("#submit-newPerson").click(function(){
		
		if ($("#personKey").val()!='') {
			var key = $("#personKey").val();
			var name = $("#name").val();
			
			var dob = $("#birthday").val();
			if (dob === undefined || dob == "" ) dob=null;
			var gender = $("#gender").val();
			if (gender === undefined || gender == "" ) gender=null;
			var mkey = $("#motherKey").val();
			if (mkey === undefined || mkey == "" ) mkey=null;
			var fkey = $("#fatherKey").val();
			if (fkey === undefined || fkey == "" ) fkey=null;
			
			var data = '{ "newPerson" : { "key": "'+key+'", "name": "'+name+'", "dob": "'+dob+'", "gender": "'+gender+'", "mkey": "'+mkey+'", "fkey": "'+fkey+'"}}';
			console.log(data);
			
			var jsonData = JSON.parse(data);
			console.log("Data sent: "+jsonData);
			
			
			$.ajax({
				type: "POST",
				url: "/GE/person/addJSON",
				contentType: "application/json",
				dataType: 'json',
				data: JSON.stringify(jsonData),
				success:function(response){
					console.log(response.result);
					if(response.result=="false"){
						$("#singleAddInProgressAlert").hide();
						$("#singleAddFailedInfo").html("<strong>Update person failed!</strong> "+response.message);
						$("#singleAddErrorAlert").alert();
						
					} else if (response.result=="true") {
						$("#singleAddInProgressAlert").hide();
						$("#singleAddCompleteInfo").html("<strong>Done!</strong> "+response.message);
						$("#singleAddCompleteAlert").show();
						completeChange(response.result);
						$("#singleAddPersonModal").modal('hide');
						
					}
					
				}
			
			});
			
		} else if ($("#mutipleAddArea").val() != '') {
			
			
			var data = $("#mutipleAddArea").val();
			console.log(data);
			
			var jsonData = JSON.parse(data);
			console.log("Data sent: "+jsonData);
			
			$.ajax({
				type: "POST",
				url: "/GE/person/addJSON",
				contentType: "application/json",
				dataType: 'json',
				data: JSON.stringify(jsonData),
				success:function(response){
					console.log(response.result);
					if(response.result=="false"){
						$("#multiAddInProgressAlert").hide();
						$("#multiAddFailedInfo").html("<strong>Update person failed!</strong> "+response.message);
						$("#multiAddErrorAlert").show();
						
					} else if (response.result=="true") {
						$("#multiAddInProgressAlert").hide();
						$("#multiAddCompleteInfo").html("<strong>Done!</strong> "+response.message);
						$("#multiAddCompleteAlert").show();
						completeChange(response.result);
						$("#multiAddPersonModal").modal('hide');
						
					}
					
				}
			
			});
		}
		
		
	});
	
	$("#submit-editedPerson").click(function(){
		
		$("#form-edit").validate({
			errorClass:'alert alert-danger',
			rules: {
				"edit-personKey":{
					required:true,
					digits:true
				},
				"edit-name":{
					required:true,
					maxlength: 36
				},
				"edit-birthday":{
					maxlength: 8,
					minlength: 8,
					digits:true
				},
				"edit-gender":{
					maxlength: 8
				},
				"edit-motherKey":{
					digits:true
				},
				"edit-fatherKey":{
					digits:true
				}
				
			},
			messages:{
				"edit-personKey":{
					required: "This field is required!",
					digits: "Invalid person key (numbers only)!"
				},
				"edit-name":{
					required: "This field is required!",
					maxlength: "Name must be less than 36 letters!"
				},
				"edit-birthday":{
					maxlength: "Invalid birthday (format: yyyymmdd)",
					minlength: "Invalid birthday (format: yyyymmdd)",
					digits: "Invalid birthday (format: yyyymmdd)!"
				},
				"edit-gender":{
					maxlength: "Gender must be less than 8 numbers ('male' or 'female')!"
				},
				"edit-motherKey":{
					digits: "Invalid mother key (numbers only)!"
				},
				"edit-fatherKey":{
					digits: "Invalid father key (numbers only)!"
				}
			},
			submitHandler:function(form){
				
				$("#editInProgressInfo").html("<strong>Please wait...</strong> Your request is being processing.");
				$("#editInProgressAlert").alert();
				
				var key = $("#edit-personKey").val();
				var name = $("#edit-name").val();
				
				var dob = $("#edit-birthday").val();
				if (dob === undefined || dob == "" ) dob=null;
				var gender = $("#edit-gender").val();
				if (gender === undefined || gender == "" ) gender=null;
				var mkey = $("#edit-motherKey").val();
				if (mkey === undefined || mkey == "" ) mkey=null;
				var fkey = $("#edit-fatherKey").val();
				if (fkey === undefined || fkey == "" ) fkey=null;
				var actualKey = $("#editToken").html();
				
				var data = '{ "key": "'+key+'", "name": "'+name+'", "dob": "'+dob+'", "gender": "'+gender+'", "mkey": "'+mkey+'", "fkey": "'+fkey+'", "actualKey": "'+actualKey+'"}';
				console.log(data);
				
				var jsonData = JSON.parse(data);
				console.log("Data sent: "+jsonData);
				
				$.ajax({
					type: "POST",
					url: "/GE/editPersonJSON",
					contentType: "application/json",
					dataType: 'json',
					data: JSON.stringify(jsonData),
					success:function(response){
						console.log(response.result);
						if(response.result=="false"){
							$("#editInProgressAlert").hide();
							$("#editFailedInfo").html("<strong>Update person failed!</strong> "+response.message);
							$("#editErrorAlert").show();
							
						} else if (response.result=="true") {
							$("#editInProgressAlert").hide();
							$("#editCompleteInfo").html("<strong>Done!</strong> "+response.message);
							$("#editCompleteAlert").show();
							completeChange(response.result);
							$("#editPersonModal").modal('hide');
							
						}
						
					}
				
				});
					
			}

		});
			
	});
	
	$("#confirm-deletePerson").click(function(){
		
		var selectedPerson = $("#personInfo").html();
		console.log("Request for delete person ["+selectedPerson+"]");
		$("#personInfo").html("");
		$("#showDescendantBtn").hide();
		$("#showAncestorBtn").hide();
		$("#deletePersonBtn").hide();
		$("#editPersonBtn").hide();
		
		
		$.ajax({
			type:"GET",
			url:"/GE/person/delete/"+selectedPerson,
			success:function(familyTreeResponse) {
				completeChange(familyTreeResponse.result)
				
			}
				
		});
		
		
		$("#deletePersonModal").modal('hide');
		
	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	$("#homeTab").click(function(){
		$("#homeTab").addClass("active");
		$("#personsTab").removeClass("active");
		$("#treeTab").removeClass("active");

		$("#intro-content").show();
		$("#familyTreeSection").hide();
	});
	
	$("#personsTab").click(function(){
		$("#homeTab").removeClass("active");
		$("#personsTab").addClass("active");
		$("#treeTab").removeClass("active");
		
		$("#intro-content").hide();
		$("#familyTreeSection").hide();
	});
	
	$("#treeTab").click(function(){
		$("#homeTab").removeClass("active");
		$("#personsTab").removeClass("active");
		$("#treeTab").addClass("active");
		
		$("#intro-content").hide();
		$("#familyTreeSection").show();
	});
	
	$("#moreOperationTab").click(function(){
		$("#homeTab").removeClass("active");
		$("#personsTab").removeClass("active");
		$("#treeTab").removeClass("active");

	});
	
	
	
});

function completeChange(result){
	
	if (result=="true") {
		
		$.ajax({
			type:"GET",
			url:"/GE/FamilyTree",
			success:function(familyTreeResponse) {
				var familyData = familyTreeResponse.memberList;
				
				console.log("Received Data Type: "+typeof familyData);
				console.log(familyTreeResponse.memberList);
				initFamilyTree(familyData);
				
			}
				
		});
	}
	
	
	
}
