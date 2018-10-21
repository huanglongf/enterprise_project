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
	INT_STA_STATUS		:"库存状态",
	TOTAL_SKU_QTY		:"计划量",
	EXE_STA				:"待执行手动作业单",
	EXE_STA_EXP			:"待执行导入作业单",
	LOCATION_OCCUPATION	:"库存占用",
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
	NOT_SKU				:"{0} 商品不存在 或 {1}没有可移动库存",
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

function breakExp(){
	$j("#exp-detail").addClass("hidden");
	$j("#div-sta-exp").removeClass("hidden");
}

//列表手动出库单显示详细列表
function showdetail(obj){
	loxia.lockPage();
	var staId=$j(obj).parent().parent().attr("id");
	details(staId);
}

function showdetailExp(obj){
	loxia.lockPage();
	var staId=$j(obj).parent().parent().attr("id");
	expDetails(staId);
}

function expDetails(staId){
	$j("#exp-info-exp input").val("");
	var postData = {};
	postData["staId"]= staId;
	$j("#tb-stv-line-out").jqGrid('setGridParam',{
		url:loxia.getTimeUrl(baseUrl+"/findImportStvLineOut.json"),postData:postData
		}).trigger("reloadGrid");
	$j("#tb-stv-line-in").jqGrid('setGridParam',{
		url:loxia.getTimeUrl(baseUrl+"/findImportStvLineIn.json"),postData:postData
		}).trigger("reloadGrid");
	var data=loxia.syncXhr(baseUrl + "/queryStaInfoByid.json",postData);
	if(data){
		if(data.result=="success"){
			$j("#input_staId_exp").val(data.sta.id);
			$j("#input_createTime_exp").val(data.sta.createTime);
			$j("#input_staCode_exp").val(data.sta.code);
			$j("#input_creater_exp").val(data.sta.creater);
			if(data.sta.intStaType == 31){
				$j("#input_staType_exp").val(i18("INPUTSTATYPE"));
			} else {
				$j("#input_staType_exp").val();
			}
			$j("#input_totalSkuQty_exp").val(data.sta.totalSkuQty);
			$j("#input_memo_exp").text(data.sta.memo);
			$j("#div-sta-exp").addClass("hidden");
			$j("#exp-detail").removeClass("hidden");
		}else{
			jumbo.showMsg(i18("GETINFO_EXEPTION"));
		}
	}else{
		jumbo.showMsg(i18("GETINFO_EXEPTION"));
	}
	loxia.unlockPage();
}

function details(staId){
	initLabel();
	var postData = {};
	postData["staId"]= staId;
	var data=loxia.syncXhr(baseUrl + "/queryStaById.json",postData);
	if(data){
		if(data.result=="success"){
			$j("#input_staId").val(data.sta.id);
			$j("#input_createTime").val(data.sta.createTime);
			$j("#input_staCode").val(data.sta.code);
			$j("#input_creater").val(data.sta.creater);
			if(data.sta.intStaType == 31){
				$j("#input_staType").val(i18("INPUTSTATYPE"));
			} else {
				$j("#input_staType").val();
			}
			$j("#input_totalSkuQty").val(data.sta.totalSkuQty);
			$j("#input_memo").text(data.sta.memo);
			$j("#stvlineListtable tr:gt(0)").remove();
			var html = [];
			for(var i in data.staLineList){
				html.push(initStvLine(data.staLineList[i]));
			}
			
			$j("#stvlineList").after(html.join(""));
			loxia.initContext($j("#stvlineListtable"));
		}else{
			jumbo.showMsg(i18("GETINFO_EXEPTION"));
		}
	}else{
		jumbo.showMsg(i18("GETINFO_EXEPTION"));
	}
	loxia.unlockPage();
	divToggle();
}

function showMsg(msg){
	jumbo.showMsg(msg);
}

//导入成功后刷新数据
function reloadTable(){
	backExpTo();
	$j("#tnFile").val('');
}

