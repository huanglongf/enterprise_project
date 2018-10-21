var $j = jQuery.noConflict();
var INCLUDE_SEL_ID = "";
var INCLUDE_SEL_VAL_CODE = "";
function initShopQuery(selId,valCode){
	INCLUDE_SEL_ID = selId;
	INCLUDE_SEL_VAL_CODE = valCode;
	if(INCLUDE_SEL_VAL_CODE =="innerShopCode"){
		INCLUDE_SEL_VAL_CODE = 'code';
	}
}
//初始化按钮
function initButton(){
	var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/getFileCreateStatus.json");
	if(rs.result=='success'){
	        if(rs.btnStatus!=null){
	        	//tab1
	        	if(JSON.parse(rs.btnStatus).snAndExpDateFlow=='1'){
	        		$j("#btn-create1").removeAttr("disabled","true");
					$j("#btn-create1").removeClass("hidden");
					$j("#btn-create-hidden1").addClass("hidden");
					$j("#btn-dowload1").removeClass("hidden");
					$j("#btn-dowload-hidden1").addClass("hidden");
					$j("#span1").addClass("hidden");
	        	}else{
	        		$j("#btn-create1").attr("disabled","true");
	        		$j("#btn-create1").addClass("hidden");
	        		$j("#btn-create-hidden1").removeClass("hidden");
	        		$j("#btn-dowload1").addClass("hidden");
	        		$j("#btn-dowload-hidden1").removeClass("hidden");
	        		$j("#span1").removeClass("hidden");
	        	}
	        	//tab2
	        	if(JSON.parse(rs.btnStatus).brandUnFinishedInstructions=='1'){
	        		$j("#btn-create2").removeAttr("disabled","true");
					$j("#btn-create2").removeClass("hidden");
					$j("#btn-create-hidden2").addClass("hidden");
					$j("#btn-dowload2").removeClass("hidden");
					$j("#btn-dowload-hidden2").addClass("hidden");
					$j("#span2").addClass("hidden");
	        	}else{
	        		$j("#btn-create2").attr("disabled","true");
	        		$j("#btn-create2").addClass("hidden");
	        		$j("#btn-create-hidden2").removeClass("hidden");
	        		$j("#btn-dowload2").addClass("hidden");
	        		$j("#btn-dowload-hidden2").removeClass("hidden");
	        		$j("#span2").removeClass("hidden");
	        	}
	        }else{
	        	jumbo.showMsg("初始化生成和下载按钮失败！");
	        }
		}else {
			if(rs.msg != null){
				jumbo.showMsg(rs.msg);
			}else{
				jumbo.showMsg("初始化生成和下载按钮失败！");
			}
		}
}

