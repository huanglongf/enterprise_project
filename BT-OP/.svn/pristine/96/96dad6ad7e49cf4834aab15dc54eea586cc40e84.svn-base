
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ include file="/base/yuriy.jsp"%>
<title>快递信息查询</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%-- <link href="<%=basePath %>plugin/My97DatePicker/WdatePicker.js"
	rel="stylesheet">
<link href="<%=basePath %>assets/css/less/tables.less" type="text/css"
	rel="stylesheet" />
<link type="text/css" href="<%=basePath %>assets/css/loadingStyle.css"
	rel="stylesheet" media="all" />

<link href="<%=basePath %>plugin/daterangepicker/font-awesome.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" media="all"
	href="<%=basePath %>plugin/daterangepicker/daterangepicker-bs3.css" />
<script type="text/javascript"
	src="<%=basePath %>plugin/daterangepicker/jquery-1.8.3.min.js"></script>
<script type="text/javascript"
	src="<%=basePath %>plugin/daterangepicker/moment.js"></script>
<!--  <script type="text/javascript" src="<%=basePath %>plugin/daterangepicker/daterangepicker.js"></script>-->
<script type="text/javascript" src="<%=basePath %>js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath %>base/base.js"></script>
<script type="text/javascript"
	src="<%=basePath %>/js/jquery.shCircleLoader.js"></script> --%>
<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
<%-- <script type="text/javascript" src="<%=basePath %>js/common.js"></script> --%>
<%-- <script type="text/javascript"
	src="<%=basePath %>plugin/My97DatePicker/WdatePicker.js"></script> --%>
<script type='text/javascript'>

function pageJump() {
	 var param='';
	 param=$('#query_logistics').serialize();
	 $.ajax({
			type: "POST",
          	url:'${root}control/orderPlatform/waybillMasterController/page.do?'+
          			param+
          			'&flag='+
          			$("#flag").val()+
          			'&startRow='+
          			$("#startRow").val()+
          			'&endRow='+
          			$("#startRow").val()+
          			"&page="+
          			$("#pageIndex").val()+
          			"&pageSize="+
          			$("#pageSize").val(),
          	dataType: "text",
          	data:'',
   		cache:false,
   		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
          	success: function (data){
             $("#page_view").html(data);
          	}
	  	});  
}
		
		
		

		/* $(document).ready(function() {
			
			$('#provice_code').bind('change',function(){
				if(this.value==''){
					$('#city_code').html('<option value="">---请选择---</option>');
					$('#state_code').html('<option value="">---请选择---</option>');	
				}
				$.post('${root}control/orderPlatform/waybillMasterController/getArea.do?area_code='+this.value,function(data){	
					if(data.toString()=='[object XMLDocument]'){
						  window.location='/BT-OP';
							return;
					  };
			    if(data.nodeName=='#document')toMain();		
				var  htmlStr='<option value="">---请选择---</option>';
				$.each(data.area,function(index,item){
					htmlStr=htmlStr+'<option value='+this.area_code+'>'+this.area_name+'</option>';		
				});
				$('#city_code').html(htmlStr);
				$('#state_code').html('<option value="">---请选择---</option>');
				});
			});
			$('#city_code').bind('change',function(){
				if(this.value==''){
					$('#state_code').html('<option value="">---请选择---</option>');	
				}
				$.post('${root}control/orderPlatform/waybillMasterController/getArea.do?area_code='+this.value,function(data){
					if(data.toString()=='[object XMLDocument]'){
						  window.location='/BT-OP';
							return;
					  };
				if(data.nodeName=='#document')toMain();
				var  htmlStr='<option value="">---请选择---</option>';
				$.each(data.area,function(index,item){
					htmlStr=htmlStr+'<option value='+this.area_code+'>'+this.area_name+'</option>';		
				});
				$('#state_code').html(htmlStr);
				});
			});
			$('#org_provice_code').bind('change',function(){
				if(this.value==''){
					$('#org_city_code').html('<option value="">---请选择---</option>');
					$('#org_state_code').html('<option value="">---请选择---</option>');	
				}
				$.post('${root}control/orderPlatform/waybillMasterController/getArea.do?area_code='+this.value,function(data){	
					if(data.toString()=='[object XMLDocument]'){
						  window.location='/BT-OP';
							return;
					  };
			    if(data.nodeName=='#document')toMain();		
				var  htmlStr='<option value="">---请选择---</option>';
				$.each(data.area,function(index,item){
					htmlStr=htmlStr+'<option value='+this.area_code+'>'+this.area_name+'</option>';		
				});
				$('#org_city_code').html(htmlStr);
				$('#org_state_code').html('<option value="">---请选择---</option>');
				});
			});
			$('#org_city_code').bind('change',function(){
				if(this.value==''){
					$('#org_state_code').html('<option value="">---请选择---</option>');	
				}
				$.post('${root}control/orderPlatform/waybillMasterController/getArea.do?area_code='+this.value,function(data){
					if(data.toString()=='[object XMLDocument]'){
						  window.location='/BT-OP';
							return;
					  };
				if(data.nodeName=='#document')toMain();
				var  htmlStr='<option value="">---请选择---</option>';
				$.each(data.area,function(index,item){
					htmlStr=htmlStr+'<option value='+this.area_code+'>'+this.area_name+'</option>';		
				});
				$('#org_state_code').html(htmlStr);
				});
			});
		}); */
		
		
		 function find(){
			 	
		    	jumpToPage(1);
		    }

	 /* function change(){
           if($("#chcdk").attr("checked")=="checked"){
        	   slideDown();
            }
           else{
        	   slideUp();
           }
		 }
	 
	function slideDown(){
		$("#mydiv").slideDown();
	 }

	 function slideUp(){
		 $("#mydiv").slideUp();
	 } */
    </script>
