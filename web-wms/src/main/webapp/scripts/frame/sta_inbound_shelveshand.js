//定义简单Map  
function getMap() {//初始化map_,给map_对象增加方法，使map_像Map    
         var map_ = new Object();    
         map_.put = function(key, value) {    
             map_[key+'_'] = value;    
         };    
         map_.get = function(key) {    
             return map_[key+'_'];    
         };    
         map_.remove = function(key) {    
             delete map_[key+'_'];    
         };    
         map_.keyset = function() {    
             var ret = "";    
             for(var p in map_) {    
                 if(typeof p == 'string' && p.substring(p.length-1) == "_") {    
                     ret += ",";    
                     ret += p.substring(0,p.length-1);    
                 }    
             }    
             if(ret == "") {    
                 return ret.split(",");    
             } else {    
                 return ret.substring(1).split(",");    
             }    
         };    
         return map_;    
}
var $j = jQuery.noConflict();
var id=null;
//商品明细
var skuInfo;
//保存上架SN
var snMap;
var extBarcode = null;
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}
function showMsg(msg){
	jumbo.showMsg(msg);
}
var onKeydownType = 0;
$j(document).ready(function (){
	
	// 扫描相关单据号进详细页
	$j("#refSlipCode").keydown(function(evt) {
		if (evt.keyCode === 13) {
			var slipCode = $j("#refSlipCode").val().trim();
			if (slipCode == "") {
				jumbo.showMsg("输入单据号码不能为空!");
				return;
			}
			showdetails(slipCode);
		}
	});
	var baseUrl = $j("body").attr("contextpath");
	initShopQuery("companyshop","innerShopCode");
	jumbo.loadShopList("companyshop");
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	//确认条码回车事件
	$j("#confimcode").keydown(function(evt){
		if(evt.keyCode === 13){
			if($j("#confimcode").val().trim()=="OK"||$j("#confimcode").val().trim()=="ok"){
				confimPutOn();
			}else{
				jumbo.showMsg("确认条码错误");
			}
		}
	});
	//推荐库位商品条码的回车事件
	$j("#recombarCode").keydown(function(evt){
		var recomBarCode=$j("#recombarCode").val().trim();
		if(evt.keyCode === 13){
			$j("#recomlocation").addClass("hidden");
			$j("#locationspan").html("");
			if($j("#recombarCode").val().trim()==""){
				jumbo.showMsg("请先扫描推荐库位商品条码");
				$j("#recombarCode").focus();
			}else{
				if(skuInfo.length===0){
					jumbo.showMsg("数据错误");
					$j("#recombarCode").focus();
				}else{
					for(var i=0;i<skuInfo.length;i++){
						if(skuInfo[i].barCode==recomBarCode){
							$j("#recomlocation").removeClass("hidden");
							if(skuInfo[i].locationCode==""){
								jumbo.showMsg("没有帮您找到合适的库位信息");
							}else{
								$j("#locationspan").html(skuInfo[i].locationCode);
							}
						}else{
							if(i===(skuInfo.length-1)){
								jumbo.showMsg("没有找到与该推荐库位商品条码匹配的信息");
							}
						}
					}
				}
			}
		}
	});
	//库位条码输入框  按下回车键触发的事件
	$j("#locationCode").keydown(function(evt){
		if(evt.keyCode === 13){
			var locationCode = $j("#locationCode").val().trim();
			if(locationCode == ""){
				jumbo.showMsg("请先扫描库位");
				return ;
			}else{
				var obj=loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findlocationbycodesta.json?locationCode=" + locationCode);
				var ishidden =$j('#invStatusName').attr('class')=="hidden";
				if(obj.result == "success" && ishidden == true){
					$j("#skuBarCode").focus();
				}else if(obj.result == "success" && ishidden == false){
					$j("#invStatus").focus();
					$j("#invStatus").keydown(function(evt){
							if(evt.keyCode === 13){
								var slt=$j("#invStatus").val();
								if(slt=="-1"){
									jumbo.showMsg("请先选择库存状态");
									$j("#invStatus").focus();
								}else{
									$j("#addnum").focus();
								}
							}
					});
				}else{
					jumbo.showMsg("该库位不存在或不可用");
					$j("#locationCode").focus();
				}
			}
		}
	});
	var invs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findinvstatusbyshop.json");//findInvStatusByOuId
	for(var i in invs){
		$j("<option value='"+invs[i].id+"'>"+invs[i].name+"</option>").appendTo($j("#invStatus"));
	}
	
	loxia.init({debug: true, region: 'zh-CN'});
	var baseUrl = $j("body").attr("contextpath");
	//initShopQuery("companyshop","innerShopCode");
//	jumbo.loadShopList("companyshop");

	
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});

	$j("#tbl_sta_list").jqGrid({
		url:baseUrl + "/findInboundShelvesSta.json",
		datatype: "json",
		colNames: ["ID","STVID","作业单号","相关单据号","辅助相关单据号","状态","作业类型","staType","店铺","CHANNELCODE","核对人","核对数量","创建时间","备注"],
		colModel: [
					{name: "id", index: "id", hidden: true},
					{name: "stvId", index: "stvId", hidden: true},
					{name: "code", index: "code",formatter:"linkFmatter", formatoptions:{onclick:"showdetail"}, width: 120, resizable: true,sortable:false},
					{name: "refSlipCode", index: "refSlipCode", width: 120, resizable: true},
					{name: "slipCode1", index: "slipCode1", width: 120, resizable: true},
					{name: "intStaStatus", index: "intStaStatus", width: 80, resizable: true,formatter:'select',editoptions:staStatus},
					{name: "intStaType", index: "intStaType", width: 120, resizable: true,formatter:'select',editoptions:staType},
					{name: "intStaType", index: "staType",hidden: true},
					{name: "channelName", index: "channelName", width: 120, resizable: true},
					{name: "owner", index: "owner", width: 120, resizable: true,hidden:true},
					{name: "affirmor", index: "affirmor", width: 120, resizable: true},
					{name: "receiptNumber", index: "receiptNumber", hidden: true},
					{name: "createTime", index: "createTime", hidden: true},
					{name: "memo", index: "memo", hidden: true}	
	               ],
		caption: "已核对收货单列表",
		rowNum:10,
	   	sortname: 'id',
		pager:"#pager",
	    multiselect: false,
		viewrecords: true,
   		rownumbers:true,
		sortorder: "desc",
		width:800,
		height:"auto",
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#tbl_sta_line_list").jqGrid({
		datatype: "json",
		colNames: ["ID","SKUID","商品条码","货号","扩展属性","商品名称","计划量","库存状态","库存状态的Value值","库位","生产日期","过期时间","当前执行量","是否是SN商品","操作"],
		colModel: [
					{name: "id", index: "id",hidden:true,sortable:false},//序号（ID）
					{name: "skuId", index: "skuId",hidden:true,sortable:false},//SKUID
					{name: "barCode", index: "sku.BAR_CODE", width: 150, resizable: true,sortable:false},//商品条码
					{name: "supplierCode", index: "sku.SUPPLIER_CODE", width: 120, resizable: true,sortable:false},//货号
					{name: "keyProperties", index: "sku.KEY_PROPERTIES", width: 150, resizable: true,sortable:false},//关键属性
					{name: "skuName", index: "sku.name", width: 150, resizable: true,sortable:false},//商品名称
					{name: "quantity", index: "quantity", width:120, resizable:true, sortable:false},//计划量
					{name: "invStatusName", index: "invStatusName", width:120, resizable:true, sortable:false},//库存状态
					{name: "intInvstatus", index: "intInvstatus",hidden:true, width:120, resizable:true, sortable:false},//库存状态Value
					{name: "locationCode", index: "locationCode", width:120, resizable:true, sortable:false},//库位
					{name: "strPoductionDate", index: "strPoductionDate", width:120, resizable:true, sortable:false},//生产日期商品
					{name: "strExpireDate", index: "strExpireDate", width:120, resizable:true, sortable:false},//过期时间商品
					{name: "addedQty", index: "addedQty", width:120, resizable:true, sortable:false},//当前执行量
					{name: "isSnSku", index: "isSnSku",hidden:true, width:120, resizable:true, sortable:false},//是否是SN商品
					{name: "reset", width: 100,resizable:true, formatter:"buttonFmatter", formatoptions:{"buttons":{label:"删除", onclick:"deleteLine(this,event);"}}}//操作
	               ],
		caption: "确认上架明细",
		rowNum:10,
	    multiselect: false,
	    gridComplete : function(){
			loxia.initContext($j(this));
		},
		width:850,
		height:"auto",
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#tbl_sn_list").jqGrid({
		datatype: "json",
		colNames: ["序号","snMapKey","SN号","处理"],
		colModel: [
					{name: "id", index: "id", width: 120, resizable: true,sortable:false},
					{name: "snMapKey", index: "snMapKey", hidden: true,sortable:false},
					{name: "SN", index: "SN", width: 150, resizable: true,sortable:false},
					{name: "reset", width: 120,resizable:true, formatter:"buttonFmatter", formatoptions:{"buttons":{label:"删除", onclick:"deletesnline(this,event);"}}}//操作
	               ],
		caption: "SN处理",
	    multiselect: false,
	    gridComplete : function(){
			loxia.initContext($j(this));
		},
		width:390,
		height:"auto",
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	//商品条码输入框  按下回车键触发的事件
	$j("#skuBarCode").keydown(function(evt){
		if(evt.keyCode === 13){
			onAddSku();
			onKeydownType = 0;
		} else {
			onKeydownType = 1;
		}
	});
	
	$j("#snbtn").click(function(evt){
		if(onKeydownType != 0){
			onAddSku();
		}
		if(validata("SN")){
			snbtnClick();
		}
		onKeydownType = 0;
	});
	
	$j("#snnumber").keydown(function(evt){
		if(evt.keyCode === 13){
			if(onKeydownType != 0){
				onAddSku();
			}
			if(validata("SN")){
				snbtnClick();
			}
			onKeydownType = 0;
		}
	});
	
	
	$j("#pdInput").keydown(function(evt){
		if(evt.keyCode === 13){
			onAddSku();
			onKeydownType = 0;
		} else {
			onKeydownType = 2;
		}
	});
	
	$j("#edInput").keydown(function(evt){
		if(evt.keyCode === 13){
			onAddSku();
			onKeydownType = 0;
		} else {
			onKeydownType = 3;
		}
	});
	
	$j("#search").click(function(){
		queryStaList();
	});
	
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
	});
	
	$j("#back").click(function(){
		$j("#divDetial").addClass("hidden");
		$j("#divHead").removeClass("hidden");
	});
	
	$j("#showPdaLog").click(function(){
		$j("#dialogPdaLog").dialog("open");
	});

	$j("#close").click(function(){
		$j("#dialogPdaLog").dialog("close");
	});
});

