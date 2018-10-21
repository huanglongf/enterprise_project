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
				<div><font id="tip" color="red"></font></div>	
				<div><font id="msg" color="red"></font></div>		
				<div  style="display:none;" ><font id="eDate2" color="red"></font></div>
				<div  style="display:none;" ><font id="isQx" color="red"></font></div>			
				<div class="am-tabs " data-am-tabs="" id="good1">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
						    <div  style="font-size:15px;text-align: left;">&nbsp;&nbsp;扫描容器号(良品批量(人))</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								   <input type="text" class="input-element"  name="staCarCode" id="staCarCode" />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="submit" class="am-btn am-btn-primary btn-right-margin" id="goodOk1">确认</button>
							<button type="button" class="am-btn am-btn-primary" id="goodBack1">返回</button>
						</div>
					</div>
				</div>
				<div class="am-tabs " data-am-tabs="" id="good2">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
				<!-- 	<div class="am-tab-panel am-fade  am-in" id="scanShow" >
							<div class="am-g">
						    <div  style="font-size:15px;text-align: left;">正在扫描商品条码</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame"  id="skuCode3"></div>
							</div>
					</div> -->
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
						    <div  style="font-size:15px;text-align: left;">&nbsp;&nbsp;扫描商品条码</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								   <input type="text" class="input-element"  name="skuCode" id="skuCode" />
								</div>
							</div>
						</div>
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
						    <div  style="font-size:15px;text-align: left;">&nbsp;&nbsp;数量</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
									<!-- <div class="input-element-frame" id ="skuCodeNum">0</div> -->
								   <input type="text" class="input-element"  name="skuCodeNum" id="skuCodeNum"/>
								</div>
							</div>
						</div>
						
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="goodOk2">确认</button>
							<button type="button" class="am-btn am-btn-primary " id="goodBack2" >返回</button>
						</div>
						<!-- <div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="shOver">上架完成</button>
						</div> -->
					</div>
				</div>
				
				
				
				<div class="am-tabs " data-am-tabs="" id="good3">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
						    <div  style="font-size:15px;text-align: left;">&nbsp;&nbsp;商品条码</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								  <!--  <input type="text" class="input-element"  name="skuCode2" id="skuCode2" /> -->
										<div class="input-element-frame"  id="skuCode2"></div>
								</div>
							</div>
						</div>
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
						    <div  style="font-size:15px;text-align: left;">&nbsp;&nbsp;数量</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
