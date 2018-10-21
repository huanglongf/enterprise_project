$j("#dialog-chgrole").dialog({title: "选择角色", modal:true, autoOpen: false, width: 450});

function setRole(role){
	$j("#userRoleId").val(role.id);
	$j("#roleId").val(role['roleid']);
	$j("#rolename").text(role['roleName']);
	$j("#orgname").html(role['ouName']);
	$j("#ouId").val(role['ouid']);
}

$j(document).ready(function(){
	$j("#tbl-rolelist").jqGrid({
		url:$j("body").attr("contextpath") + "/userrolelist.json",
		datatype: "json",
		colNames: ["ID","ROLEID","OUID","角色名称","单位简称"],
		colModel: [{name: "id", index: "ur.user.id", hidden: true},
				   {name: "roleid", index: "ur.user.id", hidden: true},
				   {name: "ouid", index: "ur.ou.id", hidden: true},
		           {name: "roleName", index: "ur.role.name asc", width: 120, resizable: true,sortable:false},
		           {name: "ouName", index: "ur.ou.name", width: 240, resizable: true,sortable:false}],
		caption: "角色列表",
		jsonReader : {
			repeatitems: false,
			id:"id"
		},
		postData:{"columns":"id,roleid,ouid,roleName,ouName"},
		sortname:"ur.ou.ouType.id,ur.ou.name",
		loadonce: true,
		rownumbers:true,
		rowNum:-1,
		loadComplete: function(data){
			var roleId = $j("#roleId").val(),ouId=$j("#ouId").val();
			if(roleId&&ouId){
				$j.each($j("#tbl-rolelist").jqGrid("getRowData"),function(i,role){
					if(role['roleid']==roleId&&ouId==role['ouid']){
						$j("#tbl-rolelist").jqGrid("setSelection", parseInt(role.id,10));	
					}
				});
			}		
		},
		onSelectRow:function(id){
			var role=$j("#tbl-rolelist").jqGrid("getRowData",id);
			$j("#currentRole").text(role["roleName"]);
		},
		height: 350
	});
	
	$j("#roleinfo").click(function(evt){
		evt.preventDefault();
//		$j("#tbl-rolelist").jqGrid().setGridParam({url : $j("body").attr("contextpath") + "/userrolelist.json"}).trigger("reloadGrid");		
		$j("#dialog-chgrole").dialog("open");		
		return false;
	});
	
	$j("#dialog-chgrole button.confirm").click(function(evt){
		var id = $j("#tbl-rolelist").jqGrid("getGridParam","selrow");
		if(id){
			setRole($j("#tbl-rolelist").jqGrid("getRowData",id));
			$j("#dialog-chgrole").dialog("close");
//			main.loadMenuTree({"userRoleId":$j("#userRoleId").val()});
			loxia.asyncXhrPost($j("body").attr("contextpath") + "/selectuserrole.json",
				{"roleId":parseInt($j("#roleId").val(),10),"ouId":parseInt($j("#ouId").val(),10)},{
			success:function (data) {
				window.location=loxia.getTimeUrl($j("body").attr("contextpath")+"/main.do");
		   }
		});
		}else{
			$j("#tbl-rolelist").jqGrid("setSelection", $j("#userRoleId").val());
			$j("#dialog-chgrole").dialog("close");
		}
	});
	
	$j("#copysession").click(function(evt){
		evt.preventDefault();
		var win = loxia.openPage($j("body").attr("contextpath") + "/main.do","_blank");
		setTimeout(function(){
			win.moveTo(0,0);
			win.resizeTo(screen.availWidth, screen.availHeight);
		},1);
		return false;
	});
	$j("#exit").click(function(evt){
		if(window.confirm("您确定要退出登录吗?")){
			loxia.asyncXhrPost($j("body").attr("contextpath") + "/logout.do",{"roleId":parseInt($j("#roleId").val(),10),"ouId":parseInt($j("#ouId").val(),10)},{
				success:function (data) {
					//window.location=$j("body").attr("contextpath")+"/j_spring_security_logout";
					//alert($j("body").attr("contextpath")+"/lout");
					window.location=$j("body").attr("contextpath")+"/lout";
				}
			});
			return true;
		}else{
			evt.preventDefault();
			return false;
		}
		return ;
	});
		
});