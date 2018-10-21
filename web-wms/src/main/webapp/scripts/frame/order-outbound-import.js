
/**
 * 
 */

$j(document).ready(function (){
	$j("#import").click(function(){
		if($j("#file").val()==''||$j("#file").val()==null){
			jumbo.showMsg("请选择文件！");
			return;
		}else if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg("请选择正确格式的文件");
			return;
		}
		$j("#importForm").attr("action",$j("body").attr("contextpath") + "/warehouse/orderOutBoundDataToLmis.do");
		loxia.submitForm("importForm");
	});
	
});