function onAddSku(){
	if(validata("skuBarCode")){
		displayLogic();
	}
}


//显示上架单明细数据
function showdetail(obj){
	initAll();
	$j("#locationCode").val("");
	$j("#skuBarCode").val("");
	jQuery("#tbl_sta_line_list").jqGrid("clearGridData");
	var tr = $j(obj).parents("tr[id]");
	id=tr.attr("id");
	$j("#sta_id").val(id);
	var pl=$j("#tbl_sta_list").jqGrid("getRowData",id);
	$j("#sta_code").text(pl["code"]);
	$j("#sta_refSlipCode").text(pl["refSlipCode"]);
	$j("#sta_slipCode1").text(pl["slipCode1"]);
	$j("#sta_type").text(tr.find("td[aria-describedby$='intStaType']:first").text());
	$j("#sta_status").text(tr.find("td[aria-describedby$='intStaStatus']").text());
	$j("#sta_createTime").text(pl["createTime"]);
	$j("#sta_memo").text(pl["memo"]);
	$j("#stv_id").val(pl["stvId"]);
	$j("#locationCode").focus();
	if(pl["intStaType"] == "41"){
		$j("#invStatusName").removeClass("hidden");
		$j("#invStatusName").focus();
	} else {
		$j("#invStatusName").addClass("hidden");
		$j("#locationCode").focus();
	}
	skuInfo = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findInboundStvLineHand.json?sta.stvId=" + pl["stvId"]);
	snMap = getMap();
	$j("#divHead").addClass("hidden");
	$j("#divDetial").removeClass("hidden");
}




