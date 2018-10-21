$j.extend(loxia.regional['zh-CN'],{
	INPUT_WEIGHT : "请输入货物重量",
	NUMBER_RULE : "货物不是一个合法的数字或精度要求不符合要求",
	WEIGHT_LAGGER : "货物重量不得超过150KG",
	
	SKUNAME : "商品名称",
	JMCODE : "商品编码",
	KEYPROPERTIES : "扩展属性",
	BARCODE : "条形码",
	QUANTITY : "计划执行数量",
	COMPLETEQUANTITY : "已执行数量",
	ORDER_DETAIL : "作业单相关明细",
	
	NO_ORDER : "该包裹不存在或已经出库",
	INPUT_TRACKINGNO : "请输入快递单号",
	OUTBOUND : "出库成功",
	ORDER_CANCELED : "出库失败: 作业单含有未出库包裹",
	OUTBOUND_ERROR : "出库失败: 执行出库异常"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

$j(document).ready(function (){
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl_scan_sku_record_list").jqGrid({
		url:baseUrl+"/json/findScanRecords.json",
		postData:loxia._ajaxFormToObj("form"),
		datatype: "json",
		colNames: ["ID","扫码批次号","库位","skuId","商品条形码","SKU编码","商品名称","数量","记录时间", "操作人"],
		colModel: [
	            {name:"id", index: "id", hidden: true},		         
	            {name:"batchCode",index:"batchCode",width:120},
	            {name:"locationCode",index:"locationCode",width:120,resizable:true},
	            {name:"skuId", index: "skuId",resizable:true, hidden: true},
	            {name:"skuBarcode", index: "skuBarcode",width:120,resizable:true},
	            {name:"skuCode",index:"skuCode",width:120,resizable:true},
				{name:"skuName",index:"skuName",width:150,resizable:true},
				{name:"qty",index:"qty",width:80,resizable:true},
				{name:"createTime",index:"createTime",width:150,resizable:true},
				{name:"operatorName",index:"operatorName",width:100,resizable:true}
				],
		caption: "扫码记录列表",
		multiselect: false,
		height:"auto",
		pager:"#pager",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		sortname: 'ssr.create_time',
		sortorder: "desc",
		viewrecords: true,
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
		}
	});
	jumbo.bindTableExportBtn("tbl_scan_sku_record_list",{}, $j("body").attr("contextpath")+"/json/findScanRecords.json",loxia._ajaxFormToObj("form"));
	
	$j("#tbl_scan_sku_record_detail").jqGrid({
		//url:baseUrl+"/findInventoryLockSta.json",
		//postData:loxia._ajaxFormToObj("form_query"),
		datatype: "json",
		colNames: ["ID","扫码批次号","库位","skuId","商品条形码","SKU编码","商品名称","数量"],
		colModel: [
	            {name:"id", index: "id", hidden: true},		         
	            {name:"batchCode",index:"batchCode",width:120},
	            {name:"locationCode",index:"locationCode",width:120,resizable:true},
	            {name:"skuId", index: "skuId",resizable:true, hidden: true},
	            {name:"skuBarcode", index: "skuBarcode",width:120,resizable:true},
	            {name:"skuCode",index:"skuCode",width:120,resizable:true},
				{name:"skuName",index:"skuName",width:200,resizable:true},
				{name:"qty",index:"qty",width:80,resizable:true}],
		caption: "扫码记录列表",
		multiselect: false,
		height:"auto",
		//pager:"#pager",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		sortname: 'createTime',
		viewrecords: true,
			rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
		}
	});
	$j("#query").click(function(){
		var postData = loxia._ajaxFormToObj("form");  
		$j("#tbl_scan_sku_record_list").jqGrid('setGridParam',{
			url:$j("body").attr("contextpath")+"/json/findScanRecords.json",postData:postData}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl_scan_sku_record_list",{}, $j("body").attr("contextpath")+"/json/findScanRecords.json", postData);
	});
	$j("#scanOpt").click(function(){
		$j("#ssrList").addClass("hidden");
		$j("#ssrOperate").removeClass("hidden");
		$j("#locationCode").focus();
	});
	$j("#back").click(function(){
		$j("#ssrList").removeClass("hidden");
		$j("#ssrOperate").addClass("hidden");
		$j("#reset").click();
		$j("#query").click();
	});
	$j("#reset").click(function(){
		$j("#form input").val("");
	});
	$j("#locationCode").focusin(function(){
		$j("#skuBarcode").val("");
		$j("#locationCode").val("");
		$j("#colorTipFail").addClass("hidden");
		$j("#colorTipSuccess").addClass("hidden");
	});
	$j("#locationCode").keydown(function(evt) {
		if (evt.keyCode === 13) {
			evt.preventDefault();
			var lCode = $j.trim($j("#locationCode").val());
			if (lCode) {
				$j("#skuBarcode").focus();
			}
		}
	});
	$j("#skuBarcode").focusin(function(){
		$j("#colorTipFail").addClass("hidden");
		$j("#colorTipSuccess").addClass("hidden");
	});
	$j("#skuBarcode").keydown(function(evt) {
		if (evt.keyCode === 13) {
			evt.preventDefault();
			var skuBarcode = $j.trim($j("#skuBarcode").val());
			var locationCode = $j.trim($j("#locationCode").val());
			if (skuBarcode) {
				var id = new Date().getTime();
				var inQty = 1;
				var batchCode = $j("#batchCode").val();
				if("" == locationCode || null == locationCode){
					jumbo.showMsg("请扫库位！");
					$j("#skuBarcode").val("");
					return;
				}
				if("" == batchCode || null == batchCode){
					var rs = loxia.syncXhrPost($j("body").attr("contextpath")+ "/getScanRecordBatchCode.json");
					if(rs && "success" == rs["result"]){
						batchCode = rs["batchCode"];
						$j("#batchCode").val(batchCode);
					}else{
						jumbo.showMsg("扫描批次号生成错误！");
						return;
					}
				}
				if(isSkuExist(skuBarcode, locationCode)){
					$j("#skuBarcode").val("");
					$j("#colorTipFail").addClass("hidden");
					$j("#colorTipSuccess").removeClass("hidden");
					addSkuQty(skuBarcode, locationCode, "1");
					return;
				}
				var sl = loxia.syncXhrPost($j("body").attr("contextpath")+ "/getSkuByBarCodeAndReturnInfo.json", {"barCode" : skuBarcode});
				if (sl && sl["skuId"]) {
					var obj = {};
					obj = {	
							id:id,
							batchCode:batchCode,
							skuId:sl["skuId"],
							skuCode:sl["skuCode"],
							skuBarcode:sl["barCode"],
							skuName:sl["skuName"],
							qty:inQty,
							locationCode:locationCode
					};
					$j("#skuBarcode").val("");
					$j("#colorTipFail").addClass("hidden");
					$j("#colorTipSuccess").removeClass("hidden");
					addSku(obj, obj.skuId, locationCode);
				} else {
					//loxia.tip(this,"指定商品条码的商品不存在！");
					$j("#colorTipFail").removeClass("hidden");
					$j("#colorTipSuccess").addClass("hidden");
				}
			}
		}
	});
	$j("#confirm").click(function(){
		var data = $j("#tbl_scan_sku_record_detail").jqGrid('getRowData');
		if(0 == data.length){
			jumbo.showMsg("无扫码记录，无法执行！");
			$j("#locationCode").focus();
			return;
		}
		var postData={};
		 $j.each(data,function(i, item) {
			 var batchCode = item.batchCode;
			 var locationCode = item.locationCode;
			 var skuId = item.skuId;
			 var qty = item.qty;
			 if("" == locationCode || "" == skuId  || "" == qty || "" == batchCode){
				 jumbo.showMsg("扫描数据异常！");
				 return;
			 }
			 postData["ssrList[" + i + "].batchCode"] = batchCode;
			 postData["ssrList[" + i + "].locationCode"] = locationCode;
			 postData["ssrList[" + i + "].skuId"] = skuId;
			 postData["ssrList[" + i + "].qty"] = qty;
		 });
		 if(confirm("扫描确认？")){
			 var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/saveScanRecords.json",postData);
			 if(rs.result=='success'){
					jumbo.showMsg("本次扫码记录保存成功！");
					$j("#tbl_scan_sku_record_detail").jqGrid("clearGridData");
					$j("#batchCode").val("");
					$j("#locationCode").focus();
			 }else {
				if(rs){
					var msg = rs.msg;
					jumbo.showMsg("确认失败："+msg);
				}
			} 
		 }
	});
	$j("#clear").click(function(){
		$j("#tbl_scan_sku_record_detail").jqGrid("clearGridData");
		$j("#locationCode").val("");
		$j("#batchCode").val("");
		$j("#colorTipFail").addClass("hidden");
		$j("#colorTipSuccess").addClass("hidden");
		$j("#locationCode").focus();
	});
});
function addSku(obj, skuId, locationCode){
	var data = $j("#tbl_scan_sku_record_detail").jqGrid('getRowData');
	var isExist = false;
	var target = null;
	$j.each(data,function(i, item){
		var sku = item.skuId;
		var location = item.locationCode;
		if(skuId == sku && location == locationCode){
			isExist = true;
			target = item;
			return;
		}
	});
	if(isExist){
		var d = $j("#tbl_scan_sku_record_detail").jqGrid('getRowData',target.id);
		var num = parseInt((null == obj.qty || "" == obj.qty) ?  parseInt("0") : parseInt(obj.qty));
		var qty = parseInt((null == d.qty || "" == d.qty) ?  parseInt("0") : parseInt(d.qty));
		d.qty = qty + num;
		$j("#tbl_scan_sku_record_detail").jqGrid('setRowData',d.id,d);
	}else{
		$j("#tbl_scan_sku_record_detail").jqGrid('addRowData',obj.id,obj);
	}
}
function isSkuExist(skuBarcode, locationCode){
	var data = $j("#tbl_scan_sku_record_detail").jqGrid('getRowData');
	var isExist = false;
	$j.each(data,function(i, item){
		var sku = item.skuBarcode;
		var location = item.locationCode;
		if(sku == skuBarcode && location == locationCode){
			isExist = true;
			return;
		}
	});
	return isExist;
}
function addSkuQty(skuBarcode, locationCode, nums){
	var data = $j("#tbl_scan_sku_record_detail").jqGrid('getRowData');
	var isExist = false;
	var target = null;
	$j.each(data,function(i, item){
		var sku = item.skuBarcode;
		var location = item.locationCode;
		if(sku == skuBarcode && location == locationCode){
			isExist = true;
			target = item;
			return;
		}
	});
	if(isExist){
		var d = $j("#tbl_scan_sku_record_detail").jqGrid('getRowData',target.id);
		var num = parseInt((null == nums || "" == nums) ?  parseInt("0") : parseInt(nums));
		var qty = parseInt((null == d.qty || "" == d.qty) ?  parseInt("0") : parseInt(d.qty));
		d.qty = qty + num;
		$j("#tbl_scan_sku_record_detail").jqGrid('setRowData',d.id,d);
	}
}
