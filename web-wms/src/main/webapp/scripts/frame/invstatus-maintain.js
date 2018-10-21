$j.extend(loxia.regional['zh-CN'],{
	INNER_INVENTORY_STATUS_EXISTS : "系统内置库存状态列表行{0}已经存在相同的库存状态名称！",
	UNINNER_INVENTORY_STATUS_EXISTS : "系统非内置库存状态列表行{0}已经存在相同的库存状态名称！",
	CREATE_SUCCESS : "创建新的库存状态成功！",
	MODIFY_SUCCESS : "修改库存状态成功！",
	INVENTORY_STATUS_NAME : "库存状态名称",
	UPDATE : "修改",
	
	NAME : "库存状态名称",
	DESCRIPTION : "描述",
	ISFORSALE : "计入销售可用量",
	ISINCOST : "参与成本计算",
	ISAVAILABLE : "使用状态",
	IDFORBTN : "操作",
	ID : "操作",
	SYSTEM_STATUS_LIST : "系统内置库存状态列表",
	DEFINED_STATUS_LIST : "自定义库存状态列表",
	DISABLE_FAILURE:"该库存状态仍然有库存,不能被禁用"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

function modifyinvs(btn){
	$j("#tbl-no-invstatuslist").jqGrid("getGridParam", "selrow", $j(btn).parents("tr").attr("id"));
	document.getElementById("editForm").reset();
	var data = $j("#tbl-no-invstatuslist").jqGrid("getRowData", $j(btn).parents("tr").attr("id"));
	$j("#editForm #invId").val($j(btn).parents("tr").attr("id"));
	$j("#editForm #invName").val(data.name);
	$j("#editForm #invDesc").val(data.description);
	$j("#editForm #invIsForSale").attr("checked",data.isForSale=='true');
	$j("#editForm #invIsInCost").attr("checked",data.isInCost=='true');
	loxia.byId($j("#editForm #invIsAvailable")).val(data.isAvailable)
//	$j("#editForm #invIsAvailable option[value='"+data.isAvailable+"']").attr("selected",true);
	$j("#editdiv").removeClass("hidden");
	$j("#newdiv").addClass("hidden");
}
function existName(name,save){
	var b=false;
	$j.each($j("#tbl-yes-invstatuslist").jqGrid("getRowData"),function(i,e){
		if($j.trim(name)==e.name){
			b=true;
			jumbo.showMsg(loxia.getLocaleMsg("INNER_INVENTORY_STATUS_EXISTS",(i+1)));//系统内置库存状态列表行"+(i+1)+"已经存在相同的库存状态名称！
			return false;
		}
	});
	$j.each($j("#tbl-no-invstatuslist").jqGrid("getRowData"),function(i,e){
		if(save&&(e.id==$j("#invId").val())){
			//
		}else{
			if($j.trim(name)==e.name){
				b=true;
				jumbo.showMsg(loxia.getLocaleMsg("UNINNER_INVENTORY_STATUS_EXISTS",(i+1)));//系统非内置库存状态列表行"+(i+1)+"已经存在相同的库存状态名称！
				return false;
			}
		}
	});
	return b;
}

$j(document).ready(function (){
	var trueOrFalse=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"trueOrFalse"});
	var status=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"status"});
	if(!trueOrFalse.exception&&!status.exception){
		$j("#tbl-yes-invstatuslist").jqGrid({
			url:$j("body").attr("contextpath")+"/invstatussyslist.json",
			datatype: "json",
			//colNames: ["ID","库存状态名称","描述","计入销售可用量","参与成本计算","使用状态","操作"],
			colNames: ["ID",i18("NAME"),i18("DESCRIPTION"),i18("ISFORSALE"),i18("ISINCOST"),i18("ISAVAILABLE"),i18("IDFORBTN")],
			colModel: [{name: "id", index: "i.id", hidden: true},
			           {name: "name", index: "i.name", width: 120, resizable: true},
			           {name: "description", index: "i.description", width: 240, resizable: true},
					   {name: "isForSale", index: "i.isForSale", width: 120, resizable: true, formatter:'select',editoptions:trueOrFalse},
					   {name: "isInCost", index: "i.isInCost", width: 120, resizable: true, formatter:'select',editoptions:trueOrFalse},
					   {name: "isAvailable", index: "i.isAvailable", width: 120, resizable: true, formatter:'select',editoptions:status},
					   {name: "idForBtn", width: 120}
					   ],
			caption: i18("SYSTEM_STATUS_LIST"),//系统内置库存状态列表
	   	sortname: 'i.isAvailable asc,i.name',
		sortorder: "asc", 
		height:"auto",
		rowNum:-1,
		jsonReader: { repeatitems : false, id: "0" }
		});	
		$j("#tbl-no-invstatuslist").jqGrid({
			url:$j("body").attr("contextpath")+"/invstatusnosyslist.json",
			datatype: "json",
			//colNames: ["ID","库存状态名称","描述","计入销售可用量","参与成本计算","使用状态","操作"],
			colNames: ["ID",i18("NAME"),i18("DESCRIPTION"),i18("ISFORSALE"),i18("ISINCOST"),i18("ISAVAILABLE"),i18("ID")],
			colModel: [{name: "id", index: "i.id", hidden: true},
			           {name: "name", index: "i.name", width: 120, resizable: true},
			           {name: "description", index: "i.description", width: 240, resizable: true},
					   {name: "isForSale", index: "i.isForSale", width: 120, resizable: true,
			        	   formatter:'select',editoptions:trueOrFalse
			           },
					   {name: "isInCost", index: "i.isInCost", width: 120, resizable: true,
			        	   formatter:'select',editoptions:trueOrFalse},
					   {name: "isAvailable", index: "i.isAvailable", width: 120, resizable: true,
			        	   formatter:'select',editoptions:status},
					   {name: "id", width: 120, formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("UPDATE"), onclick:"modifyinvs(this);"}}}
					   ],
			caption: i18("DEFINED_STATUS_LIST"),//自定义库存状态列表
	   	sortname: 'i.isAvailable asc,i.name',
		sortorder: "asc", 
		rowNum:-1,
		height:"auto",
		gridComplete : function(){loxia.initContext($j(this));},
		jsonReader: { repeatitems : false, id: "0" },
		onSelectRow:function(id){
			
		}
		});	
	}else{
		jumbo.showMsg(loxia.getLocaleMsg("ERROR_INIT"));//初始化'系统参数'数据异常！
	}
	
	$j("#add").click(function(){
		if(!jumbo.max("addName",loxia.getLocaleMsg("INVENTORY_STATUS_NAME"),true)||existName($j("#addName").val(),false)){//"库存状态名称"
			return false;
		}
		loxia.lockPage();
		var addForm=loxia._ajaxFormToObj("addForm");
		addForm['inventoryStatus.isForSale']=$j("#isForSale").attr("checked");
		addForm['inventoryStatus.isInCost']=$j("#isInCost").attr("checked");
		loxia.asyncXhrPost($j("body").attr("contextpath") + "/createinvstatus.json",addForm,{
			success:function (data) {
				loxia.unlockPage();
				jumbo.showMsg(loxia.getLocaleMsg("CREATE_SUCCESS"));//创建新的库存状态成功！
				window.location=loxia.getTimeUrl($j("body").attr("contextpath")+"/warehouse/invstatusentry.do");
			}
		});
		
	});
	
	$j("#save").click(function(){
		if(!jumbo.max("invName",i18("NAME"),true)||existName($j("#invName").val(),true)){//库存状态名称
			return false;
		}
		loxia.lockPage();
		var editForm=loxia._ajaxFormToObj("editForm");
		editForm['inventoryStatus.isForSale']=$j("#invIsForSale").attr("checked");
		editForm['inventoryStatus.isInCost']=$j("#invIsInCost").attr("checked");
		editForm['inventoryStatus.id'] = $j("#invId").val();
		loxia.asyncXhrPost($j("body").attr("contextpath") + "/updateinvstatus.json",editForm,{
			success:function (data) {
				loxia.unlockPage();
				jumbo.showMsg(loxia.getLocaleMsg("MODIFY_SUCCESS"));//修改库存状态成功！
				window.location=loxia.getTimeUrl($j("body").attr("contextpath")+"/warehouse/invstatusentry.do");
			}
		});
	});
	$j("#btn-edit-cancel").click(function(){
		$j("#editdiv").addClass("hidden");
		$j("#newdiv").removeClass("hidden");
	});
	$j(document).bind("keypress",function(e){
		var ev=e;
		if(!ev){ ev=window.event; }  
		if(13==ev.keyCode){
			return false;
		}
	});
	$j("#editForm #invIsAvailable").bind("change",function(){
		var value=$j(this).val();
		if(value==true||value=="true")return;
		else{
			loxia.lockPage()
			loxia.asyncXhrPost($j("body").attr("contextpath") + "/checkfordisableinvstatus.json",
			{"inventoryStatus.id":$j("#invId").val()},{
				success:function (data) {
					loxia.unlockPage();
					if(data.enabled==false){
						jumbo.showMsg(i18("DISABLE_FAILURE"));
						$j("#editForm #invIsAvailable").val(true);
					}
				},
				error:function(data){
					loxia.unlockPage();
					jumbo.showMsg(i18("MSG_FAILURE"));
				}
			});
		}
	});
});
