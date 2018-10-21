<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title>多件批次周转箱绑定</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/automaticEquipment/many-batch-turnoverbox-bind.js"' includeParams='none' encode='false'/>"></script>
<style>
.showborder {
	border: thin;
}
</style>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div>
    <span class="label" style="margin-left: 50px;">配货批次号:</span><input id="slipCode" loxiaType="input" style="width: 150px;height:25px" trim="true"/><br/><br/>
    <span class="label" style="margin-left: 50px;">仓储区域编码:</span><input id="zoonCode" loxiaType="input" style="width: 150px;height:25px" trim="true"/><br/><br/>  
    <span class="label" style="margin-left: 50px;">周转箱条码:</span><input id="barCode" loxiaType="input" style="width: 150px;height:25px" trim="true"/>
    </div>
    <div style="float:left;margin-left:50px;color:red">
        <br/><br/>
    	操作提示：<br/>
    	页面进入时光标自动定位在相关单据号扫描框，扫描完再进入周转箱条码扫描框，绑定操作在周转箱条码扫描完之后触发。<br/>
        1、当手动操作先输入周转箱条码时，如果相关单据号没有扫描，则提示要扫描相关单据号，并且执行光标定位。<br/>
        2、按照正确顺序扫描后，如果绑定成功，不做任何提示，直接清空进入下次绑定扫描<br/>
        3、按照正确顺序扫描后，如果绑定不成功，只在界面提示错误信息，然后还是直接清空进入下次绑定扫描<br/>
    </div>
</body>
</html>