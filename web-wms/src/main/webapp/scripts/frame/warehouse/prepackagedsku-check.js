$j.extend(loxia.regional['zh-CN'],{
	APPLY_FORM_SELECT : "请选择作业申请单",
	PRE_SKU_LIST:"现有预包装商品列表",
	CHECK_SKU_LIST:"待核对商品列表"
});
function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
$j(document).ready(function(){
	$j("#div2").addClass("hidden");
	$j("#skuBarCode").focus();
	$j("#dialog_error_ok").dialog({title: "错误信息", modal:true, autoOpen: false, width: 400});
	$j("#tbl_main_list").jqGrid({
		url:$j("body").attr("contextpath") + "/getallprepackagedsku.json",
		datatype: "json",
		colNames: ["ID","商品编码","商品名称","商品条码"],
		colModel: [
				   {name: "id", index: "id", hidden: true},
		           {name: "code", index: "code", width:100,reziable:true,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}},
		           {name: "name", index: "name", width:200,resizable:true},         
		           {name: "barCode", index: "barCode", width: 100, resizable: true}
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
		colNames: ["ID","商品编码","商品名称","商品条码","实际数量","核对数量"],
		colModel: [
				   {name: "id", index: "id", hidden: true},
		           {name: "code", index: "code", width:100,reziable:true},
		           {name: "name", index: "name", width:200,resizable:true},
		           {name: "barCode", index: "barCode" ,width:200,hidden:true},
		           {name: "qty", index: "qty", width: 100, resizable: true},
		           {name: "completeQty",index:"completeQty",width:100,resizable:true}
		           ],
		caption: i18("CHECK_SKU_LIST"),// 待核对商品列表
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: false,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc",
		hidegrid:false,
		jsonReader: { repeatitems : false, id: "0" }
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
					showDe(rs.id);
				}else{
					loxia.tip($j("#skuBarCode"),"没有该预包装商品，请重新输入！");
					$j("#skuBarCode").val("").focus();
				}
			}
		}
	});
	$j("#barCode").keydown(function(evt){
		if(evt.keyCode == 9){
			evt.preventDefault();
		}
		if(evt.keyCode === 13){
			evt.preventDefault();
			var rowid = 0;
			bc=$j.trim($j(this).val());
			rs=false;
			if(bc.length==0)
				return false;
			var data = $j("#tbl_detail_list").jqGrid('getRowData');
			var item;
			var temp = null;
			var bc1;
			$j.each(data,function(i, item1){
				item = item1;
				bc1=item1.barCode;
				var num = item.completeQty;
				if(num==null||num=='')num=0;
				//00 条形码
				if(bc1==bc){
					if(parseInt(num)+parseInt($j("#inQty").val())<=item.qty){
						temp = item;
						$j("#tbl_detail_list tr[id="+item.id+"]").children("td:last").html(parseInt(num)+parseInt($j("#inQty").val()));
						rs=true;
						return false;
					}
				}
			});
			if(temp==null){
				errorTipOK("该商品在核对计划外!");
			}else{
				var flag=true;
				var data1 = $j("#tbl_detail_list").jqGrid('getRowData');
				$j.each(data1,function(i, item1){
					if(item1.qty!=item1.completeQty){
						flag = false;
						return false;
					}
				});
				if(flag){
					$j("#div2").addClass("hidden");
					$j("#div1").removeClass("hidden");
					$j("#skuBarCode").val("").focus();
				}else{
					$j("#barCode").val("").focus();
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
	$j("#dialog_error_close_ok").click(function(){
		colseDialog1();
	});
	$j("#okCode1").keyup(function(evt){
		var code = $j("#okCode1").val();
		if(BARCODE_CONFIRM ==code){
			colseDialog1();
		}
	})
});
function colseDialog1(){
	$j("#dialog_error_ok").dialog("close");
	$j("#barCode").val("").focus();
}
function errorTipOK(text) {
	$j("#error_text_ok").html("<font style='text-align:center;font-size:36px' color='red'>"+text+"</font>");
	$j("#dialog_error_ok").dialog("open");
	$j("#okCode1").val("").focus();
}
function showDetail(tag){
	var tr = $j(tag).parents("tr");
	var id = tr.attr("id"); 
	showDe(id);
}
function showDe(param){
	$j("#div1").addClass("hidden");
	$j("#div2").removeClass("hidden");
	$j("#barCode").val("").focus();
	loxia.byId("inQty").setEnable(false);
	var postData={};
	postData["skuId"] = param;
	$j("#tbl_detail_list").jqGrid('setGridParam',
			{url:window.$j("body").attr("contextpath")+"/findsubskubyidandou.json",
			postData:postData}
		).trigger("reloadGrid");
}