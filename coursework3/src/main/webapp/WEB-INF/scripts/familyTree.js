/**
 * This script is for showing a interactive family tree only
 */

function initFamilyTree(familyDataArray) {
    
    var $ = go.GraphObject.make;
    
    // define tooltips for nodes
    var tooltiptemplate =
        $(go.Adornment, "Auto",
            $(go.Shape, "Rectangle", {
                fill: "whitesmoke",
                stroke: "black"
            }),
            $(go.TextBlock, {
                    font: "bold 8pt Helvetica, bold Arial, sans-serif",
                    wrap: go.TextBlock.WrapFit,
                    margin: 5
                },
                new go.Binding("text", "", tooltipTextConverter))
        );

    familyTreeDiagram.div=null;
    familyTreeDiagram = 
    	$(go.Diagram, "familyTreeDiagramDiv", {
        initialAutoScale: go.Diagram.Uniform,
        initialContentAlignment: go.Spot.Center,
        "undoManager.isEnabled": true,
        
        // when a node is selected, draw a blue rectangle behind it
        nodeSelectionAdornmentTemplate: 
        	$(go.Adornment, "Auto", {
                layerName: "Grid"
            }, // the predefined layer that is behind everything else
            $(go.Shape, "Rectangle", {
                fill: null,
                strokeWidth: 5,
                stroke: "blue"
            }),
            $(go.Placeholder)
        ),
        layout: // use a custom layout, defined below
            $(GenogramLayout, {
                direction: 90,
                layerSpacing: 30,
                columnSpacing: 10
            })
    });


    // two different node templates, one for each sex,
    // named by the category value in the node data object
	familyTreeDiagram.nodeTemplate = 
		$(go.Node, "Auto", {
				deletable: false,
				toolTip: tooltiptemplate
			},
			new go.Binding("text", "name"),
			$(go.Shape, "Rectangle", {
					fill: "lightgray",
					stroke: null,
					strokeWidth: 0,
					stretch: go.GraphObject.Fill,
					alignment: go.Spot.Center
				},
				new go.Binding("fill", "g", genderBrushConverter)),
			$(go.TextBlock, {
					//font: "700 12px Droid Serif, sans-serif",
					textAlign: "center",
					margin: 10,
					maxSize: new go.Size(80, NaN)
				},
				new go.Binding("text", "name"))
		);
    familyTreeDiagram.nodeTemplateMap.add("male", // male
        $(go.Node, "Vertical", {
                locationSpot: go.Spot.Center,
                locationObjectName: "ICON"
            },
            $(go.Panel, {
                    name: "ICON"
                },
                $(go.Shape, "Square", {
                    width: 40,
                    height: 40,
                    strokeWidth: 2,
                    fill: "blue",
                    portId: ""
                }),
                $(go.Panel, { // for each attribute show a Shape at a particular place in the overall square
                        itemTemplate: $(go.Panel,
                            $(go.Shape, {
                                    stroke: null,
                                    strokeWidth: 0
                                } 
                            )
                        ),
                        margin: 1
                    },
                    
                )
            ),
            $(go.TextBlock, {
                    textAlign: "center",
                    maxSize: new go.Size(80, NaN)
                },
                new go.Binding("text", "name"))
        ));
    familyTreeDiagram.nodeTemplateMap.add("female", // female
        $(go.Node, "Vertical", {
                locationSpot: go.Spot.Center,
                locationObjectName: "ICON"
            },
            $(go.Panel, {
                    name: "ICON"
                },
                $(go.Shape, "Circle", {
                    width: 40,
                    height: 40,
                    strokeWidth: 2,
                    fill: "red",
                    portId: ""
                }),
                $(go.Panel, { // for each attribute show a Shape at a particular place in the overall circle
                        itemTemplate: $(go.Panel,
                            $(go.Shape, {
                                    stroke: null,
                                    strokeWidth: 0
                                } 
                            )
                        ),
                        margin: 1
                    },
					
                )
            ),
            $(go.TextBlock, {
                    textAlign: "center",
                    maxSize: new go.Size(80, NaN)
                },
                new go.Binding("text", "name"))
        ));
    
    familyTreeDiagram.nodeTemplateMap.add("LinkLabel",
        $(go.Node, {
            selectable: false,
            width: 1,
            height: 1,
            fromEndSegmentLength: 20
        }));
    familyTreeDiagram.linkTemplate = // for parent-child relationships
        $(go.Link, {
                routing: go.Link.Orthogonal,
                curviness: 15,
                layerName: "Background",
                selectable: false,
                fromSpot: go.Spot.Bottom,
                toSpot: go.Spot.Top
            },
            $(go.Shape, {
                strokeWidth: 2
            })
        );
    familyTreeDiagram.linkTemplateMap.add("Marriage", // for marriage relationships
        $(go.Link, {
                selectable: false
            },
            $(go.Shape, {
                strokeWidth: 2,
                stroke: "blue"
            })
        ));
	familyTreeDiagram.linkTemplateMap.add("MumOnly", // for MumOnly family parental relationships
        $(go.Link, {
                selectable: false
            },
            $(go.Shape, {
                strokeWidth: 1,
                stroke: "green"
            })
        ));
	familyTreeDiagram.linkTemplateMap.add("DadOnly", // for DadOnly family parental relationships
        $(go.Link, {
                selectable: false
            },
            $(go.Shape, {
                strokeWidth: 1,
                stroke: "green"
            })
        ));

    setupDiagram(familyTreeDiagram, familyDataArray, 0);

}

