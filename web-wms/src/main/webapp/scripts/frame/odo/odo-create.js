$j.extend(loxia.regional['zh-CN'],{
	CORRECT_FILE_PLEASE : "请选择正确的Excel导入文件",
	JMCODE: "商品编码",
	SKUCODE: "SKU编码",
	KEY_PROPS : "扩展属性",
	BARCODE : "条形码",
	SKU_NAME : "商品名称",
	OWNER : "所属店铺",
	SUPPLIER_CODE : "货号",
	LOCATION_CODE : "库位",
	INNVENTORY_STATUS : "库存状态",
	MOVEQTY : "转移数量",
	QTY : "数量",
	SKU_COST : "单位成本",
	BETWEENLIBARY_LIST : "库间移动列表",
	OU_CAN_NOT_INITIALIZE : "该仓库存在库存不能进行初始化工作",
	CHOOSE_MAIN_WAREEHOUSE : "请选择源头仓库",
	CHOOSE_ADDI_WAREEHOUSE : "请选择目标仓库",
	CHOOSE_OWNER : "请选择店铺",
	MAIN_ADDI_WAREEHOUSE_NOTEQUAR : "源头仓库和目标仓库不能相同",
	CONFIRM_EXEC : "您确定要执行？",
	SAVE_SUCCE : "保存成功！",
	BETWEENLIBRARY_NOT_NULL : "库间移动列表不能为空",
	BETWEENLIBRARY_APPLICATION_IMPORT : "库间移动申请单导入"
});

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}

function showMsg(msg){
	jumbo.showMsg(msg);
}
var targetOuIdF=null;
function getTableDate(){
	var count =0;
	var postDate = {};
	$j("#tbl-invList tbody tr").each(function (i,tr){
		if(i > 0){
			--i;
		postDate["staLineCmd["+i+"].id"] = $j(tr).attr("id");
		postDate["staLineCmd["+i+"].code"] = $j(tr).find(":gt(1)").html();
		postDate["staLineCmd["+i+"].barCode"] = $j(tr).find(":gt(2)").html();
		postDate["staLineCmd["+i+"].jmCode"] = $j(tr).find(":gt(3)").html();
		postDate["staLineCmd["+i+"].keyProperties"] = $j(tr).find(":gt(4)").html();
		postDate["staLineCmd["+i+"].name"] = $j(tr).find(":gt(5)").html();
		postDate["staLineCmd["+i+"].moveQuantity"] = $j(tr).find(":gt(6)").html();
		}
		
	}); 
	return postDate;
}

