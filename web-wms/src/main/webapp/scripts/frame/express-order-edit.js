$j.extend(loxia.regional['zh-CN'],{
	APPLY_FORM_SELECT : "请选择作业申请单",
	CODE : "作业单号",
	STATUS : "状态",
	REFSLIPCODE : "相关单据号",
	INTTYPE : "作业类型名称",
	SHOPID : "相关店铺",
	LPCODE : "物流服务商简称",
	ISNEEDINVOICE : "是否需要发票",
	CREATETIME : "创建时间",
	STVTOTAL : "计划执行总件数",
	COUNT_SEARCH : "待出库列表",
	
	SKUNAME : "商品名称",
	BARCODE : "条形码",
	JMCODE : "商品编码",
	KEYPROPERTIES : "扩展属性",
	QUANTITY : "计划执行量",
	DETAIL_INFO : "详细信息",
	TRACKINGNO : "快递单号",
	INPUT_TRACKINGNO_TYPE :"添加关系快递单号",
	UPDATE_SUCCE : "修改成功",
	STA_STATUS : "作业单状态",
	SHOP_STORE : "店铺",
	LP_BUSINESS : "物流商",
	EXPRESS_ORDER_NUM : "快递单号",
	CREATE_TIME : "创建时间",
	CRT_USER : "操作人",
	PLAN_COUNT : "计划量",
    ORDERBLANK_CAPTION: "作业单列表"
});

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

function showStaLine(tag){
	var tr = $j(tag).parents("tr");
	var id = tr.attr("id");
	$j("#staInfo").addClass("hidden");
	$j("#staLineInfo").removeClass("hidden");
	var sta=$j("#tbl-staList-query").jqGrid("getRowData",id);
	$j("#s_code").html(sta["code"]);
	$j("#s_createTime").html(sta["createTime"]);
	$j("#s_slipCode").html(sta["refSlipCode"]);
	$j("#s_owner").html(sta["shopId"]);
	$j("#s_status").html(tr.find("td[aria-describedby$='intStatus']").html());
	$j("#s_type").html(tr.find("td[aria-describedby$='intType']").html());
	$j("#s_trans").html(tr.find("td[aria-describedby$='lpcode']").html());
	$j("#s_inv").html(tr.find("td[aria-describedby$='isNeedInvoice']").html());
	$j("#lpcode").val(sta["lpcode"]);
	$j("#staId").val(id);
	var postData = {};
	postData["sta.id"] = id;
	$j("#tbl_detail_list").jqGrid('setGridParam',
			{url:window.$j("body").attr("contextpath")+"/findSalesStaLine.json",
			postData:postData,page:1}
		).trigger("reloadGrid");
	
	//获取作业单对应相关快递单号
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findPackageInfoList.json",{"sta.id":id});
	$j("#trackingNoList").html("");
	for(var idx in result){
		$j("#trackingNoList").append("["+result[idx].trackingNo + "] ");
	}
	showLines(result);//初始编辑框中快递单号
}

//编辑对应快递单号  
function showLines(data){	
	var rs =[],header=getLocationsTableHeader(),foot=getLocationsTableFoot();
		var html=[],templete=getLocationsTableTemplete();
		html.push("<form id=\"logistics\" name=\"logistics\" >");
		html.push(header);
		html.push(getLocationsTable(data));
		html.push(templete);
		html.push(foot);
		html.push(" </form>");
    	rs.push(html.join(""));

 	  $j("#logistics").remove();//删除table编辑节点
	  $j("#stvlineListtable").after(rs.join(""));

	  loxia.initContext($j("#staLineInfo"));
 }

//库位EditableTable表头
function getLocationsTableHeader(){	       
	    var html ="	<div loxiaType=\"edittable\" id=\"edittable\" style=\"width:180px;\" >"+
		"<table operation=\"add,delete\" append=\"0\" style=\"width:180px;\">"+
			"<thead>"+
				"<tr>"+
					"<th><input type=\"checkbox\" /></th>"+
					"<th>"+i18("TRACKINGNO")+"</th>"+
				"</tr>"+
			"</thead>";
	    return html;
}
//快递单号编辑表格模板
function getLocationsTableTemplete(){
	      var tb2= " <tbody> "+
					"<tr >"+
					"<td><input type=\"checkbox\" /></td>"+
					"<td width=\"150\"><input  loxiaType=\"input\" trim=\"true\"  mandatory=\"true\" name=\"trackingNo\"/></td>"+
				"</tr>"+
			"</tbody> ";
	    
	    return tb2;
   }

