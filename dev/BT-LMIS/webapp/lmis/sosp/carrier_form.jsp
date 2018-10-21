<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="moudle_dashed_border_show">
	<div class="div_margin">
		<label class="control-label blue">
			---对应承运商维护---
		</label>
	</div>
	<div class="div_margin">
		<table id="carrierList" class="with-border" border="1">
	   		<thead>
		  		<tr class="title">
		  			<td>承运商类型</td>
		  			<td>承运商</td>
		  			<td>操作</td>
		  		</tr>
	  		</thead>
	  		<tbody> 
	  			<c:forEach items="${carrierList }" var="carrierList">
	  				<tr>
	  					<td>${carrierList.carrier_type_name }</td>
	  					<td>${carrierList.carrier }</td>
	  					<td>
	  						<a onclick= "freight_config('${carrierList.carrier_type }','${carrierList.carrier_code }');" >配置</a>
	  						/
	  						<a onclick= "del_carrier(${carrierList.id });" >删除</a>
	  					</td>
	  				</tr>
	  			</c:forEach>
	  		</tbody>
		</table>
	</div>
	<div class="add_button">
		<button class="btn btn-xs btn-yellow" 
			onclick='javascript:
				$("#div_1_fr").show();
				$("#carrier_config").hide();
				$("#eCC_id").val("");
				'>
           	<i class="icon-hdd" ></i>新增
       	</button>
	</div>
	<div id="div_1_fr" class="moudle_dashed_border_width_90">
		<div class="div_margin">
			<label class="control-label blue">
				承运商类型&nbsp;:
			</label>
			<select id="carrier_type_fr" class="selectpicker" style="width: 30%;" onchange="search_carrier();">
				<option selected="selected" value="0">请选择</option>
				<option value="1">快递</option>
				<option value="2">物流</option>
			</select>
		</div>
		<div id="div_2_fr" class="div_margin" style="display : none">
			<label class="control-label blue">
				承运商&nbsp;:
			</label>
			<select id="carrier_fr" class="selectpicker" style="width: 30%;">
			</select>
		</div>
		<div class="div_margin">
			<button id="save_jibanpao_ec" class="btn btn-xs btn-yellow" onclick="add_carrier();" >
           		<i class="icon-save" ></i>保存
           	</button>
           	<button class="btn btn-xs btn-grey" type="button" onclick='returnCarrier();' >
           		<i class="icon-undo" ></i>返回
           	</button>
		</div>
	</div>
</div>
<div id="carrier_config" style="display : none">
	<!-- 快递合同配置ID -->
	<input id="eCC_id" type="hidden" />
	<!-- 快递主体ID -->
	<input id="contractOwner" type="hidden" />
	<%@ include file="/lmis/express_contract/carrierContractConfig.jsp" %>
