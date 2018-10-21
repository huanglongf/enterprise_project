function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}
function confirmModification(obj){
	var rowId = $j(obj).parent().siblings().eq(1).text();
	
	//var rowId=$j("#tbl-timedtaskList").jqGrid("getGridParam","selrow");
	//var rowData = $j("#tbl-timedtaskList").jqGrid('getRowData',rowId);
	var re = loxia.syncXhr($j("body").attr("contextpath") + "/deleteSkuType2.json",{"id":rowId});
	if(null!=re){
		if(re["msg"]==true){
			jumbo.showMsg("删除成功");
			$j("#tbl-timedtaskList").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/findSkubySkuCode.json"),
				postData:loxia._ajaxFormToObj("searchForm")}).trigger("reloadGrid");
			return ;
		}else{
			jumbo.showMsg("删除失败");
			return;
		}
	}else{
		jumbo.showMsg("删除失败");
	}
}
$j(document).ready(function (){
	$j("#tbl-timedtaskList").jqGrid({
		datatype:"json",
		colNames:["ID","SKU编码","订单类型","操作"],	
		colModel:[
		          {name:"id",index:"id",hidden:true},
		          {name:"code",index:"code",width: 150,resizable: true},
		          {name:"skuTypeName",index:"skuTypeName",width: 150,resizable: true},
				  {name:"idForBtn",index:"idForBtn", width: 250,resizable:true,sortable: false, formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("删除"), onclick:"confirmModification(this)"}}}],
/*		          {name: "operator", width: 60, resizable: true,formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("删除"), onclick:"confirmModification(this);"}}}],
*/		caption: i18("特殊订单SKU维护"),
		multiselect: false,
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		width:"auto",
		height:"auto",
		pager:"#pager_query",	   
		sortorder: "id",
		viewrecords: true,
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#search").click(function(){
		$j("#tbl-timedtaskList").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/findSkubySkuCode.json"),
			postData:loxia._ajaxFormToObj("searchForm")}).trigger("reloadGrid");
	})
	
	$j("#reset").click(function(){
		$j("#code").val("");
		$j("#msr").val("");
	});
	
	$j("#update").click(function(){
		var postData={};
		var code=$j("#code1").val();
		if(code==null||code==""){
			jumbo.showMsg("请输入sku编码");
			return;
		}
		
	
		var re = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/updateSkuCode.json",{"code":code});
		if(null!=re){
			if(re["msg"]=="sccuess"){
				jumbo.showMsg("绑定成功");
				$j("#tbl-timedtaskList").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/findSkubySkuCode.json"),
					postData:loxia._ajaxFormToObj("searchForm")}).trigger("reloadGrid");
				return ;
			}else{
				jumbo.showMsg("绑定失败");
				return ;
			}
		}else{
			jumbo.showMsg("绑定失败");
			return ;
		}		
	})

});