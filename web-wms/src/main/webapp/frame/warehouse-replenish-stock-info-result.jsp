<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<%@include file="/common/meta.jsp"%>
</head>
<body>
<script type="text/javascript">
$j(document).ready(function (){
	
	var result = '${result}';
	var msg = ${message};
	var s = '库存安全警告数设置导入失败 <br/>';
	if(result == 'success'){
		s = '库存安全警告数设置导入成功 <br/>';
	}
	
	var txt = '库存安全警告数设置导入失败\n';
	for(var x in msg){
		s +=msg[x] + '<br/>';
		txt +=msg[x] + '\n';
	}
	if(result == 'error'){
		window.parent.loxia.unlockPage();
		window.parent.showMsg(s,txt);
	}else if(result == 'success'){
		window.parent.showMsg("库存安全警告数设置导入成功");
		window.parent.loxia.unlockPage();
		window.parent.showMsg(s,"库存安全警告数设置导入成功");
	}
});
</script>
</body>
</html>