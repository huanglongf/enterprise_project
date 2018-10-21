var extBarcode = null;
var outboundSn = null;
var getValues = null;
$j.extend(loxia.regional['zh-CN'],{
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
	ENTITY_SKU_BARCODE			:	"条形码",
	ENTITY_SKU_JMCODE			:	"商品编码",
	ENTITY_SKU_KEYPROP			:	"扩展属性",
	ENTITY_SKU_NAME				:	"商品名称",
	ENTITY_SKU_SUPCODE			:	"货号",
	ENTITY_STALINE_TOTAL		:	"计划量执行量",
	ENTITY_STALINE_COMPLETE		:	"已执行量",
	ENTITY_STALINE_CURRENT		:	"本次执行量",
	INVALID_NOT_EXIST 			:	"{0}不正确/不存在",
	MSG_NO_ACTION				:	"本次收货数量为0!",
	TABLE_CAPTION_STA			:	"退换货待收货列表",
	TABLE_CAPTION_STALINE		:	"退换货待收货明细表",
	ENTITY_STALINE_SNS			:	"SN序列号",			
	INVALID_SN					:	"请按正确的格式填写SN序列号",
	INVALID_SN_LINE				:	"请按正确的格式填写第{0}行SN序列号",
	CORRECT_FILE_PLEASE         :   "请选择正确的Excel导入文件",
	WARRANTY_CARD_TYPE_NO		:	"1",
	WARRANTY_CARD_TYPE_YES_NO_CHECK	:	"3",
    COACH_OWNER1				:	"1coach官方旗舰店",
    COACH_OWNER2				:	"1Coach官方商城",
//	LPCODE 						: 	"物流服务商",
	
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
function showdetail(obj){
	$j("#dialog_error_ok").dialog({title: "错误信息", modal:true, autoOpen: false, width: 470});
	$j("#dialog_error_ok").addClass("hidden");
	$j("#addBarCode").addClass("hidden");
	$j("#addSku").addClass("hidden");
	$j("#add_status").addClass("hidden");
	$j("#barCode").val("");
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
		staToggle();
		$j("#tbl-orderNumber").jqGrid('setGridParam',
			{url:$j("body").attr("contextpath")+"/findStalinelist.json?staId="+staId}).trigger("reloadGrid",[{page:1}]);
		$j("#createTime").html(sta.createTime);
		$j("#staCode").html(sta.code);
		$j("#po").html(sta.refSlipCode);
		$j("#owner").html(sta.owner);
		$j("#supplier").html(sta.supplier);
		$j("#status").html($j(obj).parent().next().html());
		$j("#left").html(sta.remnant);
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
	$j("#dialog-sns-view").dialog({title: "SN序列号", modal:true, autoOpen: false, width: 400});
	$j("#dialog_gift").dialog({title: "COACH 保修卡保存", modal:true,closeOnEscape:false, autoOpen: false, width: 550});
	$j("#needSn").click(function(){
		$j("#tbl-orderNumber .ui-button").toggle();
	});
	
	$j("#shortcut_code").focus();
	
	// 初始化物流商信息
//	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransportator.do");
//	for(var idx in result){
//		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selLpcode"));
//	}
	
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findInventoryStatus.do?isAvailable=true");
	for(var idx in result){
		$j("<option value='" + result[idx].statusId + "'>"+ result[idx].statusName +"</option>").appendTo($j("#add_status"));
	}
	
	jumbo.loadShopList("companyshop");
	initShopQuery("companyshop","innerShopCode");
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	
	//查询
	$j("#search").click(function(){
		var postData = loxia._ajaxFormToObj("form_query");
//		postData["isStvId"]=true;
		var url = $j("body").attr("contextpath")+"/json/findStaProcurementReturnInboundList.json";
		$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
			url:url,page:1,postData:postData}).trigger("reloadGrid");
	});

	//重置
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val(0);
		$j("#shortcut_code").val("");
		$j("#isNeedInvoice").val("请选择");
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
	var status=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"whSTAProcessStatus"});
	var trueOrFalse=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"trueOrFalse"});
	$j("#tbl-inbound-purchase").jqGrid({
		url:$j("body").attr("contextpath")+"/findStaProcurementReturnInboundList.json",
		datatype: "json",
		colNames: ["ID","STVID","作业单创建时间","作业单号","执行情况","相关单据号","负向采购单号","OWNER","店铺名",
			],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},
		           {name: "stvId", index: "stvId", hidden: true,sortable:false},
		           {name: "createTime", index: "createTime", width: 200, resizable: true,sortable:false},
		           {name: "code", index: "code",formatter:"linkFmatter", formatoptions:{onclick:"showdetail"}, width: 200, resizable: true,sortable:false},
		           {name: "processStatus", index: "status", width: 100, resizable: true,formatter:'select',editoptions:status,sortable:false},
		           {name: "refSlipCode", index: "refSlipCode", width: 150, resizable: true,sortable:false},
		           {name: "slipCode2", index: "slipCode2", width: 150, resizable: true,sortable:false},
		           {name: "owner", index: "owner", hidden: true,sortable:false},
		           {name: "channelName", index: "channelName", width: 150, resizable: true,sortable:false},
	               ],
		caption: "采购退货入库列表",
	   	sortname: 'createTime',
	   	sortorder: "desc",
	   	multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pager",
	   	height:jumbo.getHeight(),
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
		});
	
	// STA作业申请单行
	$j("#tbl-orderNumber").jqGrid({
		datatype: "json",
		colNames: ["ID","STAID","SKUID","WARRANTYCARDTYPE","OWNER","INTSTATUS","INTERFACETYPE","SNTYPE","SPTYPE","skuCost",i18("ENTITY_SKU_BARCODE"),i18("ENTITY_SKU_JMCODE"),i18("ENTITY_SKU_KEYPROP"),
			i18("ENTITY_SKU_NAME"),i18("ENTITY_SKU_SUPCODE"),"库存状态",i18("ENTITY_STALINE_TOTAL"),
			i18("ENTITY_STALINE_COMPLETE"),i18("ENTITY_STALINE_CURRENT"),/* i18("ENTITY_STALINE_SNS"), */i18("ENTITY_STALINE_SNS")],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},	
		           {name: "sta.id", hidden: true,sortable:false},	
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
	               {name: "isSnSku", width: 120, resizable: true,sortable:false, formatter:"boolButtonFmatter", formatoptions:{"buttons":{label:"查看SN号", onclick:"showSnDialog(this,event);"}}},
	               ],
		caption: i18("TABLE_CAPTION_STALINE"),
	   	sortname: 'barCode',
	    multiselect: false,
		viewrecords: true, 
	     postData:{"columns":"id,sta.id,skuId,invStatusId,interfaceType,snType,spType,warrantyCardType,owner,skuCost,isSnSku,barCode,jmcode,keyProperties,skuName,jmskuCode,intInvstatusName,quantity,completeQuantity"},
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
		var barcode = $j.trim($j("#barCode").val());
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
							if(row['quantity'] == "0"){
								rowId = row["id"];
							} else if(planQuantity >= (completeQuantity + count)){
								rowId = row["id"];
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
					var row = sku;
					row['id']=rowId;
					row['completeQuantity'] = 0;
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
	
	// 商品条码/SN号：
	loxia.byId("barCodeCount").setEnable(false);
	$j("#barCode").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			var barcode = $j.trim($j("#barCode").val());
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
						$j("#addSku").trigger("click");
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
	
//确认收货		
$j("#receiveAll").click(function(){
		var postData=getPostData($j("#staId").val());
		if(postData['error']){
			jumbo.showMsg(postData['error']);
		}else if(!postData['stv.stvLines[0].sku.id']){
			jumbo.showMsg(i18("MSG_NO_ACTION"));
			return true;
		} else if(confirm(i18("MSG_CONFIRM"))){
			loxia.lockPage();
			loxia.asyncXhrPost($j("body").attr("contextpath") + "/procurementReturnInboundReceipt.json",
				postData,
				{
				success:function(data){
					if(data){
						if(data.result=="success"){
							jumbo.showMsg(i18("MSG_SUCCESS"));	
							window.location.reload();
						}else if(data.result=="error"){
							loxia.unlockPage();
							jumbo.showMsg(i18("MSG_FAILURE")+data["message"]);
						}
					} else {
						loxia.unlockPage();
						jumbo.showMsg(i18("MSG_FAILURE"));
					}
				},
				error:function(){loxia.unlockPage(); jumbo.showMsg(i18("MSG_FAILURE"));}
				});
		} 
	});	
	//返回
	$j("#backto").click(function(){
		window.location.reload();
		$j("#div-sta-detail").addClass("hidden");
		$j("#div-stv").addClass("hidden");
		$j("#div-sta-list").removeClass("hidden");
		
		
	});
  //SN号导入
 $j("#sn_import").click(function(){
			if(true == starbucksFlag){
				jumbo.showMsg("无法执行导入，星巴克卡券需要作废，请扫SN号");
				return;
			}
			if(!/^.*\.xls$/.test($j("#file").val())){
				jumbo.showMsg(i18(CORRECT_FILE_PLEASE));
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
		
//		//关闭错误信息
		$j("#dialog_error_close").click(function(){
			$j("#dialog_error_ok").dialog("close");
			$j("#dialog_error_ok").addClass("hidden");
		});
});

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
			  var snNumber = tempLists[index].snNumber;//SN号
			  var errorSn = tempLists[index].errorSnList;//错误SN
			  var errorBarCode = tempLists[index].errorBarCode;//错误BarCode
			  var errorStatus = tempLists[index].errorStatus;//错误库存状态
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
									if(row['quantity'] == "0"){
										rowId = row["id"];
									} else if(planQuantity >= (completeQuantity + count)){
										rowId = row["id"];
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
						
							var addQuantity=$j.trim($j("#tbl-orderNumber tr[id="+rowId+"] :input").val());
							if(addQuantity.length==0){ 
								addQuantity=0;
							}else {
								addQuantity=parseInt(addQuantity,10);
							}
							sku['addQuantity']= addQuantity - 0 + (isNaN(count)?1:count);
							$j("#"+rowId+" input").val(addQuantity - 0 + (isNaN(count)?1:count));
						
						
							var sns2 = $j("#tbl-orderNumber tr[id="+sku["id"]+"]").data("sns");
							if(!sns2){
								sns2 = new Array();
							}
							sns2[sns2.length] = snNumber;
							$j("#tbl-orderNumber tr[id="+sku["id"]+"]").data("sns",sns2);
						
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

function errorTipOK(text) {
	$j("#dialog_error_ok").removeClass("hidden");
	$j("#error_text_ok").html("<font style='text-align:center;font-size:25px' color='red'>"+text+"</font>");
	$j("#dialog_error_ok").dialog("open");
	$j("#dialog_error_close_ok").blur();
}