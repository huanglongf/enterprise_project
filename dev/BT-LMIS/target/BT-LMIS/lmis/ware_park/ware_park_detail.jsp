<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ include file="/templet/common.jsp"%>
<title>LMIS</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link type="text/css" href="<%=basePath%>css/table.css"
	rel="stylesheet" />
<script type="text/javascript" src="<%=basePath%>js/common_view.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/work_order/work_type.js"></script>
<script type="text/javascript" src="<%=basePath%>js/selectFilter.js"></script>
</head>
<body>
	<div class="page-header">
		<div style="margin-left: 20px;margin-bottom: 20px;">
			<form id="saveForm" action="#">
				<table>
					<tr>
						<td align="right" width="25%">
							园区代码&nbsp;:
						</td>
						<td width="25%">
							${model.parkCode }
						</td>
						<td align="right" width= "25%">
							园区名称&nbsp;:
						</td>
						<td width="25%">
							${model.parkName }
						</td>
					</tr>
					<tr>
						<td align="right" width="25%">
							园区成本中心代码&nbsp;:
						</td>
						<td width="25%">
							${model.parkCostCenter }
						</td>
						<td align="right" width="25%">
							备注&nbsp;:
						</td>
						<td width="25%">
							${model.remark }
						</td>
					</tr>
					<%-- <tr>
						<td align="right" width="25%">
							仓&nbsp;:
						</td>
						<td width="25%">
							${model.wareFlag }
						</td>
						<td align="right" width="25%">
							配&nbsp;:
						</td>
						<td width="25%">
							${model.disFlag }
						</td>
					</tr>
					<tr>
						<td align="right" width="25%">
							是否由物流部出具账单&nbsp;:
						</td>
						<td width="25%">
							${model.showByCpFlag }
						</td>
						<td align="right" width="25%">
							是否出具店铺收入&nbsp;:
						</td>
						<td width="25%">
							${model.showStReFlag }
						</td>
					</tr> --%>
				</table>
			</form>
		</div>
	</div>
	<div style="margin-top: 10px;margin-left: 10px;margin-bottom: 10px;">
		<button class="btn btn-sm btn-white btn-info btn-bold btn-round btn-width"
			onclick="back();">
			<i class="ace-icon fa fa-angle-double-left blue bigger-120"> 返回 </i>
		</button>
	</div>
	<div class="table-main">
		<iframe id="warehouseAndStore" src="" runat="server" style="width:100%;min-height:250px;background-color:transparent;" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="true" allowtransparency="true" onload="setIframeHeight(this)"></iframe>
	</div>
</body>
<script type="text/javascript">

	function showModel(obj) {
		$(".modal-backdrop").show();
		$("#" + obj + "Model").modal('show');
	}
	
	function back(){
		openDivs(root+"/control/wareParkController/list.do");
	}
	
	function setIframeHeight(iframe) {
		if (iframe) {
			var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
			if (iframeWin.document.body) {
				iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
			}
		}
	}
	
	$("#warehouseAndStore").attr("src",root+"/control/wareParkController/wareParkWarehouseStoreList.do?parkId=${parkId}&isShow=true");

</script>
</html>
