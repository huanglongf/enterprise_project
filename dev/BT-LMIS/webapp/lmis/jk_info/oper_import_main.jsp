<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link type="text/css" href="<%=basePath %>css/table.css" rel="stylesheet" />
		<script type="text/javascript" src="<%=basePath %>validator/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common_view.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>js/selectFilter.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/ajaxfileupload.js"></script>
	</head>
	<body>
			<div class= "page-header" align= "left" >
			<h1>异常操作数据导入管理</h1>
		</div>
		<div class="page-header">
			<div style="margin-left: 20px;">
				<table>
			  		<tr>
<!-- 			  			<td> -->
<!-- 				  			<a id="a1" onclick="checkAll()">全选</a>/ -->
<!-- 					    	<a id="a2" onclick="CheckedNo()">全不选</a> -->
<!-- 				    	</td> -->
			  			<td width="15%" align="center">
							<button class="btn btn-xs btn-primary" onclick="$('input[id=file1]').click();">
								<i class="icon-edit"></i>选择文件
							</button>
						</td>
			  			<td width="30%" align="center" >
							<input id="file1" type="file" name='file' style="display:none;">  
							<input id="photoCover" class="input-large" type="text" style="height:30px;width: 100%;" readonly="readonly">  
		  				</td>
		  				<td align="center" width="10%">
							<button id="imps" name="imps" class="btn btn-xs btn-yellow" onclick="imp();">
								<i class="icon-hdd"></i>导入
							</button>
						</td>
					<td align="center" width="10%">
						<button class="btn btn-xs btn-yellow" onclick="transformation();">
							<i class="icon-hdd"></i>转换
						</button>
					</td>
					<td align="center" width="10%">
						<button class="btn btn-xs btn-yellow" onclick="deleteItem();">
							<i class="icon-hdd"></i>删除
						</button>
					</td>
					<td align="center" width="10%">
						<button class="btn btn-xs btn-yellow" onclick="back()">
							<i class="icon-download"></i>返回
						</button>
					</td>
				</tr>
				</table>
			</div>
		</div>
<div  class="title_div" id="sc_title">		
<table class="table table-striped" style="table-layout: fixed;">
	<thead>
	  		<tr class= "table_head_line" >
			    <td class="td_ch"></td>
			  	<td class="td_cs">批次号</td>			  		    
			  	<td class="td_cs">初始条数</td>
			  	<td class="td_cs">导入成功条数</td>
			  	<td class="td_cs">是否已转换</td>
			  	<td class="td_cs">失败条数</td>
			  	<td class="td_cs">创建人</td>
			  	<td class="td_cs">创建时间</td>
			</tr>
	</thead>
</table>
</div>		  		
<div class="tree_div"></div>


<div class="content_div" id="sc_content" onscroll="init_td('sc_title','sc_content')">
<table class="table table-striped" style="table-layout: fixed;">	 		
		  		<tbody>
		  			<c:forEach items="${pageView.records}" var="list">
			  		<tr ondblclick="detail('${list.bat_id }')"> 
			  		    <td class="td_ch"><input id="ckb"  name="ckb" type="checkbox" value= "${list.bat_id }" ></td>
			  			<td class="td_cs">${list.bat_id}</td>
			  			<td class="td_cs">${list.init_count}</td>
			  			<td class="td_cs">${list.success_count}</td>
			  			<td class="td_cs">${list.trans_flag}</td>
			  			<td class="td_cs">${list.error_count}</td>
			  			<td class="td_cs">${list.create_by}</td>
			  			<td class="td_cs">${list.create_date}</td>
			  		</tr>
			  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<div style="margin-right: 5%;margin-top:20px;margin-bottom: 10%;">${pageView.pageView}</div>
	</body>
	<script type="text/javascript">
		function detail(bat_id){
			 openDiv('${root}/control/JkErrorController/toUpload.do?bat_id='+bat_id);
			}
		/**
	   	* 转换
	   	*/
	  	function transformation(){
	  		if($("input[name='ckb']:checked").length!=1){
		  		alert("请选择单行操作");
		  		return;
	  		}
	  		var trans_url=root + "control/JkErrorController/transformation.do";
	  		$.ajax({
	  			cache:false,
	  			type:'POST',
	  			url:trans_url,
	  			data:"bat_id="+$("input[name='ckb']:checked").val(),
	  			contentType:'application/x-www-form-urlencoded;charset=UTF-8',
	  			error:function(request){
	  				alert("连接错误..");
	  			},
	  			success:function(data){
	  				alert(data.out_result_reason);
	  				if(data.out_result=="1"){
	  					 openDiv(root+"control/JkErrorController/import_main.do");
	  				}
	  			}
	  		});
	  	}
	
		$(function () {
	    	$('input[id=file1]').change(function() { 
		        $('#photoCover').val($(this).val());
	        }); 
		});

	  	/**
	   	* 导入
	   	*/
	  	function imp(){
            if($("#photoCover").val().length==0){
               alert("请先选择文件");
               return;
            }
	  		$("#imps").hide();
			$.ajaxFileUpload({
				//上传地址
				url: root+'control/JkErrorController/import.do',
				//是否需要安全协议，一般设置为false
	            secureuri: false, 
	          	//文件上传域的ID
				fileElementId: 'file1', 
				contentType:'application/x-www-form-urlencoded;charset=UTF-8',
				//返回值类型 一般设置为json
				dataType: 'json', 
				//服务器成功响应处理函数
				success: function (data, status){
					alert(data.msg); 
					if(data.code == 200) {
						openDiv(root+'control/JkErrorController/import_main.do','操作费导入');
	                }
				},error: function (data, status, e){
					//服务器响应失败处理函数
					openDiv(root+'control/JkErrorController/import_main.do','操作费导入');
	           	}
			})
			$("#imps").show();
	        return;
	  	}
	  	function inverseCkb(items){
			$('[name='+items+']:checkbox').each(function(){
				this.checked=!this.checked;
			});
		}
	  
	  	function isRepeat(arr) {
	  	    var isRepeat = false;
	  	    var hash = {};
	  	    for(var i in arr) {
	  	        if (hash[arr[i]]) {
	  	            isRepeat = arr[i];
	  	            return isRepeat;
	  	        }
	  	        hash[arr[i]] = true;
	  	    }
	  	    return isRepeat;
	  	}

	  	/**
	  	 * 删除
	  	 */
	  	function deleteItem(){
	  		if($("input[name='ckb']:checked").length<1){
	  			alert("请先选择后再操作");
	  			return;
	  		}
	  		obj = $("input[name='ckb']");
	  		var check_val = '';
	  	    for(k in obj){
	  	    	if(obj[k].checked){
	  	    		check_val = check_val+','+obj[k].value;
	  	    	}
	  	    }
	  	  if (confirm("你确定要删除这些数据吗？")) {
	  	    var url=root+"control/JkErrorController/deleteImport.do?record="+check_val;
	  			$.ajax({
	  				type : "POST",
	  				url: url,  
	  				data:"",
	  				dataType: "json",  
	  				success : function(jsonStr) {
	  		         alert(jsonStr.out_result_reason);
	  		         if(jsonStr.out_result==1){
	  		        	openDiv(root+"control/JkErrorController/import_main.do");
	  		         }
	  				}
	  			});
	  	  }
	  	}
		function back(){
			openDiv( root+"control/JkErrorController/erroroperlist.do");
		}

	</script>
</html>
