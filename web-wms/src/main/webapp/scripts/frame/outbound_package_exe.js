$j.extend(loxia.regional['zh-CN'],{
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

//显示明细
function showDetail(tag){
	 $j("#barcode").focus();
	 $j("#defectImperfect").addClass("hidden");
	var tr= $j(tag).parents("tr");
	var staCode=tr.children().eq(5).text();
	var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/stalineStauts.do?staCode="+staCode);
	if(rs.msg!="success"){
		if(rs.msg=="error"){
			jumbo.showMsg("系统异常!");
			return;
		}else{
			jumbo.showMsg(rs.msg);
			return;
		}
	}else{
		if(rs.statusName=="残次品"){
			var owner=rs.owner;
			/*var channel = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getByName.do",{"owner":owner});//渠道信息
				if(channel.isImperfect==1){
					$j("#statusName").val(rs.statusName);
					$j("#defectImperfect").removeClass("hidden");
					$j("#defectCode").empty();
				}else{*/
					var warehouse = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getWarehouseInfo.do",{"owner":owner});//仓库信息
					if(warehouse.isImperfect==1){
						$j("#statusName").val(rs.statusName);
						$j("#defectImperfect").removeClass("hidden");
						$j("#defectCode").empty();
					}
				//}
		}
	}
	$j("#divMain").addClass("hidden");
	$j("#divDetail").removeClass("hidden");
	$j("#divMsg").attr("success","");
	$j("#numbers").html(0);  
	$j("#defectnumbers").html(0);  
	var id = $j(tag).parents("tr").attr("id");
	var data = $j("#tbl_sta_package").jqGrid('getRowData',id);
	var type = data["staType"];
	$j("#cartonId").val(id);
	$j("#staType").val(type);
	$j("#tblCartonCode").html(data["code"]);
	$j("#tblCartonSeqNo").html(data["seqNo"]);
	$j("#tblStaCode").html(data["staCode"]);
	$j("#tblSlipCode").html(data["staSlipCode"]);
	$j("#tblCreateTime").html(data["createTime"]);
	$j("#tblRececiver").html(data["receiver"]);
	$j("#tblMobile").html(data["mobile"]);
	$j("#tblAddress").html(data["address"]);
	$j("#packageNum").val("");
	$j("#goodsWeight").val("");
	startWeight();
	if(type == '101'){
		$j("#printDeliveryInfo").addClass("hidden");
	}
	if(type == '102'){
		$j("#printDeliveryInfo").removeClass("hidden");
	}
	
	var dt = $j("#tbl_sta_package_line").jqGrid('getRowData');
	$j.each(dt,function(i, l){
		$j("#tbl_sta_package_line").jqGrid('delRowData',l.id,dt);	
	});
	$j("#barCode_tab tbody:eq(0) tr").remove();
	loxia.initContext($j("#tbl_sta_package_line"));
	loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/updatecartonpacking.json"),{"cartonId":id});
}
//修改扫描数量
function changeQty(tag){
	$j("#dialog_change").dialog("open");
	var id = $j(tag).parents("tr").attr("id");
	var data = $j("#tbl_sta_package_line").jqGrid('getRowData',id);
	$j("#tblBarcode").html(data["barcode"]);
	$j("#txtQty").val(data["qty"]);
	$j("#txtId").val(data["id"]);
}

var isNeedWrapStuff = false;
$j(document).ready(function (){
	restart();//重启秤
	$j("#dialog_change").dialog({title: "提示信息", modal:true, autoOpen: false, width: 400});
	$j("#dialog_msg").dialog({title: "提示信息", modal:true, autoOpen: false, width: 400});
	$j("#dialog_barcode_error").dialog({title: "提示信息", modal:true, autoOpen: false, width: 400});
	$j("button[action=add]").addClass("hidden");
	var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/findwarehousebaseinfo.json"));
	if(rs && rs.warehouse && rs.warehouse.isNeedWrapStuff){
		isNeedWrapStuff = true;
		$j("#divStuff").removeClass("hidden");
	}else{
		$j("#divStuff").addClass("hidden");
	}

	var cartonStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"cartonStatus"});
	$j("#tbl_sta_package").jqGrid({
		url:window.$j("body").attr("contextpath")+"/findcartonlist.json",
		datatype: "json",
		colNames: ["ID","箱号","箱号编码","状态","作业单","相关单据号","创建时间","receiver","mobile","address","","相关单据号2(LOAD KEY)"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name:"seqNo", index:"car.seqno", width:50,resizable:true},
					{name:"code",index:"car.code",width:100,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, resizable:true},
					{name:"intStatus", index:"car.status" ,width:80,resizable:true,formatter:'select',editoptions:cartonStatus},
					{name:"staCode",index:"sta.code",width:100, resizable:true},
					{name:"staSlipCode",index:"sta.slip_code",width:100, resizable:true},
					{name:"createTime",index:"car.create_time",width:130,resizable:true},
					{name:"receiver",width:130,resizable:true,hidden: true},
					{name:"mobile",width:130,resizable:true,hidden: true},
					{name:"address",width:130,resizable:true,hidden: true},
					{name:"staType",index:"staType",width:130,resizable:true,hidden: true},
					{name:"slipCode2",index:"slipCode2",width:150,resizable:true}
				   ],
		caption: "装箱列表",
	   	sortname: 'id',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
		sortorder: "desc",
	   	pager:"#tbl_sta_package_page",	   
		viewrecords: true,
   		rownumbers: true,
		jsonReader: { repeatitems : false, id: "0" }
	});

	$j("#tbl_sta_package_line").jqGrid({
		datatype: "json",
		colNames: ["ID","条码","数量","操作"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name:"barcode",width:120, resizable:true,sortable:false},
					{name:"qty",width:120, resizable:true,sortable:false},
					{name:"btn", hidden: true, width: 120, 
						formatter:"buttonFmatter", formatoptions:{"buttons":{label:"修改", onclick:"changeQty(this);"}}}
				   ],
		caption: "装箱列表",
	   	sortname: 'id',
	    multiselect: false,
	    rowNum: -1,
		rowList:-1,
	   	height:"auto",
		sortorder: "desc",
		viewrecords: true,
   		rownumbers: true,
		jsonReader: { repeatitems : false, id: "0" }
	});

	//查询
	$j("#query").click(function(){
		var postData = loxia._ajaxFormToObj("formQuery");
		$j("#tbl_sta_package").jqGrid('setGridParam',{
			url:window.$j("body").attr("contextpath")+"/findcartonlist.json",
			postData:postData,
			page:1
		}).trigger("reloadGrid");
	});

	//查询重置
	$j("#btnReset").click(function(){
		$j("#formQuery input").val("");
	});
	$j("#defectCode").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			var barcode = $j("#barcode").val().trim();
			if(barcode == ""){
				return;
			}
			var defectCode = $j("#defectCode").val().trim();
			if(defectCode == ""){
				return;
			}
			var barCodeNums=parseInt($j("#numbers").text());
			var defectnumbers=	parseInt($j("#defectnumbers").text());
			if(barCodeNums<=defectnumbers){
				jumbo.showMsg("操作有误!");
				 $j("#defectCode").val("");
				 $j("#barcode").val("");
				 $j("#barcode").focus();
			}
			var defectCodes=$j("#defectCodes").val();
			if(defectCodes!=""){
				var arrayObj= defectCodes.split(",");
				for (var key in arrayObj) {
					 var arr=arrayObj[key].split("-");
					if(arr[1]==defectCode){
						jumbo.showMsg("不能重复操作残次条码:"+defectCode);
						$j("#defectCode").val("");
						return;
					}
				}
			}
			var postData = {};
			postData["barCode"]=barcode;
			postData["defectCode"]=defectCode;
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/validatedefectCode.json",postData);
			if(rs.msg!="success"){
				if(rs.msg=="error"){
					jumbo.showMsg("系统异常!");
				}else{
					jumbo.showMsg(rs.msg);
				}
			}else{
				var defectCodes=$j("#defectCodes").val();
				if(defectCodes==""){
					$j("#defectCodes").val(barcode+"-"+defectCode);
				}else{
					$j("#defectCodes").val($j("#defectCodes").val()+","+barcode+"-"+defectCode);
				}
				var nums=parseInt($j("#defectnumbers").text())+1;
				 $j("#defectnumbers").html(nums);
				 $j("#defectCode").val("");
				 $j("#barcode").val("");
				 $j("#barcode").focus();
				 
			}
		}
		});

	$j("#barcode").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			
			var barcode = $j("#barcode").val().trim();
			if(barcode == ""){
				return;
			}
			var status=$j("#statusName").val();
			if(status=="残次品"){
				var barCodeNums=parseInt($j("#numbers").text());
				var defectCode=	parseInt($j("#defectnumbers").text());
				if(barCodeNums!=defectCode){
				
					 $j("#defectCode").val("");
					 $j("#barcode").val("");
					 $j("#barcode").focus();
					 jumbo.showMsg("操作有误!");
					 return;
				}
			}
			var data = $j("#tbl_sta_package_line").jqGrid('getRowData');
			var isFound = false;
			var maxId;
			$j.each(data,function(i, l){
				if(!isFound && l["barcode"] == barcode){
					isFound = true;
					//先校验数量
					l["qty"] = parseInt(l["qty"]) + 1;
					var postData = {};
					postData["staCode"]=$j("#tblStaCode").text();
					postData["barCode"]=barcode;
					postData["qty"]=l["qty"];
					var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/validateisplanornot.json",postData);
					if(rs.msg!="success"){
						if(rs.msg=="error"){
							jumbo.showMsg("系统异常!");
						}else{
							loxia.tip($j("#barcode"),rs.msg);
							return;
						}
					}else{
						//找到增加数量
						$j("#tbl_sta_package_line").jqGrid('setRowData',l.id,l);
						loxia.initContext($j("#tbl_sta_package_line"));
						
						if(status=="残次品"){
							$j("#defectCode").focus();
							
						}else{
							$j("#barcode").val("");
							$j("#barcode").focus();
						}
					}
				}
				if(maxId && maxId < l["id"]){
					maxId = l["id"];
				}else if(!maxId){
					maxId = l["id"];
					
				} 
				nums+=parseInt(l["qty"]);
			});
			if(!isFound){
				//添加校验未找到行，校验barCode是否在计划之内
				var postData = {};
				postData["staCode"]=$j("#tblStaCode").text();
				postData["barCode"]=barcode;
				postData["qty"]=1;
				var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/validateisplanornot.json",postData);
				if(rs.msg!="success"){
					if(rs.msg=="error"){
						jumbo.showMsg("系统异常!");
					}else{
						loxia.tip($j("#barcode"),rs.msg);
						return;
					}
				}else{
					//未找到增加行
					var data = {};
					data.id = 1;
					if(maxId){
						data.id = maxId + 1;
					}
					data["barcode"] = barcode;
					data["qty"] = 1;
					$j("#tbl_sta_package_line").jqGrid('addRowData',data.id,data);	
					loxia.initContext($j("#tbl_sta_package_line"));
					var status=$j("#statusName").val();
					if(status=="残次品"){
						$j("#defectCode").focus();
					}else{
						$j("#barcode").val("");
						$j("#barcode").focus();
					}
				}
			}
			
