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
public class TranslateController {
	
	ArticleValidator articleValidator;

	final int NUMBEROFMAYORS = 3;

	@Autowired private ObjectifyFactory objectifyFactory;
	@Autowired
	public TranslateController(ArticleValidator articleValidator){
			
		this.articleValidator = articleValidator;
		System.out.println("Consumer_key is completed");
		
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
				
		Translation f = ofy.query(Translation.class).filter("author", author.getKey()).filter("sid", translation.getSid()).get();
		if (f!=null)
		{
			// 기존에 번역이 있는 글에 동일 사람이 다른 번역을 달 경우에 처리
			// 기존의 voting 정보를 모두 삭제
			author.setVoting(author.getVoting()-f.getVoting());
			Mayors m = ofy.query(Mayors.class).filter("mayor", author.getKey()).filter("userid", translation.getUserid()).get();
			m.setSumofvotes(m.getSumofvotes()-f.getVoting());
			ofy.delete(f);
			ofy.put(m);
			ofy.put(author);
		}
		
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

			
	/**
	 * 해석 글들 중에 voting 순서로 4개를 뽑아서 보여줌.
	 * @param sid
	 * @return
	 */
	@RequestMapping(value="/topfour/{sid}", method=RequestMethod.GET)
    public ResponseEntity<String> findTranslationTopFour(@PathVariable String sid) {
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
	

	/**
	 * 사용자가 특정 글을 해석한 결과가 있으면 리턴함.
	 * @param sid
	 * @return
	 */
	@RequestMapping(value="/translation/{sid}", method=RequestMethod.GET)
    public ResponseEntity<String> findTranslationBySID(@PathVariable String sid) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		Objectify ofy = objectifyFactory.begin();
		System.out.println("call!");
		String result="";
		String real_result="";
		Translation t = ofy.query(Translation.class).filter("author", user.getKey()).filter("sid", sid).get();
		if (t==null)
			result= "{\"result\":\"0\"}";
		else
		{
			result = "{\"result\":\"1\", ";
			result += "\"sid\":\""+sid+"\",";
			result += "\"t_content\":\"";
			result += t.getT_content();
			result += "\"}";
		}
		
		try{
			real_result = new String(result.getBytes("utf-8"), "utf-8");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		HttpHeaders responseHeaders = new HttpHeaders(); 
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		responseHeaders.add("Content-Length", ""+real_result.getBytes().length);
		return new ResponseEntity<String>(real_result, responseHeaders, HttpStatus.CREATED); 
						
    }
		
	
}
