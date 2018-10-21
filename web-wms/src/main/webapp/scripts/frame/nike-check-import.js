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
	
	$j("#tbl_nike_check").jqGrid({
		//url:baseUrl+"/nikeCheckQuery.json",
		datatype: "json",
		colNames: ["ID","调整单编号","店铺","状态","类型","UPC","创建时间","同步时间","数量","操作人","备注"],
		colModel: [
		           {name: "id", index: "id", hidden: true},	
		           {name: "checkCode", index: "checkCode", width: 150, resizable: true},
		           {name: "ownerCode", index: "ownerCode", width: 100, resizable: true},
		           {name: "status", index: "status", width: 100, resizable: true},
		           {name: "manualType", index: "manualType", width: 100, resizable: true},
		           {name: "upc", index: "upc", width: 100, resizable: true},
		           {name: "createDate", index: "createDate", width: 120, resizable: true},
		           {name: "finishDate", index: "finishDate", width: 120, resizable: true},
	               {name: "quantity", index: "quantity", width: 80, resizable: true},
	               {name: "operator", index: "operator", width: 100, resizable: true},
	               {name: "remark", index: "remark", width: 240, resizable: true}
	               ],
		caption: "NIKE调整单同步",
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
			for(var i=0;i < ids.length;i++){
				var datas = $j("#tbl_nike_check").jqGrid('getRowData',ids[i]);
				var manualType= "";
				if(datas["manualType"]=="11"){
					manualType= "RSO调整"
				}else if(datas["manualType"]=="13"){
					manualType= "J0调整"
				}else if(datas["manualType"]=="15"){
					manualType= "财务调整"
				}else if(datas["manualType"]=="99"){
					manualType= "其他调整"
				} else {
					manualType= "未知类型"
				}
				var status= "新建状态"
				if(datas["status"]=="2"){
					status= "准备写文件状态"
				}
				if(datas["status"]=="10"){
					status= "完成状态"
				}
				$j("#tbl_nike_check").jqGrid('setRowData',ids[i],{"manualType":manualType});
				$j("#tbl_nike_check").jqGrid('setRowData',ids[i],{"status":status});
			}
		}
	});
	
	/**导入	 */
	$j("#import").click(function(){
		var file=$j.trim($j("#file").val()),errors=[];
		if(file==""||file.indexOf("xls")==-1){
			errors.push(i18("INVALID_MAND",i18("ENTITY_EXCELFILE")));
		}
		if($j("#check_manualType").val() == ""){
			jumbo.showMsg("请选择类型");
			return;
		}
		if(errors.length>0){
			jumbo.showMsg(errors.join("<br/>"));
		}else{
			if(window.confirm('你确定要导入？')){
				$j("#importForm").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importNikecheckReceive.do"));
				loxia.submitForm("importForm");
			}
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
	var url = $j("body").attr("contextpath") + "/nikeCheckQuery.json";
	$j("#tbl_nike_check").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
}
function importReturn(){
	jumbo.showMsg("导入成功！");
	$j("#importForm input,#importForm select").val("");
	queryStaList();
}
