package eRPapp.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eRPapp.domain.*;
import eRPapp.repository.*;

@Controller
@RequestMapping("/dashboard")
public class AdminController {
	
	@Autowired QuestionRepository questionRepository;
	@Autowired OptionRepository optionRepository;
	@Autowired UserRepository userRepository;
	@Autowired VoteRepository voteRepository;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String initAdmin(@ModelAttribute("questions") Question question, Model model) {
		
		model.addAttribute("options", new Option());
		//load questions and corresponding options
		model.addAttribute("questionList", (List<Question>) questionRepository.findAll());
		model.addAttribute("optionList", (List<Option>) optionRepository.findAll());
		return "Dashboard";
	}
	
	@RequestMapping("/logout")
	public String adminLogout() {
		System.out.println("------ Admin logged out -----");
		return "redirect:/userLogout";
	}
	
	@RequestMapping(value="/changeVoteStatus", method = RequestMethod.POST)
	@ResponseBody
	public String changeVoteStatus(@ModelAttribute("questions") Question question, BindingResult changeStatusResult) {
		String changeVoteStatusReport = "";
		int currentQuestionId = question.getRefId();
		double voteRateLimit = 0.8;
		
		Question currentQuestion = questionRepository.findByRefId(currentQuestionId);
		
		if (currentQuestion != null) {// If found question record in database
			
			if (currentQuestion.getStatus()==0) { // If vote is closed
				currentQuestion.setStatus(1);// Open the vote
				questionRepository.save(currentQuestion);
				changeVoteStatusReport = "OPENED";
				System.out.println("--- Vote opened successfully ---");
				
			} else if (currentQuestion.getStatus()==1) { // If vote is open
				
				Collection<User> regUser = makeCollection(userRepository.findAll());
				int registeredUser = regUser.size()-1; // -1 admin user
				
				Collection<Vote> vote = makeCollection(voteRepository.findAll());
				int countedVote = vote.size();
				
				double voteRate = (double)countedVote/registeredUser;
				
				System.out.println("--- Counted Vote ["+countedVote+"] Registered User ["+registeredUser+"] \n--- Vote Rate ["+voteRate+"]");
				
				if (voteRate < voteRateLimit) {
					changeStatusResult.reject("VOTE_RATE_ERROR");
					changeVoteStatusReport = "ERROR_VOTE_RATE";
					System.out.println("--- Unable to close vote: Vote Rate less than 80% ---");
					
				} else {
					currentQuestion.setStatus(0);// Close the vote
					questionRepository.save(currentQuestion);
					changeVoteStatusReport = "CLOSED";
					System.out.println("--- Vote closed successfully ---");
				}
					
			} else { // If status error (neither 0 nor 1)
				changeStatusResult.reject("QUESTION_STATUS_ERROR");
				changeVoteStatusReport = "ERROR_STATUS_ERROR";
				System.out.println("--- Change Vote Status Error ---");
				
			}
			
		} else { // If question record not found in database
			changeStatusResult.reject("QUESTION_NOT_FOUND");
			changeVoteStatusReport = "ERROR_QUESTION_NOT_FOUND";
			System.out.println("--- Question not found ---");
			
		}
		
		return changeVoteStatusReport;
		
	}
	
	@RequestMapping(value="/editQuestion", method = RequestMethod.POST)
	@ResponseBody
	public String editQuestion (@ModelAttribute("questions") Question question, BindingResult editQuestionResult) {
		System.out.println("--- Received request for change Question Title ---");
		String editQuestionReport = "";
		int currentQuestionId = question.getRefId();
		String editedQuestionTitle = question.getTitle();
		
		Question currentQuestion = questionRepository.findByRefId(currentQuestionId);
		
		// If found question record in database
		if (currentQuestion != null) {
			
			// If question is still open
			if (currentQuestion.getStatus() != 0) {
				editQuestionResult.reject("QUESTION_"+currentQuestionId+"_IS_OPEN");
				editQuestionReport = "ERROR_QUESTION_"+currentQuestionId+"_IS_OPEN";
				System.out.println("--- Unable to edit: Question ["+currentQuestionId+"] is open ---");
				
			// If question is closed	
			} else {
				currentQuestion.setTitle(editedQuestionTitle);
				questionRepository.save(currentQuestion);
				editQuestionReport = "Question ["+currentQuestionId+"] Title changed to ["+editedQuestionTitle+"]";
				System.out.println("--- Title of Question ["+currentQuestionId+"] changed to ["+editedQuestionTitle+"]");
			}
			
			
		// If question not found in database	
		} else {
			editQuestionResult.reject("QUESTION_"+currentQuestionId+"_NOT_FOUND");
			editQuestionReport = "ERROR_QUESTION_"+currentQuestionId+"_NOT_FOUND";
			System.out.println("--- Question ["+currentQuestionId+"] not found ---");
			
		}
		
		return editQuestionReport;
		
	}
	
	@RequestMapping(value="/editOption", method = RequestMethod.POST)
	@ResponseBody
	public String editOption (@ModelAttribute("options") Option option, BindingResult editOptionResult) {
		System.out.println("--- Received request for change Option Content ---");
		String editOptionReport = "";
		int currentOptionId = option.getId();
		String editedOptionContent = option.getOption();
		
		Option currentOption = optionRepository.findById(currentOptionId);
		
		// If found option record in database
		if (currentOption != null) {
			
			// If question associated with this option is still open
			if (currentOption.getQuestion().getStatus() !=0 ) {
				
				editOptionResult.reject("VOTE_IS_OPEN");
				editOptionReport = "ERROR_VOTE_IS_OPEN";
				System.out.println("--- Unable to edit: Vote is open ---");
				
			} else {
				
				currentOption.setOption(editedOptionContent);
				optionRepository.save(currentOption);
				editOptionReport = "Option ["+currentOptionId+"] content changed to ["+editedOptionContent+"]";
				System.out.println("--- Content of Option ["+currentOptionId+"] changed to ["+editedOptionContent+"]");
			}
			
			
		// If option not found in database	
		} else {
			editOptionResult.reject("OPTION_"+currentOptionId+"_NOT_FOUND");
			editOptionReport = "ERROR_OPTION_"+currentOptionId+"_NOT_FOUND";
			System.out.println("--- Question ["+currentOptionId+"] not found ---");
			
		}
		
		return editOptionReport;
		
	}
	
	@RequestMapping(value="/getVoteCount", method = RequestMethod.GET)
	@ResponseBody
	public Response getVoteCount(Option option) {
		int optionId = option.getId();
		System.out.println("--- Received vote count request for Option ["+optionId+"] ---");
		Option optionForCount = optionRepository.findById(optionId);
		
		String voteCount = String.valueOf(optionForCount.getCount());
		System.out.println("--- Option ["+optionId+"] vote count ["+voteCount+"] ---");
		Response response = new Response ();
		response.setStatus(voteCount);
		
		return response;
	}
	
	// Convert Iterable of all registered user in repository to List
	public static <E> Collection<E> makeCollection(Iterable<E> iter) {
	    Collection<E> list = new ArrayList<E>();
	    for (E item : iter) {
	        list.add(item);
	    }
	    return list;
	}
	
	
}
