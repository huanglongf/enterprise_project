<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="title.user.role.maintain"/></title>
<%@include file="/common/meta.jsp"%>	
<script type="text/javascript" src="../scripts/frame/role-maintain.js"></script>
	
<style type="text/css">
</style>
</head>
<body class="frame" contextpath="<%=request.getContextPath() %>">
 <s:text id="pselect" name="label.please.select"/>
		<div id="f1">	
		<form id="myform1" name="myform1" method="post" >	 
			<table>
				<tr>
					<td  class="label" align="right"><s:text name="label.role.name"/></td>
					<td width="130"><input name="name" id="name" loxiaType="input" trim="true" maxlength="100" /></td>
					<td class="label" align="right"><s:text name="label.user.outype"/></td>
					<td width="130">
			<s:select list="opeUnitType"  loxiaType="select" listKey="id" listValue="displayName" key="addOuType" headerKey="" headerValue="%{pselect}">			
			</s:select>		
                    </td>
				</tr>
			</table>
			</form>
			<div class="buttonlist">
				<button type="button" loxiaType="button" class="confirm" title="" onclick="queryRole()"><s:text name="button.query"/></button>
			</div>
			<table id="tbl-rolelist"></table>
			<div id="pager9"></div>
			<div class="buttonlist">
				<button type="button" loxiaType="button" class="confirm" onclick="toaddRole()" id="addBtn"><s:text name="button.newadd"/></button>
				<button type="button" loxiaType="button" class="confirm" onclick="deleteRole()" id="deleteBtn"><s:text name="button.delete"/></button>
				<button type="button" loxiaType="button" class="confirm" onclick="tomodifyRole()" id="modifyBtn"><s:text name="button.modify"/></button>
				<button type="button" loxiaType="button" class="confirm" onclick="viewRole()" id="viewBtn"><s:text name="button.view"/></button>
			</div>
			
		</div><!-- end f1 div -->
				

		<div id="f2" class="hidden">
		<form id="myform2" name="myform2" method="post" >
			<table border="0">
			  <tr>
				  <td  align="right" class="label"><s:text name="label.role.name"/></td>
				  <td width="130"><input name="name" id="name" loxiaType="input" trim="true" mandatory="true" value="" maxlength="100" /></td>
				  <td  align="right" class="label"><s:text name="label.user.outype"/></td>
				  <td width="130">
		 	 <s:select list="opeUnitType" mandatory="true" loxiaType="select" listKey="id" listValue="displayName" key="addOuType" headerKey="" headerValue="%{pselect}">			
			 </s:select>		   
				</td>
			  </tr>
			  
			  <tr>
				  <td align="right" class="label"><s:text name="label.describe"/></td>
				  <td colspan="3"><textarea loxiaType="input" trim="true" rows="5" name="description" id="description" checkmaster="checkDescribe"></textarea></td>
			  </tr>
			 </table>	
			</form>			 
			<div class="buttonlist buttonDivWidth">
				<button type="button" loxiaType="button" class="confirm" id="nextBtn" onclick="addRole()" ><s:text name="button.next"/></button>
			</div>
		</div><!-- end f2 div -->
				


		<div id="f3" class="hidden"><!-- bigin f3 div -->
	    <form id="myform3" name="myform3" method="post" >
			<table border="0">
			  <tr>
			      <input name="id" id="id" type="hidden"  />
				  <td  align="right" class="label"><s:text name="label.role.name"/></td>
				  <td width="130"><input name="name" id="name" loxiaType="input" trim="true" mandatory="true" maxlength="100" /></td>
				  <td  align="right" class="label"><s:text name="label.user.outype"/></td>
				 <input name="ouid" id="ouid" type="hidden" />
				  <td width="130" id="ouname">
		 		</td>
			  </tr>			  
			  <tr>
				  <td align="right" class="label"><s:text name="label.describe"/></td>
				  <td colspan="3"><textarea loxiaType="input" trim="true" rows="5" name="description" id="description" checkmaster="checkDescribe"></textarea></td>
			  </tr>
			 </table>	
			</form>		
		<div class="privilegeList">
			<h3><s:text name="label.rolelist"/></h3>
			<ul id="authoritytree"  animate="true"></ul>
			<ul id="authoritytree1"  animate="true"></ul>
		</div>
		<div class="buttonlist">
		    <div id="f3-1" class="hidden">
		       <button type="button" loxiaType="button" class="confirm" onclick="modifyRole('authoritytree')" id="comBtn"><s:text name="button.addrole"/></button>
		        <button type="button" loxiaType="button" class="confirm" onclick="backMRole()"  id="comBtn"><s:text name="button.toback"/></button>	
		    </div>
		    <div id="f3-2" class="hidden">
			<button type="button" loxiaType="button" class="confirm" onclick="modifyRole('authoritytree1')" id="comBtn"><s:text name="button.modifyrole"/></button>
		    <button type="button" loxiaType="button" class="confirm" onclick="backMRole()"  id="comBtn"><s:text name="button.toback"/></button>	
			</div>
		</div>	
		</div>
        <div id="f4" class="hidden"><!-- bigin f3 div -->
	    <form id="myform4" name="myform4" method="post" >
			<table border="0">
			  <tr>
			      <input name="id" id="id" type="hidden"  />
				  <td  align="right" class="label"><s:text name="label.role.name"/></td>
				  <td width="130"><input name="name" id="name" loxiaType="input" trim="true" mandatory="true" maxlength="100" /></td>
				  <td  align="right" class="label"><s:text name="label.user.outype"/></td>
				  <input name="ouid" id="ouid" type="hidden" />
				  <td width="130" id="ouname">
		 		</td>
			  </tr>			  
			  <tr>
				  <td align="right" class="label"><s:text name="label.describe"/></td>
				  <td colspan="3"><textarea loxiaType="input" trim="true" rows="5" name="description" id="description" checkmaster="checkDescribe"></textarea></td>
			  </tr>
			 </table>	
			</form>		
		<div class="privilegeList">
			<h3><s:text name="label.rolelist"/></h3>
			<ul id="authoritytree"  animate="true"></ul>
		</div>
		<div class="buttonlist">
	  <button type="button" loxiaType="button" class="confirm" onclick="backMRole2()"  id="comBtn"><s:text name="button.toback"/></button>	
		</div>	
		</div>
</body>
</html>
