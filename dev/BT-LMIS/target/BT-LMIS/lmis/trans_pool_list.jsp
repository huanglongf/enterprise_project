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
	</head>
	<body>
	<div style="margin-top: 10px; margin-left: 10px; margin-bottom: 10px;">
           		<button class="btn btn-xs btn-grey" type="button" onclick="back();">
   					<i class="icon-undo" ></i>返回
       			</button>	
            	<button class="btn btn-xs btn-grey" type="button" onclick="excel();">
   					<i class="icon-undo" ></i>导出
       			</button>	
       			
       			<button class="btn btn-xs btn-grey" type="button" onclick="messageError();">
   					<i class="icon-undo" ></i>错误信息列表详情
       			</button>	      
<!--              	<button class="btn btn-xs btn-grey" type="button" onclick="test();"> -->
<!--    		 			<i class="icon-undo" ></i>测试 -->
<!--        			</button>	         			 					 -->
	</div>
	
<div  class="title_div" id="sc_title">		
<table class="table table-striped" style="table-layout: fixed;">
		   		<thead>
			  		<tr class="table_head_line">
			  		    <td class="td_cs">结算周期</td>
			  		    <td class="td_cs">合同号</td>
			  		    <td class="td_cs">合同名称</td>			  		    
			  			<td class="td_cs">仓库</td>
			  			<td class="td_cs">成本中心</td>
			  			<td class="td_cs">店铺</td>
			  			<td class="td_cs">单量</td>
			  			<td class="td_cs">整车运费</td>
			  			<td class="td_cs">零担运费</td>
			  			<td class="td_cs">保价费</td>
			  			<td class="td_cs">上楼费</td>
			  			<td class="td_cs">卸货费</td>
			  			<td class="td_cs">送货费</td>
			  			<td class="td_cs">提货费</td>
			  			<td class="td_cs">管理费</td>
			  			<td class="td_cs">派送费</td>
			  			<td class="td_cs">折扣费用</td>
			  			<td class="td_cs">合计</td>
			  		</tr>
		  		</thead>
</table>
</div>		  		
<div class="tree_div"></div>


<div class="content_div" id="sc_content" onscroll="init_td('sc_title','sc_content')">
<table class="table table-striped" style="table-layout: fixed;">	 		
		  		<tbody>
		  			<c:forEach items="${pageView.records}" var="list">
		  			
			  		<tr align="center">
			  			<td class="td_cs">${list.create_date}</td>
			  			<td class="td_cs" title="${list.contract_no}">${list.contract_no}</td>
			  			<td class="td_cs">${list.contract_name}</td>
			  			<td class="td_cs">${list.warehouse}</td>
			  			<td class="td_cs">${list.cost_center}</td>
			  			<td class="td_cs">${list.shop_name}</td>
			  			<td class="td_cs">${list.bill_num}</td>
			  			<td class="td_cs">${list.carload_price}</td>
			  			<td class="td_cs">${list.Lingdan_price}</td>
			  			<td class="td_cs">${list.insurance_price}</td>
			  			<td class="td_cs">${list.upstairs_price}</td>
			  			<td class="td_cs">${list.unloading_price}</td>
			  			<td class="td_cs">${list.send_price}</td>
			  			<td class="td_cs">${list.take_price}</td>
			  			<td class="td_cs">${list.manager_price}</td>
			  			<td class="td_cs">${list.dispatch_price}</td>
			  			<td class="td_cs">${list.discount_price}</td>
			  			<td class="td_cs">${list.total_price}</td>
			  		</tr>
			  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<div style="margin-right: 5%;margin-top:20px;margin-bottom: 10%;">${pageView.pageView}</div>
		
		
		<input type="hidden" id="createDate" value="${queryParam.createDate}"/>
		<input type="hidden" id="contractId" value="${queryParam.contractId}"/>
		<input type="hidden" id="transportCode" value="${queryParam.transportCode}"/>
	</body>
	<script type="text/javascript">
	  	/**
	   	* 查询
	   	*/
	  	function find(){
			openDiv('${root}control/transPoolController/list.do?createDate='+$("#createDate").val()+"&contractId="+$("#contractId").val()+"&transportCode="+$('#transportCode').val());
	  	}

	  	function back(){
			openDiv('${root}control/transPoolController/listMain.do');
	  	}

	  	function messageError(){
			openDiv('${root}control/transPoolController/messageMainList.do');
	  	}
	  	
	  	function jump_detail(contractId,createDate){
	  		openDiv('${root}control/transPoolController/detail_list.do?createDate='+createDate+'&contractId='+contractId+"&transportCode="+$('#transportCode').val());
		}	  
		
	  	function pageJump() {
	  		 var data=$("#serarch_td").serialize();
	  		 openDiv('/BT-LMIS/control/transPoolController/list.do?startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
	  	}

	  	
		function excel(){
			var url=root+'/control/transPoolController/excel_pool.do?createDate='+$("#createDate").val()+"&contractId="+$("#contractId").val();
			var htm_td="";
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

		function test(){
			 openDiv('/BT-LMIS//control/threadController/test.do');
			}
	</script>
	
	
</html>
