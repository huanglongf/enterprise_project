<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
</head>
<body>
	<script type="text/javascript">
	$j(document).ready(function (){
		var result = '${result}';
		var msg = '${message}';
		var s = '收货上架导入失败 <br/>';
		for(var x in msg){
			s +=msg[x];
		}
		if(result == 'error'){
			window.parent.loxia.unlockPage();
			window.parent.jumbo.showMsg(s, 5000, "info", window.parent.parent);
		}else if(result == 'success'){
			window.parent.loxia.unlockPage();
			window.parent.jumbo.showMsg("收货上架导入成功！", 5000, "info", window.parent.parent);
			window.parent.importReturn();
		}
	});
	</script>
</body>
</html>