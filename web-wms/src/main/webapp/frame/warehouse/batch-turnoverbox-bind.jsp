<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title>批次周转箱绑定</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/batch-turnoverbox-bind.js' includeParams='none' encode='false'/>"></script>
<style>
.showborder {
	border: thin;
}
</style>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="div1">
		<span class="label" style="margin-left: 50px;">波次号:</span><input id="pickingListCode" loxiaType="input" style="width: 150px;height:25px" trim="true"/><br/><br/> 
	</div>
	<div id="div2">
		<div style="width:300px;float:left">
		    <span class="label" style="margin-left: 50px;">相关单据号:</span><input id="slipCode" loxiaType="input" style="width: 150px;height:25px" trim="true"/><br/><br/> 
		    <span class="label" style="margin-left: 50px;">周转箱条码:</span><input id="code" loxiaType="input" style="width: 150px;height:25px" trim="true"/>
	    </div>
	    <div style="width:1000px;height:80px;float:left;font-size:18px;font-weight:bold;color:red;line-height:80px;" id="infomsg"></div>
	    <div style="width:100%;float:left;padding-left:120px;padding-top:20px;">
	    	<button id="reback" type="button" loxiaType="button">返回</button>
	    </div>
	    <div style="float:left;margin-left:50px;color:red;width:100%">
	        <br/><br/>
	    	操作提示：<br/>
	    	页面进入时光标自动定位在相关单据号扫描框，扫描完再进入周转箱条码扫描框，绑定操作在周转箱条码扫描完之后触发。<br/>
	        1、当手动操作先输入周转箱条码时，如果相关单据号没有扫描，则提示要扫描相关单据号，并且执行光标定位。<br/>
	        2、按照正确顺序扫描后，如果绑定成功，不做任何提示，直接清空进入下次绑定扫描<br/>
	        3、按照正确顺序扫描后，如果绑定不成功，只在界面提示错误信息，然后还是直接清空进入下次绑定扫描<br/>
	    </div>
    </div>
    <div id="div3">
		<div style="width:300px;float:left">
		    <span class="label" style="margin-left: 50px;">仓库区域编码:</span><input id="zoonCode" loxiaType="input" style="width: 150px;height:25px" trim="true"/><br/><br/> 
		    <span class="label" style="margin-left: 50px;">周转箱条码:</span><input id="boxCode" loxiaType="input" style="width: 150px;height:25px" trim="true"/>
		    <div loxiaType="edittable" >
				<table operation="add,delete" id="barCode_tab" width="100%">
					<thead>
						<tr>
							<th width="20%"><input type="checkbox"/></th>
							<th>周转箱条码</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
					<tbody>
						<tr class="add" index="(#)">
							<td><input type="checkbox"/></td>
							<td><input loxiatype="input" readonly="readonly" mandatory="true" name="addList(#).barcode" class="code"/></td>
						</tr>
					</tbody>
					<tfoot></tfoot>
				</table>
			</div>
			<div class="buttonlist">
				<button type="button" loxiaType="button" class="confirm" id="zoonOver" >区域绑定完成 </button>
				<%-- <button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button> --%>
		    	<button id="reback2" type="button" loxiaType="button">返回</button>
			</div>
	    </div>
	    <div style="width:1000px;height:80px;float:left;font-size:18px;font-weight:bold;color:red;line-height:80px;" id="moreInfomsg"></div>
	    <div style="width:100%;float:left;padding-left:120px;padding-top:20px;">
	    </div>
	    <div style="float:left;margin-left:50px;color:red;width:100%">
	        <br/><br/>
	    	操作提示：<br/>
	    	页面进入时光标自动定位在仓库区域扫描框，扫描完再进入周转箱条码扫描框。<br/>
	        1、当手动操作先输入周转箱条码时，如果仓库区域没有扫描，则提示要扫描仓库区域编码，并且执行光标定位。<br/>
	        2、按照正确顺序扫描后，不做任何提示，直接清空进入下次周转箱扫描<br/>
	        3、如果在周转箱条码框里输入‘OK’，则页面会自动触发点击‘区域绑定完成’事件，对此仓库区域下的周转箱绑定<br/>
	    </div>
    </div>
    <div id="dialogInfoMsg" align="center">
		<div id="infoMsg" style="width:100%;color:red;font-size:20px;text-align:center;"></div>
		<table>
			<tr>
				<td valign="top">
					<button id="btnInfoOk" type="button" loxiaType="button" class="confirm">确认</button>
				</td>
				<td valign="top">
					<input id="txtInfoOk" loxiaType="input"  placeholder="请扫描OK..." style="width: 50px;height:20px"></input>
				</td>
			</tr>
		</table>
	</div>
	<div id="dialogInfoMsg_More" align="center">
		<div style="width:100%;color:red;font-size:20px;text-align:center;">确认要绑定周转箱并将此仓库区域设为拣货完成吗？</div>
		<table>
			<tr>
				<td valign="top">
					<button id="btnInfoOk_More" type="button" loxiaType="button" class="confirm">确认</button>
				</td>
				<td valign="top">
					<input id="txtInfoOk_More" loxiaType="input"  placeholder="请扫描OK..." style="width: 50px;height:20px"></input>
				</td>
				<td>
					<button type="button" loxiaType="button" id="close_More">关闭 </button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>