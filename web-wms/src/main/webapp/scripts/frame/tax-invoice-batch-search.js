$j.extend(loxia.regional['zh-CN'],{
	GET_DATA_ERROR		:"获取数据异常",

	STA_CODE : "发票单号",
	INVOICE_STATUS : "发票状态",
	OWNER : "平台店铺名称",
	LPCODE: "物流服务商",
	TRACKING_NO : "快递单号",
	CRAETE_TIME : "创建时间",
	OPERATOR_TIME : "操作时间",
	LAST_MODIFY_TIME : "最后修改时间",
	OPERATOR : "操作人",
	PLAN_COUNT : "计划执行总数量",
	BATCHCODE : "批次号",
	PGINDEX : "顺序号",
	PROVINCE : "省",
	CITY : "市",
	DISTRICT : "区",
	ADDRESS : "地址",
	RECEIVER : "收件人",
	MOBILE : "手机",
	TELEPHONE : "电话",
	OPERATING : "操作",
	LPCODE_IS_NOT_EQUAL : "批次操作选择物流商必须一致！",
	OWNER_IS_NOT_EQUAL : "批次操作选择店铺必须一致！",
	
	INVOICE_ORDER_LIST_TITLE:"批量补开发票导出-电子面单打印",
	NOT_EQUAL_BATCHNO : "不同批次的发票信息不允许同时批量生成",
	UPDATE_INVOICE_STATUS_SUCCESS : "批量更新发票信息成功！",
	UPDATE_INVOICE_STATUS_ERROR : "批量更新发票信息失败！",
	
	IN_INV:"退换货入库",
	OUT_INV:"换货出库",
	DATA_DETAIL_SELECT : "请选择具体数据！",
	
	
	SKUCODE : "SKU编码",
	BARCODE : "条形码",
	JMCODE : "商品编码",
	SKU_NAME : "商品名称",
	KEY_PROPS : "扩展属性",
	PLAN_QTY : "计划量",
	COMFIRMED_QTY : "执行量",
	SKU_COST : "库存成本",
	
	DETAIL_LIST_TITLE:"销售作业单明细",
	
	STATUS_CREATE:"已创建",
	STATUS_OCCUPIED:"配货中",
	STATUS_CHECKED:"已核对",
	STATUS_INTRANSIT:"已转出",
	STATUS_FINISHED:"已完成"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}
function initChannel(){
	loxia.asyncXhrPost(
		window.parent.$j("body").attr("contextpath")+"/json/findAllChannel.do",
		{},
		{
			success:function(data){
				var rs = data.channelList;
				if(rs.length>0){
					$j("#companyshop option").remove();
					$j("#companyshop").append("<option value=''></option>");
					$j.each(rs,function(i,item){
						$j("#companyshop").append("<option value='"+item.code+"'>"+item.name+"</option>");
					});
					$j("#companyshop").flexselect();
					$j("#companyshop").val("");
				}
			}
		}
	);
}
function bindTable(){
	jumbo.bindTableExportBtn("tbl-invoiceOrderList",{},baseUrl+"/queryInvoiceOrderExport.json",loxia._ajaxFormToObj("queryForm"));
}
$j(document).ready(function (){
	loxia.init({debug: true, region: 'zh-CN'});
	initChannel();
    /* 这里编写必要的页面演示逻辑*/
	baseUrl = $j("body").attr("contextpath");
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransportator.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selLpcode"));
	}
	$j("#tbl-invoiceOrderList").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("STA_CODE"),i18("INVOICE_STATUS"),i18("OWNER"),i18("LPCODE"),i18("TRACKING_NO"),i18("CRAETE_TIME"),i18("LAST_MODIFY_TIME"),"完成时间","系统来源",i18("BATCHCODE"),i18("PGINDEX")],
		colModel: [
		        {name: "id", index: "id", hidden: true},
				{name:"orderCode", index:"orderCode",width:100, resizable:true},
				{name:"intStatus",index:"intStatus",width:80,resizable:true,formatter:'select',editoptions:staStatus},
				{name:"owner", index:"owner",width:120,resizable:true},
				{name:"lpCode", index:"lpCode",width:80,resizable:true,formatter:'select',editoptions:trasportName},
				{name:"transNo", index:"transNo",width:120,resizable:true},
				{name:"createTime",index:"createTime",width:150,resizable:true},
				{name:"lastModifyTime",index:"lastModifyTime",width:150,resizable:true},
				{name:"finishTime",index:"finishTime",width:150,resizable:true},
				{name:"systemKey",index:"systemKey",width:150,resizable:true},
				{name:"batchCode", index:"batchCode",width:120,resizable:true},
				{name:"pgIndex", index:"pgIndex",width:60,resizable:true}],
		caption: i18("INVOICE_ORDER_LIST_TITLE"),
	   	sortname: 'ID',
	    multiselect: false,
	    rowNum:jumbo.getPageSize(),
	   	rowList:jumbo.getPageSizeList(),
	   	pager:"#pager",
		sortorder: "asc",
		viewrecords: true,
	   	rownumbers:true,
		height:jumbo.getHeight(),
		jsonReader: { repeatitems : false, id: "0" }
	});
	$j("#search").click(function (){
		$j("#tbl-invoiceOrderList").jqGrid('setGridParam',{
			url:loxia.getTimeUrl(baseUrl + "/queryInvoiceOrderExport.json"),postData:loxia._ajaxFormToObj("queryForm")
			}).trigger("reloadGrid");
		bindTable();
	});
});

