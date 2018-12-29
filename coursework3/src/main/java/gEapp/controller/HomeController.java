package gEapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@RequestMapping(value="/G", method=RequestMethod.GET)
	public String home() {
		System.out.println("sdsd");
		return "Home";
	}

}
