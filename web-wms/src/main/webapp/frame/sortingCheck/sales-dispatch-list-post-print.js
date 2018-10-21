$j.extend(loxia.regional['zh-CN'],{
	CODE : "配货批次号",
	INTSTATUS : "状态",
	PLANBILLCOUNT : "计划配货单据数",
	CHECKEDBILLCOUNT : "已核对单据数",
	SHIPSTACOUNT : "已发货单据数",
	PLANSKUQTY : "计划配货商品件数",
	CHECKEDSKUQTY : "已核对商品件数",
	SHIPSKUQTY : "已发货商品件数",
	CREATE_TIME: "创建时间",
	CHECKEDTIME : "最后核对时间",
	EXECUTEDTIME : "最后发货时间",
	PICKINGTIME : "开始配货时间",
	PICKING_LIST : "配货清单列表",
	TRUNKPACKINGLISTINFO:"装箱清单打印中，请等待...",
	DELIVERYINFO:"打印中，请等待..."
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function getFmtValue(tableId,rowId,column){
	return $j("#"+tableId+" tr[id="+rowId+"] td[aria-describedby='"+tableId+"_"+column+"']").attr("title");
}
var tempId = null;//用于回车传值
var inputId = 0;
var userName = "";
var dateValue = "";
var fileUrl = "";
var tempSkuId = "";
var tempbarCode = "";
var tempSnNum = "";
var tempZxl = "";
var tempPinkId = "";
var tempStaId = "";
var ouName = "";
var checkedStaIds = [];
var currentStaId = "";
var isChecking = false;
var staCodes="";
var staIds = "";
var staList="";
var staQtyInfo="";
var barcodeList="";
function showDetail(obj){
	//加载数据
	var row = null;
	var plCode = null;
	var postData=loxia._ajaxFormToObj("plForm");  //获取表单输入的值
	
	if (obj == null) {
		plCode = $j("#iptPlCode").val();
	} else {
		plCode = $j(obj).text();
	}
	postData["iptPlCode"]=plCode;
	var pl=loxia.syncXhr($j("body").attr("contextpath")+ "/findPickingListForVerifyByCodeId.json",postData);
	if (pl && pl["pl"]) {
		row = pl['pl']['id'];
	isChecking = false;
	hashMap.removeAll();
	$j("#dialog_error_ok").dialog({title: "错误信息", modal:true, autoOpen: false, width: 470});
	$j("#dialog_sure_ok").dialog({title: "系统提示", modal:true, autoOpen: false, width: 470});
	$j("#showList").addClass("hidden");
	$j("#opencvImgDiv").addClass("hidden");
	$j("#showDetail").removeClass("hidden");
	$j("#barCode").attr("value","");
	$j("#barCode").focus();
	document.getElementById('snNum').style.display = "none";
	document.getElementById('snNumber').style.display = "none";
	
	$j("#verifyCode").html(pl["pl"]['code']);
	$j("#verifyStatus").html(getFmtValue("tbl-dispatch-list",row,"intStatus"));
	$j("#verifyPlanBillCount").html(pl["pl"]['planBillCount']);
	$j("#verifyCheckedBillCount").html(pl["pl"]['checkedBillCount']);
	$j("#verifyPlanSkuQty").html(pl["pl"]['planSkuQty']);
	$j("#verifyCheckedSkuQty").html(pl["pl"]['checkedSkuQty']);
	var baseUrl = $j("body").attr("contextpath");
	//商品明细
	var pickUrl = baseUrl + "/findPickingDetail.json?pickinglistId="+row;
	$j("#tbl-showDetail").jqGrid('setGridParam',{url:pickUrl,datatype: "json",}).trigger("reloadGrid");
    staList = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findAllPostCheckInfo.do?pickinglistId="+row);
    for(var idx in staList){
		var staQtyInfos = staList[idx].extInfo2;//商品数量明细
		var skuArry = staQtyInfos.split(";");
		var jhl="",zxl="";
		for(var i = 0 ; i < skuArry.length; i++){ //修改表格数据
			jhl = skuArry[i].substring(skuArry[i].indexOf(":")+1,skuArry[i].indexOf(","));
			zxl = skuArry[i].substring(skuArry[i].indexOf(",")+1,skuArry[i].length);
			//alert(jhl+"--"+zxl);
			if(jhl == zxl){
				staList[idx].refSlipCode= null;
				break;
			}
		}
    }
	}else{
		jumbo.showMsg("未找到配货批次！");
		return;
	}
}

$j(document).ready(function (){
	userName = $j("#userinfo", window.parent.document).attr("lgname");
	dateValue = $j("#userinfo", window.parent.document).attr("sysdate");
	ouName = $j("#userinfo", window.parent.document).attr("ouName");
	$j("#dialog_error_ok").addClass("hidden");
	$j("#dialog_sure_ok").addClass("hidden");
	$j("#dialog_msg").dialog({title : "提示信息",modal : true,autoOpen : false,width : 700,closeOnEscape : false,open : function(event, ui) {	$j(".ui-dialog-titlebar-close").hide();	}});
	 try{
		 	closeGrabberJs();				
		}catch (e) {
//			jumbo.showMsg(i18("关闭摄像头失败！"));//初始化系统参数异常
	}
	try{
			openCvPhotoImgJs();//打开照相功能
//				jumbo.showMsg(i18("打开照相功能成功！"));
		} catch (e) {
			jumbo.showMsg(i18("打开照相功能失败！"));//初始化系统参数异常
	};
			
	$j("#iptPlCode").focus();
	$j("#whName").html(window.parent.$j("#orgname").html());
	var plstatus=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"pickingListStatus"});
	if(!plstatus.exception){
		$j("#tbl-dispatch-list").jqGrid({
			url:$j("body").attr("contextpath") + "/searchPickingList.json",
			postData:loxia._ajaxFormToObj("plForm"),
			datatype: "json",//"已发货单据数","已发货商品件数",
			//colNames: ["ID","配货批次号","状态","计划配货单据数","已核对单据数","已发货单据数","计划配货商品件数","已核对商品件数","已发货商品件数","最后核对时间","最后发货时间","开始配货时间"],
			colNames: ["ID",i18("CODE"),i18("INTSTATUS"),i18("PLANBILLCOUNT"),i18("CHECKEDBILLCOUNT"),i18("SHIPSTACOUNT"),i18("PLANSKUQTY"),i18("CHECKEDSKUQTY"),i18("SHIPSKUQTY"),
				i18("CREATE_TIME"),i18("CHECKEDTIME"),i18("EXECUTEDTIME"),i18("PICKINGTIME")],
			colModel: [{name: "id", index: "pl.id", hidden: true},
						{name:"code",index:"pl.code",formatter:"linkFmatter",formatoptions:{onclick:"showDetail"},width:100,resizable:true},
						{name:"intStatus", index:"pl.status" ,width:60,resizable:true,formatter:'select',editoptions:plstatus},
						{name:"planBillCount", index:"pl.plan_bill_count",width:100,resizable:true},
						{name:"checkedBillCount", index:"pl.checked_bill_count", width:90, resizable:true},
						{name:"shipStaCount",index:"pl.send_bill_count",width:90,resizable:true,hidden:true},
						{name:"planSkuQty",index: "pl.plan_sku_qty",width:120,resizable:true},
						{name:"checkedSkuQty",index:"pl.checked_sku_qty",width:100,resizable:true},
						{name:"shipSkuQty",index:"pl.send_sku_count",width:100,resizable:true,hidden:true},
						{name:"createTime",index:"pl.CREATE_TIME",width:150,resizable:true},
						{name:"checkedTime",index:"pl.CHECK_TIME",width:150,resizable:true},
						{name:"executedTime",index:"pl.last_outbound_time",width:150,resizable:true,hidden:true},
		               {name:"pickingTime",index:"pl.PICKING_TIME",width:150,resizable:true}],
			caption: i18("PICKING_LIST"),//配货清单列表
		   	sortname: 'pl.CREATE_TIME',
		    multiselect: false,
			sortorder: "desc",
			pager:"#pager",
			width:"auto",
			height:"auto",
			rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
			height:jumbo.getHeight(),
			viewrecords: true,
		   	rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" }
		});
	}else{
		jumbo.showMsg(i18("INIT_SYSTEM_DATA_EXCEPTION"));//初始化系统参数异常
	}
	
	$j("#tbl-showReady").jqGrid({
		colNames: ["商品ID","作业单明细ID","条形码","是否SN","商品名称","SKU编码","SN号","差异量","执行量","操作"],
		colModel: [	{name: "skuId", index: "skuId", hidden: true},
		           	{name:"staId",index:"staId",width:140,resizable:true,hidden:true},
					{name:"barcode",index:"barcode",width:140,resizable:true,hidden:true},
					{name:"isSnSku",index:"isSnSku",width:140,resizable:true,hidden:true},
					{name:"skuname",index:"skuname",width:140,resizable:true},
					{name:"skuCode",index:"skuCode",width:140,resizable:true},
					{name:"snNumber",index:"snNumber",width:140,resizable:true},
					{name:"cyl",index:"cyl",width:140,resizable:true,hidden:true},
					{name: "checkQuantity",index:"checkQuantity",width:140,
					formatter:function(cellvalue, options, rowObject) {
						return '<input type="text"  id="' + rowObject["skuId"] + '" name="' + rowObject["skuId"] + '" onkeyup="onKeyUp(this); " onclick="onFocus(this);"/>';}},
					{name:"btnExp",width:80,resizable:true}
					],
		caption: "当前扫描商品",//配货清单列表
	   	sortname: 'skuId',
	    multiselect: false,
		sortorder: "desc",
		width:"auto",
		height:"auto",
		height:jumbo.getHeight(),
		viewrecords: true,
	   	rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var btn1 = '<div><button type="button" style="width:80px;" class="confirm" name="btnExp" loxiaType="button" onclick="deleteSku(this);">'+"删除"+'</button></div>';
			var ids = $j("#tbl-showReady").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				$j("#tbl-showReady").jqGrid('setRowData',ids[i],{"btnExp":btn1});
			}
			loxia.initContext($j(this));
		}
	});
	
	$j("#tbl-showDetail").jqGrid({
		colNames: ["商品ID","作业单明细ID","条形码","作业单号","是否SN","商品名称","SKU编码","计划量","核对量"],
		colModel: [	{name: "skuId", index: "skuId", hidden: true},
		           	{name:"staId",index:"staId",width:150,resizable:true,hidden:true},
					{name:"barcode",index:"barcode",width:150,resizable:true,hidden:false},
					{name:"staCode",index:"staCode",width:150,resizable:true,hidden:true},
					{name:"isSnSku",index:"isSnSku",width:150,resizable:true,hidden:true},
					{name:"skuname",index:"skuname",width:150,resizable:true},
					{name:"skuCode",index:"skuCode",width:150,resizable:true},
					{name:"quantity",index:"quantity",width:150,resizable:true},
					{name:"checkQuantity",index:"checkQuantity",width:150,resizable:true}
					],
		caption: "待核对列表",//配货清单列表
	   	sortname: 'skuId',
	    multiselect: false,
		sortorder: "desc",
		width:"auto",
		height:"auto",
		//height:jumbo.getHeight(),
		rowNum: -1,
		viewrecords: true,
	   	rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			if(true == isChecking){
				var allChecked = isAllChecked();
		    	if(true == allChecked){
		    		checkedStaIds = null;
		    		jumbo.showMsg("操作成功！");
		    		isChecking = false;
		        	window.location.reload();
		    	}else{
		    		$j("#close").click();
		    		$j("#barCode").focus();
		    		
		    	}
			}
		},
		loadComplete : function() {
			loxia.initContext($j("#tbl-showDetail"));
			var postData = {};
			$j("#tbl-showDetail tr:gt(0)>td[aria-describedby$='barcode']").each(function(i, tag) {postData["skuBarcodes["+ i + "]"] = $j(tag).html();});
			barcodeList = loxia.syncXhr($j("body").attr("contextpath")	+ "/findskuBarcodeList.json",postData);
		}
	});

	//查询
	$j("#search").click(function(){
		$j("#tbl-dispatch-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/searchPickingList.json"),
			postData:loxia._ajaxFormToObj("plForm")}).trigger("reloadGrid",[{page:1}]);
	});
	
	//重置
	$j("#reset").click(function(){
		document.getElementById("plForm").reset();
	});

	//扫描完成
	$j("#finish").click(function(){
		finishBeforeCheck();
	});
	
	//返回
	$j("#backToPlList").click(function(){
		$j("#iptPlCode").attr("value","");
		$j("#iptPlCode").focus();
		$j("#showList").removeClass("hidden");
		$j("#showDetail").addClass("hidden");
		$j("#tbl-showReady").clearGridData(false);
		staList = "";
	});
	
	//关闭核对页面
	$j("#close").click(function() {
		$j("#dialog_msg").dialog("close");
		$j("#barCode").focus();
	});
	
	//关闭错误信息并删除
	$j("#dialog_error_close_ok").click(function(){
		
		$j("#barCode").attr("value","");
		$j("#tbl-showReady").clearGridData(false);
		hashMap.removeAll();
		$j("#dialog_error_ok").dialog("close");
		$j("#dialog_error_ok").addClass("hidden");
		$j("#barCode").focus();
	});
	
	//关闭错误信息并删除
	$j("#dialog_sure_delete").click(function(){
		$j("#barCode").attr("value","");
		$j("#tbl-showReady").clearGridData(false);
		hashMap.removeAll();
		$j("#dialog_sure_ok").dialog("close");
		$j("#dialog_sure_ok").addClass("hidden");
		$j("#barCode").focus();
	});
	
	//关闭错误信息
	$j("#dialog_error_close").click(function(){
		$j("#barCode").attr("value","");
		$j("#dialog_error_ok").dialog("close");
		$j("#dialog_error_ok").addClass("hidden");
		$j("#barCode").focus();
	});
	
	//确认直接核对
	$j("#dialog_sure_button").click(function(){
		var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/updateisCheckQty.json?sta.id="+staIds);
	    if(rs.result=='success'){
//	    	var pickUrl = $j("body").attr("contextpath") + "/findPickingDetail.json?pickinglistId="+tempPinkId;
//	    	$j("#tbl-showDetail").jqGrid('setGridParam',{url:pickUrl,datatype: "json",}).trigger("reloadGrid");
	    	
	    	$j("#tbl-showReady").clearGridData(false);
	    	$j("#barCode").attr("value","");
			hashMap.removeAll();
			$j("#dialog_sure_ok").dialog("close");
			$j("#dialog_sure_ok").addClass("hidden");
			$j("#barCode").focus();
			jumbo.showMsg("操作成功！");
			
		    updateWebDate();
	    }else{
	    	if(rs.msg != null){
				jumbo.showMsg(rs.msg);
			}else{
				jumbo.showMsg("操作失败！");
			}
	    }
	});
	
	
	
	// 打印装箱清单按钮功能
	$j("#printTrunkPackingList").click(function(){
		loxia.lockPage();
		jumbo.showMsg(i18("TRUNKPACKINGLISTINFO"));
		var url = $j("body").attr("contextpath") + "/printtrunkpackinglistmode1Out.json?plCmd.id=" + tempPinkId + "&plCmd.staId=" + tempStaId + "&isPostPrint=true";
		printBZ(loxia.encodeUrl(url),false);				
		loxia.unlockPage();
	});
	
	// 打印物流面单
	$j("#printDeliveryInfo").click(function(){
//		var expressBill = $j("#expressBill").html();
//		if(expressBill == null || expressBill == ""){
//			$j("#confirmBarCode").focus();
//			jumbo.showMsg("物流面单打印失败：快递单号为空！");
//		}else{
//			loxia.lockPage();
//			jumbo.showMsg(i18("DELIVERYINFO"));
//			var url = $j("body").attr("contextpath") + "/printdeliveryinfomode1.json?sta.id=" + tempStaId;
//			printBZ(loxia.encodeUrl(url),true);					
//			loxia.unlockPage();
//		}
		printDelivery();
	});
	
	// 核对
	$j("#btnConfirm").click(function(){
		confirm();
	});
	
	$j("#addTrank").click(function(){
		var data = {};
		data["sta.id"] = tempStaId;
		var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/staAddTrankNo.json",data);
		if(rs && rs.msg == 'success'){
			var temp = rs.trankCode;
			var trankCode = temp.substring(0,temp.indexOf(","));
			var packId = temp.substring(temp.indexOf(",")+1,temp.length);
			var str = "<tr id=\""+trankCode+"\"><td class='label' height='36px' style='text-align: center;font-size: 36px' >"+trankCode+"</td><td>" +
			"<button style='width: 200px;height: 45px;font-size=36px' onclick=\"printTrank('"+tempStaId+"','"+packId+"')\">"+"打印面单"+"</button></td></tr>";//删除	
			$j("#tbl-trank-list").append(str);		
		}else{
			if(rs.errMsg != null){
				jumbo.showMsg(rs.errMsg);
			}
		}
	});
	
	// 核对
	$j("#confirmBarCode").keydown(function(evt){
		if(evt.keyCode == 13){
			var inputValue = $j("#confirmBarCode").val();
			$j("#barCode").attr("value","");
			if(inputValue == "OK" || inputValue == "ok"){
				confirm();
			}else{
				$j("#confirmBarCode").attr("value","");
				$j("#confirmBarCode").focus();
			}
		}
	});
	
	// 核对
	$j("#confirmBarCode2").keydown(function(evt){
		if(evt.keyCode == 13){
			var inputValue = $j("#confirmBarCode2").val();
			if(inputValue == "OK" || inputValue == "ok"){
				var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/updateisCheckQty.json?sta.id="+staIds);
			    if(rs.result=='success'){
			    	$j("#tbl-showReady").clearGridData(false);
			    	$j("#barCode").attr("value","");
					hashMap.removeAll();
					$j("#dialog_sure_ok").dialog("close");
					$j("#dialog_sure_ok").addClass("hidden");
					$j("#barCode").focus();
					jumbo.showMsg("操作成功！");
				    updateWebDate();
			    }else{
			    	if(rs.msg != null){
						jumbo.showMsg(rs.msg);
					}else{
						jumbo.showMsg("操作失败！");
					}
			    }
			}else{
				$j("#confirmBarCode2").attr("value","");
				$j("#confirmBarCode2").focus();
			}
		}
	});
	//批次号扫描回车
	$j("#iptPlCode").keydown(function(evt){
		if (evt.keyCode === 13) {
			var plcode = $j("#iptPlCode").val().trim();
			if (plcode == "") {
				loxia.tip(this,"批次号的作业单不能为空！");
				return;
			}
			showDetail(null);
		}
		
		/*f(evt.keyCode == 13){ g
			var ids = $j("#tbl-dispatch-list").jqGrid('getDataIDs');
			var inputValue = $j("#iptPlCode").val();
			var isShow = false;
			for(var i=0;i<ids.length;i++){
				var plCode = $j("#tbl-dispatch-list").getCell(ids[i],"code");
				if(inputValue.trim() == plCode){
					tempId = ids[i];
					isShow = true;
				}
			}
			if(isShow){
				showDetail(null);
			}else{
				loxia.tip(this,"指定批次号的作业单不在待核对的列表！");
			}
		}*/
	});
	
	//SN号回车
	$j("#snNumber").keydown(function(evt){
		if(evt.keyCode == 13){
			var bc = $j("#snNumber").val().trim();
			if(bc==""){
				return;
			}
			//SN号校验
			var result = loxia.syncXhr($j("body").attr("contextpath") + "/findsnbyskuid.json",{"sn":bc,"skuId":tempSkuId});
			if(result && result.result == "success"){
				if(hashMap.get(tempbarCode+","+bc) != null){
					loxia.tip(this,"该SN已经扫描过:"+bc);
					return;
				}
				var ids = $j("#tbl-showDetail").jqGrid('getDataIDs');
				var obj = {};
				var barCode,skuId,skuname,skuCode,quantity,checkQuantity,cyl;
				for(var i=0;i<ids.length;i++){
					barCode = $j("#tbl-showDetail").getCell(ids[i],"barcode");
					skuId = $j("#tbl-showDetail").getCell(ids[i],"skuId");
					staId = $j("#tbl-showDetail").getCell(ids[i],"staId");
					skuname = $j("#tbl-showDetail").getCell(ids[i],"skuname");
					skuCode = $j("#tbl-showDetail").getCell(ids[i],"skuCode");
					quantity = $j("#tbl-showDetail").getCell(ids[i],"quantity");
					checkQuantity = $j("#tbl-showDetail").getCell(ids[i],"checkQuantity");
					isSn = $j("#tbl-showDetail").getCell(ids[i],"isSnSku");
					if(checkQuantity == ""){
						checkQuantity = 0;
					}
					cyl = parseInt(quantity) - parseInt(checkQuantity);
					if(tempbarCode == barCode){
						var pNo = $j("#verifyCode").html();
						var timestamp=new Date().getTime();
						try{
							cameraPhotoJs(dateValue +"/" +ouName+ "/" + pNo + "/" + staCode, barCode + "_" + userName+"_"+timestamp);
						} catch (e) {
//							jumbo.showMsg(i18("拍照失败！"));//初始化系统参数异常
						};
						fileUrl = dateValue + "/" + ouName + "/" + pNo + "/" + staCode+"/"+barCode + "_" + userName+"_"+timestamp;
						cameraPaintMultiJs(fileUrl);
						hashMap.put(barCode+","+bc, bc);
						obj = {
								barcode:barCode,
								skuId:skuId,
								skuname:skuname,
								skuCode:skuCode,
								cyl:cyl,
								snNumber:bc
						}; 
						addSku(obj);
						document.getElementById('snNum').style.display = "none";
						document.getElementById('snNumber').style.display = "none";
						$j("#barCode").focus();
						$j("#snNumber").blur();
						$j("#snNumber").attr("value","");
						$j("#barCode").attr("value","");
					}
				}
			}else{
				playMusic($j("body").attr("contextpath") +"/recording/barCodeError.wav");
				loxia.tip(this,i18(tempbarCode + "对应SN:" +bc+" 不存在"));
			}
		}
	});
	
	//条码扫描回车
	$j("#barCode").keydown(function(evt){
		if(evt.keyCode == 13){
			var inputValue = $j("#barCode").val();
			if(inputValue == "OK" || inputValue == "ok"){
				finishBeforeCheck();
			}else{
				$j("#opencvImgDiv").removeClass("hidden");
				var ids = $j("#tbl-showDetail").jqGrid('getDataIDs');
				var isShow = false;
				var isMore = false;
				tempSnNum = "";
				var obj = {};
				var barCode,skuId,skuname,staId,skuCode,quantity,checkQuantity,cyl,isSn;
				for(var i=0;i<ids.length;i++){
					barCode = $j("#tbl-showDetail").getCell(ids[i],"barcode");
					tempbarCode = barCode;
					skuId = $j("#tbl-showDetail").getCell(ids[i],"skuId");
					tempSkuId = skuId;
					staId = $j("#tbl-showDetail").getCell(ids[i],"staId");
					staCode = $j("#tbl-showDetail").getCell(ids[i],"staCode");
					skuname = $j("#tbl-showDetail").getCell(ids[i],"skuname");
					skuCode = $j("#tbl-showDetail").getCell(ids[i],"skuCode");
					quantity = $j("#tbl-showDetail").getCell(ids[i],"quantity");
					checkQuantity = $j("#tbl-showDetail").getCell(ids[i],"checkQuantity");
					if(checkQuantity == ""){
						checkQuantity = 0;
					}
					isSn = $j("#tbl-showDetail").getCell(ids[i],"isSnSku");
					cyl = parseInt(quantity) - parseInt(checkQuantity);
					if(inputValue.trim() == barCode){
						isMore = true;
					}else if(barcodeList[barCode] !="" && barcodeList[barCode] != null){
						for (var v in barcodeList[barCode]) {
							if (barcodeList[barCode][v] == inputValue.trim()|| barcodeList[barCode][v] == ('00' + inputValue.trim())) {
								isMore = true;
								break;
							}else{
								isMore = false;
							}
						}
					}else{
						isMore = false;
					}
					if(isMore){
						isShow = true;
						var pNo = $j("#verifyCode").html();
						var timestamp=new Date().getTime();
						try{
							//cameraPhoto(pNo, staCode, barCode,timestamp);//拍照sal
							cameraPhotoJs(dateValue +"/" +ouName+ "/" + pNo + "/" + staCode, barCode + "_" + userName+"_"+timestamp);
						} catch (e) {
//							jumbo.showMsg(i18("拍照失败！"));//初始化系统参数异常
						};
						//fileUrl = dateValue + "/" + pNo + "/" + staCode + "/" + barCode + "_" + userName + "_" + timestamp;
						//fileUrl = dateValue + "/" + ouName + "/" + pNo + "/" + staCode+"/"+userName+"_"+timestamp;
						fileUrl = dateValue + "/" + ouName + "/" + pNo + "/" + staCode+"/"+barCode + "_" + userName+"_"+timestamp;
						//fileName += barCode + "_"+userName+"_"+timestamp+".jpg"+"/";
						//cameraPaintJs(fileUrl);
						cameraPaintMultiJs(fileUrl);
						if(isSn == 1){
							document.getElementById('snNum').style.display = "";
							document.getElementById('snNumber').style.display = "";
							$j("#snNumber").focus();
							$j("#barCode").blur();
							$j("#snNumber").attr("value","");
							return;
						}else{
							$j("#barCode").attr("value","");
							$j("#barCode").focus();
						}
						document.getElementById('snNum').style.display = "none";
						document.getElementById('snNumber').style.display = "none";
						$j("#barCode").focus();
						obj = {
								barCode:barCode,
								skuId:skuId,
								skuname:skuname,
								skuCode:skuCode,
								cyl:cyl
						};
					}
//					}else{
//						alert(barcodeList[barCode]);
//						for (var v in barcodeList[barCode]) {
//							alert(22);
//							alert(barcodeList[barCode][v]);
//							if (barcodeList[barCode][v] == inputValue.trim()|| barcodeList[barCode][v] == ('00' + inputValue.trim())) {
//								isChecks = true;
//								break;
//							}
//						}
//					}
				}
				if(isShow){
					addSku(obj);
				}else{
					playMusic($j("body").attr("contextpath") +"/recording/barCodeError.wav");
					loxia.tip(this,"指定商品条码不在待核对的列表！");
				}
			}
		}
	});
});

