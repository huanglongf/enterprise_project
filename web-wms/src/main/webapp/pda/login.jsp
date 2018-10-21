<%@include file="/pda/commons/common.jsp"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html class="js cssanimations">
<head>

<title>WMS3.0 PDA</title>
<%@include file="/pda/commons/common-meta.jsp"%>
<%@include file="/pda/commons/common-css.jsp"%>
<!-- 
localhost:8080/wms/pda/pdalogin.do
 -->
</head>
<body contextpath="<%=request.getContextPath() %>">
	<div class="body-all">
		<!-- nav start -->
		<header class="am-topbar admin-header">
			<div class="am-topbar-brand">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">WMS</strong>&nbsp;&nbsp;<strong class="am-text-primary am-text-lg">首页</strong> / <small>登录</small>
				</div>
			</div>
			<div class="user-info ">
				<div class="user-info-ele"></div>
			</div>
		</header>
				<div id="msgStr">${FieldErrors.msg[0]}</div>
				<form class="am-form" id="form" method="post" action="<s:url value='/pda/userlogin.do' includeParams='none' encode='false'/>">
					<div id="loginDiv">
						<label>用户名：</label> <br /> 
						<input type="text" name="loginname" id="loginname" required /><br /> 
						<label>密码：</label><br /> 
						<input type="password" name="password" id="password" required /><br /> 
						<label>仓库组织：</label><br /> 
						<input type="text" name="oucode" id="oucode" required /><br />
						<%@include file="/pda/commons/common-msg.jsp"%>
						<button type="button" id="confirmBtn">登录</button>
						<button type="reset">重置</button>			
					</div>
					<div id="singleDiv"  style="display: none;">
						<br/>
						<br/>
						<br/>
						<label style="color: red;">此账号已在不同设备上登入，是否强制登入？</label> <br /> <br /> 
						<button type="button"  id="singleLogin">登入</button>
						<button type="button" id="cancel">取消</button>			
					</div>
				</form>
			<%@include file="/pda/commons/common-footer.jsp"%>
		<%@include file="/pda/commons/common-modal.jsp"%>
		<%@include file="/pda/commons/common-play.jsp"%>
		<%@include file="/pda/commons/common-script.jsp"%>
		<script type="text/javascript" src="./shelves/pda_zoomout.js"></script>
		<script type="text/javascript">
			$("#loginname").focus();
			$("#cancel").click(function() {
					document.getElementById("singleDiv").style.display="none";//隐藏
					document.getElementById("loginDiv").style.display="";//显示
			});
			//强制登入
			$("#singleLogin").click(function() {
					document.getElementById("form").submit();
			});
			$("#confirmBtn").click(function() {
			   var loginname = $("#loginname").val();
				//验证账户是否已经在别的客户端登入
				//alert($("body").attr("contextpath") + "/pda/checkSingleogin.do");
				var flag=request($("body").attr("contextpath") + "/pda/checkSingleogin.do",{"loginname":loginname});
				if(flag["check"]=="0"){
					document.getElementById("form").submit();
				}
				if(flag["check"]=="1"){
					//在不同设备已经登入 提示是否强制登入
					document.getElementById("loginDiv").style.display="none";//隐藏
					document.getElementById("singleDiv").style.display="";//显示
				}
			})
			$("#loginname").keypress(function(evt) {
				if (evt.keyCode === 13) {
					var key = $("#loginname").val();
					if (key != "") {
						$("#password").focus();
					} else {
						//alert("请输入用户名！");
						$("#msgStr").text("请输入用户名！");
					}
				}
			});
			$("#password").keypress(function(evt) {
				if (evt.keyCode === 13) {
					var key = $("#password").val();
					if (key != "") {
						$("#oucode").focus();
					} else {
						//alert("请输入密码！");
						$("#msgStr").text("请输入密码！");
					}
				}
			});
			$("#oucode").keypress(function(evt) {
				if (evt.keyCode === 13) {
					var key = $("#oucode").val();
					if (key != "") {
						$("#confirmBtn").trigger("click");
					} else {
						//alert("请输入仓库编码！");
						$("#msgStr").text("请输入仓库编码！");
					}
				}
			});
		function request(url, data, args){
            url=url+'?version='+new Date();
			var _data, options = this._ajaxOptions(url, data, args);
			$.extend(options,{
				async : false,
				success : function(data, textStatus){
					_data = data;
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
					_data = {};
					var exception = {};
					exception["message"] = "Error occurs when fetching data from url:" + this.url;
					exception["cause"] = textStatus? textStatus : errorThrown;
					_data["exception"] = exception;
				}
			});
			$.ajax(options);
			return _data;
		}
		</script>
</body>
</html>