var $j = jQuery.noConflict();

$j.extend(loxia.regional['zh-CN'],{
	CODE : "集货口编码",
	NAME : "集货口名称",
	CREATE_TIME : "创建时间",
	ROLE_LIST : "自定义集货库位配置"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

function showMsg(msg){
	jumbo.showMsg(msg);
}

$j(document).ready(function (){
	$j("#physicalId2").addClass("hidden");
	var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/findPhysicalWarehouse.do");
	$j("<option value=''>"+"-请选择-"+"</option>").appendTo($j("#physicalId"));
	$j("<option value=''>"+"-请选择-"+"</option>").appendTo($j("#physicalId1"));
	$j("<option value=''>"+"-请选择-"+"</option>").appendTo($j("#physicalId3"));
	$j("<option value=''>"+"-请选择-"+"</option>").appendTo($j("#physicalId1"));
	$j("<option value=''>"+"-请选择-"+"</option>").appendTo($j("#pickModel"));
	$j("<option value='拣货模式1'>"+"-拣货模式1-"+"</option>").appendTo($j("#pickModel"));
	$j("<option value='拣货模式2'>"+"-拣货模式2-"+"</option>").appendTo($j("#pickModel"));
	$j("<option value=''>"+"-请选择-"+"</option>").appendTo($j("#pickModel_1"));
	$j("<option value='拣货模式1'>"+"-拣货模式1-"+"</option>").appendTo($j("#pickModel_1"));
	$j("<option value='拣货模式2'>"+"-拣货模式2-"+"</option>").appendTo($j("#pickModel_1"));
	for(var idx in result){
		$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#physicalId"));
		$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#physicalId1"));
		$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#physicalId3"));
	}
	
	$j("#dialog_addWeight").dialog({title: "编辑", modal:true, autoOpen: false, width: 400, height: 360});
	$j("#tbl-shipping-point").jqGrid({
		//url:window.$j("body").attr("contextpath")+"/json/shippingPointCollectionQuery.do",
		datatype: "json",
		colNames: ["ID","集货区域编码","通道","拣货模式","顺序","弹出口","物理仓","更新人","更新时间","状态","操作"],
		colModel: [{name: "id", index: "id", hidden: true},		         
		           {name: "collectionCode", index: "collectionCode", width: 120},
		           {name: "passWay", index: "passWay", width: 120,resizable: true},
		           {name: "pickModel", index: "pickModel", width: 120,resizable: true},
		           {name: "sort", index: "sort", width: 100, resizable: true},
		           {name: "popUpCode", index: "popUpCode", width: 120, resizable: true},
		           {name: "popUpAreaName", index: "popUpAreaName", width: 120, resizable: true},
		           {name: "userName", index: "userName", width: 120, resizable: true},
		           {name: "modifyDate", index: "modifyDate", width: 120, resizable: true},
		           {name: "statusName", index: "statusName", width: 120, resizable: true},
				   {name:"idForBtn",index:"idForBtn", width: 100,resizable:true,sortable: false, formatter:"buttonFmatter", formatoptions:{"buttons":{label:"修改", onclick:"editLpCodeWeight(this)"}}}],
		caption: i18("ROLE_LIST"),//集货点列表
	   	sortname: 'id',
	   	pager:"#pager",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:"auto",
	   	width:"auto",
		multiselect: true,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc", 
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	//查询
	$j("#search").click(function(){
		var physicalId=$j("#physicalId").val();
		if(null==physicalId||""==physicalId){
			jumbo.showMsg("物理仓不可为空!");
			return false;
		}
		 var postData = loxia._ajaxFormToObj("form_query");  
			$j("#tbl-shipping-point").jqGrid('setGridParam',{
				url:$j("body").attr("contextpath")+"/shippingPointCollectionQuery.json",page:1,postData:postData}).trigger("reloadGrid");
	});
	
	
	//重置
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#physicalId").val("");
	});
	
	//新增
	$j("#add").click(function(){
		$j("#physicalId1").val("");
		$j("#physicalId1").removeClass("hidden");
		$j("#physicalId2").addClass("hidden");
		$j("#collectionCode").attr("readonly",false);
		$j("#dialog_addWeight input").val("");
		$j("#t_wh_goods_collection_id").val("");
		$j("#dialog_addWeight").dialog("open");
	});
	
	
	$j("#saveWeight").click(function(){
		var collectionCode=$j("#collectionCode").val();
		var physicalId=$j("#physicalId1").val();
		var physicalId2=$j("#physicalId2").val();
		var sort=$j("#sort").val();
		var popUpCode=$j("#popUpCode").val();
		var passWay=$j("#passWay_1").val();
		var pickModel=$j("#pickModel_1").val();
		var re = /^[0-9]*[1-9][0-9]*$/ ;
		if((null==physicalId||""==physicalId)&&(null==physicalId2||""==physicalId2)){
			jumbo.showMsg("物理仓不可为空!");
			return false;
		}
		if(null==collectionCode||""==collectionCode){
			jumbo.showMsg("集货区域编码不可为空!");
			return false;
		}
		if(null==sort||""==sort){
			jumbo.showMsg("顺序不可为空!");
			return false;
		}
		if(null!=sort||""!=sort){
			if(!re.test(sort)){
				jumbo.showMsg("顺序必须为正整数");
				return false;
			}
		}
		if(passWay!=null||passWay!=""){
			if(!re.test(passWay)){
				jumbo.showMsg("通道必须为正整数");
				return false;
			}
		}else{
			jumbo.showMsg("通道必填");
			return false;
		}
		if(null!=popUpCode&&""!=popUpCode){
			var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/checkPopupArea.do", {"popUpCode":popUpCode});
            if(!result["msg"]){
            	jumbo.showMsg("弹出口编码不存在");
				return false;
            }
		}
		var id=$j("#t_wh_goods_collection_id").val();
		if(null!=id&&""!=id){
			var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/updateShippingPointCollection.do", {"collectionCode" : collectionCode,"sort":sort,"popUpCode":popUpCode,"id":id,"passWay":passWay,"pickModel":pickModel});
			if("success"==result["msg"]){
				jumbo.showMsg("保存成功");
				$j("#dialog_addWeight").dialog("close");
				$j("#t_wh_goods_collection_id").val("");
				var physicalId=$j("#physicalId").val();
				if(null!=physicalId){
					var postData = loxia._ajaxFormToObj("form_query");  
					$j("#tbl-shipping-point").jqGrid('setGridParam',{
						url:$j("body").attr("contextpath")+"/shippingPointCollectionQuery.json",page:1,postData:postData}).trigger("reloadGrid");
				}
			}else if("false"==result["msg"]){
				jumbo.showMsg("集货区域编码已在使用中!");
				return false;
			}else if("error"==result["msg"]){
				jumbo.showMsg("顺序已存在!");
				return false;
			}else if("no"==result["msg"]){
				jumbo.showMsg("数据未找到!");
				return false;
			}
		}else{
			var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/saveshippingpointcollection.do", {"collectionCode" : collectionCode,"sort":sort,"popUpCode":popUpCode,"physicalId":physicalId,"passWay":passWay,"pickModel":pickModel});
			if("success"==result["msg"]){
				jumbo.showMsg("保存成功");
				$j("#dialog_addWeight").dialog("close");
				$j("#t_wh_goods_collection_id").val("");
				if(null!=physicalId){
					var postData = loxia._ajaxFormToObj("form_query");  
					$j("#tbl-shipping-point").jqGrid('setGridParam',{
						url:$j("body").attr("contextpath")+"/shippingPointCollectionQuery.json",page:1,postData:postData}).trigger("reloadGrid");
				}
			}else if("false"==result["msg"]){
				jumbo.showMsg("集货区域编码已存在!");
				return false;
			}else if("error"==result["msg"]){
				jumbo.showMsg("顺序已存在!");
				return false;
			}
		}
		
	});
	
	$j("#closediv").click(function(){
		$j("#dialog_addWeight").dialog("close");
		$j("#t_wh_goods_collection_id").val("");
	});
	

   
	/**导入	 */
	$j("#import").click(function(){
		var file=$j.trim($j("#file").val()),errors=[];
		if(file==""||file.indexOf("xls")==-1){
			errors.push("请选择文件");
		}
		var physicalId3=$j("#physicalId3").val();
		if(null==physicalId3||""==physicalId3){
			jumbo.showMsg("请选择物理仓");
			return false;
		}
		if(errors.length>0){
			jumbo.showMsg(errors.join("<br/>"));
			return false;
		}else{
			$j("#importForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/auto/importCollection.do?id="+$j("#physicalId3").val()));
			loxia.submitForm("importForm");
		}
	});
	
	
	
	/**
	 * (批量)删除
	 */
	$j("#batchRemove").click(function(){
		var arrIds = [];
		var ids = $j("#tbl-shipping-point").jqGrid('getGridParam','selarrrow');
		if (null != ids && ids.length > 0) {
			for ( var i in ids) {
				var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/findshippingpointStatus.do", {"id" : ids[i]});
				if (null != result && 1!=result["status"]) {
					jumbo.showMsg("需要删除的数据有正在使用中，无法删除");
					return false;
				}
				arrIds.push(ids[i]);
			}
			if (null != arrIds && arrIds.length > 0) {
				var arrStr= arrIds.join(",");
			}
			var res = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/deleteShippingPointCollection.do", {"arrIds" : arrStr});
			if (null != res && res.result == 'success') {
				var url = window.$j("body").attr("contextpath")+"/json/shippingPointQuery.do";
				$j("#tbl-shipping-point").jqGrid('setGridParam', {url:url,page:1,postData:loxia._ajaxFormToObj("form_query")}).trigger("reloadGrid");
				jumbo.showMsg("删除成功!");
			}else {
				jumbo.showMsg(res.msg);
			}
		}else {
			jumbo.showMsg("请勾选需要删除的数据");
		}
	});
	
	
});

function editLpCodeWeight(obj){
	   var ids = $j(obj).parent().siblings().eq(2).text();
	   if (null != ids && ids.length > 0) {
		    $j("#physicalId2").removeClass("hidden");
		    $j("#physicalId1").addClass("hidden");
			$j("#dialog_addWeight").dialog("open");
			$j("#t_wh_goods_collection_id").val(ids);
			var code=$j(obj).parent().siblings().eq(3).text();
			$j("#collectionCode").val(code);
			$j("#collectionCode").attr("readonly",true);
			var sort=$j(obj).parent().siblings().eq(6).text();
		    $j("#sort").val(sort);
			var popUpCode=$j(obj).parent().siblings().eq(7).text();
			$j("#popUpCode").val(popUpCode.replace(/(^\s*)|(\s*$)/g, ""));
			$j("#physicalId2").val($j(obj).parent().siblings().eq(8).text());
			$j("#passWay_1").val($j(obj).parent().siblings().eq(4).text());
       }
	   
};  

