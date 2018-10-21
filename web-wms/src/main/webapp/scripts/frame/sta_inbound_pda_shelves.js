$j.extend(loxia.regional['zh-CN'],{
	ENTITY_INMODE					:	"上架批次处理模式",
	ENTITY_EXCELFILE				:	"正确的excel导入文件",
	ENTITY_SKU_BARCODE				:	"条形码",
	ENTITY_SKU_JMCODE				:	"商品编码",
	ENTITY_SKU_KEYPROP				:	"扩展属性",
	ENTITY_SKU_NAME					:	"商品名称",
	ENTITY_SKU_SUP					:	"供应商名称",
	ENTITY_STALINE_TOTAL			:	"计划量执行量",
	ENTITY_STALINE_COMPLETE			:	"已执行量",
	ENTITY_SKU_SUPCODE				:	"货号"
});
var extBarcode = null
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}
function showMsg(msg){
	jumbo.showMsg(msg);
}
var $j = jQuery.noConflict();

function showdetail(obj){
	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");
	$j("#sta_id").val(id);
	var pl=$j("#tbl_sta_list").jqGrid("getRowData",id);
	$j("#sta_code").text(pl["code"]);
	$j("#sta_refSlipCode").text(pl["refSlipCode"]);
	$j("#sta_slipCode1").text(pl["slipCode1"]);
	$j("#sta_type").text(tr.find("td[aria-describedby$='intStaType']").text());
	$j("#sta_status").text(tr.find("td[aria-describedby$='intStaStatus']").text());
	$j("#sta_createTime").text(pl["createTime"]);
	$j("#sta_memo").text(pl["memo"]);
	$j("#stv_id").val(pl["stvId"]);
	if(pl["intStaType"] == "55"){
		$j("#inGI").addClass("hidden");
	} else {
		$j("#inGI").removeClass("hidden");
	}
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl_sta_line_list" ).clearGridData(); 
	$j("#tbl_sta_line_dif_list" ).clearGridData(); 
	$j("#tbl_sta_line_shelves_list" ).clearGridData(); 
	$j("#tbl_sta_line_list").jqGrid('setGridParam',{page:1,url:baseUrl + "/findpdanotdifline.json?line.stvId=" + pl["stvId"]}).trigger("reloadGrid");
	$j("#tbl_sta_line_dif_list").jqGrid('setGridParam',{page:1,url:baseUrl + "/findpdadifline.json?line.stvId=" + pl["stvId"]}).trigger("reloadGrid");

	//PDA操作人
	var result = loxia.syncXhrPost($j("body").attr("contextpath")+"/selectpdausername.json?sta.code=" + pl["code"]);
	$j("#pdaUserNameList").html('');
	$j('<option value="" selected="selected">请选择</option>').appendTo($j("#pdaUserNameList"))
	for(var idx in result.pdalist){
		$j("<option value='" + result.pdalist[idx].id + "'>"+ result.pdalist[idx].userName+"</option>").appendTo($j("#pdaUserNameList"));
	}
	$j("#divHead").addClass("hidden");
	$j("#divDetial").removeClass("hidden");
	
	
}

function queryStaList(){
	var url = $j("body").attr("contextpath") + "/findpdainboundshelves.json";
	$j("#tbl_sta_list").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_sta_list",{"intStaType":"whSTAType","intStaStatus":"whSTAStatus"},
		url,loxia._ajaxFormToObj("queryForm"));
}

function importReturn(){
	jumbo.showMsg("入库上架导入执行成功！");
	queryStaList();
	$j("#divDetial").addClass("hidden");
	$j("#divMergeDetial").addClass("hidden");
	$j("#divHead").removeClass("hidden");
}

