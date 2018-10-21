
$j.extend(loxia.regional['zh-CN'],{
	
	CANEL_DISABLE_PRINT : "当前订单状态为‘取消已处理’不能打印",
	FAILED_DISABLE_PRINT : "当前订单状态为‘配货失败’不能打印"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

var packageNum = 0;

var userName = "";
var dateValue = "";
var fileUrl = "";
var fileName = "";
var ouName = "";

$j(document).ready(function (){
	
	//获取仓库
	var resultopc = loxia.syncXhrPost($j("body").attr("contextpath")+"/selectAllPhyWarehouse.json");
	for(var idx in resultopc.pwarelist){
		$j("<option value='" + resultopc.pwarelist[idx].id + "'>"+ resultopc.pwarelist[idx].name +"</option>").appendTo($j("#selTransOpc"));
	}
	
	userName = $j("#userinfo", window.parent.document).attr("lgname");
	dateValue = $j("#userinfo", window.parent.document).attr("sysdate");
	ouName = $j("#userinfo", window.parent.document).attr("ouName");
	
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransactionTypeName.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#lpcode"));
	}

	$j("#tbl_temp_list").jqGrid({
		datatype: "json",
		//colNames: ["indexId","SKUID","LOCATIONID","INVENTORYSTATUSID","SKU编码","商品编码","条形码","扩展名","店铺","库位","状态","数量"],
		colNames: ["ID","类型","物流商","物理仓ID","物理仓","快递单号","重量","拒收原因","备注","操作"],
		colModel: [
		        {name:"id", index: "id", hidden: true,sortable:false},	
	            {name:"type",index:"type",width:120,resizable: true},
	            {name:"lpcode",index:"lpcode",width:120,resizable:true},
	            {name:"phyWhId", index: "phyWhId", hidden: true,sortable:false},
	            {name:"whName",index:"whName",width:150,resizable:true},
	            {name:"trackingNo",index:"trackingNo",width:180,resizable:true},
	            {name:"weight",index:"weight",width:180,resizable:true},
	            {name:"rejectionReasons",index:"rejectionReasons",width:250,resizable:true},
	            {name:"remarksb",index:"remarksb",width:250,resizable:true},
	            {name:"reset", width: 80,resizable:true, formatter:"buttonFmatter", formatoptions:{"buttons":{label:"删除", onclick:"deleteRow(this,event);"}}}
	            ],
		caption: "登记退换货入库包裹列表",
		multiselect: false,
		rownumbers:true,
		height:"auto",
		rowNum: 500,
		rowList:[500,1000],
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			
		},
		onSelectRow:function(id){
		}
	});
	
	
	$j("#trackingNo").keydown(function(evt) {
		if (evt.keyCode === 13) {
			var  ifEnteringWeight =  $j.trim($j('input:radio[name="saveWeight"]:checked').val());
			if(ifEnteringWeight == "记录"){
			$j("#weight").val("");
			$j("#weight").focus();
			}else{
				addPacking();
			}
		}
	});
	$j("#weight").keydown(function(evt) {
		if(evt.keyCode === 13){
			$j("#barcode").val("");
			$j("#barcode").focus();
		
		}
	});
	$j("#barcode").keydown(function(evt) {
		if(evt.keyCode === 13){
			addPacking();
			//$j("#trackingNo").focus();
		}
	});
	
	$j("#addPacking").click(function(){
		addPacking();
	});
	
	$j("#photograph").click(function(){
		//alert("此功能还在待开发中...");
		var pNo = $j.trim($j("#trackingNo").val());
		var lpcode = $j.trim($j("#lpcode").val());
		if(pNo == "" || lpcode == ""){
			jumbo.showMsg("请选择填写好登记信息后再做拍照！");
			return;
		}
		setTimeout(function(){
			$j(document).scrollTop(0);
			$j(document).scrollTop($j(document).scrollTop() + 1);
			var timestamp=new Date().getTime();
			try{
				cameraPhotoJs(dateValue + "/" + ouName + "/" + pNo + "/" + lpcode, userName+"_"+timestamp);
				fileUrl = dateValue + "/" + ouName + "/" + pNo + "/" + lpcode+"/"+userName+"_"+timestamp;
				fileName += userName+"_"+timestamp+".jpg"+"/";
				cameraPaintMultiJs(fileUrl);
			} catch (e) {
//				jumbo.showMsg(i18("重拍失败！"));//初始化系统参数异常
			};
			$j(document).scrollTop($j(document).scrollTop() - 1);
			$j("#trackingNo").focus();
		},200);
	});
	
	$j('input:radio[name="type"]').click(function(){
		var type =  $j.trim($j('input:radio[name="type"]:checked').val());
		if(type == "已拒收"){
			$j("td [name='rejection_reasons']").removeClass("hidden");
		} else {
			$j("td [name='rejection_reasons']").addClass("hidden");
			$j("#rejection_reasons").val("");
		}
	});
	
	$j('input:radio[name="saveWeight"]').click(function(){
		var  ifEnteringWeight =  $j.trim($j('input:radio[name="saveWeight"]:checked').val());
		if(ifEnteringWeight == "记录"){
			$j("tr [name='entering_weight']").removeClass("hidden");
			$j("tr [name='confirm_sku']").removeClass("hidden");
			
		} else {
			$j("tr [name='entering_weight']").addClass("hidden");
			$j("tr [name='confirm_sku']").addClass("hidden");
			$j("#entering_weight").val("");
		}
	});
	
	
	$j("#executeInventory").click(function(){
		var datalist = $j("#tbl_temp_list" ).getRowData();
		if(datalist.length == 0){
			jumbo.showMsg("登记退换货入库包裹列表，请录入包裹信息再登记！");
			return;
		}
		loxia.lockPage();
		var postData = {};
		for(var i=0;i<datalist.length;i++){
			postData["rpcommList[" + i + "].intStatus"]=(datalist[i].type == '已拒收' ? 0:1);
			postData["rpcommList[" + i + "].lpcode"]=datalist[i].lpcode;
			postData["rpcommList[" + i + "].phyWhId"]=datalist[i].phyWhId;
			postData["rpcommList[" + i + "].trackingNo"]=datalist[i].trackingNo;
			postData["rpcommList[" + i + "].remarksb"]=datalist[i].remarksb;
			postData["rpcommList[" + i + "].rejectionReasons"]=datalist[i].rejectionReasons;
			postData["rpcommList[" + i + "].weight"]=datalist[i].weight;
	   	}
		loxia.asyncXhrPost(window.$j("body").attr("contextpath")+"/returnpackagehand.json",
				postData,
				{
				success:function(data){
					if(data){
						if(data.result=="success"){
							loxia.unlockPage();
							updatePackageNum(-1 * (datalist.length));
							jumbo.showMsg("操作成功");
							$j("#rejection_reasons").val("");
							$j("#lpcode").val("");
							$j("#remarks").val("");
							$j("#trackingNo").val("");
							$j("#tbl_temp_list").jqGrid("clearGridData");
							loxia.initContext($j("#tbl_temp_list"));
							if($j('#print').is(':checked')) {
								var url = $j("body").attr("contextpath") + "/printReturnPackage.json?batchCode="+data.batchCode;
								printBZ(loxia.encodeUrl(url),true);				
							}
						}else if(data.result=="error"){
							loxia.unlockPage();
							jumbo.showMsg(data.message);
						}
					} else {
						loxia.unlockPage();
						jumbo.showMsg("数据操作异常");
					}
				}
		});
	});
	
	try{
		closeGrabberJs();
//		jumbo.showMsg("关闭摄像头成功!!！");//初始化系统参数异常
	}catch (e) {
//		jumbo.showMsg("关闭摄像头失败！");//初始化系统参数异常
	}
	try{
		openCvPhotoImgJs();//打开照相功能
//		jumbo.showMsg("打开照相功能成功！");
	} catch (e) {
//		jumbo.showMsg("打开照相功能失败！");//初始化系统参数异常
	}
	
});

