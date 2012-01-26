<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
<title>IYOU Translate: Sign Up</title> 
<style type="text/css"> 
a:link {text-decoration:none;} 
a:visited {text-decoration:none;} 
a:hover {color:#ffffff; text-decoration:none;} 
a:active {
	text-decoration:none;
	font-family: Arial, Helvetica, sans-serif, Tahoma, Verdana, "맑은 고딕";
	color: #fff;
} 
</style> 
<link rel="StyleSheet" href="/css/signup_adv.css" type="text/css"/>
 
<!-- 상단 Log in 클릭 시 나타나는 hide/show 메뉴 셋팅 --> 
<script language="javascript" src="/js/jquery-1.7.1.js"></script>
<script language="javascript" src="/js/jquery.form.js"></script>
<script language="javascript" src="/js/form.js"></script>
<script language="javascript" type="text/javascript"> 
function showHideDiv()
    {
        var divstyle = new String();
        divstyle = document.getElementById("hideShowMenu").style.visibility;
        if(divstyle.toLowerCase()=="visible" || divstyle == "")
        {
            document.getElementById("hideShowMenu").style.visibility = "hidden";
        }
        else
        {
            document.getElementById("hideShowMenu").style.visibility = "visible";
        }
    }
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}
 
function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}
 
function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}


 
</script> 


</head> 
 
<body class="twoColFixRtHdr" onload="MM_preloadImages('img/hsm_login_on.gif')"> 
 
