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
	ENTITY_SKU_JMCODE			:	"商品编码",
	ENTITY_SKU_KEYPROP			:	"扩展属性",
	ENTITY_SKU_NAME				:	"商品名称",
	ENTITY_SKU_SUPCODE			:	"货号",
	ENTITY_STALINE_TOTAL		:	"计划量执行量",
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

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}
function divToggle(id){
	if($j(id).hasClass("hidden")){
		$j(id).removeClass("hidden");
	}else{
		$j(id).addClass("hidden");
	}
}
function staListToggle(){divToggle("#div-sta-list");}
function staToggle(){divToggle("#div-sta-detail");}
function stvToggle(){divToggle("#div-stv");}
function showdetail(obj){
	$j("#dialog_error_ok").dialog({title: "错误信息", modal:true, autoOpen: false, width: 470});
	$j("#dialog_error_ok").addClass("hidden");
	$j("#addBarCode").addClass("hidden");
	$j("#addSku").addClass("hidden");
	$j("#add_status").addClass("hidden");
	staListToggle();
	var staId=$j(obj).parent().parent().attr("id");
	var sta=$j("#tbl-inbound-purchase").jqGrid("getRowData",staId);
	$j("#staId").val(staId);
	$j("#createTime3").val(sta.createTime);
	$j("#po3").val(sta.refSlipCode);
	$j("#staCode3").val(sta.code);
	$j("#owner3").val(sta.owner);
	$j("#supplier3").val(sta.supplier);
	$j("#status3").val($j(obj).parent().next().html());
	$j("#left3").val(sta.remnant);
	$j("#sta_memo").val(sta.returnReasonMemo);// 退换货原因备注
	if(!sta['stvId']){// 没有未完成的STV
		staToggle();
		$j("#tbl-orderNumber").jqGrid('setGridParam',
				{url:$j("body").attr("contextpath")+"/stalinelist.json?sta.id="+staId}).trigger("reloadGrid",[{page:1}]);
		if(sta.owner=="5Nike-Global Inline 官方商城"||sta.owner=="5Nike-Global Swoosh 官方商城"){
		var postData = {};
		postData["staid"]=staId;
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/whetherNeedInvoice.json",postData);
		if((result.isNeedInvoice=="true")&&(sta.owner=="5Nike-Global Inline 官方商城"||sta.owner=="5Nike-Global Swoosh 官方商城")){
			 
		 }else{
			 $j("#remind").addClass("hidden");
		 	}
		}else{
			 $j("#remind").addClass("hidden");
		}
		$j("#createTime").html(sta.createTime);
		$j("#staCode").html(sta.code);
		$j("#po").html(sta.refSlipCode);
		$j("#owner").html(sta.owner);
		$j("#supplier").html(sta.supplier);
		$j("#status").html($j(obj).parent().next().html());
		$j("#left").html(sta.remnant);
		
		
	}else{
		$j("#stvId").val(sta['stvId']);
		$j("#nowNum").val(sta['stvTotal']);
		tostvInfo(sta['stvId'],sta);
	}
	$j("#barCode").trigger("focus");
}

/*******************************************************************************
 * 条码扫描模块 返回<条码,<id,行>>
 ******************************************************************************/
function getBarCodeMap(){
	var codeMap={};
	$j.each($j("#tbl-orderNumber").jqGrid("getRowData"),function(i,row){
		if(!codeMap[row['barCode']]){
			// 不存在相同的条码则添加
			var rowMap=[];
			var rowVal = {};
			rowVal["id"] = row.id;
			rowVal["row"] = row;
			rowMap[0] = rowVal;
			codeMap[row['barCode']]=rowMap;	
		}else{
			var rowVal = {};
			rowVal["id"] = row.id;
			rowVal["row"] = row;
			var rowMap = codeMap[row['barCode']];
			rowMap[rowMap.length] = rowVal;
			codeMap[row['barCode']]=rowMap;	
		}
	});
	return codeMap;
}

function getSkuRow(barcode){
	var lines = getBarCodeMap()[barcode];
	for(var i in lines){
		var sku = lines[i].row;
		var addQuantity=$j.trim($j("#tbl-orderNumber tr[id="+sku['id']+"] :input").val());
		addQuantity = addQuantity == "" ? 0 : parseInt(addQuantity,10);
		if(sku['quantity']-sku['completeQuantity']>addQuantity){
			return sku;
		}
	}
	return null;
}


// 条码checkmaster
function checkBarCode(value,obj){
	if($j.trim(value).length==0)return loxia.SUCCESS;
	if(getBarCodeMap()[$j.trim(value)]){
		return loxia.SUCCESS;
	}
	for(var key in extBarcode){
		for(var i in extBarcode[key]){
			if($j.trim(value) == extBarcode[key][i]){
				return loxia.SUCCESS;
			}
		}
	}
	return i18("INVALID_NOT_EXIST",i18("ENTITY_SKU_BARCODE"));
}
// reset条码扫描后的表单
function resetBarCodeForm(id){
	$j("#tbl-orderNumber").jqGrid('resetSelection');
	$j("#tbl-orderNumber").jqGrid('setSelection',id);
	loxia.byId("barCodeCount").setEnable(false);
	
	$j("#barCode").val("");
	$j("#add_status").val("").addClass("hidden");
	$j("#tip").addClass("hidden");
	$j("#tipAD").addClass("hidden");
	$j("#addSku").addClass("hidden");
	$j("addBarCode").val("");
	
	$j("#barCode").removeClass("ui-loxia-error").focus();
	loxia.tip($j("#barCode"));
}
// 商品编码手动输入模块
// 返回<商品编码_扩展属性,行>,存在相同商品编码_扩展属性,只保存第一个
function getCodeMap(){
	var codeMap={};
	$j.each($j("#tbl-orderNumber").jqGrid("getRowData"),function(i,row){
		if(!codeMap[row['jmcode']+"_"+$j.trim(row['keyProperties'])]){
			codeMap[row['jmcode']+"_"+$j.trim(row['keyProperties'])]=row;
			codeMap[row['jmcode']]=row['jmcode'];
		}
	});
	return codeMap;
}
// 根据商品编码,返回扩展属性数组
function getKeyProps(code){
	var arr=[];
	$j.each($j("#tbl-orderNumber").jqGrid("getRowData"),function(i,row){
		if(row['jmcode']==code){
			if(!$j.trim(row['keyProperties']))
				arr.push($j.trim(row['keyProperties']));
		}
	});
	return arr.sort();
}

function refreshKeyProps(){
	var obj=$j("#keyProps");
	$j("#keyProps option").remove();
	$j('<option value="select">'+i18("LABEL_SELECT")+'</option>').appendTo(obj);
}
function getPostData(staId){
	var postData={},index=-1,total=0;
	postData['stv.sta.id']=staId;
	postData['stv.sta.memo']=$j("#sta_memo").val()
	var validateSkuQty = {};
	$j.each($j("#tbl-orderNumber").jqGrid("getRowData"),function(i,row){
		var skuQtyKey = row["barCode"];
		var skuCompleteQty = (validateSkuQty[skuQtyKey] == undefined ? 0 : validateSkuQty[skuQtyKey]);
		// 本次计划量 = 总计划量 - 已执行量
		var completeQuantity = parseInt(row['quantity'] == "" ? 0 : row['quantity']) - parseInt(row['completeQuantity'] == "" ? 0 : row['completeQuantity']);
		var addQuantity=$j.trim($j("#tbl-orderNumber tr[id="+row['id']+"] :input").val());
		var addQuantity = (addQuantity == "" ? 0:addQuantity);
		if(addQuantity != '0' && !numValidate.test(addQuantity)){
			postData['error']="数值错误！";
			return ;
		}
		// 剩余计划量 = 本次计划量 + 此行计划量 - 此行执行量
		skuCompleteQty = parseInt(skuCompleteQty ? skuCompleteQty : 0) + completeQuantity - parseInt(addQuantity == "" ? 0 : addQuantity);
		if(skuCompleteQty < 0){
			postData['error']="商品["+row["barCode"]+"]数量大于计划量";
			return ;
		}
		validateSkuQty[skuQtyKey] = skuCompleteQty;
	});
	for(sku in validateSkuQty){
		if(validateSkuQty[sku] != 0){
			postData['error']="商品["+sku+"]数量小于计划量";
		}
	}
	$j.each($j("#tbl-orderNumber").jqGrid("getRowData"),function(i,row){
		var addQuantity=$j.trim($j("#tbl-orderNumber tr[id="+row['id']+"] :input").val());
		var completeQuantity=row['completeQuantity'];
		// 构建隐藏INPUT调提交SN数据
		var td = $j("#tbl-orderNumber tr[id='"+row.id+"'] td:last-child");
		var text = td.find(".sns");
		if(row.isSnSku){
			var sns = $j("#tbl-orderNumber tr[id="+row.id+"]").data("sns");
			if(!text||text.length==0){
				text=$j("<input type='hidden' class='sns' />").appendTo(td);
			}
			if(sns) text.val(sns.join(","));
		}
		if(addQuantity.length>0){
			if(addQuantity>0){
				index+=1;
				postData['stv.stvLines['+index+'].quantity']=addQuantity;
				postData['stv.stvLines['+index+'].skuCost']=row['skuCost'];
				postData['stv.stvLines['+index+'].sku.id']=row['skuId'];
				postData['stv.stvLines['+index+'].invStatus.id']=row['invStatusId'];
				var snType = row['snType'];
				if(starbucks_sn == snType){//星巴克
					postData['snType']= starbucks_sn;
				}else{//非星巴克
					postData['snType']= '1';
				}
				var staLineid = row['id'];
				var str = staLineid.split('_');
				staLineid = str.length==2 ? str[0] : staLineid;
				postData['stv.stvLines['+index+'].staLine.id']=staLineid;
				if(outboundSn.length>0){
					if($j("#tbl-orderNumber tr[id="+row['id']+"] td:last").html()!=""){
						var sn=$j("#tbl-orderNumber tr[id="+row['id']+"] .sns").val();
						if(sn&&sn.length>0&&sn.split(",").length==addQuantity){
							postData['stv.stvLines['+index+'].sns']=$j("#tbl-orderNumber tr[id="+row['id']+"] .sns").val();
						}else{
							postData['error']=i18("INVALID_SN_LINE",i+1);
							return ;
						}
					}
				}
				total+=addQuantity;
			}
		}
	});
	var index = 0;
	
	for(id in newWarrantyCard){
		for(i in newWarrantyCard[id]){
			postData['giftLineList['+index+'].staLineId']=id;
			postData['giftLineList['+index+'].intType']=20;
			postData['giftLineList['+index+'].sanCardCode']=newWarrantyCard[id][i].sanCardCode;
			index++;
		}
	}
	
	
	postData['total']=total;
	$j("#nowNum").val(total);
	return postData;
}

