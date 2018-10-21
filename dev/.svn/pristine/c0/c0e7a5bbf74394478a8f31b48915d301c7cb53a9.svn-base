<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 店铺表单弹窗 -->
<div id="form" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" style="width:80%;" role="document">
        <div class="modal-content" style="border:3px solid #394557;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick= ";"><span aria-hidden="true">×</span></button>
                <h4 id="formTitle" class="modal-title"></h4>
            </div>
            <div class="modal-body container">
            	<form id="saveForm">
            		<input id="id" name="id" type="hidden" />
					<div class="row form-group">
						<div class="col-md-1" style="text-align:right;">
	                        <label class="no-padding-right blue" for="clientId" style="white-space:nowrap;">
                            	所属客户&nbsp;:
	                        </label>
	                    </div>
	                    <div class="col-md-3">
	                    	<select id="clientId" name="clientId" data-edit-select="1">
	                    		<option value="">---请选择---</option>
	                    	</select>
	                    </div>
			    		<div class="formCheckInfo col-md-2" validation="0">
	                    </div>
	                	<div class="col-md-1" style="text-align:right;">
	                        <label class="no-padding-right blue" for="costCenter" style="white-space:nowrap;">
                            	成本中心&nbsp;:
	                        </label>
	                    </div>
	                    <div class="col-md-3">
	                    	<input id="costCenter" name="costCenter" class="form-control" type="text" placeholder="必填，最大长度100"
	                    		checkType="{'NOTNULL':'不能为空','LENGTH':'长度不合法'}" validate-param="{'MAX':100}" onblur="checkValue($(this));" />
	                    </div>
			    		<div class="formCheckInfo col-md-2" validation="0">
	                    </div>
			    	</div>
					<div class="row form-group">
						<div class="col-md-1" style="text-align:right;">
	                        <label class="no-padding-right blue" for="storeCode" style="white-space:nowrap;">
                            	店铺编码&nbsp;:
	                        </label>
	                    </div>
	                    <div class="col-md-3">
	                    	<input id="storeCode" name="storeCode" class="form-control" type="text" placeholder="必填，最大长度50"
	                    		checkType="{'NOTNULL':'不能为空','LENGTH':'长度不合法'}" validate-param="{'MAX':50}" onblur="checkValue($(this));" />
	                    </div>
			    		<div class="formCheckInfo col-md-2" validation="0">
	                    </div>
			    		<div class="col-md-1" style="text-align:right;">
	                        <label class="no-padding-right blue" for="storeName" style="white-space:nowrap;">
                            	店铺名称&nbsp;:
	                        </label>
	                    </div>
	                    <div class="col-md-3">
	                    	<input id="storeName" name="storeName" class="form-control" type="text" placeholder="必填，最大长度50"
	                    		checkType="{'NOTNULL':'不能为空','LENGTH':'长度不合法'}" validate-param="{'MAX':50}" onblur="checkValue($(this));" />
	                    </div>
			    		<div class="formCheckInfo col-md-2" validation="0">
	                    </div>
			    	</div>
			    	<div class="row form-group">
			    		<div class="col-md-1" style="text-align:right;">
	                        <label class="no-padding-right blue" for="storeType" style="white-space:nowrap;">
                            	店铺类型&nbsp;:
	                        </label>
	                    </div>
	                    <div class="col-md-3">
	                    	<select id="storeType" name="storeType" class="form-control" checkType="{'NOTNULL':'不能为空'}" onchange="checkValue($(this));">
								<option value="">---请选择---</option>
								<option value="0">A类</option>
								<option value="1">B类</option>
								<option value="2">其它</option>
							</select>
	                    </div>
	                    <div class="formCheckInfo col-md-2" validation="0">
	                    </div>
	                    <div class="col-md-1" style="text-align:right;">
	                        <label class="no-padding-right blue" for="settlementMethod" style="white-space:nowrap;">
                            	结算方式&nbsp;:
	                        </label>
	                    </div>
	                    <div class="col-md-3">
	                    	<select id="settlementMethod" name="settlementMethod" class="form-control" checkType="{'NOTNULL':'不能为空'}" onchange="checkValue($(this));">
								<option value="">---请选择---</option>
								<option value="0">代销</option>
								<option value="1">结算经销</option>
								<option value="2">付款经销</option>
							</select>
	                    </div>
	                    <div class="formCheckInfo col-md-2" validation="0">
	                    </div>
			    	</div>
			    	<div class="row form-group">
			    		<div class="col-md-1" style="text-align:right;">
	                        <label class="no-padding-right blue" for="contact" style="white-space:nowrap;">
                            	联系人&nbsp;:
	                        </label>
	                    </div>
	                    <div class="col-md-3">
	                    	<input id="contact" name="contact" class="form-control" type="text" placeholder="必填，最大长度10"
	                    		checkType="{'NOTNULL':'不能为空','LENGTH':'长度不合法'}" validate-param="{'MAX':10}" onblur="checkValue($(this));" />
	                    </div>
	                    <div class="formCheckInfo col-md-2" validation="0">
	                    </div>
	                    <div class="col-md-1" style="text-align:right;">
	                        <label class="no-padding-right blue" for="phone" style="white-space:nowrap;">
                            	联系电话&nbsp;:
	                        </label>
	                    </div>
	                    <div class="col-md-3">
	                    	<input id="phone" name="phone" class="form-control" type="text" placeholder="必填，手机号或固定电话"
	                    		checkType="{'NOTNULL':'不能为空','PHONE':'联系方式不合法'}" onblur="checkValue($(this));" />
	                    </div>
	                    <div class="formCheckInfo col-md-2" validation="0">
	                    </div>
			    	</div>
			    	<div class="row form-group">
			    		<div class="col-md-1" style="text-align:right;">
	                        <label class="no-padding-right blue" for="address" style="white-space:nowrap;">
                            	联系地址&nbsp;:
	                        </label>
	                    </div>
	                    <div class="col-md-9">
	                    	<input id="address" name="address" class="form-control" type="text" placeholder="必填，最大长度100"
	                    		checkType="{'NOTNULL':'不能为空','LENGTH':'长度不合法'}" validate-param="{'MAX':100}" onblur="checkValue($(this));" />
	                    </div>
			    		<div class="formCheckInfo col-md-2" validation="0">
	                    </div>
			    	</div>
			    	<div class="row form-group">
			    		<div class="col-md-1" style="text-align:right;">
	                        <label class="no-padding-right blue" for="company" style="white-space:nowrap;">
                            	开票公司&nbsp;:
	                        </label>
	                    </div>
	                    <div class="col-md-3">
	                    	<input id="company" name="company" class="form-control" type="text" placeholder="必填，最大长度100"
	                    		checkType="{'NOTNULL':'不能为空','LENGTH':'长度不合法'}" validate-param="{'MAX':100}" onblur="checkValue($(this));" />
	                    </div>
	                    <div class="formCheckInfo col-md-2" validation="0">
	                    </div>
	                    <div class="col-md-1" style="text-align:right;">
	                        <label class="no-padding-right blue" for="invoiceType" style="white-space:nowrap;">
                            	发票类型&nbsp;:
	                        </label>
	                    </div>
	                    <div class="col-md-3">
	                    	<select id="invoiceType" name="invoiceType" class="form-control" checkType="{'NOTNULL':'不能为空'}" onchange="checkValue($(this));">
								<option value="">---请选择---</option>
								<option value="0">手写发票</option>
								<option value="1">机打发票</option>
								<option value="2">普通发票</option>
								<option value="3">增值税发票</option>
								<option value="4">其它发票</option>
							</select>
	                    </div>
	                    <div class="formCheckInfo col-md-2" validation="0">
	                    </div>
			    	</div>
			    	<div class="row form-group">
	                    <div class="col-md-1" style="text-align:right;">
	                        <label class="no-padding-right blue" for="invoiceInfo" style="white-space:nowrap;">
                            	发票信息&nbsp;:
	                        </label>
	                    </div>
	                    <div class="col-md-9">
	                    	<textarea id="invoiceInfo" name="invoiceInfo" class="form-control" style="height:100px;" cols="30" placeholder="最大长度200"
	                    		checkType="{'LENGTH':'长度不合法'}" validate-param="{'MAX':200}" onblur="checkValue($(this));"></textarea>
	                    </div>
	                    <div class="formCheckInfo col-md-2" validation="0">
	                    </div>
			    	</div>
			    	<div class="row form-group">
	                    <div class="col-md-1" style="text-align:right;">
	                        <label class="no-padding-right blue" for="remark" style="white-space:nowrap;">
                            	备注&nbsp;:
	                        </label>
	                    </div>
	                    <div class="col-md-9">
	                    	<textarea id="remark" name="remark" class="form-control" style="height:100px;" cols="30" placeholder="最大长度100"
	                    		checkType="{'LENGTH':'长度不合法'}" validate-param="{'MAX':100}" onblur="checkValue($(this));"></textarea>
	                    </div>
	                    <div class="formCheckInfo col-md-2" validation="0">
	                    </div>
			    	</div>
			    	<div class="row form-group">
			    		<div class="col-md-1" style="text-align:right;">
	                        <label class="no-padding-right blue" for="woFlag" style="white-space:nowrap;">
                            	雷达监控&nbsp;:
	                        </label>
	                    </div>
	                    <div class= "col-md-1" >
	                    	<input id="woFlag" name="woFlag" type="checkbox" class="ace ace-switch ace-switch-5" />
							<span class="lbl"></span>
	                    </div>
	                    <div class="formCheckInfo col-md-2" validation="0">
	                    </div>
						<div class="col-md-1" style="text-align:right;">
							<label class="no-padding-right blue" for="storebj" style="white-space:nowrap;">
								店铺保价&nbsp;:
							</label>
						</div>
						<div class= "col-md-1" >
							<input id="storebj" name="storebj" type="checkbox"  class="ace ace-switch ace-switch-5" />
							<span class="lbl"></span>
						</div>
						<div class="formCheckInfo col-md-2" validation="0">
						</div>
	                    <div class="col-md-1" style="text-align:right;">
	                        <label class="no-padding-right blue" for="validity" style="white-space:nowrap;">
                            	是否有效&nbsp;:
	                        </label>
	                    </div>
	                    <div class= "col-md-1" >
	                    	<input id="validity" name="validity" type="checkbox" checked="checked" class="ace ace-switch ace-switch-5" />
							<span class="lbl"></span>
	                    </div>
	                    <div class="formCheckInfo col-md-2" validation="0">
	                    </div>
			    	</div>
            	</form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="save();" >
                    <i class="icon-save" aria-hidden="true"></i>保存
                </button>
            </div>
        </div>
    </div>
</div>