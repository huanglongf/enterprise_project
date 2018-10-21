$j.extend(loxia.regional['zh-CN'],{
	CORRECT_FILE_PLEASE : "请选择正确的Excel导入文件"
//	CODE : "配货批次号"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function showDetail(obj){
	//$j("#dialog_addRole").dialog({title: "新建物流规则", modal:true,closeOnEscape:false, autoOpen: false, position: [600,170],width:530});//弹窗属性设置
	$j("#dialog_chooseArea").dialog({title: "选择配送范围", modal:true,closeOnEscape:false, autoOpen: false,width:500});//弹窗属性设置
	$j("#dialog_newArea").dialog({title: "创建配送范围", modal:true,closeOnEscape:false, autoOpen: false, width:500});//弹窗属性设置
	$j("#dialog_editArea").dialog({title: "编辑配送范围", modal:true,closeOnEscape:false, autoOpen: false,width:500});//弹窗属性设置
	$j("#dialog_addSku").dialog({title: "商品查询", modal:true,closeOnEscape:false, autoOpen: false, position: [500,120],width:670});//弹窗属性设置
	$j("#dialog_addTag").dialog({title: "商品标签查询", modal:true,closeOnEscape:false, autoOpen: false,position: [550,120],width:550});//弹窗属性设置
	$j("#detail_tabs").tabs();
	$j("#detail_tabs2").tabs();
	$j("#bichannel-list").addClass("hidden");
	$j("#bichannel-list-deatil").removeClass("hidden");
	$j("#SelStatusOpc option[value='1']").attr("selected", "selected"); 
	var chanId = $j(obj).parent().parent().attr("id");
	var data = $j("#tbl-bichannel-list").jqGrid("getRowData",chanId);
	$j("#detailChannelCode").html(data["code"]);
	$j("#detailChannelName").html(data["name"]);
	var baseUrl = $j("body").attr("contextpath");
	
	//物流规则表
	var roleUrls = baseUrl + "/findTransRole.json?chanId="+chanId;
	$j("#tbl-transRole-list").jqGrid('setGridParam',{url:roleUrls,datatype: "json",}).trigger("reloadGrid");
	
	var transPortUrls = baseUrl + "/findTransService.json";
	$j("#tbl-transport-service").jqGrid('setGridParam',{url:transPortUrls,datatype: "json",}).trigger("reloadGrid");
	
	tempCode = data["code"];
	tempName = data["name"];
	tempChannId = chanId;
	$j("#channelId").val(chanId);
	var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/queryKeyWordForChannlId.json?channelId="+chanId);
	if(rs && rs.msg == 'success'){
		var emskey = rs.key;
		$j("#includeKeyWord").attr("value",emskey);
	}else{
		if(rs.errMsg != null){
			jumbo.showMsg(rs.errMsg);
		}
	}
	
}

var isAdd = "1";//新增
var tempCode = "";
var tempName = "";
var tempChannId = "";
var tempAreaId = "";
$j(document).ready(function (){
	$j("#head_tab").tabs();
	$j("#bichannel-list-deatil").addClass("hidden");
	$j("#dialog_addRole").addClass("hidden");
	$j("#dialog_chooseArea").addClass("hidden");
	$j("#dialog_newArea").addClass("hidden");
	$j("#dialog_editArea").addClass("hidden");
	$j("#dialog_addSku").addClass("hidden");
	$j("#dialog_addTag").addClass("hidden");
	$j("#transAreaIdLabel").addClass("hidden");
	$j("#transAreaLabelStatus").addClass("hidden");
	$j("#btnEdit_tab").hide();
	var baseUrl = $j("body").attr("contextpath");
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findBiCustomer.do");
	for(var idx in result){
		$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#customerId"));
	}
	
	//新建渠道规则
	$j("#add").click(function(){ 
		clearMap();//清空map
		isAdd = "1";//新增
		$j("#next").text("下一步");
		$j("#next").css("width","100px"); 
		//清空数据
		$j("#RoleCode").attr("value","");
		$j("#RoleName").attr("value","");
		$j("#SelStatusOpc").attr("value","");
		$j("#RoleSort").attr("value","");
		$j("#RoleSort").removeClass("hidden");
		$j("#sortLabel").removeClass("hidden");
		document.getElementById("transAreaLabelCode").innerHTML = "";
		document.getElementById("transAreaLabelName").innerHTML = "";
		document.getElementById("transAreaIdLabel").innerHTML = "";
		document.getElementById("transAreaLabelStatus").innerHTML = "";
		$j("#transAreaLabel").removeClass("hidden");
		$j("#minAmount").attr("value","");
		$j("#maxAmount").attr("value","");
		$j("#removeKeyword").attr("value","");
		$j("#minWeight").attr("value","");
		$j("#maxWeight").attr("value","");
		var ids = $j("#tbl-transport-service").jqGrid('getDataIDs');
		for(var i=0;i<ids.length;i++){
			$j("input[name=myId]:eq("+i+")").attr("checked",false); 
		}
		$j("#tbl-sku-list").clearGridData(false);
		$j("#tbl-sku-categories").clearGridData(false);
		$j("#tbl-sku-tag").clearGridData(false);
		
		$j("#RoleCode").removeAttr("readonly","readonly"); 
		$j("#dialog_addRole").removeClass("hidden");
		$j("#bichannel-list-deatil").addClass("hidden");
		document.getElementById('one').style.display = "";
		$j("#createChannelCode").html(tempCode);
		$j("#createChannelName").html(tempName);
		findChannelWarehouse(tempChannId,"");
	});
	
	//保存EMS关键字
	$j("#saveEms").click(function(){
		var includeKey = $j("#includeKeyWord").val();
		var channelId = $j("#channelId").val();
		$j.post($j("body").attr("contextpath") + "/saveKeyWord.json",{includeKey:includeKey,channelId:channelId},function(data) {
			if(null!=data && data.msg == 'success'){
				jumbo.showMsg("操作成功！");
			}else{
				jumbo.showMsg("操作失败！");
			}
		});
	});
	//选择配送范围--弹出对话框
	$j("#choose").click(function(){
		//配送范围表
		var transAreaUrls = baseUrl + "/findTransArea.json";
		$j("#tbl-transArea-list").jqGrid('setGridParam',{url:transAreaUrls,datatype: "json",}).trigger("reloadGrid");
		$j("#dialog_chooseArea").dialog("open");
		$j("#dialog_chooseArea").removeClass("hidden");
		//$j("#tbl-transArea-list").trigger("reloadGrid"); 
	});
	
	//新建配送范围--弹出对话框
	$j("#new").click(function(){
		$j("#AreaCode").attr("value","");
		$j("#AreaName").attr("value","");
		$j("#file").attr("value","");
		$j("#dialog_newArea").dialog("open");
		//$j("#dialog_newArea").prev().find("a").addClass("hidden");
		$j("#dialog_newArea").removeClass("hidden");
	});
	
	//编辑配送范围--弹出对话框
	$j("#edit").click(function(){
		//配送范围表
		var transAreaUrl2s = baseUrl + "/updateFindTransArea.json";
		$j("#tbl-edit-transArea-list").jqGrid('setGridParam',{url:transAreaUrl2s,datatype: "json",}).trigger("reloadGrid");
		$j("#dialog_editArea").dialog("open");
		$j("#dialog_editArea").removeClass("hidden");
		document.getElementById("editOneTable").style.display = "";
		document.getElementById("editTwoTable").style.display = "none";
		//$j("#tbl-edit-transArea-list").trigger("reloadGrid"); 
	});
	
	
	//新建配送范围
	$j("#areaNew").click(function(){
		var areaCode = $j("#AreaCode").val();
		var areaName = $j("#AreaName").val();
		if(areaCode ==""){
			jumbo.showMsg("编码不能为空！");
			return;
		}
		if(areaName ==""){
			jumbo.showMsg("名称不能为空！");
			return;
		}
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg("请选择正确的Excel导入文件");
			return;
		}
		loxia.lockPage();
		var rs = loxia.getTimeUrl(baseUrl + "/warehouse/transAreaImport.do?areaCode="+areaCode+"&areaName="+areaName);
		$j("#importForm").attr("action",rs);
		loxia.submitForm("importForm");
		$j("#dialog_newArea").dialog("close");
		$j("#dialog_newArea").addClass("hidden");
		$j("#tbl-transArea-list").trigger("reloadGrid"); 
	});
	
	//新建配送范围取消--关闭对话框
	$j("#areaCancel").click(function(){
		$j("#dialog_newArea").dialog("close");
		$j("#dialog_newArea").addClass("hidden");
	});
	
	
	//编辑配送范围
	$j("#areaEditNew").click(function(){
		var areaId = $j("#transAreaIdLabel").html();
		var areaName = $j("#AreaEditName").val();
		var areaStats = $j("#SelEditStatusOpc").val();
		var Editfile = $j("#Editfile").val();
		if(areaName ==""){
			jumbo.showMsg("名称不能为空！");
			return;
		}
		if(Editfile == ""){//如果文件为空，则保存配送范围信息
			var rs = loxia.syncXhrPost(baseUrl+ "/updateAreaGroup.json?areaId="+tempAreaId+"&areaName="+areaName+"&status="+areaStats);
			if(rs && rs.msg == 'success'){
				jumbo.showMsg("操作成功！");
				$j("#dialog_editArea").dialog("close");
				//$j("#transAreaLabelName").html(areaName);
			}else{
				jumbo.showMsg("操作失败！");
			}
		}else{//如不为空，则导入exl
			if(!/^.*\.xls$/.test($j("#Editfile").val())){
				jumbo.showMsg("请选择正确的Excel导入文件");
				return;
			}
			loxia.lockPage();
			var rs = loxia.getTimeUrl(baseUrl + "/warehouse/transAreaImport.do?areaId="+areaId+"&areaName="+areaName+"&status="+areaStats);
			$j("#importEditForm").attr("action",rs);
			loxia.submitForm("importEditForm");
			$j("#dialog_editArea").dialog("close");
		}
	});
	
	//编辑配送范围取消--关闭对话框
	$j("#areaEditCancel").click(function(){
		document.getElementById("editOneTable").style.display = "";
		document.getElementById("editTwoTable").style.display = "none";
	});
	
	//编辑渠道快递规则状态变更取消--关闭对话框
	$j("#editTransRoleAccordBack").click(function(){
		$j("#btnEdit_tab").dialog("close");
	});
	
	//编辑渠道快递规则状态变更保存--关闭对话框
	$j("#saveEditTransRoleAccord").click(function(){
		var roleIsAvailable = $j("#roleIsAvailable").val();
		if(roleIsAvailable ==""){
			jumbo.showMsg("是否可用下拉单请选择相应状态！");
			return;
		}
		
		var priority = $j("#priority").val();
		if(!/^\+?[1-9][0-9]*$/ .test(priority)){
			jumbo.showMsg("请输入1到9999的整数!");//不是一个合法的数字或精度要求不符合要求
			return;
		}
		if(priority<1 || priority > 9999){
			jumbo.showMsg("请输入1到9999的整数!");//不是一个合法的数字或精度要求不符合要求
			return;
		}
		var postData = {};
		postData["id"] = $j("#transRoleAccordId").val();
		postData["changeTime"] = $j("#changeTime").val();
		postData["priority"] = priority;
		postData["roleIsAvailable"] = roleIsAvailable;
		var rs = loxia.syncXhrPost(baseUrl + "/saveTransRoleAccord.json",postData);
		$j("#btnEdit_tab").dialog("close");
		if(rs && rs["success"]=="success"){
			jumbo.showMsg("编辑成功!");
		}else{
			jumbo.showMsg("编辑失败!");
		}
		$j('#queryTransRoleAccord').trigger("click");
	});
	
	//编辑配送范围文件导出
	$j("#Editexport").click(function(){
		//var areaId = $j("#transAreaIdLabel").html();
		var areaName = $j("#AreaEditName").val()+"_导出";
		var iframe = document.createElement("iframe");
		iframe.src = loxia.getTimeUrl(baseUrl + "/warehouse/expTransAreaDetail.do?areaId="+tempAreaId+"&areaName="+areaName);
		iframe.style.display = "none";
		$j("#upload").append($j(iframe));
	});
	
	$j("#tbl-transArea-list").jqGrid({
		colNames: ["ID","编码","名称","状态","导出明细","选择"],
		colModel: [
							{name:"id", hidden: true},
							{name:"code",index:"code", width:90,resizable:true},
							{name:"name",index:"name",width:90,resizable:true},
							{name:"areaStatus",index:"areaStatus",width:80,resizable:true},
							{name:"btnExp",width:80,resizable:true},
							{name:"btnSure",width:80,resizable:true}
				 		],
    	caption: "配送范围列表",
    	sortname: 'id',
    	multiselect: false,
    	height:150,
    	width:470,
	   	sortorder: "asc",
	   	rowNum: 1000,
	    rowList:1000,
		shrinkToFit:false,
		viewrecords: false,
		rownumbers:false,
		jsonReader: {repeatitems : false, id: "0" },
		gridComplete: function(){
			var btn1 = '<div><button type="button" style="width:80px;" class="confirm" name="btnExp2" loxiaType="button" onclick="expArea(this);">'+"导出"+'</button></div>';
			var btn2 = '<div><button type="button" style="width:80px;" class="confirm" name="btnSure2" loxiaType="button" onclick="sureArea(this);">'+"确认"+'</button></div>';
			var ids = $j("#tbl-transArea-list").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				$j("#tbl-transArea-list").jqGrid('setRowData',ids[i],{"btnExp":btn1,"btnSure":btn2});
			}
			loxia.initContext($j(this));
		}
	});
	
	$j("#tbl-edit-transArea-list").jqGrid({
		colNames: ["ID","编码","名称","状态","操作"],
		colModel: [
							{name:"id", hidden: true},
							{name:"code",index:"code", width:110,resizable:true},
							{name:"name",index:"name",width:110,resizable:true},
							{name:"areaStatus",index:"areaStatus",width:110,resizable:true},
							{name:"btnExp",width:80,resizable:true}
				 		],
    	caption: "配送范围列表",
    	sortname: 'id',
    	multiselect: false,
    	height:150,
    	width:480,
	   	sortorder: "asc",
		shrinkToFit:false,
		viewrecords: false,
		rownumbers:true,
		jsonReader: {repeatitems : false, id: "0" },
		gridComplete: function(){
			var btn1 = '<div><button type="button" style="width:80px;" class="confirm" name="btnExp" loxiaType="button" onclick="editAreas(this);">'+"编辑"+'</button></div>';
			var ids = $j("#tbl-edit-transArea-list").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				$j("#tbl-edit-transArea-list").jqGrid('setRowData',ids[i],{"btnExp":btn1});
			}
			loxia.initContext($j(this));
		}
	});
	
	//新增商品
	$j("#skuAdd").click(function(){
		$j("#dialog_addSku").dialog("open");
		$j("#dialog_addSku").removeClass("hidden");
		//商品表
		$j("#tbl-commodity-query").jqGrid({
			datatype: "local",
			postData:loxia._ajaxFormToObj("form_query"),
			colNames: ["ID","SKU编码","条码","名称","品牌对接码","品牌","操作"],
			colModel: [
			           {name: "id", index: "id", hidden: true},		         
			           {name: "code", index: "code", width: 120,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, resizable: true},
			           {name: "barCode", index: "barCode", width: 110, resizable: true},
			           {name: "name", index: "name", width: 180, resizable: true},
			           {name: "extensionCode1", index: "extensionCode1", width: 110, resizable: true},
			           {name: "brandName", index: "brandName", width: 100, resizable: true,hidden: true},
			           {name: "btnChoose", width: 80, resizable: true}],
			caption: "商品列表",
		   	pager:"#pager3",
		   	sortname: 'code',
		   	rownumbers:false,
		   	multiselect: false,
		   	viewrecords: true,
		   	rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
			height:300,
			sortorder: "desc",
			jsonReader: { repeatitems : false, id: "0" },
			gridComplete: function(){
				var btn1 = '<div><button type="button" style="width:80px;" class="confirm" name="btnChoose" loxiaType="button" onclick="addSku(this);">'+"选择"+'</button></div>';
				var ids = $j("#tbl-commodity-query").jqGrid('getDataIDs');
				for(var i=0;i < ids.length;i++){
					$j("#tbl-commodity-query").jqGrid('setRowData',ids[i],{"btnChoose":btn1});
				}
				loxia.initContext($j(this));
			}
		});
		
		//查询商品
		$j("#searchSku").click(function(){
			var pinpai = $j("#brandSelect").val();
			if(pinpai == ""){
				jumbo.showMsg("请先选择品牌");
				return;
			}
			var url = $j("body").attr("contextpath")+"/findSku.json";
			$j("#tbl-commodity-query").jqGrid('setGridParam',{url:url,postData:loxia._ajaxFormToObj("form_query"),datatype: "json",}).trigger("reloadGrid");
			bindTable();
		});
		
		//商品重置
		$j("#resetSku").click(function(){
			$j("#form_query input").val("");
			$j("#brandSelect option:first").attr("selected",true);
		});
	});
	
	//新增商品分类
	$j("#addCategories").click(function(){
		var categoId = $j("#categoSelect").val(); 
		var categoName = $j("#categoSelect").find("option:selected").text();
		if(categoName == ''){
			jumbo.showMsg("分类名称不能为空!");
		}else{
			var obj ={
				id:categoId,
				skuCategoriesName:categoName
			};
			if(hashMapCatego.get(obj.id) == null && updatehashMap2.get(obj.id) == null){
				copyCategories(obj);
				hashMapCatego.put(obj.id, obj.skuCategoriesName);
				updatehashMap2.put(obj.id, obj.skuCategoriesName);
			}else{
				jumbo.showMsg("该分类已添加！");
			}
		}
	});
	
	//新增商品标签
	$j("#skuTagAdd").click(function(){
		$j("#dialog_addTag").dialog("open");
		$j("#dialog_addTag").removeClass("hidden");
		//商品标签查询
		$j("#tbl-tag-query").jqGrid({
			url:baseUrl + "/findSkuTag.json",
			datatype: "json",
			colNames: ["ID","编码","名称","类型","状态","操作"],
			colModel: [
								{name:"id", hidden: true},
								{name:"code",index:"code", width:105,resizable:true},
								{name:"name",index:"name", width:105,resizable:true},
								{name:"tagType",index:"tagType", width:100,resizable:true},
								{name:"tagStatus",index:"tagStatus", width:100,resizable:true},
								{name:"btnCreateTag",index:"btnCreateTag",width:80,resizable:true}
					 		],
			caption: "商品标签",
		   	sortname: 'id',
		   	multiselect: false,
			sortorder: "desc",
			width:520,
		   	shrinkToFit:false,
			height:300,
			viewrecords: true,
		   	rownumbers:false,
			jsonReader: {repeatitems : false, id: "0"},
			gridComplete: function(){
				var btn1 = '<div><button type="button" style="width:80px;" class="confirm" name="btnCreateTag" loxiaType="button" onclick="addTag(this);">'+"选择"+'</button></div>';
				var ids = $j("#tbl-tag-query").jqGrid('getDataIDs');
				for(var i=0;i < ids.length;i++){
					$j("#tbl-tag-query").jqGrid('setRowData',ids[i],{"btnCreateTag":btn1});
				}
				loxia.initContext($j(this));
			}
		});
		
		//查询
		$j("#searchTag").click(function(){
				var postData = loxia._ajaxFormToObj("form_query_Tag");
				$j("#tbl-tag-query").jqGrid('setGridParam',{
					url:window.$j("body").attr("contextpath")+"/findSkuTag.json",
					postData:postData,
					page:1
				}).trigger("reloadGrid");
		});
		
		//商品标签重置
		$j("#resetTag").click(function(){
			$j("#form_query_Tag input").val("");
		});
	});
	
	
	//下一步
	$j("#next").click(function(){ 
		//下一步之前数据校验
		var RoleCode = $j("#RoleCode").val();
		var RoleName = $j("#RoleName").val();
		var SelStatusOpc = $j("#SelStatusOpc").val();
		var RoleSort = $j("#RoleSort").val();
		var temp=$j('input:radio[name="myId"]:checked').val();
		if(RoleCode.replace(/[ ]/g,"") == ''){
			jumbo.showMsg("规则编码不能为空！");
			return;
		}
		if(RoleName.replace(/[ ]/g,"") == ''){
			jumbo.showMsg("规则名称不能为空！");
			return;
		}
		if(temp == null){
			jumbo.showMsg("默认物流不能为空！");
			return;
		}
		var xzwl =  document.getElementsByName("myId");//选择渠道radio
		var ids = $j("#tbl-transport-service").jqGrid('getDataIDs');
		var TransSerId="";
		for(var i=0;i<ids.length;i++){
			if(xzwl[i].checked){
				TransSerId = ids[i];
			}
		}
		if(SelStatusOpc == ""){
			SelStatusOpc = 1;
		}
		var oneDiv =document.getElementById("one").style.display;
		var twoDiv =document.getElementById("two").style.display;
		if(oneDiv == ""  && isAdd != "3" && isAdd != "4"){
			if(RoleSort >= 9999){
				jumbo.showMsg("优先级不能大于等于9999！");
				$j("#RoleSort").attr("value","");
				return;
			}
			
			if(RoleSort ==""){
				jumbo.showMsg("优先级不能为空！");
				return;
			}
			if(isAdd == 1){//新增
				var ids2 = $j("#tbl-transRole-list").jqGrid('getDataIDs');
				for(var i=0;i<ids2.length;i++){
					var sorts= $j("#tbl-transRole-list").getCell(ids2[i],"sort");
					if(sorts == RoleSort){
						jumbo.showMsg("优先级不能重复！");
						return;
					}
				}
			}else{
				var ids2 = $j("#tbl-transRole-list").jqGrid('getDataIDs');
				var selectedId = $j("#tbl-transRole-list").jqGrid("getGridParam","selrow");//物流规则ID
				var sortId= $j("#tbl-transRole-list").getCell(selectedId,"sort");//优先级
				for(var i=0;i<ids2.length;i++){
					var sorts= $j("#tbl-transRole-list").getCell(ids2[i],"sort");
					if(sorts == RoleSort && sorts != sortId){
						jumbo.showMsg("优先级不能重复！");
						return;
					}
				}
			}
			document.getElementById('one').style.display = "none";
			document.getElementById('two').style.display = "";
			$j("#createTwoChannelCode").html(tempCode);
			$j("#createTwoChannelName").html(tempName);
			$j("#TwoRoleCode").html(RoleCode);
			$j("#TwoRoleName").html(RoleName);
			$j("#next").text("保存");
			$j("#next").css("width","70px"); 
			$j("#cancel").text("返回");
			$j("#cancel").css("width","70px"); 
			$j("#transAreaLabel").html("--请选择--");
			document.getElementById("transAreaLabel").style.color="#ff0000";
			
			//加载查询品牌
			var rsBrand = loxia.syncXhr($j("body").attr("contextpath") + "/findAllBrand.json",{});
			$j("#brandSelect").append("<option></option>");
			for(var i = 0 ; i < rsBrand.brandlist.length ; i++){
				$j("#brandSelect").append("<option>"+rsBrand.brandlist[i].name+"</option>");
			}
		    $j("#brandSelect").flexselect();
		    $j("#brandSelect").val("");
		    
			//加载查询分类
			var rsCategories = loxia.syncXhr($j("body").attr("contextpath") + "/findAllCategories.json",{});
			$j("#categoSelect").append("<option></option>");
			for(var j = 0 ; j < rsCategories.categorieslist.length ; j++){
				$j("#categoSelect").append("<option value="+rsCategories.categorieslist[j].id+">"+rsCategories.categorieslist[j].skuCategoriesName+"</option>");
			}
		    $j("#categoSelect").flexselect();
		    $j("#categoSelect").val("");
		    
		    //商品表map数据插入 -- 用于校验重复数据不能添加
		    var skuIds = $j("#tbl-sku-list").jqGrid('getDataIDs');
			for(var i=0;i<skuIds.length;i++){
				updatehashMap.put(skuIds[i], "temp");
			}
			
		    //商品表map数据插入 -- 用于校验重复数据不能添加
		    var skuCateIds = $j("#tbl-sku-categories").jqGrid('getDataIDs');
			for(var i=0;i<skuCateIds.length;i++){
				updatehashMap2.put(skuCateIds[i], "temp");
			}
			
		    //商品表map数据插入 -- 用于校验重复数据不能添加
		    var skuTagIds = $j("#tbl-sku-tag").jqGrid('getDataIDs');
			for(var i=0;i<skuTagIds.length;i++){
				updatehashMap3.put(skuTagIds[i], "temp");
			}
		}else if(twoDiv == ""  && isAdd != "3" && isAdd != "4"){
			var maxAmount = $j("#maxAmount").val();
			var minAmount = $j("#minAmount").val();
			var maxWeight = $j("#maxWeight").val();
			var minWeight = $j("#minWeight").val();
			var timeTypes = $j("#timeTypes").val();

			var removeKeyword = $j("#removeKeyword").val();
			var isCod=$j("#isCod").val();
			
			if(parseFloat(minAmount) > parseFloat(maxAmount)){
				jumbo.showMsg("订单金额最大值不能小于最小值！");
				return;
			}
			if(parseFloat(minWeight) > parseFloat(maxWeight)){
				jumbo.showMsg("订单重量最大值不能小于最小值！");
				return;
			}
			var transAreaIdLabel = $j("#transAreaIdLabel").html();
			
			var skuId = "";
			var skuCate = "";
			var skuTag = "";
			var whList = "";
			var skuIds =$j("#tbl-sku-list").jqGrid('getDataIDs');
			var skuCateIds =$j("#tbl-sku-categories").jqGrid('getDataIDs');
			var skuTagIds =$j("#tbl-sku-tag").jqGrid('getDataIDs');
			var skuTagIds =$j("#tbl-sku-tag").jqGrid('getDataIDs');
			var whListDate = $j("#tbl-channel-warehouse" ).jqGrid('getGridParam','selarrrow');
			
			//商品ID集合
			for(var i = 0; i < skuIds.length; i++){
				var tempId =  $j("#tbl-sku-list #" + skuIds[i] +" td[aria-describedby='tbl-sku-list_id']").html();
				skuId +=  tempId + "/";
			}
			//商品分类ID集合
			for(var i = 0; i < skuCateIds.length; i++){
				var tempId =  $j("#tbl-sku-categories #" + skuCateIds[i] +" td[aria-describedby='tbl-sku-categories_id']").html();
				skuCate +=  tempId + "/";
			}
			//商品标签ID集合
			for(var i = 0; i < skuTagIds.length; i++){
				var tempId =  $j("#tbl-sku-tag #" + skuTagIds[i] +" td[aria-describedby='tbl-sku-tag_id']").html();
				skuTag +=  tempId + "/";
			}
			//关联仓库
			for(var i = 0; i < whListDate.length; i++){
				whList +=  whListDate[i] + "/";
			}
			
			var pastDate = {};
		    
			var selectedId = $j("#tbl-transRole-list").jqGrid("getGridParam","selrow");//物流规则ID
			var roleDtalId= $j("#tbl-transRole-list").getCell(selectedId,"roleDtalId");
			pastDate["transRole.code"] = RoleCode;//规则编码
			pastDate["transRole.name"] = RoleName;//规则名称
			pastDate["transRole.sort"] = RoleSort;//优先级
			pastDate["transRole.roleStatus"] = SelStatusOpc;//状态
			pastDate["transRole.roleChannelId"] = tempChannId;//渠道ID 
			pastDate["transRole.roleServiceId"] = TransSerId;//物流服务ID
			pastDate["transRole.areaGroupId"] = transAreaIdLabel;//配送范围组ID
			
			pastDate["roleDetail.minAmount"] = minAmount;//订单金额最小值
			pastDate["roleDetail.maxAmount"] = maxAmount;//订单金额最大值
			pastDate["roleDetail.minWeight"] = minWeight;//订单重量最小值
			pastDate["roleDetail.maxWeight"] = maxWeight;//订单重量最大值
			pastDate["isCod"] = isCod;//是否ISCOD订单
			pastDate["roleDetail.timeType"] = timeTypes;//订单时效类型

			
			pastDate["roleDetail.removeKeyword"] = removeKeyword;//订单重量最大值
			
			pastDate["skuList"] = skuId;//商品ID集合
			pastDate["skuCateList"] = skuCate;//商品分类ID集合
			pastDate["sktTagList"] = skuTag;//商品标签ID集合
			pastDate["whList"] = whList;//指定仓库数据
			var rs = null;
			if(isAdd == 1){
				pastDate["isAdd"] = isAdd;//判断新增或修改
				pastDate["roleId"] = 0;//规则ID 无实际意义
				pastDate["roleDtalId"] = 0;//规则明细ID 无实际意义
				rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/saveTransRole.json",pastDate);
			}else{
				//alert(selectedId+"--"+roleDtalId);
//				alert(chanId+"--"+RoleCode+"--"+RoleName+"--"+SelStatusOpc+"--"+"--"+RoleSort+"--"+TransSerId);
//				alert(transAreaIdLabel+"--"+minAmount+"--"+maxAmount);
				pastDate["isAdd"] = isAdd;//判断新增或修改
				pastDate["roleId"] = selectedId;//规则ID
				pastDate["roleDtalId"] = roleDtalId;//规则明细ID
				rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/saveTransRole.json",pastDate);
			}
			
			if(rs && rs.msg == 'success'){
				//执行成功
				jumbo.showMsg("操作成功！");
				//window.location.reload();
				document.getElementById('one').style.display = "none";
				document.getElementById('two').style.display = "none";
				$j("#dialog_addRole").addClass("hidden");
				$j("#bichannel-list-deatil").removeClass("hidden");
				var roleUrls1 = baseUrl + "/findTransRole.json?chanId="+tempChannId;
				$j("#tbl-transRole-list").jqGrid('setGridParam',{url:roleUrls1,datatype: "json",}).trigger("reloadGrid");
				clearMap();
				$j("#RoleCode").attr("value","");
				$j("#RoleName").attr("value","");
				$j("#SelStatusOpc").attr("value","");
				$j("#RoleSort").attr("value","");
				$j("#RoleSort").removeClass("hidden");
				$j("#sortLabel").removeClass("hidden");
				document.getElementById("transAreaLabelCode").innerHTML = "";
				document.getElementById("transAreaLabelName").innerHTML = "";
				document.getElementById("transAreaIdLabel").innerHTML = "";
				document.getElementById("transAreaLabelStatus").innerHTML = "";
				$j("#transAreaLabel").removeClass("hidden");
				$j("#minAmount").attr("value","");
				$j("#maxAmount").attr("value","");
				$j("#minWeight").attr("value","");
				$j("#maxWeight").attr("value","");
				$j("#removeKeyword").attr("value","");
				var ids = $j("#tbl-transport-service").jqGrid('getDataIDs');
				for(var i=0;i<ids.length;i++){
					$j("input[name=myId]:eq("+i+")").attr("checked",false); 
				}
				$j("#tbl-sku-list").clearGridData(false);
				$j("#tbl-sku-categories").clearGridData(false);
				$j("#tbl-sku-tag").clearGridData(false);
			}else{
				if(rs.errMsg != null){
					var msg = rs.errMsg;
					var s = '';
					for(var x in msg){
						s +=msg[x] + '<br/>';
					}
						jumbo.showMsg(s);
					}else{
						jumbo.showMsg(rs.msg);
					}
			}
		}else if(isAdd == "3" ||  isAdd == "4"){
			var pastDate = {};
			pastDate["isAdd"] = isAdd;//判断新增或修改
			pastDate["transRole.code"] = RoleCode;//规则编码
			pastDate["transRole.name"] = RoleName;//规则名称
			pastDate["transRole.roleStatus"] = SelStatusOpc;//状态
			pastDate["transRole.roleChannelId"] = tempChannId;//渠道ID
			pastDate["transRole.roleServiceId"] = TransSerId;//物流服务ID
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/saveDefaultTransRole.json",pastDate);
			if(rs && rs.msg == 'success'){
				//执行成功
				jumbo.showMsg("操作成功！");
				document.getElementById('one').style.display = "none";
				document.getElementById('two').style.display = "none";
				$j("#dialog_addRole").addClass("hidden");
				$j("#bichannel-list-deatil").removeClass("hidden");
				var roleUrls1 = baseUrl + "/findTransRole.json?chanId="+tempChannId;
				$j("#tbl-transRole-list").jqGrid('setGridParam',{url:roleUrls1,datatype: "json",}).trigger("reloadGrid");
				clearMap();
				$j("#RoleCode").attr("value","");
				$j("#RoleName").attr("value","");
				$j("#SelStatusOpc").attr("value","");
				$j("#RoleSort").attr("value","");
				$j("#RoleSort").removeClass("hidden");
				$j("#sortLabel").removeClass("hidden");
				var ids = $j("#tbl-transport-service").jqGrid('getDataIDs');
				for(var i=0;i<ids.length;i++){
					$j("input[name=myId]:eq("+i+")").attr("checked",false); 
				}

			}else{
				if(rs.errMsg != null){
					var msg = rs.errMsg;
					var s = '';
					for(var x in msg){
						s +=msg[x] + '<br/>';
					}
						jumbo.showMsg(s);
					}else{
						jumbo.showMsg(rs.msg);
					}
			}
		}
	});
	
	//编辑渠道规则
	$j("#addDefault").click(function(){ 
		var ids = $j("#tbl-transRole-list").jqGrid('getDataIDs');
		var temp = 0;
		var sortDateId = null;
		for(var i=0;i<ids.length;i++){
			var sorts= $j("#tbl-transRole-list").getCell(ids[i],"sort");
			if(sorts == "9999"){
				temp = 1;//编辑默认规则
				sortDateId = ids[i];
				break;
			}else{
				temp = 0;//新建默认规则
			}
		}
		if(temp == 0){
			isAdd = "3";//默认新建
			$j("#RoleCode").removeAttr("readonly","readonly"); 
			$j("#dialog_addRole").removeClass("hidden");
			$j("#bichannel-list-deatil").addClass("hidden");
			document.getElementById('one').style.display = "";
			$j("#createChannelCode").html(tempCode);
			$j("#createChannelName").html(tempName);
			
			$j("#RoleCode").attr("value","");
			$j("#RoleName").attr("value","");
			$j("#SelStatusOpc").attr("value","");
			$j("#RoleSort").attr("value","");
			var ids = $j("#tbl-transport-service").jqGrid('getDataIDs');
			for(var i=0;i<ids.length;i++){
				$j("input[name=myId]:eq("+i+")").attr("checked",false); 
			}
		}else{//编辑默认规则
			isAdd = "4";
			$j("#RoleCode").attr("readonly","readonly");
			$j("#dialog_addRole").removeClass("hidden");
			$j("#bichannel-list-deatil").addClass("hidden");
			document.getElementById('one').style.display = "";
			var roleCode= $j("#tbl-transRole-list").getCell(sortDateId,"code");//编码
			var roleName= $j("#tbl-transRole-list").getCell(sortDateId,"name");//名称
			var sort= $j("#tbl-transRole-list").getCell(sortDateId,"sort");//优先级
			var roleStatu= $j("#tbl-transRole-list").getCell(sortDateId,"roleStatu");//状态
			var roleServiceId= $j("#tbl-transRole-list").getCell(sortDateId,"roleServiceId");//状态
			$j("#createChannelCode").html(tempCode);
			$j("#createChannelName").html(tempName);
			$j("#RoleCode").val(roleCode);
			$j("#RoleName").val(roleName);
			$j("#RoleSort").val(sort);
			if(roleStatu == '正常'){
				$j("#SelStatusOpc option[value='1']").attr("selected", "selected"); 
			}else{
				$j("#SelStatusOpc option[value='0']").attr("selected", "selected"); 
			}
			var ids = $j("#tbl-transport-service").jqGrid('getDataIDs');
			for(var i=0;i<ids.length;i++){
				if(ids[i] == roleServiceId){
					$j("input[name=myId]:eq("+i+")").attr("checked",'checked'); 
				}
			}
		}
		findChannelWarehouse(tempChannId,(temp==0?"":temp));
		$j("#RoleSort").addClass("hidden");
		$j("#sortLabel").addClass("hidden");
		$j("#next").text("完成");
		$j("#next").css("width","70px");
		
	});
	 //取消
	$j("#cancel").click(function(){
		var oneDiv =document.getElementById("one").style.display;
		var twoDiv =document.getElementById("two").style.display;
		if(oneDiv == ""){
			$j("#dialog_addRole").addClass("hidden");
			$j("#bichannel-list-deatil").removeClass("hidden");
			document.getElementById('one').style.display = "none";
		}else if(twoDiv == ""){
			document.getElementById('two').style.display = "none";
			document.getElementById('one').style.display = "";
			$j("#next").text("下一步");
			$j("#next").css("width","100px"); 
			$j("#cancel").text("取消");
			$j("#cancel").css("width","70px"); 
		}
	});
	
	//编辑渠道规则
	$j("#update").click(function(){ 
		var selectedId = $j("#tbl-transRole-list").jqGrid("getGridParam","selrow");//物流规则ID
		var sort= $j("#tbl-transRole-list").getCell(selectedId,"sort");//优先级
		if(sort == "9999"){
			jumbo.showMsg("默认规则不能编辑，请点击设置默认规则");
			return;
		}
		clearMap();//清空map
		//$j("#RoleCode").attr("disabled","disabled"); 
		$j("#RoleCode").attr("readonly","readonly");
		var roleCode= $j("#tbl-transRole-list").getCell(selectedId,"code");//编码
		var roleName= $j("#tbl-transRole-list").getCell(selectedId,"name");//名称
		var roleStatu= $j("#tbl-transRole-list").getCell(selectedId,"roleStatu");//状态
		var roleServiceId= $j("#tbl-transRole-list").getCell(selectedId,"roleServiceId");//物流服务ID
		var roleDtalId= $j("#tbl-transRole-list").getCell(selectedId,"roleDtalId");//规则明细ID
		var minCount= $j("#tbl-transRole-list").getCell(selectedId,"minCount");//订单金额最小值
		var maxCount= $j("#tbl-transRole-list").getCell(selectedId,"maxCount");//订单金额最大金额
		var timeType= $j("#tbl-transRole-list").getCell(selectedId,"timeType");//明细时效类型
		var minWCount= $j("#tbl-transRole-list").getCell(selectedId,"minWCount");//订单重量最小值
		var maxWCount= $j("#tbl-transRole-list").getCell(selectedId,"maxWCount");//订单重量最大金额
		var areaCode= $j("#tbl-transRole-list").getCell(selectedId,"areaCode");//配送范围编码
		var areaStatus= $j("#tbl-transRole-list").getCell(selectedId,"areaStatus");//配送范围状态
		var areaName= $j("#tbl-transRole-list").getCell(selectedId,"areaName");//配送范围名称
		var areaGroupId= $j("#tbl-transRole-list").getCell(selectedId,"areaGroupId");//配送范围组ID
		var removeKeyword= $j("#tbl-transRole-list").getCell(selectedId,"removeKeyword");//配送范围组ID
		var isCod= $j("#tbl-transRole-list").getCell(selectedId,"isCod");//是否COD订单
		if(selectedId == null){
			jumbo.showMsg("请选择要编辑的主数据");
		}else{
			//alert(selectedId+"--"+roleCode+"--"+roleName+"--"+sort+"--"+roleStatu+"--"+roleServiceId);
			//alert(minCount+"--"+maxCount+"--"+areaCode+"--"+areaName+"--"+areaGroupId);
			//加载物流规则、明细数据
				$j("#RoleSort").removeClass("hidden");
				$j("#sortLabel").removeClass("hidden");
				$j("#dialog_addRole").removeClass("hidden");
				$j("#bichannel-list-deatil").addClass("hidden");
				document.getElementById('one').style.display = "";
				$j("#createChannelCode").html(tempCode);
				$j("#createChannelName").html(tempName);
				$j("#RoleCode").val(roleCode);
				$j("#RoleName").val(roleName);
				$j("#RoleSort").val(sort);
				if(roleStatu == '正常'){
					$j("#SelStatusOpc option[value='1']").attr("selected", "selected"); 
				}else{
					$j("#SelStatusOpc option[value='0']").attr("selected", "selected"); 
				}
				var ids = $j("#tbl-transport-service").jqGrid('getDataIDs');
				for(var i=0;i<ids.length;i++){
					if(ids[i] == roleServiceId){
						$j("input[name=myId]:eq("+i+")").attr("checked",'checked'); 
					}
				}
				isAdd = "0";
				$j("#RoleSort").removeClass("hidden");
				$j("#sortLabel").removeClass("hidden");
				$j("#next").text("下一步");
				$j("#next").css("width","90px"); 
				
				$j("#transAreaIdLabel").html(areaGroupId);
				$j("#transAreaLabelCode").html(areaCode);
				$j("#transAreaLabelName").html(areaName);
				$j("#transAreaLabelStatus").html(areaStatus);
				
				$j("#minAmount").val(minCount);
				$j("#maxAmount").val(maxCount);
				
				$j("#minWeight").val(minWCount);
				$j("#maxWeight").val(maxWCount);
				
				
				$j("#timeTypes").val(timeType);//时效类型

				
				$j("#removeKeyword").val(removeKeyword);
				
				$j("#isCod").val(isCod);
				
				if(areaCode != ""){
					$j("#transAreaLabel").addClass("hidden");
				}else{
					$j("#transAreaLabel").removeClass("hidden");
				}
				
				//加载已选择商品信息
				var urlSku = $j("body").attr("contextpath")+"/findSkuRef.json?roleDtalId="+roleDtalId;
				$j("#tbl-sku-list").jqGrid('setGridParam',{url:urlSku,datatype: "json",}).trigger("reloadGrid");
				
				//加载已选择商品分类信息
				var urlSkuCate = $j("body").attr("contextpath")+"/findSkuCateRef.json?roleDtalId="+roleDtalId;
				$j("#tbl-sku-categories").jqGrid('setGridParam',{url:urlSkuCate,datatype: "json",}).trigger("reloadGrid");
				
				//加载已选择商品标签信息
				var urlSkuTag = $j("body").attr("contextpath")+"/findSkuTagRef.json?roleDtalId="+roleDtalId;
				$j("#tbl-sku-tag").jqGrid('setGridParam',{url:urlSkuTag,datatype: "json",}).trigger("reloadGrid");
				
				findChannelWarehouse(tempChannId,roleDtalId);
		}
	});
	
	
	$j("#tbl-transRole-list").jqGrid({
		colNames: ["ID","时效类型","编码","名称","创建时间","优先级","状态","物流服务ID","规则明细ID","订单金额最小值","订单金额最大值","订单重量最小值","订单重量最大值","范围编码","范围状态","范围名称","范围ID","排除关键字","是否COD订单"],
		colModel: [
							{name:"id", hidden: true,width:30},
							{name:"timeType", hidden: true,width:30},
							{name:"code",index:"code", width:100,resizable:true},
							{name:"name",index:"name",width:100,resizable:true},
							{name:"createTime",index:"createTime",width:150,resizable:true},
							{name:"sort",index:"sort",width:80,resizable:true},
							{name:"roleStatu",index:"roleStatu",width:80,resizable:true},
							{name:"roleServiceId",index:"roleServiceId",width:100,hidden:true,resizable:true},
							{name:"roleDtalId",index:"roleDtalId",width:100,hidden:true,resizable:true},
							{name:"minCount",index:"minCount",width:100,hidden:true,resizable:true},
							{name:"maxCount",index:"maxCount",width:100,hidden:true,resizable:true},
							{name:"minWCount",index:"minCount",width:100,hidden:true,resizable:true},
							{name:"maxWCount",index:"maxCount",width:100,hidden:true,resizable:true},
							{name:"areaCode",index:"areaCode",width:100,hidden:true,resizable:true},
							{name:"areaStatus",index:"areaStatus",width:100,hidden:true,resizable:true},
							{name:"areaName",index:"areaName",width:100,hidden:true,resizable:true},
							{name:"areaGroupId",index:"areaGroupId",width:100,hidden:true,resizable:true},
							{name:"removeKeyword",index:"removeKeyword",width:100,hidden:true,resizable:true},
							{name:"isCod",index:"isCod",width:100,hidden:true,resizable:true}
							
				 		],
		caption: "渠道物流规则",
	   	sortname: 'id',
    	multiselect: false,
    	height:"auto",
    	width:500,
	   	sortorder: "asc",
		shrinkToFit:true,
		viewrecords: true,
		rownumbers:false,
		//pager:"#pager2",
		//rowNum: jumbo.getPageSize(),
		//rowList:jumbo.getPageSizeList(),
		jsonReader: {repeatitems : false, id: "0" }
	});
	
	
	//物流服务表
	$j("#tbl-transport-service").jqGrid({
		colNames: ["物流编码","名称","服务类型","时效类型","选择"],
		colModel: [
							{name: "id", index:"id",hidden: true,width:80,resizable:true},
							{name:"name",index:"name", width:90,resizable:true},
							{name:"serviceType",index:"serviceType",width:90,resizable:true},
							{name:"timeTypes",index:"timeTypes",width:90,resizable:true},
							 { 
				                name: 'MY_ID',
				                index: 'MY_ID',
				                sortable: false,
				                align:'center',
				                width: 120,
				                formatter:function(){
				                	 return "<input type='radio' name='myId' id = 'myId'/>";
				                }
				            }
				 		],
     	caption: "选择物流服务",
    	sortname: 'id',
    	sortorder: "desc",
    	multiselect: false,
    	height:"auto",
    	width:500,
    	rowNum: -1,
    	rownumbers:true,
		jsonReader: {repeatitems : false, id: "0" }
	});
	
	
	//渠道列表
	$j("#tbl-bichannel-list").jqGrid({
		url:baseUrl + "/findBiChannelListByTypeAndExpT.json",
		datatype: "json",
		colNames: ["ID","CID","渠道编码","渠道名称","客户名称","发货地址"],
		colModel: [
							{name:"id", hidden:true},
							{name:"customerId",index:"customerId",hidden: true},
							{name:"code",index:"code", width:280,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, resizable:true},
							{name:"name",index:"name",width:280,resizable:true},
							{name:"cName",index:"cName",width:145,resizable:true},
							{name:"address",index:"address",width:300,resizable:true,hidden: true}
				 		],
		caption: "渠道信息列表",
	   	sortname: 'id',
	   	multiselect: false,
		sortorder: "desc",
		pager:"#pager",
		width:750,
	   	shrinkToFit:false,
		height:"auto",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		viewrecords: true,
	   	rownumbers:true,
		jsonReader: {repeatitems : false, id: "0"}
	});
	
	//渠道快递规则状态变更列表
	$j("#tbl-TransRoleAccord-list").jqGrid({
		url:baseUrl + "/findTransRoleAccord.json",
		datatype: "json",
		colNames: ["ID","规则编码","渠道名称","执行时间","是否启用","是否启用Int","优先级","状态","状态Int","创建时间","创建人","最后执行时间","编辑(新建)"],
		colModel: [
							{name:"id", hidden:true},
							{name:"roleCode",index:"roleCode", width:100,resizable:true},
							{name:"channelName",index:"channelName",width:180,resizable:true},
							{name:"changeTime",index:"changeTime",width:120,resizable:true},
							{name:"strRoleIsAvailable",index:"strRoleIsAvailable",width:60,resizable:true},
							{name:"intRoleIsAvailable",index:"intRoleIsAvailable",hidden:true},
							{name:"priority",index:"priority",width:80,resizable:true},
							{name:"strChannelTransStatus",index:"strChannelTransStatus",width:80,resizable:true},
							{name:"intChannelTransStatus",index:"intChannelTransStatus",hidden:true},
							{name:"createTime",index:"createTime",width:120,resizable:true},
							{name:"createName",index:"createName",width:80,resizable:true},
							{name:"lastOperation",index:"lastOperation",width:120,resizable:true},
							{name: "btnEdit", width: 80, resizable: true},
							{name:"btnOperation",index:"sku.btnOperation",width:80,resizable:true}
				 		],
		caption: "渠道快递规则状态变更列表",
	   	sortname: 'changeTime',
	   	multiselect: false,
		sortorder: "desc",
		pager:"#pager5",
	   	shrinkToFit:false,
		height:"auto",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		viewrecords: true,
	   	rownumbers:true,
		jsonReader: {repeatitems : false, id: "0"},
		gridComplete: function(){
			var btn1 = '<div><button type="button" style="width:80px;" class="confirm" name="btnEdit" loxiaType="button" onclick="editTransRoleAccord(this,event);">'+"编辑"+'</button></div>';
			var ids = $j("#tbl-TransRoleAccord-list").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var idData = $j("#tbl-TransRoleAccord-list").jqGrid('getRowData',ids[i]);
				if(idData["intChannelTransStatus"] == 1){
					$j("#tbl-TransRoleAccord-list").jqGrid('setRowData',ids[i],{"btnEdit":btn1});
				}
			}
			loxia.initContext($j(this));
		}
	});
	$j("#btnEdit_tab").dialog({title: "渠道快递规则状态变更修改", modal:true, autoOpen: false, width:540});
	
	$j("#import").click(function(){
		if(!/^.*\.xls$/.test($j("#file1").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));
			return;
		}
		loxia.lockPage();
		$j("#importFormFile").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importTransRoleAccord.do"));
		loxia.submitForm("importFormFile");
		setTimeout(function(){
			$j('#queryTransRoleAccord').trigger("click");
			$j("#file").val("");
		},2000);
		loxia.unlockPage();
	}); 

	
	//商品表
	$j("#tbl-sku-list").jqGrid({
		datatype: "json",
		colNames: ["ID","编码","条码","商品名称","品牌对接码","品牌","操作"],
		colModel: [
							{name:"id",index:"id", hidden: true},
							{name:"code",index:"code", width:120,resizable:true},
							{name:"barCode",index:"barCode",width:100,resizable:true},
							{name:"name",index:"name",width:150,resizable:true},
							{name:"extensionCode1",index:"extensionCode1",width:130,resizable:true},
							{name:"brandName",index:"brandName",width:100,resizable:true},
							{name:"btnOperation",index:"sku.btnOperation",width:80,resizable:true}
				 		],
		caption: "商品列表",
	   	sortname: 'id',
	   	multiselect: false,
		sortorder: "desc",
		width:710,
	   	shrinkToFit:false,
		height:"auto",
		viewrecords: true,
	   	rownumbers:false,
		jsonReader: {repeatitems : false, id: "0"},
		gridComplete: function(){
			var btn1 = '<div><button type="button" style="width:80px;" class="confirm" name="btnOperation" loxiaType="button" onclick="deleteSku(this);">'+"删除	"+'</button></div>';
			var ids = $j("#tbl-sku-list").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				$j("#tbl-sku-list").jqGrid('setRowData',ids[i],{"btnOperation":btn1});
			}
			loxia.initContext($j(this));
		}
	});
	
	
	// 仓库 
	$j("#tbl-channel-warehouse").jqGrid({
		colNames: ["ID","isRef","仓库编码","名称"],
		colModel: [
							{name:"id", index:"id",hidden: true},
							//{name:"isRef", index:"isRef",hidden: true},
							{name:"isRef", index:"isRef",width:90,resizable:true},
							{name:"code",index:"code", width:90,resizable:true},
							{name:"name",index:"name", width:90,resizable:true}
				 		],
     	caption: "渠道绑定仓库列表",
    	sortname: 'id',
    	sortorder: "desc",
    	multiselect: true,
    	height:"auto",
    	width:500,
    	rowNum: -1,
    	rownumbers:true,
		jsonReader: {repeatitems : false, id: "0" },
		gridComplete: function(){
			var ids = $j("#tbl-channel-warehouse").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var rowData = $j("#tbl-channel-warehouse").jqGrid('getRowData',ids[i]);
				if(rowData.isRef == 1){
					$j("#tbl-channel-warehouse").jqGrid('setSelection',ids[i]);
				}
			}
			loxia.initContext($j(this));
		}
	});
	
	//商品分类表
	$j("#tbl-sku-categories").jqGrid({
		datatype: "json",
		colNames: ["ID","分类名称","操作"],
		colModel: [
							{name:"id", hidden: true},
							{name:"skuCategoriesName",index:"skuCategoriesName", width:180,resizable:true},
							{name:"btnDeleteCate",index:"btnDeleteCate",width:70,resizable:true}
				 		],
		caption: "商品分类",
	   	sortname: 'id',
	   	multiselect: false,
		sortorder: "desc",
		width:290,
	   	shrinkToFit:false,
		height:"auto",
		viewrecords: true,
	   	rownumbers:true,
		jsonReader: {repeatitems : false, id: "0"},
		gridComplete: function(){
			var btn1 = '<div><button type="button" style="width:70px;" class="confirm" name="btnDeleteCate" loxiaType="button" onclick="deleteCategories(this);">'+"删除"+'</button></div>';
			var ids = $j("#tbl-sku-categories").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				$j("#tbl-sku-categories").jqGrid('setRowData',ids[i],{"btnDeleteCate":btn1});
			}
			loxia.initContext($j(this));
		}
	});
	

	//商品标签表
	$j("#tbl-sku-tag").jqGrid({
		datatype: "json",
		colNames: ["ID","编码","名称","类型","状态","操作"],
		colModel: [
							{name:"id", hidden: true},
							{name:"code",index:"code", width:95,resizable:true},
							{name:"name",index:"name", width:95,resizable:true},
							{name:"tagType",index:"tagType", width:90,resizable:true},
							{name:"tagStatus",index:"tagStatus", width:90,resizable:true},
							{name:"btnDeleteTag",index:"btnDeleteTag",width:80,resizable:true}
				 		],
		caption: "商品标签",
	   	sortname: 'id',
	   	multiselect: false,
		sortorder: "desc",
		width:480,
	   	shrinkToFit:false,
		height:"auto",
		viewrecords: true,
	   	rownumbers:false,
		jsonReader: {repeatitems : false, id: "0"},
		gridComplete: function(){
			var btn1 = '<div><button type="button" style="width:80px;" class="confirm" name="btnDeleteTag" loxiaType="button" onclick="deleteTag(this);">'+"删除"+'</button></div>';
			var ids = $j("#tbl-sku-tag").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				$j("#tbl-sku-tag").jqGrid('setRowData',ids[i],{"btnDeleteTag":btn1});
			}
			loxia.initContext($j(this));
		}
	});
	
	//查询
	$j("#search").click(function(){
			var postData = loxia._ajaxFormToObj("channelForm");
			$j("#tbl-bichannel-list").jqGrid('setGridParam',{
				url:window.$j("body").attr("contextpath")+"/findBiChannelListByTypeAndExpT.json",
				postData:postData,
				page:1
			}).trigger("reloadGrid");
	});
	
	//渠道快递规则状态变更查询
	$j("#queryTransRoleAccord").click(function(){
			var postData = loxia._ajaxFormToObj("transRoleAccordForm");
			$j("#tbl-TransRoleAccord-list").jqGrid('setGridParam',{
				url:window.$j("body").attr("contextpath")+"/findTransRoleAccord.json",
				postData:postData,
				page:1
			}).trigger("reloadGrid");
	});
	
	$j("#resetTransRoleAccord").click(function(){
		$j("#transRoleAccordForm input").val("");
		$j("#intStatus option:first").attr("selected",true);
	});
	
	$j("#back").click(function(){
//		document.getElementById('one').style.display = "none";
//		document.getElementById('two').style.display = "none";
		$j("#bichannel-list").removeClass("hidden");
		$j("#bichannel-list-deatil").addClass("hidden");
		//window.location.reload();
		clearMap();
		$j("#channelId").val();
		$j("#includeKeyWord").attr("value","");
		$j("#RoleCode").attr("value","");
		$j("#RoleName").attr("value","");
		$j("#SelStatusOpc").attr("value","");
		$j("#RoleSort").attr("value","");
		$j("#RoleSort").removeClass("hidden");
		$j("#sortLabel").removeClass("hidden");
		document.getElementById("transAreaLabelCode").innerHTML = "";
		document.getElementById("transAreaLabelName").innerHTML = "";
		document.getElementById("transAreaIdLabel").innerHTML = "";
		document.getElementById("transAreaLabelStatus").innerHTML = "";
		$j("#transAreaLabel").removeClass("hidden");
		$j("#minAmount").attr("value","");
		$j("#maxAmount").attr("value","");
		$j("#minWeight").attr("value","");
		$j("#maxWeight").attr("value","");
		$j("#removeKeyword").attr("value","");
		var ids = $j("#tbl-transport-service").jqGrid('getDataIDs');
		for(var i=0;i<ids.length;i++){
			$j("input[name=myId]:eq("+i+")").attr("checked",false); 
		}
		$j("#tbl-sku-list").clearGridData(false);
		$j("#tbl-sku-categories").clearGridData(false);
		$j("#tbl-sku-tag").clearGridData(false);
	});	
	//加载EMS关键字
	/*var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/queryKeyWord.json");
	if(rs && rs.msg == 'success'){
		var emskey = rs.key;
		var inClude = emskey.substring(0,emskey.indexOf("/"));
		var exClude = emskey.substring(emskey.indexOf("/")+1,emskey.length);
		$j("#includeKeyWord").attr("value",inClude);
		$j("#exIncludeKeyWord").attr("value",exClude);
	}else{
		if(rs.errMsg != null){
			jumbo.showMsg(rs.errMsg);
		}
	}*/
});

