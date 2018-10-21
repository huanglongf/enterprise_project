$j.extend(loxia.regional['zh-CN'],{
	CODE : "配货批次号",
	INTSTATUS : "状态",
	PLANBILLCOUNT : "计划配货单据数",
	CHECKEDBILLCOUNT : "已核对单据数",
	SHIPSTACOUNT : "已发货单据数",
	PLANSKUQTY : "计划配货商品件数",
	CHECKEDSKUQTY : "已核对商品件数",
	SHIPSKUQTY : "已发货商品件数",
	CHECKEDTIME : "最后核对时间",
	EXECUTEDTIME : "最后发货时间",
	PICKINGTIME : "开始配货时间",
	PICKING_LIST : "配货清单列表",
	CREATE_TIME : "创建时间",
	INIT_SYSTEM_DATA_EXCEPTION : "初始化系统参数异常",
	KEY_PROPS : "扩展属性",
	MEMO : "备注",
	STA_CODE : "作业单号",
	INTSTATUS : "状态",
	REFSLIPCODE : "相关单据号",
	INTTYPE : "作业类型名称",
	OWNER : "相关店铺",
	LPCODE : "物流服务商简称",
	CREATETIME : "创建时间",
	STVTOTAL : "计划执行总件数",
	WAITTING_CHECKED_LIST : "待核对列表",
	
	CHECKED_LIST : "已核对列表",
	SKUCODE : "SKU编码",
	LOCATIONCODE : "库位编码预览",
	SKUNAME : "商品名称",
	JMSKUCODE : "商品编码",
	BARCODE : "条形码",
	QUANTITY : "计划执行数量",
	COMPLETEQUANTITY : "已执行数量",
	
	INPUT_CODE : "请输入相关单据号",
	NO_CODE : "指定单据号的作业单不在待核对的列表！",
	BARCODE_NOT_EXISTS : "条形码不存在",
	TRACKINGNO_EXISTS : "快递单号已经存在",
	DELETE : "删除",
	TRACKINGNO_RULE_ERROR : "快递单号格式不对",
	SURE_OPERATE : "确定执行本次操作",
	QUANTITY_ERROR : "数量错误",
	WEIGHT_RULE_ERROR : "已执行量大于计划执行量，如果继续执行核对，将直接按照计划执行量执行核对。是否继续？",
	INPUT_TRACKINGNO : "请输入快递单号",
	OPERATE_FAILED : "执行核对失败！",
	EXISTS_QUANTITY_GQ_EXECUTEQTY : "请检查核对数量,存在商品未核对"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function getFmtValue(tableId,rowId,column){
	return $j("#"+tableId+" tr[id="+rowId+"] td[aria-describedby='"+tableId+"_"+column+"']").attr("title")
}
function reloadSta(plId){
	$j("#tbl-showDetail").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/staoccupiedlist.json"),
			postData:{"plCmd.id":plId}}).trigger("reloadGrid",[{page:1}]);
	$j("#tbl-showReady").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/stacheckedlist.json"),
			postData:{"plCmd.id":plId}}).trigger("reloadGrid",[{page:1}]);
}

