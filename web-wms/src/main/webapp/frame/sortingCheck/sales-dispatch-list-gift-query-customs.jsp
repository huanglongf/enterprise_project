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
		<script type="text/javascript" src="<s:url value='/scripts/frame/sales-dispatch-list-gift-query-customs.js"' includeParams='none' encode='false'/>"></script>
	
	</head>
	<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
		<s:text id="pselect" name="label.please.select"/>
		<div id="showList">
			<div>
			    <form id="plForm" name="plForm">
			    	<input type="hidden" name="cmd.sortingModeInt" value="3"/>
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
								<input loxiaType="date" style=" width: 150px" name="cmd.pickingTime1" id="pickingTime" showTime="true"/>
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
			<div style="margin-bottom: 10px;" >
				<span class="label"><s:text name="label.warehouse.pl.verify"/></span>输入单据号后按回车进入核对界面
				<br/>
				<span class="label" style="margin-left: 50px;"><s:text name="label.warehouse.pl.refcode"/></span><span>
				<input id="refSlipCode" class="refSlipCode" loxiaType="input" style="width: 150px" trim="true"/>&nbsp; 
					<span class="hidden" style="margin-left: 50px;">批次号</span>
					<input class="hidden" id="iptPlCode" loxiaType="input" style="width: 150px" trim="true"/>
			</div>
			<div>
				<table id="tbl-dispatch-list"></table>
				<div id="pager"></div>
			</div>
		</div>
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
					<td class="label"><s:text name="label.warehouse.pl.plship"/></td>
					<td id="verifyShipStaCount"></td>
				</tr>
				<tr>
					<td class="label"><s:text name="label.warehouse.pl.prototal"/></td>
					<td id="verifyPlanSkuQty"></td>
					<td class="label"><s:text name="label.warehouse.pl.procomplete"/></td>
					<td id="verifyCheckedSkuQty"></td>
					<td class="label"><s:text name="label.warehouse.pl.proship"/></td>
					<td id="verifyShipSkuQty"></td>
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
				<span class="label" style="margin-left: 50px;">批次号：</span>
				<span>
					<input id="iptPlCode1" loxiaType="input" style="width: 150px" trim="true"/>
				</span>
			</div>

			<table id="tbl-showDetail"></table>
			<br /><br />
			<table id="tbl-showReady"></table>
			<br />
		</div>

		<div id="checkBill" class="hidden">
			<table>
			<tr>
			<td>
				<table width="700px">
				<tr>
					<td class="label" width="20%" style="font-size: 24px"><s:text name="label.warehouse.pl.sta"/></td>
					<td width="25%" id="staCode" style="font-size: 24px"></td>
					<td class="label" width="20%" style="font-size: 24px"><s:text name="label.warehouse.pl.refcode"/></td>
					<td id="staRefCode" style="font-size: 24px"></td>
				</tr>
				<tr>
					<td class="label"><s:text name="label.warehouse.pl.sta.trantype"/></td>
					<td id="staTranType"></td>
				</tr>
				<tr>
					<td class="label" width="20%"><s:text name="label.warehouse.pl.createtime"/></td>
					<td width="25%" id="staCreateTime"></td>
				</tr>
				<tr>
					<td class="label"><s:text name="label.warehouse.pl.status"/></td>
					<td id="staStatus"></td>
					<td class="label" style="font-size: 24px"><s:text name="label.warehouse.pl.sta.delivery"/></td>
					<td id="staDelivery" style="font-size: 24px"></td>
				</tr>
				<tr>
					<td class="label"><s:text name="label.warehouse.pl.sta.total"/></td>
					<td id="staTotal"></td>
					<td class="label">快递单号</td>
					<td id="staTransNo"></td>
				</tr>
				<tr>
					<td class="label"><s:text name="label.warehouse.pl.sta.comment"/></td>
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
			<div style="margin-bottom: 15px;margin-top: 25px;">
				<button loxiaType="button" id="btnInQty" class="hidden" style="width: 100px;height: 30px" >录入数量</button>
				<input loxiaType="number" value="1" id="inQty" style="width: 60px;height: 30px" /> &nbsp;
				<span class="label" style="height: 30px" >
				 <s:text name="label.warehouse.pl.sta.sku.barcode"/>
				</span>
				<span><input id="barCode" loxiaType="input" style="width: 200px;height: 25px" trim="true"/></span>
				<select id="origin" loxiaType="select" name="origin" style="width:300px;height: 30px">
							<option value="">请选择</option>
						</select>
				<button loxiaType="button" class="confirm" id="barcodeCheck">确认</button>	
				<span id="snDiv" class="label" style="height: 30px" >
					SN号&nbsp;&nbsp;<input id="snCode" loxiaType="input"  trim="true" style="height:25px;width: 200px;" />
				</span>
			</div>
			<table id="tbl-billView"></table>
			<input type="hidden" id="staId"/>
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
			</div>
			<table id="tbl-express-order" width="500px"  style="text-align: center;" border="1px" cellpadding="0px" cellspacing="0px">
				<tr>
					<td class="label" height="30" style="text-align: center;"><s:text name="label.warehouse.pl.sta.delivery.code"/></td>
					<td class="label" height="30" style="text-align: center;"><s:text name="label.warehouse.pl.sta.operation"/></td>
				</tr>
			</table>
			
			<div class="buttonlist">
				<button loxiaType="button" class="confirm" id="doCheck"><s:text name="label.warehouse.pl.executeverify"/></button>
				<button loxiaType="button" id="Back"><s:text name="button.toback"/></button>
			           确认条码：<input id="barCode1"  loxiaType="input" style="width: 100px" trim="true" maxlength="6"/>
			</div>
			<table id="tbl-trank-button" style="margin-left:520px;margin-top:-127px;">
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
		  <div id="dialog_gift">
		  	<input type="hidden" id='giftDivLineId' />
		  	<input type="hidden" id='giftDivLineId_type' />
		  	<input type="hidden" id='giftDivLineId_lineId' />
	  	 	<table>
	  	 		<tr>
	  	 			<td colspan="2"><font id="gift_type"></font></td>
	  	 		</tr>
	  	 		<tr id='tr_memo'>
	  	 			<td colspan="2" style="border:black solid 1px;width: 290px;height: 170px;WORD-BREAK: break-all; WORD-WRAP: break-word"><font id="gift_memo" size="3" style="WORD-BREAK: break-all; WORD-WRAP: break-word"></font></td>
	  	 		</tr>
	  	 		<tr id='tr_sku'>
	  	 			<td class="label">商品货号</td><td id='dialog_sku'></td>
	  	 		</tr>
	  	 		<tr id='tr_expirationTime'>
	  	 			<td class="label">过期时间</td><td id='expirationTime'></td>
	  	 		</tr>
	  	 		<tr id='tr_caochWarrantyCard'> 
	  	 			<td class="label">保修卡条码:</td><td><input loxiaType="input" id="caochWarrantyCard" trim="true" /></td>
	  	 		</tr>
	  	 		<tr>
	  	 			<td colspan="2">
	  	 				<button loxiaType="button" class="confirm" id="gift_print">打印</button>
	  	 				<button loxiaType="button" class="confirm gift_confirm" id="gift_confirm1">确定</button>
	  	 				<button loxiaType="button" class="confirm gift_confirm" id="gift_confirm2">确定</button>
	  	 				<button loxiaType="button" id="gift_skip">跳过</button>
	  	 				确认条码:<input id="barCode_gift_confirm" loxiaType="input" style="width: 100px" trim="true" maxlength="6" />
	  	 			</td>
	  	 		</tr>
	  	 		<tr>
	  	 			<td colspan="2" id='td_barCode_gift_skip'>
	  	 				跳过条码：<input id="barCode_gift_skip" loxiaType="input" style="width: 100px" trim="true" maxlength="6" />
	  	 			</td>
	  	 		</tr>
	  	 	</table>
		 </div>
		 <div id="dialog_staSpecialExecute">
	  	 	<table width="100%" style="height: 100%">
	  	 		<tr id='show_dialog_nike_gift_card_print_ABC' style="height: 100%;width: 100%;">
	  	 			<td align="center" >
	  	 				<font id="ABC_TYPE" style="font-size: 52px"></font>
	  	 			</td>
	  	 			<td>
	  	 				<font id="ABC_VALUE" style="font-size: 32px"></font>
	  	 			</td>
	  	 		</tr>
	  	 		<tr id='show_dialog_gucci_gift_card_print' style="height: 100%;width: 100%;">
	  	 			<td align="center" colspan='2' >
	  	 				<font id="GUCCI_GIFT_CARD_VALUE" style="font-size: 15px"></font>
	  	 			</td>
	  	 		</tr>
	  	 		<tr style="height: 100%;width: 100%">
	  	 			<td align="center">
	  	 				<button loxiaType="button" class="confirm" id="dialog_coach_print">打印小票</button>
	  	 				<button loxiaType="button" class="confirm" id="dialog_burberry_out_print">Burberry 寄货单据</button>
	  	 				<button loxiaType="button" class="confirm" id="dialog_burberry_return_print">Burberry 退货表格</button>
	  	 				<button loxiaType="button" class="confirm" id="dialog_nike_gift_card_print">NIKE 礼品卡打印</button>
	  	 				<button loxiaType="button" class="confirm" id="dialog_gucci_gift_card_print">GUCCI 礼品卡打印</button>
	  	 				<button loxiaType="button" class="confirm" id="dialog_mk_packaging_common">普通包装</button>
	  	 				<button loxiaType="button" class="confirm" id="dialog_mk_packaging_gift">礼品包装</button>
	  	 				<button loxiaType="button" class="confirm" id="dialog_mk_packaging_staff">员工包装</button>
	  	 				<button loxiaType="button" class="confirm" id="dialog_mk_gift_manual">礼品手册</button>
	  	 			</td>
	  	 			<td align="center"><button loxiaType="button" class="confirm" id="dialog_printSuccess">操作完成</button></td>
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
		 <div id="dialog_error_ok">
			<div id="error_text_ok" ></div>
			<div class="buttonlist">
				<button type="button" loxiaType="button" class="confirm" id="dialog_error_close_ok">关闭</button>
			</div>   
		</div>
		 <%@include file="../../common/include-opencv.jsp"%>
	</body>
</html>