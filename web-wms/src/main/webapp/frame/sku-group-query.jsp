<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title>商品组合/拆分-查询/执行</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/sku-group-query.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style>
 
</style>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">

<div id="showList">
	<form id="formQuery" name="formQuery">
			<table width="70%">
				<tr>
					<td class="label" width="13%">
						创建时间
					</td>
					<td width="20%">
						<input loxiaType="date" name="icheck.startDate1" showTime="true"></input>
					</td>
					<td class="label" width="13%">
						到
					</td>
					<td width="20%">
						<input loxiaType="date" name="icheck.endDate1" showTime="true"></input>
					</td>
					<td width="13%"></td>
					<td width="20%"></td>
				</tr>
				<tr>
					<td class="label" width="13%">
						完成时间
					</td>
					<td width="20%">
						<input loxiaType="date" name="icheck.startFinishTime1" showTime="true"></input>
					</td>
					<td class="label" width="13%">
						到
					</td>
					<td width="20%">
						<input loxiaType="date" name="icheck.endFinishTime1" showTime="true"></input>
					</td>
					<td width="13%" class="label">店铺</td>
					<td width="20%">
						<select id="companyshop" name="icheck.owner" loxiaType="select">
							<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
						</select>
						<div style="float: left">
							<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">
						单据编码
					</td>
					<td>
						<input loxiaType="input" trim="true" name="icheck.code" />
					</td>
					<td class="label">
						状态
					</td>
					<td>
						<select loxiaType="select" name="icheck.intInvCheckStatus">
							<option value="">请选择</option>
							<option value="1">新建</option>
							<option value="5">差异未处理</option>
							<option value="10">完成</option>
							<option value="0">取消</option>
						</select>
					</td>
					<td width="13%" class="label">创建人</td>
					<td width="20%">
						<input loxiaType="input" trim="true" name="icheck.creatorName" />
					</td>
				</tr>
			</table>
			</form>
			<div class="buttonlist">
				<button type="button" loxiaType="button" class="confirm" id="search" >查询</button>
				<button type="button" loxiaType="button" id="reset">重置</button>
			</div>
			<br/>
			<table id="tbl_query"></table>
			<div id="tbl_query_page"></div> 
		</div>
		<div id="showDetail" class="hidden">
			<input type="hidden" name="iCheck" value="" id="iCheck"/>
			<table width="86%">
				<tr>
					<td width="10%" class="label">
						单据号
					</td>
					<td width="20%" id="code">
					</td>
					<td width="10%" class="label">
						店铺
					</td>
					<td width="20%" id="shop">
					</td>
					<td width="10%" class="label">
						创建人					
					</td>
					<td width="15%" id="user">
					</td>
					<td width="10%"></td>
					<td width="15%"></td>
				</tr>
				<tr>
					<td class="label">
						创建时间
					</td>
					<td id="createTime">
					</td>
					<td class="label">
						完成时间
					</td>
					<td id="finishTime">
					</td>
					<td class="label">
						状态
					</td>
					<td id="status">
					</td>
					<td class="label">
						作业类型
					</td>
					<td id="invChecktype">
					</td>
				</tr>
			</table>
			<div id="tabs">
				<ul>
					<li><a href="#tab_1">商品汇总</a></li>
					<li><a href="#tab_2">调整明细</a></li>
				</ul>
				<div id="tab_1">
					<table id="tbl_line_query"></table>
					<div id="tbl_line_query_page"></div> 
				</div>
				<div id="tab_2">
					<table id="tbl_line_detial_query"></table>
					<div id="tbl_line_detial_query_page"></div> 
				</div>
			</div>
			<div class="buttonlist">
				<button type="button" loxiaType="button" class="confirm" id="exec" >执行</button>
				<button type="button" loxiaType="button" class="confirm" id="cancel" >取消</button>
				<button type="button" loxiaType="button" id="back">返回</button>
			</div>
		</div>
		<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>		
</body>
</html>