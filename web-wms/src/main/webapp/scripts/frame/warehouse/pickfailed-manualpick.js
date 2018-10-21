$j.extend(loxia.regional['zh-CN'],{
	APPLY_FORM_SELECT : "请选择作业申请单",
	CODE : "作业单号",
	STATUS : "分配状态",
	REFSLIPCODE : "相关单据号",
	INTTYPE : "作业类型名称",
	SHOPID : "店铺",
	LPCODE : "物流服务商简称",
	ISNEEDINVOICE : "是否需要发票",
	CREATETIME : "创建时间"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

$j(document).ready(function(){
	//初始化创建时间
	$j("#fromDate11").val(getDay(-3)+" 00:00:00");
	//加载平台店铺
	var shopLikeQuerys = loxia.syncXhrPost(window.$j("body").attr("contextpath") + "/findshoplistbycompanyid.json");
	$j("#shopLikeQuery").append("<option></option>");
	for(var idx in shopLikeQuerys){
		$j("#shopLikeQuery").append("<option value='"+ shopLikeQuerys[idx].code +"'>"+shopLikeQuerys[idx].name+"</option>");
	}
    $j("#shopLikeQuery").flexselect();
    $j("#shopLikeQuery").val("");
	
  //初始化商品分类
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findCategories.do");
	for(var idx in result){
		$j("<option value='" + result[idx].id + "'>" + result[idx].skuCategoriesName+"</option>").appendTo($j("#categoryId"));
	}
	
	//初始化物流商信息
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransportator.do");
	var mResult = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getTransportatorAfter.json");
	var mCode = "";
	var mName = "";
	for(var mdx in mResult){
		mCode+=mResult[mdx].expCode+",";
		mName+=mResult[mdx].name+",";
	}
	if(mCode!=""){
		$j("<option value='" + mCode.substring(0,mCode.length-1) + "'>"+ mName.substring(0,mName.length-1) +"</option>").appendTo($j("#selLpcode"));
		mtCode = mCode.substring(0,mCode.length-1);
	}
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selLpcode"));
	}
	
	$j("#tbl-staList-query").jqGrid({
		//url:$j("body").attr("contextpath")+"/json/findPickFailedByWhId.do",
		datatype: "json",
		colNames: ["ID","作业单号","相关单据号","分配状态","店铺","物流服务商","时效类型","优先发货城市","平台订单时间","是否COD","优先级","失败次数","失败上限次数","创建时间"],
		colModel: [{name: "id", index: "id", hidden: true},		         
		           {name: "code", index: "code", width: 120,formatter:"linkFmatter",formatoptions:{onclick:"showStaLine"}, resizable: true},
		           {name: "refSlipCode", index: "refSlipCode",width: 120, resizable: true},
		           {name: "cardStatus", index: "cardStatus", width: 60, resizable: true},
		           {name: "ownerOuName", index: "ownerOuName", width: 100, resizable: true},
		           {name: "lpcode", index: "lpcode", width: 80, resizable: true},
		           {name: "transTimeType", index: "transTimeType", width: 120, resizable: true},
		           {name: "city", index: "city", width: 120, resizable: true},
		           {name: "orderCreateTime", index: "orderCreateTime", width: 120, resizable: true},
		           {name: "isCod", index: "isCod", width: 120, resizable: true},
		           {name: "ocpSort", index: "ocpSort", width: 120, resizable: true},
		           {name: "ocpErrorQty", index: "ocpErrorQty", width: 120, resizable: true},
		           {name: "pickStatus", index: "pickStatus", width: 60, resizable: true},
		           {name: "createTime", index: "createTime", width: 120, resizable: true}
		           ],
		caption: "硬分配失败订单列表",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	sortname: 'sta.id',
	    pager: '#pager_query',
		sortorder: "desc", 
		height:'auto',
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var ids = $j("#tbl-staList-query").jqGrid('getDataIDs');
			//获取模糊查询选中的店铺，循环判断勾选弹窗的checkBox
			for(var j = 0 ; j < ids.length ; j++){
				var pickStatus= $j("#tbl-staList-query").getCell(ids[j],"pickStatus"); // 上限次数
				var ocpErrorQty= $j("#tbl-staList-query").getCell(ids[j],"ocpErrorQty"); // 失败次数
				//alert(pickStatus+"--"+ocpErrorQty);
				if(ocpErrorQty== "" || pickStatus == ""){
					//alert(1);
					$j("#tbl-staList-query").jqGrid('setCell',ids[j],4,"未分配"); 
				}else if(ocpErrorQty < pickStatus){
					$j("#tbl-staList-query").jqGrid('setCell',ids[j],4,"未分配"); 
				}else{
					$j("#tbl-staList-query").jqGrid('setCell',ids[j],4,"分配失败"); 
				}
			}
		loxia.initContext($j(this));
	}
	});
	
	//查询按钮功能
	$j("#btn-query").click(function(){
		var postData = loxia._ajaxFormToObj("query-form");
		$j("#tbl-staList-query").jqGrid('setGridParam',{
			url:window.$j("body").attr("contextpath")+"/json/findPickFailedByWhId.do",
			postData:postData,
			page:1
		}).trigger("reloadGrid");
	});
	
	//重置
	$j("#reset").click(function(){
		$j("#filterTable input").val("");
		$j("#query-form input").val("");
		
		$j("#selCity").val("");
		$j("#pickStatus").val("");
		$j("#transTimeType").val("");
		$j("#sortChoose1").val("");
		$j("#selLpcode").val("");
		$j("#categoryId").val("");
		$j("#selIsNeedInvoice").val("");
		$j("#isCod").val("");
		$j("#shopLikeQuery").val("");
		$j("#isPreSale").val("");
		
	});
	
	// 失败再分配
	$j("#pickAgain").click(function(){
		//var postData = loxia._ajaxFormToObj("query-form");
		var postData = {};
		postData["againType"] = "1";
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/failedReusltAgainPick.json",postData);
		if(result && result.msg == "success"){//成功后更新原始查询条件列表
			$j("#tbl-staList-query").jqGrid('setGridParam',{
				url:window.$j("body").attr("contextpath")+"/json/findPickFailedByWhId.do",
				postData:postData,
				page:1
			}).trigger("reloadGrid");
			jumbo.showMsg("执行成功！");
		}else{
			jumbo.showMsg("执行失败！");
		}
	});
	
	// 失败再分配
	$j("#queryAgainPick").click(function(){
		var postData = loxia._ajaxFormToObj("query-form");
		postData["againType"] = "2";
		postData["againSort"] = $j("#sortChoose2").val();
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/failedReusltAgainPick.json",postData);
		if(result&&result.msg == "success"){//成功后更新原始查询条件列表
			$j("#tbl-staList-query").jqGrid('setGridParam',{
				url:window.$j("body").attr("contextpath")+"/json/findPickFailedByWhId.do",
				postData:postData,
				page:1
			}).trigger("reloadGrid");
			jumbo.showMsg("执行成功！");
		}else{
			jumbo.showMsg("执行失败！");
		}
	});
	
	
	function getDay(day){
		var today = new Date();
		var targetday_milliseconds=today.getTime() + 1000*60*60*24*day;
		today.setTime(targetday_milliseconds); //注意，这行是关键代码
		var tYear = today.getFullYear();
		var tMonth = today.getMonth();
		var tDate = today.getDate();
		tMonth = doHandleMonth(tMonth + 1);
		tDate = doHandleMonth(tDate);
		return tYear+"-"+tMonth+"-"+tDate;
		};
		
		function doHandleMonth(month){
				var m = month;
				if(month.toString().length == 1){
					m = "0" + month;
					}
			return m;
		};
	
	
	
});