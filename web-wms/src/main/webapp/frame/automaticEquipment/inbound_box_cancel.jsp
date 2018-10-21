<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title>多件批次周转箱绑定</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/automaticEquipment/inbound_box_cancel.js"' includeParams='none' encode='false'/>"></script>
<style>
.showborder {
	border: thin;
}
</style>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div>
    <span class="label" style="margin-left: 50px;">入库货箱号:</span><input id="code" loxiaType="input" style="width: 150px;height:25px" trim="true"/><br/><br/> 
    </div>
    <div  class="buttonlist">
    	<button loxiaType="button" class="confirm" id="cancel">取消货箱</button>
    </div>
</body>
</html>