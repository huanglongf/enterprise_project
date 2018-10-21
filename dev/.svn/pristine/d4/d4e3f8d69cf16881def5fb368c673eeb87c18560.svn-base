<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%-- 		<link href="<%=basePath %>daterangepicker/font-awesome.min.css" rel="stylesheet"> --%>
<%-- 		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" /> --%>
<%-- 		<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script> --%>
<%-- 		<script type="text/javascript" src="<%=basePath %>js/common.js"></script> --%>
<%-- 		<script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script> --%>
<%-- 		<script type="text/javascript" src="<%=basePath %>validator/js/jquery-1.9.1.min.js"></script> --%>
<%-- 		<script type="text/javascript" src="<%=basePath %>js/common_view.js"></script> --%>
<%-- 		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>css/table.css" />		 --%>
		
		
		
				<link href="<%=basePath %>daterangepicker/font-awesome.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
		<script type="text/javascript" src="<%=basePath %>daterangepicker/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common_view.js"></script>
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>css/table.css" />	
	   <script type="text/javascript">
			window.jQuery || document.write("<script src='<%=basePath %>assets/js/jquery-2.0.3.min.js'>" + "<"+"/script>");
			$(document).ready(function() {
				$(".form-control").daterangepicker(null, function(start, end, label) {
                	console.log(start.toISOString(), end.toISOString(), label);
               	});
 			});
			$(document).ready(function() {
				$("#reservation2").daterangepicker(null, function(start, end, label) {
                	console.log(start.toISOString(), end.toISOString(), label);
               	});
 			});
		</script>		
	</head>
	
	<body>


<div style="margin-top: 20px;margin-left: 20px;margin-bottom: 20px;">
			<table>
		  		<tr>
		  			<td width="10%">数据源类型:</td>
		  			<td width="20%"><select id="dataType" name="dataType">
		  			                   <option value="1">耗材-采购明细</option>
		  			                   <option value="2">操作费</option>
		  			                   <option value="3">快递运单</option>
		  			                   <option value="4">快递运单明细</option>
		  			                   <option value="5">仓储费</option>
		  			                   <option value="6">耗材-实际使用量</option>
		  			</select>
		  			</td>
		  			<td width="10%">错误类型:</td>
		  			<td width="20%"><select id="proFlag" name="proFlag">
		  			                   <option value="0">未处理</option>
		  			                   <option value="1">处理成功</option>
		  			                   <option value="2">处理失败</option>
		  			                </select>
		  			</td>
		  		</tr>		  			  		
			</table>
		</div>
    
	<div style="margin-top: 10px; margin-left: 10px; margin-bottom: 10px;">
			<button class="btn btn-xs btn-pink" onclick="find();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
			
			<button class="btn btn-xs btn-pink" onclick="excelError();">
				<i class="icon-search icon-on-right bigger-110"></i>导出错误信息
			</button>
	</div>		
<div  class="title_div" id="sc_title_operator"  style="display:none;">		
   <table class="table table-striped" style="table-layout: fixed;">
		   		<thead>
			  		<tr>
			  		    <td class="td_cs">店铺名称</td>
			  		    <td class="td_cs">作业单号</td>
			  		    <td class="td_cs">作业类型名称</td>
			  		    <td class="td_cs">库位编码</td>
			  		    <td class="td_cs">出库数量</td>
			  		    <td class="td_cs">商品编码</td>
			  		    <td class="td_cs">货号</td>
			  		    <td class="td_cs">操作员</td>
			  		    <td class="td_cs">过程处理结果</td>
			  		</tr>
		  		</thead>	  		
   </table>
</div>
<div  class="title_div" id="sc_title_invitation"  style="display:none;">		
   <table class="table table-striped" style="table-layout: fixed;">
		   		<thead>
			  		<tr>
			  		    <td class="td_cs">店铺名称</td>
			  		    <td class="td_cs">PO单号</td>
			  		    <td class="td_cs">条形码</td>
			  		    <td class="td_cs">宝尊编码</td>
			  		    <td class="td_cs">商品编码</td>
			  		    <td class="td_cs">实际入库数量</td>
			  		    <td class="td_cs">采购单价</td>
			  		    <td class="td_cs">实际到货入库金额</td>
			  		    <td class="td_cs">过程处理结果</td>
			  		</tr>
		  		</thead>	  		
   </table>
