<%@include file="/pda/commons/common.jsp"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html class="js cssanimations">
<head>
<title>WMS3.0 PDA</title>
<%@include file="/pda/commons/common-meta.jsp"%>
<%@include file="/pda/commons/common-css.jsp"%>
</head>
<body contextpath="<%=request.getContextPath() %>">
	<div class="body-all">
		<%@include file="/pda/commons/common-header.jsp"%>
		<div class="am-cf admin-main">
			<div class="admin-content">
			<div><font id="msg" color="red"></font></div>
				<div class="am-tabs " data-am-tabs="" id="receive1">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
						    <div  style="font-size:15px;text-align: left;">扫描作业指令/相关单据号(货箱号)</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								   <input type="text" class="input-element" id="staCode" />
								</div>
							</div>
						</div>
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
						    <div  style="font-size:15px;text-align: left;">扫标签</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								   <input type="text" class="input-element" id="tag" />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="submit" class="am-btn am-btn-primary btn-right-margin" id="goodOk1">确认</button>
							<button type="button" class="am-btn am-btn-primary " id="backOk1">返回</button>
						</div>
					</div>
				</div>
				
			 </div>	
			<%@include file="/pda/commons/common-footer.jsp"%>
		</div>
		<%@include file="/pda/commons/common-modal.jsp"%>
		<%@include file="/pda/commons/common-play.jsp"%>
		<%@include file="/pda/commons/common-script.jsp"%>
		<script type="text/javascript">
		$(document).ready(function(){
			$("#staCode").focus();
			$("#staCode").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#staCode").val();
					if(key!=""){
						$("#tag").focus();
						//$("#goodOk1").trigger("click");	
					}else {
						play();
						$("#msg").text("请输入业指令/相关单据号");
					}
				}
			});
			
			$("#tag").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#tag").val();
					if(key!=""){
						var staCode = $("#staCode").val();
						if(staCode==""){
							$("#msg").text("请输入业指令/相关单据号");
						}
						$("#goodOk1").trigger("click");	
					}else {
						play();
						$("#msg").text("请输入标签");
					}
				}
			});
			

			$("#goodOk1").click(function(){//扫描容器号
				$("#msg").text("");
				var staCode = $("#staCode").val();
				var	tag = $("#tag").val();
				if(staCode==""){
					play();
					$("#msg").text("请输入业指令/相关单据号");
					return false;
				}
				if(tag==""){
					play();
					$("#msg").text("请输入标签");
					return false;
				}
				var postData = {
						"staCode":staCode,
						"tag": tag
				};
				var flag=request($("body").attr("contextpath") + "/pda/checkTag.do",postData);//初始化
				if(flag["flag"]=="success"){
					$("#msg").text("执行成功");
					$("#staCode").val("");
					$("#tag").val("");
					$("#staCode").focus();
					}else if(flag["flag"]=="error"){
						play();
						$("#msg").text(flag["msg"]);
						$("#staCode").val("");
						$("#tag").val("");
						$("#staCode").focus();
					}else{
						play();
						$("#msg").text("系统异常!");
					} 
			});
			
			//返回1
			$("#backOk1").click(function(){
				window.location.href=$("body").attr("contextpath")+"/pda/menu.do";
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
				//console.dir(_data);
				return _data;
			};
		});
			</script>
	
	
</body>
</html>