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
	
	// 初始优先发货城市
	var priorityCities = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findprioritycitylist.do");
	if (null != priorityCities && priorityCities.length > 0) {
		var arrStr = new Array(); 
		for(var idx in priorityCities){
			arrStr.push(priorityCities[idx].cityName);
		}
		if (null != arrStr && arrStr.length > 0) {
			var tempStr = arrStr.join(",");
			if (!isNull(tempStr)) {
				//$j("<optgroup label='Group 1'>").appendTo($j("#priorityCity"));
				$j("<option value='" + tempStr + "'>所有优先发货城市</option>").appendTo($j("#priorityCity"));
				$j("<option value='" + tempStr + ',opposite' + "'>非优先发货城市</option>").appendTo($j("#priorityCity"));
				//$j("</optgroup>").appendTo($j("#priorityCity"));
				//$j("<optgroup label='Group 2'>").appendTo($j("#priorityCity"));
				for(var idx in priorityCities){
					$j("<option value='" + priorityCities[idx].cityName + "'>"+ priorityCities[idx].cityName +"</option>").appendTo($j("#priorityCity"));
				}
				//$j("</optgroup>").appendTo($j("#priorityCity"));
			}
			
		}
		
	}
	
	$j("#tbl-inbound-purchase").jqGrid({
		url:$j("body").attr("contextpath")+"/json/queryEntityParams.json",
		datatype: "json",
		colNames: ["ID","优先发货城市"],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},	
		           {name: "cityName", index: "cityName", hidden:false, sortable:false, align:'center'}
		          ],
		caption: '优先发货城市配置',
		//sortname: 'sta.create_time',
	   	//sortorder: "desc",
	   	multiselect: true,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pagerCity",
	   	height:jumbo.getHeight(),
		viewrecords: true,
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			$j("#tbl-inbound-purchase").closest(".ui-jqgrid-bdiv").css({ 'overflow-x': 'hidden' });
		}
	}).setGridWidth(800);
	
	
	$j("#add").click(function() {
		var name = $j("#cityName").val();
		if (isNull(name)) {
			jumbo.showMsg(i18("INPUT_CITY_NAME"));
			return;
		}
		if (!isChina(name)) {
			jumbo.showMsg(i18("INPUT_CHINESE"));
			return;
		}
		/*var tempStr = name.substring(name.length - 1);
		if ("市" == tempStr) {
			jumbo.showMsg(i18("NOT_INPUT_SPECIFY_WORD"));
			return;
		}*/
		var postData = {};
		postData["psccCommand.cityName"] = name;
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/saveentity.json", postData);
		if (rs && rs.msg) {
			jumbo.showMsg("保存成功");
			var url = $j("body").attr("contextpath")+"/json/queryEntityParams.json";
			jQuery("#tbl-inbound-purchase").jqGrid('setGridParam',{url:url}).trigger("reloadGrid",[{page:1}]);
		}else {
			jumbo.showMsg("系统异常,请联系管理员");
		}
	});
	
	
	$j("#remove").click(function(){
		var ids = $j("#tbl-inbound-purchase").jqGrid('getGridParam','selarrrow');
		if (null != ids && ids.length > 0) {
			if (confirm("确定删除？")) {
				var ids = ids.join(',');
				var rs = loxia.syncXhr($j("body").attr("contextpath") + "/json/delentity.do",{"ids":ids});
				if(rs && rs.msg){
					jumbo.showMsg("删除成功");
					$j("#tbl-inbound-purchase").jqGrid('setGridParam',{url:$j("body").attr("contextpath")+"/json/queryEntityParams.json"}).trigger("reloadGrid",[{page:1}]);
				}else{
					jumbo.showMsg("系统异常,请联系管理员!~");
				}
			}
		}else {
			jumbo.showMsg("请勾选需要操作的数据,谢谢!~");
		}
	});
	
});
