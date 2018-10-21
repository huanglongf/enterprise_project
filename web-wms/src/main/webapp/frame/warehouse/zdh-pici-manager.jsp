<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../../common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.dispatchList.title"/></title>

<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/zdh-pici-manager.js' includeParams='none' encode='false'/>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="tbl-list">
	    <table width="90%">
		  <tr>
		  	<td> 
				<button loxiaType="button" class="confirm" id="import">自动化建批次导入</button>
			</td>
			<td>
					<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">
						<span class="label">自动化建批次选择文件:</span>
						<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
					</form>
			</td>
			
			<td style="width: 200px;" colspan="2"> 
	            <a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=创批次.xls&inputPath=zdh_pici_import.xls">自动化建批次模板下载</a>
	        </td>
		  </tr>
	    </table>
	   <br/>
	   <table id="tbl-zdhList"></table>
	   <div id="pager"></div> 
	 </div>
	 <div id="tbl-detail" class="hidden">
		<table>
			   <tr>
					<td width="20%" class="label">批次号</td>
					<td width="20%" id="code"></td>
					<td width="20%">请选择本次操作 </td>
					<td width="30%" class="label">
					   <select id="selectOption">
					      <option value="0">---请选择---</option> 
					      <option value="1">WMS3.0切IM</option>
					      <option value="2">WMS3.0切WMS4.0</option>
					      <option value="5">恢复</option>
					      
					    </select> 
					 </td>
					<td><input  type="hidden" id="zdhId"/></td>
				</tr>
				
		</table>
		<br />
		<table id="tbl-zdh-detail"></table>
	    <div id="pagerzdh"></div> 
	    <br />
		<div id="wms4" class="hidden">
			<button type="button" class="confirm" id="zdh_1">1生成库存txt文件</button>
			<button type="button" class="confirm" id="zdh_1_1">下载库存txt文件</button>
			<button type="button" class="confirm" id="zdh_2">2备份库存</button>
			<button type="button" class="confirm" id="zdh_3">3备份未完成的单据状态</button>
			<button type="button" class="confirm" id="zdh_4">4清理库存</button>
			<button type="button" class="confirm" id="zdh_5">5生成未完成的单据excel文件</button>
			<button type="button" class="confirm" id="zdh_6">6关闭未完成单据</button>
			<iframe id="exportFrame" class="hidden" target="_blank"></iframe>
	   </div>
	   <div id="IM"  class="hidden">
			<button type="button" class="confirm" id="zdh_7">插入占用数据</button>
	   </div>
	   
	    <div id="restore"  class="hidden">
			<button loxiaType="button" class="confirm" id="restore_1">库存数据恢复</button>
			<button loxiaType="button" class="confirm" id="restore_2">单据状态恢复</button>
	   </div>
	   <br />
	   <div><button loxiaType="button" class="confirm" id="back">返回</button></div>
	</div>
 	<iframe id="upload" name="upload" class="hidden"></iframe>
 </body>

</html>


