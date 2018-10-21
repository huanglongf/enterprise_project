<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link type="text/css" href="<%=basePath %>css/table.css" rel="stylesheet" />
		<script type="text/javascript" src="<%=basePath %>validator/js/jquery-1.9.1.min.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script>
		<script type="text/javascript" src="<%=basePath %>js/common_view.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<script type="text/javascript" src="<%=basePath %>/work_order/work_type.js"></script>
		<link type= "text/css" href= "<%=basePath %>css/table.css" rel= "stylesheet" />	
		<script type="text/javascript">
			$(document).ready(function(){ 
				getTypeInfo($("#mainCode").val());
				getReasonInfo($("#mainCode").val());
				getErrorInfo($("#mainCode").val());
			});
		</script>
	</head>
	<body>
			<div class= "page-header" align= "left" >
			<h1>工单基础信息编辑</h1>
		</div>
			<table>
		  		<tr>
		  			<td align= "center" width= "10%">
		  			<label class= "blue" for= "client_code">工单类型&nbsp;:</label>
		  			</td>
		  			<td width= "73%">
                    <input type="text" id="typeKid" value="${obj.wk_type}" <%-- <c:if test="${obj.wk_type!=null}">disabled="disabled" style="background-color:#d9d2e9;"</c:if> --%>>
		  			&nbsp;
		  			              <input type="checkbox" id="if_share"  <c:if test="${obj.if_share=='1' }">checked="checked"</c:if>  
		  			              name="if_share" value="${obj.if_share}"/>是否店铺共享
		  			&nbsp;
		  			              <input type="checkbox" id="so_flag"  <c:if test="${obj.so_flag=='是' }">checked="checked"</c:if>  
		  			              name="so_flag" value="${obj.so_flag}"/>是否提交至销售运营
		  			</td>
		  			<td width="17%;"></td>
		  		</tr>
		  		
		  		<tr>
		  			<td align= "center" width= "10%"><label class= "blue" for= "client_name">启用状态&nbsp;:</label></td>
		  			<td width= "40%">
		  			 <select id="status" style="width:60%;">   
		  			    <option value="1" <c:if test="${obj.status==1}">selected='selected'</c:if>>启用</option>
		  			    <option value="0" <c:if test="${obj.status==0}">selected='selected'</c:if> >禁用</option>
		  			 </select>
		  			</td>		  	
		  			<td width="50%;"></td>	
		  		</tr>
		  		
		  		<tr>
		  		  <td align="right" width="10%"><label class= "blue" for= "client_name">备注&nbsp;:</label></td>
		  		  <td colspan="3"><textarea id="remark" style="width:60%;height:70px;resize: none;">${obj.remark}</textarea></td>
		  		</tr>
			</table>
		<div style="width: 100%;margin-top: 20px;margin-left: 10%;">
		<button class= "btn btn-xs btn-yellow" onclick= "Add()" >
		<i class= "icon-hdd" ></i>提交
	    </button>&nbsp;
	 	<button class= "btn btn-xs btn-yellow" onclick= "backDress()" >
		<i class= "icon-hdd" ></i>返回
	    </button>&nbsp;&nbsp;&nbsp;&nbsp;

	    </div>
<div class= "page-header"></div>		
		<input type="hidden" id="mainId" value="${obj.id}">
		<input type="hidden" id="mainCode" value="${obj.code}">
		<input type="hidden" id="upTypeId" value="">
		<input type="hidden" id="upReasonId" value="">
		<input type="hidden" id="upErrorId" value="">
<div>
  <div>
    
   <div style="width: 30%;margin-left:1%;">
	<h3 class="header blue lighter smaller">
		<i class="icon-list-alt smaller-90"></i>
		工单级别维护
				<a class="btn btn-link" href="javascript:;" onclick= "toAddType()" >
		<i class="icon-plus-sign bigger-120 green"></i>
			添加级别
	</a>
	</h3>
   </div>
   
   
    <div style="margin-left: 10px;">
            <workType></workType>
    </div>
    
    <div class= "page-header"></div>	
  </div>
    
    
    <div style="width: 30%;margin-left:1%;">
	<h3 class="header blue lighter smaller">
		<i class="icon-list-alt smaller-90"></i>
		异常类型
				<a class="btn btn-link" href="javascript:;" onclick= "toAddError()" >
		<i class="icon-plus-sign bigger-120 green"></i>
			添加类型
	</a>
	</h3>
   </div>
   
   <div style="margin-left: 10px; margin-bottom: 100px;">
      <errorType></errorType>
    </div>
  
 
 
  <div style="width: 30%;margin-left:1%;">
	<h3 class="header blue lighter smaller">
		<i class="icon-list-alt smaller-90"></i>
		升降级原因
				<a class="btn btn-link" href="javascript:;" onclick= "toAddReason()" >
		<i class="icon-plus-sign bigger-120 green"></i>
			添加原因
	</a>
	</h3>
   </div>
   
   <div style="margin-left: 10px; margin-bottom: 100px;">
      <workReason></workReason>
    </div>
  
  <div>
  </div>
