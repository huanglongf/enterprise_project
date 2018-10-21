$j.extend(loxia.regional['zh-CN'],{
	ENTITY_INMODE				:	"上架批次处理模式",
	ENTITY_LOCATION				:	"库位",
	ENTITY_LOCATION_CAPACITY	:	"库容",
	ENTITY_LOCATION_CURRENT		:	"当前库存数",
	ENTITY_LOCATION_ADD			:	"上架数",
	MSG_LOCATION_CAPACITY		:	"不限",
	ENTITY_EXCELFILE	        :	"正确的excel导入文件",
	MSG_CONFIRM_CANCEL_STV		:	"如果返回，前面的操作将全部作废。您确定要返回吗?",
	MSG_ERROR_LOCATION_QTY		:	"当前库位库容不能满足当前上架数量!",
	MSG_ERROR_STALINE_QTY		:	"实际上架数量≠计划量",
	LABEL_TOTAL					:	"总计："
});
function divToggle(id){
	if($j(id).hasClass("hidden")){
		$j(id).removeClass("hidden");
	}else{
		$j(id).addClass("hidden");
	}
}
function staListToggle(){divToggle("#div-sta-list");}
function staToggle(){divToggle("#div-sta-detail");}
function stvToggle(){divToggle("#div-stv");}
function i18(msg, args){return loxia.getLocaleMsg(msg,args);}
$j(document).ready(function (){
	//初始化所有店铺
	jumbo.loadShopList("companyshop");
	initShopQuery("companyshop","innerShopCode");
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	var status=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"whSTAProcessStatus"});
	var trueOrFalse=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"trueOrFalse"});
	$j("#tbl-inbound-purchase").jqGrid({
		url:$j("body").attr("contextpath")+"/findStaProcurementReturnInboundList.json?isStvId="+false,
		datatype: "json",
		colNames: ["ID","stvId","作业单创建时间","作业单号","执行情况","相关单据号","负向采购单号","OWNER","店铺名",
			],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},	
		           {name: "stvId", index: "stvId", hidden: true,sortable:false},
		           {name: "createTime", index: "createTime", width: 200, resizable: true,sortable:false},
		           {name: "code", index: "code",formatter:"linkFmatter", formatoptions:{onclick:"showdetail"}, width: 200, resizable: true,sortable:false},
		           {name: "processStatus", index: "status", width: 100, resizable: true,formatter:'select',editoptions:status,sortable:false},
		           {name: "refSlipCode", index: "refSlipCode", width: 150, resizable: true,sortable:false},
		           {name: "slipCode2", index: "slipCode2", width: 150, resizable: true,sortable:false},
		           {name: "owner", index: "owner", hidden: true,sortable:false},
		           {name: "channelName", index: "channelName", width: 150, resizable: true,sortable:false},
	               ],
		caption: "采购退货入库列表",
	   	sortname: 'createTime',
	   	sortorder: "desc",
	   	multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		multiselect:true,
	   	pager:"#pager",
	   	height:jumbo.getHeight(),
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
		});
	
	// 导出执行上架模板
	$j("#export").click(function(){
		var staIds = $j("#tbl-inbound-purchase").jqGrid('getGridParam','selarrrow');
		var stvList="";
		if(staIds.length<=0){
			jumbo.showMsg("请选择导出单据！");
			return;
		}
		for(var i in staIds){
			 var data=$j("#tbl-inbound-purchase").jqGrid("getRowData",staIds[i]);
			if(i>0){
				if(""!=data["stvId"]){
					stvList = stvList +","+data["stvId"];
				}
			 }else{
				 if(""!=data["stvId"]){
						stvList = stvList +data["stvId"];
					}
			 }
		}
		var url = loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/exportPutawayTemplate.do?stvList="+stvList);
		$j("#upload").attr("src",url);
	});
	
	//批量导入上架
	$j("#expDiff").click(function(){
		var file=$j.trim($j("#staFile").val()),errors=[];
		if(file==""||file.indexOf("xls")==-1){
			errors.push(i18("INVALID_MAND",i18("ENTITY_EXCELFILE")));
		}
		var url = $j("body").attr("contextpath") + "/warehouse/importProcurementInbound.do";
		if(errors.length>0){
			jumbo.showMsg(errors.join("<br/>"));
		}else{
			loxia.lockPage();
			$j("#importForm").attr("action",loxia.getTimeUrl(url));
			$j("#importForm").submit();
		}
	});
	// 执行入库
	$j("#executeInventory").click(function(){			
		if(confirm(i18("MSG_CONFIRM"))){
			var stvId=$j("#stvId").val();
			var staId=$j("#staId").val();
			executeInventory(stvId,staId);
		}
	});
	
   //查询
	$j("#search").click(function(){
		var postData = loxia._ajaxFormToObj("form_query");
		postData["isStvId"]=false;
		var url = $j("body").attr("contextpath")+"/json/findStaProcurementReturnInboundList.json";
		$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
			url:url,page:1,postData:postData}).trigger("reloadGrid");
	});
	//重置
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val(0);
		$j("#shortcut_code").val("");
		$j("#isNeedInvoice").val("请选择");
	});
	//取消本次上架
	$j("#cancelStv").click(function(){
		if(confirm(i18("MSG_CONFIRM_CANCEL_STV"))){
			loxia.lockPage();
			loxia.asyncXhrPost($j("body").attr("contextpath") + "/cancelstv.json",
				{'stv.id':$j("#stvId").val()},
				{
				success:function (data) {
					loxia.unlockPage();
					if(data&&data.msg){
						jumbo.showMsg(data.msg);
					}
					window.location.reload();
				   },
				error:function(data){
					loxia.unlockPage();
					jumbo.showMsg(i18("MSG_FAILURE"));
					window.location.reload();
				}
				});
			
		}
	});
	 /**
	 * 打印上架商品情况
	 */
   $j("#printSkuInfo").click(function(){
	  var stvId=$j("#stvId").val();
  	  var staId=$j("#staId").val();
	  var postData = {};
	  
	  postData["stv.id"] = stvId;
	  jumbo.showMsg(i18("OPERATING")); 
	  loxia.lockPage();
	  loxia.syncXhrPost($j("body").attr("contextpath") + "/json/printpurchaseinfo.json",postData);
	  loxia.unlockPage();
   });
});

