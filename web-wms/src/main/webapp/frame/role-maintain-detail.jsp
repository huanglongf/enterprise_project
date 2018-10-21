<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="label.role.name"/></title>
<%@include file="/common/meta.jsp"%>	
<script type="text/javascript" src="<s:url value='/scripts/frame/role-maintain-detail.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>

<body class="frame" contextpath="<%=request.getContextPath() %>">
	
	    <form id="myform4" name="myform4" method="post" >
			<table border="0">
			  <tr>
			      <input name="id" id="id" type="hidden" value="<s:property value="role.id"/>"  />
				  <td width="20%" align="right" class="label"><s:text name="label.role.name"/></td>
				  <td width="30%"><s:property value="role.name"/></td>
				  <td width="20%" align="right" class="label"><s:text name="label.user.outype"/></td>
				  <input name="ouid" id="ouid" type="hidden" value="role.ouType.id"/>
				  <td width="30%" id="ouname">
				  <s:property value="role.ouType.displayName"/>
		 		   </td>
			  </tr>			  
			  <tr>
				  <td align="right" class="label"><s:text name="label.describe"/></td>
				  <td colspan="3"><textarea loxiaType="input" trim="true" rows="5" name="description" id="description">
				  <s:property value="role.description"/>
				  </textarea></td>
			  </tr>
			 </table>	
			</form>		
		<div class="privilegeList">
			<h3><s:text name="label.role.list"/></h3>
			<ul id="authoritytree"  animate="true"></ul>
		</div>
	
</body>
</html>
