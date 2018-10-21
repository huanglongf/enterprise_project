$j.extend(loxia.regional['zh-CN'],{
	ROLE_LIST : "现有配货规则列表",
	ROLE_NAME : "规则名称",
	CREATE_NAME :"创建人",
	CREATE_TIME : "创建时间",
	LAST_CHANGE_TIME : "最后修改时间",
	STATUS : "状态",
	OPTION : "操作",
	DISABLED_SUCCESS : "配货规则禁用成功！",
	OPTIONAL_CONDITION : "配货规则可选条件",
	CURRENT_RULE_CONDITION_DETAIL : "当前规则条件明细",
	CODE : "条件编码",
	NAME : "条件描述",
	GROUP_CODE : "条件组编码",
	GROUP_NAME : "条件组描述",
	KVALUE : "条件值",
	TYPE : "条件类型",
	DELETE : "删除",
	ID_IS_LIVE : "不能重复添加配货规则明细！",
	RULE_NAME_IS_NOT_NULL : "规则名称不能为空！",
	RULE_NAME_IS_EXIST : "规则名称已经存在！",
	SKU_BRACODE_IS_NOT_NULL : "SKU编码不能为空！",
	CURRENT_RULE_CONDITION_DETAIL_IS_NULL : "新增配货规则时，当前规则条件明细不能为空！"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

var isInitOrderDetail = true;
var t_ids = new Array(); 
var arrRemark = new Array();
var arrGroupCode = new Array();

function showDetail(tag){
	t_ids = new Array(); 
	arrRemark = new Array();
	arrGroupCode = new Array();
	$j("#div1").addClass("hidden");
    $j("#div2").removeClass("hidden");
    $j("#editButton").removeClass("hidden");
    $j("#newButton").addClass("hidden");
    $j("#btnlist").removeClass("hidden");
    var id = $j(tag).parents("tr").attr("id");
    var data=$j("#tbl-ruleList").jqGrid("getRowData",id);
    $j("#distributionRuleName").val(data["name"]);
    $j("#distributionRuleName").attr("readonly","readonly");
    var ruleId = data["id"];
  //初始化条件描述
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getDistributionRuleCondition.do");
	for(var idx in result.drcList){
		$j("<option value='" + result.drcList[idx].groupCode + "'>"+ result.drcList[idx].groupName +"</option>").appendTo($j("#groupcode"));
	}
	
	//加载可选条件
 	$j("#tbl-optionalRuleDetail").jqGrid({
		url:$j("body").attr("contextpath") + "/getDistributionRuleConditionDetail.json",
		datatype: "json",
		colNames: ["ID",i18("CODE"),i18("GROUP_NAME"),i18("GROUP_CODE"),i18("NAME"),i18("KVALUE"),i18("TYPE"),i18("OPTION")],
		colModel: [
				   {name: "id", index: "id", hidden: true},
				   {name: "code", index: "code", hidden: true},
		           {name: "groupName", index: "groupName", width:150,resizable:true},
		           {name: "groupCode", index: "groupCode", hidden: true},
		           {name: "name", index: "name", width: 150, resizable: true},
		           {name: "kValue", index: "kValue", hidden: true},
		           {name: "intType", index: "intType",  hidden: true},
		           {name: "btnAdd1", width: 80, resizable: true}
		           ],
		caption: i18("OPTIONAL_CONDITION"),
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: false,
	    rowNum: 200,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc",
		hidegrid:false,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var btn1 = '<div><button type="button" style="width:80px;" class="add" name="btnAdd1" loxiaType="button" onclick="addDistributionRuleConditionDetail(this,event);">'+"添加"+'</button></div>';
			var ids = $j("#tbl-optionalRuleDetail").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var idData = $j("#tbl-optionalRuleDetail").jqGrid('getRowData',ids[i]);
				var input = '<div><input loxiaType="input" id = "ruleName'+ids[i]+'" name="distributionRule.name" trim="true"/></div>';
				if(idData["groupCode"] && idData["groupCode"]=="SKUCode"){
					$j("#tbl-optionalRuleDetail").jqGrid('setRowData',ids[i],{"groupName":idData["name"]});
					$j("#tbl-optionalRuleDetail").jqGrid('setRowData',ids[i],{"name":input});
					
				}
				$j("#tbl-optionalRuleDetail").jqGrid('setRowData',ids[i],{"btnAdd1":btn1});
			}
			loxia.initContext($j(this));
		}
	});
	
 	//加载当前规则条件明细
	 	$j("#tbl-currentRuleDetail").jqGrid({
		colNames: ["ID","规则ID","条件ID","内容",i18("CODE"),i18("GROUP_NAME"),i18("GROUP_CODE"),i18("NAME"),i18("KVALUE"),i18("TYPE"),i18("OPTION")],
		colModel: [
				   {name: "id", index: "id", hidden: true},
				   {name: "l_ruleId", index: "l_ruleId", hidden: true},
				   {name: "l_conditionId", index: "l_conditionId", hidden: true},
				   {name: "remark", index: "remark", hidden: true},
				   {name: "code", index: "code", hidden: true},
		           {name: "groupName", index: "groupName", width:150,resizable:true,hidden: true},
		           {name: "groupCode", index: "groupCode", hidden: true},
		           {name: "name", index: "name", width: 150, resizable: true},
		           {name: "kValue", index: "kValue", hidden: true},
		           {name: "intType", index: "intType",  hidden: true},
		           {name: "deleteAdd1", width: 80, resizable: true}
		           ],
		caption: i18("CURRENT_RULE_CONDITION_DETAIL"),
	   	height:"auto",
	    rownumbers:true,
	    viewrecords: true,
		hidegrid:false
	});
	$j("#tbl-currentRuleDetail").empty();
 	var postData = {};
	postData['distributionRule.id'] = ruleId;
	var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/getDistributionRuleConditionCurrentDetail.json",postData);
	var data = result["dbrdList"];
	var content = null;
	for(var i = 0 ; i<data.length;i++){
		content = "<tr id=\""+data[i]["l_conditionId"]+"\"><td class='label' style='width: 26px'>"+data[i]["l_conditionId"]+"</td><td class='label' style='text-align: center;width:151px'>"+data[i]["name"]+"</td><td>";
		if(data && data[i]["groupCode"] == "SKUCode"){
			arrRemark.push(data[i]["remark"]+"");
			content = "<tr id=\""+data[i]["l_conditionId"]+"\"><td class='label' style='width: 26px'>"+data[i]["l_conditionId"]+"</td><td class='label' style='text-align: center;width:151px'>"+data[i]["remark"]+"</td><td>";
		}
		content += "<button style='width: 80px;text-align: center'  class=\"delete\"  name=\"deleteAdd1\" loxiaType=\"button\" onclick=\"deleteDistributionRuleConditionDetail('"+data[i]["l_conditionId"]+","+data[i]["remark"]+","+data[i]["groupCode"]+"')\">"+"删除"+"</button></td></tr>";
		t_ids.push(data[i]["l_conditionId"]+"");
		if((data[i]["groupCode"] == "SKUCode") || (data[i]["groupCode"] == "largeAndSmallCommodities")){
			
		}else{
			arrGroupCode.push(data[i]["groupCode"]);
			$j.unique(arrGroupCode);
		}
		$j("#tbl-currentRuleDetail").append(content);
	}
}

