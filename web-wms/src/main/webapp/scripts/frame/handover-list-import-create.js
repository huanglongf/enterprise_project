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
var wls=[];
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
		if(result.flag=="true"){
			$j("#tbl-detial").jqGrid('setGridParam',{url:window.$j("body").attr("contextpath")+"/findlinebyholistid.json",page:1,postData:{"hoListId":$j("#hidHoId").val()}}).trigger("reloadGrid");
			removeCancelHoLineBtn();
			var postData = {};
			postData["hoListId"] = $j("#hidHoId").val();
			var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/findhandoverlistbyid.json",postData);
			if(result && result.handoverlist){
				$j("#hoPackageCount").html(result.handoverlist.packageCount);
				$j("#hoTotalWeight").html(result.handoverlist.totalWeight);
			}
		}else{
			$j("#confirm_ho_form_div").addClass("hidden");
			$j("#allDelete1").removeClass("hidden");
		}
	}else if(result && result.result == "error"){
		jumbo.showMsg(result.message);
	}else{
		jumbo.showMsg(i18("CANCEL_FAILED"));//作废失败
	}
}

$j(document).ready(function (){
	//init tranports   加载物流供应商平台 fanht
	//var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getTransportator.json");
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findPlatformList.json");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selTrans,#selTrans-tabs2,#selTrans-tabs3"));
	}
	
	///////////////////////////////////////////////////////////////////
	//加载物流信息 一键交接
	$j("#selTrans2").append("<option></option>");
	for(var idx in result){
		$j("#selTrans2").append("<option value='"+result[idx].code+"'>"+result[idx].name+"</option>");
	}
    $j("#selTrans2").flexselect();
    $j("#selTrans2").val("");
    
    $j("#selTrans2").change(function(){
    	if ($j("#selTrans2").val() != ""){
    		var showShopList = $j("#showShopList").html();
    		var postshopInput = $j("#postshopInput").val();
    		var postshopInputName = $j("#postshopInputName").val();
    		var shopLikeQuerys = $j("#shopLikeQuery").val(); //模糊查询下拉框key
    		var shopLikeQuery = $j("#selTrans2_flexselect").val(); //模糊查询下拉框value
    		var shoplist = "", postshop="",shoplistName="";
    		if (showShopList.indexOf(shopLikeQuery) < 0){
    			shoplist = shopLikeQuery + " | ";
				postshop = shopLikeQuerys+ "|";
				shoplistName = shopLikeQuery+ "|";
    		}  //不包含
    		$j("#showShopList").html(showShopList + shoplist);
    		$j("#postshopInput").val(postshopInput+ postshop);
    		$j("#postshopInputName").val(postshopInputName + shoplistName);
    	}
    });
    
    //设置未交接运单数和交接上限
	var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getNum.json");
	if(rs.maxNum==null){
		document.getElementById("maxNum").innerText ='未设置';
	}else{
		document.getElementById("maxNum").innerText =rs.maxNum;
	}
	document.getElementById("num").innerText =rs.num;
	//一键交接
	$j("#oneHandover").click(function(){
	  var lpcodes=$j("#postshopInputName").val();
	  if(lpcodes==null || lpcodes==''){
			 jumbo.showMsg("请选择物流服务商");return;
	  }
	  var postData = {};
	  postData["lpcode"] =lpcodes;
	  var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/createOneHandOverList.json",postData);
		 if(rs["result"] == 'success'){
			 if(rs["brand"]=='0'){
				 jumbo.showMsg("无一键交接满足条件");
			 }else{
				  getNum();
			 }
		 }else{
			     jumbo.showMsg("一键交接失败");
		 }
	});
	//一键交接并打印
	$j("#oneHandoverPrint").click(function(){
		  var lpcodes=$j("#postshopInputName").val();
		  if(lpcodes==null || lpcodes==''){
				 jumbo.showMsg("请选择物流服务商");return;
		  }
		  var postData = {};
		  postData["lpcode"] =lpcodes;
		  var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/createOneHandOverList.json",postData);
			 if(rs["result"] == 'success'){
				 if(rs["brand"]=='0'){
					 jumbo.showMsg("无一键交接满足条件");
				 }else{
					  getNum();
					  var hoIds=rs["hoIds"];
					  var url = $j("body").attr("contextpath") + "/json/printhandoverlist2.json?hoIds=" + hoIds;
					  printBZ(loxia.encodeUrl(url),true);
				 }
			 }else{
				     jumbo.showMsg("一键交接失败");
			 }
		});
	//获取未交接运单数 公共方法
	function getNum(){
		var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getNum.json");
		if(rs.maxNum==null){
			document.getElementById("maxNum").innerText ='未设置';
		}else{
			document.getElementById("maxNum").innerText =rs.maxNum;
		}
		 document.getElementById("num").innerText =rs.num;
		 document.getElementById("num2").innerText =rs.num;
		 document.getElementById("showShopList").innerText ='';
		 document.getElementById("showShopList2").innerText ='';
		 $j("#oneHandDiv input").val("");
		 $j("#oneHandDiv2 input").val("");
	}
	
	//////////////2222222222222222222
	$j("#selTrans22").append("<option></option>");
	for(var idx in result){
		$j("#selTrans22").append("<option value='"+result[idx].code+"'>"+result[idx].name+"</option>");
	}
    $j("#selTrans22").flexselect();
    $j("#selTrans22").val("");
    
    $j("#selTrans22").change(function(){
    	if ($j("#selTrans22").val() != ""){
    		var showShopList = $j("#showShopList2").html();
    		var postshopInput = $j("#postshopInput2").val();
    		var postshopInputName = $j("#postshopInputName2").val();
    		var shopLikeQuerys = $j("#shopLikeQuery2").val(); //模糊查询下拉框key
    		var shopLikeQuery = $j("#selTrans22_flexselect").val(); //模糊查询下拉框value
    		var shoplist = "", postshop="",shoplistName="";
    		if (showShopList.indexOf(shopLikeQuery) < 0){
    			shoplist = shopLikeQuery + " | ";
				postshop = shopLikeQuerys+ "|";
				shoplistName = shopLikeQuery+ "|";
    		}  //不包含
    		$j("#showShopList2").html(showShopList + shoplist);
    		$j("#postshopInput2").val(postshopInput+ postshop);
    		$j("#postshopInputName2").val(postshopInputName + shoplistName);
    	}
    });
    
    //设置未交接运单数和交接上限
	var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getNum.json");
	if(rs.maxNum==null){
		document.getElementById("maxNum2").innerText ='未设置';
	}else{
		document.getElementById("maxNum2").innerText =rs.maxNum;
	}
	document.getElementById("num2").innerText =rs.num;
	//一键交接
	$j("#oneHandover2").click(function(){
	  var lpcodes=$j("#postshopInputName2").val();
	  if(lpcodes==null || lpcodes==''){
			 jumbo.showMsg("请选择物流服务商");return;
	  }
	  var postData = {};
	  postData["lpcode"] =lpcodes;
	  var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/createOneHandOverList.json",postData);
		 if(rs["result"] == 'success'){
			 if(rs["brand"]=='0'){
				 jumbo.showMsg("无一键交接满足条件");
			 }else{
				  getNum2();
			 }
		 }else{
			     jumbo.showMsg("一键交接失败");
		 }
	});
	//一键交接并打印
	$j("#oneHandoverPrint2").click(function(){
		  var lpcodes=$j("#postshopInputName2").val();
		  if(lpcodes==null || lpcodes==''){
				 jumbo.showMsg("请选择物流服务商");return;
		  }
		  var postData = {};
		  postData["lpcode"] =lpcodes;
		  var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/createOneHandOverList.json",postData);
			 if(rs["result"] == 'success'){
				 if(rs["brand"]=='0'){
					 jumbo.showMsg("无一键交接满足条件");
				 }else{
					  getNum2();
					  var hoIds=rs["hoIds"];
					  var url = $j("body").attr("contextpath") + "/json/printhandoverlist2.json?hoIds=" + hoIds;
					  printBZ(loxia.encodeUrl(url),true);
				 }
			 }else{
				     jumbo.showMsg("一键交接失败");
			 }
		});
	//获取未交接运单数 公共方法2
	function getNum2(){
		var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getNum.json");
		if(rs.maxNum==null){
			document.getElementById("maxNum2").innerText ='未设置';
		}else{
			document.getElementById("maxNum2").innerText =rs.maxNum;
			document.getElementById("maxNum").innerText =rs.maxNum;
		}
		 document.getElementById("num2").innerText =rs.num;
		 document.getElementById("showShopList").innerText ='';
		 document.getElementById("showShopList2").innerText ='';
		 $j("#oneHandDiv input").val("");
		 $j("#oneHandDiv2 input").val("");
	}
	/////////////////////////////////////////////////////////////////
	
	
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
//			colNames: ["ID",i18("TRACKINGNO"),i18("OWNER"),i18("REFSLIPCODE"),i18("RECEIVER"),i18("ADDRESS"),i18("WEIGHT"),i18("OUTBOUNDTIME")],
			colNames: ["ID",i18("TRACKINGNO"),i18("WEIGHT"),i18("OUTBOUNDTIME")],
			colModel: [
		           {name: "id", index: "id", hidden: true},		         
			       {name: "trackingNo",width: 150, resizable: true},
//		           {name: "owner",width: 150, resizable: true},
//		           {name: "refSlipCode", width: 150, resizable: true},
//		           {name: "receiver",width: 100, resizable: true},
//		           {name: "address", index: "address", width: 200, resizable: true},
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
//		colNames: ["ID",i18("STATUS"),i18("TRACKINGNO"),i18("REFSLIPCODE"),i18("WEIGHT"),i18("RECEIVER"),i18("OUTBOUNDTIME"),i18("LINEINTSTATUS"),i18("IDFORBTN")],
		colNames: ["ID",i18("STATUS"),i18("TRACKINGNO"),i18("WEIGHT"),i18("OUTBOUNDTIME"),i18("LINEINTSTATUS")],
		colModel: [{name: "id", index: "id", hidden: true},
		            {name:"lineIntStatus", hidden: true},
					{name :"trackingNo",index:"trackingNo",width:100,resizable:true},
//					{name: "refSlipCode", index:"refSlipCode",width:100,resizable:true},
					{name:"weight", index:"weight", width:90, resizable:true},
//					{name:"receiver",index:"receiver",width:90,resizable:true},
					{name:"outboundTime",index: "outboundTime",width:150,resizable:true},
					{name:"lineIntStatus", index:"lineIntStatus" ,width:60,resizable:true,formatter:'select',editoptions:status}],
//					{name: "idForBtn", width: 120,resizable:true, formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("CANCEL"), onclick:"cancelDetial(this,event);"}}}],//作废
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
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/createHandOverListDelete.json",postData);
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
			$j("#download").addClass("hidden");
			getNum();
		}else if(result && result.result == "error"){
			jumbo.showMsg(result.message);
		}else{
			jumbo.showMsg(i18("LIST_CREATE_FAILED"));//交货清单创建失败
		}
	});
	
	$j("#back,#back4").click(function(){
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
		$j("#allDelete1").addClass("hidden");
		$j("#divRemoveInfo").addClass("hidden");
		$j("#tbl-removeInfo tr[id]").remove();
		$j("#hidHoId").val("");
		$j("#btnDetialHo").removeClass("hidden");
		$j("#tblHandoverSuccess").addClass("hidden");
		$j("#tblHandoverInput").removeClass("hidden");
		$j("#download").removeClass("hidden");
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
	
	$j("#selTrans-tabs2").change(function(event){
	 
	}); 
	
	$j("#inputTransNoByHand").click(function(){
		var selTrans = $j("#selTrans-tabs2").val();
		var errors = [];
		if(selTrans == ""){
			errors.push(i18("NEW_LPCODE_SELECT"));//请选择物流供应商
		}
		if(!$j("#barCode_tab tbody tr").length > 0){
			errors.push("请输入快递单号.");
		}
		if(errors.length > 0){
			jumbo.showMsg(errors.join("<br />&nbsp; &nbsp; &nbsp;"));
			$j("#wrapStuff").focus();
			return;
		}
		$j("#divRemoveInfo2").removeClass("hidden");
		var transNo;
		var postData={};
		$j("#barCode_tab tbody:eq(0) tr").each(function (i,tag){ 
			transNo = $j(tag).find("td:eq(1) input").val();
			postData['transNo['+i+']'] = transNo;
		});
		postData["lpcode"] = selTrans;
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/generatehandoverlistbyhand.json",postData);
		
		 if (rs && rs.result && rs.result == "success"){
			var data = rs.hoList;
			var removeByTrackingNo = rs.removeByTrackingNo;
			var removeBylpcode = rs.removeBylpcode;
			var removeBySta = rs.removeBySta;
			var removeBylpcodePre = rs.removeBylpcodePre;
			var packageCount = rs.packageCount;
			var totalWeight = rs.totalWeight;
			$j("#packageCount2").html(packageCount);
			$j("#weight2").html(totalWeight);
		    $j("#tbl-goodsList2 tr[id]").remove();
		    
		    for(var d in data){
		    	$j("#tbl-goodsList2").jqGrid('addRowData',data[d].id,data[d]);	
		   	}
		    if ($j("#tbl-goodsList2 tbody tr:gt(0)").length > 0){
		    	$j("#buttonList2").removeClass("hidden");
		    }
		    /*if ($j("#tbl-goodsList2 tr").length  >0 ){
		    	$j("#buttonList2").removeClass("hidden");
		    }*/
		    
			var i = 0;
		    $j("#tbl-removeInfo2 tr[id]").remove();
		    if(removeBylpcodePre != null){
			    for(var d in removeBylpcodePre){
			        i++;
			    	$j("#tbl-removeInfo2").jqGrid('addRowData',i,{"trackingNo":removeBylpcodePre[d],"msg":"快递单号是预售订单"});	
			   	}
		    }
		    if(removeByTrackingNo != null){
			    for(var d in removeByTrackingNo){
			        i++;
			    	$j("#tbl-removeInfo2").jqGrid('addRowData',i,{"trackingNo":removeByTrackingNo[d],"msg":"快递单号不存在或没有出库或已经创建完交接清单"});	
			   	}
		    }
		    if(removeBylpcode != null){
			    for(var d in removeBylpcode){
			        i++;
			    	$j("#tbl-removeInfo2").jqGrid('addRowData',i,{"trackingNo":removeBylpcode[d],"msg":"物流交接商与快递单不匹配或作业单已完成"});	
			   	}
		    }
		    if(removeBySta != null){
			    for(var d in removeBySta){
			        i++;
			    	$j("#tbl-removeInfo2").jqGrid('addRowData',i,{"trackingNo":removeBySta[d],"msg":"快递单号非交接状态或者已完成交接"});	
			   	}
		    }
		    $j("#barCode_tab tbody tr").remove();
		}else {
			jumbo.showMsg("操作异常");
		}
	});
	
	
	$j("#createOrderByHand").click(function(){
		if (!$j("#tbl-goodsList2 tbody tr:gt(0)").length  >0){
			jumbo.showMsg("数据为空，操作异常");
			return ;
		}
		var postData = {};
		postData["lpcode"] = $j("#selTrans-tabs2").val();
		$j("#tbl-goodsList2 tr[id]").each(function(i,tag){
			postData['packageIds['+i+']'] = $j(tag).attr("id");
		});
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
			$j("#searchInfo2").addClass("hidden");
			//$j("#searchInfo2").addClass("hidden");
			$j("#confirm_ho_form_div2").removeClass("hidden");
			getNum();
		}else if(result && result.result == "error"){
			jumbo.showMsg(result.message);
		}else{
			jumbo.showMsg(i18("LIST_CREATE_FAILED"));//交货清单创建失败
		}
	});
	

	$j("#back2,#back3").click(function(){
//		var se = $j("#select option:eq(0)").val();
		$j("#confirm_ho_form2 input").val("");
		$j("#selTrans-tabs2").val("");
		$j("#packageCount2").text("");
		$j("#weight2").text("");
		$j("#tbl-goodsList2 tr[id]").remove();
//		$j("#searchFile").addClass("hidden");
		$j("#searchInfo2").removeClass("hidden");
		$j("#confirm_ho_form_div2").addClass("hidden");
		$j("#allDelete").addClass("hidden");
		$j("#divRemoveInfo2").addClass("hidden");
		$j("#tbl-removeInfo2 tr[id]").remove();
		$j("#hidHoId2").val("");
		$j("#btnDetialHo2").removeClass("hidden");
		$j("#tblHandoverSuccess2").addClass("hidden");
		$j("#tblHandoverInput2").removeClass("hidden");
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
	
	//交接
	$j("#btnDetialHo3").click(function(){
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
			$j("#btnDetialHo3").addClass("hidden");
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
	
	// 交接查询- 保存及打印
	$j("#btnDetialPrint2").click(function(){
		var errors=loxia.validateForm("confirm_ho_form2");
		if(errors.length>0){
			jumbo.showMsg(errors);
			return false;
		}
		var postData = loxia._ajaxFormToObj("confirm_ho_form2");
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/savehandoverlistinfo.json",postData);
		if(rs && rs.result == 'success'){
			//保存成功->打印
			jumbo.showMsg(i18("OPERATING"));
			var hlid = $j("#hidHoId2").val();
			var url = $j("body").attr("contextpath") + "/json/printhandoverlist.json?hoListId=" + hlid;
			printBZ(loxia.encodeUrl(url),true);			 
		}else{
			jumbo.showMsg(rs.message);
		}
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
//			colNames: ["ID",i18("TRACKINGNO"),i18("OWNER"),i18("REFSLIPCODE"),i18("RECEIVER"),i18("ADDRESS"),i18("WEIGHT"),i18("OUTBOUNDTIME")],
			colNames: ["ID",i18("TRACKINGNO"),i18("WEIGHT"),i18("OUTBOUNDTIME")],
			colModel: [
		           {name: "id", index: "id", hidden: true},		         
			       {name: "trackingNo",width: 150, resizable: true},
//		           {name: "owner",width: 150, resizable: true},
//		           {name: "refSlipCode", width: 150, resizable: true},
//		           {name: "receiver",width: 100, resizable: true},
//		           {name: "address", index: "address", width: 200, resizable: true},
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
//		colNames: ["ID",i18("STATUS"),i18("TRACKINGNO"),i18("REFSLIPCODE"),i18("WEIGHT"),i18("RECEIVER"),i18("OUTBOUNDTIME"),i18("LINEINTSTATUS"),i18("IDFORBTN")],
		colNames: ["ID",i18("STATUS"),i18("TRACKINGNO"),i18("WEIGHT"),i18("OUTBOUNDTIME"),i18("LINEINTSTATUS")],
		colModel: [{name: "id", index: "id", hidden: true},
		            {name:"lineIntStatus", hidden: true},
					{name :"trackingNo",index:"trackingNo",width:100,resizable:true},
//					{name: "refSlipCode", index:"refSlipCode",width:100,resizable:true},
					{name:"weight", index:"weight", width:90, resizable:true},
//					{name:"receiver",index:"receiver",width:90,resizable:true},
					{name:"outboundTime",index: "outboundTime",width:150,resizable:true},
					{name:"lineIntStatus", index:"lineIntStatus" ,width:60,resizable:true,formatter:'select',editoptions:status}],
//					{name: "idForBtn", width: 120,resizable:true, formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("CANCEL"), onclick:"cancelDetialTab2(this,event);"}}}],//作废
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
	
	$j('#selTrans-tabs3').change(function(){ 
		var w=$j('#selTrans-tabs3').val();
		var t=$j('#selTrans-tabs3  option:selected').text();
		
		if(w!=""){
			for(var i=0;i<wls.length;i++){
				if(wls[i]==w){
					return false;
				}
			}
			if(wls.length!=0){
				$j('#wlsList').append("，");
			}
			
			wls[wls.length]=w;
			$j('#wlsList').append(t);
		}
		
	});
	
	$j("#tbl-zdhlist").jqGrid({
//		url:baseUrl + "/findPickZoneInfoList.json",
		datatype: "json",
		colNames: ["物流商编码","待创建交接清单包裹数"],
		colModel: [
				   {name: "lpcode", index: "lpcode", hidden: false},
				   //{name: "lpName", index: "lpName", width:150,resizable:true},  
		           {name: "quantity", index: "quantity", width:150,resizable:true}
		           ],
		caption: "待创建交接包裹",
		pager:"#pager_query",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: true,
	    rownumbers: true,
	    viewrecords: true,
		sortorder: "desc",
		jsonReader: { repeatitems : false, id: "0" },
	});
	
	$j("#queryWld").click(function(){
		var h=$j('#wlsList').html();
		if(h==""){
			jumbo.showMsg("请选择物流商！");
			return false;
		}
		
		$j("#tbl-zdhlist").jqGrid("clearGridData");
		
		var postData = {};
		postData["lpCodeS"]=h;
		$j("#tbl-zdhlist").jqGrid('setGridParam',{
			url:window.$j("body").attr("contextpath")+"/findCountPackageByOutbound.json",
			postData:postData,
			page:1
		}).trigger("reloadGrid");
		
	});
	
	$j("#reset").click(function(){
		wls=[];
		$j('#wlsList').empty();
	});
	
	//自动化仓交接
	$j("#autoWhHandover").click(function(){
		var ids = $j("#tbl-zdhlist").jqGrid('getGridParam','selarrrow');
		
		if(ids==""){
			jumbo.showMsg("请选择物流商！");
			return false;
		}
		  var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/autoHandOverList.json?lpcode="+ids);
			 if(rs["result"] == 'success'){
				 jumbo.showMsg("交接成功！");
				 $j("#queryWld").click();
			 }else{
				     jumbo.showMsg("交接失败！");
			 }
		});
	
	//自动化仓交接并打印
	$j("#autoWhHandoverAndPrint").click(function(){
		var ids = $j("#tbl-zdhlist").jqGrid('getGridParam','selarrrow');
		
		if(ids==""){
			jumbo.showMsg("请选择物流商！");
			return false;
		}
		  var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/autoHandOverList.json?lpcode="+ids);
			 if(rs["result"] == 'success'){
					  var hoIds=rs["hoIds"];
					  var url = $j("body").attr("contextpath") + "/json/printAutoWhHandOverList.json?hoIds=" + hoIds;
					  printBZ(loxia.encodeUrl(url),true);
			 }else{
				     jumbo.showMsg("交接失败");
			 }
		});
});
function addBarCode(){
	var varCode = jQuery.trim($j("#wrapStuff").val());
	var lpCode = jQuery.trim($j("#selTrans-tabs2").val());
	var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/checktrackingno2.json",{"transNo":varCode,"lpCode":lpCode});
	if(rs){
		if(rs.result=="0"){
			jumbo.showMsg("物流商与运单号不匹配或请选择物流商!");
			$j("#wrapStuff").val("");
			return;
		}
	}else{
		jumbo.showMsg("系统异常,访问后台失败！");
		return;
	}
	$j("#wrapStuff").val("");
	if(varCode != ""){
		var isAdd = true;
		$j("#barCode_tab tbody tr").each(function (i,tag){
			if(varCode == $j(tag).find("td:eq(1) :input").val()){
				jumbo.showMsg("快递单号已存在.");
				isAdd = false;
				return false;
			}
		});
		if(isAdd){
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/checktrackingnoforholist.json",{"transNo":varCode});
			if(rs){
				if(rs.result=="error"){
					jumbo.showMsg(rs.message);
					return;
				}
			}else{
				jumbo.showMsg("系统异常,访问后台失败！");
				return;
			}
			$j("button[action=add]").click();
			var length = $j("#barCode_tab tbody tr").length-1;
			$j("#barCode_tab tbody tr:eq("+length+") td:eq(1) :input").val(varCode);
		}
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
		if(result.flag=="true"){
			$j("#tbl-detial2").jqGrid('setGridParam',{url:window.$j("body").attr("contextpath")+"/findlinebyholistid.json",page:1,postData:{"hoListId":$j("#hidHoId2").val()}}).trigger("reloadGrid");
			removeCancelHoLineBtn();
			var postData = {};
			postData["hoListId"] = $j("#hidHoId2").val();
			var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/findhandoverlistbyid.json",postData);
			if(result && result.handoverlist){
				$j("#hoPackageCount2").html(result.handoverlist.packageCount);
				$j("#hoTotalWeight2").html(result.handoverlist.totalWeight);
			}
		}else{
			$j("#confirm_ho_form_div2").addClass("hidden");
			$j("#allDelete").removeClass("hidden");
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