$j(document).ready(function (){
	loxia.init({debug: true, region: 'zh-CN'});
	$j("#tabs").tabs();
	var baseUrl = $j("body").attr("contextpath");
	initShopQuery("companyshop","innerShopCode");
	jumbo.loadShopList("companyshop");
	$j("#dialogPdaLog").dialog({title: "PDA操作日志", modal:true, autoOpen: false, width: 830});
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	$j("#show_skusn_dialog").dialog({title: "SN号信息", modal:true, autoOpen: false, width: 430, height: 600});
	$j("#tbl_sta_list").jqGrid({
		url:baseUrl + "/findpdainboundshelves.json",
		datatype: "json",
		colNames: ["ID","STVID","作业单号","相关单据号","辅助相关单据号","状态","作业类型","店铺","CHANNELCODE","核对人","核对数量","创建时间","备注"],
		colModel: [
					{name: "id", index: "id", hidden: true},
					{name: "stvId", index: "stvId", hidden: true},
					{name: "code", index: "code",formatter:"linkFmatter", formatoptions:{onclick:"showdetail"}, width: 120, resizable: true,sortable:false},
					{name: "refSlipCode", index: "refSlipCode", width: 120, resizable: true},
					{name: "slipCode1", index: "slipCode1", width: 120, resizable: true},
					{name: "intStaStatus", index: "intStaStatus", width: 80, resizable: true,formatter:'select',editoptions:staStatus},
					{name: "intStaType", index: "intStaType", width: 120, resizable: true,formatter:'select',editoptions:staType},
					//{name: "intStaType", index: "staType",hidden: true},
					{name: "channelName", index: "channelName", width: 120, resizable: true},
					{name: "owner", index: "owner", width: 120, resizable: true,hidden:true},
					{name: "affirmor", index: "affirmor", width: 120, resizable: true},
					{name: "receiptNumber", index: "receiptNumber", hidden: true},
					{name: "createTime", index: "createTime", hidden: true},
					{name: "memo", index: "memo", hidden: true}
	               ],
		caption: "已核对收货单列表",
		rowNum:10,
	   	sortname: 'id',
		pager:"#pager",
	    multiselect: false,
		viewrecords: true,
   		rownumbers:true,
		sortorder: "desc",
		width:800,
		height:"auto",
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#tbl_sta_line_list").jqGrid({
		datatype: "json",
		colNames: ["ID","商品条码","SKU商品编码","货号","扩展属性","商品名称","库位编码","执行量"],
		colModel: [
					{name: "id", index: "id", hidden: true,sortable:false},	
					{name: "barCode", index: "barCode", width: 120, resizable: true,sortable:false},
					{name: "skuCode", index: "skuCode", width: 120, resizable: true,sortable:false},
					{name: "supplierCode", index: "supplierCode", width: 120, resizable: true,sortable:false},
					{name: "keyProperties", index: "keyProperties", width: 120, resizable: true,sortable:false},
					{name: "skuName", index: "sku.name", width: 150, resizable: true,sortable:false},
					{name: "locationCode", index: "locationCode", width: 120, resizable: true,sortable:false},
					{name: "addedQty", index: "addedQty", width: 80, resizable: true,sortable:false}
	               ],
		caption: "无差异明细",
	   	sortname: 'id',
		pager:"#pagerLine",
	    multiselect: false,
		sortorder: "desc",
		height:"auto",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		viewrecords: true,
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#tbl_sta_line_dif_list").jqGrid({
		datatype: "json",
		colNames: ["ID","商品条码","SKU商品编码","货号","扩展属性","商品名称","未上架量","当前执行量","差异量"],
		colModel: [
					{name: "id", index: "id", hidden: true,sortable:false},	
					{name: "barCode", index: "barCode", width: 120, resizable: true,sortable:false},
					{name: "skuCode", index: "skuCode", width: 120, resizable: true,sortable:false},
					{name: "supplierCode", index: "supplierCode", width: 120, resizable: true,sortable:false},
					{name: "keyProperties", index: "keyProperties", width: 120, resizable: true,sortable:false},
					{name: "skuName", index: "skuName", width: 150, resizable: true,sortable:false},
					{name: "remainPlanQty", index: "remainPlanQty", width: 80, resizable: true,sortable:false},
					{name: "addedQty", index: "addedQty", width: 80, resizable: true,sortable:false},
					{name: "differenceQty", index: "differenceQty", width: 80, resizable: true,sortable:false}
	               ],
		caption: "差异明细",
	   	sortname: 'id',
		pager:"#pagerDifLine",
	    multiselect: false,
		sortorder: "desc",
		height:"auto",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		viewrecords: true,
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#tbl_skusn_list").jqGrid({
		datatype: "json",
		colNames: ["ID","SN号编码","",""],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},	
		           {name: "snCode", index: "snCode", width: 200, resizable: true,sortable:false},
		           {name: "edit",width: 75, resizable: true,sortable:false,formatter:"buttonFmatter", formatoptions:{"buttons":{label:"编辑", onclick:"editSn(this,event);"}}},
		           {name: "delete",width: 75, resizable: true,sortable:false,formatter:"buttonFmatter", formatoptions:{"buttons":{label:"删除", onclick:"deleteSn(this,event);"}}}
		           ],
		           caption: "SN号列表",
		           sortname: 'id',
		           multiselect: false,
		           sortorder: "desc",
		           height:"auto",
		           viewrecords: true,
		           rownumbers:true,
			   		gridComplete : function(){
						loxia.initContext($j(this));
					},
		           jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#tbl_sta_line_shelves_list").jqGrid({
		datatype: "json",
		colNames: ["ID","操作人","库位","商品条码","SKU商品编码","货号","扩展属性","PDA上传数量","调整量","调整人","执行量","调整","操作"],
		colModel: [
					{name: "id", index: "id", hidden: true,sortable:false},	
					{name: "userName", index: "userName", width: 80, resizable: true,sortable:false},
					{name: "locationCode", index: "locationCode", width: 110, resizable: true,sortable:false},
					{name: "barCode", index: "barCode", width: 110, resizable: true,sortable:false},
					{name: "skuCode", index: "skuCode", width: 110, resizable: true,sortable:false},
					{name: "supplierCode", index: "supplierCode", width: 100, resizable: true,sortable:false},
					{name: "keyProperties", index: "keyProperties", width: 100, resizable: true,sortable:false},
					{name: "planQty", index: "planQty", width: 50, resizable: true,sortable:false},
					{name: "differenceQty", index: "differenceQty", width: 50, resizable: true,sortable:false},
					{name: "operateName", index: "operateName", width: 90, resizable: true,sortable:false},
					{name: "addedQty", index: "addedQty", width: 50, resizable: true,sortable:false},
					{name: "adjustQty",width: 80, resizable: true,sortable:false},
					{name: "edit",width: 130, resizable: true,sortable:false,formatter:"buttonFmatter", formatoptions:{"buttons":{label:"编辑", onclick:"editQty(this,event);"}}}
	               ],
		caption: "操作明细",
	   	sortname: 'id',
		pager:"#pagerShelves",
	    multiselect: false,
		sortorder: "desc",
		height:"auto",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		viewrecords: true,
		rownumbers:true,
		gridComplete : function(){
			var ids = $j("#tbl_sta_line_shelves_list").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
//				var datas = $j("#tbl_sta_line_shelves_list").jqGrid('getRowData',ids[i]);
//				$j("#tbl_sta_line_shelves_list").jqGrid('setRowData',ids[i],{"adjust":"0"});
//				$j("#tbl_sta_line_shelves_list").jqGrid('setRowData',ids[i],{"addedQty":datas["planQty"]});
				$j("#tbl_sta_line_shelves_list").jqGrid('setRowData',ids[i],{"adjustQty":"<input name='addedQtyInput' class='getcount' value=0 disabled='true' loxiaType='number'/>"});
			}
			loxia.initContext($j(this));
		},
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	
	$j("#search").click(function(){
		queryStaList();
	});
	
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
	});
	
	/**
	 * 返回
	 */
	$j("#back").click(function(){
		$j("#divDetial").addClass("hidden");
		$j("#divHead").removeClass("hidden");
	});
	
	/**
	 * 无差异优先执行
	 */
	$j("#executeLine").click(function(){
		loxia.lockPage();
		loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+ "/json/executenotdifpdaline.json",
				{'sta.code':$j("#sta_code").text()},
				{
				success:function(data){
					loxia.unlockPage();
					if(data){
						if(data.result=="success"){
							jumbo.showMsg("上架成功");
							queryStaList();
							$j("#divDetial").addClass("hidden");
							$j("#divHead").removeClass("hidden");
						}else if(data.result=="error"){
							jumbo.showMsg(data.msg);
						}
					} else {
						jumbo.showMsg("数据操作异常");
					}
				},
				error:function(){
					loxia.unlockPage();
					jumbo.showMsg("数据操作异常");}//操作数据异常
		});
	});
	
	/**
	 * 执行PDA上架操作
	 */
	$j("#executePDALine").click(function(){
		var pdaId = $j("#pdaUserNameList").val();
		if(pdaId == ""){
			jumbo.showMsg("请选择PDA操作人");
			return;
		}
		if($j("#tbl_sta_line_shelves_list").find("button[name=savenAddendQtyButton]").length > 0){
			jumbo.showMsg("操作明细列表有调整数未保存！");
			return ;
		}
		var postData = {};
		postData['line.pdaId'] = $j("#pdaUserNameList").val();
		postData['sta.code'] = $j("#sta_code").text();
		loxia.lockPage();
		loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+ "/json/executepda.json",
				postData,
				{
				success:function(data){
					loxia.unlockPage();
					if(data){
						if(data.result=="success"){
							jumbo.showMsg("上架成功");
							queryStaList();
							$j("#divDetial").addClass("hidden");
							$j("#divHead").removeClass("hidden");
						}else if(data.result=="error"){
							jumbo.showMsg(data.msg);
						}
					} else {
						jumbo.showMsg("数据操作异常");
					}
				},
				error:function(){
					loxia.unlockPage();
					jumbo.showMsg("数据操作异常");}//操作数据异常
		});
		
	});
	
	/**
	 * 差异查询
	 */
	$j("#lineSearch").click(function(){
		var vdata = loxia._ajaxFormToObj("queryLineForm");
		vdata["line.stvId"]= $j("#stv_id").val();
		$j("#tbl_sta_line_dif_list").jqGrid('setGridParam',{page:1,url:baseUrl + "/findpdadifline.json",postData:vdata}).trigger("reloadGrid");
	});
	
	/**
	 * 差异查询重置
	 */
	$j("#lineReset").click(function(){
		$j("#queryLineForm input").val("");
	});
	
	/**
	 * PDA操作人选择
	 */
	$j("#pdaUserNameList").change(function(){
		$j("#tbl_sta_line_shelves_list" ).clearGridData(); 
		if($j("#pdaUserNameList").val() != ""){
			var baseUrl = $j("body").attr("contextpath");
			var vdata = {};
			vdata["line.stvId"]= $j("#stv_id").val();
			vdata["line.pdaId"]=$j("#pdaUserNameList").val();
			$j("#tbl_sta_line_shelves_list").jqGrid('setGridParam',{page:1,url:baseUrl + "/findpdaline.json",postData:vdata}).trigger("reloadGrid");
		}
	});
	
	/**
	 * 操作页面查询
	 */
	$j("#linePDASearch").click(function(){
		if($j("#pdaUserNameList").val() == ""){
			jumbo.showMsg("请选择PDA操作人");
			return;
		}
		var vdata = loxia._ajaxFormToObj("queryPDALineForm");
		vdata["line.stvId"]= $j("#stv_id").val();
		vdata["line.pdaId"]=$j("#pdaUserNameList").val();
		$j("#tbl_sta_line_shelves_list").jqGrid('setGridParam',{page:1,url:baseUrl + "/findpdaline.json",postData:vdata}).trigger("reloadGrid");
	});
	
	$j("#linePDAReset").click(function(){
		$j("#queryPDALineForm input").val("");
	});
});

