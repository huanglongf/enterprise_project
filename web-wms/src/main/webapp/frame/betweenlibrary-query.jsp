<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/meta.jsp"%>
<title><s:text name="betweenlibary.move.query"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/betweenlibrary-query.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
 <s:text id="pselect" name="label.please.select"/>
	<!-- 这里是页面内容区 -->
	<div id="searchCondition">
	   <form id="staForm" name="staForm">
		<table>
			<tr>
				<td class="label"><s:text name="betweenlibary.starttime"/></td>
				<td><input id="startDate" loxiaType="date" name="sta.createTime1" showTime="true"/></td>
				<td class="label"><s:text name="betweenlibary.endtime"/></td>
				<td><input id="endtDate" loxiaType="date" name="sta.finishTime1" showTime="true"/></td>
				<td class="label"><s:text name="label.warehouse.pl.status"/></td>
				<td width="150">
					<s:select list="staStatusList"  loxiaType="select" listKey="optionKey"  
						listValue="optionValue"  headerKey="" headerValue="%{pselect}" name="sta.intStatus"></s:select>
				</td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.warehouse.pl.sta"/></td>
				<td><input loxiaType="input" name="sta.code" trim="true"   /></td>
				<td class="label"><s:text name="label.wahrhouse.sta.creater"/></td>
				<td><input loxiaType="input" name="sta.creator.userName" trim="true"  /></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="label"><s:text name="betweenlibary.main.warehouse"/></td>
					<td width="150">
                <s:select list="whList"  loxiaType="select" listKey="id" 
                    listValue="name"   headerKey="" headerValue="%{pselect}" name="sta.mainWarehouse.id">            
           		 </s:select></td>
					<td class="label"><s:text name="betweenlibary.addi.warehouse"/></td>
					<td width="150">
                <s:select list="whList"  loxiaType="select" listKey="id" 
                    listValue="name"  headerKey="" headerValue="%{pselect}" name="sta.addiWarehouse.id">            
            	</s:select></td>
				<td></td>
				<td></td>
			</tr>
		</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search"><s:text name="button.search"/></button>
			<button loxiaType="button" id="reset" type="reset"><s:text name="button.reset"/></button>
		</div>
		<br />
		<div>
			<table id="tbl-query-info"></table>
			<div id="pager"></div>
		</div>
	</div>
	<br />
	<div id="details" class="hidden">
			<table>
			<tr>
				<td class="label"><s:text name="label.warehouse.pl.sta"/></td>
				<td id="code"></td>
				<td class="label"><s:text name="label.wahrhouse.sta.creater"/></td>
				<td id="creater"></td>
				
			</tr>
			<tr>
				<td class="label"><s:text name="betweenlibary.lately.poertime"/></td>
				<td id="createTime"></td>
				<td class="label"><s:text name="label.warehouse.pl.status"/></td>
                <td id="stauts"></td>
			</tr>
			<tr>
				<td class="label"><s:text name="betweenlibary.main.warehouse"/></td>
				<td width="150"  id="mainWh"></td>
				<td class="label"><s:text name="betweenlibary.addi.warehouse"/></td>
				<td width="150 " id="addiWh"></td>
			</tr>
		</table>
		<br />
		<table id="tbl-details"></table>
		<div class="buttonlist">
			<button type="button" loxiaType="button" id="back"><s:text name="button.back"/></button>
		</div>
	</div>
</body>
</html>