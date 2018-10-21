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
	var baseUrl = $j("body").attr("contextpath");
	$j("#rtoId").val(id);
	$j("#tbl-order-detail").jqGrid('setGridParam',{page:1,url:baseUrl + "/findGucciRtoLineList.json?rtoId=" + id}).trigger("reloadGrid");
}


$j(document).ready(function (){
	//STA作业申请单
	$j("#tbl-inbound-purchase").jqGrid({
		url:$j("body").attr("contextpath")+"/findGucciRtnList.json",
		datatype: "json",
		colNames: ["ID","相关单据号","目标地址","创建时间"],
		colModel: [
		           {name: "id", index: "id", hidden: true},		       
		           {name: "orderCode", index: "orderCode",formatter:"linkFmatter", formatoptions:{onclick:"showdetail"}, resizable: true,sortable:false},
	               {name:"toLocation",index:"toLocation",hidden: false},
	               {name: "createTime", index:"createTime" ,hidden: false}],
	             
		caption: "品牌指令列表",
		sortname: 'id',
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
	
	$j("#tbl-order-detail").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("SKUCODE"),i18("BARCODE"),i18("SKU_NAME"),"数量","库存状态"],
		colModel: [{name: "id", index: "id", hidden: true},
		           	{name:"skuCode",index:"skuCode",resizable:true},
					{name:"skuBarcode",index:"skuBarcode",resizable:true},
					{name:"skuName", index:"skuName",resizable:true},
					{name:"qty", index:"qty",resizable:true},
					{name:"invStatus", index:"invStatus",resizable:true}],
		caption: "指令明细",
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
			url:$j("body").attr("contextpath")+"/findGucciRtnList.json",postData:postData}).trigger("reloadGrid");
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
	
	$j("#import").click(function(){
		var rtoId=$j("#rtoId").val();
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg("请选择正确的Excel导入文件");//请选择正确的Excel导入文件
			return false;
		}
		
		loxia.lockPage();
		
			$j("#importForm").attr("action",
					loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/vmiReturnImportGucci.do?rtoId="+rtoId)
			);
		loxia.submitForm("importForm");
		
		
	});
	
	$j("#expDiff").click(function(){
		var rtoId=$j("#rtoId").val();
		$j("#upload").attr("src",$j("body").attr("contextpath") + "/warehouse/exportGucciRtoLineInfo.do?rtoId="+rtoId );	
	});
	
});
	


