$j.extend(loxia.regional['zh-CN'],{
	CODE : "配货批次号",
	INTSTATUS : "状态",
	PLANBILLCOUNT : "计划配货单据数",
	CHECKEDBILLCOUNT : "已核对单据数",
	SHIPSTACOUNT : "已发货单据数",
	PLANSKUQTY : "计划配货商品件数",
	CHECKEDSKUQTY : "已核对商品件数",
	SHIPSKUQTY : "已发货商品件数",
	CHECKEDTIME : "最后核对时间",
	EXECUTEDTIME : "最后发货时间",
	PICKINGTIME : "开始配货时间",
	PICKING_LIST : "配货清单列表",
	CREATE_TIME : "创建时间",
	INIT_SYSTEM_DATA_EXCEPTION : "初始化系统参数异常",
	KEY_PROPS : "扩展属性",
	MEMO : "备注",
	STA_CODE : "作业单号",
	INTSTATUS : "状态",
	REFSLIPCODE : "相关单据号",
	INTTYPE : "作业类型名称",
	OWNER : "相关店铺",
	LPCODE : "物流服务商简称",
	CREATETIME : "创建时间",
	STVTOTAL : "计划执行总件数",
	WAITTING_CHECKED_LIST : "待核对列表",
	
	CHECKED_LIST : "已核对列表",
	SKUCODE : "SKU编码",
	LOCATIONCODE : "库位编码预览",
	SKUNAME : "商品名称",
	JMSKUCODE : "商品编码",
	BARCODE : "条形码",
	QUANTITY : "计划执行数量",
	COMPLETEQUANTITY : "已执行数量",
	
	INPUT_CODE : "请输入相关单据号",
	NO_CODE : "指定单据号的作业单不在待核对的列表！",
	BARCODE_NOT_EXISTS : "条形码不存在",
	TRACKINGNO_EXISTS : "快递单号已经存在",
	DELETE : "删除",
	TRACKINGNO_RULE_ERROR : "快递单号格式不对",
	SURE_OPERATE : "确定执行本次操作",
	QUANTITY_ERROR : "数量错误",
	WEIGHT_RULE_ERROR : "已执行量大于计划执行量，如果继续执行核对，将直接按照计划执行量执行核对。是否继续？",
	INPUT_TRACKINGNO : "请输入快递单号",
	OPERATE_FAILED : "执行核对失败！",
	EXISTS_QUANTITY_GQ_EXECUTEQTY : "请检查核对数量,存在商品未核对",
	TRUNKPACKINGLISTINFO : "装箱清单打印中，请等待..."
});
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}
var barcodeList;
function showDetail(obj){
	var row=$j(obj).parent().parent().attr("id");
	var data = $j("#tbl-dispatch-list").jqGrid("getRowData",row);
	var datas ={};
	datas["plCmd.code"]=data['code'];
	var rs = loxia.syncXhr($j("body").attr("contextpath") + "/json/checkPickingisOver.json",datas);
	if(rs && rs.result=='success'){
		// do nothing
	}else{
		jumbo.showMsg("批次号"+data['code']+"未拣货完成，不能直接操作！ 请至[拣货批次开始]页面操作");
		return;
	}
	$j("#picking-list").addClass("hidden");
	$j("#detail").removeClass("hidden");
	$j("#barCode").focus();
	$j("#plid").val(row);
	$j("#plCode").html(data['code']);
	$j("#plCreateTime").html(data['createTime']);
	$j("#plCreateUser").html(data['crtUserName']);
	$j("#plBillCount").html(data['planBillCount']);
	$j("#tbl-detail-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/findStaLineByPickingId.json"),
			postData:{"pickinglistId":row}}).trigger("reloadGrid",[{page:1}]);
}

