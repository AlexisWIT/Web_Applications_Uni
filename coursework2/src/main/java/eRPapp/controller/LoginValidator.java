package eRPapp.controller;

import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;

public class LoginValidator implements Validator {
	
	public boolean supports(Class<?> clazz) {
		return AppDataTransferObject.class.equals(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "", "Please enter your email.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "", "Please enter your password.");
		
	}

}
