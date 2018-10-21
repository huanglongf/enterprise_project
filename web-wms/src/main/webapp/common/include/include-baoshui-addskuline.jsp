<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<s:url value='/scripts/common/include/include-baoshui-skulineadd.js' includeParams='none' encode='false'/>"></script>
<div id="shopQueryDialog2" style="display: none;">
<input type="hidden" id="reId"/>
	<!-- <form id="shopQueryDialogForm" name="shopQueryDialogForm"> -->
			<table width="95%">
			<tr>
				<td class="label">
					面单号
				</td>
				<td>
					<input id="reTransNo" loxiaType="input" trim="true" name="" style="height: 25px; line-height: 25px;width:200px;"readonly="readonly"height: 25px; line-height: 25px;/>
				</td>
			</tr>
			<tr>
				<td class="label">
					扫描耗材
				</td>
				<td>
					<input id="skuId" loxiaType="input" trim="true" name="" style="height: 25px; line-height: 25px;width:200px;"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					包裹重量
				</td>
				<td>
					<input id="autoWeigth" loxiaType="input" trim="true" name="" style="height: 25px; line-height: 25px;width:100px;" readonly="readonly"/>kg<button id="restWeight" loxiaType="button" class="confirm">重启称</button>
				</td>
			</tr>
			<tr>
				<td class="label">
					手工录入包裹重量
				</td>
				<td>
					<input id="packageWeight" loxiaType="input" trim="true" name="" style="height: 25px; line-height: 25px;width:100px;"/>kg <div id="button2" style="display: inline;"></div>
				</td>
			</tr>
			<tr>
				<td class="label">
					立方信息
				</td>
				<td>
					<input id="volume" loxiaType="input" trim="true" name="" style="height: 25px; line-height: 25px;width:100px;" readonly="readonly"/>m³<button type="button" loxiaType="button" id="btnShopQueryClose2" class="confirm">关闭</button>
				</td>
			</tr>
		<!-- 	<tr>
				<td class="label">
					称重包裹数量
				</td>
				<td>
					<input id="yNum" loxiaType="input" trim="true" name="" style="width:100px;"/>
				</td>
			</tr> -->
		</table>
		<button type="button" loxiaType="button" id="zhiSubmit" class="confirm" >确认提交</button>
		<span class="label">确认条码：</span><input id="okSubmit" loxiaType="input" trim="true" style="width: 200px; height: 30px; line-height: 30px;"/>
</div>