function genderBrushConverter(g) {
	if (g === "male") return "lightblue";
	if (g === "female") return "lightgreen";
	return "lightgray";
}

//get tooltip text from the object's data
function tooltipTextConverter(person) {
    var string = "";
    string += "Person Key: " + person.key;
    if (person.dob !== undefined && person.dob != null) {
    	string += "\nBirthday: " + person.dob;
    } else {
    	string += "\nBirthday: Not Available";
    }
    if (person.g !== undefined && person.g != null) {
    	string += "\nGender: " + person.g;
    } else {
    	string += "\nGender: Not Available";
    }
    if (person.m !== undefined && person.m != null) {
    	string += "\nMother Key: " + person.m;
    } else {
    	string += "\nMother Key: Not Available";
    }
    if (person.f !== undefined && person.f != null) {
    	string += "\nFather Key: " + person.f;
    } else {
    	string += "\nFather Key: Not Available";
    }
    //if (person.deathYear !== undefined) str += "\nDied: " + person.deathYear;
    //if (person.reign !== undefined) str += "\nReign: " + person.reign;
    return string;
}


// create and initialize the Diagram.model given an array of node data representing people
function setupDiagram(diagram, array, focusId) {

	diagram.addDiagramListener("ObjectSingleClicked", // Person selected 
            function (e1) {
                var person = e1.subject.part;
                if (!(person instanceof go.Link)) {
                	confirmSelect(person.data);
                }
            });
	
	diagram.addDiagramListener("ObjectDoubleClicked", // Modal pop-out for Editing person
            function (e2) {
                var person = e2.subject.part;
                if (!(person instanceof go.Link)) {
                	editPersonByClickObject(person.data);
                }
            });
	diagram.addDiagramListener("BackgroundSingleClicked", // Cancel select
            function (e3) {
                cancelSelect();
            });
	
    diagram.model = go.GraphObject.make(go.GraphLinksModel, { // declare support for link label nodes
            linkLabelKeysProperty: "labelKeys",
            // this property determines which template is used
            nodeCategoryProperty: "s",
            // create all of the nodes for people
            nodeDataArray: array
		});
		
    setupMarriages(diagram);
    setupParents(diagram);
	
    var node = diagram.findNodeForKey(focusId);
	
    if (node !== null) {
        diagram.select(node);
        // remove any spouse for the person under focus:
        //node.linksConnected.each(function(l) {
        //  if (!l.isLabeledLink) return;
        //  l.opacity = 0;
        //  var spouse = l.getOtherNode(node);
        //  spouse.opacity = 0;
        //  spouse.pickable = false;
        //});
    }
}

function findMarriage(diagram, a, b) { // A and B are node keys
    var nodeA = diagram.findNodeForKey(a);
    var nodeB = diagram.findNodeForKey(b);
    if (nodeA !== null && nodeB !== null) {
        var it = nodeA.findLinksBetween(nodeB); // in either direction
        while (it.next()) {
            var link = it.value;
            // Link.data.category === "Marriage" means it's a marriage relationship
            if (link.data !== null && link.data.category === "Marriage") {
                return link;
            }
        }
    }
    return null;
}

