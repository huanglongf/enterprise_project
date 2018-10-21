var isInboundInvoice = 0;
$j.extend(loxia.regional['zh-CN'], {
	ENTITY_EXCELFILE :	"正确的excel导入文件",
	ENTITY_STA_CREATE_TIME : "作业创建时间",
	ENTITY_STA_CODE : "作业单号",
	ENTITY_SKU_BARCODE : "条形码",
	ENTITY_SKU_JMCODE : "商品编码",
	ENTITY_SKU_KEYPROP : "扩展属性",
	ENTITY_SKU_NAME : "商品名称",
	ENTITY_SKU_SUPCODE : "货号",
	TABLE_CAPTION_STA : "待收货列表",
	TABLE_CAPTION_STALINE : "单据明细",
	TABLE_BOX_INBOUND : "按箱拆分作业单列表",
	ENTITY_STA_OWNER : "店铺",
	INBOUND_FINISHED : "收货完成"
});

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}

var globeShop = null;
var globeStaId = null;

function showdetail(obj){
	var staId=$j(obj).parent().parent().attr("id");
	globeStaId = staId;
	var tr = $j(obj).parents("tr[id]");
	var sta=$j("#tbl-inbound-purchase").jqGrid("getRowData",staId);
	$j("#staId").data("sta",sta);
	$j("#staId").val(staId);
	if(sta.intStaStatus==10){
		$j("#import").hide();
	}else{
		$j("#import").show();
	}
	$j("#stvId").val(sta['stvId']);
	$j("#nowNum").val(sta['stvTotal']);
    refresh();
	$j("#createTime,#createTime2").html(sta.createTime);
	$j("#staCode,#staCode2").html(sta.code);
	$j("#po").html(sta.refSlipCode);
	$j("#owner,#owner2").html(sta.owner);
	globeShop = sta.owner;
	$j("#status,#status2").html($j(obj).parent().next().html());
	$j("#left,#left2").html(tr.find("td[aria-describedby$='intStaStatus']").text());
	if(sta.intStaStatus == "10" ){
		$j("#inputDiv").addClass("hidden");
	} else {
		$j("#inputDiv").removeClass("hidden");
	}
	$j("#div-sta-list").addClass("hidden");
	$j("#div-sta-detail").removeClass("hidden");
	
}

function showCartonStaDetailList(obj){
	var cartonStaId=$j(obj).parent().parent().attr("id");
	var tr = $j(obj).parents("tr[id]");
	var sta=$j("#tbl-cartonList").jqGrid("getRowData",cartonStaId);
	$j("#cartonStaId").data("sta",sta);
	$j("#cartonStaId").val(cartonStaId);
	if(sta.intStaStatus==10){
		$j("#import").hide();
	}else{
		$j("#import").show();
	}
	var newPostData = {};
	newPostData["sta.id"]=cartonStaId;
	$j("#tbl-cartonDetailList").jqGrid('setGridParam',{
		url:loxia.getTimeUrl($j("body").attr("contextpath")+'/stalinelist.json'),
		postData:newPostData,
		page:1
	}).trigger("reloadGrid");
	//jumbo.bindTableExportBtn("tbl-orderNumber",{},$j("body").attr("contextpath")  + "/stalinelist.json",{"sta.id":staId});
	$j("#cartonCreateTime").html(sta.createTime);
	$j("#cartonStaCode").html(sta.code);
	$j("#cartonPo").html(sta.refSlipCode);
	$j("#cartonOwner").html(sta.owner);
	$j("#cartonStatus").html($j(obj).parent().next().html());
	$j("#cartonLeft").html(tr.find("td[aria-describedby$='intStaStatus']").text());
	if(sta.intStaStatus != "1"){
		$j("#inputDiv1").addClass("hidden");
	} else {
		$j("#inputDiv1").removeClass("hidden");
	}
	$j("#div-sta-list").addClass("hidden");
	$j("#div-sta-detail").addClass("hidden");
	$j("#div-cartonSta-detail").removeClass("hidden");
	
}

