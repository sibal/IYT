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
	<script language="javascript" src="/js/jquery.form.js"></script>
</head>
	
<script type="text/javascript">


	var access = '<%= user.getFace_access() %>';
	
	function translateClicked(aid, transMode)
	{
	
		if (transMode == 0)
		{
			//$('#bigContentAreaMenu').html('');
		
			//$('').append(function());
			$('#bigContentAreaMenu').hide();
			$('#bigContentAreaCmtR').hide();
			$('#bigContentAreaBtn').remove();
			// make the area of translation
			$('<div id="bigContentAreaMenu1"><a href="8.html"><img src="img/btn_bigger_screen.gif" width="32" height="32" alt="big screen" /></a></div>').insertAfter('#bigContentArea');
			
			$('<div id="bigContentTranslation">'+
					'<div id="tboxtop"><a onClick="closeClicked();"><img src="img/tboxmenu.gif" width="345" height="30" /></a></div>'+
					'<form id="form2" name="form2" method="post" action="/translate">'+
					'<input type=hidden id="sid" name="sid" value="'+aid+'" />'+
					'<input type=hidden id="voting" name="voting" value="0" />'+
					'<input type=hidden id="ori_lan" name="ori_lan" value="" />'+
					'<input type=hidden id="t_lan" name="t_lan" value="" />'+
					'<input type=hidden id="ori_content" name="ori_content" value="'+$('#bigContentArea').html()+'" />'+
					'<textarea class="translationArea" id="t_content" name="t_content" style="resize:none"></textarea>'+
					'</form>'+'</div>').insertAfter('#bigContentArea');
			
			//$('#bigContentAreaCmt').hide();
			$('<div id="bigContentAreaBtn1"><a href="#" onClick="translateClicked(\''+aid+'\',1);"><img src="img/btn_big_translate.gif" width="109" height="31" alt="translate" /></a></div>').insertAfter('#bigContentAreaMenu1');
			
			// make the top-4 translation
			var translation = '';
			$.getJSON('/topfour/'+aid, function(data) {
  			
  			  	 $.each(data.translations,function(i,fb){
				
					translation += '	<div class="bigContentAreaCmtR1" style="border-top:solid thin #e7e7e7"> ';
					if (i == 0)
						translation += '<div id="profilePic_33"><img src="img/profile_mayor.gif" width="49" height="55" alt="profile" /></div> ';
					else if (i == 1)
						translation += '<div id="profilePic_33"><img src="img/profile_1st.gif" width="49" height="55" alt="profile" /></div> ';
					else if (i == 2)
						translation += '<div id="profilePic_33"><img src="img/profile_2nd.gif" width="49" height="55" alt="profile" /></div> ';
					else
						translation += '<div id="profilePic_33"><img src="img/profile_3rd.gif" width="49" height="55" alt="profile" /></div> ';
					
					translation += '<div id="profileName_32">'+fb.author_data.name+'</div>'+
					'<div id="userTranslation">'+fb.t_content+'</div>'+
					'<div id="rankup_32"><a href="#" onclick="vote(\''+fb.id+'\','+i+');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage(\'Image55\',\'\',\'img/btn_rankup_on.gif\',1)"><img src="img/btn_rankup.gif" alt="rank-up" name="Image55" width="128" height="33" border="0" id="Image55" /></a>'+
					'<div class="rankUpNumber" id="rankUpNumber'+i+'" onselectstart="return false">'+fb.voting+'</div></div>'+
					'<div id="addfan_32"><a href="#" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage(\'Image56\',\'\',\'img/btn_fans_on.gif\',1)"><img src="img/btn_fans.gif" alt="rank-up" name="Image56" width="103" height="33" border="0" id="Image56" /></a>'+
					'<div id="fanNumber" onselectstart="return false">136</div></div>'+
					'<div id="more_32"><a href="#" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage(\'Image57\',\'\',\'img/btn_more_32_on.gif\',1)"><img src="img/btn_more_32.gif" name="Image57" width="40" height="33" border="0" id="Image57" /></a></div>'+
					'</div>';
		    	});
		    	
		    	$(translation).insertAfter('#bigContentAreaCmt');
				$('#bigContentAreaCmt').show();
		    	


			});
			
			$('#form2').ajaxForm();			
			

		}
		else
		{
			
		
			var queryString = $('#form2').formSerialize(); 
			// the data could now be submitted using $.get, $.post, $.ajax, etc 
			$.post('/translate', queryString, function(response){
			    
			    //location.href = "/translations";
			    // alert(response);
			    
			    $.getJSON('/topfour/'+aid, function(data) {
  			
  					$('.bigContentAreaCmtR1').remove();
  					
 
  			  	 $.each(data.translations,function(i,fb){
				
					translation += '	<div class="bigContentAreaCmtR1" style="border-top:solid thin #e7e7e7"> ';
					if (i == 0)
						translation += '<div id="profilePic_33"><img src="img/profile_mayor.gif" width="49" height="55" alt="profile" /></div> ';
					else if (i == 1)
						translation += '<div id="profilePic_33"><img src="img/profile_1st.gif" width="49" height="55" alt="profile" /></div> ';
					else if (i == 2)
						translation += '<div id="profilePic_33"><img src="img/profile_2nd.gif" width="49" height="55" alt="profile" /></div> ';
					else
						translation += '<div id="profilePic_33"><img src="img/profile_3rd.gif" width="49" height="55" alt="profile" /></div> ';
					
					translation += '<div id="profileName_32">'+fb.author_data.name+'</div>'+
					'<div id="userTranslation">'+fb.t_content+'</div>'+
					'<div id="rankup_32"><a href="#" onclick="vote(\''+fb.id+'\', '+i+');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage(\'Image55\',\'\',\'img/btn_rankup_on.gif\',1)"><img src="img/btn_rankup.gif" alt="rank-up" name="Image55" width="128" height="33" border="0" id="Image55" /></a>'+
					'<div class="rankUpNumber" id="rankUpNumber'+i+'" onselectstart="return false">'+fb.voting+'</div></div>'+
					'<div id="addfan_32"><a href="#" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage(\'Image56\',\'\',\'img/btn_fans_on.gif\',1)"><img src="img/btn_fans.gif" alt="rank-up" name="Image56" width="103" height="33" border="0" id="Image56" /></a>'+
					'<div id="fanNumber" onselectstart="return false">136</div></div>'+
					'<div id="more_32"><a href="#" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage(\'Image57\',\'\',\'img/btn_more_32_on.gif\',1)"><img src="img/btn_more_32.gif" name="Image57" width="40" height="33" border="0" id="Image57" /></a></div>'+
					'</div>';
		    	});
		    	
		    	$(translation).insertAfter('#bigContentAreaCmt');
				$('#bigContentAreaCmt').show();
		    	


			}); 
			
			}); 
	
		}
		
	}
	
	function closeClicked()
	{
		$('#bigContentAreaMenu').show();
		$('#bigContentAreaMenu1').remove();
		$('#bigContentTranslation').remove();
		$('.bigContentAreaCmtR1').remove();
		$('#bigContentAreaCmt').show();
		$('#bigContentAreaCmtR').show();
		$('#bigContentAreaBtn1').remove();
		$('#bigContentAreaBtn').show();
	}
	

	function showDetail(aid, flag)
	{
	
		// hide side bar
		$('#statArea').hide();
		$('#statArea2').hide();
		$('#editProfileDiv').hide();
		$('#sidebar1').hide();

		$('#sidebar1').css("width","396px");
		$('#sidebar1').css("padding","0");

		if (flag == 0)
		{
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
			'<div id="bigContentAreaBtn"><a onClick="translateClicked(\''+aid+'\', 0)"><img src="img/btn_big_translate.gif" width="109" height="31" alt="translate" /></a></div>'+
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
		
		}
		else
		{
			
		var url = "/t_getTweet/"+aid;
		$.getJSON(url, function(json) {

			var html="";
			html='<div id="profilePicLong"><img src="img/tw_ico_bw.gif" width="48" height="48" /><img src="'+json.user.profile_image_url+'" width="48" height="48" alt="profile" /></div>'+
			'<div id="profileIdNameDiv" style="margin-top:20px"><span class="profile_id">'+json.user.name+'</span></div>'+
			'<div id="bigContentArea">'+json.text+'</div>'+
  			'<div id="bigContentAreaMenu"> <span class="articleSubMenu_time">1 minute ago  </span></div>'+
			'<div id="bigContentAreaBtn"><a onClick="translateClicked(\''+aid+'\')"><img src="img/btn_big_translate.gif" width="109" height="31" alt="translate" /></a></div>'+
			'<br />'+
			'<div id="bigContentAreaCmt"><img src="img/side_cmtopn.gif" width="394" height="8" />';
            
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
			
			alert("complete");
		});
		
		}

	};

		
	function fbFetch(){
	  //Set Url of JSON data from the facebook graph api. make sure callback is set with a '?' to overcome the cross domain problems with JSON
      var url = "https://graph.facebook.com/me/home?limit=5&access_token="+access+"&date_format=U&fields=id,from,message,comments,likes&callback=?";
		
      //Use jQuery getJSON method to fetch the data from the url and then create our unordered list with the relevant data.

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

		
			$.getJSON('/t_getTimeline',  function(oo){
				$.each(oo.statuses, function (i,tw){
					container2.push(tw);
				
				});
				
				var count1 = 0;
				var count2 = 0;
				
				for(j=0;j<10;j++)
				{
													
					if (count1 < 5 && (count2 == 5 || new Date(container1[count1].created_time*1000) > new Date(Date.parse(container2[count2].created_at.replace(/(\+\S+) (.*)/, '$2 $1')))))
					{
						html+= "<div id='articleBox' onClick='showDetail(\""+container1[count1].id+"\", 0);' style='cursor:hand; cursor: pointer;'><div id='profilePic'><img src='http://graph.facebook.com/"+container1[count1].from.id+"/picture' width='48' height='48' alt='profile' /></div><div id='profileText'><span class='content_id'><img src='img/fb_ico.gif' width='18' height='18' align='absmiddle' /> "+container1[count1].from.name+"</span> </div><div id='timelineContent'><span class='timelineContent_normal'>"+container1[count1].message+"</span></div><div id='articleSubMenu'><span class='articleSubMenu_time'>"+container1[count1].created_time+" / </span><img src='img/fb_like.gif' width='17' height='15' align='absmiddle' class='textmiddle' /><span class='articleSubMenu_facebook'>Like&nbsp;<img src='img/fb_cmt.gif' width='17' height='15' align='absmiddle' class='textmiddle' />Comment</span>&nbsp;<img src='img/translate.gif' width='17' height='15' align='absmiddle' class='textmiddle' /><span class='articleSubMenu_iyoutranslate'>Translate!</span></div></div>";
						count1++;
					}
					else
					{
				
						html+= "<div id='articleBox' onClick='showDetail(\""+container2[count2].id_str+"\", 1);' style='cursor:hand; cursor: pointer;'><div id='profilePic'><img src='"+container2[count2].user.profile_image_url+"' width='48' height='48' alt='profile' /></div><div id='profileText'><span class='content_id'><img src='img/tw_ico.gif' width='18' height='18' align='absmiddle' /> "+container2[count2].user.name+"</span> </div><div id='timelineContent'><span class='timelineContent_normal'>"+container2[count2].text+"</span></div><div id='articleSubMenu'><span class='articleSubMenu_time'>"+container2[count2].created_at+" / </span><img src='img/tw_fav.gif' width='17' height='15' align='absmiddle' class='textmiddle' /><span class='articleSubMenu_twitter' onClick='getLanguage(\"hh\")'>Favorite&nbsp;<img src='img/tw_ret.gif' width='17' height='15' align='absmiddle' class='textmiddle' />Retweet&nbsp;<img src='img/tw_rep.gif' width='17' height='15' align='absmiddle' class='textmiddle' />Reply</span>&nbsp;<img src='img/translate.gif' width='17' height='15' align='absmiddle' class='textmiddle' /><span class='articleSubMenu_iyoutranslate'>Translate!</span></div></div>";
						count2++;
					}
				}	
				
				$('#articles').animate({opacity:0}, 500, function(){

					$('#articles').html(html);
			 	});
				$('#articles').animate({opacity:1}, 500);							
			
			});



			/*
		    $.each(json.data,function(i,fb){

				//				html += "<li>"+"<img src='http://graph.facebook.com/"+fb.from.id+"/picture'> " + fb.from.name+" " +fb.message + "</li>"; 
				html+= "<div id='articleBox' onClick='showDetail(\""+fb.id+"\");' style='cursor:hand; cursor: pointer;'><div id='profilePic'><img src='http://graph.facebook.com/"+fb.from.id+"/picture' width='48' height='48' alt='profile' /></div><div id='profileText'><span class='content_id'><img src='img/fb_ico.gif' width='18' height='18' align='absmiddle' /> "+fb.from.name+"</span> </div><div id='timelineContent'><span class='timelineContent_normal'>"+fb.message+"</span></div><div id='articleSubMenu'><span class='articleSubMenu_time'>1 minute ago / </span><img src='img/fb_like.gif' width='17' height='15' align='absmiddle' class='textmiddle' /><span class='articleSubMenu_facebook'>Like&nbsp;<img src='img/fb_cmt.gif' width='17' height='15' align='absmiddle' class='textmiddle' />Comment</span>&nbsp;<img src='img/translate.gif' width='17' height='15' align='absmiddle' class='textmiddle' /><span class='articleSubMenu_iyoutranslate'>Translate!</span></div></div>";
		    	});

			//A little animation once fetched

			*/

			});
	};
	
	
	function vote(tid, order){
	
		$.getJSON('/dovote/'+tid, function(data){
		
		        		// do something with a
        		if (data.success==0)
        		{
        			alert("You were already vote this translation!");
        		}
        		else
        		{
        			$('#rankUpNumber'+order).html(data.voting);
        		}
		
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
