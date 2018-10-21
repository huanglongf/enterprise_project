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

var barcodeList;
var ruleCodeList;
var partCheck;
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function getDate(strDate){
	var s = new Date(strDate.substring(0,10));
	s.setHours(strDate.substring(11,13));
	s.setMinutes(strDate.substring(11,13));
	s.setSeconds(strDate.substring(11,13));
	return s;
}



function showDetail(tag){
	$j("#tbl-detail-check-list").jqGrid("clearGridData");
	$j("#tbl-detail-list").jqGrid("clearGridData");
	$j("#tbl-detail-ruleCode tr").remove();
	
	var id = $j(tag).parents("tr").attr("id");
	var data = $j("#tbl-dispatch-list").jqGrid("getRowData",id);
	
	$j("#plid").val(id);
	$j("#picking-list").addClass("hidden");
	$j("#div2").removeClass("hidden");
	
	$j("#barCode").focus();
	$j("#plCode").html(data['code']);
	$j("#plCreateTime").html(data['createTime']);
	$j("#plCreateUser").html(data['crtUserName']);
	$j("#plBillCount").html(data['planBillCount']);

	var baseUrl = $j("body").attr("contextpath");
	var rs = loxia.syncXhr(baseUrl + "/findStaLineBySuggestion.json?pickinglistId="+id);
	barcodeList =rs.barcodeMap;
	ruleCodeList=JSON.parse(rs.ruleCodeMap);
	partCheck=rs.partCheck;
	$j("#tbl-detail-list").jqGrid('setGridParam',{datatype:'local',data:rs.staLineList}).trigger("reloadGrid");
	
	$j("#tbl-detail-check-list").jqGrid('setGridParam',{datatype:'local',data:rs.staLineCheckList}).trigger("reloadGrid");
	
	initRuleCodeList();
	
}

