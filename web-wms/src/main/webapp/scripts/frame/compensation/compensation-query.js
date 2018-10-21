$j.extend(loxia.regional['zh-CN'],{
});
var startTime = 1;
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function getFmtValue(tableId,rowId,column){
	return $j("#"+tableId+" tr[id="+rowId+"] td[aria-describedby='"+tableId+"_"+column+"']").attr("title")
}
function reloadSta(plId){
	$j("#tbl-showDetail").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/findCompensationDetailsList.json"),
			postData:{"compensationId":plId}}).trigger("reloadGrid",[{page:1}]);
	
}

function showDetail(obj){
	var row=$j(obj).parent().parent().attr("id"),pl=$j("#tbl-dispatch-list").jqGrid("getRowData",row);
	
	
	$j("#showList").addClass("hidden");
	$j("#showDetail").removeClass("hidden");
	$j("#showDetail").attr("compensationId",row);
	$j("#d_claimCode").html(pl['claimCode']);
	$j("#d_status").html(pl['claimStatus']);
	$j("#d_type").html(pl['claimTypeName']);
	$j("#d_cause").html(pl['claimReasonNmae']);
	$j("#d_owner").html(pl['shopOwner']);
	$j("#d_omsOrderCode").html(pl['omsOrderCode']);
	$j("#d_erpOrderCode").html(pl['erpOrderCode']);
	$j("#d_lpCode").html(pl['transName']);
	$j("#d_lpNum").html(pl['transNumber']);
	$j("#d_createUserName").html(pl['createUserName']);
	$j("#d_createTime").html(pl['createTime']);
	$j("#d_extralAmt").html(pl['extralAmt']);
	$j("#d_remark").html(pl['extralRemark']);
	$j("#d_totalClaimAmt").html(pl['totalClaimAmt']);
	$j("#d_logisticsepartmentAmt").val(pl['logisticsepartmentAmt']);
	$j("#d_expressDeliveryAmt").val(pl['expressDeliveryAmt']);
	$j("#d_disposeRemark").val(pl['disposeRemark']);
	$j("#inp_url").val(pl['fileUrl']);
	
	$j("#d_isOuterContainerDamaged_t").removeAttr("checked");
	$j("#d_isPackageDamaged_t").removeAttr("checked");
	$j("#d_isTwoSubBox_t").removeAttr("checked");
	$j("#d_isHasProductReturn_t").removeAttr("checked");
	$j("#d_isFilledWith_t").removeAttr("checked");
	$j("#d_isOuterContainerDamaged_f").removeAttr("checked");
	$j("#d_isPackageDamaged_f").removeAttr("checked");
	$j("#d_isTwoSubBox_f").removeAttr("checked");
	$j("#d_isHasProductReturn_f").removeAttr("checked");
	$j("#d_isFilledWith_f").removeAttr("checked");
	
	if(pl['isOuterContainerDamaged']=='true'){
		$j("#d_isOuterContainerDamaged_t").attr("checked",'true');
	}else if(pl['isOuterContainerDamaged']=='false'){
		$j("#d_isOuterContainerDamaged_f").attr("checked",'true');
	}
	if(pl['isPackageDamaged']=='true'){
		$j("#d_isPackageDamaged_t").attr("checked",'true');
	}else if(pl['isPackageDamaged']=='false'){
		$j("#d_isPackageDamaged_f").attr("checked",'true');
	}
	if(pl['isTwoSubBox']=='true'){
		$j("#d_isTwoSubBox_t").attr("checked",'true');
	}else if(pl['isTwoSubBox']=='false'){
		$j("#d_isTwoSubBox_f").attr("checked",'true');
	}
	if(pl['isHasProductReturn']=='true'){
		$j("#d_isHasProductReturn_t").attr("checked",'true');
	}else if(pl['isHasProductReturn']=='false'){
		$j("#d_isHasProductReturn_f").attr("checked",'true');
	}
	if(pl['isFilledWith']=='true'){
		$j("#d_isFilledWith_t").attr("checked",'true');
	}else if(pl['isFilledWith']=='false'){
		$j("#d_isFilledWith_f").attr("checked",'true');
	}
	
	var p=parseFloat(pl['logisticsepartmentAmt'])+parseFloat(pl['expressDeliveryAmt']);
	var z=parseFloat(pl['totalClaimAmt']);
	
	
	if(pl['claimStatus']==1){
		$j("#div_fail").addClass("hidden");
		$j("#div_success").addClass("hidden");
		$j("#div_dispose").removeClass("hidden");
		$j("#d_logisticsepartmentAmt").removeAttr("disabled");
		$j("#d_expressDeliveryAmt").removeAttr("disabled");
		$j("#d_disposeRemark").removeAttr("disabled");
	}else if(pl['claimStatus']==2){
		$j("#div_fail").addClass("hidden");
		$j("#div_success").addClass("hidden");
		$j("#div_dispose").addClass("hidden");
		$j("#d_logisticsepartmentAmt").attr({"disabled":"disabled"});
		$j("#d_expressDeliveryAmt").attr({"disabled":"disabled"});
		$j("#d_disposeRemark").attr({"disabled":"disabled"});
	}else if(pl['claimStatus']==3){
		if(pl['claimAffirmStatus']==1||(p==z)){
			$j("#div_fail").removeClass("hidden");
			$j("#div_success").removeClass("hidden");
		}else{
			$j("#div_fail").addClass("hidden");
			$j("#div_success").addClass("hidden");
		}
		$j("#d_logisticsepartmentAmt").attr({"disabled":"disabled"});
		$j("#d_expressDeliveryAmt").attr({"disabled":"disabled"});
		$j("#d_disposeRemark").attr({"disabled":"disabled"});
	}else {
		$j("#div_fail").addClass("hidden");
		$j("#div_success").addClass("hidden");
		$j("#div_dispose").addClass("hidden");
		$j("#d_logisticsepartmentAmt").attr({"disabled":"disabled"});
		$j("#d_expressDeliveryAmt").attr({"disabled":"disabled"});
		$j("#d_disposeRemark").attr({"disabled":"disabled"});
	}
	
	
	
	
	reloadSta(row);
}

