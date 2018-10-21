$j(document).ready(function (){
	window.parent.loxia.unlockPage();
	if($j("#msg").val().length>0){
 		jumbo.showMsg($j("#msg").val(), 5000, "info", window.parent.parent);
    }else{
    	jumbo.showMsg(loxia.getLocaleMsg("MSG_SUCCESS"), 5000, "info", window.parent.parent);
    }   
});

