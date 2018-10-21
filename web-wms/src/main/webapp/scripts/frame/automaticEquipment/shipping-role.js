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
	$j("#dialog_addRoleLine").dialog({
		title: "修改规则明细", 
		modal:true,
		closeOnEscape:false, 
		autoOpen: false, 
		width: 760,
		close: function() { 
			roleLineId = ""; // 初始化
			$j("#selectLpCode").find("option").remove();
			$j("#pointName").find("option").remove();
			$j("#shopLikeQuery").find("option").remove();
			$j("#selectLpCode").append( "<option value=\"\">--请选择--</option>" );
			$j("#pointName").append( "<option value=\"\">--请选择--</option>" );
			$j("#shopLikeQuery").append( "<option value=\"\">--请选择--</option>" );
		}
	});//弹窗属性设置
	
	//查询
	$j("#search").click(function(){
		 var postData = loxia._ajaxFormToObj("form_query");  
			$j("#tbl-shipping-role-line").jqGrid('setGridParam',{
				url:$j("body").attr("contextpath")+"/findRoleLineByRoleId.json",page:1,postData:postData}).trigger("reloadGrid");
		});
	
	//重置
	$j("#reset").click(function(){
		$j("#form_query input").val("");
	});
	
	//新增
	$j("#add").click(function(){
		$j("#div1").addClass("hidden");
		$j("#div2").removeClass("hidden");
		$j("#form_info input").val("");
		$j("#roleCode1").attr("disabled",false);
		$j("#tbl-shipping-role-line").clearGridData(true);
//	    // 获取地区集合
	    //加载省
		var provinceList = loxia.syncXhrPost($j("body").attr("contextpath") + "/findAreaByPaream.json",{"type":1,"parentId":1});
		for(var j = 0 ; j < provinceList.areaList.length ; j++){
			$j("<option value='" + provinceList.areaList[j].areaId + "'>"+ provinceList.areaList[j].areaName +"</option>").appendTo($j("#provinceikeQuery"));
		}
	});
	

	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	$j("#tbl-shipping-role-line").jqGrid({
		url:window.$j("body").attr("contextpath")+"/json/findRoleLineByRoleId.do",
		datatype: "json",
		colNames: ["ID","物流商","省","市","区","地址","时效类型","店铺","集货口","集货ID","是否COD","作业类型","作业单号","优先级","修改行","删除行"],
		colModel: [
							{name: "id", index:"id",hidden: true,width:80,resizable:true},
							{name:"lpcode",index:"lpcode", width:100,resizable: true,sortable:false},
							{name:"province",index:"province",width:100,resizable:true},
							{name:"city",index:"city",width:100,resizable:true},
							{name:"district",index:"district",width:100,resizable:true},
							{name:"address",index:"address",width:200,resizable:true},
							{name:"timeTypeStr",index:"timeTypeStr",width:120,resizable:true},
							{name:"owner",index:"owner",width:220,resizable:true},
							{name:"pointName",index:"pointName",width:200,resizable:true},
							{name:"pointId",index:"pointId",hidden: true,width:200,resizable:true},
							{name:"isCodStr",index:"isCodStr",width:120,resizable:true},
							{name:"types",index:"types",width:120,resizable:true,formatter:'select',editoptions:staType},
							{name:"staCode",index:"staCode",width:180,resizable:true},
							{name:"sort",index:"sort",width:100,resizable:true},
							{name:"btnUpdate",index:"btnUpdate",width:150,resizable:true},
							{name:"btnDelete",index:"btnDelete",width:150,resizable:true}
				 		],
     	caption: "集货规则明细",
    	sortname: 'id',
	   	pager:"#pager",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
    	multiselect: true,
    	height:"auto",
    	width:980,
    	rownumbers:true,
    	 viewrecords: true,
		jsonReader: {repeatitems : false, id: "0" },
		gridComplete: function(){
			var btn1 = '<div><button type="button" style="width:80px;" class="confirm" name="btnUpdate" loxiaType="button" onclick="updateRoleLine(this);">'+"修改"+'</button></div>';
			var btn2 = '<div><button type="button" style="width:80px;" name="btnDelete" loxiaType="button" onclick="deleteRoleLine(this);">'+"删除"+'</button></div>';
			var ids = $j("#tbl-shipping-role-line").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				$j("#tbl-shipping-role-line").jqGrid('setRowData',ids[i],{"btnUpdate":btn1,"btnDelete":btn2});
			}
			loxia.initContext($j(this));
		}
	});
	
	
	$j("#addSub").click(function(){
		//加载弹出口区域
		var pointList = loxia.syncXhrPost($j("body").attr("contextpath") + "/findPointList.json");
		for(var j = 0 ; j < pointList.pointList.length ; j++){
			$j("<option value='" + pointList.pointList[j].pointId + "'>"+ pointList.pointList[j].pointName +"</option>").appendTo($j("#pointName"));
		}
		
		//加载物流商
		var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransactionTypeName.do");
		for(var idx in result){
			$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selectLpCode"));
		}
		//加载平台店铺
		
		var shopLikeQuerys = loxia.syncXhrPost(window.$j("body").attr("contextpath") + "/findshoplistbycompanyid.json");
		for(var idx in shopLikeQuerys){
			$j("<option value='" + shopLikeQuerys[idx].code + "'>"+ shopLikeQuerys[idx].name +"</option>").appendTo($j("#shopLikeQuery"));
		}
		initShopQuery("companyshop","innerShopCode");
	    
		$j("#form_info2 input,#form_info2 select").val("");
		$j("#dialog_addRoleLine").dialog("open");
	//    
	});
	
	
	$j("#closeSub").click(function(){
		$j("#dialog_addRoleLine").dialog("close");
		roleLineId = ""; // 初始化
		$j("#selectLpCode").find("option").remove();
		$j("#pointName").find("option").remove();
		$j("#shopLikeQuery").find("option").remove();
		$j("#selectLpCode").append( "<option value=\"\">--请选择--</option>" );
		$j("#pointName").append( "<option value=\"\">--请选择--</option>" );
		$j("#shopLikeQuery").append( "<option value=\"\">--请选择--</option>" );
	});
	
	$j("#cityLikeQuery").click(function(){
		var s = $j("#provinceikeQuery").val();
		if(s != "" && s !="--请选择--"){
			return;
		}else{
			alert("请先选择省");
			return;
		}
	});
	
	$j("#districtLikeQuery").click(function(){
		var s = $j("#cityLikeQuery").val();
		if(s != "" && s !="--请选择--"){
			return;
		}else{
			alert("请先选择市");
			return;
		}
	});
	
	$j("#provinceikeQuery").change(function(){
		$j("#cityLikeQuery option").each(function(){ //遍历全部option
	   		 $j(this).remove();
	   	 });
		$j("#districtLikeQuery option").each(function(){ //遍历全部option
	   		 $j(this).remove();
		});
	   	$j("#cityLikeQuery").append("<option>--请选择--</option>");
	   	$j("#districtLikeQuery").append("<option>--请选择--</option>");
		   	
	    if ($j("#provinceikeQuery").val() != "" && $j("#provinceikeQuery").val() != "--请选择--"){
	    	var provinceKey = $j("#provinceikeQuery").val(); //模糊查询下拉框key
//	    	var city = $j("#cityLikeQuery").flexselect();
	    	 $j("#cityLikeQuery option").each(function(){ //遍历全部option
	    		 $j(this).remove();
	    	 });
	 		var areaLists = loxia.syncXhrPost($j("body").attr("contextpath") + "/findAreaByPaream.json",{"type":2,"parentId":provinceKey});
		    // 加载市
	 		$j("#cityLikeQuery").append("<option>--请选择--</option>");
			for(var j = 0 ; j < areaLists.areaList.length ; j++){
				$j("<option value='" + areaLists.areaList[j].areaId + "'>"+ areaLists.areaList[j].areaName +"</option>").appendTo($j("#cityLikeQuery"));
			}
			
			setTimeout(function(){
				var cityKey = $j("#cityLikeQuery").val();
				if(cityKey == "--请选择--"){
					return;
				}
				$j("#districtLikeQuery option").each(function(){ //遍历全部option
		    		 $j(this).remove();
		    	 });
		 		var areaList2 = loxia.syncXhrPost($j("body").attr("contextpath") + "/findAreaByPaream.json",{"type":3,"parentId":cityKey});
			    // 加载区
		 		$j("#districtLikeQuery").append("<option>--请选择--</option>");
				for(var j = 0 ; j < areaList2.areaList.length ; j++){
					$j("<option value='" + areaList2.areaList[j].areaId + "'>"+ areaList2.areaList[j].areaName +"</option>").appendTo($j("#districtLikeQuery"));
				}
			},300);
	    }
	 });
	
	$j("#cityLikeQuery").change(function(){
		$j("#districtLikeQuery option").each(function(){ //遍历全部option
			 $j(this).remove();
		 });
		$j("#districtLikeQuery").append("<option>--请选择--</option>");
		if ($j("#cityLikeQuery").val() != "" && $j("#cityLikeQuery").val() != "--请选择--"){
				var cityKey = $j("#cityLikeQuery").val();
				if(cityKey == "--请选择--"){
					return;
				}
				$j("#districtLikeQuery option").each(function(){ //遍历全部option
		    		 $j(this).remove();
		    	 });
		 		var areaList2 = loxia.syncXhrPost($j("body").attr("contextpath") + "/findAreaByPaream.json",{"type":3,"parentId":cityKey});
			    // 加载区
		 		$j("#districtLikeQuery").append("<option>--请选择--</option>");
				for(var j = 0 ; j < areaList2.areaList.length ; j++){
					$j("<option value='" + areaList2.areaList[j].areaId + "'>"+ areaList2.areaList[j].areaName +"</option>").appendTo($j("#districtLikeQuery"));
				}
		}
	});
    
	$j("#saveSub").click(function(){
		var postData = loxia._ajaxFormToObj("form_info2");
		// 回归注释
//		var province=$j("#provinceikeQuery").find("option:selected").text();
//		var city=$j("#cityLikeQuery").find("option:selected").text();
//		var district=$j("#districtLikeQuery").find("option:selected").text();
		var owner=$j("#shopLikeQuery").val();
//		if(province == "--请选择--"){
//			province ="";
//		}
//		if(city == "--请选择--"){
//			city ="";
//		}
//		if(district == "--请选择--"){
//			district ="";
//		}
		if(owner == "--请选择--"){
			owner ="";
		}
		postData["roleLine.id"] = roleLineId;
		postData["roleLine.owner"] = owner;
		// 回归注释
//		postData["roleLine.province"] = province;
//		postData["roleLine.city"] = city;
//		postData["roleLine.district"] = district;
		var errorMsg = loxia.validateForm("form_info");
		if(errorMsg.length == 0){
		    var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/saveOrUpdateRoleLine.json",postData);
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
	
	// 文件导入
	$j("#import").click(function(){
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg("请选择正确的Excel导入文件");
			return;
		}
		loxia.lockPage();
		$j("#importForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/auto/importShippingRole.do"));
		loxia.submitForm("importForm");
		setTimeout(function(){
			$j("#file").val("");
			var baseUrl = $j("body").attr("contextpath");
			var transPortUrls = baseUrl + "/findRoleLineByRoleId.json";
			$j("#tbl-shipping-role-line").jqGrid('setGridParam',{url:transPortUrls,datatype: "json",}).trigger("reloadGrid");
		},500);
		
	});
	
	/**
	 * 批量删除
	 */
	$j("#batchRemove").click(function(){
		var ids = $j("#tbl-shipping-role-line").jqGrid('getGridParam','selarrrow');
		if (null != ids && ids.length > 0) {
			if(confirm("确定要删除吗？")){
				var batchIds = ids.join(",");
				var res = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/batchremovebyrule.json",{"batchIds":batchIds});
			    if(null != res && res.result == 'success'){
					var url = window.$j("body").attr("contextpath")+"/json/findRoleLineByRoleId.do";
					$j("#tbl-shipping-role-line").jqGrid('setGridParam', {url:url,page:1,postData:loxia._ajaxFormToObj("form_query")}).trigger("reloadGrid");
					jumbo.showMsg("删除成功!");
			    }else {
			    	jumbo.showMsg("删除失败或系统异常,请联系管理员");
			    }
			}
		}else {
			jumbo.showMsg("请勾选需要操作的数据")
		}
		
	});
});

