var $j = jQuery.noConflict();

$j.extend(loxia.regional['zh-CN'],{
	CODE : "规则编码",
	NAME : "规则名称",
	CREATE_TIME : "创建时间",
	ROLE_LIST : "集货规则列表"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

var roleLineId = ""; // 规则明细ID

$j(document).ready(function (){
	//加载平台店铺
	var shopLikeQuerys = loxia.syncXhrPost(window.$j("body").attr("contextpath") + "/findshoplistbycompanyid.json");
	for(var idx in shopLikeQuerys){
		$j("<option value='" + shopLikeQuerys[idx].code + "'>"+ shopLikeQuerys[idx].name +"</option>").appendTo($j("#shopLikeQuery1"));
		$j("<option value='" + shopLikeQuerys[idx].code + "'>"+ shopLikeQuerys[idx].name +"</option>").appendTo($j("#shopLikeQuery"));
	}
	initShopQuery("companyshop","innerShopCode");
	
	//初始化O2O目的地编码
	var otoResult = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getallshopstoreforoption.do");
	for(var idx in otoResult){
		$j("<option value='" + otoResult[idx].code + "'>"+ otoResult[idx].name +"</option>").appendTo($j("#otoLocation"));
	}
	
	// 加载物流商
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransactionTypeName.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selLpcode"));
	}
	
	$j("#dialog_addRoleLine").dialog({title: "修改规则明细", modal:true,closeOnEscape:false, autoOpen: false, width: 700});//弹窗属性设置
	//查询
	$j("#search").click(function(){
		 var postData = loxia._ajaxFormToObj("form_query");  
		 postData["menuType"] = 1;
			$j("#tbl-checkSpace-role-line").jqGrid('setGridParam',{
				url:$j("body").attr("contextpath")+"/findlAllCheckSpace.json",page:1,postData:postData}).trigger("reloadGrid");
		});
	
	//重置
	$j("#reset").click(function(){
		$j("#form_query select").val("");
	});

	$j("#tbl-checkSpace-role-line").jqGrid({
		url:window.$j("body").attr("contextpath")+"/json/findlAllCheckSpace.do?menuType=1",
		datatype: "json",
		colNames: ["ID","","编码","店铺","工作台编码","是否QS","是否特殊处理","OTO门店","优先发货城市","物流商编码","时效类型","指定商品","优先级","是否预售编码","是否预售","修改行","删除行"],
		colModel: [
							{name: "id", index:"id",hidden: true,width:80,resizable:true},
							{name: "transType", index:"transType",hidden: true,width:80,resizable:true},
							{name:"code",index:"code", width:100,resizable: true,sortable:false},
							{name:"ownerName",index:"ownerName",width:200,resizable:true},
							{name:"checkingAreaCode",index:"checkingAreaCode",width:100,resizable:true},
							{name:"isQsStr",index:"isQsStr",width:100,resizable:true},
							{name:"isSpecStr",index:"isSpecStr",width:100,resizable:true},
							{name:"toLocationName",index:"toLocationName",width:150,resizable:true},
							{name:"city",index:"city",width:150,resizable:true},
							{name:"lpcode",index:"lpcode",width:100,resizable:true},
							{name:"transTimeTypeStr",index:"transTimeTypeStr",width:100,resizable:true},
							{name:"skuCodes",index:"skuCodes",width:100,resizable:true},
							{name:"lv",index:"lv",width:100,resizable:true},
							{name:"isPreSale",index:"isPreSale",width:100,hidden: true,resizable:true},
							{name:"isPreSaleStr",index:"isPreSaleStr",width:100,resizable:true},
							{name:"btnUpdate",index:"btnUpdate",width:90,resizable:true},
							{name:"btnDelete",index:"btnDelete",width:90,resizable:true}
				 		],
     	caption: "复核工作台规则",
    	sortname: 'lv',
	   	pager:"#pager",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
    	multiselect: false,
    	height:"auto",
    	width:1050,
    	rownumbers:true,
    	 viewrecords: true,
		jsonReader: {repeatitems : false, id: "0" },
		gridComplete: function(){
			var btn1 = '<div><button type="button" style="width:80px;" class="confirm" name="btnUpdate" loxiaType="button" onclick="updateRoleLine(this);">'+"修改"+'</button></div>';
			var btn2 = '<div><button type="button" style="width:80px;" name="btnDelete" loxiaType="button" onclick="deleteRoleLine(this);">'+"删除"+'</button></div>';
			var ids = $j("#tbl-checkSpace-role-line").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				$j("#tbl-checkSpace-role-line").jqGrid('setRowData',ids[i],{"btnUpdate":btn1,"btnDelete":btn2});
				var city = $j("#tbl-checkSpace-role-line").getCell(ids[i],"city");
				if(city.indexOf("opposite") >= 0){
					var cc  = "非（"+city.substring(0,city.length-9)+"）";
					$j("#tbl-checkSpace-role-line").setCell (ids[i],"city",cc,{color:'red'});
				}
			}
			loxia.initContext($j(this));
		}
	});
	
	
	$j("#addSub").click(function(){
		$j("#skuCodes").val("");
		$j("#form_info input,#form_info select").val("");
		$j("#dialog_addRoleLine").dialog("open");
	//    
	});
	
	
	$j("#closeSub").click(function(){
		$j("#dialog_addRoleLine").dialog("close");
		roleLineId = ""; // 初始化
		//$j("#shopLikeQuery").find("option").remove();
		//$j("#shopLikeQuery").append( "<option value=\"\">--请选择--</option>" );
	});
	
	$j("#saveSub").click(function(){
		var postData = loxia._ajaxFormToObj("form_info");
		//var toc = $j("#otoLocation option:selected").text();
		//if(toc == "--请选择--"){
			//toc = "";
		//}
		postData["roleId"] = roleLineId;
		postData["menuType"] = 1;
		//postData["toLocation"] = toc;
		var errorMsg = loxia.validateForm("form_info");
		if(errorMsg.length == 0){
		    var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/saveOrUpdateCheckRoleLine.json",postData);
		    if(rs.result=='success'){
		    	if(rs.result2 == "1"){
		    		jumbo.showMsg("保存失败：优先级"+$j("#sort").val()+"已存在！");
		    		return;
		    	}
				jumbo.showMsg("操作成功！");
				$j("#dialog_addRoleLine").dialog("close");
				window.location.reload();
		    }else {
		    	if(rs.msg != null){
					jumbo.showMsg(rs.msg);
				}else{
					jumbo.showMsg("操作失败！");
				}
		    }
			
		}else{
			jumbo.showMsg(errorMsg);
		}
	});
	
});

