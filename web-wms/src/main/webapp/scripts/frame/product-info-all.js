$j.extend(loxia.regional['zh-CN'],{
	JM_CODE:"商品编码",
	PRO_NAME:"商品名称",
	BRAND_NAME:"品牌名称",
	SUPPLIER_CODE:"货号",
	LENGTH:"长",
	WIDTH:"宽",
	HEIGHT:"高",
	WEIGHT:"重量",
	CATEGORYNAME:"所属商品分类",
	ISRAIL:"是否陆运",
	SKU_QUERY_LIST:"商品列表",
	CORRECT_FILE_PLEASE:"请选择正确的Excel文件进行导入",
	NO_BRANDNAME:"品牌名称必须选择",
	IS_CONSUMABLE:"是否耗材",
	SELECTBRAND:"--请选择品牌名称--"
});
function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
function showMsg(msg){
	jumbo.showMsg(msg);
}
var $j = jQuery.noConflict();
var baseUrl = window.parent.$j("body").attr("contextpath")+"/json";
$j(document).ready(function(){
	initBrand();
	
	// 初始化商品类型
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/auto/findSkuTypeJson.do");
	for(var idx in result){
		
			$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#skuType"));
		
	}
	
	var trueOrFalse=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"trueOrFalse"});
	//initBrandQuery("brandName","name"); 
	
	$j("#tbl-product").jqGrid({
		url:baseUrl+"/findProduct2.json",
		datatype: "json",
		postData:loxia._ajaxFormToObj("form_query"),
		colNames: ["id",i18("JM_CODE"),i18("PRO_NAME"),i18("SUPPLIER_CODE"),i18("BRAND_NAME"),i18("LENGTH"),i18("WIDTH"),i18("HEIGHT"),
		           i18("WEIGHT"),i18("IS_CONSUMABLE"),"箱型名称","箱型条码",i18("CATEGORYNAME"),i18("ISRAIL"),"商品上架类型","商品上架类型ID","原产地","报关编码","单位名称","英语名","ext_code1","ext_code2","ext_code3","颜色编码","颜色中文描述",
		           "尺码","extProp1","extProp2","extProp3","extProp4","商品吊牌价","skuCid","客户商品编码","barcode","cuId","jmCode2","validDate","warningDate",
		           " 扩展属性","种类"],
		colModel: [{name:"id",index:"id",hidden:true,resizable: true},//0
		           {name: "jmCode", index: "jmCode", width: 120,formatter:"linkFmatter",formatoptions:{onclick:"editPanel"},resizable: true},//1
		           {name: "name", index: "name", width: 150, resizable: true},//2
		           {name: 'barCode', index: "barCode", width: 200, resizable: true},//3
		           {name: "brandName",index:"brandName",width:150,resizable:true},//4
		           {name: "length", index: "length", width: 50, resizable: true},//5
		           {name: "skuCost", index: "skuCost", width: 50, resizable: true},//6
		           {name: "height", index: "height", width: 50, resizable: true},//7
		           {name: "netWeight", index: "netWeight", width: 60, resizable: true},//8
		           {name: "isConsumable", index: "isConsumable", width: 60, resizable: true},//9
		           {name: "packageSkuName", index: "packageSkuName", width: 100, resizable:true},//10
		           {name: "packageBarCode", index: "packageBarCode", width: 100, resizable:true},//11
		           {name: "categoryName", index: "categoryName", width: 100, resizable: true},//12
		           {name: "isRail", index: "isRail", width:50, resizable: true,formatter:'select',editoptions:trueOrFalse},//13
		           {name: "skuTypeName", index: "skuTypeName", width: 60, resizable: true},//14
		           {name: "skuTypeId", index: "skuTypeId", width: 60,hidden:true, resizable: true},//15
		           {name: "countryOfOrigin", index: "countryOfOrigin", width: 60,hidden:true, resizable: true},//16
		           {name: "htsCode", index: "htsCode", width: 60,hidden:true, resizable: true},//17
		           {name: "unitName", index: "unitName", width: 60,hidden:true, resizable: true},//18
		           {name: "enName", index: "enName", width: 60,hidden:true, resizable: true},//19
		           {name: "extensionCode1", index: "extensionCode1", width: 60,hidden:true, resizable: true},//20
		           {name: "extensionCode2", index: "extensionCode2", width: 60,hidden:true, resizable: true},//21
		           {name: "extensionCode3", index: "extensionCode3", width: 60,hidden:true, resizable: true},//22
		           
		           {name: "color", index: "color", width: 60,hidden:true, resizable: true},//23
		           {name: "colorName", index: "colorName", width: 60,hidden:true, resizable: true},//24
		           {name: "skuSize", index: "skuSize", width: 60,hidden:true, resizable: true},//25
		           {name: "extProp1", index: "extProp1", width: 60,hidden:true, resizable: true},//26
		           {name: "extProp2", index: "extProp2", width: 60,hidden:true, resizable: true},//27
		           {name: "extProp3", index: "extProp3", width: 60,hidden:true, resizable: true},//28
		           {name: "extProp4", index: "extProp4", width: 60,hidden:true, resizable: true},//29
		           {name: "listPrice", index: "listPrice", width: 60,hidden:true,resizable: true},//30
		           {name: "skuCid", index: "skuCid", width: 60,hidden:true,resizable: true},//31
		           {name: "customerSkuCode", index: "customerSkuCode", width: 60,hidden:true,resizable: true},//32
		           {name: "barCode2", index: "barCode2", width: 60,hidden:true,resizable: true},//33
		           {name: "cuId", index: "cuId", width: 60,hidden:true,resizable: true},//34
		           {name: "jmCode2", index: "jmCode2", width: 60,hidden:true,resizable: true},//35 
		           {name: "validDate", index: "validDate", width: 60,hidden:true,resizable: true},//36 
		           {name: "warningDate", index: "warningDate", width: 60,hidden:true,resizable: true},//37
		           {name: "keyProperties", index: "keyProperties", width: 60,hidden:true,resizable: true},//38
		           {name: "category", index: "category", width: 60,hidden:true,resizable: true}//39
		           ],
		           
		caption: i18("SKU_QUERY_LIST"),
	   	pager:"#pager",
	   	sortname:'id',
	   	sortorder:"desc",
	   	rownumbers:true,
	   	multiselect: false,
	   	viewrecords: true,
	   	rowNum: 10,
		rowList:[10,20,40],
		height:jumbo.getHeight(),
		gridComplete : function(){
			loxia.initContext($j(this));
		},
		jsonReader: { repeatitems : false, id: "0" }
	});
	bindTable();
	$j("#query").click(function(){
		var brandName = $j("#brandName").val();
		if(brandName==""){
			jumbo.showMsg(i18("NO_BRANDNAME"));
			return;
		}
		var url = baseUrl+"/findProduct2.json";
		$j("#tbl-product").jqGrid('setGridParam',{url:url,postData:loxia._ajaxFormToObj("form_query")}).trigger("reloadGrid");
		bindTable();
	});
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select option:first").attr("selected",true);
	});

	$j("#back").click(function(){
		$j("#f2").addClass("hidden").siblings().removeClass("hidden");
	});
	$j("#edit").click(function(){
		var code1=	$j("#code1").val().trim();//商品编码
		var barCode2=	$j("#barCode2").val().trim();//商品BAR编码
		var customerSkuCode=$j("#customerSkuCode").val().trim();//客户商品编码
		var cuId=$j("#cuId").val().trim();
		var id=$j("#id").val().trim();
		//判断barcode同一客户下
		var data1={};
		data1["proCmd.id"]=id;
		data1["proCmd.cuId"]=cuId;
		data1["proCmd.barCode"]=barCode2;
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/judgeSkuBarCode.json",data1);
		if(rs!=null&&rs.result=="success"){
			if(rs.brand=="0"){
				jumbo.showMsg("商品BAR编码已经存在！");
				return;
			}
		}else{
			jumbo.showMsg("验证商品BAR编码失败");
			return;
		}
		var data = {};
		data["proCmd.cuId"]=cuId;
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/getCustomerIsAdidas.json",data);
		if(rs!=null&&rs.result=="success"){
			if(rs.brand=="1"){//进行ad判断逻辑
				if(code1==barCode2 && code1==customerSkuCode && barCode2==customerSkuCode){
							var data2={};
							data2["proCmd.id"]=id;
							data2["proCmd.code"]=code1;
							data2["proCmd.customerSkuCode"]=customerSkuCode;
							var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/judgeSkuCodeOrCustomerSkuCode.json",data1);
							if(rs!=null&&rs.result=="success"){
								if(rs.brand=="code0"){
									jumbo.showMsg("商品编码已经存在！");
									return;
								}
								if(rs.brand=="customerSkuCode0"){
									jumbo.showMsg("客户商品编码已经存在！");
									return;
								}
							}else{
								jumbo.showMsg("验证code和客户商品编码失败!");
								return;
							}
				}else{
							jumbo.showMsg("商品编码&商品BAR编码&客户商品编码,ad客户必须能一致!");
							return;
				}
//				if(isNaN($j("#extProp3").val().trim())){
//					loxia.tip($j("#extProp3"),"AD客户下，必须为数字");
//					return;
//				}
			}
		}else{
			jumbo.showMsg("获取客户失败");
			return;		
		}
		
		if(isNaN($j("#length").val().trim())){
			loxia.tip($j("#length"),"长度必须为数字");
			return;
		}
		if(isNaN($j("#width").val().trim())){
			loxia.tip($j("#width"),"宽度必须为数字");
			return;
		}
		if(isNaN($j("#height").val().trim())){
			loxia.tip($j("#height"),"高度必须为数字");
			return;
		}
		if(isNaN($j("#grossWeight").val().trim())){
			loxia.tip($j("#grossWeight"),"净重必须为数字");
			return;
		}
		if(isNaN($j("#listPrice").val().trim())){
			loxia.tip($j("#listPrice"),"商品吊牌价必须为数字");
			return;
		}
		if(isNaN($j("#validDate").val().trim())){
			loxia.tip($j("#validDate"),"有效期天数必须为数字");
			return;
		}
		if(isNaN($j("#warningDate").val().trim())){
			loxia.tip($j("#warningDate"),"预警天数必须为数字");
			return;
		}
		if(confirm("确认修改?")){
			var postData = loxia._ajaxFormToObj("editForm");
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/editallskubycode.json",postData);
			if(rs!=null&&rs.result=="success"){
				var url = baseUrl+"/findProduct2.json";
				$j("#tbl-product").jqGrid('setGridParam',{url:url}).trigger("reloadGrid");
				bindTable();
				$j("#back").trigger("click");
			}else{
				jumbo.showMsg(rs.msg);
			}
		}
	});
});
function queryInfo(){
	var url = baseUrl+"/findProduct2.json";
	$j("#tbl-product").jqGrid('setGridParam',{url:url,postData:loxia._ajaxFormToObj("form_query")}).trigger("reloadGrid");
	bindTable();
}
function bindTable(){
	jumbo.bindTableExportBtn("tbl-product",{},baseUrl+"/findProduct.json",loxia._ajaxFormToObj("form_query"));
}
function initBrand(){
	loxia.asyncXhrPost(
		window.parent.$j("body").attr("contextpath")+"/json/findAllBrand.do",
		{},
		{
			success:function(data){
				var rs = data.brandlist;
				if(rs.length>0){
					$j("#brandName option").remove();
					$j("#brandName").append("<option></option>");
					$j.each(rs,function(i,item){
						$j("#brandName").append("<option value='"+item.name+"'>"+item.name+"</option>");
					});
					$j("#brandName").flexselect();
					$j("#brandName").val("");
				}
			}
		}
	);
}
function editPanel(tag){
	var code = $j(tag).parent().attr("title");
	var name = $j(tag).parent().siblings().eq(2).attr("title");
	var supplierCode = $j(tag).parent().siblings().eq(3).attr("title");
	var brandName = $j(tag).parent().siblings().eq(4).attr("title");
	var length = $j(tag).parent().siblings().eq(5).attr("title");
	var width = $j(tag).parent().siblings().eq(6).attr("title");
	var height = $j(tag).parent().siblings().eq(7).attr("title");
	var netWeight = $j(tag).parent().siblings().eq(8).attr("title");
	var packageBarCode = $j(tag).parent().siblings().eq(11).attr("title");
	var skuCategoriesName = $j(tag).parent().siblings().eq(12).attr("title");
	var railwayStr = $j(tag).parent().siblings().eq(13).attr("title");
	var skuTypeId = $j(tag).parent().siblings().eq(15).attr("title");
	var countryOfOrigin = $j(tag).parent().siblings().eq(16).attr("title");
	var htsCode = $j(tag).parent().siblings().eq(17).attr("title");
	var unitName = $j(tag).parent().siblings().eq(18).attr("title");
	var consumable = $j(tag).parent().siblings().eq(9).attr("title");
	var enName = $j(tag).parent().siblings().eq(19).attr("title");
	var extensionCode1 = $j(tag).parent().siblings().eq(20).attr("title");
	var extensionCode2 = $j(tag).parent().siblings().eq(21).attr("title");
	var extensionCode3 = $j(tag).parent().siblings().eq(22).attr("title");
	
	var color = $j(tag).parent().siblings().eq(23).attr("title");
    var colorName = $j(tag).parent().siblings().eq(24).attr("title");
    var skuSize = $j(tag).parent().siblings().eq(25).attr("title");
    var extProp1 = $j(tag).parent().siblings().eq(26).attr("title");
    var extProp2 = $j(tag).parent().siblings().eq(27).attr("title");
    var extProp3 = $j(tag).parent().siblings().eq(28).attr("title");
    var extProp4 = $j(tag).parent().siblings().eq(29).attr("title");
    var listPrice = $j(tag).parent().siblings().eq(30).attr("title");
    var id = $j(tag).parent().siblings().eq(31).attr("title");
    var customerSkuCode = $j(tag).parent().siblings().eq(32).attr("title");
    var barCode2 = $j(tag).parent().siblings().eq(33).attr("title");
    var cuId = $j(tag).parent().siblings().eq(34).attr("title");
    var jmCode2 = $j(tag).parent().siblings().eq(35).attr("title");
    var validDate = $j(tag).parent().siblings().eq(36).attr("title");
    var warningDate = $j(tag).parent().siblings().eq(37).attr("title");
    var keyProperties = $j(tag).parent().siblings().eq(38).attr("title");
    var category = $j(tag).parent().siblings().eq(39).attr("title");
    
    
	var data = {};
	data["proCmd.cuId"]=cuId;
	var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/getCustomerIsAdidas.json",data);
	if(rs!=null&&rs.result=="success"){
		if(rs.brand=="1"){//进行ad判断逻辑
//			 $j("#code1").readOnly = false;
//			 $j("#customerSkuCode").readOnly = false;
			  document.getElementById("code1").readOnly = false;
			  document.getElementById("customerSkuCode").readOnly = false;
			  $j("#code1").css('color','#222222');
			  $j("#customerSkuCode").css('color','#222222');
			  $j("#code11").css('color','#222222');
			  $j("#customerSkuCode1").css('color','#222222');
		}
	}else{
		jumbo.showMsg("获取客户失败");
		return;		
	}
    
	$j("#editForm input").val("");
	$j("#id").val(id);
	$j("#code1").val(code);
	$j("#name1").val(name);
	$j("#supplierCode1").val(supplierCode);
	$j("#brandName1").val(brandName);
	$j("#length").val(length);
	$j("#width").val(width);
	$j("#height").val(height);
	$j("#grossWeight").val(netWeight);
	$j("#railwayStr").val(railwayStr);
	$j("#skuCategoriesName").val(skuCategoriesName);
	$j("#packageBarCode").val(packageBarCode);
	$j("#f1").addClass("hidden").siblings().removeClass("hidden");
	$j("#skuType").val(skuTypeId);
	$j("#countryOfOrigin").val(countryOfOrigin);
	$j("#htsCode").val(htsCode);
	$j("#unitName").val(unitName);
	$j("#consumable").val(consumable);
	$j("#enName").val(enName);
	$j("#extensionCode1").val(extensionCode1);
	$j("#extensionCode2").val(extensionCode2);
	$j("#extensionCode3").val(extensionCode3);
	
	$j("#color").val(color);
	$j("#colorName").val(colorName);
	$j("#skuSize").val(skuSize);
	$j("#extProp1").val(extProp1);
	$j("#extProp2").val(extProp2);
	$j("#extProp3").val(extProp3);
	$j("#extProp4").val(extProp4);
	$j("#listPrice").val(listPrice);
	
	$j("#customerSkuCode").val(customerSkuCode);
	$j("#barCode2").val(barCode2);
	$j("#cuId").val(cuId);
	$j("#jmCode2").val(jmCode2);
	
	$j("#validDate").val(validDate);
	$j("#warningDate").val(warningDate);
	
	$j("#keyProperties").val(keyProperties);
	$j("#category").val(category);
	
	
	
}