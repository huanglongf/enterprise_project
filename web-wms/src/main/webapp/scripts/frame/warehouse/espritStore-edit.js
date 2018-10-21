var extBarcode = null;
var outboundSn = null;
var getValues = null;
$j.extend(loxia.regional['zh-CN'],{
	
});

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}

var routeStatus={};

$j(document).ready(function (){
	$j("#dialog_create").dialog({title: "新建", modal:true, autoOpen: false, width: 620});
	$j("#dialog_update").dialog({title: "修改", modal:true, autoOpen: false, width: 620});

	
	$j("#search").click(function(){
		var postData = {};
		$j("#tbl-inbound-purchase").jqGrid('setGridParam',{  
	        datatype:'json',  
	        postData:{
	        	"espritCommand.name":$j("#name2").val(),
				"espritCommand.code":$j("#code2").val(),
				"espritCommand.cityCode":$j("#cityCode2").val(),
				"espritCommand.gln":$j("#gln2").val(),
				"espritCommand.cityGln":$j("#cityGln2").val(),
				"espritCommand.contacts":$j("#contacts2").val(),
				"espritCommand.telephone":$j("#telephone2").val()
	        } //发送数据  
	    }).trigger("reloadGrid",[{page : 1	}]); //重新载入  
	});
		
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val(0);
	});
	
	$j("#resetS").click(function(){//添加重置
		$j("#dialog_create input").val("");
		$j("#dialog_create select").val(0);
	});
	
	
	$j("#tbl-inbound-purchase").jqGrid({
		url:$j("body").attr("contextpath")+"/json/findEspritStoreByParams.json",
		datatype: "json",
		colNames: ["ID","门店名称","门店编码","门店GLN编码","城市编码","城市GLN编码","省","市","区","详细地址","联系人","电话","操作人"],
		colModel: [
		           {name: "id", index: "id", width: 70,hidden: true,sortable:false},	
		           {name: "name", index: "name",width: 70, hidden: false,sortable:false},
		           {name: "code", index: "code", width: 70,hidden: false,sortable:false},
		           {name: "gln", index: "gln",width: 70, hidden: false,sortable:false},	
		           {name: "cityCode", index: "cityCode",width: 70, hidden: false,sortable:false},
		           {name: "cityGln", index: "cityGln",width: 70, hidden: false,sortable:false},	
		           {name: "province", index: "province",width: 70, hidden: false,sortable:false},
		           {name: "city", index: "city",width: 70, hidden: false,sortable:false},	
		           {name: "district", index: "district",width: 70, hidden: false,sortable:false},
		           {name: "address", index: "address",width: 70, hidden: false,sortable:false},
		           {name: "contacts", index: "contacts",width: 70, hidden: false,sortable:false},
		           {name: "telephone", index: "telephone",width: 70, hidden: false,sortable:false},
		           {name: "userName", index: "userName",width: 70, hidden: false,sortable:false}
		           ],
		caption: '门店列表',
	   	multiselect: true,
	   	rowNum: 10,
		rowList:[10,20,40],
	   	pager:"#pager",
		sortname: 'id',
		sortorder: "desc", 
	   	height:"auto",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: {repeatitems : false, id: "0" }
		});
		
		$j("#save").click(function(){
			if($j("#nameS").val()==""){
				jumbo.showMsg("门店名称不可为空！");
				return false;
			}
			if($j("#codeS").val()==""){
				jumbo.showMsg("门店编码不可为空！");
				return false;
			}
			if($j("#cityCode").val()==""){
				jumbo.showMsg("城市编码不可为空！");
				return false;
			}

			if($j("#gln").val()==""){
				jumbo.showMsg("门店GLN编码不可以为空！");
				return false;
			}
			
			if($j("#cityGln").val()==""){
				jumbo.showMsg("城市GLN编码不可以为空！");
				return false;
			}
			
			if($j("#province").val()==""){
				jumbo.showMsg("省不可为空！");
				return false;
			}
			
			if($j("#city").val()==""){
				jumbo.showMsg("市不可为空！");
				return false;
			}
			
			if($j("#district").val()==""){
				jumbo.showMsg("区不可为空！");
				return false;
			}
			
			if($j("#address").val()==""){
				jumbo.showMsg("详细地址不可为空！");
				return false;
			}
			
			if($j("#contacts").val()==""){
				jumbo.showMsg("联系人不可为空！");
				return false;
			}
			
			if($j("#telephone").val()==""){
				jumbo.showMsg("电话不可以为空！");
				return false;
			}
			
			
			var postData = {
					"espritCommand.name":$j("#nameS").val(),
					"espritCommand.code":$j("#codeS").val(),
					"espritCommand.cityCode":$j("#cityCode").val(),
					"espritCommand.province":$j("#province").val(),
					"espritCommand.city":$j("#city").val(),
					"espritCommand.district":$j("#district").val(),
					"espritCommand.address":$j("#address").val(),
					"espritCommand.contacts":$j("#contacts").val(),
					"espritCommand.telephone":$j("#telephone").val(),
					"espritCommand.gln":$j("#gln").val(),
					"espritCommand.cityGln":$j("#cityGln").val(),
					"espritCommand.type":"1"
			};  
			var flag=loxia.syncXhr($j("body").attr("contextpath") + "/json/saveEspritStore.do",postData);
			
			if(flag["msg"]=="error"){
				jumbo.showMsg("系统异常！");
			}else if(flag["msg2"]=="1"){
				jumbo.showMsg("门店名称已存在！");
			}else if(flag["msg2"]=="2"){
				jumbo.showMsg("门店编码已存在！");
			}else if(flag["msg2"]=="3"){
				jumbo.showMsg("城市编码已存在！");
			}else if(flag["msg2"]=="4"){
				jumbo.showMsg("门店gln已存在！");
			}else if(flag["msg"]=="success"){
				jumbo.showMsg("保存成功！");
				$j("#dialog_create").hide();
				$j("#dialog_create").dialog("close");
				$j("#dialog_create input").val("");
				$j("#dialog_create select").val(0);
				$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
					page:1}).trigger("reloadGrid");
			}
			return false;
		});
		

		$j("#delete").click(function(){
			var ids = $j("#tbl-inbound-purchase").jqGrid('getGridParam','selarrrow');
			if(ids==''){
				jumbo.showMsg("请选择数据进行删除！");
				return;
			}
			var flag=loxia.syncXhr($j("body").attr("contextpath") + "/json/delEspritStore.do",{"idStr":ids.toString()});
			if(flag!="" && flag["msg"]=="success"){
				jumbo.showMsg("删除成功");
			}else{
				jumbo.showMsg("删除失败！");
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
			$j("#nameU").val(row.name);
			$j("#cityCodeU").val(row.cityCode);
			$j("#glnU").val(row.gln);
			$j("#cityGlnU").val(row.cityGln);
			$j("#provinceU").val(row.province);
			$j("#cityU").val(row.city);
			$j("#districtU").val(row.district);
			$j("#addressU").val(row.address);
			$j("#contactsU").val(row.contacts);
			$j("#telephoneU").val(row.telephone);
			$j("#dialog_update").dialog("open");
			
		});
		
		$j("#create").click(function(){
			$j("#dialog_create input").val("");
			$j("#dialog_create").dialog("open");
		});
		

		$j("#saveU").click(function(){
			if($j("#nameU").val()==""){
				jumbo.showMsg("门店名称不可为空！");
				return false;
			}
			if($j("#codeU").val()==""){
				jumbo.showMsg("门店编码不可为空！");
				return false;
			}
			if($j("#cityCodeU").val()==""){
				jumbo.showMsg("城市编码不可为空！");
				return false;
			}
			if($j("#glnU").val()==""){
				jumbo.showMsg("门店GLN编码不可以为空！");
				return false;
			}
			if($j("#cityGlnU").val()==""){
				jumbo.showMsg("城市GLN编码不可以为空！");
				return false;
			}
			
			if($j("#provinceU").val()==""){
				jumbo.showMsg("省不可为空！");
				return false;
			}
			
			if($j("#cityU").val()==""){
				jumbo.showMsg("市不可为空！");
				return false;
			}
			
			if($j("#districtU").val()==""){
				jumbo.showMsg("区不可为空！");
				return false;
			}
			
			if($j("#addressU").val()==""){
				jumbo.showMsg("详细地址不可为空！");
				return false;
			}
			
			if($j("#contactsU").val()==""){
				jumbo.showMsg("联系人不可为空！");
				return false;
			}
			
			if($j("#telephoneU").val()==""){
				jumbo.showMsg("电话不可以为空！");
				return false;
			}
			
			
			var postData = {
					"espritCommand.id":$j("#idU").val(),
					"espritCommand.name":$j("#nameU").val(),
					"espritCommand.code":$j("#codeU").val(),
					"espritCommand.cityCode":$j("#cityCodeU").val(),
					"espritCommand.province":$j("#provinceU").val(),
					"espritCommand.city":$j("#cityU").val(),
					"espritCommand.district":$j("#districtU").val(),
					"espritCommand.address":$j("#addressU").val(),
					"espritCommand.contacts":$j("#contactsU").val(),
					"espritCommand.telephone":$j("#telephoneU").val(),
					"espritCommand.gln":$j("#glnU").val(),
					"espritCommand.cityGln":$j("#cityGlnU").val(),
					"espritCommand.type":"2"
			};  
			var flag=loxia.syncXhr($j("body").attr("contextpath") + "/json/saveEspritStore.do",postData);
			
			if(flag["msg"]=="error"){
				jumbo.showMsg("系统异常！");
			}else if(flag["msg2"]=="1"){
				jumbo.showMsg("门店名称已存在！");
			}else if(flag["msg2"]=="2"){
				jumbo.showMsg("门店编码已存在！");
			}else if(flag["msg2"]=="3"){
				jumbo.showMsg("城市编码已存在！");
			}else if(flag["msg2"]=="4"){
				jumbo.showMsg("gln已存在！");
			}
			else if(flag["msg"]=="success"){
				jumbo.showMsg("保存成功！");
				$j("#dialog_update").hide();
				$j("#dialog_update").dialog("close");
				$j("#dialog_update input").val("");
				$j("#dialog_update select").val(0);
				$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
					page:1}).trigger("reloadGrid");
			}
			return false;
		
});

});
