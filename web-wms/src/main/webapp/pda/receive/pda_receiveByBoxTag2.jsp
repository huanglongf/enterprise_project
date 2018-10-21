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
							<div  style="font-size:15px;text-align: left;">按箱收货(扫标签)</div>
						    <div  style="font-size:15px;text-align: left;">扫描作业指令/相关单据号(货箱号)</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								   <input type="text" class="input-element" id="staCode" />
								</div>
							</div>
						</div>
					<!-- 	<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
						    <div  style="font-size:15px;text-align: left;">扫标签</div>
							</div>
						<div class="am-g">
								<div class="input-element-frame">
								   <input type="text" class="input-element" id="tag" />
								</div>
						</div>
						</div> -->
						<div class="btn-center">
							<button type="submit" class="am-btn am-btn-primary btn-right-margin" id="Ok">确认</button>
							<button type="button" class="am-btn am-btn-primary " id="Back">返回</button>
						</div>
					</div>
				</div>
				
				<div class="am-tabs " data-am-tabs="" id="receive2" >
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
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
							<button type="submit" class="am-btn am-btn-primary btn-right-margin" id="receiveOk">确认</button>
							<button type="button" class="am-btn am-btn-primary " id="receiveBack">返回</button>
						</div>
					</div>
				</div>
				
			 </div>	
			<%@include file="/pda/commons/common-footer.jsp"%>
		</div>
		<%@include file="/pda/commons/common-modal.jsp"%>
		<%@include file="/pda/commons/common-play.jsp"%>
		<%@include file="/pda/commons/common-script.jsp"%>
		<script type="text/javascript" src="./shelves/pda_zoomout.js"></script>
		<script type="text/javascript">
		var staCode="";		
		$(document).ready(function(){
					toBox(1);
					//返回1
					$("#Back").click(function(){
						window.location.href=$("body").attr("contextpath")+"/pda/menu.do";
					});
					//返回2
					$("#receiveBack").click(function(){
						$("#msg").text("");
						$("#tag").val("");
						$("#staCode").val("");
						$("#staCode").focus();
						toBox(1);
					});
					
					
					$("#staCode").focus();
					
					//作业单条码扫描
					$("#staCode").keypress(function(evt){
						if (evt.keyCode === 13) {
							var key = $("#staCode").val();
							if(key!=""){
								$("#Ok").trigger("click");
							}else {
								play();
								$("#msg").text("请输入作业指令!");
								return;
							}
						}
					});
					
					$("#Ok").click(function(){
						staCode = $("#staCode").val();
						if(staCode==""){
							play();
							$("#msg").text("请输入作业指令!");
							$("#staCode").focus();
							return;
						}
						var postData={"staCode":staCode};
						var flag=request($("body").attr("contextpath") + "/pda/pdaReceiveByBoxCheckSkus.do",postData);
						if(flag["flag"]=="2"){
							toBox(2);
							$("#tag").focus();
						}else if (flag["flag"]=="3"){
							play();
							$("#msg").text("该箱中有缺失三维信息商品！");
							$("#staCode").val("");
							$("#staCode").focus();
						}else if (flag["msg2"]!=""){
							play();
							$("#msg").text(flag["msg2"]);
							$("#staCode").val("");
							$("#staCode").focus();
						}
						else if (flag["flag"]=="1"){
							play();
							$("#msg").text("按箱收货的商品必须是非效期商品、非SN商品");
							$("#staCode").val("");
							$("#staCode").focus();
						}
						else{
							play();
							$("#msg").text("系统异常");
						}
					});

					
					//扫描标签
					$("#tag").keypress(function(evt){
						if (evt.keyCode === 13) {
							var key = $("#staCode").val();
							if(key!=""){
							$("#receiveOk").trigger("click");
							}else {
								play();
								$("#msg").text("请输入标签!");
								return;
							}
						}
					});
					
					//退出登录
					$("#exit").click(function(){
						window.location.href=$("body").attr("contextpath")+"/pda/pdaExit.do";
					});
					$("#receiveOk").click(function(){
						staCode = $("#staCode").val();
						var  tag= $("#tag").val();
						if(staCode==""){
							play();
							$("#msg").text("请输入作业指令条码!");
							return false;
						}
						var postData = {
								"staCode":staCode,
								"tag":tag
						};
						var flag=request($("body").attr("contextpath") + "/pda/pdaReceiveByBox.do",postData);
						if(flag["flag"]=="success"){
							play();
							$("#msg").text("收货成功!");
			                $("#staCode").val("");
			                $("#tag").val("");
			                toBox(1);
			                $("#staCode").focus();
			                return;
						}else if(flag["flag"]=="error"){
							play();
							 $("#msg").text("收货成功,没有推荐到库位和弹出口");
							 $("#staCode").val("");
				             $("#tag").val("");
				             toBox(1);
							return;
						}
						else if(flag["msg2"] !=""){
							play();
							$("#msg").text(flag["msg2"]);
				            $("#tag").val("");
				            $("#tag").focus();
							return;
						}
						else if(flag["flag"]=="none"){
							play();
							$("#msg").text("仓库未维护SKU种类数及SKU总数");
							return;
						}else if (flag["flag"]=="false"){
							play();
							$("#msg").text("扫描指令未找到");
							return;
						}else if(flag["flag"]=="none2"){
							play();
							$("#msg").text("收货sku总数或者sku种数超出仓库配置");
							return;
						}else if(flag["flag"]=="input"){
							play();
							$("#msg").text("按箱收货的商品必须是非效期商品、非SN商品");
							return;
						}
						else if(flag["flag"]=="err"){
							play();
							$("#msg").text("系统异常");
							return;
						}
						else if(flag["flag"]=="error4"){
							play();
							$("#msg").text("拆单的主作业单不能使用此功能进行收货");
							return;
						}else if(flag["msg2"] !=""){
							play();
							$("#msg").text(flag["msg2"]);
							return;
						}
						else {
							play();
							$("#msg").text("此作业单已收货完毕");
							return;
						}
					});
					
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
				
				function toBox(i){//页面跳转
					if(i=='1'){
						$("#receive1").show();
						$("#receive2").hide();
					}else if(i=='2'){
						$("#msg").text("");
						$("#receive1").hide();
						$("#receive2").show();
					}
				}
		</script>
	
</body>
</html>