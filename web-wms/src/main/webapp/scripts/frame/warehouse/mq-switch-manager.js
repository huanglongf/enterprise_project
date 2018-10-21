function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}
function confirmModification(obj){
	var rowId=$j("#tbl-mqMessageList").jqGrid("getGridParam","selrow");
	var rowData = $j("#tbl-mqMessageList").jqGrid('getRowData',rowId);
	var id=rowData.id;
	var msgType=rowData.msgType;
	var isOpen=rowData.isOpen;
	var postData={};
	var re=null;
	postData["messageConfig.id"]=id;
	if(id==null){
		jumbo.showMsg("请先选中后再修改");
		return;
	}
	if(isOpen==1){
		postData["messageConfig.isOpen"]=0;
		if(window.confirm("你确定关闭消息类型  "+msgType+" ?")){
			re = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/switchMessageConfig.json",postData);
		}
	}else{
		postData["messageConfig.isOpen"]=1;
		if(window.confirm("你确定开启消息类型  "+msgType+" ?")){
			 re = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/switchMessageConfig.json",postData);
		}
	}
	if(re.result=="success"){
		if(isOpen==1){
			jumbo.showMsg("关闭成功!");
		}else if(isOpen==0){
			jumbo.showMsg("开启成功!");
		}
	$j("#tbl-mqMessageList").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/getMessageConfigList.json"),
		postData:loxia._ajaxFormToObj("searchForm")}).trigger("reloadGrid");
	}

}
$j(document).ready(function (){
	var resultopc = loxia.syncXhrPost($j("body").attr("contextpath")+"/getChooseOptionValue.json");
	if(resultopc["flag"]=="success"){
		$j('input:radio[name="saveWeight"][value="多线程"]').attr("checked",true);
	}else{
		$j('input:radio[name="saveWeight"][value="MQ"]').attr("checked",true);
	}
	$j("#tbl-mqMessageList").jqGrid({
		datatype:"json",
		colNames:["ID","消息类型","消息队列","二级标签","描述","是否开启","启用/停用"],	
		colModel:[
		          {name:"id",index:"id",hidden:true},
		          {name:"msgType",index:"msgType",width: 150,resizable: true},
		          {name:"topic",index:"topic",width: 150,resizable: true},
		          {name:"tags",index:"tags",width: 150,resizable: true},
		          {name:"description",index:"description",width: 150,resizable: true},
		          {name:"isOpen",index:"isOpen",width: 150,resizable: true},
			      {name: "operator", width: 60, resizable: true,formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("修改"), onclick:"confirmModification(this);"}}}],
		caption: i18("RocketMQ消息切换列表"),
		multiselect: false,
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		width:"auto",
		height:"auto",
		pager:"#pager_query",	   
		sortorder: "id",
		viewrecords: true,
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#search").click(function(){
		$j("#tbl-mqMessageList").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/getMessageConfigList.json"),
			postData:loxia._ajaxFormToObj("searchForm")}).trigger("reloadGrid");
	});
	
	$j("#updateThreadMQ").click(function(){
		var  ifEnteringWeight =  $j.trim($j('input:radio[name="saveWeight"]:checked').val());
		var optionValue=1;
		if(ifEnteringWeight == "多线程"){
			optionValue=0;
			
		} 
		var postData = {
				"optionValue":optionValue
		}; 
		var flag=loxia.syncXhr($j("body").attr("contextpath") + "/json/updateChooseOptionValue.json",postData);
		if(flag["flag"]=="success"){
			jumbo.showMsg("保存成功！");
		}else if(flag["flag"]=="error"){
			jumbo.showMsg("系统异常！");
		}else {
			jumbo.showMsg(flag["flag"]);
		}
	});
	
	$j("#reset").click(function(){
		$j("#msgType").val("");
		$j("#topic").val("");
	});
	


});