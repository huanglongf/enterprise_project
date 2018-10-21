function showMsg(msg){
	jumbo.showMsg(msg);
}
//
function showdetail(obj){
	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");
	$j("#sta_id").val(id);
	jQuery("#tbl_add_list").jqGrid("clearGridData");  //清空 本次收货明细列表数据
	$j("#addBarCode").val("");
	$j("#startNum").val("");
	$j("#stopNum").val("");
	var pl=$j("#tbl_sta_list").jqGrid("getRowData",id);
	$j("#sta_code").text(pl["code"]);
	$j("#sta_refSlipCode").text(pl["refSlipCode"]);
	$j("#sta_slipCode1").text(pl["slipCode1"]);
	$j("#sta_createTime").text(pl["createTime"]);
	$j("#sta_type").text(tr.find("td[aria-describedby$='intStaType']").text());
	$j("#sta_status").text(tr.find("td[aria-describedby$='intStaStatus']").text());
	$j("#sta_memo").text(pl["memo"]);
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl_sta_line_list").jqGrid('setGridParam',{page:1,url:baseUrl + "/findHandStaLineStarbucksBySta.json?sta.id=" +id}).trigger("reloadGrid");
	$j("#divHead").addClass("hidden");
	$j("#divDetial").removeClass("hidden");
	$j("#skuBarCode").focus();
}

//删除按钮的方法
function deleteLine(tag,event){
	var id = $j(tag).parents("tr").attr("id");
	var rowdata = $j("#tbl_add_list").jqGrid("getRowData",id);
	var skuCode=rowdata["skuCode"];
	var quantity=rowdata["completeQuantity"];  //获取执行量
	var dd;
	var data = $j("#tbl_sta_line_list").jqGrid('getRowData');
	$j.each(data,function(i, pl){
		if(pl["skuCode"] ==skuCode){
			dd=$j("#"+pl["skuId"]).find("input[name=addQuantity]").val();  //获取入库明细单的  执行量
			$j("#"+pl["skuId"]).find("input[name=addQuantity]").val(parseInt(dd)-quantity); 
		}
	});
	$j("#tbl_add_list tr[id='" + id + "']").remove(); //删除行
	
//	removeSnTblSku(id);
	} 
