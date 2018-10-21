
$j(document).ready(function (){
    $j("#barcode").focus();
    $j("#barcode").bind("keypress",function(event){
        if(event.keyCode == "13")
        {
            printRfid();
        }
    });

});
function printRfid() {
  var barcode = $("#barcode").val();
  var printcount = $("#printcount").val();

if(barcode == null || barcode.trim() == "" || barcode == undefined){
    $("#tipshow").text("barcode不能为空");
    clearTip(5000);
    return;
}
    if(printcount == null || printcount.trim() == "" || printcount == undefined){
        $("#tipshow").text("打印数量不能为空");
        clearTip(5000);
        return;
    }
    barcode = barcode.trim();
    var rfids = createRfid(printcount,barcode);
    $.ajax({
        type: 'post',
        url: "http://10.8.35.228:8088/printRfid",
        dataType: "json",
        async: false,
        data: {"rfid":rfids.toString()},
        success: function (data) {
            if(data.code=="SUCCESS"){
                $("#tipshow").text("打印成功");
                clearTip(5000);
            }else {
                $("#tipshow").text(data.msg);
                clearTip(5000);
            }
        },
        error:function(data){
            alert("错误");}
    });
}
function createRfid(printcount,barCode) {
    var rfids = [];
    for(var i=0;i<printcount;i++){
        var data = loxia.syncXhrPost($j("body").attr("contextpath")+ "/createRfid.json", {"barCode":barCode});
        if(!data||data.result==null||data.result == "" || data.result == undefined){
            $("#tipshow").text("创建rfID失败");
            clearTip(5000);
            return;
        }
        var  tempRfids = data.result;
        rfids.push(tempRfids);
    }

   return rfids;
}

function clearTip(time) {
    setTimeout(function(){
        $("#tipshow").text("");
    },time);
}