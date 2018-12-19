package gEapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gEapp.domain.Member;
import gEapp.repository.MemberRepository;

@Controller
@RequestMapping("/GE/person")
public class IndexController {
	@Autowired MemberRepository memberRepository;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index() {
		return "IndexPage";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String addMember(@RequestParam(value="key", required=true) int keyId,
							@RequestParam(value="name", required=true) String name,
							@RequestParam(value="m") int mumKey,
							@RequestParam(value="f") int dadKey,
							@RequestParam(value="dob") int birthday,
							@RequestParam(value="g") String gender) {
		
		Member member = new Member(keyId, name, mumKey, dadKey, birthday, gender);
		memberRepository.save(member);
		
		return "redirect:/GE";
	}

	//@RequestMapping(value="/")
}
