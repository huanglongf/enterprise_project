var extBarcode = null;
var outboundSn = null;
var getValues = null;
$j.extend(loxia.regional['zh-CN'],{
	
});

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}

var routeStatus={};

$j(document).ready(function (){
	
	$j("#tbl-inbound-purchase").jqGrid({
		url:$j("body").attr("contextpath")+"/json/findmsgtowcsbyparams.json",
		datatype: "json",
		colNames: ["ID","创建时间","接口类型","批次号","容器号","错误次数","错误日志","操作"],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},	
		           {name: "createTime", index: "createTime", hidden: false,sortable:false},
		           {name: "statusStr", index: "statusStr", hidden: false,sortable:false},
		           {name: "pickingListCode", index: "pickingListCode", hidden: false,sortable:false},	
		           {name: "containerCode", index: "containerCode", hidden: false,sortable:false},
		           {name: "errorCount", index: "errorCount", hidden: false,sortable:false},	
		           {name: "errorMsg", index: "errorMsg", hidden: false,sortable:false},
		           {name:"remove", width: 60, resizable: true,formatter:"buttonFmatter", formatoptions:{"buttons":{label:"删除", onclick:"removeData(this);"}}}
		           ],
		caption: 'WCS数据',
	   	//sortname: 'sta.create_time',
	   	//sortorder: "desc",
	   	//multiselect: true,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pager",
	   	height:jumbo.getHeight(),
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	  
	});
	
	/**
	 * 查询
	 */
	$j("#search").click(function(){
		var postData = {};
		$j("#tbl-inbound-purchase").jqGrid('setGridParam',{  
	        datatype:'json',  
	        postData:{
	        	"msgToWcsCommand.pickingListCode":$j("#pickingListCode").val(),
	        	"msgToWcsCommand.containerCode":$j("#containerCode").val()
	        } //发送数据  
	    }).trigger("reloadGrid",[{page : 1	}]); //重新载入  
	});
	
	/**
	 * 重设
	 */
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val(0);
	});
	
	/**
	 * 一键重置
	 */
	$j("#resetAll").click(function(){
		var ids = $j("#tbl-inbound-purchase").jqGrid('getDataIDs');
		if (null != ids && ids.length > 0) {
			if (confirm("确定重置所有错误次数？")) {
				var idStr = ids.join(',');
				var rs =loxia.syncXhr($j("body").attr("contextpath") + "/json/resetall.json");
				if(rs && rs.msg == "success"){
					jumbo.showMsg("重置成功!~");
				}else if (rs && rs.msg == "none") {
					jumbo.showMsg("请勾选需要操作的数据,谢谢!~");
				}else{
					jumbo.showMsg("系统异常,请联系管理员!~");
				}
			}
		}else {
			jumbo.showMsg("请勾选需要操作的数据,谢谢!~");
		}
		$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
			url:$j("body").attr("contextpath")+"/json/findmsgtowcsbyparams.json"}).trigger("reloadGrid",[{page:1}]);
	});
		
});

/**
 * 删除
 * @param obj
 * @param event
 */
function removeData(obj){
	var objId = $j(obj).parent().parent().attr("id");
	if (null != objId && objId.length > 0 && '' != objId) {
		if (confirm("确定删除本条数据？")) {
			var rs =loxia.syncXhr($j("body").attr("contextpath") + "/json/deletedatabyid.json?idStr=" + objId);
			if(rs && rs.msg == "success"){
				jumbo.showMsg("删除并保存日志成功!~");
			}else if (rs && rs.msg == "none") {
				jumbo.showMsg("无法获取数据ID,系统异常,请联系管理员!~");
			}else{
				jumbo.showMsg("系统异常,请联系管理员!~");
			}
		}
	}else {
		jumbo.showMsg("无法获取数据ID,系统异常,请联系管理员!~");
	}
	$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
		url:$j("body").attr("contextpath")+"/json/findmsgtowcsbyparams.json"}).trigger("reloadGrid",[{page:1}]);
}