function findChannelWarehouse(chanId,roleDtalId){
	
	var url = $j("body").attr("contextpath")+"/findTransRoleWH.json?chanId="+chanId;
	if(roleDtalId != "" && roleDtalId != null){
		url = url +"&roleDtalId="+roleDtalId;
	}
	$j("#tbl-channel-warehouse").jqGrid('setGridParam',{url:url,datatype: "json",}).trigger("reloadGrid");
}
	
//查询商品
function bindTable(){
	jumbo.bindTableExportBtn("tbl-commodity-query",{}, $j("body").attr("contextpath")+"/findSku.json",loxia._ajaxFormToObj("form_query"),isSelBrand);
}

//验证品牌
function isSelBrand(){
	var brandname = $j("#brandSelect").val(); 
	if(brandname == ''){
		jumbo.showMsg("必须选择品牌");
		return false;
	}else{
		return true;
	}
}


//自定义HashMap
function HashMap(){ 
	this.map = {};
} 
HashMap.prototype = {
		put : function(key , value){
			this.map[key] = value;
		},
		get : function(key){
			if(this.map.hasOwnProperty(key)){
				return this.map[key];
			}
			return null;
		}, 
		remove : function(key){
			if(this.map.hasOwnProperty(key)){
				return delete this.map[key];
			}
			return false;
		}, 
		removeAll : function(){
			this.map = {};
		},
		keySet : function(){
			var _keys = [];
			for(var i in this.map){
				_keys.push(i);
			} 
			return _keys;
		}
	};
