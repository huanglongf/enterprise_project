var ERROR = "异常";
var barcodeList;
var ERROR_CANCEL = "取消";
$j.extend(loxia.regional['zh-CN'], {
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
	INPUT_TRACKINGNO : "请输入快递单号",
	OPERATE_FAILED : "执行核对失败！"
});

function i18(msg, args) {
	return loxia.getLocaleMsg(msg, args);
}

function getFmtValue(tableId, rowId, column) {
	return $j("#" + tableId + " tr[id=" + rowId + "] td[aria-describedby='"+ tableId + "_" + column + "']").attr("title")
}

var fileName = "";

function showDetail(obj) {
	var id=$j(obj).parent().parent().attr("id");
	var data = $j("#tbl-dispatch-list").jqGrid("getRowData",id);
	$j("#verifyCode").html(data['code']);
	$j("#verifyPlId").html(data['id']);
	$j("#creatTime").html(data['createTime']);
	$j("#hdTime").html(data['checkedTime']);
	$j("#staCodeValues").val("");
	if (data['intStatus'] == '2') {
		$j("#pStatus").html("配货中");
	}
	if (data['intStatus'] == '8') {
		$j("#pStatus").html("部分完成");
	}
	$j("#planSkuQty").html(data['planSkuQty']);
	$j("#checkedSkuQty").html(data['checkedSkuQty']);
	$j("#plDiv").addClass("hidden");
	$j("#checkDiv").removeClass("hidden");
	$j("#quickSta").val("");
	$j("#oldCard").val("");
	$j("#newCard").val("");
	$j("#newCard").blur();
	$j("#oldCard").focus();
	var postData = {};
	postData['plCode'] = data['code'];
	postData['staCode'] = "";
	$j("#tbl-sta-list").jqGrid('setGridParam',{	url : loxia.getTimeUrl($j("body").attr("contextpath")+ "/findSnCardErrorList.json"),
		postData:postData
	}).trigger("reloadGrid");
}

function errorTipOK(text) {
	$j("#error_text_ok").html("<font style='text-align:center;font-size:36px' color='red'>"+ text + "</font>");
	$j("#dialog_error_ok").dialog("open");
}


var stastatus = loxia.syncXhr($j("body").attr("contextpath")+ "/json/formateroptions.json", {"categoryCode" : "whSTAStatus"});
var statype = loxia.syncXhr($j("body").attr("contextpath")+ "/json/formateroptions.json", {"categoryCode" : "whSTAType"});
var trasportName = loxia.syncXhr($j("body").attr("contextpath")+ "/json/getTrasportName.json");