function showdetails(obj){
	var postData = {};
	postData["silpCode"]=obj;
	var rs = loxia.syncXhr($j("body").attr("contextpath")+ "/json/findStaBySlipCode.json",postData);
	if (rs && rs.result && rs.result == "success") {
		staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/optionlist.do",{"categoryCode":"whSTAType"});
		staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/optionlist.do",{"categoryCode":"whSTAStatus"});
		$j("#sta_id").val(rs.pl["id"]);
	    $j("#sta_code").text(rs.pl["code"]);
		$j("#sta_refSlipCode").text(rs.pl["refSlipCode"]);
		$j("#sta_slipCode1").text(rs.pl["slipCode1"]);
		$j("#sta_createTime").text(rs.pl["createTime"]);
		for(var idx in staType){
			if(staType[idx].optionKey == rs.pl["intType"]){
				$j("#sta_type").text(staType[idx].optionValue);
				break;
			}
		}
		for(var idx in staStatus){
			if(staStatus[idx].optionKey == rs.pl["intStatus"]){
				$j("#sta_status").text(staStatus[idx].optionValue);
				break;
			}
		}
		$j("#sta_memo").text(rs.pl["memo"]);
		showReceiptNumber1();
		$j("#divHead").addClass("hidden");
		$j("#divDetial").removeClass("hidden");
		$j("#locationCode").focus();
	}else if(rs.result=="none"){
		jumbo.showMsg("未找到匹配的信息!");
	}else if(rs.result=="error"){
		jumbo.showMsg(data.message);		
	};
	
};