$j(document).ready(function (){
	$j("#pl_check_dialog").dialog({title: "拣货核对信息", modal:true, autoOpen: false, width: 600, height: 300});
	$j("#pickinglistCodeQuery").focus();
	$j("#tbl-div").addClass("hidden");
	var baseUrl = $j("body").attr("contextpath");
	var intStatus=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"pickingListStatus"});
	
	$j("#tbl-dispatch-list").jqGrid({
		url:baseUrl + "/pickingListForPickOut.json",
		datatype: "json",
		colNames: ["ID","配货批单号","状态","创建人","创建时间","计划单据数"],
		colModel: [{name: "id", index: "p.id", hidden: true},
					{name:"code", index:"p.code", width:140,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, resizable:true},
					{name:"intStatus", index:"p.status" ,width:80,resizable:true ,formatter:'select',editoptions:intStatus},
					{name:"crtUserName",index:"u.user_name",width:90,resizable:true},
					{name:"createTime",index:"p.create_time",width:150,resizable:true},
					{name:"planBillCount",index:"p.plan_bill_count",width:50,resizable:true}
				  ],
		caption: "配货批列表",
	   	sortname: 'id',
	   	multiselect: false,
		sortorder: "desc",
		pager:"#pager",
		width:"auto",
		height:"auto",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		viewrecords: true,
	   	rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	jumbo.bindTableExportBtn("tbl-dispatch-list",{"intStatus":"pickingListStatus"},baseUrl + "/pickingListForPickOut.json");
	
	var intStaStatus=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"whSTAStatus"});
	$j("#tbl-detail-list").jqGrid({
		//url:baseUrl + "/findstalineforpickinginfo.json",
		datatype: "json",
		colNames: ["ID","作业单ID","作业单号","状态","SKU编码","SKU条码","商品名称","数量","已核对数量","箱号","完成度","操作"],
		colModel: [{name: "id", index: "sku.id", hidden: true},
		           {name:"staId", index:"sta.id", width:120,resizable:true,hidden: true},
		           {name:"staCode", index:"sta.code", width:120,resizable:true},
					{name:"intStatus", index:"sta.status" ,width:60,resizable:true ,formatter:'select',editoptions:intStaStatus},
					{name:"skuCode",index:"sku.code",width:90,resizable:true},
					{name:"barCode",index:"sku.bar_code",width:120,resizable:true},
					{name:"skuName",index:"sku.name",width:170,resizable:true},
					{name:"quantity",index:"l.quantity",width:50,resizable:true},
					{name:"completeQuantity",index:"completeQuantity",width:50,resizable:true},
					{name:"pgIndex",index:"sta.pg_index",width:50,resizable:true,hidden:false},
					{name:"finish",index:"finish",width:50,resizable:true,hidden:true},
					{name:"btnPrint",index:"btnPrint",width:70,resizable:true}
				  ],
		caption: "待分拣商品明细列表",
	   	sortname: 'id',
	    multiselect: false,
		sortorder: "desc",
		width:"auto",
		height:"auto",
		rowNum:-1,
		viewrecords: true,
	   	rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		loadComplete:function(){
			loxia.initContext($j("#tbl-detail-list"));
			var postData = {};
			$j("#tbl-detail-list tr:gt(0)>td[aria-describedby$='barCode']").each(function (i,tag){
				postData["skuBarcodes[" + i + "]"] = $j(tag).html();
			});
			barcodeList = loxia.syncXhr($j("body").attr("contextpath") + "/findskuBarcodeList.json",postData);
		},
		gridComplete: function(){
			var btn1 = '<div><button type="button" style="width:70px;" class="confirm" name="btnPrint" loxiaType="button" onclick="btnprint(this,1);">'+"打印"+'</button></div>';
			var ids = $j("#tbl-detail-list").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				$j("#tbl-detail-list").jqGrid('setRowData',ids[i],{"btnPrint":btn1});
			}
			loxia.initContext($j(this));
			//清理表格
			initTable();
		}
	});
	jumbo.bindTableExportBtn("tbl-detail-list",{"intStatus":"whSTAStatus"},baseUrl + "/findStaLineByPickingId.json");
	
	
	//已分拣列表
	$j("#tbl-detail-list2").jqGrid({
		datatype: "json",
		colNames: ["ID","作业单ID","作业单号","状态","SKU编码","SKU条码","商品名称","数量","已核对数量","箱号","操作"],
		colModel: [{name: "id", index: "sku.id", hidden: true},
		           {name:"staId", index:"sta.id", width:120,resizable:true,hidden: true},
		           {name:"staCode", index:"sta.code", width:120,resizable:true},
					{name:"intStatus", index:"sta.status" ,width:60,resizable:true},
					{name:"skuCode",index:"sku.code",width:90,resizable:true},
					{name:"barCode",index:"sku.bar_code",width:120,resizable:true},
					{name:"skuName",index:"sku.name",width:170,resizable:true},
					{name:"quantity",index:"l.quantity",width:50,resizable:true},
					{name:"completeQuantity",index:"completeQuantity",width:50,resizable:true},
					{name:"pgIndex",index:"sta.pg_index",width:50,resizable:true,hidden:false},
					{name:"btnPrint",index:"btnPrint",width:70,resizable:true}
				  ],
		caption: "已分拣商品明细列表",
	   	sortname: 'id',
	    multiselect: false,
		sortorder: "desc",
		width:"auto",
		height:"auto",
		rowNum:-1,
		viewrecords: true,
	   	rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var btn1 = '<div><button type="button" style="width:70px;" class="confirm" name="btnPrint" loxiaType="button" onclick="btnprint(this,2);">'+"打印"+'</button></div>';
			var ids = $j("#tbl-detail-list2").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				$j("#tbl-detail-list2").jqGrid('setRowData',ids[i],{"btnPrint":btn1});
			}
			loxia.initContext($j(this));
		}
	});
	
	$j("#barCode").keydown(function(evt){
		var flag = false;
		flag = checkedIsComplete();
		if (flag){
			$j("#pl_check_dialog").dialog("open");
			$j("#showinfo").html("批次[ " + $j("#plCode").html() + " ]核对完成");
			$j("#inputBarCode").addClass("hidden");
			$j("#doCheck0").removeClass("hidden");
			$j("#checkValue").removeClass("hidden");
			$j("#checkValue").focus();
			return ;
		}
		if(evt.keyCode == 9){
			evt.preventDefault();
		}
		var rs = false;
		var value = $j.trim($j(this).val());
		if(evt.keyCode === 13){
			evt.preventDefault();
			rs = checkedBillCount(value);
			if(!rs){
				playMusic($j("body").attr("contextpath") +"/recording/barCodeError.wav");
				loxia.tip(this,i18("BARCODE_NOT_EXISTS"));//条形码不存在
				showDialog(i18("BARCODE_NOT_EXISTS"),"#showinfo");
			}else {
				playMusic($j("body").attr("contextpath") +"/recording/"+$j("#staPgindex").val()+".wav");
				showDialog("箱号是 ：" + $j("#staPgindex").val(),"#showinfo");
			}
			loxia.tip(this);
			
			flag = checkedIsComplete();
//			if (flag){
//				$j("#showinfo").html("批次[ " + $j("#plCode").html() + " ]核对完成");
//				$j("#inputBarCode").addClass("hidden");
//				$j("#doCheck0").removeClass("hidden");
//				$j("#checkValue").removeClass("hidden");
//				$j("#checkValue").focus();
//			}
			addSta(); //作业单分拣状态区分
			$j("#barCode").val("");
		}
	});
	
	// input
	$j("#barCode0").keydown(function(event){
		var flag = false;
		flag = checkedIsComplete();
		if (flag){
			$j("#pl_check_dialog").dialog("open");
			$j("#showinfo").html("批次[ " + $j("#plCode").html() + " ]核对完成");
			$j("#inputBarCode").addClass("hidden");
			$j("#doCheck0").removeClass("hidden");
			$j("#checkValue").removeClass("hidden");
			$j("#checkValue").focus();
			return ;
		}
		var value = $j.trim($j("#barCode0").val());
		var rs = false;
		if(event.keyCode == 13){
			if(value != "") {
				rs = checkedBillCount(value);
				if(!rs){
					playMusic($j("body").attr("contextpath") +"/recording/barCodeError.wav");
					loxia.tip(this,i18("BARCODE_NOT_EXISTS"));//条形码不存在
					$j("#showinfo").html(i18("BARCODE_NOT_EXISTS"));
				}else {
					playMusic($j("body").attr("contextpath") +"/recording/"+$j("#staPgindex").val()+".wav");
					$j("#showinfo").html("箱号是 ：" + $j("#staPgindex").val());
				}
			}else if(value == "" ) {
				$j("#showinfo").html("请输入商品条码.");
			}
			addSta(); //作业单分拣状态区分
			$j("#barCode0").val("");
			loxia.tip(this);
			//$j("#barCode0").focus();
		}
		flag = checkedIsComplete();
//		if (flag){
//			$j("#showinfo").html("批次[ " + $j("#plCode").html() + " ]核对完成");
//			$j("#inputBarCode").addClass("hidden");
//			$j("#doCheck0").removeClass("hidden");
//			$j("#checkValue").removeClass("hidden");
//			$j("#checkValue").focus();
//		}
	});
	
	// button 核对完成
	$j("#doCheck0").click(function(){
		var postData = {
				"barCode":$j("#plCode").html(),
				"force":true
		}; 
		var flag=loxia.syncXhr($j("body").attr("contextpath") + "/pda/moveCollectionBox.do",postData);
		var postData2 = {
				"pickId":$j("#plid").val()
				
		}; 
		var flag2=loxia.syncXhr($j("body").attr("contextpath") + "/auto/resetBoxStatus.do",postData2);
		checkCompleteoperate();
	});
	// input ok
	$j("#checkValue").keydown(function(event){
			var value = $j("#checkValue").val();
			if(event.keyCode == 13){
				if (value == BARCODE_CONFIRM){
					var postData = {
							"barCode":$j("#plCode").html(),
							"force":true
					}; 
					var flag=loxia.syncXhr($j("body").attr("contextpath") + "/pda/moveCollectionBox.do",postData);
					var postData2 = {
							"pickId":$j("#plid").val()
							
					}; 
					var flag2=loxia.syncXhr($j("body").attr("contextpath") + "/auto/resetBoxStatus.do",postData2);
					checkCompleteoperate();
				}else if(value != "" ) {
					$j("#showinfo").html("输入的确认条码不正确，请重新输入.");
				}else if(value == "" ) {
					$j("#showinfo").html("请输入确认条码.");
				}
				$j("#checkValue").val("");
			}
	});
	
	$j("#pickinglistCodeQuery").keydown(function(event){
		if(event.keyCode == 13){
			var postData = {};
			var code = $j.trim($j("#pickinglistCodeQuery").val());
			if(code != null && code != ""){
				postData["cmd.code"]= code;
				result = loxia.syncXhr($j("body").attr("contextpath") + "/checkPinkingListByCode.json",postData);
				if(result.result=='success'){
					var datas ={};
					datas["plCmd.code"]=code;
					var rs = loxia.syncXhr($j("body").attr("contextpath") + "/json/checkPickingisOver.json",datas);
					if(rs && rs.result=='success'){
						// do nothing
					}else{
						jumbo.showMsg("批次号"+code+"未拣货完成，不能直接操作！ 请至[拣货批次开始]页面操作");
						return;
					}
					var data = result.data;
					$j("#picking-list").addClass("hidden");
					$j("#detail").removeClass("hidden");
					$j("#barCode").focus();
					$j("#plCode").html(data.code);
					$j("#plCreateTime").html(data.createTime);
					$j("#plCreateUser").html(data.crtUserName);
					$j("#plBillCount").html(data.planBillCount);
					$j("#tbl-detail-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/findStaLineByPickingId.json"),
							postData:{"pickinglistId":data.id}}).trigger("reloadGrid",[{page:1}]);
				}else{
					loxia.tip($j("#pickinglistCodeQuery"),i18("指定的配货批不正确，请重新输入"));
					$j("#pickinglistCodeQuery").val("");
				}
			}else{
				loxia.tip($j("#pickinglistCodeQuery"),i18("请输入配货批"));
			}
		}
	});

	$j("#search").click(function(){
		var postData = {};
		postData["cmd.code"]= $j.trim($j("#pickinglistCode").val());
		postData["cmd.createTime1"]= $j.trim($j("#createTimeStart").val());
		postData["cmd.executedTime1"]= $j.trim($j("#createTimeEnd").val());
		$j("#tbl-dispatch-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/pickingListForPickOut.json"),
			postData:postData}).trigger("reloadGrid",[]);
	});
	$j("#reset").click(function(){
		$j("#queryTable input").val("");
	});
	$j("#back").click(function(){
		checkCompleteoperate();
		$j("#tbl-div").addClass("hidden");
		$j("#tbl-detail-list2").clearGridData(false);
	});
});
// finish 
function checkCompleteoperate(){
	$j("#barCode0").val(""); 
	$j("#barCode").val("");
	$j("#pl_check_dialog").dialog("close"); 
	$j("#detail").addClass("hidden");
	$j("#picking-list").removeClass("hidden");
	$j("#pickinglistCodeQuery").val("");
	$j("#pickinglistCodeQuery").focus();
	$j("#doCheck0").addClass("hidden");
	$j("#checkValue").addClass("hidden");
	$j("#checkValue").val("");
}
//
function checkedBillCount(value){
	var rs=false;
	if(!value || value.length==0)return false;
	var data = $j("#tbl-detail-list").jqGrid('getRowData');
	var d = null;
	var item = null, planNum = null, completeNum = null;
	for(var t in data){
		item = data[t];
		if(item.barCode == value){
			d = $j("#tbl-detail-list").jqGrid('getRowData',item.id);
			planNum = parseInt(d.quantity);
			completeNum = parseInt(d.completeQuantity);
			if (d.finish != 'y'){
				d.completeQuantity = completeNum + 1;
				if(completeNum + 1 == planNum){
					d.finish = 'y';
				}
				$j("#tbl-detail-list").jqGrid('setRowData',item.id,d);
				$j("#staPgindex").val(d.pgIndex);
				rs = true;
				//break;
				return rs;
			} else {
				continue;
			}
		}
	}
	for(var t in data){
		item = data[t];
		if(item.barCode == '00' + value){
			d = $j("#tbl-detail-list").jqGrid('getRowData',item.id);
			planNum = parseInt(d.quantity);
			completeNum = parseInt(d.completeQuantity);
			if (d.finish != 'y'){
				d.completeQuantity = completeNum + 1;
				if(completeNum + 1 == planNum){
					d.finish = 'y';
				}
				$j("#tbl-detail-list").jqGrid('setRowData',item.id,d);
				$j("#staPgindex").val(d.pgIndex);
				rs = true;
				//break;
				return rs;
			} else {
				continue;
			}
		}
	}
	for(var t in data){
		item = data[t];
		var bc = item.barCode;
		for(var v in barcodeList[bc]){
			if(barcodeList[bc][v] == value || barcodeList[bc][v] == ('00'+value)){
				d = $j("#tbl-detail-list").jqGrid('getRowData',item.id);
				planNum = parseInt(d.quantity);
				completeNum = parseInt(d.completeQuantity);
				if (d.finish != 'y'){
					d.completeQuantity = completeNum + 1;
					if(completeNum + 1 == planNum){
						d.finish = 'y';
					}
					$j("#tbl-detail-list").jqGrid('setRowData',item.id,d);
					$j("#staPgindex").val(d.pgIndex);
					rs = true;
					return rs;
				} else {
					continue;
				}
			}
		}
	}
	return rs;
} 
// true  - 完成；  false-未完成
function checkedIsComplete(){
	var datax = $j("#tbl-detail-list").jqGrid('getRowData');
	var flag = true;
	$j.each(datax,function(i, item) {
		var n1= parseInt(item.quantity);
		var cq = item.completeQuantity;
		if(cq==''||cq==null){
			 cq='0';
		}
		var n2= parseInt(cq);
		if(n2<n1){
			flag = false;
		}
	});	
	return flag;
} 
//弹出
function showDialog(text0,showid){
	if (showid != null) $j(showid).html(text0);
	$j("#barCode0").val("");
	$j("#inputBarCode").removeClass("hidden");
	$j("#pl_check_dialog").dialog("open");
	$j("#barCode0").focus();
}
// info 
function showInfo(text0,showid){
	if (showid != null) $j(showid).html(text0);	
	$j("#barCode0").val("");
	$j("#pl_check_dialog").dialog("open");
	$j("#barCode0").focus();
} 

