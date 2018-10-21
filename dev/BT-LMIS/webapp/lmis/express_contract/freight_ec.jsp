<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="form-group">
	<div class="div_margin">
		<label class="control-label blue">
			计重方式&nbsp;:
		</label>
		<select id="weight" class="selectpicker" style="width: 30%;" onchange="shiftPage_1()">
			<option selected="selected" value="0">请选择</option>
			<option value="1"
				${eCC.weight_method == "1" ? 'selected = "selected"' : '' }>实际重量
			</option>
			<option value="2"
				${eCC.weight_method == "2" ? 'selected = "selected"' : '' }>计抛计重取大值（不计半抛）
			</option>
			<option value="3"
				${eCC.weight_method == "3" ? 'selected = "selected"' : '' }>计抛计重取大值（计半抛）
			</option>
		</select>
	</div>
	<div id="weight_0" class="div_margin moudle_dashed_border">
		<div id="weight_2" class="form-group" style="display: none">
			<div>
				<div>
					<div class="div_margin">
						<span style="color: red">计抛公式 : 计抛重量 = (长 * 宽 * 高) / 基数</span>
					</div>
					<div class="div_margin">
						<span style="color: red">基数维护 : 在始发地目的地参数列表中维护</span>
					</div>
					<div>
						<div class="div_margin">
							<label class="control-label blue">
								---计全抛条件---
							</label>
						</div>
						<table id="jqpcList" class="with-border" border="1">
					   		<thead>
						  		<tr class="title">
						  			<td>序号</td>
						  			<td>条件</td>
						  			<td>操作</td>
						  		</tr>
					  		</thead>
					  		<tbody>
					  		</tbody>
						</table>
					</div>
					<div class="add_button">
						<button class="btn btn-xs btn-yellow" onclick='javascript: $("#div_JQP").show()'>
		                   	<i class="icon-hdd" ></i>新增
		               	</button>
		       		</div>
		       	</div>	
				<div id="div_JQP" class="moudle_dashed_border_width_90">
					<div class="div_margin">
						<label class="col-xs-12 control-label no-padding-right blue">
							---新增计全抛条件---
						</label>
					</div>
					<div class="div_margin">
						<label class="no-padding-right black">
							当&nbsp; 
						</label>
						<select id="attr_qp" class="selectpicker">
							<option selected="selected" value="0">
								长
							</option>
							<option value="1">
								宽
							</option>
							<option value="2">
								高
							</option>
						</select>
						<select id="mark_qp" class="selectpicker">
							<option selected="selected" value="0">
								>
							</option>
							<option value="1">
								>=
							</option>
							<!-- <option value="2"> -->
							<!--	<				-->
							<!-- </option>			-->
							<!-- <option value="3">	-->
							<!--	<=				-->
							<!-- </option>			-->
						</select>
						<input id="con_qp" name="con_qp" type="text"/>
						<select id="uom_qp" class="selectpicker">
							<option selected="selected" value="0">
								CM
							</option>
						</select>
					</div>
					<div class="div_margin">
						<button id="save_jiquanpao_ec" class="btn btn-xs btn-yellow" onclick="add_jiquanpao_condition();" >
	                   		<i class="icon-save" ></i>保存
	                   	</button>
		            	<button class="btn btn-xs btn-grey" type="button" onclick='returnJQPC()' >
		              		<i class="icon-undo" ></i>返回
		              	</button>
					</div>
				</div>
			</div>
			<!-- <div class="div_margin">
				<label class="control-label blue" for="formula_mark">
					计全抛公式&nbsp;:
				</label>
				<select id="formula_mark" class="selectpicker">
					<option selected="selected" value="0">
						计抛重量 = (长 * 宽 * 高 / 基数 - 实际重量) * 百分比 + 实际重量
					</option>
				</select>
			</div> 
			<div class="div_margin">
				<label class="control-label blue" for="percent">
					百分比设置&nbsp;:
				</label>
				<input id="percent" name="percent" type="text" value="${eCC.percent }"/>
				<select id="percent_uom" class="selectpicker">
					<option selected="selected" value="0" ${eCC.percent_uom==0 ? 'selected = "selected"' : '' }>%</option>
				</select>
			</div> -->
		</div>
		<div id="weight_3" class="form-group" style="display: none">
			<div>
				<div>
					<div class="div_margin">
						<span style="color: red">计抛公式 : 计抛重量 = (长 * 宽 * 高) / 基数</span>
					</div>
					<div class="div_margin">
						<span style="color: red">基数维护 : 在始发地目的地参数列表中维护</span>
					</div>
					<div>
						<div class="div_margin">
							<label class="control-label blue">
								---计半抛条件---
							</label>
						</div>
						<table id="jbpcList" class="with-border" border="1">
					   		<thead>
						  		<tr class="title">
						  			<td>序号</td>
						  			<td>条件</td>
						  			<td>操作</td>
						  		</tr>
					  		</thead>
					  		<tbody>
					  		</tbody>
						</table>
					</div>
					<div class="add_button">
						<button class="btn btn-xs btn-yellow" onclick='javascript: $("#div_12").show()'>
		                   	<i class="icon-hdd" ></i>新增
		               	</button>
		       		</div>
		       	</div>	
				<div id="div_12" class="moudle_dashed_border_width_90">
					<div class="div_margin">
						<label class="col-xs-12 control-label no-padding-right blue">
							---新增计半抛条件---
						</label>
					</div>
					<div class="div_margin">
						<label class="no-padding-right black">
							当&nbsp; 
						</label>
						<select id="attr" class="selectpicker">
							<option selected="selected" value="0">
								长
							</option>
							<option value="1">
								宽
							</option>
							<option value="2">
								高
							</option>
						</select>
						<select id="mark" class="selectpicker">
							<option selected="selected" value="0">
								>
							</option>
							<option value="1">
								>=
							</option>
							<!-- <option value="2"> -->
							<!--	<				-->
							<!-- </option>			-->
							<!-- <option value="3">	-->
							<!--	<=				-->
							<!-- </option>			-->
						</select>
						<input id="con" name="con" type="text"/>
						<select id="uom" class="selectpicker">
							<option selected="selected" value="0">
								CM
							</option>
						</select>
					</div>
					<div class="div_margin">
						<button id="save_jibanpao_ec" class="btn btn-xs btn-yellow" onclick="add_jibanpao_condition();" >
	                   		<i class="icon-save" ></i>保存
	                   	</button>
		            	<button class="btn btn-xs btn-grey" type="button" onclick='returnJBPC()' >
		              		<i class="icon-undo" ></i>返回
		              	</button>
					</div>
				</div>
			</div>
			<div class="div_margin">
				<label class="control-label blue" for="formula_mark">
					计半抛公式&nbsp;:
				</label>
				<select id="formula_mark" class="selectpicker">
					<option selected="selected" value="0">
						计抛重量 = (长 * 宽 * 高 / 基数 - 实际重量) * 百分比 + 实际重量
					</option>
				</select>
			</div>
			<div class="div_margin">
				<label class="control-label blue" for="percent">
					百分比设置&nbsp;:
				</label>
				<input id="percent" name="percent" type="text" value="${eCC.percent }"/>
				<select id="percent_uom" class="selectpicker">
					<option selected="selected" value="0" ${eCC.percent_uom==0 ? 'selected = "selected"' : '' }>%</option>
				</select>
			</div>
		</div>
		<div class="div_margin">
			<div>
				<div class="div_margin">
					<label class="control-label blue">
						---已维护计费公式---
					</label>
				</div>
				<table id ="pfList" class="with-border" border="1">
			   		<thead>
				  		<tr class="title">
				  			<td>序号</td>
				  			<td>公式</td>
				  			<td>详细信息</td>
				  			<td>操作</td>
				  		</tr>
			  		</thead>
			  		<tbody>
			  		</tbody>
				</table>
			</div>
			<div class="add_button">
				<button class="btn btn-xs btn-yellow" onclick="ebfAdd()" >
               		<i class="icon-hdd" ></i>新增
               	</button>
       		</div>
       		<div id="ebfAdd" class="moudle_dashed_border_width_90">
				<div>
					<div class="div_margin">
						<label class="control-label blue">
							---快递计费公式创建及配置---
						</label>
						<input id="pricingformula_ec_id" style="display : none"/>
					</div>
					<div class="div_margin">
						<label class="control-label blue" for="formula">
							计费公式&nbsp;:
						</label>
						<select id="formula" class="selectpicker" onchange="shiftPage()">
							<option selected="selected" value="0">请选择</option>
							<option value="1">公式1：总费用向上取整</option>
							<option value="2">公式2：计费重量向上取整</option>
							<option value="3">公式3：首重价格+续重价格（不向上取整）</option>
							<option value="4">公式4：EMS-首重0.5kg续重0.5kg报价</option>
						</select>
					</div>
				</div>
				<div id="formula_0" class="div_margin" style="display: none">
					<div class="div_margin">
						<span style="color: red">公式 : 费用 = ROUNDUP((计费重量 - 首重) * 续重单价 + 首重价格 , 精确小数位) * 折扣</span>
					</div>
					<div class="div_margin">
						<span style="color: red">公式说明 : 当计费重量小于等于首重时，运费 = 首重价格 * 折扣；当计费重量大于首重时，用上述公式计算，精确小数位在下方设置。。</span>
					</div>
					<div class="div_margin">
						<label class="control-label blue">
							---参数配置---
						</label>
					</div>
					<div class="div_margin">
						<label class="control-label blue" for="exact_decimal_1"> 精确小数位&nbsp;:</label>
						<input id="exact_decimal_1" name="exact_decimal_1" type="text" value=""/>
					</div>
				</div>
				<div id="formula_1" class="div_margin" style="display: none">
					<div class="div_margin">
						<span style="color: red">公式 : 费用  = ((ROUNDUP(计费重量, 精确小数位) - 首重) * 续重单价 + 首重价格) * 折扣</span>
					</div>
					<div class="div_margin">
						<span style="color: red">公式说明 : 当计费重量小于等于首重时，运费 = 首重价格 * 折扣；当计费重量大于首重时，用上述公式计算，精确小数位在下方设置。</span>
					</div>
					<div class="div_margin">
						<label class="control-label blue">
							---参数配置---
						</label>
					</div>
					<div class="div_margin">
						<label class="control-label blue" for="exact_decimal_2">精确小数位&nbsp;:</label>
						<input id="exact_decimal_2" name="exact_decimal_2" type="text" value="${employee.username }"/>
					</div>
				</div>
				<div id="formula_2" class="div_margin" style="display: none">
					<div class="div_margin">
						<span style="color: red">公式 : 费用  = ((计费重量 - 首重) * 续重单价 + 首重价格) * 折扣</span>
					</div>
					<div class="div_margin">
						<span style="color: red">公式说明 : 当计费重量小于等于首重时，运费 = 首重价格 * 折扣；当计费重量大于首重时，用上述公式计算。</span>
					</div>
				</div>
				<div id="formula_3" class="div_margin" style="display: none">
					<div class="div_margin">
						<span style="color: red">公式 : 费用 = ((计费重量  - 首重) * 续重单价 * 2 + 首重价格) * 折扣</span>
					</div>
					<div class="div_margin">
						<span style="color: red">公式说明 : EMS首重0.5KG续重0.5KG报价公式，当计费重量小于等于首重时，运费 = 首重价格 * 折扣；计费重量大于首重时，使用上述公式。</span>
					</div>
				</div>
				<div class="div_margin">
					<span style="color: red">
						快递报价，首重，续重，单运单折扣需要在始发地目的地参数列表中维护。
					</span>
				</div>
				<div class="div_margin">
					<button id="save_formula_ec" class="btn btn-xs btn-yellow" onclick="save_pricingFormula_ec()" >
	                   	<i class="icon-save" ></i>保存
	              	</button>
	             	<button class="btn btn-xs btn-grey" type="button" onclick='returnPF()'>
	              		<i class="icon-undo" ></i>返回
	              	</button>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="form-group" <c:if test="${contractOwner.contractOwner != 'SF' }">style="display : none;"</c:if>>
	<div class="div_margin">
		<label class="control-label blue">
			运单折扣启用条件
		</label>
		<input 
		id="cb_waybill" 
		type="checkbox" 
		class="ace ace-switch ace-switch-5" ${eCC.waybill_discount=='true' ? 'checked="checked"' : '' } 
		onchange='waybillDiscountShow();' />
		<span class="lbl"></span>
	</div>
	<div id="waybillDiscountDiv" class="moudle_dashed_border">
		<div class="div_margin">
			<div>
				<div class="div_margin">
					<label class="control-label blue">
						---已维护规则---
					</label>
				</div>
				<table id="wDList" class="with-border" border="1">
			   		<thead>
				  		<tr class="title">
				  			<td>序号</td>
				  			<td>产品类型</td>
				  			<td>区间</td>
				  			<td>单运单折扣率</td>
				  			<td>操作</td>
				  		</tr>
			  		</thead>
			  		<tbody>
			  		</tbody>
				</table>
			</div>
			<div class="add_button">
				<button class="btn btn-xs btn-yellow" onclick='javascript: $("#add_waybill_condition").show()' >
                   	<i class="icon-hdd" ></i>新增
              	</button>
       		</div>
		</div>
		<div id="add_waybill_condition" class="moudle_dashed_border_width_90">
			<div class="div_margin">
				<label class="control-label blue">
					产品类型&nbsp;:
				</label>
				<select id="product_type_waybill" class="selectpicker" style="width: 30%">
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
					条件&nbsp;: 
				</label>
				<select id="mark_1_waybill" class="selectpicker">
					<option selected="selected" value="0">></option>
					<option value="1">>=</option>
				</select>
				<label class="black" for="num_1_waybill">
					区间下限&nbsp; 
				</label>
				<input id="num_1_waybill" name="num_1_waybill" type="text" />
				<select id="num_1_uom_waybill" class="selectpicker">
					<option selected="selected" value="0">元</option>
				</select>
				<select id="rel_waybill" class="selectpicker">
					<option selected="selected" value="0">并且</option>
				</select>
			</div>
			<div class="div_margin">
				<label class="control-label blue">
					条件&nbsp;: 
				</label>
				<select id="mark_2_waybill" class="selectpicker">
					<option value="2"><</option>
					<option value="3"><=</option>
				</select>
				<label class="no-padding-right black" for="num_2_waybill">
					区间上限（可不填）&nbsp; 
				</label>
				<input id="num_2_waybill" name="num_2_waybill" type="text" />
				<select id="num_2_uom_waybill" class="selectpicker">
					<option selected="selected" value="0">元</option>
				</select>
			</div>
			<div class="div_margin">
				<label class="control-label blue" for="waybillDiscount_percent">
					单运单折扣率&nbsp;: 
				</label>
				<input id = "waybillDiscount_percent" name = "waybillDiscount_percent" type = "text"/>
				<select id="waybillDiscount_percent_uom" class="selectpicker">
					<option selected="selected" value="0">%</option>
				</select>
			</div>
			<div class="div_margin">
				<button id="save_discount_ec" class="btn btn-xs btn-yellow" onclick="save_waybill_discount_ec();" >
           			<i class="icon-save" ></i>保存
            	</button>
             	<button class="btn btn-xs btn-grey" type="button" onclick='returnWaybillDiscount();' >
              		<i class="icon-undo" ></i>返回
              	</button>
			</div>
		</div>
	</div>	
