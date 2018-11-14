package eRPapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eRPapp.BallotApplication;
import eRPapp.domain.User;
import eRPapp.repository.*;

@Controller
//@RequestMapping("/login")
public class LoginController {
	
	@Autowired UserRepository userRepository;
	@Autowired User user;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new LoginValidator());
	}
	
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index() {
		return "Login";
	}
	
	@RequestMapping(value = "/userLogin", method = RequestMethod.GET)
    public String login() {
    	return "Login";
    }
	
	@RequestMapping(value="/accessCheck", method=RequestMethod.POST)
	public String accountCheck(@Valid @ModelAttribute("appDataTransferObject") AppDataTransferObject appDataTransferObject, Model model) {
		if (userRepository.findByEmail(appDataTransferObject.getEmail()) != null) {
			if (userRepository.findByEmail(appDataTransferObject.getEmail()).getPassword().equals(PasswordEncryptor.getSHA256(appDataTransferObject.getPassword()))){			
				String view;
				user = userRepository.findByEmail(appDataTransferObject.getEmail());
				switch (user.getAccountType().getId()) {
				case BallotApplication.COMMISSION:
					view = "redirect:/adminLoggedIn";
					break;
				case BallotApplication.VOTER:
					view = "redirect:/voterLoggedIn";	
					break;
				default:
					view = "redirect:/voterLoggedIn";	
					break;				
				}
				return view;
				
			}
			return "Login";
			
		}
		return "Login";
	}
	
	@RequestMapping("/adminLoggedIn")
	public String commission() {
		return "Dashboard";
	}
	
	@RequestMapping("/voterLoggedIn")
	public String voter() {
		return "redirect:/home/";
	}
	
//	@RequestMapping(value="/logged-in", method=RequestMethod.GET)
//	public String accountTypeCheck() {
//		User userChecked = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		userChecked.getAuthorities()
//	}
	
	@RequestMapping("/userLogout") 
	public String logout() {
		return "Login";
		
	}
	
	@RequestMapping("/accessDenied")
	public String accessDenied() {
		return "Login";
	}

}
