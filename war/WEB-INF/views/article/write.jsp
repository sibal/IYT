
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<Title>IYOU Translate: Live Feed</title>

<style type="text/css"> 
<!-- 
textarea:focus, input:focus, select:focus{
	/* firefox, chrome 등 텍스트 상자, 드롭다운메뉴 등 선택 시 테두리 생기는 것 없앰 */
	outline:none
}
img{
	/* 모든 이미지의 테두리 없앰 (IE 등에서 미설정시 기본으로 테두리가 생김) */
	border:none
}
body {
	font: 100% Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
	background: #e7e7e7;
	background-image:url(img/bg.gif);
	background-repeat:repeat-x;
	margin: 0; /* it's good practice to zero the margin and padding of the body element to account for differing browser defaults */
	padding: 0;
	text-align: center; /* this centers the container in IE 5* browsers. The text is then set to the left aligned default in the #container selector */
	color: #000000;
}
.twoColFixRtHdr #container {
	/* 전체 콘텐츠를 담는 컨테이너 */
	width: 979px;  /* using 20px less than a full 800px width allows for browser chrome and avoids a horizontal scroll bar */
	background: #e7e7e7;
	margin: 0 auto; /* the auto margins (in conjunction with a width) center the page */
	border: 0px;
	text-align: left; /* this overrides the text-align: center on the body element. */
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
}


/* ---------------------------------------- 상단 콘텐츠 ---------------------------------------- */

.twoColFixRtHdr #header {
	/* 최상단 로고와 메뉴 등이 들어가는 어두운 회색 영역 */
	height: 93px;
	background: #808080;
	background-image:url(img/bg.gif); repeat-x;
	padding: 0 10px 0 0;  /* this padding matches the left alignment of the elements in the divs that appear beneath it. If an image is used in the #header instead of text, you may want to remove the padding. */
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
} 

.searchBar {
	/* 최상단 검색창 텍스트 필드 스타일 */
	width: 183px;
	background: transparent url('img/search_top.gif') no-repeat;
	color : #747862;
	height:28px;
	border:0;
	padding:0 0 0 12px;
	margin: 0px 55px 8px 15px;
	text-align:left;
	vertical-align:middle;
	font-size:14px;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕"
}

.menuText {
	/* 최상단 메뉴 텍스트 스타일 */
	color: #8AAC00;
	font-size: 12px;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
	letter-spacing:-20;
	text-align: center;
}



/* ---------------------------------------- 왼쪽 메인콘텐츠 ---------------------------------------- */

.twoColFixRtHdr #mainContent {
	/* 왼쪽 메인 콘텐츠 전체 컨테이너 */
	float:left;
	width:562px;
	height:auto;
	margin: 30px 0 0 0;
	padding: 5px 0 0 20px;
	background:#f9f9f9;
	font-family: Verdana, Arial, Helvetica, sans-serif;
} 

.postingArea {
	/* 포스팅 입력창 */
	width: 530px;
	color : #747862;
	height:56px;
	border:solid thin #DDD;
	margin:0;
	padding:4px;
	text-align:left;
	font-size:12px;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕"
}

.twoColFixRtHdr #postingVia {
	/*  트위터나 페이스북으로 번역 요청 글 연동시켜 포스팅하기 */
	float:left;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
	color:#AAA;
	font-size:12px;
	margin: 15px 4px 0 0;
	width:85px;
}

.twoColFixRtHdr #postingCategoryTitle {
	/*  트위터나 페이스북으로 번역 요청 글 연동시켜 포스팅하기 : 카테고리 레이블 */
	float:left;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
	color:#AAA;
	font-size:12px;
	margin: 15px 4px 0 0;
	width:65px;
	height:21px;
	vertical-align:middle;
}

.twoColFixRtHdr #postingCategory {
	/*  트위터나 페이스북으로 번역 요청 글 연동시켜 포스팅하기 : 카테고리 선택영역 */
	float:left;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
	color:#AAA;
	font-size:12px;
	margin: 15px 4px 0 0;
	width:277px;
}

