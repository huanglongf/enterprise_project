var INCLUDE_SEL_ID = "";
var INCLUDE_SEL_VAL_CODE = "";
function initShopQuery(selId,valCode){
	INCLUDE_SEL_ID = selId;
	INCLUDE_SEL_VAL_CODE = valCode;
	if(valCode == 'innerShopCode'){
		INCLUDE_SEL_VAL_CODE = 'code'
	}
}

$j.extend(loxia.regional['zh-CN'],{
	DISTRIBUTION_LIST_SELECT : "请选择配货清单",
	
	// colNames: ["ID",,],
	// colNames: ["ID","","","","","","","",""],
	CODE : "作业单号",
	INTSTATUS : "状态",
	REFSLIPCODE : "相关单据号",
	INTTYPE : "作业类型名称",
	SHOPID : "淘宝店铺ID",
	LPCODE : "物流服务商",
	CREATETIME : "创建时间",
	STVTOTAL : "计划执行总件数",
	INT_LIST : "作业单列表",
	
	BATCHNO : "配货批次号",
	CREATETIME : "创建时间",
	PLAN_BILL_COUNT : "计划单数",
	PLAN_SKU_QTY : "计划件数",
	INTSTATUS : "状态",
	NEW_LIST : "新建配货清单列表",
	
	BATCHNO : "配货批次号",
	CREATETIME : "创建时间",
	PLANBILLCOUNT : "计划配货单据数", 
	PLAYSKUQTY : "计划配货商品件数",
	INTSTATUS : "状态",
	WAITING_LIST : "查询结果列表",
	WORKER : "生成批次操作员",
	
	DISPATCH_FAILED : "由于您的配货单中所有作业单都失败，所以您的配货单被取消",
	ONLY_SUPPORT_SF : "O2O订单暂时只支持顺丰，不可转其它物流，请使用顺丰手写面单",
	DIAPATCH_CANCEL : "配货清单已取消",
	OPERATING : "配货清单打印中，请等待...",
	TRUNKPACKINGLISTINFO:"装箱清单打印中，请等待...",
	DELIVERYINFO:"物流面单打印中，请等待...",
	OPETION : "操作",
	PRINGT : "打印",
	CREATE_DISABLE_PRINT : "当前订单状态为‘新建状态’不能打印",
	CANEL_DISABLE_PRINT : "当前订单状态为‘取消已处理’不能打印",
	FAILED_DISABLE_PRINT : "当前订单状态为‘配货失败’不能打印"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}


var isEmsOlOrder = false;
var skusIdAndQty = "";
$j(document).ready(function (){
	
	
	$j("#tbl_sta_query_dialog").jqGrid({//明细行
		//url:$j("body").attr("contextpath") + "/queryStas.json?orderId="+rs["pack"]["orderId"],
		datatype: "json",
		colNames: ["指令类型","指令号"],
		colModel: [ 
//		           {name: "code", width: 100,hidden: true},
//		           {name: "strType", index: "strType", width: 120, resizable: true,formatter:'select',editoptions:strTypeFormater},
 				   {name: "strType", index: "strType", width: 120, resizable: true},
		           {name: "code", index: "name", width: 250,  resizable: true}
		           ],
//		caption: "店铺列表",
//		rowNum: 10,
//		rowList:[10,20,40],
	   	sortname: 'id',
//	    pager: '#tbl_shop_query_dialog_pager',
	    multiselect: false,
		sortorder: "desc", 
		height:'auto',
		viewrecords: true,
//   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	
	$j("#btnSearch").click(function(){//查询店铺
		var type=document.getElementById("centerType").value; 
		if(type==''){
			 jumbo.showMsg("请中心类型");
		}else if(type=='1'){//部门
			$j("#departQueryDialog").dialog("open");
		}else{//店铺
			$j("#shopQueryDialog").dialog("open");
		}
	});
	
	$j("#btnStaQueryClose").click(function(){//关闭指令明细
		$j("#showSta").hide();
		$j("#showSta").dialog("close");
	});
	
	$j("#showSta").dialog({title: "指令明细", modal:true,closeOnEscape:false, autoOpen: false, width: 760});//弹窗属性设置
	$j("#departmentshop").append("<option value=''>请选择</option>");
	var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/getChooseOptionByCode.do",{"categoryCode":"businessType"});
	$j("#businessType").append("<option value=''>请选择</option>");
	for(var i=0;i<result.length;i++){
		$j("#businessType").append("<option value="+result[i].optionKey+">"+result[i].optionValue+"</option>");
	}
	var businessTypeFormater=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"businessType"});
	var isLandTransFormater=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"isLandTrans"});
	var costCenterTypeFormater=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"costCenterType"});
