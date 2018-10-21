$j(document).ready(function (){
	$j("#tabs").tabs({
		 select: function(event, ui) {
		  }
	});
	$j("#dialog1").dialog({title: "编辑", modal:true, autoOpen: false, width: 360, height: 360});
	$j("#dialog2").dialog({title: "编辑", modal:true, autoOpen: false, width: 360, height: 360});
	
	ruleOption();
	
	
	//重置
	$j("#reset1").click(function(){
		$j("#standardCode").val("");
		$j("#vehicleVolume1").val("");
		$j("#vehicleVolume2").val("");
	});
	$j("#reset2").click(function() {
		document.getElementById("queryForm2").reset();
	});
	
	
	//查询列表
	$j("#search1").click(function(){
		var url = $j("body").attr("contextpath") + "/json/findVehicleStandardList.json";
		$j("#tbl-list1").jqGrid('setGridParam',{url:url,page:1,
			postData:{
			"vehicleCode":$j("#vehicleCode").val(),	
        	"licensePlateNumber":$j("#licensePlateNumber").val(),
			"vehicleStandard":$j("#vehicleStandard").val()
			}
		}).trigger("reloadGrid");
	});
	$j("#search2").click(function() {
		
		
		var url = $j("body").attr("contextpath") + "/json/findLicensePlateList.json";
		$j("#tbl-list2").jqGrid('setGridParam',{url:url,page:1,
			postData:{
			"vehicleCode":$j("#vehicleCode").val(),	
        	"licensePlateNumber":$j("#licensePlateNumber").val(),
			"vehicleStandard2":$j("#ruleCode").val(),
			"vehicleVolume3":$j("#vehicleVolume3").val(),
			"vehicleVolume4":$j("#vehicleVolume4").val(),
			"useTime":$j("#useTime").val()
			}
		}).trigger("reloadGrid");
	});
	
	
	//新增
	$j("#add1").click(function(){
		$j("#dialog1").dialog("open");
		$j("#id1").val("");
		$j("#vStandardCode").val("");
		$j("#vVehicleVolume").val("");
	});
	$j("#add2").click(function(){
		$j("#dialog2").dialog("open");
		$j("#id2").val("");
		$j("#lvehicleCode").val("");
		$j("#llicensePlateNumber").val("");
		$j("#lvehicleStandard").val("");
		$j("#luseTime").val("");
	});
	
	//保存
	$j("#save1").click(function(){
		var id1 = $j("#id1").val();
		var standardCode=$j("#vStandardCode").val();
		var vehicleVolume=$j("#vVehicleVolume").val();
		var postData={};
		postData["vehicleStandard.standardCode"]=standardCode;
		postData["vehicleStandard.vehicleVolume"]=vehicleVolume;
		postData["vehicleStandard.id"]=id1;
		var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/saveOrUpdateVehicleVolume.do", postData);
		if(result.msg=="success"){
			jumbo.showMsg("保存成功");
			$j("#dialog1").dialog("close");
			var url = $j("body").attr("contextpath") + "/json/findVehicleStandardList.json";
			$j("#tbl-list1").jqGrid('setGridParam',{url:url,page:1,
				postData:{
				"standardCode":$j("#standardCode").val(),	
	        	"vehicleVolume1":$j("#vehicleVolume1").val(),
				"vehicleVolume2":$j("#vehicleVolume2").val()
				}
			}).trigger("reloadGrid");
			ruleOption();
		}else{
			jumbo.showMsg(result.msg);
		}
	});
	
	
	$j("#save2").click(function(){
		var id2 = $j("#id2").val();
		var vehicleCode=$j("#lvehicleCode").val();
		var licensePlateNumber=$j("#llicensePlateNumber").val();
		var vehicleStandard=$j("#lvehicleStandard").val();
		var useTime=$j("#luseTime").val();
		var postData={};
		postData["licensePlate.vehicleCode"]=vehicleCode;
		postData["licensePlate.licensePlateNumber"]=licensePlateNumber;
		postData["licensePlate.vehicleStandard"]=vehicleStandard;
		postData["licensePlate.useTime"]=useTime;
		postData["licensePlate.id"]=id2;
		var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/saveOrUpdateLicensePlate.do", postData);
		if(result.msg=="success"){
			jumbo.showMsg("保存成功");
			$j("#dialog2").dialog("close");
			var url2 = $j("body").attr("contextpath") + "/json/findLicensePlateList.json";
			$j("#tbl-list2").jqGrid('setGridParam',{url:url2,page:1,
				postData:{
					"vehicleCode":$j("#vehicleCode").val(),	
		        	"licensePlateNumber":$j("#licensePlateNumber").val(),
					"vehicleStandard2":$j("#ruleCode").val(),
					"vehicleVolume3":$j("#vehicleVolume3").val(),
					"vehicleVolume4":$j("#vehicleVolume4").val(),
					"useTime":$j("#useTime").val()
					}
			}).trigger("reloadGrid");
		}else{
			jumbo.showMsg(result.msg);
		}
	});
	
	//删除
	$j("#batchRemove1").click(function(){
		var ids = $j("#tbl-list1").jqGrid('getGridParam','selarrrow');
		if (null != ids && ids.length > 0) {
			for ( var i in ids) {
				var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/deleteVehicleVolumeById.do", {"id" : ids[i]});
				if (result.msg=="success") {
					jumbo.showMsg("删除成功");
					var url = $j("body").attr("contextpath") + "/findVehicleStandardList.json";
					$j("#tbl-list1").jqGrid('setGridParam',{url:url,page:1,
						postData:{
						"standardCode":$j("#standardCode").val(),	
			        	"vehicleVolume1":$j("#vehicleVolume1").val(),
						"vehicleVolume2":$j("#vehicleVolume2").val()
						}
					}).trigger("reloadGrid");
					ruleOption();
					}else{
						jumbo.showMsg("删除失败");
					}
				}
			}
		});
	
	$j("#batchRemove2").click(function(){
		var ids = $j("#tbl-list2").jqGrid('getGridParam','selarrrow');
		if (null != ids && ids.length > 0) {
			for ( var i in ids) {
				var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/deleteLicensePlateById.do", {"id" : ids[i]});
				if (result.msg=="success") {
					jumbo.showMsg("删除成功");
					var url = $j("body").attr("contextpath") + "/findLicensePlateList.json";
					$j("#tbl-list2").jqGrid('setGridParam',{url:url,page:1,
						postData:{
							"vehicleCode":$j("#vehicleCode").val(),	
				        	"licensePlateNumber":$j("#licensePlateNumber").val(),
							"vehicleStandard2":$j("#ruleCode").val(),
							"vehicleVolume3":$j("#vehicleVolume3").val(),
							"vehicleVolume4":$j("#vehicleVolume4").val(),
							"useTime":$j("#useTime").val()
							}	
					}).trigger("reloadGrid");
					}else{
						jumbo.showMsg("删除失败");
					}
				}
			}
		});
	
	
	$j("#tbl-list1").jqGrid({
		datatype: "json",
		colNames: ["ID","规格编码","车辆体积","操作"],
		colModel: [
				{name:"id", index:"id", hidden: true},
				{name:"standardCode", index:"standardCode", width:100, resizable:true},
				{name:"vehicleVolume",index:"vehicleVolume",width:100,resizable:true},
				{name:"idForBtn", width:80,resizable:true,formatter:"buttonFmatter", formatoptions:{"buttons":{label:"修改", onclick:"editVehicleStandard(this);"}}}],
		caption:"车辆规格列表",
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#pager1",	   
		sortorder: "sort",
		viewrecords: true,
   		rownumbers:true,
   		multiselect: true,
		jsonReader: { repeatitems : false, id:"0"},
	});
	
	$j("#tbl-list2").jqGrid({
		datatype: "json",
		colNames: ["ID","车辆标识","车牌号码","车辆规格","使用时间","操作"],
		colModel: [
				{name:"id", index:"id", hidden: true},
				{name:"vehicleCode", index:"vehicleCode", width:80, resizable:true},
				{name:"licensePlateNumber",index:"licensePlateNumber",width:80,resizable:true},
				{name:"vehicleStandard",index:"vehicleStandard",width:80,resizable:true},
				{name:"useTime", index:"useTime", width: 120, resizable: true},
				{name:"idForBtn", width:80,resizable:true,formatter:"buttonFmatter", formatoptions:{"buttons":{label:"修改", onclick:"editLicensePlate(this);"}}}],
		caption:"车型号配置表",
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#pager2",	   
		sortorder: "sort",
		viewrecords: true,
   		rownumbers:true,
   		multiselect: true,
		jsonReader: { repeatitems : false, id:"0"},
	});
});


