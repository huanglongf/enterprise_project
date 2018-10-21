<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/meta.jsp"%>
<title>宝尊电商WMS3.0</title>
<link rel="stylesheet" type="text/css" href="<s:url value='/css/fg.menu.css' includeParams='none' encode='false'/>" />
<link rel="stylesheet" type="text/css" href="<s:url value='/css/layout-default-latest.css' includeParams='none' encode='false'/>" />
<style>
html, body {
	height: 100%;
	overflow: hidden;
}

.body_div {
	width: 700px;
	margin: 40px auto;
	border: 1px solid #999;
	padding: 0 20px;
	border-radius: 10px;
}

h2 {
	text-align: center;
}
</style>
<script type="text/javascript" src="<s:url value='/scripts/main/fg.menu.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery.layout-latest.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main/jquery.wresize.js' includeParams='none' encode='false'/>"></script>

<script type="text/javascript" src="<s:url value='/scripts/main-content-frame.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='/scripts/main-content.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript">
$j(document).ready(function (){
		$j("#tabs").tabs({});
		$j("#rfidCode").bind("keypress",function(event){
            if(event.keyCode == "13")    
            {	
            	saveRfidSession();
            }
        });
		$j("#rfidOk").bind("keypress",function(event){
            if(event.keyCode == "13")    
            {	
            	smOk();
            }
        });
});
function saveRfidSession(){
	var rfidCode = $j("#rfidCode").val();
	loxia.syncXhr(window.parent.$j("body").attr("contextpath")+"/sessionNikeRFID.json",{"nikeRFIDCode":rfidCode});
	 $j("#rfidOk").focus();
};
function smOk(){
	var rfidOk = $j("#rfidOk").val();
	var rfidCode = $j("#rfidCode").val();
	if(rfidOk == "OK"){
			$j.ajax({
	        type: 'post',
	        url: "http://10.8.35.228:8088/getRfid",
	        dataType: "json",
	        async: false,
	        data: {"hostCode":rfidCode},
	        success: function (data) {
	            if(data.code=="SUCCESS"){
	            	var value = data.rfid;
	            	$j("#rfidValue").val(value);
	            }else {
	               alert(data.msg);
	            }
	        },
	        error:function(data){
	            alert("错误");}
	    });
	}else{
			alert("请扫描OK码！");
	}
};
</script>
</head>
<body id="mainFrame" style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath()%>">
<input id="autoWeigth" type="hidden"/>
	<div id="top">
		<!--  -->
	</div>
	<div id="main-container">
		<div class="ui-layout-center">
			<div id="frame-container">
				<ul>
					<li><a href="#desktop">WMS3.0桌面</a></li>
				</ul>
				<input type="text" class="input-element" name="starbucksSnCode" id="starbucksSnCode"  hidden="true"/>
				<input type="text" id="rfidSession"  hidden="true" value="${RFIDCode}"/>
				<div id="desktop" class="ui-tabs-panel ui-widget-content ui-corner-bottom" style="height: 95%">
	 				<div  id="tabs">
					 		<ul>
								<li><a href="#tabs_1">公告</a></li>
								<li><a href="#tabs_2">设备绑定</a></li>
							</ul>
								<div id="tabs_1">
											<div class="body_div" style="font-size: 16px;">
												<h2>重要通知（WMS大陆 客户端jre升级）</h2>
												<!-- <p>为了优化WMS系统，减少系统卡顿现象，现需针对WMS客户端进行jre升级，升级后版本1.7.0_80</p>
												<p>所有客户端升级后请访问https://scm.baozun.com/scm<font color="red"> (请在仓库IT人员协助下进行安装)</font></p> -->
												<hr />
												<p>
													WMS客户端环境安装操作手册:<a href="common/wms_client_doc-2.doc">点击下载</a>
												</p>
												<p>
													火狐下载地址:<a href="common/step_1_install_firefox.msi">点击下载</a>
												</p>
												<!-- <p>
													新版JRE1.7_80下载地址:<a href="common/jre-7u80-windows-i586.exe">点击下载</a>
												</p> -->
												<p>
													新版JRE1.7_80下载地址:<a href="common/step_2_install_jre.msi">点击下载</a><font color="red"> (先安装)</font>
												</p>
												<p>
													新版JRE1.7_80辅助安装包下载地址:<a href="common/step_3_install_client_update.msi">点击下载</a><font color="red"> (后安装)</font>
												</p>
												<hr />
												<!-- <p>
													旧版JRE1.6_27安装手册下载地址:<a href="common/pring_weight_fq.doc">点击下载</a>
												</p>
												<p>
													旧版JRE1.6_27下载地址:<a href="common/jre-6u27-windows-i586-s.exe">点击下载</a>
												</p>
												<p>
													旧版JRE1.6_27辅助安装包下载地址:<a href="common/install.zip">点击下载</a>
												</p> 
												<hr />-->
												<p>
						
													<form action="common/testJre.jsp" target="test">
														系统环境测试： <input type="submit" value="点击加载测试功能" />
													</form>
													<iframe src="#" name="test" frameborder="0" height="200px"> </iframe>
												</p>
											</div>
								</div>
								<div id="tabs_2">
									<b style="font-size: 30px;">RFID设备绑定</b>
									<table>
										<tr>
											<td width="230px;" class="label" style="font-size: 15px;">RFID设备编码：</td>
											<td width="350px;" height="60px;"><input loxiaType="input" trim="true" style="height: 60px;font-size: 60px;" id="rfidCode"/></td>
											<td><button type="button" loxiaType="button" style="height: 60px;font-size: 15px;"  class="confirm" onclick="saveRfidSession();">确认</button></td>
										</tr>
										<tr>
												<td width="230px;" class="label" style="font-size: 15px;">测试RFID设备 请扫OK码：</td>
												<td width="350px;" height="60px;"><input loxiaType="input" trim="true" style="height: 60px;font-size: 60px;" id="rfidOk"/></td>
												<td><button type="button" loxiaType="button" style="height: 60px;font-size: 15px;"  class="confirm" onclick="smOk();">确认</button></td>
										</tr>
										<tr>
												<td width="100px;" class="label" style="font-size: 15px;">RFID数据：</td>
												<td width="600px;" height="60px;"><input loxiaType="input" trim="true" style="height: 60px;font-size: 15px;" id="rfidValue"/></td>
												<!--  <td><b style="font-size: 23px;" id="rfidValue"></b></td>-->
										</tr>
									</table>
								</div>
					</div>

				</div>
				<div id="rarDownload" style="margin-top: 50px; margin-left: 50px;"></div>
			</div>
		</div>
		<%@include file="/common/westMenu.jsp"%>
	</div>
	<div id="bottom">
		<span>Copyright © 2011 BaoZun Inc. All rights reserved.</span>
	</div>
	<div id='msg' style='display: none; width: 610px;'>
		<textarea class="content" cols="50" rows="3" style="width: 600px; resize: none;" readonly="readonly"></textarea>
	</div>
	<div id="functionlist" style="display: none;"></div>

 	<jsp:include page="common/include-print.jsp"></jsp:include>
	<jsp:include page="common/include-weight.jsp"></jsp:include>
	<jsp:include page="common/include-pos.jsp"></jsp:include>
</body>
</html>