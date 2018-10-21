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


//slipCode
function showslipcode(tag){
	//取Id，
	var id = $j(tag).parents("tr").attr("id");
	$j("#btnlist").addClass("hidden");  //隐藏按钮
	$j("#back1").removeClass("hidden");  
	$j("#divTbslipCode").removeClass("hidden");
	$j("#divTbDetial").addClass("hidden");
	//获得refSlipCode的值 
	var slipCode = $j("#tbl-orderDetail").getCell(id,"refSlipCode");
	var url = $j("body").attr("contextpath")+ "/getByslipCode.json?slipCode="+slipCode;
	$j("#tbl-slipCode").jqGrid('setGridParam',{url:url,datatype: "json",}).trigger("reloadGrid");
}

function showDetail(tag){
	$j("#divTbDetial").removeClass("hidden");
	var id = $j(tag).parents("tr").attr("id");
	$j("#pickinglistid").val(id);
	$j("#back1").addClass("hidden");
	$j("#divTbslipCode").addClass("hidden");
	$j("#showList").addClass("hidden");
	$j("#div2").removeClass("hidden");

	var baseUrl = $j("body").attr("contextpath");
	var rs = loxia.syncXhrPost(baseUrl + "/getPickingList.json",{"pickingListId":id});
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
	}
	var rss = loxia.syncXhrPost(baseUrl + "/findPackingByBatchCode.json",{"allocateCargoCmd.id":id});
	if(rss && rss["pl"]){
		$j("#creator").html(rss["pl"]["crtUserName"]);
		$j("#operator").html(rss["pl"]["operUserName"]);
	}
	var tr = $j(tag).parents("tr");
	$j("#dphStatus").html(tr.find("td[aria-describedby='tbl-dispatch-list_intStatus']").html());
	reloadOrderDetail($j("body").attr("contextpath") + "/detialList.json?pickingListId="+id);
	
	// show finish button
	var rs = loxia.syncXhrPost(baseUrl + "/findpackinglistbyid.json",{"pickingListId":id});
	if(rs && rs["pl"]){
		var status = rs["pl"]["intStatus"];
		if (status == 8 || status == 2){
			$j("#finish").removeClass("hidden");
		}else {
			$j("#finish").addClass("hidden");
		}
	}
}
var wlist =null;
$j(document).ready(function (){
	wlist = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/checkoperationunit.json");
	var result2= loxia.syncXhrPost($j("body").attr("contextpath")+"/selectbyopc.json");
	for(var idx in result2.warelist){
		$j("<option value='" + result2.warelist[idx].id + "'>"+ result2.warelist[idx].name +"</option>").appendTo($j("#wselTrans"));
	}
	var plstatus=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"pickingListStatus"});
	if(!plstatus.exception){
		var postDataO = {};
		for(var idx in wlist){
			postDataO["plList["+idx+"]"]=wlist[idx];
		}
		$j("#tbl-dispatch-list").jqGrid({
			url:$j("body").attr("contextpath") + "/json/matchListForModelOne.json",
			datatype: "json",
			//["ID","配货批次号","状态","计划配货单据数","已核对单据数","已发货单据数","计划配货商品件数","已核对商品件数","已发货商品件数","创建时间","最后核对时间","最后发货时间","开始配货时间"]
			colNames: ["ID","配货批次号",i18("INTSTATUS"),"仓库名称",i18("PLANBILLCOUNT"),i18("CHECKEDBILLCOUNT"),i18("SHIPSTACOUNT"),i18("PLANSKUQTY"),i18("CHECKEDSKUQTY"),i18("SHIPSKUQTY"),
					i18("CREATE_TIME"),i18("CHECKEDTIME"),i18("EXECUTEDTIME"),i18("PICKINGTIME")],
			colModel: [{name: "id", index: "id", hidden: true},
						{name :"code",index:"code",width:100,resizable:true, formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}},
						{name:"intStatus", index:"status" ,width:60,resizable:true,formatter:'select',editoptions:plstatus},
						{name: "wname", index:"wname",width:120},
						{name: "planBillCount", index:"planBillCount",width:100,resizable:true},
						{name:"checkedBillCount", index:"checkedBillCount", width:90, resizable:true,hidden:true},
						{name:"shipStaCount",index:"shipStaCount",width:90,resizable:true,hidden:true},
						{name:"planSkuQty",index: "planSkuQty",width:120,resizable:true},
						{name:"checkedSkuQty",index:"checkedSkuQty",width:100,resizable:true,hidden:true},
						{name:"shipSkuQty",index:"shipSkuQty",width:100,resizable:true,hidden:true},
						{name:"createTime",index:"createTime",width:150,resizable:true},
						{name:"checkedTime",index:"checkedTime",width:150,resizable:true},
						{name:"executedTime",index:"executedTime",width:150,resizable:true},
		                {name:"pickingTime",index:"pickingTime",width:150,resizable:true}],
			caption: i18("PICKING_LIST"),
	   		sortname: 'ID',
	  		multiselect: false,
			sortorder: "desc",
			rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
		   	height:jumbo.getHeight(),
		   	viewrecords: true,
	   		rownumbers:true,
		   	pager:"#pager",
		   	jsonReader: { repeatitems : false, id: "0" },
		   	postData:postDataO
		});
	}else{
		jumbo.showMsg(i18("INIT_SYSTEM_DATA_EXCEPTION"));//初始化系统参数异常
	}
	
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var status=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"trueOrFalse"});
	var intStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	
	$j("#tbl-orderDetail").jqGrid({
		//url:url,
		datatype: "json",
		// colNames:
		// ["ID","作业单号","状态","相关单据号","作业类型名称","淘宝店铺ID","物流服务商简称","创建时间","计划执行总件数"],
		colNames: ["ID","序号","status",i18("CODE"),i18("INTSTATUS"),i18("REFSLIPCODE"),i18("INTTYPE"),i18("SHOPID"),i18("LPCODE"),i18("CREATETIME"),i18("STVTOTAL")],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name: "index", index: "index",width: 20, resizable: true},
		           {name: "status", index: "status",hidden: true},
		           {name: "code", index: "code", width: 120, resizable: true,formatter:"linkFmatter",formatoptions:{onclick:"showslipcode"}},
		           {name: "intStatus", index: "status", width: 80, resizable: true,formatter:'select',editoptions:intStatus},
		           {name: "refSlipCode", index: "slipCode", width: 120, resizable: true},
		           {name: "intType", index: "type", width: 100, resizable: true,formatter:'select',editoptions:staType},
		           {name: "shopId", index: "shopId", width: 120, resizable: true},
		           {name: "lpcode", index: "lpcode", width: 100, resizable: true,formatter:'select',editoptions:trasportName},
	               {name:"createTime",index:"createTime",width:150,resizable:true},
	               {name: "stvTotal", index: "stvTotal", width: 100, resizable: true}],
		caption: i18("INT_LIST"),// 作业单列表
		rowNum:-1,
	   	sortname: 'createTime',
	   	height:"auto",
	    multiselect: false,
		sortorder: "desc",
		rownumbers:true,
		gridComplete : function(){
		loxia.initContext($j(this));
	},
	jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#tbl-slipCode").jqGrid({
		datatype: "json",
		colNames: ["id","相关单据号","节点状态","操作人","执行时间"],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name: "slipCode", index: "slipCode", resizable: true ,hidden: true},
		           {name: "nodeType", index: "nodeType", width: 120, resizable: true},
		           {name: "userName", index: "userName", width: 80, resizable: true},
		           {name: "executionTime", index:"execution_time", width: 120, resizable: true},
		          ],
		          	caption: "作业单操作信息",
		            sortname: 'execution_time',
			  		multiselect: false,
					sortorder: "asc",
				   	height:jumbo.getHeight(),
				   	viewrecords: true,
			   		rownumbers:true,
					jsonReader: { repeatitems : false, id: "0" }
	});
   	
	