//	var costCenterTypeFormater={"value":"1:部门;2:店铺;"};
	
	$j("#tbl-waittingList").jqGrid({
		url:$j("body").attr("contextpath") + "/getTransPackagePage.json",
		datatype: "json",
//		valuesprmNames:{status:1},
		colNames: ["ID","transportatorCode","orderStatus","相关单据号","面单号","物流服务商","包裹重量(KG)","立方信息(M3)","保价金额","是否陆运","业务类型","成本中心类型","部门/店铺","创建时间","操作人","寄件人姓名","寄件人联系方式","寄件地址","收货人姓名","收货人联系方式","省","市","区","收货地址","备注","重新称重","补打电子面单","查看指令明细"],
		colModel: [
				   {name: "id", index: "id", hidden: true},
//		           {name: "outputCount", index: "outputCount", hidden: true},orderStatus
				   {name: "orderStatus", index: "orderStatus",hidden: true},
				   {name: "transportatorCode", index: "transportatorCode",hidden: true},
				   {name: "slipCode", index: "slipCode",width:120,resizable:true},
		           {name: "transNo", index: "transNo",width:120,resizable:true},    
		           {name: "tranName", index: "tranName", width: 150, resizable: true},
		           {name: "packageWeight", index: "packageWeight", width: 70, resizable: true},
		           {name: "volume", index: "volume", width: 70, resizable: true},
	               {name:"insuranceAmount",index:"insuranceAmount",width:50,resizable:true},
		           {name:"isnotLandTrans", index: "isnotLandTrans",width: 100, resizable: true,formatter:'select',editoptions:isLandTransFormater},
	               {name:"businessType",index:"businessType",width:80,resizable:true,formatter:'select',editoptions:businessTypeFormater},
		           {name:"costCenterType",index:"costCenterType",width:80,resizable:true,formatter:'select',editoptions:costCenterTypeFormater},
	               {name:"costCenterDetail",index:"costCenterDetail",width:80,resizable:true},
	               {name:"createTime",index:"createTime",width:80,resizable:true},
	               {name:"userName",index:"userName",width:80},
	               {name:"sender",index:"sender",width:100},
	               {name:"senderTel",index:"senderTel",width:50,resizable:true},
		           {name:"senderAddress", index: "senderAddress",width: 100, resizable: true},
	               {name:"receiver",index:"receiver",width:80,resizable:true},
		           {name:"receiverTel",index:"receiverTel",width:80,resizable:true},
		           {name:"receiverProvince",index:"receiverProvince",width:80,resizable:true},
		           {name:"receiverCity",index:"receiverCity",width:80,resizable:true},
		           {name:"receiverArea",index:"receiverArea",width:80,resizable:true},
	               {name:"receiverAddress",index:"receiverAddress",width:80,resizable:true},
	               {name:"remark",index:"remark",width:80,resizable:true},
//	               {name: "idForBtn", width: 80,resizable:true,sortable: false, formatter:"buttonFmatter", formatoptions:{"buttons":{label:"重新称重", onclick:"reWeight(this);"}}},
//	               {name: "idForBtn", width: 80,resizable:true,sortable: false, formatter:"buttonFmatter", formatoptions:{"buttons":{label:"补打电子面单", onclick:"printSingleDelivery1(this,event);"}}},
	               {name:"btn2",width:100,resizable:true},
	               {name:"print",width:100,resizable:true},
	               {name:"btn3",width:100,resizable:true}
//	               {name: "idForBtn", width: 120,resizable:true,sortable: false, formatter:"buttonFmatter", formatoptions:{"buttons":{label:"查看", onclick:"checkSta(this)"}}}
		           ],
		caption: i18("WAITING_LIST"),//
		pager:"#pager_query",
		rowNum: 10,
		rowList:[10,20,40],
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: false,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc",
//		hidegrid:false,
		jsonReader: { repeatitems : false, id: "0" },
		loadComplete: function(){
			var btn1 = '<div><button type="button" style="width:100px;" class="confirm" id="print" name="btnDel" loxiaType="button" onclick="surfaceprint(this);">'+"打印电子面单"+'</button></div>';
			var btn2 = '<div><button type="button" style="width:100px;" class="confirm" id="print" name="btnDel" loxiaType="button" onclick="reWeight(this);">'+"重新称重"+'</button></div>';
			var btn3 = '<div><button type="button" style="width:100px;" class="confirm" id="print" name="btnDel" loxiaType="button" onclick="checkSta(this);">'+"查看"+'</button></div>';
		
//			 var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/findwarehousebaseinfo.do");
			var result = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/findwarehousebaseinfo.json"));
//			 alert(result["warehouse"].isSfOlOrder);//顺丰
//			 alert(result["warehouse"].isZtoOlOrder);//中通
//			 alert(result["warehouse"].isEmsOlOrder);//EMS
//			 alert(result["warehouse"].isOlSto);//申通
//			 alert(result["warehouse"].isYtoOlOrder);//圆通
//			 alert(result["warehouse"].isTtkOlOrder);//天天
//			 alert(result["warehouse"].isWxOlOrder);//万幸
			
			var ids = $j("#tbl-waittingList").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var  transportatorCode= $j("#tbl-waittingList").getCell(ids[i],"transportatorCode");
				var  orderStatus= $j("#tbl-waittingList").getCell(ids[i],"orderStatus");
				if(orderStatus =='1' ){
					$j("#tbl-waittingList").jqGrid('setRowData',ids[i],{"btn2":btn2});
				}
				$j("#tbl-waittingList").jqGrid('setRowData',ids[i],{"btn3":btn3});
				//判断物流商是否可以打印电子面单
				if(transportatorCode=='EMS' && result["warehouse"].isEmsOlOrder==true ){
					$j("#tbl-waittingList").jqGrid('setRowData',ids[i],{"print":btn1});
				}else
				if((transportatorCode=='SF'|| transportatorCode=='SFDSTH') && result["warehouse"].isSfOlOrder==true ){
					$j("#tbl-waittingList").jqGrid('setRowData',ids[i],{"print":btn1});
				}else 
				if(transportatorCode=='STO' && result["warehouse"].isOlSto==true ){
					$j("#tbl-waittingList").jqGrid('setRowData',ids[i],{"print":btn1});
				}
				else
				if(transportatorCode=='TTKDEX' && result["warehouse"].isTtkOlOrder==true ){
					$j("#tbl-waittingList").jqGrid('setRowData',ids[i],{"print":btn1});
				}else
				if(transportatorCode=='WX' && result["warehouse"].isWxOlOrder==true ){
					$j("#tbl-waittingList").jqGrid('setRowData',ids[i],{"print":btn1});
				}else
				if(transportatorCode=='YTO' && result["warehouse"].isYtoOlOrder==true ){
					$j("#tbl-waittingList").jqGrid('setRowData',ids[i],{"print":btn1});
				} else
				if(transportatorCode=='ZTO' && result["warehouse"].isZtoOlOrder==true ){
					$j("#tbl-waittingList").jqGrid('setRowData',ids[i],{"print":btn1});
				}
				
//				if(transportatorCode=='EMS'  || transportatorCode=='SF' || transportatorCode=='STO' || transportatorCode=='TTKDEX'|| transportatorCode=='WX' || transportatorCode=='YTO' || transportatorCode=='ZTO'){
//					$j("#tbl-waittingList").jqGrid('setRowData',ids[i],{"print":btn1});
//				}
			}
			loxia.initContext($j(this));
		}
	});