//添加商品
function addSku(obj){
	if(obj.cyl <= 0){//判断是否超出计划量
		jumbo.showMsg(obj.skuname+":核对量已超出计划量！");
		return;
	}
	if(obj.snNumber != null && obj.snNumber != ""){
		var objs=document.getElementsByName(obj.skuId);
		if(obj.cyl <= objs.length){
			jumbo.showMsg(obj.skuname+":核对量已超出计划量！");
			return;
		}
	}
	var ids = $j("#tbl-showReady").jqGrid('getDataIDs');
	var isCopy = true;
	var zxl = $j("#"+obj.skuId+"").val(); //根据添加数据的ID获取对应input的值
	if(zxl == null || zxl == ""){//初始化 值为0
		zxl = 0;
	}
	for(var i=0;i<ids.length;i++){//判断是否在列表中已添加
		var skuId = $j("#tbl-showReady").getCell(ids[i],"skuId");
		var snNumber = $j("#tbl-showReady").getCell(ids[i],"snNumber");
		if(skuId == obj.skuId && (snNumber == null || snNumber == "")){
			isCopy = false;
		}
	}
	if(isCopy){//如未添加，则添加数据
		var ids = $j("#tbl-showReady").jqGrid('getDataIDs');   
		var $firstTrRole = $j("#tbl-showReady").find("tr").eq(1).attr("role");  
		var rowid = $firstTrRole == "row" ? Math.max.apply(Math,ids):0;  
		var newrowid = parseInt(rowid)+1;
		$j("#tbl-showReady").jqGrid("addRowData", newrowid, obj, "last");//将新添加的行插入到最后一列
		$j("#"+obj.skuId+"").attr("value",parseInt(zxl)+1);
		if(obj.snNumber != null && obj.snNumber != ""){
			$j("input[name='"+obj.skuId+"']").attr("disabled",true);
			$j("input[name='"+obj.skuId+"']").attr("value","1");
		}
	}else{//如已添加，再判断是否超出计划量，如未超出，则数量+1
		if(obj.cyl <= parseInt(zxl)){
			jumbo.showMsg(obj.skuname+":核对量已超出计划量！");
			$j("#"+obj.skuId+"").attr("value",obj.cyl);
		}else{
			$j("#"+obj.skuId+"").attr("value",parseInt(zxl)+1);
		}
	}
}

