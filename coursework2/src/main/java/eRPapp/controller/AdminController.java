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
	
	@RequestMapping("/")
	public String initAdmin(Model model) {
		
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
	
	@RequestMapping("/changVoteStatus")
	@ResponseBody
	public String changVoteStatus(@ModelAttribute("question") Question question, BindingResult changeStatusResult) {
		String changVoteStatusReport ="";
		String changeStatusResultErrorInfo = "";
		int currentStatus = question.getStatus();
		
		
		
		return "";
	}
}
