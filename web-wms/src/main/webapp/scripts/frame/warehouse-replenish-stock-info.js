$j.extend(loxia.regional['zh-CN'],{
	REPLENISH_STOCK_IMPORT : "库存安全警告数设置导入",
	CORRECT_FILE_PLEASE : "请选择正确的Excel导入文件"
});


function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

function showMsg(msg,txtMsg){
	jumbo.showMsg(msg);
	$j("#errorMsg").val(txtMsg);
}
$j(document).ready(function (){
	loxia.init({debug: true, region: 'zh-CN'});
	var baseUrl = window.$j("body").attr("contextpath");
	$j("#reset").click(function(){
		document.getElementById("query_form").reset();
	});
	
	
	$j("#export").click(function(){
		var iframe = document.createElement("iframe");
		iframe.src = loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/excelDownload.do?fileName="+i18("REPLENISH_STOCK_IMPORT")+".xls&inputPath=tplt_import_replenish_stock.xls");
		iframe.style.display = "none";
		$j("#download").append($j(iframe));
	});
	
	  $j("#refWhShop").click(function(){
		  if(!/^.*\.xls$/.test($j("#file").val())){
				jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));//请选择正确的Excel导入文件
				return false;
			}
			loxia.lockPage();
		  $j("#importForm").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importwhReplenishStock.do"));
			loxia.submitForm("importForm");
	  });
	
	$j("#query").click(function(){
    	 var postData11 = {};
    	   postData11["DistrictCode"]=$j("#DistrictCode").val();
    	   postData11["LocationCode"]=$j("#LocationCode").val();
		   $j("#tbl_rec").jqGrid('setGridParam',{
			   url:baseUrl + "/findLocationsJson.json",
			   postData:postData11,
			   page:1
		   }).trigger("reloadGrid");
		   jumbo.bindTableExportBtn("tbl_rec",{},
					baseUrl + "/findLocationsJson.json",postData11);
	});
	
	function getDt(cellvalue,options,rowObject){
		return cellvalue.split(" ")[0];
	}
	 $j("#tbl_rec").jqGrid({
		 url: baseUrl+"/findLocationsJson.json",
 		datatype: "json",
 		colNames: ["ID","库位编码","库位容积(UNIT)","库位容积率(%)","库存安全警告数"],
 		colModel: [
            {name: "id", index: "id", hidden: true},		         
            {name: "code", index: "code", width: 110, resizable: true},
            {name: "capacity", index: "capacity", width: 110, resizable: true},
            {name: "capaRatio", index: "capaRatio", width: 110, resizable: true},
            {name: "warningNumber", index: "warningNumber", width: 110, resizable: true}],
 		caption: "库位列表",
 		sortname: 'loc.ID',
 		multiselect: false,
 	    height:"auto",
 	    rowNum: jumbo.getPageSize(),
 	    rowList:jumbo.getPageSizeList(),
 	    pager: "#tbl_rec_page",
 		sortorder: "desc",
 		viewrecords: true,
 			rownumbers:true,
 		jsonReader: { repeatitems : false, id: "0" }
	});
	 jumbo.bindTableExportBtn("tbl_rec",{},
				baseUrl + "/findLocationsJson.json");
	
     
    
     
     
});

