package iyt.controllers;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import iyt.enums.AppRole;
import iyt.models.*;

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
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
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
import twitter4j.Paging;
import twitter4j.Status;

@Controller
public class ArticleController {
	Twitter twitter = new TwitterFactory().getInstance();
	ArticleValidator articleValidator;
	final String CONSUMER_KEY = "KbEdWRWMaVZenArbG13g";
	final String CONSUMER_SECRET = "eXKS9NQwlLTwV1vgldsrcreGnYdLx0im5j7krgGAVY";
	String reqToken="";
	String reqTokenSecret="";
	@Autowired private ObjectifyFactory objectifyFactory;
	@Autowired
	public ArticleController(ArticleValidator articleValidator){
		this.articleValidator = articleValidator;
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET); 
		System.out.println("Consumer_key is completed");
	}
	
	/*
	//this mehtod should be edited, because the root url shows the timeline and the information for the time line will be given through ajax call.
	@RequestMapping(value="/", method=RequestMethod.GET)
    public ModelAndView list() {
		Objectify ofy = objectifyFactory.begin();
		Query<Article> q = ofy.query(Article.class).limit(10);
		List<Article> articles = new ArrayList<Article>();
		for(Article a: q) {
			if(a.getAuthor() != null) a.setAuthor_data((User) ofy.get(a.getAuthor()));
			articles.add(a);
		}
		
		ModelAndView mav = new ModelAndView("article/list");
		mav.addObject("command", new Article());
		mav.addObject("articles", articles);
		return mav;
    }
    */
	
	
	@RequestMapping(value="/getTwitterAuth", method=RequestMethod.GET)
    public ModelAndView getTwitAuth() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Objectify ofy = objectifyFactory.begin();
		User user = (User)authentication.getPrincipal();

		RequestToken requestToken = null;
		try {  
			requestToken = twitter.getOAuthRequestToken();
		} catch (TwitterException e) {
				
		}
		
		reqToken = requestToken.getToken();
		reqTokenSecret = requestToken.getTokenSecret();

		String redirect = requestToken.getAuthorizationURL();
		
		return new ModelAndView("redirect:"+redirect);
    }
	
	

	
	@RequestMapping(value="/callbackTwit", method=RequestMethod.GET)
    public ModelAndView callbackTwit(@RequestParam("oauth_token") String oauth_token) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Objectify ofy = objectifyFactory.begin();
		User user = (User)authentication.getPrincipal();

		AccessToken accessToken = null;
		RequestToken requestToken = new RequestToken(reqToken, reqTokenSecret);
		
		if (reqToken.equals(oauth_token)) 
		{   
			try { 
				System.out.println(oauth_token+ "  " + reqTokenSecret);
				accessToken = twitter.getOAuthAccessToken(requestToken); 
			} catch (TwitterException e) {
				
			}    
			//twitter.setOAuthAccessToken(accessToken);
			user.setTwit_authT(accessToken.getToken());//store oauthToke & secretToken to DB
			user.setTwit_authTS(accessToken.getTokenSecret()); //store oauthToke & secretToken to DB
			ofy.put(user);
			System.out.println("Access_Token Saved!");
		}
		return new ModelAndView("redirect:/");
    }
	
	@RequestMapping(value="/t_getTimeline", method=RequestMethod.GET)
	@ResponseBody
    public String twitTimeline() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Objectify ofy = objectifyFactory.begin();
		User user = (User)authentication.getPrincipal();
		System.out.println("go");
		AccessToken accessToken = new AccessToken(user.getTwit_authT(), user.getTwit_authTS());
		twitter.setOAuthAccessToken(accessToken);
		System.out.println("go2");
		List<Status> statuses = null; 
		Paging page = new Paging();
		page.count(20);
		page.setPage(1);
		String result = "";
		System.out.println("go3");
		try {   
			statuses = twitter.getHomeTimeline(page);
			System.out.println("go4");
		} catch (TwitterException e) {
				System.out.println(e.getErrorMessage());
		} 
		
		/*
		for (Status status : statuses) {
			//status.getId()
			//status.getUser().getName()
			//status.getUser().getScreenName() 
			//status.getUser().getURL()   //status.getText()
			//status.getCreatedAt()   
			//status.getUser().getProfileImageURL()
			//status.getSource()}
			
			result += status.getUser().getName();
			
		}*/
		
		JsonConfig config = new JsonConfig();
	     config.setJsonPropertyFilter(new PropertyFilter() {
	        public boolean apply(Object source, String name, Object value) {
	              if ("name".equals(name) || "screen_name".equals(name) || "createdAt".equals(name) || "text".equals(name) || "profile_image_url".equals(name)) {
	                  return false;
	              }
	              return true;
	           }
	       });
		
		System.out.println(statuses.size());
		JSONArray jsonArray = JSONArray.fromObject(statuses, config);
		System.out.println("test:"+jsonArray.toString());
		Map<String, Object> map = new HashMap<String, Object> ();
		map.put("statuses",jsonArray);
		JSONObject jsonObject = JSONObject.fromObject(map);
		
		return jsonObject.toString();
    }
	
	
	@RequestMapping(value="/", method=RequestMethod.GET)
    public ModelAndView list() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Objectify ofy = objectifyFactory.begin();
		User user = (User)authentication.getPrincipal();
		ModelAndView mav = new ModelAndView("article/list");
		mav.addObject("command", new Article());
		mav.addObject("user", user);
		return mav;
    }
	
	@RequestMapping(value="/article/{user_id}/{article_id}", method=RequestMethod.GET)
    public ModelAndView show(@PathVariable String user_id, @PathVariable String article_id) {
		Objectify ofy = objectifyFactory.begin();
		//What the hell?
		Article article = ofy.get(new Key<Article>(new Key<User>(User.class, user_id), Article.class, article_id));
		ModelAndView mav = new ModelAndView("partial/article");
		mav.addObject("article", article);
		return mav;
    }
	
	
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
    public ModelAndView doWrite(@ModelAttribute("command") Article article, BindingResult result ) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Objectify ofy = objectifyFactory.begin();
		User author = (User)authentication.getPrincipal();
		// Write!
		article.setAuthor(author.getKey());
		ofy.put(article);
		return list();
    }
	
	// For register translation
	@RequestMapping(value="/translate", method=RequestMethod.POST)
    public ModelAndView doTranslate(@ModelAttribute("command") Translation translation, BindingResult result ) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Objectify ofy = objectifyFactory.begin();
		User author = (User)authentication.getPrincipal();
		// Translate!
		translation.setAuthor(author.getKey());
		ofy.put(translation);
		System.out.println("Why?");
		return list();
    }
	/*
	// For showing translations
	@RequestMapping(value="/translation/{article_id}", method=RequestMethod.GET)
	public String getTranslations(@PathVariable String article_id) {
		Objectify ofy = objectifyFactory.begin();
		Query<Translation> q = ofy.query(Translation.class).limit(10);
		List<Translation> translations = new ArrayList<Translation>();
		for(Translation a: q) {
			if(a.getAuthor() != null) a.setAuthor_data((User) ofy.get(a.getAuthor()));
			translations.add(a);
		}
			
		ModelAndView mav = new ModelAndView("article/translations");
		mav.addObject("command", new Translation());
		mav.addObject("translations", translations);
		return mav;
	}*/
	
	
	// For showing translations
	@RequestMapping(value="/translations", method=RequestMethod.GET)
    public ModelAndView listTs() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		Objectify ofy = objectifyFactory.begin();
		Query<Translation> q = ofy.query(Translation.class).ancestor(user.getKey());

		List<Translation> translations = new ArrayList<Translation>();
		for(Translation a: q) {
			if(a.getAuthor() != null) a.setAuthor_data((User) ofy.get(a.getAuthor()));
			translations.add(a);
		}

		ModelAndView mav = new ModelAndView("article/translations");
		mav.addObject("command", new Translation());
		mav.addObject("user", user);
		mav.addObject("translations", translations);
		return mav;
    }
	
	
	// For showing translations
	@RequestMapping(value="/topfour/{sid}", method=RequestMethod.GET)
	@ResponseBody
    public String topFourT(@PathVariable String sid) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		Objectify ofy = objectifyFactory.begin();
		System.out.println(sid);
		Query<Translation> q = ofy.query(Translation.class).filter("sid", sid).order("voting").limit(4);
		//Query<Translation> q = ofy.query(Translation.class).ancestor(user.getKey());
		
		ArrayList<Translation> translations = new ArrayList<Translation>();
		for(Translation a: q) {
			if(a.getAuthor() != null) a.setAuthor_data((User) ofy.get(a.getAuthor()));
			translations.add(a);
		}
		System.out.println(translations.size());
		JsonConfig config = new JsonConfig();
	     config.setJsonPropertyFilter(new PropertyFilter() {
	        public boolean apply(Object source, String name, Object value) {
	              if ("name".equals(name) || "username".equals(name) || "id".equals(name) || "t_content".equals(name) || "voting".equals(name) || "author_data".equals(name)) {
	                  return false;
	              }
	              return true;
	           }
	       });

		JSONArray jsonArray = JSONArray.fromObject(translations, config);
		System.out.println("test:"+jsonArray.toString());
		Map<String, Object> map = new HashMap<String, Object> ();
		map.put("translations",jsonArray);
		JSONObject jsonObject = JSONObject.fromObject(map);
		
		return jsonObject.toString();
    }
	
	
	// For showing a translation
	@RequestMapping(value="/translation/{user_id}/{t_id}", method=RequestMethod.POST)
    public ModelAndView showT(@PathVariable String user_id, @PathVariable String t_id) {
		Objectify ofy = objectifyFactory.begin();
		//What the hell?
		Translation translation = ofy.get(new Key<Translation>(new Key<User>(User.class, user_id), Translation.class, t_id));
		ModelAndView mav = new ModelAndView("partial/translation");
		mav.addObject("translation", translation);
		return mav;
    }
	
	
	// vote
	@RequestMapping(value="/dovote/{t_id}", method=RequestMethod.GET)
	@ResponseBody
    public String doVote(@PathVariable String t_id) {
		Objectify ofy = objectifyFactory.begin();
		//What the hell?
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		Translation translation = ofy.get(Translation.class, t_id);
		
		Vote vote = new Vote(user, translation);
		translation.setVoting(translation.getVoting()+1);
		ofy.put(vote);
		
		return translation.getVoting()+"";
	}
	
	// cancle vote
	@RequestMapping(value="/canclevote/{t_id}", method=RequestMethod.GET)
	@ResponseBody
    public String cancleVote(@PathVariable String t_id) {
		Objectify ofy = objectifyFactory.begin();
		//What the hell?
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		Translation translation = ofy.get(Translation.class, t_id);
		
		Vote vote = ofy.query(Vote.class).filter("voter", user).filter("target", translation).get();
		ofy.delete(vote);
		translation.setVoting(translation.getVoting()-1);

		
		return translation.getVoting()+"";
	}
	
	// show vote
	@RequestMapping(value="/vote/{t_id}", method=RequestMethod.GET)
	@ResponseBody
    public String showVote(@PathVariable String t_id) {
		Objectify ofy = objectifyFactory.begin();
		//What the hell?
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		Translation translation = ofy.get(Translation.class, t_id);
				
		return translation.getVoting()+"";
	}
}
