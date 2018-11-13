package uk.ac.le.cs.wt;

import org.apache.xerces.parsers.DOMParser;
import org.apache.xerces.xs.StringList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;

//Note: you need to implement either XMLDOMParser.java OR XMLSAXParser.java

public class XMLParserDOM {
	
	public void parse(String xml) {
	  try{
	  		DOMParser parser = new DOMParser();
	  		parser.parse(xml);
	  		Document doc = parser.getDocument();
	  		traverse_tree(doc);
	       }
	       catch(Exception e){
	          e.printStackTrace(System.err);
	       }
    }
	  
	static String visibility ="";
	static String returnType ="", methodName ="";
	static String prefix ="", parameterList ="", throwsList ="";
	static String result ="";

	public static void traverse_tree(Node node){
		//Complete task 3 
		
		if (node==null) {
			return;
		}
		int nodeType = node.getNodeType();
		switch (nodeType) {
			case Node.DOCUMENT_NODE: {
				traverse_tree(((Document)node).getDocumentElement());
				break;
			}
			case Node.ELEMENT_NODE: {
				String elementName = node.getNodeName();
				NamedNodeMap attributes = node.getAttributes();
				NodeList childNodes = node.getChildNodes();
				if (elementName.equals("package") || elementName.equals("extends") 
												  || elementName.equals("import")) {		
	
				}
				
				if (elementName.equals("visibility_modifier")) {
					visibility = node.getTextContent();
				}
				
				if (elementName.equals("return")) {
					returnType = node.getTextContent();
				}
				
				if (elementName.equals("abstract_method")) {
					methodName = attributes.getNamedItem("name").getNodeValue();
				}
				
				if (elementName.equals("parameter")) {
					String type = attributes.getNamedItem("type").getNodeValue();
					String parameter = node.getTextContent();
					if (parameterList == "" ) {
						parameterList = type +" "+ parameter;
					} else {
						parameterList = parameterList +", "+ type +" "+ parameter;
					}
					
				}
				
				if (elementName.equals("exception")) {	
					String throwsName = node.getParentNode().getNodeName();
					String exceptionName = node.getTextContent();
					if (throwsList == "") {
						throwsList = "\n    "+ throwsName +" "+ exceptionName;
					} else {
						throwsList = throwsList +", "+exceptionName;
					}
					
				}
				
				if (visibility !="" && returnType !="" && methodName !="") {
					prefix = visibility +" "+ returnType +" "+methodName;
					visibility ="";
					returnType ="";
					methodName ="";
				}
				
				if (childNodes != null) {
					for (int index = 0; index < childNodes.getLength(); index++) {
						traverse_tree(childNodes.item(index));
					}
					
				}
				
				if (prefix != "") {
					System.out.println(prefix +" ("+ parameterList +")"+ throwsList +";");
					prefix ="";
					parameterList ="";
					throwsList ="";
				}
				
				break;
				
			}
		} 
		
	}

}
