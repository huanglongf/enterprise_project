var $j = jQuery.noConflict();
var baseUrl = "";
$j(document).ready(function (){
	initShopQuery("shopS","id");
	
	loxia.init({debug: true, region: 'zh-CN'});
	$j("#tabs").tabs();
	baseUrl = $j("body").attr("contextpath");
	
	//加载店铺
	jumbo.loadShopList("shopS","id");
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	
	$j("#tbl_wh").jqGrid({
		//url:baseUrl+"/findWarehouse.json",
		datatype: "json",
		colNames: ["ID","仓库编码","全称","否是关联"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name :"code",index:"code",width:150,resizable:true},
					{name:"name", index:"name" ,width:200,resizable:true},
					{name: "isRef", index:"isRef",width:80,resizable:true}],
		caption: "仓库列表",
		rowNum:-1,
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: true,
		sortorder: "desc",
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var ids = $j("#tbl_wh").jqGrid('getDataIDs');
			var txt = "否";
			for(var i=0;i < ids.length;i++){
				var data=$j("#tbl_wh").jqGrid("getRowData",ids[i]);
				if(data["isRef"] == 0){
					$j("#tbl_wh").jqGrid('setRowData',ids[i],{"isRef":txt});					
				} else {
					$j("#tbl_wh").jqGrid('setRowData',ids[i],{"isRef":"是"});
					$j("#"+data["id"]).find("td:eq(0) :input").attr("checked",true);
				}
			}
			loxia.initContext($j(this));
		}
	});

	$j("#tbl_trans").jqGrid({
		//url:baseUrl+"/findLogistics.json",
		datatype: "json",
		colNames: ["ID","物流商名称","物流商简称","否是关联"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name :"fullName",index:"fullName",width:150,resizable:true},
					{name:"expCode", index:"expCode" ,width:80,resizable:true},
					{name: "isRef", index:"isRef",width:80,resizable:true}],
		caption: "物流列表",
		rowNum:-1,
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: true,
		sortorder: "desc",
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var ids = $j("#tbl_trans").jqGrid('getDataIDs');
			var txt = "否";
			for(var i=0;i < ids.length;i++){
				var data=$j("#tbl_trans").jqGrid("getRowData",ids[i]);
				if(data["isRef"] == 0){
					$j("#tbl_trans").jqGrid('setRowData',ids[i],{"isRef":txt});					
				} else {
					$j("#tbl_trans").jqGrid('setRowData',ids[i],{"isRef":"是"});
					$j("#"+data["id"]).find("td:eq(0) :input").attr("checked",true);
				}
			}
			loxia.initContext($j(this));
		}
	});

	$j("#shopS").change(function(){
		var val = $j("#shopS").val();
		if(val != ""){
			$j("#tbl_trans").jqGrid('setGridParam',{url:baseUrl+"/findLogistics.json?shopId="+val}).trigger("reloadGrid");
			$j("#tbl_wh").jqGrid('setGridParam',{url:baseUrl+"/findWarehouse.json?shopId="+val}).trigger("reloadGrid");
		} else {
			
		}
	});

	$j("#refLogistics").click(function(){
		var val = $j("#shopS").val();
		if(val == ""){
			jumbo.showMsg("请选择好店铺！");
			return;
		}
		var ids = $j("#tbl_trans").jqGrid('getGridParam','selarrrow');
		if(ids.length>0){
			var postData = {};
			for(var i in ids){
				postData['parametIds[' + i + ']']=ids[i];
			}
			postData['shopId']=val;
			operatorPost(baseUrl + "/addTransportatorRef.json",postData);
			$j("#tbl_trans").jqGrid('setGridParam',{url:baseUrl+"/findLogistics.json?shopId="+val}).trigger("reloadGrid");
		}else{
			jumbo.showMsg("请选择需要关联的物流商！"); // 请选择店铺
		}
	});
	
	$j("#cancelRef").click(function(){
		var val = $j("#shopS").val();
		if(val == ""){
			jumbo.showMsg("请选择好店铺！");
			return;
		}
		var ids = $j("#tbl_trans").jqGrid('getGridParam','selarrrow');
		if(ids.length>0){
			var postData = {};
			for(var i in ids){
				postData['parametIds[' + i + ']']=ids[i];
			}
			postData['shopId']=val;
			operatorPost(baseUrl + "/removeTransportatorRef.json",postData);
			$j("#tbl_trans").jqGrid('setGridParam',{url:baseUrl+"/findLogistics.json?shopId="+val}).trigger("reloadGrid");
		}else{
			jumbo.showMsg("请选择需要关联的物流商！"); // 请选择店铺
		}
	});
	
	$j("#refWhShop").click(function(){
		var val = $j("#shopS").val();
		if(val == ""){
			jumbo.showMsg("请选择好店铺！");
			return;
		}
		var ids = $j("#tbl_wh").jqGrid('getGridParam','selarrrow');
		if(ids.length>0){
			var postData = {};
			for(var i in ids){
				postData['parametIds[' + i + ']']=ids[i];
			}
			postData['shopId']=val;
			operatorPost(baseUrl + "/addShopWarehuseRef.json",postData);
			$j("#tbl_wh").jqGrid('setGridParam',{url:baseUrl+"/findWarehouse.json?shopId="+val}).trigger("reloadGrid");
		}else{
			jumbo.showMsg("请选择需要关联的仓库！"); // 请选择店铺
		}
	});
	
	$j("#cancelWhShopRef").click(function(){
		var val = $j("#shopS").val();
		if(val == ""){
			jumbo.showMsg("请选择好店铺！");
			return;
		}
		var ids = $j("#tbl_wh").jqGrid('getGridParam','selarrrow');
		if(ids.length>0){
			var postData = {};
			for(var i in ids){
				postData['parametIds[' + i + ']']=ids[i];
			}
			postData['shopId']=val;
			operatorPost(baseUrl + "/removeShopWarehuseRef.json",postData);
			$j("#tbl_wh").jqGrid('setGridParam',{url:baseUrl+"/findWarehouse.json?shopId="+val}).trigger("reloadGrid");
		}else{
			jumbo.showMsg("请选择需要关联的仓库！"); // 请选择店铺
		}
	});
});


function operatorPost(url,postData){
	var rs = loxia.syncXhrPost(url,postData);
	if(rs){
		if(rs.result == 'success'){
			jumbo.showMsg("操作成功！");
		}else {
			jumbo.showMsg(rs.message);
		}
	} else {
		jumbo.showMsg("数据操作异常");
	}
	return false;
	
}