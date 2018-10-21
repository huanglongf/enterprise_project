var returnAppId = ""; // 全局变量  存储退货申请ID
//var returnStatus = ""; //全局变量  记录节点   1:创建并提交。 2：作废 。 3：新建。 4.无关联待确认。5,待处理
var returnStatus = ""; //全局变量  记录节点    0：作废 、 1：新建、 2.已提交、3：已反馈，4：新建并提交
var objAppId = ""; // 全局变量  存储退货申请ID
function showDetail(obj){
	clearForm();
	$j('#suerAdd').text("保存");
	$j("#suerAdd").css("width","70px"); 
	$j('#addAndConmit').text("保存并提交");
	$j("#addAndConmit").css("width","110px"); 
	//显示明细
	$j("#again").addClass("hidden");
	$j("#div-detail").removeClass("hidden");
	$j("#div-main").addClass("hidden");
	$j('#usEless').removeClass("hidden");
	$j("#file").val("");
	//加载数据
	if (obj != null){
		var tr = $j(obj).parents("tr[id]");
		var id=tr.attr("id");
		returnAppId = id;
	} else {
		returnAppId = objAppId;
	}
	var owner= $j("#tbl-return-app").getCell(returnAppId,"ownerCode");
	var slipCode= $j("#tbl-return-app").getCell(returnAppId,"slipCode");
	var slipCode1= $j("#tbl-return-app").getCell(returnAppId,"slipCode1");
	var slipCode2= $j("#tbl-return-app").getCell(returnAppId,"slipCode2");
	var lpCode= $j("#tbl-return-app").getCell(returnAppId,"lpCode");
	var trankNo= $j("#tbl-return-app").getCell(returnAppId,"trankNo");
	var returnUser= $j("#tbl-return-app").getCell(returnAppId,"returnUser");
	var telephone= $j("#tbl-return-app").getCell(returnAppId,"telephone");
	var locationCode= $j("#tbl-return-app").getCell(returnAppId,"locationCode");
	var memo= $j("#tbl-return-app").getCell(returnAppId,"memo");
	var staCode= $j("#tbl-return-app").getCell(returnAppId,"staCode");
	var statusName= $j("#tbl-return-app").getCell(returnAppId,"statusName");
	var omsStatuss= $j("#tbl-return-app").getCell(returnAppId,"omsStatuss");
	var omsRemark= $j("#tbl-return-app").getCell(returnAppId,"omsRemark");
	var returnSlipCode= $j("#tbl-return-app").getCell(returnAppId,"returnSlipCode");
	
	$j("#omsStatusLable").html(statusName);
	$j("#omsVluesLable").html(omsStatuss);
	$j("#omsReturnCodeLable").html(returnSlipCode);
	$j("#omsRemarkLable").html(omsRemark);
	$j("#staCodes").attr("value",staCode);
	$j("#appSlipCode1").attr("value",slipCode1);
	$j("#companyshop2").attr("value",owner);
	$j("#selTrans2").attr("value",lpCode);
	$j("#slipCodes").attr("value",slipCode);
	$j("#slipCodes1").attr("value",slipCode2);
	$j("#trankNos").attr("value",trankNo);
	$j("#returnUsers").attr("value",returnUser);
	$j("#telephones").attr("value",telephone);
	$j("#locationCodes").attr("value",locationCode);
	$j("#memo").attr("value",memo);
	$j("#barCode").focus();
	//加载明细数据
	var rs = loxia.syncXhrPost($j("body").attr("contextpath")+ "/fingReturnSkuByRaId.json?raId=" + returnAppId);
	var skuLists = null;
	if(rs && rs.skuList){
		skuLists = rs.skuList;
		for (var i in skuLists){
			if (skuLists[i].skuId == null) { //加载手动添加的商品
//				var tempId = parseInt(Math.floor(Math.random()*50000+1)); //随机生成1～50000之间的随机整数
//				var obj2 = {};
//				obj2 = {	
//						tempId:tempId,
//						quantity:skuLists[i].qty,
//						skuInfo:skuLists[i].skuInfo,
//						invStatusId:skuLists[i].invStatusId
//				};
//				addSku2(obj2, false);
			}else { //加载条码扫描的商品
				var obj1 = {};
				obj1 = {	
						id:skuLists[i].id,
						skuId:skuLists[i].skuId,
						skuCode:skuLists[i].skuCode,
						barCode:skuLists[i].skuBarCode,
						skuName:skuLists[i].skuName,
						quantity:skuLists[i].qty,
						invStatusId:skuLists[i].statusName,
						wmsOrderType:skuLists[i].wmsOrderType
				};
				addSku(obj1 , false);
			}
		}
	}else{
		jumbo.showMsg("系统初始化异常！");
	}
	// 新建状态可以编辑，其他状态只读
	var statusName= $j("#tbl-return-app").getCell(returnAppId,"statusName");
	if (statusName == "已创建" || (statusName == "已反馈" && omsStatuss == "无法确认") ){
		loadForm();
	} else{
		loadForm2();
	}
	if(statusName == "已提交"){
		returnStatus = 2;
	} else if(statusName == "已反馈"){
		returnStatus = 3;
		if(omsStatuss == "无法确认"){
			$j("#again").removeClass("hidden");
			$j("#suerAdd").addClass("hidden");
			$j("#addAndConmit").addClass("hidden");
		}
	}
}

