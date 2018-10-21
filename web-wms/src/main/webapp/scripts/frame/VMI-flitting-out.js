$j.extend(loxia.regional['zh-CN'],{
	CORRECT_FILE_PLEASE 		: "请选择正确的Excel导入文件",
	ENTITY_INMODE				: "上架批次处理模式",
	ENTITY_EXCELFILE			: "正确的excel导入文件",
	ENTITY_STA_CREATE_TIME		: "作业创建时间",
	ENTITY_STA_UPDATE_TIME		: "上次执行时间",
	ENTITY_STA_ADDI_WAREHOUSE   : "目标仓库",
	ENTITY_STA_CREATERUSER   	: "创建人",
	ENTITY_STA_CODE				: "作业单号",
	ENTITY_STA_PROCESS			: "执行情况",
	ENTITY_STA_OWNER			: "店铺名称",
	ENTITY_STA_PLANCOUNT		: "计划执行量",
	ENTITY_STA_COMMENT			: "作业单备注",
	ENTITY_SKU_BARCODE			: "条形码",
	ENTITY_SKU_JMCODE			: "商品编码",
	ENTITY_SKU_KEYPROP			: "扩展属性",
	ENTITY_SKU_NAME				: "商品名称",
	ENTITY_DISTRICT				: "库区",
	ENTITY_LOCATION				: "库位",
	TABLE_CAPTION_STA			: "待移出清单列表",
	TABLE_CAPTION_STALINE		: "待移出明细表",
	TABLE_CAPTION_STALINE_OUTOF	: "占用明细表",
	INNVENTORY_STATUS 			: "库存状态",
	OCCUPY_QUANTITY 			: "占用数量",
	INNVENTORY_OCCUPY_SUCCEE 	: "库存占用成功！",
	COMMODITY_SUCCEE_OUTOF 		: "货品成功移出！",
	INNVENTORY_SUCCEE_RELEASE 	: "库存释放成功！",
	PL_IMP_SN					: "请导入出库商品SN号!"
});

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}
function divToggle(id){
	if($j(id).hasClass("hidden")){
		$j(id).removeClass("hidden");
	}else{
		$j(id).addClass("hidden");
	}
}
function staListToggle(){	
	divToggle("#div-sta-list");
}
function staToggle(){
	divToggle("#div-sta-detailform");
	divToggle("#div-sta-detail");
	divToggle("#div-sta-detailform1");
}
function staToggle1(){
	divToggle("#div-sta-detailform");
	divToggle("#div-sta-outof");
	divToggle("#div-sta-detailform1");
}
function staToggle2(){
	divToggle("#div-sta-detail");
	divToggle("#div-sta-outof");
}

function showMsg(msg){
	jumbo.showMsg(msg);
}

function showdetail(obj){
	staListToggle();
	var staId=$j(obj).parent().parent().attr("id");
	$j("#imp_staId").val(staId);
	var sta=$j("#tbl-outof-order").jqGrid("getRowData",staId);
	$j("#staId").val(staId);
	$j("#pmFile").val("");
	if(sta.processStatus == 1){//没有未完成的STV
		$j("#tbl-orderNumber").jqGrid('setGridParam',
			{url:$j("body").attr("contextpath")+"/stalinelist.json?sta.id="+staId}).trigger("reloadGrid",[{page:1}]);
		$j("#createTime,#createTime2").html(sta.createTime);
		$j("#staCode,#staCode1").html(sta.code);
		$j("#status,#status2").html(sta.invStrutsName);
		$j("#planCount,#planCount1").html(sta.totalSkuQty);
		$j("#addiName,#addiName1").html(sta.addiName);
		$j("#ownerOuName,#ownerOuName1").html(sta.ownerOuName);
		$j("#addiOwnerOuName,#addiOwnerOuName1").html(sta.addiOwnerOuName);
		staToggle();
	}else if(sta.processStatus == 2){
		$j("#tbl-orderoutof").jqGrid('setGridParam',
			{url:$j("body").attr("contextpath")+"/queryVMIFlittingStvLine.json?sta.id="+staId}).trigger("reloadGrid");
		$j("#createTime,#createTime2").html(sta.createTime);
		$j("#staCode,#staCode1").html(sta.code);
		$j("#status,#status2").html(sta.invStrutsName);
		$j("#planCount,#planCount1").html(sta.totalSkuQty);
		$j("#addiName,#addiName1").html(sta.addiName);
		$j("#ownerOuName,#ownerOuName1").html(sta.ownerOuName);
		$j("#addiOwnerOuName,#addiOwnerOuName1").html(sta.addiOwnerOuName);
		staToggle1();
	}
}

