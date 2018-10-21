			var pickingBarCode;
			var pickingCode;
			var boxCode;
			var locationCode;
			var wp;
			var temporaryWp;
			var qty;
			var pbShort=new Array();//二级批次短拣
			var locShort=new Array();//库位短拣
			var isAutoSingle=false;
			var isOver=false;
			var boxList;
			
			$("#pickingBarCode").focus();
			
		$(document).ready(function(){
			$("#me").text("");
			$("#picking1").show();
			$("#picking2").hide();
			$("#picking3").hide();
			$("#picking4").hide();
			$("#picking5").hide();
			$("#picking6").hide();
			$("#picking7").hide();
			$("#picking8").hide();
			$("#picking9").hide();
			
			//拣货条码扫描
			$("#pickingBarCode").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#pickingBarCode").val() ;
					if(key!=""){
						$("#pickingCodeOk").trigger("click");	
					}else {
						//alert("请输入拣货条码！");
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("请输入拣货条码！");
					}
				}
			});
			
			$("#pickingCodeOk").click(function(){
				pickingBarCode = $("#pickingBarCode").val() ;
				if(pickingBarCode==""){
					//alert("请输入拣货条码！");
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("请输入拣货条码！");
					return false;
				}
				var postData = {
						"barCode":pickingBarCode						
				}; 
				var flag=request($("body").attr("contextpath") + "/pda/initMongodbWhPickingInfo.do",postData);
				if(flag["flag"]=="success"){
					if(flag["msg"]!=null&&flag["msg"]!=""){
						//alert(flag["msg"]);
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text(flag["msg"]);
						if(null!=flag["me"]&&""!=flag["me"]&&flag["me"]){
							$("#me").text("预售订单");
				    	}
						$("#pickingBarCode").val("") ;
					}else{
						//获取需要拣货的信息
					    if(findLocationByPickingCode("loc")){
					    	if(null!=flag["me"]&&""!=flag["me"]&&flag["me"]){
					    		$("#me").text("预售订单");
					    	}
					    	wp=temporaryWp;
					    	temporaryWp=null;
					    	locationCode=null;
							toBox();
					    }
						
						
					}
				}else{
					//alert("系统异常！");
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					if(flag["msg"]!=null&&flag["msg"]!=""){
						$("#msg").text(flag["msg"]);
					}else{
						$("#msg").text("系统异常！");
					}
					$("#pickingBarCode").val("") ;
				}
				
				
			})
			
			//货箱条码扫描
			$("#boxCode").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#boxCode").val() ;
					if(key!=""){
						$("#boxCodeOk").trigger("click");	
					}else {
						//alert("请输入货箱条码！");
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("请输入货箱条码！");
					}
				}
			});
			
			$("#boxCodeOk").click(function(){
				var key = $("#boxCode").val() ;
				if(key==""){
					//alert("请输入货箱条码！");
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("请输入货箱条码！");
				}
				boxCode = $("#boxCode").val() ;
				var postData = {
						"code":pickingCode,
						"cCode":boxCode						
				}; 
				//判断此货箱是否可用
				var flag=request($("body").attr("contextpath") + "/pda/checkBox.do",postData);
				if(flag["flag"]=="success"){
					if(flag["msg"]!=null&&flag["msg"]!=""){
						//alert(flag["msg"]);
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text(flag["msg"]);
						 $("#boxCode").val("") ;
						return false;
					}
				}else{
					//alert("系统异常！");
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("系统异常！");
					 $("#boxCode").val("") ;
					return false;
				} 
				$("#boxCode1").text(boxCode);
				$("#boxCode2").text(boxCode);
				
				//获取已拣货的数量
				var pickingNum=request($("body").attr("contextpath") + "/pda/getPickingNum.do",postData);
				$("#pickingNum").text(pickingNum.msg);
				
				//判断是否是切换周转箱
				if(locationCode!=null){
					toSkuInfo();
				}else{
					//获取需要拣货的库位
				    /* if(findLocationByPickingCode("loc")){
				    	wp=temporaryWp;
				    	temporaryWp=null;
				    } */
						toLocation();
					/* var postData = {
							"code":pickingCode						
					}; 
					//初始化mongodb信息
					var flag=request($("body").attr("contextpath") + "/pda/initMongodbWhPickingInfo.do",postData);
					if(flag["flag"]=="success"){
						if(flag["msg"]!=null&&flag["msg"]!=""){
							jumbo.showMsg(flag["msg"]);
						}else{
						}
					}else{
						alert("系统异常！");
					} */
				}
				
				
			})
			
			//库位条码扫描
			$("#locationCode").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#locationCode").val() ;
					if(key!=""){
						$("#locationCodeOk").trigger("click");	
					}else {
						//alert("请输入库位条码！");
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("请输入库位条码！");
					}
				}
			});
			
			$("#locationCodeOk").click(function(){
				var key = $("#locationCode").val() ;
				if(key==""){
					//alert("请输入库位条码！");
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("请输入库位条码！");
				}
				locationCode = $("#locationCode").val() ;
				//校验库位是否正确
				if(locationCode!=wp.locationCode){
					//alert("库位扫描不正确！");
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("库位扫描不正确！");
					$("#locationCode").val("") ;
					return false;
				}
				//初始化待拣商品信息
				setSkuInfo();
				
				toSkuInfo();
			})
			
			//商品扫描
			$("#skuBarCode").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#skuBarCode").val() ;
					if(key!=""){
						$("#skuBarCodeOk").trigger("click");	
					}else {
						//alert("请输入商品条码！");
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("请输入商品条码！");
					}
				}
			});
			
			$("#skuBarCodeOk").click(function(){
				var skuBarCode = $("#skuBarCode").val() ;
				if(skuBarCode==""){
					//判断此商品是否拣货完成
					if(wp.planQty!=(wp.planQty-wp.qty)){
						//提示短拣
						setSkuShort();
						toSkuShort();
					}else{
						skuPickingOver();
					}
				}else if(wp.planQty==(wp.planQty-wp.qty)){
					skuPickingOver();
				}else{
					//校验条码并更新数量添加操作日志
					var postData = {
							"skuBarCode":skuBarCode,
							"pickingId":wp.id,
							"cCode":boxCode
					}; 
					var flag=request($("body").attr("contextpath") + "/pda/checkSkuBarCode.do",postData);
					if(flag["flag"]=="success"){
						if(flag["msg"]==null||flag["msg"]==""){
							qty=flag["qty"];
							//判断是否此商品在此库位是否拣货完成
							wp.qty=qty;
							if(qty<1){
								$("#qty").text(wp.planQty+"-"+wp.planQty);
								skuPickingOver();
								return false;
							}
							//初始化待拣商品信息
							setSkuInfo();
						}else{
							//alert(flag["msg"]);
							//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
							play();
							$("#msg").text(flag["msg"]);
							$("#skuBarCode").val("") ;
						}
					}else{
						//alert("系统异常！");
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("系统异常！");
						$("#skuBarCode").val("") ;
					}
				}
			})
			
			//更换周转箱
			$("#boxOver").click(function(){
				toBox();
			})
			
			//手动拣货完成
			$("#pbOver").click(function(){
				//二级拣货批拣货完成
				checkPickingBatchOver();
			})
			
			//确认商品短拣
			$("#skuShortOK").click(function(){
				//确认短拣
				var postData = {
						"pickingId":wp.id,
						"status":1
				}; 
				var flag=request($("body").attr("contextpath") + "/pda/updateWhPickingStatusByPicking.do",postData);
				if(flag["flag"]=="success"){
					//将短拣商品放到缓存
					pbShort[pbShort.length]=wp;
					
					if(!findLocationByPickingCode("over")){
						return false;
					}
					if(temporaryWp.locationCode==wp.locationCode){
						wp=temporaryWp;
						//切换商品
				    	setSkuInfo();
						
				    	toSkuInfo();
					}else{
						wp=temporaryWp;
						
						setSkuInfo()
						//切换库位
							toLocation();
					}
				}else{
					//alert(flag["msg"]);
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text(flag["msg"]);
					return false;
				}
				
				
			})
			//商品短拣继续拣货
			$("#skuShortNO").click(function(){
				toSkuInfo();
			})
			
			//确认二级批次短拣
			$("#pbShortOK").click(function(){
				//确认短拣
				var postData = {
						"code":pickingCode,
						"status":1,
						"pickingStatus":1
						
				}; 
				var flag=request($("body").attr("contextpath") + "/pda/updateWhPickingStatusByPicking.do",postData);
				if(flag["flag"]=="success"){
					//pickingBatchOver();
					toBoxReminder();
				}else{
					//alert(flag["msg"]);
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text(flag["msg"]);
					return false;
				}
				
				
			})
			//二级批次短拣继续拣货
			$("#pbShortNO").click(function(){
				var postData = {
						"code":pickingCode,
						"status":0
				}; 
				var flag=request($("body").attr("contextpath") + "/pda/updateWhPickingStatusByPicking.do",postData);
				if(flag["flag"]=="success"){
					findLocationByPickingCode("loc");
					wp=temporaryWp;
					//切换商品
			    	setSkuInfo();
					toLocation();
				}else{
					//alert(flag["msg"]);
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text(flag["msg"]);
					return false;
				}
			})
			
			//相关单据号扫描
			$("#slipCode").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#slipCode").val() ;
					if(key!=""){
						$("#slipCodeOK").trigger("click");	
					}else {
						//alert("请输入拣货条码！");
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("请输入相关单据号！");
					}
				}
			});
			
			$("#slipCodeOK").click(function(){
				var slipCode = $("#slipCode").val() ;
				if(slipCode==""){
					//alert("请输入拣货条码！");
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("请输入相关单据号！");
					return false;
				}
				var postData = {
						"code":pickingCode,
						"slipCode":slipCode
				}; 
				var flag=request($("body").attr("contextpath") + "/pda/checkStaAndBatch.do",postData);
				if(flag["flag"]=="success"){
					if(flag["msg"]!=null&&flag["msg"]==true){
						
						if(isOver){
							toPickingCode();
						}else{
							
							toBoxBySlipCode();
						}
					}else{
						$("#msg").text("相关单据号不正确！");
						$("#slipCode").val("") ;
					}
				}else{
					//alert("系统异常！");
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					if(flag["msg"]!=null&&flag["msg"]!=""){
						$("#msg").text(flag["msg"]);
					}else{
						$("#msg").text("系统异常！");
					}
					$("#slipCode").val("") ;
				}
				
				
			})
			
			$("#slipCodeNO").click(function(){
				if(isOver){
					toPickingCode();
				}else{
					toBox();
				}
			})
			
			
			$("#countBoxQtyOk").click(function(){
				pickingBatchOver();
			})
			
			$("#countBoxQtyNo").click(function(){
				toBoxMend();
			})
			
			$("#newBoxCode").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#newBoxCode").val() ;
					if(key!=""){
						$("#newBoxCodeOK").trigger("click");	
					}else {
						play();
						$("#msg").text("请输入周转箱号！");
					}
				}
			});
			
			$("#newBoxCodeOK").click(function(){
				var newBoxCode = $("#newBoxCode").val() ;
				if(newBoxCode==""){
					play();
					$("#msg").text("请输入周转箱号！");
					return false;
				}
				bindBoxByBoxCode(newBoxCode);
			})
			
			//漏绑
			$("#newBoxCodeNO").click(function(){
				toBoxReminder()
			})
			
			$("#newBoxCodeOver").click(function(){
				pickingBatchOver();
			})
			
			//返回
			$("#pcBack").click(function(){
				window.location.href=$("body").attr("contextpath")+"/pda/menu.do";
			})
			//货箱返回
			$("#boxBack").click(function(){
				toPickingCode();
			})
			//库位返回
			$("#locBack").click(function(){
				toPickingCode();
			})
			//商品返回
			$("#skuBack").click(function(){
				toLocation();
			})
			//返回菜单
			$("#menu").click(function(){
				window.location.href=$("body").attr("contextpath")+"/pda/menu.do";
			})
			
			//退出登录
			$("#exit").click(function(){
				window.location.href=$("body").attr("contextpath")+"/pda/pdaExit.do";
			})
		});
		
		//获取需要拣货的库位
		function findLocationByPickingCode(over){
			var postData = {
					"barCode":pickingBarCode,
					"status":0
			}; 
			var flag=request($("body").attr("contextpath") + "/pda/findLocationByPickingCode.do",postData);
			if(flag["flag"]=="success"){
				if(flag.wp!=null){
					temporaryWp=flag.wp;
					//判断小批次是否拣货完成
					var c=temporaryWp.code;
					if(pickingCode!=null && pickingCode != c){
						//小批次拣货完成
						checkPickingBatchOver();
						return false;
					}else{
						$("#locCode").text(temporaryWp.locationCode);
						$("#pickingCode").text(temporaryWp.sort);
						pickingCode=temporaryWp.code;
						return true;
					}
				}else{
					if(over=="over"){
						//二级拣货批拣货完成
						checkPickingBatchOver();
						return false;
					}else{
						return false;
						//alert("没有需要拣货");
					}
				}
			}else{
				//alert("系统异常！");
				//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
				play();
				$("#msg").text("系统异常！");
			}
			return false;
		}
		
		//初始化商品信息
		function setSkuInfo(){
			$("#locCode").text(wp.locationCode);
			$("#locCode1").text(wp.locationCode);
			$("#supplierCode").text(wp.supplierCode);
			$("#keyProperties").text(wp.keyProperties);
			var sbc=wp.barcodes[0];
			if(sbc!==undefined && sbc!==null){
				var front="";
				if(sbc.length>4){
					front=sbc.substring(0,sbc.length-4);
				}
				var back=sbc.substring(sbc.length-4);
				$("#sbc").html(front+"<font style='font-size:12px; font-weight:bold;' color='red'>"+back+"</font>");
			}
			//$("#planQty").text(wp.planQty);
			if(wp.skuName!=null&&wp.skuName!="null"&&wp.skuName!=''){
				if(wp.skuName.length>30){
					$("#skuName").text(wp.skuName.substring(0,29)+"..." );
				}else{
					$("#skuName").text(wp.skuName);
				}
				
			}
			$("#expDate").text(wp.expDate);
			var p=wp.planQty;
			var q=wp.qty
			$("#qty").text(p+"-"+(p-q));
			$("#skuBarCode").val("");
			$("#msg").text("");
			
		}
		//商品拣货完成
		function skuPickingOver(){
			//判断是否短拣
			/* if(qty!=0){
				//提示短拣
				setSkuShort();
				toSkuShort();
				
			}else{ */
				if(!findLocationByPickingCode("over")){
					return false;
				}
				if(temporaryWp.locationCode==wp.locationCode){
					wp=temporaryWp;
					//切换商品
			    	setSkuInfo();
					
				}else{
					wp=temporaryWp;
					
					setSkuInfo()
					//切换库位
						toLocation();
				}
				
			//}
			
		}
		
		//校验二级批次拣货完成
		function checkPickingBatchOver(){
			//查询是否有商品短拣
			var postData = {
					"code":pickingCode						
			}; 
			var flag=request($("body").attr("contextpath") + "/pda/findShortByPickingCode.do",postData);
			if(flag["flag"]=="success"){
				if(flag.wp.length>0){
					//pbShort=flag.wp;
					//二级批次短拣
					pbShort=flag.wp;
					setPbShort();
					toPbShort();
				}else{
					//拣货完成
					//pickingBatchOver();
					toBoxReminder();
					
				}
			}else{
				//alert("系统异常！");
				//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
				play();
				$("#msg").text("系统异常！");
			}
			
		}
		
		//二级批次拣货完成
		function pickingBatchOver(){
			//
			var postData = {
					"code":pickingCode						
			}; 
			var flag=request($("body").attr("contextpath") + "/pda/pickingBatchOver.do",postData);
			if(flag["flag"]=="success"){
				if(flag["autoSingle"]!=null&&flag["autoSingle"]==true){
					isAutoSingle=true;
				}
				if(flag["msg"]!=null&&flag["msg"]!=""){
					//alert(flag["msg"]);
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text(flag["msg"]);
					return;
				}
				var postData = {
						"barCode":pickingBarCode,
						"status":0
				}; 
				var flag1=request($("body").attr("contextpath") + "/pda/findLocationByPickingCode.do",postData);
				if(flag1.wp!=null){
					temporaryWp=flag1.wp;
					if(pickingCode!=temporaryWp.code){
						toSlipCode();
						
					}
				}else if(isAutoSingle==true){
					isOver=true;
					toSlipCode();
				}else{
					//清空缓存数据
					clear();
					//至编码扫描页面
					toPickingCode();
				}
				if(flag["over"]!=null&&flag["over"]!=""){
					$("#msg").text(flag["over"]);
				}
			}else{
				play();
				if(flag["msg"]!=null&&flag["msg"]!=""){
					$("#msg").text(flag["msg"]);
				}else{
					$("#msg").text("系统异常！");
				}
			}
		}
		
		function toBoxBySlipCode(){

			$("#locCode").text(temporaryWp.locationCode);
			$("#pickingCode").text(temporaryWp.sort);
			pickingCode=temporaryWp.code;
			
			wp=temporaryWp;
	    	temporaryWp=null;
	    	locationCode=null;
			toBox();
		}
		
		//到货箱扫描
		function toBox(){
			$("#picking1").hide();
			$("#picking2").show();
			$("#picking3").hide();
			$("#picking4").hide();
			$("#picking5").hide();
			$("#picking6").hide();
			$("#picking7").hide();
			$("#picking8").hide();
			$("#picking9").hide();
			$("#boxCode").val("");
			$("#boxCode").focus();
			$("#msg").text("");
		}
		
		//到库位扫描
		function toLocation(){
			$("#picking1").hide();
			$("#picking2").hide();
			$("#picking3").show();
			$("#picking4").hide();
			$("#picking5").hide();
			$("#picking6").hide();
			$("#picking7").hide();
			$("#picking8").hide();
			$("#picking9").hide();
			$("#locationCode").val("");
			$("#locationCode").focus();
			$("#msg").text("");
		}
		
		//到商品
		function toSkuInfo(){
			$("#picking1").hide();
			$("#picking2").hide();
			$("#picking3").hide();
			$("#picking4").show();
			$("#picking5").hide();
			$("#picking6").hide();
			$("#picking7").hide();
			$("#picking8").hide();
			$("#picking9").hide();
			$("#skuBarCode").val("");
			$("#skuBarCode").focus();
			$("#msg").text("");
		}
		
		//到商品短拣
		function toSkuShort(){
			$("#picking1").hide();
			$("#picking2").hide();
			$("#picking3").hide();
			$("#picking4").hide();
			$("#picking5").show();
			$("#picking6").hide();
			$("#picking7").hide();
			$("#picking8").hide();
			$("#picking9").hide();
			$("#msg").text("");
		}
		
		//到二级批次短拣
		function toPbShort(){
			$("#picking1").hide();
			$("#picking2").hide();
			$("#picking3").hide();
			$("#picking4").hide();
			$("#picking5").hide();
			$("#picking6").show();
			$("#picking7").hide();
			$("#picking8").hide();
			$("#picking9").hide();
			$("#msg").text("");
		}
		
		//到第一步
		function toPickingCode(){
			$("#picking1").show();
			$("#picking2").hide();
			$("#picking3").hide();
			$("#picking4").hide();
			$("#picking5").hide();
			$("#picking6").hide();
			$("#picking7").hide();
			$("#picking8").hide();
			$("#picking9").hide();
			$("#pickingBarCode").focus();
			$("#pickingBarCode").val("");
			$("#msg").text("");
			 clear();
		}
		
		function toSlipCode(){
			$("#picking1").hide();
			$("#picking2").hide();
			$("#picking3").hide();
			$("#picking4").hide();
			$("#picking5").hide();
			$("#picking6").hide();
			$("#picking7").show();
			$("#picking8").hide();
			$("#picking9").hide();
			$("#slipCode").focus();
			$("#slipCode").val("");
			$("#msg").text("");
		}
		
		function toBoxReminder(){
			findBindBoxByPickingCode();
			$("#picking1").hide();
			$("#picking2").hide();
			$("#picking3").hide();
			$("#picking4").hide();
			$("#picking5").hide();
			$("#picking6").hide();
			$("#picking7").hide();
			$("#picking8").show();
			$("#picking9").hide();
			/*$("#countBoxQtyInp").focus();
			$("#countBoxQtyInp").val("");*/
			$("#msg").text("");
		}
		
		function toBoxMend(){
			$("#picking1").hide();
			$("#picking2").hide();
			$("#picking3").hide();
			$("#picking4").hide();
			$("#picking5").hide();
			$("#picking6").hide();
			$("#picking7").hide();
			$("#picking8").hide();
			$("#picking9").show();
			$("#newBoxCode").focus();
			$("#newBoxCode").val("");
			$("#msg").text("");
		}
		
		
		
		//设置商品短拣信息
		function setSkuShort(){
			$("#sortPqty").text(wp.planQty);
			$("#sortSqty").text(wp.planQty-wp.qty);
			$("#sortQqty").text(wp.qty);
			
			
		}
		//设置二级批次短拣信息
		function setPbShort(){
			$("#pbCode").text(pickingBarCode);
			$("#pbSortTable").empty();
			for(var idx in pbShort){
		    	$("<tr><td>"+pbShort[idx].locationCode+"</td><td>"+pbShort[idx].barcodes[0]+"</td><td>"+pbShort[idx].supplierCode+"</td><td>"+pbShort[idx].qty +"</td></tr>").appendTo($("#pbSortTable"));
			}
		}
		
		//清空缓存数据
		function clear(){
			pickingCode=null;
			boxCode=null;
			locationCode=null;
			wp=null;
			temporaryWp=null;
			qty=null;
			pbShort=new Array();
			isAutoSingle=false;
			isOver=false;
		}
		
		function request(url, data, args){
			url=url+'?version='+new Date();
			var _data, options = this._ajaxOptions(url, data, args);
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
			//console.dir(_data);
			return _data;
		}
		//播放提示音
		/*function playMusic(url){
		    var audio = document.createElement('audio');
		    var source = document.createElement('source');   
		    source.type = "audio/mpeg";
		    source.type = "audio/mpeg";
		    source.src = url;   
		    source.autoplay = "autoplay";
		    source.controls = "controls";
		    audio.appendChild(source); 
		    audio.play();
//		    playMusic("../../wms/images/windows.wav");
		 }*/

		
