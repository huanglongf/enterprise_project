<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">
	
<%--用于js 拼接连接 contextPath 应用程序名--%>
	var pagebase = "${pagebase}";
	var staticbase = "${staticbase}";
	var uploadbase = "${uploadbase}";
	var menuItems = "${menuItems}";
	var unitTree = "${unitTree}";
	var orgTypeId = "${orgType.id}";
	var version = "${version}";
	var result = "${result}";
	var msg = "${msg}";
	var logId = "${logId}";
</script>
<!--[if lt IE 9]>
<script src="<s:url value='/pda/scripts/ie6-jquery.min.js' includeParams='none' encode='false'/>"></script>

<![endif]-->

<!--[if (gte IE 9)|!(IE)]><!-->
<script src="<s:url value='/pda/scripts/jquery.min.js' includeParams='none' encode='false'/>"></script>
<!--<![endif]-->

<script src="<s:url value='/pda/scripts/main.js' includeParams='none' encode='false'/>"></script>
<script src="<s:url value='/pda/scripts/constants.js' includeParams='none' encode='false'/>"></script>





