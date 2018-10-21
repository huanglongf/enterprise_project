$j.extend(loxia.regional['zh-CN'],{
	CORRECT_FILE_PLEASE : "请选择正确的Excel导入文件",
	STA_LIST : "作业单列表",
	STA_LINE_LIST : "作业单明细",
	STA_CODE : "作业单号",
	STA_SLIP_CODE : "相关单据号",
	STA_TYPE : "作业类型名称",
	STA_STATUS : "作业单状态",
	OWNER : "平台店铺名称",
	STATUS : "状态",
	LPCODE: "物流服务商",
	TRACKING_NO : "快递单号",
	CRAETE_TIME : "创建时间",
	FINISH_TIME : "完成时间",
	OPERATOR : "操作人",
	KEYPROPERTIES : "扩展属性",
	PLAN_COUNT : "计划执行总数量",
	OUT_SALES_ORDER_OUTBOUND_SALES : "外部订单销售出库",
	SKUCODE : "SKU编码",
	BARCODE : "条形码",
	JMCODE : "商品编码",
	SKU_NAME : "商品名称",
	KEY_PROPS : "扩展属性",
	PLAN_QTY : "计划量",
	COMFIRMED_QTY : "执行量",
	SKU_COST : "库存成本",
	DETAIL_INFO : "详细信息",
	DIRECTION:"作用方向",
	TYPE_NAME:"作业类型",
	LOCATION_CODE:"库位编码",
	INV_STATUS:"库存状态",
	CREATER:"创建人",
	STA_IMPORY_LIST :"作业单导入成功列表",
	DETAIL_QUERY:"操作明细查询",
	QUANTITY : "数量",
	
	OUT_WAREHOUSE:"出库",
	IN_WAREHOUSE:"入库"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

function addRows(rowDatas){
    $j("#tbl_sta tr[id]").remove();
    for(var d in rowDatas){
    	$j("#tbl_sta").jqGrid('addRowData',rowDatas[d].id,rowDatas[d]);	
   	}
}

function showMsg(msg,txtMsg){
	jumbo.showMsg(msg);
	$j("#errorMsg").val(txtMsg);
}

function showDetail(tag){
	var tr = $j(tag).parents("tr");
	var id = tr.attr("id");
	$j("#showList").addClass("hidden");
	$j("#showDetail").removeClass("hidden");
	var postData = {};
	postData["sta.id"] = id;
	$j("#tbl_showDetail").jqGrid('setGridParam',
			{url:window.$j("body").attr("contextpath")+"/findSalesStaLine.json",
			postData:postData,page:1}
		).trigger("reloadGrid");
	
	postData["staId"]= id;
	var data=loxia.syncXhr(window.$j("body").attr("contextpath") + "/queryStaInfoByid.json",postData);
	if(data){
		if(data.result=="success"){
			$j("#input_staId_exp").val(data.sta.id);
			$j("#input_createTime_exp").val(data.sta.createTime);
			$j("#input_staCode_exp").val(data.sta.code);
			$j("#input_creater_exp").val(data.sta.creater);
			var transName = loxia.syncXhrPost($j("body").attr("contextpath")+"/json/getTrasportName.json",{"optionKey":data.sta.lpcode});
			$j("#input_lpcode").val(transName.value.length>0?transName.value.split(":")[1]:'');
			if(data.sta.intStaType == 25){
				$j("#input_staType_exp").val(i18("OUT_SALES_ORDER_OUTBOUND_SALES"));
			} else {
				$j("#input_staType_exp").val();
			}
			$j("#input_totalSkuQty_exp").val(data.sta.totalSkuQty);
			$j("#input_memo_exp").text(data.sta.memo);
			$j("#div-sta-exp").addClass("hidden");
			$j("#exp-detail").removeClass("hidden");
		}else{
			jumbo.showMsg(i18("GETINFO_EXEPTION"));
		}
	}else{
		jumbo.showMsg(i18("GETINFO_EXEPTION"));
	}
}

$j(document).ready(function (){
	
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	
	$j("#tbl_sta").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("STA_CODE"),i18("STATUS"),i18("STA_TYPE"),i18("OWNER"),i18("CRAETE_TIME")],
		colModel: [{name: "id", index: "id", hidden: true},
					{name :"code",index:"code",formatter:"linkFmatter",formatoptions:{onclick:"showDetail"},width:100,resizable:true},
					{name:"intStatus", index:"status" ,width:70,resizable:true,formatter:'select',editoptions:staStatus},
					{name: "intType", index:"type",width:100,resizable:true,formatter:'select',editoptions:staType},
					{name:"owner", index:"sta.owner",width:130,resizable:true},
					{name:"createTime",index:"sta.create_time",width:130,resizable:true}],
		caption: i18("STA_IMPORY_LIST"),//"作业单导入成功列表",
		rownumbers:true,
		rowNum:-1,
		height:"auto",
	   	sortname: 'ID',
	    multiselect: false,
		sortorder: "desc",
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	
	$j("#tbl_showDetail").jqGrid({
		datatype: "json",
		//colNames: ["ID","商品名称","条形码","商品编码","扩展属性","计划执行量"],
		colNames: ["ID",i18("SKUNAME"),i18("BARCODE"),i18("JMCODE"),i18("KEYPROPERTIES"),i18("QUANTITY")],
		colModel: [
		           {name: "id", index: "id", hidden: true},		         
				   {name: "skuName", index: "skuName", width: 120, resizable: true},
		           {name: "barCode", index: "barCode",width: 120, resizable: true},
		           {name: "jmcode", index: "jmcode", width: 100, resizable: true},
		           {name: "keyProperties", index: "keyProperties", width: 100, resizable: true},
		           {name: "quantity", index: "quantity", width: 120, resizable: true}],
		caption: i18("DETAIL_INFO"),//详细信息
		rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pager",
		height:"auto",
   		sortname: 'id',
     	multiselect: false,
		sortorder: "desc", 
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#import").click(function(){
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));//请选择正确的Excel导入文件
			return false;
		}
		loxia.lockPage();
		$j("#importForm").attr("action",$j("body").attr("contextpath") + "/warehouse/importOutSalsOrder.do");
		$j("#importForm").submit();
	});

	$j("#toBack").click(function(){
		$j("#showList").removeClass("hidden");
		$j("#showDetail").addClass("hidden");
	});
});
