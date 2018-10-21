$j(document).ready(function (){

	$j("#tbl-inventory-list").jqGrid({
		datatype: "json",
		colNames: ["Field1","Field2","Field3","Field4","Field5","Field6","Field7","Field8","Field9","Field10","Field11","Field12","Field13","Field14","Field15"],
		colModel: [ {name:"Field1",width:100, resizable:true},
		            {name:"Field2",width:100, resizable:true},
		            {name:"Field3",width:100, resizable:true},
		            {name:"Field4",width:100, resizable:true},
		            {name:"Field5",width:100, resizable:true},
		            {name:"Field6",width:100, resizable:true},
		            {name:"Field7",width:100, resizable:true},
		            {name:"Field8",width:100, resizable:true},
		            {name:"Field9",width:100, resizable:true},
		            {name:"Field10",width:100, resizable:true},
		            {name:"Field11",width:100, resizable:true},
		            {name:"Field12",width:100, resizable:true},
		            {name:"Field13",width:100, resizable:true},
		            {name:"Field14",width:100, resizable:true},
		            {name:"Field15",width:100, resizable:true}     
		],
		caption: '数据列表',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pager",
	   	height:"auto",
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});

});