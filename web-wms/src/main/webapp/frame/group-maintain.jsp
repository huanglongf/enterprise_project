<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<%@include file="/common/meta.jsp"%>
		<title><s:text name="title.auth.operationunit.maintain"/></title>
		<script type="text/javascript" src="<s:url value='/scripts/frame/group-maintain.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
	</head>
	<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
		<ul id="groupTree" animate="true"></ul>
		<hr />
		<div class="ui-layout-center">
			<table>
				<tr>
					<td width="400">
						<form id="modifyForm" name="modifyForm">
							<table>
								<td class="label">
									<td colspan="2">
										<h3>
											<s:text name="label.auth.operationunit.edit"/>
										</h3>
									</td>
								</td>
								<tr>
									<td class="label" width="100">
										<s:text name="label.auth.operationunit.type"/>
									</td>
									<td width="200" id="groupType">
									</td>
								</tr>
								<tr>
									<td class="label">
										<s:text name="label.auth.operationunit.code"/>
									</td>
									<td id="groupCode">
									</td>
								</tr>
								<tr>
									<td class="label">
										<s:text name="label.auth.operationunit.name"/>
									</td>

									<td>
										<input loxiaType="input" mandatory="true" id="groupName" maxLength="255" checkmaster="checkName" max="255" trim="true"/>
									</td>
								</tr>
								<tr>
									<td>
										<s:text name="label.comment"/>
									</td>
									<td>
										<textarea loxiaType="input" id="groupComment"></textarea>
									</td>
								</tr>
								<tr>
									<td>
										<s:text name="label.auth.operationunit.status"/>
									</td>
									<td>
										<s:select list="statusOptionList" listKey="optionKey" 
													listValue="optionValue" 
													loxiaType="select" mandatory="true" id="groupEnable"></s:select>
									</td>
								</tr>
							</table>
							<div class="buttonlist">
								<button type="button" loxiaType="button" class="confirm"
									id="save">
									<s:text name="button.save"/>
								</button>
							</div>
						</form>
					</td>
					<td width="400">
						<form id="createForm" name="createForm">
							<table>
								<td class="label">
									<td colspan="2">
										<h3>
											<s:text name="label.auth.operationunit.add"/>
										</h3>
									</td>
								</td>
								<tr>
									<td class="label" width="100">
										<s:text name="label.auth.operationunit.type"/>
									</td>

									<td width="200">
										<select mandatory="true" loxiaType="select" id="addOuType">
											<s:iterator value="opeUnitType">
												<option value="<s:property value='id'/>" code="<s:property value='name'/>"><s:property value="displayName"/></option>
											</s:iterator>
										</select>
									</td>
								</tr>
								<tr>
									<td class="label">
										<s:text name="label.auth.operationunit.code"/>
									</td>

									<td>
										<input loxiaType="input" mandatory="true" id="addCode" trim="true" maxLength="50" max="50" checkmaster="checkCode"/>
									</td>
								</tr>
								<tr>
									<td class="label">
										<s:text name="label.auth.operationunit.name"/>
									</td>

									<td>
										<input loxiaType="input" mandatory="true" id="addName" trim="true" maxLength="255"  max="255" checkmaster="checkName"/>
									</td>
								</tr>
								<tr>
									<td>
										<s:text name="label.comment"/>
									</td>
									<td>
										<textarea loxiaType="input" id="addComment"></textarea>
									</td>
								</tr>

							</table>
							<div class="buttonlist">
								<button type="button" loxiaType="button" class="confirm" id="add">
									<s:text name="button.add"/>
								</button>
							</div>
						</form>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>