// now process the node data to determine marriages
function setupMarriages(diagram) {
    var model = diagram.model;
    var nodeDataArray = model.nodeDataArray;
    for (var i = 0; i < nodeDataArray.length; i++) {
        var data = nodeDataArray[i];
        var key = data.key;
        var spouseId = data.spouseId;
		
        if (spouseId !== undefined && spouseId != null) {

			var link = findMarriage(diagram, key, spouseId);
			if (link === null) {
				// add a label node for the marriage link
				var marriageLabel = {
					s: "LinkLabel"
				};
				
				model.addNodeData(marriageLabel);
				
				// add the marriage link itself, also referring to the label node
				var marriageData = {
					from: key,
					to: spouseId,
					labelKeys: [marriageLabel.key],
					category: "Marriage"
				};
				
				model.addLinkData(marriageData);
				console.log("Person ["+key+"] married with spouseId: "+spouseId+" ("+typeof spouseId+")");
			}
            
        } 
		
    }
}


// process parent-child relationships once all marriages are known
function setupParents(diagram) {
    var model = diagram.model;
    var nodeDataArray = model.nodeDataArray;
    for (var i = 0; i < nodeDataArray.length; i++) {
        var data = nodeDataArray[i];
        var key = data.key;
        var mother = data.m;
        var father = data.f;
        if (mother !== undefined && mother != null && father !== undefined && father != null) {
            var link = findMarriage(diagram, mother, father);
            if (link === null) {
                // or warn no known mother or no known father or no known marriage between them
                if (window.console) window.console.log("unknown marriage: " + mother + " & " + father);
                continue;
            }
            var mdata = link.data;
            var mlabkey = mdata.labelKeys[0];
            var cdata = {
                from: mlabkey,
                to: key
            };
            familyTreeDiagram.model.addLinkData(cdata);
			
        } else if (mother !== undefined && mother != null ) { // Mother-Only family
			var parentData = {
				from: mother,
				to: key
				
			};
			
			familyTreeDiagram.model.addLinkData(parentData);
			
		} else if (father !== undefined && father != null) { // Father-Only family		
			var parentData = {
				from: father,
				to: key
				
			};
			
			familyTreeDiagram.model.addLinkData(parentData);
		}
			 
    }
}


function personDetail(detail) {
	document.getElementById("personInfo").textContent = detail;
}

function GenogramLayout() {
    go.LayeredDigraphLayout.call(this);
    this.initializeOption = go.LayeredDigraphLayout.InitDepthFirstIn;
    this.spouseSpacing = 30; 
}
go.Diagram.inherit(GenogramLayout, go.LayeredDigraphLayout);
/** @override */
GenogramLayout.prototype.makeNetwork = function (coll) {
    var net = this.createNetwork();
    if (coll instanceof go.Diagram) {
        this.add(net, coll.nodes, true);
        this.add(net, coll.links, true);
    } else if (coll instanceof go.Group) {
        this.add(net, coll.memberParts, false);
    } else if (coll.iterator) {
        this.add(net, coll.iterator, false);
    }
    return net;
};

GenogramLayout.prototype.add = function (net, coll, nonmemberonly) {
    var multiSpousePeople = new go.Set();
    var it = coll.iterator;
    while (it.next()) {
        var node = it.value;
        if (!(node instanceof go.Node)) continue;
        if (!node.isLayoutPositioned || !node.isVisible()) continue;
        if (nonmemberonly && node.containingGroup !== null) continue;
        if (node.isLinkLabel) {
            var link = node.labeledLink;
            var spouseA = link.fromNode;
            var spouseB = link.toNode;
            var vertex = net.addNode(node);
            vertex.width = spouseA.actualBounds.width + this.spouseSpacing + spouseB.actualBounds.width;
            vertex.height = Math.max(spouseA.actualBounds.height, spouseB.actualBounds.height);
            vertex.focus = new go.Point(spouseA.actualBounds.width + this.spouseSpacing / 2, vertex.height / 2);
        } else {

            var marriages = 0;
            node.linksConnected.each(function (l) {
                if (l.isLabeledLink) marriages++;
            });
            if (marriages === 0) {
                var vertex = net.addNode(node);
            } else if (marriages > 1) {
                multiSpousePeople.add(node);
            }
        }
    }
	

    it.reset();
    while (it.next()) {
        var link = it.value;
        if (!(link instanceof go.Link)) continue;
        if (!link.isLayoutPositioned || !link.isVisible()) continue;
        if (nonmemberonly && link.containingGroup !== null) continue;
        if (!link.isLabeledLink) {
            var parent = net.findVertex(link.fromNode);
            var child = net.findVertex(link.toNode);
            if (child !== null) { 
                net.linkVertexes(parent, child, link);
            } else { 
                link.toNode.linksConnected.each(function (l) {
                    if (!l.isLabeledLink) return; 
                    var mlab = l.labelNodes.first();

                    var mlabvert = net.findVertex(mlab);
                    if (mlabvert !== null) {
                        net.linkVertexes(parent, mlabvert, link);
                    }
                });
            }
        }
    }
	
    while (multiSpousePeople.count > 0) {

        var node = multiSpousePeople.first();
        var cohort = new go.Set();
        this.extendCohort(cohort, node);
        var dummyvert = net.createVertex();
        net.addVertex(dummyvert);
        var marriages = new go.Set();
        cohort.each(function (n) {
            n.linksConnected.each(function (l) {
                marriages.add(l);
            })
        });
        marriages.each(function (link) {
 
            var mlab = link.labelNodes.first()
            var v = net.findVertex(mlab);
            if (v !== null) {
                net.linkVertexes(dummyvert, v, null);
            }
        });

        multiSpousePeople.removeAll(cohort);
    }
};


