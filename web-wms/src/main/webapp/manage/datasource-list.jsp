<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title><s:text name="title.login"/></title>
<link rel="stylesheet" type="text/css" href="<s:url value='/css/redmond/jquery-ui-1.8.7.custom.css' includeParams='none' encode='false'/>"/>
<link rel="stylesheet" type="text/css" href="<s:url value='/css/redmond/loxia-ui-1.2.custom.css' includeParams='none' encode='false'/>"/>
<link rel="stylesheet" type="text/css" href="<s:url value='/css/main.css' includeParams='none' encode='false'/>"/>
<style>
	.highlight {font-weight: bold;}
	.pad2{padding: 2px;}
</style>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery-1.4.4.min.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery-ui-1.8.7.custom.min.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery.bgiframe.min.js' includeParams='none' encode='false'/>"></script>

<script type="text/javascript" src="<s:url value='/scripts/main/jquery.loxiacore-1.2.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery.loxiainput-1.2.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery.loxia.locale-zh-CN.js' includeParams='none' encode='false'/>"></script>
	
<script type="text/javascript"><!--
var $j = jQuery.noConflict();

$j(document).ready(function (){	
	loxia.init({debug: true, region: 'zh-CN'});	
	
	$j("button[ds]").click(function(){
		if($j("#tablelist").attr("ds") && $j("#tablelist").attr("ds") == $j(this).attr("ds")){
			//do not need to reload		
			if($j("#dbinfo").hasClass("hidden")){
				$j("#dbinfo").removeClass("hidden");
			}else{
				$j("#dbinfo").addClass("hidden");
			}
		}else{
			//reload
			var data = loxia.syncXhrPost($j("body").attr("contextpath") + "/manage/dbmetas.do",
					{ dsId: $j(this).attr("ds")});
			$j("#tablelist").html("");
			var str = "";
			for(var i=0,d; d=data[i]; i++){
				str += "<a href='#'><dt>" + d + "</dt></a>";
			}
			$j(str).appendTo($j("#tablelist"));
			$j("#tablelist").attr("ds",$j(this).attr("ds"));
			
			if($j("#dbinfo").hasClass("hidden"))
				$j("#dbinfo").removeClass("hidden");
		}		
		
	});
	
	$j("dl#tablelist a").live("click", function(){
		var cinfo = $j(this).data("cinfo");
		if(!cinfo){
			var data = loxia.syncXhrPost($j("body").attr("contextpath") + "/manage/tablemetas.do",
					{ dsId: $j("#tablelist").attr("ds"), tableName: $j(this).find("dt").html()});
			cinfo = "";
			for(var i=0,d; d=data[i]; i++){
				str = "<tr>";
				str += "<td>" + d[0] + "</td>";
				str += "<td>" + d[1] + "</td>";
				str += "</tr>";
				cinfo += str;
			}
			$j(this).data("cinfo", cinfo);
		}
		$j("#tablename").html($j(this).find("dt").html());
		$j("#tableinfo table").find("tbody:eq(0)").html(cinfo);
		return false;
	});
	
	$j("#btn-query").click(function(){
		$j("input[name='dsId']").val($j("#tablelist").attr("ds"));
		$j("#searchform")[0].submit();
	});
});
--></script>
<!--<script type="text/javascript" src="<s:url value='/scripts/login.js' includeParams='none' encode='false'/>"></script> -->
</head>
<body contextpath="<%=request.getContextPath() %>">
<h3>Current Datasource List</h3>
<table cellpadding="0" cellspacing="0" class="ui-loxia-table">
<thead>
<tr>
<th class="ui-state-default pad2">Key</th>
<th class="ui-state-default pad2">DriverClass</th>
<th class="ui-state-default pad2">Url</th>
<th class="ui-state-default pad2">UserName</th>
<th class="ui-state-default pad2">Operation</th>
</tr>
</thead>
<tbody>
<s:iterator value="#request.commandList">
<tr <s:if test="dsId == #session.__accountSetId">class="highlight"</s:if>>
<td><s:property value="key"/></td>
<td><s:property value="driverClassName"/></td>
<td><s:property value="url"/></td>
<td><a href="<s:url value='/manage/dsdetail.do'><s:param name='dsId' value='dsId'/></s:url>"><s:property value="username"/></a></td>
<td><button type="button" ds="<s:property value="dsId"/>">查看数据表</button></td>
</tr>
</s:iterator>
</tbody>
</table>
<div id="dbinfo" class="hidden" style="border: 1px solid #00ccff; margin: 15px 0 0 5px; padding: 5px;">
<div style="height: 300px; width: 300px; overflow-x:hidden; overflow-y:visible; float: left; padding-left: 5px;">
<dl id="tablelist">
</dl>
</div>
<div id="tableinfo" style="height: 300px; width: 240px; overflow-x:hidden; overflow-y:visible; float: left; padding-left: 5px;">
<h5 id="tablename"></h5>
<table class="ui-loxia-table" cellpadding="0" cellspacing="0">
<thead>
<tr>
	<th class="ui-state-default pad2">名称</th>
	<th class="ui-state-default pad2">类型</th>
</tr>
</thead>
<tbody>
</tbody>
</table>
</div>
<div style="margin-left: 580px;">
<s:form id="searchform" target="resultframe" action="sqlquery" namespace="/manage">
<input type="hidden" name="dsId" />
<label for="pageSize">读取记录数:</label><s:textfield name="pageSize" value="50" cssClass="ui-loxia-default ui-loxia-active" style="width: 100px;"/>
<br/>
<label for="pageSize">查询语句:</label><textarea name="query" class="ui-loxia-default ui-loxia-active" style="width: 400px;" rows="8"></textarea>
<br/>
<button type="button" id="btn-query">查询</button>
</s:form>
</div>
<div style="clear:both;"><!--  --></div>
</div>
<iframe name="resultframe" width="760" height="400" style="clear:both; border: 1px solid #777;"></iframe>
</body>
</html>