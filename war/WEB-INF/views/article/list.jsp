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
	<script src="/js/jquery.timeago.js" type="text/javascript"></script>
</head>
	
<script type="text/javascript">

	var access = '<%= user.getFace_access() %>';
	
	function translateClicked(aid, transMode, flag)
	{
		
		if (transMode == 0)
		{
			//$('#bigContentAreaMenu').html('');
			//$('').append(function());
			$('#bigContentAreaMenu').hide();
			$('.bigContentAreaCmtR').hide();
			$('#bigContentAreaBtn').hide();
			// make the area of translation
			$('<div id="bigContentAreaMenu1"></div>').insertAfter('#bigContentArea');
			
			$('<div id="bigContentTranslation">'+
					'<div id="tboxtop"><a onClick="closeClicked();"><img src="img/tboxmenu.gif" width="345" height="30" /></a></div>'+
					'<form id="form2" name="form2" method="post" action="/translate">'+
					'<input type=hidden id="sid" name="sid" value="'+aid+'" />'+
					'<input type=hidden id="voting" name="voting" value="0" />'+
					'<input type=hidden id="ori_lan" name="ori_lan" value="" />'+
					'<input type=hidden id="username" name="username" value="'+$('#profile_name').html()+'" />'+
					'<input type=hidden id="userid" name="userid" value="'+$('#user_id').html()+'" />'+
					'<input type=hidden id="t_lan" name="t_lan" value="" />'+
					'<input type=hidden id="vender" name="vender" value='+flag+' />'+
					'<input type=hidden id="profile_image_url" name="profile_image_url" value="'+$('#image_url').attr('src')+'" />'+
					'<input type=hidden id="ori_content" name="ori_content" value="'+$('#bigContentArea').html().replace(/\'/g,"\\'").replace(/\"/g,'\\"')+'" />'+
					'<textarea class="translationArea" id="t_content" name="t_content" style="resize:none"></textarea>'+
					'</form>'+'</div>').insertAfter('#bigContentArea');
			
			//$('#bigContentAreaCmt').hide();
			$('<div id="bigContentAreaBtn1"><a href="#" onClick="translateClicked(\''+aid+'\',1, '+flag+');"><img src="img/btn_big_translate.gif" width="109" height="31" alt="translate" /></a></div>').insertAfter('#bigContentAreaMenu1');
			
			// make the top-4 translation
			var translation = '';
			$.getJSON('/topfour/'+aid, function(data) {

  				if (data.translations.length == 0)
  				{
  					translation += '<div class="bigContentAreaCmtR1" style="border-top:solid thin #e7e7e7"> ';
  					translation += '<form id="requestForm" name="requestForm" method="post" action="/request">';
  					translation += '<input type=hidden id="aid" name="aid" value="'+aid+'" />';
  					translation += '<input type=hidden id="text" name="text" value="'+$('#bigContentArea').html().replace(/\'/g,"\\'").replace(/\"/g,'\\"')+'" />';
  					translation += '<input type=hidden id="vender" name="vender" value='+flag+' />';
  					translation += '<input type=hidden id="uid" name="uid" value="'+$('#user_id').html()+'" />';
  					translation += '<input type=hidden id="username" name="username" value="'+$('#profile_name').html()+'" />';
  					translation += '<input type=hidden id="profile_image_url" name="profile_image_url" value="'+$('#image_url').attr('src')+'" /></form>';
  					
  					translation += '<a href="#" onClick="doRequest(\''+aid+'\')">Do request!</a></div>';
  				}
  				else
  				{
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
		    	
		    	}
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
	
	function doRequest(aid)
	{
		// do request
		$('#requestForm').ajaxForm();
		
		var queryString = $('#requestForm').formSerialize(); 
		// the data could now be submitted using $.get, $.post, $.ajax, etc 
		$.post('/request', queryString, function(response){
		
        		if (response=="0")
        		{
        			alert("Already registered!");
        		}
        		else
        		{
        			alert("Successfully done!");
        		}		
        		
        	});
			 
		
	}
	
	function closeClicked()
	{
		$('#bigContentAreaMenu').show();
		$('#bigContentAreaMenu1').remove();
		$('#bigContentTranslation').remove();
		$('.bigContentAreaCmtR1').remove();
		$('#bigContentAreaCmt').show();
		$('.bigContentAreaCmtR').show();
		$('#bigContentAreaBtn1').remove();
		$('#bigContentAreaBtn').show();
	}
	
	function showDetail(aid, flag)
	{
		// hide side bar
		$('#statArea').hide();
		$('#statArea2').hide();
		$('#editProfileDiv').hide();
		$('#sidebar1').show();

		$('#sidebar1').css("width","396px");
		$('#sidebar1').css("padding","0");
		$('#sidebar1').html('<p align=center><img src="/img/loading.gif" /></p>');
		
		if (flag == 0)
		{
		var url = "https://graph.facebook.com/"+aid+"?access_token="+access+"&date_format=U&callback=?";
		$.getJSON(url, function(json) {
            var likeflag = 0;
            if (typeof(json.likes) == "object")
			{
                likeflag = 1; 
                
				$.each(json.likes.data, function(j, like) {
					if (like.id == '<%= user.getFid() %>')
					{
						likeflag = 2;
					}
    			}); 
            }
			
			var html="";
			html='<div id="profilePicLong"><img src="img/fb_ico_bw.gif" width="48" height="48" /><img src="http://graph.facebook.com/'+json.from.id+'/picture" id="image_url" width="48" height="48" alt="profile" /></div>'+
			'<div id="profileIdNameDiv" style="margin-top:20px"><span class="profile_id" id="profile_name">'+json.from.name+'</span><div id="user_id" style="visibility:hidden">'+json.from.id+'</div></div>'+
			'<div id="bigContentArea">'+json.message+'</div>'+
  			'<div id="bigContentAreaMenu"> <span class="articleSubMenu_time">'+jQuery.timeago(new Date(json.created_time*1000))+'</span>';
  			//if (likeflag == 2)
			//html += '<span class="articleSubMenu_facebook">&nbsp;<a onclick="fbLike(0, \''+json.id+'\');">Cancle Like</a>&nbsp;&nbsp;<a onclick="">Comment</a></span></div>';
			//else
			//html += '<span class="articleSubMenu_facebook">&nbsp;<a onclick="fbLike(1, \''+json.id+'\');">Like</a>&nbsp;&nbsp;<a onclick="">Comment</a></span></div>';
			html += '</div>';
			html += '<div id="bigContentAreaBtn"><a onClick="translateClicked(\''+aid+'\', 0, 0)"><img src="img/btn_big_translate.gif" width="109" height="31" alt="translate" /></a></div>'+
			'<br />'+
			'<div id="bigContentAreaCmt"><img src="img/side_cmtopn.gif" width="394" height="8" />';
            
            if (likeflag==2)
            {
                html+='<div class="bigContentAreaCmtR"><img src="img/fb_like.gif" width="17" height="15" align="absmiddle" /> You and <span class="articleSubMenu_facebook">'+(json.likes.count-1)+' others</span> like this</div>';
            }
            else if (likeflag == 1)
            {
                html+='<div class="bigContentAreaCmtR"><img src="img/fb_like.gif" width="17" height="15" align="absmiddle" /> <span class="articleSubMenu_facebook">'+json.likes.count+' people</span> like this</div>';
            }
            else
            {
                html+='<div class="bigContentAreaCmtR"><img src="img/fb_like.gif" width="17" height="15" align="absmiddle" /> <span class="articleSubMenu_facebook">0 people</span> like this</div>';
            }

			var comments="";
			if (typeof(json.comments.data) == "object")
			{
		
				$.each(json.comments.data, function(i,comm) {
				comments += '<div class="bigContentAreaCmtR">'+
					'<div id="profilePic"><img src="http://graph.facebook.com/'+comm.from.id+'/picture" width="48" height="48" alt="profile" /></div>'+
					'<div id="profileName_32">'+comm.from.name+'</div>'+
					'<div id="userTranslation">'+comm.message+'</div>'+
					'<div id="bigContentAreaMenu" style="padding: 0 0 0 0;margin: 0px 0px 0px 0px">'+jQuery.timeago(new Date(comm.created_time*1000));
					
					if (typeof(comm.likes) == "object")
					{
					comments += ' <img src="img/fb_like_s.gif" width="10" height="9" /><span class="articleSubMenu_facebook">'+comm.likes.count+' people </span></div>';
					}
					else
					{
					comments += ' <img src="img/fb_like_s.gif" width="10" height="9" /><span class="articleSubMenu_facebook"> 0 people </span></div>';
					}
					
					comments += '</div>';

					});
			}

			html += comments;
			html += "</div>";

			html += '<!-- hide/show -->'+
				'<div id="hideShowMenu" class="hideShowMenuStyle" style="visibility:hidden">'+
				'<div id="hsm_pic"><img src="img/profile_90_90.gif" width="20" height="20" /></div>'+
				'<div id="hsm_id">johnniek7</div>'+
				'<div id="hsm_menu" style="margin: 21px 0 0 23px"><a href="/account_view">Account Settings</a></div>'+
				'<div id="hsm_menu" style="margin: 13px 0 0 23px">Help Center</div>'+
				'<div id="hsm_menu" style="margin: 13px 0 0 23px"><a href="1-edit.html">Log Out</a></div>'+
				'</div>';
			$('#sidebar1').animate({opacity:0}, 200, function() {
				$('#sidebar1').html(html);
			});
			$('#sidebar1').animate({opacity:1},200);

		});
		
		}
		else
		{
			
			var url = "/t_getTweet/"+aid;
			$.getJSON(url, function(json) {
	
				var html="";
				html='<div id="profilePicLong"><img src="img/tw_ico_bw.gif" width="48" height="48" /><img src="'+json.user.profile_image_url+'" id="image_url" width="48" height="48" alt="profile" /></div>'+
				'<div id="profileIdNameDiv" style="margin-top:20px"><span class="profile_id" id="profile_name">'+json.user.name+'</span><div id="user_id" style="visibility:hidden">'+json.user.id+'</div></div>'+
				'<div id="bigContentArea">'+json.text+'</div>'+
	  			'<div id="bigContentAreaMenu"> <span class="articleSubMenu_time">'+jQuery.timeago(new Date(json.created_at.replace(/(\+\S+) (.*)/, '$2 $1')))+'</span></div>'+
				'<div id="bigContentAreaBtn"><a onClick="translateClicked(\''+aid+'\', 0, 1)"><img src="img/btn_big_translate.gif" width="109" height="31" alt="translate" /></a></div>'+
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
				$('#sidebar1').animate({opacity:0}, 200, function() {
					$('#sidebar1').html(html);
				});
				$('#sidebar1').animate({opacity:1},200);
				
			});
		
		}

	};

		
	function fbFetch(mode, url, url2){

	  //Set Url of JSON data from the facebook graph api. make sure callback is set with a '?' to overcome the cross domain problems with JSON
		
      //Use jQuery getJSON method to fetch the data from the url and then create our unordered list with the relevant data.
		<% if (!user.getTwit_authT().equals("") && !user.getFace_access().equals("")) {%>
					
		if (mode==0)
		{
			url= "https://graph.facebook.com/me/home?limit=5&access_token="+access+"&date_format=U&fields=id,from,message,comments,likes&callback=?";
			url2= "/t_getTimeline";
		}
	
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
	
			
				$.getJSON(url2,  function(oo){
					$.each(oo.statuses, function (i,tw){
						container2.push(tw);
					
					});
					
					var count1 = 0;
					var count2 = 0;
					
					for(j=0;j<10;j++)
					{
														
						if (count1 < 5 && (count2 == 5 || new Date(container1[count1].created_time*1000) > new Date(Date.parse(container2[count2].created_at.replace(/(\+\S+) (.*)/, '$2 $1')))))
						{
							html+= "<div class='articleBox' style='cursor:hand; cursor: pointer;'><div id='profilePic'>"+
							"<img src='http://graph.facebook.com/"+container1[count1].from.id+"/picture' width='48' height='48' alt='profile' /></div><div id='profileText'>"+
							"<span class='content_id'><img src='img/fb_ico.gif' width='18' height='18' align='absmiddle' /> "+container1[count1].from.name+"</span> </div>"+
							"<div id='timelineContent' onClick='showDetail(\""+container1[count1].id+"\", 0);'><span class='timelineContent_normal'>"+container1[count1].message+"</span></div><div id='articleSubMenu'>"+
							"<span class='articleSubMenu_time'>"+jQuery.timeago(new Date(container1[count1].created_time*1000))+" / </span>";
							//"<img src='img/fb_like.gif' width='17' height='15' align='absmiddle' class='textmiddle' /><span class='articleSubMenu_facebook'>Like&nbsp;"+
							//"<img src='img/fb_cmt.gif' width='17' height='15' align='absmiddle' class='textmiddle' />Comment</span>&nbsp;"+
							html += "<img src='img/translate.gif' width='17' height='15' align='absmiddle' class='textmiddle' /><span class='articleSubMenu_iyoutranslate'><a onClick='$(\"#trans_bar_"+container1[count1].id+"\").css(\"display\", \"block\")'>Translate!</a></span>";
							html += "<div id='trans_bar_"+container1[count1].id+"' style='display:none' >"+
							
									'<form id=\"'+container1[count1].id+'\" name=\"'+container1[count1].id+'\" method=POST>'+
									'<input type=hidden class="sid" id="sid" name="sid" value="'+container1[count1].id+'" />'+
									'<input type=hidden id="voting" name="voting" value="0" />'+
									'<input type=hidden id="ori_lan" name="ori_lan" value="" />'+
									'<input type=hidden id="username" name="username" value="'+container1[count1].from.name+'" />'+
									'<input type=hidden id="userid" name="userid" value="'+container1[count1].from.id+'" />'+
									'<input type=hidden id="t_lan" name="t_lan" value="" />'+
									'<input type=hidden id="vender" name="vender" value=0 />'+
									'<input type=hidden id="profile_image_url" name="profile_image_url" value="http://graph.facebook.com/'+container1[count1].from.id+'/picture" />'+
									'<input type=hidden id="ori_content" name="ori_content" value="'+container1[count1].message+'" />'+
							
									"<input type='text' name='t_content' id='t_content' class='qtranslateBar' style='width:400px' />"+
								    "<a onClick='translate(\""+container1[count1].id+"\")'><img src='img/translate_small_send.gif'  alt='send' width='63' height='24' align='middle' style='border:none;vertical-algin:top'/></a> </p></form></div>";
							html += "</div></div>";

							count1++;
						}
						else
						{
					
							html+= "<div class='articleBox'  style='cursor:hand; cursor: pointer;'>"+
							"<div id='profilePic'><img src='"+container2[count2].user.profile_image_url+"' width='48' height='48' alt='profile' /></div>"+
							"<div id='profileText'><span class='content_id'><img src='img/tw_ico.gif' width='18' height='18' align='absmiddle' /> "+container2[count2].user.name+"</span> </div>"+
							"<div id='timelineContent' onClick='showDetail(\""+container2[count2].id_str+"\", 1);'><span class='timelineContent_normal'>"+container2[count2].text+"</span></div><div id='articleSubMenu'>"+
							"<span class='articleSubMenu_time'>"+ jQuery.timeago(new Date(Date.parse(container2[count2].created_at.replace(/(\+\S+) (.*)/, '$2 $1'))))+" / </span>"+
							"<img src='img/tw_fav.gif' width='17' height='15' align='absmiddle' class='textmiddle' /><span class='articleSubMenu_twitter'>";
							
							if (container2[count2].favorited == false)						
								html+="<span id='favorite_"+container2[count2].id_str+"' style='width:50px'><a href='#' class='articleSubMenu_twitter' id='a_"+container2[count2].id_str+"' onClick='t_favorite(\""+container2[count2].id_str+"\");'>Favorite&nbsp;</a></span>";
							else
								html+="<span id='favorite_"+container2[count2].id_str+"' style='width:50px'><a href='#' class='articleSubMenu_twitter' id='a_"+container2[count2].id_str+"' onClick='t_cancelfavorite(\""+container2[count2].id_str+"\");'>Favorited&nbsp;</a></span>";
								
							if (container2[count2].retweeted == false)		
								html+= "<span id='retweet_"+container2[count2].id_str+"'><a onClick='t_retweet(\""+container2[count2].id_str+"\")'><img src='img/tw_ret.gif' width='17' height='15' align='absmiddle' class='textmiddle' />Retweet&nbsp;</a></span>";
							else
								html+= "<span id='retweet_"+container2[count2].id_str+"'><img src='img/tw_ret.gif' width='17' height='15' align='absmiddle' class='textmiddle' />Retweetd&nbsp;</span>";
								
							
							html += "<img src='img/translate.gif' width='17' height='15' align='absmiddle' class='textmiddle' /><span class='articleSubMenu_iyoutranslate'><a onClick='$(\"#trans_bar_"+container2[count2].id_str+"\").css(\"display\", \"block\")'>Translate!</a></span>";
							html += "<div id='trans_bar_"+container2[count2].id_str+"' style='display:none' >"+
							
									'<form id=\"'+container2[count2].id_str+'\" method=POST>'+
									'<input type=hidden class="sid" id="sid" name="sid" value="'+container2[count2].id_str+'" />'+
									'<input type=hidden id="voting" name="voting" value="0" />'+
									'<input type=hidden id="ori_lan" name="ori_lan" value="" />'+
									'<input type=hidden id="username" name="username" value="'+container2[count2].user.name+'" />'+
									'<input type=hidden id="userid" name="userid" value="'+container2[count2].user.id_str+'" />'+
									'<input type=hidden id="t_lan" name="t_lan" value="" />'+
									'<input type=hidden id="vender" name="vender" value=1 />'+
									'<input type=hidden id="profile_image_url" name="profile_image_url" value="'+container2[count2].user.profile_image_url+'" />'+
									'<input type=hidden id="ori_content" name="ori_content" value="'+container2[count2].text+'" />'+
							
									"<input type='text' name='t_content' id='t_content' class='qtranslateBar' style='width:400px' />"+
								    "<a onClick='translate(\""+container2[count2].id_str+"\")'><img src='img/translate_small_send.gif'  alt='send' width='63' height='24' align='middle' style='border:none;vertical-algin:top'/></a> </p></form></div>";
							html += "</div></div>";
							count2++;
						}
					}	
					
		
					$('#articles').animate({opacity:0}, 500, function(){
						$('#nextButton').remove();
						$('#loading').remove();
					
						$('#articles').html($('#articles').html()+html);
						$('#articles').html($('#articles').html()+"<div id='nextButton'><a onClick=\"fbFetch(1, '"+json.paging.next.replace(/&callback=[a-z|A-Z|0-9|_|=]+/g,"")+"&callback=?"+"', '/t_getTimeline/"+container2[count2-1].id_str+"')\">next!</a></div>");
				 	});
					$('#articles').animate({opacity:1}, 500);							
				
				});
			});

		<%} else if (!user.getFace_access().equals("")) {%>
		
			
			if (mode==0)
			{
				url= "https://graph.facebook.com/me/home?limit=10&access_token="+access+"&date_format=U&fields=id,from,message,comments,likes&callback=?";
			}
			
		
		  var container1 = new Array();
	      $.ajaxSetup({ cache: false }); 
	      $.getJSON(url, function(json){
		    var html = "";
		    //loop through and within data array's retrieve the message variable.

  				$.each(json.data,function(i,fb){				
				container1.push(fb);
		    	});

		
				var count1 = 0;
				
				for(j=0;j<10;j++)
				{
				html+= "<div class='articleBox' style='cursor:hand; cursor: pointer;'><div id='profilePic'>"+
							"<img src='http://graph.facebook.com/"+container1[count1].from.id+"/picture' width='48' height='48' alt='profile' /></div><div id='profileText'>"+
							"<span class='content_id'><img src='img/fb_ico.gif' width='18' height='18' align='absmiddle' /> "+container1[count1].from.name+"</span> </div>"+
							"<div id='timelineContent' onClick='showDetail(\""+container1[count1].id+"\", 0);'><span class='timelineContent_normal'>"+container1[count1].message+"</span></div><div id='articleSubMenu'>"+
							"<span class='articleSubMenu_time'>"+jQuery.timeago(new Date(container1[count1].created_time*1000))+" / </span>";
							//"<img src='img/fb_like.gif' width='17' height='15' align='absmiddle' class='textmiddle' /><span class='articleSubMenu_facebook'>Like&nbsp;"+
							//"<img src='img/fb_cmt.gif' width='17' height='15' align='absmiddle' class='textmiddle' />Comment</span>&nbsp;"+
							html += "<img src='img/translate.gif' width='17' height='15' align='absmiddle' class='textmiddle' /><span class='articleSubMenu_iyoutranslate'><a onClick='$(\"#trans_bar_"+container1[count1].id+"\").css(\"display\", \"block\")'>Translate!</a></span>";
							html += "<div id='trans_bar_"+container1[count1].id+"' style='display:none' >"+
							
									'<form id=\"'+container1[count1].id+'\" name=\"'+container1[count1].id+'\" method=POST>'+
									'<input type=hidden class="sid" id="sid" name="sid" value="'+container1[count1].id+'" />'+
									'<input type=hidden id="voting" name="voting" value="0" />'+
									'<input type=hidden id="ori_lan" name="ori_lan" value="" />'+
									'<input type=hidden id="username" name="username" value="'+container1[count1].from.name+'" />'+
									'<input type=hidden id="userid" name="userid" value="'+container1[count1].from.id+'" />'+
									'<input type=hidden id="t_lan" name="t_lan" value="" />'+
									'<input type=hidden id="vender" name="vender" value=0 />'+
									'<input type=hidden id="profile_image_url" name="profile_image_url" value="http://graph.facebook.com/'+container1[count1].from.id+'/picture" />'+
									'<input type=hidden id="ori_content" name="ori_content" value="'+container1[count1].message+'" />'+
							
									"<input type='text' name='t_content' id='t_content' class='qtranslateBar' style='width:400px' />"+
								    "<a onClick='translate(\""+container1[count1].id+"\")'><img src='img/translate_small_send.gif'  alt='send' width='63' height='24' align='middle' style='border:none;vertical-algin:top'/></a> </p></form></div>";
							html += "</div></div>";

							count1++;
				}	
				
				$('#articles').animate({opacity:0}, 500, function(){
					$('#nextButton').remove();
					$('#loading').remove();
					$('#articles').html($('#articles').html()+html);
					$('#articles').html($('#articles').html()+"<div id='nextButton'><a onClick=\"fbFetch(1, '"+json.paging.next.replace(/&callback=[a-z|A-Z|0-9|_|=]+/g,"")+"&callback=?"+"', '')\">next!</a></div>");
					
			 	});
				$('#articles').animate({opacity:1}, 500);							
			
			});
			
		
		<%} else if (!user.getTwit_authT().equals("")) {%>
				
			if (mode==0)
			{
				url2= "/t_getTimeline";
			}
			
				
			 $.getJSON(url2,  function(oo){
					$.each(oo.statuses, function (i,tw){
						container2.push(tw);
					
					});
					
					var count2 = 0;
					
					for(j=0;j<5;j++)
					{
													
								html+= "<div class='articleBox'  style='cursor:hand; cursor: pointer;'>"+
							"<div id='profilePic'><img src='"+container2[count2].user.profile_image_url+"' width='48' height='48' alt='profile' /></div>"+
							"<div id='profileText'><span class='content_id'><img src='img/tw_ico.gif' width='18' height='18' align='absmiddle' /> "+container2[count2].user.name+"</span> </div>"+
							"<div id='timelineContent' onClick='showDetail(\""+container2[count2].id_str+"\", 1);'><span class='timelineContent_normal'>"+container2[count2].text+"</span></div><div id='articleSubMenu'>"+
							"<span class='articleSubMenu_time'>"+ jQuery.timeago(new Date(Date.parse(container2[count2].created_at.replace(/(\+\S+) (.*)/, '$2 $1'))))+" / </span>"+
							"<img src='img/tw_fav.gif' width='17' height='15' align='absmiddle' class='textmiddle' /><span class='articleSubMenu_twitter'>";
							
							if (container2[count2].favorited == false)						
								html+="<span id='favorite_"+container2[count2].id_str+"' style='width:50px'><a href='#' class='articleSubMenu_twitter' id='a_"+container2[count2].id_str+"' onClick='t_favorite(\""+container2[count2].id_str+"\");'>Favorite&nbsp;</a></span>";
							else
								html+="<span id='favorite_"+container2[count2].id_str+"' style='width:50px'><a href='#' class='articleSubMenu_twitter' id='a_"+container2[count2].id_str+"' onClick='t_cancelfavorite(\""+container2[count2].id_str+"\");'>Favorited&nbsp;</a></span>";
								
							if (container2[count2].retweeted == false)		
								html+= "<span id='retweet_"+container2[count2].id_str+"'><a onClick='t_retweet(\""+container2[count2].id_str+"\")'><img src='img/tw_ret.gif' width='17' height='15' align='absmiddle' class='textmiddle' />Retweet&nbsp;</a></span>";
							else
								html+= "<span id='retweet_"+container2[count2].id_str+"'><img src='img/tw_ret.gif' width='17' height='15' align='absmiddle' class='textmiddle' />Retweetd&nbsp;</span>";
								
							
							html += "<img src='img/translate.gif' width='17' height='15' align='absmiddle' class='textmiddle' /><span class='articleSubMenu_iyoutranslate'><a onClick='$(\"#trans_bar_"+container2[count2].id_str+"\").css(\"display\", \"block\")'>Translate!</a></span>";
							html += "<div id='trans_bar_"+container2[count2].id_str+"' style='display:none' >"+
							
									'<form id=\"'+container2[count2].id_str+'\" method=POST>'+
									'<input type=hidden class="sid" id="sid" name="sid" value="'+container2[count2].id_str+'" />'+
									'<input type=hidden id="voting" name="voting" value="0" />'+
									'<input type=hidden id="ori_lan" name="ori_lan" value="" />'+
									'<input type=hidden id="username" name="username" value="'+container2[count2].user.name+'" />'+
									'<input type=hidden id="userid" name="userid" value="'+container2[count2].user.id_str+'" />'+
									'<input type=hidden id="t_lan" name="t_lan" value="" />'+
									'<input type=hidden id="vender" name="vender" value=1 />'+
									'<input type=hidden id="profile_image_url" name="profile_image_url" value="'+container2[count2].user.profile_image_url+'" />'+
									'<input type=hidden id="ori_content" name="ori_content" value="'+container2[count2].text+'" />'+
							
									"<input type='text' name='t_content' id='t_content' class='qtranslateBar' style='width:400px' />"+
								    "<a onClick='translate(\""+container2[count2].id_str+"\")'><img src='img/translate_small_send.gif'  alt='send' width='63' height='24' align='middle' style='border:none;vertical-algin:top'/></a> </p></form></div>";
							html += "</div></div>";
							count2++;
					}	
					
					$('#articles').animate({opacity:0}, 500, function(){
						$('#nextButton').remove();
						$('#loading').remove();
						$('#articles').html($('#articles').html()+html);
						$('#articles').html($('#articles').html()+"<div id='nextButton'><a onClick=\"fbFetch(1, '', '/t_getTimeline/"+container2[count2-1].id_str+"')\">next!</a></div>");
						
				 	});
					$('#articles').animate({opacity:1}, 500);							
				
			});
		
		<% } %>
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
	
	
	function t_favorite(tid)
	{
		$.getJSON('/twitterAction/favorite/'+tid, function(data){
		        		// do something with a
        		if (data.success==0)
        		{
        			alert("Error!");
        		}
        		else
        		{
        			$('#favorite_'+tid).html("<a href='#' class='articleSubMenu_twitter' id='a_"+tid+"' onClick='t_cancelfavorite(\""+tid+"\");'>Favorited</a>&nbsp;");
        		}
        		//"
 		
		});
	}
	
	function t_cancelfavorite(tid)
	{
		$.getJSON('/twitterAction/cancelfavorite/'+tid, function(data){
		        		// do something with a
        		if (data.success==0)
        		{
        			alert("Error!");
        		}
        		else
        		{
        			$('#favorite_'+tid).html("<a href='#' class='articleSubMenu_twitter' id='a_"+tid+"' onClick='t_favorite(\""+tid+"\");'>Favorite</a>&nbsp;");

        		}
 		
		});
	}
	
	function t_retweet(tid)
	{
		$.getJSON('/twitterAction/retweet/'+tid, function(data){
		        		// do something with a
        		if (data.success==0)
        		{
        			alert("Error!");
        		}
        		else
        		{
        			$('#retweet_'+tid).html("<img src='img/tw_ret.gif' width='17' height='15' align='absmiddle' class='textmiddle' />Retweeted&nbsp;");
        		}

		
		});
	}
	
	
	function t_cancelretweet(tid)
	{
		$.getJSON('/twitterAction/cancelretweet/'+tid, function(data){
		        		// do something with a
        		if (data.success==0)
        		{
        			alert("Error!");
        		}
        		else
        		{
        			alert("Successfully done!");
        		}

		
		});
	}
	
	function fbLike(flag, aid)
	{
		if (flag == 1)
		{
		$.getJSON('/fbLike/like/'+aid, function(data){
		        		// do something with 
		         if (data.success==0)
        		{
        			alert("Error!");
        		}
        		else
        		{
        			alert("Successfully done!");
        		}
		});
		}
	}
	
	function translate(aid)
	{
		$('#'+aid).ajaxForm();			
			
		var queryString = $('#'+aid).formSerialize(); 

		// the data could now be submitted using $.get, $.post, $.ajax, etc 
		$.post('/translate', queryString, function(response){
			    			
			$.getJSON('/translation/'+aid,  function(oo){
				if (oo.result==1)
				{							
					$('#trans_bar_'+aid).html('<p class="qtranslate_content" style="margin:0 0 0 0"><span style="color:gray" style="margin:0 0 0 0">Yours:'+oo.t_content+'</span>');
				}	
			});
		});
			    
	}

</script>

<body class="twoColFixRtHdr" onload=fbFetch(0,"","")>

	<div id="container">
		<div id="header"> <a href="/"><img src="img/logo_top_e.jpg" width="228" height="93" alt="Twitlator" style="margin-left:15px; border:none; vertical-align:middle; font-family: Arial, Helvetica, verdana, sans-serif;" /></a>
		<input type="text" name="searchbar_top" class="searchBar" />
		<span class="menuText"><a href="/">Live Feed</a></span><img src="img/menu_dv.gif" width="32" height="93" border="0" style="vertical-align:middle"/><span class="menuText"><a href="/contributelive">Contribute as Twitlator</a></span><img src="img/menu_dv.gif" width="32" height="93" border="0" style="vertical-align:middle"/><span class="menuText"><a href="/findUsers">Find Twitlator</a></span><img src="img/menu_dv.gif" width="32" height="93" border="0" style="vertical-align:middle"/><span class="menuText"><a href="#" onclick="showHideDiv()">Account ▾</a></span>
		</div><!-- end #header -->

		<div id="sidebar1">
			<%@ include file="/WEB-INF/views/partial/user.jsp" %>
			
		</div><!-- end #sidebar1 -->
	
		<div id="mainContent">
			
			<%@ include file="/WEB-INF/views/partial/write.jsp" %>
			
    
			<div id="timelineSubMenu"><p class="menuText">Timeline</p></div>
			<div id="timelineSubMenu_nonselect" style="width:100px"><p class="menuText"><a href="/translations">Translated</a></p></div>
			
			<p>&nbsp;</p>
			<div id="articles" style="background:#ffffff;width:100%;height:100%;">
			<div id="loading"><p align=center ><img src="/img/loading.gif" /></p></div>
			</div>
  					
		</div><!-- end #mainContent -->
		<br/>
	</div><!-- end #container -->
</body>
</html>
