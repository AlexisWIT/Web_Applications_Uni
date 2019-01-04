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
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import gEapp.domain.Member;
import gEapp.repository.MemberRepository;
import gEapp.service.MemberService;

@Controller
@RequestMapping("/GE/person")
public class IndexController {
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
	public String addMember(@RequestParam(value="key", required=true) int keyId,
							@RequestParam(value="name", required=true) String name,
							@RequestParam(value="m") int mumKey,
							@RequestParam(value="f") int dadKey,
							@RequestParam(value="dob") int birthday,
							@RequestParam(value="g") String gender) {
		
		Member member = new Member(keyId, name, birthday, gender, mumKey, dadKey);
		memberService.save(member);
		
		return "redirect:/GE/person";
	}

	@RequestMapping(value="/addJSON", method=RequestMethod.POST)
	@ResponseBody
	public JSONObject addMemberJSON(@RequestBody String memberInput) throws ParseException {
		
		final Gson gson = new Gson();
		//final ObjectMapper objectMapper = new ObjectMapper();
		JSONParser jsonParser = new JSONParser();
		
		// Check if it's empty/blank string
		if (StringUtils.isBlank(memberInput)) {
			System.out.println("String entered is blank");
			jsonResponse.put("result", false);
			jsonResponse.put("message", "String entered is blank");
			return jsonResponse;
			
		} else {
			// Check if it's valid JSON input
			//if (isValidJSON(memberInput, gson, objectMapper)) {
			if (isValidJSON(memberInput, gson)) {
				
				// If only one person to be added - JSON Object
				if (isJSONObject(memberInput)) {
					
					Integer mumBirthday = 0;
					Integer dadBirthday = 0;
					
					jsonResponse = new JSONObject();
					JSONObject jsonObject = (JSONObject) jsonParser.parse(memberInput);
					
					// Get Key ID
					int keyId = (Integer) jsonObject.get("key");
					if (memberService.findById(keyId)!=null) {
						System.out.println("---- Person ID exists ----");
						jsonResponse.put("result", false);
						jsonResponse.put("message", "person id ["+keyId+"] already exists");
						return jsonResponse;
						
					}
					
					// Get Name
					String name = (String) jsonObject.get("name");
					if (memberRepository.findByName(name)!=null) {
						System.out.println("---- Person Name exists ----");
						jsonResponse.put("result", false);
						jsonResponse.put("message", "person name ["+name+"] already exists");
						return jsonResponse;
						
					}
					
					// Get Birthday
					Integer birthday = (Integer) jsonObject.get("dob");
					if (!isValidDate(birthday)) {
						System.out.println("---- Invalid Birthday ----");
						jsonResponse.put("result", false);
						jsonResponse.put("message", "Date ["+birthday+"] is NOT valid");
						return jsonResponse;
						
					}
					
					// Get Gender
					String gender = (String) jsonObject.get("g");				
						// Make the first letter to lower case
						gender = gender.substring(0, 1).toLowerCase()+gender.substring(1);		
					if (!(gender=="male")&&!(gender=="female")) {
						System.out.println("---- Invalid Gender ----");
						jsonResponse.put("result", false);
						jsonResponse.put("message", "gender ["+gender+"] is NOT valid");
						return jsonResponse;
						
					}
					
					// Get Mother Key
					int mumKey = (Integer) jsonObject.get("m");
					if (mumKey!=0) {
						Member mum = memberService.findById(mumKey);
						if (mum!=null) {
							if (mum.getGender()!="female") {
								System.out.println("---- Invalid Mum Gender ----");
								jsonResponse.put("result", false);
								jsonResponse.put("message", "person(mum) with key ID ["+mumKey+"] may have a wrong gender");
								return jsonResponse;
								
							}
							mumBirthday = mum.getBirthday();
							
						} else {
							System.out.println("---- Invalid Mum Key ----");
							jsonResponse.put("result", false);
							jsonResponse.put("message", "person with key ID ["+mumKey+"] does NOT exist");
							return jsonResponse;
						}
						
					}
					
					// Get Father Key
					int dadKey = (Integer) jsonObject.get("f");
					if (dadKey!=0) {
						Member dad = memberService.findById(dadKey);
						if (dad!=null) {
							if (dad.getGender()!="male") {
								System.out.println("---- Invalid Dad Gender ----");
								jsonResponse.put("result", false);
								jsonResponse.put("message", "person(dad) with key ID ["+dadKey+"] may have a wrong gender");
								return jsonResponse;
								
							}
							dadBirthday = dad.getBirthday();
							
						} else {
							System.out.println("---- Invalid Dad Key ----");
							jsonResponse.put("result", false);
							jsonResponse.put("message", "person with key ID ["+dadKey+"] does NOT exist");
							return jsonResponse;
						}
						
					}
					
					// Check birthday with parents
					if (mumBirthday!=0 && dadBirthday!=0) {
						
						if (!isBornAfterParents(birthday, mumBirthday, dadBirthday)){
							System.out.println("---- Invalid Birthday ----");
							jsonResponse.put("result", false);
							jsonResponse.put("message", "person may not be born before parents' birthday");
							return jsonResponse;
						}
						
					}
					
					Member member = new Member(keyId, name, birthday, gender, mumKey, dadKey);
					memberService.save(member);
					
					System.out.println("SAVED: "+member.toString());
					System.out.println("SUCCESS: The person input have been saved!");
					jsonResponse.put("result", true);
					return jsonResponse;
					
				// Else (multiple persons) - JSON Array	
				} else if (isJSONArray(memberInput)) {
					
					JSONArray jsonArray = (JSONArray) jsonParser.parse(memberInput);
					
					for (int i = 0; i < jsonArray.length(); i++) {
						
						Integer mumBirthday = 0;
						Integer dadBirthday = 0;
						
						jsonResponse = new JSONObject();
					    JSONObject extractedJsonObject = jsonArray.getJSONObject(i);
					    
					    // Get Key ID
						int keyId = (Integer) extractedJsonObject.get("key");
						if (memberService.findById(keyId)!=null) {
							System.out.println("---- Person ID exists ----");
							jsonResponse.put("result", false);
							jsonResponse.put("message", "person id ["+keyId+"] already exists");
							return jsonResponse;
							
						}
						
						// Get Name
						String name = (String) extractedJsonObject.get("name");
						if (memberRepository.findByName(name)!=null) {
							System.out.println("---- Person Name exists ----");
							jsonResponse.put("result", false);
							jsonResponse.put("message", "person name ["+name+"] already exists");
							return jsonResponse;
							
						}
						
						// Get Birthday
						Integer birthday = (Integer) extractedJsonObject.get("dob");
						if (!isValidDate(birthday)) {
							System.out.println("---- Invalid Birthday ----");
							jsonResponse.put("result", false);
							jsonResponse.put("message", "Date ["+birthday+"] is NOT valid");
							return jsonResponse;
							
						}
						
						// Get Gender
						String gender = (String) extractedJsonObject.get("g");				
							// Make the first letter to lower case
							gender = gender.substring(0, 1).toLowerCase()+gender.substring(1);		
						if (!(gender=="male")&&!(gender=="female")) {
							System.out.println("---- Invalid Gender ----");
							jsonResponse.put("result", false);
							jsonResponse.put("message", "gender ["+gender+"] is NOT valid");
							return jsonResponse;
							
						}
						
						// Get Mother Key
						int mumKey = (Integer) extractedJsonObject.get("m");
						if (mumKey!=0) {
							Member mum = memberService.findById(mumKey);
							if (mum!=null) {
								if (mum.getGender()!="female") {
									System.out.println("---- Invalid Mum Gender ----");
									jsonResponse.put("result", false);
									jsonResponse.put("message", "person(mum) with key ID ["+mumKey+"] may have a wrong gender");
									return jsonResponse;
									
								}
								mumBirthday = mum.getBirthday();
								
							} else {
								System.out.println("---- Invalid Mum Key ----");
								jsonResponse.put("result", false);
								jsonResponse.put("message", "person with key ID ["+mumKey+"] does NOT exist");
								return jsonResponse;
							}
							
						}
						
						// Get Father Key
						int dadKey = (Integer) extractedJsonObject.get("f");
						if (dadKey!=0) {
							Member dad = memberService.findById(dadKey);
							if (dad!=null) {
								if (dad.getGender()!="male") {
									System.out.println("---- Invalid Dad Gender ----");
									jsonResponse.put("result", false);
									jsonResponse.put("message", "person(dad) with key ID ["+dadKey+"] may have a wrong gender");
									return jsonResponse;
									
								}
								dadBirthday = dad.getBirthday();
								
							} else {
								System.out.println("---- Invalid Dad Key ----");
								jsonResponse.put("result", false);
								jsonResponse.put("message", "person with key ID ["+dadKey+"] does NOT exist");
								return jsonResponse;
							}
							
						}
						
						// Check birthday with parents
						if (mumBirthday!=0 && dadBirthday!=0) {
							
							if (!isBornAfterParents(birthday, mumBirthday, dadBirthday)){
								System.out.println("---- Invalid Birthday ----");
								jsonResponse.put("result", false);
								jsonResponse.put("message", "person may not be born before parents' birthday");
								return jsonResponse;
							}
							
						}
						
						Member member = new Member(keyId, name, birthday, gender, mumKey, dadKey);
						memberService.save(member);
						
						System.out.println("SAVED: "+member.toString());
	
					}
					System.out.println("SUCCESS: All the person input have been saved!");
					jsonResponse.put("result", true);
					return jsonResponse;
					
				} else {
					System.out.println("FAILED: Error occured!");
					jsonResponse.put("result", false);
					jsonResponse.put("message", "Error occured");
					return jsonResponse;
					
				}
				
			} else {
				System.out.println("FAILED: Invalid Input String!");
				jsonResponse.put("result", false);
				jsonResponse.put("message", "Cannot parse Input String into JSON, please check syntex");
				return jsonResponse;
				
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
		try {
			date = dateFormat.parse(dateInput.toString());
//			SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
//			String formatedDate = newFormat.format(date);	
			return true;
			
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			System.out.println("Invalid Date input");
			return false;
			
		}
		
	}
	
	public boolean isBornAfterParents (Integer dateInput, Integer mumBirthday, Integer dadBirthday) {
		Date birthday;
		Date mumbirthday;
		Date dadbirthday;
		
		try {
			birthday = dateFormat.parse(dateInput.toString());
			mumbirthday = dateFormat.parse(mumBirthday.toString());
			dadbirthday = dateFormat.parse(dadBirthday.toString());
			
			if (birthday.after(mumbirthday) && birthday.after(dadbirthday)) {
				return true;
			} else {
				return false;
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
			jsonResponse.put("result", false);
			jsonResponse.put("message", "person id ["+id+"] already exists");
			return false;
			
		}
		
		// Check Name
		if (memberRepository.findByName(name)!=null) {
			System.out.println("---- Person Name exists ----");
			jsonResponse.put("result", false);
			jsonResponse.put("message", "person name ["+name+"] already exists");
			return false;
			
		}
		
		// Check Birthday
		if (!isValidDate(birthday)) {
			System.out.println("---- Invalid Birthday ----");
			jsonResponse.put("result", false);
			jsonResponse.put("message", "Date ["+birthday+"] is NOT valid");
			return false;
			
		}
		
		// Check Gender	
		if (!(gender=="male")&&!(gender=="female")) {
			System.out.println("---- Invalid Gender ----");
			jsonResponse.put("result", false);
			jsonResponse.put("message", "gender ["+gender+"] is NOT valid");
			return false;
			
		}
		
		// Check Mother Key
		if (mumKey!=0) {
			Member mum = memberService.findById(mumKey);
			if (mum!=null) {
				if (mum.getGender()!="female") {
					System.out.println("---- Invalid Mum Gender ----");
					jsonResponse.put("result", false);
					jsonResponse.put("message", "person(mum) with key ID ["+mumKey+"] may have a wrong gender");
					return false;
					
				}
				mumBirthday = mum.getBirthday();
				
			} else {
				System.out.println("---- Invalid Mum Key ----");
				jsonResponse.put("result", false);
				jsonResponse.put("message", "person with key ID ["+mumKey+"] does NOT exist");
				return false;
			}
			
		}
		
		// Check Father Key
		if (dadKey!=0) {
			Member dad = memberService.findById(dadKey);
			if (dad!=null) {
				if (dad.getGender()!="male") {
					System.out.println("---- Invalid Dad Gender ----");
					jsonResponse.put("result", false);
					jsonResponse.put("message", "person(dad) with key ID ["+dadKey+"] may have a wrong gender");
					return false;
					
				}
				dadBirthday = dad.getBirthday();
				
			} else {
				System.out.println("---- Invalid Dad Key ----");
				jsonResponse.put("result", false);
				jsonResponse.put("message", "person with key ID ["+dadKey+"] does NOT exist");
				return false;
			}
			
		}
		
		// Check birthday with parents
		if (mumBirthday!=0 && dadBirthday!=0) {
			
			if (!isBornAfterParents(birthday, mumBirthday, dadBirthday)){
				System.out.println("---- Invalid Birthday ----");
				jsonResponse.put("result", false);
				jsonResponse.put("message", "person may not be born before parents' birthday");
				return false;
			}
			
		}
		return true;
	}

	
	//(b) Deleting a person
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET) 
	@ResponseBody
	public Object deleteMember(@PathVariable Integer id ) {
		JSONObject jsonResponse = new JSONObject();
		Member member = memberService.findById(id);
		
		if (member!=null) {
			memberService.deleteById(id);
			jsonResponse.put("result", true);
			
		} else {
			jsonResponse.put("result", false);
			jsonResponse.put("message", "person id ["+id+"] can NOT be found");
			return jsonResponse;
			
		}
		return jsonResponse;
		
	}
	
	//(c) Getting information about a specific person
	@RequestMapping(value="/get/{id}", method=RequestMethod.GET)
	@ResponseBody
	public String getMemberInfo(@PathVariable Integer id) {
		Member member = memberService.findById(id);
		
		return "member";
	}
	
	//(d) Finding someones ancestors
	@RequestMapping(value="/ancestors/{id}", method=RequestMethod.GET)
	@ResponseBody
	public String getMemberAncestors(@PathVariable Integer id) {
		Member member = memberService.findById(id);
		member.getMumKey();
		
		return "redirect:/GE/person";
	}
	
	//(e) Finding someones descendants
	@RequestMapping(value="/descendants/{id}")
	@ResponseBody
	public String getMemberDescendants(@PathVariable Integer id) {
		Member member = memberService.findById(id);
		member.getId();
		
		return "redirect:/GE/person";
		
	}
	
}
