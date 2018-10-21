<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>

<script type="text/javascript">

$j(document).ready(function (){
   loxia.init({debug: true, region: 'zh-CN'});
	window.parent.loxia.unlockPage();
    var result = '${result}';
	if(result == 'error'){
		var s = '库间移动申请导入失败 \n';
		var msg = '${message}';
		for(var x in msg){
			s +=msg[x];
		}
		window.parent.showMsg(s);
	}else if(result == 'success'){
		window.parent.showMsg("库间移动申请导入成功");
		var data = ${EXL_RESULT == null? 'null' : EXL_RESULT};
	    $j("#tbl-invList tr[id]",window.parent.document).remove();
	    for(var d in data){
	    	$j("#tbl-invList",window.parent.document).jqGrid('addRowData',data[d].id,data[d]);	
	   	}
	}	 
}); 

</script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<input type="hidden" name="msg" value="<s:property value="#request.msg"/>" id="msg" />
	<%--<input type="hidden" name="result" value="<s:property value="#request.result"/>" id="result" /> --%>
	<div class="hidden" name="result" id="result">
		<s:property value="#request.result"/>
	</div>
</body>
</html>