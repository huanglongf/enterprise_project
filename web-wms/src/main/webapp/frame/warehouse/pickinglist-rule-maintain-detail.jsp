<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../../common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.dispatchList.title"/></title>

<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/pickinglist-rule-maintain-detail.js' includeParams='none' encode='false'/>"></script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">

<div id="divDetail">
	<form method="post" id="query-form1">
		<table id="distributionRuleDetailTable">
			<tr>
				<td class="label">条件描述：</td>
				<td style= "width:200px">
					<select loxiaType="select" name="distributionRuleCondition.groupcode" id="groupcode">
						<option value="">--请选择配货规则条件组--</option>
					</select>
				</td>
			</tr>
		</table>
	</form>
	<div class="buttonlist">
		<button type="button" id="btn-query1" loxiaType="button" class="confirm"><s:text name="button.search"></s:text> </button>
		<button type="button" loxiaType="button" id="reset1"><s:text name="button.reset"></s:text> </button>
	</div>
	<table width="90%">
			<tr>
				<td width="2%"></td>	
				<td width="40%">
					<table id="tbl-optionalRuleDetail">
					
					</table>
				</td>
				<td width="6%"></td>
				<td width="40%">
					<table id="tbl-currentRuleDetail">
						
					</table>
				</td>
				<td width="2%"></td>
			</tr>
	</table>
	
	<div id="btnlist" class="buttonlist">
		<div id="ruleSureDialog" style="text-align: center;">
				<form method="post" id="query-form2">
					<table id="ruleSureDialogTable">
						<tr>
							<td class="label">规则名称：</td>
							<td style= "width:200px">
								<input loxiaType="input" id = "distributionRuleName" name="distributionRule.name" trim="true"/>
							</td>
						</tr>
					</table>
				</form>
		</div>
		</br>
		<button loxiaType="button" id="newButton" class="confirm"><s:text name="button.newadd"></s:text> </button>
		<button loxiaType="button" id="editButton" class="confirm"><s:text name="button.modify"></s:text> </button>
		<button loxiaType="button" id="back2"><s:text name="button.back"></s:text></button>
	</div>
	
</body>
</html>