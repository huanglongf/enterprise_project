$j.extend(loxia.regional['zh-CN'],{
	SKUNAME:"商品名称",
	BARCODE:"商品条码",
	SUPPLIERCODE:"货号",
	QTY:"件数",
	KINDS:"种类数",
	OPERATOR:"操作",
	NEWSEDKILLSKU:"欲创建秒杀商品",
	HAVESEDKILLSKU:"现秒杀商品",
	TITLENAME:"秒杀商品【商品名称|条码|件数/商品名称|条码|件数....】",
	DELETE:"删除"
});
function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
function cancelDetial(tag,event){
	$j(tag).parents("tr").remove();
}
function cancelDetial1(tag,event){
	var id = $j(tag).parents("tr").attr("id");
	var skus = $j(tag).parents("tr").children("td:eq(1)").attr("title");
	var postData = {};
	postData["secKillId"]=id;
	postData["skus"] = skus;
	//删除秒杀商品,更新秒杀订单
	var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/deleteseckillskubyid.json",postData);
	if(result&&result.rs=="success"){
		//更新秒杀商品列表
		$j("#have-sec-kill-sku").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/selectallseckillsku.json")}).trigger("reloadGrid");
	}else{
		jumbo.showMsg("删除秒杀商品失败！");
	}
}
$j(document).ready(function(){
	$j("#new-sec-kill-sku").jqGrid({
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
		height:"auto",
   		sortname: 'id',
     	multiselect: false,
		sortorder: "desc", 
		jsonReader: { repeatitems : false, id: "0" }
	});
	$j("#have-sec-kill-sku").jqGrid({
		url:$j("body").attr("contextpath") + "/selectallseckillsku.json",
		datatype: "json",
		colNames: ["ID","SKUS",i18("TITLENAME"),i18("KINDS"),i18("OPERATOR")],
		colModel: [
		           {name: "id", index: "id", hidden: true},
		           {name: "skus", index:"skus", hidden:true},
				   {name: "skusString", index: "skusString", width: 500, resizable: true},
		           {name: "kind", index: "kind",width: 120, resizable: true},
		           {name: "operator", width: 60, resizable: true,formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("DELETE"), onclick:"cancelDetial1(this,event);"}}}],
		caption: i18("HAVESEDKILLSKU"),//详细信息
		rowNum: -1,
		height:"auto",
   		sortname: 'id',
     	multiselect: false,
		sortorder: "desc", 
		jsonReader: { repeatitems : false, id: "0" }
	});
	$j("#add").click(function(){
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
		//根据输入的条形码和数量判断商品是否存在或者保存商品信息并更新欲创建秒杀商品列表
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/getskuinfobybarcode.json",postData);
		if(result&&result.rs=="error"){
			loxia.tip($j("#barcode"),result.errorMsg);
		}else{
			var b = true;
			$j("#new-sec-kill-sku tr[id]").each(function(i,tag){
				if($j(tag).attr("id")==result.id){
					b=false;
				}
			});
			if(b){
				$j("#new-sec-kill-sku").jqGrid('addRowData',result.id,result);
				$j("#addform input").val("");
			}else{
				loxia.tip($j("#barcode"),"该商品已经添加！");
			}
		}
	});
	$j("#save").click(function(){
		var j = 0;
		var postData = {};
		$j("#new-sec-kill-sku tr[id]").each(function(i,tag){
			postData["idList["+j+"]"]=$j(tag).attr("id");
			postData["titleList["+j+"]"]=$j(tag).attr("id")+";"+$j(tag).children("td:eq(4)").attr("title")+",";
			j++;
		});
		if(j==0){
			jumbo.showMsg("请添加欲秒杀商品之后再保存！");
			return;
		}else{
			postData["skuQty"]=j;
			postData["expireDate"]=$j("#expireDate").val();
			var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/addseckillsku.json",postData);
			if(result&&result.rs=="success"){
				$j("#have-sec-kill-sku").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/selectallseckillsku.json")}).trigger("reloadGrid");
				$j("#new-sec-kill-sku tr").remove();
				$j("input").val("");
			}else{
				jumbo.showMsg(result.errorMsg);
			}
		}
	});
});