$j.extend(loxia.regional['zh-CN'],{
	STATUSNEW :		"新建",
	STATUSOCCUPIED: "配货中",
	STATUSFINISHED:	"已完成",
	STATUSCANCELED: "取消已处理",
	
	EXECUTION:		"执行",
	CANCEL:			"取消",
	TRANSATION_TYPE : "作业类型",
	
	EXECUTIONSUCCESSFUL:"执行成功",
	STACANCEL:		"作业单已取消",
	EXECUTEXCEPTION:"操作数据异常",
	
	STACODE:		"作业单号",
	TRANSACTION:	"作业方向",
	STATUS:			"状态",
	STORE:			"店铺",
	CREATETIME:		"创建时间",
	COMPLETETIME:	"完成时间",
	CREATER:		"创建人",
	OPERATOR:		"操作人",
	OPERATE:		"操作",
	
	TITLE:			"VMI转店出入库作业单列表",
	
	INSTOCK:		"入库",
	OUTSTOCK:		"出库",
	
	EXECUTIONASK:	"您确定要执行？",
	CANCELASK:		"您确定要取消？",
	
	BARCODE:		"条形码",
	SKUCODE:		"商品编码",
	DISTRICT:		"库区",
	LOCATION:		"库位",
	STATUS:			"状态",
	SKUCOST:		"成本",
	QUANTITY:		"数量",
	
	IN_SN_TEMPLATE: 	"入库SN序列号",
	OUT_SN_TEMPLATE: 	"出库SN序列号",
	DETAILTITLE:	"作业单明细",
	STATYPE : "VMI转店",
	INVOICE_PERCENTAGE_ERROR:"此发票号对应的系数没有维护，请先维护"
		
	
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

var $j = jQuery.noConflict();

function showMsg(message){
	jumbo.showMsg(message);
} 


//执行出入库作业单 
function execution2(postData){
	loxia.asyncXhrPost(
			$j("body").attr("contextpath") + "/unfreezebystaid.json",
			postData,
			{
				success:function(data){
						if(data){
							if(data.result=="success"){
								jumbo.showMsg(i18("EXECUTIONSUCCESSFUL"));//交货清单取消成功
								refreshHead();
							}else if(data.result=="error"){
								jumbo.showMsg(data.message);
							}
						}
					},
				error:function(data){jumbo.showMsg(i18("EXECUTEXCEPTION"));}//操作数据异常
			}
		);	
} 

//刷新数据
function refreshHead(){
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-query-info").jqGrid('setGridParam',{
		url:loxia.getTimeUrl(baseUrl+"/findvmiflittingstas.json")
	}).trigger("reloadGrid");
	$j("#searchCondition").removeClass("hidden");
	$j("#details").addClass("hidden");
} 

function showDetail(obj){
	var id =$j(obj).parents("tr").attr("id");
	$j("#staID").val(id);
	$j("#invoiceNumber").val("");
	$j("#totalQty").val("");
	$j("#totalFOB").val("1");
	
	
	var vals =  $j("#tbl-query-info").jqGrid('getRowData',id);
	$j("#code").html(vals["code"]);
	$j("#owner").html(vals["owner"]);
	$j("#mainWhName").html(vals["mainName"]);
	
	var pttype = vals["ptType"];
	if(pttype==1 || pttype==3 || pttype==5){
		$j("#invoiceNumberTYPE").val(vals["ptType"]);
		$j("#miscFeePercentage").val(vals["miscFeePercentage"]);
		$j("#dutyPercentage").val(vals["dutyPercentage"]);
		
	}else{
		$j("#invoiceNumberTYPE").val("");
		$j("#miscFeePercentage").val("");
		$j("#dutyPercentage").val("");
		
	}
	
	if(pttype!=5){
		$j("#invoiceNumber").unbind("change",changeFun) ;
	}
	
	if(pttype==5){
		$j("#invoiceNumber").bind("change",changeFun);
	}
	
	
	var tmp = vals['intStatus'];
	if (tmp == 25){
		$j("#status").html("冻结")
	}else if (tmp == 1){
		$j("#status").html("已创建")
	}else if (tmp == 15){
		$j("#status").html("取消未处理")
	}else if (tmp == 17){
		$j("#status").html("取消已处理")
	}
	$j("#memo").html(vals["memo"]);
	$j("#slipcode").html(vals["refSlipCode"]);

	jumbo.bindTableExportBtn("tbl-details",{"intStatus":"whSTAStatus"},
			$j("body").attr("contextpath")+"/findvmiunfreezedetails.json?",{"staid":id});
	showDt(id);
	
	$j("#searchCondition").addClass("hidden");
	$j("#details").removeClass("hidden");
	
	
}

var changeFun = function (){
	var  val = $j("#invoiceNumber").val();
	$j("#miscFeePercentage").val("");
	$j("#dutyPercentage").val("");
	if(val.substr(0,2) == "WN"){
		loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+"/findInvoiceNum.json",
				{"ipCommand.invoiceNum":$j("#invoiceNumber").val()},
				{
					success:function (data) {
					    if(data&&data.msg){
					    	jumbo.showMsg(data.msg);	
					    }else{
					    	var invType = $j("#invoiceNumberTYPE").val();
					    	if(data == null){
					    		jumbo.showMsg(i18("INVOICE_PERCENTAGE_ERROR"));	
					    	}else{
					    		$j("#miscFeePercentage").val(data.miscFeePercentage);
					    		$j("#dutyPercentage").val(data.dutyPercentage);
					    	}
					    }
					   }, 
						error:function(data){
						jumbo.showMsg(i18("INVOICE_PERCENTAGE_ERROR"));	
		               }
				});
	} else {
		$j("#dutyPercentage").val("1.86");
		$j("#miscFeePercentage").val("1.00");
	}
}

