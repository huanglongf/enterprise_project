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
	STATYPE : "VMI转店"
		
	
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

var $j = jQuery.noConflict();

function showMsg(message){
	jumbo.showMsg(message);
}

function showDetail(obj){
 	var id =$j(obj).parents("tr").attr("id");
 	$j("#snsfile").val("");																									
 	jumbo.bindTableExportBtn("tbl-details",{"intType":"whSTAType","intStatus":"whSTAStatus"},$j("body").attr("contextpath")+"/findvmitransdetails.json?",{"staID":id});
	var vals =  $j("#tbl-query-info").jqGrid('getRowData',id);
	$j("#staID").val(vals["id"]);
	$j("#code").html(vals["code"]);
	$j("#store").html(vals["store"]);
	//$j("#Dtransaction").html(vals["transaction"]);
	$j("#creater").html(vals["creater"]);
	$j("#operator").html(vals["operator"]);
	$j("#Dstatus").html(vals["status"]);
	$j("#createTime").html(vals["createTime"]);
	$j("#finishTime").html(vals["finishTime"]);
	$j("#memo").html(vals["memo"]);
	
	if(vals["status"]==i18("STATUSNEW") || vals["status"]==i18("STATUSOCCUPIED")){
		$j("#execute").removeClass("hidden");
		$j("#cancel").removeClass("hidden");
	}else if(vals["status"]==i18("STATUSOCCUPIED")){
		$j("#cancel").removeClass("hidden");
		$j("#execute").addClass("hidden");
	}else {
		$j("#cancel").addClass("hidden");
		$j("#execute").addClass("hidden");
	}
	// 获取备注
	showDt(id);
	$j("#searchCondition").addClass("hidden");
	$j("#details").removeClass("hidden");
	
}

function showDt(id){
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-details").jqGrid('setGridParam',{
		url:loxia.getTimeUrl(baseUrl+"/findvmitransdetails.json"),
		postData:{"staID":id},datatype: "json"
	}).trigger("reloadGrid");
}




//执行出入库作业单
function execution(id){
	var postData = {};
	postData['staID']=id;
	//postData["sns"]=getSnsData();
	execution2(postData);
}
function execution2(postData){
	loxia.asyncXhrPost(
			//$j("body").attr("contextpath") + "/operateExecution.json",
			$j("body").attr("contextpath") + "/executevmitransfer.json",
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
//取消出入库作业单
function canceled(id){
	var postData = {};
	postData['staID']=id;
	loxia.asyncXhrPost(
			//$j("body").attr("contextpath") + "/operateCanceled.json",
			$j("body").attr("contextpath") + "/cancelvmitransfer.json",
			postData,
			{
				success:function(data){
						if(data){
							if(data.result=="success"){
								jumbo.showMsg(i18("STACANCEL"));//交货清单取消成功
								refreshHead()
							}else if(data.result=="error"){
								jumbo.showMsg(data.message);
							}
						}
					},
				error:function(){jumbo.showMsg(i18("EXECUTEXCEPTION"));}//操作数据异常
			}
		);
}

//刷新数据
function refreshHead(){
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-query-info").jqGrid('setGridParam',{
		url:loxia.getTimeUrl(baseUrl+"/vmitransferstaquery.json"),
		postData:loxia._ajaxFormToObj("form_query")
	}).trigger("reloadGrid");
	$j("#searchCondition").removeClass("hidden");
	$j("#details").addClass("hidden");
}

