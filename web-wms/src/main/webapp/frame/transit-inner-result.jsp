<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<%@include file="/common/meta.jsp"%>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<script type="text/javascript">
$j(document).ready(function (){
	var result = '${result}';
	var msg = ${message};
	var s = '库内移动数据文件导入失败 \n';
	for(var x in msg){
		s +=msg[x] + '\n';
	}
	if(result == 'error'){
		window.parent.showMsg(s);
		window.parent.loxia.unlockPage();
	}else if(result == 'success'){
		window.parent.showMsg("库内移动数据文件导入成功");
		window.parent.reloadTable();
		window.parent.loxia.unlockPage();
	}
});
</script>
</body>
</html>