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
	DETAIL_INFO : "详细信息",
	
	ROLE_LIST : "现有配货规则列表",
	ROLE_NAME : "规则名称",
	CREATE_NAME :"创建人",
	CREATE_TIME : "创建时间",
	LAST_CHANGE_TIME : "最后修改时间",
	OPTION : "操作",
	DISABLED_SUCCESS : "配货规则禁用成功！",
	OPTIONAL_CONDITION : "配货规则可选条件",
	CURRENT_RULE_CONDITION_DETAIL : "当前规则条件明细",
	CONDITION_CODE : "条件编码",
	NAME : "条件描述",
	GROUP_CODE : "条件组编码",
	GROUP_NAME : "条件组描述",
	KVALUE : "条件值",
	TYPE : "条件类型",
	DELETE : "删除",
	ID_IS_LIVE : "不能重复添加配货规则明细！",
	RULE_NAME_IS_NOT_NULL : "规则名称不能为空！",
	RULE_NAME_IS_EXIST : "规则名称已经存在！",
	SKU_BRACODE_IS_NOT_NULL : "SKU编码不能为空！",
	CURRENT_RULE_CONDITION_DETAIL_IS_NULL : "新增配货规则时，当前规则条件明细不能为空！"
});
function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
var customizationShopMap = {};//当前仓库定制打印模版店铺集合
var mtCode = "";
var cList = "";


function showDetail(tag){
    var id = $j(tag).parents("tr").attr("id");
    var data=$j("#tbl-ruleList").jqGrid("getRowData",id);
    var ruleId = data["id"];
    var postData = {};
    postData['distributionRule.id'] = ruleId;
	$j("#tbl_pickingList_rule_dialog").jqGrid('setGridParam',{
		url:window.$j("body").attr("contextpath")+"/json/getDistributionRuleConditionCurrentDetailPagination.json",
		postData:postData,
		page:1
	}).trigger("reloadGrid");
    
	$j("#pickingListRuleDialog").dialog("open");
}