<!-- 								   <input type="text" class="input-element"  name="skuCodeNum2" id="skuCodeNum2"/>
 -->								
 											<div class="input-element-frame"  id="skuCodeNum2"></div>
 								</div>
							</div>
						</div>
					<div  style="display:none;"  id="eDatePlay" class="am-tab-panel am-fade  am-in" >
						<div class="am-g">
					    <div  style="font-size:15px;text-align: left;">失效日期</div>
						</div>
						<div class="am-g">
							<div class="input-element-frame">
							   <input type="text" class="input-element"  name="eDate" id="eDate"/>
							</div>
						</div>
						</div>
						<div  style="display:none;" id="sDatePlay"  class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
						    <div  style="font-size:15px;text-align: left;">生产日期</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								   <input type="text" class="input-element"  name="sDate" id="sDate"/>
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="goodOk3">确认</button>
							<button type="button" class="am-btn am-btn-primary" id="goodBack3">返回</button>
						</div>
					</div>
				</div>
				
				
				<div class="am-tabs " data-am-tabs="" id="good4">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
						    <div  style="font-size:15px;text-align: left;">&nbsp;&nbsp;扫描库位</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								   <input type="text" class="input-element"  name="location" id="location" />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="goodOk4">确认</button>
							<button type="button" class="am-btn am-btn-primary " id="goodBack4">取消</button>
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
		var staCarCode;//容器号
		$(document).ready(function(){
			toBox(1);
			//扫描容器号
			$("#staCarCode").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#staCarCode").val();
					if(key!=""){
						$("#goodOk1").trigger("click");	
					}else {
						play();
						$("#msg").text("请输入容器号!");
					}
				}
			});
			//扫库位
			$("#location").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#location").val();
					if(key!=""){
						$("#goodOk4").trigger("click");	
					}else{
						play();
						$("#msg").text("请输入库位!");
					}
				}
			});
			
			// 扫描商品条码
			$("#skuCode").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#skuCode").val();
					if(key!=""){
							$("#skuCodeNum").focus();
					}else {
						play();
						$("#msg").text("请输入商品条码!");
					}
				}
			});
			
			// 扫描数量
			$("#skuCodeNum").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#skuCodeNum").val();
					if(key!=""){
						$("#goodOk2").trigger("click");	
					}else {
						var num=$("#skuCodeNum").text();
						if(num !="0"){
							$("#goodOk2").trigger("click");	
						}else{
						play();
						$("#msg").text("请输入数量!");
						}
					}
				}
			});
			
			
			
			$("#goodOk1").click(function(){//扫描容器号
				staCarCode = $("#staCarCode").val();
				if(staCarCode==""){
					play();
					$("#msg").text("请输入容器号!");
					return false;
				}
				
				var postData = {
						"code":staCarCode						
				};
				var flag=request($("body").attr("contextpath") + "/pda/initMongodbWhShelvesInfoShou.do",postData);//初始化
				if(flag["flag"]=="success"){
					$("#tip").text(flag["tip"]);
					toBox(2);
					}else if(flag["flag"]=="error"){
						$("#staCarCode").val("");
						play();
						$("#msg").text(flag["msg"]);
					}else{
						play();
						$("#msg").text("系统异常!");
					} 
			});
			
			$("#goodOk2").click(function(){//扫描商品条码 确认
				var skuCode=$("#skuCode").val();
				var skuCodeNum=$("#skuCodeNum").val();
				if(skuCode==""){
					$("#msg").text("请输入商品条码");
				}else if(skuCodeNum==""){
						$("#msg").text("请输入数字");
				}else if(isNaN(skuCodeNum)){
						$("#skuCodeNum").val("");
						$("#msg").text("请输入数字");
				}else if(skuCodeNum=="0"){
						$("#skuCodeNum").val("");
						$("#msg").text("录入数量不能为0");
				}else{
					//调用该商品是否是效期商品
					var postData = {
						"code":staCarCode,						
						"skuBarcode":skuCode,
						"num":skuCodeNum
				};
				var flag=request($("body").attr("contextpath") + "/pda/checkSkuShouPro.do",postData);//调用该商品是否是效期商品
				if(flag["flag"]=="success"){
					$("#isQx").text(flag["isXq"]);
					   	$("#skuCode2").text($("#skuCode").val());
						$("#skuCodeNum2").text($("#skuCodeNum").val());
						$("#eDate").val("");
						$("#sDate").val("");
						toBox(3);
						if(flag["isXq"]=="1"){
							   $("#sDatePlay").show();
							   $("#eDatePlay").show();
								$("#eDate").focus();
						   }else{
							   $("#sDatePlay").hide();
							   $("#eDatePlay").hide();
							   $("#goodOk3").click();
						   }
					
					}else if(flag["flag"]=="error"){
						play();
						//$("#msg").text(flag["msg"]);
						$("#skuCode").val("");
						$("#skuCodeNum").val("");
						$("#skuCode").focus();
						alert("该条码:"+skuCode+"不在此箱内");
					}else{
						play();
						$("#msg").text("系统异常!");
					}
				}
			});
			
			$("#goodOk3").click(function(){//确定日期
				//验证日期
				 var b= checkDate();
				if(!b){
					return;
				}
				var skuCode2=$("#skuCode2").text();
				var skuCodeNum2=$("#skuCodeNum2").text();
				var eDate=$("#eDate").val();
				var sDate=$("#sDate").val();
				var postData = {
						"code":staCarCode,						
						"skuBarcode":skuCode2,
						"num":skuCodeNum2,
						"eDate":eDate,
						"sDate":sDate

				};
				var flag=request($("body").attr("contextpath") + "/pda/checkSkuShou.do",postData);//初始化
				if(flag["flag"]=="success"){
					$("#eDate2").text(flag["tip"]);
					toBox(4);
					}else if(flag["flag"]=="error"){
						play();
						$("#msg").text(flag["msg"]);
					}else{
						play();
						$("#msg").text("系统异常!");
					}
			});
			$("#goodOk4").click(function(){//扫描库位上架
				var skuCode2=$("#skuCode2").text();
				var skuCodeNum2=$("#skuCodeNum2").text();
				var eDate=$("#eDate2").text();
				var location=$("#location").val();
				var postData = {
						"code":staCarCode,						
						"skuBarcode":skuCode2,
						"num":skuCodeNum2,
						"eDate":eDate,
						"locationCode":location

				};
				var flag=request($("body").attr("contextpath") + "/pda/locationShelvesShou.do",postData);//上架
				if(flag["flag"]=="success"){
					$("#msg").text("该库位上架完成");
/* 					$("#tip").text(flag["tip"]);
 */							if(flag["brand"]=="1"){
								toBox(2);
								$("#eDate2").text("");
								$("#sDate").val("");
								$("#eDate").val("");
							}else{
								toBox(1);
							}
					}else if(flag["flag"]=="error"){
						play();
						$("#location").val("");
						$("#msg").text(flag["msg"]);
					}else{
						play();
						$("#msg").text("系统异常!");
					}

			});
			
			//扫描录入失效时间
			$("#eDate").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#eDate").val();
					if(key!=""){
								$("#msg").text("");
								var expirationDate1=$("#eDate").val();
								if(null!=expirationDate1&&""!=expirationDate1&&undefined!=expirationDate1){
									var a = /^(\d{4})(\d{2})(\d{2})$/;
									if (!a.test(expirationDate1)) { 
										play();
										$("#msg").text("时间格式不正确(正确 YYYYMMDD)");
										$("#eDate").val("");
										return;
									}
								}
								$("#sDate").focus();	
					}else{
						$("#sDate").focus();	
					}
				}
			});
			
			//扫描录入生产日期
			$("#sDate").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#sDate").val();
					var key2 = $("#eDate").val();
					if(key=="" && key2==""){
						$("#msg").text("请输入失效或生产日期");
						return;
					}
					if(key!=""){
							$("#msg").text("");
							var expirationDate1=$("#sDate").val();
							if(null!=expirationDate1&&""!=expirationDate1&&undefined!=expirationDate1){
								var a = /^(\d{4})(\d{2})(\d{2})$/;
								if (!a.test(expirationDate1)) { 
									play();
									$("#msg").text("时间格式不正确(正确 YYYYMMDD)");
									$("#sDate").val("");
									return;
								}
							}
					}else{
						$("#goodOk3").trigger("click");	
					}
					//$("#sDate").focus();	
				}
			});

		function checkDate(){
			var expirationDate1=$("#eDate").val();
			if(null!=expirationDate1&&""!=expirationDate1&&undefined!=expirationDate1){
				var a = /^(\d{4})(\d{2})(\d{2})$/;
				if (!a.test(expirationDate1)) { 
					play();
					$("#msg").text("时间格式不正确(正确 YYYYMMDD)");
					$("#eDate").val("");
					return  false;
				}
			}
			
			var expirationDate1=$("#sDate").val();
			if(null!=expirationDate1&&""!=expirationDate1&&undefined!=expirationDate1){
				var a = /^(\d{4})(\d{2})(\d{2})$/;
				if (!a.test(expirationDate1)) { 
					play();
					$("#msg").text("时间格式不正确(正确 YYYYMMDD)");
					$("#sDate").val("");
					return false;
				}
			}
			
			return true;
		}
			
			//返回1
			$("#goodBack1").click(function(){
				window.location.href=$("body").attr("contextpath")+"/pda/menu.do";
			});
			$("#goodBack2").click(function(){
				var result=getStaStatus();
				if(result["re"=="yes"]){
					$("#staCarCode").val("");
					$("#skuCodeNum").val("");
					$("#skuCode").val("");
					toBox(1);
				}else{
					if(confirm("此箱未完成上架,是否确认退出？")){
						$("#staCarCode").val("");
						$("#skuCodeNum").val("");
						$("#skuCode").val("");
						toBox(1);
					}
				}
			});
			//返回3 
			$("#goodBack3").click(function(){
				$("#skuCode").val("");
				$("#skuCodeNum").val("");
				$("#isQx").text("");
				toBox(2);
			});
			//返回4
			$("#goodBack4").click(function(){
				var result=getStaStatus();
					if(result["re"=="yes"]){
					$("#sDate").val("");
					$("#eDate").val("");
					var isXq= $("#isQx").text();
					toBox(3);
					if(isXq=="0"){
						$("#goodBack3").click();
					}else{
						$("#eDate").focus();
					}
				}else{
					if(confirm("此箱未完成上架,是否确认退出？")){
						$("#sDate").val("");
						$("#eDate").val("");
						var isXq= $("#isQx").text();
						toBox(3);
						if(isXq=="0"){
							$("#goodBack3").click();
						}else{
							$("#eDate").focus();
						}
					}
				}
			});
			
			//退出登录
			$("#exit").click(function(){
				//退出前校验作业单状态是否完成
				var result=getStaStatus();
				if(result["re"]=="yes"||result["re"]=="error"){
				window.location.href=$("body").attr("contextpath")+"/pda/pdaExit.do";
				}else{
					if(confirm("此箱未完成上架,是否确认退出？")){
						window.location.href=$("body").attr("contextpath")+"/pda/pdaExit.do";
					}
				}
			});
		});
		
		function toBox(i){//页面跳转
			if(i=='1'){
				$("#good1").show();
				$("#good2").hide();
				$("#good3").hide();
				$("#good4").hide();
				$("#staCarCode").focus();
				$("#staCarCode").val("");
				$("#msg").text("");
				$("#tip").text("");
			}else if(i=='2'){
				$("#good1").hide();
				$("#good2").show();
				$("#good3").hide();
				$("#good4").hide();
				$("#skuCode").focus();
				$("#skuCode").val("");
				$("#msg").text("");
				$("#skuCodeNum").val("");
			}else if(i=='3'){
				$("#good1").hide();
				$("#good2").hide();
				$("#good3").show();
				$("#good4").hide();
				$("#msg").text("");
				$("#tip").text("");
			}else if(i=='4'){
				$("#good1").hide();
				$("#good2").hide();
				$("#good3").hide();
				$("#good4").show();
				$("#location").focus();
				$("#location").val("");
				$("#msg").text("");
				$("#tip").text("");
			}
		}
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
		function getStaStatus(){
			var slipCode = $("#staCarCode").val();
			var result={};
			if(slipCode==null||slipCode==""){
				result["re"]="yes";
			}else{
				var postData = {
						"code":slipCode					
				};
				result=request($("body").attr("contextpath") + "/pda/checkStaStatus.do",postData);
			}
			return result;
		};
		</script>
</body>
</html>