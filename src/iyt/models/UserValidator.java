package iyt.models;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import iyt.models.User;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;


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
		    errors.rejectValue("password_c", "notmatch.password", "Conformation password is not matching");
		}
		String username = user.getUsername();
		if(username != null){
		    Objectify ofy = ObjectifyService.begin();
		    try {
			ObjectifyService.register(User.class);
		    }
		    catch (Exception e) { }

		    User auser = null;
		    try {
			auser = ofy.get(User.class, username);
		    }
		    catch(Exception e) {
		    }
		    if(auser != null) 
			errors.rejectValue("username", "duplicate.username", "The given email is already used");
		}

		
	}
	
}