//扫描完成前校验
function finishBeforeCheck(){
	hashMap2.removeAll();
	var ids = $j("#tbl-showReady").jqGrid('getDataIDs');
	var qty = null;
	var tempQty = 0;
	var length = 0;
	var skuList = "";
	var zxl = 0;
	var tempCount = 1;
	if(ids.length < 1){
		jumbo.showMsg("当前没有扫描商品！");
		return;
	}
	//校验扫描商品 执行量是否为0
	for(var i=0;i<ids.length;i++){
		var skuId = $j("#tbl-showReady").getCell(ids[i],"skuId");
		qty = $j("#"+skuId+"").val();
		if(parseInt(qty) == 0 ){
			tempQty++;
			continue;
		}
		var skuId2 = $j("#tbl-showReady").getCell(ids[i],"skuId");//商品ID
		zxl = $j("#"+skuId2+"").val(); //执行量
		if(hashMap2.get(skuId2) != null){//SN号商品，商品ID相同，数量++
			hashMap2.remove(skuId2);
			tempCount ++;
			hashMap2.put(skuId2, tempCount);
		}else{
			hashMap2.put(skuId2, zxl);
		}
	}
    var key = hashMap2.keySet();
    for (var i in key){
    	length++;
    	skuList += key[i] +";"+hashMap2.get(key[i])+",";
    }
	skuList = length+":"+ skuList; //扫描的商品集合； 规则[商品类数：商品ID;商品数量,]
	skuList = skuList.substring(0, skuList.length-1);
	if(tempQty == ids.length){
		jumbo.showMsg("当前扫描商品执行量为0！");
		return;
	}
	//根据配货清单查找作业单
	if(staList == null || staList == ""){
		var msg =  "配货批次号:"+$j("#verifyCode").html() +"配货清单关联的作业单号不存在！";
		errorTipOK(i18(msg));
	}else{
		var skus = "";
		var isCheck = false;
		var refSlipCode = "",code = "",index = "",trackingAndSku= "" ,staId= "",isCod= "",lpCode = "",ext = "",staStatus="";
		staCodes = "";
		staIds = "";
		staQtyInfo = "";
		//遍历作业单数据
		for(var idx in staList){
			staId = staList[idx].id;
			currentStaId = staId;
			skus = staList[idx].skus;//商品集合
		    staStatus = staList[idx].intStaStatus;
		    staCodes =  staList[idx].code;
		    staIds = staId;
			var staLine = staList[idx].extInfo;//明细
		    staQtyInfo = staList[idx].extInfo2;//商品数量明细
		    if(staList[idx].refSlipCode == null){
				continue;
			}
			if(true != isCurrentStaChecked() && (2 == staStatus && 3 != staStatus || 15 == staStatus)){
				isCheck = checkSkus(staId,staLine,skuList);
			}
			if(isCheck){
				refSlipCode = staList[idx].refSlipCode;//相关单据号
				code = staList[idx].code;//作业单号
				index = staList[idx].index;//箱号
				trackingAndSku = staList[idx].trackingNo;//快递单号
				isCod = staList[idx].isCod;//是否Cod
				lpCode = staList[idx].lpcode;//物流
				ext = staList[idx].extTransOrderId;//电子面单
				break;
			}
		}
		if(isCheck){
			if(staStatus == 15){
//				var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/checkisCheckQty.json?sta.id="+staIds);
//				if(rs.result=='success'){
//			    	$j("#barCode").blur();
//					errorTipSure("作业单号："+staCodes+"状态为取消已处理！");
//					return;
//			    }else{
//			    	var msg = "未找到扫描商品匹配的作业单，请确认重新扫描！";
//					errorTipOK(i18(msg));
//			    }
				var newArry = staQtyInfo.split(";");
				var jhl = "",zxl="";
				for(var i = 0 ; i < newArry.length; i++){
					jhl = newArry[i].substring(newArry[i].indexOf(":")+1,newArry[i].indexOf(","));
					zxl = newArry[i].substring(newArry[i].indexOf(",")+1,newArry[i].length);
					if(jhl == zxl){
						var msg = "未找到扫描商品匹配的作业单，请确认重新扫描！";
						errorTipOK(i18(msg));
						return;
					}
				}
				$j("#barCode").blur();
				errorTipSure("作业单号："+staCodes+"状态为取消未处理！");
				return;
			}
			if(ext != "" && ext != null && lpCode != "JD" && isCod != "1"){
				$j("#tbl-trank-list").removeClass("hidden");
				$j("#tbl-trank-button").removeClass("hidden");
			}else{
				$j("#tbl-trank-list").addClass("hidden");
				$j("#tbl-trank-button").addClass("hidden");
			}
			
			$j("#slipCode").html(refSlipCode);//相关单据号
			$j("#staCode").html(code);//作业单号
			$j("#boxNum").html(index);//箱号
			$j("#expressBill").html(trackingAndSku);//快递单号
			tempStaId = staId;
			$j("#printTrunkPackingList").blur();
			$j("#dialog_msg").dialog("open");
			$j("#printTrunkPackingList").blur();
			$j("#confirmBarCode").focus();
//			printTrunk();//打印装箱清单
//			printDelivery();//打印物流面单
			printAll();//打印装箱清单和物流面单
			
		}else{
			var msg = "未找到扫描商品匹配的作业单，请确认重新扫描！";
			errorTipOK(i18(msg));
		}
	}
}

