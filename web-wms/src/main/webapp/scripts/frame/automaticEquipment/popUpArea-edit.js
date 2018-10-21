var extBarcode = null;
var outboundSn = null;
var getValues = null;
$j.extend(loxia.regional['zh-CN'],{
	
});

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}

var routeStatus={};

$j(document).ready(function (){
	$j("#dialog_update").dialog({title: "弹出口修改", modal:true, autoOpen: false, width: 620});
	// 初始化物流商信息
	/*var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/auto/findAutoWh.do");
	for(var idx in result){
		
			$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#wh"));
			$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#whS"));
			$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#whU"));
		
	}*/
	
	$j("#search").click(function(){
		var postData = {};
		$j("#tbl-inbound-purchase").jqGrid('setGridParam',{  
	        datatype:'json',  
	        postData:{
	        	"popUpAreaCommand.code":$j("#code").val(),
	        	"popUpAreaCommand.barcode":$j("#barcode").val(),
	        	"popUpAreaCommand.wscPopCode":$j("#wscPopCode").val(),
				"popUpAreaCommand.name":$j("#name").val()
	        } //发送数据  
	    }).trigger("reloadGrid",[{page : 1	}]); //重新载入  
	});
		
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val(0);
	});
	
	$j("#tbl-inbound-purchase").jqGrid({
		url:$j("body").attr("contextpath")+"/json/findPopUpAreaByParams.json",
		datatype: "json",
		colNames: ["ID","编码","条码","弹出口名称","排序","wcs编码","状态"],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},	
		           {name: "code", index: "code", hidden: false,sortable:false},
		           {name: "barcode", index: "barcode", hidden: false,sortable:false},
		           {name: "name", index: "name", hidden: false,sortable:false},	
		           {name: "sort", index: "sort", hidden: false,sortable:false},
		           {name: "wscPopCode", index: "wscPopCode", hidden: false,sortable:false},	
		           {name: "status", index: "status", hidden: false,formatter:'select',editoptions:{value:"true:已禁用;false:使用中"},sortable:false}
		           ],
		caption: '弹出口区域',
	   	//sortname: 'sta.create_time',
	   	//sortorder: "desc",
	   	multiselect: true,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pager",
	   	height:jumbo.getHeight(),
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	  
		});
		
		$j("#save").click(function(){
			
			
			
			if($j("#nameS").val()==""){
				jumbo.showMsg("名称不可为空！");
				return false;
			}
			if($j("#codeS").val()==""){
				jumbo.showMsg("编码不可为空！");
				return false;
			}
			if($j("#barcodeS").val()==""){
				jumbo.showMsg("条码不可为空！");
				return false;
			}
			
			if($j("#sortS").val()==""){
				jumbo.showMsg("排序不可为空！");
				return false;
			}
			
			if(!/^[0-9]*$/.test($j("#sortS").val())){  
				jumbo.showMsg("排序只能为数字！");
				return false;
			 } 
			
			if($j("#wscPopCodeS").val()==""){
				jumbo.showMsg("WCS编码不可为空！");
				return false;
			}
			
			var postData = {
					"popUpAreaCommand.code":$j("#codeS").val(),
					"popUpAreaCommand.barcode":$j("#barcodeS").val(),
					"popUpAreaCommand.wscPopCode":$j("#wscPopCodeS").val(),
					"popUpAreaCommand.name":$j("#nameS").val(),
					"popUpAreaCommand.sort":$j("#sortS").val()
			};  
			
			var flag=loxia.syncXhr($j("body").attr("contextpath") + "/auto/savePopUpArea.do",postData);
			if(flag["flag"]=="success"){
				jumbo.showMsg("保存成功！");
				$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
					page:1}).trigger("reloadGrid");
			}else if(flag["flag"]=="error"){
				jumbo.showMsg("系统异常！");
			}else {
				jumbo.showMsg(flag["flag"]);
			}
			return false;
		});
		
		$j("#delete").click(function(){
			var ids = $j("#tbl-inbound-purchase").jqGrid('getGridParam','selarrrow');
			
			var flag=loxia.syncXhr($j("body").attr("contextpath") + "/auto/updatePopUpAreaByIds.do",{"idStr":ids.toString(),"status":true});
			if(flag!="" && flag["flag"]=="success"){
				jumbo.showMsg("禁用成功！");
			}else{
				jumbo.showMsg(flag["flag"]);
			}
			$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
				page:1}).trigger("reloadGrid");
		});
		$j("#recover").click(function(){
			var ids = $j("#tbl-inbound-purchase").jqGrid('getGridParam','selarrrow');
			
			var flag=loxia.syncXhr($j("body").attr("contextpath") + "/auto/updatePopUpAreaByIds.do",{"idStr":ids.toString(),"status":false});
			if(flag!="" && flag["flag"]=="success"){
				jumbo.showMsg("已恢复使用！");
			}else{
				jumbo.showMsg("操作失败！");
			}
			$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
				page:1}).trigger("reloadGrid");
		});
		
		
		
		$j("#update").click(function(){
			var ids = $j("#tbl-inbound-purchase").jqGrid('getGridParam','selarrrow');
			if(ids.length!=1){
				jumbo.showMsg("请选择一条数据进行修改！");
				return false;
			}
			var row = $j("#tbl-inbound-purchase").jqGrid('getRowData',ids);
			$j("#idU").val(ids);
			$j("#codeU").val(row.code);
			$j("#barcodeU").val(row.barcode);
			$j("#nameU").val(row.name);
			$j("#wscPopCodeU").val(row.wscPopCode);
			$j("#sortU").val(row.sort);
			$j("#dialog_update").dialog("open");
			
		});
		
		
		
		$j("#sysRscIdS").change(function(){
			$j("#describeS").val(routeStatus[$j("#sysRscIdS").val()]);
		});

		$j("#saveU").click(function(){
			
			if($j("#nameU").val()==""){
				jumbo.showMsg("名称不可为空！");
				return false;
			}
		
			
			if($j("#sortU").val()==""){
				jumbo.showMsg("排序不可为空！");
				return false;
			}
			
			if(!/^[0-9]*$/.test($j("#sortU").val())){  
				jumbo.showMsg("排序只能为数字！");
				return false;
			 } 
			
			if($j("#wscPopCodeU").val()==""){
				jumbo.showMsg("WCS编码不可为空！");
				return false;
			}
			
			var postData = {
					"popUpAreaCommand.id":$j("#idU").val(),
					"popUpAreaCommand.code":$j("#codeU").val(),
					"popUpAreaCommand.barcode":$j("#barcodeU").val(),
					"popUpAreaCommand.name":$j("#nameU").val(),
					"popUpAreaCommand.wscPopCode":$j("#wscPopCodeU").val(),
					"popUpAreaCommand.sort":$j("#sortU").val()
			};  
			
			var flag=loxia.syncXhr($j("body").attr("contextpath") + "/auto/updatePopUpArea.do",postData);
			if(flag["flag"]=="success"){
				jumbo.showMsg("保存成功！");
				$j("#dialog_update").dialog("close");
				
				$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
					page:1}).trigger("reloadGrid");
			}else if(flag["flag"]=="error"){
				jumbo.showMsg("系统异常！");
			}else {
				jumbo.showMsg(flag["flag"]);
			}
		});
		
		
});

function viewUpdate(obj){
	
	$j("#div-sta-list").addClass("hidden");
	$j("#dialog_create").addClass("hidden");
	$j("#dialog_update").removeClass("hidden");
	
	var id = $j(obj).parents("tr").attr("id");
	
	var data = $j("#tbl-inbound-purchase").jqGrid('getRowData',id);
	
	$j("#lpcodeU").val(data["logisticsName"]);
	$j("#sysRscIdU").val(data["code"]);
	$j("#idU").val(data["id"]);
	$j("#describeU").val(data["describe"]);
	$j("#remarkU").val(data["remark"]);
	
}