//			$j("#barcode").val("");
//			$j("#barcode").focus();
			
			//计算扫描的总数量
			var nums=0;
			var rowIds = $j("#tbl_sta_package_line").jqGrid('getDataIDs');  
			for(var i = 0, j = rowIds.length; i < j; i++) {   
		        var curRowData = $j("#tbl_sta_package_line").jqGrid('getRowData', rowIds[i]);
		        nums+=parseInt(curRowData["qty"]);   
			}
			 $j("#numbers").html(nums);
		}
		
	});

	//包材条码扫描
	$j("#pgBarcode").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			var barcode = $j("#pgBarcode").val().trim();
			if(barcode == ""){
				return;
			}
			// 判断是否为耗材
			var result = loxia.syncXhrPost(loxia.getTimeUrl(window.$j("body").attr("contextpath")+"/getSkuTypeByBarCode.json?barCode="+barcode));
			if (!result || result.value != 1){
				$j("#pgBarcode").val("");
				jumbo.showMsg("该包材不存在或为非耗材!");
				return;
			}
			var isAdd = false;
			$j("#barCode_tab tbody tr").each(function (i,tag){
				if(barcode == $j(tag).find("td:eq(1) :input").val()){
					var index = $j(tag).index;
					$j(tag).find("td:eq(2) :input").val(parseInt($j(tag).find("td:eq(2) :input").val())+1);
					isAdd = true;
				}
			});
			if(!isAdd){
				$j("button[action=add]").click();
				var length = $j("#barCode_tab tbody tr").length-1;
				$j("#barCode_tab tbody tr:eq("+length+") td:eq(1) :input").val(barcode);
				$j("#barCode_tab tbody tr:eq("+length+") td:eq(2) :input").val(1);
			}
			$j("#pgBarcode").val("");
			//$j("#doConfirm").focus();
			$j("#packageNum").focus();
		}
	});

	//修改
	$j("#btnChange").click(function(){
		var id = $j("#txtId").val();
		if(loxia.byId("txtQty").check()){
			var qty = loxia.byId("txtQty").val();
			if(qty<0){
				loxia.tip($j("#txtQty"),"数量必须大于等于0!");
				return;
			}else if(qty==0){
				$j("#tbl_sta_package_line").jqGrid('delRowData',id);
				loxia.initContext($j("#tbl_sta_package_line"));
				$j("#dialog_change").dialog("close");
				$j("#txtId").val("");
				$j("#txtQty").val("");
			}else{
				var postData = {};
				postData["staCode"]=$j("#tblStaCode").text();
				postData["barCode"]=$j("#tblBarcode").text();;
				postData["qty"]=qty;
				var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/validateisplanornot.json",postData);
				if(rs.msg!="success"){
					if(rs.msg=="error"){
						jumbo.showMsg("系统异常!");
					}else{
						loxia.tip($j("#txtQty"),rs.msg);
					}
				}else{
					var data = $j("#tbl_sta_package_line").jqGrid('getRowData',id);
					data["qty"] = qty;
					$j("#tbl_sta_package_line").jqGrid('setRowData',data.id,data);	
					loxia.initContext($j("#tbl_sta_package_line"));
					$j("#dialog_change").dialog("close");
					$j("#txtId").val("");
					$j("#txtQty").val("");
				}
			}
			
		}
		//单机修改后   计算扫描的总数量
		var nums = 0;
		var data = $j("#tbl_sta_package_line").jqGrid('getRowData');
		$j.each(data,function(i, l){
				l["qty"] = parseInt(l["qty"]);
				nums+=parseInt(l["qty"]);
		
		});
		$j("#numbers").html(nums);
	});
	
	$j("#packageNum").keydown(function(event){
		if(event.keyCode == 13){
			var data = $j("#tbl_sta_package_line").jqGrid('getRowData');
			var skuNum = 0;
			var packageNum = $j("#packageNum").val();
			if(packageNum == "" || packageNum <= 0){
				jumbo.showMsg("请输入本箱数量，并且必须大于0!");
				return;
			}
			$j.each(data,function(i, l){
				skuNum += parseInt(l["qty"]);
			});
			if(skuNum != packageNum){
				jumbo.showMsg("本箱数量与扫描的汇总数量不相等!");
				return;
			}
			$j("#confirmWeightInput").focus();
		}
	});
	
	$j("#confirmWeightInput").keydown(function(event){
		if(event.keyCode == 13){
			if ($j("#confirmWeightInput").val().toUpperCase() == BARCODE_CONFIRM){
				$j("#goodsWeight").val($j("#autoWeigth").val());
				$j("#doConfirm").focus();
			}else if($j("#confirmWeightInput").val() != "" ) {
				jumbo.showMsg("输入重量确认条码不正确，请重新输入.");
				$j("#confirmWeightInput").val("");
			}else if($j("#confirmWeightInput").val() == "" ) {
				jumbo.showMsg("请输入重量确认条码.");
				$j("#confirmWeightInput").val("");
			}
		}
	});
	
	$j("#goodsWeight").keydown(function(event){
		if(event.keyCode == 13){
			event.preventDefault();
			if(null == $j("#goodsWeight").val() || '' == $j("#goodsWeight").val()){
				$j("#goodsWeight").val($j("#autoWeigth").val());
			}
			var value = $j("#goodsWeight").val();
			if(value == ""){
				jumbo.showMsg("请输入货物重量");
				return;
			}
			if(!/^[0-9]*\.{0,1}[0-9]*$/.test(value)){
				jumbo.showMsg("货物重量不是一个合法的数字或精度要求不符合要求");
				return;
			}
			value = parseFloat(value);
			if(value == 0){
				jumbo.showMsg("请输入货物重量");
				return;
			}else if(value > 150){
				jumbo.showMsg("货物重量不得超过150KG");
				return;
			}	
			$j("#doConfirm").focus();
		}
	});
	

	$j("#restWeight").click(function(){
		restart();
	});

	//执行打包
	$j("#btnExe").click(function(){
		$j("#doConfirm").val("");
		var postData = {};
		postData["cartonId"] = $j("#cartonId").val();
		var data = $j("#tbl_sta_package_line").jqGrid('getRowData');
		var skuNum = 0;
		var packageNum = $j("#packageNum").val();
		var weight = $j("#goodsWeight").val();
		var status=$j("#statusName").val();
		var barCodeNums=parseInt($j("#numbers").text());
		var defectCode=	parseInt($j("#defectnumbers").text());
		if(status=="残次品"){
			if(barCodeNums!=defectCode){
				jumbo.showMsg("商品数量和残次条码数量不一致");
				return;
			}
			postData["isImperfect"]=true;
			postData["defectCode"]=$j("#defectCodes").val();
		}else{
			postData["isImperfect"]=false;
		}
		if(packageNum == "" || packageNum <= 0){
			jumbo.showMsg("请输入本箱数量，并且必须大于0!");
			return;
		}
		$j.each(data,function(i, l){
			postData["lines["+i+"].skuBarcode"] = l["barcode"];  
			postData["lines["+i+"].qty"] = l["qty"];  
			skuNum += parseInt(l["qty"]);
		});
		if(skuNum != packageNum){
			jumbo.showMsg("本箱数量与扫描的汇总数量不相等!");
			return;
		}
		if(isNeedWrapStuff){
			if($j("#barCode_tab tbody:eq(0) tr").length == 0){
				$j("#divMsg").html("请扫描包材！");
				$j("#divMsg").attr("success","false");
				$j("#dialog_msg").dialog("open");
				$j("#txtExtConfirm").focus();
				return;
			}
			$j("#barCode_tab tbody:eq(0) tr").each(function(i,tag){
				var barcode = $j(tag).find("td:eq(1) input").val();
				var qty = $j(tag).find("td:eq(2) input").val();
				postData["addls["+i+"].sku.barCode"] = barcode;  
				postData["addls["+i+"].quantity"] = qty;  
			});
		}
		if(!/^[0-9]*\.{0,1}[0-9]*$/.test(weight)){
			jumbo.showMsg("货物重量不是一个合法的数字或精度要求不符合要求");
			return;
		}
		var weights = parseFloat((('' == weight || null == weight)? '0' : weight));
		if(weights == 0){
			jumbo.showMsg("请输入货物重量");
			return;
		}else if(weights > 150){
			jumbo.showMsg("货物重量不得超过150KG");
			return;
		}
		postData["weight"] = weight;
		var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/exeoutboundpackage.json"),postData);
		if(rs && rs.result && rs.result == "success"){
			var type = $j("#staType").val();
			$j("#defectCodes").val("");
			$j("#defectnumbers").html(0);
			$j("#divMsg").html("执行成功");
			$j("#divMsg").attr("success","true");
			printPg($j("#cartonId").val());
			var code = $j("#tblStaCode").html();
			var sta = loxia.syncXhr(loxia.getTimeUrl($j("body").attr("contextpath") + "/json/queryStaByCode.do?sta.code="+code));
			if(sta && sta.result && sta.result == "success"){
				if(sta.sta.owner == '1Nike官方旗舰店' && (type == '101' || type == '102')){
					printHuiZong(sta.sta.id,$j("#cartonId").val());
				}
			}
			if(type == '102'){
				printDeliveryInfo();	
			}
			if(type=='101'){
				printBoxLabel();
				printNikeCrwLabel();
			}
		}else{
			var msg = "执行失败。";
			if(rs.message){
				msg += rs.message;
			}
			$j("#divMsg").html(msg);
			$j("#divMsg").attr("success","false");
		}
		$j("#dialog_msg").dialog("open");
		$j("#txtExtConfirm").focus();
	});

	$j("#btnExeConfirm").click(function(){
		$j("#dialog_msg").dialog("close");
		$j("#txtExtConfirm").val("");
		if($j("#divMsg").attr("success") == "true"){
			$j("#btnBack").trigger("click");
		}else{
			//error now do nothing
		}
		$j("#divMsg").attr("success","");
	});

	$j("#txtExtConfirm").keyup(function(){
		if($j("#txtExtConfirm").val() == BARCODE_CONFIRM){
			$j("#btnExeConfirm").trigger("click");
		}
	});
	//返回
	$j("#btnBack").click(function(){
		$j("#barcode").val("");
		$j("#divMain").removeClass("hidden");
		$j("#divDetail").addClass("hidden");
		var id = $j("#cartonId").val();
		loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/updatecartoncreate.json"),{"cartonId":id});
		clearWeight();
		$j("#query").trigger("click");
	});

	$j("#doConfirm").keydown(function(){
		if($j("#doConfirm").val() == BARCODE_CONFIRM){
			$j("#btnExe").trigger("click");
		}
	});
	
	$j("#btnCancelChange").click(function(){
		$j("#txtId").val("");
		$j("#txtQty").val("");
		$j("#dialog_change").dialog("close");
	});
	

	$j("#confirmClose").click(function(){
		$j("#dialog_barcode_error").dialog("close");
		$j("#barcode").val("");
		$j("#barcode").focus();    
	});
	

});

