$j.extend(loxia.regional['zh-CN'],{

});
var extBarcode = null;
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}
function showMsg(msg){
	jumbo.showMsg(msg);
}
var $j = jQuery.noConflict();

$j(document).ready(function (){
	var baseUrl = $j("body").attr("contextpath");
	loxia.init({debug: true, region: 'zh-CN'});
	jumbo.loadShopList("owner","id");
	initShopQuery("owner","innerShopCode");
	$j("#tbl_nike_vmi_inbound").jqGrid({
		//url:baseUrl+"/nikeCheckQuery.json",
		datatype: "json",
		colNames: ["ID","VMI入库单号","前置单据","状态","类型","创建时间","操作人"],
		colModel: [
		           {name: "id", index: "id", hidden: true},	
		           {name: "code", index: "checkCode", width: 150, resizable: true},
		           {name: "slipCode", index: "ownerCode", width: 100, resizable: true},
		           {name: "status", index: "status", width: 100, resizable: true},
		           {name: "type", index: "type", width: 100, resizable: true},
		           {name: "createDate", index: "createDate", width: 120, resizable: true},
	               {name: "operator", index: "operator", width: 100, resizable: true}
	               ],
		caption: "NIKE_VMI入库单",
	   	sortname: 'c.id',
		pager:"#pager",
	    multiselect: false,
		sortorder: "desc",
		height:"auto",
		rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var ids = $j("#tbl_nike_check").jqGrid('getDataIDs');
//			for(var i=0;i < ids.length;i++){
//				var datas = $j("#tbl_nike_check").jqGrid('getRowData',ids[i]);
//				var type= "系统调整"
//				if(datas["type"]=="2"){
//					type= "手动调整"
//				}
//				var status= "新建状态"
//				if(datas["status"]=="2"){
//					status= "准备写文件状态"
//				}
//				if(datas["status"]=="10"){
//					status= "完成状态"
//				}
//				$j("#tbl_nike_check").jqGrid('setRowData',ids[i],{"type":type});
//				$j("#tbl_nike_check").jqGrid('setRowData',ids[i],{"status":status});
//			}
		}
	});
	
	/**导入	 */
	$j("#import").click(function(){
		var file=$j.trim($j("#file").val()),errors=[];
		if(file==""||file.indexOf("xls")==-1){
			errors.push(i18("INVALID_MAND",i18("ENTITY_EXCELFILE")));
		}
		var shopId = $j("#owner").val(); 
		if(shopId == ''){errors.push("请选择入库店铺");}
		if(errors.length>0){
			jumbo.showMsg(errors.join("<br/>"));
		}else{
			$j("#importForm").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importcreatenikevmiinbound.do?shopId="+shopId));
			loxia.submitForm("importForm");
		}
	});
	
	$j("#search").click(function(){
		queryStaList();
	});
	
	

	/**
	 * 重置
	 */
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
	});
});

function queryStaList(){
	var url = $j("body").attr("contextpath") + "/findnikevmiinbound.json";
	$j("#tbl_nike_check").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
}
function importReturn(errors,success){
	jumbo.showMsg("导入成功！");
	var successMag ="";
	var errorsMsg ="";
	for(var x in errors){
		if(errors[x].errorMsg == '' || errors[x].errorMsg == null){
			errorsMsg += '第'+errors[x].no +'行，保存失败!<br/>';
		} else {
			errorsMsg += errors[x].errorMsg + '<br/>';
		}
	 }
	 for(var x in success){
		 successMag += '第'+success[x].no +'行，保存成功!<br/>';
	 }
	$j('#successMag').html(successMag);
	$j('#errorMsg').html(errorsMsg);
	queryStaList();
}