//循环显示快递单号
function getLocationsTable(data){
	var tb=[];
	tb.push(" <tbody>");
	$j.each(data,function(i, item) {
		tb.push("<tr>");
		tb.push("<td><input type=\"checkbox\" /></td>");
		tb.push("<td width=\"150\"><input loxiaType=\"input\"  value=\""+item.trackingNo+"\"  trim=\"true\"  mandatory=\"true\" name=\"trackingNo\"/></td>");
		tb.push("</tr>");
	   	});
	tb.push(" </tbody>");
	return tb.join("");
}

//库位EditableTable.Foot
function getLocationsTableFoot(){
	var foot = "</table></div>";
	return foot;
}

function showErrorRow(div){
	var isError = false;
	var msg = "";
	
	if($j(div + " table tbody").children().length == 0){
		msg = i18("INPUT_TRACKINGNO_TYPE"); // "请添加作业类型数据";
		isError = true;
	}
	if(isError){
		jumbo.showMsg(msg);
	}
	return isError;
}

function getFormData(form){
	var postData = {};
	$j(form + " table tbody tr").each(function (i,tr){
        var expNum=$j(tr).find("input[name='trackingNo']").val();
        var pattern = new RegExp("[`~!@%#$^&*()=|{}':;',　\\[\\]<>/? \\.；：%……+￥（）【】‘”“'。，、？]"); 
		if(!pattern.test(expNum)){
			postData["expLineCmd["+i+"].trackingNo"] = $j(tr).find("input[name='trackingNo']").val();
			postData["standard"]="success";
		}else{
			postData["standard"]="error";
		}
	});
	return postData;
}
var wlist =null;
$j(document).ready(function (){
	//根据运营中心仓库查询
	loxia.init({debug: true, region: 'zh-CN'});
	
	jumbo.loadShopList("selShopId");
	wlist = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/checkoperationunit.json");
	//init tranports 
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getTransportator.json");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selTrans"));
	}
	var result2= loxia.syncXhrPost($j("body").attr("contextpath")+"/selectbyopc.json");
	for(var idx in result2.warelist){
		$j("<option value='" + result2.warelist[idx].id + "'>"+ result2.warelist[idx].name +"</option>").appendTo($j("#wselTrans"));
	}
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	var postDataO = {};
	for(var idx in wlist){
		postDataO["plList["+idx+"]"]=wlist[idx];
    }
	/* 这里编写必要的页面演示逻辑*/
	 $j("#tbl-staList-query").jqGrid({
		url:$j("body").attr("contextpath")+"/json/expressOrderQuery.json",
		datatype: "json",
		colNames: ["ID","仓库名称",i18("CODE"),i18("REFSLIPCODE"),i18("STA_STATUS"),i18("SHOP_STORE"),i18("LP_BUSINESS"),i18("EXPRESS_ORDER_NUM"),i18("CREATE_TIME"),i18("CRT_USER"),i18("PLAN_COUNT")],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name:"wname",index: "wname",width:120,resizable:true},
					{name :"code",index:"lpcode",formatter:"linkFmatter",formatoptions:{onclick:"showStaLine"},width:100,resizable:true},
					{name:"refSlipCode", index:"refSlipCode" ,width:100,resizable:true},
					{name: "intStatus", index: "intStatus", width: 120, resizable: true,formatter:'select',editoptions:staStatus},
					{name:"shopId", index:"shopId", width:100, resizable:true},
					{name: "lpcode", index: "lpcode", width: 120, resizable: true,formatter:'select',editoptions:trasportName},
	                {name:"trackingNo",index:"trackingNo",width:150,resizable:true},
					{name:"createTime",index:"createTime",width:150,resizable:true},
					{name:"crUser",index:"crUser",width:150,resizable:true},
				    {name:"planQty",index: "planQty",width:120,resizable:true}],
		caption: i18("ORDERBLANK_CAPTION"),
		sortname: 'ID',
  		multiselect: false,
		sortorder: "desc",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	viewrecords: true,
   		rownumbers:true,
	   	pager:"#pager",
		jsonReader: { repeatitems : false, id: "0" },
		postData:postDataO 
	});
