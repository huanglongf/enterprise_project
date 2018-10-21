var $j = jQuery.noConflict();

$j.extend(loxia.regional['zh-CN'],{
	CODE : "作业单号",
	STATUS : "状态",
	REFSLIPCODE : "相关单据号",
	BATCHNO : "配货批次号",
	TYPE : "作业类型名称",
	SHOPID : "平台店铺ID",
	LPCODE : "物流服务商简称",
	TRACKINGNO : "快递单号",
	RECEIVER : "收货联系人",
	CREATETIME : "创建时间",
	STVTOTAL : "计划执行总件数",
	SALE_STA_LIST : "销售出库单列表",

	LOCATIONCODE : "库位",
	SKUNAME : "商品名称",
	JMSKUCODE : "商品编码",
	QUANTITY : "数量",
	SUGGESTION : "建议列表",
	WNAME:"仓库名称",
	OPERATE_SUCCESS : "库存释放成功",
	OPERATE_FAILED : "操作失败",
	ORDER_SELECT_PLEASE : "请选择作业单",
	LOCATION_CODE_NOT_EXIST : "库位编码不存在",
	LOCATION_STATUS_INVALID : "库存状态无效",
	LOCATION_LOCKED : "库存被锁定"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
var wlist =null;
$j(document).ready(function (){
	jumbo.loadShopList("shopId","shopId");
	wlist = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/checkoperationunit.json");
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransportator.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selLpcode"));
	}
	var result2= loxia.syncXhrPost($j("body").attr("contextpath")+"/selectbyopc.json");
	for(var idx in result2.warelist){
		$j("<option value='" + result2.warelist[idx].id + "'>"+ result2.warelist[idx].name +"</option>").appendTo($j("#wselTrans"));
	}
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	
	var baseUrl = $j("body").attr("contextpath");
	var postDataO = {};
	for(var idx in wlist){
		postDataO["plList["+idx+"]"]=wlist[idx];
    }
	$j("#tbl-Cancelled-list").jqGrid({
		//url:baseUrl+ "/staNoCancelledList.json",
		datatype: "json",
		//colNames: ["ID","作业单号","状态","相关单据号","配货批次号","作业类型名称","平台店铺ID","物流服务商简称","快递单号","收货联系人","创建时间","计划执行总件数"],
		colNames: ["ID",i18("WNAME"),i18("CODE"),i18("STATUS"),i18("REFSLIPCODE"),i18("BATCHNO"),i18("TYPE"),i18("SHOPID"),i18("LPCODE"),i18("TRACKINGNO"),i18("RECEIVER"),i18("CREATETIME"),i18("STVTOTAL")],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name:"mainName",index:"mainName",width:120,resizable:true},
					{name:"code",index:"code",width:110,resizable:true},
					{name:"intStatus", index:"intStatus" ,width:80,resizable:true,formatter:'select',editoptions:staStatus},
					{name:"refSlipCode", index:"refSlipCode",width:100,resizable:true},
					{name:"pickingCode", index:"pickingCode", width:110, resizable:true},
					{name:"intType",index:"type",width:90,resizable:true,formatter:'select',editoptions:staType},
					{name:"shopId",index:"shopId",width:110,resizable:true},
					{name:"lpcode", index:"lpcode" ,width:100,resizable:true,formatter:'select',editoptions:trasportName},
					{name:"trackingNo", index:"jmCodetrackingNo",width:100,resizable:true},
					{name:"receiver", index:"receiver", width:80, resizable:true},
					{name:"createTime",index:"createTime",width:150,resizable:true},
	               {name:"stvTotal",index:"stvTotal",width:100,resizable:true}],
		caption: i18("SALE_STA_LIST"),//销售出库单列表
	   	sortname: 'code',
	    multiselect: true,
		sortorder: "desc",
		height:"auto",
		
		pager:"#pager_query",
		rowNum: 10,
		rowList:[10,20,40],
	    rownumbers:true,
	    viewrecords: true,
		jsonReader: { repeatitems : false, id: "0" },
		postData:postDataO
	});
	 if(wlist!=""){
	    	$j("#wnames").show();
	    	$j("#wselTrans").show();
	    }
	$j("#search").click(function(){
		if(wlist !=""){
		var cenid=$j("#wselTrans").val();
		if(cenid==""){
			jumbo.showMsg("请选择仓库！");
			return;
		}
		}
		var createTime=$j("#ksTime").val();
		var endTime =$j("#jsTime").val();
//		if(createTime =="" || endTime =="" ){
//			jumbo.showMsg("创建时间不能为空");
//			return;
//		}
		var postData=loxia._ajaxFormToObj("query-form");
		postData["wid"]=$j("#wselTrans").val();
		for(var idx in wlist){
        postData["plList["+idx+"]"]=wlist[idx];
		}
		postData["createTime2"]=createTime;
		postData["endCreateTime2"]=endTime;

		$j("#tbl-Cancelled-list").jqGrid('setGridParam',{
			url:loxia.getTimeUrl(baseUrl+"/staNoCancelledList.json"),
			postData:postData,page:1
		}).trigger("reloadGrid");
	});
	
	$j("#reset").click(function(){
		$j("#query-form input").val("");
	});
	
		$j("#tbl-suggestion").jqGrid({
			url:baseUrl+ "/suggestion.json",
			datatype: "json",
			colNames: ["ID",i18("LOCATIONCODE"),i18("SKUNAME"),i18("JMSKUCODE"),i18("QUANTITY")],
			colModel: [{name: "id", index: "id", hidden: true},
						{name:"locationCode", index:"locationCode" ,width:150,resizable:true},
						{name:"skuName", index:"skuName",width:200,resizable:true},
						{name:"barCode", index:"barCode", width:150, resizable:true},
						{name:"quantity",index:"quantity",width:100,resizable:true}],
			caption: i18("SUGGESTION"),//建议列表
		   	sortname: 'ID',
		    multiselect: false,
			sortorder: "desc",
			height:"auto",
			rowNum:-1,
			jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#suggestion-view").click(function(){
		$j("#tbl-suggestion tr:gt(0)").remove();
		var valuelist = $j("#tbl-Cancelled-list").jqGrid("getGridParam","selarrrow");
		if(valuelist.length > 0){
			var postData = {};
			for(var i in valuelist){
				postData['staIds[' + i + ']']=valuelist[i];
			}
			var array=done(postData);
			
			$j("#tbl-suggestion").jqGrid('setGridParam',{page:1,url:loxia.getTimeUrl(baseUrl + "/suggestion.json?"+array.join("&"))}).trigger("reloadGrid");
		}else{
			jumbo.showMsg(i18("ORDER_SELECT_PLEASE"));//请选择作业单
		}
	});
	
	$j("#cancelLocation").click(function(){
		var staList = $j("#staList").val();
		if(staList.length>20000){
			jumbo.showMsg("内容太长，不能超过1000单");//操作失败
			return;
		}
		var ids = $j("#tbl-Cancelled-list").jqGrid('getGridParam','selarrrow');
		if(ids.length>0 || staList != ''){
			var appointStorage = $j("#appointStorage").val();
			var postData = {};
			for(var i in ids){
				postData['staIds['+i+']']=ids[i];
			}
			postData['staList']=staList;
			if (isNull(appointStorage)) {
				if (confirm("库存将全部释放回到原始拣货库位，请确认")) {
					//var array=done(postData);
					//var rs = loxia.syncXhrPost(baseUrl + "/releaseInventory.json?"+array.join("&"));
					var rs = loxia.syncXhrPost(baseUrl + "/releaseInventory.json",postData);
					if(rs && rs.result == "success"){
						jumbo.showMsg(i18("OPERATE_SUCCESS"));//库存释放成功
					}else if(rs && rs.result == "error"){
						jumbo.showMsg(rs.message);
					}else{
						jumbo.showMsg(i18("OPERATE_FAILED"));//操作失败
					}
				}
			}else {
				if (verifyLocationCode(appointStorage)) {
					if (confirm("库存将全部释放到" + appointStorage + "库位，请确认")) {
						postData['appointStorage'] = appointStorage;
						var rs = loxia.syncXhrPost(baseUrl + "/releaseInventory.json",postData);
						if(rs && rs.result == "success"){
							jumbo.showMsg(i18("OPERATE_SUCCESS"));//库存释放成功
						}else if(rs && rs.result == "error"){
							jumbo.showMsg(rs.message);
						}else{
							jumbo.showMsg(i18("OPERATE_FAILED"));//操作失败
						}
					}
				}
			}
		}else{
			jumbo.showMsg(i18("ORDER_SELECT_PLEASE"));//请选择作业单
		}
		var postData=loxia._ajaxFormToObj("query-form");
		postData["wid"]=$j("#wselTrans").val();
		for(var idx in wlist){
        postData["plList["+idx+"]"]=wlist[idx];
		}
		$j("#tbl-Cancelled-list").jqGrid('setGridParam',{
				url:loxia.getTimeUrl(baseUrl+"/staNoCancelledList.json"),
				postData:postData,page:1
			}).trigger("reloadGrid");
	});
	
	/**
	 * 校验库位编码
	 */
	$j("#appointStorage").blur(function(){
		var appointStorage = $j("#appointStorage").val();
		if (isNull(appointStorage)) {
			return;
		}else {
			verifyLocationCode(appointStorage);
		}
	});
	 
});

