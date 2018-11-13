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
	
//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        binder.addValidators(new SignupValidator());
//    }
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String initModel(Model model) {
		AppDataTransferObject appDataTransferObject = new AppDataTransferObject();
    	model.addAttribute("appDataTransferObject", appDataTransferObject);
        return "Signup";
	}
	
	@RequestMapping(value="register", params="register", method=RequestMethod.POST)
	public String signup(@Valid @ModelAttribute("appDataTransferObject") AppDataTransferObject appDataTransferObject, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			if (userRepository.findByEmail(appDataTransferObject.getEmail()) != null) {
				appDataTransferObject.getUser().setUserRemark("Email exists, please try another one.");
				model.addAttribute("appDataTransferObject", appDataTransferObject);
				return "redirect:/signup";
				
			}
			User newVoter = new User();
			newVoter.setEmail(appDataTransferObject.getEmail());
			newVoter.setFamilyName(appDataTransferObject.getFamilyName());
			newVoter.setGivenName(appDataTransferObject.getGivenName());
			newVoter.setDateOfBirth(appDataTransferObject.getDateOfBirth());
			newVoter.setAddress(appDataTransferObject.getAddress());
			
			return "redirect:/userLogin";
			
		} else {
			return "redirect:/signup";
			
		}
	}
	
	@RequestMapping(value="/cancel", method=RequestMethod.POST)
	public String signupCancel(@ModelAttribute("appDataTransferObject")  AppDataTransferObject appDataTransferObject, Model model) {
		System.out.println(00000000000);
		return "redirect:/userLogin";
	}

}
