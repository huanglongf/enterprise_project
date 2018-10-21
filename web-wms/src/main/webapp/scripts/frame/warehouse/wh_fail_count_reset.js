var extBarcode = null;
var outboundSn = null;
var getValues = null;

$j.extend(loxia.regional['zh-CN'],{
	INPUT_POSITIVE_INTEGER:"请输入大于零的整数"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

/**
 * 是否为正整数
 * @param s
 * @returns
 */
function isPositiveNum(s){  
    var re = /^[0-9]*[1-9][0-9]*$/ ;  
    return re.test(s)  
} 
/**
 * 判断是否为空
 * @param data
 * @returns
 */
function isNull(data){ 
	return (data == "" || data == undefined || data == null || data.length == 0) ? true : false; 
}

$j(document).ready(function (){
	var baseUrl = $j("body").attr("contextpath");
	initShopQuery("companyshop","innerShopCode");
	jumbo.loadShopList("companyshop");
	loxia.init({debug: true, region: 'zh-CN'});
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	
	$j("#tbl-inbound-purchase").jqGrid({
		url:$j("body").attr("contextpath")+"/json/findqueuestaparams.json",
		datatype: "json",
		colNames: ["ID","前置单据号","前置单据号1","前置单据号2","平台订单时间","店铺","申请单类型","错误次数","创建时间"],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},	
		           {name: "ordercode", index: "ordercode", hidden: false,sortable:false},
		           {name: "slipcode1", index: "slipcode1", hidden: false,sortable:false},	
		           {name: "slipcode2", index: "slipcode2", hidden: false,sortable:false},
		           {name: "ordercreatetime", index: "ordercreatetime", hidden: false,sortable:false},	
		           {name: "owner", index: "owner", hidden: false,sortable:false},
		           {name: "statusStr", index: "statusStr", hidden: false,sortable:false},
		           {name: "errorcount", index: "errorcount", hidden: false,sortable:false},
		           {name: "createtime", index: "createtime", hidden: false,sortable:false}
		           //{name:"remove", width: 60, resizable: true,formatter:"buttonFmatter", formatoptions:{"buttons":{label:"删除", onclick:"removeData(this);"}}}
		           ],
		caption: '过仓失败数据',
	   	//sortname: 'sta.create_time',
	   	//sortorder: "desc",
	   	multiselect: true,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pager",
	   	height:jumbo.getHeight(),
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	  
	});
	
	
	
	
	
	
	/**
	 * 查询
	 */
	$j("#search").click(function(){
		var url = $j("body").attr("contextpath") + "/findqueuestaparams.json";
		var numberUp = $j("#numberUp").val();	
		var amountTo = $j("#amountTo").val();
	
		// 数量条件仅允许录入正整数，允许只录入上限或者下限
		if (isNull(numberUp) && isNull(amountTo)) {
			jQuery("#tbl-inbound-purchase").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("form_query")}).trigger("reloadGrid",[{page:1}]);
			//jumbo.bindTableExportBtn("tbl-inventory-query",{},urlx,postData);
		}else if ((!isNull(numberUp) && isPositiveNum(numberUp)) && isNull(amountTo)) {
			jQuery("#tbl-inbound-purchase").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("form_query")}).trigger("reloadGrid",[{page:1}]);
			//jumbo.bindTableExportBtn("tbl-inventory-query",{},urlx,postData);
		}else if ((!isNull(amountTo) && isPositiveNum(amountTo)) && isNull(numberUp)) {
			jQuery("#tbl-inbound-purchase").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("form_query")}).trigger("reloadGrid",[{page:1}]);
			//jumbo.bindTableExportBtn("tbl-inventory-query",{},urlx,postData);
		}else if ((!isNull(numberUp) && isPositiveNum(numberUp)) && (!isNull(amountTo) && isPositiveNum(amountTo))) {
			jQuery("#tbl-inbound-purchase").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("form_query")}).trigger("reloadGrid",[{page:1}]);
			//jumbo.bindTableExportBtn("tbl-inventory-query",{},urlx,postData);
		}else {
			jumbo.showMsg(i18("INPUT_POSITIVE_INTEGER"));
		}
	});
	
	
	/**
	 * 重置
	 */
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val(0);
	});
	
	/**
	 * 重置为0
	 */
	$j("#resetToZero").click(function(){
		var ids = $j("#tbl-inbound-purchase").jqGrid('getGridParam','selarrrow');
		if (null != ids && ids.length > 0) {
			if (confirm("确定重置勾选的数据？")) {
				var ids = ids.join(',');
				var rs = loxia.syncXhr($j("body").attr("contextpath") + "/json/resetToZeroByWh.do",{"ids":ids});
				if(rs && rs.msg == "success"){
					jumbo.showMsg("重置成功!~");
				}else{
					jumbo.showMsg("系统异常,请联系管理员!~");
				}
			}
		}else {
			jumbo.showMsg("请勾选需要操作的数据,谢谢!~");
		}
		$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
			url:$j("body").attr("contextpath")+"/json/findqueuestaparams.json"}).trigger("reloadGrid",[{page:1}]);
	});

	
	/**
	 * 重置为99
	 */
	$j("#resetTo99").click(function(){
		var ids = $j("#tbl-inbound-purchase").jqGrid('getGridParam','selarrrow');
		if (null != ids && ids.length > 0) {
			if (confirm("确定重置勾选的数据？")) {
				var ids = ids.join(',');
				var rs = loxia.syncXhr($j("body").attr("contextpath") + "/json/resetTo99.do",{"ids":ids});
				if(rs && rs.msg == "success"){
					jumbo.showMsg("重置成功!~");
				}else{
					jumbo.showMsg("系统异常,请联系管理员!~");
				}
			}
		}else {
			jumbo.showMsg("请勾选需要操作的数据,谢谢!~");
		}
		$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
			url:$j("body").attr("contextpath")+"/json/findqueuestaparams.json"}).trigger("reloadGrid",[{page:1}]);
	});
	
		
});









///**
// * 删除
// * @param obj
// * @param event
// */
//function removeData(obj){
//	var objId = $j(obj).parent().parent().attr("id");
//	if (null != objId && objId.length > 0 && '' != objId) {
//		if (confirm("确定删除本条数据？")) {
//			var rs =loxia.syncXhr($j("body").attr("contextpath") + "/json/deletedatabyid.json?idStr=" + objId);
//			if(rs && rs.msg == "success"){
//				jumbo.showMsg("删除并保存日志成功!~");
//			}else if (rs && rs.msg == "none") {
//				jumbo.showMsg("无法获取数据ID,系统异常,请联系管理员!~");
//			}else{
//				jumbo.showMsg("系统异常,请联系管理员!~");
//			}
//		}
//	}else {
//		jumbo.showMsg("无法获取数据ID,系统异常,请联系管理员!~");
//	}
//	$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
//		url:$j("body").attr("contextpath")+"/json/findmsgtowcsbyparams.json"}).trigger("reloadGrid",[{page:1}]);
//}


