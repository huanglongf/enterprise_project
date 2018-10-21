var baseUrl = $j("body").attr("contextpath");
$j(document).ready(function(){
	$j("#tbl-provide").jqGrid({
		url:baseUrl + "/findProvince.json",
		datatype: "json",
		colNames: ["ID","物流商","省份","省份编码","创建时间","创建人"],
		colModel: [
					{name:"id",index:"id",width:150,hidden:true,resizable:true},
					{name:"lpcode",index:"lpcode",width:150,resizable:true},
					{name:"province",index:"province",width:150,resizable:true},
					{name:"areaNumber",index:"areaNumber",width:150,resizable:true},
					{name:"createTime",index:"createTime",width:150,resizable:true},
					{name:"crateUserName",index:"crateUserName",width:150,resizable:true}
				 	],
     	caption: "入库作业单",
	   	sortname: 'id',
	   	multiselect: true,
		sortorder: "desc",
	   	shrinkToFit:false,
		height:"auto",
		viewrecords: true,
		rownumbers:true,
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		pager:"#pager",
		jsonReader: {repeatitems : false, id: "0"}
	});
	
	$j("#search").click(function(){
		var urls = baseUrl + "/findProvince.json";
		$j("#tbl-provide").jqGrid('setGridParam',{url:urls,page:1,postData:loxia._ajaxFormToObj("form_query")}).trigger("reloadGrid");
	});
	
	$j("#reset").click(function(){
		$j("#queryTable select").val("");
		$j("#province").val("");
		$j("#areaNumber").val("");
	});
	
	$j("#import").click(function(){
		var lpcode = $j("#lpcodes").val();
		var updateMode = $j("#updateMode").val();
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg("请选择正确的Excel导入文件");
			return;
		}
		if(updateMode ==""){
			jumbo.showMsg("请选择更新方式！");
			return;
		}
		loxia.lockPage();
		$j("#importForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/transProvinceImport.do?lpcode="+lpcode+"&updateMode="+updateMode));
		loxia.submitForm("importForm");
  		$j("#updateMode").attr("value","");
  		$j("#file").attr("value","");
	});
	
	$j("#delete").click(function(){
		var datalist = $j("#tbl-provide" ).jqGrid('getGridParam','selarrrow');
		if(datalist.length>0){
			loxia.lockPage();
			var postData = {};
			for(var i in datalist){
				postData['ids[' + i + ']']=datalist[i];
			}
			loxia.asyncXhrPost(baseUrl + "/deleteProvince.json",
					postData,
					{
						success:function(data){
								loxia.unlockPage();
								if(data){
									if(data.result=="success"){
										var urls = baseUrl + "/findProvince.json";
										$j("#tbl-provide").jqGrid('setGridParam',{url:urls,page:1,postData:loxia._ajaxFormToObj("form_query")}).trigger("reloadGrid");
										loxia.unlockPage();
									}else if(data.result=="error"){
										loxia.unlockPage();
										jumbo.showMsg(data.message);
									}
								} else {
									loxia.unlockPage();
									jumbo.showMsg("数据操作异常");
								}
							},
						error:function(){
							jumbo.showMsg("数据操作异常！");
							loxia.unlockPage();
						}//操作数据异常
					}
			);
		}else{
			jumbo.showMsg("请勾选您须要删除的数据！");
		}
	});
});