$j(document).ready(function (){
	//初始化按钮
	initButton();
	
	//tab拆分
	$j("#tabs").tabs();
	
	//初始化结束时间，默认当前
	$j("#c1").val(thisYear()); 
	$j("#e1").val(today()); 
	
	//动态加载平台店铺
	//tab1
	var shopLikeQuerys = loxia.syncXhrPost(window.$j("body").attr("contextpath") + "/getbichannelinfoall.json");
	$j("#shopLikeQuery").append("<option></option>");
	for(var idx in shopLikeQuerys){
		$j("#shopLikeQuery").append("<option value='"+ shopLikeQuerys[idx].code +"'>"+shopLikeQuerys[idx].name+"</option>");
	}
    $j("#shopLikeQuery").flexselect();
    $j("#shopLikeQuery").val("");
    
    //tab2
    var shopLikeQuerys2=shopLikeQuerys;
    $j("#shopLikeQuery2").append("<option></option>");
	for(var idx in shopLikeQuerys2){
		$j("#shopLikeQuery2").append("<option value='"+ shopLikeQuerys2[idx].code +"'>"+shopLikeQuerys2[idx].name+"</option>");
	}
    $j("#shopLikeQuery2").flexselect();
    $j("#shopLikeQuery2").val("");
    
	//$j("#clear").click(function(){
	//  $j("#shopLikeQuery_flexselect").val("");
	//  $j("#shopLikeQuery").val("");
	// });
    
    //tab1
    $j("#shopLikeQuery").change(function(){
    	if ($j("#shopLikeQuery").val() != ""){
    		var showShopList = $j("#showShopList").val();
    		var postshopInput = $j("#postshopInput").val();
    		var postshopInputName = $j("#postshopInputName").val();
    		var shopLikeQuerys = $j("#shopLikeQuery").val(); //模糊查询下拉框key
    		var shopLikeQuery = $j("#shopLikeQuery_flexselect").val(); //模糊查询下拉框value
    		var shoplist = "", postshop="",shoplistName="";
    		if (showShopList.indexOf(shopLikeQuery) < 0){
    			shoplist = shopLikeQuery + "; ";
				postshop = shopLikeQuerys+ ";";
				shoplistName = shopLikeQuery+ ";";
    		}  //不包含
    		$j("#showShopList").val(showShopList + shoplist);
    		$j("#postshopInput").val(postshopInput+ postshop);
    		$j("#postshopInputName").val(postshopInputName + shoplistName);
    	}
    });
    //tab2
    $j("#shopLikeQuery2").change(function(){
    	if ($j("#shopLikeQuery2").val() != ""){
    		var showShopList2 = $j("#showShopList2").val();
    		var postshopInput2 = $j("#postshopInput2").val();
    		var postshopInputName2 = $j("#postshopInputName2").val();
    		var shopLikeQuerys2 = $j("#shopLikeQuery2").val(); //模糊查询下拉框key
    		var shopLikeQuery2 = $j("#shopLikeQuery2_flexselect").val(); //模糊查询下拉框value
    		var shoplist2 = "", postshop2="",shoplistName2="";
    		if (showShopList2.indexOf(shopLikeQuery2) < 0){
    			shoplist2 = shopLikeQuery2 + "; ";
				postshop2 = shopLikeQuerys2+ ";";
				shoplistName2 = shopLikeQuery2+ ";";
    		}  //不包含
    		$j("#showShopList2").val(showShopList2 + shoplist2);
    		$j("#postshopInput2").val(postshopInput2+ postshop2);
    		$j("#postshopInputName2").val(postshopInputName2 + shoplistName2);
    		
    		//更新数据来源表格数据
    		$j("#edittable button[action='add']").trigger("click");
    		updateDataSourceTable();
    	}
    });
    //隐藏添加按钮
    //$j("#edittable button[action='add']").addClass("hidden");
    //删除按钮点击事件
    $j("#edittable button[action='delete']").click(function(){
		if($j("#edittable table tbody:eq(0) tr.ui-state-highlight").length==0){
			jumbo.showMsg(loxia.getLocaleMsg("请选择要删除的数据！"));	
		}
    });
	
   //店铺查询列表
    //tab1
    $j("#shopQueryDialog").dialog({title: "店铺查询", modal:true, autoOpen: false, width: 530});
    var baseUrl = $j("body").attr("contextpath");
    $j("#tbl_shop_query_dialog").jqGrid({
    	url:baseUrl + "/getbichannelinfoallpage.json",
    	datatype: "json",
    	colNames: ["id","innerShopCode","渠道名称","发票公司简称(英文)","品牌店铺映射编码"],
    	colModel: [ {name: "id", width: 100,hidden: true},
    	           {name: "code", hidden: true},
    	           {name: "name", index: "name", width: 180,  resizable: true},
    	           {name: "companyName", index: "companyName", width: 130,  resizable: true},
    	           {name: "vmiCode", index: "vmiCode", width: 130, resizable: true}
    	           ],
    	caption: "店铺列表",
    	rowNum: 15,
		rowList:[10,15,50,100,500,1000],
       	sortname: 'id',
        pager: '#tbl_shop_query_dialog_pager',
        multiselect: true,
        width:500,
    	shrinkToFit:false,
    	sortorder: "desc", 
    	height:'auto',
    	viewrecords: true,
   		rownumbers:true,
    	jsonReader: { repeatitems : false, id: "0" }
    });
    //tab2
    $j("#shopQueryDialog2").dialog({title: "店铺查询", modal:true, autoOpen: false, width: 530});
    //var baseUrl = $j("body").attr("contextpath");
    $j("#tbl_shop_query_dialog2").jqGrid({
    	url:baseUrl + "/getbichannelinfoallpage.json",
    	datatype: "json",
    	colNames: ["id","innerShopCode","渠道名称","发票公司简称(英文)","品牌店铺映射编码"],
    	colModel: [ {name: "id", width: 100,hidden: true},
    	           {name: "code", hidden: true},
    	           {name: "name", index: "name", width: 180,  resizable: true},
    	           {name: "companyName", index: "companyName", width: 130,  resizable: true},
    	           {name: "vmiCode", index: "vmiCode", width: 130, resizable: true}
    	           ],
    	caption: "店铺列表",
    	rowNum: 15,
		rowList:[10,15,50,100,500,1000],
       	sortname: 'id',
        pager: '#tbl_shop_query_dialog_pager2',
        multiselect: true,
        width:500,
    	shrinkToFit:false,
    	sortorder: "desc", 
    	height:'auto',
    	viewrecords: true,
   		rownumbers:true,
    	jsonReader: { repeatitems : false, id: "0" }
    });

	//根据查询条件进行查询，得到查询结果,可以进行店铺选择一系列操作
    //tab1
	$j("#btnSearchShop").click(function(){
		var showShopList = $j("#postshopInputName").val();
		showShopList = showShopList.substring(0,showShopList.length-1);
		var shopArray = showShopList.split(";");
		var ids = $j("#tbl_shop_query_dialog").jqGrid('getDataIDs');
		//获取模糊查询选中的店铺，循环判断勾选弹窗的checkBox
		for(var i = 0 ; i < shopArray.length; i++){  
			for(var j = 0 ; j < ids.length ; j++){
				var shopName= $j("#tbl_shop_query_dialog").getCell(ids[j],"name");
				if (shopName == shopArray[i]){
					$j("#tbl_shop_query_dialog").setSelection(ids[j],true);
				}
			}
		}
		$j("#shopQueryDialog").dialog("open");
	});	
	//tab2
	$j("#btnSearchShop2").click(function(){
		var showShopList2 = $j("#postshopInputName2").val();
		showShopList2 = showShopList2.substring(0,showShopList2.length-1);
		var shopArray2 = showShopList2.split(";");
		var ids2 = $j("#tbl_shop_query_dialog2").jqGrid('getDataIDs');
		//获取模糊查询选中的店铺，循环判断勾选弹窗的checkBox
		for(var i = 0 ; i < shopArray2.length; i++){  
			for(var j = 0 ; j < ids2.length ; j++){
				var shopName2= $j("#tbl_shop_query_dialog2").getCell(ids2[j],"name");
				if (shopName2 == shopArray2[i]){
					$j("#tbl_shop_query_dialog2").setSelection(ids2[j],true);
				}
			}
		}
		$j("#shopQueryDialog2").dialog("open");
	});	
	
	//店铺弹出页面查询按钮
	//tab1
	$j("#btnShopQuery").click(function(){
		var url = $j("body").attr("contextpath") + "/getbichannelinfoallpage.json";
		$j("#tbl_shop_query_dialog").jqGrid('setGridParam',
				{url:url,page:1,postData:loxia._ajaxFormToObj("shopQueryDialogForm")}).trigger("reloadGrid");
	});
	//tab2
	$j("#btnShopQuery2").click(function(){
		var url2 = $j("body").attr("contextpath") + "/getbichannelinfoallpage.json";
		$j("#tbl_shop_query_dialog2").jqGrid('setGridParam',
				{url:url2,page:1,postData:loxia._ajaxFormToObj("shopQueryDialogForm2")}).trigger("reloadGrid");
	});
	
	//店铺弹出页面重置按钮
	//tab1
	$j("#btnShopFormRest").click(function(){
		$j("#shopName").val("");
		var url = $j("body").attr("contextpath") + "/getbichannelinfoallpage.json";
		$j("#tbl_shop_query_dialog").jqGrid('setGridParam',
				{url:url,page:1,postData:loxia._ajaxFormToObj("shopQueryDialogForm")}).trigger("reloadGrid");
	});
	//tab2
	$j("#btnShopFormRest2").click(function(){
		$j("#shopName2").val("");
		var url = $j("body").attr("contextpath") + "/getbichannelinfoallpage.json";
		$j("#tbl_shop_query_dialog2").jqGrid('setGridParam',
				{url:url,page:1,postData:loxia._ajaxFormToObj("shopQueryDialogForm2")}).trigger("reloadGrid");
	});
	
	//店铺弹出页面取消按钮
	//tab1
	$j("#btnShopQueryClose").click(function(){
		$j("#shopQueryDialog").hide();
		$j("#shopQueryDialog").dialog("close");
	});
	//tab2
	$j("#btnShopQueryClose2").click(function(){
		$j("#shopQueryDialog2").hide();
		$j("#shopQueryDialog2").dialog("close");
	});

	//店铺弹出页面确认按钮
	//tab1
	$j("#btnShopQueryConfirm").click(function(){
		var ids = $j('#tbl_shop_query_dialog').jqGrid('getGridParam','selarrrow');
		var data = null, shoplist = "", postshop="",shoplistName="";
		if(ids.length>0){
			for(var i in ids){
				data = $j("#tbl_shop_query_dialog").jqGrid("getRowData",ids[i]);
				shoplist += data["name"] + "; ";
				shoplistName += data["name"] + ";";
				postshop += data["code"] + ";";
			}
		}else{
		}
		if (postshop && postshop.length > 0) {
			postshop = postshop.substring(0, postshop.length-1);
		}
		$j("#showShopList").val(shoplist);
		$j("#postshopInput").val(postshop);
		$j("#postshopInputName").val(shoplistName);
		$j("#shopQueryDialog").dialog("close");
		$j("#tbl_shop_query_dialog").trigger("reloadGrid");
	});
	//tab2
	$j("#btnShopQueryConfirm2").click(function(){
		var ids2 = $j('#tbl_shop_query_dialog2').jqGrid('getGridParam','selarrrow');
		var data2 = null, shoplist2 = "", postshop2="",shoplistName2="";
		if(ids2.length>0){
			//清空数据来源table
			$j.each($j(".ui-loxia-table table tr.add"),function(i,item){
				$j(this).remove();
			}); 
			for(var i in ids2){
				data2 = $j("#tbl_shop_query_dialog2").jqGrid("getRowData",ids2[i]);
				shoplist2 += data2["name"] + "; ";
				shoplistName2 += data2["name"] + ";";
				postshop2 += data2["code"] + ";";
				//数据来源table触发新增一行
				$j("#edittable button[action='add']").trigger("click");
			}
		}else{
		}
		if (postshop2 && postshop2.length > 0) {
			postshop2 = postshop2.substring(0, postshop2.length-1);
		}
		$j("#showShopList2").val(shoplist2);
		$j("#postshopInput2").val(postshop2);
		$j("#postshopInputName2").val(shoplistName2);
		$j("#shopQueryDialog2").dialog("close");
		$j("#tbl_shop_query_dialog2").trigger("reloadGrid");
		
		//更新数据来源表数据
		updateDataSourceTable();
	});
	
	//tab1生成按钮
	$j("#btn-create1").click(function(){
		jumbo.showMsg("数据生成中，请耐心等待!");
		//灰显
		$j("#btn-create1").attr("disabled","true");
		$j("#btn-create1").addClass("hidden");
		$j("#btn-create-hidden1").removeClass("hidden");
		$j("#btn-dowload1").addClass("hidden");
		$j("#btn-dowload-hidden1").removeClass("hidden");
		//延迟执行
		setTimeout("snAndValidDateSkuFlowCreate()","500");  
	});
	//tab1下载按钮
	$j("#btn-dowload1").click(function(){
		window.location.href = $j("body").attr("contextpath") + "/warehouse/downloadExportFile.do?fileName=snOrValidDateSkuFlow";
	});
	//tab1重置按钮
	$j("#reset1").click(function(){
		$j("#showShopList").val("");
		$j("#postshopInput").val("");
		$j("#postshopInputName").val("");
		$j("#slipcodes").val("");
		//window.location.reload();
	});
	
	//tab2初始化仓库信息tree
//	$j("#groupTree").tree({
//		url : $j("body").attr("contextpath")+"/grouptree.json",
//		checkbox:true,
//		animate:true,
//		onClick : function(node){
//			if($j("div.ui-layout-center").length>0){
//				$j("#groupType").html(node.attributes.type);
//				$j("#groupCode").html(node.attributes.code);
//				loxia.byId("groupName").val(node.attributes.name);
//				$j("#groupComment").val(node.attributes.comment);
//				if(node.attributes.available){
//					$j("#groupEnable option:first").attr("selected",true);
//				}else{
//					$j("#groupEnable option:last").attr("selected",true);
//				}
//				
//				loxia.asyncXhrPost($j("body").attr("contextpath") + "/findChildOUT.json",
//						{"operationUnit.id":node.id},
//						{
//						success:function (data) {
//							$j("#addOuType option").remove();
//							for(var i in data){
//								var option="<option value='"+data[i]['id']+"'>"+data[i]['displayName']+"</option>";
//								$j("#addOuType").append($j(option));
//						   }
//						}
//						});
//			}
//		},
//		onLoadSuccess: function(){
//			var ouId=window.parent.$j("#ouId").val();
//			$j("#groupTree div[node-id="+ouId+"]").addClass('tree-node-selected');
//			var node=$j("#groupTree").tree('getSelected');
//			if(node!=null){
//				$j("#groupType").html(node.attributes.type);
//				$j("#groupCode").html(node.attributes.code);
//				$j("#groupName").val(node.attributes.name);
//				$j("#groupComment").val(node.attributes.comment);
//				$j("#groupEnable").val(node.attributes.available);
//				
//				loxia.asyncXhrPost($j("body").attr("contextpath") + "/findChildOUT.json",
//						{"operationUnit.id":node.id},
//						{
//						success:function (data) {
//							$j("#addOuType option").remove();
//							for(var i in data){
//								var option="<option value='"+data[i]['id']+"'>"+data[i]['displayName']+"</option>";
//								$j("#addOuType").append($j(option));
//						   }
//						}
//						});
//			}
//			//var node=$j("#groupTree").tree('getRoot');
//		}
//	});
	
	//tab2生成按钮
	$j("#btn-create2").click(function(){
		jumbo.showMsg("数据生成中，请耐心等待!");
		//灰显
		$j("#btn-create2").attr("disabled","true");
		$j("#btn-create2").addClass("hidden");
		$j("#btn-create-hidden2").removeClass("hidden");
		$j("#btn-dowload2").addClass("hidden");
		$j("#btn-dowload-hidden2").removeClass("hidden");
		//延迟执行
		setTimeout("brandUnFinishedInstructions()","500"); 
	});
	//tab2下载按钮
	$j("#btn-dowload2").click(function(){
		window.location.href = $j("body").attr("contextpath") + "/warehouse/downloadExportFile.do?fileName=brandUnFinishedInstructions";
	});
	//tab2重置按钮
	$j("#reset2").click(function(){
		$j("#showShopList2").val("");
		$j("#postshopInput2").val("");
		$j("#postshopInputName2").val("");
		//清空数据来源table
		$j.each($j(".ui-loxia-table table tr.add"),function(i,item){
			$j(this).remove();
		}); 
		//window.location.reload();
	});
	
});

