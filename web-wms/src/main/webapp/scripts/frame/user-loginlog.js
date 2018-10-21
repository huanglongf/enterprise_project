$j.extend(loxia.regional['zh-CN'],{
	LOBINTIME : "用户登录时间",
	LOGINSTATUS : "登录结果",
	LOGINNAME : "用户名",
	USERNAME : "真实姓名",
	REMOTEADDR : "客户端IP地址",
	USERAGENT : "客户端信息",
	LOGIN_RESULT : "用户登录结果列表"
});

var getPostData=function(){
	return loxia._ajaxFormToObj("sysLogCommandForm");
}

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

$j(document).ready(function(){
			$j("#tbl-userloginlist").jqGrid({
				url:$j("body").attr("contextpath")+"/json/userloginlist.json",
				datatype: "json",
				//colNames: ["用户登录时间","登录结果","用户名","真实姓名","客户端IP地址","客户端信息"],
				colNames: [i18("LOBINTIME"),i18("LOGINSTATUS"),i18("LOGINNAME"),i18("USERNAME"),i18("REMOTEADDR"),i18("USERAGENT")],
				colModel: [{name: "loginTime", index: "s.login_time", width: 150, resizable: true},
				           {name: "loginStatus", index: "s.login_status", width: 150, resizable: true},		         
				           {name: "loginName", index: "s.login_name", width: 150, resizable: true},
				           {name: "userName", index: "u.user_name", width: 150, resizable: true},
				           {name: "remoteAddr", index: "s.remote_addr", width: 150, resizable: true},
			               {name: "userAgent", index: "s.user_agent", width: 200, resizable: true}],
				caption: i18("LOGIN_RESULT"),//用户登录结果列表
				rowNum: jumbo.getPageSize(),
			   	rowList:jumbo.getPageSizeList(),
		   	height:jumbo.getHeight(),
		   	sortname: 's.login_time',
		    pager: '#pager',
			sortorder: "desc",
			postData:getPostData(),
			viewrecords: true,
   			rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" }
			});
			
	jumbo.bindTableExportBtn("tbl-userloginlist");
	
	$j("#reload").click(function(){
		// (4)获得当前postData选项的值  
		var postData = $j("#tbl-userloginlist").jqGrid("getGridParam", "postData");  
		// (5)将查询参数融入postData选项对象  
		$j.extend(postData, getPostData());  
//		$j("#tbl-userloginlist").jqGrid('setGridParam',{url:$j("body").attr("contextpath")+"/json/userloginlist.json",page:1,postData:getPostData()}).trigger("reloadGrid");
		$j("#tbl-userloginlist").trigger("reloadGrid",[{page:1}]);
		jumbo.bindTableExportBtn("tbl-userloginlist",{},$j("body").attr("contextpath")+"/json/userloginlist.json",getPostData());
		});	
	
});