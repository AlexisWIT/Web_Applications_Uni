package gEapp.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

import gEapp.repository.MemberRepository;
import gEapp.service.MemberService;

public class InputChecker {
	
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
	
	public boolean isValidDate (Integer dateInput, SimpleDateFormat dateFormat) {
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
	
	public boolean isBornAfterParents (Integer dateInput, Integer mumBirthday, Integer dadBirthday, SimpleDateFormat dateFormat) {
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
	
//	public JSONOrderedObject checkValidity(Integer id, String name, Integer birthday, String gender, Integer mumKey,
//			Integer dadKey, SimpleDateFormat dateFormat) {
//		
//		Integer mumBirthday = 0;
//		Integer dadBirthday = 0;
//		
//		System.out.println("Received request for CHECK");
//		
//		JSONOrderedObject jsonResult = new JSONOrderedObject();
//		
//		System.out.println("Found: "+memberService.findById(id));
//
//		// jsonResponse = new JSONObject();
//
//		// Check Key ID
//		if (memberService.findById(id) != null) {
//			System.out.println("---- Person ID exists ----");
//			jsonResult.put("result", "false");
//			jsonResult.put("message", "person id [" + id + "] already exists");
//			return jsonResult;
//
//		}
//
//		// Check Name
//		if (memberRepository.findByName(name) != null) {
//			System.out.println("---- Person Name exists ----");
//			jsonResult.put("result", "false");
//			jsonResult.put("message", "person name [" + name + "] already exists");
//			return jsonResult;
//
//		}
//
//		// Check Birthday
//		if (!isValidDate(birthday, dateFormat)) {
//			System.out.println("---- Invalid Birthday ----");
//			jsonResult.put("result", "false");
//			jsonResult.put("message", "Date [" + birthday + "] is NOT valid");
//			return jsonResult;
//
//		}
//
//		// Check Gender
//		if (!StringUtils.isBlank(gender)) {
//
//			if (!(gender.equals("male")) && !(gender.equals("female"))) {
//
//				System.out.println("---- Invalid Gender ----");
//				jsonResult.put("result", "false");
//				jsonResult.put("message", "gender [" + gender + "] is NOT valid");
//				return jsonResult;
//
//			}
//
//		}
//
//		// Check Mother Key
//		if (mumKey != null) {
//			Member mum = memberService.findById(mumKey);
//			if (mum != null) {
//				if (mum.getGender() != "female" && mum.getGender() != null) {
//					System.out.println("---- Invalid Mum Gender ----");
//					jsonResult.put("result", "false");
//					jsonResult.put("message", "person(mum) with key ID [" + mumKey + "] may have a wrong gender");
//					return jsonResult;
//
//				}
//				mumBirthday = mum.getBirthday();
//
//			} else {
//				System.out.println("---- Invalid Mum Key ----");
//				jsonResult.put("result", "false");
//				jsonResult.put("message", "person(mum) with mumKey ID [" + mumKey + "] does NOT exist");
//				return jsonResult;
//			}
//
//		}
//
//		// Check Father Key
//		if (dadKey != null) {
//			Member dad = memberService.findById(dadKey);
//			if (dad != null) {
//				if (dad.getGender() != "male" && dad.getGender() != null) {
//					System.out.println("---- Invalid Dad Gender ----");
//					jsonResult.put("result", "false");
//					jsonResult.put("message", "person(dad) with key ID [" + dadKey + "] may have a wrong gender");
//					return jsonResult;
//
//				}
//				dadBirthday = dad.getBirthday();
//
//			} else {
//				System.out.println("---- Invalid Dad Key ----");
//				jsonResult.put("result", "false");
//				jsonResult.put("message", "person(dad) with dadKey ID [" + dadKey + "] does NOT exist");
//				return jsonResult;
//			}
//
//		}
//
//		// Check birthday with parents
//		if (mumBirthday != null || dadBirthday != null) {
//			if (birthday != null) {
//				if (!isBornAfterParents(birthday, mumBirthday, dadBirthday, dateFormat)) {
//					System.out.println("---- Invalid Birthday ----");
//					jsonResult.put("result", "false");
//					jsonResult.put("message", "person may not be born before parents' birthday");
//					return jsonResult;
//
//				}
//
//			}
//
//		}
//		System.out.println("---- Checked OK ----");
//		jsonResult.put("result", "true");
//		return jsonResult;
//		
//	}

}
