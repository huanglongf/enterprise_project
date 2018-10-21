$j.extend(loxia.regional['zh-CN'],{
//	CODE : "配货批次号"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function showDetail(obj){
	var id=$j(obj).parent().parent().attr("id");
	var data = $j("#tbl-bichannel-list").jqGrid("getRowData",id);
	$j("#bichannel-list").addClass("hidden");
	$j("#channel_wh_refList").removeClass("hidden");
	$j("#cid").val(id);
	$j("#cCode").text(data["code"]);
	$j("#cName").text(data["name"]);
	$j("#tbl-bichannelwh-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/findBiChannelWhRefList.json"),
			postData:{"customerId":data['customerId']}}).trigger("reloadGrid",[{page:1}]);
}

	$j(document).ready(function (){
		$j("#tabs").tabs();
		$j("#detail").hide();
		$j("#tabs").hide();
		var baseUrl = $j("body").attr("contextpath");
		var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findBiCustomer.do");
		for(var idx in result){
			$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#customerId"));
		}
	
		$j("#tbl-bichannel-list").jqGrid({
			url:baseUrl + "/findBiChannelListByTypeAndExpT.json",
			datatype: "json",
			colNames: ["ID","CID","渠道编码","渠道名称","客户名称","发货地址"],
			colModel: [
								{name:"id", hidden: true},
								{name:"customerId",index:"customerId",hidden: true},
								{name:"code",index:"code", width:280,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, resizable:true},
								{name:"name",index:"name",width:280,resizable:true},
								{name:"cName",index:"cName",width:90,resizable:true},
								{name:"address",index:"address",width:300,resizable:true}
					 		],
			caption: "渠道信息列表",
		   	sortname: 'id',
		   	multiselect: false,
			sortorder: "desc",
			pager:"#pager",
			width:1000,
		   	shrinkToFit:false,
			height:"auto",
			rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
			viewrecords: true,
		   	rownumbers:true,
			jsonReader: {repeatitems : false, id: "0"}
		});

		$j("#tbl-bichannelwh-list").jqGrid({
			datatype: "json",
			colNames: ["ID","仓库编号","仓库名称","渠道仓库绑定","是否维护QS","交接清单(含包裹明细)","是否收货默认仓库","后置打印面单","后置打印装箱清单","是否平安投保","SF月结账号","收货上架标签前缀"],
			colModel: [
								{name:"id",index:"id",hidden: true},
								{name:"code",index:"code", width:100,resizable:true},
								{name:"name",index:"name",width:150,resizable:true},
								{name:"isWhRef",width:130,formatter:function(v,x,r){return "<input type='checkbox' name = 'isWhRefCheckbox' onclick='checkBoxCheck()'/>";}},
								{name:"isPostQs",width:130,formatter:function(v,x,r){return "<input type='checkbox' name = 'isPostQs' onclick='validateQs()'/>";}},
								{name:"isPrintPackageDetail",width:130,formatter:function(v,x,r){return "<input type='checkbox' name = 'isPrintPackageDetail' onclick='validateSetPrintPackageDetail()'/>";}},
								{name:"isWh",width:130,formatter:function(v,x,r){return "<input type='radio' name = 'isRadio' onclick='radioCheck()'/>";}},
								{name:"isPostExpressBill", width: 130, resizable: true,formatter:function(v,x,r){return "<input type='checkbox' name = 'isPostExpressBill' onclick='validatePostExpressBill()'/>";}},
						        {name:"isPostPackingPage", width: 130, resizable: true,formatter:function(v,x,r){return "<input type='checkbox' name = 'isPostPackingPage' onclick='validatePostPackingPage(this)'/>";}},
						        {name:"isPinganCover", width: 130, resizable: true,formatter:function(v,x,r){return "<input type='checkbox' name = 'isPinganCover'";}},
								{name:"name",index:"name",width:100,resizable:true,formatter:function(v,x,r){return "<input type='text' name = 'sfJcustid' onkeyup='validatePostSfJcustid()' /> ";}},
								{name:"receivePrefix1",index:"receivePrefix1",width:100,resizable:true,formatter:function(v,x,r){return "<input type='text' name = 'receivePrefix' /> ";}}
								],
			caption: "仓库信息列表",
		   	sortname: 'id',
			sortorder: "asc",
			width:1450,
		   	shrinkToFit:false,
			height:"auto",
//			multiselect: true,
			rowNum:-1,
			viewrecords: true,
		   	rownumbers:true,
			jsonReader: {repeatitems : false, id: "0"},
			gridComplete:function(){
			//给CHECKBOX勾选
			var cid = $j("#cid").val();
			var whRef=loxia.syncXhrPost($j("body").attr("contextpath") + "/findChannelWhRefListByChannelId.json",{"channelId":cid});
			if(whRef["msg"] !=""){
				var whRefValues= new Array();
				whRefValues = whRef["msg"].toString().substring(0,whRef["msg"].toString().length-1).split(",");
				var ids = $j("#tbl-bichannelwh-list").jqGrid('getDataIDs');
				for(var i=0;i < ids.length;i++){
					var datas = $j("#tbl-bichannelwh-list").jqGrid('getRowData',ids[i]);
					var tra = "";
					for(var j=0;j < whRefValues.length;j++){
						var whR = whRefValues[j].toString().split(" ");
						if(datas["id"] == whR[0]){
							 $j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isWhRefCheckbox]").attr("checked","checked");//选中仓库绑定
							if(whR[1] == 1){
								 $j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isRadio]").attr("checked","checked");//选中是否收货默认仓库
							}
							var isPostPackingPages = whR[2];
							var isPostExpressBills = whR[3];
							var sfJcustid = whR[4];//月结账号
							var receivePrefix = whR[5];
							var isPostQs = whR[6];
							var isPrintPackageDetail = whR[7];
							var isPinganCover=whR[8];
							if(isPostQs == "true"){
								$j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPostQs]").attr("checked","checked");
							}else{
								$j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPostQs]").attr("checked",false);
							}
							
							if(isPrintPackageDetail == "true"){
								$j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPrintPackageDetail]").attr("checked","checked");
							}else{
								$j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPrintPackageDetail]").attr("checked",false);
							}

							if(1 == isPostPackingPages){
								$j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPostPackingPage]").attr("checked","checked");
							}
							if("true" == isPinganCover){
								$j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPinganCover]").attr("checked","checked");
							}else{
								$j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPinganCover]").attr("checked",false);
							}
							if(1 == isPostExpressBills){
								$j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPostExpressBill]").attr("checked","checked");
							}
							if('null' == sfJcustid){}else{
								$j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=sfJcustid]").val(sfJcustid);
							}
							if('null' == receivePrefix){}else{
								$j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=receivePrefix]").val(receivePrefix);
							}
							
						}
						
					}
				}
			}
		}
	});
		
		//查询
		$j("#search").click(function(){
				var postData = loxia._ajaxFormToObj("channelForm");
				$j("#tbl-bichannel-list").jqGrid('setGridParam',{
					url:window.$j("body").attr("contextpath")+"/findBiChannelListByTypeAndExpT.json",
					postData:postData,
					page:1
				}).trigger("reloadGrid");
		});
		
		$j("#back").click(function(){
			$j("#bichannel-list").removeClass("hidden");
			$j("#channel_wh_refList").addClass("hidden");
		});
		
		//保存
		$j("#save").click(function(){
			if(!validatePostExpressBill()){
				jumbo.showMsg("该渠道不允许维护后置打印面单!");
				return;
			}
			if(!validatePostPackingPage("")){
				jumbo.showMsg("该渠道不允许维护后置打印装箱清单!");
				return;
			}
			
			if(!validateQs()){
				jumbo.showMsg("该渠道不允许QS!");
				return;
			}
			
			if(!validateSetPrintPackageDetail()){
				jumbo.showMsg("该渠道不允许维护打印交接清单包裹明细!");
				return;
			}
			var cid = $j("#cid").val();
			var ids = $j("#tbl-bichannelwh-list").jqGrid('getDataIDs');
			var postData = {};
			var index=-1;
			var checkboxNumber = 0;
			var radioNumber = 0;
			for(var i=0;i < ids.length;i++){
				var datas = $j("#tbl-bichannelwh-list").jqGrid('getRowData',ids[i]);
				if($j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isWhRefCheckbox]").attr("checked") == true){
					index+=1;
					postData["channelWhRefList[" + index + "].wh.id"]=datas["id"];
					postData["channelWhRefList[" + index + "].shop.id"]=cid;

					checkboxNumber++;
					if($j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isRadio]").attr("checked") == true){
						postData["channelWhRefList[" + index + "].isDefaultInboundWh"]=true;
						radioNumber++;
					}
					if($j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isRadio]").attr("checked") == false){
						postData["channelWhRefList[" + index + "].isDefaultInboundWh"]=false;
					}
					if($j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPostExpressBill]").attr("checked") == true){
						postData["channelWhRefList[" + index + "].isPostExpressBill"]=true;
					}else{		
						postData["channelWhRefList[" + index + "].isPostExpressBill"]=false;
					}
					//qs
				//	alert($j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPostQs]").attr("checked"));
					if($j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPostQs]").attr("checked") == true){
						postData["channelWhRefList[" + index + "].isPostQs"]=true;

					}else{		
						postData["channelWhRefList[" + index + "].isPostQs"]=false;

					}
					
					if($j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPrintPackageDetail]").attr("checked") == true){
						postData["channelWhRefList[" + index + "].isPrintPackageDetail"]=true;
						
					}else{		
						postData["channelWhRefList[" + index + "].isPrintPackageDetail"]=false;
						
					}
					
					if($j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPostPackingPage]").attr("checked") == true){
						postData["channelWhRefList[" + index + "].isPostPackingPage"]=true;
					}else{
						postData["channelWhRefList[" + index + "].isPostPackingPage"]=false;
					}
					
					if($j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPinganCover]").attr("checked") == true){
						postData["channelWhRefList[" + index + "].isPinganCover"]=true;
					}else{
						postData["channelWhRefList[" + index + "].isPinganCover"]=false;
					}
				  // var  $j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=sfJcustid]").val();
					postData["channelWhRefList[" + index + "].sfJcustid"]=$j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=sfJcustid]").val();//月结账号
					postData["channelWhRefList[" + index + "].receivePrefix"]=$j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=receivePrefix]").val();

				}
				
				
			}
			if(checkboxNumber == 0){
					jumbo.showMsg("必须选择一个仓库进行绑定！");
	 				return;
			}
			if(radioNumber == 0){
					jumbo.showMsg("必须选择一个仓库为收货默认仓！");
	 				return;
			}
			if(confirm("确定保存已修改的内容？")){
				var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/updateChannelWhRef.json?channelId="+cid,postData);
				if(rs && rs["msg"] == "SUCCESS"){
					jumbo.showMsg("渠道仓库关联保存成功！");
					$j("#bichannel-list").removeClass("hidden");
					$j("#channel_wh_refList").addClass("hidden");
					$j("#tbl-bichannel-list").jqGrid('setGridParam',{
					url:window.$j("body").attr("contextpath")+"/findBiChannelListByTypeAndExpT.json",
					page:1
				}).trigger("reloadGrid");
				}else if(rs && rs["msg"] == "ERROR"){
					jumbo.showMsg(rs["str"]+"月结账号不存在!");		
				}else{
					jumbo.showMsg("渠道仓库关联保存失败！");		
				}
			}
		});
		
	});
	
	//选中是否收货默认仓库触发事件
	function radioCheck(){
		//如果选中，那该条渠道仓库绑定也一并选中
		var ids = $j("#tbl-bichannelwh-list").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var datas = $j("#tbl-bichannelwh-list").jqGrid('getRowData',ids[i]);
				var radioValue = $j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isRadio]").attr("checked");
				if(radioValue==true){
					$j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isWhRefCheckbox]").attr("checked","checked");
