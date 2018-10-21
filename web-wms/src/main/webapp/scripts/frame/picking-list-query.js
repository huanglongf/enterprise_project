$j.extend(loxia.regional['zh-CN'],{
	DISTRIBUTION_LIST_SELECT : "请选择配货清单",
	CODE : "作业单号",
	INTSTATUS : "状态",
	REFSLIPCODE : "相关单据号",
	INTTYPE : "作业类型名称",
	SHOPID : "淘宝店铺ID",
	LPCODE : "物流服务商简称",
	CREATETIME : "创建时间",
	STVTOTAL : "计划执行总件数",
	INT_LIST : "作业单列表",
	
	BATCHNO : "配货清单号",
	CREATETIME : "创建时间",
	PLAN_BILL_COUNT : "计划配货单据数",
	PLAN_SKU_QTY : "计划配货商品件数",
	INTSTATUS : "状态",
	NEW_LIST : "新建配货清单列表",
	
	BATCHNO : "配货批次号",
	CREATETIME : "创建时间",
	PLANBILLCOUNT : "计划配货单据数", 
	PLAYSKUQTY : "计划配货商品件数",
	INTSTATUS : "状态",
	WAITING_LIST : "待配货清单列表",
	
	DISPATCH_FAILED : "由于您的配货单中所有作业单都失败，所以您的配货单被取消",
	DIAPATCH_CANCEL : "配货清单已取消",
	OPERATING : "配货清单打印中，请等待...",
	TRUNKPACKINGLISTINFO:"装箱清单打印中，请等待...",
	DELIVERYINFO:"物流面单打印中，请等待...",
	OPETION : "操作",
	PRINGT : "打印",
	CREATE_DISABLE_PRINT : "当前订单状态为‘新建状态’不能打印",
	CANEL_DISABLE_PRINT : "当前订单状态为‘取消已处理’不能打印",
	FAILED_DISABLE_PRINT : "当前订单状态为‘配货失败’不能打印"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

function reloadOrderDetail(url){
	$j("#tbl-pickingListDetail").jqGrid('setGridParam',{url:loxia.getTimeUrl(url)}).trigger("reloadGrid");
}

function showDetail(tag){
	var baseUrl = $j("body").attr("contextpath");
	$j("#div1").addClass("hidden");
	$j("#div2").removeClass("hidden");
	var id = $j(tag).parents("tr").attr("id");
	$j("#div2").attr("plId",id);
	$j("#pickingListId").html(id);
	var tr = $j(tag).parents("tr");
	var data=$j("#pickingList").jqGrid("getRowData",id);
	var rs = loxia.syncXhrPost(baseUrl + "/queryPickingListDetail.json",{"pickingListId":id});
	if(rs && rs["pickingList"]){
		$j("#dphCode").html( rs["pickingList"]["code"]);
		$j("#planBillCount").html( rs["pickingList"]["planBillCount"]);
		$j("#planSkuQty").html( rs["pickingList"]["planSkuQty"]);
		$j("#pickingTime").html( rs["pickingList"]["pickingTime"]);
		$j("#checkedBillCount").html( rs["pickingList"]["checkedBillCount"]);
		$j("#checkedTime").html( rs["pickingList"]["checkedTime"]);
		$j("#checkedSkuQty").html( rs["pickingList"]["checkedSkuQty"]);
		$j("#sendSkuQty").html( rs["pickingList"]["sendSkuQty"]);
		$j("#sendStaQty").html( rs["pickingList"]["sendStaQty"]);
		$j("#lastSendTime").html( rs["pickingList"]["lastSendTime"]);
	}
	$j("#dphStatus").html(tr.find("td[aria-describedby='pickingList_intStatus']").html());
	$j("#creator").html(data["createName"]);
	$j("#operator").html(data["operUserName"]);
	$j("#dphMode").html(data["pkModeInt"]);
	reloadOrderDetail(baseUrl + "/detialList.json?pickingListId="+id);
}


//单张物流面单的打印 （新建&&取消已处理&&配货失败） 三个不打印
function printSingleDelivery(tag,event){
	loxia.lockPage();
	var staid = $j(tag).parents("tr").attr("id");
	var plid = $j("#div2").attr("plId");
	jumbo.showMsg(i18("TRUNKPACKINGLISTINFO"));
	var url = $j("body").attr("contextpath") + "/printtrunkpackinglistmode1.json?plCmd.id=" + plid + "&plCmd.staId=" + staid;
	//var rs = loxia.syncXhrPost(url);
	printBZ(loxia.encodeUrl(url),true);				
	loxia.unlockPage();
}


$j(document).ready(function (){
	var baseUrl = $j("body").attr("contextpath");
	var plStatus=loxia.syncXhr(baseUrl + "/json/formateroptions.do",{"categoryCode":"pickingListStatus"});
	
	$j("#pickingList").jqGrid({
		//url:$j("body").attr("contextpath") + "/queryPackingList.json",
		datatype: "json",
		colNames: ["ID",i18("BATCHNO"),i18("CREATETIME"),"操作时间",i18("PLANBILLCOUNT"),i18("PLAYSKUQTY"),"模式",i18("INTSTATUS"),"操作人","创建人"],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name: "code", index: "code", formatter:"linkFmatter", formatoptions:{onclick:"showDetail"}, width:120,resizable:true},         
		           {name: "createTime", index: "createTime", width: 150, resizable: true},
		           {name: "executedTime", index: "executedTime", width: 150, resizable: true},
		           {name: "planBillCount", index: "planBillCount", width: 80, resizable: true},
		           {name: "planSkuQty", index: "planSkuQty", width: 80, resizable: true},
		           {name: "pkModeInt", index: "pkModeInt",width: 100, resizable: true},
	               {name: "intStatus",index:"intStatus",width:150,resizable:true,formatter:'select',editoptions:plStatus},
		           {name: "operUserName", index: "operUserName",width: 100, resizable: true},
		           {name: "createName", index: "createName",width: 100, resizable: true}],
		caption: i18("WAITING_LIST"),// 待配货清单列表
	   	sortname: 'pl.CREATE_TIME',
	   	height:"auto",
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		pager:"#pager",
		sortorder: "desc",
		rownumbers:true,
		viewrecords: true,
		hidegrid:false,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var ids = $j("#pickingList").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var datas = $j("#pickingList").jqGrid('getRowData',ids[i]);
				if(datas["pkModeInt"] == '1'){
					$j("#pickingList").jqGrid('setRowData',ids[i],{"pkModeInt":"模式一"});
				}
				if(datas["pkModeInt"] == '2'){
					$j("#pickingList").jqGrid('setRowData',ids[i],{"pkModeInt":"模式二"});
				}
				if(datas["pkModeInt"] == '3'){
					$j("#pickingList").jqGrid('setRowData',ids[i],{"pkModeInt":"模式三"});
				}
			}
		}
	});
	
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var status=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"trueOrFalse"});
	var intStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	
	$j("#tbl-pickingListDetail").jqGrid({
		//url:url,
		datatype: "json",
		// colNames:
		// ["ID","作业单号","状态","相关单据号","作业类型名称","淘宝店铺ID","物流服务商简称","创建时间","计划执行总件数","单批打印"],
		colNames: ["ID","status",i18("CODE"),i18("INTSTATUS"),i18("REFSLIPCODE"),i18("INTTYPE"),i18("SHOPID"),i18("LPCODE"),i18("CREATETIME"),i18("STVTOTAL"),"单批打印"],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name: "status", index: "status",hidden: true},
		           {name: "code", index: "code",sortable: false, width: 120, resizable: true},
		           {name: "intStatus", index: "status",sortable: false, width: 80, resizable: true,formatter:'select',editoptions:intStatus},
		           {name: "refSlipCode", index: "slipCode",sortable: false,width: 120, resizable: true},
		           {name: "intType", index: "type",sortable: false, width: 100, resizable: true,formatter:'select',editoptions:staType},
		           {name: "shopId", index: "shopId",sortable: false, width: 120, resizable: true},
		           {name: "lpcode", index: "lpcode",sortable: false, width: 100, resizable: true,formatter:'select',editoptions:trasportName},
	               {name:"createTime",index:"createTime",sortable: false,width:150,resizable:true},
	               {name: "stvTotal", index: "stvTotal",sortable: false, width: 100, resizable: true},
	               {name: "idForBtn", width: 120,sortable: false,resizable:true, formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("PRINGT"), onclick:"printSingleDelivery(this,event);"}}}
	               ],
		caption: i18("INT_LIST"),// 作业单列表
		rowNum:-1,
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: false,
		sortorder: "asc",
		gridComplete : function(){
			loxia.initContext($j(this));
		},
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#reset").click(function(){
		$j("#quert-form input,#quert-form select").val("");
	});
	
	
	
	$j("#query").click(function(){
		var url = baseUrl + "/queryPickingList.json";
		$j("#pickingList").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("quert-form")}).trigger("reloadGrid");
	});
	
	// 导出物流面单按钮功能
	$j("#exportDeliveryInfo").click(function(){
		if(beforePrintValidate()){
			var id = $j("#div2").attr("plId");
			$j("#frmSoInvoice").attr("src",$j("body").attr("contextpath") + "/warehouse/exportdeliveryinfo.do?pickingListId=" + id);
		}
	});
	
	// 物流面单导出
	$j("#exportDeliveryInfoZip").click(function(){
		if(beforePrintValidate()){
			var id = $j("#div2").attr("plId");
			$j("#frmSoInvoice").attr("src",$j("body").attr("contextpath") + "/warehouse/exportdeliveryinfozip.do?pickingListId=" + id);
		}
	});
	
	// 打印配货出库单：
	$j("#printPickingList").click(function(){
		loxia.lockPage();
		var plid = $j("#div2").attr("plId");
		jumbo.showMsg(i18("TRUNKPACKINGLISTINFO"));
		var url = $j("body").attr("contextpath") + "/printPickinglist.json?plCmd.id=" + plid;
		//var rs = loxia.syncXhrPost(url);
		printBZ(loxia.encodeUrl(url),true);				
		loxia.unlockPage();
	});
	
	$j("#back,#back2").click(function(){
		$j("#div2").addClass("hidden");
		$j("#div1").removeClass("hidden");
	});
	
});

function beforePrintValidate(){
	var ids = $j("#tbl-pickingListDetail").jqGrid('getDataIDs');
	var num=0,size=0;
	var datas;
	if(ids.length==0) {jumbo.showMsg("数据为空，数据出错..."); return false;}
	for(var i=0;i < ids.length;i++){
		size++;
		datas = $j("#tbl-pickingListDetail").jqGrid('getRowData',ids[i]);
		if (datas['intStatus']==17){
			num++;
		}
	}
	if (size==num){
		jumbo.showMsg("当前订单已'取消已处理'，不能打印或导出..."); 
		return false;
	}
	return true;
}
