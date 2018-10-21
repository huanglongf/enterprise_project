var INCLUDE_SEL_ID = "";
var INCLUDE_SEL_VAL_CODE = "";
function initShopQuery(selId,valCode){
	INCLUDE_SEL_ID = selId;
	INCLUDE_SEL_VAL_CODE = valCode;
	if(INCLUDE_SEL_VAL_CODE =="innerShopCode"){
		INCLUDE_SEL_VAL_CODE = 'code';
	}
}

function setWhOuid(whouid){
	var postData = {};
	postData["whouid"]=whouid;
	$j("#tbl_shop_query_dialog").jqGrid('setGridParam',{
		url:loxia.getTimeUrl($j("body").attr("contextpath") + "/getbichannelinfo.json"),
		postData:postData
	}).trigger("reloadGrid");
}

$j(document).ready(function (){
	$j("#shopQueryDialog").dialog({title: "店铺查询", modal:true, autoOpen: false, width: 530});
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl_shop_query_dialog").jqGrid({
		url:baseUrl + "/getbichannelinfo.json",
		datatype: "json",
		colNames: ["id","innerShopCode","渠道名称","发票公司简称(中文)", "发票公司简称(英文)"],
		colModel: [ {name: "id", width: 100,hidden: true},
		           {name: "code", hidden: true},
		           {name: "name", index: "name", width: 210,  resizable: true},
		           {name: "companyChName", index: "companyChName", width: 130, resizable: true},
		           {name: "companyName", index: "companyName", width: 130,  resizable: true}
		           ],
		caption: "店铺列表",
		rowNum: -1,
	   	sortname: 'id',
	    multiselect: true,
	    width:500,
		shrinkToFit:false,
		sortorder: "desc", 
		height:'auto',
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#btnShopQueryClose").click(function(){
		$j("#shopQueryDialog").dialog("close");
	});

	$j("#btnShopQueryConfirm").click(function(){
		var ids = $j('#tbl_shop_query_dialog').jqGrid('getGridParam','selarrrow');
		var data = null, shoplist = "", postshop="",shoplistName="";
		if(ids.length>0){
			var companyChName = {};
			for(var i in ids){
				data = $j("#tbl_shop_query_dialog").jqGrid("getRowData",ids[i]);
				if(null == data["companyChName"] || "" == data["companyChName"]){
					jumbo.showMsg("该店铺没有维护发票公司信息，请联系销售运营部");
					return;
				}
				companyChName[i] = data["companyChName"];
				if(companyChName[i] != companyChName[0] && "" != companyChName[0]){
					jumbo.showMsg("同一个配货清单只能选择一个公司");
					return;
				}
				companyChName[0] = data["companyChName"];
				shoplist += data["name"] + " | ";
				shoplistName += data["name"] + "|";
				postshop += data[INCLUDE_SEL_VAL_CODE] + "|";
			}
		}else{
		}
		if (postshop && postshop.length > 0) {
			postshop = postshop.substring(0, postshop.length-1);
		}
		$j("#showShopList").html(shoplist);
		$j("#postshopInput").val(postshop);
		$j("#postshopInputName").val(shoplistName);
		$j("#shopQueryDialog").dialog("close");
		$j("#tbl_shop_query_dialog").trigger("reloadGrid");
	});
	
	$j("#shopGroupQueryDialog").dialog({title: "渠道组查询", modal:true, autoOpen: false, width: 520});
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl_shopGroup_query_dialog").jqGrid({
		url:baseUrl + "/findChannelGroupByOuId.json",
		datatype: "json",
		colNames: ["id","innerShopCode","渠道组编码","渠道组名称"],
		colModel: [ {name: "id", width: 100,hidden: true},
		           {name: "code", hidden: true},
		           {name: "code", index: "code", width: 180, resizable: true},
		           {name: "name", index: "name", width: 280,  resizable: true}
		           ],
		caption: "渠道列表",
		rowNum: -1,
	   	sortname: 'id',
	    multiselect: true,
	    width:500,
		shrinkToFit:false,
		sortorder: "desc", 
		height:'auto',
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#rtwShopQueryDialog").dialog({title: "渠道组查询", modal:true, autoOpen: false, width: 520});
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl_rtw_shop_query_dialog").jqGrid({
		url:baseUrl + "/getbichannelinfo.json",
		datatype: "json",
		colNames: ["id","innerShopCode","渠道组编码","渠道组名称"],
		colModel: [ {name: "id", width: 100,hidden: true},
		            {name: "code", hidden: true},
		            {name: "code", index: "code", width: 180, resizable: true},
		            {name: "name", index: "name", width: 280,  resizable: true}
		            ],
		            caption: "渠道列表",
		            rowNum: -1,
		            sortname: 'id',
		            multiselect: true,
		            width:500,
		            shrinkToFit:false,
		            sortorder: "desc", 
		            height:'auto',
		            jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#btnShopGroupQueryClose").click(function(){
		$j("#shopGroupQueryDialog").dialog("close");
	});
	
	$j("#btnRtwShopGroupClose").click(function(){
		$j("#rtwShopQueryDialog").dialog("close");
	});
	
	$j("#btnShopGroupQueryConfirm").click(function(){
		var ids = $j('#tbl_shopGroup_query_dialog').jqGrid('getGridParam','selarrrow');
		var data = null, shoplist = "", postshop="",shoplistName="";
		if(ids.length>0){
			for(var i in ids){
				data = $j("#tbl_shopGroup_query_dialog").jqGrid("getRowData",ids[i]);
				shoplist += data["name"] + " | ";
				shoplistName += data["name"] + "|";
				postshop += data[INCLUDE_SEL_VAL_CODE] + "|";
			}
		}else{
		}
		if (postshop && postshop.length > 0) {
			postshop = postshop.substring(0, postshop.length-1);
		}
		$j("#showShopList1").html(shoplist);
		$j("#postshopInput1").val(postshop);
		$j("#postshopInputName1").val(shoplistName);
		$j("#shopGroupQueryDialog").dialog("close");
	});
	
	$j("#btnRtwShopQueryConfirm").click(function(){
		var ids = $j('#tbl_rtw_shop_query_dialog').jqGrid('getGridParam','selarrrow');
		var data = null, shoplist = "", postshop="",shoplistName="";
		$j("#postshopInput").val("");
		var gg = "|";
		if(ids.length>0){
			for(var i in ids){
				data = $j("#tbl_rtw_shop_query_dialog").jqGrid("getRowData",ids[i]);
				shoplist += data["name"] + " | ";
				shoplistName += data["name"] + "|";
				postshop += data["code"] + "|";
			}
		}else{
			gg = ""
		}
		if (postshop && postshop.length > 0) {
			postshop = postshop.substring(0, postshop.length-1);
		}
		$j("#showShopList").html(shoplist);
		$j("#postshopInput").val(postshop+gg);
		$j("#postshopInputName").val(shoplistName);
		$j("#rtwShopQueryDialog").dialog("close");
	});
	
	$j("#pickAreaQueryDialog").dialog({title: "拣货区域查询", modal:true, autoOpen: false, width: 520});
	$j("#pickWhAreaQueryDialog").dialog({title: "仓库区域查询", modal:true, autoOpen: false, width: 520});
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl_pickArea_query_dialog").jqGrid({
		url:baseUrl + "/ocp/findPickZoneByOuId.json",
		datatype: "json",
		colNames: ["id","区域编码","区域名称"],
		colModel: [ {name: "id",  index: "id", width: 100,hidden: true},
		           {name: "code", index: "code", width: 180, resizable: true},
		           {name: "name", index: "name", width: 280,  resizable: true}
		           ],
		caption: "区域列表",
		rowNum: -1,
	   	sortname: 'id',
	    multiselect: true,
	    width:500,
		shrinkToFit:false,
		sortorder: "desc", 
		height:'auto',
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	
	$j("#tbl_pickWhArea_query_dialog").jqGrid({
		url:$j("body").attr("contextpath")+"/json/findZoonByParams2.json",
		datatype: "json",
		colNames: ["ID","编码","区域名称","仓库","仓库ID","状态"],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},	
		           {name: "code", index: "code", hidden: false,sortable:false},
		           {name: "name", index: "name", hidden: false,sortable:false},	
		           {name: "ouName", index: "ouName", hidden: true,sortable:false},
		           {name: "ouId", index: "ouId", hidden: true,sortable:false},
		           {name: "statusStr", index: "statusStr", hidden: true,sortable:false}
		           ],
		caption: '仓库区域列表',
	   	multiselect: true,
	   	rowNum: -1,
	   // rowNum: jumbo.getPageSize(),
		//rowList:jumbo.getPageSizeList(),
	   	//pager:"#pager",
	   	height:jumbo.getHeight(),
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	  
		});
	
	
	$j("#btnPickAreaQueryClose").click(function(){
		$j("#pickAreaQueryDialog").dialog("close");
	});
	
	
	$j("#btnPickWhAreaQueryClose").click(function(){
		$j("#pickWhAreaQueryDialog").dialog("close");
	});
	
	
	$j("#btnPickWhAreaQueryConfirm").click(function(){
		var ids = $j('#tbl_pickWhArea_query_dialog').jqGrid('getGridParam','selarrrow');
		ids.sort(sortNumber); // 排序
		var data = null, shoplist = "", postshop="",shoplistName="";
		if(ids.length>0){
			for(var i in ids){
				data = $j("#tbl_pickWhArea_query_dialog").jqGrid("getRowData",ids[i]);
				shoplist += data["name"] + " | ";
				shoplistName += data["name"] + "|";
				postshop += data["id"] + "|";
			}
		}else{
			
		}
		if (postshop && postshop.length > 0) {
			postshop = postshop.substring(0, postshop.length-1);
		}
		$j("#whAeraList1").html(shoplist);
		$j("#whAreaInput1").val(postshop);
		$j("#whAreaInputName1").val(shoplistName);
		$j("#pickWhAreaQueryDialog").dialog("close");
	});
	
	$j("#btnPickAreaQueryConfirm").click(function(){
		var ids = $j('#tbl_pickArea_query_dialog').jqGrid('getGridParam','selarrrow');
		ids.sort(sortNumber); // 排序
		var data = null, shoplist = "", postshop="",shoplistName="";
		if(ids.length>0){
			for(var i in ids){
				data = $j("#tbl_pickArea_query_dialog").jqGrid("getRowData",ids[i]);
				shoplist += data["name"] + " | ";
				shoplistName += data["name"] + "|";
				postshop += data["id"] + "|";
			}
		}else{
			
		}
		if (postshop && postshop.length > 0) {
			postshop = postshop.substring(0, postshop.length-1);
		}
		$j("#AeraList1").html(shoplist);
		$j("#AreaInput1").val(postshop);
		$j("#AreaInputName1").val(shoplistName);
		$j("#pickAreaQueryDialog").dialog("close");
	});
	
	
	$j("#btnSearchDistrictAreaDialog").dialog({title: "仓库区域查询", modal:true, autoOpen: false, width: 520});
	var baseUrl = $j("body").attr("contextpath");
	
	$j("#tbl_district_query_dialog").jqGrid({
		url:baseUrl + "/json/findAllDistrictByOuId.json",
		datatype: "json",
		colNames: ["id","编码","区域名称"],
		colModel: [ {name: "id",  index: "id", width: 100,hidden: true},
		           {name: "code", index: "code", width: 180, resizable: true},
		           {name: "name", index: "name", width: 280,  resizable: true}
		           ],
		caption: "仓库区域列表",
		rowNum: -1,
	   	sortname: 'id',
	    multiselect: true,
	    width:500,
		shrinkToFit:false,
		sortorder: "desc", 
		height:'auto',
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	
	
	$j("#btnSearchDistrictConfirm").click(function(){
		var ids = $j('#tbl_district_query_dialog').jqGrid('getGridParam','selarrrow');
		var data = null, districtlist = "", districtshop="",districtlistName="";
		if(ids.length>0){
			for(var i in ids){
				data = $j("#tbl_district_query_dialog").jqGrid("getRowData",ids[i]);
				districtlist += data["name"] + " | ";
				districtlistName += data["name"] + "|";
				districtshop += data["id"] + "|";
			}
		}else{
		}
		if (districtshop && districtshop.length > 0) {
			districtshop = districtshop.substring(0, districtshop.length-1);
		}
		$j("#DistrictList1").html(districtlist);
		$j("#DistrictInput1").val(districtshop);
		$j("#DistrictInputName1").val(districtlistName);
		$j("#btnSearchDistrictAreaDialog").dialog("close");
	});
	
	$j("#btnSearchDistrictClose").click(function(){
		$j("#btnSearchDistrictAreaDialog").dialog("close");
	});
});

// 数组排序
function sortNumber(a,b){
	return a - b;
}
