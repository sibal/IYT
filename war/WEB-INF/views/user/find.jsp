<%@ page import="iyt.models.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.ocpsoft.pretty.time.PrettyTime" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% @SuppressWarnings("unchecked") 
   User user = (User)request.getAttribute("user");
   PrettyTime p = new PrettyTime();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>IYOU Translate: Live Feed</title>
	<link rel="StyleSheet" href="/css/default.css" type="text/css"/>
	<link rel="StyleSheet" href="/css/findusers.css" type="text/css"/>
	<script language="javascript" src="/js/jquery17.js"></script>
	<script language="javascript" src="/js/common.js"></script>
	<script language="javascript" src="/js/jquery.form.js"></script>
</head>
	
<script type="text/javascript">

function findbyname(name)
{

	var results = "";

	$.getJSON('/search.name?name='+name, function(data) {
  		
  		 $.each(data.users,function(i,user){
				
			results += '<div id="userCardBox">'+
			'<div id="profilePic"><img src="img/profile_90_90.gif" width="48" height="48" alt="profile" /></div>'+
			'<div id="profileText"><span class="content_id">johnniek7</span> <span class="content_name"> 조형욱</span></div>'+
			'<div id="profileDetail_translation"><span class="content_detail1">Translations </span><span class="content_detail2">KOR/ENG </span><span class="content_detail3">326 </span><span class="content_detail2">ENG/JPN </span><span class="content_detail3">14 </span></div>'+
			'<div id="profileDetail_interest"><span class="content_detail1">Interests </span><span class="content_detail2">books, family, funny, movie </span><span class="content_detail1">Total Rank-ups </span><span class="content_detail3">327 </span></div>'+
			'<div class="fanButtonArea" style="width:62px; background-image:url(img/btn_add_fan.gif); background-repeat:no-repeat; padding:7px 0 0 4px">&nbsp;&nbsp;Add</div>'+
			'<div class="fanButtonArea"><img src="img/btn_more_menu.gif" width="40" height="27" alt="more" /></div>'+
			'<div class="hideX"><img src="img/hide_x.gif" width="12" height="12" alt="hide" /></div>'+
		'</div>';							
																
		 });
		 
		 $(results).insertAfter("#mainContent");	  	

	});

}
</script>