</div>
<div class="form-group">
	<input id="carrierFeeFlagId" type="hidden" value="${carrierFeeFlag.id }"/>
	<div>
		<div class="div_margin">
			<label class="control-label blue">
				总运费折扣
			</label>
			<input 
			id="cb_totalFreightDiscount" 
			type="checkbox" 
			class="ace ace-switch ace-switch-5" ${carrierFeeFlag.totalFreightDiscount_flag == 'true' ? 'checked="checked"' : '' } 
			onchange='discountShow_2();'/>
			<span class="lbl"></span>
		</div>
		<div id="totalDiscountDiv" class="moudle_dashed_border">
			<div class="div_margin">
				<div>
					<div class="div_margin">
						<label class="control-label blue">
							---已维护规则---
						</label>
					</div>
					<table id="tFDList_2" class="with-border" border="1">
				   		<thead>
					  		<tr class="title">
					  			<td>序号</td>
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
					<button class="btn btn-xs btn-yellow" onclick='javascript: $("#addTotalDiscount").show()' >
	                   	<i class="icon-hdd" ></i>新增
	              	</button>
	       		</div>
			</div>
			<div id="addTotalDiscount" class="moudle_dashed_border_width_90">
				<div class="div_margin">
					<label class="control-label blue">
						阶梯类型&nbsp;:
					</label>
					<select id="totalDiscount_ladderType" class="selectpicker" style="width: 30%;" onchange="shift_totalDiscount_ladderType();">
						<option selected="selected" value="0">请选择</option>
						<option value="1">无阶梯</option>
						<option value="2">总运费超过部分阶梯</option>
						<option value="3">总运费总折扣阶梯</option>
						<option value="4">单量折扣阶梯</option>
					</select>
					<div id="ladderType_div1" class="moudle_dashed_border_width_90_red">
						<div id="ladderType_div2" style="display: none">
							<div class="div_margin">
								<label id="add_ladderType_label0" class="control-label blue">
									---超过部分阶梯价---
								</label>
							</div>
							<div class="div_margin">
								<label class="control-label blue">
									条件&nbsp;: 
								</label>
								<select id="ladderType_mark_1" class="selectpicker">
									<option selected="selected" value="0">></option>
									<option value="1">>=</option>
								</select>
								<label id="add_ladderType_label1" class="black" for="payAll">
									总运费&nbsp; 
								</label>
								<input id="ladderType_payAll" name="payAll" type="text" />
								<select id="ladderType_payAll_uom" class="selectpicker">
									<option selected="selected" value="0">元</option>
								</select>
								<select id="ladderType_rel" class="selectpicker">
									<option selected="selected" value="0">并且</option>
								</select>
							</div>
							<div class="div_margin">
								<label class="control-label blue">
									条件&nbsp;: 
								</label>
								<select id="ladderType_mark_2" class="selectpicker">
									<option value="2"><</option>
									<option value="3"><=</option>
								</select>
								<label id="add_ladderType_label2" class="no-padding-right black" for="payAll_0">
									总运费&nbsp; 
								</label>
								<input id="ladderType_payAll_0" name="payAll_0" type="text" />
								<select id="ladderType_payAll_uom_0" class="selectpicker">
									<option selected="selected" value="0">元</option>
								</select>
							</div>
						</div>
						<div class="div_margin">
							<label class="control-label blue" for="discount_percent">
								折扣率&nbsp;: 
							</label>
							<input id = "ladderType_discount_percent" name = "discount_percent" type = "text"/>
							<select id="ladderType_dp_uom" class="selectpicker">
								<option selected="selected" value="0">%</option>
							</select>
						</div>
					</div>
				</div>
				<div class="div_margin">
					<label class="control-label blue">
						保价费包含&nbsp;:
					</label>
						<input id="cb_tFD_insurance_contain" type="checkbox" class="ace ace-switch ace-switch-5" />
					<span class="lbl"></span>
				</div>
				<div class="div_margin">
					<button class="btn btn-xs btn-yellow" onclick="save_TotalDiscount();" >
	           			<i class="icon-save" ></i>保存
	            	</button>
	             	<button class="btn btn-xs btn-grey" type="button" onclick='return_TotalDiscount()' >
	              		<i class="icon-undo" ></i>返回
	              	</button>
				</div>
			</div>
		</div>
	</div>
	<div>
		<div class="div_margin">
			<label class="control-label blue">
				管理费
			</label>
			<input 
			id="cb_managementFee" 
			type="checkbox" 
			class="ace ace-switch ace-switch-5" ${carrierFeeFlag.managementFee_flag == 'true' ? 'checked="checked"' : '' } 
			onchange='managementShow();'/>
			<span class="lbl"></span>
		</div>
		<!-- 管理费 -->
		<div id="managementFee" class="moudle_dashed_border">
			<%@ include file="/lmis/express_contract/managementFee.jsp" %>
		</div>
	</div>
</div>
<div class="form-group form-actions" align="center">
	<button id="save_carrierFeeFlag" name="save_carrierFeeFlag" class="btn btn-xs btn-info btn-yellow" type="button" onclick="save_carrierFeeFlag();">
		<i class="icon-save bigger-110" ></i>配置
	</button>
</div>