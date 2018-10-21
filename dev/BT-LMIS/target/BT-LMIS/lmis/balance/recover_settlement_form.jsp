<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 重新结算界面弹窗 -->
<script type="text/javascript">
	$(function(){
		$("#recoverDate").daterangepicker(null, function(start, end, label) {});
		
	})
	function submitRecoverSettlement(formId) {
		if($("input[name='ckb']:checked").length == 0){
			alert("请选择需要重新结算的合同！");
			return;
			
		}
		if(!checkValues(formId)) {
			return;
			
		}
		if(!confirm("确认提交？（注意：重新结算为重要操作，请确认无误）")) {
			return;
			
		}
		var conId =[];
		// 遍历每一个name为priv_id的复选框，其中选中的执行函数
	  	$("input[name='ckb']:checked").each(function(){
	  		// 将选中的值添加到数组priv_ids中
			conId.push($.trim($(this).val()));
	  		
	  	});
	  	loadingStyle();
		$.ajax({
			type:"POST",
	        url:"/BT-LMIS/control/recoverSettlementDataController/addRecoverTask.do",
	        dataType:"json",
	        data:{
	        	"conId":conId,
	        	"move":$("#move").val(),
	        	"recoverDate":$("#recoverDate").val()
	        	
	        },
	        success: function(result) {
	        	alert(result.result_content);
	        	// 解除旋转
	        	cancelLoadingStyle();
	        	if(result.result_code == "SUCCESS") {
	        		loadRecoverProcess();
	        		
	        	}
	        	
	        }
	        
		})
		
	}
</script>
<div id="recoverSettlement_form" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" style="width:80%;" role="document">
        <div class="modal-content" style="border:3px solid #394557;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick=""><span aria-hidden="true">×</span></button>
                <h4 class="modal-title">重新结算</h4>
            </div>
            <div class="modal-body">
				<div class="widget-box collapsed no-margin">
					<div class="widget-header header-color-blue">
						<h4>还原进程</h4>
						<div class="widget-toolbar">
							<a href="#" data-action="reload" onclick="loadRecoverProcess();">
								<i class="icon-refresh bigger-125"></i>
							</a>
							<a href="#" data-action="collapse">
								<i class="icon-chevron-down bigger-125"></i>
							</a>
						</div>
					</div>
					<div class="widget-body">
						<div id="recoverSettlementLog" class="widget-main no-padding">
							<jsp:include page="/lmis/balance/recover_settlement_log.jsp" flush="true" />
						</div>
					</div>
           		</div>
           		<hr/>
           		<form id="recoverSettlementForm" class="container">
					<div class="row">
						<div class="col-md-2" style="text-align:right;">
							<label class="no-padding-right blue" style="white-space:nowrap;">
				       	 		还原类型&nbsp;: 
				            </label>
						</div>
						<div class="col-md-3">
							<select id="move" name="move" class="form-control" checkType="{'NOTNULL': '不能为空'}" onchange="checkValue($(this));">
								<option value="">请选择</option>
								<option value="0">异常还原</option>
								<option value="1">全部还原</option>
							</select>
						</div>
						<div class="recoverCheckInfo col-md-1" validation="0">
						</div>
						<div class="col-md-2" style="text-align:right;">
							<label class="no-padding-right blue" style="white-space:nowrap;">
				       	 		还原日期区间&nbsp;: 
				            </label>
						</div>
						<div class="col-md-3 row form-group input-group">
							<input id="recoverDate" name="recoverDate" class="form-control" type="text" readonly checkType="{'NOTNULL': '不能为空'}" onblur="checkValue($(this));" />
							<span class="input-group-addon">
								<i class="icon-calendar bigger-110"></i>
							</span>
						</div>
						<div class="recoverCheckInfo col-md-1" validation="0">
						</div>
					</div>
				</form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submitRecoverSettlement('recoverSettlementForm')">
                    <i class="icon-save" aria-hidden="true"></i>提交
                </button>
            </div>
        </div>
    </div>
</div>