//	jumbo.bindTableExportBtn("tbl-waittingList",{"isnotLandTrans":"isLandTrans","businessType":"businessType","costCenterType":"costCenterType"});
	bindTableExportBtn("tbl-waittingList",{"isnotLandTrans":"isLandTrans","businessType":"businessType","costCenterType":"costCenterType"});

	//查询按钮功能 根据作业单号和状态查询
	$j("#btn-query").click(function(){
		 var transNo=document.getElementById("transNo").value;
		 var centerType=document.getElementById("centerType").value;
		 var selectLpCode=document.getElementById("selectLpCode").value;
		 var businessType=document.getElementById("businessType").value;
		 var departmentshop=document.getElementById("departmentshop").value;
		 var startTime=document.getElementById("startTime").value;
		 var endTime=document.getElementById("endTime").value;
		 var userName=document.getElementById("userName").value;
		 
		 var receiverProvince=document.getElementById("receiverProvince").value;
		 var receiverCity=document.getElementById("receiverCity").value;
		 var receiverArea=document.getElementById("receiverArea").value;
		 var staType=document.getElementById("staType").value;
		 var staCode2=document.getElementById("staCode2").value;
		 var slipCode=document.getElementById("slipCode").value;
		 
		 var postData={};
		 postData["packageCommand.transNo"]=transNo;
		 postData["packageCommand.costCenterType"]=centerType;
		 postData["packageCommand.transportatorCode"]=selectLpCode;
		 postData["packageCommand.businessType"]=businessType;
		 postData["packageCommand.costCenterDetail"]=departmentshop;
		 postData["packageCommand.startTime"]=startTime;
		 postData["packageCommand.endTime"]=endTime;
		 postData["packageCommand.userName"]=userName;
		 
		 postData["packageCommand.receiverProvince"]=receiverProvince;//收货省
		 postData["packageCommand.receiverCity"]=receiverCity;//收货市
		 postData["packageCommand.receiverArea"]=receiverArea;//收货区
		 postData["packageCommand.staType"]=staType;//指令类型
		 postData["packageCommand.staCode2"]=staCode2;//指令号
		 postData["packageCommand.slipCode"]=slipCode;//相关单据号
		 
		 var url=window.$j("body").attr("contextpath")+"/json/getTransPackagePage.do";

		 $j("#tbl-waittingList").jqGrid('setGridParam',{
			url:url,
			postData:postData,
			page:1
		}).trigger("reloadGrid");
//		jumbo.bindTableExportBtn("tbl-waittingList",{"isnotLandTrans":"isLandTrans","businessType":"businessType","costCenterType":"costCenterType"},url,postData);
	    bindTableExportBtn("tbl-waittingList",{"isnotLandTrans":"isLandTrans","businessType":"businessType","costCenterType":"costCenterType"},url,postData);

	});
	
	//重置
	$j("#reset").click(function(){
		$j("#div1 input,#div1 select").val("");
	});
	
	//初始化时进入光标定位文本框，按enter执行相应的工作
	$j("#skuId").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			$j("#packageWeight").focus();//光标重启重
