<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script>
$(document).ready(function(){ 
	if($("#contractOwner").val()!="" && $("#contractType").val()=='2'){
		getFormula_tab($("#id").val());
		getDiscountTd($("#id").val(),'1','dt2');
		getcarriageInfo($("#id").val())		
		}
	getGood_type($("#contractOwner").val());
});
</script>
<div class="moudle_dashed_border_show_90">
								 <div>
										<div class="div_margin">
											<span>---已维护的阶梯----</span>
										</div>
                                       <dt1></dt1>
								  </div>
										<div class="add_button">
											<a data-toggle="tab" href="#tab_add">
												<button class="btn btn-xs btn-yellow" id="add_button_yf" onclick="changeShow_yf('catDiv')">
													<i class="icon-hdd"></i><span id="add_text_yf">新增</span>
												</button>
											</a>
										</div>
								<div style="display: none;" id="catDiv" class="moudle_dashed_border_width_90">
								<form  id="catDivForm">
									<div class="div_margin">
										<div>
											计费方式: <select id="cat_type" name="" onchange="changeCat();">
												<option>请选择计费方式</option>
												<option value="1">公式1:计抛计重价格取大值</option>
												<option value="2">公式2:按公斤结算</option>
												<option value="3">公式3:基重价+续重价</option>
												<option value="4">公式4:按商品类型</option>
<!-- 												<option value="3">按件数计算</option> -->
											</select>
										</div>
									</div>
									<!-- 计抛计重价格取大值 -->
									<div id="cat1" style="display: none;">
										<div class="div_margin">
											<span style="color: red">计算公式 : 运费 =max(实际重量*实重单价，立方数*立方单价) * 折扣</span>
										</div>
										<div class="div_margin">
											<span>抛货计算标准:
											<input id="standard_args" name="standard_args" type="text" value="" datatype="s6-18"  errormsg="信息至少6个字符,最多18个字符！"  /></span>
											 <select>
												<option>KG/m³</option>
											</select>
										</div>
										<div style="display: none">
											<span>最低一票重量:<input id="low_weight" name="low_weight" type="text" value="0" /></span> 
											<select>
												<option>KG</option>
											</select>
										</div>
										
										 <div class="div_margin">
										    <a data-toggle="tab" href="#tab_add">
											  <button class="btn btn-xs btn-yellow" id="catDiv1_but" onclick="save_catDiv('catDivForm','catDiv1_but')">
											 	<i class="icon-hdd"></i><span id="but_text_0">保存</span>
											  </button>
										   </a>
								         </div>				
								         	</form> 							
									</div>

									<!-- 按公斤结算 -->
									<div id="cat2" style="display: none;">
									<form id="cat1Form">
										<div>
											<span style="color: red">计算公式 : 运费 = 计重 * 公斤单价 * 折扣</span>
										</div>
										<br/> 
										<div style="display: none">
										<span>最低一票重量:<input id="low_weight2" name="low_weight"  type="text" value="0" /></span> 
										<select>
											<option>KG</option>
										</select>
								    </div>
										 <div class="div_margin">
										    <a data-toggle="tab" href="#tab_add">
											  <button class="btn btn-xs btn-yellow" id="save_catDiv1" onclick="save_catDiv('cat1Form','save_catDiv1')">
											 	<i class="icon-hdd"></i><span id="but_text_1">保存</span>
											  </button>
										   </a>
										 </div>		
										</form>								
									</div>
									

									<!-- 基重价+续重价 -->
									<div id="cat3" style="display: none;">
									<form id="cat2Form">
										<div>
											<span style="color: red">计算公式 : 运费 = （基价 + （计重 -基重）*续重单价）*折扣</span>
<!-- 											<input type="hidden" name="cat_type" value="2" /> -->
										</div>
										 <div class="div_margin">
										    <a data-toggle="tab" href="#tab_add">
											  <button class="btn btn-xs btn-yellow" id="save_catDiv2" onclick="save_catDiv('cat2Form','save_catDiv2')">
											 	<i class="icon-hdd"></i><span id="but_text_2">保存</span>
											  </button>
										   </a>
										 </div>	
										 
									</form>
									</div>
									<!-- 按商品类型-->
									<div id="cat4" style="display: none;">
									<form id="cat3Form">
										<div>
											<span style="color: red">计算公式 : 运费 = 件数 * 单价</span>