</div>
<div  class="title_div" id="sc_title_order"  style="display:none;">		
   <table class="table table-striped" style="table-layout: fixed;">
		   		<thead>
			  		<tr>
			  		    <td class="td_cs">店铺代码</td>
			  		    <td class="td_cs">店铺名称</td>
			  		    <td class="td_cs">所属仓库编码 逻辑仓库</td>
			  		    <td class="td_cs">所属仓库名称 逻辑仓库</td>
			  		    <td class="td_cs">快递代码（如SF YTO 等）</td>
			  		    <td class="td_cs">快递名称</td>
			  		    <td class="td_cs">平台订单号</td>
			  		    <td class="td_cs">上位系统订单号</td>
			  		    <td class="td_cs">运单号</td>
			  		    <td class="td_cs">实际重量</td>
			  		    <td class="td_cs">长</td>
			  		    <td class="td_cs">高</td>
			  		    <td class="td_cs">体积</td>
			  		    <td class="td_cs">始发地</td>
			  		    <td class="td_cs">省份</td>
			  		    <td class="td_cs">城市</td>
			  		    <td class="td_cs">区县</td>
			  		    <td class="td_cs">收件人</td>
			  		    <td class="td_cs">详细地址</td>
			  		    <td class="td_cs">过程处理结果</td>
			  		</tr>
		  		</thead>	  		
   </table>
</div>

<div  class="title_div" id="sc_title_orderDetail" style="display:none;">		
   <table class="table table-striped" style="table-layout: fixed;">
		   		<thead>
			  		<tr>
			  		    <td class="td_cs">运单号</td>
			  		    <td class="td_cs">SKU条码</td>
			  		    <td class="td_cs">条形码</td>
			  		    <td class="td_cs">商品名称</td>
			  		    <td class="td_cs">扩展属性</td>
			  		    <td class="td_cs">过程处理结果</td>
			  		</tr>
		  		</thead>	  		
   </table>
</div>

<div  class="title_div" id="sc_title_storage"   style="display:none;">		
   <table class="table table-striped" style="table-layout: fixed;">
		   		<thead>
			  		<tr>
			  		    <td class="td_cs">店铺编号</td>
			  		    <td class="td_cs">店铺名称</td>
			  		    <td class="td_cs">仓库编号</td>
			  		    <td class="td_cs">仓库名称</td>
			  		    <td class="td_cs">系统仓 /逻辑仓</td>
			  		    <td class="td_cs">商品类型</td>
			  		    <td class="td_cs">过程处理结果</td>
			  		</tr>
		  		</thead>	  		
   </table>
</div>

<div  class="title_div" id="sc_title_use"  style="display:none;">		
   <table class="table table-striped" style="table-layout: fixed;">
		   		<thead>
			  		<tr>
			  		    <td class="td_cs">店铺代码</td>
			  		    <td class="td_cs">店铺名称</td>
			  		    <td class="td_cs">耗材编码</td>
			  		    <td class="td_cs">耗材名称</td>
			  		    <td class="td_cs">过程处理结果</td>
			  		</tr>
		  		</thead>	  		
   </table>
</div>

<div class="tree_div"></div>



<div class="content_div" id="sc_content_operator"  style="display:none;" onscroll="init_td('sc_title','sc_content')">
<table class="table table-striped" style="table-layout: fixed;">
   <tbody>
  
		  			<c:forEach items="${pageView.records}" var="list">
			  		<tr>
			  		    <td class="td_cs" title="${list.store_name}">${list.store_name}</td>
			  			<td class="td_cs" title="${list.job_orderno}">${list.job_orderno}</td>
			  			<td class="td_cs" title="${list.job_type}">${list.job_type}</td>
			  			<td class="td_cs" title="${list.storaggelocation_code}">${list.storaggelocation_code}</td>
			  			<td class="td_cs" title="${list.out_num}">${list.out_num}</td>
			  			<td class="td_cs" title="${list.item_number}">${list.item_number}</td>
			  			<td class="td_cs" title="${list.art_no}">${list.art_no}</td>
			  			<td class="td_cs" title="${list.operator}">${list.operator}</td>
			  			<td class="td_cs" title="${list.pro_remark}">${list.pro_remark}</td>
			  		</tr>
			  		</c:forEach>
	</tbody> 		
</table>		
</div>

<div class="content_div" id="sc_content_invitation"   style="display:none;" onscroll="init_td('sc_title','sc_content')">
<table class="table table-striped" style="table-layout: fixed;">
   <tbody>
		  			<c:forEach items="${pageView.records}" var="list">
			  		<tr>
			  		    <td class="td_cs" title="${list.store_name}">${list.store_name}</td>
			  			<td class="td_cs" title="${list.inbound_no}">${list.inbound_no}</td>
			  			<td class="td_cs" title="${list.barcode}">${list.barcode}</td>
			  			<td class="td_cs" title="${list.bz_number}">${list.bz_number}</td>
			  			<td class="td_cs" title="${list.item_no}">${list.item_no}</td>
			  			<td class="td_cs" title="${list.inbound_qty}">${list.inbound_qty}</td>
			  			<td class="td_cs" title="${list.purchase_price}">${list.purchase_price}</td>
			  			<td class="td_cs" title="${list.inbound_money}">${list.inbound_money}</td>
			  			<td class="td_cs" title="${list.pro_remark}">${list.pro_remark}</td>
			  		</tr>
			  		</c:forEach>
	</tbody> 		
