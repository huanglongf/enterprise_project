$j(document).ready(function (){
//    loxia.init({debug: true, region: 'zh-CN'});
    if($j("#msg").val()=="success"){
        window.parent.location=loxia.getTimeUrl($j("body").attr("contextpath")+"/warehouse/purchaseentry.do");
    }else{
        jumbo.showMsg($j("#msg").val(), 5000, "info", window.parent.parent);
    }    
});