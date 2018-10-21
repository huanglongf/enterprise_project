$j.extend(loxia.regional['zh-CN'],{
	CORRECT_FILE_PLEASE:"请选择正确的Excel导入文件"
});
 
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function getFormDate(tb){
	/*var postDate = {};
	$j(tb + " tbody tr:gt(0)").each(function (i,tr){
		postDate["icclist["+i+"].paramId"] =  $j(tr).find("td:eq(2)").html();
		postDate["icclist["+i+"].lineType"] = $j(tr).find("td:eq(3)").html();
	});	 
	postDate["remark"] = $j("#txtRmk").val();
	return postDate;*/
}

function clear(){
	$j("#tab_2 select").val("");
	$j("#tab_2 input").val("");
}

$j(document).ready(function (){
	$j("#tabs").tabs();

	jumbo.loadShopList("ownerout","id");
	jumbo.loadShopList("ownerin","id");
	
	$j("#ownerout").flexselect();
	$j("#ownerout").val("");
	$j("#ownerin").flexselect();
	$j("#ownerin").val("");

	
	$j("#import").click(function(){
		var ownerid = $j("#ownerout").val(); 
		var addiownerid = $j("#ownerin").val();
		var invstatus = $j("#invstatus").val();
		var errs = [];
		if(ownerid == ''){errs.push("请选择转出店铺");}
		if(addiownerid == ''){errs.push("请选择转入店铺");}
		if(invstatus == ''){errs.push("请选择库存状态");}
		if(addiownerid==ownerid){errs.push("转出、转入店铺不能为同一家店铺，请重新选择");}
		
		if(errs.length>0) {
			jumbo.showMsg(errs);
			return;
		}
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));//请选择正确的Excel导入文件
			return;
		}
		loxia.lockPage();
		$j("#importForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/vmitransferimport.do?ownerid="+ownerid+"&addiownerid="+addiownerid+"&invstatusId="+invstatus));
		loxia.submitForm("importForm");
		/**
		$j("#importForm").submit();**/
	});
});

