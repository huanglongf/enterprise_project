$j.extend(loxia.regional['zh-CN'],{
	
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}



$j(document).ready(function (){
	//初始化物流商信息
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransportator.do");
	$j("<option value=''>--请选择--</option>").appendTo($j("#selLpcode"));
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selLpcode"));
	}
	$j("<option value=''>--请选择--</option>").appendTo($j("#selLpcodeAdd"));
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selLpcodeAdd"));
	}
	$j("<option value=''>--请选择--</option>").appendTo($j("#selLpcodeUpdate"));
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selLpcodeUpdate"));
	}
	//初始化分钟小时
	$j("<option value=''>--请选择--</option>").appendTo($j("#hour"));
	$j("<option value=''>--请选择--</option>").appendTo($j("#hourUpdate"));
	for(var i = 0;i<=23;i++){
		$j("<option value='"+i+"'>"+i+"点</option>").appendTo($j("#hour"));
		$j("<option value='"+i+"'>"+i+"点</option>").appendTo($j("#hourUpdate"));
	}
	$j("<option value=''>--请选择--</option>").appendTo($j("#minute"));
	$j("<option value=''>--请选择--</option>").appendTo($j("#minuteUpdate"));
	for(var j = 0;j<=59;j++){
		if(j<=9){
			$j("<option value='0"+j+"'>0"+j+"分</option>").appendTo($j("#minute"));
			$j("<option value='0"+j+"'>0"+j+"分</option>").appendTo($j("#minuteUpdate"));
		}else{
			$j("<option value='"+j+"'>"+j+"分</option>").appendTo($j("#minute"));	
			$j("<option value='"+j+"'>"+j+"分</option>").appendTo($j("#minuteUpdate"));	
		}
	}
	//初始化省
	var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/findRadarAreaProvince.json"));
	for(var idx in rs){
		$j("<option value='" + rs[idx].province + "'>"+ rs[idx].province +"</option>").appendTo($j("#province"));
		$j("<option value='" + rs[idx].province + "'>"+ rs[idx].province +"</option>").appendTo($j("#provinceAdd"));
		$j("<option value='" + rs[idx].province + "'>"+ rs[idx].province +"</option>").appendTo($j("#provinceUpdate"));
	}
	//禁用省市控件
	$j("#cityAdd").attr("disabled","disabled");
	$j("#provinceAdd").attr("disabled","disabled");  
	$j("#cityUpdate").attr("disabled","disabled");
	$j("#provinceUpdate").attr("disabled","disabled");  
	$j("#show_rr_dialog").dialog({title: "快递时效设置", modal:true, autoOpen: false, width: 800, height: 230});
	$j("#show_rr_dialog").dialog("close");
	var postData = loxia._ajaxFormToObj("query-form");
	$j("#tbl-radarTimeRuleList").jqGrid({
		url:$j("body").attr("contextpath")+"/findRadarTimeRule.json",
		postData:postData,
		datatype: "json",
		colNames: ["ID","物流服务商","物流服务类型","快递时效类型","发件仓库","省","市","标准时效(天)","截止揽件时间","创建人","操作"],
		colModel: [
				   {name: "id", index: "id", hidden: true},
		           {name: "lpCode", index: "lpCode", width:100,resizable:true},
		           {name: "wlServiceString", index: "wlServiceString", width:100,resizable:true},    
		           {name: "dateTimeTypeString", index: "dateTimeTypeString", width: 100, resizable: true},
		           {name: "phyName", index: "phyName", width: 160, resizable: true},
		           {name: "province", index: "province", width: 100, resizable: true},
		           {name: "city", index: "city", width: 80, resizable: true},
		           {name: "dateTime", index: "dateTime", width: 80, resizable: true},
		           {name: "overTakingTime", index: "overTakingTime", width: 100, resizable: true},
		           {name: "userName", index: "userName", width: 60, resizable: true},
		           {name: "operator", width: 80, resizable: true,formatter:"buttonFmatter", formatoptions:{"buttons":{label:"编辑", onclick:"updaterr(this,event);"}}}
		           ],
		caption: "快递时效信息一览表",
		pager:"#pager_query",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: true,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc",
		hidegrid:false,
		jsonReader: { repeatitems : false, id: "0" },
		loadComplete:function(){
			loxia.initContext($j("#tbl-radarTimeRuleList"));
		}
	});	
	
	//控制添加快递时效类型
	//普通才能选择省市
	$j("#dateTimeTypeAdd").change(function() {
		var dateTimeType = $j("#dateTimeTypeAdd").val();
		if(dateTimeType != "1"){
			$j("#cityAdd").attr("disabled","disabled");
			$j("#provinceAdd").attr("disabled","disabled");
//			$j("#dateTimeTypeAdd").val("");
			$j("#dateTime").attr("disabled","disabled");
			$j("#cityAdd").val("");
			$j("#provinceAdd").val("");
			$j("#cityAdd").empty();
			$j("<option value=''>"+'--请选择省--'+"</option>").appendTo($j("#cityAdd"));
			if(dateTimeType == ""){
				$j("#dateTime").val("");
			}
			if(dateTimeType == "3" || dateTimeType == "5"){
				$j("#dateTime").val("0");
			}
			if(dateTimeType == "6" ){
				$j("#dateTime").val("1");
			}
		}
		if(dateTimeType == "1"){
			$j("#cityAdd").removeAttr("disabled");
			$j("#provinceAdd").removeAttr("disabled");			
			$j("#dateTime").removeAttr("disabled");
			$j("#dateTime").val("");
		}
	});
	
	$j("#dateTimeTypeUpdate").change(function() {
		var dateTimeType = $j("#dateTimeTypeUpdate").val();
		if(dateTimeType != "1"){
			$j("#cityUpdate").attr("disabled","disabled");
			$j("#provinceUpdate").attr("disabled","disabled");
//			$j("#dateTimeTypeAdd").val("");
			$j("#dateTimeUpdate").attr("disabled","disabled");
			$j("#cityUpdate").val("");
			$j("#provinceUpdate").val("");
			$j("#cityUpdate").empty();
			$j("<option value=''>"+'--请选择省--'+"</option>").appendTo($j("#cityUpdate"));
			if(dateTimeType == ""){
				$j("#dateTimeUpdate").val("");
			}
			if(dateTimeType == "3" || dateTimeType == "5"){
				$j("#dateTimeUpdate").val("0");
			}
			if(dateTimeType == "6" ){
				$j("#dateTimeUpdate").val("1");
			}
		}
		if(dateTimeType == "1"){
			$j("#cityUpdate").removeAttr("disabled");
			$j("#provinceUpdate").removeAttr("disabled");			
			$j("#dateTimeUpdate").removeAttr("disabled");
			$j("#dateTimeUpdate").val("");
		}
	});

	//选择物流商,获取对应发件仓库(物理仓)
	$j("#selLpcode").change(function() {
		var selLpcode = $j("#selLpcode").val();
		var postData = {};
//		alert(selLpcode);
		postData['lpCode'] = selLpcode;
		if(selLpcode == ''){
			$j("#fjWh").empty();
			$j("<option value=''>"+'--请选择物流商--'+"</option>").appendTo($j("#fjWh"));
		}else{
			var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/findPhyWarehouseByLpcode.json"),postData);
			$j("#fjWh").empty();
			$j("<option value=''>"+'--请选择--'+"</option>").appendTo($j("#fjWh"));
			for(var idx in rs){
				$j("<option value='" + rs[idx].phyid + "'>"+ rs[idx].phyName +"</option>").appendTo($j("#fjWh"));
			}			
		}
	});
	
	$j("#selLpcodeAdd").change(function() {
		var selLpcode = $j("#selLpcodeAdd").val();
		var postData = {};
//		alert(selLpcode);
		postData['lpCode'] = selLpcode;
		if(selLpcode == ''){
			$j("#fjWhAdd").empty();
			$j("<option value=''>"+'--请选择物流商--'+"</option>").appendTo($j("#fjWhAdd"));
		}else{
			var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/findPhyWarehouseByLpcode.json"),postData);
			$j("#fjWhAdd").empty();
			for(var idx in rs){
				$j("<option value='" + rs[idx].phyid + "'>"+ rs[idx].phyName +"</option>").appendTo($j("#fjWhAdd"));
			}			
		}
	});
	
	$j("#selLpcodeUpdate").change(function() {
		var selLpcode = $j("#selLpcodeUpdate").val();
		var postData = {};
//		alert(selLpcode);
		postData['lpCode'] = selLpcode;
		if(selLpcode == ''){
			$j("#fjWhUpdate").empty();
			$j("<option value=''>"+'--请选择物流商--'+"</option>").appendTo($j("#fjWhUpdate"));
		}else{
			var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/findPhyWarehouseByLpcode.json"),postData);
			$j("#fjWhUpdate").empty();
			for(var idx in rs){
				$j("<option value='" + rs[idx].phyid + "'>"+ rs[idx].phyName +"</option>").appendTo($j("#fjWhUpdate"));
			}			
		}
	});
	
	//通过省查找市
	$j("#province").change(function() {
		var province = $j("#province").val();
		var postData = {};
		postData['province'] = province;
		if(province == ''){
			$j("#city").empty();
			$j("<option value=''>"+'--请选择省--'+"</option>").appendTo($j("#city"));
		}else{
			var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/findRadarAreaCity.json"),postData);
			$j("#city").empty();
			$j("<option value=''>"+'--请选择--'+"</option>").appendTo($j("#city"));
			for(var idx in rs){
				$j("<option value='" + rs[idx].city + "'>"+ rs[idx].city +"</option>").appendTo($j("#city"));
			}
	        $j("#city option[value='null']").remove();
		}
	});
	
	$j("#provinceAdd").change(function() {
		var province = $j("#provinceAdd").val();
		var postData = {};
		postData['province'] = province;
		if(province == ''){
			$j("#cityAdd").empty();
			$j("<option value=''>"+'--请选择省--'+"</option>").appendTo($j("#cityAdd"));
		}else{
			var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/findRadarAreaCity.json"),postData);
			$j("#cityAdd").empty();
			for(var idx in rs){
				$j("<option value='" + rs[idx].city + "'>"+ rs[idx].city +"</option>").appendTo($j("#cityAdd"));
			}
	        $j("#cityAdd option[value='null']").remove();
		}
	});
	
	$j("#provinceUpdate").change(function() {
		var province = $j("#provinceUpdate").val();
		var postData = {};
		postData['province'] = province;
		if(province == ''){
			$j("#cityUpdate").empty();
			$j("<option value=''>"+'--请选择省--'+"</option>").appendTo($j("#cityUpdate"));
		}else{
			var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/findRadarAreaCity.json"),postData);
			$j("#cityUpdate").empty();
			for(var idx in rs){
				$j("<option value='" + rs[idx].city + "'>"+ rs[idx].city +"</option>").appendTo($j("#cityUpdate"));
			}
			$j("#cityUpdate option[value='null']").remove();
		}
	});
	
	//新建
	$j("#save").click(function(){
		var selLpcodeAdd = $j("#selLpcodeAdd").val();
		var wlService = $j("#wlServiceAdd").val();
		var dateTimeTypeAdd = $j("#dateTimeTypeAdd").val();
		var fjWhAdd = $j("#fjWhAdd").val();
		var provinceAdd = $j("#provinceAdd").val();
		var cityAdd = $j("#cityAdd").val();
		var dateTime = $j("#dateTime").val().trim();
		var hour = $j("#hour").val();
		var minute = $j("#minute").val();
		var size = $j("select[id='cityAdd'] option").size(); 
		var fjsize = $j("select[id='fjWhAdd'] option").size(); 
		if(selLpcodeAdd==""){
			jumbo.showMsg("请选择物流商简称");
			return;
		}
		if(wlService==""){
			jumbo.showMsg("请选择物流服务类型");
			return;
		}
		if(dateTimeTypeAdd==""){
			jumbo.showMsg("请选择快递时效类型");
			return;
		}
		if(fjWhAdd==""){
			jumbo.showMsg("请选择发件仓库");
			return;
		}
		if(dateTimeTypeAdd=="1"){
			if(provinceAdd == ""){
				jumbo.showMsg("请选择省");
				return;				
			}
		}
		if(dateTime == ""){
			jumbo.showMsg("标准时效(天)不能为空");
			return;		
		}
		if(dateTime == "1"){
			if(dateTime == ""){
				jumbo.showMsg("标准时效(天)不能为空");
				return;		
			}
		}
		if(hour == ""){
			jumbo.showMsg("请选择截止揽件时间（小时）");
			return;		
		}
		if(minute == ""){
			jumbo.showMsg("请选择截止揽件时间（分钟）");
			return;		
		}
		if(fjsize == 0){
			jumbo.showMsg("此物流商无对应发货仓");
			return;	
		}
		var ex = /^\d+$/;
		if(!ex.test(dateTime)){
			jumbo.showMsg("标准时效(天)格式不正确");
			return;
		}
		var postData = {};
		postData['rc.lpCode'] = selLpcodeAdd;
		postData['rc.wlService'] = wlService;
		postData['rc.dateTimeType'] = dateTimeTypeAdd;
		postData['rc.phyid'] = fjWhAdd;
		if(dateTimeTypeAdd=="1"){	
			postData['rc.province'] = provinceAdd;
			if(size > 0){
				postData['rc.city'] = cityAdd;				
			}
		}
		postData['rc.dateTime'] = dateTime;
		postData['rc.hour'] = hour;
		postData['rc.minute'] = minute;
		var result =  loxia.syncXhrPost($j("body").attr("contextpath") + "/checkRadarTimeRule.json",postData);
		if(result &&  result.msg == "success"){
			if(result.result == 'null'){
				var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/saveRadarTimeRule.json",postData);
				if(rs &&  rs.msg == "success"){
					jumbo.showMsg("创建成功！");
					initRadarTimeRule();
					var postData = loxia._ajaxFormToObj("query-form");
					$j("#tbl-radarTimeRuleList").jqGrid('setGridParam',{
						url:window.$j("body").attr("contextpath")+"/findRadarTimeRule.json",
						postData:postData,
						page:1
					}).trigger("reloadGrid");
				}else{
					jumbo.showMsg("创建失败！");
				}				
			}else{
				jumbo.showMsg("该条记录已经存在，不能重复创建！");
			}
		}else{
			jumbo.showMsg("系统异常！");		
		}
	});
	
	//修改
	$j("#update").click(function(){
		var id = $j("#rrid").val();
		var selLpcodeAdd = $j("#selLpcodeUpdate").val();
		var wlService = $j("#wlServiceUpdate").val();
		var dateTimeTypeAdd = $j("#dateTimeTypeUpdate").val();
		var fjWhAdd = $j("#fjWhUpdate").val();
		var provinceAdd = $j("#provinceUpdate").val();
		var cityAdd = $j("#cityUpdate").val();
		var dateTime = $j("#dateTimeUpdate").val().trim();
		var hour = $j("#hourUpdate").val();
		var minute = $j("#minuteUpdate").val();
		var size = $j("select[id='cityUpdate'] option").size(); 
		var fjsize = $j("select[id='fjWhUpdate'] option").size(); 
		if(selLpcodeAdd==""){
			jumbo.showMsg("请选择物流商简称");
			return;
		}
		if(wlService==""){
			jumbo.showMsg("请选择物流服务类型");
			return;
		}
		if(dateTimeTypeAdd==""){
			jumbo.showMsg("请选择快递时效类型");
			return;
		}
		if(fjWhAdd==""){
			jumbo.showMsg("请选择发件仓库");
			return;
		}
		if(dateTimeTypeAdd=="1"){
			if(provinceAdd == ""){
				jumbo.showMsg("请选择省");
				return;				
			}
		}
		if(dateTime == ""){
			jumbo.showMsg("标准时效(天)不能为空");
			return;		
		}
		if(dateTime == "1"){
			if(dateTime == ""){
				jumbo.showMsg("标准时效(天)不能为空");
				return;		
			}
		}
		if(hour == ""){
			jumbo.showMsg("请选择截止揽件时间（小时）");
			return;		
		}
		if(minute == ""){
			jumbo.showMsg("请选择截止揽件时间（分钟）");
			return;		
		}
		if(fjsize == 0){
			jumbo.showMsg("此物流商无对应发货仓");
			return;
		}
		var ex = /^\d+$/;
		if(!ex.test(dateTime)){
			jumbo.showMsg("标准时效(天)格式不正确");
			return;
		}
		var postData = {};
		postData['rc.id'] = id;	
		postData['rc.lpCode'] = selLpcodeAdd;
		postData['rc.wlService'] = wlService;
		postData['rc.dateTimeType'] = dateTimeTypeAdd;
		postData['rc.phyid'] = fjWhAdd;
		if(dateTimeTypeAdd=="1"){	
			postData['rc.province'] = provinceAdd;
			if(size > 0){
				postData['rc.city'] = cityAdd;				
			}
		}
		postData['rc.dateTime'] = dateTime;
		postData['rc.hour'] = hour;
		postData['rc.minute'] = minute;
		if(confirm("确定修改快递时效信息？")){
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/updateRadarTimeRule.json",postData);
			if(rs &&  rs.msg == "success"){
				jumbo.showMsg("修改成功！");
				initRadarTimeRule();
				var postData = loxia._ajaxFormToObj("query-form");
				$j("#tbl-radarTimeRuleList").jqGrid('setGridParam',{
					url:window.$j("body").attr("contextpath")+"/findRadarTimeRule.json",
					postData:postData,
					page:1
				}).trigger("reloadGrid");
				$j("#show_rr_dialog").dialog("close");
			}else{
				jumbo.showMsg("修改失败！");
			}		
		}
	});
	
	//查询
	$j("#btn-query").click(function() {
		var postData = loxia._ajaxFormToObj("query-form");
		$j("#tbl-radarTimeRuleList").jqGrid('setGridParam',{
			url:window.$j("body").attr("contextpath")+"/findRadarTimeRule.json",
			postData:postData,
			page:1
		}).trigger("reloadGrid");
	});
	
	//重置
	$j("#reset").click(function() {
		$j("#selLpcode").val("");
		$j("#wlService").val("");
		$j("#dateTimeType").val("");
		$j("#fjWh").empty();
		$j("<option value=''>"+'--请选择物流商--'+"</option>").appendTo($j("#fjWh"));
		$j("#province").val("");
		$j("#city").empty();
		$j("<option value=''>"+'--请选择省--'+"</option>").appendTo($j("#city"));
	});
	
	//删除
	$j("#deleteButton").click(function() {
		var postData = {};
		var ids = $j("#tbl-radarTimeRuleList").jqGrid('getGridParam','selarrrow');
		if(ids.length == 0){
			jumbo.showMsg("请选择需要删除的快递时效信息");
			return;
		}
		if(confirm("确定删除选中的快递时效信息？")){
			for(var i in ids){
				postData['ids[' + i + ']']=ids[i];
			}
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/deleteRadarTimeRule.json",postData);
			if(rs &&  rs.msg == "success"){
				jumbo.showMsg("删除成功！");
				var postData1 = loxia._ajaxFormToObj("query-form");
				$j("#tbl-radarTimeRuleList").jqGrid('setGridParam',{
					url:window.$j("body").attr("contextpath")+"/findRadarTimeRule.json",
					postData:postData1,
					page:1
				}).trigger("reloadGrid");
				loxia.initContext($j("#tbl-radarTimeRuleList"));
			}else{
				jumbo.showMsg("删除失败！");
			}
		}		
	});
});