HashMap.prototype.constructor = HashMap;  

//商品维护
var hashMap = new HashMap();
var hashMapCatego = new HashMap();
var hashMapTag = new HashMap();
var updatehashMap = new HashMap();//存放修改 商品信息
var updatehashMap2 = new HashMap();//存放修改 商品分类信息
var updatehashMap3 = new HashMap();//存放修改 商品标签信息
function addSku(tag){
	var id = $j(tag).parents("tr").attr("id");
	var data = $j("#tbl-commodity-query").jqGrid("getRowData",id);
	var obj = {
			id:id,
			code:data["code"],
			barCode:data["barCode"],
			name:data["name"],
			extensionCode1:data["extensionCode1"],
			brandName:data["brandName"]
	};
	if(hashMap.get(obj.id) == null && updatehashMap.get(obj.id) == null){
		$j("#dialog_addSku").dialog("close");
		$j("#dialog_addSku").addClass("hidden");
		hashMap.put(obj.id, obj.name);
		updatehashMap.put(obj.id,obj.name);
		copySkuRow(obj);
	}else{
		jumbo.showMsg("该商品已添加！");
	}
}

//商品标签维护
function addTag(tag){
	var id = $j(tag).parents("tr").attr("id");
	var data = $j("#tbl-tag-query").jqGrid("getRowData",id);
	var obj = {
			id:id,
			code:data["code"],
			name:data["name"],
			tagStatus:data["tagStatus"],
			tagType:data["tagType"]
	};
	if(hashMapTag.get(obj.id) == null  && updatehashMap3.get(obj.id) == null){
		$j("#dialog_addTag").dialog("close");
		//$j("#dialog_addTag").addClass("hidden");
		hashMapTag.put(obj.id, obj.name);
		updatehashMap3.put(obj.id, obj.name);
		copyTagRow(obj);
	}else{
		jumbo.showMsg("该标签已添加！");
	}
}

