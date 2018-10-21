$j.extend(loxia.regional['zh-CN'],{
	SURE_CANCEL : "您确定要作废？",
	CANCEL_FAILED : "作废失败",
	SELECT_PLEASE : "请选择",
	NEW_LPCODE_SELECT : "请选择新物流供应商",
	CORRECT_FILE_PLEASE : "请选择正确的Excel导入文件",
	
	TRACKINGNO : "快递单号",
	MSG : "信息",
	REMOVE_INFO : "移除信息",
	
	TRACKINGNO : "快递单号",
	OWNER : "平台店铺ID",
	REFSLIPCODE : "相关单据号",
	RECEIVER : "收货人",
	ADDRESS : "收货地址",
	WEIGHT : "包裹重量",
	OUTBOUNDTIME : "扫描出库时间",
	ORDER_LIST : "作业单列表",
	
	//colNames: ["ID",],
	//colNames: ["ID","","","","","","","",""],
	STATUS : "status",
	TRACKINGNO : "快递单号",
	REFSLIPCODE : "相关单据号",
	WEIGHT : "包裹重量",
	RECEIVER : "收货人",
	OUTBOUNDTIME : "扫描出库时间",
	LINEINTSTATUS : "状态",
	IDFORBTN : "操作",
	CANCEL : "作废",
	DETAIL : "明细",
	
	LIST_CREATE_FAILED : "交货清单创建失败",
	SUCCESS : "交接成功",
	FAILED : "交接失败",
	OPERATING : "物流交接清单保存及打印中，请等待..."
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

function removeCancelHoLineBtn(){
	$j("#tbl-detial tr[id]").each(function(idx,tag){
		var id = $j(tag).attr("id");
		var data=$j("#tbl-detial").jqGrid("getRowData",id)
		if(data["lineIntStatus"] == '0'){
			$j(tag).find("button").remove();
		}
	});
}


//作废交接清单明细行
function cancelDetial(tag,event){
	if ( event && event.stopPropagation ){
		event.stopPropagation();
	}else{
		window.event.cancelBubble = true; 
	}
	if(!window.confirm(i18("SURE_CANCEL"))){//您确定要作废？
		return false;
	}
	var id = $j(tag).parents("tr").attr("id");
	var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/cancelhoListLine.json",{"hoListLineId":id});
	if(result && result.result == "success"){
		$j("#tbl-detial").jqGrid('setGridParam',{url:window.$j("body").attr("contextpath")+"/findlinebyholistid.json",page:1,postData:{"hoListId":$j("#hidHoId").val()}}).trigger("reloadGrid");
		removeCancelHoLineBtn();
		var postData = {};
		postData["hoListId"] = $j("#hidHoId").val();
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/findhandoverlistbyid.json",postData);
		if(result && result.handoverlist){
			$j("#hoPackageCount").html(result.handoverlist.packageCount);
			$j("#hoTotalWeight").html(result.handoverlist.totalWeight);
		}
	}else if(result && result.result == "error"){
		jumbo.showMsg(result.message);
	}else{
		jumbo.showMsg(i18("CANCEL_FAILED"));//作废失败
	}
}

$j(document).ready(function (){
	
	$j("#tabs").tabs();
	
	
	$j("#selTrans").change(function(){
		var html = $j("#selTrans option:selected").html();
		if(html != i18("SELECT_PLEASE")){//请选择
			$j("#searchFile").removeClass("hidden");
		}
	});	
	
	//import
	$j("#import").click(function(){
		var selTrans = $j("#selTrans").val();
		var file = $j("#tnFile").val();
		var errors = [];
		if(selTrans == ""){
			errors.push(i18("NEW_LPCODE_SELECT"));//请选择物流供应商
		}
		
		if(file==""){
			errors.push(i18("CORRECT_FILE_PLEASE"));//请选择正确的Excel导入文件
		}else{
			var postfix = file.split(".")[1];
			if(postfix != "xls"){
				errors.push(i18("CORRECT_FILE_PLEASE"));//请选择正确的Excel导入文件
			}
		}
		
		if(errors.length > 0){
			jumbo.showMsg(errors.join("<br />&nbsp; &nbsp; &nbsp;"));
			return;
		}
		
		$j("#divRemoveInfo").removeClass("hidden");
		$j("#buttonList").removeClass("hidden");
		$j("#importForm").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importTrackingNo.do"));
		loxia.submitForm("importForm");
	});
	
	
	$j("#tbl-removeInfo").jqGrid({
		datatype: "json",
		//colNames: ["ID","快递单号","信息"],
		colNames: ["ID",i18("TRACKINGNO"),i18("MSG")],
		colModel: [
		           {name: "id", index: "id", hidden: true},		         
			       {name: "trackingNo",width: 200, resizable: true},
		           {name: "msg",width: 300, resizable: true}],
	    caption: i18("REMOVE_INFO"),//移除信息
	    rowNum: -1,
	    height:"auto",
		multiselect: false,
		viewrecords: true,
   	rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});

	$j("#tbl-goodsList").jqGrid({
			datatype: "json",
			//colNames: ["ID","快递单号","平台店铺ID","相关单据号","收货人","收货地址","包裹重量","扫描出库时间"],
			colNames: ["ID",i18("TRACKINGNO"),i18("OWNER"),i18("REFSLIPCODE"),i18("RECEIVER"),i18("ADDRESS"),i18("WEIGHT"),i18("OUTBOUNDTIME")],
			colModel: [
		           {name: "id", index: "id", hidden: true},		         
			       {name: "trackingNo",width: 150, resizable: true},
		           {name: "owner",width: 150, resizable: true},
		           {name: "refSlipCode", width: 150, resizable: true},
		           {name: "receiver",width: 100, resizable: true},
		           {name: "address", index: "address", width: 200, resizable: true},
		           {name: "weight",  width: 100, resizable: true},
		           {name: "outboundTime", width: 150, resizable: true}],
			caption: i18("ORDER_LIST"),//作业单列表
			rowNum:-1,
			sortname: 'trackingNo',
			height:"auto",
			multiselect: false,
			viewrecords: true,
   			rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" }
	});

	var status=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"handoverListLineStatus"});
	$j("#tbl-detial").jqGrid({
		datatype: "json",
		//colNames: ["ID","status","快递单号","相关单据号","包裹重量","收货人","扫描出库时间","状态","操作"],
		colNames: ["ID",i18("STATUS"),i18("TRACKINGNO"),i18("REFSLIPCODE"),i18("WEIGHT"),i18("RECEIVER"),i18("OUTBOUNDTIME"),i18("LINEINTSTATUS"),i18("IDFORBTN")],
		colModel: [{name: "id", index: "id", hidden: true},
		            {name:"lineIntStatus", hidden: true},
					{name :"trackingNo",index:"trackingNo",width:100,resizable:true},
					{name: "refSlipCode", index:"refSlipCode",width:100,resizable:true},
					{name:"weight", index:"weight", width:90, resizable:true},
					{name:"receiver",index:"receiver",width:90,resizable:true},
					{name:"outboundTime",index: "outboundTime",width:150,resizable:true},
					{name:"lineIntStatus", index:"lineIntStatus" ,width:60,resizable:true,formatter:'select',editoptions:status},
					{name: "idForBtn", width: 120,resizable:true, formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("CANCEL"), onclick:"cancelDetial(this,event);"}}}],//作废
		caption: i18("DETAIL"),//明细
		rowNum:-1,
		//rowList:[10,20,30],
		sortname: 'id',
		multiselect: false,
		height:"auto",
		sortorder: "desc",
		viewrecords: true,
   	rownumbers:true,
		gridComplete : function(){
			removeCancelHoLineBtn();
			loxia.initContext($j(this));
		},
		jsonReader: { repeatitems : false, id: "0" }
	});
	

	$j("#createOrder").click(function(){
		var postData = {};
		postData["lpcode"] = $j("#selTrans").val();
		$j("#tbl-goodsList tr[id]").each(function(i,tag){
			postData['packageIds['+i+']'] = $j(tag).attr("id");
		});
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/createHandOverList.json",postData);
		if(result && result.result == "success"){
			var ho = result.ho;
			$j("#tbl-detial").jqGrid('setGridParam',{url:window.$j("body").attr("contextpath")+"/findlinebyholistid.json",page:1,postData:{"hoListId":ho["id"]}}).trigger("reloadGrid");
			$j("#hoCode").html(ho["code"]);
			$j("#hidHoId").val(ho["id"]);
			$j("#hoPackageCount").html(ho["packageCount"]);
			$j("#hoCreateTime").html(ho["createTime"]);
			$j("#hoTotalWeight").html(ho["totalWeight"]);
			$j("#tblHandoverInfo").attr("hoId",ho["id"]);
			$j("#searchInfo").addClass("hidden");
			$j("#confirm_ho_form_div").removeClass("hidden");
		}else if(result && result.result == "error"){
			jumbo.showMsg(result.message);
		}else{
			jumbo.showMsg(i18("LIST_CREATE_FAILED"));//交货清单创建失败
		}
	});
	
	$j("#back").click(function(){
		var se = $j("#select option:eq(0)").val();
		$j("#confirm_ho_form input").val("");
		$j("#selTrans").val("");
		$j("#tnFile").val("");
		$j("#packageCount").text("");
		$j("#weight").text("");
		$j("#tbl-goodsList tr[id]").remove();
		$j("#searchFile").addClass("hidden");
		$j("#searchInfo").removeClass("hidden");
		$j("#confirm_ho_form_div").addClass("hidden");
		$j("#divRemoveInfo").addClass("hidden");
		$j("#tbl-removeInfo tr[id]").remove();
		$j("#hidHoId").val("");
		$j("#btnDetialHo").removeClass("hidden");
		$j("#tblHandoverSuccess").addClass("hidden");
		$j("#tblHandoverInput").removeClass("hidden");
	});
	
	//交接
	$j("#btnDetialHo").click(function(){
		var errors=loxia.validateForm("confirm_ho_form");
		if(errors.length>0){
			jumbo.showMsg(errors);
			return false;
		}
		var postData = loxia._ajaxFormToObj("confirm_ho_form");
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/handOver.json",postData);
		if(result && result.result == "success"){
			var ho = result.ho;
			$j("#partyAOperator").html(ho["partyAOperator"]);
			$j("#partyAMobile").html(ho["paytyAMobile"]);
			$j("#partyBOperator").html(ho["partyBOperator"]);
			$j("#paytyBMobile").html(ho["paytyBMobile"]);
			$j("#paytyBPassPort").html(ho["paytyBPassPort"]);
			$j("#btnDetialHo").addClass("hidden");
			$j("#tblHandoverSuccess").removeClass("hidden");
			$j("#tblHandoverInput").addClass("hidden");
			$j("#tbl-detial tr button").remove();
			jumbo.showMsg(i18("SUCCESS"));//交接成功
		}else if(result && result.result == "error"){
			jumbo.showMsg(result.message);
		}else{
			jumbo.showMsg(i18("FAILED"));//交接失败
		}
	});
	
	// 交接查询- 保存及打印
	$j("#btnDetialPrint").click(function(){
		var errors=loxia.validateForm("confirm_ho_form");
		if(errors.length>0){
			jumbo.showMsg(errors);
			return false;
		}
		var postData = loxia._ajaxFormToObj("confirm_ho_form");
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/savehandoverlistinfo.json",postData);
		if(rs && rs.result == 'success'){
			//保存成功->打印
			jumbo.showMsg(i18("OPERATING"));
			var hlid = $j("#hidHoId").val();
			var url = $j("body").attr("contextpath") + "/json/printhandoverlist.json?hoListId=" + hlid;
			printBZ(loxia.encodeUrl(url),true);			 
		}else{
			jumbo.showMsg(rs.message);
		}
	});
	
	$j("#wrapStuff").keydown(function(event){
		if(event.keyCode == 13){
			event.preventDefault();
			if ($j("#wrapStuff").val() != null){
				addBarCode();
			}else{
				$j("#wrapStuff").focus();
			}
		}
	}); 
	$j("#createOrderByHand").click(function(){
		if (!packageIds.length  >0){
			jumbo.showMsg("数据为空，操作异常");
			return ;
		}
		
		//交接单检查 fanht
		if(checkTransNo()>0){
			return;
		}
		
		var postData = {};
		//物流编号fanht
		postData["lpcode"] = selTrans;
		//物流信息主键列表
		for(i=0;i<packageIds.length;i++){
			postData['packageIds['+i+']'] = packageIds[i];
		}
		//创建交货清单，删除中间表数据 fanht
		//var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/createHandOverList.json",postData);
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/createHandOverListDelete.json",postData);
		if(result && result.result == "success"){
			var ho = result.ho;
			$j("#tbl-detial2").jqGrid('setGridParam',{url:window.$j("body").attr("contextpath")+"/findlinebyholistid.json",page:1,postData:{"hoListId":ho["id"]}}).trigger("reloadGrid");
			$j("#hoCode2").html(ho["code"]);
			$j("#hidHoId2").val(ho["id"]);
			$j("#hoPackageCount2").html(ho["packageCount"]);
			$j("#hoCreateTime2").html(ho["createTime"]);
			$j("#hoTotalWeight2").html(ho["totalWeight"]);
			$j("#tblHandoverInfo2").attr("hoId",ho["id"]);
			$j("#outbound").addClass("hidden");
			$j("#confirm_ho_form_div2").removeClass("hidden");
		}else if(result && result.result == "error"){
			//存在已创建交接单的单子，重新初始化未交接列表 fanht
			initOutBoundPack();
			jumbo.showMsg(result.message+"，重新加载未交接列表");
		}else{
			jumbo.showMsg(i18("LIST_CREATE_FAILED"));//交货清单创建失败
		}
	});
	
	//交接返回fanht
	$j("#back2").click(function(){
		//显示、隐藏
		$j("#confirm_ho_form_div2").addClass("hidden");
		$j("#outbound").removeClass("hidden");
		//清空相关数据
		packageIds = new Array();
		transNoList = new Array();
		handoverCount = 0;
		$j("#handoverCount").html(handoverCount);
	});
	
	//交接
	$j("#btnDetialHo2").click(function(){
		var errors=loxia.validateForm("confirm_ho_form2");
		if(errors.length>0){
			jumbo.showMsg(errors);
			return false;
		}
		var postData = loxia._ajaxFormToObj("confirm_ho_form2");
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/handOver.json",postData);
		if(result && result.result == "success"){
			var ho = result.ho;
			$j("#partyAOperator2").html(ho["partyAOperator"]);
			$j("#partyAMobile2").html(ho["paytyAMobile"]);
			$j("#partyBOperator2").html(ho["partyBOperator"]);
			$j("#paytyBMobile2").html(ho["paytyBMobile"]);
			$j("#paytyBPassPort2").html(ho["paytyBPassPort"]);
			$j("#btnDetialHo2").addClass("hidden");
			$j("#tblHandoverSuccess2").removeClass("hidden");
			$j("#tblHandoverInput2").addClass("hidden");
			$j("#tbl-detial2 tr button").remove();
			jumbo.showMsg(i18("SUCCESS"));//交接成功
		}else if(result && result.result == "error"){
			jumbo.showMsg(result.message);
		}else{
			jumbo.showMsg(i18("FAILED"));//交接失败
		}
	});
	
	// 交接单打印 fanht
	$j("#btnDetialPrint2").click(function(){
		var hlid = $j("#hidHoId2").val();
		var url = $j("body").attr("contextpath") + "/json/printhandoverlist.json?hoListId=" + hlid;
		printBZ(loxia.encodeUrl(url),true);
	});
	
	$j("#tbl-removeInfo2").jqGrid({
		datatype: "json",
		//colNames: ["ID","快递单号","信息"],
		colNames: ["ID",i18("TRACKINGNO"),i18("MSG")],
		colModel: [
		           {name: "id", index: "id", hidden: true},		         
			       {name: "trackingNo",width: 200, resizable: true},
		           {name: "msg",width: 300, resizable: true}],
	    caption: i18("REMOVE_INFO"),//移除信息
	    rowNum: -1,
	    height:"auto",
		multiselect: false,
		viewrecords: true,
   	rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});

	$j("#tbl-goodsList2").jqGrid({
			datatype: "json",
			//colNames: ["ID","快递单号","平台店铺ID","相关单据号","收货人","收货地址","包裹重量","扫描出库时间"],
			colNames: ["ID",i18("TRACKINGNO"),i18("OWNER"),i18("REFSLIPCODE"),i18("RECEIVER"),i18("ADDRESS"),i18("WEIGHT"),i18("OUTBOUNDTIME")],
			colModel: [
		           {name: "id", index: "id", hidden: true},		         
			       {name: "trackingNo",width: 150, resizable: true},
		           {name: "owner",width: 150, resizable: true},
		           {name: "refSlipCode", width: 150, resizable: true},
		           {name: "receiver",width: 100, resizable: true},
		           {name: "address", index: "address", width: 200, resizable: true},
		           {name: "weight",  width: 100, resizable: true},
		           {name: "outboundTime", width: 150, resizable: true}],
			caption: i18("ORDER_LIST"),//作业单列表
			rowNum:-1,
			sortname: 'trackingNo',
			height:"auto",
			multiselect: false,
			viewrecords: true,
   			rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" }
	});

	var status=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"handoverListLineStatus"});
	$j("#tbl-detial2").jqGrid({
		datatype: "json",
		//colNames: ["ID","status","快递单号","相关单据号","包裹重量","收货人","扫描出库时间","状态","操作"],
		colNames: ["ID",i18("STATUS"),i18("TRACKINGNO"),i18("REFSLIPCODE"),i18("WEIGHT"),i18("RECEIVER"),i18("OUTBOUNDTIME"),i18("LINEINTSTATUS"),i18("IDFORBTN")],
		colModel: [{name: "id", index: "id", hidden: true},
		            {name:"lineIntStatus", hidden: true},
					{name :"trackingNo",index:"trackingNo",width:100,resizable:true},
					{name: "refSlipCode", index:"refSlipCode",width:100,resizable:true},
					{name:"weight", index:"weight", width:90, resizable:true},
					{name:"receiver",index:"receiver",width:90,resizable:true},
					{name:"outboundTime",index: "outboundTime",width:150,resizable:true},
					{name:"lineIntStatus", index:"lineIntStatus" ,width:60,resizable:true,formatter:'select',editoptions:status},
					{name: "idForBtn", width: 120,resizable:true, formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("CANCEL"), onclick:"cancelDetialTab2(this,event);"}}}],//作废
		caption: i18("DETAIL"),//明细
		rowNum:-1,
		//rowList:[10,20,30],
		sortname: 'id',
		multiselect: false,
		height:"auto",
		sortorder: "desc",
		viewrecords: true,
   	rownumbers:true,
		gridComplete : function(){
			removeCancelHoLineBtn();
			loxia.initContext($j(this));
		},
		jsonReader: { repeatitems : false, id: "0" }
	});
	
});

