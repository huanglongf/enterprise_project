<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="func" uri="/WEB-INF/tld/func.tld" %>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="../lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='<%=basePath %>assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script type="text/javascript">
			window.jQuery || document.write("<script src='<%=basePath %>assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
		</script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/ajaxfileupload.js"></script>
		<link type= "text/css" href= "<%=basePath %>shCircleLoader/css/jquery.shCircleLoader.css" rel="stylesheet" media="all" />
		<link type= "text/css" href= "<%=basePath %>css/loadingStyle.css" rel= "stylesheet" media="all" />
		<link rel="stylesheet" href="<%=basePath %>validator/css/style.css" type="text/css" media="all" />
		<link href="<%=basePath %>validator/css/demo.css" type="text/css" rel="stylesheet" />
		<link href="<%=basePath %>/css/table.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="<%=basePath %>lmis/trans_moudle/js/trans.js"></script>	
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>	
		<script type="text/javascript" src="<%=basePath %>lmis/address_param/address_param.js"></script>	
		<script type="text/javascript" src="<%=basePath %>validator/js/Validform_v5.3.2_min.js"></script>
	<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader.js" ></script>
	<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js" ></script>
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
		<style type="text/css">
			 .text-style{
				 font-size: 17px;
				 color: blue;
			 }
			 .iput_style{
				 width: 25%;
				 height: 28px;
			 }
		</style>		
	</head>
	
	<body>
		<br>
		<input id="file1" type="file" name='file' style='display:none'>  
    	<div style='text-align: center;'>  
	    	<input id="photoCover" class="input-large" type="text" style="height:30px;">  
	   		<a class="btn" onclick="$('input[id=file1]').click();">浏览</a> 
	    	<a  id='upload' class="btn"  href='javascript:void(0)'>上传</a>
			<a  id='importTemplet' class="btn"  href='${func:getNginxDownloadPreFix() }express_raw_data/templete/全国报价导入最新模板.xlsx'>下载模板</a>
		</div>
     	<div id='dowmdiv' style='width:50%;margin:150px auto'>${msg}</div>
	    <c:if test="${not empty path1}">
        	<div  style='width:50%;margin:150px auto'><a href='/BT-LMIS/upload/${path1}'><strong>您的数据格式可能存在问题，点击下载报错日志并修改再上传。谢谢！</strong></a>
        		<br/>
	         	<c:if test="${not empty path2}">
	         	</c:if>
        		<!--  <a href='/BT-LMIS/upload/${path2}'><strong>点击此处可以下载去重后的文件</strong></a> -->
        	</div>
        </c:if>
        <c:if test="${not empty path2}">
       		<div  style='display:none;width:50%;margin:150px auto'>
     			<a href='/BT-LMIS/upload/${path2}'><strong>点击此处可以下载去重后的文件</strong></a>
     		</div>
       </c:if>  
    
	</body>
	
	<script type="text/javascript">  
		$(function () {
		    $("#upload").click(function () {
		    	$("#dowmdiv").html("<span></span>");
		    	loadingStyle();
		        ajaxFileUpload();
		    });
	    
	    	$('input[id=file1]').change(function() { 
		        $('#photoCover').val($(this).val());
		        $("#dowmdiv").html("<span></span>");
	        }); 
		})
		function ajaxFileUpload() {
			$.ajaxFileUpload({
				url: '/BT-LMIS/control/lmis/originDesitinationParamlistController/upload.do', //用于文件上传的服务器端请求地址
	            secureuri: false, //是否需要安全协议，一般设置为false
				fileElementId: 'file1', //文件上传域的ID
				dataType: 'json', //返回值类型 一般设置为json
					//服务器成功响应处理函数
					success: function (data, status){
						cancelLoadingStyle();
						if(data.code==0){
							//$("#dowmdiv").html("<a href='/BT-LMIS/upload/"+data.path+"'><strong>您的数据格式可能错在问题，点击下载报错日志并修改再上传。谢谢！</strong></a>");
							openDiv('/BT-LMIS//control/lmis/originDesitinationParamlistController/upload_page.do?msg=&path1='+data.path+'&path2='+data.pathDone);
		                }else if(data.code==1){
		               		openDiv('/BT-LMIS//control/lmis/originDesitinationParamlistController/upload_page.do?msg=恭喜！您刚刚的数据已经上传成功了！');
		             	}else{
							openDiv('/BT-LMIS//control/lmis/originDesitinationParamlistController/upload_page.do?msg=系统错误请联系管理员！');
		                }
					},error: function (data, status, e){
						//服务器响应失败处理函数
						cancelLoadingStyle();
						openDiv('/BT-LMIS//control/lmis/originDesitinationParamlistController/upload_page.do?msg=系统错误请联系管理员！');
		           	}
				})
		        return false;
			}
	</script>
</html>