//修改规则明细
function updateRoleLine(tag){
	$j("#form_info2 input,#form_info2 select").val("");
	$j("#dialog_addRoleLine").dialog("open");
    // 加载数据
	var id = $j(tag).parents("tr").attr("id");
	var date = $j("#tbl-shipping-role-line").jqGrid("getRowData",id);
    roleLineId = id; // 规则明细ID赋值
    var lpcode = date["lpcode"]; 
    var province = date["province"];
    var city = date["city"];
    var district = date["district"];
    var sort = date["sort"];
    var timeTypeStr = date["timeTypeStr"];
    var owner = date["owner"];
    var isCodStr = date["isCodStr"];
    var staCode = date["staCode"];
    var types = date["types"];
    var pointId = date["pointId"];
    var address = date["address"];
	// ---------------------------- 初始化数据start-----------------------
	//加载物流商
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransactionTypeName.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selectLpCode"));
	}
	initShopQuery("companyshop","innerShopCode");
	//加载平台店铺
	var shopLikeQuerys = loxia.syncXhrPost(window.$j("body").attr("contextpath") + "/findshoplistbycompanyid.json");
	for(var idx in shopLikeQuerys){
		$j("<option value='" + shopLikeQuerys[idx].code + "'>"+ shopLikeQuerys[idx].name +"</option>").appendTo($j("#shopLikeQuery"));
	}
	//加载弹出口区域
	var pointList = loxia.syncXhrPost($j("body").attr("contextpath") + "/findPointList.json");
	for(var j = 0 ; j < pointList.pointList.length ; j++){
		$j("<option value='" + pointList.pointList[j].pointId + "'>"+ pointList.pointList[j].pointName +"</option>").appendTo($j("#pointName"));
	}
	
	 //加载省 回归注释
