
$j(document).ready(function(){
		
	$j("#cancel").click(function(){
		var code=$j("#code").val();
		if(code==""||code==null){
			jumbo.showMsg("请填写货箱编码");
		}
		var rs =loxia.syncXhr($j("body").attr("contextpath") + "/auto/cancelAutoBox.do",{"code":code});
		if(rs!=null&&rs.result=="success"){
			jumbo.showMsg("已发送取消请求！");
		}else{
			jumbo.showMsg("取消请求发送失败！");
			
		}
	})
	
});
