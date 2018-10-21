$j.extend(loxia.regional['zh-CN'],{
	RETURN_ORDER_ERROR:"请输入绑定指令号！",
	EXECUTEXCEPTION:"操作数据异常",
	RETURN_ORDER_SUCCESS:"退仓指令绑定成功！",
	RETURN_ORDER_REMOVE_BINDING_SUCCESS:"已解除绑定！"	
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}
function showMsg(msg){
	jumbo.showMsg(msg);
}

function showDetail(obj){
	
	$j("#divPd").addClass("hidden");
	$j("#divList").removeClass("hidden");
 	var id =$j(obj).parents("tr").attr("id");
// 	jumbo.bindTableExportBtn("tbl-details",{"intType":"whSTAType","intStatus":"whSTAStatus"},$j("body").attr("contextpath")+"/vmireturndetails.json?",{"staID":id});
	var vals =  $j("#tbl-query-info").jqGrid('getRowData',id);
	$j("#staCode").html(vals["code"]);
	$j("#createTime").html(vals["createTime"]);
	$j("#shop").html(vals["store"]);
	$j("#brandOrder").focus();
	$j("#brandOrder").val(vals["refSlipCode"]);
}

$j(document).ready(function (){
	jumbo.loadShopList("owner");//加载店铺
	var baseUrl = $j("body").attr("contextpath");
	var status=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"whSTAStatus"});
	$j("#tbl-query-info").jqGrid({
		url:baseUrl + "/vmireturnstaquery.json",
		datatype: "json",
		colNames: ["ID","作业单号","创建时间","状态","状态","店铺","相关单据号","","运送模式"],
		colModel: [
	            {name: "id", index: "id", hidden: true},		         
	            {name: "code",index:"code",width:150,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, resizable: true},
				{name: "createTime",index:"createTime",width:150,resizable:true},
				{name: "status", index: "status",width: "80", resizable: true,hidden:true},
				{name: "intStatus", index: "status",width: "80", resizable: true,
				        	   formatter:'select',editoptions:status,sortable:false},
				{name:"store",index:"store",width:200,resizable:true},
				{name:"refSlipCode",index:"refSlipCode",width:150,resizable:true},
				{name:"strType",index:"strType",width:30,resizable:true,hidden:true},
				{name:"freightType",index:"freightType",width:30,resizable:true,hidden:true}
				],
		caption: "VMI退仓作业单",
		pager:"pager",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		height:"auto",
   		sortname: 'id',
    	multiselect: false,
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
//		gridComplete: function(){
//			var ids = $j("#tbl-query-info").jqGrid('getDataIDs');
//			for(var i=0;i < ids.length;i++){
//				var datas = $j("#tbl-query-info").jqGrid('getRowData',ids[i]);
//				var statusVal= "";
//				if(datas["status"]=="CREATED"){
//					statusVal= i18("STATUSNEW");
//				}else if(datas["status"]=="OCCUPIED"){
//					statusVal= i18("STATUSOCCUPIED");
//				}else if(datas["status"]=="FINISHED"){
//					statusVal= i18("STATUSFINISHED");
//				}else if(datas["status"]=="CANCELED"){
//					statusVal= i18("STATUSCANCELED");
//				}else if(datas["status"]=="PACKING"){
//					statusVal= i18("STATUSPACKING");
//				}else {
//				}
//				$j("#tbl-query-info").jqGrid('setRowData',ids[i],{"status":statusVal});
//				$j("#tbl-query-info").jqGrid('setRowData',ids[i],{"intStaType":i18("STATYPE")});
//			}
//		}
	});
	
	$j("#query").click(function(){
		var errors=loxia.validateForm("form_query");
		if(errors.length>0){
			jumbo.showMsg(errors);
			return false;
		}
		var postData = loxia._ajaxFormToObj("form_query");  							 
		$j("#tbl-query-info").jqGrid('setGridParam',{url:$j("body").attr("contextpath")+"/vmireturnstaquery.json",postData:postData}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl-query-info",
			{"intStatus":"whSTAStatus","transaction":"whDirectionMode"},
			$j("body").attr("contextpath")+"/vmireturnstaquery.json",postData);
	});
	
	//绑定
	$j("#binding").click(function(){
		var returnOrder=$j("#brandOrder").val();
		if(null==returnOrder || ""==returnOrder){
			jumbo.showMsg(i18("RETURN_ORDER_ERROR"));
			return;
		}
		var staCode=$j("#staCode").text();
		var postData={};
		postData["refSlipCode"]=returnOrder;
		postData["orderCode"]=staCode;
		loxia.asyncXhrPost(	$j("body").attr("contextpath") + "/vmireturnOrderbindingConfirm.json",
				postData,	
				{
			success:function(data){
				if(data){
					if(data.result=="success"){
			jumbo.showMsg(i18("RETURN_ORDER_SUCCESS"));
			$j("#divPd").removeClass("hidden");
			$j("#divList").addClass("hidden");
			  window.location.reload();//刷新当前页面
		}else if(data.result=="error"){
			jumbo.showMsg(data.message);
		}
	}
	},
	error:function(data){jumbo.showMsg(i18("EXECUTEXCEPTION"));}
	}
	);
	});
	
	//解除绑定
	$j("#removeBinding").click(function(){
		var staCode=$j("#staCode").text();
		var returnOrder=$j("#brandOrder").val();
		var postData={};
		postData["refSlipCode"]=returnOrder;
		postData["orderCode"]=staCode;
		loxia.asyncXhrPost(	$j("body").attr("contextpath") + "/vmireturnRemorebinding.json",
				postData,	
				{
			success:function(data){
				if(data){
					if(data.result=="success"){
			jumbo.showMsg(i18("RETURN_ORDER_REMOVE_BINDING_SUCCESS"));
			$j("#divPd").removeClass("hidden");
			$j("#divList").addClass("hidden");
			  window.location.reload();//刷新当前页面
		}else if(data.result=="error"){
			jumbo.showMsg(data.message);
		}
	}
	},
	error:function(data){jumbo.showMsg(i18("EXECUTEXCEPTION"));}
	}
	);
		
	});
	
	//返回
	$j("#back").click(function(){
		$j("#divPd").removeClass("hidden");
		$j("#divList").addClass("hidden");
//		  window.location.reload();//刷新当前页面
	});
	$j("#reset").click(function(){
		$j("#form_query input,#form_query select").val("");
	});
});
