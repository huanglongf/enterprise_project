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
var barcodeList;
function showDetail(obj){
	var row=$j(obj).parent().parent().attr("id");//plid
	$j("#plid").val(row);
	var plDill=loxia.syncXhr($j("body").attr("contextpath") + "/checkWhPlDillLineByPid.json",{"pickingListId":row});
	if(plDill && plDill["msg"] == 'DATA'){
		//有差异记录
		var data = $j("#tbl-dispatch-list").jqGrid("getRowData",row);
		$j("#barCode1").focus();
		$j("#picking-list").addClass("hidden");
		$j("#detail").addClass("hidden");
		$j("#pldill").removeClass("hidden");
		$j("#plCode1").html(data['code']);
		$j("#plCreateTime1").html(data['createTime']);
		$j("#plCreateUser1").html(data['crtUserName']);
		$j("#plBillCount1").html(data['planBillCount']);
		$j("#tbl-pldill-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/findWhPlDillLineByPid.json"),
			postData:{"pickingListId":row}}).trigger("reloadGrid",[{page:1}]);
	}if(plDill && plDill["msg"] == 'NODATA'){
		//无差异记录
		$j("#picking-list").addClass("hidden");
		$j("#detail").removeClass("hidden");
		$j("#barCode").focus();
		var data = $j("#tbl-dispatch-list").jqGrid("getRowData",row);
		$j("#plCode").html(data['code']);
		$j("#plCreateTime").html(data['createTime']);
		$j("#plCreateUser").html(data['crtUserName']);
		$j("#plBillCount").html(data['planBillCount']);
		$j("#tbl-detail-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/findStaLineByPickingIdDiek.json"),
				postData:{"pickinglistId":row}}).trigger("reloadGrid",[{page:1}]);
	}
}

