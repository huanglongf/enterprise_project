$j.extend(loxia.regional['zh-CN'],{
	DATA_ERROR : "数据错误",
	QUERY_DATA_ERROR:"获取数据异常",
	BARCODE_NOT_NULL:"条形码不能为空",
	BARCODE_REPEAT:"条码 {0} 重复",
	SET_FINISH:"修改成功",
	SAVE_ERROR_REPEAT:"保存失败，数据操作异常，可能存在重复条码",
	SKU_BARCODE_NOT_NULL:"主条码不能为空",
	SAVE_ERROR:"保存失败，数据操作异常",
	SKU_QUERY_LIST:"商品查询列表",
	BARCODE_PRING:"条码打印",
	BARCODE_PRING:"条码打印",
	SKU_MAINTAIN:"维护",
	
	
	SKU_CODE:"SKU编码",
	BAR_CODE:"条形码",
	JM_CODE:"商品编码",
	KEY_PROPERTIES:"扩展属性",
	SKU_NAME:"商品名称",
	SUPPLIER_CODE:"货号",
	PRODUCT_SIZE:"商品大小",
	IS_CONSUMABLE:"是否耗材",
	
	BRAND_NAME:"品牌名称",
	PRINT:"打印",
	OPERATE:"操作",
	CORRECT_FILE_PLEASE:"请选择正确的Excel导入文件"
});
var baseUrl;

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
function initSkuBarCode(isShow,data){
	if(isShow){
		return '<tr class="odd"><td class="col-0" style="text-align: center;">'+data.barcode+'</td></tr>';
	}
	return '<tr class="odd"><td class="col-0" style="text-align: center;"><input type="checkbox"></td><td class="col-1">'+data.barcode+'<input type="hidden" name="barcode" value="'+data.barcode+'" /></td></tr>';
}

function clear(){
	$j("#file").val("");
}

function initDetailInfo(isShow,divid,id){
	$j("#detialSkuId").val(id);
	var postData = {};
	postData["skuId"] = id;
	var data=loxia.syncXhr(baseUrl + "/findSkuAndSkuBarcodeById.json",postData);
	if(data){
		if(data.sku){
			$j("#skuCode").text(data.sku.code);
			$j("#skuName").text(data.sku.name);
			$j("#spanBarcode").text(data.sku.barCode);
			$j("#skuJmCode").text(data.sku.jmCode);
			$j("#skuKeyProperties").text(data.sku.keyProperties);
			$j("#skuSupplierCode").text(data.sku.supplierCode);
			$j("#skuBrandName").text(data.sku.brandName);
			$j(divid+" table tbody:eq(0)>tr").remove();
			for(var i in data.barcodelist){
				$j(divid+" table tbody:eq(0)").append( initSkuBarCode(isShow,data.barcodelist[i]));
			}
			$j(divid + " button[action='add']").trigger('click');
			loxia.initContext($j(divid));
			loxia.unlockPage();
			return true;
		} else {
			$j("#detialSkuId").val("");
			jumbo.showMsg(i18("QUERY_DATA_ERROR"));
		}
	}else{
		$j("#detialSkuId").val("");
		jumbo.showMsg( i18("QUERY_DATA_ERROR"));
	}
	return false;
}

function modify(tag){
	var id =$j(tag).parents("tr").attr("id");
	loxia.lockPage();
	if(!initDetailInfo(false,"#edittable",id)){
		return;
	}
	loxia.unlockPage();
	$j("#divSave").addClass("hidden");
	$j("#divModify").removeClass("hidden");
	$j("#divDetial").removeClass("hidden");
	$j("#divList").addClass("hidden");
	
	$j("#save").removeClass("hidden");
	//$j("#modeify").removeClass("hidden");
	$j("#edittable").removeClass("hidden");
	$j("#showDiv").addClass("hidden");
}

function showDetail(tag){
	loxia.lockPage();
	var id =$j(tag).parents("tr").attr("id");
	if(!initDetailInfo(true,"#showDiv",id)){
		return;
	}
	loxia.unlockPage();
	
	$j("#divSave").addClass("hidden");
	$j("#divModify").removeClass("hidden");
	$j("#divDetial").removeClass("hidden");
	$j("#divList").addClass("hidden");

	$j("#save").addClass("hidden");
	//$j("#modeify").addClass("hidden");
	$j("#edittable").addClass("hidden");
	$j("#showDiv").removeClass("hidden");
}