function showdetail(obj){
	$j("#div-sta-list").addClass("hidden");
	$j("#div-stv").removeClass("hidden");
	var staId=$j(obj).parent().parent().attr("id");
	var sta=$j("#tbl-inbound-purchase").jqGrid("getRowData",staId);
	$j("#staId").val(staId);
	$j("#stvId").val(sta['stvId']);
	$j("#nowNum").val(sta['stvTotal']);
	$j("#createTime3").val(sta.createTime);
	$j("#po3").val(sta.refSlipCode);
	$j("#staCode3").val(sta.code);
	$j("#owner3").val(sta.owner);
	$j("#supplier3").val(sta.supplier);
	$j("#status3").val($j(obj).parent().next().html());
	$j("#left3").val(sta.remnant);
	$j("#sta_memo").val(sta.returnReasonMemo);// 退换货原因备注
	var stvId=sta.stvId;
	loxia.lockPage();
	loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+"/json/stvinfo.json",{"stv.id":stvId},
		{
			success:function (data) {
			    if(data&&data.msg){
			    	loxia.unlockPage();
			    	jumbo.showMsg(data.msg);	
			    }else{
			    	showLines(data);
			    	loxia.unlockPage();
			    	loxia.initContext($j("#stvlineListtable"));
			    }
			   }, 
				error:function(data){
				   loxia.unlockPage();
				jumbo.showMsg(i18("MSG_FAILURE"));	
               }			   
		}); 	  
  }  


