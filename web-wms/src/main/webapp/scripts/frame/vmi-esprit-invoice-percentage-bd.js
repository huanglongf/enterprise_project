$j.extend(loxia.regional['zh-CN'],{

});
 
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

$j(document).ready(function (){
	$j("#tbl-pbPo").jqGrid({
		url:$j("body").attr("contextpath") + "/findESPPoInvoiceBD.json",
		datatype: "json",
		colNames: ["ID","PO","发票号","绑定时间"],
		colModel: [
		           {name: "id", index: "id", hidden: true},		         
		           {name: "po", index: "stvId", width: 150, resizable: true,sortable:false},		         
		           {name: "invoiceNumber", index: "stv_total", width: 150, resizable: true,sortable:false},		         
		           {name: "createTime", index: "create_time", width: 150, resizable: true,sortable:false}
		           ],
		caption: "绑定列表",
		sortname: 'po',
	   	sortorder: "desc",
	   	multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pager",
	   	height:jumbo.getHeight(),
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	$j("#reset").click(function(){
		$j("#query_tab input").val("");
		$j("#query_tab select").val("");
	});
	$j("#query").click(function (){
		var url = $j("body").attr("contextpath") + "/findESPPoInvoiceBD.json";
		$j("#tbl-pbPo").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
	});
	$j("#bdPo").click(function (){
		
		var po = $j("#po").val();
		if(po == ""){
			jumbo.showMsg("请输入须要绑定的PO!");
			return ;
		}
		var invoiceNum = $j("#invoiceNum").val();
		if(invoiceNum == ""){
			jumbo.showMsg("请输入须要绑定的发票号!");
			return ;
		}
		
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/espPoAndInvoiceBD.json?po="+po+"&invoiceNum="+invoiceNum)
		if(rs && rs.rs=='success'){
			loxia.unlockPage();
			jumbo.showMsg("成功绑定"); // 操作成功
		}
		else {
			loxia.unlockPage();
			jumbo.showMsg(rs.msg);
		}
	});
});

