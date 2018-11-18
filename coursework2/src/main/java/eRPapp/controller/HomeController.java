package eRPapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import eRPapp.domain.*;
import eRPapp.repository.*;

@Controller
@RequestMapping("/home")
public class HomeController {
	
//	@InitBinder
//	protected void initBinder(WebDataBinder binder) {
//		binder.addValidators(new HomeValidator());
//	}
	@Autowired AccountTypeRepository accountTypeRepository;
	@Autowired BioIdCodeRepository bioIdCodeRepository;
	@Autowired OptionRepository optionRepository;
	@Autowired QuestionRepository questionRepository;
	@Autowired UserRepository userRepository;
	@Autowired VoteRepository voteRepository;
	
	@RequestMapping("/")
	public String home(Model model) {	
		// load user personal information in their home page
		String currentUserEmail = getCurrentUsername();
		System.out.println("User ["+currentUserEmail+"] logged into Home");
		
		User user = userRepository.findByEmail(currentUserEmail);
		List<User> userList = new ArrayList<User>();
		userList.add(user);
		
		model.addAttribute("userList", userList);
		
		// load user vote information in their home page
		if (user.getVote() != null) {
			Vote currentVote = user.getVote();
			Option currentOption = currentVote.getOption();
			List<Option> optionList = new ArrayList<Option>();
			optionList.add(currentOption);
			
			Question currentQuestion = currentOption.getQuestion();
			List<Question> questionList = new ArrayList<Question>();
			questionList.add(currentQuestion);
			
			model.addAttribute("optionList", optionList);
			model.addAttribute("questionList", questionList);
			
		}
		return "Home";
	}
	
	@RequestMapping("/vote")
	public String vote() {
		return "redirect:/vote/";
	}
	
	@RequestMapping("/logout")
	public String logout() {
		System.out.println("------ Voter logged out -----");
		return "redirect:/userLogout";
	}
	
	public String getCurrentUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String userName = authentication.getName();
		    return userName;
		} else {
			return "null";
		}
	}

}
