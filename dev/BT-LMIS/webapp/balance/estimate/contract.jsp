<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
	$(document).ready(function(){
		if($("input[name='ckb2']").length != 0) {
			$("input[name='ckb2']").each(function() {
				$("#contract"+$(this).val()).hide();
				
			})
			
		}
		
	})
</script>
<table class="table table-hover table-striped" style="table-layout:fixed;">
	<tbody align="center">
		<c:forEach items="${contract }" var="contract">
			<tr id="contract${contract.id }" style="cursor:pointer;">
				<td class="td_ch"><input id="ckb" name="ckb" type="checkbox" value="${contract.id }" /></td>
				<td class="td_cs" title="${contract.contract_name }">${contract.contract_name  }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