function validateInput(value,obj){
	if(value == ""){
		return i18("请输入打印数量");
	}
	//if(!/^[0-9]*\.{0,1}[0-9]*$/.test(value)){
	//value = parseInt(value);
	//alert(/^\d*$/.test(value));
	if(!(/^\d*$/.test(value))){
		return i18("输入的不是合法数字");
	}
	if (value < 1){return i18("输入合法的正数");}
	return loxia.SUCCESS;
}

function previewBarcode(tag){
	var id = $j(tag).parents("tr").attr("id");
	printpreview(id);
}

function printpreview(skuId){
	if(!skuId) {
		jumbo.showMsg(i18("DATA_ERROR"));
		return;
	}
	var url = $j("body").attr("contextpath") + "/printskubarcode.json?skuId=" + skuId;
	printBZ(loxia.encodeUrl(url),true);
}
$j(document).ready(function (){
	baseUrl = $j("body").attr("contextpath");
	$j("#detail_tabs").tabs();
	
	var rsBrand = loxia.syncXhr($j("body").attr("contextpath") + "/findAllBrand.json",{});
	$j("#brandSelect").append("<option></option>");
	for(var i = 0 ; i < rsBrand.brandlist.length ; i++){
		$j("#brandSelect").append("<option>"+rsBrand.brandlist[i].name+"</option>");
	}
    $j("#brandSelect").flexselect();
    $j("#brandSelect").val("");
    
	$j("#product_print_dialog").dialog({title: "商品条码信息打印", modal:true, autoOpen: false, width: 300, height: 160});
	
	$j("#confirmPrint").click(function(){
		var url = baseUrl + "/printskubarcode.json?skuId=" + $j("#detialSkuId").val() + "&count=" + $j("#inputCount").val();
		$j("#inputCount").val("");
		$j("#product_print_dialog").dialog("close");
		printBZ(loxia.encodeUrl(url),false);
		return ;
	});
	$j("#cancelPrint").click(function(){
		$j("#product_print_dialog").dialog("close");
		$j("#inputCount").val("");
	});

	$j("#printBarcode").click(function(){
		printpreview($j("#detialSkuId").val());
	});
	
	$j("#back").click(function(){
		$j("#divDetial").addClass("hidden");
		$j("#divList").removeClass("hidden");
		$j("#skuKeyProperties").val("");
	});

	$j("#modeify").click(function(){
		$j("#divModify").addClass("hidden");
		$j("#divSave").removeClass("hidden");
		$j("#barcode").val($j("#spanBarcode").html());
	});

	$j("#btnCancel").click(function(){
		$j("#divSave").addClass("hidden");
		$j("#divModify").removeClass("hidden");
	});
	$j("#btnSearchBrandName").click(function(){
		$j("#BrandNameQueryDialog").dialog("open");
	});
	$j("#save").click(function(){
		loxia.lockPage();
		var postData = {};
		postData["skuId"]= $j.trim($j("#detialSkuId").val());
		var inputs = $j("#edittable table tbody:eq(0)").find("input[name=barcode]");
		for(var i=0; i<inputs.length;i++){
			var val = $j.trim($j(inputs[i]).val());
			if(val== "" ){
				loxia.unlockPage();
				jumbo.showMsg(i18("BARCODE_NOT_NULL"));
				return;
			} else {
				for(var n = 0; n < i; n++){
					if(postData["skuBarcode[" + n + "].barcode"] == val){
						loxia.unlockPage();
						jumbo.showMsg(i18("BARCODE_REPEAT",[val]));
						return;
					}
				}
				postData["skuBarcode[" + i + "].barcode"]=val;
			}
		}
		var data = loxia.syncXhr(baseUrl + "/updateBarCode.json",postData);
		if(data){
			if(data.result=="success"){
				var tds = $j("#edittable table tbody:eq(0)").find("td");
				for(var i=0; i<tds.length;i++){
					var input = $j(tds[i]).find("input[name=barcode]");
					if($j(input).attr("type") == "text" ){
						var html = $j(input).val()+"<input type=\"hidden\" name=\"barcode\" value=\""+$j(input).val()+"\" />";
						$j(tds[i]).html(html);
					}
				}
				loxia.unlockPage();
				jumbo.showMsg(i18("SET_FINISH"));
			}else{
				loxia.unlockPage();
				jumbo.showMsg(i18("SAVE_ERROR_REPEAT"));
			}
		} else {
			loxia.unlockPage();
			jumbo.showMsg(i18("SAVE_ERROR_REPEAT"));
		}
	});

	$j("#btnSaveMainBarcode").click(function(){
		loxia.lockPage();
		var val = $j.trim($j("#barcode").val());
		if(val == ""){
			jumbo.showMsg(i18("SKU_BARCODE_NOT_NULL"));
			loxia.unlockPage();
			return;
		}
		var postData = {};
		postData["skuId"] = $j.trim($j("#detialSkuId").val());
		postData["barCode"] = val;
		var data = loxia.syncXhr(baseUrl + "/updateSkuBarCode.json",postData);
		if(data){
			if(data.result=="success"){
				$j("#divSave").addClass("hidden");
				$j("#divModify").removeClass("hidden");
				$j("#spanBarcode").html($j.trim($j("#barcode").val()));
				loxia.unlockPage();
				jumbo.showMsg(i18("SET_FINISH"));
			}else{
				loxia.unlockPage();
				jumbo.showMsg(data.message);
			}
		} else {
			loxia.unlockPage();
			jumbo.showMsg(i18("SAVE_ERROR"));
		}
	});

/* 这里编写必要的页面演示逻辑*/
	var StoreModeValue = loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whInboundStoreMode"});
	
	$j("#tbl-commodity-query").jqGrid({
		datatype: "local",
		postData:loxia._ajaxFormToObj("form_query"),
		//colNames: ["ID","SKU编码","条形码","商品编码","扩展属性","是否耗材","商品名称","货号","品牌名称","打印","操作"],
		colNames: ["ID",i18("SKU_CODE"),i18("BAR_CODE"),i18("JM_CODE"),"存放模式",i18("KEY_PROPERTIES"),i18("IS_CONSUMABLE"),i18("SKU_NAME"),i18("SUPPLIER_CODE"),i18("PRODUCT_SIZE"),i18("BRAND_NAME"),i18("PRINT"),i18("OPERATE")],
		colModel: [
		           {name: "id", index: "id", hidden: true},		         
		           {name: "code", index: "code", width: 150,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, resizable: true},
		           {name: "barCode", index: "barCode", width: 150, resizable: true},
		           {name: "jmCode", index: "jmCode", width: 150, resizable: true},
		           {name: "storeModeValue", index: "storeModeValue", width: 80, resizable: true,formatter:'select',editoptions:StoreModeValue},
		           {name: "keyProperties", index: "keyProperties", width: 100, resizable: true},
		           {name: "isConsumable", index: "isConsumable", width: 100, resizable: true},
		           {name: "name", index: "name", width: 200, resizable: true},
		           {name: "supplierCode", index: "supplierCode", width: 100, resizable: true},
		           {name: "productSize", index: "productSize", width: 80, resizable: true},
		           {name: "brandName", index: "brandName", width: 80, resizable: true},
		           {name: "btnForPrint", width: 100, resizable: true},
		           {name: "btnForModify", width: 80, resizable: true}],
		caption: i18("SKU_QUERY_LIST"),
	   	pager:"#pager",
	   	sortname: 'code',
	   	rownumbers:true,
	   	multiselect: false,
	   	viewrecords: true,
	   	rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		height:jumbo.getHeight(),
		sortorder: "desc",
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var btn1 = '<div><button type="button" style="width:100px;" class="confirm" name="btnExecute" loxiaType="button" onclick="previewBarcode(this);">'+i18("BARCODE_PRING")+'</button></div>';
			var btn2 = '<div><button type="button" style="width:75px;" name="btnCancel" loxiaType="button" onclick="modify(this);">'+i18("SKU_MAINTAIN")+'</button></div>';
			
			var ids = $j("#tbl-commodity-query").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				$j("#tbl-commodity-query").jqGrid('setRowData',ids[i],{"btnForPrint":btn1,"btnForModify":btn2});
			}
			loxia.initContext($j(this));
		}
	});
	$j("#search").click(function(){
		var pinpai = $j("#brandSelect").val();
		if(pinpai == ""){
			alert("请先选择品牌");
			return;
		}
		var url =baseUrl+"/findSku.json";
		$j("#tbl-commodity-query").jqGrid('setGridParam',{url:url,postData:loxia._ajaxFormToObj("form_query"),datatype: "json",}).trigger("reloadGrid");
		bindTable();
	});
	
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#brandSelect option:first").attr("selected",true);
	});
	
	//保质期商品导入
	$j("#import").click(function(){
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));
			return;
		}
//		loxia.lockPage();
			$j("#importForm").attr("action",
					loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importSkuShelfLife.do")
			);
		loxia.submitForm("importForm");
	});
});
function bindTable(){
	jumbo.bindTableExportBtn("tbl-commodity-query",{},baseUrl+"/findSku.json",loxia._ajaxFormToObj("form_query"),isSelBrand);
}

function isSelBrand(){
	var brandname = $j("#brandSelect").val(); 
	if(brandname == ''){
		jumbo.showMsg("必须选择品牌");
		return false;
	}else{
		return true;
	}
}


