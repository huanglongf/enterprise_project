<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/mq-switch-manager.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body>
	<div>
	<form name="searchForm" id="searchForm">
		<table>
			<tr>
				<td class="label"><s:text name="消息类型:"></s:text></td> 
				<td><input loxiaType="input" trim="true" id="msgType" name="messageConfig.msgType"/></td> 
				<td class="label"><s:text name="消息队列:"></s:text></td>
				<td><input loxiaType="input" trim="true" id="topic" name="messageConfig.topic"/></td>
			</tr>
		</table>
	</form>
	<button  loxiaType="button" class="confirm" id="search"><s:text name="查询"></s:text> </button>
	<button  loxiaType="button" id="reset"><s:text name="重设"></s:text> </button>
	</br>
	</br>
	<table id="tbl-mqMessageList"></table>
	<div id="pager_query"></div>
	</br>
	</br>
	</br>
	<table>
			<tr>
						<td width="15%" class="label" style="font-size: 14px">MQ和多线程的开关：</td>
						<td width="30%" style="font-size: 14px">
							<label><input  name='saveWeight' type="radio" value="多线程" />多线程</label>   
							<label><input  name='saveWeight' type="radio" value="MQ" />MQ</label> 
						</td>
			</tr>
			<tr>
						<!-- <td colspan="3" ><textarea loxiaType="input" name="threadConfig.memo" max="255" id="threadConfig_memo" maxLength="255"></textarea></td> -->
						<td colspan="5"  style="color: red;font-weight: bold;" id="remind">*此修改只对  消息类型为：	NewInventoryOccupyManagerImpl.occupiedInventoryByStaToMq 消息队列为：	MQ_wms3_occupy_inventory 描述为：占用库出库单存丢MQ开关 这条数据  并且 是否启用为：1时 才有效</td>
			</tr>
		</table>
		<button  loxiaType="button" class="confirm" id="updateThreadMQ"><s:text name="修改"></s:text> </button>
	</div>
	</br>
	</br>
</body>
</html>