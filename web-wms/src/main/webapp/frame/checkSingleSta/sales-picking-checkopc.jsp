<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.genDph.title"/></title>
<script type="text/javascript" src="<s:url value='/frame/checkSingleSta/sales-picking-checkopc.js' includeParams='none' encode='false'/>"></script>
<style type="text/css">
    .tbMsg td{
        font-size : 20px;
    };
</style>

</head>
<body contextpath="<%=request.getContextPath() %>"> 
<div id="plDiv">
    <form id="plForm" name="plForm">
        <input type="hidden" name="cmd.sortingModeInt" value="1"/>
        <table width="70%">
            <tr>
                <td class="label" width="10%"><s:text name="label.warehouse.pl.whname"/></td>
                <td width="20%">
                    <select id="selTrans" loxiaType="select" loxiaType="select" name="ou.id">
                        <option value="">--请选择仓库--</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="label"  width="10%"><s:text name="label.warehouse.pl.batchno"/></td>
                <td width="20%"><input loxiaType="input" name="cmd.code" id="code" trim="true"/></td>
                <td class="label" width="10%"><s:text name="label.warehouse.pl.status"/></td>
                <td width="20%">
                    <s:select list="plStatus" loxiaType="select" listKey="optionKey" listValue="optionValue" name="cmd.intStatus" id="status" headerKey="" headerValue="%{pselect}"/>
                </td>
            </tr>
            <tr>
                <td class="label"><s:text name="label.warehouse.pl.createtime"/></td>
                <td colspan="3">
                    <input loxiaType="date" style="width: 150px" name="cmd.pickingTime1" id="pickingTime" showTime="true"/>
                    <s:text name="label.warehouse.pl.to"/>
                    <input loxiaType="date" style="width: 150px" name="cmd.executedTime1" id="executedTime" showTime="true"/>
                </td>
            </tr>
        </table>
    </form>
    <div class="buttonlist">
        <button id="search" type="button" loxiaType="button" class="confirm"><s:text name="button.query"/></button>
        <button id="reset" type="reset" loxiaType="button"><s:text name="button.reset"/></button>
    </div>
    <br/>
        批次号扫描：<input id="quickPl" loxiaType="input" style="height:26px;font-size:24px;width: 300px;" trim="true"/>
    <br/>
    <br/>
    <table id="tbl-dispatch-list"></table>
    <div id="pager"></div>
</div>
<div id="checkDiv" class="hidden">
    <table width="700px">
        <tr>
            <td class="label"><s:text name="label.warehouse.pl.batchno"/><input type="hidden" id="verifyPlId"/></td>
            <td width="15%" id="verifyCode"></td>
            <td class="label"><s:text name="label.warehouse.pl.status"/></td>
            <td width="15%" id="verifyStatus"></td>
            <td class="label">&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td class="label"><s:text name="label.warehouse.pl.pltotal"/></td>
            <td id="verifyPlanBillCount"></td>
            <td class="label"><s:text name="label.warehouse.pl.plcomplete"/></td>
            <td id="verifyCheckedBillCount"></td>
            <td class="hidden"><s:text name="label.warehouse.pl.plship"/></td>
            <td id="verifyShipStaCount" class="hidden"></td>
        </tr>
        <tr>
            <td class="label"><s:text name="label.warehouse.pl.prototal"/></td>
            <td id="verifyPlanSkuQty"></td>
            <td class="label"><s:text name="label.warehouse.pl.procomplete"/></td>
            <td id="verifyCheckedSkuQty"></td>
            <td class="hidden"><s:text name="label.warehouse.pl.proship"/></td>
            <td id="verifyShipSkuQty" class="hidden"></td>
        </tr>
    </table>
    <br/>
    <table width="100%">
        <tr>
            <td class="label" width="20%" >条码扫描</td>
            <td width="30%">
                <input id="skuBarCode" loxiaType="input"  trim="true" style="height:34px;font-size:32px;width: 300px;"/>
            </td>
            <td class="label"  width="50%"   id="snDiv">
                SN号&nbsp;&nbsp;<input id="snCode" loxiaType="input"  trim="true" style="height:34px;font-size:32px;width: 300px;" />
            </td>
        </tr>
    </table>
    <br/>
    <table id="tbl-sta-list"></table>
    <br/><br/>
    <button id="btnTryError" loxiaType="button" >异常再次核对</button>
    <button id="idBack" loxiaType="button" ><s:text name="button.toback"/></button>
</div>

    <div id="dialog_msg">
        <input type="hidden" id="hidStaId" value="">
        <input type="hidden" id="hidSn" value="">
        <table width="90%" align="center" class="tbMsg">
            <tr>
                <td class="label" width="25%">
                    相关单据号
                </td>
                <td id="tbSlipCode" width="25%">
                </td>
                <td class="label" width="25%">
                    作业单号
                </td>
                <td id="tbStaCode" width="25%">
                </td>
            </tr>
            <tr>
                <td class="label" colspan="2">
                    箱号
                </td>
                <td id="tbPgIndex" colspan="2" style="color: red;font-size: 42px;">
                </td>
            </tr>
            <tr>
                <td class="label" colspan="2">
                    相关单据号/作业单号
                </td>
                <td colspan="2">
                    <input loxiaType="input" name="slipCode" id="idSlipCode" trim="true" style="height:34px;font-size:32px;" />
                </td>
            </tr>
            <tr>
                <td class="label" colspan="2">
                    快递单号
                </td>
                <td colspan="2">
                    <input loxiaType="input" name="txtTkNo" id="txtTkNo" trim="true" style="height:34px;font-size:32px;" />
                </td>
            </tr>
            <tr>
                <td id="tknoErrorMsg" colspan="4" align="center" style="color: red;">
                </td>
            </tr>
        </table>
        <div class="buttonlist" align="center">
            <button loxiaType="button" class="confirm" id="btnConfirm">核对</button>
            确认条码：<input id="confirmBarCode" loxiaType="input" style="width: 100px" trim="true"/>
            <button loxiaType="button"  id="close">关闭</button>
        </div>
    </div>
  
    <div id="dialog_error">
        <div id="errorMsg" style="font-size:32px" align="center">
        </div>
        <div id="divAllChecked1" class="hidden"  style="font-size:32px;color: red;" align="center">
            该批次已经核对完成
        </div>
        <div class="buttonlist" align="center">
            <button loxiaType="button" class="confirm" id="btnConfirm1">确认</button>
            确认条码：<input id="confirmBarCode1" loxiaType="input" style="width: 100px" trim="true"/>
        </div>
    </div>
    
    <div id="dialog_success_msg">
        <div style="font-size:32px" align="center">
            执行成功
        </div>
        <div id="divAllChecked"  class="hidden"  style="font-size:32px;color: red;" align="center">
            该批次已经核对完成
        </div>
        <div class="buttonlist" align="center">
            <button loxiaType="button" class="confirm" id="btnConfirm4">确认</button>
            确认条码：<input id="confirmBarCode4" loxiaType="input" style="width: 100px" trim="true"/>
        </div>
    </div>
    
    <div id="dialog_error_ok">
		<div id="error_text_ok" ></div>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="dialog_error_close_ok">关闭</button>
		</div>   
	</div>
</body>
</html>
