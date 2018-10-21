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
				<div class="am-tabs " data-am-tabs="" id="good1">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div >扫描容器号(良品批量)</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
									<input type="text" class="input-element" name="staCarCode" id="staCarCode" required />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="goodOk1">确认</button>
							<button type="button" class="am-btn am-btn-primary" id="goodBack1">返回</button>
						</div>
					</div>
				</div>
				
				
				<div class="am-tabs " data-am-tabs="" id="good2">
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
							<button type="button" class="am-btn am-btn-primary " id="goodBack2" >取消</button>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="shOver">上架完成</button>
						</div>
					</div>
				</div>
				
				
				
				<div class="am-tabs " data-am-tabs="" id="good3">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div >请扫描如下库位进行上架</div>
							</div>
							<div class="am-g">
								<div class="input-label am-text-light" ><font id="locCode"></font></div>
							</div>
							<div class="am-g">
								<div class="input-label am-text-light" >扫描库位号</div>
								<div class="input-label am-text-light"  id="locationCode2"></div>
								<input type="hidden"  id="barCode2" value=""/>
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
					</div>
				</div>
				
				
				<div class="am-tabs " data-am-tabs="" id="good4">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div style="font-size: 18px;">请上架数量<font id="shelevsNum"></font>个</div>
							</div>
							<div class="am-g">
								<div style="font-size: 15px;">失效日期<font id="expDate"></font></div>
							</div>
							<div class="am-g">
								<div >扫描SKU&nbsp;&nbsp;&nbsp;</div>
							</div>
							<div class="am-g">
							<div class="input-element-frame">
								<input type="text" class="input-element" name="skuBarCode" id="skuBarCode" required />
								</div>
							</div>
								<div class="am-g">
								<div >录入数量</div>
							</div>
							<div class="am-g">
							<div class="input-element-frame">
								<input type="text" class="input-element" name="num" id="num" required />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="goodOk4">确认</button>
							<button type="button" class="am-btn am-btn-primary " id="goodBack4">返回</button>
						</div>
						<!-- <div class="btn-center">
							<button type="button" class="am-btn am-btn-primary" id="locationShelves">库位上架完成</button>
						</div> -->
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
					//	playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("请输入容器号!");
					}
				}
			});
			$("#goodOk1").click(function(){//扫描容器号
				staCarCode = $("#staCarCode").val();
				if(staCarCode==""){
				//	playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("请输入容器号!");
					return false;
				}
				var postData = {
						"code":staCarCode						
				};
				var flag=request($("body").attr("contextpath") + "/pda/initMongodbWhShelvesInfo.do",postData);//初始化
				if(flag["flag"]=="success"){
				//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
				play();
				$("#tip").text(flag["tip"]);
				toBox(2);
				}else if(flag["flag"]=="error"){
					$("#staCarCode").val("");
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text(flag["msg"]);
				}else{
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("系统异常!");
				}
			});
			
		 $("#skuBarCode").keydown(function(evt) {//光标跳转
				if (evt.keyCode === 13) {
					var key = $("#skuBarCode").val();
					var sku = $("#sku").val();
					if(key==""){
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("请输入商品!");
					}else if(key!=sku){
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#skuBarCode").val("");
						$("#msg").text("扫描商品编码有误");
					}else {
						$("#msg").text("");
						$("#num").focus();
					}
				}
			});
			
			$("#sku").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#sku").val();
					if(key!=""){
						$("#goodOk2").trigger("click");	
					}else {
					//	playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("请输入商品!");
					}
				}
			});
			
			$("#num").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#num").val();
					if(key!=""){
						$("#goodOk4").trigger("click");	
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
					$("#locationCode2").text(flag["line"]["locationCode"]);
					$("#shelevsNum").text(flag["line"]["remainQty"]);//上架总数量
					$("#expDate").text(flag["line"]["expDate"]);//商品时效日期
				//	$("#qty1").text(flag["line"]["qty"]);//总数
					//$("#qty2").text(flag["line"]["qty"]-flag["line"]["remainQty"]);//已经扫描sku数
					$("#barCode2").val(flag["barCode"]);
				}
				toBox(3);
				}else if(flag["flag"]=="error"){
				//	playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text(flag["msg"]);
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
				var locationCode2 = $("#locationCode2").text();
				var locationCode = $("#locationCode").val();
				if(locationCode==""){
				//	playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("请输入库位!");
				}else if(locationCode2==""){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("库位提示有误!");
				}else if(locationCode2==locationCode){
					$("#num").val("");
					$("#skuBarCode").val("");
					///////////////////////////////////////////////////////////////////////////
					var sku = $("#sku").val();
					var postData = {
							"code":staCarCode,					
							"skuBarcode":sku						
					};
					var flag=request($("body").attr("contextpath") + "/pda/verifyAndRecommend.do",postData);//验证商品并推荐出库位
					if(flag["flag"]=="success"){
					//设置库位
					if(flag["line"]==null){
					//	playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("line is null");
					}else{
						$("#locationCode2").text(flag["line"]["locationCode"]);
						$("#shelevsNum").text(flag["line"]["remainQty"]);//上架总数量
						$("#expDate").text(flag["line"]["expDate"]);//商品时效日期
						}
					}
					//////////////////////////////////////////////////////////////////////////
					toBox(4);
				}else{
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("请输入正确的库位!");
				}
			});
			
	
				$("#goodOk4").click(function(){//确定上架
				var skuBarCode = $("#skuBarCode").val();
				var sku = $("#sku").val();
				var locationCode=$("#locationCode").val();
				var num=$("#num").val();
				var shelevsNum=$("#shelevsNum").text();
				if(skuBarCode==""){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("请输入商品编码!");
				}else if(sku==""){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("原商品编码null");
				}else if (locationCode==""){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("原库位编码null");
				}else if(sku!=skuBarCode){
				//	playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("扫描商品编码有误");
				}else if(num==""){
				//	playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
				play();
					$("#msg").text("请输入数字");
				}else if(isNaN(num)){
					$("#num").val("");
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("请输入数字");
				}else if(num=="0"){
					$("#num").val("");
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("录入数量不能为0");
				}else if(parseInt(num)>parseInt(shelevsNum)){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("录入数量不能大于推荐数量");
				}else{
					var postData = {
							"locationCode":locationCode,
							"code":staCarCode,
							"skuBarcode":skuBarCode,
							"num":num
					};
					var flag=request($("body").attr("contextpath") + "/pda/scanSku2.do",postData);//验证商品来更新 插入作业单操作明细
					if(flag["flag"]=="success"){
						if(flag["line"]==null){
							//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
							play();
							$("#msg").text("line is null");
						}else{
								if(flag["isOver"]=='1'){
									window.location.href=$("body").attr("contextpath")+"/pda/pageRedirectGoodBatch.do";
								}else{
									$("#good2 input,#good2 input,#good3 input,#good4 input").val("");
									toBox(2);//跳转扫描商品推荐库位页面
								}
						}
					}else if(flag["flag"]=="error"){
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
			//上架返回 
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
				$("#sku").val("");
				toBox(2);
			});
			//返回4 
			$("#goodBack4").click(function(){
				$("#locationCode").val("");
					toBox(3);
			});
			//库位上架完成
			$("#locationShelves").click(function(){
				var locationCode2 = $("#locationCode2").text();//推荐库位
				var barCode = $("#barCode2").val();//商品barCode
				var postData = {
						"locationCode":locationCode2,
						"code":staCarCode,
						"skuBarcode":barCode
				};
				var flag=request($("body").attr("contextpath") + "/pda/locationShelves.do",postData);//库位上架完成
				if(flag["flag"]=="success"){
					if(flag["isOver"]=='1'){
						window.location.href=$("body").attr("contextpath")+"/pda/pageRedirectGoodBatch.do";
					}else{
						$("#good2 input,#good2 input,#good3 input,#good4 input").val("");
						//$("#sku").val("");
						toBox(2);//跳转扫描商品推荐库位页面
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
		
			$("#exit").click(function(){//退出登录
				window.location.href=$("body").attr("contextpath")+"/pda/pdaExit.do";
			});
		});
		
		function toBox(i){//页面跳转
			if(i=='1'){
				$("#good1").show();
				$("#good2").hide();
				$("#good3").hide();
				$("#good4").hide();
				$("#staCarCode").focus();
				$("#msg").text("");
				$("#tip").text("");
			}else if(i=='2'){
				$("#good1").hide();
				$("#good2").show();
				$("#good3").hide();
				$("#good4").hide();
				$("#sku").val("");
				$("#sku").focus();
				$("#msg").text("");
			}else if(i=='3'){
				$("#good1").hide();
				$("#good2").hide();
				$("#good3").show();
				$("#good4").hide();
				$("#locationCode").focus();
				$("#msg").text("");
				$("#tip").text("");
				$("#locationCode").val("");
			}else if(i=='4'){
				$("#good1").hide();
				$("#good2").hide();
				$("#good3").hide();
				$("#good4").show();
				$("#skuBarCode").focus();
				$("#skuBarCode").val("");
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
		
		</script>
</body>
</html>