//核对作业单的skus和扫描商品是否一致
function checkSkus(staId,staLine,skuList){
	hashMap3.removeAll();
//	hashMap4.removeAll();
	var oldHead = "",oldBody = "",newHead = "",newBody = "";
	//商品种数字符串
	oldHead = staLine.substring(0,staLine.indexOf(":"));
	newHead = skuList.substring(0,skuList.indexOf(":"));
	if(oldHead != newHead){//验证商品种数
		return false;
	}
	//商品id和数量的部分字符串
	oldBody = staLine.substring(staLine.indexOf(":")+1,staLine.length);
	newBody = skuList.substring(skuList.indexOf(":")+1,skuList.length);
	var oldArray = oldBody.split(",");
	var newArray = newBody.split(",");
	for(var i = 0 ; i < oldArray.length; i++){
		hashMap3.put(oldArray[i], oldArray[i]);//将后台作业单的skus存入map3
//		hashMap4.put(oldArray[i], oldArray[i]);//将后台作业单的skus存入map4,用于校验与作业单明细的商品是否完全一致
	}
	for(var j = 0 ; j < newArray.length; j++){//前台数据与后台比较，如不存在，则报错。如存在，删掉存在的id。
		if(hashMap3.get(newArray[j]) != null){
			hashMap3.remove(newArray[j]);
		}else{
			return false;
		}
	}
	var key = hashMap3.keySet();
	if(0 == key.length){ //完全匹配.之后校验作业单明细的商品是否安全匹配
//		var oldSkuArray = staLine;
//		oldSkuArray = oldSkuArray.split(",");
//		for(var k = 0 ; k < oldSkuArray.length; k++){//前台数据与后台比较，如不存在，则报错。如存在，删掉存在的id。
//			//alert(oldSkuArray[k]);
//			if(hashMap4.get(oldSkuArray[k]) != null){
//				hashMap4.remove(oldSkuArray[k]);
//			}else{
//				return false;
//			}
//		}
//		var key2 = hashMap4.keySet();
//		if(key2 == ""){
//			return true;
//		}else{
//			return false;
//		}
		return true;
	}else{
		return false;
	}
}
//删除扫描商品
function deleteSku(tag){
	var id = $j(tag).parents("tr").attr("id");
	var barCode= $j("#tbl-showReady").getCell(id,"barcode");
	var snNumber= $j("#tbl-showReady").getCell(id,"snNumber");
	$j("#tbl-showReady").find("tr[id="+id+"]").remove();
	hashMap.remove(barCode+","+snNumber);
}