//	    if(wlist!=""){
//		 $j("#tbl-staList-query_wname").show();
//		 $j("#tbl-staList-query tr").each(function(){
//			 $j(this).children("td:eq(1)").show();
//		 });
//		}
	    if(wlist!=""){
	    	$j("#wnames").show();
	    	$j("#wselTrans").show();
	    }
	 	$j("#tbl_detail_list").jqGrid({
		datatype: "json",
		//colNames: ["ID","商品名称","条形码","商品编码","扩展属性","计划执行量"],
		colNames: ["ID",i18("SKUNAME"),i18("BARCODE"),i18("JMCODE"),i18("KEYPROPERTIES"),i18("QUANTITY")],
		colModel: [
		           {name: "id", index: "id", hidden: true},		         
				   {name: "skuName", index: "skuName", width: 200, resizable: true},
		           {name: "barCode", index: "barCode",width: 120, resizable: true},
		           {name: "jmcode", index: "jmcode", width: 100, resizable: true},
		           {name: "keyProperties", index: "keyProperties", width: 100, resizable: true},
		           {name: "quantity", index: "quantity", width: 120, resizable: true}],
		caption: i18("DETAIL_INFO"),//详细信息
		rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	//    pager:"#pager",
		height:"auto",
   		sortname: 'id',
     	multiselect: false,
		sortorder: "desc", 
		jsonReader: { repeatitems : false, id: "0" }
	});
	 	
	 $j("#search").click(function(){
		var formCrtime = getDate($j("#formCrtime").val());
		var toCrtime = getDate($j("#toCrtime").val());
		if(formCrtime > toCrtime){
			jumbo.showMsg(i18("CREATE_TIME_RULE"));//起始时间必须小于结束时间！
			return false;
		}
		var postData=loxia._ajaxFormToObj("staForm");
		postData["wid"]=$j("#wselTrans").val();
		for(var idx in wlist){
        postData["plList["+idx+"]"]=wlist[idx];
		}
		$j("#tbl-staList-query").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/expressOrderQuery.json"),
		postData:postData }).trigger("reloadGrid",[{page:1}]);
	});

	$j("#reset").click(function(){
		$j("#filterTable input").val("");
		$j("#filterTable select").val("");
	});
	
	$j('#edit').click(function(){
			var errors=loxia.validateForm("logistics");
			if(errors.length>0){
				jumbo.showMsg(errors);
				return false;
			}
			var isError = false;
			isError = showErrorRow("#edittable");
			if(isError) return false;
			
		var postData = getFormData("#logistics");
		if(postData["standard"]=="error"){
			jumbo.showMsg("快递单号包含特殊字符!");
			return;
		}
		postData["expressOrderCmd.id"] = $j("#staId").val();
		postData["expressOrderCmd.lpcode"] = $j("#lpcode").val();
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/editTrackingNos.json",postData);
		
		if(rs && rs.msg == 'success'){
			//修改成功
			jumbo.showMsg(i18("UPDATE_SUCCE"));
			$j("#staInfo").removeClass("hidden");
			$j("#staLineInfo").addClass("hidden");
			var postData=loxia._ajaxFormToObj("staForm");
			postData["wid"]=$j("#wselTrans").val();
			for(var idx in wlist){
	        postData["plList["+idx+"]"]=wlist[idx];
			}
			$j("#tbl-staList-query").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/expressOrderQuery.json"),
			postData:postData}).trigger("reloadGrid",[{page:1}]);
		}else{
			if(rs.errMsg != null){
			var msg = rs.errMsg;
			var s = '';
			for(var x in msg){
				s +=msg[x] + '<br/>';
			}
				jumbo.showMsg(s);
			}else{
				jumbo.showMsg(rs.msg);
			}
		}
	});
	
	$j("#back").click(function(){
		$j("#staInfo").removeClass("hidden");
		$j("#staLineInfo").addClass("hidden");
	});
});