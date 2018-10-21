$j(document).ready(function (){
	
	$j("#tbl-list").jqGrid({
		datatype: "json",
		colNames: ["ID","商品编码","UPC","HScode","商品中文名","商品描述","款色码","是否打折商品","净重(KG)","标准价格","原产地","品牌","申报单位","备注","状态"],
		colModel: [
				{name:"skuOriginDeclarationId", index:"skuOriginDeclarationId", hidden: true},
				{name:"skuCode", index:"skuCode", width:100, resizable:true},
				{name:"upc", index:"upc", width:100, resizable:true},
				{name:"hsCode", index:"hsCode", width:100, resizable:true},
				{name:"skuName", index:"skuName", width:100, resizable:true},
				{name:"skuDescribe", index:"skuDescribe", width:100, resizable:true},
				{name:"ksm", index:"ksm", width:200, resizable:true},
				{name:"isDiscountName", index:"isDiscountName", width:100, resizable:true},
				{name:"netWt", index:"netWt", width:100, resizable:true},
				{name:"declPrice", index:"declPrice", width:100, resizable:true},
				{name:"orogin", index:"orogin", width:100, resizable:true},
				{name:"owner", index:"owner", width:100, resizable:true},
				{name:"gUnit", index:"gUnit", width:100, resizable:true},
				{name:"memo", index:"memo", width:100, resizable:true},
				{name:"statusName", index:"statusName", width:100, resizable:true}
					],
		caption:"商品列表",
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#pager1",	   
		sortorder: "sort",
		viewrecords: true,
   		rownumbers:true,
   		multiselect: true,
		jsonReader: { repeatitems : false, id:"0"},
	});
	
	//查询列表
	$j("#search").click(function(){
		var url = $j("body").attr("contextpath") + "/json/findGoodsList.json";
		$j("#tbl-list").jqGrid('setGridParam',{url:url,page:1,
			postData:{
			"owner":$j("#owner").val(),	
			"upc":$j("#upc").val(),
			"hsCode":$j("#hsCode").val(),
			"skuCode":$j("#skuCode").val(),
			"style":$j("#style").val(),
			"color":$j("#color").val(),
			"skuSize":$j("#skuSize").val(),
			"isDiscount":$j("#isDiscount").val(),
			"status":$j("#status").val(),
        	"skuName":$j("#skuName").val(),
			}
		}).trigger("reloadGrid");
	});
	
	//重置
	$j("#reset").click(function(){
		$j("#owner").val("");
		$j("#upc").val("");
		$j("#hsCode").val("");
		$j("#skuCode").val("");
		$j("#style").val("");
		$j("#color").val("");
		$j("#skuSize").val("");
		$j("#isDiscount").val("");
		$j("#status").val("");
		$j("#skuName").val("");
	});
	
	
	//删除
	$j("#delete").click(function(){
		var ids = $j("#tbl-list").jqGrid('getGridParam','selarrrow');
		if (null != ids && ids.length > 0) {
			for ( var i in ids) {
				var result = loxia.syncXhr("/scm/json/deletefindGoodsListById.do", {"id" : ids[i]});
				if (result.msg=="success") {
					jumbo.showMsg("删除成功");
					var url = $j("body").attr("contextpath") + "/json/findGoodsList.json";
					$j("#tbl-list").jqGrid('setGridParam',{url:url,page:1,
						postData:{
						"owner":$j("#owner").val(),	
						"upc":$j("#upc").val(),
						"hsCode":$j("#hsCode").val(),
						"skuCode":$j("#skuCode").val(),
						"style":$j("#style").val(),
						"color":$j("#color").val(),
						"skuSize":$j("#skuSize").val(),
						"isDiscount":$j("#isDiscount").val(),
						"status":$j("#status").val(),
			        	"skuName":$j("#skuName").val(),
						}
					}).trigger("reloadGrid");
					}else{
						jumbo.showMsg("删除失败");
					}
				}
			}
		});
	
	
	//推送
	$j("#push").click(function(){
		var ids = $j("#tbl-list").jqGrid('getGridParam','selarrrow');
		if (null != ids && ids.length > 0) {
			for ( var i in ids) {
				var result = loxia.syncXhr("/scm/json/pushGoods.do", {"id" : ids[i]});
				if (result.msg=="success") {
					jumbo.showMsg("推送成功");
					}else{
						jumbo.showMsg("推送失败");
					}
				}
			}
		});
	
});