function executeInventory(stvId,staId){
	if(validateLocationQuantity()){
		var postData={},index=-1;
		$j.each($j("#stvlineList").nextAll(),function(i,tr){
			var skuId=$j(tr).attr("skuId"),staLineId=$j(tr).attr("staLineId"),owner=$j(tr).attr("owner"),invStatusId=$j(tr).attr("invStatusId"),skuCode=$j(tr).attr("skuCode");
			var batchCode = $j(tr).attr("batchCode");
			var isShelfManagement = $j(tr).attr("isShelfManagement");
			$j.each($j(tr).find("table tbody:eq(0) tr"),function(y,innerTr){
				if($j.trim($j(innerTr).find("input[name=quantity]").val())>0){
					index+=1;
					postData["stvlineList[" + index + "].location.code"]=$j.trim($j(innerTr).find("input[name=locationCode]").val());
					postData["stvlineList[" + index + "].quantity"]=$j.trim($j(innerTr).find("input[name=quantity]").val());
					postData["stvlineList[" + index + "].staLine.id"]=staLineId;
					postData["stvlineList[" + index + "].sku.id"]=skuId;
					postData["stvlineList[" + index + "].skuCode"]=skuCode;
					postData["stvlineList[" + index + "].batchCode"]=batchCode;
					if(invStatusId != "" && invStatusId != null){
						postData["stvlineList[" + index + "].invStatus.id"]=invStatusId;
					}
					postData["stvlineList[" + index + "].owner"]=owner;
					if(isShelfManagement == "1"){
						postData["stvlineList[" + index + "].strExpireDate"]=$j.trim($j(innerTr).find("input[name=strExpireDate]").val());
						postData["stvlineList[" + index + "].strPoductionDate"]=$j.trim($j(innerTr).find("input[name=strPoductionDate]").val());
					}
					if($j(innerTr).attr("id")){
						postData["stvlineList[" + index + "].id"]=$j(innerTr).attr("id");	
					}
				}
			});
		});
		postData["stv.id"]=stvId;
		executeinventorySend(postData)
	}
}
/**
 * 检查上架数量
 * 
 * @return {TypeName}
 */
function validateLocationQuantity(){
	var errMsg=[];
	var re = /^[1-9]+[0-9]*]*$/;
	$j.each($j("#stvlineList").nextAll(),function(i, tr) {
		if($j(tr).attr("total")!=$j(tr).find("#total").html()){
			errMsg.push($j(tr).find("td:eq(3)").html()+i18("MSG_ERROR_STALINE_QTY"));
		}
		var isShelfManagement = $j(tr).attr("isShelfManagement");
		$j.each($j(tr).find("tr:gt(0)"),function(i,e){
			var status=$j(e).find(".invStatus").val();
			var location=$j(e).find("input[name=locationCode]");
			var quantity=$j(e).find("input[name=quantity]");
			if((parseInt(i) + 1) < $j(tr).find("tr:gt(0)").length && isShelfManagement == "1"){
				var strPoductionDate = $j(e).find("input[name=strPoductionDate]").val().trim();
				var strExpireDate = $j(e).find("input[name=strExpireDate]").val().trim();
				if(strPoductionDate == "" && strExpireDate == ""){
					errMsg.push("["+$j(tr).find("td:eq(3)").html() + "]保质期商品必须填写生成日期或者过期时间!");
				} else if(strPoductionDate != "" && (strPoductionDate.length != 8 || !re.test(strPoductionDate))){
					errMsg.push("["+$j(tr).find("td:eq(3)").html() + "]生成日期格式不正确!");
				} else if(strExpireDate != "" && (strExpireDate.length != 8 || !re.test(strExpireDate))){
					errMsg.push("["+$j(tr).find("td:eq(3)").html() + "]过期时间格式不正确!");
				}
			}
			if(location&&$j.trim($j(location).val()).length>0&&quantity&&$j.trim($j(quantity).val()).length>0&&status==""){
				errMsg.push($j(tr).find("td:eq(3)").html()+i18("LABEL_LINE",i+1)+i18("INVALID_MAND",i18("ENTITY_INVENTORY_STATUS")));
			}
		});
	});
		if(errMsg.length>0){
			jumbo.showMsg(errMsg.join("<br />")); 
			return false;
		}
	return true;
}


function executeinventorySend(postData){
	loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+"/json/executeProcurementReturnPutaway.json",postData,
			{
				success:function (data) {
					if(data){
						if(data.result=="success"){
							jumbo.showMsg(i18("MSG_SUCCESS"));	
							window.location.reload();
						}else if(data.result=="error"){
							loxia.unlockPage();
							jumbo.showMsg(i18("MSG_FAILURE")+data["message"]);
						}
					}else{
						loxia.unlockPage();
						jumbo.showMsg(i18("MSG_FAILURE"));
					}
						
				}, 
		       error:function(){
					 jumbo.showMsg(i18("MSG_FAILURE"));	
				}			   
			}); 
}
function showLines(data){	
	var rs =[],header=getLocationsTableHeader(),foot=getLocationsTableFoot();
	$j("#stvlineListtable tr:gt(0)").remove();
	$j.each(data,function(i, line) {
		var html=[],templete=getLocationsTableTemplete(line);
		html.push(getSkuInfo(line));
		html.push("<form skuId=\""+line.skuId+"\" staLineId=\""+line.stalineId+"\" total=\""+line.quantity+"\"  owner=\""+line.owner+"\" >");
		html.push(header);
		html.push(getLocationsTable(line));
		html.push(templete);
		html.push(foot);
		html.push(" </form></td></tr>");
    	rs.push(html.join(""));
    	  });
	  $j("#stvlineListtable tr:gt(0)").remove();
	  $j("#stvlineList").after(rs.join(""));
	  loxia.initContext($j("#div-stv"));
 }

