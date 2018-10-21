$j.extend(loxia.regional['zh-CN'],{
	DATA_DELETE_SELECT : "请选择要删除的数据",
	SURE_DELETE : "您确定要删除选中的用户分组吗?",
	SYSTEM_ERROR : "系统出错,请重新点击'删除'按钮！",
	SAVE_FAILED : "保存用户分组列表失败,可能原因是删除选中的用户组中还有用户！",
	SAVE_SUCCESS:"保存用户分组列表成功！",
	RELEVANCY_SUCCESS : "关联选中用户操作成功!",
	RELEVANCY_FAILED : "关联选中用户操作失败!",
	SEPARATE_USER_SUCCESS : "脱离选中用户操作成功!",
	SEPARATE_USER_FAILED : "脱离选中用户操作失败!",
	
	ERROR_DELETE : "行{0}用户分组中仍然有用户，不能被删除",
	GROUP_NAME : "第{0}行分组名称",
	GROUP_DESCRIBE : "第{0}行分组描述",
	GROUP_NAME_SAME : "第{0}行分组名称{1}在第{2}行有相同的存在",
	
	LOGINNAME : "用户名",
	USERNAME : "真实姓名",
	JOBNUMBER : "工号",
	ISENABLED : "用户名是否有效",
	USER_LIST : "用户列表"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

var _group={};
_group.buildPostData=function(){
	var postData={};
	postData["userGroupCommand.groupId"]=$j("#currentGroupId").val();
	var ids=$j("#tbl-userlist2").jqGrid('getGridParam','selarrrow');
	for(var i in ids){
		postData['keyList['+i+"]"]=ids[i];
	}
	return postData;
}
_group.url=function(){
	return $j("body").attr("contextpath")+"/userlist.json";
}
$j(document).ready(function (){
	//删除
	$j("#edittable button[action='delete']").click(function(){
		if($j("#edittable table tbody:eq(0) tr.ui-state-highlight").length==0){
			jumbo.showMsg(loxia.getLocaleMsg("DATA_DELETE_SELECT"));	//请选择要删除的数据！
		}
		var keyList={};
		$j.each($j("#edittable table tr.update :checkbox:checked"),function(i,item){
			keyList['keyList['+i+']']=item.value;
		});
		if(!keyList['keyList[0]']) return true;
		var errorIds=loxia.syncXhr($j("body").attr("contextpath")+"/checkusergroupdelable.json",keyList);
		if(errorIds==null){
			if(window.confirm(loxia.getLocaleMsg("SURE_DELETE"))){	//"您确定要删除选中的用户分组吗?"
				var index=$j("#deleteHidden input").length;
				$j.each($j("#edittable table tr.update :checkbox:checked"),function(i,item){
					var tr=$j(this).parent().parent();
					$j("#deleteHidden").append($j("<input type='hidden' name='deleteList["+(index+i)+"].id' value="+item.value+" />"));
					$j("#deleteHidden").append($j("<input type='hidden' name='deleteList["+(index+i)+"].name' value="+tr.find("td:eq(1) input").val()+" />"));
					$j("#deleteHidden").append($j("<input type='hidden' name='deleteList["+(index+i)+"].description' value="+tr.find("td:eq(1) input").val()+" />"));
				});
				return true;
			}else{
				return false;
			}
		}else if(errorIds.exception){
			jumbo.showMsg(loxia.getLocaleMsg("SYSTEM_ERROR"));//系统出错,请重新点击'删除'按钮！
		}else{
			var errorLine=[];
			$j.each($j("#edittable table tr.update :checkbox:"),function(i,item){
				if($j(item).attr("checked")&&($j.inArray(item.value-0,errorIds)>-1)){
					errorLine.push(i+1);
				}
			});
			jumbo.showMsg(loxia.getLocaleMsg("ERROR_DELETE",errorLine.join(",")));	//行"+errorLine.join(",")+"用户分组中仍然有用户，不能被删除
			return false;
		}
	});
	//保存
	$j("#save").click(function(){
		var v=true;
		var c,codeMap = {}

		$j.each($j("#edittable table tbody:first tr"),function(i,item){
			//if(!jumbo.max($j(this).find("td:eq(1) input"),"第"+(i+1)+"行分组名称",true)||!jumbo.max($j(this).find("td:eq(2) input"),"第"+(i+1)+"行分组描述")){
			if(!jumbo.max($j(this).find("td:eq(1) input"),loxia.getLocaleMsg("GROUP_NAME",(i+1)),true)){
				v=false;
				return false;
			}
			c=$j(this).find("td:eq(1) input").val();
			if(c in codeMap){
				//jumbo.showMsg("第"+(i+1)+"行分组名称" + c+"在第"+codeMap[c]+"行有相同的存在");
				jumbo.showMsg(loxia.getLocaleMsg("GROUP_NAME_SAME",[(i+1),c,codeMap[c]])),
				v=false;
				return false;
			}else{
				codeMap[c] = i+1;
			}	
		});
		if(v){
			$j.each($j("#edittable table tr.add"),function(i,item){
				$j(this).find(":input").each(function(){
					$j(this).attr("name",$j(this).attr("name").replace(/\(.*\)/ig,"["+i+"]"));
				});
			});
			loxia.asyncXhrPost($j("body").attr("contextpath") + "/updateusergroup.json",loxia._ajaxFormToObj("userGroupForm"),{
			success:function (data) {
				if(data.msg=="failure"){
					jumbo.showMsg(loxia.getLocaleMsg("SAVE_FAILED"));//保存用户分组列表失败,可能原因是删除选中的用户组中还有用户！
				}
				else{
					jumbo.showMsg(loxia.getLocaleMsg("SAVE_SUCCESS"));//保存用户分组列表成功！
				}
				window.location=loxia.getTimeUrl($j("body").attr("contextpath")+"/auth/usergroupentry.do");
			   }
			});
		}
		
		
	});
	//批量设置用户
	$j("button.custom1").live("click",function(){
		document.getElementById("userForm").reset();
		if($j("#batch-edit").hasClass("hidden")){
			$j("#batch-edit").removeClass("hidden");
		}
		$j("#currentGroupId").val($j(this).attr("targetId"));
		$j("#currentGroup").html($j(this).attr("currentGroup"));
		$j("#groupId option[value='"+$j(this).attr("targetId")+"']").attr("selected",true);
		$j("#tbl-userlist2").jqGrid('setGridParam',{url:_group.url(),postData:{"userGroupCommand.groupId":$j(this).attr("targetId")}}).trigger("reloadGrid",[{page:1}]);
		jumbo.bindTableExportBtn("tbl-userlist2",{"isEnabled":"trueOrFalse"},_group.url(),{"userGroupCommand.groupId":$j(this).attr("targetId")});
	});
	//用户表格
	var status=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"trueOrFalse"});
	if(!status.exception){
		$j("#tbl-userlist2").jqGrid({
				datatype: "json",
				//colNames: ["ID","用户名","真实姓名","工号","用户名是否有效"],
				colNames: ["ID",i18("LOGINNAME"),i18("USERNAME"),i18("JOBNUMBER"),i18("ISENABLED")],
				colModel: [
						   {name: "id", index: "u.id", width: 120, hidden: true},	
				           {name: "loginName", index: "u.LOGIN_NAME", width: 120, resizable: true},		         
				           {name: "userName", index: "u.USER_NAME", width: 200, resizable: true},
				           {name: "jobNumber", index: "u.JOB_NUMBER", width: 200, resizable: true},
			               {name: "isEnabled", index: "u.IS_ENABLED", width: 200, resizable: true,
			        	   	formatter:'select',editoptions:status
			           		}],
				caption: i18("USER_LIST"),//用户列表
				rowNum: jumbo.getPageSize(),
				rowList:jumbo.getPageSizeList(),
		   		height:jumbo.getHeight(),
		   		sortname: 'u.LOGIN_NAME',
		     	pager: '#pager2',
		     	multiselect: true,
			postData:{},
			sortorder: "desc", 
			viewrecords: true,
   			rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" }
			});	
	}else{
		jumbo.showMsg(loxia.getLocaleMsg("ERROR_INIT"));//初始化'系统参数'数据异常！
	}
	
	//表格查询
	$j("#reload").click(function(){
		var url=$j("body").attr("contextpath")+"/userlist.json";
		$j("#tbl-userlist2").jqGrid('setGridParam',{url:url,postData:loxia._ajaxFormToObj("userForm")}).trigger("reloadGrid",[{page:1}]);	
		jumbo.bindTableExportBtn("tbl-userlist2",{"isEnabled":"trueOrFalse"},url,loxia._ajaxFormToObj("userForm"));
		});
	$j("#add").click(function(){
		if($j("#tbl-userlist2 tbody:eq(0) tr.ui-state-highlight").length==0){
			jumbo.showMsg(loxia.getLocaleMsg("DATA_DELETE_SELECT"));//请选择要删除的数据！
			return false;
		}
		loxia.asyncXhrPost($j("body").attr("contextpath") + "/adduserstogroup.json",_group.buildPostData(),{
		success:function (data) {
			if(data.msg=="success"){
				jumbo.showMsg(loxia.getLocaleMsg("RELEVANCY_SUCCESS"));//关联选中用户操作成功!
			}else{
				jumbo.showMsg(loxia.getLocaleMsg("RELEVANCY_FAILED"));//关联选中用户操作失败!
			}
			$j("#tbl-userlist2").jqGrid('setGridParam',{url:_group.url(),postData:{"userGroupCommand.groupId":$j("#groupId").val()}}).trigger("reloadGrid",[{page:1}]);
		   }
		});
	});
	$j("#remove").click(function(){
		if($j("#tbl-userlist2 tbody:eq(0) tr.ui-state-highlight").length==0){
			jumbo.showMsg(loxia.getLocaleMsg("DATA_DELETE_SELECT"));//请选择要删除的数据！
			return false;
		}
		loxia.asyncXhrPost($j("body").attr("contextpath") + "/removeusersfromgroup.json",_group.buildPostData(),{
			success:function (data) {
				if(data.msg=="success"){
					jumbo.showMsg(loxia.getLocaleMsg("SEPARATE_USER_SUCCESS"));//脱离选中用户操作成功!
				}else{
					jumbo.showMsg(loxia.getLocaleMsg("SEPARATE_USER_FAILED"));//脱离选中用户操作失败!
				}
				$j("#tbl-userlist2").jqGrid('setGridParam',{url:_group.url(),postData:{"userGroupCommand.groupId":$j("#groupId").val()}}).trigger("reloadGrid",[{page:1}]);
			  }
		});
	});
});

    
    
    