$j(document).ready(function (){
	jumbo.loadShopList("companyshop");
	initShopQuery("companyshop","innerShopCode");
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	var baseUrl = $j("body").attr("contextpath");
	var status=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"whSTAStatus"});
	$j("#tbl-query-info").jqGrid({
		url:baseUrl+"/vmitransferstaquery.json",
		postData:loxia._ajaxFormToObj("form_query"),
		datatype: "local",
		//colNames: ["ID","作业单号","事物方向","状态","店铺","创建时间","完成时间","创建人","操作人","操作","MEMO"],
		colNames: ["ID",i18("STACODE"),i18("TRANSATION_TYPE"),i18("STATUS"),i18("STATUS"),i18("STORE"),i18("CREATETIME"),i18("COMPLETETIME"),i18("CREATER"),i18("OPERATOR"),"合计","MEMO"],
		colModel: [
				{name: "id", index: "id", hidden: true},
				{name :"code",index:"code",formatter:"linkFmatter",formatoptions:{onclick:"showDetail"},width:120,resizable:true},
				{name: "intStaType", index: "intStaType",width: "100", resizable: true},
				{name: "status", index: "status",width: "80", resizable: true,hidden:true},
				{name: "intStatus", index: "status",width: "80", resizable: true,
				        	   formatter:'select',editoptions:status,sortable:false},
				{name: "store", index: "store",width: "120", resizable: true},
				{name: "createTime", index: "createTime",width: "130", resizable: true},
				{name: "finishTime", index: "finishTime",width: "130", resizable: true},
				{name: "creater", index: "creater",width: "90", resizable: true},
				{name: "operator", index: "operator",width: "90", resizable: true},
				{name: "skuQty", index: "sku_qty",width: "60", resizable: true},
				//{name: "operate", width: "165", resizable: true},
				{name: "memo", index: "memo", hidden: true}],
		caption: i18("TITLE"),
	   	pager:"#page_tbl-query-info",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		height:"auto",
   		sortname: 'sta.id',
    	multiselect: false,
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var ids = $j("#tbl-query-info").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var datas = $j("#tbl-query-info").jqGrid('getRowData',ids[i]);
				var statusVal= "";
				if(datas["status"]=="CREATED"){
					statusVal= i18("STATUSNEW");
				}else if(datas["status"]=="OCCUPIED"){
					statusVal= i18("STATUSOCCUPIED");
				}else if(datas["status"]=="FINISHED"){
					statusVal= i18("STATUSFINISHED");
				}else if(datas["status"]=="CANCELED"){
					statusVal= i18("STATUSCANCELED");
				}else {
				}
				$j("#tbl-query-info").jqGrid('setRowData',ids[i],{"status":statusVal});
				$j("#tbl-query-info").jqGrid('setRowData',ids[i],{"intStaType":i18("STATYPE")});
			}
		}
	});
	jumbo.bindTableExportBtn("tbl-query-info",{"intStatus":"whSTAStatus","transaction":"whDirectionMode"});
	
	$j("#tbl-details").jqGrid({
//		url:baseUrl+"/findvmitransdetails.json",
		// postData:{"staID":id},
		datatype: "local",
		// "已发货单据数","已发货商品件数",
		//colNames: ["ID","条形码","商品编码","库区","库位","状态","成本","数量"],
		colNames: ["ID","货号","商品名称","SKU编码","条形码","商品编码","扩展属性",i18("DISTRICT"),i18("LOCATION"),i18("STATUS"),i18("QUANTITY")],
		colModel: [{name: "id", index: "id", hidden: true},
		    {name:"supplierCode", index:"supplierCode" ,width:150,resizable:true},
		    {name:"skuName", index:"skuName" ,width:150,resizable:true},
		    {name:"skuCode", index:"skuCode" ,width:90,resizable:true},
		    {name:"barCode", index:"barCode" ,width:90,resizable:true},
			{name:"jmcode", index:"jmcode" ,width:90,resizable:true},
			{name:"keyProperties", index:"keyProperties" ,width:90,resizable:true},
			{name:"district",index:"district",width:80,resizable:true},
			{name:"location", index:"location" ,width:105,resizable:true},
			{name:"status", index:"status" ,width:60,resizable:true},
			//{name:"skuCost",index:"skuCost",width:60,resizable:true},
			{name:"quantity", index:"quantity" ,width:60,resizable:true}],
			//{name:"isSnSku", index:"isSnSku" ,width:60,hidden:true}],
		caption: i18("DETAILTITLE"),
   		sortname: 'id',
    	multiselect: false,
		sortorder: "desc",
		height:"auto",
		rowNum:-1,
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#execute").click(function(){
		if(confirm(i18("EXECUTIONASK"))){
			execution($j("#staID").val());
		}
	});
	$j("#cancel").click(function(){
		if(confirm(i18("CANCELASK"))){
			canceled($j("#staID").val());
		}
	});
	$j("#back").click(function(){
		$j("#searchCondition").removeClass("hidden");
		$j("#details").addClass("hidden");
	});
	
	$j("#search").click(function(){
		var postData = loxia._ajaxFormToObj("form_query");  							 
		$j("#tbl-query-info").jqGrid('setGridParam',{url:$j("body").attr("contextpath")+"/vmitransferstaquery.json",postData:postData,datatype: "json"}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl-query-info",
			{"intStatus":"whSTAStatus","transaction":"whDirectionMode"},
			$j("body").attr("contextpath")+"/vmitransferstaquery.json",postData);
	});
	
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val(0);
	});
	 
});
