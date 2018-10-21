var _g={}
/**
 * 
 * @param {Object} target eg:win.parent
 * @param {Object} msg
 */
_g.showMsg=function(target,msg){
	target.$j("#msg").html(msg);
	target.$j("div.ui-tabs-panel:not(.ui-tabs-hide) .showmsg a").trigger("click");
	setTimeout(function(){target.$j("#msg").hide();},3000);
}
_g.contextPath=function(){
	return $j("#contextPath").val();
}