</div>

<!-- 店铺编辑页面弹窗 -->
		<div id= "type_form" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
			<div class= "modal-dialog modal-lg" role= "document" >
				<div class= "modal-content" style= "border: 3px solid #394557;" >
					<div class= "modal-header" >
						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
						<h4 id= "formLabel" class= "modal-title" >新增级别</h4>
					</div>
					<div class= "modal-body" >
						<input id= "store_id" name= "store_id" type= "hidden" />
						<div class= "form-group" >
				    		<label class= "blue" for= "client_form" >工单级别&nbsp;:</label></br>
				    	<select style="width: 100%" id="level" data-edit-select="1">
		  			      <option>请选择</option>
		  			      <level></level>
		  			   </select>
				    	</div>
						<div class= "form-group" >
				    		<label class= "blue" for= "standard_time_form" >标准工时(min)&nbsp;:</label>
				    		<input id= "standard_time_form" name= "standard_time_form" class= "form-control" type= "text"/>
				    	</div>
						<div class= "form-group" >
				    		<label class= "blue" for= "plan_time_form" >计划完成工时(min)&nbsp;:</label>
				    		<input id= "plan_time_form" name= "plan_time_form" class= "form-control" type= "text"/>
				    	</div>
						<div class= "form-group" >
				    		<label class= "blue" for= "timeout_time_form" >超时升级工时&nbsp;:</label>
				    		<input id= "timeout_time_form" name= "timeout_time_form" class= "form-control" type= "text" />
				    	</div>
					</div>
					<div class= "modal-footer" >
						<button id= "btn_back" type= "button" class= "btn btn-default" data-dismiss="modal" >
							<i class= "icon-undo" aria-hidden= "true" ></i>返回
						</button>
		     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="saveType();" >
		     				<i class="icon-save" aria-hidden= "true" ></i><span id="btn_type_text">保存</span>
		     			</button>
					</div>
				</div>
			</div>
		</div>
		
		
<!-- 异常类型编辑页面弹窗 -->
		<div id= "error_form" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
			<div class= "modal-dialog modal-lg" role= "document" >
				<div class= "modal-content" style= "border: 3px solid #394557;" >
					<div class= "modal-header" >
						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
						<h4 id= "formLabel" class= "modal-title" >新增类型</h4>
					</div>
					<div class= "modal-body" >
						<div class= "form-group" >
				    		<label class= "blue" for= "reason_form" >异常类型&nbsp;:</label>
				    		<input id= "error_type" name= "error_type" class= "form-control" type= "text"/>
				    	</div>
						<div class= "form-group" style="display: none;">
				    		<label class= "blue" for= "err_form" >说明&nbsp;:</label>
				    		<input id= "err_form" name= "err_form" class= "form-control" type= "text" />
				    	</div>
					</div>
					<div class= "modal-footer" >
						<button id= "btn_back2" type= "button" class= "btn btn-default" data-dismiss="modal" >
							<i class= "icon-undo" aria-hidden= "true" ></i>返回
						</button>
		     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="saveError();">
		     				<i class="icon-save" aria-hidden= "true" ></i></i><span id="btn_error_text">保存</span>
		     			</button>
					</div>
				</div>
			</div>
		</div>		
		
		<!-- 工单升降级原因编辑页面弹窗 -->
		<div id= "reason_form" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
			<div class= "modal-dialog modal-lg" role= "document" >
				<div class= "modal-content" style= "border: 3px solid #394557;" >
					<div class= "modal-header" >
						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
						<h4 id= "formLabel" class= "modal-title" >新增原因</h4>
					</div>
					<div class= "modal-body" >
						<div class= "form-group" >
				    		<label class= "blue" for= "reason_form" >原因&nbsp;:</label>
				    		<input id= "reasons_form" name= "reason_form" class= "form-control" type= "text"/>
				    	</div>
						<div class= "form-group">
				    		<label class= "blue" for= "remark_form" >说明&nbsp;:</label>
				    		<input id= "remark_form" name= "remark_form" class= "form-control" type= "text" />
				    	</div>
					</div>
					<div class= "modal-footer" >
						<button id= "btn_back3" type= "button" class= "btn btn-default" data-dismiss="modal" >
							<i class= "icon-undo" aria-hidden= "true" ></i>返回
						</button>
		     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="saveReason();">
		     				<i class="icon-save" aria-hidden= "true" ></i></i><span id="btn_reason_text">保存</span>
		     			</button>
					</div>
				</div>
			</div>
		</div>
</html>


