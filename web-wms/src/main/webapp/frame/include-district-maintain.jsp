<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<table> 
    <tr>
        <td width="20%"><s:text name="label.warehouse.inpurchase.selectFile"/></td>
        <td width="30%">
            <form method="POST" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">
                <input type="file" loxiaType="input" id="districtFile" name="districtFile"/>
            </form>
        </td>
        <td>
            <button type="button" loxiaType="button" id="import"><s:text name="label.warehouse.location.import"/></button>
        </td>
        <td>
        	<a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tpl_location_import"></s:text>.xls&inputPath=tpl_location_import.xls"><s:text name="download.excel.template"></s:text></a>
        </td>
	</tr>
</table>
<form name="updateForm<s:property value='district.id'/>" id="updateForm<s:property value='district.id'/>">
<table>			
	<tr>
		<td width="13%" class="label"><s:text name="label.warehouse.location.name"/></td>
		<td width="20%"><input type="hidden" value="<s:property value='district.id'/>" name="district.id" id="districtId"/>
			<input type="hidden" value="<s:property value='district.code'/>" name="district.code" id="districtCode"/>
			<input loxiaType="input" mandatory="true" value="<s:property value='district.name'/>" name="district.name" id="districtName" max="100" maxLength="100"/>
		</td>
		
		<td width="13%" class="label"><s:text name="label.warehouse.location.status"/></td>
		<td width="20%">
			<s:select list="statusOptionList" listKey="optionKey" 
					listValue="optionValue" name="district.isAvailable"
					loxiaType="select" mandatory="true" id="isAvailable" value="%{district.isAvailable}"></s:select>
		</td>
		
		<td width="13%" class="label">库区类型</td>
		<td width="20%">
		
			<select id="districtType" loxiaType="select" mandatory="true" name="district.intDistrictType">
				<s:iterator value="#request.districtTypeList">
					<option value="<s:property value='optionKey'/>" <s:if test="optionKey == district.intDistrictType">selected</s:if>>
						<s:property value="optionValue"/>
					</option>
				</s:iterator>
			</select>
		</td>
	</tr>
		
</table>
<table id="tbl-locations-<s:property value='district.id'/>" target="<s:property value='district.id'/>"></table>
<div id="tbl-locations-<s:property value='district.id'/>-pager" target="<s:property value='district.id'/>" />
<div loxiaType="edittable" class="district">

<table operation="add,delete" append="0">
	<thead>
		<tr>
			<th class="label"><input type="checkbox"/></th>
			<th class="label"><s:text name="label.warehouse.location.manualcode"/></th>
			<th class="label"><s:text name="label.warehouse.location.syscode"/></th>
			<th class="label"><s:text name="label.warehouse.location.x"/></th>
			<th class="label"><s:text name="label.warehouse.location.y"/></th>
			<th class="label"><s:text name="label.warehouse.location.z"/></th>
			<th class="label"><s:text name="label.warehouse.location.c"/></th>
			<th class="label"><s:text name="label.warehouse.location.barcode"/></th>
			<th class="label hidden"><s:text name="label.warehouse.location.status"/></th>
			<th class="label">弹出口编码</th>
			<th class="label"><s:text name="label.warehouse.location.comment"/></th>
		</tr>
	</thead>
	<tbody>
		
	</tbody>
	<tbody>
		<tr class="add">
			<td><input type="checkbox"/></td>
			<td><input name="locations(#).manualCode" class="manualCode"/></td>
			<td><input name="locations(#).sysCompileCode" class="sysCompileCode" style="border:0px;" readOnly="true"/></td>
			<td><input name="locations(#).dimX" class="dimX"/></td>
			<td><input name="locations(#).dimY" class="dimY"/></td>
			<td><input name="locations(#).dimZ" class="dimZ"/></td>
			<td><input name="locations(#).dimC" class="dimC"/></td>
			<td><input name="locations(#).barCode" class="barCode"/></td>
			<td  class="hidden"><input name="locations(#).isAvailable" class="isAvailable" type="checkbox" checked/></td>
			<td><input name="locations(#).popUpAreaCode" class="popUpAreaCode"/></td>
			<td><input name="locations(#).memo" class="memo"/><input name="locations(#).code" type="hidden"/></td>
		</tr>
	</tbody>
	<tfoot></tfoot>
</table>

</div>
</form>
<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="save" targetId="<s:property value='district.id'/>"><s:text name="button.save"/></button>
		<button type="button" loxiaType="button" class="confirm" id="disable" targetId="<s:property value='district.id'/>"><s:text name="button.warehouse.location.disable"/></button>
	</div>
	