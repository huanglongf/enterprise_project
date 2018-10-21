
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<%@include file="/common/meta.jsp"%>
		<title>商品标签维护</title>
		<script type="text/javascript" src="<s:url value='/scripts/frame/sku-tag-maintain.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
		<style type="text/css">
			.tips{
				width:100%; height: 50%; text-align:center; color:red; font-size: 40px; font-weight: bold;
			}
		</style>
	</head>
	<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="dialog_tagList">
		<form id="tagForm" name="tagForm">
			<table width="80%" id="queryTable">
				<tr>
					<td class="label" width="10%">标签编码：</td>
					<td width="20%"><input loxiaType="input" name="tag.code"
						id="tagCode" trim="true" />
					</td>
					<td class="label" width="10%">标签名称：</td>
					<td width="20%"><input loxiaType="input" name="tag.name"
						id="tagName" trim="true" />
					</td>
					<td class="label" width="10%">状态：</td>
					<td width="20%"><select loxiaType="select" name="status"
						id="tagStatus">
							<option value="">--请选择状态--</option>
							<option value="1">正常</option>
							<option value="0">取消</option>
					</select></td>
				</tr>
			</table>
			<div class="buttonlist">
				<button id="search" type="button" loxiaType="button" class="confirm">
					<s:text name="button.query" />
				</button>
				<button id="reset" type="reset" loxiaType="button">
					<s:text name="button.reset" />
				</button>
			</div>
		</form>
		<div id="tag-list">
			<table id="tbl-tag-list"></table>
			<div id="pager"></div>
		</div>
		<div class="buttonlist">
			<button id="addTagBtn" type="button" loxiaType="button"
				class="confirm">创建</button>
		</div>
	</div>
	<div id="dialog_addTag">
		<table>
			<tr>
				<td class="label">编码:</td>
				<td><input loxiaType="input" id="code" name="code" width="120px"/></td>
			</tr>
			<tr>
				<td class="label">名称:</td>
				<td><input loxiaType="input" id="name" name="name" width="120px"/></td>
			</tr>
			<tr>
				<td class="label">类型:</td>
				<td><select loxiaType="select" name="type" id="type">
						<option value="">--请选择--</option>
						<option value="1">任意匹配标签</option>
						<option value="2">完全匹配标签</option>
						<option value="3">包含匹配标签</option>
						<option value="5">活动标签</option>
				</select>
				</td>
			</tr>
			<tr>
				<td><button id="addSkuTagBtn" type="button" loxiaType="button" class="confirm">创建</button></td>
				<td><button id="addTagBack" type="button" loxiaType="button">取消</button></td>
			</tr>
		</table>
	</div>
	<div id="skuTag_tabs" style="border:1px dashed #CCC;width:850px;display:none;">
		<ul>
			<li><a href="#skuTag_tabs_1" id="baseInfo">基础信息</a></li>
			<li><a href="#skuTag_tabs_2" id="refInfo">关联商品维护</a></li>
		</ul>
		<div id="skuTag_tabs_1">
			<table>
				<tr>
					<td class="label">编码:</td>
					<td><input type="hidden" id="skuTagId" name="skuTagId"
						width="120px" />
						<input loxiaType="input" id="skuTagCode" name="skuTagCode"
						width="120px" />
					</td>
					<td class="label">名称:</td>
					<td><input loxiaType="input" id="skuTagName" name="skuTagName"
						width="120px" />
					</td>
				</tr>
				<tr>
					<td class="label">状态:</td>
					<td><select loxiaType="select" name="skuTagStatus" id="skuTagStatus">
							<option value="">--请选择状态--</option>
							<option value="1">正常</option>
							<option value="0">取消</option>
					</select></td>
					<td class="label">类型:</td>
					<td><select loxiaType="select" name="skuTagType" id="skuTagType">
							<option value="">--请选择--</option>
							<option value="1">任意匹配标签</option>
							<option value="2">完全匹配标签</option>
							<option value="3">包含匹配标签</option>
							<option value="5">活动标签</option>
					</select></td>
				</tr>
				<tr>
					<td><button id="saveSkuTagBtn" type="button" loxiaType="button"
							class="confirm">保存</button>
					</td>
					<td><button id="addSkuTagBack" type="button" loxiaType="button">返回</button>
					</td>
				</tr>
			</table>
		</div>
		<div id="skuTag_tabs_2">
			<div id="dialog_skuRefList">
				<form id="skuRefForm" name="skuRefForm">
					<table width="100%" id="queryRefTable">
						<tr>
							<td class="label">商品编码:</td>
							<td><input loxiaType="input" name="skuCmd.code" id="skuCode" trim="true"/></td>
							<td class="label">商品名称:</td>
							<td><input loxiaType="input" name="skuCmd.name" id="skuName" trim="true"/></td>
							<td class="label">条码:</td>
							<td><input loxiaType="input" name="skuCmd.barCode" id="skuBarCode" trim="true"/></td>
						</tr>
						<tr>
							<td class="label">品牌名称:</td>
							<td><select loxiaType="select" id="brandName" trim="true" name="skuCmd.brandName" data-placeholder="请选择品牌"></select></td>
							<td class="label">客户:</td>
							<td><select loxiaType="select" id="customer" trim="true" name="skuCmd.customer.id" data-placeholder="请选客户"></select></td>
							<td></td>
							<td></td>
						</tr>
					</table>
					<div class="buttonlist">
						<button id="skuRefSearch" type="button" loxiaType="button"
							class="confirm">
							<s:text name="button.query" />
						</button>
						<button id="skuRefReset" type="reset" loxiaType="button">
							<s:text name="button.reset" />
						</button>
						<button id="skuRefBack" type="button" loxiaType="button">返回</button>
					</div>
				</form>
				<div id="skuRef-list">
					<table id="tbl-sku-ref-list"></table>
					<div id="refPager"></div>
				</div>
				<div id="skuRef-import">
					<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">
						<s:text name="导入所有关联明细"/><input type="file" loxiaType="input" id="skuFile" name="skuFile" style="width: 200px;"/>
						<button type="button" loxiaType="button" id="import" class="confirm"><s:text name="label.warehouse.inpurchase.import"/></button>
						<button type="button" loxiaType="button" id="export" class="confirm"><s:text name="导出明细"/></button>
						<a class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=关联商品维护.xls&inputPath=tplt_tag_ref_sku.xls" role="button">
						<span class="ui-button-text">模版文件下载</span>
						</a>
					</form>
				</div>
				<div><span style="color:#f00">导入为覆盖更新，请导出时慎重!</span></div>
			</div>
		</div>
	</div>
	<div id="skuTag_detail" style="border:1px dashed #CCC;width:850px;display:none;">
		<table style="padding-left:30px;">
			<tr>
				<td class="label">编码:</td>
				<td><input type="hidden" id="skuTagIdDetail"
					name="skuTagIdDetail" width="120px" /> <input loxiaType="input"
					id="skuTagCodeDetail" name="skuTagCodeDetail" width="120px" />
				</td>
				<td class="label">名称:</td>
				<td><input loxiaType="input" id="skuTagNameDetail"
					name="skuTagNameDetail" width="120px" />
				</td>
			</tr>
			<tr>
				<td class="label">状态:</td>
				<td><select loxiaType="select" name="skuTagStatusDetail"
					id="skuTagStatusDetail">
						<option value="">--请选择状态--</option>
						<option value="1">正常</option>
						<option value="0">取消</option>
				</select></td>
				<td class="label">类型:</td>
				<td><select loxiaType="select" name="skuTagType"
					id="skuTagTypeDetail">
						<option value="">--请选择类型--</option>
						<option value="5">活动标签</option>
				</select></td>
			</tr>
		</table>
		<div id="dialog_skuRefListDetail">
			<form id="skuRefFormDetail" name="skuRefFormDetail">
				<table width="100%" id="queryRefTableDetail">
					<tr>
						<td class="label">商品编码:</td>
						<td><input loxiaType="input" name="skuCmd.code" id="skuCodeDetail"
							trim="true" />
						</td>
						<td class="label">商品名称:</td>
						<td><input loxiaType="input" name="skuCmd.name" id="skuNameDetail"
							trim="true" />
						</td>
						<td class="label">条码:</td>
						<td><input loxiaType="input" name="skuCmd.barCode"
							id="skuBarCodeDetail" trim="true" />
						</td>
					</tr>
					<tr>
						<td class="label">品牌名称:</td>
						<td><select loxiaType="select" id="brandNameDetail" trim="true"
							name="skuCmd.brandName" data-placeholder="请选择品牌"></select>
						</td>
						<td class="label">客户:</td>
						<td><select loxiaType="select" id="customerDetail" trim="true"
							name="skuCmd.customer.id" data-placeholder="请选客户"></select>
						</td>
						<td></td>
						<td></td>
					</tr>
				</table>
				<div class="buttonlist">
					<button id="skuRefSearchDetail" type="button" loxiaType="button"
						class="confirm">
						<s:text name="button.query" />
					</button>
					<button id="skuRefResetDetail" type="reset" loxiaType="button">
						<s:text name="button.reset" />
					</button>
					<button id="skuRefBackDetail" type="button" loxiaType="button">返回</button>
				</div>
			</form>
			<div id="skuRef-list-detail">
				<table id="tbl-sku-ref-list-detail"></table>
				<div id="refPagerDetail"></div>
			</div>
		</div>
	</div>
	<iframe id="upload" name="upload" style="display:none;"></iframe>
</body>
</html>