function done(postData){
	var array=[];
	for ( var prefix in postData ) {
		array.push(prefix+"="+postData[prefix]);
	}
	return array;
}

/**
 * 校验指定库位编码
 * @param appointStorage
 * @returns {Boolean}
 */
function verifyLocationCode(appointStorage) {
	var baseUrl = $j("body").attr("contextpath");
	var res = true;
	var rs = loxia.syncXhrPost(baseUrl + "/verifylocationcode.json",{"appointStorage":appointStorage});
	if(rs && rs.result == "LOCATION_CODE_NOT_EXIST"){
		jumbo.showMsg(i18("LOCATION_CODE_NOT_EXIST")); // 库位编码不存在
		$j("#appointStorage").val("");
		res = false;
	}else if(rs && rs.result == "LOCATION_STATUS_INVALID"){
		jumbo.showMsg(i18("LOCATION_STATUS_INVALID")); // 库存状态无效
		$j("#appointStorage").val("");
		res = false;
	}else if(rs && rs.result == "LOCATION_LOCKED"){
		jumbo.showMsg(i18("LOCATION_LOCKED")); // 库存被锁定
		$j("#appointStorage").val("");
		res = false;
	}else if(rs && rs.result == "error"){
		jumbo.showMsg("系统异常,请联系管理员"); // 库存被锁定
		$j("#appointStorage").val("");
		res = false;
	}
	return res;
}
/**
 * 判断是否为空
 * @param data
 * @returns
 */
function isNull(data){ 
	return (data == "" || data == undefined || data == null || data.length == 0) ? true : false; 
}
