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
				aline:center;
			}
			table.gridtable td {
				border-width: 1px;
				padding: 1px;
				border-style: solid;
				border-color: #666666;
				background-color: #ffffff;
				/* width:50%; */
			}
			.AutoNewline {
				/* 必须 */
				Word-break: break-all;
			}
		</style>
		
		<script type="text/javascript">
			var root = '${root}';
			function str(log,process_remark,flag,accessory_list){
				var tr = "<tr>";
				var accessory_name = "";
				if(flag==true){
					$.each(accessory_list, function(index, content){
						if (index > 0) {
							accessory_name = accessory_name+"_"+content.filename;
						}
					});
				} 
				if(process_remark == null || process_remark==''){
					tr += "<td class='AutoNewline ' width='55%'><h5><font id='log' >"+log+"</font> </h5><a class='red' onclick=\"OneKeyDown(\'"+accessory_name+"\');\">附件下载</a></td>";
				}else{
					tr += "<td class='AutoNewline' width='55%'><h5><font id='log' >"+log+"</font> <br><br>处理意见:<font id='process_remark' >"+process_remark+"</font> </h5><a class='red' onclick=\"OneKeyDown(\'"+accessory_name+"\');\">附件下载</a></td>";
				}
				//是否存在附件
				if(flag==true){
					$.each(accessory_list, function(indexs, contents){
						if (indexs > 0) {
							var z = indexs%2;
							var filename = contents.filename.split('#')[0];
							var suffix = contents.suffix.toLowerCase();
							if(z!=0){
								if(suffix=='png' || suffix=='gif' || suffix=='jpeg' || suffix=='jpg' || suffix=='png' || suffix=='bmp' ){
									tr += "<td style='display: block;margin:0px;padding:1%;width:49.5%;text-align:center;float:left;'><a href='${func:getNginxDownloadPreFix() }"+filename+"' target='_blank'><img alt='' src='${func:getNginxDownloadPreFix() }"+filename+"' style='max-width:80%;height:100px;'></a></td>";
								}else{
									tr += "<td style='display: block;margin:0px;padding:1%;width:49.5%;text-align:center;float:left;'><a href='${func:getNginxDownloadPreFix() }"+filename+"' target='_blank'><img alt='' src='${func:getNginxDownloadPreFix() }rar.png' style='max-width:80%;height:100px;'></a></td>";
								}
							}else{
								if(suffix=='png' || suffix=='gif' || suffix=='jpeg' || suffix=='jpg' || suffix=='png' || suffix=='bmp' ){
									tr += "<td style='display: block;margin:0px;padding:1%;width:49.5%;float:right;text-align:center;'><a href='${func:getNginxDownloadPreFix() }"+filename+"' target='_blank'><img alt='' src='${func:getNginxDownloadPreFix() }"+filename+"' style='max-width:80%;height:100px;'></a></td></td>";
								}else{
									tr += "<td style='display: block;margin:0px;padding:1%;width:49.5%;float:right;text-align:center;'><a href='${func:getNginxDownloadPreFix() }"+filename+"' target='_blank'><img alt='' src='${func:getNginxDownloadPreFix() }rar.png' style='max-width:80%;height:100px;'></a></td></td>";
								}
							}
						}
					});
					
				}else{
					//alert(content.log+"  "+content.process_remark+" 无附件!");
				}
				tr += "</td></tr>"
			 	var $table = $("#tltable");
		        $table.append(tr);
			}
			$(document).ready(function(){ 
				$.ajax({
					url : root + 'control/workOrderManagementController/loadingEnclosure.do',
					type : 'post',
					data : {woId:'${wo_display.wo_no}'},
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
			}); 
		</script>
	</head>
	<!-- onload="init();" -->
	<body style="margin:0px" >
		<table id="tltable" name="tltable" class="gridtable" >
			<tr><th style="text-align: center;">处理记录</th><th style="text-align: center;">附件</th></tr>
		</table>
	</body>
</html>
