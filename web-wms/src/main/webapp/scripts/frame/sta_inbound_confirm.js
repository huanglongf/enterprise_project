$j.extend(loxia.regional['zh-CN'],{
	ENTITY_SKU_BARCODE				:	"条形码",
	ENTITY_SKU_JMCODE				:	"商品编码",
	ENTITY_SKU_KEYPROP				:	"扩展属性",
	ENTITY_SKU_NAME					:	"商品名称",
	ENTITY_SKU_SUP					:	"供应商名称",
	ENTITY_STALINE_TOTAL			:	"计划量执行量",
	ENTITY_STALINE_COMPLETE			:	"已执行量",
	ENTITY_SKU_SUPCODE				:	"货号"
});
var extBarcode = null;
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}
function showMsg(msg){
	jumbo.showMsg(msg);
}
var $j = jQuery.noConflict();

function showdetail(obj){
	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");
	$j("#sta_id").val(id);
	var pl=$j("#tbl_sta_list").jqGrid("getRowData",id);
	$j("#sta_code").text(pl["code"]);
	$j("#sta_refSlipCode").text(pl["refSlipCode"]);
	$j("#sta_slipCode1").text(pl["slipCode1"]);
	$j("#isInboundInvoice").val(pl["isInboundInvoice"]);
	$j("#sta_type").text(tr.find("td[aria-describedby$='intStaType']").text());
	$j("#sta_status").text(tr.find("td[aria-describedby$='intStaStatus']").text());
	$j("#sta_createTime").text(pl["createTime"]);
	$j("#sta_memo").text(pl["memo"]);
	$j("#stv_id").val(pl["stvId"]);
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl_sta_line_list").jqGrid('setGridParam',{page:1,url:baseUrl + "/findInboundStvLine.json?sta.stvId=" + pl["stvId"]}).trigger("reloadGrid");
	
	$j("#divHead").addClass("hidden");
	$j("#divDetial").removeClass("hidden");
}

function importReturn(){
	jumbo.showMsg("导入成功！");
	$j("#tbl_sta_dif_line").jqGrid('setGridParam',{page:1,url:$j("body").attr("contextpath") + "/findInboundStvLine.json?sta.stvId=" + $j("#stv_id").val()}).trigger("reloadGrid");
}

function queryStaList(){
	var url = $j("body").attr("contextpath") + "/findInboundConfirmSta.json";
	$j("#tbl_sta_list").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_sta_list",{"intStaType":"whSTAType","intStaStatus":"whSTAStatus"},
		url,loxia._ajaxFormToObj("queryForm"));
}

