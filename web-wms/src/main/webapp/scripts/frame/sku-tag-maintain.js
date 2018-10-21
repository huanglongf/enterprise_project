$j.extend(loxia.regional['zh-CN'], {
});

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}
var tagId;
$j(document).ready(function (){
	//var tagType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"skuTagType"});
	var tagStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"skuTagStatus"});
	//$j("#dialog_addTag").dialog({title: "创建商品标签", modal:true, autoOpen: false, width: 650});
	$j("#dialog_addTag").hide();
	$j("#skuTag_tabs").tabs();
	$j("#skuTag_tabs").hide();
	initBrand();
	initCustomer();
	$j("#tbl-tag-list").jqGrid({
		url:$j("body").attr("contextpath")+"/findSkuTagByPagination.json",
		datatype: "json",
		colNames: ["ID","编码","名称","状态","类型","创建时间","操作"],
		colModel: [
		           {name: "id", index: "id", hidden: true},		         
		           {name:"code",index:"code", width:95,resizable:true,formatter:"linkFmatter", formatoptions:{onclick:"showDetail(this);"},sortable:false},
		           {name:"name",index:"name", width:95,resizable:true},
		           {name:"tagStatus",index:"status", width:90,resizable:true,formatter:"select",editoptions:tagStatus},
		           {name:"tagType",index:"tagType", width:90,resizable:true},
		           {name:"createTime",index:"tag.create_time", width:150,resizable:true},
		           {name:"operator", width: 60, resizable: true,formatter:"buttonFmatter", formatoptions:{"buttons":{label:"编辑", onclick:"editDetial(this,event);"}}}
		           ],
		caption: "商品标签列表",
		sortname: 'tag.create_time',
	   	sortorder: "desc",
	   	multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pager",
	   	height:jumbo.getHeight(),
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#search").click(function(){
		var postData = loxia._ajaxFormToObj("tagForm");  
		$j("#tbl-tag-list").jqGrid('setGridParam',{
			url:$j("body").attr("contextpath")+"/findSkuTagByPagination.json",postData:postData}).trigger("reloadGrid"); 
	});
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val("");
	});
	$j("#addTagBtn").click(function(){
		//$j("#dialog_addTag").dialog("open");
		$j("#dialog_tagList").hide();
		$j("#dialog_addTag").show();
	});
	$j("#addTagBack").click(function(){
		$j("#dialog_addTag input").val("");
		$j("#dialog_addTag select").val("");
		$j("#dialog_addTag").hide();
		$j("#dialog_tagList").show();
	});
	$j("#addSkuTagBtn").click(function(){
		var tagCode = $j.trim($j("#code").val());
		var tagName = $j.trim($j("#name").val());
		var tagType = $j.trim($j("#type").val());
		if(null == tagCode || '' == tagCode){
			jumbo.showMsg("商品标签编码不能为空！");
			return false;
		}
		if(null == tagName || '' == tagName){
			jumbo.showMsg("商品标签名称不能为空！");
			return false;
		}
		if(null == tagType || '' == tagType){
			jumbo.showMsg("请选择商品标签类型！");
			return false;
		}
		var rs = loxia.syncXhrPost($j("body").attr("contextpath")+"/findSkuTagExistByCode.json?tag.code="+tagCode);
		if(rs && rs.result == true){
			jumbo.showMsg("商品标签编码已存在！");
			return false;
		}
		var tagId = -1;
		var tagStatus = 1;
		var postDate = {};
		postDate['tag.id'] = tagId;//id
		postDate["status"] = tagStatus;//状态,默认正常
		postDate["type"] = tagType;//类型
		postDate["tag.code"] = tagCode;//编码
		postDate["tag.name"] = tagName;//名称
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/saveSkuTag.json",postDate);
		if(rs && rs.msg == 'success'){
			//执行成功
			jumbo.showMsg("创建成功！");
			var param = {};
			param["tag.code"] = tagCode;
			var tagData = loxia.syncXhrPost($j("body").attr("contextpath") + "/findSkuTagByCode.json",param);
			if(tagData){
				tagId = tagData.id;
			}
			else{
				jumbo.showMsg("系统异常！");
				return false;
			}
		}else{
			jumbo.showMsg("操作失败！");
			return false;
		}
		$j("#dialog_addTag").hide();
		$j("#skuTagId").val(tagId);
		$j("#skuTagCode").val(tagCode).attr("disabled","disabled");
		$j("#skuTagName").val(tagName);
		$j("#skuTagType").val(tagType);
		$j("#skuTagStatus").val(tagStatus);
		$j("#tbl-sku-ref-list").jqGrid({
			url:$j("body").attr("contextpath")+"/findAllSkuRef.json?tag.id=-1&skuCmd.customer.id=-1",
			datatype: "json",
			colNames: ["ID","编码","名称","条码","品牌","客户","外部编码1","外部编码2"],
			colModel: [
			           {name: "id", index: "id", hidden: true},		         
			           {name:"code",index:"code", width:120,resizable:true,sortable:false},
			           {name:"name",index:"name", width:130,resizable:true},
			           {name:"barCode",index:"barCode", width:120,resizable:true},
			           {name:"brandName",index:"brandName", width:90,resizable:true},
			           {name:"customerName",index:"customerName", width:90,resizable:true},
			           {name:"extensionCode1",index:"extensionCode1", width:110,resizable:true},
			           {name:"extensionCode2",index:"extensionCode2", width:110,resizable:true}
			           ],
			caption: "关联Sku",
			sortname: 'sku.id',
		   	sortorder: "desc",
		   	multiselect: false,
		    rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
		   	pager:"#refPager",
		   	height:jumbo.getHeight(),
			viewrecords: true,
	   		rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" }
		});
		$j("#tbl-sku-ref-list").jqGrid('setGridParam',{
			url:$j("body").attr("contextpath")+"/findAllSkuRef.json?tag.id=-1&skuCmd.customer.id=-1"}).trigger("reloadGrid");
		$j("#skuTag_tabs").show();
		$j("#baseInfo").click();
		$j("#export").hide();
	});
	$j("#addSkuTagBack").click(function(){
		$j("#skuTag_tabs_1 input").val("");
		$j("#skuTag_tabs_1 select").val("");
		$j("#dialog_addTag input").val("");
		$j("#dialog_addTag select").val("");
		$j("#skuTag_tabs").hide();
		$j("#dialog_tagList").show();
	});
	$j("#saveSkuTagBtn").click(function(){
		var id = $j.trim($j("#skuTagId").val());
		var tagCode = $j.trim($j("#skuTagCode").val());
		var tagName = $j.trim($j("#skuTagName").val());
		var tagStatus = $j.trim($j("#skuTagStatus").val());
		var tagType = $j.trim($j("#skuTagType").val());
		if(null == tagName || '' == tagName){
			jumbo.showMsg("商品标签名称不能为空！");
			return false;
		}
		if(null == tagStatus || '' == tagStatus){
			jumbo.showMsg("请选择商品标签状态！");
			return false;
		}
		if(null == tagType || '' == tagType){
			jumbo.showMsg("请选择商品标签类型！");
			return false;
		}
		var postDate = {};
		postDate['tag.id'] = id;//id
		postDate["status"] = tagStatus;//状态
		postDate["type"] = tagType;//类型
		postDate["tag.code"] = tagCode;//编码
		postDate["tag.name"] = tagName;//名称
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/saveSkuTag.json",postDate);
		if(rs && rs.msg == 'success'){
			//执行成功
			jumbo.showMsg("保存成功！");
			//window.location.reload();
			$j("#addSkuTagBack").click();
			$j("#reset").click();
			$j("#search").click();
		}else{
			jumbo.showMsg("操作失败！");
		}
	});
	$j("#skuRefSearch").click(function(){
		var tagId = $j("#skuTagId").val();
		var customerId = $j("#customer").val();
		if(null == customerId || '' == customerId){
			jumbo.showMsg("请选择客户");
			return false;
		}
		var postData = loxia._ajaxFormToObj("skuRefForm");  
		$j("#tbl-sku-ref-list").jqGrid('setGridParam',{
			url:$j("body").attr("contextpath")+"/findAllSkuRef.json?tag.id="+tagId,postData:postData}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl-sku-ref-list",{},$j("body").attr("contextpath")+"/findAllSkuRef.json?tag.id="+tagId, postData);
		$j("#export").show();
	});
	$j("#skuRefReset").click(function(){
		$j("#skuRefForm input").val("");
		$j("#skuRefForm select").val("");
	});
	$j("#skuRefSearchDetail").click(function(){
		var tagId = $j("#skuTagIdDetail").val();
		var customerId = $j("#customerDetail").val();
		if(null == customerId || '' == customerId){
			jumbo.showMsg("请选择客户");
			return false;
		}
		var postData = loxia._ajaxFormToObj("skuRefFormDetail");  
		$j("#tbl-sku-ref-list-detail").jqGrid('setGridParam',{
			url:$j("body").attr("contextpath")+"/findAllSkuRef.json?tag.id="+tagId,postData:postData}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl-sku-ref-list-detail",{},$j("body").attr("contextpath")+"/findAllSkuRef.json", postData);
	});
	$j("#skuRefResetDetail").click(function(){
		$j("#skuRefFormDetail input").val("");
		$j("#skuRefFormDetail select").val("");
	});
	$j("#skuRefBack").click(function(){
		$j("#skuTag_tabs_1 input").val("");
		$j("#skuTag_tabs_1 select").val("");
		$j("#skuRefReset").click();
		$j("#skuTag_tabs").hide();
		$j("#dialog_tagList").show();
	});
	$j("#skuRefBackDetail").click(function(){
		$j("#skuTag_detail input").val("");
		$j("#skuTag_detail select").val("");
		//$j("#skuRefResetDetail").click();
		$j("#skuTag_detail").hide();
		$j("#dialog_tagList").show();
	});
	$j("#export").click(function(){
		$j("#skuTag_tabs").find(".ui-jqgrid-titlebar-export").click();
	});
	$j("#import").click(function(){
		tagId = $j("#skuTagId").val();
		if(null == tagId || '' == tagId)
			errors.push("请选择具体的商品标签后再导入！");
		var file=$j.trim($j("#skuFile").val()),errors=[];
		if(file==""||file.indexOf("xls")==-1){
			errors.push(i18("INVALID_MAND",i18("ENTITY_EXCELFILE")));
		}
		var url = $j("body").attr("contextpath") + "/warehouse/importRefSku.do?tag.id="+tagId;
		if(errors.length>0){
			jumbo.showMsg(errors.join("<br/>"));
		}else{
			loxia.lockPage();
			$j("#importForm").attr("action",loxia.getTimeUrl(url));
			$j("#importForm").submit();
		}
	});
});
function initBrand(){
	loxia.asyncXhrPost(
		$j("body").attr("contextpath")+"/json/findAllBrand.do",
		{},
		{
			success:function(data){
				var rs = data.brandlist;
				if(rs.length>0){
					$j("#brandName option").remove();
					$j("#brandNameDetail option").remove();
					$j("#brandName").append("<option></option>");
					$j("#brandNameDetail").append("<option></option>");
					$j.each(rs,function(i,item){
						$j("#brandName").append("<option value='"+item.name+"'>"+item.name+"</option>");
						$j("#brandNameDetail").append("<option value='"+item.name+"'>"+item.name+"</option>");
					});
					$j("#brandName").flexselect();
					$j("#brandNameDetail").flexselect();
					$j("#brandName").val("");
					$j("#brandNameDetail").val("");
				}
			}
		}
	);
}
function editDetial(obj,event){
	var row = $j(obj).parent().parent().attr("id");
	tagId = row;
	var data = $j("#tbl-tag-list").jqGrid("getRowData",row);
	$j("#skuTagId").val(row);
	$j("#skuTagCode").val(data['code']).attr("disabled","disabled");
	$j("#skuTagName").val(data['name']);
	$j("#skuTagStatus").val(data['tagStatus']);
	$j("#skuTagType").val(data['tagType']);
	$j("#tbl-sku-ref-list").jqGrid({
		url:$j("body").attr("contextpath")+"/findAllSkuRef.json?tag.id="+row,
		datatype: "json",
		colNames: ["ID","编码","名称","条码","品牌","客户","外部编码1","外部编码2"],
		colModel: [
		           {name: "id", index: "id", hidden: true},		         
		           {name:"code",index:"code", width:120,resizable:true,sortable:false},
		           {name:"name",index:"name", width:130,resizable:true},
		           {name:"barCode",index:"barCode", width:120,resizable:true},
		           {name:"brandName",index:"brandName", width:90,resizable:true},
		           {name:"customerName",index:"customerName", width:90,resizable:true},
		           {name:"extensionCode1",index:"extensionCode1", width:110,resizable:true},
		           {name:"extensionCode2",index:"extensionCode2", width:110,resizable:true}
		           ],
		caption: "关联Sku",
		sortname: 'sku.id',
	   	sortorder: "desc",
	   	multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#refPager",
	   	height:jumbo.getHeight(),
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	//jumbo.bindTableExportBtn("tbl-sku-ref-list",{});
	$j("#tbl-sku-ref-list").jqGrid('setGridParam',{
		url:$j("body").attr("contextpath")+"/findAllSkuRef.json?tag.id="+row}).trigger("reloadGrid");
	var postData = loxia._ajaxFormToObj("skuRefForm");
	postData["tag.id"] = row;
	jumbo.bindTableExportBtn("tbl-sku-ref-list",{},$j("body").attr("contextpath")+"/findAllSkuRef.json?tag.id="+row, postData);
	$j("#export").show();
	$j("#dialog_tagList").hide();
	$j("#skuTag_tabs").show();
}
function initCustomer(){
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findBiCustomer.do");
	$j("#customer").append("<option></option>");
	$j("#customerDetail").append("<option></option>");
	for(var idx in result){
		$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#customer"));
		$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#customerDetail"));
	}
	$j("#customer").flexselect();
	$j("#customerDetail").flexselect();
	$j("#customer").val("");
	$j("#customerDetail").val("");
}
function showDetail(obj){
	var row = $j(obj).parent().parent().attr("id");
	var data = $j("#tbl-tag-list").jqGrid("getRowData",row);
	$j("#skuTagIdDetail").val(row);
	$j("#skuTagCodeDetail").val(data['code']).attr("disabled","disabled");
	$j("#skuTagNameDetail").val(data['name']).attr("disabled","disabled");
	$j("#skuTagStatusDetail").val(data['tagStatus']).attr("disabled","disabled");
	$j("#skuTagTypeDetail").val(data['tagType']).attr("disabled","disabled");
	$j("#tbl-sku-ref-list-detail").jqGrid({
		url:$j("body").attr("contextpath")+"/findAllSkuRef.json?tag.id="+row,
		datatype: "json",
		colNames: ["ID","编码","名称","条码","品牌","客户","外部编码1","外部编码2"],
		colModel: [
		           {name: "id", index: "id", hidden: true},		         
		           {name:"code",index:"code", width:120,resizable:true,sortable:false},
		           {name:"name",index:"name", width:130,resizable:true},
		           {name:"barCode",index:"barCode", width:120,resizable:true},
		           {name:"brandName",index:"brandName", width:90,resizable:true},
		           {name:"customerName",index:"customerName", width:90,resizable:true},
		           {name:"extensionCode1",index:"extensionCode1", width:110,resizable:true},
		           {name:"extensionCode2",index:"extensionCode2", width:110,resizable:true}
		           ],
		caption: "关联Sku",
		sortname: 'sku.id',
	   	sortorder: "desc",
	   	multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#refPagerDetail",
	   	height:jumbo.getHeight(),
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	//jumbo.bindTableExportBtn("tbl-sku-ref-list-detail",{});
	$j("#tbl-sku-ref-list-detail").jqGrid('setGridParam',{
		url:$j("body").attr("contextpath")+"/findAllSkuRef.json?tag.id="+row}).trigger("reloadGrid"); 
	$j("#dialog_tagList").hide();
	$j("#skuTag_detail").show();
}
function importReturn(){
	//window.location=loxia.getTimeUrl($j("body").attr("contextpath")+"/auth/skutagmaintain.do");
	jumbo.showMsg("导入成功！");
	$j("#tbl-sku-ref-list").jqGrid('setGridParam',{
		url:$j("body").attr("contextpath")+"/findAllSkuRef.json?tag.id="+tagId}).trigger("reloadGrid"); 
	
}