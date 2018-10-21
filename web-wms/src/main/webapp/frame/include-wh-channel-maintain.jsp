<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<form name="warehousechannel<s:property value='#parameters.type' />Form" id="warehousechannel<s:property value='#parameters.type' />Form" validate="warehousechannelFormValidate">
<div loxiaType="edittable" class="warehouse-channel">
<table width="80%" operation="add,delete" append="<s:property value='warehouseChannelList.size() == 0 ? 1: 0'/>">
	<thead>
		<tr>
			<th><input type="checkbox"/></th>
			<th><s:text name="label.warehouse.channel.code"/></th>
			<th><s:text name="label.warehouse.channel.name"/></th>
			<th><s:text name="label.warehouse.channel.description"/></th>
			<th><s:text name="label.warehouse.channel.status"/></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="warehouseChannelList" status="rowstatus" var="channel">
			<tr class="update">
				<td width="15px">&nbsp;</td>
				<td><input type="hidden" name="warehouseChannelList[<s:property value='#rowstatus.index'/>].id" value="<s:property value='id'/>"/>
					<input loxiaType="input" trim="true" maxlength="100" name="warehouseChannelList[<s:property value='#rowstatus.index'/>].code" mandatory="true" value="<s:property value='code'/>" class="code"></td>
				<td><input loxiaType="input" trim="true" maxlength="100" name="warehouseChannelList[<s:property value='#rowstatus.index'/>].name" mandatory="true" value="<s:property value='name'/>" ></td>
				<td><input loxiaType="input" trim="true" maxlength="100" name="warehouseChannelList[<s:property value='#rowstatus.index'/>].description" value="<s:property value='description'/>"></td>
				<td>
					<select loxiaType="select" mandatory="true" name="warehouseChannelList[<s:property value='#rowstatus.index'/>].isAvailable">
						<s:iterator value="#request.statusOptionList" >
							<s:if test="%{(#channel.isAvailable&&#optionKey)||(!#channel.isAvailable&&!#optionKey)}">
								<option value="<s:property value='optionKey'/>" selected="true">
							</s:if>
							<s:else>
							    <option value="<s:property value='optionKey'/>">
							</s:else>
								<s:property value="optionValue" escapeHtml="false"/>
							</option>
						</s:iterator>
					</select> 
				</td>
			</tr>
		</s:iterator>
	</tbody>
	<tbody>
		<tr class="add" index="(#)">
			<td width="15px"><input type="checkbox"/></td>
			<td><input loxiatype="input" mandatory="true" name="addList(#).code" class="code"/></td>
			<td><input loxiatype="input" mandatory="true" name="addList(#).name" class="name"/></td>
			<td><input loxiatype="input" name="addList(#).description" class="description"/></td>
			<td>
				<s:select loxiaType="select" name="addList(#).isAvailable" class="isAvailable" list="#request.statusOptionList" listKey="optionKey" listValue="optionValue"></s:select>
			</td>
		</tr>
	</tbody>
	<tfoot></tfoot>
</table>
</div>
<div class="buttonlist">
	<button type="button" loxiaType="button" class="confirm" refForm="warehousechannel<s:property value='#parameters.type' />Form"><s:text name="button.save" /></button>
</div>
</form>