function submitConfirm(postDate){
	loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+ "/json/confirmInBoundSta.json",
			postDate,
			{
			success:function(data){
				if(data){
					if(data.result=="success"){
						jumbo.showMsg("已核对");
						queryStaList();
						$j("#dialogConfirm").dialog("close");
						$j("#divDetial").addClass("hidden");
						$j("#divHead").removeClass("hidden");
					}else if(data.result=="error"){
						jumbo.showMsg(data.msg);
					}
				} else {
					jumbo.showMsg("数据操作异常");
				}
			},
			error:function(){jumbo.showMsg("数据操作异常");}//操作数据异常
	});
}
//是否存在差异
var isDifference = false;
$j(document).ready(function (){

	$j("#dialogConfirm").dialog({title: "确认差异", modal:true, autoOpen: false, width: 930});
	var baseUrl = $j("body").attr("contextpath");
	initShopQuery("companyshop","innerShopCode");
	jumbo.loadShopList("companyshop");
	
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	$j("#tbl_sta_list").jqGrid({
		url:baseUrl+"/findInboundConfirmSta.json",
		datatype: "json",
		colNames: ["ID","stvId","isInboundInvoice","作业单号","相关单据号","辅助相关单据号","状态","作业类型","店铺","CHANELCODE","确认人","确认总量","创建时间","备注"],
		colModel: [
		           {name: "id", index: "id", hidden: true},
		           {name: "stvId", index: "stvId", hidden: true},
		           {name: "isInboundInvoice", index: "isInboundInvoice", hidden: true},
		           {name: "code", index: "code",formatter:"linkFmatter", formatoptions:{onclick:"showdetail"}, width: 110, resizable: true,sortable:false},
		           {name: "refSlipCode", index: "refSlipCode", width: 100, resizable: true},
		           {name: "slipCode1", index: "slipCode1", width: 100, resizable: true},
		           {name: "intStaStatus", index: "intStaStatus", width: 80, resizable: true,formatter:'select',editoptions:staStatus},
	               {name: "intStaType", index: "intStaType", width: 120, resizable: true,formatter:'select',editoptions:staType},
	               {name: "channelName", index: "channelName", width: 120, resizable: true},
	               {name: "owner", index: "owner", width: 120, resizable: true,hidden:true},
	               {name: "receiptor", index: "receiptor", width: 120, resizable: true},
	               {name: "receiptNumber", index: "receiptNumber", width: 80, resizable: true},
	               {name: "createTime", index: "createTime", hidden: true},
	               {name: "memo", index: "memo", width: 150, resizable: true}
	               ],
		caption: "入库作业单",
		sortname: 'sta.id',
		pager:"#pager",
		multiselect: false,
		sortorder: "desc",
		height:"auto",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		viewrecords: true,
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	//商品明细
	$j("#tbl_sta_line_list").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("ENTITY_SKU_BARCODE"),"SKU商品编码",i18("ENTITY_SKU_JMCODE"),i18("ENTITY_SKU_KEYPROP"),
			i18("ENTITY_SKU_NAME"),i18("ENTITY_SKU_SUP"),"店铺","CHANNELCODE","库存状态","差异量","本次收货量"],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},	
		           {name: "barCode", index: "sku.BAR_CODE", width: 100, resizable: true,sortable:false},
		           {name: "skuCode", index: "sku.code", width: 100, resizable: true,sortable:false},
		           {name: "jmCode", index: "sku.JM_CODE", width: 100, resizable: true,sortable:false},
		           {name: "keyProperties", index: "sku.KEY_PROPERTIES", width: 120, resizable: true,sortable:false},
		           {name: "skuName", index: "sku.name", width: 120, resizable: true,sortable:false},
		           {name: "supplierCode", index: "sku.SUPPLIER_CODE", width: 120, resizable: true,sortable:false},
		           {name: "shopName", index: "shopName", width: 120, resizable: true,sortable:false},
		           {name: "owner", index: "owner", width: 120, resizable: true,sortable:false,hidden:true},
		           {name: "intInvstatusName", index: "status.name", width: 120, resizable: true,sortable:false},
		           {name: "differenceQty", index: "differenceQty", hidden: true},
		           {name: "receiptQty", index: "receiptQty", width: 100, resizable: true,sortable:false}
	               ],
		caption: "确认收货明细",
	   	sortname: 'sku.bar_Code',
	    multiselect: false,
		sortorder: "desc", 
		height:"auto",
		pager:"#pager1",
		viewrecords: true,
   		rownumbers:true,
		rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			isDifference = false;
			var ids = $j("#tbl_sta_line_list").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var datas = $j("#tbl_sta_line_list").jqGrid('getRowData',ids[i]);
				if(datas["differenceQty"] != 0 && datas["differenceQty"] != ""){
					isDifference = true;
					break;
				}
			}
		}
	});
	
	
	$j("#tbl_sta_dif_line").jqGrid({
		//url:"orderNumber.json",
		datatype: "json",
		colNames: ["ID",i18("ENTITY_SKU_BARCODE"),"SKU商品编码","扩展属性","货号","平台对接编码","店铺","CHANNELCODE","库存状态","收货量","剩余计划量","差异量","最终执行量"],
		colModel: [
		           {name: "id", index: "id", hidden: true},		         
		           {name: "barCode", index: "barCode",width: 120, resizable: true},
		           {name: "skuCode", index: "skuCode", width: 120, resizable: true},
		           {name: "keyProperties", index: "keyProperties", width: 80, resizable: true},
		           {name: "supplierCode", index: "supplierCode", width: 60, resizable: true},
		           {name: "extCode1", index: "extCode1", width: 80, resizable: true},
		           {name: "shopName", index: "shopName", width: 100, resizable: true,sortable:false},
		           {name: "owner", index: "owner", width: 100, resizable: true,sortable:false,hidden:true},
		           {name: "intInvstatusName", index: "status.name", width: 80, resizable: true,sortable:false},
	               {name: "receiptQty", index: "receiptQty", width: 40, resizable: true},
	               {name: "remainPlanQty", index: "remainPlanQty", width: 40, resizable: true},
	               {name: "differenceQty", index: "differenceQty", width: 40, resizable: true},
	               {name: "quantity", index: "quantity", width: 60, resizable: true}
	               ],
		caption: "确认收货差异明细",
		sortname: 'sku.bar_Code',
	    multiselect: false,
		sortorder: "desc", 
		height:"auto",
		pager:"#pager2",
		viewrecords: true,
   		rownumbers:true,
		rowNum: 10,
	    rowList:jumbo.getPageSizeList(),
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	
	$j("#search").click(function(){
		queryStaList();
	});
	
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
	});
	
	$j("#confirm").click(function(){
		$j("#tbl_sta_dif_line").jqGrid('setGridParam',{page:1,url:baseUrl + "/findInboundStvLine.json?sta.stvId=" + $j("#stv_id").val()}).trigger("reloadGrid");
		//invoiceInfoInit($j("#isInboundInvoice").val(),"invoice",$j("#sta_refSlipCode").text());
		$j("#dialogConfirm").dialog("open");
	});
	
	$j("#dialogClose").click(function(){
		$j("#dialogConfirm").dialog("close");
	});
	
	$j("#dialogCancel,#cancel").click(function(){
		if(window.confirm("确认取消此次收货？")){
			var postData={};
			postData["sta.stvId"]=$j("#stv_id").val();
			postData["sta.id"]=$j("#sta_id").val();
			loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+ "/json/cancelInBoundConfirm.json",
					postData,
					{
					success:function(data){
						if(data){
							if(data.result=="success"){
								jumbo.showMsg("已取消");
								queryStaList();
								$j("#dialogConfirm").dialog("close");
								$j("#divDetial").addClass("hidden");
								$j("#divHead").removeClass("hidden");
							}else if(data.result=="error"){
								jumbo.showMsg(data.message);
							}
						} else {
							jumbo.showMsg("数据操作异常");
						}
					},
					error:function(){jumbo.showMsg("数据操作异常");}//操作数据异常
			});
		}
	});
	
	$j("#back").click(function(){
		$j("#divDetial").addClass("hidden");
		$j("#divHead").removeClass("hidden");
	});

	/**
	 * 模版导出
	 */
	$j("#expDiff").click(function(){
		$j("#upload").attr("src",$j("body").attr("contextpath") + "/warehouse/exportConfirmDiversity.do?sta.stvId=" + $j("#stv_id").val() + "&sta.code=" + $j("#sta_code").text());	
	});
	
	/**
	 * 商品基础信息维护导出
	 */
	$j("#export").click(function(){
		$j("#upload").attr("src",$j("body").attr("contextpath") + "/warehouse/exportSKUinfo.do?sta.stvId=" + $j("#stv_id").val());	
	});
	
	$j("#impDiff").click(function(){
		var file=$j.trim($j("#staFile").val()),errors=[];
		if(file==""||file.indexOf("xls")==-1){
			errors.push(i18("INVALID_MAND",i18("ENTITY_EXCELFILE")));
		}
		if(errors.length>0){
			jumbo.showMsg(errors.join("<br/>"));
		}else{
			$j("#importForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importConfirmDiversity.do?sta.stvId="+$j("#stv_id").val()));
			loxia.submitForm("importForm");
		}
	});
	
	$j("#submit").click(function(){
		var postDate = {};
		postDate['sta.stvId'] = $j("#stv_id").val();
		/*if(IS_INBOUND_INVOICE==1){
			if(IS_INVOICE_CODE == 0){
				jumbo.showMsg("请填写入正确的发票号！"+INVOICE_ERROR);
				return;
			} else {
				postDate["sta.invoiceNumber"] = $j("#invoice_invoiceNumber").val();
				postDate["sta.dutyPercentage"] = $j("#invoice_dutyPercentage").val();
				postDate["sta.miscFeePercentage"] = $j("#invoice_miscFeePercentage").val();
			}
		}*/
		var differenceMsg = "";
		var msgCount = 0;
		$j.each($j("#tbl_sta_dif_line").jqGrid("getRowData"),function(i,row){
			if(row["differenceQty"] != "0"){
				if(msgCount < 10){
					differenceMsg = differenceMsg + "条码["+row["barCode"]+"]差异量为["+row["differenceQty"]+"]最终执行量为["+row["quantity"]+"]\n";
				}
				msgCount++;
			}
		});
		if(msgCount > 0){
			var msg = "";
			if(msgCount > 10){
				msg = "......更多调整数据请看'确认收货差异明细'列表！\n"
			}
			msg += "差异是否已核对？";
			differenceMsg += msg;
		}
		var isOK = msgCount  > 0 ? window.confirm(differenceMsg) : true;
		if(isOK){
			if(window.confirm("确认后讲无法修改，是否确认此次收货收货差异？")){
				submitConfirm(postDate);
			}
		}
	});
	
	/**导入	 */
	$j("#corImport").click(function(){
		var file=$j.trim($j("#corFile").val()),errors=[];
		if(file==""){
			errors.push(i18("INVALID_MAND",i18("ENTITY_EXCELFILE")));
		}
		if(errors.length>0){
			jumbo.showMsg(errors.join("<br/>"));
		}else{
			$j("#corForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importInboundCertificateOfeceipt.do?sta.code="+$j("#sta_code").text()+"&fileName="+file));
			loxia.submitForm("corForm");
		}
	});
	
	$j("#import").click(function(){
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg("请选择正确的Excel导入文件");//请选择正确的Excel导入文件
			return false;
		}
		//loxia.lockPage();
		$j("#uploadForm").attr("action",window.parent.$j("body").attr("contextpath") + "/warehouse/updateProductInfo.do");
		$j("#uploadForm").submit();
	});
});

function corImportReturn(){
	jumbo.showMsg("导入成功！");
	$j("#corFile").val("")
}