function initRuleCodeList(){
	var str="";
	var i=0;
	for(var key in ruleCodeList){
		if(ruleCodeList[key]==2){
			str+="<td id='"+key+"' class='demo'>"+key+"</td>";
		}else if(ruleCodeList[key]==17||ruleCodeList[key]==15){
			str+="<td id='"+key+"' style='background-color:red'>"+key+"</td>";
		}else{
			str+="<td id='"+key+"' class='demo' style='background-color:green'>"+key+"</td>";
		}
		if((i+1)%4==0){
			$j("<tr>"+str+"</tr>").appendTo($j("#tbl-detail-ruleCode"));
			str="";
		}
		i++;
	}
	if(str!=""){
		$j("<tr>"+str+"</tr>").appendTo($j("#tbl-detail-ruleCode"));
		
	}
	for(var key in partCheck){
		$j("#"+key).css("background-color","yellow");
	}
}
var wlist =null;
$j(document).ready(function (){
	var baseUrl = $j("body").attr("contextpath");
	$j("#pl_check_dialog").dialog({title: "拣货核对信息", modal:true, autoOpen: false, width: 600, height: 300});
	
	$j("#boxPaper").attr("checked", true);
	$j("#thermalPaper").attr("checked", false);
	
	var printers =getPrintName();
	if(printers!=null){
		var printers =printers.split(",");
		for(var i in printers){
			$j("<option value='" + printers[i] + "'>"+ printers[i]+"</option>").appendTo($j("#printName"));
		}
		
	}
	
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
	
	$j("#tbl-detail-list").jqGrid({
		//url:url,
		datatype: "json",
		colNames: ["ID","作业单ID","作业单号","状态编码","状态","SKU编码","SKU条码","商品名称","数量","已核对数量","箱号","完成度"],
		colModel: [{name: "id", index: "sku.id", hidden: true},
		           {name:"staId", index:"sta.id", width:100,resizable:true,hidden: true},
		           {name:"staCode", index:"sta.code", width:100,resizable:true},
					{name:"intStatus", index:"sta.status" ,width:60,resizable:true ,hidden: true},
					{name:"status", index:"sta.status" ,width:60,resizable:true},
					{name:"skuCode",index:"sku.code",width:90,resizable:true},
					{name:"barCode",index:"sku.bar_code",width:120,resizable:true},
					{name:"skuName",index:"sku.name",width:170,resizable:true},
					{name:"quantity",index:"l.quantity",width:50,resizable:true},
					{name:"completeQuantity",index:"completeQuantity",width:50,resizable:true},
					{name:"ruleCode",index:"sta.rule_Code",width:50,resizable:true,hidden:false},
					{name:"finish",index:"finish",width:50,resizable:true,hidden:true}
					/*{name:"btnPrint",index:"btnPrint",width:70,resizable:true},*/
					/*{name:"btnPrint1",index:"btnPrint1",width:70,resizable:true}*/
				  ],
		caption: "待分拣商品明细列表",
		rowNum:500,
	   	sortname: 'createTime',
	   	width:880,
		height:"auto",
	    multiselect: false,
		sortorder: "desc",
		rownumbers:true,
		gridComplete : function(){
		loxia.initContext($j(this));
	},
	jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#tbl-detail-check-list").jqGrid({
		datatype: "json",
		colNames: ["ID","作业单ID","作业单号","状态编码","状态","SKU编码","SKU条码","商品名称","数量","已核对数量","箱号","货格号","操作"],
		colModel: [{name: "staLineId", index: "id", hidden: true},
	           	{name:"staId", index:"staId", width:120,resizable:true,hidden: true},
	           	{name:"staCode", index:"staCode", width:120,resizable:true},
				{name:"intStatus", index:"intStatus" ,width:60,resizable:true, hidden: true},
				{name:"statusS", index:"statusS" ,width:60,resizable:true},
				{name:"skuCode",index:"skuCode",width:90,resizable:true},
				{name:"skuBarcode",index:"skuBarcode",width:120,resizable:true},
				{name:"skuName",index:"skuName",width:170,resizable:true},
				{name:"qty",index:"qty",width:50,resizable:true},
				{name:"completeQty",index:"completeQty",width:50,resizable:true},
				{name:"pgIndex",index:"pgIndex",width:50,resizable:true,hidden:true},
				{name:"ruleCode",index:"ruleCode",width:50,resizable:true,hidden:false},
				{name:"btnPrint",index:"btnPrint",width:70,resizable:true}
		          ],
		          	caption: "已分拣商品明细列表",
		            sortname: 'id',
		            rowNum:500,
			  		multiselect: false,
					sortorder: "asc",
					width:"auto",
					height:"auto",
				   	viewrecords: true,
			   		rownumbers:true,
					jsonReader: { repeatitems : false, id: "0" },
					gridComplete: function(){
						var btn1 = '<div><button type="button" style="width:70px;" class="confirm" name="btnPrint" loxiaType="button" onclick="btnPrint(this,2);">'+"打印"+'</button></div>';
						var ids = $j("#tbl-detail-check-list").jqGrid('getDataIDs');
						for(var i=0;i < ids.length;i++){
							$j("#tbl-detail-check-list").jqGrid('setRowData',ids[i],{"btnPrint":btn1});
						}
						loxia.initContext($j(this));
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
	
	$j("#back1").click(function(){
		$j("#back1").addClass("hidden");
		$j("#btnlist").removeClass("hidden");
		$j("#divTbslipCode").addClass("hidden");
		$j("#divTbDetial").removeClass("hidden");
	});
	$j("#back").click(function(){
		
		checkCompleteoperate();
		
		
	});
	$j("#barCode").keydown(function(evt){
		if(evt.keyCode == 9){
			evt.preventDefault();
		}
		if(evt.keyCode === 13){
			var value = $j.trim($j(this).val());
			if(value==""){
				return;
			}
			//查看是否有此编码
			var checked=checkedSkuBarcode(value);
			if(checked==null){
				playMusic($j("body").attr("contextpath") +"/recording/barCodeError.wav");
				loxia.tip(this,i18("BARCODE_NOT_EXISTS"));//条形码不存在
				showDialog(i18("BARCODE_NOT_EXISTS"),"#showinfo");
				return ;
			}
			//记录并核对
			checkedOrRecord(value,checked);
		}
	});
	
	$j("#barCode0").keydown(function(evt){
		if(evt.keyCode == 9){
			evt.preventDefault();
		}
		if(evt.keyCode === 13){
			var value = $j.trim($j(this).val());
			if(value==""){
				return;
			}
			//查看是否有此编码
			var checked=checkedSkuBarcode(value);
			if(checked==null){
				playMusic($j("body").attr("contextpath") +"/recording/barCodeError.wav");
				loxia.tip(this,i18("BARCODE_NOT_EXISTS"));//条形码不存在
				showDialog(i18("BARCODE_NOT_EXISTS"),"#showinfo");
				return ;
			}
			//记录并核对
			checkedOrRecord(value,checked);
		}
	});
	
	$j("#pickinglistCodeQuery").keydown(function(event){
		if(event.keyCode == 13){
			var code = $j.trim($j("#pickinglistCodeQuery").val());
			if(code != null && code != ""){
				var rs = loxia.syncXhr(baseUrl + "/findStaLineBySuggestion.json?iptPlCode="+code);
				if(rs.result=="success"&& (rs.pickingCodeError==null|| rs.pickingCodeError=="" )&& (rs.pickingErrorStatus==null|| rs.pickingErrorStatus=="" )){
					var data=rs.pickingData;
					$j("#plid").val(data["id"]);
					$j("#picking-list").addClass("hidden");
					$j("#div2").removeClass("hidden");
					
					$j("#barCode").focus();
					$j("#plCode").html(data['code']);
					$j("#plCreateTime").html(data['createTime']);
					$j("#plCreateUser").html(data['crtUserName']);
					$j("#plBillCount").html(data['planBillCount']);
					
					barcodeList =rs.barcodeMap;
					ruleCodeList=JSON.parse(rs.ruleCodeMap);
					partCheck=rs.partCheck;
					$j("#tbl-detail-list").jqGrid('setGridParam',{datatype:'local',data:rs.staLineList}).trigger("reloadGrid");
					
					$j("#tbl-detail-check-list").jqGrid('setGridParam',{datatype:'local',data:rs.staLineCheckList}).trigger("reloadGrid");
					
					initRuleCodeList();
				}else{
					loxia.tip($j("#pickinglistCodeQuery"),i18("指定的配货批不正确，请重新输入"));
					$j("#pickinglistCodeQuery").val("");
				}
			}else{
				loxia.tip($j("#pickinglistCodeQuery"),i18("请输入配货批"));
			}
		}
	});
	
	$j("#doCheck0").click(function(){
		checkCompleteoperate();
	});
	$j("#checkValue").keydown(function(event){
		var value = $j("#checkValue").val();
		if(event.keyCode == 13){
			if (value == BARCODE_CONFIRM){
				checkCompleteoperate();
			}
		}	
	});	
});

function checkCompleteoperate(){
	$j("#barCode0").val(""); 
	$j("#barCode").val("");
	$j("#pl_check_dialog").dialog("close"); 
	$j("#picking-list").removeClass("hidden");
	$j("#div2").addClass("hidden");
	$j("#pickinglistCodeQuery").val("");
	$j("#pickinglistCodeQuery").focus();
	$j("#doCheck0").addClass("hidden");
	$j("#checkValue").addClass("hidden");
	$j("#checkValue").val("");
	$j("#showinfoOver").html("");
	
	$j("#tbl-detail-check-list").jqGrid("clearGridData");
	$j("#tbl-detail-list").jqGrid("clearGridData");
	$j("#tbl-detail-ruleCode tr").remove();
}

function checkedSkuBarcode(value){
	if(!value || value.length==0)return false;
	var data = $j("#tbl-detail-list").jqGrid('getRowData');
	for(var t in data){
		var item = data[t];
		if(item.barCode == value||item.barCode == '00' + value){
			return item.id;
		}
		
		var bcl=barcodeList[item.barCode];
		if(bcl!=null){
			var bcs=bcl.split(',');
			for(var v in bcs){
				if(bcs[v] == value || bcs[v] == ('00'+value)){
					return item.id;
				}
			}
		}
	}
	
	return null;
}
function checkedOrRecord(code,staLineId){
	var item=$j("#tbl-detail-list").jqGrid('getRowData',staLineId);
	var rs=loxia.syncXhr($j("body").attr("contextpath") + "/twicePickingByBarcode.json?pickinglistId="+$j("#plid").val()+"&code="+code+"&stalineId="+staLineId);
	if(rs.result=="success"){
		var data=rs.data;
		if(data==null){
			showDialog("系统异常","#showinfo");
			return;
		}
		//更新数量
		item.completeQuantity =parseInt(item.completeQuantity)+1;
		$j("#tbl-detail-list").jqGrid('setRowData',item.id,item);
		
		if(data.lineCancel){
			
			if(data.isLineCancel){
				showDialog("此行商品已被取消 ！  箱号是 ：" + item.ruleCode+"     此单已复核完成，存在行取消,请联系异常组！","#showinfo");
			}else{
				showDialog("箱号是 ：" + item.ruleCode+"     此单已复核完成，存在行取消,请联系异常组！","#showinfo");
			}
		}else{
			
			if(data.isLineCancel){
				showDialog("此行商品已被取消！ 箱号是 ：" + item.ruleCode,"#showinfo");
			}else{
				showDialog("箱号是 ：" + item.ruleCode,"#showinfo");
			}
		}
		
		
		if(data.isLineOver){
			//数据下移
			downData(staLineId);
			
			//判断此单是否核对完成
			if(data.staStatus=="check"){
				//核对完成
				updateCheckedLineStatus(data.staId,"已核对");
				//货格颜色
				$j("#"+item.ruleCode).css("background-color","green");
				if(data.lineCancel){
				}else{
					//打印
					printOrder(data.staId,data.packageInfoId);
				}
			}else if(data.staStatus=="cancel"){
				//此单已取消
				updateCheckedLineStatus(data.staId,"取消已处理");
				//货格颜色
				$j("#"+item.ruleCode).css("background-color","red");
			}else if(data.staStatus=="cancel_undo"){
				//取消待处理
				updateCheckedLineStatus(data.staId,"取消待处理");
				//货格颜色
				$j("#"+item.ruleCode).css("background-color","red");
			}else if(data.staStatus=="partCheck"){
				//取消待处理
				updateCheckedLineStatus(data.staId,"部分取消");
				//货格颜色
				$j("#"+item.ruleCode).css("background-color","yellow");
				if(data.lineCancel){
				}else{
					//打印
					printOrder(data.staId,data.packageInfoId);
				}
			}
			var ids2 = $j("#tbl-detail-list").jqGrid('getDataIDs');
			if(ids2==null||ids2.length==0){
				
				$j("#showinfoOver").html("批次[ " + $j("#plCode").html() + " ]核对完成");
				$j("#inputBarCode").addClass("hidden");
				$j("#doCheck0").removeClass("hidden");
				$j("#checkValue").removeClass("hidden");
				$j("#checkValue").focus();
			}
			
		}
		
	}else if(rs.result=="error"){
		//jumbo.showMsg(rs.msg);
		showDialog(rs.msg+" 箱号是 ：" + item.ruleCode,"#showinfo");
	}else{
		showDialog("系统异常","#showinfo");
	}
}


function downData(staLineId){
	var item=$j("#tbl-detail-list").jqGrid('getRowData',staLineId);
	$j("#tbl-detail-list").find("tr[id="+staLineId+"]").remove();
    obj = {
    		staLineId:staLineId,
			staId:item.staId,
			staCode:item.staCode,
			intStatus:item.intStatus,
			statusS:item.status,
			skuCode:item.skuCode,
			skuName:item.skuName,
			pgIndex:item.pgIndex,
			skuBarcode:item.barCode,
			ruleCode:item.ruleCode,
			qty:item.quantity,
			completeQty:item.completeQuantity
	};
    var ids2 = $j("#tbl-detail-check-list").jqGrid('getDataIDs');
	var $firstTrRole = $j("#tbl-detail-check-list").find("tr").eq(1).attr("role");  
	var rowid = $firstTrRole == "row" ? Math.max.apply(Math,ids2):0;  
	var newrowid = parseInt(rowid)+1;
	$j("#tbl-detail-check-list").jqGrid("addRowData", newrowid, obj, "last");//将新添加的行插入到最后一列
}

function updateCheckedLineStatus(staId,statusS){
	var ids = $j("#tbl-detail-check-list").jqGrid('getDataIDs');
	for(var j=0;j<ids.length;j++){
		if(staId==$j("#tbl-detail-check-list").getCell(ids[j],"staId")){
			var item=$j("#tbl-detail-check-list").jqGrid('getRowData',ids[j]);
			item.statusS =statusS;
			$j("#tbl-detail-check-list").jqGrid('setRowData',ids[j],item);
		}
	}
	
}
function printOrder(staId,packageInfoId){
	var printName=$j("#printName").val();
	if($j("#boxPaper").attr("checked")){
		var thermal=null
		var url2 ;
		if($j("#thermalPaper").attr("checked")){
			thermal="thermal";
			url2 = $j("body").attr("contextpath") + "/printPickinglist.json?plCmd.id=" + $j("#plid").val()+ "&plCmd.staId=" + staId+"&paperType="+thermal;
			printBZ(loxia.encodeUrl(url2),false);
		}else{
			url2 = $j("body").attr("contextpath") + "/printPickinglist.json?plCmd.id=" + $j("#plid").val()+ "&plCmd.staId=" + staId;
			printDf(url2,printName);
		}
	}
	var url = $j("body").attr("contextpath") + "/printSingleOrderDetail1.json?id=" +packageInfoId;
	printBZ(loxia.encodeUrl(url),false);
}
//弹出
function showDialog(text0,showid){
	if (showid != null) $j(showid).html(text0);
	$j("#barCode0").val("");
	$j("#inputBarCode").removeClass("hidden");
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

//打印
function btnPrint(tag,type){
	var id = $j(tag).parents("tr").attr("id");
  var staId = "";
	if (type == 1){
		staId = $j("#tbl-detail-list").getCell(id,"staId");
	}else{
		staId = $j("#tbl-detail-check-list").getCell(id,"staId");
	}
	var url = $j("body").attr("contextpath") + "/printSingleOrderDetail2.json?id=" +staId;
	printBZ(loxia.encodeUrl(url),true);
		
}