package eRPapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eRPapp.domain.Option;
import eRPapp.domain.Question;
import eRPapp.domain.User;
import eRPapp.repository.OptionRepository;
import eRPapp.repository.QuestionRepository;

@Controller
@RequestMapping("/dashboard")
public class AdminController {
	
	@Autowired QuestionRepository questionRepository;
	@Autowired OptionRepository optionRepository;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String initAdmin(@ModelAttribute("question") Question question, Model model) {
		
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
	public String changeVoteStatus(@ModelAttribute("question") Question question, BindingResult changeStatusResult) {
		String changeVoteStatusReport = "";
		int currentQuestionId = question.getRefId();
		
		Question currentQuestion = questionRepository.findByRefId(currentQuestionId);
		
		if (currentQuestion.getStatus()==0) { // if closed
			currentQuestion.setStatus(1);// open the vote
			questionRepository.save(currentQuestion);
			changeVoteStatusReport = "OPENED";
			System.out.println("--- Vote opened successfully ---");
			
		} else if (currentQuestion.getStatus()==1) {
			currentQuestion.setStatus(0);// close the vote
			questionRepository.save(currentQuestion);
			changeVoteStatusReport = "CLOSED";
			System.out.println("--- Vote closed successfully ---");
			
		} else {
			changeStatusResult.reject("CHANGE_STATUS_ERROR");
			changeVoteStatusReport = "ERROR";
			System.out.println("--- Change Vote Status Error ---");
			
		}
		return changeVoteStatusReport;
	}
}
