<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
   <%@ include file="/base/yuriy.jsp" %>
    <title>Packaged Composite System</title>
    <link href="<%=basePath%>print/printShou.css" rel="stylesheet" type="text/css" media="print"/>
    <script src="<%=basePath%>assets/js/jquery-1.10.2.min.js"></script>
    <script src="<%=basePath%>print/jquery-migrate-1.2.1.min.js"></script>
    <script language="javascript" src="<%=basePath%>print/jquery.jqprint-0.3.js"></script>

    <script type="text/javascript">
        function printBarcode() {
            $("#printDiv").jqprint({
                debug: false, //如果是true则可以显示iframe查看效果（iframe默认高和宽都很小，可以再源码中调大），默认是false
                importCSS: true, //true表示引进原来的页面的css，默认是true。（如果是true，先会找$("link[media=print]")，若没有会去找$("link")中的css文件）
                printContainer: true, //表示如果原来选择的对象必须被纳入打印（注意：设置为false可能会打破你的CSS规则）。
                operaSupport: true
            });
        }
    </script>
</head>
<body onload="loadding();">
<input type="button" value="print" onclick="printBarcode()">
<div id="printDiv">
    <div>
		<table class="tbale" cellspacing="0" cellpadding="0"  style=" border:2px  solid #000;  " height="100%"  width="100%">
					<tr height="9px">
						<td width="120px" style=" border:1px  solid #000; border-right: #ff0000;" colspan="2" height="36px">
							<img alt=""  src="${root}onebarcode/sf.jpg" style="width: 100px;height: 30px;">
						</td>
						<td width="176px" style=" border:1px  solid #000; border-right: #ff0000; border-left: #ff0000;" colspan="2" height="36px"></td>
						<td width="104px" style=" border:1px  solid #000; border-left: #ff0000;" colspan="2" height="36px"></td>
					</tr>
					<tr height="15px;">
						<td style="border:1px  solid #000;width: 296px; height:90px; word-wrap:break-word;" colspan=4 rowspan=2">
						<div align="center">
							<img alt=""  src="${root}onebarcode/${queryParam.waybill}.png" style="width: 240px;height: 60px;">
						</div>
					  		<div style="font-size:10px; " align="center">运单号：${queryParam.waybill}</div>
						</td>
						<td width="96px"  style=" border:1px  solid #000; " height="20px" colspan="2">
							<div  style="font-family:'黑体'; font-size:12px; font-weight:bold;" align="center"  > ${queryParam.producttype_name}</div>
						</td>
					</tr>
					<tr>
						<td width="96px" style=" border:1px  solid #000; " colspan="2" height="70px">
						</td>
					</tr>
					<tr >
						<td width="24px" style=" border:1px  solid #000; "  height="40px">
							<div  style="font-size:8px; " align="center"  >目</div>
							<div  style="font-size:8px; " align="center"  >的</div>
							<div  style="font-size:8px; " align="center"  >地</div>
						</td>
						<td  width="372px"  style=" border:1px  solid #000; " colspan="5" height="40px;">
							<div style="font-family:'黑体'; font-size:42px; font-weight:bold;" >${queryParam.mark_destination }</div>
						</td>
					</tr>
					<tr >
						<td width="24px" style=" border:1px  solid #000; "  height="45px">
							<div  style="font-size:8px; " align="center"  >收</div>
							<div  style="font-size:8px; " align="center"  >件</div>
							<div  style="font-size:8px; " align="center"  >人</div>
						</td>
						<td  width="372px"  style=" border:1px  solid #000; " colspan="5" height="45px;" valign="top">
							<div style="font-family:'黑体'; font-size:14px; font-weight:bold;" >${queryParam.to_contacts }&nbsp;&nbsp;&nbsp;${queryParam.to_phone }</div>
							<div style="font-family:'黑体'; font-size:14px; font-weight:bold;" >${queryParam.to_province }省${queryParam.to_city }市${queryParam.to_state }${queryParam.to_address }</div>
						</td>
					</tr>
					<tr >
						<td width="24px" style=" border:1px  solid #000; "  height="40px">
							<div  style="font-size:8px; " align="center"  >寄</div>
							<div  style="font-size:8px; " align="center"  >件</div>
							<div  style="font-size:8px; " align="center"  >人</div>
						</td>
						<td  width="278px"  style=" border:1px  solid #000; " colspan="3" height="40px;" valign="top">
							<div style=" font-size:8px; " >${queryParam.from_contacts }&nbsp;&nbsp;&nbsp;${queryParam.from_phone }</div>
							<div style=" font-size:8px; " >${queryParam.from_province }省${queryParam.from_city }市${queryParam.from_state }${queryParam.from_address }</div>
						</td>
						<td width="96px"  style=" border:1px  solid #000; " height="40px" colspan="2" align="center">
							<div  style="font-family:'黑体'; font-size:18px; font-weight:bold;" align="center"  >定时派送</div>
							<div  style="font-family:'黑体'; font-size:14px; font-weight:bold;" align="center"  >自寄&nbsp;自取</div>
						</td>
					</tr>
					<tr >
						<td width="120px" style=" border:1px  solid #000; " colspan="6"  height="45px">
						<table>
							<tr>
								<td style="width: 133px;">
									<div  style="font-size:8px; " >付款方式：寄付月结</div>
									<div  style="font-size:8px; ">月结账号：&nbsp;&nbsp;&nbsp;</div>
									<div  style="font-size:8px; ">实际重量：${queryParam.total_weight }</div>
									<div  style="font-size:8px; ">计费重量：${queryParam.total_weight }</div>
								</td>
								<td style="width: 133px;">
									<div  style="font-size:8px; ">声明价值：&nbsp;&nbsp;&nbsp;</div>
									<div  style="font-size:8px; ">保价费用：&nbsp;&nbsp;&nbsp;</div>
									<div  style="font-size:8px; ">总价值：${queryParam.total_amount }</div>
									<div  style="font-size:8px; ">总数量：${queryParam.total_qty }</div>
								</td>
								<td style="width: 133px;">
									<div  style="font-size:8px; ">报关方式：&nbsp;&nbsp;&nbsp;</div>
									<div  style="font-size:8px; ">运费：&nbsp;&nbsp;&nbsp;</div>
									<div  style="font-size:8px; ">费用合计：&nbsp;&nbsp;&nbsp;</div>
									<div  style="font-size:8px; ">&nbsp;</div>
								</td>
								
							</tr>
						</table>
							
						</td>
					</tr>
					<tr >
						<td width="24px" style=" border:1px  solid #000; "  height="40px">
							<div  style="font-size:8px; " align="center"  >寄</div>
							<div  style="font-size:8px; " align="center"  >托</div>
							<div  style="font-size:8px; " align="center"  >物</div>
						</td>
						<td  width="278px"  style=" border:1px  solid #000; " colspan="5" height="40px;" valign="top">
							<table>
								<tr>
									<td valign="top">
									<div  style="font-size:8px; ">
										<c:forEach  items="${list}" var="list">
											<c:if test="${not empty list.sku_name }">商品名称：${list.sku_name}&nbsp;&nbsp;&nbsp;&nbsp;</c:if><c:if test="${not empty list.qty }">件数：${list.qty}&nbsp;&nbsp;&nbsp;&nbsp;</c:if><c:if test="${not empty list.weight}">重量：${list.weight}&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
											<%-- 商品名称：${list.sku_name}&nbsp;&nbsp;&nbsp;&nbsp;件数：${list.qty}&nbsp;&nbsp;&nbsp;&nbsp; --%>
										</c:forEach>
									</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr >
						<td width="24px" style=" border:1px  solid #000; "  height="40px">
							<div  style="font-size:8px; " align="center"  >备</div>
							<div  style="font-size:8px; " align="center"  >注</div>
						</td>
						<td  width="178px"  style=" border:1px  solid #000; " colspan="2" height="40px;" valign="top">
							<div style=" font-size:8px; " >
								<c:if test="${not empty queryParam.customer_number }">
								   客户订单号: ${queryParam.customer_number }
								</c:if>
								<c:if test="${not empty queryParam.memo }">
								   备注: ${queryParam.memo }
								</c:if>
							</div>
						</td>
						<td  width="100px"  style=" border:1px  solid #000; "  height="40px;" valign="top">
						<table>
							<tr>
								<td>
									<div  style="font-size:8px; ">收件员：&nbsp;&nbsp;&nbsp;</div>
									<div  style="font-size:8px; ">寄件日期:
									<fmt:formatDate value="${queryParam.order_time}" pattern="MM-dd"/></div>
									<div  style="font-size:8px; ">派件员：&nbsp;&nbsp;&nbsp;</div>
								</td>
							</tr>
						</table>
						</td>
						<td width="96px"  style=" border:1px  solid #000; " height="40px" colspan="2" valign="top">
							<table>
							<tr>
								<td>
									<div  style="font-size:8px; ">签收：</div>
									<div  style="font-size:8px; ">&nbsp;</div>
									<div  style="font-size:8px; ">&nbsp; </div>
								</td>
								
								<td align="left">
									<div  style="font-size:8px; ">&nbsp;</div>
									<div  style="font-size:8px; ">&nbsp;</div>
									<div  style="font-size:8px; "align="left">月&nbsp;&nbsp;&nbsp;日</div>
								</td>
							</tr>
						</table>
						</td>
					</tr>
					<tr >
						<td  width="120px"  style=" border:1px  solid #000; border-top:2px solid #000;" colspan="2" height="60px;" valign="top">
						</td>
						
						<td width="180px"  style=" border:1px  solid #000; border-top:2px solid #000;" height="55px" colspan="4" valign="top">
							<div align="center">
								<img alt=""  src="${root}onebarcode/${queryParam.waybill}.png" style="width: 190px;height: 45px;">
							</div>
							<div style="font-size:7px;height: 5px" align="center">运单号：${queryParam.waybill}</div>
						</td>
					</tr>
					
					
					<tr >
						<td width="24px" style=" border:1px  solid #000; "  height="40px">
							<div  style="font-size:8px; " align="center"  >寄</div>
							<div  style="font-size:8px; " align="center"  >件</div>
							<div  style="font-size:8px; " align="center"  >人</div>
						</td>
						<td  width="278px"  style=" border:1px  solid #000; " colspan="5" height="40px;" valign="top">
							<div style=" font-size:8px; " >&nbsp;${queryParam.from_contacts }&nbsp;&nbsp;&nbsp;${queryParam.from_phone }</div>
							<div style=" font-size:8px;height: 5px " >&nbsp;</div>
							<div style=" font-size:8px; " >${queryParam.from_province }省${queryParam.from_city }市${queryParam.from_state }${queryParam.from_address }</div>
						</td>
					</tr>
					
					<tr >
						<td width="24px" style=" border:1px  solid #000; "  height="40px">
							<div  style="font-size:8px; " align="center"  >收</div>
							<div  style="font-size:8px; " align="center"  >件</div>
							<div  style="font-size:8px; " align="center"  >人</div>
						</td>
						<td  width="278px"  style=" border:1px  solid #000; " colspan="5" height="40px;" valign="top">
							<div style=" font-size:8px; " >&nbsp;${queryParam.to_contacts }&nbsp;&nbsp;&nbsp;${queryParam.to_phone }</div>
							<div style=" font-size:8px;height: 5px " >&nbsp;</div>
							<div style=" font-size:8px; " >${queryParam.to_province }省${queryParam.to_city }市${queryParam.to_state }${queryParam.to_address }</div>
						</td>
					</tr>
					<tr >
						<td width="24px" style=" border:1px  solid #000; " colspan="6" height="110px"valign="top">
							<table>
							<tr>
								<td valign="top">
									
									<div  style="font-family:'黑体'; font-size:28px; font-weight:bold;">
										<c:if test="${not empty queryParam.customer_number}">
										   客户订单号: ${queryParam.customer_number }&nbsp;&nbsp;&nbsp;&nbsp;
										</c:if>
									</div>
									<div  style="font-size:8px; ">
										<c:if test="${not empty queryParam.memo }">
										   备注: ${queryParam.memo }&nbsp;&nbsp;&nbsp;&nbsp;
										</c:if>
										<c:forEach  items="${list}" var="list">
											<c:if test="${not empty list.sku_name }">
											   商品名称：${list.sku_name}&nbsp;&nbsp;&nbsp;&nbsp;
											</c:if>
											<c:if test="${not empty list.qty}">
											   件数：${list.qty}&nbsp;&nbsp;&nbsp;&nbsp;
											</c:if>
											<c:if test="${not empty list.weight}">
											   重量：${list.weight}&nbsp;&nbsp;&nbsp;&nbsp;
											</c:if>
											<%-- 商品名称：${list.sku_name}&nbsp;&nbsp;&nbsp;&nbsp;件数：${list.qty}&nbsp;&nbsp;&nbsp;&nbsp; --%>
										</c:forEach>
									<%-- 客户订单号：${queryParam.customer_number}&nbsp;&nbsp;&nbsp;件数：${queryParam.total_qty}&nbsp;&nbsp;&nbsp;重量：${queryParam.total_weight} --%>
									</div>
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</div>
</div>
</body>
</html>