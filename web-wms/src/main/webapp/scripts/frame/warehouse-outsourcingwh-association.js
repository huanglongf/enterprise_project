$j.extend(loxia.regional['zh-CN'],{
	BRAND:"品牌",
	OUTSOURCINGCODE:"外包仓编码",
	WAREHOUSE:"外仓库来源",
	CHANNELNAME:"店铺",
	OPERATOR:"操作",
	DELETE:"删除"
});
function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
	
$j(document).ready(function(){
	//显示隐藏
	$j("#create").click(function(){
		$j("#showAdd").removeClass("hidden");
	});
	//查询
	$j("#search").click(function(){
		 var baseUrl = $j("body").attr("contextpath");
		 var postData = loxia._ajaxFormToObj("form_query");  
			$j("#new-sec-kill-sku").jqGrid('setGridParam',{
				url:$j("body").attr("contextpath")+"/findSkuWarehouseRefListAll.json",postData:postData}).trigger("reloadGrid");
		});
	//重置
	$j("#reset").click(function(){
		$j("#form_query input,#form_query select").val("");

	});
		$j("#new-sec-kill-sku").jqGrid({
			url:$j("body").attr("contextpath") + "/findSkuWarehouseRefListAll.json",
			datatype: "json",
			colNames: [i18("BRAND"),i18("WAREHOUSE"),i18("OUTSOURCINGCODE"),"品牌Id",i18("CHANNELNAME"),"店铺ID",i18("OPERATOR")],
			colModel: [
								{name: "brandName", index: "brandName",width: 100, resizable: true},
								{name:"source",index:"source",width: 120, resizable: true},
								{name:"sourceWh",index:"sourceWh", width:140,resizable:true},
								{name:"brandId",index:"brandId", hidden:true},
								{name:"channelName",index:"channelName", width:120,resizable:true},
								{name:"channelId",index:"channelId", hidden:true},
								{name: "operator", width: 60, resizable: true,formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("DELETE"), onclick:"cancelDetial(this,event);"}}}
								],
			caption: "外包仓品牌关联列表",
		   	sortname: 'sourcewh',
		   	multiselect: false,
			sortorder: "desc",
			pager:"#pager",
			width:595,
		   	shrinkToFit:false,
			height:"auto",
			rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
			viewrecords: true,
		   	rownumbers:true,
			jsonReader: {repeatitems : false, id: "0"}
		});
	
		//下拉框数据加载品牌
		var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findByBrandName.do");
		for(var idx in result){
			$j("<option value='" + result[idx].brandId + "'>"+ result[idx].brandName +"</option>").appendTo($j("#brandName"));
		}
		//下拉框数据加载店铺
		var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findByChannelName.do");
		for(var idx in result){
			$j("<option value='" + result[idx].channelId + "'>"+ result[idx].channelName +"</option>").appendTo($j("#channelId"));
		}
		//加载所有品牌
		var rsBrand = loxia.syncXhr($j("body").attr("contextpath") + "/findBrandNameAll.json",{});
		$j("#brandNameAll").append("<option></option>");
		for(var i = 0 ; i < rsBrand.brandlist.length ; i++){
			$j("#brandNameAll").append("<option value='" + rsBrand.brandlist[i].brandId + "'>"+rsBrand.brandlist[i].name+"</option>");
		}
	    $j("#brandNameAll").flexselect();
	    $j("#brandNameAll").val("");
	    
	    //加载所有店铺
		var rsChannel = loxia.syncXhr($j("body").attr("contextpath") + "/findChannelNameAll.json",{});
		$j("#channelId1").append("<option></option>");
		for(var i = 0 ; i < rsChannel.channellist.length ; i++){
			$j("#channelId1").append("<option value='" + rsChannel.channellist[i].id + "'>"+rsChannel.channellist[i].name+"</option>");
		}
	    $j("#channelId1").flexselect();
	    $j("#channelId1").val("");
		//插入外包仓关联数据
		$j("#confirm").click(function(){
			var brandName=$j("#brandNameAll").val();
			if(brandName==null || brandName==""){
				jumbo.showMsg("请选择品牌");
				return false;
			}
			var warehouseName=$j("#sourcewh1").val();
			if(warehouseName==null || warehouseName==""){
				jumbo.showMsg("请输入外包仓编码");
				return false;
			}
			var source=$j("#source1").val();
			if(source==null || source==""){
				jumbo.showMsg("请输入外包仓来源");
				return false;
			}
			var channelId1=$j("#channelId1").val();
			if(channelId1==null || channelId1==""){
				jumbo.showMsg("请选择店铺");
				return false;
			}
			
			loxia.lockPage();
			//新增
//			var cmd = loxia.syncXhrPost($j("body").attr("contextpath")+ "/findSkuWarehouseRefList.json", {"sourcewh" :warehouseName });
//			if (cmd && cmd["courcewh"]) {
//				loxia.unlockPage();
//				jumbo.showMsg("外包仓编码已经存在！");//创建失败！
//			}else{
				var addForm=loxia._ajaxFormToObj("add_form");
				loxia.asyncXhrPost($j("body").attr("contextpath") + "/insertSkuWarehouseRef.json",addForm,{
				success:function (data) {
					loxia.unlockPage();
					jumbo.showMsg("新增外包仓商品关联成功");//创建成功
					$j("#new-sec-kill-sku").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/findSkuWarehouseRefListAll.json")}).trigger("reloadGrid");
					$j("#add_form input,#add_form select").val("");
				},
				 error:function(data){
					 	loxia.unlockPage();
					  jumbo.showMsg("创建失败！");//创建失败！
				 }
				});
//			}

		});
});
//删除
function cancelDetial(tag,event){
	var brandId = $j(tag).parents("tr").children("td:eq(4)").attr("title");
	var source = $j(tag).parents("tr").children("td:eq(2)").attr("title");
	var sourcewh = $j(tag).parents("tr").children("td:eq(3)").attr("title");
	var channelId = $j(tag).parents("tr").children("td:eq(6)").attr("title");
	var postData = {};
	postData["brandId"]=brandId;
	postData["source"]=source;
	postData["sourcewh"]=sourcewh;
	postData["channelId"]=channelId;
	if(confirm("确定要删除?")){
	    //删除关联数据
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/deleteSkuWarehouseRef.json",postData);
		if(result&&result.rs=="success"){
			//更新数据
			$j("#new-sec-kill-sku").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/findSkuWarehouseRefListAll.json")}).trigger("reloadGrid");
		}else{
			jumbo.showMsg("删除外包仓关联数据失败！");
		}
	}
}
