<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/meta.jsp"%>
    <link rel="stylesheet" type="text/css" href="<s:url value='/css/bootstrap-theme.min.css' includeParams='none' encode='false'/>" />
    <link rel="stylesheet" type="text/css" href="<s:url value='/css/bootstrap.min.css' includeParams='none' encode='false'/>" />
    <script type="text/javascript" src="<%=request.getContextPath() %>/scripts/main/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/scripts/main/jquery-barcode.js"></script>
    <style>

        body {
            background-color: #c6edcc;
        }

        td {
            word-break: break-all;
            word-wrap: break-word;
        }


    </style>
    <title>rfid打印</title>
    <script type="text/javascript" src="<s:url value='/frame/rfid/print_rfid.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body contextpath="<%=request.getContextPath()%>">


<div >
    <table style="width: 80%;height: 100%;border-collapse:separate; border-spacing:0px 30px;" align="center" >
        <tr>
            <td >
                <label style="font-size: 20px">请输入打印数量：</label>
                <input type="text" id="printcount"  style="width:200px;height: 40px" value="1" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>&nbsp;双
            </td>

        </tr>
        <tr>
            <td>
                <label style="font-size: 20px">请扫描商品条码BARCODE：</label>
                <input type="text" id="barcode"  style="width:200px;height: 40px"  />
            </td>

        </tr>

        <tr>
            <td>
                <button  type="button" class="btn btn-primary btn-l" onclick="printRfid()">打印</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <button  type="button" class="btn btn-primary btn-l" onclick="back()">返回</button>
            </td>
        </tr>
        <tr>
            <td>
                <label id="tipshow" style="font-size: 20px;color: red"></label>
            </td>
        </tr>
    </table>
</div>

</body>
<%@include file="../../common/include-opencv.jsp"%>
</html>