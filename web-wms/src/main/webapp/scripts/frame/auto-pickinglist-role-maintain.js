$j.extend(loxia.regional['zh-CN'], {
});

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}
var roleId;
$j(document).ready(function (){
	var trueOrFalse=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"trueOrFalse"});
	var roleStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"autoPickingListRoleStatus"});
	$j("#dialog_addRole").hide();
	initSkuCategories();
	initSkuSizeConfig();
	initType();
	$j("#tbl-role-list").jqGrid({
		url:$j("body").attr("contextpath")+"/findAutoPLRoleByPagination.json",
		datatype: "json",
		colNames: ["ID","编码","名称","状态","创建时间","操作"],
		colModel: [
		           {name: "id", index: "id", hidden: true},		         
		           {name:"code",index:"code", width:95,resizable:true,sortable:false},
		           {name:"name",index:"name", width:95,resizable:true},
		           {name:"roleStatus",index:"roleStatus", width:90,resizable:true,formatter:"select",editoptions:roleStatus},
		           {name:"createTime",index:"role.create_time", width:150,resizable:true},
		           {name:"operator", width: 60, resizable: true,formatter:"buttonFmatter", formatoptions:{"buttons":{label:"编辑", onclick:"editDetial(this,event);"}}}
		           ],
		caption: "仓库配货清单自动创建规则列表",
		sortname: 'role.create_time',
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
	$j("#tbl-tmt-list").jqGrid({
			url:$j("body").attr("contextpath")+"/findTransportatorListAll.json",
			datatype: "json",
			colNames: ["ID","物流商编码","物流商名称",""],
			colModel: [
			           {name:"id", index: "id", hidden: true},		         
			           {name:"expCode",index:"expCode",width:260},
			           {name:"name",index:"name", width:260},
			           {name:"isMaT",width:100,formatter:function(v,x,r){return "<input type='checkbox' name = 'isMaTCheckbox'/>";}},
			           ],
			caption: "配货物流商列表",
			sortname: 'rd.sort',
		   	sortorder: "asc",
		   	multiselect: false,
		    rowNum: -1,
		   	height:jumbo.getHeight(),
			viewrecords: true,
	   		rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" }
		});
	$j("#search").click(function(){
		var postData = loxia._ajaxFormToObj("tagForm");  
		$j("#tbl-role-list").jqGrid('setGridParam',{
			url:$j("body").attr("contextpath")+"/findAutoPLRoleByPagination.json",postData:postData}).trigger("reloadGrid"); 
	});
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val("");
	});
	$j("#addRoleBtn").click(function(){
		$j("#dialog_roleList").hide();
		$j("#dialog_addRole").show();
	});
	$j("#addRoleBack").click(function(){
		$j("#dialog_addRole input").val("");
		$j("#dialog_addRole select").val("");
		$j("#dialog_addRole").hide();
		$j("#dialog_roleList").show();
	});
	$j("#saveRoleBtn").click(function(){
		var code = $j.trim($j("#code").val());
		var name = $j.trim($j("#name").val());
		if(null == code || '' == code){
			jumbo.showMsg("编码不能为空！");
			return false;
		}
		if(null == name || '' == name){
			jumbo.showMsg("名称不能为空！");
			return false;
		}
		var rs = loxia.syncXhrPost($j("body").attr("contextpath")+"/findAutoPLRoleExistByCode.json?role.code="+code);
		if(rs && rs.result == true){
			jumbo.showMsg("该编码已存在！");
			return false;
		}
		var id = -1;
		var status = 1;
		var postData = {};
		postData['role.id'] = id;//id
		postData["status"] = status;//状态,默认正常
		postData["role.code"] = code;//编码
		postData["role.name"] = name;//名称
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/saveAutoPLRole.json",postData);
		if(rs && rs.msg == 'success'){
			//执行成功
			jumbo.showMsg("创建成功！");
			var param = {};
			param["role.code"] = code;
			var roleData = loxia.syncXhrPost($j("body").attr("contextpath") + "/findAutoPLRoleByCode.json",param);
			if(roleData){
				roleId = roleData.id;
			}
			else{
				jumbo.showMsg("系统异常！");
				return false;
			}
		}else{
			jumbo.showMsg("操作失败！");
			return false;
		}
		$j("#dialog_addRole").hide();
		$j("#roleId").val(roleId);
		$j("#roleCode").val(code).attr("disabled","disabled");
		$j("#roleName").val(name);
		$j("#roleStatus").val(status);
		$j("#tbl-role-detail-list").jqGrid({
			url:$j("body").attr("contextpath")+"/findAllPLRoleDetailByRoleId.json?role.id="+roleId,
			datatype: "json",
			colNames: ["ID","类型ID","类型","商品类型ID","商品分类","商品大小ID","商品大小","优先级","最大单数","最小单数","优先发货城市","是否包含SN商品","是否允许指定物流商混合创建","操作"],
			colModel: [
			           {name: "id", index: "id", hidden: true},		         
			           {name:"type",index:"type", hidden: true},
			           {name:"pickingTypeName",index:"pickingTypeName", width:80,resizable:true,sortable:false},
			           {name:"skuCategoryId",index:"skuCategoryId", hidden: true},
			           {name:"skuCategoryName",index:"skuCategoryName", width:130,resizable:true},
			           {name:"skuSizeId",index:"skuSizeId", hidden: true},
			           {name:"skuSizeName",index:"skuSizeName", width:80,resizable:true},
			           {name:"sort",index:"sort", width:90,resizable:true},
			           {name:"maxOrder",index:"maxOrder", width:90,resizable:true},
			           {name:"minOrder",index:"minOrder", width:90,resizable:true},
			           {name:"city",index:"city", width:90,resizable:true},
			           {name:"isSn",index:"isSn",width:90,resizable:true,formatter:'select',editoptions:trueOrFalse},
			           {name:"isTransAfter",index:"isTransAfter",width:160,resizable:true,formatter:'select',editoptions:trueOrFalse},
			           {name:"operator", width: 60, resizable: true,formatter:"buttonFmatter", formatoptions:{"buttons":{label:"删除", onclick:"deleteDetail(this,event);"}}}
			           ],
			caption: "规则列表",
			sortname: 'rd.sort',
		   	sortorder: "asc",
		   	multiselect: false,
		    rowNum: -1,
			//rowList:jumbo.getPageSizeList(),
		   	//pager:"#detailPager",
		   	height:jumbo.getHeight(),
			viewrecords: true,
	   		rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" }
		});
		$j("#tbl-role-detail-list").jqGrid('setGridParam',{
			url:$j("body").attr("contextpath")+"/findAllPLRoleDetailByRoleId.json?role.id=" + roleId}).trigger("reloadGrid");
		//$j("#tbl-tmt-list").jqGrid('setGridParam',{
		//	url:$j("body").attr("contextpath")+"/findAllPLRoleDetailByRoleId.json?role.id=" + roleId}).trigger("reloadGrid");
		$j("#dialog_roleDetail").show();
	});
	$j("#saveRoleDetailBtn").click(function(){
		var id = $j.trim($j("#roleId").val());
		var roleCode = $j.trim($j("#roleCode").val());
		var roleName = $j.trim($j("#roleName").val());
		var roleStatus = $j.trim($j("#roleStatus").val());
		if(null == roleName || '' == roleName){
			jumbo.showMsg("规则名称不能为空！");
			return false;
		}
		if(null == roleStatus || '' == roleStatus){
			jumbo.showMsg("请选择规则状态！");
			return false;
		}
		var postData = {};
		postData['role.id'] = id;//roleId
		postData["status"] = roleStatus;//状态
		postData["role.code"] = roleCode;//编码
		postData["role.name"] = roleName;//名称
		var detailList = $j("#tbl-role-detail-list").jqGrid("getRowData");
		for(var i = 0; i < detailList.length; i++){
			var detail = detailList[i];
			postData["roleDetailList["+i+"].id"] = detail.id;
			postData["roleDetailList["+i+"].city"] = detail.city;
			postData["roleDetailList["+i+"].isSnString"] = detail.isSn;
			postData["roleDetailList["+i+"].isTransAfter"] = detail.isTransAfter;
			postData["roleDetailList["+i+"].roleId"] = id;
			postData["roleDetailList["+i+"].id"] = detail.id;
			postData["roleDetailList["+i+"].type"] = detail.type;
			postData["roleDetailList["+i+"].sort"] = detail.sort;
			postData["roleDetailList["+i+"].maxOrder"] = detail.maxOrder;
			postData["roleDetailList["+i+"].minOrder"] = detail.minOrder;
			postData["roleDetailList["+i+"].skuSizeId"] = detail.skuSizeId;
			postData["roleDetailList["+i+"].skuCategoryId"] = detail.skuCategoryId;
			postData["roleDetailList["+i+"].maTList"] = detail.isMaT;
		}
		if(confirm("确定保存修改？")){
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/saveAutoPLRoleDetail.json",postData);
			if(rs && rs.msg == 'success'){
				//执行成功
				jumbo.showMsg("修改成功！"); 
				$j("#dialog_roleDetail").hide();
				$j("#reset").click();
				$j("#search").click();
				$j("#dialog_roleList").show();
			}else{
				jumbo.showMsg("操作失败！");
			}
		}
	});
	$j("#addRoleDetailBack").click(function(){
		$j("#dialog_roleDetail input").val("");
		$j("#dialog_roleDetail select").val("");
		$j("#dialog_roleDetail").hide();
		$j("#dialog_roleList").show();
	});
	$j("#addRoleDetail").click(function(){
		var skuCategoryId = $j("#categorySelect").val();
		var skuCategoryName = $j("#categorySelect option:selected").text()=='--请选择--'?'':$j("#categorySelect option:selected").text();
		var skuSizeId = $j("#skuSizeSelect").val();
		var skuSizeName = $j("#skuSizeSelect option:selected").text()=='--请选择--'?'':$j("#skuSizeSelect option:selected").text();
		var sort = $j.trim($j("#sort").val());
		var maxOrder = $j.trim($j("#maxOrder").val());
		var minOrder = $j.trim($j("#minOrder").val());
		var type = $j("#type").val();
		var pickingTypeName = $j("#type option:selected").text()=='--请选择--'?'':$j("#type option:selected").text();
		var city = $j("#selCity").val();
		var isSn = $j("#isSn").val();
		var isTransAfter = "";
		if($j("#isTransAfter").attr("checked") == true){
			isTransAfter = true;
		}else{
			isTransAfter = false;			
		}
		var ids = $j("#tbl-tmt-list").jqGrid('getDataIDs');
		var index = 0;
		var maValue = "";
		for(var i=0;i < ids.length;i++){
				var datas = $j("#tbl-tmt-list").jqGrid('getRowData',ids[i]);
				if($j("#tbl-tmt-list tr[id="+datas["id"]+"] input[name=isMaTCheckbox]").attr("checked") == true){
					maValue += datas["id"]+",";
					index++;
				}
		}
//		if('' == skuCategoryId){
//			jumbo.showMsg("请选择商品分类！");
//			return false;
//		}
//		if('' == skuSizeId){
//			jumbo.showMsg("请选择商品大小！");
//			return false;
//		}
		if('' == type){
			jumbo.showMsg("请选择类型！");
			return false;
		}
		if('' == sort){
			jumbo.showMsg("优先级不能为空！");
			return false;
		}
		if('' == maxOrder){
			jumbo.showMsg("最大单数不能为空！");
			return false;
		}
		if('' == minOrder){
			jumbo.showMsg("最小单数不能为空！");
			return false;
		}
		if(0 >= parseInt(maxOrder) || 0 >= parseInt(minOrder)){
			jumbo.showMsg("单数必须大于0！");
			return false;
		}
		if(parseInt(minOrder) > parseInt(maxOrder)){
			jumbo.showMsg("最小单数不能大于最大单数！");
			return false;
		}
		if($j("#isTransAfter").attr("checked") == false){
			if(index == 0){
				jumbo.showMsg("允许混合物流商创建和物流商必须选一种！");
				return false;
			}			
		}
		var isRepeat = false;
		var detailList = $j("#tbl-role-detail-list").jqGrid("getRowData");
		for(var i = 0; i < detailList.length; i++){
			var detail = detailList[i];
			var dType = detail.type;
			var dSort = detail.sort;
			var dMaxOrder = detail.maxOrder;
			var dMinOrder = detail.minOrder;
			var dSkuSizeId = detail.skuSizeId;
			var dSkuCategoryId = detail.skuCategoryId;
			var dSendCity = detail.city;
			var dIsSn = detail.isSn;
			console.log(detail.isSn+"-------");
			var dIsTransAfter = detail.isTransAfter;
			if(isSn == dIsSn && isTransAfter == dIsTransAfter && type == dType && sort == dSort && maxOrder == dMaxOrder && minOrder == dMinOrder && skuSizeId == dSkuSizeId && skuCategoryId == dSkuCategoryId&&city==dSendCity){
				isRepeat = true;
				break; 
			}
		}
		if(true == isRepeat){
			jumbo.showMsg("规则明细不能重复！");
			return false;
		}
		var detailJSON = jQuery.parseJSON('{}');
		detailJSON['type'] = type;
		detailJSON['pickingTypeName'] = pickingTypeName;
		detailJSON['skuCategoryId'] = skuCategoryId;
		detailJSON['skuCategoryName'] = skuCategoryName;
		detailJSON['skuSizeId'] = skuSizeId;
		detailJSON['skuSizeName'] = skuSizeName;
		detailJSON['sort'] = sort;
		detailJSON['maxOrder'] = maxOrder;
		detailJSON['minOrder'] = minOrder;
		detailJSON['city'] = city;
		detailJSON['isSn'] = isSn;
		detailJSON['isTransAfter'] = isTransAfter;
		detailJSON['isMaT'] = maValue.substring(0,maValue.length-1);
		$j("#tbl-role-detail-list").addRowData("1", detailJSON, "last");
		clearDetailEdit();
		formatCheckBox();
		$j("#isTransAfter").attr("checked",false);
	});
	$j("#sort").keyup(function(){
		var s = $j(this).val();
		if(!checkNumber(s)){
			 $j(this).val("");
		}
	});
	$j("#maxOrder").keyup(function(){
		var s = $j(this).val();
		if(!checkNumber(s)){
			 $j(this).val("");
		}
		s = $j.trim(s);
		if(0 >= s){
			$j(this).val("");
			jumbo.showMsg("最大单数必须大于0！");
		}
	});
	$j("#minOrder").keyup(function(){
		var s = $j(this).val();
		if(!checkNumber(s)){
			 $j(this).val("");
		}
		s = $j.trim(s);
		if(0 >= s){
			$j(this).val("");
			jumbo.showMsg("最小单数必须大于0！");
		}
	});

});
function editDetial(obj,event){
	var row = $j(obj).parent().parent().attr("id");
	var trueOrFalse1=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"trueOrFalse"});
	tagId = row;
	formatCheckBox();
	$j("#isTransAfter").attr("checked",false);
	var data = $j("#tbl-role-list").jqGrid("getRowData",row);
	$j("#roleId").val(row);
	$j("#roleCode").val(data['code']).attr("disabled","disabled");
	$j("#roleName").val(data['name']);
	$j("#roleStatus").val(data['roleStatus']);
	$j("#tbl-role-detail-list").jqGrid({
		url:$j("body").attr("contextpath")+"/findAllPLRoleDetailByRoleId.json?role.id="+row,
		datatype: "json",
		colNames: ["ID","类型ID","类型","商品类型ID","商品分类","商品大小ID","商品大小","优先级","最大单数","最小单数","优先发货城市","是否包含SN商品","是否允许指定物流商混合创建","","操作"],
		colModel: [
		           {name: "id", index: "id", hidden: true},		         
		           {name:"type",index:"type", hidden: true},
		           {name:"pickingTypeName",index:"pickingTypeName", width:80,resizable:true,sortable:false},
		           {name:"skuCategoryId",index:"skuCategoryId", hidden: true},
		           {name:"skuCategoryName",index:"skuCategoryName", width:130,resizable:true},
		           {name:"skuSizeId",index:"skuSizeId", hidden: true},
		           {name:"skuSizeName",index:"skuSizeName", width:80,resizable:true},
		           {name:"sort",index:"sort", width:90,resizable:true},
		           {name:"maxOrder",index:"maxOrder", width:90,resizable:true},
		           {name:"minOrder",index:"minOrder", width:90,resizable:true},
		           {name:"city",index:"city", width:90,resizable:true},
		           {name:"isSn",index:"isSn",width:90,resizable:true,formatter:'select',editoptions:trueOrFalse1},
		           {name:"isTransAfter",index:"isTransAfter",width:160,resizable:true,formatter:'select',editoptions:trueOrFalse1},
		           {name:"isMaT",index:"isMaT",hidden:true},
		           {name:"operator", width: 60, resizable: true,formatter:"buttonFmatter", formatoptions:{"buttons":{label:"删除", onclick:"deleteDetail(this,event);"}}}
		           ],
		caption: "规则列表",
		sortname: 'rd.sort',
	   	sortorder: "asc",
	   	multiselect: false,
	    rowNum: -1,
	   	height:jumbo.getHeight(),
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	$j("#tbl-role-detail-list").jqGrid('setGridParam',{
		url:$j("body").attr("contextpath")+"/findAllPLRoleDetailByRoleId.json?role.id="+row}).trigger("reloadGrid");
	$j("#dialog_roleList").hide();
	//$j("#detail-edit").hide();
	clearDetailEdit();
	$j("#dialog_roleDetail").show();
}
function deleteDetail(obj,event){
	var row = $j(obj).parent().parent().attr("id");
	$j("#tbl-role-detail-list").jqGrid("delRowData", row);
}
function initSkuCategories(){
	//初始化商品分类
	$j("#categorySelect").append("<option value=''>--请选择--</option>");
	//初始化商品分类
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findCategories.do");
	for(var idx in result){
		$j("<option value='" + result[idx].id + "'>" + result[idx].skuCategoriesName+"</option>").appendTo($j("#categorySelect"));
	}
    //$j("#categorySelect").flexselect();
    $j("#categorySelect").val("");
}
function initSkuSizeConfig(){
	//初始化商品大小列表
	var cList = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findskusize.json");
	$j("#skuSizeSelect").append("<option value=''>--请选择--</option>");
	for(var i=0;i<cList.length;i++){
		$j("#skuSizeSelect").append("<option value="+cList[i].id+">"+cList[i].name+"</option>");
	}
	//$j("#skuSizeSelect").flexselect();
    $j("#skuSizeSelect").val(""); 
}
function initType(){
	//$j("#type").flexselect();
    $j("#type").val(""); 
}
function checkNumber(s){
    if (s!=null && s!=""){
      return !isNaN(s);
   }
    return false;
}
function clearDetailEdit(){
	$j("#detail-edit input").val("");
	$j("#detail-edit select").val("");
}

function formatCheckBox(){
		var ids = $j("#tbl-tmt-list").jqGrid('getDataIDs');
		for(var i=0;i < ids.length;i++){
				var datas = $j("#tbl-tmt-list").jqGrid('getRowData',ids[i]);
				if($j("#tbl-tmt-list tr[id="+datas["id"]+"] input[name=isMaTCheckbox]").attr("checked") == true){
					$j("#tbl-tmt-list tr[id="+datas["id"]+"] input[name=isMaTCheckbox]").attr("checked",false);
				}
		}
}
