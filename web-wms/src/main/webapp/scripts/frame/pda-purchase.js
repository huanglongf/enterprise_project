var extBarcode = null;

$j.extend(loxia.regional['zh-CN'],{
	ENTITY_INMODE					:	"上架批次处理模式",
	ENTITY_EXCELFILE				:	"正确的excel导入文件",
	ENTITY_STA_CREATE_TIME			:	"作业创建时间",
	ENTITY_STA_ARRIVE_TIME			:	"预计到货日期",
	ENTITY_STA_CODE					:	"作业单号",
	ENTITY_STA_PROCESS				:	"执行情况",
	ENTITY_STA_UPDATE_TIME			:	"上次执行时间",
	ENTITY_STA_PO					:	"PO单号",
	ENTITY_STA_OWNER				:	"店铺名称",
	ENTITY_STA_SUP					:	"供应商名称",
	ENTITY_STA_SEND_MODE			:	"送货方式",
	ENTITY_STA_REMANENT				:	"剩余计划入库量（件）",
	ENTITY_STA_COMMENT				:	"PO单备注",
	ENTITY_SKU_BARCODE				:	"条形码",
	ENTITY_SKU_JMCODE				:	"商品编码",
	ENTITY_SKU_KEYPROP				:	"扩展属性",
	ENTITY_SKU_NAME					:	"商品名称",
	ENTITY_SKU_SUPCODE				:	"货号",
	ENTITY_STALINE_TOTAL			:	"计划量执行量",
	ENTITY_STALINE_COMPLETE			:	"已执行量",
	ENTITY_STALINE_CURRENT			:	"本次执行量",
	ENTITY_LOCATION					:	"库位",
	ENTITY_LOCATION_CAPACITY		:	"库容",
	ENTITY_LOCATION_CURRENT			:	"当前库存数",
	ENTITY_LOCATION_ADD				:	"上架数",			
	ENTITY_STALINE_SNS				:	"SN序列号",			
	INVALID_QTY						:	"第{0}行的收货数量不是有效的数字类型!",
	INVALID_QTY_G					:	"第{0}行的本次执行量大于未收货的数量,请核对每个商品[或许存在相同的商品]的本次执行量!",
	LABEL_ENPTY						:	"无",
	LABEL_BARCODE					:	"切换为使用条码方式录入",
	LABEL_JMCODE					:	"切换为使用商品编码方式录入",
	LABEL_TOTAL						:	"总计：",
	LABEL_ADD_SNS					:	"维护序列号",
	INVALID_NOT_EXIST 				:	"{0}不正确/不存在",
	MSG_CONFIRM_CLOSESTA			:	"您确定要关闭本次采购收货吗?",
	MSG_NO_ACTION					:	"本次收货数量为0!",
	MSG_CLOSESTA_FAILURE			:	"关闭作业申请单失败!",
	MSG_CONFIRM_CANCEL_STV			:	"如果返回，前面的操作将全部作废。您确定要返回吗?",
	MSG_ERROR_INMODE				:	"当前库位不满足单批隔离上架模式!",
	MSG_ERROR_LOCATION_QTY			:	"当前库位库容不能满足当前上架数量!",
	MSG_ERROR_STALINE_QTY			:	"实际上架数量≠计划量",
	MSG_WARN_STALINE_QTY			:	"实际输入的上架数量小于计划量,差异的数据录入到虚拟仓!",
	TABLE_CAPTION_STA				:	"PDA收货列表",
	TABLE_CAPTION_STALINE			:	"PDA待收货明细表",
	MSG_LOCATION_CAPACITY			:	"不限",
	INVALID_SN						:	"请按正确的格式填写SN序列号",
	INVALID_SN_LINE					:	"请按正确的格式填写第{0}行SN序列号",
	OPERATING 						:	"商品上架情况打印中，请等待...",
	
	CAN_NOT_CANCEL					:	"当前作业单不是‘新建状态’不能取消",
	CONFIRM_CANCEL					:	"是否确认取消?",
	RESULT_SUCCESS					:	"操作成功",
	RESULT_ERROR					:	"操作失败",
	CORRECT_FILE_PLEASE 			: "请选择正确的Excel导入文件",
	PRINGT							: "打印"
});

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}
function divToggle(id){
	if($j(id).hasClass("hidden")){
		$j(id).removeClass("hidden");
	}else{
		$j(id).addClass("hidden");
	}
}
var isInboundInvoice = 0;
function staListToggle(){divToggle("#div-sta-list");}
function staToggle(){divToggle("#div-sta-detail");}
function stvToggle(){divToggle("#div-stv");}
function showdetail(obj){
	staListToggle();
	var staId=$j(obj).parent().parent().attr("id");
	var sta=$j("#tbl-inbound-purchase").jqGrid("getRowData",staId);
	$j("#staId").data("sta",sta);
	$j("#staId").val(staId);
	$j("#createTime3").val(sta.createTime);
	$j("#staCode3").val(sta.code);
	$j("#po3").val(sta.refSlipCode);
	$j("#owner3").val(sta.owner);
	$j("#supplier3").val(sta.supplier);
	$j("#status3").val($j(obj).parent().next().html());
	$j("#left3").val(sta.remnant);
	$j("#tbl-orderNumber tr:gt(0)").remove();
	//invoiceInfoInit(sta.isInboundInvoice,"invoice",sta.refSlipCode);
	var baseUrl = $j("body").attr("contextpath");
	if(sta.isPDA!="true"){
		staToggle();
		var sUrl = baseUrl+"/findpdapurchasedetail.json?staid="+staId;
		$j("#tbl-orderNumber").jqGrid('setGridParam',{page:1,url:loxia.getTimeUrl(sUrl)}).trigger("reloadGrid",[{page:1}]);
		$j("#createTime").html(sta.createTime);
		$j("#staCode").html(sta.code);
		$j("#po").html(sta.refSlipCode);
		$j("#owner").html(sta.owner);
		$j("#supplier").html(sta.supplier);
		$j("#status").html($j(obj).parent().next().html());
		$j("#left").html(sta.remnant);
	}else{//没有未完成的STV
		var rs = loxia.syncXhrPost(baseUrl+"/findisneedsnbystaid.json?staid=" + staId);
        if (rs && rs.result == 'success')divToggle("#divSnImport");
		$j("#stvId").val(sta['stvId']);
		$j("#nowNum").val(sta['stvTotal']);
		tostvInfo(sta['stvId'],sta,true);
	}
}

