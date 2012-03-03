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

/**
 * Translation request에 대한 controller
 * @author hellcodes
 *
 */

@Controller
public class RequestTranslateController {
	
	ArticleValidator articleValidator;

	@Autowired private ObjectifyFactory objectifyFactory;
	@Autowired
	public RequestTranslateController(ArticleValidator articleValidator){
			
		this.articleValidator = articleValidator;
		System.out.println("Consumer_key is completed");
		
	}
		
	
	// translation request
	@RequestMapping(value="/request", method=RequestMethod.POST)
	@ResponseBody
    public String request_requestTranslate(@ModelAttribute("command") TransRequest transrequest, BindingResult result ) {
		Objectify ofy = objectifyFactory.begin();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		
		if(ofy.query(TransRequest.class).filter("requester", user.getKey()).filter("aid", transrequest.getAid()).count() > 0)
			return "0";
		
		transrequest.setRequester(user.getKey());
		transrequest.setCreated_at(new Date());

		// Language detection
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
	
	
	// translation request cancel
	@RequestMapping(value="/cancelrequest/{rid}", method=RequestMethod.GET)
	@ResponseBody
    public String request_cancleRequestTranslate(@PathVariable long rid) {
		Objectify ofy = objectifyFactory.begin();

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
	
	
}
