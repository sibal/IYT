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

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterException;
import twitter4j.auth.RequestToken;
import twitter4j.auth.AccessToken;
import twitter4j.json.DataObjectFactory;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.conf.ConfigurationBuilder;
import org.apache.commons.lang.StringEscapeUtils;

import org.springframework.social.facebook.api.*;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;

@Controller
public class SocialController {
	TwitterFactory tf;
	Twitter twitter;
	ArticleValidator articleValidator;
	final String F_API_KEY = "125733630772899";
	final String CONSUMER_KEY = "KbEdWRWMaVZenArbG13g";
	final String CONSUMER_SECRET = "eXKS9NQwlLTwV1vgldsrcreGnYdLx0im5j7krgGAVY";
	final int NUMBEROFMAYORS = 3;
	String reqToken="";
	String reqTokenSecret="";
	@Autowired private ObjectifyFactory objectifyFactory;
	@Autowired
	public SocialController(ArticleValidator articleValidator){
			
		this.articleValidator = articleValidator;
		System.out.println("Consumer_key is completed");
		
	}
	
	///////////////// For Twitter ! //////////////////////////////////////////////////////////////////////////////////////////////
	
	private Twitter initTwitter()
	{
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setJSONStoreEnabled(true);
		TwitterFactory tf = new TwitterFactory(cb.build());		
		Twitter twitter = tf.getInstance();
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET); 
		return twitter;
	}
	
	@RequestMapping(value="/getTwitterAuth", method=RequestMethod.GET)
    public ModelAndView getTwitAuth() {
		
		twitter = initTwitter();
		
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		RequestToken requestToken = null;
		try {
			
			requestToken = twitter.getOAuthRequestToken();
			reqToken = requestToken.getToken();
			reqTokenSecret = requestToken.getTokenSecret();
			
		} catch (TwitterException e) {
				
		}
	
		String redirect = requestToken.getAuthorizationURL();
		
		return new ModelAndView("redirect:"+redirect);
    }
	
	

	
	@RequestMapping(value="/callbackTwit", method=RequestMethod.GET)
    public ModelAndView callbackTwit(@RequestParam("oauth_token") String oauth_token) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		AccessToken accessToken = null;
		RequestToken requestToken = new RequestToken(reqToken, reqTokenSecret);
		
		if (reqToken.equals(oauth_token)) 
		{   
			try { 
				System.out.println(oauth_token+ "  " + reqTokenSecret);
				accessToken = twitter.getOAuthAccessToken(requestToken); 
			} catch (TwitterException e) {
				
			}    
		}
		
		ModelAndView mv = new ModelAndView("user/closed");
		mv.addObject("token", accessToken.getToken());
		mv.addObject("token_secret", accessToken.getTokenSecret());
		
		return mv;
    }
	
	@RequestMapping(value="/t_getTimeline", method=RequestMethod.GET)
    public ResponseEntity<String> twitTimeline() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Objectify ofy = objectifyFactory.begin();
		User user = (User)authentication.getPrincipal();
		AccessToken accessToken = new AccessToken(user.getTwit_authT(), user.getTwit_authTS());
		twitter = initTwitter();
		twitter.setOAuthAccessToken(accessToken);
		List<Status> statuses = null; 
		Paging page = new Paging();
		page.count(5);
		page.setPage(1);
		
		String result = "";
		try {   
			statuses = twitter.getHomeTimeline(page);
		} catch (TwitterException e) {
				System.out.println(e.getErrorMessage());
		}
		System.out.println("twitter?");
		String results = "{\"statuses\":[";
		int count = 0;

		for (Status status : statuses) {
			count ++;
			System.out.println(status.getCreatedAt());

			results += DataObjectFactory.getRawJSON(status);

			if (count == statuses.size())
				results += "]";
			else
				results += ",";
			
			System.out.println(DataObjectFactory.getRawJSON(status));
			
		}
		
		results += "}";
		String real_result = "";
		try {
			real_result = new String(results.getBytes("utf-8"), "utf-8");
		System.out.println(real_result);
		}
		catch(Exception e)
		{
			
		}	
	
		
		HttpHeaders responseHeaders = new HttpHeaders(); 
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		responseHeaders.add("Content-Length", ""+real_result.getBytes().length);
		return new ResponseEntity<String>(real_result, responseHeaders, HttpStatus.CREATED); 

    }
	
	@RequestMapping(value="/t_getTimeline/{id}", method=RequestMethod.GET)
    public ResponseEntity<String> twitTimelineMax(@PathVariable String id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Objectify ofy = objectifyFactory.begin();
		User user = (User)authentication.getPrincipal();
		AccessToken accessToken = new AccessToken(user.getTwit_authT(), user.getTwit_authTS());
		twitter = initTwitter();
		twitter.setOAuthAccessToken(accessToken);
		List<Status> statuses = null; 
		Paging page = new Paging();
		page.count(6);
		page.setPage(1);
		page.setMaxId(Long.parseLong(id));
		String result = "";
		try {   
			statuses = twitter.getHomeTimeline(page);
		} catch (TwitterException e) {
				System.out.println(e.getErrorMessage());
		}
		statuses.remove(0);
		System.out.println("twitter?");
		String results = "{\"statuses\":[";
		int count = 0;

		for (Status status : statuses) {
			count ++;
			System.out.println(status.getCreatedAt());

			results += DataObjectFactory.getRawJSON(status);

			if (count == statuses.size())
				results += "]";
			else
				results += ",";
			
			System.out.println(DataObjectFactory.getRawJSON(status));
			
		}
		
		results += "}";
		String real_result = "";
		try {
			real_result = new String(results.getBytes("utf-8"), "utf-8");
		System.out.println(real_result);
		}
		catch(Exception e)
		{
			
		}	
	
		
		HttpHeaders responseHeaders = new HttpHeaders(); 
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		responseHeaders.add("Content-Length", ""+real_result.getBytes().length);
		return new ResponseEntity<String>(real_result, responseHeaders, HttpStatus.CREATED); 

    }
	
	@RequestMapping(value="/t_getTweet/{aid}", method=RequestMethod.GET)
    public ResponseEntity<String> twitTweet(@PathVariable String aid) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Objectify ofy = objectifyFactory.begin();
		User user = (User)authentication.getPrincipal();
		AccessToken accessToken = new AccessToken(user.getTwit_authT(), user.getTwit_authTS());
		twitter = initTwitter();
		twitter.setOAuthAccessToken(accessToken);
		Status status = null; 
		String result = "";
		System.out.println(aid);
		try {   
			status = twitter.showStatus(Long.parseLong(aid));
		} catch (TwitterException e) {
				System.out.println(e.getErrorMessage());
		}
		
		String real_result = DataObjectFactory.getRawJSON(status);
		HttpHeaders responseHeaders = new HttpHeaders(); 
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		responseHeaders.add("Content-Length", ""+real_result.getBytes().length);
		return new ResponseEntity<String>(real_result, responseHeaders, HttpStatus.CREATED); 
    }
	
	// Retweet, Favorite
	@RequestMapping(value="/twitterAction/{action}/{aid}", method=RequestMethod.GET)
	@ResponseBody
    public String twitAction(@PathVariable String action, @PathVariable String aid) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Objectify ofy = objectifyFactory.begin();
		User user = (User)authentication.getPrincipal();
		AccessToken accessToken = new AccessToken(user.getTwit_authT(), user.getTwit_authTS());
		twitter = initTwitter();
		twitter.setOAuthAccessToken(accessToken);
		Status status = null; 
		String result = "";
		System.out.println(aid);
		try {   
			if (action.equals("favorite"))
				twitter.createFavorite(Long.parseLong(aid));
			else if (action.equals("cancelfavorite"))
				twitter.destroyFavorite(Long.parseLong(aid));
			else
				twitter.retweetStatus(Long.parseLong(aid));
			
		} catch (TwitterException e) {
				System.out.println(e.getErrorMessage());
				return "{\"success\":\"0\"}";
		}
		
		return "{\"success\":\"1\"}";
    }
	
	//////////Facebook assist/////////////////////////////////////////////////////////////
		
	// Retweet, Favorite
	@RequestMapping(value="/fbLike/{action}/{aid}", method=RequestMethod.GET)
	@ResponseBody
    public String fbLike(@PathVariable String action, @PathVariable String aid) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Objectify ofy = objectifyFactory.begin();
		User user = (User)authentication.getPrincipal();
		String accessToken = user.getFace_access(); // access token received from Facebook after OAuth authorization
		System.out.println("like!");
		try{
		Facebook facebook = new FacebookTemplate(accessToken);
		facebook.likeOperations().like(aid);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		System.out.println("done!");
		return "{\"success\":\"1\"}";
    }
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////
	
	
	
}