function showSnDialog(obj){
	var id = $j(obj).parents("tr").attr("id");
	var sns = $j("#tbl-orderNumber tr[id="+id+"]").data("sns");
	$j("#divSn div.snDiv").remove();
	for(var d in sns){
		$j("#divClear").before("<div class='snDiv'>" + sns[d] + "</div>");
	}
	$j("#dialog-sns-view").dialog("open");	
}

function reloadgrid(value){
	var index=1;
	$j("#tbl-sns").jqGrid("clearGridData");
	if(value&&value.length>0){
		var arr=value.split(","),index=arr.length;
		for(var i=0;i<arr.length;i++){
			var row={};
			row["id"]=i;
			row["sns"]=arr[i];
			$j("#tbl-sns").jqGrid('addRowData',i+1,row);
		}
		index+=arr.length;
	}
	$j("#snsindex").val(index);
}
var starbucks_zhx = '3';
var starbucks_hp = '5';
var starbucks_sn = '3';
var starbucksFlag = false;
var warrantyCardTypeJson = {};
var newWarrantyCard = {};
var numValidate = /^[0-9]*[1-9][0-9]*$/;


$j(document).ready(function (){
	//nikeRFID=$j("#userinfo", window.parent.document).attr("nikeRFIDCode");
	$j("#dialog-sns-view").dialog({title: "SN序列号", modal:true, autoOpen: false, width: 400});
	$j("#dialog_gift").dialog({title: "COACH 保修卡保存", modal:true,closeOnEscape:false, autoOpen: false, width: 550});
	$j("#dialog_nike_rfid").dialog({title: "扫描OK", modal:true, autoOpen: false, width: 470});
	$j("#needSn").click(function(){
		$j("#tbl-orderNumber .ui-button").toggle();
	});
	
	$j("#shortcut_code").focus();
	
	// 初始化物流商信息
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransportator.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selLpcode"));
	}
	
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findInventoryStatus.do?isAvailable=true");
	for(var idx in result){
		$j("<option value='" + result[idx].statusId + "'>"+ result[idx].statusName +"</option>").appendTo($j("#add_status"));
	}
	
	jumbo.loadShopList("companyshop");
	initShopQuery("companyshop","innerShopCode");
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	

	$j("#search").click(function(){
		var postData = loxia._ajaxFormToObj("form_query");  
		postData['sta.intType'] = 41;
		var url = $j("body").attr("contextpath")+"/json/stalist.json";
		$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
			url:url,page:1,postData:postData}).trigger("reloadGrid");
	});
	
	
	$j("#nikeRFIDOK").keypress(function(event){
		if(event.keyCode === 13){
			var rfidCode=null;
			var nikeRFIDCode=$j("#nikeRFIDOK").val();
			if(nikeRFIDCode!=null&&nikeRFIDCode!=""){
				if(nikeRFIDCode==BARCODE_CONFIRM){
					jQuery.ajax({
				        type: 'get',
				        url: "http://localhost.baozun.com:8088/getRfid",
				        dataType: "json",
				        async: false,
				        data: {"hostCode":$j("#nikeRFID").val()},
				        success: function (data) {
				            if(data.code=="SUCCESS"){
				            	var postData={};
				            	postData["staId"]=$j("#staId").val();
				            	postData["barCode"] = $j.trim($j("#barCode").val());
				            	postData["rfidCode"]=data.rfid.join("⊥");
								var rs = loxia.syncXhr(window.parent.$j("body").attr("contextpath")+"/nikeRFIDByCode.json",postData);
								if(rs!=null&&rs["msg"]){
									$j("#dialog_nike_rfid").dialog("close");
									var barcode = $j.trim($j("#barCode").val());
									$j("#tipAD").addClass("hidden");
									var sku=getSkuRow(barcode);
									if(!sku){
										for(var key in extBarcode){
											for(var i in extBarcode[key]){
												if(barcode == extBarcode[key][i]){
													sku = getSkuRow(key);
													break;
												}
											}
											if(sku){
												break;
											}
										}
									}
									if(sku){
										var addQuantity=$j.trim($j("#tbl-orderNumber tr[id="+sku['id']+"] :input").val());
										if(addQuantity.length==0){ 
											addQuantity=0;
										}else {
											addQuantity=parseInt(addQuantity,10);
										}
										if(isNaN(addQuantity)||addQuantity<0){
											loxia.tip($j("#tbl-orderNumber tr[id="+sku['id']+"] :input"),i18("INVALID_NUMBER"));
										}else{
											$j("#add_status").html("");
											var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findInventoryStatus.do?isAvailable=true");
											for(var idx in result){
												$j("<option value='" + result[idx].statusId + "'>"+ result[idx].statusName +"</option>").appendTo($j("#add_status"));
											}
											var res = loxia.syncXhrPost($j("body").attr("contextpath")+"/findisspecial.json",{"barCode":sku["barCode"],"staCode":$j("#staCode").html()});
											if(res!=null&&res.flag=="true"){
												$j("#add_status option").each(function(){
													if(!($j(this).html().indexOf("待处理品")>=0)){
														$j(this).remove();
													}
												});
												$j("#tip").removeClass("hidden");
											}else{
												var res = loxia.syncXhrPost($j("body").attr("contextpath")+"/findIsSpecialBySku.json",{"barCode":sku["barCode"],"staCode":$j("#staCode").html()});
												if(res!=null&&res.flag){
													$j("#tipAD").removeClass("hidden");
												}else{
													$j("#add_status").val(sku["invStatusId"]);
												}
											}
											$j("#addBarCode").removeClass("hidden");
											$j("#addSku").removeClass("hidden");
											$j("#add_status").removeClass("hidden");
											$j("#add_barCode").text(sku["barCode"]);
											$j("#addBarCode").val(barcode);
											$j("#add_jmCode").text(sku["jmcode"]);
											$j("#add_skuName").text(sku["skuName"]);
											
										}
									}
								}else{
									errorTipOK("数据不匹配");
								}
				            }else {
				            	errorTipOK(data.msg.toString());
				            }
				        },
				        error:function(data){
				        	errorTipOK("RFID设备连接不上");}
				    });
				}else{
					errorTipOK("请输入OK");
				}
			}
		}
	});
	
	
	
	
	$j("#shortcut_code").keydown(function(event){
		if(event.keyCode === 13){
			var shortcut_code = $j.trim($j("#shortcut_code").val());
			if(shortcut_code != null && shortcut_code != ""){
				var postData = loxia._ajaxFormToObj("form_query");  
				postData['sta.intType'] = 41;
				postData['sta.code'] = shortcut_code;
				postData['isNeedInvoice'] = "";
				postData['lpcode'] = "";
				postData['trackingNo'] = "";
				postData['startTime'] = ""; 
				postData['endTime'] = "";
				postData['sta.refSlipCode'] = "";
				postData['sta.owner'] = "";
				
				var url = $j("body").attr("contextpath")+"/json/stalist.json";
				$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
					url:url,postData:postData}).trigger("reloadGrid",[]);
				
			}
		}
	});
	
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val(0);
		$j("#isNeedInvoice").val("请选择");
	});
	
	
	$j("#confirmsns").click(function(){
		var targetId=$j("#dialog-sns").attr('targetId');
			var addQuantity=$j.trim($j("#tbl-orderNumber tr[id="+targetId+"] :input").val()),sns_len=$j("#tbl-sns").jqGrid("getDataIDs").length;
			if(sns_len!=addQuantity){
				if(confirm("SN序列号个数="+sns_len+", 与本次执行量="+addQuantity+"不相等, 您确定SN序列号的个数正确?")){
					$j("#tbl-orderNumber tr[id="+targetId+"] :input").val(sns_len);
				}
			}
			valtext($j("#tbl-orderNumber tr[id="+targetId+"] .sns"));
			$j("#dialog-sns").dialog("close");
	});	
	$j("#tbl-sns").jqGrid({
		datatype: "local",
		colNames: ["ID",i18("ENTITY_STALINE_SNS")],
		colModel: [
		           {name: "id", index: "id", hidden: true},		         
		           {name: "sns", index: "sns",sortable:false}],
		caption: i18("ENTITY_STALINE_SNS"),
	   	sortname: 'sns',
	    multiselect: true,
		sortorder: "desc",
		width:300,
		height:"auto",
		rownumbers:true,
		rowNum:-1,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	reloadgrid();
	
	// SN对对话框删除选中行
	$j("#delSelRows").click(function(){
		var ids = $j("#tbl-sns").jqGrid('getGridParam','selarrrow');
		for(var id in ids){
			$j("#tbl-sns tr[id='" + ids[id] + "']").remove();
		}
	});	
	
	$j("#snscode").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			var reg=/^[a-zA-Z0-9]+$/,sns=$j("#snscode").val();
			
			// 判断SN号是否是出库SN号
			if(outboundSn == null){
				jumbo.showMsg("未找到出库SN号");
				return;
			}
			var isOutSn = false;
			for(var i in outboundSn){
				if(outboundSn[i].sn == sns){
					var data = $j("#tbl-orderNumber").jqGrid("getRowData",$j("#dialog-sns").attr("targetid"));
					var barcode = data.barCode;
					if(barcode != outboundSn[i].barcode){
						jumbo.showMsg("入库SN号与商品不匹配！");
						return ;
					}
					
					isOutSn = true;
					break;
				}
			}
			if(!isOutSn){
				jumbo.showMsg("SN号 : [" + sns + "] 非销售出库SN号");
				return;
			}
			
			// 判断重复
			var isSame = false;
	 		$j("#tbl-sns tr[id]").each(function(i,tr){
	 			var data = $j("#tbl-sns").jqGrid("getRowData",$j(tr).attr("id"));
	 			if(data["sns"] == sns){
	 				jumbo.showMsg("输入SN号重复");
	 				isSame = true;
	 			}
	 		});
	 		if(isSame){
	 			return;
	 		}
			
			if(sns&&sns.length>0&&reg.test(sns)){
				var row={},index=$j("#snsindex").val();
				row["id"]=index;
				row["sns"]=sns;
				$j("#tbl-sns").jqGrid('addRowData',index,row);
				$j("#snsindex").val(++index);
				$j("#snscode").val("");
			}else{
				loxia.tip($j("#snscode"),"请按正确的格式输入SN序列号");
			}
		}
	});
	
	var status=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"whSTAProcessStatus"});
	var trueOrFalse=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"trueOrFalse"});
	if(!status.exception){
		// STA作业申请单
		$j("#tbl-inbound-purchase").jqGrid({
			url:$j("body").attr("contextpath")+"/stalist.json?sta.intType=41",
			datatype: "json",
			colNames: ["ID","STVID","STVTOTAL","STVMODE",i18("ENTITY_STA_CREATE_TIME"),// i18("ENTITY_STA_ARRIVE_TIME"),
			           i18("ENTITY_STA_CODE"),
				i18("ENTITY_STA_PROCESS"),// i18("ENTITY_STA_UPDATE_TIME"),
				i18("ENTITY_STA_PO"),
				"OWNER",
				i18("ENTITY_STA_OWNER"),// i18("ENTITY_STA_SUP"),i18("ENTITY_STA_SEND_MODE"),
				i18("LPCODE"),i18("TRACKING_NO"),i18("IS_NEED_INVOICE"),i18("ORDER_CREATE_TIME"),
				i18("ENTITY_STA_REMANENT"),i18("ENTITY_STA_COMMENT"),i18("RETURN_REASON_MEMO")],
			colModel: [
			           {name: "id", index: "id", hidden: true,sortable:false},		         
			           {name: "stvId", index: "stvId", hidden: true,sortable:false},		         
			           {name: "stvTotal", index: "stv_total", hidden: true,sortable:false},		         
			           {name: "stvMode", index: "stv_mode", hidden: true,sortable:false},		         
			           {name: "createTime", index: "create_time", width: 200, resizable: true,sortable:false},
			           // {name: "arriveTime", index: "arrive_time", width:
						// 200, resizable: true,sortable:false},
			           {name: "code", index: "sta.code",formatter:"linkFmatter", formatoptions:{onclick:"showdetail"}, width: 200, resizable: true,sortable:false},
			           {name: "processStatus", index: "status", width: 100, resizable: true,
				        	   formatter:'select',editoptions:status,sortable:false},
			           // {name: "inboundTime", index: "inbound_time", width:
						// 200, resizable: true,sortable:false},
			           {name: "refSlipCode", index: "slip_code", width: 150, resizable: true,sortable:false},
			           {name: "owner", index: "owner", hidden: true,sortable:false},
			           {name: "channelName", index: "channelName", width: 80, resizable: true,sortable:false},
			           {name: "lpcode", index: "lpcode", width: 100, resizable: true,sortable:false},
			           {name: "trackingNo", index: "trackingNo", width: 100, resizable: true,sortable:false},
			           {name: "isNeedInvoice", index: "isNeedInvoice", width: 80, resizable: true,sortable:false,formatter:'select',editoptions:trueOrFalse},
			           {name: "orderCreateTime", index: "order_create_time", width: 200, resizable: true,sortable:false},
		               {name: "remnant", index: "remnant", width: 120, resizable: true,sortable:false},
		               {name:"memo",index:"memo",width:150,resizable:true,sortable:false},
		               {name:"returnReasonMemo",index:"returnReasonMemo", hidden: true,sortable:false}],
			caption: i18("TABLE_CAPTION_STA"),
		   	sortname: 'sta.create_time',
		   	sortorder: "desc",
		   	multiselect: false,
		    rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
		   	pager:"#pager",
		   	height:jumbo.getHeight(),
			viewrecords: true,
	   		rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" }
		   /*
			 * multiselect: false, sortorder: "desc", width:"auto",
			 * height:"auto", loadonce:false, rownumbers:true, rowNum:-1,
			 * jsonReader: { repeatitems : false, id: "0" }
			 */
			});
		    //jumbo.bindTableExportBtn("tbl-inbound-purchase");
		    //var url=$j("body").attr("contextpath")+"/stalist.json?sta.intType=41";
		   var postData = loxia._ajaxFormToObj("form_query");  
		   postData['sta.intType'] = 41;
		   jumbo.bindTableExportBtn("tbl-inbound-purchase",{},$j("body").attr("contextpath")+"/stalist.json",postData);
		}else{
			jumbo.showMsg(i18("ERROR_INIT"));
		}
	
