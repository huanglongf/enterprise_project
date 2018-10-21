<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<%@include file="/common/meta.jsp"%>

<script type="text/javascript">


$j(document).ready(function (){
	window.parent.loxia.unlockPage();
	if($j("#msg").val().length>0){
 		jumbo.showMsg($j("#msg").val(), 5000, "info", window.parent.parent);
    }else{
    	jumbo.showMsg("导入成功", 5000, "info", window.parent.parent);
    }   
});

</script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<s:iterator value="#request.sns"status="rowstatus">
    <input type="input"  value="<s:property />" class="sns"/>
</s:iterator>

<input type="input" name="msg" value="<s:property value="#request.msg"/>" id="msg" />
</body>
</html>
</head>