/**
 * 保存调整数量
 * @param obj
 * @return
 */
function saveAddedQty(obj){
	var id=$j(obj).parents("tr").attr("id");
	if(loxia.byId($j("#tbl_sta_line_shelves_list tr[id="+id+"] td[aria-describedby=tbl_sta_line_shelves_list_adjustQty] input")).check()){
		var postData = {};
		postData['line.pdaId'] = $j("#pdaUserNameList").val();
		var qty = parseInt($j("#tbl_sta_line_shelves_list tr[id="+id+"] td[aria-describedby=tbl_sta_line_shelves_list_adjustQty] input").val());
		var datas = $j("#tbl_sta_line_shelves_list").jqGrid('getRowData',id);
		var differenceQty = parseInt(datas["differenceQty"]) + qty;
		postData['line.pdaLineId'] = id
		postData['line.quantity'] = differenceQty;
		loxia.lockPage();
		loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+ "/json/savepdaaddedqty.json",
				postData,
				{
				success:function(data){
					loxia.unlockPage();
					if(data){
						if(data.result=="success"){
							var qty = parseInt($j("#tbl_sta_line_shelves_list tr[id="+id+"] td[aria-describedby=tbl_sta_line_shelves_list_adjustQty] input").val());
							var datas = $j("#tbl_sta_line_shelves_list").jqGrid('getRowData',id);
							var planQty = parseInt(datas["planQty"]);
							var differenceQty = parseInt(datas["differenceQty"]) + qty;
							var addedQty = planQty + differenceQty;
							$j("#tbl_sta_line_shelves_list").jqGrid('setRowData',id,{"differenceQty":differenceQty});
							$j("#tbl_sta_line_shelves_list").jqGrid('setRowData',id,{"operateName":data.userName});
							$j("#tbl_sta_line_shelves_list").jqGrid('setRowData',id,{"addedQty":addedQty});
							$j("#tbl_sta_line_shelves_list tr[id="+id+"] td[aria-describedby=tbl_sta_line_shelves_list_adjustQty]").html("<input name='addedQtyInput' class='getcount' value=0 disabled='true' loxiaType='number'/>")
							$j("#tbl_sta_line_shelves_list tr[id="+id+"] td[aria-describedby=tbl_sta_line_shelves_list_edit]").html("<button loxiaType='button' onclick='editQty(this,event);'>编辑</button>");
							loxia.initContext($j("#tbl_sta_line_shelves_list"));
						}else if(data.result=="error"){
							jumbo.showMsg(data.msg);
						}
					} else {
						jumbo.showMsg("保存失败,数据操作异常!");
					}
				},
				error:function(){
					loxia.unlockPage();
					jumbo.showMsg("保存失败,数据操作异常!");}
		});
	} else {
		jumbo.showMsg("须要保存的数据错误，重新填写!");
	}
}

