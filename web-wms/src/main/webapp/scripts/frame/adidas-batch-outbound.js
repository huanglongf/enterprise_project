
var i=0;
$j(document).ready(function(){
	var baseUrl = $j("body").attr("contextpath");
	$j("#tab_cancel_sta_list").jqGrid({
		datatype: "json",
		colNames: ["ID","作业单号","箱号","相关单据号","快递单号","物流服务商"],
		colModel: [
		           {name: "id", index: "id", hidden: true},
		           {name: "code", index: "code"},
		           {name: "pgIndex", index: "pgIndex"},
		           {name: "refSlipCode", index: "refSlipCode",width: 110, resizable: true},
		           {name: "transNo", index: "transNo", width: 100, resizable: true},
		           {name: "lpcode", index: "lpcode", width: 100, resizable: true},
	               ],
		caption: "取消单据列表",
		sortname: 'sta.id',
		pager:"#pager",
		multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#pager",	   
		sortorder: "desc",
		viewrecords: true,
 		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
	});
	var batchCode=$j("#batchCode").val();
	var consumables=$j("#consumables").val();
	var weight =$j("#weight").val();
	var postData={};
	postData["consumables"]=consumables;
	postData["batchCode"]=batchCode;
	postData["weight"]=weight;
	//jumbo.bindTableExportBtn("tab_cancel_sta_list",{},baseUrl + "/getCancelStaByBatCheCode.json");
	jumbo.bindTableExportBtn("tab_cancel_sta_list",{},baseUrl + "/getCancelStaByBatCheCode.json",postData);
	$j("#div1").addClass("hidden");

//出库
$j("#outbound").click(function(){
	clickOutbound();
	//jumbo.showMsg("出库完成");
});
//查询取消单
$j("#search").click(function(){
	var batchCode=$j("#batchCode").val();
	var postData={};
	if(batchCode==null||batchCode==''){
		jumbo.showMsg("批次号不能为空");
		return ;
	}
	postData["batchCode"]=batchCode;
	var url = $j("body").attr("contextpath") + "/getCancelStaByBatCheCode.json";
	$j("#tab_cancel_sta_list").jqGrid('setGridParam',{url:url,page:1,postData:postData}).trigger("reloadGrid");
});
	$j("#batchCode").keydown(function(evt) {
		if(evt.keyCode === 13){
			$j("#consumables").focus();
		}
	});
	$j("#consumables").keydown(function(evt) {
		if(evt.keyCode === 13){
			$j("#weight").focus();
		}
	});

});

var isTimmer = false;
var timmer;
//程序暂停
function sleep(n) {
	    var start = new Date().getTime();
	    while(true)  if(new Date().getTime()-start > n) break;
	  }

//查询批次状态,批次执行成功反馈true,否则反馈false
function findPickingListStatus(){
	var batchCode=$j("#batchCode").val();
	var consumables=$j("#consumables").val();
	var weight =$j("#weight").val();
	var postData={};
	postData["consumables"]=consumables;
	postData["batchCode"]=batchCode;
	postData["weight"]=weight;
	//查询批次
	var re = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/findPistListStatus.do", postData);
	if(re.result.indexOf("此批次已经完成")<0){
		//批次未完成
		//执行出库定时查询批次状态，10秒后查询批次状态
//		var t1 =window.setTimeout(findPickingListStatus(),10000); 
		if(!isTimmer){
			timmer = window.setInterval(findPickingListStatus,5000); 
			isTimmer= true;
		}
		return false;
	}else{
		clearInterval(timmer);
		//查询取消清单
		var result={};
		result=re.result;
		$j("#div1").removeClass("hidden");
		document.getElementById('div2').innerHTML=result.split("-")[1];
		//整批完成，解锁页面显示取消单据列表
		var url = $j("body").attr("contextpath") + "/getCancelStaByBatCheCode.json";
		$j("#tab_cancel_sta_list").jqGrid('setGridParam',{url:url,page:1,postData:postData}).trigger("reloadGrid");
		loxia.unlockPage();
		$j("#batchCode").val("");
		$j("#consumables").val("");
		$j("#weight").val("");
		$j("#batchCode").focus();
		loxia.unlockPage();
		jumbo.showMsg("出库完成");
		return true;
	}
}

