$j.extend(loxia.regional['zh-CN'],{
	STA_LIST : "作业单列表",
	STA_LINE_LIST : "作业单明细",
	STA_CODE : "作业单号",
	STA_SLIP_CODE : "相关单据号",
	SLIP_CODE1:"相关单据号1",
	SLIP_CODE2:"相关单据号2",
	STA_TYPE : "作业单类型",
	STA_STATUS : "作业单状态",
	CRAETE_TIME : "创建时间",
	OWNER : "平台店铺名称",
		
		
	SKUCODE : "SKU编码",
	BARCODE : "条形码",
	JMCODE : "商品编码",
	SKU_NAME : "商品名称",
	KEY_PROPS : "扩展属性",
	PLAN_QTY : "计划量",
	COMFIRMED_QTY : "执行量",
	
	
	GIFT_BZ: "特殊商品包装...",
	GIFT_PRINT : "特殊打印中，请等待...",
	DATA_VALIDIDATE_BEGIN :"创建时间不能为空",
	DATA_VALIDIDATE_END :"结束时间不能为空",
	GIFT_TYPE_NIKE_GIFT : "10", // NIKE赠送礼品卡(需打印)
	GIFT_TYPE_COACH_CARD : "20", // Coach保修卡(需打印)
	GIFT_TYPE_GIFT_PACKAGE : "30",// 商品特殊包装
	GIFT_TYPE_GIFT_PRINT : "50", // 商品特殊印制
	HAAGENDAZS_TICKET :"60",// 哈根达斯券处理
	
		
	STA_SPECIAL_EXECUTE_TYPE_COACH:1,
	STA_SPECIAL_EXECUTE_TYPE_BB_OUT:3,
	STA_SPECIAL_EXECUTE_TYPE_BB_RETURN:5,
	STA_SPECIAL_EXECUTE_TYPE_NIKE_GIFT:8,
	STA_SPECIAL_EXECUTE_TYPE_NIKE_GIFT_B:9,
	STA_SPECIAL_EXECUTE_TYPE_NIKE_GIFT_C:10
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

//须要特殊处理明细的数据
var giftList;
//STA 特殊处理类型
var staSpecialExecute;
var tempId = null;
var isJump1 =0;
var isJump2 =0;
var isJump3 =0;
var isJump8 =0;
var isJump8Type="";
var staLineGiftId = null;
function showDetail(obj){
	$j("#detail_tabs").tabs();
	$j("#tbl-order-detail tr:gt(0)").remove();
	$j("#query-order-list").addClass("hidden");
	$j("#order-detail").removeClass("hidden");
	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");
	tempId = id;
	var pl=$j("#tbl-orderList").jqGrid("getRowData",id);
	var baseUrl = $j("body").attr("contextpath");
	$j("#shopId").text(pl["owner"]);
	$j("#createTime").text(pl["createTime"]);
	$j("#refSlipCode").text(pl["refSlipCode"]);
	$j("#type").text(tr.find("td[aria-describedby$='intType']").text());
	$j("#tbl-order-detail").jqGrid('setGridParam',{page:1,url:baseUrl + "/gethistoricalOrderDetailList.json?sta.id=" + id}).trigger("reloadGrid");
	giftList = loxia.syncXhr($j("body").attr("contextpath") + "/findstagiftlist.json?sta.id=" + id);
	//当明细界面打开后判断作业单特殊处理列表中包含装“1”时，弹出对话框COACH小票打印，打印数据通过OMS获取，考虑扩展，可支持多种类型流程处理。
	var sse = loxia.syncXhr(baseUrl + "/querystaspecialexecute.json?sta.id=" + id);
	staSpecialExecute = sse.data;
	$j("#dialog_coach_print").addClass("hidden");
	$j("#dialog_burberry_out_print").addClass("hidden");
	$j("#dialog_burberry_return_print").addClass("hidden");
	$j("#dialog_nike_gift_card_print").addClass("hidden");
	var isShow = false;
	for(var index in staSpecialExecute){
		if(index != 'contains'){
			if(staSpecialExecute[index].intType == i18("STA_SPECIAL_EXECUTE_TYPE_COACH")){
				$j("#dialog_coach_print").removeClass("hidden");
				$j("#dialog_jump1").removeClass("hidden");
				$j("#dialog_jump2").addClass("hidden");
				$j("#dialog_jump3").addClass("hidden");
				$j("#dialog_jump8").addClass("hidden");
				isJump1 += 1;
				isShow = true;
			}else if(staSpecialExecute[index].intType == i18("STA_SPECIAL_EXECUTE_TYPE_BB_OUT")){
				if(!isShow){
					$j("#dialog_burberry_out_print").removeClass("hidden");
					$j("#dialog_jump2").removeClass("hidden");
					$j("#dialog_jump1").addClass("hidden");
					$j("#dialog_jump3").addClass("hidden");
					$j("#dialog_jump8").addClass("hidden");
				}
				isShow = true;
				isJump2 += 1;
			}else if(staSpecialExecute[index].intType == i18("STA_SPECIAL_EXECUTE_TYPE_BB_RETURN")){
				if(!isShow){
					$j("#dialog_burberry_return_print").removeClass("hidden");
					$j("#dialog_jump1").addClass("hidden");
					$j("#dialog_jump2").addClass("hidden");
					$j("#dialog_jump3").removeClass("hidden");
					$j("#dialog_jump8").addClass("hidden");
				}
				isShow = true;
				isJump3 += 1;
			}else if(staSpecialExecute[index].intType == i18("STA_SPECIAL_EXECUTE_TYPE_NIKE_GIFT") || 
					staSpecialExecute[index].intType == i18("STA_SPECIAL_EXECUTE_TYPE_NIKE_GIFT_B") ||
					staSpecialExecute[index].intType == i18("STA_SPECIAL_EXECUTE_TYPE_NIKE_GIFT_C")){
				if(!isShow){
					$j("#dialog_nike_gift_card_print").removeClass("hidden");
					$j("#dialog_jump1").addClass("hidden");
					$j("#dialog_jump2").addClass("hidden");
					$j("#dialog_jump3").addClass("hidden");
					$j("#dialog_jump8").removeClass("hidden");
				}
				isShow = true;
				isJump8 += 1;
				isJump8Type=staSpecialExecute[index].intType;
			}
		}
	}
	if(isShow){
		$j("#dialog_staSpecialExecute").prev().find("a").addClass("hidden");
		$j("#dialog_staSpecialExecute").dialog("open");
	}else{
		setTimeout(function(){
			showLine();
		},300);
	}
}	

function SpeDialogClose(){
	isJump1 =0;
	isJump2 =0;
	isJump3 =0;
	$j("#dialog_staSpecialExecute").dialog("close");
}

function specialPackagingprint(seType,staId){
	loxia.lockPage();
	jumbo.showMsg(i18("GIFT_PRINT"));
	var url = $j("body").attr("contextpath") + "/bysptypeprint.json?seType="+seType+"&staId=" + staId;
	printBZ(loxia.encodeUrl(url),true);
	loxia.unlockPage();
}


$j(document).ready(function(){
	jumbo.loadShopList("companyshop");
	initShopQuery("companyshop","innerShopCode");
	$j("#dialog_staSpecialExecute").dialog({title: "特殊处理", modal:true,closeOnEscape:false, autoOpen: false, width: 520});
	$j("#dialog_giftType").dialog({title: "作业单明细特殊处理", modal:true,closeOnEscape:false, autoOpen: false, width: 520});
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});

	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	
	$j("#tbl-orderList").jqGrid({
		//url:baseUrl + "/gethistoricalOrderList.json",
		datatype: "json",
		//colNames: ["ID","作业单号","相关单据号","作业类型名称","作业单状态","平台店铺名称","物流服务商","快递单号","创建时间","完成时间","操作人","计划执行总数量"],
		colNames: ["ID",i18("STA_CODE"),i18("STA_SLIP_CODE"),i18("SLIP_CODE1"),i18("SLIP_CODE2"),i18("STA_TYPE"),i18("STA_STATUS"),i18("OWNER"),i18("CRAETE_TIME"),"备注"],
		colModel: [
							{name: "id", index: "id", width:100,hidden: true},
							{name:"code", index:"code", formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, width:100, resizable:true},
							{name:"refSlipCode",index:"refSlipCode",width:100,resizable:true},
							{name:"slipCode1",index:"slipCode1",width:100,resizable:true},
							{name:"slipCode2",index:"slipCode2",width:100,resizable:true},
							{name:"intType", index:"intType" ,width:100,resizable:true,formatter:'select',editoptions:staType},
							{name:"intStatus",index:"intStatus",width:100,resizable:true,formatter:'select',editoptions:staStatus},
							{name:"owner", index:"owner",width:140,resizable:true},
							{name:"createTime",index:"create_time",width:130,resizable:true},
							{name:"memo", index:"memo",width:100,resizable:true}],
		caption: i18("STA_LIST"),
	   	sortname: 'sta.create_time',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#pager",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#tbl-order-detail").jqGrid({
		datatype: "json",
		colNames: ["ID","货号",i18("SKUCODE"),i18("BARCODE"),i18("JMCODE"),i18("SKU_NAME"),i18("KEY_PROPS"),i18("PLAN_QTY"),i18("COMFIRMED_QTY")],
		colModel: [{name: "id", index: "id", width:100},
		           	{name:"skuCode",index:"skuCode",width:100,resizable:true},
		           	{name: "supplierCode", index:"sku.supplier_code",width:150,resizable:true,sortable:false},
					{name:"barCode",index:"barCode",width:100,resizable:true},
					{name:"jmcode", index:"jmcode" ,width:100,resizable:true},
					{name:"skuName", index:"skuName",width:130,resizable:true},
					{name:"keyProperties",index:"keyProperties",width:100,resizable:true},
					{name:"quantity", index:"quantity",width:100,resizable:true},
					{name:"completeQuantity", index:"completeQuantity",width:100,resizable:true}],
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
	
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");

	});
	
	$j("#search").click(function(){
		var cerateTime = $j("#createTimeId").val().trim();
		var endTime = $j("#endCreateTimeId").val().trim();
		if(cerateTime == ""){
			jumbo.showMsg(i18("DATA_VALIDIDATE_BEGIN"));
		}else if(endTime == ""){
			jumbo.showMsg(i18("DATA_VALIDIDATE_END"));
		}else{
			var url = $j("body").attr("contextpath") + "/getStaOrderList.json";
			$j("#tbl-orderList").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
		}
		//jumbo.bindTableExportBtn("tbl-orderList",{"intType":"whSTAType","intStatus":"whSTAStatus"},url,loxia._ajaxFormToObj("queryForm"));
	});
	
	$j("#back").click(function(){
		$j("#query-order-list").removeClass("hidden");
		$j("#order-detail").addClass("hidden");
	});
	
	$j("#dialog_jump1").click(function(){
		if(isJump2 > 0){
			$j("#dialog_coach_print").addClass("hidden");
			$j("#dialog_burberry_out_print").removeClass("hidden");
			$j("#dialog_jump2").removeClass("hidden");
			$j("#dialog_jump1").addClass("hidden");
			$j("#dialog_jump3").addClass("hidden");
			$j("#dialog_jump8").addClass("hidden");
		}else if(isJump2 == 0  && isJump3 > 0){
			$j("#dialog_coach_print").addClass("hidden");
			$j("#dialog_burberry_return_print").removeClass("hidden");
			$j("#dialog_jump3").removeClass("hidden");
			$j("#dialog_jump1").addClass("hidden");
			$j("#dialog_jump2").addClass("hidden");
			$j("#dialog_jump8").addClass("hidden");
		}else if(isJump2 == 0  && isJump3 == 0 && isJump8 > 0){
			$j("#dialog_coach_print").addClass("hidden");
			$j("#dialog_nike_gift_card_print").removeClass("hidden");
			$j("#dialog_jump8").removeClass("hidden");
			$j("#dialog_jump1").addClass("hidden");
			$j("#dialog_jump2").addClass("hidden");
			$j("#dialog_jump3").addClass("hidden");
		}else if(isJump2 == 0 && isJump3 == 0 && isJump8 == 0){
			setTimeout(function(){
				showLine();
			},300);
			SpeDialogClose();
		}
	});

	$j("#dialog_jump2").click(function(){
		if(isJump3 > 0){
			$j("#dialog_burberry_out_print").addClass("hidden");
			$j("#dialog_burberry_return_print").removeClass("hidden");
			$j("#dialog_jump1").addClass("hidden");
			$j("#dialog_jump2").addClass("hidden");
			$j("#dialog_jump3").removeClass("hidden");
			$j("#dialog_jump8").addClass("hidden");
		}else if(isJump3 == 0 && isJump8 > 0 ){
			$j("#dialog_burberry_out_print").addClass("hidden");
			$j("#dialog_nike_gift_card_print").removeClass("hidden");
			$j("#dialog_jump1").addClass("hidden");
			$j("#dialog_jump2").addClass("hidden");
			$j("#dialog_jump3").addClass("hidden");
			$j("#dialog_jump8").removeClass("hidden");
		}else if(isJump3 == 0 && isJump8==0 ){
			setTimeout(function(){
				showLine();
			},300);
			SpeDialogClose();
		}
	});
	
	$j("#dialog_jump3").click(function(){
		if(isJump8 > 0){
			$j("#dialog_burberry_return_print").addClass("hidden");
			$j("#dialog_nike_gift_card_print").removeClass("hidden");
			$j("#dialog_jump1").addClass("hidden");
			$j("#dialog_jump2").addClass("hidden");
			$j("#dialog_jump8").removeClass("hidden");
			$j("#dialog_jump3").addClass("hidden");
		}else if(isJump8==0 ){
			setTimeout(function(){
				showLine();
			},300);
			SpeDialogClose();
		}
	});
	
	$j("#dialog_jump8").click(function(){
		SpeDialogClose();
		setTimeout(function(){
			showLine();
		},300);
	});
	$j("#dialog_coach_print").click(function(){
		specialPackagingprint(1,tempId);
	});
	
	$j("#dialog_burberry_out_print").click(function(){
		specialPackagingprint(3,tempId);
	});
	
	$j("#dialog_burberry_return_print").click(function(){
		specialPackagingprint(5,tempId);
	});
	
	$j("#dialog_nike_gift_card_print").click(function(){
		specialPackagingprint(isJump8Type,tempId);
	});
	
	$j("#dialog_zs_lpk").click(function(){
		loxia.lockPage();
		jumbo.showMsg(i18("GIFT_PRINT"));
		var url = $j("body").attr("contextpath") + "/warehouse/printgift.json?wid=" + staLineGiftId;
		printBZ(loxia.encodeUrl(url),true);
		loxia.unlockPage();
	});
	
	$j("#dialog_sp_ts_bz").click(function(){
		jumbo.showMsg("商品特殊包装成功");
	});
	
	$j("#dialog_burberry_return_print").click(function(){
		loxia.lockPage();
		jumbo.showMsg(i18("GIFT_PRINT"));
		var url = $j("body").attr("contextpath") + "/warehouse/printgift.json?wid=" + staLineGiftId;
		printBZ(loxia.encodeUrl(url),true);
		loxia.unlockPage();
	});
	
	$j("#dialog_jump4").click(function(){
		showLine();
		if(dialogClose){
			$j("#dialog_giftType").dialog("close");
		}
	});
	
	$j("#dialog_jump5").click(function(){
		showLine();
		if(dialogClose){
			$j("#dialog_giftType").dialog("close");
		}
	});
	
	$j("#dialog_jump6").click(function(){
		showLine();
		if(dialogClose){
			$j("#dialog_giftType").dialog("close");
		}
	});
	
	$j("#dialog_jump7").click(function(){
		showLine();
		if(dialogClose){
			$j("#dialog_giftType").dialog("close");
		}
	});
});

