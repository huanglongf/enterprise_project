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
	
	$j("#shopQueryDialog").dialog({title: "店铺查询", modal:true, autoOpen: false, width: 520});
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl_shop_query_dialog").jqGrid({
		url:baseUrl + "/getbichannelinfo.json",
		datatype: "json",
		colNames: ["id","innerShopCode","渠道编码","渠道名称"],
		colModel: [ {name: "id", width: 100,hidden: true},
		           {name: "code", hidden: true},
		           {name: "shopCode", index: "shop_code", width: 180, resizable: true},
		           {name: "name", index: "name", width: 280,  resizable: true}
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
			for(var i in ids){
				data = $j("#tbl_shop_query_dialog").jqGrid("getRowData",ids[i]);
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
	
	$j("#btnShopGroupQueryClose").click(function(){
		$j("#shopGroupQueryDialog").dialog("close");
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
	
	$j("#pickAreaQueryDialog").dialog({title: "区域查询", modal:true, autoOpen: false, width: 520});
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
	
	$j("#btnPickAreaQueryClose").click(function(){
		$j("#pickAreaQueryDialog").dialog("close");
	});
	
	$j("#btnPickAreaQueryConfirm").click(function(){
		var ids = $j('#tbl_pickArea_query_dialog').jqGrid('getGridParam','selarrrow');
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
	
	
});


