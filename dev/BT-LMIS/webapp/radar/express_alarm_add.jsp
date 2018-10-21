<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>快递预警信息新增</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="<%=basePath %>daterangepicker/font-awesome.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
		<script type="text/javascript" src="<%=basePath %>daterangepicker/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
	</head>
	<body>
		<div class="page-header"><h3 style='margin-left:20px'>快递预警信息新增</h3></div>	
		<div style="margin-top: 20px;margin-left: 20px;margin-bottom: 20px;" class="clearfix form-actions">
           <form class="registerform" id="menuform" role="form" action="">
			<div style="width: 100%;" align="center">
				<table style="width: 55%;" class="form_table">			
					<tr>
						<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1"> 预警类别&nbsp;: </label></td>
						<td nowrap="nowrap" class="left">
							<select id="warning_category" name="warning_category"  class='selectpicker'>
							<option value= -1>---请选择---</option>
							<option  value="0">事件</option>
							<option  value="1">超时</option>
							</select>
						</td>
					</tr>
					<tr id='to_code_tr'>
					<td  name='to_code' nowrap="nowrap" class="right" ><label class="no-padding-right blue" for="form-field-1"> 超时预警时间计时模式： </label></td>
						<td  name='to_code' nowrap="nowrap" class="left" >
							<select id="timeout_code" name="timeout_code"  class='selectpicker'>
							<option value= -1>---请选择---</option>
							<option  value="0">快递交接</option>
							<option  value="1">复核</option>
							<option  value="2">称重</option>
							<option  value="3">揽件截止时间</option>
							</select>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1"> 预警类型代码&nbsp;: </label></td>
						<td nowrap="nowrap" class="left"><input id="warningtype_code" name="contact" type="text" id="form-field-1" placeholder="" value="${store.contact}" datatype="*1-10"  errormsg="信息至少1个字符,最多10个字符！"  style='width:160px;height:30px'/></td>
						<td nowrap="nowrap" class="left"></td>		
						</td>
						<td nowrap="nowrap" class="left"></td>
					</tr>
						<tr>
						<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1">预警类型&nbsp;: </label></td>
						<td nowrap="nowrap" class="left"><input id="warningtype_name" name="contact" type="text" id="form-field-1" placeholder="" value="${store.contact}" datatype="*1-10"  errormsg="信息至少1个字符,最多10个字符！"  style='width:160px;height:30px' /></td>
						<td nowrap="nowrap" class="left"></td>
					</tr>
					 <tr>
						<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1"> 初始级别&nbsp;: </label></td>
						<td nowrap="nowrap" class="left">
							<select id="initial_level" name="initial_level"  class='selectpicker'>
							<option value= -1>---请选择---</option>
							<option value= 1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1级</option>
						    <option value= 2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2级</option>
						    <option value= 3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3级</option>
						    <option value= 4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4级</option>
					     	<option value= 5>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5级</option>
					    	<option value= 6>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6级</option>
					    	<option value= 7>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7级</option>
					      	<option value= 8>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;8级</option>
					    	<option value= 9>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;9级</option>
					    	<option value= 10>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;10级</option>
							</select>
						</td>
						<td nowrap="nowrap" class="left"></td>
					</tr>
					<tr>
						<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1">备注&nbsp;: </label></td>
						<td nowrap="nowrap" class="left"><input id="remark" name="remark" type="text" id="form-field-1" placeholder="" value="${store.contact}" datatype="*1-10"  errormsg="信息至少1个字符,最多10个字符！"  style='width:160px;height:30px' /></td>
						<td nowrap="nowrap" class="left"></td>
					</tr>								
				</table>
				
							<div class="clearfix form-actions">
								
									<button class="btn btn-info" type="button" id="subins" name="subins">
										<i class="icon-ok bigger-110"></i> 提交
									</button>
								&nbsp; &nbsp; &nbsp;
								<button class="btn" type="reset" onclick="back()">
									<i class="icon-undo bigger-110"></i> 返回
								</button>
							</div>
				<!-- 			
					<div class="clearfix form-actions">
					<table class="table table-striped"><thead  align="center">
					<h3>预警状态维护列表</h3>
			  		   <tr>
			  			<td></td>
			  			<td>序号</td>
			  			<td>物流服务商</td>
			  			<td>状态代码</td>
			  			<td>状态描述</td>
			  			<td>操作</td>
			  		</tr>  	
			  		<tbody  align="center">
			  		<tr>
			  			<td><input id="ckb" name="route_id" type="checkbox" value="${route.id}"></td>
			  			<td>22}</td>
			  			<td>1231</td>
			  			<td>wfrf</td>
			  			<td>qr2rq</td>
			  			<td><button class="btn btn-xs btn-inverse" onclick="del('');">
				              <i class="icon-trash"></i>删除
			              </button></td>
			  		</tr>
		         	<tr>
			  			<td><input id="ckb" name="route_id" type="checkbox" value="${route.id}"></td>
			  			<td>22}</td>
			  			<td>1231</td>
			  			<td>wfrf</td>
			  			<td>qr2rq</td>
			  			<td><button class="btn btn-xs btn-inverse" onclick="del('');">
				      <i class="icon-trash"></i>删除
			              </button></td>
			  		</tr>
		  		   </tbody>	  			
		  		   </thead> 
		  		   </table>
		  		     <button class="btn btn-xs btn-yellow" onclick="openDiv('${root}/control/radar/routecodeController/addEdit.do');">
				         <i class="icon-hdd"></i>新增
			         </button> &nbsp;
			         <button class="btn btn-xs btn-inverse" onclick="del()">
				         <i class="icon-trash"></i>删除
			         </button>&nbsp;   
				    </div>	
		<div class="clearfix form-actions">
				 <h3>预警级别升级规则列表</h3>   
				<table class="table table-striped"><thead  align="center">
			  		<tr>
			  			<td></td>
			  			<td>序号</td>
			  			<td>物流服务商</td>
			  			<td>状态代码</td>
			  			<td>状态描述</td>
			  			<td>操作</td>
			  		</tr>  	
			  		<tbody  align="center">
			  		<tr>
			  			<td><input id="ckb" name="route_id" type="checkbox" value="${route.id}"></td>
			  			<td>22}</td>
			  			<td>1231</td>
			  			<td>wfrf</td>
			  			<td>qr2rq</td>
			  			<td>
			  			<button class="btn btn-xs btn-primary" onclick="up();">
 				          <i class="icon-edit"></i>修改 
			           </button>
			           <button class="btn btn-xs btn-inverse" onclick="del()">
				         <i class="icon-trash"></i>删除
			           </button>&nbsp; </td>
			  		</tr>
		         	<tr>
			  			<td><input id="ckb" name="route_id" type="checkbox" value="${route.id}"></td>
			  			<td>22}</td>
			  			<td>1231</td>
			  			<td>wfrf</td>
			  			<td>qr2rq</td>
			  			<td>
			  			<button class="btn btn-xs btn-primary" onclick="up();"> 
 				          <i class="icon-edit"></i>修改
			           </button>
			           <button class="btn btn-xs btn-inverse" onclick="del()">
				         <i class="icon-trash"></i>删除
			           </button>&nbsp; </td>
			  			</td>
			  		</tr>
		  		   </tbody>	  			
		  		   </thead> 
		  		   </table>
		  		     <button class="btn btn-xs btn-yellow" onclick="openDiv('${root}/control/radar/routecodeController/addEdit.do');">
				         <i class="icon-hdd"></i>新增
			         </button> &nbsp;
			         <button class="btn btn-xs btn-inverse" onclick="del()">
				         <i class="icon-trash"></i>删除
			         </button>&nbsp;  -->
				    </div>	 			
			      </div>
			</from>
		</div>	
	</body>
