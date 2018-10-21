<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
		<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions" %>
		<%@ taglib prefix="func" uri="/WEB-INF/tld/func.tld" %>
		<% String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"; %>
		<c:set var="root" value="<%=basePath %>"></c:set>
		
		<style>
			table.gridtable {
				font-family: verdana, arial, sans-serif;
				font-size: 11px;
				color: #333333;
				border-width: 1px;
				border-color: #666666;
				border-collapse: collapse;
				width: 100%;
				height: 20px;
			}
			table.gridtable th {
				border-width: 1px;
				padding: 8px;
				border-style: solid;
				border-color: #666666;
				background-color: #87CEFA;
			}
			table.gridtables th {
				border-width: 1px;
				padding: 8px;
				border-style: solid;
				border-color: #666666;
				background-color: #EEB422;
			}
			table.gridtable td {
				border-width: 1px;
				padding: 8px;
				border-style: solid;
				border-color: #666666;
				background-color: #ffffff;
			}
			.AutoNewline {
				/* 必须 */
				Word-break: break-all;
			}
		</style>
		
		<!-- jquery -->
		<script type="text/javascript" src="<%=basePath %>jquery/jquery-2.0.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>bootstrap-table/bootstrap.min.js"></script>
		<script type="text/javascript">
			var root = '${root}';
			function str(log,process_remark,flag,accessory_list){
				var aStr = '';
				 aStr = log;
				//中文冒号截取
				var arr=log.split("：");
				if(arr.length>1){
					var woNos = arr[1];
					$("#woNO_input").val(woNos);
					//alert(woNos);
					var arr2 = woNos.split(",");
					if(arr2.length>1){
						log = arr[0]+"：";
						//aStr = "<span class='red' onclick='toShowAssociation(this);'>"+woNos+"</span>"
						//aStr = "<span class='red' onclick='toShowAssociation(this);' value='woNos'>"+arr2[0]+"等"+arr2.length+"个工单</span>"
					}
					aStr=arr[0]+":<br/>";
					for(i=0;i<arr2.length;i++){
						aStr = aStr+arr2[i]+"<br/>";
					}
				} 
				var accessory_name = "";
				if(flag==true){
					$.each(accessory_list, function(index, content){
						if (index > 0) {
							accessory_name = accessory_name+"_"+content.filename;
						}
					});
				} 
				var tr = "<tr>";
				if(process_remark == null || process_remark==''){
					tr += "<td class='AutoNewline'><h3><font>"+aStr+"</font> </h3><br>";
				}else{
					tr += "<td class='AutoNewline'><h3><font>"+aStr+"</font><br/> 处理意见:<font>"+process_remark+"</font> </h3><br>";
				}
				//是否存在附件
				if(flag==true){
					tr += "<table class='gridtables' style='width:100%'><tr><th style='display: block;width:99%;padding:0px;padding-top:8px;padding-bottom:8px;margin-left:auto;margin-right:auto;' colspan='2' onclick=\"OneKeyDown(\'"+accessory_name+"\');\">一键下载</th></tr>";
					//console.dir(accessory_list);
					$.each(accessory_list, function(indexs, contents){
						if (indexs > 0) {
							var z = indexs%2;
							var serverName = contents.filename.split('#')[0];
							var fileName = contents.filename.split('#')[1]+contents.filename.split('#')[2];
							var suffix = contents.suffix.toLowerCase();
							console.dir(contents);
							if(z!=0){
								if(suffix=='png' || suffix=='gif' || suffix=='jpeg' || suffix=='jpg' || suffix=='png' || suffix=='bmp' ){
									tr += "<tr style='display: block;width:99%;padding:0px;margin-left:auto;margin-right:auto;'><td style='display: block;margin:0px;padding:1%;width:47.5%;text-align:center;float:left;'><a onclick=\"accessorySSDownload(\'"+serverName+"\',\'"+fileName+"\');\" target='_blank'>"+fileName+"</a></td></td>";
								}else{
									tr += "<tr style='display: block;width:99%;padding:0px;margin-left:auto;margin-right:auto;'><td style='display: block;margin:0px;padding:1%;width:47.5%;text-align:center;float:left;'><a onclick=\"accessorySSDownload(\'"+serverName+"\',\'"+fileName+"\');\" target='_blank'>"+fileName+"</a></td></td>";
								}
							}else{
								if(suffix=='png' || suffix=='gif' || suffix=='jpeg' || suffix=='jpg' || suffix=='png' || suffix=='bmp' ){
									tr += "<td style='display: block;margin:0px;padding:1%;width:47.5%;float:right;text-align:center;'><a onclick=\"accessorySSDownload(\'"+serverName+"\',\'"+fileName+"\');\" target='_blank'>"+fileName+"</a></td></td></tr>";
								}else{
									tr += "<td style='display: block;margin:0px;padding:1%;width:47.5%;float:right;text-align:center;'><a onclick=\"accessorySSDownload(\'"+serverName+"\',\'"+fileName+"\');\" target='_blank'>"+fileName+"</a></td></td></tr>";
								}
							}
						}
					});
				}else{
					//alert(content.log+"  "+content.process_remark+" 无附件!");
				}
				tr += "</td></tr>"
			 	var $table = $("#tltable2");
		        $table.append(tr);
			}
			function init(){
				$.ajax({
					url : root + 'control/workOrderPlatformStoreController/loadingEnclosure.do',
					type : 'post',
					data : {woId:'${woId}'},
					dataType : 'json',
					success : function(resultData){
						if(resultData.code=='200'){
							//遍历回复记录
							$.each(resultData.enclosure, function(index, content){
								str(content.log, content.process_remark, content.flag, content.accessory_list);
							});
						}
					}
				});
			}
			
			function OneKeyDown(accessory_list){
				if(!accessory_list){
					alert("无附件！");
					return;
				}
				var str = encodeURI(accessory_list);
				var str2 = str.replace(/#/g, '1a1b1c1d3e');
				window.location.href="/BT-LMIS/control/workOrderManagementController/OneKeyDownload.do?"
						+"woId="
						+"&accessory_list="+str2;
			}
			function accessorySSDownload(serverName,originName){
				window.location.href="/BT-LMIS/control/workOrderPlatformStoreController/accessoryDownload.do?"
					+"serverName="+serverName
					+"&originName="+originName;
			}
		</script>
	</head>
	<body style="margin:0px" onload="init();">
		<table id="tltable2" name="tltable2" class="gridtable" >
			<tr><th>处理记录</th></tr>
		</table>
	</body>
</html>