$j(document).ready(function(){
	$j("#btnSearchArea").click(function(){
		$j("#pickAreaQueryDialog").dialog("open");
	});
	
    var postData = {};
    postData["distributionRule.name"] = "";
	$j("#tbl-ruleList").jqGrid({
		url:$j("body").attr("contextpath") + "/findAllDistributionRule.json",
		datatype: "json",
		postData: postData,
		colNames: ["ID",i18("ROLE_NAME"),i18("CREATE_NAME"),i18("CREATE_TIME"),i18("LAST_CHANGE_TIME"),i18("STATUS"),"选择"],
		colModel: [
				   {name: "id", index: "id", hidden: true},
		           {name: "name", index: "name", formatter:"linkFmatter", formatoptions:{onclick:"showDetail"}, width:120,resizable:true},
		           {name: "createName", index: "createName", width: 150, resizable: true},
		           {name: "createTime", index: "createTime", width: 150, resizable: true},
		           {name: "lastModifyTime", index: "lastModifyTime", width: 150, resizable: true},
		           {name: "strStatus", index: "strStatus", hidden: true},
		           {name:"isAuto",width:50}
		           ],
		caption: i18("ROLE_LIST"),
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: false,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc",
		hidegrid:false,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var ids = $j("#tbl-ruleList").jqGrid('getDataIDs');
			var radio = "";
			for(var i=0;i < ids.length;i++){
				var idData = $j("#tbl-ruleList").jqGrid('getRowData',ids[i]);
				radio = "<input type='radio' id= '"+idData["id"]+"' name = 'isAutoRadio' value='"+idData["name"]+"' onclick='getVal(this)'/>";
				$j("#tbl-ruleList").jqGrid('setRowData',ids[i],{"isAuto":radio});
			}
			loxia.initContext($j(this));
		}
	});
	
	$j("#pickingListRuleDialog").dialog({title: "配货规则明细查询", modal:true, autoOpen: false, width:520});
 	var postData = {};
	postData['distributionRule.id'] = "";
	$j("#tbl_pickingList_rule_dialog").jqGrid({
		url:$j("body").attr("contextpath") + "/getDistributionRuleConditionCurrentDetailPagination.json",
		datatype: "json",
		postData:postData,
		colNames: ["ID","规则ID","条件ID","内容",i18("CODE"),i18("GROUP_NAME"),i18("GROUP_CODE"),i18("NAME"),i18("KVALUE"),i18("TYPE"),i18("OPTION")],
		colModel: [
				   {name: "id", index: "id", hidden: true},
				   {name: "l_ruleId", index: "l_ruleId", hidden: true},
				   {name: "l_conditionId", index: "l_conditionId", hidden: true},
				   {name: "remark", index: "remark", hidden: true},
				   {name: "code", index: "code", hidden: true},
		           {name: "groupName", index: "groupName", width:220,resizable:true},
		           {name: "groupCode", index: "groupCode", hidden: true},
		           {name: "name", index: "name", width: 220, resizable: true},
		           {name: "kValue", index: "kValue", hidden: true},
		           {name: "intType", index: "intType",  hidden: true},
		           {name: "deleteAdd1", width: 80, resizable: true,hidden: true}
		           ],
		caption: i18("CURRENT_RULE_CONDITION_DETAIL"),
	   	sortname: 'id',
	   	rowNum: -1,
	   	height:"auto",
	    multiselect: false,
	    rowNum: 200,
	    rownumbers:true,
	    width:500,
	    viewrecords: true,
		sortorder: "desc",
		hidegrid:false,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var ids = $j("#tbl_pickingList_rule_dialog").jqGrid('getDataIDs');
			var name = "";
			for(var i=0;i < ids.length;i++){
				var idData = $j("#tbl_pickingList_rule_dialog").jqGrid('getRowData',ids[i]);
				if(idData && idData["groupCode"] == "SKUCode"){
					$j("#tbl_pickingList_rule_dialog").jqGrid('setRowData',ids[i],{"groupName":idData["name"]});
					name = '<label style="width:220px;">'+idData["remark"]+'</label>';
				}else{
					name = '<label style="width:220px;">'+idData["name"]+'</label>';
				}
				$j("#tbl_pickingList_rule_dialog").jqGrid('setRowData',ids[i],{"name":name});
				
			}
			loxia.initContext($j(this));
		}
	});
	
	initShopQuery("shopId","innerShopCode");//初始化查询条件，根据当前登录用户及所选仓库初始化店铺选择列表
	//根据查询条件进行查询，得到查询结果,可以进行店铺选择一系列操作
	$j("#btnSearchShop").click(function(){
		var showShopList = $j("#postshopInputName").val();
		showShopList = showShopList.substring(0,showShopList.length-1);
		var shopArray = showShopList.split("|");
		var ids = $j("#tbl_shop_query_dialog").jqGrid('getDataIDs');
		//获取模糊查询选中的店铺，循环判断勾选弹窗的checkBox
		for(var i = 0 ; i < shopArray.length; i++){  
			for(var j = 0 ; j < ids.length ; j++){
				var shopName= $j("#tbl_shop_query_dialog").getCell(ids[j],"name");
				if (shopName == shopArray[i]){
					$j("#tbl_shop_query_dialog").setSelection(ids[j],true);
				}
			}
		}
		$j("#shopQueryDialog").dialog("open");
	});
	
	//渠道组
	$j("#btnSearchShopGroup").click(function(){
		$j("#shopGroupQueryDialog").dialog("open");
	});
	
	//加载平台店铺
	var shopLikeQuerys = loxia.syncXhrPost(window.$j("body").attr("contextpath") + "/findshoplistbycompanyid.json");
	$j("#shopLikeQuery").append("<option></option>");
	for(var idx in shopLikeQuerys){
		$j("#shopLikeQuery").append("<option value='"+ shopLikeQuerys[idx].code +"'>"+shopLikeQuerys[idx].name+"</option>");
	}
    $j("#shopLikeQuery").flexselect();
    $j("#shopLikeQuery").val("");
    
    $j("#shopLikeQuery").change(function(){
    	if ($j("#shopLikeQuery").val() != ""){
    		var showShopList = $j("#showShopList").html();
    		var postshopInput = $j("#postshopInput").val();
    		var postshopInputName = $j("#postshopInputName").val();
    		var shopLikeQuerys = $j("#shopLikeQuery").val(); //模糊查询下拉框key
    		var shopLikeQuery = $j("#shopLikeQuery_flexselect").val(); //模糊查询下拉框value
    		var shoplist = "", postshop="",shoplistName="";
    		if (showShopList.indexOf(shopLikeQuery) < 0){
    			shoplist = shopLikeQuery + " | ";
				postshop = shopLikeQuerys+ "|";
				shoplistName = shopLikeQuery+ "|";
    		}  //不包含
    		$j("#showShopList").html(showShopList + shoplist);
    		$j("#postshopInput").val(postshopInput+ postshop);
    		$j("#postshopInputName").val(postshopInputName + shoplistName);
    	}
    });
    
	//初始化商品大小列表
	cList = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findskusize.json");
	for(var i=0;i<cList.length;i++){
		$j("<input type='checkbox' data='"+i+"' id='"+cList[i].id+"'/><label for='"+cList[i].id+"'>"+cList[i].name+"</label>").appendTo($j("#skuConfigtd"));
	}
	//根据当前用户选择的仓库，查询其中所有的定制打印模版的店铺
	var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/customiztationtemplshop.json");
	var customizationShopArray = null;
	if(rs && rs.returnVal){
		customizationShopArray = rs.returnVal;
		for (var i in customizationShopArray){
			customizationShopMap[customizationShopArray[i]] = customizationShopArray[i]; 
		}
	}
	//重置按钮功能
	$j("#reset").click(function(){
		$j("#filterTable input").val("");
		$j("#postshopInput").val("");
		$j("#postshopInputName").val("");
		$j("#showShopList").html("");
		$j("#postshopInput1").val("");
		$j("#postshopInputName1").val("");
		$j("#showShopList1").html("");
		$j("#AeraList1").html("");
		$j("#AreaInput1").val("");
		$j("#AreaInputName1").val("");
	});
	//返回按钮功能
	$j("#back").click(function(){
		$j("#staInfo").removeClass("hidden");
		$j("#staLineInfo").addClass("hidden");
	});
	//自动生成配货批
	$j("#autoConfirm").click(function(){
		var radioVal = "";
		radioVal = $j("#tbl-ruleList input[name='isAutoRadio']:checked").attr("id");
		if(radioVal == undefined){
			jumbo.showMsg("请选择一个配货规则进行配货！");
			return;
		}
		var count = $j("#canCount").val();
		if(count = 0){
			jumbo.showMsg("可创单据要大于0！");
			return;
		}
		var postData = {};
		if(radioVal!=""){
			postData['distributionRule.id'] = radioVal;
			var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/getDistributionRuleConditionCurrentDetail.json",postData);
			var data = result["dbrdList"];
			postData =  createPostData(data);
		}
		var isNeedInvoice = postData['isNeedInvoice'];
		var lpCode = postData['sta.staDeliveryInfo.lpCode'];
		if(isNeedInvoice == ""){
			if(!confirm("确定不选择是否需要发票将继续操作!")){
				return;
			}
		}
		
		if(lpCode == ""){
			jumbo.showMsg("请选择物流商！");
			return;
		}
		if(!loxia.byId("autoSize").check() || !loxia.byId("plCount").check() || !loxia.byId("minAutoSize").check()){
			jumbo.showMsg("请填写正确数量！");
			return;
		}
		if($j("#autoSize").val()<0||$j("#plCount").val()<0 || $j("#minAutoSize").val()<0){
			jumbo.showMsg("请填写正确数量！");
			return;
		}
		
		if($j("#autoSize").val()!= ""){
			var autoSize = $j("#autoSize").val();
			var minAutoSize = $j("#minAutoSize").val();
			if(parseInt(autoSize) < parseInt(minAutoSize)){
				jumbo.showMsg("最少单据数不能大于每批单据数！");
				return;
			}
		}
		
		if($j("#plCount").val()!=""){
			if($j("#autoSize").val()==""){
				jumbo.showMsg("请选择每批创建单据数量！");
				return;
			}
		}
		if($j("#sumCount").val()==""){
			jumbo.showMsg("请选择每单商品限制数量");
			return;
		}
		
		if(!checkMultiCompanyShop()){
			return ;
		}
		var shoplist = $j("#postshopInputName").val();
		var shoplist1 = $j("#postshopInputName1").val();
		if (shoplist != "" && shoplist1 != ""){
			jumbo.showMsg("渠道和店铺只能选择一个...");
			return;
		}
		//alert("postData="+JSON.stringify(postData));
		postData["limit"] = $j("#autoSize").val();
		postData["plCount"] = $j("#plCount").val();
		postData["minAutoSize"] = $j("#minAutoSize").val();
		//postData['isCod']=$j('#isCod').val();
		//postData["sta.pickingType"]="SKU_MANY";
		postData['pickingList.checkMode']="DEFAULE";
		postData["sumCount"] = $j("#sumCount").val();
		postData["shoplist"] = $j("#postshopInput").val();
		postData["shoplist1"] = $j("#postshopInput1").val();
		postData['pickingList.isSpecialPackaging'] = "";
		postData['isQs'] = "";
		var st = postData['sta.specialType'];//$j("#selIsSpPg").val();
		var qs = postData['sta.isSpecialPackaging'];//$j("#selIsQs").val();
		if(qs=="1"){ // QS
			postData['pickingList.isSpecialPackaging']=true;
			postData['pickingList.checkMode']="DEFAULE";
		}
		
		if (st=="NORMAL" && qs=="1"){
			postData['isQs'] = "qsAndSq";
		}
		
		if (st!="NORMAL" && qs!="1"){
			postData['isQs'] = "notQsAndSq";
		}
		if(st=="NORMAL"){ //特殊处理
			postData['pickingList.checkMode']="PICKING_SPECIAL";
			postData['pickingList.isSpecialPackaging']=false;
		}
		var clist = $j("#skuConfigtd input");
		for(var i=0;i<clist.length;i++){
			postData["ssList["+i+"].maxSize"]=-1;
			postData["ssList["+i+"].minSize"]=-1;
			postData["ssList["+i+"].groupSkuQtyLimit"]=-1;
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
		postData["areaList"] = $j("#AreaInput1").val();
		if($j("#CountArea").attr("checked")){
			postData["sta.isMerge"]=true;
		}else{
			postData["sta.isMerge"]=false;
		}
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/autoGenerDispatchListByNoPT.json",postData);
		if(result&&result.result){//成功后更新原始查询条件列表
			var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath") + "/json/findStaForPickingCount.do", postData);
			var sumCount = rs["result"];
			$j("#canCount").empty().append(sumCount);
		}
	});
});

