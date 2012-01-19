<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<p class="side_title">Post sentences you want to translate</p>

<form:form name="writeform" action="/write" method="POST"> 

<form:hidden path="vender" value="FACEBOOK"/>
<form:textarea class="postingArea" onclick="postingBar.style.display='block';" path="content" name="parea" id="parea"></form:textarea>

<div id="postingBar">
	<div id="postingVia">Via: <img src="img/viafacebook.gif" name="fbIcon" onClick="changeImage_fb()" alt="facebook" width="22" height="21" align="absmiddle" /><img src="img/viatwitter.gif" name="twitterIcon" onClick="changeImage_tw()" style="margin-left:2px" alt="twitter" width="22" height="21" align="absmiddle" /></div>
	<div id="postingCategoryTitle">Category:<img src="img/space_h21px.gif" width="1" height="21" align="absmiddle" /></div>
	<div id="postingCategory">
		<form:select path="category" class="s_select" name="selectLanguage1" size="1">
			<%=	iyt.enums.Category.toHTML()	%>
		</form:select>
	</div>
	<div id="postingBtn"><input type="image" src="img/btn_post.gif" /></div>
</div>

</form:form>