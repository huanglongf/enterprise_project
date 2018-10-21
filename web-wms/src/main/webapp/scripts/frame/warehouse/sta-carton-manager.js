function showMsg(msg){
	jumbo.showMsg(msg);
}
var $j = jQuery.noConflict();
var staType;
var staStatus;
$j(document).ready(function (){
	
	var baseUrl = $j("body").attr("contextpath");
	initShopQuery("companyshop","innerShopCode");
	jumbo.loadShopList("companyshop");
	loxia.init({debug: true, region: 'zh-CN'});
	staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	$j("#tbl_sta_list").jqGrid({
		url:baseUrl+"/findInBoundCartonSta.json",
		datatype: "json",
		colNames: ["ID","作业单号","相关单据号","辅助相关单据号","状态","作业类型","店铺","纸箱数","创建时间","备注","操作"],
		colModel: [
		           {name: "id", index: "id", hidden: true},	
		           {name: "code", index: "code", width: 130, resizable: true,sortable:false},
		           /*{name: "code", index: "code",formatter:"linkFmatter", formatoptions:{onclick:"showdetail"}, width: 110, resizable: true,sortable:false},*/
		           {name: "refSlipCode", index: "refSlipCode", width: 120, resizable: true},
		           {name: "slipCode1", index: "slipCode1", width: 120, resizable: true},
		           {name: "intStaStatus", index: "intStaStatus", width: 80, resizable: true,formatter:'select',editoptions:staStatus},
	               {name: "intStaType", index: "intStaType", width: 120, resizable: true,formatter:'select',editoptions:staType},
	               {name: "channelName", index: "channelName", width: 120, resizable: true},
	               {name: "cartonNum", index: "cartonNum", formatter:"loxiaInputFmatter", formatoptions:{loxiaType:"number",min:"0"},width: 80, resizable: true,sortable:false},
	               {name: "createTime", index: "createTime", width: 160, resizable: true},
	               {name: "memo", index: "memo", width: 120, resizable: true},
	               {name:"btnEdit",index:"btnEdit",width:80,resizable:true}
	               ],
		caption: "入库作业单",
	   	sortname: 'sta.id',
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
			var btn1 = '<div><button type="button" style="width:70px;" class="confirm" name="btnEdit" loxiaType="button" onclick="cartonEdit(this);">'+"编辑"+'</button></div>';
			var ids = $j("#tbl_sta_list").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				$j("#tbl_sta_list").jqGrid('setRowData',ids[i],{"btnEdit":btn1});
			}
			loxia.initContext($j(this));
		}
	});

	$j("#search").click(function(){
		queryStaList();
	});
	
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
	});
	
	
	
	$j("#back").click(function(){
		$j("#divDetial").addClass("hidden");
		$j("#divHead").removeClass("hidden");
	});
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});

	
	/**纸箱导入事件	 */
	$j("#carton-import").click(function(){
		var file=$j.trim($j("#carton-num-file").val()),errors=[];
	
		if(file==""||file.indexOf("xls")==-1){
			jumbo.showMsg("请选择正确的excel导入文件");
			return;
		}
		
		if(errors.length>0){
			jumbo.showMsg(errors.join("<br/>"));
			return;
		}else{
			console.log("hahah");
			$j("#importForm").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importCartonNum.do"));
			loxia.submitForm("importForm");
			console.log("qaaaa");
		}
		
	});
});


function queryStaList(){
	var url = $j("body").attr("contextpath") + "/findInBoundCartonSta.json";
	$j("#tbl_sta_list").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_sta_list",{"intStaType":"whSTAType","intStaStatus":"whSTAStatus"},
		url,loxia._ajaxFormToObj("queryForm"));
}


//修改纸箱数量
function cartonEdit(obj){
	//获取选中行的数据
	var data=$j("#tbl_sta_list").jqGrid("getRowData",$j(obj).parents("tr").attr("id"));
	if(data['cartonNum']==null || data['cartonNum']==undefined){
		jumbo.showMsg("请输入纸箱数量");
		return;
	}
	if(data['intStaStatus']===null){
		jumbo.showMsg("作业单状态异常，无法修改！");
		return;
	}
	
	if(data['intStaStatus']*1 >=10){
		jumbo.showMsg("作业单已完成或冻结，不可修改纸箱数");
		return ;
	}
	var jsonCartonNum={"staId":data['id'],
			"cartonNum":data['cartonNum']
	}
	var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/editCartonNumSta.json",jsonCartonNum);//修改作业单纸箱数量
	if(rs!=null && rs.flag=='success'){
		jumbo.showMsg("修改成功！");
		var url=$j("body").attr("contextpath")+"/findInBoundCartonSta.json";
		$j("#tbl_sta_list").jqGrid('setGridParam',{url:url}).trigger("reloadGrid");
	}else{
		jumbo.showMsg("修改失败！");
	}
}



function importReturn(){
	jumbo.showMsg("导入成功");
	$j("#file").val("");
}



