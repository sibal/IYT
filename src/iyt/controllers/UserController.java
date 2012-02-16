package iyt.controllers;

import iyt.enums.AppRole;
import iyt.models.Article;
import iyt.models.Translation;
import iyt.models.User;
import iyt.models.UserValidator;
import iyt.models.Followship;
import iyt.security.UserAuthentication;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.ObjectError;
import org.springframework.validation.FieldError;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.Query;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

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

    @RequestMapping(value="/signup_chk", method=RequestMethod.POST)
    @ResponseBody
    public String signup_chk(@ModelAttribute("command") User user, BindingResult result ) {
	userValidator.validate(user, result);
	JSONObject obj = new JSONObject();
	
    	for(ObjectError errors : result.getAllErrors()) {
	    FieldError fe = (FieldError)errors;
	    String field = fe.getField();
	    String message = fe.getDefaultMessage();
	    obj.put(field, message);
	}

	return obj.toString();
    }
    
    @RequestMapping(value="/signup", method=RequestMethod.POST)
    public ModelAndView signup_adv(@ModelAttribute("command") User user, BindingResult result ) {
    	if(user.getStep() == 1) return new ModelAndView("user/signup_adv", "user", user);
    	
    	userValidator.validate(user, result);
    	if(result.hasErrors()) return new ModelAndView("user/signup_adv", "user", user);
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	user.setAuthorities(EnumSet.of(AppRole.USER));
	user.setNumFans(0);
	System.out.println("REGISTER!");
	Objectify ofy = objectifyFactory.begin();
	ofy.put(user);
	
	// Update the context with the full authentication
	SecurityContextHolder.getContext().setAuthentication(new UserAuthentication(user, authentication.getDetails()));
	
	return new ModelAndView("redirect:/");

    }
    
    @RequestMapping(value = "/findUsers", method= RequestMethod.GET)
    public String findUsers() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();    	
        return "user/find";
    }
    
    
    // For searching friends
	@RequestMapping(value="/search.name", method=RequestMethod.GET)
	@ResponseBody
    public ResponseEntity<String> searchByName(@RequestParam("name") String name) {
		Objectify ofy = objectifyFactory.begin();
		//What the hell?
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();				
		User user = (User)authentication.getPrincipal();
		
		List<User> users = ofy.query(User.class).filter("name", name).list();
		
		JsonConfig config = new JsonConfig();
	     config.setJsonPropertyFilter(new PropertyFilter() {
	        public boolean apply(Object source, String name, Object value) {
	              if ("name".equals(name) || "username".equals(name) || "profile_image_url".equals(name)) {
	                  return false;
	              }
	              return true;
	           }
	       });

		JSONArray jsonArray = JSONArray.fromObject(users, config);
		
		Map<String, Object> map = new HashMap<String, Object> ();
		map.put("users",jsonArray);
		JSONObject jsonObject = JSONObject.fromObject(map);
		
		HttpHeaders responseHeaders = new HttpHeaders(); 
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		responseHeaders.add("Content-Length", ""+jsonObject.toString().getBytes().length);
		return new ResponseEntity<String>(jsonObject.toString(), responseHeaders, HttpStatus.CREATED); 
    }
	
    
    
    
    
    // For fans
	@RequestMapping(value="/befan/{target_id}", method=RequestMethod.GET)
	@ResponseBody
    public String beFan(@PathVariable String target_id) {
		Objectify ofy = objectifyFactory.begin();
		//What the hell?
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();				
		User target = ofy.get(User.class, target_id);
		User user = (User)authentication.getPrincipal();
		
		Followship f = new Followship(user, target);
		ofy.put(f);
		
		return "success";
    }
	
    // For fans
	@RequestMapping(value="/cancelfan/{target_id}", method=RequestMethod.GET)
	@ResponseBody
    public String cancelFan(@PathVariable String target_id) {
		Objectify ofy = objectifyFactory.begin();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();				
		User target = ofy.get(User.class, target_id);
		User user = (User)authentication.getPrincipal();
		
		Followship f = ofy.query(Followship.class).filter("follower", user).filter("followee", target).get();
		ofy.delete(f);
		
		return "success";
    }
	
	@RequestMapping(value="/fans", method=RequestMethod.GET)
    public ModelAndView showfans() {
		Objectify ofy = objectifyFactory.begin();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();				
		User user = (User)authentication.getPrincipal();

		Query<Followship> q = ofy.query(Followship.class).filter("follower", user);
		List<User> fans = new ArrayList<User>();
		
		for(Followship a: q) {
			fans.add(ofy.get(a.getFollower()));
		}
		
		ModelAndView mav = new ModelAndView("article/fans");
		mav.addObject("command", new User());
		mav.addObject("user", user);
		mav.addObject("fans", fans);
		return mav;
    }
	
		

	
}
