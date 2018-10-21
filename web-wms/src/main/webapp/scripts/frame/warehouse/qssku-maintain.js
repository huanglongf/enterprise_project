
$j(document).ready(function (){
	
	
	
	
	$j("#tbl-waittingList").jqGrid({
     	caption: "已维护QS商品列表",
		url:$j("body").attr("contextpath") + "/warehouse/queryqssku.json",
		datatype: "json",
		colNames: ["id","SKU编码","SKU条码","商品名称"],
		colModel: [ 
		           {name: "id", index: "id", width: 200, resizable: true,hidden:true},
 				   {name: "skuCode", index: "skuCode", width: 200, resizable: true},
		           {name: "barCode", index: "barCode", width: 200,  resizable: true},
		           {name: "name", index: "name", width: 300,  resizable: true}
		           ],
		rowNum: 50,
		rowList:[50,100,150],
	   	sortname: 'id',
	    pager: '#pager_query',
	    multiselect: true,
		sortorder: "desc", 
		height:'auto',
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
	});
	


	$j("#search").click(function(){
		var skuCode=document.getElementById("skuCode").value;
		 var obj = {};
		 obj["qsSkuCommand.skuCode"]=skuCode;
		 var url=$j("body").attr("contextpath") + "/warehouse/queryqssku.json";
		 $j("#tbl-waittingList").jqGrid('setGridParam',{
			url:url,
			postData:obj,
			page:1
		}).trigger("reloadGrid");
	});
	
	$j("#reset").click(function(){
		$j("#div1 input,#div1 select").val("");
	});
	
	
	
	$j("#del").click(function(){//批量删除
		var ids = $j("#tbl-waittingList").jqGrid('getGridParam','selarrrow');
		var postData = {};
		if (ids && ids.length>0) {
			postData["ids"]=ids.join(",");
			var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/delQsSkuBinding.json"),postData);
			if(rs["brand"]=='-1'){
				 jumbo.showMsg("系统异常!");
			}else if(rs["brand"]=='1'){
				 var url=$j("body").attr("contextpath") + "/warehouse/queryqssku.json";
				 $j("#tbl-waittingList").jqGrid('setGridParam',{url:url}).trigger("reloadGrid");
			}
		}
	});
	

	$j("#import").click(function(){//导入
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg("请选择正确的Excel导入文件");
			return;
		}
		loxia.lockPage();
		$j("#importForm2").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/saveQsSkuBindingImport.json"));
		loxia.submitForm("importForm2");
	});
	

});







 
