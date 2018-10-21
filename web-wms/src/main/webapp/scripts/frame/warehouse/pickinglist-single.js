$j.extend(loxia.regional['zh-CN'],{
	APPLY_FORM_SELECT : "请选择作业申请单",
	CODE : "作业单号",
	STATUS : "状态",
	REFSLIPCODE : "相关单据号",
	INTTYPE : "作业类型名称",
	SHOPID : "相关店铺",
	LPCODE : "物流服务商简称",
	ISNEEDINVOICE : "是否需要发票",
	CREATETIME : "创建时间",
	STVTOTAL : "计划执行总件数",
	COUNT_SEARCH : "待出库列表",
	
	SKUNAME : "商品名称",
	BARCODE : "条形码",
	JMCODE : "商品编码",
	KEYPROPERTIES : "扩展属性",
	QUANTITY : "计划执行量",
	DETAIL_INFO : "详细信息"
});
function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
/**
 * 判断是否为空
 * @param data
 * @returns
 */
function isNull(data){ 
	return (data == "" || data == undefined || data == null || data.length == 0) ? true : false; 
}
var customizationShopMap = {};//当前仓库定制打印模版店铺集合
var mtCode = "";
var isAuto = false; // 是否自动化仓
$j(document).ready(function(){
	initShopQuery("shopId","innerShopCode");//初始化查询条件，根据当前登录用户及所选仓库初始化店铺选择列表
	
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/auto/findZoonByOuId.do?valid="+true);
	$j("<option value=''>请选择</option>").appendTo($j("#district"));
	for(var idx in result){
			$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#district"));
	}
	
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findDistrictList.json");
	$j("<option value=''>请选择</option>").appendTo($j("#wh_district"));
	for(var idx in result){
			$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#wh_district"));
	}
	
	
	var arrStr1 = new Array();
	var arrStr2 = new Array();
	$j("#priorityCity").multipleSelect({
		 width: 200,
		 filter:true,
		 placeholder: "请选择",
		 onOpen: function() {
			 //$j("#priorityCity ul li:first").remove();
			 $j(".ms-select-all").remove();
         },
         onClick: function(view) {
            var res = view.label;
     		arrStr1.push(res);
     		var result1 = arrStr1.indexOf("所有优先发货城市");
     		var result2 = arrStr1.indexOf("非优先发货城市");
     		//alert(arrStr1);
     		if (result1 != -1 && result2 != -1) {
     			$j(".ms-choice span").text('');
     			$j("#priorityCity").multipleSelect("uncheckAll");
     			arrStr1= [];
     			$j('#priorityCity').multipleSelect('refresh');
     			jumbo.showMsg("不能同时选择'所有优先发货城市'和'非优先发货城市', 请重新选择!");
			} else if (result1 != -1 && arrStr1.length > 1) {
				$j(".ms-choice span").text('');
				$j("#priorityCity").multipleSelect("uncheckAll");
     			$j('#priorityCity').multipleSelect('refresh');
     			arrStr1= [];
				jumbo.showMsg("不能同时多选!");
			} else if (result2 != -1 && arrStr1.length > 1) {
				$j(".ms-choice span").text('');
				$j("#priorityCity").multipleSelect("uncheckAll");
     			$j('#priorityCity').multipleSelect('refresh');
     			arrStr1= [];
				jumbo.showMsg("不能同时多选!");
			}
         }
	});
	
	$j("#priorityId").multipleSelect({
		 width: 200,
		 filter:true,
		 placeholder: "请选择",
		 onOpen: function() {
			 //$j("#priorityCity ul li:first").remove();
			 $j(".ms-select-all").remove();
        },
        onClick: function(view) {
           var res = view.label;
    		arrStr2.push(res);
    		var result1 = arrStr2.indexOf("所有优先发货省份");
    		var result2 = arrStr2.indexOf("非优先发货省份");
    		//alert(arrStr1);
    		if (result1 != -1 && result2 != -1) {
    			$j(".ms-choice2 span").text('');
    			$j("#priorityId").multipleSelect("uncheckAll");
    			arrStr2= [];
    			$j('#priorityId').multipleSelect('refresh');
    			jumbo.showMsg("不能同时选择'所有优先发货省份'和'非优先发货省份', 请重新选择!");
			} else if (result1 != -1 && arrStr2.length > 1) {
				$j(".ms-choice2 span").text('');
				$j("#priorityId").multipleSelect("uncheckAll");
    			$j('#priorityId').multipleSelect('refresh');
    			arrStr2= [];
				jumbo.showMsg("不能同时多选!");
			} else if (result2 != -1 && arrStr2.length > 1) {
				$j(".ms-choice2 span").text('');
				$j("#priorityId").multipleSelect("uncheckAll");
    			$j('#priorityId').multipleSelect('refresh');
    			arrStr2= [];
				jumbo.showMsg("不能同时多选!");
			}
        }
	});
	
	
	var ruleNameResult = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getAllRuleName.do?modeName="+"单件");
    if(null!=ruleNameResult){
    	$j("<option value=''>请选择</option>").appendTo($j("#ruleName2"));
    	for(var id in ruleNameResult){
    		$j("<option value='" + ruleNameResult[id].id + "'>"+  ruleNameResult[id].ruleName +"</option>").appendTo($j("#ruleName2"));

        }
    }
	
	//根据查询条件进行查询，得到查询结果,可以进行店铺选择一系列操作
	$j("#btnSearchShop").click(function(){
		var showShopList = $j("#postshopInputName").val();
		showShopList = showShopList.substring(0,showShopList.length-1);
		var shopArray = showShopList.split("|");
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
	
	$j("#btnSearchShopGroup").click(function(){
		$j("#shopGroupQueryDialog").dialog("open");
	});

	$j("#btnSearchArea").click(function(){
		$j("#pickAreaQueryDialog").dialog("open");
	});
	
	
	$j("#btnSearchWhArea").click(function(){
		$j("#pickWhAreaQueryDialog").dialog("open");
	});
	      
	
	//初始化TOT目的地编码
	var otoResult = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getallshopstoreforoption.do");
	for(var idx in otoResult){
		$j("<option value='" + otoResult[idx].code + "'>"+ otoResult[idx].name +"</option>").appendTo($j("#selToLocation"));
	}
	//初始化物流商信息
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransportator.do");
	var mResult = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getTransportatorAfter.json");
	var mCode = "";
	var mName = "";
	for(var mdx in mResult){
		mCode+=mResult[mdx].expCode+",";
		mName+=mResult[mdx].name+",";
//		alert(mResult[mdx].expCode);
	}
	if(mCode!=""){
		$j("<option value='" + mCode.substring(0,mCode.length-1) + "'>"+ mName.substring(0,mName.length-1) +"</option>").appendTo($j("#selLpcode"));
		mtCode = mCode.substring(0,mCode.length-1);
	}
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selLpcode"));
	}
	
//	//初始化行业列表信息(销售事业部)
//	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findIndustryByOuid.do");
//	for(var idx in result){
//		$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#industryId"));
//	}
	
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
    
	//初始化商品分类
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findCategories.do");
	for(var idx in result){
		$j("<option value='" + result[idx].id + "'>" + result[idx].skuCategoriesName+"</option>").appendTo($j("#categoryId"));
	}
	//初始化商品大小列表
	var cList = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findskusize.json");
	for(var i=0;i<cList.length;i++){
		$j("<input type='checkbox' value='"+cList[i].name+"' name='box' data='"+i+"' id='"+cList[i].id+"'/><label for='"+cList[i].id+"'>"+cList[i].name+"</label>").appendTo($j("#skuConfigtd"));
	}
	//根据当前用户选择的仓库，查询其中所有的定制打印模版的店铺
	var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/customiztationtemplshop.json");
	isAuto = rs.isAuto;
	var customizationShopArray = null;
	if(rs && rs.returnVal){
		customizationShopArray = rs.returnVal;
		for (var i in customizationShopArray){
			customizationShopMap[customizationShopArray[i]] = customizationShopArray[i]; 
		}
	}
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	var trueOrFalse=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"trueOrFalse"});
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var staSpecialType={"value":"1:需特殊处理核对;2:包裹填充物;"};
	//初始化列表
	$j("#tbl-staList-query").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("CODE"),i18("REFSLIPCODE"),i18("SHOPID"),i18("LPCODE"),i18("STATUS"),i18("CREATETIME"),"平台订单时间",i18("ISNEEDINVOICE"),i18("STVTOTAL"),"是否含SN商品","是否特殊处理","是否QS","包装类型","拣货区域"],
		colModel: [{name: "id", index: "id", hidden: true},		         
		           {name: "code", index: "code", width: 120,formatter:"linkFmatter",formatoptions:{onclick:"showStaLine"}, resizable: true},
		           {name: "refSlipCode", index: "refSlipCode",width: 120, resizable: true},
		           {name: "shopId", index: "shopId", width: 100, resizable: true},
		           {name: "lpcode", index: "lpcode", width: 80, resizable: true,formatter:'select',editoptions:trasportName},
		           {name: "intStatus", index: "intStatus", width: 60, resizable: true,formatter:'select',editoptions:staStatus},
		           {name: "createTime", index: "createTime", width: 120, resizable: true},
		           {name: "orderCreateTime", index: "orderCreateTime", width: 120, resizable: true},
		           {name: "isNeedInvoice", index: "isNeedInvoice",  width: 80, resizable: true,formatter:'select',editoptions:trueOrFalse},
		           {name: "stvTotal", index: "stvTotal", width: 90, resizable: true},
		           {name: "isSn",index:"isSn",width:80,resizable:true,formatter:'select',editoptions:trueOrFalse},
		           {name: "intSpecialType",index:"isSpecialType",width:100,resizable:true,formatter:'select',editoptions:staSpecialType},
		           {name: "isQs", index: "isQs", width: 90, resizable: true},
		           {name: "packTypeStr", index: "packTypeStr", width: 90, resizable: true},
		           {name: "zoonList", index: "zoonList",  hidden: true, width: 90, resizable: true}
		           ],
		caption: i18("COUNT_SEARCH"),//待出库列表
	   	sortname: 'id',
	   	pager:"#pager_query",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:"auto",
	    multiselect: true,
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc", 
		jsonReader: { repeatitems : false, id: "0" }
	});
	$j("#tbl_detail_list").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("SKUNAME"),i18("BARCODE"),i18("JMCODE"),i18("KEYPROPERTIES"),i18("QUANTITY")],
		colModel: [
		           {name: "id", index: "id", hidden: true},		         
				   {name: "skuName", index: "skuName", width: 120, resizable: true},
		           {name: "barCode", index: "barCode",width: 120, resizable: true},
		           {name: "jmcode", index: "jmcode", width: 100, resizable: true},
		           {name: "keyProperties", index: "keyProperties", width: 100, resizable: true},
		           {name: "quantity", index: "quantity", width: 120, resizable: true}],
		caption: i18("DETAIL_INFO"),//详细信息
		rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pager",
		height:"auto",
   		sortname: 'id',
     	multiselect: false,
		sortorder: "desc", 
		jsonReader: { repeatitems : false, id: "0" }
	});
	// 店铺改变 出发查询
