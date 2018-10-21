$j.extend(loxia.regional['zh-CN'],{
	SKUCODE:"商品编码",
	SKUNAME:"商品名称",
	BARCODE:"商品条码",
	SUPPLIERCODE:"供应商编码",
	QTY:"件数",
	KINDS:"种类数",
	OPERATOR:"操作",
	HAVESPECIALSKU:"现创建特殊处理商品",
	DELETE:"删除",
	INVALID_EMPTY_DATA:"内容不可为空",
	CREATETIME:"创建时间"
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
	postData["ssId"]=id;
	if(confirm("确定要删除?")){
	//删除秒杀商品,更新秒杀订单
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/deletespecialskubyid.json",postData);
		if(result&&result.rs=="success"){
			//更新秒杀商品列表
			$j("#have-special-sku").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/selectallspecialsku.json")}).trigger("reloadGrid");
		}else{
			jumbo.showMsg("删除特殊处理商品失败！");
		}
	}
}
$j(document).ready(function(){
	$j("#have-special-sku").jqGrid({
		url:$j("body").attr("contextpath") + "/selectallspecialsku.json",
		datatype: "json",
		colNames: ["ID",i18("SKUCODE"),i18("SKUNAME"),i18("BARCODE"),i18("SUPPLIERCODE"),i18("CREATETIME")],
		colModel: [
		           {name: "id", index: "id", hidden: true},
		           {name: "code",index:"code",width:150,sortable:false},
		           {name: "name",index:"name",width:200,sortable:false},
		           {name: "barCode",index:"barCode",width:150,sortable:false},
		           {name: "supplierCode",index:"supplierCode",width:150,sortable:false},
		           {name: "createTime",index:"createTime",width:200,sortable:false}
		          ],
		caption: i18("HAVESPECIALSKU"),//详细信息
		sortname:"id",
		height:"auto",
		multiselect: true,
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		height:jumbo.getHeight(),
		pager:"#pager",
		sortorder: "desc",
		viewrecords: true,
	   	rownumbers:true,
	   	width:1000,
		jsonReader: { repeatitems : false, id: "0" },
	});
	$j("#add").click(function(){
		if($j("#supplierCode").val()==""){
			loxia.tip($j("#supplierCode"),"请填写供应商编码");
			return;
		}
		var postData = loxia._ajaxFormToObj("addform");
		postData['sord']="desc";
		postData['rows']=20;
		postData['page']=1;
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/addspecialskubysuppliercode.json",postData);
		if(result&&result.rs=="error"){
			loxia.tip($j("#supplierCode"),result.msg);
		}else{
			if(result.msg){
				jumbo.showMsg(result.msg);
			}else{
				$j("#have-special-sku").jqGrid('setGridParam',{
					url:loxia.getTimeUrl($j("body").attr("contextpath") + "/selectallspecialsku.json")
				}).trigger("reloadGrid");
				jumbo.showMsg("添加成功!");
			}
		}
	});
	
	$j("#sn_import").click(function(){
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg("请选择要导入的文件！");
			return null;
		}
		$j("#importForm").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importSkuSupplierCode.do"));
		loxia.submitForm("importForm");
		$j("#have-special-sku").jqGrid('setGridParam',{
			url:loxia.getTimeUrl($j("body").attr("contextpath") + "/selectallspecialsku.json")
		}).trigger("reloadGrid");
	});
	
	//删除
	$j("#batchRemove").click(function(){
		var ids = $j("#have-special-sku").jqGrid('getGridParam','selarrrow');
		var postData = {};		
		for(var i in ids){
					postData['idList[' + i + ']']=ids[i];
				}
				var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/deletespecialskubyid.json",postData);
				if (result.rs=="success") {
					jumbo.showMsg("删除成功");
					var supplierCode=$j("#supplierCode1").val();
					$j("#have-special-sku").jqGrid('setGridParam',{
						url:loxia.getTimeUrl($j("body").attr("contextpath") + "/selectallspecialsku.json?supplierCode="+supplierCode),page:1
					}).trigger("reloadGrid");					
					}else{
						jumbo.showMsg("删除失败");
					}
		});
	
	//查询
	$j("#search").click(function(){
		if($j("#supplierCode1").val()==""){
			loxia.tip($j("#supplierCode1"),"请填写供应商编码");
			return;
		}
		var supplierCode=$j("#supplierCode1").val();
		$j("#have-special-sku").jqGrid('setGridParam',{
			url:loxia.getTimeUrl($j("body").attr("contextpath") + "/selectallspecialsku.json?supplierCode="+supplierCode),page:1
		}).trigger("reloadGrid");
	});
	
	//重置
	$j("#reset").click(function(){
		$j("#supplierCode1").val("");
	});
	
});