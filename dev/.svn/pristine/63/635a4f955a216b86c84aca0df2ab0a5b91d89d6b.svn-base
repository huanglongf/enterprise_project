<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 非OMS索赔界面弹窗 -->
<div class= "modal-body container" style= "background: #F0F8FF; height: 600px; overflow-y: auto; overflow-x: hidden;" >
	<div class= "row form-group" >
		<div class= "col-xs-2" style= "text-align: right;" >
			<label id= "claim_number_label_NOMSC" class= "no-padding-right blue" for= "claim_number_NOMSC" style= "white-space: nowrap;" >
         		</label>
		</div>
		<div class= "col-xs-2" >
  			<label id= "claim_number_NOMSC" style= "white-space: nowrap;" >
         	</label>
     	</div>
    	<div class= "col-xs-1" style= "text-align: right;" >
			<label id= "claim_status_label_NOMSC" class= "no-padding-right blue" for= "claim_status_NOMSC" style= "white-space: nowrap;" >
          	</label>
		</div>
		<div class= "col-xs-2" >
      		<label id= "claim_status_NOMSC" style= "white-space: nowrap;" >
         	</label>
     	</div>
     	<div class= "col-xs-1" style= "text-align: right;" >
			<label id= "days_label_NOMSC" class= "no-padding-right blue" for= "days_NOMSC" style= "white-space: nowrap;" >
          	</label>
		</div>
		<div class= "col-xs-2" >
      		<label id= "days_NOMSC" style= "white-space: nowrap;" >
           	</label>
       	</div>
	</div>
	<div class= "row form-group" >
		<div class= "col-xs-2" style= "text-align: right;" >
			<label id= "claim_type_label_NOMSC" class= "no-padding-right blue" for= "claim_type_NOMSC" style= "white-space: nowrap;" >
          	</label>
		</div>
		<div class= "col-xs-2" >
			<select id= "claim_type_NOMSC" name= "claim_type_NOMSC" onchange= "shiftClaimType('NOMSC');" style= "width: 100%; height: 34px;"
				${type == "process" && wo_display.wo_process_status_display == "处理中" ? "": "disabled= 'disabled'" } >
				<option value= "">请选择</option>
				<option value= "1">遗失</option>
				<option value= "2">破损</option>
				<option value= "3">错发</option>
				<option value= "4">补偿</option>
			</select>
		</div>
		<div class= "col-xs-1" style= "text-align: right;" >
			<label id= "claim_reason_label_NOMSC" class= "no-padding-right blue" for= "claim_reason_NOMSC" style= "white-space: nowrap;" >
	        </label>
		</div>
		<div class= "col-xs-2" >
			<select id= "claim_reason_NOMSC" name= "claim_reason_NOMSC" style= "width: 100%; height: 34px;" disabled= "disabled" >
				<option value= "">请选择</option>
			</select>
		</div>
	</div>
	<div class= "row form-group" >
		<div class= "col-xs-2" style= "text-align: right;" >
     		<label id= "outer_damaged_flag_label_NOMSC" class= "no-padding-right blue" for= "outer_damaged_flag_NOMSC" style= "white-space: nowrap;" >
         	</label>
     	</div>
     	<div class= "col-xs-1" >
     		<input id="outer_damaged_flag_NOMSC" name="outer_damaged_flag_NOMSC" type="checkbox" class="ace ace-switch ace-switch-5"
     			${type == "process" && wo_display.wo_process_status_display == "处理中" ? "": "disabled= 'disabled'" } />
			<span class="lbl"></span>
       	</div>
        <div class= "col-xs-1" style= "text-align: right;" >
     		<label id= "repeat_box_flag_label_NOMSC" class= "no-padding-right blue" for= "repeat_box_flag_NOMSC" style= "white-space: nowrap;" >
         	</label>
     	</div>
     	<div class= "col-xs-1" >
     		<input id="repeat_box_flag_NOMSC" name="repeat_box_flag_NOMSC" type="checkbox" class="ace ace-switch ace-switch-5"
     			${type == "process" && wo_display.wo_process_status_display == "处理中" ? "": "disabled= 'disabled'" } />
			<span class="lbl"></span>
       	</div>
       	<div class= "col-xs-1" style= "text-align: right;" >
     		<label id= "return_flag_label_NOMSC" class= "no-padding-right blue" for= "return_flag_NOMSC" style= "white-space: nowrap;" >
         	</label>
     	</div>
     	<div class= "col-xs-1" >
     		<input id="return_flag_NOMSC" name="return_flag_NOMSC" type="checkbox" class="ace ace-switch ace-switch-5"
     			${type == "process" && wo_display.wo_process_status_display == "处理中" ? "": "disabled= 'disabled'" } />
			<span class="lbl"></span>
        </div>
       	<div class= "col-xs-1" style= "text-align: right;" >
     		<label id= "filling_in_box_fully_flag_label_NOMSC" class= "no-padding-right blue" for= "filling_in_box_fully_flag_NOMSC" style= "white-space: nowrap;" >
         	</label>
     	</div>
     	<div class= "col-xs-1 " >
     		<input id="filling_in_box_fully_flag_NOMSC" name="filling_in_box_fully_flag_NOMSC" type="checkbox" class="ace ace-switch ace-switch-5"
     			${type == "process" && wo_display.wo_process_status_display == "处理中" ? "": "disabled= 'disabled'" } />
			<span class="lbl"></span>
        </div>
        <div class= "col-xs-1" style= "text-align: right;" >
     		<label id= "package_damaged_flag_label_NOMSC" class= "no-padding-right blue" for= "package_damaged_flag_NOMSC" style= "white-space: nowrap;" >
         	</label>
     	</div>
     	<div class= "col-xs-1" >
     		<input id="package_damaged_flag_NOMSC" name="package_damaged_flag_NOMSC" type="checkbox" class="ace ace-switch ace-switch-5"
   				${type == "process" && wo_display.wo_process_status_display == "处理中" ? "": "disabled= 'disabled'" } />
			<span class="lbl"></span>
       	</div>
	</div>
    <div class= "row form-group" >
   		<div class= "col-xs-2" style= "text-align: right;" >
			<label id= "added_value_label_NOMSC" class= "no-padding-right blue" for= "added_value_NOMSC" style= "white-space: nowrap;" >
         	</label>
		</div>
     	<div class= "col-xs-2" >
     		<input id= "added_value_NOMSC" name= "added_value_NOMSC" class= "form-control" type= "text" onblur= "calValueAll()" onkeyup= "value=value.replace(/[^\-?\d.]/g,'')"
     			${type == "process" && wo_display.wo_process_status_display == "处理中" ? "": "disabled= 'disabled'" } />
     	</div>
     	<div class= "col-xs-2" style= "text-align: right;" >
     		<label id= "total_applied_claim_amount_label_NOMSC" class= "no-padding-right blue" for= "total_applied_claim_amount_NOMSC" style= "white-space: nowrap;" >
           	</label>
       	</div>
       	<div class= "col-xs-2" >
       		<input id= "total_applied_claim_amount_NOMSC" name= "total_applied_claim_amount_NOMSC" class= "form-control" type= "text" disabled />
       	</div>           
	</div>
	<div class= "row form-group" >
   		<div class= "col-xs-2" style= "text-align: right;">
        	<label id= "store_remark_label_NOMSC" class= "no-padding-right blue" for= "store_remark_NOMSC" style= "white-space: nowrap;" ></label>
     	</div>
     	<div class="col-xs-9">
        	<textarea id="store_remark_NOMSC" name="store_remark_NOMSC" class="form-control" style="height: 150px;" cols="30" placeholder="最大长度200"
       			${type == "process" && wo_display.wo_process_status_display == "处理中" ? "": "disabled= 'disabled'" } ></textarea>
   		</div>
   	</div>
	<hr />
	<div class= "row form-group" >
		<div class= "col-xs-2" style= "text-align: right;" >
			<label class= "no-padding-right blue" style= "white-space: nowrap;" >
				赔偿责任方&nbsp;:
          	</label>
		</div>
		<div class= "col-xs-2" style= "text-align: right;" >
	  		<label id= "logistics_department_claim_flag_label_NOMSC" class= "no-padding-right blue" for= "logistics_department_claim_flag_NOMSC" style= "white-space: nowrap;" >
	        </label>
    	</div>
	    <div class= "col-xs-1 " >
	  		<input id= "logistics_department_claim_flag_NOMSC" name= "logistics_department_claim_flag_NOMSC" type= "checkbox" class= "ace ace-switch ace-switch-5" onchange= "shiftPayFlag('NOMSC', 'logistics_department_claim_flag')"
	   			${type == "process" && wo_display.wo_process_status_display == "处理中" ? "": "disabled= 'disabled'" } />
			<span class="lbl"></span>
	    </div>
	    <div class= "col-xs-2" style= "text-align: right;" >
	   		<label id= "logistics_department_claim_amount_label_NOMSC" class= "no-padding-right blue" for= "logistics_department_claim_amount_NOMSC" style= "white-space: nowrap;" >
	       	</label>
	    </div>
	    <div class= "col-xs-2" >
	  		<input id= "logistics_department_claim_amount_NOMSC" name= "logistics_department_claim_amount_NOMSC" class= "form-control" type= "text" disabled= "disabled"  onkeyup= "value=value.replace(/[^\-?\d.]/g,'')" placeholder= "最大长度10" />
	    </div>
	  
	</div>
	<div class= "row form-group" >
		<div class= "col-xs-2" >
		</div>
		<div class= "col-xs-2" style= "text-align: right;" >
     		<label id= "transport_vendor_claim_flag_label_NOMSC" class= "no-padding-right blue" for= "transport_vendor_claim_flag_NOMSC" style= "white-space: nowrap;" >
         	</label>
    	</div>
     	<div class= "col-xs-1 " >
     		<input id="transport_vendor_claim_flag_NOMSC" name="transport_vendor_claim_flag_NOMSC" type="checkbox" class="ace ace-switch ace-switch-5" onchange= "shiftPayFlag('NOMSC', 'transport_vendor_claim_flag')"
     			${type == "process" && wo_display.wo_process_status_display == "处理中" ? "": "disabled= 'disabled'" } />
			<span class="lbl"></span>
        </div>
        <div class= "col-xs-2" style= "text-align: right;" >
     		<label id= "transport_vendor_claim_amount_label_NOMSC" class= "no-padding-right blue" for= "transport_vendor_claim_amount_NOMSC" style= "white-space: nowrap;" >
         	</label>
     	</div>
     	<div class= "col-xs-2" >
     		<input id="transport_vendor_claim_amount_NOMSC" name="transport_vendor_claim_amount_NOMSC" class= "form-control" type="text" disabled= "disabled" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" placeholder= "最大长度10" />
       	</div>
	</div>
	
	
	
	
		<div class= "row form-group" >
		<div class= "col-xs-2" >
		</div>
		<div class= "col-xs-2" style= "text-align: right;" >
     		<label id= "isbj_label_NOMSC" class= "no-padding-right blue" for= "isbj_NOMSC" style= "white-space: nowrap;" >
         	</label>
    	</div>
     	<div class= "col-xs-1 " >
     		<input id="isbj_NOMSC" name="isbj_NOMSC" type="checkbox" class="ace ace-switch ace-switch-5"
     			${type == "process" && wo_display.wo_process_status_display == "处理中" ? "": "disabled= 'disabled'" } />
			<span class="lbl"></span>
        </div>
        <div class= "col-xs-2" style= "text-align: right;" >
     		<label id= "result_label_NOMSC" class= "no-padding-right blue" for= "result_NOMSC" style= "white-space: nowrap;" >
         	</label>
     	</div>
     	<div class= "col-xs-2" >
     		<input id="result_NOMSC" name="result_NOMSC" class= "form-control" type="text" disabled= "disabled"  placeholder= "最大长度10" />
       	</div>
	</div>

	<div class= "row form-group" >
		<div class= "col-xs-2" style= "text-align: right;" >
			<label id= "resultxf_label_NOMSC" class= "no-padding-right blue" for= "resultxf_NOMSC" style= "white-space: nowrap;" >
			</label>
		</div>
		<div class= "col-xs-3 " >
			<input id="resultxf_NOMSC" name="resultxf_NOMSC" class= "form-control" type="text" disabled= "disabled"  placeholder= "最大长度10" />
		</div>
		<div class= "col-xs-2" style= "text-align: right;" >
			<label id= "zrgs_label_NOMSC" class= "no-padding-right blue" for= "zrgs_NOMSC" style= "white-space: nowrap;" >
			</label>
		</div>
		<div class= "col-xs-3" >
			<input id="zrgs_NOMSC" name="zrgs_NOMSC" class= "form-control" type="text" disabled= "disabled"  placeholder= "最大长度10" />
		</div>
	</div>

	<div class= "row form-group" >
		<div class= "col-xs-2" style= "text-align: right;" >
			<label id= "ps_label_NOMSC" class= "no-padding-right blue" for= "ps_NOMSC" style= "white-space: nowrap;" >
			</label>
		</div>
		<div class= "col-xs-8 " >
			<input id="ps_NOMSC" name="ps_NOMSC" class= "form-control" type="text" disabled= "disabled"  placeholder= "请输入" />
		</div>

	</div>
	
	<hr />
	<div class= "row form-group" >
		<div class= "col-xs-2" style= "text-align: right;" >
			<label class= "no-padding-right blue" style= "white-space: nowrap;" >
				索赔明细&nbsp;:
            </label>
		</div>
		<div class= "col-xs-9" >
			<div class= "title_div" id= "sc_title_NOMSC" >		
				<table class= "table table-striped" style= "table-layout: fixed;" >
					<thead>
						<tr class= "table_head_line">
							<td class= "td_cs" title= "SKU编码" >SKU编码</td>
							<td class= "td_cs" title= "商品名称" >商品名称</td>
							<td class= "td_cs" title= "商品数量" >商品数量</td>
							<td class= "td_cs" title= "购买单价" >购买单价</td>
							<td class= "td_cs" title= "购买金额" >购买金额</td>
							<td class= "td_cs" title= "申请索赔数量" >申请索赔数量</td>
							<td class= "td_cs" title= "申请理赔单价" >申请索赔单价</td>
							<td class= "td_cs" title= "申请索赔总金额" >申请索赔总金额</td>
							<td id="op_NOMSC" ${type == "process" && wo_display.wo_process_status_display == "处理中" ? "": "style= 'display: none;'" } class= "td_cs" title= "操作" >操作</td>
			  			</tr>  	
					</thead>
				</table>
			</div>
			<div class= "tree_div" ></div>
			<div style= "height: 150px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content_NOMSC" onscroll= "init_td('sc_title_NOMSC', 'sc_content_NOMSC')" >
				<table class= "table table-hover table-striped" style= "table-layout: fixed;" >
 						<tbody id= "claim_detail_NOMSC" align= "center" ></tbody>
				</table>
			</div>
			<div style= "float: right" >
				<button id="NOMSCadd" ${type == "process" && wo_display.wo_process_status_display == "处理中"? "": "style= 'display: none;'" } type= "button" class= "btn btn-sm btn-primary" onclick= "claimDetailAddForm('${wo_display.express_number }');" >
             		<i class= "icon-plus" aria-hidden= "true" ></i>新增
             	</button>
			</div>
  		</div>
	</div>
	<div class= "row form-group" >
		<div class= "col-xs-2" style= "text-align: right;" >
			<label id= "remark_label_NOMSC" class= "no-padding-right blue" for= "remark_NOMSC" style= "white-space: nowrap;" >
			</label>
		</div>
		<div class= "col-xs-9" >
			<textarea id= "remark_NOMSC" name= "remark_NOMSC" class= "form-control" style= "height: 150px;" cols= "30" placeholder= "最大长度200"  disabled= "disabled" } ></textarea>
		</div>
	</div>
	<div class= "row form-group" >
      	<div class= "col-xs-2" style= "text-align: right;" >
        	<label id= "remark_new_label_NOMSC" class= "no-padding-right blue" for= "remark_new_NOMSC" style= "white-space: nowrap;" >
         	</label>
     	</div>
    	<div class= "col-xs-9" >
    		<select id= "remark_new_NOMSC" name= "remark_new_NOMSC" class= "form-control"  cols= "30"  ${type == "process" && wo_display.wo_process_status_display == "处理中" ? "": "disabled= 'disabled'" } >
				<option value= "">请选择</option>
			</select>
        </div>
	</div>
    <div class= "row form-group" >
		<div class= "col-xs-2" style= "text-align: right;" >
			<label id= "fileName_label_NOMSC" class= "no-padding-right blue" style= "white-space: nowrap;" >
           	</label>
       	</div>
		<div class= "col-xs-9">
			<NOMSC_UPLOAD></NOMSC_UPLOAD>
       	</div>
  	</div>
