<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>

<script type="text/javascript">
<!--
$j(document).ready(function (){
	 window.parent.loxia.unlockPage();
    if($j("#result").val()=="success"){
    	if($j("#flag").val() == "nike"){
    		window.parent.showMsg("配置导入（修改）成功");
            window.parent.importNikeReturn(true);
    	} else {
    		window.parent.showMsg("配置导入（修改）成功");
            window.parent.importSFCReturn(true);
    	}
    }else{
    	window.parent.showMsg("配置导入（修改）失败：" + $j("#msg").val());
    	window.parent.importSFCReturn(false);
    }    
});
-->
</script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<input type="hidden" name="result" value="<s:property value="#request.result"/>" id="result" />
<input type="hidden" name="msg" value="<s:property value="#request.msg"/>" id="msg" />
<input type="hidden" name="flag" value="<s:property value="#request.flag"/>" id="flag" />
</body>
</html>