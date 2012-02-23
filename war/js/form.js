function update(data) {
    l = ['name', 'username', 'password', 'password_c', 'nick'];
    for(var t in l) 
	$('#c_' + l[t]).html('&nbsp;&nbsp;<img src="img/ok.gif" align="absbottom" width="15" height="25" /><span style="color:#99aa06">Verified</span>');// ok normal dda000 bad ff0000'
    
    l = 0;
    for(var idx in data) {
	$('#c_' + idx).html('&nbsp;&nbsp;<img src="img/bad.gif" align="absbottom" width="15" height="25" /><span style="color:#ff0000">' + data[idx] + '</span>');// ok normal dda000 bad ff0000'
	l++;
    }
    return l;

}

function check() {
    $('#signupform').ajaxSubmit(function(data) { 
	update($.parseJSON(data));
    }); 
}


function submit() {
    $('#signupform').ajaxSubmit(function(data) { 
	res = update($.parseJSON(data));
	if(res == 0) {
	    $('#signupform').attr({action: '/signup'});
	    $('#signupform').submit();
	}
    }); 
}

$(function() { 
    check();
    fields = $('#signupform input');
    for(var c in fields) {
	fields[c].addEventListener('keyup', check);
	fields[c].addEventListener('change', check);
	fields[c].addEventListener('focus', check);
    }
});