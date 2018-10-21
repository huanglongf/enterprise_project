<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.auth.usergroup"/></title>
<style type="text/css">
	.label{ text-align:left;}
</style>
<script type="text/javascript" src="<s:url value='/scripts/frame/user-group.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>

<body contextpath="<%=request.getContextPath() %>"><form name="userGroupForm" id="userGroupForm">
<div id="edittable" loxiaType="edittable" >

<table operation="add,delete" append="<s:if test='%{userGroupList.size>0}'>0</s:if><s:if test='%{userGroupList.size==0}'>1</s:if>">
	<thead>
		<tr>
			<th class="label"><input type="checkbox"/></th>
			<th class="label" width="130"><s:text name="label.auth.usergroup.groupname"/></th>
			<th class="label" width="130"><s:text name="label.auth.usergroup.groupdescription"/></th>
			<th class="label" width="130"><s:text name="label.auth.usergroup.operation"/></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="userGroupList" status="rowstatus">
		<tr class="update">
			<td><input type="checkbox" value="<s:property value='id'/>"/><input type="hidden" name="userGroupList[<s:property value='#rowstatus.index'/>].id" value="<s:property value='id'/>"/></td>
			<td><input loxiaType="input" name="userGroupList[<s:property value='#rowstatus.index'/>].name" mandatory="true" value="<s:property value='name'/>" max="50" maxLength="50"></td>
			<td><input loxiaType="input" name="userGroupList[<s:property value='#rowstatus.index'/>].description" value="<s:property value='description'/>" max="255" maxLength="255"></td>
			<td><button type="button" loxiatype="button" class="custom1" targetId="<s:property value='id'/>" currentGroup="<s:property value='name'/>"><s:text name="button.auth.usergroup.operation"/></button></td>
		</tr>
		</s:iterator>
	</tbody>
	<tbody>
		<tr class="add" index="(#)">
			<td><input type="checkbox"/></td>
			<td><input loxiatype="input" mandatory="true" name="addList(#).name" class="name" max="50" maxLength="50"/></td>
			<td><input loxiatype="input" name="addList(#).description" class="description" max="255" maxLength="255"/></td>
			<td>&nbsp;</td>
		</tr>
	</tbody>
</table>

</div>
<div id="deleteHidden" class="hidden">
</div>
</form>
<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="save"><s:text name="button.auth.usergroup.save"/></button>
	</div>
<div id="batch-edit" class="hidden">
	<form name="userForm" id="userForm">
	<table>
	<tr>
		<td class="label"><s:text name="label.auth.usergroup.currentgroup"/><input type="hidden" name="currentGroupId" id="currentGroupId"/></td>
		<td colspan="5" id="currentGroup"></td>		
	  </tr>
	  <tr>
		<td class="label"><s:text name="label.auth.usergroup.loginname"/></td>
		<td width="130"><input id="loginName" name="userGroupCommand.loginName" loxiaType="input" trim="true" maxlength="50" /></td>
		<td class="label"><s:text name="label.auth.usergroup.username"/></td>
		<td width="130"><input id="userName" name="userGroupCommand.userName" loxiaType="input" trim="true" maxlength="100" /></td>
		<td class="label"><s:text name="label.auth.usergroup.jobnumber"/></td>
		<td width="130"><input id="jobNumber" name="userGroupCommand.jobNumber" loxiaType="input" trim="true" maxlength="10" /></td>
	  </tr>
	  
	  <tr>
	    <td class="label"><s:text name="label.auth.usergroup.isenabled"/></td>		
		<td width="130">
			<s:select list="statusOptionList" loxiaType="select" listKey="optionKey" 
					listValue="optionValue" id="isEnabled" name="userGroupCommand.isEnabled" headerKey="" headerValue="-ALL-"></s:select>
		</td>
		<td class="label"><s:text name="label.auth.usergroup.usergroup"/></td>
		<td width="130">
			<s:select list="userGroupList" loxiaType="select" listKey="id" 
			listValue="name" id="groupId" name="userGroupCommand.groupId" headerKey="" headerValue="-ALL-" ></s:select>
		</td>
		<td colspan="2"></td>
	  </tr>
	  
	</table>
	</form>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" title="tip：<s:text name='tip.auth.usergroup.search'/>" id="reload"><s:text name="button.query"/></button>
		<button type="button" loxiaType="button" onclick="$j('#batch-edit').addClass('hidden');"><s:text name="button.close"/></button>
	</div>

	 <br/><br/>
	
	<table id="tbl-userlist2"></table>
	<div id="pager2"></div>

 <br/><br/>
	<div class="buttonlist">
		<button type="button" loxiaType="button" id="add"><s:text name="button.auth.usergroup.add"/></button>
		<button type="button" loxiaType="button" id="remove"><s:text name="button.auth.usergroup.remove"/></button>
	</div>
</div>
</body> 
</html>
