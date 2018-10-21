$j.extend(loxia.regional['zh-CN'],{
	CODE : "作业单号",
	INTSTATUS : "状态",
	PLANBILLCOUNT : "计划配货单据数",
	CHECKEDBILLCOUNT : "已核对单据数",
	SHIPSTACOUNT : "已发货单据数",
	PLANSKUQTY : "计划配货商品件数",
	CHECKEDSKUQTY : "已核对商品件数",
	SHIPSKUQTY : "已发货商品件数",
	CHECKEDTIME : "最后核对时间",
	EXECUTEDTIME : "最后发货时间",
	PICKINGTIME : "开始配货时间",
	PICKING_LIST : "配货清单列表",
	CREATE_TIME : "创建时间",
	INIT_SYSTEM_DATA_EXCEPTION : "初始化系统参数异常",
	KEY_PROPS : "扩展属性",
	MEMO : "备注",
	STA_CODE : "作业单号",
	INTSTATUS : "状态",
	REFSLIPCODE : "相关单据号",
	INTTYPE : "作业类型名称",
	OWNER : "相关店铺",
	LPCODE : "物流服务商简称",
	CREATETIME : "创建时间",
	STVTOTAL : "计划执行总件数",
	WAITTING_CHECKED_LIST : "待核对列表",
	
	CHECKED_LIST : "已核对列表",
	
	LOCATIONCODE : "库位编码预览",
	SKUNAME : "商品名称",
	JMSKUCODE : "商品编码",
	BARCODE : "条形码",
	QUANTITY : "计划执行数量",
	COMPLETEQUANTITY : "已执行数量",
	
	INPUT_CODE : "请输入相关单据号",
	NO_CODE : "指定单据号的作业单不在待核对的列表！",
	BARCODE_NOT_EXISTS : "条形码不存在",
	TRACKINGNO_EXISTS : "快递单号已经存在",
	DELETE : "删除",
	TRACKINGNO_RULE_ERROR : "快递单号格式不对",
	SURE_OPERATE : "确定执行本次操作",
	QUANTITY_ERROR : "数量错误",
	WEIGHT_RULE_ERROR : "已执行量大于计划执行量，如果继续执行核对，将直接按照计划执行量执行核对。是否继续？",
	INPUT_TRACKINGNO : "请输入快递单号",
	OPERATE_FAILED : "执行核对失败！",
	INT_LIST : "作业单列表",
	CREATE_TIME_RULE : "创建时间:起始时间必须小于结束时间！",
	PICKING_TIME_RULE : "配货时间:起始时间必须小于结束时间！",
	OUTBOUND_TIME_RULE : "发货时间:起始时间必须小于结束时间！",
	CHECKED_TIME_RULE : "最后核对时间:起始时间必须小于结束时间！",
	SHOPID			:"店铺"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function getDate(strDate){
	var s = new Date(strDate.substring(0,10));
	s.setHours(strDate.substring(11,13));
	s.setMinutes(strDate.substring(11,13));
	s.setSeconds(strDate.substring(11,13));
	return s;
}

function reloadOrderDetail(url,postData){
	$j("#tbl-orderDetail").jqGrid('setGridParam',{url:loxia.getTimeUrl(url)}).trigger("reloadGrid");
}


function showDetail(tag){
	$j("#divTbDetial").removeClass("hidden");
	var id = $j(tag).parents("tr").attr("id");
	$j("#staId").val(id);
	var sta=$j("#tbl-dispatch-list").jqGrid("getRowData",id);
	$j("#staCode_d").text(sta["code"]);
	$j("#slipCode_d").text(sta["refSlipCode"]);
	
	$j("#showList").addClass("hidden");
	$j("#div2").removeClass("hidden");

	var baseUrl = $j("body").attr("contextpath");
	
	//reloadOrderDetail($j("body").attr("contextpath") + "/detialList.json?pickingListId="+id);
	$j("#tbl-product").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/findThreeDimensionalSkuInfo.json?staId="+id)}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl-product",{"isRail":"trueOrFalse"},$j("body").attr("contextpath") + "/findThreeDimensionalSkuInfo.json",{"staId":$j("#staId").val()});
	
}


function showDetail2(tag){
	var pinkingListId = $j(tag).parents("tr").attr("id");
	var sta=$j("#tbl-dispatch-list2").jqGrid("getRowData",pinkingListId);
	$j("#pinkingListId_d").text(sta["pinkingCode"]);
	$j("#showList2").addClass("hidden");
	$j("#div3").removeClass("hidden");
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-product2").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/findNoThreeDimensionalSkuInfo.json?plId="+pinkingListId)}).trigger("reloadGrid");
}




