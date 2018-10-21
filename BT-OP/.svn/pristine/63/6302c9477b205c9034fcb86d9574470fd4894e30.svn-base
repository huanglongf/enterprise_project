<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ include file="/base/yuriy.jsp"%>
<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/selectFilter.js"></script>
<script type="text/javascript">
</script></head>
<body>
		<div class= "modal-dialog modal-lg" role= "document" style= "width: 65%;" >
				<div class= "modal-content" style= "border: 3px solid #394557;" >
					<div class= "modal-header" style='height:50px' >
						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
						<h4 id= "formLabel" class= "modal-title" >新增页面</h4>
					</div>
					<div class= "modal-body" >
		 <form id="addForm">			
			<table width='95%'> 
			   <tr> 
		  			<td width="10%" align="left"><label>发货机构</label></td>
		  			<td width="25%" align="right">
		  				<select id="from_orgnization" name="from_orgnization" data-edit-select="1" style="width: 168px;">
			  				<option value="${org.org_name}" selected>${org.org_name}</option>
						</select>
		  			</td>
		  			<td width="20%" align="left"><label style="width: 95px;">状态</label></td>
		  			<td width="25%" align="right">
		  				<input type='text' id='status' name='status' value="已录单">
		  			</td>
		  			<td width="20%" align="left"><label style="width: 95px;">运单号</label></td>
		  			<td width="25%" align="right">
		  				<input type='text' id='waybill' name='waybill' >
		  			</td>
		  		</tr>
		  		<tr>
		  			<td width="20%" align="left"><label style="width: 70px;">快递公司</label></td>
		  			<td width="25%" align="right">
		  				<select id="expressCode" name="expressCode" data-edit-select="1" style="width: 168px;">
			  				<option value= "">---请选择---</option>
			  				  <c:forEach items= "${venders}" var= "street" >
			  					    <option value="${street.express_code}"  >${street.express_name}</option>
							 </c:forEach> 
						</select>
		  			</td>
		  			<td width="25%" align="left"><label style="width: 95px;">快递业务类型</label></td>
		  			<td width="25%" align="right">
		  				<select id="producttype_name" name="producttype_name" data-edit-select="1" style="width: 168px;">
			  				<option value= "">---请选择---</option>
			  				  <c:forEach items= "${type}" var= "street" >
			  					    <option value="${street.product_type_code}">${street.product_type_name}</option>
							 </c:forEach> 
						</select>
		  			</td>
		  			<td width="25%" align="left"><label style="width: 95px;">快递单号</label></td>
		  			<td width="25%" align="right">
		  				<input type='text' id='order_id' name='order_id'>
		  			</td>
		  		</tr>
		  		
		  		<tr>
		  			<td width="25%" align="left"><label style="width: 70px;">支付方式</label></td>
		  			<td width="25%" align="right">
		  				<select id="group_code" name="group_code" data-edit-select="1" style="width: 168px;">
			  				<option value= "">月付</option>
						</select>
		  			</td>
		  			<td width="25%" align="left"><label style="width: 95px;">备注</label></td>
		  			<td width="25%" colspan="5">
		  				<input type='text' id='memo' name='memo' style="width: 435px;">
		  			</td>
		  		</tr>
		  		<tr>
		  			<td width="25%" align="left" colspan="4"><label style="width: 70px;">收货信息</label></td>
		  			<td width="25%" align="left"><label style="width: 95px;">收货机构</label></td>
		  			<td width="25%" align="right">
		  				<select id="to_orgnization" name="to_orgnization" data-edit-select="1" style="width: 168px;">
			  				<option value= "">---请选择---</option>
			  				  <c:forEach items= "${orgs}" var= "street" >
			  					    <option value="${street.org_name}">${street.org_name}</option>
							 </c:forEach>  
						</select>
		  			</td>
		  		</tr>
		  		<tr>
		  		
		  			<td width="25%" align="left"><label style="width: 70px;">省</label></td>
		  			<div id="distpicker">
		  			<td width="25%" align="right">
		  				<select id="org_provice_code" name="to_province" data-edit-select="1" style="width: 168px;" onchange='shiftArea(1,"org_provice_code","org_city_code","org_state_code","")'>
			  				<option value= "">---请选择---</option>
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
		  			</div>
		  			<td width="25%" align="left"><label>市</label></td>
		  			<td width="25%" align="right">
		  				<select id="org_city_code" name="to_city" data-edit-select="1" style="width: 168px;" onchange='shiftArea(2,"org_provice_code","org_city_code","org_state_code","")'>
			  				<option value= "">---请选择---</option>
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
		  			<td width="25%" align="left"><label style="width: 95px;">区</label></td>
		  			<td width="25%" align="right">
		  				<select id="org_state_code" name="to_state" data-edit-select="1" style="width: 168px;" >
			  				<option value= "">---请选择---</option>
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
		  			<td width="25%" align="left"><label style="width: 70px;">街道</label></td>
		  			<td width="25%" align="right">
		  				<input type='text' id='to_street' name='to_street' value="${queryOrg.org_street}">
		  			</td>
		  			<td width="25%" align="left"><label style="width: 95px;">收货联系人</label></td>
		  			<td width="25%" align="right">
		  				<input type='text' id='to_contacts' name='to_contacts' value="${queryOrg.org_contacts}">
		  			</td>
		  			<td width="25%" align="left"><label style="width: 95px;">收货联系电话</label></td>
		  			<td width="25%" align="right">
		  				<input type='text' id='to_phone' name='to_phone' value="${queryOrg.org_phone}">
		  			</td>
		  		</tr>
		  		<tr> 
		  			<td width="25%" align="left"><label style="width: 70px;">收货地址</label></td>
		  			<td width="25%" align="right">
		  				<input type='text' id='to_address' name='to_address' value="${queryOrg.org_address}">
		  			</td>
		  			
		  			<td width="25%" align="right">
		  				<input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/> 
		  			</td>
		  			<td width="25%" align="left">
		  				<label style="width: 95px;">是否保价</label>
		  			</td>
		  			
		  		</tr>
		  		<tr>
		  			<td width="25%" align="left"><label style="width: 70px;" colspan="5">发货信息</label></td>
		  		</tr>
		  		<tr>
		  			<td width="25%" align="left"><label style="width: 70px;" >省</label></td>
		  			<td width="25%" align="right">
		  				<select id="provice_code" name="provice_code" data-edit-select="1" style="width: 168px;" onchange='shiftArea(1,"provice_code","city_code","state_code","")'>
			  				<option value= "">---请选择---</option>
			  				<c:forEach items="${areas}" var = "area" >
							    <c:if test='${org.org_province_code eq area.area_code }'> 
									<option selected='selected' value="${area.area_code}">${area.area_name}</option>
								 </c:if>
								 <c:if test='${org.org_province_code ne area.area_code }'> 
									<option  value="${area.area_code}">${area.area_name}</option>
								 </c:if>
							</c:forEach>
						</select>
		  			</td>
		  			<td width="25%" align="left"><label style="width: 95px;" >市</label></td>
		  			<td width="25%" align="right">
		  				<select id="city_code" name="city_code" data-edit-select="1" style="width: 168px;" onchange='shiftArea(2,"provice_code","city_code","state_code","")'>
			  				<option value= "">---请选择---</option>
							<c:forEach items="${city}" var = "cityVar" >
							    <c:if test='${org.org_city_code eq cityVar.area_code}'>
									<option selected='selected'  value="${cityVar.area_code}">${cityVar.area_name}</option>
								</c:if>
								<c:if test='${org.org_city_code ne cityVar.area_code}'>
									<option  value="${cityVar.area_code}">${cityVar.area_name}</option>
								</c:if>
							</c:forEach>
						</select>
		  			</td>
		  			<td width="25%" align="left"><label style="width: 95px;" >区</label></td>
		  			<td width="25%" align="right">
		  				<select id="state_code" name="state_code" data-edit-select="1" style="width: 168px;" >
			  				<option value= "">---请选择---</option>
							<c:forEach items="${state}" var = "stateVar" >
						      <c:if test='${stateVar.area_code eq org.org_state_code }'> 
								<option selected='selected' value="${stateVar.area_code}">${stateVar.area_name}</option>
							  </c:if>
							  <c:if test='${stateVar.area_code ne org.org_state_code }'> 
								<option  value="${stateVar.area_code}">${stateVar.area_name}</option>
							  </c:if>	
							</c:forEach>
						</select>
		  			</td>
		  		</tr>
		  		<tr>
		  			<td width="25%" align="left"><label style="width: 70px;">街道</label></td>
		  			<td width="25%" align="right">
			  				<input type='text' id='from_street' name='from_street' value="${org.org_street}">
		  			</td>
		  			<td width="25%" align="left"><label style="width: 95px;">发货联系人</label></td>
		  			<td width="25%" align="right">
		  				<input type='text' id='from_contacts' name='from_contacts' value="${org.org_contacts}">
		  			</td>
		  			<td width="25%" align="left"><label style="width: 95px;">发货联系电话</label></td>
		  			<td width="25%" align="right">
		  				<input type='text' id='from_phone' name='from_phone' value="${org.org_phone}">
		  			</td>
		  		</tr>
		  		</table>
		  		<table width='80%'> 
		  		<tr>
		  			<td width="25%" align="left"><label style="width: 70px;">发货地址</label></td>
		  			<td width="25%" align="right">
		  				<input type='text' id='from_address' name='from_address' style="width: 702px;" value="${org.org_address}">
		  			</td>
		  		</tr>
			</table>
			
				<div border="1" bordercolor="#a0c6e5" style="border-collapse:collapse;"  class="divclass">
				<input type="button" value="添加行" onclick="onchecks();"> 
			 <table border="1" bordercolor="#a0c6e5" style="border-collapse:collapse;" id="tableId" >
				<tr>
					<td><label style="width: 30px;">选择</label></td>
					<td><label style="width: 70px;">序号</label></td>
					<td><label style="width: 100px;">商品代码</label></td>
					<td><label style="width: 100px;">商品描述</label></td>
					<td><label style="width: 100px;">件数</label></td>
					<td><label style="width: 100px;">毛重</label></td>
					<td><label style="width: 100px;">体积</label></td>
					<td><label style="width: 100px;">金额</label></td>
				</tr>
				
				<tr>
					<td align = "center"><input id="ckb" name="ckb" type="checkbox"></td>
					<td><label style="width: 70px;">1</label></td>
					<td><label><input style="width: 100px;" id="sku_code1" name="sku_code"></<input></label></td>
					<td><label ><input style="width: 100px;" id="sku_name1" name="sku_name"></<input></label></td>
					<td><label ><input style="width: 100px;" id="qty1" name="qty" onblur="setStyleqty(this.id)"></<input></label></td>
					<td><label ><input style="width: 100px;" id="weight1" name="weight" onblur="setStyleweight(this.id)"></<input></label></td>
					<td><label ><input style="width: 100px;" id="volumn1" name="volumn" onblur="setStylevolumn(this.id)"></<input></label></td>
					<td><label ><input style="width: 100px;" id="amount1" name="amount" onblur="setStyleamount(this.id)"></<input></label></td>
				</tr>
				</table>
				<table border="1" bordercolor="#a0c6e5" style="border-collapse:collapse;" >
				<tr>
					<td><label style="width: 30px;"></label></td>
					<td><label style="width: 70px;">合计</label></td>
					<td><label style="width: 100px;"></label></td>
					<td><label style="width: 100px;"></label></td>
					<td><label  ><input type="text" readonly id="total_qty" style="width: 100px;"/></label></td>
					<td><label ><input type="text" readonly id="total_weight" style="width: 100px;"/></label></td>
					<td><label ><input type="text" readonly id="total_volumn" style="width: 100px;"/></label></td>
					<td><label><input type="text" readonly id="total_amount" style="width: 100px;"/></label></td>
				</tr>
			</table>
			</div>
			<div>
			<table width='95%'> 
			   <tr> 
		  			<td width="10%" align="middle"><label>制单人</label></td>
		  			<td width="25%" align="middle">
		  				<input type='text' id='create_user' name='create_user' value="${org.org_name}" style="width: 260px;"/>
		  			</td>
		  			<td width="10%" align="middle"><label>修改人</label></td>
		  			<td width="25%" align="middle">
		  				<input type='text' id='update_user' name='update_user' value="${org.org_name}" style="width: 260px;"/>
		  			</td>
		  		</tr>
		  	</table>
		  	</div>
			</form>
				</div>
					<div class= "modal-footer" >
						<button id= "btn_back" type= "button" class= "btn btn-default" data-dismiss= "modal" >
							<i class= "icon-undo" aria-hidden= "true" ></i>返回
						</button>
		     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="addLogistics();" >
		     				<i class= "icon-save" aria-hidden= "true" ></i>保存
		     			</button>
					</div>
				</div>
			</div>

</body>
</html>