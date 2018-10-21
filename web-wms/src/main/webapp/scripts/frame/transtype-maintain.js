var $j = jQuery.noConflict();

$j.extend(loxia.regional['zh-CN'], {
	CREATE_SUCCESS : "创建作业类型成功！",
	ACTIVITYTYPE_ISUSED : "您选择的作业类型编码已经在使用了，请重新输入新的作业类型编码！",
	CREATE_FAILED : "创建作业类型失败！",
	MODIFY_SUCCESS : "修改作业类型成功！",
	MODIFY_FAILED : "修改作业类型失败！",
	
	TYPE_CODE : "作业类型编码",
	TYPE_NAME : "作业类型名称",
	TYPE_DESCRIBE : "作业描述大于255个字符",
	DIRECTION_SELECT : "请选择作业方向",
	PLAN_SELECT : "请选择计划和执行控制",
	ISSYNCHTAOBAO:"是否需要同步淘宝",
	
	CODE : "作业类型编码",
	NAME : "作业类型名称",
	DESCRIPTION : "描述",
	INTDIRECTION : "作业方向",
	INTCONTROL : "计划与执行控制",
	ISINCOST : "参与成本计算",
	ISAVAILABLE : "使用状态",
	OPER : "操作",
	OPER_LIST : "系统预定义作业类型列表",
	DEFINED_LIST : "自定义作业类型列表"
		});
function i18(msg, args){
	return loxia.getLocaleMsg(msg, args);
}
	
function checkDescribe(value,obj){
	if (value.length > 0 && value.length > 255) {
		return loxia.getLocaleMsg("TYPE_DESCRIBE");
	}
	return loxia.SUCCESS;
}

function modifytt(btn) {
	var id = $j(btn).parents("tr").attr("id");//$j(btn).attr("value");
	document.getElementById("editForm").reset();
	var data = $j("#tbl-transtypelist").jqGrid("getRowData", id);
	$j("#id").val(data.id);
	$j("#code").val(data.code);
	$j("#name").val(data.name);
	$j("#description").val(data.description);
	$j("#isInCost").attr("checked", data.isInCost == 'true');
	$j("#isSynchTaobao").attr("checked", data.isSynchTaobao == 'true');
	$j("#intDirection option[value='" + data.intDirection + "']").attr(
			"selected", true);
	if(data.intDirection==2){
		$j("#isInCost").parent("td").hide();
	}else{
		$j("#isInCost").parent("td").show();
	}
	$j("#intControl option[value='" + data.intControl + "']").attr("selected",
			true);
	$j("#isAvailable option[value='" + data.isAvailable + "']").attr(
			"selected", true);
	$j("#editdiv").removeClass("hidden");
	$j("#newdiv").addClass("hidden");
}

