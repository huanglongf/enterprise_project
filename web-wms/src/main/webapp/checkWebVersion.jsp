<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link rel="stylesheet" type="text/css" href="<s:url value='css/main.css' includeParams='none' encode='false'/>"/>
<title>WEB服务器版本</title>
<script type="text/javascript" src="<s:url value='scripts/main/jquery-1.4.4.min.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript"><!--
var $j = jQuery.noConflict();

$j(document).ready(function (){
		var postData = {};
		var rs = request($j("body").attr("contextpath") + "/rest/webCheckVersion.json",postData);
		$j("#versionT").html("version："+rs["version"]);
		$j("#versionText").html(rs["memo"]);
});
		
function request(url, data, args){
            url=url+'?version='+new Date();
			var _data, options = this._ajaxOptions(url, data, args);
			$.extend(options,{
				async : false,
				success : function(data, textStatus){
					_data = data;
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
					_data = {};
					var exception = {};
					exception["message"] = "Error occurs when fetching data from url:" + this.url;
					exception["cause"] = textStatus? textStatus : errorThrown;
					_data["exception"] = exception;
				}
			});
			$.ajax(options);
			return _data;
		}
--></script>
<style>
body {
	TEXT-ALIGN: center;
}

</style>
</head>

<body contextpath="<%=request.getContextPath()%>">
		<p style="font-size: 30px;color: red;" id="versionT"><p/>
		<p style="font-size: 30px;" id="versionText"><p/>
</body>
		<%@include file="/pda/commons/common-script.jsp"%>
</html>