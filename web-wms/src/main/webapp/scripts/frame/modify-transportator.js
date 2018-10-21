$j.extend(loxia.regional['zh-CN'],{
	BARCODE : "条形码",
	JMSKUCODE : "商品编码",
	SKUNAME : "商品名称",
	QUANTITY : "计划执行数量",
	COMPLETE_QUANTITY : "已执行数量",
	ORDER_INFO : "作业单明细",
	LPCODE_SELECT : "请选择新物流供应商",
	INPUT_NEW_EXPRESSNO : "第 {0} 行 请输入新快递单号",
	INPUT_WEIGHT : "第 {0} 行 请输入重量",
	NEW_EXPRESSNO_ERROR : "新快递单号  {0}不符合所选物流供应商的快递单号规则",
	MODIFY_SUCCESS : "转物流成功！",
	MODIFY_FAILED : "转物流失败，填写信息错误或快递单号已经存在！",
	CHECK_WEIGHT : "请输入小于150千克的重量",
	TRACKINGNO_REPEAT_ERROR : "行 {0} 快递单号已存在",
	ORDER_NOT_FOUND : "作业单未找到"
});

function checkWeight(obj){
	if(!/^[0-9]*\.{0,1}[0-9]*$/.test(obj)){
		return i18("INVALID_DATA");
	}
	value = parseFloat(obj);
	if(value == 0){
		return i18("INVALID_EMPTY_DATA");
	}else if(value > 150){
		return i18("CHECK_WEIGHT");
	}	
	return loxia.SUCCESS;
}

function checkTrackingNo(){
	return loxia.SUCCESS;
}

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function clearInfo(){
		$j("#tbl-orderInfo tr:gt(0)").remove();
		$j("#expressNos").text("");
		$j("#aboutOrder").text("");
		$j("#aboutStore").text("");
		$j("#select option:eq(0)").attr("selected","selected");
		$j("input.newExpress").val("");
		$j("input.weight").val("");
}