</table>		
</div>

<div class="content_div" id="sc_content_order"   style="display:none;" onscroll="init_td('sc_title','sc_content')">
<table class="table table-striped" style="table-layout: fixed;">
   <tbody>
		  			<c:forEach items="${pageView.records}" var="list">
			  		<tr>
			  		    <td class="td_cs" title="${list.store_code}">${list.store_code}</td>
			  			<td class="td_cs" title="${list.store_name}">${list.store_name}</td>
			  			<td class="td_cs" title="${list.warehouse_code}">${list.warehouse_code}</td>
			  			<td class="td_cs" title="${list.warehouse_name}">${list.warehouse_name}</td>
			  			<td class="td_cs" title="${list.transport_code}">${list.transport_code}</td>
			  			<td class="td_cs" title="${list.transport_name}">${list.transport_name}</td>
			  			<td class="td_cs" title="${list.delivery_order}">${list.delivery_order}</td>
			  			<td class="td_cs" title="${list.epistatic_order}">${list.epistatic_order}</td>
			  			<td class="td_cs" title="${list.express_number}">${list.express_number}</td>
			  			<td class="td_cs" title="${list.weight}">${list.weight}</td>
			  			<td class="td_cs" title="${list.length}">${list.length}</td>
			  			<td class="td_cs" title="${list.higth}">${list.higth}</td>
			  			<td class="td_cs" title="${list.volumn}">${list.volumn}</td>
			  			<td class="td_cs" title="${list.delivery_address}">${list.delivery_address}</td>
			  			<td class="td_cs" title="${list.province}">${list.province}</td>
			  			<td class="td_cs" title="${list.city}">${list.city}</td>
			  			<td class="td_cs" title="${list.state}">${list.state}</td>
			  			<td class="td_cs" title="${list.shiptoname}">${list.shiptoname}</td>
			  			<td class="td_cs" title="${list.address}">${list.address}</td>
			  			<td class="td_cs" title="${list.pro_remark}">${list.pro_remark}</td>
			  		</tr>
			  		</c:forEach>
	</tbody> 		
</table>		
</div>
<div class="content_div" id="sc_content_orderDetail"   style="display:none;" onscroll="init_td('sc_title','sc_content')">
<table class="table table-striped" style="table-layout: fixed;">
   <tbody>
		  			<c:forEach items="${pageView.records}" var="list">
			  		<tr>
			  		    <td class="td_cs" title="${list.express_number}">${list.express_number}</td>
			  			<td class="td_cs" title="${list.sku_number}">${list.sku_number}</td>
			  			<td class="td_cs" title="${list.barcode}">${list.barcode}</td>
			  			<td class="td_cs" title="${list.item_name}">${list.item_name}</td>
			  			<td class="td_cs" title="${list.extend_pro}">${list.extend_pro}</td>
			  			<td class="td_cs" title="${list.pro_remark}">${list.pro_remark}</td>
			  		</tr>
			  		</c:forEach>
	</tbody> 		
</table>		
</div>
<div class="content_div" id="sc_content_storage" style="display:none;" onscroll="init_td('sc_title','sc_content')">
<table class="table table-striped" style="table-layout: fixed;">
   <tbody>
		  			<c:forEach items="${pageView.records}" var="list">
			  		<tr>
			  		    <td class="td_cs" title="${list.store_code}">${list.store_code}</td>
			  			<td class="td_cs" title="${list.store_name}">${list.store_name}</td>
			  			<td class="td_cs" title="${list.warehouse_code}">${list.warehouse_code}</td>
			  			<td class="td_cs" title="${list.warehouse_name}">${list.warehouse_name}</td>
			  			<td class="td_cs" title="${list.system_warehouse}">${list.system_warehouse}</td>
			  			<td class="td_cs" title="${list.item_type}">${list.item_type}</td>
			  			<td class="td_cs" title="${list.pro_remark}">${list.pro_remark}</td>
			  		</tr>
			  		</c:forEach>
	</tbody> 		
</table>		
</div>

