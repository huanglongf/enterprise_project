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
	
	$j("#trackingNo").text("");
	
	$j("#query-order-list").addClass("hidden");
	$j("#order-detail").removeClass("hidden");
	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");

	/*var refSlipCodetr=$j(obj).parents("tr[refSlipCode]");
	var refSlipCode=refSlipCodetr.attr("refSlipCode");*/
//	staid = id;
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
	
	//查询快递单号fanht
	var transportNos =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTransportNos.do",{"staId":id});
	if(transportNos.value!=null&&transportNos.value!=""){
		$j("#trackingNo").text(transportNos.value);
	}
	
	$j("#tbl-operate-log tr:gt(0)").remove();
	$j("#tbl-operate-log").jqGrid('setGridParam',{page:1,url:baseUrl + "/selectOperateLog.json?sta.refSlipCode="+pl["refSlipCode"].toString()+"&sta.code="+pl["code"].toString()}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl-operate-log",{},
			baseUrl + "/selectOperateLog.json",{"sta.refSlipCode":pl["refSlipCode"].toString()});
	//特殊行处理
	$j("#tbl-special-line tr:gt(0)").remove();
	$j("#tbl-special-line").jqGrid('setGridParam',{page:1,url:baseUrl + "/selectSpecialLog.json?sta.id=" + id}).trigger("reloadGrid");

}
var isEmsOlOrder = false;
$j(document).ready(function (){
	$j("#detail_tabs").tabs();
	initShopQuery("companyshop","innerShopCode");
	
	var arrStr1 = new Array();
	var arrStr2= new Array();
	$j("#priorityCity").multipleSelect({
		 //multiple: true,
		
		 width: 200,
		 filter:true,
		 placeholder: "请选择",
		 onOpen: function() {
			 //$j("#priorityCity ul li:first").remove();
			 $j(".ms-select-all").remove();
        },
        onClick: function(view) {
           var res = view.label;
    		arrStr1.push(res);
    		var result1 = arrStr1.indexOf("所有优先发货城市");
    		var result2 = arrStr1.indexOf("非优先发货城市");
    		//alert(arrStr1);
    		if (result1 != -1 && result2 != -1) {
    			$j(".ms-choice2 span").text('');
    			$j("#priorityCity").multipleSelect("uncheckAll");
    			arrStr1= [];
    			$j('#priorityCity').multipleSelect('refresh');
    			jumbo.showMsg("不能同时选择'所有优先发货城市'和'非优先发货城市', 请重新选择!");
			} else if (result1 != -1 && arrStr1.length > 1) {
				$j(".ms-choice2 span").text('');
				$j("#priorityCity").multipleSelect("uncheckAll");
    			$j('#priorityCity').multipleSelect('refresh');
    			arrStr1= [];
				jumbo.showMsg("不能同时多选!");
			} else if (result2 != -1 && arrStr1.length > 1) {
				$j(".ms-choice2 span").text('');
				$j("#priorityCity").multipleSelect("uncheckAll");
    			$j('#priorityCity').multipleSelect('refresh');
    			arrStr1= [];
				jumbo.showMsg("不能同时多选!");
			}
        }
	});
	
	$j("#priorityId").multipleSelect({
		 width: 200,
		 filter:true,
		 placeholder: "请选择",
		 onOpen: function() {
			 //$j("#priorityCity ul li:first").remove();
			 $j(".ms-select-all").remove();
      },
      onClick: function(view) {
         var res = view.label;
  		arrStr2.push(res);
  		var result1 = arrStr2.indexOf("所有优先发货省份");
  		var result2 = arrStr2.indexOf("非优先发货省份");
  		//alert(arrStr1);
  		if (result1 != -1 && result2 != -1) {
  			$j(".ms-choice1 span").text('');
  			$j("#priorityId").multipleSelect("uncheckAll");
  			arrStr2= [];
  			$j('#priorityId').multipleSelect('refresh');
  			jumbo.showMsg("不能同时选择'所有优先发货省份'和'非优先发货省份', 请重新选择!");
			} else if (result1 != -1 && arrStr2.length > 1) {
				$j(".ms-choice1 span").text('');
				$j("#priorityId").multipleSelect("uncheckAll");
  			$j('#priorityId').multipleSelect('refresh');
  			arrStr2= [];
				jumbo.showMsg("不能同时多选!");
			} else if (result2 != -1 && arrStr2.length > 1) {
				$j(".ms-choice1 span").text('');
				$j("#priorityId").multipleSelect("uncheckAll");
  			$j('#priorityId').multipleSelect('refresh');
  			arrStr2= [];
				jumbo.showMsg("不能同时多选!");
			}
      }
	});
	
	$j("#tbl-sn-detail").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("SKUCODE"),i18("BARCODE"),i18("JMCODE"),i18("SKU_NAME"),i18("KEY_PROPS"),i18("SN"),i18("DIRECT")],
		colModel: [{name: "id", index: "id", hidden: true},
		           	{name:"skuCode",index:"skuCode",width:120,resizable:true},
					{name:"barCode",index:"barCode",width:120,resizable:true},
					{name:"jmcode", index:"jmcode" ,width:120,resizable:true},
					{name:"skuName", index:"skuName",width:120,resizable:true},
					{name:"keyProperties",index:"keyProperties",width:80,resizable:true},
					{name:"sn", index:"sn",width:150,resizable:true},
					{name:"direction", index:"direction",width:120,resizable:true}],
		caption: i18("SN_DETAIL"),
	   	sortname: 'sn',
	    multiselect: false,
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pagerSn",
		sortorder: "asc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var ids = $j("#tbl-sn-detail").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var datas = $j("#tbl-sn-detail").jqGrid('getRowData',ids[i]);
				var tra = "";
				if(datas["direction"]=="2"){
					tra= "出库";
				} else if(datas["direction"]=="1") {
					tra = "入库";
				}
				$j("#tbl-sn-detail").jqGrid('setRowData',ids[i],{"direction":tra});
			}
		}
	});
	
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	$j("#tbl-operate-log").jqGrid({
		datatype: "json",
		colNames: ["id",i18("STA_CODE"),i18("STA_SLIP_CODE"),i18("CRAETE_TIME"),i18("STA_STATUS"),i18("OPERATOR")],
		colModel: [
		            {name: "id", index: "id", hidden: true},
		           	{name:"code",index:"code",width:120},
					{name:"refSlipCode",index:"refSlipCode",width:120},
					{name:"executionTime", index:"executionTime" ,width:120},
					{name:"intStatus",index:"intStatus",width:70},
					{name:"operator",index:"operator",width:120}],
		caption: i18("OPERATE_LOG_LIST"),
	    multiselect: false,
	    sortname: 'executionTime',
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pagerOperateLog",
		viewrecords: true,
   		rownumbers:true,
   		sortorder: "asc",
		jsonReader: { repeatitems : false, id: "0" },
	});
		/**
		 * 行特殊处理
		 */
	$j("#tbl-special-line").jqGrid({
		datatype: "json",
		colNames: ["id","SKUCODE","商品名称","特殊处理类型","备注","卡号"],
		colModel: [
		            {name: "id", index: "id", hidden: true},
		           	{name:"skuCode",index:"skuCode",width:120},
					{name:"name",index:"name",width:120},
					{name:"typeString", index:"typeString" ,width:120},
					{name:"memo",index:"meno",width:70},
					{name:"sanCardCode",index:"sanCardCode",width:120}],
		caption:"特殊处理",
	    multiselect: false,
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pagerSpecialLog",
		viewrecords: true,
	    sortname: 'id',
   		rownumbers:true,
   		sortorder: "asc",
		jsonReader: { repeatitems : false, id: "0" },
	});
	
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
	//var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	//var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-orderList").jqGrid({
		//url:baseUrl + "/gethistoricalOrderList.json",
		datatype: "json",
		//colNames: ["ID","作业单号","相关单据号","作业类型名称","作业单状态","平台店铺名称","物流服务商","快递单号","创建时间","完成时间","操作人","计划执行总数量"],
		colNames: ["ID",i18("STA_CODE"),i18("STA_SLIP_CODE"),i18("SLIP_CODE1"),i18("SLIP_CODE2"),"合并订单号",i18("STA_TYPE"),i18("STA_STATUS"),"是否加入配货清单",i18("OWNER"),"平台订单时间","是否锁定","目标店铺",
						    "原始仓库","目标仓库","来源方","目标地",i18("LPCODE"),i18("TRACKING_NO"),i18("PRODUCT_SIZE"), i18("TRACKING_AND_SKU"),i18("CRAETE_TIME"),"最近入库时间",
						    i18("FINISH_TIME"),i18("OPERATOR"),"计划执行量","MEMO","批次号","是否货到付款","O2O门店","配货模式","运单时限类型","是否QS","商品分类","是否预售订单","操作"],
		colModel: [
							{name: "id", index: "id", hidden: true},
							{name:"code", index:"code", formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, width:100, resizable:true},
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
							{name:"isCod", index:"isCod",width:80},//是否货到付款
							{name:"o2oShop", index:"o2oShop",width:100,resizable:true},
							{name:"pickingTypeString", index:"pickingTypeString",width:100,resizable:true},
							{name:"transTimeType", index:"transTimeType",width:100,resizable:true},
							{name:"isQs", index:"isQs",width:80,resizable:true},
							{name:"skuCategoriesName", index:"skuCategoriesName",width:100,resizable:true},
							{name:"isPreSale", index:"isPreSale",width:100,resizable:true},
							{name: "idForBtn", width: 80,resizable:true,sortable: false, formatter:"buttonFmatter", formatoptions:{"buttons":{label:"打印相关单据号", onclick:"printSingleDelivery1(this)"}}}
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
			var ids = $j("#tbl-orderList").jqGrid('getDataIDs');
			//获取模糊查询选中的店铺，循环判断勾选弹窗的checkBox
			for(var j = 0 ; j < ids.length ; j++){
				var pickStatus= $j("#tbl-orderList").getCell(ids[j],"pickStatus"); // 上限次数
				if(pickStatus == "是"){
					$j("#tbl-orderList").setCell (ids[j],"pickStatus",pickStatus,{color:'red'});  
				}
			}
		}
	});
	jumbo.bindTableExportBtn("tbl-orderList",{"intType":"whSTAType","intStatus":"whSTAStatus"});
	$j("#tbl-order-detail").jqGrid({
		datatype: "json",
		colNames: [i18("SKUCODE"),i18("BARCODE"),i18("JMCODE"),i18("SKU_NAME"),i18("KEY_PROPS"),i18("PLAN_QTY"),i18("COMFIRMED_QTY")],
		colModel: [
		           	{name:"skuCode",index:"skuCode",width:80,resizable:true},
					{name:"barCode",index:"barCode",width:80,resizable:true},
					{name:"jmcode", index:"jmcode" ,width:80,resizable:true},
					{name:"skuName", index:"skuName",width:120,resizable:true},
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
	
	$j("#tbl-order-detail_operate").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("SKUCODE"),i18("BARCODE"),i18("SKU_NAME"),i18("OWNER"),i18("COMFIRMED_QTY"),i18("DIRECTION"),i18("TYPE_NAME"),i18("LOCATION_CODE"),i18("INV_STATUS"),i18("CRAETE_TIME"),i18("FINISH_TIME"),i18("CREATER"),"商品效期"],
		colModel: [{name: "id", index: "id", hidden: true},
		           	{name:"skuCode",index:"skuCode",width:80,resizable:true},
					{name:"barCode",index:"barCode",width:80,resizable:true},
					{name:"skuName", index:"skuName",width:120,resizable:true},
					{name:"owner", index:"owner" ,width:80,resizable:true},
					{name:"quantity", index:"quantity",width:50,resizable:true},
					{name:"directionInt",index:"directionInt",width:50,resizable:true},
					{name:"typeName",index:"typeName",width:80,resizable:true},
					{name:"locationCode",index:"locationCode",width:80,resizable:true},
					{name:"intInvstatusName", index:"intInvstatusName",width:80,resizable:true},
					{name:"createDate",index:"createDate",width:130,resizable:true},
					{name:"finishDate",index:"finishDate",width:130,resizable:true},
					{name:"creater", index:"creater",width:80,resizable:true},
					{name:"expireDate", index:"expireDate",width:130,resizable:true}],
		caption: i18("DETAIL_QUERY"),
	   	sortname: 'ID',
	    multiselect: false,
	    height:"auto",
	    width:"1000px",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pagerDetail_operate",
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var ids = $j("#tbl-order-detail_operate").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var datas = $j("#tbl-order-detail_operate").jqGrid('getRowData',ids[i]);
				var tra = i18("OUT_WAREHOUSE");
				if(datas["directionInt"]=="1"){
					tra= i18("IN_WAREHOUSE");
				}
				$j("#tbl-order-detail_operate").jqGrid('setRowData',ids[i],{"directionInt":tra});
			}
		}
	});
	
