package gEapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/GE")
public class IndexController {
	
	//@RequestMapping(value="/", method=RequestMethod.GET)
	public String index() {
		return "IndexPage";
	}
	
	@RequestMapping(value="/add")
	public String addMember(@RequestParam(value="key", required=true) int id,
							@RequestParam(value="name", required=true) String name,
							@RequestParam(value="mumKey") int mumKey,
							@RequestParam(value="dadKey") int dadKey,
							@RequestParam(value="birthday") int birthday,
							@RequestParam(value="gender") String gender) {
		
		return "redirect:/GE";
	}

}
