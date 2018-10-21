<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 结算预估新增弹窗 -->
<div id="add_Form" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" style="width:80%;" role="document">
        <div class="modal-content" style="border:3px solid #394557;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick= "cleanAddForm();"><span aria-hidden="true">×</span></button>
                <h4 class="modal-title">结算预估新增</h4>
            </div>
            <div class="modal-body container">
            	<form id="addForm">
            		<div class="row form-group">
	                	<div class="col-xs-1" style="text-align:right;">
	                        <label class="no-padding-right blue" for="dateDomain" style="white-space: nowrap;">
                            	时间范围&nbsp;:
	                        </label>
	                    </div>
	                    <div class="col-md-3 form-group input-group">
	                    	<input id="dateDomain" name="dateDomain" class="form-control" type="text" readonly checkType="{'NOTNULL':'不能为空' }" onblur="checkValue($(this));" />
							<span class="input-group-addon">
								<i class="icon-calendar bigger-110"></i>
							</span>
	                    </div>
	                    <div class="addCheckInfo col-md-2" validation="0">
	                    </div>
	                    <div class="col-md-1" style="text-align:right;">
	                    	<label class="no-padding-right blue" for="estimateType" style="white-space:nowrap;">
                  				预估类型&nbsp;: 
	                        </label>
	                    </div>
	                    <div class="col-md-3">
	                    	<select id="estimateType" name="estimateType" data-edit-select="1" onchange="checkValue($(this));shiftContractListByType();"
	                    		checkType="{'NOTNULL': '不能为空'}" >
	                    		<option value="">---请选择---</option>
	                    		<option value="0">支出</option>
	                    		<option value="1">收入</option>
	                    	</select>
	                    </div>
	                    <div class="addCheckInfo col-md-2" validation="0">
	                    </div>
	                </div>
	                <div id="contractList" class="row form-group" style="display:none;">
	                	<div class="col-md-1" style="text-align:right;">
							<label class="no-padding-right blue" style="white-space:nowrap;">
								合同列表&nbsp;:
							</label>
						</div>
						<div class="col-md-3">
							<div class="title_div" id="sc_title_contract">		
								<table class="table table-striped" style="table-layout: fixed;">
									<thead>
										<tr class= "table_head_line">
											<td class="td_ch" ><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')" /></td>
								  			<td class="td_cs" title="合同名称"><input id="contractNameParam" onkeydown="queryContract(window.event);" onblur="queryContract(null)" placeholder="合同名称" /></td>
										</tr>
									</thead>
								</table>
							</div>
							<div class="tree_div"></div>
							<div style="height:300px;overflow:auto;overflow:scroll;border:solid #F2F2F2 1px;" id="sc_content_contract" onscroll="init_td('sc_title_contract','sc_content_contract')">
								<jsp:include page="/balance/estimate/contract.jsp" flush="true" />
							</div>
						</div>
						<div class="col-md-1">
							<div style="margin-top:133px">
								<button type="button" class="form-control btn btn-white btn-sm" onclick="chooseContract();">
									<i class= "icon-angle-right bigger-200" ></i>
									<i class= "icon-angle-right bigger-200" ></i>
								</button>
							</div>
							<div>
								<button type="button" class="form-control btn btn-white btn-sm" onclick="removeContract();">
									<i class="icon-angle-left bigger-200"></i>
									<i class="icon-angle-left bigger-200"></i>
								</button>
							</div>
						</div>
						<div class="col-md-3">
							<div class="title_div" id="sc_title_contract2">		
								<table class="table table-striped" style="table-layout:fixed;">
									<thead>
										<tr class="table_head_line">
											<td class="td_ch"><input type="checkbox" id="checkAll2" onclick="inverseCkb('ckb2')" /></td>
								  			<td class="td_cs" title="已选择合同">已选择合同</td>
										</tr>
									</thead>
								</table>
							</div>
							<div class="tree_div"></div>
							<div style="height:300px;overflow:auto;overflow:scroll;border:solid #F2F2F2 1px;" id="sc_content_contract2" onscroll="init_td('sc_title_contract2','sc_content_contract2')">
								<table class="table table-hover table-striped" style="table-layout:fixed;">
									<tbody id="choosenContract" align="center">
									</tbody>
								</table>
							</div>
						</div>
	                </div>
	                <div class="row form-group">
	                	<div class="col-md-1" style="text-align: right;">
	                        <label class="no-padding-right blue" for="remark" style="white-space: nowrap;">
                          		备注&nbsp;:
	                        </label>
	                    </div>
	                    <div class="col-md-9">
	                        <textarea id="remark" name="remark" class= "form-control" style="height: 150px;" cols="30" placeholder="最大长度120"
	                        	checkType="{'LENGTH':'长度不合法'}" validate-param="{'MAX': 120}" oninput="checkValue($(this));"></textarea>
	                    </div>
	                    <div class="addCheckInfo col-md-2" validation="0">
	                    </div>
	                </div>
            	</form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="cleanAddForm();">
                    <i class="icon-undo" aria-hidden="true"></i>返回
                </button>
                <button type="button" class="btn btn-primary" onclick="add();" >
                    <i class="icon-save" aria-hidden="true"></i>新增
                </button>
            </div>
        </div>
    </div>
</div>