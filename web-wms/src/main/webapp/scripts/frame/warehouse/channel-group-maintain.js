$j.extend(loxia.regional['zh-CN'], {
});

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}
var groupId;
$j(document).ready(function (){
	$j("#tbl-channel-group-list").jqGrid({
		url:$j("body").attr("contextpath")+"/findAllChannelGroupByOuId.json",
		datatype: "json",
		colNames: ["ID","编码","名称","创建时间","操作"],
		colModel: [
		           {name: "id", index: "id", hidden: true},		         
		           {name:"code",index:"code", width:95,resizable:true},
		           {name:"name",index:"name", width:95,resizable:true},
		           {name:"createTime",index:"createTime", width:150,resizable:true},
		           //{name:"operator", width: 120, resizable: true,formatter:"buttonFmatter", formatoptions:{"buttons":{label:"编辑", onclick:"editDetial(this,event);"},"buttons":{label:"删除", onclick:"deleteDetail(this,event);"}}}
		           {name:"operator", width: 120, resizable: true,formatter:function(v,x,r){return "<button type='button' loxiaType='button' onclick='editDetial(this)'>编辑</button><button type='button' loxiaType='button' onclick='deleteCG(this)'>删除</button>";}}
		           ],
		caption: "仓库关联渠道组列表",
		sortname: 'cg.create_time',
	   	sortorder: "desc",
	   	multiselect: false,
	   	height:"auto",
	   	//multiselect: false,
	    rowNum: -1,
		//rowList:jumbo.getPageSizeList(),
	   	//pager:"#pager",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	$j("#add").click(function(){
		$j("#dialog_channelGroup").hide();
		$j("#dialog_addGroup").show();
	});
	$j("#saveGroup").click(function(){
		var code = $j.trim($j("#code").val());
		var name = $j.trim($j("#name").val());
		var sort = $j.trim($j("#sort").val());
		if(null == code || '' == code){
			jumbo.showMsg("编码不能为空！");
			return false;
		}
		if(null == name || '' == name){
			jumbo.showMsg("名称不能为空！");
			return false;
		}
		if(null == sort || '' == sort){
			jumbo.showMsg("优先级不能为空！");
			return false;
		}
		var rs = loxia.syncXhrPost($j("body").attr("contextpath")+"/findChannelGroupExistByCode.json?group.code="+code);
		if(rs && rs.result == true){
			jumbo.showMsg("该编码已存在！");
			return false;
		}
		var rs = loxia.syncXhrPost($j("body").attr("contextpath")+"/findChannelGroupExistByName.json?group.name="+name);
		if(rs && rs.result == true){
			jumbo.showMsg("该名称已存在！");
			return false;
		}
		var id = -1;
		var status = 1;
		var postData = {};
		postData['group.id'] = id;//id
		postData["status"] = status;//状态,默认正常
		postData["group.code"] = code;//编码
		postData["group.name"] = name;//名称
		postData["group.sort"] = sort;//优先级
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/saveChannelGroup.json",postData);
		if(rs && rs.msg == 'success'){
			//执行成功
			jumbo.showMsg("创建成功！");
			var param = {};
			param["group.code"] = code;
			var data = loxia.syncXhrPost($j("body").attr("contextpath") + "/findChannelGroupByCode.json",param);
			if(data){
				groupId = data.id;
				$j("#oldGroupName").val(name);
			}
			else{
				jumbo.showMsg("系统异常！");
				return false;
			}
		}else{
			jumbo.showMsg("操作失败！");
			return false;
		}
		$j("#dialog_addGroup").hide();
		$j("#groupId").val(groupId);
		$j("#groupCode").val(code).attr("disabled","disabled");
		$j("#groupName").val(name);
		$j("#groupSort").val(sort);
		$j("#tbl-channel-ref-list").jqGrid({
			url:$j("body").attr("contextpath")+"/findAllChannelRefByOuId.json",
			datatype: "json",
			colNames: ["ID","渠道编码","渠道名称","创建时间","是否合并发货渠道","操作"],
			colModel: [
			           {name: "id", index: "id", hidden: true},		         
			           {name:"code",index:"code", width:95,resizable:true},
			           {name:"name",index:"name", width:95,resizable:true},
			           {name:"createTime",index:"createTime", width:150,resizable:true},
			           {name: "isMarger", index: "isMarger", hidden: true},	
			           {name:"operator", width: 100, resizable: true,formatter:function(v,x,r){return "<input type='checkbox' name = 'isChannelRef' onclick='validateChannelRef(this)'/>";}}
			           ],
			caption: "关联渠道列表",
			sortname: 'channel.create_time',
		   	sortorder: "desc",
		   	shrinkToFit:false,
			height:"auto",
		   	//multiselect: false,
		    rowNum: -1,
			//rowList:jumbo.getPageSizeList(),
		   	//pager:"#pager",
		   	height:jumbo.getHeight(),
			viewrecords: true,
	   		rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" },
			gridComplete: function(){
				$j("#tbl-channel-ref-list input[name='isChannelRef']").attr("checked",""); 
				var channelList = loxia.syncXhrPost($j("body").attr("contextpath")+"/findAllChannelRefByGroupId.json?group.id=" + groupId);
				if(channelList){
					for(var i = 0; i < channelList.length; i++){
						var id = channelList[i].id;
						$j("#"+ id +" input[name='isChannelRef']").attr("checked","checked");
					}
				}
			}
		});
		$j("#tbl-channel-ref-list").jqGrid('setGridParam',{
			url:$j("body").attr("contextpath")+"/findAllChannelRefByOuId.json"}).trigger("reloadGrid");
		$j("#dialog_groupDetail").show();
		
	});
	$j("#addGroupBack").click(function(){
		$j("#dialog_addGroup input").val("");
		$j("#dialog_addGroup").hide();
		$j("#dialog_channelGroup").show();
	});
	$j("#code").focusout(function(){
		var code = $j.trim($j("#code").val());
		if('' != code){
			var rs = loxia.syncXhrPost($j("body").attr("contextpath")+"/findChannelGroupExistByCode.json?group.code="+code);
			if(rs && rs.result == true){
				jumbo.showMsg("该编码已存在！");
				$j("#code").val("");
				return false;
			}
		}
	});
	$j("#name").focusout(function(){
		var name = $j.trim($j("#name").val());
		if('' != name){
			var rs = loxia.syncXhrPost($j("body").attr("contextpath")+"/findChannelGroupExistByName.json?group.name="+name);
			if(rs && rs.result == true){
				jumbo.showMsg("该名称已存在！");
				$j("#name").val("");
				return false;
			}
		}
	});
	$j("#name").focusin(function(){
		var code = $j.trim($j("#code").val());
		if('' == code)
			$j("#code").focus();
	});
	$j("#sort").focusin(function(){
		var name = $j.trim($j("#name").val());
		if('' == name)
			$j("#name").focus();
	});
	$j("#sort").keyup(function(){
		var s = $j(this).val();
		if(!checkNumber(s)){
			 $j(this).val("");
		}
	});
	$j("#groupSort").keyup(function(){
		var s = $j(this).val();
		if(!checkNumber(s)){
			 $j(this).val("");
		}
	});
	$j("#back").click(function(){
		$j("#dialog_groupDetail").hide();
		$j("#dialog_groupDetail input").val("");
		$j("#dialog_addGroup input").val("");
		$j("#tbl-channel-group-list").jqGrid('setGridParam',{
			url:$j("body").attr("contextpath")+"/findAllChannelGroupByOuId.json"}).trigger("reloadGrid");
		$j("#dialog_channelGroup").show();
	});
	$j("#save").click(function(){
		var id = $j.trim($j("#groupId").val());
		var groupCode = $j.trim($j("#groupCode").val());
		var oldGroupName = $j.trim($j("#oldGroupName").val());
		var groupName = $j.trim($j("#groupName").val());
		var groupSort = $j.trim($j("#groupSort").val());
		if(null == groupName || '' == groupName){
			jumbo.showMsg("渠道组名称不能为空！");
			return false;
		}else{
			if(oldGroupName != groupName){
				var rs = loxia.syncXhrPost($j("body").attr("contextpath")+"/findChannelGroupExistByName.json?group.name="+groupName);
				if(rs && rs.result == true){
					jumbo.showMsg("该渠道组名称已存在！");
					$j("#groupName").val("").focus();
					return false;
				}
			}
		}
		if(null == groupSort || '' == groupSort){
			jumbo.showMsg("渠道组优先级不能为空！");
			return false;
		}
		var postData = {};
		postData['group.id'] = id;//roleId
		postData["status"] = 1;//状态,正常
		postData["group.code"] = groupCode;//编码
		postData["group.name"] = groupName;//名称
		postData["group.sort"] = groupSort;//优先级
 		var ids = $j("#tbl-channel-ref-list").jqGrid('getDataIDs');
 		var index = 0;
		for(var i=0;i < ids.length;i++){
			var datas = $j("#tbl-channel-ref-list").jqGrid('getRowData',ids[i]);
			var isChannelRef = $j("#"+datas["id"]+" input[name='isChannelRef']").attr('checked');
			if(true == isChannelRef){
				postData["channelRef[" + index + "].id"]=datas["id"];
				index++;
			}
		}
		if(confirm("确定保存已修改的内容？")){
			var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/saveChannelRef.json",postData);
			if(rs && rs["msg"] == "success"){
				jumbo.showMsg("保存成功！");
				$j("#back").click();
			}else{
				jumbo.showMsg("操作失败！");		
			}
		}
	});
});
function checkNumber(s){
    if (s!=null && s!=""){
      return !isNaN(s);
   }
    return false;
}
function editDetial(obj,event){
	var row = $j(obj).parent().parent().attr("id");
	groupId = row;
	var data = $j("#tbl-channel-group-list").jqGrid("getRowData",row);
	$j("#groupId").val(row);
	$j("#groupCode").val(data['code']).attr("disabled","disabled");
	$j("#groupName").val(data['name']);
	$j("#oldGroupName").val(data['name']);
	var param = {};
	param["group.code"] = data['code'];
	var cg = loxia.syncXhrPost($j("body").attr("contextpath") + "/findChannelGroupByCode.json",param);
	$j("#groupSort").val(cg['sort']);
	$j("#tbl-channel-ref-list").jqGrid({
		url:$j("body").attr("contextpath")+"/findAllChannelRefByOuId.json",
		datatype: "json",
		colNames: ["ID","渠道编码","渠道名称","创建时间","是否合并发货渠道","操作"],
		colModel: [
		           {name: "id", index: "id", hidden: true},		         
		           {name:"code",index:"code", width:95,resizable:true},
		           {name:"name",index:"name", width:95,resizable:true},
		           {name:"createTime",index:"createTime", width:150,resizable:true},
		           {name: "isMarger", index: "isMarger", hidden: true},	
		           {name:"operator", width: 100, resizable: true,formatter:function(v,x,r){return "<input type='checkbox' name = 'isChannelRef' onclick='validateChannelRef(this)'/>";}}
		           ],
		caption: "关联渠道列表",
		sortname: 'channel.create_time',
	   	sortorder: "desc",
	   	shrinkToFit:false,
		height:"auto",
	   	//multiselect: false,
	    rowNum: -1,
		//rowList:jumbo.getPageSizeList(),
	   	//pager:"#pager",
	   	height:jumbo.getHeight(),
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			$j("#tbl-channel-ref-list input[name='isChannelRef']").attr("checked",""); 
			var channelList = loxia.syncXhrPost($j("body").attr("contextpath")+"/findAllChannelRefByGroupId.json?group.id=" + groupId);
			if(channelList){
				for(var i = 0; i < channelList.length; i++){
					var id = channelList[i].id;
					$j("#"+ id +" input[name='isChannelRef']").attr("checked","checked");
				}
			}
		}
	});
	$j("#tbl-channel-ref-list").jqGrid('setGridParam',{
		url:$j("body").attr("contextpath")+"/findAllChannelRefByOuId.json"}).trigger("reloadGrid");
	$j("#dialog_channelGroup").hide();
	$j("#dialog_groupDetail").show();
}
function deleteCG(obj, event){
	var row = $j(obj).parent().parent().attr("id");
	if(confirm("确定删除此渠道组及其关联的所有渠道？")){
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/deleteChannelGroup.json?group.id="+row);
		if(rs && rs["msg"] == "success"){
			jumbo.showMsg("删除成功！");
			$j("#tbl-channel-group-list").jqGrid('setGridParam',{
				url:$j("body").attr("contextpath")+"/findAllChannelGroupByOuId.json"}).trigger("reloadGrid");
		}else{
			jumbo.showMsg("操作失败！");
		}
	}
}
function validateChannelRef(obj){
	var channelId = $j(obj).parent().parent().attr("id");
	var groupId = $j.trim($j("#groupId").val());
	if('' == channelId || '' == groupId){
		jumbo.showMsg("操作失败！");
	}
	var currentSelected = $j("#"+ channelId +" input[name='isChannelRef']").attr("checked");
	if(true == currentSelected){
		var cgList = loxia.syncXhrPost($j("body").attr("contextpath") + "/findAllChannelGroupByCIdAndOuId.json?channelId="+channelId+"&groupId="+groupId);
		if(cgList){
			var size = cgList.length;
			if(0 < size){
				$j("#"+ channelId +" input[name='isChannelRef']").attr("checked", "");
				jumbo.showMsg("该渠道已加入当前仓库的渠道组【"+ cgList[0].name +"】，无法加入其他渠道组！");
				return false;
			}
		}
		var data = $j("#tbl-channel-ref-list").jqGrid('getRowData',channelId);
		var isMarger = data["isMarger"];
		if(1 == isMarger){
			var detailList = $j("#tbl-channel-ref-list").jqGrid("getRowData");
			for(var i = 0; i < detailList.length; i++){
				var id = detailList[i].id;
				var isSelected = $j("#"+ id +" input[name='isChannelRef']").attr("checked");
				if(true == isSelected && id != channelId){
					if(0 == detailList[i].isMarger){
						$j("#"+ channelId +" input[name='isChannelRef']").attr("checked", "");
						jumbo.showMsg("该渠道是合并发货渠道，不允许同其他渠道加入同一渠道组！");
						return false;
					}
				}
			}
		}else{
			var detailList = $j("#tbl-channel-ref-list").jqGrid("getRowData");
			for(var i = 0; i < detailList.length; i++){
				var id = detailList[i].id;
				var isSelected = $j("#"+ id +" input[name='isChannelRef']").attr("checked");
				if(true == isSelected && id != channelId){
					if(1 == detailList[i].isMarger){
						$j("#"+ channelId +" input[name='isChannelRef']").attr("checked", "");
						jumbo.showMsg("该渠道不允许同合并发货渠道加入同一渠道组！");
						return false;
					}
				}
			}
		}
	}
	
}