//	 if(wlist!=""){
//		 alert($j("#tbl-dispatch-list tr:eq(1)"));
//		 $j("#tbl-dispatch-list_wname").show();
//		 $j("#tbl-dispatch-list tr:eq(1)").each(function(){
//			 alert($j(this).children("td:eq(3)"));
//			 $j(this).children("td:eq(3)").show();
//		 });
//		}
    if(wlist!=""){
    	$j("#wnames").show();
    	$j("#wselTrans").show();
    }
	$j("#search").click(function(){
		
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
		
		var formOutBoundTime = getDate($j("#formOutBoundTime").val());
		var toOutBoundTime = getDate($j("#toOutBoundTime").val());
		if(formOutBoundTime > toOutBoundTime){
			jumbo.showMsg(i18("OUTBOUND_TIME_RULE"));//起始时间必须小于结束时间！
			return false;
		}
		
		var formCheckedTime = getDate($j("#formCheckedTime").val());
		var toCheckedTime = getDate($j("#toCheckedTime").val());
		if(formCheckedTime > toCheckedTime){
			jumbo.showMsg(i18("CHECKED_TIME_RULE"));//起始时间必须小于结束时间！
			return false;
		}
		var postData=loxia._ajaxFormToObj("plForm");
		postData["wid"]=$j("#wselTrans").val();
		for(var idx in wlist){
        postData["plList["+idx+"]"]=wlist[idx];
		}
		$j("#tbl-dispatch-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/matchListForModelOne.json"),
			postData:postData}).trigger("reloadGrid",[{page:1}]);
	});
	
	$j("#reset").click(function(){
		document.getElementById("plForm").reset();
	});
	
	$j("#back1").click(function(){
		$j("#back1").addClass("hidden");
		$j("#btnlist").removeClass("hidden");
		$j("#divTbslipCode").addClass("hidden");
		$j("#divTbDetial").removeClass("hidden");
	});
	$j("#back").click(function(){
			$j("#div2").addClass("hidden");
			$j("#showList").removeClass("hidden");
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
	
});
