/**
 * Scripts for Index page
 */

$(document).ready(function () {
	
	
	$("#showFamilyTree").click(function(){
		
		$("#showDescendantBtn").hide();
		$("#showAncestorBtn").hide();
		$("#deletePersonBtn").hide();
		$("#editPersonBtn").hide();
		
		$("#homeTab").removeClass("active");
		$("#personsTab").removeClass("active");
		$("#treeTab").addClass("active");
		$("#intro-content").hide();
		$("#familyTreeSection").show();
		
		$("#loadingWindow").modal('show');
		console.log("Request for family tree");
		
		$.ajax({
			type:"GET",
			url:"/GE/FamilyTree",
			success:function(familyTreeResponse) {
				var familyData = familyTreeResponse.memberList;
				
				console.log("Received Data Type: "+typeof familyData);
				console.log(familyTreeResponse.memberList);
				initFamilyTree(familyData);
				$("#loadingWindow").modal('hide');
				
			}
				
		});
		
	});
	
	
	$("#showAncestorBtn").click(function(){
		
		var selectedPerson = $("#personInfo").val();
		console.log("Request for Ancestor tree of ["+selectedPerson+"]");
		$("#loadingWindow").modal('show');
		
		$("#personInfo").val("");
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
				$("#loadingWindow").modal('hide');
				
			}
				
		});
		
	});
	
	
	$("#showDescendantBtn").click(function(){
		
		var selectedPerson = $("#personInfo").val();
		console.log("Request for Descendant tree of ["+selectedPerson+"]");
		$("#loadingWindow").modal('show');
		
		$("#personInfo").val("");
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
				$("#loadingWindow").modal('hide');
				
			}
				
		});
		
	});
	
	
	$("#refreshTreeBtn").click(function(){
		$("#showAncestorBtn").hide();
		$("#showDescendantBtn").hide();
		$("#loadingWindow").modal('show');
		
		$("#personInfo").val("");
		$("#showDescendantBtn").hide();
		$("#showAncestorBtn").hide();
		$("#deletePersonBtn").hide();
		$("#editPersonBtn").hide();
		
		console.log("Request for family tree");
		
		$.ajax({
			type:"GET",
			url:"/GE/FamilyTree",
			success:function(familyTreeResponse) {
				var familyData = familyTreeResponse.memberList;
				
				console.log("Received Data Type: "+typeof familyData);
				console.log(familyTreeResponse.memberList);
				initFamilyTree(familyData);
				$("#loadingWindow").modal('hide');
				
			}
				
		});
		
		
	});
	
	$("#addPersonBtn").click(function(){
		$("#modeToken").val('1');
		
	});
	
	$("#addMultiplePersonLink").click(function(){
		$("#modeToken").val('2');
		$("#personKey").val('');
		$("#name").val('');
		$("#birthday").val('');
		$("#gender").val('');
		$("#motherKey").val('');
		$("#fatherKey").val('');
		$("#addSinglePerson").hide();
		$("#addMultiplePerson").show();
		
		$("#singleAddErrorAlert").fadeOut();
		$("#singleAddCompleteAlert").fadeOut();
		$("#singleAddInProgressAlert").fadeOut();
		
	});
	
	$("#addSinglePersonLink").click(function(){
		$("#modeToken").val('1');
		$("#mutipleAddArea").val('');
		$("#addMultiplePerson").hide();
		$("#addSinglePerson").show();
		
		$("#multiAddErrorAlert").fadeOut();
		$("#multiAddCompleteAlert").fadeOut();
		$("#multiAddInProgressAlert").fadeOut();
		
	});
	
	$("#addPersonBtn").click(function(){
		$("#multiAddInProgressAlert").fadeOut();
		$("#multiAddErrorAlert").fadeOut();
		$("#multiAddCompleteAlert").fadeOut();
		$("#singleAddInProgressAlert").fadeOut();
		$("#singleAddErrorAlert").fadeOut();
		$("#singleAddCompleteAlert").fadeOut();
		
	});
	
	$("#submit-newPerson").click(function(){
		$("#multiAddInProgressAlert").fadeOut();
		$("#multiAddErrorAlert").fadeOut();
		$("#multiAddCompleteAlert").fadeOut();
		$("#singleAddInProgressAlert").fadeOut();
		$("#singleAddErrorAlert").fadeOut();
		$("#singleAddCompleteAlert").fadeOut();
		
		$("#personInfo").val("");
		$("#showDescendantBtn").hide();
		$("#showAncestorBtn").hide();
		$("#deletePersonBtn").hide();
		$("#editPersonBtn").hide();
		
		if ($("#modeToken").val()==1) {
			
			console.log("Input Mode ["+$("#modeToken").val()+"]");
			
			$("#form-addNew").validate({
				errorClass:'errors',
				rules: {
					"personKey":{
						required:true,
						digits:true
					},
					"name":{
						required:true,
						maxlength: 36
					},
					"birthday":{
						maxlength: 8,
						minlength: 8,
						digits:true
					},
					"gender":{
						maxlength: 8
					},
					"motherKey":{
						digits:true
					},
					"fatherKey":{
						digits:true
					}
					
				},
				messages:{
					"personKey":{
						required: "This field is required!",
						digits: "Invalid person key (numbers only)!"
					},
					"name":{
						required: "This field is required!",
						maxlength: "Name must be less than 36 letters!"
					},
					"birthday":{
						maxlength: "Invalid birthday (format: yyyymmdd)",
						minlength: "Invalid birthday (format: yyyymmdd)",
						digits: "Invalid birthday (format: yyyymmdd)!"
					},
					"gender":{
						maxlength: "Gender must be less than 8 numbers ('male' or 'female')!"
					},
					"motherKey":{
						digits: "Invalid mother key (numbers only)!"
					},
					"fatherKey":{
						digits: "Invalid father key (numbers only)!"
					}
				},
				submitHandler:function(form){
					
					$("#singleAddInProgressInfo").html("<strong>Please wait...</strong> Your request is being processing.");
					$("#singleAddInProgressAlert").fadeIn();
					
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
					
					var data = '{"key": "'+key+'", "name": "'+name+'", "dob": "'+dob+'", "gender": "'+gender+'", "mkey": "'+mkey+'", "fkey": "'+fkey+'"}';
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
								console.log(response.message);
								$("#singleAddInProgressAlert").toggle();
								$("#singleAddFailedInfo").html("<strong>Failed to add new person!</strong> "+response.message);
								$("#singleAddErrorAlert").fadeIn(1000);
								
							} else if (response.result=="true") {
								$("#singleAddInProgressAlert").toggle();
								$("#singleAddCompleteInfo").html("<strong>Done!</strong> "+response.message);
								$("#singleAddCompleteAlert").fadeIn();
								completeChange(response.result);
								$("#addPersonModal").modal('hide');
								
							}
							
						}
					
					});
				}
					
			})
			
			
		} else if ($("#modeToken").val()==2) {
			
			console.log("Input Mode ["+$("#modeToken").val()+"]");
			
			$("#personInfo").val("");
			$("#showDescendantBtn").hide();
			$("#showAncestorBtn").hide();
			$("#deletePersonBtn").hide();
			$("#editPersonBtn").hide();
			
			$("#form-addNew").validate({
				errorClass:'errors',
				rules: {
					"mutipleAddArea":{
						required:true
					}
					
				},
				messages:{
					"mutipleAddArea":{
						required: "This field is required!"
					}
				},
				submitHandler:function(form){
					
					$("#multiAddInProgressInfo").html("<strong>Please wait...</strong> Your request is being processing.");
					$("#multiAddInProgressAlert").fadeIn();
					
					var data = $("#mutipleAddArea").val();
					console.log("Data input: "+data);
					
					try {
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
									console.log(response.message);
									$("#multiAddInProgressAlert").toggle();
									$("#multiAddFailedInfo").html("<strong>Failed to add new person!</strong> "+response.message);
									$("#multiAddErrorAlert").fadeIn(1000);
									
								} else if (response.result=="true") {
									$("#multiAddInProgressAlert").toggle();
									$("#multiAddCompleteInfo").html("<strong>Done!</strong> "+response.message);
									$("#multiAddCompleteAlert").fadeIn();
									completeChange(response.result);
									$("#addPersonModal").modal('hide');
									
								}
								
							}
						
						});
						
					} catch (exception) {
						$("#multiAddInProgressAlert").toggle();
						$("#multiAddFailedInfo").html("<strong>JSON Input "+exception.name+":</strong> "+exception.message);
						$("#multiAddErrorAlert").fadeIn(1000);

					}
					
				}
					
			})
			
			
		}
		
		
	});
	
	$("#cancel-editedPerson").click(function(){
		$("#editErrorAlert").fadeOut();
		$("#editCompleteAlert").fadeOut();
		$("#editInProgressAlert").fadeOut();
		
		$("#personInfo").val("");
		$("#personName").val('');
		$("#personDob").val('');
		$("#personGender").val('');
		$("#personMkey").val('');
		$("#personFkey").val('');
		
	});
	
	$("#cancel-editedPersonIcon").click(function(){
		$("#editErrorAlert").fadeOut();
		$("#editCompleteAlert").fadeOut();
		$("#editInProgressAlert").fadeOut();
		
		$("#personInfo").val("");
		$("#personName").val('');
		$("#personDob").val('');
		$("#personGender").val('');
		$("#personMkey").val('');
		$("#personFkey").val('');
	});
	
	$("#editPersonBtn").click(function(){
		$("#edit-personKey").val($("#personInfo").val());
		$("#edit-name").val($("#personName").val());
		
		if ($("#personDob").val() !== undefined && $("#personDob").val() != null)
		$("#edit-birthday").val($("#personDob").val());
		
		if ($("#personGender").val() !== undefined && $("#personGender").val() != null)
		$("#edit-gender").val($("#personGender").val());
		
		if ($("#personMkey").val() !== undefined && $("#personMkey").val() != null)
		$("#edit-motherKey").val($("#personMkey").val());
		
		if ($("#personFkey").val() !== undefined && $("#personFkey").val() != null)
		$("#edit-fatherKey").val($("#personFkey").val());
		
		$("#editToken").val($("#personInfo").val());
		
	});
	
	$("#submit-editedPerson").click(function(){
		$("#editErrorAlert").fadeOut();
		$("#editCompleteAlert").fadeOut();
		$("#editInProgressAlert").fadeOut();

		$("#personInfo").val("");
		$("#showDescendantBtn").hide();
		$("#showAncestorBtn").hide();
		$("#deletePersonBtn").hide();
		$("#editPersonBtn").hide();
		
		$("#form-edit").validate({
			errorClass:'errors',
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
				$("#editInProgressInfo").html("");
				$("#editInProgressInfo").html("<strong>Please wait...</strong> Your request is being processing.");
				$("#editInProgressAlert").fadeIn();
				
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
				var actualKey = $("#editToken").val();
				
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
							console.log(response.message);
							$("#editInProgressAlert").toggle();
							$("#editFailedInfo").html("");
							$("#editFailedInfo").html("<strong>Update person failed!</strong> "+response.message);
							$("#editErrorAlert").fadeIn(1000);		
							
						} else if (response.result=="true") {
							$("#editInProgressAlert").toggle();
							$("#editCompleteInfo").html("<strong>Done!</strong> "+response.message);
							$("#editCompleteAlert").fadeIn();
							completeChange(response.result);
							$("#editPersonModal").modal('hide');
							
						}
						
					}
				
				});
					
			}

		});
			
	});
	
	$("#confirm-deletePerson").click(function(){
		
		$("#loadingWindow").modal('show');
		
		$("#showDescendantBtn").hide();
		$("#showAncestorBtn").hide();
		$("#deletePersonBtn").hide();
		$("#editPersonBtn").hide();
		
		
		var selectedPerson = $("#personInfo").val();
		console.log("Request for delete person ["+selectedPerson+"]");
		
		$("#personInfo").val("");
		
		
		
		$.ajax({
			type:"GET",
			url:"/GE/person/delete/"+selectedPerson,
			success:function(familyTreeResponse) {
				console.log("Refereshing family tree...");
				completeChange(familyTreeResponse.result)

			}
				
		});
		
		
		$("#deletePersonModal").modal('hide');
		
	});
	
	
	$("#homeTab").click(function(){
		$("#showDescendantBtn").hide();
		$("#showAncestorBtn").hide();
		$("#deletePersonBtn").hide();
		$("#editPersonBtn").hide();
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
		$("#showDescendantBtn").hide();
		$("#showAncestorBtn").hide();
		$("#deletePersonBtn").hide();
		$("#editPersonBtn").hide();
		completeChange("true");
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
	
	$("#personInfo").val("");
	$("#personName").val('');
	$("#personDob").val('');
	$("#personGender").val('');
	$("#personMkey").val('');
	$("#personFkey").val('');
	
	$("#showDescendantBtn").hide();
	$("#showAncestorBtn").hide();
	$("#deletePersonBtn").hide();
	$("#editPersonBtn").hide();
	
	$("#editErrorAlert").fadeOut();
	$("#editCompleteAlert").fadeOut();
	$("#editInProgressAlert").fadeOut();
	$("#multiAddInProgressAlert").fadeOut();
	$("#multiAddErrorAlert").fadeOut();
	$("#multiAddCompleteAlert").fadeOut();
	$("#singleAddInProgressAlert").fadeOut();
	$("#singleAddErrorAlert").fadeOut();
	$("#singleAddCompleteAlert").fadeOut();
	
	if (result=="true") {
		$("#loadingWindow").modal('show');
		$.ajax({
			type:"GET",
			url:"/GE/FamilyTree",
			success:function(familyTreeResponse) {
				var familyData = familyTreeResponse.memberList;
				
				console.log("Received Data Type: "+typeof familyData);
				console.log(familyTreeResponse.memberList);
				initFamilyTree(familyData);
				$("#loadingWindow").modal('hide');
				console.log("Family Tree Refereshed");
				
			}
				
		});
	}
	
	
	
}
