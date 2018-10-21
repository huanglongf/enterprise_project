<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- 工作权限编辑界面弹窗 -->
<div id= "workPower_form" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
    <div class= "modal-dialog modal-lg" style= "width: 50%;" role= "document" >
        <div class= "modal-content" style= "border: 3px solid #394557;" >
            <div class= "modal-header" >
                <button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" onclick= "initWorkPower();" ><span aria-hidden= "true" >×</span></button>
                <h4 class= "modal-title" >工作权限</h4>
            </div>
            <div class= "modal-body" style= "height: 450px;" >
            	<div id= "storePowerForm" class= "container row-height" >
            		<input id= "wp_id" name= "wp_id" type= "hidden" >
            		<div class= "row form-group" >
            			<div class= "col-xs-2" style= "text-align: right;" >
	                    	<label class= "no-padding-right blue" for= "carrier" style= "white-space: nowrap;" >
	                    		物流商&nbsp;:
	                        </label>
	                    </div>
	                    <div class= "col-xs-9" >
	                    	<select id= "carrier" name= "carrier" data-edit-select= "1" >
			  					<option selected= "selected" value="" >---请选择---</option>
			  					<c:forEach items= "${carrier }" var= "carriers" >
	                                <option value= "${carriers.transport_code }" >${carriers.transport_name }</option>
	                            </c:forEach>
			  				</select>
	                    </div>
            		</div>
            		<div class= "row form-group" >
            			<div class= "col-xs-2" style= "text-align: right;" >
            				<label class= "no-padding-right blue" for= "carrier" style= "white-space: nowrap;" >
	                    		工单类型&级别&nbsp;:
	                        </label>
            			</div>
            			<div class= "col-xs-9" >
						</div>
            		</div>
            	</div>
            </div>
            <div class= "modal-footer" >
                <button type= "button" class= "btn btn-default" data-dismiss= "modal" onclick= "initWorkPower();" >
                    <i class= "icon-undo" aria-hidden= "true" ></i>返回
                </button>
                <button type= "button" class= "btn btn-primary" onclick= "saveWorkPower();" >
                    <i class= "icon-save" aria-hidden= "true" ></i>保存
                </button>
            </div>
        </div>
    </div>
</div>