//			var skuId=$j("#skuId").val();
//			alert($j("#skuId").val());
//			var rs=loxia.syncXhr($j("body").attr("contextpath") + "/json/getOneTransPackage2.do",{"skuId":skuId});
//			var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getOneTransPackage2.do",{"skuId":skuId});
//			if(rs["brand"]=="yes"){
//				$j("#packageWeight").focus();//光标重启重
//			}else{
//				jumbo.showMsg("耗材已存在");
//				$j("#skuId").val('');
//			}
		}
	});
	
});


//中心类型
function centerType(){
	var type=document.getElementById("centerType").value; 
	if(type==1){//部门
		 document.getElementById("departmentshop").options.length = 0;  
		 $j("#departmentshop").append("<option value=''>请选择</option>");
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/getChooseOptionByCode.do",{"categoryCode":"departmentType"});
		for(var i=0;i<result.length;i++){
			$j("#departmentshop").append("<option value="+result[i].optionKey+">"+result[i].optionValue+"</option>");
		}
	}else if(type==2){//店铺
		document.getElementById("departmentshop").options.length = 0;  
		$j("#departmentshop").append("<option value=''>请选择</option>");
		initShopQuery("departmentshop","innerShopCode");
		jumbo.loadShopList("departmentshop");
	}
}
//打印电子面单
function surfaceprint(obj){
	var id = $j(obj).parents("tr").attr("id");
	var data=$j("#tbl-waittingList").jqGrid("getRowData",id);
//	var transNo=data['transNo'];
	loxia.lockPage();
	jumbo.showMsg(i18("DELIVERYINFO"));
	var url = $j("body").attr("contextpath") + "/printSingleOrderDetailOutMode12.json?id=" + data['id'];
//	alert(url);
	printBZ(loxia.encodeUrl(url),true);
	loxia.unlockPage();
}
 
//重新称重
function reWeight(obj){
	$j("#shopQueryDialog2 input,#shopQueryDialog2 select").val("");
	var id = $j(obj).parents("tr").attr("id");
	var data=$j("#tbl-waittingList").jqGrid("getRowData",id);
	var transNo=data['transNo'];
	var id=data['id'];
	$j("#reId").val(id);
	$j("#reTransNo").val(transNo);
	$j("#shopQueryDialog2").show();
	$j("#shopQueryDialog2").dialog("open");
	$j("#skuId").focus();//光标耗材
}

//查看指令明细
function checkSta(obj){
	$j("#showSta input,#showSta select").val("");
	var id = $j(obj).parents("tr").attr("id");
	var data=$j("#tbl-waittingList").jqGrid("getRowData",id);
	var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getOneTransPackage.do",{"id":data['id']});
//	var strTypeFormater=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	 $j("#staCode").val(rs["pack"]["code"]);
	 $j("#staBusinessType").val(rs["pack"]["businessType"]);
	 $j("#orderId").val(rs["pack"]["orderId"]);
	 $j("#showSta").dialog("open");
	 $j("#showSta").show();
	 var url=$j("body").attr("contextpath") + "/queryStas.json?orderId="+rs["pack"]["orderId"];
		$j("#tbl_sta_query_dialog").jqGrid('setGridParam',{
			url:url}).trigger("reloadGrid");
}
/**
 * 
 */
