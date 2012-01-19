package iyt.models;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ArticleValidator implements Validator{

	@Override
	public boolean supports(Class clazz) {
		//just validate the User instances
		return Article.class.isAssignableFrom(clazz);

	}

	@Override
	public void validate(Object target, Errors errors) {
		
	}
	
}