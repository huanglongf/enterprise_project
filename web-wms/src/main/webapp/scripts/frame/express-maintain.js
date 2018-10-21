$j.extend(loxia.regional['zh-CN'],{
	CORRECT_FILE_PLEASE : "请选择正确的Excel导入文件"
//	CODE : "配货批次号"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}
var maId =""; // 全局变量 快递ID
var tranId = ""; // 全局变量 服务ID
function showDetail(obj){
	$j("#div1").addClass("hidden");
	$j("#div2").removeClass("hidden");
	//加载数据
	var tr = $j(obj).parents("tr[id]");
	var transId=tr.attr("id"); // 行ID
	maId = transId;
	var name= $j("#tbl-tmt-list").getCell(transId,"name");
	var expCode= $j("#tbl-tmt-list").getCell(transId,"expCode");
	var platformCode= $j("#tbl-tmt-list").getCell(transId,"platformCode");
	var code= $j("#tbl-tmt-list").getCell(transId,"code");
	var fullName= $j("#tbl-tmt-list").getCell(transId,"fullName");
	var statusName= $j("#tbl-tmt-list").getCell(transId,"statusName");
	var isSupportCodStr= $j("#tbl-tmt-list").getCell(transId,"isSupportCodStr");
	var jasperOnLine= $j("#tbl-tmt-list").getCell(transId,"jasperOnLine");
	var jasperNormal= $j("#tbl-tmt-list").getCell(transId,"jasperNormal");
	var isAfter= $j("#tbl-tmt-list").getCell(transId,"isAfter");
	var isReg= $j("#tbl-tmt-list").getCell(transId,"isReg");
	var k3Code= $j("#tbl-tmt-list").getCell(transId,"k3Code");
	if(isSupportCodStr == "是"){
		isSupportCodStr = "1";
	}else{
		isSupportCodStr = "0";
	}
	if(statusName == "可用"){
		statusName = "1";
	}else{
		statusName = "0";
	}
	if(isAfter == "是"){
		isAfter = "1";
	}else{
		isAfter = "0";
	}
	if(isReg == "是"){
		isReg = "1";
	}else{
		isReg = "0";
	}
	$j("#name").attr("value",name);
	$j("#expCode").attr("value",expCode);
	$j("#platformCode").attr("value",platformCode);
	$j("#code").attr("value",code);
	$j("#fullName").attr("value",fullName);
	$j("#statusName").attr("value",statusName);
	$j("#selTrans4").attr("value",isSupportCodStr);
	$j("#jasperOnLine").attr("value",jasperOnLine);
	$j("#jasperNormal").attr("value",jasperNormal);
	$j("#selTrans6").attr("value",isAfter);
	$j("#selTrans5").attr("value",isReg);
	$j("#k3Code").attr("value",k3Code);
	
	var baseUrl = $j("body").attr("contextpath");
	var transPortUrls = baseUrl + "/findTransServiceById.json?transId="+transId;
	$j("#tbl-transport-service").jqGrid('setGridParam',{url:transPortUrls,datatype: "json",}).trigger("reloadGrid");
}

function showDetail2(obj){
	$j("#file").val("");
	$j("#dialog_addTrans").dialog("open");
	var tr = $j(obj).parents("tr[id]");
	var transSubId=tr.attr("id"); // 行ID
	tranId = transSubId;
	var name= $j("#tbl-transport-service").getCell(transSubId,"name");
	var serviceType= $j("#tbl-transport-service").getCell(transSubId,"serviceType");
	var timeTypes= $j("#tbl-transport-service").getCell(transSubId,"timeTypes");
	var statuss= $j("#tbl-transport-service").getCell(transSubId,"statuss");
	if(serviceType == "普通"){
		serviceType= 1;
	}else if(serviceType == "航空"){
		serviceType= 4;
	}else{
		serviceType= 7;
	}
	if(timeTypes == "普通"){
		timeTypes= 1;
	}else if(timeTypes == "及时达"){
		timeTypes= 3;
	}else if(timeTypes == "当日"){
		timeTypes= 5;
	}else{
		timeTypes= 6;
	}
	if(statuss == "可用"){
		statuss = "1";
	}else{
		statuss = "0";
	}
	$j("#serviceName").attr("value",name);
	$j("#selTrans7").attr("value",serviceType);
	$j("#selTrans8").attr("value",timeTypes);
	$j("#selTrans9").attr("value",statuss);
	
	var baseUrl = $j("body").attr("contextpath");
	var transPortUrls = baseUrl + "/findTransServiceAreaById.json?transId="+tranId;
	$j("#tbl-transport-service-area").jqGrid('setGridParam',{url:transPortUrls,datatype: "json",}).trigger("reloadGrid");
}

$j(document).ready(function (){
	$j("#dialog_addTrans").dialog({title: "新增物流服务", modal:true,closeOnEscape:false, autoOpen: false, width: 610});//弹窗属性设置
	$j("#div2").addClass("hidden");
	//查询
	$j("#search").click(function(){
		 var postData = loxia._ajaxFormToObj("form_query");  
			$j("#tbl-tmt-list").jqGrid('setGridParam',{
				url:$j("body").attr("contextpath")+"/findTransportatorListByAll.json",postData:postData}).trigger("reloadGrid");
		});
	
	//重置
	$j("#reset").click(function(){
		$j("#form_query input,#form_query select").val("");
	});
	
	$j("#save").click(function(){
		var postData = loxia._ajaxFormToObj("form_info");
		if($j("#selTrans4").val()=='1'){
			postData["ma.isSupportCod"]=true;
		}
		postData["ma.id"] = maId;
		var errorMsg = loxia.validateForm("form_info");
		if(errorMsg.length == 0){
		    var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/saveMaTransport.json",postData);
		    if(rs.result=='success'){
		    	maId = rs.maId;
				jumbo.showMsg("操作成功！");
		    }else {
		    	if(rs.msg != null){
					jumbo.showMsg(rs.msg);
				}else{
					jumbo.showMsg("操作失败！");
				}
		    }
			
		}else{
			jumbo.showMsg(errorMsg);
		}
	});
	
	$j("#addSub").click(function(){
		if(maId == ""){
			jumbo.showMsg("请先新增物流信息");
			return;
		}
		tranId = "ADD";
		$j("#form_info2 input,#form_info2 select").val("");
		$j("#dialog_addTrans").dialog("open");
		$j("#file").val("");
		$j("#tbl-transport-service-area").jqGrid("clearGridData");
		
	});
	
	$j("#saveSub").click(function(){
		if($j("#file").val() !=""){
			if(!/^.*\.xls$/.test($j("#file").val())){
				jumbo.showMsg("请选择正确的Excel导入文件");
				return;
			}
		}
		var serviceName = $j("#serviceName").val();
		var serviceType = $j("#selTrans7").val();
		var timeType = $j("#selTrans8").val();
		var statuss = $j("#selTrans9").val();
//		alert(maId+"--"+tranId+"--"+serviceName+"--"+serviceType+"--"+timeType+"--"+statuss); transServiceImport
		loxia.lockPage();
		var rs = loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/transServiceImport.do?maId="+maId+"&tranId="+tranId+"&serviceName="+serviceName+"&serviceType="+serviceType+"&timeType="+timeType+"&statuss="+statuss);
		$j("#importForm").attr("action",rs);
		loxia.submitForm("importForm");
		
		$j("#dialog_addTrans").dialog("close");
		
		setTimeout(function(){
			var baseUrl = $j("body").attr("contextpath");
			var transPortUrls = baseUrl + "/findTransServiceById.json?transId="+maId;
			$j("#tbl-transport-service").jqGrid('setGridParam',{url:transPortUrls,datatype: "json",}).trigger("reloadGrid");
		},800);
	});
	
	$j("#closeSub").click(function(){
		$j("#dialog_addTrans").dialog("close");
	});
	
	$j("#newTrans").click(function(){
		$j("#div1").addClass("hidden");
		$j("#div2").removeClass("hidden");
		maId = "";
	});
	
	$j("#tbl-tmt-list").jqGrid({
		url:$j("body").attr("contextpath")+"/findTransportatorListByAll.json",
		datatype: "json",
		colNames: ["ID","物流商名称","内部平台对接编码","K3编码","COD","外部平台对接编码","物流商编码","物流商全称","状态","是否支持COD","电子面单模板","普通电子面单","是否后置","是否分区域","创建时间","最后修改时间"],
		colModel: [
		           {name:"id", index: "id", hidden: true},	
		           {name:"name",index:"name", width:120,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, resizable: true,sortable:false},
		           {name:"expCode",index:"expCode", width:120,hidden: true},
		           {name:"k3Code",index:"k3Code", width:120,hidden: true},
		           {name:"codMaxAmt",index:"codMaxAmt", width:120,hidden: true},
		           {name:"platformCode",index:"platformCode", width:120},
		           {name:"code",index:"code", width:100},
		           {name:"fullName",index:"fullName", width:120},
		           {name:"statusName",index:"statusName", width:100},
		           {name:"isSupportCodStr",index:"isSupportCodStr", width:100},
		           {name:"jasperOnLine",index:"jasperOnLine", width:130},
		           {name:"jasperNormal",index:"jasperNormal", width:130},
		           {name:"isAfter",index:"isAfter", width:100},
		           {name:"isReg",index:"isReg", width:100},
		           {name:"createTime",index:"createTime", width:150},
		           {name:"lastModifyTime",index:"lastModifyTime", width:150},
		           ],
		caption: "物流商列表",
		sortname: 'createTime',
	   	sortorder: "desc",
	   	multiselect: false,
	    rowNum: -1,
	   	height:jumbo.getHeight(),
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#tbl-transport-service").jqGrid({
		colNames: ["ID","名称","服务类型","时效类型","状态","创建时间"],
		colModel: [
							{name: "id", index:"id",hidden: true,width:80,resizable:true},
							{name:"name",index:"name", width:90,formatter:"linkFmatter",formatoptions:{onclick:"showDetail2"}, resizable: true,sortable:false},
							{name:"serviceType",index:"serviceType",width:90,resizable:true},
							{name:"timeTypes",index:"timeTypes",width:90,resizable:true},
							{name:"statuss",index:"statuss",width:90,resizable:true},
							{name:"createTime",index:"createTime",width:150,resizable:true}
				 		],
     	caption: "物流服务",
    	sortname: 'id',
    	sortorder: "desc",
    	multiselect: false,
    	height:"auto",
    	width:500,
    	rowNum: -1,
    	rownumbers:true,
		jsonReader: {repeatitems : false, id: "0" }
	});
	
	$j("#tbl-transport-service-area").jqGrid({
		colNames: ["ID","省","市","区"],
		colModel: [
							{name: "id", index:"id",hidden: true,width:80,resizable:true},
							{name:"province",index:"province", width:90,resizable:true},
							{name:"city",index:"city",width:90,resizable:true},
							{name:"district",index:"district",width:90,resizable:true}
				 		],
     	caption: "物流服务配送范围",
    	sortname: 'id',
    	sortorder: "desc",
    	multiselect: false,
    	height:"auto",
    	width:500,
    	rowNum: -1,
    	rownumbers:true,
		jsonReader: {repeatitems : false, id: "0" }
	});
	
	$j("#back").click(function(){
		$j("#div1").removeClass("hidden");
		$j("#div2").addClass("hidden");
		 var postData = loxia._ajaxFormToObj("form_query");  
			$j("#tbl-tmt-list").jqGrid('setGridParam',{
				url:$j("body").attr("contextpath")+"/findTransportatorListByAll.json",postData:postData}).trigger("reloadGrid");
	});
	
	
});
	


