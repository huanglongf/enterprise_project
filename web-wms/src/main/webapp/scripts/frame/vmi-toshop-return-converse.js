/**
 * 
 */
function clear(){
	$j("#owner").val("");
	$j("#file").val("");
}
$j(document).ready(function (){
	jumbo.loadShopList("owner");
	$j("#import").click(function(){
		if($j("#file").val()==''){
			jumbo.showMsg("请选择文件！");
			return;
		}else if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg("请选择正确格式的文件");
			return;
		}
		
		var owner=$j("#owner").val();
		var returnReason =$j("#returnReason").val();
		var resonCode=$j("#returnCode").val();
		if(owner==''||owner==null){
			jumbo.showMsg("请选择店铺");
			return;
		}else if (returnReason==''||returnReason==null){
			jumbo.showMsg("请选择退仓原因");
			return;
		}else if (resonCode==''||resonCode==null){
			jumbo.showMsg("请输入退货原因编码");
			return;
		}
		$j("#importForm").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/vmiReturnImportConverse.do?"+"resonCode="+resonCode+"&owner="+owner+"&returnReason="+returnReason));
		loxia.submitForm("importForm");
	})
	
});