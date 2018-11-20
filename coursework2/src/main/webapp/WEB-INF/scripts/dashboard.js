		$(document).ready(function(){
	    	
	    	var questionStatusCode = $("#statusCode").html();
	    	$("#statusCode").hide();
	    	console.log("Vote status ["+questionStatusCode+"]");
	    	
	    	if (questionStatusCode==1) { // Status 1 means vote is currently open
	    		$("#statusOpen").html("OPEN");
	    		$("#changeStatusButton").val("Close");
	    	} else if (questionStatusCode==0) { // Status 0 means vote is closed
	    		$("#statusClosed").html("CLOSED");
	    		$("#changeStatusButton").val("Open");
	    	} else { // Button will be disabled in case of unknown status (which normally not gonna happen)
	    		$("#statusUnknown").html("UNKNOWN");
	    		$("#changeStatusButton").prop('disabled', true);
	    	}
	    	
	    	$("#changeStatusButton").click(function(){
	    		var buttonLabel = $("#changeStatusButton").val();
	    		var questionId = $("#questionRefId").html();
	    		if (buttonLabel == "Close") {
	    			$("#statusOpen").html("");
		    		
		    		$.ajax({
		    			type: "POST",
		    			url: "/dashboard/changeVoteStatus",
		    			data: "refId="+questionId,
		    			success: function(response){
		    				if (response.includes("CLOSED")) { // question closed in database
		    					$("#statusClosed").html("CLOSED")
		    					console.log("Question ["+questionId+"] closed successfully");
		    					$("#changeStatusButton").val("Open");
		    					
		    				} else { // failed to close question in database
		    					console.log("Failed to close Question ["+questionId+"]");
		    					$("#changeStatusButton").val("Close");
		    				}
		    				
		    			},
			    		error: function (error) {
							alert("Error occured: " + error + "\n Please check your database connection.");
						}
		    		});
		    		
		    		
		    		
	    		} else { // if button label is "Open"
		    		$("#statusClosed").html("")
		    		
		    		$.ajax({
		    			type: "POST",
		    			url: "/dashboard/changeVoteStatus",
		    			data: "refId="+questionId,
		    			success: function(response){
		    				if (response.includes("OPENED")) { // question closed in database
		    					$("#statusOpen").html("OPEN");
		    					console.log("Question ["+questionId+"] opened successfully");
		    					$("#changeStatusButton").val("Close");
		    					
		    				} else { // failed to close question in database
		    					console.log("Failed to open Question ["+questionId+"]");
		    					$("#changeStatusButton").val("Open");
		    				}
		    			},
			    		error: function (error) {
							alert("Error occured: " + error + "\n Please check your database connection.");
						}
		    		});
		    		
	    		}
	    		
	    	});
	    	
	    });