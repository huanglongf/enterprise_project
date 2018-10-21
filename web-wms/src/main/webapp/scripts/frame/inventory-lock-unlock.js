$j.extend(loxia.regional['zh-CN'],{
	INPUTSTATYPE 		:"库内移动",
	GETINFO_EXEPTION	:"获取信息异常",
	OPERATE_EXEPTION	:"数据操作异常",
	LOCATION			:"库位",
	INVENTORY_QTY		:"当前库存数",
	SHELVES_QTY			:"上架数",
	IS_NOT_LOCATION		:"库位不存在或不可用",
	INPUT_NOT_NULL		:"输入不能为空",
	STA_CODE			:"作业单号",
	CREATE_TIME			:"创建时间",
	CREATER				:"创建人",
	INT_STA_STATUS		:"作业单状态",
	TOTAL_SKU_QTY		:"锁定量",
	EXE_STA				:"待解锁作业单列表",
	EXE_STA_EXP			:"待执行导入作业单",
	LOCATION_OCCUPATION	:"库存占用",
	INV_LOCK            :"库存锁定",
	TOTAL				:"总数",
	BARCODE				:"条形码",
	SKUNAME				:"商品名称",
	SKU					:"商品",
	JMCODE				:"商品编码",
	KEYPROPERTIES		:"扩展属性",
	SUPPLIERSKUCODE		:"货号",
	BATCH_CODE			:"批次号",
	KEYPROPERTIES		:"扩展属性",
	SKUCODE				:"SKU编码",
	OWNER				:"店铺",
	STATUS				:"状态",
	QTY					:"数量",
	CREATE_STA_LINE		:"创建作业单明细列表",
	SKU_NUMBER_ERROR	:"数量不正确，必须大于0的整数",
	IS_OPERATE			:"您确定要执行本次操作？",
	ADD_SKU				:"请添加须要移动的商品",
	CANCEL_INNER		:"库内移动取消成功",
	QTY_INFO_NOT_EQ		:"移动数量和实际移动数量不相等",
	LOCATION_INCORRECT	:"库位不正确",
	LOCATION_QTY		:"数量不正确",
	SUCCESS_INNER		:"库内移动执行成功",
	LOADING				:"加载中",
	P_SELECT			:"请选择",
	INPUT_FILE_ERROR	:"请选择正确的Excel导入文件",
	LOCATION_IS_NOT_NULL:"库位不能为空",
	SKU_CODE_NOT_NULL	:"SKU 编码不能为空",
	DE					:"的",
	NOT_SKU				:"{0} 商品不存在 或 {1}没有库存",
	NOT_LOCATION		:"{0} 库位不存在 或 库位上没有{1}商品",
	LOCATION_NOT_FOUNDED : "输入的库位  {0} 系统不存在",
	DATA_ERROR			:"数据错误"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}
var baseUrl = "";

function divToggle(){
	if($j("#div-sta-detail").hasClass("hidden")){
		$j("#div-sta-detail").removeClass("hidden");
		$j("#div-sta").addClass("hidden");
	}else{
		$j("#div-sta").removeClass("hidden");
		$j("#div-sta-detail").addClass("hidden");
	}
}

//列表手动出库单显示详细列表
function showdetail(obj){
	loxia.lockPage();
	$j("#input_staId").val("");
	var staId=$j(obj).parent().parent().attr("id");
	details(staId);
}

function details(staId){
	var sta = $j("#tbl_inventory_lock_list").jqGrid("getRowData",staId);
	if(sta){
		$j("#input_staId").val(staId);
		$j("#input_staCode").val(sta.code);
		$j("#input_creater").val(sta.creater);
		$j("#input_totalSkuQty").val(sta.totalSkuQty);
		$j("#input_createTime").val(sta.createTime);
		$j("#input_memo").html(sta.memo);
	}
	$j("#tbl_unlock_list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/findOccupiedStaLineByStaId.json"),
		postData:{"staCmd.id":staId}}).trigger("reloadGrid",[{page:1}]);
	$j("#inventory_lock_div").addClass("hidden");
	$j("#inventory_unlock_div").removeClass("hidden");
	loxia.unlockPage();
}

function showMsg(msg){
	jumbo.showMsg(msg);
}


function backTo(){
	//$j("#reset").click();
	var postData=loxia._ajaxFormToObj("form_query");
	$j("#tbl_inventory_lock_list").jqGrid('setGridParam',{
		url:loxia.getTimeUrl(baseUrl+"/findInventoryLockSta.json"),postData:postData
		}).trigger("reloadGrid");
	$j("#inventory_lock_div").removeClass("hidden");
	$j("#inventory_unlock_div").addClass("hidden");
}

var isChangeAll = true;
var isChange = true;
var isInit = false;

