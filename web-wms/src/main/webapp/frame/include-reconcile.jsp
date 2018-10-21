<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/include-reconcile.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<%@taglib prefix="s" uri="/struts-tags"%>
<iframe id="upload" name="upload" class="hidden"></iframe>
	<input type="hidden" id="preStarteDate" />
	<input type="hidden" id="preEndDate" />
			<ul>
				<li><a href="#tabs_view_add">相关费用信息</a></li>
			</ul>
			
			<div id="tabs_view_add">
					<table width="430px">
						<tr>
							<th colspan="4">
								对账明细
							</th>
						</tr>
						<tr>
							<td width="120px" class="label">
								订单销售金额
							</td>
							<td width="100px" id="total_actual">
								0
							</td>
							<td width="120px" class="label">
								支付宝收入
							</td>
							<td width="100px" id="alipay_cost">
								0
							</td>
						</tr>
						<tr>
							<td class="label">
								淘宝佣金
							</td>
							<td id="tb_cost">
								0
							</td>
							<td class="label">
								积分返点
							</td>
							<td id="score_cost">
								0
							</td>
						</tr>
						<tr>
							<td class="label" >
								代收邮费
							</td>
							<td id="actual_transfer_fee">
								0
							</td>
							<td class="label">
								线下退款
							</td>
							<td id="offl_return_cost">
								0
							</td>
						</tr>
						<tr>
							<td class="label" >
								信用卡手续费
							</td>
							<td id="credit_card_cost">
							0
							</td>
							<td class="label">
								其它手续费
							</td>
							<td id="other_cost">
								0
							</td>
						</tr>
						<tr>
							<td class="label">
								预付款金额
							</td>
							<td>
								0
							</td>
							<td class="label">
							淘宝客佣金代扣款
							</td>
							<td id="brokerageCost">
							0
							</td>
						</tr>
						<tr>
							<td class="label">
								捐赠支出
							</td>
							<td id="presentCost">
								0
							</td>
							<td class="label">
							彩票
							</td>
							<td id="lotteryTicketCost">
							0
							</td>
						</tr>
						<tr>
							<td class="label">
								交易成功前退款金额
							</td>
							<td id="olReturnCost">
								0
							</td>
							<td class="label">
							保证金理赔金额
							</td>
							<td id="earnestCost">
							0
							</td>
						</tr>
						<tr>
							<td class="label">
								B2CONLINE
							</td>
							<td id="b2cOnline">
								0
							</td>
							<td class="label">
							</td>
							<td id="XXX">
							</td>
						</tr>
					</table>
					<br/>
				<table width="900px">
					<tr>
						<td>
							<table width="470px" id="tblApply">
								<tr>
									<th colspan="3">供应商开票部分（增票）</th>
								</tr>
								<tr>
									<th width="160px">费用项名称</th>
									<th width="80px" >开票金额</th>
									<th width="110px">明细文件下载</th>
								</tr>
								<tfoot>
									<tr>
									    <th  align="right" >合计：</th>
										<th  align="left" id="applycount">0</th>									
									</tr>
								</tfoot>
							</table>
						</td>
						<td>
							<table width="470px" id="tblBZ">
								<tr>
									<th colspan="3">宝尊开票部分（服务性发票）</th>
								</tr>
								<tr>
									<th width="160px">费用项名称</th>
									<th width="80px" >开票金额</th>
									<th width="110px">明细文件下载</th>
								</tr>
								<tfoot>
									<tr>
									    <th  align="right" >合计：</th>
										<th  align="left" class="bzcount" id="bzcount">0</th>									
									</tr>
								</tfoot>
							</table>
						</td>
					</tr>
				</table>
			</div>
			<div id="dialog_add">
		<form method="POST" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
			<table width="80%">
				<tr>
					<td class="label" width="35%" id="filename">
						添加明细类型
					</td>
					<td>
						<select id="selAddType" name="addType">
							<option value="">请选择</option>
							<option value="4">供应商开票部分（增票）</option>
							<option value="5">宝尊开票部分（服务性发票）</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">
						费用明细
					</td>
					<td>
						<input loxiaType="input" trim="true" id="addMsg" name="reconcileline.explain"  mandatory="true"></input>
					</td>
				</tr>
				<tr>
					<td class="label">
						金额
					</td>
					<td>
						<input loxiaType="number" id="addTotal" name="reconcileline.fee" trim="true" style="width: 100px"  mandatory="true"></input>
					</td>
				</tr>
				<tr>
					<td class="label">
						附加文件
					</td>
					<td>
					<input id="tnFile" type="file" name="file" style="width:200px"/>
					</td>
				</tr>
			</table>
		</form>
		<input type="hidden" id="ishidden" name="ishidden"/>
			<div class="buttonlist">
				<button id="dialogBtnAdd" loxiaType="button" class="confirm">确定</button>
				<button id="dialogAddClose" loxiaType="button">关闭</button>
			</div>
		</div>