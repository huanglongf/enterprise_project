$j.extend(loxia.regional['zh-CN'],{
	STATUSNEW :		"新建",
	STATUSOCCUPIED: "配货中",
	STATUSPACKING:  "装箱中",
	STATUSFINISHED:	"已完成",
	STATUSCANCELED: "取消已处理",
	
	EXECUTION:		"执行",
	CANCEL:			"取消",
	TRANSATION_TYPE : "作业类型",
	
	EXECUTIONSUCCESSFUL:"执行成功",
	STACANCEL:		"作业单已取消",
	EXECUTEXCEPTION:"操作数据异常",
	
	STACODE:		"作业单号",
	TRANSACTION:	"作业方向",
	STATUS:			"状态",
	STORE:			"店铺",
	CREATETIME:		"创建时间",
	COMPLETETIME:	"完成时间",
	CREATER:		"创建人",
	OPERATOR:		"操作人",
	OPERATE:		"操作",
	
	TITLE:			"VMI转店出入库作业单列表",
	
	INSTOCK:		"入库",
	OUTSTOCK:		"出库",
	
	EXECUTIONASK:	"您确定要执行？",
	CANCELASK:		"您确定要取消？",
	
	BARCODE:		"条形码",
	SKUCODE:		"商品编码",
	DISTRICT:		"库区",
	LOCATION:		"库位",
	STATUS:			"状态",
	SKUCOST:		"成本",
	QUANTITY:		"数量",
	
	IN_SN_TEMPLATE: 	"入库SN序列号",
	OUT_SN_TEMPLATE: 	"出库SN序列号",
	DETAILTITLE:	"作业单明细",
	STATYPE : "VMI转店"
		
	
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

var $j = jQuery.noConflict();

function showMsg(message){
	jumbo.showMsg(message);
}

function showDetail(obj){
 	var id =$j(obj).parents("tr").attr("id");
 	jumbo.bindTableExportBtn("tbl-details",{"intType":"whSTAType","intStatus":"whSTAStatus"},$j("body").attr("contextpath")+"/vmireturndetails.json?",{"staID":id});
	var vals =  $j("#tbl-query-info").jqGrid('getRowData',id);
	$j("#pmFile").val("");
	$j("#staID").val(vals["id"]);
	$j("#imp_staId").val(vals["id"]);
	$j("#code").html(vals["code"]);
	$j("#isEsprit").val(vals["isEsprit"]);
	$j("#isPf").val(vals["isPf"]);
	$j("#shop").html(vals["store"]);
	$j("#createTime").html(vals["createTime"]);
	$j("#status_").html(vals["status"]);
	if('10'==vals["freightType"]){
		$j("#freightMode").html('上门自取');
	}else if('20'==vals["freightType"]){
		$j("#freightMode").html('物流商配送');
	}else{
		$j("#freightMode").html(vals["freightType"]);
	}
	
	var type = vals["strType"];
	var isPf=vals["isPf"];//唯品会 是否批发标示
	//alert(isPf != '1');
	//alert(isPf);
	$j("#update").removeClass("hidden");
	if(type == '101'){
		//退大仓作业单隐藏修改地址按钮
		$j("#update").addClass("hidden");
	}
	if(type == '102'){
		if('20'==vals["freightType"]){
			$j("#update").removeClass("hidden");	
		}else{
			$j("#update").addClass("hidden");
		}
	}
	if (vals["intStatus"] == 2){
		$j("#packing").removeClass("hidden");
		$j("#divInv").removeClass("hidden");
	}else {
		$j("#packing").addClass("hidden");
		$j("#divInv").addClass("hidden");
	}
	if(vals["status"]==i18("STATUSNEW") || vals["status"]==i18("STATUSOCCUPIED")){
		$j("#execute").removeClass("hidden");
		$j("#cancel").removeClass("hidden");
		$j("#print").removeClass("hidden");
		$j("#is_PM").removeClass("hidden");
	}else if(vals["status"]==i18("STATUSOCCUPIED")){
		$j("#cancel").removeClass("hidden");
		$j("#execute").addClass("hidden");
		$j("#print").removeClass("hidden");
		$j("#is_PM").addClass("hidden");
	}else {
		$j("#cancel").addClass("hidden");
		$j("#execute").addClass("hidden");
		$j("#print").addClass("hidden");
		$j("#is_PM").addClass("hidden");
	}
	// 获取备注
	showDt(id);
	$j("#divPd").addClass("hidden");
	$j("#divList").removeClass("hidden");
	//唯品会 批发 销售出库
	if(isPf == '1'){
		$j("#update").addClass("hidden");
	}
}

function showDt(id){
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-details").jqGrid('setGridParam',{
		url:loxia.getTimeUrl(baseUrl+"/vmireturndetails.json"),
		postData:{"staID":id}
	}).trigger("reloadGrid");
}

