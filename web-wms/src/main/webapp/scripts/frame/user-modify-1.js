var $j = jQuery.noConflict();

$j.extend(loxia.regional['zh-CN'], {
	DATA_DETAIL_SELECT : "请选择具体数据！！",
	DATA_ERROR : "操作的数据异常！",
	SURE_BATCHING_INEFFECTIVENESS: "是否确定批量设定无效？",
	BATCHINE_INEFFECTIVENESS_SUCCESS : "批量设定无效成功",
	DATA_ERROR : "操作的数据异常，批量设定无效失败！",

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
		url: window.parent.$j("body").attr("contextpath")+"/json/finduserlist.json?user.intIsEnabled="+"",
		datatype: "json",
		//colNames: ["ID","登入姓名","真实姓名","Email","组织类型","组织","是否有效"],
		colNames: ["ID",i18("LOGINNAME"),i18("USERNAME"),i18("EMAIL"),i18("OPERATIONTYPENAME"),i18("OPERATIONNAME"),i18("ISENABLED")],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name: "loginName", index: "u.login_name", width: 120, resizable: true},		         
		           {name: "userName",  width: 200, resizable: true,sortable:false},
	               {name: "email", width: 200, resizable: true,sortable:false},
				   {name: "operationTypeName",  width: 200, resizable: true,sortable:false},				  
				   {name: "operationName",  width: 200, resizable: true,sortable:false},
				    {name: "isenabled", index: "u.is_enabled", width: 100,sortable:false, resizable: true,formatter:'select',editoptions:boollist}],
		caption: i18("USER_LIST"),//用户列表
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

		$j("#toback").live("click", function(){
		$j("#idmodify").css("display","none");
		$j("#queryid").css("display","block");
		
	});
});
  function searchUser(){
       var urlx=window.parent.$j("body").attr("contextpath")+"/json/finduserlist.json";
   $j("#tbl-userlist").jqGrid('setGridParam',
   {url:urlx,postData:loxia._ajaxFormToObj("myform")}).trigger("reloadGrid",[{page:1}]);
   jumbo.bindTableExportBtn("tbl-userlist",{"isenabled":"status"},urlx,loxia._ajaxFormToObj("myform"));
  }
   function modifyUser(){  
     var ids = jQuery("#tbl-userlist").jqGrid('getGridParam','selarrrow');
       if(ids.length==0){
    	 jumbo.showMsg(loxia.getLocaleMsg("DATA_DETAIL_SELECT"));//请选择具体数据！！
    	 return
     }
     var id = ids[0];
         loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+"/json/finduserbyid.do",
				{
				"user.id":id
				},
				{
			   success:function (data) {
			   $j("#idmodify").css("display","block");
		       $j("#queryid").css("display","none");
			    var ln = data.loginName;
			    $j("#lgn").empty();
				$j("#lgn").append(ln);
				$j("#myform1 #userName").val(data.userName);
				$j("#myform1 #phone").val(data.phone);
				$j("#myform1 #jobNumber").val(data.jobNumber);
				$j("#myform1 #email").val(data.email);
				$j("#myform1 #userid").val(data.id);
				$j("#myform1 #groupId").val(data.groupId);
				$j("#myform1 #memo").val(data.memo);
				$j("#myform1 #maxNum").val(data.maxNum);
				  var uid =data.id;
				 var url=window.parent.$j("body").attr("contextpath")+"/json/findrolelistbyid.json?user.id="+uid
				  jQuery("#tbl-userrole").jqGrid('setGridParam',
                 {url:url}).trigger("reloadGrid",[{page:1}]); 
			   }, 
			   error:function(){
				 jumbo.showMsg(loxia.getLocaleMsg("DATA_ERROR"));	//操作的数据异常！
               }
			   
			}); 
     
   }
   function deleleUser(){
	     var ids = jQuery("#tbl-userlist").jqGrid('getGridParam','selarrrow');
	       if(ids.length==0){
	    	 jumbo.showMsg(loxia.getLocaleMsg("DATA_DETAIL_SELECT"));//"请选择具体数据"
	    	 return;
	     }
    if(window.confirm(loxia.getLocaleMsg("SURE_BATCHING_INEFFECTIVENESS"))){	   //是否确定批量设定无效？  	
    	    var data = {};
    	    $j.each(ids,function(i, item) {
    		 data["userList[" + i + "].id"] = item;
    	  });         
          loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+"/json/deleleuserlist.do",
				data,
				{
			   success:function (data) {
        	     if(data.rs=='true'){
        	    	   searchUser();	
        	    jumbo.showMsg(loxia.getLocaleMsg("BATCHINE_INEFFECTIVENESS_SUCCESS"));  //批量设定无效成功
        	     }			   										
			   }, 
			   error:function(){
				 jumbo.showMsg(loxia.getLocaleMsg("DATA_ERROR"));	//操作的数据异常，批量设定无效失败！
               }
			});    
		}
   }
