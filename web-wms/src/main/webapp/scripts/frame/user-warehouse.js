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

var _search=function(boollist,warehouseName){
		$j("#tbl-userlist").jqGrid({
		url: window.parent.$j("body").attr("contextpath")+"/json/finduserlist.json?user.intIsEnabled="+"",
		datatype: "json",
		//colNames: ["ID","登入姓名","真实姓名","Email","组织类型","组织","是否有效"],
		colNames: ["ID",i18("LOGINNAME"),i18("USERNAME"),"所属物理仓",i18("EMAIL"),i18("OPERATIONTYPENAME"),i18("OPERATIONNAME"),i18("ISENABLED")],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name: "loginName", index: "u.login_name", width: 120, resizable: true},		         
		           {name: "userName",  width: 200, resizable: true,sortable:false},
		           {name: "whOuId", width: 200, resizable: true,sortable:false,formatter:'select',editoptions:warehouseName},
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
		postData:{"columns":"id,loginName,userName,whOuId,email,operationTypeName,operationName,isenabled"},
		sortorder: "desc",
		viewrecords: true,
		multiselect: true,	
		jsonReader: { repeatitems : false, id: "0" }
	});
	jumbo.bindTableExportBtn("tbl-userlist",{"isenabled":"status"});
};

$j(document).ready(function (){

	//获取仓库
	var resultopc = loxia.syncXhrPost($j("body").attr("contextpath")+"/selectAllPhyWarehouse.json");
	for(var idx in resultopc.pwarelist){
		$j("<option value='" + resultopc.pwarelist[idx].id + "'>"+ resultopc.pwarelist[idx].name +"</option>").appendTo($j("#selTransOpc"));
		$j("<option value='" + resultopc.pwarelist[idx].id + "'>"+ resultopc.pwarelist[idx].name +"</option>").appendTo($j("#selTransOpc1"));
	}
	
	var boollist = loxia.syncXhr(window.parent.$j("body").attr("contextpath")+"/formateroptions.json",{categoryCode:"status"});
	var warehouseName = loxia.syncXhrPost($j("body").attr("contextpath")+"/findAllWhByChoose.json");
	_search(boollist,warehouseName);

		$j("#toback").live("click", function(){
		$j("#idmodify").css("display","none");
		$j("#queryid").css("display","block");
		
	});
		
	$j("#save").click(function (){
		var userId = $j("#lgnId").html();
		var whOuId = $j("#selTransOpc1").val();
		//alert(userId+"--"+whId);
		var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/saveUserWhRef.json?userId="+userId+"&whOuId="+whOuId);
	    if(rs.result=='success'){
	    	jumbo.showMsg("保存成功！");
	    	window.location.reload();
	    }else{
	    	jumbo.showMsg("保存失败！");
	    }
	});

	$j("#import").click(function(){
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg("请选择正确的Excel导入文件");
			return;
		}
		loxia.lockPage();
		$j("#importForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/userWarehouseRefImport.do"));
		loxia.submitForm("importForm");
		setTimeout(function(){
			$j("#tbl-userlist").jqGrid('setGridParam').trigger("reloadGrid");
		},300);
		
	});
	
});
  function searchUser(){
	   var whOuId = $j("#selTransOpc").val();
       var urlx=window.parent.$j("body").attr("contextpath")+"/json/finduserlist.json?whOuId="+whOuId;
	   $j("#tbl-userlist").jqGrid('setGridParam',
	   {url:urlx,postData:loxia._ajaxFormToObj("myform")}).trigger("reloadGrid",[{page:1}]);
	   jumbo.bindTableExportBtn("tbl-userlist",{"isenabled":"status"},urlx,loxia._ajaxFormToObj("myform"));
  }
  
  // 修改
   function modifyUser(){  
     var ids = jQuery("#tbl-userlist").jqGrid('getGridParam','selarrrow');
       if(ids.length==0){
    	 jumbo.showMsg(loxia.getLocaleMsg("DATA_DETAIL_SELECT"));//请选择具体数据！！
    	 return
       }
       if(ids.length>1){
      	 jumbo.showMsg("批量修改请用exl导入");
      	 return
        }
       var userId= $j("#tbl-userlist").getCell(ids[0],"id");
       var loginName= $j("#tbl-userlist").getCell(ids[0],"loginName");
       var whOuId= $j("#tbl-userlist").getCell(ids[0],"whOuId");
       
	    $j("#lgn").empty();
		$j("#lgn").append(loginName);
		$j("#lgnId").empty();
		$j("#lgnId").append(userId);
		$j("#selTransOpc1").val(whOuId);
		
       $j("#idmodify").css("display","block");
       $j("#queryid").css("display","none");
   }
 
   