//复制商品标签信息
function copyTagRow(obj){
	var ids = $j("#tbl-sku-tag").jqGrid('getDataIDs');   
	var $firstTrRole = $j("#tbl-sku-tag").find("tr").eq(1).attr("role");  
	var rowid = $firstTrRole == "row" ? Math.max.apply(Math,ids):0;  
	var newrowid = parseInt(rowid)+1;
	$j("#tbl-sku-tag").jqGrid("addRowData", newrowid, obj, "last");//将新添加的行插入到最后一列
}

//删除商品标签信息
function deleteTag(tag){
	var id = $j(tag).parents("tr").attr("id");
	var ids= $j("#tbl-sku-tag").getCell(id,"id");
	hashMapTag.remove(ids);
	updatehashMap3.remove(ids);
	$j("#tbl-sku-tag").find("tr[id="+id+"]").remove();
	//$j("#tbl-sku-list").trigger("reloadGrid"); 
}

//复制商品信息
function copySkuRow(obj){
	var ids = $j("#tbl-sku-list").jqGrid('getDataIDs');   
	var $firstTrRole = $j("#tbl-sku-list").find("tr").eq(1).attr("role");  
	var rowid = $firstTrRole == "row" ? Math.max.apply(Math,ids):0;  
	var newrowid = parseInt(rowid)+1;
	$j("#tbl-sku-list").jqGrid("addRowData", newrowid, obj, "last");//将新添加的行插入到最后一列
}

