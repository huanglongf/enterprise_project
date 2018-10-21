$j.extend(loxia.regional['zh-CN'],{
	SELECT_SHOP				:"请选择店铺",
	INNERSHOPCODE 			:"店铺code",
	SHOPID					:"淘宝ID",
	SHOPNAME				:"淘宝店铺名称",
	INDUSTRY				:"所属行业",
	ISRELATIVETHISCOMPANY	:"是否已关联当前公司",
	SHOP_LIST				:"店铺列表",
	OPERATE_SUCCESS			:"操作成功"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
} 

$j(document).ready(function (){
	$j("#tbl-shopDetail").jqGrid({
		url:$j("body").attr("contextpath") + "/findshoplist.json",
		datatype: "json",
		// colsNames : ["id","店铺code","淘宝ID","淘宝店铺名称","所属行业","是否已关联当前公司"],
		colNames: ["ID",i18("INNERSHOPCODE"),i18("SHOPID"),i18("SHOPNAME"),i18("INDUSTRY"),i18("ISRELATIVETHISCOMPANY")],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name: "innerShopCode", index: "innerShopCode", width:120,resizable:true},         
		           {name: "shopId", index: "shopId", width: 150, resizable: true},
		           {name: "shopName", index: "shopName", width: 150, resizable: true},
		           {name: "industry", index: "industry", width: 150, resizable: true},
	               {name:"isRef",index:"isRef",width:150,resizable:true}],
		caption: i18("SHOP_LIST"),
		rowNum:-1,
	   	sortname: 'innerShopCode',
	    multiselect: true,
	    height:"auto",
		sortorder: "desc",
		gridComplete: function(){
			var ids = $j("#tbl-shopDetail").jqGrid('getDataIDs');
			var txt = "否";
			for(var i=0;i < ids.length;i++){
				var data=$j("#tbl-shopDetail").jqGrid("getRowData",ids[i]);
				if(data["isRef"] == 0){
					$j("#tbl-shopDetail").jqGrid('setRowData',ids[i],{"isRef":txt});					
				} else {
					$j("#tbl-shopDetail").jqGrid('setRowData',ids[i],{"isRef":"是"});
					$j("#"+data["id"]).find("td:eq(0) :input").attr("checked",true);
				}
			}
			loxia.initContext($j(this));
		},
		jsonReader: { repeatitems : false, id: "0" }
		});


	$j("#addToCompany").click(function(){
		var ids = $j("#tbl-shopDetail").jqGrid('getGridParam','selarrrow');
		if(ids.length>0){
			var postData = {};
			for(var i in ids){
				postData['shopidList[' + i + ']']=ids[i];
			}
			var baseUrl = $j("body").attr("contextpath");
			operator(baseUrl + "/addtocompany.json",postData);
			$j("#tbl-shopDetail").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findshoplist.json")}).trigger("reloadGrid");
		}else{
			jumbo.showMsg(i18("SELECT_SHOP")); // 请选择店铺
		}
	});
	
	$j("#cancelToCompany").click(function(){
		var ids = $j("#tbl-shopDetail").jqGrid('getGridParam','selarrrow');
		if(ids.length>0){
			var postData = {};
			for(var i in ids){
				postData['shopidList[' + i + ']']=ids[i];
			}
			var baseUrl = $j("body").attr("contextpath");
			operator(baseUrl + "/removefromcompany.json",postData);
			$j("#tbl-shopDetail").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findshoplist.json")}).trigger("reloadGrid");
		}else{
			jumbo.showMsg(i18("SELECT_SHOP"));  // 请选择店铺
		}
	});
});

function operator(url,postData){
	var rs = loxia.syncXhrPost(url,postData);
	if(rs&&rs.result == 'error'){
		jumbo.showMsg(rs.msg);
		return false;
	}
	jumbo.showMsg(i18("OPERATE_SUCCESS"));
	return true;
}
