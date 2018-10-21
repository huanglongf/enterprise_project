<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title>ESPRIT门店维护</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/espritStore-edit.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style>
.showborder {
	border: thin;
}
.divFloat{
	float: left;
	margin-right: 10px;
}
</style>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
	<div id="div-sta-list">
		<form id="form_query">
			<table>
				<tr>
					<td class="label" >门店名称:</td>
					<td>
						<input loxiaType="input" trim="true" name="espritStoreCommand.name" id="name2" maxlength="80"/>
					</td>
					<td class="label">门店编码:</td>
					<td>
						<input loxiaType="input" trim="true" name="espritStoreCommand.code" id="code2" maxlength="80"/>
					</td>
					<td class="label" >城市编码:</td>
					<td>
						<td><input loxiaType="input" trim="true" name="espritStoreCommand.cityCode" id="cityCode2" maxlength="80"/></td>
					</td>
					<td class="label" >门店GLN编码:</td>
					<td>
						<td><input loxiaType="input" trim="true" name="espritStoreCommand.gln" id="gln2" maxlength="80"/></td>
					</td>
					<td class="label" >城市GLN编码:</td>
					<td>
						<td><input loxiaType="input" trim="true" name="espritStoreCommand.cityGln" id="cityGln2" maxlength="80"/></td>
					</td>
				</tr>
				<tr>
					<td class="label">联系人:</td>
					<td>
						<input loxiaType="input" trim="true" name="espritStoreCommand.contacts" id="contacts2" maxlength="80"/>
					</td>
					<td class="label">电话:</td>
					<td>
						<input loxiaType="input" trim="true" name="espritStoreCommand.telephone" id="telephone2" maxlength="80"/>
					</td>
				</tr>	
				
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search">查询</button>
			<button loxiaType="button" id="reset"><s:text name="button.reset"/></button>
		</div>
		<table id="tbl-inbound-purchase"></table>
		<div id="pager"></div>
		<div class="buttonlist">
				<button loxiaType="button" class="confirm" id="update">修改</button>
				<button loxiaType="button" class="confirm" id="delete">删除</button>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button loxiaType="button" class="confirm" id="create">新增</button>
		</div>
	</div>
	<div id="dialog_create">
		
			<table>
				<tr>
				<td class="label" ><font style="color: red;">*</font>门店名称:</td>
					<td>
						<input loxiaType="input" trim="true" name="espritStore.name" id="nameS" maxlength="100"/>
					</td>
					<td class="label"><font style="color: red;">*</font>门店编码:</td>
					<td>
						<input loxiaType="input" trim="true" name="espritStore.code" id="codeS" maxlength="100"/>
					</td>
					
				</tr>
				<tr>	
					<td class="label"><font style="color: red;">*</font>城市编码:</td>
					<td>
						<input loxiaType="input" trim="true" name="espritStore.cityCode" id="cityCode" maxlength="200"/>
					</td>
					<td class="label" ><font style="color: red;">*</font>门店GLN编码:</td>
					<td>
						<input loxiaType="input" trim="true" name="espritStore.gln" id="gln" maxlength="100"/>
					</td>
					
				</tr>
				<tr>	
					<td class="label" ><font style="color: red;">*</font>城市GLN编码:</td>
					<td>
						<input loxiaType="input" trim="true" name="espritStore.cityGln" id="cityGln" maxlength="100"/>
					</td>
					<td class="label" ><font style="color: red;">*</font>省:</td>
						<td>
							<input loxiaType="input" trim="true" name="espritStore.province" id="province" maxlength="100"/>
					</td>
				</tr>
					<td class="label"><font style="color: red;">*</font>市:</td>
					<td>
						<input loxiaType="input" trim="true" name="espritStore.city" id="city" maxlength="100"/>
					</td>
					<td class="label" ><font style="color: red;">*</font>区:</td>
					<td>
						<input loxiaType="input" trim="true" name="espritStore.district" id="district" maxlength="100"/>
					</td>
				</tr>
				
				<tr>	
					<td class="label"><font style="color: red;">*</font>详细地址:</td>
					<td colspan="3">
						<input loxiaType="input" trim="true" name="espritStore.address" id="address" maxlength="200"/>
					</td>
					
				</tr>
				<tr>	
					<td class="label"><font style="color: red;">*</font>联系人:</td>
					<td>
						<input loxiaType="input" trim="true" name="espritStore.contacts" id="contacts" maxlength="100"/>
					</td>
					<td class="label" ><font style="color: red;">*</font>电话:</td>
					<td>
						<input loxiaType="input" trim="true" name="espritStore.telephone" id="telephone" maxlength="50"/>
					</td>
				</tr>
				<tr>	
					<td>
						<button loxiaType="button" id="resetS">重置</button>
					</td>
					<td></td>
					<td></td>
					<td >
						<button loxiaType="button" class="confirm" id="save">保存</button>
					</td>
				</tr>
			</table>
		</div>
	<div id="dialog_update">
			<table>
				<tr>
					<td class="label" ><font style="color: red;">*</font>门店名称:</td>
					<td>
						<input loxiaType="input" trim="true" name="espritStore.name" id="nameU"   maxlength="80"/>
					</td>
					<td class="label"><font style="color: red;">*</font>门店编码:</td>
					<td>
						<input loxiaType="input" trim="true" name="espritStore.code" id="codeU"   maxlength="80"/>
						<input type="hidden" trim="true" name="espritStore.id" id="idU"  disabled="disabled"/>
					</td>
				</tr>
				<tr>	
					<td class="label"><font style="color: red;">*</font>城市编码:</td>
					<td>
						<input loxiaType="input" trim="true" name="espritStore.cityCode" id="cityCodeU" maxlength="200"/>
					</td>
					<td class="label" ><font style="color: red;">*</font>门店GLN编码:</td>
					<td>
						<input loxiaType="input" trim="true" name="espritStore.gln" id="glnU" maxlength="100"/>
					</td>
				</tr>
				<tr>	
				<td class="label" ><font style="color: red;">*</font>城市GLN编码:</td>
					<td>
						<input loxiaType="input" trim="true" name="espritStore.cityGln" id="cityGlnU" maxlength="100"/>
				</td>
				<td class="label" ><font style="color: red;">*</font>省:</td>
					<td>
						<input loxiaType="input" trim="true" name="espritStore.province" id="provinceU" maxlength="100"/>
					</td>
					
				</tr>
				<tr>	
					<td class="label"><font style="color: red;">*</font>市:</td>
					<td>
						<input loxiaType="input" trim="true" name="espritStore.city" id="cityU" maxlength="100"/>
					</td>
					<td class="label" ><font style="color: red;">*</font>区:</td>
					<td>
						<input loxiaType="input" trim="true" name="espritStore.district" id="districtU" maxlength="100"/>
					</td>
				</tr>
				<tr>	
					<td class="label"><font style="color: red;">*</font>详细地址:</td>
					<td colspan="3">
						<input loxiaType="input" trim="true" name="espritStore.address" id="addressU" maxlength="200"/>
					</td>
				</tr>
				<tr>	
					<td class="label"><font style="color: red;">*</font>联系人:</td>
					<td>
						<input loxiaType="input" trim="true" name="espritStore.contacts" id="contactsU" maxlength="100"/>
					</td>
					<td class="label" ><font style="color: red;">*</font>电话:</td>
					<td>
						<input loxiaType="input" trim="true" name="espritStore.telephone" id="telephoneU" maxlength="50"/>
					</td>
				</tr>
				<tr>	
					<td>
						<button loxiaType="button" id="resetU">重置</button>
					</td>
					<td></td>
					<td></td>
					<td>
						<button loxiaType="button" class="confirm" id="saveU">保存</button>
					</td>
				</tr>
			</table>
		</div>
</body>
</html>