function getPostData(receiveAll,mode,staId){
	var postData={},index=-1,total=0;
	postData['stv.sta.id']=staId;
	$j.each($j("#tbl-orderNumber").jqGrid("getRowData"),function(i,row){
		var addQuantity=$j.trim($j("#tbl-orderNumber tr[id="+row['id']+"] :input").val());
		var completeQuantity=row['completeQuantity'];
		if(receiveAll||addQuantity.length>0){
			if(receiveAll){
				addQuantity=row['quantity']-completeQuantity;
			}else{
				addQuantity=parseInt(addQuantity,10);
			}
			if(isNaN(addQuantity)||addQuantity<0){
					postData['error']=i18("INVALID_QTY",i+1);
					return ;
			}
			if(row['quantity']-completeQuantity<addQuantity){
				postData['error']=i18("INVALID_QTY_G",i+1);
				return ;
			}else if(addQuantity>0){
				index+=1;
				postData['stv.stvLines['+index+'].quantity']=addQuantity;
				postData['stv.stvLines['+index+'].skuCost']=row['skuCost'];
				postData['stv.stvLines['+index+'].sku.id']=row['skuId'];
				postData['stv.stvLines['+index+'].staLine.id']=row['id'];
				if($j("#tbl-orderNumber tr[id="+row['id']+"] td:last").html()!=""){
					var sn=$j("#tbl-orderNumber tr[id="+row['id']+"] .sns").val();
					if(sn&&sn.length>0&&sn.split(",").length==addQuantity){
						postData['stv.stvLines['+index+'].sns']=$j("#tbl-orderNumber tr[id="+row['id']+"] .sns").val();
					}else{
						postData['error']=i18("INVALID_SN_LINE",i+1);
						return ;
					}
				}
				total+=addQuantity;
			}
		}
	});
	postData['total']=total;
	$j("#nowNum").val(total);
	return postData;
}

