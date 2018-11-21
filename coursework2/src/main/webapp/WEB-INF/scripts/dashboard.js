$("#Sheets").hide();
$("#Charts").hide();

$(document).ready(function () {

	// GET VOTE STATUS
	var questionStatusCode = $("#statusCode").html();
	$("#statusCode").hide();
	console.log("Vote status [" + questionStatusCode + "]");

	if (questionStatusCode == 1) { // Status 1 means vote is currently open
		$("#statusOpen").html("OPEN");
		$("#changeStatusButton").val("Close");
	} else if (questionStatusCode == 0) { // Status 0 means vote is closed
		$("#statusClosed").html("CLOSED");
		$("#changeStatusButton").val("Open");
	} else { // Button will be disabled in case of unknown status (which normally not gonna happen)
		$("#statusUnknown").html("UNKNOWN");
		$("#changeStatusButton").prop('disabled', true);
	}


	// CLOSE & OPEN VOTE
	$("#changeStatusButton").click(function () {
		var buttonLabel = $("#changeStatusButton").val();
		var questionId = $("#questionRefId").html();

		// Close vote
		if (buttonLabel == "Close") {
			$("#statusOpen").html("");

			$.ajax({ // Send the instruction [Close Vote] to controller [AdminController]
				type: "POST",
				url: "/dashboard/changeVoteStatus",
				data: "refId=" + questionId,
				success: function (response) {

					if (response.includes("CLOSED")) { // Response: Vote question closed in database
						$("#statusClosed").html("CLOSED")
						console.log("Question [" + questionId + "] closed successfully");
						$("#changeStatusButton").val("Open");

					} else { // Response: Failed to close vote in database
						console.log("Failed to close Question [" + questionId + "]");
						$("#changeStatusButton").val("Close");
					}

				},
				error: function (error) {
					alert("Error occured: " + error + "\n Please check your database connection.");
				}
			});

			// Open vote	
		} else { // if button label is [Open]
			$("#statusClosed").html("")

			$.ajax({ // Send the instruction [Open Vote] to controller [AdminController]
				type: "POST",
				url: "/dashboard/changeVoteStatus",
				data: "refId=" + questionId,
				success: function (response) {

					if (response.includes("OPENED")) { // Response: Vote question opened in database
						$("#statusOpen").html("OPEN");
						console.log("Question [" + questionId + "] opened successfully");
						$("#changeStatusButton").val("Close");

					} else { // Response: Failed to open vote in database
						console.log("Failed to open Question [" + questionId + "]");
						$("#changeStatusButton").val("Open");
					}
				},
				error: function (error) {
					alert("Error occured: " + error + "\nPlease check your database connection.");
				}
			});

		}

	});

	// EDIT QUESTION TITLE
	var currentQuestionTitle = "";
	$("#editQuestionButton").click(function () {
		var buttonLabel = $("#editQuestionButton").val();
		var questionId = $("#questionRefId").html();
		var editedQuestionTitle = "";

		console.log(buttonLabel);

		// Make the question title editable
		if (buttonLabel == "Edit Question") {
			currentQuestionTitle = $("#editableQuestionTitle").html();

			$("#editableQuestionTitle").replaceWith("<textarea id='titleEditBox' rows='6' cols='40'>" + currentQuestionTitle + "</textarea>");
			console.log("Start edit question [" + questionId + "]");

			$("#editQuestionButton").val("Confirm Edit"); // Change button label to [Confirm Edit]

		// Confirm the edited question
		} else if (buttonLabel == "Confirm Edit") {
			editedQuestionTitle = $("#titleEditBox").val();

			console.log(" Current Title [" + currentQuestionTitle + "]");
			console.log(" Edited Title  [" + editedQuestionTitle + "]");

			// If title content changed (need update in database) and not contains spaces only
			if ((editedQuestionTitle != currentQuestionTitle) && (/\S/.test(editedQuestionTitle))) {

				// Send [Question ID] and the edited [Question Title] to the controller [AdminController]
				$.ajax({
					type: "POST",
					url: "/dashboard/editQuestion",
					data: "refId=" + questionId + "&title=" + editedQuestionTitle,
					success: function (response) {

						// Edited title updated in database
						if (response.includes(editedQuestionTitle)) {
							console.log("Question [" + questionId + "] title [" + editedQuestionTitle + "] successfully updated in database");
							$("#titleEditBox").replaceWith("<span id='editableQuestionTitle'>" + editedQuestionTitle + "</span>");
							console.log("Edit confirmed");

						// Unable to update the edited title in database
						} else {
							console.log("Unable to update Question [" + questionId + "] title [" + editedQuestionTitle + "] in database");
							$("#titleEditBox").replaceWith("<span id='editableQuestionTitle'>" + currentQuestionTitle + "</span>");
							console.log("Edit failed");
							alert("Error occured: \nUnable to update Question (" + questionId + ") title [" + editedQuestionTitle + "] in database.\nPlease check your database connection and try again.");
						}
					}
				});

			// If question title unchanged (no need to contact with database)
			} else { 
				$("#titleEditBox").replaceWith("<span id='editableQuestionTitle'>" + currentQuestionTitle + "</span>");
				console.log("Edit confirmed");
			}

			$("#editQuestionButton").val("Edit Question"); // Change button label to [Edit Question]

		}

	});
	
	
	// EDIT OPTION CONTENT
	var currentOptionContent1 = "";
	var currentOptionContent2 = "";
	var currentOptionContent3 = "";
	$("#editOptionButton").click(function () {
		var buttonLabel = $("#editOptionButton").val();
		var optionId1 = $("#optionOptId-1").html();
		var optionId2 = $("#optionOptId-2").html();
		var optionId3 = $("#optionOptId-3").html();
		var editedOptionContent1 = "";
		var editedOptionContent2 = "";
		var editedOptionContent3 = "";
		
		console.log(buttonLabel);
		
		// Make the options editable
		if (buttonLabel == "Edit Options") {
			currentOptionContent1 = $("#editableOption-1").html();
			currentOptionContent2 = $("#editableOption-2").html();
			currentOptionContent3 = $("#editableOption-3").html();
			
			$("#editableOption-1").replaceWith("<input type='text' id='optionEditBox1' value=''>");
			$("#optionEditBox1").val(currentOptionContent1)
			console.log("Option [" + optionId1 + "] ready for edit");
			
			$("#editableOption-2").replaceWith("<input type='text' id='optionEditBox2' value=''>");
			$("#optionEditBox2").val(currentOptionContent2)
			console.log("Option [" + optionId2 + "] ready for edit");
			
			$("#editableOption-3").replaceWith("<input type='text' id='optionEditBox3' value=''>");
			$("#optionEditBox3").val(currentOptionContent3)
			console.log("Option [" + optionId3 + "] ready for edit");
			
			// Change button label to [Confirm Edit]
			$("#editOptionButton").val("Confirm Edit");
			
		// Confirm edited options	
		} else if (buttonLabel == "Confirm Edit") {
			editedOptionContent1 = $("#optionEditBox1").val();
			editedOptionContent2 = $("#optionEditBox2").val();
			editedOptionContent3 = $("#optionEditBox3").val();
			
			console.log(" Option ["+optionId1+"] current content [" + currentOptionContent1 + "]");
			console.log(" Option ["+optionId1+"] edited content  [" + editedOptionContent1 + "]");
			
			console.log(" Option ["+optionId2+"] current content [" + currentOptionContent2 + "]");
			console.log(" Option ["+optionId2+"] edited content  [" + editedOptionContent2 + "]");
			
			console.log(" Option ["+optionId3+"] current content [" + currentOptionContent3 + "]");
			console.log(" Option ["+optionId3+"] edited content  [" + editedOptionContent3 + "]");

			// For Option 1:
			// If option content changed (need update in database) and not contains spaces only
			if ((editedOptionContent1 != currentOptionContent1) && (/\S/.test(editedOptionContent1))) {
				
				// Send [Option ID] and the edited [Option Content] to the controller [AdminController]
				$.ajax({
					async: true,
					type: "POST",
					url: "/dashboard/editOption",
					data: "id=" + optionId1 + "&option=" + editedOptionContent1,
					success: function (response) {

						// Edited option content updated in database
						if (response.includes("Option ["+optionId1+"] content changed to ["+editedOptionContent1+"]")) {
							console.log("Option [" + optionId1 + "] with content [" + editedOptionContent1 + "] successfully updated in database");
							$("#optionEditBox1").replaceWith("<span id='editableOption-1'>" + editedOptionContent1 + "</span>");
							console.log("Edit for Option [" + optionId1 + "] confirmed");

						// Unable to update the edited option content in database
						} else {
							console.log("Unable to update Option [" + optionId1 + "] with content [" + editedOptionContent1 + "] in database");
							$("#optionEditBox1").replaceWith("<span id='editableOption-1'>" + currentOptionContent1 + "</span>");
							console.log("Edit for Option [" + optionId1 + "] failed");
							alert("Error occured: \nUnable to update Option [" + optionId1 + "] with content [" + editedOptionContent1 + "] in database.\nPlease check your database connection and try again.");
						}
						
					}
				
				});
				
			// If option content unchanged (no need to contact with database)
			} else { 
				$("#optionEditBox1").replaceWith("<span id='editableOption-1'>" + currentOptionContent1 + "</span>");
				console.log("Edit for Option [" + optionId1 + "] confirmed");
			}
			
			// For Option 2:
			// If option content changed (need update in database) and not contains spaces only
			if ((editedOptionContent2 != currentOptionContent2) && (/\S/.test(editedOptionContent2))) {
				
				// Send [Option ID] and the edited [Option Content] to the controller [AdminController]
				$.ajax({
					async: true,
					type: "POST",
					url: "/dashboard/editOption",
					data: "id=" + optionId2 + "&option=" + editedOptionContent2,
					success: function (response) {

						// Edited option content updated in database
						if (response.includes("Option ["+optionId2+"] content changed to ["+editedOptionContent2+"]")) {
							console.log("Option [" + optionId2 + "] with content [" + editedOptionContent2 + "] successfully updated in database");
							$("#optionEditBox2").replaceWith("<span id='editableOption-2'>" + editedOptionContent2 + "</span>");
							console.log("Edit for Option [" + optionId2 + "] confirmed");

						// Unable to update the edited option content in database
						} else {
							console.log("Unable to update Option [" + optionId2 + "] with content [" + editedOptionContent2 + "] in database");
							$("#optionEditBox2").replaceWith("<span id='editableOption-2'>" + currentOptionContent2 + "</span>");
							console.log("Edit for Option [" + optionId2 + "] failed");
							alert("Error occured: \nUnable to update Option [" + optionId2 + "] with content [" + editedOptionContent2 + "] in database.\nPlease check your database connection and try again.");
						}
						
					}
				
				});
				
			// If option content unchanged (no need to contact with database)
			} else { 
				$("#optionEditBox2").replaceWith("<span id='editableOption-2'>" + currentOptionContent2 + "</span>");
				console.log("Edit for Option [" + optionId2 + "] confirmed");
			}
			
			
			// For Option 3:
			// If option content changed (need update in database) and not contains spaces only
			if ((editedOptionContent3 != currentOptionContent3) && (/\S/.test(editedOptionContent3))) {
				
				// Send [Option ID] and the edited [Option Content] to the controller [AdminController]
				$.ajax({
					async: true,
					type: "POST",
					url: "/dashboard/editOption",
					data: "id=" + optionId3 + "&option=" + editedOptionContent3,
					success: function (response) {

						// Edited option content updated in database
						if (response.includes("Option ["+optionId3+"] content changed to ["+editedOptionContent3+"]")) {
							console.log("Option [" + optionId3 + "] with content [" + editedOptionContent3 + "] successfully updated in database");
							$("#optionEditBox3").replaceWith("<span id='editableOption-3'>" + editedOptionContent3 + "</span>");
							console.log("Edit for Option [" + optionId3 + "] confirmed");

						// Unable to update the edited option content in database
						} else {
							console.log("Unable to update Option [" + optionId3 + "] with content [" + editedOptionContent3 + "] in database");
							$("#optionEditBox3").replaceWith("<span id='editableOption-3'>" + currentOptionContent3 + "</span>");
							console.log("Edit for Option [" + optionId3 + "] failed");
							alert("Error occured: \nUnable to update Option [" + optionId3 + "] with content [" + editedOptionContent3 + "] in database.\nPlease check your database connection and try again.");
						}
						
					}
				
				});
				
			// If option content unchanged (no need to contact with database)
			} else { 
				$("#optionEditBox3").replaceWith("<span id='editableOption-3'>" + currentOptionContent3 + "</span>");
				console.log("Edit for Option [" + optionId3 + "] confirmed");
			}		
			
			$("#editOptionButton").val("Edit Options"); // Change button label to [Edit Options]
			
		}
			
	});
	
	
	// CREATE STATS SHEET & BAR CHART & PIE CHART
	$("#ChartsButton").click(function(){
		var buttonLabel = $("#ChartsButton").val();
		var QuestionTitle = $("#editableQuestionTitle").html();
		var OptionContent1 = $("#editableOption-1").html();
		var OptionContent2 = $("#editableOption-2").html();
		var OptionContent3 = $("#editableOption-3").html();
		
		var optionId1 = $("#optionOptId-1").html();
		var optionId2 = $("#optionOptId-2").html();
		var optionId3 = $("#optionOptId-3").html();

		var optionCount1 = 0;
		var optionCount2 = 0;
		var optionCount3 = 0;
		
		// Table header
		$("#Stats-QuestionTitle").text(QuestionTitle);
		$("#Stats-Option-1").text(OptionContent1);
		$("#Stats-Option-2").text(OptionContent2);
		$("#Stats-Option-3").text(OptionContent3);
		
		if (buttonLabel == "View Charts") {
			
			// Create data sheets
			// Option 1 vote count
			$.ajax({
				async: false,
				type: "GET",
				url: "/dashboard/getVoteCount",
				data: "id="+optionId1,
				success: function(response) {
					if (response.status >= 0) {
						optionCount1 = response.status;
						console.log("Option 1 count ["+optionCount1+"]");
						
					} else {
						console.log("Option 1 count [ERROR]");
					}
					
				}
			
			});
			
			// Option 2 vote count
			$.ajax({
				async: false,
				type: "GET",
				url: "/dashboard/getVoteCount",
				data: "id="+optionId2,
				success: function(response) {
					if (response.status >= 0) {
						optionCount2 = response.status;
						console.log("Option 2 count ["+optionCount2+"]");
						
					} else {
						console.log("Option 2 count [ERROR]");
					}
					
				}
			
			});
			
			// Option 3 vote count
			$.ajax({
				async: false,
				type: "GET",
				url: "/dashboard/getVoteCount",
				data: "id="+optionId3,
				success: function(response) {
					if (response.status >= 0) {
						optionCount3 = response.status;
						console.log("Option 3 count ["+optionCount3+"]");
						
					} else {
						console.log("Option 3 count [ERROR]");
					}
					
				}
			
			});
			
			// Option count in table
			$("#Stats-Option-1-Count").text(optionCount1);
			$("#Stats-Option-2-Count").text(optionCount2);
			$("#Stats-Option-3-Count").text(optionCount3);
			
			$("#Sheets").show();
			
			// Parse string data to int
			var count1 = parseInt(optionCount1);
			var count2 = parseInt(optionCount2);
			var count3 = parseInt(optionCount3);
			
			// Create Google Charts
			google.charts.load('current', {'packages':['corechart']});
			google.charts.setOnLoadCallback(drawChart);

			function drawChart() {
				
				
				
				var data = google.visualization.arrayToDataTable([
				  ['Options','Vote Count'],
				  [OptionContent1, count1],
				  [OptionContent2, count2],
				  [OptionContent3, count3],
				]);
				
				var pieOptions = {
					'title': currentQuestionTitle, 
					'width':600, 
					'height':500,
				};
			  
				var pieChart = new google.visualization.PieChart(document.getElementById('GooglePieChart'));
				pieChart.draw(data, pieOptions);
				  
				var barOptions = {
					'title': currentQuestionTitle, 
					'width':600, 
					'height':500,
				};
				  
				var barChart = new google.visualization.BarChart(document.getElementById('GoogleBarChart'));
				barChart.draw(data, barOptions);
			}
			
			$("#Charts").show();
			$("#ChartsButton").val("Hide Charts");
			
		} else {
			
			$("#Charts").hide();
			$("#Sheets").hide();
			$("#ChartsButton").val("View Charts");
			
		}
		
	});
	
});