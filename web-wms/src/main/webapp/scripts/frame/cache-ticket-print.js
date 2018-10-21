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
	TRUNKPACKINGLISTINFO:"Coach小票打印中，请等待...",
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


//单张物流面单的打印 （新建&&取消已处理&&配货失败） 三个不打印
function printSingleDelivery(tag,event){
	loxia.lockPage();
	var staid = $j(tag).parents("tr").attr("id");
	jumbo.showMsg(i18("TRUNKPACKINGLISTINFO"));
	var url = $j("body").attr("contextpath") + "/bysptypeprint.json?seType=1&staId=" + staid;
	printBZ(loxia.encodeUrl(url),true);				
	loxia.unlockPage();
}


$j(document).ready(function (){
	var baseUrl = $j("body").attr("contextpath");
	
	var trueOrFalse=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"trueOrFalse"});
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	
	$j("#tbl-StaList").jqGrid({
		datatype: "json",
		colNames: ["ID","作业单号","相关单据号","作业类型名称","作业单状态","平台店铺名称","创建时间","单批打印"],
		colModel: [
				{name:"id", index: "id", hidden: true},
				{name:"code", index:"code",width:120, resizable:true},
				{name:"refSlipCode",index:"refSlipCode",width:180,resizable:true},
				{name:"intStaType", index:"intStaType" ,width:100,resizable:true,formatter:'select',editoptions:staType},
				{name:"intStaStatus",index:"intStaStatus",width:100,resizable:true,formatter:'select',editoptions:staStatus},
				{name:"owner", index:"owner",width:180,resizable:true},
				{name:"createTime",index:"create_time",width:180,resizable:true},
				{name: "idForBtn", width: 100,resizable:true, formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("PRINGT"), onclick:"printSingleDelivery(this,event);"}}}
					 	],
		caption: "Coach 订单列表",
	   	sortname: 'sta.create_time',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#pager",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
   		gridComplete : function(){
			loxia.initContext($j(this));
		},
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#reset").click(function(){
		$j("#quert-form input,#quert-form select").val("");
	});
	
	$j("#query").click(function(){
		var url = baseUrl + "/querycoachsta.json";
		$j("#tbl-StaList").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("quert-form")}).trigger("reloadGrid");
	});
});