function deleteRow(tag,event){
	var id = $j(tag).parents("tr").attr("id");
	var data = $j("#tbl_temp_list").jqGrid('getRowData',id);
	var msg = "你确定要删除 "+data['lpcode']+"["+data['trackingNo']+"] 的 "+data['type']+" 包裹，的信息吗？";
	if(window.confirm(msg)){
		updatePackageNum(-1);
		$j("#tbl_temp_list tr[id='" + id + "']").remove();
		loxia.initContext($j("#tbl_temp_list"));
    }
} 

function addPacking(){
	var type =  $j.trim($j('input:radio[name="type"]:checked').val());
	if(type == ""){
		jumbo.showMsg("请选择 登记类型！");
		return;
	}
	var rejectionReasons =  $j.trim($j("#rejection_reasons").val());
	if(type == "已拒收" && rejectionReasons == ""){
		jumbo.showMsg("请选择 拒收原因！");
		return ;
	}
	var lpcode =  $j.trim($j("#lpcode").val());
	var whouId =  $j.trim($j("#selTransOpc").val());
	if(lpcode == ""){
		jumbo.showMsg("请选择 退回包裹物流商！");
		return ;
	}
	if(whouId == ""){
		jumbo.showMsg("请选择 退回包裹所属物理仓！");
		return ;
	}
	var trackingNo =  $j.trim($j("#trackingNo").val());
	if(trackingNo == ""){
		jumbo.showMsg("快递单号不能为空，请填写包裹快递单号！");
		return ;
	}
	if(trackingNo.length <6||trackingNo.length>20){
		jumbo.showMsg("快递单号长度不在6-20个字符之间！");
		return ;
	}
	var  ifEnteringWeight =  $j.trim($j('input:radio[name="saveWeight"]:checked').val());
	var weight=null;
	if(ifEnteringWeight == "记录"){
		 weight= $j.trim($j("#weight").val());
		 if(isNaN(weight)){
			 jumbo.showMsg("重量字段非法"); 
			 return;
		 }else if(weight<=0){
			 jumbo.showMsg("重量不能小于0"); 
			 return; 
		 }
	}
	var isTrackingNoInput = false;
	var trackingNoInput  = 1;
	var rowId = 0;
	$j.each($j("#tbl_temp_list").jqGrid("getRowData"),function(i,row){
		if(row['trackingNo'] == trackingNo){
			// 不存在相同的条码则添加
			isTrackingNoInput= true;
			trackingNoInput = i;
		}
		rowId = row['id'];
	});
	if(isTrackingNoInput == true){
		jumbo.showMsg("快递单号【"+trackingNo+"】已录入，在 登记退换货入库包裹列表 的第"+(trackingNoInput+1)+"行！");
		return ;
	}
	if($j.trim($j("#remarks").val()).length > 450){
		jumbo.showMsg("备注信息太长，请简化。（最大支持450个字）");
		return;
	}
	
	var phyId = $j("#selTransOpc").val();
	var whName =$j("#selTransOpc option:selected").text();
	$j("#photograph").click();
	rowId = parseInt(rowId) + 1;
	updatePackageNum(1);
	var row = {};
	row['id']=rowId;
	row['type'] = type;
	row['lpcode'] = lpcode;
	row['phyWhId'] = phyId;
	row['whName'] = whName;
	row['trackingNo'] = trackingNo;
	row['remarksb'] =  $j.trim($j("#remarks").val());
	row['rejectionReasons'] = rejectionReasons;
	row['weight'] = weight;
	$j("#tbl_temp_list").jqGrid('addRowData',rowId,row);
	loxia.initContext($j("#tbl_temp_list"));
	$j("#trackingNo").val("");
	$j("#remarks").val("");
//	$j("#trackingNo").focus();
}

