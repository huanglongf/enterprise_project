<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/warehouse-upload-print-templet.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div>
	<form name="searchForm" id="searchForm">
		<table>
			<tr>
				<td class="label"><s:text name="店铺:"></s:text></td> 
				<td>
					<div style="float: left">
				<%-- 	<select id="companyshop" name="deliveryLog.channel" loxiaType="select">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
					</select> --%>
					<input loxiaType="input" trim="true" id="companyshop" />
					</div>
					<!-- <div style="float: left">
							<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
					 </div> -->
				</td> 
			</tr>
		</table>
		</form>
	<br>
	<button  loxiaType="button" class="confirm" id="search"><s:text name="查询"></s:text> </button>
	<button  loxiaType="button" id="reset"><s:text name="重设"></s:text> </button>
	<table id="tbl-upload-templet-list"></table>
	<div id="pager_query"></div>
	<br>
	<button  loxiaType="button"  class="confirm" id="createPrintTemplate"><s:text name="新增"></s:text> </button>
	</div>
	<div id="dialog" style="text-align: center;" >
			<form method="post" enctype="multipart/form-data" id="importTempletForm" name="importTempletForm" target="upload">
			<table>
				<tr style="display: none;">
					<td></td>
					<td>
						<input loxiaType="input"  name="id"  id="id"></input>
					</td>
				</tr>
				<tr>
					<td>模板编码<span style="color:red;">*</span></td>
					<td>
						<input loxiaType="input"  name="templetCode" showTime="true" id="templetCode"></input>
					</td>
				</tr>
				<tr>
					<td>主模板<span style="color:red;">*</span></td>
					<td>
						<input type="file" name="mainJrxmlFile" loxiaType="input" id="mainJrxmlFile" />
					</td>
				</tr>
				<tr>
					<td>子模板<span style="color:red;">*</span></td>
					<td>
						<input type="file" name="subJrxmlFile" loxiaType="input" id="subJrxmlFile"/>
					</td>
				</tr>
				<tr>
					<td>图片<span style="color:red;">(非必填)</span></td>
					<td>
						<input type="file" name="picture" loxiaType="input" id="picture" />
					</td>
				</tr>
				<tr>
					<td>备注<span style="color:red;">*</span></td>
					<td>
						<input loxiaType="input"  name="memo" showTime="true" id="memo"></input>
					</td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
					<button type="button" loxiaType="button" class="confirm" id="import">保存</button>
					<button type="button" loxiaType="button" id="closediv">关闭</button>
			</div>
	</div>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
	<iframe id="upload" name="upload" class="hidden"></iframe>
	<iframe id="exportFrame" class="hidden" target="_blank"></iframe>
	<iframe id="exportFrame1" class="hidden" target="_blank"></iframe>
</body>
</html>