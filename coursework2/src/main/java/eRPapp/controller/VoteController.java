package eRPapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eRPapp.domain.*;
import eRPapp.repository.*;

@Controller
@RequestMapping("/vote")
public class VoteController {
	
	@Autowired QuestionRepository questionRepository;
	@Autowired OptionRepository optionRepository;
	@Autowired UserRepository userRepository;
	@Autowired VoteRepository voteRepository;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String vote(@ModelAttribute("option") Option option, Model model) {
		
		// load user personal information in their home page
		String currentUserEmail = getCurrentUserEmail();
		System.out.println("User ["+currentUserEmail+"] logged into Vote Page");
		User user = userRepository.findByEmail(currentUserEmail);
		List<User> userList = new ArrayList<User>();
		userList.add(user);
		
		model.addAttribute("userList", userList);
		
		model.addAttribute("question", new Question());
		//load questions and corresponding options
		model.addAttribute("questionList", (List<Question>) questionRepository.findAll());
		model.addAttribute("optionList", (List<Option>) optionRepository.findAll());
		return "Vote";
	}
	
	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public String confirmVote(@ModelAttribute("option") Option option, Model model) {
		String currentUserEmail = getCurrentUserEmail();
		int votedOptionId = option.getId();
		Option votedOption = optionRepository.findById(votedOptionId);
		User user = userRepository.findByEmail(currentUserEmail);
		Question currentQuestion = votedOption.getQuestion();
		int voteStatus = currentQuestion.getStatus();
		
		// If user has made vote
		if (user.getVote() != null) {
			System.out.println("--- Unable to vote, user has made a vote before ---");
			return "redirect:/vote/voteFailed";
			
		} else if (voteStatus==0) {
			System.out.println("--- Unable to vote, Admin has closed vote ---");
			return "redirect:/vote/voteFailed";
			
		} else {
			System.out.println("---- Processing vote ----");
			votedOption.setCount(1);
			System.out.println("---- Option ["+votedOptionId+"] vote count: "+votedOption.getCount()+" ----");
			
			Vote vote = new Vote(user, votedOption);
			user.setVote(vote);
			user.setUserRemark("VOTED");
			optionRepository.save(votedOption);
			userRepository.save(user);
			voteRepository.save(vote);
			System.out.println("---- Processing OK ----");
			
			return "redirect:/home/";
		}
		
	}
	
	@RequestMapping("/voteFailed")
	public String voteFailed() {
		
		
		return "redirect:/home/";
	}
	
	public String getCurrentUserEmail() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String userName = authentication.getName();
		    return userName;
		} else {
			return "null";
		}
	}

}
