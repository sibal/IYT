<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div id="profilePic"><img src="img/profile_90_90.gif" width="48" height="48" alt="profile" /></div>
<div id="profileIdNameDiv">
	<span class="profile_id"><sec:authentication property="principal.nick"/></span> 
	<span class="profile_name"><sec:authentication property="principal.name"/></span>
</div>
<div id="editProfileDiv"><span class="edit_profile"><a href="#">edit profile</a></span></div><p><br /><br /></p>
<div id="statArea">
   <div id="statDetail_number">365</div>
   <div id="statDetail_number">189</div>
   <div id="statDetail_number">16</div>
   <div id="statDetail_number">264</div>
   <div id="statDetail_text">in Korean</div>
   <div id="statDetail_text">in English</div>
   <div id="statDetail_text">others</div>
   <div id="statDetail_text">Fans</div>
</div>

<br /><p><br /><br /></p>
<p><img src="img/table_line_f.gif" width="350" height="2" /></p>

<p class="side_title">You are a Fan of</p>
	<div class="fanOf"><img src="img/profile_90_90.gif" width="24" height="24" style="margin-right:3px" /><img src="img/profile_48_1.gif" width="24" height="24" style="margin-right:3px" /><img src="img/profile_48_2.gif" width="24" height="24" style="margin-right:3px" /><img src="img/profile_48_4.gif" width="24" height="24" style="margin-right:3px" /><img src="img/profile_48_5.gif" width="24" height="24" style="margin-right:3px" /><img src="img/profile_48_6.gif" width="24" height="24" style="margin-right:3px" /><img src="img/profile_48_7.gif" width="24" height="24" style="margin-right:3px" /><img src="img/profile_48_8.gif" width="24" height="24" style="margin-right:3px" /><img src="img/profile_48_9.gif" width="24" height="24" style="margin-right:3px" /><img src="img/profile_48_3.gif" width="24" height="24" style="margin-right:3px" /><img src="img/ten_thumb_7.gif" width="24" height="24" style="margin-right:3px" /><img src="img/ten_thumb_1.gif" width="24" height="24" alt="profile" /></div>
<br /><br />

<p class="side_title">Recent Translations</p>
	<p class="side_content"><span class="side_content_smaller" style="color:#8aac00">Just now:</span> 메르세데스-벤츠코리아는 신형 C클래스를 오늘 공개..<br />
	<span class="side_content_smaller" style="color:#8aac00">17 mins ago:</span> 매각 절차를 밟고 있는 7개 저축은행 인수전에..<br />
	<span class="side_content_smaller" style="color:#8aac00">29 JUN:</span> The M Festival is going to take place from 23-26..<br />
	<span class="side_content_smaller" style="color:#8aac00">25 JUN:</span> Class is almost in session: The New York office..</p>

	<div style="height:10px"></div>

<p class="side_title">Your Translation Requests</p>
	<p class="side_content"><span class="side_content_smaller" style="color:#888">Waiting</span> President Obama, during a week's traveling..<br />
	<span class="side_content_smaller" style="color:#8aac00">Done</span> Class is almost in session: The New York office..<br />
	<span class="side_content_smaller" style="color:#8aac00">Done </span> 벨기에 앤트워프예술학교에서 새롭게 제시하는 패션..</p>

<br />

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
