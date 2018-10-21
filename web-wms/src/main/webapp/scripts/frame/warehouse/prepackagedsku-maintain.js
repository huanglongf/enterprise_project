$j.extend(loxia.regional['zh-CN'],{
	APPLY_FORM_SELECT : "请选择作业申请单",
	PRE_SKU_LIST:"现有预包装商品列表",
	SKU_LIST:"商品列表",
	OPERATOR:"操作",
	DELETE:"删除"
});
function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

//全局main_Sku_Id
var g_SkuId = null;

$j(document).ready(function(){
	$j("#div2").addClass("hidden");
	$j("#skuBarCode").focus();
	$j("#dialog_error_ok").dialog({title: "错误信息", modal:true, autoOpen: false, width: 400});
	$j("#tbl_main_list").jqGrid({
		url:$j("body").attr("contextpath") + "/getallprepackagedsku.json",
		datatype: "json",
		colNames: ["ID","商品编码","商品名称","商品条码",i18("OPERATOR")],
		colModel: [
				   {name: "id", index: "id", hidden: true},
		           {name: "code", index: "code", width:100,reziable:true,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}},
		           {name: "name", index: "name", width:200,resizable:true},         
		           {name: "barCode", index: "barCode", width: 100, resizable: true},
		           {name: "operator", width: 100, resizable: true,formatter:"buttonFmatter",align:"center",formatoptions:{"buttons":{label:i18("DELETE"), onclick:"deletePreSku(this,event);"}}}
		           ],
		caption: i18("PRE_SKU_LIST"),// 预包装商品列表
		pager:"#pager",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: false,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc",
		hidegrid:false,
		jsonReader: { repeatitems : false, id: "0" }
	});
	$j("#tbl_detail_list").jqGrid({
		datatype: "json",
		colNames: ["ID","商品编码","商品名称","商品条码","实际数量",i18("OPERATOR")],
		colModel: [
				   {name: "id", index: "id", hidden: true},
		           {name: "code", index: "code", width:100,reziable:true},
		           {name: "name", index: "name", width:200,resizable:true},
		           {name: "barCode", index: "barCode" ,width:200,hidden:false},
		           {name: "qty", index: "qty", width: 100, resizable: true},
		           {name: "operator", width: 100, resizable: true,formatter:"buttonFmatter",align:"center",formatoptions:{"buttons":{label:i18("DELETE"), onclick:"deletePreSku1(this,event);"}}}
		           ],
		caption: i18("SKU_LIST"),//商品列表
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: false,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc",
		hidegrid:false,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	//添加预包装商品
	$j("#btn-add_1").click(function(){
		var barCode = $j("#p_barCode").val();
		if(barCode.length==0){
			loxia.tip($j("#p_barCode"),"请输入商品条码！");
		}else{
			bc=$j.trim($j("#p_barCode").val());
			rsFlag=false;
			var data = $j("#tbl_main_list").jqGrid('getRowData');
			var item=null;
			var bc1=null;
			$j.each(data,function(i, item1){
				item = item1;
				bc1=item1.barCode;
				if(bc==bc1){
					rsFlag=true;
					loxia.tip($j("#p_barCode"),"预包装商品已经存在！");
					$j("#p_barCode").val("").focus();
					return;
				}
			});
				if(!rsFlag){
					var isExist = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/getSkuByBarCode.json",{"barCode":barCode});
					if(isExist!=null&&isExist.result=="success"){
						//添加预包装商品
						var aps = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/addPrepackagedSkuByBarCode.json",{"barCode":barCode});
						if(aps!=null&&aps.result=="success"){
							$j("#p_barCode").val("").focus();
							var postData = loxia._ajaxFormToObj("queryForm");
							$j("#tbl_main_list").jqGrid('setGridParam',{
								url:loxia.getTimeUrl($j("body").attr("contextpath")+"/getallprepackagedsku.json"),
								postData:postData,
							}).trigger("reloadGrid");
						}
					}else{
						loxia.tip($j("#p_barCode"),"没有该预包装商品，请重新输入！");
						$j("#p_barCode").val("").focus();
						return;
					}
					
				}
		}
	});
	
	//预包装商品列表的详细信息的添加
	$j("#btn-add_2").click(function(){
		var barCode = $j("#barcode").val();
		var qty = $j("#skuqty").val();
		if(barCode.length==0){
			loxia.tip($j("#barcode"),"请输入商品条码！");
			return;
		}else{
			if (qty != "" || qty.length != 0){
				if(!loxia.byId("skuqty").check()){
					loxia.tip($j("#skuqty"),"请填写正确数量！");
					return;
				}
				if($j("#skuqty").val()<0){
					loxia.tip($j("#skuqty"),"请填写正确数量！");
					return;
				}
			}else{
				loxia.tip($j("#skuqty"),"请输入商品件数！");
				return;
			}
			
			bc=$j.trim($j("#barcode").val());
			rsFlag=false;
			var data = $j("#tbl_detail_list").jqGrid('getRowData');
			var item=null;
			var bc1=null;
			$j.each(data,function(i, item1){
				item = item1;
				bc1=item1.barCode;
				if(bc==bc1){
					rsFlag=true;
					loxia.tip($j("#barcode"),"预包装商品已经存在！");
					$j("#barcode").val("").focus();
					$j("#skuqty").val("");
					return;
				}
			});
				if(!rsFlag){
					if(g_SkuId){
						//添加预包装商品详细商品列表
						var aps = loxia.syncXhrPost($j("body").attr("contextpath") + "/insertPrepackagedSkuBySkuIdAndSubBarCode.json",{"skuId":g_SkuId,"barCode":barCode,"qty":qty});
						if(aps!=null&&aps.result=="success"){
							showDe(g_SkuId);
							$j("#barcode").val("");
							$j("#skuqty").val("");
							//此操作需要加，避免数据库中有多余空数据
							//loxia.syncXhrPost($j("body").attr("contextpath") + "/deleteEmptyPrepackagedSkuByMainSkuId.json",{"skuId":g_SkuId});
						}else{
							loxia.tip($j("#barcode"),"没有该预包装商品，请重新输入！");
							$j("#barcode").val("").focus();
							$j("#skuqty").val("");
							return;
						}
					}
				}
		}
	});
	
	
	//返回
	$j("#backToPlList").click(function(){
		$j("#div1").removeClass("hidden");
		$j("#div2").addClass("hidden");
		$j("#skuBarCode").val("").focus();
	});
	
	$j("#btn-query").click(function(){
		var postData = loxia._ajaxFormToObj("queryForm");
		$j("#tbl_main_list").jqGrid('setGridParam',{
			url:loxia.getTimeUrl($j("body").attr("contextpath")+"/getallprepackagedsku.json"),
			postData:postData,
		}).trigger("reloadGrid");
	});
	
	$j("#reset").click(function(){
		$j("#queryForm input").val("");
	});
	
	$j("#skuBarCode").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			var barCode = $j("#skuBarCode").val();
			if(barCode.length==0){
				loxia.tip($j("#skuBarCode"),"请输入商品条码！");
			}else{
				var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/findprepackagedskubybarcode.json",{"barCode":barCode});
				if(rs!=null&&rs.result=="success"){
					g_SkuId = rs.id;
					showDe(rs.id);
				}else{
					loxia.tip($j("#skuBarCode"),"没有该预包装商品，请重新输入！");
					$j("#skuBarCode").val("").focus();
				}
			}
		}
	});
	
	//商品列表条形码
	$j("#scan_barCode").keydown(function(evt){
		if(evt.keyCode == 9){
			evt.preventDefault();
		}
		if(evt.keyCode === 13){
			evt.preventDefault();
			var barCode = $j("#scan_barCode").val();
			var qty = $j("#inQty").val();
			if(barCode.length==0){
				loxia.tip($j("#scan_barCode"),"请输入商品条码！");
				return;
			}else{
				if (qty != "" || qty.length != 0){
					if(!loxia.byId("inQty").check()){
						loxia.tip($j("#inQty"),"请填写正确数量！");
						return;
					}
					if($j("#inQty").val()<0){
						loxia.tip($j("#inQty"),"请填写正确数量！");
						return;
					}
				}else{
					loxia.tip($j("#inQty"),"请输入商品件数！");
					return;
				}
				
				bc=$j.trim($j("#scan_barCode").val());
				rsFlag=false;
				var data = $j("#tbl_detail_list").jqGrid('getRowData');
				var item=null;
				var bc1=null;
				$j.each(data,function(i, item1){
					item = item1;
					bc1=item1.barCode;
					if(bc==bc1){
						rsFlag=true;
						loxia.tip($j("#scan_barCode"),"预包装商品已经存在！");
						$j("#scan_barCode").val("").focus();
						return;
					}
				});
					if(!rsFlag){
						if(g_SkuId){
							//添加预包装商品详细商品列表
							var aps = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/insertPrepackagedSkuBySkuIdAndSubBarCode.json",{"skuId":g_SkuId,"barCode":barCode,"qty":qty});
							if(aps!=null&&aps.result=="success"){
								showDe(g_SkuId);
								$j("#scan_barCode").val("");
							}else{
								loxia.tip($j("#scan_barCode"),"没有该预包装商品，请重新输入！");
								$j("#scan_barCode").val("").focus();
								return;
							}
						}
					}
			}
		}
	});
	
	
	loxia.byId("inQty").setEnable(false);
	
	$j("#btnInQty").click(function(){
		loxia.byId("inQty").setEnable(true);
		$j("#inQty").select(); 
	});
	
	$j("#inQty").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			if(loxia.byId("inQty").check() == false){
				$j("#inQty").select(); 
			}else{
				loxia.byId("inQty").setEnable(false);
				$j("#barCode").select(); 
			}
		}
	});
});

