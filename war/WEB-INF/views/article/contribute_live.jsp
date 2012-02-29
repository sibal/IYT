<%@ page import="iyt.models.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>

<%@ page import="com.ocpsoft.pretty.time.PrettyTime" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% @SuppressWarnings("unchecked") 
   List<Translation> translations = (List<Translation>)request.getAttribute("translations");
   User user = (User)request.getAttribute("user");
   TransInformation maxTrans = (TransInformation)request.getAttribute("maxTrans");
   TransInformation secTrans = (TransInformation)request.getAttribute("secTrans");
   List<Translation> recent = (List<Translation>)request.getAttribute("recent");
	List<TransRequest> requests = (List<TransRequest>)request.getAttribute("requests");
   PrettyTime p = new PrettyTime();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>IYOU Translate: Live Feed</title>
	<link rel="StyleSheet" href="/css/contri.css" type="text/css"/>
	<script language="javascript" src="/js/jquery17.js"></script>
	<script language="javascript" src="/js/common.js"></script>
		<script language="javascript" src="/js/jquery.form.js"></script>
	
		<script src="/js/jquery.timeago.js" type="text/javascript"></script>
	
</head>
	
<script type="text/javascript">

	var access = '<%= user.getFace_access() %>';


	function fbFetch(mode, url, url2){
	

	
	  //Set Url of JSON data from the facebook graph api. make sure callback is set with a '?' to overcome the cross domain problems with JSON
      var url = "https://graph.facebook.com/me/home?limit=5&access_token="+access+"&date_format=U&fields=id,from,message,comments,likes&callback=?";
		
      //Use jQuery getJSON method to fetch the data from the url and then create our unordered list with the relevant data.
		<% if (!user.getTwit_authT().equals("") && !user.getFace_access().equals("")) {%>
		
		
				if (mode==0)
		{
			url= "https://graph.facebook.com/me/home?limit=5&access_token="+access+"&date_format=U&fields=id,from,message,comments,likes&callback=?";
			url2= "/t_getTimeline";
		}
		
		
		  var container1 = new Array();
		  var container2 = new Array();
	      $.ajaxSetup({ cache: false }); 
	      $.getJSON(url, function(json){
		    var html = "";
		    //loop through and within data array's retrieve the message variable.

  				$.each(json.data,function(i,fb){

				//				html += "<li>"+"<img src='http://graph.facebook.com/"+fb.from.id+"/picture'> " + fb.from.name+" " +fb.message + "</li>"; 
				//html+= "<div id='articleBox' onClick='showDetail(\""+fb.id+"\");' style='cursor:hand; cursor: pointer;'><div id='profilePic'><img src='http://graph.facebook.com/"+fb.from.id+"/picture' width='48' height='48' alt='profile' /></div><div id='profileText'><span class='content_id'><img src='img/fb_ico.gif' width='18' height='18' align='absmiddle' /> "+fb.from.name+"</span> </div><div id='timelineContent'><span class='timelineContent_normal'>"+fb.message+"</span></div><div id='articleSubMenu'><span class='articleSubMenu_time'>1 minute ago / </span><img src='img/fb_like.gif' width='17' height='15' align='absmiddle' class='textmiddle' /><span class='articleSubMenu_facebook'>Like&nbsp;<img src='img/fb_cmt.gif' width='17' height='15' align='absmiddle' class='textmiddle' />Comment</span>&nbsp;<img src='img/translate.gif' width='17' height='15' align='absmiddle' class='textmiddle' /><span class='articleSubMenu_iyoutranslate'>Translate!</span></div></div>";
				
				container1.push(fb);
								
		    	});

		
			$.getJSON(url2,  function(oo){
				$.each(oo.statuses, function (i,tw){
					container2.push(tw);
				
				});
				
				var count1 = 0;
				var count2 = 0;
				
				for(j=0;j<10;j++)
				{
													
					if (count1 < 5 && (count2 == 5 || new Date(container1[count1].created_time*1000) > new Date(Date.parse(container2[count2].created_at.replace(/(\+\S+) (.*)/, '$2 $1')))))
					{
						html+= 	'<div class="qtranslate_type1">'+
								'<form id=\"'+container1[count1].id+'\" name=\"'+container1[count1].id+'\" method=POST>'+
								'<input type=hidden id="sid" name="sid" value="'+container1[count1].id+'" />'+
								'<input type=hidden id="voting" name="voting" value="0" />'+
								'<input type=hidden id="ori_lan" name="ori_lan" value="" />'+
								'<input type=hidden id="username" name="username" value="'+container1[count1].from.name+'" />'+
								'<input type=hidden id="userid" name="userid" value="'+container1[count1].from.id+'" />'+
								'<input type=hidden id="t_lan" name="t_lan" value="" />'+
								'<input type=hidden id="vender" name="vender" value=0 />'+
								'<input type=hidden id="profile_image_url" name="profile_image_url" value="http://graph.facebook.com/'+container1[count1].from.id+'/picture" />'+
								'<input type=hidden id="ori_content" name="ori_content" value="'+container1[count1].message+'" />'+
								'<img src="img/fb_ico.gif" width="18" height="18" align="absmiddle" /><span class="qtranslate_id">'+container1[count1].from.name+'</span><br />'+
								'<p class="qtranslate_content">'+container1[count1].message+
								'<br /><img src="img/translate_small.gif" alt="translate" width="21" height="25" align="middle" />'+
								'<input type="text" name="t_content" id="t_content" class="qtranslateBar" />'+
								'<a href="#" onClick="translate(\''+container1[count1].id+'\')"><img src="img/translate_small_send.gif" alt="send" width="63" height="24" align="middle" style="border:none"/></a> </p>'+
								'</form>'+	
								'</div>';
						count1++;
					}
					else
					{
					
						html+= 	'<div class="qtranslate_type1">'+
								'<form id=\"'+container2[count2].id_str+'\" method=POST>'+
								'<input type=hidden id="sid" name="sid" value="'+container2[count2].id_str+'" />'+
								'<input type=hidden id="voting" name="voting" value="0" />'+
								'<input type=hidden id="ori_lan" name="ori_lan" value="" />'+
								'<input type=hidden id="username" name="username" value="'+container2[count2].user.name+'" />'+
								'<input type=hidden id="userid" name="userid" value="'+container2[count2].user.id_str+'" />'+
								'<input type=hidden id="t_lan" name="t_lan" value="" />'+
								'<input type=hidden id="vender" name="vender" value=1 />'+
								'<input type=hidden id="profile_image_url" name="profile_image_url" value="'+container2[count2].user.profile_image_url+'" />'+
								'<input type=hidden id="ori_content" name="ori_content" value="'+container2[count2].text+'" />'+
								'<img src="img/tw_ico.gif" width="18" height="18" align="absmiddle" /><span class="qtranslate_id">'+container2[count2].user.name+'</span><br />'+
								'<p class="qtranslate_content">'+container2[count2].text+
								'<br /><img src="img/translate_small.gif" alt="translate" width="21" height="25" align="middle" />'+
								'<input type="text" name="t_content" id="t_content" class="qtranslateBar" />'+
								'<a href="#" onClick="translate(\''+container2[count2].id_str+'\')"><img src="img/translate_small_send.gif" alt="send" width="63" height="24" align="middle" style="border:none"/></a> </p>'+
								'</form>'+
								'</div>';
				
						count2++;
					}
				}	
				
				
				$('#articles').animate({opacity:0}, 500, function(){

$('#nextButton').remove();
					$('#loading').remove();
					$('#articles').html($('#articles').html()+html);
					$('#articles').html($('#articles').html()+"<div class = 'qtranslate_type1' id='nextButton'><a onClick=\"fbFetch(1, '"+json.paging.next.replace(/&callback=[a-z|A-Z|0-9|_|=]+/g,"")+"&callback=?"+"', '/t_getTimeline/"+container2[count2-1].id_str+"')\">next!</a></div>");

			 	});
				$('#articles').animate({opacity:1}, 500);							
			
			});
		});

		<%} else if (!user.getFace_access().equals("")) {%>
		
		if (mode==0)
		{
			url= "https://graph.facebook.com/me/home?limit=10&access_token="+access+"&date_format=U&fields=id,from,message,comments,likes&callback=?";
		}
		
		
		  var container1 = new Array();
	      $.ajaxSetup({ cache: false }); 
	      $.getJSON(url, function(json){
		    var html = "";
		    //loop through and within data array's retrieve the message variable.

  				$.each(json.data,function(i,fb){				
				container1.push(fb);
		    	});

		
				var count1 = 0;
				
				for(j=0;j<10;j++)
				{
						html+= 	'<div class="qtranslate_type1">'+
								'<form id=\"'+container1[count1].id+'\" name=\"'+container1[count1].id+'\" method=POST>'+
								'<input type=hidden id="sid" name="sid" value="'+container1[count1].id+'" />'+
								'<input type=hidden id="voting" name="voting" value="0" />'+
								'<input type=hidden id="ori_lan" name="ori_lan" value="" />'+
								'<input type=hidden id="username" name="username" value="'+container1[count1].from.name+'" />'+
								'<input type=hidden id="userid" name="userid" value="'+container1[count1].from.id+'" />'+
								'<input type=hidden id="t_lan" name="t_lan" value="" />'+
								'<input type=hidden id="vender" name="vender" value=0 />'+
								'<input type=hidden id="profile_image_url" name="profile_image_url" value="http://graph.facebook.com/'+container1[count1].from.id+'/picture" />'+
								'<input type=hidden id="ori_content" name="ori_content" value="'+container1[count1].message+'" />'+
								'<img src="img/fb_ico.gif" width="18" height="18" align="absmiddle" /><span class="qtranslate_id">'+container1[count1].from.name+'</span><br />'+
								'<p class="qtranslate_content">'+container1[count1].message+
								'<br /><img src="img/translate_small.gif" alt="translate" width="21" height="25" align="middle" />'+
								'<input type="text" name="t_content" id="t_content" class="qtranslateBar" />'+
								'<a href="#" onClick="translate(\''+container1[count1].id+'\')"><img src="img/translate_small_send.gif" alt="send" width="63" height="24" align="middle" style="border:none"/></a> </p>'+
								'</form>'+	
								'</div>';
						count1++;						
				}	
				
				$('#articles').animate({opacity:0}, 500, function(){

$('#nextButton').remove();
					$('#loading').remove();
					$('#articles').html($('#articles').html()+html);
					$('#articles').html($('#articles').html()+"<div class='qtranslate_type1' id='nextButton'><a onClick=\"fbFetch(1, '"+json.paging.next.replace(/&callback=[a-z|A-Z|0-9|_|=]+/g,"")+"&callback=?"+"', '')\">next!</a></div>");

			 	});
				$('#articles').animate({opacity:1}, 500);							
			
			});
			
		
		<%} else if (!user.getTwit_authT().equals("")) {%>
		
		if (mode==0)
		{
			url2= "/t_getTimeline";
		}
		
		 $.getJSON(url2,  function(oo){
				$.each(oo.statuses, function (i,tw){
					container2.push(tw);
				
				});
				
				var count2 = 0;
				
				for(j=0;j<5;j++)
				{
				html+= 	'<div class="qtranslate_type1">'+
								'<form id=\"'+container2[count2].id_str+'\" method=POST>'+
								'<input type=hidden id="sid" name="sid" value="'+container2[count2].id_str+'" />'+
								'<input type=hidden id="voting" name="voting" value="0" />'+
								'<input type=hidden id="ori_lan" name="ori_lan" value="" />'+
								'<input type=hidden id="username" name="username" value="'+container2[count2].user.name+'" />'+
								'<input type=hidden id="userid" name="userid" value="'+container2[count2].user.id_str+'" />'+
								'<input type=hidden id="t_lan" name="t_lan" value="" />'+
								'<input type=hidden id="vender" name="vender" value=1 />'+
								'<input type=hidden id="profile_image_url" name="profile_image_url" value="'+container2[count2].user.profile_image_url+'" />'+
								'<input type=hidden id="ori_content" name="ori_content" value="'+container2[count2].text+'" />'+
								'<img src="img/tw_ico.gif" width="18" height="18" align="absmiddle" /><span class="qtranslate_id">'+container2[count2].user.name+'</span><br />'+
								'<p class="qtranslate_content">'+container2[count2].text+
								'<br /><img src="img/translate_small.gif" alt="translate" width="21" height="25" align="middle" />'+
								'<input type="text" name="t_content" id="t_content" class="qtranslateBar" />'+
								'<a href="#" onClick="translate(\''+container2[count2].id_str+'\')"><img src="img/translate_small_send.gif" alt="send" width="63" height="24" align="middle" style="border:none"/></a> </p>'+
								'</form>'+
								'</div>';
						count2++;
				}	
				
				$('#articles').animate({opacity:0}, 500, function(){
$('#nextButton').remove();
					$('#loading').remove();
					$('#articles').html($('#articles').html()+html);
					$('#articles').html($('#articles').html()+"<div class='qtranslate_type1' id='nextButton'><a onClick=\"fbFetch(1, '', '/t_getTimeline/"+container2[count2-1].id_str+"')\">next!</a></div>");
					
			 	});
				$('#articles').animate({opacity:1}, 500);							
			
			});
		
		<% } %>
	};
	
	
	function translate(aid)
	{
		$('#'+aid).ajaxForm();			
			
		var queryString = $('#'+aid).formSerialize(); 
		alert(queryString);
		
		// the data could now be submitted using $.get, $.post, $.ajax, etc 
		$.post('/translate', queryString, function(response){
			    
			//location.href = "/translations";
			alert("Successfully submitted!");
			
		});
			    
	}

