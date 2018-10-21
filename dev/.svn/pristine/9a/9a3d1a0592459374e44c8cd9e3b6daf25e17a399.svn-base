<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="widget-box no-margin">
	<div class="widget-header header-color-blue">
		<h4>查询列表</h4>
        <div class="widget-toolbar">
            <a href="#" data-action="reload" onclick="refreshDataView();">
                <i class="icon-refresh bigger-125"></i>
            </a>
            <a href="#" data-action="collapse">
                <i class="icon-chevron-up bigger-125"></i>
            </a>
        </div>
        <div class="widget-toolbar no-border">
            <button class="btn btn-xs btn-white bigger" onclick="dataView();">
                <i class="icon-search"></i>查询
            </button>
        </div>
	</div>
	<div class="widget-body">
		<div class="widget-main" style="background:#F0F8FF;">
    		<form id="condition" class="container-fruid">
    			<div class="row form-group">
    				<div class="col-md-1" style="text-align:right;">
    					<label class="no-padding-right blue" for="costCenterQuery" style="white-space:nowrap;">
               				成本中心&nbsp;: 
                        </label>
    				</div>
                       <div class="col-md-2">
                       	<input id="costCenterQuery" name="costCenterQuery" type="text" class="form-control" />
                       </div>
                       <div class="col-md-1">
                       </div>
                       <div class="col-md-1" style="text-align:right;">
                       	<label class="no-padding-right blue" for="storeCodeQuery" style="white-space:nowrap;">
                       		店铺编码&nbsp;: 
                        </label>
                       </div>
                   	<div class="col-md-2">
                   		<input id="storeCodeQuery" name="storeCodeQuery" type="text" class="form-control" />
                   	</div>
                   	<div class="col-md-1">
                       </div>
                   	<div class="col-md-1" style="text-align:right;">
                   		<label class="no-padding-right blue" for="storeNameQuery" style="white-space:nowrap;">
                       		店铺名称&nbsp;: 
                        </label>
                   	</div>
                   	<div class="col-md-2">
                   		<input id="storeNameQuery" name="storeNameQuery" type="text" class="form-control" />
                   	</div>
                   	<div class="col-md-1">
                       </div>
    			</div>
    			<div class="row form-group">
    				<div class="col-md-1" style="text-align:right;">
    					<label class="no-padding-right blue" for="ownedCustomerQuery" style="white-space:nowrap;">
                       		所属客户&nbsp;: 
                        </label>
    				</div>
                   	<div class="col-md-2">
                   		<select id="ownedCustomerQuery" name="ownedCustomerQuery" data-edit-select="1">
                    		<option value="">---请选择---</option>
                    		<c:forEach items="${customer }" var="customer">
                                <option value="${customer.id }">${customer.clientName }</option>
                            </c:forEach>
                    	</select>
                   	</div>
                   	<div class="col-md-1">
                   	</div>
    				<div class="col-md-1" style="text-align:right;">
    					<label class="no-padding-right blue" for="storeTypeQuery" style="white-space:nowrap;">
                       		店铺类型&nbsp;: 
                        </label>
    				</div>
                   	<div class="col-md-2">
                   		<select id="storeTypeQuery" name="storeTypeQuery" data-edit-select="1">
                    		<option value="">---请选择---</option>
                    		<option value="0">A类</option>
                    		<option value="1">B类</option>
                    		<option value="2">其它</option>
                    	</select>
                   	</div>
                   	<div class="col-md-1">
                   	</div>
                   	<div class="col-md-1" style="text-align:right;">
                   		<label class="no-padding-right blue" for="settlementMethodQuery" style="white-space:nowrap;">
                       		结算方式&nbsp;:
                        </label>
                   	</div>
                   	<div class="col-md-2">
                   		<select id="settlementMethodQuery" name="settlementMethodQuery" data-edit-select="1">
                    		<option value="">---请选择---</option>
                    		<option value="0">代销</option>
                    		<option value="1">结算经销</option>
                    		<option value="2">付款经销</option>
                    	</select>
                   	</div>
                   	<div class="col-md-1">
                   	</div>
    			</div>
    			<div class="row form-group">
    				<div class="col-md-1" style="text-align:right;">
                   		<label class="no-padding-right blue" for="contactQuery" style="white-space:nowrap;">
                       		联系人&nbsp;: 
                        </label>
                   	</div>
                   	<div class="col-md-2">
                   		<input id="contactQuery" name="contactQuery" type="text" class="form-control" />
                   	</div>
                   	<div class="col-md-1">
                   	</div>
    				<div class="col-md-1" style="text-align:right;">
    					<label class="no-padding-right blue" for="phoneQuery" style="white-space:nowrap;">
                       		联系电话&nbsp;:
                        </label>
    				</div>
                   	<div class="col-md-2">
                   		<input id="phoneQuery" name="phoneQuery" type="text" class="form-control" />
                   	</div>
                   	<div class="col-md-1">
                   	</div>
                   	<div class="col-md-1" style="text-align:right;">
    					<label class="no-padding-right blue" for="addressQuery" style="white-space:nowrap;">
                       		联系地址&nbsp;:
                        </label>
    				</div>
                   	<div class="col-md-2">
                   		<input id="addressQuery" name="addressQuery" type="text" class="form-control" />
                   	</div>
                   	<div class="col-md-1">
                   	</div>
    			</div>
    			<div class="row form-group">
    				<div class="col-md-1" style="text-align:right;">
    					<label class="no-padding-right blue" for="companyQuery" style="white-space:nowrap;">
                       		开票公司&nbsp;:
                        </label>
    				</div>
                   	<div class="col-md-2">
                   		<input id="companyQuery" name="companyQuery" type="text" class="form-control" />
                   	</div>
                   	<div class="col-md-1">
                   	</div>
                   	<div class="col-md-1" style="text-align:right;">
                   		<label class="no-padding-right blue" for="invoiceTypeQuery" style="white-space:nowrap;">
                       		发票类型&nbsp;:
                        </label>
                   	</div>
                   	<div class="col-md-2">
                   		<select id="invoiceTypeQuery" name="invoiceTypeQuery" data-edit-select="1">
                    		<option value="">---请选择---</option>
                    		<option value="0">手写发票</option>
                    		<option value="1">机打发票</option>
                    		<option value="2">普通发票</option>
                    		<option value="3">增值税发票</option>
                    		<option value="4">其他</option>
                    	</select>
                   	</div>
                   	<div class="col-md-1">
                   	</div>
                   	<div class="col-md-1" style="text-align:right;">
    					<label class="no-padding-right blue" for="invoiceInfoQuery" style="white-space:nowrap;">
                       		发票信息&nbsp;:
                        </label>
    				</div>
                   	<div class="col-md-2">
                   		<input id="invoiceInfoQuery" name="invoiceInfoQuery" type="text" class="form-control" />
                   	</div>
                   	<div class="col-md-1">
                   	</div>
    			</div>
    			<div class="row form-group">
    				<div class="col-md-1" style="text-align:right;">
    					<label class="no-padding-right blue" for="woFlagQuery" style="white-space:nowrap;">
                       		雷达监控&nbsp;:
                        </label>
    				</div>
                   	<div class="col-md-2">
                   		<select id="woFlagQuery" name="woFlagQuery" data-edit-select="1">
                    		<option value="">---请选择---</option>
                    		<option value="0">否</option>
                    		<option value="1">是</option>
                    	</select>
                   	</div>
                   	<div class="col-md-1">
                   	</div>
    				<div class="col-md-1" style="text-align:right;">
    					<label class="no-padding-right blue" for="validityQuery" style="white-space:nowrap;">
                       		是否有效&nbsp;:
                        </label>
    				</div>
                   	<div class="col-md-2">
                   		<select id="validityQuery" name="validityQuery" data-edit-select="1">
                    		<option value="">---请选择---</option>
                    		<option value="0">无效</option>
                    		<option value="1">有效</option>
                    	</select>
                   	</div>
                   	<div class="col-md-1">
                   	</div>
    			</div>
    		</form>
    	</div>
    </div>
</div>