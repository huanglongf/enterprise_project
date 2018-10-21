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
    loxia.init({debug: true, region: 'zh-CN'});
    var result = '${result}';
	if(result == 'error'){
		var msg = '${message}';
		var s = 'VMI调拨申请导入失败 <br/>';
		for(var x in msg){
			s +=msg[x];
		}
		window.parent.showMsg(s);
	}else if(result == 'success'){
		var data = ${EXL_RESULT == null? 'null' : EXL_RESULT};
	    $j("#tbl-invList tr[id]",window.parent.document).remove();
	    for(var d in data){
	    	$j("#tbl-invList",window.parent.document).jqGrid('addRowData',data[d].id,data[d]);	
	   	}
		window.parent.showMsg("VMI调拨申请导入成功");
	}
	window.parent.loxia.unlockPage();
});
</script>
</body>
</html>