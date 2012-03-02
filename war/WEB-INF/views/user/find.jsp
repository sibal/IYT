<%@ page import="iyt.models.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.ocpsoft.pretty.time.PrettyTime" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% @SuppressWarnings("unchecked") 
   	User user = (User)request.getAttribute("user");
   	List<Followship> followees = (List<Followship>)request.getAttribute("followees");
   	List<User> popular = (List<User>)request.getAttribute("popular");
   	List<String> maxTran = (List<String>)request.getAttribute("maxTran");
   	
   	PrettyTime p = new PrettyTime();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>IYOU Translate: Live Feed</title>
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
			'<div id="profilePic"><img src="'+user.profile_image_url+'" width="48" height="48" alt="profile" /></div>'+
			'<div id="profileText"><span class="content_id">'+user.username+'</span> <span class="content_name">'+user.name+'</span></div>'+
			'<div id="profileDetail_translation"><span class="content_detail1">Translations </span><span class="content_detail2">'+user.firstTran+'</span><span class="content_detail3">'+user.firstTranNum;
			if (user.secondTran != '')
				results += ' </span><span class="content_detail2">'+user.secondTran+' </span><span class="content_detail3">'+user.secondTranNum+' </span></div>';
			else
				results += ' </span><span class="content_detail2">  </span><span class="content_detail3">  </span></div>';
				
			
			results += '<div id="profileDetail_interest"><span class="content_detail1">Interests </span><span class="content_detail2"> '+user.interests_str+' </span></div>';
			
			if (user.isMyFriend == 1)
			{
				results += '<div class="fanButtonArea" id="befanbutton"  onClick="cancelFan(\''+user.username+'\')" style="width:62px; background-image:url(img/btn_already_fan.gif); background-repeat:no-repeat; padding:7px 0 0 4px">&nbsp;&nbsp;</div>';
				results += '<div class="fanButtonArea"><img src="img/btn_more_menu.gif" width="40" height="27" alt="more" /></div>';
			}
			else
			{
				results += '<div class="fanButtonArea" id="befanbutton" style="width:62px; background-image:url(img/btn_add_fan.gif); background-repeat:no-repeat; padding:7px 0 0 4px" >&nbsp;&nbsp;<a href="#" onclick="befan(\''+user.username+'\')">Add</a></div>';
				results += '<div class="fanButtonArea"><img src="img/btn_more_menu.gif" width="40" height="27" alt="more" /></div>';
			}
			results += '<div class="hideX"><img src="img/hide_x.gif" width="12" height="12" alt="hide" /></div>'+'</div>';							
																
		 });
		 
		 $(results).insertAfter("#mainContent");	  	

	});

}


function befan(id)
{
	id=Base64.encode(id);
	
	$.ajaxSetup({
		cache:false
	});
	
	$.getJSON('/befan/'+id, function(data) {
  	 		 	 		
  	 	if (data.success == 0)
  	 	 	alert("Being a fan is failed");
  	 	else
  	 	{
  	 		$('#befanbutton').html("");
  	 		$('#befanbutton').css('background-image', 'url(/img/btn_already_fan.gif)');
  	 		$('#befanbutton').bind('click', function() { cancelFan(Base64.decode(id)); });  	 			
  	 	}

	});
}


function cancelFan(id)
{


	id=Base64.encode(id);
	
	$.ajaxSetup({
		cache:false
	});
	
			
	
	$.getJSON('/cancelfan/'+id, function(data) {
  	 		 	 		
  	 	if (data.success == 0)
  	 	 	alert("Being a fan is failed");
  	 	else
  	 	{
  	 		$('#befanbutton').css('background-image', 'url(/img/btn_add_fan.gif)');
  	 		$('#befanbutton').html("&nbsp;&nbsp;<a href=\"#\" onclick=\"befan(\'"+Base64.decode(id)+"\')\">Add</a>");
  	 		$('#befanbutton').unbind('click');
  	 		
  	 	}

	});
}


function sendInvite(email)
{

	email = Base64.encode(email);
	$.ajaxSetup({
		cache:false
	});
	
	$.getJSON('/invite/'+email, function(data) {
  	 		 	 		
  	 	if (data.success == 0)
  	 	 	alert("Inviting is failed");
  	 	else
  	 	{
  	 		alert("Successfully sended!");
  	 	}

	});

}

</script>


