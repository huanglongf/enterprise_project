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
	var msg = '${msg}';
	var s = '残品原因类型维护失败 <br/>';
	var id=window.parent.$j("#imperfectId").val();
	for(var x in msg){
		s +=msg[x] + '<br/>';
	}
	if(result == 'success'){
		window.parent.jumbo.showMsg("残品原因类型维护成功");
		window.parent.$j("#tbl-bichannel-imperfect-line-list").jqGrid('setGridParam',{
			url:window.$j("body").attr("contextpath")+"/findBiChanneImperfectlLineList.json?imperfectId="+id,
			page:1
		}).trigger("reloadGrid");
		window.parent.$j("#filewhy").val("");
	}else{
		window.parent.jumbo.showMsg(msg);
	}
	window.parent.loxia.unlockPage();
});
</script>
</body>
</html>