$j(document).ready(function (){
	baseUrl = $j("body").attr("contextpath");
	jumbo.loadShopList("shopS","id");
	
	$j("#startDate2").attr('disabled','disabled');
	$j("#endDate2").attr('disabled','disabled');
	$j("#startDate1").attr('disabled','disabled');
	$j("#endDate1").attr('disabled','disabled');
	$j("#tbl_inventory_lock_list").jqGrid({
			url:baseUrl+"/findInventoryLockSta.json",
			postData:loxia._ajaxFormToObj("form_query"),
			datatype: "json",
			colNames: ["ID",i18("STA_CODE"),i18("STATUS"),"invStatusId","库存状态",i18("TOTAL_SKU_QTY"),"锁定原因",i18("CREATE_TIME"),"完成时间",i18("CREATER")],
			colModel: [
		            {name:"id", index: "id", hidden: true},		         
		            {name:"code",index:"code",width:100,formatter:"linkFmatter",formatoptions:{onclick:"showdetail"}, resizable: true},
		            {name:"intStaStatus",index:"intStaStatus",width:100,resizable:true},
		            {name:"inventoryStatusId", index: "inventoryStatusId", hidden: true},
		            {name:"inventoryStatusName",index:"inventoryStatusName",width:100,hidden: true},
					{name:"totalSkuQty",index:"totalSkuQty",width:70,resizable:true},
					{name:"memo",index:"memo",width:250,resizable:true},
					{name:"createTime",index:"createTime",width:150,resizable:true},
					{name:"finishTime",index:"finishTime",width:150,hidden: true},
					{name:"creater", index:"creater", width:100, resizable:true}],
			caption: i18("EXE_STA"),
			multiselect: false,
			height:"auto",
			pager:"#pager2",
			rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
			sortname: 'createTime',
			viewrecords: true,
   			rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" },
			gridComplete: function(){
				var ids = $j("#tbl_inventory_lock_list").jqGrid('getDataIDs');
				for(var i=0;i < ids.length;i++){
					$j("#tbl_inventory_lock_list").jqGrid('setRowData',ids[i],{"intStaStatus":i18("INV_LOCK")});
				}
			}
	});
	
	$j("#tbl_unlock_list").jqGrid({
		//url:"dispatch-list-view.json",
		datatype: "json",
		//colNames: ["ID","库位编码预览","商品名称","商品编码","条形码","计划执行数量","已执行数量"],
		colNames: ["是否SN商品",i18("SKUCODE"),i18("SKUNAME"),i18("JMCODE"),"货号",i18("KEYPROPERTIES"),i18("BARCODE"),"库位","库存状态","数量"],
		colModel: [
		            {name: "isSnSku", index: "isSnSku", hidden: true},//是否SN商品
					{name:"skuCode", index:"skuCode" ,width:150,resizable:true,sortable:false},
					{name:"skuName", index:"skuName" ,width:200,resizable:true,sortable:false},
					{name: "jmcode", index:"jmcode",width:150,resizable:true,sortable:false},
					{name: "supplierCode", index:"sku.supplier_code",width:150,resizable:true,sortable:false},
					{name: "keyProperties", index:"keyProperties",width:60,resizable:true,sortable:false},
					{name:"barCode", index:"barCode", width:90,hidden: true, resizable:true},
					{name:"location", index:"location", width:90, resizable:true},
					{name:"intInvstatusName", index:"intInvstatusName", width:90, resizable:true},
					{name:"quantity",index:"quantity",width:120,resizable:true,sortable:false}],
		caption: "作业单明细列表",
	   	sortname: 'sku.code',
	    multiselect: false,
		sortorder: "desc",
		rownumbers:true,
		rowNum: -1,
		height:"auto",
		gridComplete: function() {
		},
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#query").click(function(){
		backTo();
	});
	
	$j("#reset").click(function(){
		$j("#form_query input").val("");
	});
	
	$j("#query_exp").click(function(){
		backExpTo();
	});
	
	$j("#reset_exp").click(function(){
		$j("#form_query_exp input").val("");
	});
	
	$j("#inventoryLockBatch").click(function(){
		$j("#shopShow").val("");
		$j("#locationCodeBatch").val("");
		$j("#skuCodeBatch").val("");
		$j("#productionDate").val("");
		$j("#expireDate").val("");
		$j("#inventoryStatusId").val("");
		$j("#tbl_inventory_lock_batch_list").jqGrid("clearGridData");
		$j("#invLockBatchDialog").dialog('open');  
	});

	
	$j("#tbl_temp_list").jqGrid({
		datatype: "json",
		//colNames: ["indexId","SKUID","LOCATIONID","INVENTORYSTATUSID","SKU编码","商品编码","条形码","扩展名","店铺","库位","状态","数量"],
		colNames: ["indexId","SKUID","LOCATIONID","INVENTORYSTATUSID",i18("SKUCODE"),i18("JMCODE"),i18("BARCODE"),
		           i18("KEYPROPERTIES"),i18("OWNER"),"CHANNELCODE",i18("LOCATION"),i18("STATUS"),i18("QTY"),"库存ID"],
		colModel: [
			        {name:"indexId", index: "indexId", hidden: true},
		            {name:"skuId", index: "skuId", hidden: true},
		            {name:"locationId", index: "locationId", hidden: true},	
		            {name:"inventoryStatusId", index: "inventoryStatusId", hidden: true},	
		            {name:"skuCode",index:"skuCode",width:120,resizable: true},
		            {name:"barCode",index:"barCode",width:120,resizable:true},
		            {name:"jmCode",index:"jmCode",width:120,resizable: true},
		            {name:"keyProperties",index:"keyProperties",width:100,resizable:true},
		            {name:"shopName",index:"shopName",width:150,resizable:true},
					{name:"invOwner", index:"invOwner",hidden:true,width:150, resizable:true},
					{name:"locationCode",index:"locationCode",width:130,resizable:true},
					{name:"inventoryStatusName",index:"inventoryStatusName",width:100,resizable:true},
					{name:"quantity",index:"quantity",width:80,resizable:true},
			        {name:"invIds", index: "invIds", hidden: true}
				],
		caption: i18("CREATE_STA_LINE"),
		multiselect: true,
		height:"auto",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		jsonReader: {repeatitems : false, id: "0"},
		gridComplete: function(){
			var length = $j(this).find("tbody tr").length - 1;
			$j(this).find("tbody tr:eq("+length+") td:eq(0) :input").change(function(){
				var ch = $j(this).attr("checked");
				$j(this).attr("checked",!ch);
				if(isChangeAll && !ch){
					$j("#cb_tbl_temp_list").attr("checked",true);
				} else {
					$j("#cb_tbl_temp_list").attr("checked",false);
				}
			});
			$j("#cb_tbl_temp_list").attr("checked",false);
		},
		onSelectRow:function(id){
			var chek = true;
			var temp = true;
			$j.each($j(this).find("tbody tr"),function(y,innerTr){
				var isChek = $j(innerTr).find("td:eq(0) :input").attr("checked");
				if($j(innerTr).attr("id") == id){
					$j(innerTr).find("td:eq(0) :input").attr("checked",!isChek);
					temp = !isChek;
					isChek = true;
				}
				if(y > 0 && !isChek){
					chek = isChek;
				}
			});
			if(chek && temp){
				$j("#cb_tbl_temp_list").attr("checked",true);
			} else {
				$j("#cb_tbl_temp_list").attr("checked",false);
			}
			isChangeAll = chek;
			isChange = temp;
		}
	});
	
	$j("#shopS").change(function(){
		if($j.trim($j("#locationCode").val()) != ""){
			findSku(null,null);
		} 
		if($j.trim($j("#skuCode").val()) != ""){
			findLocationCode(null,null);
		} 
	});
	
	
	//库存锁定
	$j("#inventoryLock").click(function(){
		var lockType = $j("#txtLockReason").val();
		var lockReson = $j.trim($j("#txtLockReason").find("option:selected").text());
		var memo = $j.trim($j("#txtRmk").val());
		var isMemo = ('' == memo || null == memo);
		if("" == lockType || null == lockType){
			jumbo.showMsg("请选择锁定原因");
			return;
		}
		else if('10' == lockType){
			lockReson = "其他";
			if(isMemo){
				jumbo.showMsg("其他原因必须填写备注");
				return;
			}
		}
		if(!isMemo){
			lockReson += ("," + memo);
		}
		var datalist = $j("#tbl_temp_list" ).getRowData();
		if(datalist.length > 0){
			var postData = {};
			postData["memo"]= lockReson;
			postData["lockType"]= lockType;
			var ownerList = [];
			for(var i=0,d;(d=datalist[i]);i++){
				var statusName = $j.trim(d.inventoryStatusName);
				/*if('2' == lockType){
					if('良品' == statusName){
						jumbo.showMsg("明细列表中不能包含良品");
						return;
					}
				}*/
				var currentOwner = d.invOwner;
				postData["stvLinelist[" + i + "].sku.id"]=d.skuId;
				postData["stvLinelist[" + i + "].location.id"]=d.locationId;
				postData["stvLinelist[" + i + "].invStatus.id"]=d.inventoryStatusId;
				postData["stvLinelist[" + i + "].owner"]=d.invOwner;
				postData["stvLinelist[" + i + "].quantity"]=d.quantity;
				var flag = true;
				if(0 == ownerList.length){
					flag = false;
				} else {
					flag = false;
					for(var j in ownerList){
						var c = ownerList[j];
						if(currentOwner == c){
							flag = true;
						}
						if(true == flag)
							break;
					}
				}
				if(false == flag){
					ownerList.push(currentOwner);
				}
		   	}
			if(ownerList.length > 1){
				jumbo.showMsg("待锁定的明细必须是相同店铺的商品");
				return;
			}
			if(confirm(i18("IS_OPERATE"))){
				loxia.lockPage();
				loxia.asyncXhrPost(
						baseUrl + "/inventoryLock.json",
						postData,
						{
							success:function(data){
									if(data){
										if(data.result=="success"){
											jumbo.showMsg("库存锁定成功");
											resetInventoryLockData();
											loxia.unlockPage();
										}else if(data.result=="error"){
											jumbo.showMsg(data.message);
											loxia.unlockPage();
										}
									}
								},
							error:function(){
								jumbo.showMsg(i18("OPERATE_EXEPTION"));
								loxia.unlockPage();
							}//操作数据异常
						}
				);
			}
		} else {
			jumbo.showMsg(i18("ADD_SKU"));
		}
	});

	//返回
	$j("#back").click(function(){
		backTo();
	});
	
	$j("#inventoryUnclock").click(function(){
		var staId = $j("#input_staId").val();
		if("" == staId || null == staId){
			jumbo.showMsg("数据异常");
			return;
		}
		var postData = {};
		postData["staCmd.id"]= staId;
		if(confirm(i18("IS_OPERATE"))){
			loxia.lockPage();
			loxia.asyncXhrPost(
					baseUrl + "/inventoryUnlock.json",
					postData,
					{
						success:function(data){
								if(data){
									if(data.result=="success"){
										jumbo.showMsg("库存解锁成功");
										resetInventoryLockData();
										loxia.unlockPage();
									}else if(data.result=="error"){
										jumbo.showMsg(data.message);
										loxia.unlockPage();
									}
								}
							},
						error:function(){
							jumbo.showMsg(i18("OPERATE_EXEPTION"));
							loxia.unlockPage();
						}//操作数据异常
					}
			);
		}
	});

	//根据商品和库位得出状态
	$j("#selectSku").change(function(){
		locationSelectAadNum("skuNum","selectStatus","statusSkuNum");
		var sku = $j.trim($j("#selectSku").val());
		if(sku != ""){
			var obj=$j("#selectStatus");
			removeSelect("selectStatus",i18("LOADING"));
			var postData = {};
			postData["locationCode"] = $j("#locationCode").val();
			var shopid = $j.trim($j("#shopS").val());
			if(shopid != ""){
				postData["shopId"]=shopid;
			}
			postData["skuId"] = sku;
			var rt=loxia.syncXhr(baseUrl + "/querySkuStatus.json",postData);
			if(!rt.exception){
				if(rt.size==0){
					jumbo.showMsg(i18("OPERATE_EXEPTION"));
					return 
				}
				removeSelect("selectStatus",i18("P_SELECT"));
				for(var i in rt.list){
					$j('<option value="'+rt.list[i].inventoryStatusId+'">'+rt.list[i].inventoryStatusName+'</option>').appendTo(obj);
					setSkuNum("statusSkuNum",rt.list[i].inventoryStatusId,rt.list[i].quantity);
				};
				setSkuNumber("skuNum",$j('#selectLocationSkuNum input[name="'+sku+'"]').val());
				return;
			}else{
				jumbo.showMsg(i18("GETINFO_EXEPTION"));
			}
			removeSelect("selectStatus",i18("P_SELECT"));
		}else {
			resetDateTimePicker('startDate1');
			resetDateTimePicker('endDate1');
		}
		
	});
	
	$j("#selectStatus").change(function(){
		var status = $j.trim($j("#selectStatus").val());
		if(status != ""){
			setSkuNumber("skuNum",$j('#statusSkuNum input[name="'+status+'"]').val());
			$j("#startDate1").removeAttr('disabled');
			$j("#endDate1").removeAttr('disabled');
			return;
		} 
		var sku = $j.trim($j("#selectSku").val());
		if(sku != ""){
			setSkuNumber("skuNum",$j('#selectLocationSkuNum input[name="'+sku+'"]').val());
			resetDateTimePicker('startDate1');
			resetDateTimePicker('endDate1');
			return ;
		}
		setSkuNumber("skuNum","");
		resetDateTimePicker('startDate1');
		resetDateTimePicker('endDate1');
	});
	
	
	
	$j("#selectStatus1").change(function(){
		var status = $j.trim($j("#selectStatus1").val());
		if(status != ""){
			setSkuNumber("skuNum1",$j('#statusSkuNum1 input[name="'+status+'"]').val());
			$j("#startDate2").removeAttr('disabled');
			$j("#endDate2").removeAttr('disabled');
			return;
		}
		var location = $j.trim($j("#selectLocation").val());
		if(location != ""){
			setSkuNumber("skuNum1",$j('#selectLocationSkuNum1 input[name="'+location+'"]').val());
			resetDateTimePicker('startDate2');
			resetDateTimePicker('endDate2');
			return ;
		}
		setSkuNumber("skuNum1","");
		//locationSelectAadNum('skuNum1');
		resetDateTimePicker('startDate2');
		resetDateTimePicker('endDate2');
	});
	
	
	//方式二的选择库位时所加载的数据
	$j("#selectLocation").change(function(){
		locationSelectAadNum("skuNum1","selectStatus1","statusSkuNum1");
		var locationCode = $j.trim($j("#selectLocation").val());
		if(locationCode != ""){
			removeSelect("selectStatus1",i18("LOADING"));
			var postData = {};
			postData["locationCode"] = locationCode;
			var shopid = $j.trim($j("#shopS").val());
			if(shopid != ""){
				postData["shopId"]=shopid;
			}
			postData["skuCode"] =  $j("#skuCode").val();
			var rt=loxia.syncXhr(baseUrl + "/querySkuStatus.json",postData);
			if(!rt.exception){
				var obj = $j("#selectStatus1");
				if(rt.size==0){
					jumbo.showMsg(i18("GETINFO_EXEPTION"));
					return 
				}
				removeSelect("selectStatus1",i18("P_SELECT"));
				for(var i in rt.list){
					$j('<option value="'+rt.list[i].inventoryStatusId+'">'+rt.list[i].inventoryStatusName+'</option>').appendTo(obj);
					setSkuNum("statusSkuNum1",rt.list[i].inventoryStatusId,rt.list[i].quantity);
				};
				setSkuNumber("skuNum1",$j('#selectLocationSkuNum1 input[name="'+locationCode+'"]').val());
				return;
			}else{
				jumbo.showMsg(i18("GETINFO_EXEPTION"));
			}
			removeSelect("selectStatus1",i18("P_SELECT"));
		}else {
			resetDateTimePicker('startDate2');
			resetDateTimePicker('endDate2');
		}
	});
	
	//文件导入
	$j("#inputSumit").click(function(){
		var file = $j("#tnFile").val();
		var errors = [];
		if(file==""){
			errors.push(i18("INPUT_FILE_ERROR"));//请选择正确的Excel导入文件
		}else{
			var postfix = file.split(".")[1];
			if(postfix != "xls" && postfix != "xlsx"){
				errors.push(i18("INPUT_FILE_ERROR"));//请选择正确的Excel导入文件
			}
		}
		if(errors.length > 0){
			jumbo.showMsg(errors.join("<br />&nbsp; &nbsp; &nbsp;"));
			return false;
		}
		loxia.lockPage();
		$j("#inputFromId").attr("action",loxia.getTimeUrl(baseUrl + "/warehouse/importTransitInner.do"));
		$j("#inputFromId").submit();
	});
	
	
	$j("#searchOne").click(function(){
		var locationCode = $j.trim($j("#locationCode").val());
		var skuId = $j.trim($j("#selectSku").val());
		var statusId = $j.trim($j("#selectStatus").val());
		var count = parseInt($j("#skuNum").val(),10);
		var shopId = $j.trim($j("#shopS").val());
		var skuNum = $j.trim($j("#skuNum").val());
		var re = /^[1-9]+[0-9]*]*$/;
		var sku = $j.trim($j("#selectSku").val());
		if(locationCode == ""){
			jumbo.showMsg(i18("LOCATION_IS_NOT_NULL"));
			return;
		}
		if(skuId == ""){
			jumbo.showMsg(i18("ADD_SKU"));
			return;
		}
		if(isNaN(count)){
			jumbo.showMsg(i18("LOCATION_QTY"));
			return;
		}
		//var productionDate = $j("#startDate1").val();
		//var expireDate = $j("#endDate1").val();
		/*if (productionDate && productionDate.length > 0) {
			productionDate = productionDate.substring(0,10);
		}
		if (expireDate && expireDate.length > 0) {
			expireDate = expireDate.substring(0,10);
		}*/
		var postData = {};
		postData["locationCode"] = $j("#locationCode").val();
		var shopid = $j.trim($j("#shopS").val());
		if(shopid != ""){
			postData["shopId"]=shopid;
		}
		postData["skuId"] = sku;
		var rt=loxia.syncXhr(baseUrl + "/querySkuStatus.json",postData); // 根据库上的商品查询库存状态(可销售库存的良品)
		var skuNum2;
		for(var i in rt.list){
			var invstatusId = rt.list[i].inventoryStatusId; // 库存状态ID
			if(statusId == invstatusId){
				skuNum2 = rt.list[i].quantity;
				break;
			}
		}
		if(skuNum2<skuNum){
			jumbo.showMsg("超出实际数量范围");
			return;
		}
	    if (!re.test(skuNum)){
	    	jumbo.showMsg("数量不正确，必须大于0的整数!");
			$j("#skuNum").focus();
			return;
		}
		querySkuQty(locationCode,"",skuId,statusId,shopId,count,1);
	});
	
	$j("#searchTwo").click(function(){
		var skuCode = $j.trim($j("#skuCode").val());
		var locationCode = $j.trim($j("#selectLocation").val());
		var statusId = $j.trim($j("#selectStatus1").val());
		var count = parseInt($j("#skuNum1").val(),10);
		var shopId = $j.trim($j("#shopS").val());
		var skuNum2 = $j.trim($j("#skuNum1").val());
		var re = /^[1-9]+[0-9]*]*$/;
		if(skuCode == ""){
			jumbo.showMsg(i18("SKU_CODE_NOT_NULL"));
			return;
		}
		if(locationCode == ""){
			jumbo.showMsg(i18("LOCATION_INCORRECT"));
			return;
		}
		if(isNaN(count)){
			jumbo.showMsg(i18("LOCATION_QTY"));
			return;
		}
		//var productionDate = $j("#startDate2").val();
		//var expireDate = $j("#endDate2").val();
		/*if (productionDate && productionDate.length > 0) {
			productionDate = productionDate.substring(0,10);
		}
		if (expireDate && expireDate.length > 0) {
			expireDate = expireDate.substring(0,10);
		}*/
		var postData = {};
		postData["locationCode"] = locationCode;
		var shopid = $j.trim($j("#shopS").val());
		if(shopid != ""){
			postData["shopId"]=shopid;
		}
		postData["skuCode"] =  $j("#skuCode").val();
		var rt=loxia.syncXhr(baseUrl + "/querySkuStatus.json",postData);
		var skuNum;
		for(var i in rt.list){
			var invstatusId = rt.list[i].inventoryStatusId;
			if(statusId == invstatusId){
				skuNum = rt.list[i].quantity;
				break;
			}
		}
		if(skuNum<skuNum2){
			jumbo.showMsg("超出实际数量范围");
			return;
		}
	    if (!re.test(skuNum2)){
	    	jumbo.showMsg("数量不正确，必须大于0的整数!");
			$j("#skuNum1").focus();
			return;
		}
		querySkuQty(locationCode,skuCode,"",statusId,shopId,count,2);
	});
	
	$j("#deleteSku").click(function(){
		var ids = $j("#tbl_temp_list").jqGrid('getGridParam','selarrrow');
//		var idsList = $j("#idsList").val();
//		var invids = "";
		for(var i in ids){
			$j("#tbl_temp_list tr[id='"+ids[i]+"']").remove();
		}
//		//判断TABLE是否有值
//		var row = $j("#tbl_temp_list").find("tr").length;
//		if(row > 1){
//			$j("#tbl_temp_list").find("tr").each(function(){
//				var tdArr = $j(this).children();
//				var inv = tdArr.eq(14).html().trim().replace(/&nbsp;/ig,'');
//				if(""!=inv){
//					invids+=inv+",";	
//				}
//			});		
//		}else{
//			$j("#searchOne").show();
//			$j("#searchTwo").show();
//			$j("#inventoryLockBatch").show();
//			$j("#idsList").val("");
//		}
//		if(""!=invids){
//			var inv = invids.substr(0, (invids.length-1));
//			$j("#idsList").val(inv);
//		}
	});
	
	$j("#locationCode").blur(function (){
		var temp = $j.trim($j("#locationCode").val());
		if(temp != breakLocationCode){
			breakLocationCode = temp;
			findSku(null,null);
		}
		
	});
	
	$j("#skuCode").blur(function (){
		var temp = $j.trim($j("#skuCode").val());
		if(temp != breakSkuCode){
			breakSkuCode = temp;
			findLocationCode(null,null);
		}
	});
	
	
	$j("#startDate1").blur(function (){
		var productionDate = $j("#startDate1").val();
		if (null != productionDate && '' != productionDate) {
			//setSkuNumber('skuNum1',"");
			locationSelectAadNum('skuNum');
			var postData = {};
			var locationCode = $j.trim($j("#locationCode").val()); // 库位编码
			var selectSku = $j.trim($j("#selectSku").val());// 库位
			var selectStatus = $j.trim($j("#selectStatus").val());// 库存状态
			if ("" == locationCode || "" == selectSku || "" == selectStatus) {
				jumbo.showMsg('请输入必填数据:包括库位编码,商品,库存状态');
				return false;
			}
			var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
			var result = productionDate.match(reg);
			if (result == null) {
				jumbo.showMsg('请输入或选择正确的日期格式');
				return false;
			}else {
				productionDate = productionDate.substr(0,10);
				var shopid = $j.trim($j("#shopS").val()); // 店铺ID
				if(shopid != ""){
					postData["shopId"]=shopid;
				}
				postData["locationCode"] = locationCode;
				postData["skuId"] = selectSku;
				postData["inventoryStatusId"] = selectStatus;
				postData["productionDate"] = productionDate;
				var rt=loxia.syncXhr(baseUrl + "/querySkuStatus.json",postData);
				if(!rt.exception){
					if(rt.size==0){
						return false; 
					}
					var number = 0;
					for(var i in rt.list){
						 number += rt.list[i].quantity;
					};
					setSkuNumber("skuNum1",number);
					return;
				}else{
					jumbo.showMsg(i18("GETINFO_EXEPTION"));
				}
			}
		}else {
			$j("#selectStatus").trigger('change');
		}
	});
	
	
	$j("#endDate1").blur(function (){
		var expireDate = $j("#endDate1").val();
		if (null != expireDate && '' != expireDate) {
			//setSkuNumber('skuNum1',"");
			locationSelectAadNum('skuNum');
			var postData = {};
			var locationCode = $j.trim($j("#locationCode").val()); // 库位编码
			var selectSku = $j.trim($j("#selectSku").val());// 商品
			var selectStatus = $j.trim($j("#selectStatus").val());// 库存状态
			if ("" == locationCode || "" == selectSku || "" == selectStatus) {
				jumbo.showMsg('请输入必填数据:包括库位编码,商品,库存状态');
				return false;
			}
			var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
			var result = expireDate.match(reg);
			if (result == null) {
				jumbo.showMsg('请输入或选择正确的日期格式');
				return false;
			}else {
				expireDate = expireDate.substr(0,10);
				var shopid = $j.trim($j("#shopS").val()); // 店铺ID
				var productionDate = $j.trim($j("#startDate1").val()); // 店铺ID
				if ("" != productionDate) {
					productionDate = productionDate.substr(0,10);
					postData["productionDate"] = productionDate;
				}
				if(shopid != ""){
					postData["shopId"]=shopid;
				}
				postData["locationCode"] = locationCode;
				postData["skuId"] = selectSku;
				postData["inventoryStatusId"] = selectStatus;
				postData["expireDate"] = expireDate;
				var rt=loxia.syncXhr(baseUrl + "/querySkuStatus.json",postData);
				if(!rt.exception){
					if(rt.size == 0){
						return;
					}
					var number = 0;
					for(var i in rt.list){
						 number += rt.list[i].quantity;
					};
					setSkuNumber("skuNum",number);
					return;
				}else{
					jumbo.showMsg(i18("GETINFO_EXEPTION"));
				}
			}
		}else {
			$j("#startDate1").trigger('blur');
		}
	});
	
	
	
	$j("#startDate2").blur(function (){
		var productionDate = $j("#startDate2").val();
		if (null != productionDate && '' != productionDate) {
			//setSkuNumber('skuNum1',"");
			locationSelectAadNum('skuNum1');
			var postData = {};
			var skuCode = $j.trim($j("#skuCode").val()); // sku编码
			var selectLocation = $j.trim($j("#selectLocation").val());// 库位
			var selectStatus1 = $j.trim($j("#selectStatus1").val());// 库存状态
			if ("" == skuCode || "" == selectLocation || "" == selectStatus1) {
				jumbo.showMsg('请输入必填数据:包括SKU编码,库位,库存状态');
				return false;
			}
			var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
			var result = productionDate.match(reg);
			if (result == null) {
				jumbo.showMsg('请输入或选择正确的日期格式');
				return false;
			}else {
				productionDate = productionDate.substr(0,10);
				var shopid = $j.trim($j("#shopS").val()); // 店铺ID
				if(shopid != ""){
					postData["shopId"]=shopid;
				}
				postData["skuCode"] = skuCode;
				postData["locationCode"] = selectLocation;
				postData["inventoryStatusId"] = selectStatus1;
				postData["productionDate"] = productionDate;
				var rt=loxia.syncXhr(baseUrl + "/querySkuStatus.json",postData);
				if(!rt.exception){
					if(rt.size==0){
						return false; 
					}
					var number = 0;
					for(var i in rt.list){
						 number += rt.list[i].quantity;
					};
					setSkuNumber("skuNum1",number);
					return;
				}else{
					jumbo.showMsg(i18("GETINFO_EXEPTION"));
				}
			}
		}else {
			$j("#selectStatus1").trigger('change');
		}
	});
	
	
	$j("#endDate2").blur(function (){
		var expireDate = $j("#endDate2").val();
		if (null != expireDate && '' != expireDate) {
			//setSkuNumber('skuNum1',"");
			locationSelectAadNum('skuNum1');
			var postData = {};
			var skuCode = $j.trim($j("#skuCode").val()); // sku编码
			var selectLocation = $j.trim($j("#selectLocation").val());// 库位
			var selectStatus1 = $j.trim($j("#selectStatus1").val());// 库存状态
			if ("" == skuCode || "" == selectLocation || "" == selectStatus1) {
				jumbo.showMsg('请输入必填数据:包括SKU编码,库位,库存状态');
				return false;
			}
			var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
			var result = expireDate.match(reg);
			if (result == null) {
				jumbo.showMsg('请输入或选择正确的日期格式');
				return false;
			}else {
				expireDate = expireDate.substr(0,10);
				var shopid = $j.trim($j("#shopS").val()); // 店铺ID
				var productionDate = $j.trim($j("#startDate2").val()); // 店铺ID
				
				if ("" != productionDate) {
//					var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
//					var result = expireDate.match(reg);
//					if (result == null) {
//						jumbo.showMsg('请输入或选择正确的日期格式');
//						return false;
//					}
					productionDate = productionDate.substr(0,10);
					postData["productionDate"] = productionDate;
				}
				if(shopid != ""){
					postData["shopId"]=shopid;
				}
				postData["skuCode"] = skuCode;
				postData["locationCode"] = selectLocation;
				postData["inventoryStatusId"] = selectStatus1;
				postData["expireDate"] = expireDate;
				var rt=loxia.syncXhr(baseUrl + "/querySkuStatus.json",postData);
				if(!rt.exception){
					if(rt.size == 0){
						return;
					}
					var number = 0;
					for(var i in rt.list){
						 number += rt.list[i].quantity;
					};
					setSkuNumber("skuNum1",number);
					return;
				}else{
					jumbo.showMsg(i18("GETINFO_EXEPTION"));
				}
			}
		}else {
			$j("#startDate2").trigger('blur');
		}
	});
	
	
});


