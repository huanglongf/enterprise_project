<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<style>
			.clear{
				clear:both;
				height:0;
			    line-height:0;
			}
			.photoInfo{
				float:right;
				margin-left:20px;
				width:130px;
			}
		</style>
		<%@include file="/common/meta.jsp"%>
		<title><s:text name="title.warehouse.pl.verify"/></title>
		<script type="text/javascript" src="<s:url value='/frame/sortingCheck/sales-dispatch-list-query.js"' includeParams='none' encode='false'/>"></script>
	    <script type="text/javascript" src="<s:url value='/scripts/common/aes/aes.js"' includeParams='none' encode='false'/>"></script> 
        <script type="text/javascript" src="<s:url value='/scripts/common/aes/mode-ecb.js"' includeParams='none' encode='false'/>"></script> 
        <script type="text/javascript" src="<s:url value='/scripts/common/aes/aes-baozun.js"' includeParams='none' encode='false'/>"></script> 
	</head>
	<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
		<s:text id="pselect" name="label.please.select"/>
		<input type="hidden" id="backTarget" />
		<!-- 配货清单列表 -->
		<div id="showList">
			<div>
			    <form id="plForm" name="plForm">
			    	<input type="hidden" name="cmd.sortingModeInt" value="2"/>
					<table width="70%">
						<tr>
							<td class="label" width="10%"><s:text name="label.warehouse.pl.whname"/></td>
							<td colspan="3" id="whName" width="90%"></td>
						</tr>
						<tr>
							<td class="label"><s:text name="label.warehouse.pl.batchno"/></td>
							<td width="20%"><input loxiaType="input" name="cmd.code" id="code" trim="true"/></td>
							<td class="label" width="10%"><s:text name="label.warehouse.pl.status"/></td>
	                        <td width="20%">
	                            <s:select list="plStatus" loxiaType="select" listKey="optionKey" listValue="optionValue" name="cmd.intStatus" id="status" headerKey="" headerValue="%{pselect}"/>
	                        </td>
						</tr>
						<tr>
							<td class="label"><s:text name="label.warehouse.pl.createtime"/></td>
							<td colspan="3">
								<input loxiaType="date" style="width: 150px" name="cmd.pickingTime1" id="pickingTime" showTime="true"/>
								<s:text name="label.warehouse.pl.to"/>
								<input loxiaType="date" style="width: 150px" name="cmd.executedTime1" id="executedTime" showTime="true"/>
							</td>
						</tr>
					</table>
					<div class="clear"></div>
				</form>
				<div class="buttonlist">
					<button id="search" type="button" loxiaType="button" class="confirm"><s:text name="button.query"/></button>
					<button id="reset" type="reset" loxiaType="button"><s:text name="button.reset"/></button>
				</div>
			</div>
			<div style="margin-bottom: 10px;">
				<span class="label"><s:text name="label.warehouse.pl.verify"/></span>输入单据号后按回车进入核对界面
				<br/>
				<span class="label" style="margin-left: 50px;"><s:text name="label.warehouse.pl.refcode"/></span><span>
				<input id="refSlipCode" class="refSlipCode" loxiaType="input" style="width: 150px" trim="true"/>&nbsp; 
					<span class="label" style="margin-left: 50px;">批次号</span>
					<input id="iptPlCode" class="refSlipCode" loxiaType="input" style="width: 150px" trim="true"/>
			</div>
			<div>
				<table id="tbl-dispatch-list"></table>
				<div id="pager"></div>
			</div>
		</div>
		<!-- 详情列表 -->
		<div id="showDetail" class="hidden">
			<table width="600px">
				<tr>
					<td class="label"><s:text name="label.warehouse.pl.batchno"/><input type="hidden" id="verifyPlId"/>
					<input type="hidden" id="isPostpositionPackingPage"/><input type="hidden" id="isPostpositionExpressBill"/>
					</td>
					<td width="15%" id="verifyCode"></td>
					<td class="label"><s:text name="label.warehouse.pl.status"/></td>
					<td width="15%" id="verifyStatus"></td>
					<td class="label">&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td class="label"><s:text name="label.warehouse.pl.pltotal"/></td>
					<td id="verifyPlanBillCount"></td>
					<td class="label"><s:text name="label.warehouse.pl.plcomplete"/></td>
					<td id="verifyCheckedBillCount"></td>
					<td class="hidden"></td>
					<td id="verifyShipStaCount" class="hidden"></td>
				</tr>
				<tr>
					<td class="label"><s:text name="label.warehouse.pl.prototal"/></td>
					<td id="verifyPlanSkuQty"></td>
					<td class="label"><s:text name="label.warehouse.pl.procomplete"/></td>
					<td id="verifyCheckedSkuQty"></td>
					<td class="hidden"><s:text name="label.warehouse.pl.proship"/></td>
					<td id="verifyShipSkuQty" class="hidden"></td>
				</tr>
			</table>
			<div class="buttonlist">
				<button loxiaType="button" id="reloadSta" class="confirm"><s:text name="button.reload"/></button>	
				<button id="backToPlList" loxiaType="button" ><s:text name="button.toback"/></button>
			</div>
			<div style="margin-bottom: 10px;">
				<br />
				<span class="label" style="margin-left: 50px;"><s:text name="label.warehouse.pl.refcode"/></span>
				<span>
					<input id="refSlipCode2" class="refSlipCode" loxiaType="input" style="width: 150px" trim="true"/>&nbsp; 
					<s:text name="label.warehouse.pl.prompt"/>
				</span>
			</div>

			<table id="tbl-showDetail"></table>
			<br /><br />
			<table id="tbl-showReady"></table>
			<br /><br />
			<table id="tbl-showCancel"></table>
			<br />
		</div>
		<!-- 核对列表 -->
		<div id="checkBill" class="hidden">
		<table>
			<tr>
				<td>
					<table width="700px">
						<tr>
							<td class="label" width="20%" style="font-size: 24px"><s:text
									name="label.warehouse.pl.sta" />
							</td>
							<td width="25%" id="staCode" style="font-size: 24px"></td>
							<td class="label" width="20%" style="font-size: 24px"><s:text
									name="label.warehouse.pl.refcode" />
							</td>
							<td id="staRefCode" style="font-size: 24px"></td>
						</tr>
						<tr>
							<td class="label"><s:text
									name="label.warehouse.pl.sta.trantype" />
							</td>
							<td id="staTranType"></td>
						</tr>
						<tr>
							<td class="label" width="20%"><s:text
									name="label.warehouse.pl.createtime" />
							</td>
							<td width="25%" id="staCreateTime" ></td>
							
							<td class="label" width="20%" id="icePackageLabel" style="color:red;font-size: 20px;">冰袋数量:</td>
							<td width="25%" id="icePackage" style="color:red;font-size: 20px;">0</td>
						</tr>
						<tr>
							<td class="label"><s:text name="label.warehouse.pl.status" />
							</td>
							<td id="staStatus"></td>
							<td class="label" style="font-size: 24px"><s:text
									name="label.warehouse.pl.sta.delivery" />
							</td>
							<td id="staDelivery" style="font-size: 24px"></td>
						</tr>
						<tr>
							<td class="label"><s:text
									name="label.warehouse.pl.sta.total" />
							</td>
							<td id="staTotal"></td>
							<td class="label">快递单号</td>
							<td id="staTransNo"></td>
						</tr>
						<tr>
							<td class="label"><s:text
									name="label.warehouse.pl.sta.comment" />
							</td>
							<td id="staComment"></td>
							<td class="label"><button loxiaType="button" class="confirm" id="newCamera">照片重拍</button></td>
							<td></td>
						</tr>
					</table>
			</td>
			<td>
				<div class="photoInfo" id="photoInfo">
					<%@include file="../../common/include-opencvimgmulti.jsp"%>
				</div>
			</td>
			</tr>
		</table>
		<br />
		<div>
				<div id="sn_import">
					<span class="label" style="float:left;">　SN号导入</span><input type="text" id="isBkCheck" class="hidden"/>
					<form style="float:left;" method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
						<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
					</form>
					<button style="float:left;" loxiaType="button" class="confirm" id="import"><s:text name="button.import"></s:text></button>
					<a style="float:left;" loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=销售出库SN号导入.xls&inputPath=tplt_import_sales_out_sn.xls"><s:text name="download.excel.template"></s:text></a>
					<button style="float:left;"  loxiaType="button" id="getstarbucksSncode" class="hidden">
				</div>
			</div>
			<br />
			<div style="margin-bottom: 15px;margin-top: 25px;">
				<button loxiaType="button" id="btnInQty" style="width: 100px;height: 30px" >录入数量</button>
				<input loxiaType="number" value="1" id="inQty" style="width: 60px;height: 30px" /> &nbsp;
				<span class="label" style="height: 30px" >
				 <s:text name="label.warehouse.pl.sta.sku.barcode"/><span class="label" style="height: 30px" >/卡号</span>
				</span>
				<span><input id="barCode" loxiaType="input" style="width: 200px;height: 25px" trim="true"/></span>
				<span id="snDiv" class="label" style="height: 30px" >
					SN号&nbsp;&nbsp;<input id="snCode" loxiaType="input"  trim="true" style="height:25px;width: 200px;" />
				</span>
			</div>
			<br/>
			<table id="tbl-billView"></table>
			<input type="hidden" id="staId"/>
			<table>
				<tr>
					<td>
						<div style="margin-top: 20px;margin-bottom: 15px;">
							<span class="label">
							<font style='text-align;' size=+1 color='red'>
							<s:text name="label.warehouse.pl.sta.delivery.code"/>
							</font>
							</span>
							<input id="express_order" style="width: 200px;height: 22px" loxiaType="input" />
							<button id="printDeliveryInfo" class="confirm ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false">
			                <span class="ui-button-text">打印物流面单</span>
			                </button>
			                <button id="btnTryError" loxiaType="button" >激活失败卡券拣货单补打</button>
						</div>
						<table id="tbl-express-order" width="500px"  style="text-align: center;" border="1px" cellpadding="0px" cellspacing="0px">
							<tr>
								<td class="label" height="30" style="text-align: center;"><s:text name="label.warehouse.pl.sta.delivery.code"/></td>
								<td class="label" height="30" style="text-align: center;"><s:text name="label.warehouse.pl.sta.operation"/></td>
							</tr>
						</table>
					</td>
					<td>
						<table id="tbl-trank-button" style="margin-top:20px;">
							<tr height = "38px">
								<td  valign="top">
									<button loxiaType="button" class="confirm" id="addTrank">新增运单</button>
								</td>
							</tr>
							<tr>
								<td>
									<table id="tbl-trank-list" width="500px"  style="text-align: center;" border="1px" cellpadding="0px" cellspacing="0px">
										<tr>
											<td class="label" height="30" style="text-align: center;">拆包运单号</td>
											<td class="label" height="30" style="text-align: center;"><s:text name="label.warehouse.pl.sta.operation"/></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<div class="buttonlist">
				<button loxiaType="button" class="confirm" id="doCheck"><s:text name="label.warehouse.pl.executeverify"/></button>
				<button loxiaType="button" id="Back"><s:text name="button.toback"/></button>
			           确认条码：<input id="barCode1"  loxiaType="input" style="width: 100px" trim="true" maxlength="6"/>
			</div>
		</div>
		<input type="hidden" id="flag0" />
		  <div id="dialog_error">
		      <table>
		        <tr align="center" >
		           <td colspan="2" id="errorText" align="center"></td>
		        </tr>
		        <tr><td></td></tr>
		        <tr><td></td></tr>
		        <tr><td></td></tr>
		        <tr><td></td></tr>
		        <tr>
		           <td><button loxiaType="button" class="confirm" id="doCheck0">确认</button></td>
		           <td>确认条码：<input id="barCode0" loxiaType="input" style="width: 100px" trim="true" maxlength="6" /></td>
		        </tr>
		      </table>
		  </div>
		  <div id="dialog_error_recheck">
		      <table>
		        <tr align="center" >
		           <td colspan="2" id="error_recheck_Text" align="center"></td>
		        </tr>
		        <tr><td></td></tr>
		        <tr><td></td></tr>
		        <tr><td></td></tr>
		        <tr><td></td></tr>
		        <tr>
		           <td><button loxiaType="button" class="confirm" id="doCheck_recheck">确认</button></td>
		           <td>确认条码：<input id="barCode_recheck" loxiaType="input" style="width: 100px" trim="true" maxlength="6" /></td>
		        </tr>
		      </table>
		  </div>
		 
		<div id="dialog-sns">
			<h3>SN序列号</h3>
			<div id="divSn">
				<div id="divClear" class="clear"></div>
			</div>
			<div class="buttonlist">
				<button type="button" loxiaType="button" class="confirm" id="close">关闭</button>
			</div>
		</div>
		<iframe id="upload" name="upload" class="hidden"></iframe>
		<div id="dialog_error_ok">
			<div id="error_text_ok" ></div>
			<div>
				<button type="button" loxiaType="button" class="confirm" id="dialog_error_close_ok" >关闭</button>
			</div>
		</div>
		<div id="dialog_la_check">
			<div id="la_check" ></div>
			<div>
				<button type="button" loxiaType="button" class="confirm" id="dialog_la_check_ok" >关闭</button>
			</div>
		</div>
	</body>
	<%@include file="../../common/include-opencv.jsp"%>
</html>