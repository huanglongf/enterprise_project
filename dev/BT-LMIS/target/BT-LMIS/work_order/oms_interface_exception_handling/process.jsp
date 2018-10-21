<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- OMS接口异常处理弹窗 -->
<div id= "omsInterfaceExceptionHandling_form" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
    <div class= "modal-dialog modal-lg" style= "width: 80%;" role= "document" >
        <div class= "modal-content" style= "border: 3px solid #394557;" >
            <div class= "modal-header" >
                <button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" onclick= "emptyProcess();" ><span aria-hidden= "true" >×</span></button>
                <h4 class= "modal-title" >OMS接口异常处理</h4>
            </div>
            <div class= "modal-body container" >
            	<form id= "oiehForm" >
            		<input id="claimId" name="claimId" type="hidden" />
            		<input id="woSource" name="woSource" type="hidden" value="OMS" />
            		<input id="woType" name="woType"  type="hidden" value="OMSC">
            		<input id="woTypeDisplay" name="woTypeDisplay" type="hidden" value="OMS索赔" />
            		<input id="platformNumber" name="platformNumber" type="hidden" />
            		<input id="relatedNumber" name="relatedNumber" type="hidden" />
            		<input id="expressNumber" name="expressNumber" type="hidden" />
            		<div class= "row form-group" >
	                	<div class="col-xs-2" style="text-align: right;">
	                        <label class= "no-padding-right blue" style= "white-space: nowrap;" >
                            	平台订单号&nbsp;:
	                        </label>
	                    </div>
	                    <div class= "col-xs-2">
	                    	<label id="pn" style="white-space: nowrap;">
	                    	</label>
	                    </div>
	                    <div class="col-xs-2">
	                    </div>
	                    <div class= "col-xs-2" style= "text-align: right;" >
	                        <label class= "no-padding-right blue" for= "relatedNumber" style= "white-space: nowrap;" >
                            	相关单据号&nbsp;:
	                        </label>
	                    </div>
	                    <div class= "col-xs-2">
	                    	<label id="rn" style="white-space: nowrap;">
	                    	</label>
	                    </div>
	                    <div class="col-xs-2">
	                    </div>
	                </div>
	                <div class= "row form-group" >
	                    <div class= "col-xs-2" style= "text-align: right;" >
	                        <label class= "no-padding-right blue" style= "white-space: nowrap;" >
                            	快递单号&nbsp;:
	                        </label>
	                    </div>
	                    <div class="col-xs-2">
	                        <label style="white-space: nowrap; cursor: pointer;" title= "点击刷新" >
	                        	<a id="en" onclick="getData();"></a>
	                        </label>
	                    </div>
	                    <div class="col-xs-2"></div>
	                    <div class= "col-xs-2" style= "text-align: right;" >
	                        <label class= "no-padding-right blue" for= "carriers" style= "white-space: nowrap;" >
                           		物流服务商&nbsp;:
	                        </label>
	                    </div>
	                    <div class= "col-xs-2" >
	                        <select id= "carriers" name= "carriers" data-edit-select= "1" checkType= "{'NOTNULL': '不能为空'}" onchange= "checkValue($(this));endueDisplay('carriers');" >
	                            <option value= "" >---请选择---</option>
	                        </select>
	                        <input id= "carriersDisplay" name= "carriersDisplay" type= "hidden" />
	                    </div>
	                   	<div class= "oiehCheckInfo col-xs-2" validation= "0" >
	                    </div>
	                </div>
	                <div class= "row form-group" >
	                    <div class= "col-xs-2" style= "text-align: right;" >
	                        <label class= "no-padding-right blue" for= "warehouses" style= "white-space: nowrap;" >
                            	发货仓库&nbsp;:
	                        </label>
	                    </div>
	                    <div class= "col-xs-2" >
	                        <select id= "warehouses" name= "warehouses" data-edit-select= "1" checkType= "{'NOTNULL': '不能为空'}" onchange= "checkValue($(this));endueDisplay('warehouses');" >
	                            <option value= "" >---请选择---</option>
	                        </select>
	                       	<input id= "warehousesDisplay" name= "warehousesDisplay" type= "hidden" />
	                    </div>
	                    <div class= "oiehCheckInfo col-xs-2" validation= "0" >
	                    </div>
	                    <div class= "col-xs-2" style= "text-align: right;" >
	                        <label class= "no-padding-right blue" for= "transportTime" style= "white-space: nowrap;" >
                            	出库时间&nbsp;:
	                        </label>
	                    </div>
	                    <div class= "col-xs-2" >
	                    	<input id="transportTime" name="transportTime" type="text" style= "width: 100%; height: 34px;" class= "form-control Wdate" placeholder= "" onfocus= "showCalendar($(this));"
	                    		checkType= "{'yyyy-MM-dd hh:mm:ss': '非法时间'}" oninput= "checkValue($(this));" onblur= "javascript:if(!$(this).prop('readonly')){checkValue($(this));}" />
	                    </div>
	                    <div class= "oiehCheckInfo col-xs-2" validation= "0" >
	                    </div>
	                </div>
	                <div class= "row form-group" >
	                    <div class= "col-xs-2" style= "text-align: right;" >
	                        <label class= "no-padding-right blue" for= "stores" style= "white-space: nowrap;" >
                            	店铺&nbsp;:
	                        </label>
	                    </div>
	                    <div class= "col-xs-2" >
	                        <select id= "stores" name= "stores" data-edit-select= "1" checkType= "{'NOTNULL': '不能为空'}" onchange= "checkValue($(this));endueDisplay('stores');" >
	                            <option value= "" >---请选择---</option>
	                        </select>
	                        <input id= "storesDisplay" name= "storesDisplay" type= "hidden" />
	                    </div>
	                    <div class= "oiehCheckInfo col-xs-2" validation= "0" >
	                    </div>
	                    <div class= "col-xs-2" style= "text-align: right;" >
	                        <label class= "no-padding-right blue" for= "orderAmount" style= "white-space: nowrap;" >
                           		订单金额（元）&nbsp;:
	                        </label>
	                    </div>
	                    <div class= "col-xs-2" >
	                        <input id= "orderAmount" name= "orderAmount" type= "text" class= "form-control" placeholder= "最大长度10" checkType= "{'LENGTH': '长度不合法', 'NUM': '非法数字'}" validate-param= "{'MAX': 10}" oninput= "checkValue($(this));" />
	                    </div>
	                    <div class= "oiehCheckInfo col-xs-2" validation= "0" >
	                    </div>
	                </div>
	                <div class= "row form-group" >
	                    <div class= "col-xs-2" style= "text-align: right;" >
	                        <label class= "no-padding-right blue" for= "recipient" style= "white-space: nowrap;" >
                           		收件人&nbsp;:
	                        </label>
	                    </div>
	                    <div class= "col-xs-2" >
	                        <input id= "recipient" name= "recipient" type= "text" class= "form-control" placeholder= "最大长度20"
	                        	checkType= "{'LENGTH': '长度不合法'}" validate-param= "{'MAX': 20}" oninput= "checkValue($(this));" />
	                    </div>
	                    <div class= "oiehCheckInfo col-xs-2" validation= "0" >
	                    </div>
	                    <div class= "col-xs-2" style= "text-align: right;" >
	                        <label class= "no-padding-right blue" for= "phone" style= "white-space: nowrap;" >
                           		联系电话&nbsp;:
	                        </label>
	                    </div>
	                    <div class= "col-xs-2" >
	                        <input id= "phone" name= "phone" type= "text" class= "form-control" placeholder= "座机/手机" checkType= "{'LENGTH': '长度不合法'}" validate-param= "{'MAX': 30}" oninput= "checkValue($(this));" />
	                    </div>
	                    <div class= "oiehCheckInfo col-xs-2" validation= "0" >
	                    </div>
	                </div>
	                <div class= "row form-group" >
	                    <div class= "col-xs-2" style= "text-align: right;" >
	                        <label class= "no-padding-right blue" for= "address" style= "white-space: nowrap;" >
                          		联系地址&nbsp;:
	                        </label>
	                    </div>
	                    <div class= "col-xs-8" >
	                        <input id= "address" name= "address" type= "text" class= "form-control" placeholder= "最大长度120" checkType= "{'LENGTH': '长度不合法'}" validate-param= "{'MAX': 120}" oninput= "checkValue($(this));" />
	                    </div>
	                    <div class= "oiehCheckInfo col-xs-2" validation= "0" >
	                    </div>
	                </div>
            	</form>
            </div>
            <div class= "modal-footer" >
                <button type= "button" class= "btn btn-default" data-dismiss= "modal" onclick= "emptyProcess();" >
                    <i class= "icon-undo" aria-hidden= "true" ></i>返回
                </button>
                <button type= "button" class= "btn btn-primary" onclick= "save('oiehForm');" >
                    <i class= "icon-save" aria-hidden= "true" ></i>生成工单
                </button>
            </div>
        </div>
    </div>
</div>