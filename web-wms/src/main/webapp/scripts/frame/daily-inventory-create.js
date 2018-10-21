$j.extend(loxia.regional['zh-CN'],{
	DISTRICTCODE: "库区编码",
	DISTRICTNAME: "库区名称",
	DISTRICTLIST: "库区列表", 
	
	LOCATIONCODE: "库位编码",
	LOCATIONNAME: "库位名称",
	LOCATIONLIST: "库位列表", 
	OWNER : "店铺",
	TYPE: "类型",
	
	BRAND : "品牌",
	
	WAITED_FOR_INVCHECK_LIST: "待盘点列表",
	
	INVCHECK_LOCATION_LIST: "盘点库位列表",
	
	NUMBER_ISNULL: "数据为空",
	
	SUCCESS: "操作成功",
	DISTRIST: "库区",
	LOCATION: "库位",
	CORRECT_FILE_PLEASE:"请选择正确的Excel导入文件"
});
 
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function getFormDate(tb){
	var postDate = {};
	$j(tb + " tbody tr:gt(0)").each(function (i,tr){
		postDate["icclist["+i+"].paramId"] =  $j(tr).find("td:eq(2)").html();
		postDate["icclist["+i+"].lineType"] = $j(tr).find("td:eq(3)").html();
	});	 
	postDate["remark"] = $j("#txtRmk").val();
	return postDate;
}

function getFormDateMode2(tb){
	var postDate = {};
	$j(tb + " tbody tr:gt(0)").each(function (i,tr){
		postDate["icclist["+i+"].owner"] =  $j(tr).find("td[aria-describedby$='paramId']").html();
		postDate["icclist["+i+"].lineType"] = $j(tr).find("td[aria-describedby$='type']").html();
	});	 
	postDate["remark"] = $j("#txtRmk1").val();
	return postDate;
}
	

function createSuccessCallBack(rs){
	loxia.unlockPage();
	var intStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"inventoryCheckStatus"});
	if(rs && rs.result == 'success'){
		$j("#invcheckid").val(rs.invcheckid);
		if(rs.invcheckid != ''){
			var postData = {};
			postData["invcheckid"] = rs.invcheckid; 
			var url = $j("body").attr("contextpath") + "/findinvchecklinedetial.json";
			$j("#tbl_detial").jqGrid('setGridParam',{url:loxia.getTimeUrl(url),page:1,postData:postData}).trigger("reloadGrid");
			var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/findinvcheckinfobyid.json?invcheckid=" + rs.invcheckid);
			var value=intStatus.value,map={};
			if(value&&value.length>0){
				var array=value.split(";");
				$j.each(array,function(i,e){
					if(e.length>0){
						var each=e.split(":");
						map[each[0]]=each[1];
					}
				});
			}
			if(result && result.invcheck){
				$j("#divCreate").addClass("hidden");
				$j("#divExport").removeClass("hidden");
				$j("#invCheckCode").html(result.invcheck.code);
				$j("#createTime").html(result.invcheck.createTime);
				$j("#creator").html(result.invcheck.creatorName);
				$j("#status").html(map[result.invcheck.intStatus]);
				$j("#remark").html(result.invcheck.remork);
				jumbo.showMsg(i18("SUCCESS")); // 操作成功
			}
		}
	}else {
		$j("invcheckid").val("");
		var msg = "";
		for(var i in rs.msg){
			msg += rs.msg[i] +  '<br/>';
		}
		jumbo.showMsg(msg);
	}
}


