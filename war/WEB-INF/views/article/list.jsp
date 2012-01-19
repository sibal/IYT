<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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

<body class="twoColFixRtHdr">

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