// $j("table.ui-jqgrid-htable").attr("style","width:1400px;");
// $j("#tbl-inbound-purchase").attr("style","width:1400px;");
	// STA作业申请单行
	$j("#tbl-orderNumber").jqGrid({
		datatype: "json",
		colNames: ["ID","STAID","SKUID","WARRANTYCARDTYPE","OWNER","INTSTATUS","INTERFACETYPE","SNTYPE","SPTYPE","skuCost",i18("ENTITY_SKU_BARCODE"),i18("ENTITY_SKU_JMCODE"),i18("ENTITY_SKU_KEYPROP"),
			i18("ENTITY_SKU_NAME"),i18("ENTITY_SKU_SUPCODE"),"库存状态",i18("ENTITY_STALINE_TOTAL"),
			i18("ENTITY_STALINE_COMPLETE"),i18("ENTITY_STALINE_CURRENT"),"发票",/* i18("ENTITY_STALINE_SNS"), */i18("ENTITY_STALINE_SNS"),"RFID"],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},	
		           {name: "staId",index: "staId", hidden: true,sortable:false},	
		           {name: "skuId", hidden: true,sortable:false},
		           {name: "invStatusId", hidden: true,sortable:false},
		           {name: "interfaceType", hidden: true,sortable:false},
		           {name: "snType", hidden: true,sortable:false},
		           {name: "spType", hidden: true,sortable:false},
		           {name: "warrantyCardType",index: "warrantyCardType", hidden: true,sortable:false},
		           {name: "owner", index: "owner", hidden: true,sortable:false},	
		           {name: "skuCost", index: "sku_cost", hidden: true,sortable:false},	
		           {name: "barCode", index: "barCode", width: 100, resizable: true,sortable:false},
		           {name: "jmcode", index: "jmcode", width: 100, resizable: true,sortable:false},
		           {name: "keyProperties", index: "keyProperties", width: 120, resizable: true,sortable:false},
		           {name: "skuName", index: "skuName", width: 80, resizable: true,sortable:false},
		           {name: "jmskuCode", index: "jmskuCode", width: 120, resizable: true,sortable:false},
		           {name: "intInvstatusName", index: "intInvstatusName", width: 80, resizable: true,sortable:false},
		           {name: "quantity", index: "quantity", width: 100, resizable: true,sortable:false},
		           {name: "completeQuantity", index: "completeQuantity", width: 100, resizable: true,sortable:false},
	               {name: "addQuantity",formatter:"loxiaInputFmatter", formatoptions:{loxiaType:"number",min:"0"}, width: 160, hidden: false,resizable: true,sortable:false},
	               {name: "extCode1", index: "extCode1",width: 100, hidden:true,sortable:false},
	               {name: "isSnSku", width: 120, resizable: true,sortable:false, formatter:"boolButtonFmatter", formatoptions:{"buttons":{label:"查看SN号", onclick:"showSnDialog(this,event);"}}},
	               {name: "skuRfid", index: "skuRfid",hidden: true}
	               ],
		caption: i18("TABLE_CAPTION_STALINE"),
	   	sortname: 'barCode',
	    multiselect: false,
		viewrecords: true, 
	     postData:{"columns":"id,sta.id,staId,skuId,invStatusId,interfaceType,snType,spType,warrantyCardType,owner,skuCost,isSnSku,barCode,jmcode,keyProperties,skuName,jmskuCode,intInvstatusName,quantity,completeQuantity,extCode1,skuRfid"},
		sortorder: "desc", 
		height:"auto",
		loadonce:true,
		rownumbers:true,
		rowNum:-1,
		jsonReader: { repeatitems : false, id: "0" },
		loadComplete:function(){
			loxia.initContext($j("#tbl-orderNumber"));
			var postData = {};
			$j("#tbl-orderNumber tr:gt(0)>td[aria-describedby$='barCode']").each(function (i,tag){
				postData["skuBarcodes[" + i + "]"] = $j(tag).html();
			});
			extBarcode = loxia.syncXhr($j("body").attr("contextpath") + "/findskuBarcodeList.json",postData);
			var staid = $j("#staId").val();
			var rs = loxia.syncXhr($j("body").attr("contextpath") + "/findOutBoundSn.json",{"staid" : staid});
			if( rs && rs.sns){
				outboundSn = rs.sns;
				if(outboundSn.length == 0){
					$j("#tbl-orderNumber tr td[aria-describedby$='isSnSku'] button").addClass("hidden");
				}
			} else {
				$j("#tbl-orderNumber tr td[aria-describedby$='isSnSku'] button").addClass("hidden");
			}
			//查询当前退货单是否是 coach 店铺 所对应的商品
			var ids = $j("#tbl-orderNumber").jqGrid('getDataIDs');
			var key = "";
			var owner = $j("#owner3").val();
			if(owner == i18("COACH_OWNER1") || owner == i18("COACH_OWNER2")){
				for(var i=0;i < ids.length;i++){
					var datas = $j("#tbl-orderNumber").jqGrid('getRowData',ids[i]);
					if(datas["warrantyCardType"] == i18("WARRANTY_CARD_TYPE_YES_NO_CHECK")){
						var k = datas["id"].toString();
						warrantyCardTypeJson[k] = datas["jmskuCode"];
		                if(key == ""){
		                	key = k;
		                }
					}
				}
			}
			starbucksFlag = false;
			for(var i=0;i < ids.length;i++){
				var datas = $j("#tbl-orderNumber").jqGrid('getRowData',ids[i]);
				if(starbucks_sn == datas["snType"]){
					starbucksFlag = true;
				}
			}
			if(key != ""){
				$j("#dialog_gift").prev().find("a").addClass("hidden");
				initDialogGift(key,warrantyCardTypeJson[key]);
				$j("#dialog_gift").dialog("open");
			}
		}
	});
	
	$j("#caochWarrantyCard").keydown(function(evt){
		$j("#dialog_gift_error").text("");
		if(evt.keyCode === 13){
			var card = $j.trim($j("#caochWarrantyCard").val());
			if(card != ""){
				var id = $j("#staLineId").val();
				var key = id.toString();
				if(newWarrantyCard[key] == null){
					newWarrantyCard[key] = eval("[{'staLineId':'"+key+"','sanCardCode':'"+card+"'}]");
				}else{
					for(i in newWarrantyCard[key]){
						if(newWarrantyCard[key][i].sanCardCode == card){
							$j("#dialog_gift_error").text("保修卡条码重复！");
							return ;
						}
					}
					var arr  =
					{
						'staLineId':key,
						'sanCardCode':card
				    }
					newWarrantyCard[key].push(arr);
				}
				if($j("#showGiftList").text()=="收起"){
					var strHtml = $j("#warrantyCardList").html()+"["+card+ "]<br />";
					$j("#warrantyCardList").html(strHtml);
				}
				$j("#warrantyCardNumber").text(parseInt($j("#warrantyCardNumber").text()) + 1);
				$j("#caochWarrantyCard").val("");
				$j("#caochWarrantyCard").focus();
			} else {
				$j("#gift_success").focus();
			}
		}
	});
	
	
	$j("#gift_success").click(function(){
		if($j("#warrantyCardNumber").text() == "0"){
			$j("#caochWarrantyCard").focus();
			$j("#dialog_gift_error").text("请扫描保修卡条码！");
			return;
		}else{
			var staLineid = $j("#staLineId").val();
			var isBreak = false;
			for(id in warrantyCardTypeJson){
				if(isBreak) {
					initDialogGift(id,warrantyCardTypeJson[id]);
					return;
				}
				if(id == staLineid){
					isBreak = true;
				}
			}
			$j("#dialog_gift").dialog("close");
		}
	});
	$j("#showGiftList").click(function(){
		var id = $j("#staLineId").val();
		if($j("#showGiftList").text()=="查看"){
			var strHtml = "";
			for(i in newWarrantyCard[id]){
				strHtml += "["+newWarrantyCard[id][i].sanCardCode + "]<br />";
			}
			$j("#warrantyCardList").html(strHtml);
			$j("#showGiftList").text("收起");
		} else {
			$j("#warrantyCardList").html("");
			$j("#showGiftList").text("查看");
		}
	});
	
	//确认条码
	$j("#addSku").click(function(){
		var count = parseInt($j("#barCodeCount").val()||"1",10);
		var barcode = $j.trim($j("#addBarCode").val());
		var intStatusId = $j("#add_status").val();
		if(intStatusId == "") {
			jumbo.showMsg("库存状态不能为空");
			return false;
		}
		if(true == starbucksFlag){
			var invStatusText = $j.trim($j("#add_status").find("option:selected").text());
			if('残次品' != invStatusText){
				jumbo.showMsg("库存状态不正确，星巴克卡券必须残次入库");
				return false;
			}
			
		}
		if(barcode!=""){
			var sku=getSkuRow(barcode);
			if(!sku){
				for(var key in extBarcode){
					for(var i in extBarcode[key]){
						if(barcode == extBarcode[key][i]){
							sku = getSkuRow(key);
							break;
						}
					}
					if(sku){
						break;
					}
				}
			}
			// SN号商品通过条码无法核对
			if(sku && !sku.isSnSku == ''){
				jumbo.showMsg("条码为：" + sku.barCode + "的商品必须输入SN号");
				return false;
			}
			// 扫描SN核对
			var isSnSku =false;
			for(var i in outboundSn){
				if(outboundSn[i].sn == barcode){
					sku=getSkuRow(outboundSn[i].barcode);
					if(sku){
						// SN号商品核对1次扫描只能加1
						count = 1;
						outboundSn[i]={};
						isSnSku = true;
						break;
					}
				}
			}
			
			if(sku){
				var rowId = "";
				// 计划量
				var skuPlanQuantity = 0;
				// 已经执行力
				var skuCompleteQuantity = 0;
				
				$j.each($j("#tbl-orderNumber").jqGrid("getRowData"),function(i,row){
					if(row['barCode'] == sku.barCode){
						var planQuantity = parseInt(row['quantity'] == "" ? 0 : row['quantity']);
						var completeQuantity = (row['completeQuantity'] == "" ? 0 : row['completeQuantity'])
						// 查看本次执行量
						var addQuantity = $j("#"+row["id"]+" td[aria-describedby$='addQuantity'] input").val()
						completeQuantity = parseInt(completeQuantity,10) + parseInt(addQuantity == "" ? 0 : addQuantity);
						if(intStatusId == row["invStatusId"]){
							if(row['quantity'] == "0"){
								rowId = row["id"];
							} else if(planQuantity >= (completeQuantity + count)){
								rowId = row["id"];
							}
						}
						skuPlanQuantity = skuPlanQuantity  + planQuantity;
						skuCompleteQuantity = parseInt(skuCompleteQuantity,10) + completeQuantity;
					}
				});
				if(skuPlanQuantity < (skuCompleteQuantity + 1)){
					jumbo.showMsg("条码为：" + sku.barCode + "的商品执行量超出计划量！");
					return false;
				}
				if(rowId == ""){
					rowId = sku['id'] + "_" + intStatusId;
					var row = sku;
					row['id']=rowId;
					row['completeQuantity'] = 0;
					row['invStatusId'] = intStatusId;
					row['quantity'] = 0;
					row['addQuantity'] = (isNaN(count)?1:count);
					row['intInvstatusName'] = $j("#add_status").find("option:selected").text();
					$j("#tbl-orderNumber").jqGrid('addRowData',rowId,row);
					loxia.initContext($j("#tbl-orderNumber"));
				} else {
					var addQuantity=$j.trim($j("#tbl-orderNumber tr[id="+rowId+"] :input").val());
					if(addQuantity.length==0){ 
						addQuantity=0;
					}else {
						addQuantity=parseInt(addQuantity,10);
					}
					sku['addQuantity']= addQuantity - 0 + (isNaN(count)?1:count);
					$j("#"+rowId+" input").val(addQuantity - 0 + (isNaN(count)?1:count));
				}
				if(isSnSku){
					var sns = $j("#tbl-orderNumber tr[id="+rowId+"]").data("sns");//rowId调整逻辑，获取相同库存状态的明细行中的sns
					if(!sns){
						sns = new Array();
					}
					sns[sns.length] = barcode;
					$j("#tbl-orderNumber tr[id="+rowId+"]").data("sns",sns);//增加sn
				}
				resetBarCodeForm(rowId);
				loxia.initContext($j("#tbl-orderNumber"));
				resetSkuInfo();
			}else{
				loxia.tip($j("#barCode"),"条形码不正确/SN号不正确或已统计");
			}
		}else{
			loxia.tip($j("#barCode"),i18("INVALID_ENTITY_EMPTY",i18("ENTITY_SKU_BARCODE")));
		}
		return false;
	});
	
	// 条码
	loxia.byId("barCodeCount").setEnable(false);
	$j("#barCode,#barCodeCount").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			var barcode = $j.trim($j("#barCode").val());
			$j("#tipAD").addClass("hidden");
			if('星巴克官方旗舰店' == $j.trim($j("#owner3").val())){
				barcode = barcode.split("=")[0];
			}
			if(barcode!=""){
				var sku=getSkuRow(barcode);
				if(!sku){
					for(var key in extBarcode){
						for(var i in extBarcode[key]){
							if(barcode == extBarcode[key][i]){
								sku = getSkuRow(key);
								break;
							}
						}
						if(sku){
							break;
						}
					}
				}
				// SN号商品通过条码无法核对
				if(sku && !sku.isSnSku == ''){
					jumbo.showMsg("条码为：" + sku.barCode + "的商品必须输入SN号");
					resetSkuInfo();
					return;
				}
				
				var isNikeRfid=false;
				$j.each($j("#tbl-orderNumber").jqGrid("getRowData"),function(i,row){
					if(row['barCode']==barcode){
						if(row['skuRfid']!=null&&row['skuRfid']){
							isNikeRfid=true;
						}
					}
				});
				if(isNikeRfid){
					$j("#dialog_nike_rfid").dialog("open");
					return;
					//弹出对话框 扫描ok
				}
				
				// 扫描SN核对
				var isSnSku =false;
				for(var i in outboundSn){
					if(outboundSn[i].sn == barcode){
						sku=getSkuRow(outboundSn[i].barcode);
						if(sku){
							// SN号商品核对1次扫描只能加1
							isSnSku = true;
							break;
						}
					}
				}
				if(sku){
					var interfaceType = sku.interfaceType;
					var snType = sku.snType;
					var spType = sku.spType;
					if(starbucks_sn == snType){
						//作废卡（星巴克）
						if("" != barcode){
							var bc = barcode.split("=")[0];
							barcode = bc;
						}
						var staId = $j.trim($j("#staId").val());
						var skuId = sku.skuId;
						var rs = loxia.syncXhr($j("body").attr("contextpath") + "/cancelCard.json",{"staid":staId,"sn":barcode,"skuId":skuId,"interfaceType":interfaceType,"snType":snType,"spType":spType});
						if(rs && 'success' == rs.result){
							if(starbucks_zhx == interfaceType){
								//保存SN->待解冻->解冻成功->作废成功
								var msg = "此卡已作废成功";
								jumbo.showMsg(msg);
							}else{
								//保存SN
								//starbucks_hp
							}
						}else{
							var msg = rs.msg;
							jumbo.showMsg(msg);
							return;
						}
					}
					var addQuantity=$j.trim($j("#tbl-orderNumber tr[id="+sku['id']+"] :input").val());
					if(addQuantity.length==0){ 
						addQuantity=0;
					}else {
						addQuantity=parseInt(addQuantity,10);
					}
					if(isNaN(addQuantity)||addQuantity<0){
						loxia.tip($j("#tbl-orderNumber tr[id="+sku['id']+"] :input"),i18("INVALID_NUMBER"));
					}else{
						$j("#add_status").html("");
						var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findInventoryStatus.do?isAvailable=true");
						for(var idx in result){
							$j("<option value='" + result[idx].statusId + "'>"+ result[idx].statusName +"</option>").appendTo($j("#add_status"));
						}
						var res = loxia.syncXhrPost($j("body").attr("contextpath")+"/findisspecial.json",{"barCode":sku["barCode"],"staCode":$j("#staCode").html()});
						if(res!=null&&res.flag=="true"){
							$j("#add_status option").each(function(){
								if(!($j(this).html().indexOf("待处理品")>=0)){
									$j(this).remove();
								}
							});
							$j("#tip").removeClass("hidden");
						}else{
							var res = loxia.syncXhrPost($j("body").attr("contextpath")+"/findIsSpecialBySku.json",{"barCode":sku["barCode"],"staCode":$j("#staCode").html()});
							if(res!=null&&res.flag){
								$j("#tipAD").removeClass("hidden");
							}else{
								$j("#add_status").val(sku["invStatusId"]);
							}
						}
						$j("#addBarCode").removeClass("hidden");
						$j("#addSku").removeClass("hidden");
						$j("#add_status").removeClass("hidden");
						$j("#add_barCode").text(sku["barCode"]);
						$j("#addBarCode").val(barcode);
						$j("#add_jmCode").text(sku["jmcode"]);
						$j("#add_skuName").text(sku["skuName"]);
						
					}
				}else{
					loxia.tip($j("#barCode"),"条形码不正确/SN号不正确或已统计");
					resetSkuInfo();
				}
			}else{
				loxia.tip($j("#barCode"),i18("INVALID_ENTITY_EMPTY",i18("ENTITY_SKU_BARCODE")));
				resetSkuInfo();
			}
		}
	});	
	
	//关闭错误信息
	$j("#dialog_error_close").click(function(){
		$j("#dialog_error_ok").dialog("close");
		$j("#dialog_error_ok").addClass("hidden");
	});
	
    // 执行入库
	$j("#executeInventory").click(function(){			
		if(confirm(i18("MSG_CONFIRM"))){
			var stvId=$j("#stvId").val();
			var staId=$j("#staId").val();
			executeInventory(false,stvId,staId);
		}
	});
		
	$j("#receiveAll").click(function(){
		var postData=getPostData($j("#staId").val());
		if(postData['error']){
			jumbo.showMsg(postData['error']);
		}else if(!postData['stv.stvLines[0].sku.id']){
			jumbo.showMsg(i18("MSG_NO_ACTION"));
			return true;
		} else if(confirm(i18("MSG_CONFIRM"))){
			loxia.lockPage();
			loxia.asyncXhrPost($j("body").attr("contextpath") + "/purchasereceivestep1.json",
				postData,
				{
				success:function (data) {
					loxia.unlockPage();
					if(data&&data.msg){
						jumbo.showMsg(i18("MSG_FAILURE")+data.msg);
						window.location=loxia.getTimeUrl($j("body").attr("contextpath")+"/warehouse/returninboundentry.do");
					}else{
						jumbo.showMsg(i18("MSG_SUCCESS"));
						$j("#stvId").val(data.stvId);
						staToggle();
						tostvInfo(data.stvId);
					}
				   },
				error:function(data){
					loxia.unlockPage();
					jumbo.showMsg(i18("MSG_FAILURE"));
				}
				});
		} 
	});
	$j("#closeSta").click(function(){
		if(confirm(i18("MSG_CONFIRM_CLOSESTA"))){
			loxia.lockPage();
				loxia.asyncXhrPost($j("body").attr("contextpath") + "/closesta.json",
					{'sta.id':$j("#staId").val()},
					{
					success:function (data) {
						loxia.unlockPage();
						window.location=loxia.getTimeUrl($j("body").attr("contextpath")+"/warehouse/returninboundentry.do");
					   },
					error:function(data){
						loxia.unlockPage();
						jumbo.showMsg(i18("MSG_CLOSESTA_FAILURE"));
					}
					});
		}
	});

	$j("#backto").click(function(){
		 window.location=loxia.getTimeUrl($j("body").attr("contextpath")+"/warehouse/returninboundentry.do");
	});

	$j("#cancelStv").click(function(){
		if(confirm(i18("MSG_CONFIRM_CANCEL_STV"))){
			loxia.lockPage();
			loxia.asyncXhrPost($j("body").attr("contextpath") + "/cancelstv.json",
				{'stv.id':$j("#stvId").val()},
				{
				success:function (data) {
					loxia.unlockPage();
					if(data&&data.msg){
						jumbo.showMsg(data.msg);
					}
					window.location=loxia.getTimeUrl($j("body").attr("contextpath")+"/warehouse/returninboundentry.do");
				   },
				error:function(data){
					loxia.unlockPage();
					jumbo.showMsg(i18("MSG_FAILURE"));
					window.location=loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/returninboundentry.do");
				}
				});
			
		}
	});
	
	   /**
		 * 打印上架商品情况
		 */
	   $j("#printSkuInfo").click(function(){
		  var stvId=$j("#stvId").val();
	  	  var staId=$j("#staId").val();
		  var postData = {};
		  
		  postData["stv.id"] = stvId;
		  jumbo.showMsg(i18("OPERATING")); 
		  loxia.lockPage();
		  loxia.syncXhrPost($j("body").attr("contextpath") + "/json/printpurchaseinfo.json",postData);
		  loxia.unlockPage();
	   });
	   
		// 导出明细
		$j("#exportInventory").click(function(){
			var iframe = document.createElement("iframe");
			iframe.src = loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/invExportForPurchase.do?sta.id="+$j("#staId").val());
			iframe.style.display = "none";
			$j("#download").append($j(iframe));
		});
		
		// 导入
		$j("#importPurchaseInbound").click(function(){
			if(!/^.*\.xls$/.test($j("#file").val())){
				jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));// 请选择正确的Excel导入文件
				return false;
			}
			loxia.lockPage();
			$j("#importPurchaseInboundForm").attr("action",$j("body").attr("contextpath") + "/warehouse/importPurchaseInbound.do?sta.id=" + $j("#staId").val() + "&stv.id=" + $j("#stvId").val());
			$j("#importPurchaseInboundForm").submit();
		});
		
		//SN号导入
		$j("#sn_import").click(function(){
			if(true == starbucksFlag){
				jumbo.showMsg("无法执行导入，星巴克卡券需要作废，请扫SN号");
				return;
			}
			if(!/^.*\.xls$/.test($j("#file").val())){
				jumbo.showMsg("请选择正确的Excel导入文件");
				return;
			}
			
			var ids = $j("#tbl-orderNumber").jqGrid('getDataIDs');
			var snBarCode = "";
			for(var i=0;i<ids.length;i++){
				var barCode= $j("#tbl-orderNumber").getCell(ids[i],"barCode");
				var sku=getSkuRow(barCode);
				if(sku && !sku.isSnSku == ''){
					snBarCode += barCode+"/";
				}
			}
			loxia.lockPage();
			var staid = $j("#staId").val();
			$j("#importForm").attr("action",$j("body").attr("contextpath") + "/warehouse/staSnImportToWeb.do?staid="+staid+"&snBarCode="+snBarCode);
		    $j("#importForm").submit();
		     getValues = setInterval("getValue()",300);
		});
		
		
});