function showDt(id){
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-details").jqGrid('setGridParam',{
		url:loxia.getTimeUrl(baseUrl+"/findvmiunfreezedetails.json"),
		postData:{"staid":id}
	}).trigger("reloadGrid");
}

$j(document).ready(function (){
	loxia.init({debug: true, region: 'zh-CN'});
	var baseUrl = $j("body").attr("contextpath");
	$j("#dutyPercentage").attr("readonly","readonly");
	$j("#miscFeePercentage").attr("readonly","readonly");
	$j("#totalFOB").attr("readonly","readonly");
	jumbo.loadShopList("companyshop");
	
	initShopQuery("companyshop","innerShopCode");
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	
	$j("#search").click(function(){
		var postData = loxia._ajaxFormToObj("form_query");  
		$j("#tbl-query-info").jqGrid('setGridParam',{
			url: baseUrl + "/findvmiflittingstas.json",postData:postData}).trigger("reloadGrid");
	});
	
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val(0);
	});
	
	var status=loxia.syncXhr(baseUrl + "/formateroptions.json",{"categoryCode":"whSTAStatus"});

	$j("#tbl-query-info").jqGrid({
		url: baseUrl + "/findvmiflittingstas.json",
		datatype: "json",
		colNames: ["ID","作业单号","状态", "状态","仓库","店铺","备注","相关单据号","PO类型","税收系数","其他系数","创建时间","总计划量"],
		colModel: [
				{name: "id", index: "id", hidden: true},
				{name :"code",index:"code",formatter:"linkFmatter",formatoptions:{onclick:"showDetail"},width:150,resizable:true},
				{name: "status", index: "status",hidden:true},  
				{name: "intStatus", index: "status",width: "80", resizable: true,
				        	   formatter:'select',editoptions:status,sortable:false},
				{name: "mainName", index: "mainName",width: "120", resizable: true},
				{name: "owner", index: "owner",width: "120", resizable: true},				
				{name: "memo", index: "memo",hidden:true},
				{name: "refSlipCode", index: "refSlipCode",width: "120", resizable: true},
				{name: "ptType", index: "ptType",hidden: true},
				{name: "dutyPercentage", index: "dutyPercentage",hidden: true},
				{name: "miscFeePercentage", index: "miscFeePercentage",hidden: true},
				{name: "createTime", index: "createTime",width: "120", resizable: true},
				{name: "totalQty", index: "totalQty",width: "120", resizable: true}
				],
		caption: "VMI收货作业单",
   		sortname: 'id',
   		sortorder: "desc",
		multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pager",
	   	height:jumbo.getHeight(),
		viewrecords: true,
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});

	jumbo.bindTableExportBtn("tbl-query-info",{"intStatus":"whSTAStatus"});
	
	$j("#tbl-details").jqGrid({
		//url: baseUrl + "/findvmiunfreezedetails.json",
		datatype: "json",//"已发货单据数","已发货商品件数",  
		colNames: ["ID","条形码","商品编码","扩展属性","计划执行量","已执行量"],
		colModel: [{name: "id", index: "id", hidden: true},
			{name:"barCode", index:"barCode" ,width:100,resizable:true},
			{name:"jmskuCode", index:"jmskuCode" ,width:100,resizable:true},
			{name:"keyProperties", index:"keyProperties" ,width:100,resizable:true},
			{name:"quantity",index:"quantity",width:"60",resizable:true},
			{name:"completeQuantity", index:"completeQuantity" ,width:60,resizable:true}],
		caption: "VMI收货作业单明细",
		//rowNum:10,
   		//rowList:[5,10,15,20,25,30],
   		sortname: 'id',
    		multiselect: false,
		sortorder: "desc",
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#back").click(function(){
		$j("#searchCondition").removeClass("hidden");
		$j("#details").addClass("hidden");
	});
	
	$j("#execute,#cancel").click(function(){
		if(confirm("您确定要执行此操作？")){
			$j("#searchCondition").removeClass("hidden");
			$j("#details").addClass("hidden");
		}
	});
	
	
	$j("#unfreeze").click(function(){
		
		var postData = {};
		var id = $j("#staID").val();
		var invoiceNumber = $j("#invoiceNumber").val();
		var totalQty = $j("#totalQty").val();
		var totalFOB = $j("#totalFOB").val();
		var dutyPercentage = $j("#dutyPercentage").val();
		var miscFeePercentage = $j("#miscFeePercentage").val();
		var invoiceNumberTYPE = $j("#invoiceNumberTYPE").val();
		
		var error = [];
		if (invoiceNumber == null || invoiceNumber ==""){error.push("请输入发票号.");}
		if (totalQty == null || totalQty ==""){error.push("请输入总量.");}
		if (totalFOB == null || totalFOB ==""){error.push("请输入成本.");}
		if (dutyPercentage == null || dutyPercentage ==""){error.push("请输入税收系数.");}
		if (miscFeePercentage == null || miscFeePercentage ==""){error.push("请输入其他系数.");}
		if (invoiceNumberTYPE == null || invoiceNumberTYPE ==""){error.push("发票类型没有维护，请先维护.");}
		
		if(error.length >0){
			jumbo.showMsg(error);
			return false;
		}
		
		postData['sta.id'] = id;
		postData['sta.invoiceNumber'] = invoiceNumber;
		postData['sta.totalQty'] = totalQty;
		postData['sta.totalFOB'] = totalFOB;
		postData['sta.dutyPercentage'] = dutyPercentage;
		postData['sta.miscFeePercentage'] = miscFeePercentage;
		
		
		if(confirm(i18("EXECUTIONASK"))){
			execution2(postData);
		}
	});
	
});
