$j.extend(loxia.regional['zh-CN'],{

		
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
var wlist=null;
$j(document).ready(function (){
	wlist = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/checkoperationunit.json");
	initShopQuery("companyshop","innerShopCode");
	jumbo.loadShopList("companyshop");
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-orderList").jqGrid({
		datatype: "json",
		colNames: ["ID","作业单号","相关单据号","作业类型","作业单状态","平台店铺名称","物流服务商","快递单号","创建时间","操作人","备注"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name:"code", index:"code", width:100, resizable:true},
					{name:"refSlipCode",index:"sta.slip_code",width:80,resizable:true},
					{name:"intType", index:"type" ,width:70,resizable:true,formatter:'select',editoptions:staType},
					{name:"intStatus",index:"status",width:70,resizable:true,formatter:'select',editoptions:staStatus},
					{name:"owner", index:"sta.owner",width:130,resizable:true},
					{name:"lpcode", index:"lpcode",width:80,resizable:true},
					{name:"trackingNo", index:"trackingNo",width:80,resizable:true},
					{name:"createTime",index:"sta.create_time",width:130,resizable:true},
					{name:"operator", index:"operator",width:100,resizable:true},
					{name:"memo", index:"memo",width:150,resizable:true}],
		caption: "作业单列表",
	   	sortname: 'sta.create_time',
	   	sortorder: "desc",
	    multiselect: true,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#pager",	   
		viewrecords: true,
   		rownumbers: true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	 if(wlist!=""){
			var result = loxia.syncXhrPost($j("body").attr("contextpath")+"/selectbyopc.json");
			for(var idx in result.warelist){
				$j("<option value='" + result.warelist[idx].id + "'>"+ result.warelist[idx].name +"</option>").appendTo($j("#selTrans"));
			}
			$j("#selTransname").show();
			$j("#selTrans").show();
	 }
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
	});
	
	$j("#search").click(function(){
		var url = baseUrl + "/staLpcodeQuery.json";
		$j("#tbl-orderList").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl-orderList",{"intType":"whSTAType","intStatus":"whSTAStatus"},url,loxia._ajaxFormToObj("queryForm"));
	});
	
	$j("#updateSelect").click(function(){
		var lpcode = $j("#select").val();
		if(lpcode == ""){
			jumbo.showMsg("请选择物流商！");
			return;
		}
		var ids = $j("#tbl-orderList").jqGrid('getGridParam','selarrrow');
		if(ids.length>0){
			var postData = {};
			for(var i in ids){
				postData['staIds[' + i + ']']=ids[i];
			}
			postData['lpcode']=lpcode;
			postData['isSelectAll']=false;
			operatorPost(baseUrl + "/updateStaLpcode.json",postData);
		} else {
			jumbo.showMsg("请选择好须要修改的作业单！");
		}
	});
	
	$j("#updateAll").click(function(){
		var lpcode = $j("#select").val();
		if(lpcode == ""){
			jumbo.showMsg("请选择好物流商！");
			return;
		}
		if(confirm("你确定要修改查询结果集吗？")){
			var postData = loxia._ajaxFormToObj("queryForm");
			postData['lpcode']=lpcode;
			postData['isSelectAll']=true;
			operatorPost(baseUrl + "/updateStaLpcode.json",postData);
		}
	});
	
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransactionTypeName.do");
	for(var idx in result){
		if(result[idx].code == 'SFCOD' || result[idx].code == 'EMSCOD' || result[idx].code == 'JDCOD'){
		}else{
			$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#select"));
		}
			$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#staLpcode"));			
	}
	
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
});

function operatorPost(url,postData){
	var rs = loxia.syncXhrPost(url,postData);
	if(rs){
		if(rs.result == 'success'){
			jumbo.showMsg("修改成功！");
			var url = $j("body").attr("contextpath") + "/staLpcodeQuery.json";
			$j("#tbl-orderList").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
			jumbo.bindTableExportBtn("tbl-orderList",{"intType":"whSTAType","intStatus":"whSTAStatus"},url,loxia._ajaxFormToObj("queryForm"));
		}else {
			jumbo.showMsg(rs.message);
		}
	} else {
		jumbo.showMsg("数据操作异常");
	}
	return false;
}