//核对方法
function confirm(){
	isChecking = true;
	var expressBill = $j("#expressBill").html();
	if(expressBill == null || expressBill == ""){
		jumbo.showMsg("核对失败：快递单号为空！");
		$j("#confirmBarCode").focus();
		return;
	}
	var data={};
	var tempI = -1;
	data["packageInfos[" + 0 + "].trackingNo"] = expressBill;//快递单号
	data["sta.id"] = tempStaId;
    //SN号数据提交
    var key = hashMap.keySet();
    for (var i in key){
    	tempI++;
    	data["snlist["+ tempI +"]"] = hashMap.get(key[i]);
    }
    var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/staSortingCheck.json",data);
    if(rs.result=='success'){
    	checkedStaIds.push(tempStaId);
//    	var pickUrl = $j("body").attr("contextpath") + "/findPickingDetail.json?pickinglistId="+tempPinkId;
//    	$j("#tbl-showDetail").jqGrid('setGridParam',{url:pickUrl,datatype: "json",}).trigger("reloadGrid");
    	updateWebDate();
    	$j("#barCode").attr("value","");
		$j("#barCode").focus();
		hashMap.removeAll();
    	$j("#tbl-showReady").clearGridData(false);
    	$j("#dialog_msg").dialog("close");
    	jumbo.showMsg("操作成功！");
    	isChecking = false;
//    	var allChecked = isAllChecked();
//    	if(true == allChecked){
//    		
//    		jumbo.showMsg("操作成功！");
//    		
//        	window.location.reload();
//    	}else{
//    		$j("#close").click();
//    		$j("#barCode").focus();
//    		
//    	}

//		$j("#barCode").focus();
//		var pickUrls = baseUrl + "/findPickingDetail.json?pickinglistId="+tempPinkId;
//		$j("#tbl-showDetail").jqGrid('setGridParam',{url:pickUrls,datatype: "json",}).trigger("reloadGrid");
//		try{
//			var pNo = $j.trim($j("#verifyCode").val());
//			var staCode = $j.trim($j("#staCode").val());
//			var timestamp=new Date().getTime();
//			creatZipJs(dateValue + "/" + pNo + "/" + staCode,timestamp);//打包图片
//			updateHttpJs(dateValue + "/" + pNo + "/" + staCode,timestamp);//发送图片zip http服务器
//			loxia.syncXhr($j("body").attr("contextpath") + "/creatStvCheckImg.json",{"staCode":staCode,"fileUrl":"/"+dateValue + "/" + pNo + "/" + staCode});//保存STV图片路径
//		} catch (e) {
//		};
	}else {
		jumbo.showMsg(rs.msg);
		if(rs.msg == "执行失败,作业单已取消,系统自动更改核对量"){
			updateWebDate();
			$j("#tbl-showReady").clearGridData(false);
			$j("#dialog_msg").dialog("close");
			$j("#barCode").attr("value","");
			$j("#barCode").focus();
			hashMap.removeAll();
		}
	}
}
//input键盘抬起事件
function onKeyUp(tag){
	var id = $j(tag).parents("tr").attr("id");
	var data = $j("#tbl-showReady").jqGrid("getRowData",id);
	var skuId = data["skuId"];
//	var cyl = data["cyl"];
	tag.value=tag.value.replace(/\D/g,'');
	var zxl = $j("#"+skuId+"").val();
	setTimeout(function(){
		if(zxl == ""){
			$j("#"+skuId+"").attr("value","0");
		}
		if(parseInt(zxl) > tempZxl ){
			jumbo.showMsg("执行量只能减少不能增加！");
			$j("#"+skuId+"").attr("value",tempZxl);
		}
	},200);
}

