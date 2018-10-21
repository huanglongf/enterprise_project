$j.extend(loxia.regional['zh-CN'],{

});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function showDetail(obj){
	
	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");
	var pl=$j("#tbl-query-list").jqGrid("getRowData",id);
	$j("#hoListId").text(id);
	$j("#code").text(pl["code"]);
	$j("#expName").text(pl["expName"]);
	$j("#billCount").text(pl["billCount"]);
	$j("#sender").text(pl["sender"]);
	$j("#partyAOperator").text(pl["partyAOperator"]);
	$j("#userName").text(pl["userName"]);
	$j("#tbl-query-detail").jqGrid('setGridParam',{page:1,url:$j("body").attr("contextpath") + "/queryHandOverListLine.json?hoListId=" + id}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl-query-detail",{},$j("body").attr("contextpath") + "/queryHandOverListLine.json",{"hoListId":id});
	$j("#list").addClass("hidden");
	$j("#detailed").removeClass("hidden");
}

$j(document).ready(function (){
	var baseUrl = $j("body").attr("contextpath");
	
	$j("#tbl-query-list").jqGrid({
		url : baseUrl + "/queryCheckHandOverList.json",
		datatype: "json",
		colNames: ["ID","交接清单编码","物流服务商","单据数量","包裹数量","总重量","发货方","宝尊交接人","创建人"],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name :"code",index:"code",width:150, formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, width:100, resizable:true},
				   {name :"expName",index:"expName",width:200,resizable:true},
				   {name :"billCount",index:"billCount",width:80,resizable:true},
				   {name :"packageCount",index:"packageCount",width:80,resizable:true},
				   {name :"totalWeight",index:"totalWeight",width:80,resizable:true},
				   {name :"sender",index:"sender",width:100,resizable:true},
				   {name :"partyAOperator",index:"partyAOperator",width:100,resizable:true},
				   {name :"userName",index:"userName",width:100,resizable:true}
				],
		caption: "交接清单列表", // 包裹清单
		sortname: 'expName',
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		height:jumbo.getHeight(),
	    multiselect: false,
	   	pager:"#pager",	   
		viewrecords:true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#tbl-query-detail").jqGrid({
		datatype: "json",
		colNames: ["ID","快递单号","相关单据号","包裹重量","收货人","是否扫描"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name :"trackingNo",index:"trackingNo",width:100,resizable:true},
					{name: "refSlipCode", index:"refSlipCode",width:100,resizable:true},
					{name:"weight", index:"weight", width:90, resizable:true},
					{name:"receiver",index:"receiver",width:90,resizable:true},
					{name: "isCheck", width: 120,resizable:true}],//作废
		caption: "明细列表",
		rowNum:-1,
	   	sortname: 'trackingNo',
	    multiselect: false,
		sortorder: "desc",
		height:"auto",
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#back").click(function(){
		$j("#list").removeClass("hidden");
		$j("#detailed").addClass("hidden");
	});
	
	$j("#billsId").keydown(function(evt){
		if(evt.keyCode === 13){
			var trackingNo = $j("#billsId").val().replace(/\s/ig,'');
			var bool = 0;
			$j("#tbl-query-detail tr:gt(0)>td[aria-describedby$='tbl-query-detail_id']").each(function (i,tag){
				var no = $j("#"+$j(tag).html()+" td[aria-describedby$='tbl-query-detail_trackingNo']").html();
				if(no == trackingNo){
					bool = 1;
					$j("#"+$j(tag).html()+" td[aria-describedby$='tbl-query-detail_isCheck']").html("已扫描");
					$j("#billsId").val("");
					return;
				} 
			});
			if(bool == 0) {
				jumbo.showMsg("快递单号:["+trackingNo+"]不在列表当中.");
			}
		}
	});
	
	$j("#submit").click(function(){
		var postData={},index = 0;
		postData["hoListId"]=$j("#hoListId").text();
		$j("#tbl-query-detail tr:gt(0)>td[aria-describedby$='tbl-query-detail_id']").each(function (i,tag){
			var isCheck = $j("#"+$j(tag).html()+" td[aria-describedby$='tbl-query-detail_isCheck']").html();
			if(isCheck == '已扫描'){
				postData['holine['+index+']'] = $j(tag).html();
				index++;
			}
		});
		if(index == 0){
			jumbo.showMsg("没有扫描的快递单");
			return;
//			if(!confirm("没有已经扫描的快递单，是否当前交接清单所有快递单号都作废？")){
//				return;
//			}
		}
		loxia.asyncXhrPost(
				$j("body").attr("contextpath") + "/json/checkHandOverList.do",
				postData,
				{
					success:function(data){
							if(data){
								if(data.result=="success"){
									jumbo.showMsg("交接成功");//交货清单取消成功
									$j("#tbl-query-list").jqGrid('setGridParam',{url: window.$j("body").attr("contextpath") + "/json/queryCheckHandOverList.json"}).trigger("reloadGrid");
									$j("#list").removeClass("hidden");
									$j("#detailed").addClass("hidden");
								}else if(data.result=="error"){
									jumbo.showMsg(data.message);
								}
							}
						},
					error:function(){jumbo.showMsg("操作数据异常");}//操作数据异常
				}
			);
	});
});
