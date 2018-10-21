<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>快递预警信息修改</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
		<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
	</head>
	<body>
		<div class="page-header"><h1 style='margin-left:20px'>快递预警信息修改</h1></div>	
		<div style="margin-top: 20px;margin-left: 20px;margin-bottom: 20px;" class="clearfix form-actions">
			<div style="width: 100%;" align="center">
				<table style="width: 60%;" class="form_table">			
					<tr>
					<td nowrap="nowrap" ><label class="no-padding-right blue" for="form-field-1"> 预警类别&nbsp;: </label></td>
						<td width="10%">
							<select id="warning_category" name="warning_category"  class='selectpicker' disabled>
							<option value= -1>---请选择---</option>
							<option  value="0">事件</option>
							<option  value="1">超时</option>
							</select>
						</td>
						<td name='to_mode' nowrap="nowrap"><label class="no-padding-right blue" for="form-field-1"> 超时预警时间计时模式 &nbsp;: </label></td>
						<td  name='to_mode' width="20%">
							<select id="timeout_mode" name="timeout_mode"  class='selectpicker'  disabled>
							<option value= -1>---请选择---</option>
							<option  value="0">快递交接</option>
							<option  value="1">复核</option>
							<option  value="2">称重</option>
							<option  value="3">揽件截止时间</option>
							</select>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap" ><label class="no-padding-right blue" for="form-field-1"> 预警类型代码&nbsp;: </label></td>
						<td nowrap="nowrap" class="left"><input id="warningtype_code" name="contact" type="text" id="form-field-1" placeholder=""   readonly style='width:160px;height:30px'/></td>
						<td nowrap="nowrap" class="left"></td>		
						</td>
						<td nowrap="nowrap" class="left"></td>
					</tr>
						<tr>
						<td nowrap="nowrap" ><label class="no-padding-right blue" for="form-field-1">预警类型&nbsp;: </label></td>
						<td nowrap="nowrap" class="left"><input id="warningtype_name" name="contact" type="text" id="form-field-1" placeholder=""   readonly style='width:160px;height:30px'/></td>
						<td nowrap="nowrap" class="left"></td>
					</tr>
					<tr>
						<td nowrap="nowrap" ><label class="no-padding-right blue" for="form-field-1"> 初始级别&nbsp;: </label></td>
						<td nowrap="nowrap" class="left">
							<select id="initial_level" name="initial_level"  class='selectpicker' disabled>
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
						<td nowrap="nowrap" ><label class="no-padding-right blue" for="form-field-1">备注&nbsp;: </label></td>
						<td nowrap="nowrap" class="left"><input id="remark" name="remark" type="text" id="form-field-1" placeholder="" value="${store.contact}" readonly  style='width:160px;height:30px'/></td>
						<td nowrap="nowrap" class="left"></td>
					</tr>								
				</table>
					<div class="clearfix form-actions" style='height:150px'>		
									 <button class="btn btn-info" type="button" id="bianji" onclick='edit()'>
										<i class="icon-ok bigger-110"></i> 编辑
									</button>
								&nbsp; &nbsp; &nbsp;
								<button class="btn" type="reset" onclick="back()">
									<i class="icon-undo bigger-110"></i> 返回
								</button> 
							</div>			
				<div class="clearfix form-actions">
					<table class="table table-striped"><thead  align="center">
				<div class="page-header"><h1 style='margin-left:20px'>预警状态维护列表</h1></div>	
			  		   <tr>
			  			<td>序号</td>
			  			<td>物流服务商</td>
			  			<td>状态代码</td>
			  			<td>状态描述</td>
			  			<td>操作</td>
			  		</tr>  	
			  		<tbody  align="center">
			  		<c:forEach items="${routes}" var="records" varStatus='status'>
			  		<tr>
			  			<!-- <td><input id="ckb" name="" type="checkbox" value="${records.id_code}"></td> -->
			  			<td>${status.index+1}</td>
			  			<td>${records.transport_name}</td>
			  			<td>${records.status_code}</td>
			  			<td>${records.status}</td>
			  			<td><button class="btn btn-xs btn-inverse" onclick="delItem('${records.id_code}');">
				              <i class="icon-trash"></i>删除
			              </button></td>
			  		</tr></c:forEach>
		  		   </tbody>	  			
		  		   </thead> 
		  		   </table>
		  		     <button class="btn btn-xs btn-yellow" data-toggle="modal" data-target="#myModal" >
				         <i class="icon-hdd"></i>新增
			         </button> &nbsp;
			     <!--     <button class="btn btn-xs btn-inverse" onclick="del()">
				         <i class="icon-trash"></i>删除
			         </button>&nbsp;    -->
				    </div>	
		<div class="clearfix form-actions">
	<div class="page-header"><h1 style='margin-left:20px'>预警级别升级规则列表</h1></div>		 
				<table class="table table-striped"><thead  align="center">
			  		<tr>
			  			<td>序号</td>
			  			<td>花费时间（min）</td>
			  			<td>到达级别</td>
			  			<td>操作</td>
			  		</tr>  	
			  		<tbody  align="center">
			  			<c:forEach items='${wll}' varStatus='status' var ='wllRecord'>
		              <c:if  test='${status.count eq 1}'>
			  			<tr>
			  			<!-- <td><input id="ckb" name="route_id" type="checkbox" value="${route.id}"></td> -->
			  			<td>${status.count}</td>
			  			<td tid='time'>${wllRecord.levelup_time}</td>
			  			<td tid='level'>${wllRecord.levelup_level}</td>
			  			<td>
			  			</td>
			  		</tr>
			     </c:if>
			      <c:if  test='${status.count ne 1}'>
			  			<tr>
			  			<!-- <td><input id="ckb" name="route_id" type="checkbox" value="${route.id}"></td> -->
			  			<td>${status.count}</td>
			  			<td tid='time'>${wllRecord.levelup_time}</td>
			  			<td tid='level'>${wllRecord.levelup_level}</td>
			  			<td>
			  		<button class="btn btn-xs btn-primary" onclick="upwll(this,'${wllRecord.id}');">
 				          <i class="icon-edit"></i>修改 
			           </button>
			           <button class="btn btn-xs btn-inverse" onclick="delwll('${wllRecord.id}')">
				         <i class="icon-trash"></i>删除
			           </button>&nbsp; </td>
			  		</tr>
			     </c:if>
			     
			  		</c:forEach>
	                
		  		   </tbody>	  			
		  		   </thead> 
		  		   </table>
		  		     <button class="btn btn-xs btn-yellow" data-toggle="modal" data-target="#yourModal">
				         <i class="icon-hdd"></i>新增
			         </button> &nbsp;
			         <!-- <button class="btn btn-xs btn-inverse" onclick="del()">
				         <i class="icon-trash"></i>删除
			         </button>&nbsp; -->  
				    </div>	 			
			      </div>
			</from>
		</div>	
		
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content" style='border:3px solid #394557'>
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">预警状态添加</h4>
			</div>
			<div class="modal-body">
			    <label class="no-padding-right blue" for="form-field-1">物流服务商&nbsp;: </label>
				<select id="transport_venders" name="transport_venders"  class='selectpicker'>
							<option value= -1>---请选择---</option>
							<c:forEach items="${transport_venders}" var="route"  varStatus="status">
							<option  value="${route.transport_code}">${route.transport_name}</option></c:forEach>
							</select><br/><br/>
				<label class="no-padding-right blue" for="form-field-1">路由状态代码&nbsp;: </label>
				<select id="route_status_code" name="route_status_code"  class='selectpicker'>
				       <option value=-1>请先选择物流服务商</option>
						</select><br/><br/>			
					<label class="no-padding-right blue" for="form-field-1">路由状态&nbsp;: </label>
					<input id="route_status" name="route_status" type="text" id="form-field-1" placeholder="" value="${store.contact}" readonly/>
					<input style='display:none' value='' />
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default"  data-dismiss="modal">关闭
				</button>
				<button type="button" class="btn btn-primary" data-dismiss="modal" onclick='warningAdd()'>
					确认添加
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
<div class="modal fade" id="yourModal" tabindex="-1" role="dialog" aria-labelledby="yourModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content" style='border:3px solid #394557'>
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					新增预警 级别规则
				</h4>
			</div>
			<div class="modal-body">
				 <label class="no-padding-right blue" for="form-field-1">预警级别&nbsp;: </label>
				<select id="wll_level" class='selectpicker'>
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
		<label class="no-padding-right blue" for="form-field-1">预警时间&nbsp;: </label>
					<input id="wll_time"  type="text" id="form-field-1" placeholder="" value="${store.contact}" />
		
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="button" class="btn btn-primary"  data-dismiss="modal" onclick='addwll()'>
					提交更改
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
		
	</body>
