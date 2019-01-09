package gEapp.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import gEapp.domain.JSONOrderedObject;
import gEapp.domain.JSONResponse;
import gEapp.domain.Member;
import gEapp.service.MemberService;

@Controller
public class InterfaceController {
	
	@Autowired MemberService memberService;
	IndexController indexController;
	
	@RequestMapping(value="/GE/FamilyTree", method=RequestMethod.GET)
	public @ResponseBody JSONResponse createFamilyTree() {
		
		JSONResponse familyTreeResponse = new JSONResponse();
		List<Member> listForTree = new ArrayList<>();
		setupMarriage(0,0);
		
		listForTree = (List<Member>) memberService.findAllMembers();
		
		familyTreeResponse.setMemberList(listForTree);
		return familyTreeResponse;
	}
	
	@RequestMapping(value="/GE/AncestorTree", method=RequestMethod.GET)
	@ResponseBody
	public JSONResponse createAncestorTree(Integer key) {
		JSONObject jsonAncestors = new JSONObject();
		JSONResponse ancestorTreeResponse = new JSONResponse();
		List<Member> ancestorListForTree = new ArrayList<>();
		List<Integer> resultParentList = new ArrayList<>();
		System.out.println("Received Request for finding ancestor of ["+key+"]");
		setupMarriage(1,key);

		resultParentList = (List<Integer>)getParentObject(key);

		System.out.println("\nResult="+resultParentList.toString());
		for(Integer memberkey: resultParentList) {
			Member member = memberService.findById(memberkey);
			ancestorListForTree.add(member);
				
		}
		ancestorTreeResponse.setMemberList(ancestorListForTree);
		return ancestorTreeResponse;
		
	}
	
	
	@RequestMapping(value="/GE/DescendantTree", method=RequestMethod.GET)
	@ResponseBody
	public JSONResponse createDescendantTree(Integer key) {
		JSONObject jsonDescendants = new JSONObject();
		JSONResponse descendantTreeResponse = new JSONResponse();
		List<Member> descendantListForTree = new ArrayList<>();
		List<Integer> resultChildrenList = new ArrayList<>();
		System.out.println("Received Request for finding ancestor of ["+key+"]");
		setupMarriage(2,key);
		
//		resultChildrenList = (List<Integer>)getChildrenObject(key);
//		
//		System.out.println("\nResult="+resultChildrenList.toString());
//		for(Integer memberkey: resultChildrenList) {
//			Member member = memberService.findById(memberkey);
//			descendantListForTree.add(member);
//				
//		}
		descendantListForTree = (List<Member>)getChildrenObject(key);
		System.out.println("\nResult="+descendantListForTree.toString());
		
		descendantTreeResponse.setMemberList(descendantListForTree);
		return descendantTreeResponse;
		
	}
	
	public Object getChildrenObject(Integer id) {
		
		List<Member> childrenObjList = new ArrayList<>();
		List<Member> childrenList = new ArrayList<>();
		List<Member> resultList = new ArrayList<>();
		Member parent = memberService.findById(id);
		
		if (parent != null) {
			
			System.out.println("Start searching children for: " + parent.getName() + " [" + id + "]");

			List<Member> memberInDatabase = (List<Member>) memberService.findAllMembers();

			for (Member memberForCheck : memberInDatabase) {
				if (memberForCheck.getMumKey() != null && memberForCheck.getMumKey() == id) {
					
					memberForCheck.setDadKey(0);
					Integer childMemberKey = memberForCheck.getKey();
					
					System.out.println("(1) Get Child For Mum: " + parent.getName() + " ["
							+ id + "] - " + memberForCheck.getName() + " [" + childMemberKey + "]");
					
					childrenObjList.add(memberForCheck);
					childrenList = (List<Member>) mergeMemberList(childrenList, (List<Member>)getChildrenList(childMemberKey));
					
				} else if (memberForCheck.getDadKey() != null && memberForCheck.getDadKey() == id) {

					memberForCheck.setMumKey(0);
					Integer childMemberKey = memberForCheck.getKey();
					
					System.out.println("(1) Get Child For Dad: " + parent.getName() + " ["
							+ id + "] - " + memberForCheck.getName() + " [" + childMemberKey + "]");
					
					childrenObjList.add(memberForCheck);
					childrenList = (List<Member>) mergeMemberList(childrenList, (List<Member>)getChildrenList(childMemberKey));

				}

			}
			
			System.out.println("(No more children found for: " + parent.getName() + " [" + id + "])");
			childrenObjList.add(parent);
			
			return resultList;

		} else {
			
			return resultList;

		}
	}
	
