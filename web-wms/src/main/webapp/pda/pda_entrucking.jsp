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
				<div class="am-tabs " data-am-tabs="" id="receive1">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div id="before">
							<div class="am-g">
									车牌号码：<select id="licensePlateNumber">
									</select>
							</div>
							<div class="am-g">
								<div>扫描运单号</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								   <input type="text"  class="input-element" name="trackingNo" id="trackingNo"  />
								</div>
							</div>
						</div>
						<div id="after" style="display:none;margin-top:10px;">
						    <div id="licensePlateNumberAfter">
						    </div>
						    <div id="trackingNoAfter">
						    </div>
						</div>
						<div><font id="msg" color="red"></font></div>
						<div class="btn-center" id="buttonbefore">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="Ok">装车完成</button>
							<button type="button" class="am-btn am-btn-primary " id="Back">返回</button>
						</div>
						
						<div class="btn-center" id="buttonafter" style="display:none;">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="confirm">装车</button>
							<button type="button" class="am-btn am-btn-primary " id="refuse">不装车</button>
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
		$(document).ready(function(){
			
			var flag = request($("body").attr("contextpath") + "/pda/findLicensePlateNumber.do");
			if(flag==null){
				$("#msg").text("当天没有可用车辆,请去维护车辆信息");
			}
			for(var idx in flag){
				$("<option value='" + flag[idx].licensePlateNumber + "'>"+ flag[idx].licensePlateNumber +"</option>").appendTo($("#licensePlateNumber"));
			}
		});
		$("#trackingNo").focus(function(){
			var licensePlateNumber=$("#licensePlateNumber").val();
			if(licensePlateNumber!=""){
			}else{
				$("#msg").text("请选择车牌号码");
			}
		});
		$("#trackingNo").focus();
		//退出登录
		$("#exit").click(function(){
			window.location.href=$("body").attr("contextpath")+"/pda/pdaExit.do";
		})
		//返回
		$("#Back").click(function(){
			window.location.href=$("body").attr("contextpath")+"/pda/menu.do";
		});
		
		$("#Ok").click(function(){
			var licensePlateNumber=$("#licensePlateNumber").val();
			var trackingNo=$("#trackingNo").val();
		 	var flag=request($("body").attr("contextpath") + "/pda/trucking.do",{"licensePlateNumber":licensePlateNumber,"trackingNo":trackingNo});
			if(flag["msg"]=="success"){
				$("#msg").text("装车成功");
				$("#trackingNo").val("");
			}else{
				$("#before").hide();
				$("#after").show();
				$("#buttonbefore").hide();
				$("#buttonafter").show();
				$("#licensePlateNumberAfter").text("车牌号:"+$("#licensePlateNumber").val());
				$("#trackingNoAfter").text("运单号:"+$("#trackingNo").val());
				$("#msg").text("此包裹属于车辆号:"+flag["msg"]+"，请确认是否继续装车？");
			} 
		});
		
		$('#trackingNo').bind('keyup', function(event) {
			if (event.keyCode == "13") {
				$('#Ok').click();
			}
		}); 
		
		//不装车
		$("#refuse").click(function(){
			window.location.reload();
		});
		
		//装车
		$("#confirm").click(function(){
			var licensePlateNumber=$("#licensePlateNumber").val();
			var trackingNo=$("#trackingNo").val();
			var flag=request($("body").attr("contextpath") + "/pda/truckingConfirm.do",{"licensePlateNumber":licensePlateNumber,"trackingNo":trackingNo});
			if(flag["msg"]=="success"){
				$("#msg").text("装车成功");
			}else{
				$("#msg").text(flag["msg"]);
				$("#before").show();
				$("#after").hide();
				$("#buttonbefore").show();
				$("#buttonafter").hide();
			}
		});
		
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