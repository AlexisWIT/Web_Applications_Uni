package eRPapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eRPapp.BallotApplication;
import eRPapp.repository.*;

@Controller
//@RequestMapping("/login")
public class LoginController {
	
	@Autowired UserRepository userRepository;
	@Autowired eRPapp.domain.User user;
	
//	@Autowired
//	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new LoginValidator());
	}
	
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index() {
		System.out.println("----- App Initialised -----");
		return "Login";
	}
	
	@RequestMapping(value = "/userLogin", method = RequestMethod.GET)
    public String login() {
    	return "Login";
    }

//For no security only:	
//	@RequestMapping(value="/accessCheck", method=RequestMethod.POST)
//	public String accountCheck(@Valid @ModelAttribute("appDataTransferObject") AppDataTransferObject appDataTransferObject, Model model) {
//		  if (userRepository.findByEmail(appDataTransferObject.getEmail()) != null) {
//			  if (userRepository.findByEmail(appDataTransferObject.getEmail()).getPassword().equals(PasswordEncryptor.getSHA256(appDataTransferObject.getPassword()))){
//			  if (userRepository.findByEmail(appDataTransferObject.getEmail()).getPassword().equals(passwordEncoder.encode(appDataTransferObject.getPassword()))){
	
	@RequestMapping(value="/accessCheck", method=RequestMethod.GET)
	public String accountCheck() {
		User userForCheck = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();	
		userForCheck.getAuthorities().stream().forEach(c -> System.out.println (c));
		
		System.out.println("----- User "+ userForCheck.getUsername() +" logged in -----");
		String view;
		user = userRepository.findByEmail(userForCheck.getUsername());
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
	
	@RequestMapping("/adminLoggedIn")
	public String commission() {
		return "redirect:/dashboard/";
	}
	
	@RequestMapping("/voterLoggedIn")
	public String voter() {
		return "redirect:/home/";
	}
	
	@RequestMapping("/userLogout") 
	public String logout() {
		return "Login";
		
	}
	
	@RequestMapping("/accessDenied")
	public String accessDenied() {
		System.out.println("----------- Access Denied ---------------");
		return "Error_Access";
	}

}