//input获取焦点
function onFocus(tag){
	tempZxl = null;
	var id = $j(tag).parents("tr").attr("id");
	var data = $j("#tbl-showReady").jqGrid("getRowData",id);
	var skuId = data["skuId"];
	tempZxl = $j("#"+skuId+"").val();
}

//拍照
function cameraPhoto(pNo, staNo, barCode,timestamp) {
	cameraPhotoJs(dateValue + "/" + pNo + "/" + staNo, barCode + "_" + userName+"_"+timestamp);
}

function errorTipOK(text) {
	$j("#dialog_error_ok").removeClass("hidden");
	$j("#error_text_ok").html("<font style='text-align:center;font-size:32px' color='red'>"+text+"</font>");
	$j("#dialog_error_ok").dialog("open");
	$j("#dialog_error_close_ok").blur();
}

function errorTipSure(text) {
	$j("#dialog_sure_ok").removeClass("hidden");
	$j("#dialog_text_ok").html("<font style='text-align:center;font-size:32px' color='red'>"+text+"</font>");
	$j("#dialog_sure_ok").dialog("open");
	$j("#dialog_sure_button").blur();
	$j("#confirmBarCode2").focus();
	
}

function printTrank(staId ,packId){
	if((staId == null || staId == "") && (packId == null || packId == "")){
		jumbo.showMsg("打印失败：系统异常！");
	}else{
		loxia.lockPage();
		jumbo.showMsg("面单打印中，请等待...");
		var url = $j("body").attr("contextpath") + "/printSingleDeliveryMode2Out.json?sta.id="+staId+"&trankCode="+packId;
		printBZ(loxia.encodeUrl(url),false);				
		loxia.unlockPage();
	}
}

