
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}



	$j(document).ready(function (){
		$j("#tabs").tabs();
		var baseUrl = $j("body").attr("contextpath");
		
	
		$j("#tbl-modify-list").jqGrid({
			url:baseUrl + "/findModifyLogList.json",
			datatype: "json",
			colNames: ["ID","修改时间","名称","编码","条码","货号","品牌","宽度","高度","长度","是否sn","销售模式","毛重","预警天数","编码1","编码2","编码3","jmCode","扩展属性"],
			colModel: [
								{name: "id", hidden: true},
								{name:"lastModifyTime",index:"lastModifyTime",fixed:true,width:120,resizable:true},
								{name:"name",index:"name",fixed:true,width:100,resizable:true},
								{name:"code",index:"code",fixed:true,width:120, resizable:true},
								{name:"barCode",index:"barCode",fixed:true,width:120,resizable:true},
								{name:"supplierCode",index:"supplierCode",width:120,resizable:true},
								{name:"brandName",index:"brandName",width:80,resizable:true},
								{name:"width",index:"width",width:30,resizable:true},
								{name:"height",index:"height",width:30,resizable:true},
								{name:"length",index:"length",width:30,resizable:true},
								{name:"isSnSku",index:"isSnSku",width:40,edittype:'select', formatter:'select', editoptions:{value:"true:是;false:否"},resizable:true},
								{name:"salesModel",index:"salesModel",width:100,edittype:'select', formatter:'select', editoptions:{value:"0:PAYMENT;1:CONSIGNMENT;2:SETTLEMENT"},resizable:true},
								{name:"grossWeight",index:"grossWeight",width:30,resizable:true},
								{name:"warningDate",index:"warningDate",width:40,resizable:true},
								{name:"extensionCode1",index:"extensionCode1",width:120,resizable:true},
								{name:"extensionCode2",index:"extensionCode2",width:120,resizable:true},
								{name:"extensionCode3",index:"extensionCode3",width:120,resizable:true},
								{name:"jmCode",index:"jmCode",width:120,resizable:true},
								{name:"keyProperties",index:"keyProperties",width:120,resizable:true}
					 		],
			caption: "商品信息修改日志",
		   	sortname: 'id',
		   	multiselect: false,
			sortorder: "desc",
			pager:"#pager",
			width:"1000",
		   	shrinkToFit:false,
			height:"auto",
			rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
			viewrecords: true,
		   	rownumbers:true,
			jsonReader: {repeatitems : false, id: "0"}
		});
		
		//查询
		$j("#search").click(function(){
				var postData = loxia._ajaxFormToObj("channelForm");
				$j("#tbl-modify-list").jqGrid('setGridParam',{
					url:window.$j("body").attr("contextpath")+"/findModifyLogList.json",
					postData:postData,
					page:1
				}).trigger("reloadGrid");
		});
		
		
	});
	



