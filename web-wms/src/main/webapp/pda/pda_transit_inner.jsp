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
				        <div><font id="msg" color="red"></font></div>		
						<div class="am-tab-panel am-fade  am-in" id="1">
								库位编码<input type="text"    id="location"  />
						</div>
						
						<div class="am-tab-panel am-fade  am-in" id="2">
							
								商品条码<input type="text"    id="skuBarCode"  />
								
						</div>
						
						<div class="am-tab-panel am-fade  am-in" id="3">
							<div class="am-g">
								<div>店铺</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								   <input type="text"  class="input-element"  id="owner"/>
								     <div id="ownerDiv" class="hidden">
				                     <select id="ownerselect" style="width: 195px;">
				                     </select>
				                   </div>
								</div>
							</div>
						</div>
						
						<div class="am-tab-panel am-fade  am-in" id="4">
							<div class="am-g">
								<div>库存状态</div>
							</div>
							<div class="am-g">
								<div class="input-element-frame">
								    <input type="text"  class="input-element"  id="statusName"  />
								    <div id="statusNameDiv" class="hidden">
				                       <select id="statusNameSelect" style="width: 195px;"></select>
								    </div>
							    </div>
						    </div>
						</div>
						
						<div class="am-tab-panel am-fade  am-in" style="float: left;" id="5">	
						 货号尺码&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						 
						 <span id="skuName"></span>
						
						</div>
						
						<div class="am-tab-panel am-fade  am-in"  style="float: left;" id="6">
							
						库位编码  &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="locationCodeName"></span>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
					    </div>
					
						<div class="am-tab-panel am-fade  am-in"  style="float: left;" id="7">
                                                                                          库存状态 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                              
                              <span id="statusInvName"></span> 
                                                                                             
						</div>
						<div class="am-tab-panel am-fade  am-in" style="float: left;" id="10">
                                                                                              总数/可用数量    &nbsp;&nbsp;&nbsp;&nbsp;<span  id="totalNum"></span>
                                                                                             
						</div>
						<div class="am-tab-panel am-fade  am-in" style="float: left;" id="8">
								移动数量<input type="text"    id="skuNum"  />	
						</div>
						
						<div class="am-tab-panel am-fade  am-in" style="float: left;" id="9">
								目标库位<input type="text" id="sendlocationCode"  />
						</div>
						
						<div class="btn-center" style="float: left;">
						    <button type="button" class="am-btn am-btn-primary btn-right-margin" id="back">返回</button>
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="next">下一步</button>
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="exec">确认执行</button>
							<button type="button" class="am-btn am-btn-primary btn-right-margin" id="cancel">取消</button>
						</div>
				</div>
			 </div>	
			</div>
			<%@include file="/pda/commons/common-footer.jsp"%>
		</div>
		<%@include file="/pda/commons/common-modal.jsp"%>
		<%@include file="/pda/commons/common-play.jsp"%>
		<%@include file="/pda/commons/common-script.jsp"%>
		<script type="text/javascript" src="./shelves/pda_zoomout.js"></script>
		<script type="text/javascript">
		
		$(document).ready(function(){
			var flagCommit=null;
			var location;
			var skuBarCode;
			var skuTotalQty;
			$("#location").focus();
			$("#back").show();
			$("#3").hide();
			$("#4").hide();
			$("#5").hide();
			$("#6").hide();
			$("#7").hide();
			$("#8").hide();
			$("#9").hide();
			$("#10").hide();
			$("#next").hide();
			$("#exec").hide();
			$("#cancel").hide();
			var falgMsg="false";
			var statusMsg="false";
			var owners;
			var statusNames;
			var sendlocationCode;
			var isLocationCheck=false;
			
			$("#location").keypress(function(evt){
				if (evt.keyCode === 13) {
					location = $("#location").val();
					if(location!=""){
						$("#msg").text("");
						var flag=request($("body").attr("contextpath") + "/pda/checkLocation.do",{"locationCode":location});
						if(flag["msg"]){
							$("#skuBarCode").focus();
						}else{
							$("#msg").text("库位编码不存在或者被锁定");
							return;
						}
					}else {
						$("#msg").text("请输入库位编码");
						return;
					}
				}
			});
			
			$("#sendlocationCode").keypress(function(evt){
				if(evt.keyCode === 13){
					$("#msg").text("");
					isLocationCheck=false;
					var sendlocationCode1=$("#sendlocationCode").val();
					if(null!=sendlocationCode1&&""!=sendlocationCode1){
						var flag=request($("body").attr("contextpath") + "/pda/pdaCheckSendlocationCode.do",{"sendlocationCode":sendlocationCode1,"checkSkuBarCode":skuBarCode,"locationCode":location,"statusName":statusNames,"owner":owners});
						if(flag["msg"]=="respective"){
							$("#msg").text("sku"+skuBarCode+"为单批隔离");
							return;
						}else if(flag["msg"]=="isTogether"){
							$("#msg").text("此库位"+sendlocationCode1+" 效期商品已经混放");
							return;
						}else if(flag["msg"]=="expireDateIsEquals"){
							$("#msg").text("此库位"+sendlocationCode1+" 效期商品跟准备移入的商品效期不一致 ");
							return;
						}else if(flag["msg"]=="noFind"){
							$("#msg").text("此库位"+sendlocationCode1+" 不存在或者已经锁定 ");
							return;
						}else if(flag["msg"]=="success"){
							sendlocationCode=sendlocationCode1;
							isLocationCheck=true;
							return;
						}
					}else{
						$("#msg").text("请输入库位编码");
						return;
					}
				}
			});

			$("#skuBarCode").keypress(function(evt){
				if (evt.keyCode === 13) {
					skuBarCode = $("#skuBarCode").val();
					location = $("#location").val();
					if(skuBarCode!=""){
						$("#msg").text("");
						var flag=request($("body").attr("contextpath") + "/pda/pdaCheckLocation.do",{"checkSkuBarCode":skuBarCode,"locationCode":location});
						if(flag["msg"]=="success"){
							var ownerList=flag["key"];
							var owner=ownerList.split(",");
							var statusNameList=new Array();
							var channelList=new Array();
							if(null!=owner&&owner.length>1){
								falgMsg="Ture";
								$("#ownerselect").empty();
								$("#owner").hide();
								$("#ownerDiv").show();
								$("#ownerselect").append("<option value=''>--请选择--</option>");
								var i=0;
								var j=0;
								
								for(var idx in owner){
									if(null!=channelList&&channelList.length>0){
										var t=false;
										var t1=false;
										for(var channel in channelList){
											if(channelList[channel]==owner[idx].split(":")[0]){
												t=false;
												break;
											}else{
												t=true;
											}
											if(channel==channelList.length-1&&t){
												channelList[j]=owner[idx].split(":")[0];
												j=j+1;
											}
										}
										/* if(channelList.contains(owner[idx].split(":")[0])<0){
											$("#msg").text("4");
											channelList[j]=owner[idx].split(":")[0];
											j=j+1;
										} */
									}else{
										channelList[j]=owner[idx].split(":")[0];
										j=j+1;
									}
									//$("<option value='" + owner[idx].split(":")[0] + "'>"+ owner[idx].split(":")[0] +"</option>").appendTo($("#ownerselect"));
									if(null!=statusNameList&&statusNameList.length>0){
										for(var channel in statusNameList){
											if(statusNameList[channel]==owner[idx].split(":")[1]){
												t1=false;
												break;
											}else{
												t1=true;
											}
											if(channel==statusNameList.length-1&&t1){
												statusNameList[j]=owner[idx].split(":")[1];
												i=i+1;
											}
										}
										
										/* if(statusNameList.indexOf(owner[idx].split(":")[1])<0){
											statusNameList[i]=owner[idx].split(":")[1];
											i=i+1;
										} */
									}else{
										statusNameList[i]=owner[idx].split(":")[1];
										i=i+1;
									}
								}
								for(var channel in channelList){
									$("<option value='" + channelList[channel] + "'>"+ channelList[channel]+"</option>").appendTo($("#ownerselect"));
								}
							}else{
								var s=owner[0].split(":");
								$("#ownerDiv").hide();
								$("#statusNameDiv").hide();
								$("#owner").show();
								$("#owner").val(s[0]);
								$("#statusName").val(s[1]);
								
							}
							
							 if(null!=statusNameList&&statusNameList.length>1){
								$("#statusNameSelect").empty();
								$("#statusNameDiv").show();
								$("#statusName").hide();
								$("#statusNameSelect").append("<option value=''>--请选择--</option>");
								for(var idx in statusNameList){
									$("<option value='" + statusNameList[idx]+ "'>"+ statusNameList[idx]+"</option>").appendTo($("#statusNameSelect"));
								}
								statusMsg="Ture";
							}else if(null!=statusNameList&&statusNameList.length==1){
								$("#statusNameDiv").hide();
								$("#statusName").val(statusNameList[0]);
								
							}
							
							/* if(null!=flag["isShelf"]){
								if(flag["isShelf"]=="ture"){
									if(null!=flag["expireDateList"]&&flag["expireDateList"]){
										var expireDateArray=flag["expireDateList"].split(",");
										if(null!=expireDateArray&&expireDateArray.length>1){
											$("#expireDateDiv").show();
											$("#expireDate").hide();
											$("#expireDateSelect").append("<option value=''>--请选择--</option>");
											for(var idx in expireDateArray){
												$("<option value='" + expireDateArray[idx]+ "'>"+ expireDateArray[idx] +"</option>").appendTo($("#expireDateSelect"));
											}
										}else{
											$("#expireDateDiv").hide();
											$("#expireDate").val(expireDateArray[0]);
										}
									}else{
										$("#5").hide();
									}
								}else{
									$("#5").hide();
								}
							} */
							$("#3").show();
							$("#1").hide();
							$("#2").hide();
							$("#4").show();
							$("#next").show();
							$("#back").hide();
							return;
						}else if(flag["msg"]=="false"){
							$("#msg").text("库位不存在");
							return;
						}else if (flag["msg"]=="NoSku"){
							$("#msg").text("商品不存在");
							return;
						}else if (flag["msg"]=="NoQty"){
							$("#msg").text("不存在可移动的库存数"+skuBarCode);
							return;
						}
					}else {
						$("#msg").text("请输入sku条码");
						return;
					}
				}
			});
			var brandCommit=null;
			$("#next").click(function(){
				if(brandCommit==null){
				brandCommit="1";
				$("#msg").text("");
				if(falgMsg=="Ture"){
					var owner1=$("#ownerselect").val();
					if(null==owner1||""==owner1){
						$("#msg").text("请选择店铺");
						return;
					}else{
						owners=owner1;
					}
				}else{
					var owner1=$("#owner").val();
					if(null==owner1||""==owner1){
						$("#msg").text("请输入店铺");
						return;
					}else{
						owners=owner1;
					}
				}
				if(statusMsg=="Ture"){
					var statusName1=$("#statusNameSelect").val();
					if(null==statusName1||""==statusName1){
						$("#msg").text("请选择库存状态");
						return;
					}else{
						statusNames=statusName1;
					}
				}else{
					var statusName1=$("#statusName").val();
					if(null==statusName1||""==statusName1){
						$("#msg").text("请输入库存状态");
						return;
					}else{
						statusNames=statusName1;
					}
				}
				$("#3").hide();
				$("#4").hide();
				var flag=request($("body").attr("contextpath") + "/pda/pdaFindSku.do",{"checkSkuBarCode":skuBarCode,"locationCode":location,"statusName":statusNames,"owner":owners});
				if(null!=flag&&null!=flag["msg"]){
					var msgArray=flag["msg"].split("-:");
					$("#skuName").html(msgArray[0]);
					$("#locationCodeName").html(msgArray[1]);
					$("#statusInvName").html(statusNames);
					$("#skuNum").val("1");
					skuTotalQty=msgArray[2];
					$("#5").show();
					$("#6").show();
					$("#7").show();
					$("#8").show();
					$("#9").show();
					$("#10").show();
					$("#exec").show();
					$("#cancel").show();
					$("#next").hide();
					$("#sendlocationCode").val("");
					$("#totalNum").html(msgArray[3]+"/"+msgArray[2]);
					$("#sendlocationCode").focus();
					brandCommit=null;
				}
				}else{
					$("#msg").text("已经提交，请等待");
				}
			});
			
			$("#exec").click(function(){
				$("#msg").text("");
				if(flagCommit==null){
					flagCommit="1";
					var skuNum=$("#skuNum").val();
					if(null!=skuNum&&''!=skuNum){
						if(parseInt(skuNum)>parseInt(skuTotalQty)){
							$("#msg").text("sku移动数量超过"+skuTotalQty);
							return;
						}
						var sendlocationCode1=$("#sendlocationCode").val();
						if(null==sendlocationCode1||""==sendlocationCode1){
							$("#msg").text("请输入目标库位");
							return;
						}
						var flag=request($("body").attr("contextpath") + "/pda/pdaExecuteTransitInner.do",{"sendlocationCode":sendlocationCode1,"checkSkuBarCode":skuBarCode,"locationCode":location,"statusName":statusNames,"owner":owners,"skuQty":skuNum});
						flagCommit=null;
						if(flag["msg"]=="respective"){
							$("#msg").text("sku"+skuBarCode+"为单批隔离");
							return;
						}else if(flag["msg"]=="isTogether"){
							$("#msg").text("此库位"+sendlocationCode1+" 效期商品已经混放");
							return;
						}else if(flag["msg"]=="expireDateIsEquals"){
							$("#msg").text("此库位"+sendlocationCode1+" 效期商品跟准备移入的商品效期不一致 ");
							return;
						}else if(flag["msg"]=="noFind"){
							$("#msg").text("此库位"+sendlocationCode1+" 不存在或者已经锁定 ");
							return;
						}else if(flag["msg"]=="NoInventory"){
							$("#msg").text("库存被单据占用");
							return;
						}else if(flag["msg"]=="success"){
							$("#msg").text("移入成功 ");
							var msg=request($("body").attr("contextpath")+"/pda/pdaTransitInnerIndexcheckLoc.do",{"locationCode":location});
							if(null!=msg&&"succuess"==msg["msg"]){
								$("#1").show();
								$("#2").show();
								$("#skuBarCode").val("");
								$("#location").val(location);
								$("#5").hide();
								$("#6").hide();
								$("#7").hide();
								$("#8").hide();
								$("#9").hide();
								$("#10").hide();
								$("#exec").hide();
								$("#cancel").hide();
								$("#next").hide();
								$("#back").show()
							}else{
								window.location.href=$("body").attr("contextpath")+"/pda/pdaTransitInnerIndex.do";
								$("#skuBarCode").val("");
								$("#location").val("");
							}
						}
					}else{
						$("#msg").text("请输入sku移动数量");
						return;
					}
				}else{
					$("#msg").text("已经提交，请等待");
				}
			});
			
			$("#cancel").click(function(){
				window.location.href=$("body").attr("contextpath")+"/pda/pdaTransitInnerIndex.do";
			});
			//返回
			$("#back").click(function(){
				window.location.href=$("body").attr("contextpath")+"/pda/menu.do";
			});
		});
		
		
		//退出登录
		$("#exit").click(function(){
			window.location.href=$("body").attr("contextpath")+"/pda/pdaExit.do";
		})
		
		
		
		function request(url, data, args){
            url=url+'?version='+new Date();
			var _data, options = this._ajaxOptions(url, data, args);
			options["type"] = "POST";
			$.extend(options,{
				async : false,
				success : function(data, textStatus){
					_data = data;
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
					_data = {};
					var exception = {};
					exception["message"] = "Error occurs when fetching data from url:" + this.url;
					exception["cause"] = textStatus? textStatus : errorThrown;
					_data["exception"] = exception;
				}
			});
			$.ajax(options);
			return _data;
		}
		</script>
	
</body>
</html>