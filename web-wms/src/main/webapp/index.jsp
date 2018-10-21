<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title><s:text name="title.login"/></title>
<link rel="stylesheet" type="text/css" href="<s:url value='css/redmond/jquery-ui-1.8.7.custom.css' includeParams='none' encode='false'/>"/>
<link rel="stylesheet" type="text/css" href="<s:url value='css/redmond/loxia-ui-1.2.custom.css' includeParams='none' encode='false'/>"/>
<link rel="stylesheet" type="text/css" href="<s:url value='css/main.css' includeParams='none' encode='false'/>"/>
<style>
	html,body {height: 100%; overflow: hidden;}
	body{text-align: center;}
</style>
<script type="text/javascript" src="<s:url value='scripts/main/jquery-1.4.4.min.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='scripts/main/jquery-ui-1.8.7.custom.min.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='scripts/main/jquery.loxiacore-1.2.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript"><!--
var $j = jQuery.noConflict();
$j(document).ready(function (){ 
    var redirectUrl = "<s:url value='/main.do' includeParams='none' encode='false'/>" ;
    var width = screen.width ;
    var height = screen.height ;
    if (height == 768 ) height -= 75 ;
    if (height == 600 ) height -= 60 ;
    var szFeatures = "top=0," ; 
    szFeatures +="left=0," ;
    szFeatures +="width="+width+"," ;
    szFeatures +="height="+height+"," ;
    szFeatures +="directories=no," ;
    szFeatures +="status=yes," ;
    szFeatures +="menubar=no," ;
    if (height <= 600 ) szFeatures +="scrollbars=yes," ;
    else szFeatures +="scrollbars=no," ;
    szFeatures +="resizable=yes" ; //channelmode
    /**
    * 检查窗口是否被阻止
    * updated by cyril on 2008-06-02
    */
    
    //window.opener='ecology';
    window.top.location.href = redirectUrl;
    /*
    var newwin = window.open(redirectUrl,"",szFeatures) ;
    if(newwin==null) {
        var helpurl="<s:url value='/help.htm' includeParams='none' encode='false'/>";
        var yn = confirm("弹出窗口被阻止，无法正常进入系统！\n按【确定】查看帮助，按【取消】以普通模式进入系统。");
        if(!yn) location.href = redirectUrl;
        if(yn) location.href = helpurl;
    }else{
        window.opener='ecology';
        window.open('','_self');
        window.close();
    } */
});
-->
</script>
</head>
<body>
<div id="loginSuccess">
	<center>
		<h3>登录成功</h3>
	</center>
</div>
</body>
</html>
