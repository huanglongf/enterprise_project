<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/base/yuriy.jsp" %>
		<title>Packaged Composite System</title>
		<script type= "text/javascript" src= "<%=basePath %>My97DatePicker/WdatePicker.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>/assets/js/jquery-2.0.3.min.js" ></script>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		
	<script type="text/javascript">
			$(document).ready(function(){ 
				loadding();
			});	
			function closewin(){
				self.opener=null;
				self.close();
			}
			function clock(){
				i=i-1
				if(i>0){
					setTimeout("clock();",3000);
				}else{ 
					this.window.opener = null;
					window.close();
				}
			}
			document.getElementById('time').innerHTML=new Date().toLocaleString();     
	        setInterval("document.getElementByIdx_x('time').innerHTML=new Date().toLocaleString();",1000);
			function loadding() {
				jsPrintSetup.setOption('orientation', jsPrintSetup.kPortraitOrientation);
				// set top margins in millimeters
				jsPrintSetup.setOption('marginTop', 0);
				jsPrintSetup.setOption('marginBottom', 0);
				jsPrintSetup.setOption('marginLeft', 0);
				jsPrintSetup.setOption('marginRight', 0);
				// set page header
				jsPrintSetup.setOption('headerStrLeft', '');
				jsPrintSetup.setOption('headerStrCenter', '');
				jsPrintSetup.setOption('headerStrRight', '');
				// set empty page footer
				jsPrintSetup.setOption('footerStrLeft', '');
				jsPrintSetup.setOption('footerStrCenter', '');
				jsPrintSetup.setOption('footerStrRight', '');
				// Suppress print dialog
				jsPrintSetup.setSilentPrint(true);
				// Do Print
				jsPrintSetup.print();
				// Restore print dialog
				//jsPrintSetup.setSilentPrint(false);
				closewin();
			}
			
		</script>
	</head>
	<body>
		<div>
		<table class="tbale" cellspacing="0" cellpadding="0"  style=" border:2px  solid #000;  height=100%;width=100%;table-layout:fixed" >
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
							<div style="font-family:'黑体'; font-size:42px; font-weight:bold;overflow: hidden;" >${queryParam.mark_destination }</div>
						</td>
					</tr>
					<tr >
						<td width="24px" style=" border:1px  solid #000; "  height="45px">
							<div  style="font-size:8px; " align="center"  >收</div>
							<div  style="font-size:8px; " align="center"  >件</div>
							<div  style="font-size:8px; " align="center"  >人</div>
						</td>
						<td  width="372px"  style=" border:1px  solid #000;overflow: hidden; " colspan="5" height="45px;" valign="top">
							<div style="font-family:'黑体'; font-size:14px; font-weight:bold;overflow: hidden;" >${queryParam.to_contacts }&nbsp;${queryParam.to_phone }</div>
							<div style="font-family:'黑体'; font-size:14px; font-weight:bold;overflow: hidden;" >${queryParam.to_province }${queryParam.to_city }${queryParam.to_state }${queryParam.to_address }</div>
						</td>
					</tr>
					<tr >
						<td width="24px" style=" border:1px  solid #000; "  height="40px">
							<div  style="font-size:8px; " align="center"  >寄</div>
							<div  style="font-size:8px; " align="center"  >件</div>
							<div  style="font-size:8px; " align="center"  >人</div>
						</td>
						<td  width="278px"  style=" border:1px  solid #000; " colspan="3" height="40px;" valign="top">
							<div style=" font-size:8px; " >${queryParam.from_contacts }&nbsp;${queryParam.from_phone }</div>
							<div style=" font-size:8px; " >${queryParam.from_province }${queryParam.from_city }${queryParam.from_state }${queryParam.from_address }</div>
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
									<div  style="font-size:8px; " >
									<c:if test="${queryParam.pay_path=='1'}">付款方式：寄方付</c:if>
									<c:if test="${queryParam.pay_path=='2'}">付款方式：收方付</c:if>
									<c:if test="${queryParam.pay_path=='4'}">付款方式：寄付月结</c:if>
									<c:if test="${queryParam.pay_path=='3'}">付款方式：寄付月结</c:if>
									</div>
									<div  style="font-size:8px; ">月结账号：${queryParam.custid }</div>
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
									<div  style="font-size:8px; ">寄件日期:&nbsp;&nbsp;&nbsp;</div>
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
						<td width="24px" style=" border:1px  solid #000; " colspan="6" height="80px"valign="top">
							<table>
							<tr>
								<td valign="top">
																	
									<div  style="">
										<c:if test="${not empty queryParam.customer_number}">
										   <img alt=""  src="${root}onebarcode/${queryParam.customer_number}.png"  style="width:240px;height:58px;vertical-align:middle;">
										</c:if>
										<span style="font-family:'黑体'; font-size:22px; font-weight:bold;">${queryParam.expected_from_date}</span>
										<c:if test="${not empty queryParam.customer_number}">
										   <div style="font-size:10px;margin-left: 50px;margin-top: 2px;"> 客户订单号: ${queryParam.customer_number}</div>
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
										</c:forEach>
									</div> 
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</div> 
	</body>
</html>
<style>
</style>