//判断商品数量
function validateSkuQty(barCode,qty){
	var sku = getSkuInfo(barCode);
	//判断上架量不能超过计划量
	for(i in skuInfo){
		if(skuInfo[i].barCode == barCode){
			if(skuInfo[i].quantity === 0){
				return false;
			}
		}
	}

	if(sku == "undefined" || sku ==''){
		return false;
	}
	var skuId = sku.skuId;
	if(skuId == "" || qty < 1){
		return false;
	}
	var skuQty = 0;

	for(i in skuInfo){
		if(skuInfo[i].skuId == skuId){
			skuQty = parseInt(skuQty) + parseInt(skuInfo[i].quantity);
		}
	}
	var datalist = $j("#tbl_sta_line_list" ).getRowData();
	for(var i=0;i<datalist.length;i++){
		if(datalist[i].skuId==skuId){
			qty = parseInt(qty) + parseInt(datalist[i].addedQty);
		}
   	}
	if(qty > skuQty){
		return false;
	}
	return true;
}

//扫描条码后的验证
function validata(validateType){
	var barCode = $j("#skuBarCode").val().trim();
	var inputnum = $j("#addnum").val().trim();
	var locationCode=$j("#locationCode").val().trim();
	var isReturnOrder = $j('#invStatusName').attr('class')=="hidden";
	var re = /^[1-9]+[0-9]*]*$/;
	if(locationCode == ""){
		jumbo.showMsg("请填写库位");
		$j("#locationCode").focus();
		return false;
	}
	if(barCode == ""){
		jumbo.showMsg("请先扫描正确的商品条码");
		$j("#skuBarCode").focus();
		return false;
	}
	if(getSkuInfo(barCode) == ""){
		jumbo.showMsg("商品条码不在计划上架商品条码内");
		$j("#skuBarCode").focus();
		return false;
	}
	if(isReturnOrder==false){
		if($j('#invStatus').find("option:selected").text()=="请选择"){
			jumbo.showMsg("请选择库存状态");
			$j('#invStatus').focus();
			return false;
		}
	}
	if(isShelfManagement(barCode)){
		var pdVal = $j('#pdInput').val().trim();
		var edVal = $j('#edInput').val().trim();
		if(pdVal == "" && edVal == ""){
			jumbo.showMsg("保质期商品必须填写生成日期或者过期时间!");
			return false;
		}
		if(pdVal != "" && (pdVal.length != 8 || !re.test(pdVal))){
			jumbo.showMsg("生成日期格式不正确!");
			$j('#pdInput').focus();
			return false;
		}
		if(edVal != "" && (edVal.length != 8 || !re.test(edVal))){
			jumbo.showMsg("过期时间格式不正确!");
			$j('#edInput').focus();
			return false;
		}
	}
	if(isSnSku(barCode)!=true){
		if(inputnum == "" || inputnum.length == 0){
			jumbo.showMsg("请填写录入数量");
			$j("#addnum").focus();
			return false;
		} 
	    if (!re.test(inputnum)){
	    	jumbo.showMsg("数量不正确，必须大于0的整数!");
			$j("#addnum").focus();
			return false;
		}
	}
	if(validateType == "SN"){
		var snnumber=$j("#snnumber").val().trim();
		if(snnumber == ""){
			jumbo.showMsg("请先填写SN号码");
			$j("#snnumber").focus();
			return false;
		}
		var datalist = $j("#tbl_sn_list" ).getRowData();
		if(datalist.length>0){
			for(var i=0;i<datalist.length;i++){
				if(snnumber==datalist[i].SN){
					jumbo.showMsg("该SN号码已经存在，请重新填写");
					$j("#snnumber").focus();
					return false;
				}
			}
		}
	}
	return true;
}