//删除商品信息
function deleteSku(tag){
	var id = $j(tag).parents("tr").attr("id");
	var ids= $j("#tbl-sku-list").getCell(id,"id");
	hashMap.remove(ids);
	updatehashMap.remove(ids);
	$j("#tbl-sku-list").find("tr[id="+id+"]").remove();
	//$j("#tbl-sku-list").trigger("reloadGrid"); 
}

//复制商品分类
function copyCategories(obj){
	var ids = $j("#tbl-sku-categories").jqGrid('getDataIDs');   
	var $firstTrRole = $j("#tbl-sku-categories").find("tr").eq(1).attr("role");  
	var rowid = $firstTrRole == "row" ? Math.max.apply(Math,ids):0;  
	var newrowid = parseInt(rowid)+1;
	$j("#tbl-sku-categories").jqGrid("addRowData", newrowid, obj, "last");//将新添加的行插入到最后一列
}

//删除商品分类
function deleteCategories(tag){
	var id = $j(tag).parents("tr").attr("id");
	var ids= $j("#tbl-sku-categories").getCell(id,"id");
	hashMapCatego.remove(ids);
	updatehashMap2.remove(ids);
	$j("#tbl-sku-categories").find("tr[id="+id+"]").remove();
	//$j("#tbl-sku-list").trigger("reloadGrid"); 
}