var lineNo;
var isLineNoError = "";
var userName = "";
var dateValue = "";
var ouName = "";
//条形码选中的行
var lineStal;
var fileUrl = "";
$j(document).ready(function() {
				$j("#quickSta").focus();
				$j("#whName").html(window.parent.$j("#orgname").html());

				$j("#dialog_error_ok").dialog( {
					title : "错误信息",
					modal : true,
					autoOpen : false,
					width : 400
				});
				$j("#dialog_msg_singel").dialog({
					title : "提示信息",
					modal : true,
					autoOpen : false,
					width : 1000,
					position:"left",
					closeOnEscape : false,
					open : function(event, ui) {	$j(".ui-dialog-titlebar-close").hide();	}
				});
				$j("#dialog_msg_dis").dialog({
					title : "提示信息",
					modal : true,
					autoOpen : false,
					width : 1000,
					position:"left",
					closeOnEscape : false,
					open : function(event, ui) {	$j(".ui-dialog-titlebar-close").hide();	}
				});

				var plstatus = loxia.syncXhr($j("body").attr("contextpath")+ "/formateroptions.json", {	"categoryCode" : "pickingListStatus"
				});
				if (!plstatus.exception) {
					$j("#tbl-dispatch-list").jqGrid(
									{
										url : $j("body").attr("contextpath")+ "/searchSnCardCheckList.json",	postData : loxia._ajaxFormToObj("plForm"),
										datatype : "json",//"已发货单据数","已发货商品件数",
										//colNames: ["ID","配货批次号","状态","计划配货单据数","已核对单据数","已发货单据数","计划配货商品件数","已核对商品件数","已发货商品件数","最后核对时间","最后发货时间","开始配货时间"],
										colNames : ["ID", i18("CODE"),i18("INTSTATUS"),i18("PLANBILLCOUNT"),i18("CHECKEDBILLCOUNT"),i18("SHIPSTACOUNT"),i18("PLANSKUQTY"),i18("CHECKEDSKUQTY"),i18("SHIPSKUQTY"),i18("CREATE_TIME"),i18("CHECKEDTIME"),	i18("EXECUTEDTIME"),	i18("PICKINGTIME"),''],
										colModel : [
												{name : "id",index : "pl.id",hidden : true}, 
												{name : "code",index : "pl.code",formatter : "linkFmatter",formatoptions : {onclick : "showDetail"},width : 100,resizable : true},
												{name : "intStatus",	index : "pl.status",width : 40,resizable : true,	formatter : 'select',editoptions : plstatus	},
												{name : "planBillCount",index : "pl.plan_bill_count",width : 100, resizable : true},
												{name : "checkedBillCount",index : "pl.checked_bill_count",width : 90,	resizable : true},
												{name : "shipStaCount",	index : "pl.send_bill_count",width : 90,resizable : true,hidden : true	},
												{name : "planSkuQty",	index : "pl.plan_sku_qty",width : 120,	resizable : true},
												{name : "checkedSkuQty",	index : "pl.checked_sku_qty",width : 100,resizable : true},
												{name : "shipSkuQty",	index : "pl.send_sku_count",width : 100,resizable : true,hidden : true}, 
												{name : "createTime",	index : "pl.CREATE_TIME",	width : 150,resizable : true	},
												{name : "checkedTime",index : "pl.CHECK_TIME",width : 150,	resizable : true},
												{name : "executedTime",	index : "pl.last_outbound_time",width : 150,resizable : true,hidden : true},
												{name : "pickingTime",index : "pl.PICKING_TIME",	width : 150,resizable : true	},
												{name : "isBkCheckInteger",index : "isBkCheckInteger",hidden : true}
										],
										caption : i18("PICKING_LIST"),//配货清单列表
										sortname : 'pl.CREATE_TIME',
										multiselect : false,
										sortorder : "desc",
										pager : "#pager",
										width : "auto",
										height : "auto",
										rowNum : jumbo.getPageSize(),
										rowList : jumbo.getPageSizeList(),
										height : jumbo.getHeight(),
										viewrecords : true,
										rownumbers : true,
										jsonReader : {repeatitems : false,id : "0"}
									});
				} else {
					jumbo.showMsg(i18("INIT_SYSTEM_DATA_EXCEPTION"));//初始化系统参数异常
				}

				$j("#search").click(
						function() {	$j("#tbl-dispatch-list").jqGrid('setGridParam',	{url : loxia.getTimeUrl($j("body").attr("contextpath")	+ "/json/searchSnCardCheckList.json"),postData : loxia._ajaxFormToObj("plForm")}).trigger("reloadGrid",
											[{page : 1	}]);
				});

				$j("#reset").click(function() {
					document.getElementById("plForm").reset();
				});
				
				$j("#btnTryError").click(function() {
					printSnCardErrorList();
				});

				$j("#tbl-sta-list").jqGrid(
								{
									datatype : "json",
									colNames : [ "ID","staid","skuid","barCode","customerid","作业单号","相关单据号","商品编码","卡券号","商品名称","操作状态"],
									colModel : [
									            {name : "snid",index : "snid",hidden : true,	sortable : false},
									            {name : "staid",index : "staid",	hidden : true,sortable : false},
									            {name : "skuid",index : "skuid",hidden : true,	sortable : false},
									            {name : "barCode",index : "barCode",hidden : true,	sortable : false},
									            {name : "customerid",index : "customerid",hidden : true,	sortable : false},
									            {name : "code",index : "code",width : 130,	resizable : true,sortable : false},
									     		{name : "slipCode",index : "slipCode",width : 130,	resizable : true,sortable : false},
									     		{name : "skuCode",index : "skuCode",width : 130,	resizable : true,sortable : false},
									     		{name : "sn",index : "sn",width : 160,	resizable : true,sortable : false},
									     		{name : "skuName",index : "skuName",width : 260,	resizable : true,sortable : false},
									     		{name : "cardStatus",index : "cardStatus",width : 80,	resizable : true,sortable : false}
											],
									caption : "激活失败卡(券)号列表",
									multiselect : false,
									width : "auto",
									height : "auto",
									rowNum : -1,
									viewrecords : true,
									jsonReader : {repeatitems : false,	id : "0"},
								});
	

				//返回
				$j("#idBack").click(function() {
					$j("#checkDiv").addClass("hidden");
					$j("#plDiv").removeClass("hidden");
					$j("#quickSta").focus();
				});
				
				$j("#close").click(function() {
					$j("#dialog_msg_singel").dialog("close");
				});
				
				$j("#disclose").click(function() {
					$j("#dialog_msg_dis").dialog("close");
				});
				
				$j("#closedialog").click(function() {
					$j("#dialog_error_ok").dialog("close");
				});
				
				$j("#printExpressBill").click(function() {
					postPrintExpressBill("1");
				});
				
				$j("#disprintExpressBill").click(function() {
					postPrintExpressBill("2");
				});
				
				//通过作业单号查找对应信息
				$j("#quickSta").keydown(function(evt) {
					if (evt.keyCode === 13) {
						var staCode = $j("#quickSta").val().trim();
						if (staCode == "") {
							jumbo.showMsg("输入单据号码不能为空!");
							return;
						}
						var sta = loxia.syncXhrPost($j("body").attr("contextpath")+ "/findSnCardErrorByStaCode.json", {"staCode" : staCode});
						if(sta && sta['sta']){
							var p = loxia.syncXhrPost($j("body").attr("contextpath")+ "/getSnCardErrorPl.json", {"plid" : sta['sta']['pickingListId']});
								if(p && p['p']){
									$j("#verifyCode").html(p['p']['code']);
									$j("#verifyPlId").html(p['p']['id']);
									$j("#creatTime").html(p['p']['createTime']);
									$j("#hdTime").html(p['p']['checkedTime']);
									$j("#staCodeValues").val(staCode);
									if (p['p']['intStatus'] == '2') {
										$j("#pStatus").html("配货中");
									}
									if (p['p']['intStatus'] == '8') {
										$j("#pStatus").html("部分完成");
									}
									$j("#planSkuQty").html(p['p']['planSkuQty']);
									$j("#checkedSkuQty").html(p['p']['checkedSkuQty']);
									var postData = {};
									postData['staCode'] = staCode;
									postData['plCode'] = "";
									$j("#plDiv").addClass("hidden");
									$j("#checkDiv").removeClass("hidden");
									$j("#quickSta").val("");
									$j("#oldCard").val("");
									$j("#newCard").val("");
									$j("#oldCard").focus();
									$j("#tbl-sta-list").jqGrid('setGridParam',{	url : loxia.getTimeUrl($j("body").attr("contextpath")+ "/findSnCardErrorList.json"),
										postData:postData
									}).trigger("reloadGrid");
								}					
							}else{
								jumbo.showMsg("未找到对应作业单！");
								return;
						}	
					}
				});
				
				//查找对应卡号
				$j("#oldCard").keydown(function(evt) {
					if (evt.keyCode === 13) {
						var oldCard = $j("#oldCard").val().trim();
						if (oldCard == "") {
							jumbo.showMsg("激活失败卡号不能为空!");
							return;
						}
						var bc = oldCard.split('=');
						if(bc.length > 0){
							oldCard = bc[0];
							$j("#oldCard").val(bc[0]);
						}
						var sn = "";
						var checkSn = false;
						var checkCS = false;
						
						//循环错误卡列表查找是否存在对应激活失败卡
						var ids = $j("#tbl-sta-list").jqGrid('getDataIDs');
						for(var i=0;i < ids.length;i++){
							var datas = $j("#tbl-sta-list").jqGrid('getRowData',ids[i]);
							var sn = $j("#tbl-sta-list tr[id="+datas["snid"]+"]  td[aria-describedby$='sn']").html();
							if(sn == oldCard){
								var checkSn = true;
								$j("#tbl-sta-list tr[id="+datas["snid"]+"]  td[aria-describedby$='cardStatus']").html("已找到");
							}
						}
						if(checkSn){
							for(var i=0;i < ids.length;i++){
								var datas = $j("#tbl-sta-list").jqGrid('getRowData',ids[i]);
								var cardStatus = $j("#tbl-sta-list tr[id="+datas["snid"]+"]  td[aria-describedby$='cardStatus']").html();
								if(cardStatus == "激活失败"){
									checkCS = true;
								}
							}
							if(checkCS){
								$j("#oldCard").val("");
								$j("#newCard").val("");
								$j("#oldCard").focus();
							}else{
								$j("#oldCard").val("");
								$j("#oldCard").blur();
								$j("#newCard").val("");
								$j("#newCard").focus();
							}
						}else{
							errorTipOK(oldCard + " 不在激活失败卡号列表中");
							$j("#oldCard").val("");
							$j("#oldCard").focus();
							return;	
						}
					}
				});
				
				//替换激活失败卡号
				$j("#newCard").keydown(function(evt) {
					if (evt.keyCode === 13) {
						var newCard = $j("#newCard").val().trim();
						if (newCard == "") {
							jumbo.showMsg("卡券号不能为空!");
							return;
						}
						var bc = newCard.split('=');
						if(bc.length > 0){
							newCard = bc[0];
							$j("#newCard").val(bc[0]);
						}
						var ids = $j("#tbl-sta-list").jqGrid('getDataIDs');
						var result = "";
						//验证卡号是否存在SN表
						result = loxia.syncXhr($j("body").attr("contextpath")+ "/findBarcodeBySn.json", {"sn" : newCard});
						var staid = "";
						var oldsn = "";
						var snid = "";
						var skuid = "";
						if(result && result.result == "error") {
							//不存在 根据规则验证卡号
							var postData = {};
							var isCheck = false;
							for(var i=0;i < ids.length;i++){
								var datas = $j("#tbl-sta-list").jqGrid('getRowData',ids[i]);
								var cs = $j("#tbl-sta-list tr[id="+datas["snid"]+"]  td[aria-describedby$='cardStatus']").html();
								if(cs == "已找到"){
									var bcode = $j("#tbl-sta-list tr[id="+datas["snid"]+"]  td[aria-describedby$='barCode']").html();
									var cid = $j("#tbl-sta-list tr[id="+datas["snid"]+"]  td[aria-describedby$='customerid']").html();
									postData["barCodeList[" + i + "]"] = bcode+","+cid;
									isCheck = true;
								}
							}
							if(isCheck){
								postData['sn'] = newCard;
								var bar = loxia.syncXhr($j("body").attr("contextpath")+ "/formatCardToSn.json", postData);
								if (bar && bar.result == "success") {
									if(bar.rs == "snok"){
										result = loxia.syncXhr($j("body").attr("contextpath")+ "/findBarcodeBySn.json", {"sn" : newCard});
									}else{
										//卡号不正确
										$j("#newCard").val("");
										errorTipOK(bar.msg);
										return;
									}
								}else{
									jumbo.showMsg("系统异常");
									return;
								}
							}else{
								$j("#newCard").val("");
								errorTipOK("激活失败卡号列表中不存在【已找到】状态卡券");
								return;	
							}
						}
						if (result && result.result == "success") {
							//卡号正确 查找列表中已找到的卡号
							var bcheck = false;
							for(var i=0;i < ids.length;i++){
								var datas = $j("#tbl-sta-list").jqGrid('getRowData',ids[i]);
								var bcode = $j("#tbl-sta-list tr[id="+datas["snid"]+"]  td[aria-describedby$='barCode']").html();
								var cs = $j("#tbl-sta-list tr[id="+datas["snid"]+"]  td[aria-describedby$='cardStatus']").html();
								if(cs == "已找到"){
									if(result.barcode == bcode){
										snid = $j("#tbl-sta-list tr[id="+datas["snid"]+"]  td[aria-describedby$='snid']").html();
										oldsn = $j("#tbl-sta-list tr[id="+datas["snid"]+"]  td[aria-describedby$='sn']").html();
										staid = $j("#tbl-sta-list tr[id="+datas["snid"]+"]  td[aria-describedby$='staid']").html();
										bcheck = true;
										break;
									}
								}
							}
							if(bcheck){
								var postData = {};
								postData['sn'] = newCard;
								var rs = loxia.syncXhr($j("body").attr("contextpath")+ "/checkSnStatus.json",postData);
								//验证卡号状态
								if (rs && rs.result && rs.result == "success") {
									//判断此卡号是否在列表中有对应的STA
									var rs1 = loxia.syncXhr($j("body").attr("contextpath")+ "/getStaIdBySnStv.json",postData);
									if (rs1 && rs1.result && rs1.result == "success") {
										if(rs1.staid == 'null'){
											//对应作业单绑定新的SN号
											postData['snid'] = snid;
											postData['staid'] = staid;
											var bd = loxia.syncXhr($j("body").attr("contextpath")+ "/shiftCardBindingStv.json",postData);
											if (bd && bd.result && bd.result == "success") {
												//激活卡号
												postData['sta.id'] = staid;
												var kjh = loxia.syncXhr($j("body").attr("contextpath")+ "/activateCardStatus.json",postData);
												if (kjh && kjh.result && kjh.result == "success") {
													if(kjh.msg == 'ok'){
														postData['staid'] = staid;
														var sta = loxia.syncXhr($j("body").attr("contextpath")+ "/findSnCardCheckSta.json",postData);
														if (sta && sta.result && sta.result == "success") {
															if(sta['sta']['pickType'] == '1'){
																//单件作业单 直接弹出核对
																playMusic($j("body").attr("contextpath") +"/recording/"+sta['sta']["pgIndex"]+".wav");
																$j("#snid").val(snid);
																$j("#tbSlipCode").html(sta['sta']["staSlipCode"]);
																$j("#tbStaCode").html(sta['sta']["staCode"]);
																$j("#tbPgIndex").html(sta['sta']["pgIndex"]);
																$j("#recommandSku").html(sta['sta']["packageBarCode"]);
																$j("#isPostpositionPackingPage").val(sta['sta']['isPostpositionPackingPage']);
																$j("#isPostpositionExpressBill").val(sta['sta']['isPostpositionExpressBill']);
																$j("#hidStaId").val(sta['sta']["id"]);
																$j("#plId").val(sta['sta']["plid"]);
																$j("#hidSn").val(newCard);
																$j("#txtTkNo").val("");
																$j("#txtTkNo").attr("sf",sta['sta']["trackingNo1"]);
																$j("#dialog_msg_singel").dialog("open");
																$j("#idSlipCode").val("");
																$j("#idSlipCode").focus();
																var isPostpositionPackingPage = sta['sta']['isPostpositionPackingPage'];
																var isPostpositionExpressBill = sta['sta']['isPostpositionExpressBill'];
																if('1' == isPostpositionPackingPage){
																	$j("#idSlipCode").val(sta['sta']["staSlipCode"]);
																	$j("#txtTkNo").focus();
																}
																if('1' == isPostpositionExpressBill){
																	$j("#txtTkNo").val(sta['sta']["trackingNo1"]);
																}
																if('1' == isPostpositionPackingPage && '1' == isPostpositionExpressBill){
																	$j("#confirmBarCode").focus();
																}
																if('0' == isPostpositionPackingPage && '0' == isPostpositionExpressBill){
																	$j("#idSlipCode").focus();
																}
															}else{
																postData['staCode'] = sta['sta']["staCode"];
																postData['plCode'] = "";
																var errorok = loxia.syncXhr($j("body").attr("contextpath")+ "/checkSnCardErrorList.json",postData);
																if(errorok && errorok.result && errorok.result == "success"){
																	if(errorok.rs == "ok"){
																		//作业单还有失败的卡 不弹核对页面 删除对应条目
																		$j("#tbl-sta-list tr[id="+snid+"]").remove();
																		$j("#newCard").val("");
																		$j("#newCard").focus();
																		return;
																	}
																}else{
																	jumbo.showMsg("系统异常");
																	return;
																}
																$j("#dissnid").val(snid);
																$j("#dishidStaId").val(sta['sta']["id"]);
																$j("#tbl-trank-list tr:gt(0)").remove();
																$j("#distbSlipCode").html(sta['sta']["staSlipCode"]);
																$j("#distbStaCode").html(sta['sta']["staCode"]);
																$j("#dishidStaId").val(sta['sta']["id"]);
																$j("#displId").val(sta['sta']["plid"]);
																$j("#txtTk").html(sta['sta']["trackingNo1"]);
																var info = loxia.syncXhr($j("body").attr("contextpath")+ "/findPackAgeInfoForSta.json",postData);
																if(info && info.result && info.result == "success"){
																	if(info.info.length > 0){
																		for(var i = 0;i<info.info.length;i++){
																			var str = "<tr id=\""+info['info'][i]['trackingNo']+"\"><td class='label' height='23px' style='text-align: center;font-size: 23px' >"+info['info'][i]['trackingNo']+"</td><td>" +
																			"<button style='width: 200px;height: 26px;font-size=26px' onclick=\"printTrank('"+sta['sta']["id"]+"','"+info['info'][i]['id']+"')\">"+"打印面单"+"</button></td></tr>";
																			$j("#tbl-trank-list").append(str);		
																		}
																	}
																}else{
																	jumbo.showMsg("系统异常");
																	return;
																}
																$j("#dialog_msg_dis").dialog("open");
																$j("#distxtTkNo").val("");
																$j("#distxtTkNo").focus();
															}
														}else{
															jumbo.showMsg("系统异常");
															return;
														}
													}else{
														jumbo.showMsg("卡激活失败！");
														var ksb = loxia.syncXhr($j("body").attr("contextpath")+ "/findBarcodeBySn.json", {"sn" : newCard});
														if (ksb && ksb.result == "success") {
															$j("#tbl-sta-list tr[id="+snid+"]  td[aria-describedby$='cardStatus']").html("激活失败");
															$j("#tbl-sta-list tr[id="+snid+"]  td[aria-describedby$='sn']").attr("title",newCard);
															$j("#tbl-sta-list tr[id="+snid+"]  td[aria-describedby$='sn']").html(newCard);
															$j("#tbl-sta-list tr[id="+snid+"]  td[aria-describedby$='snid']").attr("title",ksb.id);
															$j("#tbl-sta-list tr[id="+snid+"]  td[aria-describedby$='snid']").html(ksb.id);
															$j("#tbl-sta-list tr[id="+snid+"]").attr("id",ksb.id);
															$j("#newCard").val("");
															$j("#newCard").focus();
															return;															
														}else{
															jumbo.showMsg("系统异常");
															return;
														}
													}
												}else{
													jumbo.showMsg("系统异常");
													return;
												}
											}else{
												jumbo.showMsg("系统异常");
												return;
											}
										}else{
											var stacheck = false;
											for(var i=0;i < ids.length;i++){
												var datas = $j("#tbl-sta-list").jqGrid('getRowData',ids[i]);
												var bcode = $j("#tbl-sta-list tr[id="+datas["snid"]+"]  td[aria-describedby$='barCode']").html();
												var cs = $j("#tbl-sta-list tr[id="+datas["snid"]+"]  td[aria-describedby$='cardStatus']").html();
												if(cs == "已找到"){
													staid = $j("#tbl-sta-list tr[id="+datas["snid"]+"]  td[aria-describedby$='staid']").html();
													if(rs1.staid  == staid){
														snid = $j("#tbl-sta-list tr[id="+datas["snid"]+"]  td[aria-describedby$='snid']").html();
														oldsn = $j("#tbl-sta-list tr[id="+datas["snid"]+"]  td[aria-describedby$='sn']").html();
														stacheck = true;
														break;
													}
												}
											}
											if(stacheck){
													//激活卡号
													postData['sta.id'] = staid;
													var kjh = loxia.syncXhr($j("body").attr("contextpath")+ "/activateCardStatus.json",postData);
													if (kjh && kjh.result && kjh.result == "success") {
														if(kjh.msg == 'ok'){
															postData['staid'] = staid;
															var sta = loxia.syncXhr($j("body").attr("contextpath")+ "/findSnCardCheckSta.json",postData);
															if (sta && sta.result && sta.result == "success") {
																if(sta['sta']['pickType'] == '1'){
																	//单件作业单 直接弹出核对
																	playMusic($j("body").attr("contextpath") +"/recording/"+sta['sta']["pgIndex"]+".wav");
																	$j("#snid").val(snid);
																	$j("#tbSlipCode").html(sta['sta']["staSlipCode"]);
																	$j("#tbStaCode").html(sta['sta']["staCode"]);
																	$j("#tbPgIndex").html(sta['sta']["pgIndex"]);
																	$j("#recommandSku").html(sta['sta']["packageBarCode"]);
																	$j("#isPostpositionPackingPage").val(sta['sta']['isPostpositionPackingPage']);
																	$j("#isPostpositionExpressBill").val(sta['sta']['isPostpositionExpressBill']);
																	$j("#hidStaId").val(sta['sta']["id"]);
																	$j("#plId").val(sta['sta']["plid"]);
																	$j("#txtTkNo").val("");
																	$j("#hidSn").val(newCard);
																	$j("#txtTkNo").attr("sf",sta['sta']["trackingNo1"]);
																	$j("#dialog_msg_singel").dialog("open");
																	$j("#idSlipCode").val("");
																	$j("#idSlipCode").focus();
																	var isPostpositionPackingPage = sta['sta']['isPostpositionPackingPage'];
																	var isPostpositionExpressBill = sta['sta']['isPostpositionExpressBill'];
																	if('1' == isPostpositionPackingPage){
																		$j("#idSlipCode").val(sta['sta']["staSlipCode"]);
																		$j("#txtTkNo").focus();
																	}
																	if('1' == isPostpositionExpressBill){
																		$j("#txtTkNo").val(sta['sta']["trackingNo1"]);
																	}
																	if('1' == isPostpositionPackingPage && '1' == isPostpositionExpressBill){
																		$j("#confirmBarCode").focus();
																	}
																	if('0' == isPostpositionPackingPage && '0' == isPostpositionExpressBill){
																		$j("#idSlipCode").focus();
																	}
																}else{
																	postData['staCode'] = sta['sta']["staCode"];
																	postData['plCode'] = "";
																	var errorok = loxia.syncXhr($j("body").attr("contextpath")+ "/checkSnCardErrorList.json",postData);
																	if(errorok && errorok.result && errorok.result == "success"){
																		if(errorok.rs == "ok"){
																			//作业单还有失败的卡 不弹核对页面 删除对应条目
																			$j("#tbl-sta-list tr[id="+snid+"]").remove();
																			$j("#newCard").val("");
																			$j("#newCard").focus();
																			return;
																		}
																	}else{
																		jumbo.showMsg("系统异常");
																		return;
																	}
																	$j("#dissnid").val(snid);
																	$j("#dishidStaId").val(sta['sta']["id"]);
																	$j("#tbl-trank-list tr:gt(0)").remove();
																	$j("#distbSlipCode").html(sta['sta']["staSlipCode"]);
																	$j("#distbStaCode").html(sta['sta']["staCode"]);
																	$j("#dishidStaId").val(sta['sta']["id"]);
																	$j("#displId").val(sta['sta']["plid"]);
																	$j("#txtTk").html(sta['sta']["trackingNo1"]);
																	var info = loxia.syncXhr($j("body").attr("contextpath")+ "/findPackAgeInfoForSta.json",postData);
																	if(info && info.result && info.result == "success"){
																		if(info.info.length > 0){
																			for(var i = 0;i<info.info.length;i++){
																				var str = "<tr id=\""+info['info'][i]['trackingNo']+"\"><td class='label' height='23px' style='text-align: center;font-size: 23px' >"+info['info'][i]['trackingNo']+"</td><td>" +
																				"<button style='width: 200px;height: 26px;font-size=26px' onclick=\"printTrank('"+sta['sta']["id"]+"','"+info['info'][i]['id']+"')\">"+"打印面单"+"</button></td></tr>";
																				$j("#tbl-trank-list").append(str);			
																			}
																		}
																	}else{
																		jumbo.showMsg("系统异常");
																		return;
																	}
																	$j("#dialog_msg_dis").dialog("open");
																	$j("#distxtTkNo").val("");
																	$j("#distxtTkNo").focus();
																}
														}else{
															jumbo.showMsg("卡激活失败！");
															var ksb = loxia.syncXhr($j("body").attr("contextpath")+ "/findBarcodeBySn.json", {"sn" : newCard});
															if (ksb && ksb.result == "success") {
																$j("#tbl-sta-list tr[id="+snid+"]  td[aria-describedby$='cardStatus']").html("激活失败");
																$j("#tbl-sta-list tr[id="+snid+"]  td[aria-describedby$='sn']").attr("title",newCard);
																$j("#tbl-sta-list tr[id="+snid+"]  td[aria-describedby$='sn']").html(newCard);
																$j("#tbl-sta-list tr[id="+snid+"]  td[aria-describedby$='snid']").attr("title",ksb.id);
																$j("#tbl-sta-list tr[id="+snid+"]  td[aria-describedby$='snid']").html(ksb.id);
																$j("#tbl-sta-list tr[id="+snid+"]").attr("id",ksb.id);
																$j("#newCard").val("");
																$j("#newCard").focus();
																return;															
															}else{
																jumbo.showMsg("系统异常");
																return;
															}
														}
													}else{
														jumbo.showMsg("系统异常");
														return;
													}
											}else{
												//其他批次绑定了此卡
												errorTipOK("【"+newCard+"】" + " 卡片已被作业单：【"+rs1.stacode+"】 绑定使用！");
												$j("#newCard").val("");
												return;	
											}
										}
										}
									}else{
										jumbo.showMsg("系统异常");
										return;
									}
								}else{
									errorTipOK(rs.msg);
									$j("#newCard").val("");
									return;
								}
							}else{
								errorTipOK("激活失败卡号列表中不存在【已找到】状态卡券");
								$j("#newCard").val("");
								$j("#newCard").blur();
								$j("#oldCard").focus();
								return;	
							}
						}else{
							jumbo.showMsg("系统异常");
							return;
						}
					}
				});
				
				//执行核对(单件)
				$j("#btnConfirm").click(function() {
							//作业单号、相关单据号 fanht
								$j("#table01 tr").length;
								if ($j("#idSlipCode").val().trim() == "") {
									$j("#tknoErrorMsg").html("请输入相关单据号/作业单号 ");
									$j("#idSlipCode").focus();
									return;
								}
								//快递单号 fanht
								if ($j("#txtTkNo").val().trim() == "") {
									$j("#tknoErrorMsg").html("请输入快递单号");
									$j("#txtTkNo").focus();
									return;
								} else {
									var tkNo = $j("#txtTkNo").val().trim();
									var sftkno = $j("#txtTkNo").attr("sf");
									if (sftkno != "" && sftkno != tkNo && sftkno != "null") {
										$j("#txtTkNo").focus();
										$j("#txtTkNo").val("");
										$j("#tknoErrorMsg").html("非正确的快递单号!");
										return;
									}
								}
//								var pNo = $j("#verifyCode").html();
								//作业单检查
								var slipCode = $j("#idSlipCode").val();
								var snid = $j("#snid").val();
								if (slipCode != $j("#tbSlipCode").html()	&& slipCode != $j("#tbStaCode").html()) {
									$j("#idSlipCode").focus();
									$j("#idSlipCode").val("");
									$j("#tknoErrorMsg").html("作业单不正确");
									return;
								}
								var postData = getCheckPostData();
								if (postData != null) {
									//核对成功
									var rs = loxia.syncXhr($j("body").attr("contextpath")+ "/checkSingleCheckStaList.json",postData);
									if (rs && rs.result && rs.result == "success") {
										var isPostpositionPackingPage = $j("#isPostpositionPackingPage").val();
										var isPostpositionExpressBill = $j("#isPostpositionExpressBill").val();
										if('1' == isPostpositionPackingPage){
											postPrintPackingPage();
										}
										if('1' == isPostpositionExpressBill){
											postPrintExpressBill();
										}
										$j("#dialog_msg_singel").dialog("close");
										$j("#confirmBarCode").val("");
										$j("#tbl-sta-list tr[id="+snid+"]").remove();
										var ids = $j("#tbl-sta-list").jqGrid('getRowData');
										if(ids.length == 0){
											//没有数据 回到最初页面
											$j("#plDiv").removeClass("hidden");
											$j("#checkDiv").addClass("hidden");
											$j("#search").trigger("click");
										}else{
											$j("#newCard").val("");
											$j("#newCard").focus();
										}
									} else {
										jumbo.showMsg("核对异常");
									}
								}
							});
				
				//执行核对(多件)
				$j("#disbtnConfirm").click(function() {
					//快递单号 fanht
					var staid = $j("#dishidStaId").val();
					var snid = $j("#dissnid").val();
					if ($j("#distxtTkNo").val().trim() == "") {
						$j("#distknoErrorMsg").html("请输入快递单号");
						$j("#distxtTkNo").focus();
						return;
					} else {
						var tkNo = $j("#distxtTkNo").val().trim();
						var tn = $j("#txtTk").html();
						if(tn != ""){
							if (tkNo != tn) {
								$j("#distxtTkNo").focus();
								$j("#distxtTkNo").val("");
								$j("#distknoErrorMsg").html("非正确的快递单号!");
								return;
							}						
						}
					}
					var data={};
					var postData = {};
					postData[staid] = staid;
					var staline = loxia.syncXhr($j("body").attr("contextpath")+ "/findSnCardGiftStaLineListByStaId.json",postData);
					if(staline && staline.result && staline.result == "success"){
					    data["sta.id"]=staid;
						for(var i = 0;i<staline.staline.length;i++){
					    	//完成数量
					    	data["staLineList[" + i + "].completeQuantity"] = staline['staline'][i]['quantity'];
					    	//明细主键
					    	data["staLineList[" + i + "].id"] = staline['staline'][i]['id'];
						}
						 data["packageInfos[0].trackingNo"] = $j("#distxtTkNo").val().trim();
					    var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/staSortingCheck.json",data);
						if(rs.result=='success'){
							$j("#dialog_msg_dis").dialog("close");
							$j("#tbl-sta-list tr[id="+snid+"]").remove();
							var ids = $j("#tbl-sta-list").jqGrid('getRowData');
							if(ids.length == 0){
								//没有数据 回到最初页面
								$j("#plDiv").removeClass("hidden");
								$j("#checkDiv").addClass("hidden");
								$j("#search").trigger("click");
							}else{
								$j("#newCard").val("");
								$j("#newCard").focus();
							}
						}else{
							errorTipOK(rs.msg);
						}
					}else{
						jumbo.showMsg("核对异常");
					}
				});
				
				$j("#confirmBarCode").keyup(function(evt) {
					if (BARCODE_CONFIRM == $j("#confirmBarCode").val().trim()) {
						$j("#btnConfirm").trigger("click");
					}
				});
				
				$j("#disconfirmBarCode").keyup(function(evt) {
					if (BARCODE_CONFIRM == $j("#confirmBarCode").val().trim()) {
						$j("#disbtnConfirm").trigger("click");
					}
				});
				
			});

