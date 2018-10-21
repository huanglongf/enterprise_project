<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
	$(function(){
		$("#account").next().prop("disabled", "disabled");
		$("#wl").next().prop("disabled", "disabled");
		$("#et").next().prop("disabled", "disabled");
		change_wo_type();
		change_error_type();
	})
</script>
<!-- 工单异动界面弹窗 -->
<div id= "alter_form" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
    <div class= "modal-dialog modal-lg" role= "document" style= "width: 60%;" >
        <div class= "modal-content" style= "border: 3px solid #394557;" >
            <div class= "modal-header" >
                <button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" onclick= "" ><span aria-hidden= "true" >×</span></button>
                <h4 class= "modal-title" >工单异动</h4>
            </div>
            <div class= "modal-body" >
            	<form class= "container" >
            		<input id= "wt" name= "wt" type= "hidden" value= "${wo_display.woType }" >
	            	<input id= "wl_before" name= "wl_before" type= "hidden" value= "${wo_display.woLevel }" >
	            	<input id= "et_before" name= "et_before" type= "hidden" value= "${wo_display.exception_type }" >
	            	<input id= "ar_before" name= "ar_before" type= "hidden" value= "${wo_display.level_alter_reason }" >
	             <div id= "wo_type_div" class= "col-xs-12 row form-group" >
						<div class= "col-xs-2" style= "text-align: right;" >
							<label class= "no-padding-right blue"   style= "white-space: nowrap;" >
							    工单类型&nbsp;: 
						    </label>
						</div>
						<div class= "col-xs-8" >
						    <select id= "wo_type_select_id"  data-edit-select= "1"  onchange="change_wo_type()">
					        	<option value= "" >---请选择---</option>
						        <c:forEach items= "${woTypes}" var= "woTypes" >
									<option value= "${woTypes.code}"  ${wo_display.woType == woTypes.code? "selected= 'selected'": "" } >${woTypes.name }</option>
								</c:forEach>
					    	</select>
						</div>
						<div class= "alterCheckInfo col-xs-2" validation= "0" >
	                    </div>
					</div>	
	
					
	            	<div class= "col-xs-12 row form-group" >
						<div class= "col-xs-2" style= "text-align: right;" >
							<label class= "no-padding-right blue" for= "wl" style= "white-space: nowrap;" >
								工单级别&nbsp;: 
						    </label>
						</div>
						
						<div class= "col-xs-2" >
						    <select id= "wl" name= "wl"  checkType= "{'NOTNULL': '不能为空'}" 
						    	onchange= "shiftLevel('wl', {'wlbId': 'wl_before', 'reasonDiveId': 'levelAlter', 'reasonId': 'ar', 'groupId': 'groups', 'accountId': 'account'});endueDisplay('wl');"   style="width:200px;">
					        	<option value= "" >---请选择---</option>
					    	</select>
					    	<input id= "wlDisplay" name= "wlDisplay" type= "hidden" />
						</div>
						
						<div class= "alterCheckInfo col-xs-2" validation= "0" ></div>
						<div class= "col-xs-2" style= "text-align: right;" >
							<label class= "no-padding-right blue" for= "et" style= "white-space: nowrap;" >
								异常类型&nbsp;: 
						    </label>
						</div>
						<div class= "col-xs-2" >
						    <select id= "et" name= "et"  checkType= "{}" onchange= "shiftReason('et', 'et_before');"  style="width:200px;">
						        <option value= "" >---请选择---</option>
<%-- 						        <c:forEach items= "${exceptions }" var= "exceptions" > --%>
<%-- 									<option value= "${exceptions.type_name }" ${wo_display.exception_type == exceptions.type_name? "selected= 'selected'": "" } > --%>
<%-- 										${exceptions.type_name } --%>
<!-- 									</option> -->
<%-- 								</c:forEach> --%>
						    </select>
						</div>
						<div class= "alterCheckInfo col-xs-2" validation= "0" ></div>
						
	
							
			
					</div>
					
					<div  class= "col-xs-8" ></div>
				
				
					
					
					
					
					<div id= "levelAlter" class= "col-xs-12 row form-group" style= "display: none;" >
						<div class= "col-xs-2" style= "text-align: right;" >
							<label class= "no-padding-right blue" for= "ar" style= "white-space: nowrap;" >
								升降级原因&nbsp;: 
						    </label>
						</div>
						<div class= "col-xs-8" >
						    <select id= "ar" name= "ar"  checkType= "{'NOTNULL': '不能为空'}" onchange= "checkValue($(this));"  style="width: 100%;">
					        	<option value= "" >---请选择---</option>
<%-- 						        <c:forEach items= "${woLevelAlterReason }" var= "woLevelAlterReason" > --%>
<%-- 									<option value= "${woLevelAlterReason.id }" > --%>
<%-- 									${woLevelAlterReason.reason } --%>
<!-- 								</option> -->
<%-- 								</c:forEach> --%>
					    	</select>
						</div>
						<div class= "alterCheckInfo col-xs-2" validation= "0" >
	                    </div>
					</div>
					
					
               			
					
					<div class= "col-xs-12 row form-group" >
						<hr>
						<div class= "col-xs-2" style= "text-align: right;" >
	                        <label class= "no-padding-right blue" for= "groups" style= "white-space: nowrap;" >
	 							<h4><strong>转发工单</strong></h4>
	                        </label>
	                    </div>
					</div>
					<div class= "col-xs-12 row form-group" >
						<div class= "col-xs-2" style= "text-align: right;" >
	                        <label class= "no-padding-right blue" for= "groups" style= "white-space: nowrap;" >
	 							组别&nbsp;: 
	                        </label>
	                    </div>
	                    <div class= "col-xs-2" >
	                    	<select id= "groups" name= "groups" data-edit-select= "1" checkType= "{}" onchange= "shiftGroup('groups', 'account', '1');checkValue($(this));" >
	                            <option value= "" >---请选择---</option>
	                            <c:forEach items= "${groups }" var= "groups" >
									<option value= "${groups.id }" >
									${groups.group_name }
								</option>
								</c:forEach>
	                        </select>
	                    </div>
	                    <div class= "alterCheckInfo col-xs-2" validation= "0" >
	                    </div>
	                    <div class= "col-xs-2" style= "text-align: right;" >
	                        <label class= "no-padding-right blue" for= "account" style= "white-space: nowrap;" >
	 							员工&nbsp;: 
	                        </label>
	                    </div>
	                   	<div class= "col-xs-2" >
	              			<select id= "account" name= "account" data-edit-select= "1" checkType= "{'NOTNULL': '不能为空'}" onchange= "checkValue($(this));" >
	                            <option value= "" >---请选择---</option>
	                        </select>
	                    </div>
	                    <div class= "alterCheckInfo col-xs-2" validation= "0" >
	                   	</div>
					</div>
            	</form>
            </div>
            <div class= "modal-footer" >
                <button type= "button" class= "btn btn-default" data-dismiss= "modal" onclick= "" >
                    <i class= "icon-undo" aria-hidden= "true" ></i>返回
                </button>
                <button type= "button" class= "btn btn-primary btn-alterWorkOrder" onclick= "updateWorkOrder();" >
                    <i class= "icon-save" aria-hidden= "true" ></i>保存
                </button>
            </div>
        </div>
    </div>
</div>