</script>


<body class="twoColFixRtHdr" onLoad='fbFetch(0, "", "")'>

	<div id="container">
	
		<!-- 헤더 -->
		<div id="header"> <a href="/"><img src="img/logo_top_e.jpg" width="228" height="93" alt="Twitlator" style="margin-left:15px; border:none; vertical-align:middle; font-family: Arial, Helvetica, verdana, sans-serif;" /></a>
		<input type="text" name="searchbar_top" class="searchBar" />
		<span class="menuText"><a href="/">Live Feed</a></span><img src="img/menu_dv.gif" width="32" height="93" border="0" style="vertical-align:middle"/><span class="menuText"><a href="/contributelive">Contribute as Twitlator</a></span><img src="img/menu_dv.gif" width="32" height="93" border="0" style="vertical-align:middle"/><span class="menuText"><a href="/findUsers">Find Twitlator</a></span><img src="img/menu_dv.gif" width="32" height="93" border="0" style="vertical-align:middle"/><span class="menuText"><a href="#" onclick="showHideDiv()">Account ▾</a></span>
		</div><!-- end #header -->

		<!-- 오른쪽 사이드바 -->
		<div id="sidebar1">
			<p class="side_title">TOP 10 of</p>
			<div class="topTen_row1" style="background-color:#f9f9f9">254 <span class="topTen_row1g">(-)</span></div>
			<div class="topTen_rowp" style="background-color:#f9f9f9"><img src="img/ten_thumb_0.gif" width="18" height="18" align="absmiddle" /></div>
			<div class="topTen_row2" style="background-color:#f9f9f9">johnniek7</div>
			<div class="topTen_row3" style="background-color:#f9f9f9"><span class="topTen_row3g">356</span> <span class="topTen_row1g">Rank-ups</span></div>

			<div class="topTen_row1" >1 <span class="topTen_row1g">(+2)</span></div>
			<div class="topTen_rowp" ><img src="img/ten_thumb_10.gif" width="18" height="18" align="absmiddle" /></div>
			<div class="topTen_row2" >YBM Sisa</div>
			<div class="topTen_row3" ><span class="topTen_row3g">11,034</span> <span class="topTen_row1g">Rank-ups</span></div>

			<br />

			<p class="side_title">&nbsp;</p>
			<p class="side_title"><img src="img/table_line_f.gif" width="290" height="2" /></p>
			