</html>
<script>
var warning_categoryf =  '${queryParam.warning_category}';
var warningtype_codef =  '${queryParam.warningtype_code}';
var warningtype_namef = '${queryParam.warningtype_name}';
var timeout_mode= '${queryParam.timeout_mode}';
var remarkf = '${queryParam.remark}';
if(warning_categoryf==1){
	$('#timeout_mode').val(timeout_mode);
}else{
	$.each($("td[name='to_mode']"),function(index,item){
		this.style.display='none';
		
	})
	
}
var initial_levelf =  '${queryParam.initial_level}';
$('#warning_category').val(warning_categoryf);
$('#warningtype_code').val(warningtype_codef);
$('#warningtype_name').val(warningtype_namef);
$('#remark').val(remarkf);
$('#initial_level').val(initial_levelf);


  var  Gmap=new Object();
  var  tt=$('td[tid=time]');
  var  ttArra=new Array();
  var  tl=$('td[tid=level]');
  var gtlArra=new Array(); 
  var gttArra=new Array(); 
  var  tlArra=new Array();   var s='';
  $.each(tl,function(index,item){
  	tlArra[index]=item.innerHTML;
  	gtlArra[index]=item.innerHTML;
  	s=item.innerHTML;
  });
  var  adtl=tlArra;
  $.each(tt,function(index,item){
  	ttArra[index]=item.innerHTML;
  	gttArra[index]=item.innerHTML;
  });
  

  var  adtt=ttArra;
  var indexno=''
 function edit(){
	 //$("#warning_category").attr("disabled",false); 
	// $("#initial_level").attr("disabled",false);
	 //$("#warningtype_code").removeAttr("readonly");
	 //$("#warningtype_name").removeAttr("readonly");
	 $("#remark").removeAttr("readonly");
	 $("#bianji").html('<i class="icon-ok bigger-110"></i>提交');
	 $("#bianji").bind('click',function(){
		  var warning_category =  $("#warning_category").find("option:selected").val();
			var warningtype_code = $("#warningtype_code").val();;
			var warningtype_name = $('#warningtype_name').val();
			var initial_level =  $("#initial_level").find("option:selected").val();
			var remark = $('#remark').val();
			if(warning_category==-1){alert('预警类别不能为空！');return;}
			if(warningtype_code==''){alert('预警类型代码信息不能为空！');return;}
			if(warningtype_name==''){alert('预警类型不能为空！');return;}
			if(remark.length>99){alert('备注信息不能超过99个字符！');return;}
			var adFlag=false;
			 $.each(tlArra,function(index,item){
				if(initial_level>=item){
					if(adFlag)return;
					adFlag=true;
					alert("您的初始级别修改后低于已有的级别 请先删除低级别在修改");
					return;
				} 
				  });
			if(adFlag)return;
		 $.post('${root}/controller/express_alarm/updateSubmit.do',	
				 { warning_category: warning_category,
			        warningtype_code:warningtype_code,
			        warningtype_name:warningtype_name,
			        remark:remark,
			        initial_level:initial_level,
			        id:'${queryParam.id}'
				 },
				 function(data){
					 if(data.toString()=='[object XMLDocument]'){
						  window.location='/BT-LMIS';
							return;
					  };
			       if(data.code==1){
				      alert('操作成功！');
				      openDiv('${root}/controller/express_alarm/toUpdate.do?id=${queryParam.id}');
			                      }else{
				      alert('操作失败！'); 
			          }
		                     });
	                    })
 }	
 

  function back(){	  
	  openDiv('${root}/controller/express_alarm/query.do');
  }
  
  $(function(){  
	     $('#transport_venders').bind('change',function(){
	      //获得路由状态代码	
	      $('#route_status').val('');
	      if(this.value==-1)return;
	      $.post('${root}controller/express_alarm/getStatus_code.do?transport_code='+this.value,
	         function(data){
	    	  if(data.toString()=='[object XMLDocument]'){
				  window.location='/BT-LMIS';
					return;
			  };
	    	  var  record=data.records;
	    	  Gmap=new Object();
	    	  var  strHtml='<option value=-1>---请选择---</option>';
	    	  $.each(record,function(index,item){
	    		 if(timeout_mode==4&&warning_categoryf==1&&item.flag==-1){return true;}
	    			 Gmap[item.status_code]=item.status;
		    		  strHtml=strHtml+'<option  value='+item.status_code+'>'+item.status_code+'</option>'; 
	    	  });
	    	  $('#route_status_code').html(strHtml);
	      })
	     })                             
              })
              $('#route_status_code').bind('change',function(){
            	  $('#route_status').val('');
            	  $('#route_status').val(Gmap[this.value]);
              })
  
  function warningAdd(){
	 var w_transport_code= $('#transport_venders').find("option:selected").val();
	 var w_route_status_code= $('#route_status_code').find("option:selected").val();//${queryParam.warningtype_code}
	 if(w_transport_code==-1||w_transport_code==''){alert('请选择物流服务商');return;}
	 if(w_route_status_code==-1||w_route_status_code==''){alert('请选择路由状态');return;}
	 $.post('${root}controller/express_alarm/addWarning_routestatus_list.do',
			 {      transport_code:w_transport_code,
			        warningtype_code:'${queryParam.warningtype_code}',
			         routestatus_code:w_route_status_code
				 },
			 function(data){
					 if(data.toString()=='[object XMLDocument]'){
						  window.location='/BT-LMIS';
							return;
					  };	
		   if(data.code==1){
			   alert("添加成功！！");
		   		openDiv('${root}/controller/express_alarm/toUpdate.do?id=${queryParam.id}');
				 }else if(data.code==2){
					 alert("此条记录已经存在，不能重复添加！！");	
				 }
		     $(".modal-backdrop").remove();
			 $("body").removeClass('modal-open');
		   }
		 
	 ); 
  }
    function  delItem(id){
   	 var result=  	confirm('确定要删除以下数据？'); 
		if(result){
    	$.post('${root}/controller/express_alarm/delWRList.do?id='+id,function(data){
    		if(data.toString()=='[object XMLDocument]'){
				  window.location='/BT-LMIS';
					return;
			  };
    		if(data.code==1){
  			   alert("删除成功！！");
  			 openDiv('${root}/controller/express_alarm/toUpdate.do?id=${queryParam.id}');
  				 }
    	});
		}
    }
    function delwll(id){
    	 var result=  	confirm('确定要删除以下数据？'); 
 		if(result){
    	$.post('${root}/controller/express_alarm/delwll.do?id='+id,function(data){
    		if(data.toString()=='[object XMLDocument]'){
				  window.location='/BT-LMIS';
					return;
			  };
    		if(data.code==1){
    			   alert("删除成功！！");
    			   openDiv('${root}/controller/express_alarm/toUpdate.do?id=${queryParam.id}');
    				 }	
    	});}
    }

    function upwll(e,id){
    	var par=e.parentNode;
    	var parpar=par.parentNode;
    	var child=parpar.getElementsByTagName("td");
    	$.each(child,function(index,item){
    		if(index==2){
    			indexno=tlArra.indexOf(item.innerHTML);
    			item.innerHTML='<input style="width:60px" id="level" type="text" value='+item.innerHTML+'></input>';}
    		if(index==1){
    			item.innerHTML='<input style="width:60px"  id="level_time" type="text" value='+item.innerHTML+'></input>';}
    	})
    e.outerHTML=
    	'<button class="btn btn-xs btn-pink" onclick="subwll(this,\''+id+'\')"><i class="icon-edit"></i>提交修改 </button>';
    	
    }
    function subwll(e,id){
        var  returnFlag=false;
    	var par=e.parentNode;
    	var parpar=par.parentNode;
    	var child=parpar.getElementsByTagName("td");
    	var level=($('#level').val());
    	var level_time=($('#level_time').val());
    	if(isNaN(Number(level))){
    		alert('请填写数字');
    		return;
    	}
    	if(isNaN(Number(level_time))){
    		alert('请填写数字');
    		return;
    	}
     	 if(level<='${queryParam.initial_level}'){
    		alert('不能小于等于初始预警级别！');
    		return;
    	}  
    	/*  $.each(gtlArra,function(index,item){
    		 if(item==level){
    			 returnFlag=true;
    			 alert("修改的级别已经存在");
    			 return;
    		 };
    		  });
    	 $.each(gttArra,function(index,item){
    		 ttArra[index]=item;
    		 if(item==level_time){
    			 returnFlag=true;
    			 alert("修改的花费时间已经存在");
    			 return;
    		 };	 
    	 }); */
    	/* if(returnFlag){
    		return;
    	} */
    	
    	/* tlArra[indexno]=level;
    	ttArra[indexno]=level_time;
    	
    	tlArra = tlArra.sort(function (a, b) {  
            return b - a;  
        }); 
    	ttArra = ttArra.sort(function (a, b) {  
            return b - a;  
        });  
         if(tlArra.indexOf(level)!=ttArra.indexOf(level_time)){
        	 alert('不符合修改规则');
        	 $.each(gtlArra,function(index,item){
        		 tlArra[index]=item;
        		  });
        	 $.each(gttArra,function(index,item){
        		 ttArra[index]=item;
        		  });
        	 return;
         } */
     $.post('${root}/controller/express_alarm/upwll.do',
    			{id:id,
    		     levelup_level:level,
    		     levelup_time:level_time		
    		}
    			,function(data){
    				if(data.toString()=='[object XMLDocument]'){
						  window.location='/BT-LMIS';
							return;
					  };
    				 if(data.code==1){
    	    			   alert("修改成功！！");
    	    				 }else if(data.code==0){
    	    					 alert("操作失败！！");	 
    	    				 }
    				 openDiv('${root}/controller/express_alarm/toUpdate.do?id=${queryParam.id}');
    	})	 
    }
    
    
    function addwll(){
    	if($('#wll_level').val()<='${queryParam.initial_level}'){
    		alert('不能小于等于初始预警级别！');
    		return;
    	}
    	if(isNaN(Number($('#wll_time').val()))){
    		alert('请在预警时间一栏填写数字');
    		return;
    	}
    	var returnFlag=false;
    	$.each(gtlArra,function(index,item){
   		 if(item===$('#wll_level').val()){
   			 returnFlag=true;
   			 alert("新增的级别已经存在");
   			 return;
   		 };
   		  });
   	 $.each(gttArra,function(index,item){
   		 ttArra[index]=item;
   		 if(item==$('#wll_time').val()){
   			 returnFlag=true;
   			 alert("新增的花费时间已经存在");
   			 return;
   		 };	 
   	 
   	 });
    	if(returnFlag)return;
    	
    	adtt[adtt.length]=$('#wll_time').val();
    	adtl[adtl.length]=$('#wll_level').val();
    	adtl = adtl.sort(function (a, b) {  
            return b - a;  
        }); 
    	adtt = adtt.sort(function (a, b) {  
            return b - a;  
        }); 
    	var wll_level='';
    		wll_level=$('#wll_level').val();
    	if(adtl.indexOf($('#wll_level').val())!=adtt.indexOf($('#wll_time').val())){
    		$.each(gtlArra,function(index,item){
    			adtl[index]=item;
       		  });
       	    $.each(gttArra,function(index,item){
       	    	adtt[index]=item;
       		  });
    		alert('不符合修改规则');
       	 return;
        }
 	 $.post('${root}/controller/express_alarm/addwll.do',
    			{levelup_level:$('#wll_level').val(),
    		     levelup_time:$('#wll_time').val(),
    		     warningtype_code:'${queryParam.warningtype_code}'
    	},function(data){
    		if(data.toString()=='[object XMLDocument]'){
				  window.location='/BT-LMIS';
					return;
			  };
 		   if(data.code==1){
 			         alert("添加成功！！");
 		   		openDiv('${root}/controller/express_alarm/toUpdate.do?id=${queryParam.id}');
 				 }else if(data.code==2){
 					 alert("此条记录已经存在，不能重复添加！！");	
 				 }
 		     $(".modal-backdrop").remove();
 			 $("body").removeClass('modal-open');	
    	})	  
    }
   
</script>
<style>

select {
    padding: 3px 4px;
    height: 30px;
    width: 160px;
   text-align: enter;
}
</style>