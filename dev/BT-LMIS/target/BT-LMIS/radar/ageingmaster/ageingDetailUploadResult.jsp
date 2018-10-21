<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
		function toAgeingDetail(id){
			openDiv("/BT-LMIS//control/radar/ageingDetailBackupsController/query.do?batId="+id);
		}
		function pageJump() {
			query();
		}
		function query(){
			openIdDiv("rawDataList","/BT-LMIS//control/radar/ageingDetailUploadController/query.do?startRow="
				  + $("#startRow").val()
				  + "&endRow="
				  + $("#startRow").val()
				  + "&page="
				  + $("#pageIndex").val()
				  + "&pageSize="
				  + $("#pageSize").val()
				  + "&ageingId="
				  + $("#ageingId").val());
		}
    	</script>
		
		<div id="page_view">
		<input id= "batId" type= "hidden" value= "${queryParam.ageingId }" />
		<div class='title_div' style="height : 520px; overflow : auto; overflow : scroll; border : solid #F2F2F2 1px;"  >
			<table class="table table-striped" overflow-x：show>
		   		<thead  align="center">
			  		<tr class='table_head_line'>
			  			<td><input style="width: 5%" type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/> </td>
			  			<td class='td_cs' style="width: 10%">序号</td>
			  			<td class='td_cs' style="width: 15%">导入文件名</td>
			  			<td class='td_cs' style="width: 30%">总条数</td>
			  			<td class='td_cs' style="width: 30%">失败条数</td>
			  			<td class='td_cs' style="width: 30%">创建人</td>
			  			<td class='td_cs' style="width: 40%">导入时间</td>
			  		</tr>  	
		  		</thead>
		  		<tbody id='tbody' align="center">
		  		<c:forEach items="${pageView.records}" var="power" varStatus='status'>
			  		<tr ondblclick='toAgeingDetail("${power.batId}");'>
			  			<td><input id="ckb" name="ckb" type="checkbox" value="${power.id}"></td>
			  			<td nowrap="nowrap">${status.count }</td>
			  			<td nowrap="nowrap">${power.fileName}</td>
			  			<td nowrap="nowrap">${power.totalNumber}</td>
			  			<td nowrap="nowrap">${power.errorNumber}</td>
			  			<td nowrap="nowrap">${power.createUser}</td>
			  			<td nowrap="nowrap">
			  				<fmt:formatDate value="${power.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			  			</td>
			  		</tr>
		  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<!-- 分页添加 -->
     <div id='botton_page' style="margin-right: 30px;margin-top:20px;">
<div style="margin-right: 30px;margin-top:20px;">${pageView.pageView}</div>	
      	 </div>
      	 </div>
