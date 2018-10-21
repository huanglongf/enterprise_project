function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}
function confirmModification(obj){
	var rowId=$j("#tbl-timedtaskList").jqGrid("getGridParam","selrow");
	var rowData = $j("#tbl-timedtaskList").jqGrid('getRowData',rowId);
	var id=rowData.id;
	var beanName=rowData.beanName;
	var methodName=rowData.methodName;
	$j("#beanName_1").val(beanName);
	$j("#methodName_1").val(methodName);
	$j("#id").val(id);
	//$j("#timeExp").val(id);
	$j("#timeExp").focus();
}
$j(document).ready(function (){
	$j("#tabs").tabs({
		 select: function(event, ui) {
		  }
	});
	$j("#tbl-timedtaskList").jqGrid({
		datatype:"json",
		colNames:["ID","类名","描述","方法名","执行时间间隔","是否启用","修改时间"],	
		colModel:[
		          {name:"id",index:"id",hidden:true},
		          {name:"beanName",index:"beanName",width: 150,resizable: true},
		          {name:"description",index:"description",width: 150,resizable: true},
		          {name:"methodName",index:"methodName",width: 150,resizable: true},
		          {name:"timeExp",index:"timeExp",width: 150,resizable: true},
		          {name:"node",index:"node",width: 150,resizable: true},
			      {name: "operator", width: 60, resizable: true,formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("修改"), onclick:"confirmModification(this);"}}}],
		caption: i18("wms定时任务列表"),
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
		$j("#tbl-timedtaskList").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/getSchedulerTaskList.json"),
			postData:loxia._ajaxFormToObj("searchForm")}).trigger("reloadGrid");
	})
	
	$j("#reset").click(function(){
		$j("#beanName").val("");
		$j("#methodName").val("");
	});
	
	$j("#update").click(function(){
		var postData={};
		var id=	$j("#id").val();
		var timeExp=$j("#timeExp").val();
		var methodName=	$j("#methodName_1").val();
		var node=$j("#node").val();
		postData["ssTask.id"]=id;
		postData["ssTask.timeExp"]=timeExp;
		postData["ssTask.node"]=node;
		if(id==null||id==""){
			jumbo.showMsg("请选择需要修改的的定时任务！");
			return;
		}
		if(timeExp==null||timeExp==""){
			jumbo.showMsg("请输入修改后定时任务的执行时间！");
			return ;
		}
		if(node==null||node==""){
			jumbo.showMsg("请输入是否启用！");
			return ;
		}
		if(isNaN(node)){
			jumbo.showMsg("请输入数字！");
			return ;
		}
			if(window.confirm("你确定要修改定时任务"+methodName+"的执行时间？")){
			var re = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/updateSchedulerTask.json",postData);
				if(re.result=="error"){
					jumbo.showMsg("请输入正确的时间格式！或者是否启用值格式不对！");
					$j("#timeExp").focus();
				}
				if(re.result=="success"){
					jumbo.showMsg("定时任务执行时间已修改");
					$j("#tbl-timedtaskList").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/getSchedulerTaskList.json"),
						postData:loxia._ajaxFormToObj("searchForm")}).trigger("reloadGrid");
				}
			}
	});
	
	$j("#importLfTxt").click(function(event){
		if(!/^.*\.txt$/.test($j("#flTxt").val())){
			jumbo.showMsg("请选择正确的TXT导入文件");
			return false;
		}
		$j("#flTxtForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importLfConfig.do")
		);
		loxia.submitForm("flTxtForm");	
	});

});