var breakLocationCode="";
var breakSkuCode="";

/**
 * 重置并关闭时间控件(JIRA VWMS-153)
 * @param id
 */
function resetDateTimePicker(id){
	$j('#' + id).val('');
	$j('#' + id).attr('disabled','disabled');
}

function getDate(strDate){
	var s = new Date(strDate.substring(0,10));
	s.setHours(strDate.substring(11,13));
	s.setMinutes(strDate.substring(11,13));
	s.setSeconds(strDate.substring(11,13));
	s.toString();
	return s;
}

function setSkuNum(divId,nweInputName,value){
	$j('<input name="'+nweInputName+'" value="'+value+'"/>').appendTo($j("#"+divId));
}

function setSkuNumber(id,num){
	$j("#"+id).val(num);
}

function removeSelect(id,info){
	$j("#"+id+" option").remove();
	$j('<option value="">'+info+'</option>').appendTo($j("#"+id));
}

function locationSelectAadNum(numId,statusId,statusNumId,selectId,selectNumId){
	setSkuNumber(numId,"");
	$j("#"+statusNumId+" input").remove();
	removeSelect(statusId,i18("P_SELECT"));
	$j("#"+selectNumId+" input").remove();
	removeSelect(selectId,i18("P_SELECT"));
}

function querySkuQty(locationCode,skuCode,skuId,statusId,shopId,qty,mode){
	var postData = {};
	postData["skuId"] = skuId;
	postData["skuCode"] = skuCode;
	postData["locationCode"] = locationCode;
	postData["statusId"] = statusId;
	postData["shopId"] = shopId;
	postData["qty"] = qty;
	//postData["productionDate"] = productionDate;
	//postData["expireDate"] = expireDate;
	
	var data = loxia.syncXhr(baseUrl+"/json/querySkuQty.json",postData); // 根据条件获取商品数量
	if(data){
		/*for(var i in data.list){
			if(data.list[i].storeMode == 33){
				jumbo.showMsg("该商品为保质期商品,不支持手动创建！");
				return;
			}
		}*/
		for(var i in data.list){
			var ids = $j("#tbl_temp_list").jqGrid('getRowData',data.list[i].indexId);
			if(ids["indexId"]!=data.list[i].indexId){
				if(data.list[i].quantity >= qty){
					data.list[i].quantity = qty;
					$j("#tbl_temp_list").jqGrid('addRowData',data.list[i].indexId,data.list[i]);
					break;
				} else {
					$j("#tbl_temp_list").jqGrid('addRowData',data.list[i].indexId,data.list[i]);
					qty = qty - data.list[i].quantity;
				}
			}
		}
		if(mode == 1){
			$j("#locationCode").val(""); //库位编码设为空
			breakLocationCode="";
			locationSelectAadNum("skuNum","selectStatus","statusSkuNum","selectSku","selectLocationSkuNum");
		} else {
			$j("#skuCode").val(""); //sku编码设为空
			breakSkuCode="";
			locationSelectAadNum("skuNum1","selectStatus1","statusSkuNum1","selectLocation","selectLocationSkuNum1");
		}
//		$j("#inventoryLockBatch").hide();
	} else {
		jumbo.showMsg(i18("OPERATE_EXEPTION"));
	}
}

