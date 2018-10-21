<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script>
$(document).ready(function(){ 
	if($("#contractOwner").val()!="" && $("#contractType").val()!='1'){
		getFormula_tab($("#id").val());
		getDiscountTd_for_offer($("#id").val(),'2','td_offer');	
		initData($("#id").val());	
		}
});
</script>							
<div class="form-group">
		<div class="form-group">
			<span>保价方式:</span> 
				<input id="if_baojia" type="checkbox" class="ace ace-switch ace-switch-5" onchange="changeShow('disDiv2')"/>
			 <span class="lbl"></span>
		</div>
</div>
								
<div id="disDiv2"  class="moudle_dashed_border">

<div>
	<div class="div_margin">
		<span>---已维护的阶梯----</span>
	</div>
										
	<td_offer></td_offer>
	
	<div class="add_button">
		<a data-toggle="tab" href="#tab_add">
			<button class="btn btn-xs btn-yellow" onclick="changeShow('add_dis_div2')">
			<i class="icon-hdd"></i>新增
		</button>
		</a>
	</div>	

<div id="add_dis_div2" class="moudle_dashed_border_width_90">
<form id="offer_disCount_form">	
			<div class="div_margin">
				<input type="hidden" name="discount" id="discount_ofer_no" value="">	
				<input type="hidden" name="discount_type" id="discount_type_ofer_no" value="">		
				<input type="hidden" name="low_price" id="discount_type_low_price" value="">	
				<label class="control-label blue">
					产品类型&nbsp;:
				</label>
	           <gt_ofer></gt_ofer>
			</div>
			
			<div class="div_margin">
				<label class="control-label blue">
					阶梯类型&nbsp;:
				</label>
				<select id=disCont2 class="selectpicker" name="interva_type_id" id="interva_type_id_ofer" style="width: 30%;" onchange="show_div2()">
					<option selected="selected" value="0"> 请选择</option>
					<option value="1">无阶梯</option>
					<option value="2">总费用阶梯</option>
					<option value="3">超出部分</option>
				</select>
			</div>
		<hr/>
				<div id="cont_dis_1" class="moudle_dashed_border_width" style="display: none;">
					<div class="div_margin">
						<div class="div_margin">
							比率:<input type="text" id="discount_arg_ofer_no" /><select><option>%</option></select><br/>
							 &nbsp;
							<div class="form-group">
										<span>最低一票:</span>
										 <input type="checkbox" id="low_prices" class="ace ace-switch ace-switch-5" /> 
											<span class="lbl"></span>
							<input type="text" id="discount_arg2_ofer_no" /><select><option>元</option></select>				
							</div> 
							
						</div>
					</div>
	<div class="div_margin">
		<a data-toggle="tab" href="#tab_add">
			<button class="btn btn-xs btn-yellow" id="offer_but" onclick="save_offer_data('offer_disCount_form','offer_but')">
			<i class="icon-hdd"></i>保存
		</button>
		</a>
	</div>					
				</div>		
</form>					
			       <div id="cont_dis_2" class="moudle_dashed_border_width_100">
<form id="discount_offer_2From">
											<span style="color: red">计算公式 : 保价费 = 订单金额 * 收费比率</span><br />
											<span style="color: red">公式说明：折扣为阶梯区间，需要在下方设置</span><br />
											<input type="hidden" name="section" id="section_ofer" value=""/>
											<input type="hidden" name="interva_type_id" id="interva_type_id_ofer">
											<input type="hidden" name="good_type" id="interva_good_type_ofer" value="">
											<input type="hidden" name="discount" id="discount_ofer" value="">
											<input type="hidden" name="discount_type" id="discount_type_ofer" value="">
											<div class="div_margin">
												<span>---阶梯设置---</span>
											</div>
											<div class="div_margin">

												条件 <select id="of_mark1">
													<option value=">">></option>
													<option value=">=">>=</option>
<!-- 													<option><</option> -->
<!-- 													<option><=</option> -->
												</select>
												<span id="text_arg1">  订单金额</span><input type="text" id="of_agrs1"/>
												       <select><option>元</option></select>
											</div>
											<div class="div_margin" >
												组合方式:<select id="of_mark2">
<!-- 												         <option value="0">或者</option> -->
													     <option value="1">并且</option>
											 		   </select>
											</div>
											<div class="div_margin" >
												条件 <select id="of_mark3">