function initStvLine(data){
	setDataNull(data);
	var url = baseUrl + "/findallavaillocations.json";
	var html =	'<tr skuId="'+data.skuId+'" intInvstatus="'+data.intInvstatus+'" owner="'+data.owner+'" batchCode="'+data.batchCode+'" skuCost="'+data.skuCost+'">'+
					'<td>'+data.barCode+'</td>'+
					'<td>'+data.jmCode+'</td>'+
					'<td>'+data.keyProperties+'</td>'+
					'<td>'+data.batchCode+'</td>'+
					'<td>'+data.intInvstatusName+'</td>'+
					'<td>'+data.shopName+'</td>'+
					'<td>'+data.skuName+'</td>'+
					'<td>'+data.supplierCode+'</td>'+
					'<td align="center">'+data.quantity+'</td>'+
					'<td>'+
						'<div loxiaType="edittable" class="district">'+
							'<table operation="add,delete" append="0" width="320">'+
								'<thead>'+
									'<tr>'+
										'<th><input type="checkbox" /></th>'+
										'<th>'+i18("LOCATION")+'</th>'+
										'<th>'+i18("INVENTORY_QTY")+'</th>'+
										'<th>'+i18("SHELVES_QTY")+'</th>'+
									'</tr>'+
								'</thead>'+
								'<tbody>'+
									'<tr locationId="">'+
										'<td><input type="checkbox" /></td>'+
										'<td><input loxiaType="input" trim="true" checkmaster="onloadLocation" aclist="'+ url +'" autocomplete="locoptions"/></td>'+
										'<td></td>'+ 
										'<td class="decimal"><input loxiaType="number" min="1" value="'+data.quantity+'" /></td>'+
									'</tr>'+
								'</tbody>'+
								'<tbody>'+
									'<tr>'+
										'<td><input type="checkbox" /></td>'+
										'<td width="150"><input loxiaType="input" checkmaster="onloadLocation" aclist="'+ url +'" trim="true" /></td>'+
										'<td>&nbsp;</td>'+
										'<td width="50" class="decimal"><input class="getcount" min="1" loxiaType="number" /></td>'+
									'</tr>'+
								'</tbody>'+
								'<tfoot>'+
									'<tr>'+
										'<td colspan="3" class="decimal">'+i18('TOTAL')+'</td>'+
										'<td class="decimal" decimal="0">&nbsp;</td>'+
									'</tr>'+
								'</tfoot>'+
							'</table>'+
						'</div>'+
					'</td>'+
				'</tr>';
	return html;
}


function onloadLocation(value,obj){
	if(!$j.trim(value)){
		return loxia.SUCCESS;
	}else{
		 var postData = {};
		 postData["locationCode"] = value;
		 var rs = loxia.syncXhrPost(baseUrl+"/findlocationbycode.json", postData);
		 if(rs){
			if(rs.result == "success"){
				$j(obj.element).parent().parent().attr("locationId",rs.location.id);
				$j(obj.element).parent().next().html(rs.location.qty);
				return loxia.SUCCESS;
			}else if (rs.result == 'error'){
				return i18("LOCATION_NOT_FOUNDED", value); //"输入的库位 ' "+value+" '  系统不存在";
			}else{
				return i18("DATA_ERROR"); // 数据错误
			}
		 }
	}
	return loxia.SUCCESS;
	/*var locCode= $j.trim(value);
	//$j(obj.element).parent().next().html('');
	if(locCode != ""){
		var postData = {};
		postData["locationCode"] = locCode;
		var data=loxia.syncXhr(baseUrl + "/queryLocationBylocationCode.json",postData);
		$j(obj.element).parent().parent().attr("locationId","");
		if(data){
			if(data.result == "error"){
				return i18("IS_NOT_LOCATION");//库位不存在或不可用！
			} else if(data.location){
				$j(obj.element).parent().parent().attr("locationId",data.location.id);
				$j(obj.element).parent().next().html(data.location.inventoryNum);
				return loxia.SUCCESS;
			}else{
				return i18("GETINFO_EXEPTION");
			}
		}else{
			return i18("GETINFO_EXEPTION");
		}
	} else {
		return i18("INPUT_NOT_NULL");
	}*/
}

function initLabel(){
	$j("#tbl_temp_list tbody").remove();
	locationSelectAadNum("skuNum","selectStatus","statusSkuNum","selectSku","selectLocationSkuNum");
	locationSelectAadNum("skuNum1","selectStatus1","statusSkuNum1","selectLocation","selectLocationSkuNum1");
	$j("#skuCode").val("");
	$j("#locationCode").val("");
	$j("#txtRmk").val("");
	breakLocationCode="";
	$j("#shopS").val("");
	$j("#stvlineList thead").nextAll().remove();
	$j("#stvlineListtable tr:gt(0)").remove();
	
	$j("#input_staId").val("");
	$j("#input_createTime").val("");
	$j("#input_staCode").val("");
	$j("#input_creater").val("");
	$j("#input_staType").val("");
	$j("#input_totalSkuQty").val("");
	$j("#input_memo").text("");
}

