$j(document).ready(function(){
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-provide").jqGrid({
		url:baseUrl + "/findProvide.json",
		datatype: "json",
		colNames: ["物流简称","店铺","已导入数量","剩余数量","是否SF子运单","SF子运单月结账号"],
		colModel: [
							{name:"lpcode",index:"lpcode",width:100,resizable:true},
							{name:"owner",index:"owner",width:200,resizable:true},
							{name:"qty",index:"qty",width:110,resizable:true},
							{name:"quantity",index:"quantity",width:110,resizable:true},
							{name:"checkboxSf1",index:"checkboxSf1",width:110,resizable:true},
							{name:"jcustid",index:"jcustid",width:110,resizable:true}
				 		],
     	caption: "运单号列表",
	   	sortname: 'lpcode',
	   	multiselect: false,
		sortorder: "desc",
		width:800,
	   	shrinkToFit:false,
		height:"auto",
		viewrecords: true,
		jsonReader: {repeatitems : false, id: "0"}
		
	});
	
	$j("#search").click(function(){
		var url = baseUrl + "/findProvide.json";
		$j("#tbl-provide").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("form_query")}).trigger("reloadGrid");
	});
	$j("#lpcode").change(function(){
		var lpcode = $j("#lpcode").val();
		if(lpcode=="SF"){
			$j("#transSfCheckbox").removeClass("hidden");
			$j("#jcustidSf").removeClass("hidden");
		}else{
			$j("#checkboxSf").attr("checked",false);
			$j("#jcustid").val("");
			$j("#transSfCheckbox").addClass("hidden");
			$j("#jcustidSf").addClass("hidden");
		}
	});
	
	$j("#reset").click(function(){
		$j("#queryTable select").val("");
	});
	
	$j("#import").click(function(){
		var lpcode = $j("#lpcode").val(); 
		var checkboxSf=$j('#checkboxSf').attr('checked');
		var jcustid=$j('#jcustid').val();
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg("请选择正确的Excel导入文件");
			return;
		}
		loxia.lockPage();
	
//		if(lpcode=="SF"){
//			if (checkboxSf) {
//				if(jcustid==""){
//					jumbo.showMsg("SF子单号,请填写月结账号");
//					return;
//				}
//			}
//		}
		$j("#importForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/transProvideImport.do?lpcode="+lpcode+"&jcustid="+jcustid+"&checkboxSf="+checkboxSf));
		loxia.submitForm("importForm");
	});
});
