$j.extend(loxia.regional['zh-CN'],{
	ENTITY_INMODE		:	"上架批次处理模式",
	ENTITY_EXCELFILE		:	"正确的excel导入文件",
	ENTITY_STA_CREATE_TIME		:	"作业创建时间",
	ENTITY_STA_UPDATE_TIME		:	"上次执行时间",
	ENTITY_STA_ADDI_WAREHOUSE   :  "目标仓库",
	ENTITY_STA_CREATERUSER   :  "创建人",
	ENTITY_STA_CODE		:	"作业单号",
	ENTITY_STA_PROCESS		:	"执行情况",
	ENTITY_STA_OWNER		:	"店铺名称",
	ENTITY_STA_PLANCOUNT		:	"计划执行量",
	ENTITY_STA_EXECCOUNT		:	"已执行量",
	ENTITY_STA_COMMENT		:	"作业单备注",
	ENTITY_SKU_BARCODE			:	"条形码",
	ENTITY_SKU_JMCODE			:	"商品编码",
	ENTITY_SKU_KEYPROP		:	"扩展属性",
	ENTITY_SKU_NAME		:	"商品名称",
	ENTITY_LOCATION		:	"库位",
	TABLE_CAPTION_STA	:"待移出清单列表",
	TABLE_CAPTION_STALINE	:"待移出明细表",
	TABLE_CAPTION_STALINE_OUTOF	:"占用明细表",
	STATUS	:"状态",
	MAIN_WAREEHOUSE : "源仓库",
	ADDI_WAREEHOUSE : "目标仓库",
	OPERATE_TIME : "操作时间",
	OPERATE : "操作",
	OPERATE_SUCCEE : "操作成功",
	OPERATE_FAIL : "操作失败",
	BETWEENLIBRARY_MOVE_LIST_CAPTION : "库间移动单列表",
	CONFIRM_EXEC : "您确定要执行？",
	CONFIRM_EXEC_CANCEL : "您确定要作废？",
	CONFIRM_EXEC_OPERATE : "您确定要执行此操作？",
	CANCEL : "取消",
	ENTITY_STA_CODE_DETAIL: "作业单明细"
});
function i18(msg, args){return loxia.getLocaleMsg(msg,args);}

function showDetail(obj){
	var id =$j(obj).parents("tr").attr("id");
	var vals =  $j("#tbl-query-info").jqGrid('getRowData',id);
	$j("#code").html(vals["code"]);
	$j("#creater").html(vals["creater"]);
	$j("#createTime").html(vals["finishTime"]);
	$j("#stauts").html(vals["intStatus"]);
	$j("#mainWh").html(vals["mainName"]);
	$j("#addiWh").html(vals["addiName"]);

	$j("#searchCondition").addClass("hidden");
	$j("#details").removeClass("hidden");
	$j("#tbl-details").jqGrid('setGridParam',
			{url:$j("body").attr("contextpath")+"/json/stalinelist.json?sta.id="+id}).trigger("reloadGrid",[{page:1}]);
	jumbo.bindTableExportBtn("tbl-details",{}, $j("body").attr("contextpath")+"/json/stalinelist.json",{"sta.id":id});
}

function doCancel(id){
	//$j("#"+id).remove();
	if(id != ''){
		var baseUrl = $j("body").attr("contextpath");
		var postData = {}; 
		postData['sta.id'] = id;
		var rs = loxia.syncXhrPost(baseUrl + "/canceltransitcrosssta.json",postData);
		if(rs && rs.rs=='success'){
			jumbo.showMsg(i18("OPERATE_SUCCEE"));
			$j("#tbl-query-info").jqGrid('setGridParam',{url: baseUrl + "/statransitcrossquery.json"}).trigger("reloadGrid",[{page:1}]);
		} else jumbo.showMsg(rs.msg);
	}else{
		jumbo.showMsg(i18("OPERATE_FAIL"));
	}
}

