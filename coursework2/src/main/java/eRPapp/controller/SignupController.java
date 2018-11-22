package eRPapp.controller;

import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eRPapp.domain.BioIdCode;
import eRPapp.domain.User;
import eRPapp.repository.*;

//import eRPapp.controller.SignupValidator;

@Controller
@RequestMapping("/signup")
public class SignupController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	AccountTypeRepository accountTypeRepository;
	@Autowired
	BioIdCodeRepository bioIdCodeRepository;

//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        binder.addValidators(new SignupValidator());
//    }

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String initModel(@ModelAttribute("user") User user, Model model) {
		System.out.println("-----------  Register started  ---------------");
		return "Signup";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String signup(@Valid @ModelAttribute("user") User user, Model model) {
		System.out.println("-----------  Processing register info  ---------------");
		if (userRepository.findByEmail(user.getEmail()) != null) {
			System.out.println("!!! Email exists !!!");
			return "redirect:/signup/";
		}
		// set available BIC code to user, and set usage to 1 in domain User class use
		// 'setter' method
		BioIdCode bic = bioIdCodeRepository.findByBioIdCode(user.getBioIdCodeString());
		user.setBioIdCode(bic);

		// set account type "voter" to user
		user.setAccountType(accountTypeRepository.findById(1));

		// set date of birth to user, parse date of birth to Date format
		try {
			user.setDateOfBirth(user.toDate(user.getDateOfBirthString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(user.toString());

		// encrypt user password
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		// user.setPassword(PasswordEncryptor.getSHA256(user.getPassword()));
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		userRepository.save(user);
		bioIdCodeRepository.save(bic);

		System.out.println("-----------  User created  ---------------");
		return "redirect:/userLogin";

	}

	@RequestMapping(value = "/emailCheck", method = RequestMethod.POST)
	@ResponseBody
	public String emailCheck(@ModelAttribute("user") User user, BindingResult emailCheckResult) {
		String emailReport = "";
		String emailCheckResultErrorInfo = "";
		String emailForCheck = user.getEmail();

		System.out.println("---- Checking Email [" + emailForCheck + "] with database ----");

		// check input email with database record if already in use
		User userHasEmail = userRepository.findByEmail(emailForCheck);
		if (userHasEmail != null) {
			System.out.println("---- Email [" + emailForCheck + "] already in use ----");
			emailCheckResult.reject("EMAIL_IN_USE");
			emailCheckResultErrorInfo = "Email already in use!";
		}
		if (!emailCheckResult.hasErrors()) {
			emailReport = "<span id='emailOk' class='ok'>OK</span>";
		} else {
			emailReport = "<span class='error'>" + emailCheckResultErrorInfo + "</span>";
		}

		System.out.println("---- Sent emailReport ----\n");
		return emailReport;

	}

	@RequestMapping(value = "/bicCheck", method = RequestMethod.POST)
	@ResponseBody
	public String bicCheck(@ModelAttribute("user") User user, BindingResult bicCheckResult) {
		String bicReport = "";
		String bicCheckResultErrorInfo = "";
		String bicForCheck = user.getBioIdCodeString();

		System.out.println("---- Checking BIC [" + bicForCheck + "] with database ----");

		// check input BIC with database record if already in use
		if (bioIdCodeRepository.findByBioIdCode(bicForCheck) != null) { // BIC input match with any in database

			System.out.println("---- Found BIC [" + bicForCheck + "] in database ----");
			BioIdCode bicInUse = bioIdCodeRepository.findByBioIdCode(bicForCheck);
			int usage = bicInUse.getUsage();

			if (usage != 0) {
				System.out.println("---- BIC [" + bicForCheck + "] in use ----");
				bicCheckResult.reject("BIC_IN_USE");
				bicCheckResultErrorInfo = "BIC already in use!";
			}

		} else { // BIC not match with any in database
			System.out.println("---- BIC [" + bicForCheck + "] not found ----");
			bicCheckResult.reject("BIC_NOT_FOUND");
			bicCheckResultErrorInfo = "BIC not found in database!";
		}

		if (!bicCheckResult.hasErrors()) {
			bicReport = "<span id='bicOk' class='ok'>OK</span>";
		} else {
			bicReport = "<span class='error'>" + bicCheckResultErrorInfo + "</span>";
		}

		System.out.println("---- Sent bicReport ----");
		return bicReport;

	}

	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public String signupCancel(@ModelAttribute("user") User user, Model model) {
		System.out.println("-------  Register cencelled  -------");
		return "redirect:/userLogin";
	}

}
