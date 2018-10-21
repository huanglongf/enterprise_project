$j.extend(loxia.regional['zh-CN'],{
	SURE_MODIFY : "您确定要修改当前库区所有库位的库位容积和库位容积率吗？",
	UPDATE_SUCCESS : "库位容积、容积率更新成功！",
	UPDATE_FAILED : "库位容积、容积率更新失败！",
	
	CAPACITY : "库位容积（UNIT）",
	CAPARATIO : "库位容积率（%）",
	MANUALCODE : "手工编制库位编码",
	SYSCOMPILECODE : "系统编制库位编码",
	BARCODE : "库位条码",
	ISAVAILABLE : "库位使用状态",
	MEMO : "备注",
	LOCATION_LIST : "库位列表（操作员注意：不设定数字的库位表示不限容积，请自行确保空间可用性）"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

function openDistrict(district, focus) {
	var container = $j("#district-container").tabs();
	container.tabs("add",$j("body").attr("contextpath")+"/warehouse/locationsforupdateentry.do?district.id=" + district.id, district.code);		
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
	var $tabs = $j("#district-container").tabs();
	var status=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"status"});
	if(!status.exception){
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
					url:$j("body").attr("contextpath")+"/json/locationsofdistrictjson.json",
					datatype: "json",
					//colNames: ["ID","库位容积（UNIT）","库位容积率（%）","手工编制库位编码","系统编制库位编码","库位条码","库位使用状态","备注"],
					colNames: ["ID",i18("CAPACITY"),i18("CAPARATIO"),i18("MANUALCODE"),i18("SYSCOMPILECODE"),i18("BARCODE"),i18("ISAVAILABLE"),i18("MEMO")],
					colModel: [{name: "id", index: "l.id", hidden: true},
					           {name: "capacity", index: "l.capacity", formatter:"loxiaInputFmatter", formatoptions:{loxiaType:"number",min:"0"}, width: 160, resizable: true},
					           {name: "capaRatio", index: "l.capaRatio", formatter:"loxiaInputFmatter", formatoptions:{loxiaType:"number",mandatory:"true",min:"0",max:"100"}, width: 160, resizable: true},
					           {name: "manualCode", index: "l.manualCode", width: 120, resizable: true},
					           {name: "sysCompileCode", index: "l.sysCompileCode", width: 120, resizable: true},
							   {name: "barCode", index: "l.barcode", width: 120, resizable: true},						 
							   {name: "isAvailable", index: "l.isAvailable", width: 120, resizable: true,
								   formatter:'select',editoptions:status},
							   {name: "memo", index: "l.memo", width: 120, resizable: true}
							   ],
					caption: i18("LOCATION_LIST"),//库位列表（操作员注意：不设定数字的库位表示不限容积，请自行确保空间可用性）
					postData:{"district.id":$j("#disId",ui.panel).val(),"columns":"id,capacity,capaRatio,manualCode,sysCompileCode,barCode,isAvailable,memo"},
				multiselect: true,
				sortname: 'l.id',
				sortorder: "desc",
				height:jumbo.getHeight(),
				rowNum: jumbo.getPageSize(),
				rowList:jumbo.getPageSizeList(),
				pager:"#table-" + ui.index+"-pager",
				gridComplete : function(){loxia.initContext($j(this));},
				jsonReader: { repeatitems : false, id: "0" }
				});	
			}
		});
	}else{
		jumbo.showMsg(loxia.getLocaleMsg("ERROR_INIT"));//初始化'系统参数'数据异常！
	}
	
	$j("#btn-save").click(function(){
		var selected = $tabs.tabs('option', 'selected'); // => 0
		var formId = "form-" + selected;
		var errorMsg = loxia.validateForm(formId);
		if(errorMsg.length > 0){
			jumbo.showMsg(errorMsg);
		}else if(window.confirm(loxia.getLocaleMsg("SURE_MODIFY"))){//您确定要修改当前库区所有库位的库位容积和库位容积率吗？
			loxia.lockPage();
			var locations={};
			var tableId = "table-" + selected;
			$j.each($j("#" + tableId).jqGrid("getRowData"),function(i,e){
				locations['locations['+i+'].id']=e.id;
				if(isNaN(parseInt($j.trim(e.capacity),10))){
					locations['locations['+i+'].capacity']=0;
				}else{
					locations['locations['+i+'].capacity']=parseInt($j.trim(e.capacity),10);
				}
				if(isNaN(new Number($j.trim(e.capaRatio)))){
					locations['locations['+i+'].capaRatio']=0;
				}else{
					locations['locations['+i+'].capaRatio']=parseInt($j.trim(e.capaRatio),10);
				}
				
			});
			
			loxia.asyncXhrPost($j("body").attr("contextpath") + "/updatecapacity.json",locations,{
			success:function (data) {
				$tabs.tabs("load",selected);
				loxia.unlockPage();
				jumbo.showMsg(loxia.getLocaleMsg("UPDATE_SUCCESS"));//库位容积、容积率更新成功！
			   },
			error:function (data) {
				$tabs.tabs("load",selected);
				loxia.unlockPage();
				jumbo.showMsg(loxia.getLocaleMsg("UPDATE_FAILED"));//库位容积、容积率更新失败！
			   }
			});
		}
	});
	
	$j("#btn-batch-cap").click(function(){
		var selected = $tabs.tabs('option', 'selected'); // => 0
		var tableId = "table-" + selected;
		var rowIds = $j("#" + tableId).jqGrid('getGridParam','selarrrow');
		var value = loxia.byId("batch-cap").val() || "";
		for(var i=0; i< rowIds.length; i++){				
			loxia.byId($j("input[rowId='" + rowIds[i]+ "'][name='capacity']",$j("#" + tableId))[0]).val(value);
		}
	});
	
	$j("#btn-batch-cappa").click(function(){
		var selected = $tabs.tabs('option', 'selected'); // => 0
		var tableId = "table-" + selected;
		var rowIds = $j("#" + tableId).jqGrid('getGridParam','selarrrow');
		var value = loxia.byId("batch-cappa").val() || "";
		for(var i=0; i< rowIds.length; i++){			
			loxia.byId($j("input[rowId='" + rowIds[i]+ "'][name='capaRatio']",$j("#" + tableId))[0]).val(value);
		}
	});
	loxia.asyncXhrGet($j("body").attr("contextpath") + "/districtlist.json",{},{
		success:function (data) {
			openDistricts(data);
		   }
		});
		
});