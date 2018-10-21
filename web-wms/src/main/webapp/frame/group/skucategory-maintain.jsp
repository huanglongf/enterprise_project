<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title><s:text name="betweenlibary.title.inwarehouse"/></title>
<style type="text/css">
	h3{
		text-align:center;
	}
</style>
<script type="text/javascript" src="<s:url value='/scripts/frame/group/skucategory-maintain.js"' includeParams='none' encode='false'/>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<ul id="skuCategoryTree" animate="true"></ul>
	<hr />
	<div id="editDiv">
		<table>
			<tr>
				<td colspan="2" style="color:#f00">
				*注意事项：<br/>
				1、商品分类是否配货时显示规则在配货下拉时规定：若父分类为显示，则子分类不再作为配货下拉条件。故在操作将父分类配货显示时，子分类默认修改为不显示。<br/>
				2、删除分类时，如果该分类或其子分类下维护有商品，则不能将其删除。反之删除该分类及其包含的子分类。
				</td>
			</tr>
			<tr>
				<td>
					<form id="editForm" method="post">
						<table width="400px">
							<tr>
								<td colspan="2">
									<h3>编辑选中的分类</h3>
								</td>
							</tr>
							<tr>
								<td class="label">分类名称：</td>
								<td><input loxiaType="input" id="sname" name="sc.skuCategoriesName" trim="true"/><input loxiaType="input" id="sid" name="sc.id" hidden/></td>
							</tr>
							<tr>
								<td class="label">是否配货时显示：</td>
								<td>
									<select loxiaType="select" id="sispick" name="sc.isPickingCategories">
										<option value="true">显示</option>
										<option value="false">不显示</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="label">拣货模式订单商品分类数量：</td>
								<td >
									<input loxiaType="number" id="sqty" name="sc.sedPickingskuQty"/>
								</td>
							</tr>
							<tr>
								<div id = "editSku">
									<td class="label" id = "editSkuMaxLable">自动化小批次容量：</td>
									<td >
										<input loxiaType="input" trim="true" maxlength="6" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/[^0-9]/g,'')}"  value="<s:property value='sc.skuMaxLimit'/>"  name="sc.skuMaxLimit" id = "editSkuMaxLimit"/>
									</td>
								</div>
							</tr>
						</table>
					</form>
				</td>
				<td>
					<form id="addForm" method="post">
						<table width="400px">
							<tr>
								<td colspan="2">
									<h3>添加子分类</h3>
								</td>
							</tr>
							<tr>
								<td class="label">分类名称：</td>
								<td ><input loxiaType="input" id="sonsname" name="sc.skuCategoriesName" trim="true"/><input loxiaType="input" id="sid1" name="sc.skuCategories.id" hidden/></td>
							</tr>
							<tr>
								<td class="label">是否配货时显示：</td>
								<td >
									<select loxiaType="select" id="sonsispick" name="sc.isPickingCategories">
										<option value="true">显示</option>
										<option value="false">不显示</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="label">拣货模式订单商品分类数量：</td>
								<td >
									<input loxiaType="number" id="sonqty" name="sc.sedPickingskuQty"/>
								</td>
							</tr>
							<tr>
								<div id = "addSku">
									<td class="label" id = "addSkuMaxLable">自动化小批次容量：</td>
									<td >
										<input loxiaType="input" trim="true" maxlength="6" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/[^0-9]/g,'')}"  value="<s:property value='sc.skuMaxLimit'/>"  name="sc.skuMaxLimit" id = "addSkuMaxLimit"/>
									</td>
								</div>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<div class="buttonlist">
						<button type="button" loxiaType="button" class="confirm"
							id="save">
							<s:text name="button.save"/>
						</button>
						<button type="button" loxiaType="button" class="confirm"
							id="remove">
							删除
						</button>
					</div>
				</td>
				<td>
					<div class="buttonlist">
						<button type="button" loxiaType="button" class="confirm"
							id="saveson">
							<s:text name="button.add"/>
						</button>
					</div>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>