</div>
<div 
id="totalFreightDiscountDiv" 
class="form-group" 
<c:if test="${contractOwner.contractOwner == 'SF' }">style="display : none;"</c:if>>
	<div class="div_margin">
		<label class="control-label blue">
			总运费折扣
		</label>
		<input id="cb_0" type="checkbox" class="ace ace-switch ace-switch-5" ${eCC.total_freight_discount=='true' ? 'checked="checked"' : '' } onchange='discountShow();'/>
		<span class="lbl"></span>
	</div>
	<div id="discount_div" class="moudle_dashed_border">
		<div class="div_margin">
			<div>
				<div class="div_margin">
					<label class="control-label blue">
						---已维护规则---
					</label>
				</div>
				<table id="tFDList" class="with-border" border="1">
			   		<thead>
				  		<tr class="title">
				  			<td>序号</td>
				  			<td>产品类型</td>
				  			<td>阶梯类型</td>
				  			<td>区间</td>
				  			<td>折扣率</td>
				  			<td>保价费包含</td>
				  			<td>操作</td>
				  		</tr>
			  		</thead>
			  		<tbody>
			  		</tbody>
				</table>
			</div>
			<div class="add_button">
				<button class="btn btn-xs btn-yellow" onclick='javascript: $("#add").show()' >
                   	<i class="icon-hdd" ></i>新增
              	</button>
       		</div>
		</div>
		<div id="add" class="moudle_dashed_border_width_90">
			<div class="div_margin">
				<label class="control-label blue">
					产品类型&nbsp;:
				</label>
				<select id="product_type_fr" class="selectpicker" style="width: 30%">
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
				<select id="ladder_type_fr" class="selectpicker" style="width: 30%;" onchange="shiftPage_2()">
					<option selected="selected" value="0">请选择</option>
					<option value="1">无阶梯</option>
					<option value="2">总运费超过部分阶梯</option>
					<option value="3">总运费总折扣阶梯</option>
					<option value="4">单量折扣阶梯</option>
				</select>
				<div id="discount_0" class="moudle_dashed_border_width_90_red">
					<div id="discount_1" style="display: none">
						<div class="div_margin">
							<label id="add_label0" class="control-label blue">
								---超过部分阶梯价---
							</label>
						</div>
						<div class="div_margin">
							<label class="control-label blue">
								条件&nbsp;: 
							</label>
							<select id="mark_1" class="selectpicker">
								<option selected="selected" value="0">></option>
								<option value="1">>=</option>
							</select>
							<label id="add_label1" class="black" for="payAll">
								总运费&nbsp; 
							</label>
							<input id="payAll" name="payAll" type="text" />
							<select id="payAll_uom" class="selectpicker">
								<option selected="selected" value="0">元</option>
							</select>
							<select id="rel" class="selectpicker">
								<option selected="selected" value="0">并且</option>
							</select>
						</div>
						<div class="div_margin">
							<label class="control-label blue">
								条件&nbsp;: 
							</label>
							<select id="mark_2" class="selectpicker">
								<option value="2"><</option>
								<option value="3"><=</option>
							</select>
							<label id="add_label2" class="no-padding-right black" for="payAll_0">
								总运费&nbsp; 
							</label>
							<input id="payAll_0" name="payAll_0" type="text" />
							<select id="payAll_uom_0" class="selectpicker">
								<option selected="selected" value="0">元</option>
							</select>
						</div>
					</div>
					<div class="div_margin">
						<label class="control-label blue" for="discount_percent">
							折扣率&nbsp;: 
						</label>
						<input id = "discount_percent" name = "discount_percent" type = "text"/>
						<select id="dp_uom" class="selectpicker">
							<option selected="selected" value="0">%</option>
						</select>
					</div>
				</div>
			</div>
			<div class="div_margin">
				<label class="control-label blue">
					保价费包含&nbsp;:
				</label>
					<input id="cb_insurance_contain" type="checkbox" class="ace ace-switch ace-switch-5" />
				<span class="lbl"></span>
			</div>
			<div class="div_margin">
				<button id="save_discount_ec" class="btn btn-xs btn-yellow" onclick="save_discount_ec();" >
           			<i class="icon-save" ></i>保存
            	</button>
             	<button class="btn btn-xs btn-grey" type="button" onclick='returnDiscount()' >
              		<i class="icon-undo" ></i>返回
              	</button>
			</div>
		</div>
	</div>	
</div>
<div class="form-group form-actions" align="center">
	<button id="save_0" name="save_0" class="btn btn-xs btn-info btn-yellow" type="button" onclick="configureEC('1');">
		<i class="icon-save bigger-110" ></i>配置
	</button>
</div>