//播放提示音
function playMusic(url){
    var audio = document.createElement('audio');
    var source = document.createElement('source');   
    source.type = "audio/mpeg";
    source.type = "audio/mpeg";
    source.src = url;   
    source.autoplay = "autoplay";
    source.controls = "controls";
    audio.appendChild(source); 
    audio.play();
 }

//打印装箱清单
function btnprint(tag,type){
	var id = $j(tag).parents("tr").attr("id");
	var staId = "";
	if (type == 1){
		staId = $j("#tbl-detail-list").getCell(id,"staId");
	}else{
		staId = $j("#tbl-detail-list2").getCell(id,"staId");
	}
	var pkId = $j("#plid").val();
	loxia.lockPage();
	jumbo.showMsg(i18("TRUNKPACKINGLISTINFO"));
	var url = $j("body").attr("contextpath") + "/printtrunkpackinglistmode1Out.json?plCmd.id=" + pkId + "&plCmd.staId=" + staId + "&isPostPrint=true";
	printBZ(loxia.encodeUrl(url),false);				
	loxia.unlockPage();
}

function addSta(){
	var ids = $j("#tbl-detail-list").jqGrid('getDataIDs');
	var value = $j.trim($j("#barCode").val());
	if(value == ""){
		value = $j.trim($j("#barCode0").val());
	}
	var obj = {};
	for(var i=0;i<ids.length;i++){
		var staCode = $j("#tbl-detail-list").getCell(ids[i],"staCode");
		var intStatus = $j("#tbl-detail-list").getCell(ids[i],"intStatus");
		if (intStatus == 0){
			intStatus = "取消";
		}else if(intStatus == 1){
			intStatus = "等待配货";
		}else if(intStatus == 2){
			intStatus = "配货中";
		}else if(intStatus == 8){
			intStatus = "部分完成";
		}else if(intStatus == 10){
			intStatus = "全部完成";
		}else if(intStatus == 19){
			intStatus = "快递无法送达";
		}else if(intStatus == 20){
			intStatus = "配货失败";
		}
		var staId = $j("#tbl-detail-list").getCell(ids[i],"staId");
		var skuCode = $j("#tbl-detail-list").getCell(ids[i],"skuCode");
		var skuName = $j("#tbl-detail-list").getCell(ids[i],"skuName");
		var pgIndex = $j("#tbl-detail-list").getCell(ids[i],"pgIndex");
		var barCode = $j("#tbl-detail-list").getCell(ids[i],"barCode");
		var quantity = $j("#tbl-detail-list").getCell(ids[i],"quantity");
		var completeQuantity = $j("#tbl-detail-list").getCell(ids[i],"completeQuantity");
		if (value == barCode && quantity == completeQuantity){
			$j("#tbl-detail-list").find("tr[id="+ids[i]+"]").remove();
		    obj = {
					id:ids[i],
					staId:staId,
					staCode:staCode,
					intStatus:intStatus,
					skuCode:skuCode,
					skuName:skuName,
					pgIndex:pgIndex,
					barCode:barCode,
					quantity:quantity,
					completeQuantity:completeQuantity
			};
		    var ids2 = $j("#tbl-detail-list2").jqGrid('getDataIDs');
			var $firstTrRole = $j("#tbl-detail-list2").find("tr").eq(1).attr("role");  
			var rowid = $firstTrRole == "row" ? Math.max.apply(Math,ids2):0;  
			var newrowid = parseInt(rowid)+1;
			$j("#tbl-detail-list2").jqGrid("addRowData", newrowid, obj, "last");//将新添加的行插入到最后一列
			$j("#tbl-div").removeClass("hidden");
		}
	}
}



