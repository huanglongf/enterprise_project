$j.extend(loxia.regional['zh-CN'],{
	LOCATION_SELECT : "请选择需要绑定的库位！",
	TYPE_SELECT : "请选择需要绑定的作业类型！",
	SURE_MANIPULATE : "您确定要对当前选中的库位的作业类型进行操作吗？",
	SURE_DISTRICT : "您确定要对当前库区的作业类型进行操作吗？",
	BAND_SUCCESS : "绑定作业类型成功！",
	CANCEL_BAND_SUCCESS : "取消绑定作业类型成功！",
	
	TRANSATION_TYPE_LIST : "已绑定的作业类型数量",
	MANUAL_CODE : "手工编制库位编码",
	SYSCOMPILE_CODE : "系统编制库位编码",
	BARCODE : "库位条码",
	ISAVAILABLE : "库位使用状态",
	MEMO : "备注",
	LOCATION_LIST : "库位列表（操作员注意：没有完成绑定的库位将无法执行任何库内操作）",
	
	//colNames: ["ID",],
	//colNames: ["ID","","","","","","",""],
	CODE : "作业类型编码",
	NAME : "作业类型名称",
	DESCRIPTION : "描述",
	INTDIRECTION : "作业方向",
	INTCONTROL : "计划与执行控制",
	ISINCOST : "参与成本计算",
	ISAVAILABLE : "使用状态",
	TYPE_LIST : "作业类型列表",
	
	TYPE_CODE : "作业类型编码",
	TYPE_NAME : "作业类型名称",
	STATUS : "使用状态",
	ABLE : "使用中",
	UNABLE : "已禁用"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

function openDistrict(district, focus) {
	var container = $j("#district-container").tabs();
	container.tabs("add",window.parent.$j("body").attr("contextpath")+"/warehouse/locationstransentry.do?district.id=" + district.id, district.code);		
	var idx = container.find("li").length - 1;
	
	container.find("li:eq(" + idx + ")").attr("district", district.id);
	if(!!focus)
		container.tabs("select", idx);
};

function openDistricts(districts){
	if($j.isArray(districts)){
		for(var i=0,d;(d=districts[i]);i++)
			openDistrict(d);
	}else
		openDistrict(districts);
}

function showtranstypelist(obj){
	var pos = $j(obj).offset(),
		w = $j(obj).width(),
		$ttsum = $j("#div-tt-sum"),
		currLoc = $j(obj).parents("tr").find("td:eq(1)").text();
	var position={
				position: "absolute",
				left: pos.left + w + 5,
				top: pos.top,
				zIndex: 9999};
	if(currLoc != $ttsum.attr("loc")){
		resetDiv(obj,position);
	}else{
		if($ttsum.hasClass("hidden") || currLoc != $ttsum.attr("loc")){	
			$ttsum.removeClass("hidden").attr("loc",currLoc).css(position);
			setTimeout(function(){$ttsum.addClass("hidden").css({
				left: "inherit",
				top: "inherit"
			}).attr("loc","");},3000);
		}else{
			$ttsum.addClass("hidden").css({
				left: "inherit",
				top: "inherit"
			}).attr("loc","");
		}
	}
}
function getIds(){
	var ids={};
	var tabIndex=$j("#tabIndex").val();
	var locationIds = $j("#table-"+tabIndex).jqGrid('getGridParam','selarrrow');
	var transIds = $j("#tbl-transtypelist").jqGrid('getGridParam','selarrrow');
	if(locationIds==""){
		jumbo.showMsg(loxia.getLocaleMsg("LOCATION_SELECT"));//请选择需要绑定的库位！
		return false;
	}else{
		for(var i in locationIds){
			ids["locationIds["+i+"]"]=locationIds[i];
		}
	}
	if(transIds==""){
		jumbo.showMsg(loxia.getLocaleMsg("TYPE_SELECT"));//请选择需要绑定的作业类型！
		return false;
	}else{
		for(var i in transIds){
			ids["transIds["+i+"]"]=transIds[i];
		}
	}
	return ids;
}

function getTransIds(){
	var transIds = $j("#tbl-transtypelist").jqGrid('getGridParam','selarrrow');
	if(transIds==""){
		jumbo.showMsg(loxia.getLocaleMsg("TYPE_SELECT"));//请选择需要绑定的作业类型！
		return false;
	}else{
		var result = {};
		for(var i in transIds){
			result["transIds["+i+"]"]=transIds[i];
		}
		return result;
	}
}

function resetDiv(a,position){
	var id=$j(a).parents("tr").attr("id");
	loxia.asyncXhrPost($j("body").attr("contextpath") + "/locationtransdetail.json",{"district.id":id},{
			success:function (data) {
					var html=[];
					html.push('<div id="div-tt-sum" >');
					html.push('<table cellpadding="0" cellspacing="0">');
					html.push('<thead>');
					html.push('<tr>');
//					html.push('<th>作业类型编码</th>');
//					html.push('<th>作业类型名称</th>');
//					html.push('<th>使用状态</th>');
					html.push('<th>'+i18("TYPE_CODE")+'</th>');
					html.push('<th>'+i18("TYPE_NAME")+'</th>');
					html.push('<th>'+i18("STATUS")+'</th>');
					html.push('</tr>');
					html.push('</thead>');
					html.push('<tbody>');
					$j.each(data,function(i,item){
						html.push('<tr>');
						html.push('<td>'+item.code+'</td>');
						html.push('<td>'+item.name+'</td>');
						html.push('<td>'+(item.isAvailable?i18("ABLE"):i18("UNABLE"))+'</td>');//"使用中":"已禁用"
						html.push('</tr>');
					});
					html.push('</tbody></table></div>');
					$j("#div-tt-sum").replaceWith(html.join(""));
					$j("#div-tt-sum").attr("loc",id).css(position);
					setTimeout(function(){$j("#div-tt-sum").addClass("hidden").css({
				left: "inherit",
				top: "inherit"
			}).attr("loc","");},3000);
			   }
			});
}
$j(document).ready(function (){
	var $tabs = $j("#district-container").tabs();	
	var control=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"whTransactionControl"});
	var direction=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"whDirectionMode"});
	var status=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"status"});
	var trueOrFalse=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"trueOrFalse"});
	if(!control.exception&&!direction.exception&&!trueOrFalse.exception&&!status.exception){
		$tabs.tabs({
			cache: false,
			load: function( event, ui ) {		
				$j.each($tabs.find("li:not(.ui-state-active) a"),function(i,e){
					$j($j(this).attr("href")).addClass("ui-tabs-hide");
				});
				$j("#districtId").val($j("#disId",ui.panel).val());
				$j("table",ui.panel).attr("id","table-" + ui.index);
				$j("#pager").append('<div id="pager-'+ui.index+'"></div>');
				loxia.initContext($j(ui.panel));
				$j("#table-"+ui.index).jqGrid({
					url:$j("body").attr("contextpath")+"/locationtranslist.json",
					datatype: "json",
					//colNames: ["ID","已绑定的作业类型数量","手工编制库位编码","系统编制库位编码","库位条码","库位使用状态","备注"],
					colNames: ["ID",i18("TRANSATION_TYPE_LIST"),i18("MANUAL_CODE"),i18("SYSCOMPILE_CODE"),i18("BARCODE"),i18("ISAVAILABLE"),i18("MEMO")],
					colModel: [{name: "id", index: "l.id", hidden: true},
					           {name: "transactionTypesCount", index: "transaction_Types_Count", formatter:"linkFmatter", formatoptions:{onclick:"showtranstypelist"}, width: 160, resizable: true},
					           {name: "manualCode", index: "l.manual_Code", width: 120, resizable: true},
					           {name: "sysCompileCode", index: "l.sys_Compile_Code", width: 120, resizable: true},
							   {name: "barCode", index: "l.barcode", width: 120, resizable: true},						 
							   {name: "isAvailable", index: "l.is_Available", width: 120, resizable: true,
								   formatter:'select',editoptions:status},
							   {name: "memo", index: "l.memo", width: 120, resizable: true}
							   ],
					caption: i18("LOCATION_LIST"),//库位列表（操作员注意：没有完成绑定的库位将无法执行任何库内操作）
					multiselect: true,
					rowNum:jumbo.getPageSize(),
					rowList:jumbo.getPageSizeList(),
					sortname: 'code',
					height:jumbo.getHeight(),
					sortorder: "asc", 
					pager:"#pager-"+ui.index,
					//rowNum:-1,
					postData:{"district.id":$j("#disId",ui.panel).val(),"columns":"id,transactionTypesCount,manualCode,sysCompileCode,barCode,isAvailable,memo"},
					gridComplete:function(){
						//alert($j("#locationId").val());
						/*var districtId = $j("#districtId").val();
						var locId = $j("#locationId").val();
						var tabIndex = $j("#tabIndex").val();
						if(districtId&&locId){
							var url = $j("body").attr("contextpath")+"/locationtranslist.json";
							$j("#table-"+tabIndex).jqGrid('setGridParam',{url:url,
								postData:{"district.id":districtId,"locationId":locId}}).trigger("reloadGrid",[{page:1}]);
							$j("#locationId").val("");
						}*/
					},
					jsonReader: { repeatitems : false, id: "0" }
				});
				//
				
				$j("#search", ui.panel).click(function(){
					var locCode = $j.trim($j("#locationCode",ui.panel).val());
					if("" == locCode){
						jumbo.showMsg("库位编码不能为空！");
						return;
					}
					var rs = loxia.syncXhrPost($j("body").attr("contextpath")+ "/getlocationbyloccode.json", {"locationCode" : locCode});
					if(rs){
						if(rs.result == "success"){
							$j("#locationId").val("");
							var location = rs.location;
							if(location){
								var locationId  = location.id;
								var district = location.district;
								if(district){
									var districtId = district.id;
									if(districtId){
										//var li = $j("li", ui.panel).find('[district="5879"]');
										var li = $j("#district-container").find("li[district='"+ districtId +"']");
										if(li.length == 1){
											$j("#locId", ui.panel).val(locationId);
											$j("#locationId").val(locationId);
											$j(li).find("a:first").click();
											$j("#district-container").find("li").removeClass("ui-tabs-selected ui-state-active");
											$j(li).addClass("ui-tabs-selected ui-state-active");
											var locId = locationId;
											if(locId){
												var url = $j("body").attr("contextpath")+"/locationtranslist.json";
												$j("#table-"+ui.index).jqGrid('setGridParam',{url:url,
													postData:{"district.id":districtId,"locationId":locId}}).trigger("reloadGrid",[{page:1}]);
											}else{
												$j("#tbl-transtypelist").jqGrid("resetSelection");
											}
										}
										$j("#locationCode",ui.panel).val("");
									}else{
										$j("#locationId").val("");
										jumbo.showMsg("库位对应的库区不存在！");
									}
								}
							}
						}else {
							$j("#locationId").val("");
							jumbo.showMsg("库位不存在或已被锁！");
						}
					 }
				});
			},
			select:function(event, ui){
				$j("#tabIndex").val(ui.index);
				$j("#tbl-transtypelist").jqGrid("resetSelection");
				$j("#districtId").val($j("#disId",ui.panel).val());
				//$j("#locationId").val("");
				var locationId = $j("#locationId").val();
				if("" != locationId){
					event.preventDefault();
					$j("#locationId").val("");
				}
			}
		});
		
		
		
		$j("#tbl-transtypelist").jqGrid({
			url:$j("body").attr("contextpath")+"/translist.json",
			datatype: "json",
			//colNames: ["ID","作业类型编码","作业类型名称","描述","作业方向","计划与执行控制","参与成本计算","使用状态"],
			colNames: ["ID",i18("CODE"),i18("NAME"),i18("DESCRIPTION"),i18("INTDIRECTION"),i18("INTCONTROL"),i18("ISINCOST"),i18("ISAVAILABLE")],
			colModel: [{name: "id", index: "t.id", hidden: true},
			           {name: "code", index: "t.code", width: 120, resizable: true},
			           {name: "name", index: "t.name", width: 120, resizable: true},
			           {name: "description", index: "t.description", width: 180, resizable: true},
					   {name: "intDirection", index: "t.direction", width: 60, resizable: true,
								   formatter:'select',editoptions:direction},
					   {name: "intControl", index: "t.control", width: 160, resizable: true,
								   formatter:'select',editoptions:control},
					   {name: "isInCost", index: "t.is_InCost", width: 80, resizable: true,
								   formatter:'select',editoptions:trueOrFalse},
					   {name: "isAvailable", index: "t.is_Available", width: 60, resizable: true,
								   formatter:'select',editoptions:status}
					   ],
			caption: i18("TYPE_LIST"),//作业类型列表
	   	sortname: 't.name',
		sortorder: "asc", 
		height:"auto",
		multiselect: true,
		rowNum:-1,
		jsonReader: { repeatitems : false, id: "0" }
		});	
	}else{
		jumbo.showMsg(loxia.getLocaleMsg("ERROR_INIT"));//初始化'系统参数'数据异常！
	}
			
	
	loxia.asyncXhrGet($j("body").attr("contextpath") + "/districtlist.json",{},{
		success:function (data) {
			openDistricts(data);
		   }
		});
	$j("#bind").click(function(){
		var ids=getIds();
		if(ids&&window.confirm(loxia.getLocaleMsg("SURE_MANIPULATE"))){//您确定要对当前选中的库位的作业类型进行操作吗？
			loxia.lockPage();
			loxia.asyncXhrPost($j("body").attr("contextpath") + "/bindtrans.json",ids,{
			success:function (data) {
				//$tabs.tabs("load",$tabs.tabs('option', 'selected'));
				var districtId = $j("#districtId").val();
				var locId = $j("#locationId").val();
				var tabIndex = $j("#tabIndex").val();
				if(districtId&&locId){
					var url = $j("body").attr("contextpath")+"/locationtranslist.json";
					$j("#table-"+tabIndex).jqGrid('setGridParam',{url:url,
						postData:{"district.id":districtId,"locationId":locId}}).trigger("reloadGrid",[{page:1}]);
				} else {
					$tabs.tabs("load",$tabs.tabs('option', 'selected'));
				}
				loxia.unlockPage();
				jumbo.showMsg(loxia.getLocaleMsg("BAND_SUCCESS"));//绑定作业类型成功！
				$j("#tbl-transtypelist").jqGrid("resetSelection"); 
			   }
			});
		}
	});
	$j("#unbind").click(function(){
		var ids=getIds();
		if(ids&&window.confirm(loxia.getLocaleMsg("SURE_MANIPULATE"))){//您确定要对当前选中的库位的作业类型进行操作吗？
			loxia.lockPage();
			loxia.asyncXhrPost($j("body").attr("contextpath") + "/unbindtrans.json",ids,{
			success:function (data) {
				//$tabs.tabs("load",$tabs.tabs('option', 'selected'));
				var districtId = $j("#districtId").val();
				var locId = $j("#locationId").val();
				var tabIndex = $j("#tabIndex").val();
				if(districtId&&locId){
					var url = $j("body").attr("contextpath")+"/locationtranslist.json";
					$j("#table-"+tabIndex).jqGrid('setGridParam',{url:url,
						postData:{"district.id":districtId,"locationId":locId}}).trigger("reloadGrid",[{page:1}]);
				} else {
					$tabs.tabs("load",$tabs.tabs('option', 'selected'));
				}
				loxia.unlockPage();
				jumbo.showMsg(loxia.getLocaleMsg("CANCEL_BAND_SUCCESS"));//取消绑定作业类型成功！
				$j("#tbl-transtypelist").jqGrid("resetSelection"); 
			   }
			});
		}
	});
	
	$j("#bindDistrict").click(function(){
		var postData = getTransIds();
		if(postData&&window.confirm(loxia.getLocaleMsg("SURE_DISTRICT"))){//您确定要对当前库区的作业类型进行操作吗？
			loxia.lockPage();
			postData['districtId']=$j("#districtId").val();
			loxia.asyncXhrPost($j("body").attr("contextpath") + "/binddistrict.json",postData,{
			success:function (data) {
				$tabs.tabs("load",$tabs.tabs('option', 'selected'));
				loxia.unlockPage();
				jumbo.showMsg(loxia.getLocaleMsg("BAND_SUCCESS"));//绑定作业类型成功！
				$j("#tbl-transtypelist").jqGrid("resetSelection"); 
			   }
			});
		}
	});
	$j("#unbindDistrict").click(function(){
		var postData = getTransIds();
		if(postData&&window.confirm(loxia.getLocaleMsg("SURE_DISTRICT"))){//您确定要对当前库区的作业类型进行操作吗？
			loxia.lockPage();
			postData['districtId']=$j("#districtId").val();
			loxia.asyncXhrPost($j("body").attr("contextpath") + "/unbinddistrict.json",postData,{
			success:function (data) {
				$tabs.tabs("load",$tabs.tabs('option', 'selected'));
				loxia.unlockPage();
				jumbo.showMsg(loxia.getLocaleMsg("CANCEL_BAND_SUCCESS"));//取消绑定作业类型成功！
				$j("#tbl-transtypelist").jqGrid("resetSelection"); 
			   }
			});
		}
	});
});
