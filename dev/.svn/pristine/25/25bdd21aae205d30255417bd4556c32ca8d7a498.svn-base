<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <script>
$(document).ready(function(){ 
	if($("#contractOwner").val()!="" && $("#contractType").val()!='1'){
		getSpecial($("#id").val());	
		init_gs();
		}
});
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

								<div class="form-group">
									<div class="form-group">
										<span>派送费:</span>
										 <input type="checkbox" id="if_dispatch" class="ace ace-switch ace-switch-5" /> 
											<span class="lbl"></span>（需要在 始发地 目的地参数列表中 维护）
									</div>
								</div>
								<div class="form-group">
									<div class="form-group">
										<span>中转费:</span>
										 <input type="checkbox" id="if_transfer" class="ace ace-switch ace-switch-5" /> 
										 <span class="lbl"></span>（需要在 始发地 目的地参数列表中 维护）
									</div>
								</div>


								<div class="form-group">
									<div class="form-group" style="margin-right: 21%;">
										<span>卸货费:</span><input type="checkbox" id="if_unloading" class="ace ace-switch ace-switch-5" onchange="changeShow('cat2_01')" /> 
										<span class="lbl"></span>
									</div>
								</div>
<form id="unloading_from">
								<div class="moudle_dashed_border" id="cat2_01">
									<div class="div_margin">
										<div>
											计费公式 :<select id="choseCat" name="unloading_cat" onchange="changeunLoadCat()">
												<option value="">请选择计费方式</option>
												<option value="1">实际重量</option>
												<option value="2">计重价格计抛价格取大值</option>
											</select>
										</div>
									</div>

									<div class="form-group" style="display:none;" id="ts_cat1">
										<div>
											<span>公斤单价:<input id="unloading_price" type="text" value="" /></span> 
												<select id="unloading_unit">
												<option value="1">元/KG</option>
												<option value="2">元/吨</option>
											</select>
										</div>
									</div>
									<div class="form-group" style="display:none;" id="ts_cat2">
										<div>
											<span>公斤单价:<input id="unloading_price2" type="text" value="" /></span> 
												<select id="unloading_unit">
												<option value="1">元/KG</option>
												<option value="2">元/吨</option>
												</select>
										</div>
										<div>
											<span>立方单价:<input id="unloading_price3" type="text" value="" /></span> 
												<select>
												<option>元/m³</option>
											</select>
										</div>
									</div>									
								</div>