//删除现有预包装商品列表
function deletePreSku(tag,event){
	var id = $j(tag).parents("tr").attr("id");
	var postData = {};
	//此处skuId即为mainSkuId
	postData["skuId"]=id;
	if(confirm("确定要删除?")){
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/deletePrepackagedSkuByMainSkuId.json",postData);
		if(rs!=null&&rs.result=="success"){
			var postData = loxia._ajaxFormToObj("queryForm");
			$j("#tbl_main_list").jqGrid('setGridParam',{
				url:loxia.getTimeUrl($j("body").attr("contextpath")+"/getallprepackagedsku.json"),
				postData:postData,
			}).trigger("reloadGrid");
		}else{
			jumbo.showMsg("删除预包装商品列表错误！");
		}
	}
}

//删除现有预包装商品列表子商品列表
function deletePreSku1(tag,event){
	var id = $j(tag).parents("tr").attr("id");
	var postData = {};
	//此处skuId即为mainSkuId
	postData["skuId"] = g_SkuId;
	postData["subSkuId"] = id;
	if(confirm("确定要删除?")){
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/deletePrepackagedSkuByMainSkuIdAndSubSkuId.json",postData);
		if(rs!=null&&rs.result=="success"){
			var postData={};
			postData["skuId"] = g_SkuId;
			$j("#tbl_detail_list").jqGrid('setGridParam',
					{url:window.$j("body").attr("contextpath")+"/findsubskubyidandou.json",
					postData:postData}
				).trigger("reloadGrid");
		}else{
			jumbo.showMsg("删除商品列表错误！");
		}
	}
}


//显示预包装商品列表详情
function showDetail(tag){
	var tr = $j(tag).parents("tr");
	var id = tr.attr("id"); 
	g_SkuId = id;
	showDe(id);
}

function showDe(param){
	$j("#div1").addClass("hidden");
	$j("#div2").removeClass("hidden");
	$j("#scan_barCode").val("").focus();
	$j("#skuBarCode").val("");
	loxia.byId("inQty").setEnable(false);
	var postData={};
	postData["skuId"] = param;
	$j("#tbl_detail_list").jqGrid('setGridParam',
			{url:window.$j("body").attr("contextpath")+"/findsubskubyidandou.json",
			postData:postData}
		).trigger("reloadGrid");
}