function _constructHidden (name,value){
	name = name||"",value = value||"";
	return "<input type=hidden name=\"" + name + "\" value=\"" + value + "\" />";
}

/**
 * 导出
 */
function bindTableExportBtn(table,optionCols,url,postData,postCheck){
	var $t = loxia.isString(table)?$j("#" + table):$j(table),
		url = url || $t.jqGrid("getGridParam","url"),
		$c = $t.parents("div.ui-jqgrid");
	var html = [];
	html.push('<a class="ui-jqgrid-titlebar-export HeaderButton"' +  
			' href="javascript:;" title="导出" role="link" style="float:left;">' + 
			'<span class="ui-icon ui-icon-comment"><!-- --></span></a>');
	html.push("<form action="+url+" type='post'>");
	html.push(_constructHidden("isExcel","true"));
	html.push(_constructHidden("page","1"));
	html.push(_constructHidden("rows","-1"));
	html.push(_constructHidden("sidx",""));
	html.push(_constructHidden("sord",""));
	html.push(_constructHidden("caption",$t.jqGrid("getGridParam","caption")));
	var cm=$t.jqGrid("getGridParam","colModel"),cn=$t.jqGrid("getGridParam","colNames"),index=-1;
	var filter={"id":"id","rn":"","subgrid":"","cb":"","receiver":"receiver","receiverTel":"receiverTel","receiverProvince":"receiverProvince","receiverCity":"receiverCity","receiverArea":"receiverArea","receiverAddress":"receiverAddress",
			"btn2":"btn2","print":"print","btn3":"btn3"};
	$j.each(cm,function(i,e){
		if(e.hidden==false&&!(e.name in filter)){
			index++;
			html.push(jumbo._constructHidden("colModel["+index+"]",e.name));
			html.push(jumbo._constructHidden("colNames["+index+"]",cn[i]));
		}
	});
	if(optionCols){
		for(var k in optionCols){
			html.push(_constructHidden("columnOption." + k,optionCols[k]));
		}
	}
	if(postData){
		for(var k in postData){
			html.push(_constructHidden(k,postData[k]));
		}
	}
	html.push("</form>");
	if($c.find(".ui-jqgrid-titlebar-export").length>0){
		$c.find(".ui-jqgrid-titlebar-export").remove();
		$c.find("form").remove();
	}
	$c.find(".ui-jqgrid-titlebar .ui-jqgrid-titlebar-close").after(html.join(""));
	$c.find(".ui-jqgrid-titlebar-export").hover(function(){
		$j(this).addClass("ui-state-hover");
	},function(){
		$j(this).removeClass("ui-state-hover");
	});
	$c.find(".ui-jqgrid-titlebar-export").click(function(){
		var num;
		var text = $c.find("div.ui-jqgrid-pager>div>table>tbody>tr>td:eq(2)>div").text();
		if(text == "" || text == "无数据显示"){
			alert("没有要导出的数据!");
			return;
		}
		if(text==""){
			var num1 = $c.find("div.ui-jqgrid-pager>div>table>tbody>tr>td:eq(1)>table>tbody>tr>td:eq(3)>span").text().replace(" ","");
			var num2 = $c.find("div.ui-jqgrid-pager>div>table>tbody>tr>td:eq(1)>table>tbody>tr>td:eq(7)>select").val();
			num = parseInt(num1)*parseInt(num2);
		}else{
			var d = text.indexOf("共");
			if(d>0){
				var num=text.substring(d+2,text.length-2);
				num=num.replace(new RegExp(" ", 'g'), "");
			}
		}
		if(num>20000){
			alert("系统设定最多导出20000条数据!");
			return;
		}
		if(typeof(postCheck)==="function"){
			if(postCheck()== false){
				return false;
			}
		}
		$c.find(".ui-jqgrid-titlebar input[name='sidx']").val($t.jqGrid("getGridParam","sortname"));
		$c.find(".ui-jqgrid-titlebar input[name='sord']").val($t.jqGrid("getGridParam","sortorder"));
		$c.find(".ui-jqgrid-titlebar form")[0].submit();
		return false;
	});
}
 
