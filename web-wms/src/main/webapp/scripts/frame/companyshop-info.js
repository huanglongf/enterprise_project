$j.extend(loxia.regional['zh-CN'],{

});


$j(document).ready(function (){
	$j("#tabs_m,#tabs").tabs();
     
	$j("#tbl_wh").jqGrid({
		url:$j("body").attr("contextpath")+"/findWarehouse2.json",
		datatype: "json",
		colNames: ["ID","仓库编码","全称"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name :"code",index:"code",width:150,resizable:true},
					{name:"name", index:"name" ,width:200,resizable:true}],
		caption: "仓库列表",
		rowNum:-1,
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: false,
		sortorder: "desc",
		jsonReader: { repeatitems : false, id: "0" }
	});

	$j("#tbl_trans").jqGrid({
		url:$j("body").attr("contextpath")+"/findLogistics2.json",
		datatype: "json",
		colNames: ["ID","物流商名称","物流商简称"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name :"name",index:"name",width:150,resizable:true},
					{name:"expCode", index:"expCode" ,width:200,resizable:true}],
		caption: "物流列表",
		rowNum:-1,
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: false,
		sortorder: "desc",
		jsonReader: { repeatitems : false, id: "0" }
	});
});
