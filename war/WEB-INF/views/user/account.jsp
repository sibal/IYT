<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="iyt.enums.*" %>
<%@ page import="iyt.models.*" %>


<% @SuppressWarnings("unchecked") 
   
   	User user = (User)request.getAttribute("user");
   	String result = (String)request.getAttribute("result");

%>


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
<script language="javascript" type="text/javascript">

<% if (result != "true") { %>
	alert('Password error!');
<% } %>
 
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


function updateInterest()
{
	var interests = "";
	$.each($('.i'), function() {
		if(this.checked)
		{
			interests += ""+this.value+"/";
		}
	});

	document.forms["signupform"].interests_str.value=interests;
}

function submit() {
    $('#signupform').submit();
}
 

</script> 


</head> 
 
<body class="twoColFixRtHdr" onload="MM_preloadImages('img/hsm_login_on.gif')"> 
 <!-- Facebook api -->
	<div id="fb-root"></div>
	<script type="text/javascript">
		window.fbAsyncInit = function() {
			FB.init({appId: '125733630772899', status: true, cookie: true, xfbml: true});

			FB.Event.subscribe('auth.login', function(response) {
				login();
			});
			FB.Event.subscribe('auth.logout', function(response) {
				logout();
			});


		};
		(function() {
			var e = document.createElement('script');
			e.type = 'text/javascript';
			e.src = document.location.protocol +
				'//connect.facebook.net/en_US/all.js';
			e.async = true;
			document.getElementById('fb-root').appendChild(e);
		}());

		function login(){
			FB.api('/me', function(response) {
			
				FB.getLoginStatus(function(response) {
				var accessToken = response.authResponse.accessToken;
				var userid = response.authResponse.userID;
				document.forms["signupform"].face_access.value = accessToken;
				document.forms["signupform"].fid.value = userid;
				alert("Successfully registered!");
				
			});
			});
		}
		function logout(){

		}
		function greet(){
			FB.api('/me', function(response) {
				alert('Welcome, '+response.name+"!");
			});
		}

	</script>
 