//	$j("#postshopInput").on('input',function(e){  
//		alert('A');
//		$('#btn-query').click();
//	});
	
	
	
	
	
	
	//查询按钮功能
	$j("#btn-query").click(function(){
		if(!checkMultiCompanyShop()){
			return ;
		}
		var postData = loxia._ajaxFormToObj("query-form");
		var arrCity = postData["city"];
		var priority = postData["priority"];
		postData["priority"]=priority.join(",");
		postData["city"] = arrCity.join(",");
		postData["shoplist"] = $j("#postshopInput").val();
		postData["shoplist1"] = $j("#postshopInput1").val();
		postData["sta.pickingType"]="SKU_SINGLE";
		//var isSp =$j("#selIsSpPg").val();
		var isQs =$j("#selIsQs").val();
		if (isQs == 1){
			postData["sta.isSpecialPackaging"]=true;
		}else{
			postData["sta.isSpecialPackaging"]=false;
		}
		
		var clist = $j("#skuConfigtd input");
		for(var i=0;i<clist.length;i++){
			postData["ssList["+i+"].maxSize"]=-1;
			postData["ssList["+i+"].minSize"]=-1;
			postData["ssList["+i+"].groupSkuQtyLimit"]=-1;
		}
		var checkList = $j("#skuConfigtd input:checked");
		if(checkList.length>0){ 
			for(var i = 0;i<checkList.length;i++){
				postData["ssList["+i+"].maxSize"]=cList[$j(checkList[i]).attr("data")].maxSize;
				postData["ssList["+i+"].minSize"]=cList[$j(checkList[i]).attr("data")].minSize;
				postData["ssList["+i+"].groupSkuQtyLimit"]=cList[$j(checkList[i]).attr("data")].groupSkuQtyLimit;
			}
		}else{
			for(var ss in cList){
				if(cList[ss].isDefault){
					postData["ssList[0].maxSize"]=cList[ss].maxSize;
					postData["ssList[0].minSize"]=cList[ss].minSize;
					postData["ssList[0].groupSkuQtyLimit"]=cList[ss].groupSkuQtyLimit;
				}
			}
		}
		postData["areaList"] = $j("#AreaInput1").val();
		postData["whAreaList"] = $j("#whAreaInput1").val();
		
		if($j("#whCountArea").attr("checked")){
			postData["isMargeWhZoon"]=true;
		}else{
			postData["isMargeWhZoon"]=false;
		}
		if($j("#CountArea").attr("checked")){
			postData["sta.isMerge"]=true;
		}else{
			postData["sta.isMerge"]=false;
		}
		//O2O全选
		var array = new Array();  //定义数组   
		if($j("#otoAll").attr("checked")){
		     $j("#selToLocation option").each(function(){  //遍历所有option  
		          var txt = $j(this).val();   //获取option值   
		          if(txt!=''){  
		               array.push(txt);  //添加到数组中  
		          }  
		     });
		}
//		alert(array);
		postData["otoAll"] = array.toString();
		$j("#tbl-staList-query").jqGrid('setGridParam',{
			url:loxia.getTimeUrl($j("body").attr("contextpath")+"/json/findStaForPickingByModel.do"),
			postData:postData,
			page:1
		}).trigger("reloadGrid");
		
		//$j(".ms-choice span").text('');
		//$j("#priorityCity").multipleSelect("uncheckAll");
		//arrStr1= [];
		$j('#priorityCity').multipleSelect('refresh');
		
	});
	//重置按钮功能
	$j("#reset").click(function(){
		//$j("#filterTable input").val("");
		$j("#fromDate").val('');
		$j("#toDate").val('');
		$j("#sta_code").val('');
		$j("#sta_refslipCode").val('');
		$j("#sta_memo").val('');
		$j("#selIsNeedInvoice option:first").attr("selected",true);
		$j("#selCity option:first").attr("selected",true);
		$j("#selLpcode option:first").attr("selected",true);
		$j("#selIsSpPg option:first").attr("selected",true);
		$j("#selIsQs option:first").attr("selected",true);
		$j("#industryId option:first").attr("selected",true);
		$j("#categoryId option:first").attr("selected",true);
		$j("#isSn option:first").attr("selected",true);
		$j("#status option:first").attr("selected",true);
		$j("#packingType option:first").attr("selected",true);
		$j("#isCod option:first").attr("selected",true);
		$j("#isPreSale option:first").attr("selected",true);//重置是否是预售
		$j("#orderType2 option:first").attr("selected",true);//重置订单类型
		$j("#postshopInput").val("");
		$j("#postshopInputName").val("");
		$j("#showShopList").html("");
		$j("#postshopInput1").val("");
		$j("#postshopInputName1").val("");
		$j("#showShopList1").html("");
		$j("#AeraList1").html("");
		$j("#AreaInput1").val("");
		$j("#AreaInputName1").val("");
		$j("#selToLocation option:first").attr("selected",true);
		$j("#skuConfigtd input").attr("checked",false);
	});
	//返回按钮功能
	$j("#back").click(function(){
		$j("#staInfo").removeClass("hidden");
		$j("#staLineInfo").addClass("hidden");
	});
	//生成配货清单按钮功能
	$j('#confirm').click(function(){
		var staIds = $j("#tbl-staList-query").jqGrid('getGridParam','selarrrow');
		if(staIds.length>0){
			if(!checkMultiCompanyShop()){
				return ;
			}
			var selectV = $j("#selLpcode").val();
//			alert(selectV+" / "+mtCode);
			var pl = $j("#"+staIds[0]+" td[aria-describedby='tbl-staList-query_lpcode']").text();
			var st = $j("#"+staIds[0]+" td[aria-describedby='tbl-staList-query_intSpecialType']").text();
			var qs = $j("#"+staIds[0]+" td[aria-describedby='tbl-staList-query_isQs']").text();
			var pt = $j("#"+staIds[0]+" td[aria-describedby='tbl-staList-query_zoonList']").text();
			for(var i in staIds){
				if(selectV != mtCode){
					/*var tempPL = $j("#"+staIds[i]+" td[aria-describedby='tbl-staList-query_lpcode']").text();
					if(pl != tempPL){
						jumbo.showMsg("物流商不同不允许创建同一批次里面！");
						return;
					}*/
					// 遍历列表中的订单，如果是包装类型的，则给标示赋值，if判断不重复覆盖by_fxl
					var tempPZL = $j("#"+staIds[i]+" td[aria-describedby='tbl-staList-query_zoonList']").text();
					if(isAuto && pt != tempPZL){
						jumbo.showMsg("【自动化仓】--不同拣货区域不允许创建同一批次里面！");
						return;
					}
				}
//				var tempST = $j("#"+staIds[i]+" td[aria-describedby='tbl-staList-query_intSpecialType']").text();
//				if(st != tempST){
//					jumbo.showMsg("特殊处理类型不同不允许创建同一批次里面！");
//					return;
//				}
			}
			var postData2 = loxia._ajaxFormToObj("query-form");
			var postData = {};
			for(var i in staIds){
				postData['staIdList[' + i + ']']=staIds[i];
			}
			var arrCity = postData2["city"];
			postData["city"] = arrCity.join(",");
			//postData['pickingList.packingType']= packTypeL;
			postData['pickingList.checkMode']="PICKING_CHECK";
			postData['pickingList.isSpecialPackaging']=false;
			postData['isCod']=$j('#isCod').val();
			if(st=="需特殊处理核对"){
				postData['pickingList.checkMode']="PICKING_SPECIAL";
			}
			if(qs=="是"){
				postData['pickingList.isSpecialPackaging']=true;
			}
			var lpcode=$j('#selLpcode').val();
			var selIsQs=$j('#selIsQs').val();
			var selToLocation=$j('#selToLocation').val();
			var transTimeType=$j('#transTimeType').val();
			if(lpcode!=""&&selIsQs!=0&&selToLocation!=""&&transTimeType!=""){
				postData['pickingList.isOTwoo']=true;
			}
			var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/generpickinglistbysta.json",postData);
			if(result&&result.result){
				var postData1 = loxia._ajaxFormToObj("query-form");
				postData1["shoplist"] = $j("#postshopInput").val();
				postData1["shoplist1"] = $j("#postshopInput1").val();
				var isQs1 =$j("#selIsQs").val();
				if (isQs1 == 1){
					postData1["sta.isSpecialPackaging"]=true;
				}else{
					postData1["sta.isSpecialPackaging"]=false;
				}
				postData1["sta.pickingType"]="SKU_SINGLE";
				var clist = $j("#skuConfigtd input");
				for(var i=0;i<clist.length;i++){
					postData1["ssList["+i+"].maxSize"]=-1;
					postData1["ssList["+i+"].minSize"]=-1;
					postData1["ssList["+i+"].groupSkuQtyLimit"]=-1;
				}
				var checkList = $j("#skuConfigtd input:checked");
				if(checkList.length>0){
					for(var i = 0;i<checkList.length;i++){
						postData1["ssList["+i+"].maxSize"]=cList[$j(checkList[i]).attr("data")].maxSize;
						postData1["ssList["+i+"].minSize"]=cList[$j(checkList[i]).attr("data")].minSize;
						postData1["ssList["+i+"].groupSkuQtyLimit"]=cList[$j(checkList[i]).attr("data")].groupSkuQtyLimit;
					}
				}else{
					for(var ss in cList){
						if(cList[ss].isDefault){
							postData1["ssList[0].maxSize"]=cList[ss].maxSize;
							postData1["ssList[0].minSize"]=cList[ss].minSize;
							postData1["ssList[0].groupSkuQtyLimit"]=cList[ss].groupSkuQtyLimit;
						}
					}
				}
				$j("#tbl-staList-query").jqGrid('setGridParam',{url:window.$j("body").attr("contextpath")+"/json/findStaForPickingByModel.do",postData:postData1,page:1}).trigger("reloadGrid");
				if(result&&result.result=='error'){
					jumbo.showMsg(result.message);
				}
			}
		}else{	
			jumbo.showMsg(loxia.getLocaleMsg("APPLY_FORM_SELECT"));//请选择作业申请单
		}
	});
	
	// 自动化仓不支持忽略拣货条件
	$j("#CountArea").change(function(){
		if(isAuto){
			jumbo.showMsg("自动化仓不支持该选项！");
			$j("#CountArea").attr("checked", false);
		}
	});
	
	//自动生成配货批
	$j("#autoConfirm").click(function(){
		if(isAuto && $j("#AreaInput1").val() == ""){
			jumbo.showMsg("【自动化仓】--请选择拣货区域！ 注： 选择多个拣货区域，将会by拣货区域创建配货批！");
	    	return;
		}
		
		if($j("#selIsNeedInvoice").val() == ""){
			if(!confirm("确定不选择是否需要发票将继续操作!")){
				return;
			}
		}
		/*if($j("#selLpcode").val() == ""){
			jumbo.showMsg("请选择物流商！");
			return;
		}*/
		if(!loxia.byId("autoSize").check() || !loxia.byId("plCount").check() || !loxia.byId("minAutoSize").check()){
			jumbo.showMsg("请填写正确数量！");
			return;
		}
		if($j("#autoSize").val()<0||$j("#plCount").val()<0 || $j("#minAutoSize").val()<0){
			jumbo.showMsg("请填写正确数量！");
			return;
		}
		if($j("#plCount").val()!=""){
			if($j("#autoSize").val()==""){
				jumbo.showMsg("请选择每批创建单据数量！");
				return;
			}
		}
		if($j("#autoSize").val()!= ""){
			var autoSize = $j("#autoSize").val();
			var minAutoSize = $j("#minAutoSize").val();
			if(parseInt(autoSize) < parseInt(minAutoSize)){
				jumbo.showMsg("最少单据数不能大于每批单据数！");
				return;
			}
		}
		
		if(!checkMultiCompanyShop()){
			return ;
		}
		var shoplist = $j("#postshopInputName").val();
		var shoplist1 = $j("#postshopInputName1").val();
		if (shoplist != "" && shoplist1 != ""){
			jumbo.showMsg("渠道和店铺只能选择一个...");
			return;
		}
		var postData = loxia._ajaxFormToObj("query-form");
		var arrCity = postData["city"];
		postData["city"] = arrCity.join(",");
		postData["limit"] = $j("#autoSize").val();
		postData["minAutoSize"] = $j("#minAutoSize").val();
		postData["plCount"] = $j("#plCount").val();
		postData["shoplist"] = $j("#postshopInput").val();
		postData["shoplist1"] = $j("#postshopInput1").val();
		postData["sta.pickingType"]="SKU_SINGLE";
		postData['pickingList.checkMode']="PICKING_CHECK";
		postData['isCod']=$j('#isCod').val();
		var st = $j("#selIsSpPg").val();
		var qs = $j("#selIsQs").val();
		postData['isQs'] = "";
		if(qs==1){ // QS
			postData['pickingList.isSpecialPackaging']=true;
			postData['pickingList.checkMode']="PICKING_CHECK";
		}
		if (st=="NORMAL" && qs==1){
			postData['isQs'] = "qsAndSq";
		}
		
		if (st!="NORMAL" && qs!="1"){
			postData['isQs'] = "notQsAndSq";
		}
		if(st=="NORMAL"){ //特殊处理
			postData['pickingList.checkMode']="PICKING_SPECIAL";
			postData['pickingList.isSpecialPackaging']=false;
		}
		
		//当订单为O2O且为QS订单时，要把原有的pickingType设置为O2OANDQS
		/*var qs =  $j("#selIsQs").find("option:selected").text();
		var o2o = $j('option:selected', '#selToLocation').index();
		if(qs=="是" && o2o !=0){
			postData["sta.pickingType"]="O2OANDQS";
		}*/
		postData["areaList"] = $j("#AreaInput1").val();
		if($j("#CountArea").attr("checked")){
			postData["sta.isMerge"]=true;
		}else{
			postData["sta.isMerge"]=false;
		}
		var clist = $j("#skuConfigtd input");
		for(var i=0;i<clist.length;i++){
			postData["ssList["+i+"].maxSize"]=-1;
			postData["ssList["+i+"].minSize"]=-1;
			postData["ssList["+i+"].groupSkuQtyLimit"]=-1;
		}
		var checkList = $j("#skuConfigtd input:checked");
		if(checkList.length>0){
			for(var i = 0;i<checkList.length;i++){
				postData["ssList["+i+"].maxSize"]=cList[$j(checkList[i]).attr("data")].maxSize;
				postData["ssList["+i+"].minSize"]=cList[$j(checkList[i]).attr("data")].minSize;
				postData["ssList["+i+"].groupSkuQtyLimit"]=cList[$j(checkList[i]).attr("data")].groupSkuQtyLimit;
			}
		}else{
			for(var ss in cList){
				if(cList[ss].isDefault){
					postData["ssList[0].maxSize"]=cList[ss].maxSize;
					postData["ssList[0].minSize"]=cList[ss].minSize;
					postData["ssList[0].groupSkuQtyLimit"]=cList[ss].groupSkuQtyLimit;
				}
			}
		}
		var isOtoPicking = false;
		//O2O全选
		var array = new Array();  //定义数组   
		if($j("#otoAll").attr("checked")){
			 isOtoPicking = true;
		     $j("#selToLocation option").each(function(){  //遍历所有option  
		          var txt = $j(this).val();   //获取option值   
		          if(txt!=''){  
		               array.push(txt);  //添加到数组中  
		          }  
		     });
		}
//		alert($j("#selToLocation").val());
		if($j("#selToLocation").val() != ""){
			isOtoPicking = true;
		}
//		alert(isOtoPicking);
//		alert(array);
		postData["otoAll"] = array.toString();
		postData["isOtoPicking"] = isOtoPicking;
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/autogenerdispatchList.json",postData);
		if(result&&result.result){//成功后更新原始查询条件列表
			var postData1 = loxia._ajaxFormToObj("query-form");
			postData1["shoplist"] = $j("#postshopInput").val();
			postData1["shoplist1"] = $j("#postshopInput").val();
			var isQs2 =$j("#selIsQs").val();
			if (isQs2 == 1){
				postData1["sta.isSpecialPackaging"]=true;
			}else{
				postData1["sta.isSpecialPackaging"]=false;
			}
			postData1["sta.pickingType"]="SKU_SINGLE";
			var clist = $j("#skuConfigtd input");
			for(var i=0;i<clist.length;i++){
				postData1["ssList["+i+"].maxSize"]=-1;
				postData1["ssList["+i+"].minSize"]=-1;
				postData1["ssList["+i+"].groupSkuQtyLimit"]=-1;
			}
			var checkList = $j("#skuConfigtd input:checked");
			if(checkList.length>0){
				for(var i = 0;i<checkList.length;i++){
					postData1["ssList["+i+"].maxSize"]=cList[$j(checkList[i]).attr("data")].maxSize;
					postData1["ssList["+i+"].minSize"]=cList[$j(checkList[i]).attr("data")].minSize;
					postData1["ssList["+i+"].groupSkuQtyLimit"]=cList[$j(checkList[i]).attr("data")].groupSkuQtyLimit;
				}
			}else{
				for(var ss in cList){
					if(cList[ss].isDefault){
						postData1["ssList[0].maxSize"]=cList[ss].maxSize;
						postData1["ssList[0].minSize"]=cList[ss].minSize;
						postData1["ssList[0].groupSkuQtyLimit"]=cList[ss].groupSkuQtyLimit;
					}
				}
			}
			//O2O全选
			var array1 = new Array();  //定义数组   
			if($j("#otoAll").attr("checked")){
			     $j("#selToLocation option").each(function(){  //遍历所有option  
			          var txt = $j(this).val();   //获取option值   
			          if(txt!=''){  
			        	  array1.push(txt);  //添加到数组中  
			          }  
			     });
			}
//			alert(array);
			postData1["otoAll"] = array1.toString();
			$j("#tbl-staList-query").jqGrid('setGridParam',{url:window.$j("body").attr("contextpath")+"/json/findStaForPickingByModel.do",postData:postData1,page:1}).trigger("reloadGrid");
			if(result&&result.result=='error'){
				jumbo.showMsg(result.message);
			}
		}
	});
	
	$j("#deleteRuleName").click(function(){
		var id=$j("#ruleName2").val();
		if(null!=id&&''!=id){
			var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/deleteRultName.json?id="+id);
	        if(result["result"]=="true"){
	        	jumbo.showMsg("删除成功");
	        	$j("#ruleName2").empty();
	        	var ruleNameResult = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getAllRuleName.do?modeName="+"单件");
	            if(null!=ruleNameResult){
	            	$j("<option value=''>请选择</option>").appendTo($j("#ruleName2"));
	            	for(var id in ruleNameResult){
	            		$j("<option value='" + ruleNameResult[id].id + "'>"+  ruleNameResult[id].ruleName +"</option>").appendTo($j("#ruleName2"));

	                }
	            }
	        }else{
	        	jumbo.showMsg("删除失败");
	        }
		}else{
			jumbo.showMsg("请选择规则名称");
		}
	});
	
	$j("#createPicking").click(function(){
		var district=$j("#district").val();
		var wh_district=$j("#wh_district").val();
		if((district==null||district=="")&&(wh_district==null||wh_district=="")){
			jumbo.showMsg("一键创批仓库区域或者库区区域不可都为空！");
			return;
		}
		
		//jumbo.showMsg("正在处理，请稍后查看......");
    	var ruleNameResult = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/createPickinglistBySingOrder.do?district="+district+"&wh_district="+wh_district);
    	if(ruleNameResult&&ruleNameResult.msg!=null&&ruleNameResult.msg!=""){
			jumbo.showMsg(ruleNameResult.msg);
			return;
		}
//		loxia.asyncXhrPost($j("body").attr("contextpath") + "/json/createPickinglistBySingOrder.json",
//				{"district":district},
//				{success:function (data) {
//					if(data&&data.msg!=null&&data.msg!=""){
//						jumbo.showMsg(data.msg);
//					}
//				}
//		});
		jumbo.showMsg("正在处理，请稍后查看......");
    	
	});

	$j("#addRuleName").click(function(){
		if(!checkMultiCompanyShop()){
			return ;
		}
		var postData = loxia._ajaxFormToObj("query-form");
		var arrCity = postData["city"];
		var priority = postData["priority"];
		
		postData["priority"] = priority.join(",");
		postData["city"] = arrCity.join(",");
		postData["shoplist"] = $j("#postshopInput").val();
		postData["shoplist1"] = $j("#postshopInput1").val();
		postData["postshopInputName"]=$j("#postshopInputName").val();
		postData["postshopInputName1"]=$j("#postshopInputName1").val();
		
		var str="";
	    $j('input[name="box"]:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数  
	    	str +=($j(this).val())+","; 
	    });
	    if(null!=str&&""!=str){
	    	str=str.substring(0,str.length-1); 
	    	postData["ssList1"]=str;
	    }else{
	    	postData["ssList1"]=null;
	    }
//		if($j("#issingle").attr("checked")==true){
//			postData["sta.skuQty"]="";
//		}else{
//			postData["sta.skuQty"]=2;
//		}
//		postData["sta.isSedKill"]=false;
//		postData["isGroupSku"]=false;
		var isQs =$j("#selIsQs").val();
		if (isQs == 1){
			postData["sta.isSpecialPackaging"]=true;
		}else{
			postData["sta.isSpecialPackaging"]=false;
		}
		postData["sta.pickingType"]="SKU_SINGLE";
		
		
		postData["areaList"] = $j("#AreaInput1").val();
		postData["whAreaList"] = $j("#whAreaInput1").val();
		postData["AeraList1"]=$j("#AreaInputName1").val();
		postData["whZoneList1"]=$j("#whAreaInputName1").val();
		if($j("#CountArea").attr("checked")){
			postData["sta.isMerge"]=true;
		}else{
			postData["sta.isMerge"]=false;
		}
		if($j("#whCountArea").attr("checked")){
			postData["isMargeWhZoon"]=true;
		}else{
			postData["isMargeWhZoon"]=false;
		}
		
		if($j("#CountAreas").attr("checked")){
			postData["mergePickZoon"]=1;
		}else{
			postData["mergePickZoon"]=0;
		}
		
		postData["modeName"]="单件";
		if(null!=$j("#ruleName").val()&&""!=$j("#ruleName").val()){
			postData["ruleName"]=$j("#ruleName").val();
		}else{
			jumbo.showMsg("规则名称不能为空");
			return;
		}
		
		postData["ruleCode"]=$j("#ruleCode").val();
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/addRultName.json",postData);
	    if(result["result"]=="success"){
	    	jumbo.showMsg("保存成功");
	    	$j("#ruleName2").empty();
	    	var ruleNameResult = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getAllRuleName.do?modeName="+"单件");
	        if(null!=ruleNameResult){
	        	$j("<option value=''>请选择</option>").appendTo($j("#ruleName2"));
	        	for(var id in ruleNameResult){
	        		$j("<option value='" + ruleNameResult[id].id + "'>"+  ruleNameResult[id].ruleName +"</option>").appendTo($j("#ruleName2"));

	            }
	        }
	    }else if(result["result"]=="error"){
	    	jumbo.showMsg("规则名称已经存在");
	    	return;
	    }else{
	    	jumbo.showMsg("系统异常");
	    }
	});
});

