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
    if($j("#msg").val()=="success"){
    	  window.parent.jumbo.showMsg("导入成功");
    	  var stvid = $j("#stv").val();
    	  var isPda = $j("#pda").val();
    	  var quantity = $j("#quantity").val();
    	  var sta = new Object();
    	  sta.code = window.parent.$j("#staCode3").val();
    	  sta.id = window.parent.$j("#staId").val();

    	  window.parent.$j("#div-sta-detail").addClass("hidden");
    	  window.parent.$j("#div-sta-list").addClass("hidden");
    	  
    	  window.parent.setReaciveQuantity(quantity);
    	  window.parent.tostvInfo(stvid,sta,isPda);
    	  
    }else{
    	window.parent.jumbo.showMsg("操作失败！"+$j("#msg").val(), 5000, "info", window.parent.parent);
    }    
});
-->
</script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<input type="hidden" name="msg" value="<s:property value="#request.msg"/>" id="msg" />

<input type="text" name="stv" value="<s:property value="#request.stvId"/>" id="stv" />
<input type="text" name="pda" value="<s:property value="#request.isPda"/>" id="pda" />
<input type="text" name="pda" value="<s:property value="#request.quantity"/>" id="quantity" />
</body>
</html>