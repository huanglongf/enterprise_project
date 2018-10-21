$j.extend(loxia.regional['zh-CN'],{
	DISTRIBUTION_LIST_SELECT : "请选择配货清单",
	
	EXPRESS_CITY : "快递省份一览表",
	PROVINCE : "省",
	CITY : "市",
	
	EXPRESS_ORDER_INFO : "快递信息查询结果列表",
	PICKUP_TIME : "揽件时间",
	EXPRESS_CODE : "快递单号",
	PCODE : "平台订单号",
	LP_NAME : "物流服务商",
	WAREHOUSE : "发件仓库",
	SHOP : "店铺",
	ORDER_CODE : "作业单号",
	EXPRESS_STATUS : "快递状态",
	WARNING_TYPE : "预警类型",
	WARNING_LEVEL : "预警级别",
	STANDARD_PERIOD : "标准时效",
	ACTUAL_PERIOD : "实际时效",
	UPDATE_TIME : "更新时间",
	NOTE : "备注",
	WARNING_CREATOR : "预警创建人/修改人",
	EXPRESS_DETAIL : "快递明细",
	ORDER_DETAIL : "订单明细",
	LOGISTICS_DETAIL : "物流明细",
	SKU_CODE : "SKU编码",
	BAR_CODE : "条形码",
	PRODUCT_NAME : "商品名称",
	EXTEND_PROPERTY : "扩展属性",
	PRODUCT_QUANTITY : "商品数量",
	PRICE : "平台售价",
	DETAIL_DESCRIPTION : "明细说明",
	SELECTPHYWAREHOUSE: "请选择",
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

var warningTypeList={};

function showDetail(tag){
	var baseUrl = $j("body").attr("contextpath");
	$j("#div1").addClass("hidden");
	$j("#div2").removeClass("hidden");
	$j("#tabs").removeClass("hidden");
	var id = $j(tag).parents("tr").attr("id");
	$j("#div2").attr("plId",id);
	$j("#selectId").html(id);
	var tr = $j(tag).parents("tr");
	var data=$j("#tbl-expressInfoList").jqGrid("getRowData",id);
	var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/findExpressDetailInfo.json",{"id":id});
	var sortingMode = 0;
	if(rs){
		$j("#lpCode1").html( data['lpCode']);
		$j("#expressCode1").html( data['expressCode']);
		$j("#orderCode1").html( data['orderCode']);
		$j("#pCode1").html( data['pcode']);
		$j("#owner1").html( data['owner']);
		$j("#pwhName1").html( data['pwhName']);
		$j("#province1").html( data['province']);
		$j("#city1").html( data['city']);
		$j("#receiver1").html( rs["receiver"]);
		$j("#mobile1").html( rs["mobile"]);
		$j("#refSlipCode").html( rs["telephone"]);
		$j("#address1").html( rs["address"]);
		$j("#status1").html( data['status']);
		$j("#warningType1").html( data['warningType']);
		$j("#warningLv1").html( data['warningLv']);
	}
	
	var expressCode = data['expressCode']
	var orderCode = data['orderCode'];
	var pcode = data['pcode'];
	$j("#tbl-expressDetail").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findExpressDetail.json?expressCode="+expressCode)}).trigger("reloadGrid");
	
	$j("#tbl-orderDetail").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findOrderDetail.json?orderCode="+orderCode)}).trigger("reloadGrid");
	
	$j("#tbl-logisticsDetail").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findLogisticsDetail.json?expressCode="+expressCode)}).trigger("reloadGrid");
}


function initPhy(){
	loxia.asyncXhrPost(
		window.parent.$j("body").attr("contextpath")+"/json/selectAllPhyWarehouse.do",
		{},
		{
			success:function(data){
				var rs = data.pwarelist;
				if(rs.length>0){
					$j("#pwhName option").remove();
					$j("#pwhName").append("<option value=''>"+i18("SELECTPHYWAREHOUSE")+"</option>");
					$j.each(rs,function(i,item){
						$j("#pwhName").append("<option value='"+item.id+"'>"+item.name+"</option>");
					});
				}
			}
		}
	);
}

function initProvince(){
	var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/findRadarAreaProvince.json"));
	for(var idx in rs){
		$j("<option value='" + rs[idx].province + "'>"+ rs[idx].province +"</option>").appendTo($j("#province"));
	}
}

function initStatus(){
	var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/findSysRouteStatusCodeList.json"));
	for(var idx in rs){
		$j("<option value='" + rs[idx].id + "'>"+ rs[idx].name +"</option>").appendTo($j("#status"));
	}
}

function initWarningType(){
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findOrderErrorCode.json");
	$j("<option value=''>"+'--请选择--'+"</option>").appendTo($j("#warningType2"));
	for(var idx in result){
		$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#warningType"));
		$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#warningType2"));
		warningTypeList[result[idx].id]=result[idx].code;
	}
}

function initWarningLv(){
	var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findRdWarningLv.json");
	for(var idx in rs){
		$j("<option value='" + rs[idx].id + "'>"+ rs[idx].name +"</option>").appendTo($j("#warningLv"));
	}
}

function initOwner(){
	var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/getExpressOwner.json"));
	for(var idx in rs){
		$j("<option value='" + rs[idx].owner + "'>"+ rs[idx].owner +"</option>").appendTo($j("#owner"));
	}
}
$j(document).ready(function (){
	//初始化物流商信息
	$j("#tabs").tabs();
	jumbo.loadTransportator("lpCode");
	initPhy();
	initOwner();
	initProvince();
	initStatus();
	initWarningType();
	initWarningLv();
	$j("#tbl-expressInfoList").jqGrid({
//		url:$j("body").attr("contextpath") + "/findAllExpressInfoList.json",
		datatype: "json",
		colNames: ["ID","outputCount",i18("PICKUP_TIME"),i18("EXPRESS_CODE"),i18("LP_NAME"),i18("WAREHOUSE"),i18("SHOP"),i18("PCODE"),i18("ORDER_CODE"),i18("EXPRESS_STATUS"),i18("WARNING_TYPE"),i18("WARNING_LEVEL"),i18("STANDARD_PERIOD"),i18("ACTUAL_PERIOD"),i18("PROVINCE"),i18("CITY")],
		colModel: [
				   {name: "id", index: "id", hidden: true},
		           {name: "outputCount", index: "outputCount", hidden: true},
		           {name: "takingTime", index: "takingTime", width: 100, resizable: true},
		           {name: "expressCode", index: "expressCode", formatter:"linkFmatter", formatoptions:{onclick:"showDetail"}, width:120,resizable:true},    
		           {name: "lpCode", index: "lpCode", width: 100, resizable: true},
		           {name: "pwhName", index: "pwhName", width: 100, resizable: true},
		           {name: "owner", index: "owner", width: 100, resizable: true},
		           {name: "pcode", index: "pcode", width: 100, resizable: true},
		           {name: "orderCode", index: "orderCode", width: 100, resizable: true},
		           {name: "status", index: "status", width: 100, resizable: true},
		           {name: "warningType", index: "warningType", width: 100, resizable: true},
		           {name: "warningLv", index: "warningLv", width: 100, resizable: true},
		           {name: "standardDate", index: "standardDate", width: 100, resizable: true},
		           {name: "actualDate", index: "actualDate", width: 100, resizable: true},
		           {name: "province", index: "province", width: 100, resizable: true},
		           {name: "city", index: "city", width: 100, resizable: true}
		           ],
		caption: i18("EXPRESS_ORDER_INFO"),// 待配货清单列表
		pager:"#express_order_pager_query",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: false,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc",
		jsonReader: { repeatitems : false, id: "0" },
	});

	//快递明细信息
	$j("#tbl-expressDetail").jqGrid({
		datatype: "json",
		valuesprmNames:{status:1},
		colNames: ["ID","outputCount",i18("EXPRESS_STATUS"),i18("UPDATE_TIME"),i18("WARNING_TYPE"),i18("WARNING_LEVEL"),i18("NOTE"),i18("WARNING_CREATOR")],
		colModel: [
				   {name: "id", index: "id", hidden: true},
		           {name: "outputCount", index: "outputCount", hidden: true},
		           {name: "status", index: "code", width:120,resizable:true},    
		           {name: "updateTime", index: "updateTime", width: 100, resizable: true},
		           {name: "warningType", index: "warningType", width: 100, resizable: true},
		           {name: "warningLv", index: "warningLv", width: 100, resizable: true},
		           {name: "remark", index: "remark", width: 100, resizable: true},
		           {name: "modifyUser", index: "modifyUser", width: 100, resizable: true},
		           ],
		caption: i18("EXPRESS_DETAIL"),// 待配货清单列表
		pager:"#pager_query",
	    rowNum: 100,
		rowList:jumbo.getPageSizeList(),
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: false,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc",
		hidegrid:false,
		jsonReader: { repeatitems : false, id: "0" },
	});
	
	//订单明细信息
	$j("#tbl-orderDetail").jqGrid({
		datatype: "json",
		valuesprmNames:{status:1},
		colNames: ["ID","outputCount",i18("SKU_CODE"),i18("BAR_CODE"),i18("PRODUCT_NAME"),i18("EXTEND_PROPERTY"),i18("PRODUCT_QUANTITY"),i18("PRICE")],
		colModel: [
				   {name: "id", index: "id", hidden: true},
		           {name: "outputCount", index: "outputCount", hidden: true},
		           {name: "code", index: "code", width:100,resizable:true},    
		           {name: "barCode", index: "barCode", width: 100, resizable: true},
		           {name: "name", index: "name", width: 100, resizable: true},
		           {name: "keyProperties", index: "keyProperties", width: 100, resizable: true},
		           {name: "count", index: "count", width: 100, resizable: true},
		           {name: "listPrice", index: "listPrice", width: 100, resizable: true},
		           ],
		caption: i18("ORDER_DETAIL"),// 待配货清单列表
		pager:"#pager_query",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: false,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc",
		hidegrid:false,
		jsonReader: { repeatitems : false, id: "0" },
	});
	
	//物流明细信息
	$j("#tbl-logisticsDetail").jqGrid({
		datatype: "json",
		valuesprmNames:{status:1},
		colNames: ["ID","outputCount",i18("UPDATE_TIME"),i18("DETAIL_DESCRIPTION")],
		colModel: [
				   {name: "id", index: "id", hidden: true},
		           {name: "outputCount", index: "outputCount", hidden: true},
		           {name: "operateTime", index: "operateTime", width:100,resizable:true},    
		           {name: "message", index: "message", width: 600, resizable: true},
		           ],
		caption: i18("LOGISTICS_DETAIL"),// 待配货清单列表
		pager:"#pager_query",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: false,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc",
		hidegrid:false,
		jsonReader: { repeatitems : false, id: "0" },
	});
	

	$j("#back,#back2,#back3").click(function(){
		$j("#div2").addClass("hidden");
		$j("#tabs").addClass("hidden");
		$j("#div1").removeClass("hidden");
		$j("#btnlist").addClass("hidden");
		$j("#errBtnlist").addClass("hidden");
		$j("#divTbDetial").addClass("hidden");
		$j("#metrobtn").addClass("hidden");
	});

	$j("#changeCode").click(function(){
		$j("#gbox_tbl-waittingList").appendTo($j("#hidden-waittingList"));
		var attr = $j("#hidden-waittingList").attr("class");
		if(attr == "hidden"){
			$j("#hidden-waittingList").removeClass("hidden");
		}else{
			$j("#hidden-waittingList").addClass("hidden");
		}
	});
	
	//查询按钮功能 根据作业单号和状态查询
	$j("#btn-query").click(function(){
		postData = {};
		postData["expressOrderQueryCommand.lpCode"]=$j("#lpCode").find("option:selected").text();
		postData["expressOrderQueryCommand.expressCode"]=$j("#expressCode").val();
		postData["expressOrderQueryCommand.pcode"]=$j("#pCode").val();
		postData["expressOrderQueryCommand.pwhName"]=$j("#pwhName").find("option:selected").text();
		postData["expressOrderQueryCommand.owner"]=$j("#owner").find("option:selected").text();
		postData["expressOrderQueryCommand.province"]=$j("#province").val();
		postData["expressOrderQueryCommand.city"]=$j("#city").val();
		postData["expressOrderQueryCommand.status"]=$j("#status").find("option:selected").text();
		postData["expressOrderQueryCommand.warningType"]=$j("#warningType").find("option:selected").text();
		postData["expressOrderQueryCommand.warningLv"]=$j("#warningLv").find("option:selected").text();
		postData["expressOrderQueryCommand.orderCode"]=$j("#orderCode").val();
		postData["expressOrderQueryCommand.startDate"]=$j("#startDate").val();
		postData["expressOrderQueryCommand.endDate"]=$j("#endDate").val();
		
		if("" != postData["expressOrderQueryCommand.endDate"]){
			if("" == postData["expressOrderQueryCommand.startDate"]){
				jumbo.showMsg("请选择起始时间");
				return false;
			}
		}
		if("" != postData["expressOrderQueryCommand.startDate"]){
			if("" == postData["expressOrderQueryCommand.endDate"]){
				postData["expressOrderQueryCommand.endDate"] = new Date();
			}
		}
		if(postData["expressOrderQueryCommand.startDate"] > postData["expressOrderQueryCommand.endDate"]){
			jumbo.showMsg("请选择正确的时间");
			return false;
		}
		$j("#tbl-expressInfoList").jqGrid('setGridParam',{
			url:window.$j("body").attr("contextpath")+"/json/findAllExpressInfoList.do",
			postData:postData,
			page:1
		}).trigger("reloadGrid");
	});
	
	
	$j("#btn-query1").click(function(){
		postData = {};
		postData["expressCode"] = $j("#expressCode1").text();
		postData["warningTypeId"]=$j("#warningType2").find("option:selected").val();
		postData["lvId"]=$j("#warningLv2").find("option:selected").val();
		if("" == postData["warningTypeId"] || "" == postData["lvId"] || "--请选择--" == postData["warningTypeId"] || "--请选择--" == postData["lvId"] ){
			jumbo.showMsg("请选择一个预警类型和一个预警级别");
			return false;
		}
		postData["remark"]=$j("#remark").val();
		var flag =loxia.syncXhrPost($j("body").attr("contextpath") + "/updateWarningType.json", postData);
		if(flag["flag"]=="success"){
			jumbo.showMsg("预警类型更新成功！");
		} else{
			jumbo.showMsg("预警类型更新失败！");
			return false;
		}
		$j("#tbl-expressInfoList").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/findAllExpressInfoList.json")}).trigger("reloadGrid");
		$j("#warningType1").html( $j("#warningType2").find("option:selected").text());
		$j("#warningLv1").html( $j("#warningLv2").find("option:selected").text());
		$j("#tbl-expressDetail").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/findExpressDetail.json?expressCode="+postData["expressCode"])}).trigger("reloadGrid");
	});
	
	//重置
	$j("#reset").click(function(){
		$j("#query-form input,#query-form select").val("");

	});
	
	//取消预警
	$j("#cancle").click(function(){
		postData = {};
		postData["expressCode"] = $j("#expressCode1").text();
		postData["warningTypeId"]="";
		postData["remark"]="";
		
		var flag = loxia.syncXhrPost($j("body").attr("contextpath") + "/updateWarningType.json", postData);
		if(flag["flag"]=="success"){
			jumbo.showMsg("取消预警成功！");
		} else{
			jumbo.showMsg("取消预警失败！");
			return false;
		}
		$j("#tbl-expressInfoList").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/findAllExpressInfoList.json")}).trigger("reloadGrid");
		$j("#warningType1").html("");
		$j("#warningLv1").html("");
		$j("#tbl-expressDetail").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/findExpressDetail.json?expressCode="+postData["expressCode"])}).trigger("reloadGrid");

	});
	

	//通过警告类型查找警告级别
	$j("#warningType2").change(function() {
		var warningType2 = warningTypeList[$j("#warningType2").val()];
		var postData = {};
		postData['errorCode'] = warningType2;
		if(warningType2 == ''){
			$j("#warningLv2").empty();
			$j("<option value=''>"+'--请选择预警类型--'+"</option>").appendTo($j("#warningLv2"));
		}else{
			var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/findOrderWarningLv.json"),postData);
			$j("#warningLv2").empty();
			$j("<option value=''>"+'--请选择--'+"</option>").appendTo($j("#warningLv2"));
			for(var idx in rs){
				$j("<option value='" + rs[idx].lvId + "'>"+ rs[idx].lvCode+'级' +"</option>").appendTo($j("#warningLv2"));
			}
	        $j("#warningLv2 option[value='null']").remove();
		}
	});
});