function backTo(){
	var postData=loxia._ajaxFormToObj("form_query");
	$j("#tbl_transit_list").jqGrid('setGridParam',{
		url:loxia.getTimeUrl(baseUrl+"/findExecuteTransitInnerSta.json"),postData:postData
		}).trigger("reloadGrid");
}

function backExpTo(){
	var postData=loxia._ajaxFormToObj("form_query_exp");
	$j("#tbl_transit_list_exp").jqGrid('setGridParam',{
		url:loxia.getTimeUrl(baseUrl+"/findExecuteExpTransitInnerSta.json"),postData:postData
		}).trigger("reloadGrid");
}

function setDataNull(data){
	
	if(data.barCode == null){
		data.barCode = "";
	}
	if(data.jmCode == null){
		data.jmCode = "";
	}
	if(data.locationCode == null){
		data.locationCode = "";
	}
	if(data.intInvstatusName == null){
		data.intInvstatusName = "";
	}
	if(data.keyProperties == null){
		data.keyProperties = "";
	}
	if(data.owner == null){
		data.owner = "";
	}
	if(data.skuName == null){
		data.skuName = "";
	}
	if(data.supplierCode == null){
		data.supplierCode = "";
	}
	if(data.quantity == null){
		data.quantity = "";
	}
}

var isChangeAll = true;
var isChange = true;
var isInit = false;