var wlist =null;
$j(document).ready(function (){
	/*var result2= loxia.syncXhrPost($j	("body").attr("contextpath")+"/selectbyopc.json");
	for(var idx in result2.warelist){
		$j("<option value='" + result2.warelist[idx].id + "'>"+ result2.warelist[idx].name +"</option>").appendTo($j("#wselTrans"));
	}*/
	$j("#div2").addClass("hidden");
	$j("#showList").removeClass("hidden");
	$j("#tabs").tabs({
		 select: function(event, ui) {
		  }
	});
	$j("#tbl-dispatch-list").jqGrid({
		url:$j("body").attr("contextpath") + "/json/findThreeDimensional.json",
		datatype: "json",
		//["ID","配货批次号","状态","计划配货单据数","已核对单据数","已发货单据数","计划配货商品件数","已核对商品件数","已发货商品件数","创建时间","最后核对时间","最后发货时间","开始配货时间"]
		colNames: ["ID","作业单号","相关单据号","相关单据号1","作业类型","店铺","状态","创建时间","备注"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name :"code",index:"code",width:100,resizable:true, formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}},
					{name:"refSlipCode", index:"refSlipCode" ,width:60,resizable:true},
					{name: "slipCode1", index:"slipCode1",width:120},
					{name: "strType", index:"strType",width:100,resizable:true},
					{name:"owner", index:"owner", width:90, resizable:true,hidden:false},
					{name:"statusName",index:"statusName",width:90,resizable:true,hidden:false},
					{name:"createTime",index: "createTime",width:120,resizable:true},
					{name:"memo",index:"memo",width:100,resizable:true,hidden:false}],
		caption: "入库单列表",
   		sortname: 'ID',
  		multiselect: false,
		sortorder: "desc",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	viewrecords: true,
   		rownumbers:true,
	   	pager:"#pager",
	   	jsonReader: { repeatitems : false, id: "0" }
	});
	jumbo.bindTableExportBtn("tbl-dispatch-list",{},$j("body").attr("contextpath") +"/findThreeDimensional.json",loxia._ajaxFormToObj("plForm"));

	var trueOrFalse=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"trueOrFalse"});
	$j("#tbl-product").jqGrid({
		//url:baseUrl+"/findThreeDimensionalSkuInfo.json",
		datatype: "json",
		//postData:loxia._ajaxFormToObj("form_query"),
		colNames: ["id","商品编码","商品名称","货号","品牌名称","长","宽","高","重量","是否耗材","箱型名称","箱型条码","所属商品分类","是否陆运","商品上架类型"],
		colModel: [{name:"id",index:"id",hidden:true},        
		           {name: "code", index: "code", width: 120,resizable: true},
		           {name: "name", index: "name", width: 150, resizable: true},
		           {name: 'supplierCode', index: "supplierCode", width: 200, resizable: true},
		           {name: "brandName",index:"brandName",width:150,resizable:true},
		           {name: "length", index: "length", width: 50, resizable: true},
		           {name: "width", index: "width", width: 50, resizable: true},
		           {name: "height", index: "height", width: 50, resizable: true},
		           {name: "grossWeight", index: "grossWeight", width: 60, resizable: true},
		           {name: "isConsumable", index: "isConsumable", width: 60, resizable: true},
		           {name: "packageSkuName", index: "packageSkuName", width: 100, resizable:true},
		           {name: "packageBarCode", index: "packageBarCode", width: 100, resizable:true},
		           {name: "categoryName", index: "categoryName", width: 100, resizable: true},
		           {name: "isRail", index: "isRail", width:50, resizable: true,formatter:'select',editoptions:trueOrFalse},
		           {name: "skuTypeName", index: "skuTypeName", width: 60, resizable: true}
		           ],
		           
		caption: "缺失三维商品列表",
	   	pager:"#pager2",
	   	sortname:'id',
	   	sortorder:"desc",
	   	rownumbers:true,
	   	multiselect: false,
	   	viewrecords: true,
	   	rowNum: 10,
		rowList:[10,20,40],
		height:jumbo.getHeight(),
		gridComplete : function(){
			loxia.initContext($j(this));
		},
		jsonReader: { repeatitems : false, id: "0" }
	});
	jumbo.bindTableExportBtn("tbl-product",{"isRail":"trueOrFalse"},$j("body").attr("contextpath") + "/findThreeDimensionalSkuInfo.json",{"staId":$j("#staId").val()});
	
	
	$j("#tbl-product2").jqGrid({
		datatype: "json",
		colNames: ["id","商品编码","商品名称","货号","品牌名称","长","宽","高","重量","是否耗材","箱型名称","箱型条码","所属商品分类","是否陆运","商品上架类型"],
		colModel: [{name:"id",index:"id",hidden:true},        
		           {name: "code", index: "code", width: 120,resizable: true},
		           {name: "name", index: "name", width: 150, resizable: true},
		           {name: 'supplierCode', index: "supplierCode", width: 200, resizable: true},
		           {name: "brandName",index:"brandName",width:150,resizable:true},
		           {name: "length", index: "length", width: 50, resizable: true},
		           {name: "width", index: "width", width: 50, resizable: true},
		           {name: "height", index: "height", width: 50, resizable: true},
		           {name: "grossWeight", index: "grossWeight", width: 60, resizable: true},
		           {name: "isConsumable", index: "isConsumable", width: 60, resizable: true},
		           {name: "packageSkuName", index: "packageSkuName", width: 100, resizable:true},
		           {name: "packageBarCode", index: "packageBarCode", width: 100, resizable:true},
		           {name: "categoryName", index: "categoryName", width: 100, resizable: true},
		           {name: "isRail", index: "isRail", width:50, resizable: true,formatter:'select',editoptions:trueOrFalse},
		           {name: "skuTypeName", index: "skuTypeName", width: 60, resizable: true}
		           ],
		           
		caption: "缺失三维商品列表",
	   	pager:"#pager4",
	   	sortname:'id',
	   	sortorder:"desc",
	   	rownumbers:true,
	   	multiselect: false,
	   	viewrecords: true,
	   	rowNum: 10,
		rowList:[10,20,40],
		height:jumbo.getHeight(),
		gridComplete : function(){
			loxia.initContext($j(this));
		},
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	
	
	
	$j("#tbl-dispatch-list2").jqGrid({
		url:$j("body").attr("contextpath") + "/json/findNoThreeDimensional.json",
		datatype: "json",
		colNames: ["批次ID","批次号","店铺","创建时间"],
		colModel: [	{name:"pinkingListId",index:"pinkingListId",hidden:true},
					{name :"pinkingCode",index:"pinkingCode",width:150,resizable:true, formatter:"linkFmatter",formatoptions:{onclick:"showDetail2"}},
					{name:"store", index:"store", width:150, resizable:true},
					{name:"createTime",index: "createTime",width:150,resizable:true}
				  ],
		caption: "入库单列表",
   		sortname: 'pinkingListId',
  		multiselect: false,
		sortorder: "desc",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	viewrecords: true,
   		rownumbers:true,
	   	pager:"#pager3",
	   	jsonReader: { repeatitems : false, id: "0" }
	});
	
	
	
	$j("#search").click(function(){
		
		var postData=loxia._ajaxFormToObj("plForm");
		//postData["wid"]=$j("#wselTrans").val();
		
		$j("#tbl-dispatch-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/findThreeDimensional.json"),
			postData:postData}).trigger("reloadGrid",[{page:1}]);
		jumbo.bindTableExportBtn("tbl-dispatch-list",{},$j("body").attr("contextpath") +"/findThreeDimensional.json",loxia._ajaxFormToObj("plForm"));
	});
	
	
	$j("#search2").click(function(){
		
		var postData={};
		postData["pinkingCode"]=$j("#pinkingListId").val();
		postData["startCreateTime2"]=$j("#startCreateTime2").val();
		postData["endCreateTime2"]=$j("#endCreateTime2").val();
		$j("#tbl-dispatch-list2").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/findNoThreeDimensional.json"),
			postData:postData}).trigger("reloadGrid",[{page:1}]);
	});
	
	$j("#reset").click(function(){
		document.getElementById("plForm").reset();
	});
	
	$j("#reset2").click(function(){
		document.getElementById("plForm2").reset();
	});
	
	$j("#back").click(function(){
			$j("#div2").addClass("hidden");
			$j("#showList").removeClass("hidden");
		});
	
	$j("#back2").click(function(){
		$j("#div3").addClass("hidden");
		$j("#showList2").removeClass("hidden");
	});
	$j("#finish").click(function(){
		var id = $j("#pickinglistid").val();
		if(id == null){jumbo.showMsg("数据错误.");return false;}
		var baseUrl = $j("body").attr("contextpath");

		var rs = loxia.syncXhrPost(baseUrl + "/updatepickinglisttofinish.json",{"pickingListId":id});
		if(rs){
			var result = rs.result;
			if (result == 'success'){
				jumbo.showMsg('操作成功.');
			}else if (result == 'error'){
				var msgmap = {};
				msgmap = {"OCCUPIED":"库存占用（配货中）","CHECKED":"已核对", "INTRANSIT":"已转出", "CANCEL_UNDO":"取消未处理"}
				jumbo.showMsg(rs.msg);
			}
		}
	});
	/**
	 * 导出商品信息
	 */
	$j("#exportOrder").click(function(){

		var hehe=loxia._ajaxFormToObj("plForm");
		var postdata="staCommand.startCreateTime1="+hehe['staCommand.startCreateTime1']+"&staCommand.endCreateTime1="+
		hehe['staCommand.endCreateTime1']+"&staCommand.code="+hehe['staCommand.code']+
		"&staCommand.refSlipCode="+hehe['staCommand.refSlipCode']+"&staCommand.slipCode1"+hehe['staCommand.slipCode1']+
		"&staCommand.strType="+hehe['staCommand.strType']+"&staCommand.owner="+
		hehe['staCommand.owner']+"&staCommand.intStaStatus="+hehe['staCommand.intStaStatus'];
		var url=$j("body").attr("contextpath") + "/json/exportThreeDimensionalSkuInfo.json?"+postdata;
		$j("#exportFrame").attr("src",url);
	});
	
	/**
	 * 导出商品信息
	 */
	$j("#exportOrder2").click(function(){
		var url=$j("body").attr("contextpath") + "/json/exportNoThreeDimensional.json?";
		$j("#exportFrame").attr("src",url);
	});
	
	$j("#import").click(function(){
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg("请选择正确的Excel导入文件");//请选择正确的Excel导入文件
			return false;
		}
		var flag = false;
		var brand = "";
		$j("#importForm").attr("action",window.parent.$j("body").attr("contextpath") + "/warehouse/updateProductInfo2.do?nikeFlag=" + flag + "&brandName=" + brand);
		$j("#importForm").submit();
	});
	$j("#skuSpTypeImport").click(function(){
		if(!/^.*\.xls$/.test($j("#skuSpTypeImportFile").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));//请选择正确的Excel导入文件
			return false;
		}
		//loxia.lockPage();
		$j("#skuSpTypeImportForm").attr("action",window.parent.$j("body").attr("contextpath") + "/auto/skuSpTypeImport.do");
		$j("#skuSpTypeImportForm").submit();
	});
	
});
