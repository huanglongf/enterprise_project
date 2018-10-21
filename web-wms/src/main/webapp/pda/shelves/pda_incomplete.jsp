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
				<div class="am-tabs " data-am-tabs="" id="good1">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div >扫描容器号(残次品)</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
									<input type="text" class="input-element" name="staCarCode" id="staCarCode" required />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="submit" class="am-btn am-btn-primary btn-right-margin" id="goodOk1">确认</button>
							<button type="button" class="am-btn am-btn-primary" id="goodBack1">返回</button>
						</div>
					</div>
				</div>
				
				
			<!-- 	<div class="am-tabs " data-am-tabs="" id="good2">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div class="input-label am-text-light" >扫描SKU</div>
							</div>
							<div class="am-g">
							<div class="input-element-frame">
								<input type="text" class="input-element" name="sku" id="sku" required />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="goodOk2">确认</button>
							<button type="button" class="am-btn am-btn-primary " id="goodBack2" >返回</button>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" >上架完成</button>
						</div>
					</div>
				</div> -->
				
				
				
				<div class="am-tabs " data-am-tabs="" id="good3">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
						<!-- 	<div class="am-g">
								<div >请扫描如下库位进行上架</div>
							</div>
							<div class="am-g">
								<div class="input-label am-text-light" ><font id="locCode"></font></div>
							</div> -->
							<div class="am-g">
								<div class="input-label am-text-light" >扫描库位号</div>
							</div>
							<div class="am-g">
							<div class="input-element-frame">
								<input type="text" class="input-element" name="locationCode" id="locationCode" required />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="goodOk3">确认</button>
							<button type="button" class="am-btn am-btn-primary" id="goodBack3">返回</button>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary" id="shOver">容器上架完成</button>
						</div>
					</div>
				</div>
				
				
				<div class="am-tabs " data-am-tabs="" id="good4">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
						<!-- 	<div class="am-g">
								<div style="font-size: 18px;">请上架数量<font id="shelevsNum"></font>个</div>
							</div>
							<div class="am-g">
								<div style="font-size: 15px;">失效日期<font id="expDate"></font></div>
							</div> -->
							<div class="am-g">
								<div >扫描残次品条码<font id="qty1">-</font>-<font id="qty2"></font></div>
							</div>
							<div class="am-g">
							<div class="input-element-frame">
								<input type="text" class="input-element" name="skuBarCode" id="skuBarCode" required />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="goodOk4">确认</button>
							<button type="button" class="am-btn am-btn-primary " id="goodBack4">取消</button>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary" id="locationShelves">库位上架完成</button>
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
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("请输入容器号!");
					}
				}
			});
			$("#goodOk1").click(function(){//扫描容器号
				staCarCode = $("#staCarCode").val();
				if(staCarCode==""){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
				    $("#msg").text("请输入容器号!");
					return false;
				}
				var postData = {
						"code":staCarCode						
				};
				var flag=request($("body").attr("contextpath") + "/pda/initMongodbWhShelvesInfoIncomplete.do",postData);//初始化
				if(flag["flag"]=="success"){
				toBox(3);
				}else if(flag["flag"]=="error"){
					$("#staCarCode").val("");
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text(flag["msg"]+".");
				}else{
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("系统异常!");
				}
			});
			
			$("#sku").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#sku").val();
					if(key!=""){
						$("#goodOk2").trigger("click");	
					}else {
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("请输入商品!");
					}
				}
			});
			$("#goodOk2").click(function(){//扫描商品
				var sku = $("#sku").val();
				if(sku==""){
				//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
				play();
				$("#msg").text("请输入商品!");
					return false;
				}
				var postData = {
						"code":staCarCode,					
						"skuBarcode":sku						
				};
				var flag=request($("body").attr("contextpath") + "/pda/verifyAndRecommend.do",postData);//验证商品并推荐出库位
				if(flag["flag"]=="success"){
				//设置库位
				if(flag["line"]==null){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("line is null");
				}else{
			//$("#locationCode2").text(flag["line"]["locationCode"]);
					$("#shelevsNum").text(flag["line"]["qty"]);//上架总数量
					$("#expDate").text(flag["line"]["expDate"]);//商品时效日期
					$("#qty1").text(flag["line"]["qty"]);//总数
					$("#qty2").text(flag["line"]["qty"]-flag["line"]["remainQty"]);//已经扫描sku数
					$("#barCode2").val(flag["barCode"]);
				}
				toBox(3);
				}else if(flag["flag"]=="error"){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text(flag["msg"]+".");
				}else{
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("系统异常!");
				}
			});
			
			$("#locationCode").keypress(function(evt){//enter locationCode
				if (evt.keyCode === 13) {
					var key = $("#locationCode").val();
					if(key!=""){
						$("#goodOk3").trigger("click");	
					}else {
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("请输入库位!");
					}
				}
			});
			$("#goodOk3").click(function(){//扫描库位
				var locationCode = $("#locationCode").val();
				if(locationCode==""){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("请输入库位!");
				}else{
					var postData = {
							"locationCode":locationCode
					};
					var flag=request($("body").attr("contextpath") + "/pda/verifyLocation.do",postData);//验证商品来更新 插入作业单操作明细
					if(flag["flag"]=="success"){
							if(flag["location"]=="0"){
								$("#locationCode").val("");
								//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
								play();
								$("#msg").text("该仓库没有此库位");
							}else{
								$("#qty1").text("");
								$("#qty2").text("");
								toBox(4);
							}
					}else{
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("系统异常");
					}
					
				}
			});
			
			$("#skuBarCode").keypress(function(evt){//enter skuBarCode
				if (evt.keyCode === 13) {
					var key = $("#skuBarCode").val();
					if(key!=""){
						$("#goodOk4").trigger("click");	
					}else {
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("请输入残次条码!");
					}
				}
			});
				$("#goodOk4").click(function(){//逐渐扫描商品
				var skuBarCode = $("#skuBarCode").val();
				var locationCode=$("#locationCode").val();
				if(skuBarCode==""){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("请输入残次条码!");
				}else if (locationCode==""){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("原库位编码null");
				}else{
					var postData = {
							"locationCode":locationCode,
							"code":staCarCode,
							"skuBarcode":skuBarCode
					};
					var flag=request($("body").attr("contextpath") + "/pda/scanSkuIncomplete.do",postData);//验证商品来更新 插入作业单操作明细
					if(flag["flag"]=="success"){
						if(flag["line"]==null){
							//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
							play();
							$("#msg").text("line is null");
						}else{
							$("#skuBarCode").val("");
							$("#msg").text("");
							if(flag["isOver"]=='1'){
								window.location.href=$("body").attr("contextpath")+"/pda/pageRedirectIncomplete.do";
							}else{
							$("#qty1").text(flag["line"]["qty"]);//总数
							$("#qty2").text(flag["line"]["qty"]-flag["line"]["remainQty"]);//已经扫描sku数
							}
						}
					}else if(flag["flag"]=="error"){
							$("#skuBarCode").val("");
							//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
							play();
							$("#msg").text(flag["msg"]);
						}else{
							//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
							play();
							$("#msg").text("系统异常!");
						}
				}
			});
			//该容器上架完成
			$("#shOver").click(function(){
				$("#staCarCode").val("");
				toBox(1);
			});
			
			//返回1
			$("#goodBack1").click(function(){
				window.location.href=$("body").attr("contextpath")+"/pda/menu.do";
			});
			//返回2 
			$("#goodBack2").click(function(){
				$("#staCarCode").val("");
				toBox(1);
			});
			//返回3 
			$("#goodBack3").click(function(){
				$("#staCarCode").val("");
				toBox(1);
			});
			//返回4 
			$("#goodBack4").click(function(){
				var locationCode=$("#locationCode").val();
				var postData = {
						"locationCode":locationCode,
						"code":staCarCode
				};
				var flag=request($("body").attr("contextpath") + "/pda/cancelSkuIncomplete.do",postData);//扫描商品取消回滚
				if(flag["flag"]=="success"){
					toBox(3);
					$("#locationCode").val("");
				}else if(flag["flag"]=="error"){
					//alert(flag["msg"]);
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text(flag["msg"]+".");
				}else{
					//alert("系统异常!");
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("系统异常!");
				}
			});
			//库位上架完成
			$("#locationShelves").click(function(){
				var locationCode = $("#locationCode").text();//推荐库位
				var postData = {
						"locationCode":locationCode,
						"code":staCarCode
				};
				var flag=request($("body").attr("contextpath") + "/pda/locationShelvesDmgCode.do",postData);//库位上架完成
				if(flag["flag"]=="success"){
					if(flag["isOver"]=='1'){
						window.location.href=$("body").attr("contextpath")+"/pda/pageRedirectIncomplete.do";
					}else{
						$("#good2 input,#good2 input,#good3 input,#good4 input").val("");
						toBox(3);//跳转扫描商品推荐库位页面
					}
				}else if(flag["flag"]=="error"){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text(flag["msg"]+".");
				}else{
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("系统异常!");
				}
			});
			//退出登录
			$("#exit").click(function(){
				window.location.href=$("body").attr("contextpath")+"/pda/pdaExit.do";
			})
		});
		
		function toBox(i){//页面跳转
			if(i=='1'){
				$("#good1").show();
				$("#good2").hide();
				$("#good3").hide();
				$("#good4").hide();
				$("#staCarCode").focus();
				$("#msg").text("");
			}else if(i=='2'){
				$("#good1").hide();
				$("#good2").show();
				$("#good3").hide();
				$("#good4").hide();
			}else if(i=='3'){
				$("#good1").hide();
				$("#good2").hide();
				$("#good3").show();
				$("#good4").hide();
				$("#locationCode").focus();
				$("#msg").text("");
			}else if(i=='4'){
				$("#good1").hide();
				$("#good2").hide();
				$("#good3").hide();
				$("#good4").show();
				$("#skuBarCode").focus();
				$("#msg").text("");
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
		</script>
</body>
</html>