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
				jsPrintSetup.setSilentPrint(false);
				//closewin();
			}
			
		</script>
	</head>
	<body>
		<div>
		<table class="tbale" cellspacing="0" cellpadding="0"  style=" border:2px  solid #000;  " height="100%"  width="100%">
					<tr height="9px">
						<td width="30%" style=" border:1px  solid #000; border-right: #ff0000;" colspan="2" height="36px">
							<%-- <c:if test="${queryParam.expressCode='BSC'}">
							<img alt=""  src="${root}onebarcode/yunda.jpg" style="width: 100px;height: 30px;">
						</c:if>  --%>
							<c:if test="${queryParam.expressCode=='BS'}"><img alt=""  src="${root}onebarcode/yunda.jpg" style="width: 100px;height: 30px;">
						</c:if>
							<c:if test="${queryParam.expressCode=='ZTO'}"><img alt=""  src="${root}onebarcode/ZTO.jpg" style="width: 100px;height: 30px;">
						</c:if>
							</td>
						<td width="44%" style=" border:1px  solid #000; border-right: #ff0000; border-left: #ff0000;" colspan="2" height="36px"><div class="hite" height="9px;"></div></td>
						<td width="24%" style=" border:1px  solid #000; border-left: #ff0000;" colspan="2" height="36px">
						</td>
					</tr>
					<tr height="15px;" >
						<td colspan="6" style=" border:1px  solid #000; "height="56px;">
						<div style="font-family:'黑体'; font-size:42px; font-weight:bold;" align="center">${queryParam.mark_destination }</div></td>
					</tr>
					<tr height="10px;">
						<td width="74%" align="left" style=" border:1px  solid #000; " colspan="4" height="37px">
						<div  style="font-size:20px; " > ${queryParam.package_name }</div></td>
						<td width="24%" align="center" style=" border:1px  solid #000; " colspan="2" height="37px"><div class="hite">
						<img alt=""  src="${root}onebarcode/${queryParam.waybill}.png" style="width: 100px;height: 30px;">
						</div></td>
					</tr>
					<tr >
						<td width="5%" style=" border:1px  solid #000; "  height="45px" rowspan="2" >
						<div  style="font-size:10px;">收件</div>
						</td>
						<td width="69%" style=" border:1px  solid #000; " height="45px" colspan="3"  rowspan="2" valign="top">
						<div  style="font-family:'黑体'; font-size:11px; font-weight:bold; height: 15px">&nbsp;&nbsp;&nbsp;${queryParam.to_contacts }&nbsp;&nbsp;&nbsp;${queryParam.to_phone }</div>
						<div  style="font-family:'黑体'; font-size:11px; font-weight:bold; height: 5px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
						<div style="font-family:'黑体'; font-size:11px; font-weight:bold;height: 15px">${queryParam.to_province }省${queryParam.to_city }市${queryParam.to_state }${queryParam.to_address }</div>
						</td>
						
						<td width="24%" style=" border:1px  solid #000; " height="6px" colspan="2">
						<div  style="font-size:10px;" align="center">服&nbsp;&nbsp;&nbsp;务</div>
						</td>
					</tr>
					<tr height="13px;" valign="top">
						<td width="24%" style=" border:1px  solid #000; " colspan="2" height="84px" rowspan="2" valign="top">
							<table>
								<tr>
									<td>
										<div  style="font-size:8px; height: 15px">代收金额:${queryParam.total_amount }</div>
										<div  style="font-size:8px;height: 15px">预约配送:</div>
										<div  style="font-size:8px;height: 15px" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
										<div  style="font-size:8px;height: 15px">声明价值:${queryParam.total_amount }</div>
										<div  style="font-size:8px;height: 15px">计费重量:${queryParam.total_weight }</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr height="12px;">
						<td width="5%" style=" border:1px  solid #000; " height="45px">
						<div  style="font-size:10px;">寄件</div></td>
						<td width="69%" style=" border:1px  solid #000; " colspan="3" valign="top">
						<div style="font-size:8px;height: 15px" >&nbsp;&nbsp;&nbsp;${queryParam.from_orgnization }&nbsp;&nbsp;&nbsp;${queryParam.from_phone }</div>
						<div  style="font-family:'黑体'; font-size:11px; font-weight:bold; height: 5px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
						<div style="font-size:8px;height: 10px">${queryParam.from_province }省${queryParam.from_city }市${queryParam.from_state }${queryParam.from_address }</div>
						</td>
					</tr>
					<tr height="25px;">
						<td width="98%" style=" border:1px  solid #000; " colspan="6" height="82px"><div class="hite" align="center">
						<img alt=""  src="${root}onebarcode/${queryParam.waybill}.png" style="width: 240px;height: 60px;">
						</div>
						<div align="center" style="font-size:10px;">${queryParam.waybill}</div>
						</td>
					</tr>
					<tr style=" border:2px  solid #000; ">
						<td width="44%" style=" border:1px  solid #000; " colspan="3" height="73px" >
						<div  style="font-family:'黑体'; font-size:10px;">快件送达收件人地址，经收件人或收件人(寄件人)允许的代收人签字，视为送达。您的签字代表您已签收此包裹,
						并已确认商品信息无误、包装完好、没有划痕、破损等表面质量问题。</div></td>
						<td width="35%" style=" border:1px  solid #000; " colspan="2" valign="top">
						<div class="hite" style="font-size:9px;height: 10px" >签收人：</div>
						<div  style="font-family:'黑体'; font-size:11px; font-weight:bold; height: 55px">&nbsp;</div>
						<div class="hite" style="font-size:10px;height: 10px">时间：</div>
						</td>
						<td width="19%" style=" border:1px  solid #000; ">
						</td>
					</tr>
					<tr height="9px">
						<td width="30%" style=" border:1px  solid #000; border-right: #ff0000; border-top:2px solid #000;" colspan="3" height="50px"></td>
						<td width="44%" style=" border:1px  solid #000; border-left: #ff0000; border-top:2px solid #000;" colspan="3">
						<div class="hite" align="center">
						<img alt=""  src="${root}onebarcode/${queryParam.waybill}.png" style="width: 160px;height: 40px;">
						</div><div align="center" style="font-size:7px;">${queryParam.waybill}</div></td>
					</tr>
					<tr height="5px;">
						<td width="5%" style=" border:1px  solid #000; "  height="44px">
						<div  style="font-size:10px;">收件</div></td>
						<td width="69%" style=" border:1px  solid #000; " colspan="3" valign="top">
						<div style="font-size:8px;" >&nbsp;&nbsp;&nbsp;${queryParam.to_contacts }&nbsp;&nbsp;&nbsp;${queryParam.to_phone }</div>
						<div  style="font-family:'黑体'; font-size:11px; font-weight:bold; height: 5px">&nbsp;</div>
						<div style="font-size:8px;">${queryParam.to_province }省${queryParam.to_city }市${queryParam.to_state }${queryParam.to_address }</div>
						</td>
						<td width="24%" style=" border:1px  solid #000; " colspan="2" rowspan="2">
						</td>
					</tr>
					<tr height="12px;">
						<td width="5%" style=" border:1px  solid #000; " height="44px" valign="top">
						<div  style="font-size:10px;">寄件</div></td>
						<td width="69%" style=" border:1px  solid #000; " colspan="3" valign="top">
						<div style="font-size:8px;" >&nbsp;&nbsp;&nbsp;${queryParam.from_orgnization }&nbsp;&nbsp;&nbsp;${queryParam.from_phone }</div>
						<div  style="font-family:'黑体'; font-size:11px; font-weight:bold; height: 5px">&nbsp;</div>
						<div style="font-size:8px;">${queryParam.from_province }省${queryParam.from_city }市${queryParam.from_state }${queryParam.from_address }</div>
						</td>
					</tr>
					<tr>
						<td colspan="6" style=" border:1px  solid #000; "height="112px;" valign="bottom">
							<div style="float:right;font-size:8px;font-weight:bold;">已验视</div>
						</td>
					</tr>
				</table>
				</div>
	</body>
</html>