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
<script type="text/javascript" src="<s:url value='/scripts/frame/user-warehouse.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
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
	      <td align="left" width="75px"><b>绑定仓库:</b></td>
						<td width="180px"><select id="selTransOpc" name="user.whOuId" loxiaType="select" loxiaType="select">
							<option value="">--请选择仓库--</option>
						</select>
				</td>
		</tr>
	</table>
	</form>
	<p></p>
	<div class="buttonlist">
		<table>
			<tr>
				<td>
					<button type="button" loxiaType="button" class="confirm" id="create" onclick="searchUser()"><s:text name="button.query"/></button>
				</td>
				<td>&nbsp;</td>
				<td align="left"><b>选择文件:</b></td>
				<td  align="center">
					<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
						<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
					</form>
				</td>
				<td> 
					<button loxiaType="button" class="confirm" id="import">导入</button>
		             <a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=用户仓库绑定导入.xls&inputPath=tplt_import_user_warehouse_ref.xls">模板文件下载</a>
		        </td>
		     </tr>
		 </table>
	</div>
	<div id="tbl-opb-line" >
	<table id="tbl-userlist"></table>
	<div id="pager9"></div> 
	
	</div>
	<p></p>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="modifyUser" onclick="modifyUser()"><s:text name="button.modify"/></button>
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
			<td align="left" width="75px"><b>绑定仓库:</b></td>
						<td width="180px"><select id="selTransOpc1"  mandatory="true" loxiaType="select" loxiaType="select">
							<option value="">--请选择仓库--</option>
						</select>
			</td>
			<td id="lgnId" width="130" hidden= "true"></td>
		</tr>
	</table>
	</form>
	<p></p>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="save" >保存</button>
		<button type="button" loxiaType="button" id="toback" ><s:text name="button.toback"/></button>
	</div>
	<table id="tbl-userrole"></table>
	

	<p></p>
	<iframe id="upload" name="upload" class="hidden"></iframe>
</body>
</html>