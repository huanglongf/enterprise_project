<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../../common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.dispatchList.title"/></title>

<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/seckillpickinglist-printopc.js' includeParams='none' encode='false'/>"></script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
    
<div id="div1">
    <form method="post" id="query-form">
        <span class="label" style="font-size: 15px;" >基础条件</span>
        <table id="filterTable">
            <tr>
                <td class="label">配货清单号：</td>
                <td>
                    <input loxiaType="input" name="pickingList.code" id="picklistcode" trim="true"/>
                </td>
                <td class="label">状态</td>
                <td>
                    <select loxiaType="select" name="pickingList.status">
                        <option value="PACKING">配货中</option>
                        <option value="FAILED_SEND">快递无法送达</option>
                        <option value="FAILED">配货失败</option>
                        <option value="WAITING">待配货</option>
                    </select>
                </td>
                <td class="label">物流服务商：</td>
				<td>
					<select loxiaType="select" name="pickingList.lpcode" id="selLpcode">
						<option value="">--请选择物流服务商--</option>
					</select>
				</td>
                <td class="label" style="color: red;">仓库</td>
                <td width="20%">
                    <select id="selTrans" loxiaType="select" loxiaType="select">
                        <option value="">--请选择仓库--</option>
                    </select>
                </td>
            </tr>
        </table>
    </form>
    <div class="buttonlist">
        <button type="button" id="btn-query" loxiaType="button" class="confirm"><s:text name="button.search"></s:text> </button>
    </div>
    <span>输入配货批次号回车进入明细页面</span>
    <table width="25%">
        <tr><td class="label" style="color:red" width="100px;">配货批次号：</td><td><input loxiaType="input" id="pCode" trim="true"/></td></tr>
    </table>
    <div id="div-waittingList">
        <table id="tbl-waittingList"></table>
        <div id="pager_query"></div>
    </div>