$j(document).ready(function (){
	$j("#div-detail").addClass("hidden");
	$j("#again").addClass("hidden");
	//loxia.byId("inQty").setEnable(false);
	$j("#trackingNo").focus();
	$j("#usEless").addClass("hidden");
	jumbo.loadShopList("companyshop");//加载仓库下关联店铺
	jumbo.loadShopList("companyshop2");//加载仓库下关联店铺
	//加载物流商下拉列表
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findPlatformList.json");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selTrans,#selTrans-tabs2"));
	}
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selTrans2,#selTrans-tabs2"));
	}
	//快递转换
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	$j("#tbl-return-app").jqGrid({
		datatype: "json",
		//url:$j("body").attr("contextpath")+"/json/returnApplicationList.json",
		colNames:["ID","事件编号","店铺","退货作业单号","相关单据号","销售相关单据号","销售平台订单号","退换货指令","拒收反馈单号","物流","快递单号","WMS状态","OMS状态","创建时间","退换货人","电话","操作人","商品总数","暂存库位","反馈时间","备注","店铺编码","OMS备注","退货指令状态"],
		colModel:[
		          {name: "id", index: "id", hidden: true},	
		          {name: "code", index: "code", width: 120, formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, resizable: true,sortable:false},
		          {name: "owner", index: "owner", width: 160, resizable: true,sortable:false},
		          {name: "staCode", index: "staCode", width: 90,resizable: true,sortable:false},
		          {name: "slipCode", index: "slipCode", width: 90, resizable: true,sortable:false},
		          {name: "slipCode1", index: "slipCode1", width: 90, resizable: true,sortable:false},
		          {name: "slipCode2", index: "slipCode2", width: 90, resizable: true,sortable:false},
		          {name: "returnSlipCode", index: "returnSlipCode", width: 90, resizable: true,sortable:false},
		          {name: "returnSlipCode1", index: "returnSlipCode1", width: 90, resizable: true,sortable:false},
		          {name: "lpCode", index:"lpCode",width:80,resizable:true,formatter:'select',editoptions:trasportName},
		          {name: "trankNo", index: "trankNo", width: 90, resizable: true,sortable:false},
		          {name: "statusName", index: "statusName", width: 90, resizable: true,sortable:false},
		          {name: "omsStatuss", index: "omsStatuss", width: 90, resizable: true,sortable:false},
		          {name: "createTime", index: "createTime", width: 140, resizable: true,sortable:false},
		          {name: "returnUser", index: "returnUser", width: 90, resizable: true,sortable:false},
		          {name: "telephone", index: "telephone",  hidden: true,width: 90, resizable: true,sortable:false},
		          {name: "creataUserName", index: "creataUserName", width: 90, resizable: true,sortable:false},
		          {name: "skuQty", index: "skuQty", width: 90, resizable: true,sortable:false},
		          {name: "locationCode", index: "locationCode", width: 90, resizable: true,sortable:false},
		          {name: "feedBackTime", index: "feedBackTime", width: 90, hidden: false, resizable: true,sortable:false},
		          {name: "memo", index: "memo", width: 90,resizable: true,hidden: true,sortable:false},
		          {name: "ownerCode", index: "ownerCode", width: 90, hidden: true, resizable: true,sortable:false},
		          {name: "omsRemark", index: "omsRemark", width: 90, hidden: true,hidden: true,resizable: true,sortable:false},
		          {name:"status",index:"status",width:70,resizable:true,formatter:'select',editoptions:staStatus}
		          ],
	    caption: "退货申请列表",
	   	sortname: 'a.id',
	    multiselect: false,
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pager",
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0"}
	}); 
	jumbo.bindTableExportBtn("tbl-return-app");

	$j("#detail_tbl_one").jqGrid({
		datatype: "json",
		colNames:["ID","商品ID","SKU编码","条形码","商品名称","数量","商品状态","WMS工单类型","操作"],
		colModel:[
		          {name: "id", index: "id", width: 100, hidden: true},	
		          {name: "skuId", index: "skuId", width: 100, hidden: false},	
		          {name: "skuCode", index: "skuCode", width: 100,resizable: true,sortable:false},	
		          {name: "barCode", index: "barCode", width: 100, resizable: true,sortable:false},
		          {name: "skuName", index: "skuName", width: 140, resizable: true,sortable:false},
		          {name: "quantity", index: "quantity", width: 100, formatter:function(cellvalue, options, rowObject) {
				
		        	 // return '<input type="text"  id="q_' + rowObject["skuId"] + '" name="q_' + rowObject["skuId"] + '"  onkeyup="onKeyUp(this); " />';}},
		        	  return '<a id="q_' + rowObject["skuId"] + '">1</a>';}},
		          {name: "skuStatus", index: "skuStatus", width: 100,  formatter:function(cellvalue, options, rowObject) {
		        	/*	var $firstTrRole = $j("#detail_tbl_one").find("tr").eq(1).attr("role");  
		        		var rowid = $firstTrRole == "row" ? Math.max.apply(Math,ids):0; 
		        	  alert(rowid);*/
		        	  return '<select loxiaType="select" id="s_' + rowObject["id"] + '" name="s_' +rowObject["id"] +  '" mandatory="true"><option value="残次品">残次品</option><option value="待处理品">待处理品</option><option value="良品">良品</option><option value="良品不可销售">良品不可销售</option><option value="待报废">待报废</option><option value="临近保质期">临近保质期</option></select>';}},
		          
				 {name: "wmsOrderType", index: "wmsOrderType", width: 100,  formatter:function(cellvalue, options, rowObject) {
					 //从后台读取
					//	var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/getChooseOptionByCode.do",{"categoryCode":"businessType"});
					 	var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/findAdPackageByOuIdByAdName.do");
						var wmsStatusStr=null; 
						for(var i=0;i<result.length;i++){
						//	alert(result[i].optionValue);
						wmsStatusStr+='<option value="'+result[i].adName+'">'+result[i].adName+'</option>';
							//wmsStatusStr+='<option value="'+result[i].optionValue+'">'+result[i].optionValue+'</option>';

						}
						return '<select loxiaType="select" id="y_' + rowObject["id"]+ '" name="s_' +rowObject["id"] +  '" mandatory="true">'+wmsStatusStr+'</select>';}},
					  //return '<select loxiaType="select" id="s_' + rowObject["skuId"] + '" name="s_' + rowObject["skuId"] +  '" mandatory="true"><option value="残次品">残次品</option><option value="待处理品">待处理品</option><option value="良品">良品</option><option value="良品不可销售">良品不可销售</option><option value="待报废">待报废</option><option value="临近保质期">临近保质期</option></select>';}},
						
				  {name:"btnDel",width:100,resizable:true}],
	    caption: "扫描录入商品列表",
	    multiselect: false,
	    height:"auto",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0"},
		gridComplete: function(){
			var btn1 = '<div><button type="button" style="width:100px;" class="confirm" id="btnDel" name="btnDel" loxiaType="button" onclick="deleteSku(this);">'+"删除"+'</button></div>';
			var ids = $j("#detail_tbl_one").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				$j("#detail_tbl_one").jqGrid('setRowData',ids[i],{"btnDel":btn1});
			}
			loxia.initContext($j(this));
		}
	}); 
	
	$j("#detail_tbl_two").jqGrid({
		datatype: "json",
		colNames:["商品ID","商品信息描述","数量","商品状态","操作"],
		colModel:[
		          {name: "tempId", index: "tempId", width: 100, hidden: true},	
		          {name: "skuInfo", index: "skuInfo",width: 350, formatter:function(cellvalue, options, rowObject) {
		        	  return '<input type="text"  id="i_' + rowObject["tempId"] + '" name="i_' + rowObject["tempId"] + '" maxlength = "200" onkeyup="onKeyUp2(this); " />';}},
		          {name: "quantity", index: "quantity", width: 100, formatter:function(cellvalue, options, rowObject) {
		        	  return '<input type="text"  id="q_' + rowObject["tempId"] + '" name="q_' + rowObject["tempId"] + '"  onkeyup="onKeyUp(this); " />';}},
		          {name: "skuStatus", index: "skuStatus", width: 100,  formatter:function(cellvalue, options, rowObject) {
						return '<select loxiaType="select" id="s_' + rowObject["tempId"] + '" name="s_' + rowObject["tempId"] +  '"mandatory="true"><option value="121">残次品</option><option value="122">待处理品</option><option value="123">良品</option><option value="124">良品不可销售</option><option value="162">待报废</option><option value="421">临近保质期</option></select>';}},
		          {name:"btnDel2",width:100,resizable:true}],
	    caption: "手动添加商品列表",
	    multiselect: false,
	    height:"auto",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0"},
		gridComplete: function(){
			var btn2 = '<div><button type="button" style="width:100px;" class="confirm" id="btnDel2" name="btnDel2" loxiaType="button" onclick="deleteSku2(this);">'+"删除"+'</button></div>';
			var ids = $j("#detail_tbl_two").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				$j("#detail_tbl_two").jqGrid('setRowData',ids[i],{"btnDel2":btn2});
			}
			loxia.initContext($j(this));
		}
	}); 
	
	$j("#trackingNo").keydown(function(evt){
		if(evt.keyCode === 13){
			var trackingNo = this.value;
			if(trackingNo == ""){
				jumbo.showMsg("请输入运单号");
				return;
			}
			$j("#file").val("");
			$j("#omsStatusLable").html(""); 
			$j("#omsVluesLable").html("");
			$j("#omsReturnCodeLable").html("");
			$j("#omsRemarkLable").html("");
			loxia.lockPage();
			loxia.asyncXhrPost($j("body").attr("contextpath") + "/json/staTrackingNoCheck.json",
				{"sta.trackingNo":trackingNo},
				{success:function (data) {
					loxia.unlockPage();
					if(data&&data.id){
						clearForm();
						$j('#suerAdd').text("保存");
						$j("#suerAdd").css("width","70px"); 
						$j('#addAndConmit').text("保存并提交");
						$j("#addAndConmit").css("width","110px"); 
						//显示明细
						$j("#div-detail").removeClass("hidden");
						$j("#again").addClass("hidden");
						$j("#div-main").addClass("hidden");
						$j('#usEless').removeClass("hidden");
						$j("#companyshop2").attr("value",data["ownerCode"]);
						$j("#selTrans2").attr("value",data["lpCode"]);
						
						$j("#omsStatusLable").html(data["statusName"]);
						$j("#omsVluesLable").html(data["omsStatusName"]);
						$j("#omsReturnCodeLable").html(data["returnSlipCode"]);
						$j("#omsRemarkLable").html(data["omsRemark"]);
						$j("#staCodes").attr("value",data["staCode"]);
						$j("#appSlipCode1").attr("value",data["slipCode1"]);
						$j("#slipCodes").attr("value",data["slipCode"]);
						$j("#slipCodes1").attr("value",data["slipCode2"]);
						$j("#trankNos").attr("value",data["trankNo"]);
						$j("#returnUsers").attr("value",data["returnUser"]);
						$j("#telephones").attr("value",data["telephone"]);
						$j("#locationCodes").attr("value",data["locationCode"]);
						$j("#memo").attr("value",data["memo"]);
						$j("#barCode").focus();
						returnAppId = data["id"];
						//加载明细数据
						var rs = loxia.syncXhrPost($j("body").attr("contextpath")+ "/fingReturnSkuByRaId.json?raId=" + returnAppId);
						var skuLists = null;
						if(rs && rs.skuList){
							skuLists = rs.skuList;
							for (var i in skuLists){
								if (skuLists[i].skuId != null) { //加载手动添加的商品
									var obj1 = {};
									obj1 = {	
											id:skuLists[i].id,
											skuId:skuLists[i].skuId,
											skuCode:skuLists[i].skuCode,
											barCode:skuLists[i].skuBarCode,
											skuName:skuLists[i].skuName,
											quantity:skuLists[i].qty,
											invStatusId:skuLists[i].statusName,
											wmsOrderType:skuLists[i].wmsOrderType
									};
									addSku(obj1 , false);
								}
							}
						}else{
							jumbo.showMsg("系统初始化异常！");
						}
						
						// 新建状态可以编辑，其他状态只读
							var statusName= $j("#tbl-return-app").getCell(returnAppId,"statusName");
							var omsStatuss= $j("#tbl-return-app").getCell(returnAppId,"omsStatuss");
							if (statusName == "已创建"){
								loadForm();
							} else{
								loadForm2();
							}
							if(statusName == "已提交"){
								returnStatus = 2;
							} else if(statusName == "已反馈"){
								returnStatus = 3;
								if(omsStatuss == "无法确认"){
									$j('#usEless').removeClass("hidden");
									$j("#again").removeClass("hidden");
									$j("#suerAdd").addClass("hidden");
									$j("#addAndConmit").addClass("hidden");
								}
							}
					}else{
						$j('#add').trigger("click");
						$j('#trankNos').val(trackingNo);
						returnAppId ="";
					}
				}
			});
			$j("#trackingNo").val("");
		}
	});
	
	//查询退货申请列表
	$j("#search").click(function(){
		var postData = loxia._ajaxFormToObj("form_query");  
		var url = $j("body").attr("contextpath")+"/json/returnApplicationList.json";
		$j("#tbl-return-app").jqGrid('setGridParam',{url:url,page:1,postData:postData}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl-return-app",{},url,postData);
	});
	
	
	//重置
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val(0);
		$j("#brand").attr("value","1");
		$j("#status").attr("value","");
		
	});
	
	//新增
	$j("#add").click(function(){
		returnStatus = 4;
		$j("#file").val("");
		$j("#div-main").addClass("hidden"); 
		$j("#div-detail").removeClass("hidden");
		$j("#barCode").focus();
		$j('#suerAdd').text("创建");
		$j("#suerAdd").css("width","70px"); 
		$j('#addAndConmit').text("创建并提交");
		$j("#addAndConmit").css("width","110px"); 
		$j("#usEless").addClass("hidden");
		$j("#omsStatusLable").html(""); 
		$j("#omsVluesLable").html("");
		$j("#omsReturnCodeLable").html("");
		$j("#omsRemarkLable").html("");
		clearForm();
		loadForm();
	});
	
	//返回
	$j("#back").click(function(){
		$j("#div-detail").addClass("hidden");
		$j("#div-main").removeClass("hidden");
		$j("#brand").attr("value","1");
		$j("#trackingNo").focus();
	});
	
	//事件编码回车事件
	$j("#appCode").keydown(function(evt){
		if(evt.keyCode == 13){
			var appCode = $j("#appCode").val().trim();
			var rl = loxia.syncXhrPost($j("body").attr("contextpath")+ "/findReturnListByParam.json", {"barCode" : appCode});
			if (rl && rl["raId"]) {
				objAppId = rl["raId"];
				showDetail(null);
			} else {
				loxia.tip(this,"指定相关单据号的退货申请单不在列表内！");
			}
		}
	});
	
	//录入数量点击事件
	$j("#btnInQty").click(function(){
		loxia.byId("inQty").setEnable(true);
		$j("#inQty").select(); 
	});
	var adId=0;
	//商品条码回车事件
	$j("#barCode").keydown(function(evt){
		if(evt.keyCode == 13){
			var barCode = $j("#barCode").val();
			var inQty = $j("#inQty").val();
			var companyshop=$j("#companyshop2").val();
			if(null==companyshop || companyshop==''){
				jumbo.showMsg("请选择店铺");
				return;
			}
			var sl = loxia.syncXhrPost($j("body").attr("contextpath")+ "/getSkuByBarCodeAndReturnInfo1.json", {"barCode" : barCode,"companyshop":companyshop});
			if (sl && sl["skuId"]) {
				//alert(sl["skuCode"] + "--" + sl["barCode"] + "--" + sl["skuName"]);
				adId = parseInt(adId)+1;
				var obj = {};
				obj = {	
						id:adId,
						skuId:sl["skuId"],
						skuCode:sl["skuCode"],
						barCode:sl["barCode"],
						skuName:sl["skuName"],
						quantity:inQty
				};
				addSku(obj, true);
				$j("#barCode").attr("value","");
			} else {
				loxia.tip(this,"指定商品条码的商品不存在！");
			}
		}
	});
	
	//添加商品点击事件
	$j("#skuAdd").click(function(){
		var tempId = parseInt(Math.floor(Math.random()*50000+1)); //随机生成1～50000之间的随机整数
		var inQty = $j("#inQty").val();
		var obj = {};
		obj = {	
				tempId:tempId,
				quantity:inQty
		};
		addSku2(obj, true);
	});
	
	//创建并提交
	$j("#addAndConmit").click(function(){
		returnStatus = 4;
		commitRequest();
	});
	
	//作废
	$j("#usEless").click(function(){
		returnStatus = 0;
		var postData = {};
		postData["app.id"] = returnAppId;
		postData["app.returnStatus"] = returnStatus;
		 var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/addReturnApplication.json",postData);
		    if(rs.result=='success'){
		    	//清空表单数据
		    	clearForm();
				//隐藏明细，显示列表
				$j("#div-main").removeClass("hidden");
				$j("#div-detail").addClass("hidden");
				$j("#trackingNo").focus();
				var postData = loxia._ajaxFormToObj("form_query");  
				var url = $j("body").attr("contextpath")+"/json/returnApplicationList.json";
				$j("#tbl-return-app").jqGrid('setGridParam',{
					url:url,postData:postData}).trigger("reloadGrid");
				jumbo.showMsg("操作成功！");
		    }else {
		    	if(rs.msg != null){
					jumbo.showMsg(rs.msg);
				}else{
					jumbo.showMsg("操作失败！");
				}
		    }
	});
	
	//确认
	$j("#again").click(function(){
		returnStatus = 5;
		commitRequest();
	});
	
	
	//确认创建 点击事件
	$j("#suerAdd").click(function(){
		returnStatus = 1;
		commitRequest();
	});
	
});