/**
 * 
 * @param obj
 * @return
 */
function editQty(obj){
	var baseUrl = $j("body").attr("contextpath");
	var id=$j(obj).parents("tr").attr("id");	
	var rs = loxia.syncXhrPost(baseUrl+"/checkSkuSn.json",{"ids":id});
	if(rs && rs.result == 'OK'){
		//如果是SN号商品,获取对应PDA_SN列表
		var vdata = {};
		vdata["ids"]= id;
		$j("#tbl_skusn_list").jqGrid('setGridParam',{page:1,url:baseUrl + "/findPdaLineSnList.json?",postData:vdata}).trigger("reloadGrid");
		$j("#orderSkuId").val(id);
		$j("#show_skusn_dialog").dialog({
			closeOnEscape:false, 
			open:function(event,ui){$j(".ui-dialog-titlebar-close").hide();} 
			}); 
		$j("#show_skusn_dialog").dialog("open");
		$j("#skusn").val("");
		$j("#skusn").focus();
	}else{
		$j("#tbl_sta_line_shelves_list tr[id="+id+"] td[aria-describedby=tbl_sta_line_shelves_list_edit]").html("<button loxiaType='button' name='savenAddendQtyButton' onclick='saveAddedQty(this,event);'>保存</button>");
		var qty = $j("#tbl_sta_line_shelves_list tr[id="+id+"] td[aria-describedby=tbl_sta_line_shelves_list_adjustQty] input").val();
		$j("#tbl_sta_line_shelves_list tr[id="+id+"] td[aria-describedby=tbl_sta_line_shelves_list_adjustQty]").html("<input name='addedQtyInput' class='getcount' value="+qty+" loxiaType='number'/>")
		loxia.initContext($j("#tbl_sta_line_shelves_list"));
	}
}

