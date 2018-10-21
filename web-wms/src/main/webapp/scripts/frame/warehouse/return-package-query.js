$j.extend(loxia.regional['zh-CN'],{
	
	FAILED_DISABLE_PRINT : "当前订单状态为‘配货失败’不能打印"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

$j("#query-order-list").addClass("hidden");
$j("#order-detail").removeClass("hidden");

function showDetail(obj){
	$j("#div1").addClass("hidden");
	$j("#div2").removeClass("hidden");
	var id=$j(obj).parent().parent().attr("id");
    var trackingNo= $j("#return_package_list").getCell(id,"trackingNo");
    var strStatus= $j("#return_package_list").getCell(id,"strStatus");
    $j("#returnId").html(id);
    $j("#trackNoId").html(trackingNo);
    if(strStatus == "已入库"){
    	$j("input[id=radio1]:eq(0)").attr("checked",'checked'); 
    }else{
    	$j("input[id=radio2]:eq(0)").attr("checked",'checked'); 
    }
    //$j("input[name=xzhbfh]:eq(0)").attr("checked",'checked'); 
}

$j(document).ready(function (){
	$j("#div2").addClass("hidden");
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransactionTypeName.do");
	var baseUrl = $j("body").attr("contextpath");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#lpcode"));
	}
	//获取仓库
	var resultopc = loxia.syncXhrPost($j("body").attr("contextpath")+"/selectAllPhyWarehouse.json");
	for(var idx in resultopc.pwarelist){
		$j("<option value='" + resultopc.pwarelist[idx].id + "'>"+ resultopc.pwarelist[idx].name +"</option>").appendTo($j("#selTransOpc"));
	}
	$j("#return_package_list").jqGrid({
		datatype: "json",
		colNames: ["ID","批次","状态","物流商","快递单号","拒收原因","登记物理仓","退换货申请单","作业单","相关单号","仓库","登记时间","最后操作时间","创建人","解锁人","重量(KG)","备注"],
		colModel: [
		        {name:"id", index: "id", hidden: true,sortable:false},	
		        {name:"batchCode",index:"type",width:110,resizable: true},
	            {name:"strStatus",index:"strStatus",width:80,resizable: true},
	            {name:"lpcode",index:"lpcode",width:80,resizable:true},
	            {name:"trackingNo",index:"tracking_no",width:100,resizable:true},
	            {name:"rejectionReasons",index:"rejection_reasons",width:120,resizable:true},
	            {name:"physicalWarehouse",index:"physicalWarehouse",width:100,resizable:true},
	            {name:"returnApplicationCode",index:"returnApplicationCode",width:120,resizable: true},
	            {name:"staCode",index:"staCode",width:100,resizable: true},
	            {name:"staSlipCode",index:"staSlipCode",width:100,resizable: true},
	            {name:"warehouseName",index:"warehouseName",width:120,resizable:true},
	            {name:"createTime",index:"createTime",width:120,resizable: true},
	            {name:"lastModifyTime",index:"last_modify_time",width:120,resizable: true},
	            {name:"userName",index:"userName",width:80,resizable: true},
	            {name:"unlockUser",index:"unlockUser",width:80,resizable: true},
	            {name:"weight",index:"weight",width:80,resizable:true},
	            {name:"remarksb",index:"remarksb",width:180,resizable:true}
	            ],
		caption: "登记退换货入库包裹列表",
		sortname: 't.create_time',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#pager",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
//			var datalist = $j("#return_package_list" ).getRowData();
//			for(var i=0;i<datalist.length;i++){
//				var status = datalist[i].intStatus;
//				var statusName = "未知状态"
//				if(status == 0){
//					statusName = '已拒收';
//				} else if(status == 1){
//					statusName = '已收件';
//				} else if(status == 5){
//					statusName = '处理中';
//				} else if(status == 10){
//					statusName = '已入库';
//				}
//				$j("#return_package_list").jqGrid('setRowData',datalist[i].id,{"intStatus":statusName});
//		   	}
			//loxia.initContext($j("#return_package_list"));
		}
	});
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
	});
	$j("#search").click(function(){
		var url = baseUrl + "/queryreturnpackagehand.json";
		$j("#return_package_list").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("return_package_list",{},url,loxia._ajaxFormToObj("queryForm"));
	});
	
	
	var whs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/queryWarehouse.do");
	
//	warehouseDate = new Array()
//	var i = 0;
//	for(var i in whs){
//		warehouseDate[i] = whs[i].name;
//	}
//	$j("#warehouseName").autocomplete(warehouseDate, {
//		width: 320,
//		max: 4,
//		highlight: false,
//		multiple: true,
//		multipleSeparator: "",
//		scroll: true,
//		scrollHeight: 300
//	});
	
	
	$j('#warehouseName').autocomplete(whs, {
        max: 12,    //列表里的条目数
        minChars: 0,    //自动完成激活之前填入的最小字符
        width: 400,     //提示的宽度，溢出隐藏
        scrollHeight: 300,   //提示的高度，溢出显示滚动条
        matchContains: true,    //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示
        autoFill: false,    //自动填充
        formatItem: function(row, i, max) {
            return  row.name + '—[' + row.code + ']';
        },
        formatMatch: function(row, i, max) {
            return row.name + row.code;
        },
        formatResult: function(row) {
            return row.name;
        }
    }).result(function(event, row, formatted) {
        //alert(row.code);
    });
	
	$j("#toback").click(function(){
		$j("#div2").addClass("hidden");
		$j("#div1").removeClass("hidden");
	});
	
	$j("#save").click(function(){
		$j("#returnId").html();
		var raId = $j("#returnId").html();
		var typeId = "";  //登记类型的ID
		var type =  document.getElementsByName("type");
		if(type[0].checked){
			typeId= 10; // 录入
		}else{
			typeId = 0; // 拒收
		}
		var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/returnApplicationUpdate.json?raId="+raId+"&typeId="+typeId);
	    if(rs.result=='success'){
	    	jumbo.showMsg("保存成功！");
	    	window.location.reload();
	    }else{
	    	jumbo.showMsg("保存失败！");
	    }
	});
	
	
});

