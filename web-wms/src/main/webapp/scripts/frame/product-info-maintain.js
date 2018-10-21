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
		url:baseUrl+"/findProduct.json",
		datatype: "json",
		postData:loxia._ajaxFormToObj("form_query"),
		colNames: ["id",i18("JM_CODE"),i18("PRO_NAME"),i18("SUPPLIER_CODE"),i18("BRAND_NAME"),i18("LENGTH"),i18("WIDTH"),i18("HEIGHT"),
		           i18("WEIGHT"),i18("IS_CONSUMABLE"),"箱型名称","箱型条码",i18("CATEGORYNAME"),i18("ISRAIL"),"商品上架类型","商品上架类型ID","原产地","报关编码","单位名称"],
		colModel: [{name:"id",index:"id",hidden:true},        
		           {name: "jmCode", index: "jmCode", width: 120,formatter:"linkFmatter",formatoptions:{onclick:"editPanel"},resizable: true},
		           {name: "name", index: "name", width: 150, resizable: true},
		           {name: 'barCode', index: "barCode", width: 200, resizable: true},
		           {name: "brandName",index:"brandName",width:150,resizable:true},
		           {name: "length", index: "length", width: 50, resizable: true},
		           {name: "skuCost", index: "skuCost", width: 50, resizable: true},
		           {name: "height", index: "height", width: 50, resizable: true},
		           {name: "netWeight", index: "netWeight", width: 60, resizable: true},
		           {name: "isConsumable", index: "isConsumable", width: 60, resizable: true},
		           {name: "packageSkuName", index: "packageSkuName", width: 100, resizable:true},
		           {name: "packageBarCode", index: "packageBarCode", width: 100, resizable:true},
		           {name: "categoryName", index: "categoryName", width: 100, resizable: true},
		           {name: "isRail", index: "isRail", width:50, resizable: true,formatter:'select',editoptions:trueOrFalse},
		           {name: "skuTypeName", index: "skuTypeName", width: 60, resizable: true},
		           {name: "skuTypeId", index: "skuTypeId", width: 60,hidden:true, resizable: true},
		           {name: "countryOfOrigin", index: "countryOfOrigin", width: 60,hidden:true, resizable: true},
		           {name: "htsCode", index: "htsCode", width: 60,hidden:true, resizable: true},
		           {name: "unitName", index: "unitName", width: 60,hidden:true, resizable: true}
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
		var url = baseUrl+"/findProduct.json";
		$j("#tbl-product").jqGrid('setGridParam',{url:url,postData:loxia._ajaxFormToObj("form_query")}).trigger("reloadGrid");
		bindTable();
	});
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select option:first").attr("selected",true);
	});
	$j("#import").click(function(){
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));//请选择正确的Excel导入文件
			return false;
		}
		//loxia.lockPage();
		var flag = $j("#nikeFlag").val();
		var brand = $j("#brandName").val();
		$j("#importForm").attr("action",window.parent.$j("body").attr("contextpath") + "/warehouse/updateProductInfo.do?nikeFlag=" + flag + "&brandName=" + brand);
		$j("#importForm").submit();
	});
	$j("#skuTypeImport").click(function(){
		if(!/^.*\.xls$/.test($j("#skuTypeImportFile").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));//请选择正确的Excel导入文件
			return false;
		}
		loxia.lockPage();
		$j("#skuTypeImportForm").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/auto/skuTypeImport.do")	);
		loxia.submitForm("skuTypeImportForm");
	});
	
	$j("#skuSpTypeImport").click(function(){
		if(!/^.*\.xls$/.test($j("#skuSpTypeImportFile").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));//请选择正确的Excel导入文件
			return false;
		}
		//loxia.lockPage();
		$j("#skuSpTypeImportForm").attr("action",window.parent.$j("body").attr("contextpath") + "/auto/skuSpTypeImport.do");
		$j("#skuSpTypeImportForm").submit();
	});
	
	
	$j("#channelSkuSpTypeImport").click(function(){
		if(!/^.*\.xls$/.test($j("#channelSkuSpTypeImportFile").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));//请选择正确的Excel导入文件
			return false;
		}
		//loxia.lockPage();
		$j("#channelSkuSpTypeImportForm").attr("action",window.parent.$j("body").attr("contextpath") + "/auto/channelSkuSpTypeImport.do");
		$j("#channelSkuSpTypeImportForm").submit();
	});
//	$j("#btnSearchBrandName").click(function(){
//		$j("#BrandNameQueryDialog").dialog("open");
//	});
	$j.flexselect.prototype.afterEvent = function(){
		var selected = this.results[this.selectedIndex];
	      if (selected) {
	    	  var currentValue = selected.value
	    	  if(null != currentValue && (currentValue.toUpperCase().indexOf("NIKE")>0)  || (currentValue.toUpperCase().indexOf("耐克")>0)){
	    		  var url = $j("body").attr("contextpath") + "/warehouse/excelDownload.do?fileName=NIKE商品基础信息维护-体积重量.xls&inputPath=tplt_import_nike_info_maintain.xls";
	    		  $j("#downloadTemplate").attr("href",url);
	    		  $j("#nikeFlag").val(true);
	    	  }else{
	    		  var url = $j("body").attr("contextpath") + "/warehouse/excelDownload.do?fileName=商品基础信息维护-体积重量.xls&inputPath=tplt_import_pro_info_maintain.xls";
	    		  $j("#downloadTemplate").attr("href",url);
	    		  $j("#nikeFlag").val(false);
	    	  }
	      }
	};
	$j("#back").click(function(){
		$j("#f2").addClass("hidden").siblings().removeClass("hidden");
	});
	$j("#edit").click(function(){
		if($j("#length").val().trim()==""||isNaN($j("#length").val().trim())){
			loxia.tip($j("#length"),"长度必填且必须为数字");
			return;
		}
		if($j("#width").val().trim()==""||isNaN($j("#width").val().trim())){
			loxia.tip($j("#width"),"宽度必填且必须为数字");
			return;
		}
		if($j("#height").val().trim()==""||isNaN($j("#height").val().trim())){
			loxia.tip($j("#height"),"高度必填且必须为数字");
			return;
		}
		if($j("#grossWeight").val().trim()==""||isNaN($j("#grossWeight").val().trim())){
			loxia.tip($j("#grossWeight"),"净重必填且必须为数字");
			return;
		}
		if(confirm("确认修改?")){
			var postData = loxia._ajaxFormToObj("editForm");
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/editskubycode.json",postData);
			if(rs!=null&&rs.result=="success"){
				var url = baseUrl+"/findProduct.json";
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
	var url = baseUrl+"/findProduct.json";
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
	
	$j("#editForm input").val("");
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
}