<div id="container"> 
	<div id="header"> <a href="5.html"><img src="img/logo_top_e.jpg" width="228" height="93" alt="Twitlator" style="margin-left:15px; border:none; vertical-align:middle; font-family: Arial, Helvetica, verdana, sans-serif;" /></a><input type="text" name="searchbar_top" class="searchBar" /><span class="menuText">Already a member?</span><img src="img/menu_dv.gif" width="32" height="93" border="0" style="vertical-align:middle"/><span class="menuText"><a href="#" onclick="showHideDiv()" style="color:#99aa06">Log In ▾</a></span> 
	</div><!-- end #header --> 
    
    <div id="mainTitle"><p class="side_title">Create Account</p> 
    </div><!-- end #mainTitle --> 
	
	
    <div id="mainContent"> 
		<form:form id="signupform" name="signupform" action="/signup_chk" method="POST"> 
		
    	<div id="leftSide"><!-- 맨 왼쪽의 회원가입 항목 --> 
			<p>Full Name</p> 
    		<p>Email</p> 
			<p>Password</p> 
    		<p>Verify Password</p> 
			<p>Nickname</p> 
			<p>&nbsp;</p> 
			<p>Interests</p> 
			<p>&nbsp;</p> 
			<p>&nbsp;</p> 
			<p>&nbsp;</p> 
			<p>&nbsp;</p> 
			<p>&nbsp;</p> 
			<p>&nbsp;</p> 
			<p>&nbsp;</p> 
			<p>Native Language</p> 
			<p>Possible Languages</p> 
    	</div> 
    	
		<div id="centerMain"><!-- 회원가입 입력 영역 --> 
            <p><form:input type="text" path="name" name="signupFullNameForm" id="signupFullName"  class="s_form" /><span id='c_name'/></p> 
            <p><form:input type="text" path="username" name="signupEmailForm" id="signupEmail"  class="s_form" /><span id='c_username'/></p>
            <p><form:input name="signupPasswordForm" path="password" type="password" class="s_form" id="signupPassword" /><span id='c_password'/></p>
            <p><form:input type="password" path="password_c" name="signupPwvForm" id="signupPwv"  class="s_form" /><span id='c_password_c'/></p>
            <p><form:input type="text" path="nick" name="signupNicknameForm" id="signupNickname"  class="s_form" /><span id='c_nick'/></p>
            <p>&nbsp;</p> 
			
			<!-- 관심사 선택 부분 왼쪽 다단 --> 
			<div id="centerMainLeft"> 
				<p><input type="checkbox" name="checkForm" id="interest1" /> Art & Design</p> 
				<p><input type="checkbox" name="checkForm" id="interest3" /> Business</p> 
				<p><input type="checkbox" name="checkForm" id="interest5" /> Deals</p> 
				<p><input type="checkbox" name="checkForm" id="interest7" /> Family</p> 
				<p><input type="checkbox" name="checkForm" id="interest9" /> Food & Drink</p> 
				<p><input type="checkbox" name="checkForm" id="interest11" /> Health</p> 
				<p><input type="checkbox" name="checkForm" id="interest13" /> News</p> 
				<p><input type="checkbox" name="checkForm" id="interest15" /> Sci &amp; Tech</p> 
				<p><input type="checkbox" name="checkForm" id="interest17" /> Travel</p> 
            </div> 
            
			<!-- 관심사 선택 부분 오른쪽 다단 --> 
			<div id="centerMainRight"> 
				<p><input type="checkbox" name="checkForm" id="interest2" /> Books</p> 
				<p><input type="checkbox" name="checkForm" id="interest4" /> Charity</p> 
				<p><input type="checkbox" name="checkForm" id="interest6" /> Entertainment</p> 
				<p><input type="checkbox" name="checkForm" id="interest8" /> Fashion</p> 
				<p><input type="checkbox" name="checkForm" id="interest10" /> Funny</p> 
				<p><input type="checkbox" name="checkForm" id="interest12" /> Music</p> 
				<p><input type="checkbox" name="checkForm" id="interest14" /> Politics</p> 
				<p><input type="checkbox" name="checkForm" id="interest16" /> Sports</p> 
				<p><input type="checkbox" name="checkForm" id="interest18" /> Vehicle</p> 
            </div> 
			
			
			<!-- 언어선택 부분 왼쪽 다단 --> 
			<div id="centerMainLeft_fsb"> 
			
				<!-- Native Language Selection --> 
				<p> 
				<select class="s_select" name="selectLanguage1" size="1"> 
					<option value="selectOne">-- Select Language --</option> 
					<option value="portuguese">Portuguese - Português</option> 
					<option value="italian">Italian - Italiano</option> 
					<option value="spanish">Spanish - Español</option> 
					<option value="turkish">Turkish - Türkçe</option> 
					<option value="english">English</option> 
					<option value="korean">Korean - 한국어</option> 
					<option value="french">French - Française</option> 
					<option value="russian">Russian - Русский</option> 
					<option value="german">German - Deutsch</option> 
					<option value="chinese">Chinese - 中文</option> 
					<option value="japanese">Japanese - 日本語</option> 
				</select> 
				</p> 
				
				<!-- First Possible Language Selection --> 
				<p> 
				<select class="s_select" name="selectLanguage2" size="1"> 
					<option value="selectOne">-- Select Language --</option> 
					<option value="portuguese">Portuguese - Português</option> 
					<option value="italian">Italian - Italiano</option> 
					<option value="spanish">Spanish - Español</option> 
					<option value="turkish">Turkish - Türkçe</option> 
					<option value="english">English</option> 
					<option value="korean">Korean - 한국어</option> 
					<option value="french">French - Française</option> 
					<option value="russian">Russian - Русский</option> 
					<option value="german">German - Deutsch</option> 
					<option value="chinese">Chinese - 中文</option> 
					<option value="japanese">Japanese - 日本語</option> 
				</select> 
				</p> 
				
				<!-- Third Possible Language Selection --> 
				<p> 
				<select class="s_select" name="selectLanguage4" size="1"> 
					<option value="selectOne">-- Select Language --</option> 
					<option value="portuguese">Portuguese - Português</option> 
					<option value="italian">Italian - Italiano</option> 
					<option value="spanish">Spanish - Español</option> 
					<option value="turkish">Turkish - Türkçe</option> 
					<option value="english">English</option> 
					<option value="korean">Korean - 한국어</option> 
					<option value="french">French - Française</option> 
					<option value="russian">Russian - Русский</option> 
					<option value="german">German - Deutsch</option> 
					<option value="chinese">Chinese - 中文</option> 
					<option value="japanese">Japanese - 日本語</option> 
				</select> 
				</p> 
			</div> 
			
			
			<!-- 언어선택 부분 오른쪽 다단 --> 
			<div id="centerMainRight_fsb"> 
			
				<!-- Second Possible Language Selection --> 
				<p> 
				<select class="s_select" name="selectLanguage3" size="1"> 
					<option value="selectOne">-- Select Language --</option> 
					<option value="portuguese">Portuguese - Português</option> 
					<option value="italian">Italian - Italiano</option> 
					<option value="spanish">Spanish - Español</option> 
					<option value="turkish">Turkish - Türkçe</option> 
					<option value="english">English</option> 
					<option value="korean">Korean - 한국어</option> 
					<option value="french">French - Française</option> 
					<option value="russian">Russian - Русский</option> 
					<option value="german">German - Deutsch</option> 
					<option value="chinese">Chinese - 中文</option> 
					<option value="japanese">Japanese - 日本語</option> 
				</select> 
				</p> 
				
				<!-- Forth Possible Language Selection --> 
				<p> 
				<select class="s_select" name="selectLanguage5" size="1"> 
					<option value="selectOne">-- Select Language --</option> 
					<option value="portuguese">Portuguese - Português</option> 
					<option value="italian">Italian - Italiano</option> 
					<option value="spanish">Spanish - Español</option> 
					<option value="turkish">Turkish - Türkçe</option> 
					<option value="english">English</option> 
					<option value="korean">Korean - 한국어</option> 
					<option value="french">French - Française</option> 
					<option value="russian">Russian - Русский</option> 
					<option value="german">German - Deutsch</option> 
					<option value="chinese">Chinese - 中文</option> 
					<option value="japanese">Japanese - 日本語</option> 
				</select> 
				</p> 
			</div> 
			
			
		</div> 
		
		</form:form>
		
		<!-- 우측 도움말 영역 --> 
		<div id="rightSide"> 
			<p class="side_title">Account</p> 
			<p>You can change your basic account information, interested fields, and possible languages. You can change your basic account information, interested fields, and possible languages.</p> 
			<p><img src="img/table_line_f.gif" style="margin:10px 0" width="240" /></p> 
			<p class="side_title">Tips</p> 
			<p>Useful tips for changing your account setting. Followings are useful tips for changing your account settings. Change your account settings here.</p> 
		</div> 
		
		<!-- 상단 Log in 클릭 시 나타나는 hide/show 메뉴 --> 
		<div id="hideShowMenu" class="hideShowMenuStyle" style="visibility:hidden"> 
			<div id="hsm_email">Email</div> 
			<div id="hsm_form"><input type="text" name="signupFullNameForm" id="signupFullName" class="hsm_formArea" /></div> 
			<div id="hsm_password">Password</div> 
			<div id="hsm_form"><input type="text" name="signupFullNameForm" id="signupFullName" class="hsm_formArea" /></div> 
			<div id="hsm_rememberForgot"><input type="checkbox" name="checkForm" id="rememberMe" /> Remember Me&nbsp;/ Forgot Password?</div> 
			<div id="hsm_loginbtn"><a href="5.html" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image9','','img/hsm_login_on.gif',1)"><img src="img/hsm_login.gif" alt="Log In" name="Image9" width="214" height="41" border="0" id="Image9" /></a></div> 
            <div id="hsm_logintext"><a href="5.html" style="color:#6f7b0a">Log In</a></div> 
		</div> 
		
		<!-- 우측 하단의 취소/완료 버튼 영역 --> 
		<div id="cancelChanges"><a href="2.html" style="color:#929292">Cancel</a></div> 
		<div id="saveChanges"><a href="javascript:submit();" style="color:#6f7b0a">Done</a></div> 
    </div><!-- end #mainContent --> 
    
</div><!-- end #container --> 
 
</body> 
</html> 