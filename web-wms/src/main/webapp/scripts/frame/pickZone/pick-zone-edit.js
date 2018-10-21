$j.extend(loxia.regional['zh-CN'],{
	ENTITY_EXCELFILE : "正确的excel导入文件",
	PICKZONE_EDIT : "拣货区域定义列表",
	PICKZONE_CODE : "拣货区域编码",
	PICKZONE_NAME : "拣货区域名称",
	EDIT : "编辑",
	REMOVE : "删除",
	LOCATION : "库位",
	DISTRICT : "库区",
	SORT : "拣货顺序",
	CREATE_TIME : "创建时间",
	CREATOR : "创建人",
	PICKZONE_LIST : "查询列表结果"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

function removePickZone(obj){
	if(confirm("确定删除选中的拣货区域?删除拣货区域后库位上的拣货区域也会被删除！")){
		
		
		var id = $j(obj).parents("tr").attr("id");
		var flag = loxia.syncXhrPost($j("body").attr("contextpath") + "/deletePickZone.json",{"id":id});
		
		if(flag["flag"]=="success"){
			jumbo.showMsg("删除成功！");
		}else if(flag["flag"]=="useing"){
			jumbo.showMsg("此拣货区域有作业单正在占用，不能删除！");
		}else{
			jumbo.showMsg("删除失败！");
		}
		$j("#btn-query").click();
	}
}
function edit(obj){
	var row=$j(obj).parent().parent().attr("id"),pl=$j("#tbl-waittingList").jqGrid("getRowData",row);
	var id = $j(obj).parents("tr").attr("id");
	$j("#zoneSelectedId").val(id);
	$j("#zoneNameEdit").dialog("open");
	$j("#zoneNewName").val(pl["name"]);
	$j("#whZoonIdS").val(pl["whZoonId"]);
	
	//$j("#btn-name-confirm").click();
}


function initDistrict(){
	var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/findDistrictList.json"));
	for(var idx in rs){
		$j("<option value='" + rs[idx].id + "'>"+ rs[idx].code +"</option>").appendTo($j("#district"));
	}
}

function initPickZone(){
	var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/findPickZoneInfo.json"));
	for(var idx in rs){
		$j("<option value='" + rs[idx].name + "'>"+ rs[idx].name +"</option>").appendTo($j("#pickZoneName"));
		$j("<option value='" + rs[idx].code + "'>"+ rs[idx].code +"</option>").appendTo($j("#pickZoneCode"));
	}
}

$j(document).ready(function (){
	
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/auto/findZoonByOuId.do");
	for(var idx in result){
			$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#whZoonId"));
			$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#whZoonIdS"));
	}
	
	$j("#tabs").tabs();
	$j("#showErrorDialog").dialog({title: "错误信息", modal:true, autoOpen: false, width: 400, height: 300});
	$j("#showOverrideDialog").dialog({title: "导入重复信息", modal:true, autoOpen: false, width: 400, height: 300});
	initDistrict();
	initPickZone();
	var baseUrl = $j("body").attr("contextpath");
	$j("#zoneNameEdit").dialog({title: "区域名字修改", modal:true, autoOpen: false, width: 520});
	$j("#tbl-waittingList").jqGrid({
		url:baseUrl + "/findPickZoneList.json",
		datatype: "json",
		colNames: ["ID",i18("PICKZONE_CODE"),i18("PICKZONE_NAME"),"仓库区域ID","仓库区域编码","仓库区域名称",i18("EDIT"),i18("REMOVE")],
		colModel: [
				   {name: "id", index: "id", hidden: true},
		           {name: "code", index: "code", width:150,resizable:true},    
		           {name: "name", index: "name", width: 150, resizable: true},
		           {name: "whZoonId", index: "whZoonId", width: 150, resizable: true,hidden: true},
		           {name: "whZoonCode", index: "whZoonCode", width: 150, resizable: true,hidden: true},
		           {name: "whZoonName", index: "whZoonName", width: 150, resizable: true},
		           {name: "edit", width: 150, formatter:"buttonFmatter",sortable:false, formatoptions:{"buttons":{label:"编辑", onclick:"edit(this)"}}},
		           {name: "remove", width: 150, formatter:"buttonFmatter",sortable:false, formatoptions:{"buttons":{label:"删除", onclick:"removePickZone(this)"}}},
		           ],
		caption: i18("PICKZONE_EDIT"),// 待配货清单列表
		pager:"#pager_query",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	sortname: 'code',
	   	height:"auto",
	    multiselect: false,
	    rownumbers: true,
	    viewrecords: true,
		sortorder: "asc",
		jsonReader: { repeatitems : false, id: "0" },
	});
	
	$j("#tbl-pickZoneInfoList").jqGrid({
//		url:baseUrl + "/findPickZoneInfoList.json",
		datatype: "json",
		colNames: ["ID",i18("LOCATION"),i18("DISTRICT"),i18("PICKZONE_NAME"),i18("PICKZONE_CODE"),i18("SORT"),i18("CREATE_TIME"),i18("CREATOR")],
		colModel: [
				   {name: "locationId", index: "locationId", hidden: true},
				   {name: "location", index: "location", width:150,resizable:true},  
		           {name: "district", index: "district", width:150,resizable:true},    
		           {name: "name", index: "name", width: 150, resizable: true},
		           {name: "code", index: "code", width:150,resizable:true},  
		           {name: "sort", index: "sort", width:150,resizable:true}, 
		           {name: "createTime", index: "createTime", width:150,resizable:true}, 
		           {name: "createUser", index: "createUser", width:150,resizable:true}, 
		           ],
		caption: i18("PICKZONE_LIST"),// 待配货清单列表
		pager:"#pager_query1",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	sortname: 'locationId',
	   	height:"auto",
	    multiselect: false,
	    rownumbers: true,
	    viewrecords: true,
		sortorder: "desc",
		jsonReader: { repeatitems : false, id: "0" },
	});
	
	//查询拣货区域
	$j("#btn-query").click(function(){
		var postData = {};
			postData["code"]=$j("#code").val();
			postData["name"]=$j("#name").val();
		$j("#tbl-waittingList").jqGrid('setGridParam',{
			url:baseUrl+"/findPickZoneList.json",
			postData:postData,
			page:1
		}).trigger("reloadGrid");
		$j("#query-form input").val("");
	});
	
	//添加区域
	$j("#btn-add").click(function(){
		var codeReg = /^[a-zA-Z0-9_]{1,}$/;
		var postData = {};
		postData["code"]=$j("#code").val();
		if(""==$j("#code").val()){
			return false;
		}
		if(!postData["code"].match(codeReg)){
			jumbo.showMsg("拣货区域编码只能为字母, 数字, 下划线");
			return false;
		}
		postData["name"]=$j("#name").val();
		if(""==$j("#name").val()){
			return false;
		}
		postData["whZoonId"]=$j("#whZoonId").val();
		var flag = loxia.syncXhrPost(baseUrl + "/addPickZone.json",postData);
		if(flag["flag"]=="success"){
			jumbo.showMsg("添加成功！");
		}else if(flag["flag"]=="exist"){
			jumbo.showMsg("区域已存在！");
		}else{
			jumbo.showMsg("添加失败！");
		}
		$j("#query-form input").val("");
		$j("#btn-query").click();
	});
	
	//确认修改区域名称
	$j("#btn-name-confirm").click(function(){
		var id = $j("#zoneSelectedId").val();
		var newName = $j("#zoneNewName").val();
		var whZoonId = $j("#whZoonIdS").val();
		if("" == newName && ""==whZoonId){
			return false;
		}
		var flag = loxia.syncXhrPost($j("body").attr("contextpath") + "/editPickZoneName.json",{"id":id,"name":newName,"whZoonId":whZoonId});
		if(flag["flag"]=="success"){
			jumbo.showMsg("修改成功！");
		}else{
			jumbo.showMsg("修改失败！");
		}
		$j("#btn-query").click();
		$j("#btn-name-cancel").click();
	});
	
	$j("#btn-name-cancel").click(function(){
		$j("#zoneNewName").val("");
		$j("#zoneNameEdit").dialog("close");
	});
	
	$j("#btn-query1").click(function(){
		var postData = {};
			postData["district"] = $j("#district").find("option:selected").text()
			postData["location"] = $j("#location").val();
			postData["pickZoneCode"] = $j("#pickZoneCode").find("option:selected").text()
			postData["pickZoneName"] = $j("#pickZoneName").find("option:selected").text()
			if("" == postData["location"]){
				postData["location"] = null;
			}
		$j("#tbl-pickZoneInfoList").jqGrid('setGridParam',{
			url:baseUrl+"/findPickZoneInfoList.json",
			postData:postData,
			page:1
		}).trigger("reloadGrid");
		$j("#districtSelected").val(postData["district"]);
		$j("#locationSelected").val(postData["location"]);
		$j("#nameSelected").val(postData["pickZoneName"]);
		$j("#codeSelected").val(postData["pickZoneCode"]);
		$j("#query-form1 input").val("");
	});
	
	//重置
	$j("#reset").click(function(){
		$j("#query-form1 input,#query-form1 select").val("");
	});
	
	//库位导出
	$j("#export").click(function(){
		var district = $j("#districtSelected").val();
		var location = $j("#locationSelected").val();
		var pickZoneName = $j("#nameSelected").val();
		var pickZoneCode = $j("#codeSelected").val();
		$j("#frmExportPickZone").attr("src",$j("body").attr("contextpath") + "/ocp/exportLocation.do?district="+district+"&location="+location+"&pickZoneName="+pickZoneName+"&pickZoneCode="+pickZoneCode);
		
		
		/*var postData = {};
		postData["district"] = $j("#districtSelected").val();
		postData["location"] = $j("#locationSelected").val();
		postData["pickZoneName"] = $j("#nameSelected").val();
		postData["pickZoneCode"] = $j("#codeSelected").val();
		$j("#frmSoInvoice").attr("src",$j("body").attr("contextpath") + "/ocp/exportLocation.do?postData="+postData);*/
	});
	
	$j("#import").click(function(){
		var file=$j.trim($j("#uploadFile").val()),errors=[];
		if(file==""||file.indexOf("xls")==-1){
			errors.push(i18("INVALID_MAND",i18("ENTITY_EXCELFILE")));
		}
		var url = $j("body").attr("contextpath") + "/ocp/importPickZone.do";
		if(errors.length>0){
			jumbo.showMsg(errors.join("<br/>"));
		}else{
			loxia.lockPage();
			$j("#importForm").attr("action",loxia.getTimeUrl(url+"?flag="+0));
			$j("#importForm").submit();
		}
	});
	//确认继续导入
	$j("#btn-override-confirm").click(function(){
		var url = $j("body").attr("contextpath") + "/ocp/importPickZone.do";
		$j("#importForm").attr("action",loxia.getTimeUrl(url+"?flag="+1));
		$j("#importForm").submit();
		$j("#btn-override-cancel").click();
//		$j("#btn-query1").click();
	});
	
	//取消继续导入
	$j("#btn-override-cancel").click(function(){
		$j("#showOverrideDialog").dialog("close");
	});
	
});
function showMsg(msg){
	jumbo.showMsg(msg);
}
