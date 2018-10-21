/*function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}
function confirmModification(obj){
	var rowId=$j("#tbl-threadList").jqGrid("getGridParam","selrow");
	var rowData = $j("#tbl-threadList").jqGrid('getRowData',rowId);
	var id=rowData.id;
	var beanName=rowData.beanName;
	var methodName=rowData.methodName;
	$j("#beanName_1").val(beanName);
	$j("#methodName_1").val(methodName);
	$j("#id").val(id);
	$j("#timeExp").focus();
}*/
$j(document).ready(function (){
	$j("#tabs").tabs({
		 select: function(event, ui) {
		  }
	});
	$j("#oms_update4").click(function(){
		
		if($j("#oms_optionValue").val()==""){
			jumbo.showMsg("不可为空！");
			return false;
		}
		if(isNaN($j("#oms_optionValue").val())){
			jumbo.showMsg("请输入数字！");
			return false;
		}
		var postData = {
				"optionValue":$j("#oms_optionValue").val(),
		}; 
		var flag=loxia.syncXhr($j("body").attr("contextpath") + "/json/omsChooseOptionUpdate.json",postData);
		if(flag["flag"]=="success"){
			jumbo.showMsg("修改成功！");
			$j("#tbl-PriorityChannelOmsList").jqGrid('setGridParam',{
				page:1}).trigger("reloadGrid");
		}else if(flag["flag"]=="error"){
			jumbo.showMsg("系统异常！");
		}else {
			jumbo.showMsg(flag["flag"]);
		}
		
	});
$j("#pac_update4").click(function(){
		
		if($j("#pac_optionValue").val()==""){
			jumbo.showMsg("不可为空！");
			return false;
		}
		if(isNaN($j("#pac_optionValue").val())){
			jumbo.showMsg("请输入数字！");
			return false;
		}
		
		var postData = {
				"optionValue":$j("#pac_optionValue").val(),
		}; 
		var flag=loxia.syncXhr($j("body").attr("contextpath") + "/json/pacChooseOptionUpdate.json",postData);
		if(flag["flag"]=="success"){
			jumbo.showMsg("修改成功！");
		}else if(flag["flag"]=="error"){
			jumbo.showMsg("系统异常！");
		}else {
			jumbo.showMsg(flag["flag"]);
		}
		
	});
});
