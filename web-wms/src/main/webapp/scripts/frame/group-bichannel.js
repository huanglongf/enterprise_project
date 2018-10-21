$j.extend(loxia.regional['zh-CN'],{
	CODE : "配货批次号"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function showDetail(obj){
	$j("#cuIdErroe").html("");
	$j('#detailForm')[0].reset();
	var id=$j(obj).parent().parent().attr("id");
	$j("#bichannel-list").addClass("hidden");
	$j("#detail").removeClass("hidden");
	var channel = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getBiChannelById.do",{"channelId":id});//渠道信息
	var bcsa20 = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getBiChannelSpecialActionByCidTypePackingPage.do",{"channelId":id});//渠道行为运单
	var bcsa30 = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getBiChannelSpecialActionByCidTypeExpressBill.do",{"channelId":id});//渠道行为装箱单
	$j("#cid").val(id);
	$j("#pid").val(bcsa30["id"]);
	$j("#eid").val(bcsa20["id"]);
	$j("#tbl-bichannel-imperfect-list").jqGrid('setGridParam',{
		url:window.$j("body").attr("contextpath")+"/findBiChanneImperfectlList.json?channelId="+id,
		//postData:postData,
		page:1
	}).trigger("reloadGrid");
	$j("#sf_yun_config_list").jqGrid('setGridParam',{
		url:window.$j("body").attr("contextpath")+"/findSfTimeList.json?channelId="+id,
		//postData:postData,
		page:1
	}).trigger("reloadGrid");
	$j("#imperfect").removeClass("hidden");
	$j("#imperfectLine").addClass("hidden");
	$j("#imperfect-add").removeClass("hidden");
	$j("#addImperfectLine").addClass("hidden");
	$j("#imperfectLine-add").addClass("hidden");
	//渠道信息
	$j("#cCode").text(channel["code"]);
	$j("#cName").val(channel["name"]);
	$j("#address").val(channel["address"]);
	$j("#rtnAddress").val(channel["rtnWarehouseAddress"]);
	$j("#telephone").val(channel["telephone"]);
	$j("#transAccountsCode").val(channel["transAccountsCode"]);
	$j("#sfJcustid").val(channel["sfJcustid"]);
	$j("#sfJcusttag").val(channel["sfJcusttag"]);
	$j("#zipcode").val(channel["zipcode"]);
	if(channel["isNotValInBoundBachCode"] == 1){
		$j("#isNotValInBoundBachCode").attr("checked","checked");
	}
	/*if(channel["isReturnCheckBatch"] == 1){
		$j("#isReturnCheckBatch").attr("checked","checked");
	}*/
	if(channel["isPickinglistByLoc"]==1){
		$j("#isPickinglistByLoc").attr("checked","checked");
	}
	if(channel["isPda"]==1){
		$j("#isPda").attr("checked","checked");
	}
	if(channel["intSpecialType"] == 1){
		$j("#isDefaultStatus").attr("checked","checked");
	}
	if(channel["isImperfect"]==1){
		$j("#isImperfect").attr("checked","checked");
	}
	if(channel["isImperfectPoId"]==1){
		$j("#isImperfectPoId").attr("checked","checked");
	}
	if(channel["isImperfectTime"]==1){
		$j("#isImperfectTime").attr("checked","checked");
	}
	if(channel["isSupportNoCancelSta"]==1){
		$j("#isSupportNoCancelSta").attr("checked","checked");
	}
	if(channel["isSupportNextMorning"]==1){
		$j("#isSupportNextMorning").attr("checked","checked");
	}
	if(channel["transErrorToEms"]==1){
		$j("#transErrorToEms").attr("checked","checked");
	}
	
	
	$j("#cmId option[value='"+channel["customerId"]+"']").attr("selected","selected");
	$j("#limitType option[value='"+channel["limitTypeInt"]+"']").attr("selected","selected");
	$j("#limitAmount").val(channel["limitAmount"]);
	$j("#skuCategories").val(channel["skuCategories"]);
	$j("#companyName1 option[value='"+channel["companyName"]+"']").attr("selected","selected");
	$j("#cuId").val(channel["customerId"]);
	$j("#minInsurance").val(bcsa30["minInsurance"]);
	$j("#maxInsurance").val(bcsa30["maxInsurance"]);
	if(bcsa30["isInsurance"] == 1){
		$j("#isInsurance").attr("checked","checked");
	}
	if(channel["isMarger"] == 1){
		$j("#isMarger").attr("checked","checked");
	}
	if(channel["isReturnNeedPackage"] == 1){
		$j("#isReturnNeedPackage").attr("checked","checked");
	}
	if(channel["isSms"] == 1){
		$j("#isSms").attr("checked","checked");
	}
	if(channel["isJdolOrder"] == 1){
		$j("#isJdolOrder").attr("checked","checked");
	}
	if(channel["isCartonStaShop"] == 1){
		$j("#isCartonStaShop").attr("checked","checked");
	}
	//打印海关单维护
	if(channel["isPrintMaCaoHGD"] == 1){
		$j("#isPrintMaCaoHGD").attr("checked","checked");
		$j("#setMoneyLmit").removeClass("hidden");
	}
	$j("#moneyLmit").val(channel["moneyLmit"]);
	//运单维护
	$j("#shopName").val(bcsa30["shopName"]);
	$j("#srtnAddress").val(bcsa30["rtnAddress"]);
	$j("#pcontactsPhone").val(bcsa30["contactsPhone"]);
	$j("#pcontactsPhone1").val(bcsa30["contactsPhone1"]);
	$j("#contacts").val(bcsa30["contacts"]);
	$j("#transAddMemo").val(bcsa30["transAddMemo"]);
	//装箱单维护
	$j("#shopName1").val(bcsa20["shopName"]);
	$j("#srtnAddress1").val(bcsa20["rtnAddress"]);
	$j("#econtactsPhone").val(bcsa20["contactsPhone"]);
	$j("#econtactsPhone1").val(bcsa20["contactsPhone1"]);
	$j("#contacts1").val(bcsa20["contacts"]);
	$j("#printCode").val(bcsa20["customPrintCode"]);
	if(bcsa20["isNotDisplaySum"] == 1){
		$j("#isSum").attr("checked","checked");
	}
	
}
function showImperfect(obj){
	var id=$j(obj).parent().parent().attr("id");
	$j("#tbl-bichannel-imperfect-line-list").jqGrid('setGridParam',{
		url:window.$j("body").attr("contextpath")+"/findBiChanneImperfectlLineList.json?imperfectId="+id,
		page:1
	}).trigger("reloadGrid");
	$j("#imperfectId").val(id);
	$j("#imperfectLineadd").removeClass("hidden");
	$j("#imperfect-add").addClass("hidden");
	$j("#imperfect").addClass("hidden");
	$j("#imperfectLine").removeClass("hidden");
	$j("#imperfectLine-add").removeClass("hidden");
	$j("#save").addClass("hidden");
	$j("#back").addClass("hidden");
	$j("#lineback").removeClass("hidden");
}
function initCompanyName(){
	var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/findInvoiceCompanyList.json"));
	for(var idx in rs){
		$j("<option value='" + rs[idx].companyName + "'>"+ rs[idx].companyChName +"</option>").appendTo($j("#companyName"));
		$j("<option value='" + rs[idx].companyName + "'>"+ rs[idx].companyChName +"</option>").appendTo($j("#companyName1"));
	}
}

function initPrintCode(){
	var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/getPrintCode.json"));
	for(var idx in rs){
		$j("<option value='" + rs[idx].templetCode + "'>"+ rs[idx].templetCode +"</option>").appendTo($j("#printCode"));
	}
}
	$j(document).ready(function (){
		$j("#tabs").tabs();
		initCompanyName();
		initPrintCode();
		var baseUrl = $j("body").attr("contextpath");
		var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findBiCustomer.do");
		for(var idx in result){
			$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#customerId"));
			$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#cmId"));
		}
	
		$j("#tbl-bichannel-list").jqGrid({
			url:baseUrl + "/findBiChannelList.json",
			datatype: "json",
			colNames: ["ID","渠道编码","渠道名称","客户名称", "发票公司简称(英文)", "发票公司简称(中文)", "发货地址"],
			colModel: [
								{name: "id", hidden: true},
								{name:"code",index:"code", width:280,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, resizable:true},
								{name:"name",index:"name",width:280,resizable:true},
								{name:"cName",index:"cName",width:90,resizable:true},
								{name:"companyName",index:"companyName",width:90,resizable:true},
								{name:"companyChName",index:"companyChName",width:90,resizable:true},
								{name:"address",index:"address",width:300,resizable:true}
					 		],
			caption: "渠道信息列表",
		   	sortname: 'id',
		   	multiselect: false,
			sortorder: "desc",
			pager:"#pager",
			width:1000,
		   	shrinkToFit:false,
			height:"auto",
			rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
			viewrecords: true,
		   	rownumbers:true,
			jsonReader: {repeatitems : false, id: "0"}
		});
		$j("#sf_yun_config_list").jqGrid({//sf时效类型
		//	url:baseUrl + "/findSfTimeList.json",
			datatype: "json",
			colNames: ["ID","仓库","省","市","时效类型","修改时间","修改人"],
			colModel: [
								{name: "id", hidden: true},
								{name: "opName",index:"opName",width:100},
								{name:"province",index:"province", width:100},
								{name: "city",index:"city",width:100 },
								{name: "timeTypeString",index:"timeTypeString",width:100},
								{name: "updateTime",index:"updateTime",width:100},
								{name: "userName",index:"userName",width:100}
					 		],
			caption: "SF时效信息列表",
		   	sortname: 'id',
		   	multiselect: false,
			sortorder: "desc",
			pager:"#pagerSf",
			width:800,
		   	shrinkToFit:false,
			height:"auto",
			rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
			viewrecords: true,
		   	rownumbers:true,
			jsonReader: {repeatitems : false, id: "0"}
		});
		
		$j("#tbl-bichannel-imperfect-list").jqGrid({
			url:baseUrl + "/findBiChanneImperfectlList.json",
			datatype: "json",
			colNames: ["ID","渠道残次类型编码","残次类型描述","渠道名称"],
			colModel: [
								{name: "id", hidden: true},
								{name: "code",index:"code",width:280},
								{name:"name",index:"name", width:280,formatter:"linkFmatter",formatoptions:{onclick:"showImperfect"}, resizable:true},
								{name: "channelName",index:"channelName",width:280 },
					 		],
			caption: "渠道残次信息列表",
		   	sortname: 'id',
		   	multiselect: false,
			sortorder: "desc",
			pager:"#pagerImperfect",
			width:1000,
		   	shrinkToFit:false,
			height:"auto",
			rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
			viewrecords: true,
		   	rownumbers:true,
			jsonReader: {repeatitems : false, id: "0"}
		});
		$j("#tbl-bichannel-imperfect-line-list").jqGrid({
			url:baseUrl + "/findBiChanneImperfectlLineList.json",
			datatype: "json",
			colNames: ["ID","残次原因编码","残次原因描述","残次类型名称"],
			colModel: [
								{name: "id", hidden: true},
								{name: "code",index:"code",width:280},
								{name:"name",index:"name", width:280},
								{name: "imperfectName",index:"imperfectName",width:280 },
					 		],
			caption: "渠道残次信息列表",
		   	sortname: 'id',
		   	multiselect: false,
			sortorder: "desc",
			pager:"#pagerImperfectLine",
			width:1000,
		   	shrinkToFit:false,
			height:"auto",
			rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
			viewrecords: true,
		   	rownumbers:true,
			jsonReader: {repeatitems : false, id: "0"}
		});
		
		//查询
		$j("#search").click(function(){
				var postData = loxia._ajaxFormToObj("channelForm");
				$j("#tbl-bichannel-list").jqGrid('setGridParam',{
					url:window.$j("body").attr("contextpath")+"/findBiChannelList.json",
					postData:postData,
					page:1
				}).trigger("reloadGrid");
		});
		
		$j("#back").click(function(){
			$j("#bichannel-list").removeClass("hidden");
			$j("#detail").addClass("hidden");
		});
		//保存
		$j("#save").click(function(){
			//判断渠道名称是不是由中文、英文、数字组成
			var deleteBCWR = 0;
			var reg = /^[\u4e00-\u9fa5a-zA-Z0-9\s\-]+$/;
//			var baseUrl = $j("body").attr("contextpath");
			var postData = {};
			if($j("#cName").val() == ""){
					jumbo.showMsg("渠道名称必须填写！");
 					return;
			}
			if(!reg.test($j("#cName").val().toString())){
					jumbo.showMsg("渠道名称格式不正确，只能有中文、英文、数字、-线、空格组成！");
 					return;					
			}
			if($j("#address").val() == ""){
					jumbo.showMsg("发货地址必须填写！");
 					return;
			}
			if($j("#rtnAddress").val() == ""){
					jumbo.showMsg("退换货地址必须填写！");
 					return;
			}
			if($j("#telephone").val() == ""){
					jumbo.showMsg("售后联系电话必须填写！");
 					return;
			}
			if($j("#zipcode").val() == ""){
					jumbo.showMsg("邮编必须填写！");
 					return;
			}
			postData["biChannel.code"] =  $j("#cCode").html();
			if($j("#isMarger").attr("checked")){
				//选择不允许混合创建就要判断是否和渠道组有绑定
				var isMarger = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/checkBiChannelRefByBiChannel.json",postData);
				if(isMarger["msg"] == "DATA"){
						//有数据则必须为混合创建模式
						jumbo.showMsg("该渠道必须为混合创建配货清单模式！");
	 					return;
				}
			}
			if($j("#cmId").val() != $j("#cuId").val()){
				//如果该渠道绑定过仓库，就先要删除对应的绑定仓库
				deleteBCWR = 1;
			}
			if(($j("#sfJcustid").val() == "" && $j("#sfJcusttag").val() !="") || ($j("#sfJcustid").val() != "" && $j("#sfJcusttag").val() =="")){
				jumbo.showMsg("顺丰结算月结账号和顺丰客户编码不能为空或者都为空！");
				return;
			}
			postData["biChannel.skuCategories"]=$j("#skuCategories").val();
			postData["biChannel.sfJcustid"] =  $j("#sfJcustid").val();
			postData["biChannel.sfJcusttag"] =  $j("#sfJcusttag").val();
			postData["biChannel.transAccountsCode"] =  $j("#transAccountsCode").val();
			postData["biChannel.name"] =  $j("#cName").val();
			postData["biChannel.address"] =  $j("#address").val();
			postData["biChannel.rtnWarehouseAddress"] =  $j("#rtnAddress").val();
			postData["biChannel.telephone"] =  $j("#telephone").val();
			postData["biChannel.zipcode"] =  $j("#zipcode").val();
			postData["biChannel.limitAmount"] =  $j("#limitAmount").val();
			postData["biChannel.limitTypeInt"] =  $j("#limitType").val();
			if($j("#isNotValInBoundBachCode").attr("checked")){
				postData["biChannel.isNotValInBoundBachCode"] = true;
			}else{
				postData["biChannel.isNotValInBoundBachCode"] = false;
			}
			
			if($j("#isPickinglistByLoc").attr("checked")){
				postData["biChannel.isPickinglistByLoc"] = true;
			}else{
				postData["biChannel.isPickinglistByLoc"] = false;
			}
			
			if($j("#isPda").attr("checked")){
				postData["biChannel.isPda"] = true;
			}else{
				postData["biChannel.isPda"] = false;
			}
			
			/*if($j("#isReturnCheckBatch").attr("checked")){
				postData["biChannel.isReturnCheckBatch"] = true;
			}else{
				postData["biChannel.isReturnCheckBatch"] = false;
			}*/
			if($j("#isDefaultStatus").attr("checked")){
				postData["biChannel.specialType"] = "DEFAULT_DEFECTIVE";
			}
			if($j("#isSupportNextMorning").attr("checked")){
				postData["biChannel.isSupportNextMorning"] = true;
			}else{
				postData["biChannel.isSupportNextMorning"] = false;	
			}
			if($j("#transErrorToEms").attr("checked")){
				postData["biChannel.transErrorToEms"] = true;
			}else{
				postData["biChannel.transErrorToEms"] = false;	
			}
			if($j("#isMarger").attr("checked")){
				postData["biChannel.isMarger"] = true;
			}else{
				postData["biChannel.isMarger"] = false;	
			}
			if($j("#isReturnNeedPackage").attr("checked")){
				postData["biChannel.isReturnNeedPackage"] = true;
			}else{
				postData["biChannel.isReturnNeedPackage"] = false;	
			}
			if($j("#isJdolOrder").attr("checked")){
				postData["biChannel.isJdolOrder"] = true;
			}else{
				postData["biChannel.isJdolOrder"] = false;	
			}
			if($j("#isCartonStaShop").attr("checked")){
				postData["biChannel.isCartonStaShop"] = true;
			}else{
				postData["biChannel.isCartonStaShop"] = false;	
			}
			if($j("#isImperfect").attr("checked")){
				postData["biChannel.isImperfect"] = true;
			}else{
				postData["biChannel.isImperfect"] = false;
			}
			if($j("#isImperfectPoId").attr("checked")){
				postData["biChannel.isImperfectPoId"] = true;
			}else{
				postData["biChannel.isImperfectPoId"] = false;
			}
			if($j("#isImperfectTime").attr("checked")){
				postData["biChannel.isImperfectTime"] = true;
			}else{
				postData["biChannel.isImperfectTime"] = false;
			}
			if($j("#isSupportNoCancelSta").attr("checked")){
				postData["biChannel.isSupportNoCancelSta"] = true;
			}else{
				postData["biChannel.isSupportNoCancelSta"] = false;
			}
			
			var minIn = $j("#minInsurance").val();
			var maxIn = $j("#maxInsurance").val();
			postData["biChannel.customer.id"] =  $j("#cmId").val();
			postData["biChannel.companyName"] =  $j("#companyName1").val();
			postData["bcsap.shopName"] =  $j("#shopName").val();
			postData["bcsap.rtnAddress"] =  $j("#srtnAddress").val();
			postData["bcsap.contactsPhone"] =  $j("#pcontactsPhone").val();
			postData["bcsap.minInsurance"] =  minIn;
			postData["bcsap.maxInsurance"] =  maxIn;
			postData["bcsae.minInsurance"] =  minIn;
			postData["bcsae.maxInsurance"] =  maxIn;
			postData["bcsap.contactsPhone1"] =  $j("#pcontactsPhone1").val();
			postData["bcsap.contacts"] =  $j("#contacts").val();
			postData["bcsap.transAddMemo"] =  $j("#transAddMemo").val();
			postData["bcsae.shopName"] =  $j("#shopName1").val();
			postData["bcsae.rtnAddress"] =  $j("#srtnAddress1").val();
			postData["bcsae.contactsPhone"] =  $j("#econtactsPhone").val();
			postData["bcsae.contactsPhone1"] =  $j("#econtactsPhone1").val();
			postData["bcsae.contacts"] =  $j("#contacts1").val();
			postData["bcsae.customPrintCode"] =  $j("#printCode").val();
			postData["bcsap.customPrintCode"] =  $j("#printCode").val();
			postData["deleteBCWR"] = deleteBCWR;
			if(parseInt(minIn) > parseInt(maxIn)){
				jumbo.showMsg("最低金额不能大于最高金额！");
				return;
			}
			if($j("#isInsurance").attr("checked")){
				postData["bcsap.isInsurance"] = 1;
				postData["bcsae.isInsurance"] = 1;
			}else{
				postData["bcsap.isInsurance"] = 0;	
				postData["bcsae.isInsurance"] = 0;	
			}
			if($j("#isSum").attr("checked")){
				postData["bcsae.isNotDisplaySum"] = true;
			}else{
				postData["bcsae.isNotDisplaySum"] = false;	
			}
			if($j("#isPrintMaCaoHGD").attr("checked")){
				postData["biChannel.isPrintMaCaoHGD"] = true;
			}else{
				postData["biChannel.isPrintMaCaoHGD"] = false;	
			}
			postData["biChannel.moneyLmit"] =  $j("#moneyLmit").val();
			if($j("#cid").val() == ""){
				//新增
				//验证渠道编号和名称是否存在
				var checkCodeName = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/checkBiChannelByCodeOrName.json",postData);
				if(checkCodeName["msg"] == "DATA"){
						//有数据
						jumbo.showMsg("渠道名称已存在！");
						$j("#cName").focus();
	 					return;
				};
				if(checkCodeName["msg"] == "NODATA"){
					if(confirm("确定保存新建的内容？")){
						
						var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/addBiChannel.json",postData);
						if(rs && rs["msg"] == 'success'){
								jumbo.showMsg("渠道信息新建成功！");
									$j("#detail").addClass("hidden");
									$j("#bichannel-list").removeClass("hidden");
									$j("#tbl-bichannel-list").jqGrid('setGridParam',{
										url:window.$j("body").attr("contextpath")+"/findBiChannelList.json",
										page:1
							}).trigger("reloadGrid");
						}else{
							jumbo.showMsg("渠道信息新建失败！");
						}
					}			
				}
			}else{
				//修改
				postData["biChannel.id"] =  $j("#cid").val();
				postData["bcsap.id"] =  $j("#pid").val();
				postData["bcsae.id"] =  $j("#eid").val();
				//验证渠道编号和名称是否存在
				var checkName = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/checkBiChannelByCodeOrName.json",postData);
				if(checkName["msg"] == "DATA"){
						//有数据
						jumbo.showMsg("渠道名称已存在！");
						$j("#cName").focus();
	 					return;
				}
				if(checkName["msg"] == "NODATA"){
					if(confirm("确定保存已修改的内容？")){
						var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/updateBiChannel.json",postData);
						if(result && result["msg"] == 'success'){
								jumbo.showMsg("渠道信息修改成功！");
									$j("#detail").addClass("hidden");
									$j("#bichannel-list").removeClass("hidden");
									$j("#tbl-bichannel-list").jqGrid('setGridParam',{
										url:window.$j("body").attr("contextpath")+"/findBiChannelList.json",
										page:1
							}).trigger("reloadGrid");
						}else{
							jumbo.showMsg("渠道信息修改失败！");
						}
					}			
				}
			}
		});
		//新增渠道
		$j("#addChannel").click(function(){
			$j('#detailForm')[0].reset();
			$j("#bichannel-list").addClass("hidden");
			$j("#detail").removeClass("hidden");
			$j("#cCode").text("渠道编码系统自动生成");
			$j("#cuIdErroe").html("");
		});
		//新增渠道原因
		$j("#addImperfect").click(function(){
			$j("#tbl-bichannel-imperfect-list").addClass("hidden");
			$j("#tbl-bichannel-imperfect-add").removeClass("hidden");
			$j("#addImperfect").addClass("hidden");
			$j("#imperfectSave").removeClass("hidden");
			$j("#imperfect").hide();
			$j("#save").hide();
		});
		$j("#addImperfectLine").click(function(){
			$j("#tbl-bichannel-imperfectLine-add").removeClass("hidden");
			$j("#addImperfectLine").addClass("hidden");
			$j("#imperfectLine").hide();
			$j("#imperfectLineSave").removeClass("hidden");
			
		});
		
		//保存残次原因
		$j("#imperfectSave").click(function(){
			if($j("#imperfectCode").val() == ""){
				jumbo.showMsg("渠道原因编码必须填写！");
					return;
		}
			if($j("#imperfectName").val() == ""){
				jumbo.showMsg("渠道原因名称必须填写！");
					return;
		}
			var postData = {};
			postData["biChannelImperfect.channelId.id"] =  $j("#cid").val();
			postData["biChannelImperfect.code"] =  $j("#imperfectCode").val();
			var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/addImperfect.json",postData);
			if(rs && rs["msg"] == 'success'){
					jumbo.showMsg("渠道信息新建成功！");
						$j("#detail").addClass("hidden");
						$j("#bichannel-list").removeClass("hidden");
						$j("#tbl-bichannel-list").jqGrid('setGridParam',{
							url:window.$j("body").attr("contextpath")+"/findBiChannelList.json",
							page:1
				}).trigger("reloadGrid");
			}else{
				jumbo.showMsg("渠道信息新建失败！");
			}
		});
		//保存残次类型
		$j("#imperfectLineSave").click(function(){
			if($j("#imperfectLineCode").val() == ""){
				jumbo.showMsg("渠道类型编码必须填写！");
					return;
		}
			
			var postData = {};
			alert($j("#imperfectLineId").val());
			postData["biChannelImperfectLine.imperfectId.id"] =  $j("#imperfectId").val();
			postData["biChannelImperfectLine.code"] =  $j("#imperfectLineCode").val();
			var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/addImperfectLine.json",postData);
			if(rs && rs["msg"] == 'success'){
					jumbo.showMsg("渠道信息新建成功！");
						$j("#detail").addClass("hidden");
						$j("#bichannel-list").removeClass("hidden");
						$j("#tbl-bichannel-list").jqGrid('setGridParam',{
							url:window.$j("body").attr("contextpath")+"/findBiChannelList.json",
							page:1
				}).trigger("reloadGrid");
			}else{
				jumbo.showMsg("渠道信息新建失败！");
			}
		});
		$j("#lineback").click(function(){
			$j("#imperfect").removeClass("hidden");
			$j("#imperfect-add").removeClass("hidden");
			$j("#imperfectLine").addClass("hidden");
			$j("#imperfectLineadd").addClass("hidden");
			$j("#lineback").addClass("hidden");
			$j("#save").removeClass("hidden");
			$j("#back").removeClass("hidden");
		});
			//客户选中事件
		$j("#cmId").change(function(){
				if($j("#cmId").val() != $j("#cuId").val()){
					//如果该渠道绑定过仓库，就先要删除对应的绑定仓库
					$j("#cuIdErroe").html("修改渠道对应客户，渠道和客户下仓库绑定信息将一并取消，需要在渠道仓库管理中重新设置！");
				}else{
					$j("#cuIdErroe").html("");
				}
		}); 
		$j("#import1").click(function(){
			var icId = $j("#cid").val();
			if(!/^.*\.xls$/.test($j("#file").val())){
				jumbo.showMsg("请选择正确的Excel导入文件");
				return;
			}
			$j("#detailForm").attr("action",
					loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importImperfect.do?icId="+icId));
			loxia.submitForm("detailForm");
		});
		$j("#importwhy").click(function(){
			var imperfectId = $j("#imperfectId").val();
			if(!/^.*\.xls$/.test($j("#filewhy").val())){
				jumbo.showMsg("请选择正确的Excel导入文件");
				return;
			}
			$j("#detailForm").attr("action",
					loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importImperfectWhy.do?imperfectId="+imperfectId));
			loxia.submitForm("detailForm");
		});
		
		//获取所有仓库
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/warehouse/findAllWarehouse.do");
		$j("#wareHouse").append("<option value='0'>请选择</option>");
		for(var i=0;i<result.length;i++){
			$j("#wareHouse").append("<option value="+result[i].ouId+">"+result[i].ouName+"</option>");
		};
		//导出该仓库下的数据
		$j("#exprotsfyun").click(function(){
			var ouId=$j("#wareHouse").val();
			var cid=$j("#cid").val();
			if(ouId=='0'){
				jumbo.showMsg("请选择仓库");return;
			}
			var url = $j("body").attr("contextpath") + "/warehouse/exportsfconfig.do?ouId="+ouId+"&cId="+cid;
			$j("#exportFrame").attr("src",url);
		});
		//导入该仓库下的数据
		$j("#importsfyun").click(function(){
			if(!/^.*\.xls$/.test($j("#fileSFT").val())){
				jumbo.showMsg("请选择正确的Excel导入文件");
				return false;
			}
			loxia.lockPage();
			var ouId=$j("#wareHouse").val();
			var cid=$j("#cid").val();
			if(ouId=='0'){
				jumbo.showMsg("请选择仓库");return;
			}
			if(confirm("该操作将清除当前维护的区域时效信息，确认操作？")){
				$j("#detailForm").attr("action",
						loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importsfconfig.do?ouId="+ouId+"&cId="+cid)
				);
				loxia.submitForm("detailForm");	
			}
		});
		//查询sf时效
		$j("#querysfyun").click(function(){
			var url=null;
			var ouId=$j("#wareHouse").val();
			var cid=$j("#cid").val();
			if(ouId=='0'){
				url=window.$j("body").attr("contextpath")+"/findSfTimeList.json?channelId="+cid;
			}else{
				url=window.$j("body").attr("contextpath")+"/findSfTimeList.json?channelId="+cid+"&whouid="+ouId;
			}
			$j("#sf_yun_config_list").jqGrid('setGridParam',{
				url:url,
				//postData:postData,
				page:1
			}).trigger("reloadGrid");
		});
		//澳门件打印海关单金额设置
	    $j("#isPrintMaCaoHGD").bind("click",function(){
			if(($j("#isPrintMaCaoHGD").attr("checked"))){
				$j("#setMoneyLmit").removeClass("hidden");
			}else{
				$j("#setMoneyLmit").addClass("hidden");
			};
		});
	});
	
	