</html>
<script>

  function find(){
		var warning_category =  $("#warning_category").find("option:selected").val();
		var warningtype_code = $("#warningtype_code").val();
		var warningtype_name = $("#warningtype_name").val();
		var remark = $('#remark').val();
		var initial_level =  $("#initial_level").find("option:selected").val();

		
  }
  function back(){
	  
	  openDiv('${root}/controller/express_alarm/query.do');

	  
  }
  
  $(function(){
	 $('#to_code_tr').hide(); 
	  $("#warning_category").bind('change',function(){
		if(this.value==1){
			$('#to_code_tr').show();  
		}else{
			$('#to_code_tr').hide(); 	
		}	
	  });
	  
	  $("#subins").bind('click',function(){
		    var warning_category =  $("#warning_category").find("option:selected").val();
			var warningtype_code = $("#warningtype_code").val();;
			var warningtype_name = $('#warningtype_name').val();
			var initial_level =  $("#initial_level").find("option:selected").val();
		    var timeout_code=$("#timeout_code").find("option:selected").val();
			var remark = $('#remark').val();
			
			
			if(warning_category==-1||warning_category==''){alert('预警类别不能为空！');return;}
			if(warning_category==1){
              if(timeout_code==-1||timeout_code==''){
            	  alert('超时预警时间计时模式  不能为空');return;
              }
			
			}
			if(warningtype_code==''||warningtype_code==-1){alert('预警类型代码信息不能为空！');return;}
			if(warningtype_name==''||warningtype_code==-1){alert('预警类型不能为空！');return;}
			if(remark.length>99){alert('备注信息不能超过99个字符！');return;}
			if(initial_level==''||initial_level==-1){alert('初始级别信息不能为空！');return;}
		 $.post('${root}//controller/express_alarm/addSubmit.do',	
				 { warning_category: warning_category,
			        warningtype_code:warningtype_code,
			        warningtype_name:warningtype_name,
			        remark:remark,
			        initial_level:initial_level,
			        timeout_mode:timeout_code
				 },
				 function(data){
					 if(data.toString()=='[object XMLDocument]'){
						  window.location='/BT-LMIS';
							return;
					  };
			       if(data.code==1){
				      alert('操作成功！');
				      openDiv('${root}/controller/express_alarm/query.do');
			                      }else{
				      alert('操作失败！'); 
				      alert(data.msg);
			          }
		                     });
	                     })
              })
</script>
<style>
select {
    padding: 3px 4px;
    height: 30px;
    width: 160px;
   text-align: enter;
}
</style>