function showDetail(obj){
	
	$j("#showList").addClass("hidden");
	$j("#showDetail").removeClass("hidden");
	$j("#billsId-2").focus();
	var row=$j(obj).parent().parent().attr("id"),pl=$j("#tbl-dispatch-list").jqGrid("getRowData",row);
	$j("#verifyPlId").val(row);
	$j("#showDetail").attr("plId",row);
	$j("#verifyCode").html(pl['code']);
	$j("#verifyStatus").html(getFmtValue("tbl-dispatch-list",row,"intStatus"));
	$j("#verifyPlanBillCount").html(pl['planBillCount']);
	$j("#verifyCheckedBillCount").html(pl['checkedBillCount']);
	$j("#verifyShipStaCount").html(pl['shipStaCount']);
	$j("#verifyPlanSkuQty").html(pl['planSkuQty']);
	$j("#verifyCheckedSkuQty").html(pl['checkedSkuQty']);
	$j("#verifyShipSkuQty").html(pl['shipSkuQty']);
	reloadSta(row);
}
var barcodeList;
var rs0;
var lineNo;
var isNotLineNo;
$j(document).ready(function (){
	rs0 = loxia.syncXhr($j("body").attr("contextpath") + "/json/checkIsNeedBarcode.json",null);
	
	//$j("express_order").focus();
	$j("#dialog_error").dialog({title: "错误信息", modal:true, autoOpen: false, width: 400});
	$j("#dialog_error_recheck").dialog({title: "错误信息", modal:true, autoOpen: false, width: 400});

	
	$j("#whName").html(window.parent.$j("#orgname").html());
	var plstatus=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"pickingListStatus"});
	if(!plstatus.exception){
		$j("#tbl-dispatch-list").jqGrid({
			url:$j("body").attr("contextpath") + "/pllistforverify.json",
			datatype: "json",//"已发货单据数","已发货商品件数",
			//colNames: ["ID","配货批次号","状态","计划配货单据数","已核对单据数","已发货单据数","计划配货商品件数","已核对商品件数","已发货商品件数","最后核对时间","最后发货时间","开始配货时间"],
			colNames: ["ID",i18("CODE"),i18("INTSTATUS"),i18("PLANBILLCOUNT"),i18("CHECKEDBILLCOUNT"),i18("SHIPSTACOUNT"),i18("PLANSKUQTY"),i18("CHECKEDSKUQTY"),i18("SHIPSKUQTY"),
				i18("CREATE_TIME"),i18("CHECKEDTIME"),i18("EXECUTEDTIME"),i18("PICKINGTIME")],
			colModel: [{name: "id", index: "pl.id", hidden: true},
						{name:"code",index:"pl.code",formatter:"linkFmatter",formatoptions:{onclick:"showDetail"},width:100,resizable:true},
						{name:"intStatus", index:"pl.status" ,width:60,resizable:true,formatter:'select',editoptions:plstatus},
						{name:"planBillCount", index:"pl.plan_bill_count",width:100,resizable:true},
						{name:"checkedBillCount", index:"pl.checked_bill_count", width:90, resizable:true},
						{name:"shipStaCount",index:"pl.send_bill_count",width:90,resizable:true},
						{name:"planSkuQty",index: "pl.plan_sku_qty",width:120,resizable:true},
						{name:"checkedSkuQty",index:"pl.checked_sku_qty",width:100,resizable:true},
						{name:"shipSkuQty",index:"pl.send_sku_count",width:100,resizable:true},
						{name:"createTime",index:"pl.CREATE_TIME",width:150,resizable:true},
						{name:"checkedTime",index:"pl.CHECK_TIME",width:150,resizable:true},
						{name:"executedTime",index:"pl.last_outbound_time",width:150,resizable:true},
		               {name:"pickingTime",index:"pl.PICKING_TIME",width:150,resizable:true}],
			caption: i18("PICKING_LIST"),//配货清单列表
		   	sortname: 'pl.CREATE_TIME',
		    multiselect: false,
			sortorder: "desc",
			width:"auto",
			height:"auto",
			rowNum:-1,
			viewrecords: true,
		   	rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" }
		});
	}else{
		jumbo.showMsg(i18("INIT_SYSTEM_DATA_EXCEPTION"));//初始化系统参数异常
	}
	$j("#search").click(function(){
		$j("#tbl-dispatch-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/pllistforverify.json"),
			postData:loxia._ajaxFormToObj("plForm")}).trigger("reloadGrid",[{page:1}]);
	});
	$j("#reset").click(function(){
		document.getElementById("plForm").reset();
	});
	var stastatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.json",{"categoryCode":"whSTAStatus"}),
		statype=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.json",{"categoryCode":"whSTAType"});
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.json");
	if(!stastatus.exception&&!stastatus.statype){
		$j("#tbl-showDetail").jqGrid({
	//		url:$j("body").attr("contextpath") + "/staoccupiedlist.json",
			datatype: "json",//"已发货单据数","已发货商品件数",
			//colNames: ["ID","备注","作业单号","状态","相关单据号","作业类型名称","相关店铺","物流服务商简称","创建时间","计划执行总件数"],
			colNames: ["ID",i18("MEMO"),i18("STA_CODE"),i18("INTSTATUS"),i18("REFSLIPCODE"),i18("INTTYPE"),i18("OWNER"),i18("LPCODE"),i18("CREATETIME"),i18("STVTOTAL"),"快递单号"],
			colModel: [{name: "id", index: "id", hidden: true},
						{name: "memo", index: "memo", hidden: true},
						{name :"code",index:"code",width:100,resizable:true},
						{name:"intStatus", index:"intStatus" ,width:80,resizable:true,
							formatter:'select',editoptions:stastatus},
						{name: "refSlipCode", index:"refSlipCode",width:120,resizable:true},
						{name:"intType", index:"intType", width:90, resizable:true,
							formatter:'select',editoptions:statype},
						{name:"owner",index:"owner",width:120,resizable:true},
						{name:"lpcode",index: "lpcode",width:100,resizable:true,formatter:'select',editoptions:trasportName},
						{name:"createTime",index:"sta.CREATE_TIME",width:120,resizable:true},
		                {name:"stvTotal",index:"stvTotal",width:70,resizable:true},
		                {name:"transNo",index:"delinfo.TRACKING_NO",width:90,resizable:true}
						],
			caption: i18("WAITTING_CHECKED_LIST"),//待核对列表
		   	sortname: 'sta.CREATE_TIME',
		    multiselect: false,
			sortorder: "desc",
			width:"auto",
			height:"auto",
			rowNum:-1,
			viewrecords: true,
   			rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" }
		});
		
		$j("#tbl-showReady").jqGrid({
			datatype: "json",
			//colNames: ["ID","作业单号","状态","相关单据号","作业类型名称","相关店铺","物流服务商简称","创建时间","计划执行总件数"],
			colNames: ["ID",i18("STA_CODE"),i18("INTSTATUS"),i18("REFSLIPCODE"),i18("INTTYPE"),i18("OWNER"),i18("LPCODE"),i18("CREATETIME"),i18("STVTOTAL"),"核对时间"],
			colModel: [{name: "id", index: "id", hidden: true},
						{name :"code",index:"code",width:120,resizable:true},
						{name:"intStatus", index:"intStatus" ,width:80,resizable:true,
							formatter:'select',editoptions:stastatus},
						{name: "refSlipCode", index:"planBillCount",width:150,resizable:true},
						{name:"intType", index:"intType", width:90, resizable:true,
							formatter:'select',editoptions:statype},
						{name:"owner",index:"owner",width:120,resizable:true},
						{name:"lpcode",index: "lpcode",width:120,resizable:true,formatter:'select',editoptions:trasportName},
						{name:"createTime",index:"sta.CREATE_TIME",width:150,resizable:true},
		               {name:"stvTotal",index:"stvTotal",width:100,resizable:true},
		               {name:"checkTime",index:"checkTime",width:100,resizable:true}],
			caption: i18("CHECKED_LIST"),//已核对列表
		   	sortname: 'sta.status',
		    multiselect: false,
			sortorder: "desc",
			width:"auto",
			height:"auto",
			rowNum:-1,
			viewrecords: true,
   			rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" }
		});
	}else{
		jumbo.showMsg(i18("INIT_SYSTEM_DATA_EXCEPTION"));//初始化系统参数异常
	}
	
	$j("#reloadSta").click(function(){
		//$j("#tbl-showReady").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/pllistforverify.json"),postData:{"plCmd.id":0}}).trigger("reloadGrid",[{page:1}]);
		showDetail($j("#tbl-dispatch-list").jqGrid("getRowData",$j("#showDetail").attr("plId"))['code']);
		reloadSta($j("#showDetail").attr("plId"));
	});
	
	$j("#tbl-billView").jqGrid({
		//url:"dispatch-list-view.json",
		datatype: "json",
		//colNames: ["ID","库位编码预览","商品名称","商品编码","条形码","计划执行数量","已执行数量"],
		colNames: ["ID",i18("SKUCODE"),i18("LOCATIONCODE"),i18("SKUNAME"),i18("JMSKUCODE"),"货号",i18("KEY_PROPS"),i18("BARCODE"),i18("QUANTITY"),i18("COMPLETEQUANTITY")],
		colModel: [{name: "id", index: "id", hidden: true},
					{name:"skuCode", index:"skuCode" ,width:150,resizable:true},
					{name :"locationCode",index:"locationCode",width:120,resizable:true,hidden: true},
					{name:"skuName", index:"skuName" ,width:150,resizable:true},
					{name: "jmcode", index:"jmcode",width:150,resizable:true},
					{name: "supplierCode", index:"sku.supplier_code",width:150,resizable:true},
					{name: "keyProperties", index:"keyProperties",width:60,resizable:true},
					{name:"barCode", index:"barCode", width:90,hidden: true, resizable:true},
					{name:"quantity",index:"quantity",width:120,resizable:true},
	                {name:"completeQuantity",index:"completeQuantity",width:100,resizable:true}],
		caption: i18("CHECKED_LIST"),//已核对列表
	   	sortname: 'sku.code',
	    multiselect: false,
		sortorder: "desc",
		rowNum: -1,
		height:"auto",
		gridComplete: function() {
			var postData = {};
			$j("#tbl-billView tr:gt(0)>td[aria-describedby$='barCode']").each(function (i,tag){
				postData["skuBarcodes[" + i + "]"] = $j(tag).html();
			});
			barcodeList = loxia.syncXhr($j("body").attr("contextpath") + "/findskuBarcodeList.json",postData);
		},
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#refSlipCode").focus();
	
	$j("#iptPlCode").keydown(function(evt){
		if(evt.keyCode === 13){
			$j(".refSlipCode").trigger("keydown");
		}
	});
	
	$j(".refSlipCode").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			var _this=$j(this);
			var itemid=$j(this).attr("id");
			var plid;
			if(itemid=="refSlipCode"){
				plid = "iptPlCode"
			}else{
				plid = "iptPlCode1"
			}
			if(_this.val().length==0){
				loxia.tip(_this,i18("INPUT_CODE"));//请输入相关单据号
				return;
			}
			loxia.lockPage();
			loxia.asyncXhrPost($j("body").attr("contextpath") + "/json/stabyrefslipcode.json",
				{"sta.refSlipCode":_this.val(),
				 "plCode":$j("#"+plid).val()},
				{success:function (data) {
					loxia.unlockPage();
					if(data&&data.id){
						if(data['hasCancelUndoSta'] == 'true'){
							//在所有批次中存在取消未处理订单，要求输入批次号
							jumbo.showMsg("请输入批次号");
							$j("#"+plid).focus();
						}else{
							//进入核对界面
							$j("#tbl-billView").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/stalinebystaid.json"),
								postData:{"sta.id":data.id}}).trigger("reloadGrid",[{page:1}]);
							$j("#staCode").html(data['code']);
							$j("#staId").val(data['id']);
							$j("#staCreateTime").html(data['createTime']);
							$j("#verifyPlId").val(data['pickingList.id']);
							$j("#tbl-express-order tr:gt(0)").remove();
							var transName = loxia.syncXhrPost($j("body").attr("contextpath")+"/json/getTrasportName.json",{"optionKey":data['lpcode']});
							var stastatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/optionvalue.json",{"categoryCode":"whSTAStatus","optionKey":data["intStatus"]}),
								statype=loxia.syncXhr($j("body").attr("contextpath") + "/json/optionvalue.json",{"categoryCode":"whSTAType","optionKey":data["intType"]});
							$j("#staDelivery").html(transName.value.length>0?transName.value.split(":")[1]:'');
							$j("#staTranType").html(statype.optionValue);
							$j("#staRefCode").html(data['refSlipCode']);
							$j("#staStatus").html(stastatus.optionValue);
							$j("#staComment").html(data['memo']);
							 $j("#staTransNo").html(data['transNo']);
							$j("#staTotal").html(data['stvTotal']);
							$j("#showList").addClass("hidden");
							$j("#showDetail").addClass("hidden");
							$j("#showDetail").attr("plId",data['pickingList.id']);
							$j("#checkBill").removeClass("hidden");
							$j("#barCode").focus();
							_this.val('');
//							if(data['intCheckMode'] == 5){
//								errorTip("请使用配货批核对模式加快核对效率");
//							}
						}
					}else{
						loxia.tip(_this,i18("NO_CODE"));//指定单据号的作业单不在待核对的列表！
					}
				}
			});
		}
	});
	
	$j("#doCheck_recheck").click(function(){
		var data = $j("#tbl-billView").jqGrid('getRowData');
		$j.each(data,function(i, item){
			var d = $j("#tbl-billView").jqGrid('getRowData',item.id);
			d.completeQuantity = 0;
			$j("#tbl-billView").jqGrid('setRowData',item.id,d);
		});
		$j("#dialog_error_recheck").dialog("close");
		$j("#barCode").focus();
		$j("#doCheck_recheck").val("");
	});
	
	$j("#barCode_recheck").keydown(function(evt){
		var barCode1 = $j("#barCode_recheck").val();
		if(BARCODE_CONFIRM ==barCode1){
			$j("#doCheck_recheck").trigger("click");
		}
	});
	
	$j("#barCode").keydown(function(evt){
		if(evt.keyCode == 9){
			evt.preventDefault();
			$j("#express_order").select();
		}
		
		if(evt.keyCode === 13){
			$j("#flag0").val("barCode");
			evt.preventDefault();
			var rowid = 0,bc=$j.trim($j(this).val()),rs=false;
			if(bc.length==0)return false;
			var data = $j("#tbl-billView").jqGrid('getRowData');
			$j.each(data,function(i, item){
				var bc1=item.barCode;
				if(bc1==bc){
					var d = $j("#tbl-billView").jqGrid('getRowData',item.id);
					var num =  d.completeQuantity;
					if(num==null||num=='')num=0;
					var inQty = parseInt($j("#inQty").val());
					d.completeQuantity = parseInt(num)+inQty;
					$j("#inQty").val(1);
					$j("#tbl-billView").jqGrid('setRowData',item.id,d);
					rs=true;
					return false;
				}else if(bc1 == '00' + bc){
					var d = $j("#tbl-billView").jqGrid('getRowData',item.id);
					var num =  d.completeQuantity;
					if(num==null||num=='')num=0;
					var inQty = parseInt($j("#inQty").val());
					d.completeQuantity = parseInt(num)+inQty;
					$j("#inQty").val(1);
					$j("#tbl-billView").jqGrid('setRowData',item.id,d);
					rs=true;
					return false;
				}
				
				if(!rs){
					for(var v in barcodeList[bc1]){
						if(barcodeList[bc1][v] == bc || barcodeList[bc1][v] == ('00'+bc) ){
							var d = $j("#tbl-billView").jqGrid('getRowData',item.id);
							var num =  d.completeQuantity;
							if(num==null||num=='')num=0;
							var inQty = parseInt($j("#inQty").val());
							d.completeQuantity = parseInt(num)+inQty;
							$j("#inQty").val(1);
							$j("#tbl-billView").jqGrid('setRowData',item.id,d);
							rs=true;
							return false;
						}
					}
				}
			});
			if(!rs){
				//loxia.tip(this,i18("BARCODE_NOT_EXISTS"));//条形码不存在
				//$j("#barCode").select();
				errorTip(i18("BARCODE_NOT_EXISTS"));
				return;
			}
			$j("#barCode").val("");
			$j("#barCode").focus();
			loxia.tip(this);
			//如果计划量等于执行量则光标跳到快递单输入框
			removeCursor();
		}
		
	});
	
	$j("#express_order").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			var transNo = $j("#staTransNo").html();
			if(transNo != "" && transNo != $j.trim($j(this).val())){
				errorTip("快递单号不匹配！");
				return;
			}
			var billId = $j.trim($j(this).val());
			var ch = $j("#"+billId);
			if(ch.length!=0){
				 //loxia.tip(this,i18("TRACKINGNO_EXISTS"));//快递单号已经存在
				 $j("#flag0").val("express_order");
				 errorTip(i18("TRACKINGNO_EXISTS"));
//				 $j(this).select();
				 return;
			}else{
				loxia.tip(this);
			}
			//校验快递单号的合法性
			var staId = $j.trim($j("#staId").val());
			var data ={trackingNo:billId};
			data["sta.id"]=staId;
			var rs = loxia.syncXhr($j("body").attr("contextpath") + "/json/checktrackingno.json",data);
			if(rs && rs.result=='success'){
				if(billId != ""&&billId != null){
					var str = "<tr id=\""+billId+"\"><td class='label' height='36px' style='text-align: center;font-size: 36px' >"+billId+"</td><td>" +
					"<button style='width: 200px;height: 45px;font-size=36px' onclick=\"doDelete('"+billId+"')\">"+i18("DELETE")+"</button></td></tr>";//删除	
					$j("#tbl-express-order").append(str);			
				}
				loxia.tip(this);
				$j(this).val("");
				$j(this).focus();
			}else {
				$j("#flag0").val("express_order");
				errorTip(i18("TRACKINGNO_RULE_ERROR"));
				//loxia.tip(this,i18("TRACKINGNO_RULE_ERROR"));//快递单号格式不对
//				$j(this).select();
				return ;
			}
			$j("#barCode1").focus();
			
		}});
	
	$j("#backToPlList").click(function(){
		window.location=loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/plverifyentry.do");
	});
	
	$j("#Back").click(function(){
		$j("#tbl-dispatch-list").jqGrid().trigger("reloadGrid",[{page:1}]);
		loxia.tip($j("#barCode"));
		loxia.tip($j("#express_order"));
		$j("#showList").removeClass("hidden");
		$j("#checkBill").addClass("hidden");
		$j("#refSlipCode").focus();
		//清空输入框
		$j("#express_order").val("");
		$j("#barCode0").val("");
		$j("#barCode1").val("");
		
	});
	
	//执行核对
	$j("#doCheck").click(function(){
		//先检查是否条码核对
		if(rs0 && rs0.result=='success'){
			//验证计划量和执行量
			var datax = $j("#tbl-billView").jqGrid('getRowData');
			var flag = true;
			$j.each(datax,function(i, item) {
				var n1= parseInt(item.quantity);
				var cq = item.completeQuantity;
				if(cq==''||cq==null){
					 cq='0';
				}
				var n2= parseInt(cq);
				if(n2<n1){
					flag = false
				}

			});	
			if(!flag){
				$j("#flag0").val("barCode");
				errorTip(i18("EXISTS_QUANTITY_GQ_EXECUTEQTY"));
				return ;
			}
		}
	/////////////////////////////////////////////////////////	
		
		
//		if(confirm(i18("SURE_OPERATE"))){//确定执行本次操作
			//检查数量是否正确	
			var r = false;
			var isNeedCheck = true;
			if(isNeedCheck){
				//不修改数量直接通过核对
				var isAllNull = true;
				var datax = $j("#tbl-billView").jqGrid('getRowData');
				$j.each(datax,function(i, item) {
					var cq = item.completeQuantity;
					if(!(cq==''||cq==null||cq=='0')){
						isAllNull = false;
					}
				});
				if(isAllNull){
					isNeedCheck = false;
				}
			}
			if(isNeedCheck){
				var datax = $j("#tbl-billView").jqGrid('getRowData');
				$j.each(datax,function(i, item) {
					var n1= parseInt(item.quantity);
					var cq = item.completeQuantity;
					if(cq==''||cq==null){
						 cq='0';
					}
					var n2= parseInt(cq);
					if(n2<n1){
						//jumbo.showMsg(item.skuName+i18("QUANTITY_ERROR"));//数量错误
						$j("#flag0").val("barCode");
						errorTip(item.skuName+i18("QUANTITY_ERROR"));
						r=true;
						return false;
					}
					
					//判断已执行量是否大于计划执行量，并给出提示
					if(n2>n1){
						var msg = item.skuName+" 已执行量大于计划执行量，请重新核对！";//已执行量大于计划执行量，如果继续执行核对，将直接按照计划执行量执行核对。是否继续？
						$j("#error_recheck_Text").html(msg);
						$j("#dialog_error_recheck").dialog("open");
						$j("#barCode1").val("");
						$j("#barCode_recheck").focus();
						r=true;
						return false;
//						if(!confirm(msg)){
//							r = true;
//							return false;
//						}
					}
				});
			}
			var data={};
			//检查快递单号
			var ids = $j("#tbl-express-order tr:gt(0)");
			if(ids.length==0){
				//jumbo.showMsg(i18("INPUT_TRACKINGNO"));//请输入快递单号
				$j("#flag0").val("express_order");
				errorTip(i18("INPUT_TRACKINGNO"));

				r=true;
				return false;
			}
			$j.each(ids,function(i, item) {
				 var id =  item.id;
				  data["packageInfos[" + i + "].trackingNo"] = id;
			});
			
		    var staId = $j("#staId").val();
		    data["sta.id"]=staId;
		    $j.each(datax,function(i, item) {
		    	if(!isNeedCheck){
		    		data["staLineList[" + i + "].completeQuantity"] = item.quantity;
		    	}else{
		    		//已执行量大于计划执行量的，按照计划执行量进行核对
		    		if(item.completeQuantity>item.quantity){
		    			data["staLineList[" + i + "].completeQuantity"] = item.quantity;
		    		}else{
		    			data["staLineList[" + i + "].completeQuantity"] = item.completeQuantity;
		    		}
		    	}
		        data["staLineList[" + i + "].id"] = item.id;
		    });
		    if(r) return ;
		    data["lineNo"] = lineNo;
		    var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/checkdistributionlist.json",data);
				if(rs.result=='success'){
					$j("#Back").trigger("click");
				}else {
					$j("#flag0").val("barCode1");
					if(rs.msg=="流水开票号未找到"){
						isNotLineNo='true';
					}
					errorTip(i18("OPERATE_FAILED")+"<br />"+rs.msg);
					

					//jumbo.showMsg(i18("OPERATE_FAILED")+"<br />"+rs.msg);//执行核对失败！
				}
//			}
	});
	
	loxia.byId("inQty").setEnable(false);
	
	$j("#btnInQty").click(function(){
		loxia.byId("inQty").setEnable(true);
		$j("#inQty").select(); 
	});
	
	$j("#inQty").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			if(loxia.byId("inQty").check() == false){
				$j("#inQty").select(); 
			}else{
				loxia.byId("inQty").setEnable(false);
				$j("#barCode").select(); 
			}
		}
	});
	
	
	
	$j("#barCode0").keyup(function(evt){
			expressCommon();
	});
	
	//确认按钮
	$j("#doCheck0").click(function(){
		expressCommon0();
	});
	
	
	$j("#barCode1").keyup(function(evt){
		
			var barCode1 = $j("#barCode1").val();
			if(BARCODE_CONFIRM ==barCode1){
			//执行核对
			$j('#doCheck').trigger('click');
			}
	});
});

