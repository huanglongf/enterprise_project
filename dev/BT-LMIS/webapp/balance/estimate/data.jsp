<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
<div id="data" class="form-group">
	<div class="title_div" id="sc_title">
		<table class="table table-striped" style="table-layout:fixed;">
	   		<thead>
	  			<tr class="table_head_line">
	  				<td class="td_cs" title="预估批次">预估批次</td>
	  				<td class="td_cs" title="预估状态">预估状态</td>
	  				<td class="td_cs" title="前方排队数">前方排队数</td>	  			
	  				<td class="td_cs" title="时间范围">时间范围</td>	  			
	  				<td class="td_cs" title="预估类型">预估类型</td>	  			
	  				<td class="td_cs" title="包含合同">包含合同</td>
	  				<td class="td_cs" title="备注">备注</td>
	  				<td class="td_cs" title="操作">操作</td>
		  		</tr>
		  	</thead>
	 	</table>
	</div>
	<div class="tree_div"></div>
	<div style="height:400px; overflow:auto; overflow:scroll; border:solid #F2F2F2 1px;" id="sc_content" onscroll="init_td('sc_title','sc_content')">
		<table id="table_content" class="table table-hover table-striped" style="table-layout:fixed;">
	  		<tbody align="center">
	  			<c:forEach items="${pageView.records }" var="pageView">
			  		<tr style="cursor:pointer;">
			  			<td class="td_cs" title="${pageView.batch_number }">${pageView.batch_number }</td>
			  			<td class="td_cs" title="${pageView.batch_status }">${pageView.batch_status }</td>
			  			<td class="td_cs" title="${pageView.rank }">${pageView.rank }</td>
			  			<td class="td_cs" title="${pageView.domain }">${pageView.domain }</td>
			  			<td class="td_cs" title="${pageView.estimate_type }">${pageView.estimate_type }</td>
			  			<td class="td_cs" title="${pageView.contract }">${pageView.contract }</td>
			  			<td class="td_cs" title="${pageView.remark }">${pageView.remark }</td>
			  			<td class="td_cs">
			  				<button ${pageView.batch_status=='已完成'?'':'style="display:none"' } class="btn btn-xs btn-primary" onclick="download('${pageView.batch_number }');"><i class="icon-arrow-down"></i>下载</button>
			  				<button ${pageView.batch_status=='已完成' || pageView.batch_status=='已取消' || pageView.batch_status=='异常待处理'?'':'style="display:none"' } class="btn btn-xs btn-danger" onclick="shiftEstimateStatus('delete','${pageView.batch_number }');"><i class="icon-trash"></i>删除</button>
			  				<button ${pageView.batch_status=='等待中'?'':'style="display:none"' } class="btn btn-xs btn-inverse" onclick="shiftEstimateStatus('cancel','${pageView.batch_number }');"><i class="icon-stop"></i>取消</button>
			  				<button ${pageView.batch_status=='已取消' || pageView.batch_status=='异常待处理'?'':'style="display:none"' } class="btn btn-xs btn-success" onclick="shiftEstimateStatus('restart','${pageView.batch_number }');"><i class="icon-play"></i>重启</button>
			  			</td>
			  		</tr>
		  		</c:forEach>
	  		</tbody>
	  	</table>
 	</div>
	<div style="margin-right:1%; margin-top:20px;">${pageView.pageView }</div>
</div>