$j(document).ready(function (){
	baseUrl = $j("body").attr("contextpath");
	$j("#tabs").tabs();
	$j("#exp-stv-line").tabs();
	
	jumbo.loadShopList("shopS","id");
	
	$j("#tbl_transit_list").jqGrid({
			url:baseUrl+"/findExecuteTransitInnerSta.json",
			postData:loxia._ajaxFormToObj("form_query"),
			datatype: "json",
			//colNames: ["ID","作业单号","创建时间","创建人","状态","计划量"],
			colNames: ["ID",i18("STA_CODE"),i18("CREATE_TIME"),i18("CREATER"),i18("INT_STA_STATUS"),i18("TOTAL_SKU_QTY")],
			colModel: [
		            {name:"id", index: "id", hidden: true},		         
		            {name:"code",index:"code",width:150,formatter:"linkFmatter",formatoptions:{onclick:"showdetail"}, resizable: true},
					{name:"createTime",index:"createTime",width:150,resizable:true},
					{name:"creater", index:"creater", width:100, resizable:true},
					{name:"intStaStatus",index:"intStaStatus",width:150,resizable:true},
					{name:"totalSkuQty",index:"totalSkuQty",width:80,resizable:true}],
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
				var ids = $j("#tbl_transit_list").jqGrid('getDataIDs');
				for(var i=0;i < ids.length;i++){
					$j("#tbl_transit_list").jqGrid('setRowData',ids[i],{"intStaStatus":i18("LOCATION_OCCUPATION")});
				}
			}
	});
	
	$j("#tbl_transit_list_exp").jqGrid({
		datatype: "json",
		//colNames: ["ID","作业单号","创建时间","创建人","状态","计划量"],
		colNames: ["ID",i18("STA_CODE"),i18("CREATE_TIME"),i18("CREATER"),i18("INT_STA_STATUS"),i18("TOTAL_SKU_QTY"),'文件名','导入状态','执行时间','执行结果'],
		colModel: [
	            {name:"id", index: "id", hidden: true},		         
	            {name:"code",index:"code",width:120,formatter:"linkFmatter",formatoptions:{onclick:"showdetailExp"}, resizable: true},
	            {name:"createTime",index:"createTime",width:140,resizable:true},
				{name:"creater", index:"creater", width:100, resizable:true},
				{name:"intStaStatus",index:"intStaStatus",width:80,resizable:true},
				{name:"totalSkuQty",index:"totalSkuQty",width:80,resizable:true},
				 {name:"fileName", index:"fileName", width:250, resizable:true},
		         {name:"fileStatus", index:"fileStatus", width:80, resizable:true},
		         {name:"exeTime",index:"exeTime",width:140,resizable:true},
				{name:"remork", index:"remork", width:150, resizable:true}],
		caption: i18("EXE_STA_EXP"),
		multiselect: false,
		height:"auto",
		sortname: 'createTime',
		pager:"#pager3",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		viewrecords: true,
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var ids = $j("#tbl_transit_list_exp").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var datas = $j("#tbl_transit_list_exp").jqGrid('getRowData',ids[i]);
				if(datas["code"] == "null" || datas["code"] == ""){
					$j("#tbl_transit_list_exp").jqGrid('setRowData',ids[i],{"code":""});
					$j("#tbl_transit_list_exp").jqGrid('setRowData',ids[i],{"intStaStatus":""});
				}else{
					$j("#tbl_transit_list_exp").jqGrid('setRowData',ids[i],{"intStaStatus":i18("LOCATION_OCCUPATION")});
				}
				
			}
		}
	});
	//初始化 导入标签页面数据
	$j("#exp_li").click(function(){
		if(!isInit){
			isInit = true;
			backExpTo();
		}
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
		$j("#importStatus").val("");
	});

	
	$j("#tbl_temp_list").jqGrid({
		datatype: "json",
		//colNames: ["indexId","SKUID","LOCATIONID","INVENTORYSTATUSID","SKU编码","商品编码","条形码","扩展名","店铺","库位","状态","数量"],
		colNames: ["indexId","SKUID","LOCATIONID","INVENTORYSTATUSID",i18("SKUCODE"),i18("JMCODE"),i18("BARCODE"),i18("KEYPROPERTIES"),i18("OWNER"),"CHANNELCODE",i18("LOCATION"),i18("STATUS"),i18("QTY")],
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
				{name:"quantity",index:"quantity",width:80,resizable:true}],
		caption: i18("CREATE_STA_LINE"),
		multiselect: true,
		height:"auto",
		
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		jsonReader: { repeatitems : false, id: "0" },
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
	
	
	$j("#tb-stv-line-out").jqGrid({
		datatype: "json",
		//colNames: ["indexId","SKUID","LOCATIONID","INVENTORYSTATUSID","SKU编码","商品编码","条形码","扩展名","店铺","库位","状态","数量"],
		colNames: ["id",i18("SKUCODE"),i18("BARCODE"),i18("JMCODE"),i18("BATCH_CODE"),i18("OWNER"),i18("LOCATION"),i18("STATUS"),i18("QTY")],
		colModel: [
		        {name:"id", index: "id", hidden: true},
	            {name:"skuCode",index:"skuCode",width:100,resizable: true},
	            {name:"barCode",index:"barCode",width:100,resizable:true},
	            {name:"jmCode",index:"jmCode",width:100,resizable: true},
	            {name:"batchCode",index:"batchCode",width:100,resizable:true},
				{name:"owner", index:"owner", width:150, resizable:true},
				{name:"locationCode",index:"locationCode",width:100,resizable:true},
				{name:"intInvstatusName",index:"intInvstatusName",width:100,resizable:true},
				{name:"quantity",index:"quantity",width:80,resizable:true}],
		caption: i18("CREATE_STA_LINE"),
		multiselect: false,
		height:"auto",
		sortname: 'skuCode',
		pager:"#pager1",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			
		}
	});
	
	$j("#tb-stv-line-in").jqGrid({
		datatype: "json",
		//colNames: ["indexId","SKUID","LOCATIONID","INVENTORYSTATUSID","SKU编码","商品编码","条形码","扩展名","店铺","库位","状态","数量"],
		colNames: ["id",i18("SKUCODE"),i18("BARCODE"),i18("JMCODE"),i18("BATCH_CODE"),i18("OWNER"),i18("LOCATION"),i18("STATUS"),i18("QTY")],
		colModel: [
		        {name:"id", index: "indexId", hidden: true},
	            {name:"skuCode",index:"skuCode",width:100,resizable: true},
	            {name:"barCode",index:"barCode",width:100,resizable:true},
	            {name:"jmCode",index:"jmCode",width:100,resizable: true},
	            {name:"batchCode",index:"batchCode",width:100,resizable:true},
				{name:"owner", index:"owner", width:150, resizable:true},
				{name:"locationCode",index:"locationCode",width:100,resizable:true},
				{name:"intInvstatusName",index:"intInvstatusName",width:100,resizable:true},
				{name:"quantity",index:"quantity",width:80,resizable:true}],
		caption: i18("CREATE_STA_LINE"),
		multiselect: false,
		height:"auto",
		sortname: 'skuCode',
		pager:"#pager",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			
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
	
	
	//占用待转出
	$j("#turn_out").click(function(){
		var datalist = $j("#tbl_temp_list" ).getRowData();
		if(datalist.length > 0){
			if(confirm(i18("IS_OPERATE"))){
				loxia.lockPage();
				var postData = {};
				postData["memo"]= $j("#txtRmk").val();
				for(var i=0,d;(d=datalist[i]);i++){
					postData["stvLinelist[" + i + "].sku.id"]=d.skuId;
					postData["stvLinelist[" + i + "].location.id"]=d.locationId;
					postData["stvLinelist[" + i + "].invStatus.id"]=d.inventoryStatusId;
					postData["stvLinelist[" + i + "].owner"]=d.invOwner;
					postData["stvLinelist[" + i + "].quantity"]=d.quantity;
			   	}
				loxia.asyncXhrPost(
						baseUrl + "/createTransitInnerSta.json",
						postData,
						{
							success:function(data){
									if(data){
										if(data.result=="success"){
											details(data.sta.id);
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
		initLabel();
		divToggle();
	});
	
	$j("#back_exp").click(function(){
		breakExp();
	});

	
	//取消本次库内移动
	$j("#cancel").click(function(){
		if(cancelTransitInner($j("#input_staId").val())){
			backTo();
			divToggle();
		}
	});
	
	$j("#cancel_exp").click(function(){
		if(cancelTransitInner($j("#input_staId_exp").val())){
			backExpTo();
			breakExp();
		}
	});
	
	//执行本次导入库内移动
	$j("#executeInventory_exp").click(function(){
		if(confirm(i18("IS_OPERATE"))){
			var postData = {};
			postData["staId"]=$j("#input_staId_exp").val();
			loxia.asyncXhrPost(baseUrl + "/executionExpTransitInner.json",
					postData,
					{
					success:function(data){
						if(data){
							if(data.result=="success"){
								backExpTo();
								breakExp();
								jumbo.showMsg(i18("SUCCESS_INNER"));
							}else if(data.result=="error"){
								jumbo.showMsg(data.message);
							}
						} else {
							jumbo.showMsg(i18("OPERATE_EXEPTION"));
						}
					},
					error:function(){jumbo.showMsg(i18("OPERATE_EXEPTION"));}//操作数据异常
			});
		}
	});

	//执行本次库内移动
	$j("#executeInventory").click(function(){
		if(confirm(i18("IS_OPERATE"))){
			var postData = {};
			postData["staId"]=$j("#input_staId").val();
			var isError = 0,msg="";
			var index = 0;
			$j.each($j("#stvlineList").nextAll(),function(i,tr){
				var skuId=$j(tr).attr("skuId");
				var intInvstatus=$j(tr).attr("intInvstatus");
				var batchCode=$j(tr).attr("batchCode");
				var skuCost=$j(tr).attr("skuCost");
				var owner=$j(tr).attr("owner");
				var qty = $j.trim($j(tr).find("td:eq(8)").text());
				var newQty = $j.trim($j(tr).find("table tfoot tr td:eq(1)").text());
				if(qty != newQty){
					msg=i18("QTY_INFO_NOT_EQ");
					isError += 1;
				} else {
					$j.each($j(tr).find("table tbody:eq(0) tr"),function(y,innerTr){
						if(isError == 0 && $j.trim($j(innerTr).find("td:eq(3) :input").val())>0){
							var locationId=$j(innerTr).attr("locationId");
							if(locationId == ""){
								$j(innerTr).find("td:eq(1) :input").addClass("error");
								msg=i18("LOCATION_INCORRECT");
								//alert('msg: ' + msg);
								isError += 1;
							} else {
								postData["stvlineCommandList[" + index + "].skuId"]=skuId;
								postData["stvlineCommandList[" + index + "].batchCode"]=batchCode;
								postData["stvlineCommandList[" + index + "].skuCost"]=skuCost;
								postData["stvlineCommandList[" + index + "].locationId"]=locationId;
								postData["stvlineCommandList[" + index + "].quantity"]=$j.trim($j(innerTr).find("td:eq(3) :input").val());
								postData["stvlineCommandList[" + index + "].owner"]=owner;
								postData["stvlineCommandList[" + index + "].intInvstatus"]=intInvstatus;
								index+=1;
							}
						} else if(isError == 0) {
							isError+=1;
							msg=i18("LOCATION_QTY");
						}
					});
				}
			});
			if(isError != 0){
				jumbo.showMsg(msg);
				return;
			}
			loxia.asyncXhrPost(baseUrl + "/executionTransitInner.json",
					postData,
					{
					success:function(data){
						if(data){
							if(data.result=="success"){
								backTo();
								divToggle();
								jumbo.showMsg(i18("SUCCESS_INNER"));
							}else if(data.result=="error"){
								jumbo.showMsg(data.message);
							}
						} else {
							jumbo.showMsg(i18("OPERATE_EXEPTION"));
						}
					},
					error:function(){jumbo.showMsg(i18("OPERATE_EXEPTION"));}//操作数据异常
			});
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
		}
		
	});
	
	$j("#selectStatus").change(function(){
		var status = $j.trim($j("#selectStatus").val());
		
		if(status != ""){
			setSkuNumber("skuNum",$j('#statusSkuNum input[name="'+status+'"]').val());
			return;
		} 
		var sku = $j.trim($j("#selectSku").val());
		if(sku != ""){
			setSkuNumber("skuNum",$j('#selectLocationSkuNum input[name="'+sku+'"]').val());
			return ;
		}
		setSkuNumber("skuNum","");
	});
	
	
	
	$j("#selectStatus1").change(function(){
		var status = $j.trim($j("#selectStatus1").val());
		if(status != ""){
			setSkuNumber("skuNum1",$j('#statusSkuNum1 input[name="'+status+'"]').val());
			return;
		}
		var location = $j.trim($j("#selectLocation").val());
		if(location != ""){
			setSkuNumber("skuNum1",$j('#selectLocationSkuNum1 input[name="'+location+'"]').val());
			return ;
		}
		setSkuNumber("skuNum1","");
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
				var obj=$j("#selectStatus1");
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
		var selectFileType = $j("#selectFileType").val();
		if(selectFileType=="SS"){
			document.getElementById("fileName").value="";
		}else{
			document.getElementById("fileName").value=file.split(".")[0];
		}
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
		var postData = {};
		postData["locationCode"] = $j("#locationCode").val();
		var shopid = $j.trim($j("#shopS").val());
		if(shopid != ""){
			postData["shopId"]=shopid;
		}
		postData["skuId"] = sku;
		postData["inventoryStatusId"] = statusId;
		var rt=loxia.syncXhr(baseUrl + "/querySkuStatus.json",postData);
		var skuNum2;
		for(var i in rt.list){
			skuNum2 = rt.list[i].quantity;
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
			skuNum = rt.list[i].quantity;
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
		querySkuQty(locationCode,skuCode,"",statusId,shopId,count,2);
	});
	
	$j("#deleteSku").click(function(){
		var ids = $j("#tbl_temp_list").jqGrid('getGridParam','selarrrow');
		for(var i in ids){
			$j("#tbl_temp_list tr[id='"+ids[i]+"']").remove();
		}
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
});
var breakLocationCode="";
var breakSkuCode="";

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


//取消库内移动
function cancelTransitInner(staId){
	if(confirm(i18("IS_OPERATE"))){
		var data = loxia.syncXhr(baseUrl+"/cancelTransitInner.json",{'staId':staId});
		if(data){
			if(data){
				if(data.result=="success"){
					jumbo.showMsg(i18("CANCEL_INNER"));
					return true;
				}else if(data.result=="error"){
					jumbo.showMsg(data.message);
				}
			} else {
				jumbo.showMsg(i18("OPERATE_EXEPTION"));
			}
		} else {
			jumbo.showMsg(i18("OPERATE_EXEPTION"));//操作数据异常
		}
	}
	return false;
}


function querySkuQty(locationCode,skuCode,skuId,statusId,shopId,qty,mode){
	var postData = {};
	postData["skuId"] = skuId;
	postData["skuCode"] = skuCode;
	postData["locationCode"] = locationCode;
	postData["statusId"] = statusId;
	postData["shopId"] = shopId;
	postData["qty"] = qty;
	var data = loxia.syncXhr(baseUrl+"/querySkuQty.json",postData);
	if(data){
		for(var i in data.list){
			if(data.list[i].storeMode == 33){
				jumbo.showMsg("该商品为保质期商品,不支持手动创建！");
				return;
			}
		}
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
			$j("#locationCode").val("");
			breakLocationCode="";
			locationSelectAadNum("skuNum","selectStatus","statusSkuNum","selectSku","selectLocationSkuNum");
		} else {
			$j("#skuCode").val("");
			breakSkuCode="";
			locationSelectAadNum("skuNum1","selectStatus1","statusSkuNum1","selectLocation","selectLocationSkuNum1");
		}
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
				var obj=$j("#selectLocation");
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