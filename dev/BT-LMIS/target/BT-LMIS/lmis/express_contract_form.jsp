<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ include file="/lmis/yuriy.jsp"%>
<title>LMIS</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>validator/css/style.css" />
<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>validator/css/demo.css"  />
<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>css/table.css" />
<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>daterangepicker/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />

<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
<script type="text/javascript" src="<%=basePath %>validator/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>validator/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
<script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
<script type="text/javascript" src="<%=basePath %>lmis/express_contract/js/express_contract_form.js"></script>
<script type="text/javascript" src="<%=basePath %>lmis/trans_moudle/js/trans.js"></script>
<script type="text/javascript" src="<%=basePath %>js/common.js"></script>

<script>
$(document).ready(function(){ 
	var status = $("#con_type").val();
	if(status=="1"){
		$("#myTab_kd").css("display","block");
		$("#myTab_wl").css("display","none");
		$("#kd_moudle_li").css("display","block");
		$("#wl_moudle_li").css("display","none");
	}
	if(status=="2"){
		$("#myTab_kd").css("display","none");
		$("#myTab_wl").css("display","block");
		$("#kd_moudle_li").css("display","none");
		$("#wl_moudle_li").css("display","block");
	}
});
</script>
</head>
<body>
	<div class="page-header" align="left">
		<h1>承运商合同信息创建及配置</h1>
	</div>
	<div class="col-xs-12">
		<div class="clearfix form-group no-margin-bottom no-padding-bottom no-border-bottom">
			<form id="express_contract_form" class="registerform no-margin-bottom no-padding-bottom no-border-bottom"
				role="form" action="${root}control/expressContractController/saveECM.do">
				<div class="no-margin-bottom no-padding-bottom no-border-bottom"
					style="width: 100%;" align="center">
					<table style="width: 100%;" class="form_table">
						<tr>
							<td class="right" nowrap="nowrap" width="30%">
								<label class="no-padding-right blue" for="contractType">
									合同类型&nbsp;: 
								</label>
							</td>
							<td class="left" nowrap="nowrap" width="30%">
								<input type="hidden" id="id" name="id" value="${expressContract.id }"/>
								<input type="hidden" id="eCC_id" name="eCC_id" value="${eCC.id }"/>
								<c:if test="${not empty expressContract.id}">
									<input type="hidden" id="contractType" name="contractType" value="${expressContract.contract_type }"/>
								</c:if>
								<c:if test= "${empty expressContract.id }" >
									<select id="contractType" name="contractType"  style="width: 100%;" onchange="selBelongTo();">
										<option value= "0" >请选择</option>
										<option value= "1" ${expressContract.contract_type == 1? "selected= 'selected'": "" } >快递</option>
										<option value= "2" ${expressContract.contract_type == 2? "selected= 'selected'": "" } >物流</option>
									</select>
								</c:if>
								<c:if test="${not empty expressContract.id}">
									<c:if test="${expressContract.contract_type==1}">
										<input type="text" readonly="readonly" value="快递"/>
									</c:if>
									<c:if test="${expressContract.contract_type==2}">
										<input type="text" readonly="readonly" value="物流"/>
									</c:if>
								</c:if>
							</td>
							<td nowrap="nowrap"  width="30%"  class="left"></td>
						</tr>
						<c:if test= "${empty expressContract.id}" >
							<tr id="kd" style = "display: none">
								<td class="right">
								 	<label class="no-padding-right blue" for="express">
								 		所属快递&nbsp;: 
								 	</label>
								</td>
								<td class="left">
								 	<select id="express" name="express" style="width: 100%;" data-edit-select="1">
										<c:forEach items="${expressList}" var="expressList">
											<option value= "${expressList.transport_code }" >${expressList.transport_name }</option>
										</c:forEach>
									</select>
								</td>
								<td class="left"><div class="Validform_checktip"></div></td>	
							</tr>
							<tr id="wl" style = "display: none">
								<td class="right">
								 	<label class="no-padding-right blue" for="physicalDistribution">
								 		所属物流&nbsp;: 
								 	</label>
								</td>
								<td class="left">
									<select id="physicalDistribution" name="physicalDistribution"  style="width: 100%;" onchange="getGood_type()">
										<c:forEach items= "${physicaldistributionList }" var= "physicaldistributionList" >
											<option value= "${physicaldistributionList.transport_code }" >${physicaldistributionList.transport_name }</option>
										</c:forEach>
									</select>
								</td>
								<td class="left"><div class="Validform_checktip"></div></td>	
							</tr>
						</c:if>
						<c:if test="${not empty expressContract.id}">
							<c:if test="${expressContract.contract_type==1 }">
								<tr id="dp" >
									<td class="right">
									 	<label class="no-padding-right blue" for="express">
									 		所属快递&nbsp;: 
									 	</label>
									 </td>
									<td class="left">
										<input type="hidden" value="${contractOwner.contractOwner }" id="contractOwner" name="contractOwner"/>
										<input type="text" readonly="readonly" value="${contractOwner.contractOwnerName }"/>
									</td>
									<td class="left"><div class="Validform_checktip"></div></td>
								</tr>
							</c:if>
							<c:if test="${expressContract.contract_type==2 }">
								<tr id="pp">
									<td class="right">
									 	<label class="no-padding-right blue" for="physicalDistribution">
									 		所属物流&nbsp;: 
									 	</label>
									 </td>
									<td class="left">
									    <input type="hidden" value="${contractOwner.contractOwner }" id="contractOwner" name="contractOwner"/>
										<input type="text" readonly="readonly" value="${contractOwner.contractOwnerName }"/>
									</td>
									<td class="left"><div class="Validform_checktip"></div></td>
								</tr>
							</c:if>
						</c:if>
						<tr>
							<td class="right" nowrap="nowrap" width="30%">
								<label class="no-padding-right blue" for="contractCycle">
									合同周期&nbsp;: 
								</label>
							</td>
							<td class="left" nowrap="nowrap" width="30%">
		               			<input 
		               				id="contractCycle" 
		               				name="contractCycle" 
		               				class="form-control" 
		               				type="text"
		               				readonly style="width: 100%"
		               				<c:if test="${not empty expressContract.id }">
		               					value="${expressContract.contract_start } - ${expressContract.contract_end }" 
		               				</c:if>
		               				<c:if test="${empty expressContract.id }">
		               					value="${contract_start } - ${contract_start }" 
		               				</c:if>/>
							</td>
							<td nowrap="nowrap"  width="30%"  class="left"></td>
						</tr>
						<tr>
							<td class="right" nowrap="nowrap" width="30%">
								<label class="no-padding-right blue" for="balanceDate">
									结算日期&nbsp;: 
								</label>
							</td>
							<td class="left" nowrap="nowrap" width="30%">
								<select id= "balanceDate" name= "balanceDate" style= "width: 100%;" >
									<c:forEach var= "date" begin= "1" end= "31" >
										<option value= "${date }" ${expressContract.settle_date == date? "selected= 'selected'": "" } >
											${date }日
										</option>
									</c:forEach>
								</select>
							</td>
							<td nowrap="nowrap"  width="30%"  class="left">
								&nbsp;
								<span class="red" style="font-size: 10px;">
									*结算周期是以此日期为尾节点的一个自然月（包含此日期）
								</span>
							</td>
						</tr>
						<tr>
							<td class="right">
							 	<label class="no-padding-right blue" for="contractCode">
							 		合同编号&nbsp;: 
							 	</label>
					 		</td>
					 		<td class="left">
								<input id="contractCode" name="contractCode" type="text" placeholder="如：ZXV001" 
									datatype="*" nullmsg="请输入合同编号！" errormsg="必须填写合同编号！" value="${expressContract.contract_no }"/>
					 		</td>
					 		<td class="left"><div class="Validform_checktip">必填</div></td>	
						</tr>
						<tr>
							<td class="right">
							 	<label class="no-padding-right blue" for="contractName">
							 		合同名称&nbsp;: 
							 	</label>
							</td>
							<td class="left">
								<input id="contractName" name="contractName" type="text" placeholder="如：合同名称"
									datatype="*" nullmsg="请输入合同名称！" errormsg="必须填写合同名称！" value="${expressContract.contract_name }" />
					 		</td>
					 		<td class="left"><div class="Validform_checktip">必填</div></td>		
						</tr>
						<tr>
							 <td class="right">
							 	<label class="no-padding-right blue" for="contractVersion">
							 		版本&nbsp;: 
							 	</label>
							 </td>	
							 <td class="left">
								<input id="contractVersion" name="contractVersion" type="text" placeholder="如：0.1.0"
									datatype="*" nullmsg="请输入合同版本！" errormsg="必须填写合同版本！" value="${expressContract.contract_version }"/>
							 </td>
							 <td class="left"><div class="Validform_checktip">必填</div></td>		
						</tr>
						<tr>
							<td class="right">
							 	<label class="no-padding-right blue" for="contact">
							 		联系人&nbsp;: 
							 	</label>
							 </td>
							 <td class="left">
								<input id="contact" name="contact" type="text" placeholder="如：张三"
									datatype="*" nullmsg="请输入联系人！" errormsg="必须填写联系人！" value="${expressContract.contact }"/>
							 </td>
							 <td class="left"><div class="Validform_checktip">必填</div></td>		
						</tr>
						<tr>
							<td class="right">
							 	<label class="no-padding-right blue" for="tel">
							 		联系电话&nbsp;: 
							 	</label>
							 </td>
							 <td class="left">
							 	<input id="tel" name="tel" type="text" placeholder="如：12345578901" 
							 		datatype="m"  nullmsg="请输入联系电话！" errormsg="联系电话格式不正确！" value="${expressContract.tel }"/>
							 </td>
							 <td class="left"><div class="Validform_checktip">必填</div></td>		
						</tr>
						<c:if test="${not empty expressContract.id}">
							<tr>
								<td class="right" nowrap="nowrap" width="30%">
									<label class="no-padding-right blue" for="cb_contractValidity">
										合同有效性&nbsp;: 
									</label>
								</td>
								<td class="left">
									<input 
										id="cb_contractValidity"
										name="cb_contractValidity"
										type="checkbox" 
										class="ace ace-switch ace-switch-5" 
										${expressContract.validity==1 ? 'checked="checked"' : '' } 
										onchange="checkUniqueValidity();"/>
									<span class="lbl"></span>
								</td>
							</tr>
						</c:if>
						<tr>
							<td class="right">
							 	<label class="no-padding-right blue" for="createBy">
							 		创建人&nbsp;: 
							 	</label>
							 </td>
							 <td class="left">
								<input id="createBy" name="createBy" type="text" placeholder="如：张三" readonly
									value="${expressContract.create_user }"/>
							 </td>
							 <td class="left"><div class="Validform_checktip"></div></td>	
						</tr>
						<tr>
							<td class="right">
							 	<label class="no-padding-right blue" for="createTime">
							 		创建时间&nbsp;: 
							 	</label>
							</td>
							<td class="left">
								<input type="text" id="createTime" name="createTime" readonly
									value="${expressContract.create_time }"/>
							</td>
							<td class="left"><div class="Validform_checktip"></div></td>	
						</tr>
						<tr>
							<td class="right">
							 	<label class="no-padding-right blue" for="updateBy">
							 		更新人&nbsp;: 
							 	</label>
							</td>
							<td class="left">
								<input id="updateBy" name="updateBy" type="text" placeholder="如：张三" readonly
									value="${expressContract.update_user }"/>
							 </td>
							 <td class="left"><div class="Validform_checktip"></div></td>	
						</tr>
						<tr>
							<td class="right">
							 	<label class="no-padding-right blue" for="updateTime">
							 		更新时间&nbsp;: 
							 	</label>
							</td>
							<td class="left">
								<input type="text" id="updateTime" name="updateTime" readonly
									value="${expressContract.update_time }"/>
							</td>
							<td class="left"><div class="Validform_checktip"></div></td>	
						</tr>
						<tr>
							<td class="right">
							</td>
							<td class="left">
								<div class="form-actions no-margin-bottom no-padding-bottom no-border-bottom"
									align="center">
									<button class="btn btn-info" type="button" onclick = "saveECM();">
										<i class="icon-ok bigger-110"></i> 提交
									</button>
									&nbsp; &nbsp; &nbsp;
									<button class="btn" type="reset" onclick="openDiv('${root}control/contractController/list.do');">
										<i class="icon-undo bigger-110"></i> 返回
									</button>
								</div>
							</td>
							<td class="left">
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
		<div id= "carrier_contract_config" class= "form-group" style= "width: 100%; display: none;" >
			<%@ include file= "/lmis/express_contract/carrierContractConfig.jsp" %>
		</div>
	</div>
</body>
</html>