//库位EditableTable.Foot
function getLocationsTableFoot(){
	var foot = " <tfoot>"+
				" <tr>"+
				"<td colspan=\"6\" class=\"decimal\">"+i18("LABEL_TOTAL")+"</td> "+
					" <td class=\"decimal\" id=\"total\" decimal=\"0\"></td>"+
				"</tr>"+
			"</tfoot> </table></div>";
	return foot;
}

//库位EditableTable表头
function getLocationsTableHeader(){
	    var html ="	<div loxiaType=\"edittable\" class=\"district\">"+
		"<table operation=\"add,delete\" append=\"0\" width=\"550\">"+
			"<thead>"+
				"<tr>"+
					"<th><input type=\"checkbox\" /></th>"+
					"<th width=\"150\">"+i18("ENTITY_LOCATION")+"</th>"+
					"<th>"+i18("ENTITY_LOCATION_CAPACITY")+"</th>"+
					"<th>"+i18("ENTITY_LOCATION_CURRENT")+"</th>"+
					"<th width=\"80\">生产日期<br/>格式：20160101</th>"+
					"<th width=\"80\">过期时间<br/>格式：20160101</th>"+
					"<th width=\"80\">"+i18("ENTITY_LOCATION_ADD")+"</th>"+
				"</tr>"+
			"</thead>";
	    return html;
}
//新增库位-上架数量的模版
function getLocationsTableTemplete(line){
	      // var url=$j("body").attr("contextpath") +
			// "/findavaillocationforpurchase.json?sku.id="+line.skuId+"&stv.id="+$j("#stvId").val();;
	      var url=$j("body").attr("contextpath") + "/findallavaillocations.json";
	      var tb2= " <tbody> "+
					"<tr invStatusId=\""+line.invStatusId+"\" skuId=\""+line.skuId+"\" staLineId=\""+line.stalineId+"\"  owner=\""+line.owner+"\">"+
					"<td><input type=\"checkbox\" /></td>"+
					"<td width=\"150\"><input name='locationCode' loxiaType=\"input\" checkmaster=\"checklocation\" aclist=\""+url+"\" /></td>"+
					"<td>&nbsp;</td>"+
					"<td>&nbsp;</td>"+
					"<td width=\"80\"><input loxiaType=\"input\" name='strPoductionDate' /></td>"+
					"<td width=\"80\"><input loxiaType=\"input\" name='strExpireDate' /></td>"+
					"<td width=\"80\" class=\"decimal\"><input class=\"getcount\" name='quantity' checkmaster=\"checksjNum\" loxiaType=\"number\" aclist=\""+url+"\" /></td>"+
				"</tr>"+
			"</tbody> ";
	    
	    return tb2;
   }
////SKU信息
//function getSkuInfo(line){	 
//	var html=[];
//	html.push("<tr skuId=\""+line.skuId+"\" staLineId=\""+line.stalineId+"\" total=\""+line.quantity+"\"  owner=\""+line.owner+"\" batchCode=\""+line.batchCode+"\"skuCode=\""+line.skuCode+"\"  >");
//	html.push("<td>"+line.barCode+"</td>");
//	html.push("<td>"+line.skuCode+"</td>");
//    html.push("<td>"+(!line.keyProperties?'&nbsp;':line.keyProperties)+"</td>");
//    html.push("<td>"+line.skuName+"</td>");
//    html.push("<td>"+line.supplierCode+"</td>");
//    html.push("<td>"+(!line.intInvstatusName?'&nbsp;':line.intInvstatusName)+"</td>");
//    html.push("<td class=\"decimal\">"+line.quantity+"</td><td>");
//    return html.join("");
//}
//SKU信息
function getSkuInfo(line){	 
	var html=[];
	html.push("<tr invStatusId=\""+line.invStatusId+"\" skuId=\""+line.skuId+"\" isShelfManagement=\""+line.isShelfManagement
			+"\" strPoductionDate=\""+line.strPoductionDate+"\" strExpireDate=\""+line.strExpireDate +"\" validDate=\""+line.validDate
			+"\" staLineId=\""+line.stalineId+"\" total=\""+line.quantity+"\"  owner=\""+line.owner+"\" batchCode=\""+line.batchCode+"\" >");
	html.push("<td>"+line.barCode+"</td>");
	html.push("<td>"+line.skuCode+"</td>");
    html.push("<td>"+(!line.keyProperties?'&nbsp;':line.keyProperties)+"</td>");
    html.push("<td>"+line.skuName+"</td>");
    html.push("<td>"+line.supplierCode+"</td>");
    html.push("<td>"+(!line.intInvstatusName?'&nbsp;':line.intInvstatusName)+"</td>");
    html.push("<td class=\"decimal\">"+line.quantity+"</td><td>");
    return html.join("");
}


