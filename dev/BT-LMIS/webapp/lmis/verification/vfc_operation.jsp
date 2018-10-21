<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<title>LMIS</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/lmis/yuriy.jsp"%>
<link type="text/css" href="<%=basePath %>css/table.css"
	rel="stylesheet" />
<link type="text/css"
	href="<%=basePath %>daterangepicker/font-awesome.min.css"
	rel="stylesheet" />
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>daterangepicker/font-awesome.min.css" />
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
<!-- 日期 -->
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>assets/css/datepicker.css" />
<link type= "text/css" href= "<%=basePath %>css/loadingStyle.css" rel= "stylesheet" media="all" />
<!-- 时间 -->
<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>My97DatePicker/skin/WdatePicker.css" />	
<link type= "text/css" href= "<%=basePath %>shCircleLoader/css/jquery.shCircleLoader.css" rel="stylesheet" media="all" />
<link rel="stylesheet" href="<%=basePath %>/css/bootstrap/bootstrap-multiselect.css"/>
<!-- <link rel="stylesheet" href="<%=basePath %>/css/bootstrap/bootstrap.min.css">  -->
<style type="text/css">
label {
	margin-right: 15px;
	font-size: 14px;
}
</style>
<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
<script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
<script type="text/javascript" src="<%=basePath %>assets/js/date-time/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="<%=basePath %>assets/js/date-time/locales/bootstrap-datepicker.zh-CN.js"></script>
<!-- 时间 -->
<script type="text/javascript" src="<%=basePath %>My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
<script type="text/javascript" src="<%=basePath %>js/ajaxfileupload.js"></script>
<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader.js" ></script>
<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js" ></script>
<script type= "text/javascript" src="<%=basePath %>/js/bootstrap-multiselect.js"></script>
<script>
var materid = ${master.id};
$(function () {
	 $('#example-getting-started').multiselect({
		 includeSelectAllOption: true,
     	enableCaseInsensitiveFiltering: true
	 });
	 $('#example-getting-started1').multiselect({
		 includeSelectAllOption: true,
     	enableCaseInsensitiveFiltering: true
	 });
	$(".data-range").daterangepicker(null, function(start, end, label) {});
    $("#upload").click(function () {
    	if(!confirm("确认导入？")) {
    		return;
    	}
	        ajaxFileUpload();
    });
    
    $("#upload1").click(function () {
    	if(!confirm("确认导入？")) {
    		return;
    	}
	        ajaxFileUpload1();
    });
    
    
	$('input[id=file1]').change(function() { 
        $('#photoCover').val($(this).val());
    }); 
	$('input[id=file2]').change(function() { 
        $('#photoCover2').val($(this).val());
    }); 
	
	
    
	
	
	$("#transfer").click(function(){
		if($("input[name='ckb111']:checked").length==1){
	  		 var result=  	confirm('是否要转换？'); 
	  		if(result){
				var id=$("input[name='ckb111']:checked");
				var idsStr="";
				$.each(id,function(index,item){
					idsStr=this.value;
				});     
				$.post('${root}/control/lmis/expressbillBatchmasterController/transfer.do?id='+idsStr+"&master_id=${master.id}",
		        		function(data){
		        	            if(data.code==1){
		        	            	alert("操作成功,已经开始转换");
		        	            }else if(data.code==0){
		        	            	alert("操作失败！");
		        	             }
		        	            $(window).scrollTop(0);
	        					jumpbatch();
		                        }
		  		      ); 
	  		}	
		}else{
			alert("请选择一行!");
		}  
	});
	$("#deletevfc").click(function(){
		if($("input[name='ckb111']:checked").length==1){
	  		 var result=  	confirm('是否要删除？'); 
	  		if(result){
				var id=$("input[name='ckb111']:checked");
				var idsStr="";
				$.each(id,function(index,item){
					idsStr=this.value;
				});
				$(window).scrollTop(0);
				$.post('${root}/control/lmis/expressbillBatchmasterController/deletevfc.do?ids='+idsStr,
		        		function(data){
		        	            if(data.data==0){
		        	            	alert("操作成功！");
		        	            }else if(data.data==2){
		        	            	alert("操作失败！");
		        	             }else if(data.data==1){
		        	            	alert("操作进行中，不能删除！");
		        	             }
		        	            jumpbatch();}
		  		      ); 
	  		}	
		}else{
			alert("请选择一行!");
		}  
	});
});

