<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title>批次周转箱绑定</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/release-turnoverbox-bypc.js' includeParams='none' encode='false'/>"></script>
<style>
.showborder {
	border: thin;
}
</style>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="div1">
		<span class="label" style="margin-left: 50px;font-size:30px;">波次号:</span><input id="pickingListCode" loxiaType="input" style="width:400px;height:40px;font-size:30px;" trim="true"/><br/><br/> 
	    <div style="float:left;margin-left:50px;color:red;width:100%;font-size:20px;">
	        <br/><br/>
	    	操作提示：<br/>
	    	进入页面光标自动定位，扫描波次号(手动输入要按回车)系统自动进行释放<br/>
	    	成功直接进入下一单，不做提示！<br/>
	    	失败要扫OK确认<br/>
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
</body>
</html>