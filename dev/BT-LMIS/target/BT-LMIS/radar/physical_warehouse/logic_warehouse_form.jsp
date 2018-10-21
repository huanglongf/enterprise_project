<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 逻辑仓新增/编辑界面弹窗 -->
<div id= "logic_warehosue_form" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
    <div class= "modal-dialog modal-lg" style= "width: 50%;" role= "document" >
        <div class= "modal-content" style= "border: 3px solid #394557;" >
            <div class= "modal-header" >
                <button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" onclick= "initDetail();" ><span aria-hidden= "true" >×</span></button>
                <h4 id= "lwTitle" class= "modal-title" ></h4>
            </div>
            <div class= "modal-body" style= "height: 100px;" >
            	<form id="logicWarehouseForm" class= "container row-height" >
	            	<input id = "detail_id" type = "hidden" />
            		<div class= "row form-group" >
            			 <div class= "col-xs-2" >
	            			<label class= "no-padding-right blue" style= "white-space: nowrap;" for= "logic_warehouse" >
	            				逻辑仓&nbsp;:
	                        </label>
                        </div>
                        <div class="col-xs-8">
                        	<select id="logic_warehouse" name="logic_warehouse" data-edit-select="1" checkType="{'NOTNULL': '不能为空'}" onchange="checkValue($(this));">
			  					<option value="">---请选择---</option>
			  				</select>
                        </div>
                        <div class="lwCheckInfo col-xs-2" validation="0">
	                    </div>
            		</div>
            	</form>
            </div>
            <div class= "modal-footer" >
                <button type="button" class="btn btn-primary" onclick="saveDetail('logicWarehouseForm');">
                    <i class="icon-save" aria-hidden="true"></i>提交
                </button>
            </div>
        </div>
    </div>
</div>