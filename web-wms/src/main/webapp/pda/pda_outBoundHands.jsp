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
				<div align="right">未交接包裹数量:<span id="num"></span></div>
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div>扫描快递单号<span id="msg"></span></div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								   <input type="text"  class="input-element" name="trackingNo" id="trackingNo"  />
								</div>
							</div>
						</div>
						
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="Ok">交接</button>
							<button type="button" class="am-btn am-btn-primary " id="Back">返回</button>
						</div>
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
		var count;
		$(document).ready(function(){
			var flag=request($("body").attr("contextpath") + "/pda/pdaOutBoundHandNum.do");
			$("#num").text(flag["msg"]);
			count=parseInt(flag["num"]);
		});
		$("#trackingNo").keypress(function(evt){
			if (evt.keyCode === 13) {
				var trackingNo = $("#trackingNo").val() ;
				if(trackingNo!=""){
					var num=parseInt($("#num").html());
					if(null==count||undefined==count||''==count){
						count=200;
					}
					
					if(parseInt(num)>parseInt(count)){
						$("#msg").text("未交接已经超"+count+",请先交接");
						alert("未交接已经超"+count+",请先交接");
						return;
					}else{
						check();
					}
					
				}else {
					$("#msg").text("请输入快递单号");
					alert("请输入快递单号");
				}
			}
		});
		//退出登录
		$("#exit").click(function(){
			window.location.href=$("body").attr("contextpath")+"/pda/pdaExit.do";
		})
		//返回
		$("#Back").click(function(){
			window.location.href=$("body").attr("contextpath")+"/pda/menu.do";
		});
		
		$("#Ok").click(function(){
			var flag=request($("body").attr("contextpath") + "/pda/pdaOutBoundHand.do");
			if(flag["msg"]=="success"){
				$("#msg").text("交接成功");
				$("#num").text(0);
			}else{
				$("#msg").text("没有可交接的订单");
				alert("没有可交接的订单");
			}
		});
		
		
		function check(){
			var postData={"trackingNo":$("#trackingNo").val()};
			var flag=request($("body").attr("contextpath") + "/pda/checkTrackingNo.do",postData);
			if(null!=flag){
				if(flag["msg"]=="success"){
					$("#msg").text("容许出库");
					var flag=request($("body").attr("contextpath") + "/pda/pdaOutBoundHandNum.do");
					$("#num").text(flag["msg"]);
					count=flag["num"];
					$("#trackingNo").val("");
				}else if (flag["msg"]=="ad_cancel"){
					$("#msg").text("订单已取消,不可出库");
					$("#trackingNo").val("");
					alert("订单已取消,不可出库");
				}else if(flag["msg"]=="alreadyOutBound"){
					$("#msg").text("订单出库");
					$("#trackingNo").val("");
					alert("订单出库");
					return;
				}
				else if (flag["msg"]=="ad_statusError"){
					$("#msg").text("作业单状态不对");
					$("#trackingNo").val("");
					alert("作业单状态不对");
				}
				else if (flag["msg"]=="ad_NotOutBount"){
					$("#msg").text("未付尾款,不允许出库");
					$("#trackingNo").val("");
					alert("未付尾款,不允许出库");
				}
				else if (flag["msg"]=="ad_sysError"){
					$("#msg").text("AD异常订单");
					$("#trackingNo").val("");
					alert("AD异常订单");
				}
				else if (flag["msg"]=="ad_over"){
                                        $("#msg").text("作业单已完成");
					$("#trackingNo").val("");
				}
				else if (flag["msg"]=="isNotPreSale"){
					$("#msg").text("不是预售订单,不能使用此功能");
					$("#trackingNo").val("");
					alert("不是预售订单,不能使用此功能");
				}
				else if(flag["msg"]=="staIsNotFind"){
					$("#msg").text("运单号不存在");
					$("#trackingNo").val("");
					alert("运单号不存在");
				}else if(flag["msg"]=="changeTransNo"){
					$("#msg").text("收货人信息已更改,请重新打印面单");
					$("#trackingNo").val("");
					alert("收货人信息已更改,请重新打印面单");
				}else if(flag["msg"]=="TransError"){
					$("#msg").text("无此运单号");
					$("#trackingNo").val("");
					alert("无此运单号");
				}else if(flag["msg"]=="NoTransNo"){
					$("#msg").text("收货人信息已更改,请重新打印面单");
					$("#trackingNo").val("");
					alert("收货人信息已更改,请重新打印面单");
				}else if(flag["msg"]=="NotOutBount"){
					$("#msg").text("未付尾款,不允许出库");
					$("#trackingNo").val("");
					alert("未付尾款,不允许出库");
				}else if(flag["msg"]=="cancel"){
					$("#msg").text("订单已取消,不可出库");
					$("#trackingNo").val("");
					alert("订单已取消,不可出库");
				}else if(flag["msg"]=="out"){
					$("#msg").text("已出库");
					$("#trackingNo").val("");
					alert("已出库");
				}else if(flag["msg"]=="error"){
					$("#msg").text("异常订单");
					$("#trackingNo").val("");
					alert("异常订单");
				}else if(flag["msg"].indexOf("scanning")>=0){
					$("#msg").text(flag["msg"].split("==+")[1]+"已经扫描过");
					$("#trackingNo").val("");
					alert(flag["msg"].split("==+")[1]+"已经扫描过");
				}
				else{
					$("#msg").text(flag["msg"]+".");
                    $("#trackingNo").val("");
					alert(flag["msg"]+".");
				}
				return;
			}else{
				$("#msg").text("未付尾款,不允许交接");
				$("#trackingNo").val("");
				alert("未付尾款,不允许交接");
				return;
			}
		};
		
		function request(url, data, args){
            url=url+'?version='+new Date();
			var _data, options = this._ajaxOptions(url, data, args);
			options["type"] = "POST";
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