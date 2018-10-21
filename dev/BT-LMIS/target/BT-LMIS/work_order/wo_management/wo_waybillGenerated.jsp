<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 运单已生成工单明细界面弹窗 -->
<div id="woWaybillGenerated_form" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" style="width:100%;" role="document">
        <div class="modal-content" style="border:3px solid #394557;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="cleanWaybillGenerated();"><span aria-hidden="true">×</span></button>
                <h4 class="modal-title">工单明细</h4>
            </div>
            <div class="modal-body container-fruid">
            	<div class="row">
            		<div class="col-md-2">
	            		<table>
		            		<thead>
		            			<tr>
		            				<td>工单号</td>
		            			</tr>
		            		</thead>
		            		<tbody id="waybillGenerated" align="center">	
		            		</tbody>
		            	</table>
	            	</div>
	            	<div id="workOrderInfo" class="col-md-10">
	            	</div>
            	</div>
            </div>
        </div>
    </div>
</div>