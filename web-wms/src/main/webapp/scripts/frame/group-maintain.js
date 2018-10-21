$j.extend(loxia.regional['zh-CN'],{
	ENTITY_OPERATION_CENTER		:	"运营中心",
	ENTITY_WAREHOUSE			:	"仓库",
	ENTITY_COMPANY				:	"公司",
	ENTITY_ROOT					:	"集团",
	ENTITY_COM_ROOT				:	"公司或者集团",
	ENTITY_GROUP_CODE			:	"组织节点编码",
	ENTITY_GROUP_NAME			:	"组织节点名称",
	EXIST 						: 	"已经存在相同的{0}[{1}]！",
	FUNCTION_MAINTAIN			: 	"集团组织架构管理",
	MAX_LENGTH					:	"{0}最长只能{1}个字符！",
	MSG_CONFIRM_ADD				:	"您确定要新增该节点的子节点吗！",
	MSG_ERROR_DEL				:	"集团根节点不能被删除！",
	REQUIRED_NODE				: 	"请选择需要编辑的节点！",
	RIGHT_MODE					:	"{0}只能是{1}的子节点！"
});
/**
 * 
 * @param {Object} msg
 * @param {Object} args
 * @return {TypeName} 
 */
function i18(msg, args){
	return loxia.getLocaleMsg(msg, args);
}
/**
 * 
 * @param {Object} value
 * @param {Object} obj
 * @memberOf {TypeName} 
 * @return {TypeName} 
 */
function checkName(value,obj){
	var o=$j(obj.element),max=o.attr("max")||255;
	if(value.length>max){
		return i18("MAX_LENGTH",[i18("ENTITY_GROUP_NAME"),max]);
	}
	var b=false,isAdd=o.attr("id")=="addName";
	$j("#groupTree div.tree-node"+(isAdd?"":":not(.tree-node-selected)")+" span.tree-title").each(function(){
		if(value==$j(this).html().substring(0,$j(this).html().indexOf("("))){
			b=true;
			return false;
		}
	});
	if(b){
		return i18("EXIST",[i18("ENTITY_GROUP_NAME"),value]);
	}
	return loxia.SUCCESS;
}
/**
 * 
 * @param {Object} value
 * @param {Object} obj
 * @return {TypeName} 
 */
function checkCode(value,obj){
	var o=$j(obj.element),max=o.attr("max")||50;
	if(value.length>max){
		return i18("MAX_LENGTH",[i18("ENTITY_GROUP_CODE"),max]);
	}
	var b=false;
	$j("#groupTree div.tree-node span.tree-title").each(function(i){
		if(value==$j(this).html().substring($j(this).html().indexOf(")-")+2)){
			b=true;
			return false;
		}
	});
	if(b){
		return i18("EXIST",[i18("ENTITY_GROUP_CODE"),value]);
	}
	return loxia.SUCCESS;
}
/**
 * 
 * @return {TypeName} 
 */
