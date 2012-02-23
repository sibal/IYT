package iyt.controllers;

import java.lang.reflect.Array;
import java.util.Vector;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
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

@Controller
public class ArticleController {
	Twitter twitter;
	ArticleValidator articleValidator;
	final String CONSUMER_KEY = "KbEdWRWMaVZenArbG13g";
	final String CONSUMER_SECRET = "eXKS9NQwlLTwV1vgldsrcreGnYdLx0im5j7krgGAVY";
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
		
		/*
		JsonConfig config = new JsonConfig();
	     config.setJsonPropertyFilter(new PropertyFilter() {
	        public boolean apply(Object source, String name, Object value) {
	              if ("createdAt".equals(name)) {
	                  return false;
	              }
	              return true;
	           }
	       });
		
		*/
		
		String results = "{\"statuses\":[";
		int count = 0;

		for (Status status : statuses) {
			count ++;
			System.out.println(status.getCreatedAt());
			/*
			results += "{";
			
			results += "\"name\":\""+status.getUser().getName()+"\",";
			results += "\"id\":\""+status.getId()+"\",";
			results += "\"text\":\""+status.getText()+"\",";
			results += "\"created_at\":\""+status.getCreatedAt()+"\",";
			results += "\"profileImageUrl\":\""+status.getUser().getProfileImageURL()+"\"";
			
			results += "}";
			*/
			
			results += DataObjectFactory.getRawJSON(status);
			//status.getId()
			//status.getUser().getName()
			//status.getUser().getScreenName() 
			//status.getUser().getURL()   //status.getText()
			//status.getCreatedAt()   
			//status.getUser().getProfileImageURL()
			//status.getSource()}
			
			if (count == statuses.size())
				results += "]";
			else
				results += ",";
			
		//result += status.getUser().getName();
			System.out.println(DataObjectFactory.getRawJSON(status));
			
		}
		
		results += "}";
		String real_result = "";
		try {
			real_result = new String(results.getBytes("utf-8"), "utf-8");
			//real_result = real_result.replace( "\r\n", "\\r\\n" );
		System.out.println(real_result);
		}
		catch(Exception e)
		{
			
		}
	
		
		HttpHeaders responseHeaders = new HttpHeaders(); 
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		responseHeaders.add("Content-Length", ""+real_result.getBytes().length);
		return new ResponseEntity<String>(real_result, responseHeaders, HttpStatus.CREATED); 

		
		
		//return results; 
    }
	
	@RequestMapping(value="/t_getTweet/{aid}", method=RequestMethod.GET)
	@ResponseBody
    public String twitTweet(@PathVariable String aid, HttpServletRequest request, HttpServletResponse response) {
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
		System.out.println("what");
		response.setContentType("text/plain; charset=UTF-8");
		System.out.println(response.getCharacterEncoding());
		System.out.println(status);
		response.setCharacterEncoding("UTF-8");
		System.out.println(DataObjectFactory.getRawJSON(status));
		return DataObjectFactory.getRawJSON(status);
    }
	
	
	
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
		mav.addObject("command", new Article());
		mav.addObject("user", user);
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
			/*
			Language r=null;
			for (Language i: Language.values())
			{
				
				if (i.abb_name.equals(abb))
				{
					result = i;
					break;
				}
				
			}
			
			if (result == null)
				return UNDEFINED;
			return KOREAN;
			*/
			
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

			TransInformation t = new TransInformation(translation.getOri_lan(), translation.getT_lan(), 1);
			System.out.println("test3 " + t.getNum() + " " + t.getDest() + " " +t.getSource() + " " + t.getId() );
			//ofy.put(t);
			System.out.println("test4");
			author.getTransinfo().add(t);

		}
		
		System.out.println("test5");
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
		Query<Translation> q = ofy.query(Translation.class).filter("author", user.getKey());

		List<Translation> translations = new ArrayList<Translation>();
		for(Translation a: q) {
			if(a.getAuthor() != null) a.setAuthor_data((User) ofy.get(a.getAuthor()));
			translations.add(a);
		}
		
		ModelAndView mav = new ModelAndView("article/translations");
		
		
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
		
		
		
		
		
		mav.addObject("followees",(ofy.query(Followship.class).filter("follower", user.getKey()).list()));

		mav.addObject("command", new Translation());
		mav.addObject("user", user);
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
		ofy.put(vote);
		ofy.put(translation);
		
		Translation t = ofy.get(translation.getKey());
		
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
