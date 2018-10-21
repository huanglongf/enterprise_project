var extBarcode = null;
var outboundSn = null;
var getValues = null;
$j.extend(loxia.regional['zh-CN'],{
	
});

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}

var routeStatus={};

$j(document).ready(function (){
	
	
	
	$j("#search").click(function(){
		var postData = {};
		$j("#tbl-inbound-purchase").jqGrid('setGridParam',{  
	        datatype:'json',  
	        postData:{'skuType.name':$j("#name").val()
	        } //发送数据  
	    }).trigger("reloadGrid"); //重新载入  
	});
		
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val(0);
	});
	
	$j("#tbl-inbound-purchase").jqGrid({
		url:$j("body").attr("contextpath")+"/json/findSkuTypeByParams.json",
		datatype: "json",
		colNames: ["ID","类型名称","状态"],
		colModel: [	
		           {name: "id", index: "id", hidden: true,sortable:false},		         
		           {name: "name", index: "name", hidden: false,sortable:false},		         
		           {name: "status", index: "status", hidden: false,sortable:false,formatter:'select',editoptions:{value:"true:已禁用;false:使用中"}}
		           ],
		caption: '商品类型表',
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
				jumbo.showMsg("输入框不可为空！");
				return false;
			}
			
			var postData = {
					"skuType.name":$j("#nameS").val()
			};  
			
			var flag=loxia.syncXhr($j("body").attr("contextpath") + "/auto/saveSkuType.do",postData);
			if(flag["flag"]=="success"){
				jumbo.showMsg("保存成功！");
			}else{
				jumbo.showMsg(flag["flag"]);
			}
			$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
				page:1}).trigger("reloadGrid");
		});
		
		$j("#delete").click(function(){
			var ids = $j("#tbl-inbound-purchase").jqGrid('getGridParam','selarrrow');
			
			var flag=loxia.syncXhr($j("body").attr("contextpath") + "/auto/updateSkuType.do",{"idStr":ids.toString(),"status":true});
			if(flag!="" && flag["flag"]=="success"){
				jumbo.showMsg("禁用成功！");
			}else{
				jumbo.showMsg("禁用失败！");
			}
			$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
				page:1}).trigger("reloadGrid");
		});
		$j("#recover").click(function(){
			var ids = $j("#tbl-inbound-purchase").jqGrid('getGridParam','selarrrow');
			
			var flag=loxia.syncXhr($j("body").attr("contextpath") + "/auto/updateSkuType.do",{"idStr":ids.toString(),"status":false});
			if(flag!="" && flag["flag"]=="success"){
				jumbo.showMsg("已恢复使用！");
			}else{
				jumbo.showMsg("操作失败！");
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