$j(document).ready(function (){
	$j("#modes").tabs();
	$j("#m2_tabs").tabs();
	$j("#tabs").tabs();

	var intStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"inventoryCheckStatus"});
	
	$j("#tbl_ob_list").jqGrid({
		datatype: "json",
//		colNames: ["ID","店铺","品牌"],
		colNames: ["ID",i18("OWNER"),i18("BRAND"),"paramId","type"],
		colModel: [
	            {name: "id",hidden: true},		         
	            {name:"owner",width:200,resizable: true},
				{name:"brandName",width:200,resizable:true},
				{name: "paramId",hidden: true},
				{name: "type",hidden: true},	  
	            ],
		caption: i18("INVCHECK_LOCATION_LIST"),
	    rowNum: -1,
	   	height:"120",
		sortorder: "desc",
		multiselect: true,
		jsonReader: { repeatitems : false, id: "0" }
});
	
	$j("#tbl_dit_list").jqGrid({
			url:$j("body").attr("contextpath") + "/findDistrictList.json",
			datatype: "json",
			//colNames: ["ID","库区编码","库区名称"],
			colNames: ["ID",i18("DISTRICTCODE"),i18("DISTRICTNAME")],
			colModel: [
		            {name: "id", index: "id", hidden: true},		         
		            {name:"code",index:"code",width:200,resizable: true},
					{name:"name",index:"name",width:200,resizable:true}],
			caption: i18("DISTRICTLIST"),// 库区列表
			sortname: 'id',
		    rowNum: -1,
		   	height:"auto",
			sortorder: "desc",
			multiselect: true,
			postData:{"columns":"id,code,name"},
			jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#tbl_owner_list").jqGrid({
		url:$j("body").attr("contextpath") + "/findOwnerListByInv.json",
		datatype: "json",
		//colNames: ["ID","店铺"],
		colNames: ["ID",i18("OWNER")],
		colModel: [{name: "invOwner", index: "invOwner", hidden: true},
		           {name:"invOwner",index:"invOwner",width:200,resizable:true}],
		caption: i18("OWNER"),// 店铺
		sortname: 'invOwner',
	    rowNum: -1,
	   	height:"auto",
		sortorder: "desc",
		multiselect: true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#tbl_brand_list").jqGrid({
		url:$j("body").attr("contextpath") + "/findBrandListByInv.json",
		datatype: "json",
		//colNames: ["ID","品牌"],
		colNames: ["ID",i18("BRAND")],
		colModel: [{name: "brandId", index: "brandId", hidden: true},
		           {name:"brandName",index:"brandName",width:200,resizable:true}],
		caption: i18("BRAND"),// 店铺
		sortname: 'brandName',
	    rowNum: -1,
	   	height:"auto",
		sortorder: "desc",
		multiselect: true,
		jsonReader: { repeatitems : false, id: "0" }
	});

	$j("#tbl_loc_list").jqGrid({
		url:$j("body").attr("contextpath") + "/findlocationlist.json",
		datatype: "json",
		//colNames: ["ID","库位编码","库区编码","库区名称"],
		colNames: ["ID",i18("LOCATIONCODE"),i18("DISTRICTCODE"),i18("DISTRICTNAME")],
		colModel: [
	            {name: "id", index: "id", hidden: true},		         
	            {name:"code",index:"code",width:150,resizable: true},
				{name:"districtCode",index:"districtCode",width:150,resizable:true},
				{name:"districtName",index:"districtName",width:150,resizable:true}],
		caption: i18("LOCATIONLIST"), // 库位列表 
		multiselect: true,
		pager : "#pager",
		height:jumbo.getHeight(),
		sortname: 'code',
		sortorder: 'asc',
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		jsonReader: { repeatitems : false, id: "0" }
	});

	$j("#query").click(function(){
		var postData = loxia._ajaxFormToObj("query_loc_form");
		$j("#tbl_loc_list").jqGrid('setGridParam',{url: window.$j("body").attr("contextpath") + "/findlocationlist.json",page:1,postData:postData}).trigger("reloadGrid");
	});
	
	$j("#tbl_list").jqGrid({
		datatype: "json",
		//colNames: ["ID","paramId","lineType","库位编码","库区编码","库区名称","类型"],
		colNames: ["ID","paramId","lineType",i18("LOCATIONCODE"),i18("DISTRICTCODE"),i18("DISTRICTNAME"),i18("TYPE")],
		colModel: [
	            {name: "sid",index:"sid",hidden: true},
	            {name: "paramId",index:"icclist.paramId",hidden:true},
	            {name: "lineType",index:"icclist.lineType",hidden: true},
	            {name: "code",index:"icclist.locationCode",width:150,resizable:true},
				{name: "districtCode",index:"icclist.districtCode",width:150,resizable:true},
				{name: "districtName",index:"icclist.districtName",width:150,resizable:true},
				{name:"type",width:70,resizable:true}],
		caption: i18("WAITED_FOR_INVCHECK_LIST"), // 待盘点列表
		multiselect: true,
		sortable: false,
		height:"120",
		rowNum: -1,
		jsonReader: { repeatitems : false, id: "0" }
	});

	$j("#tbl_detial").jqGrid({
		datatype: "json",
		//colNames: ["ID","库位编码","库区编码","库区名称"],
		colNames: ["ID",i18("LOCATIONCODE"),i18("DISTRICTCODE"),i18("DISTRICTNAME")],
		colModel: [
	            {name: "id", index: "id", hidden: true},		         
				{name:"locationCode",index:"locationCode",width:200,resizable:true},
				{name:"districtCode",index:"districtCode",width:200,resizable:true},
				{name:"districtName",index:"districtName",width:200,resizable:true}
				],
		caption: i18("INVCHECK_LOCATION_LIST"), // 盘点库位列表 
		height:"auto",
		sortname: "locationCode",
		sortorder: "asc",
		multiselect: false,
		//rowNum: -1,
		pager : "#pager9",
		//height:jumbo.getHeight(),
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		jsonReader: { repeatitems : false, id: "0" }
	});

	$j("#create").click(function(){
		var ids = $j("#tbl_list tbody").find("tr:gt(0)");
		if (ids.length == 0){
			jumbo.showMsg(i18("NUMBER_ISNULL")); // 数据为空  
			return loxia.SUCCESS;
		}
		var postData = {};
		postData = getFormDate("#tbl_list");
		loxia.lockPage();
		loxia.asyncXhrPost(
			$j("body").attr("contextpath") + "/createinventorycheck.json?daily=true",
			postData,
			{success:function (data) {
				createSuccessCallBack(data);
			}});
		
	});
	
	$j("#delSelect").click(function(){
		var ids = $j("#tbl_list").jqGrid('getGridParam','selarrrow');
		for(var i in ids){
			$j("#tbl_list tr[id='"+ids[i]+"']").remove();
		}
	});
	
	$j("#export").click(function(){
		var id = $j("#invcheckid").val();
		$j("#exportInventoryCheck").attr("src",$j("body").attr("contextpath") + "/warehouse/exportinventorycheck.do?invcheckid=" + id);		
	});
	
	$j("#addDecTo").click(function(){
		var ids = $j("#tbl_dit_list").jqGrid('getGridParam','selarrrow');
		for(var i in ids){
			var tr = $j("#tbl_list tr[id='1_"+ ids[i] +"']");
			if(tr.length == 0 ){
				var data=$j("#tbl_dit_list").jqGrid("getRowData",ids[i]);
				var ndata = {};
				ndata.sid = '1_' + ids[i];
				ndata.paramId=ids[i];
				ndata.code="";
				ndata.districtCode=data["code"];
				ndata.districtName=data["name"];
				ndata.type = i18("DISTRIST"); // 库区
				ndata.lineType = '1';
				$j("#tbl_list").jqGrid('addRowData',ndata.sid,ndata);
			} 	
		}
	});
	
	$j("#addLocTo").click(function(){
		// id
		var ids2 = $j("#tbl_loc_list").jqGrid('getGridParam','selarrrow');
		for(var i in ids2){
			var tr = $j("#tbl_list tr[id='2_"+ ids2[i] +"']");
			if(tr.length == 0 ){
				var data=$j("#tbl_loc_list").jqGrid("getRowData",ids2[i]);
				var ndata = {};
				ndata.sid = '2_' + ids2[i];
				ndata.paramId=ids2[i];
				ndata.code=data["code"];
				ndata.districtCode=data["districtCode"];
				ndata.districtName=data["districtName"];
				ndata.type = i18("LOCATION"); // 库位
				ndata.lineType = '2';
				$j("#tbl_list").jqGrid('addRowData',ndata.sid,ndata);
			}
		}
	});
	
	$j("#addObTo2").click(function(){
		// id
		var ids2 = $j("#tbl_brand_list").jqGrid('getGridParam','selarrrow');
		for(var i in ids2){
			var tr = $j("#tbl_ob_list tr[id='4_"+ ids2[i] +"']");
			if(tr.length == 0 ){
				var data=$j("#tbl_brand_list").jqGrid("getRowData",ids2[i]);
				var ndata = {};
				ndata.sid = '4_' + ids2[i];
				ndata.id=ids2[i];
				ndata.brandName=data["brandName"];
				ndata.paramId = ids2[i];
				ndata.type = '4';
				$j("#tbl_ob_list").jqGrid('addRowData',ndata.sid,ndata);
			}
		}
	});
	
	$j("#addObTo1").click(function(){
		// id
		var ids2 = $j("#tbl_owner_list").jqGrid('getGridParam','selarrrow');
		for(var i in ids2){
			var tr = $j("#tbl_ob_list tr[id='3_"+ ids2[i] +"']");
			if(tr.length == 0 ){
				var data=$j("#tbl_owner_list").jqGrid("getRowData",ids2[i]);
				var ndata = {};
				ndata.sid = '3_' + ids2[i];
				ndata.id=ids2[i];
				ndata.owner=data["invOwner"];
				ndata.paramId = ids2[i];
				ndata.type = '3';
				$j("#tbl_ob_list").jqGrid('addRowData',ndata.sid,ndata);
			}
		}
	});
	
	$j("#delSelect1").click(function(){
		var ids = $j("#tbl_ob_list").jqGrid('getGridParam','selarrrow');
		for(var i in ids){
			$j("#tbl_ob_list tr[id='"+ids[i]+"']").remove();
		}
	});
	
	$j("#create1").click(function(){
		var ids = $j("#tbl_ob_list tbody").find("tr:gt(0)");
		if (ids.length == 0){
			jumbo.showMsg(i18("NUMBER_ISNULL")); // 数据为空  
			return loxia.SUCCESS;
		}
		var postData = {};
		postData = getFormDateMode2("#tbl_ob_list");
		loxia.lockPage();
		loxia.asyncXhrPost(
			$j("body").attr("contextpath") + "/createInventoryCheckMode2.json",
			postData,
			{success:function (data) {
				createSuccessCallBack(data);
			}});
	});
	
	$j("#import").click(function(){
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg(CORRECT_FILE_PLEASE);
			return;
		}
		var rmk = $j("#importRmk").val();
		loxia.lockPage();
		$j("#importForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/inventorycheckbyimport.do?remark=" + rmk)
		);
		loxia.submitForm("importForm");
	});
});

function clear(){
	$j("#file").val("");
}
function callback(rs,initStatus){
	$j("#divCreate").addClass("hidden");
	$j("#divExport").removeClass("hidden");
	$j("#invCheckCode").html(rs.code);
	$j("#createTime").html(rs.createTime);
	$j("#creator").html(rs.creatorName);
	$j("#remark").html(rs.remork);
	$j("#status").html(initStatus);
}
function setInvcheckid(msg){
	$j("#invcheckid").val(msg);
} 