//后置打印面单
function postPrintExpressBill(type){
	var staId = "";
	if(type == "1"){
		staId = $j("#hidStaId").val();
	}
	if(type == "2"){
		staId = $j("#dishidStaId").val();
	}
	if(null == staId || "" == staId){
		playMusic($j("body").attr("contextpath") +"/recording/staError.wav");
		jumbo.showMsg("参数异常");
		return false;
	}
	var dt = new Date().getTime();
	var url = $j("body").attr("contextpath") + "/printsingledeliverymode1.json?rt="+ dt +"&sta.id=" + staId;
	printBZ(loxia.encodeUrl(url),false);
}


function getCheckPostData() {
	var postdata = {};
	postdata["sta.id"] = $j("#hidStaId").val();
	postdata["sta.trackingNo"] = $j("#txtTkNo").val().trim();
	postdata["lineNo"] = lineNo;
	postdata["sn"] = $j("#hidSn").val();
	return postdata;
}

function printTrank(staId ,packId){
	if((staId == null || staId == "") && (packId == null || packId == "")){
		playMusic($j("body").attr("contextpath") +"/recording/staError.wav");
		jumbo.showMsg("打印失败：系统异常！");
	}else{
		loxia.lockPage();
		jumbo.showMsg("面单打印中，请等待...");
		var url = $j("body").attr("contextpath") + "/printSingleDeliveryMode2Out.json?sta.id="+staId+"&trankCode="+packId;
		printBZ(loxia.encodeUrl(url),true);				
		loxia.unlockPage();
	}
}

