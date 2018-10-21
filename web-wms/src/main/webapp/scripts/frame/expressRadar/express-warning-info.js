var extBarcode = null;
var outboundSn = null;
var getValues = null;
$j.extend(loxia.regional['zh-CN'],{
	
});

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}


$j(document).ready(function (){
	
	
	// 初始化物流商信息
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransportator.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selLpcode"));
	}
	
		
	$j("#search").click(function(){
		var postData = loxia._ajaxFormToObj("form_query");  
		postData['sta.intType'] = 41;
		var url = $j("body").attr("contextpath")+"/json/stalist.json";
		$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
			url:url,postData:postData}).trigger("reloadGrid");
	});
		
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val(0);
	});
	
		$j("#tbl-inbound-purchase").jqGrid({
			url:$j("body").attr("contextpath")+"/stalist.json?sta.intType=41",
			datatype: "json",
			colNames: ["ID","预警类型","初始预警级别","备注","创建人","操作"],
			colModel: [
			           {name: "id", index: "id", hidden: true,sortable:false},		         
			           {name: "stvId", index: "stvId", hidden: false,sortable:false},		         
			           {name: "stvTotal", index: "stv_total", hidden: false,sortable:false},		         
			           {name: "stvMode", index: "stv_mode", hidden: false,sortable:false},		         
			           {name: "createTime", index: "create_time", width: 200, resizable: true,sortable:false},
			           {name: "refSlipCode", width: 150, formatter:"buttonFmatter",sortable:false, formatoptions:{"buttons":{label:"编辑", onclick:"warningSetting(this)"}}}
			           ],
			caption: '快递预警类型',
		   	sortname: 'sta.create_time',
		   	sortorder: "desc",
		   	multiselect: true,
		    rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
		   	pager:"#pager",
		   	height:jumbo.getHeight(),
			viewrecords: true,
	   		rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" }
		  
			});

});

function warningSetting(row){
	$j("#div-sta-list").addClass("hidden");
	$j("#dialog_create").addClass("hidden");
	$j("#div-warning-detail").removeClass("hidden");
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findPackageInfoList.json",{"sta.id":"14403102"});
	showLines(result);//初始编辑框中快递单号
	
	
}

//编辑对应快递单号  
function showLines(data){	
	var rs =[],header=getLocationsTableHeader(),foot=getLocationsTableFoot();
		var html=[],templete=getLocationsTableTemplete();
		html.push("<form id=\"logistics\" name=\"logistics\" >");
		html.push(header);
		html.push(getLocationsTable(data));
		html.push(templete);
		html.push(foot);
		html.push(" </form>");
    	rs.push(html.join(""));

 	  $j("#logistics").remove();//删除table编辑节点
	  $j("#settingListtable").after(rs.join(""));

	  loxia.initContext($j("#div-warning-detail"));
 }

//库位EditableTable表头
function getLocationsTableHeader(){	       
	    var html ="	<div loxiaType=\"edittable\" id=\"edittable\" style=\"width:280px;\" >"+
		"<table operation=\"add,delete\" append=\"0\" style=\"width:280px;\">"+
			"<thead>"+
				"<tr>"+
					"<th><input type=\"checkbox\" /></th>"+
					"<th>预警级别</th><th>有效时间（h）</th><th>预警说明</th>"+
				"</tr>"+
			"</thead>";
	    return html;
}
//快递单号编辑表格模板
function getLocationsTableTemplete(){
	      var tb2= " <tbody> "+
					"<tr >"+
					"<td><input type=\"checkbox\" /></td>"+
					"<td width=\"150\"><input  loxiaType=\"input\" trim=\"true\"  mandatory=\"true\" name=\"trackingNo\"/></td>"+
					"<td width=\"150\"><input  loxiaType=\"input\" trim=\"true\"  mandatory=\"true\" name=\"trackingNo\"/></td>"+
					"<td width=\"150\"><input  loxiaType=\"input\" trim=\"true\"  mandatory=\"true\" name=\"trackingNo\"/></td>"+
				"</tr>"+
			"</tbody> ";
	    
	    return tb2;
   }

//循环显示快递单号
function getLocationsTable(data){
	var tb=[];
	tb.push(" <tbody>");
	$j.each(data,function(i, item) {
		tb.push("<tr>");
		tb.push("<td><input type=\"checkbox\" /></td>");
		tb.push("<td width=\"150\"><input loxiaType=\"input\"  value=\""+item.trackingNo+"\"  trim=\"true\"  mandatory=\"true\" name=\"trackingNo\"/></td>");
		tb.push("<td width=\"150\"><input loxiaType=\"input\"  value=\""+item.trackingNo+"\"  trim=\"true\"  mandatory=\"true\" name=\"trackingNo\"/></td>");
		tb.push("<td width=\"150\"><input loxiaType=\"input\"  value=\""+item.trackingNo+"\"  trim=\"true\"  mandatory=\"true\" name=\"trackingNo\"/></td>");
		tb.push("</tr>");
	   	});
	tb.push(" </tbody>");
	return tb.join("");
}

//库位EditableTable.Foot
function getLocationsTableFoot(){
	var foot = "</table></div>";
	return foot;
}