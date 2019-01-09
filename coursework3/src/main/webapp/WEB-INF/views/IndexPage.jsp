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
	<script src="../scripts/jquery.min.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	
	<!-- Bootstrap scripts -->
	<script src="resources/bootstrap/js/bootstrap.min.js"></script>
	
	<!-- Bootstrap css -->
	<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css">
	
	<!-- GoJS scripts -->
	<script src="../scripts/go.js" type="text/javascript"></script>

	<!-- Custom scripts -->
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
				
					<li class="active"><a href="#">Home</a></li>
					<li><a href="#">Persons</a></li>
					<li><a href="#">Tree</a></li>
					<li class="dropdown">
					
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">More Options <span class="caret"></span></a>

						<ul class="dropdown-menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li role="separator" class="divider"></li>
							<li class="dropdown-header">Nav header</li>
							<li><a href="#">Separated link</a></li>
							<li><a href="#">One more separated link</a></li>
						</ul>
					  
					</li>
					
				</ul>
				
			</div>
		  
		</div>
	</nav>

	<!-- [Section 2]: Main Part below the Nav-bar -->
	<div class="container">
	
		<div class="jumbotron" id="familyTreeSection">
			<div id="operation-buttons">
				<p>
					<button class="btn btn-sm btn-primary" data-toggle="modal" data-target="#addPersonModal">Add Person</button>
					<button class="btn btn-sm btn-primary" data-toggle="modal" data-target="#editPersonModal">Edit Person</button>
					<button class="btn btn-sm btn-danger" data-toggle="modal" data-target="#deletePersonModal">Delete Person</button>
					<button class="btn btn-sm btn-primary" id="showAncestorBtn" data-toggle="modal" data-target="#showAncestor">View Ancestor</button>
					<button class="btn btn-sm btn-primary" id="showDescendantBtn" data-toggle="modal" data-target="#showDescendant">View Descendant</button>
				</p>
	
			</div>
			
			<div id="familyTreeChart">
				
				<div id="myDiagramDiv" style="border: solid 1px black; width:100%; height:600px"></div>
			
			</div>
		</div>
	
		<div class="jumbotron" id="intro-content" aria-hidden="false">
	
			<h1>Find your family</h1>
			<p>Genealogy Explorer is an online tool for building a family tree and tracking ancestry.</p>
			<p>This project consists of two parts - RESTful Service (Back end) and Web Interface (Front end).</p>
			<p>
			  <a class="btn btn-lg btn-primary" id="showFamilyTree" role="button">Check it out &raquo;</a>
			</p>
	
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
	            </div>
	            
	            <div class="modal-body">
	            
						在这里添加一些文本
	            
	            </div>
	            
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	                <button type="button" class="btn btn-primary" id="submit-newPerson">Submit</button>
	            </div>
	            
	        </div>
	    </div>
	</div>
	
	


</body>
</html>