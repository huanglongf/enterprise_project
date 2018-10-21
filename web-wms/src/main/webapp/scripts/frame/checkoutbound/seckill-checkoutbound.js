$j.extend(loxia.regional['zh-CN'],{
	BATCHCODE:"批次号",
	LPCODE:"物流",
	STATUS:"状态",
	PLANSTA:"计划单据数",
	PLANSKU:"计划商品数",
	CHECKEDSTA:"核对单据数",
	CHECKEDSKU:"核对商品数",
	CREATETIME:"创建时间",
	PICKINGLIST_SHOW:"配货清单列表",
	DELIVERYINFO:"物流面单打印中，请等待...",
	CHECKED_LIST : "待核对列表",
	SKUCODE : "SKU编码",
	LOCATIONCODE : "库位编码预览",
	SKUNAME : "商品名称",
	JMSKUCODE : "货号",
	BARCODE : "条形码",
	QUANTITY : "计划执行数量",
	COMPLETEQUANTITY : "已执行数量",
	KEY_PROPS:"扩展属性",
	
	CANCELSTALIST:"取消单据列表",
	PGINDEX:"箱号",
	CODE:"作业单号",
	SLIPCODE:"相关单据号",
	SHOPOWNER:"店铺",
	OPERATION:"处理",
	DELETE:"删除",
	
	INPUT_WEIGHT : "请输入货物重量",
	NUMBER_RULE : "货物不是一个合法的数字或精度要求不符合要求",
	WEIGHT_LAGGER : "货物重量不得超过150KG"
});
function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

