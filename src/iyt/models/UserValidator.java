package iyt.models;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator{

	@Override
	public boolean supports(Class clazz) {
		//just validate the User instances
		return User.class.isAssignableFrom(clazz);

	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username",
				"required.username", "Field name is required.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"required.password", "Field name is required.");
			
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password_c",
				"required.password_c", "Field name is required.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nick", 
				"required.nick", "Field name is required.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", 
				"required.name", "Field name is required.");
		
		User user = (User)target;
		
		if(!(user.getPassword().equals(user.getPassword_c()))){
			errors.rejectValue("password", "notmatch.password");
		}
	}
	
}