<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="form-group col-xs-12">
	<div class="div_margin">
		<label class="control-label blue">
			保价费
		</label>
		<input id="cb_1" type="checkbox" class="ace ace-switch ace-switch-5" ${eCC.insurance == 'true' ? 'checked="checked"' : '' } onchange="insuranceECShow();"/>
		<span class="lbl"></span>
	</div>
	<div id="div_0" class="moudle_dashed_border">
		<div class="div_margin">
			<div>
				<div class="div_margin">
					<label class="control-label blue">
						---已维护规则---
					</label>
				</div>
				<table id="iECList" class="with-border" border="1">
			   		<thead>
				  		<tr class="title">
				  			<td>序号</td>
				  			<td>产品类型</td>
				  			<td>阶梯类型</td>
				  			<td>最低收费</td>
				  			<td>区间</td>
				  			<td>收费率</td>
				  			<td>收费金额</td>
				  			<td>操作</td>
				  		</tr>
			  		</thead>
			  		<tbody>
			  		</tbody>
				</table>
			</div>
			<div class="add_button">
				<button class="btn btn-xs btn-yellow" onclick='javascript: $("#add_0").show()' >
                  	<i class="icon-hdd" ></i>新增
             	</button>
       		</div>
		</div>
		<div id="add_0" class="moudle_dashed_border_width_90">
			<div class="div_margin">
				<label class="control-label blue">
					产品类型&nbsp;:
				</label>
				<select id="product_type_iF" class="selectpicker" style="width: 30%">
					<c:if test="${contractOwner.contractOwner != 'SF' }">
						<option selected="selected" value="">无</option>
					</c:if>
					<c:if test="${contractOwner.contractOwner == 'SF' }">
						<option selected="selected" value="ALL">所有类型</option>
					</c:if>
					<c:forEach items="${productType}" var="productType">
						<option value="${productType.product_type_code}">${productType.product_type_name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="div_margin">
				<label class="control-label blue">
					阶梯类型&nbsp;:
				</label>
				<select id="ladder_type_iF" class="selectpicker" style="width: 30%" onchange="shiftPage_3()">
					<option selected="selected" value="0">请选择</option>
					<option value="1">无阶梯</option>
					<option value="2">总费用阶梯价</option>
					<option value="3">超出部分阶梯价</option>
				</select>
				<div id="div_1" class="moudle_dashed_border_width_90_red">
					<div class="div_margin">
						<label class="control-label blue" for="charge_percent_iF_2">
							收费率&nbsp;:
						</label>
						<input id="charge_percent_iF_2" name="charge_percent_iF_2" type="text"/>
						<select id="charge_percent_iF_2_uom" class="selectpicker">
							<option selected="selected" value="0">%</option>
						</select>
					</div>
					<div class="div_margin">
						<input id="cb_2" type="checkbox" class="ace ace-switch ace-switch-5"/>
						<span class="lbl"></span>
						<label class="control-label blue">
							最低收费&nbsp;:
						</label>
						<input id="charge_min_iF" name="cost_min" type="text" />
						<select id="charge_min_iF_uom" class="selectpicker">
							<option selected="selected" value="0">元</option>
						</select>
					</div>
				</div>
				<div id="div_2" class="moudle_dashed_border_width_90_red">
					<div class="div_margin">
						<span style="color: red">计算公式 : 保价费 = 订单金额 * 收费比率</span>
					</div>
					<div class="div_margin">
						<span style="color: red">公式说明 : 折扣为阶梯区间，需要在下方设置</span>
					</div>
					<div class="div_margin">
						<label class="control-label blue">
							---阶梯设置---
						</label>
					</div>
					<div class="div_margin">
						<label class="control-label blue">
							条件&nbsp;:
						</label>
						<select id="compare_1_iF" class="selectpicker">
							<option selected="selected" value="0">></option>
							<option value="1">>=</option>
						</select>
						<label class="black" for="num_1_iF">
							订单金额&nbsp; 
						</label>
						<input id="num_1_iF" name="num_1_iF" type="text"/>
						<select id="num_1_iF_uom" class="selectpicker">
							<option selected="selected" value="0">元</option>
						</select>
						<select id="rel_iF" class="selectpicker">
							<option selected="selected" value="0">并且</option>
						</select>
					</div>
					<div class="div_margin">
						<label class="control-label blue">
							条件&nbsp;:
						</label>
						<select id="compare_2_iF" class="selectpicker">
							<option value="2"><</option>
							<option value="3"><=</option>
						</select>
						<label class="black" for="num_2_iF">
							订单金额&nbsp; 
						</label>
						<input id="num_2_iF" name="num_2_iF" type="text"/>
						<select id="num_2_iF_uom" class="selectpicker">
							<option selected="selected" value="0">元</option>
						</select>
					</div>
					<div class="div_margin">
						<label class="no-padding-right blue">
							<input id="radio0" checked="checked" name="radio" type="radio" class="ace" />
							<span class="lbl"> 收费率&nbsp;:</span>
						</label>	
						<input id="charge_percent_iF" name="charge_percent_iF" type="text"/>
						<select id="charge_percent_iF_uom" class="selectpicker">
							<option selected="selected" value="0">%</option>
						</select>
					</div>
					<div class="div_margin">
						<label class="no-padding-right blue">
							<input id="radio1" name="radio" type="radio" class="ace" />
							<span class="lbl"> 收费金额&nbsp;:</span>
						</label>
						<input id="charge_iF" name="charge_iF" type="text" />
						<select id="charge_iF_uom" class="selectpicker">
							<option selected="selected" value="0">元</option>
						</select>
					</div>
				</div>
			</div>
			<div class="div_margin">
				<button class="btn btn-xs btn-yellow" onclick="save_iEC();" >
               		<i class="icon-save" ></i>保存
           		</button>
               	<button class="btn btn-xs btn-grey" type="button" onclick="returnIEC();" >
               		<i class="icon-undo" ></i>返回
               	</button>
			</div>
		</div>
	</div>
</div>
<div class="form-group form-actions col-xs-12" align="center">
	<button id="save_1" name="save_1" class="btn btn-xs btn-info btn-yellow" type="button" onclick="configureEC('2');">
		<i class="icon-save bigger-110" ></i>配置
	</button>
</div>