function expressCommon(){
	var barCode1 = $j("#barCode0").val();
	if(BARCODE_CONFIRM ==barCode1){
		expressCommon0()
	}
}

function expressCommon0(){
	$j("#dialog_error").dialog("close");
	$j("#express_order").val("");
	var flag0 = $j("#flag0").val();
	if(flag0=='express_order'){
		$j("#express_order").val("");
	    $j("#express_order").focus();
	}
	else if(flag0=='barCode0'){
		$j("#barCode0").val("");
		$j("#barCode0").focus();
	}else if(flag0=='barCode1'){
		$j("#barCode1").val("");
		$j("#barCode1").focus();
	}else if(flag0='barCode'){
		$j("#barCode").val("");
		$j("#barCode").focus();
	}
	$j("#barCode1").val("");
	if(isNotLineNo == "true"){
		jumbo.removeAssemblyLineNo();
		lineNo = jumbo.getAssemblyLineNo();
	}
}

function doDelete(id){
	$j("#"+id).remove();
}

//执行光标移动
function removeCursor(){
	var datax = $j("#tbl-billView").jqGrid('getRowData');
	var flag = true;
	$j.each(datax,function(i, item) {
		var n1= parseInt(item.quantity);
		var cq = item.completeQuantity;
		if(cq==''||cq==null){
			 cq='0';
		}
		var n2= parseInt(cq);
		if(n2<n1){
			flag = false
		}
	});
	if(flag){
	 $j("#express_order").focus();
	}
}


//弹出
function errorTip(text0){
	$j("#errorText").html("<font style='text-align:center;font-size:36px' color='red'>"+text0+"</font>");
	$j("#barCode0").val("");
	$j("#dialog_error").dialog("open");
	$j("#barCode0").focus();
}