</form>

								<div class="form-group" style="margin-right: 21%;">
									<div class="form-group">
										<span>提货费:</span>
										 <input  type="checkbox" id="if_take" class="ace ace-switch ace-switch-5" onchange="changeShow('cat2_002')" />
										 <span class="lbl"></span>
									</div>
								</div>

								<div  class="moudle_dashed_border" id="cat2_002">
									<div class="div_margin" style="display: none;">
										<span>免提货条件:</span> 
										<input id="if_take_free" type="checkbox" class="ace ace-switch ace-switch-5" onchange="changeShow('freeModel_div')" /> 
										<span class="lbl"></span>
									</div>

									<div class="form-group" style="display: none;" id="freeModel_div">
										<div class="form-group" >
											<span>单次提货量(重量):</span>
											 <input id="if_take_free_by_weight" type="checkbox" class="ace ace-switch ace-switch-5" /> 
											 <span class="lbl"></span> 
											 <select id="if_take_free_by_weight_mark">
												<option value=">">></option>
												<option value=">=">>=</option>
												<option value="<"><</option>
												<option value="<="><=</option>
											</select> 
											<span>
											<input id="every_time_take_weight" name="every_time_take_weight" type="text" value="" />
											</span>
											 <select>
												<option>KG</option>
											</select>
										</div>

										<div class="form-group" style="display:;">
											<div class="form-group">
												<span>单次提货量(体积):</span> <input id="if_take_free_by_volume" type="checkbox" class="ace ace-switch ace-switch-5" /> 
												<span class="lbl"></span> 
												<select id="if_take_free_by_volume_mark">
													<option value=">">></option>
													<option value=">=">>=</option>
													<option value="<"><</option>
													<option value="<="><=</option>
												</select>
												 <span><input id="every_time_take_volume" name="every_time_take_volume" type="text" value="" /></span> 
												 <select>
													<option>m³</option>
												</select>
											</div>
										</div>
									</div>



									<div class="form-group" style="display:;">
										<div class="form-group">
											<span>按次收费:</span> 
											<input id="id-button-borders" type="radio" checked="checked" name="redio" value="1" class="ace ace-switch ace-switch-5" /> 
											<span class="lbl"></span> <span><input id="take_by_time_price" name="take_by_time_price" type="text" value="" /></span>
											 <select>
												<option>元/次</option>
											</select>
										</div>

										<div class="div_margin">
											<span>按单收费:</span> 
											<input id="id-button-borders" type="radio" checked="checked" name="redio" value="2" class="ace ace-switch ace-switch-5" /> 
											<span class="lbl"></span>
											 <span><input id="take_by_num_price"  name="take_by_num_price" type="text" value="" /></span>
											 <select>
												<option>元/单</option>
											</select>
										</div>
									</div>
								</div>
								<div class="form-group" style="margin-right: 21%;" onchange="changeShow('upFlooerDiv')">
									<div class="form-group">
										<span>上楼费:</span>
										<input id="if_upstairs" type="checkbox"  class="ace ace-switch ace-switch-5" /> 
										<span class="lbl"></span>
									</div>
									
								</div>

							<div  id="upFlooerDiv" class="moudle_dashed_border">
								  <div class="div_margin">
									   <span><input id="upstairs_price" name="upstairs_price" type="text" value="" /></span>
										 <select>
										 <option>元/件</option>
									     </select>
								  </div>
                                 <dtx></dtx>
									 
									<div class="add_button">
										<a data-toggle="tab" href="#tab_add">
											<button class="btn btn-xs btn-yellow" onclick="changeShow('zf_ad_td')">
											<i class="icon-hdd"></i>新增
										</button>
										</a>
									</div>		   
									
									
					          <div class="div_margin" style="display:none" id="zf_ad_td">
								<span>商品类型：<input id="goods_name" type="text"/></span>
<!-- 								<span>价格：<input id="zf_gt_price" type="text"/></span><select><option>元</option></select>		 -->
  									 <div class="div_margin">
										    <a data-toggle="tab" href="#tab_add">
											  <button class="btn btn-xs btn-yellow" id="save_catDiv3" onclick="save_gs()">
											 	<i class="icon-hdd"></i><span id="but_text_3">保存</span>
											  </button>
											  <button class="btn btn-xs btn-yellow" id="save_catDiv3" onclick="changeShow('zf_ad_td')">
											 	<i class="icon-hdd"></i><span id="but_text_3">取消</span>
											  </button>												  
										   </a>
								        </div>     
                                  </div>	 
  									 									 								  
								</div>



								<div class="form-group" style="margin-right: 21%;">
									<div class="form-group">
										<span>送货费:</span> 
										<input id="if_send" type="checkbox" class="ace ace-switch ace-switch-5" onchange="changeShow('freeReasonDiv')" />
									    <span class="lbl"></span>
									</div>
								</div>

								<div  class="moudle_dashed_border" id="freeReasonDiv">
									<div class="div_margin">
										<span>免送货条件:</span> 
										<input id="if_send_free" type="checkbox" class="ace ace-switch ace-switch-5" onchange="changeShow('freeReasonDetailDiv')" />
										 <span class="lbl"></span>
									</div>
									<div class="form-group" style="display: none;" id="freeReasonDetailDiv">
										<div class="form-group">
											<span>重量:</span> 
											<select id="send_free_mark">
												<option>></option>
												<option>>=</option>
												<option><</option>
												<option><=</option>
											</select>
											 <span><input id="send_free_weight" name="send_free_weight" type="text" value="" /></span> 
											 <select>
												<option>KG</option>
											</select>
										</div>
										
								 <div class="form-group">
											<span>（或者）体积:</span> 
											<select id="send_free_mark_vlum">
												<option>></option>
												<option>>=</option>
												<option><</option>
												<option><=</option>
											</select>
											 <span><input id="send_free_volum" name="send_free_volum" type="text" value="" /></span> 
											 <select>
												<option>m³</option>
											</select>
										</div>
										
																				
									</div>
									<div class="form-group">
										<span>收费:<input id="send_price" name="send_price" type="text" value="" /></span> 
										<select>
											<option>元/单</option>
										</select>
									</div>
								</div>

										<div class="div_margin">
											<a data-toggle="tab" href="#tab_add">
												<button class="btn btn-xs btn-yellow" onclick="save_special()">
													<i class="icon-hdd"></i>保存
												</button>
											</a>
										</div>