<body class="twoColFixRtHdr">

	<div id="container">
	
		<!-- 헤더 -->
		<div id="header">
			<a href="/"><img src="img/logo_top_e.jpg" width="228" height="93" alt="Twitlator" style="margin-left:15px; border:none; vertical-align:middle; font-family: Arial, Helvetica, verdana, sans-serif;" /></a>
			<input type="text" name="searchbar_top" class="searchBar" />
			<span class="menuText"><a href="/">Live Feed</a></span><img src="img/menu_dv.gif" width="32" height="93" border="0" style="vertical-align:middle"/><span class="menuText"><a href="/contributelive">Contribute as Twitlator</a></span><img src="img/menu_dv.gif" width="32" height="93" border="0" style="vertical-align:middle"/><span class="menuText"><a href="/findUsers">Find Twitlator</a></span><img src="img/menu_dv.gif" width="32" height="93" border="0" style="vertical-align:middle"/><span class="menuText"><a href="#" onclick="showHideDiv()">Account ▾</a></span>
		</div><!-- end #header -->

		<!-- 오른쪽 사이드바 -->
		<div id="sidebar1">
			
			<% if (followees.size() != 0) {%>
			<p class="side_title">You are a Fan of</p>
				<div class="fanOf">
					
					<% for(Followship f : followees) { %>
			
						<img src="img/default_profile.jpg" width="24" height="24" style="margin-right:3px" />
			
					<% } %>
				</div>
			<br /><br />
			<% } %>
			
				
			
			<p class="side_title">Popular Mayors</p>
				<!--mayor 추천 1-->
				<% int index=0; %>
				<% for(User pp: popular) { %>
				
				<div id="popularMayors_profilePic"><img src="/img/default_profile.jpg" width="24" height="24" alt="profile" /></div>
				<div id="popularMayors_info">
					<div id="popularMayors_idName"><%= pp.getName() %></div>
					<div id="popularMayors_details"><%= maxTran.get(index) %>, <%= pp.getNumFans() %> Fans</div>
				</div>
				
					<% if (index == 1 || index == 3){ %>
						<br /><br />
					<% } %>
				<% index++; %>
				<% } %>
								
				<br /><br /><div style="height:10px"></div>        
       
			<p class="side_title">Invite Friends to Twitlator</p>
				<p class="side_detail"><div id="inviteFriendsBar"><input type="text" name="find_twitlator_bar" id="invite_bar" class="inviteFriends_Bar" value="Your friend's email address"/></div>
				<div id="inviteFriendsSend"><a href="#" onClick="sendInvite($('#invite_bar').val())">invite</a></div></p>
        
		<br /><br /><br /><div style="height:20px"><img src="img/table_line_f.gif" width="350" height="2" /></div>
        
			<p class="side_detail"><a href="#">About</a> <a href="#">News</a> <a href="#">Help</a> <a href="#">Blog</a> <a href="#">Contact</a> <a href="#">Terms</a> <a href="#">Privacy</a><br />
			<a href="#">Developers</a> (c) 2011 I&amp;YOU Translate</p>
        
        
			<!-- hide/show 메뉴 -->
			<div id="hideShowMenu" class="hideShowMenuStyle" style="visibility:hidden">
				<div id="hsm_pic"><img src="img/profile_90_90.gif" width="20" height="20" /></div>
				<div id="hsm_id">johnniek7</div>
				<div id="hsm_menu" style="margin: 21px 0 0 23px"><a href="/account_view">Account Settings</a></div>
				<div id="hsm_menu" style="margin: 13px 0 0 23px">Help Center</div>
				<div id="hsm_menu" style="margin: 13px 0 0 23px"><a href="1-edit.html">Log Out</a></div>
			</div>

		</div><!-- end #sidebar1 -->
		
		<div id="mainContent">
			<p class="side_title">Search for the Twitlators </p>
			<div id="timelineSubMenu_nonselect" style="width:auto"><p class="subMenuText">Browse by</p></div> 
			<div id="timelineSubMenu"><p class="menuText">Language</p></div>
			<div id="timelineSubMenu_nonselect" style="width:90"><p class="menuText"><a href="#">Interests</a></p></div>
			<div id="timelineSubMenu_nonselect" style="width:80"><p class="menuText"><a href="#">Rank-up</a></p></div>
			<div id="timelineSubMenu_nonselect" style="width:130"><p class="menuText"><a href="#">Smart Suggestion</a></p></div>
			
			<div id="findTwitlatorBar"><input type="text" id="findbyname1" name="find_twitlator_bar" class="findTwitlator_Bar" /></div>
			<div id="findTwitlatorSend"><a href="#" onclick="findbyname($('#findbyname1').val());">search</a></div>
		</div><!-- end #mainContent -->


		<br />
	</div><!-- end #container -->
</body>
</html>
