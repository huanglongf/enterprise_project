$j.extend(loxia.regional['zh-CN'],{
	INFO_BATCH_RESULT_SUCCESS 			: "批量修改成功",
	INFO_BATCH_RESULT_FAILURE			: "批量修改失败",
	INFO_CHOOSE_USERGROUP 				: "请选择用户分组",
	INFO_CHOOSE_OUTYPE 					: "请选择组织类型",
	INFO_CHOOSE_OU 						: "请选择组织",
	INFO_CHOOSE_ROLE 					: "请选择用户角色",
	
	INFO_BATCH_ROLE_ADD_SUCCESS 		: "批量授权角色成功",
	INFO_BATCH_ROLE_ADD_FAILURE 		: "批量授权角色失败,可能当前用户组的用户都具有该角色",
	INFO_BATCH_ROLE_DELETE_SUCCESS 		: "批量脱离角色成功",
	INFO_BATCH_ROLE_DELETE_FAILURE 		: "批量脱离角色失败，可能当前用户组的用户都不具有该角色",
	
	INFO_ADD_ROLE						: "授权角色",
	INFO_BATCH_ADD_ROLE					: "批量授权用户角色",
	INFO_DELETE_ROLE					: "脱离角色",
	INFO_BATCH_DELETE_ROLE				: "批量脱离用户角色",
		
	INFO_OPTION_OU						: "请选择组织",
	INFO_OPTION_ROLE					: "请选择角色"
});

$j(document).ready(function (){ 
	$j("#changeBatch").bind("click", function(event) {
			var userGroupid = $j("#userGroupId :selected").val();
			var isEnabled = $j("#isEnabled :selected").val();
			if(userGroupid == '' || userGroupid ==0){
				jumbo.showMsg(loxia.getLocaleMsg("INFO_CHOOSE_USERGROUP"));
				return false;
			}
	    	loxia.asyncXhrPost(
		    	"/wms/json/changeuserisenabled.do",
				{
		    		"userGroupId":userGroupid,
					"isEnabled":isEnabled
				},
				{
					success:function (data) {
						if( data.rs=='true'){
							jumbo.showMsg(loxia.getLocaleMsg("INFO_BATCH_RESULT_SUCCESS"));
						}else{
							jumbo.showMsg(loxia.getLocaleMsg("INFO_BATCH_RESULT_FAILURE"));
						}
						
				   }
				}
			);
});
	
	$j("#addRole").bind("click", function(event) {
        var reftype = $j(this).attr("refattribute");
        $addOrDeleteUserRole(reftype);
	});
	
	$j("#removeRole").bind("click", function(event) { 
		var reftype = $j(this).attr("refattribute");
		$addOrDeleteUserRole(reftype);
	});
	
});
	
var $addOrDeleteUserRole = function(reftype){
        var userGroupid = $j("#userGroupId :selected").val();
        var operationUnitTypeid = $j("#OperationUnitTypeId :selected").val();
        var operationUnitid = $j("#OperationUnitId :selected").val();
        var roleid = $j("#roleId :selected").val();
        
        var usergrouptxt = $j("#userGroupId :selected").text();
        var operationUnittxt = $j("#OperationUnitId :selected").text();
        var roletxt = $j("#roleId :selected").text();
        var operationtxt = null;
        var desc = null;

        if(userGroupid == 0){
			jumbo.showMsg(loxia.getLocaleMsg("INFO_CHOOSE_USERGROUP"));
			return false;
		}else if(operationUnitTypeid == 0){
			jumbo.showMsg(loxia.getLocaleMsg("INFO_CHOOSE_OUTYPE"));
			return false;
		}else if(operationUnitid == 0){
			jumbo.showMsg(loxia.getLocaleMsg("INFO_CHOOSE_OU"));
			return false;
		}else if(roleid == 0){
			jumbo.showMsg(loxia.getLocaleMsg("INFO_CHOOSE_ROLE"));
			return false;
		}
        if(reftype == 'add'){
        	operationtxt = loxia.getLocaleMsg("INFO_ADD_ROLE");
        	desc = loxia.getLocaleMsg("INFO_BATCH_ADD_ROLE");
	       	loxia.asyncXhrPost(
		    	"/wms/json/grantroles.do",
				{
		    		"userGroupId":userGroupid,
					"operationUnitId":operationUnitid,
					"roleId":roleid
				},
				{
					success:function (data) {
						if( data.rs=='true'){
							jumbo.showMsg(loxia.getLocaleMsg("INFO_BATCH_ROLE_ADD_SUCCESS"));
							 var temp ="<tr>" + 
								"<td>"+ operationtxt +"</td>" +
								"<td>"+ usergrouptxt +"</td>" +
								"<td>"+ operationUnittxt +"</td>" +
								"<td>"+ roletxt +"</td>" +
								"<td>"+ desc +"</td>" + 
							"</tr>";
							$j("#rolesInfoTable").append(temp);
						}else{
							jumbo.showMsg(loxia.getLocaleMsg("INFO_BATCH_ROLE_ADD_FAILURE"));
						}
				   }
				}
			);
     	}else if(reftype == 'remove'){
        	operationtxt = loxia.getLocaleMsg("INFO_DELETE_ROLE");
        	desc = loxia.getLocaleMsg("INFO_BATCH_DELETE_ROLE");
	        loxia.asyncXhrPost(
		    	"/wms/json/deleteroles.do",
				{
		    		"userGroupId":userGroupid,
					"operationUnitId":operationUnitid,
					"roleId":roleid
				},
				{
					success:function (data) {
						if( data.rs=='true'){
							jumbo.showMsg(loxia.getLocaleMsg("INFO_BATCH_ROLE_DELETE_SUCCESS"));
							var temp ="<tr>" + 
								"<td>"+ operationtxt +"</td>" +
								"<td>"+ usergrouptxt +"</td>" +
								"<td>"+ operationUnittxt +"</td>" +
								"<td>"+ roletxt +"</td>" +
								"<td>"+ desc +"</td>" + 
							"</tr>";
						$j("#rolesInfoTable").append(temp);
						}else{
							jumbo.showMsg(loxia.getLocaleMsg("INFO_BATCH_ROLE_DELETE_FAILURE"));
						}
				   }
				}
			);
        }
} 

	function selectOperationUnitType(obj) {
		var oneSelect = obj;
		var optId = oneSelect.options[oneSelect.selectedIndex].value;
		loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath") + "/json/findoperationunitbyoutypeid.do", 
			{
				"user.ou.ouType.id" : optId
			}, 
			{
			success : function(data) {
				var rs = data.oulist;
				var rs1 = data.rolelist;
				getRoleList(rs1);
				var twoSelect = $j("#OperationUnitId");
				$j("#OperationUnitId option").remove();
				$j("#OperationUnitId").append(
						"<option value=''>" + loxia.getLocaleMsg("INFO_OPTION_OU") + "</option>");
				$j.each(rs, function(i, item) {
					$j("#OperationUnitId").append(
							"<option value='" + item.id + "'>" + item.name
									+ "</option>");
				});
			}
		});
	}
   
     function getRoleList(rs){
        var twoSelect = $j("#roleId");
		 $j("#roleId option").remove();
		    $j("#roleId").append("<option value=''>" + loxia.getLocaleMsg("INFO_OPTION_ROLE") + "</option>");
		    $j.each(rs, function(i, item) {
		 	$j("#roleId").append("<option value='" + item.id + "'>" + item.name+ "</option>");
			});
    }
