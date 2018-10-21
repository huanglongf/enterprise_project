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
			
			$("#boxCode").focus();
			
		$(document).ready(function(){
			
			$("#picking1").show();
			
			//集货库位条码扫描
			$("#boxCode").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#boxCode").val() ;
					if(key!=""){
						$("#boxCodeOk").trigger("click");	
					}else {
						play();
						$("#msg").text("请输入周转箱编码！");
					}
				}
			});
			
			$("#boxCodeOk").click(function(){
				boxCode = $("#boxCode").val() ;
				if(boxCode==""){
					play();
					$("#msg").text("请输入周转箱编码！");
					return false;
				}
				var postData = {
						"cCode":boxCode
				}; 
				var flag=request($("body").attr("contextpath") + "/pda/pdaResetBox.do",postData);
				if(flag["flag"]=="success"){
						$("#msg").text("周转箱["+boxCode+"]释放成功！");
						$("#boxCode").val("") ;
				}else{
					play();
					if(flag["msg"]!=null&&flag["msg"]!=""){
						$("#msg").text(flag["msg"]);
					}else{
						$("#msg").text("系统异常！");
					}
					$("#boxCode").val("") ;
				}
				
				$("#boxCode").focus();
				clear();
				
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
		
