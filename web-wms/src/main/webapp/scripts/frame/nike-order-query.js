$j.extend(loxia.regional['zh-CN'],{
	DATA_ERROR : "数据错误",
	OPERATE:"操作"
});
var $j = jQuery.noConflict();


function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

$j(document).ready(function (){
	loxia.init({debug: true, region: 'zh-CN'});
	var baseUrl = $j("body").attr("contextpath")+"/json";
	var queryUrl =baseUrl+"/nikeOrderQuery.json?comd.intStatusList[0]=0&comd.intStatusList[1]=20";
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	/* 这里编写必要的页面演示逻辑*/
	$j("#tbl-list-query").jqGrid({
		url:queryUrl,
		datatype: "json",
		colNames: ["ID","NIKE单据","状态","类型","收件人","手机","地址","备注","过单失败原因"],
		colModel: [
		           {name: "id", index: "id", hidden: true},		         
		           {name: "slipCode", index: "code", width: 100, resizable: true},
		           {name: "intStatus", index: "intStatus", width: 80, resizable: true},
		           {name: "intType", index: "intType", width: 100, resizable: true, formatter:'select',editoptions:staType},
		           {name: "receiver", index: "receiver", width: 100, resizable: true},
		           {name: "mobile", index: "mobile", width: 100, resizable: true},
		           {name: "address", index: "address", width: 150, resizable: true},
		           {name: "remark", index: "remark", width: 150, resizable: true},
		           {name: "failureInfo", index: "failureInfo", width: 500, resizable: true}],
		caption: "NIKE过单单据",
	   	pager:"#pager",
	   	sortname: 'slip_code',
	   	rownumbers:true,
	   	multiselect: true,
	   	rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		height:jumbo.getHeight(),
		sortorder: "desc",
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var ids = $j("#tbl-list-query").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var datas = $j("#tbl-list-query").jqGrid('getRowData',ids[i]);
				if(datas["intStatus"]=="0"){
					$j("#tbl-list-query").jqGrid('setRowData',ids[i],{"intStatus":"失败"});
				} else if(datas["intStatus"]=="1") {
					$j("#tbl-list-query").jqGrid('setRowData',ids[i],{"intStatus":"新建"});
				} else if(datas["intStatus"]=="2") {
					$j("#tbl-list-query").jqGrid('setRowData',ids[i],{"intStatus":"已发送"});
				} else if(datas["intStatus"]=="5") {
					$j("#tbl-list-query").jqGrid('setRowData',ids[i],{"intStatus":"执行中"});
				} else if(datas["intStatus"]=="10") {
					$j("#tbl-list-query").jqGrid('setRowData',ids[i],{"intStatus":"完成"});
				} else if(datas["intStatus"]=="20") {
					$j("#tbl-list-query").jqGrid('setRowData',ids[i],{"intStatus":"不执行"});
				} else if(datas["intStatus"]=="-1") {
					$j("#tbl-list-query").jqGrid('setRowData',ids[i],{"intStatus":"错误"});
				}
			}
			loxia.initContext($j(this));
		}
	});
	
	$j("#search").click(function(){
		$j("#tbl-list-query").jqGrid('setGridParam',{url:queryUrl,postData:loxia._ajaxFormToObj("form_query")}).trigger("reloadGrid");
	});
	
	$j("#updateStatus").click(function(){
		var ids = $j("#tbl-list-query").jqGrid('getGridParam','selarrrow');
		if(ids.length>0){
			var postData = {};
			for(var i in ids){
				postData['idList[' + i + ']']=ids[i];
			}
			operatorPost(baseUrl + "/forUpdateStatus.json",postData);
			$j("#tbl-list-query").jqGrid('setGridParam',{url:queryUrl,postData:loxia._ajaxFormToObj("form_query")}).trigger("reloadGrid");
		}else{
			jumbo.showMsg("请选择需要关联的物流商！"); // 请选择店铺
		}
	});
	
	$j("#reset").click(function(){
		$j("#form_query input,#form_query select").val("");
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
