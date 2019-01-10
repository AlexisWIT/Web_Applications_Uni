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
				
				System.out.println("Invalid Date format input");
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

}
