<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="div_margin">
	<div class="div_margin">
		<span style="color: red">计算公式 : 管理费 = SUM(各项组合费用)</span>
	</div>
	<div class="div_margin">
		<span style="color: red">
			公式说明 : 将管理费分为多个任意搭配的组合，分别定义这些组合的组合方式以及计算规则，最后将所有组合求和算出管理费。
		</span>
	</div>
	<div>
		<div class="div_margin">
			<label class="control-label blue">
				---已维护规则---
			</label>
		</div>
		<table id="manECList" class="with-border" border="1">
	   		<thead>
		  		<tr class="title">
		  			<td>序号</td>
		  			<td>承运商</td>
		  			<td>搭配组合</td>
		  			<td>阶梯类型</td>
		  			<td>区间</td>
					<td>收费率</td>
		  			<td>操作</td>
		  		</tr>
	  		</thead>
	  		<tbody>
	  		</tbody>
		</table>
	</div>
	<div class="add_button">
		<button class="btn btn-xs btn-yellow" onclick='javascript:$("#add_1").show();' >
       		<i class="icon-hdd" ></i>新增
      	</button>
	</div>
</div>
<div id="add_1" class="moudle_dashed_border_width_90">
	<div class="div_margin">
		<label class="control-label blue">
			已维护承运商&nbsp;:
		</label>
		<select id="carrierUsed_man" class="selectpicker" style="width: 30%;" >
			<c:if test= "${empty carrierList }" >
				<option selected= "selected" value= "">无</option>
			</c:if>
			<c:if test= "${not empty carrierList }" >
				<option selected= "selected" value= "ALL">所有已维护承运商</option>
			</c:if>
			<c:forEach items= "${carrierList }" var= "carrier">
				<option value="${carrier.carrier_code }" >${carrier.carrier }</option>
			</c:forEach>
		</select>
	</div>
	<div class="div_margin">
		<label class="control-label blue">
			---搭配组合---
		</label>
	</div>
	<div class="div_margin">
		<input id="cb_8" type="checkbox" class="ace ace-switch ace-switch-5"/>
		<span class="lbl"></span>
		<label class="control-label blue">
			运费
		</label>
		<div id="insurance_man" style="display : inline;" >
			<input id="cb_9" type="checkbox" class="ace ace-switch ace-switch-5" />
			<span class="lbl"></span>
			<label class="control-label blue">
				保价费
			</label>
		</div>
		<div id="specialService_man" style="display : inline; display : none;">
			<input id="cb_10" type="checkbox" class="ace ace-switch ace-switch-5" onchange="specialServiceSwitch();"/>
			<span class="lbl"></span>
			<label class="control-label blue">
				特殊服务费
			</label>
			<div id="div_9" class="moudle_dashed_border_width_90_red">
				<div class="div_margin">
					<label class="control-label blue">
						---特殊服务费包含---
					</label>
				</div>
				<div class="div_margin">
					<div id="cod_man" style="display:inline;${eCC.cod==1 ? 'display : none;' : '' }">
						<input id="cb_11" type="checkbox" class="ace ace-switch ace-switch-5"/>
						<span class="lbl"></span>
						<label class="control-label blue">
							COD手续费
						</label>
					</div>
					<div id="delegated_pickup_man" style="display:inline;${eCC.delegated_pickup==1 ? 'display : none;' : '' }">
						<input id="cb_12" type="checkbox" class="ace ace-switch ace-switch-5"/>
						<span class="lbl"></span>
						<label class="control-label blue">
							委托取件费
						</label>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="div_margin">
		<span style="color: red">计算公式 : 管理费 = 组合费用 * 收费比率</span>
	</div>
	<div class="div_margin">
		<label class="control-label blue">
			阶梯设置&nbsp;:
		</label>
		<select id="ladder_type_man" class="selectpicker" style="width:30%" onchange="shiftPage_5()">
			<option selected="selected" value="0">请选择</option>
			<option value="1">无阶梯</option>
			<option value="2">总费用阶梯价</option>
			<option value="3">超出部分阶梯价</option>
		</select>
	</div>
	<div id="div_10" class="moudle_dashed_border_width_90_red">
		<div id="div_11" style="display: none">
			<div class="div_margin">
				<label class="control-label blue">
					条件&nbsp;: 
				</label>
				<select id="compare_1_man" class="selectpicker">
					<option selected="selected" value="0">></option>
					<option value="1">>=</option>
				</select>
				<label class="control-label black" for="payAll_1_man">
					总费用&nbsp; 
				</label>
				<input id="payAll_1_man" name="payAll_1_man" type="text"/>
				<select id="payAll_1_uom_man" class="selectpicker">
					<option selected="selected" value="0">元</option>
				</select>
				<select id="rel_man" class="selectpicker">
					<option selected="selected" value="0">并且</option>
				</select>
			</div>
			<div class="div_margin">
				<label class="control-label blue">
					条件&nbsp;:
				</label>
				<select id="compare_2_man" class="selectpicker">
					<option selected="selected" value="2"><</option>
					<option value="3"><=</option>
				</select>
				<label class="control-label black" for="payAll_2_man">
					总费用&nbsp; 
				</label>
				<input id="payAll_2_man" name="payAll_2_man" type="text"/>
				<select id="payAll_2_uom_man" class="selectpicker">
					<option selected="selected" value="0">元</option>
				</select>
			</div>
		</div>
		<div class="div_margin">
			<label class="control-label blue" for="charge_percent_man">
				收费率&nbsp;: 
			</label>
			<input id="charge_percent_man" name="charge_percent_man" type="text"/>
			<select id="charge_percent_uom_man" class="selectpicker">
				<option selected="selected" value="0">%</option>
			</select>
		</div>
		<div class="div_margin">
			<button class="btn btn-xs btn-yellow" onclick="saveManagementEC();" >
           		<i class="icon-save" ></i>保存
           	</button>
       		<button class="btn btn-xs btn-grey" type="button" onclick="returnManagement();">
				<i class="icon-undo" ></i>返回
   			</button>
		</div>
	</div>
</div>
