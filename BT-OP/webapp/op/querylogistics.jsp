<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ include file="/base/yuriy.jsp"%>
<%-- <script type="text/javascript" src="<%=basePath %>js/common.js"></script> --%>
<script type="text/javascript" src="<%=basePath %>/js/selectFilter.js"></script>
<script type="text/javascript">
</script></head>
<body>
	
	<div class= "modal-dialog modal-lg" role= "document" style= "width: 65%;" >
				<div class= "modal-content" style= "border: 3px solid #394557;" >
					<div class= "modal-header" style='height:50px' >
						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
						<h4 id= "formLabel" class= "modal-title" >查询页面</h4>
					</div>
					<div style="margin-top: 20px;margin-left: 20px;margin-bottom: 20px;">
		 <form id="query_logistics" >			
			<table width='85%' id="query_logistics_inf"> 
			   <tr> 
			   		<td width="20%" align="left"><label style="width: 95px;">状态:</label></td>
			   		<td width="25%" align="right">
		  				<select id="query_state" name="status" data-edit-select="1" style="width: 180px;">
							<option value="" select>---请选择---</option>
							<option value="1">已录单</option>
						</select>
		  			</td>
		  			<td width="10%" align="left"><label>发货机构:</label></td>
		  			<td width="25%" align="right">
						<select id="query_from_orgnization" name="from_orgnization" data-edit-select="1" style="width: 180px;" >
							<option value="" select>---请选择---</option>
							 <c:forEach items= "${orgs}" var= "street" >
			  					    <option value="${street.org_name}">${street.org_name}</option>
							 </c:forEach>  
						</select>
		  			</td>
		  		</tr>
		  		 <tr> 
			   		<td width="20%" align="left"><label style="width: 95px;">下单日期</label></td>
			   		<td width="25%" align="right"><input type='text'
							id='query_time' name='s_time' style="width: 180px;"
							onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})">
					</td>
		  			<td width="10%" align="left"><label>到:</label></td>
		  			<td width="25%" align="right">
		  				<input type='text'
							id='query_end_time' name='e_time' style="width: 180px;"
							onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})">
					</td>
		  		</tr>
		  		
		  		<tr>
		  			<td width="20%" align="left"><label style="width: 70px;">快递公司:</label></td>
		  			<td width="25%" align="right">
		  				<select id="query_expressCode" name="expressCode" data-edit-select="1" style="width: 180px;">
				  				<option value= "" select>---请选择---</option>
				  				  <c:forEach items= "${venders}" var= "street" >
				  					    <option value="${street.express_code}"  >${street.express_name}</option>
								 </c:forEach> 
							</select>
		  			</td>
		  			<td width="25%" align="left"><label style="width: 95px;">快递业务类型:</label></td>
		  			<td width="25%" align="right">
		  				<select id="query_producttype_name" name="producttype_name" data-edit-select="1" style="width: 180px;">
			  				<option value= "" select>---请选择---</option>
			  				  <c:forEach items= "${type}" var= "street" >
			  					    <option value="${street.product_type_code}">${street.product_type_name}</option>
							 </c:forEach> 
						</select>
		  			</td>
		  			
		  		</tr>
		  		
		  		<tr>
		  			<td width="25%" align="left"><label style="width: 95px;">快递单号:</label></td>
		  			<td width="25%" align="right">
		  				<input type='text' id='query_order_id' name='order_id' style="width: 180px;">
		  				
		  			</td>
		  			<td width="25%" align="left"><label style="width: 70px;">支付方式:</label></td>
		  			<td width="25%" align="right">
		  				<select id="query_group_code" name="group_code" data-edit-select="1" style="width: 180px;">
				  				<option value= "">月付</option>
						</select>
		  			</td>
		  		</tr>
		  		<tr>
		  			<td width="25%" align="left"><label style="width: 95px;">收货机构:</label></td>
		  			<td width="25%" align="right">
		  				<select id="query_to_orgnization" name="to_orgnization" data-edit-select="1" style="width: 180px;">
				  				<option value= "" select>---请选择---</option>
				  				  <c:forEach items= "${orgs}" var= "street" >
				  					    <option value="${street.organization_code}">${street.org_name}</option>
								 </c:forEach>  
							</select>
		  			</td>
		  			<td width="25%" align="left"><label style="width: 70px;">省:</label></td>
		  			<td width="25%" align="right">
		  				<select id="query_org_provice_code" name="to_province" data-edit-select="1" style="width: 180px;" onchange='shiftArea(1,"query_org_provice_code","query_org_city_code","query_org_state_code","")'>
				  				<option value= "" select>---请选择---</option>
				  				<c:forEach items="${areas}" var = "area" >
								    <c:if test='${queryOrg.org_province_code eq area.area_code }'> 
										<option selected='selected' value="${area.area_code}">${area.area_name}</option>
									 </c:if>
									 <c:if test='${queryOrg.org_province_code ne area.area_code }'> 
										<option  value="${area.area_code}">${area.area_name}</option>
									 </c:if>
								</c:forEach>
							</select>
		  			</td>
		  		</tr>
		  		<tr>
		  			<td width="25%" align="left"><label>市:</label></td>
		  			<td width="25%" align="right">
		  				<select id="query_org_city_code" name="to_city" data-edit-select="1" style="width: 180px;" onchange='shiftArea(2,"query_org_provice_code","query_org_city_code","query_org_state_code","")'>
				  				<option value= "" select>---请选择---</option>
								<c:forEach items="${city}" var = "cityVar" >
								    <c:if test='${queryOrg.org_city_code eq cityVar.area_code}'>
										<option selected='selected'  value="${cityVar.area_code}">${cityVar.area_name}</option>
									</c:if>
									<c:if test='${queryOrg.org_city_code ne cityVar.area_code}'>
										<option  value="${cityVar.area_code}">${cityVar.area_name}</option>
									</c:if>
								</c:forEach>
							</select>
		  			</td>
		  			<td width="25%" align="left"><label style="width: 95px;">区:</label></td>
		  			<td width="25%" align="right">
		  				<select id="query_org_state_code" name="to_state" data-edit-select="1" style="width: 180px;" >
				  				<option value= "" select>---请选择---</option>
								<c:forEach items="${state}" var = "stateVar" >
							      <c:if test='${stateVar.area_code eq queryOrg.org_state_code }'> 
									<option selected='selected' value="${stateVar.area_code}">${stateVar.area_name}</option>
								  </c:if>
								  <c:if test='${stateVar.area_code ne queryOrg.org_state_code }'> 
									<option  value="${stateVar.area_code}">${stateVar.area_name}</option>
								  </c:if>	
								</c:forEach>
							</select>
		  			</td>
		  		</tr>
		  		<tr>
		  			<td width="25%" align="left"><label style="width: 70px;">街道:</label></td>
		  			<td width="25%" align="right">
		  				<input type='text' id='query_to_street' name='to_street' value="${queryOrg.org_street}" style="width: 180px;">
		  			</td>
		  			<td width="25%" align="left"><label style="width: 95px;">收货联系人:</label></td>
		  			<td width="25%" align="right">
		  				<input type='text' id='query_to_contacts' name='to_contacts' style="width: 180px;">
		  			</td>
		  			
		  		</tr>
		  		<tr> 
		  		<td width="25%" align="left"><label style="width: 95px;">收货联系电话:</label></td>
		  			<td width="25%" align="right">
		  				<input type='text' id='query_to_phone' name='to_phone' >
		  			</td>
		  			<td width="25%" align="left"><label style="width: 70px;">收货地址:</label></td>
		  			<td width="25%" align="right">
		  				<input type='text' id='query_to_address' name='to_address' style="width: 160px;">
		  			</td>
		  		</tr>
		  		<tr> 
		  			<td width="25%" align="left"><label style="width: 95px;">运单号:</label></td>
		  			<td width="25%" align="right" >
		  				<input type='text' id='query_waybill' name='waybill' style="width: 160px;">
		  			</td>
		  		</tr>
		  		</table>
			</form>
			</div>
				</div>
					<div class= "modal-footer" >
		     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="queryLogistics();" >
		     				<i class= "icon-save" aria-hidden= "true" ></i>确定
		     			</button>
		     			<button id= "btn_back" type= "button" class= "btn btn-default" data-dismiss= "modal" >
							<i class= "icon-undo" aria-hidden= "true" ></i>返回
						</button>
					</div>
				</div>
</body>
</html>
<style>

.divclass{
border:5px solid #E0EEEE
} 

.select {
    padding: 3px 4px;
    height: 30px;
    width: 160px;
   text-align: enter;
}

.table_head_line td {
font-weight: bold;
font-size: 16px
}
</style>