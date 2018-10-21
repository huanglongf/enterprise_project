<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/outbound-picking-task.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.outbound.picking.task"/></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="divMain">
		<form id="formQuery" name="formQuery">
			<table width="80%">
				<tr>
					<td  class="label" width="9%">创建时间</td>
					<td width="16%"><input id="startDate" name="sta.startCreateTime1" loxiaType="date" showTime="true"/></td>
					<td class="label" width="5%">到</td>
					<td width="16%"><input id="endDate" name="sta.endCreateTime1" loxiaType="date" showTime="true"/></td>
					<td class="label"width="12%">作业类型</td>
					<td width="15%">
						<select id="type" name="sta.intStaType" loxiaType="select">
							<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							<option value="61">采购出库</option>
							<option value="101">VMI退大仓</option>
							<option value="102">VMI转店退仓</option>
							<option value="64">代销出库</option>
							<option value="62">结算经销出库</option>
						</select>
					</td>
					<td class="label" width="13%" >
						作业单状态
					</td>
					<td width="14%">
						<select id="status" name="sta.intStaStatus" loxiaType="select">
							<option value="1">已创建</option>
							<option selected="selected" value="2">配货中</option>
							<option value="3">已核对</option>
							<option value="4">已转出</option>
							<option value="5">部分转入</option>
							<option value="8">装箱中</option>
							<option value="10">已完成</option>
							<option value="15">取消未处理</option>
							<option value="17">取消已处理</option>
							<option value="20">配货失败</option>
							<option value="25">冻结</option>
						</select>
					</td>	
					
					
				</tr>
				<tr>
					<td  class="label">作业单号</td>
					<td ><input loxiaType="input"  trim="true" id="staCode" name="sta.code"/></td>
					<td class="label">店铺</td>
					<td>
						<select id="companyshop" name="sta.owner" loxiaType="select" >
							<option value="">--请选择店铺--</option>
						</select>
					</td>
					<td  class="label">相关单据号</td>
					<td ><input loxiaType="input"  trim="true" id="staSlipCode" name="sta.refSlipCode"/></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button name="query" id="query" type="button" loxiaType="button" class="confirm">查询</button>
			<button id="btnReset" type="button" loxiaType="button" >重置</button>
		</div>
		<table id="tbl_sta"></table>
		<div id="tbl_sta_page"></div>
		
	</div>
	
	<div id="divDetail" class="hidden">
		<input type="hidden" value="" id="staid"/>
		<table width="100%">
			<tr>
				<td class="label" width="10%">
					作业单号:
				</td>
				<td id="staCode_" width="15%">
				     
				</td>
				<td class="label" width="10%">
					相关单据号:
				</td>
				<td id="staSlipCode" width="15%">
				 
				</td>
				
				<td class="label" width="10%">
					状态:
				</td>
				<td id="staStatus" width="15%">
				
				</td>
				<td class="label" width="10%">
					 店铺:
				</td>
				<td id="staOwner" width="15%">
				      
				</td>
			</tr>
			<tr>
				<td class="label" width="10%">
					计划出库量:
				</td>
				<td id="staSkuQty" width="15%">
				 
				</td>
				<td class="label" width="10%">
					SKU种类数:
				</td>
				<td id="staSkuTypeNum" width="15%">
				 
				</td>
				<td class="label" width="10%">
					拣货区域数量:
				</td>
				<td id="staPickAreasNum" width="15%">
					 
				</td>
				<td class="label" width="10%">
					仓库区域数量:
				</td>
				<td id="staWarehouseAreasNum" width="15%">
					 
				</td>
			</tr>
			<tr>
				<td class="label" width="10%">
					创建时间:
				</td>
				<td id="createTime" width="15%">
				</td>
			</tr>
			</table>
		<div id="tabs" width="100%">
			<ul>
				<li><a href="#tabs_1">出库明细汇总</a></li>
				<li><a href="#tabs_2">拣货任务</a></li>
				<li><a href="#tabs_3">拣货周转箱明细</a></li>
				<li><a href="#tabs_4">出库明细</a></li>
				<li><a href="#tabs_5">单据头基本信息</a></li>
			</ul>
			<div id="tabs_1">
				<table id="tbl_sta_detail_collection"></table>
				<div id="tbl_sta_detail_collection_page"></div>
				<div class="buttonlist">
			<table>
				<tr>
					<td  class="label">
						拆分规则：
					</td>
					<td >
						<select id="select" name="sta.intStaType" loxiaType="select">
							<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							<option value="1">按拣货区域拆分</option>
							<option value="2">按仓库区域拆分</option>
							<option value="3">按商品货号拆分</option>
						</select>
					</td>
					<td  class="label">
						子任务总数商品上限：
					</td>
					<td ><input loxiaType="input" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  trim="true" id="taskNum" name="taskNum"/></td>
					<td  class="label">
						(不填为无限制)
					</td>
				    <td>
						<button loxiaType="button" class="confirm" id="addPickingTask">生成拣货任务</button>
					</td>
					
				</tr>
			</table>
		</div>
		
			</div>
			<div id="tabs_2">
				<table id="tbl_sta_pick_task"></table>
				<div id="tbl_sta_pick_task_page"></div>
				<div class="buttonlist">
					<button id="printPdaBarCode" type="button" loxiaType="button" class="confirm">打印PDA拣货条码</button>
					<button id="printDiekingLine" type="button" loxiaType="button" class="confirm">打印拣货单</button>
				</div>
			</div>
			<div id="tabs_3">
			 <table id="tbl_sta_pick_zzx_detail"></table>
			<div id="tbl_sta_pick_zzx_detail_page"></div>
			</div>
			<div id="tabs_4">
				<table id="tbl_sta_line"></table>
				<div id="tbl_sta_line_page"></div>
			</div>
			<div id="tabs_5">
			   <form id="tabs_form">
				<table>
				  <tr>
					<td class="label">相关单据号1:</td>
					<td id="slip_code1"></td>
					<td class="label">相关单据号2:</td>
					<td id="slip_code2"></td>
					<td><input type="hidden" id="lfId" name="staLf.id"></input></td>
					<td></td>
					
					
				  </tr>
				  <tr>
					<td  class="label">计划发货时间:</td>
					<td id="planDateTime"></td>
					<td  class="label">运输时效:</td>
					<td id="transportPra"></td>
					<td  class="label">CRD:</td>
					<td><input type="text" id="crd" name="staLf.crd"></input></td>
					<td></td>
					
				  </tr>
				  <tr>
				    <td>送货地址:</td>
				  </tr>
				  <tr>
					<td  class="label">NFS店铺编码:</td>
					<td><input type="text" id="nfsStoreCode" name="staLf.nfsStoreCode"></input></td>
					<!-- <td  class="label">省份:</td>
					<td><input type="text" id="crd" name="staLf.crd"></input></td> -->
					<td  class="label">城市:</td>
					<td><input type="text" id="city" name="staLf.city"></input></td>
					<td>邮编:</td>
					<td><input type="text" id="zip" name="staLf.zip"></input></td>
				  </tr>
				  <tr>
				    <td>地址1:</td>
				    <td colspan="5"><input type="text" size="100px" id="address1" name="staLf.address1"></input></td>
				  </tr>
				  <tr>
				    <td>地址2:</td>
				    <td colspan="5"><input type="text" size="100px" id="address2" name="staLf.address2"></input></td>
				  </tr>
				  <tr>
				    <td>地址3:</td>
				    <td colspan="5"><input type="text" size="100px" id="address3" name="staLf.address3"></input></td>
				  </tr>
				  <tr>
				    <td>地址4:</td>
				    <td colspan="5"><input type="text"  size="100px" id="address4" name="staLf.address4"></input></td>
				  </tr>
				  <tr>
				    <td>公司名:</td>
				    <td id="companyName"></td>
				    <td>NikePO:</td>
				    <td id="nikePo"></td>
				    <td>VAS Code:</td>
				    <td id="vasCode"></td>
				  </tr>
				  <tr>
				    <td>鞋服配:</td>
				    <td id="divisionCodeTranslation"></td>
				    <td>宝尊单独发货:</td>
				    <td>是</td>
				    <td>宝尊多仓发货:</td>
				    <td>否</td>
				   
				  </tr>
			  </table>
			    <div class="buttonlist">
					<button id="save" type="button" loxiaType="button" class="confirm">保存</button>
				</div>
			  </form>
			</div>
		</div>
		<div class="buttonlist">
			<button id="btnBack" type="button" loxiaType="button" >返回</button>
		</div>
		
		
		
	</div>
	
</body>
</html>