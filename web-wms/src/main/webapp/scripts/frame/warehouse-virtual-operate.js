var $j = jQuery.noConflict();
$j.extend(loxia.regional['zh-CN'],{
	SELECTCOMPANY:"请选择公司",
	SELECTPHYWAREHOUSE:"请选择物理仓",
	SELECENTER:"请选择运营中心",
	WNAME_SHOULD_INPUT:"请输入仓库名称!",
	WCODE_SHOULD_INPUT:"请输入仓库编码",
	OPERSUCCESS:"创建物理仓成功!",
	OPERMSG:"仓库名已经存在!",
	OPERERROR:"操作失败!",
	NOPHYWARE:"请先选择物理仓库，才可以进行双击添加关联",
	EXIST_VIRTUAL:"该仓库已经关联有对应物理仓，不能重复关联!",
	ALREADYADD:"该仓库已于给定物理仓关联，不能重复关联!",
	HAVENOWARE:"没有选择仓库，无法添加关联!",
	EXIST_SOME_VIRTUAL:"其中有的仓库已经有对应的物理仓，添加关联失败",
	ALREADYADDSOME:"部分仓库已和对应物理仓形成关联，不能重复添加",
	HAVENOPHYWARE:"没有选中任何关联信息，无法完成删除操作",
	COULDNOT:"没有物理仓被选择，无效操作",
	SUCCESS:"操作成功",
	FAILED:"操作失败"
});
function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
$j(document).ready(function(){
	$j("#tabs").tabs();
	initCompany();
	initPhy();
	$j("#comsele").change(function(){
		comseleChange(this);
	});
	$j("#censele").change(function(){
		cenChange(this);
	});
	$j("#saveBtn").click(function(){
		valideAndSave();
	});
	$j("#waresele").dblclick(function(){
		var ware = $j("#pwsele").val();
		if(ware==""){
			jumbo.showMsg(loxia.getLocaleMsg("NOPHYWARE"));
			return;
		}
		var name = "["+$j("#comsele").find('option:selected').text()+"]"+
		"["+$j("#censele").find('option:selected').text()+"]"+
		$j("#waresele").find('option:selected').text();
		var val = $j(this).val().toString();
		var valList = $j("#phyware option").val();
		//alert(valList);
		var data = {};
		data["list[0]"]=val;
		loxia.asyncXhrPost(
			window.parent.$j("body").attr("contextpath")+"/json/findOuidByPhyware.do",
			data,
			{
				success:function(data){
					if(data.rs){
						var b=true;
						$j("#phyware option").each(function(){
							if(val == $j(this).val()){
								b=false;
							}
						});
						if(b){
							$j("#phyware").append("<option value='"+val+"'>"+name+"</option>");
						}else{
							jumbo.showMsg(loxia.getLocaleMsg("ALREADYADD"));
						}
					}else{
						jumbo.showMsg(loxia.getLocaleMsg("EXIST_VIRTUAL"));
					}
				}
			}
		);
	});
	$j("#phyware").dblclick(function(){
		$j(this).find('option:selected').remove();
	});
	$j("#addre").click(function(){
		addreClick();
	});
	$j("#dere").click(function(){
		dereClick();
	});
	$j("#pwsele").change(function(){
		initPhyware(this);
	});
	$j("#saveall").click(function(){
		saveAll();
	});
});
//保存物理仓虚拟仓关联关系
function saveAll(){
	var phid = $j("#pwsele").val();
	var obj = $j("#phyware option");
	if(phid==""){
		jumbo.showMsg(loxia.getLocaleMsg("COULDNOT"));
		return;
	}
	var data={};
	data["phWarehouse.id"]=phid;
	if(obj.length>0){
		for(var i=0;i<obj.length;i++){
			data["list["+i+"]"]=$j(obj[i]).val();
		}
	}
	loxia.asyncXhrPost(
		window.parent.$j("body").attr("contextpath")+"/json/saveRelationShip.do",
		data,
		{
			success:function(data){
				if(data.rs){
					jumbo.showMsg(loxia.getLocaleMsg("SUCCESS"));
					initCompany();
					initPhy();
					$j("#phyware option").remove();
					$j("#censele option").remove();
					$j("#waresele option").remove();
				}else{
					jumbo.showMsg(loxia.getLocaleMsg("FAILED"));
				}
			}
		}
	);
}
//点击删除关联是删除选中的关联
function dereClick(){
	var slist = $j("#phyware").find('option:selected');
	if(slist.length==0){
		jumbo.showMsg(loxia.getLocaleMsg("HAVENOPHYWARE"));
		return;
	}
	$j("#phyware").find('option:selected').remove();
}
function addreClick(){
	var slist = $j("#waresele").find('option:selected');
	if(slist.length==0){
		jumbo.showMsg(loxia.getLocaleMsg("HAVENOWARE"));
		return;
	}
	var data = {};
	for(var i=0;i<slist.length;i++){
		data["list["+i+"]"]=$j(slist[i]).val();
	}
	loxia.asyncXhrPost(
			window.parent.$j("body").attr("contextpath")+"/json/findOuidByPhyware.do",
			data,
			{
				success:function(data){
					if(data.rs){
						var b=true;
						$j("#phyware option").each(function(){
							var value = $j(this).val();
							$j("#waresele").find('option:selected').each(function(){
								if($j(this).val()==value){b=false}
							});
						});
						if(b){
							var name1 = "["+$j("#comsele").find('option:selected').text()+"]"+
							"["+$j("#censele").find('option:selected').text()+"]";
							$j("#waresele").find('option:selected').each(function(){
								var name2 = $j(this).text();
								var name = name1+name2;
								$j("#phyware").append("<option value='"+$j(this).val()+"'>"+name+"</option>");
							});
						}else{
							jumbo.showMsg(loxia.getLocaleMsg("ALREADYADDSOME"));
						}
					}else{
						jumbo.showMsg(loxia.getLocaleMsg("EXIST_SOME_VIRTUAL"));
					}
				}
			}
		);
}
function valideAndSave(){
	var name = $j("#newform #wname").val();
	if(ztrim(name)==''){
		jumbo.showMsg(loxia.getLocaleMsg("WNAME_SHOULD_INPUT"));
		return;
	}
	if(name.length>50){
		jumbo.showMsg(loxia.getLocaleMsg("NAMEMAXLENGTH"));
		return;
	}
	var data={};
	data["phWarehouse.name"]=name;
	loxia.asyncXhrPost(
		window.parent.$j("body").attr("contextpath")+"/json/newPhWareHouse.do",
		data,
		{
			success:function(data){
				if(data.rs){
					jumbo.showMsg(loxia.getLocaleMsg("OPERSUCCESS"));
					$j("#wname").val("");
					initPhy();
				}else{
					jumbo.showMsg(loxia.getLocaleMsg("OPERMSG"));
				}
			},
			error:function(){
				jumbo.showMsg(loxia.getLocaleMsg("OPERERROR"));
			}
		}
	);
}
function cenChange(obj){
	$j("#waresele option").remove();
	$j("#waresele").attr("disabled",true);
	var cenid = $j(obj).val();
	loxia.asyncXhrPost(
		window.parent.$j("body").attr("contextpath")+"/json/findWarehouseByCenter.do",
		{"operUnit.id":cenid},
		{
			success:function(data){
				var rs = data.warelist;
				if(rs.length>0){
					$j("#waresele option").remove();
					$j.each(rs,function(i,item){
						$j("#waresele").append("<option value='"+item.id+"'>"+item.name+"</option>");
					});
					$j("#waresele").attr("disabled",false);
					$j("#waresele").attr("aria-disabled",false);
					$j("#waresele").removeClass("ui-loxiaselect-disabled");
					$j("#waresele").removeClass("ui-state-disabled");
				}
			}
		}
	);
}
function comseleChange(obj){
	$j("#censele option").remove();
	$j("#waresele option").remove();
	$j("#waresele").attr("disabled",true);
	var comid = $j(obj).val();
	loxia.asyncXhrPost(
		window.parent.$j("body").attr("contextpath")+"/json/findCenterByCompany.do",
		{"operUnit.id":comid},
		{
			success:function(data){
				var rs = data.cenlist;
				if(rs.length>0){
					$j("#censele option").remove();
					$j("#censele").append("<option value=''>"+i18("SELECENTER")+"</option>");
					$j.each(rs,function(i,item){
						$j("#censele").append("<option value='"+item.id+"'>"+item.name+"</option>");
					});
				}
			}
		}
	);
}
function initCompany(){
	loxia.asyncXhrPost(
		window.parent.$j("body").attr("contextpath")+"/json/findAllCompany.do",
		{},
		{
			success:function(data){
				var rs = data.comlist;
				if(rs.length>0){
					$j("#comsele option").remove();
					$j("#comsele").append("<option value=''>"+i18("SELECTCOMPANY")+"</option>");
					$j.each(rs,function(i,item){
						$j("#comsele").append("<option value='"+item.id+"'>"+item.name+"</option>");
					});
				}
			}
		}
	);
}
function initPhy(){
	loxia.asyncXhrPost(
		window.parent.$j("body").attr("contextpath")+"/json/selectAllPhyWarehouse.do",
		{},
		{
			success:function(data){
				var rs = data.pwarelist;
				if(rs.length>0){
					$j("#pwsele option").remove();
					$j("#pwsele").append("<option value=''>"+i18("SELECTPHYWAREHOUSE")+"</option>");
					$j.each(rs,function(i,item){
						$j("#pwsele").append("<option value='"+item.id+"'>"+item.name+"</option>");
					});
				}
			}
		}
	);
}
function initPhyware(obj){
	$j("#phyware option").remove();
	var id = $j(obj).val();
	loxia.asyncXhrPost(
		window.parent.$j("body").attr("contextpath")+"/json/selectAllWarehouseByPhyId.do",
		{"phWarehouse.id":id},
		{
			success:function(data){
				var rs = data.phywarelist;
				if(rs.length>0){
					$j("#phyware option").remove();
					$j.each(rs,function(i,item){
						$j("#phyware").append("<option value='"+item.id+"'>"+item.name+"</option>");
					});
				}else{
					$j("#phyware option").remove();
				}
			}
		}
	);
}