//显示逻辑处理
function displayLogic(){
	var barCode = $j("#skuBarCode").val().trim();
	var number = $j("#addnum").val().trim();
	var locationCode=$j("#locationCode").val().trim();
	var statusName=$j('#invStatus').find("option:selected").text();
	var invStatusValue=$j('#invStatus').val();
	var pductionDate=$j('#pdInput').val().trim();
	var expireDate=$j('#edInput').val().trim();
	var datalist = $j("#tbl_sta_line_list" ).getRowData();
	if(isSnSku(barCode) == true){
		//显示SN 商品基本信息 包括SN列表
		showSN(locationCode,statusName,barCode,pductionDate,expireDate);
	} else {
		$j("#divSN").addClass("hidden");
		$j("#addNumBtn").removeAttr("disabled"); 
		$j("#addnum").removeAttr("disabled");
		if(datalist.length > 0){
			updateSkuNumber(locationCode,statusName,invStatusValue,barCode,pductionDate,expireDate,number);
		} else {
			addSku(locationCode,statusName,invStatusValue,barCode,pductionDate,expireDate,number);
		}
		$j("#skuBarCode").val("");
		$j("#number").val("1");
		$j("#locationCode").val("");
		$j("#locationCode").focus();
		$j("#pdInput").val("");
		$j("#edInput").val("");
	}
}

//查询sta列表
function queryStaList(){
	var url = $j("body").attr("contextpath") + "/findInboundShelvesSta.json";
	$j("#tbl_sta_list").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_sta_list",{"intStaType":"whSTAType","intStaStatus":"whSTAStatus"},
		url,loxia._ajaxFormToObj("queryForm"));
}

//是否推荐库位选中事件
function isRecom(obj){
	if($j('#recommend').checked="1")
	{
		if($j('#divrecom').attr('class')==("hidden")){
			$j("#divrecom").removeClass("hidden");
			$j("#recombarCode").focus();
		}else{
			$j("#divrecom").addClass("hidden");
			$J("#locationCode").focus();
		}
	}
};

//条码扫描获取商品信息
function getSkuInfo(barCode){
	var temp = "";
	for(i in skuInfo){
		if(skuInfo[i].barCode == barCode){
			temp = skuInfo[i];
			if(skuInfo[i].quantity > 0){
				return skuInfo[i];
			}
		}
	}
	if(temp != ""){
		return skuInfo[i];
	}
	return "";
}
//判断是否可以继续添加当前执行量
function checkEqual(quantity,addedQty){
	if(skuInfo[i].quantity===0){
		jumbo.showMsg("该商品的计划量为0，不需要上架操作");
		$j("#skuBarCode").focus();
		return ;
	}
	if(addedQty===0||addedQty<skuInfo[i].quantity){
		return skuInfo[i];
	}
	if(skuInfo[i].quantity==addedQty){
		jumbo.showMsg("当前执行量已经等于计划量，可以选择确认上架操作了");
		$j("#confim").focus();
		return ;
	}
}
//库位条码输入框 光标离开事件
function locOnBlur(){
	var locationCode = $j("#locationCode").val().trim();
	if(locationCode == ""){
		return ;
	}else{
		var obj=loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findlocationbycodesta.json?locationCode=" + locationCode);
		var ishidden =$j('#invStatusName').attr('class')=="hidden";
		if(obj.result == "success" && ishidden == true){
			$j("#skuBarCode").focus();
		}else if(obj.result == "success" && ishidden == false){
			$j("#invStatus").focus();
			$j("#invStatus").keydown(function(evt){
					if(evt.keyCode === 13){
						var slt=$j("#invStatus").val();
						if(slt=="-1"){
							jumbo.showMsg("请先选择库存状态");
							$j("#invStatus").focus();
						}else{
							$j("#skuBarCode").focus();
						}
					}
			});
		}else{
			jumbo.showMsg("该库位不存在或不可用");
			$j("#locationCode").focus();
		}
	}
}

