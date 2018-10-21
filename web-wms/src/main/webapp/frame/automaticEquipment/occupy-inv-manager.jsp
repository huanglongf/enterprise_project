<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title>配货失败处理</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/automaticEquipment/occupy-inv-manager.js' includeParams='none' encode='false'/>"></script>
<style>
.showborder {
	border: thin;
}
</style>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="orderList">
		<form id="queryForm">
			<table>
				<tr>
					<td width="15%" class="label">仓库区域编码：</td>
					<td width="25%"><input loxiaType="input"  name="zoonCode" showTime="true" id="zoonCode"></input></td>
					<td colspan="2"></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="search" >查询</button>
			<button type="button" loxiaType="button" id="reset">重置</button>
		</div>
		<table id="tbl-zoonOcpInvList"></table>
		<div id="pager"></div>
			<div class="buttonlist">
			<table>
				<button type="button" loxiaType="button"  class="confirm" id="add">
					新建
				</button>
				&nbsp;
				<button type="button" loxiaType="button"  class="confirm" id="batchRemove">
					删除
				</button>
			</table>
		</div>
	</div>
		<div id="dialog" style="text-align: center;" >
		<form id="form_update">
				<table>
				        <tr><td><input id="zoon_ocpsort_id" type="hidden" /></td></tr>
						
						<tr>
							<td>配货模式<span style="color:red;">*</span></td>
							<td><select id="saleModle1" loxiaType="select" >
								<option value="1">单件</option>
								<option value="10">多件</option>
								<option value="20">团购</option>
								<option value="30">秒杀</option>
								<option value="2">套装组合</option>
								<option value="3">O2OQS</option>
								<option value="预售">预售</option>
							</select>
						    </td>
						</tr>
						<tr>
							<td>仓库区域<span style="color:red;">*</span></td>
							<td><select id="zoonCode1" loxiaType="select" ></select>
						    </td>
						</tr>
						<tr>
							<td>优先级<span style="color:red;">*</span></td>
							<td><input loxiaType="input" trim="true" id="sort"/></td>
						</tr>
				</table>
				</form>
			<div class="buttonlist">
					<button type="button" loxiaType="button" class="confirm" id="save">保存</button>
					<button type="button" loxiaType="button" id="closediv">关闭</button>
			</div>
         </div>
	</div>
</body>
</html>