$j(document).ready(function (){
	//init tranports 
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransportatorsName.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#select"));
	}

	$j("#tbl-orderInfo").jqGrid({
		//url:url,
		datatype: "json",
		colNames: ["ID",i18("BARCODE"),i18("JMSKUCODE"),"扩展属性",i18("SKUNAME"),i18("QUANTITY"),i18("COMPLETE_QUANTITY")],
		//colNames: ["ID","条形码","JM SKU编码","商品名称","计划执行数量","已执行数量"],
		colModel: [
		           {name: "id", index: "id", hidden: true},
		           {name: "barCode", index: "barCode", width: 150, resizable: true},
		           {name: "jmcode", index: "jmcode",width: 150, resizable: true},
		           {name: "keyProperties", index: "keyProperties", width: 100, resizable: true},
		           {name: "skuName", index: "skuName", width: 250, resizable: true},
		           {name: "quantity", index: "quantity", width: 100, resizable: true},
		           {name: "completeQuantity", index: "completeQuantity", width: 100, resizable: true},],
		caption: i18("ORDER_INFO"),//作业单明细
		rowNum:-1,
	   	//rowList:[5,10,15,20,25,30],
	   	sortname: 'jmcode',
	   	height:"auto",
	   	rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pager",
	    multiselect: false,
		sortorder: "desc",
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	
	$j("#tbl-logisticsChange").jqGrid({
		//url:url,
		datatype: "json",
		colNames: ["ID","快递面单","物流服务商","体积(m³)","重量(kg)"],
		colModel: [
		           {name: "id", index: "id", hidden: true},
		           {name: "trackingNo", index: "trackingNo", width: 150, resizable: true},
		           {name: "lpcode", index: "lpcode",width: 150, resizable: true},
		           {name: "volume", index: "volume", width: 100, resizable: true},
		           {name: "weight", index: "weight", width: 250, resizable: true}],
		caption: i18("原始快递面单列表"),
		rowNum:-1,
	   	//rowList:[5,10,15,20,25,30],
	   	sortname: 'jmcode',
	   	height:"auto",
	   	rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pager",
	    multiselect: false,
		sortorder: "desc",
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#tbl-logisticsChangeNew").jqGrid({
		//url:url,
		datatype: "json",
		colNames: ["ID","快递面单","物流服务商","体积(m³)","重量(kg)"],
		colModel: [
		           {name: "id", index: "id", hidden: true},
		           {name: "newTrackingNo", index: "newTrackingNo", width: 150, resizable: true},
		           {name: "newLpcode", index: "newLpcode",width: 150, resizable: true},
		           {name: "volume", index: "volume", width: 100, resizable: true},
		           {name: "weight", index: "weight", width: 250, resizable: true}],
		caption: i18("变更后的快递面单列表"),
		rowNum:-1,
	   	sortname: 'jmcode',
	   	height:"auto",
	   	rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pager",
	    multiselect: false,
		sortorder: "desc",
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	 var staId = null;
	$j("#expressOrder").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			var expressOrder = $j("#expressOrder").val();
			if(expressOrder!=""){
				var postData = {};
				postData["trackingNo"] = expressOrder;
				var re = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/getStaListByTrackingNo.do",postData);
				$j("#aboutOrder").text(re.refSlipCode);
				$j("#aboutStore").text(re.shopId);
				if(re.refSlipCode==null||re.refSlipCode == ""){
					$j("#turnLogistics").attr("disabled","disabled");
					clearInfo();
					$j("#new").addClass("hidden");
					$j("#expressNo").addClass("hidden");
					jumbo.showMsg(i18("ORDER_NOT_FOUND"));//作业单未找到
				}else{
					$j("#turnLogistics").removeAttr("disabled");
					postData["stacode"] = re.stacode;
					var trackingnos = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/findRelevanceTrackingno.do",postData);
					$j("#expressNos").html(trackingnos.result);
					staId = re.staId;
					$j("#tbl-orderInfo").jqGrid('setGridParam',{url:window.$j("body").attr("contextpath")+"/json/findStaLineListByStaId.do",page:1,postData:{"staId":staId}}).trigger("reloadGrid");
					$j("#new").removeClass("hidden");
					$j("#expressNo").removeClass("hidden");
				}
			}
		}
	});
	$j("#expressOrder1").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			var  trackingNo= $j("#expressOrder1").val();
			if(trackingNo!=""){
				var postData = {};
				postData["trackingNo"] = trackingNo;
				var re = loxia.syncXhrPost($j("body").attr("contextpath") + "/getDeliveryByTNo.json",postData);
				$j("#tbl-logisticsChange").jqGrid("clearGridData");
				$j("#tbl-logisticsChangeNew").jqGrid("clearGridData");
				var dataRow = {id : 1,trackingNo :re.trackingNo ,lpcode : re.lpCode, volume : re.volume, weight : re.weight  }; 
				var dataRow1 = {id : 1,newTrackingNo :re.newTrackingNo ,newLpcode : re.newLpCode, volume : re.volume, weight : re.weight  }; 
				 if(re.newTrackingNo!=null&&re.relevance=="isTrue"){
					jumbo.showMsg("不可变更物流");
					$j("#tbl-logisticsChange").jqGrid("addRowData", 0, dataRow, "first");  //更新原始快递单列表
					$j("#tbl-logisticsChangeNew").jqGrid("addRowData", 0, dataRow1, "first"); //更新变更后快递单列表
				}
				if(re.newTrackingNo!=null&&re.relevance=="isFalse"){
					$j("#tbl-logisticsChange").jqGrid("addRowData", 0, dataRow, "first");  //更新原始快递单列表
					$j("#tbl-logisticsChangeNew").jqGrid("addRowData", 0, dataRow1, "first"); //更新变更后快递单列表
					//如果是电子面单，并且没有关联原始物流面单，执行系统分配的面单和用户扫入的面单匹配
					var staId=re.staId;
					var newLpcode=re.newLpCode;
					var packId=re.packId;
					var dt = new Date().getTime();
					var url = $j("body").attr("contextpath") + "/printDeliveryInfoByStaId.json?staId="+staId+"&newLpcode="+newLpcode+"&rt="+dt+"&packId="+packId;
					printBZ(loxia.encodeUrl(url),false);
					jumbo.showMsg("电子面单打印中...请稍等");
					//光标跳转到原始面单输入框
					$j("#expressOrder2").focus();
					$j("#expressOrder2").keydown(function(evt){
						if(evt.keyCode === 13){
							evt.preventDefault();
							//光标跳转到新面单输入框，用户扫描后匹配系统面单
							$j("#expressOrder3").focus();
							$j("#expressOrder3").keydown(function(evt){
								if(evt.keyCode === 13){
									evt.preventDefault();
								var  trackingNo= $j("#expressOrder3").val();
									if(trackingNo==re.newTrackingNo){
										$j("#expressOrder1").val("");
										$j("#expressOrder2").val("");
										$j("#expressOrder3").val("");
										$j("#tbl-logisticsChange").jqGrid("clearGridData");
										$j("#tbl-logisticsChangeNew").jqGrid("clearGridData");
										jumbo.showMsg("物流变更完成");
										$j("#expressOrder1").focus();
									}
								}
							});
						}
					});
				
				}else if(re.newTrackingNo==""){
					$j("#expressOrder2").focus();
					jumbo.showMsg("请输入原始面单号")
					$j("#expressOrder2").keydown(function(evt){
						if(evt.keyCode === 13){
							evt.preventDefault();
							$j("#expressOrder3").focus();
							jumbo.showMsg("请输入新面单号");
							$j("#expressOrder3").keydown(function(evt){
								if(evt.keyCode === 13){
									evt.preventDefault();
								var  newTrackingNo= $j("#expressOrder3").val();
								var  trackingNo= $j("#expressOrder2").val();
								var  PostData={};
								postData["trackingNo"] = trackingNo;
								postData["newTrackingNo"] = newTrackingNo;
								if(newTrackingNo!=null&&newTrackingNo!=""){
								var re = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/modifyDeliveryByTrackingNo.json",postData);
								if(re.result=="success"){
										jumbo.showMsg("物流变更完成");
										$j("#expressOrder1").val("");
										$j("#expressOrder2").val("");
										$j("#expressOrder3").val("");
										$j("#tbl-logisticsChange").jqGrid("clearGridData");
										$j("#tbl-logisticsChangeNew").jqGrid("clearGridData");
										$j("#expressOrder1").focus();
								}else{
									jumbo.showMsg("没有找到变更配置信息,请添加配置信息后重试")
								}
									}
								}
							});
						}
					});
				}
			}
		}
	});
	$j("#turnLogistics").click(function(){
		var msg = "";
		var lpCode = $j("#select option:selected").val();
		if(lpCode == ""){
			jumbo.showMsg(i18("LPCODE_SELECT"));//请选择新物流供应商
			return;
		}
		//删除多余空行
		$j("#tbl-newExpressInfo tbody>tr").each(function(i,tag){
			var isRemove = true;
			$j(tag).find("input[type='text']").each(function(j,input){
				if($j(input).val() != ''){
					isRemove = false;
				}
			});
			if(isRemove && i != 0){
				$j(tag).remove();
			}
		});
		
		var newExpressNos = $j("input.newExpress");
		var weights = $j("input.weight");
		
		var errors=loxia.validateForm("createForm");
		
		$j("input.newExpress").each(function(i,data){
			if(i>0){
				var now = $j("input.newExpress:lt("+i+")");
				for(var j=0;j<now.length;j++){
					var v = now[j].value;
					var d = $j(data).val();
					if(now[j].value == $j(data).val()){
						errors.push(i18("TRACKINGNO_REPEAT_ERROR",(i+1)));
						$j(data).addClass("ui-loxia-error");
						$j(data).parent().parent().addClass("ui-state-highlight")
						return;
					}
					$j(data).removeClass("ui-loxia-error");
					$j(data).parent().parent().removeClass("ui-state-highlight")
				}
			}
		});
		
		if(errors.length>0){
			jumbo.showMsg(errors.join("\n"));
			return false;
		}
		
		var rs = false;
		var we = false;
		$j("input.weight").each(function(i,data){
			if(!checkWeight($j(data).val())){
				rs = true;
			}
		});
		
		var postData = {};
		for (i=0; i < weights.length; i++) {
			var trackingNo = newExpressNos[i].value;
			var weight = weights[i].value;
			
   			postData["packageInfo["+i+"].trackingNo"]=trackingNo;
   			postData["packageInfo["+i+"].weight"]=weight;
		}
		postData["staId"] = staId;
		postData["lpCode"] = lpCode;
		
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/toTurnLogistics.json",postData);
		msg = result.msg;
		
		if(msg!=""){
			if(msg != "MODIFY_FAILED"){
				jumbo.showMsg(i18(msg,result.tracking));
			}else if(result.tracking != ""){
				jumbo.showMsg(i18(msg,result.tracking));
			}else{
				jumbo.showMsg(i18(msg));
				clearInfo();
				$j("#expressOrder").val("");
				$j("#new").addClass("hidden");
				$j("#expressNo").addClass("hidden");
			}
		}
	});
	
	$j("#expressOrder").focus(function(){
		$j("#expressOrder").select();
	});
	$j("#expressOrder").focus();
});