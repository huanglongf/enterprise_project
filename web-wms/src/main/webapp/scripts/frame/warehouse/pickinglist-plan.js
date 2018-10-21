$j(document).ready(function(){
	//获取枚举类型
	//var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	$j("#jubNumber1").focus();
	$j("#tbl-orderList").jqGrid({
		url:$j("body").attr("contextpath") + "/findBatchAllocation.json",
		datatype: "json",
		colNames: ["id","配货批次号","配货状态","拣货状态","配货创建时间","拣货开始时间","拣货完成时间","登录账号","总商品件数"],
		colModel: [ 
		           {name: "id", index: "id", hidden: true},
		           {name: "code", index:"code", width: 100, resizable: true},
		           {name: "statusc", index:"statusc", width: 100, resizable: true},
		           {name: "nodeType", index:"nodeType", width: 100, resizable: true},
		           {name: "createTime", index:"createTime", width: 130, resizable: true},
		           {name: "startTime", index:"startTime", width: 130, resizable: true},
		           {name: "executionTime", index:"executionTime", width: 130, resizable: true},
		           {name: "jobNumber", index:"jobNumber",resizable:true},
		           {name: "quantity", index:"quantity",resizable:true}
		           ],
		caption: "配货批次分配列表",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	sortname: 'id',
	    pager: '#pager',
		sortorder: "desc", 
		height:'auto',
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
//		 onSelectRow: function (rowid, status) {
//			 var rowData = $j("#tbl-orderList").jqGrid("getRowData",rowid);
//			 var val= rowData.code;
//			 $j("#batchCode").val(val);
//         }
	});
	jumbo.bindTableExportBtn("tbl-orderList");	
	//查询
	$j("#search").click(function(){
		 var urlx = loxia.getTimeUrl($j("body").attr("contextpath") +"/findBatchAllocation.json");
   		 var postData=loxia._ajaxFormToObj("form_query");
  		 jQuery("#tbl-orderList").jqGrid('setGridParam',{url:urlx,postData:postData}).trigger("reloadGrid",[{page:1}]);
  		jumbo.bindTableExportBtn("tbl-orderList",{},urlx,loxia._ajaxFormToObj("form_query"));	
  		$j("#jubNumber1").focus();
	});
	//重置
	$j("#reset").click(function(){
		$j("#filterTable input").val("");
		$j("#select1").val("");
		$j("#select2").val("");
	});
	
	//确定 
	$j("#sure").click(function(){
		var jubNumber1 = $j("#jubNumber1").val();
		var pickType = $j("#pickType").val();
		var batchCode = $j("#batchCode").val();
		//alert(jubNumber+"--"+pickType+"--"+batchCode);
		if(jubNumber1 == null || jubNumber1.trim() == ""){
			jumbo.showMsg("工号不能为空！");
			return;
		}
		if(batchCode == null || batchCode.trim() == ""){
			jumbo.showMsg("批次号不能为空！");
			return;
		}
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/savePickNode.json?jubNumber1="+jubNumber1+"&pickType="+pickType+"&batchCode="+batchCode);
		if(rs && rs.msg == 'success'){
			jumbo.showMsg("操作成功！");
			 $j("#tbl-orderList").jqGrid('setGridParam').trigger("reloadGrid");
			$j("#jubNumber1").attr("value","");
			$j("#batchCode").attr("value","");
			$j("#batchStatus").attr("value","");
			$j("#isSure").attr("value","");
			$j("#jubNumber1").focus();
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
	//重置1
	$j("#reset1").click(function(){
		$j("#filterTable1 input").val("");
	});
	
	$j("#jubNumber1").keydown(function(evt){
		if(evt.keyCode === 13){
			$j("#batchCode").focus();
		}
	});
	$j("#batchCode").keydown(function(evt){
		if(evt.keyCode === 13){
			$j("#batchStatus").focus();
		}
	});
	$j("#batchStatus").keydown(function(evt){
		if(evt.keyCode === 13){
			$j("#isSure").focus();
		}
	});
	
	
	$j("#isSure").keydown(function(evt){
		if(evt.keyCode === 13){
			var code = $j("#isSure").val();
			if (code == "OK" || code == "ok"){
				$j("#sure").click();
			}
		}
	});
	
	$j("#batchStatus").keyup(function(){
		var code = $j("#batchStatus").val();
		if (code == "1"){
			 $j("#pickType ").attr("value","13");
		}else if (code == "2"){
			 $j("#pickType ").attr("value","14");
		}
	});
});