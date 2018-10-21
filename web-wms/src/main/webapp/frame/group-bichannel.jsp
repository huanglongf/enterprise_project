
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/meta.jsp"%>
		<script type="text/javascript">
</script>
		<title>渠道信息管理</title>
		<script type="text/javascript" src="<s:url value='/scripts/frame/group-bichannel.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
		<style type="text/css">
			.tips{
				width:100%; height: 50%; text-align:center; color:red; font-size: 40px; font-weight: bold;
			}
		</style>
	</head>
	<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<form id="channelForm" name="channelForm">
		<div id="bichannel-list">
				<table width="80%" id="queryTable">
					<tr>
						<td class="label" width="10%">渠道编码：</td>
						<td width="20%"><input loxiaType="input" name = "biChannel.code"  id="channelCode" trim="true"/></td>
						<td class="label" width="10%">渠道名称：</td>
						<td width="20%"><input loxiaType="input" name = "biChannel.name" id="channelName" trim="true"/></td>		
						<td class="label" width="10%">客户：</td>
						<td width="20%">
							<select loxiaType="select" name="biChannel.customer.id" id="customerId">
								<option value="">--请选择客户--</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="label" width="10%">发票公司简称：</td>
						<td width="20%">
							<select loxiaType="select" name="biChannel.companyName" id="companyName">
								<option value="">--请选择公司--</option>
							</select>
						</td>
					</tr>
				</table>
				<div class="buttonlist">
					<button id="search" type="button" loxiaType="button" class="confirm"><s:text name="button.query"/></button>
					<button id="reset" type="reset" loxiaType="button"><s:text name="button.reset"/></button>
				</div>
				<table id="tbl-bichannel-list"></table>
				<div id="pager"></div>
				<div class="buttonlist">
					<button id="addChannel" type="button" loxiaType="button" class="confirm">新建渠道</button>
				</div>
		</div>
		</form>
		<form id="detailForm" name="detailForm"  method="post" enctype="multipart/form-data" target="upload">
		<div id="detail" class="hidden">
			<div>
				<table width="80%">
					<tr>
						<td width="10%" class="label">渠道编码：</td>
						<td colspan="2"><span id="cCode"></span></td>
						<td width="20%"><input type="text" name="cid" id="cid" class="hidden"/></td>
						<td width="20%"><input type="text" name="cuId" id="cuId" class="hidden"/></td>
					</tr>
				</table>
			</div> 
			<div id="tabs">
					<ul>
						<li><a href="#tabs_1">渠道信息维护</a></li>
						<li><a href="#tabs_2">运单定制</a></li>
						<li><a href="#tabs_3">装箱清单打印定制</a></li>
						<li><a href="#tabs_4">残次品原因及类型维护</a></li>
						<li><a href="#tabs_5">SF时效类型</a></li>
					</ul>
					<div id="tabs_1">
						<table width="80%">
							<!-- <tr>
								<td width="10%" class="label">渠道编码：</td>
								<td colspan="2"><span id="cCode"></span></td>
								<td width="20%"><input type="text" name="cid" id="cid" class="hidden"/></td>
								<td width="20%"><input type="text" name="cuId" id="cuId" class="hidden"/></td>
							</tr> -->
							<tr>
								<td width="10%" class="label">渠道名称：</td>
								<td colspan="2"><input loxiaType="input" trim="true"  mandatory="true"   name="cName" id="cName"/></td>
							</tr>
							<tr>
									<td class="label" width="10%">客户：</td>
									<td width="20%">
										<select loxiaType="select" name="cmId" id="cmId" mandatory="true" >
										</select>
									</td>
									<td colspan="3"><span id = "cuIdErroe" style="size:12px;color:red; "></span></td>
							</tr>
							<tr>
								<td class="label" width="10%">发票公司简称：</td>
									<td width="20%">
										<select loxiaType="select" name="companyName1" id="companyName1" mandatory="true" >
										</select>
								</td>
							</tr>
							<tr>
								<td width="10%" class="label">发货地址：</td>
								<td colspan="2"><input loxiaType="input" trim="true"  mandatory="true"   name="address" id="address"/></td>
							</tr>
							<tr>
								<td width="10%" class="label">退换货地址：</td>
								<td colspan="2"><input loxiaType="input" trim="true"  mandatory="true"   name="rtnAddress" id="rtnAddress"/></td>
							</tr>
							<tr>
								<td width="10%" class="label">售后联系电话：</td>
								<td width="20%"><input loxiaType="input" trim="true"  mandatory="true"   name="telephone" id="telephone"/></td>
								<td width="10%" class="label">邮编：</td>
								<td width="20%"><input loxiaType="input" trim="true"  mandatory="true" maxlength="6"  name="zipcode" id="zipcode"/></td>
							</tr>
							<tr>
								<td width="10%" class="label">顺丰结算月结账号：</td>
								<td width="20%"><input loxiaType="input" trim="true"    name="sfJcustid" id="sfJcustid"/></td>
								<td width="10%" class="label">顺丰客户编码：</td>
								<td width="20%"><input loxiaType="input" trim="true"   name="sfJcusttag" id="sfJcusttag"/></td>
							</tr>
							<tr>
								<td width="20%" class="label">定制SF月结卡号：</td>
								<td colspan="2"><input loxiaType="input" trim="true"  mandatory="true" name="transAccountsCode" id="transAccountsCode"/></td>
							</tr>
							<tr>
								<td width="20%" class="label">是否支持SF次晨达业务：</td>
								<td width="20%"><input type="checkbox" name = "isSupportNextMorning" id = "isSupportNextMorning"/></td>
							</tr>
							<tr>
								<td width="20%" class="label">物流不可达转EMS：</td>
								<td width="20%"><input type="checkbox" name = "transErrorToEms" id = "transErrorToEms"/></td>
							</tr>
							<tr>
								<td width="20%" class="label">是否不允许混合创建配货清单：</td>
								<td width="20%"><input type="checkbox" name = "isMarger" id = "isMarger"/></td>
							</tr>
							<tr>
								<td width="20%" class="label">是否强制装箱：</td>
								<td width="20%"><input type="checkbox" name = "isReturnNeedPackage" id = "isReturnNeedPackage"/></td>
							</tr>
							<tr>
								<td width="20%" class="label">是否品牌按箱收货：</td>
								<td width="20%"><input type="checkbox" name = "isCartonStaShop" id = "isCartonStaShop"/></td>
							</tr>
							<tr>
								<td width="20%" class="label">是否京东电子面单：</td>
								<td width="20%"><input type="checkbox" name = "isJdolOrder" id = "isJdolOrder"/></td>
							</tr>
							<!-- <tr>
								<td width="10%" class="label">是否不需要校验退货入库批次：</td>
								<td width="20%"><input type="checkbox" name = "isNotValInBoundBachCode" id = "isNotValInBoundBachCode"/></td>
							</tr> -->
							<!-- <tr>
								<td width="10%" class="label">是否不需要关联销售单据：</td>
								<td width="20%"><input type="checkbox" name = "isReturnCheckBatch" id = "isReturnCheckBatch"/></td>
							</tr> -->
							<tr>
								<td width="20%" class="label">退货默认残次/良品不可销售：</td>
								<td width="20%"><input type="checkbox" name = "isDefaultStatus" id = "isDefaultStatus"/></td>
							</tr>
							<tr class="hidden">
								<td width="10%" class="label">是否发送短信：</td>
								<td width="20%"><input type="checkbox" name = "isSms" id = "isSms"/></td>
							</tr>
							<tr>
								<td width="10%" class="label">是否管理残次品：</td>
								<td width="20%"><input type="checkbox" name = "isImperfect" id = "isImperfect"/></td>
							</tr>
							<tr>
								<td width="10%" class="label">残次品退换货入库工厂代码必填限制：</td>
								<td width="20%"><input type="checkbox" name = "isImperfectPoId" id = "isImperfectPoId"/></td>
							</tr>
							<tr>
								<td width="10%" class="label">残次品退换货入库下单日期必填限制：</td>
								<td width="20%"><input type="checkbox" name = "isImperfectTime" id = "isImperfectTime"/></td>
							</tr>
							<tr>
								<td width="10%" class="label">VMI创入库单是否支持非取消单：</td>
								<td width="20%"><input type="checkbox" name = "isSupportNoCancelSta" id = "isSupportNoCancelSta"/></td>
							</tr>
							<tr>
								<td width="10%" class="label">澳门件是否打印海关单：</td>
								<td width="20%"><input type="checkbox" name = "isPrintMaCaoHGD" id = "isPrintMaCaoHGD"/></td>
							</tr>
							<tr id="setMoneyLmit" class="hidden">
								<td width="10%" class="label">海关单打印金额设置(港元)：</td>
								<td width="20%"><input loxiaType="input" trim="true"  mandatory="true" maxlength="6"  name="moneyLmit" id="moneyLmit" /></td>
							</tr>
							<tr>
								<td width="20%" class="label">销售出库取消状态限制类型：</td>
								<td width="20%">
										<select loxiaType="select" id="limitType">
										 <option value="1">出库</option>
										 <option value="2">配货中</option>
										</select>
								</td>
							</tr>
							<tr>
								<td width="20%" class="label">卡券拆包金额限制：</td>
								<td width="20%"><input loxiaType="input" onkeyup="value=value.replace(/[^\d.]/g,'')" trim="true" name="limitAmount" id="limitAmount"/></td>
							</tr>
							<tr>
								<td width="20%" class="label">店铺销售商品分类</td>
								<td width="20%"><input loxiaType="input" trim="true"  name = "skuCategories" id = "skuCategories"/></td>
							</tr>
							<tr>
								<td width="20%" class="label">是否执行单件订单基于库位创批</td>
								<td width="20%"><input type="checkbox" name ="isPickinglistByLoc" id = "isPickinglistByLoc"/></td>
							</tr>
							<tr>
								<td width="20%" class="label">PDA上架按单反馈</td>
								<td width="20%"><input type="checkbox" name ="isPda" id = "isPda"/></td>
							</tr>
						</table>
						<br>
						<br>
						<table width="30%">
								<label >保价信息维护：</label>
							<tr>
								<td width="10%" class="label">是否保价：</td>
								<td width="20%"><input type="checkbox" name = "isInsurance" id = "isInsurance"/></td>
							</tr>
							<tr>
								<td width="10%" class="label">保价范围-最低金额：</td>
								<td width="20%"><input loxiaType="input" onkeyup="value=value.replace(/[^\d.]/g,'')" trim="true" name="minInsurance" id="minInsurance"/></td>
							</tr>
							<tr>
								<td width="10%" class="label">保价范围-最高金额：</td>
								<td width="20%"><input loxiaType="input" onkeyup="value=value.replace(/[^\d.]/g,'')" trim="true"   name="maxInsurance" id="maxInsurance"/></td>
							</tr>
						</table>
					</div>
					<div id="tabs_2">
						<table width="80%">
							<tr>
								<td width="10%" class="label">店铺名称：</td>
								<td colspan="2"><input loxiaType="input" trim="true"   name="shopName" id="shopName"/></td>
								<td width="20%"><input type="text" name="pid" id="pid" class="hidden"/></td>
							</tr>
							<tr>
								<td width="10%" class="label">退货地址：</td>
								<td colspan="2"><input loxiaType="input" trim="true"  name="srtnAddress" id="srtnAddress"/></td>
							</tr>
							<tr>
								<td width="10%" class="label">联系电话1：</td>
								<td width="20%"><input loxiaType="input" trim="true"  name="pcontactsPhone" id="pcontactsPhone"/></td>
								<td width="10%" class="label">联系电话2：</td>
								<td width="20%"><input loxiaType="input" trim="true"   name="pcontactsPhone1" id="pcontactsPhone1"/></td>
							</tr>
							<tr>
								<td width="10%" class="label">联系人：</td>
								<td width="20%"><input loxiaType="input" trim="true"   name="contacts" id="contacts"/></td>
								<td width="10%" class="label">快递面单附加信息：</td>
								<td width="20%"><input loxiaType="input" trim="true"   name="transAddMemo" id="transAddMemo"/></td>
							</tr>
						</table>
					</div>
					<div id="tabs_3">
						<table width="80%">
							<tr>
								<td width="10%" class="label">店铺名称：</td>
								<td colspan="2"><input loxiaType="input" trim="true"  name="shopName1" id="shopName1"/></td>
								<td width="20%"><input type="text" name="eid" id="eid" class="hidden"/></td>
							</tr>
							<tr>
								<td width="10%" class="label">退货地址：</td>
								<td colspan="2"><input loxiaType="input" trim="true"  name="srtnAddress1" id="srtnAddress1"/></td>
							</tr>
							<tr>
								<td width="10%" class="label">联系电话1：</td>
								<td width="20%"><input loxiaType="input" trim="true"  name="econtactsPhone" id="econtactsPhone"/></td>
								<td width="10%" class="label">联系电话2：</td>
								<td width="20%"><input loxiaType="input" trim="true"  name="econtactsPhone1" id="econtactsPhone1"/></td>
							</tr>
							<tr>
								<td width="10%" class="label">联系人：</td>
								<td width="20%"><input loxiaType="input" trim="true"  name="contacts1" id="contacts1"/></td>
							</tr>
							<tr>
								<td width="10%" class="label">是否不显示总金额：</td>
								<td width="20%"><input type="checkbox" name = "isSum" id = "isSum"/></td>
							</tr>
								<tr>
								<td width="20%" class="label">仓库自定义打印模板：</td>
								<td width="20%">
										<select loxiaType="select" id="printCode">
										<option value="">--请选择--</option>
										</select>
								</td>
							</tr>
						</table>
					</div>
					<div id="tabs_4">
					<div id="imperfect">
					<table id="tbl-bichannel-imperfect-list"></table>
						<div id="pagerImperfect"></div>
						</div>
				<div class="buttonlist" id="imperfect-add">
				
					<table width="35%" id="tbl-bichannel-imperfect-add">
							<tr>
								<td align="left"><b>选择文件:</b></td>
								<td  align="center">
										 <input type="file" name="file" loxiaType="input" id="file" style="width:200px"/> 
								</td>
								<td> 
									<button loxiaType="button" class="confirm" id="import1">导入</button>
						            <a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_imperfect_type"></s:text>.xls&inputPath=tplt_imperfect.xls">模板文件下载</a>
						        </td>
							</tr>
						</table>
				</div>
				
				<div id="imperfectLine" class="hidden">
				<table id="tbl-bichannel-imperfect-line-list"></table>
				<div id="pagerImperfectLine"></div>
				</div>
				<div id="imperfectLineadd" class="hidden">
				<div class="buttonlist" id="imperfectLine-add">
					<table width="35%" id="tbl-bichannel-imperfect-add">
							<tr>
								<td align="left"><b>选择文件:</b></td>
								<td  align="center">
								<input id="imperfectId" name="imperfectId" type="text" class="hidden"/>
										 <input type="file" name="filewhy" loxiaType="input" id="filewhy" style="width:200px"/> 
								</td>
								<td> 
									<button loxiaType="button" class="confirm" id="importwhy">导入</button>
						            <a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_imperfect_type"></s:text>.xls&inputPath=tplt_imperfect.xls">模板文件下载</a>
						        </td>
							</tr>
						</table>
				</div>
					
					</div>
			</div>
			<div id="tabs_5">
				<table>
						<tr>
							<!-- <td class="label"</td> -->
							<td>
							仓库:<select loxiaType="select" name="wareHouse" style="width: 100px;" id="wareHouse">
							    </select>
							</td>
							<td>
								<input type="file" loxiaType="input" id="fileSFT" name="fileSFT" style="width: 300px;"/>
							</td>
							<td>
								<button loxiaType="button" class="confirm" id="importsfyun"><s:text name="导入(修改)配置"></s:text> </button>
								<button loxiaType="button" class="confirm" id="exprotsfyun"><s:text name="导出现有配置"></s:text> </button>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<span><font color="red">初次维护/编辑请先点击“导出现有配置”按钮,编辑内容后再将文件导入，并点击“导入（修改）配置”按钮，即可完成配置的初始化或者修改</font></span>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<button loxiaType="button" class="confirm" id="querysfyun"><s:text name="查询SF时效"></s:text> </button>
							</td>
						</tr>
				</table>
				
					<table id="sf_yun_config_list"></table>
						<div id="pagerSf"></div>
					
			</div>
				<div class="buttonlist">
					<button id="save" type="button" loxiaType="button" class="confirm">保存</button>
					<button id="back" type="button" loxiaType="button">返回</button>
					<button id="lineback" type="button" loxiaType="button" class="hidden">返回</button>
				</div>
		</div>
			</form>
<iframe id="upload" name="upload" class="hidden"></iframe>

<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload" class="hidden">
</form>
<iframe id="exportFrame" class="hidden" target="_blank"></iframe>
	</body>
</html>