//复制商品标签信息
function copyTag(obj){
	var ids = $j("#tbl-sku-list").jqGrid('getDataIDs');   
	var $firstTrRole = $j("#tbl-sku-list").find("tr").eq(1).attr("role");  
	var rowid = $firstTrRole == "row" ? Math.max.apply(Math,ids):0;  
	var newrowid = parseInt(rowid)+1;
	$j("#tbl-sku-list").jqGrid("addRowData", newrowid, obj, "last");//将新添加的行插入到最后一列
}

//编辑配送范围
function editAreas(tag){
	document.getElementById("editOneTable").style.display = "none";
	document.getElementById("editTwoTable").style.display = "";
	var id = $j(tag).parents("tr").attr("id");
	tempAreaId = id;
	var data = $j("#tbl-edit-transArea-list").jqGrid("getRowData",id);
	if(data["areaStatus"] == "正常"){
		$j("#SelEditStatusOpc option[value='1']").attr("selected", "selected"); 
	}else{
		$j("#SelEditStatusOpc option[value='2']").attr("selected", "selected"); 
	}
	$j("#AreaEditCode").attr("disabled","disabled");    
	$j("#AreaEditCode").val(data["code"]);
	$j("#AreaEditName").val(data["name"]);
}
//确认 选择配送范围
function sureArea(tag){
	var id = $j(tag).parents("tr").attr("id");
	var data = $j("#tbl-transArea-list").jqGrid("getRowData",id);
	$j("#transAreaLabel").addClass("hidden");
	$j("#transAreaLabelCode").html(data["code"]+"   ");
	$j("#transAreaLabelName").html(data["name"]);
	$j("#transAreaIdLabel").html(id);
	$j("#transAreaLabelStatus").html(data["areaStatus"]);
	$j("#dialog_chooseArea").dialog("close");
}