//创建交接单检查 fanht
function checkTransNo(){
	var postData={};
	for(i=0;i<transNoList.length;i++){
		postData['transNo['+i+']'] = transNoList[i];
	}
	postData["lpcode"] = selTrans;
	var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/generatehandoverlistbyhand.json",postData);
	 if (rs && rs.result && rs.result == "success"){
		var data = rs.hoList;
		var removeByTrackingNo = rs.removeByTrackingNo;
		var removeBylpcode = rs.removeBylpcode;
		var removeBySta = rs.removeBySta;
		var i = 0;
		var msg = "";
	    if(removeByTrackingNo != null){
		    for(var d in removeByTrackingNo){
		        i++;
		    	//$j("#tbl-removeInfo2").jqGrid('addRowData',i,{"trackingNo":removeByTrackingNo[d],"msg":"快递单号对应作业单不存在或已创建交接清单"});	
		        msg = msg + removeByTrackingNo[d] + ":" + "快递单号对应作业单不存在或已创建交接清单</br>";
		   	}
	    }
	    if(removeBylpcode != null){
		    for(var d in removeBylpcode){
		        i++;
		    	//$j("#tbl-removeInfo2").jqGrid('addRowData',i,{"trackingNo":removeBylpcode[d],"msg":"物流交接商与快递单不匹配或作业单已完成"});	
		        msg = msg + removeBylpcode[d] + ":" + "物流交接商与快递单不匹配或作业单已完成</br>";
		   	}
	    }
	    if(removeBySta != null){
		    for(var d in removeBySta){
		        i++;
		    	//$j("#tbl-removeInfo2").jqGrid('addRowData',i,{"trackingNo":removeBySta[d],"msg":"作业单不存在或其中非所有快递单被交接"});	
		        msg = msg + removeBySta[d] + ":" + "快递单号非交接状态或者已完成交接</br>";
		   	}
	    }
	    
	}else {
		jumbo.showMsg("操作异常");
		 i++;
	}
	 if(i>0){
		 jumbo.showMsg(msg);
	 }
	 return i;
}