</head>
<body>
	<div class="page-header">
		<h1 style='margin-left: 20px'>运单信息查询</h1>
	</div>
	<div style="margin-top: 20px; margin-left: 20px; margin-bottom: 20px;">
		<div class="widget-header header-color-blue">
			<h4>查询列表</h4>
			<input id="chcdk" onchange="change()" type="checkbox"
				class="ace ace-switch ace-switch-5" /> <span class="lbl"></span>
		</div>
	</div>


	<div id="mydiv" style="display: none;">
		<div>
			<form id="query_logistics">
				<table width='85%' id="query_logistics_inf">
					<tr>
						<td width="20%" align="left"><label class="blue">状态:</label></td>
						<td width="25%" align="right"><input type='text'
							id='states' name='states' style="width: 260px;">
						</td>
						<td align="left" width="10%"><label class="blue">发货机构:</label></td>
						<td width="25%" align="right">
								<select id="from_orgnization" name="from_orgnization" data-edit-select="1" style="width: 168px;">
									<option value="">---请选择---</option>
									 <c:forEach items= "${orgs}" var= "street" >
					  					    <option value="${street.org_name}">${street.org_name}</option>
									 </c:forEach>  
								</select>
						</td>
					</tr>
					<tr>
						<td width="20%" align="left"><label class="blue">下单日期</label></td>
						<td width="25%" align="right"><input type='text'
							id='query_time' name='query_time' style="width: 260px;"
							onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})">
						</td>
						<td width="10%" align="left"><label class="blue">到:</label></td>
						<td width="25%" align="right"><input type='text'
							id='query_end_time' name='query_end_time' style="width: 260px;"
							onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})">
						</td>
					</tr>
					<tr>
						<td width="20%" align="left"><label class="blue">快递公司:</label></td>
						<td width="25%" align="right">
							<select id="expressCode" name="expressCode" data-edit-select="1" style="width: 168px;">
				  				<option value= "">---请选择---</option>
				  				  <c:forEach items= "${venders}" var= "street" >
				  					    <option value="${street.express_code}"  >${street.express_name}</option>
								 </c:forEach> 
							</select>
						</td>
						<td width="25%" align="left"><label class="blue">快递业务类型:</label></td>
						<td width="25%" align="right">
						<select id="producttype_name" name="producttype_name" data-edit-select="1" style="width: 168px;">
			  				<option value= "">---请选择---</option>
			  				  <c:forEach items= "${type}" var= "street" >
			  					    <option value="${street.product_type_code}">${street.product_type_name}</option>
							 </c:forEach> 
						</select>
						</td>
					</tr>
					<tr>
						<td width="25%" align="left"><label class="blue">快递单号:</label></td>
						<td width="25%" align="right">
							<input type='text' id='order_id' name='order_id' style="width: 260px;">
						</td>
						<td width="25%" align="left"><label class="blue">支付方式:</label></td>
						<td width="25%" align="right">
							<select id="group_code" name="group_code" data-edit-select="1" style="width: 168px;">
				  				<option value= "">月付</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="25%" align="left"><label class="blue">收货机构:</label></td>
						<td width="25%" align="right">
							<select id="to_orgnization" name="to_orgnization" data-edit-select="1" style="width: 168px;">
				  				<option value= "">---请选择---</option>
				  				  <c:forEach items= "${orgs}" var= "street" >
				  					    <option value="${street.organization_code}">${street.org_name}</option>
								 </c:forEach>  
							</select>
						</td>
						<td width="25%" align="left"><label class="blue">省:</label></td>
						<td width="25%" align="right">
							<select id="org_provice_code" name="to_province" data-edit-select="1" style="width: 168px;" onchange='shiftArea(1,"org_provice_code","org_city_code","org_state_code","")'>
				  				<option value= "">---请选择---</option>
				  				<c:forEach items="${areas}" var = "area" >
								    <c:if test='${org.org_province_code eq area.area_code }'> 
										<option selected='selected' value="${area.area_code}">${area.area_name}</option>
									 </c:if>
									 <c:if test='${org.org_province_code ne area.area_code }'> 
										<option  value="${area.area_code}">${area.area_name}</option>
									 </c:if>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td width="25%" align="left"><label class="blue">市:</label></td>
						<td width="25%" align="right">
							<select id="org_city_code" name="to_city" data-edit-select="1" style="width: 168px;" onchange='shiftArea(2,"org_provice_code","org_city_code","org_state_code","")'>
				  				<option value= "">---请选择---</option>
								<c:forEach items="${city}" var = "cityVar" >
								    <c:if test='${org.org_city_code eq cityVar.area_code}'>
										<option selected='selected'  value="${cityVar.area_code}">${cityVar.area_name}</option>
									</c:if>
									<c:if test='${org.org_city_code ne cityVar.area_code}'>
										<option  value="${cityVar.area_code}">${cityVar.area_name}</option>
									</c:if>
								</c:forEach>
							</select>
						</td>
						<td width="25%" align="left"><label class="blue">区:</label></td>
						<td width="25%" align="right">
							<select id="org_state_code" name="to_state" data-edit-select="1" style="width: 168px;" >
				  				<option value= "">---请选择---</option>
								<c:forEach items="${state}" var = "stateVar" >
							      <c:if test='${stateVar.area_code eq org.org_state_code }'> 
									<option selected='selected' value="${stateVar.area_code}">${stateVar.area_name}</option>
								  </c:if>
								  <c:if test='${stateVar.area_code ne org.org_state_code }'> 
									<option  value="${stateVar.area_code}">${stateVar.area_name}</option>
								  </c:if>	
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td width="25%" align="left"><label class="blue">街道:</label></td>
						<td width="25%" align="right">
							<input type='text' id='to_street' name='to_street' value="${org.org_street}" style="width: 260px;">
						</td>
						<td width="25%" align="left"><label class="blue">收货联系人:</label></td>
						<td width="25%" align="right">
							<input type='text' id='to_contacts' name='to_contacts' style="width: 260px;">
						</td>

					</tr>
					<tr>
						<td width="25%" align="left"><label class="blue">收货联系电话:</label></td>
						<td width="25%" align="right">
							<input type='text' id='to_phone' name='to_phone' style="width: 260px;">
						</td>
						<td width="25%" align="left"><label class="blue">收货地址:</label></td>
						<td width="25%" align="right">
							<input type='text' id='to_address' name='to_address' style="width: 260px;">
						</td>
					</tr>
					<tr>
						<td width="25%" align="left"><label class="blue">运单号:</label></td>
						<td width="25%" align="right">
							<input type='text' id='waybill' name='waybill' style="width: 260px;">
						</td>
					</tr>
				</table>
				<input type="radio" name="flag" value="fromme">我发的
				 <input type="radio" name="flag" value="tome">我收的
			</form>
			<div style="margin-top: 10px;margin-left: 20px;margin-bottom: 10px;">
			<button class="btn btn-xs btn-pink" onclick="find();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
 			&nbsp; 
			</div>	
		</div>
		</div>
	</div>

	<div class="tree_div" id="page_view">
		<div
			style="height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;"
			id="sc_content">
			<table id="table_content" title="运单列表"
				class="table table-hover table-striped" style="table-layout: fixed;">
				<thead align="center">
					<tr class='table_head_line'>
						<td><input type="checkbox" id="checkAll"
							onclick="inverseCkb('ckb')" /></td>
						<td class="td_cs" style="width: 100px">序号</td>
						<td class="td_cs" style="width: 100px">运单状态</td>
						<td class="td_cs" style="width: 200px">运单号</td>
						<td class="td_cs" style="width: 200px">快递公司</td>
						<td class="td_cs" style="width: 200px">快递单号</td>
						<td class="td_cs" style="width: 200px">收货机构</td>
						<td class="td_cs" style="width: 200px">收货区域</td>
						<td class="td_cs" style="width: 200px">收货联系人</td>
						<td class="td_cs" style="width: 200px">收货联系电话</td>
						<td class="td_cs" style="width: 200px">收货地址</td>
						<td class="td_cs" style="width: 200px">总件数</td>
						<td class="td_cs" style="width: 200px">总毛重</td>
						<td class="td_cs" style="width: 200px">总体积</td>
						<td class="td_cs" style="width: 200px">总金额</td>
						<td class="td_cs" style="width: 200px">下单日期</td>
					</tr>
				</thead>
				<tbody align="center">
					<c:forEach items="${pageView.records }" var="records"
						varStatus='status'>
						<tr ondblclick='toUpdate("${records.id}")'>
							<td><input id="ckb" name="ckb" type="checkbox"
								value="${records.id}"></td>
							<td class="td_cs" style="width: 100px" title="${status.count }">${status.count }</td>
							<td class="td_cs" style="width: 100px" title="${records.status }">${records.status }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.order_id }">${records.order_id }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.expressCode }">${records.expressCode }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.waybill }">${records.waybill }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.to_orgnization }">${records.to_orgnization }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.to_province }">${records.to_province }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.to_contacts }">${records.to_contacts }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.to_phone }">${records.to_phone }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.to_address }">${records.to_address }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.total_qty }">${records.total_qty }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.total_weight }">${records.total_weight }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.total_volumn }">${records.total_volumn }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.total_amount }">${records.total_amount }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.order_time }">${records.order_time }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div style="margin-right: 1%; margin-top: 20px;">${pageView.pageView }</div>
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
</style>