function initTable(){
	var ids = $j("#tbl-detail-list").jqGrid('getDataIDs');
	var obj = {};
	for(var i=0;i<ids.length;i++){
		var staCode = $j("#tbl-detail-list").getCell(ids[i],"staCode");
		var intStatus = $j("#tbl-detail-list").getCell(ids[i],"intStatus");
		if (intStatus == 0){
			intStatus = "取消";
		}else if(intStatus == 1){
			intStatus = "等待配货";
		}else if(intStatus == 2){
			intStatus = "配货中";
		}else if(intStatus == 8){
			intStatus = "部分完成";
		}else if(intStatus == 10){
			intStatus = "全部完成";
		}else if(intStatus == 19){
			intStatus = "快递无法送达";
		}else if(intStatus == 20){
			intStatus = "配货失败";
		}
		var staId = $j("#tbl-detail-list").getCell(ids[i],"staId");
		var skuCode = $j("#tbl-detail-list").getCell(ids[i],"skuCode");
		var skuName = $j("#tbl-detail-list").getCell(ids[i],"skuName");
		var pgIndex = $j("#tbl-detail-list").getCell(ids[i],"pgIndex");
		var barCode = $j("#tbl-detail-list").getCell(ids[i],"barCode");
		var quantity = $j("#tbl-detail-list").getCell(ids[i],"quantity");
		var completeQuantity = $j("#tbl-detail-list").getCell(ids[i],"completeQuantity");
		if (quantity == completeQuantity){
			$j("#tbl-detail-list").find("tr[id="+ids[i]+"]").remove();
		    obj = {
					id:ids[i],
					staId:staId,
					staCode:staCode,
					intStatus:intStatus,
					skuCode:skuCode,
					skuName:skuName,
					pgIndex:pgIndex,
					barCode:barCode,
					quantity:quantity,
					completeQuantity:completeQuantity
			};
		    var ids2 = $j("#tbl-detail-list2").jqGrid('getDataIDs');
			var $firstTrRole = $j("#tbl-detail-list2").find("tr").eq(1).attr("role");  
			var rowid = $firstTrRole == "row" ? Math.max.apply(Math,ids2):0;  
			var newrowid = parseInt(rowid)+1;
			$j("#tbl-detail-list2").jqGrid("addRowData", newrowid, obj, "last");//将新添加的行插入到最后一列
			$j("#tbl-div").removeClass("hidden");
		}
	}
}