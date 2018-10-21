$j.extend(loxia.regional['zh-CN'],{
	NONODESELECT:"请选择分类节点再进行操作！",
	NAMESHOULDINPUT:"分类名字必须输入！"
	
});
function i18(msg, args){
	return loxia.getLocaleMsg(msg, args);
}
function init(){
	$j("#skuCategoryTree").tree({
		url : $j("body").attr("contextpath")+"/skuCategoryTree.json",
		onClick : function(node){
		$j("#sname").val(node.attributes.name);
		$j("#sid").val(node.id);
		$j("#sid1").val(node.id);
		$j("#sqty").val(node.attributes.cqty);
		$j("#editSkuMaxLimit").val(node.attributes.skuLimit);
		if(node.attributes.tree2 == "1"){
			$j("#addSkuMaxLimit").removeClass("hidden");
			$j("#addSkuMaxLable").removeClass("hidden");
		}else{
			$j("#addSkuMaxLimit").addClass("hidden");
			$j("#addSkuMaxLable").addClass("hidden");
		}
		if(node.attributes.tree3 == "1"){
			$j("#editSkuMaxLable").removeClass("hidden");
			$j("#editSkuMaxLimit").removeClass("hidden");
		}else{
			$j("#editSkuMaxLable").addClass("hidden");
			$j("#editSkuMaxLimit").addClass("hidden");
		}
		if(node.attributes.able){
			$j("#sispick option:first").attr("selected",true);
		}else{
			$j("#sispick option:last").attr("selected",true);
		}
	}
	});
}
$j(document).ready(function(){
	init();
	$j("#save").click(function(){
		var id = $j("#sid").val();
		var name = $j("#sname").val();
		var sqty = $j("#sqty").val();
		if(id == ""){
			jumbo.showMsg(i18("NONODESELECT"));
			return;
		}
		if(name == ""){
			loxia.tip($j("#sname"),i18("NAMESHOULDINPUT"));
			return;
		}
		if(sqty != ""){
			if(!loxia.byId("sqty").check()){
				loxia.tip($j("#sqty"),"请填写正确数量！");
				return;
			}
			if($j("#sqty").val()<0){
				loxia.tip($j("#sqty"),"请填写正确数量！");
				return;
			}
		}
		var postData = loxia._ajaxFormToObj("editForm");
		loxia.asyncXhrPost(
			$j("body").attr("contextpath") + "/updateSkuCategory.json",
			postData,
			{
			success:function (data) {
				if(data.rs){
					jumbo.showMsg("分类信息修改成功");
					init();
					$j("#editDiv input").val("");
				}else{
					if(data.error&&data.error=="1"){
						jumbo.showMsg("分类信息修改失败,修改后的名字不能与已有分类名字重复");
						return;
					}
					jumbo.showMsg("分类信息修改失败");
				}
			}
		});
	});
	$j("#saveson").click(function(){
			var id = $j("#sid1").val();
			var name = $j("#sonsname").val();
			var sqty = $j("#sonqty").val();
			if(id == ""){
				jumbo.showMsg(i18("NONODESELECT"));
				return;
			}
			if(name == ""){
				loxia.tip($j("#sonsname"),i18("NAMESHOULDINPUT"));
				return;
			}
			if(sqty != ""){
				if(!loxia.byId("sqty").check()){
					loxia.tip($j("#sqty"),"请填写正确数量！");
					return;
				}
				if($j("#sqty").val()<0){
					loxia.tip($j("#sqty"),"请填写正确数量！");
					return;
				}
			}
			var postData = loxia._ajaxFormToObj("addForm");
			loxia.asyncXhrPost(
				$j("body").attr("contextpath") + "/saveNewSkuCategory.json",
				postData,
				{
				success:function (data) {
					if(data.rs){
						jumbo.showMsg("新建分类成功!");
						init();
						$j("#editDiv input").val("");
					}else{
						if(data.error&&data.error=="1"){
							jumbo.showMsg("新建分类名称不能与已有分类名重复!");
							return;
						}
						jumbo.showMsg("新建分类失败!");
					}
				}
			});
	});
	$j("#remove").click(function(){
		var id = $j("#sid").val();
		if(id == ""){
			jumbo.showMsg(i18("NONODESELECT"));
			return;
		}
		var postData = loxia._ajaxFormToObj("editForm");
		loxia.asyncXhrPost(
			$j("body").attr("contextpath")+"/removeCategoryById.json",
			postData,
			{
				success:function(data){
					if(data.rs&&data.rs=="true"){
						jumbo.showMsg("删除分类成功!");
						init();
						$j("#editDiv input").val("");
					}else{
						jumbo.showMsg("当前分类或其子分类中包含有商品,该分类暂时不能删除!");
					}
				}
			}						
		);
	});
});