//初始化
$j(document).ready(function (){
	var postData = loxia._ajaxFormToObj("query-form");
	$j("#tbl-ruleList").jqGrid({
		url:$j("body").attr("contextpath") + "/findAllDistributionRule.json",
		datatype: "json",
		postData: postData,
		colNames: ["ID",i18("ROLE_NAME"),i18("CREATE_NAME"),i18("CREATE_TIME"),i18("LAST_CHANGE_TIME"),i18("STATUS"),i18("OPTION")],
		colModel: [
				   {name: "id", index: "id", hidden: true},
		           {name: "name", index: "name", formatter:"linkFmatter", formatoptions:{onclick:"showDetail"}, width:120,resizable:true},
		           {name: "createName", index: "createName", width: 150, resizable: true},
		           {name: "createTime", index: "createTime", width: 150, resizable: true},
		           {name: "lastModifyTime", index: "lastModifyTime", width: 150, resizable: true},
		           {name: "strStatus", index: "strStatus", hidden: true},
		           {name: "btnDisable", width: 80, resizable: true}
		           ],
		caption: i18("ROLE_LIST"),
		pager:"#pager_query",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: false,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc",
		hidegrid:false,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var btn1 = '<div><button type="button" style="width:80px;" class="confirm" name="btnDisable" loxiaType="button" onclick="disableDistributionRule(this,event);">'+"禁用"+'</button></div>';
			var ids = $j("#tbl-ruleList").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var id = $j("#tbl-ruleList").jqGrid('getRowData',ids[i]);
				$j("#tbl-ruleList").jqGrid('setRowData',ids[i],{"btnDisable":btn1});
			}
			loxia.initContext($j(this));
		}
	});

	$j("#back,#back2,#back3").click(function(){
		$j("#div2").addClass("hidden");
		$j("#div1").removeClass("hidden");
		t_ids = new Array(); 
		arrRemark = new Array();
		arrGroupCode = new Array();
	});

	$j("#newButton").click(function(){
		//当前没有选择规则条件明细
		if(t_ids.length == 0){
			jumbo.showMsg(i18("CURRENT_RULE_CONDITION_DETAIL_IS_NULL"));
			return;
		}
		var value = $j("#distributionRuleName").val();
		if(value == ""){
			jumbo.showMsg(i18("RULE_NAME_IS_NOT_NULL"));
			$j("#distributionRuleName").focus();
			return;
		}
		var baseUrl = $j("body").attr("contextpath");
		postData['distributionRule.name']=value;
		var rule_rs = loxia.syncXhrPost(baseUrl + "/checkRuleNameIsExist.json",postData);
		if(rule_rs && rule_rs["success"]=="success"){
			jumbo.showMsg(i18("RULE_NAME_IS_EXIST"));
			$j("#distributionRuleName").focus();
			return;
		}else{
			var t_ids_f = t_ids.join("-");
			postData["idList"] = t_ids_f;
			var arrRemark_f = arrRemark.join(",");
			postData["remark"] =  arrRemark_f ;
			var new_rs = loxia.syncXhrPost(baseUrl + "/newDistributionRuleAndDetail.json",postData);
			if(new_rs && new_rs["success"]=="success"){
				$j("#div2").addClass("hidden");
				$j("#div1").removeClass("hidden");
				var postDatas = loxia._ajaxFormToObj("query-form");
				$j("#tbl-ruleList").jqGrid('setGridParam',{
					url:window.$j("body").attr("contextpath")+"/json/findAllDistributionRule.json",
					postData:postDatas,
					page:1
				}).trigger("reloadGrid");
				$j("#distributionRuleName").val("");
				$j("#query-form1 input,#query-form1 select").val("");
				var groupcode = $j("#groupcode").val();//loxia._ajaxFormToObj("query-form1");
				var postData1 = {};
				postData1["groupCode"] = groupcode ;
				$j("#tbl-optionalRuleDetail").jqGrid('setGridParam',{
					url:window.$j("body").attr("contextpath")+"/json/getDistributionRuleConditionDetail.json",
					postData:postData1,
					page:1
				}).trigger("reloadGrid");
				$j("#tbl-currentRuleDetail").empty();	
			}
		}
		t_ids = new Array(); 
		arrRemark = new Array();
	});
	
	$j("#editButton").click(function(){
		//当前没有选择规则条件明细
		if(t_ids.length == 0){
			jumbo.showMsg(i18("CURRENT_RULE_CONDITION_DETAIL_IS_NULL"));
			return;
		}
		var t_ids_f = t_ids.join("-");
		postData["idList"] = t_ids_f;
		var arrRemark_f = arrRemark.join(",");
		postData["remark"] =  arrRemark_f ;
		var ruleName = $j("#distributionRuleName").val();
		postData["distributionRule.name"] = ruleName;
		var update_rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/updateDistributionRuleAndDetail.json",postData);
		if(update_rs && update_rs["success"]=="success"){
			$j("#div2").addClass("hidden");
			$j("#div1").removeClass("hidden");
			var postDatas = loxia._ajaxFormToObj("query-form");
			$j("#tbl-ruleList").jqGrid('setGridParam',{
				url:window.$j("body").attr("contextpath")+"/json/findAllDistributionRule.json",
				postData:postDatas,
				page:1
			}).trigger("reloadGrid");
			$j("#distributionRuleName").val("");
			$j("#query-form1 input,#query-form1 select").val("");
			var groupcode = $j("#groupcode").val();//loxia._ajaxFormToObj("query-form1");
			var postData1 = {};
			postData1["groupCode"] = groupcode ;
			$j("#tbl-optionalRuleDetail").jqGrid('setGridParam',{
				url:window.$j("body").attr("contextpath")+"/json/getDistributionRuleConditionDetail.json",
				postData:postData1,
				page:1
			}).trigger("reloadGrid");
			$j("#tbl-currentRuleDetail").empty();	
		}
		t_ids = new Array(); 
		arrRemark = new Array();
		
	});
	
	//查询按钮功能 根据作业单号和状态查询
	$j("#btn-query").click(function(){
		var postData = loxia._ajaxFormToObj("query-form");
		$j("#tbl-ruleList").jqGrid('setGridParam',{
			url:window.$j("body").attr("contextpath")+"/json/findAllDistributionRule.json",
			postData:postData,
			page:1
		}).trigger("reloadGrid");
	});
	
	//重置
	$j("#reset").click(function(){
		$j("#query-form input,#query-form select").val("");
	});
	
	$j("#btn-newRule").click(function(){
		 t_ids = new Array(); 
		 arrRemark = new Array();
		 arrGroupCode = new Array();
		 $j("#div1").addClass("hidden");
	     $j("#div2").removeClass("hidden");
	     $j("#editButton").addClass("hidden");
	     $j("#newButton").removeClass("hidden");
	     $j("#btnlist").removeClass("hidden");
	     $j("#distributionRuleName").removeAttr("readonly");
		var postDatas = loxia._ajaxFormToObj("query-form");
		$j("#tbl-ruleList").jqGrid('setGridParam',{
			url:window.$j("body").attr("contextpath")+"/json/findAllDistributionRule.json",
			postData:postDatas,
			page:1
		}).trigger("reloadGrid");
		$j("#distributionRuleName").val("");
		$j("#query-form1 input,#query-form1 select").val("");
		var groupcode = $j("#groupcode").val();//loxia._ajaxFormToObj("query-form1");
		var postData1 = {};
		postData1["groupCode"] = groupcode ;
		$j("#tbl-optionalRuleDetail").jqGrid('setGridParam',{
			url:window.$j("body").attr("contextpath")+"/json/getDistributionRuleConditionDetail.json",
			postData:postData1,
			page:1
		}).trigger("reloadGrid");
		$j("#tbl-currentRuleDetail").empty();	
	     
	     
	   //初始化条件描述
	 	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getDistributionRuleCondition.do");
	 	for(var idx in result.drcList){
	 		$j("<option value='" + result.drcList[idx].groupCode + "'>"+ result.drcList[idx].groupName +"</option>").appendTo($j("#groupcode"));
	 	}
	 	
	 	//加载可选条件
 	 	$j("#tbl-optionalRuleDetail").jqGrid({
			url:$j("body").attr("contextpath") + "/getDistributionRuleConditionDetail.json",
			datatype: "json",
			colNames: ["ID",i18("CODE"),i18("GROUP_NAME"),i18("GROUP_CODE"),i18("NAME"),i18("KVALUE"),i18("TYPE"),i18("OPTION")],
			colModel: [
					   {name: "id", index: "id", hidden: true},
					   {name: "code", index: "code", hidden: true},
			           {name: "groupName", index: "groupName", width:150,resizable:true},
			           {name: "groupCode", index: "groupCode", hidden: true},
			           {name: "name", index: "name", width: 150, resizable: true},
			           {name: "kValue", index: "kValue", hidden: true},
			           {name: "intType", index: "intType",  hidden: true},
			           {name: "btnAdd1", width: 80, resizable: true}
			           ],
			caption: i18("OPTIONAL_CONDITION"),
		   	sortname: 'id',
		   	height:"auto",
		    multiselect: false,
		    rowNum: 200,
		    rownumbers:true,
		    viewrecords: true,
			sortorder: "desc",
			hidegrid:false,
			jsonReader: { repeatitems : false, id: "0" },
			gridComplete: function(){
				var btn1 = '<div><button type="button" style="width:80px;" class="add" name="btnAdd1" loxiaType="button" onclick="addDistributionRuleConditionDetail(this,event);">'+"添加"+'</button></div>';
				var ids = $j("#tbl-optionalRuleDetail").jqGrid('getDataIDs');
				for(var i=0;i < ids.length;i++){
					var idData = $j("#tbl-optionalRuleDetail").jqGrid('getRowData',ids[i]);
					var input = '<div><input loxiaType="input" id = "ruleName'+ids[i]+'" name="distributionRule.name" trim="true"/></div>';
					if(idData["groupCode"] && idData["groupCode"]=="SKUCode"){
						$j("#tbl-optionalRuleDetail").jqGrid('setRowData',ids[i],{"groupName":idData["name"]});
						$j("#tbl-optionalRuleDetail").jqGrid('setRowData',ids[i],{"name":input});
						
					}
					$j("#tbl-optionalRuleDetail").jqGrid('setRowData',ids[i],{"btnAdd1":btn1});
				}
				loxia.initContext($j(this));
			}
		});
 	 	
 	 	
 	 //加载当前规则条件明细
 	 	$j("#tbl-currentRuleDetail").jqGrid({
			colNames: ["ID",i18("CODE"),i18("GROUP_NAME"),i18("GROUP_CODE"),i18("NAME"),i18("KVALUE"),i18("TYPE"),i18("OPTION")],
			colModel: [
					   {name: "id", index: "id", hidden: true},
					   {name: "code", index: "code", hidden: true},
			           {name: "groupName", index: "groupName", width:150,resizable:true,hidden: true},
			           {name: "groupCode", index: "groupCode", hidden: true},
			           {name: "name", index: "name", width: 150, resizable: true},
			           {name: "kValue", index: "kValue", hidden: true},
			           {name: "intType", index: "intType",  hidden: true},
			           {name: "deleteAdd1", width: 80, resizable: true}
			           ],
			caption: i18("CURRENT_RULE_CONDITION_DETAIL"),
		   	height:"auto",
		    rownumbers:true,
		    viewrecords: true,
			hidegrid:false
		});
 	 	
 	 	
	 	
	});
});

