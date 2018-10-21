$j.extend(loxia.regional['zh-CN'],{
	APPLY_FORM_SELECT : "请选择作业申请单",
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
var customizationShopMap = {};//当前仓库定制打印模版店铺集合
$j(document).ready(function(){
	initShopQuery("shopId","innerShopCode");//初始化查询条件，根据当前登录用户及所选仓库初始化店铺选择列表
	//根据查询条件进行查询，得到查询结果,可以进行店铺选择一系列操作
	$j("#selTransopc").val($j("#selTrans").val());
	$j("#btnSearchShop").click(function(){
		var whouid=$j("#selTrans").val();
		if(whouid==""){
			jumbo.showMsg("请选择仓库！");
			return;
		}
		setWhOuid(whouid);
		$j("#shopQueryDialog").dialog("open");
	});
	//仓库下拉值改变事件
	$j("#selTrans").change(function(){
		$j("#showShopList").html("");
		var postData={};
		postData["ou.id"]=$j("#selTrans").val();
		//根据当前用户选择的仓库，查询其中所有的定制打印模版的店铺
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/customiztationtemplshopopc.json",postData);
		var customizationShopArray = null;
		if(rs && rs.returnVal){
			customizationShopArray = rs.returnVal;
			for (var i in customizationShopArray){
				customizationShopMap[customizationShopArray[i]] = customizationShopArray[i]; 
			}
		}
	});
	//根据运营中心仓库查询
	var result = loxia.syncXhrPost($j("body").attr("contextpath")+"/selectbyopc.json");
	for(var idx in result.warelist){
		$j("<option value='" + result.warelist[idx].id + "'>"+ result.warelist[idx].name +"</option>").appendTo($j("#selTrans"));
	}
	//初始化物流商信息
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransportator.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selLpcode"));
	}
	//初始化商品分类
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findCategories.do");
	for(var idx in result){
		$j("<option value='" + result[idx].id + "'>" + result[idx].skuCategoriesName+"</option>").appendTo($j("#categoryId"));
	}
	//初始化商品大小列表
	var cList = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findskusize.json");
	for(var i=0;i<cList.length;i++){
		$j("<input type='checkbox' data='"+i+"' id='"+cList[i].id+"'/><label for='"+cList[i].id+"'>"+cList[i].name+"</label>").appendTo($j("#skuConfigtd"));
	}
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	var trueOrFalse=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"trueOrFalse"});
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	//初始化列表
	$j("#tbl-staList-query").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("CODE"),i18("REFSLIPCODE"),i18("SHOPID"),i18("LPCODE"),i18("STATUS"),i18("CREATETIME"),i18("ISNEEDINVOICE"),i18("STVTOTAL"),"是否含SN号商品"],
		colModel: [{name: "id", index: "id", hidden: true},		         
		           {name: "code", index: "code", width: 120,formatter:"linkFmatter",formatoptions:{onclick:"showStaLine"}, resizable: true},
		           {name: "refSlipCode", index: "refSlipCode",width: 120, resizable: true},
		           {name: "shopId", index: "shopId", width: 100, resizable: true},
		           {name: "lpcode", index: "lpcode", width: 80, resizable: true,formatter:'select',editoptions:trasportName},
		           {name: "intStatus", index: "intStatus", width: 60, resizable: true,formatter:'select',editoptions:staStatus},
		           {name: "createTime", index: "createTime", width: 120, resizable: true},
		           {name: "isNeedInvoice", index: "isNeedInvoice",  width: 80, resizable: true,formatter:'select',editoptions:trueOrFalse},
		           {name: "stvTotal", index: "stvTotal", width: 90, resizable: true},
		           {name: "isSn",index:"isSn",width:80,resizable:true,formatter:'select',editoptions:trueOrFalse}
		           ],
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
	//查询按钮功能
	$j("#btn-query").click(function(){
		var postData = loxia._ajaxFormToObj("query-form");
		postData["shoplist"] = $j("#postshopInput").val();
//		postData["sta.skuQty"]=1;
//		postData["sta.isSedKill"]=false;
//		postData["isGroupSku"]=false;
		postData["sta.pickingType"]="SKU_SINGLE";
		var clist = $j("#skuConfigtd input");
		for(var i=0;i<clist.length;i++){
			postData["ssList["+i+"].maxSize"]=-1
			postData["ssList["+i+"].minSize"]=-1
			postData["ssList["+i+"].groupSkuQtyLimit"]=-1
		}
		var checkList = $j("#skuConfigtd input:checked");
		if(checkList.length>0){ 
			for(var i = 0;i<checkList.length;i++){
				postData["ssList["+i+"].maxSize"]=cList[$j(checkList[i]).attr("data")].maxSize;
				postData["ssList["+i+"].minSize"]=cList[$j(checkList[i]).attr("data")].minSize;
				postData["ssList["+i+"].groupSkuQtyLimit"]=cList[$j(checkList[i]).attr("data")].groupSkuQtyLimit;
			}
		}else{
			for(var ss in cList){
				if(cList[ss].isDefault){
					postData["ssList[0].maxSize"]=cList[ss].maxSize;
					postData["ssList[0].minSize"]=cList[ss].minSize;
					postData["ssList[0].groupSkuQtyLimit"]=cList[ss].groupSkuQtyLimit;
				}
			}
		}
		var cenid=$j("#selTrans").val();
		if(cenid==""){
			jumbo.showMsg("请选择仓库！");
			return;
		}
		postData["ou.id"]=cenid;
		$j("#tbl-staList-query").jqGrid('setGridParam',{
			url:loxia.getTimeUrl($j("body").attr("contextpath")+"/json/findStaForPickingByModel.do"),
			postData:postData,
			page:1
		}).trigger("reloadGrid");
	});
	//重置按钮功能
	$j("#reset").click(function(){
		$j("#filterTable input").val("");
		$j("#selIsNeedInvoice option:first").attr("selected",true);
		$j("#selCity option:first").attr("selected",true);
		$j("#selTrans option:first").attr("selected",true);
		$j("#selLpcode option:first").attr("selected",true);
		$j("#selIsSpPg option:first").attr("selected",true);
		$j("#categoryId option:first").attr("selected",true);
		$j("#status option:first").attr("selected",true);
		$j("#isSn option:first").attr("selected",true);
		$j("#postshopInput").val("");
		$j("#showShopList").html("");
		$j("#skuConfigtd input").attr("checked",false);
	});
	//返回按钮功能
	$j("#back").click(function(){
		$j("#staInfo").removeClass("hidden");
		$j("#staLineInfo").addClass("hidden");
	});
	//生成配货清单按钮功能
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
			postData['pickingList.checkMode']="PICKING_CHECK";
			var cenid=$j("#selTrans").val();
			if(cenid==""){
				jumbo.showMsg("请选择仓库！");
				return;
			}
			postData["ou.id"]=cenid;
			var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/generpickinglistbysta.json",postData);
			//if(result&&result.finish&&result.finish=='true'){//成功后更新原始查询条件列表
			if(result&&result.result){//成功后更新原始查询条件列表
				var postData1 = loxia._ajaxFormToObj("query-form");
				postData1["shoplist"] = $j("#postshopInput").val();
//				postData1["sta.skuQty"]=1;
//				postData1["sta.isSedKill"]=false;
//				postData1["isGroupSku"]=false;
				postData1["sta.pickingType"]="SKU_SINGLE";
				var clist = $j("#skuConfigtd input");
				for(var i=0;i<clist.length;i++){
					postData1["ssList["+i+"].maxSize"]=-1
					postData1["ssList["+i+"].minSize"]=-1
					postData1["ssList["+i+"].groupSkuQtyLimit"]=-1
				}
				var checkList = $j("#skuConfigtd input:checked");
				if(checkList.length>0){
					for(var i = 0;i<checkList.length;i++){
						postData1["ssList["+i+"].maxSize"]=cList[$j(checkList[i]).attr("data")].maxSize;
						postData1["ssList["+i+"].minSize"]=cList[$j(checkList[i]).attr("data")].minSize;
						postData1["ssList["+i+"].groupSkuQtyLimit"]=cList[$j(checkList[i]).attr("data")].groupSkuQtyLimit;
					}
				}else{
					for(var ss in cList){
						if(cList[ss].isDefault){
							postData1["ssList[0].maxSize"]=cList[ss].maxSize;
							postData1["ssList[0].minSize"]=cList[ss].minSize;
							postData1["ssList[0].groupSkuQtyLimit"]=cList[ss].groupSkuQtyLimit;
						}
					}
				}
				var cenid=$j("#selTrans").val();
				postData1["ou.id"]=cenid;
				$j("#tbl-staList-query").jqGrid('setGridParam',{url:window.$j("body").attr("contextpath")+"/json/findStaForPickingByModel.do",postData:postData1,page:1}).trigger("reloadGrid");
				//if(result&&result.result&&result.result=='error'){
				if(result&&result.result=='error'){
					jumbo.showMsg(result.message);
				}
			}
