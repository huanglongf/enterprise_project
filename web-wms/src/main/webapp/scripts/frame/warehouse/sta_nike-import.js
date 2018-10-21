/**
 * 执行批量导入NIKE收货-导入箱号关系
 */




$j(document).ready(function (){
	$j("#sn_import").click(function(){
			if(!/^.*\.xls$/.test($j("#file").val())){
				jumbo.showMsg("请选择要导入的文件！");
				return;
			}

			$j("#importForm").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importNikeRelation.do"));
			loxia.submitForm("importForm");
		
		});
	
	var postData0 = {};
	$j("#tbl-dispatch-list").jqGrid({
		url:$j("body").attr("contextpath") + "/json/queryNikeRelation.json",
		datatype: "json",
		colNames: ["ID","系统箱号","实物箱号"],
		colModel: [{name: "id", index: "id", hidden: true},
				   {name:"sysPid",index:"sysPid",width:150,resizable:true},
	               {name:"enPid",index:"enPid",width:150,resizable:true}],
		caption: 'NIKE收货-导入箱号关系列表',
   		sortname: 'ID',
  		multiselect: false,
		sortorder: "desc",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	viewrecords: true,
   		rownumbers:true,
	   	pager:"#pager",
	   	jsonReader: { repeatitems : false, id: "0" },
	   	postData:postData0
	});
	
	$j("#search").click(function(){
		
		var postData=loxia._ajaxFormToObj("plForm");
		$j("#tbl-dispatch-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/queryNikeRelation.json"),
			postData:postData}).trigger("reloadGrid",[{page:1}]);
	});
	
	
	$j("#reset").click(function(){
		document.getElementById("plForm").reset();
	});
});