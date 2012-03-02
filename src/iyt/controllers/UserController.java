package iyt.controllers;

import iyt.enums.AppRole;
import iyt.enums.Interest;
import iyt.enums.Language;

import iyt.models.Article;
import iyt.models.TransInformation;
import iyt.models.Translation;
import iyt.models.User;
import iyt.models.UserValidator;
import iyt.models.Followship;
import iyt.security.UserAuthentication;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

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
import org.springframework.mail.*;
import org.springframework.mail.javamail.*;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.Query;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import java.util.Random;

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
    public ModelAndView signup_adv(@ModelAttribute("command") User user, BindingResult result) {
    	if(user.getStep() == 1) return new ModelAndView("user/signup_adv", "user", user);
    	
    	userValidator.validate(user, result);
    	if(result.hasErrors()) return new ModelAndView("user/signup_adv", "user", user);
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	user.setAuthorities(EnumSet.of(AppRole.USER));
	user.setNumFans(0);
	System.out.println("REGISTER!");
	Objectify ofy = objectifyFactory.begin();
	String interests = (String)result.getRawFieldValue("interests_str");
	StringTokenizer stk = new StringTokenizer(interests+"x", "/");
	System.out.println(interests);
	for(String s=stk.nextToken();stk.hasMoreTokens();s=stk.nextToken())
	{
		System.out.println(s);
		user.getInterests().add(Interest.findByNum(Integer.parseInt(s)));
	}
		
	if (!result.getRawFieldValue("language1").equals("-1"))
		user.getLanguages().add(Language.findByNum(Integer.parseInt((String)result.getRawFieldValue("language1"))));
	
	if (!result.getRawFieldValue("language2").equals("-1"))
		user.getLanguages().add(Language.findByNum(Integer.parseInt((String)result.getRawFieldValue("language2"))));
	
	if (!result.getRawFieldValue("language3").equals("-1"))
		user.getLanguages().add(Language.findByNum(Integer.parseInt((String)result.getRawFieldValue("language3"))));
	
	if (!result.getRawFieldValue("language4").equals("-1"))
		user.getLanguages().add(Language.findByNum(Integer.parseInt((String)result.getRawFieldValue("language4"))));
	
	if (!result.getRawFieldValue("language5").equals("-1"))
		user.getLanguages().add(Language.findByNum(Integer.parseInt((String)result.getRawFieldValue("language5"))));

	ofy.put(user);
	
	// Update the context with the full authentication
	SecurityContextHolder.getContext().setAuthentication(new UserAuthentication(user, authentication.getDetails()));
	
	return new ModelAndView("redirect:/");

    }
    
    @RequestMapping(value = "/findUsers", method= RequestMethod.GET)
    public ModelAndView findUsers() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	Objectify ofy = objectifyFactory.begin();
		User user = (User)authentication.getPrincipal();
		ModelAndView mav = new ModelAndView("user/find");
		mav.addObject("followees",(ofy.query(Followship.class).filter("follower", user.getKey()).list()));
		
		List<User> users = ofy.query(User.class).order("-voting").limit(50).list();
		List<User> popular = new ArrayList<User>();
		List<String> maxTran = new ArrayList<String>();
		
		Random oRandom = new Random();
		for(int i=0; i<6; i++)
		{
			popular.add(users.get(oRandom.nextInt(users.size()-1)));
		}
		
		for(User p: popular)
		{
			int max=0;
			String max_str = "";
			for(TransInformation t:p.getTransinfo())
			{
				if (max < t.getNum())
				{
					max = t.getNum();
					max_str = t.getSource().abb_name+"/"+t.getDest().abb_name;
				}
			}
			
			if (max_str.equals(""))
			{
				max_str="NOT/NOT";
			}
			
			maxTran.add(max_str);
		}
		

		
		mav.addObject("popular", popular);
		mav.addObject("maxTran", maxTran);
        return mav;
    }
    
    /**
     * 이름으로 찾기
     * @param name
     * @return
     */
    // For searching friends
	@RequestMapping(value="/search.name", method=RequestMethod.GET)
    public ResponseEntity<String> searchByName(@RequestParam("name") String name) {
		Objectify ofy = objectifyFactory.begin();
		//What the hell?
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();				
		User user = (User)authentication.getPrincipal();
		List<User> users = ofy.query(User.class).filter("name", name).list();
		
		JsonConfig config = new JsonConfig();
	     config.setJsonPropertyFilter(new PropertyFilter() {
	        public boolean apply(Object source, String name, Object value) {
	              if ("name".equals(name) || "username".equals(name) 
	            		  || "profile_image_url".equals(name) 
	            		  || "fid".equals(name) 
	            		  || "firstTran".equals(name) 
	            		  || "firstTranNum".equals(name) 
	            		  || "interests_str".equals(name)
	            		  || "secondTran".equals(name)
	            		  || "secondTranNum".equals(name) 
	            		  || "isMyFriend".equals(name)) {
	                  return false;
	              }
	              return true;
	           }
	       });
	          
	     for(User u:users)
	     {
	    	 if(u.getProfile_image_url() == null)
	    	 {
	    		 u.setProfile_image_url("http://graph.facebook.com/"+u.getFid()+"/picture");
	    		 ofy.put(u);
	    	 }
	    	 
	    	 if(ofy.query(Followship.class).filter("follower", user.getKey()).filter("followee", u.getKey()).count() == 1)
	    	 {
	    		 u.setIsMyFriend(1);
	    	 }
	    	 else
	    	 {
	    		 u.setIsMyFriend(0);
	    	 }
	    	 	    	 
	    	 
	 		// translation info
	 		int max =0;
	 		List<TransInformation> transinfo = u.getTransinfo();
	 		max=-1;
	 		TransInformation maxinfo=null;
	 		for (TransInformation ti:transinfo)
	 		{
	 			if (max < ti.getNum())
	 			{
	 				max = ti.getNum();
	 				maxinfo = ti;
	 			}
	 		}
	 		int secmax = -1;
	 		TransInformation secinfo=null;
	 		for (TransInformation ti:transinfo)
	 		{
	 			if (ti.equals(maxinfo))
	 				continue;
	 			if (secmax < ti.getNum())
	 			{
	 				secmax = ti.getNum();
	 				secinfo = ti;
	 			}
	 		}
	 		
	 		if(maxinfo == null)
	 		{
	 			u.setFirstTran("");
	 			u.setFirstTranNum(0);

	 		}else{
	 			u.setFirstTran(maxinfo.getSource().abb_name+"/"+maxinfo.getDest().abb_name);
	 			u.setFirstTranNum(max);
	 		}
	 		
	 		if(secinfo == null)
	 		{
	 			u.setSecondTran("");
	 			u.setSecondTranNum(0);
	 		}
	 		else
	 		{
	 			u.setSecondTran(secinfo.getSource().abb_name+"/"+secinfo.getDest().abb_name);
	 			u.setSecondTranNum(secmax);
	 		}
	 		
	 		
			String str = "";
			int count=0;
			for(Interest i: user.getInterests())
			{
				if (count == user.getInterests().size()-1)
					str+=i;
				else
					str+=i+", ";
				count++;
			}
	 		u.setInterests_str(str);
	     }
	     
	     
	     
	     
	    
		JSONArray jsonArray = JSONArray.fromObject(users, config);
		
		Map<String, Object> map = new HashMap<String, Object> ();
		map.put("users",jsonArray);
		JSONObject jsonObject = JSONObject.fromObject(map);
		System.out.println(jsonObject.toString());
		
		HttpHeaders responseHeaders = new HttpHeaders(); 
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		responseHeaders.add("Content-Length", ""+jsonObject.toString().getBytes().length);
		return new ResponseEntity<String>(jsonObject.toString(), responseHeaders, HttpStatus.CREATED); 
    }
	
        
    
    /**
     * 팬 되기
     * @param target_id
     * @return
     */
    // For being a fan
	@RequestMapping(value="/befan/{target_id}", method=RequestMethod.GET)
	@ResponseBody
    public String beFan(@PathVariable String target_id) {
		Objectify ofy = objectifyFactory.begin();
		//What the hell?
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		target_id = new String(Base64.decodeBase64(target_id));
		System.out.println(target_id);
		User target = ofy.get(User.class, target_id);
		User user = (User)authentication.getPrincipal();
				
		Followship f = new Followship(user, target);
		target.setNumFans(target.getNumFans()+1);
		ofy.put(target);
		ofy.put(f);
		
		return "{\"success\":\"1\"}";
    }
	
	/**
	 * 팬 취소
	 * @param target_id
	 * @return
	 */
    // For canceling being a fan
	@RequestMapping(value="/cancelfan/{target_id}", method=RequestMethod.GET)
	@ResponseBody
    public String cancelFan(@PathVariable String target_id) {
		Objectify ofy = objectifyFactory.begin();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		target_id = new String(Base64.decodeBase64(target_id));
		System.out.println("delete fan "+ target_id);
		User target = ofy.get(User.class, target_id);
		User user = (User)authentication.getPrincipal();
		Followship f = ofy.query(Followship.class).filter("follower", user).filter("followee", target).get();
		ofy.delete(f);
		
		return "{\"success\":\"1\"}";
    }
	
	
	/**
	 * 팬정보 보여주기 (구현 안됨)
	 * @return
	 */
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
	
	/**
	 * 초대 메일 보내기
	 * SMTP 정보가 있어야 하므로 구현이 다 되지 못함.
	 * @param target
	 * @return
	 */
	
	@RequestMapping(value="/invite/{target}", method=RequestMethod.GET)
	@ResponseBody
    public String sendInviteMail(@PathVariable String target) {
		Objectify ofy = objectifyFactory.begin();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();				
		User user = (User)authentication.getPrincipal();
		target = new String(Base64.decodeBase64(target));
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(target);
		msg.setText("Welcome to IUTranslate. ~_~");
		JavaMailSender sender = new JavaMailSenderImpl();
		
		try{
			sender.send(msg);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return "{\"success\":\"0\"}";
		}
		
		return "{\"success\":\"1\"}";
    }


	/**
	 * 계정정보 수정하기
	 * @param user1
	 * @param result
	 * @return
	 */

    @RequestMapping(value="/account_edit", method=RequestMethod.POST)
    public ModelAndView account_edit(@ModelAttribute("command") User user1, BindingResult result) {

    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String opass = (String)result.getRawFieldValue("opassword");
		User user = (User)authentication.getPrincipal();
    	if (!opass.equals(user.getPassword()))
    	{
    		System.out.println("pass:"+opass);
    		ModelAndView mv = account_view();
    		mv.addObject("result", "false");
    		return mv;
    	}	
    	
    	    	
		Objectify ofy = objectifyFactory.begin();
		
		String interests = (String)result.getRawFieldValue("interests_str");
		if (!interests.equals(""))
		{
			StringTokenizer stk = new StringTokenizer(interests+"x", "/");
			System.out.println(interests);
			user.setInterests(new ArrayList<Interest>());
			for(String s=stk.nextToken();stk.hasMoreTokens();s=stk.nextToken())
			{
				System.out.println(s);
				user.getInterests().add(Interest.findByNum(Integer.parseInt(s)));
			}
		}
		
		user.setLanguages(new ArrayList<Language>());
		
		if (!result.getRawFieldValue("language1").equals("-1"))
			user.getLanguages().add(Language.findByNum(Integer.parseInt((String)result.getRawFieldValue("language1"))));
		
		if (!result.getRawFieldValue("language2").equals("-1"))
			user.getLanguages().add(Language.findByNum(Integer.parseInt((String)result.getRawFieldValue("language2"))));
		
		if (!result.getRawFieldValue("language3").equals("-1"))
			user.getLanguages().add(Language.findByNum(Integer.parseInt((String)result.getRawFieldValue("language3"))));
		
		if (!result.getRawFieldValue("language4").equals("-1"))
			user.getLanguages().add(Language.findByNum(Integer.parseInt((String)result.getRawFieldValue("language4"))));
		
		if (!result.getRawFieldValue("language5").equals("-1"))
			user.getLanguages().add(Language.findByNum(Integer.parseInt((String)result.getRawFieldValue("language5"))));
	
		if(!user1.getFace_access().equals(""))
			user.setFace_access(user1.getFace_access());
		if(!user1.getFid().equals(""))
			user.setFid(user1.getFid());
		if(!user1.getTwit_authT().equals(""))
			user.setTwit_authT(user1.getTwit_authT());
		if(!user1.getTwit_authTS().equals(""))
			user.setTwit_authTS(user1.getTwit_authTS());
		if(!user1.getNick().equals(""))
			user.setNick(user1.getNick());
		
		if(!user1.getPassword().equals(user1.getPassword_c()))
		{
    		ModelAndView mv = account_view();
    		mv.addObject("result", "false");
    		return mv;
		}
			
		if(!user1.getPassword().equals("") && (user1.getPassword().equals(user1.getPassword_c())))
		{
			user.setPassword(user1.getPassword());
			user.setPassword_c(user1.getPassword_c());
		}
		
		ofy.put(user);
	
		return account_view();

    }
    
    /**
     * 계정 정보 보여주기
     * @return
     */
    @RequestMapping(value="/account_view", method=RequestMethod.GET)
    public ModelAndView account_view() {

    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		Objectify ofy = objectifyFactory.begin();
	
		
		ModelAndView mv = new ModelAndView("/user/account");
		mv.addObject("user", user);
		mv.addObject("result", "true");

		return mv;

    }
	
}