//	var provinceList = loxia.syncXhrPost($j("body").attr("contextpath") + "/findAreaByPaream.json",{"type":1,"parentId":1});
//	for(var j = 0 ; j < provinceList.areaList.length ; j++){
//		$j("<option value='" + provinceList.areaList[j].areaId + "'>"+ provinceList.areaList[j].areaName +"</option>").appendTo($j("#provinceikeQuery"));
//	}
//	$j("#provinceikeQuery option").each(function (){
//	    if($j(this).text()==province){
//	        $j(this).attr('selected',true);
//	    }
//	});
//	    if (province != "" && province != null){
//	    	var provinceKey = $j("#provinceikeQuery").val(); //模糊查询下拉框key
//	    	 $j("#cityLikeQuery option").each(function(){ //遍历全部option
//	    		 $j(this).remove();
//	    	 });
//	 		var areaLists = loxia.syncXhrPost($j("body").attr("contextpath") + "/findAreaByPaream.json",{"type":2,"parentId":provinceKey});
//		    // 加载市
//	 		$j("#cityLikeQuery").append("<option>--请选择--</option>");
//			for(var j = 0 ; j < areaLists.areaList.length ; j++){
//				$j("<option value='" + areaLists.areaList[j].areaId + "'>"+ areaLists.areaList[j].areaName +"</option>").appendTo($j("#cityLikeQuery"));
//			}
//			$j("#cityLikeQuery option").each(function (){
//			    if($j(this).text()==city){
//			        $j(this).attr('selected',true);
//			    }
//			});
//			var cityKey = $j("#cityLikeQuery").val();
//			$j("#districtLikeQuery option").each(function(){ //遍历全部option
//	    		 $j(this).remove();
//	    	 });
//	 		var areaList2 = loxia.syncXhrPost($j("body").attr("contextpath") + "/findAreaByPaream.json",{"type":3,"parentId":cityKey});
//		    // 加载区
//	 		$j("#districtLikeQuery").append("<option>--请选择--</option>");
//			for(var j = 0 ; j < areaList2.areaList.length ; j++){
//				$j("<option value='" + areaList2.areaList[j].areaId + "'>"+ areaList2.areaList[j].areaName +"</option>").appendTo($j("#districtLikeQuery"));
//			}
//			$j("#districtLikeQuery option").each(function (){
//			    if($j(this).text()==district){
//			        $j(this).attr('selected',true);
//			    }
//			});
//	    }

    // ---------------------------- 初始化数据end-----------------------
    if(isCodStr == "是"){
    	$j("#isCod").val("1");
    }else if(isCodStr == "否"){
    	$j("#isCod").val("0");
    }
    if(timeTypeStr == "普通"){
    	$j("#selTrans8").val("1");
    }else if(timeTypeStr == "及时达"){
    	$j("#selTrans8").val("3");
    }else if(timeTypeStr == "当日"){
    	$j("#selTrans8").val("5");
    }else if(timeTypeStr == "次日"){
    	$j("#selTrans8").val("6");
    }else if(timeTypeStr == "次晨"){
    	$j("#selTrans8").val("7");
    }
    //$j("#shopLikeQuery").val(owner);
    //$j("#typeList option:selected").html(types);
    $j("#typeList").val(types);
    $j("#staCode").val(staCode);
    $j("#sort").val(sort);
    $j("#selectLpCode").val(lpcode);
    $j("#address").val(address);
    $j("#provinceInput").val(province);
    $j("#cityInput").val(city);
    $j("#districtInput").val(district);
    $j("#pointName").val(pointId);
	$j("#shopLikeQuery option").each(function (){
	    if($j(this).text()==owner){
	        $j(this).attr('selected',true);
	    }
	});
	//$j("#shopLikeQuery").attr('selected',true);
   // $j("#shopLikeQuery").find("option[value='1松下电工专卖']").attr("selected", true);
   
    //$j("#provinceikeQuery_flexselect").val(province);
    //$j("#cityLikeQuery_flexselect").val(city);
    //$j("#districtLikeQuery_flexselect").val(district);
    //$j("#shopLikeQuery_flexselect").val(owner);
    //alert("--"+lpcode+"--"+province+"--"+city+"--"+sort+"--"+district+"--"+timeTypeStr+"--"+owner+"--"+isCodStr+"--"+staCode+"--"+types);
}

//删除规则明细
function deleteRoleLine(tag){
	if(confirm("确定要删除吗？")){
		var id = $j(tag).parents("tr").attr("id");
		var data = $j("#tbl-shipping-role-line").jqGrid("getRowData",id);
		var postData = {};
		postData["roleId"] = data["id"];
		var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/deleteRoleLineById.json",postData);
	    if(rs.result=='success'){
			jumbo.showMsg("操作成功！");
			$j("#tbl-shipping-role-line").find("tr[id="+data["id"]+"]").remove();
	    }else {
	    	if(rs.msg != null){
				jumbo.showMsg(rs.msg);
			}else{
				jumbo.showMsg("操作失败！");
			}
	    }
	}
}
