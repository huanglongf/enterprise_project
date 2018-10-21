$j.extend(loxia.regional['zh-CN'],{
	WAREHOUSETYPE : "类型",
	WAREHOUSESTATUS : "状态",
	WAREHOUSEDESC : "描述",
	ROLELIST : "角色列表",
	CORRECT_FILE_PLEASE : "请选择正确的Excel导入文件",
	PROVINCE : "省",
	CITY : "市",
	DISTRICT : "区",
	PRIORITY : "优先级",
	CREATORNAME : "创建人",
	CREATETIME : "创建时间",
	OPERATOR : "操作",
	DELETE : "删除",
	OWNER : "店铺"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

$j(document).ready(function (){
	$j("#dialog_addWeight").dialog({title: "编辑规则", modal:true, autoOpen: false, width: 500, height: 260});
	initShopQuery("companyshop","innerShopCode");
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	jumbo.loadShopList("companyshop");
	// warehouse
	$j("#tabs").tabs({
		 select: function(event, ui) {
			 var panelId = ui.panel.id;
			 if(panelId == "tabs_6"||panelId=="tabs_9"||panelId=="tabs_10"||panelId=="tabs_11"||panelId=="tabs_14"){
				 $j("#saveArea").addClass("hidden");
			 }else{
				 $j("#saveArea").removeClass("hidden");
			 }
		  }
	});
	var baseUrl = $j("body").attr("contextpath");
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/optionvaluelist.json?categoryCode=system&optionKey=TRANS_DEPARTURE");
	if(result){
		for(var i in result){
			$j("<option value='"+result[i].optionValue+"'>"+result[i].optionValue+"</option>").appendTo($j("#departure"));
		}
	}
	
	var result = loxia.syncXhrPost(loxia.getTimeUrl(window.$j("body").attr("contextpath")+"/findPlatformList.json"));
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#expCode"));
	}
	
	var result = loxia.syncXhrPost(loxia.getTimeUrl(window.$j("body").attr("contextpath")+"/findPlatformList.json"));
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#addexpCode"));
	}
	
	$j("#departure").val($j("#departure").attr("vl"));
	
	initSelectableTransportator(); 
	initSelectedTransportator();
	initAutoPl();
//	alert(biWarehouse["msg"]);
//	$j.get(baseUrl+"/getBiWarehousestatus.do", function(data){alert("Data Loaded: " + data)});
//	alert(biWarehouse);
	$j("#tbl-bichannel-imperfect-list").jqGrid({
		url:baseUrl + "/findBiChanneImperfectlList.json",
		datatype: "json",
		colNames: ["ID","残次类型编码","残次类型描述","仓库名称"],
		colModel: [
							{name: "id", hidden: true},
							{name: "code",index:"code",width:280},
							{name:"name",index:"name", width:280,formatter:"linkFmatter",formatoptions:{onclick:"showImperfect"}, resizable:true},
							{name: "channelName",index:"channelName",width:280 }
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
							{name:"name",index:"name", width:280, resizable:true},
							{name: "imperfectName",index:"imperfectName",width:280 }
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
$j("#warehouse_status_list").jqGrid({
		url:$j("body").attr("contextpath") + "/getWarehousestatus.json",
		datatype: "json",
		colNames: ["ID",i18("WAREHOUSETYPE"),i18("WAREHOUSESTATUS"),"","",""],
//		colNames: ["ID",i18("WAREHOUSETYPE")],
		colModel: [ 
						   {name: "id", index: "id", hidden: true},
//		          		 {name: "outputCount", index: "outputCount"}
				           {name: "typeName", index: "typeName",width: 265, resizable: true},
				           {name: "statusName", index: "statusName",width: 265, resizable: true},         
				           {name: "type", index: "createTime", hidden: true},
				           {name:"status",index:"status",hidden: true},
				           {name:"sort",index:"sort",hidden: true}
		           		],
		caption: i18("ROLELIST"),//角色列表
	   	sortname: 'id',
		pager:"#pager",
//	    rowNum: jumbo.getPageSize(),
//		rowList:jumbo.getPageSizeList(),
	   	height:"auto",
	   	width:600,
	   	shrinkToFit:false,
	    multiselect: true,
	   	rownumbers:true,
	    viewrecords: true,
		sortorder: "asc",
		jsonReader: {repeatitems : false, id: "0"},
		gridComplete:function(){
			//给CHECKBOX勾选
			var biWarehouse=loxia.syncXhrPost($j("body").attr("contextpath") + "/getBiWarehouseStatus.json");
			if(biWarehouse["msg"] !=""){
				var biValues= new Array();
				biValues = biWarehouse["msg"].toString().substring(0,biWarehouse["msg"].toString().length-1).split(",");
	//				alert(biWarehouse["msg"]);
				var ids = $j("#warehouse_status_list").jqGrid('getDataIDs');
				for(var i=0;i < ids.length;i++){
					var datas = $j("#warehouse_status_list").jqGrid('getRowData',ids[i]);
					var curChk = $j("#"+ids[i]+"").find(":checkbox");
					var tra = "";
					for(var j=0;j < biValues.length;j++){
						if(datas["status"] == biValues[j]){
							 curChk.attr('name', 'checkboxname');//给每一个checkbox赋名字
							 curChk.attr('value', datas['status']);   //给checkbox赋值 --状态
			                 curChk.attr('title', datas['type']);   //给checkbox赋予额外的属性值 --类型
			                 curChk.attr('values', datas['sort']);   //给checkbox赋予额外的属性值 --顺序
							 curChk.attr('checked', 'true');
						}else{
							 curChk.attr('name', 'checkboxname');
							 curChk.attr('value', datas['status']);
			                 curChk.attr('title', datas['type']);
			                 curChk.attr('values', datas['sort']); 
						}
					}
				}
//				if(datas["isSn"]=='true'){
//					curChk.attr('checked', 'false');//选中对应的checkbox
////					 $("#warehouse_status_list").find("input[id='" + ids[i] +"']").attr("checked", true);
////					alert(datas["isSn"]);
////					$j("#warehouse_status_list tr td[aria-describedby$='isSn']").html("<input type = 'checkbox' checkbox = 'checkbox'/>");
////					$j("#isSn").append("<input type = 'checkbox' checkbox = 'checkbox'/>");
////					tra= "<input type = 'checkbox'  checkbox = 'checkbox'/>";
//				} else{
//					 curChk.attr('checked', 'true');
//				}
//				$j("#warehouse_status_list").jqGrid('setRowData',ids[i],{"isSn":tra});
			}
		}
	});

$j("#warehouse-autopl-list").jqGrid({
			url:$j("body").attr("contextpath") + "/findAutoPickingListRoleList.json",
			datatype: "json",
			colNames: ["ID","编码","名称","创建时间","状态","选择"],
			colModel: [
								{name:"id",index:"id",hidden: true},
								{name:"code",index:"code", width:200,resizable:true},
								{name:"name",index:"name",width:200,resizable:true},
								{name:"createTime",index:"createTime",width:130,resizable:true},
								{name:"roleStatus",index:"roleStatus",width:50,resizable:true},
								{name:"isAuto",width:50,formatter:function(v,x,r){return "<input type='radio' name = 'isAutoRadio' />";}}
							],
			caption: "规则选择",
		   	sortname: 'id',
			sortorder: "asc",
			width:690,
		   	shrinkToFit:false,
			height:"auto",
//			multiselect: true,
			rowNum:-1,
			viewrecords: true,
		   	rownumbers:true,
			jsonReader: {repeatitems : false, id: "0"},
			gridComplete:function(){
			//给CHECKBOX勾选
			var autopl = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getAotoPlConfig.json");
			if(autopl && autopl.msg){
				var idss = $j("#warehouse-autopl-list").jqGrid('getDataIDs');
				for(var i=0;i < idss.length;i++){
					var datass = $j("#warehouse-autopl-list").jqGrid('getRowData',idss[i]);
						if(datass["id"] == autopl.msg['autoPlr']){
//							alert($j("#'1' input[name=isAutoRadio]").length);
							$j("#warehouse-autopl-list tr[id="+datass["id"]+"] input[name=isAutoRadio]").attr("checked","true");//选中
						}
				}
			}
		}
	});
$j("#warehouse_coverage_area_list").jqGrid({
	url:$j("body").attr("contextpath") + "/findCoverageAreaByOuId.json",
	datatype: "json",
	//colNames: ["ID","省","市","区","优先级","创建人","创建时间","操作"],
	colNames: ["ID",i18("PROVINCE"),i18("CITY"),i18("DISTRICT"),i18("PRIORITY"),i18("CREATORNAME"),i18("CREATETIME"),i18("OPERATOR")],
	colModel: [{name: "id", index: "id", hidden: true},
				{name:"province",index:"province",width:120,resizable:true},
                {name:"city",index:"city",width:120,resizable:true},
                {name:"district",index:"district",width:120,resizable:true},
                {name:"priority",index:"priority",width:100,resizable:true},
                {name:"creatorName",index:"creatorName",width:120,resizable:true},
                {name:"createTime",index:"createTime",width:150,resizable:true},
                {name:"operator",index:"operator",width:120,resizable:true}
				],
	caption: "",
   	sortname: 'wca.priority',
    multiselect: false,
	sortorder: "asc",
	pager:"#pager",
	width:"auto",
	height:"auto",
	rowNum: jumbo.getPageSize(),
	rowList:jumbo.getPageSizeList(),
	height:jumbo.getHeight(),
	viewrecords: true,
   	rownumbers:true,
	jsonReader: { repeatitems : false, id: "0" },
	gridComplete: function(){
		var btn = '<div><button type="button" style="width:100px;" name="btnExecute" loxiaType="button" onclick="deleteCoverageArea(this);">'+i18("DELETE")+'</button></div>';
		var ids = $j("#warehouse_coverage_area_list").jqGrid('getDataIDs');
		for(var i=0;i < ids.length;i++){
			$j("#warehouse_coverage_area_list").jqGrid('setRowData',ids[i],{"operator":btn});
		}
		loxia.initContext($j(this));
	}
});

$j("#sf_next_morning_config_list").jqGrid({
	url:$j("body").attr("contextpath") + "/findsfnextmorningconfigbyouid.json",
	datatype: "json",
	//colNames: ["ID","省","市","区","优先级","创建人","创建时间","操作"],
	colNames: ["ID",i18("PROVINCE"),i18("CITY"),i18("DISTRICT"),i18("CREATORNAME"),i18("CREATETIME")],
	colModel: [{name: "id", index: "id", hidden: true},
				{name:"province",index:"province",width:120,resizable:true},
                {name:"city",index:"city",width:120,resizable:true},
                {name:"district",index:"district",width:120,resizable:true},
                {name:"creatorName",index:"creatorName",width:120,resizable:true},
                {name:"createTime",index:"createTime",width:150,resizable:true}				],
	caption: "SF次晨达业务支持区域",
   	sortname: 'id',
    multiselect: false,
	sortorder: "asc",
	pager:"#pagerSFC",
	width:"auto",
	height:"auto",
	rowNum: jumbo.getPageSize(),
	rowList:jumbo.getPageSizeList(),
	height:jumbo.getHeight(),
	viewrecords: true,
   	rownumbers:true,
	jsonReader: { repeatitems : false, id: "0" }
});

$j("#nike_today_send_config_list").jqGrid({
	url:$j("body").attr("contextpath") + "/findNIKETodaySendConfigByOuId.json",
	datatype: "json",
	//colNames: ["ID","省","市","区""创建时间","店铺"],
	colNames: ["ID",i18("升级金额"),i18("PROVINCE"),i18("CITY"),i18("DISTRICT"),i18("CREATETIME"),i18("OWNER"),"时间段1","时间段2"],
	colModel: [{name: "id", index: "id", hidden: true},
	           {name:"totalActual",index:"totalActual",width:120,resizable:true},
	           {name:"province",index:"province",width:120,resizable:true},
	           {name:"city",index:"city",width:120,resizable:true},
	           {name:"district",index:"district",width:120,resizable:true},
	           {name:"createTime",index:"createTime",width:150,resizable:true},	
	           {name:"owner",index:"owner",width:150,resizable:true},	
	           {name:"startTime",index:"startTime",width:100,resizable:true},	
	           {name:"endTime",index:"endTime",width:100,resizable:true}	
	           ],
	           caption: "NIKE当日达支持区域",
	           sortname: 'id',
	           multiselect: false,
	           sortorder: "asc",
	           pager:"#pagerNIKE",
	           width:"auto",
	           height:"auto",
	           rowNum: jumbo.getPageSize(),
	           rowList:jumbo.getPageSizeList(),
	           height:jumbo.getHeight(),
	           viewrecords: true,
	           rownumbers:true,
	           jsonReader: { repeatitems : false, id: "0" }
});


$j("#ad_list").jqGrid({
	url:$j("body").attr("contextpath") + "/findAdPackageByOuIdPage.json",
	datatype: "json",
	colNames: ["ID","异常工单类型名称","异常处理结果","创建人","创建时间"],
	colModel: [{name: "id", index: "id", hidden: true},
	           {name:"adName",index:"adName",width:120,resizable:true},
	           {name:"adResult",index:"adResult",width:120,resizable:true},
	           {name:"createPerson",index:"createPerson",width:120,resizable:true},
	           {name:"createTime",index:"createTime",width:120,resizable:true}
	           ],
	           caption: "AD异常工单类型配置",
	           sortname: 'id',
	           multiselect: false,
	           sortorder: "asc",
	           pager:"#pagerAd",
	           width:"auto",
	           height:"auto",
	           rowNum: jumbo.getPageSize(),
	           rowList:jumbo.getPageSizeList(),
	           height:jumbo.getHeight(),
	           viewrecords: true,
	           rownumbers:true,
	           jsonReader: { repeatitems : false, id: "0" }
});



$j("#check_lpCode_package_info").jqGrid({
	url:$j("body").attr("contextpath") + "/findTransportatorListByWeight.json",
	datatype: "json",
	//colNames: ["ID","省","市","区""创建时间","店铺"],
	colNames: ["ID","物流商","单包裹容许重量差异范围","单包裹最大重量","单包裹最小重量","操作"],
	colModel: [{name: "id", index: "id", hidden: true},
	           {name:"expCode",index:"province",width:100,resizable:true},
	           {name:"weightDifferencePercent",index:"city",width:100,resizable:true},
	           {name:"maxWeight",index:"district",width:100,resizable:true},
	           {name:"minWeight",index:"minWeight",width:100,resizable:true},
			   {name:"idForBtn",index:"idForBtn", width: 100,resizable:true,sortable: false, formatter:"buttonFmatter", formatoptions:{"buttons":{label:"编辑", onclick:"editLpCodeWeight(this)"}}}],
	           caption: "",
	           sortname: 'id',
	           multiselect: false,
	           sortorder: "asc",
	           pager:"#pagerPackageInfo",
	           width:700,
	           height:"auto",
	           viewrecords: true,
	           rownumbers:true,
	           pager:"#pagerPackageInfo",
	   		   rowNum: 10,
	   		   rowList:[10,20,40],
	           /*gridComplete: function(){
	       		var btn = '<div><button type="button" style="width:100px;" name="btnExecute1" loxiaType="button" onclick="editLpCodeWeight(obj);">编辑</button></div>';
	       		var ids = $j("#check_lpCode_package_info").jqGrid('getDataIDs');
	       		for(var i=0;i < ids.length;i++){
	       			$j("#check_lpCode_package_info").jqGrid('setRowData',ids[i],{"operator":btn});
	       		}
	       		loxia.initContext($j(this));
	       	   },*/
	           jsonReader: { repeatitems : false, id: "0" }
});

	// 保存Nike的规则
	$j("#saveNikeConfig").click(function(){
		var postData={};
		if ($j("#companyshop").val() == "") {
			jumbo.showMsg("请选择店铺!");
			return;
		}
		if($j("#totalActual").val() == ""){
			jumbo.showMsg("请填写升级金额");
			return;
		}
		if($j("#lsProvince").val() == ""){
			jumbo.showMsg("请填写省");
			return;
		}
		if($j("#lsCity").val() == ""){
			jumbo.showMsg("请填写市");
			return;
		}
		if($j("#lsDistrict").val() == ""){
			jumbo.showMsg("请填写区");
			return;
		}
		var reg = /^(([0-1]\d)|(2[0-3])):[0-5]\d$/;
		var oneBegin = $j("#lsTimeOneBegin").val();
		if(oneBegin == ""){
			oneBegin = "00:00";
		} else {
			if (!reg.test(oneBegin)) {
				jumbo.showMsg("当天时间规则输入有误!输入示例: 17:00, 09:09");
				return;
			}
		}
		var oneEnd = $j("#lsTimeOneEnd").val();
		if(oneEnd == ""){
			oneEnd = "10:30";
		} else {
			if (!reg.test(oneEnd)) {
				jumbo.showMsg("当天时间规则输入有误!输入示例: 17:00, 09:09");
				return;
			}
		}
		var twoBegin = $j("#lsTimeTwoBegin").val();
		if(twoBegin == ""){
			twoBegin = "17:00";
		} else {
			if (!reg.test(twoBegin)) {
				jumbo.showMsg("当天时间规则输入有误!输入示例: 17:00, 09:09");
				return;
			}
		}
		var twoEnd = $j("#lsTimeTwoEnd").val();
		if(twoEnd == ""){
			twoEnd = "23:59";
		} else {
			if (!reg.test(twoEnd)) {
				jumbo.showMsg("当天时间规则输入有误!输入示例: 17:00, 09:09");
				return;
			}
		}
		var t1s = parseInt(oneBegin.split(":")[0],10);
		var t1e = parseInt(oneEnd.split(":")[0],10);
		var t2s = parseInt(twoBegin.split(":")[0],10);
		var t2e = parseInt(twoEnd.split(":")[0],10);
		if (t1s > t1e || t2s > t2e || t1e > t2s) {
			jumbo.showMsg("时间规则配置有误,请检查是否符合逻辑!");
			return;
		}
		postData['ls.startTime'] = oneBegin + "-" + oneEnd;
		postData['ls.endTime'] = twoBegin + "-" + twoEnd;
		postData['ls.owner'] = $j("#companyshop").val();
		postData['ls.province'] = $j("#lsProvince").val();
		postData['ls.totalActual'] = $j("#totalActual").val();
		postData['ls.city'] = $j("#lsCity").val();
		if ($j("#lsDistrict").val() != "") {
			postData['ls.district'] = $j("#lsDistrict").val();
		}
		var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/saveNikeConfig.json"),postData);
		if (rs && rs.result == "success") {
			jumbo.showMsg("保存成功!");
			$j("#lsProvince").val("");
			$j("#lsCity").val("");
			$j("#lsDistrict").val("");
			$j("#lsStartTime").val("");
			$j("#lsEndTime").val("");
			$j("#nike_today_send_config_list").jqGrid('setGridParam',{url:$j("body").attr("contextpath") + "/findNIKETodaySendConfigByOuId.json",page:1}).trigger("reloadGrid");
		} else {
			jumbo.showMsg(rs.msg);
		}
	});
	$j("#exprotNIKE").click(function(){
		var url = $j("body").attr("contextpath") + "/warehouse/exportNikeTodaySendConfig.do";
		$j("#exportFrame").attr("src",url);
	});
	$j("#importNIKE").click(function(event){
		event.preventDefault();
		if(!/^.*\.xls$/.test($j("#fileNIKE").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));
			return false;
		}
		var flag = confirm("该操作将清除当前维护的区域信息，确认操作？");
		if(flag){
			var file = $j("#fileNIKE");
			var coverFile = $j(file).clone();
			$j(coverFile).attr("id", "nikeFile");
			$j("#importForm").append(coverFile);
			$j("#importForm").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importNikeTodaySendConfig.do")
			);
			loxia.submitForm("importForm");
		}
	});
	
	$j("#importAd").click(function(event){
		event.preventDefault();
		if(!/^.*\.xls$/.test($j("#fileAd").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));
			return false;
		}
		var flag = confirm("该操作将清除当前维护的区域信息，确认操作？");
		if(flag){
			$j("#importForm2").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importAdPackage.do"));
			loxia.submitForm("importForm2");
		}
		
	});
	
	$j("#exprotAd").click(function(){
		var url = $j("body").attr("contextpath") + "/warehouse/exportAdPackage.do";
		$j("#exportFrame").attr("src",url);
	});
	
	$j("#exprotsfc").click(function(){
		var url = $j("body").attr("contextpath") + "/warehouse/warehousesfnextmornigconfigexport.do";
		$j("#exportFrame").attr("src",url);
	});
	$j("#importsfc").click(function(event){
		if(!/^.*\.xls$/.test($j("#fileSFC").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));
			return false;
		}
		if(confirm("该操作将清除当前维护的区域信息，确认操作？")){
			$j("#warehouseForm").attr("action",
					loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importsfnextmorningconfig.do")
			);
			loxia.submitForm("warehouseForm");	
		}
	});
	

	$j("#create").click(function(){
		$j("#addexpCode").val("");
		$j("#dialog_addWeight input").val("");
		$j("#t_ma_transportator_weigth_id").val("");
		$j("#dialog_addWeight").dialog("open");
	});
	$j("#closediv").click(function(){
		$j("#dialog_addWeight").dialog("close");
		$j("#t_ma_transportator_weigth_id").val("");
	});
	
	$j("#saveWeight").click(function(){
		var addexpCode=$j("#addexpCode").val();
		if(null==addexpCode||""==addexpCode){
			jumbo.showMsg("该物流商不存在");
			return false;
		}
		var re = /^[0-9]*[1-9][0-9]*$/ ;  
		var maxWeight=$j("#add_max_weight").val();
		var minWeight=$j("#add_min_weight").val();
		if(null!=maxWeight&&""!=maxWeight){
			if(!re.test(maxWeight)){
				jumbo.showMsg("最大重量必须为正整数");
				return false;
			}
		}
		if(null!=minWeight&&""!=minWeight){
			if(!re.test(minWeight)){
				jumbo.showMsg("最小重量必须为正整数");
				return false;
			}
		}
		if(parseFloat(maxWeight)<parseFloat(minWeight)){
				jumbo.showMsg("最小重量必须大于最大重量");
				return false;
		}
		var differenceWeight=$j("#add_weight_difference_percent").val();
		if(null!=differenceWeight&&""!=differenceWeight){
			if(!re.test(differenceWeight)){
				jumbo.showMsg("重量差异范围必须为正整数");
				return false;
			}
		}
		var ch = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/checkexpCode.json",{"expCode":addexpCode});
		if("success"==ch["result"]){
			var id=$j("#t_ma_transportator_weigth_id").val();
			if(null!=id&&""!=id){
				var postData={"expCode":addexpCode,"maxWeight":$j("#add_max_weight").val(),"minWeight":$j("#add_min_weight").val(),"weightDifferencePercent":$j("#add_weight_difference_percent").val(),"lpCodeWeigthId":$j("#t_ma_transportator_weigth_id").val()};
				loxia.asyncXhrPost(
						$j("body").attr("contextpath") + "/updateTransportatorWeigth.json",  
						postData,
						{
							success:function () {
				    			$modfiyStatusInfo("成功");
				    			$j("#dialog_addWeight").dialog("close");
				    			var url=baseUrl + "/findTransportatorListByWeight.json";
				    			$j("#check_lpCode_package_info").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm1")}).trigger("reloadGrid");
				    			jumbo.bindTableExportBtn("check_lpCode_package_info",{},url,loxia._ajaxFormToObj("queryForm1"));
						   }
						}
					);
			}else{
				var postData={"expCode":addexpCode,"maxWeight":$j("#add_max_weight").val(),"minWeight":$j("#add_min_weight").val(),"weightDifferencePercent":$j("#add_weight_difference_percent").val(),"lpCodeWeigthId":$j("#t_ma_transportator_weigth_id").val()};
				loxia.asyncXhrPost(
						$j("body").attr("contextpath") + "/saveTransportatorWeigth.json",  
						postData,
						{
							success:function () {
				    			$modfiyStatusInfo("成功");
				    			$j("#dialog_addWeight").dialog("close");
				    			var url=baseUrl + "/findTransportatorListByWeight.json";
				    			$j("#check_lpCode_package_info").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm1")}).trigger("reloadGrid");
				    			jumbo.bindTableExportBtn("check_lpCode_package_info",{},url,loxia._ajaxFormToObj("queryForm1"));
						   }
						}
					);
			}
		}else{
			jumbo.showMsg("该物流商不存在");
			return false;
		}
		
	});
	
	//保存
	$j("#WHsaveBtn").bind("click", function(event) {
			if($j("#whAddress").val() == ""){
				jumbo.showMsg("仓库地址必须填写");
				$j("#whAddress").focus();
				return;
			}
		 	//获取角色选中的值
			var checkedVals = new Array();
			var checkedTitles = new Array();
			var checkedVal = new Array();
			var statusValue = "";
			$j("#zxTime").focus();
			$j("#jgTime").focus();
//			$j("#ui-datepicker-div").style.display="none";
			document.getElementById("ui-datepicker-div").style.display = "none";
			$j(":checkbox[name=checkboxname][checked]").each(function(){
			      checkedVals.push($j(this).val());//status
			      checkedTitles.push($j(this).attr("title"));//type
			      checkedVal.push($j(this).attr("values"));//sort
			});
			for(var p=0; p<checkedVals.length; p++){
				statusValue+=checkedVals[p]+" "+checkedTitles[p]+" "+checkedVal[p]+",";
			}
		    $j("#statusName").attr("value",statusValue);
			var dep = $j("#departure").val();
			if (dep == null || dep == ''){jumbo.showMsg("请选择 [发运地]"); return;}
			var postData = loxia._ajaxFormToObj("warehouseForm");
			//var errorMsg = loxia.validateForm("warehouseForm");
			if($j("#isSfOlOrder").attr("checked")){
 				postData["warehouse.isSfOlOrder"] = true;
 				if($j("#zipcode").val()== ""){
 					jumbo.showMsg("请填写邮政编码!");
 					$j("#zipcode").focus();
 					return;
 				}
 				if($j("#selProvince").val() == ""){
 					jumbo.showMsg("请选择省");
 					return;
 				}
 				if($j("#selCity").val() == ""){
 					jumbo.showMsg("请选择市");
 					return;
 				}
 				if($j("#selDistrict").val() == ""){
 					jumbo.showMsg("请选择区");
 					return;
 				}
 				/*if($j("#sfWhCode").val() == ""){
 					jumbo.showMsg("SF仓库编码必须填写");
 					$j("#sfWhCode").focus();
 					return;
 				}*/
 				if($j("#cityCode").val() == ""){
 					jumbo.showMsg("仓库城市编码必须填写");
 					$j("#cityCode").focus();
 					return;
 				}
 			}else{
 				postData["warehouse.isSfOlOrder"] = false;
 			}
			if($j("#isThirdPartyPaymentSF").attr("checked")){
 				postData["warehouse.isThirdPartyPaymentSF"] = true;
			}else{
				postData["warehouse.isThirdPartyPaymentSF"] = false;
			}
			//EMS电子面单
			if($j("#isEmsOlOrder").attr("checked")){
 				postData["warehouse.isEmsOlOrder"] = true;
			}else{
				postData["warehouse.isEmsOlOrder"] = false;
			}	
			//STO电子面单
			if($j("#isOlSto").attr("checked")){
				postData["warehouse.isOlSto"] = true;
			}else{
				postData["warehouse.isOlSto"] = false;
			}
			if($j("#isZtoOlOrder").attr("checked")){
				postData["warehouse.isZtoOlOrder"] = true;
			}else{
				postData["warehouse.isZtoOlOrder"] = false;
			}
			//圆通电子面单
			if($j("#isYtoOlOrder").attr("checked")){
				postData["warehouse.isYtoOlOrder"] = true;
			}else{
				postData["warehouse.isYtoOlOrder"] = false;
			}
			//如风达电子面单
			if($j("#isRfdOlOrder").attr("checked")){
				postData["warehouse.isRfdOlOrder"] = true;
			}else{
				postData["warehouse.isRfdOlOrder"] = false;
			}
			//TTK电子面单
			if($j("#isTtkOlOrder").attr("checked")){
				postData["warehouse.isTtkOlOrder"] = true;
			}else{
				postData["warehouse.isTtkOlOrder"] = false;
			}
			if($j("#isDhlOlOrder").attr("checked")){
				postData["warehouse.isDhlOlOrder"] = true;
			}else{
				postData["warehouse.isDhlOlOrder"] = false;
			}
			// 是否自动分配订单库存
			if($j("#isAutoOcp").attr("checked")){
				postData["warehouse.isAutoOcp"] = true;
			}else{
				postData["warehouse.isAutoOcp"] = false;
			}
			//是否启用订单占用区域优先级的校验
			if($j("#isAreaOcpInv").attr("checked")){
				postData["warehouse.isAreaOcpInv"] = true;
			}else{
				postData["warehouse.isAreaOcpInv"] = false;
			}
//			//万象电子面单
//			if($j("#isWxOlOrder").attr("checked")){
//				postData["warehouse.isWxOlOrder"] = true;
//			}else{
//				postData["warehouse.isWxOlOrder"] = false;
//			}
			//CXC电子面单
			if($j("#isCxcOlOrder").attr("checked")){
				postData["warehouse.isCxcOlOrder"] = true;
			}else{
				postData["warehouse.isCxcOlOrder"] = false;
			}
			if($j("#isSupportPackageSku").attr("checked")){
				postData["warehouse.isSupportPackageSku"]=true;
			}else{
				postData["warehouse.isSupportPackageSku"]=false;
			}
			if($j("#isImperfect").attr("checked")){
				postData["warehouse.isImperfect"]=true;
			}else{
				postData["warehouse.isImperfect"]=false;
			}
			if($j("#isSupportSecKill").attr("checked")){
				postData["warehouse.isSupportSecKill"]=true;
			}else{
				postData["warehouse.isSupportSecKill"]=false;
			}
			if($j("#isNotExpireDate").attr("checked")){
				postData["warehouse.isNotExpireDate"]=true;
			}else{
				postData["warehouse.isNotExpireDate"]=false;
			}
			if($j("#isCheckConsumptiveMaterial").attr("checked")){
				postData["warehouse.isCheckConsumptiveMaterial"]=true;
			}else{
				postData["warehouse.isCheckConsumptiveMaterial"]=false;
			}
			
			if($j("#isBigLuxuryWeigh").attr("checked")){
				postData["warehouse.isBigLuxuryWeigh"]=true;
			}else{
				postData["warehouse.isBigLuxuryWeigh"]=false;
			}
			
			if($j("#isCheckPickingStatus").attr("checked")){
				postData["warehouse.isCheckPickingStatus"]=true;
			}else{
				postData["warehouse.isCheckPickingStatus"]=false;
			}
			if($j("#isCartonManager").attr("checked")){
				postData["warehouse.isCartonManager"]=true;
			}else{
				postData["warehouse.isCartonManager"]=false;
			}
			if($j("#isNotSn").attr("checked")){
				postData["warehouse.isNotSn"]=true;
			}else{
				postData["warehouse.isNotSn"]=false;
			}
			if($j("#isMqInvoice").attr("checked")){
 				postData["warehouse.isMqInvoice"] = true;
 				if($j("#invoiceTaxMqCode").val() == ""){
 					jumbo.showMsg("流水开票队列名必须填写");
 					$j("#invoiceTaxMqCode").focus();
 					return;
 				}
			}else{
				postData["warehouse.isMqInvoice"] = false;
			}
			
 			if($j("#wrapStuff").attr("checked")){
 				postData["warehouse.isNeedWrapStuff"] = true;
 			}else{
 				postData["warehouse.isNeedWrapStuff"] = false;
 			}
 			if($j("#isManualWeighing").attr("checked")){
 				postData["warehouse.isManualWeighing"] = true;
 			}else{
 				postData["warehouse.isManualWeighing"] = false;
 			}
 			if($j("#isSuggest").attr("checked")){
 				postData["warehouse.isSuggest"] =true;
 			}else{
 				postData["warehouse.isSuggest"] = false;
 			}
 			if($j("#isTransMust").attr("checked")){
 				postData["warehouse.isTransMust"] = true;
 			}else{
 				postData["warehouse.isTransMust"] = false;
 			}
 			if($j("#isManpowerConsolidation").attr("checked")){
 				postData["warehouse.isManpowerConsolidation"] = true;
 			}else{
 				postData["warehouse.isManpowerConsolidation"] = false;
 			}
 			
 			if($j("#isAgv").attr("checked")){
 				postData["warehouse.isAgv"] =true;
 			}else{
 				postData["warehouse.isAgv"] = false;
 			}
 			
 			if($j("#isSkipWeight").attr("checked")){
				postData["warehouse.isSkipWeight"]=true;
				var rs = loxia.syncXhrPost($j("body").attr("contextpath")+"/skuWeightCount.json");
				if(rs.result==0){
					jumbo.showMsg("该仓库下没有重量 无法跳过称重");
 					return;
				}
			}else{
				postData["warehouse.isSkipWeight"]=false;
			}
 			if($j("#isBondedWarehouse").attr("checked")){
 				postData["warehouse.isBondedWarehouse"] =true;
 			}else{
 				postData["warehouse.isBondedWarehouse"] = false;
 			}
 			
 			
 			if($j("#isAutoWh").attr("checked")){
 				postData["warehouse.isAutoWh"] = true;
 				//如果是自动化仓，初始化配置不能为空
 				if($j("#orderCountLimit").val() == ""){
 					jumbo.showMsg("自动化仓批次订单上限不能为空！");
 					return;
 				}
 				if($j("#idx2MaxLimit").val() == ""){
 					jumbo.showMsg("自动化仓小批次容量不能为空！");
 					return;
 				}
 				if($j("#seedingAreaCode").val() == ""){
 					jumbo.showMsg("自动化仓播种区编码不能为空！");
 					return;
 				}
 				if($j("#checkingAreaCode").val() == ""){
 					jumbo.showMsg("自动化仓复核区编码不能为空！");
 					return;
 				}
 				if($j("#autoPickinglistLimit").val() == ""){
 					jumbo.showMsg("集货口批次数量上限不能为空！");
 					return;
 				}
 			}else{
 				postData["warehouse.isAutoWh"] = false;
 			}
// 			if($j("#isCheckedBarcode").attr("checked")){
// 				postData["warehouse.isCheckedBarcode"] = true;
// 			}else{
// 				postData["warehouse.isCheckedBarcode"] = false;
// 			}
 			if($j("#ocpErrorLimit").val() == "0"){
 				jumbo.showMsg("失败次数不能为0");
 				return;
 			}
 			if($j("#ocpErrorLimit").val() > 5){
 				jumbo.showMsg("失败次数不能超过5");
 				return;
 			}
 			if($j("#pickModeSku").val() < 2 && $j("#pickModeSku").val() != ""){
 				jumbo.showMsg("拣货模式分隔值不能小于2");
 				return;
 			}
 			
 			
 			if(""!=$j("#totalPickinglistLimit").val()&&null!=$j("#totalPickinglistLimit").val()){
 				var g = /^[0-9]*[0-9][0-9]*$/;
 				if(!g.test($j("#totalPickinglistLimit").val())){
 					jumbo.showMsg("拣货配货批次上限必须为正整数");
 					return;
 				}
 			}
 			
 			if(""!=$j("#outBoundOrderNum").val()&&null!=$j("#outBoundOrderNum").val){
 				var g = /^[0-9]*[0-9][0-9]*$/;
 				if(!g.test($j("#outBoundOrderNum").val())){
 					jumbo.showMsg("一键创批下限订单数必须为正整数");
 					return;
 				}
 			}
 			
 			if(""!=$j("#manpowerPickinglistLimit").val()&&null!=$j("#manpowerPickinglistLimit").val()){
 				var g = /^[0-9]*[0-9][0-9]*$/;
 				if(!g.test($j("#manpowerPickinglistLimit").val())){
 					jumbo.showMsg("人工集货配货批次上限必须为正整数");
 					return;
 				}
 			}
 			
 			
 			postData["warehouse.orderCountLimit"] = $j("#orderCountLimit").val();
 			postData["warehouse.seedingAreaCode"] = $j("#seedingAreaCode").val();
 			postData["warehouse.checkingAreaCode"] = $j("#checkingAreaCode").val();
 			postData["warehouse.autoSeedGroup"] = $j("#autoSeedGroup").val();
 			postData["warehouse.idx2MaxLimit"] = $j("#idx2MaxLimit").val();
 			postData["warehouse.ocpErrorLimit"] = $j("#ocpErrorLimit").val();
 			postData["warehouse.skuQty"] = $j("#pickModeSku").val();
 			postData["warehouse.autoPickinglistLimit"] = $j("#autoPickinglistLimit").val();
 			postData["warehouse.handLimit"] = $j("#handLimit").val();
 			postData["warehouse.totalPickinglistLimit"] = $j("#totalPickinglistLimit").val();
 			postData["warehouse.manpowerPickinglistLimit"] = $j("#manpowerPickinglistLimit").val();
 			postData["warehouse.outBoundOrderNum"]=$j("#outBoundOrderNum").val();
 			// "warehouseForm"
 			var transIds = getTransIds();
 			if(transIds.length > 0){
 				transIds = transIds.join();
 				postData["transIds"] = transIds;
 			}else{
 				postData["transIds"] = "";
 			}
 			if($j("#autoCheckBox").attr("checked")){
 				var index = 0;
 				//如果选中自动创建配货清单判断自动配货配置数据
 				 if($j("#jgTime").val() == ""){
 					jumbo.showMsg("间隔时间（分钟）必须填写！");
 					$j("#jgTime").focus();
 					return;
 				}
 				 if($j("#jgTime").val().search("^[0-9]*[1-9][0-9]*$") != 0){
 					jumbo.showMsg("间隔时间（分钟）格式不正确！");
 					$j("#jgTime").focus();
 					return;
 				}
 				var idss = $j("#warehouse-autopl-list").jqGrid('getDataIDs');
				for(var i=0;i < idss.length;i++){
					var datass = $j("#warehouse-autopl-list").jqGrid('getRowData',idss[i]);
					if($j("#warehouse-autopl-list tr[id="+datass["id"]+"] input[name=isAutoRadio]").attr('checked') == true){
						index++;
						postData["apc.autoPlr"] = datass["id"];
					}
				}
				if(index == 0){
					jumbo.showMsg("请选择自动配货规则！");
 					return;
				}
				postData["apc.status"] = 1;
	 			postData["apc.intervalMinute"]=$j("#jgTime").val();
	 			if($j("#zxTime").val()!=null){
		 			postData["apc.nextExecuteTime"] = $j("#zxTime").val();	
	 			}
 			}else{
				postData["apc.status"] = 0;
 			}
 			//if(errorMsg.length == 0){
 				if(confirm("确定保存已修改的内容？")){
					loxia.asyncXhrPost(
						baseUrl + "/updatewarehouseinfo.json",  
						postData,
						{
							success:function () {
				    			$modfiyStatusInfo("仓库基本信息编辑修改成功");
								setTimeout("$modfiyStatusInfo('状态栏');",5000);
						   }
						}
					);
				}
 			//}else{
 				//jumbo.showMsg(errorMsg);
 				//setTimeout(function(){jumbo.showMsg();},5000);
 			//}
	});
	
	$j("#query").click(function(){
		var url=baseUrl + "/findTransportatorListByWeight.json";
		$j("#check_lpCode_package_info").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm1")}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("check_lpCode_package_info",{},url,loxia._ajaxFormToObj("queryForm1"));
	});
	
	// operationCenter
	$j("#OCsaveBtn").bind("click", function(event) {
 			var errorMsg = loxia.validateForm("operationcenterForm");
 			if(errorMsg.length == 0){
 				if(confirm("确定保存已修改的内容？")){
 					loxia.asyncXhrPost(
 						baseUrl + "/updateopercenterinfo.json",
						"operationcenterForm",
						{
							success:function () {
				    			$modfiyStatusInfo("运营中心基本信息编辑修改成功");
								setTimeout("$modfiyStatusInfo('状态栏');",5000);
						   }
						}
					);
 				}
 			}else{
 				jumbo.showMsg(errorMsg);
 				setTimeout(function(){jumbo.showMsg();},5000);
 			}
	});
		
	$j("#selProvince").change(function(){
		var province = $j(this).val();
		if (province == '') {
			return;
		}else{
			var rs = loxia.syncXhrPost(baseUrl+"/loadcitybyprovince.json",{"province":province});
			if(rs &&  rs.result == "success"){
				$j("#selCity").empty();
				$j("#selCity").append("<option value=''>--请选择--</option>");
				for (var i=0;i <rs.list.length; i++) {
					$j("#selCity").append("<option value='" + rs.list[i].city + "'>" + rs.list[i].city + "</option>");
				}
			}else{
				$j("#selCity").empty();
				$j("#selCity").append("<option value=''>--请选择--</option>");
			}
			$j("#selDistrict").empty();
			$j("#selDistrict").append("<option value=''>--请选择--</option>");
		}
	});
	
	$j("#selCity").change(function(){
		var province = $j("#selProvince").val();
		var city = $j(this).val();
		if (city == '' || province == '') {
			return;
		}else{
			var rs = loxia.syncXhrPost(baseUrl+"/loaddistrictsbycity.json",{"province":province,"city":city});
			if(rs &&  rs.result == "success"){
				$j("#selDistrict").empty();
				$j("#selDistrict").append("<option value=''>--请选择--</option>");
				for (var i=0;i <rs.list.length; i++) {
					$j("#selDistrict").append("<option value='" + rs.list[i].district + "'>" + rs.list[i].district + "</option>");
				}
			}else{
				$j("#selDistrict").empty();
				$j("#selDistrict").append("<option value=''>--请选择--</option>");
			}
		}
	});
	
	//设置客户
	$j("#setCustomer").click(function(){
			var cid = $j("#customerId").val();
			if(cid == ""){
				jumbo.showMsg("客户不允许为空!");
				return;
			}
			var rs = loxia.syncXhrPost(baseUrl+"/warehousesetcustomer.json",{"customer.id":cid});
			if(rs &&  rs.result == "success"){
				window.location.reload();
			}else{
				jumbo.showMsg("系统异常!");
			}
	});
	
	$j("#checkbox").click(function(){
		var a = $j("#checkbox").attr("checked");
		if(a){
			$j("#selectableTransportatorBody input[type='checkbox']").attr("checked", true);
		}
		else{
			$j("#selectableTransportatorBody input[type='checkbox']").attr("checked", false);
		}
			
		
	});
	$j("#addTransportator").click(function(){
		$j("#deleteTransportator").click();
//		var selectedIds = [];
//		$j("#selectedTransportatorBody tr").each(function(i){
//			var id = $j(this).attr("id");
//			selectedIds.push(id);
//		});
		$j("#selectableTransportatorBody input[type='checkbox']:checked").each(function(i){
			var id = $j(this).parent().parent().attr("id");
//			var isContain = false;
//			for(var j = 0; j < selectedIds.length; j++){
//				if(id == selectedIds[j]){
//					isContain = true;
//					break;
//				}
//			}
//			if(false == isContain){
				var html = "<tr id='"+id+"'>"+
				"<td>"+$j("#code"+id).html()+"</td>"+
				"<td>"+$j("#name"+id).html()+"</td>"+
				"<td>"+$j("#cod"+id).html()+"</td></tr>";
				$j(html).appendTo($j("#selectedTransportatorBody"));
//			}
		});
	});
	$j("#deleteTransportator").click(function(){
		$j("#selectedTransportatorBody").children().remove();
	});
	//导入覆盖区域
	$j("#import").click(function(event){
		event.preventDefault();
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));
			return;
		}
		loxia.lockPage();
		//$j("#coverageAreaFile").val($j("#file").val());
		var file = $j("#file");
		var coverFile = $j(file).clone();
		$j(coverFile).attr("id", "coverageAreaFile");
		$j("#importForm").append(coverFile);
		$j("#importForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/base/importCoverageArea.do")
		);
		loxia.submitForm("importForm");
	});
	//覆盖导入
	$j("#coverImport").click(function(event){
		event.preventDefault();
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));
			return;
		}
		if(confirm("该操作将清除当前维护的所有覆盖区域？")){
			loxia.lockPage();
			//$j("#coverageAreaFile").val($j("#file").val());
			var file = $j("#file");
			var coverFile = $j(file).clone();
			$j(coverFile).attr("id", "coverageAreaFile");
			$j("#importForm").append(coverFile);
			$j("#importForm").attr("action",
					loxia.getTimeUrl($j("body").attr("contextpath") + "/base/coverImportCoverageArea.do")
			);
			loxia.submitForm("importForm");	
		}
	});
	$j("#import1").click(function(){
		if(!/^.*\.xls$/.test($j("#fileType").val())){
			jumbo.showMsg("请选择正确的Excel导入文件");
			return;
		}
		$j("#warehouseForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importWarehouseImperfect.do"));
		loxia.submitForm("warehouseForm");
	});
	$j("#importwhy").click(function(){
		var imperfectId = $j("#imperfectId").val();
		if(!/^.*\.xls$/.test($j("#filewhy").val())){
			jumbo.showMsg("请选择正确的Excel导入文件");
			return;
		}
		$j("#warehouseForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importImperfectWhy.do?imperfectId="+imperfectId));
		loxia.submitForm("warehouseForm");
	});
	$j("#imp1").click(function(){
		$j("#tbl-bichannel-imperfect-list").jqGrid('setGridParam',{
			url:window.$j("body").attr("contextpath")+"/findWarehouseImperfectlList.json",
			page:1
		}).trigger("reloadGrid");
		$j("#imperfectLineadd").addClass("hidden");
		$j("#imperfect-add").removeClass("hidden");
		$j("#imperfect").removeClass("hidden");
		$j("#imperfectLine").addClass("hidden");
		$j("#imperfectLine-add").addClass("hidden");
		
	});
});