$j(document).ready(function() {
	var whDirectionMode = loxia.syncXhr($j("body").attr("contextpath")+ "/formateroptions.json", {categoryCode : "whDirectionMode"});
	var whTransactionControl = loxia.syncXhr($j("body").attr("contextpath")+ "/formateroptions.json", {categoryCode : "whTransactionControl"});
	var boollist = loxia.syncXhr($j("body").attr("contextpath")+ "/formateroptions.json", {categoryCode : "status"});
	var trueOrFalse = loxia.syncXhr($j("body").attr("contextpath")+ "/formateroptions.json", {categoryCode : "trueOrFalse"});
	$j("#tbl-systranstypelist").jqGrid({
		url : $j("body").attr("contextpath")	+ "/findtranstypelist.json?transactionType.isSystem=true",
		datatype : "json",
		//colNames : [ "ID", "作业类型编码", "作业类型名称","描述", "作业方向", "计划与执行控制","参与成本计算", "使用状态", "操作" ],
		colNames : [ "ID", i18("CODE"), i18("NAME"),i18("DESCRIPTION"), i18("INTDIRECTION"), i18("INTCONTROL"),i18("ISINCOST"), i18("ISAVAILABLE"), 
		             i18("ISSYNCHTAOBAO"),i18("OPER") ],
		colModel : [ {name : "id",	index : "id",	hidden : true}, 
			{name : "code",	width : 120, resizable : true,	 sortable : false}, 
			{name : "name", width : 120, resizable : true, sortable : false}, 
			{name : "description",width : 180,resizable : true,sortable : false},
			{name : "intDirection",width : 60,resizable : true,formatter : 'select',editoptions : whDirectionMode,sortable : false},
			{name : "intControl",index : "control",width : 160,resizable : true,formatter : 'select',editoptions : whTransactionControl,sortable : false},
			{name : "isInCost",width : 120,resizable : true,formatter : 'select',editoptions : trueOrFalse,sortable : false}, 
			{name : "isAvailable",width : 120,resizable : true,formatter : 'select',editoptions : boollist,sortable : false}, 
			{name : "isSynchTaobao",	width : 120,	resizable : true,	formatter : 'select',	editoptions : trueOrFalse,	sortable : false},
			{width : 120} ],
			caption : i18("OPER_LIST"),//系统预定义作业类型列表
			sortname : 'name',
			sortorder : "asc",
										viewrecords: true,
   										rownumbers:true,
										sortname : 't.isAvailable desc, t.code desc',
			gridComplete : function() {
				loxia.initContext($j(this));
			},
			jsonReader : {
				repeatitems : false,
				id : "0"
			}
		});

	$j("#tbl-transtypelist").jqGrid({
		url : $j("body").attr("contextpath")+ "/findtranstypelist.json?transactionType.isSystem=false",
		datatype : "json",
			//colNames : [ "ID", "作业类型编码", "作业类型名称","描述", "作业方向", "计划与执行控制","参与成本计算", "使用状态", "操作" ],
			colNames : [ "ID", i18("CODE"), i18("NAME"),i18("DESCRIPTION"), i18("INTDIRECTION"), i18("INTCONTROL"),i18("ISINCOST"), i18("ISAVAILABLE"),
			             i18("ISSYNCHTAOBAO"),i18("OPER") ],
			colModel : [{name : "id",	index : "id",	hidden : true}, 
			{name : "code",	width : 120, resizable : true,	 sortable : false}, 
			{name : "name", width : 120, resizable : true, sortable : false}, 
			{name : "description",width : 180,resizable : true,sortable : false},
			{name : "intDirection",width : 60,resizable : true,formatter : 'select',editoptions : whDirectionMode,sortable : false},
			{name : "intControl",index : "control",width : 160,resizable : true,formatter : 'select',editoptions : whTransactionControl,sortable : false},
			{name : "isInCost",width : 120,resizable : true,formatter : 'select',editoptions : trueOrFalse,sortable : false}, 
			{name : "isAvailable",width : 120,resizable : true,formatter : 'select',editoptions : boollist,sortable : false}, 
			{name : "isSynchTaobao",	width : 120,	resizable : true,	formatter : 'select',	editoptions : trueOrFalse,	sortable : false},
			{name : "id",index : "id",sortable : false,width : 120,formatter : "buttonFmatter",formatoptions : {
												"buttons" : {label : "修改",onclick : "modifytt(this);"}}} ],
			caption : i18("DEFINED_LIST"),//自定义作业类型列表
			sortname : 'name',
			sortorder : "asc",
			viewrecords: true,
   			rownumbers:true,
			sortname : 't.isAvailable desc, t.code desc',
			gridComplete : function() {
				loxia.initContext($j(this));
			},
			jsonReader : {
				repeatitems : false,
				id : "0"
			},
			onSelectRow : function(id) {

			}
		});
		$j("#addTransactionType").click(function() {
			if (checkForm("addForm")) {return;}
			loxia.lockPage();
			var addForm = loxia._ajaxFormToObj("addForm");
			loxia.asyncXhrPost($j("body").attr("contextpath")+ "/addorModifytransactiontype.json",addForm,
					{success : function(data) {
							loxia.unlockPage();
							if (data.rs == 'true') {
								document.getElementById("addForm").reset();
								$j("#tbl-transtypelist").trigger("reloadGrid",[ {page : 1} ]);
								jumbo.showMsg(loxia.getLocaleMsg("CREATE_SUCCESS"));//创建作业类型成功！
							} else {
								jumbo.showMsg(loxia.getLocaleMsg("ACTIVITYTYPE_ISUSED"));//您选择的作业类型编码已经在使用了，请重新输入新的作业类型编码！
								}
					},
					error : function() {
							jumbo.showMsg(loxia.getLocaleMsg("CREATE_FAILED")); //创建作业类型失败！
							}
					});
		});
		$j("#modifyTransactionType").click(
				function() {
					if (checkForm("editForm")) {return;}
					loxia.lockPage();
					var editForm = loxia._ajaxFormToObj("editForm");
					loxia.asyncXhrPost($j("body").attr("contextpath")+ "/addorModifytransactiontype.json",editForm,
						{success : function(data) {
								loxia.unlockPage();
								if (data.rs == 'true') {
																	// document.getElementById("editForm").reset();
								$j("#tbl-transtypelist").trigger("reloadGrid",[ {page : 1} ]);
								jumbo.showMsg(loxia.getLocaleMsg("MODIFY_SUCCESS"));//修改作业类型成功！
						} else {
								jumbo.showMsg(loxia.getLocaleMsg("ACTIVITYTYPE_ISUSED")); //您选择的作业类型编码已经在使用了，请重新输入新的作业类型编码！
								return;
								}
						},
						error : function() {
								jumbo.showMsg(loxia.getLocaleMsg("MODIFY_FAILED")); //修改作业类型失败！
								}
						});
		});

					$j("#btn-edit-cancel").click(function() {
						$j("#editdiv").addClass("hidden");
						$j("#newdiv").removeClass("hidden");
						document.getElementById("addForm").reset();
					});
					jumbo.bindTableExportBtn("tbl-systranstypelist",{"intDirection":"whDirectionMode","intControl":"whTransactionControl",
						"isAvailable":"status","isInCost":"trueOrFalse"},
						$j("body").attr("contextpath")+ "/findtranstypelist.json",
						{"transactionType.isSystem":true});
					jumbo.bindTableExportBtn("tbl-transtypelist",{"intDirection":"whDirectionMode","intControl":"whTransactionControl",
						"isAvailable":"status","isInCost":"trueOrFalse"},
						$j("body").attr("contextpath")+ "/findtranstypelist.json",
						{"transactionType.isSystem":false});
					$j("#addForm #intDirection").bind("change",function(){toggleDirection($j("#addForm"));});
					$j("#editForm #intDirection").bind("change",function(){toggleDirection($j("#editForm"));});
				});
function toggleDirection(form){
	if($j("#intDirection",form).val()==2){
		$j("#isInCost",form).parent("td").hide();
	}else{
		$j("#isInCost",form).parent("td").show();
	}
}
function checkForm(f) {
	var code = $j("#" + f + " #code").val();
	var name = $j("#" + f + " #name").val();
	var description = $j("#" + f + " #description").val();
	var intDirection = $j("#" + f + " #intDirection").val();
	var intControl = $j("#" + f + " #intControl").val();
	var errors = loxia.validateForm(f);

	if (errors.length > 0) {
		jumbo.showMsg(errors);
		return true;
	}
	
	if (intDirection == '') {
		jumbo.showMsg(loxia.getLocaleMsg("DIRECTION_SELECT"));//请选择作业方向
		return true;
	}
	if (intControl == '') {
		jumbo.showMsg(loxia.getLocaleMsg("PLAN_SELECT"));//请选择计划和执行控制
		return true;
	}

	return false;

}