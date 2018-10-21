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
	DATA_ERROR			:"数据错误",
	LOCATIONCODE		:"库位编码",
	INVSTATUSNAME	:"库存状态",
	INVOWNER		:"所属店铺",
	QUANTITY		:"实际库存量",
	LOCKQTY			:"占用库存量",
	AVAILQTY		:"可用库存量",
	PRODUCTSIZE		:"商品大小"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}
var baseUrl = "";

//function divToggle(){
//	if($j("#div-sta-detail").hasClass("hidden")){
//		$j("#div-sta-detail").removeClass("hidden");
//		$j("#div-sta").addClass("hidden");
//	}else{
//		$j("#div-sta").removeClass("hidden");
//		$j("#div-sta-detail").addClass("hidden");
//	}
//}

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

function initShopQuery(selId,valCode){
	INCLUDE_SEL_ID = selId;
	INCLUDE_SEL_VAL_CODE = valCode;
	if(valCode == 'innerShopCode'){
		INCLUDE_SEL_VAL_CODE = 'code'
	}
}



$j(document).ready( function () {
	
	var baseUrl = $j("body").attr("contextpath");
	initShopQuery("companyshop","innerShopCode");
	jumbo.loadShopList("companyshop");
	loxia.init({debug: true, region: 'zh-CN'});
	jumbo.loadShopList("shopS","id");
	
//	$j("#btnSearchShop").click(function(){
//		$j("#shopQueryDialog").dialog("open");
//	});
	
	/*$j("#upgrade").click(function(){
		$j("#upgradeDialog").dialog("open");
	});*/
	
	//库存商品状态
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findInventoryStatus.do");
	for(var idx in result){
		$j("<option value='" + result[idx].statusId + "'>"+ result[idx].statusName +"</option>").appendTo($j("#inventoryStatusId"));
	}
	
	/*$j("#startDate2").attr('disabled','disabled');
	$j("#endDate2").attr('disabled','disabled');
	$j("#startDate1").attr('disabled','disabled');
	$j("#endDate1").attr('disabled','disabled');*/
	
	$j("#tbl_inventory_adjust_list").jqGrid({
			url:baseUrl+"/validityadjustquery.json",
			postData:loxia._ajaxFormToObj("form_query"),
			datatype: "json",
//			colNames: ["ID","SKU编码","条形码","商品名称","品牌对接码","货号","库位编码","库存状态","所属店铺","实际库存","占用库存","可用库存","生产日期","过期时间"],
			colNames: ["ID","SKUID","INVENTORYSTATUSID",i18("SKUCODE"),i18("BARCODE"),i18("SKUNAME"),"品牌对接编码",i18("SUPPLIERSKUCODE"),i18("LOCATIONCODE"),i18("INVSTATUSNAME"),
			           i18("INVOWNER"),i18("QUANTITY"),i18("LOCKQTY"),i18("AVAILQTY"),"生产日期","过期时间","操作"],
			colModel: [
		            {name: "id", index: "id", hidden: true},
		            {name: "skuId", index: "skuId", hidden: true},
		            {name: "inventoryStatusId", index: "inventoryStatusId", hidden: true},
					{name:"skuCode",index:"skuCode",width:60,resizable:true},
					{name:"barCode",index:"barCode",width:60,resizable:true},
					{name:"skuName", index:"skuName", width:100, resizable:true},
					{name:"extCode2", index:"extCode2", width:60, resizable:true},
					{name:"supplierSkuCode", index:"supplierSkuCode", width:80, resizable:true},
					{name:"locationCode",index:"locationCode",width:60,resizable:true},
					{name:"invStatusName", index:"invStatusName" ,width:40,resizable:true},
					{name:"invOwner", index:"invOwner" ,width:60,resizable:true},
					{name:"quantity", index:"quantity" ,width:30,resizable:true},
					{name:"lockQty",index:"lockQty",width:30,resizable:true},
					{name:"availQty",index:"availQty",width:30,resizable:true},
					{name:"productionDate",index:"productionDate",width:80,resizable:true},
					{name:"expireDate",index:"expireDate",width:80,resizable:true},
					{name:"btn3",width:50,resizable:true}
			],
			caption: "在库商品效期查询",
			multiselect: false,
			height:"auto",
			pager:"#pager2",
			rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
			sortname: 'productionDate',
			sortorder: "asc",
			viewrecords: true,
   			rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" },
			loadComplete: function(){
				var button3 = '<div><button type="button" style="width:80px;" class="confirm" loxiaType="button" onclick="popup(this);">'+"修改"+'</button></div>';
				var ids = $j("#tbl_inventory_adjust_list").jqGrid('getDataIDs');
				for(var i = 0; i < ids.length; i++){
					$j("#tbl_inventory_adjust_list").jqGrid('setRowData',ids[i],{"btn3":button3});
				}
				loxia.initContext($j(this));
			}
	}).setGridWidth(1500);
	
	
	
	/**
	 * reset button
	 */
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#companyshop").val("");
		$j("#inventoryStatusId").val("");
		
	});
	
	/**
	 * search button
	 */
	$j("#query").click(function(){
		var url = loxia.getTimeUrl(baseUrl+"/validityadjustquery.json");
		var postData = loxia._ajaxFormToObj("form_query");
		jQuery("#tbl_inventory_adjust_list").jqGrid('setGridParam',{url:url, page:1, postData:postData}).trigger("reloadGrid");
	});
	
	
	
});


/**
 * operate data
 */
function popup(tag){
	var id = $j(tag).parents("tr").attr("id");
	$j("#selectId").val(id);
	$j("#upgradeDialog").dialog("open");
}