// 根据stvId显示带有建议库位的预上架列表
function tostvInfo(stvId,sta){
	loxia.lockPage();
	stvToggle();
	
	loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+"/json/stvinfo.json",{	"stv.id":stvId},
		{
			success:function (data) {
			    if(data&&data.msg){
			    	loxia.unlockPage();
			    	jumbo.showMsg(data.msg);	
			    }else{
			    	showLines(data);
			    	loxia.unlockPage();
			    	loxia.initContext($j("#stvlineListtable"));
			    }
			   }, 
				error:function(data){
				   loxia.unlockPage();
				jumbo.showMsg(i18("MSG_FAILURE"));	
               }			   
		}); 	  
  }   
// 显示带有建议库位的预上架列表
function showLines(data){	
	var rs =[],header=getLocationsTableHeader(),foot=getLocationsTableFoot();
	$j("#stvlineListtable tr:gt(0)").remove();
	$j.each(data,function(i, line) {
		var html=[],templete=getLocationsTableTemplete(line);
		html.push(getSkuInfo(line));
		html.push("<form invStatusId=\""+line.invStatusId+"\" skuId=\""+line.skuId+"\" staLineId=\""+line.stalineId+"\" total=\""+line.quantity+"\"  owner=\""+line.owner+"\" >");
		html.push(header);
		html.push(getLocationsTable(line));
		html.push(templete);
		html.push(foot);
		html.push(" </form></td></tr>");
    	rs.push(html.join(""));
    	  });
	  $j("#stvlineListtable tr:gt(0)").remove();
	  $j("#stvlineList").after(rs.join(""));
	  loxia.initContext($j("#div-stv"));
 }
