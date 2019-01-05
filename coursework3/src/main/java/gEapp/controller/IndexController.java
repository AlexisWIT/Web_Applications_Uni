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
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import gEapp.domain.JSONOrderedObject;
import gEapp.domain.Member;
import gEapp.repository.MemberRepository;
import gEapp.service.MemberService;

@Controller
@RequestMapping("/GE/person")
public class IndexController{
	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	JSONObject jsonResponse = new JSONObject();
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index() {
		return "IndexPage";
	}
	
	//(a) Adding a person
	@RequestMapping(value="/add", method=RequestMethod.GET)
	@ResponseBody
	public Object addMember(@RequestParam(value="key", required=true) int keyId,
							@RequestParam(value="name", required=true) String name,
							@RequestParam(value="m", required=false) Integer mumKey,
							@RequestParam(value="f", required=false) Integer dadKey,
							@RequestParam(value="dob", required=false) Integer birthday,
							@RequestParam(value="g", required=false) String gender) {
		
		JSONObject jsonResponseAdd = new JSONObject();
		
		// Make the first letter to lower case
		if (gender!=null) {
			gender = gender.substring(0, 1).toLowerCase()+gender.substring(1);
		}
		
		if (!isValidPerson(keyId, name, birthday, gender, mumKey, dadKey)) {
			System.out.println("Person ["+keyId+"] input is NOT VALID");
			return jsonResponse.toMap();
		}
		
		Member member = new Member(keyId, name, birthday, gender, mumKey, dadKey);
		memberService.save(member);
		
		System.out.println("SAVED: "+member.toString());
		System.out.println("SUCCESS: The person input have been saved!");
		jsonResponseAdd.put("result", "true");
		return jsonResponseAdd.toMap();
	}