$j(document).ready(function (){
	//初始化需要按箱收货的店铺信息
	var retList = loxia.syncXhrPost($j("body").attr("contextpath")+"/getBiChannelInfoOfCartonSta.json"); 
	for(var idx in retList.cartonStaShoplist){
		$j("<option value='" + retList.cartonStaShoplist[idx].code + "'>"+ retList.cartonStaShoplist[idx].name +"</option>").appendTo($j("#companyshop"));
	}
	$j("#search").click(function(){
		var postData = loxia._ajaxFormToObj("form_query");  
		$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
			url:$j("body").attr("contextpath")+"/findCartonStaByPagination.json",postData:postData}).trigger("reloadGrid"); 
	});
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val(0);
	});
	$j("#toBackDetial").click(function(){
		$j("#div-sta-detail").addClass("hidden");
		$j("#div-sta-list").removeClass("hidden");
	});
	$j("#toBackCartonStaDetial").click(function(){
		$j("#div-sta-detail").removeClass("hidden");
		$j("#div-sta-list").addClass("hidden");
		$j("#div-cartonSta-detail").addClass("hidden");
	});
	$j("#searchCartonSta").click(function(){
		$j("#search").trigger("click");
		console.log("-----"+globeStaId);
		//window.showdetail($j("#tbl-cartonList").jqGrid("getRowData",globeStaId));
	});
	$j("#searchRootStaLine").click(function(){
		//主收货作业单明细
		var newPostData = {};
		newPostData["sta.id"]=globeStaId;
		$j("#tbl-orderNumber").jqGrid('setGridParam',{
			url:loxia.getTimeUrl($j("body").attr("contextpath")+'/stalinelist.json'),
			postData:newPostData,
			page:1
		}).trigger("reloadGrid");
	});
	
	var status=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"whSTAStatus"});
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	if(!status.exception){
		//STA作业申请单
		$j("#tbl-inbound-purchase").jqGrid({
			datatype: "json",
			colNames: ["ID","相关单据号",i18("ENTITY_STA_CREATE_TIME"),
			           i18("ENTITY_STA_CODE"),"作业单类型","作业单状态",i18("ENTITY_STA_OWNER")],
			colModel: [
			           {name: "id", index: "id", hidden: true},		         
			           {name: "refSlipCode", index: "refSlipCode", hidden: false},	
			           {name: "createTime", index: "createTime", width: 150, resizable: true,sortable:false},
			           {name: "code", index: "code",formatter:"linkFmatter", formatoptions:{onclick:"showdetail"}, width: 150, resizable: true,sortable:false},
			           {name: "intStaType", index:"intStaType" ,width:90,resizable:true,formatter:'select',editoptions:staType},
			           {name: "intStaStatus", index:"intStaStatus" ,width:70,resizable:true,formatter:'select',editoptions:status},
			           {name: "owner", index: "owner", width: 150, resizable: true,sortable:false}],
			caption: i18("TABLE_CAPTION_STA"),
			sortname: 'sta.create_time',
		   	sortorder: "desc",
		   	multiselect: false,
		    rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
		   	pager:"#pager",
		   	height:jumbo.getHeight(),
			viewrecords: true,
	   		rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" }
		});
	}else{
		jumbo.showMsg(i18("ERROR_INIT"));
	}

	//STA作业申请单行
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-orderNumber").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("ENTITY_SKU_BARCODE"),i18("ENTITY_SKU_JMCODE"),i18("ENTITY_SKU_KEYPROP"),
			i18("ENTITY_SKU_NAME"),i18("ENTITY_SKU_SUPCODE"),"计划量","剩余计划量","执行量","库存状态"],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},	
		           {name: "barCode", index: "barCode", width: 100, resizable: true,sortable:false},
		           {name: "jmcode", index: "jmcode", width: 100, resizable: true,sortable:false},
		           {name: "keyProperties", index: "keyProperties", width: 120, resizable: true,sortable:false},
		           {name: "skuName", index: "jmskuCode", width: 80, resizable: true,sortable:false},
		           {name: "jmskuCode", index: "jmskuCode", width: 120, resizable: true,sortable:false},
		           {name: "quantity", index: "quantity", width: 120, resizable: true,sortable:false},
		           {name: "receiptQty", index: "receiptQty", width: 120, resizable: true,sortable:false},
		           {name: "completeQuantity", index: "completeQuantity", width: 120, resizable: true,sortable:false},
		           {name: "intInvstatusName", index: "intInvstatusName", width: 120, resizable: true,sortable:false},
		           ],
		caption: i18("TABLE_CAPTION_STALINE"),
	   	sortname: 'id',
	    multiselect: false,
		viewrecords: true, 
		sortorder: "desc", 
		height:"auto",
		rownumbers:true,
		rowNum:-1,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	//按箱收货关联作业单显示
	$j("#tbl-cartonList").jqGrid({
		url:$j("body").attr("contextpath")+"/findCartonStaByGroupStaId.json",
		datatype: "json",
		colNames: ["ID","相关单据号",i18("ENTITY_STA_CREATE_TIME"),
		           i18("ENTITY_STA_CODE"),"作业单类型","作业单状态",i18("ENTITY_STA_OWNER"),"操作"],
		colModel: [
		           {name: "id", index: "id", hidden: true},	
		           {name: "refSlipCode", index: "refSlipCode", hidden: false},	
		           {name: "createTime", index: "createTome", width: 150, resizable: true,sortable:false},
		           {name: "code", index: "code", width: 150, resizable: true,sortable:false},
		           {name: "intStaType", index:"type" ,width:70,resizable:true,formatter:'select',editoptions:staType},
		           {name: "intStaStatus", index:"intStaStatus" ,width:70,resizable:true,formatter:'select',editoptions:status},
		           {name: "owner", index: "owner", width: 80, resizable: true,sortable:false},
		           {name:"btnDelete",width:80,resizable:true}],
		caption: i18("TABLE_BOX_INBOUND"),
		sortname: 'sta.create_time',
	   	sortorder: "desc",
	   	multiselect: false,
	   	height:jumbo.getHeight(),
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
	    gridComplete: function(){
		var btn1 = '<div><button type="button" style="width:80px;" class="confirm" name="btnDelete" loxiaType="button" onclick="cancleCaronsta(this);">'+"关闭"+'</button></div>';
		var ids = $j("#tbl-cartonList").jqGrid('getDataIDs');
		for(var i=0;i < ids.length;i++){
			var data = $j("#tbl-cartonList").jqGrid("getRowData",ids[i]);
			if(data["intStaStatus"] == 1){
				$j("#tbl-cartonList").jqGrid('setRowData',ids[i],{"btnDelete":btn1});
			}
		}
		loxia.initContext($j(this));
	    }
	});
	
	//CARTONSTA作业申请单行
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-cartonDetailList").jqGrid({
		datatype: "json",
		colNames: ["ID","STAID","SKUID","OWNER","skuCost",i18("ENTITY_SKU_BARCODE"),i18("ENTITY_SKU_JMCODE"),i18("ENTITY_SKU_KEYPROP"),
			i18("ENTITY_SKU_NAME"),i18("ENTITY_SKU_SUPCODE"),"计划量","执行量","库存状态"],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},	
		           {name: "sta.id", index: "sta.id", hidden: true,sortable:false},	
		           {name: "skuId", index: "sku.id", hidden: true,sortable:false},	
		           {name: "owner", index: "owner", hidden: true,sortable:false},	
		           {name: "skuCost", index: "skuCost", hidden: true,sortable:false},	
		           {name: "barCode", index: "sku.BAR_CODE", width: 100, resizable: true,sortable:false},
		           {name: "jmcode", index: "sku.JM_CODE", width: 100, resizable: true,sortable:false},
		           {name: "keyProperties", index: "sku.KEY_PROPERTIES", width: 120, resizable: true,sortable:false},
		           {name: "skuName", index: "sku.name", width: 80, resizable: true,sortable:false},
		           {name: "jmskuCode", index: "sku.SUPPLIER_CODE", width: 120, resizable: true,sortable:false},
		           {name: "quantity", index: "quantity", width: 120, resizable: true,sortable:false},
		           {name: "completeQuantity", index: "completeQuantity", width: 120, resizable: true,sortable:false},
		           {name: "intInvstatusName", index: "intInvstatusName", width: 120, resizable: true,sortable:false}
		           ],
		caption: i18("TABLE_CAPTION_STALINE"),
	   	sortname: 'isSnSku,sku.bar_Code',
	    multiselect: false,
		viewrecords: true, 
	    postData:{"columns":"id,sta.id,skuId,owner,skuCost,isSnSku,barCode,jmcode,keyProperties,skuName,jmskuCode,quantity,completeQuantity,intInvstatusName"},
		sortorder: "desc", 
		height:"auto",
		rownumbers:true,
		rowNum:-1,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
