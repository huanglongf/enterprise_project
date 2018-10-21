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
	ADDRESS:		"送货地址",
	RECEIVER:		"联系人",
	MOBILE:			"联系方式",
	
	TITLE:			"仓库其他出入库作业单列表",
	
	INSTOCK:		"其他入库",
	OUTSTOCK:		"其他出库",
	
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
	DETAILTITLE:	"作业单明细"
	
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

var $j = jQuery.noConflict();

function snTemplateDownloadurl(dir){
	if(dir == '其他出库'){
		var url = $j("body").attr("contextpath") + "/warehouse/excelDownload.do?fileName=" + i18("OUT_SN_TEMPLATE") + ".xls&inputPath=tplt_import_warehouse_sn_import.xls";
		$j("#sntemplateHref").attr("href",url);
	} else {
		var url = $j("body").attr("contextpath") + "/warehouse/excelDownload.do?fileName=" + i18("IN_SN_TEMPLATE") + ".xls&inputPath=tplt_import_warehouse_sns.xls";
		$j("#sntemplateHref").attr("href",url);
	}
}

function showMsg(message){
	jumbo.showMsg(message);
}

function snsFormSubmit(dir){
	if(dir == '其他出库'){
		// 出库url
		var url = loxia.getTimeUrl($j("body").attr("contextpath")+ "/warehouse/outboundSnImport.do?staId=" + $j("#staID").val());		
		$j("#snsForm").attr("action",url);
	} else {
		// 入库url
		var url = loxia.getTimeUrl($j("body").attr("contextpath")+ "/warehouse/snsimport.do?staID=" + $j("#staID").val());
		$j("#snsForm").attr("action",url);
	}
}

function showDetail(obj){
 	var id =$j(obj).parents("tr").attr("id");
 	$j("#snsfile").val("");
 	jumbo.bindTableExportBtn("tbl-details",{"intType":"whSTAType","intStatus":"whSTAStatus"},$j("body").attr("contextpath")+"/operationOthersOperateQueryDetails.json?",{"staID":id});
	var vals =  $j("#tbl-query-info").jqGrid('getRowData',id);
	$j("#staID").val(vals["id"]);
	$j("#code").html(vals["code"]);
	$j("#store").html(vals["store"]);
	$j("#Dtransaction").html(vals["transaction"]);
	$j("#creater").html(vals["creater"]);
	$j("#operator").html(vals["operator"]);
	$j("#Dstatus").html(vals["status"]);
	$j("#createTime").html(vals["createTime"]);
	$j("#finishTime").html(vals["finishTime"]);
	$j("#is_out").addClass("hidden");
	$j("#memo").html(vals["memo"]);
	$j("#pmFile").val("");
	snTemplateDownloadurl(vals["transaction"]);
	snsFormSubmit(vals["transaction"]);
	if(vals["status"]==i18("STATUSNEW") || vals["status"]==i18("STATUSOCCUPIED")){
		$j("#execute").removeClass("hidden");
		$j("#cancel").removeClass("hidden");
		if($j("#Dtransaction").html() == i18("OUTSTOCK")){
			$j("#is_out").removeClass("hidden");
		}
	}else if(vals["status"]==i18("STATUSOCCUPIED")){
		$j("#cancel").removeClass("hidden");
		$j("#execute").addClass("hidden");
	}else {
		$j("#cancel").addClass("hidden");
		$j("#execute").addClass("hidden");
	}
	// 获取备注
	var url = $j("body").attr("contextpath")+ "/operationOthersOperateQueryDetails.json?staID="+id;
	$j("#tbl-details").jqGrid('setGridParam',{url:url,datatype: "json",}).trigger("reloadGrid");	
	
	var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/operationOthersOperateQueryDetails2.json?staID="+id);
	
	if(rs.result=='success'){
	   	 	//显示div
			if($j("#Dstatus").text()==i18("STATUSFINISHED") || $j("#Dstatus").text()==i18("STATUSCANCELED")){	
				$j("#importsn").addClass("hidden");
			}else{
				$j("#importsn").removeClass("hidden");
			}
	    }else {
	    	//隐藏DIV
	    	$j("#importsn").addClass("hidden");
	    }
	
	
	$j("#searchCondition").addClass("hidden");
	$j("#details").removeClass("hidden");
	
}

	/*function showDt(id){
		var baseUrl = $j("body").attr("contextpath");
		$j("#tbl-details").jqGrid('setGridParam',{
			url:loxia.getTimeUrl(baseUrl+"/operationOthersOperateQueryDetails.json"),
			postData:{"staID":id},		
		}).trigger("reloadGrid");
			
	}*/




