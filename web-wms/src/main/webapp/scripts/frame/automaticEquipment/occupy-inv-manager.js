$j(document).ready(function(){
	var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/getZoonList.do");
	$j("#dialog").dialog({title: "编辑", modal:true, autoOpen: false, width: 360, height: 360});
	for(var idx in result){
		$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#zoonCode1"));
	}
	$j("#tbl-zoonOcpInvList").jqGrid({
		datatype: "json",
		colNames: ["ID","配货模式","区域id","仓库区域","优先级","操作"],
		colModel: [
				{name:"id", index:"id", hidden: true},
				{name:"saleModle", index:"saleModle", width:100, resizable:true},
				{name:"zoonId",index:"zoonId",width:100,resizable:true,hidden:true},
				{name:"zoonCode",index:"zoonCode",width:100,resizable:true},
				{name:"sort", index:"sort", width: 100, resizable: true},
				{name:"idForBtn", width:80,resizable:true,formatter:"buttonFmatter", formatoptions:{"buttons":{label:"修改", onclick:"editZoonCode(this);"}}}],
		caption:"订单占用区域列表",
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#pager",	   
		sortorder: "sort",
		viewrecords: true,
   		rownumbers:true,
   		multiselect: true,
		jsonReader: { repeatitems : false, id:"0"},
	});
	
	$j("#search").click(function(){
		var url = $j("body").attr("contextpath") + "/findzoonOcpSortList.json";
		$j("#tbl-zoonOcpInvList").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
	});




	//新增
	$j("#add").click(function(){
		$j("#dialog").dialog("open");
		$j("#zoon_ocpsort_id").val("");
		$j("#zoonCode1").val("");
		
		$j("#sort").val("");
	});
	
	//关闭
	$j("#closediv").click(function(){
		$j("#dialog").dialog("close");
		$j("#zoon_ocpsort_id").val("");
	});
	
	//重置
	$j("#reset").click(function(){
		$j("#zoonCode").val("");
	});

	//保存
	$j("#save").click(function(){
		var ids = $j("#zoon_ocpsort_id").val();
		var zoonId=$j("#zoonCode1").val();
		var saleModel=$j("#saleModle1").val();
		var sort=$j("#sort").val();
		var re = /^[0-9]+$/ ;
		if(!re.test(sort)){
		jumbo.showMsg("优先级为大于0的正整数");  
			return;
		}
		if(sort==0){
			jumbo.showMsg("优先级不能为0"); 
			return;
		}
		var postData={};
		postData["zoonOcpSort.zoonId"]=zoonId;
		postData["zoonOcpSort.saleModle"]=saleModel;
		postData["zoonOcpSort.sort"]=sort;
		postData["zoonOcpSort.id"]=ids;
		var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/saveOrUpdateZoonOcpSort.do", postData);
		if(result.msg=="success"){
			jumbo.showMsg("保存成功");
			$j("#dialog").dialog("close");
			var url = $j("body").attr("contextpath") + "/findzoonOcpSortList.json";
			$j("#tbl-zoonOcpInvList").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
		}else{
			jumbo.showMsg(result.msg);
		}
	});
	
	//删除
	$j("#batchRemove").click(function(){
		var ids = $j("#tbl-zoonOcpInvList").jqGrid('getGridParam','selarrrow');
		if (null != ids && ids.length > 0) {
			for ( var i in ids) {
				var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/deleteZoonOcpSortById.do", {"id" : ids[i]});
				if (result.msg=="success") {
					jumbo.showMsg("删除成功");
					var url = $j("body").attr("contextpath") + "/findzoonOcpSortList.json";
					$j("#tbl-zoonOcpInvList").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
					}else{
						jumbo.showMsg("删除失败");
					}
				}
			}
		});
});

//修改
function editZoonCode(obj){
	   var ids = $j(obj).parent().siblings().eq(2).text();
	   $j("#form_update select").val(0);
	   if (null != ids && ids.length > 0) {
			$j("#zoon_ocpsort_id").val(ids);
			var saleModel=	$j(obj).parent().siblings().eq(3).text();
			if(saleModel=="单件"){
				$j("#saleModle1").val(1);
			}else if (saleModel=="多件"){
				$j("#saleModle1").val(10);
			}else if (saleModel=="团购"){
				$j("#saleModle1").val(20);
			}else if (saleModel=="秒杀"){
				$j("#saleModle1").val(30);
			}else if (saleModel=="套装组合"){
				$j("#saleModle1").val(2);
			}else if (saleModel=="O2OQS"){
				$j("#saleModle1").val(3);
			}else if (saleModel=="预售"){
				$j("#saleModle1").val("预售");
			}
			$j("#zoonCode1").val($j(obj).parent().siblings().eq(4).text());
			$j("#sort").val( $j(obj).parent().siblings().eq(6).text());
			$j("#dialog").dialog("open");
    }
}; 