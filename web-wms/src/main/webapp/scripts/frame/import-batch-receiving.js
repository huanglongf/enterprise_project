var isInboundInvoice = 0;
$j.extend(loxia.regional['zh-CN'], {
	ENTITY_EXCELFILE				:	"正确的excel导入文件",
	ENTITY_STA_CREATE_TIME : "作业创建时间",
	ENTITY_STA_CODE : "作业单号",
	ENTITY_SKU_BARCODE : "条形码",
	ENTITY_SKU_JMCODE : "商品编码",
	ENTITY_SKU_KEYPROP : "扩展属性",
	ENTITY_SKU_NAME : "商品名称",
	ENTITY_SKU_SUPCODE : "货号",
	TABLE_CAPTION_STA : "待收货列表",
	TABLE_CAPTION_STALINE : "待收货明细表",
	ENTITY_STA_OWNER : "店铺"
});

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}


function showdetail(obj){
	var staId=$j(obj).parent().parent().attr("id");
	var tr = $j(obj).parents("tr[id]");
	var sta=$j("#tbl-inbound-purchase").jqGrid("getRowData",staId);
	$j("#staId").data("sta",sta);
	$j("#staId").val(staId);
	$j("#createTime3").val(sta.createTime);
	$j("#staCode3").val(sta.code);
	$j("#owner3").val(sta.owner);
	$j("#supplier3").val(sta.supplier);
	$j("#status3").val($j(obj).parent().next().html());
	$j("#left3").val(sta.remnant);
	if(sta.intStaStatus==10){
		$j("#import").hide();
	}else{
		$j("#import").show();
	}
	$j("#stvId").val(sta['stvId']);
	$j("#nowNum").val(sta['stvTotal']);

	var newPostData = {};
	newPostData["sta.id"]=staId;
	$j("#tbl-orderNumber").jqGrid('setGridParam',{
		url:loxia.getTimeUrl($j("body").attr("contextpath")+'/stalinelist.json'),
		postData:newPostData,
		page:1
	}).trigger("reloadGrid");
	//jumbo.bindTableExportBtn("tbl-orderNumber",{},$j("body").attr("contextpath")  + "/stalinelist.json",{"sta.id":staId});
	$j("#createTime,#createTime2").html(sta.createTime);
	$j("#staCode,#staCode2").html(sta.code);
	$j("#po").html(sta.refSlipCode);
	$j("#memo2").html(sta.memo);
	$j("#owner,#owner2").html(sta.owner);
	$j("#supplier,#supplier2").html(sta.supplier);
	$j("#status,#status2").html($j(obj).parent().next().html());
	$j("#left,#left2").html(tr.find("td[aria-describedby$='intStaStatus']").text());
	if(sta.intStaStatus != "1"){
		$j("#inputDiv").addClass("hidden");
		$j("#cancelA").addClass("hidden");
	} else {
		$j("#inputDiv").removeClass("hidden");
		$j("#cancelA").removeClass("hidden");
	}
	$j("#div-sta-list").addClass("hidden");
	$j("#div-sta-detail").removeClass("hidden");
}

