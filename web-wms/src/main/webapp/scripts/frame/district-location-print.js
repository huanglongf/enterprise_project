var $j = jQuery.noConflict();

$j.extend(loxia.regional['zh-CN'],{
	 
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}
//打印装箱明细
function printPg(id){
	loxia.lockPage();
	if (id != null){
		var url = $j("body").attr("contextpath") + "/printoutboundpackageinfo.json?staid="+ id;  
		printBZ(loxia.encodeUrl(url),true);		
	}
	loxia.unlockPage();
}
$j(document).ready(function(){
	loxia.init({debug: true, region: 'zh-CN'});

	$j("#tabs").tabs();
	
	var baseUrl = $j("body").attr("contextpath");
	$j("#tb_loc").jqGrid({
		url:baseUrl + "/findvalidlocations.json",
		datatype: "json",
		colNames: ["ID","库位编码","操作"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name:"code",index:"code",width:150, resizable:true},
					{name: "operate", width: 140, resizable:true}
					],
		caption: "库位列表",
	   	sortname: 'id',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#tb_loc_page",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var ids = $j("#tb_loc").jqGrid('getDataIDs');
			var btn = '<button type="button" class="confirm" name="btnPrint" loxiaType="button">打印</button>';
			for(var i=0;i < ids.length;i++){
				var datas = $j("#tb_loc").jqGrid('getRowData',ids[i]);
				$j("#tb_loc").jqGrid('setRowData',ids[i],{"operate":btn});
			}
			loxia.initContext($j(this));
			$j("button[name='btnPrint']").click(function(){
				var id =$j(this).parents("tr").attr("id");
				printLocation(id);
			});
		}
	});
	jumbo.bindTableExportBtn("tb_loc",{}, baseUrl + "/findvalidlocations.json", {});
	
	$j("#tb_district").jqGrid({
		url:baseUrl + "/findvaliddistricts.json",
		datatype: "json",
		colNames: ["ID","库区编码","打印库区编码","打印库位编码"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name:"code",index:"code",width:140, resizable:true},
					{name: "btn", width: 120, resizable:true},
					{name: "btn2", width: 120, resizable:true}
					],
		caption: "库区列表",
	   	sortname: 'id',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#tb_district_page",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var ids = $j("#tb_district").jqGrid('getDataIDs');
			var btn = '<button type="button" class="confirm" name="btnPrint1" loxiaType="button">打印</button>';
			var btn2 = '<button type="button" class="confirm" name="btnPrint2" loxiaType="button">打印</button>';
			for(var i=0;i < ids.length;i++){
				var datas = $j("#tb_district").jqGrid('getRowData',ids[i]);
				$j("#tb_district").jqGrid('setRowData',ids[i],{"btn":btn});
				$j("#tb_district").jqGrid('setRowData',ids[i],{"btn2":btn2});
			}
			loxia.initContext($j(this));
			$j("button[name='btnPrint1']").click(function(){
				var id =$j(this).parents("tr").attr("id");
				printDistrict(id);
			});
			$j("button[name='btnPrint2']").click(function(){
				var id =$j(this).parents("tr").attr("id");
				printDistrictLocations(id);
			});
		}
	});
	jumbo.bindTableExportBtn("tb_district",{}, baseUrl + "/findvaliddistricts.json", {});
	

	//查询
	$j("#locQuery").click(function(){
		var url = baseUrl + "/findvalidlocations.json";
		$j("#tb_loc").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("locationForm")}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tb_loc",{}, baseUrl + "/findvalidlocations.json",loxia._ajaxFormToObj("locationForm"));
	});
	
	//查询
	$j("#districtQuery").click(function(){
		var url = baseUrl + "/findvaliddistricts.json";
		$j("#tb_district").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("districtForm")}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tb_district",{}, baseUrl + "/findvaliddistricts.json",loxia._ajaxFormToObj("districtForm"));
	});
	
	//查询重置
	$j("#locReset").click(function(){
		$j("#locationForm input").val("");
	});
	$j("#districtReset").click(function(){
		$j("#districtForm input").val("");
	});
});
//库位打印
function printLocation(id){
	// var id =$j(tag).parents("tr").attr("id");
	loxia.lockPage();
	if (id != null){
		var url = $j("body").attr("contextpath") + "/printlocationbarcode.json?id="+ id;  
		printBZ(loxia.encodeUrl(url),true);		
	}
	loxia.unlockPage();
}
//库区打印
function printDistrict(id){
	//var id =$j(tag).parents("tr").attr("id");
	loxia.lockPage();
	if (id != null){
		var url = $j("body").attr("contextpath") + "/printdistrictcode.json?id="+ id;  
		printBZ(loxia.encodeUrl(url),true);		
	}
	loxia.unlockPage();
}
//库区-库位打印
function printDistrictLocations(id){
	//var id =$j(tag).parents("tr").attr("id");
	loxia.lockPage();
	if (id != null){
		var url = $j("body").attr("contextpath") + "/printdistrictrelativelocations.json?id="+ id;  
		printBZ(loxia.encodeUrl(url),true);		
	}
	loxia.unlockPage();
}