//编辑对应SN号
function editSn(obj){
		var id=$j(obj).parents("tr").attr("id");	
		$j("#tbl_skusn_list tr[id="+id+"] td[aria-describedby=tbl_skusn_list_edit]").html("<button loxiaType='button' name='savenAddendSnButton' onclick='saveAddedSn(this,event);'>保存</button>");
		var sn = $j("#tbl_skusn_list tr[id="+id+"] td[aria-describedby=tbl_skusn_list_snCode]").html();
		$j("#tbl_skusn_list tr[id="+id+"] td[aria-describedby=tbl_skusn_list_snCode]").html("<input name='updateSnInput' class='getcount' value="+sn+" loxiaType='input'/>");
		loxia.initContext($j("#tbl_skusn_list"));
}

//保存修改的SN号
function saveAddedSn(obj){
	var baseUrl = $j("body").attr("contextpath");
	var id=$j(obj).parents("tr").attr("id");
	var sn = $j("#tbl_skusn_list tr[id="+id+"] td[aria-describedby=tbl_skusn_list_snCode] input").val().trim();
	var oid = $j("#orderSkuId").val();
	if(sn == null || sn == ""){
		jumbo.showMsg("SN号编码不能为空");
		return;
	}
	var postData = {};
	postData['ids'] = id;
	postData['skuCode'] = sn;
	loxia.lockPage();
	loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+ "/json/editPdaOrderSkuSn.json",
			postData,
			{
			success:function(data){
				loxia.unlockPage();
				if(data){
					if(data.result=="success"){
//						jumbo.showMsg("修改成功");
						var vdata = {};
						vdata["ids"]= oid;
						$j("#tbl_skusn_list").jqGrid('setGridParam',{page:1,url:baseUrl + "/findPdaLineSnList.json?",postData:vdata}).trigger("reloadGrid");
					}else if(data.result=="error"){
						jumbo.showMsg("数据操作异常");
					}
				} else {
					jumbo.showMsg("数据操作异常");
				}
			},
			error:function(){
				loxia.unlockPage();
				jumbo.showMsg("数据操作异常");}//操作数据异常
	});
}

