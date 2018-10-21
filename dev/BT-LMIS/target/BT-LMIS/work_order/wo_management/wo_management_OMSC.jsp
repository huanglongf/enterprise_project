<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- OMS索赔界面弹窗 -->
<div class= "modal-body container" style= "background: #F0F8FF; height: 600px; overflow-y: auto; overflow-x: hidden;" >
	<div class= "row form-group" >
		<div class= "col-xs-2" style= "text-align: right;" >
			<label id= "claim_number_label_OMSC" class= "no-padding-right blue" for= "claim_number_OMSC" style= "white-space: nowrap;" >
          	</label>
		</div>
		<div class= "col-xs-2" >
       		<label id= "claim_number_OMSC" style= "white-space: nowrap;" >
			</label>
        </div>
        <div class= "col-xs-1" style= "text-align: right;" >
			<label id= "claim_status_label_OMSC" class= "no-padding-right blue" for= "claim_status_OMSC" style= "white-space: nowrap;" >
            </label>
		</div>
		<div class= "col-xs-2" >
       		<label id= "claim_status_OMSC" style= "white-space: nowrap;" >
			</label>
		</div>
		<div class= "col-xs-1" style= "text-align: right;" >
			<label id= "days_label_OMSC" class= "no-padding-right blue" for= "days_OMSC" style= "white-space: nowrap;" >
           	</label>
		</div>
		<div class= "col-xs-2" >
       		<label id= "days_OMSC" style= "white-space: nowrap;" >
            </label>
       	</div>
	</div>
	<div class= "row form-group" >
		<div class= "col-xs-2" style= "text-align: right;" >
			<label id= "claim_type_label_OMSC" class= "no-padding-right blue" for= "claim_type_OMSC" style= "white-space: nowrap;" >
            </label>
		</div>
		<div class= "col-xs-2" >
			<label id= "claim_type_OMSC" style= "white-space: nowrap;" >
        	</label>
		</div>
		<div class= "col-xs-1" style= "text-align: right;" >
			<label id= "claim_reason_label_OMSC" class= "no-padding-right blue" for= "claim_reason_OMSC" style= "white-space: nowrap;" >
           	</label>
		</div>
		<div class= "col-xs-6" >
			<label id= "claim_reason_OMSC" style= "white-space: nowrap;" >
           	</label>
		</div>
	</div>
	<div class= "row form-group" >
		<div class= "col-xs-2" style= "text-align: right;" >
       		<label id= "outer_damaged_flag_label_OMSC" class= "no-padding-right blue" for= "outer_damaged_flag_OMSC" style= "white-space: nowrap;" >
            </label>
		</div>
        <div class= "col-xs-1" >
       		<input id="outer_damaged_flag_OMSC" name="outer_damaged_flag_OMSC" type="checkbox" class="ace ace-switch ace-switch-5" disabled="disabled" />
			<span class="lbl"></span>
		</div>
		<div class= "col-xs-1" style= "text-align: right;" >
       		<label id= "repeat_box_flag_label_OMSC" class= "no-padding-right blue" for= "repeat_box_flag_OMSC" style= "white-space: nowrap;" >
            </label>
		</div>
		<div class= "col-xs-1" >
       		<input id="repeat_box_flag_OMSC" name="repeat_box_flag_OMSC" type="checkbox" class="ace ace-switch ace-switch-5" disabled="disabled" />
			<span class="lbl"></span>
		</div>
		<div class= "col-xs-1" style= "text-align: right;" >
       		<label id= "return_flag_label_OMSC" class= "no-padding-right blue" for= "return_flag_OMSC" style= "white-space: nowrap;" >
            </label>
		</div>
		<div class= "col-xs-1" >
			<input id="return_flag_OMSC" name="return_flag_OMSC" type="checkbox" class="ace ace-switch ace-switch-5" disabled="disabled" />
			<span class="lbl"></span>
		</div>
        <div class= "col-xs-1" style= "text-align: right; width: 100px;" >
			<label id= "filling_in_box_fully_flag_label_OMSC" class= "no-padding-right blue" for= "filling_in_box_fully_flag_OMSC" style= "white-space: nowrap;" >
			</label>
		</div>
		<div class= "col-xs-1 " >
			<input id="filling_in_box_fully_flag_OMSC" name="filling_in_box_fully_flag_OMSC" type="checkbox" class="ace ace-switch ace-switch-5" disabled="disabled" />
			<span class="lbl"></span>
		</div>
		<div class= "col-xs-1" style= "text-align: right; width: 100px;" >
        	<label id= "package_damaged_flag_label_OMSC" class= "no-padding-right blue" for= "package_damaged_flag_OMSC" style= "white-space: nowrap;" >
            </label>
		</div>
		<div class= "col-xs-1" >
			<input id="package_damaged_flag_OMSC" name="package_damaged_flag_OMSC" type="checkbox" class="ace ace-switch ace-switch-5" disabled="disabled" />
			<span class="lbl"></span>
		</div>
	</div>
	<div class= "row form-group" >
		<div class= "col-xs-2" style= "text-align: right;" >
			<label id= "added_value_label_OMSC" class= "no-padding-right blue" for= "added_value_OMSC" style= "white-space: nowrap;" >
			</label>
    	</div>
    	<div class= "col-xs-2" >
        	<label id= "added_value_OMSC" style= "white-space: nowrap;" >
			</label>
		</div>
		<div class= "col-xs-2" style= "text-align: right;" >
			<label id= "added_value_remark_label_OMSC" class= "no-padding-right blue" for= "added_remark_value_OMSC" style= "white-space: nowrap;" >
			</label>
    	</div>
    	<div class= "col-xs-2" >
        	<label id= "added_value_remark_OMSC" style= "white-space: nowrap;" >
			</label>
		</div>
    </div>
    <div class= "row form-group" >
    	<div class= "col-xs-2" style= "text-align: right;" >
			<label id= "total_applied_claim_amount_label_OMSC" class= "no-padding-right blue" for= "total_applied_claim_amount_OMSC" style= "white-space: nowrap;" >
			</label>
		</div>
		<div class= "col-xs-2" >
    		<label id= "total_applied_claim_amount_OMSC" style= "white-space: nowrap;" >
            </label>
    	</div>
    	<div class= "col-xs-2" style= "text-align: right;" >
			<label id= "oms_attachment_url_label_OMSC" class= "no-padding-right blue" style= "white-space: nowrap;" >
           	</label>
       	</div>
       	<div class= "col-xs-6" >
       		<a id= "oms_attachment_url_OMSC" href= "" download= "" ></a>
       	</div>
    </div>
	<div class= "row form-group" >
		<div class= "col-xs-2" style= "text-align: right;">
        	<label id= "store_remark_label_OMSC" class= "no-padding-right blue" for= "store_remark_OMSC" style= "white-space: nowrap;" ></label>
        </div>
       	<div class="col-xs-9">
            <label id= "store_remark_OMSC" style= "white-space: nowrap;" >
            </label>
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
			<label id= "logistics_department_claim_flag_label_OMSC" class= "no-padding-right blue" for= "logistics_department_claim_flag_OMSC" style= "white-space: nowrap;" >
			</label>
		</div>
		<div class= "col-xs-1 " >
			<input id= "logistics_department_claim_flag_OMSC" name= "logistics_department_claim_flag_OMSC" type= "checkbox" class= "ace ace-switch ace-switch-5" onchange= "shiftPayFlag('OMSC', 'logistics_department_claim_flag')"
	   			${type == "process" && wo_display.wo_process_status_display == "处理中" ? "": "disabled= 'disabled'" } />
			<span class="lbl"></span>
	    </div>
		<div class= "col-xs-2" style= "text-align: right;" >
			<label id= "logistics_department_claim_amount_label_OMSC" class= "no-padding-right blue" for= "logistics_department_claim_amount_OMSC" style= "white-space: nowrap;" >
	    	</label>
		</div>
		<div class= "col-xs-2" >
			<input id="logistics_department_claim_amount_OMSC" name="logistics_department_claim_amount_OMSC" class= "form-control" type="text" disabled= "disabled" onkeyup= "value=value.replace(/[^\-?\d.]/g,'')" placeholder= "最大长度10" />
		</div>
	</div>
	<div class= "row form-group" >
		<div class= "col-xs-2" >
		</div>
		<div class= "col-xs-2" style= "text-align: right;" >
			<label id= "transport_vendor_claim_flag_label_OMSC" class= "no-padding-right blue" for= "transport_vendor_claim_flag_OMSC" style= "white-space: nowrap;" >
	    	</label>
		</div>
		<div class= "col-xs-1 " >
			<input id="transport_vendor_claim_flag_OMSC" name="transport_vendor_claim_flag_OMSC" type="checkbox" class="ace ace-switch ace-switch-5" onchange= "shiftPayFlag('OMSC', 'transport_vendor_claim_flag')"
				${type == "process" && wo_display.wo_process_status_display == "处理中" ? "": "disabled= 'disabled'" } />
			<span class="lbl"></span>
		</div>
		<div class= "col-xs-2" style= "text-align: right;" >
			<label id= "transport_vendor_claim_amount_label_OMSC" class= "no-padding-right blue" for= "transport_vendor_claim_amount_OMSC" style= "white-space: nowrap;" >
	    	</label>
		</div>
		<div class= "col-xs-2" >
			<input id="transport_vendor_claim_amount_OMSC" name="transport_vendor_claim_amount_OMSC" class= "form-control" type="text" disabled= "disabled" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" placeholder= "最大长度10" />
		</div>
		<div class= "OMSCCheckInfo col-xs-2" >
		</div>
	</div>


	<div class= "row form-group" >
		<div class= "col-xs-2" >
		</div>
		<div class= "col-xs-2" style= "text-align: right;" >
			<label id= "isbj_label_OMSC" class= "no-padding-right blue" for= "isbj_OMSC" style= "white-space: nowrap;" >
			</label>
		</div>
		<div class= "col-xs-1 " >
			<input id="isbj_OMSC" name="isbj_OMSC" type="checkbox" class="ace ace-switch ace-switch-5" onchange= "shiftPayFlag('NOMSC', 'transport_vendor_claim_flag')"
			${type == "process" && wo_display.wo_process_status_display == "处理中" ? "": "disabled= 'disabled'" } />
			<span class="lbl"></span>
		</div>
		<div class= "col-xs-2" style= "text-align: right;" >
			<label id= "result_label_NOMSC" class= "no-padding-right blue" for= "result_OMSC" style= "white-space: nowrap;" >
			</label>
		</div>
		<div class= "col-xs-2" >
			<input id="result_OMSC" name="result_OMSC" class= "form-control" type="text" disabled= "disabled" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" placeholder= "最大长度10" />
		</div>
	</div>

	<div class= "row form-group" >
		<div class= "col-xs-2" style= "text-align: right;" >
			<label id= "resultxf_label_OMSC" class= "no-padding-right blue" for= "resultxf_OMSC" style= "white-space: nowrap;" >
			</label>
		</div>
		<div class= "col-xs-3 " >
			<input id="resultxf_OMSC" name="resultxf_NOMSC" class= "form-control" type="text" disabled= "disabled" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" placeholder= "最大长度10" />
		</div>
		<div class= "col-xs-2" style= "text-align: right;" >
			<label id= "zrgs_label_OMSC" class= "no-padding-right blue" for= "zrgs_OMSC" style= "white-space: nowrap;" >
			</label>
		</div>
		<div class= "col-xs-3" >
			<input id="zrgs_OMSC" name="zrgs_OMSC" class= "form-control" type="text" disabled= "disabled" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" placeholder= "最大长度10" />
		</div>
	</div>

	<div class= "row form-group" >
		<div class= "col-xs-2" style= "text-align: right;" >
			<label id= "ps_label_OMSC" class= "no-padding-right blue" for= "ps_OMSC" style= "white-space: nowrap;" >
			</label>
		</div>
		<div class= "col-xs-8 " >
			<input id="ps_OMSC" name="ps_NOMSC" class= "form-control" type="text" disabled= "disabled" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" placeholder= "请输入" />
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
			<div class= "title_div" id= "sc_title_OMSC" >		
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
	  					</tr>  	
					</thead>
				</table>
			</div>
			<div class= "tree_div" ></div>
			<div style= "height: 150px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content_OMSC" onscroll= "init_td('sc_title_OMSC', 'sc_content_OMSC')" >
				<table class= "table table-hover table-striped" style= "table-layout: fixed;" >
			  		<tbody id= "claim_detail_OMSC" align= "center" >
			  		</tbody>
				</table>
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
			<label id= "fileName_label_OMSC" class= "no-padding-right blue" style= "white-space: nowrap;" >
           	</label>
       	</div>
		<div class= "col-xs-9" >
			<OMSC_UPLOAD></OMSC_UPLOAD>
		</div>
	</div>
</div>
<div id="function_buttion" class="modal-footer" style="display:none;">
	<button style="display:none;" id="save_OMSC" type="button" class="btn btn-primary" onclick="operation('${wo_display.woType }', 0);">
		<i class="icon-save" aria-hidden="true" ></i>保存
	</button>
	<button style="display: none;" id="process_OMSC" type="button" class="btn btn-inverse" onclick="operation('${wo_display.woType }', 2);">
		<i class="icon-tasks" aria-hidden="true" ></i>处理索赔
	</button>
	<button style="display: none;" id="success_OMSC" type="button" class="btn btn-success" onclick="operation('${wo_display.woType }', 3);">
		<i class="icon-check" aria-hidden="true" ></i>索赔成功
	</button>
	<button style="display: none;" id="failure_OMSC" type="button" class="btn btn-danger" onclick="$('#import_form').modal({backdrop: 'static', keyboard: false});$('.modal-backdrop').hide();">
		<i class="icon-lock" aria-hidden="true" ></i>索赔失败
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
</script >