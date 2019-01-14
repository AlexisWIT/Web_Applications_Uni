package gEapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import gEapp.domain.JSONOrderedObject;
import gEapp.domain.Member;
import gEapp.repository.MemberRepository;
import gEapp.service.MemberService;

@Controller
@RequestMapping("/")
public class IndexController {
	@Autowired
	MemberService memberService;
	@Autowired
	MemberRepository memberRepository;
	
	InputChecker inputChecker = new InputChecker();
	Gson gson = new Gson();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	JSONObject jsonResponse = new JSONObject();

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		System.out.println("IndexPage");
		return "IndexPage";
	}

	// (a) Adding a person
	@RequestMapping(value = "/GE/person/add", method = RequestMethod.GET)
	@ResponseBody
	public Object addMember(@RequestParam(value = "key", required = true) Integer keyId,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "dob", required = false) Integer birthday,
			@RequestParam(value = "g", required = false) String gender,
			@RequestParam(value = "m", required = false) Integer mumKey,
			@RequestParam(value = "f", required = false) Integer dadKey) {
		
		JSONObject jsonResponseAdd = new JSONObject();
		
		// Make the first letter to lower case
		if (gender != null) {
			gender = gender.substring(0, 1).toLowerCase() + gender.substring(1);
		}
		
		if (isValidPerson(keyId, name, birthday, gender, mumKey, dadKey)) {
			System.out.println("Person ["+keyId+"] input is VALID");
		} else {
			return jsonResponse.toMap();
		}
			
		Member member = new Member(keyId, name, birthday, gender, mumKey, dadKey);
		memberService.save(member);

		System.out.println("SAVED: " + member.toString());
		System.out.println("SUCCESS: The person input have been saved!");
		jsonResponseAdd.put("result", "true");
		return jsonResponseAdd.toMap();
	}

	@RequestMapping(value = "/GE/person/addJSON", method = RequestMethod.POST)
	@ResponseBody
	public Object addMemberJSON(@RequestBody String memberInput) {

		System.out.println(memberInput);
		final Gson gson = new Gson();

		// Check if it's empty/blank string
		if (StringUtils.isBlank(memberInput)) {
			System.out.println("String entered is blank");
			jsonResponse.put("result", "false");
			jsonResponse.put("message", "String entered is blank");
			return jsonResponse;

		} else {
			// Check if it's valid JSON input
			// if (isValidJSON(memberInput, gson, objectMapper)) {
			if (inputChecker.isValidJSON(memberInput, gson)) {

				// If it's JSON Object
				if (inputChecker.isJSONObject(memberInput)) {
					
					String parsableInput = memberInput;
					jsonResponse = new JSONObject();

					JSONObject jsonObject = new JSONObject(parsableInput);
					Iterator<String> jsonKeyIterator = jsonObject.keys();
					
					Integer keyId = null;
					String name = null;
					Integer birthday = null;
					String gender = null;
					Integer mumKey = null;
					Integer dadKey = null;
					
					while (jsonKeyIterator.hasNext()) {
				        String keyName = (String)jsonKeyIterator.next();
				        String keyValue = null;
				        JSONArray keyValueArray = new JSONArray();
				        JSONObject keyValueObject = new JSONObject();
				        
				        try {
				        	keyValue = jsonObject.getString(keyName);
				        	
				        } catch (JSONException e1) {
				        	System.out.println("keyValue for KeyName ["+keyName+"] is not String");
				        	try {
				        		keyValueArray = jsonObject.getJSONArray(keyName);
				        		System.out.println("JSONArray found as the keyValue for KeyName ["+keyName+"] in the current JSONObject!");
					        	addMemberJSON(keyValueArray.toString());
				        		
				        	} catch (JSONException e2) {
				        		System.out.println("keyValue for KeyName ["+keyName+"] is not JSONArray");
				        		try {
				        			keyValueObject = jsonObject.getJSONObject(keyName);
				        			System.out.println("Another JSONObject found as the keyValue for KeyName ["+keyName+"] in the current JSONObject!");
						        	addMemberJSON(keyValueObject.toString());
						        	
				        		} catch (JSONException e3) {
				        			System.out.println("keyValue for KeyName ["+keyName+"] is Unknown");
				        			jsonResponse.put("result", "false");
				        			jsonResponse.put("message", "Unknown Internal Error");
				        			return jsonResponse;
				        		}
				        	}
				        	
				        }
				        	
			        	switch (keyName) {
			        		case "key" :
				        			System.out.println("key="+keyValue);
				        			try {
				        				keyId = Integer.valueOf(keyValue);
				        			} catch (NumberFormatException ne1) { }
				        			break;
			        		case "name":
				        			System.out.println("name="+keyValue);
				        			name = keyValue;
				        			break;
			        		case "dob":
				        			System.out.println("dob="+keyValue);
				        			try {
				        				birthday = Integer.valueOf(keyValue);
				        			} catch (NumberFormatException ne1) { }
				        			break;
			        		case "g":
				        			System.out.println("g="+keyValue);
				        			gender = keyValue;
				        			break;
			        		case "m":
				        			System.out.println("m="+keyValue);
				        			try {
				        				mumKey = Integer.valueOf(keyValue);
				        			} catch (NumberFormatException ne2) { }
				        			break;
			        		case "f":
				        			System.out.println("f="+keyValue);
				        			try {
				        				dadKey = Integer.valueOf(keyValue);
				        			} catch (NumberFormatException ne3) { }
				        			break;
			        	}
					        
					}
					
					if (keyId != null && name != null) {	
						if (isValidPerson(keyId, name, birthday, gender, mumKey, dadKey)) {
							System.out.println("Person ["+keyId+"] input is valid");
						} else {
							return jsonResponse.toMap();
						}

						Member member = new Member(keyId, name, birthday, gender, mumKey, dadKey);
						memberService.save(member);
						System.out.println("SAVED: " + member.toString());
						System.out.println("SUCCESS: The person ["+name+"] input has been saved!");
						
					}

					jsonResponse.put("result", "true");
					return jsonResponse.toMap();

					// Else (multiple persons) - JSON Array
				} else if (inputChecker.isJSONArray(memberInput)) {
					
					JSONArray jsonArray = new JSONArray(memberInput);

					for (int i = 0; i < jsonArray.length(); i++) {

						jsonResponse = new JSONObject();
						JSONObject inArrayJsonObject = jsonArray.getJSONObject(i);
						
						Iterator<String> jsonKeyIterator = inArrayJsonObject.keys();
						
						Integer keyId = null;
						String name = null;
						Integer birthday = null;
						String gender = null;
						Integer mumKey = null;
						Integer dadKey = null;
						
						while (jsonKeyIterator.hasNext()) {
					        String keyName = (String)jsonKeyIterator.next();
					        String keyValue = null;
					        JSONArray keyValueArray = new JSONArray();
					        JSONObject keyValueObject = new JSONObject();
					        
					        try {
					        	keyValue = inArrayJsonObject.getString(keyName);
					        	
					        } catch (JSONException e1) {
					        	System.out.println("keyValue for KeyName ["+keyName+"] is not String");
					        	try {
					        		keyValueArray = inArrayJsonObject.getJSONArray(keyName);
					        		System.out.println("JSONArray found as the keyValue for KeyName ["+keyName+"] in the current JSONObject!");
						        	addMemberJSON(keyValueArray.toString());
					        		
					        	} catch (JSONException e2) {
					        		System.out.println("keyValue for KeyName ["+keyName+"] is not JSONArray");
					        		try {
					        			keyValueObject = inArrayJsonObject.getJSONObject(keyName);
					        			System.out.println("Another JSONObject found as the keyValue for KeyName ["+keyName+"] in the current JSONObject!");
							        	addMemberJSON(keyValueObject.toString());
							        	
					        		} catch (JSONException e3) {
					        			System.out.println("keyValue for KeyName ["+keyName+"] is Unknown");
					        			jsonResponse.put("result", "false");
					        			jsonResponse.put("message", "Unknown Internal Error");
					        			return jsonResponse;
					        		}
					        	}
					        	
					        }
					        	
				        	switch (keyName) {
				        		case "key" :
				        			System.out.println("key="+keyValue);
				        			try {
				        				keyId = Integer.valueOf(keyValue);
				        			} catch (NumberFormatException ne1) { }
				        			break;
				        		case "name":
				        			System.out.println("name="+keyValue);
				        			name = keyValue;
				        			break;
				        		case "dob":
				        			System.out.println("dob="+keyValue);
				        			try {
				        				birthday = Integer.valueOf(keyValue);
				        			} catch (NumberFormatException ne1) { }
				        			break;
				        		case "g":
				        			System.out.println("g="+keyValue);
				        			gender = keyValue;
				        			break;
				        		case "m":
				        			System.out.println("m="+keyValue);
				        			try {
				        				mumKey = Integer.valueOf(keyValue);
				        			} catch (NumberFormatException ne2) { }
				        			break;
				        		case "f":
				        			System.out.println("f="+keyValue);
				        			try {
				        				dadKey = Integer.valueOf(keyValue);
				        			} catch (NumberFormatException ne3) { }
				        			break;
				        	}
						        
						}
						
						if (keyId != null && name != null) {	
							if (isValidPerson(keyId, name, birthday, gender, mumKey, dadKey)) {
								System.out.println("Person ["+keyId+"] input is valid");
							} else {
								return jsonResponse.toMap();
							}

							Member member = new Member(keyId, name, birthday, gender, mumKey, dadKey);
							memberService.save(member);
							System.out.println("SAVED: " + member.toString());
							System.out.println("SUCCESS: The person ["+name+"] input has been saved!");
							
						}

					}
					jsonResponse.put("result", "true");
					return jsonResponse.toMap();

				} else {
					System.out.println("FAILED: Error occured!");
					jsonResponse.put("result", "false");
					jsonResponse.put("message", "Error occured! JSON format may not be correct.");
					return jsonResponse.toMap();

				}

			} else {
				System.out.println("FAILED: Invalid Input String!");
				jsonResponse.put("result", "false");
				jsonResponse.put("message", "Cannot parse Input String into JSON, please check syntex");
				return jsonResponse.toMap();

			}

		}

	}

	// (b) Deleting a person
	// remember to deleted marriage and Father/Mother status as well!
	@RequestMapping(value = "/GE/person/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Object deleteMember(@PathVariable Integer id) {
		JSONObject jsonResponseDelete = new JSONObject();
		Member member = memberService.findById(id);
		
		System.out.println("Received request for deleting ["+id+"]");

		if (member != null) {
			memberService.deleteById(id);
			jsonResponseDelete.put("result", "true");
			
			@SuppressWarnings("unchecked")
			List<Member> allMember = (List<Member>) memberService.findAllMembers();
			for (Member memberForUpdate : allMember) {
				Integer dadKey = memberForUpdate.getDadKey();
				Integer mumKey = memberForUpdate.getMumKey();
				Integer spouseId = memberForUpdate.getSpouseId();
				
				if (dadKey == id) {
					memberForUpdate.setDadKey(null);
				}
				
				if (mumKey == id) {
					memberForUpdate.setMumKey(null);
				}
				
				if (spouseId == id) {
					memberForUpdate.setSpouseId(null);
				}
				
				memberService.save(memberForUpdate);
				
			}
			System.out.println("Person ["+id+"] deleted successfully.");

		} else {
			jsonResponseDelete.put("result", "false");
			jsonResponseDelete.put("message", "person id [" + id + "] can NOT be found");
			return jsonResponseDelete;

		}
		return jsonResponseDelete.toMap();

	}

	// (c) Getting information about a specific person
	@RequestMapping(value = "/GE/person/get/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Object getMemberInfo(@PathVariable Integer id) {
		JSONOrderedObject jsonResponseMemberInfo = new JSONOrderedObject();
		Member member = memberService.findById(id);
		
		if (member != null) {
			Integer key = member.getKey();
			String name = member.getName();
			Integer dob = member.getBirthday();
			String	g	= member.getGender();
			Integer m	= member.getMumKey();
			Integer f	= member.getDadKey();
			
			jsonResponseMemberInfo.put("key", key.toString());
			jsonResponseMemberInfo.put("name", name);
			
			if (dob	!=null)	jsonResponseMemberInfo.put("dob", dob.toString());
			if ( g	!=null)	jsonResponseMemberInfo.put("g", g);
			if ( m	!=null)	jsonResponseMemberInfo.put("m", m.toString());
			if ( f	!=null)	jsonResponseMemberInfo.put("f", f.toString());
			
			return jsonResponseMemberInfo.toLinkedHashMap();

		} else {
			jsonResponseMemberInfo.put("result", "false");
			jsonResponseMemberInfo.put("message", "person with id [" + id + "] does NOT exist");
			return jsonResponseMemberInfo.toLinkedHashMap();

		}

	}

	// (d) Finding someones ancestors
	@RequestMapping(value = "/GE/person/ancestors/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Object getMemberAncestors(@PathVariable Integer id) {
		JSONOrderedObject jsonResponseAncestor = new JSONOrderedObject();

		if (memberService.findById(id) != null) {
			Member member = memberService.findById(id);
			System.out.println("Start searching parents for: " + member.getName() + " [" + id + "]");
			jsonResponseAncestor.put("key", id.toString());

			if (member.getMumKey() != null || member.getDadKey() != null) {
				jsonResponseAncestor.put("parents", getParentsList(id));

			}
			System.out.println("(No more parents found for: " + member.getName() + " [" + id + "])");
			return jsonResponseAncestor.toLinkedHashMap();

		} else {
			jsonResponseAncestor.put("result", "false");
			jsonResponseAncestor.put("message", "person with id [" + id + "] does NOT exist");
			return jsonResponseAncestor.toLinkedHashMap();

		}

	}

	@SuppressWarnings("rawtypes")
	public Object getParentsList(Integer keyId) {
		Integer mumKey = null;
		Integer dadKey = null;

		@SuppressWarnings("unchecked")
		Map<String, Object> parentsMap = new LinkedHashMap();
		Member member = memberService.findById(keyId);

		if (member.getMumKey() != null) {
			mumKey = member.getMumKey();
			System.out.println("Found Mum of " + member.getName() + " [" + member.getKey() + "] - "
					+ memberService.findById(mumKey).getName() + " [" + mumKey + "]");
			parentsMap.put("m", getMemberAncestors(mumKey));
		}

		if (member.getDadKey() != null) {
			dadKey = member.getDadKey();
			System.out.println("Found Dad of " + member.getName() + " [" + member.getKey() + "] - "
					+ memberService.findById(dadKey).getName() + " [" + dadKey + "]");
			parentsMap.put("f", getMemberAncestors(dadKey));
		}

		return parentsMap;
	}

	// (e) Finding someones descendants
	@RequestMapping(value = "/GE/person/descendants/{id}")
	@ResponseBody
	public Object getMemberDescendants(@PathVariable Integer id) {

		JSONOrderedObject jsonResponseDescendant = new JSONOrderedObject();

		Integer currentPersonId = id;

		if (memberService.findById(currentPersonId) != null) {
			Member member = memberService.findById(currentPersonId);
			System.out.println("Start searching children for: " + member.getName() + " [" + currentPersonId + "]");

			@SuppressWarnings("unchecked")
			Collection<Member> memberInDatabase = makeCollection(memberService.findAllIter());

			JSONArray jsonChildrenArray = new JSONArray();

			for (Member memberForCheck : memberInDatabase) {
				if ((memberForCheck.getMumKey() != null && memberForCheck.getMumKey() == currentPersonId)
						|| (memberForCheck.getDadKey() != null && memberForCheck.getDadKey() == currentPersonId)) {

					Integer childMemberKey = memberForCheck.getKey();
					System.out.println("(1) Get Child For: " + memberService.findById(currentPersonId).getName() + " ["
							+ currentPersonId + "] - " + memberForCheck.getName() + " [" + childMemberKey + "]");

					jsonChildrenArray.put(getChildrenList(childMemberKey));

				}

			}
			System.out.println("(No more children found for: " + member.getName() + " [" + currentPersonId + "])");
			jsonResponseDescendant.put("key", currentPersonId.toString());

			if (jsonChildrenArray.toList().size() != 0) {
				jsonResponseDescendant.put("children", jsonChildrenArray);
				System.out.println("(1) Children found: " + jsonChildrenArray.toString());
			}

			return jsonResponseDescendant.toLinkedHashMap();

		} else {
			jsonResponseDescendant.put("result", "false");
			jsonResponseDescendant.put("message", "person with id [" + currentPersonId + "] does NOT exist");

			return jsonResponseDescendant.toLinkedHashMap();

		}

	}

	@SuppressWarnings("rawtypes")
	public Object getChildrenList(Integer id) {

		Map<String, Object> jsonChildren = new LinkedHashMap<>();

		@SuppressWarnings("unchecked")
		Collection<Member> memberInDatabase = makeCollection(memberService.findAllIter());

		System.out.println("Start searching children for: " + memberService.findById(id).getName() + " [" + id + "]");

		@SuppressWarnings("unchecked")
		List<Object> jsonChildrenArray = new ArrayList();

		for (Member memberForCheck : memberInDatabase) {
			if ((memberForCheck.getMumKey() != null && memberForCheck.getMumKey() == id)
					|| (memberForCheck.getDadKey() != null && memberForCheck.getDadKey() == id)) {

				Integer childMemberKey = memberForCheck.getKey();
				System.out.println("(2) Get Child For: " + memberService.findById(id).getName() + " [" + id + "] - "
						+ memberForCheck.getName() + " [" + childMemberKey + "]");
				jsonChildrenArray.add(getMemberDescendants(childMemberKey));

			}

		}
		jsonChildren.put("key", id.toString());

		if (jsonChildrenArray.size() != 0) {

			jsonChildren.put("children", jsonChildrenArray);
			System.out.println("(2) Children found: " + jsonChildrenArray.toString());
		}

		return jsonChildren;

	}

	// Convert Iterable of all person in repository to List
	public static <E> Collection<E> makeCollection(Iterable<E> iterable) {
		Collection<E> list = new ArrayList<E>();
		for (E item : iterable) {
			list.add(item);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public boolean isValidPerson (Integer id, String name, Integer birthday, String gender, Integer mumKey, Integer dadKey) {
		Integer mumBirthday = null;
		Integer dadBirthday = null;
		
		jsonResponse = new JSONObject();
	    
	    // Check Key ID
		if (id==null) {
			System.out.println("---- Person ID is NULL ----");
			jsonResponse.put("result", "false");
			jsonResponse.put("message", "person id is null");
			return false;
			
			
		} else if (memberService.findById(id)!=null) {
			System.out.println("---- Person ID exists ----");
			jsonResponse.put("result", "false");
			jsonResponse.put("message", "person id ["+id+"] already exists");
			return false;
			
		}
		
		// Check Name
		if (name.equals(null)) {
			System.out.println("---- Person Name is NULL ----");
			jsonResponse.put("result", "false");
			jsonResponse.put("message", "person name is null");
			return false;
			
		} else if (memberRepository.findByName(name)!=null) {
			System.out.println("---- Person Name exists ----");
			jsonResponse.put("result", "false");
			jsonResponse.put("message", "person name ["+name+"] already exists");
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
					if (member.getMumKey()==id && gender.equals("male")) {
						jsonResponse.put("result", "false");
						jsonResponse.put("message", "This person is "+member.getName()+" ["+member.getKey()
															+"]'s mother, whose gender can not be 'male'!");
						return false;
						
					} else if (member.getDadKey()==id && gender.equals("female")) {
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