.twoColFixRtHdr #postingBtn {
	/*  트위터나 페이스북으로 번역 요청 글 연동시켜 포스팅하기 : 포스팅 버튼 */
	float:left;
	width: 100px;
	margin: 10px 20px 20px 0;
	text-align:right;
	height:32px;
	vertical-align:middle;
}

.twoColFixRtHdr #timelineSubMenu {
	/* 포스팅 텍스트 박스 아래 서브메뉴(선택된 것) */
	position: relative;
	left: 0;
	top: 0px;
	background-image:url(img/ptmenu_bg_2.gif);
	background-repeat:no-repeat;
	z-index : 3;
	float:left;
	width:91px;
	margin: 0;
	padding: 0;
	background-color:#f9f9f9;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
	z-index:2;
	height: 37px;
}

.twoColFixRtHdr #timelineSubMenu_nonselect {
	/* 포스팅 텍스트 박스 아래 서브메뉴(선택된 안 된 것) */
	position: relative;
	left: 0;
	top: -1px;
	z-index : 3;
	float:left;
	height: 35px;
	margin: 0;
	padding: 0;
	background-color:#f9f9f9;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
}

.twoColFixRtHdr #articleBox{
	/* 가운데 개개의 게시물 영역 */
	float:left;
	background-color:#fff;
	background-image:url(img/table_line.gif);
	background-repeat:repeat-x;
	width:542px;
	height:auto;
	margin: -2px 0 0 -20px;
	padding: 20px 20px;
	font-family: 바탕, Verdana, Tahoma, Arial, Helvetica, sans-serif;
	z-index:1;
}

.twoColFixRtHdr #profilePic {
	/* 게시글 사용자 프로필 사진 */
	float:left;
	width:60px;
	margin: 0;
	background:#f9f9f9;
}

.twoColFixRtHdr #profileText {
	/* 게시글 사용자 프로필 아이디와 이름 영역 */
	float:left;
	width:480px;
	margin: 0 0 6px 0;
	background:#fff;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
}

.content_id {
	/* 게시글 사용자 프로필 아이디 텍스트 */
	font-size: 10pt;
	font-weight:bold;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
	color:#444;
}

.content_name {
	/* 게시글 사용자 프로필 이름 텍스트 */
	font-size: 10pt;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
	color:#555;
}

.twoColFixRtHdr #timelineContent {
	/* 게시글 내용 영역 */
	float:left;
	width:480px;
	margin: 0 0 4px 0;
	line-height:110%;
	background:#fff;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
}

.timelineContent_link {
	/* 게시글 내용 하이퍼링크 스타일 */
	font-size: 10pt;
	font-family:Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
	color:#8aac00;
}

.timelineContent_normal {
	/* 게시글 내용 텍스트 스타일 */
	font-size: 10pt;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
	color:#555;
}

.twoColFixRtHdr #articleSubMenu {
	/* 게시글 아래에 시간, 리트윗twitter, 라이크facebook 등 들어가는 영역 */
	float:left;
	width:480px;
	margin: 0 10px 0 60px;
	background:#fff;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
}

.articleSubMenu_time {
	/* 게시글 아래 시간 텍스트 스타일 */
	font-size: 8pt;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
	color:#AAA;
}

.articleSubMenu_twitter {
	/* 게시글 아래 트위터 메뉴 텍스트 스타일 */
	font-size: 8pt;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
	color:#00bee9;
}

.articleSubMenu_facebook {
	/* 게시글 아래 페이스북 메뉴 텍스트 스타일 */
	font-size: 8pt;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
	color:#2757a5;
}

.articleSubMenu_iyoutranslate {
	/* 게시글 아래 트랜스레이트 메뉴 텍스트 스타일 */
	font-size: 8pt;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
	color:#8aa900;
}



/* ---------------------------------------- 오른쪽 사이드 콘텐츠 ---------------------------------------- */

