<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<s:url value='/scripts/common/include/include-validity-operate.js' includeParams='none' encode='false'/>"></script>
<div id="upgradeDialog">
		<table width="95%">
			<tr>
				<td class="label">
					生产日期:
				</td>
				<td>
					<input loxiaType="date" id="productionDate" name="productionDate" showTime="true"/>
					<input id="selectId" hidden /> <br />
				</td>
				<td class="label">
					失效日期:
				</td>
				<td>
					<input loxiaType="date" id="expireDate" name="expireDate" showTime="true"/>
				</td>
			</tr>
			
		</table>
	<br/>
	<button type="button" loxiaType="button" class="confirm" id="confirm" >确认</button>
	<button type="button" loxiaType="button" id="cancel" >取消</button>
</div>

