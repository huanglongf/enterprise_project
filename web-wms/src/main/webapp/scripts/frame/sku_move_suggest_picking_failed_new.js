var $j = jQuery.noConflict();

$j.extend(loxia.regional['zh-CN'],{
	 
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}
/*
function openDistricts(districts){
	if($j.isArray(districts)){
		var obj=$j("#districtCode");
		for(var i=0,d;(d=districts[i]);i++){
			$j('<option value="'+d.code+'">'+d.code+'</option>').appendTo(obj);
		}
	}
}*/

$j(document).ready(function(){
	loxia.init({debug: true, region: 'zh-CN'});
	//var baseUrl = $j("body").attr("contextpath");
	
	/*
	loxia.asyncXhrGet(baseUrl + "/districtlist.json",{},{
		success:function (data) {
			openDistricts(data);
		   }
		});
	 */

	$j("#expSummary").click(function(){
		var pathDate = '?';
		var staCode = $j("#staCode").val().replace(/^\s+|\s+$/g,"");
		if(staCode != ""){
			pathDate += 'comm.staCode=' + staCode;
		}
		var iframe = document.createElement("iframe");
		if(pathDate != '?'){
			iframe.src = loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/skuReplenishmentSuggestExport.do"+pathDate);
		} else {
			iframe.src = loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/skuReplenishmentSuggestExport.do");
		}
		iframe.style.display = "none";
		$j("#download").append($j(iframe));
	});
	
	$j("#reset").click(function(){
		$j("#queryForm input").val("");
	});
});
 