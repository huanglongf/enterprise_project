$j(document).ready(function (){
	$j("#tbl-orderList").jqGrid({
		//url:$j("body").attr("contextpath")+"/findSecKillMaintain.json",hidden: true
		datatype: "json",
		colNames: ["id","作业单","创建时间","店铺","仓库","错误次数"],
		colModel: [
		           {name: "rtnId", index: "rtnId", hidden: true},		         
		           {name:"rtnStaCode",index:"rtnStaCode",  width: 150,resizable:true},
		           {name:"rtnCreateTime",index:"rtnCreateTime", width:95,resizable:true},
		           {name:"shopName",index:"shopName", width:150,resizable:true},
		           {name:"ouName",index:"ouName", width:150,resizable:true},
		           {name:"errorCount",index:"errorCount", width:150,resizable:true}
		           ],
		caption: "外包仓出库反馈功能维护列表",
	   	multiselect: true,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pager",
	   	sortname: "t.id",
	   	height:jumbo.getHeight(),
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	
	
	
	$j("#search").click(function(){
		var code=$j("#codeList").val();
		if(null!=code&&""!=code){
		   var url=$j("body").attr("contextpath")+"/findStaByStaCode.json";
		   $j("#tbl-orderList").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
		   jumbo.bindTableExportBtn("tbl-orderList",url,loxia._ajaxFormToObj("queryForm"));
		}else{
			jumbo.showMsg("请输入单号");
		}
	});
	//返回
	$j("#send").click(function(){
		var ids = $j("#tbl-orderList").jqGrid('getGridParam','selarrrow');
		var postData = {};
		for(var i in ids){
			postData['idList[' + i + ']']=ids[i];
		}
		if (ids && ids.length>0) {
			var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/updateWmsOutBound.json"),postData);
			if (rs && rs.result == "success") {
				var url = loxia.getTimeUrl($j("body").attr("contextpath") + "/findStaByStaCode.json");
				$j("#tbl-orderList").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
			}
			jumbo.showMsg(rs.message);
		} else {
			jumbo.showMsg("请选择需要推送的单据");
		}
	});
	
});