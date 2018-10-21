var extBarcode = null;
var outboundSn = null;
var getValues = null;
$j.extend(loxia.regional['zh-CN'],{
	
});

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}

var routeStatus={};

$j(document).ready(function (){
	$j("#dialog_update").dialog({title: "上架规则修改", modal:true, autoOpen: false, width: 900});
	
	// 初始化商品类型
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/auto/findSkuTypeJson.do");
	for(var idx in result){
	    	$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#skuType"));
	    	$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#skuTypeS"));
			$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#skuTypeU"));
		
	}
	
	// 初始化店铺信息
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/auto/findChannelByAutoWh.do");
	for(var idx in result){
			$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#channel"));
			$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#channelS"));
			$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#channelU"));
	}
	
	// 初始化弹出口信息
	var provinceList = loxia.syncXhrPost($j("body").attr("contextpath") + "/findpopAreaList.json");
	for(var j = 0 ; j < provinceList.areaList.length ; j++){
		$j("<option value='" + provinceList.areaList[j].areaCode + "'>"+ provinceList.areaList[j].areaName +"</option>").appendTo($j("#targetLocation"));
		$j("<option value='" + provinceList.areaList[j].areaCode + "'>"+ provinceList.areaList[j].areaName +"</option>").appendTo($j("#targetLocationS"));
		$j("<option value='" + provinceList.areaList[j].areaCode + "'>"+ provinceList.areaList[j].areaName +"</option>").appendTo($j("#targetLocationU"));
	}
	
	$j("#search").click(function(){
		var postData = {};
		$j("#tbl-inbound-purchase").jqGrid('setGridParam',{  
	        datatype:'json',  
	        postData:loxia._ajaxFormToObj("form_query")
	    }).trigger("reloadGrid"); //重新载入  
	});
		
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val(0);
	});
	
	$j("#tbl-inbound-purchase").jqGrid({
		url:$j("body").attr("contextpath")+"/json/findInboundRoleByParams.json",
		datatype: "json",
		colNames: ["ID","仓库","店铺","店铺ID","商品","SKU编码","商品上架类型","商品上架类型ID","库位编码","弹出口编码","弹出口名称","优先级","操作"],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},	
		           {name: "ouName", index: "ouName", hidden: false,sortable:false},
		           {name: "channelName", index: "channelName", hidden: false,sortable:false},
		           {name: "channelId", index: "channelId", hidden: true,sortable:false},
		           {name: "skuName", index: "skuName", hidden: false,sortable:false},	
		           {name: "skuCode", index: "skuCode", hidden: false,sortable:false},	
		           {name: "skuTypeName", index: "skuTypeName", hidden: false,sortable:false},
		           {name: "skuTypeId", index: "skuTypeId", hidden: true,sortable:false},
		           {name: "locationCode", index: "locationCode", hidden: false,sortable:false},
		           {name: "popUpAraeCode", index: "popUpAraeCode", hidden: false,sortable:false},
		           {name: "popUpAraeName", index: "popUpAraeName", hidden: false,sortable:false},
		           {name: "lv", index: "lv", hidden: false},
		           {name: "refSlipCode", width: 150, formatter:"buttonFmatter",sortable:false, formatoptions:{"buttons":{label:"编辑", onclick:"viewUpdate(this)"}}}
		           ],
		caption: '货箱定位规则',
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
		
		$j("#save1").click(function(){
			
			
			/*if($j("#channelS").val()==""){
				jumbo.showMsg("店铺不可为空！");
				return false;
			}*/
			if($j("#lvS").val()==""){
				jumbo.showMsg("优先级不可为空！");
				return false;
			}
			if(!/^[0-9]*$/.test($j("#lvS").val())){  
				jumbo.showMsg("优先级只能为数字！");
				return false;
			 } 
						
			if($j("#targetLocationS").val()=="" && $j("#locationCodeS").val()==""){
				jumbo.showMsg("弹出口和库位编码不可都为空！");
				return false;
			}
			
			if($j("#skuTypeS").val()==""&&$j("#skuCodeS").val()==""&&$j("#channelS").val()==""){
				jumbo.showMsg("店铺、商品上架类型和SKU编码不可都为空！");
				return false;
			}
			
			var flag=loxia.syncXhr($j("body").attr("contextpath") + "/auto/saveInboundRole.do",loxia._ajaxFormToObj("form_save"));
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
			
			if(!confirm("确认删除？")){
				return false;
			}
			
			var ids = $j("#tbl-inbound-purchase").jqGrid('getGridParam','selarrrow');
			
			var flag=loxia.syncXhr($j("body").attr("contextpath") + "/auto/deleteInboundRoleByIds.do",{"idStr":ids.toString()});
			if(flag!="" && flag["flag"]=="success"){
				jumbo.showMsg("删除成功！");
			}else{
				jumbo.showMsg("删除失败！");
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
			
			/*if($j("#channelU").val()==""){
				jumbo.showMsg("店铺不可为空！");
				return false;
			}*/
			if($j("#lvU").val()==""){
				jumbo.showMsg("优先级不可为空！");
				return false;
			}
			if(!/^[0-9]*$/.test($j("#lvU").val())){  
				jumbo.showMsg("优先级只能为数字！");
				return false;
			 }
			
			
			
			if($j("#skuTypeU").val()==""&&$j("#skuCodeU").val()==""&&$j("#channelU").val()==""){
				jumbo.showMsg("店铺、商品上架类型和SKU编码不可都为空！");
				return false;
			}
			if($j("#targetLocationU").val()=="" && $j("#locationCodeU").val()==""){
				jumbo.showMsg("弹出口和库位编码不可都为空！");
				return false;
			}
			
			var flag=loxia.syncXhr($j("body").attr("contextpath") + "/auto/updateInboundRoleByIds.do",loxia._ajaxFormToObj("form_update"));
			if(flag["flag"]=="success"){
				jumbo.showMsg("修改成功！");
				$j("#dialog_update").dialog("close");
			}else if(flag["flag"]=="error"){
				jumbo.showMsg("系统异常！");
			}else {
				jumbo.showMsg(flag["flag"]);
			}
			$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
				page:1}).trigger("reloadGrid");
		});
		
		$j("#import").click(function(){
			if(!/^.*\.xls$/.test($j("#inboundRoleFile").val())){
				jumbo.showMsg("请选择正确的Excel导入文件");
				return false;
			}
			loxia.lockPage();
			$j("#importForm").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/auto/inboundRoleImport.do")			);
			loxia.submitForm("importForm");
			$j("#search").trigger("click");
		});
		
});

function viewUpdate(obj){
	$j("#form_update input").val("");
	$j("#form_update select").val(0);
	
	
	/*$j("#div-sta-list").addClass("hidden");
	$j("#dialog_create").addClass("hidden");
	$j("#dialog_update").removeClass("hidden");*/
	var id = $j(obj).parents("tr").attr("id");
	var data = $j("#tbl-inbound-purchase").jqGrid('getRowData',id);
	
	$j("#channelU").val(data["channelId"]);
	$j("#skuTypeU").val(data["skuTypeId"]);
	$j("#skuCodeU").val(data["skuCode"]);
	$j("#locationCodeU").val(data["locationCode"]);
	$j("#targetLocationU").val(data["popUpAraeCode"]);
	$j("#lvU").val(data["lv"]);
	$j("#irId").val(id);
	 
	$j("#dialog_update").dialog("open");
}
function validate(lv){  
    var reg = new RegExp("^[0-9]*$");  
      
 if(!reg.test(obj.value)){  
     alert("请输入数字!");  
 }  
 if(!/^[0-9]*$/.test(obj.value)){  
     alert("请输入数字!");  
 }  
}  