//打印装箱清单
function printTrunk(){
	jumbo.showMsg(i18("TRUNKPACKINGLISTINFO"));
	var url = $j("body").attr("contextpath") + "/printtrunkpackinglistmode1Out.json?plCmd.id=" + tempPinkId + "&plCmd.staId=" + tempStaId + "&isPostPrint=true";
	printBZ(loxia.encodeUrl(url),false);		
}
//打印物流面单
function printDelivery(){
	var expressBill = $j("#expressBill").html();
	if(expressBill == null || expressBill == ""){
		$j("#confirmBarCode").focus();
		jumbo.showMsg("物流面单打印失败：快递单号为空！");
	}else{
		jumbo.showMsg(i18("DELIVERYINFO"));
		var url = $j("body").attr("contextpath") + "/printsingledeliverymode1.json?sta.id=" + tempStaId;
		printBZ(loxia.encodeUrl(url),false);					
	}
}
//打印装箱清单、物流面单合并为一个请求
function printAll(){
	var expressBill = $j("#expressBill").html();
	if(expressBill == null || expressBill == ""){
		$j("#confirmBarCode").focus();
		jumbo.showMsg("打印失败：快递单号为空！");
	}else{
		jumbo.showMsg(i18("DELIVERYINFO"));
		var url = $j("body").attr("contextpath") + "/printTrankAndDelivery.json?plCmd.id=" + tempPinkId + "&plCmd.staId=" + tempStaId + "&isPostPrint=true";
		printBZ(loxia.encodeUrl(url),false);					
	}
}
function isAllChecked(){
	var isChecked = true;
	var dataList = $j("#tbl-showDetail").jqGrid("getRowData");
	for(var i = 0; i < dataList.length; i++){
		var qty = parseInt(dataList[i].quantity);
		var checkQty = parseInt((dataList[i].checkQuantity) == "" ? "0" : (dataList[i].checkQuantity));
		if(qty != checkQty){
			isChecked = false;
			break;
		}
	}
	return isChecked;
}

