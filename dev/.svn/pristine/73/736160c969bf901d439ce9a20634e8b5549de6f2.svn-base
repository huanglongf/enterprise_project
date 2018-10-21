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

			<div style="width: 100%;" align="center">
				<table style="width: 80%;" class="form_table">
						<tr>
							<td  nowrap="nowrap"  width="30%"  class="right"><label class="blue"> 创建时间: </label></td>
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
							<td  nowrap="nowrap"  width="30%"  class="right"><label class="blue">创建人: </label></td>
						<td class="left"><input id="orderNum"  readonly name="orderNum" type="text" id="form-field-1" placeholder="如：ZXV001" value="" /></td>
						<td class="left"><div class="Validform_checktip"></div></td>
						</tr>	
					<tr>
						<td class="right" ><label class="no-padding-right blue" for="form-field-1">更新时间&nbsp;: </label></td>
						<td class="left"><input id="orderNum"  readonly name="orderNum" type="text" id="form-field-1" placeholder="如：ZXV001" value="" /></td>
						<td class="left"><div class="Validform_checktip"></div></td>
					</tr>
					<tr>
						<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1"> 更新人&nbsp;: </label></td>
						<td nowrap="nowrap" class="left"><input readonly id="orderName" name="orderName" type="text" id="form-field-1" placeholder="" value="" /></td>
						<td class="left"><div class="Validform_checktip"></div></td>
					</tr>
                    			
					<tr>
						<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1"> 仓库代码&nbsp;: </label></td>
						<td nowrap="nowrap" class="left"><input readonly id="orderVersion" name="orderVersion" type="text" id="form-field-1" placeholder="" value=""/></td>
						<td class="left"><div class="Validform_checktip"></div></td>
					</tr>
					<tr>
						<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1">仓库名称&nbsp;: </label></td>
						<td nowrap="nowrap" class="left"><input readonly id="contractPerson" name="contractPerson" type="text" id="form-field-1"  /></td>
						<td class="left"><div class="Validform_checktip"></div></td>
					</tr>
					<tr>
						<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1"> 联系人&nbsp;: </label></td>
						<td nowrap="nowrap" class="left"><input readonly id="contractPhone" name="contractPhone" type="text" id="form-field-1" /></td>
						<td class="left"><div class="Validform_checktip"></div></td>
					</tr>
				</table>
				
			   </div>
		</div>


		<div style="margin-top: 10px;margin-left: 10px;margin-bottom: 10px;">
			<button class="btn btn-xs btn-pink" onclick="find();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
			&nbsp;&nbsp;
			<button class="btn btn-xs btn-primary" onclick="up();">
				<i class="icon-edit"></i>修改
			</button>
			&nbsp;
			<button class="btn btn-xs btn-inverse" onclick="del('${employeeid}','${ret}');">
				<i class="icon-trash"></i>删除
			</button
			>&nbsp;
		</div>
		<div style="height:400px;overflow:auto;overflow:scroll; border:solid #F2F2F2 1px;margin-top: 22px;">
			<table class="table table-striped">
		   		<thead  align="center">
			  		<tr>
			  		<td><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/> </td>
					<td>区域名称</td>
					<td>区域代码</td>
					<td>创建人</td>
					<td>更新时间</td>
					<td>更新人</td>
					<td>有效性</td>	
			  		<tr>
			  		     <td></td>
			  		     <% for(int i=1;i<=6;i++){ %>
<%-- 			  	<c:forEach items="${power}" var="power"> --%>
                        <td nowrap="nowrap">
						  <span class="input-icon input-icon-right">
						    <select style="text-align:center;height: 27px;">
						    <option value="0">≈</option>
						    <option value="1">=</option>
						    <option value="2">></option>
						    <option value="3">>=</option>
						    <option value="4"><</option>
						    <option value="5"><=</option>
						    </select>
							<input type="text" id="form-field-icon-2" style="width:80px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						 </span>
						</td>
						 <%} %>
<%-- 		  		</c:forEach> --%>
			  		</tr>  				  			
		  		</thead>
		  		
		  		<tbody  align="center">
		  		<c:forEach items="${power}" var="power">
			  		<tr>
			  			<td><input id="ckb" name="ckb" type="checkbox" value="${power.id}"></td>
			  			<td></td>
			  			<td></td>
			  			<td></td>
			  			<td></td>
			  			<td></td>
                        <td></td>
			  		</tr>
		  		</c:forEach>
		 
		  		</tbody>
			</table>
		</div>
		<div class="space-4"></div>
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
// 	var mian_time=$("#reservation").val();
// 	var orderNum=$("#orderNum").val();
// 	var orderName=$("#orderName").val();
// 	var orderVersion=$("#orderVersion").val();
// 	var contractPerson=$("#contractPerson").val();
// 	var contractPhone=$("#contractPhone").val();
// 	var belongTo=$("#belongTo").val();
// 	var mianInfo=mian_time+"&"+orderNum+"&";
	var formInfo=$("#menuform").serialize();
	alert(formInfo);
}



	function changeCat() {
		var divId = $("#choseCat").val();
		var divObj = $("div[id^='cat']");
		for (i = 0; i < divObj.length; i++) {
			if (i == divId) {
				$("#cat" + i).css("display", "block");
			} else {
				$("#cat" + i).css("display", "none");
			}
		}
	}

	function showDeatil() {
		var divId = $("#catDiv2").val();
		var divObj2 = $("div[id^='opDiv']");
		for (i = 1; i <= divObj2.length; i++) {
			if (i == divId) {
				$("#opDiv" + i).css("display", "block");
			} else {
				$("#opDiv" + i).css("display", "none");
			}
		}
	}

	function changeShow(div) {
		var ifshow = $("#" + div).css("display");
		if (ifshow == "none") {
			$("#" + div).css("display", "block");
		} else {
			$("#" + div).css("display", "none");
		}
	}

	function isChecked(id) {
		if ($("[id=" + id + "]:checkbox").prop('checked')) {
			return true;
		} else {
			return false;
		}
	}

	$("#subins").click(function() {
		//alert(isChecked("fredd"));
          save_mian_data();
          return;
		$.ajax({
			type : "POST",
			//url: root+"/control/powerController/add.do",  
			//我们用text格式接收  
			dataType : "text",
			//json格式接收数据  
			//dataType: "json",  
			data : "name=" + $('#name').val() + "&menuid=" + $("#ids").val(),
			success : function(jsonStr) {
				alert(jsonStr);
			}
		});
	});
	
	$("#subup").click(
			function() {
				$.ajax({
					type : "POST",
					// url: root+"/control/powerController/update.do",  
					//我们用text格式接收  
					dataType : "text",
					data : "id=" + $('#id').val() + "&name=" + $('#name').val()+ "&remark=" + $('#remark').val(),
					success : function(jsonStr) {
						alert(jsonStr);
					}
				});
			});
</script>
</html>