//执行出库方法
function doPickingOutbound(){
	var batchCode=$j("#batchCode").val();
	var consumables=$j("#consumables").val();
	var weight =$j("#weight").val();
	var postData={};
	postData["consumables"]=consumables;
	postData["batchCode"]=batchCode;
	postData["weight"]=weight;
	//请求出库方法
    var flag=loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/outBoundByPistList.do", postData);
    if("不是耗材"==flag["result"]){
    	jumbo.showMsg("SKU不是耗材");
    	loxia.unlockPage();
		return ;
    }
    if("耗材不存在"==flag["result"]){
    	jumbo.showMsg("SKU不存在");
    	loxia.unlockPage();
		return ;
    }
    if("重量超过100"==flag["result"]){
    	jumbo.showMsg("重量超过100");
    	loxia.unlockPage();
		return ;
    }
    if("重量不能为空"==flag["result"]){
    	jumbo.showMsg("重量不能为空");
    	loxia.unlockPage();
		return ;
    }
}
//点击出库按钮执行
function clickOutbound(){
	var batchCode=$j("#batchCode").val();
	var consumables=$j("#consumables").val();
	var weight =$j("#weight").val();
	var postData={};
	postData["consumables"]=consumables;
	postData["batchCode"]=batchCode;
	postData["weight"]=weight;
	if(batchCode==null||batchCode==''){
			jumbo.showMsg("批次号不能为空");
			return ;
		}else{
			var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/isPistListByOneKey.do", postData);
			if(result!=null&&result.msg=="error1"){
				jumbo.showMsg("不是【单件-一键创批】功能创建出的批次号");
				return ;
			}else if(result!=null&&result.msg=="error2"){
				jumbo.showMsg("批次号不存在");
				return ;
			}else{
				
			}
		}
		if(weight==null||weight==''){
			jumbo.showMsg("重量不能为空");
			return ;
		}else {
			if(isNaN(weight)){
				jumbo.showMsg("重量值非法");
				return ;
			}else{
				
			}
		}
		if(consumables==null||consumables==''){
				jumbo.showMsg("耗材不能为空");
				return ;
		}else{
				//校验是否耗材
				var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/isSkuMaterial.do", postData);
				if(result.msg!="yes"){
					jumbo.showMsg("不是耗材");
					return ;	
				}
		}
	//先查询，如果批次未完成执行出库
	var sts = findPickingListStatus();
	if(!sts){
		loxia.lockPage();
		doPickingOutbound();
	}
}
/*//执行出库
function toOutBound() {
	i++;
	var batchCode=$j("#batchCode").val();
	var consumables=$j("#consumables").val();
	var weight =$j("#weight").val();
	var postData={};
	postData["consumables"]=consumables;
	postData["batchCode"]=batchCode;
	postData["weight"]=weight;
	if(batchCode==null||batchCode==''){
			jumbo.showMsg("批次号不能为空");
			return ;
		}else{
			var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/isPistListByOneKey.do", postData);
			if(result!=null&&result.msg=="error1"){
				jumbo.showMsg("不是【单件-一键创批】功能创建出的批次号");
				return ;
			}else if(result!=null&&result.msg=="error2"){
				jumbo.showMsg("批次号不存在");
				return ;
			}else{
				
			}
		}
		if(weight==null||weight==''){
			jumbo.showMsg("重量不能为空");
			return ;
		}else {
			if(isNaN(weight)){
				jumbo.showMsg("重量值非法");
				return ;
			}else{
				
			}
		}
		if(consumables==null||consumables==''){
				jumbo.showMsg("耗材不能为空");
				return ;
		}else{
				//校验是否耗材
				var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/isSkuMaterial.do", postData);
				if(result.msg!="yes"){
					jumbo.showMsg("不是耗材");
					return ;	
				}
		}
		//查询是否整批完成
		var re = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/findPistListStatus.do", postData);
			//不是整批完成，锁定页面
		if(re.result.indexOf("此批次已经完成")<0){
			loxia.lockPage();
			//请求出库方法
			 loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/outBoundByPistList.do", postData);
			 jumbo.showMsg("正在执行出库,请等待结果,期间请不要操作,以防浏览器崩溃...");
			 sleep(10000);
			 toOutBound();
				 if(i>20){
					 jumbo.showMsg("出库超时，请稍后再试。");
					 return;
				 	}
			result1=re;
		}else{
			var result={};
			result=re.result;
			$j("#div1").removeClass("hidden");
			document.getElementById('div2').innerHTML=result.split("-")[1];
			//整批完成，解锁页面显示取消单据列表
			var url = $j("body").attr("contextpath") + "/getCancelStaByBatCheCode.json";
			$j("#tab_cancel_sta_list").jqGrid('setGridParam',{url:url,page:1,postData:postData}).trigger("reloadGrid");
			loxia.unlockPage();
			$j("#batchCode").val("");
			$j("#consumables").val("");
			$j("#weight").val("");
			$j("#batchCode").focus();
		}
}*/
