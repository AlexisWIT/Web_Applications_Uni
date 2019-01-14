<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <meta name="description" content="A index page for Genealogy Explorer">
    <meta name="author" content="Yifan Tang (yt120)">
    
	<title>Genealogy Explorer</title>
	
	<!-- JQuery scripts -->
	<script src="../scripts/jquery.331.min.js"></script>
	<script src="../scripts/jquery.validate.min.js"></script>
	
	<!-- Bootstrap scripts -->
	<script src="resources/bootstrap/js/bootstrap.min.js"></script>
	
	<!-- Bootstrap css -->
	<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css">
	
	<!-- GoJS scripts -->
	<script src="../scripts/go.js" type="text/javascript"></script>

	<!-- Custom scripts -->
	<script src="../scripts/interactiveTree.js" type="text/javascript"></script>
	<script src="../scripts/familyTree.js" type="text/javascript"></script>
	<script src="../scripts/IndexPage.js" type="text/javascript"></script>
	
	<!-- Custom css -->
	<link href="resources/css/index.css" rel="stylesheet" >
		
</head>

<body>

	<!-- [Section 1]: Nav-bar -->
	<nav class="navbar navbar-default navbar-static-top">
		<div class="container">
		
			<div class="navbar-header">
			
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
				  <span class="sr-only">Toggle navigation</span>
				  <span class="icon-bar"></span>
				  <span class="icon-bar"></span>
				  <span class="icon-bar"></span>
				</button>
				
				<a class="navbar-brand" href="#">Genealogy Explorer</a>
				
			</div>
		  
			<div id="navbar" class="navbar-collapse collapse">
			
				<ul class="nav navbar-nav">
				
					<li class="active" id="homeTab"><a href="#">Home</a></li>
					<!-- li id="personsTab" ><a href="#"></a></li -->
					<li id="treeTab"><a href="#">Tree</a></li>
					<li class="dropdown">
					
						<a href="#" id="moreOperationTab" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">More Options <span class="caret"></span></a>

						<ul class="dropdown-menu">
							<li><a href="#">Settings</a></li>
							<li><a href="#">About</a></li>
							<li role="separator" class="divider"></li>
							<li class="dropdown-header">Manage Data</li>
							<li><a href="#">Export to JSON</a></li>
							<li><a href="#">Clear all</a></li>
						</ul>
					  
					</li>
					
				</ul>
				
			</div>
		  
		</div>
	</nav>

	<!-- [Section 2]: Main Part below the Nav-bar -->
	<div class="container">
	
		<div class="jumbotron" id="intro-content">
	
			<h1>Find your family</h1>
			<p>Genealogy Explorer is an online tool for building a family tree and tracking ancestry.</p>
			<p>This project consists of two parts - RESTful Service (Back end) and Web Interface (Front end).</p>
			<p>
			  <a class="btn btn-lg btn-primary" id="showFamilyTree" role="button">Check it out &raquo;</a>
			</p>
	
		</div>
	
		<div class="jumbotron" id="familyTreeSection">
			<div id="operation-buttons">
				<p>
					<button class="btn btn-sm btn-info" id="refreshTreeBtn" data-toggle="modal" data-target="#refreshTree"><span class="glyphicon glyphicon-refresh"></span> Refresh Tree</button>
					<button class="btn btn-sm btn-primary" id="addPersonBtn"data-toggle="modal" data-target="#addPersonModal"><span class="glyphicon glyphicon-plus"></span> Add Person</button>
					<button class="btn btn-sm btn-primary" id="editPersonBtn"data-toggle="modal" data-target="#editPersonModal"><span class="glyphicon glyphicon-edit"></span> Edit Person</button>
					<button class="btn btn-sm btn-danger" id="deletePersonBtn"data-toggle="modal" data-target="#deletePersonModal"><span class="glyphicon glyphicon-trash"></span> Delete Person</button>
					<button class="btn btn-sm btn-primary" id="showAncestorBtn" data-toggle="modal" data-target="#showAncestor"><span class="glyphicon glyphicon-circle-arrow-up"></span> View Ancestors</button>
					<button class="btn btn-sm btn-primary" id="showDescendantBtn" data-toggle="modal" data-target="#showDescendant"><span class="glyphicon glyphicon-circle-arrow-down"></span> View Descendants</button>
				</p>
	
			</div>
			
			<div id="familyTreeDiagram">	
				<div id="familyTreeDiagramDiv" style="border: solid 1px black; width:100%; height:600px"></div>
				<div hidden>
					<input id="personInfo" ></input><!-- This is person key -->
					<input id="personName" ></input>
					<input id="personDob" ></input>
					<input id="personGender" ></input>
					<input id="personMkey" ></input>
					<input id="personFkey" ></input>
				</div>
			</div>
		</div>
	
		

	</div>
	
	<!-- [Section 3]: Modal windows -->
	<!-- Modal 01 - Add New Person -->
	<div class="modal fade" id="addPersonModal" tabindex="-1" role="dialog" aria-labelledby="addPersonLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	        
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title" id="addPersonLabel">Add New Person</h4>
	                <div hidden><input id="modeToken"></input></div>
	            </div>
	            <form class="form-group" id="form-addSingleNew">
		            <div class="modal-body" id="singleAddBody">
		            	<div id="addSinglePerson">
			           		<div class="input-group">
		      					<span class="input-group-addon"><i class="glyphicon glyphicon-asterisk"></i> Person Key</span>
		        				<input id="personKey"	name="personKey" class="form-control" placeholder="Person Key">
	        				</div>
	        				<div class="input-group">
		      					<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i> Name</span>
		        				<input id="name"		name="name" class="form-control" placeholder="Name">
	        				</div>
	        				<div class="input-group">
		      					<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i> Birthday</span>
		        				<input id="birthday"	name="birthday" class="form-control" placeholder="Birthday (eg. 19970101)" >
	        				</div>
	        				<div class="input-group">
		      					<span class="input-group-addon"><i class="glyphicon glyphicon-heart-empty"></i> Gender</span>
		        				<input id="gender"		name="gender" class="form-control" placeholder="Gender" >
	        				</div>
	        				<div class="input-group">
		      					<span class="input-group-addon"><i class="glyphicon glyphicon-home"></i> Mother Key</span>
		        				<input id="motherKey"	name="motherKey" class="form-control" placeholder="Mother Key" >
	        				</div>
	        				<div class="input-group">
		      					<span class="input-group-addon"><i class="glyphicon glyphicon-home"></i> Father Key</span>
		        				<input id="fatherKey"	name="fatherKey" class="form-control" placeholder="Father Key" >
	        				</div>
						
							<div id="addInfoSingle"> </div>
							<br>
							<p><a href="#" id="addMultiplePersonLink">Add multiple persons (JSON)</a></p>
							<div class="alert alert-info fade in out" id="singleAddInProgressAlert" role="alert"><span id="singleAddInProgressInfo"></span></div>
							<div class="alert alert-danger fade in out" id="singleAddErrorAlert" role="alert"><span id="singleAddFailedInfo"></span></div>
							<div class="alert alert-success fade in out" id="singleAddCompleteAlert" role="alert"><span id="singleAddCompleteInfo"></span></div>
						</div>
					</div>	
					
					<div class="modal-footer" id="singleAddFooter">
		                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
		                <button type="submit" class="btn btn-primary" id="submit-newSinglePerson">Submit</button>
		            </div>	
				</form>
				<form class="form-group" id="form-addMultiNew">		
					<div class="modal-body" id="multiAddBody">	
						<div id="addMultiplePerson">
    						<label for="mutipleAddArea">Add multiple persons (JSON)</label>
							<textarea class="form-control" id="mutipleAddArea" name="mutipleAddArea" rows="10"></textarea>
							<div id="addErrorInfoMulti" role="alert"> </div>
							<br>
							<p><a href="#" id="addSinglePersonLink">Add single person</a></p>
							<div class="alert alert-info fade in out" id="multiAddInProgressAlert" role="alert"><span id="multiAddInProgressInfo"></span></div>
							<div class="alert alert-danger fade in out" id="multiAddErrorAlert" role="alert"><span id="multiAddFailedInfo"></span></div>
							<div class="alert alert-success fade in out" id="multiAddCompleteAlert" role="alert"><span id="multiAddCompleteInfo"></span></div>
						</div>
		            </div>

		            <div class="modal-footer" id="multiAddFooter">
		                <button type="button" class="btn btn-default" id="cancelAddMulti" data-dismiss="modal">Cancel</button>
		                <button type="submit" class="btn btn-primary" id="submit-newMultiPerson">Submit</button>
		            </div>
	            </form>
	            
	        </div>
	    </div>
	</div>
	
	<!-- Modal 02 - Edit Current Person -->
	<div class="modal fade" id="editPersonModal" tabindex="-1" role="dialog" aria-labelledby="editPersonLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	        
	            <div class="modal-header">
	                <button type="button" class="close" id="cancel-editedPersonIcon" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title" id="editPersonLabel">Edit Person</h4>
	            </div>
	            
	            <form class="form-group" id="form-edit">
		            <div class="modal-body">
	            		<div id="editSinglePerson">
	            			<div hidden><input id="editToken"></input></div>
			           		<div class="input-group">
		      					<span class="input-group-addon"><i class="glyphicon glyphicon-asterisk"></i> Person Key</span>
		        				<input id="edit-personKey"	name="edit-personKey" class="form-control" placeholder="Person Key" required disabled>
	        				</div>
	        				<div class="input-group">
		      					<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i> Name</span>
		        				<input id="edit-name"		name="edit-name" class="form-control" placeholder="Name" required autofocus>
	        				</div>
	        				<div class="input-group">
		      					<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i> Birthday</span>
		        				<input id="edit-birthday"	name="edit-birthday" class="form-control" placeholder="Birthday (eg. 19970101)" >
	        				</div>
	        				<div class="input-group">
		      					<span class="input-group-addon"><i class="glyphicon glyphicon-heart-empty"></i> Gender</span>
		        				<input id="edit-gender"		name="edit-gender" class="form-control" placeholder="Gender" >
	        				</div>
	        				<div class="input-group">
		      					<span class="input-group-addon"><i class="glyphicon glyphicon-home"></i> Mother Key</span>
		        				<input id="edit-motherKey"	name="edit-motherKey" class="form-control" placeholder="Mother Key" >
	        				</div>
	        				<div class="input-group">
		      					<span class="input-group-addon"><i class="glyphicon glyphicon-home"></i> Father Key</span>
		        				<input id="edit-fatherKey"	name="edit-fatherKey" class="form-control" placeholder="Father Key" >
	        				</div>
	        				<br>
	        				<div class="alert alert-info fade in out" id="editInProgressAlert" role="alert"><span id="editInProgressInfo"></span></div>
							<div class="alert alert-danger fade in out" id="editErrorAlert" role="alert"><span id="editFailedInfo"></span></div>
							<div class="alert alert-success fade in out" id="editCompleteAlert" role="alert"><span id="editCompleteInfo"></span></div>
							
						</div>
		            </div>
		            
		            <div class="modal-footer">
		                <button type="button" class="btn btn-default" id="cancel-editedPerson" data-dismiss="modal">Cancel</button>
		                <button type="submit" class="btn btn-primary" id="submit-editedPerson">Submit</button>
		            </div>
	            </form>
	            
	        </div>
	    </div>
	</div>
	
	<!-- Modal 03 - Delete Current Person -->
	<div class="modal fade" id="deletePersonModal" tabindex="-1" role="dialog" aria-labelledby="deletePersonLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	        
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title" id="deletePersonLabel">Delete Person</h4>
	            </div>
	            
	            <div class="modal-body">
	            	<p>Are you sure you want to delete this person?</p>
	            </div>
	            
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	                <button type="button" class="btn btn-danger" id="confirm-deletePerson">Delete</button>
	            </div>
	            
	        </div>
	    </div>
	</div>
	
	<!-- Modal 04 - Tips for editing Person -->
	<div class="modal fade" id="editPersonTips" tabindex="-1" role="dialog" aria-labelledby="editPersonTipsLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	        
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title" id="editPersonTipsLabel">Edit Person Tips</h4>
	            </div>
	            
	            <div class="modal-body">
	            	<p>Please double-click a person to edit!</p>
	            </div>
	            
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">OK</button>
	            </div>
	            
	        </div>
	    </div>
	</div>
	
	<!-- Modal 05 - Loading window -->
	<div class="modal fade" id="loadingWindow" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="loadingWindowLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	        	<div class="modal-body">
		        	<div>
		        		<p><img id="loaderImg" src="resources/loader.gif" />   Processing your request, please wait...</p>
					</div>
				</div>
	        </div>
	    </div>
	</div>


</body>
</html>