//执行其他出入库作业单
function execution(id){
	var postData = {};
	postData['staID']=id;
	postData["sns"]=getSnsData();
	var rsl=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/operationOthersOperateQueryDetails2.json?staID="+id);
	/*var ids = $j("#tbl-details").jqGrid('getDataIDs');
	var num=0;
	for(var i=0;i < ids.length;i++){
		var datas = $j("#tbl-details").jqGrid('getRowData',ids[i]);
		if (datas['isSnSku']==1){
			num++;
			return false;
		}
	}*/
	if(rsl.result=="success"){// 有sn号的商品，查询sn表的sn数量
		var rs;
		var podata = {};
		podata['staID']=id;
		rs =  loxia.syncXhrPost($j("body").attr("contextpath") + "/findsnskuqtybystaid.json",podata);	
		if(rs && rs.qty==0){
			jumbo.showMsg("当前sku需要SN序列号...");
			return false;
		}else{
			execution2(postData);
			
		}
	}else{
		execution2(postData);
	}
}
function execution2(postData){
	loxia.asyncXhrPost(
			$j("body").attr("contextpath") + "/operateExecution.json",
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
//取消其他出入库作业单
function canceled(id){
	var postData = {};
	postData['staID']=id;
	loxia.asyncXhrPost(
			$j("body").attr("contextpath") + "/operateCanceled.json",
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
		url:loxia.getTimeUrl(baseUrl+"/operationOthersOperateQuery.json"),
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
		//url:baseUrl+"/operationOthersOperateQuery.json",
		//postData:loxia._ajaxFormToObj("form_query"),
		datatype: "json",
		//colNames: ["ID","作业单号","事物方向","状态","店铺","创建时间","完成时间","创建人","操作人","操作","MEMO"],
		colNames: ["ID",i18("STACODE"),"相关单据","作业单类型","事物方向",i18("STATUS"),i18("STATUS"),i18("STORE"),i18("ADDRESS"),i18("RECEIVER"),i18("MOBILE"),i18("CREATETIME"),i18("COMPLETETIME"),"计划执行量","MEMO"],
		colModel: [
				{name: "id", index: "id", hidden: true},
				{name :"code",index:"code",formatter:"linkFmatter",formatoptions:{onclick:"showDetail"},width:120,resizable:true},
				{name: "refSlipCode", index: "sta.slip_code",width: "80", resizable: true},
				{name: "transactionTypeName", index: "transactionTypeName",width: "100", resizable: true},
				{name: "transaction", index: "transaction",width: "80", resizable: true},
				{name: "status", index: "status",width: "80", resizable: true,hidden:true},
				{name: "intStatus", index: "status",width: "80", resizable: true,
				        	   formatter:'select',editoptions:status,sortable:false},
				{name: "store", index: "store",width: "120", resizable: true},
				{name: "address", index: "address",width: "120", resizable: true},
				{name: "receiver", index: "receiver",width: "80", resizable: true},
				{name: "mobile", index: "mobile",width: "100", resizable: true},
				{name: "createTime", index: "createTime",width: "130", resizable: true},
				{name: "finishTime", index: "finishTime",width: "130", resizable: true},
				//{name: "creater", index: "creater",width: "90", resizable: true},
				//{name: "operator", index: "operator",width: "90", resizable: true},
				{name: "count", index: "count",width: "60", resizable: true},
				//{name: "isSns", width: "isSns", resizable: true},
				{name: "memo", index: "memo", hidden: true}],
		caption: i18("TITLE"),
		// rowNum:10,
		// rowList:[5,10,15,20,25,30],
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#pager",	
//		rowNum:-1,
		height:"auto",
   		sortname: 'id',
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
				var tra = i18("OUTSTOCK");
				if(datas["transaction"]=="13"){
					tra= i18("INSTOCK");
				}
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
				$j("#tbl-query-info").jqGrid('setRowData',ids[i],{"transaction":tra});
			}
			 
			/*var btn1 = '<div style="float: left"><button type="button" style="width:75px;" class="confirm" name="btnExecute" loxiaType="button">'+i18("EXECUTION")+'</button></div>';
			var btn2 = '<div style="float: left"><button type="button" style="width:75px;" name="btnCancel" loxiaType="button">'+i18("CANCEL")+'</button></div>';
			var btnNull = '<div style="width: 75px;float: left">&nbsp;</div>';
			
			var ids = $j("#tbl-query-info").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var datas = $j("#tbl-query-info").jqGrid('getRowData',ids[i]);
				var btn = btn1 + btn2;
				var statusVal= "";
				var tra = i18("OUTSTOCK");
				if(datas["transaction"]=="1"){
					tra= i18("INSTOCK");
				}
				if(datas["status"]=="CREATED"){
					btn = btn1 + btn2;
					statusVal= i18("STATUSNEW");
				}else if(datas["status"]=="OCCUPIED"){
					btn = btn1 + btn2; 
					statusVal= i18("STATUSOCCUPIED");
				}else if(datas["status"]=="FINISHED"){
					btn = btnNull; 
					statusVal= i18("STATUSFINISHED");
				}else if(datas["status"]=="CANCELED"){
					btn = btnNull;
					statusVal= i18("STATUSCANCELED");
				}else {
					// -----
				}
				$j("#tbl-query-info").jqGrid('setRowData',ids[i],{"status":statusVal});
				$j("#tbl-query-info").jqGrid('setRowData',ids[i],{"transaction":tra});
				$j("#tbl-query-info").jqGrid('setRowData',ids[i],{"operate":btn});
			}*/
			/*loxia.initContext($j(this));
			$j("button[name='btnExecute']").click(function(){
				var id =$j(this).parents("tr").attr("id");
				if(!window.confirm(i18("EXECUTIONASK"))){
					return false;
				}
				execution(id);
			});
			$j("button[name='btnCancel']").click(function(){
				var id = $j(this).parents("tr").attr("id");
				// 作废交接清单
				if(!window.confirm(i18("CANCELASK"))){
					return false;
				}
				canceled(id);
				var postData = {};
				postData['staID']=id;

			});*/
		}
	});
	
	$j("#tbl-details").jqGrid({
//		url:baseUrl+"/operationOthersOperateQueryDetails.json",
		// postData:{"staID":id},
		datatype: "json",
		// "已发货单据数","已发货商品件数",
		//colNames: ["ID","条形码","商品编码","库区","库位","状态","成本","数量"],
		colNames: ["ID","货号","商品名称","SKU编码","条形码","商品编码","扩展属性",i18("DISTRICT"),i18("LOCATION"),i18("STATUS"),i18("QUANTITY"),"isSnSku"],
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
			{name:"quantity", index:"quantity" ,width:60,resizable:true},
			{name:"isSnSku", index:"isSnSku" ,width:60,hidden:true}],
		caption: i18("DETAILTITLE"),
   		sortname: 'id',
    	multiselect: false,
		sortorder: "desc",
		height:"auto",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		pager:"#pager2",	 
		viewrecords: true,
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },

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
//		var errors=loxia.validateForm("modifyForm");
//		if(errors.length>0){
//			jumbo.showMsg(errors);
//			return false;
//		}
		var postData = loxia._ajaxFormToObj("form_query");
		$j("#tbl-query-info").jqGrid('setGridParam',{url:$j("body").attr("contextpath")+"/operationOthersOperateQuery.json",postData:postData}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl-query-info",
			{"intStatus":"whSTAStatus","transaction":"whDirectionMode"},
			$j("body").attr("contextpath")+"/operationOthersOperateQuery.json",postData);
		jumbo.bindTableExportBtn("tbl-query-info",{"intStatus":"whSTAStatus","transaction":"whDirectionMode"});
	});
	
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val(0);
	});
	$j("#snsimport").click(function(){
		/// 出库导入 manage方法： outboundSnImportByStv
		var errors=[];
		var file=$j.trim($j("#snsfile").val());
		if(file==""||file.indexOf("xls")==-1){
			errors.push("请导入excel文件");
		}
		if(errors.length>0){
			jumbo.showMsg(errors);
			return false;
		}
		loxia.lockPage();
		//$j("#snsForm").attr("action",	loxia.getTimeUrl($j("body").attr("contextpath")+ "/warehouse/snsimport.do?staID=" + $j("#staID").val()));
		loxia.submitForm("snsForm");
	});
	
	//---------------包材导入-----
	$j("#inputPM").click(function (){
		var file = $j("#pmFile").val();
		var postfix = file.split(".")[1];
		if(postfix != "xls" && postfix != "xlsx"){
			jumbo.showMsg("请选择导入包材数据");
			return;
		}
		loxia.lockPage();
		$j("#importPMForm").attr("action", loxia.getTimeUrl(baseUrl + "/warehouse/packagingMaterialsImport.do?sta.id="+$j("#staID").val()));
		loxia.submitForm("importPMForm");
	});
	var warehouse = loxia.syncXhr($j("body").attr("contextpath") + "/getWHByOuId.json");
	if(!warehouse.exception){
		if(warehouse.warehouse.isNeedWrapStuff){
			$j("#packaging_materials").removeClass("hidden");
		} 
	}
});



function getSnsData(){
	var sns=[];
	$j.each($j(window.frames["snsupload"].document).find(".sns"),function(i,e){
		sns.push($j(e).val());
	});
	return sns.join(";");
}

