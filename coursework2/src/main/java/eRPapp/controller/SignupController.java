package eRPapp.controller;

import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
		System.out.println("-----------  Register started  ---------------");
        return "Signup";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String signup(@Valid @ModelAttribute("user") User user, Model model) {
		System.out.println("-----------  Processing register info  ---------------");
		if (userRepository.findByEmail(user.getEmail()) != null) {
			System.out.println("!!! Email exists !!!");
			user.setUserRemark("Email exists, please try another one.");
			return "redirect:/signup/";
			
		}
		// set account type "voter"
		user.setAccountType(accountTypeRepository.findById(1));
		
		// parse date of birth to Date format
		try {
			user.setDateOfBirth(user.toDate(user.getDateOfBirthString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(user.toString());
		
		// encrypt user password
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//user.setPassword(PasswordEncryptor.getSHA256(user.getPassword()));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		userRepository.save(user);
		System.out.println("-----------  User created  ---------------");
		return "redirect:/userLogin";	

	}
	
	@RequestMapping(value="/cancel", method=RequestMethod.POST)
	public String signupCancel(@ModelAttribute("user")  User user, Model model) {
		System.out.println("-------  Register cencelled  -------");
		return "redirect:/userLogin";
	}

}