//	$j("#import").click(function(){
//		var file=$j.trim($j("#file").val()),errors=[];
//		if(file==""||file.indexOf("xls")==-1){
//			errors.push(i18("INVALID_MAND",i18("ENTITY_EXCELFILE")));
//		}
//		var url = $j("body").attr("contextpath") + "/warehouse/importWhBoxInbound.do?innerShopName="+globeShop+"&staId="+globeStaId;
//		if(errors.length>0){
//			jumbo.showMsg(errors.join("<br/>"));
//		}else{
//			
//			$j("#importFormC").attr("action",loxia.getTimeUrl(url));
//			$j("#importFormC").submit();
//		}
//	});
	$j("#import").click(function(){
		console.log("import click");
		var file=$j.trim($j("#file").val()),errors=[];
		if(file==""||file.indexOf("xls")==-1){
			errors.push(i18("INVALID_MAND",i18("ENTITY_EXCELFILE")));
		}
		var url = $j("body").attr("contextpath") + "/warehouse/importboxreceive.do?staId="+globeStaId;
		if(errors.length>0){
			jumbo.showMsg(errors.join("<br/>"));
		}else{
			
			$j("#importFormC").attr("action",loxia.getTimeUrl(url));
			$j("#importFormC").submit();
		}
	});
	$j("#import1").click(function(){
		var file=$j.trim($j("#staFile").val()),errors=[];
		if(file==""||file.indexOf("xls")==-1){
			errors.push(i18("INVALID_MAND",i18("ENTITY_EXCELFILE")));
		}
		var url = $j("body").attr("contextpath") + "/warehouse/importForBatchReceiving.do";
		if(errors.length>0){
			jumbo.showMsg(errors.join("<br/>"));
		}else{
			loxia.lockPage();
			$j("#importFormD").attr("action",loxia.getTimeUrl(url));
			$j("#importFormD").submit();
		}
	});
});

