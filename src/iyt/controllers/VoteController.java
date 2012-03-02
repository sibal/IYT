package iyt.controllers;

import java.lang.reflect.Array;
import java.util.Vector;

import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.io.*;

import iyt.enums.AppRole;
import iyt.models.*;
import iyt.enums.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.HttpHeaders;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.JSONString;
import net.sf.json.util.PropertyFilter;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.Query;

import org.apache.commons.lang.StringEscapeUtils;


@Controller
public class VoteController {
	
	ArticleValidator articleValidator;

	final int NUMBEROFMAYORS = 3;

	@Autowired private ObjectifyFactory objectifyFactory;
	@Autowired
	public VoteController(ArticleValidator articleValidator){
			
		this.articleValidator = articleValidator;
		System.out.println("Consumer_key is completed");
		
	}
		
	
	// vote
	@RequestMapping(value="/dovote/{t_id}", method=RequestMethod.GET)
	@ResponseBody
    public String voting_doVote(@PathVariable String t_id) {
		Objectify ofy = objectifyFactory.begin();
		//What the hell?
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		System.out.println(t_id);
		Translation translation = ofy.get(Translation.class, Long.parseLong(t_id));
		System.out.println("test2");
		
		if(ofy.query(Vote.class).filter("voter", user.getKey()).filter("target", translation.getKey()).count() > 0)
		{
			return "{\"success\":0}";
		}
		
		Vote vote = new Vote(user, translation);
		translation.setVoting(translation.getVoting()+1);
		User author = ofy.get(translation.getAuthor());
		author.setVoting(author.getVoting()+1);
		ofy.put(author);
		ofy.put(vote);
		ofy.put(translation);
		
		Translation t = ofy.get(translation.getKey());
				
		
		// update mayor information
		Mayors mayor = ofy.query(Mayors.class).filter("userid", translation.getUserid()).filter("mayor", translation.getAuthor()).get();
		if (mayor == null)
			System.out.println("no existing slot error");
		else
		{
			mayor.setSumofvotes(mayor.getSumofvotes()+1);
			ofy.put(mayor);
		}
		

		System.out.println("{\"voting\":"+t.getVoting()+", \"success\":1}");
		return "{\"voting\":"+t.getVoting()+", \"success\":1}";
	}
	
	// cancle vote
	@RequestMapping(value="/canclevote/{t_id}", method=RequestMethod.GET)
	@ResponseBody
    public String voting_cancleVote(@PathVariable String t_id) {
		Objectify ofy = objectifyFactory.begin();
		//What the hell?
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		Translation translation = ofy.get(Translation.class, t_id);
		
		Vote vote = ofy.query(Vote.class).filter("voter", user).filter("target", translation).get();
		ofy.delete(vote);
		translation.setVoting(translation.getVoting()-1);

		
		// update mayor information
		Mayors mayor = ofy.query(Mayors.class).filter("userid", translation.getUserid()).filter("mayor", translation.getAuthor()).get();
		if (mayor == null)
			System.out.println("no existing slot error");
		else
		{
			mayor.setSumofvotes(mayor.getSumofvotes()-1);
			ofy.put(mayor);
		}
		
		User author = ofy.get(translation.getAuthor());
		author.setVoting(author.getVoting()-1);
		ofy.put(author);

		return translation.getVoting()+"";
	}
	
	// show vote
	@RequestMapping(value="/vote/{t_id}", method=RequestMethod.GET)
	@ResponseBody
    public String voting_showVote(@PathVariable String t_id) {
		Objectify ofy = objectifyFactory.begin();
		//What the hell?
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		Translation translation = ofy.get(Translation.class, t_id);
		
		return translation.getVoting()+"";
	}
	
	
}
