var $j = jQuery.noConflict();

$j.extend(loxia.regional['zh-CN'], {
	STACODE : "作业单号",
	SLIPCODE : "相关单据号",
	STATUS: "状态",
	CREATETIME : "创建时间",
	LPCODE : "物流服务商",
	INBOUNDTIME : "入库时间",
	
	BARCODE : "商品条码",
	SKUCODE : "商品编码",
	QTY : "数量",
	RTN_LINE : "明细",
	
	DATE_RULE : "起始时间必须小于结束时间！",
	MSG_QUERY_LIST : "入库过仓日志查询列表",
	INIT_SYSTEM_DATA_EXCEPTION : "初始化系统参数异常"
});

function i18(msg, args) {
	return loxia.getLocaleMsg(msg, args);
}


function showMsg(msg){
	jumbo.showMsg(msg);
}

function getDate(strDate){
	var s = new Date(strDate.substring(0,10));
	s.setHours(strDate.substring(11,13));
	s.setMinutes(strDate.substring(11,13));
	s.setSeconds(strDate.substring(11,13));
	return s;
}

$j(document).ready(function() {
	var baseUrl = $j("body").attr("contextpath");
	$j("#tabs").tabs();
	
	// tab1
	$j("#query").click(function(){
		var st = getDate($j("#startDate").val());
		var ed = getDate($j("#endDate").val());
		var ouId = $j("#selVMIWarehouse").val();
		if(ouId == ""){
			jumbo.showMsg("请选择仓库");
			return false;
		}
		if(st > ed){
			jumbo.showMsg(i18("DATE_RULE"));//起始时间必须小于结束时间！
			return false;
		}
	    var urlx = loxia.getTimeUrl(baseUrl+"/findCurrentMsgInboundOrderRoot.json");
	    var postData=loxia._ajaxFormToObj("quert-form");
	   
	    jQuery("#tbl-inventory-query").jqGrid('setGridParam',{url:urlx,postData:postData}).trigger("reloadGrid",[{page:1}]);
	    jumbo.bindTableExportBtn("tbl-inventory-query",{"intStatus":"msgOrderStatus"},urlx,postData);
	});
	
	$j("#reset").click(function(){
		$j("#quert-form input").val("");
		$j("#quert-form select").val("");
	});
	var msgStatus=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"msgOrderStatus"});
	var msgRtnStatus=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"msgRtnInboundStatus"});
	var wareHouseResult = loxia.syncXhrPost(baseUrl + "/getVMIWarehouse.json");
	for(var idx in wareHouseResult){
		$j("<option value='" + wareHouseResult[idx].ouId + "'>"+ wareHouseResult[idx].name +"</option>").appendTo($j("#selVMIWarehouse"));
	}
	for(var idx in wareHouseResult){
		$j("<option value='" + wareHouseResult[idx].ouId + "'>"+ wareHouseResult[idx].name +"</option>").appendTo($j("#selVMIWarehouse2"));
	}
	if(!msgStatus.exception && !msgRtnStatus.exception){
		$j("#tbl-inventory-query").jqGrid({
			postData : loxia._ajaxFormToObj("quert-form"),
			datatype: "json",
	
			//colNames: ["作业单号","单据编码","状态","创建时间","快递供应商"]
			colNames: ["ID",i18("STACODE"),i18("SLIPCODE"),i18("STATUS"),i18("CREATETIME"),i18("LPCODE")],
	
			colModel: [ 
			           {name: "id", index: "id", hidden: true},
			           {name: "staCode", index: "staCode", width: 100,  resizable: true},
			           {name: "slipCode", index: "slipCode", width: 100, resizable: true},
			           {name: "intStatus", index: "intStatus", width: 60, resizable: true,
			        	   formatter:'select',editoptions:msgStatus},
			           {name: "createTime", index: "createTime", width: 130, resizable: true},
			           {name: "lpCode", index: "lpCode", width: 100, resizable: true}],
			caption: i18("MSG_QUERY_LIST"),//库存查询列表
			rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
		   	sortname: 'msg.create_time',
		    pager: '#pager1',
		    multiselect: false,
			sortorder: "desc", 
			height:jumbo.getHeight(),
			jsonReader: { repeatitems : false, id: "0" },
			viewrecords: true,
	   		rownumbers:true,
			subGrid : false
		});
		
		$j("#tbl-inventory-query2").jqGrid({
			postData : loxia._ajaxFormToObj("quert-form2"),
			datatype: "json",
	
			//colNames: ["作业单号","单据编码","状态","创建时间","地址","快递供应商"]
			colNames: ["ID",i18("STACODE"),i18("SLIPCODE"),i18("STATUS"),i18("CREATETIME"),i18("INBOUNDTIME")],
	
			colModel: [ 
			           {name: "id", index: "id", hidden: true},
			           {name: "staCode", index: "staCode",formatter:"linkFmatter",formatoptions:{onclick:"showDetail"},width:100,resizable:true},
			           {name: "slipCode", index: "slipCode", width: 100, resizable: true},
			           {name: "intStatus", index: "intStatus", width: 60, resizable: true,
			        	   formatter:'select',editoptions:msgRtnStatus},
			           {name: "createTime", index: "createTime", width: 130, resizable: true},
			           {name: "inboundTime", index: "inboundTime", width: 130, resizable: true}],
			caption: i18("MSG_QUERY_LIST"),//库存查询列表
			rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
		   	sortname: 'msg.create_time',
		    pager: '#pager2',
		    multiselect: false,
			sortorder: "desc", 
			height:jumbo.getHeight(),
			jsonReader: { repeatitems : false, id: "0" },
			viewrecords: true,
	   		rownumbers:true,
			subGrid : false
		});
		
		$j("#tbl-line-detial").jqGrid({
			datatype: "json",
	
			//colNames: ["ID","商品条码","商品编码","数量"]
			colNames: ["ID",i18("BARCODE"),i18("SKUCODE"),i18("QTY")],
	
			colModel: [ 
			           {name: "id", index: "id", hidden: true},
			           {name: "barcode", index: "barcode",width:100,resizable:true},
			           {name: "skuCode", index: "skuCode", width: 100, resizable: true},
			           {name: "qty", index: "qty", width: 60, resizable: true}
			          ],
			caption: i18("RTN_LINE"),//库存查询列表
			rowNum:-1,
		   	sortname: 'id',
		    multiselect: false,
			sortorder: "desc",
			height:"auto",
			jsonReader: { repeatitems : false, id: "0" }
		});
	}else{
		jumbo.showMsg(i18("INIT_SYSTEM_DATA_EXCEPTION"));//初始化系统参数异常
	}
	jumbo.bindTableExportBtn("tbl-inventory-query", {"intStatus":"msgOrderStatus"});
	jumbo.bindTableExportBtn("tbl-inventory-query2", {"intStatus":"msgRtnInboundStatus"});
	
	// tab2
	$j("#query2").click(function(){
		var st = getDate($j("#rtnStartDate").val());
		var ed = getDate($j("#rtnEndDate").val());
		var ouId = $j("#selVMIWarehouse2").val();
		if(ouId == ""){
			jumbo.showMsg("请选择仓库");
			return false;
		}
		if(st > ed){
			jumbo.showMsg(i18("DATE_RULE"));//起始时间必须小于结束时间！
			return false;
		}
		var urlx = loxia.getTimeUrl(baseUrl+"/findCurrentMsgRtnInboundRoot.json");
		var postData=loxia._ajaxFormToObj("quert-form2");
		   
		jQuery("#tbl-inventory-query2").jqGrid('setGridParam',{url:urlx,postData:postData}).trigger("reloadGrid",[{page:1}]);
		jumbo.bindTableExportBtn("tbl-inventory-query2",{"intStatus":"msgRtnInboundStatus"},urlx,postData);
	});
		
	$j("#reset2").click(function(){
		$j("#quert-form2 input").val("");
		$j("#quert-form2 select").val("");
	});
	
	$j("#back").click(function(){
		$j("#divRtnList").removeClass("hidden");
		$j("#divRtnDeital").addClass("hidden");
	});
});

function showDetail(tag){
	var postData = {};
	var id =$j(tag).parents("tr").attr("id");
	$j("#divRtnList").addClass("hidden");
	$j("#divRtnDeital").removeClass("hidden");
	$j("#divRtnDeital").attr("rtnInboundId",id);
	postData["rtnInId"] = id;
	loxia.asyncXhrPost(
			$j("body").attr("contextpath") + "/json/findRtnInboundDetailById.do",
			postData,
			{
				success:function(data){
						if(data){
							var rs = data.rtnInbounDetail;
							showDetailRtnInboundListInfo(rs);
						}
					},
				error:function(){jumbo.showMsg(i18("DATA_ERROR"));}//操作数据异常
			}
	);
	$j("#tbl-line-detial").jqGrid('setGridParam',{url:loxia.getTimeUrl(window.$j("body").attr("contextpath") + "/json/findMsgRtnInboundLineById.json?rtnInId="+id)}).trigger("reloadGrid");
}

function showDetailRtnInboundListInfo(rs){
	$j("#d_staCode").html(rs.staCode);
	$j("#d_slipCode").html(rs.slipCode);
	$j("#d_createTime").html(rs.createTime);
}
