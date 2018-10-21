var extBarcode = null;
var outboundSn = null;
var getValues = null;
$j.extend(loxia.regional['zh-CN'],{
	ENTITY_INMODE				:	"上架批次处理模式",
	ENTITY_EXCELFILE			:	"正确的excel导入文件",
	ENTITY_STA_CREATE_TIME		:	"作业创建时间",
	ENTITY_STA_ARRIVE_TIME		:	"预计到货日期",
	ENTITY_STA_CODE				:	"作业单号",
	ENTITY_STA_PROCESS			:	"执行情况",
	ENTITY_STA_UPDATE_TIME		:	"上次执行时间",
	ENTITY_STA_PO				:	"相关单据号",
	ENTITY_STA_OWNER			:	"店铺名称",
	ENTITY_STA_SUP				:	"供应商名称",
	ENTITY_STA_SEND_MODE		:	"送货方式",
	ENTITY_STA_REMANENT			:	"剩余计划入库量（件）",
	ENTITY_STA_COMMENT			:	"相关单据备注",
	// "SKU条形码","SKU JMCODE","SKU扩展属性","SKU
	// 名称","SKU供应商编码","计划量执行量","已执行量","本次执行量"],
	ENTITY_SKU_BARCODE			:	"条形码",
	IMPERFECT_BARCODE			:	"残次条码",
	IMPERFECT_TYPE			    :	"残次类型",
	IMPERFECT_WHY			    :	"残次原因",
	ENTITY_SKU_JMCODE			:	"商品编码",
	ENTITY_SKU_KEYPROP			:	"扩展属性",
	ENTITY_SKU_NAME				:	"商品名称",
	ENTITY_SKU_SUPCODE			:	"货号",
	ENTITY_STALINE_TOTAL		:	"计划量执行量",
	TRUNKPACKINGLISTINFO:"装箱清单打印中，请等待...",
	ENTITY_STALINE_COMPLETE		:	"已执行量",
	ENTITY_STALINE_CURRENT		:	"本次执行量",
	ENTITY_LOCATION				:	"库位",
	ENTITY_LOCATION_CAPACITY	:	"库容",
	ENTITY_LOCATION_CURRENT		:	"当前库存数",
	ENTITY_LOCATION_ADD			:	"上架数",			
	ENTITY_INVENTORY_STATUS		:	"库存状态",			
	INVALID_QTY					:	"第{0}行的收货数量不是有效的数字类型!",
	INVALID_QTY_G				:	"第{0}行的本次执行量大于未收货的数量,请核对每个商品[或许存在相同的商品]的本次执行量!",
	LABEL_ENPTY					:	"无",
	LABEL_BARCODE				:	"切换为使用条码方式录入",
	LABEL_JMCODE				:	"切换为使用商品编码方式录入",
	LABEL_TOTAL					:	"总计：",
	LABEL_LINE					:	"第{0}行",
	INVALID_NOT_EXIST 			:	"{0}不正确/不存在",
	MSG_CONFIRM_CLOSESTA		:	"您确定要关闭本次采购收货吗?",
	MSG_NO_ACTION				:	"本次收货数量为0!",
	MSG_CLOSESTA_FAILURE		:	"关闭作业申请单失败!",
	MSG_CONFIRM_CANCEL_STV		:	"如果返回，前面的操作将全部作废。您确定要返回吗?",
	MSG_ERROR_INMODE			:	"当前库位不满足单批隔离上架模式!",
	MSG_ERROR_LOCATION_QTY		:	"当前库位库容不能满足当前上架数量!",
	MSG_ERROR_STALINE_QTY		:	"实际上架数量≠计划量",
	MSG_WARN_STALINE_QTY		:	"实际输入的上架数量小于计划量,差异的数据录入到虚拟仓!",
	TABLE_CAPTION_STA			:	"退换货待收货列表",
	TABLE_CAPTION_STALINE		:	"退换货待收货明细表",
	MSG_LOCATION_CAPACITY		:	"不限",
	MSG_INVSTATUS_ERROR			:	"获取库存状态列表异常",
	ENTITY_STALINE_SNS			:	"SN序列号",			
	LABEL_ADD_SNS				:	"维护序列号",
	INVALID_SN					:	"请按正确的格式填写SN序列号",
	INVALID_SN_LINE				:	"请按正确的格式填写第{0}行SN序列号",
	OPERATING 					:	"商品上架情况打印中，请等待...",
	CORRECT_FILE_PLEASE         :   "请选择正确的Excel导入文件",
	
	WARRANTY_CARD_TYPE_NO		:	"1",
	WARRANTY_CARD_TYPE_YES_NO_CHECK	:	"3",
	
    COACH_OWNER1				:	"1coach官方旗舰店",
    COACH_OWNER2				:	"1Coach官方商城",
	
	LPCODE 						: 	"物流服务商",
	TRACKING_NO					:	"快递单号", 
	IS_NEED_INVOICE				: 	"是否需要发票",
	ORDER_CREATE_TIME			:	"原订单创建时间",
	RETURN_REASON_MEMO			:   "退换货备注"
	
});
function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
function initShopQuery(selId,valCode){
	INCLUDE_SEL_ID = selId;
	INCLUDE_SEL_VAL_CODE = valCode;
	if(valCode == 'innerShopCode'){
		INCLUDE_SEL_VAL_CODE = 'code';
	}
}


