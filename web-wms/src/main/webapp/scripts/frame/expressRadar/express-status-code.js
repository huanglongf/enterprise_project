var extBarcode = null;
var outboundSn = null;
var getValues = null;
$j.extend(loxia.regional['zh-CN'],{
	
});

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}

var routeStatus={};

$j(document).ready(function (){
	
	
	// 初始化物流商信息
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransportator.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#lpcodeQ"));
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#lpcodeS"));
	}
	// 初始化代码信息
	var codeResult = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findSysRouteStatusCodeList.json");
	for(var idx in codeResult){
		$j("<option value='" + codeResult[idx].id + "'>"+ codeResult[idx].code +"</option>").appendTo($j("#sysRscId"));
		$j("<option value='" + codeResult[idx].id + "'>"+ codeResult[idx].code +"</option>").appendTo($j("#sysRscIdS"));
		routeStatus[codeResult[idx].id]=codeResult[idx].name;
	}
	
	$j("#search").click(function(){
		var postData = {};
		$j("#tbl-inbound-purchase").jqGrid('setGridParam',{  
	        datatype:'json',  
	        postData:{'logisticsCode':$j("#lpcodeQ").val(),
	        	'sysRscId':$j("#sysRscId").val(),
	        	'describe':$j("#describe").val()
	        } //发送数据  
	    }).trigger("reloadGrid"); //重新载入  
	});
		
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val(0);
	});
	
	$j("#tbl-inbound-purchase").jqGrid({
		url:$j("body").attr("contextpath")+"/json/findStatusCodeList.json",
		datatype: "json",
		colNames: ["ID","物流服务商","代码","描述","备注","操作"],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},		         
		           {name: "logisticsName", index: "logisticsName", hidden: false,sortable:false},		         
		           {name: "code", index: "code", hidden: false,sortable:false},		         
		           {name: "describe", index: "describe", hidden: false,sortable:false},		         
		           {name: "remark", index: "remark", width: 200, resizable: true,sortable:false},
		           {name: "refSlipCode", width: 150, formatter:"buttonFmatter",sortable:false, formatoptions:{"buttons":{label:"编辑", onclick:"viewUpdate(this)"}}}
		           ],
		caption: '快递状态一览表',
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
			var postData = {
					"rrsrCommand.expCode":$j("#lpcodeS").val(),
					"rrsrCommand.sysRscId":$j("#sysRscIdS").val(),
					"rrsrCommand.remark":$j("#remarkS").val()
			};  
			
			var flag=loxia.syncXhr($j("body").attr("contextpath") + "/ooc/saveStatusCode.do",postData);
			if(flag["flag"]=="success"){
				jumbo.showMsg("保存成功！");
			}else if(flag["flag"]=="exist"){
				jumbo.showMsg("数据已经存在！");
			}else{
				jumbo.showMsg("保存失败！");
			}
			$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
				page:1}).trigger("reloadGrid");
		});
		
		$j("#delete").click(function(){
			var ids = $j("#tbl-inbound-purchase").jqGrid('getGridParam','selarrrow');
			
			var flag=loxia.syncXhr($j("body").attr("contextpath") + "/ooc/deleteStatusCode.do",{"idStr":ids.toString()});
			if(flag["flag"]=="success"){
				jumbo.showMsg("删除成功！");
			}else{
				jumbo.showMsg("删除失败！");
			}
			$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
				page:1}).trigger("reloadGrid");
		});
		
		$j("#update").click(function(){
			var postData = {
					"idStr":$j("#idU").val(),
					"remark":$j("#remarkU").val()
					
			}; 
			var flag=loxia.syncXhr($j("body").attr("contextpath") + "/ooc/updateStatusCode.do",postData);
			if(flag["flag"]=="success"){
				jumbo.showMsg("修改成功！");
			}else{
				jumbo.showMsg("修改失败！");
			}
			$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
				page:1}).trigger("reloadGrid");
		});
		
		$j("#sysRscIdS").change(function(){
			$j("#describeS").val(routeStatus[$j("#sysRscIdS").val()]);
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