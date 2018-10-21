<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<script type="text/javascript" src="<%=basePath %>validator/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common_view.js"></script>
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>css/table.css" />
	</head>
	<body>
		<div class="page-header">
		</div>
		<div style="margin-top: 10px;margin-left: 10px;margin-bottom: 10px;">
			<button class="btn btn-xs btn-pink" onclick="pageJump();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
			<button class= "btn btn-xs btn-primary" onclick= "export_data();" >
				<i class= "icon-edit" ></i>导出
			</button>		
			<button class= "btn btn-xs btn-primary" onclick= "to_import_data();" >
				<i class= "icon-edit" ></i>添加数据
			</button>			
		</div>
		
		
		
		
		
<div  class="title_div" id="sc_title">		
<table class="table table-striped" style="table-layout: fixed;">
		   		<thead>
			  		<tr>
			  			<td class="td_ch"><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/></td>
			  			<td class="td_cs">错误信息</td>
			  			<td class="td_cs">店铺名称</td>
			  			<td class="td_cs">仓库名称</td>
			  			<td class="td_cs">仓库类型</td>
			  			<td class="td_cs">作业单号</td>
			  			<td class="td_cs">相关单据号</td>
			  			<td class="td_cs">作业类型名称</td>
			  			<td class="td_cs">商品编码</td>
			  			<td class="td_cs">sku编码</td>
			  			<td class="td_cs">商品名称</td>
<!-- 			  			<td class="td_cs">操作</td> -->
			  		</tr>  	
		  		</thead>

</table>
</div>
<div class="tree_div"></div>

<div style="height:400px;overflow:auto;overflow:scroll; border:solid #F2F2F2 1px;" id="sc_content" onscroll="init_td('sc_title','sc_content')">
<table class="table table-striped" style="table-layout: fixed;">
		  		<tbody  align="center">
		  		<c:forEach items="${pageView.records}" var="res">
			  		<tr>
			  			<td class="td_ch"><input id="ckb" name="ckb" type="checkbox" value="${res.id}"></td>
			  			<td class="td_cs" title="${res.pro_remark}">${res.pro_remark}</td>
			  			<td class="td_cs" title="${res.store_name}">${res.store_name}</td>
			  			<td class="td_cs" title="${res.warehouse_name}">${res.warehouse_name}</td>
			  			<td class="td_cs"><input id="ip1_${res.id}" type="text" readonly="readonly" value="${res.warehouse_type}"/></td>
			  			
			  			<td class="td_cs" title="${res.job_orderno}">${res.job_orderno}</td>
			  			<td class="td_cs" title="${res.related_orderno}">${res.related_orderno}</td>
			  			<td class="td_cs"><input id="ip4_${res.id}" type="text" readonly="readonly" value="${res.job_type}"/></td>
			  			<td class="td_cs" title="${res.item_number}">${res.item_number}</td>
			  			<td class="td_cs" title="${res.sku_number}">${res.sku_number}</td>
			  			<td class="td_cs" title="${res.item_name}">${res.item_name}</td>
<!-- 			  			<td class="td_cs"> -->
<%-- 			  			<button class="btn btn-xs btn-info" onclick="upStatus('${res.id}','0');">修改</button> --%>
<%-- 			  			<button class="btn btn-xs btn-info" onclick="upStatus('${res.id}','1');">保存</button> --%>
<!-- 			  			</td> -->
			  		</tr>
		  		</c:forEach>
		  		</tbody>
</table>
</div>
		<!-- 分页添加 -->
      	<div style="margin-right: 10%;margin-top:20px;margin-bottom: 10%;">${pageView.pageView}</div>		
	</body>
	
	<script type="text/javascript">
	function inverseCkb(items){
		$('[name='+items+']:checkbox').each(function(){
			this.checked=!this.checked;
		});

	}
	
	
	  function findAdd(){
		  var data="?1=1";
		  if($("#contract_no").val()!=""){
			  data=data+"&contract_no="+$("#contract_no").val();
			}
		  if($("#contract_name").val()!=""){
			  data=data+"&contract_name="+$("#contract_name").val();
			}			
		  if($("#carrier_name").val()!=""){
			  data=data+"&carrier_name="+$("#carrier_name").val();
			}			

		  if($("#startplace").val()!=""){
			  data=data+"&startplace="+$("#startplace").val();
			}

		  if($("#endplace").val()!=""){
			  data=data+"&endplace="+$("#endplace").val();
			}
		  openDiv('${root}/control/addressController/list.do'+data);
	  }

		function toUpdates() {
			if($("input[name='ckb']:checked").length>=1){
				if($("input[name='ckb']:checked").length>1){
					alert("只能选择一行!");
			  	}else{
			  		openDiv('${root}control/addressController/toUpdate.do?id='+$("input[name='ckb']:checked").val());
			  	}
			}else{
				alert("请选择一行!");
			}
		}

		function upStatus(id,status){
			if(status==0){
			$("#ip1_"+id).attr("readonly",false);
			$("#ip2_"+id).attr("readonly",false);
			$("#ip3_"+id).attr("readonly",false);
			$("#ip4_"+id).attr("readonly",false);
			$("#ip5_"+id).attr("readonly",false);
			}else{
			$("#ip1_"+id).attr("readonly",true);
			$("#ip2_"+id).attr("readonly",true);
			$("#ip3_"+id).attr("readonly",true);
			$("#ip4_"+id).attr("readonly",true);
			$("#ip5_"+id).attr("readonly",true);
			var delivery_address=$("#ip1_"+id).val();
			var province=$("#ip2_"+id).val();
			var city=$("#ip3_"+id).val();
			var state=$("#ip4_"+id).val();
			var detail_address=$("#ip5_"+id).val();
			var url=root+"/control/JkErrorController/updateErrornData.do?delivery_address="+delivery_address+"&province="+province+"&city="+city+"&state="+state+"&detail_address="+detail_address+"&id="+id;
		    $.ajax({
				type : "POST",
				url: url,  
				data:"",
				dataType: "json",  
				success : function(jsonStr) {
					alert(jsonStr.result_reason);
					pageJump();
				}
			});
			}
		}


function 	anysis_data(){

	$("#but_2").attr("disabled","disabled");
	var url=root+"/control/addressController/anysis.do";
    $.ajax({
		type : "POST",
		url: url,  
		data:"",
		dataType: "json",  
		success : function(jsonStr) {
			alert(jsonStr.result_reason);
			pageJump();
		}
	});
    $("#but_2").attr("disabled",false);
	}

		
		 function export_data(){
				var url=root+"control/JkErrorController/exportExcel.do";
				window.location.href=url;
		 }

       function to_import_data(){
    	   openDiv(root+'control/JkErrorController/import_main.do');
        }
		 
		 function pageJump() {
			 var data=$("#search_form").serialize();
			 openDiv('/BT-LMIS/control/JkErrorController/erroroperlist.do?'+data+'&startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
	 }		 
	</script>
</html>
