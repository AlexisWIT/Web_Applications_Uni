package gEapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String addMemberJSON() {
		
		return "redirect:/GE/person";
	}
	
	//(b) Deleting a person
	@RequestMapping(value="/delete/{id}") 
	public String deleteMember(@PathVariable Integer id ) {
		
		memberService.deleteById(id);
		return "redirect:/GE/person";
	}
	
	//(c) Getting information about a specific perso
	@RequestMapping(value="/get/{id}", method=RequestMethod.GET)
	public String getMemberInfo(@PathVariable Integer id) {
		Member member = memberService.findById(id);
		
		return "redirect:/GE/person";
	}
	
	//(d) Finding someone¡¯s ancestors
	@RequestMapping(value="/ancestors/{id}", method=RequestMethod.GET)
	public String getMemberAncestors(@PathVariable Integer id) {
		Member member = memberService.findById(id);
		member.getMumKey();
		
		return "redirect:/GE/person";
	}
	
	//(e) Finding someone¡¯s descendants
	@RequestMapping(value="/descendants/{id}")
	public String getMemberDescendants(@PathVariable Integer id) {
		Member member = memberService.findById(id);
		member.getId();
		
		return "redirect:/GE/person";
		
	}
	
}
