function showMsg(msg){
	jumbo.showMsg(msg);
}
var $j = jQuery.noConflict();
function showdetail(obj){
	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");
	$j("#sta_id").val(id);
	var pl=$j("#tbl_sta_list").jqGrid("getRowData",id);
	$j("#sta_code").text(pl["code"]);
	$j("#sta_refSlipCode").text(pl["refSlipCode"]);
	$j("#sta_slipCode1").text(pl["slipCode1"]);
	$j("#sta_createTime").text(pl["createTime"]);
	$j("#sta_type").text(tr.find("td[aria-describedby$='intStaType']").text());
	$j("#sta_status").text(tr.find("td[aria-describedby$='intStaStatus']").text());
	$j("#sta_memo").text(pl["memo"]);
	showReceiptNumber();
	if(tr.find("td[aria-describedby$='intStaStatus']").text() == '已完成'){
		$j("#confirm").addClass("hidden");
	}else{
		$j("#confirm").removeClass("hidden");
	}
	$j("#divHead").addClass("hidden");
	$j("#divDetial").removeClass("hidden");
	$j("#skuBarCode").focus();
}
//如果数据超过500行  建议使用自动入货方式
function prompt(){
//	var datalist = $j("#tbl_sta_line_list" ).getRowData();
//	if(datalist.length>500){
//		jumbo.showMsg("由于数据量过多，建议您使用自动收货！");
//	}
}
function showDetail(obj,plid){
	$j("#skuBarCode").val("");
	if(plid){
		var inputvalue=$j("#addnum").val();
		var addnum=new Number(inputvalue);
		var addQuantity=$j("#"+plid).find("input[name=addQuantity]").val();
		var inputnum=new Number(addQuantity);
		$j("#"+plid).find("input[name=addQuantity]").val(addnum+inputnum);
	}else {
			jumbo.showMsg("未找到匹配的信息！");
			return;
	}
}
//在明细页里填充对应单据信息
function putInfo(){
	
}
var staType;
var staStatus;
$j(document).ready(function (){

	var baseUrl = $j("body").attr("contextpath");
	initShopQuery("companyshop","innerShopCode");
	jumbo.loadShopList("companyshop");
	loxia.init({debug: true, region: 'zh-CN'});
	staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	$j("#tbl_sta_list").jqGrid({
		url:baseUrl+"/findInboundStaFinish.json",
		datatype: "json",
		colNames: ["ID","作业单号","相关单据号","辅助相关单据号","状态","作业类型","店铺","CHANNELNAME","创建时间","备注"],
		colModel: [
		           {name: "id", index: "id", hidden: true},	
		           {name: "code", index: "code",formatter:"linkFmatter", formatoptions:{onclick:"showdetail"}, width: 110, resizable: true,sortable:false},
		           {name: "refSlipCode", index: "refSlipCode", width: 100, resizable: true},
		           {name: "slipCode1", index: "slipCode1", hidden: true},
		           {name: "intStaStatus", index: "intStaStatus", width: 80, resizable: true,formatter:'select',editoptions:staStatus},
	               {name: "intStaType", index: "intStaType", width: 120, resizable: true,formatter:'select',editoptions:staType},
	               {name: "channelName", index: "channelName", width: 120, resizable: true},
	               {name: "owner", index: "owner", width: 120, resizable: true,hidden:true},
	               {name: "createTime", index: "createTime", width: 150, resizable: true},
	               {name: "memo", index: "memo", width: 150, resizable: true}
	               ],
		caption: "入库作业单",
	   	sortname: 'sta.id',
		pager:"#pager",
	    multiselect: false,
		sortorder: "desc",
		height:"auto",
		rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});

	//商品明细
	$j("#tbl_sta_line_list").jqGrid({
		datatype: "json",
		colNames: ["ID","skuId","商品条码","货号","商品名称","执行量"],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},	
		           {name: "skuId", index: "skuId" ,hidden: true,sortable:false},
		           {name: "barCode", index: "barCode", width: 160, resizable: true,sortable:false},//商品条码
		           {name: "supplierCode", index: "supplierCode", width: 160, resizable: true,sortable:false},//货号
		           {name: "skuName", index: "skuName", width: 230, resizable: true,sortable:false},//商品名称
		           {name: "receiptQty",index: "receiptQty",width: 160, resizable: true,sortable:false}//执行量
	               ],
		       		caption: "入库明细单",
		    	   	sortname: 'sku.id',
		    	   	pager:"#pager1",
		    	    multiselect: false,
		    		sortorder: "desc",
		    		height:"auto",
		    		viewrecords: true,
					rowNum: jumbo.getPageSize(),
				    rowList:jumbo.getPageSizeList(),
		       		rownumbers:true,
//		    	   	gridComplete : function(){
//						loxia.initContext($j(this));
//						prompt();
//					},
		    		jsonReader: { repeatitems : false, id: "0" }
	});
	$j("#search").click(function(){
		queryStaList();
	});
	
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
	});
	
	
	
	$j("#back").click(function(){
		$j("#divDetial").addClass("hidden");
		$j("#divHead").removeClass("hidden");
	});
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});

	//商品条码输入框  按下回车键触发的事件
	$j("#skuBarCode").keydown(function(evt){
		if(evt.keyCode === 13){
			var barCode = $j("#skuBarCode").val().trim();
			if(barCode == ""){
				return ;
			}
			var isFinish = false;
			var plid = null;
			var data = $j("#tbl_sta_line_list").jqGrid('getRowData');
			$j.each(data,function(i, pl){
				if(!isFinish && pl["barCode"] == barCode){
					isFinish = true;
					plid = pl["id"];
				}
			});
			showDetail(null,plid);
		}
	});
	
	
	/**确认按钮事件	 */
	$j("#confirm").click(function(){
		var postData = {};
		postData["sta.id"]= $j("#sta_id").val();
		var datalist = $j("#tbl_sta_line_list" ).getRowData();
			if(confirm("确定关闭此收货单据？")){
				loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+ "/json/closeInBoundFinish.json",
						postData,
						{
						success:function(data){
							if(data){
								if(data.result=="success"){
									jumbo.showMsg("操作成功");
									$j("#divDetial").addClass("hidden");
									$j("#divHead").removeClass("hidden");
									queryStaList();
								}else if(data.result=="error"){
									jumbo.showMsg(data.message);
								}
							} else {
								jumbo.showMsg("数据操作异常");
							}
						},
						error:function(){jumbo.showMsg("数据操作异常");}//操作数据异常
				});
			}
	});
});
//重置按钮的方法
function printSingleDelivery(tag,event){
	var id = $j(tag).parents("tr").attr("id");
	$j("#"+id).find("input[name=addQuantity]").val("");
} 


function queryStaList(){
	var url = $j("body").attr("contextpath") + "/findInboundStaFinish.json";
	$j("#tbl_sta_list").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_sta_list",{"intStaType":"whSTAType","intStaStatus":"whSTAStatus"},
		url,loxia._ajaxFormToObj("queryForm"));
}

function showReceiptNumber(){
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl_sta_line_list").jqGrid('setGridParam',{page:1,url:baseUrl + "/findStaLineByStaHand.json?sta.id=" + $j("#sta_id").val()}).trigger("reloadGrid");
}


