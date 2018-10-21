<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/lmis/yuriy.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <script type= "text/javascript" src= "<%=basePath %>js/selectFilter.js" ></script> --%>
<div id= "data" class= "form-group" >
<div  class="title_div" id="sc_title">		
<table class="table table-striped" style="table-layout: fixed;">
		   		<thead>
			  		<tr>
			  			<td class="td_ch"><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/></td>
			  			<td class="td_cs">运单号</td>
			  			<td class="td_cs">错误信息</td>
			  			<td class="td_cs">出库时间</td>
			  			<td class="td_cs">店铺名称</td>
			  			<td class="td_cs">仓库名称</td>
<!-- 			  			<td class="td_cs">始发地</td> -->
			  			<td class="td_cs">目的地省</td>
			  			<td class="td_cs">目的地市</td>
			  			<td class="td_cs">目的地区</td>
			  			<td class="td_cs">目的地详细地址</td>
<!-- 			  			<td class="td_cs">操作</td> -->
<!-- 			  			<td class="td_cs"></td> -->
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
			  			<td class="td_cs" title="${res.express_number}">${res.express_number}</td>
			  			<td class="td_cs" title="${res.pro_remark}">${res.pro_remark}</td>
			  			<td class="td_cs" title="${res.transport_time}">${res.transport_time}</td>
			  			<td class="td_cs" title="${res.store_name}">${res.store_name}</td>
			  			<td class="td_cs" title="${res.warehouse_name}">${res.warehouse_name}</td>
<%-- 			  			<td class="td_cs"><input id="ip1_${res.id}" type="text" readonly="readonly" value="${res.delivery_address}"/></td> --%>
			  			<td class="td_cs"><input id="ip2_${res.id}" type="text" readonly="readonly" value="${res.province}"/></td>
			  			<td class="td_cs"><input id="ip3_${res.id}" type="text" readonly="readonly" value=" ${res.city}"/></td>
			  			<td class="td_cs"><input id="ip4_${res.id}" type="text" readonly="readonly" value="${res.state}"/></td>
			  			<td class="td_cs"><input id="ip5_${res.id}" type="text" readonly="readonly" value="${res.address}"/></td>
<!-- 			  			<td class="td_cs"> -->
<%-- 			  			<button class="btn btn-xs btn-info" onclick="upStatus('${res.id}','0');">修改</button> --%>
<%-- 			  			<button class="btn btn-xs btn-info" onclick="upStatus('${res.id}','1');">保存</button> --%>
<!-- 			  			</td> -->
<!-- 			  			<td class="td_cs"> -->
<%-- 			  				<button class="btn btn-xs btn-info" onclick="add_error_address('${res.id}');">添加解析记录</button> --%>
<!-- 			  			</td> -->
			  		</tr>
		  		</c:forEach>
		  		</tbody>
</table>
</div>
		<!-- 分页添加 -->
   <div style="margin-right: 10%;margin-top:20px;margin-bottom: 10%;">${pageView.pageView}</div>		
</div>





<!-- <!-- 店铺编辑页面弹窗 --> 
<!-- 		<div id= "address_form" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" > -->
<!-- 			<div class= "modal-dialog modal-lg" role= "document" > -->
<!-- 				<div class= "modal-content" style= "border: 3px solid #394557;" > -->
<!-- 					<div class= "modal-header" > -->
<!-- 						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button> -->
<!-- 						<h4 id= "formLabel" class= "modal-title" >添加地址解析记录</h4> -->
<!-- 					</div> -->
<!-- 					<div class= "modal-body" > -->
<!-- 						<input id= "id_param" name= "id_param" type= "hidden" /> -->
               
<!-- 						<div class= "form-group" > -->
<!-- 				    		<label class= "blue" for= "client_form" >目的地省&nbsp;:<span id="province_lable"  style="color: red;"></span></label> -->
<!-- 				    		<input id= "province_input" name= "standard_time_form" class= "form-control" type= "text"  placeholder="请填写正确的省"/> -->
<!-- 				    	</div> -->
				    	
<!-- 						<div class= "form-group" > -->
<!-- 				    		<label class= "blue" for= "standard_time_form" >目的地市&nbsp;:<span id="city_lable" style="color: red;"></span></label> -->
<!-- 				    		<input id= "city_input" name= "standard_time_form" class= "form-control" type= "text" placeholder="请填写正确的市"/> -->
<!-- 				    	</div> -->
				    	
<!-- 						<div class= "form-group" > -->
<!-- 				    		<label class= "blue" for= "plan_time_form" >目的地区&nbsp;:<span id="state_lable" style="color: red;"></span></label> -->
<!-- 				    		<input id= "state_input" name= "plan_time_form" class= "form-control" type= "text" placeholder="请填写正确的区"/> -->
<!-- 				    	</div> -->
				    	
<!-- 		             <div class= "form-group" > -->
<!-- 				    		<label class= "blue" for= "client_form" >详细地址&nbsp;:<span id="detail_lable" style="color: red;"></span></label> -->
<!-- 				    		<input id= "detail_input"  readonly="readonly" name= "standard_time_form" class= "form-control" type= "text" value=""/> -->
<!-- 				    	</div> -->
				    					    	
<!-- 					</div> -->
<!-- 					<div class= "modal-footer" > -->
<!-- 						<button id= "btn_back" type= "button" class= "btn btn-default" data-dismiss="modal" > -->
<!-- 							<i class= "icon-undo" aria-hidden= "true" ></i>返回 -->
<!-- 						</button> -->
<!-- 		     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="save_address();" > -->
<!-- 		     				<i class="icon-save" aria-hidden= "true" ></i><span id="btn_type_text">保存</span> -->
<!-- 		     			</button> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
		
	