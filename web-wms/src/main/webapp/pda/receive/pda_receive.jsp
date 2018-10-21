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
		<div class="am-cf admin-main">
			<div class="admin-content">
			<div><font id="msg" color="red"></font></div>
				<div class="am-tabs " data-am-tabs="" id="receive1">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div>是否拆箱</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								    <select name="select" id="isPacking">
                                        <option value="否">否</option>
                                        <option value="是">是</option>
                                     </select>
								</div>
							</div>
						</div>
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div >录入库存状态</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								    <select name="select" id="inventoryStatus">
                                        <option value="良品">良品</option>
                                        <option value="残次品">残次品</option>
                                     </select>
								</div>
							</div>
						</div>
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div>数量模式</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								    <select name="select" id="quantitativeModel">
                                        <option value="逐件模式">逐件模式</option>
                                        <option value="批量模式">批量模式</option>
                                     </select>
								</div>
							</div>
						</div>
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div>扫描作业指令/相关单据号(货箱号)</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								   <input type="text" class="input-element" id="staCode" />
								</div>
							</div>
						</div>
						
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="receiveOk">确认</button>
							<button type="button" class="am-btn am-btn-primary " id="receiveBack">返回</button>
						</div>
					</div>
					
				</div>
				<div class="am-tabs " data-am-tabs="" id="receive2">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div>扫描容器号</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								   <input type="text" class="input-element" id="cartonBox" />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="cartonBoxOk">确认</button>
							<button type="button" class="am-btn am-btn-primary " id="cartonBoxBack">返回</button>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary" id="cartonASNOk">ASN收货完成</button>
						</div>
					</div>
					
				</div>
				<div class="am-tabs " data-am-tabs="" id="receive3">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div >扫描SKU           计数:<span id="skuNumCount"></span></div> 
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								   <input type="text" class="input-element" id="sku" />
								</div>
							</div>
						</div>
						<div class="am-tab-panel am-fade  am-in" style="display: none;" id="skuNumDiv">
							<div class="am-g">
								<div >录入数量</div> 
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								   <input type="text" class="input-element" id="skuNum" />
								</div>
							</div>
						</div>
					</div>
					<div class="btn-center">
						<button type="button" class="am-btn am-btn-primary btn-right-margin" id="skuOk">确认</button>
						<button type="button" class="am-btn am-btn-primary " id="skuBack">返回</button>
					</div>
					<div class="btn-center">
						<button type="button" class="am-btn am-btn-primary" id="boxOver">容器收货完成</button>
					</div>
						
				</div>
					
				
				<div class="am-tabs " data-am-tabs="" id="receive4">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div>录入失效日期<font id="expDate"></font></div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								   <input type="text" class="input-element" id="expirationDate" />
								</div>
							</div>
							<div class="am-g">
								<div>录入生产日期</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								   <input type="text" class="input-element" id="productionDate" />
								</div>
							</div>
							
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="timeCodeOk">确认</button>
							<button type="button" class="am-btn am-btn-primary " id="timeBack">返回</button>
						</div>
					</div>
				</div>
				
				<div class="am-tabs " data-am-tabs="" id="receive5">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
						    <div class="am-g" id="numDiv" style="display:none"> 
								<div>录入残次数量             <span id="expDemNum">计数: <span id="totalNum1"></span>-<span id="totalNum2"></span></span></div>
							</div>
							<div class="am-g" id="damageNumDiv" style="display:none">
								<div class="input-element-frame">
								   <input type="text" class="input-element" id="damageNum" />
								</div>
							</div>
							<div class="am-g">
								<div>录入残次类型</div>
							</div>
							<div class="am-g"> 
								<div class="input-element-frame">
								  <!--  <input type="text" class="input-element" id="unType" /> -->
								   <select id="unType"  loxiaType="select"></select>
								</div>
							 </div> 
							<div class="am-g">
								<div>录入残次原因</div>
							</div>
							<div class="am-g"> 
								<div class="input-element-frame">
								   <!-- <input type="text" class="input-element" id="unReason" /> -->
								   <select id="unReason"  loxiaType="select"></select>
								</div>
						    </div> 
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="damageCodeOk">确认</button>
							<button type="button" class="am-btn am-btn-primary " id="damageBack">返回</button>
						</div>
					</div>
				</div>
				
				<div class="am-tabs " data-am-tabs="" id="receive6">
					<div class="am-tabs-bd" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
						<div class="am-tab-panel am-fade  am-in" >
							<div class="am-g">
								<div>录入SN号 <span id="totalSn" style="display:none">计数:<span id="totalSnNum1"></span>-<span id="totalSnNum2"></span></span></div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								   <input type="text" class="input-element" id="sn" />
								</div>
							</div>
						</div>
						<div class="btn-center">
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="snOK">确认</button>
							<button type="button" class="am-btn am-btn-primary " id="snBack">返回</button>
						</div>
					</div>
					
				</div>
			 </div>	
			</div>
			<%@include file="/pda/commons/common-footer.jsp"%>
		</div>
		<%@include file="/pda/commons/common-modal.jsp"%>
		<%@include file="/pda/commons/common-play.jsp"%>
		<%@include file="/pda/commons/common-script.jsp"%>
		<script type="text/javascript" src="./receive/pda_receive.js"></script>
		<script type="text/javascript" src="./shelves/pda_zoomout.js"></script>
		
	
</body>
</html>