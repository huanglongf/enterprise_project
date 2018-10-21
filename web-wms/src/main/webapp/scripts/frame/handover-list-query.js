$j.extend(loxia.regional['zh-CN'],{
	//OPERATING : "物流交接清单打印中，请稍候...",
	CANCEL_SUCCESS : "交货清单取消成功",
	DATA_ERROR : "操作数据异常",
	SURE_CANCEL : "您确定要作废？",
	DETAIL_CANCEL_SUCCESS : "交货清单明细取消成功",
	SUCCESS : "交接成功",
	FAILED : "交接失败",

	CODE : "交接清单号",
	HOINTSTATUS : "状态",
	CREATETIME : "创建时间",
	HANDOVERTIME : "交接时间",
	LPCODE : "物流商",
	PARTYBOPERATO : "物流交接人",
	PARTYAOPERATOR : "宝尊交接人",
	IDBTN : "操作",
	DETAIL_LIST : "交接清单列表",
	
	TRACKINGNO : "快递单号",
	REFSLIPCODE : "相关单据号",
	WEIGHT : "包裹重量",
	RECEIVER : "收货人",
	OUTBOUNDTIME : "扫描出库时间",
	LINEINTSTATUS : "状态",
	IDFORBTN : "操作",
	CANCEL : "作废",
	DETAIL : "明细",
	
	OPERATING : "保存成功，物流交接清单打印中，请稍候...",
	EXP_NAME: "物流服务商",
	TOTAL_PACKAGE_COUNT : "总订单",
	HANDEDOVER_QTY : "已交接",
	UNHANDOVER_QTY : "未交接",
	PACKAGE_LIST : "包裹清单" 
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

function removeCancelHoLineBtn(){
	$j("#tbl-detial tr[id]").each(function(idx,tag){
		var id = $j(tag).attr("id");
		var data=$j("#tbl-detial").jqGrid("getRowData",id)
		var hoId = $j("#divHandOverDeital").attr("handOverId");
		var hoData=$j("#tbl-hand-over-list").jqGrid("getRowData",hoId)
		if(data["lineIntStatus"] == '0' || hoData["hoIntStatus"] == '10'){
			$j(tag).find("button").remove();
		}
	});
}

//打印交接清单
function printHoInfo(id){
	 var url = $j("body").attr("contextpath") + "/json/printhandoverlist.do?hoListId=" + id;
	printBZ(loxia.encodeUrl(url),true);
}

// 作废交接清单
function canelHandOverList(id){
	var postData = {};
	postData["hoListId"] = id;
	loxia.asyncXhrPost(
		$j("body").attr("contextpath") + "/json/cancelhandoverlist.do",
		postData,
		{
			success:function(data){
					if(data){
						if(data.result=="success"){
							jumbo.showMsg(i18("CANCEL_SUCCESS"));//交货清单取消成功
							var postData1 = loxia._ajaxFormToObj("query_form");
							$j("#tbl-hand-over-list").jqGrid('setGridParam',{url: window.$j("body").attr("contextpath") + "/json/handoverlist.json",page:1,postData:postData1}).trigger("reloadGrid");
						}else if(data.result=="error"){
							jumbo.showMsg(data.message);
						}
					}
				},
			error:function(){jumbo.showMsg(i18("DATA_ERROR"));}//操作数据异常
		}
	);
}

//作废交接清单明细行
function cancelDetial(tag){
	if(!window.confirm(i18("SURE_CANCEL"))){//您确定要作废？
		return false;
	}
	var id = $j(tag).parents("tr").attr("id");
	var holid = $j("#handOverListid").val();
	var postData = {};
	postData["hoListLineId"] = id;
	loxia.asyncXhrPost(
			$j("body").attr("contextpath") + "/json/cancelhoListLine.do",
			postData,
			{
				success:function(data){
						if(data){
							if(data.result=="success"){
								if(data.flag==true){
									jumbo.showMsg(i18("DETAIL_CANCEL_SUCCESS"));//交货清单明细取消成功
									$j("#tbl-detial").jqGrid('setGridParam',{url:loxia.getTimeUrl(window.$j("body").attr("contextpath") + "/json/findlinebyholistid.json?hoListId="+holid)}).trigger("reloadGrid");
									
									var postData2 = {};
									postData2["hoListId"] = $j("#handOverListid").val();
									var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/findhandoverlistbyid.json",postData2);
									if(result && result.handoverlist){
										$j("#packageCount").html(result.handoverlist.packageCount);
										$j("#totalWeight").html(result.handoverlist.totalWeight);
									}
								}else{
									$j("#divHandOverDeital").addClass("hidden");
									$j("#allDelete").removeClass("hidden");
								}
							}else if(data.result=="error"){
								jumbo.showMsg(data.message);
							}
						}
					},
				error:function(){jumbo.showMsg(i18("DATA_ERROR"));} //操作数据异常
			}
	);
}

//交接
function handOver(id){
	var errors=loxia.validateForm("handOver_form");
	if(errors.length>0){
		jumbo.showMsg(errors);
		return false;
	}
	var postData = loxia._ajaxFormToObj("handOver_form");
	var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/handOver.json",postData);
	if(result && result.result == "success"){
		var ho = result.ho;
		$j("#partyAOperator").html(ho["partyAOperator"]);
		$j("#partyAMobile").html(ho["paytyAMobile"]);
		$j("#partyBOperator").html(ho["partyBOperator"]);
		$j("#partyBMobile").html(ho["paytyBMobile"]);
		$j("#paytyBPassPort").html(ho["paytyBPassPort"]);
		var postData = loxia._ajaxFormToObj("query_form");
		$j("#tbl-hand-over-list").jqGrid('setGridParam',{url: window.$j("body").attr("contextpath") + "/json/handoverlist.json",page:1,postData:postData}).trigger("reloadGrid");
		$j("#tbl-detial tr[id]").each(function(idx,tag){
			$j(tag).find("button").remove();
		});
		$j("#tbl_hand_over_list_info").removeClass("hidden");
		$j("#handOver_form").addClass("hidden");
		$j("#brnDetialHo").addClass("hidden");
		$j("#btnDetialSavePrint").addClass("hidden");
		$j("#btnDetialPrint").removeClass("hidden");
		jumbo.showMsg(i18("SUCCESS"));//交接成功
	}else if(result && result.result == "error"){
		jumbo.showMsg(result.message);
	}else{
		jumbo.showMsg(i18("FAILED"));//交接失败
	}
}


$j(document).ready(function (){
	
	var baseUrl = $j("body").attr("contextpath");
	
	$j("#tabs").tabs();
	
	$j("#tbl_handover_total").jqGrid({
		url : baseUrl + "/currenthandoverlisttotal.json",
		datatype: "json",
		//colNames: ["ID","物流服务商","总订单","已交接","未交接"],
		colNames: ["ID",i18("EXP_NAME"),i18("TOTAL_PACKAGE_COUNT"),i18("HANDEDOVER_QTY"),i18("UNHANDOVER_QTY")],
		colModel: [{name: "id", index: "id", hidden: true},
				   {name :"expName",index:"expName",width:200,resizable:true},
				   //{name :"packageCount",index:"packageCount",width:200,resizable:true,formatter:"number", summaryType:'sum', summaryTpl:'<b>TOTAL: {0}</b>'},
				   {name :"packageCount",index:"packageCount",width:200,resizable:true,summaryType:'sum', summaryTpl:'<b>TOTAL: {0}</b>'},
				   {name :"handoveredQty",index:"handoveredQty",width:200,resizable:true,summaryType:'sum', summaryTpl:'<b>{0}</b>'},
				   {name :"unHandoverQty",index:"unHandoverQty",width:200,resizable:true,summaryType:'sum', summaryTpl:'<b>{0}</b>'}
				],
		caption: i18("PACKAGE_LIST"), // 包裹清单
		rowNum:-1,
	   	sortname: 'expName',
	    multiselect: false,
		sortorder: 'asc',
		grouping:true,

		groupingView : {
   		groupField : [''],
   		groupSummary : [true],
   		groupColumnShow : [true],
   		groupText : ['<b>{0}</b>'],
   		groupCollapse : false,
		groupOrder: ['asc']
		},
		gridComplete : function(){
		$j("#tbl_handover_total .jqgroup").remove();
		},
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#flush").bind("click",function(){
		$j("#tbl_handover_total").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/currenthandoverlisttotal.json")}).trigger("reloadGrid");
	});
		
	
	var trasportName =loxia.syncXhr(baseUrl + "/json/getTrasportName.do");
	var hoStatus=loxia.syncXhr(baseUrl + "/json/formateroptions.do",{"categoryCode":"handoverListStatus"});
	var status=loxia.syncXhr(baseUrl + "/json/formateroptions.do",{"categoryCode":"handoverListLineStatus"});
	var result = loxia.syncXhrPost(baseUrl + "/getTransportator.json");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selTrans"));
	}

	$j("#reset").click(function(){
		$j("#query_form input,#query_form select").val("");
	});
	
	$j("#query").click(function(){
		var postData = loxia._ajaxFormToObj("query_form");
		$j("#tbl-hand-over-list").jqGrid('setGridParam',{url: window.$j("body").attr("contextpath") + "/json/handoverlist.json",page:1,postData:postData}).trigger("reloadGrid");
	});
	

	$j("#tbl-hand-over-list").jqGrid({
		url: "",
		datatype: "json",
		//colNames: ["ID","交接清单号","状态","创建时间","交接时间","物流商","物流交接人","宝尊交接人","操作"],
		colNames: ["ID",i18("CODE"),i18("HOINTSTATUS"),i18("CREATETIME"),i18("HANDOVERTIME"),i18("LPCODE"),i18("PARTYBOPERATO"),i18("PARTYAOPERATOR"),i18("IDBTN")],
		colModel: [{name: "id", index: "id", hidden: true},
					{name :"code",index:"code",formatter:"linkFmatter",formatoptions:{onclick:"showDetail"},width:100,resizable:true},
					{name:"hoIntStatus", index:"hoIntStatus" ,width:60,resizable:true,formatter:'select',editoptions:hoStatus},
					{name:"createTime", index:"createTime",width:100,resizable:true},
					{name:"handOverTime", index:"handOverTime", width:90, resizable:true},
					{name:"lpcode",index:"lpcode",width:90,resizable:true,formatter:'select',editoptions:trasportName},
					{name:"partyBOperator",index: "partyBOperator",width:120,resizable:true},
					{name:"partyAOperator",index:"partyAOperator",width:100,resizable:true},
					{name:"idBtn",width:160,resizable:true}],
		caption: i18("DETAIL_LIST"),//交接清单列表
		rowNum:-1,
	   	//rowList:[10,20,30],
	   	sortname: 'code',
		pager: '#pager',
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		rownumbers:true,
	    multiselect: false,
		sortorder: "desc",
		height:"auto",
		gridComplete: function(){
			var ids = $j("#tbl-hand-over-list").jqGrid('getDataIDs');
//			var btn1 = '<div name="divCancelBtn" style="float: left"><button type="button" style="width:70px;" class="confirm" name="btnCancel" loxiaType="button">作废</button></div>';
			var btn1='';
			var btn2 = '<div style="float: left"><button type="button" style="width:70px;" name="btnPrint" loxiaType="button">打印</button></div>';
			for(var i=0;i < ids.length;i++){
				var btn = btn1 + btn2;
				var data=$j("#tbl-hand-over-list").jqGrid("getRowData",ids[i]);
				if(data["hoIntStatus"] == '0' || data["hoIntStatus"] == '10'){
//					var btnNull = '<div style="width: 71px;float: left">&nbsp;</div>';
					var btnNull='';
					btn = btnNull + btn2;
				}
				$j("#tbl-hand-over-list").jqGrid('setRowData',ids[i],{"idBtn":btn});
			}
			loxia.initContext($j(this));
			$j("button[name='btnHandOver']").click(function(){
				var id =$j(this).parents("tr").attr("id");
				handOver(id);
			});
			$j("button[name='btnCancel']").click(function(){
				var id =$j(this).parents("tr").attr("id");
				//作废交接清单
				if(!window.confirm(i18("SURE_CANCEL"))){//您确定要作废？
					return false;
				}
				canelHandOverList(id);
			});
			$j("button[name='btnPrint']").click(function(){
				// if(beforePrintValidate())
				var tr = $j(this).parents("tr");
				var id = tr.attr("id");
				var data=$j("#tbl-hand-over-list").jqGrid("getRowData",id)
				if(data["hoIntStatus"] == '0'){
					jumbo.showMsg("交接清单已作废不能打印.");
					return;
				}
//				alert("run");
				printHoInfo(id);  // 明细打印
			});
		},
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#tbl-detial").jqGrid({
		datatype: "json",
		//colNames: ["ID","快递单号","相关单据号","包裹重量","收货人","扫描出库时间","状态","操作"],
//		colNames: ["ID",i18("TRACKINGNO"),i18("REFSLIPCODE"),i18("WEIGHT"),i18("RECEIVER"),i18("OUTBOUNDTIME"),i18("LINEINTSTATUS"),i18("IDFORBTN")],
		colNames: ["ID",i18("TRACKINGNO"),i18("WEIGHT"),i18("OUTBOUNDTIME"),i18("LINEINTSTATUS")],
		colModel: [{name: "id", index: "id", hidden: true},
					{name :"trackingNo",index:"trackingNo",width:100,resizable:true},
//					{name: "refSlipCode", index:"refSlipCode",width:100,resizable:true},
					{name:"weight", index:"weight", width:90, resizable:true},
//					{name:"receiver",index:"receiver",width:90,resizable:true},
					{name:"outboundTime",index: "outboundTime",width:150,resizable:true},
					{name:"lineIntStatus", index:"lineIntStatus" ,width:60,resizable:true,formatter:'select',editoptions:status}],
//					{name: "idForBtn", width: 120, formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("CANCEL"), onclick:"cancelDetial(this);"}}}],//作废
		caption: i18("DETAIL"),//明细
		rowNum:-1,
	   	//rowList:[10,20,30],
	   	sortname: 'trackingNo',
	    multiselect: false,
		sortorder: "desc",
		height:"auto",
		gridComplete : function(){
			removeCancelHoLineBtn();
			loxia.initContext($j(this));
		},
		jsonReader: { repeatitems : false, id: "0" }
	});

	$j("#brnDetialHo").click(function(){
		var id = $j("#divHandOverDeital").attr("handOverId");
		handOver(id);
	});

	$j("#back,#back3").click(function(){
		$j("#divHandOverList").removeClass("hidden");
		$j("#divHandOverDeital").addClass("hidden");
		$j("#allDelete").addClass("hidden");
		$j("#query").trigger("click");
	});

	
	// 交接查询- 保存及打印
	$j("#btnDetialSavePrint").click(function(){
		var errors=loxia.validateForm("handOver_form");
		if(errors.length>0){
			jumbo.showMsg(errors);
			return false;
		}
		var postData = loxia._ajaxFormToObj("handOver_form");
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/savehandoverlistinfo.json",postData);
		if(rs && rs.result == 'success'){
			//保存成功->打印
			if(beforePrintValidate()){
				jumbo.showMsg(i18("OPERATING"));
				var hlid = $j("#handOverListid").val();
				var url = $j("body").attr("contextpath") + "/json/printhandoverlist.json?hoListId=" + hlid;
				printBZ(loxia.encodeUrl(url),true);				
			}
		}else{
			jumbo.showMsg(rs.message);
		}
	});
	
	
	// 交接查询- 打印
	$j("#btnDetialPrint").click(function(){
		if(beforePrintValidate()){
			var hoId = $j("#divHandOverDeital").attr("handOverId");
			var postData = {};
			postData["hoListId"] = hoId;
			loxia.asyncXhrPost(
					$j("body").attr("contextpath") + "/json/findhandoverlistbyid.do",
					postData,
					{
						success:function(data){
								if(data){
									var ho = data.handoverlist;
									if(ho.intStatus == 0){
										jumbo.showMsg("当前交接清单已取消，不能打印");
									}else {
										printHoInfo(hoId);				
									}
								}
							},
						error:function(){jumbo.showMsg(i18("DATA_ERROR"));}//操作数据异常
					}
			);
		}
	});
	
	//导出物流送达明细
	$j("#export").click(function(){
		var hlid = $j("#handOverListid").val();
		var code = $j("#code").text();
		$j("#frmTrans").attr("src",baseUrl + "/warehouse/exportTransInfo.do?hoListId=" + hlid+"&hoListCode="+code);
	});
});

function showDetail(tag){
	var postData = {};
	var id =$j(tag).parents("tr").attr("id");
	$j("#divHandOverList").addClass("hidden");
	$j("#divHandOverDeital").removeClass("hidden");
	$j("#divHandOverDeital").attr("handOverId",id);
	postData["hoListId"] = id;
	loxia.asyncXhrPost(
			$j("body").attr("contextpath") + "/json/findhandoverlistbyid.do",
			postData,
			{
				success:function(data){
						if(data){
							var rs = data.handoverlist;
							showDetailHandoverListInfo(rs);
						}
					},
				error:function(){jumbo.showMsg(i18("DATA_ERROR"));}//操作数据异常
			}
	);
	$j("#tbl-detial").jqGrid('setGridParam',{url:loxia.getTimeUrl(window.$j("body").attr("contextpath") + "/json/findlinebyholistid.json?hoListId="+id)}).trigger("reloadGrid");
}

function beforePrintValidate(){
	var ids = $j("#tbl-detial").jqGrid('getDataIDs');
	if(ids.length==0) {jumbo.showMsg("数据为空，是否数据出错..."); return false;}
	var num=0,size=0;
	var datas;
	for(var i=0;i < ids.length;i++){
		size++;
		datas = $j("#tbl-detial").jqGrid('getRowData',ids[i]);
		if (datas['lineIntStatus']==0){
			num++;
		}
	}
	if (size==num){
		jumbo.showMsg("当前交接单已取消，不能打印..."); 
		return false;
	}
	return true;
}

function showDetailHandoverListInfo(rs){
	$j("#code").html(rs.code);
	$j("#createTime").html(rs.createTime);
	$j("#packageCount").html(rs.packageCount);
	$j("#totalWeight").html(rs.totalWeight);
	if(rs.intStatus == 1) {
		$j("#tbl_hand_over_list_info").addClass("hidden");
		$j("#handOver_form").removeClass("hidden");
		$j("#brnDetialHo").removeClass("hidden");
		$j("#export").addClass("hidden");
		
		$j("#handOverListid").val(rs.id);
		$j("#partyAOperator1").val(rs.partyAOperator);
		$j("#partyAMobile1").val(rs.paytyAMobile);
		$j("#partyBOperator1").val(rs.partyBOperator);
		$j("#partyBMobile1").val(rs.paytyBMobile);
		$j("#paytyBPassPort1").val(rs.paytyBPassPort);
		$j("#btnDetialPrint").addClass("hidden");
		$j("#btnDetialSavePrint").removeClass("hidden");
	}else if(rs.intStatus == 10 || rs.intStatus == 0) {
		$j("#tbl_hand_over_list_info").removeClass("hidden");
		$j("#handOver_form").addClass("hidden");
		$j("#brnDetialHo").addClass("hidden");
		$j("#export").removeClass("hidden");
		
		$j("#handOverListid").val(rs.id);
		$j("#partyAOperator").html(rs.partyAOperator);
		$j("#partyAMobile").html(rs.paytyAMobile);
		$j("#partyBOperator").html(rs.partyBOperator);
		$j("#partyBMobile").html(rs.paytyBMobile);
		$j("#paytyBPassPort").html(rs.paytyBPassPort);
		$j("#btnDetialSavePrint").addClass("hidden");
		$j("#btnDetialPrint").removeClass("hidden");
	} // 完成状态
}