$j(document).ready(function (){
	jumbo.loadShopList("companyshop");
	initShopQuery("companyshop","innerShopCode");
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	$j("#search").click(function(){
		var postData = loxia._ajaxFormToObj("form_query");  
		var companyshop=$j("#companyshop").val();
		postData["sta.owner"]=companyshop;
		var url = $j("body").attr("contextpath")+"/inventoryImperfect.json";
		$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
			url:url,postData:postData}).trigger("reloadGrid");
	});
	if(!status.exception){
		// STA作业申请单
		$j("#tbl-inbound-purchase").jqGrid({
			url:$j("body").attr("contextpath")+"/inventoryImperfect.json",
			datatype: "json",
			colNames: ["ID","商品编码","店铺名称","残次数量","生成并打印残次标签"],
			colModel: [
			           {name: "skuId", index: "skuId",hidden: true},
			           {name: "jmCode", index: "jmCode",sortable: false, width: 100, resizable: true},
			           {name: "invOwner", index: "invOwner",sortable: false, width: 100, resizable: true},
			           {name: "quantity", index: "quantity",sortable: false, width: 120, resizable: true},
		               {name: "idForBtn", width: 150,resizable:true,sortable: false, formatter:"buttonFmatter", formatoptions:{"buttons":{label:"生成并打印", onclick:"printSingleDelivery(this,event);"}}}],
			caption: "残次品列表标签",// 作业单列表
			rowNum:-1,
		   	sortname: 'id',
		   	pager:"#pager",
		   	height:"auto",
		    rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
		   	multiselect: false,
		    rownumbers:true,
		    viewrecords: true,
			hidegrid:false,
			sortorder: "asc",
			gridComplete : function(){
				loxia.initContext($j(this));
			},
			jsonReader: { repeatitems : false, id: "0" }
			  
		});
	
		}else{
			jumbo.showMsg(i18("ERROR_INIT"));
		}
	//新建配送范围取消--关闭对话框
	$j("#areaCancel").click(function(){
		$j("#dialog_newArea").dialog("close");
		$j("#dialog_newArea").addClass("hidden");
		$j("#quantity").val("");
	});
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val(0);
		$j("#isNeedInvoice").val("请选择");
		
	});
	$j("#imperfect").change(function(){
		var owner=$j("#owner").val();
		var channel = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getByName.do",{"owner":owner});//渠道信息
		if(channel.isImperfect==1){
			var imperfectId=$j("#imperfect").val();
			var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getByImperfectLine.do",{"imperfectId":imperfectId});
			$j("#imperfectLine").empty();
			$j("<option value='其他'>其他</option>").appendTo($j("#imperfectLine"));
			for(var idx in result){
				$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#imperfectLine"));
			}
		}else{
			var warehouse = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getWarehouseInfo.do");//仓库信息
			if(warehouse.isImperfect==1){
				var imperfectId=$j("#imperfect").val();
				var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getByImperfectLine.do",{"imperfectId":imperfectId});
				$j("#imperfectLine").empty();
				$j("<option value='其他'>其他</option>").appendTo($j("#imperfectLine"));
				for(var idx in result){
					$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#imperfectLine"));
				}
			}else{
				$j("#imperfectLine").empty();
				$j("<option value='其他'>其他</option><option value='摔坏'>摔坏</option><option value='无理由'>无理由</option>").appendTo($j("#imperfectLine"));
			}
		}
	});
	$j("#areaNew").click(function(){
		var qty=$j("#qty").val();
		var quantity=$j("#quantity").val();
		var factoryCode=$j('#factoryCode').val();
		var poId=$j('#poId').val();
		var imperfect=$j('#imperfect').val();
		var memo=$j('#memo').val();
		if(quantity==""){
			jumbo.showMsg("数量不能为空");
			return;
		}
		var display =$j('#factory').css('display');
		if(display=="inline"){
			if(factoryCode==""){
				jumbo.showMsg("工厂代码为空");
				return;
			} 		
		}
		var display1 =$j('#poTime').css('display');
		if(display1=="inline"){
			if(poId==""){
				jumbo.showMsg("下单日期为空");
				return;
			} 
		}
		if(imperfect=="其他"){
			if(memo==""){
				jumbo.showMsg("原因类型为其他时，备注不能为空");
				return;
			} 
		}
		if(quantity<=0){
			jumbo.showMsg("残次库存量不能小于等于0");
		}
		if(Number(qty)>=Number(quantity)){
			var postData = {};
			postData['skuImperfect.owner']=$j("#owner").val();
			postData['skuImperfect.defectType']=$j("#imperfect").find("option:selected").text();
			postData['skuImperfect.defectWhy']=$j("#imperfectLine").find("option:selected").text();
			postData['skuImperfect.sku.id']=$j("#skuId").val();
			postData['skuImperfect.qty']=quantity;
			for ( var int = 1; int <= quantity; int++) {
				postData['skuImperfect.qty']=1;
				var rs=loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/addSkuImperfect.do",postData);//渠道信息
				if(rs && rs["msg"] == 'success'){
					var id=rs["id"];
					var url=window.$j("body").attr("contextpath")+"/json/printSkuImperfect.do?imperfectId="+id;
					printBZ(loxia.encodeUrl(url),true);
				}
			}
			$j("#dialog_newArea").dialog("close");
			$j("#dialog_newArea").addClass("hidden");
			$j("#quantity").val("");
		}else{
			jumbo.showMsg("超出在库库存量");
			$j("#dialog_newArea").dialog("close");
			$j("#dialog_newArea").addClass("hidden");
			$j("#quantity").val("");
		}
	});
	$j("#dialog_newArea").dialog({title: "新建残次原因及类型", modal:true,closeOnEscape:false, autoOpen: false, width:300});//弹窗属性设置

});
function printSingleDelivery(a,b){
	var id = $j(a).parents("tr").attr("id");
	var tr= $j(a).parents("tr");
	var owner=tr.children().eq(3).text();
	var qty=tr.children().eq(4).text();
	$j("#qty").val(qty);
	$j("#skuId").val(id);
	$j("#owner").val(owner);
	var channel = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getByName.do",{"owner":owner});//渠道信息
		if(channel.isImperfect){
			if(channel.isImperfectPoId==1){
				$j("#factory").removeClass("hidden");
				$j("#factory1").removeClass("hidden");
			}
			if(channel.isImperfectTime==1){
				$j("#poTime").removeClass("hidden");
				$j("#poTime1").removeClass("hidden");
			}
			$j("#add_imperfect").removeClass("hidden");
			$j("#imperfect").empty();
			$j("<option value='其他'>其他</option>").appendTo($j("#imperfect"));
			var channelId=channel.id;
			var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getByImperfect.do",{"channelId":channelId});
			for(var idx in result){
				$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#imperfect"));
			}
		}else{
			var warehouse = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getWarehouseInfo.do");//仓库信息
			if(warehouse.isImperfect){
				$j("#add_imperfect").removeClass("hidden");
				var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findWarehouseImperfectl.json");
				for(var idx in result){
					$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#imperfect"));
				}
			}else{
				$j("#imperfect").empty();
				$j("#imperfectLine").empty();
				$j("<option value='其他'>其他</option><option value='摔坏'>摔坏</option><option value='无理由'>无理由</option>").appendTo($j("#imperfect"));
				$j("<option value='其他'>其他</option><option value='摔坏'>摔坏</option><option value='无理由'>无理由</option>").appendTo($j("#imperfectLine"));
			}
		}
	$j("#AreaCode").attr("value","");
	$j("#AreaName").attr("value","");
	$j("#file").attr("value","");
	$j("#dialog_newArea").dialog("open");
	$j("#dialog_newArea").removeClass("hidden");
}

