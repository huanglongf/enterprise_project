var $j = jQuery.noConflict();

$j.extend(loxia.regional['zh-CN'],{
	MANIPULATE_DATA_SELECT : "请选择操作的数据",
	SURE_SAVE : "是否确定保存",
	MODE_SET_SUCCESS : "拣货模式设定成功！",
	MODE_SET_FAILED : "拣货模式设定失败！",
	
	INTSORTING_MODE : "拣货模式",
	MANUAL_CODE : "手工编制库位编码",
	SYSCOMPILE_CODE : "系统编制库位编码",
	BARCODE : "库位条码",
	ISABAILABLE : "库位使用状态",
	MEMO : "备注",
	LOCATION_LIST : "库位列表（如在一库区内不同库位设置不同捡货模式，请注意实际业务运作细节）"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

function openDistrict(district, focus) {
	var container = $j("#district-container").tabs();	
   container.tabs("add",window.parent.$j("body").attr("contextpath")+"/warehouse/includelocationtranstype.do?district.id=" + district.id, district.code);		
	var idx = container.find("li").length - 1;
	
	container.find("li:eq(" + idx + ")").attr("district", district.id);
	if(!!focus)
		container.tabs("select", idx);
};

function openDistricts(districts){
	if($j.isArray(districts)){
		for(var i=0,d;(d=districts[i]);i++)
			openDistrict(d);
	}else
		openDistrict(districts);
}

$j(document).ready(function (){
	var boollist = loxia.syncXhr(window.parent.$j("body").attr("contextpath")+"/formateroptions.json",{categoryCode:"status"});
	var $tabs = $j("#district-container");		
	$tabs.tabs({
		cache: true,
		load: function( event, ui ) {
			$j.each($tabs.find("li:not(.ui-state-active) a"),function(i,e){
					$j($j(this).attr("href")).addClass("ui-tabs-hide");
				});
			$j("table",ui.panel).attr("id","table-" + ui.index);
			$j("form",ui.panel).attr("id","form-" + ui.index);
			$j(".pager",ui.panel).attr("id","table-" + ui.index+"-pager");
			$j("#table-"+ui.index).jqGrid({
				url:window.parent.$j("body").attr("contextpath")+"/json/locationsofdistrictjson.json",
				datatype: "json",
				//colNames: ["ID","拣货模式","手工编制库位编码","系统编制库位编码","库位条码","库位使用状态","备注"],
				colNames: ["ID",i18("INTSORTING_MODE"),i18("MANUAL_CODE"),i18("SYSCOMPILE_CODE"),i18("BARCODE"),i18("ISABAILABLE"),i18("MEMO")],
				colModel: [{name: "id", index: "id", hidden: true},				           
				           {name: "intSortingMode", index: "intSortingMode", formatter:"selectFmatter", formatoptions:{templateSelect:"sel-pickup-model"}, width: 160, resizable: true,sortable:false},
				           {name: "manualCode", index: "manualCode", width: 120, resizable: true,sortable:false},
				           {name: "sysCompileCode", index: "sysCompileCode", width: 120, resizable: true,sortable:false},
						   {name: "barCode", index: "barCode", width: 120, resizable: true,sortable:false},						 
						   {name: "isAvailable", index: "isAvailable", width: 120, resizable: true,
							    formatter:'select',editoptions:boollist,sortable:false},
						   {name: "memo", index: "memo", width: 120, resizable: true,sortable:false}
						   ],
				caption: "库位列表",//库位列表（如在一库区内不同库位设置不同捡货模式，请注意实际业务运作细节）
			multiselect: true,	
			sortname: 'l.id',
			sortorder: "desc",
			height:jumbo.getHeight(),
			rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
			pager:"#table-" + ui.index+"-pager",
			postData:{"district.id":$j("#disId",ui.panel).val(),"columns":"id,intSortingMode,capaRatio,manualCode,sysCompileCode,barCode,isAvailable,memo"},
			gridComplete : function(){loxia.initContext($j(this));},
			jsonReader: { repeatitems : false, id: "0" }
			});	
		}
	});
	
		loxia.asyncXhrGet(window.parent.$j("body").attr("contextpath") + "/districtlist.json",{},{
		success:function (data) {
			openDistricts(data);
		   }
		});
	
	$j("#btn-batch-pickup").click(function(){
		var tableId = "table-" + $j("#district-container").tabs().tabs("option","selected");
		var rowIds = $j("#" + tableId).jqGrid('getGridParam','selarrrow');		
		if(rowIds.length==0){
			jumbo.showMsg(loxia.getLocaleMsg("MANIPULATE_DATA_SELECT"));//请选择操作的数据
			return ;
		}
		var value = loxia.byId("sel-pickup-model").val() || "";
		for(var i=0; i< rowIds.length; i++){		
			$j("select[rowId='" + rowIds[i]+ "'][name='intSortingMode']",$j("#" + tableId)).val(value);
		}
	});
	
	$j("#btn-save").click(function(){
		var tableId = "table-" + $j("#district-container").tabs().tabs("option","selected");
		var dx = $j("#"+tableId).jqGrid("getRowData");
		var data={};
	 	for(var i=0,d;(d=dx[i]);i++){
    		data["whLocationList[" + i + "].id"] = d.id;
    		data["whLocationList[" + i + "].intSortingMode"] = d.intSortingMode;
    	}
	 	
		 if(window.confirm(loxia.getLocaleMsg("SURE_SAVE"))){
		  loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+"/json/modifywarehouselocationlistpickupmode.json",
              data,
				{
			   success:function (data) {
			   if(data.rs=='true'){
			    $j("#"+tableId).trigger("reloadGrid");
			    jumbo.showMsg(loxia.getLocaleMsg("MODE_SET_SUCCESS"));//拣货模式设定成功！
			    }
                },
			   error:function(){
				 jumbo.showMsg(loxia.getLocaleMsg("MODE_SET_FAILED"));	//拣货模式设定失败！
               }
		    	});
		  
		  }
		
	});
	
});

