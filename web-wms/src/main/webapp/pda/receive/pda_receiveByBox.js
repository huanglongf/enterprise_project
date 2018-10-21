var staCode		
$(document).ready(function(){
			//返回
			$("#receiveBack").click(function(){
				window.location.href=$("body").attr("contextpath")+"/pda/menu.do";
			});
			
			$("#staCode").focus();
			
			//作业单条码扫描
			$("#staCode").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#staCode").val();
					if(key!=""){
						$("#receiveOk").trigger("click");
					}else {
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("请输入作业指令!");
						return;
					}
				}
			});
			
			//退出登录
			$("#exit").click(function(){
				window.location.href=$("body").attr("contextpath")+"/pda/pdaExit.do";
			})
			
			$("#receiveOk").click(function(){
				staCode = $("#staCode").val();
				if(staCode==""){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("请输入作业指令条码!");
					return false;
				}
				var inventoryStatus=$("#inventoryStatus").val();
				if(inventoryStatus==""){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("请选择库存状态");
					return false;
				}
				var postData = {
						"staCode":staCode,
						"inventoryStatus":inventoryStatus
				};
				var flag=request($("body").attr("contextpath") + "/pda/pdaReceiveByBox.do",postData);
				if(flag["flag"]=="success"){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("收货成功!");
	                $("#staCode").val("");
	                return;
				}else if(flag["flag"]=="error"){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("收货成功,没有推荐到库位和弹出口");
					return;
				}else if(flag["flag"]=="none"){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("仓库未维护SKU种类数及SKU总数");
					return;
				}else if (flag["flag"]=="false"){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("扫描指令未找到");
					return;
				}else if(flag["flag"]=="none2"){
					play();
					$("#msg").text("收货sku总数或者sku种数超出仓库配置");
					return;
				}else if(flag["flag"]=="input"){
					play();
					$("#msg").text("单号不存在或按箱收货的商品必须是非效期商品、非SN商品");
					return;
				}
				else if(flag["flag"]=="err"){
					play();
					$("#msg").text("系统异常");
					return;
				}
				else if(flag["flag"]=="error4"){
					play();
					$("#msg").text("拆单的主作业单不能使用此功能进行收货");
					return;
				}
				else if(flag["flag"]=="threeDimensional"){
					play();
					$("#msg").text("该箱中有缺失三维信息商品！");
					return;
				}
				else {
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("此作业单已收货完毕");
					return;
				}
			});
			
	    });
		

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
//    playMusic("../../wms/images/windows.wav");
}*/

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
			//console.dir(_data);
			return _data;
		}		
		