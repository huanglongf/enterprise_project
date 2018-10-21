$j.extend(loxia.regional['zh-CN'],{
	VALIDATE_WAREHOUSECHANNELCODE_ECHO 			: "收发货通道编码【{0}】已存在",
	VALIDATE_WAREHOUSECHANNELCODE_INBOUND_ECHO	: "{0}和收货通道编码重复，请重新输入",
	VALIDATE_WAREHOUSECHANNELCODE_OUTBOUND_ECHO	: "{0}和发货通道编码重复，请重新输入",
	
	TAB_INCHANNEL 								: "收货通道",
	TAB_OUTCHANNEL 								: "发货通道",
	
	INFO_SELECT_ROW 							: "请选择要删除的行",
	
	RESULT_SUCCESS_INFO							: "修改成功"
});

function warehousechannelFormValidate(oForm){
	var errorMsg = [], codeMap = {};
	$j("input.code",oForm).each(function(){
		var code = $j(this).val();
		if(code in codeMap){
			errorMsg.push(loxia.getLocaleMsg("VALIDATE_WAREHOUSECHANNELCODE_ECHO",code));
		}else
			codeMap[code] = code;
	});
	if(errorMsg.length == 0) return loxia.SUCCESS;
	return errorMsg;
}

$j(document).ready(function (){	 
	var $tabs = $j("#wh-channel-container").tabs({
		cache: true,
		load: function( event, ui ) {
			loxia.initContext($j(ui.panel));						
		}
	});		
	$tabs.tabs("add",$j("body").attr("contextpath")+"/warehouse/inChannelType.do?type=1", loxia.getLocaleMsg("TAB_INCHANNEL"));
	$tabs.tabs("add",$j("body").attr("contextpath")+"/warehouse/outChannelType.do?type=2", loxia.getLocaleMsg("TAB_OUTCHANNEL"));

	//// 删除按钮
	$j("button[action=delete]").live("click",function(){ 
	//$j("div.tbl-action-bar :last-child").live("click",function(){
		var uncheck=0;
		for(var i=1;i<=$j("input[type=checkbox]:gt(0)").length;i++){
			if(!$j("input[type=checkbox]:eq("+i+")").is(":checked")){
				uncheck++;
			}
		}
		if(uncheck == $j("input[type=checkbox]:gt(0)").length){
			jumbo.showMsg(loxia.getLocaleMsg("INFO_SELECT_ROW"));
		}
	});
	
	//// form提交
	$j("button[refForm]").live("click",function(){
		var formId = $j(this).attr("refForm");
		var errorMsg = loxia.validateForm(formId);
		if(errorMsg.length == 0){
 				var url;
 				if(formId == 'warehousechannel1Form'){
					url = window.$j("body").attr("contextpath") + "/json/addinchannel.json";
				}else{
					url = window.$j("body").attr("contextpath") + "/json/addoutchannel.json";
				}
				
				$j.each($j(".ui-loxia-table table tr.add"),function(i,item){
					$j(this).find(":input").each(function(){
						$j(this).attr("name",$j(this).attr("name").replace(/\(.*\)/ig,"["+i+"]"));
					});
				});
				loxia.asyncXhrPost(url,
				loxia._ajaxFormToObj(formId),
				{
					success:function (data) {
						if(data != null){
							if(data.result != loxia.getLocaleMsg("RESULT_SUCCESS_INFO")){
								var echocode="";
								var channeltype="";
					            $j.each(data.result, function(i, item) {
									echocode += "【" + item.code + "】 ";
									channeltype = item.intType;
								});
					            if(channeltype==1) jumbo.showMsg(loxia.getLocaleMsg("VALIDATE_WAREHOUSECHANNELCODE_OUTBOUND_ECHO",echocode)); 
					            else if(channeltype==2) jumbo.showMsg(loxia.getLocaleMsg("VALIDATE_WAREHOUSECHANNELCODE_INBOUND_ECHO",echocode));
							}else{
								jumbo.showMsg(data.result);
								$tabs.tabs("load",$tabs.tabs("option","selected"));
							}
						}
					}
				});
		}else{
			jumbo.showMsg(errorMsg);
		}
	});
});