function findLocationCode(value,obj){
	var isTrue = false;
	var msg = i18("OPERATE_EXEPTION");
	locationSelectAadNum("skuNum1","selectStatus1","statusSkuNum1","selectLocation","selectLocationSkuNum1");
	$j("#selectLocationSkuNum1 input").remove();
	var skuCode = $j.trim($j("#skuCode").val());
	if(skuCode != ""){
		removeSelect("selectLocation",i18("LOADING"));
		var postData = {};
		var shopid = $j.trim($j("#shopS").val());
		if(shopid != ""){
			postData["shopId"]=shopid;
		}
		postData['skuCode']=skuCode;
		var data = loxia.syncXhr(baseUrl + "/queryLocation.json",postData);
		if(data){
			locationSelectAadNum("skuNum1","selectStatus1","statusSkuNum1","selectLocation","selectLocationSkuNum1");
			if(data.result=="success"){
				var obj = $j("#selectLocation");
				removeSelect("selectLocation",i18("P_SELECT"));
				if(data.size==0){
					msg = "";
					if(shopid){
						msg = $j('#shopS option[value="'+shopid+'"]').text()+ " "+skuCode +" "+i18("SKU");
					}
					msg = i18("NOT_SKU",[skuCode,msg]);
				} else {
					for(var i in data.list){
						$j('<option value="'+data.list[i].locationCode+'">'+data.list[i].locationCode+'</option>').appendTo(obj);
						setSkuNum("selectLocationSkuNum1",data.list[i].locationCode,data.list[i].quantity);
					};
					isTrue = true;
				}
			}else if(data.result=="error"){
				msg = data.message;
			}
		}
	} else {
		resetDateTimePicker('startDate2');
		resetDateTimePicker('endDate2');
		msg =i18("SKU_CODE_NOT_NULL");
	}
	if(skuCode != $j.trim($j("#skuCode").val())){
		return "";
	}
	if(isTrue){
		return loxia.SUCCESS;
	} else {
		jumbo.showMsg(msg);
		removeSelect("selectLocation",i18("P_SELECT"));
		return msg;
	}
}