function deleteAccount(){
	if($("input[name='ckb1234']:checked").length==0){
		alert("请选择一项");
	}else{
		var result=  	confirm('是否要删除？'); 
		loadingStyle();
		
  		if(result){
			var id=$("input[name='ckb1234']:checked");
			var idsStr="";
			$.each(id,function(index,item){
				idsStr=this.value+";";
			});
		
		 $.post("${root}//control/lmis/expressbillMasterhxController/deleteClose.do?ids="+idsStr,function(data){
				cancelLoadingStyle();
			 if(data.code==1){
				alert("操作成功");
				gettotal();
			}else{
				
				alert("操作失败");
			}
		}); 
		}
		
	}
	
}



function showMsg(v){
	 var code = event.keyCode;
	if(code == 13){
		var $v = $(v.parentNode.parentNode);
		var ff=$v.children(".td_cs");
		var param='';
		$.each(ff,function(index,item){
			if(item.firstChild.value!='')
			param=param+item.firstChild.name+"="+item.firstChild.value+'&';
		});
		
		param=param+"page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val();		 if($('#is_verification').val()==0)
		
		DiffBilldeatils(param);
		if($('#is_verification').val()==1)
		DiffBilldeatils_Is_verification_page(param);
		
	} 
	}


function ajaxFileUpload() {
			$.ajaxFileUpload({
				url: '/BT-LMIS/control/lmis/expressbillBatchmasterController/input_excel.do?master_id=${master.id}', //用于文件上传的服务器端请求地址
	            secureuri: false, //是否需要安全协议，一般设置为false
				fileElementId: 'file1', //文件上传域的ID
				dataType: 'json', //返回值类型 一般设置为json
				//服务器成功响应处理函数
				success: function (data, status){
					
					if(data.code==1){
						jumpbatch();
					}else if(data.code==0){
						alert(data.msg);
						
					}
					
					
				},error: function (data, status, e){
					//服务器响应失败处理函数
	           	}
					
			})
	        return false;
			
		}

   function toDiff(){
	   if($("input[name='ckb111']:checked").length==1){
	   var id=$("input[name='ckb111']:checked").val();
		var idsStr="";   
		loadingStyle();
	  	$.post('${root}/control/lmis/expressbillDetailController/toDiff.do?id='+id,function(data){
	  		cancelLoadingStyle();
	  		if(data.code==1){alert('操作成功')}else{
           	 alert('操作失败'); 
             }
	  	})
	   
	   
	   }else{
	  		
	  		alert('请选择一行');
	  	}
   }

  
 function downloadDetails(value){

		if($("input[name='ckb']:checked").length==0){
			loadingStyle();
	   $.post('${root}/control/lmis/diffBilldeatilsController/downloadDetails.do?'+$('#diff').serialize()+'&is_verification='+value+"&month_accountq="+$('#example-getting-started').val()+'&master_id='+materid,function(data){
		   cancelLoadingStyle();
		   if(data.code==1){
			 alert("操作成功！！");
			 window.open(root + "/DownloadFile/hxupload/"+data.url);
		 }  else{
			 alert("操作失败");
		 }
		    
		   
	   });
		}else{
			var ids=$("input[name='ckb']:checked");
			var idsStr='';
			$.each(ids,function(index,item){
				idsStr=idsStr+this.value+';';
			});
			loadingStyle();
			$.post('${root}/control/lmis/diffBilldeatilsController/downloadDetailsIds.do?ids='+idsStr,function(data){
				cancelLoadingStyle();
				if(data.code==1){
					 alert("操作成功！！");
					 window.open(root + "/DownloadFile/hxupload/"+data.url);
				 }  else{
					 alert("操作失败");
				 }
			   });	
		}

   }
   

 
