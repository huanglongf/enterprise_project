<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">

<script type="text/javascript">

$j(document).ready(function (){
	var result = '${result}';
	if(result == 'error'){
		var msg = '${message}';
		var s = "";
		for(var x in msg){
			s +=msg[x];
		}
		window.parent.$j("#errorMsg").html(s);
		window.parent.$j("#showErrorDialog").dialog("open");
	} else if(result == 'override'){
		var msg = '${message}';
		var s = "";
		for(var x in msg){
			s +=msg[x];
		}
		window.parent.$j("#overrideMsg").html(s);
		window.parent.$j("#showOverrideDialog").dialog("open");
	} else if(result == 'success'){
		//window.parent.setInit();
		window.parent.showMsg("导入成功");
	} else if(result == 'exceeded'){
		window.parent.$j("#errorMsg").html("导出行超过4万条, 请选择库区后再导出对应库区的库位信息");
		window.parent.$j("#showErrorDialog").dialog("open");
	}
	
	window.parent.loxia.unlockPage();
});
</script>
</body>
</html>