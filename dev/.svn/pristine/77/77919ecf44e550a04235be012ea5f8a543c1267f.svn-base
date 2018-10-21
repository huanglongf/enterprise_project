<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 工单处理界面弹窗 -->
<div id="${wo_display.woType }_form" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" style="width:80%;" role="document">
        <div class="modal-content" style="border:3px solid #394557;">
			<div class="modal-header">
                <button type="button" class="close" aria-label="Close" onclick="$('#${wo_display.woType }_form').modal('hide');"><span aria-hidden="true">×</span></button>
                <h4 class="modal-title">${wo_display.wo_type_display }</h4>
            </div>
            <jsp:include page="/work_order/wo_management/wo_management_${wo_display.woType != 'OMSC' &&  wo_display.woType != 'NOMSC'?'COMMON': wo_display.woType }.jsp" flush="true" />
    	</div>
    </div>
</div>