package eRPapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eRPapp.domain.Option;
import eRPapp.domain.Question;
import eRPapp.repository.*;

@Controller
@RequestMapping("/vote")
public class VoteController {
	
	@Autowired QuestionRepository questionRepository;
	@Autowired OptionRepository optionRepository;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String vote(@ModelAttribute("option") Option option, Model model) {
		
		model.addAttribute("question", new Question());
		//load questions and corresponding options
		model.addAttribute("questionList", (List<Question>) questionRepository.findAll());
		model.addAttribute("optionList", (List<Option>) optionRepository.findAll());
		return "Vote";
	}
	
	@RequestMapping("/confirm")
	public String confirmVote(@ModelAttribute("option") Option option, Model model) {
		
		return "redirect:/home/";
	}
	
	@RequestMapping("/voteFailed")
	public String voteFailed() {
		return "";
	}

}
