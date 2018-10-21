<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<%@ include file="yuriy.jsp" %>
	<!-- css -->
	<link rel="stylesheet" href="${root}css/jquery-ui.main.css">
	<link rel="stylesheet" href="${root}css/jquery.mobile-1.3.2.min.css">
	<!-- js -->
	<script src="${root }js/jquery-1.8.3.min.js"></script>
	<script src="${root }js/jquery.mobile-1.3.2.min.js"></script>
	<script src="${root }js/jquery.min.js"></script>
	<script src="${root }js/jquery-ui.min.js"></script>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>宝尊电商-登记</title>
		
		<style type="text/css">
			textarea.ui-input-text { min-height: 90px; };
			.opendiv {
				z-index: 920;
				position: absolute;
				left: 50%;
				top: 50%;
				margin-left: 75px;
				margin-top: -203px;
				width: 125px;
				height: 120px;
				border:1px;
				border-color:blue;
				text-align: center;
				padding-top: 20px;
				solid:#F00;
			}
			.main {
				z-index: 920;
				position: absolute;
				left: 50%;
				top: 50%;
				margin-left: -150px;
				margin-top: -250px;
				width: 200px;
				height: 200px;
				border:1px;
				text-align: center;
				padding-top: 20px;
				solid:#F00;
			}
			.main2 {
				z-index: 920;
				position: absolute;
				left: 50%;
				top: 50%;
				margin-left: 260px;
				margin-top: -250px;
				width: 100px;
				height: 140px;
				border:1px;
				text-align: center;
				solid:#F00;
			}
			</style>
			<script>
				<!--自动补全-->
				$(function() {
					<!--访客信息-->
			    	var fkid = ${fkid};
			    	$("#fkid").autocomplete({
			      		source: fkid
		    		});
		  		});
				function sign() {
					window.location.href="${root}control/mainController/sign.do";
				}
				function puch() {
					$.ajax({
						type: "POST",  
						url: root+"/control/mainController/puch.do?data="+$("#fkid").val(),  
						//我们用text格式接收  
						//dataType: "text",   
						//json格式接收数据  
						dataType: "json",  
						data: null,
						success: function (data) { 
							if(data.code!=200){
								alert("编号不存在或已签退!");
								window.location.href="${root}control/mainController/sign_out.do";
							}else{
								window.location.href="${root}control/mainController/sign_out.do?data="+$("#fkid").val();
							}
						}  
					}); 
				}
				function address() {
					window.location.href="${root}control/addressControl/addressList.do";
				}
				function sign_out() {
					window.location.href="${root}control/mainController/sign_out.do";
				}
				function detailed_list() {
					window.location.href="${root}control/mainController/detailed_list.do";
				}
				function main_list() {
					window.location.href="${root}control/mainController/main_list.do";
				}
				function out_login() {
					window.location.href="${root}control/userController/outLogin.do";
				}

				function update(){
			    	$.ajax({
						type: "POST",  
						url: root+"/control/mainController/updateSign.do?data="+$("#fkid").val(),  
						//我们用text格式接收  
						//dataType: "text",   
						//json格式接收数据  
						dataType: "json",  
						data: null,
						success: function (data) { 
							alert(data.msg);
							sign_out();
						}  
					}); 
				}
				$(document).ready(function(){
					if($("#fkid").val()==''){
						$('input[type=text]:first').focus();
					}else{
						document.getElementById("qcbt").focus();
					}
				});
			</script>
	</head>

	<body>
		<div data-role="page" id="pageone" style="vertical-align:middle;" align="center">
			<div data-role="header" data-theme="b">
				<div style="height: 60px;">
					<div style="display: inline;float: left;"><h3>&nbsp;&nbsp;[Visitor information management system]访客信息管理系统</h3></div>
					<div style="display: inline;float: right;margin-right: 10px;"><img alt="log" src="${root}img/baozun.png" style="height: 60px;width: 160px;"></div>
					<div style="display: inline;float: right;margin-right: 10px;margin-top: 25px;">欢迎,${session_user.name}｜<a href="javascript:void();" onclick="out_login();">[退出]</a></div>
				</div>
			</div>
			<div style="width: 1200px;height: 400px;margin-top: 20px;" id="bodys" name="bodys">
				<form id="sign_main" name="sign_main">
					<!-- 拍照次数 -->
					<input id="pzid" name="pzid" type="hidden" value="0" />
					<table width="100%">
						<tr height="35px;">
							<td width="15%">访客编号:<br><span style="font: 黑体;font-size: 10px;color: #B8B8B8;">(Visitor Name)</span></td>
							<td width="40%">
								<div style="width: 300px;">
									<div style="width: 150px;float: left;">
										<input id="fkid" name="fkid" type="text" tabindex="0" value="${obj.data}" />
									</div>
									<div style="width: 130px;float: right;">
										<input type="button" onclick="puch();" value="查询" />
									</div>
								</div>
							</td>
							<td rowspan="8" width="45%">
								<div class="main2" style="width: 210px;height: 260px;background-color: #B8B8B8;">
									<div style="margin-top: 10px;"><img alt="" height="240px;" src="data:image/png;base64,${img}"></div>
								</div>
								<div style="margin-top: 400px;width: 200px;margin-left: 200px;">
									<c:if test="${msg=='[已签到]'}">
									</c:if>
									<input type="button" id="qcbt" name="qcbt" onclick="update();" selected value="签出" />
								</div>
							</td>
						</tr>
						<tr height="35px;">
							<td width="15%">访客姓名:<br><span style="font: 黑体;font-size: 10px;color: #B8B8B8;">(Visitor Name)</span></td>
							<td width="40%">
								<div style="width: 300px;">
									<input id="visitor_name" name="visitor_name" type="text" value="${obj.visitor_name}" readonly disabled="disabled"/>
								</div>
							</td>
						</tr>
						<tr height="35px;">
							<td width="15%">联系电话:<br><span style="font: 黑体;font-size: 10px;color: #B8B8B8;">(Phone Number)</span></td>
							<td width="40%">
								<div style="width: 300px;">
									<input id="visitor_phone" name="visitor_phone" type="text" value="${obj.visitor_phone}" readonly disabled="disabled"/>
								</div>
							</td>
						</tr>
						<tr height="35px;">
							<td width="15%">拜访对象:<br><span style="font: 黑体;font-size: 10px;color: #B8B8B8;">(Host)</span></td>
							<td width="40%">
								<div style="width: 300px;z-index: 999">
							  		<input id="host" name="host"   value="${obj.host}"  readonly disabled="disabled"/>
								</div>
							</td>
						</tr>
						<tr height="35px;">
							<td width="15%">工作单位:<br><span style="font: 黑体;font-size: 10px;color: #B8B8B8;">(Visitor Company Name)</span></td>
							<td width="40%">
								<div style="width: 300px;">
									<input id="visitor_company_name" name="visitor_company_name" value="${obj.visitor_company_name}"  type="text" readonly disabled="disabled"/>
								</div>
							</td>
						</tr>
						<tr height="35px;">
							<td width="15%">车牌号:<br><span style="font: 黑体;font-size: 10px;color: #B8B8B8;">(License Plate Number)</td>
							<td width="40%">
								<div style="width: 300px;">
									<input id="license_plate_number" name="license_plate_number" value="${obj.license_plate_number}"  type="text" readonly disabled="disabled"/>
								</div>
							</td>
						</tr>
						<tr height="35px;">
							<td width="15%">事由:<br><span style="font: 黑体;font-size: 10px;color: #B8B8B8;">(Content)</td>
							<td width="40%">
								<div style="width: 300px;">
									<input id="content" name="content" type="text" value="${obj.content}"  readonly disabled="disabled"/>
								</div>
							</td>
						</tr>
						<tr height="100px;">
							<td width="15%">备注<br><span style="font: 黑体;font-size: 10px;color: #B8B8B8;">(Remark)</td>
							<td width="40%">
								<div style="width: 300px;">
									<textarea id="remark" name="remark" rows="6" cols="" style="resize:none" readonly disabled="disabled">${obj.remark}</textarea>
								</div>
							</td>
						</tr>
						<tr height="35px;" align="center">
							<td colspan="2" align="right">
							</td>
							<td colspan="1" align="left">
								<div style="width: 200px;">
								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div data-role="footer"  data-theme="b" data-position="fixed">
			    <div data-role="navbar" data-iconpos="left">
			      <ul>
			        <li><a href="javascript.void();" onclick="main_list();" data-icon="home">主&nbsp;&nbsp;&nbsp;&nbsp;页</a></li>
			        <li><a href="javascript.void();" onclick="address()" data-icon="check">地&nbsp;&nbsp;&nbsp;&nbsp;址</a></li>
			        <li><a href="javascript.void();" onclick="sign()" data-icon="check">签&nbsp;&nbsp;&nbsp;&nbsp;入</a></li>
			        <li><a href="javascript.void();" onclick="detailed_list()" data-icon="info">查&nbsp;看&nbsp;记&nbsp;录</a></li>
			      </ul>
			    </div>
			</div>
		</div>
	</body>
</html>