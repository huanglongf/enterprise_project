var extBarcode = null;
var outboundSn = null;

$j.extend(loxia.regional['zh-CN'],{
	ENTITY_STA_CODE				:	"作业单号",
	ENTITY_STA_PO				:	"相关单据号",
	ENTITY_STA_OWNER			:	"店铺名称",
	ENTITY_STA_CREATE_TIME		:	"作业创建时间",
	ENTITY_STA_STATUS           :   "状态",
	
	ENTITY_TRACKINGNO			:	"快递单号",
	MSG_CONFIRM_LOCK			:	"是否确定要解锁此订单",
	
	MSG_UNLOCK_FAILURE			:   "该订单解锁失败！",
	TABLE_CAPTION_STA			:	"退换货待收货列表"
	
});

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}


$j(document).ready(function (){
	$j("#slipcode1Id").focus();
	
	//初始化物流商信息
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransportator.do");
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selLpcode"));
	}
	
	jumbo.loadShopList("companyshop");
	initShopQuery("companyshop","innerShopCode");
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	
	$j("#search").click(function(){
		var postData = loxia._ajaxFormToObj("form_query");  
//		postData['sta.intType'] = 41;
		postData['barCode'] = $j("#barCode").val();
		var url = $j("body").attr("contextpath")+"/listUnlockedStaJson.json";
		$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
			url:url,page:1,postData:postData}).trigger("reloadGrid");
		
		//$j("#reset").click();
		$j("#slipcode1Id").focus();
	});
	
	$j("#tbl-inbound-purchase").jqGrid({
		//url:$j("body").attr("contextpath")+"/listUnlockedStaJson.json",
		datatype: "json",
		colNames: ["ID",i18("ENTITY_STA_CODE"),
		           i18("ENTITY_STA_PO"),
		           i18("ENTITY_TRACKINGNO"),
		           i18("ENTITY_STA_CREATE_TIME"),
		           i18("ENTITY_STA_OWNER"),
		           i18("ENTITY_STA_OWNER"),
		           "状态"
		          ],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},		         
		           {name: "code", index: "sta.code",formatter:"linkFmatter", formatoptions:{onclick:"showdetail"}, width: 200, resizable: true,sortable:true},
		           {name: "refSlipCode", index: "slip_code", width: 150, resizable: true,sortable:true},
		           {name: "trackingNo", index: "trackingNo", width: 150, resizable: true,sortable:true},
		           {name: "createTime", index: "create_time", width: 200, resizable: true,sortable:true},
		           {name: "channelName", index: "channelName", width: 150, resizable: true,sortable:true},
		           {name: "owner", index: "owner", width: 150, resizable: true,sortable:true,hidden:true},
		           {name:"intStatus",index:"sta.Status",width:80,resizable:true,formatter:'select',editoptions:staStatus}
		           
		          ],
		caption: i18("TABLE_CAPTION_STA"),
	   	sortname: 'sta.create_time',
	   	sortorder: "desc",
	   	multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pager",
	   	height:"auto",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
		});
	
	
	
	
	$j("#tbl-commodity-detail").jqGrid({
		//url:$j("body").attr("contextpath")+"/returnInboundGoodsDetail.json",
		datatype: "json",
		colNames: ["ID","商品条码","商品编码","扩展属性","名称","货号","库存状态","件数"],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},		         
		           {name: "barCode", index: "barCode", width: 150, resizable: true,sortable:true},
		           {name: "code", index: "code", width: 150, resizable: true,sortable:true},
		           {name: "keyProperties", index: "keyProperties", width: 150, resizable: true,sortable:true},
		           {name: "name", index: "name", width: 150, resizable: true,sortable:true},
		           {name: "supplierCode", index: "supplierCode", width: 150, resizable: true,sortable:true},
		           {name: "invStatus", index: "invStatus", width: 150, resizable: true,sortable:true},
		           {name:"planQuantity",index:"planQuantity",width:100,resizable:true,sortable:true}
		           
		          ],
		caption: "退换货入库指令商品明细",
	   	sortname: 'id',
	   	sortorder: "desc",
	   	multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pager1",
	   	height:"auto",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
		});
	
	
	// 返回
	$j("#backto").click(function(){
		 window.location=loxia.getTimeUrl($j("body").attr("contextpath")+"/warehouse/returnEntryUnlocked.do");
	});
	$j("#backto1").click(function(){
		 window.location=loxia.getTimeUrl($j("body").attr("contextpath")+"/warehouse/returnEntryUnlocked.do");
	});
	
	// 解锁
	$j("#unlockd_btn").click(function(){
		var lpcode = $j.trim($j("#lpcode").val());
		var shortcut_code = $j.trim($j("#shortcut_code").val());
		var returnReasonType = $j.trim($j("#returnReasonType").val());
		var returnReasonMemo = $j.trim($j("#returnReasonMemo").val());
//		if(lpcode == null || lpcode.length == 0) {
//			jumbo.showMsg("物流供应商不能为空");
//			return;
//		}
		if(shortcut_code > 0 && (lpcode == null || lpcode.length == 0)) {
			jumbo.showMsg("物流服务商不能为空");
			return;
		}
		if(returnReasonType == null || returnReasonType.length == 0) {
			jumbo.showMsg("退货原因不能为空");
			return;
		}
		if(confirm(i18("MSG_CONFIRM_LOCK") )){
		loxia.lockPage();
		loxia.asyncXhrPost($j("body").attr("contextpath") + "/updateStaUnlocked.json",
				{'sta.id':$j("#staId").val(),
				 'lpcode':$j("#lpcode option:selected").val(),	
				 'trackingNo':$j("#shortcut_code").val(),
				 'returnReasonType':returnReasonType,
				 'returnReasonMemo':returnReasonMemo
				},
				{
				success:function (data) {
					loxia.unlockPage();
					if(data.result=="success"){
						window.location=loxia.getTimeUrl($j("body").attr("contextpath")+"/warehouse/returnEntryUnlocked.do");
					}else if(data.result=="error"){
						jumbo.showMsg(i18("MSG_UNLOCK_FAILURE")+data.msg);
					}
				},
				error:function(data){
					loxia.unlockPage();
					jumbo.showMsg(i18("MSG_UNLOCK_FAILURE"));
				}
				});
		}
		
//		loxia.lockPage();
//		var date = loxia.asyncXhrPost($j("body").attr("contextpath") + "/updateStaUnlocked.json",
//				{'sta.id':$j("#staId").val(),
//				 'lpcode':$j("#lpcode option:selected").val(),	
//				 'trackingNo':$j("#shortcut_code").val(),
//				 'returnReasonType':returnReasonType,
//				 'returnReasonMemo':returnReasonMemo
//				});
//		alert(date);
//		if(date.result == "success"){
//			alert(2)
//			loxia.unlockPage();
//			window.location=loxia.getTimeUrl($j("body").attr("contextpath")+"/warehouse/returnEntryUnlocked.do");
//		}else{
//			alert(1);
//			loxia.unlockPage();
//			jumbo.showMsg(i18("MSG_UNLOCK_FAILURE")+data.msg);
//		
//		}
	});
	
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val(0);
		$j("#slipcode1Id").focus();
	});
	
	$j("#trackingNoId,#staCodeId,#slipcodeId,#slipcode1Id").keypress(function(evt){
		if(evt.keyCode === 13){
			$j("#search").click();
		}
	});
	
	// 运单号回车事件带出物流商
	$j("#shortcut_code").keypress(function(evt){
		if(evt.keyCode === 13){
			var trackNo = $j("#shortcut_code").val();
			$j("#shortcut_code").blur();
			var rl = loxia.syncXhrPost($j("body").attr("contextpath")+ "/findlpCodeByTrackNo.json", {"tranckNo" : trackNo});
			if (rl && rl["lpCode"] != "null") {
				//有物流商
				$j("#shortcut_code").val(trackNo);
				$j("#lpcode").val(rl["lpCode"]);
				$j("#returnReasonType").focus();
			}else{
				// 没物流商
				//$j("#shortcut_code").val(trackNo);
				$j("#lpcode").focus();
			}
		}
	});
	$j("#lpcode").change(function () {
		$j("#lpcode").blur();
		$j("#returnReasonType").focus();
	});
	
	$j("#returnReasonType").change(function () {
		$j("#returnReasonType").blur();
		$j("#confirmWeightInfoValue").focus();
	});
	
	$j("#confirmWeightInfoValue").keydown(function(evt){
		if(evt.keyCode === 13){
			var code = $j("#confirmWeightInfoValue").val();
			if (code == "OK" || code == "ok"){
				$j("#unlockd_btn").click();
			}
		}
	});
});

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}
function divToggle(id){
	if($j(id).hasClass("hidden")){
		$j(id).removeClass("hidden");
	}else{
		$j(id).addClass("hidden");
	}
}
function staListToggle(){	divToggle("#div-sta-list");}
function staToggle(){divToggle("#div-sta-detail");}
function staToggle1(){divToggle("#div-sta-detail1");}
function staToggle2(){divToggle("#div-sta-detail2");}
function showdetail(obj){
	staListToggle();
	$j("#returnReasonType").val("99");
	var staId=$j(obj).parent().parent().attr("id");
	var sta=$j("#tbl-inbound-purchase").jqGrid("getRowData",staId);
	staToggle();
	if(sta.intStatus==1){ // 已创建
		staToggle1();
	}else if(sta.intStatus==17){ // 取消已处理
		staToggle2();
	}
	
	//初始化物流商信息
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransportator.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#lpcode"));
	}
	
	var staId=$j(obj).parent().parent().attr("id");
	var sta=$j("#tbl-inbound-purchase").jqGrid("getRowData",staId);
