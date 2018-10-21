<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ include file="/lmis/yuriy.jsp"%>
<title>LMIS</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link href="<%=basePath %>daterangepicker/font-awesome.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" media="all"
	href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
<script type="text/javascript"
	src="<%=basePath %>daterangepicker/jquery-1.8.3.min.js"></script>
<script type="text/javascript"
	src="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=basePath %>daterangepicker/moment.js"></script>
<script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
<script type="text/javascript">
if("ontouchend" in document) document.write("<script src='<%=basePath %>assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");</script>
<script type="text/javascript">
window.jQuery || document.write("<script src='<%=basePath %>assets/js/jquery-2.0.3.min.js'>"+ "<"+"/script>");
</script>
<link rel="stylesheet" href="<%=basePath %>validator/css/style.css" type="text/css" media="all" />
<link href="<%=basePath %>validator/css/demo.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>/css/table.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=basePath %>validator/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="<%=basePath %>lmis/trans_moudle/js/trans.js"></script>
<script type="text/javascript">
$(function(){
  $(".registerform").Validform({
	tiptype:2
  });
})
</script>

<script type="text/javascript">
			window.jQuery || document.write("<script src='<%=basePath %>assets/js/jquery-2.0.3.min.js'>" + "<"+"/script>");
			$(document).ready(function() {
				$("#reservation").daterangepicker(null, function(start, end, label) {
                	console.log(start.toISOString(), end.toISOString(), label);
               	});
 			});
			$(document).ready(function() {
				$("#reservation2").daterangepicker(null, function(start, end, label) {
                	console.log(start.toISOString(), end.toISOString(), label);
               	});
 			});
		</script>
</head>

<body>
	<div class="page-header" align="left">
		<h1>快递物流创建和编辑</h1>
	</div>

	<div class="col-xs-12">
		<div class="clearfix form-actions" style="margin-bottom:10px;">
			<c:if test="${empty role.id}">
				<form class="registerform" id="menuform" role="form">
			</c:if>

			<c:if test="${not empty role.id}">
				<form class="registerform" id="menuform" role="form">
			</c:if>
             <input type="hidden" value="1" id="contractId"/>
			<div style="width: 100%;" align="center">
				<table style="width: 80%;" class="form_table">
						<tr>
							<td  nowrap="nowrap"  width="30%"  class="right"><label class="blue"> 合同周期: </label></td>
							<td  nowrap="nowrap"  width="30%"  class="left">
				            	<div class="controls">
									<div class="input-prepend input-group">
										<span class="add-on input-group-addon">
											<i class="glyphicon glyphicon-calendar fa fa-calendar"></i></span>
											<input type="text" readonly  style="width: 100%" name="reservation" id="reservation" class="form-control" value="2016-6-7 - 2016-6-7" />
									</div>
								</div>
							</td>
							<td></td>
						</tr>
						<tr>
							<td  nowrap="nowrap"  width="30%"  class="right"><label class="blue">结算周期: </label></td>
							<td  nowrap="nowrap"  width="30%"  class="left">
                                   <select style="width: 100%;" name="settleDate">
                                        <%for(int i=1;i<=31;i++){ %>
                                         <option><%=i%></option> 
                                         <%}%>                                                                                             
                                  </select>
							</td>
							<td nowrap="nowrap"  width="32%">&nbsp;<span class="red" style="font-size: 10px;">*次月结算日，如果当月不满指定天数，则该月的最后一天为结算日期</span></td>
						</tr>	
					<tr>
						<td class="right" ><label class="no-padding-right blue" for="form-field-1"> 合同编号&nbsp;: </label></td>
						<td class="left"><input id="orderNum" name="orderNum" type="text" id="form-field-1" placeholder="如：ZXV001" value="" datatype="s6-18"  errormsg="信息至少6个字符,最多18个字符！" /></td>
						<td class="left"><div class="Validform_checktip">必填</div></td>
					</tr>
					<tr>
						<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1"> 合同名称&nbsp;: </label></td>
						<td nowrap="nowrap" class="left"><input id="orderName" name="orderName" type="text" id="form-field-1" placeholder="如：合同名称" value="" datatype="s6-18"  errormsg="信息至少6个字符,最多18个字符！" /></td>
						<td class="left"><div class="Validform_checktip">必填</div></td>
					</tr>
                    			
					<tr>
						<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1"> 版本&nbsp;: </label></td>
						<td nowrap="nowrap" class="left"><input id="orderVersion" name="orderVersion" type="text" id="form-field-1" placeholder="如：版本" value="" datatype="s6-18"  errormsg="信息至少6个字符,最多18个字符！"  /></td>
						<td class="left"><div class="Validform_checktip">必填</div></td>
					</tr>
					<tr>
						<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1"> 联系人&nbsp;: </label></td>
						<td nowrap="nowrap" class="left"><input id="contractPerson" name="contractPerson" type="text" id="form-field-1" placeholder="如：联系人" value="" datatype="s6-18"  errormsg="信息至少6个字符,最多18个字符！"  /></td>
						<td class="left"><div class="Validform_checktip">必填</div></td>
					</tr>
					<tr>
						<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1"> 联系电话&nbsp;: </label></td>
						<td nowrap="nowrap" class="left"><input id="contractPhone" name="contractPhone" type="text" id="form-field-1" placeholder="如：联系电话" value="" datatype="m"  errormsg="手机号格式不正确"  /></td>
						<td class="left"><div class="Validform_checktip">必填</div></td>
					</tr>