//修改规则明细
function updateRoleLine(tag){
	$j("#form_info input,#form_info select").val("");
	$j("#dialog_addRoleLine").dialog("open");
    // 加载数据
	var id = $j(tag).parents("tr").attr("id");
	var date = $j("#tbl-checkSpace-role-line").jqGrid("getRowData",id);
    roleLineId = id; // 规则明细ID赋值
    var owner = date["ownerName"];
    var checkingAreaCode = date["checkingAreaCode"];
    var isQsStr = date["isQsStr"];
    var isSpecStr = date["isSpecStr"];
    var otoCode = date["toLocationName"];
    var timeType=date["transType"];//时效
    var skuCodes=date["skuCodes"];//指定商品
    var city = date["city"];
    var lpcode = date["lpcode"];
    var lv = date["lv"];
    var isPreSale=date["isPreSale"];//预售
    if(isQsStr == "是"){
    	$j("#isQs").val("1");
    }else if(isQsStr == "否"){
    	$j("#isQs").val("0");
    }
    if(isSpecStr == "是"){
    	$j("#isSpaice").val("1");
    }else if(isSpecStr == "否"){
    	$j("#isSpaice").val("0");
    }
    $j("#checkCode").val(checkingAreaCode);
    $j("#sort").val(lv);
    
    		
    var nCity = city.indexOf("非");
    var yCity = city.indexOf(",");
    if(yCity >=0 && nCity <0){
    	city="所有优先发货城市";
    }else if(nCity >=0){
    	city="非优先发货城市";
    }
	$j("#priorityCity option").each(function (){
	    if($j(this).text()==city){
	        $j(this).attr('selected',true);
	    }
	});
	$j("#selLpcode option").each(function (){
	    if($j(this).val()==lpcode){
	        $j(this).attr('selected',true);
	    }
	});
	$j("#shopLikeQuery1 option").each(function (){
	    if($j(this).text()==owner){
	        $j(this).attr('selected',true);
	    }
	});
	$j("#otoLocation option").each(function (){
	    if($j(this).text()==otoCode){
	        $j(this).attr('selected',true);
	    }
	});
	
	$j("#transTimeType option").each(function (){//时效
	    if($j(this).val()==timeType){
	        $j(this).attr('selected',true);
	    }
	});
	$j("#isPreSale option").each(function (){//时效
	    if($j(this).val()==isPreSale){
	        $j(this).attr('selected',true);
	    }
	});
	$j("#skuCodes").val(skuCodes);
}

//删除规则明细
function deleteRoleLine(tag){
	if(confirm("确定要删除吗？")){
		var id = $j(tag).parents("tr").attr("id");
		var data = $j("#tbl-checkSpace-role-line").jqGrid("getRowData",id);
		var postData = {};
		postData["roleId"] = data["id"];
		var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/deleteCheckLineById.json",postData);
	    if(rs.result=='success'){
			jumbo.showMsg("操作成功！");
			$j("#tbl-checkSpace-role-line").find("tr[id="+data["id"]+"]").remove();
	    }else {
	    	if(rs.msg != null){
				jumbo.showMsg(rs.msg);
			}else{
				jumbo.showMsg("操作失败！");
			}
	    }
	}
}