//			else if(result&&result.finish&&result.finish=='false'){
//				jumbo.showMsg(result.message);
//			}
		}else{	
			jumbo.showMsg(loxia.getLocaleMsg("APPLY_FORM_SELECT"));//请选择作业申请单
		}
	});
	//自动生成配货批
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
		if($j("#autoSize").val()<0||$j("#plCount").val()<0){
			jumbo.showMsg("请填写正确数量！");
			return;
		}
		if($j("#plCount").val()!=""){
			if($j("#autoSize").val()==""){
				jumbo.showMsg("请选择每批创建单据数量！");
				return;
			}
		}
		if(!checkMultiCompanyShop()){
			return ;
		}
		 
		var postData = loxia._ajaxFormToObj("query-form");
		postData["limit"] = $j("#autoSize").val();
		postData["plCount"] = $j("#plCount").val();
		postData["shoplist"] = $j("#postshopInput").val();
//		postData["sta.skuQty"]=1;
//		postData["sta.isSedKill"]=false;
//		postData["isGroupSku"]=false;
		postData["sta.pickingType"]="SKU_SINGLE";
		postData['pickingList.checkMode']="PICKING_CHECK";
		var clist = $j("#skuConfigtd input");
		for(var i=0;i<clist.length;i++){
			postData["ssList["+i+"].maxSize"]=-1
			postData["ssList["+i+"].minSize"]=-1
			postData["ssList["+i+"].groupSkuQtyLimit"]=-1
		}
		var checkList = $j("#skuConfigtd input:checked");
		if(checkList.length>0){
			for(var i = 0;i<checkList.length;i++){
				postData["ssList["+i+"].maxSize"]=cList[$j(checkList[i]).attr("data")].maxSize;
				postData["ssList["+i+"].minSize"]=cList[$j(checkList[i]).attr("data")].minSize;
				postData["ssList["+i+"].groupSkuQtyLimit"]=cList[$j(checkList[i]).attr("data")].groupSkuQtyLimit;
			}
		}else{
			for(var ss in cList){
				if(cList[ss].isDefault){
					postData["ssList[0].maxSize"]=cList[ss].maxSize;
					postData["ssList[0].minSize"]=cList[ss].minSize;
					postData["ssList[0].groupSkuQtyLimit"]=cList[ss].groupSkuQtyLimit;
				}
			}
		}
		var cenid=$j("#selTrans").val();
		if(cenid==""){
			jumbo.showMsg("请选择仓库！");
			return;
		}
		postData["ou.id"]=cenid;
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/autogenerdispatchList.json",postData);
		//if(result&&result.finish&&result.finish=='true'){//成功后更新原始查询条件列表
		if(result&&result.result){//成功后更新原始查询条件列表
			var postData1 = loxia._ajaxFormToObj("query-form");
			postData1["shoplist"] = $j("#postshopInput").val();
//			postData1["sta.skuQty"]=1;
//			postData1["sta.isSedKill"]=false;
//			postData1["isGroupSku"]=false;
			postData1["sta.pickingType"]="SKU_SINGLE";
			var clist = $j("#skuConfigtd input");
			for(var i=0;i<clist.length;i++){
				postData1["ssList["+i+"].maxSize"]=-1
				postData1["ssList["+i+"].minSize"]=-1
				postData1["ssList["+i+"].groupSkuQtyLimit"]=-1
			}
			var checkList = $j("#skuConfigtd input:checked");
			if(checkList.length>0){
				for(var i = 0;i<checkList.length;i++){
					postData1["ssList["+i+"].maxSize"]=cList[$j(checkList[i]).attr("data")].maxSize;
					postData1["ssList["+i+"].minSize"]=cList[$j(checkList[i]).attr("data")].minSize;
					postData1["ssList["+i+"].groupSkuQtyLimit"]=cList[$j(checkList[i]).attr("data")].groupSkuQtyLimit;
				}
			}else{
				for(var ss in cList){
					if(cList[ss].isDefault){
						postData1["ssList[0].maxSize"]=cList[ss].maxSize;
						postData1["ssList[0].minSize"]=cList[ss].minSize;
						postData1["ssList[0].groupSkuQtyLimit"]=cList[ss].groupSkuQtyLimit;
					}
				}
			}
			var cenid2=$j("#selTrans").val();
			postData1["ou.id"]=cenid2;
			$j("#tbl-staList-query").jqGrid('setGridParam',{url:window.$j("body").attr("contextpath")+"/json/findStaForPickingByModel.do",postData:postData1,page:1}).trigger("reloadGrid");
			//if(result&&result.result&&result.result=='error'){
			if(result&&result.result=='error'){
				jumbo.showMsg(result.message);
			}
		}
//		else if(result&&result.finish&&result.finish=='false'){
//			jumbo.showMsg(result.message);
//		}
	});
});
//作业单明细
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
	$j("#s_type").html("类型");
	$j("#s_trans").html(tr.find("td[aria-describedby$='lpcode']").html());
	$j("#s_inv").html(tr.find("td[aria-describedby$='isNeedInvoice']").html());
	var postData = {};
	postData["sta.id"] = id;
	$j("#tbl_detail_list").jqGrid('setGridParam',
			{url:window.$j("body").attr("contextpath")+"/findPickingStaLine.json",
			postData:postData,page:1}
		).trigger("reloadGrid");
}
function checkMultiCompanyShop(){
	var shoplist = $j("#postshopInput").val();
	var postData={};
	postData["ou.id"]=$j("#selTrans").val();
	var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/warehosueisrelateshopforprintopc.json",postData);
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
	if (s.length > 1 && flag > 0){
		jumbo.showMsg("以下定制店铺不能多选 ： " + iteration);
		return false;
	}
	return true;
}
function checkHaveFlush(){
	return true;
}