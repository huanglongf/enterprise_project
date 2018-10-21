$j.extend(loxia.regional['zh-CN'],{
	
	EXPRESS_CITY : "快递省份一览表",
	PROVINCE : "省",
	CITY : "市",
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

$j(document).ready(function (){
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-waittingList").jqGrid({
		url:baseUrl + "/findAreaList.json",
		datatype: "json",
		colNames: ["ID",i18("PROVINCE"),i18("CITY")],
		colModel: [
				   {name: "id", index: "id", hidden: true},
		           {name: "province", index: "province", width:150,resizable:true},    
		           {name: "city", index: "city", width: 150, resizable: true},
		           ],
		caption: i18("EXPRESS_CITY"),// 待配货清单列表
		pager:"#pager_query",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: true,
	    rownumbers: true,
	    viewrecords: true,
		sortorder: "desc",
		jsonReader: { repeatitems : false, id: "0" },
	});
	
	//查询省市
	$j("#btn-query").click(function(){
		var postData = {};
			postData["province"]=$j("#province").val();
			postData["city"]=$j("#city").val();
		//$j("#query-form input").val("");
		$j("#tbl-waittingList").jqGrid('setGridParam',{
			url:window.$j("body").attr("contextpath")+"/findAreaList.json",
			postData:postData,
			page:1
		}).trigger("reloadGrid");
	});
	
	//添加省市
	$j("#btn-add").click(function(){
		var postData = {};
		postData["province"]=$j("#addProvince").val();
		if(""==$j("#addProvince").val()){
//			$j("#addProvinceFont").text("此项必填");
			return false;
		}
		postData["city"]=$j("#addCity").val();
		var flag = loxia.syncXhrPost($j("body").attr("contextpath") + "/addRadarArea.json",postData);
		if(flag["flag"]=="success"){
			jumbo.showMsg("添加成功！");
		}else if(flag["flag"]=="exist"){
			jumbo.showMsg("省市已存在！");
		}else{
			jumbo.showMsg("添加失败！");
		}
		$j("#btn-query").click();
	});
	
	//删除省市
	$j("#btn-del").click(function(){
		var ids = $j("#tbl-waittingList").jqGrid('getGridParam','selarrrow');
		if(ids.length>0){
			var postData = {};
			for(var i in ids){
				postData['areaIds[' + i + ']']=ids[i];
			}
			var flag = loxia.syncXhrPost(baseUrl + "/delRadarArea.json",postData);
			if(flag["flag"]=="success"){
				jumbo.showMsg("删除成功！");
			}else{
				jumbo.showMsg("删除失败！");
			}
			$j("#btn-query").click();
		} else {
			jumbo.showMsg("请选择一个城市");
		}
	});
	
	//重置
	$j("#reset").click(function(){
		$j("#query-form input,#query-form select").val("");
	});
})
