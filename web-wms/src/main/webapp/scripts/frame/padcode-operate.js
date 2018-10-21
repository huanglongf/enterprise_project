$j.extend(loxia.regional['zh-CN'],{
	
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

$j(document).ready(function (){
	var baseUrl = $j("body").attr("contextpath");
	$j("#query-info").jqGrid({
		url:baseUrl+"/queryPadcodeOperate.json",
		postData : loxia._ajaxFormToObj("quert-form"),
		datatype: "json",
		colNames: ["id","编码"],
		colModel: [{name: "id", index: "id", width: 100, hidden: true},
		           {name: "optionValue", index: "optionValue", width: 150, resizable: true}],
		caption: "PDA器编码查寻",
		multiselect: true,
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		sortorder: "desc", 
		jsonReader: { repeatitems : false, id: "0" },
		height:jumbo.getHeight(),
		gridComplete: function(){
			
		}
	});
	
	$j("#submit").click(function(){
		$j("#query-info").jqGrid('setGridParam',{url:baseUrl + "/queryPadcodeOperate.json",postData:loxia._ajaxFormToObj("quert-form")}).trigger("reloadGrid");
	});
	
	$j("#deleteSelect").click(function(){
		var datalist = $j("#query-info" ).jqGrid('getGridParam','selarrrow');
		if(datalist.length>0){
			loxia.lockPage();
			var postData = {};
			for(var i in datalist){
				postData['ids[' + i + ']']=datalist[i];
			}
			loxia.asyncXhrPost(baseUrl + "/deletePadcodeOperate.json",
					postData,
					{
						success:function(data){
								loxia.unlockPage();
								if(data){
									if(data.result=="success"){
										$j("#query-info").jqGrid('setGridParam',{url:baseUrl + "/queryPadcodeOperate.json",postData:loxia._ajaxFormToObj("quert-form")}).trigger("reloadGrid");
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
			jumbo.showMsg("请勾选您须要删除的PDA编码");
		}
	});
	
	$j("#save").click(function(){
		var code = $j.trim($j("#addCode").val());
		if(code == ""){
			jumbo.showMsg("请填写");
		} else {
			loxia.lockPage();
			var postData = {};
			postData["code"]=code
			loxia.asyncXhrPost(baseUrl + "/savePadcodeOperate.json",
					postData,
					{
						success:function(data){
								loxia.unlockPage();
								if(data){
									if(data.result=="success"){
										$j("#addCode").val("")
										$j("#query-info").jqGrid('setGridParam',{url:baseUrl + "/queryPadcodeOperate.json",postData:loxia._ajaxFormToObj("quert-form")}).trigger("reloadGrid");
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
			
		}
	});
});