function onTheTrigger(){
	$j('#btn-query').trigger("click");
}

function checkMultiCompanyShop(){
	var shoplist = $j("#postshopInputName").val();
	var shoplist1 = $j("#postshopInputName1").val();
	if (shoplist==""){
		if(shoplist1==""){
			jumbo.showMsg("请选择渠道或渠道组...");
			return false;
		}
	}else{
		if(shoplist1!=""){
			jumbo.showMsg("渠道和店铺只能选择一个...");
			return false;
		}
	}
	var flag = 0,index=0;
	var s = shoplist.split("|");
	var iteration = [];
	if(s.length > 2){
		for(var i in s){
			if (customizationShopMap[s[i]]){
				flag++;
				iteration[index++] = customizationShopMap[s[i]];
			}
		}
	}
	if (s.length > 1 && flag > 0){
		jumbo.showMsg("以下定制店铺不能多选 ： " + iteration);
		return false;
	}
	return true;
}

var getVal = function(el){
	var postData = {};
	//初始化checkbox
	$j("#skuConfigtd input[data='0']").removeAttr("checked");
	$j("#skuConfigtd input[data='1']").removeAttr("checked");
	$j("#skuConfigtd input[data='2']").removeAttr("checked");
	if(!checkMultiCompanyShop()){
		$j("#tbl-ruleList input[name='isAutoRadio']").removeAttr("checked");
		return ;
	}
	var radioVal = $j("#tbl-ruleList input[name='isAutoRadio']:checked").attr("id");
	if(radioVal!=""){
		postData['distributionRule.id'] = radioVal;
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/getDistributionRuleConditionCurrentDetail.json",postData);
		var data = result["dbrdList"];
		postData =  createPostData(data);
		postData["shoplist"] = $j("#postshopInput").val();
		postData["shoplist1"] = $j("#postshopInput1").val();
		var isQs = postData['sta.isSpecialPackaging'];
		if (isQs == 1){
			postData["sta.isSpecialPackaging"]=true;
		}else{
			postData["sta.isSpecialPackaging"]=false;
		}
		//postData["sta.pickingType"]="SKU_MANY";
		var clist = $j("#skuConfigtd input");
		for(var i=0;i<clist.length;i++){
			postData["ssList["+i+"].maxSize"]=-1;
			postData["ssList["+i+"].minSize"]=-1;
			postData["ssList["+i+"].groupSkuQtyLimit"]=-1;
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
	}
	//alert("postData2="+JSON.stringify(postData));
	var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath") + "/json/findStaForPickingCount.do", postData);
	var sumCount = rs["result"];
	$j("#currentRule").empty().append(el.value);
	$j("#canCount").empty().append(sumCount);
  }  

function createPostData(data){
	var postData = loxia._ajaxFormToObj("query-form");
	var groupCode = "";
	//是否需要发票
	postData['isNeedInvoice'] = "";
	//优先发货城市
	postData['city'] = "";
	//物流服务商
	postData['sta.staDeliveryInfo.lpCode'] = "";
	//是否特殊处理
	postData['sta.specialType'] = "";
	//是否COD订单
	postData['isCod'] = "";
	//商品分类
	postData['categoryId'] = "";
	//是否QS
	postData['sta.isSpecialPackaging'] = "0";
	//是否含SN号商品
	postData['isSn'] = "0";
	//运单时限类型
	postData['transTimeType'] = "";
	//状态
	postData['sta.status'] = "";
	//O2O门店
	postData['sta.toLocation'] = "";
	//SKU编码
	postData['sta.code1'] = "";
	postData['sta.code2'] = "";
	postData['sta.code3'] = "";
	postData['sta.code4'] = "";
	//其他
	
	for(var i = 0;i<data.length;i++){
		groupCode = data[i]["groupCode"];
		switch(groupCode){
		case "isNeedInvoice":
			if(data[i]["code"] == "IsNeedInvoiceTrue" || data[i]["code"] == "IsNeedInvoiceFalse"){
				postData['isNeedInvoice'] = data[i]["l_kValue"];
			}
			break;
		case "priorityShippingCity":
			if (data[i]["code"] == "AllPriorityRecommendationCity"
					|| data[i]["code"] == "ShangHai"
					|| data[i]["code"] == "GuangZhou"
					|| data[i]["code"] == "BeiJing"
					|| data[i]["code"] == "HangZhou"
					|| data[i]["code"] == "ShenZheng") {
				postData['city'] = data[i]["l_kValue"];
			}
			break;
		case "logisticsServiceProvider":
			if (data[i]["code"] == "YTO"
					|| data[i]["code"] == "EMS"
					|| data[i]["code"] == "SF"
					|| data[i]["code"] == "STO"
					|| data[i]["code"] == "YUNDA"
					|| data[i]["code"] == "HTKY"
					|| data[i]["code"] == "OTHER"
					|| data[i]["code"] == "TTKDEX"
					|| data[i]["code"] == "NEDA"
					|| data[i]["code"] == "EMSCOD"
					|| data[i]["code"] == "EMS_EYB"
					|| data[i]["code"] == "SFCOD"
					|| data[i]["code"] == "ZTO"
					|| data[i]["code"] == "XR"
					|| data[i]["code"] == "ML"
					|| data[i]["code"] == "MT"
					|| data[i]["code"] == "中铁物流"
					|| data[i]["code"] == "LB"
					|| data[i]["code"] == "SFDSTH"
					|| data[i]["code"] == "KY"
					|| data[i]["code"] == "CRE"
					|| data[i]["code"] == "STLY"
					|| data[i]["code"] == "KERRY_A"
					|| data[i]["code"] == "JD"
					|| data[i]["code"] == "KERRY"
					|| data[i]["code"] == "SFLY"
					|| data[i]["code"] == "UC"
					|| data[i]["code"] == "GTO"
					|| data[i]["code"] == "YCT"
					|| data[i]["code"] == "JDCOD"
					|| data[i]["code"] == "天津中铁物流"
					|| data[i]["code"] == "中铁物流-自提"
					|| data[i]["code"] == "中铁物流-送货上门"
					|| data[i]["code"] == "QRT"
					|| data[i]["code"] == "DHL"
			) {
				postData['sta.staDeliveryInfo.lpCode'] = data[i]["l_kValue"];
			}
			break;
		case "isSpecial":
			if (data[i]["code"] == "IsSpecialTrue"
					|| data[i]["code"] == "IsSpecialFalse"
			){
				postData['sta.specialType'] = $j.trim(data[i]["l_kValue"]);
			}
			break;
		case "isCodOrder":
			if (data[i]["code"] == "IsCodOrderTrue" || data[i]["code"] == "IsCodOrderFalse"){
				postData['isCod'] = data[i]["l_kValue"];
			}
			break;
		case "commodityClassification":
			if (data[i]["code"] == "AccessoriesCategory"
					|| data[i]["code"] == "Liquid"
					|| data[i]["code"] == "Cosmetics"
					|| data[i]["code"] == "Valuables"
					|| data[i]["code"] == "Clothes"
					|| data[i]["code"] == "Shoes") {
				postData['categoryId'] = data[i]["l_kValue"];
			}
			break;
		case "isQS":
			if (data[i]["code"] == "IsQSTrue" || data[i]["code"] == "IsQSFalse") {
				postData['sta.isSpecialPackaging'] = data[i]["l_kValue"];
			}
			break;
		case "isContainingSNCommodity":
			if (data[i]["code"] == "IsContainingSNCommodityTrue" || data[i]["code"] == "IsContainingSNCommodityFalse") {
				postData['isSn'] = data[i]["l_kValue"];
			}
			break;
		case "waybillTypeTime":
			if (data[i]["code"] == "Ordinary" || data[i]["code"] == "TimelyAmountsTo" || data[i]["code"] == "TheSameDay" || data[i]["code"] == "TheNextDay") {
				postData['transTimeType'] = data[i]["l_kValue"];
			}
			break;
		case "pickingListStatus":
			if (data[i]["code"] == "Created" || data[i]["code"] == "DistributionFailure") {
				postData['sta.status'] = data[i]["l_kValue"];
			}
			break;
		case "O2OShop":
			if (data[i]["code"] == "MongDistrict" || data[i]["code"] == "WanChaiDistrict" || data[i]["code"] == "KwunTongDistrict" || data[i]["code"] == "Eastern") {
				postData['sta.toLocation'] = data[i]["l_kValue"];
			}
			break;
		case "SKUCode":
			if (data[i]["code"] == "SKUCode1"){
				postData['sta.code1'] = data[i]["remark"];
			}else if(data[i]["code"] == "SKUCode2"){
				postData['sta.code2'] = data[i]["remark"];
			}else if(data[i]["code"] == "SKUCode3"){
				postData['sta.code3'] = data[i]["remark"];
			}else if(data[i]["code"] == "SKUCode4"){
				postData['sta.code4'] = data[i]["remark"];
			}
			break;
		case "largeAndSmallCommodities":
			if (data[i]["code"] == "Small"){
				$j("#skuConfigtd input[data='0']").attr("checked","checked");
			}else if(data[i]["code"] == "Middle"){
				$j("#skuConfigtd input[data='1']").attr("checked","checked");
			}else if(data[i]["code"] == "Big"){
				$j("#skuConfigtd input[data='2']").attr("checked","checked");
			}
			break;	
		default:
			break;
		}
	}
	return postData;
}