function downFile(obj){
	var row=$j(obj).parent().parent().attr("id"),pl=$j("#tbl-dispatch-list").jqGrid("getRowData",row);
	var url=pl['fileUrl'];
	if(url==""||url=="null"){
		jumbo.showMsg("此单据无附件！");
		return false;
	}
	window.open(url);
	
}



$j(document).ready(function (){
	initShopQuery("warehouseId","name");
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findCompensateType.do");
	for(var idx in result){
		$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#claimTypeId"));
	}
	
	var trans = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransactionTypeName.do");
	for(var idx in trans){
		$j("<option value='" + trans[idx].code + "'>"+ trans[idx].name +"</option>").appendTo($j("#transCode"));
	}
	
	var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/json/getSendWarehouse.do"));
	for(var idx in rs){
			$j("<option value='" + rs[idx].id + "'>"+ rs[idx].name +"</option>").appendTo($j("#warehouseId"));
	}
	
	$j("#tbl-dispatch-list").jqGrid({
		url:$j("body").attr("contextpath") + "/findCompensationList.json",
		postData:loxia._ajaxFormToObj("plForm"),
		datatype: "json",
		colNames: ["ID","索赔编码","相关订单号","索赔状态","索赔分类","索赔原因","店铺名称","物流商名称","快递单号","申请理赔总金额","物流部赔偿金额",
		           "快递商赔偿金额","预警天数","发货仓","订单发货时间","创建人","创建时间","最后修改人","最后修改时间","附件下载","承担方","是否外箱破损",
		           "产品包装是否破损","是否二次封箱","是否有产品退回","箱内填充是否充分","平台备注","OMS订单号","附加金额","附加金额备注","索赔金额确认状态","收件人姓名","收件人联系方式","收件人地址","处理意见"],
			colModel: [	{name: "id", index: "co.id", hidden: true},
						{name:"claimCode",index:"co.claim_Code",formatter:"linkFmatter",formatoptions:{onclick:"showDetail"},width:90,resizable:true},
						{name:"omsOrderCode", index:"co.oms_Order_Code" ,width:90,resizable:true},
						{name:"claimStatus", index:"co.status",width:90,resizable:true,formatter:'select',editoptions:{value:"1:已创建;2:已处理;3:已审核;5:索赔成功;10:索赔失败;17:已作废"}},
						{name:"claimTypeName", index:"ct.name", width:90, resizable:true},
						{name:"claimReasonNmae",index:"cc.name",width:90,resizable:true,},
						{name:"shopOwner",index: "bc.name",width:90,resizable:true},
						{name:"transName",index:"mt.name",width:90,resizable:true},
						{name:"transNumber",index:"co.trans_Number",width:90,resizable:true},
						{name:"totalClaimAmt",index:"co.total_claim_amt",width:90,resizable:true},
						{name:"logisticsepartmentAmt",index:"co.logistics_department_amt",width:90,resizable:true},
						{name:"expressDeliveryAmt",index:"co.express_delivery_amt",width:90,resizable:true},
						{name:"warn",index:"warn",width:90,resizable:true},
						{name:"warehouseName",index:"co.warehouseName",width:50},
						{name:"staFinishDate",index:"staFinishDate",width:90,resizable:true},
						{name:"createUserName",index:"co.createUserName",width:90,resizable:true},
						{name:"createTime",index:"co.create_Time",width:90,resizable:true},
						{name:"userName",index:"userName",width:90,resizable:true},
						{name:"lastModifyTime",index:"lastModifyTime",width:90,resizable:true},
						{name:"fileUrl",index:"co.fileUrl",width:90,formatter:"buttonFmatter", formatoptions:{"buttons":{label:"下载", onclick:"downFile(this);"}},resizable:true},
						{name:"bearTarget",index:"co.bearTarget",width:90,resizable:true,hidden: true},
						{name:"isOuterContainerDamaged",index:"co.isOuterContainerDamaged",width:90,resizable:true,formatter:'select',editoptions:{value:"true:是;false:否"},hidden: true},
						{name:"isPackageDamaged",index:"co.isPackageDamaged",width:90,resizable:true,formatter:'select',editoptions:{value:"true:是;false:否"},hidden: true},
						{name:"isTwoSubBox",index:"co.isTwoSubBox",width:90,resizable:true,formatter:'select',editoptions:{value:"true:是;false:否"},hidden: true},
						{name:"isHasProductReturn",index:"co.isHasProductReturn",width:90,resizable:true,formatter:'select',editoptions:{value:"true:是;false:否"},hidden: true},
						{name:"isFilledWith",index:"co.isFilledWith",width:90,resizable:true,formatter:'select',editoptions:{value:"true:是;false:否"},hidden: true},
						{name:"remark",index:"co.remark",width:90,resizable:true ,hidden: true},
						{name:"erpOrderCode",index:"co.erpOrderCode",width:90,resizable:true,hidden: true},
						{name:"extralAmt",index:"co.extralAmt",width:90,resizable:true,hidden: true},
						{name:"extralRemark",index:"co.extralRemark",width:90,resizable:true,hidden: true},
						{name:"claimAffirmStatus",index:"co.claimAffirmStatus",width:90,resizable:true,hidden: true},
			            {name:"receiver",index:"co.receiver",width:50, hidden: true},
			            {name:"mobile",index:"co.mobile",width:50, hidden: true},
			            {name:"receiverAddress",index:"co.receiverAddress",width:250, hidden: true},
						{name:"disposeRemark",index:"co.dispose_remark",width:90,resizable:true,hidden: true}
			            ],
		caption: '索赔单查询列表',
	   	sortname: 'co.id',
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
	jumbo.bindTableExportBtn("tbl-dispatch-list");	
	$j("#search").click(function(){
		$j("#tbl-dispatch-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/findCompensationList.json"),
			postData:loxia._ajaxFormToObj("plForm")}).trigger("reloadGrid",[{page:1}]);
		jumbo.bindTableExportBtn("tbl-dispatch-list",{},loxia.getTimeUrl($j("body").attr("contextpath") + "/json/findCompensationList.json"),loxia._ajaxFormToObj("plForm"));	
	});
	$j("#reset").click(function(){
		document.getElementById("plForm").reset();
	});
	
	$j("#tbl-showDetail").jqGrid({
		datatype: "json",//"已发货单据数","已发货商品件数",
		//colNames: ["ID","备注","作业单号","状态","相关单据号","作业类型名称","相关店铺","物流服务商简称","创建时间","计划执行总件数"],
		colNames: ["ID","相关单据号","换货申请码","SKU编码","商品名称","购买数量","购买单价","购买金额","申请理赔数量","申请理赔单价","申请理赔金额"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name: "omsOrderCode", index: "omsOrderCode",},
					{name:"rasCode", index:"rasCode" ,width:80,resizable:true},
					{name: "skuCode", index:"skuCode",width:120,resizable:true},
					{name:"skuName", index:"skuName", width:90, resizable:true},
					{name:"quantity",index:"quantity",width:120,resizable:true},
					{name:"unitPrice",index: "unitPrice",width:100,resizable:true},
					{name:"totalPrice",index:"totalPrice",width:120,resizable:true},
	                {name:"claimQty",index:"claimQty",width:70,resizable:true},
	                {name:"claimUnitPrice",index:"claimUnitPrice",width:70,resizable:true},
	                {name:"claimAmt",index:"claimAmt",width:90,resizable:true}
					],
		caption: '索赔明细',
	   	sortname: 'cd.id',
	    multiselect: false,
		sortorder: "desc",
		width:"auto",
		height:"auto",
		rowNum:-1,
		viewrecords: true,
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
		
	
	$j("#reloadSta").click(function(){
		//$j("#tbl-showReady").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/pllistforverify.json"),postData:{"plCmd.id":0}}).trigger("reloadGrid",[{page:1}]);
		showDetail($j("#tbl-dispatch-list").jqGrid("getRowData",$j("#showDetail").attr("plId"))['code']);
		reloadSta($j("#showDetail").attr("plId"));
	});
	$j("#printDeliveryInfo").click(function(){
		    loxia.lockPage();
			var staId = $j("#showDetail").attr("staId");
			var url = $j("body").attr("contextpath") + "/printsingledeliverymode1.json?sta.id=" + staId;
			printBZ(loxia.encodeUrl(url),true);					
		    loxia.unlockPage();
    });
	
	
	
	$j("#file_button").click(function(){
		var url=$j("#inp_url").val();
		if(url==""||url=="null"){
			jumbo.showMsg("此单据无附件！");
			return false;
		}
		window.open(url);
	});
	
	$j("#fileDown").click(function(){
		var hehe=loxia._ajaxFormToObj("plForm");
		var url=$j("body").attr("contextpath") + "/compensateDownFile.json";
		var postdata="whCompensationCommand.claimCode="+hehe['whCompensationCommand.claimCode']+"&whCompensationCommand.claimTypeId="+
			hehe['whCompensationCommand.claimTypeId']+"&whCompensationCommand.claimReasonId="+hehe['whCompensationCommand.claimReasonId']+
			"&whCompensationCommand.claimStatus="+hehe['whCompensationCommand.claimStatus']+"&whCompensationCommand.shopOwner+"+hehe['']+
			"&whCompensationCommand.omsOrderCode="+hehe['whCompensationCommand.omsOrderCode']+"&whCompensationCommand.transNumber="+
			hehe['whCompensationCommand.transNumber']+"&whCompensationCommand.transCode="+hehe['whCompensationCommand.transCode']+
			"&finishStartDate="+hehe['finishStartDate']+"&finishEndDate="+hehe['finishEndDate']+"&startDate="+hehe['startDate']+"&endDate="+
			hehe['endDate']+"&whCompensationCommand.createUserName="+hehe['whCompensationCommand.createUserName'];
		//校验文件大小
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/confirmFileSize.json",loxia._ajaxFormToObj("plForm"));
		if(result.message=="ok"){
		url=url+"?"+postdata
		window.open(url);
		}else if(result.message=="error"){
			jumbo.showMsg("文件过大，不能超过15M！");
		}else{
			jumbo.showMsg("批量下载失败！");

		}
	});
	
	/**
	 * 导出索赔信息
	 */
	$j("#exportClaimant").click(function(){
		//$j("#tbl-dispatch-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/exportClaimantInfo.json"),
			//postData:loxia._ajaxFormToObj("plForm")}).trigger("reloadGrid",[{page:1}]);
		//jumbo.bindTableExportBtn("tbl-dispatch-list",{},loxia.getTimeUrl($j("body").attr("contextpath") + "/json/exportClaimantInfo.json"),loxia._ajaxFormToObj("plForm"));	

		var hehe=loxia._ajaxFormToObj("plForm");
		var postdata="whCompensationCommand.claimCode="+hehe['whCompensationCommand.claimCode']+"&whCompensationCommand.claimTypeId="+
		hehe['whCompensationCommand.claimTypeId']+"&whCompensationCommand.claimReasonId="+hehe['whCompensationCommand.claimReasonId']+
		"&whCompensationCommand.claimStatus="+hehe['whCompensationCommand.claimStatus']+"&whCompensationCommand.shopOwner+"+hehe['']+
		"&whCompensationCommand.omsOrderCode="+hehe['whCompensationCommand.omsOrderCode']+"&whCompensationCommand.transNumber="+
		hehe['whCompensationCommand.transNumber']+"&whCompensationCommand.transCode="+hehe['whCompensationCommand.transCode']+
		"&finishStartDate="+hehe['finishStartDate']+"&finishEndDate="+hehe['finishEndDate']+"&startDate="+hehe['startDate']+"&endDate="+
		hehe['endDate']+"&whCompensationCommand.createUserName="+hehe['whCompensationCommand.createUserName'];
		var url=$j("body").attr("contextpath") + "/warehouse/exportClaimantInfo.do?"+postdata;
		$j("#exportFrame").attr("src",url);
	});
	
	
	$j("#back").click(function(){
		//window.location=loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/plverifyentry.do");
		$j("#showList").removeClass("hidden");
		$j("#showDetail").addClass("hidden");
		$j("#search").trigger("click");
	});
	
	$j("#claimTypeId").change(function(){
		var claimTypeId=$j("#claimTypeId").val();
		var claimReason = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findCompensateCause.do?claimTypeId="+claimTypeId);
		$j("#claimReasonId").empty();
		$j("<option value=''>--请选择--</option>").appendTo($j("#claimReasonId"));
		for(var idx in claimReason){
			$j("<option value='" + claimReason[idx].code + "'>"+ claimReason[idx].name +"</option>").appendTo($j("#claimReasonId"));
		}
	});
	
	
	$j("#dispose").click(function(){
		
		/*if($j("#d_logisticsepartmentAmt").val()==""&&$j("#d_expressDeliveryAmt").val()==""){
			jumbo.showMsg("请填写金额！");
			return false;
		}*/
		
		
		var   compensationId=$j("#showDetail").attr("compensationId");
		var logisticsepartmentAmt=$j("#d_logisticsepartmentAmt").val();
		var expressDeliveryAmt=$j("#d_expressDeliveryAmt").val();
		var disposeRemark=$j("#d_disposeRemark").val();
		var totalClaimAmt=$j("#d_totalClaimAmt").html();
		if(expressDeliveryAmt==""){
			expressDeliveryAmt=0;
			
			
		}
		if(logisticsepartmentAmt==""){
			logisticsepartmentAmt=0;
		}
		var p=parseFloat(expressDeliveryAmt)+parseFloat(logisticsepartmentAmt);
		var z=parseFloat(totalClaimAmt);
		if(!validata(logisticsepartmentAmt)){
			return false;
		}
		if(!validata(expressDeliveryAmt)){
			return false;
		}
		
		if(z<p){
			jumbo.showMsg("赔偿金额不能大于申请的总金额");
			return false;
		}else if(z>p){
			if(disposeRemark==""){
				jumbo.showMsg("赔偿金额小于申请的总金额则必须填写处理意见");
				return false;
			}
			
		}
		if(!window.confirm('你确定处理索赔吗？')){
            return false;
         }
		
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/disposeCompensate.json?logisticsepartmentAmt="
				+logisticsepartmentAmt+"&expressDeliveryAmt="+expressDeliveryAmt+"&disposeRemark="+disposeRemark+"&compensationId="+compensationId);
		if(rs && rs.msg == 'success'){
			jumbo.showMsg("操作成功！");
			$j("#back").trigger("click");
		}else{
			
			jumbo.showMsg(rs.msg);
		}
	});
	
	$j("#success").click(function(){
		if(!window.confirm('确定索赔成功吗？')){
            return false;
         }
		var   compensationId=$j("#showDetail").attr("compensationId");
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/compensateSuccess.json?compensationId="+compensationId);
		if(rs && rs.msg == 'success'){
			jumbo.showMsg("操作成功！");
			$j("#back").trigger("click");
		}else{
			jumbo.showMsg(rs.msg);
		}
	});
	
	$j("#fail").click(function(){
		if(!window.confirm('确定索赔失败吗？')){
            return false;
         }
		var   compensationId=$j("#showDetail").attr("compensationId");
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/compensateFail.json?compensationId="+compensationId);
		if(rs && rs.msg == 'success'){
			jumbo.showMsg("操作成功！");
			$j("#back").trigger("click");
		}else{
			jumbo.showMsg(rs.msg);
		}
	});
	
	
	
	
});


function validata(obj)
{
  var reg = /^\d+(?=\.{0,1}\d+$|$)/;
  
  var v=obj.value;	  
  if(reg.test(v)){
	  return true;
  }
  if(v!="" && v!=null){
	  
	  jumbo.showMsg("请输入正确的赔偿金额");
	  obj.value="";
	  return false ;  
  }
  return true;
}