	public Object getChildrenList(Integer id) {

		List<Member> memberInDatabase = (List<Member>) memberService.findAllMembers();
		Member childrenMember = memberService.findById(id);

		System.out.println("Start searching children for: " + childrenMember.getName() + " [" + id + "]");
		List<Member> childrenList = new ArrayList(); 

		for (Member memberForCheck : memberInDatabase) {
			if (memberForCheck.getMumKey() != null && memberForCheck.getMumKey() == id) {
				
				memberForCheck.setDadKey(0);
				Integer childMemberKey = memberForCheck.getKey();
				
				System.out.println("(2) Get Child For Mum: " + childrenMember.getName() + " ["
						+ id + "] - " + memberForCheck.getName() + " [" + childMemberKey + "]");
				
				childrenList.add(memberForCheck);
				childrenList = (List<Member>) mergeMemberList(childrenList, (List<Member>)getChildrenObject(childMemberKey));
				
				
			} else if (memberForCheck.getDadKey() != null && memberForCheck.getDadKey() == id) {

				memberForCheck.setMumKey(0);
				Integer childMemberKey = memberForCheck.getKey();
				
				System.out.println("(2) Get Child For Dad: " + childrenMember.getName() + " [" 
						+ id + "] - " + memberForCheck.getName() + " [" + childMemberKey + "]");
				
				childrenList.add(memberForCheck);
				childrenList = (List<Member>) mergeMemberList(childrenList, (List<Member>)getChildrenObject(childMemberKey));

			}

		}

		return childrenList;

	}
	
	
	
	
	
	
	public Object getParentObject(Integer id) {
		List<Integer> parentList = new ArrayList<>();
		List<Integer> parentObjList = new ArrayList<>();
		List<Integer> resultList = new ArrayList<>();
		Member child = memberService.findById(id);

		if (child != null) {
			
			System.out.println("Start searching parents for: " + child.getName() + " [" + id + "]");

			if (child.getMumKey() != null || child.getDadKey() != null) {
				
				//jsonAncestor.put("parents", getParentsList(id));
				parentList = (List<Integer>) mergeIntegerList(parentList, (List<Integer>)getParentsList(id));

			}
			System.out.println("(No more parents found for: " + child.getName() + " [" + id + "])");
			parentObjList.add(id);
			parentList = (List<Integer>) mergeIntegerList(parentList, parentObjList);
//			resultList = Stream.of(parentList, parentObjList)
//					.flatMap(Collection::stream).collect(Collectors.toList());
			//return jsonAncestor;
			
			Set<Integer> set = new HashSet<>(resultList);
			resultList.clear();
			resultList.addAll(set);// Remove the duplicates
			
			return resultList;

		} else {
			
			return resultList;

		}

	}

	public Object getParentsList(Integer keyId) {
		Integer mumKey = null;
		Integer dadKey = null;
		List<Integer> parentList = new ArrayList<>();

		Map<String, Object> parentsMap = new LinkedHashMap();
		Member member = memberService.findById(keyId);

		if (member.getMumKey() != null) {
			mumKey = member.getMumKey();
			System.out.println("Found Mum of " + member.getName() + " [" + member.getKey() + "] - "
					+ memberService.findById(mumKey).getName() + " [" + mumKey + "]");
			parentList = (List<Integer>) mergeIntegerList(parentList, (List<Integer>)getParentObject(mumKey));
			//parentsMap.put("m", getParentObject(mumKey));
			parentList.add(mumKey);
		}

		if (member.getDadKey() != null) {
			dadKey = member.getDadKey();
			System.out.println("Found Dad of " + member.getName() + " [" + member.getKey() + "] - "
					+ memberService.findById(dadKey).getName() + " [" + dadKey + "]");
			parentList = Stream.of(parentList, (List<Integer>)getParentObject(dadKey))
					.flatMap(Collection::stream).collect(Collectors.toList());
			//parentsMap.put("f", getParentObject(dadKey));
			parentList.add(dadKey);
		}

		//return parentsMap;
		return parentList;
	}
	
	
	
	public void setupMarriage(Integer workingMode, Integer memberKey) {
		
		
		List<Member> members = (List<Member>) memberService.findAllMembers();
		List<Member> marriagedMemberList = new ArrayList<>();
		
		for (Member memberForCheckMarriage : members) {
			Integer dadKey = memberForCheckMarriage.getDadKey();
			Integer mumKey = memberForCheckMarriage.getMumKey();
			
			if (dadKey!=null && mumKey!=null) { // Couples family
				Member maleSpouse = memberService.findById(dadKey);
				Member femaleSpouse = memberService.findById(mumKey);
				maleSpouse.setSpouseId(mumKey);
				femaleSpouse.setSpouseId(dadKey);
				memberService.save(maleSpouse);
				memberService.save(femaleSpouse);
				marriagedMemberList.add(maleSpouse);
				marriagedMemberList.add(femaleSpouse);
				
			} else if (dadKey==null && mumKey!=null) { // Mum-only family
				Member femaleSpouse = memberService.findById(mumKey);
				femaleSpouse.setSpouseId(0);
				memberService.save(femaleSpouse);
				marriagedMemberList.add(femaleSpouse);
				
			} else if (dadKey!=null && mumKey==null) { // Dad-only family
				Member maleSpouse = memberService.findById(dadKey);
				maleSpouse.setSpouseId(0);
				memberService.save(maleSpouse);
				marriagedMemberList.add(maleSpouse);
			}
		}
			
		if (workingMode == 0) { // For whole family tree	
			
		} else if (workingMode == 1) {// For ancestor tree
			Member rootMember = memberService.findById(memberKey);
			rootMember.setSpouseId(0);
			memberService.save(rootMember);
			
		} else if (workingMode == 2) {// For descendant tree
			
		}
		
	}
	
	public Object mergeIntegerList (List<Integer> list1, List<Integer> list2) {
		list1.removeAll(list2);
		list1.addAll(list2);
		return list1;
	}
	
	public Object mergeMemberList (List<Member> list1, List<Member> list2) {
		list1.removeAll(list2);
		list1.addAll(list2);
		return list1;
	}


}
