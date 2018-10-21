$j.extend(loxia.regional['zh-CN'],{
	//OPERATING : "物流交接清单打印中，请稍候...",
	OPERATING : "保存成功，物流交接清单打印中，请稍候...",
	INPUT_FROM_TIME:	"输入开始时间",
	INPUT_END_TIME:		"输入结束时间"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}


$j(document).ready(function (){
	var baseUrl = $j("body").attr("contextpath");
	var result = loxia.syncXhrPost(baseUrl + "/getTransportator.json");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selTrans"));
	}
	//根据运营中心仓库查询
	var resultopc = loxia.syncXhrPost($j("body").attr("contextpath")+"/selectbyopc.json");
	for(var idx in resultopc.warelist){
		$j("<option value='" + resultopc.warelist[idx].id + "'>"+ resultopc.warelist[idx].name +"</option>").appendTo($j("#selTransOpc"));
	}
	var result = loxia.syncXhrPost(baseUrl + "/queryEntryIsOpc.json");
	var s = result["flag"];
	if(s==true){
		$j("#warehouse").removeClass("hidden");
		$j("#warehouselable").removeClass("hidden");
	}else{
		$j("#warehouse").addClass("hidden");
		$j("#warehouselable").addClass("hidden");
	}
	var pD=function(s){
		var dt=s.split(/ /);
		var d=dt[0].split(/-/);
		var t;
		if(dt[1]){
			t=dt[1].split(/:/);
			t.push(0);
			t.push(0);
		}else{
			t=[0,0,0];
		}
		return new Date(d[0],d[1]-1,d[2],t[0],t[1],t[2]);
	};
	var pS=function(d){
		var Y=d.getFullYear();
		var M=d.getMonth()+1;
		(M<10)&&(M='0'+M);
		var D=d.getDate();
		(D<10)&&(D='0'+D);
		var h=d.getHours();
		(h<10)&&(h='0'+h);
		var m=d.getMinutes();
		(m<10)&&(m='0'+m);
		var s=d.getSeconds();
		(s<10)&&(s='0'+s);
		return Y+'-'+M+'-'+D+' '+h+':'+m+':'+s;
	};
	//导出按钮
	$j("#btnExport").click(function(){
		var fromDate = $j.trim($j("#fromDate").val());
		var endDate = $j.trim($j("#endDate").val());
		var d1=pD(fromDate);
		var d2=pD(endDate);
		var s1=pS(d1);
		var s2=pS(d2);
		if(s1>s2){
			jumbo.showMsg("开始时间不能大于结束时间");
			$j("#fromDate").focus();
			return false;
		}
		if((d2-d1)/(60*60*1000) > 12){
			jumbo.showMsg("时间跨度最大为12小时");
			$j("#fromDate").focus();
			return false;
		}
		var lpCode = $j("#selTrans").val();
		var whId = $j("#selTransOpc").val();
		if (!fromDate){
			jumbo.showMsg(i18("INPUT_FROM_TIME"));
			$j("#fromDate").focus();
			return false;
		}
		if (!endDate){
			jumbo.showMsg(i18("INPUT_END_TIME"));
			$j("#endDate").focus();
			return false;
		} 
		//var postData=loxia._ajaxFormToObj("plForm");
		//var url=loxia.syncXhr($j("body").attr("contextpath") + "/warehouse/findHandOverListExportReturnOrderRecord.do",postData);
		var url = $j("body").attr("contextpath") + "/warehouse/findHandOverListExportReturnOrderRecord.do?fromDate=" + fromDate + "&endDate=" + endDate +"&lpCode=" + lpCode+ "&whId=" + whId;
		$j("#plForm").attr("action", url);
		loxia.submitForm("plForm");
	});
	
	
	$j("#query").click(function(){
		var postData = loxia._ajaxFormToObj("query_form");
		$j("#tbl-hand-over-list").jqGrid('setGridParam',{url: window.$j("body").attr("contextpath") + "/json/handoverlist.json",page:1,postData:postData}).trigger("reloadGrid");
	});
});