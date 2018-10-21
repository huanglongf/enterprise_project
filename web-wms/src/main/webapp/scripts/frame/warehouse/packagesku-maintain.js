$j.extend(loxia.regional['zh-CN'],{
	SKUNAME:"商品名称",
	BARCODE:"商品条码",
	SUPPLIERCODE:"货号",
	QTY:"件数",
	KINDS:"种类数",
	OPERATOR:"操作",
	NEWSEDKILLSKU:"欲创建套装组合商品",
	HAVESEDKILLSKU:"现创建套装组合商品",
	TITLENAME:"秒杀商品【商品名称|条码|件数/商品名称|条码|件数....】",
	DELETE:"删除",
	SKUQTY:"商品总件数",
	SKU1BARCODE:"SKU1条码",
	SKU1PROPERTITY:"SKU1货号关键属性",
	SKU1QTY:"SKU1数量",
	SKU2BARCODE:"SKU2条码",
	SKU2PROPERTITY:"SKU2货号关键属性",
	SKU2QTY:"SKU2数量",
	SKU3BARCODE:"SKU3条码",
	SKU3PROPERTITY:"SKU3货号关键属性",
	SKU3QTY:"SKU3数量",
	INVALID_EMPTY_DATA:"内容不可为空"
});
function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
function cancelDetial(tag,event){
	$j(tag).parents("tr").remove();
	var j=0,q=0;
	$j("#new-package-sku tr[id]").each(function(i,tag1){
		q+=parseInt($j(tag1).children("td:eq(4)").attr("title"));
		j++;
	});
	if(q!=0&&j!=0){
		q+=1;
		j+=1;
		$j("#skuQty").val(j);
		$j("#staTotalSkuQty").val(q);
	}else{
		$j("#skuQty").val("");
		$j("#staTotalSkuQty").val("");
	}
}
function cancelDetial1(tag,event){
	var id = $j(tag).parents("tr").attr("id");
	var postData = {};
	postData["packageSkuId"]=id;
	//删除秒杀商品,更新秒杀订单
	var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/deletepackageskubyid.json",postData);
	if(result&&result.rs=="success"){
		//更新秒杀商品列表
		$j("#have-package-sku").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/selectallpackagesku.json")}).trigger("reloadGrid");
	}else{
		jumbo.showMsg("删除套装组合商品失败！");
	}
}
$j(document).ready(function(){
	$j("#new-package-sku").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("SKUNAME"),i18("BARCODE"),i18("SUPPLIERCODE"),i18("QTY"),i18("OPERATOR")],
		colModel: [
		           {name: "id", index: "id", hidden: true},
				   {name: "name", index: "name", width: 120, resizable: true},
		           {name: "barCode", index: "barCode",width: 120, resizable: true},
		           {name: "supplierCode", index: "supplierCode", width: 100, resizable: true},
		           {name: "version", index: "version", width: 100, resizable: true},
		           {name: "operator", width: 60, resizable: true,formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("DELETE"), onclick:"cancelDetial(this,event);"}}}],
		caption: i18("NEWSEDKILLSKU"),//详细信息
		rowNum:-1,
		height:"auto",
   		sortname: 'id',
     	multiselect: false,
		sortorder: "desc", 
		jsonReader: { repeatitems : false, id: "0" }
	});
	$j("#have-package-sku").jqGrid({
		url:$j("body").attr("contextpath") + "/selectallpackagesku.json",
		datatype: "json",
		colNames: ["ID",i18("SKUQTY"),i18("SKU1BARCODE"),i18("SKU1PROPERTITY"),i18("SKU1QTY"),i18("SKU2BARCODE"),i18("SKU2PROPERTITY"),i18("SKU2QTY"),i18("SKU3BARCODE"),i18("SKU3PROPERTITY"),i18("SKU3QTY"),i18("OPERATOR")],
		colModel: [
		           {name: "id", index: "id", hidden: true},
		           {name: "staTotalSkuQty", index:"staTotalSkuQty",width:80},
		           {name: "skus1BarCode",index:"skus1BarCode",width:100},
		           {name: "skus1SupportCode",index:"skus1SupportCode",width:120},
		           {name: "skus1Qty",index:"skus1Qty",width:60},
		           {name: "skus2BarCode",index:"skus2BarCode",width:100},
		           {name: "skus2SupportCode",index:"skus2SupportCode",width:120},
		           {name: "skus2Qty",index:"skus2Qty",width:60},
		           {name: "skus3BarCode",index:"skus3BarCode",width:100},
		           {name: "skus3SupportCode",index:"skus3SupportCode",width:120},
		           {name: "skus3Qty",index:"skus3Qty",width:60},
		           {name: "operator", width: 60, resizable: true,formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("DELETE"), onclick:"cancelDetial1(this,event);"}}}],
		caption: i18("HAVESEDKILLSKU"),//详细信息
		height:"auto",
		rowNum:-1,
   		sortname: 'id',
   		rownumbers:true,
     	multiselect: false,
		sortorder: "desc", 
		jsonReader: { repeatitems : false, id: "0" }
	});
	$j("#add").click(function(){
		var items = $j("#new-package-sku tr[id]").length;
		if(items>=3){
			jumbo.showMsg("最多只能添加三个商品！");
			return;
		}
		if($j("#barcode").val()==""){
			loxia.tip($j("#barcode"),"请填写商品条码");
			return;
		}
		if($j("#skuqty").val()==""){
			loxia.tip($j("#skuqty"),"请填写商品件数");
			return;
		}else{
			if(!loxia.byId("skuqty").check()){
				loxia.tip($j("#skuqty"),"请填写正确数量！");
				return;
			}
			if($j("#skuqty").val()<0){
				loxia.tip($j("#skuqty"),"请填写正确数量！");
				return;
			}
		}
		var postData = loxia._ajaxFormToObj("addform");
		postData["isPackage"]="true";
		//根据输入的条形码和数量判断商品是否存在或者保存商品信息并更新欲创建秒杀商品列表
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/getskuinfobybarcode.json",postData);
		if(result&&result.rs=="error"){
			loxia.tip($j("#barcode"),result.errorMsg);
		}else{
			var b = true;
			$j("#new-package-sku tr[id]").each(function(i,tag){
				if($j(tag).attr("id")==result.id){
					b=false;
				}
			});
			if(b){
				$j("#new-package-sku").jqGrid('addRowData',result.id,result);
				$j("#addform input").val("");
				var j = 0,q=0;
				$j("#new-package-sku tr[id]").each(function(i,tag){
					q+=parseInt($j(tag).children("td:eq(4)").attr("title"));
					j++;
				});
				q+=1;
				j+=1;
				$j("#skuQty").val(j);
				$j("#staTotalSkuQty").val(q);
			}else{
				loxia.tip($j("#barcode"),"该商品已经添加！");
			}
		}
	});
	$j("#save").click(function(){
		var j = 0,q=0;
		var postData = {};
		$j("#new-package-sku tr[id]").each(function(i,tag){
			postData["idList["+j+"]"]=$j(tag).attr("id");
			postData["titleList["+j+"]"]=$j(tag).attr("id")+";"+$j(tag).children("td:eq(4)").attr("title");
			q+=parseInt($j(tag).children("td:eq(4)").attr("title"));
			j++;
		});
		if(j==0){
			jumbo.showMsg("请添加套装组合商品之后再保存！");
			return;
		}else{
			postData["expireDate"]=$j("#expireDate").val();
			postData["staTotalSkuQty"]=$j("#staTotalSkuQty").val();
			postData["skuQty"]=$j("#skuQty").val();
			var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/addpackagesku.json",postData);
			if(result&&result.rs=="success"){
				$j("#have-package-sku").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/selectallpackagesku.json")}).trigger("reloadGrid");
				$j("#new-package-sku tr").remove();
				$j("input").val("");
			}else{
				jumbo.showMsg(result.errorMsg);
			}
		}
	});
});