function printStaCode(tag,event){
	var id = $j(tag).parents("tr").attr("id");
	loxia.lockPage();
	var url = $j("body").attr("contextpath") + "/warehouse/printstacode.do?staid=" + id;
	printBZ(loxia.encodeUrl(url),true);
	loxia.unlockPage();
} 



$j(document).ready(function (){
	
	var status=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"whSTAProcessStatus"});
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	if(!status.exception){
		//STA作业申请单
		$j("#tbl-inbound-purchase").jqGrid({
			//url:$j("body").attr("contextpath")+"/findpdapurchasestas.json",
			datatype: "json",
			postData:loxia._ajaxFormToObj("queryForm"),
			colNames: ["ID","STVID","STVTOTAL","STVMODE",i18("ENTITY_STA_CREATE_TIME"),i18("ENTITY_STA_CODE"),
				i18("ENTITY_STA_PROCESS"),"作业类型",i18("ENTITY_STA_UPDATE_TIME"),i18("ENTITY_STA_PO"),
				i18("ENTITY_STA_OWNER"),//i18("ENTITY_STA_SUP"),i18("ENTITY_STA_SEND_MODE"),i18("ENTITY_STA_REMANENT"),
				i18("ENTITY_STA_COMMENT"),"操作","IS_PDA","isInboundInvoice"],
			colModel: [
			           {name: "id", index: "id", hidden: true},		         
			           {name: "stvId", index: "stvId", hidden: true},		         
			           {name: "stvTotal", index: "stv_total", hidden: true},		         
			           {name: "stvMode", index: "stv_mode", hidden: true},		         
			           {name: "createTime", index: "create_time", width: 120, resizable: true,sortable:false},
			           {name: "code", index: "sta.code",formatter:"linkFmatter", formatoptions:{onclick:"showdetail"}, width: 120, resizable: true,sortable:false},
			           {name: "processStatus", index: "status", width: 100, resizable: true, formatter:'select',editoptions:status,sortable:false},
				       {name: "intStaType", index:"type" ,width:70,resizable:true,formatter:'select',editoptions:staType},
			           {name: "inboundTime", index: "inbound_time", width: 120, resizable: true,sortable:false},
			           {name: "refSlipCode", index: "slip_code", width: 100, resizable: true,sortable:false},
			           {name: "owner", index: "owner", width: 120, resizable: true,sortable:false},
			          //{name: "remnant", index: "remnant", width: 100, resizable: true,sortable:false},
		               {name: "memo",index:"memo",width:120,resizable:true,sortable:false},
		               {name: "idForBtn", width: 90,resizable:true, formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("PRINGT"), onclick:"printStaCode(this,event);"}}},		               
		               {name: "isPDA",hidden: true},
		               {name: "isInboundInvoice", index:"isInboundInvoice", hidden: true}
		               ],
			caption: i18("TABLE_CAPTION_STA"),
			postData:{"columns":"id,stvId,stvTotal,stvMode,createTime,intStaType,code,processStatus,inboundTime,refSlipCode,owner,memo,isPDA,isInboundInvoice"},
		   	sortname: 'sta.create_time',
		   	pager:"#pager",
		   	viewrecords: true,
		    multiselect: false,
			sortorder: "desc",
			width:"auto",
			height:"auto",
			//loadonce:true,
			rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" },
			gridComplete: function(){
				var ids = $j("#tbl-inbound-purchase").jqGrid('getDataIDs');
				for(var i=0;i < ids.length;i++){
					var datas = $j("#tbl-inbound-purchase").jqGrid('getRowData',ids[i]);
					if(datas["isPDA"]=='true'){
						$j("#tbl-inbound-purchase").jqGrid('setRowData',ids[i],{"processStatus":"7"});
					}
				}
				loxia.initContext($j(this));
			}
		});
	}else{
		jumbo.showMsg(i18("ERROR_INIT"));
	}
	//STA作业申请单行
	$j("#tbl-orderNumber").jqGrid({
		datatype: "json",
		colNames: ["ID","STAID","SKUID","OWNER","skuCost",i18("ENTITY_SKU_BARCODE"),i18("ENTITY_SKU_JMCODE"),i18("ENTITY_SKU_KEYPROP"),
			i18("ENTITY_SKU_NAME"),i18("ENTITY_SKU_SUPCODE"),i18("ENTITY_STALINE_TOTAL"),
			i18("ENTITY_STALINE_COMPLETE"),i18("ENTITY_STALINE_SNS")],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},	
		           {name: "sta.id", index: "sta.id", hidden: true,sortable:false},	
		           {name: "skuId", index: "sku.id", hidden: true,sortable:false},	
		           {name: "owner", index: "owner", hidden: true,sortable:false},	
		           {name: "skuCost", index: "skuCost", hidden: true,sortable:false},	
		           {name: "barCode", index: "sku.BAR_CODE", width: 100, resizable: true,sortable:false},
		           {name: "jmcode", index: "sku.JM_CODE", width: 100, resizable: true,sortable:false},
		           {name: "keyProperties", index: "sku.KEY_PROPERTIES", width: 120, resizable: true,sortable:false},
		           {name: "skuName", index: "sku.name", width: 80, resizable: true,sortable:false},
		           {name: "jmskuCode", index: "sku.SUPPLIER_CODE", width: 120, resizable: true,sortable:false},
		           {name: "quantity", index: "quantity", width: 100, resizable: true,sortable:false},
		           {name: "completeQuantity", index: "completeQuantity", width: 100, resizable: true,sortable:false},
	              	//{name: "isSnSku", index:"isSnSku", width: 120,resizable:true,sortable:false, formatter:"boolButtonFmatter", formatoptions:{"buttons":{label:i18("LABEL_ADD_SNS"), onclick:"addsns(this,event);"}}},
		           {name: "isSnSku", index:"isSnSku", width: 70, hidden:true,resizable:true,sortable:false},
	               ],
		caption: i18("TABLE_CAPTION_STALINE"),
	   	sortname: 'isSnSku,sku.bar_Code',
	    multiselect: false,
	    postData:{"columns":"id,sta.id,skuId,owner,skuCost,isSnSku,barCode,jmcode,keyProperties,skuName,jmskuCode,quantity,completeQuantity"},
		sortorder: "desc", 
	 	pager:"#pager1",
	   	viewrecords: true,
		height:"auto",
		loadonce:true,
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	//查寻
	$j("#PDA_search").click(function(){
		var url = $j("body").attr("contextpath")+"/findpdapurchasestas.json";
		$j("#tbl-inbound-purchase").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
	});
	
	//重置
	$j("#PDA_reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
	});
	
	//返回
	$j("#PDA_break").click(function(){
		window.location=loxia.getTimeUrl($j("body").attr("contextpath")+"/warehouse/pdapurchaseentry.do");
	});
	//pda 收货
	$j("#pdaSave").click(function(){
		/**
		 *须要坐数据处理 
		 */
		//staListToggle();
		//stvToggle(f);
		if(confirm(i18("MSG_CONFIRM"))){
			var postData = {};
			postData["staid"]=$j("#staId").val();
			postData["code"]=$j("#staCode3").val();
			loxia.lockPage();
			loxia.asyncXhrPost($j("body").attr("contextpath") + "/warehouse/executepdapurchase.json",
				postData,
				{
				success:function (data) {
					loxia.unlockPage();
					if(data&&data.result){
						jumbo.showMsg(i18("MSG_SUCCESS"));
						$j("#stvId").val(data.stvId);
						if(data.sncount && data.sncount>0) divToggle("#divSnImport");
						staToggle();
						var staData = $j("#staId").data("sta");
						tostvInfo(data.stvId,staData,true);
					}else {
						jumbo.showMsg(i18("MSG_FAILURE")+data.message);
						window.location=loxia.getTimeUrl($j("body").attr("contextpath")+"/warehouse/pdapurchaseentry.do");
					}
				   },
				error:function(data){
					loxia.unlockPage();
					jumbo.showMsg(i18("MSG_FAILURE"));
				}
			});
		}
	});
	
	//执行本次入库
	$j("#pdaExecuteInventory,#pdaClosePO").click(function(){
		var postData = {};
		/*if(IS_INBOUND_INVOICE==1){
			if(IS_INVOICE_CODE == 0){
				jumbo.showMsg("请填写入发票号！");
				return;
			}
			if($j("#invoice_invoiceNumber").val() == ''){
				jumbo.showMsg("请填写入发票号！");
				return;
			}
			postData["staCommand.invoiceNumber"] = $j("#invoice_invoiceNumber").val();
			postData["staCommand.dutyPercentage"] = $j("#invoice_dutyPercentage").val();
			postData["staCommand.miscFeePercentage"] = $j("#invoice_miscFeePercentage").val();
		}*/
		if($j(this).attr("id") == "pdaClosePO"){
			postData["isFinish"] = true;
		}
		postData["staCommand.id"] = $j("#staId").val();
		postData["pdaCode"] = $j("#pdaCode").val();
		var url = loxia.getTimeUrl($j("body").attr("contextpath") + "/pdaInboundPurchase.json");
		loxia.lockPage();
		loxia.asyncXhrPost(url,postData,{
			success:function (data) {
				loxia.unlockPage();
				if(data&&data.result == "success"){
					jumbo.showMsg("执行成功");
					if(data.stvid && data.stvid == $j("#stvId").val()){
						var staData = $j("#staId").data("sta");
						divToggle("#div-stv");
						tostvInfo($j("#stvId").val(),staData,true);
					} else {
						window.location=loxia.getTimeUrl($j("body").attr("contextpath")+"/warehouse/pdapurchaseentry.do");
					}
				}else{
					jumbo.showMsg(data.msg);
				}
		    },
			error:function(data){
				loxia.unlockPage();
				jumbo.showMsg(i18("MSG_FAILURE"));
			}
		});
	});
	
	//取消本次收货
	$j("#pdaCancelStv").click(function(){
		if(confirm("您确定要取消本次收货吗?")){
			loxia.lockPage();
			loxia.asyncXhrPost($j("body").attr("contextpath") + "/pdaCancelStv.json",
				{'stv.id':$j("#stvId").val()},
				{
				success:function (data) {
					loxia.unlockPage();
					if(data&&data.msg){
						jumbo.showMsg(data.msg);
					}
					window.location=loxia.getTimeUrl($j("body").attr("contextpath")+"/warehouse/pdapurchaseentry.do");
				   },
				error:function(data){
					loxia.unlockPage();
					jumbo.showMsg(i18("MSG_FAILURE"));
					window.location=loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/pdapurchaseentry.do");
				}
				});
		}
	});
	
	//sn号导入
	$j("#import").click(function(){
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));//请选择正确的Excel导入文件
			return false;
		}
		loxia.lockPage();
		$j("#importForm").attr("action",$j("body").attr("contextpath") + "/warehouse/pdapurchasesnimport.do?staid="+$j("#staId").val());
		$j("#importForm").submit();
	});
	
	$j("#queryPDACode").click(function(){
		var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/pdaCodeQuery.json?staid="+$j("#staId").val());
		$j("#pdaCode option").remove();
		$j("<option value=''>　</option>").appendTo($j("#pdaCode"));
		if(result){
			if(result.length==0){
				jumbo.showMsg("未找到数据");
			}else{
				for(var idx in result){
					$j("<option value='" + result[idx].pdaCode + "'>"+ result[idx].pdaCode +"</option>").appendTo($j("#pdaCode"));
				}
			}
		} else {
			jumbo.showMsg("获取数据异常");
		}
	});
	
	

});

function initPdaCode(){
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/pdaCodeQuery.json?staid="+$j("#staId").val());
	$j("#pdaCode option").remove();
	$j("<option value=''>　</option>").appendTo($j("#pdaCode"));
	if(result){
		for(var idx in result){
			$j("<option value='" + result[idx].pdaCode + "'>"+ result[idx].pdaCode +"</option>").appendTo($j("#pdaCode"));
		}
	}
}


function showMsg(msg){
	jumbo.showMsg(msg);
}
//根据stvId显示带有建议库位的预上架列表
function tostvInfo(stvId,sta,isPda){
	loxia.lockPage();
	stvToggle();
	initPdaCode();
	if(isPda){
		setPdaInit(window.parent.$j("body").attr("contextpath"),sta["code"],sta["id"]);
		$j("#pdaOpDiv").removeClass("hidden");
		$j("#inboundNormalDiv").addClass("hidden");
		pdaLogReload();
		loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+"/suggestlocation.json",{	"stv.id":stvId},{
			success:function (data) {
	 			loxia.unlockPage();
			}, 
			error:function(data){
				loxia.unlockPage();
            }
		});
	}else{
		jumbo.showMsg("非PDA收货作业单");
	}
}
