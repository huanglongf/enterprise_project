$j.extend(loxia.regional['zh-CN'],{
	APPLY_FORM_SELECT : "请选择作业申请单",
	SHOP_STORE_LIST:"现有门店信息列表",
	SKU_LIST:"商品列表",
	OPERATOR:"操作",
	DELETE:"删除"
});
function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
$j(document).ready(function(){
	$j("#tbl_store_list").jqGrid({
		url:$j("body").attr("contextpath") + "/getallshopstore.json",//getallprepackagedsku.json",
		datatype: "json",
		colNames: ["ID","店铺编码","店铺名称","联系人","联系电话","联系地址","创建时间","最后修改时间","国家","省","市","区",i18("OPERATOR")],
		colModel: [
				   {name: "id", index: "id", hidden: true},
		           {name: "code", index: "code", width:100,reziable:true,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}},
		           {name: "name", index: "name", width:200,resizable:true},         
		           {name: "receiver", index: "receiver", width: 100, resizable: true},
		           {name: "telephone", index: "telephone", width: 100, resizable: true},
		           {name: "address", index: "address", width: 100, resizable: true},
		           {name: "createTime", index: "createTime", width: 100, resizable: true},
		           {name: "lastModifyTime", index: "lastModifyTime", width: 100, resizable: true},
		           {name: "country", index: "country", hidden: true},
		           {name: "province", index: "province", hidden: true},
		           {name: "city", index: "city", hidden: true},
		           {name: "district", index: "district", hidden: true},
		           {name: "operator", width: 100, resizable: true,formatter:"buttonFmatter",align:"center",formatoptions:{"buttons":{label:i18("DELETE"), onclick:"deleteShopStore(this,event);"}}}
		           ],
		caption: i18("SHOP_STORE_LIST"),// 预包装商品列表
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: false,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc",
		hidegrid:false,
		jsonReader: { repeatitems : false, id: "0" }
	});
	$j("#reset").click(function(){
		$j("#queryForm input").val("");
	});
	$j("#edit").click(function(){
		var postData = loxia._ajaxFormToObj("storeDetail");
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/editshopstorebycode.json",postData);
		if(rs!=null&&rs.result=="success"){
			jumbo.showMsg("修改门店信息成功!");
		}else{
			jumbo.showMsg("修改门店信息遇到错误!");
		}
	});
	$j("#save").click(function(){
		if($j("#storecode").val()==""){
			loxia.tip($j("#storecode"),"门店编码必填!");
			return;
		}
		var postData = loxia._ajaxFormToObj("storeDetail");
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/addnewshopstore.json",postData);
		if(rs!=null&&rs.result=="success"){
			jumbo.showMsg("添加门店信息成功!");
		}else{
			var s = rs.msg;
			if(s!=null&&s=="01"){
				jumbo.showMsg("门店编码已存在，不允许重复添加!");
				return;
			}
			jumbo.showMsg("添加门店信息遇到错误!");
		}
	});
	$j("#back").click(function(){
		$j("#storeDetail input").val("");
		$j("#detail").addClass("hidden");
		$j("#list").removeClass("hidden");
		reshow();
	});
	$j("#add").click(function(){
		$j("#list").addClass("hidden");
		$j("#textCode").css("color","#f00");
		$j("#storecode").attr("readonly",false);
		$j("#save").removeClass("hidden");
		$j("#edit").addClass("hidden");
		$j("#detail").removeClass("hidden");
	});
	$j("#btn-query").click(function(){
		reshow();
	});
});

//删除现有预包装商品列表
function deleteShopStore(tag,event){
	var id = $j(tag).parents("tr").attr("id");
	var postData = {};
	postData["ssi.id"]=id;
	if(confirm("确定要删除?")){
		//deletePrepackagedSkuByMainSkuId.json
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/deleteshopstorebyid.json",postData);
		if(rs!=null&&rs.result=="success"){
			reshow();
		}else{
			jumbo.showMsg("删除门店信息遇到错误！");
		}
	}
}
function reshow(){
	var postData = loxia._ajaxFormToObj("queryForm");
	$j("#tbl_store_list").jqGrid('setGridParam',{
		url:loxia.getTimeUrl($j("body").attr("contextpath")+"/getallshopstore.json"),
		postData:postData,
	}).trigger("reloadGrid");
}
//显示门店信息明细
function showDetail(tag){
	var code = $j(tag).parent().attr("title");
	var name = $j(tag).parent().siblings().eq(2).attr("title");
	var receiver = $j(tag).parent().siblings().eq(3).attr("title");
	var telephone = $j(tag).parent().siblings().eq(4).attr("title");
	var address = $j(tag).parent().siblings().eq(5).attr("title");
	var country = $j(tag).parent().siblings().eq(8).attr("title");
	var province = $j(tag).parent().siblings().eq(9).attr("title");
	var city = $j(tag).parent().siblings().eq(10).attr("title");
	var district = $j(tag).parent().siblings().eq(11).attr("title");
	$j("#textCode").css("color","#888");
	$j("#storecode").val(code).css("color","#888").attr("readonly",true);
	$j("#name").val(name);
	$j("#receiver").val(receiver);
	$j("#telephone").val(telephone);
	$j("#address").val(address);
	$j("#country").val(country);
	$j("#province").val(province);
	$j("#city").val(city);
	$j("#district").val(district);
	$j("#edit").removeClass("hidden");
	$j("#save").addClass("hidden");
	$j("#list").addClass("hidden");
	$j("#detail").removeClass("hidden");
}