<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="betweenlibary.createoredit"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/odo/odo-create.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<s:text id="pselect" name="label.please.select"/>
<div id="mainAdd">
	   <form id="queryForm" name="queryForm">
			<table>
				<tr>
					<td class="label" width="10%">库间移动单号：</td>
					<td width="20%"><input loxiaType="input" name="odoCommand.code" id="code" trim="true"/></td>
					<td class="label" width="10%">出库仓：</td>
					<td width="20%"><input loxiaType="input" name="odoCommand.ouName" id="code" trim="true"/></td>
					<td class="label">入库仓：</td>
					<td width="20%"><input loxiaType="input" name="odoCommand.inOuName" id="code" trim="true"/></td>
				</tr>
				<tr>
					<td class="label">店铺：</td>
					<td width="20%"><input loxiaType="input" name="odoCommand.ownerName" id="code" trim="true"/></td>
					<td class="label" width="10%">状态</td>
                       <td width="20%">
                           <select loxiaType="select" id="statusList" name="odoCommand.status">
								<option value="" selected="selected">请选择</option>
								<option value="1">新建</option>
								<option value="2">待出库反馈</option>
								<option value="3">出库完成</option>
								<option value="4">待入库反馈</option>
								<option value="5">差异待确认</option>
								<option value="6">差异待反馈</option>
								<option value="7">差异未完成</option>
								<!-- <option value="20">出库单创建失败</option> -->
								<option value="21">入库单创建失败</option>
								<option value="22">差异单创建失败</option>
								<option value="10">完成</option>
								<option value="17">已取消</option>
							</select>
                       </td>
                       <td colspan="2"></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<table>
				<tr>
					<button type="button" loxiaType="button" class="confirm" id="search">
						查询
					</button>
					<button type="button" loxiaType="button" id="reset">
						重置
					</button>
				</tr>
			</table>
		</div>
		<table id="tbl-odo-list"></table>
		<div id="odoListPager"></div> 
</div>

<div id="odo-detail" class="hidden">
		<table>
			<tr>
				<td width="15%" class="label">
					库间移动单号
				</td>
				<td width="35%" id="odo_code"></td>
				<td class="label" >
					出库仓
				</td>
				<td width="35%" id="odo_ouName"></td>
			</tr>
			<tr>
				<td class="label">
					入库仓
				</td>
				<td id="odo_inOuName"></td>
				<td class="label" >
					创建时间
				</td>
				<td id="odo_createTime"></td>
			</tr>
			<tr>
				<td class="label">
					单据状态
				</td>
				<td id="odo_status"></td>
				<td class="label">
					店铺
				</td>
				<td id="odo_ownerName"></td>
			</tr>
		</table>
		<br />
		
		<div id="detail_tabs">
			<ul>
				<li><a href="#m2_tabs-1">退仓出库明细</a></li>
				<li><a href="#m2_tabs-2">入库明细</a></li>
				<li><a href="#m2_tabs-3">入库差异</a></li>
			</ul>
			<div id="m2_tabs-1">
				<table id="tbl-odoOutBound-detail"></table>
				<div id="odoOutBoundPager"></div>
			</div>
			
			<div id="m2_tabs-2">
				<table id="tbl-odoInBound-detail"></table>
				<div id="odoInBoundPager"></div>
			</div>
			
			<div id="m2_tabs-3">
			   <div id="diff" class="hidden">
				    差异库存转移到：<select id="odoDiffOuId"></select>
				  <button type="button" loxiaType="button" id="targetConfirm" class="confirm">提交</button>
				</div>
				<table id="tbl-odoDiff-detail"></table>
				<div id="odoDiffPage"></div> 
				<br/>
				<input type="hidden" id="odoId" />
				
			</div>
		</div>
		 <button type="button" loxiaType="button" id="createOutStaConfirm" class="confirm">创建出库作业单</button>
	</div>
<div id="importDiv">	
<table>
	<tr id="condition">
		<td class="label">出库仓：</td>
		<td width=150>
            <s:select list="whList"  loxiaType="select" listKey="id" 
                    listValue="name"   headerKey="" headerValue="%{pselect}" name="odoCommand.ouId">            
            </s:select>
        </td>
		<td class="label">入库仓：</td>
		<td width=200>
            <%-- <s:select list="whList"  loxiaType="select" listKey="id" 
                    listValue="name"  headerKey="" headerValue="%{pselect}" name="odoCommand.targetOuId">            
            </s:select> --%>
            <select id="odoCommand_targetOuId" name="odoCommand.targetOuId" loxiaType="select">
			</select>
        </td>
        <td class="label">库存状态：</td>
		<td width=200>
            <select id="odoCommand_invStatusId" name="odoCommand.targetOuId" loxiaType="select">
				<option value="">请选择</option>
			</select>
        </td>
		<td class="label">店铺：</td>
		<td width=150>
			<select id="selShopId" name="odoCommand.owner" loxiaType="select">
				<option value="">请选择</option>
			</select>
		</td>
		<td>
		</td>
		
	</tr>
	<tr id="fileDiv">
		<td class="label"><span class="label">选择文件 </span></td>
		<td colspan=2 >
			<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
				<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
			</form>
		</td>
		<td >
		<button type="button" loxiaType="button" id="import" class="confirm">导入</button>
		<button type="button" loxiaType="button" id="importNew" class="confirm">重新导入</button>
		<button type="button" loxiaType="button" id="export">模板导出</button>
		</td>
		
		<td ></td>
	</tr>
	<tr >
	<td ></td>
	<td ><button type="button" loxiaType="button" id="back">返回</button></td>
	<td colspan="2"></td>
	</tr>
</table>
<br />

<table id="tbl-invList"></table>
	<div id="download"></div>
	<iframe id="upload" name="upload" class="hidden"></iframe>
	<br/>
	<%-- <button type="button" loxiaType="button" class="confirm" id="confirm"><s:text name="betweenlibary.submit"/></button> --%>
</div>	

<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>