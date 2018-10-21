<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <script>
$(document).ready(function(){ 
	if($("#contractOwner").val()!="" && $("#contractType").val()=='2'){
		initData_glf($("#id").val());	
		}
});
</script>
<div class="form-group">
		<div class="form-group">
			<span>管理费:</span>
				<input id="if_manager" type="checkbox" class="ace ace-switch ace-switch-5" onchange="changeShow('tab6Div')" />
			<span class="lbl"></span>
		</div>
</div>

<div id="tab6Div" class="moudle_dashed_border">
	<div class="div_margin">
		<span>管理费:<input id="manager_price" name="manager_price" type="text" value="" /></span>
        <select>
		  <option>元/单</option>
	   </select>
   </div>
<div class="div_margin">
	<a data-toggle="tab" href="#tab_add">
			<button class="btn btn-xs btn-yellow" onclick="save_mager()">
             <i class="icon-hdd"></i>保存
		</button>
	</a>
</div>		

<input type="hidden" value="" id="mangerId"/>					 
</div>
   <div>
		<a data-toggle="tab" href="#tab_add">
			<button class="btn btn-xs btn-yellow" id="discount_but2_ofer" onclick="changeSwitch_glf()">
			<i class="icon-hdd"></i>保存
		</button>
		</a>
	</div>	
<script>
  function save_mager(){
	  if($("#id").val()==""){
		  alert("请先维护合同主信息");
		  return;
	  }
	  var data="";
	  var url=root+"/control/transOrderController/saveManager.do";
	  
	  if(isChecked("if_manager")){
		  if(not_num_or_empty("manager_price", "管理费")){
			  return
	    }
		  data="if_manager=1&manager_price="+$("#manager_price").val()+"&id="+$("#mangerId").val()+"&contract_id="+$("#id").val()+"&belong_to="+$("#contractOwner").val();
		  ajax_save_getId(data,url,"mangerId");
	  }

  }
  
  
  

  
</script>