</div>
<div id="div2" class="hidden">
    <div id="pickingListId" class="hidden"></div>
    <input id="pickId" hidden></input>
    <input id="whid" hidden></input>
    <table width="90%">
        <tr>
            <td class="label"><s:text name="label.warehouse.pl.batchno"></s:text> </td>
            <td>
                <label id="dphCode"></label>
            </td>
            <td class="label"><s:text name="label.warehouse.pl.status"></s:text> </td>
            <td id="dphStatus"></td>
            <td class="label">物流</td>
            <td id="tdIdLpcode"></td>
        </tr>
        <tr>
            <td class="label"><s:text name="label.wahrhouse.sta.creater"></s:text> </td>
            <td width="20%" id="creator" ></td>
            <td class="label" class="15%"><s:text name="label.wahrhouse.sta.operator"></s:text> </td>
            <td id="operator"></td>
            <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <td class="label" width="15%"><s:text name="label.warehouse.pl.pltotal"></s:text> </td>
            <td width="15%" id="planBillCount"></td>
            <td class="label"><s:text name="label.warehouse.pl.prototal"></s:text> </td>
            <td id="planSkuQty"></td>
            <td class="label"><s:text name="label.wahrhouse.pl.picking.time"></s:text> </td>
            <td id="pickingTime"></td>
        </tr>
    </table>
    <br /><br />
    <div id="divTbDetial"><table id="tbl-orderDetail"></table><div id="pager"></div></div>
    <div id="btnlist">
        <div id="tabs" class="buttonlist">
            <ul>
                <li><a href="#tabs-1">单据打印</a></li>
                <li><a href="#tabs-2">物流面单号维护</a></li>
            </ul>
            <div id="tabs-1">
                <p>
                    <button loxiaType="button" class="confirm" id="btnSoInvoice"><s:text name="button.export.invoice"></s:text></button>
                    <button loxiaType="button" id="btnSoInvoiceZip"><s:text name="button.export.invoice"></s:text>压缩文件</button>
                    <button loxiaType="button" id="excelPickingList">配货清单导出EXCEL</button>
                </p>
                <p>
                    <button loxiaType="button" id="printPickingList" class="confirm"><s:text name="button.pl.print"/></button>
                    <button loxiaType="button" id="printPickingListNew"><s:text name="button.pl.print2"/></button>
                    <button loxiaType="button" class="confirm" id="printTrunkPackingList"><s:text name="button.pg.print"></s:text></button>
                </p>
                <p>
                    <button loxiaType="button" class="confirm" id="exportDeliveryInfo"><s:text name="button.trans.export"></s:text></button>
                    <button loxiaType="button" id="exportDeliveryInfoZip"><s:text name="button.trans.export"></s:text>压缩文件</button>
                    <button loxiaType="button" class="confirm" id="printDeliveryInfo"><s:text name="button.trans.print"></s:text></button>
                    <button loxiaType="button" class="hidden" id="emsPrintAll">ems面单打印</button>
                    <button loxiaType="button" id="back"><s:text name="button.back"></s:text> </button>
                </p>            
            </div>
            <div id="tabs-2">
                <div id="td1">
                    <span class="label">快速维护：<font style="color:red">单据核对前可以维护修改</font></span><br/><br/>
                    <span class="label">相关单据号:</span><input loxiaType="input" style="width:200px" id="sdSlipCode"></input><input loxiaType="input" hidden id="staId"></input><span class="label">物流面单号：</span><input id="transNo" loxiaType="input" style="width:200px"></input><button id="confirm" loxiaType="button" class="confirm">确认</button>
                    <br/><br/>
                    <span style="color:red" id="sum">0</span>单<button loxiaType="button" id="checkDetail">查看扫描明细</button><button loxiaType="button" id="saveScan">保存扫描结果</button>
                    <br/><br/>
                    <form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="tranFrame">
                        <button loxiaType="button" class="confirm" id="exportTrans">导出单号列表</button>
                        <input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
                        <input type="hidden" name="pickingListId" id="piid"/>
                        <input type="hidden" name="whid" id="whid"/>
                        <button loxiaType="button" class="confirm" id="inportTrans">导入物流面单</button>
                    </form>
                    <br/>
                    <button loxiaType="button" id="backto">返回</button>
                    <iframe id="tranFrame" name="tranFrame" class="hidden"></iframe>
                </div>
                <div id="td2" class="hidden">
                    <br/>
                    <span class="label">查看明细</span>
                    <br/><br/>
                    <span id="lpName" class="label"></span><span style="color:red" id="sum1">0</span>单
                    <br/><br/>
                    <table id="detailTable">
                    </table>
                    <br/>
                    <button loxiaType="button" class="confirm" id="saveStaTrans">保存</button><button loxiaType="button" id="reback">返回</button>
                </div>
            </div>
        </div>
    </div><br/><br/>
    <div id="errBtnlist" class="buttonlist">
        <button loxiaType="button" class="confirm" id="remFailed"><s:text name="button.pl.remove.failed"></s:text></button>
        <button loxiaType="button" class="confirm" id="occupiedInv"><s:text name="button.pl.reoccupied"></s:text> </button>
        <button loxiaType="button" id="plCancel"><s:text name="button.cancel"></s:text> </button>
        <button loxiaType="button" id="back2"><s:text name="button.back"></s:text></button>
    </div>
    <div id="errTransBtnlist" class="buttonlist">
        <button loxiaType="button" class="confirm" id="rmTransFailed">删除快递失败单据</button>
        <button loxiaType="button" class="confirm" id="tryTrans">再次尝试</button>
        <button loxiaType="button" id="back3">返回</button><br/><br/>
        选择快递
        <select name="sta.staDeliveryInfo.lpCode" id="selLpcode" loxiaType="select" style="width:80px;">
        </select>
        <button loxiaType="button" class="confirm" id="btnChgTrans">无法配送转物流</button>
    </div>
</div>
<div class="hidden">
    <OBJECT ID='emsObject' name='emsObject' CLASSID='CLSID:53C732B2-2BEA-4BCD-9C69-9EA44B828C7F' align=center hspace=0 vspace=0></OBJECT>
</div>
<iframe id="frmSoInvoice" class="hidden"></iframe>
<iframe id="exportFrame" class="hidden" target="_blank"></iframe>
<iframe id="upload" name="upload" style="display:none;"></iframe>
</body>
</html>