</div>       
<div id="function_buttion" class="modal-footer" style="display:none;">
    <button style="display:none;" id="save_NOMSC" type="button" class="btn btn-primary" onclick="operation('${wo_display.woType }', 0);">
    	<i class="icon-save" aria-hidden="true"></i>保存
	</button>
	<button style="display: none;" id="process_NOMSC" type="button" class="btn btn-inverse" onclick="operation('${wo_display.woType }', 2);">
    	<i class="icon-tasks" aria-hidden="true"></i>处理索赔
	</button>
	<button style="display: none;" id="success_NOMSC" type="button" class="btn btn-success" onclick="operation('${wo_display.woType }', 3);">
    	<i class="icon-check" aria-hidden= "true" ></i>索赔成功
	</button>
	<button style="display: none;" id="failure_NOMSC" type="button" class="btn btn-danger" onclick="$('#import_form').modal({backdrop: 'static', keyboard: false});$('.modal-backdrop').hide();">
    	<i class="icon-lock" aria-hidden="true"></i>索赔失败
    </button>
</div>
<div id="import_form" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
	
		<input id="file1Path" type="hidden" name='file1Path' />
		<input id="file2Path" type="hidden" name='file2Path' />
	
	    <div class="modal-dialog modal-lg" style="width:800px;" role="document">
	        <div class="modal-content">
	            <div class="modal-header">
	            	<button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick= ""><span aria-hidden="true">×</span></button>
	                <h4 class="modal-title">选择索赔失败原因</h4>
	            </div>
	            <div class="modal-body container">
					<table align="center">
						<tr align="center">
							<td align="center">
								<label class="blue" >
									失败原因:
								</label>
							</td>
							<td align="center">
								
			                    	<select id= "failure_reason" name= "failure_reason" data-edit-select= "1" >
			                            <option value= "" >---请选择---</option>
			                            <c:forEach items= "${failureReason }" var= "item" >
			                                <option value= "${item.reason }" >${item.reason}</option>
			                            </c:forEach>
			                        </select>
			                 
							</td>			
						</tr>				
					</table>
	            </div>
	            <div class="modal-footer">
	                <button id='upload' type="button" class="btn btn-primary" onclick="saveFailureReason('${wo_display.woType }', 4);" >
	                    <i class="icon-save" aria-hidden="true"></i>确认
	                </button>
	              
	            </div>
	        </div>
	    </div>
	</div>
<script >
function saveFailureReason(wotype,type) {
    var failReason = $("#failure_reason").val();
    if(failReason==null||failReason==""){
        alert("请选择失败原因");
        return;
}
operation(wotype, type);
}
</script>