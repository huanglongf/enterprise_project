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

$j(document).ready(function (){
	//查询按钮功能 根据作业单号和状态查询
	$j("#btn-query1").click(function(){
		var groupcode = $j("#groupcode").val();
		var postData = {};
		postData["groupCode"] = groupcode ;
		$j("#tbl-optionalRuleDetail").jqGrid('setGridParam',{
			url:window.$j("body").attr("contextpath")+"/json/getDistributionRuleConditionDetail.json",
			postData:postData,
			page:1
		}).trigger("reloadGrid");
	});
	
	//重置
	$j("#reset1").click(function(){
		$j("#query-form1 input,#query-form1 select").val("");
	});
});

