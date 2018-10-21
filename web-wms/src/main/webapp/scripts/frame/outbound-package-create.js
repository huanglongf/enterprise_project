$j.extend(loxia.regional['zh-CN'],{
	OUTBOUNDPACKAGE : "退仓装箱清单打印中，请等待..."
});
var $j = jQuery.noConflict();

$j.extend(loxia.regional['zh-CN'],{
	 
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

//显示明细
function showDetail(obj){
	var baseUrl = $j("body").attr("contextpath");
	$j("#divMain").addClass("hidden");
	$j("#divDetail").removeClass("hidden");
	$j("#tabs").tabs();
	
	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");
	$j("#staid").val(id);
	
	var data = $j("#tbl_sta").jqGrid("getRowData",id);
	
	$j("#staCode_").text(data["code"]);
	$j("#staSlipCode_").text(data["refSlipCode"]);
	$j("#createTime").text(data["createTime"]);
	$j("#principal").text(data["receiver"]);
	$j("#telephone").text(data["telephone"]);
	$j("#address").text(data["address"]);
	if (data["intStatus"] == 10){
		$j("#btnExe").addClass("hidden");
		$j("#btnPgCreate").addClass("hidden");
	} else {
		$j("#btnExe").removeClass("hidden");
		$j("#btnPgCreate").removeClass("hidden");
	}

	// tab1  trunkdetailinfo.json 箱号列表
	$j("#tbl_sta_package tr:gt(0)").remove();
	$j("#tbl_sta_package").jqGrid('setGridParam',{page:1,url:baseUrl + "/trunkdetailinfo.json?staid=" + id}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_sta_package",{},
			baseUrl + "/trunkdetailinfo.json",{"staid":id});
	
	// tab2  planexecutedetailinfo.json 计划执行明细
	$j("#tbl_sta_line tr:gt(0)").remove();
	$j("#tbl_sta_line").jqGrid('setGridParam',{page:1,url:baseUrl + "/planexecutedetailinfo.json?staid=" + id}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_sta_line",{},
			baseUrl + "/planexecutedetailinfo.json",{"staid":id});
	
	// tab3  completedetailinfo.json 已装箱明细
	var tab3PostDate = loxia._ajaxFormToObj("formPgQuery");
	tab3PostDate["staid"]=id;
	$j("#tbl_pg_line tr:gt(0)").remove();
	$j("#tbl_pg_line").jqGrid('setGridParam',{page:1,url:baseUrl + "/completedetailinfo.json?staid=" + id,postData:loxia._ajaxFormToObj("formPgQuery")}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_pg_line",{},
			baseUrl + "/completedetailinfo.json",tab3PostDate);
	
	// tab4  canceldetailinfo.json 差异明细
	$j("#tbl_stv_line tr:gt(0)").remove();
	$j("#tbl_stv_line").jqGrid('setGridParam',{page:1,url:baseUrl + "/canceldetailinfo.json?staid=" + id}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_stv_line",{},
			baseUrl + "/canceldetailinfo.json",{"staid":id});
}

function printNikeCrwLabel(id){
	//loxia.lockPage();
	var dt = new Date().getTime();
	var url = $j("body").attr("contextpath") + "/printNikeCrwLabel.json?cartonId=" + id;
	printBZ(loxia.encodeUrl(url),true);
	//loxia.unlockPage();
}

//打印装箱明细
function printPg(id){
	loxia.lockPage();
	if (id != null){
		jumbo.showMsg(i18("OUTBOUNDPACKAGE"));
		var code=$j("#staCode_").text();
		var sta = loxia.syncXhr(loxia.getTimeUrl($j("body").attr("contextpath") + "/json/queryStaByCode.do?sta.code="+code));
		var url;
		if(sta.sta.owner== 'Nike CRW线下配送店'&&sta.staType== '101'){
			 url = $j("body").attr("contextpath") + "/printPackingBoxLabel.json?cartonId=" + id+"&sta.id=" + sta.sta.id;
		}else{
			 url = $j("body").attr("contextpath") + "/printoutboundpackageinfo.json?staid="+ id;  
		}
		printBZ(loxia.encodeUrl(url),true);	
		printNikeCrwLabel(id);
	}
	loxia.unlockPage();
}
$j(document).ready(function(){
	loxia.init({debug: true, region: 'zh-CN'});

	$j("#tabs").tabs();
	var baseUrl = $j("body").attr("contextpath");
	
	var staType=loxia.syncXhr(baseUrl + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var staStatus=loxia.syncXhr(baseUrl + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	
	$j("#tbl_sta").jqGrid({
		url:baseUrl + "/getoutboundpackagestalist.json",
		datatype: "json",
		colNames: ["ID","作业单号","相关单据号","作业类型","状态","店铺","创建时间","计划执行总数","已执行量","接收人","电话","地址","相关单据号(LOAD KEY)"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name:"code", index:"code", width:100,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, resizable:true},
					{name:"refSlipCode",index:"sta.slip_code",width:100,resizable:true},
					{name:"intType", index:"type" ,width:80,resizable:true,formatter:'select',editoptions:staType},
					{name:"intStatus",index:"status",width:80,resizable:true,formatter:'select',editoptions:staStatus},
					{name:"owner", index:"sta.owner",width:125,resizable:true},
					{name:"createTime",index:"sta.create_time",width:130,resizable:true},
					{name:"totalQty", index:"sta.totalQty",width:80,resizable:true},
					{name:"skuQty", index:"sta.skuQty",width:80,resizable:true},
					{name:"receiver", index:"receiver",width:30,resizable:true,hidden:true},
					{name:"telephone", index:"telephone",width:30,resizable:true,hidden:true},
					{name:"address", index:"address",width:30,resizable:true,hidden:true},
					{name:"slipCode2", index:"slipCode2",width:160,resizable:true}
				  ],
		caption: "作业单列表",
	   	sortname: 'id',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#tbl_sta_page",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	jumbo.bindTableExportBtn("tbl_sta",{"intType":"whSTAType","intStatus":"whSTAStatus"},baseUrl + "/getoutboundpackagestalist.json");
	
	
	// tab-1
	var cartonStatus = loxia.syncXhr(baseUrl + "/json/formateroptions.do",{"categoryCode":"cartonStatus"});
	$j("#tbl_sta_package").jqGrid({
		datatype: "json",
		colNames: ["ID","箱号","箱号编码","状态","状态","创建时间","已执行量","物流商","运单号","退仓耗材","重量","操作","物流面单"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name:"seqNo", index:"seqNo", width:10,resizable:true},
					{name:"code",index:"code",width:50, resizable:true},
					{name:"status",index:"status",width:10, resizable:true,hidden:true},
					{name:"intCartonStatus",index:"status",width:10,resizable:true,formatter:'select',editoptions:cartonStatus},
					{name:"createTime",index:"createTime",width:30,resizable:true},
					{name:"completeQty", index:"completeQty",width:10,resizable:true},
					{name:"lpCode", index:"lpCode",width:30,resizable:true},
					{name:"trackno", index:"trackno",width:30,resizable:true},
					{name:"skus", index:"trackno",width:30,resizable:true},
					{name: "weight", index:"weight", width:20, resizable:true},
					{name: "operate", width:15, resizable:true},
					{name: "print", width:15,  resizable:true}
					],
		caption: "装箱列表",
	   	sortname: 'id',
	    multiselect: false,
	    rowNum: -1,
		rowList:-1,
	   	height:jumbo.getHeight(),
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:false,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){  
			var ids = $j("#tbl_sta_package").jqGrid('getDataIDs');
			var btn2 = '<div style="float: left"><button type="button" style="width:55px;text-align:center;" name="btnPrint" loxiaType="button">打印</button></div>';
			for(var i=0;i < ids.length;i++){
				var printBtn2 = '<div style="float: left"><button type="button" style="width:55px;text-align:center;" name="print" loxiaType="button">打印</button></div>';
				var btn;
				var datas = $j("#tbl_sta_package").jqGrid('getRowData',ids[i]);
				if((datas["status"]=='FINISH')){
					btn = btn2;
				}else{
					btn = '<div style="width: 56px;float: left">&nbsp;</div>';
					printBtn2 = '<div style="width: 56px;float: left">&nbsp;</div>';
				}
				$j("#tbl_sta_package").jqGrid('setRowData',ids[i],{"operate":btn});
				$j("#tbl_sta_package").jqGrid('setRowData',ids[i],{"print":printBtn2});
			}
			loxia.initContext($j(this));
			$j("button[name='btnPrint']").click(function(){
				var id =$j(this).parents("tr").attr("id");
				printPg(id);
			});
			$j("button[name='print']").click(function(){
				var id =$j(this).parents("tr").attr("id");
				printSingleDeliveryInfo(id);
			});
			
//			$j("#tbl_sta_package").closest(".ui-jqgrid-bdiv").css({ 'overflow-x': 'hidden' });
			
		}, 
		loadComplete:function(){
			loxia.initContext($j("#tbl_sta_package"));
		}
	}).setGridWidth(1000);
	
	// tab-2
	$j("#tbl_sta_line").jqGrid({
		datatype: "json",
		colNames: ["ID","商品编码","商品条码","商品名称","货号","计划执行量","剩余计划量"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name:"code", index:"code", width:100,resizable:true},
					{name:"barCode",index:"barCode",width:120,resizable:true},
					{name:"name",index:"name",width:150,resizable:true},
					{name:"supplierCode", index:"supplierCode" ,width:130,resizable:true},
					{name:"quantity", index:"quantity",width:100,resizable:true},
					{name:"salesQty", index:"salesQty",width:100,resizable:true}],
		caption: "计划出库明细",
	   	sortname: 'id',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#tbl_sta_line_page",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	// tab-3
	$j("#tbl_pg_line").jqGrid({
		datatype: "json",
		colNames: ["ID","箱号","箱号编码","商品编码","商品条码","商品名称","货号","执行量"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name:"color", index:"color", width:100,resizable:true},
					{name:"cartonCode",index:"cartonCode",width:120,resizable:true},
					{name:"code", index:"code", width:100,resizable:true},
					{name:"barCode",index:"barCode",width:120,resizable:true},
					{name:"name",index:"name",width:150,resizable:true},
					{name:"supplierCode", index:"supplierCode" ,width:130,resizable:true},
					{name:"quantity", index:"quantity",width:100,resizable:true}],
		caption: "装箱明细",
	   	sortname: 'id',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#tbl_pg_line_page",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers: true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	// tab-4
	$j("#tbl_stv_line").jqGrid({
		datatype: "json",
		colNames: ["ID","商品编码","商品条码","商品名称","货号","库位","数量"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name:"skuCode", index:"skuCode", width:100,resizable:true},
					{name:"barCode",index:"barCode",width:120,resizable:true},
					{name:"skuName",index:"skuName",width:150,resizable:true},
					{name:"supplierCode", index:"supplierCode" ,width:130,resizable:true},
					{name:"locationCode", index:"locationCode" ,width:130,resizable:true},
					{name:"quantity", index:"quantity",width:100,resizable:true}],
		caption: "差异明细",
	   	sortname: 'id',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#tbl_stv_line_page",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});	
	
	//创建箱号
	$j("#btnPgCreate").click(function(){
		var id = $j("#staid").val();
		var rs = loxia.syncXhrPost( baseUrl + "/generatecartonbystaid.json",{"staid":id});
		if(rs && rs["result"] == 'success'){
			jumbo.showMsg("创建成功");
			$j("#tbl_sta_package").jqGrid('setGridParam',{page:1,url:baseUrl + "/trunkdetailinfo.json?staid=" + id}).trigger("reloadGrid");
		}else if(rs && rs["result"] == 'error'){
			jumbo.showMsg(rs["message"]);
		}
	});
	

	//执行
	$j("#btnExe").click(function(){
		if(window.confirm("确认执行出库？如有差异系统也将执行，差异库存将被释放！")){
			var id = $j("#staid").val();
			if (id != null){
				var rs = loxia.syncXhrPost( baseUrl + "/executepartlyoutbound.json",{"staid":id});
				if(rs && rs["result"] == 'success'){
					jumbo.showMsg("操作成功");
				}else if(rs && rs["result"] == 'error'){
					jumbo.showMsg("操作失败<br/>" + rs["message"]);
				}
			}
		}
	});
	
	//查询
	$j("#query").click(function(){
		var url = baseUrl + "/getoutboundpackagestalist.json";
		$j("#tbl_sta").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("formQuery")}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl_sta",{"intType":"whSTAType","intStatus":"whSTAStatus"},
			url,loxia._ajaxFormToObj("formQuery"));
	});
	//查询重置
	$j("#btnReset").click(function(){
		$j("#formQuery input,select").val("");
	});

	//装箱明细查询
	$j("#pgQuery").click(function(){
		var postData = loxia._ajaxFormToObj("formPgQuery");
		postData["staid"]=$j("#staid").val();
		$j("#tbl_pg_line").jqGrid('setGridParam',{page:1,url:baseUrl + "/completedetailinfo.json",postData:postData}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl_pg_line",{},
				baseUrl + "/completedetailinfo.json",tab3PostDate);
	});
	
	//装箱明细查询重置
	$j("#btnPgReset").click(function(){
		$j("#formPgQuery input").val("");
	});
	
	//返回
	$j("#btnBack").click(function(){
		$j("#divMain").removeClass("hidden");
		$j("#divDetail").addClass("hidden");
	});
	
	//打印汇总
	$j("#btnPrintTotal").click(function(){
		var id = $j("#staid").val();
		printPgTotal(id);
	});
	
	$j("#printPod").click(function (){
		printNikeCrwPod();
	});
});



//打印装箱汇总
function printPgTotal(id){
	loxia.lockPage();
	if (id != null){
		var code=$j("#staCode_").text();
		var sta = loxia.syncXhr(loxia.getTimeUrl($j("body").attr("contextpath") + "/json/queryStaByCode.do?sta.code="+code));
		var url;
		if(sta.sta.owner== 'Nike CRW线下配送店'){
			url = $j("body").attr("contextpath") + "/printoutboundsendinfo.json?sta.id="  + sta.sta.id;
		}else{
			 url = $j("body").attr("contextpath") + "/printoutboundpackingintegrity.json?staid="+ id;  
		}
		printBZ(loxia.encodeUrl(url),true);		
	}
	loxia.unlockPage();
}

//打印物流面单信息
function printSingleDeliveryInfo(id){
	var dt = new Date().getTime();
	var url = $j("body").attr("contextpath") + "/printSingleVmiDeliveryMode1OutByCarton.json?cartonId=" + id;
	printBZ(loxia.encodeUrl(url),false);
	loxia.unlockPage();
}

function printNikeCrwPod(){
	loxia.lockPage();
	var id =$j("#staid").val();
	var dt = new Date().getTime();
	var url = $j("body").attr("contextpath") + "/printNikeCrwPod.json?staid=" + id;
	printBZ(loxia.encodeUrl(url),true);
	loxia.unlockPage();
}