//执行出入库作业单
function execution(id){
	var ids =$j("#tbl-details").jqGrid('getDataIDs');
	var isExe = true;
	for(var i = 0 ;i < ids.length; i ++){
		var invStatusId = $j("#tbl-details").getCell(ids[i],"invStatusId");
		if(invStatusId == 0){
			isExe = false;
		}
	}
	if(isExe){
		var isNeedImpSn = !$j("#divSnImport").hasClass("hidden");;
		if(isNeedImpSn){
			//查询是否已经导入SN
			var rs = loxia.syncXhr($j("body").attr("contextpath") + "/findIsImportSn.json",{"staId":id});
			if(rs.result == "success" && rs.msg == false){
				jumbo.showMsg("请导入出库商品SN号!");//请导入出库商品SN号!
				return ;
			}
		}
		var postData = {};
		postData['staID']=id;
		//add  1：有运单号 2：没有运单号 3：系统异常
		 var isEsprit= $j("#isEsprit").val();
		if(""==isEsprit || isEsprit==null){
			execution2(postData);
		}else{
					var rs2 = loxia.syncXhr($j("body").attr("contextpath") + "/getStaDel.json",{"staId":id});
					if(rs2.msg=="3"){
						jumbo.showMsg("系统异常");return;
					}else if(rs2.msg=="2"){
						jumbo.showMsg("该作业单没有运单号");return;
					}else{
						execution2(postData);
					}
		}
		//
	//	execution2(postData);
	}else{
		jumbo.showMsg("付款经销商品不允许执行！");
		return;
	}
	
}
function execution2(postData){
	var status = $j("#status_").html();
	var shop = $j("#shop").html();
	var channel = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getChannelVmiCode.do",{"code":shop});//渠道信息
		if(channel['vmiCode'] == '5751' && status != '装箱中'){
			jumbo.showMsg(shop+" 店铺必须装箱后才能执行...");
			return;
	}
	loxia.asyncXhrPost(
			//$j("body").attr("contextpath") + "/operateExecution.json",
			$j("body").attr("contextpath") + "/executevmireturn.json",
			postData,
			{
				success:function(data){
						if(data){
							if(data.result=="success"){
								jumbo.showMsg(i18("EXECUTIONSUCCESSFUL"));//交货清单取消成功
								refreshHead();
							}else if(data.result=="error"){
								jumbo.showMsg(data.message);
							}
						}
					},
				error:function(data){jumbo.showMsg(i18("EXECUTEXCEPTION"));}//操作数据异常
			}
		);	
}
//取消出入库作业单
function canceled(id){
	var postData = {};
	postData['staID']=id;
	loxia.asyncXhrPost(
			//$j("body").attr("contextpath") + "/operateCanceled.json",
			$j("body").attr("contextpath") + "/cancelvmireturn.json",
			postData,
			{
				success:function(data){
						if(data){
							if(data.result=="success"){
								jumbo.showMsg(i18("STACANCEL"));//交货清单取消成功
								refreshHead()
							}else if(data.result=="error"){
								jumbo.showMsg(data.message);
							}
						}
					},
				error:function(){jumbo.showMsg(i18("EXECUTEXCEPTION"));}//操作数据异常
			}
		);
}

