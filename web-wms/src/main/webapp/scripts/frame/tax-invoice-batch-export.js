$j.extend(loxia.regional['zh-CN'],{
	GET_DATA_ERROR		:"获取数据异常",

	STA_CODE : "作业单号",
	STA_SLIP_CODE : "相关单据号",
	INVOICE_STATUS : "发票状态",
	OWNER : "平台店铺名称",
	LPCODE: "物流服务商",
	TRACKING_NO : "快递单号",
	CRAETE_TIME : "创建时间",
	OPERATOR_TIME : "操作时间",
	LAST_MODIFY_TIME : "最后修改时间",
	OPERATOR : "操作人",
	PLAN_COUNT : "计划执行总数量",
	BATCHCODE : "批次号",
	PGINDEX : "顺序号",
	PROVINCE : "省",
	CITY : "市",
	DISTRICT : "区",
	ADDRESS : "地址",
	RECEIVER : "收件人",
	MOBILE : "手机",
	TELEPHONE : "电话",
	OPERATING : "操作",
	LPCODE_IS_NOT_EQUAL : "批次操作选择物流商必须一致！",
	OWNER_IS_NOT_EQUAL : "批次操作选择店铺必须一致！",
	
	INVOICE_ORDER_LIST_TITLE:"批量补开发票导出-电子面单打印",
	NOT_EQUAL_BATCHNO : "不同批次的发票信息不允许同时批量生成",
	UPDATE_INVOICE_STATUS_SUCCESS : "批量更新发票信息成功！",
	UPDATE_INVOICE_STATUS_ERROR : "批量更新发票信息失败！",
	
	IN_INV:"退换货入库",
	OUT_INV:"换货出库",
	DATA_DETAIL_SELECT : "请选择具体数据！",
	
	
	SKUCODE : "SKU编码",
	BARCODE : "条形码",
	JMCODE : "商品编码",
	SKU_NAME : "商品名称",
	KEY_PROPS : "扩展属性",
	PLAN_QTY : "计划量",
	COMFIRMED_QTY : "执行量",
	SKU_COST : "库存成本",
	
	DETAIL_LIST_TITLE:"销售作业单明细",
	
	STATUS_CREATE:"已创建",
	STATUS_OCCUPIED:"配货中",
	STATUS_CHECKED:"已核对",
	STATUS_INTRANSIT:"已转出",
	STATUS_FINISHED:"已完成"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

var baseUrl = "";


//单张物流面单的打印
function printSingleInvoiceDelivery(tag,event){
	var id = $j(tag).parents("tr").attr("id");
	var data=$j("#tbl-invoiceOrderList").jqGrid("getRowData",id);
	var wioId = data["id"];
	loxia.lockPage();
	var dt = new Date().getTime();
	var url = $j("body").attr("contextpath") + "/printSingleInvoiceOrderDeliveryOut.json?rt="+ dt +"&wioCmd.id=" + wioId;
	printBZ(loxia.encodeUrl(url),true);
	loxia.unlockPage();
	//查询
	$j("#search").trigger("click");
} 
function initChannel(){
	loxia.asyncXhrPost(
		window.parent.$j("body").attr("contextpath")+"/json/findAllChannel.do",
		{},
		{
			success:function(data){
				var rs = data.channelList;
				if(rs.length>0){
					$j("#companyshop option").remove();
					$j("#companyshop").append("<option value=''></option>");
					$j.each(rs,function(i,item){
						$j("#companyshop").append("<option value='"+item.code+"'>"+item.name+"</option>");
					});
					$j("#companyshop").flexselect();
					$j("#companyshop").val("");
				}
			}
		}
	);
}
$j(document).ready(function (){
	loxia.init({debug: true, region: 'zh-CN'});
	initChannel();
	$j("#dialog_error_ok").dialog({title: "错误信息", modal:true, autoOpen: false, width: 400,closeOnEscape: false});
	$j("#dialog_ok").dialog({title: "打印和导出", modal:true, autoOpen: false, width: 400,closeOnEscape: false});
    /* 这里编写必要的页面演示逻辑*/
	baseUrl = $j("body").attr("contextpath");
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransportator.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selLpcode"));
	}
	$j("#tbl-invoiceOrderList").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("STA_CODE"),i18("INVOICE_STATUS"),i18("OWNER"),i18("LPCODE"),i18("TRACKING_NO"),i18("CRAETE_TIME"),i18("LAST_MODIFY_TIME"),"完成时间","系统来源",i18("OPERATING"),i18("BATCHCODE"),i18("PGINDEX")],
		colModel: [
		        {name: "id", index: "id", hidden: true},
				{name:"orderCode", index:"orderCode",width:100, resizable:true},
				{name:"intStatus",index:"intStatus",width:80,resizable:true,formatter:'select',editoptions:staStatus},
				{name:"owner", index:"owner",width:120,resizable:true},
				{name:"lpCode", index:"lpCode",width:80,resizable:true,formatter:'select',editoptions:trasportName},
				{name:"transNo", index:"transNo",width:120,resizable:true},
				{name:"createTime",index:"createTime",width:150,resizable:true},
				{name:"lastModifyTime",index:"lastModifyTime",width:150,resizable:true},
				{name:"finishTime",index:"finishTime",width:150,resizable:true},
				{name:"systemKey",index:"systemKey",width:150,resizable:true},
				{name:"btnPrint", width:80,resizable:true},
				{name:"batchCode", index:"batchCode",width:120,resizable:true},
				{name:"pgIndex", index:"pgIndex",width:60,resizable:true}],
		caption: i18("INVOICE_ORDER_LIST_TITLE"),
	   	sortname: 'ID',
	    multiselect: false,
	    rowNum:jumbo.getPageSize(),
	   	rowList:jumbo.getPageSizeList(),
	   	pager:"#pager",
		sortorder: "asc",
		viewrecords: true,
	   	rownumbers:true,
		height:jumbo.getHeight(),
		multiselect: true,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			//运单打印
			var btn1 = '<div><button type="button" style="width:80px;" class="btnPrint" name="btnPrint" loxiaType="button" onclick="printSingleInvoiceDelivery(this,event);">'+"打印运单"+'</button></div>';
			var ids = $j("#tbl-invoiceOrderList").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				$j("#tbl-invoiceOrderList").jqGrid('setRowData',ids[i],{"btnPrint":btn1});
			}
			loxia.initContext($j(this));
		}
	});
	
	//查询
	$j("#search").click(function (){
		$j("#tbl-invoiceOrderList").jqGrid('setGridParam',{
			url:loxia.getTimeUrl(baseUrl + "/queryInvoiceOrderExport.json"),postData:loxia._ajaxFormToObj("queryForm")
			}).trigger("reloadGrid");
	});
	
	//重置
	$j("#reset").click(function (){
		$j("#queryForm input,select").val("");
		$j("#wioCmdStatus").val("");
	});
	
	//返回
	$j("#back").click(function(){
		$j("#query-order-list").removeClass("hidden");
		$j("#order-detail").addClass("hidden");
	});
	
	//错误提示
	function errorTipOK(text) {
		$j("#error_text_ok").html("<font style='text-align:center;font-size:30px' color='red'>"+text+"</font>");
		$j("#dialog_error_ok").dialog("open");
	}
	
	//成功提示
	function successTipOK(msg,divId) {
		$j("#batchNo").html("");
		if(msg!=null){
			$j("#batchNo").html(msg);
		}
		$j("#orderOp,#batchOp").addClass("hidden");
		$j("#"+divId).removeClass("hidden");
		var $s = $j("#dialog_ok");
		$j("#dialog_ok").dialog("open");
		if(msg!=null){
			$j(".ui-dialog-titlebar-close", $s.parent()).hide();
		}else{
			$j(".ui-dialog-titlebar-close", $s.parent()).show();
		}
	}
	
	$j("#dialog_error_close_ok").click(function(){
		$j("#dialog_error_ok").dialog("close");
	});
	
	//确定按钮，点击表示发票订单打印和导出物流完成
	$j("#wmsInvoiceSure").click(function(){
		var postData = {};
		var ids = jQuery("#tbl-invoiceOrderList").jqGrid('getGridParam','selarrrow');
		for(var i in ids){
   		 	postData['wioIdlist[' + i + ']']=ids[i];
   	 	}
		if(confirm("确定是否已经导出物流面单和打印发票信息已经完成？确认后即将更新发票单据状态为完成。")){
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/updateWmsInvoiceOrderStatus.json",postData);
			if(rs && rs["result"] == "success"){
				jumbo.showMsg(i18("UPDATE_INVOICE_STATUS_SUCCESS"));
				$j("#dialog_ok").dialog("close");
				//查询
				$j("#search").trigger("click");
			}else{
				jumbo.showMsg(i18("UPDATE_INVOICE_STATUS_ERROR"));
			}
		}
	});
	
	//打印面单
	$j("#print_All_Express").click(function(){
		var swioIdlist = "";
		var tempLpCode = "";
		var ids = jQuery("#tbl-invoiceOrderList").jqGrid('getGridParam','selarrrow');
		for(var i in ids){
			 var data=$j("#tbl-invoiceOrderList").jqGrid("getRowData",ids[i]);
			 if(i>0){
				 swioIdlist = swioIdlist +","+ data["id"];
			 }else{
				 swioIdlist = swioIdlist + data["id"];
			 }
			 
    		 if(i==0){
    			 tempLpCode = data["lpCode"];
    		 }else{
    			 var ss =  data["lpCode"];
    			 if(ss != tempLpCode){
    				 jumbo.showMsg(i18("LPCODE_IS_NOT_EQUAL"));
    				 return;
    			 }
    		 }
			 
   	    }
		loxia.lockPage();
		var url = $j("body").attr("contextpath") + "/printSelectedWmsInvoiceOrderTrans.json?swioIdlist="+swioIdlist;
		printBZ(loxia.encodeUrl(url),false);
		loxia.unlockPage();
	});
	
	//批量导出发票信息
	$j("#export_All_Invoice").click(function(){
		var swioIdlist = "";
		var batchNo = null;
		var ids = jQuery("#tbl-invoiceOrderList").jqGrid('getGridParam','selarrrow');
		for(var i in ids){
			 var data=$j("#tbl-invoiceOrderList").jqGrid("getRowData",ids[i]);
			 if(i>0){
				 swioIdlist = swioIdlist +","+ data["id"];
			 }else{
				 swioIdlist = swioIdlist + data["id"];
				 batchNo = data["batchCode"];
			 }
   	    }
	   if (batchNo == null || batchNo == "") {
		   batchNo = $j("#batchNo").text();
	   }
	   $j("#wmsInvoiceOrderfrm").attr("src",$j("body").attr("contextpath") + "/warehouse/exportWmsInvoiceOrder.do?batchNo=" + batchNo+"&swioIdlist="+swioIdlist);
	});
	
	
	$j("#export").click(function(){
		//无批次号
		 var postData = {};
		 var tempLpCode = "";
		 var tempOwner = "";
		 var ids = jQuery("#tbl-invoiceOrderList").jqGrid('getGridParam','selarrrow');
		 //alert(JSON.stringify(ids));
	     if(ids.length==0){
	    	 jumbo.showMsg(loxia.getLocaleMsg("DATA_DETAIL_SELECT"));	//请选择具体数据！！
    	 	 return;
         }else{
        	 //所选发票信息都未进行批量生成操作（无批次号）,所有被选发票信息来源同已批次（相同批次号），显示左上角批次号，不同则显示错误提示窗口
        	 var flag = true;
        	 var bc = null;
        	 var msg=null;
        	 var status=null;
        	 for(var i in ids){
        		 var data=$j("#tbl-invoiceOrderList").jqGrid("getRowData",ids[i]);
        		 if(null==status){
        			 status = data["intStatus"];
        		 }else{
        			 if(status!=data["intStatus"]){
        				 flag = false;
        				 msg = "选定单据状态必须一致!";
        				 break;
        			 }
        		 }
        		 if(null==bc){
        			 bc = data["batchCode"];
        		 }else{
        			 if(bc!=data["batchCode"]){
        				 flag = false;
        				 msg = "选定单据批次号必须一致!";
        				 break;
        			 }
        		 }
        		 postData['wioIdlist[' + i + ']']=ids[i];
        		 if(i==0){
        			 tempLpCode = data["lpCode"];
        			 tempOwner = data["owner"];
        		 }else{
        			 var ss =  data["lpCode"];
        			 var oo = data["owner"];
        			 if(ss != tempLpCode){
        				 jumbo.showMsg(i18("LPCODE_IS_NOT_EQUAL"));
        				 return;
        			 }
        			 if(oo != tempOwner){
        				 jumbo.showMsg(i18("OWNER_IS_NOT_EQUAL"));
        				 return;
        			 }
        		 }
        	 }
        	 if(flag){
        		 if(bc!=null&&bc!=""){//如果存在批次则部分打印，要传单据明细
        			 successTipOK(null,"orderOp");
        		 }else{
	        		 var ret = loxia.syncXhr($j("body").attr("contextpath")+"/openPrintAndExportPage.json",postData);
	        		 if(ret!=null&&"success" == ret["result"]){
	        			 successTipOK(ret["batchCode"],"batchOp");
	        		 }else{
	        			 jumbo.showMsg(ret["msg"]);
	        		 }
        		 }
        	 }else{
        		 jumbo.showMsg(msg);
        	 }
         }
	});
});
