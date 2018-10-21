<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>		
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
	<div class="page-header"><h1 style="margin-left:20px">快递雷达线下运单导入</h1></div>
		<br>
		<input id="file1" type="file" name='file' style='display:none'>  
    	<div style='text-align: center;'>  
	    	<input id="photoCover" class="input-large" type="text" style="height:30px;">  
	   		<a class="btn" onclick="$('input[id=file1]').click();">浏览</a> 
	    	<a  id='upload' class="btn"  href='javascript:void(0)'>上传</a>
	    	<a  id='delete' class="btn btn-info"  href='javascript:del()'>删除</a>
	    	<a  id='transfer' class="btn btn-success"  href='javascript:uploaddata(0)'>校验并转换</a>
		</div>
        <div class= "title_div" id= "sc_title">
			<table class= "table table-striped" style= "table-layout: fixed;overflow-x:show;" >
		   		<thead>
			  		<tr class= "table_head_line"  >
			  			<td><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/> </td>
			  			<td class='td_cs'>序号</td>
			  			<td class='td_cs'>批次号</td>
			  			<td class='td_cs'>导入条数</td>
			  			<td class='td_cs'>失败条数</td>
			  			<td class='td_cs'>成功条数</td>
			  			<td class='td_cs'>点击下载失败原因</td>
			  			<td class='td_cs'>导入时间</td>
			  			<td class='td_cs'>操作人员</td>
			  			<td class='td_cs'>状态</td>
			  		</tr>  	
				</thead>
			</table>
		</div>
		<div class= "tree_div" ></div>
		<div style= "height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content" onscroll= "init_td('sc_title', 'sc_content')" >
			<table id= "table_content" class= "table table-hover table-striped" style= "table-layout: fixed;" >
		  		<tbody align= "center">
			  		<c:forEach items="${pageView.records}" var="power" varStatus='status'>
			  		<tr >
			  		<td><input id="ckb" name="ckb" type="checkbox" value="${power.bat_id}"></td>
			  			<td class= "td_cs" nowrap="nowrap">${status.count }</td>
			  			<td class= "td_cs" nowrap="nowrap" title='${power.bat_id}'>${power.bat_id}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.total_num} </td>
			  			<td class= "td_cs" nowrap="nowrap">${power.fail_num}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.success_num}</td>
			  			
			  			<td class= "td_cs" nowrap="nowrap">
			  			<c:if test="${empty power.wrong_path}">---</c:if>
			  			<c:if test="${not empty power.wrong_path}"><a href='/BT-LMIS/DownloadFile/${power.wrong_path}'>点击下载原因</a></c:if>
			  			</td>
			  			<td class= "td_cs" nowrap="nowrap"><fmt:formatDate value="${power.create_time}"  type="both" pattern=" yyyy-MM-dd HH:mm:ss"/> </td>
			  			<td class= "td_cs" nowrap="nowrap">${power.username}</td>
			  			<td class= "td_cs">
			  			<c:if test='${power.flag==0}'>已上传</c:if>
			  			<c:if test='${power.flag==1}'>已转换</c:if>
			  			<c:if test='${power.flag==2}'>正在转换</c:if>
			  			</td>
			  		</tr>
		  		</c:forEach>
		  		</tbody>
			</table>
		</div>
      
      
		<!-- 分页添加 -->
     <div style="margin-right: 30px;margin-top:20px;">${pageView.pageView}</div>	
    
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
				url: '/BT-LMIS//control/radar/expressinfoMasterInputlistController/uploadWaybill.do', //用于文件上传的服务器端请求地址
	            secureuri: false, //是否需要安全协议，一般设置为false
				fileElementId: 'file1', //文件上传域的ID
				dataType: 'json', //返回值类型 一般设置为json
					//服务器成功响应处理函数
					success: function (data, status){
						cancelLoadingStyle();
						 if(data.code==0){
							alert("上传失败！");
							 //openDiv('/BT-LMIS//control/lmis/originDesitinationParamlistController/upload_page.do?msg=&path1='+data.path+'&path2='+data.pathDone);
		                }else if(data.code==1){
		                	alert("上传成功！");
		                	openDiv('/BT-LMIS//control/radar/expressinfoMasterController/waybillUploadPage.do');
		               		//openDiv('/BT-LMIS//control/lmis/originDesitinationParamlistController/upload_page.do?msg=恭喜！您刚刚的数据已经上传成功了！');
		             	}else{
		             		alert('系统错误请联系管理员！');
							//openDiv('/BT-LMIS//control/lmis/originDesitinationParamlistController/upload_page.do?msg=系统错误请联系管理员！');
		                } 
					},error: function (data, status, e){
						//服务器响应失败处理函数
						cancelLoadingStyle();
						alert('系统错误请联系管理员！');
		           	}
				})
		        return false;
			}
		
		function uploaddata(){
			if ($("input[name='ckb']:checked").length > 1) {
			alert("只能选择一个批次进行校验和导入");
		}else if($("input[name='ckb']:checked").length == 0){
			alert("请选择一个批次进行操作！");
		}else if($("input[name='ckb']:checked").length ==1){
			if($("input[name='ckb']:checked").parent().parent().children().eq(9).text().replace(/(^\s*)|(\s*$)/g, "")=='已转换'||$("input[name='ckb']:checked").parent().parent().children().eq(9).text().replace(/(^\s*)|(\s*$)/g, "")=='正在转换'){
				alert("此批号已经转换过后者正在转换，不能重复转换！");
				return;
			};
			loadingStyle();
			$.post("/BT-LMIS/control/radar/expressinfoMasterInputlistController/uploadInWaybill.do?bat_id="+$("input[name='ckb']:checked").val(),function(data){
				if(data.code==0){
				alert("请点击下载日志");
				}else if(data.code==1){
					alert("导入成功！");
				}else if(data.code==2){
				    alert("您上传的批次已经达到30个，为了减少服务器压力建议您先删除历史批次之后再进行校验转换，谢谢！");	
				    cancelLoadingStyle();
				    return;
				}
				else{
					alert("系统错误,请联系管理员！");
					cancelLoadingStyle();
					return;
				}
				openDiv('/BT-LMIS//control/radar/expressinfoMasterController/waybillUploadPage.do');
				cancelLoadingStyle();
			})
		}
		}
		
		
		function del(){
			if ($("input[name='ckb']:checked").length ==0) {
				alert("请选择一个批次删除");
			}else{
				if(!confirm("您确定要删除勾选的记录"))return;
				var uuid='';
			var obj=	$("input[name='ckb']:checked");
			$("input[name='ckb']:checked").each(function(index,item){
				uuid=uuid+item.value+";";
			})
				loadingStyle();
				$.post("/BT-LMIS/control/radar/expressinfoMasterInputlistController/delete.do?bat_id="+uuid,function(data){
					if(data.code==0){
					alert("请点击下载日志");
					}else if(data.code==1){
						alert("操作成功！");
						openDiv('/BT-LMIS//control/radar/expressinfoMasterController/waybillUploadPage.do');
					}else{
						alert(data);
						alert("系统错误,请联系管理员！");
					}
					cancelLoadingStyle();
				})	
			}
			
		}
		
		
	function jumpToPage(page){
		loadingStyle();
		openDiv('/BT-LMIS//control/radar/expressinfoMasterController/waybillUploadPage.do?page='+page+"&pageSize="+$("#pageSize").val());	
		cancelLoadingStyle();
	}
		
	function pageJump() {
		loadingStyle();
	      openDiv('${root}//control/radar/expressinfoMasterController/waybillUploadPage.do?startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
	      cancelLoadingStyle(); 	
	}
		
	</script>

</html>