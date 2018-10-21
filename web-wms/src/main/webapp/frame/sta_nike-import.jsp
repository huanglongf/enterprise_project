<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title>NIKE收货-导入箱号关系</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/sta_nike-import.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body>
<s:text id="pselect" name="label.please.select"/>

<div id="showList">
			 <form id="plForm" name="plForm">
				<table>
					<tr>
						<td class="label">系统箱号</td>
						<td colspan="1">
							<input loxiaType="input" trim="true" style="width: 130px"  name="relationNike.sysPid" id="sysPid"/></td>
						</td>
					
						<td class="label">实物箱号</td>
						<td colspan="1">
							<input loxiaType="input" trim="true" style="width: 130px"  name="relationNike.enPid" id="enPid"/></td>
						</td>
					</tr>
				</table>
			</form>
			<form method="post" enctype="multipart/form-data" id="importForm"	name="importForm" target="upload">
					<td width="200px" class="label">批量导入：</td>
					<td align="center">
							<input type="file" name="file" loxiaType="input" id="file"
								style="width: 150px" />
					</td>
					<td><a loxiaType="button"
						href="<%=request.getContextPath()%>/warehouse/excelDownload.do?fileName=NIKE收货-导入箱号关系.xls&inputPath=nike_relation_import.xls">模板下载</a>
					<button loxiaType="button" class="confirm" id='sn_import'>导入</button>
					<iframe id="upload" name="upload" class="hidden"></iframe>
					</td>
</form>
			<div class="buttonlist">
				<button id="search" type="button" loxiaType="button" class="confirm"><s:text name="button.query"/></button>
				<button id="reset" type="reset" loxiaType="button"><s:text name="button.reset"/></button>
			</div>
			<div>
				<table id="tbl-dispatch-list"></table>
			</div>
			<div id="pager"></div>

</div>
</body>
</html>