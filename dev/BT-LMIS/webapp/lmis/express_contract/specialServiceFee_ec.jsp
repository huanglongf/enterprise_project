<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="form-group col-xs-12">
	<div class="div_margin">
		<label class="control-label blue">
			COD手续费
		</label>
		<input id="cb_3" type="checkbox" class="ace ace-switch ace-switch-5" ${eCC.cod == 'true' ? 'checked="checked"' : '' } onchange="codShow();"/>
		<span class="lbl"></span>
	</div>
	<div id="div_3" class="moudle_dashed_border">
		<div id="div_4" class="div_margin" style="display:none">
			<label class="control-label blue">
				COD收费方式&nbsp;:
			</label>
			<input id="sSE_id" type="hidden" />
			<select id="CODChargeMethod" class="selectpicker" style="width:30%" onchange="shiftPage_4()">
				<option selected="selected" value="0"
					${findData.status == "0" ? 'selected = "selected"' : '' }>请选择
				</option>
				<option value="1"
					${findData.status == "1" ? 'selected = "selected"' : '' }>单运单订单金额百分比（向上取整）
				</option>
				<option value="2"
					${findData.status == "2" ? 'selected = "selected"' : '' }>单运单订单金额百分比
				</option>
				<option value="3"
					${findData.status == "3" ? 'selected = "selected"' : '' }>单运单代收货款金额百分比
				</option>
			</select>
		</div>
		<div id="div_5" style="display: none;">
			<div class="div_margin">
				<span id="CODFormula" style="color: red">
					计算公式 : 费用 = IF(ROUNDUP(代收货款金额 * 百分比, 精确小数位) <= 参数1, 收费1, ROUNDUP(货值 * 百分比, 精确小数位))
				</span>
			</div>
			<div class="div_margin">
				<span id="CODFormulaExplain" style="color: red">公式说明 : 最低收费，百分比，精确小数位，参数1，收费1 需要在下方设置</span>
			</div>
			<div class="div_margin">
				<label class="control-label blue">
					---参数设置---
				</label>
			</div>
			<div class="div_margin">
				<input id="cb_5" type="checkbox" class="ace ace-switch ace-switch-5"/>
				<span class="lbl"></span>
				<label class="control-label black" for="charge_min_cod">
					最低收费&nbsp;: 
				</label>
				<input id="charge_min_cod" name="charge_min_cod" type="text"/>
				<select id="charge_min_uom_cod" class="selectpicker">
					<option selected="selected" value="0">元</option>
				</select>
			</div>
			<div class="div_margin">
				<label class="control-label black" for="percent_cod">
					百分比&nbsp;: 
				</label>
				<input id="percent_cod" name="percent_cod" type="text"/>
				<select id="percent_uom_cod" class="selectpicker">
					<option selected="selected" value="0">%</option>
				</select>
			</div>
			<div id="div_6" style="display: none;">
				<div class="div_margin">
					<label class="control-label black" for="input_6">
						精确小数位&nbsp;:
					</label>
					<input id="accurate_decimal_place_cod" name="accurate_decimal_place_cod" type="text"/>
				</div>
				<div class="div_margin">
					<label class="control-label black" for="param_1_cod">
						参数1&nbsp;:
					</label>
					<input id="param_1_cod" name="param_1_cod" type="text"/>
					<select id="param_1_uom_cod" class="selectpicker">
						<option selected="selected" value="0">
							元
						</option>
					</select>
				</div>
				<div class="div_margin">
					<label class="control-label black" for="charge_1_cod">
						收费1&nbsp;: 
					</label>
					<input id="charge_1_cod" name="charge_1_cod" type="text"/>
					<select id="charge_1_uom_cod" class="selectpicker">
						<option selected="selected" value="0">元</option>
					</select>
				</div>
			</div>
		</div>
	</div>
</div>															
<div class="form-group col-xs-12">
	<div class="div_margin">
		<label class="control-label blue">
			委托取件费
		</label>
		<input id="cb_4" type="checkbox" class="ace ace-switch ace-switch-5" ${eCC.delegated_pickup == 'true' ? 'checked="checked"' : '' } onchange="delegatedPickupShow();"/>
		<span class="lbl"></span>
	</div>
	<div id="div_7" class="moudle_dashed_border">
		<div class="div_margin">
			<label class="control-label blue">
				委托取件费&nbsp;:
			</label>
			<input id="delegated_pickup" name="pay" type="text" style="width:30%" value="${employee.username }"/>
			<select id="delegated_pickup_uom" class="selectpicker">
				<option selected="selected" value="0"
					${findData.status == "0" ? 'selected = "selected"' : '' }>
					元/单
				</option>
			</select>
		</div>
	</div>
</div>
<div class="form-group form-actions col-xs-12" align="center">
	<button id="save_2" name="save_2" class="btn btn-xs btn-info btn-yellow" type="button" onclick="configureEC('3');">
       	<i class="icon-save bigger-110" ></i>配置
   	</button>
</div>