// SKU信息
function getSkuInfo(line){	 
	var html=[];
	html.push("<tr invStatusId=\""+line.invStatusId+"\" skuId=\""+line.skuId+"\" isShelfManagement=\""+line.isShelfManagement
			+"\" strPoductionDate=\""+line.strPoductionDate+"\" strExpireDate=\""+line.strExpireDate +"\" validDate=\""+line.validDate
			+"\" staLineId=\""+line.stalineId+"\" total=\""+line.quantity+"\"  owner=\""+line.owner+"\" batchCode=\""+line.batchCode+"\" >");
	html.push("<td>"+line.barCode+"</td>");
	html.push("<td>"+line.skuCode+"</td>");
    html.push("<td>"+(!line.keyProperties?'&nbsp;':line.keyProperties)+"</td>");
    html.push("<td>"+line.skuName+"</td>");
    html.push("<td>"+line.supplierCode+"</td>");
    html.push("<td>"+(!line.intInvstatusName?'&nbsp;':line.intInvstatusName)+"</td>");
    html.push("<td class=\"decimal\">"+line.quantity+"</td><td>");
    return html.join("");
}
// 库位EditableTable表头
function getLocationsTableHeader(){	       
	    var html ="	<div loxiaType=\"edittable\" class=\"district\">"+
		"<table operation=\"add,delete\" append=\"0\" width=\"550\">"+
			"<thead>"+
				"<tr>"+
					"<th><input type=\"checkbox\" /></th>"+
					"<th width=\"150\">"+i18("ENTITY_LOCATION")+"</th>"+
					"<th>"+i18("ENTITY_LOCATION_CAPACITY")+"</th>"+
					"<th>"+i18("ENTITY_LOCATION_CURRENT")+"</th>"+
					"<th width=\"80\">生产日期<br/>格式：20140101</th>"+
					"<th width=\"80\">过期时间<br/>格式：20140101</th>"+
					"<th width=\"80\">"+i18("ENTITY_LOCATION_ADD")+"</th>"+
				"</tr>"+
			"</thead>";
	    return html;
}
// 循环显示库位编码-上架数量列表
function getLocationsTable(line){
	var tb=[];
	var url=$j("body").attr("contextpath") + "/findallavaillocations.json";
	tb.push(" <tbody>");
	$j.each(line.stvLines,function(i, item) {
		// var url=$j("body").attr("contextpath") +
		// "/findavaillocationforpurchase.json?sku.id="+line.skuId+"&stv.id="+$j("#stvId").val();;
		tb.push("<tr id=\""+item.id+"\" skuId=\""+line.skuId+"\" staLineId=\""+line.stalineId+"\"  owner=\""+line.owner+"\" >");
		tb.push("<td><input type=\"checkbox\" /></td>");
		var loc = item.locationCode == null ? '' : item.locationCode;
		tb.push("<td width=\"150\"><input name='locationCode' loxiaType=\"input\"  value=\""+loc+"\" checkmaster=\"checklocation\" aclist=\""+url+"\" /></td>");
		tb.push("<td>"+(item.locationAvailable==0||item.locationAvailable==null?i18("MSG_LOCATION_CAPACITY"):item.locationAvailable)+"</td>");
		tb.push("<td>"+(item.locationInventory==0?'':item.locationInventory)+"</td>");
		tb.push("<td width=\"80\"><input loxiaType=\"input\" name='strPoductionDate' /></td>");
		tb.push("<td width=\"80\"><input loxiaType=\"input\" name='strExpireDate' /></td>");
		tb.push("<td width=\"80\" class=\"decimal\"><input class=\"getcount\" name='quantity' value=\""+item.quantity+"\" checkmaster=\"checksjNum\"  loxiaType=\"number\" /></td>");
		tb.push("</tr>");
	   	});
	tb.push(" </tbody>");
	return tb.join("");	   
}
// 新增库位-上架数量的模版
function getLocationsTableTemplete(line){
	      // var url=$j("body").attr("contextpath") +
			// "/findavaillocationforpurchase.json?sku.id="+line.skuId+"&stv.id="+$j("#stvId").val();;
	      var url=$j("body").attr("contextpath") + "/findallavaillocations.json";
	      var tb2= " <tbody> "+
					"<tr invStatusId=\""+line.invStatusId+"\" skuId=\""+line.skuId+"\" staLineId=\""+line.stalineId+"\"  owner=\""+line.owner+"\">"+
					"<td><input type=\"checkbox\" /></td>"+
					"<td width=\"150\"><input name='locationCode' loxiaType=\"input\" checkmaster=\"checklocation\" aclist=\""+url+"\" /></td>"+
					"<td>&nbsp;</td>"+
					"<td>&nbsp;</td>"+
					"<td width=\"80\"><input loxiaType=\"input\" name='strPoductionDate' /></td>"+
					"<td width=\"80\"><input loxiaType=\"input\" name='strExpireDate' /></td>"+
					"<td width=\"80\" class=\"decimal\"><input class=\"getcount\" name='quantity' checkmaster=\"checksjNum\" loxiaType=\"number\" aclist=\""+url+"\" /></td>"+
				"</tr>"+
			"</tbody> ";
	    
	    return tb2;
   }
