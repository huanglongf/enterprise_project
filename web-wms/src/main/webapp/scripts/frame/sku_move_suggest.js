var $j = jQuery.noConflict();

$j.extend(loxia.regional['zh-CN'],{
	 
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function openDistricts(districts){
	if($j.isArray(districts)){
		var obj=$j("#districtCode");
		for(var i=0,d;(d=districts[i]);i++){
			$j('<option value="'+d.code+'">'+d.code+'</option>').appendTo(obj);
		}
	}
}

$j(document).ready(function(){
	loxia.init({debug: true, region: 'zh-CN'});
	var baseUrl = $j("body").attr("contextpath");
	loxia.asyncXhrGet(baseUrl + "/districtPickingList.json",{},{
		success:function (data) {
			openDistricts(data);
		   }
		});

	$j("#expSummary").click(function(){
		loxia.lockPage();
		var pathDate = '?';
		var skuCode = $j("#skuCode").val().replace(/^\s+|\s+$/g,"");
		if(skuCode != ""){
			pathDate += 'comm.skuCode='+skuCode;
		}
		var jmCode = $j("#jmCode").val().replace(/^\s+|\s+$/g,"");
		if(jmCode != ""){
			if(pathDate != '?') {
				pathDate += '&comm.jmCode='+jmCode
			}else {
				pathDate += 'comm.jmCode='+jmCode;
			}
		}
		var barCode = $j("#barCode").val().replace(/^\s+|\s+$/g,"");
		if(barCode != ""){
			if(pathDate != '?') {
				pathDate += '&comm.barCode='+barCode
			}else {
				pathDate += 'comm.barCode='+barCode;
			}
		}
		var districtCode = $j("#districtCode").val();
		if(districtCode != ""){
			if(pathDate != '?') {
				pathDate += '&comm.districtCode='+districtCode
			}else {
				pathDate += 'comm.districtCode='+districtCode;
			}
		}
		var warningPre = $j("#warningPre").val().replace(/^\s+|\s+$/g,"");
		if(warningPre != ""){
			if(pathDate != '?') {
				pathDate += '&comm.warningPre='+warningPre
			}else {
				pathDate += 'comm.warningPre='+warningPre;
			}
		}
		var supplierCode = $j("#supplierCode").val().replace(/^\s+|\s+$/g,"");
		if(supplierCode != ""){
			if(pathDate != '?') {
				pathDate += '&comm.supplierCode='+supplierCode
			}else {
				pathDate += 'comm.supplierCode='+supplierCode;
			}
		}
		var iframe = document.createElement("iframe");
		if(pathDate != '?'){
			iframe.src = loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/exportReplenishmentInfo.do"+pathDate);
		} else {
			iframe.src = loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/exportReplenishmentInfo.do");
		}
		iframe.style.display = "none";
		$j("#download").append($j(iframe));
		loxia.unlockPage();
	});
	
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
	});
});
 