function ajaxFileUpload1() {
	$(window).scrollTop(0);
	loadingStyle();
	$.ajaxFileUpload({
		url: '/BT-LMIS/control/lmis/expressbillBatchmasterController/input_result.do?master_id='+materid, //用于文件上传的服务器端请求地址
        secureuri: false, //是否需要安全协议，一般设置为false
		fileElementId: 'file2', //文件上传域的ID
		dataType: 'json', //返回值类型 一般设置为json
		//服务器成功响应处理函数
		success: function (data, status){
			cancelLoadingStyle();
			if(data.code==1){
				alert('操作成功');
				jumpbatch();
			}else if(data.code==0){
				alert(data.msg);
			}
			
			
		},error: function (data, status, e){
			//服务器响应失败处理函数
			cancelLoadingStyle();
			alert('操作失败');
			
       	}
			
	})
    return false;
	
}

        function jumpbatch(){
        	/*  $.ajax({
 				type: "POST",
 	           	url:'${root}/control/lmis/expressbillBatchmasterController/pageQuery.do?master_id=${master.id}',
 	           	dataType: "text",
 	           	data:'',
 	    		cache:false,
 	    		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
 	           	success: function (data){
 	              $("#page_view").html(data);
 	           //   $("#load_load").css("display","none");
 	           	}
 		  	});  */
 		  	
        	openDiv('${root}control/lmis/expressbillMasterController/tablist.do?id='+materid);
        }
        
        function DiffBilldeatils(param){
        	$(window).scrollTop(0);
        	loadingStyle();	
        	//alert(decodeURIComponent('${root}/control/lmis/diffBilldeatilsController/page.do?master_id='+materid+'&is_verification=0&'+param));
         	 $.ajax({
 				type: "POST",
 	           	url:decodeURIComponent('${root}/control/lmis/diffBilldeatilsController/page.do?master_id='+materid+'&is_verification=0&'+param+"&month_accountq="+$('#example-getting-started').val()),
 	           	dataType: "text",
 	           	data:'',
 	    		cache:false,
 	    		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
 	           	success: function (data){
 	           	$("#diff_billdeatils_is_verification_page").html("");
 	              $("#diff_billdeatils_page").html(data);
 	             cancelLoadingStyle();
 	           	}
 		  	});  	
        	
        }
        function gettotal(param){
        	$(window).scrollTop(0);
        	loadingStyle();
        	 $.ajax({
 				type: "POST",
 	           	url:decodeURIComponent('${root}/control/lmis/expressbillMasterhxController/goPage.do?master_id='+materid),
 	           	dataType: "text",
 	           	data:'',
 	    		cache:false,
 	    		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
 	           	success: function (data){
 	              $("#diff_total_page").html(data);
 	             cancelLoadingStyle();
 	           	}
 		  	}); 	
        	
        }
        function DiffBilldeatils_Is_verification_page(param){
        	$(window).scrollTop(0);
        	loadingStyle();	
        	 $.ajax({
 				type: "POST",
 	           	url:'${root}/control/lmis/diffBilldeatilsController/page.do?'+param+'&master_id='+materid+"&is_verification=1&month_accountq="+$('#example-getting-started1').val(),
 	           	dataType: "text",
 	           	data:'',
 	    		cache:false,
 	    		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
 	           	success: function (data){
 	           	cancelLoadingStyle();
 	           	$("#diff_billdeatils_page").html("");
 	              $("#diff_billdeatils_is_verification_page").html(data);
 	           //   $("#load_load").css("display","none");
 	           	}
 		  	}); 	
        }
        
        
        function exportwmd(){
	  		 var result=  	confirm('是否导出！'); 
	  		var param='';
			 param=$('#diff').serialize();
				
					if(result){
					 loadingStyle();	
					
						var ids=$("input[name='ckb']:checked");
						var idsStr='';
						$.each(ids,function(index,item){
							idsStr=idsStr+this.value+';';
						});
					var materid = ${master.id};
					$.ajax({
						type: "POST",  
						url: '${root}/control/lmis/diffBilldeatilsController/uploade.do?master_id='+materid+'&ids='+idsStr+'&'+param,
						//json格式接收数据  
						dataType: "json",  
						success: function (jsonStr) {
							cancelLoadingStyle();
						    if(jsonStr.out_result==1){
							    window.open(root +jsonStr.path);
							 // alert(root + jsonStr.path);
						    }
						}  
					}); 
	  		
				}
			
		
	}
        
        
        
        
        
     function uploade_diff_error(bat_id){
			var result=  	confirm('是否导出错误日志！'); 
				if(result){
					loadingStyle();
					$.ajax({
						type: "POST",  
						url: '${root}/control/lmis/expressbillDetailController/uploade_diff_error.do?bat_id='+bat_id,
						//json格式接收数据  
						dataType: "json",  
						success: function (jsonStr) {
							cancelLoadingStyle();
						    if(jsonStr.out_result==1){
							    window.open(root + jsonStr.path);
							 // alert(root + jsonStr.path);
						    }
						} 
					}); 
	  		
			}
	}
     function uploade_expressbillmasterhx(id){
    	master_id = $("#master_id").val();
			var result=  	confirm('是否导出账单明细！'); 
				if(result){
					loadingStyle();
					$.ajax({
						type: "POST",  
						url: '${root}/control/lmis/expressbillMasterhxController/uploade_expressbillmasterhx.do?id='+id+'&master_id='+master_id,
						//json格式接收数据  
						dataType: "json",  
						success: function (jsonStr) {
							cancelLoadingStyle();
						    if(jsonStr.out_result==1){
							    window.open(root + jsonStr.path);
							 // alert(root + jsonStr.path);
						    }
						}  
					}); 
	  		
			}
	}
			