function commitRequest(){
	var isUpload = false;
	/*if($j("#file").val() !=""){
	     isUpload = true;
		if(!/^.*\.doc$/.test($j("#file").val()) && !/^.*\.docx$/.test($j("#file").val())){
			jumbo.showMsg("请选择正确的doc文件");
			return;
		}
	}*/
	var sel=$j("#selTrans2").val().trim();
	if(sel==""||sel==null){
		jumbo.showMsg("物流商不能为空");
		return;
	}
	var postData = loxia._ajaxFormToObj("form_info");
	var skuString1 = "";
	//获取条码扫描的商品信息。 拼接成字符串。  格式： 商品ID,数量;状态ID/......
	var ids = $j("#detail_tbl_one").jqGrid('getDataIDs');
	for(var i=0;i<ids.length;i++){
		var skuId = $j("#detail_tbl_one").getCell(ids[i],"skuId");
		var id = $j("#detail_tbl_one").getCell(ids[i],"id");

	//	var quantity = $j("#q_" + skuId).val();//RUN
		var quantity = $j("#q_" + skuId).text();
	 	var status = $j("#s_" + id).val();
	 	var wmsOrderType = $j("#y_" + id).val();
	 	//alert(wmsOrderType);
		//var  status= $j("#detail_tbl_one").getCell(ids[i],"s_"+id);
		$j("#S_"+'01').val();
		//alert(status);
		if(quantity == "" || quantity == "0"){
			jumbo.showMsg("扫描录入商品列表第【"+(i+1)+"】行商品数量不能为0或空！");
			return;
		}
		skuString1 += skuId+"," + quantity + ";" + status+"#"+ wmsOrderType+ "/";
		//alert(skuString1);
	}
	//获取手动添加的商品信息。 拼接成字符串。  格式： 商品信息描述,数量;状态ID/......
//	var ids = $j("#detail_tbl_two").jqGrid('getDataIDs');
//	for(var i=0;i<ids.length;i++){
//		var info = $j("#detail_tbl_two").getCell(ids[i],"skuInfo");
//		//由于手动添加构造的列，没有固定的ID。所以需要根据html截取，获取单元格的ID。
//		var tempId = info.substring(info.indexOf("i_")+2,info.indexOf("name=")-2);
//		var info =  $j("#i_" + tempId).val();
//		var quantity =  $j("#q_" + tempId).val();
//		var status =  $j("#s_" + tempId).val();
//		if(info.trim() == ""){
//			jumbo.showMsg("手动添加商品列表第【"+(i+1)+"】行商品信息描述不能为空！");
//			return;
//		}
//		if(quantity == "" || quantity == "0"){
//			jumbo.showMsg("手动添加商品列表第【"+(i+1)+"】行商品数量不能为0或空！");
//			return;
//		}
//		skuString2 += info+"," + quantity + ";" + status + "/";
//	}
	if (skuString1 == ""){
		jumbo.showMsg("商品列表不能为空！");
		return;
	}
	if($j("#memo").val().trim() == ""){
		jumbo.showMsg("备注不能为空！");
		return;
	}
	postData["app.memo"] = $j("#memo").val();
	postData["app.skuString1"] = skuString1;
	postData["app.id"] = returnAppId;
	postData["app.returnStatus"] = returnStatus;
	postData["app.isUpload"] = isUpload;
	postData["app.brand"] = '1';
	var errorMsg = loxia.validateForm("form_info");
	if(errorMsg.length == 0){
	    //$j("#importForm").attr("action",$j("body").attr("contextpath")+"/json/addReturnApplication.json",postData);
		//loxia.submitForm("importForm");
		var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/addReturnApplication.json",postData);
	    if(rs.result=='success'){
	    	var code = rs.code;
	    	if(isUpload == true && code != ""){
				var rss = loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/uploadReturnDoc.do?code="+code);
				$j("#importForm").attr("action",rss);
				loxia.submitForm("importForm");
	    	}
	    	if(returnStatus == 5){
	    		jumbo.showMsg("操作成功！原单作废，生成新单："+code);
			}else{
				jumbo.showMsg("操作成功！");
			}
	    	//清空表单数据
	    	clearForm();
			//隐藏明细，显示列表
			$j("#div-main").removeClass("hidden");
			$j("#div-detail").addClass("hidden");
			$j("#trackingNo").focus();
			var postDatas = loxia._ajaxFormToObj("form_query"); 
			var url = $j("body").attr("contextpath")+"/json/returnApplicationList.json";
			$j("#tbl-return-app").jqGrid('setGridParam',{
				url:url,postData:postDatas}).trigger("reloadGrid");
			
	    }else {
	    	if(rs.msg != null){
				jumbo.showMsg(rs.msg);
			}else{
				jumbo.showMsg("操作失败！");
			}
	    }
		
	}else{
		jumbo.showMsg(errorMsg);
	}
}
//添加扫描商品   // isAdd = true 新增 。 else  修改
function addSku(obj, isAdd){

	var ids = $j("#detail_tbl_one").jqGrid('getDataIDs');
	var isCopy = true;
	//RUN
	/*for(var i=0;i<ids.length;i++){//判断是否在列表中已添加
		var skuCode = $j("#detail_tbl_one").getCell(ids[i],"skuCode");
		if(skuCode == obj.skuCode){
			isCopy = false;
		}
	}*/
	
	if(isCopy){//如未添加，则添加数据
		var $firstTrRole = $j("#detail_tbl_one").find("tr").eq(1).attr("role");  
		var rowid = $firstTrRole == "row" ? Math.max.apply(Math,ids):0;  
		var newrowid = parseInt(rowid)+1;
		$j("#detail_tbl_one").jqGrid("addRowData", newrowid, obj, "last");//将新添加的行插入到最后一列
		$j("#q_"+obj.skuId+"").attr("value",obj.quantity);
	}
	//RUN
	/*else{//如已添加，则数量++
		var quantity = $j("#q_"+obj.skuId+"").val();
		var inQty = $j("#inQty").val();
		$j("#q_"+obj.skuId+"").attr("value",parseInt(quantity) + parseInt(inQty));
	}*/
	//修改
	if (!isAdd){
		$j("#q_"+obj.skuId+"").attr("value",obj.quantity);
		$j("#s_"+obj.id+"").attr("value",obj.invStatusId);
		//alert("1_"+obj.wmsOrderType);
		$j("#y_"+obj.id+"").attr("value",obj.wmsOrderType);

	}
}

