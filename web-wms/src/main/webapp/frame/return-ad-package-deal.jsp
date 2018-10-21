<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inpurchase"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/return-ad-package-deal.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body>
<div id="div-list1">
		<form id="form_query">
			<table>
				
				<tr>
					<td class="label" width="10%">800ts工单编号:</td>
					<td width="20%">
						<input loxiaType="input" trim="true"  id="adOrderId1" name="adOrderId1" maxlength="80"/>
					</td>
					<td class="label" width="10%">WMS事件编号:</td>
					<td width="20%">
						<input loxiaType="input" trim="true"  id="wmsOrderId1" name="wmsOrderId1" maxlength="80"/>
					</td>
					<td class="label" width="10%">退货作业单号:</td>
					<td width="20%">
						<input loxiaType="input" trim="true"  id="extended1" name="extended1" maxlength="80"/>
					</td>
					
				</tr>
				<tr>
					<td class="label" width="10%">工单类型:</td>
					<td width="20%">
						<select  loxiaType="select" id="adOrderType1" name="adOrderType1">
							<option value="">请选择</option>
						</select>
					</td>
					<td class="label" width="10%">状态:</td>
					<td width="20%">
						<select  loxiaType="select" id="status1" name="status1">
							<option value="0">请选择</option>
							<option value="1">新建</option>
							<option value="2">处理中</option>
							<option value="3">已完成</option>
						</select>
					</td>
					<td class="label" width="10%">运输单号:</td>
					<td width="20%">
						<input loxiaType="input" trim="true" id="trankNo1" name="trankNo1"  maxlength="80"/>
					</td>
				</tr>
				<tr>
					<td class="label" width="10%">退回快递公司:</td>
					<td width="20%">
						<select  loxiaType="select" id="lpCode1" name="lpCode1">
							<option value="">请选择</option>
						</select>
					</td>
					<td class="label" width="10%">创建时间:</td>
					<td colspan="3" width="40%"><input loxiaType="date" showTime="true" style="width: 40%" id="fromTime" name="fromTime"/>到<input loxiaType="date" showTime="true" style="width: 40%" id="endTime" name="endTime"/></td>
				</tr>
				<tr>
					<td class="label" width="10%">是否已操作:</td>
					<td width="20%">
						<select  loxiaType="select" id="opStatus1" name="opStatus1">
							<option value="2">请选择</option>
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</td>
					<td class="label" width="10%">SKU编码:</td>
					<td width="20%">
						<input loxiaType="input" trim="true"  id="skuId1" name="skuId1" maxlength="80"/>
					</td>
				</tr>
				
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search">查询</button>
			<button loxiaType="button" id="reset"><s:text name="button.reset"/></button>
		</div>
		<table id="tbl-ad-package-deal"></table>
		<div id="pager"></div>
</div>

<div id="div-list2">
<div>
		<table> 
						<tr>
							<td class="label" width="10%">800ts工单编号:</td>
							<td width="20%">
								<input loxiaType="input" trim="true" id="adOrderId2" name="adOrderId2"  maxlength="80" disabled="disabled"/>
							</td>
							
							<td class="label" width="10%">退货作业单号:</td>
							<td width="20%">
								<input loxiaType="input" trim="true" id="extended2" name="extended2"  maxlength="80" disabled="disabled"/>
							</td>
							
							<td class="label" width="10%">退回快递公司:</td>
							<td width="20%">
								<input loxiaType="input" trim="true"  id="lpCode2" name="lpCode2" maxlength="80" disabled="disabled"/>
							</td>
						</tr>
						
						<tr>
							<td class="label" width="10%">800ts工单类型:</td>
							<td width="20%">
								<input loxiaType="input" trim="true" id="adOrderType2" name="adOrderType2"  maxlength="80" disabled="disabled"/>
							</td>
							
							<td class="label" width="10%">800ts处理结果:</td>
							<td width="20%">
								<input loxiaType="input" trim="true" id="adStatus2" name="adStatus2"  maxlength="80" disabled="disabled"/>
							</td>
							
							<td class="label" width="10%">运输单号:</td>
							<td width="20%">
								<input loxiaType="input" trim="true" id="trankNo2" name="trankNo2"  maxlength="80" disabled="disabled"/>
							</td>
						</tr>
						
						<tr>
							<td class="label" width="10%">SKU编码:</td>
							<td width="20%">
								<input loxiaType="input" trim="true" id="skuId2" name="skuId2"  maxlength="80" disabled="disabled"/>
							</td>
							
							<td class="label" width="10%">数量:</td>
							<td width="20%">
								<input loxiaType="input" trim="true" id="quantity2" name="quantity2"  maxlength="80" disabled="disabled"/>
							</td>
							
							<td class="label" width="10%">800ts反馈备注:</td>
							<td width="20%">
								<input loxiaType="input" trim="true" id="adRemark2" name="adRemark2"  maxlength="80" disabled="disabled"/>
							</td>
						</tr>
						
						<tr>
							<td class="label" width="10%">WMS事件编号:</td>
							<td width="20%">
								<input loxiaType="input" trim="true"  id="wmsOrderId2" name="wmsOrderId2" maxlength="80" disabled="disabled"/>
							</td>
							
							<td class="label" width="10%">WMS工单类型:</td>
							<td width="20%">
								<input loxiaType="input" trim="true"  id="wmsOrderType2" name="wmsOrderType2" maxlength="80" disabled="disabled"/>
							</td>
							
							<td class="label" width="10%">WMS处理结果:</td>
							<td width="20%">
								<select  loxiaType="select" id="wmsStatus2" name="wmsStatus2">
									<option value="">请选择</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="label" width="10%">备注：</td>
							<td colspan="3">
								<textarea id='remark2' name="remark2" rows="2" cols="70"></textarea>
							</td>
							<td class="label" width="10%">退货指令:</td>
							<td width="20%">
								<input loxiaType="input" trim="true"  id="returnInstruction2" name="returnInstruction2" maxlength="80" disabled="disabled"/>
							</td>
						</tr>	
		</table>
</div>
<table id="tbl-ad-package-deal-log"></table>
<div id="pager2"></div>

		<div class="buttonlist" >
			<button loxiaType="button"  id="back">返回</button>
			<button loxiaType="button" class="confirm" id="commit">提交</button>
		</div>
		<input loxiaType="input" trim="true"  id="adOrderIdCommit" name="adOrderIdCommit" maxlength="80" class="hidden"/>
</div>
</body>
</html>