function addBarCode(){
	var varCode = jQuery.trim($j("#wrapStuff").val());
	if(varCode != ""){
		// 判断是否为耗材
		var result = loxia.syncXhrPost(loxia.getTimeUrl(window.$j("body").attr("contextpath")+"/getSkuTypeByBarCode.json?barCode="+varCode));
		if (!result || result.value != 1){
			$j("#wrapStuff").val("");
			jumbo.showMsg("该包材不存在或为非耗材!");
			return;
		}
		var isAdd = true;
		$j("#barCode_tab tbody tr").each(function (i,tag){
			if(varCode == $j(tag).find("td:eq(1) :input").val()){
				jumbo.showMsg("快递单号已存在.");
				isAdd = false;
			}
		});
		if(isAdd){
			$j("button[action=add]").click();
			var length = $j("#barCode_tab tbody tr").length-1;
			$j("#barCode_tab tbody tr:eq("+length+") td:eq(1) :input").val(varCode);
		}
	}
	if($j("#barCode_tab tbody tr").length > 0){
		$j("#wrapStuff").val("");
	}else {
		jumbo.showMsg("快递单号不能为空");
	}
}
//作废交接清单明细行
function cancelDetialTab2(tag,event){
	if ( event && event.stopPropagation ){
		event.stopPropagation();
	}else{
		window.event.cancelBubble = true; 
	}
	if(!window.confirm(i18("SURE_CANCEL"))){//您确定要作废？
		return false;
	}
	var id = $j(tag).parents("tr").attr("id");
	var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/cancelhoListLine.json",{"hoListLineId":id});
	if(result && result.result == "success"){
		$j("#tbl-detial2").jqGrid('setGridParam',{url:window.$j("body").attr("contextpath")+"/findlinebyholistid.json",page:1,postData:{"hoListId":$j("#hidHoId2").val()}}).trigger("reloadGrid");
		removeCancelHoLineBtn();
		var postData = {};
		postData["hoListId"] = $j("#hidHoId2").val();
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/findhandoverlistbyid.json",postData);
		if(result && result.handoverlist){
			$j("#hoPackageCount2").html(result.handoverlist.packageCount);
			$j("#hoTotalWeight2").html(result.handoverlist.totalWeight);
		}
		jumbo.showMsg("取消成功");
	}else if(result && result.result == "error"){
		jumbo.showMsg(result.message);
	}else{
		jumbo.showMsg(i18("CANCEL_FAILED"));//作废失败
	}
}
function removeCancelHoLineBtn(){
	$j("#tbl-detial2 tr[id]").each(function(idx,tag){
		var id = $j(tag).attr("id");
		var data=$j("#tbl-detial2").jqGrid("getRowData",id)
		if(data["lineIntStatus"] == '0'){
			$j(tag).find("button").remove();
		}
	});
}
