<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 工单任务手动分配界面弹窗 -->
<div id= "woAlloc_form" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
    <div class= "modal-dialog modal-lg" style= "width: 50%;" role= "document" >
        <div class= "modal-content" style= "border: 3px solid #394557;" >
            <div class= "modal-header" >
                <button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" onclick= "initalAllocForm();" ><span aria-hidden= "true" >×</span></button>
                <h4 class= "modal-title" >工单任务手动分配</h4>
            </div>
            <div class= "modal-body" >
            	<form id= "allocForm" class= "container" >
            		<div class= "row form-group" >
            			<div class= "col-xs-2" style= "text-align: right;" >
	                    	<label class= "no-padding-right blue" for= "outManhours" style= "white-space: nowrap;" >
	                          		不计入工时&nbsp;: 
	                        </label>
	                    </div>
	                    <div class= "col-xs-4" >
	                    	<input id="outManhours" name="outManhours" type="checkbox" class="ace ace-switch ace-switch-5" />
							<span class="lbl"></span>
	                    </div>
            		</div>
            		<div class= "row form-group" >
            			<div class= "col-xs-2" style= "text-align: right;" >
	                        <label class= "no-padding-right blue" for= "groups" style= "white-space: nowrap;" >
                           		组别&nbsp;: 
	                        </label>
	                    </div>
	                    <div class= "col-xs-8" >
	                    	<select id= "groups" name= "groups" data-edit-select= "1" checkType= "{'NOTNULL': '不能为空'}" onchange= "shiftGroup('groups', 'account', '0');checkValue($(this));" >
	                            <option value= "" >---请选择---</option>
	                        </select>
	                    </div>
	                    <div class= "allocCheckInfo col-xs-2" validation= "0" >
	                    </div>
            		</div>
            		<div class= "row form-group" >
           				<div class= "col-xs-2" style= "text-align: right;" >
	                        <label class= "no-padding-right blue" for= "account" style= "white-space: nowrap;" >
                           		员工&nbsp;: 
	                        </label>
	                    </div>
                    	<div class= "col-xs-8" >
                       		<select id= "account" name= "account" data-edit-select= "1" checkType= "{'NOTNULL': '不能为空'}" onchange= "checkValue($(this));" >
	                            <option value= "" >---请选择---</option>
	                        </select>
	                    </div>
	                    <div class= "allocCheckInfo col-xs-2" validation= "0" >
	                    </div>
            		</div>
            	</form>
            </div>
            <div class= "modal-footer" >
                <button type= "button" class= "btn btn-default" data-dismiss= "modal" onclick= "initalAllocForm();" >
                    <i class= "icon-undo" aria-hidden= "true" ></i>返回
                </button>
                <button type= "button" class= "btn btn-primary" onclick= "alloc('allocForm');" >
                    <i class= "icon-save" aria-hidden= "true" ></i>分配
                </button>
            </div>
        </div>
    </div>
</div>