function createFormValidate(){
	//运营中心只能是公司的子节点，仓库只能是运营中心的子节点，公司可以是公司和集团的子节点
	var type=$j("#addOuType option:selected").text(),node=$j("#groupTree").tree('getSelected'),nodeType=node.attributes.type;
	if(type.indexOf(i18("ENTITY_OPERATION_CENTER"))!=-1&&nodeType.indexOf(i18("ENTITY_COMPANY"))==-1){
		return (i18("RIGHT_MODE",[i18("ENTITY_OPERATION_CENTER"),i18("ENTITY_COMPANY")]));
	}else if(type.indexOf(i18("ENTITY_WAREHOUSE"))!=-1&&nodeType.indexOf(i18("ENTITY_OPERATION_CENTER"))==-1){
		return  (i18("RIGHT_MODE",[i18("ENTITY_WAREHOUSE"),i18("ENTITY_OPERATION_CENTER")]));
	}else if(type.indexOf(i18("ENTITY_COMPANY"))!=-1&&
			(nodeType.indexOf(i18("ENTITY_COMPANY"))==-1&&nodeType.indexOf(i18("ENTITY_ROOT"))==-1)){
		return  (i18("RIGHT_MODE",[i18("ENTITY_COMPANY"),i18("ENTITY_COM_ROOT")]));
	}
	return loxia.SUCCESS;
}
$j(document).ready(function() {
	$j("#groupTree").tree({
		url : $j("body").attr("contextpath")+"/grouptree.json",
		onClick : function(node){
			if($j("div.ui-layout-center").length>0){
				$j("#groupType").html(node.attributes.type);
				$j("#groupCode").html(node.attributes.code);
				loxia.byId("groupName").val(node.attributes.name);
				$j("#groupComment").val(node.attributes.comment);
				if(node.attributes.available){
					$j("#groupEnable option:first").attr("selected",true);
				}else{
					$j("#groupEnable option:last").attr("selected",true);
				}
				
				loxia.asyncXhrPost($j("body").attr("contextpath") + "/findChildOUT.json",
						{"operationUnit.id":node.id},
						{
						success:function (data) {
							$j("#addOuType option").remove();
							for(var i in data){
								var option="<option value='"+data[i]['id']+"'>"+data[i]['displayName']+"</option>";
								$j("#addOuType").append($j(option));
						   }
						}
						});
			}
		},
		onLoadSuccess: function(){
			var ouId=window.parent.$j("#ouId").val();
			$j("#groupTree div[node-id="+ouId+"]").addClass('tree-node-selected');
			var node=$j("#groupTree").tree('getSelected');
			if(node!=null){
				$j("#groupType").html(node.attributes.type);
				$j("#groupCode").html(node.attributes.code);
				$j("#groupName").val(node.attributes.name);
				$j("#groupComment").val(node.attributes.comment);
				$j("#groupEnable").val(node.attributes.available);
				
				loxia.asyncXhrPost($j("body").attr("contextpath") + "/findChildOUT.json",
						{"operationUnit.id":node.id},
						{
						success:function (data) {
							$j("#addOuType option").remove();
							for(var i in data){
								var option="<option value='"+data[i]['id']+"'>"+data[i]['displayName']+"</option>";
								$j("#addOuType").append($j(option));
								
						   }
						}
						});
			}
		}
	});
	
	$j("#toEdit").click(function(){
		window.parent.$j("a[acode='"+i18("FUNCTION_MAINTAIN")+"']").trigger("click");
	});
	$j("#save").click(function(){
		var node=$j("#groupTree").tree('getSelected');
		if(null==node){
			jumbo.showMsg(i18("REQUIRED_NODE"));
			
		}else{
			var errors=loxia.validateForm("modifyForm");
			if(errors.length>0){
				jumbo.showMsg(errors);
				return false;
			}
			if($j("#groupEnable").val()=="false"){
				if($j("#groupTree > li > div.tree-node-selected").length==1){
					jumbo.showMsg(i18("MSG_ERROR_DEL"));
					$j("#groupEnable").val("true");
					return false;
				}
//				if(!window.confirm("您确定要删除该节点吗")){
//					$j("#groupEnable").val("true");
//					return false;
//				}
			}
			
			$j("#save").attr("disabled",true);
			loxia.lockPage();
			loxia.asyncXhrPost($j("body").attr("contextpath") + "/groupupdate.json",
				{
				"operationUnit.id":node.id,
				"operationUnit.name":$j("#groupName").val(),
				"operationUnit.comment":$j("#groupComment").val(),
				"operationUnit.isAvailable":$j("#groupEnable").val()
				},
				{
				success:function (data) {
					loxia.unlockPage();
					window.location=loxia.getTimeUrl($j("body").attr("contextpath") + "/auth/groupmaintain.do");
				   }
				});
		}
		
	});
	
	$j("#add").click(function(){
		var node=$j("#groupTree").tree('getSelected');
		if(null==node){
			jumbo.showMsg(i18("REQUIRED_NODE"));
		}
		var errors=loxia.validateForm("createForm");
		if(errors.length>0){
			jumbo.showMsg(errors);
			return false;
		}else if(window.confirm(i18("MSG_CONFIRM_ADD"))){
			
			$j("#add").attr("disabled",true);
			loxia.lockPage();
			loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath") + "/groupadd.json",
				{
				"operationUnit.parentUnit.id":node.id,
				"operationUnit.code":$j("#addCode").val(),
				"operationUnit.name":$j("#addName").val(),
				"operationUnit.comment":$j("#addComment").val(),
				"operationUnit.ouType.id":$j("#addOuType").val()
				},
				{
			success:function (data) {
				loxia.unlockPage();
				window.location=loxia.getTimeUrl($j("body").attr("contextpath") + "/auth/groupmaintain.do");
			   }
			});
		}
		
	});
	$j(document).bind("keypress",function(e){
		var ev=e;
		if(!ev){ ev=window.event; }  
		if(13==ev.keyCode){
			return false;
		}
	});
});