<div id="container"> 
	<div id="header"> <a href="5.html"><img src="img/logo_top_e.jpg" width="228" height="93" alt="Twitlator" style="margin-left:15px; border:none; vertical-align:middle; font-family: Arial, Helvetica, verdana, sans-serif;" /></a><input type="text" name="searchbar_top" class="searchBar" /><span class="menuText">Already a member?</span><img src="img/menu_dv.gif" width="32" height="93" border="0" style="vertical-align:middle"/><span class="menuText"><a href="#" onclick="showHideDiv()" style="color:#99aa06">Log In ▾</a></span> 
	</div><!-- end #header --> 
    
    <div id="mainTitle"><p class="side_title">Create Account</p> 
    </div><!-- end #mainTitle --> 
	
	
    <div id="mainContent"> 
		<form id="signupform" name="signupform" action="/account_edit" method="POST"> 
		<fb:login-button autologoutlink='true' perms='email,user_birthday,status_update,publish_stream,read_stream'></fb:login-button>
    	<div id="leftSide"><!-- 맨 왼쪽의 회원가입 항목 --> 
    		<p>Original Password</p> 
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
	  	    <p><input name="opassword" type="password" class="s_form" id="opassword" value=""/></p>
            <p><input name="password" type="password" class="s_form" id="password" value="" /></p>
            <input type="hidden" name="interests_str" id="interests_str" value="" />
            <input type="hidden" name="twit_authT" id="twit_authT" value="" />
            <input type="hidden" name="twit_authTS" id="twit_authTS" value="" />
            <p><input type="password" name="password_c" id="password_c"  class="s_form" value="" /></p>
            <p><input type="text" name="nick" id="nick"  class="s_form" value="<%= user.getNick() %>" /></p>
            <input type="hidden" name="face_access" id="face_access" value="" />
            <input type="hidden" name="fid" id="fid" value="" />
            <p>&nbsp;</p> 
			
			<!-- 관심사 선택 부분 왼쪽 다단 --> 
			<div id="centerMainLeft">
				<% for(Interest i: Interest.values()) { %> 
					<% if(i.num % 2 ==1){ %>
					
					<% if (user.getInterests().contains(i)) {%>	
						<p><input type="checkbox" class="i" id="interest<%= i.num %>" checked="true" value="<%= i.num %>" name="interest<%= i.num %>" onClick="updateInterest()" /><%= i.name %></p>
				
					<% } else { %>
						<p><input type="checkbox" class="i" id="interest<%= i.num %>" value="<%= i.num %>" name="interest<%= i.num %>" onClick="updateInterest()" /><%= i.name %></p>
					
					<% } %>
					<% } %>
				<% } %> 
            </div> 
            
			<!-- 관심사 선택 부분 오른쪽 다단 --> 
			<div id="centerMainRight"> 
				<% for(Interest i: Interest.values()) { %> 
					<% if(i.num % 2 ==0){ %>

					<% if (user.getInterests().contains(i)) {%>	
						<p><input type="checkbox" class="i" id="interest<%= i.num %>" checked="true" value="<%= i.num %>" name="interest<%= i.num %>" onClick="updateInterest()" /><%= i.name %></p>
				
					<% } else { %>
						<p><input type="checkbox" class="i" id="interest<%= i.num %>" value="<%= i.num %>" name="interest<%= i.num %>" onClick="updateInterest()" /><%= i.name %></p>
					
					<% } %>
					
					<% } %>
				<% } %> 
            </div> 
			
			
			<!-- 언어선택 부분 왼쪽 다단 --> 
			<div id="centerMainLeft_fsb"> 
			
				<!-- Native Language Selection --> 
				<p> 
				<select class="s_select" name="language1" id="language1" size="1"> 
				<option value="-1">-- Select Language --</option> 
				<% for(Language i: Language.values()) { %> 
					<% if(user.getLanguages().get(0).equals(i)) { %>
					<option selected=true value="<%= i.num %>"><%= i.name_en %> - <%= i.name %></option> 
					<% } else { %>
					<option value="<%= i.num %>"><%= i.name_en %> - <%= i.name %></option> 
					<% } %>
				<% } %> 
				
				</select> 
				</p> 
				
				<!-- First Possible Language Selection --> 
				<p> 
				<select class="s_select" name="language2" id="language2" size="1"> 
					<option value="-1">-- Select Language --</option> 
				<% for(Language i: Language.values()) { %> 
					
					<% if(user.getLanguages().size() > 1 && user.getLanguages().get(1).equals(i)) { %>
					<option selected=true value="<%= i.num %>"><%= i.name_en %> - <%= i.name %></option> 
					<% } else { %>
					<option value="<%= i.num %>"><%= i.name_en %> - <%= i.name %></option> 
					<% } %>

				<% } %> 
				
				</select> 
				</p> 
				
				<!-- Third Possible Language Selection --> 
				<p> 
				<select class="s_select" name="language3" id="language3" size="1"> 
					<option value="-1">-- Select Language --</option> 
					<% for(Language i: Language.values()) { %> 
					
					<% if(user.getLanguages().size() > 2 && user.getLanguages().get(2).equals(i)) { %>
					<option selected=true value="<%= i.num %>"><%= i.name_en %> - <%= i.name %></option> 
					<% } else { %>
					<option value="<%= i.num %>"><%= i.name_en %> - <%= i.name %></option> 
					<% } %>

				<% } %> 
				</select> 
				</p> 
			</div> 
			
			
			<!-- 언어선택 부분 오른쪽 다단 --> 
			<div id="centerMainRight_fsb"> 
			
				<!-- Second Possible Language Selection --> 
				<p> 
				<select class="s_select" name="language4" id="language5" size="1"> 
					<option value="-1">-- Select Language --</option> 
					<% for(Language i: Language.values()) { %> 
					
					<% if(user.getLanguages().size() > 3 && user.getLanguages().get(3).equals(i)) { %>
					<option selected=true value="<%= i.num %>"><%= i.name_en %> - <%= i.name %></option> 
					<% } else { %>
					<option value="<%= i.num %>"><%= i.name_en %> - <%= i.name %></option> 
					<% } %>

				<% } %> 
				</select> 
				</p> 
				
				<!-- Forth Possible Language Selection --> 
				<p> 
				<select class="s_select" name="language5" id="language6" size="1"> 
					<option value="-1">-- Select Language --</option> 
					<% for(Language i: Language.values()) { %> 
					
					<% if(user.getLanguages().size() > 4 && user.getLanguages().get(4).equals(i)) { %>
					<option selected=true value="<%= i.num %>"><%= i.name_en %> - <%= i.name %></option> 
					<% } else { %>
					<option value="<%= i.num %>"><%= i.name_en %> - <%= i.name %></option> 
					<% } %>

				<% } %> 
				</select> 
				</p> 
			</div> 
			
			
		</div> 
		
		</form>
		
		<!-- 우측 도움말 영역 --> 
		<div id="rightSide">
		<a href="/getTwitterAuth" target="_blank">Getting Twitter Auth </a> 
		 
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