function isCurrentStaChecked(){
	var isChecked = false;
	for(var k = 0; k < checkedStaIds.length; k++){
		var checkedId = checkedStaIds[k];
		if(currentStaId == checkedId){
			isChecked = true;
			break;
		}
	}
	return isChecked;
}

//自定义HashMap
function HashMap(){ 
	this.map = {};
} 
HashMap.prototype = {
		put : function(key , value){
			this.map[key] = value;
		},
		get : function(key){
			if(this.map.hasOwnProperty(key)){
				return this.map[key];
			}
			return null;
		}, 
		remove : function(key){
			if(this.map.hasOwnProperty(key)){
				return delete this.map[key];
			}
			return false;
		}, 
		removeAll : function(){
			this.map = {};
		},
		keySet : function(){
			var _keys = [];
			for(var i in this.map){
				_keys.push(i);
			} 
			return _keys;
		}
	};
HashMap.prototype.constructor = HashMap;  

//商品维护
var hashMap = new HashMap();//sn号存储
var hashMap2 = new HashMap();//商品ID存储
var hashMap3 = new HashMap(); //后台作业明细商品存储
//var hashMap4 = new HashMap(); //后台作业明细商品ID存储


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


function updateWebDate(){
	var newArry = staQtyInfo.split(";");
	var jhl ="",zxl="",ext2="",temp="",skuId="",skuIds="";
	for(var i = 0 ; i < newArry.length; i++){ //获取数据
		jhl = newArry[i].substring(newArry[i].indexOf(":")+1,newArry[i].indexOf(","));
		zxl = newArry[i].substring(newArry[i].indexOf(",")+1,newArry[i].length);
		skuId = newArry[i].substring(0,newArry[i].indexOf(":"));
		temp = newArry[i].replace(zxl,jhl);
		ext2 +=temp+";";
		skuIds +=skuId+","+jhl+";";
	}
	for(var idx in staList){ //重新设置前台商品数量参数
		if(staIds == staList[idx].id){
			staList[idx].extInfo2 = ext2.substring(0, ext2.length-1);
		}
	}
	var skuArry = skuIds.split(";");
	var skuId2 = "",zxl1="",zxl2="";
	for(var i = 0 ; i < skuArry.length; i++){ //修改表格数据
		skuId2 = skuArry[i].substring(0,skuArry[i].indexOf(","));
		zxl2 = skuArry[i].substring(skuArry[i].indexOf(",")+1,skuArry[i].length);
		zxl1 = $j("#tbl-showDetail").getCell(skuId2,"checkQuantity");
		if(zxl1 == null || zxl1 ==""){
			zxl1 =0;
		}
		$j("#tbl-showDetail").setCell(skuId2,"checkQuantity",parseInt(zxl1)+parseInt(zxl2),null,null,null);
	}
	
	//删除已核对的数据
    for(var idx in staList){
		var staQtyInfo2 = staList[idx].extInfo2;//商品数量明细
		var skuArry = staQtyInfo2.split(";");
		var jhl="",zxl="";
		for(var i = 0 ; i < skuArry.length; i++){ //修改表格数据
			jhl = skuArry[i].substring(skuArry[i].indexOf(":")+1,skuArry[i].indexOf(","));
			zxl = skuArry[i].substring(skuArry[i].indexOf(",")+1,skuArry[i].length);
			if(jhl == zxl){
				staList[idx].refSlipCode= null;
				break;
			}
		}
    }
}