GenogramLayout.prototype.extendCohort = function (coll, node) {
    if (coll.contains(node)) return;
    coll.add(node);
    var lay = this;
    node.linksConnected.each(function (l) {
        if (l.isLabeledLink) { 
            lay.extendCohort(coll, l.fromNode);
            lay.extendCohort(coll, l.toNode);
        }
    });
};
/** @override */
GenogramLayout.prototype.assignLayers = function () {
    go.LayeredDigraphLayout.prototype.assignLayers.call(this);
    var horiz = this.direction == 0.0 || this.direction == 180.0;
    var maxsizes = [];
    this.network.vertexes.each(function (v) {
        var lay = v.layer;
        var max = maxsizes[lay];
        if (max === undefined) max = 0;
        var sz = (horiz ? v.width : v.height);
        if (sz > max) maxsizes[lay] = sz;
    });
    this.network.vertexes.each(function (v) {
        var lay = v.layer;
        var max = maxsizes[lay];
        if (horiz) {
            v.focus = new go.Point(0, v.height / 2);
            v.width = max;
        } else {
            v.focus = new go.Point(v.width / 2, 0);
            v.height = max;
        }
    });
};
/** @override */
GenogramLayout.prototype.commitNodes = function () {
    go.LayeredDigraphLayout.prototype.commitNodes.call(this);

    this.network.vertexes.each(function (v) {
        if (v.node !== null && !v.node.isLinkLabel) {
            v.node.position = new go.Point(v.x, v.y);
        }
    });

    var layout = this;
    this.network.vertexes.each(function (v) {
        if (v.node === null) return;
        if (!v.node.isLinkLabel) return;
        var labnode = v.node;
        var lablink = labnode.labeledLink;
        lablink.invalidateRoute();
        var spouseA = lablink.fromNode;
        var spouseB = lablink.toNode;

        if (spouseA.data.s === "F") { // sex is female
            var temp = spouseA;
            spouseA = spouseB;
            spouseB = temp;
        }
 
        var aParentsNode = layout.findParentsMarriageLabelNode(spouseA);
        var bParentsNode = layout.findParentsMarriageLabelNode(spouseB);
        if (aParentsNode !== null && bParentsNode !== null && aParentsNode.position.x > bParentsNode.position.x) {

            var temp = spouseA;
            spouseA = spouseB;
            spouseB = temp;
        }
        spouseA.position = new go.Point(v.x, v.y);
        spouseB.position = new go.Point(v.x + spouseA.actualBounds.width + layout.spouseSpacing, v.y);
        if (spouseA.opacity === 0) {
            var pos = new go.Point(v.centerX - spouseA.actualBounds.width / 2, v.y);
            spouseA.position = pos;
            spouseB.position = pos;
        } else if (spouseB.opacity === 0) {
            var pos = new go.Point(v.centerX - spouseB.actualBounds.width / 2, v.y);
            spouseA.position = pos;
            spouseB.position = pos;
        }
    });

    this.network.vertexes.each(function (v) {
        if (v.node === null || v.node.linksConnected.count > 1) return;
        var mnode = layout.findParentsMarriageLabelNode(v.node);
        if (mnode !== null && mnode.linksConnected.count === 1) {
            var mvert = layout.network.findVertex(mnode);
            var newbnds = v.node.actualBounds.copy();
            newbnds.x = mvert.centerX - v.node.actualBounds.width / 2;
   
            var overlaps = layout.diagram.findObjectsIn(newbnds, function (x) {
                return x.part;
            }, function (p) {
                return p !== v.node;
            }, true);
            if (overlaps.count === 0) {
                v.node.move(newbnds.position);
            }
        }
    });
};
GenogramLayout.prototype.findParentsMarriageLabelNode = function (node) {
    var it = node.findNodesInto();
    while (it.next()) {
        var n = it.value;
        if (n.isLinkLabel) return n;
    }
    return null;
};