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
	COUNT_SEARCH : "待升单列表",
	
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
var mtCode = "";
$j(document).ready(function(){
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
	
	$j("#btnSearchShopGroup").click(function(){
		$j("#shopGroupQueryDialog").dialog("open");
	});

	//初始化TOT目的地编码
	var otoResult = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getallshopstoreforoption.do");
	for(var idx in otoResult){
		$j("<option value='" + otoResult[idx].code + "'>"+ otoResult[idx].name +"</option>").appendTo($j("#selToLocation"));
	}
	//初始化物流商信息
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransportator.do");
	var mResult = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getTransportatorAfter.json");
	var mCode = "";
	var mName = "";
	for(var mdx in mResult){
		mCode+=mResult[mdx].expCode+",";
		mName+=mResult[mdx].name+",";
//		alert(mResult[mdx].expCode);
	}
	if(mCode!=""){
		$j("<option value='" + mCode.substring(0,mCode.length-1) + "'>"+ mName.substring(0,mName.length-1) +"</option>").appendTo($j("#selLpcode"));
		mtCode = mCode.substring(0,mCode.length-1);
	}
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selLpcode"));
	}
//	//初始化行业列表信息(销售事业部)
//	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findIndustryByOuid.do");
//	for(var idx in result){
//		$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#industryId"));
//	}
	
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
	//根据当前用户选择的仓库，查询其中所有的定制打印模版的店铺
	var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/customiztationtemplshop.json");
	var customizationShopArray = null;
	if(rs && rs.returnVal){
		customizationShopArray = rs.returnVal;
		for (var i in customizationShopArray){
			customizationShopMap[customizationShopArray[i]] = customizationShopArray[i]; 
		}
	}
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	var trueOrFalse=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"trueOrFalse"});
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var staSpecialType={"value":"1:需特殊处理核对;2:包裹填充物;"};
	//初始化列表
	$j("#tbl-staList-query").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("CODE"),i18("REFSLIPCODE"),i18("SHOPID"),i18("LPCODE"),i18("STATUS"),i18("CREATETIME"),"平台订单时间",i18("ISNEEDINVOICE"),i18("STVTOTAL"),"是否含SN商品","是否特殊处理","是否QS"],
		colModel: [{name: "id", index: "id", hidden: true},		         
		           {name: "code", index: "code", width: 120,formatter:"linkFmatter",formatoptions:{onclick:"showStaLine"}, resizable: true},
		           {name: "refSlipCode", index: "refSlipCode",width: 120, resizable: true},
		           {name: "shopId", index: "shopId", width: 100, resizable: true},
		           {name: "lpcode", index: "lpcode", width: 80, resizable: true,formatter:'select',editoptions:trasportName},
		           {name: "intStatus", index: "intStatus", width: 60, resizable: true,formatter:'select',editoptions:staStatus},
		           {name: "createTime", index: "createTime", width: 120, resizable: true},
		           {name: "orderCreateTime", index: "orderCreateTime", width: 120, resizable: true},
		           {name: "isNeedInvoice", index: "isNeedInvoice",  width: 80, resizable: true,formatter:'select',editoptions:trueOrFalse},
		           {name: "stvTotal", index: "stvTotal", width: 90, resizable: true},
		           {name: "isSn",index:"isSn",width:80,resizable:true,formatter:'select',editoptions:trueOrFalse},
		           {name: "intSpecialType",index:"isSpecialType",width:100,resizable:true,formatter:'select',editoptions:staSpecialType},
		           {name: "isQs", index: "isQs", width: 90, resizable: true},
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
	// 店铺改变 出发查询
//	$j("#postshopInput").on('input',function(e){  
//		alert('A');
//		$('#btn-query').click();
//	});
	//查询按钮功能
	$j("#btn-query").click(function(){
		query();
	});
	function query(){
		var postData = loxia._ajaxFormToObj("query-form");
		$j("#tbl-staList-query").jqGrid('setGridParam',{
			url:loxia.getTimeUrl($j("body").attr("contextpath")+"/json/findStaLsingle.do"),
			postData:postData,
			page:1
		}).trigger("reloadGrid");
	}
	

	//升单
	$j('#confirm').click(function(){
		var staIds = $j("#tbl-staList-query").jqGrid('getGridParam','selarrrow');
		if(staIds.length>0){
			var postData = {};
			for(var i in staIds){
				postData['staIdList[' + i + ']']=staIds[i];
			}
			var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/generstaLsingle.json",postData);
			if(result&&result.result){
				query();
			}
		}else{	
			jumbo.showMsg(loxia.getLocaleMsg("APPLY_FORM_SELECT"));//请选择作业申请单
		}
	});
	//不升单
	$j('#outconfirm').click(function(){
		var staIds = $j("#tbl-staList-query").jqGrid('getGridParam','selarrrow');
		if(staIds.length>0){
			var postData = {};
			for(var i in staIds){
				postData['staIdList[' + i + ']']=staIds[i];
			}
			var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/outGenerstaLsingle.json",postData);
			if(result&&result.result){
				query();
			}
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
		var shoplist = $j("#postshopInputName").val();
		var shoplist1 = $j("#postshopInputName1").val();
		if (shoplist != "" && shoplist1 != ""){
			jumbo.showMsg("渠道和店铺只能选择一个...");
			return;
		}
		var postData = loxia._ajaxFormToObj("query-form");
		postData["limit"] = $j("#autoSize").val();
		postData["plCount"] = $j("#plCount").val();
		postData["shoplist"] = $j("#postshopInput").val();
		postData["shoplist1"] = $j("#postshopInput1").val();
		postData["sta.pickingType"]="SKU_SINGLE";
		postData['pickingList.checkMode']="PICKING_CHECK";
		postData['isCod']=$j('#isCod').val();
		var st = $j("#selIsSpPg").val();
		var qs = $j("#selIsQs").val();
		postData['isQs'] = "";
		if(qs==1){ // QS
			postData['pickingList.isSpecialPackaging']=true;
			postData['pickingList.checkMode']="PICKING_CHECK";
		}
		if (st=="NORMAL" && qs==1){
			postData['isQs'] = "qsAndSq";
		}
		
		if (st!="NORMAL" && qs!="1"){
			postData['isQs'] = "notQsAndSq";
		}
		if(st=="NORMAL"){ //特殊处理
			postData['pickingList.checkMode']="PICKING_SPECIAL";
			postData['pickingList.isSpecialPackaging']=false;
		}
		
		//当订单为O2O且为QS订单时，要把原有的pickingType设置为O2OANDQS
		/*var qs =  $j("#selIsQs").find("option:selected").text();
		var o2o = $j('option:selected', '#selToLocation').index();
		if(qs=="是" && o2o !=0){
			postData["sta.pickingType"]="O2OANDQS";
		}*/
		
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
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/autogenerdispatchList.json",postData);
		if(result&&result.result){//成功后更新原始查询条件列表
			var postData1 = loxia._ajaxFormToObj("query-form");
			postData1["shoplist"] = $j("#postshopInput").val();
			postData1["shoplist1"] = $j("#postshopInput").val();
			var isQs2 =$j("#selIsQs").val();
			if (isQs2 == 1){
				postData1["sta.isSpecialPackaging"]=true;
			}else{
				postData1["sta.isSpecialPackaging"]=false;
			}
			postData1["sta.pickingType"]="SKU_SINGLE";
			var clist = $j("#skuConfigtd input");
			for(var i=0;i<clist.length;i++){
				postData1["ssList["+i+"].maxSize"]=-1;
				postData1["ssList["+i+"].minSize"]=-1;
				postData1["ssList["+i+"].groupSkuQtyLimit"]=-1;
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
			$j("#tbl-staList-query").jqGrid('setGridParam',{url:window.$j("body").attr("contextpath")+"/json/findStaForPickingByModel.do",postData:postData1,page:1}).trigger("reloadGrid");
			if(result&&result.result=='error'){
				jumbo.showMsg(result.message);
			}
		}
	});
	$j("#reset").click(function(){
		$j("#query-form input,#query-form select").val("");
		$j("#isMorePackage").attr("checked",false);
		$j("#isMergeInt").attr("checked",false);
	});
});

function onTheTrigger(){
	$j('#btn-query').trigger("click");
}


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

