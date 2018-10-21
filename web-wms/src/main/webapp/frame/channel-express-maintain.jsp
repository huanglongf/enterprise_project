
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/meta.jsp"%>
		<title>渠道信息管理</title>
		<script type="text/javascript" src="<s:url value='/scripts/frame/channel-express-maintain.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
		<style type="text/css">
			.tips{
				width:100%; height: 50%; text-align:center; color:red; font-size: 40px; font-weight: bold;
			}
		</style>
	</head>
	<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
		<div id="head_tab">
			<ul>
				<li><a href="#m_tab-1">渠道快递维护</a></li>
				<!-- <li><a href="#m_tab-2">EMS关键字维护</a></li> -->
				<li><a href="#m_tab-3">渠道快递规则状态变更</a></li>
			</ul>
			<div id = "m_tab-1">
				<div id="bichannel-list">
					<form id="channelForm" name="channelForm">
						<table width="49%" id="queryTable">
							<tr>
								<td class="label" width="5%">渠道编码：</td>
								<td width="12%"><input loxiaType="input" name = "biChannel.code"  id="channelCode" trim="true"/></td>
								<td class="label" width="5%">渠道名称：</td>
								<td width="12%"><input loxiaType="input" name = "biChannel.name" id="channelName" trim="true"/></td>
								<td class="label" width="3%">客户：</td>
								<td width="12%">
									<select loxiaType="select" name="biChannel.customer.id" id="customerId">
										<option value="">--请选择客户--</option>
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
					</form>
				</div>
				<div id="dialog_addRole">
					<div id="one" style="display:none">
						<table>
								<tr>
									<td><b>渠道编码:</b></td>
									<td><label id="createChannelCode"></label></td>
									<td width="50px"></td>
									<td><b>渠道名称:</b></td>
									<td ><label id="createChannelName"></label></td>
								</tr>
								<tr>
									<td><label><b>规则编码：</b></label></td>
									<td><input loxiaType="input" id="RoleCode"  trim="true" mandatory="true" name="code" width="180px"></input></td>	
									<td width="50px"></td>	
									<td><label><b>规则名称：</b></label></td>
									<td><input loxiaType="input" id="RoleName"  trim="true" mandatory="true" name="name" width="180px"></input></td>
								</tr>
						</table>
						<table id="tbl-transport-service"></table>
						<table>
							<tr>
								<td><label><b>状态：</b></label></td>
								<td width="150px">
									<select id="SelStatusOpc" name="status" loxiaType="select"  mandatory="true" loxiaType="select">
										<option value="1">使用中</option>
										<option value="0">禁用</option>
									</select>
								</td>		
								<td><label id = "sortLabel"><b>优先级：</b></label></td>
								<td><input loxiaType="input" id="RoleSort" name="sort"  mandatory="true" onkeyup="this.value=this.value.replace(/\D/g,'')" width="120px"></input></td>
							</tr>
						</table>
					</div>
					<div id="two" style="display:none">
						<table>
								<tr>
									<td><b>渠道编码:</b></td>
									<td><label id="createTwoChannelCode"></label></td>
									<td width="50px"></td>
									<td><b>渠道名称:</b></td>
									<td ><label id="createTwoChannelName"></label></td>
								</tr>
								<tr>
								<td><b>规则编码:</b></td>
									<td><label id="TwoRoleCode"></label></td>
									<td width="50px"></td>
									<td><b>规则名称:</b></td>
									<td ><label id="TwoRoleName"></label></td>
								</tr>
						</table>
						<br>
				 		<div id="detail_tabs" style="border-top:1px solid #CCC;border-left:1px solid #CCC;border-right:1px solid #CCC;">
							<ul>
								<li><a href="#m2_tabs-1">配送范围</a></li>
								<li><a href="#m2_tabs-2">订单信息</a></li>
								<li><a href="#m2_tabs-3">商品信息</a></li>
								<li><a href="#m2_tabs-4">排除关键字</a></li>
								<li><a href="#m2_tabs-5">指定仓库</a></li>
							</ul>
							<div id="m2_tabs-1">
								<table>
									<tr>
										<td><b>配送范围:</b></td>
										<td><label id="transAreaLabel"></label></td>
										<td><label id="transAreaLabelCode"></label></td>
										<td><label id="transAreaLabelName"></label></td>
										<td><label id="transAreaIdLabel"></label></td>
										<td><label id="transAreaLabelStatus"></label></td>
									</tr>
								</table>
								<button id="choose" type="button" loxiaType="button" class="confirm">选择</button>
								<button id="new" type="button" loxiaType="button" class="confirm">新建</button>
								<button id="edit" type="button" loxiaType="button" class="confirm" >编辑</button>
							</div>
							<div id="m2_tabs-2">
									<table>
										<tr>
											<td><label><b>订单金额：</b></label></td>
											<td><input loxiaType="input" id="minAmount" name="minAmount"  onkeyup="value=value.replace(/[^\d.]/g,'')" trim="true" width="120px"></input></td>	
											<td><label><b>至：</b></label></td>
											<td><input loxiaType="input" id="maxAmount" name="maxAmount" onkeyup="value=value.replace(/[^\d.]/g,'')" trim="true" width="120px"></input></td>
										</tr>
										<tr>
											<td><label><b>订单重量：</b></label></td>
											<td><input loxiaType="input" id="minWeight" name="minWeight"  onkeyup="value=value.replace(/[^\d.]/g,'')" trim="true" width="120px"></input></td>	
											<td><label><b>至：</b></label></td>
											<td><input loxiaType="input" id="maxWeight" name="maxWeight" onkeyup="value=value.replace(/[^\d.]/g,'')" trim="true" width="120px"></input></td>
										</tr>
										<tr>
											<td><label><b>是否COD订单：</b></label></td>
											<td>
												<select id="isCod" name="isCod" loxiaType="select"  loxiaType="select">
													<option value="">不做限制</option>
													<option value="1">COD订单</option>
													<option value="0">非COD订单</option>
												</select>
											</td>
										<td class="label" width="15%">时效类型：</td>
											<td>
												<select loxiaType="select" name="timeTypes" id="timeTypes" mandatory="true">
													<option value="">无</option>
													<option value="1">普通</option>
													<option value="3">及时达</option>
													<option value="5">当日</option>
													<option value="6">次日</option>
													<option value="7">次晨</option>
													<option value="8">云仓专配次日</option>
													<option value="9">云仓专配隔日</option>
												</select>
											</td>
										<td colspan="1"><label></label></td>
										</tr>
									</table>
							</div>
							<div id="m2_tabs-3">	
								<div id="detail_tabs2" style="border:1px dashed #CCC;width:750px;">
									<ul>
										<li><a href="#m3_tabs-1">商品</a></li>
										<li><a href="#m3_tabs-2">分类</a></li>
										<li><a href="#m3_tabs-3">标签</a></li>
									</ul>
									<div id="m3_tabs-1">
										<table id="tbl-sku-list"></table>
										<button id="skuAdd" type="button" loxiaType="button" class="confirm" >新增</button>
									</div>
									<div id="m3_tabs-2">
										<table id="tbl-sku-categories"></table>
										<teble>
											<tr>
												<td><b>分类名称:</b></td>
												<td>
													<select class="special-flexselect" id="categoSelect" name="skuCategories.name" data-placeholder="请选择分类名称">
													</select>
												</td>
												<td><button id="addCategories" type="button" loxiaType="button" class="confirm">新增</button></td>
											</tr>
										</teble>
									</div>
									<div id="m3_tabs-3">
										<table id="tbl-sku-tag"></table>
										<button id="skuTagAdd" type="button" loxiaType="button" class="confirm" >新增</button>
									</div>
								</div>
							</div>
							<div id="m2_tabs-4">	
								<table>
									<tr>
										<td colspan = "2"  height = "30px"><label id="emsMsg" style="color:red; font-size:12px">注意：多个关键字以英文逗号“,”分隔！当地址包含排除关键字时会排除此规则物流！</label></td>
									</tr>
									<tr>
										<td class="label"  width="80px">排除关键字： </td>
										<td>
											<textarea loxiaType="input" trim="true" id ="removeKeyword" name="removeKeyword"></textarea>
										</td>
									</tr>
								</table>
							</div>
							<div id="m2_tabs-5">
								<label id="emsMsg" style="color:red; font-size:12px">默认为全不勾选，支持所有与渠道有绑定关系的仓库，勾选任意一个仓库，就只支持勾选仓库！</label>
								<div><table id="tbl-channel-warehouse"></table></div>
							</div>
						</div>
					</div>
					<div class="buttonlist">
							<button id="next" type="button" loxiaType="button" class="confirm" >下一步</button>
							<button id="cancel" type="button" loxiaType="button" >取消</button>
					</div>
				</div>
				
				<div id ="bichannel-list-deatil">
					<table>
							<tr>
								<td align="left" width="75px"><b>渠道编码:</b></td>
								<td width="180px"><label id="detailChannelCode"></label></td>
								<td align="left" width="75px"><b>渠道名称:</b></td>
								<td width="180px"><label id="detailChannelName"></label></td>
							</tr>
					</table>
					<table>
					   <tr>
					   	<td height = "50px" colspan="2"><label id="emsMsg" style="color:red; font-size:14px">
					   	注意：多组关键字以英文分号";"分割!!! 一组关键字中包含和排除以"|"分割("|"必须要有), "|"前面的为包含关键字,后面的为排除关键字</br>
					   	若多个关键字以英文逗号“,”分隔！如地址只包含关键字优先推荐EMS，当地址包含排除关键字时不会优先推荐EMS</label></td>
					   </tr>
					   <tr><td width="100px">EMS关键字维护:</td>
					    <td width="400px"> 
							<textarea loxiaType="input" trim="true" id = "includeKeyWord" name="includeKeyWord"></textarea>
						</td>
					   </tr>
					</table>
				   <div class="buttonlist">
				    <input type="hidden" id="channelId" />
					<button id="saveEms" type="button" loxiaType="button" class="confirm" >保存</button>
				   </div>
					<table id="tbl-transRole-list"></table>
					<div id="pager2"></div>
					<div class="buttonlist">
							<button id="add" type="button" loxiaType="button" class="confirm" >新建</button>
							<button id="update" type="button" loxiaType="button" class="confirm" >编辑</button>
							<button id="addDefault" type="button" loxiaType="button" class="confirm">设置默认规则</button>
							<button id="back" type="button" loxiaType="button">返回</button>
					</div>
				</div>
			</div>
			<!-- <div id = "m_tab-2">
				<table>
					<tr>
						<td colspan = "2"  height = "30px"><label id="emsMsg" style="color:red; font-size:12px">注意：多个关键字以英文逗号“,”分隔！如地址只包含关键字优先推荐EMS，当地址包含排除关键字时不会优先推荐EMS</label></td>
					</tr>
					<tr>
						<td class="label"  width="80px">包含关键字： </td>
						<td>
							<textarea loxiaType="input" trim="true" maxlength="500" id = "includeKeyWord" name="includeKeyWord"></textarea>
						</td>
					</tr>
					<tr>
						<td class="label"  width="80px">排除关键字： </td>
						<td>
							<textarea loxiaType="input" trim="true" maxlength="500" id = "exIncludeKeyWord" name="exIncludeKeyWord"></textarea>
						</td>
					</tr>
				</table>
				<div class="buttonlist">
					<button id="saveEms" type="button" loxiaType="button" class="confirm" >保存</button>
				</div>
			</div> -->
			<div id = "m_tab-3">
				<form id="transRoleAccordForm" name="transRoleAccordForm">
				<table width="49%">
					<tr>
						<td class="label" width="5%">渠道编码:</td>
						<td width="12%"><input loxiaType="input" name="trac.channelCode" id="channelCode" trim="true"/></td>
						<td class="label" width="5%">渠道名称:</td>
						<td width="12%"><input loxiaType="input" name="trac.channelName" id="channelName" trim="true"/></td>
						<td class="label" width="3%">状态:</td>
						<td width="12%">
							<select id="intStatus" name="trac.intStatus" loxiaType="select"  loxiaType="select">
											<option value="">--请选择--</option>
											<option value="1">新建</option>
											<option value="10">已经执行</option>
											<option value="0">取消/失败</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="label" width="5%">规则编码:</td>
						<td width="12%"><input loxiaType="input" name="trac.roleCode" id="roleCode" trim="true"/></td>
						<td class="label" width="5%">规则名称:</td>
						<td width="12%"><input loxiaType="input" name="trac.roleName" id="roleName" trim="true"/></td>
						<td></td>
						<td></td>
					</tr>
				</table>
				</form>
				<div class="buttonlist">
					<button loxiaType="button" class="confirm" id="queryTransRoleAccord"><s:text name="button.query"></s:text></button>
					<button type="button" loxiaType="button" id="resetTransRoleAccord"><s:text name="button.reset"></s:text></button><br/>
				</div>
				<table id="tbl-TransRoleAccord-list">
				</table>
				<div id="pager5"></div>
				<div class="buttonlist">
					<form method="post" enctype="multipart/form-data" id="importFormFile" name="importFormFile" target="pdaLogFrame">	
						<input type="file" name="file" loxiaType="input" id="file1" style="width:200px"/>
						<button type="button" loxiaType="button" class="confirm" id="import" >导入</button>
			        	<a id="downloadTemplate" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=渠道快递规则状态变更.xls&inputPath=tplt_import_channel_trans_exchange.xls">
							<span class="ui-button-text">模版文件下载</span>
						</a>
					</form>
				<iframe id="pdaLogFrame" name="pdaLogFrame" class="hidden"></iframe>
				</div>
				<!-- <div class="buttonlist">
					<button id="saveTransRoleAccord" type="button" loxiaType="button" class="confirm" >保存</button>
				</div> -->
				
				<div id="btnEdit_tab">
					<table>
						<tr>
							<td class="label">规则编码:</td>
							<td><input loxiaType="input" id="editChannelCode" name="editChannelCode"
								width="120px" readonly="readonly"/>
							</td>
							<td class="label">渠道名称:</td>
							<td><input loxiaType="input" id="editChannelName" name="editChannelName"
								width="120px" readonly="readonly" />
							</td>
						</tr>
						<tr>
							<td class="label">执行时间:</td>
							<td><input loxiaType="date" style="width: 150px" name="changeTime" id="changeTime" showTime="true" readonly="readonly"/>
							</td>
							<td class="label">是否启用:</td>
							<td><select loxiaType="select" name="roleIsAvailable" id="roleIsAvailable" width="120px">
									<option value="">--请选择状态--</option>
									<option value="1">是</option>
									<option value="0">否</option>
							</select></td>
						</tr>
						<tr>
							<td class="label">优先级:</td>
							<td><input loxiaType="input" id="priority" name="priority"
								width="120px"/>
							</td>
							<td class="label">状态:</td>
							<td><select loxiaType="select" name="strChannelTransStatus" id="strChannelTransStatus" width="120px" readonly="readonly">
										<option value="">--请选择状态--</option>
										<option value="1">新建</option>
										<option value="10">已经执行</option>
										<option value="0">取消/失败</option>
							</select></td>
						</tr>
						<tr>
							<td class="label">创建人:</td>
							<td><input loxiaType="input" id="editCreateName" name="editCreateName"
								width="120px" readonly="readonly"/>
							</td>
							<td class="label">最后执行时间:</td>
							<td><input loxiaType="input" id="editLastOperation" name="editLastOperation"
								width="120px" readonly="readonly" />
							</td>
						</tr>
						<tr>
							<td><input loxiaType="input" id="transRoleAccordId" name="transRoleAccordId"
								width="120px" readonly="readonly" hidden="true"/>
							</td>
						</tr>
						<tr>
							<td><button id="saveEditTransRoleAccord" type="button" loxiaType="button"
									class="confirm">保存</button>
							</td>
							<td><button id="editTransRoleAccordBack" type="button" loxiaType="button">返回</button>
							</td>
						</tr>
					</table>
				</div>
		</div>
		</div>
		
		<div id="dialog_chooseArea">
			<table id="tbl-transArea-list"></table>
		</div>
		<div id="dialog_newArea">
			<table>
				<tr>
					<td width="55px"><label><b>编码：</b></label></td>
					<td><input loxiaType="input" id="AreaCode" name="code" width="120px"></input></td>	
					<td><label><b>名称：</b></label></td>
					<td><input loxiaType="input" id="AreaName" name="name" width="120px"></input></td>
				</tr>
				<tr>
					<td ><b>选择文件:</b></td>
					<td  align="center">
						<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
							<input type="file" name="file" loxiaType="input" id="file" style="width:150px"/>
						</form>
					</td>
					<td colspan="2">
		           		<a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=配送范围导入.xls&inputPath=tplt_import_tracs_area.xls">模板文件下载</a>
			        </td>
				</tr>
			</table>
			<div class="buttonlist">
					<button id="areaNew" type="button" loxiaType="button" class="confirm" >创建</button>
					<button id="areaCancel" type="button" loxiaType="button" >取消</button>
			</div>
		</div>
		<div id="dialog_editArea">
			<div id = "editOneTable" style="display:none">
				<table id="tbl-edit-transArea-list"></table>
			</div>
			<div id = "editTwoTable" style="display:none">
				<table>
					<tr>
						<td width="55px"><label><b>编码：</b></label></td>
						<td><input loxiaType="input" id="AreaEditCode" name="name" width="120px"></input></td>	
						<td><label><b>名称：</b></label></td>
						<td><input loxiaType="input" id="AreaEditName" name="name" width="120px"></input></td>
					</tr>
					<tr>
						<td ><b>选择文件:</b></td>
						<td  align="center">
							<form method="post" enctype="multipart/form-data" id="importEditForm" name="importForm" target="upload">	
								<input type="file" name="file" loxiaType="input" id="Editfile" style="width:150px"/>
							</form>
						</td>
						<td colspan="2">
			           		<button loxiaType="button" class="confirm" id="Editexport">导出明细</button>
				        </td>
					</tr>
					<tr>
						<td width="55px"><label><b>状态：</b></label></td>
						<td>
							<select id="SelEditStatusOpc" name="status" loxiaType="select" loxiaType="select">
										<option value="1">正常</option>
										<option value="2">禁用</option>
							</select>
						</td>	
					</tr>
				</table>
				<div class="buttonlist">
						<button id="areaEditNew" type="button" loxiaType="button" class="confirm" >编辑</button>
						<button id="areaEditCancel" type="button" loxiaType="button" >返回</button>
				</div>
			</div>
		</div>
		<div id="dialog_addSku">
			<form id="form_query" name="form_query">
				<table width="80%">
						<tr>
							<td width="15%"><s:text name="label.warehouse.inpurchase.skucode"></s:text></td>
							<td width="25%"><input loxiaType="input" trim="true" name="skuCom.code" /></td>
							<td width="15%"><s:text name="label.warehouse.pl.sta.sku.barcode"></s:text></td>
							<td width="25%"><input loxiaType="input" trim="true" name="skuCom.barCode" /></td>
						</tr>
						<tr>
							<td><s:text name="label.warehouse.sku.name"></s:text></td>
							<td><input loxiaType="input" trim="true" name="skuCom.name" /></td>
							<td><s:text name="label.warehouse.inpurchase.brand"></s:text></td>
							<td>
								<select class="special-flexselect" id="brandSelect" name="skuCom.brandName" data-placeholder="请选择品牌">
								</select>
							</td>
						</tr>
				</table>
			</form>
			<div class="buttonlist">
					<button type="button" loxiaType="button" class="confirm" id="searchSku"><s:text name="button.search"></s:text></button>
					<button type="button" loxiaType="button" id="resetSku"><s:text name="button.reset"></s:text></button>
			</div>
			<table id="tbl-commodity-query"></table>
			<div id="pager3"></div>
		</div>
		
		<div id="dialog_addTag">
			<form id="form_query_Tag" name="form_query_Tag">
				<table width="70%">
						<tr>
							<td width="8%">编码</td>
							<td width="27%"><input loxiaType="input" trim="true" name="tag.code" /></td>
							<td width="8%">名称</td>
							<td width="27%"><input loxiaType="input" trim="true" name="tag.name" /></td>
						</tr>
				</table>
			</form>
			<div class="buttonlist">
					<button type="button" loxiaType="button" class="confirm" id="searchTag"><s:text name="button.search"></s:text></button>
					<button type="button" loxiaType="button" id="resetTag"><s:text name="button.reset"></s:text></button>
			</div>
			<table id="tbl-tag-query"></table>
			<div id="pager4"></div>
		</div>
		<iframe id="upload" name="upload" class="hidden"></iframe>
	</body>
</html>