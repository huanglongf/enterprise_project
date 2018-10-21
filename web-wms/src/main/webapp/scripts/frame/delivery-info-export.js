$j.extend(loxia.regional['zh-CN'],{
	 
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg, args);
} 
$j(document).ready(function (){
	loxia.init({debug: true, region: 'zh-CN'});
	var baseUrl = $j("body").attr("contextpath");
	
	jumbo.loadTransportator("deliveryid");
	
	$j("#reset").click(function(){
		$j("#showList select,input").val("");		
	});

	$j("#export").click(function(){
		loxia.lockPage();
		var starttime = $j("#startTime").val(); 
		var endtime = $j("#endTime").val(); 
		var deliveryid = $j("#deliveryid").val();
		var ouid = $j("#ouid").val();
		if (starttime != null && endtime != null && starttime>endtime){
			jumbo.showMsg("开始时间不能大于结束时间...");
			loxia.unlockPage();
			return false;
		}
		var postData = {};
		postData["starttime"]=starttime;
		postData["endtime"]=endtime;
		postData["deliveryid"]=deliveryid;
		postData["ouid"]=ouid;
		var rs = loxia.syncXhrPost($j("body").attr("contextpath")+ "/findDeliveryInfoCount.json", postData);
		//判断是否超过30W条数据
		if(rs.msg!="success"){
			jumbo.showMsg("导出数量不能超过30W，导出失败!");
			loxia.unlockPage();	
			return false;
		}else{	
			var url = baseUrl + "/warehouse/deliveryinfoexportforaccount.do?starttime=" + starttime + "&endtime=" + endtime + "&deliveryid=" + deliveryid + "&ouid=" + ouid; 
			$j("#deliveryInfoExport").attr("src",url);
			loxia.unlockPage();	
		}
	});
});
 