//获取sn和效期商品流水数据
function snAndValidDateSkuFlowCreate(){//构建方法
	var postData = loxia._ajaxFormToObj("form_info");
	var errorMsg = loxia.validateForm("form_info");
	if(errorMsg.length == 0){
	    var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/snAndValidDateSkuFlowCreate.json",postData);
	    if(rs.result=='success'){
	        jumbo.showMsg("数据生成成功！");
	        $j("#btn-create1").removeAttr("disabled","true");
			$j("#btn-create1").removeClass("hidden");
			$j("#btn-create-hidden1").addClass("hidden");
			$j("#btn-dowload1").removeClass("hidden");
			$j("#btn-dowload-hidden1").addClass("hidden");
	    }else {
	    	if(rs.msg != null){
				jumbo.showMsg(rs.msg);
			}else{
				jumbo.showMsg("操作失败！");
			}
	    	$j("#btn-create1").removeAttr("disabled","true");
			$j("#btn-create1").removeClass("hidden");
			$j("#btn-create-hidden1").addClass("hidden");
			$j("#btn-dowload1").removeClass("hidden");
			$j("#btn-dowload-hidden1").addClass("hidden");
	    }
	}else{
		jumbo.showMsg(errorMsg);
	}
}

//品牌未完成数据导出
function brandUnFinishedInstructions(){//构建方法
	//替代(#)为[#]
	$j.each($j(".ui-loxia-table table tr.add"),function(i,item){
		$j(this).find(":input").each(function(){
			$j(this).attr("name",$j(this).attr("name").replace(/\(.*\)/ig,"["+i+"]"));
		});
	});
	var postData = loxia._ajaxFormToObj("form_info2");
	var errorMsg = loxia.validateForm("form_info2");
	if(errorMsg.length == 0){
	    var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/brandUnFinishedInstructions.json",postData);
	    if(rs.result=='success'){
	        jumbo.showMsg("数据生成成功！");
	        $j("#btn-create2").removeAttr("disabled","true");
			$j("#btn-create2").removeClass("hidden");
			$j("#btn-create-hidden2").addClass("hidden");
			$j("#btn-dowload2").removeClass("hidden");
			$j("#btn-dowload-hidden2").addClass("hidden");
	    }else {
	    	if(rs.msg != null){
				jumbo.showMsg(rs.msg);
			}else{
				jumbo.showMsg("操作失败！");
			}
	    	$j("#btn-create2").removeAttr("disabled","true");
			$j("#btn-create2").removeClass("hidden");
			$j("#btn-create-hidden2").addClass("hidden");
			$j("#btn-dowload2").removeClass("hidden");
			$j("#btn-dowload-hidden2").addClass("hidden");
	    }
	}else{
		jumbo.showMsg(errorMsg);
	}
}