var dialogClose = false;
function showLine(){
	$j("#dialog_zs_lpk").addClass("hidden");
	$j("#dialog_sp_ts_bz").addClass("hidden");
	$j("#dialog_sp_ts_yz").addClass("hidden");
	$j("#supplierCodeTr2").addClass("hidden");
	$j("#dialog_jump4").addClass("hidden");
	$j("#dialog_jump5").addClass("hidden");
	$j("#dialog_jump6").addClass("hidden");
	$j("#dialog_jump7").addClass("hidden");
	dialogClose = false;
	for(var index in giftList){
		for(i in giftList[index]){
			var gift = giftList[index][i];
			if(gift.intType == i18("GIFT_TYPE_NIKE_GIFT") && gift.intType !=100){
				gift.intType = "100";
				$j("#dialog_zs_lpk").removeClass("hidden");
				$j("#dialog_jump4").removeClass("hidden");
				var supplierCode = $j("#tbl-order-detail tr[id='"+index+"'] td[aria-describedby='tbl-order-detail_skuCode']").text();
				isShowDialog(supplierCode,gift.id);
				return;
			}else if(gift.intType == i18("GIFT_TYPE_GIFT_PACKAGE")  && gift.intType !=100){
				gift.intType = "100";
				$j("#dialog_sp_ts_bz").removeClass("hidden");
				$j("#dialog_jump5").removeClass("hidden");
				var supplierCode = $j("#tbl-order-detail tr[id='"+index+"'] td[aria-describedby='tbl-order-detail_skuCode']").text();
				isShowDialog(supplierCode,gift.id);
				return;
			}else if(gift.intType == i18("GIFT_TYPE_GIFT_PRINT") && gift.intType !=100){
				gift.intType = "100";
				$j("#dialog_sp_ts_yz").removeClass("hidden");
				$j("#dialog_jump6").removeClass("hidden");
				var supplierCode = $j("#tbl-order-detail tr[id='"+index+"'] td[aria-describedby='tbl-order-detail_skuCode']").text();
				isShowDialog(supplierCode,gift.id);
				return;
			}else if(gift.intType == i18("HAAGENDAZS_TICKET") && gift.intType !=100){
				gift.intType = "100";
				$j("#supplierCodeTr2").removeClass("hidden");
				$j("#dialog_jump7").removeClass("hidden");
				var supplierCode = $j("#tbl-order-detail tr[id='"+index+"'] td[aria-describedby='tbl-order-detail_skuCode']").text();
				$j("#dialog_memo").html(gift.memo);
				isShowDialog(supplierCode,gift.id);
				return;
			}
		}
	}
	dialogClose = true;
}

function isShowDialog(staLineId,giftId){
	staLineGiftId = giftId;
	$j("#supplierCode1").html(staLineId);
	$j("#dialog_giftType").prev().find("a").addClass("hidden");
	$j("#dialog_giftType").dialog("open");
}