//刷新数据
function refreshHead(){
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-query-info").jqGrid('setGridParam',{
		url:loxia.getTimeUrl(baseUrl+"/vmireturnstaquery.json"),
		postData:loxia._ajaxFormToObj("form_query")
	}).trigger("reloadGrid");
	$j("#divPd").removeClass("hidden");
	$j("#divList").addClass("hidden");
}

$j(document).ready(function (){
	
 	jumbo.loadShopList("owner");
	
	/****
	 * initShopQuery("companyshop","innerShopCode");
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	 */
	$j("#show_address_dialog").dialog({title: "退货地址详细信息", modal:true, autoOpen: false, width: 630, height: 230});
	
	var baseUrl = $j("body").attr("contextpath");
	var status=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"whSTAStatus"});
	$j("#tbl-query-info").jqGrid({
		//postData:loxia._ajaxFormToObj("form_query"),
		datatype: "json",
		
		colNames: ["ID","isEsprit","isPf","作业单号","创建时间","状态","状态","店铺","相关单据号","VMI退货拣货单打印","","运送模式","相关单据号2(LOADKEY)"],
		colModel: [
	            {name: "id", index: "id", hidden: true},
	            {name: "isEsprit", index: "isEsprit", hidden: true},
	            {name: "isPf", index: "isPf", hidden: true},		         
	            {name: "code",index:"code",width:150,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, resizable: true},
				{name: "createTime",index:"createTime",width:150,resizable:true},
				{name: "status", index: "status",width: "80", resizable: true,hidden:true},
				{name: "intStatus", index: "status",width: "80", resizable: true,
				        	   formatter:'select',editoptions:status,sortable:false},
				{name:"store",index:"store",width:200,resizable:true},
				{name:"refSlipCode",index:"refSlipCode",width:150,resizable:true},
				{name:"code", width:"150",resizable:true,sortable: false, formatter:"buttonFmatter", formatoptions:{"buttons":{label:"打印", onclick:"vmiBackprint(this)"}}},
				{name:"strType",index:"strType",width:30,resizable:true,hidden:true},
				{name:"freightType",index:"freightType",width:30,resizable:true,hidden:true},
				{name:"slipCode2",index:"slipCode2",width:160,resizable:true}
				],
		caption: "VMI退仓作业单",
//		 rowNum:10,
//		 rowList:[5,10,15,20,25,30],
		pager:"pager",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		height:"auto",
   		sortname: 'id',
    	multiselect: false,
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		
		gridComplete: function(){
			var ids = $j("#tbl-query-info").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var datas = $j("#tbl-query-info").jqGrid('getRowData',ids[i]);
				var statusVal= "";
				if(datas["status"]=="CREATED"){
					statusVal= i18("STATUSNEW");
				}else if(datas["status"]=="OCCUPIED"){
					statusVal= i18("STATUSOCCUPIED");
				}else if(datas["status"]=="FINISHED"){
					statusVal= i18("STATUSFINISHED");
				}else if(datas["status"]=="CANCELED"){
					statusVal= i18("STATUSCANCELED");
				}else if(datas["status"]=="PACKING"){
					statusVal= i18("STATUSPACKING");
				}else {
				}
				$j("#tbl-query-info").jqGrid('setRowData',ids[i],{"status":statusVal});
				$j("#tbl-query-info").jqGrid('setRowData',ids[i],{"intStaType":i18("STATYPE")});
			}
		}
	});
	//jumbo.bindTableExportBtn("tbl-query-info",{"intStatus":"whSTAStatus"});

	$j("#tbl-details").jqGrid({
		postData:loxia._ajaxFormToObj("form_query"),
		datatype: "json",
		colNames: ["ID","SKU编码","库区编码","库位编码","库存状态","","销售模式","商品名称","条形码","供应商编码","商品编码","扩展属性","数量"],
		colModel: [
	            {name: "id", index: "id", hidden: true},		         
	            {name:"skuCode",index:"skuCode",width:90,resizable:true},
	            {name:"district",index:"district",width:90,resizable:true},
				{name:"location",index:"location",width:90,resizable:true},
				{name:"status",index:"status",width:60,resizable:true},
				{name:"isSnSku",index:"isSnSku",hidden:true},
				{name:"invStatusId",index:"invStatusId",hidden:true},
				{name:"skuName",index:"skuName",width:180,resizable:true},
				{name:"barCode",index:"barCode",width:90,resizable:true},
				{name:"supplierCode",index:"supplierCode",width:120,resizable:true},
				{name:"jmcode",index:"jmcode",width:90,resizable:true},
				{name:"keyProperties",index:"keyProperties",width:80,resizable:true},
				{name:"quantity",index:"quantity",width:50,resizable:true}
				],
		caption: "VMI退仓作业单明细",
		pager:"pager",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
   		sortname: 'id',
    	multiselect: false,
		sortorder: "desc",
		height:"auto",
		//rowNum:-1,
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		loadComplete:function(){
			loxia.initContext($j("#tbl-details"));
			//判断是否显示导入SN
			var b = false;
			$j("#tbl-details tr[id]").each(function(i,tr){
				var rowData=$j("#tbl-details").jqGrid("getRowData",$j(tr).attr("id"));
				if(rowData["isSnSku"]==1){
					b = true;
				}
			});
			if(b){
				$j("#divSnImport").removeClass("hidden");
			} else {
				$j("#divSnImport").addClass("hidden");
			}
		}
	});
	
	$j("#execute").click(function(){
		if(confirm(i18("EXECUTIONASK"))){
			execution($j("#staID").val());
		}
	});
	$j("#cancel").click(function(){
		if(confirm(i18("CANCELASK"))){
			canceled($j("#staID").val());
		}
	});
	$j("#back").click(function(){
		$j("#divPd").removeClass("hidden");
		$j("#divList").addClass("hidden");
		
	});
	
	$j("#query").click(function(){
		var errors=loxia.validateForm("form_query");
		if(errors.length>0){
			jumbo.showMsg(errors);
			return false;
		}
		var postData = loxia._ajaxFormToObj("form_query");  							 
		$j("#tbl-query-info").jqGrid('setGridParam',{url:$j("body").attr("contextpath")+"/vmireturnstaquery.json",postData:postData}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl-query-info",
			{"intStatus":"whSTAStatus","transaction":"whDirectionMode"},
			$j("body").attr("contextpath")+"/vmireturnstaquery.json",postData);
	});
	
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val(0);
	});
	 
	
	$j("#print").click(function(){
		loxia.lockPage();
		if(beforePrintValidate()){
			var url = $j("body").attr("contextpath") + "/warehouse/printvmireturninfo.do?staID=" + $j("#staID").val();
			printBZ(loxia.encodeUrl(url),true);					
		}
		loxia.unlockPage();
	});
	
	$j("#printSendInfo").click(function(){
		loxia.lockPage();
		var id = $j("#staID").val();
		var url = $j("body").attr("contextpath") + "/printoutboundsendinfo.json?sta.id=" + id;
		printBZ(loxia.encodeUrl(url),true);
		loxia.unlockPage();
	});
	
	$j("#packing").click(function(){
		var id = $j("#staID").val();
		if (id != null){
			//add  1：有运单号 2：没有运单号 3：系统异常
			 var isEsprit= $j("#isEsprit").val();
			 var isPf= $j("#isPf").val();//唯品会 批发
			 if(""==isEsprit || isEsprit==null || isPf =='1'){
						var rs = loxia.syncXhrPost(baseUrl + "/vmireturnpacking.json",{"staID":id});
						if(rs && rs["result"] == 'success'){
							jumbo.showMsg("操作成功");
						}else if(rs && rs["result"] == 'error'){
							jumbo.showMsg("操作失败<br/>" + rs["message"]);
						}
				}else{
					var rs2 = loxia.syncXhr($j("body").attr("contextpath") + "/getStaDel.json",{"staId":id});
					if(rs2.msg=="3"){
						jumbo.showMsg("系统异常");return;
					}else if(rs2.msg=="2"){
						jumbo.showMsg("该作业单没有运单号");return;
					}else{
						var rs = loxia.syncXhrPost(baseUrl + "/vmireturnpacking.json",{"staID":id});
						if(rs && rs["result"] == 'success'){
							jumbo.showMsg("操作成功");
						}else if(rs && rs["result"] == 'error'){
							jumbo.showMsg("操作失败<br/>" + rs["message"]);
						}
					}
				}
			//end
//			var rs = loxia.syncXhrPost(baseUrl + "/vmireturnpacking.json",{"staID":id});
//			if(rs && rs["result"] == 'success'){
//				jumbo.showMsg("操作成功");
//			}else if(rs && rs["result"] == 'error'){
//				jumbo.showMsg("操作失败<br/>" + rs["message"]);
//			}
		}
	});
	
	$j("#update").click(function(){
		var staid = 	$j("#staID").val();
		var stad = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getVmiReturnAddress.json",{"staID":staid});
		$j("#province").val(stad['province']);
		$j("#city").val(stad['city']);
		$j("#district").val(stad['district']);
		$j("#username").val(stad['receiver']);
		$j("#address").val(stad['address']);
		$j("#telphone").val(stad['telephone']);
		$j("#lpcode").val(stad['lpCode']);
		$j("#select option[value='"+stad['lpCode']+"']").attr("selected","selected");
		//判断订单是否有装箱
		var carton = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/judgeCarton.json",{"staID":staid});
		
		if(carton.result){
			$j("#select").attr("disabled","disabled"); 
			$j("#hint").removeClass("hidden");
		}else{
			$j("#select").removeAttr("disabled");  
			$j("#hint").addClass("hidden");
		}
		
		$j("#show_address_dialog").dialog("open");
	});
	//sn号导入
	$j("#import").click(function(){
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg("请选择正确的Excel导入文件");//请选择正确的Excel导入文件
			return false;
		}
		loxia.lockPage();
		$j("#importFormSN").attr("action",$j("body").attr("contextpath") + "/warehouse/outboundSnImport.do");
		$j("#importFormSN").submit();
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
		$j("#importPMForm").attr("action", loxia.getTimeUrl(baseUrl + "/warehouse/packagingMaterialsImport.do?sta.id="+$j("#staID").val()));
		loxia.submitForm("importPMForm");
	});
	
	$j("#input").click(function(){
		var file = $j("#tnFile").val();
		var postfix = file.split(".")[1];
		if(postfix != "xls" && postfix != "xlsx"){
			jumbo.showMsg(i18("INVALID_MAND",i18("ENTITY_EXCELFILE")));
			return;
		}
		loxia.lockPage();
		$j("#importForm").attr("action", loxia.getTimeUrl(baseUrl + "/warehouse/predefinedOutImport.do?sta.id="+$j("#staID").val()));
		loxia.submitForm("importForm");
	});
	
	var warehouse = loxia.syncXhr($j("body").attr("contextpath") + "/getWHByOuId.json");
	if(!warehouse.exception){
		if(warehouse.warehouse.isNeedWrapStuff){
			$j("#packaging_materials").removeClass("hidden");
		} 
	}
	
	$j("#printPod").click(function (){
		printNikeCrwPod();
	});
	
});
function printNikeCrwPod(){
	loxia.lockPage();
	var id =$j("#staID").val();
	var dt = new Date().getTime();
	var url = $j("body").attr("contextpath") + "/printNikeCrwPod.json?staid=" + id;
	printBZ(loxia.encodeUrl(url),true);
	loxia.unlockPage();
}
function beforePrintValidate(){
	var ids = $j("#tbl-details").jqGrid('getDataIDs');
	var num=0,size=0;
	var datas;
	if(ids.length==0) {jumbo.showMsg("数据为空，数据出错..."); return false;}
	if($j("#status_") && $j("#status_").html()=='取消已处理') {jumbo.showMsg("取消已处理，无需打印库位信息..."); return false;}
	return true;
}

