package gEapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

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
	public String addMemberJSON(@RequestBody String memberInput) throws ParseException {
		
		final Gson gson = new Gson();
		final ObjectMapper objectMapper = new ObjectMapper();
		JSONParser jsonParser = new JSONParser();
		
		// Check if it's empty/blank string
		if (StringUtils.isBlank(memberInput)) {
			System.out.println("String entered is blank");
			
		} else {
			// Check if it's valid JSON input
			if (isValidJSON(memberInput, gson, objectMapper)) {
				
				// If only one person to be added - JSON Object
				if (isJSONObject(memberInput)) {
					
					JSONObject jsonObject = (JSONObject) jsonParser.parse(memberInput);
					
					// Get Key ID
					int keyId = (Integer) jsonObject.get("key");
					// Get Name
					String name = (String) jsonObject.get("name");
					// Get Birthday
					int birthday = (Integer) jsonObject.get("dob");
					// Get Gender
					String gender = (String) jsonObject.get("g");
					// Get Mother Key
					int mumKey = (Integer) jsonObject.get("m");
					// Get Father Key
					int dadKey = (Integer) jsonObject.get("f");
					
					Member member = new Member(keyId, name, birthday, gender, mumKey, dadKey);
					memberService.save(member);
					
					System.out.println("SAVED: "+member.toString());
					System.out.println("SUCCESS: The person input have been saved!");
					
				// Else (multiple persons) - JSON Array	
				} else if (isJSONArray(memberInput)) {
					
					JSONArray jsonArray = (JSONArray) jsonParser.parse(memberInput);
					for (int i = 0; i < jsonArray.length(); i++) {
					    JSONObject extractedJsonObject = jsonArray.getJSONObject(i);
					    
					    // Get Key ID
						int keyId = (Integer) extractedJsonObject.get("key");
						// Get Name
						String name = (String) extractedJsonObject.get("name");
						// Get Birthday
						int birthday = (Integer) extractedJsonObject.get("dob");
						// Get Gender
						String gender = (String) extractedJsonObject.get("g");
						// Get Mother Key
						int mumKey = (Integer) extractedJsonObject.get("m");
						// Get Father Key
						int dadKey = (Integer) extractedJsonObject.get("f");
						
						Member member = new Member(keyId, name, birthday, gender, mumKey, dadKey);
						memberService.save(member);
						
						System.out.println("SAVED: "+member.toString());
	
					}
					System.out.println("SUCCESS: All the person input have been saved!");
					
				} else {
					System.out.println("FAILED: Error occured!");
					
				}
				
			}
			System.out.println("FAILED: Invalid Input String!");
			
		}
		return "redirect:/GE/person";
		
	}
	
	// Check if String input is valid for parsing to JSON
	public boolean isValidJSON (String inputString, Gson gson, ObjectMapper mapper) {
		try {
			gson.fromJson(inputString, Object.class);
			mapper.readTree(inputString);			
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
	
	
	
	
	
	//(b) Deleting a person
	@RequestMapping(value="/delete/{id}") 
	@ResponseBody
	public Object deleteMember(@PathVariable Integer id ) {
		
		memberService.deleteById(id);
		return "redirect:/GE/person";
	}
	
	//(c) Getting information about a specific person
	@RequestMapping(value="/get/{id}", method=RequestMethod.GET)
	@ResponseBody
	public String getMemberInfo(@PathVariable Integer id) {
		Member member = memberService.findById(id);
		
		return "member";
	}
	
	//(d) Finding someone¡¯s ancestors
	@RequestMapping(value="/ancestors/{id}", method=RequestMethod.GET)
	@ResponseBody
	public String getMemberAncestors(@PathVariable Integer id) {
		Member member = memberService.findById(id);
		member.getMumKey();
		
		return "redirect:/GE/person";
	}
	
	//(e) Finding someone¡¯s descendants
	@RequestMapping(value="/descendants/{id}")
	@ResponseBody
	public String getMemberDescendants(@PathVariable Integer id) {
		Member member = memberService.findById(id);
		member.getId();
		
		return "redirect:/GE/person";
		
	}
	
}
