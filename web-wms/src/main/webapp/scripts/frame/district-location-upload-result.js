$j(document).ready(function (){
    if($j("#msg").val()=="success"){
    	jumbo.showMsg(loxia.getLocaleMsg("MSG_SUCCESS"),5000, "info", window.parent.parent);
    	var $tabs=window.parent.$j("#district-container").tabs(),index=$tabs.tabs('option', 'selected');
    	$tabs.tabs("url",index,
    		loxia.getTimeUrl(window.parent.$j("body").attr("contextpath")+"/warehouse/locationsofdistrict.do?district.id=" + $j("#districtId").val()));
		$tabs.tabs("load",index);
    }else{
        jumbo.showMsg($j("#msg").val(), 5000, "info", window.parent.parent);
    }    
});