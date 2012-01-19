package iyt.controllers;

import iyt.enums.AppRole;
import iyt.models.User;
import iyt.models.UserValidator;
import iyt.security.UserAuthentication;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;

/**
 * @author Inkyu lee
 */
@Controller
public class UserController {
    UserValidator userValidator;
    @Autowired private ObjectifyFactory objectifyFactory;
    @Autowired
	public UserController(UserValidator userValidator){
	this.userValidator = userValidator;
    }
 
	
    @RequestMapping(value = "/login", method= RequestMethod.GET)
	public String login() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	System.out.println((String)authentication.getPrincipal());
    	
        return "user/login";
    }
    
    @RequestMapping(value = "/logout", method= RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        String logoutUrl = UserServiceFactory.getUserService().createLogoutURL("/loggedout");

        response.sendRedirect(logoutUrl);
    }

    @RequestMapping(value = "/loggedout", method= RequestMethod.GET)
	public String loggedOut() {
        return "loggedout";
    }
    
    @RequestMapping(value="/signup", method=RequestMethod.GET)
	public ModelAndView signup(Model model) {
	//model.addAttribute("name", name);
    	return new ModelAndView("user/signup", "command", new User());
    }
    
    @RequestMapping(value="/signup", method=RequestMethod.POST)
	public ModelAndView signup_adv(@ModelAttribute("command") User user, BindingResult result ) {
    	if(user.getStep() == 1) return new ModelAndView("user/signup_adv", "user", user);
    	
    	userValidator.validate(user, result);
    	if(result.hasErrors()) return new ModelAndView("user/signup_adv", "user", user);
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	System.out.println((String)authentication.getPrincipal());

	user.setAuthorities(EnumSet.of(AppRole.USER));
		
	System.out.println("REGISTER!!!!");
	Objectify ofy = objectifyFactory.begin();
	ofy.put(user);
				
	// Update the context with the full authentication
	SecurityContextHolder.getContext().setAuthentication(new UserAuthentication(user, authentication.getDetails()));
		
	return new ModelAndView("redirect:/");

    }
}
