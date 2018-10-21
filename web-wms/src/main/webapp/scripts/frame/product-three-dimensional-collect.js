var $j = jQuery.noConflict();

$j.extend(loxia.regional['zh-CN'],{
	CORRECT_FILE_PLEASE:"请选择正确的Excel导入文件",
	UPDATE_SUCCESSFUL:"更新成功",
	NO_QUERY_DATA:"没有查询到数据",
	DATA_UPDATE_EXCEPTION:"数据更新异常,请联系管理员!",
	PLEASE_INPUT_WEIGHT:"请输入重量",
	PLEASE_INPUT_CUBIC:"请输入长/宽/高",
	PLEASE_INPUT_BARCODE:"请输入条码",
	PLEASE_INPUT_ISSUPPLIERCODE:"请选择是否更新到货号",
	PLEASE_SELECT_DATA:"请选择需要更新的数据",
	DEVICE_SCAN_EXCEPTION:"设备扫描异常"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
} 

var baseUrl = $j("body").attr("contextpath");

$j(document).ready(function(){
	loxia.init({debug: true, region: 'zh-CN'});
	$j("#tbl_query").jqGrid({
		//url:baseUrl + "/findthreedimensionaldata.json",
		datatype: "json",
		colNames: ["ID","客户名称","商品名称","商品编码","货号","条形码","关键属性"],
		colModel: [ {name: "id", index: "id", hidden: true},
					{name:"customerName", index:"customerName", width:80, resizable:true},
					{name:"name",index:"name",width:200,resizable:true},
					{name:"code",index:"code",width:100,resizable:true},
					{name:"supplierCode", index:"supplier_code" ,width:50,resizable:true },
					{name:"barCode",index:"bar_code",width:130,resizable:true},
					{name:"keyProperties",index:"key_properties",width:130,resizable:true}
				  ],
		caption: "商品三维数据采集",
	   	sortname: 'id',
	    multiselect: true,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),	
	   	height:jumbo.getHeight(),
	   	pager:"#tbl_query_page",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});

	var data = {};
	function checkData(){
		
		var $jlym = $j("#frame-container");
		var isSupplierCode = $j("#isSupplierCode").val();
		if (null == isSupplierCode || "" == isSupplierCode) {
			jumbo.showMsg(i18("PLEASE_INPUT_ISSUPPLIERCODE"));
			return null;
		}
		var barCode = $j("#barCode").val();
		if (null == barCode || "" == barCode) {
			jumbo.showMsg(i18("PLEASE_INPUT_BARCODE"));
			return null;
		}
		var length = $j("#length").val();
		if (null == length || "" == length) {
			jumbo.showMsg(i18("PLEASE_INPUT_CUBIC"));
			return null;
		}
		var width = $j("#width").val();
		if (null == width || "" == width) {
			jumbo.showMsg(i18("PLEASE_INPUT_CUBIC"));
			return null;
		}
		var height = $j("#height").val();
		if (null == height || "" == height) {
			jumbo.showMsg(i18("PLEASE_INPUT_CUBIC"));
			return null;
		}
		var grossWeight = $j("#grossWeight").val();
		if (null == grossWeight || "" == grossWeight) {
			jumbo.showMsg(i18("PLEASE_INPUT_WEIGHT"));
			return null;
		}	
		data["product.isSupplierCode"] = isSupplierCode;
		data["product.barCode"] = barCode;
		data["product.length"] = length;
		data["product.width"] = width;
		data["product.height"] = height;
		data["product.grossWeight"] = grossWeight;
		return data;
	}
	
	
	//查询
	$j("#search").click(function(){
		if (checkData()) {
			var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/findthreedimensionaldata.json",data);
			if(result && result.msg == "success"){
				jumbo.showMsg(i18("UPDATE_SUCCESSFUL"));
			}else if(result && result.msg == "multiple"){
				var url = baseUrl + "/findtablebydata.json";
				$j("#tbl_query").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("formQuery")}).trigger("reloadGrid");
			}else if(result && result.msg == "none"){
				jumbo.showMsg(i18("NO_QUERY_DATA"));
				window.location.reload();
			}else{
				jumbo.showMsg(i18("DATA_UPDATE_EXCEPTION"));
			}
		}
		
	});
	
	// 更新三维商品数据
	$j("#updateData").click(function(){
		var ids = $j("#tbl_query").jqGrid('getGridParam','selarrrow');
		if (null == ids || "" == ids || undefined == ids) { 
			jumbo.showMsg(i18("PLEASE_SELECT_DATA"));
			return;
		}
		if (checkData()) {
			var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/refreshdata.json?ids=" + ids, data);
			if(result && result.msg == "success"){
				jumbo.showMsg(i18("UPDATE_SUCCESSFUL"));
				window.location.reload();
			}else{
				jumbo.showMsg(i18("DATA_UPDATE_EXCEPTION"));
			}
		}
		
	});
	$j("#barCode").keypress(function(evt){
		if (evt.keyCode === 13) {
			var barCode = $j("#barCode").val().trim();
			if(barCode == ""){
				loxia.tip(this,"请扫描商品条码！");
			}else{
				getInfoFromCubiScan();
			}
		}
	});
});
function getInfoFromCubiScan(){
	var info;
	try{
		info= document.threeDimensional.scan('10.7.13.240', 1050);
	}catch(e){
		console.log(e);
	}
	if (info) {
		var arr = info.split(",");
		if (arr.length == 4) {
			$j("#length").val(arr[0]);
			$j("#width").val(arr[1]);
			$j("#height").val(arr[2]);
			$j("#grossWeight").val(arr[3]);
		}else {
			jumbo.showMsg(i18("DEVICE_SCAN_EXCEPTION"));
		}
	}else {
		jumbo.showMsg(i18("DEVICE_SCAN_EXCEPTION"));
	}
	
}




 