//	$j("#staId").val(staId);
//	
//	$j("#createTime").val(sta.createTime);
//	$j("#staCode").val(sta.code);
//	$j("#refSlipCode").val(sta.refSlipCode);
	
	$j("#staId").val(staId);
	$j("#createTime").html(sta.createTime);
	$j("#staCode").html(sta.code);
	$j("#refSlipCode").html(sta.refSlipCode);
	$j("#owner").html(sta.owner);
	//$j("#ownerId").html(sta.owner);
	$j("#ownerId").html(sta.channelName);
	
	
	
	//staListToggle();
	//var staId=$j(obj).parent().parent().attr("id");
	//var sta=$j("#tbl-inbound-purchase").jqGrid("getRowData",staId);
	
//	$j("#staId").val(staId);
//	$j("#createTime3").val(sta.createTime);
//	$j("#po3").val(sta.refSlipCode);
//	$j("#staCode3").val(sta.code);
//	$j("#owner3").val(sta.owner);
//	$j("#supplier3").val(sta.supplier);
//	$j("#status3").val($j(obj).parent().next().html());
//	$j("#left3").val(sta.remnant);
//	if(!sta['stvId']){//没有未完成的STV
//		staToggle();
////	 	$j("div-sta-detail").removeClass("hidden");
////	 	$j("#tbl-shelve").addClass("hidden");
//		$j("#tbl-orderNumber").jqGrid('setGridParam',
//			{url:$j("body").attr("contextpath")+"/stalinelist.json?sta.id="+staId}).trigger("reloadGrid",[{page:1}]);
//		$j("#createTime,#createTime2").html(sta.createTime);
//		$j("#staCode,#staCode2").html(sta.code);
//		$j("#po,#po2").html(sta.refSlipCode);
//		$j("#owner,#owner2").html(sta.owner);
//		$j("#supplier,#supplier2").html(sta.supplier);
//		$j("#status,#status2").html($j(obj).parent().next().html());
//		$j("#left,#left2").html(sta.remnant);
//	}else{
//		$j("#stvId").val(sta['stvId']);
//		$j("#nowNum").val(sta['stvTotal']);
//		tostvInfo(sta['stvId'],sta);
//	}
//	$j("#barCode").trigger("focus");
	
	// 如果快递单号不为空，自动带出物流商
	
	if(sta.trackingNo != ""){
		var rl = loxia.syncXhrPost($j("body").attr("contextpath")+ "/findlpCodeByTrackNo.json", {"tranckNo" : sta.trackingNo});
		if (rl && rl["lpCode"] != "null") {
			//有物流商
			$j("#shortcut_code").val(sta.trackingNo);
			$j("#lpcode").val(rl["lpCode"]);
			$j("#returnReasonType").focus();
		}else{
			// 没物流商
			$j("#shortcut_code").val(sta.trackingNo);
			$j("#lpcode").focus();
		}
	}else{
		$j("#shortcut_code").focus();
	}
	$j("#tbl-commodity-detail").jqGrid('setGridParam',{url:$j("body").attr("contextpath")+"/returnInboundGoodsDetail.json?sta.id="+staId}).trigger("reloadGrid",[{page:1}]);
}