function initRadarTimeRule(){
	$j("#selLpcodeAdd").val("");
	$j("#wlServiceAdd").val("");
	$j("#dateTimeTypeAdd").val("");
	$j("#fjWhAdd").empty();
	$j("<option value=''>"+'--请选择物流商--'+"</option>").appendTo($j("#fjWhAdd"));
	$j("#provinceAdd").val("");
	$j("#cityAdd").empty();
	$j("<option value=''>"+'--请选择省--'+"</option>").appendTo($j("#cityAdd"));
	$j("#dateTime").val("");
	$j("#hour").val("");
	$j("#minute").val("");
}

//弹出编辑页面
function updaterr(tag,event){
	var id = $j(tag).parents("tr").attr("id");
	$j("#cityUpdate").empty();
	$j("<option value=''>"+'--请选择省--'+"</option>").appendTo($j("#cityUpdate"));
	$j("#provinceUpdate").val("");
	$j("#cityUpdate").attr("disabled","disabled");
	$j("#provinceUpdate").attr("disabled","disabled");
	var postData = {};
	$j("#rrid").val(id);
	postData['id'] = id;
	var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/getRadarTimeRuleById.json",postData);
	if(rs &&  rs.msg){
		$j("#selLpcodeUpdate").val(rs.msg['lpCode']);
		getfjwh(rs.msg['lpCode']);
		$j("#fjWhUpdate").val(rs.msg['phyid']);
		$j("#wlServiceUpdate").val(rs.msg['wlService']);
		$j("#dateTimeTypeUpdate").val(rs.msg['dateTimeType']);
		if(rs.msg['dateTimeType'] == "1"){
			$j("#cityUpdate").removeAttr("disabled");
			$j("#provinceUpdate").removeAttr("disabled");			
			$j("#dateTimeUpdate").removeAttr("disabled");
			$j("#provinceUpdate").val(rs.msg['province']);
			getCity(rs.msg['province']);
			$j("#cityUpdate").val(rs.msg['city']);
		}
		if(rs.msg['dateTimeType'] != "1"){
			$j("#dateTimeUpdate").attr("disabled","disabled");
		}
		$j("#dateTimeUpdate").val(rs.msg['dateTime']);
		var ljTime = rs.msg['overTakingTime'].split(":");
		$j("#hourUpdate").val(ljTime[0]);
		$j("#minuteUpdate").val(ljTime[1]);
		$j("#show_rr_dialog").dialog({
			closeOnEscape:false, 
			open:function(event,ui){$j(".ui-dialog-titlebar-close").hide();} 
			}); 
		$j("#show_rr_dialog").dialog("open");
	}
}