//后置打印装箱清单
function postPrintPackingPage(){
	var plId = $j("#plId").val();
	var staId = $j("#hidStaId").val();
	if(null == staId || "" == staId || null == plId || "" == plId){
		playMusic($j("body").attr("contextpath") +"/recording/staError.wav");
		jumbo.showMsg("参数异常");
		return false;
	}
	var dt = new Date().getTime();
	var url = $j("body").attr("contextpath") + "/printtrunkpackinglistmode1Out.json?rt="+ dt +"&plCmd.id=" + plId + "&plCmd.staId=" + staId + "&isPostPrint=true";
	printBZ(loxia.encodeUrl(url),false);
}

//激活失败拣货单补打
function printSnCardErrorList(){
	var plCode = $j("#verifyCode").html().trim();
	var staCode = $j("#staCodeValues").val().trim();
	if(staCode != "" && staCode != null){
		plCode = "";
	}
	if(null == staCode && "" == staCode && null == plCode && "" == plCode){
		playMusic($j("body").attr("contextpath") +"/recording/staError.wav");
		jumbo.showMsg("参数异常");
		return false;
	}
	loxia.lockPage();
	var url = $j("body").attr("contextpath") + "/printSnCardErrorList.json?staCode="+staCode+"&plCode="+plCode;
	printBZ(loxia.encodeUrl(url),false);
	loxia.unlockPage();
}

//播放提示音
function playMusic(url){
    var audio = document.createElement('audio');
    var source = document.createElement('source');   
    source.type = "audio/mpeg";
    source.type = "audio/mpeg";
    source.src = url;   
    source.autoplay = "autoplay";
    source.controls = "controls";
    audio.appendChild(source); 
    audio.play();
 }