/**
 * 删除对应SN号信息
 * @param obj
 */
function deleteSn(obj){
	var baseUrl = $j("body").attr("contextpath");
	var id=$j(obj).parents("tr").attr("id");
	var oid = $j("#orderSkuId").val();
	var postData = {};
	postData['ids'] = id;
	if(confirm("确定删除对应SN信息？")){
		loxia.lockPage();
		loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+ "/json/deletePdaOrderSkuSn.json",
				postData,
				{
			success:function(data){
				loxia.unlockPage();
				if(data){
					if(data.result=="success"){
//						jumbo.showMsg("删除成功");
						var vdata = {};
						vdata["ids"]= oid;
						$j("#tbl_skusn_list").jqGrid('setGridParam',{page:1,url:baseUrl + "/findPdaLineSnList.json?",postData:vdata}).trigger("reloadGrid");
					}else if(data.result=="error"){
						jumbo.showMsg("数据操作异常");
					}
				} else {
					jumbo.showMsg("数据操作异常");
				}
			},
			error:function(){
				loxia.unlockPage();
				jumbo.showMsg("数据操作异常");}//操作数据异常
			});
	}
}

//新增SN号信息
function addNewSkuSn(obj){
	var baseUrl = $j("body").attr("contextpath");
	var oid = $j("#orderSkuId").val();
	var sn = $j("#skusn").val().trim();
	if(sn == null || sn == ""){
		jumbo.showMsg("SN编码不能为空");
		$j("#skusn").focus();
		return;
	}
	var postData = {};
	postData['ids'] = oid;
	postData['skuCode'] = sn;
	loxia.lockPage();
	loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+ "/json/addPdaOrderSkuSn.json",
			postData,
			{
			success:function(data){
				loxia.unlockPage();
				if(data){
					if(data.result=="success"){
//						jumbo.showMsg("添加成功");
						var vdata = {};
						vdata["ids"]= oid;
						$j("#tbl_skusn_list").jqGrid('setGridParam',{page:1,url:baseUrl + "/findPdaLineSnList.json?",postData:vdata}).trigger("reloadGrid");
						$j("#skusn").val("");
						$j("#skusn").focus();
					}else if(data.result=="error"){
						jumbo.showMsg("数据操作异常");
					}
				} else {
					jumbo.showMsg("数据操作异常");
				}
			},
			error:function(){
				loxia.unlockPage();
				jumbo.showMsg("数据操作异常");}//操作数据异常
	});
}

