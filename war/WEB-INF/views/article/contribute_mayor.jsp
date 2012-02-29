<%@ page import="iyt.models.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>

<%@ page import="com.ocpsoft.pretty.time.PrettyTime" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% @SuppressWarnings("unchecked") 
   List<Translation> translations = (List<Translation>)request.getAttribute("translations");
   User user = (User)request.getAttribute("user");
   List<TransRequest> requests = (List<TransRequest>)request.getAttribute("requests");
     List<TransRequest> mrequests = (List<TransRequest>)request.getAttribute("mrequests");
   
      List<Translation> recent = (List<Translation>)request.getAttribute("recent");
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


<body class="twoColFixRtHdr">

	<div id="container">
	
		<!-- 헤더 -->
		<div id="header"> <a href="/"><img src="/img/logo_top_e.jpg" width="228" height="93" alt="Twitlator" style="margin-left:15px; border:none; vertical-align:middle; font-family: Arial, Helvetica, verdana, sans-serif;" /></a>
		<input type="text" name="searchbar_top" class="searchBar" />
		<span class="menuText"><a href="/">Live Feed</a></span><img src="/img/menu_dv.gif" width="32" height="93" border="0" style="vertical-align:middle"/><span class="menuText"><a href="/contributelive">Contribute as Twitlator</a></span><img src="/img/menu_dv.gif" width="32" height="93" border="0" style="vertical-align:middle"/><span class="menuText"><a href="/findUsers">Find Twitlator</a></span><img src="/img/menu_dv.gif" width="32" height="93" border="0" style="vertical-align:middle"/><span class="menuText"><a href="#" onclick="showHideDiv()">Account ▾</a></span>
		</div><!-- end #header -->

		<!-- 오른쪽 사이드바 -->
		<div id="sidebar1">
			<p class="side_title">TOP 10 of</p>
			<div class="topTen_row1" style="background-color:#f9f9f9">254 <span class="topTen_row1g">(-)</span></div>
			<div class="topTen_rowp" style="background-color:#f9f9f9"><img src="/img/ten_thumb_0.gif" width="18" height="18" align="absmiddle" /></div>
			<div class="topTen_row2" style="background-color:#f9f9f9">johnniek7</div>
			<div class="topTen_row3" style="background-color:#f9f9f9"><span class="topTen_row3g">356</span> <span class="topTen_row1g">Rank-ups</span></div>

			<div class="topTen_row1" >1 <span class="topTen_row1g">(+2)</span></div>
			<div class="topTen_rowp" ><img src="/img/ten_thumb_10.gif" width="18" height="18" align="absmiddle" /></div>
			<div class="topTen_row2" >YBM Sisa</div>
			<div class="topTen_row3" ><span class="topTen_row3g">11,034</span> <span class="topTen_row1g">Rank-ups</span></div>

			<br />

			<p class="side_title">&nbsp;</p>
			<p class="side_title"><img src="/img/table_line_f.gif" width="290" height="2" /></p>
			

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
	<p class="side_content">
	<% for(TransRequest r : requests) { %>
	<span class="side_content_smaller" style="color:#888">Waiting</span> <%= r.getText() %> <br />
	<% } %>
<br />
<% } %>			

			<div style="height:20px"><img src="/img/table_line_f.gif" width="290" height="2" /></div>
			<p class="side_content"><a href="#">About</a> <a href="#">News</a> <a href="#">Help</a> <a href="#">Blog</a> <a href="#">Contact</a> <a href="#">Terms</a> <a href="#">Privacy</a> <a href="#">Developers</a> (c) 2011 I&amp;YOU Translate</p>
        
			<!-- 숨김메뉴 -->
			<div id="hideShowMenu" class="hideShowMenuStyle" style="visibility:hidden">
				<div id="hsm_pic"><img src="/img/profile_90_90.gif" width="20" height="20" /></div>
				<div id="hsm_id">johnniek7</div>
				<div id="hsm_menu" style="margin: 21px 0 0 23px"><a href="12.html">Account Settings</a></div>
				<div id="hsm_menu" style="margin: 13px 0 0 23px">Help Center</div>
				<div id="hsm_menu" style="margin: 13px 0 0 23px"><a href="1-edit.html">Log Out</a></div>
			</div><!-- end #hideShowMenu -->

		</div><!-- end #sidebar1 -->
 
		<!-- 왼쪽 메인 콘텐츠 -->  
		<div id="mainContent">
			<div id="profilePic"><img src="/img/profile_90_90.gif" width="75" height="75" alt="profile" /></div>
			<div id="profileText"><span class="content_id">johnniek7</span> <span class="content_name"> 조형욱</span></div>
			<div id="profileDetail_translation"><span class="side_content_smaller" style="color:#8aac00">Translations </span><span class="content_detail1">KOR/ENG </span><span class="content_detail2">326 </span><span class="content_detail1">ENG/JPN </span><span class="content_detail2">14 </span></div>
			<div id="profileDetail_interest"><span class="side_content_smaller" style="color:#8aac00">Interests </span><span class="content_detail1">books, family, funny, movie </span><span class="side_content_smaller" style="color:#8aac00">Total Rank-ups </span><span class="content_detail2">327 </span></div>
			<div id="timelineSubMenu_nonselect" style="width:123px"><p class="menuText"><a href="/contributelive">Live Feed: All ▾</a></p></div>
			<div id="timelineSubMenu" style="width:75px"><p class="menuText"><a href="#">Mayors</a></p></div>
			<div id="timelineSubMenu_nonselect" style="width:60px"><p class="menuText"><a href="#">Fans</a></p></div>
		</div><!-- end #mainContent -->
  
		<!-- 모아보기 1 -->
			<div id="articles">
			
				<% for (TransRequest r: mrequests) { %>			
						<div class="qtranslate_type1">
						<form id="<%= r.getAid() %>" name="<%= r.getAid() %>" method=POST>
						<input type=hidden id="sid" name="sid" value="<%= r.getAid() %>" />
						<input type=hidden id="voting" name="voting" value="0" />
						<input type=hidden id="ori_lan" name="ori_lan" value="" />
						<input type=hidden id="username" name="username" value="<%= r.getUsername() %>" />
						<input type=hidden id="userid" name="userid" value="<%= r.getUid() %>" />
						<input type=hidden id="t_lan" name="t_lan" value="" />
						<input type=hidden id="profile_image_url" name="profile_image_url" value="<%= r.getProfile_image_url() %>" />
						<input type=hidden id="ori_content" name="ori_content" value="<%= r.getText() %>" />
						<% if (r.getVender() == 0) { %>
						<img src="/img/fb_ico.gif" width="18" height="18" align="absmiddle" /><span class="qtranslate_id"><%= r.getUsername() %></span><br />
						<input type=hidden id="vender" name="vender" value=0 />
						<% } else { %>
						<img src="/img/tw_ico.gif" width="18" height="18" align="absmiddle" /><span class="qtranslate_id"><%= r.getUsername() %></span><br />
						<input type=hidden id="vender" name="vender" value=1 />						
						<% } %>
						<p class="qtranslate_content"><%= r.getText() %>
						<br /><img src="/img/translate_small.gif" alt="translate" width="21" height="25" align="middle" />
						<input type="text" name="t_content" id="t_content" class="qtranslateBar" />
						<a href="#" onClick="translate('<%= r.getAid() %>')"><img src="/img/translate_small_send.gif" alt="send" width="63" height="24" align="middle" style="border:none"/></a> </p>
						</form>	
						</div>
					
				<% } %>
			</div>
	
		<br />
	</div><!-- end #container -->
</body>

</html>