$j(document).ready(function (){
	$j("#pl_check_dialog").dialog({title: "拣货核对信息", modal:true, autoOpen: false, width: 600, height: 300});
	$j("#pickinglistCodeQuery").focus();
	var baseUrl = $j("body").attr("contextpath");
	var intStatus=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"pickingListStatus"});
	
	$j("#tbl-dispatch-list").jqGrid({
		url:baseUrl + "/findPickingListDieking.json",
		datatype: "json",
		colNames: ["ID","配货批单号","状态","创建人","创建时间","计划单据数"],
		colModel: [{name: "id", index: "p.id", hidden: true},
					{name:"code", index:"p.code", width:140,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, resizable:true},
					{name:"intStatus", index:"p.status" ,width:80,resizable:true ,formatter:'select',editoptions:intStatus},
					{name:"crtUserName",index:"u.user_name",width:90,resizable:true},
					{name:"createTime",index:"p.create_time",width:150,resizable:true},
					{name:"planBillCount",index:"p.plan_bill_count",width:70,resizable:true}
				  ],
		caption: "配货批列表",
	   	sortname: 'id',
	   	multiselect: false,
		sortorder: "desc",
		pager:"#pager",
		width:585,
	   	shrinkToFit:false,
		height:"auto",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		viewrecords: true,
	   	rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	jumbo.bindTableExportBtn("tbl-dispatch-list",{"intStatus":"pickingListStatus"},baseUrl + "/findPickingListDieking.json");
	
	var intStaStatus=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"whSTAStatus"});
	$j("#tbl-detail-list").jqGrid({
		datatype: "json",
		colNames: ["ID","作业单号","状态","SKU编码","SKU条码","SKU","商品名称","数量","已核对数量","完成度","skuid"],
//		colNames: ["ID","作业单号","状态","SKU编码","SKU条码","商品名称","数量","已核对数量","箱号","完成度"],
		colModel: [{name: "id", index: "sku.id", hidden: true},
		           {name:"staCode", index:"sta.code", width:120,resizable:true},
					{name:"intStatus", index:"sta.status" ,width:60,resizable:true ,formatter:'select',editoptions:intStaStatus},
					{name:"skuCode",index:"sku.code",width:90,resizable:true},
					{name:"extCode1",index:"sku.ext_code1",width:120,resizable:true,hidden: true},
					{name:"barCode",index:"sku.bar_code",hidden: true},
					{name:"skuName",index:"sku.name",width:170,resizable:true},
					{name:"quantity",index:"l.quantity",width:50,resizable:true},
					{name:"completeQuantity",index:"completeQuantity",width:70,resizable:true},
//					{name:"pgIndex",index:"sta.pg_index",width:50,resizable:true,hidden:false},
					{name:"finish",index:"finish",width:50,resizable:true,hidden:true},
					{name:"skuId",index:"l.sku_id",hidden: true}
				  ],
		caption: "商品明细列表",
	   	sortname: 'id',
	    multiselect: false,
		sortorder: "desc",
		width:625,
	   	shrinkToFit:false,
		height:"auto",
		rowNum:-1,
		viewrecords: true,
	   	rownumbers:true,
		jsonReader: {repeatitems : false, id: "0"},
		loadComplete:function(){
			loxia.initContext($j("#tbl-detail-list"));
			var postData = {};
			$j("#tbl-detail-list tr:gt(0)>td[aria-describedby$='barCode']").each(function (i,tag){
				postData["skuBarcodes[" + i + "]"] = $j(tag).html();
			});
			barcodeList = loxia.syncXhr($j("body").attr("contextpath") + "/findskuBarcodeList.json",postData);
		}
	});
	
	$j("#tbl-pldill-list").jqGrid({
		datatype: "json",
		colNames: ["ID","SKU编码","SKU条码","SKU","商品名称","计划量","执行量","差异量","完成度"],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name:"code", index:"code", width:120,resizable:true},
					{name:"extCode1",index:"extCode1",width:120,resizable:true,hidden: true},
					{name:"barCode",index:"barCode",hidden: true},
					{name:"name",index:"name",width:230,resizable:true},
					{name:"planQty",index:"planQty",width:50,resizable:true},
					{name:"qty",index:"qty",width:50,resizable:true},
					{name:"discrepancy",index:"discrepancy",width:50,resizable:true},
//					{name:"pgIndex",index:"sta.pg_index",width:50,resizable:true,hidden:false},
					{name:"finish",index:"finish",hidden:true}
				  ],
		caption: "拣货差异明细表",
	   	sortname: 'id',
	    multiselect: false,
		sortorder: "desc",
		width:560,
	   	shrinkToFit:false,
		height:"auto",
		rowNum:-1,
		viewrecords: true,
	   	rownumbers:true,
		jsonReader: {repeatitems : false, id: "0"},
		loadComplete:function(){
			loxia.initContext($j("#tbl-pldill-list"));
			var postData = {};
			$j("#tbl-pldill-list tr:gt(0)>td[aria-describedby$='barCode']").each(function (i,tag){
				postData["skuBarcodes[" + i + "]"] = $j(tag).html();
			});
			barcodeList = loxia.syncXhr($j("body").attr("contextpath") + "/findskuBarcodeList.json",postData);
		}
	});
	jumbo.bindTableExportBtn("tbl-detail-list",{"intStatus":"whSTAStatus"},baseUrl + "/findStaLineByPickingIdDiek.json");
	
	
	
	// 确认完成
	$j("#checkok").click(function(){
		var flag = false;
		flag = checkedIsComplete();
		var postData={};
		var index=-1;
		var plid = $j("#plid").val();
		if(confirm("确认已全部完成该批次拣货工作？")){
			if(flag){
				//全部核对成功，直接修改配货清单状态
				var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/updatesicklistwhstatus.json",{"pickingListId":plid});//修改配货清单状态
				if(rs && rs["msg"] == 'SUCCESS'){
					jumbo.showMsg("拣货完成操作成功！");
					$j("#tbl-dispatch-list").jqGrid('setGridParam',{
						url:window.$j("body").attr("contextpath")+"/findPickingListDieking.json"
					}).trigger("reloadGrid");
					$j("#pickinglistCodeQuery").focus();
					$j("#pl_check_dialog").dialog("close");
					$j("#picking-list").removeClass("hidden");
					$j("#detail").addClass("hidden");
				}else{
					jumbo.showMsg("拣货完成操作失败！");
					$j("#pl_check_dialog").dialog("open");
					$j("#showinfo").html("批次[ " + $j("#plCode").html() + " ]核对完成，等待系统确认！");
					$j("#reconfirm").removeClass("hidden");
					return ;
				}
			}
			if(!flag){
				//核对有差异，记录t_wh_pl_diff_line表
				var data = $j("#tbl-detail-list").jqGrid('getRowData');
				var values = "";
				var item = null;
				for(var t in data){
					item = data[t];
					if(item.quantity==item.completeQuantity){
					}else{
						index+=1;
						postData["whPlDiffLineList[" + index + "].skuId.id"]=item.skuId;
						postData["whPlDiffLineList[" + index + "].planQty"]=parseInt(item.quantity);
						postData["whPlDiffLineList[" + index + "].qty"]=parseInt(item.completeQuantity);
					}
				}
				//插入配货清单差异表
				var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/addWhPlDiffLine.json?pickingListId="+plid,postData);
				if(rs && rs["msg"] == 'SUCCESS'){
						$j("#picking-list").addClass("hidden");
						$j("#detail").addClass("hidden");
						$j("#pldill").removeClass("hidden");
						var data = $j("#tbl-dispatch-list").jqGrid("getRowData",plid);
						$j("#plCode1").html(data['code']);
						$j("#plCreateTime1").html(data['createTime']);
						$j("#plCreateUser1").html(data['crtUserName']);
						$j("#plBillCount1").html(data['planBillCount']);
						$j("#barCode1").focus();
						$j("#tbl-pldill-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/findWhPlDillLineByPid.json"),
							postData:{"pickingListId":plid}}).trigger("reloadGrid",[{page:1}]);
				}
			}
		}
	});
	
	//差异明细确认完成
	$j("#pldillok").click(function(){
			var postData={};
			var index=-1;
			var plid = $j("#plid").val();
			if(confirm("确认已全部完成该批次拣货工作？")){
				var data = $j("#tbl-pldill-list").jqGrid('getRowData');
				var values = "";
				var item = null;
				for(var t in data){
					item = data[t];
					index+=1;
					postData["whPlDiffLineList[" + index + "].id"]=item.id;
					postData["whPlDiffLineList[" + index + "].planQty"]=parseInt(item.planQty);
					postData["whPlDiffLineList[" + index + "].qty"]=parseInt(item.qty);
				}
				var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/donePickListWhDieking.json?pickingListId="+plid,postData);
				if(rs && rs["msg"] == 'SUCCESS'){
					jumbo.showMsg("拣货完成操作成功！");
					$j("#picking-list").removeClass("hidden");
					$j("#detail").addClass("hidden");
					$j("#pldill").addClass("hidden");
					$j("#pickinglistCodeQuery").focus();
					postData["cmd.code"]= $j.trim($j("#pickinglistCode").val());
					postData["cmd.createTime1"]= $j.trim($j("#createTimeStart").val());
					postData["cmd.executedTime1"]= $j.trim($j("#createTimeEnd").val());
					$j("#tbl-dispatch-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/findPickingListDieking.json"),
						postData:postData}).trigger("reloadGrid",[]);
				}else{
					jumbo.showMsg("拣货完成操作失败！");
				}
			}
	});
	
	
	// 再次确认完成
	$j("#reconfirm").click(function(){
		if(confirm("确认已全部完成该批次拣货工作？")){
			var plid = $j("#plid").val();
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/updatesicklistwhstatus.json",{"pickingListId":plid});//修改配货清单状态
			if(rs && rs["msg"] == 'SUCCESS'){
				jumbo.showMsg("拣货完成操作成功！");
				var postData = {};
				postData["cmd.code"]= $j.trim($j("#pickinglistCode").val());
				postData["cmd.createTime1"]= $j.trim($j("#createTimeStart").val());
				postData["cmd.executedTime1"]= $j.trim($j("#createTimeEnd").val());
				$j("#tbl-dispatch-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/findPickingListDieking.json"),
					postData:postData}).trigger("reloadGrid",[]);
				$j("#pl_check_dialog").dialog("close");
				$j("#picking-list").removeClass("hidden");
				$j("#pickinglistCodeQuery").focus();
				$j("#detail").addClass("hidden");
			}else{
				jumbo.showMsg("拣货完成操作失败！");
			}
		}
	});
	
	//重新操作
	$j("#rehandle").click(function(){
		if(confirm("确认对此配货清单拣货差异进行重新操作吗？")){
			var plid = $j("#plid").val();
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/deleteWhPlDillLineByPid.json",{"pickingListId":plid});//删除配货清单差异明细
			if(rs && rs["msg"] == 'SUCCESS'){
				jumbo.showMsg("重新操作成功！");
				var data = $j("#tbl-dispatch-list").jqGrid("getRowData",plid);
				$j("#barCode").focus();
				$j("#picking-list").addClass("hidden");
				$j("#detail").removeClass("hidden");
				$j("#pldill").addClass("hidden");
				$j("#plCode1").html(data['code']);
				$j("#plCreateTime1").html(data['createTime']);
				$j("#plCreateUser1").html(data['crtUserName']);
				$j("#plBillCount1").html(data['planBillCount']);
				$j("#tbl-detail-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/findStaLineByPickingIdDiek.json"),
					postData:{"pickinglistId":data.id}}).trigger("reloadGrid",[{page:1}]);
			}else{
				jumbo.showMsg("重新操作失败！");
			}
		}
	});
	
	//差异明细返回
	$j("#pldillnext").click(function(){
		$j("#picking-list").removeClass("hidden");
		$j("#detail").addClass("hidden");
		$j("#pldill").addClass("hidden");
		$j("#barCode").val("");
		$j("#barCode1").val("");
		$j("#pickinglistCodeQuery").val("");
		$j("#pickinglistCodeQuery").focus();
	});
	
	
	$j("#barCode").keydown(function(evt){
		if(evt.keyCode == 9){
			evt.preventDefault();
		}
		var rs = false;//判断数量
		var rs1 = false;//判断SKU是否存在
		var value = $j.trim($j(this).val());
		if(evt.keyCode === 13){
			evt.preventDefault();
			rs = checkedBillCount(value);
			rs1 = checkedSku(value,0);
			if(!rs1){
//				$j("#barCode").val("");
				loxia.tip($j("#barCode"),i18("BARCODE_NOT_EXISTS"));//条形码不存在
				return;
			}
			if(!rs){
//				$j("#barCode").val("");
				loxia.tip($j("#barCode"),i18("此商品已核对完成！"));
				return;
			}
			$j("#barCode").val("");
		}
	});
	
	//差异明细SKU
	$j("#barCode1").keydown(function(evt){
		if(evt.keyCode == 9){
			evt.preventDefault();
		}
		var rs = false;//判断数量
		var rs1 = false;//判断SKU是否存在
		var value = $j.trim($j(this).val());
		if(evt.keyCode === 13){
			evt.preventDefault();
			rs = checkedBillCount1(value);
			rs1 = checkedSku(value,1);
			if(!rs1){
				loxia.tip($j("#barCode1"),i18("BARCODE_NOT_EXISTS"));//条形码不存在
				return;
			}
			if(!rs){
				loxia.tip($j("#barCode1"),i18("此商品已核对完成！"));
				return;
			}
			$j("#barCode1").val("");
		}
	});
	
	function checkedSku(value,type){
	var rs=false, flag =false;
	if(!value || value.length==0)return false;
	if(type == 0){
		var data = $j("#tbl-detail-list").jqGrid('getRowData');
	}
	if(type == 1){
		var data = $j("#tbl-pldill-list").jqGrid('getRowData');
	}
	var d = null;
	var item = null, planNum = null, completeNum = null;
	for(var t in data){
		item = data[t];
		if(item.barCode == value){
				rs = true;
				//break;
				return rs;
		}else {
				continue;
		}
	}
	for(var t in data){
		item = data[t];
		if(item.barCode == '00' + value){
				rs = true;
				//break;
				return rs;
		}else {
				continue;
		}
	}
	for(var t in data){
		item = data[t];
		var bc = item.barCode;
		for(var v in barcodeList[bc]){
			if(barcodeList[bc][v] == value || barcodeList[bc][v] == ('00'+value)){
					rs = true;
					return rs;
			}else {
					continue;
			}
		}
	}
	return rs;
} 
	
	//差异明细SKU验证
	function checkedBillCount1(value){
	var rs=false, flag =false;
	if(!value || value.length==0)return false;
	var data = $j("#tbl-pldill-list").jqGrid('getRowData');
	var d = null;
	var item = null, planNum = null, completeNum = null,discrepancyNum = null;
	for(var t in data){
		item = data[t];
		if(item.barCode == value){
			d = $j("#tbl-pldill-list").jqGrid('getRowData',item.id);
			planNum = parseInt(d.planQty);
			completeNum = parseInt(d.qty);
			discrepancyNum = parseInt(d.discrepancy);
			if (d.finish != 'y'){
				d.qty = completeNum + 1;
				d.discrepancy = discrepancyNum - 1;
				if(completeNum + 1 == planNum){
					d.finish = 'y';
				}
				$j("#tbl-pldill-list").jqGrid('setRowData',item.id,d);
				$j("#staPgindex").val(d.pgIndex);
				rs = true;
				//break;
				return rs;
			} else {
				continue;
			}
		}
	}
	for(var t in data){
		item = data[t];
		if(item.barCode == '00' + value){
			d = $j("#tbl-pldill-list").jqGrid('getRowData',item.id);
			planNum = parseInt(d.planQty);
			completeNum = parseInt(d.qty);
			discrepancyNum = parseInt(d.discrepancy);
			if (d.finish != 'y'){
				d.qty = completeNum + 1;
				d.discrepancy = discrepancyNum - 1;
				if(completeNum + 1 == planNum){
					d.finish = 'y';
				}
				$j("#tbl-pldill-list").jqGrid('setRowData',item.id,d);
				$j("#staPgindex").val(d.pgIndex);
				rs = true;
				//break;
				return rs;
			} else {
				continue;
			}
		}
	}
	for(var t in data){
		item = data[t];
		var bc = item.barCode;
		for(var v in barcodeList[bc]){
			if(barcodeList[bc][v] == value || barcodeList[bc][v] == ('00'+value)){
			d = $j("#tbl-pldill-list").jqGrid('getRowData',item.id);
			planNum = parseInt(d.planQty);
			completeNum = parseInt(d.qty);
			discrepancyNum = parseInt(d.discrepancy);
				if (d.finish != 'y'){
					d.qty = completeNum + 1;
					d.discrepancy = discrepancyNum - 1;
					if(completeNum + 1 == planNum){
						d.finish = 'y';
					}
					$j("#tbl-pldill-list").jqGrid('setRowData',item.id,d);
					$j("#staPgindex").val(d.pgIndex);
					rs = true;
					return rs;
				} else {
					continue;
				}
			}
		}
	}
	return rs;
} 
	
