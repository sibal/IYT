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
public class ArticleController {
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
	public ArticleController(ArticleValidator articleValidator){
			
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setJSONStoreEnabled(true);
		TwitterFactory tf = new TwitterFactory(cb.build());		
		twitter = tf.getInstance();
		
		this.articleValidator = articleValidator;
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET); 
		System.out.println("Consumer_key is completed");
		
	}
	
	///////////////// For Twitter ! //////////////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/getTwitterAuth", method=RequestMethod.GET)
    public ModelAndView getTwitAuth() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

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
    public ResponseEntity<String> twitTimelineSince(@PathVariable String id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Objectify ofy = objectifyFactory.begin();
		User user = (User)authentication.getPrincipal();
		AccessToken accessToken = new AccessToken(user.getTwit_authT(), user.getTwit_authTS());
		twitter.setOAuthAccessToken(accessToken);
		List<Status> statuses = null; 
		Paging page = new Paging();
		page.count(5);
		page.setPage(1);
		page.setMaxId(Long.parseLong(id));
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
	
	@RequestMapping(value="/t_getTweet/{aid}", method=RequestMethod.GET)
    public ResponseEntity<String> twitTweet(@PathVariable String aid) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Objectify ofy = objectifyFactory.begin();
		User user = (User)authentication.getPrincipal();
		AccessToken accessToken = new AccessToken(user.getTwit_authT(), user.getTwit_authTS());
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
		twitter.setOAuthAccessToken(accessToken);
		Status status = null; 
		String result = "";
		System.out.println(aid);
		try {   
			if (action.equals("favorite"))
				twitter.createFavorite(Long.parseLong(aid));
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
	
	@RequestMapping(value="/", method=RequestMethod.GET)
    public ModelAndView list() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Objectify ofy = objectifyFactory.begin();
		User user = (User)authentication.getPrincipal();
		ModelAndView mav = new ModelAndView("article/list");
			
		// add additional information for the user
		// find the first and second languages of the user.
		// find the numbers of translations for each language.

		Language[] languages = Language.values();
		int[] counts = new int[languages.length];
		
		int total=0;
		for (TransInformation t: user.getTransinfo())
		{
			counts[t.getDest().num]+=t.getNum();
			total+=t.getNum();
		}
		
		int max=-1;
		int maxid=-1;
		for (int i=0;i<counts.length;i++)
		{
			if (max < counts[i])
			{
				max = counts[i];
				maxid = i;
			}
		}
		int secmax = -1;
		int secmaxid = -1;
		for (int i=0;i<counts.length;i++)
		{
			if (i == maxid)
				continue;
			if (secmax < counts[i])
			{
				secmax = counts[i];
				secmaxid = i;
			}
		}		
		
		user.setNumFirstLan(max);
		user.setNumSecondLan(secmax);
		user.setFirstLanguage(Language.findByNum(maxid).name);
		user.setSecondLanguage(Language.findByNum(secmaxid).name);
		user.setNumOtherLan(total-max-secmax);
		
		//user.setFollowees();
		//System.out.println(user.getFollowees().size());
		
		// Provide recent translations
		List<Translation> trans = ofy.query(Translation.class).filter("author", user.getKey()).order("-created_at").limit(5).list();
		if (user.getRequestInfo() != null)
		{
			if (user.getRequestInfo().size() > 5)
			{
				List<TransRequest> requests = new ArrayList<TransRequest>();
				for (Key<TransRequest> t:user.getRequestInfo().subList(user.getRequestInfo().size()-6, user.getRequestInfo().size()-1))
				{
					requests.add(ofy.get(t));
				}
				mav.addObject("requests",requests);
			}
			else
			{
				List<TransRequest> requests = new ArrayList<TransRequest>();
				for (Key<TransRequest> t:user.getRequestInfo())
				{
					requests.add(ofy.get(t));
				}
				mav.addObject("requests",requests);
			}
		}
		else
		{
			mav.addObject("requests",new ArrayList<TransRequest>());
		}
		mav.addObject("command", new Article());
		mav.addObject("user", user);
		mav.addObject("recent",trans);
		
		
		// translation info
		max =0;

		List<TransInformation> transinfo = user.getTransinfo();
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
		secmax = -1;
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
		
		mav.addObject("maxTrans", maxinfo);
		mav.addObject("secTrans", secinfo);
		mav.addObject("followees",(ofy.query(Followship.class).filter("follower", user.getKey()).list()));
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
	@ResponseBody
    public String doTranslate(@ModelAttribute("command") Translation translation, BindingResult result ) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Objectify ofy = objectifyFactory.begin();
		User author = (User)authentication.getPrincipal();
		// Translate!
		translation.setAuthor(author.getKey());
				
		System.out.println(translation.getOri_content());
		System.out.println(result.toString());
		
		try {
			String s = URLEncoder.encode(translation.getOri_content(), "UTF-8");
			URL url = new URL(
					"http://ws.detectlanguage.com/0.2/detect?key=309c147273b57d16f43ed794646e7fb8&q="
							+ s + "");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String str;
			StringBuilder buffer = new StringBuilder();
			while ((str = in.readLine()) != null) {
				buffer.append(str);
			}
			in.close();
			System.out.println(buffer.toString());
			JSONObject obj = (JSONObject) JSONSerializer.toJSON(buffer
					.toString());
			System.out.println(obj.getJSONObject("data")
					.getJSONArray("detections").getJSONObject(0)
					.get("language"));

			
			translation.setOri_lan(Language.findByAbb(obj.getJSONObject("data").getJSONArray("detections").getJSONObject(0).get("language").toString()));

			
			s = URLEncoder.encode(translation.getT_content(), "UTF-8");
			url = new URL(
					"http://ws.detectlanguage.com/0.2/detect?key=309c147273b57d16f43ed794646e7fb8&q="
							+ s + "");
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			buffer = new StringBuilder();
			while ((str = in.readLine()) != null) {
				buffer.append(str);
			}
			in.close();
			System.out.println(buffer.toString());
			obj = (JSONObject) JSONSerializer.toJSON(buffer.toString());
			System.out.println(obj.getJSONObject("data").getJSONArray("detections").getJSONObject(0).get("language"));
			
			translation.setT_lan(Language.findByAbb(obj.getJSONObject("data").getJSONArray("detections").getJSONObject(0).get("language").toString()));

		        //System.out.println(obj.get("confidence")); 
		    } catch (UnsupportedEncodingException e) { 
		        e.printStackTrace(); 
		    } catch (MalformedURLException e) { 
		        e.printStackTrace(); 
		    } catch (IOException e) { 
		        e.printStackTrace(); 
		    } 

		
		//translation.setOri_lan("test");
		//translation.setT_lan("bbb");
		
		System.out.println("test1");
		boolean isHere=false;
		if (author.getTransinfo() != null)
			for(TransInformation i : author.getTransinfo() )
			{
				if (i.getSource().equals(translation.getOri_lan()))
					if(i.getDest().equals(translation.getT_lan()))
					{
						isHere=true;
						i.setNum(i.getNum()+1);
						//ofy.put(author);
					}
			}
		else
		{
			System.out.println("null");
		}
		System.out.println("test2");
		if (!isHere)
		{
			System.out.println(translation.getOri_lan() + " " + translation.getT_lan());
			TransInformation t = new TransInformation(translation.getOri_lan(), translation.getT_lan(), 1);
			System.out.println("test3 " + t.getNum() + " " + t.getDest() + " " +t.getSource());
			//ofy.put(t);
			System.out.println("test4");
			author.getTransinfo().add(t);

		}
		
		System.out.println("middle");
		// update mayor information
		Mayors mayor = ofy.query(Mayors.class).filter("userid", translation.getUserid()).filter("mayor", author.getKey()).get();
		if (mayor == null)
		{
			Mayors newmayor = new Mayors(translation.getUserid(), Vender.getByInteger(translation.getVender()));
			newmayor.setMayor(author.getKey());
			newmayor.setSumofvotes(0);
			System.out.println("test5 " + translation.getVender());
			try{
			ofy.put(newmayor);
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			System.out.println("test6");

		}
		

		if(ofy.query(TransRequest.class).filter("aid", translation.getSid()).count() > 0)
		{
			System.out.println("??");
			TransRequest t = ofy.query(TransRequest.class).filter("aid", translation.getSid()).get();
			User u;
			if (t.getRequester().equals(author.getKey()))
				u = author;
			else
				u = ofy.get(t.getRequester());
			u.getRequestInfo().remove(t.getKey());
			u.setRequestInfo(u.getRequestInfo());
			ofy.put(u);	
			ofy.delete(t);
		}
			
		
		
		System.out.println("test7");
		ofy.put(author);
		ofy.put(translation);
		System.out.println("Why?");
		Translation t = ofy.get(translation.getKey());		
		System.out.println("{\"success\":\""+t.getOri_content()+"\"}");
		return "{\"success\":\""+t.getKey()+"\"}";
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
		Query<Translation> q = ofy.query(Translation.class).filter("author", user.getKey()).order("-created_at");

		List<Translation> translations = new ArrayList<Translation>();
		for(Translation a: q) {
			if(a.getAuthor() != null) a.setAuthor_data((User) ofy.get(a.getAuthor()));
			translations.add(a);
		}
		
		ModelAndView mav = list();
		mav.setViewName("article/translations");
		mav.addObject("lan", -1);
		mav.addObject("translations", translations);
				
		return mav;
    }
	
	
	
	// For showing translations
	@RequestMapping(value="/translations/{lan}", method=RequestMethod.GET)
    public ModelAndView listTwithLan(@PathVariable int lan) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		Objectify ofy = objectifyFactory.begin();
		Query<Translation> q = ofy.query(Translation.class).filter("author", user.getKey()).filter("ori_lan", Language.findByNum(lan)).order("-created_at");

		List<Translation> translations = new ArrayList<Translation>();
		for(Translation a: q) {
			if(a.getAuthor() != null) a.setAuthor_data((User) ofy.get(a.getAuthor()));
			translations.add(a);
		}
		
		ModelAndView mav = list();
		mav.setViewName("article/translations");
		mav.addObject("lan", lan);
		mav.addObject("translations", translations);
				
		return mav;
    }
	
	// For showing translations
	@RequestMapping(value="/topfour/{sid}", method=RequestMethod.GET)
    public ResponseEntity<String> topFourT(@PathVariable String sid) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		Objectify ofy = objectifyFactory.begin();
		System.out.println(sid);
		Query<Translation> q = ofy.query(Translation.class).filter("sid", sid).order("-voting").limit(4);
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
		
		HttpHeaders responseHeaders = new HttpHeaders(); 
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		responseHeaders.add("Content-Length", ""+jsonObject.toString().getBytes().length);
		return new ResponseEntity<String>(jsonObject.toString(), responseHeaders, HttpStatus.CREATED); 
		
		//return jsonObject.toString();
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
    public String cancleVote(@PathVariable String t_id) {
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
    public String showVote(@PathVariable String t_id) {
		Objectify ofy = objectifyFactory.begin();
		//What the hell?
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		Translation translation = ofy.get(Translation.class, t_id);
		
		return translation.getVoting()+"";
	}
	
	
	// translation request
	@RequestMapping(value="/request", method=RequestMethod.POST)
	@ResponseBody
    public String requestTranslate(@ModelAttribute("command") TransRequest transrequest, BindingResult result ) {
		Objectify ofy = objectifyFactory.begin();
		//What the hell?
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		
		if(ofy.query(TransRequest.class).filter("requester", user.getKey()).filter("aid", transrequest.getAid()).count() > 0)
			return "0";
		
		transrequest.setRequester(user.getKey());
		transrequest.setCreated_at(new Date());
		
		//System.out.println(transreque);
		
		try {
			String s = URLEncoder.encode(transrequest.getText(), "UTF-8");
			URL url = new URL(
					"http://ws.detectlanguage.com/0.2/detect?key=309c147273b57d16f43ed794646e7fb8&q="
							+ s + "");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String str;
			StringBuilder buffer = new StringBuilder();
			while ((str = in.readLine()) != null) {
				buffer.append(str);
			}
			in.close();
			System.out.println(buffer.toString());
			JSONObject obj = (JSONObject) JSONSerializer.toJSON(buffer
					.toString());
			System.out.println(obj.getJSONObject("data")
					.getJSONArray("detections").getJSONObject(0)
					.get("language"));

			
			transrequest.setLanguage(Language.findByAbb(obj.getJSONObject("data").getJSONArray("detections").getJSONObject(0).get("language").toString()));

		        //System.out.println(obj.get("confidence")); 
		    } catch (UnsupportedEncodingException e) { 
		        e.printStackTrace(); 
		    } catch (MalformedURLException e) { 
		        e.printStackTrace(); 
		    } catch (IOException e) { 
		        e.printStackTrace(); 
		    } 

		
		
		
		
		System.out.println("Request!!!");
		ofy.put(transrequest);
		user.getRequestInfo().add(transrequest.getKey());
		ofy.put(user);
		
		return "1";
	}
	
	
	// translation request cancle
	@RequestMapping(value="/canclerequest/{rid}", method=RequestMethod.GET)
	@ResponseBody
    public String cancleRequestTranslate(@PathVariable long rid) {
		Objectify ofy = objectifyFactory.begin();
		//What the hell?
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
				
		List<Key<TransRequest>> trs = user.getRequestInfo();
		Key<TransRequest> target = null;
		for(Key<TransRequest> t:trs)
		{
			if (t.getId() == rid)
			{
				target = t;
				break;
			}
		}
		
		if (target != null)
			trs.remove(target);
		user.setRequestInfo(trs);
		ofy.put(user);
		
		ofy.delete(new Key<TransRequest> (TransRequest.class, rid));
		
		
		return "success";
	}
	
	/////////////////////////////////////////Contribute as///////////////////////////////////////////////////
	
	// For showing contribute-as
	@RequestMapping(value="/contributelive", method=RequestMethod.GET)
    public ModelAndView contri_live() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		Objectify ofy = objectifyFactory.begin();
		
		ModelAndView mav = list();
		mav.setViewName("article/contribute_live");				
		return mav;
    }
	
	// For showing contribute-as
	@RequestMapping(value="/contributelive/mayor", method=RequestMethod.GET)
    public ModelAndView contri_mayor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		Objectify ofy = objectifyFactory.begin();
		
		List<Mayors> mymayorinfo = ofy.query(Mayors.class).filter("mayor", user.getKey()).list();
		List<TransRequest> requests = new ArrayList<TransRequest>();
		for(Mayors mayor:mymayorinfo)
		{
			Mayors t = ofy.query(Mayors.class).filter("userid", mayor.getUserid()).order("-sumofvotes").limit(1).get();
			if (t.getMayor().equals(mayor.getMayor()))
			{
				System.out.println("hit");
				List<TransRequest> tempre = ofy.query(TransRequest.class).filter("uid", mayor.getUserid()).list();
				System.out.println(tempre.size());
				requests.addAll(tempre);
			}
		}
		System.out.println(requests.size());
		ModelAndView mav = list();
		mav.setViewName("article/contribute_mayor");
		mav.addObject("mrequests", requests);
		return mav;
    }
	
	// For showing contribute-as
	@RequestMapping(value="/contributelive/fan", method=RequestMethod.GET)
    public ModelAndView contri_fan() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		Objectify ofy = objectifyFactory.begin();
		
		ModelAndView mav = list();
		mav.setViewName("article/contribute_fan");				
		return mav;
    }
	
}
