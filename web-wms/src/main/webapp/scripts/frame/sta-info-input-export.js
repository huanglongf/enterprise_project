$j.extend(loxia.regional['zh-CN'],{
	SALES_SEND_OUT_INFO_EXPORT_FILE_NAME	:"销售（换货）发货表",
	RETURN_REGISTER_INFO_EXPORT_FILE_NAME	:"退货登记表",
	
	BATCHNO : "配货清单号",
	CREATETIME : "创建时间",
	PLAN_BILL_COUNT : "计划配货单据数",
	PLAN_SKU_QTY : "计划配货商品件数",
	STVTOTAL : "计划执行总件数",
	INTSTATUS : "状态",
	NEW_LIST : "新建配货清单列表",
	PLANBILLCOUNT : "计划配货单据数",
	PLAYSKUQTY : "计划配货商品件数",
	
	CREATE_TIME_RULE : "创建时间:起始时间必须小于结束时间！",
	PICKING_TIME_RULE : "配货时间:起始时间必须小于结束时间！",
	CHECKED_TIME_RULE : "最后核对时间:起始时间必须小于结束时间！",
	
	INPUT_FILE_ERROR	:"请选择正确的Excel导入文件"
		
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

function getDate(strDate){
	var s = new Date(strDate.substring(0,10));
	s.setHours(strDate.substring(11,13));
	s.setMinutes(strDate.substring(11,13));
	s.setSeconds(strDate.substring(11,13));
	return s;
}

function showDetail(tag){
	var baseUrl = $j("body").attr("contextpath");
	var id = $j(tag).parents("tr").attr("id");
	$j("#div2").attr("plId",id);
	$j("#pickingListId").val(id);
	var tr = $j(tag).parents("tr");
	var data=$j("#batch-list").jqGrid("getRowData",id);
	var rs = loxia.syncXhrPost(baseUrl + "/queryPickingListDetail.json",{"pickingListId":id});
	if(rs && rs["pickingList"]){
		$j("#dphCode").html( rs["pickingList"]["code"]);
		$j("#planBillCount").html( rs["pickingList"]["planBillCount"]);
		$j("#planSkuQty").html( rs["pickingList"]["planSkuQty"]);
		$j("#pickingTime").html( rs["pickingList"]["pickingTime"]);
		$j("#checkedBillCount").html( rs["pickingList"]["checkedBillCount"]);
		$j("#checkedTime").html( rs["pickingList"]["checkedTime"]);
		$j("#checkedSkuQty").html( rs["pickingList"]["checkedSkuQty"]);
		$j("#sendSkuQty").html( rs["pickingList"]["sendSkuQty"]);
		$j("#sendStaQty").html( rs["pickingList"]["sendStaQty"]);
		$j("#lastSendTime").html( rs["pickingList"]["lastSendTime"]);
		$j("#invPKStatus").val( rs["pickingList"]["intStatus"]);
		
	}
	$j("#dphStatus").html(tr.find("td[aria-describedby='pickingList_intStatus']").html());
	$j("#creator").html(data["createName"]);
	$j("#operator").html(data["operUserName"]);
	$j("#dphMode").html(data["pkModeInt"]);
	reloadOrderDetail(baseUrl + "/detialList.json?pickingListId="+id);
	$j("#query-batch-list").addClass("hidden");
	$j("#order-List").removeClass("hidden");
}

function reloadOrderDetail(url){
	$j("#sta-list").jqGrid('setGridParam',{url:loxia.getTimeUrl(url)}).trigger("reloadGrid");
}

function findBatchList(){
	var formCrtime = getDate($j("#formCrtime").val());
	var toCrtime = getDate($j("#toCrtime").val());
	
	if(formCrtime > toCrtime){
		jumbo.showMsg(i18("CREATE_TIME_RULE"));//起始时间必须小于结束时间！
		return false;
	}
	
	var formPickingTime = getDate($j("#formPickingTime").val());
	var toPickingTime = getDate($j("#toPickingTime").val());
	if(formPickingTime > toPickingTime){
		jumbo.showMsg(i18("PICKING_TIME_RULE"));//起始时间必须小于结束时间！
		return false;
	}
	
	var formCheckedTime = getDate($j("#formCheckedTime").val());
	var toCheckedTime = getDate($j("#toCheckedTime").val());
	if(formCheckedTime > toCheckedTime){
		jumbo.showMsg(i18("CHECKED_TIME_RULE"));//起始时间必须小于结束时间！
		return false;
	}
	var postData=loxia._ajaxFormToObj("queryForm");
	$j("#batch-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/batchQuery.json"),
		postData:postData}).trigger("reloadGrid",[{page:1}]);
}