//	// input
//	$j("#barCode0").keydown(function(event){
//		var flag = false;
//		flag = checkedIsComplete();
//		if (flag){
//			$j("#pl_check_dialog").dialog("open");
//			$j("#showinfo").html("批次[ " + $j("#plCode").html() + " ]核对完成");
//			$j("#inputBarCode").addClass("hidden");
//			$j("#doCheck0").removeClass("hidden");
//			$j("#checkValue").removeClass("hidden");
//			$j("#checkValue").focus();
//			return ;
//		}
//		var value = $j.trim($j("#barCode0").val());
//		var rs = false;
//		if(event.keyCode == 13){
//			if(value != "") {
//				rs = checkedBillCount(value);
//				if(!rs){
//					loxia.tip(this,i18("BARCODE_NOT_EXISTS"));//条形码不存在
//					$j("#showinfo").html(i18("BARCODE_NOT_EXISTS"));
//				}else {
//					$j("#showinfo").html("箱号是 ：" + $j("#staPgindex").val());
//				}
//			}else if(value == "" ) {
//				$j("#showinfo").html("请输入商品条码.");
//			}
//			$j("#barCode0").val("");
//			loxia.tip(this);
//			//$j("#barCode0").focus();
//		}
//		flag = checkedIsComplete();
////		if (flag){
////			$j("#showinfo").html("批次[ " + $j("#plCode").html() + " ]核对完成");
////			$j("#inputBarCode").addClass("hidden");
////			$j("#doCheck0").removeClass("hidden");
////			$j("#checkValue").removeClass("hidden");
////			$j("#checkValue").focus();
////		}
//	});
	