//库位校验
function checklocation(value,obj){
	var code=$j.trim(value);	
	if(code.length==0){
		return i18("INVALID_ENTITY_EMPTY",i18("ENTITY_LOCATION"));
	}
var data={};
data["stvid"]=$j("#stvId").val();
data["locationCode"]=code;
	var rs = loxia.syncXhr(window.parent.$j("body").attr("contextpath")+"/findlocationtranstypebycode.json",data);
 var ex = rs.exception;
 var result = rs.result;
 if(rs.exception){
		$j(obj.element).parent().next().html('');
 	$j(obj.element).parent().next().next().html('');
		return i18("INVALID_NOT_EXIST",i18("ENTITY_LOCATION"));
	}else{ 
		if (result == 'success'){
			$j(obj.element).parent().next().html(rs.location.capacity==0?i18("MSG_LOCATION_CAPACITY"):rs.location.capacity);
		    $j(obj.element).parent().next().next().html(rs.location.qty);
			return loxia.SUCCESS;
		}else if(result == 'error'){
			return rs.message;
		}
	}
}

function checksjNum(value,obj){
		$j("form[stalineid="+$j(obj.element).parent().parent().attr("stalineid")+"]").find("#total").html("");
	    var value=parseInt($j.trim(value)),ocu =$j(obj.element).parent().prev().text(),cap=$j(obj.element).parent().prev().prev().text();
	   if(isNaN(value)||value<0){
	    	return i18("INVALID_NUMBER");	
	    }
	   if(isNaN(cap)){
	    	return loxia.SUCCESS;
	    }
	    if(($j.trim(ocu).length==0&&cap<value)||($j.trim(ocu).length>0&&(cap-ocu<value))){
	   		return i18("MSG_ERROR_LOCATION_QTY");	
	    }
	   return loxia.SUCCESS;
   }

//循环显示库位编码-上架数量列表
function getLocationsTable(line){
	var tb=[];
	var url=$j("body").attr("contextpath") + "/findallavaillocations.json";
	tb.push(" <tbody>");
	$j.each(line.stvLines,function(i, item) {
		// var url=$j("body").attr("contextpath") +
		// "/findavaillocationforpurchase.json?sku.id="+line.skuId+"&stv.id="+$j("#stvId").val();;
		tb.push("<tr id=\""+item.id+"\" skuId=\""+line.skuId+"\" staLineId=\""+line.stalineId+"\"  owner=\""+line.owner+"\" >");
		tb.push("<td><input type=\"checkbox\" /></td>");
		var loc = item.locationCode == null ? '' : item.locationCode;
		tb.push("<td width=\"150\"><input name='locationCode' loxiaType=\"input\"  value=\""+loc+"\" checkmaster=\"checklocation\" aclist=\""+url+"\" /></td>");
		tb.push("<td>"+(item.locationAvailable==0||item.locationAvailable==null?i18("MSG_LOCATION_CAPACITY"):item.locationAvailable)+"</td>");
		tb.push("<td>"+(item.locationInventory==0?'':item.locationInventory)+"</td>");
		tb.push("<td width=\"80\"><input loxiaType=\"input\" name='strPoductionDate' /></td>");
		tb.push("<td width=\"80\"><input loxiaType=\"input\" name='strExpireDate' /></td>");
		tb.push("<td width=\"80\" class=\"decimal\"><input class=\"getcount\" name='quantity' value=\""+item.quantity+"\" checkmaster=\"checksjNum\"  loxiaType=\"number\" /></td>");
		tb.push("</tr>");
	   	});
	tb.push(" </tbody>");
	return tb.join("");	   
}