function beforePrintValidate(){
	var ids = $j("#sta-list").jqGrid('getDataIDs');
	var num=0,size=0;
	var datas;
	if(ids.length==0) {jumbo.showMsg("数据为空，数据出错..."); return false;}
	for(var i=0;i < ids.length;i++){
		size++;
		datas = $j("#sta-list").jqGrid('getRowData',ids[i]);
		if (datas['intStatus']==17){
			num++;
		}
	}
	if (size==num){
		jumbo.showMsg("当前订单已'取消已处理'，不能打印或导出..."); 
		return false;
	}
	return true;
}

$j(document).ready(function (){
	var baseUrl= $j("body").attr("contextpath");
	$j("#tabs").tabs();
	$j("#showErrorDialog").dialog({title: "导入错误信息", modal:true, autoOpen: false, width: 400, height: 300});
	
	var plStatus=loxia.syncXhr(baseUrl + "/json/formateroptions.do",{"categoryCode":"pickingListStatus"});
	$j("#batch-list").jqGrid({
		datatype: "json",
		url:baseUrl+"/batchQuery.json",
		colNames: ["ID",i18("BATCHNO"),i18("CREATETIME"),"操作时间",i18("PLANBILLCOUNT"),i18("PLAYSKUQTY"),"模式","状态","操作人","创建人"],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name: "code", index: "code", formatter:"linkFmatter", formatoptions:{onclick:"showDetail"}, width:120,resizable:true},         
		           {name: "createTime", index: "createTime", width: 150, resizable: true},
		           {name: "executedTime", index: "executedTime", width: 150, resizable: true},
		           {name: "planBillCount", index: "planBillCount", width: 80, resizable: true},
		           {name: "planSkuQty", index: "planSkuQty", width: 80, resizable: true},
		           {name: "pkModeInt", index: "pkModeInt",width: 100, resizable: true},
		           {name: "intStatus",index:"intStatus",width:150,resizable:true,formatter:'select',editoptions:plStatus},
		           {name: "operUserName", index: "operUserName",width: 100, resizable: true},
		           {name: "createName", index: "createName",width: 100, resizable: true}],
		caption: '待配货清单列表',// 待配货清单列表
	   	sortname: 'pl.CREATE_TIME',
	   	height:"auto",
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		pager:"#pager",
		sortorder: "desc",
		rownumbers:true,
		viewrecords: true,
		hidegrid:false,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var ids = $j("#batch-list").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var datas = $j("#batch-list").jqGrid('getRowData',ids[i]);
				if(datas["pkModeInt"] == '1'){
					$j("#batch-list").jqGrid('setRowData',ids[i],{"pkModeInt":"模式一"});
				}
				if(datas["pkModeInt"] == '2'){
					$j("#batch-list").jqGrid('setRowData',ids[i],{"pkModeInt":"模式二"});
				}
				if(datas["pkModeInt"] == '3'){
					$j("#batch-list").jqGrid('setRowData',ids[i],{"pkModeInt":"模式三"});
				}
			}
		}
	});
	
	
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var intStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	$j("#sta-list").jqGrid({
		datatype: "json",
		// ["ID","作业单号","状态","相关单据号","作业类型名称","淘宝店铺ID","物流服务商简称","创建时间","计划执行总件数","单批打印"],
		colNames: ["ID","status","作业单号","状态","相关单据号","作业类型名称","淘宝店铺ID","物流服务商简称","创建时间","计划执行总件数"],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name: "status", index: "status",hidden: true},
		           {name: "code", index: "code",sortable: false, width: 120, resizable: true},
		           {name: "intStatus", index: "status",sortable: false, width: 80, resizable: true,formatter:'select',editoptions:intStatus},
		           {name: "refSlipCode", index: "slipCode",sortable: false,width: 120, resizable: true},
		           {name: "intType", index: "type",sortable: false, width: 100, resizable: true,formatter:'select',editoptions:staType},
		           {name: "shopId", index: "shopId",sortable: false, width: 120, resizable: true},
		           {name: "lpcode", index: "lpcode",sortable: false, width: 100, resizable: true,formatter:'select',editoptions:trasportName},
	               {name:"createTime",index:"createTime",sortable: false,width:150,resizable:true},
	               {name: "stvTotal", index: "stvTotal",sortable: false, width: 100, resizable: true}
	               ],
		caption: "作业单列表",// 
		rowNum:-1,
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: false,
		sortorder: "asc",
		gridComplete : function(){
			loxia.initContext($j(this));
		},
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	//查询
	$j("#search").click(function(){
		findBatchList();
	});
	
	//重置
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
	});
	
	//明细返回按钮
	$j("#back").click(function(){
		$j("#order-List").addClass("hidden");
		$j("#query-batch-list").removeClass("hidden");
	});
	
	//创建配货批次
	$j("#createBatch").click(function(){
		loxia.lockPage();
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/createpicklistbyallsta.json");
		if(rs){//成功后更新原始查询条件列表
			if(rs.result=='success'){
				jumbo.showMsg("创建成功！\n批次["+rs.plCode+"],单据数量为："+rs.staCount+",配货成功:"+rs.staSuccessCount+",配货失败:"+rs.staErrorCount
						+"\n" + rs.message);
			}else if(rs.result=='error' && rs.isStaCountNull  && rs.isStaCountNull == 'true'){
				jumbo.showMsg("创建失败！没有可配货单据！");
			} else {
				jumbo.showMsg("创建失败！"+rs.message);
			}
			findBatchList();
			loxia.unlockPage();
		} else {
			loxia.unlockPage();
			jumbo.showMsg("操作错误！");
		}
	});
	
	//导出配货批次单据信息
	$j("#saleSendOutInfoExport").click(function(){
		if($j("#invPKStatus").val() == 2){
			$j("#upload").attr("src",baseUrl + "/warehouse/salessendoutinfoexport.do?plId="+$j("#pickingListId").val()+"&plCode="+$j("#dphCode").html());	
		} else {
			jumbo.showMsg("当前配货清单状态不允许导出!");
		}
	});
	
	
	//退货登记表
	$j("#returnRegisterInfoExport").click(function(){
		$j("#upload").attr("src",baseUrl + "/warehouse/returnregisterinfoexport.do");	
	});
	
	
	//库存报表导出
	$j("#returnReportingStockExport").click(function(){
		$j("#upload").attr("src",baseUrl + "/warehouse/inventoryreportkexport.do");	
	});
	
	// 打印装箱清单：
	$j("#printTrunkPackingList").click(function(){
		loxia.lockPage();
		if(beforePrintValidate()){
			var plid = $j("#pickingListId").val();
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/queryDispatchListOutputCount.json",{"plCmd.id":plid});
			if(rs && rs["msg"] == 'success' && parseInt(rs["count"]) == 0){
				var plcode = $j("#dphCode").html();
				jumbo.showMsg('装箱清单打印中，请等待...');
				var url = $j("body").attr("contextpath") + "/printtrunkpackinglistmode1.json?plCmd.id=" + plid + "&plCmd.code=" + plcode;
				printBZ(loxia.encodeUrl(url),true);
				$j("#" + plid).addClass("printRow");
			} else if(rs && rs["msg"] == 'success'){
				jumbo.showMsg("装箱清单已打印，不能重复打印！");
			} else {
				jumbo.showMsg("获取打印次数失败！");
			}
		}
		loxia.unlockPage();
	});
	
	
	$j("#returnRequestInboundImport").click(function(){		
		var file = $j("#fileInput").val();
		var errors = [];
		if(file == ""){
			errors.push(i18("INPUT_FILE_ERROR"));//请选择正确的Excel导入文件
		}else{
			var postfix = file.split(".")[1];
			if(postfix != "xls" && postfix != "xlsx"){
				errors.push(i18("INPUT_FILE_ERROR"));//请选择正确的Excel导入文件
			}
		}
		if(errors.length > 0){
			jumbo.showMsg(errors.join("<br />&nbsp; &nbsp; &nbsp;"));
			return false;
		}
		loxia.lockPage();
		$j("#returnRequestInboundImportForm").attr("action",loxia.getTimeUrl(baseUrl + "/warehouse/returnrequestinboundimport.do"));
		$j("#returnRequestInboundImportForm").submit();
	});
	
	//作业单批量出库导入
	$j("#save1").click(function(){		
		var file = $j("#file1").val();
		var errors = [];
		if(file == ""){
			errors.push(i18("INPUT_FILE_ERROR"));//请选择正确的Excel导入文件
		}else{
			var postfix = file.split(".")[1];
			if(postfix != "xls" && postfix != "xlsx"){
				errors.push(i18("INPUT_FILE_ERROR"));//请选择正确的Excel导入文件
			}
		}
		if(errors.length > 0){
			jumbo.showMsg(errors.join("<br />&nbsp; &nbsp; &nbsp;"));
			return false;
		}
		loxia.lockPage();
		$j("#importForm1").attr("action",loxia.getTimeUrl(baseUrl + "/warehouse/importrefreshpickinglistsn.do"));
		$j("#importForm1").submit();
	});
});
function showMsg(msg){
	jumbo.showMsg(msg);
}
