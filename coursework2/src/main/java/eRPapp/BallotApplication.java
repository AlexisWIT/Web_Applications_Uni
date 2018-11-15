package eRPapp;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import eRPapp.controller.PasswordEncryptor;
import eRPapp.domain.*;
import eRPapp.repository.*;

@SpringBootApplication
public class BallotApplication extends WebMvcConfigurerAdapter implements CommandLineRunner {
	
	@Autowired VoteRepository voteRepository;
	@Autowired AccountTypeRepository accountTypeRepository;
	@Autowired UserRepository userRepository;
	@Autowired BioIdCodeRepository bioIdCodeRepository;
	@Autowired QuestionRepository questionRepository;
	@Autowired OptionRepository optionRepository;
	
	public final static int COMMISSION = 0;
	public final static int VOTER = 1;
	
	public static void main (String[] args) {
		SpringApplication.run(BallotApplication.class, args);
	}
	

	public void run(String[] args) throws Exception {
		
		// Add account types (voter, election commission) to database
		AccountType commissionType = new AccountType(COMMISSION, "Election Commission");
		AccountType voterType = new AccountType(VOTER, "Voter");
		
		accountTypeRepository.save(commissionType);
		accountTypeRepository.save(voterType);
		
		// Add account of election commission to database
		try {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			//String adminPassword = PasswordEncryptor.getSHA256(PasswordEncryptor.getJBCrypt(PasswordEncryptor.getMD5("admin")));
			String adminPassword = passwordEncoder.encode("admin");
			User adminUser = new User("admin@gov.com", "admin", "admin", "2018-11-07", "Government facility", adminPassword, commissionType);
			
			userRepository.save(adminUser);	
		
		} catch (ParseException e) {
			e.printStackTrace();
		}
		

		// Add BIC data to database
		BioIdCode bioId0 = new BioIdCode("7F63-YDNH-G2LL-AKY7",0); bioIdCodeRepository.save(bioId0);
		BioIdCode bioId1 = new BioIdCode("7M73-LMDA-883S-EJT7",0); bioIdCodeRepository.save(bioId1);
		BioIdCode bioId2 = new BioIdCode("9T5C-RD3T-RYF2-SSJM",0); bioIdCodeRepository.save(bioId2);
		BioIdCode bioId3 = new BioIdCode("DJL8-3RDP-32JS-QUA8",0); bioIdCodeRepository.save(bioId3);
		BioIdCode bioId4 = new BioIdCode("E4U4-Z87Z-G6BM-QEJM",0); bioIdCodeRepository.save(bioId4);
		BioIdCode bioId5 = new BioIdCode("EHNL-G55E-YMQD-GF25",0); bioIdCodeRepository.save(bioId5);
		BioIdCode bioId6 = new BioIdCode("GJ2X-ERGA-RGT7-D9V9",0); bioIdCodeRepository.save(bioId6);
		BioIdCode bioId7 = new BioIdCode("TGJ7-CHX4-8FXA-35WF",0); bioIdCodeRepository.save(bioId7);
		BioIdCode bioId8 = new BioIdCode("VLPF-MXSF-533T-BA5Y",0); bioIdCodeRepository.save(bioId8);
		BioIdCode bioId9 = new BioIdCode("ZCXT-G275-JJ3R-C5YU",0); bioIdCodeRepository.save(bioId9);
		
		
		// Add the question of referendum to database
		Question question1 = new Question(1,"How should Shangri-La proceed to the next stage? (choose ONE)",1);
		questionRepository.save(question1);
		
		
		// Add options to database
		Option option1 = new Option(1, question1, "No deal", 0);
		Option option2 = new Option(2, question1, "Government\'s proposed trade deal", 0);
		Option option3 = new Option(3, question1, "Status quo", 0);
		optionRepository.save(option1);
		optionRepository.save(option2);
		optionRepository.save(option3);
	}
	

}
