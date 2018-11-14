package eRPapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import eRPapp.domain.User;

@Controller
@RequestMapping("/home")
public class HomeController {
	
//	@InitBinder
//	protected void initBinder(WebDataBinder binder) {
//		binder.addValidators(new HomeValidator());
//	}
	
	@RequestMapping("/")
	public String home(@ModelAttribute("user")  User user, Model model) {
		return "Home";
	}
	
	@RequestMapping("/vote")
	public String vote() {
		return "redirect:/vote/";
	}
	
	@RequestMapping("/logout")
	public String logout() {
		System.out.println("------ Voter logged out -----");
		return "redirect:/userLogin";
	}

}
