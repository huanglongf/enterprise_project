<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.modify.title"/></title>
<script type="text/javascript" src="<s:url value='/scripts/baseinfo.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/priority_city_config.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/hight_province_config.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style type="text/css">
			 #selectableTransportator table tr td{
			 border-collapse:collapse;
			 border-left:#000000 0px solid;
			 border-right:#000000 0px solid;
			 border-bottom:#000000 1px solid;text-align:center;line-height:24px;
			 }
			 #selectableTransportator table tr th{
			 border-collapse:collapse;
			 border-left:#000000 0px solid;
			 border-right:#000000 0px solid;
			 border-bottom:#000000 1px solid;text-align:center;line-height:24px;
			 }
			 #selectedTransportator table tr td{
			 border-collapse:collapse;
			 border-left:#000000 0px solid;
			 border-right:#000000 0px solid;
			 border-bottom:#000000 1px solid;text-align:center;line-height:24px;
			 }
			 #selectedTransportator table tr th{
			 border-collapse:collapse;
			 border-left:#000000 0px solid;
			 border-right:#000000 0px solid;
			 border-bottom:#000000 1px solid;text-align:center;line-height:24px;
			 }
		</style>
</head>
<body contextpath="<%=request.getContextPath() %>">	
	 <form id="warehouseForm" name="warehouseForm" method="post" enctype="multipart/form-data" target="upload">
	 <div>
	 	<table>
	 				<tr>
						<td class="label" style="font-size: 16px"><s:text name="warehouse.ou.node.code" />:</td>
						<td style="font-size: 16px"><s:property value="ou.code"/></td>
						<td class="label" style="font-size: 16px"><s:text name="warehouse.ou.node.type" />:</td>
						<td style="font-size: 16px"><s:property value="ou.ouType.displayName"/></td>
						<td class="label" style="font-size: 16px">客户信息：</td>
						<td style="font-size: 16px">
							<s:property value="customer.name"/>
							<s:if test="customerList">
								<table>
									<tr>
										<td><s:select id="customerId" list="customerList"  loxiaType="select"   listKey="id"  listValue="name"  headerValue="请选择"  headerKey="" ></s:select></td>
										<td><button type="button" loxiaType="button" class="confirm" id="setCustomer">设置客户</button></td>
										<td style="color: red;">请先设置仓库基础信息后设置客户，设置后无法修改请慎重选择</td>
									</tr>
								</table>
							</s:if>
						</td>
					</tr>
	 	</table><br/>
	 </div>
	 <div  id="tabs">
	 			<ul>
					<li><a href="#tabs_1">仓库基本信息--编辑</a></li>
					<li><a href="#tabs_2">仓库作业设置</a></li>
					<li><a href="#tabs_3">仓库附加操作设置</a></li>
					<li><a href="#tabs_4">可用快递维护</a></li>
					<li><a href="#tabs_5">自动配货配置</a></li>
					<li><a href="#tabs_6">仓库覆盖区域维护</a></li>
					<li><a href="#tabs_7">自动分配库存设置</a></li>
					<li><a href="#tabs_8" id="imp1">残次品原因及类型维护</a></li>
					<li><a href="#tabs_9">SF次晨达区域维护</a></li>
					<li><a href="#tabs_10">NIKE当日达区域维护</a></li>
					<li><a href="#tabs_11">优先发货城市配置</a></li>
					<li><a href="#tabs_12">物流商包裹称重异常校验配置</a></li>
					<li><a href="#tabs_13">优先发货省配置</a></li>
					<li><a href="#tabs_14">AD异常工单类型配置</a></li>
				</ul>
				<div id="tabs_1">
			<table>
		<tr>
			<td width="20%" class="label"><s:text name="warehouse.ou.node.name" />:</td>
			<td width="30%"><input loxiaType="input" trim="true" maxlength="100" mandatory="true" value="<s:property value='ou.name'/>" name="ou.name"/></td>
			<td width="20%" class="label">发运地</td>
			<td width="30%">
				<select name="warehouse.departure" id="departure" vl="<s:property value='warehouse.departure'/>" mandatory="true">
					<option value="">请选择</option>
				</select>
			</td>
		</tr>
		<tr>
			<td width="20%" class="label"><s:text name="warehouse.operation.mode" />:</td>
			<td width="30%">
					<select loxiaType="select" mandatory="true" name="warehouse.intOpMode">
						<s:iterator value="#request.opModeList">
							<option value="<s:property value='optionKey'/>" <s:if test="optionKey == warehouse.intOpMode">selected</s:if>>
								<s:property value="optionValue"/>
							</option>
						</s:iterator>
					</select></td>
			<td width="20%" class="label"><s:text name="warehouse.manage.mode" />:</td>
			<td width="30%">
					<select loxiaType="select" mandatory="true" name="warehouse.intManageMode">
						<s:iterator value="#request.manageModeList" >
							<option value="<s:property value='optionKey'/>" <s:if test="optionKey == warehouse.intManageMode">selected</s:if>>
								<s:property value="optionValue" escapeHtml="false"/>
							</option>
						</s:iterator>
					</select>
			</td>
		</tr>
		<tr>
			<td width="20%" class="label"><s:text name="warehouse.principal.name" />:</td>
			<td width="30%"><input loxiaType="input" trim="true" maxlength="50" mandatory="true" value="<s:property value='warehouse.pic'/>" name="warehouse.pic"/></td>
			<td width="20%" class="label"><s:text name="warehouse.principal.contact" />:</td>
			<td width="30%"><input loxiaType="input" trim="true" maxlength="255" mandatory="true" value="<s:property value='warehouse.picContact'/>" name="warehouse.picContact"/></td>
		</tr>
		<tr>
			<td width="20%" class="label"><s:text name="warehouse.phoneNum" />:</td>
			<td width="30%"><input loxiaType="input" trim="true" maxlength="255" mandatory="true" value="<s:property value='warehouse.phone'/>" name="warehouse.phone"/></td>
			<td width="20%" class="label"><s:text name="warehouse.faxNum" />:</td>
			<td width="30%"><input loxiaType="input" trim="true" maxlength="255" mandatory="true" value="<s:property value='warehouse.fax'/>" name="warehouse.fax"/></td>
		</tr>
		<tr>
			<td width="20%" class="label"><s:text name="warehouse.other.Contact1" />(面单发货地址定制):</td>
			<td colspan="3"><input loxiaType="input" trim="true" maxlength="255" value="<s:property value='warehouse.otherContact1'/>" name="warehouse.otherContact1" /></td>			
		</tr>
		<tr>
			<td width="20%" class="label"><s:text name="warehouse.other.Contact2" />:</td>
			<td colspan="3"><input loxiaType="input" trim="true" maxlength="255" value="<s:property value='warehouse.otherContact2'/>" name="warehouse.otherContact2"/></td>			
		</tr>
		<tr>
			<td width="20%" class="label"><s:text name="warehouse.other.Contact3" />:</td>
			<td colspan="3"><input loxiaType="input" trim="true" maxlength="255" value="<s:property value='warehouse.otherContact3'/>" name="warehouse.otherContact3"/></td>			
		</tr>
		<tr>
			<td width="20%" class="label"><s:text name="warehouse.all.size" />:</td>
			<td width="30%"><input loxiaType="number" trim="true" decimal="1" mandatory="true" value="<s:property value='warehouse.size'/>" name="warehouse.size"/></td>
			<td width="20%" class="label"><s:text name="warehouse.avail.size" />:</td>
			<td width="30%"><input loxiaType="number" trim="true" decimal="1" mandatory="true" value="<s:property value='warehouse.availSize'/>" name="warehouse.availSize"/></td>
		</tr>
		<tr>
			<td width="20%" class="label"><s:text name="warehouse.worker.number" />:</td>
			<td width="30%"><input loxiaType="number" trim="true" mandatory="true" value="<s:property value='warehouse.workerNum'/>" name="warehouse.workerNum"/></td>
		</tr>

		<tr>
			<td width="20%" class="label">仓库地址:</td>
			<td colspan="3"><input id = "whAddress" loxiaType="input" trim="true" maxlength="255" value="<s:property value='warehouse.address'/>" name="warehouse.address"/></td>			
		</tr>
		<tr>
			<td width="20%" class="label"><s:text name="warehouse.description" />:</td>
			<td colspan="3"><textarea loxiaType="input" trim="true" maxlength="255" name="ou.comment"><s:property value="ou.comment"/></textarea></td>			
		</tr>
		<tr>
			<td width="20%" class="label">PDA按箱收货最大SKU种类数:</td>
			<td width="30%"><input loxiaType="number" trim="true" value="<s:property value='warehouse.skuNum'/>" name="warehouse.skuNum"/></td>
			<td width="20%" class="label">PDA按箱收货最大SKU总数:</td>
			<td width="30%"><input loxiaType="number" trim="true" value="<s:property value='warehouse.skuTotal'/>" name="warehouse.skuTotal"/></td>			
		</tr>
		 <tr>
		  <td width="20%" class="label">一键创批下限订单数:</td>
		  <td width="30%"><input loxiaType="number" trim="true" value="<s:property value='warehouse.outboundOrderNum'/>" name="warehouse.outboundOrderNum" id="outBoundOrderNum"/></td>
		</tr> 
		<tr>
		<td width="20%" class="label">AGV仓库</td>
						<td width="30%">
							<s:if test="warehouse.isAgv"><input type="checkbox" value="<s:property value='warehouse.isAgv'/>" checked="checked" id="isAgv" name="warehouse.isAgv"/></s:if>
							<s:else>
								<input type="checkbox" value="<s:property value='warehouse.isAgv'/>" id="isAgv" name="warehouse.isAgv"/>
							</s:else>
						</td>
		</tr>
		<tr>
		<td width="20%" class="label">保税仓</td>
						<td width="30%">
							<s:if test="warehouse.isBondedWarehouse"><input type="checkbox" value="<s:property value='warehouse.isBondedWarehouse'/>" checked="checked" id="isBondedWarehouse" name="warehouse.isBondedWarehouse"/></s:if>
							<s:else>
								<input type="checkbox" value="<s:property value='warehouse.isBondedWarehouse'/>" id="isBondedWarehouse" name="warehouse.isBondedWarehouse"/>
							</s:else>
						</td>
		</tr>
	</table>
				</div>
				<div id="tabs_2">
					<table>
					<tr>
						<td width="40%" ><b style="font-size: 18px">出库信息设置：</b></td>
					</tr>
						<tr>
						<td width="20%" class="label">是否允许手工称重:</td>
						<td width="30%">
							<s:if test="warehouse.isManualWeighing"><input type="checkbox" value="<s:property value='warehouse.isManualWeighing'/>" checked="checked" id="isManualWeighing" name="warehouse.isManualWeighing"/></s:if>
							<s:else>
								<input type="checkbox" value="<s:property value='warehouse.isManualWeighing'/>" id="isManualWeighing" name="warehouse.isManualWeighing"/>
							</s:else>
						</td>
					<td width="20%" class="label"><span class="label">是否计算包材成本:</span></td>
						<td width="30%">
							<s:if test="warehouse.isNeedWrapStuff">
								<input type="checkbox" value="<s:property value='warehouse.isNeedWrapStuff'/>" checked="checked" id="wrapStuff" name="warehouse.isNeedWrapStuff"/>
							</s:if>
							<s:else>
								<input type="checkbox" value="<s:property value='warehouse.isNeedWrapStuff'/>" id="wrapStuff" name="warehouse.isNeedWrapStuff"/>
							</s:else>
						</td>
					</tr>
					<tr>
						<!--  <td width="20%" class="label">核对是否需要条码:</td>
						<td width="30%">
							<s:if test="warehouse.isCheckedBarcode"><input type="checkbox" value="<s:property value='warehouse.isCheckedBarcode'/>" checked="checked" id="isCheckedBarcode" name="warehouse.isCheckedBarcode"/></s:if>
							<s:else>
								<input type="checkbox" value="<s:property value='warehouse.isCheckedBarcode'/>" id="isCheckedBarcode" name="warehouse.isCheckedBarcode"/>
							</s:else>
						</td>-->
						<td width="20%" class="label">是否计算秒杀:</td>
						<td width="30%">
							<s:if test="warehouse.isSupportSecKill"><input type="checkbox" value="<s:property value='warehouse.isSupportSecKill'/>" checked="checked" id="isSupportSecKill" name="warehouse.isSupportSecKill"/></s:if>
							<s:else>
								<input type="checkbox" value="<s:property value='warehouse.isSupportSecKill'/>" id="isSupportSecKill" name="warehouse.isSupportSecKill"/>
							</s:else>
						</td>
						<td width="20%" class="label">是否计算套装组合商品:</td>
						<td width="30%">
							<s:if test="warehouse.isSupportPackageSku"><input type="checkbox" value="<s:property value='warehouse.isSupportPackageSku'/>" checked="checked" id="isSupportPackageSku" name="warehouse.isSupportPackageSku"/></s:if>
							<s:else>
								<input type="checkbox" value="<s:property value='warehouse.isSupportPackageSku'/>" id="isSupportPackageSku" name="warehouse.isSupportPackageSku"/>
							</s:else>
						</td>
					</tr>
					<tr>
						<td width="20%" class="label">是否强制使用推荐耗材:</td>
						<td width="30%">
							<s:if test="warehouse.isCheckConsumptiveMaterial"><input type="checkbox" value="<s:property value='warehouse.isCheckConsumptiveMaterial'/>" checked="checked" id="isCheckConsumptiveMaterial" name="warehouse.isCheckConsumptiveMaterial"/></s:if>
							<s:else>
								<input type="checkbox" value="<s:property value='warehouse.isCheckConsumptiveMaterial'/>" id="isCheckConsumptiveMaterial" name="warehouse.isCheckConsumptiveMaterial"/>
							</s:else>
						</td>
						<td width="20%" class="label">是否使用大件复核称重：</td>
						<td width="30%">
							<s:if test="warehouse.isBigLuxuryWeigh"><input type="checkbox" value="<s:property value='warehouse.isBigLuxuryWeigh'/>" checked="checked" id="isBigLuxuryWeigh" name="warehouse.isBigLuxuryWeigh"/></s:if>
							<s:else>
								<input type="checkbox" value="<s:property value='warehouse.isBigLuxuryWeigh'/>" id="isBigLuxuryWeigh" name="warehouse.isBigLuxuryWeigh"/>
							</s:else>
						</td>
					</tr>
					<tr>
						<td width="20%" class="label">是否校验拣货状态:</td>
						<td width="30%">
							<s:if test="warehouse.isCheckPickingStatus"><input type="checkbox" value="<s:property value='warehouse.isCheckPickingStatus'/>" checked="checked" id="isCheckPickingStatus" name="warehouse.isCheckPickingStatus"/></s:if>
							<s:else>
								<input type="checkbox" value="<s:property value='warehouse.isCheckPickingStatus'/>" id="isCheckPickingStatus" name="warehouse.isCheckPickingStatus"/>
							</s:else>
						</td>
						<td width="20%" class="label">是否强制推荐物流:</td>
						<td width="30%">
							<s:if test="warehouse.isTransMust"><input type="checkbox" value="<s:property value='warehouse.isTransMust'/>" checked="checked" id="isTransMust" name="warehouse.isTransMust"/></s:if>
							<s:else>
								<input type="checkbox" value="<s:property value='warehouse.isTransMust'/>" id="isTransMust" name="warehouse.isTransMust"/>
							</s:else>
						</td>
					</tr>
					<tr>
						<td width="20%" class="label">拣货模式分隔值（不填默认为3）:</td>
						<td width="30%">
							<input loxiaType="number"  onkeyup="value=value.replace(/[^\d.]/g,'')"  id="pickModeSku" trim="true" maxlength="10" value="<s:property value='warehouse.skuQty'/>" name="warehouse.skuQty" style="width: 30px;height: 15px" />
						</td>
						<td width="20%" class="label">是否需要扫描残次品标签:</td>
						<td width="30%">
								<s:if test="warehouse.isImperfect"><input type="checkbox" value="<s:property value='warehouse.isImperfect'/>" checked="checked" id="isImperfect" name="warehouse.isImperfect"/></s:if>
							<s:else>
								<input type="checkbox" value="<s:property value='warehouse.isImperfect'/>" id="isImperfect" name="warehouse.isImperfect"/>
							</s:else>
						</td>
					</tr>
					<tr>
						<td width="20%" class="label">是否跳过称重:</td>
						<td width="30%">
							<s:if test="warehouse.isSkipWeight"><input type="checkbox" value="<s:property value='warehouse.isSkipWeight'/>" checked="checked" id="isSkipWeight" name="warehouse.isSkipWeight"/></s:if>
							<s:else>
								<input type="checkbox" value="<s:property value='warehouse.isSkipWeight'/>" id="isSkipWeight" name="warehouse.isSkipWeight"/>
							</s:else>
						</td>
						<td width="20%" class="label">是否管理入库单到货箱数:</td>
						<td width="30%">
							<s:if test="warehouse.isCartonManager"><input type="checkbox" value="<s:property value='warehouse.isCartonManager'/>" checked="checked" id="isCartonManager" name="warehouse.isCartonManager"/></s:if>
							<s:else>
								<input type="checkbox" value="<s:property value='warehouse.isCartonManager'/>" id="isCartonManager" name="warehouse.isCartonManager"/>
							</s:else>
						</td>
					</tr>
					<tr>
						<td width="40%" ><b style="font-size: 18px">自动化仓设置：</b></td>
					</tr>
					<tr>
						<td width="20%" class="label">是否自动化仓库:</td>
						<td width="30%">
							<s:if test="warehouse.isAutoWh"><input type="checkbox" value="<s:property value='warehouse.isAutoWh'/>" checked="checked" id="isAutoWh" name="warehouse.isAutoWh"/></s:if>
							<s:else>
								<input type="checkbox" value="<s:property value='warehouse.isAutoWh'/>" id="isAutoWh" name="warehouse.isAutoWh"/>
							</s:else>
						</td>
						<td width="20%" class="label">批次订单上限:</td>
						<td width="30%">
							<input loxiaType="input" trim="true" maxlength="6" style="width: 64px;height: 15px" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/[^0-9]/g,'')}" value="<s:property value='warehouse.orderCountLimit'/>"  name="warehouse.orderCountLimit" id = "orderCountLimit"/>
						</td>
					</tr>
					<tr>
						<td width="20%" class="label">小批次容量:</td>
						<td width="30%">
							<input loxiaType="input" trim="true" maxlength="6" style="width: 64px;height: 15px" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/[^0-9]/g,'')}"  value="<s:property value='warehouse.idx2MaxLimit'/>"  name="warehouse.idx2MaxLimit" id = "idx2MaxLimit"/>
						</td>
						<td width="20%" class="label">播种区编码:</td>
						<td width="30%">
							<input loxiaType="input" id="seedingAreaCode" style="width: 64px;height: 15px" trim="true" maxlength="50" value="<s:property value='warehouse.seedingAreaCode'/>" name="warehouse.seedingAreaCode"/>
						</td>
					</tr>
					<tr>
						<td width="20%" class="label">复核区编码:</td>
						<td width="30%">
							<input loxiaType="input" id="checkingAreaCode" style="width: 64px;height: 15px"  trim="true" maxlength="50" value="<s:property value='warehouse.checkingAreaCode'/>" name="warehouse.checkingAreaCode"/>
						</td>
						<td width="20%" class="label">集货口批次数量上限:</td>
						<td width="30%">
							<input loxiaType="input" id="autoPickinglistLimit" style="width: 64px;height: 15px" trim="true" maxlength="50" value="<s:property value='warehouse.autoPickinglistLimit'/>" name="warehouse.autoPickinglistLimit"/>
						</td>
					</tr>
					<tr>
						<td width="20%" class="label">交接批次订单上限:</td>
						<td width="30%">
							<input loxiaType="input" id="handLimit" style="width: 64px;height: 15px"  trim="true" maxlength="50" value="<s:property value='warehouse.handLimit'/>" name="warehouse.handLimit"/>
						</td>
						<td width="20%" class="label">播种墙组编码:</td>
						<td width="30%">
							<input loxiaType="input" id="autoSeedGroup" style="width: 64px;height: 15px"  trim="true" maxlength="50" value="<s:property value='warehouse.autoSeedGroup'/>" name="warehouse.autoSeedGroup"/>
						</td>
					</tr>
					<tr>
						<td width="20%" class="label">人工集货配货批次上限:</td>
						<td width="30%">
							<input loxiaType="input" id="manpowerPickinglistLimit" style="width: 64px;height: 15px"  trim="true" maxlength="6" value="<s:property value='warehouse.manpowerPickinglistLimit'/>" name="warehouse.manpowerPickinglistLimit"/>
						</td>
						<td width="20%" class="label">拣货配货批次上限:</td>
						<td width="30%">
							<input loxiaType="input" id="totalPickinglistLimit" style="width: 64px;height: 15px"  trim="true" maxlength="6" value="<s:property value='warehouse.totalPickinglistLimit'/>" name="warehouse.totalPickinglistLimit"/>
						</td>
					</tr>
					<tr>
						<td width="20%" class="label">仅跨多个仓库区域的配货批次进入人工集货:</td>
						<td width="30%">
							<s:if test="warehouse.isManpowerConsolidation"><input type="checkbox" value="<s:property value='warehouse.isManpowerConsolidation'/>" checked="checked" id="isManpowerConsolidation" name="warehouse.isManpowerConsolidation"/></s:if>
							<s:else>
								<input type="checkbox" value="<s:property value='warehouse.isManpowerConsolidation'/>" id="isManpowerConsolidation" name="warehouse.isManpowerConsolidation"/>
							</s:else>
						</td>
						<td width="20%" class="label">团购复核工作台编码:</td>
						<td width="30%">
							<input loxiaType="input" id="groupWorkbenchCode" style="width: 64px;height: 15px"  trim="true" maxlength="6" value="<s:property value='warehouse.groupWorkbenchCode'/>" name="warehouse.groupWorkbenchCode"/>
						</td>
					</tr>
					<tr>
						<td width="20%" class="label">特殊处理复核工作台编码:</td>
						<td width="30%">
							<input loxiaType="input" id="specialWorkbenchCode" style="width: 64px;height: 15px"  trim="true" maxlength="6" value="<s:property value='warehouse.specialWorkbenchCode'/>" name="warehouse.specialWorkbenchCode"/>
						</td>
						<td width="20%" class="label">特殊时效（当日）复核工作台编码:</td>
						<td width="30%">
							<input loxiaType="input" id="sameDayWorkbenchCode" style="width: 64px;height: 15px"  trim="true" maxlength="6" value="<s:property value='warehouse.sameDayWorkbenchCode'/>" name="warehouse.sameDayWorkbenchCode"/>
						</td>
					</tr>
					<tr>
						<td width="20%" class="label">特殊时效（次日）复核工作台编码:</td>
						<td width="30%">
							<input loxiaType="input" id="nextDayWorkbenchCode" style="width: 64px;height: 15px"  trim="true" maxlength="6" value="<s:property value='warehouse.nextDayWorkbenchCode'/>" name="warehouse.nextDayWorkbenchCode"/>
						</td>
						<td width="20%" class="label">特殊时效（次晨）复核工作台编码:</td>
						<td width="30%">
							<input loxiaType="input" id="nextMorningWorkbenchCode" style="width: 64px;height: 15px"  trim="true" maxlength="6" value="<s:property value='warehouse.nextMorningWorkbenchCode'/>" name="warehouse.nextMorningWorkbenchCode"/>
						</td>
					</tr>
					<tr>
						<td width="40%" ><b style="font-size: 18px">虚拟仓特殊设置：</b></td>
					</tr>
					<tr>
						<td width="20%" class="label">是否不校验保质期:</td>
						<td width="30%">
							<s:if test="warehouse.isNotExpireDate"><input type="checkbox" value="<s:property value='warehouse.isNotExpireDate'/>" checked="checked" id="isNotExpireDate" name="warehouse.isNotExpireDate"/></s:if>
							<s:else>
								<input type="checkbox" value="<s:property value='warehouse.isNotExpireDate'/>" id="isNotExpireDate" name="warehouse.isNotExpireDate"/>
							</s:else>
						</td>
						<td width="20%" class="label">是否不校验SN商品 :</td>
						<td width="30%">
							<s:if test="warehouse.isNotSn"><input type="checkbox" value="<s:property value='warehouse.isNotSn'/>" checked="checked" id="isNotSn" name="warehouse.isNotSn"/></s:if>
							<s:else>
								<input type="checkbox" value="<s:property value='warehouse.isNotSn'/>" id="isNotSn" name="warehouse.isNotSn"/>
							</s:else>
						</td>
					</tr>
					</table><br/>
					<table>
					<tr>
						<td width="20%" ><b style="font-size: 18px">物流信息设置：</b></td>
						<td width="30%" ></td>
					</tr>
					<tr>
							<td colspan="2">顺丰接口信息</td>
							<td width="20%" class="label">是否使用顺丰电子面单:</td>
							<td width="30%">
								<s:if test="warehouse.isSfOlOrder"><input type="checkbox" value="<s:property value='warehouse.isSfOlOrder'/>" checked="checked" id="isSfOlOrder" name="warehouse.isSfOlOrder"/></s:if>
								<s:else>
									<input type="checkbox" value="<s:property value='warehouse.isSfOlOrder'/>" id="isSfOlOrder" name="warehouse.isSfOlOrder"/>
								</s:else>
							</td>
						</tr>
						<tr>
							<td class="label">
								省
							</td>
							<td >
								<select id="selProvince" loxiaType="select" mandatory="true" name="warehouse.province">
									<s:iterator value="#request.provinces">
										<option value="<s:property value='province'/>"<s:if test="province == warehouse.province">selected</s:if>>
											<s:property value="province"/>
										</option>
									</s:iterator>
								</select>
							</td>
							<td class="label">
								市
							</td>
							<td>
								<select id="selCity" loxiaType="select" mandatory="true" name="warehouse.city">
									<option value=''>--请选择--</option>
									<s:iterator value="#request.cities">
										<option value="<s:property value='city'/>"<s:if test="city == warehouse.city">selected</s:if>>
											<s:property value="city"/>
										</option>
									</s:iterator>
								</select>
							</td>
						</tr>
						<tr>
							<td class="label">
								区
							</td>
							<td>
								<select id="selDistrict" loxiaType="select" mandatory="true" name="warehouse.district">
									<option value=''>--请选择--</option>
									<s:iterator value="#request.districts">
										<option value="<s:property value='district'/>"<s:if test="district == warehouse.district">selected</s:if>>
											<s:property value="district"/>
										</option>
									</s:iterator>
								</select>
							</td>
							<td class="label" >
								邮编
							</td>
							<td >
								<input loxiaType="input" id="zipcode" trim="true" maxlength="50" value="<s:property value='warehouse.zipcode'/>" name="warehouse.zipcode"/>
							</td>
						</tr>
						<tr>
							<%-- <td class="label" >
								SF仓库编码
							</td>
							<td >
								<input loxiaType="input" id="sfWhCode" trim="true" maxlength="50" value="<s:property value='warehouse.sfWhCode'/>" name="warehouse.sfWhCode"/>
							</td> --%>
							<td class="label">
								仓库城市编码
							</td>
							<td >
								<input loxiaType="input" id="cityCode" trim="true" maxlength="50" value="<s:property value='warehouse.cityCode'/>" name="warehouse.cityCode"/>
							</td>
							<td colspan="2"></td>
						</tr>
						<tr>
							<td class="label" >
								SF是否第三方付款
							</td>
							<td >
								<s:if test="warehouse.isThirdPartyPaymentSF"><input type="checkbox" value="<s:property value='warehouse.isThirdPartyPaymentSF'/>" checked="checked" id="isThirdPartyPaymentSF" name="warehouse.isThirdPartyPaymentSF"/></s:if>
								<s:else>
									<input type="checkbox" value="<s:property value='warehouse.isThirdPartyPaymentSF'/>" id="isThirdPartyPaymentSF" name="warehouse.isThirdPartyPaymentSF"/>
								</s:else>
							</td>
							<td>
							</td>
							<td >
							</td>
						</tr>
					  <tr>
							<td colspan="2">
								是否使用EMS电子面单
								<s:if test="warehouse.isEmsOlOrder"><input type="checkbox" value="<s:property value='warehouse.isEmsOlOrder'/>" checked="checked" id="isEmsOlOrder" name="warehouse.isEmsOlOrder"/></s:if>
								<s:else>
									<input type="checkbox" value="<s:property value='warehouse.isEmsOlOrder'/>" id="isEmsOlOrder" name="warehouse.isEmsOlOrder"/>
								</s:else>
							</td>
			                   <td colspan="2">
								是否使用STO电子面单
								<s:if test="warehouse.isOlSto"><input type="checkbox" value="<s:property value='warehouse.isOlSto'/>" checked="checked" id="isOlSto" name="warehouse.isOlSto"/></s:if>
								<s:else>
									<input type="checkbox" value="<s:property value='warehouse.isOlSto'/>" id="isOlSto" name="warehouse.isOlSto"/>
								</s:else>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								是否使用ZTO电子面单
								<s:if test="warehouse.isZtoOlOrder"><input type="checkbox" value="<s:property value='warehouse.isZtoOlOrder'/>" checked="checked" id="isZtoOlOrder" name="warehouse.isZtoOlOrder"/></s:if>
								<s:else>
									<input type="checkbox" value="<s:property value='warehouse.isZtoOlOrder'/>" id="isZtoOlOrder" name="warehouse.isZtoOlOrder"/>
								</s:else>
							</td>
							<td colspan="2">
								是否使用TTK电子面单
								<s:if test="warehouse.isTtkOlOrder"><input type="checkbox" value="<s:property value='warehouse.isTtkOlOrder'/>" checked="checked" id="isTtkOlOrder" name="warehouse.isTtkOlOrder"/></s:if>
								<s:else>
									<input type="checkbox" value="<s:property value='warehouse.isTtkOlOrder'/>" id="isTtkOlOrder" name="warehouse.isTtkOlOrder"/>
								</s:else>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								是否使用YTO电子面单
								<s:if test="warehouse.isYtoOlOrder"><input type="checkbox" value="<s:property value='warehouse.isYtoOlOrder'/>" checked="checked" id="isYtoOlOrder" name="warehouse.isYtoOlOrder"/></s:if>
								<s:else>
									<input type="checkbox" value="<s:property value='warehouse.isYtoOlOrder'/>" id="isYtoOlOrder" name="warehouse.isYtoOlOrder"/>
								</s:else>
							</td>
							<td colspan="2">
								是否使用DHL电子面单
								<s:if test="warehouse.isDhlOlOrder"><input type="checkbox" value="<s:property value='warehouse.isDhlOlOrder'/>" checked="checked" id="isDhlOlOrder" name="warehouse.isDhlOlOrder"/></s:if>
								<s:else>
									<input type="checkbox" value="<s:property value='warehouse.isDhlOlOrder'/>" id="isDhlOlOrder" name="warehouse.isDhlOlOrder"/>
								</s:else>
							</td>
						</tr>
						
						<tr>
							<td colspan="2">
								是否使用CXC电子面单
								<s:if test="warehouse.isCxcOlOrder"><input type="checkbox" value="<s:property value='warehouse.isCxcOlOrder'/>" checked="checked" id="isCxcOlOrder" name="warehouse.isCxcOlOrder"/></s:if>
								<s:else>
									<input type="checkbox" value="<s:property value='warehouse.isCxcOlOrder'/>" id="isCxcOlOrder" name="warehouse.isCxcOlOrder"/>
								</s:else>
							</td>
							<td class="label" >
								顺丰COD模板
							</td>
							<td>
								<input loxiaType="input" id="sfWhCodeCod" trim="true" maxlength="50" value="<s:property value='warehouse.sfWhCodeCod'/>" name="warehouse.sfWhCodeCod"/>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								是否使用RFD电子面单
								<s:if test="warehouse.isRfdOlOrder"><input type="checkbox" value="<s:property value='warehouse.isRfdOlOrder'/>" checked="checked" id="isRfdOlOrder" name="warehouse.isRfdOlOrder"/></s:if>
								<s:else>
									<input type="checkbox" value="<s:property value='warehouse.isRfdOlOrder'/>" id="isRfdOlOrder" name="warehouse.isRfdOlOrder"/>
								</s:else>
							</td>
							<<td class="label" >
								顺丰非COD模板
							</td>
							<td>
								<input loxiaType="input" id="sfWhCode" trim="true" maxlength="50" value="<s:property value='warehouse.sfWhCode'/>" name="warehouse.sfWhCode"/>
							</td>
						</tr>
						<tr>
							<td colspan="4">&nbsp;</td>
						</tr>
						<tr>
							<td class="label">是否使用流水开票:</td>
							<td>
								<s:if test="warehouse.isMqInvoice"><input type="checkbox" value="<s:property value='warehouse.isMqInvoice'/>" checked="checked" id="isMqInvoice" name="warehouse.isMqInvoice"/></s:if>
								<s:else>
									<input type="checkbox" value="<s:property value='warehouse.isSfOlOrder'/>" id="isMqInvoice" name="warehouse.isMqInvoice"/>
								</s:else>
							</td>
							<td class="label">
								流水开票队列名
							</td>
							<td>
								<input loxiaType="input" id="invoiceTaxMqCode" trim="true" maxlength="50" value="<s:property value='warehouse.invoiceTaxMqCode'/>" name="warehouse.invoiceTaxMqCode"/>
							</td>
						</tr>
					<%-- 	<tr>
							<td class="label">是否再次推荐SF云仓逻辑:</td>
							<td>
								<s:if test="warehouse.isSuggest"><input type="checkbox" value="<s:property value='warehouse.isSuggest'/>" checked="checked" id="isSuggest" name="warehouse.isSuggest"/></s:if>
								<s:else>
									<input type="checkbox" value="<s:property value='warehouse.isSuggest'/>" id="isSuggest" name="warehouse.isSuggest"/>
								</s:else>
							</td>
						</tr> --%>
					</table>
				</div>
				<div id="tabs_3">
				<s:hidden id="statusName" name="wss.statusName"></s:hidden>
				<!--  <input type="text" id = "statusName" name = "wss.statusName" />-->
					<table id="warehouse_status_list" >
					
					</table>
				</div>
				<div id="tabs_4">
					<table>
						<tr>
							<td>
								<div id="selectedTransportator">
								已选快递<br/>
								<table style="border:#000000 1px solid;width: 320px;" cellspacing="0" cellpadding="0" >
									<thead>
										<tr>
											<th>快递编码</th>
											<th>名称</th>
											<th>是否支持COD</th>
										</tr>
									</thead>
									<tbody id="selectedTransportatorBody"></tbody>
								</table>
								</div>
							</td>
							<td>
								<div id="selectOperator">
									<button type="button" loxiaType="button" id="addTransportator" style="margin-bottom:40px;"  class="confirm">添加</button><br/>
									<button type="button" loxiaType="button" id="deleteTransportator">删除</button>
								</div>
							</td>
							<td>
								<div id="selectableTransportator">
								可选快递<br/>
								<table style="border:#000000 1px solid;width: 320px;" cellspacing="0" cellpadding="0" >
									<thead>
										<tr>
											<th><input type="checkbox" name="checkbox" value="checkbox" id="checkbox"></th>
											<th>快递编码</th>
											<th>名称</th>
											<th>是否支持COD</th>
										</tr>
									</thead>
									<tbody id="selectableTransportatorBody"></tbody>
								</table>
								</div>
							</td>
						</tr>
					</table>
				</div>
				<div id="tabs_5">
				<table width="50%">
					<tr>
						<td width="30%" class="label">仓库编码：<s:property value='ou.code'/></td>
						<td width="30%" class="label">仓库名称：<s:property value='ou.name'/></td>
					</tr>
					<tr>
						<td width="30%" class="label">是否自动创建配货清单：</td>
						<td width="30%"><input type="checkbox" id = "autoCheckBox"/></td>
					</tr>
					<tr>
						<td width="30%" class="label">间隔时间（分钟）：</td>
						<td width="30%">
							<input loxiaType="input" trim="true" maxlength="100" name="jgTime" id = "jgTime"/>
						</td>
					</tr>
					<tr>
						<td width="30%" class="label">下次执行时间（无特殊需求无需指定）：</td>
						<td width="30%">
							<input loxiaType="date" showTime="true" min="today" id="zxTime"/>
						</td>
					</tr>
				</table>
				<br/>
				<!--  <input type="text" id = "statusName" name = "wss.statusName" />-->
					<table id="warehouse-autopl-list" >
					
					</table>
				</div>
				<div id="tabs_6">
				<table>
					<tr>
						<td class="label">导入文件</td>
					<td >
						
						 <input type="file" loxiaType="input" id="file" name="file" style="width: 200px;"/> 
					</td>
					<td>
						<button loxiaType="button" class="confirm" id="import"><s:text name="附加导入"></s:text> </button>
						<button loxiaType="button" class="confirm" id="coverImport"><s:text name="覆盖导入"></s:text> </button>
					</td>
					<td >
						<a class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=仓库覆盖区域导入.xls&inputPath=tplt_import_warehouse_coverage_area.xls" role="button">
							<span class="ui-button-text">模版文件下载</span>
						</a>
					</td>
					</tr>
					<tr>
					<td colspan="4">
						<span><font color="red">维护到区将只能送货到指定的区，否则全市范围送货</font></span>
					</td>
					</tr>
				</table>
				<table id="warehouse_coverage_area_list" >
					
				</table>
				<div id="pager"></div>
				</div>
				<div id="tabs_7">
					<table>
						<tr>
							<td width="220px" class="label">是否自动分配订单库存:</td>
							<td width="200px">
								<s:if test="warehouse.isAutoOcp"><input type="checkbox" value="<s:property value='warehouse.isAutoOcp'/>" checked="checked" id="isAutoOcp" name="warehouse.isAutoOcp"/></s:if>
								<s:else>
									<input type="checkbox" value="<s:property value='warehouse.isAutoOcp'/>" id="isAutoOcp" name="warehouse.isAutoOcp"/>
								</s:else>
							</td>
						</tr>
						<tr>
							<td width="220px" class="label">是否启用订单占用区域优先级的校验:</td>
							<td width="200px">
								<s:if test="warehouse.isAreaOcpInv"><input type="checkbox" value="<s:property value='warehouse.isAreaOcpInv'/>" checked="checked" id="isAreaOcpInv" name="warehouse.isAreaOcpInv"/></s:if>
								<s:else>
									<input type="checkbox" value="<s:property value='warehouse.isAreaOcpInv'/>" id="isAreaOcpInv" name="warehouse.isAreaOcpInv"/>
								</s:else>
							</td>
						</tr>
						<tr>
							<td width="220px" class="label">分配失败次数上限：</td>
							<td width="200px">
								<input loxiaType="input" trim="true"  onkeyup="value=value.replace(/[^\d.]/g,'')"  value="<s:property value='warehouse.ocpErrorLimit'/>" name="warehouse.ocpErrorLimit" id = "ocpErrorLimit"/>
							</td>
						</tr>
					</table>
				</div>
				<div id="tabs_8">
					<div id="imperfect">
						<table id="tbl-bichannel-imperfect-list"></table>
						<div id="pagerImperfect"></div>
					</div>
					<div class="buttonlist" id="imperfect-add">
						<table width="35%" id="tbl-bichannel-imperfect-add">
							<tr>
								<td align="left"><b>选择文件:</b></td>
								<td  align="center">
										 <input type="file" name="fileType" loxiaType="input" id="fileType" style="width:200px"/> 
								</td>
								<td> 
									<button loxiaType="button" class="confirm" id="import1">导入</button>
						            <a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_imperfect_type"></s:text>.xls&inputPath=tplt_imperfect.xls">模板文件下载</a>
						        </td>
							</tr>
						</table>
					</div>
					<div id="imperfectLine" class="hidden">
						<table id="tbl-bichannel-imperfect-line-list"></table>
						<div id="pagerImperfectLine"></div>
					</div>
					<div id="imperfectLineadd" class="hidden">
						<div class="buttonlist" id="imperfectLine-add">
							<table width="35%" id="tbl-bichannel-imperfect-add">
								<tr>
									<td align="left"><b>选择文件:</b></td>
									<td  align="center">
									<input id="imperfectId" name="imperfectId" type="text" class="hidden"/>
											 <input type="file" name="filewhy" loxiaType="input" id="filewhy" style="width:200px"/> 
									</td>
									<td> 
										<button loxiaType="button" class="confirm" id="importwhy">导入</button>
							            <a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_imperfect_type"></s:text>.xls&inputPath=tplt_imperfect.xls">模板文件下载</a>
							        </td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<div id="tabs_9">
					<table>
						<tr>
							<td>
								<input type="file" loxiaType="input" id="fileSFC" name="fileSFC" style="width: 300px;"/>
							</td>
							<td>
								<button loxiaType="button" class="confirm" id="importsfc"><s:text name="导入(修改)1配置"></s:text> </button>
								<button loxiaType="button" class="confirm" id="exprotsfc"><s:text name="导出现有配置"></s:text> </button>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<span><font color="red">初次维护/编辑请先点击“导出现有配置”按钮,编辑内容后再将文件导入，并点击“导入（修改）配置”按钮，即可完成配置的初始化或者修改</font></span>
							</td>
						</tr>
					</table>
					<table id="sf_next_morning_config_list" >
					</table>
					<div id="pagerSFC"></div>
				</div>
				<div id="tabs_10">
					<table>
						<tr>
							<td class="label"><s:text name="店铺"/>：</td>
							<td colspan="6">
								<div style="float: left">
									<select id="companyshop" name="ls.owner" loxiaType="select">
										<option value=""><s:text name="请选择"></s:text></option>
									</select>
								</div>
								<div style="float: left">
									<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">升级金额：</td>
							<td><input loxiaType="input" trim="true" name="ls.totalActual" id="totalActual"/></td>
							<td class="label">省：</td>
							<td><input loxiaType="input" trim="true" name="ls.province" id="lsProvince"/></td>
							<td class="label">市：</td>
							<td><input loxiaType="input" trim="true" name="ls.city" id="lsCity"/></td>
							<td class="label">区：</td>
							<td><input loxiaType="input" trim="true" name="ls.district" id="lsDistrict"/></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="7">
								<span><font color="red">
									时效维护:设置当前地区的时间段配置,默认为当天00:00-10:30和17:30-23:59为快递类型升级时间
								</font></span>
							</td>
						</tr>
						<tr>
							<td class="label">时间段1：</td>
							<td><input loxiaType="input" trim="true" id="lsTimeOneBegin" placeholder="00:00"/></td>
							<td class="label">到：</td>
							<td><input loxiaType="input" trim="true" id="lsTimeOneEnd" placeholder="10:30"/></td>
							<td colspan="3"></td>
						</tr>
						<tr>
							<td class="label">时间段2：</td>
							<td><input loxiaType="input" trim="true" id="lsTimeTwoBegin" placeholder="17:00"/></td>
							<td class="label">到：</td>
							<td><input loxiaType="input" trim="true" id="lsTimeTwoEnd" placeholder="23:59"/></td>
							<td colspan="3"><button type="button" loxiaType="button" class="confirm" id="saveNikeConfig"><s:text name="保存"></s:text> </button></td>
						</tr>
					</table>
					<table class="buttonlist">
						<tr>
							<td>
								<input type="file" loxiaType="input" id="fileNIKE" name="fileNIKE" style="width: 300px;"/>
							</td>
							<td>
								<button type="button" loxiaType="button" class="confirm" id="importNIKE"><s:text name="导入(修改)配置"></s:text> </button>
								<button type="button" loxiaType="button" class="confirm" id="exprotNIKE"><s:text name="导出现有配置"></s:text> </button>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<span><font color="red">初次维护/编辑请先点击“导出现有配置”按钮,编辑内容后再将文件导入，并点击“导入（修改）配置”按钮，即可完成配置的初始化或者修改</font></span>
							</td>
						</tr>
					</table>
					<table id="nike_today_send_config_list" >
					</table>
					<div id="pagerNIKE"></div>
				</div>
				<div id="tabs_11">
					<s:hidden id="" name=""></s:hidden>
						<form id="form_query" method="post">
							<table>
								<td width="40%" class="label">城市名称 : </td>