function disableDistributionRule(tag,event){
	var id = $j(tag).parents("tr").attr("id");
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/deleteDistributionRuleById.json?distributionRule.id=" + id);
	if(result && result["status"] == "success"){
		jumbo.showMsg(i18("DISABLED_SUCCESS"));
	}
	var postData = loxia._ajaxFormToObj("query-form");
	$j("#tbl-ruleList").jqGrid('setGridParam',{
		url:window.$j("body").attr("contextpath")+"/json/findAllDistributionRule.json",
		postData:postData,
		page:1
	}).trigger("reloadGrid");
} 

//配货规则维护界面添加按钮
function addDistributionRuleConditionDetail(tag,event){
	var id = $j(tag).parents("tr").attr("id");
	var idData = $j("#tbl-optionalRuleDetail").jqGrid('getRowData',id);
	if((idData["groupCode"] == "SKUCode") || (idData["groupCode"] == "largeAndSmallCommodities")){
	}else{
		var rIndex = $j.inArray(idData["groupCode"], arrGroupCode); 
		if(rIndex == "-1"){
			arrGroupCode.push(idData["groupCode"]);
		}else{
			jumbo.showMsg("相同条件组不能重复添加操作,请先删除右侧条件！");
			return;
		}
	}
	var content = "<tr id=\""+id+"\"><td class='label' style='width: 26px'>"+id+"</td><td class='label' style='text-align: center;width:151px'>"+idData["name"]+"</td><td>";
	if(idData && idData["groupCode"] == "SKUCode"){
		var skuBarCode = $j("#ruleName"+idData["id"]+"").val();
		if (skuBarCode == "") {
			jumbo.showMsg(i18("SKU_BRACODE_IS_NOT_NULL"));
			return;
		}
		for(var i=0;i < t_ids.length;i++){
			if(id == t_ids[i]){
				jumbo.showMsg(i18("ID_IS_LIVE"));
				$j("#ruleName"+idData["id"]+"").val("");
				return;
			}
		}
		for(var i=0;i < arrRemark.length;i++){
			if(skuBarCode == arrRemark[i]){
				jumbo.showMsg(i18("ID_IS_LIVE"));
				$j("#ruleName"+idData["id"]+"").val("");
				return;
			}
		}
		arrRemark.push(skuBarCode+"");
		content = "<tr id=\""+id+"\"><td class='label' style='width: 26px'>"+id+"</td><td class='label' style='text-align: center;width:151px'>"+skuBarCode+"</td><td>";
	}
	content += "<button style='width: 80px;text-align: center'  class=\"delete\"  name=\"deleteAdd1\" loxiaType=\"button\" onclick=\"deleteDistributionRuleConditionDetail('"+id+","+skuBarCode+","+idData["groupCode"]+"')\">"+"删除"+"</button></td></tr>";//删除
	for(var i=0;i < t_ids.length;i++){
		if(id == t_ids[i]){
			jumbo.showMsg(i18("ID_IS_LIVE"));
			return;
		}
	}
	t_ids.push(id+"");
	$j("#tbl-currentRuleDetail").append(content);	
} 

//当前规则条件明细删除
function deleteDistributionRuleConditionDetail(obj){
	 if(obj){
		 var temp = obj.split(",");
		 var id = temp[0];
		 var skuBarCode = temp[1];
		 var groupCode = temp[2];
	 }
	 $j("#tbl-currentRuleDetail tr[id="+id+"]").remove();
	 t_ids.splice($j.inArray(id,t_ids),1);
	 if(skuBarCode != "null" && skuBarCode !="undefined"){
		 arrRemark.splice($j.inArray(skuBarCode,arrRemark),1);
	 }
	 if(!(groupCode == "SKUCode" ||  groupCode == "largeAndSmallCommodities")){
		 arrGroupCode.splice($j.inArray(groupCode,arrGroupCode),1);
	 }
}
