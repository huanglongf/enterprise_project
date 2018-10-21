<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title>Insert title here</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/warehouse-carService.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body contextpath="<%=request.getContextPath() %>">
		<div  id="tabs">
				<ul>
					<li><a href="#tabs_1">车辆车牌维护</a></li>
					<li><a href="#tabs_2">车型号配置</a></li>
				</ul>
				
						<div id="tabs_1">
							<form id="queryForm1">
								<table>
									<tr>
										<td width="10%" >规格编码:</td>
										<td width="20%"><input loxiaType="input" trim="true" maxlength="100" name="standardCode" id="standardCode"/></td>
										<td width="10%" >车辆体积:</td>
										<td width="30%"><input loxiaType="input" trim="true" maxlength="50" name="vehicleVolume1" id="vehicleVolume1"/>到<input loxiaType="input" trim="true" maxlength="50" name="vehicleVolume2" id="vehicleVolume2"/></td>
									</tr>
								</table>
							</form>
							<div class="buttonlist">
								<button type="button" loxiaType="button" class="confirm" id="search1" >查询</button>
								<button type="button" loxiaType="button" id="reset1">重置</button>
							</div>
							<table id="tbl-list1"></table>
							<div id="pager1"></div>
								<div class="buttonlist">
									<table>
										<button type="button" loxiaType="button"  class="confirm" id="add1">
											新建
										</button>
										&nbsp;
										<button type="button" loxiaType="button"  class="confirm" id="batchRemove1">
											删除
										</button>
									</table>
								</div>
								<div id="dialog1" style="text-align: center;" >
										<form id="form_update">
											<table>
											        <tr><td><input id="id1" type="hidden" name="id1"/></td></tr>
													
													<tr>
														<td>规格编码<span style="color:red;">*</span></td>
														<td><input loxiaType="input" trim="true" id="vStandardCode"/></td>
													</tr>
													<tr>
														<td>车辆体积<span style="color:red;">*</span></td>
														<td><input loxiaType="input" trim="true" id="vVehicleVolume"/></td>
													</tr>
											</table>
											</form>
										<div class="buttonlist">
												<button type="button" loxiaType="button" class="confirm" id="save1">保存</button>
												<button type="button" loxiaType="button" id="closediv1">关闭</button>
										</div>								
								</div>
						</div>
						<div id="tabs_2">
							<form id="queryForm2">
								<table>
									<tr>
										<td width="20%" >车辆标识:</td>
										<td width="30%"><input loxiaType="input" trim="true" maxlength="100"  name="vehicleCode" id="vehicleCode"/></td>
										<td width="20%" >车牌号码:</td>
										<td width="30%"><input loxiaType="input" trim="true" maxlength="100"  name="licensePlateNumber" id="licensePlateNumber"/></td>
									</tr>
									<tr>
										<td width="20%" >车辆规格:</td>
										<td width="30%">
											<select name="ruleCode" id="ruleCode"  >
												<option value="">请选择</option>
											</select>
										</td>
										<td width="20%" >车辆体积:</td>
										<td width="30%"><input loxiaType="input" trim="true" maxlength="100"  name="vehicleVolume3" id="vehicleVolume3"/>到<input loxiaType="input" trim="true" maxlength="100" name="vehicleVolume4" id="vehicleVolume4" /></td>
									</tr>
									<tr>
										<td width="20%" >用车时间:</td>
										<td width="30%"><input loxiaType="date" showTime="true" trim="true" maxlength="100"  name="useTime" id="useTime"/></td>
									</tr>
								</table>
							</form>
							<div class="buttonlist">
								<button type="button" loxiaType="button" class="confirm" id="search2" >查询</button>
								<button type="button" loxiaType="button" id="reset2">重置</button>
							</div>
							<table id="tbl-list2"></table>
							<div id="pager2"></div>
								<div class="buttonlist">
									<table>
										<button type="button" loxiaType="button"  class="confirm" id="add2">
											新建
										</button>
										&nbsp;
										<button type="button" loxiaType="button"  class="confirm" id="batchRemove2">
											删除
										</button>
									</table>
								</div>
								<div id="dialog2" style="text-align: center;" >
										<form id="form_update">
											<table>
											        <tr><td><input id="id2" type="hidden" name="id2"/></td></tr>
													
													<tr>
														<td>车辆标识<span style="color:red;">*</span></td>
														<td><input loxiaType="input" trim="true" id="lvehicleCode"/></td>
													</tr>
													
													<tr>
														<td>车牌号码<span style="color:red;">*</span></td>
														<td><input loxiaType="input" trim="true" id="llicensePlateNumber"/></td>
													</tr>
													
													<tr>
														<td>车辆规格<span style="color:red;">*</span></td>
														<td>
														<select id="lvehicleStandard" loxiaType="select" >
															<option value="">请选择</option>
														</select>
													    </td>
													</tr>
													<tr>
														<td>使用时间<span style="color:red;">*</span></td>
														<td><input loxiaType="date" showTime="true" trim="true" id="luseTime"/></select>
													    </td>
													</tr>
											</table>
											</form>
										<div class="buttonlist">
												<button type="button" loxiaType="button" class="confirm" id="save2">保存</button>
												<button type="button" loxiaType="button" id="closediv2">关闭</button>
										</div>								
								</div>
						</div>
		</div>
</body>
</html>