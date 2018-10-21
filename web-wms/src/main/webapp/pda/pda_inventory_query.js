			var pickingCode;
			var barCode;
			var locationCode;
			var collection;
		function scanningBarcode(){
				$("#picking1").show();
				$("#picking2").hide();
				$("#picking3").hide();
				$("#skuBarCode").focus();
				$("#msg").text("");
			}
		
		$("#locationCode").focus();
		$(document).ready(function(){
			scanningBarcode();
			//inventoryQty();
			//商品条码扫描
			$("#barCode").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#barCode").val() ;
					if(key!=""){
						$("#confirm").trigger("click");	
					}else {
						play();
						$("#msg").text("请输扫描条码！");
					}
				}
			});
			//扫描库位条码
			$("#locationCode").keypress(function(evt){
					if (evt.keyCode === 13) {
						var key = $("#locationCode").val() ;
						if(key!=""){
							$("#confirm").trigger("click");	
						}else {
							play();
							$("#msg").text("请输扫描条码！");
						}
					}
				});
			
			$("#confirm").click(function(){
				barCode = $("#barCode").val() ;
				locationCode=$("#locationCode").val() ;
				$("#locationCode").focus();
				if(barCode==null&&locationCode==null){
					play();
					$("#msg").text("请扫描商品条码或库位编码！");
					return ;
				}
				if(barCode==''&&locationCode==''){
					play();
					$("#msg").text("请扫描商品条码或库位编码！");
					return ;
				}
				var postData = {
						"barCode":barCode,
						"locationCode":locationCode
				}; 
				$("#msg").text("");
				var flag=request($("body").attr("contextpath") + "/pda/getInventoryQty.do",postData);
				if(flag["result"]!=null){
					var	inventoryQty=new Array();
					inventoryQty=flag["result"];
					$("#inventorySku").empty();
					if(barCode!=null&&barCode!=""){
						$("#picking1").hide();
						$("#picking2").show();
						$("#picking3").hide();
						$("#barCodeValue").text(barCode);
						 $("#inventorySku").html("");
						for(var idx in inventoryQty){
							if(inventoryQty[idx].quantity>0){
					    	$("<tr><td>"+barCode+"</td><td>"+inventoryQty[idx].availQty+"</td><td>"+inventoryQty[idx].lockQty+"</td><td>"+inventoryQty[idx].quantity +"</td></tr>").appendTo($("#inventorySku"));
							}
						}
					}else{
						$("#inventorySkuLoc").html("");
						$("#loctionCodeValue").text(locationCode);
						$("#picking1").hide();
						$("#picking2").hide();
						$("#picking3").show();
						for(var idx in inventoryQty){
							if(inventoryQty[idx].quantity>0){
					    	$("<tr><td>"+inventoryQty[idx].barCode+"</td><td>"+inventoryQty[idx].availQty+"</td><td>"+inventoryQty[idx].lockQty+"</td><td>"+inventoryQty[idx].quantity +"</td></tr>").appendTo($("#inventorySkuLoc"));
							}
							}
					}
				}
				$("#barCode").focus();
				clear();
			});
			//返回
			$("#collectionCodeBack").click(function(){
				window.location.href=$("body").attr("contextpath")+"/pda/menu.do";
			});
			//返回
			$("#collectionCodeBack1").click(function(){
				$("#picking1").show();
				$("#picking2").hide();
				$("#picking3").hide();
			});
			//返回
			$("#collectionCodeBack2").click(function(){
				$("#picking1").show();
				$("#picking2").hide();
				$("#picking3").hide();
			});
			//货箱返回
			$("#collectionCodeBack").click(function(){
				toPickingCode();
			});
			//库位返回
			$("#moveBack").click(function(){
				toPickingCode();
			});
			//返回菜单
			$("#menu").click(function(){
				window.location.href=$("body").attr("contextpath")+"/pda/menu.do";
			});
			
			//退出登录
			$("#exit").click(function(){
				window.location.href=$("body").attr("contextpath")+"/pda/pdaExit.do";
			});
		});
		
		//清空缓存数据
		function clear(){
			boxCode=null;
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
		