//编辑完成
function okDialog(obj){
	var baseUrl = $j("body").attr("contextpath");
	var data = $j("#tbl_skusn_list").jqGrid('getDataIDs');
	var id = $j("#orderSkuId").val();
	var datas = $j("#tbl_sta_line_shelves_list").jqGrid('getRowData',id);
	var pdaid = $j("#pdaUserNameList").val();
	var snQty = data.length;//编辑后的SN数量
	var qty = parseInt(datas["differenceQty"]);//调整量
	var addedQty = parseInt(datas["addedQty"]);//执行量
	var differenceQty = snQty-addedQty+qty;//差异量
	var postData = {};
	postData['line.pdaId'] = pdaid;
	postData['line.pdaLineId'] = id;
	postData['line.quantity'] = differenceQty;
	loxia.lockPage();
	loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+ "/json/savepdaaddedqty.json",
			postData,
			{
			success:function(data){
				loxia.unlockPage();
				if(data){
					if(data.result=="success"){
						$j("#show_skusn_dialog").dialog("close");
						$j("#skusn").val("");
//						jumbo.showMsg("编辑成功");
						var vdata = loxia._ajaxFormToObj("queryPDALineForm");
						vdata["line.stvId"]= $j("#stv_id").val();
						vdata["line.pdaId"]=$j("#pdaUserNameList").val();
						$j("#tbl_sta_line_shelves_list").jqGrid('setGridParam',{page:1,url:baseUrl + "/findpdaline.json",postData:vdata}).trigger("reloadGrid");
						loxia.initContext($j("#tbl_sta_line_shelves_list"));
					}else if(data.result=="error"){
						jumbo.showMsg(data.msg);
					}
				} else {
					jumbo.showMsg("保存失败,数据操作异常!");
				}
			},
			error:function(){
				loxia.unlockPage();
				jumbo.showMsg("保存失败,数据操作异常!");
			}
	});
}


function closeDialog(obj){
	$j("#show_skusn_dialog").dialog("close");
	$j("#skusn").val("");
}

function trim(str){   
    return str.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');   
}  