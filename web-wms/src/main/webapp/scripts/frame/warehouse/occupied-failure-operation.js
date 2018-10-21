$j.extend(loxia.regional['zh-CN'],{
	
});
function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
var staStatus = {"value":"20:配货失败;25:冻结;1:新建"};
var trueOrFalse = {"value":"true:是;false:否;"};
$j(document).ready(function(){
	initShopQuery("companyshop","innerShopCode");
	jumbo.loadShopList("companyshop");
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
	});
	$j("#search").click(function(){
		var url = $j("body").attr("contextpath") + "/getalloccupiedstalist.json";
		$j("#tbl-orderList").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
	});
	$j("#tbl-orderList").jqGrid({
		url:$j("body").attr("contextpath") + "/getalloccupiedstalist.json",
		datatype: "json",
		colNames: ["ID","作业单号","相关单据号","创建时间","状态","店铺","是否存在占用","操作"],
		colModel: [
				{name:"id", index:"id", hidden: true},
				{name:"code", index:"code", formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, width:100, resizable:true},
				{name:"refSlipCode",index:"refSlipCode",width:100,resizable:true},
				{name:"createTime", index:"ceateTime", width: 120, resizable: true},
				{name:"intStaStatus",index:"intStaStatus",width:70,resizable:true,formatter:'select',editoptions:staStatus},
				{name:"owner",index:"owner",width:130,resizable:true},
				{name:"isHaveOccupation",index:"isHaveOccupation",width:80,resizable:true,formatter:'select',editoptions:trueOrFalse},
				{name:"idForBtn", width:300,resizable:true}
					 	],
		caption:"单据列表",
	   	sortname: 'createTime',
	    multiselect: true,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#pager",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id:"0"},
		gridComplete: function(){
			var ids = $j("#tbl-orderList").jqGrid('getDataIDs');
			var bt1 ='<button type="button" class="confirm" name="btnOc" loxiaType="button">重新占用</button>';
			var bt2 ='<button type="button" class="confirm" name="btnCancel" loxiaType="button">整单取消</button>';
			var bt3 ='<button type="button" class="confirm" name="btnSend" loxiaType="button">部分发货</button>';
			//获取模糊查询选中的店铺，循环判断勾选弹窗的checkBox
			for(var j = 0 ; j < ids.length ; j++){
				var btn=bt1;
				var data=$j("#tbl-orderList").jqGrid("getRowData",ids[j]);
				var intStatus = $j("#tbl-orderList").getCell(ids[j],"intStaStatus");
				if(intStatus=="20"){
					btn = bt1;
				}
				else{
					if(intStatus=="1"){
						btn = bt1;
					}else{
						var ic = $j("#tbl-orderList").getCell(ids[j],"isHaveOccupation");
						console.log(j+ic);
						if(ic=="true"){
							btn = bt1+bt2+bt3;
						}else{
							btn = bt1+bt2;
						}					
					}
				}
				$j("#tbl-orderList").jqGrid('setRowData',ids[j],{"idForBtn":btn});
			}
			loxia.initContext($j(this));
			$j("button[name='btnOc']").click(function(){
				var id =$j(this).parents("tr").attr("id");
				reOccupied(id);
			});
			$j("button[name='btnCancel']").click(function(){
				var id =$j(this).parents("tr").attr("id");
				cancelById(id);
			});
			$j("button[name='btnSend']").click(function(){
				var id =$j(this).parents("tr").attr("id");
				partySend(id);
			});
		}	
	});
	$j("#tbl-orderList-detail").jqGrid({
		datatype: "json",
		colNames: ["ID","作业单号","相关单据号","sku条码","是否取消","是否占用"],
		colModel: [
				{name:"id", index:"id", hidden: true},
				{name:"code", index:"code",width:100, resizable:true},
				{name:"refSlipCode",index:"refSlipCode",width:100,resizable:true},
				{name:"barCode", index:"barCode", width: 120, resizable: true},
				{name:"isCancel",index:"isCancel",width:70,resizable:true,formatter:'select',editoptions:trueOrFalse},
				{name:"isHaveOccupation",index:"isHaveOccupation",width:130,resizable:true,formatter:'select',editoptions:trueOrFalse}
					 	],
		caption:"明细列表",
	   	sortname: 'line.id',
	    multiselect: false,
	    //rowNum: jumbo.getPageSize(),
		//rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	//pager:"#pager1",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id:"0"}
	});
	$j("#batchOcc").click(function(){
		var staIds = $j("#tbl-orderList").jqGrid('getGridParam','selarrrow');
		if(staIds.length>0){
			var postData = {};
			for(var i in staIds){
				postData['staIdList[' + i + ']']=staIds[i];
			}
			var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/reoccupationbyidlist.json",postData);
			if(result&&result.result){
				jumbo.showMsg("已重新占用");
			}
		}else{
			jumbo.showMsg("请选择需要操作的单据");
		}
	});
	$j("#back").click(function(){
		$j("#orderList").removeClass("hidden");
		$j("#div2").addClass("hidden");
	});
});
/**
 * 重新占用该单
 */
function reOccupied(id){
	var postData = {};
	postData['staIdList[0]']=id;
	var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/reoccupationbyidlist.json",postData);
	if(result){
		jumbo.showMsg("已重新占用");
		$j("#search").trigger("click");
	}
}
/**
 * 该单整单取消
 * @param id
 */
function cancelById(id){
	var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/canceladorderbyid.json",{"sta.id":id});
	if(result){
		$j("#search").trigger("click");
	}
}
/**
 * 该单部分发货
 * @param id
 */
function partySend(id){
	var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/partysendadorderbyid.json",{"sta.id":id});
	if(result){
		$j("#search").trigger("click");
	}
}

function showDetail(obj){
	var id = $j(obj).parents("tr[id]").attr("id");
	$j("#div2").removeClass("hidden");
	$j("#orderList").addClass("hidden");
	$j("#tbl-orderList-detail").jqGrid('setGridParam',{page:1,url:$j("body").attr("contextpath") + "/showoccdetail.json?sta.id=" + id}).trigger("reloadGrid");
}