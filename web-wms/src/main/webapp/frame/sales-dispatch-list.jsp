<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.dispatchList.title"/></title>

<script type="text/javascript" src="<s:url value='/scripts/frame/sales-dispatch-list.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
	
<div id="div1">
	<p><span class="label"><s:text name="label.wahrhouse.pl.new"></s:text> </span></p>
	<table id="tbl-newDistribution"></table>
	<div class="buttonlist">
		<button loxiaType="button" class="confirm" id="addDistribution"><s:text name="button.pl.add"></s:text> </button>
		<button id="cancel" loxiaType="button"><s:text name="button.pl.new.cancel"></s:text> </button>
	</div>
	<br />
	<p><span class="label"><s:text name="label.wahrhouse.pl.wating"></s:text> </span></p>
	<div id="div-waittingList">
		<table id="tbl-waittingList"></table>
	</div>
</div>
<div id="div2" class="hidden">
	<div id="pickingListId" class="hidden"></div>
	<table width="90%">
		<tr>
			<td class="label"><s:text name="label.warehouse.pl.batchno"></s:text> </td>
			<td>
				<label id="dphCode"></label>
				<button loxiaType="button" id="changeCode"><s:text name="button.change"></s:text> </button>
				<div id="hidden-waittingList" class="hidden" style="position: absolute;z-index: 99999"></div>
			</td>
			<td class="label"><s:text name="label.warehouse.pl.status"></s:text> </td>
			<td id="dphStatus"></td>
			<td class="label">物流</td>
			<td id="tdIdLpcode"></td>
		</tr>
		<tr>
			<td class="label"><s:text name="label.wahrhouse.sta.creater"></s:text> </td>
			<td width="20%" id="creator" ></td>
			<td class="label" class="15%"><s:text name="label.wahrhouse.sta.operator"></s:text> </td>
			<td id="operator"></td>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td class="label" width="15%"><s:text name="label.warehouse.pl.pltotal"></s:text> </td>
			<td width="15%" id="planBillCount">0</td>
			<td class="label" width="15%"><s:text name="label.warehouse.pl.plcomplete"></s:text> </td>
			<td width="15%" id="checkedBillCount">0</td>
			<td class="label" width="15%"><s:text name="label.warehouse.pl.plship"></s:text> </td>
			<td width="15%" id="sendStaQty"></td>
		</tr>
		<tr>
			<td class="label"><s:text name="label.warehouse.pl.prototal"></s:text> </td>
			<td id="planSkuQty"></td>
			<td class="label"><s:text name="label.warehouse.pl.procomplete"></s:text> </td>
			<td id="checkedSkuQty">0</td>
			<td class="label"><s:text name="label.warehouse.pl.proship"></s:text> </td>
			<td id="sendSkuQty">0</td>
		</tr>
		<tr>
			<td class="label"><s:text name="label.wahrhouse.pl.picking.time"></s:text> </td>
			<td id="pickingTime"></td>
			<td class="label"><s:text name="label.wahrhouse.pl.last.checked.time"></s:text> </td>
			<td id="checkedTime"></td>
			<td class="label"><s:text name="label.wahrhouse.pl.last.send.time"></s:text> </td>
			<td id="lastSendTime"></td>
		</tr>
	</table>
	<br /><br />
	<div id="divTbDetial"><table id="tbl-orderDetail"></table></div>
	<div id="btnlist" class="buttonlist" >
		<p>
			<button loxiaType="button" class="confirm" id="btnSoInvoice"><s:text name="button.export.invoice"></s:text></button>
			<button loxiaType="button" id="btnSoInvoiceZip"><s:text name="button.export.invoice"></s:text>压缩文件</button>
			<button loxiaType="button" id="excelPickingList">配货清单导出EXCEL</button>
		</p>
		<p>
			<button loxiaType="button" id="printPickingList"><s:text name="button.pl.print"/></button>
			<button loxiaType="button" class="confirm" id="printPickingListNew"><s:text name="button.pl.print2"/></button>
			<button loxiaType="button" class="confirm" id="printTrunkPackingList"><s:text name="button.pg.print"></s:text></button>
		<p>
		<p>
			<button loxiaType="button" class="confirm" id="exportDeliveryInfo"><s:text name="button.trans.export"></s:text></button>
			<button loxiaType="button" id="exportDeliveryInfoZip"><s:text name="button.trans.export"></s:text>压缩文件</button>
			<button loxiaType="button" class="confirm" id="printDeliveryInfo"><s:text name="button.trans.print"></s:text></button>
			<button loxiaType="button" class="hidden" id="emsPrintAll">ems面单打印</button>
			<button loxiaType="button" id="back"><s:text name="button.back"></s:text> </button>
		</p>			
		</p>
		<div class="buttonlist">
			<p>
				<button loxiaType="button" class="confirm" id="coachInvOutputZip">导出coach开票信息 压缩文件</button>
			</p>
		</div>
	</div><br/><br/>
	<div class="buttonlist" id="metrobtn">
		<button loxiaType="button" class="confirm" id="btnImportMetro"><s:text name="button.pl.metro.export"/></button>
		<button loxiaType="button" class="confirm" id="btnPrintMetroInvoice"><s:text name="button.pl.metro.print"/></button>
	</div>
	<div id="errBtnlist" class="buttonlist">
		<button loxiaType="button" class="confirm" id="remFailed"><s:text name="button.pl.remove.failed"></s:text></button>
		<button loxiaType="button" class="confirm" id="occupiedInv"><s:text name="button.pl.reoccupied"></s:text> </button>
		<button loxiaType="button" id="plCancel"><s:text name="button.cancel"></s:text> </button>
		<button loxiaType="button" id="back2"><s:text name="button.back"></s:text></button>
	</div>
	<div id="errTransBtnlist" class="buttonlist">
		<button loxiaType="button" class="confirm" id="rmTransFailed">删除快递失败单据</button>
		<button loxiaType="button" class="confirm" id="tryTrans">再次尝试</button>
		<button loxiaType="button" id="back3">返回</button><br/><br/>
		选择快递
		<select name="sta.staDeliveryInfo.lpCode" id="selLpcode" loxiaType="select" style="width:80px;">
		</select>
		<button loxiaType="button" class="confirm" id="btnChgTrans">无法配送转物流</button>
	</div>
</div>
<div class="hidden">
	<OBJECT ID='emsObject' name='emsObject' CLASSID='CLSID:53C732B2-2BEA-4BCD-9C69-9EA44B828C7F' align=center hspace=0 vspace=0></OBJECT>
</div>
<iframe id="frmSoInvoice" class="hidden"></iframe>
<iframe id="exportFrame" class="hidden" target="_blank"></iframe>
</body>
</html>