$j(document).ready(function (){
	var entry=$j("body").attr("contextpath")+"/warehouse/initToVMIFlittingOut.do";
	
	var status=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"whSTAProcessStatus"});
	if(!status.exception){
		//STA作业申请单
		$j("#tbl-outof-order").jqGrid({
			url:$j("body").attr("contextpath")+"/queryVMIFlittingOut.json?sta.intType=90",
			datatype: "json",
			colNames: ["ID",i18("ENTITY_STA_CREATE_TIME"),i18("ENTITY_STA_CODE"),
				i18("ENTITY_STA_PROCESS"),i18("ENTITY_STA_UPDATE_TIME"),i18("ENTITY_STA_ADDI_WAREHOUSE"),"源店铺","目标店铺","库存状态",i18("ENTITY_STA_CREATERUSER")
				//,i18("ENTITY_STA_COMMENT")
				],
			colModel: [
			           {name: "id", index: "id", hidden: true},
			           {name: "createTime", index: "createTime", width: 120, resizable: true,sortable:false},
			           {name: "code", index: "code",formatter:"linkFmatter", formatoptions:{onclick:"showdetail"}, width: 120, resizable: true,sortable:false},
			           {name: "processStatus", index: "status", width: 80, resizable: true,formatter:'select',editoptions:status,sortable:false},
				       {name: "updateTime", index: "updateTime", width: 120, resizable: true,sortable:false},
				       {name: "addiName", index: "addiName", width: 120, resizable: true,sortable:false},
				       {name: "ownerOuName", index: "ownerOuName", width: 120, resizable: true,sortable:false},
				       {name: "addiOwnerOuName", index: "addiOwnerOuName", width: 120, resizable: true,sortable:false},
				       {name: "invStrutsName", index: "invStrutsName", width: 100, resizable: true,sortable:false},
				       {name: "creater", index: "creater", width: 80, resizable: true,sortable:false}
		             //  {name: "totalSkuQty", index: "totalSkuQty", width: 70, resizable: true,sortable:false},
		             //  {name:"memo",index:"memo",width:200,resizable:true,sortable:false}
		               ],
			caption: i18("TABLE_CAPTION_STA"),
     	sortname: 'createtime',
	    multiselect: false,
		sortorder: "desc",
		width:"auto",
		height:"auto",
		loadonce:true,
		rownumbers:true,
		rowNum:-1,
		jsonReader: { repeatitems : false, id: "0" }
		});
	}else{
		jumbo.showMsg(i18("ERROR_INIT"));
	}

	//STA作业申请单行库存占用
	$j("#tbl-orderNumber").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("ENTITY_SKU_BARCODE"),i18("ENTITY_SKU_JMCODE"),i18("ENTITY_SKU_KEYPROP"),
			i18("ENTITY_SKU_NAME"),i18("ENTITY_STA_PLANCOUNT")],
		colModel: [
		           {name: "id", index: "id", hidden: true},
		           {name: "barCode", index: "sku.bar_Code", width: 100, resizable: true,sortable:false},
		           {name: "jmcode", index: "sku.jm_Code", width: 100, resizable: true,sortable:false},
		           {name: "keyProperties", index: "sku.key_Properties", width: 120, resizable: true,sortable:false},
		           {name: "skuName", index: "sku.name", width: 200, resizable: true},
		           {name: "quantity", index: "quantity", width: 100, resizable: true,sortable:false}],
		caption: i18("TABLE_CAPTION_STALINE"),
	   	sortname: 'sku.bar_Code',
	    multiselect: false,
		viewrecords: true, 
	   //postData:{"columns":"id,sku.barCode,sku.jmCode,sku.keyProperties,sku.name,quantity"},
		sortorder: "desc", 
		height:"auto",
		loadonce:true,
		rownumbers:true,
		rowNum:-1,
		jsonReader: { repeatitems : false, id: "0" },
		loadComplete:function(){
			loxia.initContext($j("#tbl-orderNumber"));
		}
	});
	
	//STA申请单行确认出库
	$j("#tbl-orderoutof").jqGrid({
		datatype: "json",
		colNames: ["ID","isSnSku",i18("ENTITY_DISTRICT"),i18("ENTITY_LOCATION"),i18("ENTITY_SKU_BARCODE"),i18("ENTITY_SKU_JMCODE"),i18("ENTITY_SKU_KEYPROP"),i18("ENTITY_SKU_NAME"),i18("INNVENTORY_STATUS"),i18("OCCUPY_QUANTITY")],
		colModel: [
		           {name: "id", index: "id", hidden:true},	
		           {name: "isSnSku", index: "isSnSku", hidden:true},	
		           {name: "districtCode", index: "districtCode", width: 120, resizable: true},
		           {name: "locationCode", index: "locationCode", width: 120, resizable: true},
		           {name: "barCode", index: "barCode", width: 120, resizable: true},
		           {name: "jmCode", index: "jmCode", width: 100, resizable: true},
		           {name: "keyProperties", index: "keyProperties", width: 100, resizable: true},
		           {name: "skuName", index: "skuName", width: 170, resizable: true},
		           {name: "intInvstatusName", index: "intInvstatusName", width: 100, resizable: true},
		           {name: "quantity", index: "quantity", width: 70, resizable: true}],
		caption: i18("TABLE_CAPTION_STALINE_OUTOF"),
	   	sortname: 'id',
	    multiselect: false,
		viewrecords: true, 
	 //  postData:{"columns":"id,district.name,location.code,sku.barCode,sku.jmCode,sku.keyProperties,sku.name,invStatus.name,quantity"},
	//	sortorder: "desc", 
		height:"auto",
		loadonce:true,
		rownumbers:true,
		rowNum:-1,
		jsonReader: { repeatitems : false, id: "0" },
		loadComplete:function(){
			loxia.initContext($j("#tbl-orderoutof"));
			
			//判断是否显示导入SN
			$j("#tbl-orderoutof tr[id]").each(function(i,tr){
				var rowData=$j("#tbl-orderoutof").jqGrid("getRowData",$j(tr).attr("id"));
				if(rowData["isSnSku"] == 1){
					$j("#divSnImport").removeClass("hidden");
				}
			});
		}
	});
	
    //库存占用
	$j("#occupancy").click(function(){
		if(windows.confirm(i18("MSG_CONFIRM"))){
			loxia.lockPage();
			loxia.asyncXhrPost($j("body").attr("contextpath") + "/occupiedVMIFlitting.json",
				{'comd.staId':$j("#staId").val()},
				{
				success:function (data) {
					loxia.unlockPage();
					if(data&&data.msg =='success'){
						jumbo.showMsg(i18("INNVENTORY_OCCUPY_SUCCEE"));
						var staId = $j("#staId").val();
						$j("#tbl-orderoutof").jqGrid('setGridParam',
							{url:$j("body").attr("contextpath")+"/queryVMIFlittingStvLine.json?sta.id="+staId}).trigger("reloadGrid");
						staToggle2();
					}else{
						if(data.errMsg != null){
							var msg = data.errMsg;
							var s = '';
							for(var x in msg){
								s +=msg[x];
							}
								jumbo.showMsg(s);
							}else{
								jumbo.showMsg(data.msg);
							}
						}	
				},
				error:function(data){
					loxia.unlockPage();
					jumbo.showMsg(i18("MSG_FAILURE"));
				}
				});
		}
	});
	
	$j("#backto, #backto1").click(function(){
		 window.location=loxia.getTimeUrl(entry);
	});

    //确认出库
	$j("#confirm").click(function(){
		//查询需要SN导入是否已经导入
		var isNeedImpSn = !$j("#divSnImport").hasClass("hidden");;
		if(isNeedImpSn){
			//查询是否已经导入SN
			var rs = loxia.syncXhr($j("body").attr("contextpath") + "/findIsImportSn.json",{"staId":$j("#staId").val()});
			if(rs.result == "success" && rs.msg == false){
				jumbo.showMsg(i18("PL_IMP_SN"));//请导入出库商品SN号!
				return ;
			}
		}
		if(windows.confirm(i18("MSG_CONFIRM"))){
			loxia.lockPage();
			loxia.asyncXhrPost($j("body").attr("contextpath") + "/outBoundVMIFlitting.json",
				{'sta.id':$j("#staId").val()},
				{
				success:function (data) {
					loxia.unlockPage();
					if(data&&data.msg =='success'){
						jumbo.showMsg(i18("COMMODITY_SUCCEE_OUTOF"));
						window.location=loxia.getTimeUrl(entry);
					}else{
						jumbo.showMsg(data.msg);
					}	
				},
				error:function(data){
					loxia.unlockPage();
					jumbo.showMsg(i18("MSG_FAILURE"));
				}
				});
		}
	});
	$j("#release").click(function(){
		if(windows.confirm(i18("MSG_CONFIRM"))){
			loxia.lockPage();
			loxia.asyncXhrPost($j("body").attr("contextpath") + "/releaseVMIFlittingInventory.json",
				{'sta.id':$j("#staId").val()},
				{
				success:function (data) {
					loxia.unlockPage();
					if(data&&data.msg =='success'){
						jumbo.showMsg(i18("INNVENTORY_SUCCEE_RELEASE"));
						window.location=loxia.getTimeUrl(entry);
					}else{
						jumbo.showMsg(data.msg);
					}	
				},
				error:function(data){
					loxia.unlockPage();
					jumbo.showMsg(i18("MSG_FAILURE"));
				}
				});
		}
	});
	
	
	/***模版导出***/
	$j("#outputTmpt").click(function(){
		var iframe = document.createElement("iframe");
		var id = $j("#staId").val();
		iframe.src = loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/predefinedOutExport.do?sta.id="+id);
		iframe.style.display = "none";
		$j("#download").append($j(iframe));
	});

	/**
	 * 导入占用
	 */
	$j("#importOccupancy").click(function(){
		var file = $j("#importFileOcp").val();
		var postfix = file.split(".")[1];
		if(postfix != "xls" && postfix != "xlsx"){
			jumbo.showMsg(i18("INVALID_MAND",i18("ENTITY_EXCELFILE")));
			return;
		}
		loxia.lockPage();
		var id = $j("#staId").val();
		$j("#importOcpForm").attr("action", loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/predefinedOutImportForVmiFlitting.do?sta.id="+id));
		loxia.submitForm("importOcpForm");
	});

	$j("#print").click(function(){
		 // betweenMovestvLineListJson.json  //stvbystaid
		var id = $j("#staId").val();
		var url = $j("body").attr("contextpath") + "/printVMIFlittingInventory.json?sta.id=" + id;
		printBZ(loxia.encodeUrl(url),true);
	});
	
	//sn号导入
	$j("#import").click(function(){
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));//请选择正确的Excel导入文件
			return false;
		}
		loxia.lockPage();
		$j("#importForm").attr("action",$j("body").attr("contextpath") + "/warehouse/importVMIFlittingOutBoundSn.do");
		$j("#importForm").submit();
	});
	
	$j("#output").click(function(){
		var iframe = document.createElement("iframe");
		iframe.src = loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/externalOutOutport.do?sta.id="+$j("#staId").val());
		iframe.style.display = "none";
		$j("#download").append($j(iframe));
	});
	//---------------包材导入-----
	$j("#inputPM").click(function (){
		var file = $j("#pmFile").val();
		var postfix = file.split(".")[1];
		if(postfix != "xls" && postfix != "xlsx"){
			jumbo.showMsg("请选择导入包材数据");
			return;
		}
		loxia.lockPage();
		$j("#importPMForm").attr("action", loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/packagingMaterialsImport.do?sta.id="+$j("#staId").val()));
		loxia.submitForm("importPMForm");
	});
	var warehouse = loxia.syncXhr($j("body").attr("contextpath") + "/getWHByOuId.json");
	if(!warehouse.exception){
		if(warehouse.warehouse.isNeedWrapStuff){
			$j("#packaging_materials").removeClass("hidden");
		} 
	}
});

function importReturn(){
	jumbo.showMsg("导入占用执行成功");
	$j("#backto").trigger("click");
}