<input type="hidden" value="" id="special_id"/>
 
<script>
  function save_special(){
	  if($("#id").val()==""){
		  alert("请先维护合同主信息");
		  return;
	  }
	  var data="&contract_id="+$("#id").val()+"&id="+$("#special_id").val()+"&belong_to="+$("#contractOwner").val();
	  if(isChecked("if_dispatch")){
		  data=data+"&if_dispatch=1"
	  }else{
		  data=data+"&if_dispatch=0"
		  }
	  if(isChecked("if_transfer")){
		  data=data+"&if_transfer=1";
	  }else{
		  data=data+"&if_transfer=0";
		  }
	  if(isChecked("if_unloading")){
		  if($("#choseCat").val()==""){
			  alert("卸货费重必须选择一种计费公式");
			  return;
		  }
		  data=data+"&if_unloading=1";

		  if($("#choseCat").val()=="1"){
			  if(not_num_or_empty("unloading_price", "公斤单价")){
				  return;
			  }			  
		  data=data+"&unloading_cat="+$("#choseCat").val()+"&unloading_price="+$("#unloading_price").val()+"&unloading_unit="+$("#unloading_unit").val();
		 }
	     if($("#choseCat").val()=="2"){
		  if(not_num_or_empty("unloading_price2", "立方单价")){
				     return;
		   }		     
		   if(not_num_or_empty("unloading_price3", "立方单价")){
			     return;
		   }
	      data=data+"&unloading_cat="+$("#choseCat").val()+"&unloading_price="+$("#unloading_price2").val()+"&unloading_price2="+$("#unloading_price3").val()+"&unloading_unit="+$("#unloading_unit").val();
		 }
		  
	  }else{
		  data=data+"&if_unloading=0";
		  }
	  
	  if(isChecked("if_take")){
		  var take_type=$("input[name='redio']:checked").val();
		  data=data+"&if_take=1&take_type="+take_type;
		  if(take_type=="1"){
			  if(not_num_or_empty("take_by_time_price", "按次收费")){
				  return;
			  }
			  data=data+"&take_by_time_price="+$("#take_by_time_price").val();
		  }
		  if(take_type=="2"){
			  if(not_num_or_empty("take_by_num_price", "按单收费")){
				  return;
			  }
			  data=data+"&take_by_num_price="+$("#take_by_num_price").val();
		  } else{
			  data=data+"&if_take=0";
			  }
		  
 		  //免提货条件
		  if(isChecked("if_take_free")){
			  data=data+"&if_take_free=1";
			  //单次提货量(重量): 
			  if(isChecked("if_take_free_by_weight")){
				  data=data+"&if_take_free_by_volume=1";
 				  var every_time_take_weight=getSection("if_take_free_by_weight_mark", "", "", "every_time_take_weight","");
 				  if(!every_time_take_weight){
 					  return;
 				  }
 				  data=data+"&every_time_take_weight="+every_time_take_weight;
			  }else{
				  data=data+"&if_take_free_by_volume=0";
				  }	 
			  //单次提货量(体积): 
			  if(isChecked("if_take_free_by_volume")){
				 data=data+"&if_take_free_by_weight=1";
				 var every_time_take_volume=getSection("if_take_free_by_volume_mark", "", "", "every_time_take_volume", "");
				  if(!every_time_take_volume){
 					  return;
 				  }
				 data=data+"&every_time_take_volume="+every_time_take_volume;
			  }else{
				  data=data+"&if_take_free_by_weight=0";
				  }	 
			  
		  }	else{
			  data=data+"&if_take_free=0";
			  } 
	        }else{
	        	data=data+"&if_take=0";
		        }
	  if(isChecked("if_upstairs")){
		  data=data+"&if_upstairs=1";
		  if(not_num_or_empty("upstairs_price", "上楼费")){
			  return;
		  }
		  data=data+"&upstairs_price="+$("#upstairs_price").val();
	  }else{
		  data=data+"&if_upstairs=0";
		  }	  
	  
	  if(isChecked("if_send")){
		  data=data+"&if_send=1";
		  if(not_num_or_empty("send_price", "送货费")){
			  return;
		  }
		  data=data+"&send_price="+$("#send_price").val();
			  if(isChecked("if_send_free")){
				  if($("#send_free_weight").val()=="" && $("#send_free_volum").val()==""){
                         alert("免送货条件不能为空");
                         reurn;
					   }
				   if($("#send_free_weight").val()!=""){
					   var send_free_weight=getSection("send_free_mark", "", "", "send_free_weight","");
					   }
                    if($("#send_free_volum").val()!=""){
                    	 var send_free_volum=getSection("send_free_mark_vlum", "", "", "send_free_volum","");
                        }
  				    if(!send_free_weight){
 					  return;
 				    }                    
                    data=data+"&send_free_weight="+send_free_weight+"&if_send_free=1&send_free_volum="+send_free_volum;
                    
		  }
 	  }else{
 		 data=data+"&if_send=0";
 	 	  }	  
	  var url=root+"/control/transOrderController/saveSpecial.do";
	  ajax_save_special(data, url, "");
  }
  
  
  function ajax_save_special(data,url,butId){
	   $.ajax({
			type : "POST",
			url: url,  
			data:data,
			dataType: "json",  
			success : function(jsonStr) {
				alert(jsonStr.result_reason);
				 init_gs();
			}
		});
}


  function changeunLoadCat(){
			var divId = $("#choseCat").val();
			var divObj = $("div[id^='ts_cat']");
			for (i = 1; i <=divObj.length; i++) {
				if (i == divId) {
					$("#ts_cat" + i).css("display", "block");
				} else {
					$("#ts_cat" + i).css("display", "none");
				}
			}
	  }

  function save_gs(){
	  var data="&contract_id="+$("#id").val()+"&goods_name="+$("#goods_name").val()+"&belong_to="+$("#contractOwner").val();
	  var url=root+"/control/transOrderController/savegoods.do";
	  ajax_save_special(data, url, "");
	 
	  }

  function init_gs(){
	  var data="&contract_id="+$("#id").val()+"&belong_to="+$("#contractOwner").val();
	  var url=root+"/control/transOrderController/getgoods.do";
	  var htm_td="";
	   $.ajax({
			type : "POST",
			url: url,  
			data:data,
			dataType: "json",  
			success : function(jsonStr) {
					var text="<table class='with-border' border='1'>"
					       +"<tr class='title'>"
					       +"<td>序号</td><td>收费商品类型</td><td>操作</td>";
					for(i=0;i<jsonStr.length;i++){
						htm_td=htm_td+"<tr><td>"+(i+1)+"</td><td>"+jsonStr[i].goods_name+"</td><td><a href='javaScript:;' onclick='del_gs_tab(\""+jsonStr[i].id+"\")'>删除</a></td></tr>"
					}

			
				$('dtx').html(text+htm_td+"</table>");
			}
		
		});
		
	  }

  function del_gs_tab(id){
	  if(!confirm("是否删除以下所选数据?")){
		  	return;
		  	
		}
	  var url=root+"/control/transOrderController/del_gs_tab.do?id="+id;
	  $.ajax({
			type : "POST",
			url: url,  
			data:'',
			dataType: "json",  
			success : function(jsonStr) {
				alert(jsonStr.result_reason);
				init_gs();
			}
		});
	  }
</script>
