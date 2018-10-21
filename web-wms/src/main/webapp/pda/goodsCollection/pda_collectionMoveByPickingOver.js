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
			
			$("#collectionCode").focus();
			
			function overList(){
				$("#overList").empty();
				var flag=request($("body").attr("contextpath") + "/pda/findTwoPickingOver.do");
				if(flag!=null){
					var gcList=flag.gcList;
					for(var i in gcList){
						if(i==5){
							break;
						}
						$("<tr><td>"+gcList[i].collectionCode+"</td><td>"+gcList[i].plCode+"</td></tr>").appendTo($("#overList"));
					}
					$("#collectionListQty").text(gcList.length);
				}else{
					$("#collectionListQty").text(0);
				}
			}
		$(document).ready(function(){
			//$("#picking1").show();
			$("#one").show();
			$("#two").hide();
			overList();
			
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
				pickingBarCode = $("#collectionCode").val() ;
				if(pickingBarCode==""){
					play();
					$("#msg").text("请输入集货库位条码！");
					return false;
				}
				var postData = {
						"collectionCode":pickingBarCode
				}; 
				var flag=request($("body").attr("contextpath") + "/pda/moveCollectionBoxByPickingOver.do",postData);
				if(flag["flag"]=="success"){
					if(flag["msg"]!=null&&flag["msg"]!=""){
						$("#msg").text(flag["msg"]);
						$("#collectionCode").val("") ;
						$("#collectionCode").focus();
					}else{
						
						$("#msg").text("此集货区域周转箱已成功移走！");
						overList();
						$("#collectionCode").val("") ;
						$("#collectionCode").focus();
					}
				}else{
					play();
					$("#msg").text("系统异常！");
					$("#move").val("") ;
				}
				
				
			});
			//否
			$("#no").click(function(){
				$("#one").show();
				$("#two").hide();
				$("#collectionCode").val("");
				
			});
			//是
			$("#yes").click(function(){
				pickingBarCode = $("#collectionCode").val() ;
				if(pickingBarCode==""){
					play();
					$("#msg").text("请输入集货库位条码！");
					return false;
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
						
						$("#msg").text("此集货区域周转箱已成功移走！");
						overList();
						$("#collectionCode").val("") ;
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
						$("#boxCodeOk").trigger("click");	
					}else {
						play();
						$("#msg").text("请输入货箱条码！");
					}
				}
			});
			
			$("#boxCodeOk").click(function(){
				var key = $("#boxCode").val() ;
				if(key==""){
					play();
					$("#msg").text("请输入货箱条码！");
				}
				boxCode = $("#boxCode").val() ;
				var postData = {
						"code":boxCode					
				}; 
				//判断此货箱是否可用
				var flag=request($("body").attr("contextpath") + "/pda/checkCollectionBox.do",postData);
				if(flag["flag"]=="success"){
					if(flag["msg"]!=null&&flag["msg"]!=""){
						$("#zzx").text(boxCode);
						$("#collection").text(flag["msg"]);
						collection=flag["msg"];
						toBox();
					}else{
						$("#boxCode").val("") ;
						play();
						$("#msg").text("此周转箱无人工集货信息！");
						return false;
						
					}
				}else{
					play();
					$("#msg").text("系统异常！");
					 $("#boxCode").val("") ;
					return false;
				} 
				
				
			})
			
			//库位条码扫描
			$("#move").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#move").val() ;
					if(key=="OK"){
						$("#moveOk").trigger("click");	
					}else {
						play();
						$("#msg").text("请输入确认条码！");
					}
				}
			});
			
			$("#moveOk").click(function(){
				var key = $("#move").val() ;
				
				var postData = {
						"pickingBarCode":pickingBarCode
				}; 
				var flag=request($("body").attr("contextpath") + "/pda/moveCollectionBox.do",postData);
				if(flag["flag"]=="success"){
					toPickingCode();
					$("#msg").text("此配货批周转箱已成功移走！");
				}else{
					play();
					$("#msg").text("系统异常！");
					$("#move").val("") ;
				}
				
			})
			
		
			
		
		
			
		
			
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
		