function closedialog(){
	$j("#show_address_dialog").dialog("close");
}

function editAddress(){
	var staid = 	$j("#staID").val();
	var _p = $j("#province").val().trim(); 
	var _c = $j("#city").val().trim();
	var _d = $j("#district").val().trim();
	var _u = $j("#username").val().trim();
	var _a = $j("#address").val().trim();
	var _t = $j("#telphone").val().trim();
	var _l =  $j("#select").val();
	var lpcode = $j("#lpcode").val();
	if(_l != lpcode){
		//如果修改物流商 判断是否有对应装箱信息是执行中或完成状态
		var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/checkCartonstatus.json",{"staID":staid});
		if(rs.result == 'errorcount'){
			jumbo.showMsg("此转店退仓单已经有装箱信息为完成状态，所以无法修改物流商!");
			return;
		}
		if(rs.result == 'error'){
			jumbo.showMsg("编辑失败,数据操作异常!");
			return;
		}
	}
	if (!_p){
		jumbo.showMsg("请输入省");
		return;
	}
	if (!_c){
		jumbo.showMsg("请输入市");
		return;
	}
	if (!_d){
		jumbo.showMsg("请输入区");
		return;
	}
	if (!_u){
		jumbo.showMsg("请输入联系人");
		return;
	}
	if (!_a){
		jumbo.showMsg("请输入联系地址");
		return;
	}
	if (!_t){
		jumbo.showMsg("请输入联系电话");
		return;
	}
	var postData = {};
	postData['staID'] = staid;
	postData['province'] = _p;
	postData['city'] = _c;
	postData['district'] = _d;
	postData['receiver'] = _u;
	postData['address'] = _a;
	postData['telphone'] = _t;
	if(_l == lpcode){
		postData['lpCode'] = "";
	}else{
		postData['lpCode'] = _l;
	}
	loxia.lockPage();
	loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+ "/editVmiReturnAddress.json",
			postData,
			{
			success:function(data){
				loxia.unlockPage();
				if(data){
					if(data.result=="success"){
						jumbo.showMsg("编辑成功!");
						$j("#show_address_dialog").dialog("close");						
					}else if(data.result=="error"){
						jumbo.showMsg("编辑失败,数据操作异常!");
					}
				} else {
					jumbo.showMsg("编辑失败,数据操作异常!");
				}
			},
			error:function(){
				loxia.unlockPage();
				jumbo.showMsg("编辑失败,数据操作异常!");}
	});
}

function importReturn(){
	showDt($j("#staID").val());
	jumbo.showMsg("导入占用执行成功");
}

/**
 * vmi退仓拣货单打印
 * @param obj
 */
function vmiBackprint(obj){
	var staid =$j(obj).parents("tr").attr("id");
	var url=window.$j("body").attr("contextpath")+"/vmiBackPrint.json?staid="+staid;
	printBZ(loxia.encodeUrl(url),true);
}

/***
 * 
function getSnsData(){
	var sns=[];
	$j.each($j(window.frames["snsupload"].document).find(".sns"),function(i,e){
		sns.push($j(e).val());
	});
	return sns.join(";");
}
 */