// 库位EditableTable.Foot
function getLocationsTableFoot(){
	var foot = " <tfoot>"+
				" <tr>"+
				"<td colspan=\"6\" class=\"decimal\">"+i18("LABEL_TOTAL")+"</td> "+
					" <td class=\"decimal\" id=\"total\" decimal=\"0\"></td>"+
				"</tr>"+
			"</tfoot> </table></div>";
	return foot;
}
function executeInventory(closeSta,stvId,staId){
	if(validateLocationQuantity()){
		var msg=true;
		var postData={},index=-1;
		$j.each($j("#stvlineList").nextAll(),function(i,tr){
			var skuId=$j(tr).attr("skuId"),staLineId=$j(tr).attr("staLineId"),owner=$j(tr).attr("owner"),invStatusId=$j(tr).attr("invStatusId");
			var batchCode = $j(tr).attr("batchCode");
			var isShelfManagement = $j(tr).attr("isShelfManagement");
			$j.each($j(tr).find("table tbody:eq(0) tr"),function(y,innerTr){
				if($j.trim($j(innerTr).find("input[name=quantity]").val())>0){
					var code2=$j.trim($j(innerTr).find("input[name=locationCode]").val());
				    if(!checklocation2(code2)){
				    	msg=false;
				    	return;
				    }
					index+=1;
					postData["stvlineList[" + index + "].location.code"]=$j.trim($j(innerTr).find("input[name=locationCode]").val());
					postData["stvlineList[" + index + "].quantity"]=$j.trim($j(innerTr).find("input[name=quantity]").val());
					postData["stvlineList[" + index + "].staLine.id"]=staLineId;
					postData["stvlineList[" + index + "].sku.id"]=skuId;
					postData["stvlineList[" + index + "].batchCode"]=batchCode;
					if(invStatusId != "" && invStatusId != null){
						postData["stvlineList[" + index + "].invStatus.id"]=invStatusId;
					}
					postData["stvlineList[" + index + "].owner"]=owner;
					if(isShelfManagement == "1"){
						postData["stvlineList[" + index + "].strExpireDate"]=$j.trim($j(innerTr).find("input[name=strExpireDate]").val());
						postData["stvlineList[" + index + "].strPoductionDate"]=$j.trim($j(innerTr).find("input[name=strPoductionDate]").val());
					}
					if($j(innerTr).attr("id")){
						postData["stvlineList[" + index + "].id"]=$j(innerTr).attr("id");	
					}
				}
			});
		});
		if(msg){
			postData["stv.id"]=stvId;
			postData["sta.intStatus"]=closeSta?10:5;
			executeinventorySend(postData);
		}
		
	}
}