function getfjwh(lpCode){
	var postData = {};
//	alert(selLpcode);
	postData['lpCode'] = lpCode;
	if(selLpcode == ''){
		$j("#fjWhUpdate").empty();
		$j("<option value=''>"+'--请选择物流商--'+"</option>").appendTo($j("#fjWhUpdate"));
	}else{
		var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/findPhyWarehouseByLpcode.json"),postData);
		$j("#fjWhUpdate").empty();
		$j("<option value=''>"+'--请选择--'+"</option>").appendTo($j("#fjWhUpdate"));
		for(var idx in rs){
			$j("<option value='" + rs[idx].phyid + "'>"+ rs[idx].phyName +"</option>").appendTo($j("#fjWhUpdate"));
		}			
	}
}

function getCity(province){
	var postData = {};
	postData['province'] = province;
	if(province == ''){
		$j("#cityUpdate").empty();
		$j("<option value=''>"+'--请选择省--'+"</option>").appendTo($j("#cityUpdate"));
	}else{
		var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/findRadarAreaCity.json"),postData);
		$j("#cityUpdate").empty();
		for(var idx in rs){
			$j("<option value='" + rs[idx].city + "'>"+ rs[idx].city +"</option>").appendTo($j("#cityUpdate"));
		}
        $j("#cityUpdate option[value='null']").remove();
	}
}

function closeDialog(){
	var postData = loxia._ajaxFormToObj("query-form");
	$j("#tbl-radarTimeRuleList").jqGrid('setGridParam',{
		url:window.$j("body").attr("contextpath")+"/findRadarTimeRule.json",
		postData:postData,
		page:1
	}).trigger("reloadGrid");
	$j("#show_rr_dialog").dialog("close");
}

function deleteR(){
	var postData = {};
	var id = $j("#rrid").val();		
	if(confirm("确定删除快递时效信息？")){
		postData['ids[0]']=id;
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/deleteRadarTimeRule.json",postData);
		if(rs &&  rs.msg == "success"){
			jumbo.showMsg("删除成功！");
			var postData1 = loxia._ajaxFormToObj("query-form");
			$j("#tbl-radarTimeRuleList").jqGrid('setGridParam',{
				url:window.$j("body").attr("contextpath")+"/findRadarTimeRule.json",
				postData:postData1,
				page:1
			}).trigger("reloadGrid");
			loxia.initContext($j("#tbl-radarTimeRuleList"));
			$j("#show_rr_dialog").dialog("close");
		}else{
			jumbo.showMsg("删除失败！");
		}
	}		
}