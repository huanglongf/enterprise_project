var $j = jQuery.noConflict();


function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}



$j(document).ready(function (){
	
    //加载弹出口区域
	var provinceList = loxia.syncXhrPost($j("body").attr("contextpath") + "/findpopAreaList.json");
	for(var j = 0 ; j < provinceList.areaList.length ; j++){
		$j("<option value='" + provinceList.areaList[j].areaCode + "'>"+ provinceList.areaList[j].areaName +"</option>").appendTo($j("#popCode"));
	}
	
	$j("#containerCode").focus();
	$j("#containerCode").blur(function(){
	});
	$j("#containerCode").keydown(function(evt) {
		if (evt.keyCode === 13) {
			$j("#containerCode").blur();
			$j("#popCode").focus();
		}
	});
	
	$j("#save").click(function(){
		var postData = loxia._ajaxFormToObj("form_query");
		var errorMsg = loxia.validateForm("form_query");
		if(errorMsg.length == 0){
			// 1: 货箱条码不存在，但保存成功。 2：保存失败，货箱条码已绑定。 3:保存成功
		    var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/saveContainPop.json",postData);
		    if(rs.result=='1'){
				jumbo.showMsg("货箱条码不存在，但保存成功！");
		    }else if(rs.result.substr(0,1)=='2'){
				jumbo.showMsg("保存失败，货箱条码已绑定出口："+rs.result.substr(1,rs.result.length));
		    }else if(rs.result=='3'){
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
	
});