//添加手动商品
function addSku2(obj, isAdd){
	var ids = $j("#detail_tbl_two").jqGrid('getDataIDs');
	var $firstTrRole = $j("#detail_tbl_two").find("tr").eq(1).attr("role");  
	var rowid = $firstTrRole == "row" ? Math.max.apply(Math,ids):0;  
	var newrowid = parseInt(rowid)+1;
	$j("#detail_tbl_two").jqGrid("addRowData", newrowid, obj, "last");//将新添加的行插入到最后一列
	$j("#q_"+obj.tempId+"").attr("value",obj.quantity);
	//修改
	if (!isAdd){
		$j("#i_"+obj.tempId+"").attr("value",obj.skuInfo);
		$j("#q_"+obj.tempId+"").attr("value",obj.quantity);
		$j("#s_"+obj.tempId+"").attr("value",obj.invStatusId);
	}
}

//删除扫描商品
function deleteSku(tag){
	var id = $j(tag).parents("tr").attr("id");
	$j("#detail_tbl_one").find("tr[id="+id+"]").remove();
}

//删除手动商品
function deleteSku2(tag){
	var id = $j(tag).parents("tr").attr("id");
	$j("#detail_tbl_two").find("tr[id="+id+"]").remove();
}

//数量input键盘抬起事件    只运行输入正整数
function onKeyUp(tag){
	tag.value=tag.value.replace(/\D/g,'');
}