function showCodeLine(obj){
	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");
	$j("#odoId").val(id);
	var pl=$j("#tbl-odo-list").jqGrid("getRowData",id);
	var baseUrl = $j("body").attr("contextpath");
	$j("#detail_tabs").tabs();
	$j("#odo-detail").removeClass("hidden");
	$j("#mainAdd").addClass("hidden");
	$j("#condition").addClass("hidden");
	$j("#back").removeClass("hidden");
	
	if(pl["statusName"]=="新建"){
		$j("#fileDiv").removeClass("hidden");
		$j("#createOutStaConfirm").removeClass("hidden");
		$j("#importNew").removeClass("hidden");
		$j("#import").addClass("hidden");
		
	}else{
		$j("#fileDiv").addClass("hidden");
		$j("#createOutStaConfirm").addClass("hidden");
		$j("#importNew").addClass("hidden");
		$j("#import").removeClass("hidden");
	}
	
	$j("#file").val("");
	
	$j("#odo_code").text("");
	$j("#odo_ouName").text("");
	$j("#odo_inOuName").text("");
	$j("#odo_createTime").text("");
	$j("#odo_status").text("");
	$j("#odo_ownerName").text("");
	
	$j("#odo_code").text(pl["code"]);
	$j("#odo_ouName").text(pl["outOuName"]);
	$j("#odo_inOuName").text(pl["inOuName"]);
	$j("#odo_createTime").text(pl["createTime"]);
	$j("#odo_status").text(pl["statusName"]);
	$j("#odo_ownerName").text(pl["ownerName"]);
	
	$j("#tbl-odoOutBound-detail").jqGrid('setGridParam',{page:1,url:baseUrl + "/odoOutBoundDetail.json?id=" + id+"&status="+1}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl-odoOutBound-detail",{},baseUrl + "/odoOutBoundDetail.json",{"id":id,"status":1});

	$j("#tbl-odoInBound-detail").jqGrid('setGridParam',{page:1,url:baseUrl + "/odoOutBoundDetail.json?id=" + id+"&status="+2}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl-odoInBound-detail",{},baseUrl + "/odoOutBoundDetail.json",{"id":id,"status":2});
	
	$j("#tbl-odoDiff-detail").jqGrid('setGridParam',{page:1,url:baseUrl + "/odoOutBoundDetail.json?id=" + id+"&status="+3}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl-odoDiff-detail",{},baseUrl + "/odoOutBoundDetail.json",{"id":id,"status":3});
	
	
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/odoOutBoundDetailList.json?",{"id":id,"status":3});
    if(staStatus!=null&&staStatus["msg"]){
    	$j("#diff").removeClass("hidden");
    	var result=loxia.syncXhr($j("body").attr("contextpath") + "/odoOuIdList.json?",{"id":id});
    	if(result!=null){
    		$j("<option value=''>请选择</option>").appendTo($j("#odoDiffOuId"));
			for(var idx in result){
				$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#odoDiffOuId"));
			}
    	}
    }
}

$j(document).ready(function (){
	var  start = "";
	var end = "";
	
	$j("#importNew").addClass("hidden");
	$j("#back").addClass("hidden");
    
	initShopQuery("selShopId","innerShopCode");
	$j("#odoCommand_ouId").flexselect();
    $j("#odoCommand_ouId").val("");
    
    

    $j("#tbl-odo-list").jqGrid({
		url:window.$j("body").attr("contextpath")+"/json/findOdOAllQuery.do",
		datatype: "json",
		colNames: ["ID","单号","店铺","单据状态","库存状态","出库仓库id","出库仓库","入库仓库","差异入库指定","创建时间","创建人"],
		colModel: [{name: "id", index: "id", hidden: true},		         
		           {name: "code", index: "code", width: 120,formatter:"linkFmatter",formatoptions:{onclick:"showCodeLine"}, resizable: true},
		           {name: "ownerName", index: "ownerName", width: 120, resizable: true},
		           {name: "statusName", index: "statusName", width: 120, resizable: true},
		           {name: "invName", index: "invName", width: 120,align:"center", resizable: true},
		           {name: "ouId", index: "ouId", width: 120, resizable: true, hidden: true},
		           {name: "outOuName", index: "outOuName", width: 120, resizable: true},
		           {name: "inOuName", index: "inOuName", width: 120, resizable: true},
		           {name: "diffOuName", index: "diffOuName", width: 120, resizable: true},
		           {name: "createTime", index: "createTime"},
		           {name: "userName", index: "userName"}],
		caption: "单据列表",
	   	sortname: 'odo.id',
	   	pager:"#odoListPager",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:"auto",
		multiselect: false,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc", 
		jsonReader: { repeatitems : false, id: "0" }
		
	});
	
	jumbo.loadShopList("selShopId",'innerShopCode',false);
	
	$j("#tbl-odoOutBound-detail").jqGrid({
		datatype: "json",
		colNames: ["ID","箱号","条码","编码","数量"],
		colModel: [{name: "id", index: "id", hidden: true},		         
		           {name: "code", index: "code", width: 120, resizable: true},
		           {name: "skuBarcode", index: "skuBarcode", width: 120, resizable: true},
		           {name: "skuCode", index: "skuCode", width: 120, resizable: true},
		           {name: "qty", index: "qty", width: 120,align:"center", resizable: true}],
		caption: "单据列表",
	   	sortname: 'l.id',
	   	pager:"#odoOutBoundPager",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:"auto",
		multiselect: false,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc", 
		jsonReader: { repeatitems : false, id: "0" }
		
	});
	
	$j("#tbl-odoInBound-detail").jqGrid({
		datatype: "json",
		colNames: ["ID","箱号","条码","编码","数量"],
		colModel: [{name: "id", index: "id", hidden: true},		         
		           {name: "code", index: "code", width: 120, resizable: true},
		           {name: "skuBarcode", index: "skuBarcode", width: 120, resizable: true},
		           {name: "skuCode", index: "skuCode", width: 120, resizable: true},
		           {name: "qty", index: "qty", width: 120,align:"center", resizable: true}],
		caption: "单据列表",
	   	sortname: 'l.id',
	   	pager:"#odoInBoundPager",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:"auto",
		multiselect: false,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc", 
		jsonReader: { repeatitems : false, id: "0" }
		
	});
	
	$j("#tbl-odoDiff-detail").jqGrid({
		datatype: "json",
		colNames: ["ID","箱号","条码","编码","数量"],
		colModel: [{name: "id", index: "id", hidden: true},		         
		           {name: "code", index: "code", width: 120, resizable: true},
		           {name: "skuBarcode", index: "skuBarcode", width: 120, resizable: true},
		           {name: "skuCode", index: "skuCode", width: 120, resizable: true},
		           {name: "qty", index: "qty", width: 120,align:"center", resizable: true}],
		caption: "单据列表",
	   	sortname: 'l.id',
	   	pager:"#odoDiffPage",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:"auto",
		multiselect: false,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc", 
		jsonReader: { repeatitems : false, id: "0" }
		
	});

	$j("#import").click(function(){
		if(!/^.*\.xlsx$/.test($j("#file").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));//请选择正确的Excel导入文件
			return false;
		}
		var ouId = $j("#odoCommand_ouId").val();
		var targetOuId = $j("#odoCommand_targetOuId").val();
		var invStatusId = $j("#odoCommand_invStatusId").val();
		var owner = $j("#selShopId").val();
		
		
		if(ouId == ""){
			jumbo.showMsg("出库仓不能为空");
			return false;
		}
		if(targetOuId == ""){
			jumbo.showMsg("入仓库不能为空！");
			return false;
		}
		if(invStatusId == ""){
			jumbo.showMsg("库存状态不能为空！");
			return false;
		}
		if(owner == ""){
			jumbo.showMsg("店铺不能为空！");
			return false;
		}

		$j("#importForm").attr("action",$j("body").attr("contextpath") + "/warehouse/importOdoLine.do?ouId="+ouId+"&targetId="+targetOuId+"&invStatusId="+invStatusId+"&owner="+owner);
		$j("#importForm").submit();
		loxia.lockPage();
	});
	
	$j("#importNew").click(function(){
		if(!/^.*\.xlsx$/.test($j("#file").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));//请选择正确的Excel导入文件
			return false;
		}
		var odoId = $j("#odoId").val();
		
		$j("#importForm").attr("action",$j("body").attr("contextpath") + "/warehouse/importOdoLine.do?odoId="+odoId);
		$j("#importForm").submit();
		loxia.lockPage();
	});
	
	$j("#export").click(function(){
		var iframe = document.createElement("iframe");
		iframe.src = loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/excelDownload.do?fileName=库间移动模板.xlsx&inputPath=tplt_import_odo.xlsx");
		iframe.style.display = "none";
		$j("#download").append($j(iframe));
//		<a href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_import_move_application"></s:text>.xls&inputPath=tplt_import_transit_cross.xls"><s:text name="download.excel.template"></s:text></a>

	});
	
	//加载店铺下拉列表
	function loadShop(){
		var startWarehouse = $j("#odoCommand_ouId").val();
		var endWarehouse = $j("#odoCommand_targetOuId").val();
		if(startWarehouse != null && endWarehouse != null){
			var postData = {};			
			postData = getTableDate();
			postData["blmcmd.startWarehouseId"] = startWarehouse;
			postData["blmcmd.endWarehouseId"] = endWarehouse;
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/findallshoplists.json",postData);
			$j("#selShopId").html("");
			for(var idx in rs){
				$j("<option value='" + rs[idx].code + "'>"+ rs[idx].name +"</option>").appendTo($j("#selShopId"));
			}
		}
	}
	
	//判断什么时候加载店铺
//	$j("#betwenMoveCmd_startWarehouseId").click(function(){
//		var s = $j("#betwenMoveCmd_startWarehouseId").val();
//		var e = $j("#betwenMoveCmd_endWarehouseId").val();
//		if(s != "" && e != ""){
//			loadShop();
//		}
//	});
//	$j("#betwenMoveCmd_endWarehouseId").click(function(){
//		var s = $j("#betwenMoveCmd_startWarehouseId").val();
//		var e = $j("#betwenMoveCmd_endWarehouseId").val();
//		if(s != "" && e != ""){
//			loadShop();
//		}
//	});
	$j("#selShopId").click(function(){
		var s = $j("#odoCommand_ouId").val();
		var e = $j("#odoCommand_targetOuId").val();
		if(s != "" && e != ""){
			if(start == "" && end == ""){
				start = s;
				end = e;
				loadShop();
			}
			if(start != s || end !=e){
				loadShop();
				start = s;
				end = e;
			}
		}else{
			alert("请先选择源仓库和目标仓库！");
			return;
		}
	});
	
	
	
	$j("#confirm").click(function(){//保存动作
		var startWarehouse = $j("#betwenMoveCmd_startWarehouseId").val();
		var endWarehouse = $j("#betwenMoveCmd_endWarehouseId").val();
		var owner = $j("#selShopId").val();
		var invId = $j("#betwenMoveCmd_invStatusId").val();
		if(startWarehouse == ""){
			jumbo.showMsg(i18("CHOOSE_MAIN_WAREEHOUSE"));
			return false;
		}
		
		if(endWarehouse == ""){
			jumbo.showMsg(i18("CHOOSE_ADDI_WAREEHOUSE"));
			return false;
		}
		
		if(owner == ""){
			jumbo.showMsg(i18("CHOOSE_OWNER"));
			return false;
		}
		if(invId == ""){
			jumbo.showMsg("库存状态必选!");
			return false;
		}
		
		if(startWarehouse == endWarehouse){
			jumbo.showMsg(i18("MAIN_ADDI_WAREEHOUSE_NOTEQUAR"));
			return false;
		}
	
		if($j("#tbl-invList tbody").children().length == 1){
			jumbo.showMsg(i18("BETWEENLIBRARY_NOT_NULL"));
			return false;
		}
		
		if(!window.confirm(i18("CONFIRM_EXEC"))){
			return false;
		}
		
		var postData = {};
		
		postData = getTableDate();
		
		postData["betwenMoveCmd.startWarehouseId"] = startWarehouse;
		postData["betwenMoveCmd.endWarehouseId"] = endWarehouse;
		postData["betwenMoveCmd.owner"] = owner;
		postData["betwenMoveCmd.invStatusId"] = invId;
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/createBetweenLibaryMoveSta.json",postData);
		
		if(rs && rs.msg == 'success'){
			//执行成功
			$j("#tbl-invList tr[id]").remove();
			$j("#file").val(""); 
			jumbo.showMsg(i18("SAVE_SUCCE"));
		}else{
			if(rs.errMsg != null){
			var msg = rs.errMsg;
			var s = '';
			for(var x in msg){
				s +=msg[x] + '\n';
			}
				jumbo.showMsg(s);
			}else{
				jumbo.showMsg(rs.msg);
			}
		}
	});
	
	$j("#targetConfirm").click(function(){
		var ouId=$j("#odoDiffOuId").val();
		if(ouId == ""){
			jumbo.showMsg("仓库必须");
			return false;
		}
		var postData={"id":$j("#odoId").val(),"ouId":ouId};
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/findOdoLineByOdoId.json",postData);
		if(rs!=null&&rs["msg"]){
			jumbo.showMsg("提交成功");
		}else{
			jumbo.showMsg("已经提交过 请勿重复操作 ");
		}
	});
	
	$j("#odoCommand_ouId").change(function(){
		var ouId=$j("#odoCommand_ouId").val();
		if(ouId==null||ouId==''){
			return;
		}
		var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findWarehouseOuListByOuId.do?ouId="+ouId);
		//$j("#odoCommand_targetOuId").html("");

		$j("#odoCommand_targetOuId").empty();
		/*$j("<option value=''>请选择</option>").appendTo($j("#odoCommand_targetOuId"));*/
		for(var idx in result){
				$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#odoCommand_targetOuId"));
		}
		
		var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findInvStatusListByCompany.do?ouId="+ouId);
		$j("#odoCommand_invStatusId").html("");
		$j("<option value=''>请选择</option>").appendTo($j("#odoCommand_invStatusId"));
		for(var idx in result){
				$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#odoCommand_invStatusId"));
		}
		$j("#odoCommand_targetOuId_flexselect").remove();
		targetOuIdF=$j("#odoCommand_targetOuId").flexselect();
		$j("#odoCommand_targetOuId").val("");
		$j("#odoCommand_targetOuId_flexselect").attr("style","visibility: visible;");
	});
	
	$j("#createOutStaConfirm").click(function(){
		var odoId = $j("#odoId").val();
		if(odoId==null||odoId==''){
			jumbo.showMsg("请选择单据！");
			return;
		}
		var postData={"odoId":$j("#odoId").val()};
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/warehouse/createOutStaByOdoId.do",postData);
		if(rs!=null&&rs["msg"]=='success'){
			jumbo.showMsg("保存成功");
		}else{
			jumbo.showMsg(rs["msg"]);
		}
	});
	
	$j("#back").click(function(){
		$j("#mainAdd").removeClass("hidden");
		$j("#condition").removeClass("hidden");
		$j("#import").removeClass("hidden");
		$j("#importNew").addClass("hidden");
		$j("#back").addClass("hidden");
		$j("#odo-detail").addClass("hidden");
		$j("#createOutStaConfirm").addClass("hidden");
		/*$j("#importDiv").removeClass("hidden");*/
		
		$j("#fileDiv").removeClass("hidden");
		
	});
	
	$j("#search").click(function(){
		queryStaList();
	});
	
});
 
function queryStaList(){
	var url = $j("body").attr("contextpath") + "/json/findOdOAllQuery.do";
	$j("#tbl-odo-list").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
	
}