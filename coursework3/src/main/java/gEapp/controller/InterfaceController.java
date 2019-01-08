package gEapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gEapp.domain.JSONOrderedObject;
import gEapp.domain.JSONResponse;
import gEapp.domain.Member;
import gEapp.service.MemberService;

@Controller
public class InterfaceController {
	
	@Autowired MemberService memberService;
	
	@RequestMapping(value="/GE/FamilyTree", method=RequestMethod.GET)
	public @ResponseBody JSONResponse getFamilyTree() {
		
		JSONResponse familyTreeResponse = new JSONResponse();
		List<Member> listForTree = new ArrayList<>();
		List<Member> members = (List<Member>) memberService.findAllMembers();
		
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
				
			} else if (dadKey==null && mumKey!=null) { // Mum-only family
				Member femaleSpouse = memberService.findById(mumKey);
				femaleSpouse.setSpouseId(0);
				memberService.save(femaleSpouse);
				
			} else if (dadKey!=null && mumKey==null) { // Dad-only family
				Member maleSpouse = memberService.findById(dadKey);
				maleSpouse.setSpouseId(0);
				memberService.save(maleSpouse);
			}
		}
		
		listForTree = (List<Member>) memberService.findAllMembers();
		
		familyTreeResponse.setMemberList(listForTree);
		return familyTreeResponse;
//		return formatList(listForTree);
	}

}