var $modfiyStatusInfo=function(info){
		window.parent.$j("#bottom").html(info);
	};
function initSelectableTransportator(){
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getAllTransportator.json");
	if(result){
		for(var idx in result){
			var isSupportCod = result[idx].isSupportCod;
			if(true == isSupportCod){
				isSupportCod = "是";
			}else{
				isSupportCod = "否";
			}
			var html = "<tr id='"+result[idx].id+"'>"+
			"<td><input type='checkbox' name='checkbox' value='checkbox'/></td>"+
			"<td id='code"+result[idx].id+"'>"+result[idx].expCode+"</td>"+
			"<td id='name"+result[idx].id+"'>"+result[idx].name+"</td>"+
			"<td id='cod"+result[idx].id+"'>"+isSupportCod+"</td></tr>";
			$j(html).appendTo($j("#selectableTransportatorBody"));
		}
	}
}
function getTransIds(){
	var selectedIds = [];
	$j("#selectedTransportatorBody tr").each(function(i){
		var id = $j(this).attr("id");
		selectedIds.push(id);
	});
	return selectedIds;
}
function initSelectedTransportator(){
	$j("#selectedTransportatorBody").children().remove();
	var selectedIds = [];
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findAllTransRef.json");
	if(result){
		for(var idx in result){
			var id = result[idx].id;
			selectedIds.push(id);
			var isSupportCod = result[idx].isSupportCod;
			if(true == isSupportCod){
				isSupportCod = "是";
			}else{
				isSupportCod = "否";
			}
			var html = "<tr id='"+id+"'>"+
			"<td>"+result[idx].expCode+"</td>"+
			"<td>"+result[idx].name+"</td>"+
			"<td>"+isSupportCod+"</td></tr>";
			$j(html).appendTo($j("#selectedTransportatorBody"));
		}
	}
	for(var i = 0; i < selectedIds.length; i++){
		var id = selectedIds[i];
		var tr = $j("#selectableTransportatorBody").find("tr[id='"+id+"']");
		if(tr.length > 0){
			$j(tr).find("input[type='checkbox']").attr("checked",true);
		}
	}
}
//初始化自动创建配货清单配置数据
function initAutoPl(){
	var autopl = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getAotoPlConfig.json");
	if(autopl && autopl.msg){
		var dateTime = autopl.msg["nextExecuteTime"].toString().split(".");
		$j("#jgTime").val(autopl.msg["intervalMinute"]);
		$j("#zxTime").val(dateTime[0]);
		if(autopl.msg["status"] == 1){
			$j("#autoCheckBox").attr("checked","checked");
		}
	}
}
function importSFCReturn(flag){
	if(true == flag){
		$j("#sf_next_morning_config_list").jqGrid('setGridParam',{url:$j("body").attr("contextpath") + "/findsfnextmorningconfigbyouid.json",
			postData:{}}).trigger("reloadGrid",[{page:1}]);
	}
}
function importNikeReturn(flag){
	if(true == flag){
		$j("#nike_today_send_config_list").jqGrid('setGridParam',{url:$j("body").attr("contextpath") + "/findNIKETodaySendConfigByOuId.json",
			postData:{}}).trigger("reloadGrid",[{page:1}]);
		$j("#importForm").html("");
	}
}
function importReturn(flag){
	$j("#coverageAreaFile").remove();
	$j("#file").val("");
	if(true == flag){
		reloadCoverageArea();
	}
}