<body class="twoColFixRtHdr">

	<div id="container">
	
		<!-- 헤더 -->
		<div id="header">
			<a href="5.html"><img src="img/logo_top_e.jpg" width="228" height="93" alt="Twitlator" style="margin-left:15px; border:none; vertical-align:middle; font-family: Arial, Helvetica, verdana, sans-serif;" /></a>
			<input type="text" name="searchbar_top" class="searchBar" />
			<span class="menuText"><a href="5.html">Live Feed</a></span><img src="img/menu_dv.gif" width="32" height="93" border="0" style="vertical-align:middle"/><span class="menuText"><a href="9.html">Contribute as Twitlator</a></span><img src="img/menu_dv.gif" width="32" height="93" border="0" style="vertical-align:middle"/><span class="menuText"><a href="10.html">Find Twitlator</a></span><img src="img/menu_dv.gif" width="32" height="93" border="0" style="vertical-align:middle"/><span class="menuText"><a href="#" onclick="showHideDiv()">Account ▾</a></span>
		</div><!-- end #header -->

		<!-- 오른쪽 사이드바 -->
		<div id="sidebar1">
			
			<p class="side_title">You are a Fan of</p>
				<div class="fanOf">
					<img src="img/profile_90_90.gif" width="24" height="24" style="margin-right:3px" /><img src="img/profile_48_1.gif" width="24" height="24" style="margin-right:3px" /><img src="img/profile_48_2.gif" width="24" height="24" style="margin-right:3px" /><img src="img/profile_48_4.gif" width="24" height="24" style="margin-right:3px" /><img src="img/profile_48_5.gif" width="24" height="24" style="margin-right:3px" /><img src="img/profile_48_6.gif" width="24" height="24" style="margin-right:3px" /><img src="img/profile_48_7.gif" width="24" height="24" style="margin-right:3px" /><img src="img/profile_48_8.gif" width="24" height="24" style="margin-right:3px" /><img src="img/profile_48_9.gif" width="24" height="24" style="margin-right:3px" /><img src="img/profile_48_3.gif" width="24" height="24" style="margin-right:3px" /><img src="img/ten_thumb_7.gif" width="24" height="24" style="margin-right:3px" /><img src="img/ten_thumb_1.gif" width="24" height="24" alt="profile" />
				</div>
				<br /><p class="side_title"><img src="img/table_line_f.gif" width="350" height="2" /></p>
			
			<p class="side_title">Popular Mayors</p>
				<!--mayor 추천 1-->
				<div id="popularMayors_profilePic"><img src="img/ten_thumb_9.gif" width="24" height="24" alt="profile" /></div>
				<div id="popularMayors_info">
					<div id="popularMayors_idName">abbakoo</div>
					<div id="popularMayors_details">KOR/ENG, 245 Fans</div>
				</div>
				<!--mayor 추천 2-->
				<div id="popularMayors_profilePic"><img src="img/ten_thumb_1.gif" width="24" height="24" alt="profile" /></div>
				<div id="popularMayors_info">
					<div id="popularMayors_idName">seyong</div>
					<div id="popularMayors_details">ENG/FRN, 334 Fans</div>
				</div> <br /><br />
				<!--mayor 추천 3-->
				<div id="popularMayors_profilePic"><img src="img/ten_thumb_5.gif" width="24" height="24" alt="profile" /></div>
				<div id="popularMayors_info">
					<div id="popularMayors_idName">mdesign</div>
					<div id="popularMayors_details">KOR/CHN, 1,045 Fans</div>
				</div>
				<!--mayor 추천 4-->
				<div id="popularMayors_profilePic"><img src="img/ten_thumb_4.gif" width="24" height="24" alt="profile" /></div>
				<div id="popularMayors_info">
					<div id="popularMayors_idName">af_gtr</div>
					<div id="popularMayors_details">JPN/ENG, 915 Fans</div>
				</div> <br /><br />
				<!--mayor 추천 5-->
				<div id="popularMayors_profilePic"><img src="img/profile_48_7.gif" width="24" height="24" alt="profile" /></div>
				<div id="popularMayors_info">
					<div id="popularMayors_idName">stargate88</div>
					<div id="popularMayors_details">KOR/ENG, 675 Fans</div>
				</div>
				<!--mayor 추천 6-->
				<div id="popularMayors_profilePic"><img src="img/profile_48_6.gif" width="24" height="24" alt="profile" /></div>
				<div id="popularMayors_info">
					<div id="popularMayors_idName">oisoo</div>
					<div id="popularMayors_details">JPN/RUS, 466 Fans</div>
				</div> 

				<br /><br /><div style="height:10px"></div>
        
			<p class="side_title">Sponsored Users</p>
				<div id="popularMayors_profilePic"><img src="img/profile_48_0.gif" width="24" height="24" alt="profile" /></div>
				<div id="popularMayors_info">
				<div id="popularMayors_idName">YBM Sisa </div>
				<div id="popularMayors_details">KOR/ENG, 675 Fans</div>
				</div>
				<div id="popularMayors_info">
				<div id="popularMayors_idName">영어교육의 최강자, YBM시사!</div>
				<div id="popularMayors_details"><a href="http://www.ybmsisa.com">http://www.ybmsisa.com</a></div>
				</div> 

				<br /><br /><div style="height:10px"></div>
        
			<p class="side_title">Invite Friends to Twitlator</p>
				<p class="side_detail"><div id="inviteFriendsBar"><input type="text" name="find_twitlator_bar" class="inviteFriends_Bar" value="Your friend's email address"/></div>
				<div id="inviteFriendsSend"><a href="#">invite</a></div></p>
        
		<br /><br /><br /><div style="height:20px"><img src="img/table_line_f.gif" width="350" height="2" /></div>
        
			<p class="side_detail"><a href="#">About</a> <a href="#">News</a> <a href="#">Help</a> <a href="#">Blog</a> <a href="#">Contact</a> <a href="#">Terms</a> <a href="#">Privacy</a><br />
			<a href="#">Developers</a> (c) 2011 I&amp;YOU Translate</p>
        
        
			<!-- hide/show 메뉴 -->
			<div id="hideShowMenu" class="hideShowMenuStyle" style="visibility:hidden">
				<div id="hsm_pic"><img src="img/profile_90_90.gif" width="20" height="20" /></div>
				<div id="hsm_id">johnniek7</div>
				<div id="hsm_menu" style="margin: 21px 0 0 23px"><a href="12.html">Account Settings</a></div>
				<div id="hsm_menu" style="margin: 13px 0 0 23px">Help Center</div>
				<div id="hsm_menu" style="margin: 13px 0 0 23px"><a href="1-edit.html">Log Out</a></div>
			</div>

		</div><!-- end #sidebar1 -->
		
		<div id="mainContent">
			<p class="side_title">Search for the Twitlators </p>
			<div id="timelineSubMenu_nonselect" style="width:auto"><p class="subMenuText">Browse by</p></div>
			<div id="timelineSubMenu"><p class="menuText">Language</p></div>
			<div id="timelineSubMenu_nonselect" style="width:90"><p class="menuText"><a href="#">Interests</a></p></div>
			<div id="timelineSubMenu_nonselect" style="width:80"><p class="menuText"><a href="#">Rank-ups</a></p></div>
			<div id="timelineSubMenu_nonselect" style="width:130"><p class="menuText"><a href="#">Smart Suggestion</a></p></div>
			<div id="findTwitlatorBar"><input type="text" name="find_twitlator_bar" class="findTwitlator_Bar" /></div>
			<div id="findTwitlatorSend"><a href="#">search</a></div>
		</div><!-- end #mainContent -->


		<br />
	</div><!-- end #container -->
</body>
</html>