<% if (recent.size() != 0) { %>
<p class="side_title">Recent Translations</p>
	<p class="side_content" id="recentContent">
		<script>
	var a = "";
	var b = "";
	<% for(Translation t : recent) { %>
	a=jQuery.timeago(new Date(<%= t.getCreated_at().getTime() %>));
	b += '<span class="side_content_smaller" style="color:#8aac00" >'+a+'</span> <%= StringEscapeUtils.escapeJavaScript(t.getT_content()) %><br />';
	<% } %>
	$('#recentContent').html(b);
	</script>

	<div style="height:10px"></div>
<% } %>
<% if (requests.size() != 0) { %>
<p class="side_title">Your Translation Requests</p>
	<p class="side_content" id="requestContent">
	<script>
	var c = "";
	var d = "";
	<% for(TransRequest r : requests) { %>
	c=jQuery.timeago(new Date(<%= r.getCreated_at().getTime() %>));
	d += '<span class="side_content_smaller" style="color:#8aac00" >'+c+'</span> <%= StringEscapeUtils.escapeJavaScript(r.getText()) %><br />';
	<% } %>
	
	$('#requestContent').html(d);
	
	</script>
<br />
<% } %>			

			<div style="height:20px"><img src="img/table_line_f.gif" width="290" height="2" /></div>
			<p class="side_content"><a href="#">About</a> <a href="#">News</a> <a href="#">Help</a> <a href="#">Blog</a> <a href="#">Contact</a> <a href="#">Terms</a> <a href="#">Privacy</a> <a href="#">Developers</a> (c) 2011 I&amp;YOU Translate</p>
        
			<!-- 숨김메뉴 -->
			<div id="hideShowMenu" class="hideShowMenuStyle" style="visibility:hidden">
				<div id="hsm_pic"><img src="img/default_profile.jpg" width="20" height="20" /></div>
				<div id="hsm_id"><%= user.getName() %></div>
				<div id="hsm_menu" style="margin: 21px 0 0 23px"><a href="12.html">Account Settings</a></div>
				<div id="hsm_menu" style="margin: 13px 0 0 23px">Help Center</div>
				<div id="hsm_menu" style="margin: 13px 0 0 23px"><a href="1-edit.html">Log Out</a></div>
			</div><!-- end #hideShowMenu -->

		</div><!-- end #sidebar1 -->
 
		<!-- 왼쪽 메인 콘텐츠 -->  
		<div id="mainContent">
			<div id="profilePic"><img src="img/default_profile.jpg" width="75" height="75" alt="profile" /></div>
			<div id="profileText"><span class="content_id"><%= user.getName() %></span> <span class="content_name"> <%= user.getNick() %></span></div>
			<div id="profileDetail_translation"><span class="side_content_smaller" style="color:#8aac00">Translations </span>
			<% if (maxTrans != null) { %>
			<span class="content_detail1"><%= maxTrans.getSource().name %>/<%= maxTrans.getDest().name%> </span>
			<span class="content_detail2"><%= maxTrans.getNum() %> </span>
			<% } %>
			<% if (secTrans != null) { %>
			<span class="content_detail1"><%= secTrans.getSource().name %>/<%= secTrans.getSource().name %> 
			</span><span class="content_detail2"><%= secTrans.getNum() %> </span>
			<% } %>
			</div>
			<div id="profileDetail_interest"><span class="side_content_smaller" style="color:#8aac00">Interests </span><span class="content_detail1">books, family, funny, movie </span><span class="side_content_smaller" style="color:#8aac00">Total Rank-ups </span><span class="content_detail2">327 </span></div>
			<div id="timelineSubMenu" style="width:123px"><p class="menuText">Live Feed</p></div>
			<div id="timelineSubMenu_nonselect" style="width:75px"><p class="menuText"><a href="/contributelive/mayor">Mayors</a></p></div>
			<div id="timelineSubMenu_nonselect" style="width:60px"><p class="menuText"><a href="#">Fans</a></p></div>
		</div><!-- end #mainContent -->
  
		<!-- 모아보기 1 -->
			<div id="articles">
			<div id="loading"><p align=center ><img src="/img/loading.gif" /></p></div>
			</div>
	
		<br />
	</div><!-- end #container -->
</body>

</html>
