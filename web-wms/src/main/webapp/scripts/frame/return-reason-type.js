$j(document).ready(function (){
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-orderList").jqGrid({
		url:baseUrl + "/json/findAllReturnGoods.json",
		datatype: "json",
		colNames: ["id","作业单据号","创建时间","结束时间","相关单据号","相关单据号1","相关单据号2","退货运单号","订单收货人","订单人电话","备注","退货原因"],
		colModel: [
							{name: "id", index: "id", hidden: true},
							{name:"code", index:"code", width:100, resizable:true},	
							{name:"createTime",index:"createTime",width:100,resizable:true},
							{name:"endCreateTime", index:"endCreateTime",width:130,resizable:true},
							{name:"slipCode",index:"refSlipCode",width:100,resizable:true},
							{name:"slipCode1", index:"slipCode1",width:130,resizable:true},
							{name:"slipCode2", index:"slipCode2",width:130,resizable:true},
							{name:"trackingNo", index:"trackingNo",width:130,resizable:true},
							{name:"receiver", index:"receiver",width:80,resizable:true},
							{name:"telephone", index:"telephone",width:130,resizable:true},
							{name:"returnReasonMemo", index:"returnReasonMemo",width:130,resizable:true},
							{name:"returnReasonType",index:"returnReasonType",width:100,resizable:true},
							
						
				  ],
		caption:'退货单列表',
	   	sortname: 'create_time',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#pager",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	jumbo.bindTableExportBtn("tbl-orderList");
	//重置
	$j("#reset").click(function(){
		$j("#code").val("");
		$j("#returnReasonType").val("");
		$j("#slipCode").val("");
		$j("#slipCode1").val("");
		$j("#slipCode2").val("");
		$j("#trackingNo").val("");
		$j("#createTime").val("");
		$j("#endCreateTime").val("");
		
	});
	//查询
	$j("#search").click(function(){
		 var urlx = loxia.getTimeUrl(baseUrl+"/findAllReturnGoods.json");
		   var postData=loxia._ajaxFormToObj("form_query");
		   jQuery("#tbl-orderList").jqGrid('setGridParam',{url:urlx,postData:postData}).trigger("reloadGrid",[{page:1}]);
		   jumbo.bindTableExportBtn("tbl-orderList",{},urlx,postData);
	});
});