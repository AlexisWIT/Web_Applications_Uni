package gEapp.controller;

import java.text.SimpleDateFormat;
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

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import gEapp.domain.InputChecker;
import gEapp.domain.JSONOrderedObject;
import gEapp.domain.JSONResponse;
import gEapp.domain.Member;
import gEapp.repository.MemberRepository;
import gEapp.service.MemberService;

@Controller
public class InterfaceController {
	
	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;
	IndexController indexController;
	InputChecker inputChecker = new InputChecker();
	JSONObject jsonResponse = new JSONObject();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	
	@RequestMapping(value="/GE/FamilyTree", method=RequestMethod.GET)
	public @ResponseBody JSONResponse createFamilyTree() {
		
		JSONResponse familyTreeResponse = new JSONResponse();
		List<Member> listForTree = new ArrayList<>();
		setupMarriage(0,0);
		
		System.out.println("Creating diagram, please wait...");
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
	
	
	@RequestMapping(value="/GE/editPersonJSON", method=RequestMethod.POST)
	@ResponseBody
	public Object editPerson(@RequestBody String person) throws ParseException {
		System.out.println("Received request for editing person :"+person);
		final Gson gson = new Gson();
		JSONParser jsonParser = new JSONParser();
		JSONResponse jsonEditResponse = new JSONResponse();
		
		if (inputChecker.isValidJSON(person, gson)) {
			
		} else {
			jsonEditResponse.setResult("false");
			jsonEditResponse.setMessage("Invalid input format!");
			return jsonEditResponse;
		}
		
		JSONObject jsonObject = (JSONObject) jsonParser.parse(person);
		
		Integer keyId = Integer.valueOf((String) jsonObject.get("key"));
		Integer actualKeyId = Integer.valueOf((String)jsonObject.get("actualKey"));
		
		System.out.println("KeyId="+keyId+", ActualKey="+actualKeyId);
		
		String name = (String) jsonObject.get("name");
		Integer birthday = null;
		String gender = null;
		Integer mumKey = null;
		Integer dadKey = null;
		
		if (((String)jsonObject.get("dob")).equals("null")) {
			
		} else {
			birthday = Integer.valueOf((String)jsonObject.get("dob"));
		}
		
		if (((String)jsonObject.get("gender")).equals("null")) {
			
		} else {
			gender = (String)jsonObject.get("gender");
			// Make the first letter to lower case
			gender = gender.substring(0, 1).toLowerCase() + gender.substring(1);
		}

		if (((String)jsonObject.get("mkey")).equals("null")) {
		
		} else {
			mumKey = Integer.valueOf((String)jsonObject.get("mkey"));
		}
		
		if (((String)jsonObject.get("fkey")).equals("null")) {
		
		} else {
			dadKey = Integer.valueOf((String)jsonObject.get("fkey"));
		}
		
		if (isValidEditedPerson(keyId, actualKeyId, name, birthday, gender, mumKey, dadKey)) {
			System.out.println("Person ["+keyId+"] input is VALID");
		} else {
			return jsonResponse;
		}
		
		Member member = memberService.findById(keyId);
		member.setName(name);
		member.setBirthday(birthday);
		member.setGender(gender);
		member.setMumKey(mumKey);
		member.setDadKey(dadKey);
		memberService.save(member);
		
		System.out.println("SAVED: Person info has been updated successfully!");
		jsonEditResponse.setResult("true");
		jsonEditResponse.setMessage("Person info has been updated.");
		return jsonEditResponse;
		
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
					
					memberForCheck.setDadKey(null);
					Integer childMemberKey = memberForCheck.getKey();
					
					System.out.println("(1) Get Child For Mum: " + parent.getName() + " ["
							+ id + "] - " + memberForCheck.getName() + " [" + childMemberKey + "]");
					
					childrenObjList.add(memberForCheck);
					childrenList = (List<Member>) mergeMemberList(childrenList, (List<Member>)getChildrenList(childMemberKey));
					
				} else if (memberForCheck.getDadKey() != null && memberForCheck.getDadKey() == id) {

					memberForCheck.setMumKey(null);
					Integer childMemberKey = memberForCheck.getKey();
					
					System.out.println("(1) Get Child For Dad: " + parent.getName() + " ["
							+ id + "] - " + memberForCheck.getName() + " [" + childMemberKey + "]");
					
					childrenObjList.add(memberForCheck);
					childrenList = (List<Member>) mergeMemberList(childrenList, (List<Member>)getChildrenList(childMemberKey));

				}

			}
			
