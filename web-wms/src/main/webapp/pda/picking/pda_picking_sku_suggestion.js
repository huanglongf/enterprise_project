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
			
			var staLineList;
			var plId;
			var barcodeList;
			var isLineOver=false;
			var isStaOver=false;
			var residueQty={};
			
			$("#pickingBarCode").focus();
			
		$(document).ready(function(){
			$("#me").text("");
			$("#picking1").show();
			$("#picking2").hide();
			$("#picking3").hide();
			$("#picking4").hide();
			$("#picking5").hide();
			$("#picking6").hide();
			
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
						$("#msg").text("请输入配货批次号！");
					}
				}
			});
			
			$("#pickingCodeOk").click(function(){
				pickingBarCode = $("#pickingBarCode").val() ;
				if(pickingBarCode==""){
					play();
					$("#msg").text("请输入配货批次号！");
					return false;
				}
				var postData = {
						"barCode":pickingBarCode						
				}; 
				var rs=request($("body").attr("contextpath") + "/pda/pdaFindStaLineBySuggestion.do",postData);
				if(rs.result=="success"){
					if(rs.pickingCodeError==null|| rs.pickingCodeError=="" ){
						if(rs.pickingErrorStatus==null|| rs.pickingErrorStatus=="" ){
							var data=rs.pickingData;
							
							if(rs.staLineList==null||rs.staLineList==""){
								play();
								$("#msg").text(pickingBarCode+"此批次已分拣完成！");
								$("#pickingBarCode").val("") ;
								return;
							}
							
							staLineList=rs.staLineList;
							pickingCode=data['code'];
							plId=data["id"];
							barcodeList=rs.barcodeMap;
							
							toBox();
						}else{
							play();
							$("#msg").text(pickingBarCode+"此批次不能分拣！");
							$("#pickingBarCode").val("") ;
						}
					}else{
						play();
						$("#msg").text(pickingBarCode+"此批次不存在！");
						$("#pickingBarCode").val("") ;
					}
				}else{
					$("#msg").text("系统异常！");
					$("#pickingBarCode").val("") ;
				}
				
				
			})
			
			//货箱条码扫描
			$("#skuBarCode").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#skuBarCode").val() ;
					if(key!=""){
						$("#skuBarCodeOk").trigger("click");	
					}else {
						play();
						$("#msg").text("请输入商品条码！");
					}
				}
			});
			
			$("#skuBarCodeOk").click(function(){
				$("#msg").text("");
				var key = $("#skuBarCode").val() ;
				if(key==""){
					play();
					$("#msg").text("请输入商品条码！");
					return;
				}
				var skuBarCode = $("#skuBarCode").val() ;
				var staLineId=checkedSkuBarcode(skuBarCode);
				if(staLineId==null||staLineId==""){
					play();
					$("#msg").text(skuBarCode+"此商品条码不正确！");
					$("#skuBarCode").val("") ;
					return;
				}
				var postData = {
						"pickingId":plId,
						"staLineId":staLineId,
						"isNeedCheck":false,
						"skuBarCode":skuBarCode
				}; 
				//记录拣货信息
				var flag=request($("body").attr("contextpath") + "/pda/pdaTwicePickingByBarcode.do",postData);
				if(flag.result=="success"){
					var idx;
					for(var i in staLineList){
						if(staLineId==staLineList[i].id){
							var completeQuantity=staLineList[i].completeQuantity;
							staLineList[i].completeQuantity=completeQuantity+1;
							if(flag.data.isLineOver){
								isLineOver=true;
								idx=i;
							}
							
							$("#ruleCode3").text(staLineList[i].ruleCode);
							$("#staCode3").text(staLineList[i].staCode);
							$("#pickingListCode3").text(pickingCode);
							$("#showSkuBarCode3").text(skuBarCode);
							
							$("#ruleCode4").text(staLineList[i].ruleCode);
							$("#staCode4").text(staLineList[i].staCode);
							$("#pickingListCode4").text(pickingCode);
							
							$("#pickingListCode5").text(pickingCode);
							
							break;
						}
					}
					if(flag.data.isLineOver){
						staLineList.splice(idx,1);
					}
					if(flag.data.isStaOver!=null&&flag.data.isStaOver!=""){
						isStaOver=flag.data.isStaOver;
					}
					
					toLocation();
					
				}else if(flag.result=="error"){
					play();
					$("#msg").text(flag.msg);
					 $("#skuBarCode").val("") ;
					return false;
				}else{
					play();
					$("#msg").text("系统异常！");
					 $("#skuBarCode").val("") ;
					return false;
				} 
				
			})
			
			//库位条码扫描
			$("#picking3Inp").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#picking3Inp").val() ;
					if(key=="OK"){
						$("#picking3Ok").trigger("click");	
					}else {
						play();
						$("#msg").text("请输入确认条码！");
					}
				}
			});
			
			$("#picking3Ok").click(function(){
				if(isStaOver){
					toSkuInfo();
				}else{
					toBox();
				}
			})
			
			//商品扫描
			$("#picking4Inp").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#picking4Inp").val() ;
					if(key=="OK"){
						$("#picking4Ok").trigger("click");	
					}else {
						play();
						$("#msg").text("请输入确认条码！");
					}
				}
			});
			
			$("#picking4Ok").click(function(){
				var isPickingOver=true;
				for(var i in staLineList){
					isPickingOver=false;
					break;
				}
				if(isPickingOver){
					var postData = {"pickingId":plId}; 
					//释放集货库位和周转箱
					request($("body").attr("contextpath") + "/pda/pdaResetBoxAndCollection.do",postData);
					
					toSkuShort();
				}else{
					toBox();
				}
			})
			
			$("#picking5Inp").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#picking5Inp").val() ;
					if(key=="OK"){
						$("#picking5Ok").trigger("click");	
					}else {
						play();
						$("#msg").text("请输入确认条码！");
					}
				}
			});
			
			$("#picking5Ok").click(function(){
				toPickingCode()
			})
			
			$("#pickingOver").click(function(){
				residuePickingQty();
				residueQtyList();
				toPickingOver();
			})
			
			$("#picking6Ok").click(function(){
				//释放集货库位和周转箱
				var postData = {"pickingId":plId}; 
				request($("body").attr("contextpath") + "/pda/pdaResetBoxAndCollection.do",postData);
				toPickingCode();
			})
			
			$("#picking6No").click(function(){
				toBox();
			})
			
			//返回
			$("#skuBarCodeBack").click(function(){
				toPickingCode();
			})
			
			
			//返回
			$("#pcBack").click(function(){
				window.location.href=$("body").attr("contextpath")+"/pda/menu.do";
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
		
		
		
		//到货箱扫描
		function toBox(){
			$("#picking1").hide();
			$("#picking2").show();
			$("#picking3").hide();
			$("#picking4").hide();
			$("#picking5").hide();
			$("#picking6").hide();
			$("#pickingCode").text(pickingCode);
			isLineOver=false;
			isStaOver=false;
			$("#skuBarCode").val("");
			$("#skuBarCode").focus();
			$("#msg").text("");
		}
		
		//到货格号
		function toLocation(){
			$("#picking1").hide();
			$("#picking2").hide();
			$("#picking3").show();
			$("#picking4").hide();
			$("#picking5").hide();
			$("#picking6").hide();
			$("#picking3Inp").val("");
			$("#picking3Inp").focus();
			$("#msg").text("");
		}
		
		//到货格号完成
		function toSkuInfo(){
			$("#picking1").hide();
			$("#picking2").hide();
			$("#picking3").hide();
			$("#picking4").show();
			$("#picking5").hide();
			$("#picking6").hide();
			$("#picking4Inp").val("");
			$("#picking4Inp").focus();
			$("#msg").text("");
		}
		
		//到批次完成
		function toSkuShort(){
			$("#picking1").hide();
			$("#picking2").hide();
			$("#picking3").hide();
			$("#picking4").hide();
			$("#picking5").show();
			$("#picking6").hide();
			$("#picking5Inp").val("");
			$("#picking5Inp").focus();
			$("#msg").text("");
			
		}
		
		//到强制完成
		function toPickingOver(){
			$("#picking1").hide();
			$("#picking2").hide();
			$("#picking3").hide();
			$("#picking4").hide();
			$("#picking5").hide();
			$("#picking6").show();
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
			$("#pickingBarCode").val("");
			$("#msg").text("");
			 clear();
			$("#pickingBarCode").focus();
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
			
			 staLineList=null;
			 plId=null;
			 barcodeList=null;
			 isLineOver=false;
			 isStaOver=false;
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
	
	
		function checkedSkuBarcode(value){
			for(var i in staLineList){
				if(staLineList[i].quantity>staLineList[i].completeQuantity){
					
					if(staLineList[i].barCode==value||staLineList[i].barCode == '00' + value){
						return staLineList[i].id;
					}
					var bcl=barcodeList[staLineList[i].barCode];
					if(bcl!=null){
						var bcs=bcl.split(',');
						for(var v in bcs){
							if(bcs[v] == value || bcs[v] == ('00'+value)){
								return staLineList[i].id;
							}
						}
					}
				}
			}
		}
		
		
		function residuePickingQty(){
			residueQty={};
			for(var i in staLineList){
				if(staLineList[i].quantity>staLineList[i].completeQuantity){
					var qty=staLineList[i].quantity-staLineList[i].completeQuantity;
					var q=residueQty[staLineList[i].ruleCode];
					if(q==null||q==""){
						q=0;
					}
					residueQty[staLineList[i].ruleCode]=q+qty;
				}
			}
		}
		
		function residueQtyList(){
			$("#notOverQtyList").empty();
			for(var i in residueQty){
				$("<tr><td>"+i+"</td><td>"+residueQty[i]+"</td></tr>").appendTo($("#notOverQtyList"));
				
			}
		}