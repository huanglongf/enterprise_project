$j.extend(loxia.regional['zh-CN'],{
	CORRECT_FILE_PLEASE				:	"请选择正确的Excel导入文件",
	ENTITY_INMODE					:	"上架批次处理模式",
	ENTITY_EXCELFILE				:	"正确的excel导入文件",
	ENTITY_STA_CREATE_TIME			:	"作业创建时间",
	ENTITY_STA_ARRIVE_TIME			:	"预计到货日期",
	ENTITY_STA_CODE					:	"作业单号",
	ENTITY_STA_PROCESS				:	"执行情况",
	ENTITY_STA_UPDATE_TIME			:	"上次执行时间",
	ENTITY_STA_PO					:	"PO单号",
	ENTITY_STA_OWNER				:	"店铺名称",
	ENTITY_STA_SUP					:	"供应商名称",
	ENTITY_STA_SEND_MODE			:	"送货方式",
	ENTITY_STA_REMANENT				:	"剩余计划入库量（件）",
	ENTITY_STA_COMMENT				:	"PO单备注",
	ENTITY_SKU_BARCODE				:	"条形码",
	ENTITY_SKU_JMCODE				:	"商品编码",
	ENTITY_SKU_KEYPROP				:	"扩展属性",
	ENTITY_SKU_NAME					:	"商品名称",
	ENTITY_SKU_SUPCODE				:	"货号",
	ENTITY_STALINE_TOTAL			:	"计划量执行量",
	ENTITY_STALINE_COMPLETE			:	"已执行量",
	ENTITY_STALINE_CURRENT			:	"本次执行量",
	ENTITY_LOCATION					:	"库位",
	ENTITY_LOCATION_CAPACITY		:	"库容",
	ENTITY_LOCATION_CURRENT			:	"当前库存数",
	ENTITY_LOCATION_ADD				:	"上架数",			
	ENTITY_STALINE_SNS				:	"SN序列号"		
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
	$j("#staFile").val("");
	var pl=$j("#tbl_sta_list").jqGrid("getRowData",id);
	$j("#sta_code").text(pl["code"]);
	$j("#sta_refSlipCode").text(pl["refSlipCode"]);
	$j("#sta_type").text(tr.find("td[aria-describedby$='intStaType']").text());
	$j("#sta_status").text(tr.find("td[aria-describedby$='intStaStatus']").text());
	$j("#sta_slipCode1").text(pl["slipCode1"]);
	$j("#sta_createTime").text(pl["createTime"]);
	$j("#sta_memo").text(pl["memo"]);
	
	showReceiptNumber();
	
	$j("#divHead").addClass("hidden");
	$j("#divDetial").removeClass("hidden");
}
var staType;
var staStatus;
$j(document).ready(function (){
	$j("#refShlpCode").focus();
	var baseUrl = $j("body").attr("contextpath");
	initShopQuery("companyshop","innerShopCode");
	jumbo.loadShopList("companyshop");
	loxia.init({debug: true, region: 'zh-CN'});
	
	staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	$j("#tbl_sta_list").jqGrid({
		url:baseUrl+"/findInboundStaByType.json",
		datatype: "json",
		colNames: ["ID","作业单号","相关单据号","辅助相关单据号","状态","作业类型","店铺","CHANNELCODE","创建时间","备注"],
		colModel: [
		           {name: "id", index: "id", hidden: true},	
		           {name: "code", index: "code",formatter:"linkFmatter", formatoptions:{onclick:"showdetail"}, width: 110, resizable: true,sortable:false},
		           {name: "refSlipCode", index: "refSlipCode", width: 100, resizable: true},
		           {name: "slipCode1", index: "slipCode1", width: 100, resizable: true},
		           {name: "intStaStatus", index: "intStaStatus", width: 80, resizable: true,formatter:'select',editoptions:staStatus},
	               {name: "intStaType", index: "intStaType", width: 120, resizable: true,formatter:'select',editoptions:staType},
	               {name: "channelName", index: "channelName", width: 120, resizable: true},
	               {name: "owner", index: "owner", width: 120, resizable: true,hidden:true},
	               {name: "createTime", index: "createTime", width: 150, resizable: true},
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

	var snSku = {"value":"0:否;1:是;"};
	var stMode = {"value":"11:否;22:否;33:是;"};
	//商品明细
	$j("#tbl_sta_line_list").jqGrid({
		datatype: "json",
		colNames: ["ID","STAID","SKUID","OWNER","skuCost",i18("ENTITY_SKU_BARCODE"),i18("ENTITY_SKU_JMCODE"),i18("ENTITY_SKU_KEYPROP"),
			i18("ENTITY_SKU_NAME"),i18("ENTITY_STA_SUP"),"店铺","CHANNELCODE","库存状态",i18("ENTITY_STALINE_TOTAL"),i18("ENTITY_STALINE_COMPLETE"),"本次计划确认量","是否SN号商品","是否保质期商品"],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},	
		           {name: "sta.id", index: "sta.id", hidden: true,sortable:false},	
		           {name: "skuId", index: "sku.id", hidden: true,sortable:false},	
		           {name: "owner", index: "owner", hidden: true,sortable:false},	
		           {name: "skuCost", index: "skuCost", hidden: true,sortable:false},	
		           {name: "barCode", index: "sku.BAR_CODE", width: 100, resizable: true,sortable:false},
		           {name: "jmcode", index: "sku.JM_CODE", width: 100, resizable: true,sortable:false},
		           {name: "keyProperties", index: "sku.KEY_PROPERTIES", width: 120, resizable: true,sortable:false},
		           {name: "skuName", index: "sku.name", width: 120, resizable: true,sortable:false},
		           {name: "jmskuCode", index: "sku.SUPPLIER_CODE", width: 120, resizable: true,sortable:false},
		           {name: "channelName", index: "channelName", width: 120, resizable: true,sortable:false},
		           {name: "owner", index: "owner", width: 120, resizable: true,sortable:false,hidden:true},
		           {name: "intInvstatusName", index: "intInvstatusName", width: 80, resizable: true,sortable:false},
		           {name: "quantity", index: "quantity", width: 100, resizable: true,sortable:false},
		           {name: "completeQuantity", index: "completeQuantity", width: 100, resizable: true,sortable:false},
		           {name: "receiptQty", index: "receiptQty", width: 100, resizable: true,sortable:false},
		           {name: "isSnSku", index: "sku.isSnSku", width: 100, resizable: true,sortable:false,formatter:'select',editoptions:snSku},
		           {name: "storeMode", index: "sku.storeMode", width: 100, resizable: true,sortable:false,formatter:'select',editoptions:stMode}
	               ],
		caption: "入库明细单",
	   	sortname: 'isSnSku,sku.bar_Code',
	    multiselect: false,
		sortorder: "desc", 
		height:"auto",
		pager:"#pager1",
		viewrecords: true,
   		rownumbers:true,
		rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
		jsonReader: { repeatitems : false, id: "0" }
	});
	$j("#search").click(function(){
		queryStaList();
	});
	
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
	});
	
	
	
	$j("#back").click(function(){
		$j("#refShlpCode").focus();
		$j("#divDetial").addClass("hidden");
		$j("#divHead").removeClass("hidden");
	});
	
    $j("#refShlpCode").bind('keypress',function(event){
        if(event.keyCode == "13")    {
        	queryStaList();
        }
    });

	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	/**模版导出	 */
	$j("#exportZip").click(function(){
		var id = $j("#sta_id").val();
		$j("#upload").attr("src",$j("body").attr("contextpath") + "/warehouse/exportInBound.do?sta.id=" + id + "&sta.code=" + $j("#sta_code").text());	
	});
	
	
	
	/**导入	 */
	$j("#import").click(function(){
		var file=$j.trim($j("#staFile").val()),errors=[];
		if(file==""||file.indexOf("xls")==-1){
			errors.push(i18("INVALID_MAND",i18("ENTITY_EXCELFILE")));
		}
		if(errors.length>0){
			jumbo.showMsg(errors.join("<br/>"));
		}else{
			$j("#importForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importStaInbound.do?sta.id="+$j("#sta_id").val()));
			loxia.submitForm("importForm");
		}
	});
	
	/**确认	 */
	$j("#confirm").click(function(){
		var ids = $j("#tbl_sta_line_list").jqGrid('getDataIDs');
		for(var i=0;i<ids.length;i++){
			var isSnSku= $j("#tbl_sta_line_list").getCell(ids[i],"isSnSku");//编码
			var storeMode= $j("#tbl_sta_line_list").getCell(ids[i],"storeMode");//编码
			if(isSnSku == 1){
				jumbo.showMsg("SN号商品不能无条件收货");
				return;
			}
			if(storeMode == 1){
				jumbo.showMsg("保质期商品不能无条件收货");
				return;
			}
		}
	loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+ "/json/noChoseinBoundAffirm.json",
				{'sta.id':$j("#sta_id").val()},
				{
				success:function(data){
					if(data){
						if(data.result=="success"){
							jumbo.showMsg("操作成功");
							queryStaList();
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
	});
});


function queryStaList(){
	var url = $j("body").attr("contextpath") + "/findInboundStaByType.json";
	$j("#tbl_sta_list").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_sta_list",{"intStaType":"whSTAType","intStaStatus":"whSTAStatus"},
		url,loxia._ajaxFormToObj("queryForm"));
}

function showReceiptNumber(){
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl_sta_line_list").jqGrid('setGridParam',{page:1,url:baseUrl + "/findStaLineBySta.json?sta.id=" + $j("#sta_id").val()}).trigger("reloadGrid");
}

function importReturn(){
	jumbo.showMsg("导入成功！");
	showReceiptNumber();
}
