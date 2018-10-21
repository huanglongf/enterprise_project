$j.extend(loxia.regional['zh-CN'],{
	
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

function showDetail(obj){
	var id=$j(obj).parent().parent().attr("id");
	var data = $j("#tbl-radarWarningReasonList").jqGrid("getRowData",id);
	$j("#ooc_rwr_list").addClass("hidden");
	$j("#ooc_rwr_lineList").removeClass("hidden");
	$j("#eCode").text(data["name"]);
	$j("#lName").text(data["lvname"]);
	$j("#memor").text(data["remark"]);
	$j("#rid").val(id);
	var postData = {};
	postData['rwr.id'] = id;
	var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/findRdWarningLvByLv.json",postData);
	$j("#lvlAdd").empty();
	$j("<option value=''>--请选择--</option>").appendTo($j("#lvlAdd"));
	for(var idx in rs){
		$j("<option value='" + rs[idx].id + "'>"+ rs[idx].name +"</option>").appendTo($j("#lvlAdd"));
	}
	renovateradarWarningReasonLineList(id);
}

$j(document).ready(function (){
	//初始化预警类型
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findRdErrorCode.json");
	for(var idx in result){
		$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#errorCode"));
		$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#errorCodeAdd"));
	}
	//初始化预警级别
	var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findRdWarningLv.json");
	for(var idx in rs){
		$j("<option value='" + rs[idx].id + "'>"+ rs[idx].name +"</option>").appendTo($j("#lv"));
		$j("<option value='" + rs[idx].id + "'>"+ rs[idx].name +"</option>").appendTo($j("#lvAdd"));
	}
	var postData = loxia._ajaxFormToObj("query-form");
	$j("#tbl-radarWarningReasonList").jqGrid({
		url:$j("body").attr("contextpath")+"/findRadarWarningReason.json",
		postData:postData,
		datatype: "json",
		colNames: ["ID","预警类型","初始化预警级别","备注","创建人"],
		colModel: [
				   {name: "id", index: "id", hidden: true},
		           {name: "name", index: "name", width:200,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, resizable:true},
		           {name: "lvname", index: "lvname", width:160,resizable:true},    
		           {name: "remark", index: "remark", width: 230, resizable: true},
		           {name: "username", index: "username", width: 80, resizable: true}
		           ],
		caption: "快递预警类型",
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
		jsonReader: { repeatitems : false, id: "0" }
	});	
	
	$j("#tbl-rwrline-list").jqGrid({
		datatype: "json",
		colNames: ["ID","预警级别","有效时间（h）","预警说明","操作"],
		colModel: [
							{name:"id",index:"id",hidden: true},
							{name:"lvCode",index:"lvCode", width:160,resizable:true},
							{name:"warningHour",index:"warningHour",width:100,resizable:true},
							{name:"memo",index:"memo",width:200,resizable:true},
							{name: "operator", width: 80, resizable: true,formatter:"buttonFmatter", formatoptions:{"buttons":{label:"删除", onclick:"deleteline(this,event);"}}}
						],
		caption: "快递预警升级规则列表",
	   	sortname: 'id',
		sortorder: "asc",
	   	shrinkToFit:false,
		height:"auto",
		rowNum:-1,
		viewrecords: true,
	   	rownumbers:true,
		jsonReader: {repeatitems : false, id: "0"},
		loadComplete:function(){
			loxia.initContext($j("#tbl-rwrline-list"));
		}
});
	
	//新建
	$j("#save").click(function(){
		var errorCode = $j("#errorCodeAdd").val();
		var lv = $j("#lvAdd").val();
		var memo = $j("#memo").val();
		if(errorCode==""){
			jumbo.showMsg("请选择预警类型");
			return;
		}
		if(lv==""){
			jumbo.showMsg("请选择初始预警级别");
			return;
		}
		var postData = {};
		postData['rwr.eid'] = errorCode;
		postData['rwr.lvid'] = lv;
		postData['rwr.remark'] = memo;
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/saveRadarWarningReason.json",postData);
		if(rs &&  rs.msg == "success"){
			jumbo.showMsg("创建成功！");
			renovateradarWarningReasonList();
			initRadarWarningReason();
		} 
		else if(rs &&  rs.msg == "exist"){
			jumbo.showMsg("预警类型已存在，请换一个添加");
		}
		else{
			jumbo.showMsg("创建失败！");
		}
	});
	
	//添加时效预警明细
	$j("#saveLine").click(function(){
		var id = 	$j("#rid").val();
		var lvl = $j("#lvlAdd").val();
		var hour = $j("#hour").val();
		var memo = $j("#linememo").val();
		var isChecked = false;
		var error = "";
		if(lvl==""){
			jumbo.showMsg("请选择预警级别");
			return;
		}
		if(hour==""){
			jumbo.showMsg("有效时间（h）不能为空");
			return;
		}
		var ex = /^\d+$/;
		if(!ex.test(hour)){
			jumbo.showMsg("有效时间（h）格式不正确");
			return;
		}
		var data = $j("#tbl-rwrline-list").jqGrid('getRowData');
		$j.each(data,function(i, rwr) {
			var jibei = rwr['lvCode'].substring(0,rwr['lvCode'].length-1);
			var time = rwr['warningHour'];
			if(lvl == jibei){
				error = "预警级别："+lvl+" 已存在";
				isChecked = true;
				return false;
			}
		});
		if(isChecked){
			jumbo.showMsg(error);
			return;
		}
		if(data.length > 0){
			for(var i = data.length;i<=data.length;i++){
				var jibei = data[i-1]['lvCode'].substring(0,data[i-1]['lvCode'].length-1);
				var time = data[i-1]['warningHour'];
				if(lvl > jibei){
					if(parseInt(hour) <= parseInt(time)){
						error = "有效时间必须大于 "+jibei+"级 的有效时间";
						isChecked = true;
					}					
				}
				if(lvl < jibei){
					if(hour >= time){
						error = "有效时间必须小于 "+jibei+"级 的有效时间";
						isChecked = true;
					}					
				}
			}		
		}
		if(isChecked){
			jumbo.showMsg(error);
			return;
		}
		$j.each(data,function(i, rwr) {
			var jibei = rwr['lvCode'].substring(0,rwr['lvCode'].length-1);
			var time = rwr['warningHour'];
			var lvll = lvl -1;
			if(lvll == jibei){
				if(parseInt(hour) <= parseInt(time)){
					error = "有效时间必须大于 "+jibei+"级 的有效时间";
					isChecked = true;
					return false;
				}	
			}
		});
		if(isChecked){
			jumbo.showMsg(error);
			return;
		}
		var postData = {};
		postData['rwrl.wrId'] = id;
		postData['rwrl.lvId'] = lvl;
		postData['rwrl.warningHour'] = hour;
		postData['rwrl.memo'] = memo;
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/saveRadarWarningReasonLine.json",postData);
		if(rs &&  rs.msg == "success"){
			jumbo.showMsg("创建成功！");
			renovateradarWarningReasonLineList(id);
			initRadarWarningReasonLine();
		}else{
			jumbo.showMsg("创建失败！");
		}
	});
	
	//查询
	$j("#btn-query").click(function() {
		renovateradarWarningReasonList();
	});
	
	//重置
	$j("#reset").click(function() {
		$j("#errorCode").val("");
		$j("#lv").val("");
	});
	
	//删除
	$j("#deleteButton").click(function() {
		var postData = {};
		var ids = $j("#tbl-radarWarningReasonList").jqGrid('getGridParam','selarrrow');
		if(ids.length == 0){
			jumbo.showMsg("请选择需要删除的快递预警类型");
			return;
		}
		if(confirm("确定删除选中的快递预警类型？")){
			for(var i in ids){
				postData['ids[' + i + ']']=ids[i];
			}
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/deleteRadarWarningReason.json",postData);
			if(rs &&  rs.msg == "success"){
				jumbo.showMsg("删除成功！");
				renovateradarWarningReasonList();
			}else{
				jumbo.showMsg("删除失败！");
			}
		}		
	});
	
	$j("#back").click(function(){
		$j("#ooc_rwr_lineList").addClass("hidden");
		$j("#ooc_rwr_list").removeClass("hidden");
	});
});

function initRadarWarningReason(){
	$j("#errorCodeAdd").val("");
	$j("#lvAdd").val("");
	$j("#memo").val("");
}

function initRadarWarningReasonLine(){
	$j("#lvlAdd").val("");
	$j("#hour").val("");
	$j("#linememo").val("");
}

function deleteline(tag,event){
	var id = $j(tag).parents("tr").attr("id");
	var rid = $j("#rid").val();
	var postData = {};
	postData['id'] = id;
	if(confirm("确定删除该条快递预警升级规则吗？")){
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/deleteRadarWarningReasonLine.json",postData);
		if(rs &&  rs.msg == "success"){
			jumbo.showMsg("删除成功！");
			renovateradarWarningReasonLineList(rid);
		}else{
			jumbo.showMsg("删除失败！");
		}
	}	
}

function renovateradarWarningReasonList(){
	var postData = loxia._ajaxFormToObj("query-form");
	$j("#tbl-radarWarningReasonList").jqGrid('setGridParam',{
		url:window.$j("body").attr("contextpath")+"/findRadarWarningReason.json",
		postData:postData,
		page:1
	}).trigger("reloadGrid");
}

function renovateradarWarningReasonLineList(id){
	var postData = {};
	postData['rwr.id'] = id;
	$j("#tbl-rwrline-list").jqGrid('setGridParam',{
		url:window.$j("body").attr("contextpath")+"/findRadarWarningReasonLine.json",
		postData:postData,
		page:1
	}).trigger("reloadGrid");
	loxia.initContext($j("#tbl-rwrline-list"));
}