$j(document).ready(function (){
	$j("#tbl-query-info").jqGrid({
		url: $j("body").attr("contextpath")+"/json/statransitcrossquery.json",
		datatype: "json",
		colNames: ["ID","STATUS","intStatus",i18("ENTITY_STA_CODE"),i18("STATUS"),i18("MAIN_WAREEHOUSE"),i18("ADDI_WAREEHOUSE"),i18("ENTITY_STA_CREATERUSER"),i18("OPERATE_TIME"),i18("OPERATE")],
		colModel: [
				{name: "id", index: "id", hidden: true},
				{name: "status", index: "status", hidden: true},
				{name: "intStatus", index: "intStatus", hidden: true},
				{name :"code",index:"sta.code",formatter:"linkFmatter",formatoptions:{onclick:"showDetail"},width:150,resizable:true},
				{name: "intStatus", index: "sta.status",width: "80", resizable: true,formatter:"selectKVFmatter", formatoptions:{templateSelect:"sta_intStatus"}},
				{name: "mainName", index: "mainName",width: "200"},
				{name: "addiName", index: "addiName",width: "200"},
				{name: "creater", index: "u.user_name",width: "100", resizable: true},
				{name: "finishTime", index: "finishTime",width: "150", resizable: true},
				{name: "operate", width: "60", resizable: true}],
		caption: i18("BETWEENLIBRARY_MOVE_LIST_CAPTION"),
		viewrecords: true,
   		rownumbers:true,
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pager",
	   	height:jumbo.getHeight(),
   		sortname: 'sta.code',
    	multiselect: false,
		sortorder: "desc",
		postData:{"columns":"id,status,code,intStatus,mainName,addiName,creater,finishTime"},
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){  
			var ids = $j("#tbl-query-info").jqGrid('getDataIDs');
			//var btn1 = '<div style="float: left"><button type="button" style="width:55px;" class="confirm" name="btnExecute" loxiaType="button">执行</button></div>';
			var btn2 = '<div style="float: left"><button type="button" style="width:55px;" name="btnCancel" loxiaType="button">'+i18("CANCEL")+'</button></div>';
			for(var i=0;i < ids.length;i++){
				var btn;
				var datas = $j("#tbl-query-info").jqGrid('getRowData',ids[i]);				
				if((datas["status"]=='CREATED') || (datas["status"]=='OCCUPIED')){
					btn = btn2;
				}else{
					btn = '<div style="width: 56px;float: left">&nbsp;</div>';
				}
				$j("#tbl-query-info").jqGrid('setRowData',ids[i],{"operate":btn});
			}
			loxia.initContext($j(this));
			$j("button[name='btnExecute']").click(function(){
//				var id =$j(this).parents("tr").attr("id");
				if(!window.confirm(i18("CONFIRM_EXEC"))){
					return false;
				}
				//doCancel(id);
			});
			$j("button[name='btnCancel']").click(function(){
				var id =$j(this).parents("tr").attr("id");
				//作废交接清单
				if(!window.confirm(i18("CONFIRM_EXEC_CANCEL"))){
					return false;
				}
				doCancel(id);
			});
		}
	});
	
	jumbo.bindTableExportBtn("tbl-query-info",{"intStatus":"whSTAStatus"});
	
	$j("#search").click(function(){
		$j("#tbl-query-info").jqGrid('setGridParam',
			{url: $j("body").attr("contextpath")+"/json/statransitcrossquery.json",postData:loxia._ajaxFormToObj("staForm")}).trigger("reloadGrid",[{page:1}]);
		jumbo.bindTableExportBtn("tbl-query-info",{"intStatus":"whSTAStatus"}, $j("body").attr("contextpath")+"/json/statransitcrossquery.json",loxia._ajaxFormToObj("staForm"));
	});
	$j("#reset").click(function(){
		document.getElementById("staForm").reset();
	});
	$j("#tbl-details").jqGrid({ // stalinelist
//		url:"warehouse-other-operate-query-details.json",
		datatype: "json",//"已发货单据数","已发货商品件数",
		colNames: ["ID",i18("ENTITY_SKU_BARCODE"),i18("ENTITY_SKU_JMCODE"),i18("ENTITY_SKU_KEYPROP"),i18("ENTITY_STA_OWNER"),i18("ENTITY_STA_PLANCOUNT"),i18("ENTITY_STA_EXECCOUNT")],
		colModel: [{name: "id", index: "id", hidden: true},
			{name:"barCode", index:"BAR_CODE" ,width:100,resizable:true},
			{name:"jmcode", index:"JM_CODE" ,width:100,resizable:true},
			{name:"keyProperties", index:"KEY_PROPERTIES" ,width:100,resizable:true},
			{name:"owner", index:"owner" ,width:180,resizable:true},
			{name:"quantity",index:"quantity",width:60,resizable:true},
			//{name:"completeQuantity", index:"quantity" ,width:60,resizable:true}
			{name:"completeQuantity", index:"COMPLETE_QUANTITY" ,width:60,resizable:true}
			],
		caption: i18("ENTITY_STA_CODE_DETAIL"),
		postData:{"columns":"id,barCode,jmcode,keyProperties,owner,quantity,completeQuantity"},
		width:"auto",
		height:"auto",
		viewrecords: true,
   		rownumbers:true,
		rowNum:-1,
   		sortname: 'stal.id',
    	multiselect: false,
		sortorder: "desc",
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#back").click(function(){
		$j("#searchCondition").removeClass("hidden");
		$j("#details").addClass("hidden");
	});
	
	$j("#execute,#cancel").click(function(){
		if(confirm(i18("CONFIRM_EXEC_OPERATE"))){
			$j("#searchCondition").removeClass("hidden");
			$j("#details").addClass("hidden");
		}
	});
});
