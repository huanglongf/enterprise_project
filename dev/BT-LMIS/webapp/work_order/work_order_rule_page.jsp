<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<link href="<%=basePath %>/css/table.css" type="text/css" rel="stylesheet" />
	<body>
		<div  class="title_div" id="sc_title">		
	<table class="table table-striped" style="table-layout: fixed;">
			   		<thead>
				  		<tr>
				  		<td class="td_ch"><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/></td>
                        <td class="td_cs">序号</td> 
			  			<td class="td_cs">预警类型</td>
			  			<td class="td_cs">预警级别</td>
			  			<td class="td_cs">工单类型</td>
			  			<td class="td_cs">工单级别</td>
			  			<td class="td_cs">启用状态</td>
				  		</tr>  	
			  		</thead>
	
	</table>
</div>
<div class="tree_div"></div>
<div style="height:400px;overflow:auto;overflow:scroll; border:solid #F2F2F2 1px;" id="sc_content" onscroll="init_td('sc_title','sc_content')">
<table id='table_content' class="table table-striped" style="table-layout: fixed;">
		  		<tbody>
		  		<c:forEach items="${pageView.records}" var="power" varStatus='status'>
			  		<tr>
			  			<td class="td_ch"><input id="ckb" name="ckb" type="checkbox" value="${power.id}"></td>
			  			<td class="td_cs">${status.count}</td>
			  			<td class="td_cs" code='${power.ew_type_code}'>${power.ew_type_name }</td>
			  			<td class="td_cs" code='${power.ew_level}'>${power.ew_level }</td>
			  			<td class="td_cs" code='${power.wk_type_code}'>${power.wk_type_name}</td>
			  			<td class="td_cs" code='${power.wk_level_code}'>${power.wk_level}
			  			</td>
			  			<td class= "td_cs" >
				  				<c:if test="${power.ew_flag==0}">禁用</c:if><c:if test="${power.ew_flag==1}">启用</c:if>
				  				<%-- |
				  				<c:if test= "${power.ew_flag ==1}" ><button class="btn btn-xs btn-danger" onclick="upStatus('${power.id}','false');"><i class= "icon-stop" ></i>禁用</button></c:if>
				  				<c:if test= "${power.ew_flag ==0}" ><button class="btn btn-xs btn-success" onclick="upStatus('${power.id}','true');"><i class= "icon-play" ></i>启用</button></c:if> --%>
				  			</td>
				  			
			  		</tr>
		  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<!-- 分页添加 -->
<div style="margin-right: 30px;margin-top:20px;">${pageView.pageView}</div>	 	 
	</body>
</html>