//获取当前时间
function today(){//构建方法
    var today=new Date();//new 出当前时间
    var h=today.getFullYear();//获取年
    var m=today.getMonth()+1;//获取月
    if(m<10) m="0"+m;
    var d=today.getDate();//获取日
    if(d<10) d="0"+d; 
    var mytime=today.toLocaleTimeString();
    return h+"-"+m+"-"+d+" "+mytime; //返回 年-月-日 时:分:秒
   // return h+"-"+m+"-"+d+" 00:00:00"; 
}
//获取当前年份时间
function thisYear(){//构建方法
    var today=new Date();//new 出当前时间
    var h=today.getFullYear();//获取年
    return h+"-01-01 00:00:00"; //返回 年
}
//更新数据来源表格数据
function updateDataSourceTable(){
	var showShopList2 = $j("#postshopInputName2").val();
	showShopList2 = showShopList2.substring(0,showShopList2.length-1);
	var shopArray2 = showShopList2.split(";");
	$j.each($j(".ui-loxia-table table tr.add"),function(i,item){
		$j(this).find(":input").each(function(){
			$j(this).attr("name",$j(this).attr("name").replace(/\(.*\)/ig,"["+i+"]"));
		});
		$j(this).find(":input[name='addList["+i+"].dataSourceShopName']").val(shopArray2[i]);
	}); 
}


