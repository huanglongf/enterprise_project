$j.extend(loxia.regional['zh-CN'],{
	APPLY_FORM_SELECT : "请选择作业申请单",
	//colNames: ["ID",],
	//colNames: ["ID","","","","","","","",""],
	CODE : "作业单号",
	STATUS : "状态",
	REFSLIPCODE : "相关单据号",
	INTTYPE : "作业类型名称",
	SHOPID : "相关店铺",
	LPCODE : "物流服务商简称",
	ISNEEDINVOICE : "是否需要发票",
	CREATETIME : "创建时间",
	STVTOTAL : "计划执行总件数",
	COUNT_SEARCH : "待出库列表",
	
	SKUNAME : "商品名称",
	BARCODE : "条形码",
	JMCODE : "商品编码",
	KEYPROPERTIES : "扩展属性",
	QUANTITY : "计划执行量",
	DETAIL_INFO : "详细信息"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

function showinventorydetail(obj){
	var pos = $j(obj).offset(),
		w = $j(obj).width(),
		$ttsum = $j("#div-inventory-detail"),
		currLoc = $j(obj).parents("tr").find("td:eq(1)").text();
	if($ttsum.hasClass("hidden") || currLoc != $ttsum.attr("loc")){		
		$ttsum.removeClass("hidden").attr("loc",currLoc).css({
			position: "absolute",
			left: pos.left + w + 5,
			top: pos.top,
			zIndex: 9999
		});
	}else{
		$ttsum.addClass("hidden").css({
			left: "inherit",
			top: "inherit"
		}).attr("loc","");
	}
}

function showStaLine(tag){
	var tr = $j(tag).parents("tr");
	var id = tr.attr("id");
	$j("#staInfo").addClass("hidden");
	$j("#staLineInfo").removeClass("hidden");
	var sta=$j("#tbl-staList-query").jqGrid("getRowData",id);
	$j("#s_code").html(sta["code"]);
	$j("#s_createTime").html(sta["createTime"]);
	$j("#s_slipCode").html(sta["refSlipCode"]);
	$j("#s_owner").html(sta["shopId"]);
	$j("#s_status").html(tr.find("td[aria-describedby$='intStatus']").html());
	$j("#s_type").html(tr.find("td[aria-describedby$='intType']").html());
	$j("#s_trans").html(tr.find("td[aria-describedby$='lpcode']").html());
	$j("#s_inv").html(tr.find("td[aria-describedby$='isNeedInvoice']").html());
	var postData = {};
	postData["sta.id"] = id;
	$j("#tbl_detail_list").jqGrid('setGridParam',
			{url:window.$j("body").attr("contextpath")+"/findSalesStaLine.json",
			postData:postData,page:1}
		).trigger("reloadGrid");
}

var customizationShopMap = {};
var isMqInvoice = false;
$j(document).ready(function (){
	
	var wh = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/findwarehousebaseinfo.json"));
	$j("div[isMqInvoice='true']").addClass('hidden');
	if(!wh.warehouse.isMqInvoice){
		isMqInvoice = false;
	}else{
		isMqInvoice = true;
	}
	
	jumbo.loadShopList("shopId","shopId");
	initShopQuery("shopId","innerShopCode");
	
	//============= 所有定制模版的店铺
	var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/customiztationtemplshop.json");
	var customizationShopArray = null;
	if(rs && rs.returnVal){
		customizationShopArray = rs.returnVal;
		for (var i in customizationShopArray){
			customizationShopMap[customizationShopArray[i]] = customizationShopArray[i]; 
		}
	}
	//=============
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	
	$j("#selIsNeedInvoice").change(function(){
		if($j("#selIsNeedInvoice").val() == 1 && isMqInvoice){
			$j("div[isMqInvoice='true']").removeClass('hidden');
		}else{
			$j("div[isMqInvoice='true']").addClass('hidden');
			$j("#isMqInvoice").attr("checked","");
		}
	});
	
	var trueOrFalse=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"trueOrFalse"});
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	
	$j("#tbl-staList-query").jqGrid({
//		url:window.$j("body").attr("contextpath")+"/json/staPendingList.do",
		datatype: "json",
		//colNames: ["ID","作业单号","相关单据号","作业类型名称","相关店铺","物流服务商简称","是否需要发票","创建时间","计划执行总件数"],
		colNames: ["ID",i18("CODE"),i18("STATUS"),i18("REFSLIPCODE"),i18("INTTYPE"),i18("SHOPID"),i18("LPCODE"),i18("ISNEEDINVOICE"),i18("CREATETIME"),i18("STVTOTAL")],
		colModel: [{name: "id", index: "id", hidden: true},		         
		           {name: "code", index: "code", width: 120,formatter:"linkFmatter",formatoptions:{onclick:"showStaLine"}, resizable: true},
		           {name: "intStatus", index: "intStatus", width: 60, resizable: true,formatter:'select',editoptions:staStatus},
		           {name: "refSlipCode", index: "refSlipCode",width: 120, resizable: true},
		           {name: "intType", index: "intType", width: 100, resizable: true,formatter:'select',editoptions:staType},
		           {name: "shopId", index: "shopId", width: 100, resizable: true},
		           {name: "lpcode", index: "lpcode", width: 80, resizable: true,formatter:'select',editoptions:trasportName},
		           {name: "isNeedInvoice", index: "isNeedInvoice",  width: 80, resizable: true,formatter:'select',editoptions:trueOrFalse},
		           {name: "createTime", index: "createTime", width: 120, resizable: true},
		           {name: "stvTotal", index: "stvTotal", width: 90, resizable: true}],
		caption: i18("COUNT_SEARCH"),//待出库列表
	   	sortname: 'id',
	   	pager:"#pager_query",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:"auto",
	    multiselect: true,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc", 
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#tbl_detail_list").jqGrid({
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
	
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransportator.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selLpcode"));
	}
	
	//行业下拉框 fanht
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findIndustryByOuid.do");
	for(var idx in result){
		$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#industryId"));
	}
	
	$j("#btn-query").click(function(){
		if($j("#skuSelect").val() != "" && $j("#txtSkuCode").val() == "" && $j("#skuQtySelect").val() == ""){
			jumbo.showMsg("按作业单商品种类数配货查询必须填写单SKU编码！");
			return;
		}
		var postData = loxia._ajaxFormToObj("query-form");
		postData["shoplist"] = $j("#postshopInput").val();
		
		//是否包含大件商品 fanht
		if($j("#skuMaxLength").attr("checked")){
				postData["sta.skuMaxLength"] = 1;
		}else{
			postData["sta.skuMaxLength"] = 0;
		}
		
		$j("#tbl-staList-query").jqGrid('setGridParam',{
			url:window.$j("body").attr("contextpath")+"/json/staPendingList.do",
			postData:postData,
			page:1
		}).trigger("reloadGrid");
	});
	
	$j("#autoConfirm").click(function(){
		if($j("#selIsNeedInvoice").val() == ""){
			if(!confirm("确定不选择是否需要发票将继续操作!")){
				return;
			}
		}
		if($j("#selLpcode").val() == ""){
			jumbo.showMsg("请选择物流商！");
			return;
		}
		if(!loxia.byId("autoSize").check() || !loxia.byId("plCount").check()){
			jumbo.showMsg("请填写正确数量！");
			return;
		}
		
		if($j("#skuSelect").val() != "" && $j("#txtSkuCode").val() == "" && $j("#skuQtySelect").val() == ""){
			jumbo.showMsg("按作业单商品种类数配货查询必须填写单SKU编码！");
			return;
		}
		if(!checkMultiCompanyShop()){
			return ;
		}
		 
		var postData = loxia._ajaxFormToObj("query-form");
		postData["limit"] = $j("#autoSize").val();
		postData["plCount"] = $j("#plCount").val();
		postData["shoplist"] = $j("#postshopInput").val();
		if($j("#isMqInvoice").attr("checked")){
			postData["isMqInvoice"] = true;
		}else{
			postData["isMqInvoice"] = false;
		}
		
		//是否包含大件商品 fanht
		if($j("#skuMaxLength").attr("checked")){
				postData["isBigBox"] = true;
				postData["sta.skuMaxLength"] = 1;
		}else{
			postData["isBigBox"] = false;
			postData["sta.skuMaxLength"] = 0;
		}
		
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/generationPendingList.json",postData);
		if(result&&result.result&&result.result=='success'){
			$j("#tbl-staList-query").jqGrid('setGridParam',{url:window.$j("body").attr("contextpath")+"/json/staPendingList.do",page:1}).trigger("reloadGrid");
		}else if(result&&result.result&&result.result=='error'){
			jumbo.showMsg(result.message);
		}
	});

	$j("#reset").click(function(){
		$j("#filterTable input").val("");
		$j("#filterTable select").val("");
		$j("#postshopInput").val("");
		$j("#showShopList").html("");
		//是否包含大件商品 fanht
		$j("#skuMaxLength").attr("checked",false)
	});
	
	$j('#confirm').click(function(){
		var staIds = $j("#tbl-staList-query").jqGrid('getGridParam','selarrrow');
		if(staIds.length>0){
			
			if(!checkMultiCompanyShop()){
				return ;
			}
			var pl = $j("#"+staIds[0]+" td[aria-describedby='tbl-staList-query_lpcode']").text();
			for(var i in staIds){
				var tempPL = $j("#"+staIds[i]+" td[aria-describedby='tbl-staList-query_lpcode']").text();
				if(pl != tempPL){
					jumbo.showMsg("物流商不同不允许创建同一批次里面！");
					return;
				}
			}
			var postData = {};
			for(var i in staIds){
				postData['staIdList[' + i + ']']=staIds[i];
			}
			if($j("#isMqInvoice").attr("checked")){
				postData["isMqInvoice"] = true;
			}else{
				postData["isMqInvoice"] = false;
			}
			
			//是否包含大件商品 fanht
			if($j("#skuMaxLength").attr("checked")){
					postData["isBigBox"] = true;
			}else{
				postData["isBigBox"] = false;
			}
			
			var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/generationPendingListByList.json",postData);
			if(result&&result.result&&result.result=='success'){
				$j("#tbl-staList-query").jqGrid('setGridParam',{url:window.$j("body").attr("contextpath")+"/json/staPendingList.do",page:1}).trigger("reloadGrid");
			}else if(result&&result.result&&result.result=='error'){
				jumbo.showMsg(result.message);
			}
		}else{	
			jumbo.showMsg(loxia.getLocaleMsg("APPLY_FORM_SELECT"));//请选择作业申请单
		}
	});
	
	$j("#back").click(function(){
		$j("#staInfo").removeClass("hidden");
		$j("#staLineInfo").addClass("hidden");
	});
	
});

function checkCompanyShop(){
	var shoplist = $j("#postshopInput").val();
	var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/warehosueisrelateshopforprint.json");
	// error 需要选择店铺 ，success 不是必须选择店铺
	if(result && result.result && result.result=='error'){
		if (shoplist==""){
			jumbo.showMsg("当前仓库装箱清单打印为店铺定制，配货前请选择店铺...");
			return false;
		}
		/*else {
			var s = shoplist.split("|");
			if (s.length > 1){
				var postData = {};
				postData['shoplist'] = shoplist;
				var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/warehosueisrelatemulitshop.json",postData);
				if(rs && rs.result && rs.result=='error'){
					jumbo.showMsg("当前仓库关联的店铺装箱清单打印模版为店铺定制，不能选择多个定制的店铺...");
					return false;
				}
			}
		}*/
	}
	return true;
}


function checkMultiCompanyShop(){
	var shoplist = $j("#postshopInput").val();
	var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/warehosueisrelateshopforprint.json");
	// error 需要选择店铺 ，success 不是必须选择店铺
	if(result && result.result && result.result=='error'){
		if (shoplist==""){
			jumbo.showMsg("当前仓库装箱清单打印为店铺定制，配货前请选择店铺...");
			return false;
		}
	}
	var flag = 0,index=0;
	var s = shoplist.split("|");
	var iteration = [];
	for(var i in s){
		if (customizationShopMap[s[i]]){
			flag++;
			iteration[index++] = customizationShopMap[s[i]];
		}
	}
	/*if (flag > 1){
		jumbo.showMsg("以下定制店铺不能多选 ： " + iteration);
		return false;
	}*/
	if (s.length > 1 && flag > 0){
		jumbo.showMsg("以下定制店铺不能多选 ： " + iteration);
		return false;
	}
	return true;
}


