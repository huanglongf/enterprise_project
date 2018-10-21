$j.extend(loxia.regional['zh-CN'],{
	INPUT_POSITIVE_INTEGER:"请输入大于零的整数",
	INPUT_CITY_NAME:"请输入城市名称",
	NOT_INPUT_SPECIFY_WORD:"请不要输入指定字符:'市'",
	INPUT_CHINESE:"请输入中文"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

/**
 * 是否为正整数
 * @param s
 * @returns
 */
function isPositiveNum(s){  
    var re = /^[0-9]*[1-9][0-9]*$/ ;  
    return re.test(s);
} 
/**
 * 判断是否为空
 * @param data
 * @returns
 */
function isNull(data){ 
	return ("" == data || data == undefined || data == null || data.length == 0) ? true : false; 
}

/**
 * 是否为中文
 */
function isChina(str) {
	var reg = /^[\u4E00-\u9FA5]+$/; 
	if(reg.test(str)){ 
		return true; 
	}else {
		return false;
	}
}	
	
$j(document).ready(function (){
//	var baseUrl = $j("body").attr("contextpath");
	loxia.init({debug: true, region: 'zh-CN'});
	/*$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});*/
	
	// 初始优先发货省份
	var priorityCities = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findPriorityList.do");
	if (null != priorityCities && priorityCities.length > 0) {
		var arrStr3 = new Array(); 
		for(var idx in priorityCities){
			arrStr3.push(priorityCities[idx].priorityName);
		}
		if (null != arrStr3 && arrStr3.length > 0) {
			var tempStr = arrStr3.join(",");
			if (!isNull(tempStr)) {
				//$j("<optgroup label='Group 1'>").appendTo($j("#priorityCity"));
				$j("<option value='" + tempStr + "'>所有优先发货省份</option>").appendTo($j("#priorityId"));
				$j("<option value='" + tempStr + ',opposite' + "'>非优先发货省份</option>").appendTo($j("#priorityId"));
				//$j("</optgroup>").appendTo($j("#priorityCity"));
				//$j("<optgroup label='Group 2'>").appendTo($j("#priorityCity"));
				for(var idx in priorityCities){
					$j("<option value='" + priorityCities[idx].priorityName + "'>"+ priorityCities[idx].priorityName +"</option>").appendTo($j("#priorityId"));
				}
				//$j("</optgroup>").appendTo($j("#priorityCity"));
			}
			
		}
		
	}
	$j("#priorityId").multipleSelect('refresh');
	$j("#tbl-hight_province_Config").jqGrid({
		url:$j("body").attr("contextpath")+"/json/queryHightProvinceConfig.json",
		datatype: "json",
		colNames: ["ID","优先发货省份"],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},	
		           {name: "priorityName", index: "priorityName", hidden:false, sortable:false, align:'center'}
		          ],
		caption: '优先发货省份配置',
		//sortname: 'sta.create_time',
	   	//sortorder: "desc",
	   	multiselect: true,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pagerProvince",
	   	height:jumbo.getHeight(),
		viewrecords: true,
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			$j("#tbl-hight_province_Config").closest(".ui-jqgrid-bdiv").css({ 'overflow-x': 'hidden' });
		}
	}).setGridWidth(800);
	
	
	$j("#addHightProvince").click(function() {
		var name = $j("#priorityName").val();
		if (isNull(name)) {
			jumbo.showMsg("省份不能为空");
			return;
		}
		if (!isChina(name)) {
			jumbo.showMsg(i18("省份只能为中文"));
			return;
		}
		
		var postData = {};
		postData["hightProvinceConfig.priorityName"] = name;
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/saveHightProvinceConfig.json", postData);
		if (rs && rs.msg) {
			jumbo.showMsg("保存成功");
			var url = $j("body").attr("contextpath")+"/json/queryHightProvinceConfig.json";
			jQuery("#tbl-hight_province_Config").jqGrid('setGridParam',{url:url}).trigger("reloadGrid",[{page:1}]);
		}else {
			jumbo.showMsg("系统异常,请联系管理员");
		}
	});
	
	
	$j("#removeHightProvince").click(function(){
		var ids = $j("#tbl-hight_province_Config").jqGrid('getGridParam','selarrrow');
		if (null != ids && ids.length > 0) {
			if (confirm("确定删除？")) {
				var ids = ids.join(',');
				var rs = loxia.syncXhr($j("body").attr("contextpath") + "/json/deleteHightProvinceConfig.do",{"ids":ids});
				if(rs && rs.msg){
					jumbo.showMsg("删除成功");
					$j("#tbl-hight_province_Config").jqGrid('setGridParam',{url:$j("body").attr("contextpath")+"/json/queryHightProvinceConfig.json"}).trigger("reloadGrid",[{page:1}]);
				}else{
					jumbo.showMsg("系统异常,请联系管理员!~");
				}
			}
		}else {
			jumbo.showMsg("请勾选需要操作的数据,谢谢!~");
		}
	});
	
});