function reloadCoverageArea(){
	$j("#warehouse_coverage_area_list").jqGrid('setGridParam',{url:$j("body").attr("contextpath") + "/findCoverageAreaByOuId.json",
		postData:{}}).trigger("reloadGrid",[{page:1}]);
}

function deleteCoverageArea(obj){
	var row = $j(obj).parent().parent().parent().attr("id");
	loxia.lockPage();
	if(confirm("确定删除当前区域？")){
		var baseUrl = $j("body").attr("contextpath");
		var rs = loxia.syncXhrPost(baseUrl+"/deleteCoverageArea.json",{"areaId":row});
		if(rs &&  rs.result == "success"){
			reloadCoverageArea();
		}else{
			jumbo.showMsg(rs.msg);
		}
	}
	loxia.unlockPage();
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
	
	
}
function editLpCodeWeight(obj){
	   var ids = $j(obj).parent().siblings().eq(1).text();
	   if (null != ids && ids.length > 0) {
			$j("#dialog_addWeight").dialog("open");
			$j("#t_ma_transportator_weigth_id").val(ids);
			$j("#addexpCode").val($j(obj).parent().siblings().eq(2).text().replace(/(^\s*)|(\s*$)/g, ""));
			$j("#add_max_weight").val($j(obj).parent().siblings().eq(4).text().replace(/(^\s*)|(\s*$)/g, ""));
			$j("#add_min_weight").val($j(obj).parent().siblings().eq(5).text().replace(/(^\s*)|(\s*$)/g, ""));
			$j("#add_weight_difference_percent").val($j(obj).parent().siblings().eq(3).text().replace(/(^\s*)|(\s*$)/g, ""));
       }
};    


function showMsg(msg){
	jumbo.showMsg(msg);
}

