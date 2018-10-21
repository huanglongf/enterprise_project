var $j = jQuery.noConflict();
var id=null;
var extBarcode = null;
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}
function showMsg(msg){
	jumbo.showMsg(msg);
}
var onKeydownType = 0;
$j(document).ready(function (){
	
	/*var baseUrl = $j("body").attr("contextpath");
	initShopQuery("companyshop","innerShopCode");
	jumbo.loadShopList("companyshop");
	*/
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});

	

	var invs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findinvstatusbyshop.json");//findInvStatusByOuId
	for(var i in invs){
		$j("<option value='"+invs[i].id+"'>"+invs[i].name+"</option>").appendTo($j("#invStatus"));
	}
	
	loxia.init({debug: true, region: 'zh-CN'});
	var baseUrl = $j("body").attr("contextpath");
	initShopQuery("companyshop","innerShopCode");
	jumbo.loadShopList("companyshop");

	
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});

	$j("#tbl_sta_list").jqGrid({
		url:baseUrl + "/findPdaShelvesSta.json",
		datatype: "json",
		colNames: ["ID","sta创建时间","作业单号","相关单据号","辅助相关单据号","状态","作业类型","staType","店铺","CHANNELCODE","操作"],
		colModel: [
					{name: "id", index: "id", hidden: true},
					{name: "createTime", index: "createTime",hidden:true},
					{name: "code", index: "code",formatter:"linkFmatter", formatoptions:{onclick:"showdetail"}, width: 120, resizable: true,sortable:false},
					{name: "refSlipCode", index: "refSlipCode", width: 120, resizable: true},
					{name: "slipCode1", index: "slipCode1", width: 120, resizable: true},
					{name: "intStaStatus", index: "intStaStatus", width: 80, resizable: true,formatter:'select',editoptions:staStatus},
					{name: "intStaType", index: "intStaType", width: 120, resizable: true,formatter:'select',editoptions:staType},
					{name: "intStaType", index: "staType",hidden: true},
					{name: "channelName", index: "channelName", width: 120, resizable: true},
					{name: "owner", index: "owner", width: 120, resizable: true,hidden:true},
		            {name:"btn3",width:100,resizable:true}
	               ],
		caption: "已核对收货单列表",
		rowNum:10,
	   	sortname: 'id',
		pager:"#pager",
	    multiselect: false,
		viewrecords: true,
   		rownumbers:true,
		sortorder: "desc",
		width:1000,
		height:"auto",
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		loadComplete: function(){
			var btn3 = '<div><button type="button" style="width:100px;" class="confirm" id="print" name="btnDel" loxiaType="button" onclick="check(this);">'+"审核"+'</button></div>';
			var ids = $j("#tbl_sta_list").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				$j("#tbl_sta_list").jqGrid('setRowData',ids[i],{"btn3":btn3});
			}
			loxia.initContext($j(this));
		}
	});
	
	$j("#tbl_sta_line_list").jqGrid({
		datatype: "json",
		colNames: ["ID","SKUID","商品条码","货号","扩展属性","商品名称","计划量","剩余上架","已上架","收货总数量"],
		colModel: [
					{name: "id", index: "id",hidden:true,sortable:false},//序号（ID）
					{name: "skuId", index: "skuId",hidden:true,sortable:false},//SKUID
					{name: "barCode", index: "sku.BAR_CODE", width: 150, resizable: true,sortable:false},//商品条码
					{name: "supplierCode", index: "sku.SUPPLIER_CODE", width: 120, resizable: true,sortable:false},//货号
					{name: "keyProperties", index: "sku.KEY_PROPERTIES", width: 150, resizable: true,sortable:false},//关键属性
					{name: "skuName", index: "sku.name", width: 150, resizable: true,sortable:false},//商品名称
					{name: "quantity", index: "quantity", width:120, resizable:true, sortable:false},//计划量
					{name: "quantity4", index: "quantity4", width:120, resizable:true, sortable:false},//当前执行量
					{name: "quantity2", index: "quantity2", width:120, resizable:true, sortable:false},
					{name: "quantity3", index: "quantity3", width:120, resizable:true, sortable:false}
	               ],
		caption: "确认上架明细",
		rowNum:10,
	    multiselect: false,
	    gridComplete : function(){
			loxia.initContext($j(this));
		},
		width:1000,
		height:"auto",
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	

	$j("#search").click(function(){
		queryStaList();
	});
	
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
	});
	
	$j("#back").click(function(){
		$j("#divDetial").addClass("hidden");
		$j("#divHead").removeClass("hidden");
	});


});


//显示上架单明细数据
function showdetail(obj){
	//initAll();
	jQuery("#tbl_sta_line_list").jqGrid("clearGridData");
	var tr = $j(obj).parents("tr[id]");
	id=tr.attr("id");
	$j("#sta_id").val(id);
	var pl=$j("#tbl_sta_list").jqGrid("getRowData",id);
	$j("#sta_code").text(pl["code"]);
	$j("#sta_refSlipCode").text(pl["refSlipCode"]);
	$j("#sta_slipCode1").text(pl["slipCode1"]);
	$j("#sta_type").text(tr.find("td[aria-describedby$='intStaType']:first").text());
	$j("#sta_status").text(tr.find("td[aria-describedby$='intStaStatus']").text());
	$j("#sta_createTime").text(pl["createTime"]);
	$j("#sta_memo").text(pl["memo"]);
	$j("#stv_id").val(pl["stvId"]);
	$j("#locationCode").focus();
	if(pl["intStaType"] == "41"){
		$j("#invStatusName").removeClass("hidden");
		$j("#invStatusName").focus();
	} else {
		$j("#invStatusName").addClass("hidden");
		$j("#locationCode").focus();
	}
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl_sta_line_list").jqGrid('setGridParam',{page:1,url:baseUrl + "/checkPdaShelvesStaLine.json?sta.id=" + $j("#sta_id").val()}).trigger("reloadGrid");
	$j("#divHead").addClass("hidden");
	$j("#divDetial").removeClass("hidden");
}

//查询sta列表
function queryStaList(){
	var url = $j("body").attr("contextpath") + "/findPdaShelvesSta.json";
	$j("#tbl_sta_list").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_sta_list",{"intStaType":"whSTAType","intStaStatus":"whSTAStatus"},
		url,loxia._ajaxFormToObj("queryForm"));
}

//返回事件
function backClick(){
	$j("#divDetial").addClass("hidden");
	$j("#divHead").removeClass("hidden");
};
//审核
function check(obj){
	var id = $j(obj).parents("tr").attr("id");
	var data=$j("#tbl_sta_list").jqGrid("getRowData",id);
	var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/checkShelves.do",{"staId":data['id']});//更新作业单为完成，删除箱号
	var url=$j("body").attr("contextpath")+"/findPdaShelvesSta.json";
	$j("#tbl_sta_list").jqGrid('setGridParam',{url:url}).trigger("reloadGrid");
}