</script>

</head>
<body>
<div class="page-header">
		<h1>账单操作</h1>
	</div>	
	<ul id="myTab" class="nav nav-tabs" style='height: 44px'>
		<li class="active"><a href="#inputin" data-toggle="tab">供应商账单导入</a>
		</li>
		<li><a href="#nov" data-toggle="tab" onclick="DiffBilldeatils()">未核销</a></li>
		<li><a href="#noed" data-toggle="tab" onclick="DiffBilldeatils_Is_verification_page('')">已核销</a></li>
		<li><a href="#warning1" onclick="gettotal()" data-toggle="tab">账单汇总</a></li>
	</ul>
	<div id="myTabContent" class="tab-content">
		<div class="tab-pane fade in active" id="inputin">
			<div><c:if test="${master.status==1}">
				<tr>
					<td style="width: 260px;"><input id="file1" type="file"
						name='file' style='display: none'> <input id="photoCover"
						class="input-large" type="text"
						style="height: 20px; width: 170px;"> <a
						class="btn btn-xs btn-grey"
						onclick="$('input[id=file1]').click();">浏览</a></td>
					<td><a id='upload' class="btn btn-xs btn-grey"
						href='javascript:void(0)'>导入</a></td>
					<td><a  class="btn btn-xs btn-grey"
						href='/BT-LMIS/DownloadFile/ddhxmodal.xlsx'>模版下载</a></td>
				</tr>
				<tr>
					<td><a id='transfer' class="btn btn-xs btn-grey"
						href='javascript:void(0)'>确认转换</a></td>
					<td><a id='deletevfc' class="btn btn-xs btn-grey"
						href='javascript:void(0)'>删除</a></td>
					<td><a id='toDiff' class="btn btn-xs btn-grey"
						href='javascript:toDiff()'>匹配</a></td>	
						<td><a id='toDiff' class="btn btn-xs btn-grey"
						href='javascript:jumpbatch()'>刷新</a></td>
				</tr></c:if>
			</div>
			<div id='page_view'
				style="height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px; margin-top: 5px">
				<table class="table table-striped" overflow-x：show>
					<thead align="center">
						<tr class='table_head_line'>
							<td style='width: 50px'>
						<input type="checkbox" id="checkAll" onclick="inverseCkb('ckb111')"/>
					</td>
							<td class="td_cs" >序号</td>
							<td class="td_cs" >导入时间</td>
							<td class="td_cs" >导入批次号</td>
							<td class="td_cs" >总数量</td>
							<td class="td_cs" >成功数量</td>
							<td class="td_cs" >失败数量</td>
							<td class="td_cs" >状态</td>
							<td class="td_cs" >错误原因下载</td>
						</tr>
					</thead>
					<tbody align="center">
                <c:forEach items="${pageView.records}" var="power"
					varStatus='status'>
					<tr>
						<td class="td_ch"><input id="ckb111" name="ckb111" type="checkbox"
							value="${power.id}"></td>
						<td class="td_cs">${status.count}</td>
						<td class="td_cs">
						<fmt:formatDate
								value="${power.create_time}" type="both"
								pattern=" yyyy-MM-dd HH:mm:ss" /></td>
						<td class="td_cs">${power.batch_id}</td>
						<td class="td_cs">${power.total_num}</td>
						<td class="td_cs">${power.success_num}</td>
						<td class="td_cs">${power.fail_num }</td>
						<td class="td_cs">
						<c:if test="${power.status==0}">
						 未上传
						</c:if>
						<c:if test="${power.status==1}">
						 已上传
						</c:if>
						<c:if test="${power.status==4}">
						 转换失败
						</c:if>
						<c:if test="${power.status==3}">
						 已转换
						</c:if>
						<c:if test="${power.status==8}">
						 处理中
						</c:if>
						<c:if test="${power.status==666}">
						 已匹配
						</c:if>
						<c:if test="${power.status==7}">
						 匹配失败
						</c:if>
				     </td>
				     <td>
				     <c:if test="${power.fail_num != 0 }">
				     <a  class="btn btn-xs btn-grey"
						 onclick="uploade_diff_error('${power.batch_id}')">下载</a>
						 
					</c:if>	 
						 </td>
					</tr>
				</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="tab-pane fade" id="nov">
			<div class="tab-pane fade in active">
				<c:if test="${master.status==1}"><tr>
				    <td style="width: 260px;"><input id="file2" type="file"
						name='file' style='display: none'> <input id="photoCover2"
						class="input-large" type="text"
						style="height: 20px; width: 170px;"> <a
						class="btn btn-xs btn-grey"
						onclick="$('input[id=file2]').click();">浏览</a></td>
					<td><a id='upload1' class="btn btn-xs btn-grey"
						href='javascript:void(0)'>导入</a></td>
					<!-- <td><a i class="btn btn-xs btn-grey"
						href='javascript:void(0)'>显示字段</a></td> -->
					<td><a  class="btn btn-xs btn-grey"
						href='javascript:downloadDetails(0)'>导出明细</a></td>
				</tr>
				<tr>
					<td><a  class="btn btn-xs btn-grey"
						onclick='hx()'>核销</a></td>
					<!-- <td><a id='upload' class="btn btn-xs btn-grey"
						href='javascript:void(0)'>重新匹配</a></td> -->
				</tr></c:if>
				<c:if test="${master.status!=1}">
				<tr><td><a  class="btn btn-xs btn-grey"
						href='javascript:downloadDetails(0)'>导出明细</a></td>
				</tr>
				</c:if>
				<!-- <tr><td><a  class="btn btn-xs btn-grey"
						href='javascript:DiffBilldeatils("")'>刷新</a></td>
				</tr> -->
			
				<tr><td>请选择月结账号 :</td><td><select id="example-getting-started" multiple="multiple" >
				  <c:forEach items='${monthAccount}' var="power">
				  <option value='${power.code}'>${power.code}</option>
				  </c:forEach>
                        </select>     
                        </td></tr> 
                              
			</div>
			<div id="diff_billdeatils_page">
			<div 
				style="height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px; margin-top: 5px">
				<table class="table table-striped" overflow-x：show>
					<thead align="center">
						<tr class='table_head_line'>
						<td style='width: 50px'>
						<input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/></td>
							<td class="td_cs" style="width: 100px">序号</td>
							<td class="td_cs" style="width: 200px">核销周期</td>
							<td class="td_cs" style="width: 200px">发货时间</td>
							<td class="td_cs" style="width: 200px">运单号</td>
							<td class="td_cs" style="width: 200px">发货重量（kg）</td>
							<td class="td_cs" style="width: 200px">体积(长*宽*高）</td>
							<td class="td_cs" style="width: 200px">始发地（省）</td>
							<td class="td_cs" style="width: 200px">始发地（市）</td>
							<td class="td_cs" style="width: 200px">始发地（区）</td>
							<td class="td_cs" style="width: 200px">目的地(市)</td>
							<td class="td_cs" style="width: 200px">目的地(区)</td>
							<td class="td_cs" style="width: 200px">计费重量（kg）</td>
							<td class="td_cs" style="width: 200px">供应商名称</td>
							<td class="td_cs" style="width: 200px">产品类型</td>
							<td class="td_cs" style="width: 200px">保值</td>
							<td class="td_cs" style="width: 200px">运费</td>
							<td class="td_cs" style="width: 200px">保价费</td>
							<td class="td_cs" style="width: 200px">其他增值服务费</td>
							<td class="td_cs" style="width: 200px">合计费用</td>
							<td class="td_cs" style="width: 200px">发货仓</td>
							<td class="td_cs" style="width: 200px">所属店铺</td>
							<td class="td_cs" style="width: 200px">成本中心代码</td>
							<td class="td_cs" style="width: 200px">前置单据号</td>
							<td class="td_cs" style="width: 200px">平台订单号</td>
							<td class="td_cs" style="width: 200px">耗材sku编码</td>
							<td class="td_cs" style="width: 200px">长（mm）</td>
							<td class="td_cs" style="width: 200px">宽（mm)</td>
							<td class="td_cs" style="width: 200px">高(mm)</td>
							<td class="td_cs" style="width: 200px">体积(mm3)</td>
							<td class="td_cs" style="width: 200px">发货时间</td>
							<td class="td_cs" style="width: 200px">始发地（省）</td>
							<td class="td_cs" style="width: 200px">始发地（市）</td>
							<td class="td_cs" style="width: 200px">目的地（省）</td>
							<td class="td_cs" style="width: 200px">目的地（市）</td>
							<td class="td_cs" style="width: 200px">发货重量</td>
							<td class="td_cs" style="width: 200px">物流商名称</td>
							<td class="td_cs" style="width: 200px">产品类型</td>
							<td class="td_cs" style="width: 200px">保价货值</td>
							<td class="td_cs" style="width: 200px">体积计费重量（mm3）</td>
							<td class="td_cs" style="width: 200px">计费重量（kg)</td>
							<td class="td_cs" style="width: 200px">首重</td>
							<td class="td_cs" style="width: 200px">续重</td>
							<td class="td_cs" style="width: 200px">折扣</td>
							<td class="td_cs" style="width: 200px">标准运费</td>
							<td class="td_cs" style="width: 200px">折扣运费</td>
							<td class="td_cs" style="width: 200px">保价费</td>
							<td class="td_cs" style="width: 200px">附加费&服务费</td>
							<td class="td_cs" style="width: 200px">最终费用</td>
							<td class="td_cs" style="width: 200px">保价费</td>
							<td class="td_cs" style="width: 200px">是否核销</td>
							<td class="td_cs" style="width: 200px">未核销原因备注</td>
							<td class="td_cs" style="width: 200px">备注</td>
						</tr>
					</thead>
					<thead align="center">
						<tr class='table_head_line'>
						<td style='width: 50px'>
							<td class="td_cs" style="width: 100px"></td>
							<td class="td_cs" style="width: 200px"><input id='applyCertNum'/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
						</tr>
					</thead>
					<tbody align="center">
					</tbody>
				</table>
			</div>
			</div>
		</div>
		<div class="tab-pane fade" id="noed">
			<div class="tab-pane fade in active">
			<c:if test="${master.status==1}">
				<tr>
					 <td><a  class="btn btn-xs btn-grey"
						href='javascript:downloadDetails(1)'>导出</a></td>
						<td><a  class="btn btn-xs btn-grey"
						href='javascript:void(0)' onclick="exportwmd()">导出预估汇总</a></td>	
				 
				</tr>
				<tr>
					<!-- <td><a id='upload' class="btn btn-xs btn-grey"
						href='javascript:void(0)'>删除</a></td> -->
					<td><a id='cancelHx' class="btn btn-xs btn-grey"
						href='javascript:void(0)' onclick="cancelhx()">取消核销</a></td>		
					<!-- <td><a id='hx' class="btn btn-xs btn-grey"
						href='javascript:closeAccount()'>关账</a></td> -->
				</tr></c:if>
				<c:if test="${master.status!=1}">
				<tr>
				<td><a id='cancelHx' class="btn btn-xs btn-grey"
						href='javascript:void(0)' onclick="startAccount()">生成账单</a></td>	
					<td><a  class="btn btn-xs btn-grey"
						href='javascript:downloadDetails(1)'>导出</a></td> 
				 	</tr>
				</c:if>
				<tr><td><a  class="btn btn-xs btn-grey"
						href='javascript:DiffBilldeatils_Is_verification_page("")'>刷新</a></td>
				</tr>
				 <tr><td>请选择月结账号 :</td><td><select id="example-getting-started1" multiple="multiple" >
				  <c:forEach items='${monthAccount}' var="power">
				  <option value='${power.code}'>${power.code}</option>
				  </c:forEach>
                        </select>     
                        </td></tr> 
			</div>
			<div id="diff_billdeatils_is_verification_page">
			<div 
				style="height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px; margin-top: 5px">
				<table class="table table-striped" overflow-x：show>
					<thead align="center">
						<tr class='table_head_line'>
						<td style='width: 50px'>
						<input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/></td>
							<td class="td_cs" style="width: 100px">序号</td>
							<td class="td_cs" style="width: 200px">核销周期</td>
							<td class="td_cs" style="width: 200px">发货时间</td>
							<td class="td_cs" style="width: 200px">运单号</td>
							<td class="td_cs" style="width: 200px">发货重量（kg）</td>
							<td class="td_cs" style="width: 200px">体积(长*宽*高）</td>
							<td class="td_cs" style="width: 200px">始发地（省）</td>
							<td class="td_cs" style="width: 200px">始发地（市）</td>
							<td class="td_cs" style="width: 200px">始发地（区）</td>
							<td class="td_cs" style="width: 200px">目的地(市)</td>
							<td class="td_cs" style="width: 200px">目的地(区)</td>
							<td class="td_cs" style="width: 200px">计费重量（kg）</td>
							<td class="td_cs" style="width: 200px">供应商名称</td>
							<td class="td_cs" style="width: 200px">产品类型</td>
							<td class="td_cs" style="width: 200px">保值</td>
							<td class="td_cs" style="width: 200px">运费</td>
							<td class="td_cs" style="width: 200px">保价费</td>
							<td class="td_cs" style="width: 200px">其他增值服务费</td>
							<td class="td_cs" style="width: 200px">合计费用</td>
							<td class="td_cs" style="width: 200px">发货仓</td>
							<td class="td_cs" style="width: 200px">所属店铺</td>
							<td class="td_cs" style="width: 200px">成本中心代码</td>
							<td class="td_cs" style="width: 200px">前置单据号</td>
							<td class="td_cs" style="width: 200px">平台订单号</td>
							<td class="td_cs" style="width: 200px">耗材sku编码</td>
							<td class="td_cs" style="width: 200px">长（mm）</td>
							<td class="td_cs" style="width: 200px">宽（mm)</td>
							<td class="td_cs" style="width: 200px">高(mm)</td>
							<td class="td_cs" style="width: 200px">体积(mm3)</td>
							<td class="td_cs" style="width: 200px">发货时间</td>
							<td class="td_cs" style="width: 200px">始发地（省）</td>
							<td class="td_cs" style="width: 200px">始发地（市）</td>
							<td class="td_cs" style="width: 200px">目的地（省）</td>
							<td class="td_cs" style="width: 200px">目的地（市）</td>
							<td class="td_cs" style="width: 200px">发货重量</td>
							<td class="td_cs" style="width: 200px">物流商名称</td>
							<td class="td_cs" style="width: 200px">产品类型</td>
							<td class="td_cs" style="width: 200px">保价货值</td>
							<td class="td_cs" style="width: 200px">体积计费重量（mm3）</td>
							<td class="td_cs" style="width: 200px">计费重量（kg)</td>
							<td class="td_cs" style="width: 200px">首重</td>
							<td class="td_cs" style="width: 200px">续重</td>
							<td class="td_cs" style="width: 200px">折扣</td>
							<td class="td_cs" style="width: 200px">标准运费</td>
							<td class="td_cs" style="width: 200px">折扣运费</td>
							<td class="td_cs" style="width: 200px">保价费</td>
							<td class="td_cs" style="width: 200px">附加费&服务费</td>
							<td class="td_cs" style="width: 200px">最终费用</td>
							<td class="td_cs" style="width: 200px">保价费</td>
							<td class="td_cs" style="width: 200px">是否核销</td>
							<td class="td_cs" style="width: 200px">未核销原因备注</td>
							<td class="td_cs" style="width: 200px">备注</td>
						</tr>
					</thead>
					<thead align="center">
						<tr class='table_head_line'>
						<td style='width: 50px'>
							<td class="td_cs" style="width: 100px"></td>
							<td class="td_cs" style="width: 200px"><input id='applyCertNum'/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
							<td class="td_cs" style="width: 200px"><input/></td>
						</tr>
					</thead>
					<tbody align="center">
					</tbody>
				</table>
			</div>
			</div>
		</div>
		<div class="tab-pane fade" id="warning1">
			<div class="tab-pane fade in active">
				<c:if test="${master.status==0}">
				<tr>
					<!-- <td><a id='hx' class="btn btn-xs btn-grey"
						href='javascript:hx()'>导出</a></td>-->
						
				   	<td><a id='hx' class="btn btn-xs btn-grey"
						href='javascript:deleteAccount()'>删除</a></td>
				</tr>
				<tr>
					<td><a  class="btn btn-xs btn-grey"
						href='javascript:void(0)'>导出明细</a></td>
				</tr></c:if>
			</div>
			<div  id='diff_total_page'
				style="height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px; margin-top: 5px">
				<table class="table table-striped" overflow-x：show>
					<thead align="center">
						<tr class='table_head_line'>
							<td>账单名称</td>
							<td>汇总时间</td>
							<td>汇总人</td>
							<td>明细导出</td>
						</tr>
					</thead>
					<tbody align="center">

					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
<style>
.select {
	padding: 3px 4px;
	height: 30px;
	width: 160px;
	text-align: enter;
}

.table_head_line td {
	font-weight: bold;
	font-size: 16px
}

.modal-header {
	height: 50px;
}
</style>