$j(document).ready(function (){
	jumbo.loadShopList("companyshop");
	initShopQuery("companyshop","innerShopCode");
	
	//根据作业单查询状态
	var result = loxia.syncXhrPost($j("body").attr("contextpath")+"/findstastatusbystaid.json"); 
	for(var idx in result.warelist){
		$j("<option value='" + result.warelist[idx].id + "'>"+ result.warelist[idx].name +"</option>").appendTo($j("#stastatus"));
	}
	//根据作业单查询类型
//	var resultb = loxia.syncXhrPost($j("body").attr("contextpath")+"/findtypesbystaid.json");
//	for(var idx in resultb.warelist){
//		$j("<option value='" + resultb.warelist[idx].id + "'>"+ resultb.warelist[idx].name +"</option>").appendTo($j("#statypes"));
//	}
	$j("#search").click(function(){
		var postData = loxia._ajaxFormToObj("form_query");  
		$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
			url:$j("body").attr("contextpath")+"/findBatchStaByPagination.json",postData:postData}).trigger("reloadGrid"); 
	});
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val(0);
	});
	$j("#toBackDetial").click(function(){
		$j("#div-sta-detail").addClass("hidden");
		$j("#div-sta-list").removeClass("hidden");
	});
	$j("#cancelA").click(function(){
		if(window.confirm('确定要取消此单？')){
			var result = loxia.syncXhrPost($j("body").attr("contextpath")+"/cancelstaEasy.json?sta.id="+$j("#staId").val()+"&sta.memo="+$j("#importRmk").val());
			if(result.result=="bingo"){
				jumbo.showMsg("取消作业单成功！");
				var postData = loxia._ajaxFormToObj("form_query");  
				$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
				url:$j("body").attr("contextpath")+"/findBatchStaByPagination.json",postData:postData}).trigger("reloadGrid");
				$j("#div-sta-list").removeClass("hidden");
				$j("#div-sta-detail").addClass("hidden");
		    }
		}
	});
	var status=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"whSTAStatus"});
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	if(!status.exception){
		//STA作业申请单
		$j("#tbl-inbound-purchase").jqGrid({
			url:$j("body").attr("contextpath")+"/findBatchStaByPagination.json",
			datatype: "json",
			colNames: ["ID","STVID","STVTOTAL","STVMODE","相关单据号",i18("ENTITY_STA_CREATE_TIME"),
			           i18("ENTITY_STA_CODE"),"作业单类型","作业单状态",i18("ENTITY_STA_OWNER"),"作业单总数量"],
			colModel: [
			           {name: "id", index: "id", hidden: true},		         
			           {name: "stvId", index: "stvId", hidden: true},		         
			           {name: "stvTotal", index: "stv_total", hidden: true},		         
			           {name: "stvMode", index: "stv_mode", hidden: true},	
			           {name: "refSlipCode", index: "", hidden: false},	
			           {name: "createTime", index: "create_time", width: 150, resizable: true,sortable:false},
			           {name: "code", index: "sta.code",formatter:"linkFmatter", formatoptions:{onclick:"showdetail"}, width: 150, resizable: true,sortable:false},
			           {name: "intStaType", index:"type" ,width:70,resizable:true,formatter:'select',editoptions:staType},
			           {name: "intStaStatus", index:"intStaStatus" ,width:70,resizable:true,formatter:'select',editoptions:status},
			           {name: "owner", index: "owner", width: 80, resizable: true,sortable:false},
		               {name: "sumnumber", index: "sumnumber", width: 100, resizable: true,sortable:false}],
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
		colNames: ["ID","STAID","SKUID","OWNER","skuCost",i18("ENTITY_SKU_BARCODE"),i18("ENTITY_SKU_JMCODE"),i18("ENTITY_SKU_KEYPROP"),
			i18("ENTITY_SKU_NAME"),i18("ENTITY_SKU_SUPCODE"),"数量","库存状态"],
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

	$j("#import").click(function(){
		var file=$j.trim($j("#staFile").val()),errors=[];
		if(file==""||file.indexOf("xls")==-1){
			errors.push(i18("INVALID_MAND",i18("ENTITY_EXCELFILE")));
		}
		var url = $j("body").attr("contextpath") + "/warehouse/importForBatchReceiving.do";
		if(errors.length>0){
			jumbo.showMsg(errors.join("<br/>"));
		}else{
			loxia.lockPage();
			$j("#importFormB").attr("action",loxia.getTimeUrl(url));
			$j("#importFormB").submit();
		}
	});
	$j("#import1").click(function(){
		var file=$j.trim($j("#staFile1").val()),errors=[];
		if(file==""||file.indexOf("xls")==-1){
			errors.push(i18("INVALID_MAND",i18("ENTITY_EXCELFILE")));
		}
		var url = $j("body").attr("contextpath") + "/warehouse/importForBatchReceiving.do";
		if(errors.length>0){
			jumbo.showMsg(errors.join("<br/>"));
		}else{
			loxia.lockPage();
			$j("#importFormC").attr("action",loxia.getTimeUrl(url));
			$j("#importFormC").submit();
		}
	});
	$j("#import2").click(function(){
		var file=$j.trim($j("#staFile2").val()),errors=[];
		if(file==""||file.indexOf("xls")==-1){
			errors.push(i18("INVALID_MAND",i18("ENTITY_EXCELFILE")));
		}
		
		var url = $j("body").attr("contextpath") + "/warehouse/importNewForBatchReceiving.do";
		if(errors.length>0){
			jumbo.showMsg(errors.join("<br/>"));
		}else{
			loxia.lockPage();
			$j("#importFormD").attr("action",loxia.getTimeUrl(url));
			$j("#importFormD").submit();
		}
	});
	$j("#import3").click(function(){
		var file=$j.trim($j("#staFile3").val()),errors=[];
		if(file==""||file.indexOf("xls")==-1){
			errors.push(i18("INVALID_MAND",i18("ENTITY_EXCELFILE")));
		}
		
		var url = $j("body").attr("contextpath") + "/warehouse/importNewForBatchReceiving.do";
		if(errors.length>0){
			jumbo.showMsg(errors.join("<br/>"));
		}else{
			loxia.lockPage();
			$j("#importFormB").attr("action",loxia.getTimeUrl(url));
			$j("#importFormB").submit();
		}
	});
});

function importReturn(){
	window.location=loxia.getTimeUrl($j("body").attr("contextpath")+"/warehouse/importTemplateBatReceiving.do");
}
