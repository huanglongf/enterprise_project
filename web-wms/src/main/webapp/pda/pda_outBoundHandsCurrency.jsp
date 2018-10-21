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
				<div style="display:none;" align="right"><span id="num"></span></div>
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
									物流服务商：<select id="selTrans">
										<option value="">请选择</option>
									</select>
							</div>
						</div>
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div>扫描快递单号</div>
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
			var flag=request($("body").attr("contextpath") + "/pda/pdaOutBoundHandCurrencyNum.do");
			$("#num").text(flag["msg"]);
			count=parseInt(flag["num"]);
			
			//物流服务商列表
			var flag = request($("body").attr("contextpath") + "/pda/getTransportatorPda.do");
			for(var idx in flag){
				$("<option value='" + flag[idx].code + "'>"+ flag[idx].name +"</option>").appendTo($("#selTrans"));
			}
		});
		$("#trackingNo").keypress(function(evt){
			if (evt.keyCode === 13) {
				var trans=$("#selTrans").val();
				if(trans!=""){
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
				}else{
					$("#msg").text("请选择物流服务商");
					alert("请选择物流服务商");
				}
			}
		});
		$("#trackingNo").focus(function(){
			var trans=$("#selTrans").val();
			if(trans!=""){
			}else{
				$("#msg").text("请选择物流服务商");
				alert("请选择物流服务商");
			}
		});
		$('#selTrans').change(function () {
			var trans=$("#selTrans").val();
			if(trans!=""){
				$("#trackingNo").focus();
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
			var flag=request($("body").attr("contextpath") + "/pda/pdaOutBoundHandCurrency.do");
			if(flag["msg"]=="success"){
				$("#num").text(0);
				$("#msg").text("交接成功");
				$("#trackingNo").focus();
			}else{
				$("#msg").text("没有可交接的订单");
				$("#trackingNo").focus();
				alert("没有可交接的订单");
			}
		});
		
		function checkSelTrans(){
			var data=$("#selTrans").val();
			if(data!=""){
				return true;
			}
			return false;
		}
		
		function check(){
			var postData={"trackingNo":$("#trackingNo").val(),"lpCode":$("#selTrans").val()};
			var flag=request($("body").attr("contextpath") + "/pda/checkTrackingNoCurrency.do",postData);
			if(null!=flag){
				if(flag["msg"]=="success"){
					var flag=request($("body").attr("contextpath") + "/pda/pdaOutBoundHandCurrencyNum.do");
					$("#num").text(flag["msg"]);
					count=flag["num"];
					$("#trackingNo").val("");
					$("#msg").text("允许出库");
				}else if (flag["msg"]=="isPreSale"){
					$("#trackingNo").val("");
					$("#msg").text("预售订单,不能使用此功能");
					alert("预售订单,不能使用此功能");
				}else if (flag["msg"]=="notLogisticsPackage"){
					$("#trackingNo").val("");
					$("#msg").text("本次扫描非该物流商包裹，请核对");
					alert("本次扫描非该物流商包裹，请核对");
				}else if(flag["msg"]=="staIsNotFind"){
					$("#trackingNo").val("");
					$("#msg").text("运单号不存在");
					alert("运单号不存在");
				}else if(flag["msg"]=="packageStatusOperation"){
					$("#trackingNo").val("");
					$("#msg").text("包裹状态不能交接");
					alert("包裹状态不能交接");
				}else if(flag["msg"]=="yes"){
					$("#trackingNo").val("");
					$("#msg").text("包裹已生成交接清单");
					alert("包裹已生成交接清单");
				}
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