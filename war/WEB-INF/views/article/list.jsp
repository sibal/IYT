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
	<link rel="StyleSheet" href="/css/main.css" type="text/css"/>
	<script language="javascript" src="/js/jquery17.js"></script>
	<script language="javascript" src="/js/common.js"></script>
</head>
	
<script type="text/javascript">


	var access = '<%= user.getFace_access() %>';
	var curr_aid = '';
	var transMode = 0;
	
	function translateClicked()
	{
	
		if (transMode == 0)
		{
			//$('#bigContentAreaMenu').html('');
		
			//$('').append(function());
			$('#bigContentAreaMenu').hide();
			$('#bigContentAreaBtn').css({
				width:'109px',
				margin: '25px 0 5px 0'
			});
		
		
			$('<div id="bigContentAreaMenu1"><a href="8.html"><img src="img/btn_bigger_screen.gif" width="32" height="32" alt="big screen" /></a></div>').insertAfter('#bigContentArea');
			$('<div id="bigContentTranslation">'+
					'<div id="tboxtop"><a href="6.html"><img src="img/tboxmenu.gif" width="345" height="30" /></a></div>'+
					'<form id="form2" name="form2" method="post" action="/translate">'+
					'<input type=hidden id="sid" name="sid" value="'+curr_aid+'" />'+
					'<input type=hidden id="ori_content" name="ori_content" value="'+$('#bigContentArea').html()+'" />'+
					'<textarea class="translationArea" id="t_content" name="t_content" style="resize:none"></textarea>'+
					'</form>'+'</div>').insertAfter('#bigContentArea');
			
			$('#bigContentAreaCmt').hide();
			transMode = 1;

		}
		else
		{
			$('#form2').submit();
		}
		
	}
	

	function showDetail(aid)
	{
		curr_aid = aid;
		// hide side bar
		$('#statArea').hide();
		$('#statArea2').hide();
		$('#editProfileDiv').hide();
		$('#sidebar1').hide();

		$('#sidebar1').css("width","396px");
		$('#sidebar1').css("padding","0");

		var url = "https://graph.facebook.com/"+aid+"?access_token="+access+"&callback=?";
		$.getJSON(url, function(json) {
            var flag = 0;
            if (json.likes != undefined)
			{
                flag = 1; 
                /*
				$.each(json.likes.data, function(j, like) {
					if (like.id == myid)
					{
						flag = 2;
					}
    			}); */
//                    '<div id="bigContentAreaCmtR"><span class="articleSubMenu_facebook"><img src="img/fb_cmt.gif" width="17" height="15" align="absmiddle" /> View all 6 comments ??</span></div>';
            }

			var html="";
			html='<div id="profilePicLong"><img src="img/fb_ico_bw.gif" width="48" height="48" /><img src="http://graph.facebook.com/'+json.from.id+'/picture" width="48" height="48" alt="profile" /></div>'+
			'<div id="profileIdNameDiv" style="margin-top:20px"><span class="profile_id">'+json.from.name+'</span></div>'+
			'<div id="bigContentArea">'+json.message+'</div>'+
  			'<div id="bigContentAreaMenu"> <span class="articleSubMenu_time">1 minute ago  </span><span class="articleSubMenu_facebook">&nbsp;<a onclick="fbLike(\''+json.id+'\');">Like</a>&nbsp;&nbsp;<a onclick="">Comment</a></span></div>'+
			'<div id="bigContentAreaBtn"><a onClick="translateClicked()"><img src="img/btn_big_translate.gif" width="109" height="31" alt="translate" /></a></div>'+
			'<br />'+
			'<div id="bigContentAreaCmt"><img src="img/side_cmtopn.gif" width="394" height="8" />';
            
            if (flag==2)
            {
                html+='<div id="bigContentAreaCmtR"><img src="img/fb_like.gif" width="17" height="15" align="absmiddle" /> You and <span class="articleSubMenu_facebook">'+json.likes.count+' others</span> like this</div>';
            }
            else if (flag == 1)
            {
                html+='<div id="bigContentAreaCmtR"><img src="img/fb_like.gif" width="17" height="15" align="absmiddle" /> <span class="articleSubMenu_facebook">'+json.likes.count+' people</span> like this</div>';
            }
            else
            {
                html+='<div id="bigContentAreaCmtR"><img src="img/fb_like.gif" width="17" height="15" align="absmiddle" /> <span class="articleSubMenu_facebook">0 people</span> like this</div>';
            }


			var comments="";
			if (!json.comments==undefined)
			{
				$.each(json.comments.data, function(i,comm) {
				comments += '<div id="bigContentAreaCmtR">'+
					'<div id="profilePic_32"><img src="http://graph.facebook.com/'+comm.from.id+'/picture" width="32" height="32" alt="profile" /></div>'+
					'<div id="profileName_32">'+comm.from.name+'</div>'+
					'<div id="timelineContent_32">'+comm.message+'</div>'+
					'<div id="timelineContentMenu_32">May 27 12:19am  <img src="img/fb_like_s.gif" width="10" height="9" /><span class="articleSubMenu_facebook"> 1 person</span></div>'+
					'</div>';

					});
			}

			html += comments;
			html += "</div>";

			html += '<!-- hide/show -->'+
				'<div id="hideShowMenu" class="hideShowMenuStyle" style="visibility:hidden">'+
				'<div id="hsm_pic"><img src="img/profile_90_90.gif" width="20" height="20" /></div>'+
				'<div id="hsm_id">johnniek7</div>'+
				'<div id="hsm_menu" style="margin: 21px 0 0 23px"><a href="12.html">Account Settings</a></div>'+
				'<div id="hsm_menu" style="margin: 13px 0 0 23px">Help Center</div>'+
				'<div id="hsm_menu" style="margin: 13px 0 0 23px"><a href="1-edit.html">Log Out</a></div>'+
				'</div>';
			$('#sidebar1').html(html);
			$('#sidebar1').animate({opacity:0}, 200, function() {
				$('#sidebar1').show();
			});
			$('#sidebar1').animate({opacity:1},200);

		});

	};

		
	function fbFetch(){
	  //Set Url of JSON data from the facebook graph api. make sure callback is set with a '?' to overcome the cross domain problems with JSON
      var url = "https://graph.facebook.com/me/home?limit=10&access_token="+access+"&fields=id,from,message,comments,likes&callback=?";
		
      //Use jQuery getJSON method to fetch the data from the url and then create our unordered list with the relevant data.

	      $.ajaxSetup({ cache: false }); 
	      $.getJSON(url, function(json){
		    var html = "";
		    //loop through and within data array's retrieve the message variable.

		    $.each(json.data,function(i,fb){

				//				html += "<li>"+"<img src='http://graph.facebook.com/"+fb.from.id+"/picture'> " + fb.from.name+" " +fb.message + "</li>"; 
				html+= "<div id='articleBox' onClick='showDetail(\""+fb.id+"\");' style='cursor:hand; cursor: pointer;'><div id='profilePic'><img src='http://graph.facebook.com/"+fb.from.id+"/picture' width='48' height='48' alt='profile' /></div><div id='profileText'><span class='content_id'><img src='img/fb_ico.gif' width='18' height='18' align='absmiddle' /> "+fb.from.name+"</span> </div><div id='timelineContent'><span class='timelineContent_normal'>"+fb.message+"</span></div><div id='articleSubMenu'><span class='articleSubMenu_time'>1 minute ago / </span><img src='img/fb_like.gif' width='17' height='15' align='absmiddle' class='textmiddle' /><span class='articleSubMenu_facebook'>Like&nbsp;<img src='img/fb_cmt.gif' width='17' height='15' align='absmiddle' class='textmiddle' />Comment</span>&nbsp;<img src='img/translate.gif' width='17' height='15' align='absmiddle' class='textmiddle' /><span class='articleSubMenu_iyoutranslate'>Translate!</span></div></div>";
		    	});

			//A little animation once fetched
			$('#articles').animate({opacity:0}, 500, function(){

				$('#articles').html(html);
			 });
			$('#articles').animate({opacity:1}, 500);


			});
	};



