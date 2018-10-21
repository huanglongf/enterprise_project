<%@include file="/pda/commons/common.jsp"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html class="js cssanimations">
<head>
<title>WMS3.0 PDA</title>
<%@include file="/pda/commons/common-meta.jsp"%>
<%@include file="/pda/commons/common-css.jsp"%>
</head>
<body contextpath="<%=request.getContextPath() %>">
	<div class="body-all">
		<%@include file="/pda/commons/common-header.jsp"%>
			<div id="div1">
				<table>
					<tr>
						<td width="30px">1.</td>
						<td align="left"><a href="<%=request.getContextPath()%>/pda/pageReceive.do">收 货</a></td>
					</tr>
					<tr>
						<td>2.</td>
						<td align="left"><a href="<%=request.getContextPath()%>/pda/pageReceiveByBox.do">按箱收货</a></td>
					</tr>
					<tr>
						<td>3.</td>
						<td align="left"><a href="<%=request.getContextPath()%>/pda/pageReceiveByBoxTag.do">按箱收货(扫标签)</a></td>
					</tr>
					<tr>
						<td>4.</td>
						<td align="left"><a href="<%=request.getContextPath()%>/pda/pageRedirectGood.do">良品逐件上架</a></td>
					</tr>
					<tr>
						<td>5.</td>
						<td align="left"><a href="<%=request.getContextPath()%>/pda/pageRedirectGoodBatch.do">良品批量上架</a></td>
					</tr>
					<tr>
						<td>6.</td>
						<td align="left"><a href="<%=request.getContextPath()%>/pda/pageRedirectIncomplete.do">残次品上架</a></td>
					</tr>
					<tr>
						<td>7.</td>
						<td align="left"><a href="<%=request.getContextPath()%>/pda/pageRedirect.do">拣货</a></td>
					</tr>
					
				</table>
			</div>
			<div id="div2">
				<table>
					<tr>
						<td width="30px">8.</td>
						<td align="left"><a href="<%=request.getContextPath()%>/pda/pageRedirectGoodRen.do">良品逐件上架(人)</a></td>
					</tr>
					<tr>
						<td>9.</td>
						<td align="left"><a  href="<%=request.getContextPath()%>/pda/pageRedirectGoodBatchRen.do">良品批量上架(人)</a></td>
					</tr>
					<tr>
						<td>10.</td>
						<td align="left"><a  href="<%=request.getContextPath()%>/pda/toCollection.do">自定义集货</a></td>
					</tr>
					<tr>
						<td>11.</td>
						<td align="left"><a href="<%=request.getContextPath()%>/pda/toCollectionMove.do">释放集货库位</a></td>
					</tr>
					<tr>
						<td>12.</td>
						<td align="left"><a href="<%=request.getContextPath()%>/pda/toCollectionNoticeWcs.do">集货区域推荐</a></td>
					</tr>
					<tr>
						<td>13.</td>
						<td align="left"><a href="<%=request.getContextPath()%>/pda/outBoundHands.do">出库交接(预售)</a></td>
					</tr>
					<tr>
						<td>14.</td>
						<td align="left"><a href="<%=request.getContextPath()%>/pda/toCollectionMoveByBox.do">释放集货库位-扫容器</a></td>
					</tr>
				</table>
			</div>
			<div id="div3">
				<table>
					<tr>
						<td width="30px">15.</td>
						<td align="left"><a href="<%=request.getContextPath()%>/pda/outBoundHandsCurrency.do">出库交接(通用)</a></td>
					</tr>
					<tr>
						<td width="30px">16.</td>
						<td align="left"><a href="<%=request.getContextPath()%>/pda/pdaTransitInnerIndex.do">库内移动</a></td>
					</tr>
					<tr>
						<td width="30px">17.</td>
						<td align="left"><a href="<%=request.getContextPath()%>/pda/pageRedirectPickingSuggestion.do">二次分拣</a></td>
					</tr>
					<tr>
						<td width="30px">18.</td>
						<td align="left"><a href="<%=request.getContextPath()%>/pda/toCollectionMoveTwoPicking.do">释放集货库位-二次分拣</a></td>
					</tr>
					
					<tr>
						<td width="30px">19.</td>
						<td align="left"><a href="<%=request.getContextPath()%>/pda/returnWareHousePicking.do">退仓-拣货</a></td>
					</tr>
					
					<tr>
						<td width="30px">20.</td>
						<td align="left"><a href="<%=request.getContextPath()%>/pda/pageBoxMove.do">释放周转箱</a></td>
					</tr>
					
					<tr>
						<td width="30px">21.</td>
						<td align="left"><a href="<%=request.getContextPath()%>/pda/inventoryQuery.do">PDA库存查询</a></td>
					</tr>
					<tr>
						<td width="30px">26.</td>
						<td align="left"><a href="<%=request.getContextPath()%>/pda/entrucking.do">PDA装车</a></td>
					</tr>
				</table>
			
		
			</div>
			
			<div id="div4">
				<table>
					<tr>
						<td width="30px">22.</td>
						<td align="left"><a href="<%=request.getContextPath()%>/pda/pageReceiveByBoxNike.do">按箱收货-NIKE</a></td>
					</tr>
					<tr>
						<td width="30px">23.</td>
						<td align="left"><a href="<%=request.getContextPath()%>/pda/pageRedirectGoodRenNike.do">逐件上架-NIKE</a></td>
					</tr>
					<tr>
						<td width="30px">24.</td>
						<td align="left"><a href="<%=request.getContextPath()%>/pda/pageRedirectGoodBatchRenNike.do">批量上架-NIKE</a></td>
					</tr>
					<tr>
						<td width="30px">30.</td>
						<td align="left"><a href="<%=request.getContextPath()%>/pda/pageRedirectPdaBoxBind.do">批次周转箱绑定</a></td>
					</tr>
				</table>
			</div>
			<br/>
			<div>
				<a id="page1">第一页</a><a id="page2">第二页</a><a id="page3">第三页</a><a id="page4">第四页</a><br/>
				<a>快速进入功能</a><input type="text" style="text-align: center;" name="menuId" id="menuId" required />
			</div>
								
		<%@include file="/pda/commons/common-footer.jsp"%>
		<%@include file="/pda/commons/common-modal.jsp"%>
		<%@include file="/pda/commons/common-play.jsp"%>
		<%@include file="/pda/commons/common-script.jsp"%>
		<script type="text/javascript" src="./shelves/pda_zoomout.js"></script>
		<script type="text/javascript">
			    $("#menuId").focus();
				$(document).ready(function(){
					////////////////////////////////////////////////////适应屏幕大小
					/* var height=$(window).height();
					if(height==664 || height==1384){
						zoomoutPda();
					}  */
					///////////////////////////////////////////////////////////////////////////////////////////////
					$("#div1").show();
					$("#div2").hide();
					$("#div3").hide();
					$("#div4").hide();
						
					$("#page1").click(function(){
						$("#div1").show();
						$("#div2").hide();
						$("#div3").hide();
						$("#div4").hide();
					});
					$("#page2").click(function(){
						$("#div2").show();
						$("#div1").hide();
						$("#div3").hide();
						$("#div4").hide();
					});
					
					$("#page3").click(function(){
						$("#div3").show();
						$("#div1").hide();
						$("#div2").hide();
						$("#div4").hide();
					});
					
					$("#page4").click(function(){
						$("#div3").hide();
						$("#div1").hide();
						$("#div2").hide();
						$("#div4").show();
					});
					
					//退出登录
					$("#exit").click(function(){
						window.location.href=$("body").attr("contextpath")+"/pda/pdaExit.do";
					});
					
					$("#menuId").keydown(function(evt){
						if (evt.keyCode == 13) {
							var key = $("#menuId").val() ;
							if("7" == key){
								window.location.href=$("body").attr("contextpath")+"/pda/pageRedirect.do";
							}else if("1" == key){
								window.location.href=$("body").attr("contextpath")+"/pda/pageReceive.do";
							}else if("4"==key){
								window.location.href=$("body").attr("contextpath")+"/pda/pageRedirectGood.do";
							}else if("5"==key){
								window.location.href=$("body").attr("contextpath")+"/pda/pageRedirectGoodBatch.do";
							}else if("2"==key){
								window.location.href=$("body").attr("contextpath")+"/pda/pageReceiveByBox.do";
							}else if("6"==key){
								window.location.href=$("body").attr("contextpath")+"/pda/pageRedirectIncomplete.do";
							}else if("16"==key){
								window.location.href=$("body").attr("contextpath")+"/pda/pdaTransitInnerIndex.do";
							}else if("8"==key){
								window.location.href=$("body").attr("contextpath")+"/pda/pageRedirectGoodRen.do";
							}else if("9"==key){
								window.location.href=$("body").attr("contextpath")+"/pda/pageRedirectGoodBatchRen.do";
							}else if("3"==key){
								//window.location.href=$("body").attr("contextpath")+"/pda/pageReceiveByBoxTag.do";
								window.location.href=$("body").attr("contextpath")+"/pda/pageReceiveByBoxTag.do";
							}else if("10"==key){
								window.location.href=$("body").attr("contextpath")+"/pda/toCollection.do";
							}else if("11"==key){
								window.location.href=$("body").attr("contextpath")+"/pda/toCollectionMove.do";
							}else if("12"==key){
								window.location.href=$("body").attr("contextpath")+"/pda/toCollectionNoticeWcs.do";
							}else if("13"==key){
								window.location.href=$("body").attr("contextpath")+"/pda/outBoundHands.do";
							}else if("14"==key){
								window.location.href=$("body").attr("contextpath")+"/pda/toCollectionMoveByBox.do";
							}else if("15"==key){
								window.location.href=$("body").attr("contextpath")+"/pda/outBoundHandsCurrency.do";
							}else if("17"==key){
								window.location.href=$("body").attr("contextpath")+"/pda/pageRedirectPickingSuggestion.do";
							}else if("18"==key){
								window.location.href=$("body").attr("contextpath")+"/pda/toCollectionMoveTwoPicking.do";
							}else if("19"==key){
								window.location.href=$("body").attr("contextpath")+"/pda/returnWareHousePicking.do";
							}else if("20"==key){
								window.location.href=$("body").attr("contextpath")+"/pda/pageBoxMove.do";
							}else if("21"==key){
								window.location.href=$("body").attr("contextpath")+"/pda/inventoryQuery.do";
							}else if("22"==key){
								window.location.href=$("body").attr("contextpath")+"/pda/pageReceiveByBoxNike.do";
							}else if("23"==key){
								window.location.href=$("body").attr("contextpath")+"/pda/pageRedirectGoodRenNike.do";
							}else if("24"==key){
								window.location.href=$("body").attr("contextpath")+"/pda/pageRedirectGoodBatchRenNike.do";
							}else if("30"==key){
								window.location.href=$("body").attr("contextpath")+"/pda/pageRedirectPdaBoxBind.do";
							}else if("26"==key){
								window.location.href=$("body").attr("contextpath")+"/pda/entrucking.do";
							}
						}
					});
				});
		</script>
</body>
</html>