//商品信息 input键盘抬起事件  不能输入特殊字符 “/” ，因为字符串拼接需要
function onKeyUp2(tag){
	tag.value=tag.value.replace("/",'');
}

//清空表单数据
function clearForm(){
	$j("#form_query input").val("");
	$j("#form_info input").val("");
	$j("#form_info select").val(0);
	$j("#form_query select").val(0);
	$j("#memo").attr("value","");
	$j("#status").attr("value","");
	$j("#brand").attr("value","1");
	$j("#detail_tbl_one").clearGridData(false);
	$j("#detail_tbl_two").clearGridData(false);
	returnAppId = ""; 
	returnStatus = "";
}

//初始化表单
function loadForm(){
	$j("#form_info input").attr("disabled",false);
	$j("#form_info select").attr("disabled",false);
	$j("#tableForm input").attr("disabled",false);
	$j("#tableForm select").attr("disabled",false);
	$j("#tableForm button").attr("disabled",false);
	$j("#memo").attr("disabled",false);
	$j("#suerAdd").removeClass("hidden");
    $j("#addAndConmit").removeClass("hidden");
	//$j("#usEless").removeClass("hidden");
	$j("#skuAdd").removeClass("hidden");
	$j("#barCode").attr("disabled",false);
	$j("#btnInQty").attr("disabled",false);
}

//初始化表单
function loadForm2(){
	$j("#form_info input").attr("disabled",true);
	$j("#form_info select").attr("disabled",true);
	$j("#tableForm input").attr("disabled",true);
	$j("#tableForm select").attr("disabled",true);
	$j("#tableForm button").attr("disabled",true);
	$j("#memo").attr("disabled",true);
	$j("#suerAdd").addClass("hidden");
	$j("#addAndConmit").addClass("hidden");
	$j("#usEless").addClass("hidden");
	$j("#skuAdd").addClass("hidden");
	$j("#barCode").attr("disabled",true);
	$j("#btnInQty").attr("disabled",true);
}
