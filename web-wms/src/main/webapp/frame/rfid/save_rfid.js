

$j(document).ready(function (){
    $j("#barcode").focus();
    $j("#barcode").bind("keypress",function(event){
        if(event.keyCode == "13")
        {
            barcodeNext();
        }
    });
    $j("#okcode").bind("keypress",function(event){
        if(event.keyCode == "13")
        {
            getRfid();
        }
    });
});
var param={};
function barcodeNext() {
    $j("#okcode").focus();
}
function getRfid() {
    $("#rf1").val("");
    $("#rf2").val("");

    var rfidSession = $("#rfidSession").val();

    if(!rfidSession){
        $("#tipshow").text("请先绑定rfidReader设备");
        setTimeout(function(){
            $("#tipshow").text("");
        },5000);
         return;
    }
        $.ajax({
        type: 'post',
        url: "http://10.8.35.228:8088/getRfid",
        dataType: "json",
        async: false,

        data: {"hostCode":rfidSession},
        success: function (data) {
        
            if(data.code=="SUCCESS"){
                $("#rf1").val(data.rfid[0]);
                $("#rf2").val(data.rfid[1]);
            }else {
                $("#tipshow").text(data.msg.toString());
                setTimeout(function(){
                    $("#tipshow").text("");
                },5000);
            }
        },
        error:function(data){
            alert("错误");}
    });
     
}
function saveRfid() {
     var barCode =$("#barcode").val();
     if(!barCode){
         $("#tipshow").text("请扫描barcode");
         setTimeout(function(){
             $("#tipshow").text("");
         },5000);
         return;
     }
     param.barCode=barCode;
     param.rf1=$("#rf1").val();
     param.rf2=$("#rf2").val();

     loxia.asyncXhrPost($j("body").attr("contextpath") + "/json/saveRfid.json",param,{
        success:function(data){
            $("#tipshow").text(data.result.msg);
            setTimeout(function(){
                $("#tipshow").text("");
            },5000);
        },
         error:function(){jumbo.showMsg("网络连接异常");}
    });
}
function displayRfid(data) {
    if(data.length!=2){
        $("#tipshow").text(data.msg);
        setTimeout(function(){
            $("#tipshow").text("");
        },5000);
        return;
    }
    $("#rf1").val(data[0]);
    $("#rf2").val(data[1]);
}
function reset() {


    $("#barcode").val("");
    $("#rf1").val("");
    $("#rf2").val("");
    $("#okcode").val("");
    $("#hostcode").val("");
}