//修改
function editVehicleStandard(obj){
	var ids = $j(obj).parent().siblings().eq(2).text();
	if (null != ids && ids.length > 0) {
		$j("#id1").val(ids);
		$j("#vStandardCode").val($j(obj).parent().siblings().eq(3).text());
		$j("#vVehicleVolume").val( $j(obj).parent().siblings().eq(4).text());
		$j("#dialog1").dialog("open");
	}
}; 
function editLicensePlate(obj){
	var ids2 = $j(obj).parent().siblings().eq(2).text();
	if (null != ids2 && ids2.length > 0) {
		$j("#id2").val(ids2);
		$j("#lvehicleCode").val($j(obj).parent().siblings().eq(3).text());
		$j("#llicensePlateNumber").val( $j(obj).parent().siblings().eq(4).text());
		$j("#lvehicleStandard").val($j(obj).parent().siblings().eq(5).text());
		$j("#luseTime").val($j(obj).parent().siblings().eq(6).text());
		$j("#dialog2").dialog("open");
	}
};


//选择框

function ruleOption(){
	$j("#ruleCode").empty();
	$j("#lvehicleStandard").empty();
	$j("<option value=''>请选择</option>").appendTo($j("#ruleCode"));
	$j("<option value=''>请选择</option>").appendTo($j("#lvehicleStandard"));
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/optionrulelist.json");
	if(result){
		for(var i in result){
			$j("<option value='"+result[i]+"'>"+result[i]+"</option>").appendTo($j("#ruleCode"));
			$j("<option value='"+result[i]+"'>"+result[i]+"</option>").appendTo($j("#lvehicleStandard"));
		}
	}
	
}