<div class="content_div" id="sc_content_use"  style="display:none;" onscroll="init_td('sc_title','sc_content')">
<table class="table table-striped" style="table-layout: fixed;">
   <tbody>
		  			<c:forEach items="${pageView.records}" var="list">
			  		<tr>
			  		    <td class="td_cs" title="${list.store_code}">${list.store_code}</td>
			  			<td class="td_cs" title="${list.store_name}">${list.store_name}</td>
			  			<td class="td_cs" title="${list.sku_code}">${list.sku_code}</td>
			  			<td class="td_cs" title="${list.sku_name}">${list.sku_name}</td>
			  			<td class="td_cs" title="${list.pro_remark}">${list.pro_remark}</td>
			  		</tr>
			  		</c:forEach>
	</tbody> 		
</table>		
</div>
<form id="serarch_td">
			<input type="hidden" id="dataTypeJs" name="dataTypeJs" value="${dataType}">
			<input type="hidden" id="proFlagJs" name="proFlagJs" value="${proFlag}">
</form>
		<div style="margin-right: 5%;margin-top:20px;margin-bottom: 10%;">${pageView.pageView}</div>
		<script type="text/javascript">
			function pageJump() {
			 	var data=$("#serarch_td").serialize();
		 		openDiv('${root}control/transPoolController/detail_list.do?'+data+'&startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
			};
		</script>
	</body>
	<script type="text/javascript">

	   $(function(){
           var dataTypeParam = document.getElementById("dataTypeJs").value;
           var proFlagParam = document.getElementById("proFlagJs").value;
           var all_options = document.getElementById("dataType").options;
           for (i=0; i<all_options.length; i++){
        	   if (all_options[i].value == dataTypeParam) {
        		   all_options[i].selected = true;
        		   break;
               }
           } 
           var proFlag_option = document.getElementById("proFlag").options;
           for (i=0; i<proFlag_option.length; i++){
        	   if (proFlag_option[i].value == proFlagParam) {
        		   proFlag_option[i].selected = true;
        		   break;
               }
           } 
           if(dataTypeParam == 1){
        	   document.getElementById("sc_title_invitation").style.display="";
        	   document.getElementById("sc_content_invitation").style.display="";
           }
           if(dataTypeParam == 2){
        	   document.getElementById("sc_title_operator").style.display="";
        	   document.getElementById("sc_content_operator").style.display="";
           }
           if(dataTypeParam == 3){
        	   document.getElementById("sc_title_order").style.display="";
        	   document.getElementById("sc_content_order").style.display="";
           }
           if(dataTypeParam == 4){
        	   document.getElementById("sc_title_orderDetail").style.display="";
        	   document.getElementById("sc_content_orderDetail").style.display="";
           }
           if(dataTypeParam == 5){
        	   document.getElementById("sc_title_storage").style.display="";
        	   document.getElementById("sc_content_storage").style.display="";
           }
           if(dataTypeParam == 6){
        	   document.getElementById("sc_title_use").style.display="";
        	   document.getElementById("sc_content_use").style.display="";
           }
	   });
	  	/**
	   	* 查询
	   	*/
	  	function find(){
	  		var data=$("#serarch_td").serialize();
			openDiv('${root}control/transPoolController/messageMainList.do?'+data
					                                                  +'&dataType='+$("#dataType").val()
					                                                  +'&proFlag='+$("#proFlag").val()
					                                                  )
	  	}

	  	function jump(){
		  	if($("#opId").val()=='2'){
		  		openDiv('${root}/control/summaryController/summaryList.do?ym='+$("#ym").val()+'&clientid='+$("#clientid").val()+'&storeid='+$("#storeid").val());
			}else{
		  		var data=$("#serarch_td").serialize();
		  		openDiv('${root}/control/transPoolController/list.do?'+data);
			}
		}

	  	function excelError(){
			var url=root+'/control/transPoolController/excelError_pool.do';
			var htm_td="";
			   $.ajax({
					type : "POST",
					url: url,  
					data:"",
					dataType: "html",  
					success : function(jsonStr) {
						window.open(root+jsonStr);
					}
				});
			}

		function excel(){
			
			var data=$("#serarch_td").serialize();
			var url=root+'/control/transPoolController/excel_pool_detail.do?'+data
            +'&transportNum='+$("#transportNum").val()
            +'&orderNum='+$("#orderNum").val()
            +'&startPlace='+$("#startPlace").val()
            +'&endPlace='+$("#endPlace").val()
            +"&orederTime="+$("#orederTime").val().replace(" - ",'/')
            +'&transportTime='+$("#transportTime").val().replace(" - ",'/');
			 $.ajax({
					type : "POST",
					url: url,  
					data:"",
					dataType: "",  
					success : function(jsonStr) {
						window.open(root+jsonStr);
					}
				});
			}
	  	function clean(){
	  		$("input[type*='text']").each(function(){
	  		     $(this).val('');
	  		});
	  	}		
	</script>
</html>
