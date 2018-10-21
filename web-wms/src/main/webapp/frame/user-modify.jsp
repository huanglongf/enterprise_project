<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><s:text name="title.user.maintain"/></title>

<style>
	body, .ui-widget { font:9pt Verdana, Arail,"Trebuchet MS", sans-serif;}		
	td.decimal { text-align: right;	padding-right: 4px;}
	/* div.ui-datepicker{ font-size: 11px;} */	
	.label { text-align: right; padding-right: 4px; font-weight: bold; background-color: #efefef;}
	select.ui-loxia-default, input.ui-loxia-default, textarea.ui-loxia-default { width: 95%; }
</style>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/user-modify-1.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/frame/user-modify-2.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
 <s:text id="pselect" name="label.please.select"/>
    <div id="queryid">
	<form id="myform" name="myform" method="post" >
	<table>
		<tr>
			<td class="label"><s:text name="label.user.loginname"/></td>
			<td width="130"><input loxiaType="input" name="user.loginName" id="loginName" maxlength="50" trim="true"/></td>
			<td class="label"><s:text name="label.user.username"/></td>
			<td width="130"><input loxiaType="input" name="user.userName" id="userName" maxlength="100" trim="true"/></td>
		</tr>
		<tr>
	        <td class="label"><s:text name="label.user.email"/></td>
			<td width="130"><input loxiaType="input" name="user.email" id="email" maxlength="100" trim="true"/></td>
			<td class="label"><s:text name="label.user.iseffective"/></td>
			<td width="130"><s:select list="statusOptionList" loxiaType="select" listKey="optionKey" listValue="optionValue"
			 id="isEnabled" name="user.memo" headerKey="" headerValue="%{pselect}"></s:select></td>			
		</tr>
	
		<tr>
		   <td class="label"><s:text name="label.user.phone"/></td>
			<td width="130"><input loxiaType="input"  name="user.phone" id="phone" maxlength="100" trim="true"/></td>
		  <td class="label"><s:text name="label.user.jobnumber"/></td>
			<td width="130"><input loxiaType="input"  name="user.jobNumber" id="jobNumber" maxlength="10" trim="true"/></td>
		</tr>
		<tr>
		 <td class="label"><s:text name="label.user.usergroup"/></td>
			<td width="130"><s:select list="userGroupList" loxiaType="select" listKey="id" 
			listValue="name" id="groupId" name="userGroup.id" headerKey="-1" headerValue="%{pselect}"></s:select>
	              </td>
		</tr>
	</table>
	</form>
	<p></p>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="create" onclick="searchUser()"><s:text name="button.query"/></button>
	</div>
	<div id="tbl-opb-line" >
	<table id="tbl-userlist"></table>
	<div id="pager9"></div> 
	
	</div>
	<p></p>
	<div class="buttonlist">
	  
		<button type="button" loxiaType="button" class="confirm" id="modifyUser" onclick="modifyUser()"><s:text name="button.modify"/></button>
		
		<button type="button" loxiaType="button" class="confirm" id="deleleUser" onclick="deleleUser()"><s:text name="button.batchsetinvalid"/></button>
	
	</div>
  </div>
  <!-- 查询页面结束 -->
  <div id="idmodify" style="display:none" >
   <form id="myform1" name="myform1" method="post"  >
	<table>
	<input type="hidden" name="user.id" id="userid" value="<s:property value="user.id"/>"/>
		<tr>
			<td class="label"><s:text name="label.user.loginname"/></td>
			<td id="lgn" width="130"></td>
			<td class="label"><s:text name="label.user.userfirstpassword"/></td>
			<td width="130"><input type="input" name="user.password" id="password" loxiaType="input" maxlength="20" checkmaster="checkPassword"></td>
		</tr>
			<tr>
			<td class="label"><s:text name="label.user.username"/></td>
			<td width="130"><input loxiaType="input" name="user.userName" id="userName" mandatory="true" maxlength="100"/></td>
			<td class="label"><s:text name="label.user.jobnumber"/></td>
			<td width="130"><input loxiaType="input"  name="user.jobNumber" id="jobNumber" mandatory="true" maxlength="10"  /></td>
			
		</tr>
		
		<tr>
		   	<td class="label"><s:text name="label.user.phone"/></td>
			<td width="130"><input loxiaType="input"  name="user.phone" id="phone" maxlength="100" checkmaster="checkPhone" /></td>
		
		 	<td class="label"><s:text name="label.user.email"/></td>
			<td width="130"><input loxiaType="input" name="user.email" id="email" mandatory="true" maxlength="100" checkmaster="checkEmailisNullorRule"/></td>
			
		</tr>
		<tr>
			<td class="label"><s:text name="label.user.remark"/></td>
			<td width="130"><textarea loxiaType="input" id="memo" name="user.memo" checkmaster="checkRemark"></textarea></td>
		
			<td class="label">交接上限</td>
			<td width="130"><input loxiaType="input"  name="user.maxNum" id="maxNum" maxlength="100" checkmaster="checkNumber2" /></td>
		</tr>
	</table>
	</form>
	<p></p>
	<div class="buttonlist">
	   <!-- 
		<button type="button" loxiaType="button" class="confirm" id="create" onclick="modifyUserInfo()" >修改</button>
		 -->
		<button type="button" loxiaType="button" class="confirm" id="toback" onclick=""><s:text name="button.toback"/></button>
	</div>
	<table id="tbl-userrole"></table>
	

	<p></p>
	<!-- 
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="button2">增加</button>
		<button type="button" id="button1" loxiaType="button" class="confirm" onclick="delUserRole()"  >删除</button>
	</div>
    -->
	<div id="tbl-opb-line_1"   >
	
		<table>
		<tr>
			<td class="label"><s:text name="label.user.outype"/></td>
			<td width="130">
			<s:select onchange="setFAQTwo(this)" list="opeUnitType" mandatory="true" loxiaType="select" listKey="id" listValue="displayName" key="addOuType" headerKey="" headerValue="%{pselect}">			
			</s:select>
						</td>
			<td class="label"></td>
			<td width="130"></td>
		</tr>
		<tr>
			<td class="label"><s:text name="label.user.ou"/></td>
			<td width="130"><select id="OperationUnitId" loxiaType="select" mandatory="true">
					
					</select></td>
			<td class="label"><s:text name="label.user.role"/></td>
			<td width="130"><select id="roleId" loxiaType="select" mandatory="true">
						
					</select></td>
		</tr>
		<tr>
			<td class="label"><s:text name="lable.user.isdefault"/></td><td width="30%"><input type="checkbox" name="isDefinexxx" id="isDefinexxx" value ="1" checked="checked"></td>		
			<td colspan="2"><button type="button" loxiaType="button" onclick="baddUserRole()" class="confirm" ><s:text name="button.addrole"/></button>
			<button id="button1" type="button" loxiaType="button" onclick="delUserRole()" ><s:text name="button.deleteselectrole"/></button></td>
		</tr>
		
	</table>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="create" onclick="addUserandRole()"><s:text name="button.modifyuser"/></button>
	</div>
  
  </div>
</body>
</html>