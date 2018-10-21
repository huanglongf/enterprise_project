var $j = jQuery.noConflict(); 

$j.extend(loxia.regional['zh-CN'],{
	SKUNAME : "商品名称",
	JMSKUCODE : "商品编码",
	BARCODE : "条形码",
	QUANTITY : "数量",
	CHECKED_LIST : "已核对列表",
	KEY_PROP : "扩展属性",
	CODE : "作业单号",
	INTSTATUS : "状态",
	REFSLIPCODE : "相关单据号",
	PICKINGCODE : "配货批次号",
	INTTYPE : "作业类型名称",
	SHOPID : "平台店铺ID",
	LPCODE : "物流服务商简称",
	TRACKINGNO : "快递单号",
	RECEIVER : "收货联系人",
	CREATETIME : "创建时间",
	STVTOTAL : "计划执行总件数",
	MEMO : "备注",
	SALE_STA_LIST : "销售出库单列表",
	
	CANCELLED_FAILED : "作业单取消失败",
	ORDER_SELECT_PLEASE : "请选择作业单",
	ORDER_CANCEL : "作业单取消成功"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

var reload = false;
function showSta(staId){
	var baseUrl = $j("body").attr("contextpath");
	if(!reload){
		$j("#tbl-billView").jqGrid({
			url:baseUrl + "/findStaLineInfo.json",
			postData:{'staIds[0]':staId},
			datatype: "json",
			//colNames: ["ID","商品名称","JM SKU编码","条形码","数量"],
			colNames: ["ID",i18("SKUNAME"),i18("JMSKUCODE"),i18("KEY_PROP"),i18("BARCODE"),i18("QUANTITY")],
			colModel: [{name: "id", index: "id", hidden: true},
						{name:"skuName", index:"skuName" ,width:150,resizable:true},
						{name: "jmcode", index:"jmcode",width:150,resizable:true},
						{name: "keyProperties", index: "keyProperties", width: 100, resizable: true},
						{name:"barCode", index:"barCode", width:90, resizable:true},
						{name:"quantity",index:"quantity",width:120,resizable:true}],
			caption: i18("CHECKED_LIST"),//"已核对列表",
		   	sortname: 'ID',
		    multiselect: false,
			sortorder: "desc",
			height:"auto",
			rowNum: jumbo.getPageSize(),
		    rowList:jumbo.getPageSizeList(),
		    pager:"#pager",
			jsonReader: { repeatitems : false, id: "0" }
		});
		reload = true;
	}else{
		$j("#tbl-billView").jqGrid('setGridParam',{
			url:loxia.getTimeUrl(baseUrl + "/findStaLineInfo.json"),
			page:1,
			postData:{'staIds[0]':staId}
		}).trigger("reloadGrid");
	}
}

function showDetail(tag){
	var id = $j(tag).parents("tr").attr("id");
	var tr = $j(tag).parents("tr");
	$j("#detail").attr("staId",id);
	var data=$j("#tbl-order-cancel").jqGrid("getRowData",id);
	$j("#staCode").html(data["code"]);
	$j("#staCreateTime").html(data["createTime"]);
	$j("#staTransCode").html(data["lpcode"]);
	$j("#staType").html(tr.find("td[aria-describedby='tbl-order-cancel_intType']").html());
	$j("#staStatus").html(tr.find("td[aria-describedby='tbl-order-cancel_intStatus']").html());
	$j("#staReCode").html(data["refSlipCode"]);
	$j("#staMemo").html(data["memo"]);
	$j("#planCount").html(data["stvTotal"]);
	showSta(id);
	$j("#list").addClass("hidden");
	$j("#detail").removeClass("hidden");
}

$j(document).ready(function (){
	
	jumbo.loadShopList("companyshop","shopId");
	
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransportator.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selLpcode"));
	}
	
	
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-order-cancel").jqGrid({
		url:baseUrl + "/staNoFinishList.json",
		datatype: "json",
		//colNames: ["ID","作业单号","状态","相关单据号","配货批次号","作业类型名称","平台店铺ID","物流服务商简称","快递单号","收货联系人","创建时间","计划执行总件数","备注"],
		colNames: ["ID",i18("CODE"),i18("INTSTATUS"),i18("REFSLIPCODE"),i18("PICKINGCODE"),i18("INTTYPE"),i18("SHOPID"),i18("LPCODE"),i18("TRACKINGNO"),i18("RECEIVER"),i18("CREATETIME"),i18("STVTOTAL"),i18("MEMO")],
		colModel: [{name: "id", index: "id", hidden: true},
					{name:"code",index:"code",formatter:"linkFmatter",formatoptions:{onclick:"showDetail"},width:110,resizable:true},
					{name:"intStatus", index:"intStatus" ,width:60,resizable:true,formatter:'select',editoptions:staStatus},
					{name:"refSlipCode", index:"refSlipCode",width:100,resizable:true},
					{name:"pickingCode", index:"pickingCode", width:110, resizable:true},
					{name:"intType",index:"intType",width:90,resizable:true,formatter:'select',editoptions:staType},
					{name:"shopId",index:"shopId",width:110,resizable:true},
					{name:"lpcode", index:"lpcode" ,width:100,resizable:true,formatter:'select',editoptions:trasportName},
					{name:"trackingNo", index:"trackingNo",width:100,resizable:true},
					{name:"receiver", index:"receiver", width:80, resizable:true},
					{name:"createTime",index:"createTime",width:150,resizable:true},
	                {name:"stvTotal",index:"stvTotal",width:100,resizable:true},
					{name: "memo", index: "memo", hidden: true}],
		caption: i18("SALE_STA_LIST"),//"销售出库单列表",
	   	sortname: 'code',
	    multiselect: true,
		sortorder: "desc",
		// height:"auto",
		// rowNum:-1,
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:"auto",
	   	viewrecords: true,
   		rownumbers:true,
	   	pager:"#pager-cancel",
	   	
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#query").click(function(){
		$j("#tbl-order-cancel").jqGrid('setGridParam',{
			url:loxia.getTimeUrl(baseUrl+"/staNoFinishList.json"),
			postData:loxia._ajaxFormToObj("query-form"),
			page:1
		}).trigger("reloadGrid");
	});
	
	$j("#reset").click(function(){
		$j("#query-form input,select").val("");
	});
	
	$j("#doCancel").click(function(){
		var staIds = $j("#tbl-order-cancel").jqGrid('getGridParam','selarrrow');
		if(staIds.length>0){
			var postData = {};
			for(var i in staIds){
				postData['staIds[' + i + ']']=staIds[i];
			}
			var rs = loxia.syncXhrPost(baseUrl + "/cancelSta.json",postData);
			if(rs&&rs.result == 'success'){
				$j("#tbl-order-cancel tr").each(function(i,tag){
					for(var idx in staIds){
						if($j(tag).attr('id') == staIds[idx]){
							$j(tag).remove();
						}
					}
				});
			}else{
				$j("#tbl-order-cancel").jqGrid('setGridParam',{
					url:loxia.getTimeUrl(baseUrl+"/staNoFinishList.json"),
					postData:loxia._ajaxFormToObj("query-form"),
					page:1
				}).trigger("reloadGrid");
				if(rs && rs.message){
					var msg = '';
					for(var m in rs.message){
						msg += rs.message[m] + '<br/>';
					}
					jumbo.showMsg(msg);
				}else{
					jumbo.showMsg(i18("CANCELLED_FAILED"));//"作业单取消失败"
				}
				
			}
		}else{
			jumbo.showMsg(i18("ORDER_SELECT_PLEASE"));//请选择作业单
		}
	});
	
	
	$j("#cancelSta").click(function(){
		var id = $j("#detail").attr("staId");
		var postData = {};
		postData['staIds[0]']=id;
		var rs = loxia.syncXhrPost(baseUrl + "/cancelSta.json",postData);
		if(rs&&rs.result == 'success'){
			$j("#tbl-order-cancel tr").each(function(i,tag){
				if($j(tag).attr('id') == id){
					$j(tag).remove();
				}
			});
			jumbo.showMsg(i18("ORDER_CANCEL"));//作业单取消成功
		}else if(rs&&rs.result == 'error'){
			jumbo.showMsg(rs.message);
		}
	});
	
	$j("#toBack").click(function(){
		$j("#list").removeClass("hidden");
		$j("#detail").addClass("hidden");
	});

});