function onTheTrigger(){
	$j('#btn-query').trigger("click");
}

//作业单明细
function showStaLine(tag){
	var tr = $j(tag).parents("tr");
	var id = tr.attr("id");
	$j("#staInfo").addClass("hidden");
	$j("#staLineInfo").removeClass("hidden");
	var sta=$j("#tbl-staList-query").jqGrid("getRowData",id);
	$j("#s_code").html(sta["code"]);
	$j("#s_createTime").html(sta["createTime"]);
	$j("#s_slipCode").html(sta["refSlipCode"]);
	$j("#s_owner").html(sta["shopId"]);
	$j("#s_status").html(tr.find("td[aria-describedby$='intStatus']").html());
	$j("#s_type").html("类型");
	$j("#s_trans").html(tr.find("td[aria-describedby$='lpcode']").html());
	$j("#s_inv").html(tr.find("td[aria-describedby$='isNeedInvoice']").html());
	var postData = {};
	postData["sta.id"] = id;
	$j("#tbl_detail_list").jqGrid('setGridParam',
			{url:window.$j("body").attr("contextpath")+"/findPickingStaLine.json",
			postData:postData,page:1}
		).trigger("reloadGrid");
}
function checkMultiCompanyShop(){
	var shoplist = $j("#postshopInputName").val();
	var shoplist1 = $j("#postshopInputName1").val();
//	var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/warehosueisrelateshopforprint.json");
//	// error 需要选择店铺 ，success 不是必须选择店铺
//	if(result && result.result && result.result=='error'){
//		if (shoplist==""){
//			jumbo.showMsg("当前仓库装箱清单打印为店铺定制，配货前请选择店铺...");
//			return false;
//		}
//	}
	if (shoplist==""){
		if(shoplist1==""){
			jumbo.showMsg("请选择渠道或渠道组...");
			return false;
		}
	}else{
		if(shoplist1!=""){
			jumbo.showMsg("渠道和店铺只能选择一个...");
			return false;
		}
	}
	var AeraList1 = $j("#AreaInput1").val();
	var whAeraList1 = $j("#whAreaInput1").val();	
	var CountArea = $j("#CountArea").attr("checked");
	var whCountArea = $j("#whCountArea").attr("checked");
	if(AeraList1 !="" && whAeraList1 != "" && !CountArea && !whCountArea){
		jumbo.showMsg("拣货区域和仓库区域只能选择一个...");
		return false;
    }
	
	var flag = 0,index=0;
	var s = shoplist.split("|");
	var iteration = [];
	if(s.length > 2){
		for(var i in s){
			if (customizationShopMap[s[i]]){
				flag++;
				iteration[index++] = customizationShopMap[s[i]];
			}
		}
	}
	if (s.length > 1 && flag > 0){
		jumbo.showMsg("以下定制店铺不能多选 ： " + iteration);
		return false;
	}
	var postData = {};
	postData['shoplist'] = shoplist;
	var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/warehosueShopForPrintIsSame.json",postData);
	// error 所选店铺存在装箱清单模板不同的情况 ，success 所选店铺的装箱清单模板均相同
	if(result && result.result && result.result=='error'){
		if (result.tips!=""){
			jumbo.showMsg("以下店铺装箱清单模板不同 :"+ result.tips);
			return false;
		}
	}
	return true;
}
function checkHaveFlush(){
	return true;
}

