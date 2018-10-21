
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<%@include file="/common/meta.jsp"%>
		<title>仓库配货清单自动创建规则维护</title>
		<script type="text/javascript" src="<s:url value='/scripts/frame/auto-pickinglist-role-maintain.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
		<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/priority_city_config.js' includeParams='none' encode='false'/>"></script>
		
		<style type="text/css">
			.tips{
				width:100%; height: 50%; text-align:center; color:red; font-size: 40px; font-weight: bold;
			}
		</style>
	</head>
	<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="dialog_roleList">
		<form id="tagForm" name="tagForm">
			<table width="80%" id="queryTable">
				<tr>
					<td class="label" width="10%">规则编码：</td>
					<td width="20%"><input loxiaType="input" name="role.code"
						trim="true" /></td>
					<td class="label" width="10%">规则名称：</td>
					<td width="20%"><input loxiaType="input" name="role.name"
						trim="true" /></td>
					<td class="label" width="10%">状态：</td>
					<td width="20%"><select loxiaType="select" name="status">
							<option value="">--请选择状态--</option>
							<option value="1">正常</option>
							<option value="0">禁用</option>
					</select>
					</td>
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
		<div id="role-list">
			<table id="tbl-role-list"></table>
			<div id="pager"></div>
		</div>
		<div class="buttonlist">
			<button id="addRoleBtn" type="button" loxiaType="button"
				class="confirm">新建</button>
		</div>
	</div>
	<div id="dialog_addRole" style="display:none;">
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
				<td><button id="saveRoleBtn" type="button" loxiaType="button" class="confirm">创建</button></td>
				<td><button id="addRoleBack" type="button" loxiaType="button">取消</button></td>
			</tr>
		</table>
	</div>
	<div id="dialog_roleDetail"  style="display:none;">
		<table>
			<tr>
				<td class="label">编码:</td>
				<td><input type="hidden" id="roleId" name="role.id"
					width="120px" /> <input loxiaType="input" id="roleCode"
					name="role.code" width="120px" /></td>
				<td class="label">名称:</td>
				<td><input loxiaType="input" id="roleName" name="role.name"
					width="120px" />
				</td>
			</tr>
			<tr>
				<td class="label">状态:</td>
				<td><select loxiaType="select" id="roleStatus" trim="true"
					name="status" data-placeholder="请选择状态">
						<option value="1">正常</option>
						<option value="0">禁用</option>
				</select>
				</td>
			</tr>
		</table>
		<div id="detail-list">
			<table id="tbl-role-detail-list"></table>
		</div>
		<div id="tmt-list">
			<table id="tbl-tmt-list"></table>
		</div>
		<br/>
		<div id="detail-edit">
			<table>
			<tr>
				<td  class="label">是否允许指定物流商混合创建：</td>
				<td><input type="checkbox" id = "isTransAfter" name = "isTransAfter"/></td>
			</tr>
				<tr>
					<td  class="label">商品分类：</td>
					<td><select loxiaType="select" id="categorySelect" trim="true"
						name="category" data-placeholder="请选择分类">
					</select></td>
					<td class="label">商品大小：</td>
					<td><select loxiaType="select" id="skuSizeSelect" trim="true"
						name="skuSize" data-placeholder="请选择商品大小">
					</select></td>
					<td class="label">是否包含SN号</td>
					<td>
						<select loxiaType="select" id="isSn">
							<option value="">--请选择--</option>
							<option value="true">是</option>
							<option value="false">否</option>
						</select>
					</td>
				</tr>
				<tr>
					<td  class="label">优先级：</td>
					<td><input loxiaType="input" id="sort" name="sort"
					width="120px" />
					</td>
					<td  class="label">最大单数：</td>
					<td><input loxiaType="input" id="maxOrder" name="maxOrder"
					width="120px" />
					</td>
					<td  class="label">最小单数：</td>
					<td><input loxiaType="input" id="minOrder" name="minOrder"
					width="120px" />
					</td>
				</tr>
				<tr>
					<td  class="label">类型：</td>
					<td><select loxiaType="select" id="type" trim="true"
						name="type" data-placeholder="请选择类型">
							<option value="">--请选择--</option>
							<option value="1">单件</option>
							<option value="10">多件</option>
							<!-- <option value="2">套装组合</option>
							<option value="20">团购</option>
							<option value="30">秒杀</option> -->
					</select>
					</td>
					<td  class="label">优先发货城市：</td>
					<td>
						<select name="city" id="priorityCity" loxiaType="select">
							<option value="">请选择</option>
						</select>
					</td>
				</tr>
			</table>
			<div class="buttonlist">
				<button id="addRoleDetail" type="button" loxiaType="button" class="confirm">
					新增规则
				</button>
			</div>
			<div class="buttonlist">
				<button id="saveRoleDetailBtn" type="button" loxiaType="button" class="confirm">
					保存修改
				</button>
				<button id="addRoleDetailBack" type="button" loxiaType="button">返回</button>
			</div>
		</div>
	</div>
</body>
</html>