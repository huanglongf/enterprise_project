$j.extend(loxia.regional['zh-CN'], {

});

function i18(msg, args) {
	return loxia.getLocaleMsg(msg, args);
}

function skuCheck(value, obj) {
	var rs = loxia.syncXhr(window.parent.$j("body").attr("contextpath")
			+ "/findskucodebyskucode.json", {
		skuCode : value
	});
	if (rs && rs.result == "success") {
		$j(obj.element).attr("skuId", rs.skuId);
		var objId = $j(obj.element).attr("id");
		if ($j(obj.element).parent("td").next().find("div").length != 0) {
			$j(obj.element).parent("td").next().find("div").html(rs.skuCost);
		}
		if ($j(obj.element).parent("td").next().find("input").length != 0) {
			$j(obj.element).parent("td").next().find("input").val(rs.skuCost);
		}
		return loxia.SUCCESS;
	} else {
		$j(obj.element).attr("skuId", "");
		if ($j(obj.element).parent("td").next().find("div").length != 0) {
			$j(obj.element).parent("td").next().find("div").html("");
		}
		if ($j(obj.element).parent("td").next().find("input").length != 0) {
			$j(obj.element).parent("td").next().find("input").val("");
		}
		if($j(obj.element).attr("isSkuTrue") == '1'){
			return loxia.SUCCESS;
		} else {
			return "商品未找到";
		}
	}
}

$j(document).ready(function() {
	$j("#tabs").tabs();
	jumbo.loadShopList("selShopId", "id");

	initShopQuery("selShopId", "id");

	$j("#btnSearchShop").click(function() {
		$j("#shopQueryDialog").dialog("open");
	});

	$j("#tab1Create").click(function() {
		if ($j("#selShopId").val() == "") {
			jumbo.showMsg("请选择店铺");
			return;
		}
		var postData = {};
		var total = 0;
		$j("#tab1Table tbody:eq(0) tr").each(function(i,tr){
			var tag = $j(tr).find("td:eq(1) input");
			if(!(loxia.byId(tag).check())){
				jumbo.showMsg("请输入正确的调整商品");
				$j(tag).focus();
				return;
			}
			var cost = $j(tr).find("td:eq(2) input");
			if(!(loxia.byId(cost).check())){
				jumbo.showMsg("请输入正确的价格");
				$j(cost).focus();
				return;
			}
			postData["skuList["+i+"].code"]=tag.val();
			postData["skuList["+i+"].skuCost"]=parseFloat(cost.val()).toFixed(4);
			total += parseFloat(cost.val());
		});
		total = total.toFixed(4)
		if (!loxia.byId("tabs1Qty").check()) {
			jumbo.showMsg("请输入正确的调整数");
			$j("#tabs1Qty").focus();
			return;
		}
		
		if (!$j("#tab1SkuCode").attr("skuId") || $j("#tab1SkuCode").attr("skuId") == "") {
			jumbo.showMsg("请输入拆分商品");
			$j("#tab1SkuCode").focus();
			return;
		}
		if ($j("#tab1Sts").val() == "") {
			jumbo.showMsg("请选择库存状态");
			return;
		}
		if (total != parseFloat($j("#tab1SkuCodeDiv").text())){
			jumbo.showMsg("总成本不等于平均成本");
			return;
		}
		postData["skuCode"]=$j("#tab1SkuCode").val();
		postData["invStsId"]=$j("#tab1Sts").val();
		postData["qty"]=$j("#tabs1Qty").val();
		postData["isGroup"]=true;
		postData["shopId"]=$j("#selShopId").val();
		rs = loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath") + "/createSkuGroup.json",
				postData,
				{
				success:function(data){
					if(data){
						if(data.result=="success"){
							jumbo.showMsg("创建成功!");
						}else if(data.result=="error"){
							jumbo.showMsg(data.message);
						}
					} else {
						jumbo.showMsg("数据操作异常");
					}
				},
				error:function(){jumbo.showMsg("数据操作异常");}//操作数据异常
		});
	});
	
	$j("#tab2Create").click(function() {
		if ($j("#selShopId").val() == "") {
			jumbo.showMsg("请选择店铺");
			return;
		}
		var postData = {};
		var total = 0.0;
		var isTrue = true;
		$j("#tab2Table tbody:eq(0) tr").each(function(i,tr){
			var tag = $j(tr).find("td:eq(1) input");
			if(!(loxia.byId(tag).check())){
				jumbo.showMsg("请输入正确的调整商品");
				$j(tag).focus();
				return;
			}
			var cost = $j(tr).find("td:eq(2) input");
			if(!(loxia.byId(cost).check())){
				jumbo.showMsg("请输入正确的价格");
				$j(cost).focus();
				return;
			}
			postData["skuList["+i+"].code"]=tag.val();
			postData["skuList["+i+"].skuCost"]=parseFloat(cost.val());
			total += parseFloat(cost.val());
			isTrue = false;
		});
		if(isTrue == true){
			jumbo.showMsg("请输入组合的商品");
			return;
		}
		if (!$j("#tab2SkuCode").attr("skuId") || $j("#tab2SkuCode").attr("skuId") == "") {
			jumbo.showMsg("请输入组合后的商品");
			$j("#tab2SkuCode").focus();
			return;
		}
		if ($j("#tab2Sts").val() == "") {
			jumbo.showMsg("请选择库存状态");
			return;
		}
		if (!loxia.byId("tab2Qty").check()) {
			jumbo.showMsg("请输入正确的调整数");
			$j("#tab2Qty").focus();
			return;
		}
		if ($j("#locCode").val() == "") {
			jumbo.showMsg("请输入正确的库位");
			$j("#locCode").focus();
			return;
		}
		postData["skuCode"]=$j("#tab2SkuCode").val();
		postData["invStsId"]=$j("#tab2Sts").val();
		postData["qty"]=$j("#tab2Qty").val();
		postData["isGroup"]=false;
		postData["shopId"]=$j("#selShopId").val();
		postData["locCode"]=$j("#locCode").val();
		postData["skuCost"]=total.toFixed(4);
		rs = loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath") + "/createSkuGroup.json",
				postData,
				{
				success:function(data){
					if(data){
						if(data.result=="success"){
							jumbo.showMsg("创建成功!");
						}else if(data.result=="error"){
							jumbo.showMsg(data.message);
						}
					} else {
						jumbo.showMsg("数据操作异常");
					}
				},
				error:function(){jumbo.showMsg("数据操作异常");}//操作数据异常
		});
	});
});