//	// button 核对完成
//	$j("#doCheck0").click(function(){
//		checkCompleteoperate();
//	});
	// input ok
//	$j("#checkValue").keydown(function(event){
//			var value = $j("#checkValue").val();
//			if(event.keyCode == 13){
//				if (value == BARCODE_CONFIRM){
//					checkCompleteoperate();
//				}else if(value != "" ) {
//					$j("#showinfo").html("输入的确认条码不正确，请重新输入.");
//				}else if(value == "" ) {
//					$j("#showinfo").html("请输入确认条码.");
//				}
//				$j("#checkValue").val("");
//			}
//	});
	
	$j("#pickinglistCodeQuery").keydown(function(event){
		if(event.keyCode == 13){
			var postData = {};
			var code = $j.trim($j("#pickinglistCodeQuery").val());
			if(code != null && code != ""){
				postData["cmd.code"]= code;
				postData["diekNo"]= 1;
				result = loxia.syncXhr($j("body").attr("contextpath") + "/checkPinkingListByCode.json",postData);
				if(result.result=='success'){
					var data = result.data;
					$j("#plid").val(data.id);
					var plDill=loxia.syncXhr($j("body").attr("contextpath") + "/checkWhPlDillLineByPid.json",{"pickingListId":data.id});
					if(plDill && plDill["msg"] == 'DATA'){
						$j("#plCode1").html(data.code);
						$j("#plCreateTime1").html(data.createTime);
						$j("#plCreateUser1").html(data.crtUserName);
						$j("#plBillCount1").html(data.planBillCount);
						$j("#picking-list").addClass("hidden");
						$j("#detail").addClass("hidden");
						$j("#pldill").removeClass("hidden");
						$j("#tbl-pldill-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/findWhPlDillLineByPid.json"),
							postData:{"pickingListId":data.id}}).trigger("reloadGrid",[{page:1}]);
					}
					if(plDill && plDill["msg"] == 'NODATA'){
						$j("#picking-list").addClass("hidden");
						$j("#detail").removeClass("hidden");
						$j("#barCode").focus();
						$j("#plCode").html(data.code);
						$j("#plCreateTime").html(data.createTime);
						$j("#plCreateUser").html(data.crtUserName);
						$j("#plBillCount").html(data.planBillCount);
						$j("#tbl-detail-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/findStaLineByPickingIdDiek.json"),
								postData:{"pickinglistId":data.id}}).trigger("reloadGrid",[{page:1}]);		
					}
				}else{
					loxia.tip($j("#pickinglistCodeQuery"),i18("指定的配货批不正确，请重新输入"));
					$j("#pickinglistCodeQuery").val("");
				}
			}else{
				loxia.tip($j("#pickinglistCodeQuery"),i18("请输入配货批"));
			}
		}
	});

	$j("#search").click(function(){
		var postData = {};
		postData["cmd.code"]= $j.trim($j("#pickinglistCode").val());
		postData["cmd.createTime1"]= $j.trim($j("#createTimeStart").val());
		postData["cmd.executedTime1"]= $j.trim($j("#createTimeEnd").val());
		$j("#tbl-dispatch-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/findPickingListDieking.json"),
			postData:postData}).trigger("reloadGrid",[]);
	});
	$j("#reset").click(function(){
		$j("#queryTable input").val("");
	});
	$j("#back").click(function(){
		checkCompleteoperate();
	});
});
// finish 
function checkCompleteoperate(){
	$j("#barCode0").val(""); 
	$j("#barCode").val("");
	$j("#pl_check_dialog").dialog("close"); 
	$j("#detail").addClass("hidden");
	$j("#picking-list").removeClass("hidden");
	$j("#pickinglistCodeQuery").val("");
	$j("#pickinglistCodeQuery").focus();
	$j("#doCheck0").addClass("hidden");
	$j("#checkValue").addClass("hidden");
	$j("#checkValue").val("");
}
//
function checkedBillCount(value){
	var rs=false, flag =false;
	if(!value || value.length==0)return false;
	var data = $j("#tbl-detail-list").jqGrid('getRowData');
	var d = null;
	var item = null, planNum = null, completeNum = null;
	for(var t in data){
		item = data[t];
		if(item.barCode == value){
			d = $j("#tbl-detail-list").jqGrid('getRowData',item.id);
			planNum = parseInt(d.quantity);
			completeNum = parseInt(d.completeQuantity);
			if (d.finish != 'y'){
				d.completeQuantity = completeNum + 1;
				if(completeNum + 1 == planNum){
					d.finish = 'y';
				}
				$j("#tbl-detail-list").jqGrid('setRowData',item.id,d);
				$j("#staPgindex").val(d.pgIndex);
				rs = true;
				//break;
				return rs;
			} else {
				continue;
			}
		}
	}
	for(var t in data){
		item = data[t];
		if(item.barCode == '00' + value){
			d = $j("#tbl-detail-list").jqGrid('getRowData',item.id);
			planNum = parseInt(d.quantity);
			completeNum = parseInt(d.completeQuantity);
			if (d.finish != 'y'){
				d.completeQuantity = completeNum + 1;
				if(completeNum + 1 == planNum){
					d.finish = 'y';
				}
				$j("#tbl-detail-list").jqGrid('setRowData',item.id,d);
				$j("#staPgindex").val(d.pgIndex);
				rs = true;
				//break;
				return rs;
			} else {
				continue;
			}
		}
	}
	for(var t in data){
		item = data[t];
		var bc = item.barCode;
		for(var v in barcodeList[bc]){
			if(barcodeList[bc][v] == value || barcodeList[bc][v] == ('00'+value)){
				d = $j("#tbl-detail-list").jqGrid('getRowData',item.id);
				planNum = parseInt(d.quantity);
				completeNum = parseInt(d.completeQuantity);
				if (d.finish != 'y'){
					d.completeQuantity = completeNum + 1;
					if(completeNum + 1 == planNum){
						d.finish = 'y';
					}
					$j("#tbl-detail-list").jqGrid('setRowData',item.id,d);
					$j("#staPgindex").val(d.pgIndex);
					rs = true;
					return rs;
				} else {
					continue;
				}
			}
		}
	}
	return rs;
} 
// true  - 完成；  false-未完成
function checkedIsComplete(){
	var datax = $j("#tbl-detail-list").jqGrid('getRowData');
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
	return flag;
} 
//弹出
function showDialog(text0,showid){
	if (showid != null) $j(showid).html(text0);
	$j("#barCode0").val("");
	$j("#inputBarCode").removeClass("hidden");
	$j("#pl_check_dialog").dialog("open");
	$j("#barCode0").focus();
}
// info 
function showInfo(text0,showid){
	if (showid != null) $j(showid).html(text0);	
	$j("#barCode0").val("");
	$j("#pl_check_dialog").dialog("open");
	$j("#barCode0").focus();
} 