<!-- 											<input type="hidden" name="cat_type" value="3" /> -->
										</div>
										
										 <div class="div_margin">
										    <a data-toggle="tab" href="#tab_add">
											  <button class="btn btn-xs btn-yellow" id="save_catDiv3" onclick="save_catDiv('cat3Form','save_catDiv3')">
											 	<i class="icon-hdd"></i><span id="but_text_3">配置</span>
											  </button>
										   </a>
								        </div>	
								   </form>     										
									</div>

								 							
								</div>
								<hr>	
					<div <c:if test='${expressContract.contract_type!=2 }'> style="display:none;"</c:if>>
					 <div class="form-group">
							<div class="form-group">
								<span>总运费折扣:</span>
									<input id="carriage_discount_switch" type="checkbox" class="ace ace-switch ace-switch-5" onchange="changeShow('disDiv')" />
								<span class="lbl"></span>
							</div>
					</div>	
					    <div style="display: none" id="disDiv">
						<div class="div_margin">
							<span>---已维护的阶梯----</span>
						</div>
						<dt2></dt2>
						<div class="add_button">
							<a data-toggle="tab" href="#tab_add">
								<button class="btn btn-xs btn-yellow" onclick="changeShow('add_dis_div')">
								<i class="icon-hdd"></i>新增
							</button>
							</a>
						</div>	
	
				<div id="add_dis_div" class="moudle_dashed_border_width_90">
				<form id="disContForm">
			    <div class="div_margin">
				<label class="control-label blue">
					产品类型&nbsp;:
				</label>
			    <gt></gt>

			</div>
			
			
			<div class="div_margin">
				<label class="control-label blue">
					阶梯类型&nbsp;:
				</label>
				<select id="disCont" class="selectpicker" style="width: 30%;" onchange="show_div()" name="interva_type_id">
					<option selected="selected" value="0" > 请选择</option>
					<option value="1">无阶梯</option>
					<option value="2">总运费超过部分阶梯</option>
					<option value="3">总运费总折扣阶梯</option>
					<option value="4">单量阶梯折扣</option>
				</select>
			</div>
			
				<div id="discount_1x" class="moudle_dashed_border_width_90_red">
				<input type="hidden" value="" name="discount_type" id="discountTypes"/>
				<input type="hidden" value="" name="discount" id="discountIds"/>
					<div class="div_margin">
						<label class="control-label blue" for="d1">
							折扣率&nbsp;: 
						</label>
						<input id="dicount_lv" type="text" value=""/>
						<select id="d1_mark" class="selectpicker">
							<option selected="selected" value="0">%</option>
						</select>
					</div>
						<div class="div_margin">
		<a data-toggle="tab" href="#tab_add">
			<button class="btn btn-xs btn-yellow" id="discount_but" onclick="saveDisCount('disContForm','discount_but')">
			<i class="icon-hdd"></i>保存
		</button>
		</a>
	</div>	
	</form>		
		</div>		
				
				<form id="discount_2From">
			       <div id="discount_2" class="moudle_dashed_border_width_100">
											<span style="color: red">计算公式 : 折扣 = 订单金额 * 收费比率</span><br />
											<span style="color: red">公式说明：折扣为阶梯区间，需要在下方设置</span><br />
											<input type="hidden" name="section" id="section" value=""/>
											<input type="hidden" name="interva_type_id" id="interva_type_id">
											<input type="hidden" name="good_type" id="interva_good_type" value="">
											<input type="hidden" name="discount" id="discount" value="">
											<input type="hidden" name="discount_type" id="discount_type2" value="">
											<div class="div_margin">
												<span>---阶梯设置---</span>
											</div>
											<div class="div_margin">

												条件 <select id="mark1">
													<option value=">">></option>
													<option value=">=" >>=</option>
<!-- 													<option><</option> -->
<!-- 													<option><=</option> -->
												</select>
												<span id="text_arg1">  订单金额</span><input type="text" id="agrs1"/>
												       <select><option>元</option></select>
											</div>
											<div class="div_margin" >
												组合方式:<select id="mark2">
<!-- 												         <option value="0">或者</option> -->
													     <option value="1">并且</option>
											 		   </select>
											</div>
											<div class="div_margin" >
												条件 <select id="mark3">
<!-- 													<option>></option> -->
<!-- 													<option>>=</option> -->
													<option value="<"><</option>
													<option value="<="><=</option>
												</select>
												<span id="text_arg2">  订单金额</span><input type="text" id="agrs2"/>
												 <select><option>元</option></select>
											</div>
											<div class="div_margin">
												 <input type="radio"  name="discount_type_radio" value="1" />收费比率:<input type="text" id="discount_arg" />&nbsp;<select><option>%</option></select>
											     &nbsp;&nbsp;&nbsp;
											     <input type="radio"  name="discount_type_radio" value="2"/>&nbsp;收费:<input type="text" id="discount_arg2" />&nbsp;<select><option>元</option></select>
											</div>
	<div>
	</div>
		<a data-toggle="tab" href="#tab_add">
			<button class="btn btn-xs btn-yellow" id="discount_but2" onclick="saveSection('discount_2From','discount_but2')">
			<i class="icon-hdd"></i>保存
		</button>
		</a>
	</div>	
	</div>
	</form>			
	

		
		<input type="hidden" id="Formula_tab_data" value=""/>	
		<input type="hidden" id="Formula_tab_data_id" value=""/>	
