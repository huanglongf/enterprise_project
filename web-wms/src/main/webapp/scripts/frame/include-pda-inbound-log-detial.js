/**
 * to set this
 */
var include_pda_log_base_url;
var include_pda_log_pda_code;
var include_pda_log_sta_id;

function setPdaInit(baseurl,code,staid){
	include_pda_log_base_url = baseurl;
	include_pda_log_pda_code = code;
	include_pda_log_sta_id = staid;
}

function pdaLogReload(){
	var url=loxia.getTimeUrl(include_pda_log_base_url + "/findpdapostlogbysta.json?staid=" + include_pda_log_sta_id);
	$j("#pda_detial").jqGrid('setGridParam',{page:1,url:url}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("pda_detial",{},include_pda_log_base_url + "/findpdapostlogbysta.json",{staid:include_pda_log_sta_id});
}

$j(document).ready(function (){
	$j("#padLogTabs").tabs();
	$j("#pda_detial").jqGrid({
		datatype: "json",
		colNames: ["ID","PDA机器编码","创建时间","商品名称","商品条码","商品SKU编码","货号","库位","数量","机器入库汇总"],
		colModel: [{name: "id", index: "id", hidden: true},
		            {name:"pdaCode", index:"lg.pdaCode",width:100,resizable:true},
		            {name:"createTime", index:"lg.create_time",width:150,resizable:true},
					{name:"skuName", index:"skuName",width:150,resizable:true},
					{name:"skuBarcode", index:"skuBarcode",width:110,resizable:true},
					{name:"skuCode", index:"skuCode",width:110,resizable:true},
					{name:"skuSupplierCode", index:"skuSupplierCode",width:100,resizable:true},
					{name:"locCode", index:"locCode",width:100,resizable:true},
					{name:"qty", index:"qty",width:80,resizable:true},
					{name:"num", index:"num",width:80,resizable:true}
					],
		caption: "PDA上传记录",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	sortname: 'lg.id',
	   	height:'auto',
		pager: '#pda_detial_pager',
	    multiselect: true,
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#btnExePostLog").click(function(){
		var url = loxia.getTimeUrl(include_pda_log_base_url + "/exepdapostlog.json?staid=" +include_pda_log_sta_id);
		var rs = loxia.syncXhrPost(url);
		if(rs&&rs.result == 'success'){
			jumbo.showMsg("操作成功");
		}else{
			jumbo.showMsg(rs.message);
		}
	});
	 
	// 导出
	$j("#btnExpErrorLog").click(function(){
		var url = loxia.getTimeUrl(include_pda_log_base_url + "/exportpdaerrorlog.json?staid=" +include_pda_log_sta_id);
		$j("#pdaLogFrame").attr("src", url);
	});
	
	$j("#btnDelErrorLog").click(function(){
		var url = loxia.getTimeUrl(include_pda_log_base_url + "/delposterrorlog.json?staid=" +include_pda_log_sta_id);
		var rs = loxia.syncXhrPost(url);
		if(rs&&rs.result == 'success'){
			jumbo.showMsg("删除日志成功");
		}else{
			jumbo.showMsg(rs.message);
		}
	});
	
	$j("#pdaLogQuery").click(function(){
		var url = loxia.getTimeUrl(include_pda_log_base_url + "/findpdapostlogbysta.json?staid=" +include_pda_log_sta_id);
		$j("#pda_detial").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("pdaLogQueryForm")}).trigger("reloadGrid");
	});
	
	$j("#pdaLogQuseryReset").click(function(){
		$j("#pdaLogQueryForm input,#pdaLogQueryForm select").val("");
	});
	
	$j("#pdaLogExport").click(function(){
		var url = loxia.getTimeUrl(include_pda_log_base_url + "/warehouse/pdalogexport.do?code=" + include_pda_log_pda_code);
		$j("#pdaLogFrame").attr("src", url);
	});
	
	$j("#pdaLogDelete").click(function(){
		var url = loxia.getTimeUrl(include_pda_log_base_url + "/deletepdalog.json");
		var logids = $j("#pda_detial").jqGrid('getGridParam','selarrrow');
		if(logids.length>0){
			var postData = {};
			for(var i in logids){
				postData['logids[' + i + ']']=logids[i];
			}
			var rs = loxia.syncXhrPost(url,postData);
			if(rs&&rs.result == 'success'){
				$j("#pdaLogQuery").trigger("click");
				jumbo.showMsg("删除日志成功");
			}else{
				jumbo.showMsg(rs.msg);
			}
		}else{
			jumbo.showMsg("请选择需要删除的日志记录");
		}
	});
	
	$j("#pdaLogDeleteAll").click(function(){
		if(!window.confirm("确定清空单据["+include_pda_log_pda_code+"]全部未执行PDA操作日志?")){
			return ;
		}
		var url = loxia.getTimeUrl(include_pda_log_base_url + "/deleteallpdalog.json");
		var postData = {};
		postData['code']=include_pda_log_pda_code;
		var rs = loxia.syncXhrPost(url,postData);
		if(rs&&rs.result == 'success'){
			$j("#pdaLogQuery").trigger("click");
			jumbo.showMsg("删除日志成功");
		}else{
			jumbo.showMsg(rs.msg);
		}
	});

}); 