function findSku(value,obj){
	var isTrue = false;
	var msg = i18("OPERATE_EXEPTION");
	locationSelectAadNum("skuNum","selectSku","selectLocationSkuNum","selectStatus","statusSkuNum");
	var locationCode = $j.trim($j("#locationCode").val());
	$j("#selectLocationSkuNum input").remove();
	if(locationCode != ""){
		removeSelect("selectSku",i18("LOADING"));
		var postData = {};
		var shopid = $j.trim($j("#shopS").val());
		if(shopid != ""){
			postData["shopId"]=shopid;
		}
		postData['locationCode']=locationCode;
		var data = loxia.syncXhr(baseUrl + "/querySku.json",postData);
		if(data){
			locationSelectAadNum("skuNum","selectStatus","statusSkuNum","selectSku","selectLocationSkuNum");
			if(data.result=="success"){
				var obj=$j("#selectSku");
				removeSelect("selectSku",i18("P_SELECT"));
				if(data.size==0){
					msg= "";
					if(shopid){
						msg = $j('#shopS option[value="'+shopid+'"]').text()+i18("DE");
					}
					msg = i18("NOT_LOCATION",[$j("#locationCode").val(),msg]);
				} else {
					for(var i in data.list){
						$j('<option value="'+data.list[i].skuId+'">'+data.list[i].skuCode+'|'+data.list[i].barCode+'</option>').appendTo(obj);
						setSkuNum("selectLocationSkuNum",data.list[i].skuId,data.list[i].quantity);
					};
					isTrue = true;
				}
			}else if(data.result=="error"){
				msg = data.message;
			}
		}
	} else {
		resetDateTimePicker('startDate1');
		resetDateTimePicker('endDate1');
		msg = i18("LOCATION_INCORRECT");
	}
	if(locationCode != $j.trim($j("#locationCode").val())){
		return "";
	}
	if(isTrue){
		return loxia.SUCCESS;
	} else {
		jumbo.showMsg(msg);
		removeSelect("selectSku",i18("P_SELECT"));
		return msg;
	}
}

function resetInventoryLockData(){
	$j("#tbl_temp_list").jqGrid("clearGridData");
	$j("#txtLockReason").val("");
	$j("#txtRmk").val("");
	$j("#reset").click();
	$j("#query").click();
}