//查询上架列表中的数据
function findStaLineList(locationCode,statusName,barCode,poductionDate,expireDate){
	var datalist = $j("#tbl_sta_line_list" ).getRowData();
	var isReturnOrder = $j('#invStatusName').attr('class')=="hidden";
	var isSm = isShelfManagement(barCode);//是否保质期商品
	for(var i=0;i<datalist.length;i++){
		if(datalist[i].barCode==barCode && datalist[i].locationCode == locationCode && 
				(isSm ? (datalist[i].strPoductionDate == poductionDate && datalist[i].strExpireDate == expireDate) : true)){
			if((isReturnOrder == true) || (isReturnOrder == false && datalist[i].invStatusName == statusName)){
				return new Number(datalist[i].id);
			}
		}
   	}
	return "";
}

//更新数量
function updateSkuNumber(locationCode,statusName,invStatusValue,barCode,pductionDate,expireDate,number){
	var id = findStaLineList(locationCode,statusName,barCode,pductionDate,expireDate);
	if(validateSkuQty(barCode,number) == false){
		jumbo.showMsg("商品[" + barCode +"]当前上架量不可以超过计划量！");
		return ;
	}
	if(id != ""){
		var datas = $j("#tbl_sta_line_list" ).getRowData(id);
		var initnum=new Number(datas.addedQty);
		var addnum=new Number(number);
		$j("#tbl_sta_line_list").jqGrid('setRowData', id, {"addedQty":initnum+addnum});
	} else {
		//在现有列表里面不存在的商品，再做添加
		addSku(locationCode,statusName,invStatusValue,barCode,pductionDate,expireDate,number);
	}
}

//添加上架商品
function addSku(locationCode,statusName,invStatusValue,barCode,pductionDate,expireDate,number){
	var plid = getSkuInfo(barCode);
	if(plid== null || plid ==''){
		jumbo.showMsg("找不到对应的商品信息");
		$j("#skuBarCode").focus();
	}else{
		if(validateSkuQty(barCode,number) == false){
			jumbo.showMsg("商品[" + barCode +"]当前上架量不可以超过计划量！");
			return ;
		}
		var isReturnOrder = $j('#invStatusName').attr('class')=="hidden";
		var rowdata = plid;
		//数量
		rowdata.addedQty = number;
		//库位
		rowdata.locationCode = locationCode;
		if(isReturnOrder == true){
			rowdata.invStatusName="";
			rowdata.invStatusValue = "";
		}else{
			rowdata.invStatusValue = invStatusValue;
			rowdata.invStatusName=statusName;
		}
		if(plid.isShelfManagement=="1"){
			rowdata.strPoductionDate = pductionDate;
			rowdata.strExpireDate = expireDate;
		} else {
			rowdata.strPoductionDate="";
			rowdata.strExpireDate="";
		}
		var len = getTableTrMaxId("tbl_sta_line_list");
		if(len == ""){
			len = 1;
		}
		rowdata.id = len;
		$j("#tbl_sta_line_list").jqGrid('addRowData', len, rowdata);
	}
}

function getTableTrMaxId(tableId){
	if(tableId == "") return ""; 
	var datalist = $j("#"+tableId).getRowData();
	if(datalist.length < 1){
		return "";
	} else {
		return (new Number(datalist[datalist.length - 1].id)) + 1;
	}
}

/**************************************SN 处理*****************************************/

//显示divSN 填充商品信息
function showSN(locationCode,statusName,barCode,pductionDate,expireDate){
	var rowdata =getSkuInfo(barCode);
	if(rowdata == ""){
		return ;
	}
	if(rowdata.isSnSku=="1"){
		$j("#divSN").removeClass("hidden");
		$j("#addnum").val(1);
		$j("#sku_barCode").html(rowdata["barCode"]);
		$j("#sku_SUPPLIER_CODE").text(rowdata["supplierCode"]);
		$j("#sku_KEY_PROPERTIES").text(rowdata["keyProperties"]);
		$j("#sku_name").text(rowdata["skuName"]);
		//如果是SN商品   设置录入数量按钮和文本框为不可用状态
		$j("#addNumBtn").attr("disabled", "disabled"); 
		$j("#addnum").attr("disabled","disabled");
		showSNList(findStaLineList(locationCode,statusName,barCode,pductionDate,expireDate));
		$j("#snnumber").focus();
	} else {
		$j("#divSN").addClass("hidden");
		$j("#addNumBtn").removeAttr("disabled"); 
		$j("#addnum").removeAttr("disabled");
	}
}


