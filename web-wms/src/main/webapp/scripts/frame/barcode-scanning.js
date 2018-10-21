$j.extend(loxia.regional['zh-CN'],{
	VALIDATE_WAREHOUSECHANNELCODE_ECHO 			: "收发货通道编码【{0}】已存在",
	VALIDATE_WAREHOUSECHANNELCODE_INBOUND_ECHO	: "{0}和收货通道编码重复，请重新输入",
	VALIDATE_WAREHOUSECHANNELCODE_OUTBOUND_ECHO	: "{0}和发货通道编码重复，请重新输入",
	
	TAB_INCHANNEL 								: "收货通道",
	TAB_OUTCHANNEL 								: "发货通道",
	
	INFO_SELECT_ROW 							: "请选择要删除的行",
	
	RESULT_SUCCESS_INFO							: "修改成功"
});

function addBarCode(){
	var varCode = $j("#barcode").val();
	var isAdd = false;
	$j("#barCode_tab tbody tr").each(function (i,tag){
		if(varCode == $j(tag).find("td:eq(1) :input").val()){
//			var index = $j(tag).index;
			$j(tag).find("td:eq(2) :input").val(parseInt($j(tag).find("td:eq(2) :input").val())+1);
			isAdd = true;
		}
	});
	if(isAdd == false){
		$j("button[action=add]").click();
		var length = $j("#barCode_tab tbody tr").length-1;
		$j("#barCode_tab tbody tr:eq("+length+") td:eq(1) :input").val(varCode);
		$j("#barCode_tab tbody tr:eq("+length+") td:eq(2) :input").val(1);
	}
}


$j(document).ready(function (){	 
	$j("button[action=add]").addClass("hidden");
	//// 删除按钮
	$j("#barcode").keydown(function(evt){
		if(evt.keyCode === 13){
			addBarCode();
		}
	});
	
	$j("button[action=add]").click(function (){
		
	});
});