	        var isPacking;
			var staCode;
			var cartonBox;
			var boxCode;
			var qty;
			var inventoryStatus;
			var quantitativeModel;
			var dataList;
			var skuEntity;
			var typeAndsnList; 
			var typeAndsnData; //sn和残次data
			var snList;        //sn 集合
			var data;
			var skuNum;
			var totalNum2;
			var totalNum1;
			var damageNum;
			var totalSnNum2;
			var key;
			var expirationDate;
			var productionDate;
			var container;

		$(document).ready(function(){
			firstDiv();
			//作业单条码扫描
			$("#staCode").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#staCode").val();
					if(key!=""){
						isPacking=$("#isPacking").val();
						inventoryStatus=$("#inventoryStatus").val();
						quantitativeModel=$("#quantitativeModel").val();
						$("#receiveOk").trigger("click");	
					}else {
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("请输入作业指令!");
						return;
					}
				}
			});
			
			$("#receiveOk").click(function(){
				staCode = $("#staCode").val();
				isPacking=$("#isPacking").val();
				inventoryStatus=$("#inventoryStatus").val();
				quantitativeModel=$("#quantitativeModel").val();
				if(staCode==""){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("请输入作业指令条码!");
					return false;
				}
				var postData = {
						"staCode":staCode						
				};
				
				var flag=request($("body").attr("contextpath") + "/pda/initMongodbWhReceiveInfo.do",postData);
				if(flag["flag"]=="success"){
					staCode=flag["sta"];
					var postData1 = {
							"staCode":staCode						
					};
					$("#unType").empty();
					inventoryStatus=$("#inventoryStatus").val();
					if("残次品"==inventoryStatus){
						var flags=request($("body").attr("contextpath") + "/pda/getImperfectByChannel.do",postData1);
						if(flags["result"]=="success"){
							var result=flags["object"];
							$("<option value=''>请选择</option>").appendTo($("#unType"));
							for(var idx in result){
								$("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($("#unType"));
							}
						}else{
							var flagMsg=request($("body").attr("contextpath") + "/pda/getImperfectByOuId.do");
							if(flagMsg["result"]=="success"){
								var result=flagMsg["object"];
								$("<option value=''>请选择</option>").appendTo($("#unType"));
								for(var idx in result){
									$("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($("#unType"));
								}
							}else{
								$("<option value=''>请选择</option>").appendTo($("#unType"));
								$("<option value='其他'>其他</option>").appendTo($("#unType"));
							}
						}
					}
					if("是"==isPacking){    //拆箱收货
						toSku();
						$("#skuNum").val("");
						return;
					}else{
						toBox();
						$("#cartonBox").val("");
						return;
					}
					
				}else if(flag["flag"]=="input"){
					$("#msg").text("子作业单未收货完,主作业单不能收货");
					return;
				}
				else if(flag["flag"]=="error"){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text(flag["msg"]);
					return;
				}else{
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text(flag["flag"]);
					return;
				}
			});
			
			//扫描容器号
			$("#cartonBox").keypress(function(evt){
				$("#msg").text("");
				if (evt.keyCode === 13) {
					var key = $("#cartonBox").val();
					if(key!=""){
						$("#cartonBoxOk").trigger("click");	
					}else {
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("请输入容器号!");
						return;
					}
				}
			});
			
			$("#cartonBoxOk").click(function(){
				cartonBox = $("#cartonBox").val();
				if(cartonBox==""){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("请输入容器号!");
					return false;
				}
				var flag=null;
				if("是"==isPacking){
					container=cartonBox;
					var postData = {
							"cartonBox":cartonBox,
							"staCode":staCode,
							"isPacking":isPacking,
							"barCode":key
							
					};
					flag=request($("body").attr("contextpath") + "/pda/initMongodbWhcartonBox1.do",postData);
				}else{
					var postData = {
							"cartonBox":cartonBox,
							"staCode":staCode,
							"isPacking":isPacking
							
					};
					flag=request($("body").attr("contextpath") + "/pda/initMongodbWhcartonBox.do",postData);
				}
	
					
				
				
				if(flag["flag"]=="success"){
					if("是"==isPacking){
						asnOver();
						return;
					}else{
						toSku();
						return;
					}
					
				}
				else{
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text(flag["flag"]);
					return;
				}
			});
			
			//扫描sku
			$("#sku").keypress(function(evt){
				if (evt.keyCode === 13) {
					key = $("#sku").val();
					if(key!=""){
						quantitativeModel=$("#quantitativeModel").val();
						if("批量模式"==quantitativeModel){
							$("#skuNum").focus();
						}else{
							$("#skuOk").trigger("click");	
						}
					}else {
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("请输入sku!");
						return;
					}
				}
			});
			
			$("#skuNum").keypress(function(evt){
				if(evt.keyCode===13){
					skuNum=$("#skuNum").val();
					if(skuNum!=""){
						$("#skuOk").trigger("click");
						return;
					}else{
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("请输入数量!");
						return;
					}
				}
			});
			
			$("#skuOk").click(function(){
				key = $("#sku").val();
				if(key==""){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("请输入sku!");
					return;
				}
				quantitativeModel=$("#quantitativeModel").val();
				if("批量模式"==quantitativeModel){
					var num=$("#skuNum").val();
					if(num==""){
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("请输入数量!");
						return;
					}
				}
				clear();
				skuNum=$("#skuNum").val();
				if(null==dataList||undefined==dataList||''==dataList){
					dataList=new Array();
				}
				//staCode = $("#staCode").val();
				
				if("批量模式"==quantitativeModel){
					mogodbSum=$("#skuNum").val();
				}else{
					mogodbSum=1;
				}
				
				var postData = {
						"staCode":staCode,
						"barCode":key,
						"qty":mogodbSum
			    };
				var flag=request($("body").attr("contextpath") + "/pda/verifySku.do",postData); //验证sku是否在计划量中
				if(flag["flag"]=="success"){
					var postData1 = {
							"staCode":staCode,
							"barCode":key
							
				    };
					skuEntity=request($("body").attr("contextpath") + "/pda/findPDASkuByBarCode.do",postData1);
					//sku 获取箱号
					if("是"==isPacking){
						var postData1 = {
								"staCode":staCode,
								"barCode":key
								
					    };//1.
						var flags=request($("body").attr("contextpath") + "/pda/packingfindbyNO.do",postData1);
						if(flags["flag"]!='error'){
							container=flags["flag"]["cartonCode"];
						}
					}
					data = {};
					data["skuId"]=skuEntity["flag"]["id"];
					dataList[dataList.length]=data;
					data["skubarcode"]=key;
					var mogodbSum;
					if("逐件模式"==quantitativeModel){
						data["skuNum"]=1;
						mogodbSum=1;
					}else{
						data["skuNum"]=$("#skuNum").val();
					}
					/*var postData1 = {
							"staCode":staCode,
							"barCode":key,
							"qty":mogodbSum
					};
					var flags=request($("body").attr("contextpath") + "/pda/updateMogodbRestQty.do",postData1);*/
					/*if(flags["msg"]!="error"){*/
						inventoryStatus=$("#inventoryStatus").val();
						if("33"==skuEntity["flag"]["storemode"].value){  //检验商品是否是效期商品
							toTime();
							$("#expirationDate").focus();
							$("#expirationDate").val("");
							$("#productionDate").val("");
							return;
						}else if("残次品"==inventoryStatus){
							toDamage();
							$("#unType").val("");
							$("#unReason").val("");
							return;
						}else if(null!=skuEntity["flag"]["isSnSku"]&&skuEntity["flag"]["isSnSku"]){   //sn商品
							toSN();
							$("#sn").val("");
							return;
						}else{
							var postData1 = {
									"staCode":staCode,
									"barCode":key,
									"qty":mogodbSum
							};
							var flags=request($("body").attr("contextpath") + "/pda/updateMogodbRestQty.do",postData1);
							if(flags["msg"]!="error"){
								if("是"==isPacking){
									toBox();
									//$("#cartonBox").val("");
									return;
								}
								var postData2={"staCode":staCode};
								var restQty=request($("body").attr("contextpath") + "/pda/findMongodbByRestQty.do",postData2); //查询作业单下的所有商品计划量 如果都为0，默认收货完毕，跳转作业单扫描页面
								if(restQty["flag"]=="success"){  //未完成 继续扫描sku 
									toSku();
									clear();
									return;
								}else{
									//生成数据
									asnOver();
									firstDiv();
									clear();
									return;
								}
							}else{
								//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
								play();
								$("#msg").text("输入的数量大于计划量!");
								return;
							}
							
						}
						
						/*}else{
						playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						$("#msg").text("输入的数量大于计划量!");
						return;
					}*/
				}else if(flag["flag"]=="none"){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("该sku不在单据"+staCode+"计划中或计划量已满!");
					return;
                }else if(flag["flag"]=="SkuThreeDimensional"){
                    //playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
                    play();
                    $("#msg").text("该商品缺失三维信息!");
                    return;
				}
			});
			
			//扫描录入失效时间
			$("#expirationDate").keypress(function(evt){
				if (evt.keyCode === 13) {
					$("#msg").text("");
					var expirationDate1=$("#expirationDate").val();
					if(null!=expirationDate1&&""!=expirationDate1&&undefined!=expirationDate1){
						var a = /^(\d{4})(\d{2})(\d{2})$/;
						if (!a.test(expirationDate1)) { 
							//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
							play();
							$("#msg").text("时间格式不正确(正确 YYYYMMDD)");
							return;
						}
					}
					$("#productionDate").focus();	
				}
			});
			
			//扫描录入生产时间
			$("#productionDate").keypress(function(evt){
				if (evt.keyCode === 13) {
					$("#msg").text("");
					var productionDate1=$("#productionDate").val();
					if(null!=productionDate1&&""!=productionDate1&&undefined!=productionDate1){
						var a = /^(\d{4})(\d{2})(\d{2})$/;
						if (!a.test(productionDate1)) { 
							//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
							play();
							$("#msg").text("时间格式不正确(正确 YYYYMMDD)");
							return;
						}
					}
					$("#timeCodeOk").trigger("click");
				}
			});
			//录入时间
			$("#timeCodeOk").click(function(){
				if(null==data||undefined==data||''==data){
					data={};
				}
				if(null==dataList||undefined==dataList||''==dataList){
					dataList=new Array();
					dataList[dataList.length]=data;
				}
				expirationDate=$("#expirationDate").val();
				productionDate=$("#productionDate").val();
				if((expirationDate=='' || expirationDate==undefined || expirationDate==null)&&((productionDate=='' || productionDate==undefined || productionDate==null))){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("sku为效期商品,生产日期和失效时间二者必填之一");
					return;
				}
				data["expirationDate"]=expirationDate;
				data["productionDate"]=productionDate;
				inventoryStatus=$("#inventoryStatus").val();
				if("残次品"==inventoryStatus){
					quantitativeModel=$("#quantitativeModel").val();
					if("逐件模式"==quantitativeModel){
						toDamage();
						$("#unType").focus();
						return;
					}else if("批量模式"==quantitativeModel){
						toDamage();
						$("#damageNum").focus();
						return;
					}
				}else{
					if(null!=skuEntity["flag"]["isSnSku"]&&skuEntity["flag"]["isSnSku"]){   //sn商品
						toSN();
					    $("#sn").val("");
						return;
					}else{
						if("批量模式"==quantitativeModel){
							mogodbSum=skuNum;
						}else{
							mogodbSum=1;
						}
						//staCode = $("#staCode").val();
						var postData1 = {
								"staCode":staCode,
								"barCode":key,
								"qty":mogodbSum
						};
						var flags=request($("body").attr("contextpath") + "/pda/updateMogodbRestQty.do",postData1);
						if(flags["msg"]!="error"){
							if("是"==isPacking){
								toBox();
								//$("#cartonBox").val("");
								return;
							}
							var postData={"staCode":staCode};
							var restQty=request($("body").attr("contextpath") + "/pda/findMongodbByRestQty.do",postData); //查询作业单下的所有商品计划量 如果都为0，默认收货完毕，跳转作业单扫描页面\
							if(restQty["flag"]=="success"){  //未完成
								toSku();
								$("#sku").val("");
								$("#skuNum").val("");
								return;
							}else{
								if("是"==isPacking){
									toBox();
									$("#cartonBox").val("");
									return;
								}
								asnOver();
								//生成数据
								firstDiv();
								$("#skuNum").val("");
								return;
							}
						}else{
							//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
							play();
							$("#msg").text("输入的数量大于计划量!");
							return;
						}
					}
				}
			});
			
			/*//录入残次数量
			$("#damageNum").keypress(function(evt){
				var damageNum1=$("#damageNum").val();
				if (evt.keyCode === 13) {
					if(null!=damageNum1&&''!=damageNum1){
						if(null!=damageNum&&''!=damageNum){
							var num=parseInt(damageNum1)+parseInt(damageNum);
							if(parseInt(num)>parseInt(skuNum)){
								playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
								$("#msg").text("输入数量超出计划量!");
								return;
							}
						}else{
							if(parseInt(damageNum1)>parseInt(skuNum)){
								playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
								$("#msg").text("输入数量超出计划量!");
								return;
							}
							
						}
						$("#unType").focus();
					}else{
						playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						$("#msg").text("输入残次数量!");
						return;
					}
				}
			});*/
			
			//录入残次类型
			$("#unType").keypress(function(evt){
				var unType1=$("#unType").val();
				if (evt.keyCode === 13) {
					if(null!=unType1&&''!=unType1){
						$("#unReason").focus();
					}else{
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("输入残次类型!");
						return;
					}
				}
			});
			
			//录入残次原因
			$("#unReason").keypress(function(evt){
				var unReason1=$("#unReason").val();
				if (evt.keyCode === 13) {
					if(null!=unReason1&&''!=unReason1){
						$("#damageCodeOk").trigger("click");
					}else{
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("输入残次原因!");
						return;
					}
				}
			});
			
			//残次DIV 提交
			$("#damageCodeOk").click(function(){
				if(data =='' || data ==undefined || data ==null){
					data = {};
				}
				if(typeAndsnData=='' || typeAndsnData==undefined || typeAndsnData==null){
					typeAndsnData={};
					if(null!=typeAndsnList){
						typeAndsnList[typeAndsnList.length]=typeAndsnData;
					}
				}
				if(typeAndsnList=='' || typeAndsnList==undefined || typeAndsnList==null){
					typeAndsnList=new Array();
					typeAndsnList[typeAndsnList.length]=typeAndsnData;
				}
				if(null==dataList||undefined==dataList||''==dataList){
					dataList=new Array();
					dataList[dataList.length]=data;
				}
				var unType=$("#unType").val();
			    var unReason=$("#unReason").val();
			    if(unType=='' || unType==undefined || unType==null){
			    	//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
			    	play();
					$("#msg").text("请输入残次类型!");
					return;
			    }
			    if(unReason=='' || unReason==undefined || unReason==null){
			    	//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
			    	play();
					$("#msg").text("请输入残次原因!");
			    	return;
			    }
			    if("批量模式"==quantitativeModel){
			    	var damageNum1=$("#damageNum").val();
			    	if(null!=damageNum1&&''!=damageNum1){
						if(null!=damageNum&&''!=damageNum){
							var num=parseInt(damageNum1)+parseInt(damageNum);
							if(parseInt(num)>parseInt(skuNum)){
								//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
								play();
								$("#msg").text("输入数量超出计划量!");
								return;
							}
						}else{
							if(parseInt(damageNum1)>parseInt(skuNum)){
								//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
								play();
								$("#msg").text("输入数量超出计划量!");
								return;
							}
							
						}
						//$("#unType").focus();
					}else{
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("输入残次数量!");
						return;
					}
			    }
			    data["typeAndsnList"]=typeAndsnList;
				cartonBox = $("#cartonBox").val();
				//staCode = $("#staCode").val();
				
			    quantitativeModel=$("#quantitativeModel").val();
			    if("批量模式"==quantitativeModel){
			    	if(null==damageNum||''==damageNum||undefined==damageNum){
			    		damageNum=0;
			    	}
			    	var damageNum1=$("#damageNum").val();
					var num=parseInt(damageNum)+parseInt(damageNum1);
					damageNum=num;
					typeAndsnData["damageNum"]=damageNum1;
					mogodbSum=damageNum1;
			    }else{
			    	typeAndsnData["damageNum"]=1;
			    	mogodbSum=1;
			    }
			   
			    typeAndsnData["unType"]=unType;
			    typeAndsnData["unReasonList"]=unReason;
			    /*var postData1 = {
						"staCode":staCode,
						"barCode":key
						
			    };
			    skuEntity=request($("body").attr("contextpath") + "/pda/findPDASkuByBarCode.do",postData1);*/
			    if(null!=skuEntity["flag"]["isSnSku"]&&skuEntity["flag"]["isSnSku"]){  //sn商品
					toSN();
			        $("#sn").focus();
			        $("#sn").val("");
			        $("#totalSnNum2").text(0);
			        totalSnNum2=0;
					return;
				}else if("批量模式"==quantitativeModel){    //循环5 模块
					skuNum=$("#skuNum").val();
					$("#totalNum2").text(damageNum);
					if(parseInt(damageNum)<parseInt(skuNum)){
						toDamage();
						$("#damageNum").val("");
						$("#unReason").val("");
						$("#unType").val("");
						return;
					}else{
						var postData1 = {
								"staCode":staCode,
								"barCode":key,
								"qty":skuNum
						};
						var flags=request($("body").attr("contextpath") + "/pda/updateMogodbRestQty.do",postData1);
						if(flags["msg"]!="error"){
							if("是"==isPacking){
								toBox();
								return;
							}
							asnOver();
							return;
							/*var postData={"staCode":staCode};
							var restQty=request($("body").attr("contextpath") + "/pda/findMongodbByRestQty.do",postData); //查询作业单下的所有商品计划量 如果都为0，默认收货完毕，跳转作业单扫描页面
							if(restQty["flag"]=="success"){  //未完成
								toSku();
								$("#skuNum").val("");
								$("#sku").focus();
								return;
							}else{
								
								asnOver();
								clear();
								$("#skuNum").val("");
								//生成数据
								firstDiv();
								return;
							}*/
						}
						else{
							//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
							play();
							$("#msg").text("输入的数量大于计划量!");
							return;
						}
						
					}
				}else{
					var postData1 = {
							"staCode":staCode,
							"barCode":key,
							"qty":mogodbSum
					};
					var flags=request($("body").attr("contextpath") + "/pda/updateMogodbRestQty.do",postData1);
					if(flags["msg"]!="error"){
						if("是"==isPacking){
							toBox();
							$("#cartonBox").val("");
							return;
							/*var postData={"staCode":staCode,"barCode":key};
							var restQty=request($("body").attr("contextpath") + "/pda/findMongodbByRestQty.do",postData);*/ //查询作业单下的所有商品计划量 如果都为0，默认收货完毕，跳转作业单扫描页面
						}else{
							asnOver();
							return;
						}
							/*....
							var postData={"staCode":staCode};
							var restQty=request($("body").attr("contextpath") + "/pda/findMongodbByRestQty.do",postData); //查询作业单下的所有商品计划量 如果都为0，默认收货完毕，跳转作业单扫描页面
						}
						if(restQty["flag"]=="success"){  //未完成
							toSku();
							$("#skuNum").val("");
							$("#sku").focus();
							return;
						}else{
							asnOver();
							clear();
							$("#skuNum").val("");
							//生成数据
							firstDiv();
							return;
						}*/
					}else{
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("输入的数量大于计划量!");
						return;
					}
					
				}
			});
			
			//扫描sn号
			$("#sn").keypress(function(evt){
				if (evt.keyCode === 13) {
					var key = $("#sn").val();
					if(key!=""){ //校验sn唯一性
						var postData1={"sn":key};
						var flags=request($("body").attr("contextpath") + "/pda/findBySn.do",postData1);
						if(flags["result"]!="success"){
							play();
							$("#msg").text("此sn号已存在!");
							return;
						}else{
							if(null!=snList&&undefined!=snList&&''!=snList){
								for(var i=0;i<snList.length;i++){
									 if(snList[i]==key){
										 play();
										 $("#msg").text("此sn号已存在!");
										 return;
									 }
								}
							}
						}
						$("#snOK").trigger("click");	
					}else {
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("请输入sn号");
						return;
					}
				}
			});
			
			$("#snOK").click(function(){
				isPacking=$("#isPacking").val();
				var snval = $("#sn").val();
				if(""==snval){
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("请输入sn号");
					return;
				}
				//staCode=$("#staCode").val();
				var postData={"staCode":staCode};
				if(snList==''||snList==undefined||snList==null){
					snList=new Array();
				}
				snList.push(snval);
				if(typeAndsnData=='' || typeAndsnData==undefined || typeAndsnData==null){
					typeAndsnData={};
					if(null!=typeAndsnList){
						typeAndsnList[typeAndsnList.length]=typeAndsnData;
					}
				}
				if(null==data||undefined==data||''==data){
					data={};
				}
				if(typeAndsnList=='' || typeAndsnList==undefined || typeAndsnList==null){
					typeAndsnList=new Array();
					typeAndsnList[typeAndsnList.length]=typeAndsnData;
				}
				if(null==dataList||undefined==dataList||''==dataList){
					dataList=new Array();
					dataList[dataList.length]=data;
				}
				typeAndsnData["sn"]=snList;
				data["typeAndsnList"]=typeAndsnList;
				if("批量模式"==$("#quantitativeModel").val()){
					skuNum=$("#skuNum").val();
					mogodbSum=skuNum;
					$("#totalSnNum2").text(parseInt(1)+parseInt(totalSnNum2));
					totalSnNum2=parseInt(1)+parseInt(totalSnNum2);
					var damageNum1=$("#damageNum").val();
					if(null!=damageNum&&undefined!=damageNum&&''!=damageNum){
						if(parseInt(totalSnNum2)<parseInt(damageNum1)){
							toSN();
							$("#sn").val("");
							return;
						}else{
							if(parseInt(damageNum)<parseInt(skuNum)){
								toDamage();
								$("#damageNum").val("");
								return;
							}
							var postData1 = {
									"staCode":staCode,
									"barCode":key,
									"qty":mogodbSum
							};
							var flags=request($("body").attr("contextpath") + "/pda/updateMogodbRestQty.do",postData1);
							if(flags["msg"]!="error"){
								if("是"==isPacking){
									toBox();
									return;
								}else{
									asnOver();
									return;
								}
							}else{
								//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
								play();
								$("#msg").text("输入的数量大于计划量!");
								return;
							}
							
						}
					}else{
						if(parseInt(totalSnNum2)<parseInt(skuNum)){
							toSN();
							$("#sn").val("");
							return;
						}else{
							
							var postData1 = {
									"staCode":staCode,
									"barCode":key,
									"qty":mogodbSum
							};
							var flags=request($("body").attr("contextpath") + "/pda/updateMogodbRestQty.do",postData1);
							if(flags["msg"]!="error"){
								if("是"==isPacking){
									toBox();
									return;
								}else{
									asnOver();
									return;
								}
							}else{
								//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
								play();
								$("#msg").text("输入的数量大于计划量!");
								return;
							}
						}
						
					}
				}else{
					mogodbSum=1;
					var postData1 = {
							"staCode":staCode,
							"barCode":key,
							"qty":mogodbSum
					};
					var flags=request($("body").attr("contextpath") + "/pda/updateMogodbRestQty.do",postData1);
					if(flags["msg"]!="error"){
					   if("是"==isPacking){
						   toBox();
						   return;
					   }else{
						   asnOver();
						   return;
					   }
				   }else{
					   //playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					   play();
					   $("#msg").text("输入的数量大于计划量!");
					   return;
				   }
				}
			});
			
			//返回
			$("#receiveBack").click(function(){
				window.location.href=$("body").attr("contextpath")+"/pda/menu.do";
			});
			
			function firstDiv(){
				$("#msg").text("");
				$("#receive1").show();
				$("#receive2").hide();
				$("#receive3").hide();
				$("#receive4").hide();
				$("#receive5").hide();
				$("#receive6").hide();
				$("#staCode").val("");
				$("#staCode").focus();
			}
			
			//到扫描容器
			function toBox(){
				$("#msg").text("");
				$("#receive1").hide();
				$("#receive2").show();
				$("#receive3").hide();
				$("#receive4").hide();
				$("#receive5").hide();
				$("#receive6").hide();
				isPacking=$("#isPacking").val();
				$("#cartonBox").focus();
				if("是"==isPacking){
					if(null==container||undefined==container||''==container){
						$("#cartonBox").val("");
					}else{
						$("#cartonBox").val(container);
					}
				}
			}
			
			function toSN(){
				$("#msg").text("");
				var damageNum1=$("#damageNum").val();
				$("#receive1").hide();
				$("#receive2").hide();
				$("#receive3").hide();
				$("#receive4").hide();
				$("#receive5").hide();
				$("#receive6").show();
				$("#sn").focus();
				if("批量模式"==$("#quantitativeModel").val()){
					$("#totalSn").show();
					if(damageNum1!=null&&damageNum1!=undefined&&damageNum1!=''){
						$("#totalSnNum1").text(damageNum1);
					}else{
						$("#totalSnNum1").text(skuNum);
					}
					if(totalSnNum2==null||totalSnNum2==undefined||totalSnNum2==''){
						$("#totalSnNum2").text(0);
						totalSnNum2=0;
					}else{
						$("#totalSnNum2").text(totalSnNum2);
					}
				}
			}
			
			function toSku(){
				$("#msg").text("");
				quantitativeModel=$("#quantitativeModel").val();
				if("批量模式"==quantitativeModel){
					$("#skuNumDiv").show();
					$("#skuNumCount").hide();
				}else{
					//var staCode = $("#staCode").val();
					var key = $("#sku").val();
					var postData = {
							"staCode":staCode,
							"barCode":key
					};
					if(""!=key){
						var flag=request($("body").attr("contextpath") + "/pda/mongodbfindQtyByCode.do",postData);
						if(flag["flag"]!="error"){
							$("#skuNumCount").text(flag["flag"]);
						}else{
							$("#skuNumCount").text(0);
						}
					}
				}
				if("残次品"==inventoryStatus){
					$("#totalNum2").val("");
					damageNum=0;
				}
				$("#receive1").hide();
				$("#receive2").hide();
				$("#receive4").hide();
				$("#receive5").hide();
				$("#receive6").hide();
				$("#receive3").show();
				$("#sku").focus();
				$("#sku").val("");
			};
			
			function toTime(){
				$("#msg").text("");
				$("#receive1").hide();
				$("#receive2").hide();
				$("#receive3").hide();
				$("#receive4").show();
				$("#receive5").hide();
				$("#receive6").hide();
			};
			
			function toSku2(){
				$("#msg").text("");
				$("#receive1").hide();
				$("#receive2").hide();
				$("#receive3").show();
				$("#receive4").hide();
				$("#receive5").hide();
				$("#receive6").hide();
				$("#sn").focus();
			};
			
			function toDamage(){
				$("#msg").text("");
				snList=null;
				typeAndsnData=null;
				inventoryStatus=$("#inventoryStatus").val();
				quantitativeModel=$("#quantitativeModel").val();
				$("#receive1").hide();
				$("#receive2").hide();
				$("#receive3").hide();
				$("#receive4").hide();
				if("残次品"==inventoryStatus){
					if("逐件模式"==quantitativeModel){
					   $("#receive5").show();
					   $("#expirationDate").focus();
					}else if("批量模式"==quantitativeModel){
						$("#unReason").val("");
						$("#damageNum").val("");
						$("#unType").val("");
						$("#receive5").show();
						$("#numDiv").show();
						$("#damageNumDiv").show();
						$("#damageNum").focus();
						if(''==damageNum||null==damageNum||undefined==damageNum){
							totalNum2=$("#totalNum2").val();
							if(totalNum2==''||totalNum2==undefined||totalNum2==null){
								   totalNum2=0;
								   $("#totalNum2").text(0);
							}
							damageNum=0;
						}else{
							$("#totalNum2").text(parseInt(damageNum)+parseInt(totalNum2));
							totalNum2=parseInt(damageNum)+parseInt(totalNum2);
						}
						$("#totalNum1").text(skuNum);
					}
				}else{
					$("#receive5").hide();
					$("#receive3").show();
				}
				$("#receive6").hide();
			};
			
			function toBox2(){
				$("#msg").text("");
				$("#receive1").hide();
				$("#receive2").show();
				$("#receive3").hide();
				$("#receive4").hide();
				$("#receive5").hide();
				$("#receive6").hide();
				$("#cartonBox").val("");
				$("#cartonBox").focus();
				
			};
			
			function clear(){
				$("#msg").text("");
			    qty=null;
			    snList=null;
			    typeAndsnList=null;
			    typeAndsnData=null;
			}
			
			function asnOver(){
				var datas={};
				var postData={"staCode":staCode};
				datas["dataList"]=JSON.stringify(dataList);
				datas["quantitativeModel"]=quantitativeModel;
				datas["cartonBox"]=cartonBox;
				datas["inventoryStatus"]=inventoryStatus;
				datas["staCode"]=staCode;
				var flag=request($("body").attr("contextpath") + "/pda/asnOver.do",datas);
				snList=null;
				container=null;
				totalNum1=null;
				dataList=null;
				totalSnNum2=null;
				//totalSnNum1=null;
				if(flag["flag"]!="error"){
					var restQty=request($("body").attr("contextpath") + "/pda/findMongodbByRestQty.do",postData); //查询作业单下的所有商品计划量 如果都为0，默认收货完毕，跳转作业单扫描页面
					if("批量模式"==quantitativeModel){
						if(parseInt(damageNum)<parseInt(skuNum)&&restQty["flag"]=="success"){
							toDamage();
							return;
						}
					}
					data=null;
					skuEntity=null;
					typeAndsnList=null; 
					typeAndsnData=null; 
					$("#damageNum").val("");
					$("#unReason").val("");
					$("#unType").val("");
					$("#skuNum").val("");
					damageNum=null;
					skuNum=null;
					totalNum2=null;
					key=null;
					container=null;
					if(restQty["flag"]=="success"){  //有未收货的商品
						toSku();
						$("#sku").val("");
						return;
					}else{
						firstDiv();
						$("#staCode").val("");
						staCode=null;
						return;
					}	
				}else{
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("系统异常!");
					return;
				}
			};
			
			$("#unType").change(function(){
				$("#unReason").empty();
				var imperfect=$("#unType").val();
				if(imperfect!="其他"){
					var postData={"imperfectId":imperfect};
					var resultQty=request($("body").attr("contextpath") + "/pda/getByImperfectLine.do",postData);
					if(resultQty["result"]=="success"){
						var result=resultQty["object"];
						$("<option value=''>请选择</option>").appendTo($("#unReason"));
						for(var idx in result){
							$("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($("#unReason"));
						}
					}else{
						$("#unReason").empty();
						$("<option value=''>请选择</option>").appendTo($("#unReason"));
						$("<option value='其他'>其他</option>").appendTo($("#unReason"));
					}
				}else{
					$("#unReason").empty();
					$("<option value=''>请选择</option>").appendTo($("#unReason"));
					$("<option value='其他'>其他</option>").appendTo($("#unReason"));
				}
			});
			
			$("#cartonBoxBack").click(function(){
				firstDiv();
			});
			$("#skuBack").click(function(){
				if("是"==isPacking){
					firstDiv();
					return;
				}else{
					toBox();
					return;
				}
			});
			
			$("#timeBack").click(function(){
				toSku();
				dataList.pop();
				return;
			});
			
			$("#damageBack").click(function(){
				if("33"==skuEntity["flag"]["storemode"].value){
					toTime();
					dataList[dataList.length-1]["productionDate"]="";
					dataList[dataList.length-1]["expirationDate"]="";
					return;
				}else{
					toSku();
					dataList.pop();
					return;
				}
			});
			
			$("#snBack").click(function(){
				if("残次品"==inventoryStatus){
					toDamage();
					/*dataList[dataList.length-1]["typeAndsnList"][dataList.length-1]["unReasonList"]="";
					dataList[dataList.length-1]["typeAndsnList"][dataList.length-1]["unType"]="";
					dataList[dataList.length-1]["typeAndsnList"][dataList.length-1]["damageNum"]="";*/
					dataList[dataList.length-1]["typeAndsnList"][dataList.length-1].pop();
					return;
				}else if("33"==skuEntity["flag"]["storemode"].value){
					toTime();
					dataList[dataList.length-1]["productionDate"]="";
					dataList[dataList.length-1]["expirationDate"]="";
					return;
				}else{
					toSku();
					dataList.pop();
					return;
				}
			});
			
			//退出登录
			$("#exit").click(function(){
				window.location.href=$("body").attr("contextpath")+"/pda/pdaExit.do";
			})
			
			
			$("#boxOver").click(function(){
				var datas={};
				//staCode=$("#staCode").val();
				var postData={"staCode":staCode};
				var num=$("#skuNum").val();
				if(""!=num){
					key = $("#sku").val();
					if(key==""){
						//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
						play();
						$("#msg").text("请输入sku!");
						return;
					}
					quantitativeModel=$("#quantitativeModel").val();
					if("批量模式"==quantitativeModel){
						var num=$("#skuNum").val();
						if(num==""){
							//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
							play();
							$("#msg").text("请输入数量!");
							return;
						}
					}
					clear();
					skuNum=$("#skuNum").val();
					if(null==dataList||undefined==dataList||''==dataList){
						dataList=new Array();
					}
					//staCode = $("#staCode").val();
					
					if("批量模式"==quantitativeModel){
						mogodbSum=$("#skuNum").val();
					}else{
						mogodbSum=1;
					}
					
					var postData = {
							"staCode":staCode,
							"barCode":key,
							"qty":mogodbSum
				    };
					var flag=request($("body").attr("contextpath") + "/pda/verifySku.do",postData);
					if(flag["flag"]=="success"){
						data = {};
						var postData1 = {
								"staCode":staCode,
								"barCode":key
								
					    };
						skuEntity=request($("body").attr("contextpath") + "/pda/findPDASkuByBarCode.do",postData1);
											data["skuId"]=skuEntity["flag"]["id"];
											dataList[dataList.length]=data;
											data["skubarcode"]=key;
											var mogodbSum;
											if("逐件模式"==quantitativeModel){
												data["skuNum"]=1;
												mogodbSum=1;
											}else{
												data["skuNum"]=$("#skuNum").val();
											}
											
											var postData1 = {
															"staCode":staCode,
															"barCode":key,
															"qty":mogodbSum
													};
													var flags=request($("body").attr("contextpath") + "/pda/updateMogodbRestQty.do",postData1);
													if(flags["msg"]!="error"){
														
													}else{
														play();
														$("#msg").text("系统异常");
														return;
													}
						}
				}
				datas["dataList"]=JSON.stringify(dataList);
				datas["quantitativeModel"]=quantitativeModel;
				datas["cartonBox"]=cartonBox;
				datas["inventoryStatus"]=inventoryStatus;
				datas["staCode"]=staCode;
				//$("#staCode").val();
				var flag=request($("body").attr("contextpath") + "/pda/cartonASNOk.do",datas);
				if(flag["flag"]!="error"){
					$("#cartonBox").val("");
					snList=null;
					if(""!=container){
						container=null;
					}
					totalNum1=null;
					dataList=null;
                    totalSnNum2=null;
					/*totalSnNum1=null;*/
					data=null;
					if(""!=skuEntity){
						skuEntity=null;
					}
					if(""!=typeAndsnList){
						typeAndsnList=null; 
					}
					typeAndsnData=null; 
					$("#damageNum").val("");
					$("#unReason").val("");
					$("#unType").val("");
					$("#skuNum").val("");
					damageNum=null;
					skuNum=null;
					totalNum2=null;
					key=null;
					toBox();
					$("#staCode").val("");
					$("#cartonBox").val("");
					return;
				}else{
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("系统异常!");
					return;
				}
			});
			
			$("#cartonASNOk").click(function(){
				var datas={};
				//staCode=$("#staCode").val();
				var postData={"staCode":staCode};
				datas["dataList"]=JSON.stringify(dataList);
				datas["quantitativeModel"]=quantitativeModel;
				datas["cartonBox"]=cartonBox;
				datas["inventoryStatus"]=inventoryStatus;
				datas["staCode"]=staCode;
				//$("#staCode").val();
				var flag=request($("body").attr("contextpath") + "/pda/cartonASNOk.do",datas);
				if(flag["flag"]!="error"){
					snList=null;        
					totalNum1=null;
					dataList=null;
					totalSnNum2=null;
					//totalSnNum1=null;
					data=null;
					skuEntity=null;
					typeAndsnList=null; 
					typeAndsnData=null; 
					$("#damageNum").val("");
					$("#unReason").val("");
					$("#unType").val("");
					$("#skuNum").val("");
					damageNum=null;
					skuNum=null;
					totalNum2=null;
					key=null;
					firstDiv();
					$("#staCode").val("");
					staCode=null;
					return;
				}else{
					//playMusic($("body").attr("contextpath")+"/pda/images/1.wav");
					play();
					$("#msg").text("系统异常!");
					return;
				}
			});
	    });
		
		
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
		