function change(){
	$j("#reset").trigger("click");
	$j("input:checkbox").attr("checked",false);
	//$j("#priorityCity option").attr("checked",false);
	$j(".ms-choice span").text('');
	
	var id=$j("#ruleName2").val();
	if(null!=id&&''!=id){
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/findRulaNameById.json?id="+id);
        if(null!=result&&null!=result["result"]){
        	var resultJason=result["result"]["sqlContent"];
        	var rusult=eval('('+resultJason+')');
        	$j("#fromDate").val(rusult["date"]);
        	$j("#toDate").val(rusult["date2"]);
        	$j("#orderCreateTime").val(rusult["date3"]);
        	$j("#toOrderCreateTime").val(rusult["date4"]);
        	$j("#selIsNeedInvoice").val(rusult["isNeedInvoice"]);
        	
        	$j("#selLpcode").val(rusult["codeList"]);
        	$j("#skuConfigtd").val(rusult[""]);
        	if(null!=rusult["isSpecialPackaging"]&&"1"==rusult["isSpecialPackaging"]){
        		$j("#selIsSpPg").find("option[value='NORMAL']").attr("selected",true);
        	}else{
        		$j("#selIsSpPg option:first").attr("selected",true);
        	}
        	
        	$j("#isCod").val(rusult["isCod"]);
        	$j("#categoryId").val(rusult["categoryId"]);
        	$j("#selIsQs").val(rusult["isSpecialPackaging"]);
        	$j("#isSn").val(rusult["isSn"]);
        	//$j("#transTimeType").val(rusult["transTimeType"]);
        	$j("#transTimeType").find("option[value='"+rusult["transTimeType"]+"']").attr("selected",true);  
        	if(null!=rusult["status"]){
        		if("1"==rusult["status"]){
        			$j("#status").find("option[value='CREATED']").attr("selected",true);  
        		}
        		if("20"==rusult["status"]){
        			$j("#status").find("option[value='FAILED']").attr("selected",true);
        		}
        	}	
        	$j("#code1").val(rusult["code1"]);
        	$j("#code2").val(rusult["code2"]);
        	$j("#code3").val(rusult["code3"]);
        	$j("#code4").val(rusult["code4"]);
        	$j("#pickingType").val(rusult["pickingType"]);
        	$j("#pickSubType").val(rusult["pickSubType"]);
        	$j("#isPreSale").val(rusult["isPreSale"]);
        	$j("#orderType2").val(rusult["orderType2"]);
        	$j("#priorityId").val(result["result"]["priority"]);
        	if(null!=rusult["shoplist"]&&""!=rusult["shoplist"]&&rusult["shoplist"].length>0){
        		$j("#showShopList").html(rusult["postshopInputName"]+"");
        		$j("#postshopInput").val(rusult["shoplist"]);
        		$j("#postshopInputName").val(rusult["postshopInputName"]);
        	}
        	if(null!=rusult["shoplist1"]&&""!=rusult["shoplist1"]&&rusult["shoplist1"].length>0){
        		$j("#showShopList1").html(rusult["postshopInputName1"]+"");
        		$j("#postshopInput1").val(rusult["shoplist1"]);
        		$j("#postshopInputName1").val(rusult["postshopInputName1"]);
        	}
        	
        	if(null!=rusult["pickZoneList"]&&""!=rusult["pickZoneList"]&&rusult["pickZoneList"].length>0){
        		$j("#AreaInput1").val(rusult["pickZoneList"]);
        		$j("#AeraList1").html(rusult["aeraList1"]+"");
        		$j("#AreaInputName1").val(rusult["aeraList1"]);
        	}
        	if(null!=rusult["whZoneList"]&&""!=rusult["whZoneList"]&&rusult["whZoneList"].length>0){
        		$j("#whAreaInput1").val(rusult["whZoneList"]);
        		$j("#whAeraList1").html(rusult["whZoneList1"]+"");
        		$j("#whAreaInputName1").val(rusult["whZoneList1"]);
        	}
    		//$j("#postshopInputName").val(postshopInputName + shoplistName);
        	//$j("#postshopInputName1").val(rusult["shopInnerCodes"]);
        	$j("#pickZoneList").val(rusult["pickZoneList"]);
        	$j("#whZoneList").val(rusult["whZoneList"]);
        	$j("#CountArea").val(rusult["isMergeInt"]);
        	$j("#selToLocation").val(rusult["toLocation"]);
        	$j("#selLpcode").val(rusult["codeList"]);
        	if(null!=rusult["packingType"]&&""!=rusult["packingType"]){
        		$j("#packingType").val("GIFT_BOX");
        	}else{
        		$j("#packingType").val("");
        	}
        	if(null!=rusult["isMergeInt"]&&""!=rusult["isMergeInt"]){
        		if("1"==rusult["isMergeInt"]){
        			$j("#CountArea").attr("checked",true);
        		}
        	}
        	if(null!=rusult["isMargeZoon"]&&""!=rusult["isMargeZoon"]){
        		if("1"==rusult["isMargeZoon"]){
        			$j("#whCountArea").attr("checked",true);
        		}
        	}
        	if(null!=rusult["mergePickZoon"]&&""!=rusult["mergePickZoon"]){
        		if("1"==rusult["mergePickZoon"]){
        			$j("#CountAreas").attr("checked",true);
        		}
        	}
        	if(null!=rusult["mergeWhZoon"]&&""!=rusult["mergeWhZoon"]){
        		if("1"==rusult["mergeWhZoon"]){
        			$j("#whCountAreas").attr("checked",true);
        		}
        	}
        	if(null!=rusult["sList"]&&""!=rusult["sList"]&&"null"!=rusult["sList"]){
        		var date=rusult["sList"].split(",");
        		var val="";
        		var dateVal=new Array();
        		$j("[name='box']").each(function(index, element) {
                    val += $j(this).val() + ",";
                });
                if(null!=val&&""!=val){
                	val=val.substring(0,val.length-1); 
                	dateVal=val.split(",");
                }
                if(null!=dateVal&&dateVal.length>0){
                	for(idx in date){
            			for(id in dateVal){
            				if(date[idx]==dateVal[id]){
            					$j("input:checkbox[value='"+date[idx]+"']").attr('checked','true');
            					//$j("[name='box']").find("checkbox[value='"+date[idx]+"']").attr("checked", true);
            				}
            			}
            		}
                }
        	}
        	if(document.getElementById("priorityCity").options.length>0){
        		if(null!=result["result"]["city"]&&""!=result["result"]["city"]){
        			var arrayCity={};
        			arrayCity=result["result"]["city"].split(",");
        			//var cityDate={};
        			//var test=document.getElementById("priorityCity").options;
        			var array=new Array();
        			$j("#priorityCity option").map(function(){
        				array.push($j(this).val());
        		    });
        			for(inx in arrayCity){
        				for(id in array){
        					if(arrayCity[inx]==array[id]){
        						$j("#priorityCity").find("option[value='"+array[id]+"']").attr("selected",true);
        						break;
        					}
        					if(arrayCity.indexOf("opposite")>0){
        						//$j("#priorityCity").find("option[value='"+array[id]+"']").attr("selected",true);
        						$j("#priorityCity").find("option[value*='opposite']").attr("selected",true);
        						break;
        					}
        				}
        			}
        			$j("#priorityCity").multipleSelect('refresh');
                	}
        	}
        	
        	
        	if(document.getElementById("priorityId").options.length>0){
        		if(null!=result["result"]["priority"]&&""!=result["result"]["priority"]){
        			var arrayCity={};
        			arrayCity=result["result"]["priority"].split(",");
        			//var cityDate={};
        			//var test=document.getElementById("priorityCity").options;
        			var array=new Array();
        			$j("#priorityId option").map(function(){
        				array.push($j(this).val());
        		    });
        			for(inx in arrayCity){
        				for(id in array){
        					if(arrayCity[inx]==array[id]){
        						$j("#priorityId").find("option[value='"+array[id]+"']").attr("selected",true);
        						break;
        					}
        					if(arrayCity.indexOf("opposite")>0){
        						$j("#priorityId").find("option[value*='opposite']").attr("selected",true);
        						//$j("#priorityId option[text='"+非优先发货省份+"']").attr("selected", "selected");
        						break;
        					}
        				}
        			}
        			$j("#priorityId").multipleSelect('refresh');
                	}
        	}
        	
        }
	}
}