<!-- 													<option>></option> -->
<!-- 													<option>>=</option> -->
													<option value="<"><</option>
													<option value="<="><=</option>
												</select>
												<span id="text_arg2">  订单金额</span><input type="text" id="of_agrs2"/>
												 <select><option>元</option></select>
											</div>
											<div class="div_margin">
												 <input type="radio"  name="discount_type_radio_ofer" value="1" />收费比率:<input type="text" id="discount_arg_ofer" />&nbsp;<select><option>%</option></select>
											     &nbsp;&nbsp;&nbsp;
											     <input type="radio"  name="discount_type_radio_ofer" value="2"/>&nbsp;收费:<input type="text" id="discount_arg2_ofer" />&nbsp;<select><option>元</option></select>
											</div>
	<div>
		<a data-toggle="tab" href="#tab_add">
			<button class="btn btn-xs btn-yellow" id="discount_but2_ofer" onclick="saveSection_ofer('discount_offer_2From','discount_but2_ofer')">
			<i class="icon-hdd"></i>保存
		</button>
		</a>
	</div>	
	</div>
	</form>	
	</div>		
		
</div>	

</div>		

   <div>
		<a data-toggle="tab" href="#tab_add">
			<button class="btn btn-xs btn-yellow" id="" onclick="changeSwitch()">
			<i class="icon-hdd"></i>保存
		</button>
		</a>
	</div>	
<script>
function save_offer_data(formId,catDiv1_but){
	   var url=root+"/control/transOrderController/saveDiscount.do";
	   var data="";
		if($("#id").val()==""){
			alert("请先维护合同主信息");
			return;
		}else{
			if($("#disCont2").val()=="1"){
				data=data+"&interval_type_name=无阶梯";
			    var dis_type_no=isChecked("low_prices");;
			    $("#discount_type_ofer_no").val(dis_type_no);
			    if(!check_dicType("1")){
			    	return;
			    }
			    if(dis_type_no){
				  if(!check_dicType("2")){
				    	return;
				 }	
			     $("#discount_type_low_price").val($("#discount_arg2_ofer_no").val());	
			    }else{
			    	 $("#discount_type_low_price").val("");
			    }
			    $("#discount_type_ofer_no").val("1");
			    $("#discount_ofer_no").val($("#discount_arg_ofer_no").val());    
				
				
			  }
			
			
			 if($("#disCont2").val()=="2"){
			  data=data+"&interval_type_name=总费用阶梯";
			  }
			  
			  
			if($("#disCont2").val()=="3"){
			 data=data+"&interval_type_name=超出部分";
			}	
			
			data=data+"&contract_id="+$("#id").val()+"&belong_to="+$("#contractOwner").val();
			data=data+"&if_baojia=1"
			data=data+"&cat_id=2"
			
			if(!checkInp(formId)){
				return;
			}
			data=$("#"+formId).serialize()+data;
			ajax_save(data,url,catDiv1_but);
		}
}


function check_dicType(type){
	if(type=="1"){
		 if(not_num_or_empty("discount_arg_ofer_no","收费率格式")){
				return false;
		 }	
		
		return true;
	}
	if(type=="2"){
		 if(not_num_or_empty("discount_arg2_ofer_no","最低一票")){
				return false;
		 }	
		return true;
	}
	
}
function saveSection_ofer(){
	    var result=getSection("of_mark1","of_mark2","of_mark3","of_agrs1","of_agrs2");
        if(!result){
 	     return;
        }
	   $("#section_ofer").val(result);
	   $("#interva_good_type_ofer").val($("#good_type_ofer").val());
	   $("#interva_type_id_ofer").val($("#disCont2").val());
	   var dis_type=$("input[name='discount_type_radio_ofer']:checked").val();
	   if(dis_type=="1"){
		 if(not_num_or_empty("discount_arg_ofer","收费比率")){
				return ;
		 }	
		$("#discount_ofer").val($("#discount_arg_ofer").val());
	   }
	   else if(dis_type=="2"){
		if(not_num_or_empty("discount_arg2_ofer","收费金额")){
			return false;
		 }	
		$("#discount_ofer").val($("#discount_arg2_ofer").val());
	   }else{
		  alert("必须选择一种收费方式！");
		  return;
	   }
	   $("#discount_type_ofer").val(dis_type);
	   save_offer_data('discount_offer_2From','discount_but2_ofer');
}


</script>