$j.extend(loxia.regional['zh-CN'],{
	JMCODE : "商品编码",
	KEYPROPERTIES : "扩展属性",
	SKUNAME : "商品名称",
	SKUCODE: "SKU编码",
	BARCODE : "条形码",
	SUPPLIERSKUCODE : "货号",
	INVOWNER : "所属店铺",
	QUANTITY : "实际库存量（件）",
	LOCKQTY : "已占用库存量（件）",
	AVAILQTY : "可用库存量（件）",
	SALESAVAILQTY : "销售可用量（件）",
	INVENTORY_QUERY_LIST : "库存查询列表",
	LOCATIONCODE: "库位编号",
	BATCHCODE : "批次号",
	DISTRICTCODE : "库区",
	LOCATIONCODE : "库位",
	INVSTATUSNAME : "库存状态",
	QUANTITY : "数量",
	LOCKQTY : "占用量",
	ENTITY_EXCELFILE:"正确的excel导入文件",
	EXTENSION_CODE1:"外部编码"
	
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}
var $j = jQuery.noConflict();


function showDetail(obj){
	$j("#createInStaDIV").addClass("hidden");
	var tr = $j(obj).parents("tr[id]");
	
	var id=tr.attr("id");
	$j("#tbl_details").jqGrid('setGridParam',{page:1,url:$j("body").attr("contextpath")+"/queryGILocSku.json?loc.id="+id}).trigger("reloadGrid");
	var data=$j("#tbl_gi_location").jqGrid("getRowData",id);
	
	$j("#locId").val(id);
	$j("#code").text(data['code']);
	$j("#staCode").text(data['staCode']);
	$j("#staSlipCode").text(data['staSlipCode']);
	$j("#createDate").text(data['createDate']);
	$j("#skuQty").text(data['qty']);
	
	
	$j("#GILocation").addClass("hidden");
	$j("#details").removeClass("hidden");
}




$j(document).ready(function (){
	var baseUrl = $j("body").attr("contextpath");
	
	$j("#tbl_gi_location").jqGrid({
		url:baseUrl+"/queryGILocation.json",
		datatype: "json",
		colNames: ["ID","库位","作业单","前置单据","存放时间","商品数量"],
		colModel: [{name: "id", index: "id", hidden: true},
		    {name:"code", index:"code" ,width:250,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"},resizable:true},
		    {name:"staCode", index:"staCode" ,width:150,resizable:true},
		    {name:"staSlipCode", index:"staSlipCode" ,width:150,resizable:true},
		    {name:"createDate", index:"createDate" ,width:150,resizable:true},
		    {name:"qty", index:"qty" ,width:90,resizable:true}],
		caption: "暂存区库位列表",
   		sortname: 'loc.id',
    	multiselect: false,
		sortorder: "desc",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#tbl_details").jqGrid({
		datatype: "json",
		colNames: ["skuId",i18("SKUCODE"),i18("JMCODE"),i18("KEYPROPERTIES"),i18("BARCODE"),i18("SUPPLIERSKUCODE"),i18("SKUNAME"),
		           i18("INVOWNER"),i18("INVOWNER"),i18("EXTENSION_CODE1"),'占用码',i18("QUANTITY")],
		           colModel: [ {name: "skuId", index: "skuId", width: 100, hidden: true},
		    		           {name: "skuCode", index: "skuCode", width: 100,  resizable: true},
		    		           {name: "jmCode", index: "jmCode", width: 100, resizable: true},
		    		           {name: "keyProperties", index: "keyProperties", width: 100, resizable: true},
		    		           {name: "barCode", index: "barCode", width: 100, resizable: true},
		    		           {name: "supplierSkuCode", index: "supplierSkuCode", width:150, resizable: true},
		    		           {name: "skuName", index: "skuName", width: 80, resizable: true},
		    		           {name: "invOwner", index: "invOwner", width: 80, resizable: true,hidden:true},
		    		           {name: "brandName", index: "brandName", width: 110, resizable: true},
		    		           {name: "extCode1", index: "extCode1", width: 80, resizable: true},
		    		           {name: "occupationCode", index: "occupationCode", width: 120, resizable: true},
		    		           {name: "quantity", index: "quantity", width: 120, resizable: true}
		    		           ],
		caption: "商品列表",
   		sortname: 'sku.id',
    	multiselect: false,
    	rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		pager:"#pager",
	    height:"auto",
		sortorder: "desc",
		viewrecords: true,
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		loadComplete:function(){
			var ids = $j("#tbl_details").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var datas = $j("#tbl_details").jqGrid('getRowData',ids[i]);
				if(datas["occupationCode"] == ""){
					$j("#createInStaDIV").removeClass("hidden");
					return;
				} 
			}
		}
	});
	
	$j("#search").click(function (){
		$j("#tbl_gi_location").jqGrid('setGridParam',{url:baseUrl+"/queryGILocation.json",page:1,postData:loxia._ajaxFormToObj("form_query")}).trigger("reloadGrid");
	});
	
	$j("#reset").click(function (){
		$j("#form_query input,#form_query select").val("");
	});
	
	$j("#createInStaDIV").click(function (){
		if(window.confirm('你确定须要创建入库上架单？')){
			var postData = {};
			postData["loc.id"] = $j("#locId").val();
			var rs = loxia.syncXhrPost(baseUrl + "/createGIInboundByLoc.json",postData);
			if(rs && rs.msg == 'success'){
				//执行成功
				$j("#tbl-invList tr[id]").remove();
				$j("#file").val(""); 
				jumbo.showMsg("创建成功！");
				$j("#tbl_details").jqGrid('setGridParam',{page:1,url:$j("body").attr("contextpath")+"/queryGILocSku.json?loc.id="+$j("#locId").val()}).trigger("reloadGrid");
				$j("#createInStaDIV").addClass("hidden");
			}else{
				if(rs.errMsg != null){
				var msg = rs.errMsg;
				var s = '';
				for(var x in msg){
					s +=msg[x] + '<br/>';
				}
					jumbo.showMsg(s);
				}else{
					jumbo.showMsg(rs.msg);
				}
			}
		}
	});
	
	$j("#back").click(function (){
		$j("#details").addClass("hidden");
		$j("#GILocation").removeClass("hidden");
	});
});
