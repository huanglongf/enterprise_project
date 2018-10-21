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
	$j("#divHead").addClass("hidden");
	$j("#divDetial").removeClass("hidden");
	$j("#skuBarCode").focus();
}
//如果数据超过500行  建议使用自动入货方式
function prompt(){
	var datalist = $j("#tbl_sta_line_list" ).getRowData();
	if(datalist.length>500){
		jumbo.showMsg("由于数据量过多，建议您使用自动收货！");
	}
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
function showdetails(obj){
	var postData = {};
	postData["silpCode"]=obj;
	var rs = loxia.syncXhr($j("body").attr("contextpath")+ "/json/findStaBySlipCode.json",postData);
	if (rs && rs.result && rs.result == "success") {
		staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/optionlist.do",{"categoryCode":"whSTAType"});
		staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/optionlist.do",{"categoryCode":"whSTAStatus"});
		$j("#sta_id").val(rs.pl["id"]);
	    $j("#sta_code").text(rs.pl["code"]);
		$j("#sta_refSlipCode").text(rs.pl["refSlipCode"]);
		$j("#sta_slipCode1").text(rs.pl["slipCode1"]);
		$j("#sta_createTime").text(rs.pl["createTime"]);
		for(var idx in staType){
			if(staType[idx].optionKey == rs.pl["intType"]){
				$j("#sta_type").text(staType[idx].optionValue);
				break;
			}
		}
		for(var idx in staStatus){
			if(staStatus[idx].optionKey == rs.pl["intStatus"]){
				$j("#sta_status").text(staStatus[idx].optionValue);
				break;
			}
		}
		$j("#sta_memo").text(rs.pl["memo"]);
		showReceiptNumber();
		$j("#divHead").addClass("hidden");
		$j("#divDetial").removeClass("hidden");
		$j("#skuBarCode").focus();
	}else if(rs.result=="none"){
		jumbo.showMsg("未找到匹配的信息!");
	}else if(rs.result=="error"){
		jumbo.showMsg(data.message);		
	};
	
};
var staType;
var staStatus;
$j(document).ready(function (){
	//快速核对进去详细页
	$j("#refSlipCode").keydown(function(evt) {
		if (evt.keyCode === 13) {
			var slipCode = $j("#refSlipCode").val().trim();
			if (slipCode == "") {
				jumbo.showMsg("输入单据号码不能为空!");
				return;
			}
			showdetails(slipCode);
		}
	});
	
	var baseUrl = $j("body").attr("contextpath");
	initShopQuery("companyshop","innerShopCode");
	jumbo.loadShopList("companyshop");
	loxia.init({debug: true, region: 'zh-CN'});
	staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	$j("#tbl_sta_list").jqGrid({
		url:baseUrl+"/findInboundSta.json",
		datatype: "json",
		colNames: ["ID","作业单号","相关单据号","辅助相关单据号","状态","作业类型","店铺","CHANNELNAME","创建时间","备注"],
		colModel: [
		           {name: "id", index: "id", hidden: true},	
		           {name: "code", index: "code",formatter:"linkFmatter", formatoptions:{onclick:"showdetail"}, width: 110, resizable: true,sortable:false},
		           {name: "refSlipCode", index: "refSlipCode", width: 100, resizable: true},
		           {name: "slipCode1", index: "slipCode1", width: 100, resizable: true},
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
		colNames: ["ID","skuId","商品条码","货号","关键属性",
			"商品名称","本次执行量","操作"],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},	
		           {name: "skuId", index: "skuId" ,hidden: true,sortable:false},
		           {name: "barCode", index: "barCode", width: 100, resizable: true,sortable:false},//商品条码
		           {name: "supplierCode", index: "supplierCode", width: 120, resizable: true,sortable:false},//货号
		           {name: "keyProperties", index: "keyProperties", width: 120, resizable: true,sortable:false},//关键属性
		           {name: "skuName", index: "skuName", width: 190, resizable: true,sortable:false},//商品名称
		           {name: "addQuantity",index: "addQuantity",formatter:"loxiaInputFmatter", formatoptions:{loxiaType:"number",min:"0"}, width: 160, resizable: true,sortable:false},//本次执行量
		           {name: "reset", width: 80,resizable:true, formatter:"buttonFmatter", formatoptions:{"buttons":{label:"重置", onclick:"printSingleDelivery(this,event);"}}}//操作
	               ],
		       		caption: "入库明细单",
		    	   	sortname: 'sku.bar_Code',
		    	    multiselect: false,
		    		sortorder: "desc",
		    		height:"auto",
		    		viewrecords: true,
		    		rowNum:-1,
		       		rownumbers:true,
		    	   	gridComplete : function(){
						loxia.initContext($j(this));
						prompt();
					},
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
			if(barCode.toUpperCase()=="OK".toUpperCase()){
				$j("#confirm").trigger("click");
				return false;
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
		for(var i=0,d;(d=datalist[i]);i++){
			var qty = $j("#"+d.id).find("input[name=addQuantity]").val();
			if(qty != ''){
				postData["stvLineList[" + i + "].skuId"]=d.skuId;
				postData["stvLineList[" + i + "].receiptQty"]=qty;
			}
	   	}
		loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+ "/json/inBoundAffirmHand.json",
				postData,
				{
				success:function(data){
					if(data){
						if(data.result=="success"){
							var errorMsg="";
							if(data.errorMsg!=null&&data.errorMsg!=""&&data.errorMsg!="undefined"){
								errorMsg=data.errorMsg;
							}
							jumbo.showMsg("操作成功!  "+errorMsg);
							$j("#divDetial").addClass("hidden");
							$j("#divHead").removeClass("hidden");
							$j("#refSlipCode").val(""); // 清除值
							$j("#refSlipCode").focus(); // 聚焦
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
	});
	/**
	 * 打印货箱标签
	 */
	$j("#printContainerCode").click(function(){
		loxia.lockPage();
		jumbo.showMsg("货箱条码打印");
		var url = $j("body").attr("contextpath") + "/printContainerCode.json?type=in&sta.id=" + $j("#sta_id").val();
		printBZ(loxia.encodeUrl(url),false);
		loxia.unlockPage();
	});
	
	/**
	 * 打印相关单据号
	 */
	$j("#printSlipCode").click(function(){
		loxia.lockPage();
		jumbo.showMsg("作业单号打印");
		var url = $j("body").attr("contextpath") + "/printAutoSlipCode.json?sta.id=" + $j("#sta_id").val();
		printBZ(loxia.encodeUrl(url),false);
		loxia.unlockPage();
	});
	
	$j("#printInput").keypress(function(event){
		if (event.keyCode === 13) {
			var key = $j("#printInput").val().trim().toUpperCase();
			if("OK" == key){
				$j("#printContainerCode").trigger("click");	
			}else if("NO" == key){
				$j("#printSlipCode").trigger("click");	
			}
			 $j("#printInput").val("");
		}
	});
});
//重置按钮的方法
function printSingleDelivery(tag,event){
	var id = $j(tag).parents("tr").attr("id");
	$j("#"+id).find("input[name=addQuantity]").val("");
} 


function queryStaList(){
	var url = $j("body").attr("contextpath") + "/findInboundSta.json";
	$j("#tbl_sta_list").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_sta_list",{"intStaType":"whSTAType","intStaStatus":"whSTAStatus"},
		url,loxia._ajaxFormToObj("queryForm"));
}

function showReceiptNumber(){
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl_sta_line_list").jqGrid('setGridParam',{page:1,url:baseUrl + "/findStaLineByStaHand.json?sta.id=" + $j("#sta_id").val()}).trigger("reloadGrid");
}


