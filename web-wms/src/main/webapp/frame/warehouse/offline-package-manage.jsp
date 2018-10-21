<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title>线下包裹管理</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/offline-package-manage.js"' includeParams='none' encode='false'/>"></script>
<style>
.showborder {
	border: thin;
}
</style>
<style>
.ui-icon ui-icon-closethick{
    display: none;
}
</style>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="div2" style="float: left;" >
	 	 <table>
	 		  <tr>
			   	<td class="label">仓库名称:</td>
			  	<td><input id="ouId" value="" name="transOrder.ouId" disabled="disabled"/></td>
			  </tr>
			   <tr>
			   	<td class="label">备注:</td>
			  	<td><textarea style="height: 50px;" id="remark" name="transOrder.remark"></textarea></td>
			  </tr>
			  <tr>
			   	<td class="label">业务类型:</td>
			    <td><select loxiaType="select" name="transOrder.businessType" style="width: 100px;" id="businessType" onchange="businessType(this.id)">
				</select>
				</td>
		      </tr>
		</table> 
   <br/>
   	作业指令信息 <div id="button" style="display: inline;"><button loxiaType="button"  onclick="addNewRow()" id="addNewRow"  class="confirm">新增</button><button  onclick="removeRow()" id="removeRow" loxiaType="button" class="confirm">删除</button></div> 
  	<table id="myTab" style="border: 1px solid black" >
  		<tr>
  			<td class="label"><input type="checkbox"  id="allCheckBox"/></td>
  			<td style="width: 150px;font-weight: bold; background-color: #efefef;" 	align="center">指令类型</td>
  			<td  style="width: 100px;font-weight: bold; background-color: #efefef;" align="center">作业指令</td>
  		</tr>
  	</table>
  	<button id="next2" loxiaType="button" class="confirm">下一步</button>
  	</div>
  	<br/>
  	<div id="div3" style="float: left;display: none;">
  	成本中心和寄件人信息
  	 <table style="border: 1px solid black">
  	 	<tr>
  	 	<td class="label">成本中心类型</td>
  	 	<td>	
 			<select id="centerType" loxiaType="select" name="transOrder.costCenterType" style="width: 100px;"onchange="centerType(this.id)">
 				<option value=""><s:text name="label.wahrhouse.sta.select"></s:text></option>
				<option value="1">部门</option>
				<option value="2">店铺</option>
			</select>
		</td>
  	 	</tr>
  	 	<tr>
  	 	<td class="label">部门/店铺</td>
  	 	<td>	
 			<select id="departmentshop" loxiaType="select" name="transOrder.costCenterDetail" style="width:120px;" onchange="departmentShop(this.id)">
			<%-- 	<option value=""><s:text name="label.wahrhouse.sta.select"></s:text></option> --%>
			</select>
				<button type="button" loxiaType="button" class="confirm" id="btnSearchShop">查询</button>
		</td>
  	 	</tr>
  	 	<tr>
  	 	<td class="label">寄件人</td>
  	 	<td>	
 			<input loxiaType="input" id="sender"  name="transOrder.sender"  placeholder="姓名" style="width:100px;"/>
 			<input loxiaType="input" id="senderTel"  name="transOrder.senderTel"  placeholder="手机号码" style="width:120px;"/>
		</td>
  	 	</tr>
  	    <tr>
  	   		<td class="label"></td>
  	    	<td>
  	    		<input loxiaType="input" id="senderTel2"  name="transOrder.senderTel2"  placeholder="固定电话" style="width:220px;"/>
  	   		</td>
  	    </tr>
  <!-- 	 	<tr>
  	 	<td class="label">寄件地址</td>
  	 	<td>	
 			<input loxiaType="input"  placeholder="中国" style="width: 25px;"/>
 			<input loxiaType="input"  placeholder="省" style="width: 25px;"/>
 			<input loxiaType="input"  placeholder="市" style="width: 25px;"/>
 			<input loxiaType="input"  placeholder="区" style="width: 70px;"/>
		</td>
  	 	</tr> -->
  	 	<tr>
  	 	<td></td>
  	 	<td>	
 			<input loxiaType="input" id="sAddress" name="transOrder.sAddress" placeholder="详细地址" style="width:220px;"  disabled="true"/>
		</td>
  	 	</tr>
  	 </table>
  	   <button id="next3" loxiaType="button" class="confirm">下一步</button>
  	</div>
    <br/>
    <div id="div4" style="float: left;display: none;">
    	物流和包裹信息
  	 <table style="border: 1px solid black">
  	 	<tr>
  	 	<td class="label">物流服务商</td>
  	 	<td>	
 			<select loxiaType="select" id="selectLpCode" name="transOrder.transportatorCode" style="width: 100px;" onchange="LpCode(this.id)" >
 						<option value="">请选择</option>
						<option value="EMS">EMS速递</option>
						<!-- <option value="EMS-COD">EMS-COD</option>
						<option value="SF-EXPRESS">SFHK</option> -->
						<option value="JD">京东快递</option>
						<option value="SFDSTH">顺丰电商特惠</option>
						<option value="SF">顺丰快递</option>
						<option value="YTO">圆通快递</option>
						<option value="STO">申通快递</option>
						<option value="中铁物流">中铁物流</option>
						<option value="V_BAISHI">百世物流</option>
						<option value="KY">跨越物流</option> 
						<option value="V_XINYI">心怡物流</option> 
						<!-- 
						<option value="虹迪物流">虹迪物流</option>
						<option value="新越物流">新越物流</option> -->
 			
 				<!-- <option value="">请选择</option>
 				<option value="SF">顺丰快递</option>
 				<option value="SFDSTH">顺丰电商特惠</option>
 				<option value="SF1">顺丰特惠1</option>
 				<option value="STO">申通快递</option>
 				<option value="YTO">圆通快递</option>
 				<option value="EMS">EMS速递</option>
 				<option value="WX">万象</option>
 				<option value="WX1">京东快递1</option>
 				<option value="中铁物流">中铁物流1</option>
 				<option value="其他物流资源">其他物流资源1</option>
 				<option value="SFHK">SF-EXPRESS</option>
 				<option value="SFHK1">顺丰快递-COD1</option>
 				<option value="SFHK11">顺丰国际1</option>
 				<option value="SFHK111">百世物流1</option>
 				<option value="SFHK1111">跨越物流1</option> -->
			</select> 
			<!-- <button type="button" loxiaType="button" class="confirm" id="btnSearchShop22">查询</button> -->
		</td>
  	 	</tr>
  	 	<tr>
  	 	<td id="timeType2" class="label" style="display: none;">时效类型</td>
  	 	<td>	
 			<select loxiaType="select" id="timeType" name="transOrder.timeType" style="width: 100px;" style="display: none;">
 			   <!--  <option value="">请选择</option> -->
				<!-- <option value="1">普通</option>
				<option value="3">及时达</option>
				<option value="5">当日</option>
				<option value="6">次日</option> -->
			</select> <input id="isLandTrans" type="checkbox"/>是否陆运
		</td>
  	 	</tr>
  	 	<tr>
  	 	<td class="label">保价范围</td>
  	 	<td>	
 			<input loxiaType="input"  id="priceRange" value=""  disabled="true" />
 		</td>
  	 	</tr>
  	 	<tr>
  	 	<td class="label">保价金额</td>
  	 	<td>	
 			<input loxiaType="input" id="price" name="transOrder.insuranceAmount"  value=""/>
 		</td>
  	 	</tr>
  	 	<tr>
  	 	<td class="label">包裹数量</td>
  	 	<td>	
 			<input loxiaType="input" id="packageNum" name="transOrder.packageNum" />
 		</td>
  	 	</tr>
  	 	<tr>
  	 	<td class="label">收货人</td>
  	 	<td>	
 			<input loxiaType="input" id="receiver"  placeholder="姓名" style="width: 120px;" name="transOrder.receiver"/>
 			<input loxiaType="input" id="rTel" placeholder="手机号码" style="width: 100px;" name="transOrder.rTel"/>
 			<input type="text" id="yrTel" value=""   style="display: none;"/>
 		    <button   id="updateRel" loxiaType="button" class="confirm"   style="display: none;">修改手机</button>
 		   
		</td>
  	 	</tr>
  	 	 <tr>
  	   		<td class="label"></td>
  	    	<td>
  	    		<input loxiaType="input" id="rTel2"  name="transOrder.rTel2"  placeholder="固定电话" style="width:220px;"/>
  	   		</td>
  	    </tr>
  	 		<tr>
  	 	<td class="label">收件地址</td>
  	 	<td>	
 			<input loxiaType="input" id="rCountry"  style="width: 50px;" name="transOrder.rCountry" value="中国" readonly="readonly"/>
 			<input loxiaType="input" id="rProvince" placeholder="省" style="width: 60px;" name="transOrder.rProvince"/>
 			<input loxiaType="input" id="rCity" placeholder="市" style="width: 60px;" name="transOrder.rCity"/>
 			<input loxiaType="input" id="rArea" placeholder="区" style="width: 60px;" name="transOrder.rArea"/>
		</td>
  	 	</tr>
  	 	<tr>
  	 	<td></td>
  	 	<td>	
 			<input loxiaType="input" id="rAddress" placeholder="详细地址" style="width:220px;" name="transOrder.rAddress"/>
		</td>
  	 	</tr>
  	 </table>
  	   <button type="button" id="weightOutbound" loxiaType="button" class="confirm">称重出库</button>
    </div>
<div id="oneHandDiv" style="margin-left: 30%;">
<span class="label">本账号下当前未交接运单数:</span><span id="num" style="color:red;font-size:16px;"></span><span class="label">交接上限:</span><span id="maxNum" style="color:red;font-size:16px;"></span><br>
<span class="label">物流服务商:</span><select style="width:200px" class="special-flexselect" id="selTrans2" name="lpcode2" data-placeholder="*请输入物流信息"></select>
<div style="display:inline-block"  id="showShopList"></div><input type="hidden" value="" id="postshopInput" /><input type="hidden" value="" id="postshopInputName" />
<br><button loxiaType="button" class="confirm" id="oneHandoverPrint">一键交接并打印交接清单</button><button loxiaType="button" class="confirm" id="oneHandover">一键交接</button>
</div>
 	<jsp:include page="/common/include/include-offline-weight.jsp"></jsp:include>
    <jsp:include page="/common/include/include-shop-query2.jsp"></jsp:include>
    <jsp:include page="/common/include/include-department-query.jsp"></jsp:include>
</body>
</html>