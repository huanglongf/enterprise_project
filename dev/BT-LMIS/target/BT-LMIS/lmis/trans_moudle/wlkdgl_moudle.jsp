<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
$(document).ready(function(){ 
 	getExpressTab($("#id").val());
	getExpress();
});
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="moudle_dashed_border_show">
	<div>
		<div>
			<div class="div_margin">---已维护公式---</div>
		</div>
	<tab_express></tab_express>
	</div>
	<div class="add_button">
		<a data-toggle="tab" href="#tab_add">
			<button class="btn btn-xs btn-yellow" onclick="changeShow('add_customer')">
				<i class="icon-hdd"></i>新增
			</button>
		</a>
	</div>
	
<div  style="display: none;" id="add_customer">					
	<div class="div_margin">
	   <express></express>
</div>										

<div class="div_margin">
	<a data-toggle="tab" href="#tab_add">
		<button class="btn btn-xs btn-yellow" id="save_catDiv3" onclick="saveTab()">
			<i class="icon-hdd"></i><span id="but_text_3">保存</span>
		</button>
	</a>
</div>	
</div>


<div style="display:none;" id="jz_type_div">
   <input type="hidden" id="wk_transport_code"/>
	<div class="div_margin" >
		<label class="control-label blue">
			计重方式&nbsp;:
		</label>
		<select id="weight" class="selectpicker" style="width: 30%;" onchange="changeShow('table_wk')">
			<option selected="selected" value="0">请选择</option>
			<option value="1" selected="selected">实际重量
			</option>
		</select>
	</div>
	
		<div class="div_margin" id="table_wk">
		
          <wk_express></wk_express>
			<div class="add_button">
				<button class="btn btn-xs btn-yellow" onclick="changeShow_yf('wl_ex_div')" >
               		<i class="icon-hdd" ></i><span id="add_text_wk">新增</span>
               	</button>
       		</div>
 </div>     	
       		
 <div id="wl_ex_div" style="display:none">
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
						<select id="wk_formula"   class="selectpicker" onchange="changeCat_wk();">
							<option value="0">请选择</option>
							<option value="1">公式1：总费用向上取整</option>
							<option value="2">公式2：计费重量向上取整</option>
							<option value="3">公式3：首重价格+续重价格（不向上取整）</option>
							<option value="4">公式4：EMS-首重0.5kg续重0.5kg报价</option>
						</select>
					</div>
				</div>      		

<div id="wk1" class="div_margin" style="display: none">
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
						<label class="control-label blue" for="wk_decimal_1"> 精确小数位&nbsp;:</label>
						<input id="wk_decimal_1" name="wk_decimal_1" type="text" value=""/>
					</div>
</div>


<div id="wk2" class="div_margin" style="display: none">
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
						<label class="control-label blue" for="wk_decimal_2">精确小数位&nbsp;:</label>
						<input id="wk_decimal_2" name="wk_decimal_2" type="text" value="${employee.username }"/>
					</div>
				</div>									
</div>															

<div id="wk3" class="div_margin" style="display: none">
					<div class="div_margin">
						<span style="color: red">公式 : 费用  = ((计费重量 - 首重) * 续重单价 + 首重价格) * 折扣</span>
					</div>
					<div class="div_margin">
						<span style="color: red">公式说明 : 当计费重量小于等于首重时，运费 = 首重价格 * 折扣；当计费重量大于首重时，用上述公式计算。</span>
					</div>
</div>
										
<input type="hidden" id="ch_id"/>

<div id="wk4" class="div_margin" style="display: none">
					<div class="div_margin">
						<span style="color: red">公式 : 费用 = ((ROUNDUP(计费重量 * 2, 0) * 0.5 - 首重) * 续重单价 * 2 + 首重价格) * 折扣</span>
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
</div>				
				<div class="div_margin">
					<button id="save_formula_ec" class="btn btn-xs btn-yellow" onclick="save_wk()" >
	                   	<i class="icon-save" ></i>保存
	              	</button>
				</div>	

</div>
<script>
 function saveTab(){
	 if($("#id").val()==""){
		 alert("请先维护合同主信息");
		 }
	  data="transport_id="+$("#wl_express").val()+"&contract_id="+$("#id").val();
	  var url=root+"/control/transOrderController/addExpress.do";
	  ajax_save_Td(data, url, "");
	 }

 function ajax_save_Td(data,url,butId){
	   $.ajax({
			type : "POST",
			url: url,  
			data:data,
			dataType: "json",  
			success : function(jsonStr) {
				alert(jsonStr.result_reason);
				if(jsonStr.result_flag==1){
					getExpressTab($("#id").val());
				}
			}
		});
}


 function changeCat_wk(){
		var divId = $("#wk_formula").val();
		var divObj = $("div[id^='wk']");
		for (i = 0; i <=divObj.length; i++) {
			if (i == divId) {
				$("#wk" + i).css("display", "block");
			} else {
				$("#wk" + i).css("display", "none");
			}
		}		
	 }

 function save_wk(){
	var transport_code= $("#wk_transport_code").val();
	var cat_code=$("#wk_formula").val();
	var cat_name=$('#wk_formula  option:selected').text();  
	var decimal_num=0;
	var op_type="";
	var id="";
	if(cat_code=="0"){
     alert("请选择计重方式");
	}
	var url=root+"/control/transOrderController/save_wk.do";
	if(cat_code=="1"){
		decimal_num=$("#wk_decimal_1").val();
	}
	if(cat_code=="2"){
		decimal_num=$("#wk_decimal_2").val();
	}
	var contract_id=$("#id").val();

	if($("#add_text_wk").text()=="取消编辑"){
	op_type="update";
	id=$("#ch_id").val();
	}else{
	op_type="add";
	}
	var data="cat_code="+cat_code+"&cat_name="+cat_name+"&decimal_num="+decimal_num+"&transport_code="+transport_code+"&contract_id="+contract_id+"&op_type="+op_type+"&id="+id;
	ajax_save_wk(data,url);
    }



 function ajax_save_wk(data,url){
	   $.ajax({
			type : "POST",
			url: url,  
			data:data,
			dataType: "json",  
			success : function(jsonStr) {
				if(jsonStr.result_flag==1){
					alert(jsonStr.result_reason);
					init_wk();
				}else{
					alert(jsonStr.result_reason);
				}
				// $("#wk_formula").attr("disabled",true);
			}
		});
}
</script>