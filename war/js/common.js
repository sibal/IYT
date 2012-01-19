function MM_preloadImages() { //v3.0
	  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
	    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
	    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
	}

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