.twoColFixRtHdr #sidebar1 {
	/* 오른쪽 다단 전체 컨테이너 */
	
	float: right;
	width: 356px;
	background: #f9f9f9;
	padding: 20px 20px 20px;
	margin-top:30px;
}

.twoColFixRtHdr #profileIdNameDiv {
	/* 사이드 개인 프로필 영역 아이디와 이름 박스 */
	float:left;
	width:220px;
	margin: 0 10px 4px 0;
	background:#f9f9f9;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
}

.profile_id {
	/* 사이드 개인 프로필 영역 아이디 텍스트 스타일 */
	font-size: 12pt;
	font-weight:bold;
	font-family:Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
	color:#444;
}

.profile_name {
	/* 사이드 개인 프로필 영역 이름 텍스트 스타일 */
	font-size: 11pt;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
	color:#666;
}

.twoColFixRtHdr #editProfileDiv {
	/* 사이드 개인 프로필 영역 프로필 수정 박스 */
	float:left;
	width:220px;
	margin: 0 10px 2px 0;
	background:#f9f9f9;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
}

.edit_profile {
	/* 사이드 개인 프로필 영역 프로필 수정 텍스트 스타일 */
	font-size: 9pt;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
	color:#888;
}

.twoColFixRtHdr #statArea {
	/* 사용내역 통계 전체 컨테이너 */
	float:left;
	width:336px;
	height:65px;
	padding:0 0 0 16px;
	background-image:url(img/stat_bg.gif);
	background-repeat:no-repeat;
	margin: 0 0 0 0;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
}

.twoColFixRtHdr #statDetail_number {
	/* 사용내역 통계 숫자 영역 */
	float:left;
	width:80px;
	height:20px;
	text-align:center;
	vertical-align:middle;
	padding:12px 0 0 0;
	margin: 0 0 0 0;
	font-size:14px;
	color:#8aac00;
	font-weight:bold;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
}

.twoColFixRtHdr #statDetail_text {
	/* 사용내역 통계 텍스트 영역 */
	float:left;
	width:80px;
	height:24px;
	text-align:center;
	vertical-align:middle;
	padding:8px 0 0 0;
	margin: 0 0 0 0;
	font-size:11px;
	color:#969696;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
}

.side_title {
	/* 오른쪽 사이드 타이틀 텍스트 */
	font-size: 12pt;
	font-family:Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
	font-weight:bold;
	color:#676767;
	margin-top:20px;
}

.side_content {
	/* 오른쪽 사이드 내용 텍스트 */
	margin:4px 0;
	font-size: 12px;
	color:#868686;
	line-height:150%;
}

.side_content_smaller {
	/* 오른쪽 사이드 내용 앞의 작은 레이블 */
	font-size: 7pt;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
	color:#8aac00;
}

.fanOf {
	/* 오른쪽 사이드 팬 리스트 */
	float:left;
	width:360px;
	height:22px;
	padding:0;
	margin: 0;
}



/* ---------------------------------------- hide/show 메뉴 ---------------------------------------- */

.hideShowMenuStyle {
	/* hide/show 메뉴 전체 틀 스타일 */
	float:right;
	position:relative;
	top:-653px;
	left: 20px;
	z-index:3;
	background-image:url(img/hs_menu_bg.png);
	background-repeat:no-repeat;
    width:178px;
    height:155px;
    margin:0 auto;
}

.twoColFixRtHdr #hsm_pic {
	/* hide/show 메뉴 프로필 사진 */
	float:left;
	width:20px;
	height:20px;
	padding:0;
	margin: 22px 8px 0 22px;
}

.twoColFixRtHdr #hsm_id {
	/* hide/show 메뉴 아이디 */
	float:left;
	width:90px;
	height:auto;
	padding:0;
	margin: 22px 0 0 0;
	text-align:left;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
	color:#fff;
	font-size:12px;
}

.twoColFixRtHdr #hsm_menu {
	/* hide/show 메뉴 텍스트 스타일 */
	float:left;
	width:120px;
	height:auto;
	padding:0;
	margin: 21px 0 0 23px;
	text-align:left;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
	color:#AAA;
	font-size:12px;
}