function cameraPhoto(pNo, staNo, barCode,timestamp) {
//	var timestamp=new Date().getTime();
	cameraPhotoJs(dateValue + "/" + ouName + "/" + pNo + "/" + staNo, barCode + "_" + userName+"_"+timestamp);
	fileName += barCode + "_" + userName+"_"+timestamp+".jpg"+"/";
//	setTimeout(cameraPaintJs(dateValue + "/" + pNo + "/" + staNo+"/"+barCode + "_" + userName+"_"+timestamp),1000);
}

/**
 * 更新显示数量
 * @param num 更新数量
 * @returns
 */
function updatePackageNum(num){
	packageNum = packageNum + parseInt(num);
	$j("#packageNum").html(packageNum);
}

/**
 * 执行批量导入退换货登记信息
 */
$j(document).ready(function (){
	$j("#sn_import").click(function(){
			if(!/^.*\.xls$/.test($j("#file").val())){
				jumbo.showMsg("请选择要导入的文件！");
				return;
			}
			if(importPackage()){
				var postData={};
				var whouId =  $j.trim($j("#selTransOpc").val());
				var type =  $j.trim($j('input:radio[name="type"]:checked').val());
				var remarks=$j.trim($j("#remarks").val());
				var whName =$j.trim($j("#whName").val());
				var rejectionReasons =  $j.trim($j("#rejection_reasons").val());
				var whName =$j("#selTransOpc option:selected").text();
				postData["packages.phyWhId"]=whouId;
				postData["packages.type"]=type;
				postData["packages.remarksb"]=remarks;
				postData["packages.rejectionReasons"]=rejectionReasons;
				postData["packages.whName"]=whName;
				
				$j("#importForm").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/returnPackageImport.do"));
					loxia.submitForm("importForm");
				/*$j("#importForm").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/returnPackageImport.do"));
				$j("#importForm").submit();*/
				//alert("已导入")
				//loxia.unlockPage();
				var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/checkImportPackage.json");
				if(result.message=="error1"){
					jumbo.showMsg("行数据缺失，请检查后重新导入！");
					loxia.unlockPage();
					$j("#tbl_temp_list").jqGrid("clearGridData");
					return;
				}else if(result.message=="error2"){
					jumbo.showMsg("快递单号位数不能小于10位，请检查后重新导入！");
					loxia.unlockPage();
					$j("#tbl_temp_list").jqGrid("clearGridData");
					return;
				}else if(result.message=="error3"){
					jumbo.showMsg("存在无效物流商编码，请检查后重新导入！");
					loxia.unlockPage();
					$j("#tbl_temp_list").jqGrid("clearGridData");
					return;
				}else if(result.message=="error4"){
					jumbo.showMsg("导入数据存在重复，请检查后重新导入！");
					loxia.unlockPage();
					$j("#tbl_temp_list").jqGrid("clearGridData");
					return;
				}else if(result.message=="error5"){
					jumbo.showMsg("导入数据不可以超过200，请检查后重新导入！");
					loxia.unlockPage();
					$j("#tbl_temp_list").jqGrid("clearGridData");
					return;
				}else {
				$j("#tbl_temp_list").jqGrid('setGridParam',{url:$j("body").attr("contextpath") + "/returnPackageInfo.json"
				,postData:postData}).trigger("reloadGrid");
				loxia.unlockPage();
				}
			}
		});
});

//批量导入退换货信息验证
function importPackage(){
	var type =  $j.trim($j('input:radio[name="type"]:checked').val());
	if(type == ""){
		jumbo.showMsg("请选择 登记类型！");
		return false;
	}
	var rejectionReasons =  $j.trim($j("#rejection_reasons").val());
	if(type == "已拒收" && rejectionReasons == ""){
		jumbo.showMsg("请选择 拒收原因！");
		return false;
	}
	var lpcode =  $j.trim($j("#lpcode").val());
	var whouId =  $j.trim($j("#selTransOpc").val());
	if(whouId == ""){
		jumbo.showMsg("请选择 退回包裹所属物理仓！");
		return false;
	}
	var trackingNo =  $j.trim($j("#trackingNo").val());

	var isTrackingNoInput = false;
	var trackingNoInput  = 1;
	var rowId = 0;
	if(isTrackingNoInput == true){
		jumbo.showMsg("快递单号【"+trackingNo+"】已录入，在 登记退换货入库包裹列表 的第"+(trackingNoInput+1)+"行！");
		return false;
	}
	if($j.trim($j("#remarks").val()).length > 450){
		jumbo.showMsg("备注信息太长，请简化。（最大支持450个字）");
		return false;
	}
	return true;
}