//页面加载完绑定方法
var barCodeList;
var rswh;//仓库基本信息 是否允许手动称重
$j(document).ready(function(){
	$j("#pickId").focus();
	$j("button[action=add]").addClass("hidden");
	//查询当前仓库基本信息，应用是否需要包材 是否允许手动称重
	rswh = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/findwarehousebaseinfo.json"));
	if (rswh.warehouse.isNeedWrapStuff){
		$j("#p1").removeClass("hidden");
		$j("#p2").removeClass("hidden");
		$j("#p_tr").removeClass("hidden");
	}else{
		$j("#p1").addClass("hidden");
		$j("#p2").addClass("hidden");
		$j("#p_tr").addClass("hidden");
	}
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	$j("#pickinglist-table").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("BATCHCODE"),i18("LPCODE"),i18("STATUS"),i18("PLANSTA"),i18("PLANSKU"),i18("CHECKEDSTA"),i18("CHECKEDSKU"),i18("CREATETIME"),"handId"],
		colModel: [{name: "id", index: "id", hidden: true},		         
		           {name: "code", index: "code", width: 120,formatter:"linkFmatter",formatoptions:{onclick:"showStaLine"}, resizable: true},
		           {name: "lpcode", index: "lpcode",width: 120, resizable: true,formatter:'select',editoptions:trasportName},
		           {name: "intStatus", index: "intStatus", width: 100, resizable: true,formatter:'select',editoptions:staStatus},
		           {name: "planBillCount", index: "planBillCount", width: 80, resizable: true},
		           {name: "planSkuQty", index: "planSkuQty", width: 80, resizable: true},
		           {name: "checkBillCount", index: "checkBillCount", width: 80, resizable: true},
		           {name: "checkSkuQty", index: "checkSkuQty",  width: 80, resizable: true},
		           {name: "createTime", index: "createTime", width: 150, resizable: true},
		           {name: "handId",index:"handId",hidden:true}],
		caption: i18("PICKINGLIST_SHOW"),//待出库列表
	   	sortname: 'id',
	   	pager:"#pager_query",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:"auto",
	    multiselect: false,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc", 
		jsonReader: { repeatitems : false, id: "0" }
	});
	//查询按钮功能
	$j("#search").click(function(){
		var postData = loxia._ajaxFormToObj("queryForm");
		$j("#pickinglist-table").jqGrid('setGridParam',{
			url:window.$j("body").attr("contextpath")+"/getallseckillpickinglistbystatus.json",
			postData:postData,
			page:1
		}).trigger("reloadGrid");
	});
	//重置按钮功能
	$j("#reset").click(function(){
		$j("#queryForm input").val("");
		$j("#queryForm option:first").attr("selected",true);
	});
	//错误信息提示弹出窗口初始化
	$j("#dialog_error").dialog({title: "错误信息", modal:true, autoOpen: false, width: 400});
	// 取消订单提示数量
	$j("#dialog_Show").dialog({title: "提示信息", modal:true, autoOpen: false, width: 400});
	//确认出库弹出窗口初始化
	$j("#dialog_confirm").dialog({title: "确认出库", modal:true, autoOpen: false, width: 400});
	//待核对列表
	$j("#waitcheck").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("SKUCODE"),i18("LOCATIONCODE"),i18("SKUNAME"),i18("JMSKUCODE"),i18("KEY_PROPS"),i18("BARCODE"),i18("QUANTITY"),i18("COMPLETEQUANTITY")],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name:"skuCode", index:"skuCode" ,width:150,resizable:true,sortable:false},
		           {name :"locationCode",index:"locationCode",width:120,resizable:true,hidden: true},
		           {name:"skuName", index:"skuName" ,width:150,resizable:true,sortable:false},
		           {name: "supplierCode", index:"sku.supplier_code",width:150,resizable:true,sortable:false},
		           {name: "keyProperties", index:"keyProperties",width:60,resizable:true,sortable:false},
		           {name:"barCode", index:"barCode", width:90,hidden: true, resizable:true},
		           {name:"quantity",index:"quantity",width:120,resizable:true,sortable:false},
		           {name:"completeQuantity",index:"completeQuantity",width:100,resizable:true,sortable:false}],
		           caption: i18("CHECKED_LIST"),//已核对列表
		           sortname: 'sku.code',
		           multiselect: false,
		           sortorder: "desc",
		           rowNum: -1,
		           height:"auto",
		           gridComplete: function() {
			   			var postData = {};
			   			$j("#waitcheck tr:gt(0)>td[aria-describedby$='barCode']").each(function (i,tag){
			   				postData["skuBarcodes[" + i + "]"] = $j(tag).html();
			   			});
			   			barcodeList = loxia.syncXhr($j("body").attr("contextpath") + "/findskuBarcodeList.json",postData);
			   		},
		           jsonReader: { repeatitems : false, id: "0" }
	});
	//取消单据列表
	$j("#cancleStaList").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("PGINDEX"),i18("CODE"),i18("SLIPCODE"),i18("SHOPOWNER"),i18("STATUS"),i18("OPERATION")],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name:"pgIndex", index:"pgIndex" ,width:150,resizable:true,sortable:false},
		           {name :"code",index:"code",width:120,resizable:true,hidden: true},
		           {name:"slipCode", index:"slipCode" ,width:150,resizable:true,sortable:false},
		           {name: "owner", index:"owner",width:150,resizable:true,sortable:false},
		           {name: "intStatus", index:"intStatus",width:150,resizable:true,sortable:false,formatter:'select',editoptions:staStatus},
		           {name: "idForBtn", width: 120,resizable:true, formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("DELETE"), onclick:"deleteById(this);"}}}],
		           caption: i18("CANCELSTALIST"),//已核对列表
		           sortname: 'sta.pg_index',
		           multiselect: false,
		           sortorder: "asc",
		           rowNum: -1,
		           height:"auto",
		           gridComplete: function() {
//			   			var postData = {};
//			   			$j("#waitcheck tr:gt(0)>td[aria-describedby$='barCode']").each(function (i,tag){
//			   				postData["skuBarcodes[" + i + "]"] = $j(tag).html();
//			   			});
//			   			barcodeList = loxia.syncXhr($j("body").attr("contextpath") + "/findskuBarcodeList.json",postData);
		           		loxia.initContext($j(this));
				   },
		           jsonReader: { repeatitems : false, id: "0" }
	});
	//条码扫描键盘事件
	$j("#barCode").keydown(function(evt){
		if(evt.keyCode == 9){//Tab 光标定位
			evt.preventDefault();
			$j(this).removeClass("ui-loxia-active");
			barCodeMouseMove();
		}
		if(evt.keyCode === 13){//Enter 判断光标定位
			evt.preventDefault();
			var bc = $j.trim($j(this).val());
			if(bc.length==0)return;
			var data = $j("#waitcheck").jqGrid('getRowData');
			var flag = false;
			$j.each(data,function(i, item){
				var bc1=item.barCode;
				if(bc1==bc||bc1=="00"+bc){//主条码相等
					flag = true;
					var num =  item.completeQuantity;
					if(num==null||num=='')num=0;
					//判断计划量大于执行量
					if(parseInt(num)+parseInt($j("#inQty").val())>item.quantity){
						$j("#barCode").val("");
						$j("#barCode0").val("");
						$j("#flag").val("barCode");
						var msg = item.skuName+" 您要核对量大于计划执行量，请重新核对！";
						errorTip(i18(msg));
					}else{
						item.completeQuantity=parseInt(num)+parseInt($j("#inQty").val());
						$j("#waitcheck").jqGrid('setRowData',item.id,item);
						var b = canMoveFocus();
						if(b){
							//alert($j("#pId").val());
							var plid = $j("#pId").val();
							loxia.lockPage();
							jumbo.showMsg(i18("DELIVERYINFO"));
							var url = $j("body").attr("contextpath") + "/printdeliveryinfomode1.json?plCmd.id="+plid;
							printBZ(loxia.encodeUrl(url),false);
							loxia.unlockPage();
							var rl = loxia.syncXhrPost($j("body").attr("contextpath")+ "/findCancelCountByPickId.json", {"pickId" : plid});
							if (rl) {
								// 提示取消单数
								if(rl["count"] == 0){
									// do nothing
								}else{
									$j("#showText").text("该批次下有 "+rl["count"] +"单被取消！");
									$j("#dialog_Show").dialog("open");
								}
							} else {
								loxia.tip(this,"获取配货批下的取消订单失败！");
							}
							
							$j("#barCode").val("");
							barCodeMouseMove();
						}else{
							$j("#barCode").val("");
							$j("#inQty").val(1);
							$j("#barCode").focus();
						} 
					}
				}else{//主条码不相等
					for(var v in barcodeList[bc1]){
						if(barcodeList[bc1][v] == bc || barcodeList[bc1][v] == ('00'+bc) ){//子条码相等
							flag = true;
							var d = $j("#waitcheck").jqGrid('getRowData',item.id);
							var num =  d.completeQuantity;
							if(num==null||num=='')num=0;
							var inQty = parseInt($j("#inQty").val());
							if(parseInt(num)+inQty>d.quantity){
								$j("#barCode").val("");
								$j("#barCode0").val("");
								$j("#flag").val("barCode");
								var msg = item.skuName+" 您要核对量大于计划执行量，请重新核对！";
								errorTip(i18(msg));
							}else{
								d.completeQuantity=parseInt(num)+parseInt($j("#inQty").val());
								$j("#waitcheck").jqGrid('setRowData',item.id,d);
								if(b){
									$j("#barCode").val("");
									barCodeMouseMove();
								}else{
									$j("#barCode").val("");
									$j("#inQty").val(1);
									$j("#barCode").focus();
								}
							}
						}
					}
				}
			});
			//条码不存在
			if(!flag){
				var msg = "条形码"+$j("#barCode").val()+"不存在！";
				$j("#barCode").val("");
				$j("#barCode0").val("");
				$j("#flag").val("barCode");
				errorTip(i18(msg));
			}
		}
	});
	//自动称重确认条码文本框键盘事件
	$j("#confirmWeightInput").keyup(function(evt){
		var code = $j(this).val();
		if(BARCODE_CONFIRM==code){
			weightCheck(2);
		}
	});
	$j("#reback,#reback1,#reback2,#reback3").click(function(){
		$j("#div1").removeClass("hidden");
		$j("#div2").addClass("hidden");
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
	$j("#doCheck0").click(function(){
		var flag = $j("#flag").val();
		$j("#dialog_error").dialog("close");
		if(flag=="barCode"){
			$j("#inQty").val(1);
			$j("#barCode").focus();
		}else if(flag=="confirmWeight"){
			$j("#confirmWeightInput").removeClass("ui-loxia-active");
			$j("#inQty").val(1);
			$j("#barCode").focus();
		}else if(flag=="autoWeight"){
			$j("#confirmWeightInput").focus();
		}else if(flag=="wrapStuff"){
			$j("#wrapStuff").focus();
		}
	});
	
	$j("#doNothingSure").click(function(){
		$j("#dialog_Show").dialog("close");
		$j("#goodsWeigth").focus();
	});
	
	$j("#barCode0").keyup(function(evt){
		var code = $j(this).val();
		if(BARCODE_CONFIRM==code){
			$j("#doCheck0").trigger("click");
		}
	});
	
	$j("#restWeight").click(function(){
		restart();
	});
	//手动称重键盘事件
	$j("#goodsWeigth").keydown(function(evt){
		if(evt.keyCode === 13){//Enter 判断光标定位
			evt.preventDefault();
			weightCheck(1);
		}
	});
	$j("#exitbutton").click(function(){
		$j("#waitcheck").jqGrid('setGridParam',{
			url:window.$j("body").attr("contextpath")+"/getstapickinglistpgindex.json",
			postData:{"pickingList.id":$j("#pId").val()},
			page:1
		}).trigger("reloadGrid");
		$j("#barCode_tab tbody").html("");
		$j("#dialog_confirm").dialog("close");
	});
	$j("#confirm1").click(function(){
		var postData={};
		var weight = $j("#cweight").html();
		var pid = $j("#pId").val();
		postData["weight"]=weight;
		postData["pickingList.id"]=pid;
		$j("#barCode_tab tbody:eq(0) tr").each(function (i,tag){ 
			barcode = $j(tag).find("td:eq(1) input").val();
			qty = $j(tag).find("td:eq(2) input").val();
			postData["saddlines[" + i + "].sku.barCode"] = barcode;
			postData["saddlines[" + i + "].quantity"] =  qty;
		});
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/seckillgenerhandoverlist.json",postData);
		if(rs&&rs.rs=="success"){
			$j("#exitbutton").trigger("click");
			$j("#div4").removeClass("hidden");
			$j("#div3").addClass("hidden");
			$j("#search").trigger("click");
			jumbo.showMsg("出库成功");
		}else{
			$j("#exitbutton").trigger("click");
			jumbo.showMsg(rs.message);
		}
	});
	$j("#printHand").click(function(){
		var id=$j("#pId").val();
		printHoInfo(id);
	});
	$j("#wrapStuff").keydown(function(event){
		if(event.keyCode == 9){
			event.preventDefault();
			$j("#wrapStuff").removeClass("ui-loxia-active");
			wrapStuffMoustMove();
		}
		if(event.keyCode == 13){
			event.preventDefault();
			if ($j("#wrapStuff").val() != null){
				addBarCode();
			}else{
				$j("#wrapStuff").focus();
			}
		}
	}); 
	$j("#pickId").keydown(function(event){
		if(event.keyCode == 13){
			event.preventDefault();
			var code = $j("#pickId").val();
			if(code==""){
				loxia.tip($j("#pickId"),"请输入配货批次号！");
			}else{
				var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/findcheckoutpickingbycode.json",{"pickingList.code":code});
				if(rs&&rs.result&&rs.result=="success"){
					if(rs&&rs["pickingList"]){
						$j("#pId").val(rs["pickingList"]["id"]);
						$j("#code").html(rs["pickingList"]["code"]);
						$j("#cpid").html(rs["pickingList"]["code"]);
						$j("#cde").html(rs["pickingList"]["skuCode"]);
						$j("#status").html(rs["pickingList"]["intStatus"]==2?"配货中":"已完成");
						$j("#planSta").html(rs["pickingList"]["planBillCount"]);
						$j("#csta").html(rs["pickingList"]["planBillCount"]);
						if(rs["pickingList"]["intStatus"]==10){
							$j("#div4").removeClass("hidden");
							$j("#div3").addClass("hidden");
							$j("#div5").addClass("hidden");
							$j("#div6").addClass("hidden");
						}else{
							$j("#barCode").val("");
							$j("#confirmWeightInput").val("");
							$j("#goodsWeigth").val("");
							$j("#div4").addClass("hidden");
							$j("#div6").addClass("hidden");
							if(rs["pickingList"]["isHaveCancel"]==false){
								$j("#div3").removeClass("hidden");
								$j("#div5").addClass("hidden");
								$j("#waitcheck").jqGrid('setGridParam',{
									url:window.$j("body").attr("contextpath")+"/getstapickinglistpgindex.json",
									postData:{"pickingList.id":$j("#pId").val()},
									page:1
								}).trigger("reloadGrid");
							}else{
								$j("#div3").addClass("hidden");
								$j("#div5").removeClass("hidden");
								$j("#cancleStaList").jqGrid('setGridParam',{
									url:window.$j("body").attr("contextpath")+"/getcancelstalistpgindex.json",
									postData:{"pickingList.id":$j("#pId").val()},
									page:1
								}).trigger("reloadGrid");
							}
						}
						$j("#div2").removeClass("hidden");
						$j("#div1").addClass("hidden");
						$j("#barCode").focus();
					}
				}else{
					loxia.tip($j("#pickId"),"你输入的配货批次号不正确!");
				}
			}
		}
	});
});
function addBarCode(){
	var varCode = jQuery.trim($j("#wrapStuff").val());
	if(($j("#wrapStuff").val() == ''||$j("#wrapStuff").val() == null) && $j("#barCode_tab tbody tr").length > 0){
		$j("#wrapStuff").removeClass("ui-loxia-active");
		wrapStuffMoustMove();
	}
	if(varCode != ""){
		// 判断是否为耗材
		var result = loxia.syncXhrPost(loxia.getTimeUrl(window.$j("body").attr("contextpath")+"/getSkuTypeByBarCode.json?barCode="+varCode));
		if (!result || result.value != 1){
			$j("#wrapStuff").val("");
			jumbo.showMsg("该包材不存在或为非耗材!");
			return;
		}
		var isAdd = false;
		$j("#barCode_tab tbody tr").each(function (i,tag){
			if(varCode == $j(tag).find("td:eq(1) :input").val()){
				var index = $j(tag).index;
				$j(tag).find("td:eq(2) :input").val(parseInt($j(tag).find("td:eq(2) :input").val())+1);
				isAdd = true;
			}
		});
		if(isAdd == false){
			$j("button[action=add]").click();
			var length = $j("#barCode_tab tbody tr").length-1;
			$j("#barCode_tab tbody tr:eq("+length+") td:eq(1) :input").val(varCode);
			$j("#barCode_tab tbody tr:eq("+length+") td:eq(2) :input").val(1);
		}
	}
	if($j("#barCode_tab tbody tr").length > 0){
		$j("#wrapStuff").val("");
		$j("#wrapStuff").removeClass("ui-loxia-active");
		wrapStuffMoustMove();
	}else {
		$j("#wrapStuff").val("");
		$j("#flag").val("wrapStuff");
		var msg = "包材条码不能为空!";
		$j("#barCode0").val("");
		errorTip(i18(msg));
	}
}
function showStaLine(tag){
	$j("#pId").val($j(tag).parents("tr").attr("id"));
	$j("#code").html($j(tag).html());
	$j("#cpid").html($j(tag).html());
	$j("#cde").html($j(tag).parents("tr").children("td:eq(3)").attr("title"));
	$j("#status").html($j(tag).parents("tr").children("td:eq(4)").attr("title"));
	$j("#planSta").html($j(tag).parents("tr").children("td:eq(5)").attr("title"));
	$j("#csta").html($j(tag).parents("tr").children("td:eq(5)").attr("title"));
	if($j(tag).parents("tr").children("td:eq(4)").attr("title")=="已完成"){
		$j("#div4").removeClass("hidden");
		$j("#div3").addClass("hidden");
		$j("#div5").addClass("hidden");
		$j("#div6").addClass("hidden");
	}else{
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/findcheckoutpickingbycode.json",{"pickingList.code":$j(tag).html()});
		$j("#barCode").val("");
		$j("#confirmWeightInput").val("");
		$j("#goodsWeigth").val("");
		$j("#div4").addClass("hidden");
		$j("#div6").addClass("hidden");
		if(rs["pickingList"]["isHaveCancel"]==false){
			$j("#div3").removeClass("hidden");
			$j("#div5").addClass("hidden");
			$j("#waitcheck").jqGrid('setGridParam',{
				url:window.$j("body").attr("contextpath")+"/getstapickinglistpgindex.json",
				postData:{"pickingList.id":$j("#pId").val()},
				page:1
			}).trigger("reloadGrid");
		}else{
			$j("#div3").addClass("hidden");
			$j("#div5").removeClass("hidden");
			$j("#cancleStaList").jqGrid('setGridParam',{
				url:window.$j("body").attr("contextpath")+"/getcancelstalistpgindex.json",
				postData:{"pickingList.id":$j("#pId").val()},
				page:1
			}).trigger("reloadGrid");
		}
	}	
	$j("#div2").removeClass("hidden");
	$j("#div1").addClass("hidden");
	$j("#barCode").focus();
}
function errorTip(msg){
	$j("#errorText").text(msg);
	$j("#dialog_error").dialog("open");
	$j("#barCode0").focus();
}
function canMoveFocus(){
	var data = $j("#waitcheck").jqGrid('getRowData');
	var b = true;
	$j.each(data,function(i, item){
		if(item.quantity!=item.completeQuantity){
			b =false;
		}
	});
	return b;
}
//从条码区进行光标跳转
function barCodeMouseMove(){
	$j("#barCode").removeClass("ui-loxia-active");
	if(rswh.warehouse.isNeedWrapStuff){//如果需要包材则定位到包材条码区
		$j("#wrapStuff").focus();
	}else{//负责定位重量填写区
		wrapStuffMoustMove();
	}
}
function wrapStuffMoustMove(){
	if(rswh.warehouse.isManualWeighing){
		$j("#goodsWeigth").focus();
	}else{
		appletStart();//开秤
		$j("#confirmWeightInput").focus();
	}
}
//称重 先判断是否核对
function weightCheck(type){
	var b = canMoveFocus();
	if(b){//如果单据核对判断数量
		if(type==1){//手动称重
			var rs = doExecute($j("#goodsWeigth").val());
			if(rs!=loxia.SUCCESS){
				loxia.tip($j("#goodsWeigth"),rs);
				return;
			}else{
				showConfirm($j("#goodsWeigth").val());
			}
		}else{
			var rs = doExecute($j("#autoWeigth").val());
			if(rs!=loxia.SUCCESS){
				$j("#flag").val("autoWeight");
				$j("#confirmWeightInput").val("");
				$j("#barCode0").val("");
				errorTip(rs);
			}else{
				showConfirm($j("#autoWeigth").val());
			}
		}
	}else{//如果单据没有核对先提示核对
		$j("#flag").val("confirmWeight");
		$j("#confirmWeightInput").val("");
		$j("#barCode0").val("");
		errorTip("请先执行核对再称重！");
	}
}
function showConfirm(weight){
	$j("#cweight").html(weight);
	$j("#goodsWeigth").val("");
	$j("#autoWeigth").val("")
	appletStop();
	$j("#dialog_confirm").dialog("open");
}
function doExecute(value,obj){
	if(value == ""){
		return i18("INPUT_WEIGHT");//请输入货物重量
	}
	if(!/^[0-9]*\.{0,1}[0-9]*$/.test(value)){
		return i18("NUMBER_RULE");//不是一个合法的数字或精度要求不符合要求
	}
	value = parseFloat(value);
	if(value == 0){
		return i18("INPUT_WEIGHT");//请输入货物重量
	}else if(value > 150){
		return i18("WEIGHT_LAGGER");//货物重量不得超过150KG
	}	
	return loxia.SUCCESS;
}
function printHoInfo(id){
	var rs1 = loxia.syncXhrPost($j("body").attr("contextpath") + "/gethandoveridbypickinglistid.json",{"pickingList.id":id});
	if(rs1&&rs1.rs=="success"){
		var hId = rs1.handId;
		var url = $j("body").attr("contextpath") + "/json/printhandoverlist.do?hoListId=" + hId;
		printBZ(loxia.encodeUrl(url),true);
	}else{
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/generhandoverlist.json",{"pickingList.id":id});
		if(rs&&rs.rs=="success"){
			var handId = rs.handId;
			var url = $j("body").attr("contextpath") + "/json/printhandoverlist.do?hoListId=" + handId;
			printBZ(loxia.encodeUrl(url),true);
		}else{
			jumbo.showMsg("打印交接清单失败，单据未成功创建");
		}
	}
}
function deleteById(tag){
	var id = $j(tag).parents("tr").attr("id");
	var rs = loxia.syncXhrPost($j("body").attr("contextpath")+"/deletestabyid.json",{"staId":id,"pickingList.id":$j("#pId").val()});
	if(rs&&rs.result&&rs.result=="success"){//删除成功
		if(rs.flag=="YES"){//pickingList被删除
			$j("#div6").removeClass("hidden");
			$j("#div5").addClass("hidden");
			$j("#search").trigger("click");
		}else{//pickingList没被删除
			if(rs.isHaveCancel=="true"){//还有取消单据
				$j("#cancleStaList").jqGrid('setGridParam',{
					url:window.$j("body").attr("contextpath")+"/getcancelstalistpgindex.json",
					postData:{"pickingList.id":$j("#pId").val()},
					page:1
				}).trigger("reloadGrid");
			}else{//没有取消单据
				$j("#div3").removeClass("hidden");
				$j("#div5").addClass("hidden");
				$j("#waitcheck").jqGrid('setGridParam',{
					url:window.$j("body").attr("contextpath")+"/getstapickinglistpgindex.json",
					postData:{"pickingList.id":$j("#pId").val()},
					page:1
				}).trigger("reloadGrid");
			}
		}
	}else{//删除失败
		jumbo.showMsg("删除失败");
	}
}