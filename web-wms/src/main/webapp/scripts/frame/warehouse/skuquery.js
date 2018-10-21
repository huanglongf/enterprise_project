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
	CORRECT_FILE_PLEASE:"请选择正确的Excel文件进行导入"
});
function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
var customizationShopMap = {};//当前仓库定制打印模版店铺集合
$j(document).ready(function(){
var baseUrl = $j("body").attr("contextpath");
	
	initShopQuery("invOwner","innerShopCode");
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});

	
	jumbo.loadShopList("invOwner");
	$j("#tabs").tabs();
	$j("#pCode").focus();
	initShopQuery("shopId","innerShopCode");//初始化查询条件，根据当前登录用户及所选仓库初始化店铺选择列表
	//根据查询条件进行查询，得到查询结果,可以进行店铺选择一系列操作
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	//初始化列表
	$j("#tbl-staList-query").jqGrid({
		datatype: "json",
		colNames: ["ID","SKU商品编码","商品条码","SN号","操作"],
		colModel: [{name: "id", index: "id", hidden: true},		         
		           {name: "code", index: "code", width: 120 ,resizable: true},
		           {name: "barcode", index: "barcode",width: 120, resizable: true},
		           {name: "sn", index: "sn", width: 100, resizable: true},
		           {name: "deleteThis", index: "deleteThis"}],
		caption: "SN号产品列表",//待出库列表
	   	sortname: 'id',
	   	pager:"#pager_query",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:"auto",
	    multiselect: false,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc", 
		jsonReader: { repeatitems : false, id: "0" }
	});
$j("#import").click(function(){
	var file=$j.trim($j("#snsfile").val());
	if(!/^.*\.xls$/.test($j("#file").val())){
		jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));
		return;
	}
	$j("#importForm").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/skuquerylist.do"));
	loxia.submitForm("importForm");
});
$j("#btn-query").click(function(){
	var pCode=$j("#pCode").val();
	var pNumber=$j("#snnumber").val();
	var postData1={};
	postData1["barCode"]=pCode;
	postData1["snnumber"]=pNumber;	
    var j = 0;
    var postData = {};
    $j("#tbl-staList-query tr[id]").each(function(i,tag){
    	postData["skuSns["+j+"].sn"]=$j(tag).children("td:eq(3)").attr("title");
    	postData["skuSns["+j+"].sku.barCode"]=$j(tag).children("td:eq(2)").attr("title");
    	j++;
    });
    if(!postData.hasOwnProperty("skuSns[0].sn")){
    	jumbo.showMsg("列表没有SN编辑信息！");
    	return;
    }
    var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/snsimportHand.json",postData);
    if(rs["result"]=="product"){
    	jumbo.showMsg("没有该商品！");
    }else if(rs["result"]=="notSNproduct"){
    	 jumbo.showMsg("该商品不是sn商品！");
	}else if(rs["result"]=="UNIQUE"){
		jumbo.showMsg(rs["sncode"]+"SN号在数据库中已存在！");
	}else if(rs["result"]=="SUCCES"){
		jumbo.showMsg("保存成功！");
	}
	});
$j("#pCode").keydown(function(evt){
	if(evt.keyCode === 13){
		evt.preventDefault();
		showDetail1($j("#pCode").val());  
	}
});
$j("#snnumber").keydown(function(evt){
	var j = 0,q=0,b=0;
	if(evt.keyCode === 13){
		evt.preventDefault();
		if($j("#pCode").val()==""){
			jumbo.showMsg("请填写商品条码！");
			return;
		}else if($j("#snnumber").val()==""){
			jumbo.showMsg("请填写SN号！");
			return;
		}else if($j("#snnumber").val().indexOf(" ")!=-1){
			jumbo.showMsg("SN号中包含空格！");
			return;
		}else if(!/^[a-zA-Z0-9_]*$/.test($j("#snnumber").val())){
			jumbo.showMsg("请输入字母或数字！");
			return;
		}else if($j("#skuCodes").text()==""){
			jumbo.showMsg("请执行SN商品查询！");
			return;
		}
		$j("#tbl-staList-query tr[id]").each(function(i,tag){
			if($j("#snnumber").val()==$j(tag).children("td:eq(3)").attr("title")){
				jumbo.showMsg("请不要添加相同SN号编辑信息！");
				q=1;
			}else{
				b=0;
			}
			j++;
		});
		if($j("#tbl-staList-query tr[id]").length==0){
			var str = "<tr id='"+$j("#snnumber").val()+"' class='ui-widget-content jqgrow ui-row-ltr' role='row' tabindex='-1'><td class='ui-state-default jqgrid-rownum' aria-describedby='tbl-staList-query_rn' title='' style='text-align:center;width: 25px;' role='gridcell'>1</td><td aria-describedby='tbl-staList-query_code' title='"+$j("#skuCodes").text()+"' style='' role='gridcell'>"+$j("#skuCodes").text()+"</td><td aria-describedby='tbl-staList-query_barcode' title='"+$j("#pCode").val()+"' style='' role='gridcell'>"+$j("#pCode").val()+"</td><td id='snnumber' aria-describedby='tbl-staList-query_sn' title='"+$j("#snnumber").val()+"' style='' role='gridcell'>"+$j("#snnumber").val()+"</td><td aria-describedby='tbl-staList-query_dt' style='' role='gridcell' title='titlessss'><button type='button' onclick='deleteThis(this);' loxiaType='button' class='confirm'>删除</button></td></tr>";
			$j("#tbl-staList-query").append(str);
		}else if(q==0&&b==0){
			var str = "<tr id='"+$j("#snnumber").val()+"' class='ui-widget-content jqgrow ui-row-ltr' role='row' tabindex='-1'><td class='ui-state-default jqgrid-rownum' aria-describedby='tbl-staList-query_rn' title='' style='text-align:center;width: 25px;' role='gridcell'>1</td><td aria-describedby='tbl-staList-query_code' title='"+$j("#skuCodes").text()+"' style='' role='gridcell'>"+$j("#skuCodes").text()+"</td><td aria-describedby='tbl-staList-query_barcode' title='"+$j("#pCode").val()+"' style='' role='gridcell'>"+$j("#pCode").val()+"</td><td id='snnumber' aria-describedby='tbl-staList-query_sn' title='"+$j("#snnumber").val()+"' style='' role='gridcell'>"+$j("#snnumber").val()+"</td><td aria-describedby='tbl-staList-query_dt' style='' role='gridcell' title='titlessss'><button type='button' onclick='deleteThis(this);' loxiaType='button' class='confirm'>删除</button></td></tr>";
			$j("#tbl-staList-query").append(str);
		}
	}
});
function showDetail1(pCode){
	if(pCode.length==0){
		loxia.tip($j("#pCode"),"请输入配货批次号！");
	}else{
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/getSkuBycode.json",{"barCode":pCode});
		if(rs["result"]=="product"){
          jumbo.showMsg("没有该商品！");		
		}else if(rs["result"]=="notSNproduct"){
			jumbo.showMsg("该商品不是sn商品！");		
		}else{
		$j("#skuSupplierCodes").html(rs["skulist"]["supplierCode"]);
		$j("#skuCodes").html(rs["skulist"]["code"]);
		$j("#skuNames").html(rs["skulist"]["name"]);
		}
	}
}
});
function deleteThis(obj){
	var tr = $j(obj).parents("tr");
	tr.remove();
}