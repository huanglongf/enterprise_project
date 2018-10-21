$j.extend(loxia.regional['zh-CN'],{
	SKUCODE : "SKU编码",
	BARCODE : "条形码",
	PLANQTY : "计划数量",
	GQTY : "申报数量",
	GUNIT : "申报单位",
	DECLPRICE : "单价",
	ISMANUALADD : "是否手工添加",
		
    
  
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function showMsg(msg){
	jumbo.showMsg(msg);
}

function editSta(obj){//编辑功能
		var id = $j(obj).parents("tr").attr("id");
		var pl=$j("#tbl-baoShuiOutStaList").jqGrid("getRowData",id);
		$j("#editCustomsDeclaration").dialog("open");
		$j("#wmsCode").val(pl["wmsCode"]);
		$j("#grossWt").val(pl["grossWt"]);
		$j("#wrapType").val(pl["wrapType"]);
		$j("#licensePlateNumber").val(pl["licensePlateNumber"]);
		$j("#customsDeclarationDtoId").val(id);
		$j("#grossWt").focus();
};

function showDetail(obj){
	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");
	var tl=$j("#tbl-baoShuiOutStaList").jqGrid("getRowData",id);
	$j("#div-sta-list").addClass("hidden");
	$j("#div-sta-detail").removeClass("hidden");
	$j("#pmFile").val("");
	$j("#div-sta-detail").attr("staId",id);
	$j("#imp_staId").val(id);
	$j("#code").text(tl["wmsCode"]);
	$j("#slipCode").text(tl["slipCode"]);
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-sta-line").jqGrid('setGridParam',{page:1,url:baseUrl + "/queryBaoShuiOutStaLineList.json?sta.id=" + id}).trigger("reloadGrid");
	var pl=$j("#tbl-staList").jqGrid("getRowData",id);
	if(pl["intStaType"]==61){
		$j("#canceled").addClass("hidden");
	} else {
		$j("#canceled").removeClass("hidden");
	}
	if(pl["intStaStatus"] == 2 && (pl["intStaType"] == 62 || pl["intStaType"] == 64)) {
		$j("#packing").removeClass("hidden");
	} else {
		$j("#packing").addClass("hidden");
	}
	if(pl["intStaStatus"] == 8 && (pl["intStaType"] == 62 || pl["intStaType"] == 64)) {
		$j("#cancelPacking").removeClass("hidden");
	} else {
		$j("#cancelPacking").addClass("hidden");
	}
	$j("#div-sta-detail").attr("staType",pl["intStaType"]);
	$j("#div-sta-detail").attr("staStatus",pl["intStaStatus"]);
	initButtion(pl["intStaStatus"],"");
	$j("#createTime").text(pl["createTime"]);
	$j("#finishTime").text(pl["finishTime"]);
	$j("#code").text(pl["code"]);
	$j("#slipCode").text(pl["slipCode"]);
	$j("#shopId").text(pl["channelName"]);
	$j("#sta_memo").text(pl["memo"]);
	$j("#operator").text(pl["creater"]);
	$j("#receiver").text(pl["receiver"]);
	$j("#telephone").text(pl["telephone"]);
	$j("#mobile").text(pl["mobile"]);
	$j("#address").text(pl["address"]);
	$j("#status").text(tr.find("td[aria-describedby$='intStaStatus']").text());
	$j("#sta_type").text(tr.find("td[aria-describedby$='intStaType']").text());
	$j("#tnFile").val("");
	$j("#memo").val("");
}

function initButtion(status,statusName){
	if(status == 1){
		$j("#tr_II").addClass("hidden");
		$j("#tr_I").removeClass("hidden");
		$j("#canceled").removeClass("hidden");
	} else if(status == 2){
		$j("#tr_II").removeClass("hidden");
		$j("#tr_I").removeClass("hidden");
		$j("#canceled").removeClass("hidden");
	} else if(status == 8){
		$j("#tr_II").removeClass("hidden");
		$j("#tr_I").removeClass("hidden");
		$j("#canceled").removeClass("hidden");
	}
	else {
		$j("#tr_II").addClass("hidden");
		$j("#tr_I").addClass("hidden");
		$j("#canceled").removeClass("hidden").addClass("hidden");
	}
	if(statusName != ""){
		$j("#status").text(statusName);
	}
}

function editDeclarationLine(obj){
		$j("#editCustomsDeclarationLine").dialog("open");
		var tr = $j(obj).parents("tr[id]");
		var id=tr.attr("id");
		var tl=$j("#tbl-sta-line").jqGrid("getRowData",id);
		$j("#skuCode1").val(tl["skuCode"]);
		$j("#customsDeclarationDtoLineId").val(id);
		$j("#gQty").val(tl["gQty"]);
		$j("#gQty").focus();
}

function refreshStaList(){
	var url = $j("body").attr("contextpath") + "/queryBaoShuiOutStaList.json";
	$j("#tbl-baoShuiOutStaList").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
}

$j(document).ready(function (){
	initShopQuery("companyshop","innerShopCode");
	jumbo.loadShopList("companyshop");
	$j("#editCustomsDeclarationLine").dialog({title: "编辑", modal:true, autoOpen: false, width: 360, height: 360});
	$j("#editCustomsDeclaration").dialog({title: "编辑", modal:true, autoOpen: false, width: 360, height: 360});
	var baseUrl = $j("body").attr("contextpath");
	//var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	//var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
//	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	$j("#tbl-baoShuiOutStaList").jqGrid({
		datatype: "json",
		colNames: ["ID","PO单号","相关单据号","平台订单号","创建时间","店铺","毛重(千克)","净重(千克)","总件数","单据类型","包装种类","车牌号","配载单号","申报数量变更","报关状态","明细状态","作业单状态","编辑","推送"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name:"wmsCode", index:"WMS_CODE",formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, width:100, resizable:true},//作业单号
					{name:"slipCode",index:"slip_code",width:80,resizable:true},//相关单据号
					{name:"slipCode1",index:"platform_code",width:80,resizable:true},//平台订单号
					{name:"createTime",index:"create_time",width:100,resizable:true},//创建时间
					{name:"owner",index:"owner",width:80,resizable:true},//店铺
					{name:"grossWt", index:"gross_wt",width:40,resizable:true},//毛重(千克)
					{name:"netWt", index:"net_wt",width:40,resizable:true},//净重(千克)
					{name:"packNo", index:"pack_no",width:40,resizable:true},//总件数
					//{name:"intStaType", index:"intStaType" ,width:70,resizable:true,formatter:'select',editoptions:staType},//单据类型
					{name:"wmsType", index:"wms_type",width:40,resizable:true},//单据类型
					{name:"wrapType", index:"wrap_type",width:40,resizable:true},//包装种类
					//{name:"intStaType", index:"intStaType" ,width:70,resizable:true,formatter:'select',editoptions:staType},//包装种类
					{name:"licensePlateNumber", index:"LICENSE_PLATE_NUMBER",width:60,resizable:true},//车牌号
					{name:"prestowageNo", index:"PRESTOWAGE_NO",width:110,resizable:true},//配载单号
					{name:"isToModify", index:"IS_TO_MODIFY",width:70,resizable:true},//申报数量变更
					{name:"status", index:"STATUS" ,width:30,resizable:true},//报关状态
					{name:"status", index:"STATUS" ,width:30,resizable:true},//明细状态
					//{name:"status", index:"STATUS" ,width:70,resizable:true,formatter:'select',editoptions:staType},//报关状态
					//{name:"status", index:"STATUS" ,width:70,resizable:true,formatter:'select',editoptions:staType},//明细状态
				//	{name:"intStaType", index:"intStaType" ,width:70,resizable:true,formatter:'select',editoptions:staType},//作业单状态
					{name:"wmsStatus", index:"wms_status" ,width:70,resizable:true},//作业单状态
		            {name:"btn1",width:50,resizable:true},//编辑
		            {name:"btn2",width:50,resizable:true}//推送
					],
		caption: "保税仓出库列表",
	  	sortname: 'd.create_time',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#pager",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
   		width:1600,
		jsonReader: { repeatitems : false, id: "0" },
		loadComplete: function(){
			var btn1 = '<div><button type="button" style="width:100px;" class="confirm" id="print" name="btnDel" loxiaType="button" onclick="editSta(this);">'+"编辑"+'</button></div>';
			var btn2 = '<div><button type="button" style="width:100px;" class="confirm" id="print" name="btnDel" loxiaType="button" onclick="tuiSta(this);">'+"推送"+'</button></div>';
			var ids = $j("#tbl-baoShuiOutStaList").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				$j("#tbl-baoShuiOutStaList").jqGrid('setRowData',ids[i],{"btn1":btn1});
				$j("#tbl-baoShuiOutStaList").jqGrid('setRowData',ids[i],{"btn2":btn2});
			}
			loxia.initContext($j(this));
		}
	});
	
	
	$j("#tbl-sta-line").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("SKUCODE"),i18("BARCODE"),i18("PLANQTY"),i18("GQTY"),i18("GUNIT"),i18("DECLPRICE"),i18("ISMANUALADD"),"编辑"],
		colModel: [{name: "id", index: "id", hidden: true},
		           	{name:"skuCode",index:"skuCode",width:120,resizable:true},
					{name:"upc",index:"upc",width:120,resizable:true},
					{name:"planQty", index:"planQty" ,width:120,resizable:true},
					{name:"gQty", index:"gQty",width:120,resizable:true},
					{name:"gUnit",index:"gUnit",width:120,resizable:true},
					{name:"declPrice",index:"declPrice",width:120,resizable:true},
					{name:"isManualAdds", index:"isManualAdds",width:80,resizable:true},
					{name:"btn1",width:100,resizable:true}//编辑
					],
		caption: "作业明细单",
	   	sortname: 'ID',
	    multiselect: false,
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pagerDetail",
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
   		width:1300,
		jsonReader: { repeatitems : false, id: "0" },
		loadComplete: function(){
			var btn1 = '<div><button type="button" style="width:100px;" class="confirm" id="print" name="btnDel" loxiaType="button" onclick="editDeclarationLine(this);">'+"编辑"+'</button></div>';
			var ids = $j("#tbl-sta-line").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				$j("#tbl-sta-line").jqGrid('setRowData',ids[i],{"btn1":btn1});
			}
			loxia.initContext($j(this));
		}
	});
	
	$j("#reset").click(function(){
		$j("#queryForm input").val("");
		$j("#queryForm select").val("");
	});
	
	$j("#reset2").click(function(){//重置明细行2
		$j("#queryForm2 input").val("");
		$j("#queryForm2 select").val("");
	});
	
	
	$j("#search").click(function(){
		refreshStaList();
	});
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	
	$j("#break").click(function(){
		$j("#div-sta-list").removeClass("hidden");
		$j("#div-sta-detail").addClass("hidden");
	});
	
	
	$j("#addSku").click(function(){//添加明细行
		$j("#shopQueryDialog2 input,#shopQueryDialog2 select").val("");
		$j("#shopQueryDialog2").show();
		$j("#shopQueryDialog2").dialog("open");
	});
	
	//查询作业明细单
	$j("#search2").click(function(){
		var postData={};
		postData["sta.id"]=$j("#imp_staId").val();
		postData["sta.skuCode"]=$j("#skuCode").val();
		postData["sta.barCode"]=$j("#skuUpc").val();
		var url = $j("body").attr("contextpath") + "/queryBaoShuiOutStaLineList.json";
		$j("#tbl-sta-line").jqGrid('setGridParam',{url:url,page:1,postData:postData}).trigger("reloadGrid");
	});
	
	//更改入库报关头
	$j("#savecus").click(function(){
		var postData={};
		postData["cdd.grossWt"]=$j("#grossWt").val();
		postData["cdd.wrapType"]=$j("#wrapType").val();
		postData["cdd.licensePlateNumber"]=$j("#licensePlateNumber").val();
		postData["cdd.id"]=$j("#customsDeclarationDtoId").val();
		var url = $j("body").attr("contextpath") + "/queryBaoShuiOutStaList.json";
		//var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/updateBaoShuiOutSta.do");
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/updateBaoShuiOutSta.json",postData);
		if(result.msg=="success"){
			$j("#tbl-baoShuiOutStaList").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
			$j("#editCustomsDeclaration").dialog("close");
			jumbo.showMsg("编辑成功");
		}else{
			jumbo.showMsg("编辑失败，请稍后再试！");
		}
	});
	//更改入库报关明细
	$j("#save").click(function (){
		var postData={};
		postData["sta.id"]=$j("#imp_staId").val();
		postData["sta.skuCode"]=$j("#skuCode").val();
		postData["sta.barCode"]=$j("#skuUpc").val();
		var id=$j("#customsDeclarationDtoLineId").val();
		var gQty=$j("#gQty").val();
		var url = $j("body").attr("contextpath") + "/queryBaoShuiOutStaLineList.json";
		var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/updateBaoShuiOutStaLineById.do?customsDeclarationDtoLineId="+id+"&gQty="+gQty);
		if(result.msg=="success"){
			$j("#tbl-sta-line").jqGrid('setGridParam',{url:url,page:1,postData:postData}).trigger("reloadGrid");
			$j("#editCustomsDeclarationLine").dialog("close");
			jumbo.showMsg("编辑成功");
		}else{
			jumbo.showMsg("编辑失败，请稍后再试！");
		}
	});
	$j("#closediv").click(function (){
		$j("#editCustomsDeclarationLine").dialog("close");
	});
	
	$j("#closediv2").click(function (){
		$j("#editCustomsDeclaration").dialog("close");
	});
	
	$j("#countGrossWtNetWt").click(function (){
		var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/countWeight.do");
		if(result.msg=="success"){
			jumbo.showMsg("计算成功");
		}else{
			jumbo.showMsg("计算失败，请稍后再试！");
		}
		
	});
});


