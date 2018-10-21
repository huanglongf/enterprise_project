<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp"%>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
      	<link href="<%=basePath %>daterangepicker/font-awesome.min.css" rel="stylesheet">
	    <link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
	    <script type="text/javascript" src="<%=basePath %>daterangepicker/jquery-1.8.3.min.js"></script>
	    <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
	    <script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
	    <script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
		<script type="text/javascript" src="<%=basePath %>validator/js/Validform_v5.3.2_min.js"></script>
		<script type="text/javascript" src="<%=basePath %>lmis/express_contract/js/express_contract_form.js"></script>
		<script type="text/javascript" src="<%=basePath %>lmis/trans_moudle/js/trans.js"></script>
		<script type="text/javascript" src="<%=basePath %>lmis/sosp/js/sosp.js"></script>
	    <!-- BT-JS工具 -->
	    <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
		<!--Validform  -->
		<link type="text/css" href="<%=basePath %>validator/css/style.css" rel="stylesheet" media="all" />
		<link type="text/css" href="<%=basePath %>validator/css/demo.css" rel="stylesheet" />
		<link type="text/css" href="<%=basePath %>css/table.css" rel="stylesheet" />
		<link href="<%=basePath %>daterangepicker/font-awesome.min.css" rel="stylesheet">
		<link href="<%=basePath %>/css/table.css" type="text/css" rel="stylesheet" />
		
		<script type="text/javascript">
			window.jQuery || document.write("<script src='<%=basePath %>assets/js/jquery-2.0.3.min.js'>" + "<"+"/script>");
			$(function(){
				$(".registerform").Validform({
					tiptype:2
				});
			});
			function tj(){
				var params = $(".registerform").serialize();
				$.ajax({
                    url: "${root}control/sospController/saveMain.do",
                    type: "POST",
                    data: params,
                    dataType: "json",
                    success: function(data){
                    	$("#id").attr("value", data.id);
    					$("#ssc_cb_id").attr("value",data.id);
    					$("#ccf_sheet").css("display","block");
    					$("#ccf_sheet_div").css("display","block");
    					if($("#carrierFeeFlagId").val() == ""){
    						$("#carrierFeeFlagId").val(data.cFFId);
    					}
    					alert(data.info);
                    },error: function(XMLHttpRequest){  
                        alert( "Error: " + XMLHttpRequest.responseText);  
                    } 
                });
			};
			$(document).ready(function() {
				$("#reservation").daterangepicker(null, function(start, end, label) {
                	console.log(start.toISOString(), end.toISOString(), label);
               	});
 			});
			
		</script>
	</head>
	<body>
		<div class="page-header">
			<h1>仓储费+操作费+耗材费+打包费&nbsp;&nbsp;创建及配置</h1>
		</div>
		<div>
			<form class="registerform" id="sospform" role="form" action="${root}control/sospController/saveORupdate.do">
				<div style="width: 100%;" align="center">
					<table id="sosp_form" name="sosp_form" style="width: 80%;" class="form_table">
						<tr>
							<td class="right"><label class="blue"> 合同类型&nbsp;: </label></td>
							<td class="left">
								<input id="id" name="id" type="hidden" value="${cb.id}" />
								<c:if test="${empty cb.id}">
								<select id="status" name="status"  style="width: 100%;" onchange="selectStatus();">
									<option value="3">店铺</option>
									<option value="4">客户</option>
								</select>
								</c:if>
								<c:if test="${not empty cb.id}">
									<c:if test="${cb.contract_type==3}"><input type="hidden" id="status" name="status" value="${cb.contract_type}" /><input type="text" id="lxname" name="lxname" readonly="readonly" value="店铺"/></c:if>
									<c:if test="${cb.contract_type==4}"><input type="hidden" id="status" name="status" value="${cb.contract_type}" /><input type="text" id="lxname" name="lxname" readonly="readonly" value="客户"/></c:if>
								</c:if>
							</td>
							<td class="left"><div class="Validform_checktip"></div></td>
						</tr>
						
						<c:if test="${empty cb.id}">
							<tr id="dp" >
								<td class="right"><label class="blue"> 店铺&nbsp;: </label></td>
								<td class="left">
									<select id="store" name="store"  style="width: 100%;">
										<c:forEach items="${storeList}" var="storeList">
											<option value="${storeList.store_code}">${storeList.store_name}</option>
										</c:forEach>
									</select>
								</td>
								<td class="left"><div class="Validform_checktip"></div></td>
							</tr>
							<tr id="pp" style="display: none;">
								<td class="right"><label class="blue"> 客户&nbsp;: </label></td>
								<td class="left">
									<select id="client" name="client"  style="width: 100%;">
										<c:forEach items="${clientList}" var="clientList">
											<option value="${clientList.client_code}">${clientList.client_name}</option>
										</c:forEach>
									</select>
								</td>
								<td class="left"><div class="Validform_checktip"></div></td>
							</tr>
						</c:if>
						
						<c:if test="${not empty cb.id}">
							<c:if test="${cb.contract_type==3 }">
								<tr id="dp" >
									<td class="right"><label class="blue"> 店铺&nbsp;: </label></td>
									<td class="left">
										<input id="store" name="store" type="hidden" value="${cb.contract_owner}"/>
										<c:forEach items="${storeList}" var="storeList">
											<c:if test="${storeList.store_code==cb.contract_owner}">
												<input type="text" id="storename" name="storename" readonly="readonly" value="${storeList.store_name }"/>
											</c:if>
										</c:forEach>
									</td>
									<td class="left"><div class="Validform_checktip"></div></td>
								</tr>
							</c:if>
							<c:if test="${cb.contract_type==4 }">
								<tr id="pp">
									<td class="right"><label class="blue"> 客户&nbsp;: </label></td>
									<td class="left">
										<input id="client" name="client" type="hidden" value="${cb.contract_owner}"/>
										<c:forEach items="${clientList}" var="clientList">
											<c:if test="${clientList.client_code==cb.contract_owner}">
												<input type="text" id="clientname" name="clientname" readonly="readonly" value="${clientList.client_name }"/>
											</c:if>
										</c:forEach>
									</td>
									<td class="left"><div class="Validform_checktip"></div></td>
								</tr>
							</c:if>
						</c:if>
						<tr>
							<td  nowrap="nowrap"  width="30%"  class="right"><label class="blue"> 合同周期: </label></td>
							<td  nowrap="nowrap"  width="30%"  class="left">
								<c:if test="${not empty cb.id}"><input type="text" id="reservations" name="reservations" readonly="readonly" value="${cb.contract_start} - ${cb.contract_end}"/></c:if>
								<c:if test="${empty cb.id}">
				            	<div class="controls">
									<div class="input-prepend input-group">
										<span class="add-on input-group-addon">
											<i class="glyphicon glyphicon-calendar fa fa-calendar"></i></span>
											<input type="text" readonly  style="width: 100%" name="reservation" id="reservation" class="form-control" value="${sdate} - ${edate}" />
									</div>
								</div>
								</c:if>
							</td>
							<td></td>
						</tr>
						<tr>
							<td  nowrap="nowrap"  width="30%"  class="right"><label class="blue">结算日期: </label></td>
							<td  nowrap="nowrap"  width="30%"  class="left">
	                           	<select style="width: 100%;" id="settle_date" name="settle_date">
                             		<c:forEach var="i" begin="1" end="31" varStatus="status">
                             			<option ${cb.settle_date == status.index? "selected= 'selected'": "" } value= "${status.index }" >${status.index}日</option> 
                             		</c:forEach>
                             	</select>
							</td>
							<td>&nbsp;<span class="red" style="font-size: 10px;">*次月结算日，如果当月不满指定天数，则该月的最后一天为结算日期</span></td>
						</tr>						
						<tr>
							<td class="right"><label class="blue"> 经销单位: </label></td>
							<td class="left"><input type="text" id="distribution_unit" name="distribution_unit" value="${cb.distribution_unit}" datatype="*" nullmsg="请输入经销单位名称！" errormsg="必须填写经销单位名称!"/></td>
							<td class="left"><div class="Validform_checktip">必填</div></td></tr>
						<tr>
							<td class="right"><label class="blue"> 合同编号: </label></td>
							<td class="left"><input type="text" id="contract_no" name="contract_no" value="${cb.contract_no}" datatype="*" nullmsg="请输入合同编号！" errormsg="必须填写合同编号!"/></td>
							<td class="left"><div class="Validform_checktip">必填</div></td>
						</tr>
						<tr>
							<td class="right"><label class="blue"> 合同名称&nbsp;: </label></td>
							<td class="left"><input type="text" id="contract_name" name="contract_name" value="${cb.contract_name}" datatype="*" nullmsg="请输入合同名称！" errormsg="必须填写合同名称!"/></td>
							<td class="left"><div class="Validform_checktip">必填</div></td>
						</tr>
						<tr>
							<td class="right"><label class="blue"> 版本&nbsp;&nbsp;: </label></td>
							<td class="left"><input type="text" id="contract_version" name="contract_version" readonly="readonly" value="${contract_version}" datatype="*" nullmsg="请输入版本！" errormsg="必须填写版本!"/></td>
							<td class="left"><div class="Validform_checktip">必填</div></td>
						</tr>
						<tr>
							<td class="right"><label class="blue"> 联系人&nbsp;: </label></td>
							<td class="left"><input type="text" id="contact" name="contact" value="${cb.contact}" datatype="*" nullmsg="请输入联系人！" errormsg="必须填写联系人!"/></td>
							<td class="left"><div class="Validform_checktip">必填</div></td>
						</tr>
						<tr>
							<td class="right"><label class="blue"> 联系电话&nbsp;: </label></td>
							<td class="left"><input type="text" id="tel" name="tel" value="${cb.tel}" datatype="*" nullmsg="请输入联系电话！" errormsg="必须填写联系电话!"/></td>
							<td class="left"><div class="Validform_checktip">必填</div></td>
						</tr>
						<tr>
							<td class="right"><label class="blue"> 创建人&nbsp;: </label></td>
							<td class="left">
								<c:if test="${empty createEMP.id }">
									<input id="create_user" name="create_user" type="hidden" value="${employee.id}"/>
									<input type="text" id="name" name="name" readonly="readonly" value="${employee.name }"/>
								</c:if>
								<c:if test="${not empty createEMP.id }">
									<input id="create_user" name="create_user" type="hidden" value="${createEMP.id}"/>
									<input type="text" id="name" name="name" readonly="readonly" value="${createEMP.name }"/>
								</c:if>
								<input type="hidden" value="${cb.contract_type}" id="contractType"/>
							</td>
							<td class="left"></td>
						</tr>
						<c:if test="${not empty cb.id}">
							<tr>
								<td class="right" nowrap="nowrap" width="30%">
									<label class="no-padding-right blue" for="cb_contractValidity">
										合同有效性&nbsp;: 
									</label>
								</td>
							</tr>
						</c:if>
					</table>
					<div class="clearfix form-actions" style="height: 80px;">
						<button class="btn btn-info" type="button" onclick="tj();">
							<i class="icon-ok bigger-110"></i> 提交
						</button>
						&nbsp; &nbsp; &nbsp;
						<button class="btn" type="reset" onclick="openDiv('${root}control/contractController/list.do');">
							<i class="icon-undo bigger-110"></i>返回
						</button>
					</div>	
				</div>
			</form>
		</div>
		<!-- sheet页面 -->
		<div class="form-group" style="width: 100%;" >
			<div style="width: 100%;" >
				<div>
					<div class="tabbable" >
						<ul id="ccf_sheet" class="nav nav-tabs padding-8 tab-color-blue background-blue" id="myTab4" >
							<li id="carrierCharge" class="active"
								<c:if test="${pP.carrier_charge==0 }">style="display : none"</c:if>
							><a data-toggle="tab" href="#tabCarrierCharge">承运商费用</a></li>
							<li id="storage" 
								<c:if test="${pP.storage==0 }">style="display : none"</c:if>
								<c:if test="${pP.carrier_charge==0 }">class="active"</c:if>
							><a data-toggle="tab" href="#tabCCF">仓储费</a></li>
							<li id="operation" 
								<c:if test="${pP.operation==0 }">style="display : none"</c:if>
								<c:if test="${pP.carrier_charge==0 && pP.storage==0 }">class="active"</c:if>
							><a data-toggle="tab" href="#tabCZF">操作费</a></li>
							<li id="consumable" 
								<c:if test="${pP.consumable==0 }">style="display : none"</c:if>
								<c:if test="${pP.carrier_charge==0 && pP.storage==0 && pP.operation==0 }">class="active"</c:if>
							><a data-toggle="tab" href="#tabHCF">耗材费</a></li>
							<li id="packagePrice"
								<c:if test="${pP.carrier_charge==0 && pP.storage==0 && pP.operation==0 && pP.consumable==0 }">class="active"</c:if>
							><a data-toggle="tab" href="#tabDBF">打包费</a></li>
							<li id="zzfwf"><a data-toggle="tab" href="#tabZZFWF">增值服务费</a></li>
						</ul>
						<div id= "ccf_sheet_div" class= "tab-content" align= "center">
							<div 
								id= "tabCarrierCharge" 
								align= "center" 
								class= "tab-pane<c:if test= "${pP.carrier_charge== 1 || empty pP.id }"> in active</c:if>" >
								<!-- 运费 -->
								<%@ include file="/lmis/sosp/carrier_form.jsp" %>
							</div>
							<div 
								id= "tabCCF" 
								align= "center"
								class= "tab-pane<c:if test= "${pP.carrier_charge== 0 && pP.storage== 1 && not empty pP.id }"> in active</c:if>" >
								<!-- 仓储费 -->
								<%@ include file="/lmis/sosp/ccf_form.jsp"%>
							</div>
							<div 
								id= "tabCZF" 
								align= "center" 
								class= "tab-pane<c:if test= "${pP.carrier_charge== 0 && pP.storage== 0 && pP.operation== 1 && not empty pP.id }"> in active</c:if>" >
								<!-- 操作费 -->
								<%@ include file="/lmis/sosp/czf_form.jsp"%>
							</div>
							<div 
								id= "tabHCF" 
								align= "center" 
								class= "tab-pane<c:if test= "${pP.carrier_charge== 0 && pP.storage== 0 && pP.operation== 0 && pP.consumable== 1 && not empty pP.id }"> in active</c:if>" >
								<!-- 耗材费 -->
								<%@ include file="/lmis/sosp/hcf_form.jsp"%>
							</div>
							<div 
								id= "tabDBF" 
								align= "center" 
								class= "tab-pane<c:if test= "${pP.carrier_charge== 0 && pP.storage== 0 && pP.operation== 0 && pP.consumable== 0 && not empty pP.id }"> in active</c:if>" >
                                <!--打包费 -->
								<jsp:include page="/lmis/sosp/dbf_form.jsp" />
							</div>
							<div id="tabZZFWF" align="center" class="tab-pane" >
								<!-- 增值服务费 -->
								<jsp:include page="/lmis/sosp/zzfwf_form.jsp" />
							</div>
						</div>
					</div>
					<!-- /span -->
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">

		function initIframe(obj,iframeId) {
			//输入你希望根据页面高度自动调整高度的iframe的名称的列表
			//用逗号把每个iframe的ID分隔. 例如: ["myframe1", "myframe2"]，可以只有一个窗体，则不用逗号。
			//定义iframe的ID
			//如果用户的浏览器不支持iframe是否将iframe隐藏 yes 表示隐藏，no表示不隐藏
			var iframehide="no";
			var dyniframe = null;
			if (document.getElementById){
				//自动调整iframe高度
				dyniframe = document.getElementById("iframe_" + iframeId);
				if (dyniframe && !window.opera){
					dyniframe.style.display="block";
					if (dyniframe.contentDocument && dyniframe.contentDocument.body.offsetHeight) //如果用户的浏览器是NetScape
						dyniframe.height = dyniframe.contentDocument.body.offsetHeight;
					else if (dyniframe[i].Document && dyniframe.Document.body.scrollHeight) //如果用户的浏览器是IE
						dyniframe.height = dyniframe.Document.body.scrollHeight;
				}
			}
			//根据设定的参数来处理不支持iframe的浏览器的显示问题
			if ((document.all || document.getElementById) && iframehide=="no"){
				console.dir(document.getElementById(iframeId));
				var tempobj=document.all? document.all[iframeId] : document.getElementById(iframeId);
				tempobj.style.display="block";
			}
		}

		function setShowRow(oTable,iRow){
		    oTable.rows[iRow].style.display = oTable.rows[iRow].style.display = "block";
		}
		function setHiddenRow(oTable,iRow){
		    oTable.rows[iRow].style.display = oTable.rows[iRow].style.display = "none";
		}
		function selectStatus(){
			var status = $("#status").val();
			if(status==3){
				$('#pp').hide();
				$('#dp').show();
			}else {
				$('#dp').hide();
				$('#pp').show();
			}
		}

	</script>
</html>