//	$j("#exportPoReport").click(function(){
//		$j("#upload").attr("src",$j("body").attr("contextpath") + "/warehouse/exportPoConfirmReport.do?sta.id="+$j("#searchDetail").attr("staId"));
//	});
//	
//	// 打印装箱清单：
//	$j("#printPoReport").click(function(){
//		loxia.lockPage();
//		var url = $j("body").attr("contextpath") + "/printPoConfirmReport.json?sta.id="+$j("#searchDetail").attr("staId");
//		printBZ(loxia.encodeUrl(url),true);				
//		loxia.unlockPage();
//	});
		
	$j("#back").click(function(){
		$j("#query-order-list").removeClass("hidden");
		$j("#order-detail").addClass("hidden");
	});
	
//	//作业单查询-打印物流面单bin.hu
//	$j("#printDeliveryInfo").click(function(){
//		var staid = $j("#staid").text();//ID
//		var zyCode = $j("#zyCode").text();//作业单号
//		var intStatus = $j("#intStatus").text();//状态
//		var intType = $j("#intType").text();//作业类型名称
//		var ipCode = $j("#ipCode").text();//物流服务
//		var pickListId = $j("#pickListId").text();//批次号ID
////		alert("statid："+staid+" zyCode："+zyCode+" intStatus："+intStatus+" intType："+intType+" ipCode："+ipCode+" pickListId："+pickListId);
//		loxia.lockPage();
//		jumbo.showMsg(i18("DELIVERYINFO"));
//		if(isEmsOlOrder && ('EMS' == ipCode || 'EMSCOD' == ipCode)){
//			jumbo.emsprint(zyCode,pickListId);
//		}else{
//			var url = $j("body").attr("contextpath") + "/printSingleOrderDetail.json?stap.id=" + staid;
//			printBZ(loxia.encodeUrl(url),true);
//		}
//		loxia.unlockPage();
//	});
	 
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
		$j("#isMorePackage").attr("checked",false);
		$j("#isMergeInt").attr("checked",false);
		$j("#isQueryHis").attr("checked",false);
		$j("#isShowMerge").attr("checked",false);
		$j("#isPreSale option:first").attr("selected",true);//重置是否是预售
		$j("#priorityCity").multipleSelect("uncheckAll");
		$j('#priorityCity').multipleSelect('refresh');
		$j("#priorityId").multipleSelect("uncheckAll");
		$j('#priorityId').multipleSelect('refresh');
	});
	
	$j("#printBoxTag").click(function(){
		var staId=$j("#staid").html();
		loxia.lockPage();
		jumbo.showMsg("货箱条码打印");
		var url = $j("body").attr("contextpath") + "/printContainerCode.json?type=print&sta.id=" + staId;
		printBZ(loxia.encodeUrl(url),false);
		loxia.unlockPage();
	});
	
	$j("#printSlipCode").click(function(){
		var staId=$j("#staid").html();
		loxia.lockPage();
		jumbo.showMsg("作业单号打印");
		var url = $j("body").attr("contextpath") + "/printAutoSlipCode.json?sta.id=" + staId;
		printBZ(loxia.encodeUrl(url),false);
		loxia.unlockPage();
	});
	
	$j("#search").click(function(){
		var url =null;

		//是否分包 fanht
		if($j("#isMorePackage").attr("checked")){
			$j("#morePackageValue").val(1);
		}else{
			$j("#morePackageValue").val(0);
		}
		//是否显示合并订单主订单
		if($j("#isShowMerge").attr("checked")){
			$j("#isShowMerge2").val(1);
		}else{
			$j("#isShowMerge2").val(0);
		}
		//是否显示预售订单
		/*if($j("#isPreSale").attr("checked")){
			$j("#isPreSale1").val(1);
		}else{
			$j("#isPreSale1").val(0);
		}*/
		
		//是否查询历史数据
		if($j("#isQueryHis").attr("checked")){
			$j("#isQueryHis2").val(1);
			var createTime =$j("#c1").val();
			var endCreateTime =$j("#e1").val();
			if(createTime=='' || endCreateTime==''){
				 jumbo.showMsg("请填写创建时间范围");return;
			}
			url = baseUrl + "/gethistoricalOrderList.json";//查询历史数据
		}else{
			$j("#isQueryHis2").val(0);
			url= baseUrl + "/gethistoricalOrderList2.json";//不查询历史数据
		}
		//var url = baseUrl + "/gethistoricalOrderList.json";
		var postData = loxia._ajaxFormToObj("queryForm");
		var arrCity = postData["city"];
		var priority = postData["priority"];
		postData["priority"] = priority.join(",");
		postData["city"] = arrCity.join(",");
		$j("#tbl-orderList").jqGrid('setGridParam',{url:url,page:1,postData:postData}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl-orderList",{"intType":"whSTAType","intStatus":"whSTAStatus"},url,loxia._ajaxFormToObj("queryForm"));
	});
	
	$j("#searchDetail").click(function(){
		var id = $j(this).attr("staId");
		$j("#tbl-order-detail_operate").jqGrid('setGridParam',{page:1,url:baseUrl + "/gethistoricalOrderDetailOperateList.json?sta.id=" + id,postData:loxia._ajaxFormToObj("query_detail_form")}).trigger("reloadGrid");
		var vdata = loxia._ajaxFormToObj("query_detail_form")
		vdata["sta.id"]=id;
		jumbo.bindTableExportBtn("tbl-order-detail_operate",{},baseUrl + "/gethistoricalOrderDetailOperateList.json",vdata);
	});
	
	$j("#resetDetail").click(function(){
		$j("#query_detail_form input").val("");
		$j("#query_detail_form select").val("");
	});
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});

	$j("#btnShopFormRest").click(function(){
		$j("#shopQueryDialogForm input").val("");
	});
	
	//初始化O2O目的地编码
	var otoResult = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getallshopstoreforoption.do");
	for(var idx in otoResult){
		$j("<option value='" + otoResult[idx].code + "'>"+ otoResult[idx].name +"</option>").appendTo($j("#selToLocation"));
	}
});
function printSingleDelivery1(obj){
	var id = $j(obj).parent().siblings().eq(3).text();
	 var url=window.$j("body").attr("contextpath")+"/json/printSlipCode.do?slipCodes="+id;
	printBZ(loxia.encodeUrl(url),true);
}