	@RequestMapping(value="/addJSON", method=RequestMethod.POST)
	@ResponseBody
	public Object addMemberJSON(@RequestBody String memberInput) throws ParseException {
		
		final Gson gson = new Gson();
		//final ObjectMapper objectMapper = new ObjectMapper();
		JSONParser jsonParser = new JSONParser();
		//JSONObject jsonResponse = new JSONObject();
		
		// Check if it's empty/blank string
		if (StringUtils.isBlank(memberInput)) {
			System.out.println("String entered is blank");
			jsonResponse.put("result", "false");
			jsonResponse.put("message", "String entered is blank");
			return jsonResponse;
			
		} else {
			// Check if it's valid JSON input
			//if (isValidJSON(memberInput, gson, objectMapper)) {
			if (isValidJSON(memberInput, gson)) {
				
				// If only one person to be added - JSON Object
				if (isJSONObject(memberInput)) {
					
					jsonResponse = new JSONObject();
					JSONObject jsonObject = (JSONObject) jsonParser.parse(memberInput);
					
					// Get Key ID
					int keyId = (Integer) jsonObject.get("key");
					
					// Get Name
					String name = (String) jsonObject.get("name");
					
					// Get Birthday
					Integer birthday = (Integer) jsonObject.get("dob");
					
					// Get Gender
					String gender = (String) jsonObject.get("g");				
						// Make the first letter to lower case
						if (gender!=null) {
							gender = gender.substring(0, 1).toLowerCase()+gender.substring(1);
						}
						
					
					// Get Mother Key
					Integer mumKey = (Integer) jsonObject.get("m");
					
					// Get Father Key
					Integer dadKey = (Integer) jsonObject.get("f");
					
					if (isValidPerson(keyId, name, birthday, gender, mumKey, dadKey)) {
						System.out.println("Person ["+keyId+"] input is VALID");
					} else {
						return jsonResponse.toMap();
					}
					
					Member member = new Member(keyId, name, birthday, gender, mumKey, dadKey);
					memberService.save(member);
					
					System.out.println("SAVED: "+member.toString());
					System.out.println("SUCCESS: The person input have been saved!");
					jsonResponse.put("result", "true");
					return jsonResponse.toMap();
					
				// Else (multiple persons) - JSON Array	
				} else if (isJSONArray(memberInput)) {
					
					JSONArray jsonArray = (JSONArray) jsonParser.parse(memberInput);
					
					for (int i = 0; i < jsonArray.length(); i++) {
						
						jsonResponse = new JSONObject();
					    JSONObject extractedJsonObject = jsonArray.getJSONObject(i);
					    
					    // Get Key ID
						int keyId = (Integer) extractedJsonObject.get("key");
						
						// Get Name
						String name = (String) extractedJsonObject.get("name");
						
						// Get Birthday
						Integer birthday = (Integer) extractedJsonObject.get("dob");
						
						// Get Gender
						String gender = (String) extractedJsonObject.get("g");				
							// Make the first letter to lower case
							if (gender!=null) {
								gender = gender.substring(0, 1).toLowerCase()+gender.substring(1);
							}
						
						// Get Mother Key
						Integer mumKey = (Integer) extractedJsonObject.get("m");
						
						// Get Father Key
						Integer dadKey = (Integer) extractedJsonObject.get("f");
						
						if (isValidPerson(keyId, name, birthday, gender, mumKey, dadKey)) {
							System.out.println("Person ["+keyId+"] input is VALID");
						} else {
							return jsonResponse.toMap();
						}
						
						Member member = new Member(keyId, name, birthday, gender, mumKey, dadKey);
						memberService.save(member);
						
						System.out.println("SAVED: "+member.toString());
	
					}
					System.out.println("SUCCESS: All the person input have been saved!");
					jsonResponse.put("result", "true");
					return jsonResponse.toMap();
					
				} else {
					System.out.println("FAILED: Error occured!");
					jsonResponse.put("result", "false");
					jsonResponse.put("message", "Error occured");
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
	
	// Check if String input is valid for parsing to JSON
	//public boolean isValidJSON (String inputString, Gson gson, ObjectMapper mapper) {
	public boolean isValidJSON (String inputString, Gson gson) {
		try {
			gson.fromJson(inputString, Object.class);
			//mapper.readTree(inputString);			
			return true;
			
		} catch (Exception e1) {
			System.out.println("Invalid Input String");
			return false;	
		}
	}
	
	public boolean isJSONArray (String input) {
		try {
			new JSONArray(input);
			System.out.println("Input String can be parsed to JSON Array");
			return true;
			
		} catch (JSONException e2) {
			return false;
		}
	}
	
	public boolean isJSONObject (String input) {
		try {
			new JSONObject(input);
			System.out.println("Input String can be parsed to JSON Object");
			return true;
			
		} catch (JSONException e3) {
			return false;
		}
	}
	
	public boolean isValidDate (Integer dateInput) {
		Date date;
		if (dateInput!=null) {
			try {
				date = dateFormat.parse(dateInput.toString());
//				SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
//				String formatedDate = newFormat.format(date);	
				return true;
				
			} catch (java.text.ParseException e) {
				e.printStackTrace();
				System.out.println("Invalid Date input");
				return false;
				
			}
			
		} else {
			return true;
			
		}
		
	}
	
	public boolean isBornAfterParents (Integer dateInput, Integer mumBirthday, Integer dadBirthday) {
		Date birthday;
		Date mumbirthday;
		Date dadbirthday;
		
		try {
			birthday = dateFormat.parse(dateInput.toString());
			if (mumBirthday!=null && dadBirthday!=null) {
				mumbirthday = dateFormat.parse(mumBirthday.toString());
				dadbirthday = dateFormat.parse(dadBirthday.toString());
				
				if (birthday.after(mumbirthday) && birthday.after(dadbirthday)) {
					return true;
				} else {
					return false;
				}
					
			} else if (mumBirthday!=null && dadBirthday==null) {
				mumbirthday = dateFormat.parse(mumBirthday.toString());
				
				if (birthday.after(mumbirthday)) {
					return true;
				} else {
					return false;
				}
				
			} else if (mumBirthday==null && dadBirthday!=null) {
				dadbirthday = dateFormat.parse(dadBirthday.toString());
				
				if (birthday.after(dadbirthday)) {
					return true;
				} else {
					return false;
				}
				
			} else {
				return true;
			}
			
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			System.out.println("Parsing Date Error");
			return false;
		}
		
	}
	
	public boolean isValidPerson (Integer id, String name, Integer birthday, String gender, Integer mumKey, Integer dadKey) {
		Integer mumBirthday = 0;
		Integer dadBirthday = 0;
		
		//jsonResponse = new JSONObject();
	    
	    // Check Key ID
		if (memberService.findById(id)!=null) {
			System.out.println("---- Person ID exists ----");
			jsonResponse.put("result", "false");
			jsonResponse.put("message", "person id ["+id+"] already exists");
			return false;
			
		}
		
		// Check Name
		if (memberRepository.findByName(name)!=null) {
			System.out.println("---- Person Name exists ----");
			jsonResponse.put("result", "false");
			jsonResponse.put("message", "person name ["+name+"] already exists");
			return false;
			
		}
		
		// Check Birthday
		if (!isValidDate(birthday)) {
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
				
			} 
			
		}
		
		// Check Mother Key
		if (mumKey!=null) {
			Member mum = memberService.findById(mumKey);
			if (mum!=null) {
				if (mum.getGender()!="female" && mum.getGender()!=null) {
					System.out.println("---- Invalid Mum Gender ----");
					jsonResponse.put("result", "false");
					jsonResponse.put("message", "person(mum) with key ID ["+mumKey+"] may have a wrong gender");
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
					jsonResponse.put("message", "person(dad) with key ID ["+dadKey+"] may have a wrong gender");
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
				if (!isBornAfterParents(birthday, mumBirthday, dadBirthday)){
					System.out.println("---- Invalid Birthday ----");
					jsonResponse.put("result", "false");
					jsonResponse.put("message", "person may not be born before parents' birthday");
					return false;
					
				}
				
			}
			
		}
		return true;
	}

	
	//(b) Deleting a person
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET) 
	@ResponseBody
	public Object deleteMember(@PathVariable Integer id ) {
		JSONObject jsonResponseDelete = new JSONObject();
		Member member = memberService.findById(id);
		
		if (member!=null) {
			memberService.deleteById(id);
			jsonResponseDelete.put("result", "true");
			
		} else {
			jsonResponseDelete.put("result", "false");
			jsonResponseDelete.put("message", "person id ["+id+"] can NOT be found");
			return jsonResponseDelete;
			
		}
		return jsonResponseDelete.toMap();
		
	}
	
	//(c) Getting information about a specific person
	@RequestMapping(value="/get/{id}", method=RequestMethod.GET)
	@ResponseBody
	public Object getMemberInfo(@PathVariable Integer id) {
		JSONObject jsonResponseMemberInfo = new JSONObject();
		Member member = memberService.findById(id);
		if (member!=null) {
			return member;
			
		} else {
			jsonResponseMemberInfo.put("result", "false");
			jsonResponseMemberInfo.put("message", "person with id ["+id+"] does NOT exist");
			return jsonResponseMemberInfo.toMap();
			
		}
		
	}
	
	//(d) Finding someones ancestors
	@RequestMapping(value="/ancestors/{id}", method=RequestMethod.GET)
	@ResponseBody
	public Object getMemberAncestors(@PathVariable Integer id) {
		JSONObject jsonResponseAncestor = new JSONObject();
		
		if (memberService.findById(id)!=null) {
			Member member = memberService.findById(id);
			System.out.println(member.getName()+" ["+id+"]");
			jsonResponseAncestor.put("key", id.toString());
			
			if (member.getMumKey()!=null || member.getDadKey()!=null) {
				jsonResponseAncestor.put("parents", getParentsList(id));
				
			}
			return jsonResponseAncestor.toMap();
			
		} else {
			jsonResponseAncestor.put("result", "false");
			jsonResponseAncestor.put("message", "person with id ["+id+"] does NOT exist");
			return jsonResponseAncestor.toMap();
			
		}
		
	}
	
	public Object getParentsList(Integer keyId) {
		Integer mumKey = null;
		Integer dadKey = null;
		JSONObject jsonResponseParents = new JSONObject();
		Member member = memberService.findById(keyId);

		if(member.getDadKey()!=null) {
			dadKey = member.getDadKey();
			jsonResponseParents.put("f", getMemberAncestors(dadKey));
		}
		
		if(member.getMumKey()!=null) {
			mumKey = member.getMumKey();
			jsonResponseParents.put("m", getMemberAncestors(mumKey));
		}
		
		return jsonResponseParents;
	}
	
	//(e) Finding someones descendants
	@RequestMapping(value="/descendants/{id}")
	@ResponseBody
	public Object getMemberDescendants(@PathVariable Integer id) {
		Map<String, Object> jsonResponseDescendant = new LinkedHashMap<>();
		Integer currentPersonId=id;
		
		if (memberService.findById(currentPersonId)!=null) {
			Member member = memberService.findById(currentPersonId);
			System.out.println(member.getName()+" ["+currentPersonId+"]");
			
			
			@SuppressWarnings("unchecked")
			Collection<Member> memberInDatabase = makeCollection(memberService.findAllIter());
			int totalMember = memberInDatabase.size();

			
			List<Object> jsonChildrenArray = new ArrayList();
			
			for (Member memberForCheck : memberInDatabase) {		
				if ((memberForCheck.getMumKey()!=null && memberForCheck.getMumKey()==currentPersonId)
						|| (memberForCheck.getDadKey()!=null && memberForCheck.getDadKey()==currentPersonId)) {
					
					Integer childMemberKey = memberForCheck.getId();
					jsonChildrenArray.add(getChildrenList(childMemberKey));
					
				}
				
			}
			jsonResponseDescendant.put("key", currentPersonId.toString());
			
			if (jsonChildrenArray.size()!=0) {
				jsonResponseDescendant.put("children", jsonChildrenArray);
			}
			
			return jsonResponseDescendant;
			
		} else {
			jsonResponseDescendant.put("result", "false");
			jsonResponseDescendant.put("message", "person with id ["+currentPersonId+"] does NOT exist");
			return jsonResponseDescendant;
			
		}
		
	}
	
	public Object getChildrenList(Integer id) {
		
		Map<String,Object>jsonChildren = new LinkedHashMap<>();
		
		@SuppressWarnings("unchecked")
		Collection<Member> memberInDatabase = makeCollection(memberService.findAllIter());
		
		
		List<Object> jsonChildrenArray = new ArrayList();
		
		for (Member memberForCheck : memberInDatabase) {
			if ((memberForCheck.getMumKey()!=null && memberForCheck.getMumKey()==id)
					|| (memberForCheck.getDadKey()!=null && memberForCheck.getDadKey()==id)) {
				
				Integer childMemberKey = memberForCheck.getId();
				jsonChildrenArray.add(getMemberDescendants(childMemberKey));
				
			}
			
		}
		jsonChildren.put("key", id.toString());
		
		if (jsonChildrenArray.size()!=0) {
			jsonChildren.put("children", jsonChildrenArray);
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
	
}
