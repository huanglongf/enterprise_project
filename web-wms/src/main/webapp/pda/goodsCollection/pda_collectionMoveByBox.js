			var pickingBarCode;
			var pickingCode;
			var boxCode;
			var locationCode;
			var wp;
			var temporaryWp;
			var qty;
			var pbShort=new Array();//二级批次短拣
			var locShort=new Array();//库位短拣
			var collection;
			var wcList;
			var wcListOver={};
			$("#collectionCode").focus();
			
		$(document).ready(function(){
			
			$("#one").show();
			$("#two").hide();
			
			//集货库位条码扫描
			$("#collectionCode").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#collectionCode").val() ;
					if(key!=""){
						$("#collectionCodeOk").trigger("click");	
					}else {
						play();
						$("#msg").text("请输入集货库位条码！");
					}
				}
			});
			
			$("#collectionCodeOk").click(function(){
				$("#msg").text("");
				pickingBarCode = $("#collectionCode").val() ;
				if(pickingBarCode==""){
					play();
					$("#msg").text("请输入集货库位条码！");
					return false;
				}
				var postData = {
						"collectionCode":pickingBarCode
				}; 
				var flag=request($("body").attr("contextpath") + "/pda/queryCollectionBoxStatus.do",postData);
				if(flag["flag"]=="success"){
					if(flag["msg"]!=null&&flag["msg"]!=""){
						$("#msg").text(flag["msg"]);
						$("#collectionCode").val("") ;
					}else if(flag["wcList"]==null||flag["wcList"]==""){
						$("#msg").text("此集货库位没有周转箱！");
						$("#collectionCode").val("") ;
					}else{
						wcList=flag["wcList"].split(",");
						for(var idx in wcList){
							if(wcList[idx]!=null&&wcList[idx]!=""){
								wcListOver[wcList[idx]]=0;
							}
						}
						
						$("#one").hide();
						$("#two").show();
						$("#boxCode").focus();
						
					}
				}else{
					play();
					$("#msg").text("系统异常！");
					$("#move").val("") ;
				}
				
				
			});
			//返回
			$("#boxBack").click(function(){
				$("#one").show();
				$("#two").hide();
				$("#collectionCode").val("");
				wcList=null;
				wcListOver={};
				$("#collectionCode").focus();
			});
			//确认
			$("#boxOK").click(function(){
				$("#msg").text("");
				var boxCode = $("#boxCode").val() ;
				if(boxCode==""){
					play();
					$("#msg").text("请输入周转箱号！");
					return false;
				}
				
				if(wcListOver[boxCode]==null){
					play();
					$("#msg").text("此周转箱不属于当前集货区域！");
					$("#boxCode").val("");
					return false;
				}
				$("#boxCode").val("");
				wcListOver[boxCode]=1;
				for(var idx in wcListOver){
					if(wcListOver[idx]!=1){
						return false;
					}
				}
				var postData = {
						"collectionCode":pickingBarCode
				}; 
				var flag=request($("body").attr("contextpath") + "/pda/moveCollectionBox2.do",postData);		
				if(flag["flag"]=="success"){
					if(flag["msg"]!=null&&flag["msg"]!=""){
						$("#msg").text(flag["msg"]);
						$("#collectionCode").val("") ;
					}else{
						$("#one").show();
						$("#two").hide();
						$("#collectionQty").text("");
						$("#collectionOverQty").text("");
						$("#collectionOverCode").text("");
						$("#msg").text("此集货区域周转箱已成功移走！");
						$("#collectionQty").text(flag["collectionQty"]);
						$("#collectionOverQty").text(flag["collectionOverQty"]);
						$("#collectionOverCode").text(flag["collectionOverCode"]);
						$("#collectionCode").val("") ;
						wcList=null;
						wcListOver={};
						$("#collectionCode").focus();
					}
				}else{
					play();
					$("#msg").text("系统异常！");
					$("#move").val("") ;
				}	
						});
			
			//货箱条码扫描
			$("#boxCode").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#boxCode").val() ;
					if(key!=""){
						$("#boxOK").trigger("click");	
					}else {
						play();
						$("#msg").text("请输入周转箱号！");
					}
				}
			});
			
		
			
			//返回
			$("#collectionCodeBack").click(function(){
				window.location.href=$("body").attr("contextpath")+"/pda/menu.do";
			})
			//货箱返回
			$("#collectionCodeBack").click(function(){
				toPickingCode();
			})
			//库位返回
			$("#moveBack").click(function(){
				toPickingCode();
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
			$("#collectionCode").val("");
			$("#collectionCode").focus();
			$("#msg").text("");
		}
		
		//到库位扫描
		function toLocation(){
			$("#picking1").hide();
			$("#picking2").hide();
			$("#picking3").show();
			$("#move").val("");
			$("#move").focus();
			$("#msg").text("");
		}
		
		
		
		//到第一步
		function toPickingCode(){
			$("#picking1").show();
			$("#picking2").hide();
			$("#picking3").hide();
			$("#boxCode").focus();
			$("#boxCode").val("");
			$("#msg").text("");
			 clear();
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
			collection=null;
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
		