<!-- 					<tr> -->
<!-- 						<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1"> 创建人&nbsp;: </label></td> -->
<!-- 						<td nowrap="nowrap" class="left"><input id="name" name="name" type="text" id="form-field-1" placeholder="如：创建人" value="" datatype="s6-18"  errormsg="信息至少6个字符,最多18个字符！"  /></td> -->
<!-- 						<td nowrap="nowrap" class="left"></td> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<!-- 						<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1"> 创建时间 &nbsp;: </label></td> -->
<!-- 						<td nowrap="nowrap" class="left"><input id="name" name="name" type="text" id="form-field-1" placeholder="如：创建时间 " value="" datatype="s6-18"  errormsg="信息至少6个字符,最多18个字符！"  /></td> -->
<!-- 						<td nowrap="nowrap" class="left"></td> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<!-- 						<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1"> 更新时间&nbsp;: </label></td> -->
<!-- 						<td nowrap="nowrap" class="left"><input id="name" name="name" type="text" id="form-field-1" placeholder="如：更新时间" value="" datatype="s6-18"  errormsg="信息至少6个字符,最多18个字符！"  /></td> -->
<!-- 						<td nowrap="nowrap" class="left"></td> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<!-- 						<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1"> 更新人&nbsp;: </label></td> -->
<!-- 						<td nowrap="nowrap" class="left"><input id="name" name="name" type="text" id="form-field-1" placeholder="如：更新人" value="" datatype="s6-18"  errormsg="信息至少6个字符,最多18个字符！"  /></td> -->
<!-- 						<td nowrap="nowrap" class="left"></td> -->
<!-- 					</tr> -->
					<tr>
						<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1"> 所属物流 &nbsp;: </label></td>
						<td nowrap="nowrap" class="left"><input id="belongTo" name="belongTo" type="text" id="form-field-1" placeholder="如：所属物流 " value="" datatype="s6-18"  errormsg="信息至少6个字符,最多18个字符！"  /></td>
						<td class="left"><div class="Validform_checktip">必填</div></td>
					</tr>				
				</table>
				
							<div class="clearfix form-actions">
								<c:if test="${empty power.id}">
									<button class="btn btn-info" type="button" id="subins" name="subins">
										<i class="icon-ok bigger-110"></i> 提交
									</button>
								</c:if>
								<c:if test="${not empty power.id}">
									<button class="btn btn-info" type="button" id="subup" name="subup">
										<i class="icon-ok bigger-110"></i> 提交
									</button>
								</c:if>
								&nbsp; &nbsp; &nbsp;
								<button class="btn" type="reset" onclick="">
									<i class="icon-undo bigger-110"></i> 返回
								</button>
							</div>				
			   </div>
             </form>
		</div>



		<div class="form-group" style="width: 100%;margin-bottom: 100px;">
			<div style="width: 100%;">
				<div>
					<div class="tabbable">
						<ul class="nav nav-tabs padding-8 tab-color-blue background-blue" id="myTab4">
							<li class="active"><a data-toggle="tab" href="#yf_tab">运费</a></li>
							<li><a data-toggle="tab" href="#bjf_tab">保价费</a></li>
							<li><a data-toggle="tab" href="#tsfwf_tab">特殊费服务</a></li>
							<li><a data-toggle="tab" href="#glf_tab">管理费</a></li>
							<li><a data-toggle="tab" href="#wlkdgf_tab">物流使用快递管理</a></li>
							<li style="display:"><a data-toggle="tab" href="#address_tab">始发地目的地参数列表</a></li>
						</ul>
                       
						<div class="tab-content"  align="center">
						
						      <!-- 运费 -->
							  <div id="yf_tab" class="tab-pane in active">
								 <%@ include file="/lmis/trans_moudle/yf_moudle.jsp"%>
	                          </div>
	                          
	                          <!-- 保价费 -->
	                          <div id="bjf_tab" class="tab-pane" align="center">
	                             <%@ include file="/lmis/trans_moudle/bjf_moudle.jsp"%>
	                          </div>
	                          
	                          <!-- 特殊服务费 -->
							  <div id="tsfwf_tab" class="tab-pane" align="center">
								  <%@ include file="/lmis/trans_moudle/tsfwf_moudle.jsp"%>
							  </div>
							  
							  <!-- 管理费 -->
	                          <div id="glf_tab" class="tab-pane" align="center">
	                             <%@ include file="/lmis/trans_moudle/glf_moudle.jsp"%>
	                          </div>
	                          
	                          <!-- 起始地目的地参数 -->
							  <div id="address_tab" class="tab-pane">
	                              <%@ include file="/lmis/trans_moudle/address_moudle.jsp"%>
							  </div>
							  
							  <!-- 物流是使用快递管理 -->
	                          <div id="wlkdgf_tab" class="tab-pane" align="center">
	                            <%@ include file="/lmis/trans_moudle/wlkdgl_moudle.jsp"%>
	                          </div>
							
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="space-4"></div>
	</div>
</body>
<script src="/assets/js/bootstrap.min.js"></script>
<script src="/assets/js/typeahead-bs2.min.js"></script>

<!-- page specific plugin scripts -->

<script src="/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script src="/assets/js/jquery.ui.touch-punch.min.js"></script>

<!-- ace scripts -->

<script src="/assets/js/ace-elements.min.js"></script>
<script src="/assets/js/ace.min.js"></script>
<script type="text/javascript">
function save_mian_data(){
	var formInfo=$("#menuform").serialize();
	$.ajax({
		type : "POST",
		url: root+"/control/transOrderController/saveMain.do",  
		//我们用text格式接收  
		//dataType : "text",
		data:formInfo,
		//json格式接收数据  
		dataType: "json",  
		success : function(jsonStr) {
			if(jsonStr.result_flag==1){
				alert(jsonStr.result_reason);
// 				$("#subins").attr("disabled",true);
				$("#contractId").val(jsonStr.contractId);
			}
		}
	});
}


</script>
</html>
