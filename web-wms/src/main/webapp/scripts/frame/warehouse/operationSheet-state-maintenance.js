$j.extend(loxia.regional['zh-CN'],{
	STA_LIST : "作业单列表",
	STA_LINE_LIST : "作业单明细",
	STA_CODE : "作业单号",
	STA_SLIP_CODE : "相关单据号",
	SLIP_CODE1:"相关单据号1",
	SLIP_CODE2:"相关单据号2",
	STA_TYPE : "作业类型名称",
	STA_STATUS : "作业单状态",
	OWNER : "平台店铺名称",
	LPCODE: "物流服务商",
	TRACKING_NO : "快递单号",
	CRAETE_TIME : "创建时间",
	FINISH_TIME : "完成时间",
	OPERATOR : "操作人",
	PLAN_COUNT : "计划执行总数量",
	
	SKUCODE : "SKU编码",
	BARCODE : "条形码",
	JMCODE : "商品编码",
	SKU_NAME : "商品名称",
	KEY_PROPS : "扩展属性",
	PLAN_QTY : "计划量",
	COMFIRMED_QTY : "执行量",
	SKU_COST : "库存成本",
	TRACKING_AND_SKU : "快递单号及耗材",
	PRODUCT_SIZE : "商品大小",
	
	DIRECTION:"作用方向",
	TYPE_NAME:"作业类型",
	LOCATION_CODE:"库位编码",
	INV_STATUS:"库存状态",
	CREATER:"创建人",
	
	DETAIL_QUERY:"操作明细查询",
	
	OUT_WAREHOUSE:"出库",
	IN_WAREHOUSE:"入库",
	SN:"SN号",
	DIRECT:"出入库方向",
	SN_DETAIL:"SN号明细",
	DELIVERYINFO:"物流面单打印中，请等待...",
	OPERATE_LOG:"操作日志",
	OPERATE_LOG_LIST:"操作日志列表"
		
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

function showDetail(obj){
	$j("#detail_tabs").tabs();
	// 
	$j("#tbl-order-detail tr:gt(0)").remove();
	
	$j("#query-order-list").addClass("hidden");
	$j("#order-detail").removeClass("hidden");
	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");

	$j("#searchDetail").attr("staId",id);
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-order-detail").jqGrid('setGridParam',{page:1,url:baseUrl + "/gethistoricalOrderDetailList.json?sta.id=" + id}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl-order-detail",{},baseUrl + "/gethistoricalOrderDetailList.json",{"sta.id":id});

	$j("#tbl-order-detail_operate").jqGrid('setGridParam',{page:1,url:baseUrl + "/gethistoricalOrderDetailOperateList.json?sta.id=" + id,postData:loxia._ajaxFormToObj("query_detail_form")}).trigger("reloadGrid");
	
	var tbpdata = loxia._ajaxFormToObj("query_detail_form");
	tbpdata["sta.id"]=id;
	jumbo.bindTableExportBtn("tbl-order-detail_operate",{},baseUrl + "/gethistoricalOrderDetailOperateList.json",tbpdata);
	
	var pl=$j("#tbl-orderList").jqGrid("getRowData",id);
	
	$j("#staid").html(id.toString());//获取staid

	
	$j("#createTime").text(pl["createTime"]);
	$j("#finishTime").text(pl["finishTime"]);
	$j("#sta_memo").text(pl["memo"]);
	$j("#code").text(pl["code"]);
	$j("#refSlipCode").text(pl["refSlipCode"]);
	$j("#type").text(tr.find("td[aria-describedby$='intType']").text());
	
	//bin.hu
	$j("#zyCode").html(pl["code"].toString());//获取作业单号code
	$j("#intStatus").html(pl["intStatus"].toString());//获取状态
	$j("#intType").html(pl["intType"].toString());//获取作业类型名称
	$j("#ipCode").html(pl["lpcode"].toString());//获取物流服务
	$j("#pickListId").html(pl["pickListId"].toString());//批次号
	
	
	if(pl["intType"] == 11){
		$j("#exportPoReport").removeClass("hidden");
		$j("#printPoReport").removeClass("hidden");
	}else{
		$j("#exportPoReport").addClass("hidden");
		$j("#printPoReport").addClass("hidden");
	}
	
	
	
	$j("#status").text(tr.find("td[aria-describedby$='intStatus']").text());
	$j("#shopId").text(pl["owner"]);
	$j("#lpcode").text(tr.find("td[aria-describedby$='lpcode']").text());
	$j("#trackingNo").text(pl["trackingNo"]);
	$j("#operator").text(pl["operator"]);
	
	// --------------
	$j("#tbl-sn-detail tr:gt(0)").remove();

	$j("#tbl-sn-detail").jqGrid('setGridParam',{page:1,url:baseUrl + "/showsndetail.json?sta.id=" + id}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl-sn-detail",{},
			baseUrl + "/showsndetail.json",{"sta.id":id});
	
	$j("#tbl-operate-log tr:gt(0)").remove();
	$j("#tbl-operate-log").jqGrid('setGridParam',{page:1,url:baseUrl + "/selectOperateLog.json?sta.refSlipCode="+pl["code"].toString()}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl-operate-log",{},
			baseUrl + "/selectOperateLog.json",{"sta.refSlipCode":pl["code"].toString()});
}

function confirmModification(tag){
	if(window.confirm('你确定要修改这条数据？')){
		var id = $j(tag).parents("tr").attr("id");
		var postData = {};
		postData["staid"]=id;
		//alert(id)
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/warehouse/staUpdate.do",postData);
		if(result.rs=="success1"){
			jumbo.showMsg("修改成功！,请通知上游系统，不能WMS单独取消！");
			$j("#tbl-orderList").jqGrid('setGridParam',{
				url:$j("body").attr("contextpath")+"/warehouse/gethistoricalOrderList.json"}).trigger("reloadGrid");
		}else if(result.rs=="success2"){
			jumbo.showMsg("修改成功！,请确认外包仓是否已取消,并通知上游系统，不能WMS单独取消！");
			$j("#tbl-orderList").jqGrid('setGridParam',{
				url:$j("body").attr("contextpath")+"/warehouse/gethistoricalOrderList.json"}).trigger("reloadGrid");
		}else if(result.rs=="error1"){
			jumbo.showMsg("合并订单的主订单不允许取消！");
		}
		else{
			jumbo.showMsg("只能修改状态为：'以创建或者配货中的作业单！");
		}
	   return true;
     }else{
        return false;
  }		
}
var isEmsOlOrder = false;
$j(document).ready(function (){
	$j("#detail_tabs").tabs();
	initShopQuery("companyshop","innerShopCode");
	
	
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	

	jumbo.loadShopList("companyshop");
	
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransactionTypeName.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#select"));
	}
	var whinfo = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getWarehouseInfo.json");
	if(whinfo.isEmsOlOrder){
		isEmsOlOrder = true;
	}
	var trueOrFalse=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"trueOrFalse"});
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-orderList").jqGrid({
		//url:baseUrl + "/gethistoricalOrderList.json",
		datatype: "json",
		//colNames: ["ID","作业单号","相关单据号","作业类型名称","作业单状态","平台店铺名称","物流服务商","快递单号","创建时间","完成时间","操作人","计划执行总数量"],
		colNames: ["ID",i18("STA_CODE"),i18("STA_SLIP_CODE"),i18("SLIP_CODE1"),i18("SLIP_CODE2"),"合并订单号",i18("STA_TYPE"),i18("STA_STATUS"),"是否加入配货清单",i18("OWNER"),"平台订单时间","是否锁定","目标店铺",
						    "原始仓库","目标仓库","来源方","目标地",i18("LPCODE"),i18("TRACKING_NO"),i18("PRODUCT_SIZE"), i18("TRACKING_AND_SKU"),i18("CRAETE_TIME"),"最近入库时间",
						    i18("FINISH_TIME"),i18("OPERATOR"),"计划执行量","MEMO","批次号",'修改订单状态'],
		colModel: [
							{name: "id", index: "id", hidden: true},
							{name:"code", index:"code", width:100, resizable:true},
							{name:"refSlipCode",index:"refSlipCode",width:100,resizable:true},
							{name:"slipCode1",index:"slipCode1",width:100,resizable:true},
							{name:"slipCode2",index:"slipCode2",width:100,resizable:true},
							{name:"groupStaCode",index:"groupStaCode",width:100,resizable:true},
							{name:"intType", index:"intType" ,width:70,resizable:true,formatter:'select',editoptions:staType},
							{name:"intStatus",index:"intStatus",width:70,resizable:true,formatter:'select',editoptions:staStatus},
							{name:"pickStatus", index:"pickStatus",width:130,resizable:true},
							{name:"owner", index:"owner",width:130,resizable:true},
							{name: "orderCreateTime", index: "orderCreateTime", width: 120, resizable: true},
							{name:"isLocked",index:"isLocked",width:80,resizable:true,formatter:'select',editoptions:trueOrFalse},
							{name:"addiOwner", index:"sta.addiOwner",width:130,resizable:true},
							{name:"mainName", width:130,resizable:true},
							{name:"addiName", index:"sta.addiName",width:130,resizable:true},
							{name:"fromLocation", index:"sta.fromLocation",width:130,resizable:true},
							{name:"toLocation", index:"sta.toLocation",width:130,resizable:true},
							{name:"lpcode", index:"lpcode",width:80,resizable:true,formatter:'select',editoptions:trasportName},
							{name:"trackingNo", index:"tracking_no",width:120,resizable:true},
							{name:"productSize", index:"productSize",width:120,resizable:true},
							{name:"trackingAndSku", index:"trackingAndSku",width:120,resizable:true},
							{name:"createTime",index:"create_time",width:130,resizable:true},
							{name:"inboundTime",index:"inboundTime",width:130,resizable:true},
							{name:"finishTime",index:"finish_time",width:130,resizable:true},
							{name:"operator", index:"operator",width:100,resizable:true},
							{name:"skuQty", index:"skuQty",width:100,resizable:true},
							{name:"memo", index:"memo",width:100,hidden:true},
							{name:"pickListId", index:"pickListId",width:100,hidden:true},//批次号bin.hu
					        {name: "operator", width: 60, resizable: true,formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("修改"), onclick:"confirmModification(this);"}}}
					 	],
		caption: i18("STA_LIST"),
	   	sortname: 'data.create_time',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#pager",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var ids = jQuery("#tbl-orderList").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
			var id = ids[i];
			//var DeleteBtn = "<a  style='color:blue' onclick='return confirmModification("+id+")' >修改</a>";
			//jQuery("#tbl-orderList").jqGrid('setRowData', ids[i], {Delete: DeleteBtn });
			}
		}
	});
	jumbo.bindTableExportBtn("tbl-orderList",{"intType":"whSTAType","intStatus":"whSTAStatus"});
	
	$j("#back").click(function(){
		$j("#query-order-list").removeClass("hidden");
		$j("#order-detail").addClass("hidden");
	});
	
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
		$j("#isMorePackage").attr("checked",false);
		$j("#isMergeInt").attr("checked",false);
	});
	
	$j("#search").click(function(){
		//是否分包 fanht
		if($j("#isMorePackage").attr("checked")){
			$j("#morePackageValue").val(1);
		}else{
			$j("#morePackageValue").val(0);
		}
		var url = baseUrl + "/gethistoricalOrderList.json";
		$j("#tbl-orderList").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl-orderList",{"intType":"whSTAType","intStatus":"whSTAStatus"},url,loxia._ajaxFormToObj("queryForm"));
	});
	
		
});