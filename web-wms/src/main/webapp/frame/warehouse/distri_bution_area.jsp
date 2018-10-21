<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
    <div id="tabs" class="ui-tabs ui-widget ui-widget-content ui-corner-all">
     <!-- <div>
      <table>
      <tr>
     	<td><button type="button" loxiaType="button" class="confirm" id="butionArea" >分配区域新建</button></td>
     	<td><button type="button" loxiaType="button" class="confirm" id="butionArea_Log">区域绑定库位</button></td>
     	<td><button type="button" loxiaType="button" class="confirm" id="butionArea_Type">区域绑定作业类型</button></td>
      </tr>
      </table>
	 </div> -->
	 <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
		<li class="ui-state-default ui-corner-top ui-tabs-selected ui-state-active"><a href="#distri_butionArea">分配区域新建</a></li>
		<li class="ui-state-default ui-corner-top"><a href="#distri_butionArea_Log">区域绑定库位</a></li>
		<li class="ui-state-default ui-corner-top"><a href="#distri_butionArea_Type">区域绑定作业类型</a></li>
	</ul>
    <!-- 分配区域 -->  
	 <div id="distri_butionArea" class="ui-tabs-panel ui-widget-content ui-corner-bottom">
	   <div>
		<table>	
			<tr>	
			    <td class="label">分配区域编码</td>
				<td><input loxiaType="input" name="staCmd.slipCode2" id="distri_bution_area_code" trim="true"/></td>
				<td class="label">分配区域名称</td>
				<td><input loxiaType="input" name="staCmd.slipCode1" id="distri_bution_area_name" trim="true"/></td>
			</tr>
			<tr>
				<td><button id="find" type="button" loxiaType="button" class="confirm">查询</button></td>
				<td><button id="add" type="button" loxiaType="button" class="confirm">新增</button></td>
			</tr>
		</table>
	   </div>
		<div id="bution-area-list">
			<table id="tbl-bution-area-list"></table>
			<div id="pager_query"></div>
		</div>
		<div id="modifyform" title="分配区域定义列表" style="display:none;"> 
		      <p ip="txtDistriButionAreaCodeP">分配区域编码：<input type="text" id="txtDistriButionAreaCode" /></p>
		      <p ip="txtDistriButionAreaNameP">分配区域名称：<input type="text" id="txtDistriButionAreaName" /></p>
		</div>
		
 	</div> 
 	<!-- 区域绑定库位 --> 
	<div id="distri_butionArea_Log" class="ui-tabs-panel ui-widget-content ui-corner-bottom ui-tabs-hide">
	 <div>
	    <table>
	       <tr>
	          <td class="label">库区：</td>
	          <td><select id="distri_butionArea_Log_selectkq"  loxiaType="select">
					 <option value="">--请选择--</option>
					 <c:forEach items="${nameList}" var = "stores" >
					 <c:if test="${stores.codeName != null}">
					 <option value="${stores.codeName}">${stores.codeName}</option>
					 </c:if>
				     </c:forEach>
				  </select>
			  </td>
	          <td class="label">库位：</td>
	          <td><input id="distri_butionArea_Log_inputkw" loxiaType="input" name="staCmd.slipCode1"  trim="true"/></td>
	       </tr>
	       <tr>
	       	  <td class="label">分配区域名称：</td>
	          <td><select id="distri_butionArea_Log_selectfpqymc"  loxiaType="select">
					 <option value="">--请选择--</option>
					 <c:forEach items="${distriButionNameList}" var = "stores" >
					 <c:if test="${stores.distriButionAreaName != null}">
					 <option value="${stores.distriButionAreaName}">${stores.distriButionAreaName}</option>
					 </c:if>
				     </c:forEach>
				  </select>
			  </td>
	          <td class="label">分配区域编码：</td>
	          <td><select id="distri_butionArea_Log_selectfpqbm"  loxiaType="select">
					 <option value="">--请选择--</option>
					 <c:forEach items="${distriButionCodeList}" var = "stores" >
					 <c:if test="${stores.distriButionAreaCode != null}">
					 <option value="${stores.distriButionAreaCode}">${stores.distriButionAreaCode}</option>
					 </c:if>
				     </c:forEach>
				  </select>
			</td>
	       </tr>
	     </table>
	    </div>
	    <div class="buttonlist">
	       <table>
		     <tr>
		    	<td><button type="button" loxiaType="button" class="confirm" id="distri_butionArea_Log_search" >查询</button></td>
		    	<td><button type="button" loxiaType="button" class="confirm" id="distri_butionArea_Log_reset">重置</button></td>
		    	<td><button type="button" loxiaType="button" class="confirm" id="distri_butionArea_Log_export">导出库位</button></td>
		     </tr>
          </table>	
	    </div>
	    <div id="Log-group-list">
			<table id="tbl-Log-group-list"></table>
			<div id="pager_log"></div>
		</div>
		<div class="buttonlist">
	    	<button type="button" loxiaType="button" class="confirm" id="distri_butionArea_Log_delete" >批量删除</button>
	    </div>
	    <div class="buttonlist">
			<table>
			    <tr>
			    	<td class="label"><a class="confirm">请导入文件绑定分配区域和库位</a></td>
			    </tr>
				<tr>
					<td class="label">请选择导入文件</td>
					<td>
						<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">
							<input type="file" loxiaType="input" id="file" name="file" style="width: 200px;"/>
							<button loxiaType="button" class="confirm" id="import">导入创建</button>
						</form>
					</td>
					<td>
						<a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=分配区域绑定库位.xls&inputPath=dwhdistributionarealoc.xls">模板文件下载</a>
					</td>
			    </tr>
		  </table>
	   </div>
 	</div>
 	<!-- 区域绑定作业类型 -->  
	<div id="distri_butionArea_Type" class="ui-tabs-panel ui-widget-content ui-corner-bottom ui-tabs-hide" >
	   <div>
	    <table>
	      <tr>
	        <td class="label">分配区域编码</td>
			<td><input loxiaType="input" id="distri_butionArea_type_code" trim="true"/></td>
			<td class="label">分配区域名称</td>
			<td><input loxiaType="input" id="distri_butionArea_type_name" trim="true"/></td>
			<td><button id="distri_butionArea_type_find" type="button" loxiaType="button" class="confirm">查询</button></td>
	      </tr>
	    </table>
	    <!-- 已绑定的作用区域 -->
	    <div id="type-group-list">
			<table id="tbl-type-group-list"></table>
			<div id="pager_type"></div>
		</div>
		</div>
		<div>
		<!-- 作业类型 -->
		<div id="transaction-type">
			<table id="tbl-transaction-type-list"></table>
			<div id="pager_transaction-type"></div>
		</div>
		<div class="buttonlist">
	    	<button type="button" loxiaType="button" class="confirm" id="binding_transaction-type" >绑定作业类型</button>
	    	<button type="button" loxiaType="button" class="confirm" id="cancel_binding" >取消绑定</button>
	    </div>
	    <div id="binding_detai" title="绑定明细列表" style="display:none;"> 
			<table id="tbl-binding_detai-list"></table>
			<div id="pager_binding_detai"></div>
		</div>
		</div>
 	</div>
 	<div>
 		<iframe id="export" name="export" style="display:none;"></iframe>
 		<iframe id="upload" name="upload" style="display:none;"></iframe>
 	</div>
   </div> 	
 	<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/distriBution-area.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
 	
</body>
</html>