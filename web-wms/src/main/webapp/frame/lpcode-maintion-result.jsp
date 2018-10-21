<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<%@include file="/common/meta.jsp"%>

<script type="text/javascript">

$j(document).ready(function (){
	window.parent.loxia.unlockPage();
		//$j("#tab_2 select").val("");
	    if($j("#msg").val()=="success"){
	    	  //window.parent.clear();
	    	 window.parent.jumbo.showMsg("导入成功");
	    	 var url = $j("body").attr("contextpath") + "/findProvince.json";
	    	 window.parent.$j("#tbl-provide").jqGrid('setGridParam',{url:loxia.getTimeUrl(url),page:1}).trigger("reloadGrid");
	    	 
	    }else{
	    	window.parent.jumbo.showMsg("导入失败  <br/>" + $j("#msg").val(), 5000, "info", window.parent.parent);
	    }  
});

</script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<input type="hidden" name="msg" value="<s:property value="#request.msg"/>" id="msg" />
</body>
</html>
</head>