-->
</style>

<style type="text/css">
a:link {color:#ababab; text-decoration:none;}    /* unvisited link */
a:visited {color:#ababab; text-decoration:none;}    /* unvisited link */
a:hover {color:#ffffff; text-decoration:none;}    /* unvisited link */
a:active {
	color:#ababab;
	text-decoration:none;
	font-family: Verdana, Tahoma, Arial, Helvetica, sans-serif, "맑은 고딕";
}    /* unvisited link */
</style>

<script type="text/javascript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}
//-->
</script>

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
    </script>
    
    
    <SCRIPT>
var imageURL = "img/viatwitter.gif";

if (document.images) {
     var twitter = new Image();
     twitter.src = "img/viatwitter.gif";

     var twitterOn = new Image();
     twitterOn.src = "img/viatwitter-on.gif";
}

function changeImage_tw() {
     if (document.images) {
          if (imageURL == "img/viatwitter-on.gif") imageURL = "img/viatwitter.gif";
          else imageURL = "img/viatwitter-on.gif";

         document.twitterIcon.src = imageURL;
     }
}

</SCRIPT>

    <SCRIPT>
var imageURL = "img/viafacebook.gif";

if (document.images) {
     var twitter = new Image();
     twitter.src = "img/viafacebook.gif";

     var twitterOn = new Image();
     twitterOn.src = "img/viafacebook-on.gif";
}

function changeImage_fb() {
     if (document.images) {
          if (imageURL == "img/viafacebook-on.gif") imageURL = "img/viafacebook.gif";
          else imageURL = "img/viafacebook-on.gif";

         document.fbIcon.src = imageURL;
     }
}

</SCRIPT>
    
</head>

<body class="twoColFixRtHdr">

<div id="container">
  <div id="header"> <a href="5.html"><img src="img/logo_top_e.jpg" width="228" height="93" alt="Twitlator" style="margin-left:15px; border:none; vertical-align:middle; font-family: Arial, Helvetica, verdana, sans-serif;" /></a>
    <input type="text" name="searchbar_top" class="searchBar" />
<span class="menuText"><a href="5.html">Live Feed</a></span><img src="img/menu_dv.gif" width="32" height="93" border="0" style="vertical-align:middle"/><span class="menuText"><a href="9.html">Contribute as Twitlator</a></span><img src="img/menu_dv.gif" width="32" height="93" border="0" style="vertical-align:middle"/><span class="menuText"><a href="10.html">Find Twitlator</a></span><img src="img/menu_dv.gif" width="32" height="93" border="0" style="vertical-align:middle"/><span class="menuText"><a href="#" onclick="showHideDiv()">Account ▾</a></span>
  <!-- end #header --></div>

<div id="sidebar1">
			<div id="profilePic"><img src="img/profile_90_90.gif" width="48" height="48" alt="profile" /></div>
			<div id="profileIdNameDiv"><span class="profile_id">johnniek7</span> <span class="profile_name"> 조형욱</span></div>
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

		</div><!-- end #sidebar1 -->
		
		<div id="mainContent">
			<%@ include file="/WEB-INF/views/partial/write.jsp" %>
			
			<div id="timelineSubMenu"><p class="menuText">Timeline</p></div>
			<div id="timelineSubMenu_nonselect" style="width:100px"><p class="menuText"><a href="#">Translated</a></p></div>
			<div id="timelineSubMenu_nonselect" style="width:100px"><p class="menuText"><a href="#">Languages ▾</a></p></div>
			<div id="timelineSubMenu_nonselect" style="width:100px"><p class="menuText"><a href="#">Categories ▾</a></p></div>
			
			<p>&nbsp;</p>

  			<%@ include file="/WEB-INF/views/partial/articles.jsp" %>
		</div><!-- end #mainContent -->
		<br/>
	</div><!-- end #container -->
</body>
</html>
