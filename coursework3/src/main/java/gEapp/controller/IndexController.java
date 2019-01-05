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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import gEapp.domain.InputChecker;
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

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	JSONObject jsonResponse = new JSONObject();

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		System.out.println("IndexPage");
		return "IndexPage";
	}

//	@RequestMapping(value="/", method=RequestMethod.GET)
//	public String index2() {
//		System.out.println("IndexPage");
//		return "redirect:/GE/person";
//	}

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
		
//		JSONOrderedObject jsonCheckResult = new JSONOrderedObject();
//		System.out.println("Checking...");
//		System.out.println(inputChecker.checkValidity(keyId, name, birthday, gender, mumKey, dadKey, dateFormat).toString());
//		if (!jsonCheckResult.containsValue("true")) {
//			System.out.println("Person [" + keyId + "] input is NOT VALID");
//			return jsonCheckResult.toLinkedHashMap();
//		}
			
		Member member = new Member(keyId, name, birthday, gender, mumKey, dadKey);
		memberService.save(member);

		System.out.println("SAVED: " + member.toString());
		System.out.println("SUCCESS: The person input have been saved!");
		jsonResponseAdd.put("result", "true");
		return jsonResponseAdd.toMap();
	}

	@RequestMapping(value = "/GE/person/addJSON", method = RequestMethod.POST)
	@ResponseBody
	public Object addMemberJSON(@RequestBody String memberInput) throws ParseException {

		final Gson gson = new Gson();
		// final ObjectMapper objectMapper = new ObjectMapper();
		JSONParser jsonParser = new JSONParser();
		// JSONObject jsonResponse = new JSONObject();

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

				// If only one person to be added - JSON Object
				if (inputChecker.isJSONObject(memberInput)) {

					jsonResponse = new JSONObject();
					JSONObject jsonObject = (JSONObject) jsonParser.parse(memberInput);

					int keyId = (Integer) jsonObject.get("key");
					String name = (String) jsonObject.get("name");
					Integer birthday = (Integer) jsonObject.get("dob");
					String gender = (String) jsonObject.get("g");
					// Make the first letter to lower case
					if (gender != null) {
						gender = gender.substring(0, 1).toLowerCase() + gender.substring(1);
					}

					Integer mumKey = (Integer) jsonObject.get("m");
					Integer dadKey = (Integer) jsonObject.get("f");

//					JSONOrderedObject jsonCheckResult = new JSONOrderedObject();
//					jsonCheckResult = (JSONOrderedObject) inputChecker.checkValidity(keyId, name, birthday, gender, mumKey, dadKey, dateFormat);
//					if (!jsonCheckResult.containsValue("true")) {
//						System.out.println("Person [" + keyId + "] input is NOT VALID");
//						return jsonCheckResult.toLinkedHashMap();
//					}

					Member member = new Member(keyId, name, birthday, gender, mumKey, dadKey);
					memberService.save(member);

					System.out.println("SAVED: " + member.toString());
					System.out.println("SUCCESS: The person input have been saved!");
					jsonResponse.put("result", "true");
					return jsonResponse.toMap();

					// Else (multiple persons) - JSON Array
				} else if (inputChecker.isJSONArray(memberInput)) {

					JSONArray jsonArray = (JSONArray) jsonParser.parse(memberInput);

					for (int i = 0; i < jsonArray.length(); i++) {

						jsonResponse = new JSONObject();
						JSONObject extractedJsonObject = jsonArray.getJSONObject(i);

						int keyId = (Integer) extractedJsonObject.get("key");
						String name = (String) extractedJsonObject.get("name");
						Integer birthday = (Integer) extractedJsonObject.get("dob");
						String gender = (String) extractedJsonObject.get("g");
						// Make the first letter to lower case
						if (gender != null) {
							gender = gender.substring(0, 1).toLowerCase() + gender.substring(1);
						}

						Integer mumKey = (Integer) extractedJsonObject.get("m");
						Integer dadKey = (Integer) extractedJsonObject.get("f");

//						JSONOrderedObject jsonCheckResult = new JSONOrderedObject();
//						jsonCheckResult = (JSONOrderedObject) inputChecker.checkValidity(keyId, name, birthday, gender, mumKey, dadKey, dateFormat);
//						if (!jsonCheckResult.containsValue("true")) {
//							System.out.println("Person [" + keyId + "] input is NOT VALID");
//							return jsonCheckResult.toLinkedHashMap();
//						}

						Member member = new Member(keyId, name, birthday, gender, mumKey, dadKey);
						memberService.save(member);

						System.out.println("SAVED: " + member.toString());

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

	// (b) Deleting a person
	@RequestMapping(value = "/GE/person/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Object deleteMember(@PathVariable Integer id) {
		JSONObject jsonResponseDelete = new JSONObject();
		Member member = memberService.findById(id);

		if (member != null) {
			memberService.deleteById(id);
			jsonResponseDelete.put("result", "true");

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
		JSONObject jsonResponseMemberInfo = new JSONObject();
		Member member = memberService.findById(id);
		if (member != null) {
			return member;

		} else {
			jsonResponseMemberInfo.put("result", "false");
			jsonResponseMemberInfo.put("message", "person with id [" + id + "] does NOT exist");
			return jsonResponseMemberInfo.toMap();

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

	public Object getParentsList(Integer keyId) {
		Integer mumKey = null;
		Integer dadKey = null;
		// JSONOrderedObject jsonResponseParents = new JSONOrderedObject();
		Map<String, Object> parentsMap = new LinkedHashMap();
		Member member = memberService.findById(keyId);

		if (member.getMumKey() != null) {
			mumKey = member.getMumKey();
			System.out.println("Found Mum of " + member.getName() + " [" + member.getId() + "] - "
					+ memberService.findById(mumKey).getName() + " [" + mumKey + "]");
			parentsMap.put("m", getMemberAncestors(mumKey));
		}

		if (member.getDadKey() != null) {
			dadKey = member.getDadKey();
			System.out.println("Found Dad of " + member.getName() + " [" + member.getId() + "] - "
					+ memberService.findById(dadKey).getName() + " [" + dadKey + "]");
			parentsMap.put("f", getMemberAncestors(dadKey));
		}

		return parentsMap;
	}

	// (e) Finding someones descendants
	@RequestMapping(value = "/GE/person/descendants/{id}")
	@ResponseBody
	public Object getMemberDescendants(@PathVariable Integer id) {

		// Map<String, Object> jsonResponseDescendant = new LinkedHashMap<>();
		JSONOrderedObject jsonResponseDescendant = new JSONOrderedObject();

		Integer currentPersonId = id;

		if (memberService.findById(currentPersonId) != null) {
			Member member = memberService.findById(currentPersonId);
			System.out.println("Start searching children for: " + member.getName() + " [" + currentPersonId + "]");

			@SuppressWarnings("unchecked")
			Collection<Member> memberInDatabase = makeCollection(memberService.findAllIter());
			int totalMember = memberInDatabase.size();

			// List<Object> jsonChildrenArray = new ArrayList();
			JSONArray jsonChildrenArray = new JSONArray();

			for (Member memberForCheck : memberInDatabase) {
				if ((memberForCheck.getMumKey() != null && memberForCheck.getMumKey() == currentPersonId)
						|| (memberForCheck.getDadKey() != null && memberForCheck.getDadKey() == currentPersonId)) {

					Integer childMemberKey = memberForCheck.getId();
					System.out.println("(1) Get Child For: " + memberService.findById(currentPersonId).getName() + " ["
							+ currentPersonId + "] - " + memberForCheck.getName() + " [" + childMemberKey + "]");
					// jsonChildrenArray.add(getChildrenList(childMemberKey));
					jsonChildrenArray.put(getChildrenList(childMemberKey));

				}

			}
			System.out.println("(No more children found for: " + member.getName() + " [" + currentPersonId + "])");
			jsonResponseDescendant.put("key", currentPersonId.toString());

			// if (jsonChildrenArray.size()!=0) {
			if (jsonChildrenArray.toList().size() != 0) {
				jsonResponseDescendant.put("children", jsonChildrenArray);
				System.out.println("(1) Children found: " + jsonChildrenArray.toString());
			}

			// return jsonResponseDescendant;
			return jsonResponseDescendant.toLinkedHashMap();

		} else {
			jsonResponseDescendant.put("result", "false");
			jsonResponseDescendant.put("message", "person with id [" + currentPersonId + "] does NOT exist");
			// return jsonResponseDescendant;
			return jsonResponseDescendant.toLinkedHashMap();

		}

	}

	public Object getChildrenList(Integer id) {

		Map<String, Object> jsonChildren = new LinkedHashMap<>();
		// JSONOrderedObject jsonChildren = new JSONOrderedObject();

		@SuppressWarnings("unchecked")
		Collection<Member> memberInDatabase = makeCollection(memberService.findAllIter());

		System.out.println("Start searching children for: " + memberService.findById(id).getName() + " [" + id + "]");

		List<Object> jsonChildrenArray = new ArrayList();
		// JSONArray jsonChildrenArray = new JSONArray();

		for (Member memberForCheck : memberInDatabase) {
			if ((memberForCheck.getMumKey() != null && memberForCheck.getMumKey() == id)
					|| (memberForCheck.getDadKey() != null && memberForCheck.getDadKey() == id)) {

				Integer childMemberKey = memberForCheck.getId();
				System.out.println("(2) Get Child For: " + memberService.findById(id).getName() + " [" + id + "] - "
						+ memberForCheck.getName() + " [" + childMemberKey + "]");
				jsonChildrenArray.add(getMemberDescendants(childMemberKey));
				// jsonChildrenArray.put(getMemberDescendants(childMemberKey));

			}

		}
		jsonChildren.put("key", id.toString());

		if (jsonChildrenArray.size() != 0) {
			// if (jsonChildrenArray.toList().size()!=0) {
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

}
