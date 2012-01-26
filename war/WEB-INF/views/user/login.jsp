<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
	<title>IYOU Translate: Log In</title> 
	<link rel="StyleSheet" href="/css/default.css" type="text/css"/>
	<link rel="StyleSheet" href="/css/login.css" type="text/css"/>
	
	<script lang="text/javascript">
		function submit() {document.loginform.submit();}
	</script> 
</head> 
 
<body class="twoColFixRtHdr"> 
 
<div id="backgroundSet"> 
	<form commandName="command" name="loginform" action="<c:url value='login_p' />" method="POST">
	<div id="container"> 
		<p><br /><br /></p> 
		<p align="center"> 
		<div id="lableDiv">Email</div>
		<div id="boxDiv"><input type="text" name="j_username" id="email" class="textField" /></div>
		<div id="lableDiv">Password</div>
		<div id="boxDiv"><input type="password" name="j_password" id="password" class="textField" /></div>
		<div id="rememberForgot"><input type="checkbox" name="checkForm" id="rememberMe" /> Remember Me&nbsp; &nbsp;/&nbsp;&nbsp; Forgot Password?</div> 
		
		<div id="swapLoginSignin"><a href="/signup" style="color:#929292">Signup</a></div> 
		<div id="proceed"><input type="submit" style="visibility:hidden"/><a href="javascript:submit();" style="color:#6f7b0a">Log In</a></div> 
		
		<div id="mayorDisplayPic"><img src="img/profile_first.gif" width="62" height="62" /></div> 
		<div id="mayorDisplayID">johnniek7</div> 
		<div id="mayorDisplayLang">한국어/ENG</div> 
		<div id="mayorDisplayLang">▾</div> 
		</p> 
   	</div><!-- end #container -->
   	</form> 
</div><!-- end #backgroundSet --> 
 
<!--bottom language settings --> 
<div style="margin-bottom:4px"><span class="bottomSettingsTitle"> Languages </span><span class="bottomSettings">· English ·</span> <span class="bottomSettingsLinks">Française <span class="bottomSettings"> · </span>Deutsch <span class="bottomSettings"> · </span>Italiano <span class="bottomSettings"> · </span>한국어<span class="bottomSettings"> · </span> Türkçe<span class="bottomSettings"> · </span> Português <span class="bottomSettings"> · </span>Español <span class="bottomSettings"> · </span>中文 <span class="bottomSettings"> · </span>日本語 <span class="bottomSettings"> · </span>Русский</span></div> 
 
<!-- copyrights and addtional menus --> 
<div><span class="bottomSettingsTitle">(c) I&amp;YOU Translate </span><span class="bottomSettings">· </span> <span class="bottomSettingsLinks">About <span class="bottomSettings"> · </span> News  <span class="bottomSettings"> · </span>Help <span class="bottomSettings"> · </span> Blog <span class="bottomSettings"> · </span> Contact <span class="bottomSettings"> · </span> Terms  <span class="bottomSettings"> · </span>Privacy  <span class="bottomSettings"> · </span>Developers <span class="bottomSettings"> · </span> Resources</span></div> 
 
</body> 
</html> 