function insertCartonSta(){
	$j('#searchCartonSta').trigger("click");
	$j("#file").val("");
}

function importReturn(){
	$j('#searchRootStaLine').trigger("click");
	$j('#searchCartonSta').trigger("click");
	$j("#file").val("");
	$j("#staFile").val("");
	jumbo.showMsg(i18("INBOUND_FINISHED"));
	//调用后台，校验是否计划与收货执行量一致，一致，则跳转到第一个页面
	var result = loxia.syncXhrPost($j("body").attr("contextpath")+"/checkingRootStaCompleteQty.json?sta.id="+globeStaId);
	//alert(JSON.stringify(result));
	if(result["result"]=="success" && result["flag"]==true){
		$j("#div-sta-list").removeClass("hidden");
		$j("#div-sta-detail").addClass("hidden");
		$j("#search").trigger("click");
	}else{
		$j("#div-sta-list").addClass("hidden");
		$j("#div-sta-detail").removeClass("hidden");
	}
	$j("#div-cartonSta-detail").addClass("hidden");
}

function cancleCaronsta(tag){
	var cartonStaId = $j(tag).parents("tr").attr("id");
	if(window.confirm('确定要取消此单？')){
		var result = loxia.syncXhrPost($j("body").attr("contextpath")+"/cancelCartonSta.json?cartonSta.id="+cartonStaId);
		if(result.result=="success"){
			jumbo.showMsg("取消作业单成功！");
			refresh();
	    }
	}
}
function refresh(){
	var cartonPostData = {};
	cartonPostData["cartonSta.id"]=globeStaId;
	$j("#tbl-cartonList").jqGrid('setGridParam',{
		url:loxia.getTimeUrl($j("body").attr("contextpath")+'/findCartonStaByGroupStaId.json'),
		postData:cartonPostData,
		page:1
	}).trigger("reloadGrid");
	
	//主收货作业单明细
	var newPostData = {};
	newPostData["sta.id"]=globeStaId;
	$j("#tbl-orderNumber").jqGrid('setGridParam',{
//		url:loxia.getTimeUrl($j("body").attr("contextpath")+'/stalinelist.json'),
		url:loxia.getTimeUrl($j("body").attr("contextpath")+'/boxreceivestaline.json'),
		postData:newPostData,
		page:1
	}).trigger("reloadGrid");
}
