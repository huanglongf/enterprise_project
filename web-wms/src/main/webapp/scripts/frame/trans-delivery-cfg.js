function showDetail(obj){
	var transId=$j(obj).parent().parent().attr("id");
	var trans=$j("#tbl_trans").jqGrid("getRowData",transId);
	$j("#transId").val(transId);
	$j("#td_expCode").html(trans.expCode);
	$j("#td_fullName").html(trans.fullName);
	$j("#td_sendQty").html(trans.sendQty);
	if(trans.quantity != '未设置'){
		$j("#qty").val(trans.quantity);
	}
	$j("#divTrans").addClass("hidden");
	$j("#divDetial").removeClass("hidden");
}

function checkCoefficient(value,obj){
	if(value == "") return loxia.SUCCESS;
	var test =/^0\.[0-9]+$/;
	if(!test.test(value)) return "数据超出范围";
	else return loxia.SUCCESS;
}

var baseUrl = "";
$j(document).ready(function (){
	baseUrl = $j("body").attr("contextpath");
	$j("#tbl_trans").jqGrid({
		url:baseUrl+"/queryTransportatorCfg.json",
		datatype: "json",
		colNames: ["ID","物流商简称","物流商名称","当日计划发货量","当日已发货量"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name :"expCode",index:"expCode",formatter:"linkFmatter",formatoptions:{onclick:"showDetail"},width:80,resizable:true},
					{name:"fullName", index:"fullName" ,width:150,resizable:true},
					{name:"quantity", index:"quantity" ,width:100,resizable:true},
					{name:"sendQty", index:"sendQty" ,width:100,resizable:true}
					],
		caption: "物流发货量配置列表",
	   	sortname: 'id',
	    multiselect: false,
	    rowNum: -1,
		rowList:jumbo.getPageSizeList(),
	    rownumbers:true,
	    height:jumbo.getHeight(),
		sortorder: "desc",
		loadComplete:function(){
			loxia.initContext($j("#tbl_trans"));
		},
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete:function(){
			var ids = $j("#tbl_trans").jqGrid('getDataIDs');
			var datas;
			for(var i=0;i < ids.length;i++){
				datas=$j("#tbl_trans").jqGrid('getRowData',ids[i])
				if(datas['quantity'] == '-1'){
					$j("#tbl_trans").jqGrid('setRowData',ids[i],{"quantity":'未设置'});
				}
			}
		}
	});
	
	$j("#back").click(function(){
		initFrom();
	});
	
	$j("#detialSave").click(function(){
		if(confirm("确认执行此操作？")){
			var postData = {};
			if($j.trim($j("#qty").val()) == ""){
				jumbo.showMsg("计划发货量不正确.");
				return;
			}
			postData["qty"]=$j.trim($j("#qty").val());
			postData["transId"]=$j("#transId").val();
			var rs = loxia.syncXhrPost(baseUrl + "/saveCfg.json",postData);
			if(rs){
				if(rs.result == 'success'){
					jumbo.showMsg("修改成功！");
				} else if (rs.result == 'error'){
					jumbo.showMsg("修改失败，"+rs.msg);
				} else {
					jumbo.showMsg("修改失败，数据异常。");
				}
			} else {
				jumbo.showMsg("修改失败，数据异常。");
			}
			$j("#tbl_trans").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/queryTransportatorCfg.json")}).trigger("reloadGrid");
			initFrom();
		}
	});
});

function initFrom(){
	$j("#divTrans").removeClass("hidden");
	$j("#divDetial").addClass("hidden");
	$j("#divDetial input").val("");
}