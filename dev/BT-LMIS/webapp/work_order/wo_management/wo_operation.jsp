<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div>
	<br>
	<div class="row form-group">
		<div class="col-xs-3"></div>
		<div class="col-xs-3" ${type == "process" && wo_display.wo_process_status_display == "处理中" ? "": "style= 'display: none;'" }>
			<button id= "btn_alter" class= "btn btn-white" style= "width: 100%;" onclick= "$('#alter_form').modal({backdrop: 'static', keyboard: false});" ><i class= "icon-edit" ></i>工单异动</button>
		</div>
		<div class="col-xs-3">
		   <button id="btn_detail" class="btn btn-white" style="width:100%;" onclick="toOperateForm('${wo_display.id }','${wo_display.woType }');"><i class="icon-edit"></i>处理界面</button>
		</div>
		<div class="col-xs-3"></div>
	</div>
		<%-- <iframe id="timeline" src="toTimeLine.do?woId=${wo_display.wo_no }" style="border:0px;padding:0px;width:100%;height:1000px;overflow:visible;"></iframe> --%>
	<jsp:include page="/work_order/wo_management/mutual_log.jsp" flush="true" /> 
	<br>
	<br>
</div>