//初始化
$j(document).ready(function (){
	var baseUrl = $j("body").attr("contextpath");
	initShopQuery("companyshop","innerShopCode");
	jumbo.loadShopList("companyshop");
	loxia.init({debug: true, region: 'zh-CN'});
	staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	$j("#tbl_sta_list").jqGrid({
		url:baseUrl+"/findInboundSta.json",
		datatype: "json",
		colNames: ["ID","作业单号","相关单据号","辅助相关单据号","状态","作业类型","店铺","CHANNELNAME","创建时间","备注"],
		colModel: [
		           {name: "id", index: "id", hidden: true},	
		           {name: "code", index: "code",formatter:"linkFmatter", formatoptions:{onclick:"showdetail"}, width: 110, resizable: true,sortable:false},
		           {name: "refSlipCode", index: "refSlipCode", width: 100, resizable: true},
		           {name: "slipCode1", index: "slipCode1", width: 100, resizable: true},
		           {name: "intStaStatus", index: "intStaStatus", width: 80, resizable: true,formatter:'select',editoptions:staStatus},
	               {name: "intStaType", index: "intStaType", width: 120, resizable: true,formatter:'select',editoptions:staType},
	               {name: "channelName", index: "channelName", width: 120, resizable: true},
	               {name: "owner", index: "owner", width: 120, resizable: true,hidden:true},
	               {name: "createTime", index: "createTime", width: 150, resizable: true},
	               {name: "memo", index: "memo", width: 150, resizable: true},
	               ],
		caption: "入库作业单",
	   	sortname: 'sta.id',
		pager:"#pager",
	    multiselect: false,
		sortorder: "desc",
		height:"auto",
		rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
	
	});
	
	//本次收货明细列表
	$j("#tbl_add_list").jqGrid({
		datatype: "json",
		colNames: ["ID","商品ID","SKU编码","条形码","商品名称","起始卡号","终止卡号","执行量","扩展属性","店铺","操作"],
		colModel: [
					{name: "id", index: "id",hidden:true,sortable:false},//序号（ID）
					 {name: "skuId", index: "skuId" ,hidden: true,sortable:false},
					{name: "skuCode", index: "skuCode", width: 150, resizable: true,sortable:false},//商品编码
					{name: "barCode", index: "barCode", width: 150, resizable: true,sortable:false},//条形码
					{name: "skuName", index: "skuName", width: 150, resizable: true,sortable:false},//商品名称
					{name: "startNum", index: "startNum", width:120, resizable:true, sortable:false},//起始卡号
					{name: "stopNum", index: "stopNum", width:120, resizable:true, sortable:false},//终止卡号
					{name: "completeQuantity", index: "completeQuantity", width:120, resizable:true, sortable:false},//执行量
					{name: "keyProperties", index: "keyProperties", width:120, resizable:true, sortable:false},//扩展属性
					{name: "channelName", index: "channelName", width:120, resizable:true, sortable:false},//店铺名
					{name: "reset", width: 100,resizable:true, formatter:"buttonFmatter", formatoptions:{"buttons":{label:"删除", onclick:"deleteLine(this,event);"}}}//操作
	               ],
		caption: "本次收货明细列表",
		rowNum:10,
	    multiselect: false,
	    gridComplete : function(){
			loxia.initContext($j(this));
		},
		width:950,
		height:"auto",
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	//商品明细
	$j("#tbl_sta_line_list").jqGrid({
		datatype: "json",
		colNames: ["ID","skuId","SKU编码","条形码","商品名称","计划量","执行量","扩展属性","店铺","snCheckMode"],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},	
		           {name: "skuId", index: "skuId" ,hidden: true,sortable:false},
		           {name: "skuCode", index: "skuCode", width: 100, resizable: true,sortable:false},//商品编码
		           {name: "barCode", index: "barCode", width: 120, resizable: true,sortable:false},//条形码
		           {name: "skuName", index: "skuName", width: 190, resizable: true,sortable:false},//
		           {name: "quantity",index: "quantity",resizable:true},//计划执行量
		           {name: "addQuantity",index: "addQuantity",formatter:"loxiaInputFmatter", formatoptions:{loxiaType:"number",min:"0"}, width: 160, resizable: true,sortable:false},//本次执行量
		           {name: "keyProperties", index:"keyProperties",width: 120,resizable:true},//扩展属性
		           {name: "channelName", index:"channelName",width: 120,resizable:true},//店铺名
		           {name: "snCheckMode", index:"snCheckMode",hidden: true,sortable:false},//店铺名
	               ],
       caption: "入库明细单",
       sortname: 'bar_Code',
       multiselect: false,
       sortorder: "desc",
       height:"auto",
       viewrecords: true,
       rowNum:-1,
       rownumbers:true,
       gridComplete : function(){
    	   loxia.initContext($j(this));
       },
       jsonReader: { repeatitems : false, id: "0" }
	});
	
	
	//sku编码
	$j("#addBarCode").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			var addBarCode = $j("#addBarCode").val().trim();
			if(addBarCode.toUpperCase()=="OK".toUpperCase()){
				$j("#confirm").trigger("click");
				return false;
			}
			if(addBarCode== ""){
				jumbo.showMsg("请填写商品条码！");
				return;
			}
			var isFinish = false;
			var checkMode;
			var data = $j("#tbl_sta_line_list").jqGrid('getRowData');
			$j.each(data,function(i, pl){
				if(pl["barCode"] == addBarCode){
					checkMode=pl["snCheckMode"];
					$j("#hiddenSnCheckMode").val(checkMode);
					isFinish=true;
					return false;
				}
			});
			if(!isFinish){
				$j("#addBarCode").val("");
				$j("#addbarCode").focus();
				jumbo.showMsg("商品条码不在收货范围内！");
				return;
			}else{
				$j("#startNum").focus();
			}
		}
	});
	
	/**
	 * 其实卡号文本框回车事件，卡号示例： 6010203009635884
	 */
	$j("#startNum").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			var barCode = $j("#addBarCode").val().trim();
			var startNum=$j("#startNum").val().trim();
			if(barCode == ""){
				jumbo.showMsg("请填写商品条码！");
				$j("#addBarCode").focus();
				$j("#startNum").val("");
				return;
			}
			if(startNum ==""){
				jumbo.showMsg("请填写起始卡号！");
				$j("#startNum").focus();
				return;
			}else{
				$j("#stopNum").focus();
			}
		}
	});

	/**
	 * 终止卡号文本框回车事件
	 */
	$j("#stopNum").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			var stopNum=$j("#stopNum").val().trim();
			var startNum=$j("#startNum").val().trim();
			var barCode=$j("#addBarCode").val().trim();
			var staId=$j("#sta_id").val();
			if(barCode==""){
				jumbo.showMsg("请填写商品条码！");
				$j("#addBarCode").focus();
				$j("#stopNum").val("");
				return;
			}
			if(startNum==""){
				jumbo.showMsg("请填写起始卡号！");
				$j("#startNum").focus();
				$j("#stopNum").val("");
				return;
			}
			if(stopNum==""){
				jumbo.showMsg("请填写终止卡号！");
				$j("#stopNum").focus();
				return;
			}
			var channelName;
			var skuid;
			var datas = $j("#tbl_sta_line_list").jqGrid('getRowData');
			$j.each(datas,function(i, pl){
				if( pl["barCode"] == barCode){
					skuid=pl["skuId"];
					channelName=pl["channelName"];
					return false;
				}
			});
			//去等号 起始号
			var st = startNum.split('=');
			if(st.length > 0){
				startNum = st[0];
			}
			//去等号 终止号
			var sp = stopNum.split('=');
			if(sp.length > 0){
				stopNum = sp[0];
			}
			var postData={};
			postData["staLine.skuId"]=skuid;
			postData["staLine.staId"]=staId;
			postData["staLine.startNum"]=startNum;
			postData["staLine.stopNum"]=stopNum;
			var data = loxia.syncXhrPost($j("body").attr("contextpath")+ "/validationStarbucksInfo.json",postData);
			if(data){
				if(data.result=="success"){
					var qtys;
					var qtyz;
					var rowdata={};
					if (data && data["pl"]) {
						rowdata["skuCode"]=data['pl']['skuCode'];
						rowdata["barCode"]=data['pl']['barCode'];
						rowdata["skuName"]=data['pl']['skuName'];
//							rowdata["startNum"]=data['pl']['startNum'];
						qtys=data['pl']['startNum'];
						rowdata["startNum"]=qtys;
//							rowdata["stopNum"]=data['pl']['stopNum'];
						qtyz=data['pl']['stopNum'];
						rowdata["stopNum"]=qtyz;
						rowdata["completeQuantity"]=data['pl']['completeQuantity'];
						rowdata["keyProperties"]=data['pl']['keyProperties'];
						rowdata["channelName"]=channelName;
						rowdata["skuId"]=skuid;
						rowdata["id"]=skuid;
						//校验重复卡号
						var datalist = $j("#tbl_add_list" ).getRowData();
						if(datalist.length>0){
							for(var i=0;i<datalist.length;i++){
								if(qtys==datalist[i].startNum || qtys==datalist[i].stopNum || qtyz==datalist[i].startNum || qtyz==datalist[i].stopNum){
									jumbo.showMsg("录入的卡号有重复！");
									$j("#addBarCode").val("");
									$j("#startNum").val("");
									$j("#stopNum").val("");
									return ;
								}
							}
						}
						//
						var len = getTableTrMaxId("tbl_add_list");
						if(len == ""){
							len = 1;
						}
						rowdata.id = len;
						var total=0;
						$j("#tbl_add_list").jqGrid('addRowData', len, rowdata);  //添加数据
						var datax = $j("#tbl_add_list").jqGrid('getRowData');
						$j.each(datax,function(i, item) {
							if(item["barCode"] == barCode){
								total=total+parseInt(item["completeQuantity"]);
							}
						});
						if(datax==0){
							$j("#"+skuid).find("input[name=addQuantity]").val(completeQuantity); 
						}else{
							$j("#"+skuid).find("input[name=addQuantity]").val(total);  //有相同的商品编码就累加 
						}
					}
				}else if(data.result=="error"){
					jumbo.showMsg(data.message);
				}
			} else {
				jumbo.showMsg("数据操作异常");
			}
			
			$j("#addBarCode").val("");
			$j("#startNum").val("");
			$j("#stopNum").val("");
			$j("#addBarCode").focus();
		}
	});
	
	function getTableTrMaxId(tableId){
		if(tableId == "") return ""; 
		var datalist = $j("#"+tableId).getRowData();
		if(datalist.length < 1){
			return "";
		} else {
			return (new Number(datalist[datalist.length - 1].id)) + 1;
		}
	}
	
	/**确认按钮事件	 */
	$j("#confirm").click(function(){
		var postData = {};
		var datalist = $j("#tbl_sta_line_list" ).getRowData();
		postData["sta.id"]= $j("#sta_id").val();
		for(var i=0,d;(d=datalist[i]);i++){
			var qty = $j("#"+d.id).find("input[name=addQuantity]").val();
			if(qty != ''){
				postData["stvLineList[" + i + "].skuId"]=d.skuId;
				postData["stvLineList[" + i + "].receiptQty"]=qty;
				var datalists = $j("#tbl_add_list" ).getRowData();
				//获取卡号信息
				for(var j=0,h;(h=datalists[j]);j++){
					if(d.skuId==h.skuId){
						postData["stvLineLists[" + j + "].skuId"]=h.skuId;
						postData["stvLineLists[" + j + "].startNum"]=h.startNum;
						postData["stvLineLists[" + j + "].stopNum"]=h.stopNum;
					}
		   	    }
				
			}
	   	}

		loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+ "/json/inBoundAffirmHand.json",
				postData,
				{
				success:function(data){
					if(data){
						if(data.result=="success"){
							jumbo.showMsg("操作成功");
							$j("#divDetial").addClass("hidden");
							$j("#divHead").removeClass("hidden");
							queryStaList();
						}else if(data.result=="error"){
							jumbo.showMsg(data.message);
						}
					} else {
						jumbo.showMsg("数据操作异常");
					}
				},
				error:function(){jumbo.showMsg("数据操作异常");}//操作数据异常
		});
		
	});
	//查询店铺
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	$j("#back").click(function(){
		$j("#divDetial").addClass("hidden");
		$j("#divHead").removeClass("hidden");
	});	
	//查询
	$j("#search").click(function(){
		queryStaList();
	});
	//重置	
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
	});
	
	$j("#stopNum").click(function(){
		$j("#addDiv").removeClass("hidden");
	});
});
function changeStr(allstr,start,end,start1,end1){ //allstr:原始字符串，start,开始位置,end：结束位  	
		 var c=allstr.substring(start,end)+allstr.substring(start1,end1); 
	      return c; 
	 
}
function queryStaList(){
	var url = $j("body").attr("contextpath") + "/findInboundSta.json";
	$j("#tbl_sta_list").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_sta_list",{"intStaType":"whSTAType","intStaStatus":"whSTAStatus"},
		url,loxia._ajaxFormToObj("queryForm"));
}
function doDelete(id){
	$j("#"+id).remove();
}