</script>

<body class="twoColFixRtHdr" onload=fbFetch()>

	<div id="container">
		<div id="header"> <a href="5.html"><img src="img/logo_top_e.jpg" width="228" height="93" alt="Twitlator" style="margin-left:15px; border:none; vertical-align:middle; font-family: Arial, Helvetica, verdana, sans-serif;" /></a>
		<input type="text" name="searchbar_top" class="searchBar" />
		<span class="menuText"><a href="5.html">Live Feed</a></span><img src="img/menu_dv.gif" width="32" height="93" border="0" style="vertical-align:middle"/><span class="menuText"><a href="9.html">Contribute as Twitlator</a></span><img src="img/menu_dv.gif" width="32" height="93" border="0" style="vertical-align:middle"/><span class="menuText"><a href="10.html">Find Twitlator</a></span><img src="img/menu_dv.gif" width="32" height="93" border="0" style="vertical-align:middle"/><span class="menuText"><a href="#" onclick="showHideDiv()">Account ▾</a></span>
		</div><!-- end #header -->

		<div id="sidebar1">
			<%@ include file="/WEB-INF/views/partial/user.jsp" %>
			
		</div><!-- end #sidebar1 -->
	
		<div id="mainContent">
			
			<%@ include file="/WEB-INF/views/partial/write.jsp" %>
			
    
			<div id="timelineSubMenu"><p class="menuText">Timeline</p></div>
			<div id="timelineSubMenu_nonselect" style="width:100px"><p class="menuText"><a href="/translations">Translated</a></p></div>
			<div id="timelineSubMenu_nonselect" style="width:100px"><p class="menuText"><a href="#">Languages ▾</a></p></div>
			<div id="timelineSubMenu_nonselect" style="width:100px"><p class="menuText"><a href="#">Categories ▾</a></p></div>
			
			<p>&nbsp;</p>
			<div id="articles">
				Loading
			</div>
  					
		</div><!-- end #mainContent -->
		<br/>
	</div><!-- end #container -->
</body>
</html>
