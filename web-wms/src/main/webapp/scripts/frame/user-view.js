var $j = jQuery.noConflict();

$j.extend(loxia.regional['zh-CN'], {
	DATA_SELECT : "请选择具体数据",

	LOGINNAME : "登入姓名",
	USERNAME : "真实姓名",
	EMAIL : "Email",
	OPERATIONTYPENAME : "组织类型",
	OPERATIONNAME : "组织",
	ISENABLED : "是否有效",
	USER_LIST : "用户列表"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

var _search=function(boollist){

		$j("#tbl-userlist").jqGrid({
		url:window.parent.$j("body").attr("contextpath")+"/json/finduserlist.do?user.intIsEnabled="+"",
		datatype: "json",
		//colNames: ["ID","登入姓名","真实姓名","Email","组织类型","组织","是否有效"],
		colNames: ["ID",i18("LOGINNAME"),i18("USERNAME"),i18("EMAIL"),i18("OPERATIONTYPENAME"),i18("OPERATIONNAME"),i18("ISENABLED")],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name: "loginName", index: "u.login_name", width: 120, resizable: true},		         
		           {name: "userName",  width: 200, resizable: true,sortable:false},
	               {name: "email",  width: 200, resizable: true,sortable:false},
				   {name: "operationTypeName",  width: 200, resizable: true,sortable:false},				  
				   {name: "operationName",  width: 200, resizable: true,sortable:false},
				    {name: "isenabled",index: "u.is_enabled",  width: 100,sortable:false, resizable: true,formatter:'select',editoptions:boollist}],
		caption: i18("USER_LIST"),//	用户列表
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		height:jumbo.getHeight(),
   	sortname: 'u.is_enabled desc, u.login_name desc,u.id',
   	pager: '#pager9',
	postData:{"columns":"id,loginName,userName,email,operationTypeName,operationName,isenabled"},
	sortorder: "desc", 
	 viewrecords: true,
	multiselect: true,	
	jsonReader: { repeatitems : false, id: "0" }
	});
	jumbo.bindTableExportBtn("tbl-userlist",{"isenabled":"status"});
	}
$j(document).ready(function (){
	var boollist = loxia.syncXhr(window.parent.$j("body").attr("contextpath")+"/formateroptions.json",{categoryCode:"status"});
	_search(boollist);

});
  function searchUser(){
       var loginName = $j("#myform #loginName").val();
       var isEnabled = $j("#myform #isEnabled").val();
      
       var userName = $j("#myform #userName").val();
       var email = $j("#myform #email").val();
        var groupId = $j("#myform #groupId").val();
       var urlx=window.parent.$j("body").attr("contextpath")+"/json/finduserlist.json";
       var d = {'user.loginName':loginName,'user.userName':userName
       ,'user.memo':isEnabled,'user.email':email,'userGroup.id':groupId};
   jQuery("#tbl-userlist").jqGrid('setGridParam',
   {url:urlx,postData:loxia._ajaxFormToObj("myform")}).trigger("reloadGrid",[{page:1}]); 
   jumbo.bindTableExportBtn("tbl-userlist",{"isenabled":"status"},urlx,loxia._ajaxFormToObj("myform"));
  }
  
    function showInfoUser(){
     var ids = $j("#tbl-userlist").jqGrid('getGridParam','selarrrow');
      if(ids.length==0){
    	 return loxia.getLocaleMsg("DATA_SELECT");
     }
	  $j.each(ids,function(i, item) {
    		 viewRoleInfo(item);
    	  });
     }
      function viewRoleInfo(id){
    var d = $j("#tbl-userlist").jqGrid('getRowData',id);
     var url =window.parent.$j("body").attr("contextpath")+"/auth/viewuserinfoentry.do?user.id="+id;  
     	jumbo.openFrame("frm-" + (new Date()).getTime().toString(),d.userName,url);

    }
    
  
 
  