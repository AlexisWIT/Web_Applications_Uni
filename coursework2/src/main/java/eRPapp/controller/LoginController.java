package eRPapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eRPapp.BallotApplication;
import eRPapp.repository.*;

@Controller
//@RequestMapping("/login")
public class LoginController {
	
	@Autowired UserRepository userRepository;
	@Autowired eRPapp.domain.User user;
	
//	@Autowired
//	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
//	@InitBinder
//	protected void initBinder(WebDataBinder binder) {
//		binder.addValidators(new LoginValidator());
//	}
	
	
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
		
		System.out.println("----- User ["+userForCheck.getUsername()+"] logged in -----");
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
	
	@RequestMapping(value="/credentialCheck", method=RequestMethod.GET)
	@ResponseBody
	public Response credentialCheck(@ModelAttribute("user") eRPapp.domain.User user, BindingResult credentialCheckResult) {
		
		System.out.println("--- Received request for checking user credential ---");
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Response response = new Response();
		String emailForCheck = user.getEmail();
		String passwordForCheck = user.getPassword();
		eRPapp.domain.User userInDB = userRepository.findByEmail(emailForCheck);
		
		// If found user in database, then email must be correct.
		if (userInDB != null) {
			
			System.out.println("--- Found record of user ["+emailForCheck+"] in database");
			
			//String encryptedPasswordForCheck = passwordEncoder.encode(passwordForCheck);
			// If passwordInput does NOT match the password in Database
			if (!passwordEncoder.matches(passwordForCheck, userInDB.getPassword())) {
				System.out.println("--- User ["+emailForCheck+"] input wrong password");
				credentialCheckResult.reject("ERROR_INCORRECT_PASSWORD");
				response.setStatus("FAILED");
				
			} else {
				// All correct
				response.setStatus("CHECKED");
				
			}

			
		// Else, email is incorrect
		} else {
			System.out.println("--- User ["+emailForCheck+"] does NOT exist");
			credentialCheckResult.reject("ERROR_INCORRECT_EMAIL");
			response.setStatus("FAILED");
			
		}
		
		if (credentialCheckResult.hasErrors()) {
			response.setResult(credentialCheckResult.getAllErrors());
			
		}
		
		return response;
		
	}

}