//遍历显示SN
function showSNList(mapKey){
	$j("#tbl_sn_list" ).clearGridData();
	var values = snMap.get(mapKey);
	if(values != undefined){
		for(var i = 0; i < values.length; i++){
			var rows = {};
			rows["id"] = (i+1);
			rows["snMapKey"] = mapKey;
			rows["SN"] = values[i];
			$j("#tbl_sn_list").jqGrid('addRowData', (i+1), rows);
		}
	}
}

//tbl_sn_list里的删除按钮事件
function deletesnline(tag,event){
	var id = $j(tag).parents("tr").attr("id");
	var snDatas = $j("#tbl_sn_list").jqGrid('getRowData',id);
	var mapKey = snDatas["snMapKey"];
	var skuDatas = $j("#tbl_sta_line_list" ).getRowData(mapKey);
	//每次删除  在相应的里将当前执行量减1
	var addedQty = new Number(skuDatas["addedQty"]) - 1;
	if(addedQty > 0){
		$j("#tbl_sta_line_list").jqGrid('setRowData', mapKey, {"addedQty":addedQty});
	} else {
		removeSnSku(mapKey);
	}
	$j("#tbl_sn_list tr[id='" + id + "']").remove();
}

//删除SN商品
function removeSnSku(key){
	$j("#tbl_sta_line_list tr[id='" + key + "']").remove();
	snMap.remove(key);
}
//删除tbl_sn_list里的对应信息
function removeSnTblSku(key){
	//sn列表是否存在值
	//不存在直接逃过
	//存在， 判断该当前sn列表里面的snMapkey值是不是等于 key
	// == 晴空snlist
	// != 跳过
	if(getTblSNListMapKey() != key){
		return ;
	}else{
		$j("#tbl_sn_list" ).clearGridData();
	}
	snMap.remove(key);
}
//删除按钮的方法
function deleteLine(tag,event){
	var id = $j(tag).parents("tr").attr("id");
	$j("#tbl_sta_line_list tr[id='" + id + "']").remove();
	removeSnTblSku(id);
	} 

//判断tbl_sn_list里的SN号不可以重复
function checkSnRepeat(){
	
}


//SN号码回车事件
function snbtnClick(){
	var barCode = $j("#skuBarCode").val().trim();
	var locationCode=$j("#locationCode").val().trim();
	var statusName=$j('#invStatus').find("option:selected").text();
	var statusNameValue=$j('#invStatus').find("option:selected").val();
	var pductionDate=$j('#pdInput').val().trim();
	var expireDate=$j('#edInput').val().trim();
	var datalist = $j("#tbl_sta_line_list" ).getRowData();
	//$j("#locationCode").val("");
	//往tbl_sta_line_list里填充数据
	if(datalist.length > 0){
		updateSkuNumber(locationCode,statusName,statusNameValue,barCode,pductionDate,expireDate,1);
	} else {
		addSku(locationCode,statusName,statusNameValue,barCode,pductionDate,expireDate,1);
	}
	//往tbl_sn_list里填充数据
	addTblSn($j("#snnumber").val(),findStaLineList(locationCode,statusName,barCode,pductionDate,expireDate));
	$j("#snnumber").val("");
	$j("#snnumber").focus();
}

//向map添加数据
function addMapValue(key,value){
	var values = snMap.get(key);
	if(values != undefined){
		values[values.length] = value;
	}else {
		values = new Array();
		values[0] = value;
	}
	snMap.put(key,values);
}

//往tbl_sn_list里填充数据
function addTblSn(sn,snMapKey){
	if(getTblSNListMapKey() != snMapKey){
		//如果不等于 清空 tbl_sn_list
		$j("#tbl_sn_list" ).clearGridData();
	}
	
	var len=getTableTrMaxId("tbl_sn_list");
	if(len == ""){
		len=1;
	}
	var rowdata = {};
	rowdata["id"]=len;
	rowdata["snMapKey"]=snMapKey;
	rowdata["SN"]=sn;
	$j("#tbl_sn_list").jqGrid('addRowData', len, rowdata);
	addMapValue(snMapKey,sn);
	loxia.initContext($j("#tbl_sn_list"));
}

