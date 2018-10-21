$j.extend(loxia.regional['zh-CN'],{

});

$j(document).ready(function (){
	loxia.init({debug: true, region: 'zh-CN'});
	var baseUrl = window.$j("body").attr("contextpath");
	$j("#reset").click(function(){
		document.getElementById("query_form").reset();
	});
	var wareHouseResult = loxia.syncXhrPost(baseUrl + "/getVMIWarehouse.json");
	for(var idx in wareHouseResult){
		$j("<option value='" + wareHouseResult[idx].ouId + "'>"+ wareHouseResult[idx].name +"</option>").appendTo($j("#selVMIWarehouse"));
	}
	$j("#query").click(function(){
		 var ouId = $j("#selVMIWarehouse").val();
		    if(ouId == ""){
				jumbo.showMsg("请选择仓库");
			return false;
		 }
    	 var postData11 = {};
    	   if(""!=$j("#q_endDate").val()){
    		   postData11["msgout.endDate1"]=$j("#q_endDate").val();
    	   }
    	   if(""!=$j("#q_startDate").val()){
    		  
    		   postData11["msgout.starteDate1"]=$j("#q_startDate").val();
    	   }
    	   postData11["msgout.slipCode"]=$j("#q_slipCode").val();
    	   postData11["msgout.staCode"]=$j("#q_staCode").val();
    	   postData11["msgout.statusId"]=$j("#selectId").val();
    	   postData11["msgout.ouId"]=$j("#selVMIWarehouse").val();
    	   if(postData11["msgout.starteDate1"]==undefined){
    		   postData11["msgout.starteDate1"]="";
    	   }
    
    	   if(postData11["msgout.endDate1"]==undefined){
    		   postData11["msgout.endDate1"]="";
    	   }
		   $j("#tbl_rec").jqGrid('setGridParam',{
			   url:baseUrl + "/findOutboundOrderCancelListRoot.json",
			   postData:postData11,
			   page:1
		   }).trigger("reloadGrid");
		   jumbo.bindTableExportBtn("tbl_rec",{"statusId":"msgOrderStatus"},
					baseUrl + "/findOutboundOrderCancelListRoot.json",postData11);
	});
	
	
	function getDt(cellvalue,options,rowObject){
		return cellvalue.split(" ")[0];
	}
	var statusStr=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"msgOrderStatus"});
     $j("#tbl_rec").jqGrid({
 		datatype: "json",
 		colNames: ["ID","创建日期","作业单号","相关单据号","状态"],
 		colModel: [
            {name: "id", index: "id", hidden: true},		         
            {name: "createTime", index: "createTime", width: 150, resizable: true},
            {name: "staCode", index: "staCode", width: 150, resizable: true},
            {name: "slipCode", index: "slipCode", width: 150, resizable: true},
            {name: "statusId", index: "statusId", width: 60, resizable: true,formatter:'select',editoptions:statusStr}],
 		caption: "取消订单列表",
 		sortname: 'ID',
 		multiselect: false,
 	    height:"auto",
 	    rowNum: jumbo.getPageSize(),
 	    rowList:jumbo.getPageSizeList(),
 	    pager: "#tbl_rec_page",
 		sortorder: "desc",
 		viewrecords: true,
 			rownumbers:true,
 		jsonReader: { repeatitems : false, id: "0" }
	});
     jumbo.bindTableExportBtn("tbl_rec",{"statusId":"msgOrderStatus"},
				baseUrl + "/findOutboundOrderCancelListRoot.json"); 
	
     
    
     
     
});