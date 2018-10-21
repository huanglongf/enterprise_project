var $j = jQuery.noConflict();

$j.extend(loxia.regional['zh-CN'],{
	CODE : "集货口编码",
	NAME : "集货口名称",
	CREATE_TIME : "创建时间",
	ROLE_LIST : "自定义集货库位配置"
});
var targetRatio=[];
function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

function showMsg(msg){
	jumbo.showMsg(msg);
}
function clearTargetRatio(){
	$j(".new").remove();
}

function showdetail(obj){
	targetRatio=[];
	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");
	var pl=$j("#tbl-transfer-owner-target").jqGrid("getRowData",id);
	$j("#skuCodeD").text(pl["skuCode"]);
	$j("#skuBarCodeD").text(pl["skuBarCode"]);
	$j("#transferOwnerTargetIdD").text(id);
	$j("#skuNameD").text(pl["skuName"]);
	$j("#sourceOwnerD").text(pl["sourceOwner"]);
	$j("#sourceRatioD").text(pl["sourceRatio"]);
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findTransferOwnerTargetByUpdate.do?sourceOwner="+pl["sourceOwner"]+"&skuCode="+pl["skuCode"]);
	for(var idx in result){
		if(idx!=0){
			$j("<tr class='new'><td></td><td><span>"+result[idx].targetOwnerName+"</span></td><td></td><td><input id='targetRatio"+idx+"' type='text' value='"+result[idx].targetRatio+"' /></td></tr>").appendTo($j("#tb_targetRatioUpdate"));
		}else{
			$j("#targetOwner0").text(result[idx].targetOwnerName);
			$j("#targetRatio0").val(result[idx].targetRatio);
		}
		var trObj={"ownerCode":result[idx].targetOwner,"targetRatioId":"targetRatio"+idx};
		targetRatio[idx]=trObj;
	}
	$j("#dialog_addWeight").dialog("open");
}
$j(document).ready(function (){
	$j("#update_div").hide();
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findAllSourceOwner.do");
	$j("<option value=''>"+"-请选择-"+"</option>").appendTo($j("#sourceOwner"));
	for(var idx in result){
			$j("<option value='" + result[idx].sourceOwner + "'>"+ result[idx].sourceOwnerName +"</option>").appendTo($j("#sourceOwner"));
	}
	$j("#physicalId2").addClass("hidden");
	/*var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/findPhysicalWarehouse.do");
	$j("<option value=''>"+"-请选择-"+"</option>").appendTo($j("#physicalId1"));
	$j("<option value=''>"+"-请选择-"+"</option>").appendTo($j("#physicalId3"));
	for(var idx in result){
		$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#physicalId"));
		$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#physicalId1"));
		$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#physicalId3"));
	}*/
	
	$j("#dialog_addWeight").dialog({title: "编辑", modal:true, autoOpen: false, width: 500, height: 360});
	$j("#tbl-priority-owner").jqGrid({
		url:window.$j("body").attr("contextpath")+"/json/findTransferOwnerSource.do",
		datatype: "json",
		colNames: ["ID","来源店铺","来源店铺名称","目标店铺","目标店铺名称","优先店铺","优先店铺名称","修改","删除"],
		colModel: [{name: "id", index: "id", hidden: true},		         
		           {name: "ownerSource", index: "ownerSource", width: 120, hidden: true},
		           {name: "ownerSourceName", index: "ownerSourceName", width: 120},
		           {name: "targetOwner", index: "targetOwner", width: 100, resizable: true, hidden: true},
		           {name: "targetOwnerName", index: "targetOwnerName", width: 100, resizable: true},
		           {name: "priorityOwner", index: "priorityOwner", width: 120, resizable: true, hidden: true},
		           {name: "priorityOwnerName", index: "priorityOwnerName", width: 120, resizable: true},
				   {name:"update",index:"update", width: 100,resizable:true,sortable: false, formatter:"buttonFmatter", formatoptions:{"buttons":{label:"修改", onclick:"updatePriorityOwner(this)"}}},
		           {name:"delete",index:"delete", width: 100,resizable:true,sortable: false, formatter:"buttonFmatter", formatoptions:{"buttons":{label:"删除", onclick:"deletePriorityOwner(this)"}}}
		           ],
		caption: "优先店铺配置",
	   	sortname: 'id',
	   	pager:"#pager1",
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
	
	$j("#tbl-transfer-owner-target").jqGrid({
		url:window.$j("body").attr("contextpath")+"/json/findTransferOwnerTarget.do",
		datatype: "json",
		colNames: ["ID","SKU编码","SKU条码","商品名称","来源店铺","来源店铺名称","来源店铺占比(%)","目标店铺","目标店铺名称","目标店铺占比(%)"],
		colModel: [{name: "id", index: "id", hidden: true},		         
		           {name: "skuCode", index: "skuCode",formatter:"linkFmatter", formatoptions:{onclick:"showdetail"}, width: 110, resizable: true,sortable:false},
		           {name: "skuBarCode", index: "skuBarCode", width: 100, resizable: true},
		           {name: "skuName", index: "skuName", width: 120, resizable: true},
		           {name: "sourceOwner", index: "sourceOwner", width: 120, resizable: true, hidden: true},
		           {name: "sourceOwnerName", index: "sourceOwnerName", width: 120, resizable: true},
		           {name: "sourceRatio", index: "sourceRatio", width: 120, resizable: true},
		           {name: "targetOwner", index: "targetOwner", width: 120, resizable: true, hidden: true},
		           {name: "targetOwnerName", index: "targetOwnerName", width: 120, resizable: true},
		           {name: "targetRatio", index: "targetRatio", width: 120, resizable: true}
		           ],
		caption: "商品库存分配比例",
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
		var skuCode=$j("#skuCodeQ").val();
		var sourceOwner=$j("#sourceOwnerQ").val();
		var targetOwner=$j("#targetOwnerQ").val();
		$j("#tbl-transfer-owner-target").jqGrid('setGridParam',{
			url:$j("body").attr("contextpath")+"/findTransferOwnerTarget.json",page:1,postData:{"skuCode":skuCode,"sourceOwnerName":sourceOwner,"targetOwnerName":targetOwner}}).trigger("reloadGrid");
	});
	
	
	//重置
	$j("#reset").click(function(){
		$j("#form_query input").val("");
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
	
	
	$j("#save").click(function(){
		var sourceOwner=$j("#sourceOwner").val();
		
		var targetOwner=$j("#targetOwner").val();
		
		var priorityOwner=$j("#priorityOwner").val();
		
	
		
		if(null==sourceOwner||""==sourceOwner){
			jumbo.showMsg("来源店铺不可为空!");
			return false;
		}
		if(null==targetOwner||""==targetOwner){
			jumbo.showMsg("目标店铺不可为空!");
			return false;
		}
		if(null==priorityOwner||""==priorityOwner){
			jumbo.showMsg("优先店铺不可为空!");
			return false;
		}
		var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/saveTransferOwnerSource.do", {"sourceOwner" : sourceOwner,"targetOwner":targetOwner,"priorityOwner":priorityOwner});
		if("success"==result["flag"]){
			jumbo.showMsg("保存成功");
			$j("#update_div").hide();
			$j("#tbl-priority-owner").jqGrid('setGridParam',{
				page:1}).trigger("reloadGrid");
		}else if("error"==result["flag"]){
			jumbo.showMsg("系统错误!");
			return false;
		}else{
			jumbo.showMsg(result["flag"]);
			return false;
		}
		
		
	});
	
	$j("#update").click(function(){
		var sourceOwner=$j("#sourceOwnerU").val();
		
		var targetOwner=$j("#targetOwnerU").val();
		
		var priorityOwner=$j("#priorityOwnerU").val();
		
		var priorityOwnerId=$j("#priorityOwnerId").val();
		
		
		if(null==sourceOwner||""==sourceOwner){
			jumbo.showMsg("来源店铺不可为空!");
			return false;
		}
		if(null==targetOwner||""==targetOwner){
			jumbo.showMsg("目标店铺不可为空!");
			return false;
		}
		if(null==priorityOwner||""==priorityOwner){
			jumbo.showMsg("优先店铺不可为空!");
			return false;
		}
		var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/saveTransferOwnerSource.do", {"sourceOwner" : sourceOwner,"targetOwner":targetOwner,"priorityOwner":priorityOwner,"id":priorityOwnerId});
		if("success"==result["flag"]){
			jumbo.showMsg("保存成功");
			$j("#update_div").hide();
			$j("#tbl-priority-owner").jqGrid('setGridParam',{
				page:1}).trigger("reloadGrid");
		}else if("error"==result["flag"]){
			jumbo.showMsg("系统错误!");
			return false;
		}else{
			jumbo.showMsg(result["flag"]);
			return false;
		}
		
		
	});
	
	$j("#closediv").click(function(){
		$j("#dialog_addWeight").dialog("close");
		clearTargetRatio();
	});
	
	$j("#saveTargetRatio").click(function(){
		var ratioVal=0;
		var re = /^[0-9]*[1-9][0-9]*$/ ;
		var targetOwnerRatio_update=[];
		for(var idx in targetRatio){
			var owner=targetRatio[idx].ownerCode;
			var ratio=$j("#"+targetRatio[idx].targetRatioId).val();
			if(ratio==null||""==ratio){
				ratio=0;
			}
			ratio=parseInt(ratio);
			if (!re.test(ratio)) {
				jumbo.showMsg("目标店铺的比例必须为正整数！");
				return;
			}
			targetOwnerRatio_update[idx]={"targetOwner":owner,"targetRatio":ratio};
			ratioVal=ratioVal+ratio;
		}
		if(ratioVal>100||ratioVal<0){
			jumbo.showMsg("目标店铺的比例总和不得超出100或低于0！");
			return false;
		}
		var sourceOwner=$j("#sourceOwnerD").text();
		var skuCode=$j("#skuCodeD").text();
		
		var flag=loxia.syncXhr($j("body").attr("contextpath") + "/json/updateTransferOwnerTarget.do",{"targetRatioStr":JSON.stringify(targetOwnerRatio_update),"skuCode":skuCode,"sourceOwner":sourceOwner});
		if(flag!="" && flag["flag"]=="success"){
			jumbo.showMsg("修改成功！");
			$j("#closediv").click();
			$j("#tbl-transfer-owner-target").jqGrid('setGridParam',{
				page:1}).trigger("reloadGrid");
		}else{
			jumbo.showMsg("修改失败！");
		}
	});

   
	/**导入	 */
	$j("#import").click(function(){
		var file=$j.trim($j("#file").val()),errors=[];
		if(file==""||file.indexOf("xls")==-1){
			errors.push("请选择文件");
		}
		if(errors.length>0){
			jumbo.showMsg(errors.join("<br/>"));
			return false;
		}else{
			$j("#importForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importSkuTargetOwner.do"));
			loxia.submitForm("importForm");
		}
		$j("#tbl-transfer-owner-target").jqGrid('setGridParam',{
			page:1}).trigger("reloadGrid");
	});
	
	
	
	
	
	$j("#sourceOwner").change(function(){
		$j("#targetOwner").empty();
		var sourceOwner=$j("#sourceOwner").val();
		if(sourceOwner!=null&&sourceOwner!=""){
			var r1 = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findAllTargetOwner.do?sourceOwner="+sourceOwner);
			$j("<option value=''>"+"-请选择-"+"</option>").appendTo($j("#targetOwner"));
			for(var idx in r1){
					$j("<option value='" + r1[idx].targetOwner + "'>"+ r1[idx].targetOwnerName +"</option>").appendTo($j("#targetOwner"));
			}
		}
	});
	
	$j("#targetOwner").change(function(){
		$j("#priorityOwner").empty();
		var sourceOwner=$j("#sourceOwner").val();
		var sourceOwnerName=$j("#sourceOwner").find("option:selected").text();
		var targetOwner=$j("#targetOwner").val();
		var targetOwnerName=$j("#targetOwner").find("option:selected").text();
		if(sourceOwner!=null&&sourceOwner!=""&&sourceOwner!=null&&sourceOwner!=""){
			$j("<option value=''>"+"-请选择-"+"</option>").appendTo($j("#priorityOwner"));
			$j("<option value='"+sourceOwner+"'>"+sourceOwnerName+"</option>").appendTo($j("#priorityOwner"));
			$j("<option value='"+targetOwner+"'>"+targetOwnerName+"</option>").appendTo($j("#priorityOwner"));
		}
	});
	
	$j("#delete").click(function(){
		var ids = $j("#tbl-transfer-owner-target").jqGrid('getGridParam','selarrrow');
		
		var flag=loxia.syncXhr($j("body").attr("contextpath") + "/json/deleteTransferOwnerTarget.do",{"idStr":ids.toString()});
		if(flag!="" && flag["flag"]=="success"){
			jumbo.showMsg("删除成功！");
		}else{
			jumbo.showMsg("删除失败！");
		}
		$j("#tbl-transfer-owner-target").jqGrid('setGridParam',{
			page:1}).trigger("reloadGrid");
	});
	
	
	$j("#cancel").click(function(){
		$j("#update_div").hide();
		$j("#priorityOwnerU").empty();
	});
	
});

function updatePriorityOwner(obj){
	$j("#priorityOwnerU").empty();
	   var ids = $j(obj).parent().siblings().eq(2).text();
	   if (null != ids && ids.length > 0) {
		   var rowData = $j("#tbl-priority-owner").jqGrid('getRowData',ids);
		   var sourceOwner=rowData.ownerSource;
		   var sourceOwnerName=rowData.ownerSourceName;
		   var targetOwner=rowData.targetOwner;
		   var targetOwnerName=rowData.targetOwnerName;
		   
		   var priorityOwner=rowData.priorityOwner;
		   $j("#sourceOwnerU").val(sourceOwner);
		   $j("#sourceOwnerU1").val(sourceOwnerName);
		   $j("#targetOwnerU").val(targetOwner);
		   $j("#targetOwnerU1").val(targetOwnerName);
		   $j("#priorityOwnerId").val(ids);
		   
		   //$j("<option value=''>"+"-请选择-"+"</option>").appendTo($j("#priorityOwnerU"));
			$j("<option value='"+sourceOwner+"'>"+sourceOwnerName+"</option>").appendTo($j("#priorityOwnerU"));
			$j("<option value='"+targetOwner+"'>"+targetOwnerName+"</option>").appendTo($j("#priorityOwnerU"));
			$j("#priorityOwnerU").find("option[ value='"+priorityOwner+"']").attr("selected",true);
			
			$j("#update_div").show();
       }
	   
};  

function deletePriorityOwner(obj){
	   var ids = $j(obj).parent().siblings().eq(2).text();
	   if (null != ids && ids.length > 0) {
		   var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/deleteTransferOwnerSource.do", {"id":ids});
		   if("success"==result["flag"]){
				jumbo.showMsg("删除成功！");
				$j("#tbl-priority-owner").jqGrid('setGridParam',{
					page:1}).trigger("reloadGrid");
			}else{
				jumbo.showMsg("删除失败！");
				return false;
			}
    }
	   
};  