//打印装箱明细
function printPg(id){
	loxia.lockPage();
	if (id != null){
		var url = $j("body").attr("contextpath") + "/printoutboundpackageinfo.json?staid="+ id;  
		printBZ(loxia.encodeUrl(url),false);		
	}
	loxia.unlockPage();
}
//打印汇总单
function printHuiZong(id,cartonId){
	loxia.lockPage();
	if (id != null){
		var url = $j("body").attr("contextpath") + "/printoutboundpackagemain.json?staid="+ id + "&cartonId=" + cartonId;  
		printBZ(loxia.encodeUrl(url),false);		
	}	
	loxia.unlockPage();
}
//条形码错误提示
function openBarcodeErrorDlg(msg, code){
	if("" != msg || null != msg){
		var i = msg.indexOf("对应的商品不存在!");
		var errorMsg = msg.substring(0, i);
		if("" != code)
		errorMsg += "<font color='red'>["+ code +"]</font>";
		errorMsg += msg.substring(i);
		$j("#barcodeErrorMsg").html(errorMsg);
	}
	$j("#dialog_barcode_error").dialog("open");
}

function printDeliveryInfo(){
	loxia.lockPage();
	var id =$j("#cartonId").val();
	var dt = new Date().getTime();
	//var url = $j("body").attr("contextpath") + "/printSingleVmiDeliveryMode1Out.json?rt="+ dt +"&sta.id=" + id;
	var url = $j("body").attr("contextpath") + "/printSingleVmiDeliveryMode1OutByCarton.json?cartonId=" + id;
	printBZ(loxia.encodeUrl(url),false);
	loxia.unlockPage();
}