//获取当前tbl_sn_list 的 snMapKey
function getTblSNListMapKey(){
	var datalist = $j("#tbl_sn_list" ).getRowData();
	if(datalist.length>0){
		for(var i=0;i<datalist.length;i++){
			if(i===0){
				return datalist[i].snMapKey;
			}
		}
	}
	return "";
}
//判断是否是SN商品
function isSnSku(barCode){
	var rowdata = getSkuInfo(barCode);
	if(rowdata == ""){
		return false;
	}
	if(rowdata.isSnSku=="1" && rowdata.snType!="3"){
		return true;
	}else{
		return false;
	}
}

// 判断是否是保质期商品
function isShelfManagement(barCode){
	var rowdata = getSkuInfo(barCode);
	if(rowdata == ""){
		return false;
	}
	if(rowdata.isShelfManagement=="1"){
		return true;
	}else{
		return false;
	}
}

function showReceiptNumber1(){
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl_sta_line_list").jqGrid('setGridParam',{page:1,url:baseUrl + "/findInboundStvLineHand.json?sta.stvId=" + $j("#sta_id").val()}).trigger("reloadGrid");
}

//初始化所有控件
function initAll(){
	$j("#divSN").addClass("hidden");
	$j("#sku_barCode").html();
	$j("#sku_SUPPLIER_CODE").text();
	$j("#sku_KEY_PROPERTIES").text();
	$j("#sku_name").text();
	//如果是SN商品   设置录入数量按钮和文本框为不可用状态
	$j("#addNumBtn").attr("disabled", false); 
	$j("#addnum").attr("disabled",false);
}
//返回事件
function backClick(){
	$j("#divDetial").addClass("hidden");
	$j("#divHead").removeClass("hidden");
};
function confimPutOn(){
	var datalist = $j("#tbl_sta_line_list" ).getRowData();
	var snlist=$j("#tbl_sn_list" ).getRowData();
	if(datalist.length == 0){
		jumbo.showMsg("请录入上架商品！");
		return;
	}
	var sncode="";
	var postData = {};
	postData["sta.id"]= $j("#sta_id").val();
	var skuQty = getMap();
	var addedQty = getMap();
	for(i in skuInfo){
		skuQty.put((skuQty.get(skuInfo[i].skuId) == 'undefined' ? 0 : + skuQty.get(skuInfo[i].skuId)) + parseInt(skuInfo[i].quantity));
	}
	for(var i=0,b;(b=snlist[i]);i++){
		if(i==0){
			sncode+=b.SN;//SN
		}else{
			sncode+=","+b.SN;
		}
	}
	for(var i=0,d;(d=datalist[i]);i++){
		var qty = d.addedQty;
		if(qty != ''){
			var intInvstatus = d.intInvstatus;
			var locationCode = d.locationCode;
			var staId = $j("#sta_id").val();
			var quantity = skuQty.get(d.skuId);
			if(quantity == 'undefined'){
				jumbo.showMsg("商品[" + d.barCode+"] 未在计划上架内！");
				return ;
			}
			var tempQty = (addedQty.get(d.skuId) == 'undefined' ? 0 : + addedQty.get(d.skuId))
			tempQty = parseInt(tempQty) + parseInt(qty);
			if(quantity < tempQty){
				jumbo.showMsg("商品[" + d.barCode+"]当前上架量不可以超过计划量！");
				return ;
			}
			addedQty.put(d.skuId,tempQty);
			postData["stvLineList[" + i + "].skuId"]=d.skuId;//商品
			postData["stvLineList[" + i + "].addedQty"]=qty;//当前执行量
			postData["stvLineList[" + i + "].intInvstatus"]=intInvstatus;//库存状态
			postData["stvLineList[" + i + "].locationCode"]=locationCode;//库位条码
			postData["stvLineList[" + i + "].stalineId"]=staId;//staId
			postData["stvLineList[" + i + "].strPoductionDate"]=d.strPoductionDate;
			postData["stvLineList[" + i + "].strExpireDate"]=d.strExpireDate;
			if(d.isSnSku == "1"){
				postData["stvLineList[" + i + "].sns"]=sncode;
			}else{
				postData["stvLineList[" + i + "].sns"]="";
			}
		}
   	}
	
	loxia.asyncXhrPost(window.$j("body").attr("contextpath")+"/inboundShelvesHand.json",
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
			}
	});
}