//					$j("#"+datas["id"]+" input[name=isWhRefCheckbox]").attr("checked","checked");
			}
		}
	}
	
	//选中渠道仓库绑定触发事件
	function checkBoxCheck(){
		//如果取消选中，那该条是否收货默认仓库一并取消
		var ids = $j("#tbl-bichannelwh-list").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var datas = $j("#tbl-bichannelwh-list").jqGrid('getRowData',ids[i]);
				var checkBoxValue = $j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isWhRefCheckbox]").attr("checked")
				if(checkBoxValue==false){
					var radioValue = $j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isRadio]").attr("checked");
					var isQsValue = $j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPostQs]").attr("checked");
					var isPrintPackageDetailValue = $j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPrintPackageDetail]").attr("checked");
					if(radioValue){
						$j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isRadio]").attr("checked",false);
					}
					if(isQsValue){
						$j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPostQs]").attr("checked",false);
					}
					if(isPrintPackageDetailValue){
						$j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPrintPackageDetail]").attr("checked",false);
					}
				}
			}
		}
	////选中QS触发事件
	function checkQsCheck(){
		//如果取消选中，那该条是否收货默认仓库一并取消
		var ids = $j("#tbl-bichannelwh-list").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var datas = $j("#tbl-bichannelwh-list").jqGrid('getRowData',ids[i]);
				//var checkBoxValue = $j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isQs]").attr("checked");
//				if(checkBoxValue==false){
//					var radioValue = $j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isRadio]").attr("checked");
//					if(radioValue){
//						$j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isRadio]").attr("checked",false);
//					}
//				}
			}
		}
	
	function validatePostPackingPage(obj){
		var ids = $j("#tbl-bichannelwh-list").jqGrid('getDataIDs');
		for(var i=0;i < ids.length;i++){
			var datas = $j("#tbl-bichannelwh-list").jqGrid('getRowData',ids[i]);
			var checkBoxValue = $j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPostPackingPage]").attr("checked");
			if(checkBoxValue==true){
				var isWhRefCheckbox = $j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isWhRefCheckbox]").attr("checked");
				if(isWhRefCheckbox==false){
					jumbo.showMsg("该渠道不允许维护后置打印装箱清单!");
					$j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPostPackingPage]").attr("checked",false);
					return false;
				}
			}
		}
		var channelId = $j(obj).parent().parent().attr("id");
		var specialAction = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getBiChannelSpecialActionByCidTypePackingPage.json?channelId="+channelId);
		if(specialAction && specialAction.msg != "none"){
			var templateType = specialAction.templateType;
			if(null != templateType && "" != templateType){
				$j("#"+ channelId +" input[name='isPostPackingPage']").attr("checked", "");
				jumbo.showMsg("该渠道不允许维护后置打印装箱清单！");
				return false;
			}
		}
		return true;
	}
	
	function validatePostExpressBill(){
		var ids = $j("#tbl-bichannelwh-list").jqGrid('getDataIDs');
		for(var i=0;i < ids.length;i++){
			var datas = $j("#tbl-bichannelwh-list").jqGrid('getRowData',ids[i]);
			var checkBoxValue = $j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPostExpressBill]").attr("checked");
			if(checkBoxValue==true){
				var isWhRefCheckbox = $j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isWhRefCheckbox]").attr("checked");
				if(isWhRefCheckbox==false){
					jumbo.showMsg("该渠道不允许维护后置打印面单!");
					$j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPostExpressBill]").attr("checked",false);
					return false;
				}
			}
		}
		return true;
	}
	
	
	function validateQs(){
		var ids = $j("#tbl-bichannelwh-list").jqGrid('getDataIDs');
		for(var i=0;i < ids.length;i++){
			var datas = $j("#tbl-bichannelwh-list").jqGrid('getRowData',ids[i]);
			var checkBoxValue = $j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPostQs]").attr("checked");
			if(checkBoxValue==true){
				var isWhRefCheckbox = $j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isWhRefCheckbox]").attr("checked");
				if(isWhRefCheckbox==false){
					jumbo.showMsg("该渠道不允许维护QS!");
					$j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPostQs]").attr("checked",false);
					return false;
				}
			}
		}
		return true;
	}
	
	function validateSetPrintPackageDetail(){
		var ids = $j("#tbl-bichannelwh-list").jqGrid('getDataIDs');
		for(var i=0;i < ids.length;i++){
			var datas = $j("#tbl-bichannelwh-list").jqGrid('getRowData',ids[i]);
			var checkBoxValue = $j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPrintPackageDetail]").attr("checked");
			if(checkBoxValue==true){
				var isWhRefCheckbox = $j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isWhRefCheckbox]").attr("checked");
				if(isWhRefCheckbox==false){
					jumbo.showMsg("该渠道不允许维护打印交接清单包裹明细!");
					$j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isPrintPackageDetail]").attr("checked",false);
					return false;
				}
			}
		}
		return true;
	}
	
	function validatePostSfJcustid(){
	//	alert(23232);
		var ids = $j("#tbl-bichannelwh-list").jqGrid('getDataIDs');
		for(var i=0;i < ids.length;i++){
			var datas = $j("#tbl-bichannelwh-list").jqGrid('getRowData',ids[i]);
			var sf = $j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=sfJcustid]").val();
			
			if(sf==''){}else{
				var isWhRefCheckbox = $j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=isWhRefCheckbox]").attr("checked");
				if(isWhRefCheckbox==false){
					$j("#tbl-bichannelwh-list tr[id="+datas["id"]+"] input[name=sfJcustid]").val('');
					jumbo.showMsg("该渠道不允许维护月结账号!");
					return false;
				}
			}
		}
		return true;
	}