function printNikeCrwLabel(){
	loxia.lockPage();
	var id =$j("#cartonId").val();
	var dt = new Date().getTime();
	var url = $j("body").attr("contextpath") + "/printNikeCrwLabel.json?cartonId=" + id;
	printBZ(loxia.encodeUrl(url),true);
	loxia.unlockPage();
}


/**
 * 退仓箱标签前打印
 */
function printBoxLabel(){
	loxia.lockPage();
	var id =$j("#cartonId").val();
	var code=$j("#tblStaCode").text();
	var sta = loxia.syncXhr(loxia.getTimeUrl($j("body").attr("contextpath") + "/json/queryStaByCode.do?sta.code="+code));
	var url = $j("body").attr("contextpath") + "/printPackingBoxLabel.json?cartonId=" + id+"&sta.id=" + sta.sta.id;
	printBZ(loxia.encodeUrl(url),false);
	loxia.unlockPage();
}
function clearWeight(){
	if($j("#autoWeigth").attr("clear") != "true"){
		$j("#autoWeigth").attr("clear","true");
		//关秤
		appletStop();
		//清除重量
		$j("#autoWeigth").val("");
		loxia.byId("goodsWeight").val("");
		$j("#confirmWeightInput").val("");
	}
}

function startWeight(){
	if($j("#autoWeigth").attr("clear") === "true"){
		$j("#autoWeigth").attr("clear","false");
		//开秤
		appletStart();
	}
}