</div>	
</div>
</div>
    <div class="div_margin">
		<a data-toggle="tab" href="#tab_add">
			<button class="btn btn-xs btn-yellow" id="discount_but2" onclick="saveSwitch()">
			<i class="icon-hdd"></i>保存
		</button>
		</a>
	</div>	
<script>
   function save_catDiv(formId,catDiv1_but){
	      var url=root+"/control/transOrderController/saveDetail.do";
		   var data=$("#"+formId).serialize();
			   if($("#add_text_yf").text()=="取消编辑"){
					 data=data+"&op_type=1&id="+$("#Formula_tab_data_id").val();
		   }
		if($("#id").val()==""){
			alert("请先维护合同主信息");
			return;
		}else{
			data=data+"&contract_id="+$("#id").val()+"&belong_to="+$("#contractOwner").val();
			if($("#cat_type").val()=="0"){
			    alert("请选择计费公式");
			    return;
				}
			if($("#cat_type").val()=="1"){
		    data=data+"&formula=公式1:计抛计重价格取大值";
			}
			if($("#cat_type").val()=="2"){
			data=data+"&formula=公式2:按公斤结算";
			}
			if($("#cat_type").val()=="3"){
			data=data+"&formula=公式3:基重价续重价";
			}	
			if($("#cat_type").val()=="4"){
			data=data+"&formula=公式4:按商品类型";
			}		
			data=data+"&cat_type="+$("#cat_type").val();
			if(!checkInp(formId)){
				return;
			}
			ajax_save(data,url,"");
			
		}

   }

   function saveDisCount(formId,catDiv1_but){
	   var url=root+"/control/transOrderController/saveDiscount.do";
	   var data="";
		if($("#id").val()==""){
			alert("请先维护合同主信息");
			return;
		}else{
			data=data+"&contract_id="+$("#id").val()+"&belong_to="+$("#contractOwner").val();
			if($("#disCont").val()=="1"){
		    data=data+"&interval_type_name=无阶梯";
		    
			}
			if($("#disCont").val()=="2"){
			data=data+"&interval_type_name=总运费超过部分阶梯";
			}
			if($("#disCont").val()=="3"){
			data=data+"&interval_type_name=总运费总折扣阶梯";
			}	
			if($("#disCont").val()=="4"){
			data=data+"&interval_type_name=单量阶梯折扣";
			$("#discountTypes").val($("#disCont").val());
			}
			data=data+"&if_disount=1"
			data=data+"&cat_id=1"
			if(!checkInp(formId)){
				return;
			}
			if($("#disCont").val()=="1"){
			$("#discountIds").val($("#dicount_lv").val());
			$("#discountTypes").val($("#disCont").val());
			}
			data=$("#"+formId).serialize()+data;
			ajax_save(data,url,catDiv1_but);
		}
   }
   
   function saveSection(){
	   var result=getSection("mark1","mark2","mark3","agrs1","agrs2");
       if(!result){
       return;
       }
	   $("#section").val(result);
	   $("#interva_good_type").val($("#good_type").val());
	   $("#interva_type_id").val($("#disCont").val());
	   var dis_type=$("input[name='discount_type_radio']:checked").val();
	   if(dis_type=="1"){
		if(not_num_or_empty("discount_arg","收费比率")){
		return;
	   }
	   $("#discount").val($("#discount_arg").val());
	   }
	   else if(dis_type=="2"){
		if(not_num_or_empty("discount_arg2","资金金额")){
		return;
		 }
		$("#discount").val($("#discount_arg2").val());
	   }else{
		alert("必须选择一种收费方式！");
		return;
	   }
	   $("#discount_type2").val(dis_type);
	   saveDisCount('discount_2From','discount_but2');
   }

   function saveSwitch(){
	   var url=root+"/control/transOrderController/updateCarriagelData.do";
	   var data="contract_id="+$("#id").val()+"&belong_to="+$("#contractOwner").val();
        if(isChecked('carriage_discount_switch')){
           data=data+"&if_disount=1";
            }else{
            	 data=data+"&if_disount=0";
                }

 	   $.ajax({
			type : "POST",
			url: url,  
			data:data,
			dataType: "json",  
			success : function(jsonStr) {
					alert(jsonStr.result_reason);
			}
		});
	   }
</script>
