<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<form>
<div>
	<label>库位编码：</label><input id="locationCode" class="locationCode" loxiaType="input" style="width: 150px" trim="true"/>
	<button id="search" type="button" loxiaType="button" class="confirm"><s:text name="button.query"/></button>
</div>	
<input type="hidden" name="locationId" value="<s:property value='locationId'/>" id="locId"/>
<input type="hidden" name="district.id" value="<s:property value='district.id'/>" id="disId"/>
<table></table>
<div class="pager"></div>
</form>