<%-- 								<td width="30%"><input loxiaType="input" trim="true" maxlength="100" mandatory="true" value="<s:property value='warehouse.pic'/>" name="warehouse.pic"/></td> --%>
									<td><input loxiaType="input" trim="true" name="psccCommand.cityName" id="cityName" maxlength="80" style="width: 137%;" mandatory="true"/></td>
								<%-- <span style="float: right; margin-right: 770px; padding-top: 3px; color: red; font-weight: bold;">
									注意：填写请不要包含"市"字样 便于后续的数据匹配逻辑 (北京市，仅录入北京)
								</span> --%>
								</tr>
							</table>
						</form>
						<div class="buttonlist">
							<button loxiaType="button" class="confirm" id="add">新增</button>
						</div>
						<table id="tbl-inbound-purchase"></table>
						<div id="pagerCity"></div>
						<div class="buttonlist">
							<button loxiaType="button" class="confirm" id="remove">删除</button>
						</div>
					
<!-- 						<table id="" > -->
						
<!-- 						</table> -->
				</div>
				
				<div id="tabs_12">
				<form id="queryForm1" name="queryForm1">
					<table>
						<tr>
							<td>
							  物流商:<select id="expCode" name="transportatorWeigth.expCode"  loxiaType="select">
							        <option value="">-请选择-</option>
									</select>
							</td>
						</tr>
					</table>
				</form>
				<div class="buttonlist">
				   <button loxiaType="button" class="confirm" id="query" >查询</button>
				   <button loxiaType="button" class="confirm" id="create">创建</button>
				</div>
					<table id="check_lpCode_package_info" >
					</table>
					<div id="pagerPackageInfo"></div>
				</div>
				<div id="tabs_13">
					<s:hidden id="" name=""></s:hidden>
						<form id="form_query" method="post">
							<table>
								<td width="40%" class="label">省份名称 : </td>
									<td><input loxiaType="input" trim="true" name="hightProvinceConfig.priorityName" id="priorityName" maxlength="80" style="width: 137%;" mandatory="true"/></td>
								</tr>
							</table>
						</form>
						<div class="buttonlist">
							<button loxiaType="button" class="confirm" id="addHightProvince">新增</button>
						</div>
						<table id="tbl-hight_province_Config"></table>
						<div id="pagerProvince"></div>
						<div class="buttonlist">
							<button loxiaType="button" class="confirm" id="removeHightProvince">删除</button>
						</div>
				</div>
				<div id="tabs_14">
				<form method="post" enctype="multipart/form-data" id="importForm2" name="importForm2" target="upload">
				     <table class="buttonlist">
						<tr>
							<td>
								<input type="file" loxiaType="input" id="fileAd" name="fileAd" style="width: 300px;"/>
							</td>
							<td>
								<button type="button" loxiaType="button" class="confirm" id="importAd"><s:text name="导入(修改)配置"></s:text> </button>
								<button type="button" loxiaType="button" class="confirm" id="exprotAd"><s:text name="导出现有配置"></s:text> </button>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<span><font color="red">初次维护/编辑请先点击“导出现有配置”按钮,编辑内容后再将文件导入，并点击“导入（修改）配置”按钮，即可完成配置的初始化或者修改</font></span>
							</td>
						</tr>
					</table>
					<table id="ad_list" >
					</table>
					<div id="pagerAd"></div>
				</form>
				</div>
			<div id="dialog_addWeight" style="text-align: center;" >
				<table>
				        <tr><td><input id="t_ma_transportator_weigth_id" type="hidden" /></td></tr>
						<tr>
							<td>物流商 <span style="color:red;">*</span></td>
							<td><select id="addexpCode"  loxiaType="select">
							        <option value="">-请选择-</option>
								</select>
						    </td>
						    <td></td>
						</tr>
						<tr>
							<td>单包裹最大重量</td>
							<td><input loxiaType="input" trim="true" id="add_max_weight"/></td>
							<td>KG</td>
						</tr>
						<tr>
							<td>单包裹最小重量</td>
							<td><input loxiaType="input" trim="true" id="add_min_weight"/></td>
							<td>KG</td>
						</tr>
						
						<tr>
							<td>单包裹容许重量差异范围</td>
							<td><input loxiaType="input" trim="true" id="add_weight_difference_percent"/></td>
							<td>%</td>
						</tr>
						
				</table>
			<div class="buttonlist">
					<button type="button" loxiaType="button" class="confirm" id="saveWeight">保存</button>
					<button type="button" loxiaType="button" id="closediv">关闭</button>
			</div>
         </div>
				
	</div>
</form>
<div class="buttonlist" id="saveArea">
	<button type="button" loxiaType="button" class="confirm" id="WHsaveBtn"><s:text name="button.save"/></button>
</div>
<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload" class="hidden">

</form>
<iframe id="upload" name="upload" class="hidden"></iframe>
<iframe id="exportFrame" class="hidden" target="_blank"></iframe>
<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>
