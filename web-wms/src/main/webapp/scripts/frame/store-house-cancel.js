$j.extend(loxia.regional['zh-CN'],{
	STA_LINE_LIST : "作业单明细",
	STA_CODE : "作业单号",
	STA_SLIP_CODE : "相关单据号",
	SLIP_CODE1:"相关单据号1",
	STA_TYPE : "作业类型名称",
	STA_STATUS : "作业单状态",
	OWNER : "平台店铺名称",
	TRACKING_NO : "快递单号",
	CRAETE_TIME : "创建时间",
	FINISH_TIME : "完成时间",
	PLAN_COUNT : "计划执行总数量",
	SKUCODE : "SKU编码",
	BARCODE : "条形码",
	JMCODE : "商品编码",
	SKU_NAME : "商品名称",
	KEY_PROPS : "扩展属性",
	PLAN_QTY : "计划量",
	COMFIRMED_QTY : "执行量",
	INV_STATUS:"库存状态",
		
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
function showDetail(obj){
	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");
	var datas = $j("#tbl-sta-cancel-list").jqGrid('getRowData',id);
	var baseUrl = $j("body").attr("contextpath");
	$j("#detail_tabs").removeClass("hidden");
	$j("#query-poOrder-list").addClass("hidden");
	$j("#staCode").text(datas["staCode"]);
	$j("#slipCode").text(datas["slipCode"]);
	$j("#intStaStatus").text(datas["staStatus"]);
	$j("#planQty").text(datas["planQty"]);
	$j("#staStatus").text(tr.find("td[aria-describedby$='staStatus']").text());
	var postData ={};
	postData["sta.id"]=id;
	if(datas["staStatus"]==15||datas["staStatus"]==17){
		$j("#m2_tabs-2").addClass("hidden");
		$j("#m2_tabs-3").removeClass("hidden");
		$j("#tbl-sta-cancel-detail1").jqGrid('setGridParam',{url:baseUrl + "/getDeliveryFailureStaLine.json",page:1,postData:postData}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl-po-order-detail1",{},baseUrl + "/getPoLineStaList.json",{"sta.id":id});
	}else{
		$j("#m2_tabs-3").addClass("hidden");
		$j("#m2_tabs-2").removeClass("hidden");
		$j("#tbl-sta-cancel-detail").jqGrid('setGridParam',{url:baseUrl + "/getDeliveryFailureStaLine.json",page:1,postData:postData}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl-po-order-detail",{},baseUrl + "/getPoLineStaList.json",{"sta.id":id});
	}
}

$j(document).ready(function (){
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	//var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	initShopQuery("companyshop","innerShopCode");
	$j("#detail_tabs").tabs();
	jumbo.loadShopList("companyshop");
	$j("#tbl-sta-cancel-list").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("STA_CODE"),i18("STA_SLIP_CODE"),"店铺","计划数量","作业单状态","创建时间"],
		colModel: [
							{name: "id", index: "id", hidden: true},
							{name:"staCode", index:"staCode", formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, width:100, resizable:true},
							{name:"slipCode",index:"slipCode",width:100,resizable:true},
							{name:"owner", index:"owner" ,width:70,resizable:true},
							{name:"planQty",index:"planQty", width:130,resizable:true},
							{name:"staStatus",index:"staStatus",width:70,resizable:true,formatter:'select',editoptions:staStatus},
							{name:"createTime", index:"createTime",width:130,resizable:true},
					 	],
		caption: "主动取消单据列表",
	   	sortname: 'data.createTime',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#pager",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
   		width:1000,
		jsonReader: { repeatitems : false, id: "0" },
	});
	jumbo.bindTableExportBtn("tbl-sta-cancel-list");
	
	$j("#tbl-sta-cancel-detail").jqGrid({
		datatype: "json",
		colNames: ["ID","商品编码","商品条码","商品名称","计划数量","实际可用量","报缺数量","报缺原因","报缺数量","报缺原因"],
		colModel: [
							{name: "id", index: "id", hidden: true},
							{name:"skuCode", index:"skuCode",  width:100, resizable:true},
							{name:"barCode", index:"barCode",  width:100, resizable:true},
							{name:"skuName", index:"supplierCode",  width:100, resizable:true},
							{name:"planQty", index:"planQty",width:80, resizable:true},
							{name:"usableQty",index:"usableQty",width:80,resizable:true},
							{name:"lackQty",index:"lackQty",width:80,hidden: true},
							{name:"reason",index:"reason",width:100,hidden: true},
							{name:"lackQty1",index:"lackQty1",width:80,resizable:true,formatter:function(v,x,r){return "<input type='text' name = 'lackQty1'/> ";}},
							{name:"reason1",index:"reason1",width:100,resizable:true,formatter:function(v,x,r){return "<select  name = 'reason1' /> ";}}
					 	],
		caption: "主动取消单据明细",
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
		pager:"#pagerDetail",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
   		width:1000,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete:function(){
			var ids = $j("#tbl-sta-cancel-detail").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var datas = $j("#tbl-sta-cancel-detail").jqGrid('getRowData',ids[i]);
				$j("<option value='" + null + "'>"+ "请选择" +"</option>").appendTo($j("#tbl-sta-cancel-detail tr[id="+datas["id"]+"] select[name=reason1]"));
				$j("<option value='" + "系统和实物库存不一致" + "'>"+ "系统和实物库存不一致" +"</option>").appendTo($j("#tbl-sta-cancel-detail tr[id="+datas["id"]+"] select[name=reason1]"));
				$j("<option value='" + "WMS和IM库存不一致" + "'>"+ "WMS和IM库存不一致" +"</option>").appendTo($j("#tbl-sta-cancel-detail tr[id="+datas["id"]+"] select[name=reason1]"));
				$j("<option value='" + "无能力发货" + "'>"+ "无能力发货" +"</option>").appendTo($j("#tbl-sta-cancel-detail tr[id="+datas["id"]+"] select[name=reason1]"));
				if(datas["reason"]!=""&&datas["reason"]!=null){
					$j("#tbl-sta-cancel-detail tr[id="+datas["id"]+"] input[name=lackQty1]").val(datas["lackQty"]);	
					$j("#tbl-sta-cancel-detail tr[id="+datas["id"]+"] select[name=reason1]").val(datas["reason"]);
				}else{
					$j("#tbl-sta-cancel-detail tr[id="+datas["id"]+"] input[name=lackQty1]").val(datas["planQty"]-datas["usableQty"]);
					
				}
			}
		}
	});
	
	$j("#tbl-sta-cancel-detail1").jqGrid({
		datatype: "json",
		colNames: ["ID","商品编码","商品条码","商品名称","计划数量","实际可用量","报缺数量","报缺原因"],
		colModel: [
							{name: "id", index: "id", hidden: true},
							{name:"skuCode", index:"skuCode",  width:100, resizable:true},
							{name:"barCode", index:"barCode",  width:100, resizable:true},
							{name:"skuName", index:"supplierCode",  width:100, resizable:true},
							{name:"planQty", index:"planQty",width:80, resizable:true},
							{name:"usableQty",index:"usableQty",width:80,resizable:true},
							{name:"lackQty",index:"lackQty",width:80,resizable:true},
							{name:"reason",index:"reason",width:100,resizable:true}
					 	],
		caption: "主动取消单据明细",
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
		pager:"#pagerDetail1",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
   		width:1000,
		jsonReader: { repeatitems : false, id: "0" },
	});
	
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
	});
	
	$j("#search").click(function(){
		var url =null;
		var baseUrl = $j("body").attr("contextpath");
		url= baseUrl + "/getDeliveryFailureSta.json";
		var postData = loxia._ajaxFormToObj("queryForm");
		$j("#tbl-sta-cancel-list").jqGrid('setGridParam',{url:url,page:1,postData:postData}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl-sta-cancel-list",{"intType":"whSTAType","intStatus":"whSTAStatus"},url,loxia._ajaxFormToObj("queryForm"));

	});
	
	$j("#back").click(function(){
		$j("#query-poOrder-list").removeClass("hidden");
		$j("#detail_tabs").addClass("hidden");
	});
	
	$j("#save").click(function(){
		var postData={};
		var ids = $j("#tbl-sta-cancel-detail").jqGrid('getDataIDs');
		var index=-1;
		var staCode=$j("#staCode").text();
		var intStatus=$j("#intStaStatus").text();
		for(var i=0;i < ids.length;i++){
			index+=1;
			var datas = $j("#tbl-sta-cancel-detail").jqGrid('getRowData',ids[i]);
			postData["wmsCancelList[" + index + "].id"]=datas["id"];
			postData["wmsCancelList[" + index + "].skuCode"]=datas["skuCode"];
			postData["wmsCancelList[" + index + "].barCode"]=datas["barCode"];
			postData["wmsCancelList[" + index + "].skuName"]=datas["skuName"];
			postData["wmsCancelList[" + index + "].planQty"]=datas["planQty"];
			postData["wmsCancelList[" + index + "].usableQty"]=datas["usableQty"];
			var lackQty=$j("#tbl-sta-cancel-detail tr[id="+datas["id"]+"] input[name=lackQty1]").val();
			if(/^[0-9]+$/.test(lackQty)){
				postData["wmsCancelList[" + index + "].lackQty"]=lackQty;
			}else{
				jumbo.showMsg("报缺数量必须为正整数");
				return;
			}
			var reason= $j("#tbl-sta-cancel-detail tr[id="+datas["id"]+"] select[name=reason1]").find("option:selected").text();
			if(reason=="请选择"){
				jumbo.showMsg("未选择报缺原因!");
				return;
			}else{
				postData["wmsCancelList[" + index + "].reason"]=reason;
			}
		}
		if(intStatus==15||intStatus==17){
			jumbo.showMsg("作业单已取消，不可操作!");
			return;
		}
		if(confirm("确认保存？")){
			var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/saveDeliveryFailureStaInfo.json?staCode="+staCode+"&intStatus="+intStatus,postData);
			if(rs["re"]=="success"){
				jumbo.showMsg("保存成功");
				$j("#back").trigger("click");	
			}else{
				jumbo.showMsg(rs["re"]);
			}
		}
		
	});
	
	$j("#saveAndPush").click(function(){
		var postData={};
		var ids = $j("#tbl-sta-cancel-detail").jqGrid('getDataIDs');
		var index=-1;
		var staCode=$j("#staCode").text();
		var intStatus=$j("#intStaStatus").text();
		var isPost=true;
		for(var i=0;i < ids.length;i++){
			index+=1;
			var datas = $j("#tbl-sta-cancel-detail").jqGrid('getRowData',ids[i]);
			postData["wmsCancelList[" + index + "].id"]=datas["id"];
			postData["wmsCancelList[" + index + "].skuCode"]=datas["skuCode"];
			postData["wmsCancelList[" + index + "].barCode"]=datas["barCode"];
			postData["wmsCancelList[" + index + "].skuName"]=datas["skuName"];
			postData["wmsCancelList[" + index + "].planQty"]=datas["planQty"];
			postData["wmsCancelList[" + index + "].usableQty"]=datas["usableQty"];
			var lackQty=$j("#tbl-sta-cancel-detail tr[id="+datas["id"]+"] input[name=lackQty1]").val();
			if(/^[0-9]+$/.test(lackQty)){
				postData["wmsCancelList[" + index + "].lackQty"]=lackQty;
			}else{
				jumbo.showMsg("报缺数量必须为正整数");
				return;
			}
			var reason= $j("#tbl-sta-cancel-detail tr[id="+datas["id"]+"] select[name=reason1]").find("option:selected").text();
			if(reason=="请选择"){
				jumbo.showMsg("未选择报缺原因!");
				return;
			}else{
				postData["wmsCancelList[" + index + "].reason"]=reason;
			}
		}
		if(intStatus==15||intStatus==17){
			jumbo.showMsg("作业单已取消，不可操作!");
			return;
		}
		if(confirm("确认保存并推送？")){
			var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/saveDeliveryFailureStaInfo.json?staCode="+staCode+"&intStatus="+intStatus+"&isPost="+isPost,postData);
			if(rs["re"]=="success"){
				jumbo.showMsg("保存成功");
				$j("#back").trigger("click");	
			}else{
				jumbo.showMsg(rs["re"]);
			}
		}
		
	});
});