function bindBoxByBoxCode(newBoxCode){
	$("#msg").text("");
	var postData = {
			"code":pickingCode,
			"cCode":newBoxCode
	}; 
	var flag=request($("body").attr("contextpath") + "/pda/bindBoxByBoxCode.do",postData);
	if(flag["flag"]=="success"){
		if(flag["msg"]==null||flag["msg"]==""){
			$("#newBoxCode").val("") ;
		}else{
			play();
			$("#msg").text(flag["msg"]);
			$("#newBoxCode").val("") ;
		}
	}else{
		play();
		$("#msg").text("系统异常！");
		$("#newBoxCode").val("") ;
	}
}		

function findBindBoxByPickingCode(){
	var postData = {
			"code":pickingCode
	}; 
	var flag=request($("body").attr("contextpath") + "/pda/findBindBoxByPickingCode.do",postData);
	if(flag["flag"]=="success"){
		boxList=flag["boxList"];
		if(boxList==null||boxList==""){
			$("#countBoxQty").text("0");
		}else{
			$("#countBoxQty").text(boxList.length);
			setBoxList();
		}
	}else{
		play();
		$("#msg").text("系统异常！");
	}
}
//设置已绑定周转箱显示
function setBoxList(){
	$("#bindBoxList").empty();
	var trStr="";
	for(var idx in boxList){
		if(idx%2==0){
			trStr="<tr ><td style='width: 100px;'>"+boxList[idx]+"</td>";
		}else{
			trStr+="<td style='width: 100px;'>"+boxList[idx]+"</td></tr>";
			$(trStr).appendTo($("#bindBoxList"));
			trStr="";
		}
	}
	if(boxList!=null&&boxList.length!=0&&(boxList.length%2==1)){
		trStr+="<td style='width: 100px;'></td></tr>";
		$(trStr).appendTo($("#bindBoxList"));
		trStr="";
	}
}