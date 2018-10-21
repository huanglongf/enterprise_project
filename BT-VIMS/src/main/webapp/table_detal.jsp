<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML>
<html>
<%@ include file="yuriy.jsp" %>
<head>
	<style type="text/css">
	.td_cs1{
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
		text-align: center;
	}
	.td_cs2{
	width: 154px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    text-align: center;
    }
	</style>
</head>
<div>
<div  class="title_div" id="sc_title" style="width: 100%;">		
<table class="table table-striped" style="table-layout: fixed;">
	<thead>
	  		<tr>
	                        <th class="td_cs1">编号</th>
	                        <th class="td_cs1">访客类型</th>
				         	<th class="td_cs1">来访人</th>
				         	<th class="td_cs1">联系电话</th>
				         	<th class="td_cs1">拜访人</th>
				         	<th class="td_cs1">拜访人公司</th>
				         	<th class="td_cs1">拜访人数</th>
				         	<th class="td_cs1">车牌号</th>
				         	<th class="td_cs1">到访地点</th>
				         	<th class="td_cs2">到访时间</th>
				         	<th class="td_cs2">离去时间</th>
				         	<th class="td_cs1">当前状态</th>
				         	<th class="td_cs1">操作</th>
			</tr>
	</thead>
</table>
</div>	
<div class= "tree_div" ></div>	  		
<div class="content_div" id="bodys" onscroll="init_td('sc_title','bodys')">
   <table class="table table-striped" style="table-layout: fixed;width: 99%;">	 		
		  		<tbody>
		  			<c:forEach items="${pageView.records}" var="vi">
		  			<tr>
		  			       <td class="td_cs1">${vi.data}</td>
		  			       <td class="td_cs1">
		  			       	<c:if test="${vi.visitorType == '01'}">个人</c:if>
		  			       	<c:if test="${vi.visitorType == '02'}">团体</c:if>
		  			       </td>
					       <td class="td_cs1" title="${vi.visitor_name}">
								<c:if test="${fn:length(vi.visitor_name)>8 }">
									${fn:substring(vi.visitor_name,0,8) }...
								</c:if>
								<c:if test="${fn:length(vi.visitor_name)<=8 }">
									${vi.visitor_name}
								</c:if>
					       </td>
					       <td class="td_cs1" title="${vi.visitor_phone}">
								<c:if test="${fn:length(vi.visitor_phone)>11 }">
									${fn:substring(vi.visitor_phone,0,11) }...
								</c:if>
								<c:if test="${fn:length(vi.visitor_phone)<=11 }">
									${vi.visitor_phone}
								</c:if>
					       </td>
					       <td class="td_cs1" title="${vi.host}">
								<c:if test="${fn:length(vi.host)>8 }">
									${fn:substring(vi.host,0,8) }...
								</c:if>
								<c:if test="${fn:length(vi.host)<=8 }">
									${vi.host}
								</c:if>
					       </td>
					       <td class="td_cs1" title="${vi.visitor_company_name}">
								<c:if test="${fn:length(vi.visitor_company_name)>8 }">
									${fn:substring(vi.visitor_company_name,0,8) }...
								</c:if>
								<c:if test="${fn:length(vi.visitor_company_name)<=8 }">
									${vi.visitor_company_name}
								</c:if>
					       </td>
					       <td class="td_cs1">${vi.visitorNum}</td>
					       <td class="td_cs1">${vi.license_plate_number}</td>
					       <td class="td_cs1">${vi.checkInPlcae}</td>
					       <td class="td_cs2"><fmt:formatDate value="${vi.check_in_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					       <td class="td_cs2"><fmt:formatDate value="${vi.check_out_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					       <td class="td_cs1">${vi.check_state}</td>
					       <td class="td_cs1">
					          <c:if test="${vi.check_state=='[已离开]'}">已签退</c:if>
					          <c:if test="${vi.check_state=='[已到访]'}"><a  href="javascript.void();" onclick="out('${vi.data}');">[签退]</a>&nbsp; </c:if>
				              /&nbsp;<a href="javascript.void();" onclick="callModule('${vi.data}');">[打印]</a>
				           </td>
			  		</tr>
			  		</c:forEach>
		  		</tbody>
			</table>
		</div>
	<div style="margin-right: 5%;margin-top:20px;margin-bottom: 10%;">${pageView.pageView}</div>
	</div>
</html>	
		