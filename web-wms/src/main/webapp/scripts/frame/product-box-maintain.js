var $j = jQuery.noConflict();

$j.extend(loxia.regional['zh-CN'],{
	CORRECT_FILE_PLEASE:"请选择正确的Excel导入文件"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
} 

var baseUrl = $j("body").attr("contextpath");
//打印装箱明细
$j(document).ready(function(){
	loxia.init({debug: true, region: 'zh-CN'});
	 
	$j("#tbl_query").jqGrid({
		url:baseUrl + "/findproductinfo.json",
		datatype: "json",
		colNames: ["ID","商品编码","商品名称","货号","商品整箱件数","创建时间"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name:"code", index:"code", width:80, resizable:true},
					{name:"name",index:"name",width:200,resizable:true},
					{name:"supplierCode",index:"supplier_code",width:100,resizable:true},
					{name:"boxQty", index:"box_qty" ,width:50,resizable:true },
					{name:"updateTime",index:"update_time",width:130,resizable:true}
				  ],
		caption: "商品列表",
	   	sortname: 'id',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),	
	   	height:jumbo.getHeight(),
	   	pager:"#tbl_query_page",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	jumbo.bindTableExportBtn("tbl_query",{},baseUrl + "/findproductinfo.json");

	//查询
	$j("#search").click(function(){
		var url = baseUrl + "/findproductinfo.json";
		$j("#tbl_query").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("formQuery")}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl_query",{},
			url,loxia._ajaxFormToObj("formQuery"));
	});
	//查询重置
	$j("#reset").click(function(){
		$j("#formQuery input").val("");
	});
	
	$j("#import").click(function(){
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));
			return;
		}
		loxia.lockPage();
		$j("#importForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/productboxcountimport.do"));
		loxia.submitForm("importForm");
	});
});

function reloadProductInfoList(){
	$j("#file").val("");
	$j("#formQuery input").val("");
	$j("#tbl_query").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findproductinfo.json")}).trigger("reloadGrid");
}
 