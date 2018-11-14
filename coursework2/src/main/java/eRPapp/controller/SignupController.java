package eRPapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eRPapp.domain.User;
import eRPapp.repository.*;

//import eRPapp.controller.SignupValidator;

@Controller
@RequestMapping("/signup")
public class SignupController {
	
	@Autowired UserRepository userRepository;
	@Autowired AccountTypeRepository accountTypeRepository;
	
//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        binder.addValidators(new SignupValidator());
//    }
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String initModel(@ModelAttribute("user") User user, Model model) {
		System.out.println("Register started");
        return "Signup";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String signup(@Valid @ModelAttribute("user") User user, Model model) {
		System.out.println(0);
		

		if (userRepository.findByEmail(user.getEmail()) != null) {
			System.out.println(05);
			
			user.setUserRemark("Email exists, please try another one.");
			return "redirect:/signup/";
			
		}
		// set account type "voter"
		user.setAccountType(accountTypeRepository.findById(1));
		System.out.println(user.toString());
		
		// encrypt user password
		PasswordEncryptor passwordEncryptor = new PasswordEncryptor();
		user.setPassword(passwordEncryptor.getSHA256(user.getPassword()));
		
		userRepository.save(user);
		
		return "redirect:/userLogin";	

	}
	
	@RequestMapping(value="/cancel", method=RequestMethod.POST)
	public String signupCancel(@ModelAttribute("user")  User user, Model model) {
		System.out.println("Register cencelled");
		return "redirect:/userLogin";
	}

}
