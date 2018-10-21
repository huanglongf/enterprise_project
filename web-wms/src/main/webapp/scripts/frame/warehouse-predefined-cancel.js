var extBarcode = null;

$j.extend(loxia.regional['zh-CN'],{
	ENTITY_STA_CREATE_TIME			:	"作业创建时间",
	ENTITY_STA_CODE					:	"作业单号",
	ENTITY_STA_PROCESS				:	"执行情况",
	ENTITY_STA_UPDATE_TIME			:	"上次执行时间",
	ENTITY_STA_TYPE					:	"作业类型",
	ENTITY_STA_OWNER				:	"店铺名称",
	ENTITY_STA_COMMENT				:	"备注",
	
	STA_LINE_LIST 					: "作业单明细",
	SKUCODE 						: "SKU编码",
	BARCODE 						: "条形码",
	JMCODE 							: "商品编码",
	SKU_NAME 						: "商品名称",
	KEY_PROPS 						: "扩展属性",
	PLAN_QTY 						: "计划量",
	COMFIRMED_QTY 					: "执行量",
	
	OPERATE_FAILED					: "操作失败",
	OPERATE_SUCCESS					: "操作成功",
	SURE_CANCEL						: "是否確定取消",
	SURE_CLOSE						: "是否确认关闭",
	TABLE_CAPTION_STA				:"待收货列表"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

var tempId = null;
function showdetail(obj){
	$j("#detail_tabs").tabs();
	$j("#tbl-order-detail tr:gt(0)").remove();
	$j("#div-sta-list").addClass("hidden");
	$j("#order-detail").removeClass("hidden");
	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");
	//取得被选中的列的值
	var intStatus = $j("#tbl-inbound-purchase").getCell(id,"intStaStatus");
	//隐藏关闭按钮
	$j("#close").addClass("hidden");
	//显示取消按钮
	$j("#cancel").removeClass("hidden");
	//判断是部分转让显示关闭按钮，隐藏取消按钮
	if(intStatus==5){
		$j("#close").removeClass("hidden");
		$j("#cancel").addClass("hidden");
	}	
	tempId = id;
	var pl=$j("#tbl-inbound-purchase").jqGrid("getRowData",id);
	var baseUrl = $j("body").attr("contextpath");
	$j("#createTime").text(pl["createTime"]);
	$j("#finishTime").text(pl["finishTime"]);
	$j("#code").text(pl["code"]);
	$j("#refSlipCode").text(pl["refSlipCode"]);
	$j("#type").text(tr.find("td[aria-describedby$='intStaType']").text());
	$j("#shopId").text(pl["owner"]);
	$j("#tbl-order-detail").jqGrid('setGridParam',{page:1,url:baseUrl + "/gethistoricalOrderDetailList.json?sta.id=" + id}).trigger("reloadGrid");
}


$j(document).ready(function (){
	jumbo.loadShopList("companyshop");
	initShopQuery("companyshop","innerShopCode");
	var statu=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"whSTAStatus"});
	var status=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"whSTAProcessStatus"});
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	if(!status.exception){
		//STA作业申请单
		$j("#tbl-inbound-purchase").jqGrid({
			url:$j("body").attr("contextpath")+"/findpredecancelstabypagination.json",
			datatype: "json",
			colNames: ["ID","STVID","STVTOTAL","STVMODE","相关单据号",i18("ENTITY_STA_CREATE_TIME"),
			           i18("ENTITY_STA_CODE"),i18("ENTITY_STA_PROCESS"),i18("ENTITY_STA_UPDATE_TIME"),i18("ENTITY_STA_TYPE"),
			           i18("ENTITY_STA_OWNER"),//i18("ENTITY_STA_REMANENT"),
			           i18("ENTITY_STA_COMMENT"),"IS_PDA","作业单状态"],
			colModel: [
			           {name: "id", index: "id", hidden: true},		         
			           {name: "stvId", index: "stvId", hidden: true},		         
			           {name: "stvTotal", index: "stv_total", hidden: true},		         
			           {name: "stvMode", index: "stv_mode", hidden: true},	
			           {name: "refSlipCode", index: "refSlipCode", hidden: false},	
			           {name: "createTime", index: "create_time", width: 150, resizable: true,sortable:false},
			           {name: "code", index: "sta.code",formatter:"linkFmatter", formatoptions:{onclick:"showdetail"}, width: 150, resizable: true,sortable:false},
			           {name: "processStatus", index: "status", width: 100, resizable: true,
				        	   formatter:'select',editoptions:status,sortable:false},
			           {name: "inboundTime", index: "inbound_time", width: 150, resizable: true,sortable:false},
			           {name: "intStaType", index:"" ,width:80,resizable:true,formatter:'select',editoptions:staType},
			           {name: "shopId", index: "shopId", width: 120, resizable: true,sortable:false},
		               //{name: "remnant", index: "remnant", width: 100, resizable: true,sortable:false},
		               {name:"memo",index:"memo",width:150,resizable:true,sortable:false},
		               {name:"isPda",index:"isPda",hidden: true},
		               {name: "intStaStatus", index:"intStaStatus" ,width:80,resizable:true,formatter:'select',editoptions:statu}],
		             
			caption: i18("TABLE_CAPTION_STA"),
			sortname: 'sta.create_time',
		   	sortorder: "desc",
		   	multiselect: false,
		    rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
		   	pager:"#pager",
		   	height:jumbo.getHeight(),
			viewrecords: true,
	   		rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" }
		});
	}else{
		jumbo.showMsg(i18("ERROR_INIT"));
	}
	
	$j("#tbl-order-detail").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("SKUCODE"),i18("BARCODE"),i18("JMCODE"),i18("SKU_NAME"),i18("KEY_PROPS"),i18("PLAN_QTY"),i18("COMFIRMED_QTY")],
		colModel: [{name: "id", index: "id", hidden: true},
		           	{name:"skuCode",index:"skuCode",width:80,resizable:true},
					{name:"barCode",index:"barCode",width:100,resizable:true},
					{name:"jmcode", index:"jmcode" ,width:80,resizable:true},
					{name:"skuName", index:"skuName",width:140,resizable:true},
					{name:"keyProperties",index:"keyProperties",width:80,resizable:true},
					{name:"quantity", index:"quantity",width:80,resizable:true},
					{name:"completeQuantity", index:"completeQuantity",width:120,resizable:true}],
		caption: i18("STA_LINE_LIST"),
	   	sortname: 'ID',
	    multiselect: false,
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pagerDetail",
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0"}
	});
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	$j("#search").click(function(){
		var postData = loxia._ajaxFormToObj("form_query");  
		$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
			url:$j("body").attr("contextpath")+"/findpredecancelstabypagination.json",postData:postData}).trigger("reloadGrid");
	});
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val(0);
	});
	$j("#back").click(function(){
		$j("#div-sta-list").removeClass("hidden");
		$j("#order-detail").addClass("hidden");
	});
	$j("#cancel").click(function(){
		var data = {};
		data["sta.id"] = tempId;
		if(window.confirm(loxia.getLocaleMsg("SURE_CANCEL"))){
			 loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+"/json/modifyroleStatus.json",
		        		data,
		  				{
			  			   success:function () {
			          	    	jumbo.showMsg(loxia.getLocaleMsg("OPERATE_SUCCESS"));
			          	    	$j("#div-sta-list").removeClass("hidden");
			          			$j("#order-detail").addClass("hidden");
			          			$j("#tbl-inbound-purchase tr[id='"+tempId+"']").remove();
			  	           },
			               error:function(){
			                	jumbo.showMsg(loxia.getLocaleMsg("OPERATE_FAILED"));	//操作失败！
			               }
		  			});
		}
	});
	//预定义入库 关闭/取消  修改作业单状态和完成时间
	$j("#close").click(function(){
		var data = {};
		data["sta.id"] = tempId;
		if(window.confirm(loxia.getLocaleMsg("SURE_CLOSE"))){
			 loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+"/json/updateTypeAndFinishTimeByid.json",
		        		data,
		  				{
			  			   success:function () {
			          	    	jumbo.showMsg(loxia.getLocaleMsg("OPERATE_SUCCESS"));
			          	    	$j("#div-sta-list").removeClass("hidden");
			          			$j("#order-detail").addClass("hidden");
			          			$j("#tbl-inbound-purchase tr[id='"+tempId+"']").remove();
			  	           },
			               error:function(){
			                	jumbo.showMsg(loxia.getLocaleMsg("OPERATE_FAILED"));	//操作失败！
			               }
		  			});
		}
	});
	
});
	