function executeinventorySend(postData){
	loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+"/json/executeinventory.json",postData,
			{
				success:function (data) {
					if(data==null){
						 jumbo.showMsg(i18("MSG_FAILURE"));	
					}
					if(data && data.result != 'success'){
						if(data.result == 'wait'){
							var msg = data.message+'\n强行入库时则只能入残次品状态。\n你确定要强行入库吗？';
							if(window.confirm(msg)){
								postData["isForced"]=true;
								executeinventorySend(postData);
							} 
						}
						else{
							jumbo.showMsg(data.message);
						}
					}else{
						window.location=$j("body").attr("contextPath")+"/warehouse/returninboundentry.do";
					}	
				}, 
		       error:function(){
					 jumbo.showMsg(i18("MSG_FAILURE"));	
				}			   
			}); 
}
   
function closePO(){
	if(confirm(i18("MSG_CONFIRM"))){
			var stvId=$j("#stvId").val();
			var staId=$j("#staId").val();
			executeInventory(true,$j("#stvId").val(),$j("#staId").val()); 
	}
}
    /**
	 * 检查上架数量
	 * 
	 * @return {TypeName}
	 */
function validateLocationQuantity(){
	var errMsg=[];
	var re = /^[1-9]+[0-9]*]*$/;
	$j.each($j("#stvlineList").nextAll(),function(i, tr) {
		if($j(tr).attr("total")!=$j(tr).find("#total").html()){
			errMsg.push($j(tr).find("td:eq(3)").html()+i18("MSG_ERROR_STALINE_QTY"));
		}
		var isShelfManagement = $j(tr).attr("isShelfManagement");
		$j.each($j(tr).find("tr:gt(0)"),function(i,e){
			var status=$j(e).find(".invStatus").val();
			var location=$j(e).find("input[name=locationCode]");
			var quantity=$j(e).find("input[name=quantity]");
			if((parseInt(i) + 1) < $j(tr).find("tr:gt(0)").length && isShelfManagement == "1"){
				var strPoductionDate = $j(e).find("input[name=strPoductionDate]").val().trim();
				var strExpireDate = $j(e).find("input[name=strExpireDate]").val().trim();
				if(strPoductionDate == "" && strExpireDate == ""){
					errMsg.push("["+$j(tr).find("td:eq(3)").html() + "]保质期商品必须填写生成日期或者过期时间!");
				} else if(strPoductionDate != "" && (strPoductionDate.length != 8 || !re.test(strPoductionDate))){
					errMsg.push("["+$j(tr).find("td:eq(3)").html() + "]生成日期格式不正确!");
				} else if(strExpireDate != "" && (strExpireDate.length != 8 || !re.test(strExpireDate))){
					errMsg.push("["+$j(tr).find("td:eq(3)").html() + "]过期时间格式不正确!");
				}
			}
			if(location&&$j.trim($j(location).val()).length>0&&quantity&&$j.trim($j(quantity).val()).length>0&&status==""){
				errMsg.push($j(tr).find("td:eq(3)").html()+i18("LABEL_LINE",i+1)+i18("INVALID_MAND",i18("ENTITY_INVENTORY_STATUS")));
			}
		});
	});
	if(errMsg.length>0){
		jumbo.showMsg(errMsg.join("<br />")); 
		return false;
	}
	return true;
}

// 库位校验
function checklocation(value,obj){
	var code=$j.trim(value);	
	if(code.length==0){
		return i18("INVALID_ENTITY_EMPTY",i18("ENTITY_LOCATION"));
	}
   var data={};
   data["stvid"]=$j("#stvId").val();
   data["locationCode"]=code;
	var rs = loxia.syncXhr(window.parent.$j("body").attr("contextpath")+"/findlocationtranstypebycode.json",data);
    var ex = rs.exception;
    var result = rs.result;
    if(rs.exception){
		$j(obj.element).parent().next().html('');
    	$j(obj.element).parent().next().next().html('');
		return i18("INVALID_NOT_EXIST",i18("ENTITY_LOCATION"));
	}else{ 
		if (result == 'success'){
			$j(obj.element).parent().next().html(rs.location.capacity==0?i18("MSG_LOCATION_CAPACITY"):rs.location.capacity);
		    $j(obj.element).parent().next().next().html(rs.location.qty);
			return loxia.SUCCESS;
		}else if(result == 'error'){
			jumbo.showMsg(rs.message); 
			return rs.message;
		}
	}
}

