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
		<div><font id="msg" color="red"></font></div>
		<div class="am-tabs " data-am-tabs="" id="div1">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
						    <div  style="font-size:15px;text-align: center;">&nbsp;&nbsp;波次号:</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								   <input type="text" class="input-element"  name="pickingListCode" id="pickingListCode" />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="submit" class="am-btn am-btn-primary btn-right-margin" id="goodOk1">确认</button>
							<button type="button" class="am-btn am-btn-primary" id="goodBack1">返回</button>
						</div>
					</div>
		</div>
		<div class="am-tabs " data-am-tabs="" id="div2">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
						    <div  style="font-size:15px;text-align: center;">&nbsp;&nbsp;相关单据号:</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								   <input type="text" class="input-element"  name="slipCode" id="slipCode" />
								</div>
							</div>
							<div class="am-g">
						    <div  style="font-size:15px;text-align: center;">&nbsp;&nbsp;周转箱条码:</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								   <input type="text" class="input-element"  name="code" id="code" />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary" id="goodBack2">返回</button>
						</div>
					</div>
		</div>
		<div class="am-tabs " data-am-tabs="" id="div3">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
						    <div  style="font-size:15px;text-align: center;">&nbsp;&nbsp;仓库区域编码:</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								   <input type="text" class="input-element"  name="zoonCode" id="zoonCode" />
								</div>
							</div>
							<div class="am-g">
						    <div  style="font-size:15px;text-align: center;">&nbsp;&nbsp;周转箱条码:</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								   <input type="text" class="input-element"  name="boxCode" id="boxCode" />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="submit" class="am-btn am-btn-primary btn-right-margin" id="goodOk3">确认</button>
							<button type="submit" class="am-btn am-btn-primary btn-right-margin" id="zoonOver">区域绑定完成</button>
							<button type="button" class="am-btn am-btn-primary" id="goodBack3">返回</button>
						</div>
					</div>
		</div>
	</div>
		<%@include file="/pda/commons/common-modal.jsp"%>
		<%@include file="/pda/commons/common-play.jsp"%>
		<%@include file="/pda/commons/common-script.jsp"%>
		<script type="text/javascript">
		var slipCode="";
		var code="";
		var pickingListCode="";
		var curStep="";
		var pId =0;
		var flag = false;
		var boxCodeList= new Array();
		$(document).ready(function(){
			$("#div2").hide();
			$("#div3").hide();
			setFocusOn("pickingListCode");
			
			$("#pickingListCode").keypress(function(event){
				if (event.keyCode === 13) {
					$("#goodOk1").trigger("click");	
				}
			});
			$("#slipCode").keypress(function(event){
				if (event.keyCode === 13) {
					slipCode=$("#slipCode").val().trim();
					if(slipCode==""){
						$("#msg").text("请扫描箱子中任意一个单据的相关单据号!");
					}else{
						code="";
						setFocusOn("code");
					}	
				}
			});
			
			$("#code").keypress(function(event){
				if (event.keyCode === 13) {
					code=$("#code").val().trim();
					if(code==""){
						$("#msg").text("请扫描集装箱条码!");
					}else{
						slipCode=$("#slipCode").val().trim();
						if(slipCode==""){
							$("#msg").text("请扫描箱子中任意一个单据的相关单据号!");
							slipCode="";
							setFocusOn("slipCode");
						}else{
							var rs=request($("body").attr("contextpath") + "/pda/bindbatchandturnbox.do",{"slipCode":slipCode,"code":code,"pickId":pId});
							if(rs!=null&&rs.result=="success"){
								if(rs.flag="true"){
									flag = true;
									$("#msg").text("当前批次已经完成全部绑定!");
								}else{
									clear();
									setFocusOn("slipCode");
								}
							}else{
								curStep = "slipCode";
								$("#msg").text(rs.msg);
							}
						}
						
					}
				}
			});
			
			$("#zoonCode").keypress(function(event){
				if (event.keyCode === 13) {
					slipCode=$("#zoonCode").val().trim();
					if(slipCode==""){
						$("#msg").text("请扫描仓库区域编码!");
					}else{
						code="";
						setFocusOn("boxCode");
					}
				}
			});
			
			$("#boxCode").keypress(function(event){
				if (event.keyCode === 13) {
					$("#goodOk3").trigger("click");	
				}
			});
			
			
			$("#goodBack1").click(function(){
				window.location.href=$("body").attr("contextpath")+"/pda/menu.do";
			});
			$("#goodBack2").click(function(){
				code="";
				slipCode="";
				clear();
				curStep="pickingListCode";
				$("#div2").hide();
				$("#div3").hide();
				$("#div1").show();
				setFocusOn(curStep);
			});
			$("#goodBack3").click(function(){
				$("#goodBack2").trigger("click");	
			});
			$("#goodOk1").click(function(){
				pickingListCode = $("#pickingListCode").val().trim();
				if(pickingListCode==""){
					$("#msg").text("请扫描拣货单上的波次号！");
				}else{
					var rs=request($("body").attr("contextpath") + "/pda/checkbindpickinglist.do",{"plCode":pickingListCode});
					if(rs!=null&&rs.result=="success"){
						pId = rs.pId;
						$("#div1").hide();
						if(rs.pType==5){//单件
							$("#div2").show();
							setFocusOn("slipCode");
						}else if(rs.pType==1 || rs.pType==3|| rs.pType==10){//多件
							boxCodeList= new Array();
							$("#div3").show();
							setFocusOn("zoonCode");
						}
					}else{
						$("#msg").text(rs.msg);
					}
				}
			});
			
			$("#goodOk3").click(function(){
				code=$("#boxCode").val();	
				if(code==""){
					$("#msg").text("请扫描周转箱条码!");
				}else{
					slipCode=$("#zoonCode").val().trim();
					if(slipCode==""){
						$("#msg").text("请扫描仓库区域编码!");
						slipCode="";
						setFocusOn("zoonCode");
					}else{
						$('#zoonCode').attr("disabled",true);
						for(var i = 0; i < boxCodeList.length; i++){
							if(boxCodeList[i]==code){
								$("#msg").text("周转箱编码已存在");
								return;
							}
						}
						boxCodeList.push(code);
						setFocusOn("boxCode");
						$("#msg").text("");
					}
				}
			});
			
			
			$("#zoonOver").click(function(){
				if(boxCodeList.length==0){
					$("#msg").text("请输入周转箱编码");
				}else{
					var postData={};
					for(var i = 0; i < boxCodeList.length; i++){
						postData['boxCode['+i+']'] = boxCodeList[i];
					}
					postData["zoonCode"] = slipCode;
					postData["pickId"]=pId;
					var rs=request($("body").attr("contextpath") + "/pda/pickingListAndZoneOver.do",postData);
					if(rs!=null&&rs.result=="success"){
						
						if(rs.over!=""&&rs.over!=null){
							$("#msg").text(rs.over);
						}else{
							$("#msg").text("绑定成功！");
						}
					}else{
						$("#msg").text(rs.msg);
					}
					
					boxCodeList= new Array();
					$('#zoonCode').removeAttr("disabled"); 
					$("#zoonCode").val("");
					$("#boxCode").val("");
					$("#pickingListCode").val("");
				}	
			});
		});
		
		function setFocusOn(flag){
			$("#"+flag).val("");
			$("#"+flag).focus();
		}
		function clear(){
			$("input").val("");
			$("#msg").text("");
			boxCodeList= new Array();
			$('#zoonCode').removeAttr("disabled"); 
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