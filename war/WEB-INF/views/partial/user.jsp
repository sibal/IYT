<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<% @SuppressWarnings("unchecked") 
	List<Followship> followees = (List<Followship>)request.getAttribute("followees");
	List<Translation> recent = (List<Translation>)request.getAttribute("recent");
	List<TransRequest> requests = (List<TransRequest>)request.getAttribute("requests");
%>


<div id="profilePic"><img src="img/default_profile.jpg" width="48" height="48" alt="profile" /></div>
<div id="profileIdNameDiv">
	<span class="profile_id"><sec:authentication property="principal.nick"/></span> 
	<span class="profile_name"><sec:authentication property="principal.name"/></span>
</div>
<div id="editProfileDiv"><span class="edit_profile"><a href="#">edit profile</a></span></div><p><br /><br /></p>
<div id="statArea">
   <div id="statDetail_number"><sec:authentication property="principal.numFirstLan"/></div>
   <div id="statDetail_number"><sec:authentication property="principal.numSecondLan"/></div>
   <div id="statDetail_number"><sec:authentication property="principal.numOtherLan"/></div>
   <div id="statDetail_number"><sec:authentication property="principal.numFans"/></div>
   <div id="statDetail_text">in <sec:authentication property="principal.firstLanguage"/></div>
   <div id="statDetail_text">in <sec:authentication property="principal.secondLanguage"/></div>
   <div id="statDetail_text">others</div>
   <div id="statDetail_text">Fans</div>
</div>

<br /><p><br /><br /></p>
<p><img src="img/table_line_f.gif" width="350" height="2" /></p>

<% if (followees.size() != 0) {%>
<p class="side_title">You are a Fan of</p>
	<div class="fanOf">
		
		<% for(Followship f : followees) { %>

			<img src="img/default_profile.jpg" width="24" height="24" style="margin-right:3px" />

		<% } %>
	</div>
<br /><br />
<% } %>

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
<div style="height:20px"><img src="img/table_line_f.gif" width="350" height="2" /></div>

<p class="side_content"><a href="#">About</a> <a href="#">News</a> <a href="#">Help</a> <a href="#">Blog</a> <a href="#">Contact</a> <a href="#">Terms</a> <a href="#">Privacy</a><br />
<a href="#">Developers</a> (c) 2011 I&amp;YOU Translate</p>

<!-- hide/show 메뉴 -->
<div id="hideShowMenu" class="hideShowMenuStyle" style="visibility:hidden">
	<div id="hsm_pic"><img src="img/profile_90_90.gif" width="20" height="20" /></div>
	<div id="hsm_id">johnniek7</div>
	<div id="hsm_menu" style="margin: 21px 0 0 23px"><a href="12.html">Account Settings</a></div>
	<div id="hsm_menu" style="margin: 13px 0 0 23px">Help Center</div>
	<div id="hsm_menu" style="margin: 13px 0 0 23px"><a href="1-edit.html">Log Out</a></div>
</div>