//导出配送范围
function expArea(tag){
	var id = $j(tag).parents("tr").attr("id");
	var data = $j("#tbl-transArea-list").jqGrid("getRowData",id);
	var areaName = data["name"]+"_明细";
	var iframe = document.createElement("iframe");
	iframe.src = loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/expTransAreaDetail.do?areaId="+id+"&areaName="+areaName);
	iframe.style.display = "none";
	$j("#upload").append($j(iframe));
}

//清空map
function clearMap(){
	hashMap.removeAll(); 
	hashMapCatego.removeAll(); 
	hashMapTag.removeAll(); 
	updatehashMap.removeAll(); 
	updatehashMap2.removeAll();  
	updatehashMap3.removeAll(); 
}

function editTransRoleAccord(tag,event){
	var id = $j(tag).parents("tr").attr("id");
	var idData = $j("#tbl-TransRoleAccord-list").jqGrid('getRowData',id);
	$j("#editChannelCode").val(idData["roleCode"]);
	$j("#editChannelName").val(idData["channelName"]);
	$j("#changeTime").val(idData["changeTime"]);
	$j("#roleIsAvailable").val(idData["intRoleIsAvailable"]);
	$j("#priority").val(idData["priority"]);
	$j("#strChannelTransStatus").val(idData["intChannelTransStatus"]);
	$j("#editCreateName").val(idData["createName"]);
	$j("#editLastOperation").val(idData["lastOperation"]);
	$j("#transRoleAccordId").val(idData["id"]);
	$j("#btnEdit_tab").dialog("open");
}



