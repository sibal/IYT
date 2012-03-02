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
public class BasicViewController {
	
	ArticleValidator articleValidator;

	final int NUMBEROFMAYORS = 3;

	@Autowired private ObjectifyFactory objectifyFactory;
	@Autowired
	public BasicViewController(ArticleValidator articleValidator){
			
		this.articleValidator = articleValidator;
		System.out.println("Consumer_key is completed");
		
	}
	
	/**
	 * 메인화면
	 * @return
	 */
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
	

	/**
	 * 해석한 결과를 모아서 보아줌 - 메인에서 translated 탭 클릭시 여기로 접속
	 * @return
	 */
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
	
	
	/**
	 * 해석된 결과 중에, 원래 언어의 language를 선택해서 보여줌.
	 * @param lan
	 * @return
	 */
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
	
	
	/////////////////////////////////////////Contribute as///////////////////////////////////////////////////
	
	/**
	 * Contribute-as Twitlator의 livefeed 
	 * @return
	 */
	// For showing contribute-as 
	@RequestMapping(value="/contributelive", method=RequestMethod.GET)
    public ModelAndView contri_live() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		Objectify ofy = objectifyFactory.begin();
		
		//getting top-10 users
		List<User> users = ofy.query(User.class).order("-voting").limit(10).list();
		ModelAndView mav = list();
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
		
		//getting rank
		int rank = ofy.query(User.class).filter("voting >", user.getVoting()).count()+1;
		
		mav.addObject("rank", rank);
		mav.setViewName("article/contribute_live");				
		mav.addObject("interests", str);
		mav.addObject("fusers", users);
		return mav;
    }
	
	/**
	 * 자신이 Mayor인 계정(페북, 트위터)의 글 중에 request가 발생햇을 경우에 해당하는 글들을 보여줌.
	 * @return
	 */
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
		
		//getting top-10 users
		List<User> users = ofy.query(User.class).order("-voting").limit(10).list();
		int rank = ofy.query(User.class).filter("voting >", user.getVoting()).count()+1;
		
		System.out.println(requests.size());
		ModelAndView mav = list();

		mav.addObject("fusers", users);
		mav.addObject("rank", rank);

		mav.setViewName("article/contribute_mayor");
		mav.addObject("mrequests", requests);
		mav.addObject("interests", str);

		return mav;
    }
	
	
}
