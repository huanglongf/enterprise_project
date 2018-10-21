<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 工单挂起界面弹窗 -->
<div id= "woPause_form" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
    <div class= "modal-dialog modal-lg" style= "width: 40%;" role= "document" >
        <div class= "modal-content" style= "border: 3px solid #394557;" >
            <div class= "modal-header" >
                <button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" onclick= "initalPauseForm();" ><span aria-hidden= "true" >×</span></button>
                <h4 class= "modal-title" >挂起</h4>
            </div>
            <div class= "modal-body" >
            	<form id= "pauseForm" class= "container" >
            		<div class= "row form-group" >
            			<div class= "col-xs-2" style= "text-align: right;" >
	                        <label class= "no-padding-right blue" for= "pause_reason" style= "white-space: nowrap;" >
                          		挂起原因&nbsp;: 
	                        </label>
	                    </div>
            			<div class= "col-xs-8" >
            				<textarea id= "pause_reason" name= "pause_reason" class= "form-control" style= "height: 150px;" cols= "30" placeholder= "最大长度120"
            					checkType= "{'NOTNULL': '不能为空', 'LENGTH': '长度不合法'}" validate-param= "{'MAX': 120}" oninput= "checkValue($(this));" ></textarea>
            			</div>
            			<div class= "pauseCheckInfo col-xs-2" validation= "0" >
            			</div>
            		</div>
            	</form>
            </div>
            <div class= "modal-footer" >
                <button type= "button" class= "btn btn-default" data-dismiss= "modal" onclick= "initalPauseForm();" >
                    <i class= "icon-undo" aria-hidden= "true" ></i>返回
                </button>
                <button type= "button" class= "btn btn-primary" onclick= "operateStatus('${role }', '1', 'PAUSE');" >
                    <i class= "icon-save" aria-hidden= "true" ></i>挂起
                </button>
            </div>
        </div>
    </div>
</div>