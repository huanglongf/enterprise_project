$j.extend(loxia.regional['zh-CN'],{
	OUTBOUNDPACKAGE : "退仓装箱清单打印中，请等待..."
});
var $j = jQuery.noConflict();

$j.extend(loxia.regional['zh-CN'],{
	 
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}


//打印装箱明细
function printPg(id){
	loxia.lockPage();
	if (id != null){
		jumbo.showMsg(i18("OUTBOUNDPACKAGE"));
		var url = $j("body").attr("contextpath") + "/printoutboundpackageinfo.json?staid="+ id;  
		printBZ(loxia.encodeUrl(url),true);		
	}
	loxia.unlockPage();
}
$j(document).ready(function(){
	$j("#tabs").tabs();
	var baseUrl = $j("body").attr("contextpath");
	
	$j("#btnShopQueryClose").click(function(){
		$j("#rtwShopQueryDialog").dialog("close");
	});
	//initShopQuery("shopId","innerShopCode");//初始化查询条件，根据当前登录用户及所选仓库初始化店铺选择列表
	//根据查询条件进行查询，得到查询结果,可以进行店铺选择一系列操作
	$j("#btnSearchShop").click(function(){
		var showShopList = $j("#postshopInputName").val();
		showShopList = showShopList.substring(0,showShopList.length-1);
		var shopArray = showShopList.split("|");
		var ids = $j("#tbl_rtw_shop_query_dialog").jqGrid('getDataIDs');
		//获取模糊查询选中的店铺，循环判断勾选弹窗的checkBox
		for(var i = 0 ; i < shopArray.length; i++){  
			for(var j = 0 ; j < ids.length ; j++){
				var shopName= $j("#tbl_rtw_shop_query_dialog").getCell(ids[j],"name");
				if (shopName == shopArray[i]){
					$j("#tbl_rtw_shop_query_dialog").setSelection(ids[j],true);
				}
			}
		}
		$j("#rtwShopQueryDialog").dialog("open");
	});
	//加载平台店铺
	var shopLikeQuerys = loxia.syncXhrPost(window.$j("body").attr("contextpath") + "/findshoplistbycompanyid.json");
	$j("#shopLikeQuery").append("<option></option>");
	for(var idx in shopLikeQuerys){
		$j("#shopLikeQuery").append("<option value='"+ shopLikeQuerys[idx].code +"'>"+shopLikeQuerys[idx].name+"</option>");
	}
    $j("#shopLikeQuery").flexselect();
    $j("#shopLikeQuery").val("");
    
    $j("#shopLikeQuery").change(function(){
    	if ($j("#shopLikeQuery").val() != ""){
    		var showShopList = $j("#showShopList").html();
    		var postshopInput = $j("#postshopInput").val();
    		var postshopInputName = $j("#postshopInputName").val();
    		var shopLikeQuerys = $j("#shopLikeQuery").val(); //模糊查询下拉框key
    		var shopLikeQuery = $j("#shopLikeQuery_flexselect").val(); //模糊查询下拉框value
    		var shoplist = "", postshop="",shoplistName="";
    		if (showShopList.indexOf(shopLikeQuery) < 0){
    			shoplist = shopLikeQuery + " | ";
    			postshop = shopLikeQuerys+ "|"; 				
				shoplistName = shopLikeQuery+ "|";
    		}  //不包含
    		$j("#showShopList").html(showShopList + shoplist);
    		$j("#postshopInput").val(postshopInput+ postshop);
    		$j("#postshopInputName").val(postshopInputName + shoplistName);
    	}
    });
    
	loxia.init({debug: true, region: 'zh-CN'});
		
	var staType=loxia.syncXhr(baseUrl + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var staStatus=loxia.syncXhr(baseUrl + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	
	$j("#tbl_sta").jqGrid({
		url:baseUrl + "/getRtwDiekingList.json",
		datatype: "json",
		colNames: ["ID","作业单号","创建时间","作业类型","相关单据号","店铺","拣货任务号","SKU编码","SKU条码","拣货区域","库位编码","库存状态","商品名称","扩展属性"
		           ,"计划拣货数量","实际拣货数量","短拣数量","短拣标志"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name:"staCode", index:"staCode", width:130,resizable:true},
					{name:"createTime",index:"createTime",width:130,resizable:true},
					{name:"staType", index:"staType" ,width:130,resizable:true,formatter:'select',editoptions:staType},
					{name:"staRefSlipCode",index:"staRefSlipCode",width:150,resizable:true},
					{name:"owner", index:"sta.owner",width:130,resizable:true},
					{name:"batchCode",index:"batchCode",width:130,resizable:true},
					{name:"skuCode", index:"skuCode",width:100,resizable:true},
					{name:"skuBarCode", index:"skuBarCode",width:100,resizable:true},
					{name:"diekingAreaCode", index:"diekingAreaCode",width:100,resizable:true},
					{name:"locationCode", index:"locationCode",width:100,resizable:true},
					{name:"skuInvStatus", index:"skuInvStatus",width:60,resizable:true},
					{name:"skuName", index:"skuName",width:130,resizable:true},
					{name:"skuKeyProperties", index:"skuKeyProperties",width:100,resizable:true},
					{name:"planQuantity", index:"planQuantity",width:80,resizable:true},
					{name:"realityQuantity", index:"realityQuantity",width:80,resizable:true},
					{name:"shortPickQty", index:"shortPickQty",width:80,resizable:true},
					{name:"shortPickStatus", index:"shortPickStatus",width:80,resizable:true}
				  ],
		caption: "退仓拣货执行明细",
	   	sortname: 'id',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#tbl_sta_page",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
//	bindTableExportBtn("tbl_sta",{"staType":"whSTAType"});
	
	//查询
	$j("#query").click(function(){
		var postData = loxia._ajaxFormToObj("formQuery");
		postData["shoplist"] = $j("#postshopInput").val();
		var url = baseUrl + "/getRtwDiekingList.json";
		$j("#tbl_sta").jqGrid('setGridParam',{url:url,page:1,postData:postData}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl_sta",{"staType":"whSTAType","intStatus":"whSTAStatus"},
			url,postData);
	});
	//查询重置
	$j("#btnReset").click(function(){
		$j("#formQuery input,select").val("");
	});

});


function _constructHidden (name,value){
	name = name||"",value = value||"";
	return "<input type=hidden name=\"" + name + "\" value=\"" + value + "\" />";
};

/**
 * 导出
 */
function bindTableExportBtn(table,optionCols,url,postData,postCheck){
	var $t = loxia.isString(table)?$j("#" + table):$j(table),
		url = url || $t.jqGrid("getGridParam","url"),
		$c = $t.parents("div.ui-jqgrid");
	var html = [];
	html.push('<a class="ui-jqgrid-titlebar-export HeaderButton"' +  
			' href="javascript:;" title="导出" role="link" style="float:left;">' + 
			'<span class="ui-icon ui-icon-comment"><!-- --></span></a>');
	html.push("<form action="+url+" type='post'>");
	html.push(_constructHidden("isExcel","true"));
	html.push(_constructHidden("page","1"));
	html.push(_constructHidden("rows","-1"));
	html.push(_constructHidden("sidx",""));
	html.push(_constructHidden("sord",""));
	html.push(_constructHidden("caption",$t.jqGrid("getGridParam","caption")));
	var cm=$t.jqGrid("getGridParam","colModel"),cn=$t.jqGrid("getGridParam","colNames"),index=-1;
	var filter={"id":"id","rn":"","subgrid":"","cb":"","receiver":"receiver","receiverTel":"receiverTel","receiverProvince":"receiverProvince","receiverCity":"receiverCity","receiverArea":"receiverArea","receiverAddress":"receiverAddress",
			"btn2":"btn2","print":"print","btn3":"btn3"};
	$j.each(cm,function(i,e){
		if(e.hidden==false&&!(e.name in filter)){
			index++;
			html.push(jumbo._constructHidden("colModel["+index+"]",e.name));
			html.push(jumbo._constructHidden("colNames["+index+"]",cn[i]));
		}
	});
	if(optionCols){
		for(var k in optionCols){
			html.push(_constructHidden("columnOption." + k,optionCols[k]));
		}
	}
	if(postData){
		for(var k in postData){
			html.push(_constructHidden(k,postData[k]));
		}
	}
	html.push("</form>");
	if($c.find(".ui-jqgrid-titlebar-export").length>0){
		$c.find(".ui-jqgrid-titlebar-export").remove();
		$c.find("form").remove();
	}
	$c.find(".ui-jqgrid-titlebar .ui-jqgrid-titlebar-close").after(html.join(""));
	$c.find(".ui-jqgrid-titlebar-export").hover(function(){
		$j(this).addClass("ui-state-hover");
	},function(){
		$j(this).removeClass("ui-state-hover");
	});
	$c.find(".ui-jqgrid-titlebar-export").click(function(){
		var num;
		var text = $c.find("div.ui-jqgrid-pager>div>table>tbody>tr>td:eq(2)>div").text();
		if(text == "" || text == "无数据显示"){
			alert("没有要导出的数据!");
			return;
		}
		if(text==""){
			var num1 = $c.find("div.ui-jqgrid-pager>div>table>tbody>tr>td:eq(1)>table>tbody>tr>td:eq(3)>span").text().replace(" ","");
			var num2 = $c.find("div.ui-jqgrid-pager>div>table>tbody>tr>td:eq(1)>table>tbody>tr>td:eq(7)>select").val();
			num = parseInt(num1)*parseInt(num2);
		}else{
			var d = text.indexOf("共");
			if(d>0){
				var num=text.substring(d+2,text.length-2);
				num=num.replace(new RegExp(" ", 'g'), "");
			}
		}
		if(num>20000){
			alert("系统设定最多导出20000条数据!");
			return;
		}
		if(typeof(postCheck)==="function"){
			if(postCheck()== false){
				return false;
			}
		}
		$c.find(".ui-jqgrid-titlebar input[name='sidx']").val($t.jqGrid("getGridParam","sortname"));
		$c.find(".ui-jqgrid-titlebar input[name='sord']").val($t.jqGrid("getGridParam","sortorder"));
		$c.find(".ui-jqgrid-titlebar form")[0].submit();
		return false;
	});
};