function checklocation2(code){ 
	var msg=false;
	
	var data={};
	data["stvid"]=$j("#stvId").val();
	data["locationCode"]=code;
	var rs = loxia.syncXhr(window.parent.$j("body").attr("contextpath")+"/findlocationtranstypebycode.json",data);
	var ex = rs.exception;
	var result = rs.result;
	if(rs.exception){
	   msg==false;
	   return;
	}else{ 
	   if (result == 'success'){
		   msg==true;
		   return true;
	   }else if(result == 'error'){
			jumbo.showMsg(code+rs.message);
			msg==false;
			return;
	}
   }
			
	return msg;
}

function checksjNum(value,obj){
		$j("form[stalineid="+$j(obj.element).parent().parent().attr("stalineid")+"]").find("#total").html("");
	    var value=parseInt($j.trim(value)),ocu =$j(obj.element).parent().prev().text(),cap=$j(obj.element).parent().prev().prev().text();
	   if(isNaN(value)||value<0){
	    	return i18("INVALID_NUMBER");	
	    }
	   if(isNaN(cap)){
	    	return loxia.SUCCESS;
	    }
	    if(($j.trim(ocu).length==0&&cap<value)||($j.trim(ocu).length>0&&(cap-ocu<value))){
	   		return i18("MSG_ERROR_LOCATION_QTY");	
	    }
	   return loxia.SUCCESS;
   }

function initDialogGift(staLineId,sku){
	$j("#dialog_gift_error").text("");
	$j("#showGiftList").text("查看");
	$j("#warrantyCardNumber").text("0");
	$j("#warrantyCardList").text("");
	$j("#staLineId").val(staLineId);
	$j("#dialog_sku").text(sku);
}

function resetSkuInfo(){
	$j("#add_barCode").text("");
	$j("#addBarCode").val("");
	$j("#add_jmCode").text("");
	$j("#add_skuName").text("");
	$j("#add_status").val("").addClass("hidden");
	$j("#tip").addClass("hidden");
	$j("#addSku").addClass("hidden");
}

function importReturn(){
	window.location=loxia.getTimeUrl($j("body").attr("contextpath")+"/warehouse/returninboundentry.do");
}
//获取从return-inbound-result.jsp传过来的exl的值
function getValue (){
	 var tempLists = $j("#tempList").html();
	 var errorBarCodeList = "",errorSnListList = "",errorStatusList = "";
	 var count = parseInt($j("#barCodeCount").val()||"1",10);
	 if(tempLists != ""){
		  document.getElementById("tempList").innerHTML = "";
		  clearInterval(getValues);
		  tempLists = eval("(" + tempLists + ")");
		  for(var index in tempLists){
			  var barCode = tempLists[index].barCode;//条形码
			  var status = tempLists[index].intInvstatusName;//库存状态
			  var statusId = status == "良品" ? 1 : (status == "残次品" ? 2 : (status == "良品不可销售" ? 61 : (status == "待处理品" ? 81 : (status == "待报废" ? 161 : (status == "临近保质期" ? 324:"")))));
			  var snNumber = tempLists[index].snNumber;//SN号
			  var errorSn = tempLists[index].errorSnList;//错误SN
			  var errorBarCode = tempLists[index].errorBarCode;//错误BarCode
			  var errorStatus = tempLists[index].errorStatus;//错误库存状态
			  //alert("条形码:"+barCode+",库存状态:"+status+",SN号:"+snNumber+",错误SN:"+errorSn+",错误BarCode:"+errorBarCode+",错误库存状态的BarCode:"+errorStatus);
			  if(barCode != null && status != null && snNumber != null && errorBarCode == null){
				//校验通过的数据
				  var sku=getSkuRow(barCode);
				  if(sku){
					  var rowId = "";
						// 计划量
						var skuPlanQuantity = 0;
						// 已经执行量
						var skuCompleteQuantity = 0;
						$j.each($j("#tbl-orderNumber").jqGrid("getRowData"),function(i,row){
							if(row['barCode'] == sku.barCode){
								var planQuantity = parseInt(row['quantity'] == "" ? 0 : row['quantity']);
								var completeQuantity = (row['completeQuantity'] == "" ? 0 : row['completeQuantity']);
								// 查看本次执行量
								var addQuantity = $j("#"+row["id"]+" td[aria-describedby$='addQuantity'] input").val();
								completeQuantity = parseInt(completeQuantity,10) + parseInt(addQuantity == "" ? 0 : addQuantity);
								//alert(statusId+"--"+row["invStatusId"]);
								if(statusId == row["invStatusId"]){
									if(row['quantity'] == "0"){
										rowId = row["id"];
									} else if(planQuantity >= (completeQuantity + count)){
										rowId = row["id"];
									}
								}
								skuPlanQuantity = skuPlanQuantity  + planQuantity;
								skuCompleteQuantity = parseInt(skuCompleteQuantity,10) + completeQuantity;
							}
						});
						if(skuPlanQuantity < (skuCompleteQuantity + 1)){
							jumbo.showMsg("条码为：" + sku.barCode + "的商品执行量超出计划量！");
							clearInterval(getValues);
							return false;
						}
						if(rowId == ""){
							rowId = sku['id'] + "_" + statusId;
							var row = sku;
							row['id']=rowId;
							row['completeQuantity'] = 0;
							row['invStatusId'] = statusId;
							row['quantity'] = 0;
							row['addQuantity'] = (isNaN(count)?1:count);
							row['intInvstatusName'] =status;
							$j("#tbl-orderNumber").jqGrid('addRowData',rowId,row);
							loxia.initContext($j("#tbl-orderNumber"));
						} else {
							var addQuantity=$j.trim($j("#tbl-orderNumber tr[id="+rowId+"] :input").val());
							if(addQuantity.length==0){ 
								addQuantity=0;
							}else {
								addQuantity=parseInt(addQuantity,10);
							}
							sku['addQuantity']= addQuantity - 0 + (isNaN(count)?1:count);
							$j("#"+rowId+" input").val(addQuantity - 0 + (isNaN(count)?1:count));
						}
						if(statusId == rowId.substring(rowId.indexOf("_")+1, rowId.length)){
							var sns = $j("#tbl-orderNumber tr[id="+rowId+"]").data("sns");
							if(!sns){
								sns = new Array();
							}
							sns[sns.length] = snNumber;
							$j("#tbl-orderNumber tr[id="+rowId+"]").data("sns",sns);
						}else{
							var sns2 = $j("#tbl-orderNumber tr[id="+sku["id"]+"]").data("sns");
							if(!sns2){
								sns2 = new Array();
							}
							sns2[sns2.length] = snNumber;
							$j("#tbl-orderNumber tr[id="+sku["id"]+"]").data("sns",sns2);
						}
						//resetBarCodeForm(rowId);
						loxia.initContext($j("#tbl-orderNumber"));
						//resetSkuInfo();
				  }
			  }else{
				  if(errorBarCode != null){
					  errorBarCodeList += errorBarCode+"/";
				  }
				  if(errorSn != null){
					  errorSnListList += errorSn+"/";
				  }
				  if(errorStatus != null){
					  errorStatusList += errorStatus+"/";
				  }
				//校验不通过的数据
			  }
		  }
		  if(errorBarCodeList != ""){
			  errorBarCodeList ="1.条形码："+errorBarCodeList.substring(0, errorBarCodeList.length-1)+"错误或不是待收货列表的SN号商品;";
		  }
		  if(errorSnListList != ""){
			  errorSnListList ="2.SN号："+errorSnListList.substring(0, errorSnListList.length-1)+"错误或已统计;";
		  }
		  if(errorStatusList != ""){
			  errorStatusList ="3.条形码："+errorStatusList.substring(0, errorStatusList.length-1)+"库存状态错误;";
		  }
		  if(errorBarCodeList != "" || errorSnListList != "" || errorStatusList != "" ){
			  errorTipOK(errorBarCodeList+errorSnListList+errorStatusList);
		  }
	 }
}
$j("#nikeRFID").keypress(function(event){
	if(event.keyCode === 13){
		loxia.syncXhr(window.parent.$j("body").attr("contextpath")+"/sessionNikeRFID.json",{"nikeRFIDCode":$j("#nikeRFID").val()});
	}
});
function errorTipOK(text) {
	$j("#dialog_error_ok").removeClass("hidden");
	$j("#error_text_ok").html("<font style='text-align:center;font-size:25px' color='red'>"+text+"</font>");
	$j("#dialog_error_ok").dialog("open");
	$j("#dialog_error_close_ok").blur();
}