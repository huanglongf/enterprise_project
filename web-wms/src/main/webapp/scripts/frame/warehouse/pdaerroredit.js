$j.extend(loxia.regional['zh-CN'],{
	CODE:"作业单号",
	CREATETIME:"创建时间",
	STATUS:"状态",
	MEMO:"失败原因",
	SKUCODE:"商品编码",
	LOCATIONCODE:"库位",
	INVENTORYSTATUS:"库存状态",
	DIRECTION:"作业方向",
	OPERATOR:"操作人",
	NUMBER:"数量",
	OPERATION:"操作",
	STALIST:"失败作业单列表",
	DETAILLIST:"作业单明细列表",
	STATUSCHANGE:"重置状态",
	SAVE:"保存",
	LOCATION_NOT_FOUNDED: "输入的库位  {0} 系统不存在",
	DATA_ERROR:"数据错误",
	INVALID_EMPTY_DATA:"数据必填"
});
function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
var baseUrl = "";
$j(document).ready(function(){
	baseUrl = $j("body").attr("contextpath");
	var statusName={"value":"0:执行失败"};
	var directionName={"value":"1:入库;2:出库;"};
	$j("#staList").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("CODE"),i18("CREATETIME"),i18("STATUS"),i18("MEMO"),i18("STATUSCHANGE")],
		colModel: [ 
		           {name: "id", index: "id", hidden: true},
		           {name: "orderCode", index:"orderCode", formatter:"linkFmatter",formatoptions:{onclick:"showStaLine"},resizable: true},
		           {name: "createTime", index:"createTime", resizable: true},
				   {name: "intStatus", index: "intStatus", width: 60, resizable: true,formatter:'select',editoptions:statusName},
		           {name: "memo", index: "memo",width: 400, resizable: true},
		           {name: "idForBtn", width: 100,resizable:true, formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("STATUSCHANGE"), onclick:"changeStatus(this,event);"}}}
		           ],
		caption: i18("STALIST"),
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	sortname: 'id',
	    pager: '#staList_pager',
		sortorder: "desc", 
		height:'auto',
		viewrecords: true,
   		rownumbers:true,
   		gridComplete : function(){
			loxia.initContext($j(this));
		}, 
		jsonReader: { repeatitems : false, id: "0" }
	});
	//var url = baseUrl + "/findallavaillocations.json";
	$j("#detailList").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("SKUCODE"),i18("LOCATIONCODE"),i18("INVENTORYSTATUS"),i18("DIRECTION"),i18("OPERATOR"),i18("NUMBER"),i18("OPERATION")],
		colModel: [ 
		           {name: "id", index: "id", hidden: true},
		           {name: "skuCode", index:"skuCode", resizable: true},
		           {name: "locationCode", index:"locationCode", resizable: true,formatter:"loxiaInputFmatter", formatoptions:{loxiaType:"input",mandatory:"true",trim:"true",checkmaster:"onloadLocation"}},
				   {name: "invStatus", index: "invStatus", width:100, resizable: true},
		           {name: "intDirection", index: "intDirection",width: 60, resizable: true,formatter:'select',editoptions:directionName},
		           {name: "operator", index:"operator", resizable: true},
				   {name: "qty", index: "qty", width: 100, resizable: true},
				   {name: "idForBtn", width: 100,resizable:true, formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("SAVE"), onclick:"saveDetail(this,event);"}}}
		           ],
		caption: i18("DETAILLIST"),
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	sortname: 'id',
		sortorder: "desc", 
		height:'auto',
		viewrecords: true,
   		rownumbers:true,
   		gridComplete : function(){
			$j("#detailList tr:gt(0)").each(function(){
				$j(this).children("td:eq(3)").children("input").attr("aclist","/wms/findAvailLocation.json").attr("trim","true");
			});
			loxia.initContext($j(this));
		},
		jsonReader: { repeatitems : false, id: "0" }
	});
	$j("#search").click(function(){
		var postData = loxia._ajaxFormToObj("queryForm");
		$j("#staList").jqGrid('setGridParam',{
			url:loxia.getTimeUrl($j("body").attr("contextpath")+"/geterrorpdaloglist.json"),
			postData:postData,
			page:1
		}).trigger("reloadGrid");
	});
	$j("#reset").click(function(){
		$j("#queryForm input").val("");
	});
	$j("#back").click(function(){
		$j("#div2").addClass("hidden");
		$j("#div1").removeClass("hidden");
	});
});
function changeStatus(tag,event){
	if(confirm("确定修改？")){
		var id = $j(tag).parents("tr").attr("id");
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/updatepdaorderstatus.json",{"pc.id":id});
		if(result.flag=="true"){
			$j("#search").trigger("click");
		}else{
			jumbo.showMsg("状态更新失败");
		}
	}
}
function showStaLine(tag){
	var id = $j(tag).parents("tr").attr("id");
	$j("#pcode").html($j(tag).parents("tr").children("td:eq(2)").attr("title"));
	$j("#ptime").html($j(tag).parents("tr").children("td:eq(3)").attr("title"));
	$j("#pmemo").html($j(tag).parents("tr").children("td:eq(5)").attr("title"));
	$j("#detailList").jqGrid('setGridParam',{
		url:loxia.getTimeUrl($j("body").attr("contextpath")+"/geterrorpdalogdetaillist.json"),
		postData:{"pc.id":id},
		page:1
	}).trigger("reloadGrid");
	$j("#div1").addClass("hidden");
	$j("#div2").removeClass("hidden");
}
function saveDetail(tag){
		if($j(tag).parents("tr").children("td:eq(3)").children("input").val()==""){
			loxia.tip($j(tag).parents("tr").children("td:eq(3)").children("input"),"不可填入空值!");
			return;
		}
		var msg = onloadLocation($j(tag).parents("tr").children("td:eq(3)").children("input").val(),$j(tag).parents("tr").children("td:eq(3)").children("input"));
		if(msg!="success"){
			loxia.tip($j(tag).parents("tr").children("td:eq(3)").children("input"),msg);
		}else{
			if(confirm("确定修改内容?")){
				var postData={};
				postData["pc.id"]=$j(tag).parents("tr").attr("id");
				postData["pc.orderCode"]=$j(tag).parents("tr").children("td:eq(3)").children("input").val();
				var rs = loxia.syncXhrPost(baseUrl+"/updateerrorpdalinelocation.json",postData);
				if(rs&&rs.result=="success"){
					jumbo.showMsg("库位修改成功!");
				}else{
					jumbo.showMsg("库位修改失败!");
				}
			}
		}
}
function onloadLocation(value,obj){
	if(!$j.trim(value)){
		return loxia.SUCCESS;
	}else{
		 var postData = {};
		 postData["locationCode"] = value;
		 var rs = loxia.syncXhrPost(baseUrl+"/findlocationbycode.json", postData);
		 if(rs){
			if(rs.result == "success"){
				$j(obj.element).parent().parent().attr("locationId",rs.location.id);
				return loxia.SUCCESS;
			}else if (rs.result == 'error'){
				return i18("LOCATION_NOT_FOUNDED", value); //"输入的库位 ' "+value+" '  系统不存在";
			}else{
				return i18("DATA_ERROR"); // 数据错误
			}
		 }
	}
	return loxia.SUCCESS;
}