			System.out.println("(No more children found for: " + parent.getName() + " [" + id + "])");
			childrenObjList.add(parent);
			
			resultList = (List<Member>) mergeMemberList(childrenList, childrenObjList);
			
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
				
				memberForCheck.setDadKey(null);
				Integer childMemberKey = memberForCheck.getKey();
				
				System.out.println("(2) Get Child For Mum: " + childrenMember.getName() + " ["
						+ id + "] - " + memberForCheck.getName() + " [" + childMemberKey + "]");
				
				childrenList.add(memberForCheck);
				childrenList = (List<Member>) mergeMemberList(childrenList, (List<Member>)getChildrenObject(childMemberKey));
				
				
			} else if (memberForCheck.getDadKey() != null && memberForCheck.getDadKey() == id) {

				memberForCheck.setMumKey(null);
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
				parentList = (List<Integer>) mergeIntegerList(parentList, (List<Integer>)getParentsList(id));
			}
			
			System.out.println("(No more parents found for: " + child.getName() + " [" + id + "])");
			parentObjList.add(id);
			resultList = (List<Integer>) mergeIntegerList(parentList, parentObjList);
			
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
		Member member = memberService.findById(keyId);

		if (member.getMumKey() != null) {
			mumKey = member.getMumKey();
			
			System.out.println("Found Mum of " + member.getName() + " [" + member.getKey() + "] - "
					+ memberService.findById(mumKey).getName() + " [" + mumKey + "]");
			
			parentList = (List<Integer>) mergeIntegerList(parentList, (List<Integer>)getParentObject(mumKey));
			parentList.add(mumKey);
		}

		if (member.getDadKey() != null) {
			dadKey = member.getDadKey();
			
			System.out.println("Found Dad of " + member.getName() + " [" + member.getKey() + "] - "
					+ memberService.findById(dadKey).getName() + " [" + dadKey + "]");
			
			parentList = (List<Integer>) mergeIntegerList(parentList, (List<Integer>)getParentObject(dadKey));
			parentList.add(dadKey);
		}

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
				femaleSpouse.setSpouseId(null);
				memberService.save(femaleSpouse);
				marriagedMemberList.add(femaleSpouse);
				
			} else if (dadKey!=null && mumKey==null) { // Dad-only family
				Member maleSpouse = memberService.findById(dadKey);
				maleSpouse.setSpouseId(null);
				memberService.save(maleSpouse);
				marriagedMemberList.add(maleSpouse);
			} else {
				marriagedMemberList.add(memberForCheckMarriage);
			}
			
		}
			
		if (workingMode == 0) { // For whole family tree	
			
		} else if (workingMode == 1) {// For ancestor tree
			Member rootMember = memberService.findById(memberKey);
			rootMember.setSpouseId(null);
			memberService.save(rootMember);
			
		} else if (workingMode == 2) {// For descendant tree
			for (Member memberForSetMarriage : members) {
				memberForSetMarriage.setSpouseId(null);
				memberService.save(memberForSetMarriage);
			}
		}
		
	}
	
	public List<Integer> mergeIntegerList (List<Integer> list1, List<Integer> list2) {
		System.out.println("Merge Int lists: \n"+list1.toString()+"\n"+list2.toString());
		
		List<Integer> resultList = new ArrayList<>();
		resultList = Stream.of(list1, list2).flatMap(Collection::stream).collect(Collectors.toList());
		
		Set<Integer> resultSet = new HashSet<>(resultList);
		resultList.clear();
		resultList.addAll(resultSet);
		System.out.println("Merged:"+resultList.toString());
		
		return resultList;
	}
	
	public Object mergeMemberList (List<Member> list1, List<Member> list2) {
		System.out.println("Merge Member lists: \n"+list1.toString()+"\n"+list2.toString());
		
		List<Member> resultList = new ArrayList<>();
		resultList = Stream.of(list1, list2).flatMap(Collection::stream).collect(Collectors.toList());
		
		Set<Member> resultSet = new HashSet<>(resultList);
		resultList.clear();
		resultList.addAll(resultSet);
		System.out.println("Merged:"+resultList.toString());
				
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	public boolean isValidEditedPerson (Integer key, Integer actualKey, String name, Integer birthday, String gender, Integer mumKey, Integer dadKey) {
		Integer mumBirthday = null;
		Integer dadBirthday = null;
		
		jsonResponse = new JSONObject();
		
		if (key!=null&&actualKey!=null) {
			int newKey = key;
			int newActualKey = actualKey;
			
			// Check Key ID
			if (key!=actualKey) {
				System.out.println("---- Warning: Person ID Changed ----");
				System.out.println("Key="+newKey+"ActualKey="+newActualKey);
				jsonResponse.put("result", "false");
				jsonResponse.put("message", "person id must NOT be changed");
				return false;
				
			}
			
		} else {
			// Check Key ID
			if (key!=actualKey) {
				System.out.println("---- Warning: Person ID Changed ----");
				System.out.println("Key="+key+"ActualKey="+actualKey);
				jsonResponse.put("result", "false");
				jsonResponse.put("message", "person id must NOT be changed");
				return false;
				
			}
			
		}
		
	    
	    
		
		// Check Name
		//If name is not original name and still exists already
		if (memberRepository.findByName(name)!=null && !name.equals(memberService.findById(actualKey).getName())) {
			System.out.println("---- Person Name exists ----");
			jsonResponse.put("result", "false");
			jsonResponse.put("message", "person name ["+name+"] is already exists");
			return false;
			
		}
		
		// Check Birthday
		if (!inputChecker.isValidDate(birthday, dateFormat)) {
			System.out.println("---- Invalid Birthday ----");
			jsonResponse.put("result", "false");
			jsonResponse.put("message", "Date ["+birthday+"] is NOT valid");
			return false;
			
		}
		
		// Check Gender	
		if (!StringUtils.isBlank(gender)) {
			
			if (!(gender.equals("male")) && !(gender.equals("female"))) {

				System.out.println("---- Invalid Gender ----");
				jsonResponse.put("result", "false");
				jsonResponse.put("message", "gender ["+gender+"] is NOT valid");
				return false;
				
			} else {
				List<Member> allMembers = new ArrayList<>();
				allMembers = (List<Member>) memberService.findAllMembers();
				for (Member member: allMembers) {
					if (member.getMumKey()==key && gender.equals("male")) {
						jsonResponse.put("result", "false");
						jsonResponse.put("message", "This person is "+member.getName()+" ["+member.getKey()
															+"]'s mother, whose gender can not be 'male'!");
						return false;
						
					} else if (member.getDadKey()==key && gender.equals("female")) {
						jsonResponse.put("result", "false");
						jsonResponse.put("message", "This person is "+member.getName()+" ["+member.getKey()
															+"]'s father, whose gender can not be 'female'!");
						return false;
					}
				}
				
			}
			
		}
		
		// Check Mother Key
		if (mumKey!=null) {
			Member mum = memberService.findById(mumKey);
			if (mum!=null) {
				if (mum.getGender()!="female" && mum.getGender()!=null) {
					System.out.println("---- Invalid Mum Gender ----");
					jsonResponse.put("result", "false");
					jsonResponse.put("message", "person(mum) with key ID ["+mumKey+"] is a male");
					return false;
					
				}
				mumBirthday = mum.getBirthday();
				
			} 
			else {
				System.out.println("---- Invalid Mum Key ----");
				jsonResponse.put("result", "false");
				jsonResponse.put("message", "person(mum) with mumKey ID ["+mumKey+"] does NOT exist");
				return false;
			}
			
		}
		
		// Check Father Key
		if (dadKey!=null) {
			Member dad = memberService.findById(dadKey);
			if (dad!=null) {
				if (dad.getGender()!="male" && dad.getGender()!=null) {
					System.out.println("---- Invalid Dad Gender ----");
					jsonResponse.put("result", "false");
					jsonResponse.put("message", "person(dad) with key ID ["+dadKey+"] is a female");
					return false;
					
				}
				dadBirthday = dad.getBirthday();
				
			} 
			else {
				System.out.println("---- Invalid Dad Key ----");
				jsonResponse.put("result", "false");
				jsonResponse.put("message", "person(dad) with dadKey ID ["+dadKey+"] does NOT exist");
				return false;
			}
			
		}
		
		// Check birthday with parents
		if (mumBirthday!=null || dadBirthday!=null) {
			if (birthday!=null) {
				if (!inputChecker.isBornAfterParents(birthday, mumBirthday, dadBirthday, dateFormat)){
					System.out.println("---- Invalid Birthday ----");
					jsonResponse.put("result", "false");
					jsonResponse.put("message", "person may not be born before parents' birthday");
					return false;
					
				}
				
			}
			
		}
		return true;
	}


}
