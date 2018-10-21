
$j.extend(loxia.regional['zh-CN'],{
	SAME_CODE_EXISTS : "当前仓库已经存在相同编码的库区！",
	
	LOCATION_CODE : "库区编码",
	LOCATION_NAME : "库区名称",
	CODE : "库位编码",
	BARCODE : " 库位条码",
	REMARK : "备注",
	
	LOCATION_NAME_EXISTS : "创建新库区失败，当前仓库已经存在同名的库区！",
	LOCATION_CREATE_SUCCESS : "创建新库区成功！",
	LOCATION_CREATE_FAILED : "创建新库区失败！",
	
	CODE_INSTALL_ERROR_X : "行{0}:系统库位编码设置错误：在未设置行标识（X）时，后续标识无效！",
	CODE_INSTALL_ERROR_Y : "行{0}:系统库位编码设置错误：在未设置行标识（Y）时，后续标识无效！",
	CODE_INSTALL_ERROR_Z : "系统库位编码设置错误：在未设置行标识（Z）时，后续标识无效！",
	CODE_INSTALL_RULE : "行标识X,Y,Z,单元格C 只允许字母或者数字",
	
	CODE_INSTALL : "行{0}:库位编码未设置",
	SYNCH_ERROR : "行{0}:库位编码设置方式错误：不能同时设置自定义和系统库位编码",
	LOCATION_CODE_ERROR : "行{0}:库位编码码设置错误：'{1}' 已存在",
	LOCATION_CODE_EXISTS : "行{0}:库位编码设置错误：'{1}' 在其他库区已存在",
	BARCODE_EXISTS_ERROR : "行{0}:库位条码设置错误：'{1}' 已存在",
	LOCATION_EXISTS_ERROR : "行{0}:库位条码设置错误：'{1}' 在其他库区已存在",
	SURE_SAVE : "您确定要保存当前库区的操作吗？",
	MANIPULATE_AGAIN : "您禁用了当前库区,但是当前库区仍然有正在使用的库位,请修正之后再操作！",
	CANNOT_FOTBID : "该库区有库存,不能被禁用！",
	SURE_FOTBID : "您确定要禁用当前库区吗？",
	UPDATE_PART_SUCCESS : "更新库区成功,但是有些库位编码/条码有重复,’{0}’,这些库位的操作失效！",
	UPDATE_SUCCESS : "更新库区成功！",
	UPDATE_FAILED : "更新库区失败,可能的原因是库位编码在该仓库有重复或是弹出口区域编码不正确！",
	DELETE_SELECT : "请选择要删除的数据！",
	
	DEFINED_LOCATION_CODE : "手工编制库位编码",
	LINE_IDENT : "行标识（{0}）",
	ENTITY_EXCELFILE		:	"正确的excel导入文件",
	CELL : "单元格（{0}）",
	MSG_LOCATION_ID:"请选择需要禁用的库位"
});
function i18(msg, args){return loxia.getLocaleMsg(msg,args);}
function openDistrict(district, focus) {
	var container = $j("#district-container").tabs();
	container.tabs("add",$j("body").attr("contextpath")+"/warehouse/locationsofdistrict.do?district.id=" + district.id, district.code);		
	var idx = container.find("li").length - 1;
	container.find("li:eq(" + idx + ")").attr("district", district.id);
	if(!!focus)
		container.tabs("select", idx);
};

function openDistricts(districts){
	if($j.isArray(districts)){
		for(var i=0,d;(d=districts[i]);i++)
			openDistrict(d);
	}else
		openDistrict(districts);
}
function existCode(){
	var code=$j.trim($j("#code").val());
	var r=false;
	$j("#district-container ul li a span").each(function(){
		if($j(this).html()==code){
			jumbo.showMsg(i18("SAME_CODE_EXISTS"));//当前仓库已经存在相同编码的库区！
			r=true;
			return false;
		}
	});
	return r;
}
function createDistrict(){
	//form validate here
	//sync save and return district
	/*
	 * 库区编码
	 * 库区名称
	 * 备注
	 */
	if(!jumbo.max("code",i18("LOCATION_CODE"),true)||!jumbo.max("name",i18("LOCATION_NAME"),true)||!jumbo.max("comment",i18("REMARK"))||existCode()){
		return false;
	}else{
		loxia.lockPage();
		var addForm=loxia._ajaxFormToObj("addForm");
		addForm["district.code"]=$j.trim(addForm["district.code"]);
		addForm["district.name"]=$j.trim(addForm["district.name"]);
		loxia.asyncXhrPost($j("body").attr("contextpath") + "/createdistrict.json",addForm,{
		success:function (data) {
			loxia.unlockPage();
			if(data.id=="failure"){
				jumbo.showMsg(i18("LOCATION_NAME_EXISTS"));//创建新库区失败，当前仓库已经存在同名的库区！
			}else{
				window.location=loxia.getTimeUrl($j("body").attr("contextpath")+"/warehouse/locationmaintainentry.do?district.code="+$j.trim(addForm["district.code"]));
				jumbo.showMsg(i18("LOCATION_CREATE_SUCCESS"));//创建新库区成功！
			}
		   },
		 error:function(data){
			  loxia.unlockPage(); 
			  jumbo.showMsg(i18("LOCATION_CREATE_FAILED"));//创建新库区失败！
		 }
		});
	}
	
}
function validateCode(tr,idx){
	var $x = tr.find("td:eq(3) input"),$y =tr.find("td:eq(4) input"),$z = tr.find("td:eq(5) input"),$c = tr.find("td:eq(6) input");
	var $x_e=$j.trim($x.val())== "",$y_e=$j.trim($y.val())== "",$z_e=$j.trim($z.val())== "",$c_e=$j.trim($c.val())== "";
	if(!jumbo.max(tr.find("td:eq(1) input"),i18("DEFINED_LOCATION_CODE"),false,100)||!jumbo.max($x,i18("LINE_IDENT","X"),false,10)
			||!jumbo.max($y,i18("LINE_IDENT","Y"),false,10)
			||!jumbo.max($z,i18("LINE_IDENT","Z"),false,10)||!jumbo.max($x,i18("CELL","C"),false,10)
			||!jumbo.max(tr.find("td:eq(7) input"),i18("BARCODE"),false,100)
			||!jumbo.max(tr.find("td:eq(10) input"),i18("REMARK"),false,100)){
		return false;
	}
	var reg=/[\w]*/i;
	if((!$x_e&&!(reg.test($j.trim($x.val()))))
			||(!$y_e&&!(reg.test($j.trim($y.val()))))
			||(!$z_e&&!(reg.test($j.trim($z.val()))))
			||(!$c_e&&!(reg.test($j.trim($c.val()))))){
		jumbo.showMsg(i18("CODE_INSTALL_RULE"));//行标识X,Y,Z,单元格C 只允许字母或者数字
		return false;
	}
	if($x_e&&(!$y_e||!$z_e||!$c_e)){
		jumbo.showMsg(i18("CODE_INSTALL_ERROR_X",(idx+1)));//行"+(idx+1)+":系统库位编码设置错误：在未设置行标识（X）时，后续标识无效！
		return false;
	}else if(!$x_e&&$y_e&&(!$z_e||!$c_e)){
		jumbo.showMsg(i18("CODE_INSTALL_ERROR_Y",(idx_1)));//行"+(idx+1)+":系统库位编码设置错误：在未设置行标识（Y）时，后续标识无效！
		return false;
	}else if(!$x_e&&!$y_e&&$z_e&&(!$c_e)){
		jumbo.showMsg(i18("CODE_INSTALL_ERROR_Z"));//系统库位编码设置错误：在未设置行标识（Z）时，后续标识无效！
		return false;
	}else{
		var code=[];
		if(!$x_e)code.push($j.trim($x.val()));
		if(!$y_e)code.push($j.trim($y.val()));
		if(!$z_e)code.push($j.trim($z.val()));
		if(!$c_e)code.push($j.trim($c.val()));
		tr.find("td:eq(2) input").val(code.join("-"));
		return true;
	}
}
function getCodeMap(){
	var codeMap={};
	$j.each($j("#district-container div.ui-tabs-hide"),function(i,e){
		$j(e).find("div.district table tbody:eq(0)").find("tr").each(function(idx){
			var $cc = $j("td:eq(1) input", this),
			$v = $j("td:eq(2) input", this);
			var c = $j.trim($cc.val())==""?$v.val():$j.trim($cc.val());
			codeMap[c] = c;
		});
	});
	return codeMap;
}
function getBarCodeMap(){
	var codeMap={};
	$j.each($j("#district-container div.ui-tabs-hide"),function(i,e){
		$j(e).find("div.district table tbody:eq(0)").find("tr").each(function(idx){
			var c=$j.trim($j("td:eq(7) input", this).val());
			codeMap[c] =c;
		});
	});
	return codeMap;
}
function checkIsAvailable(obj,panel){
	loxia.lockPage()
	loxia.asyncXhrPost($j("body").attr("contextpath") + "/checkfordisabledistrict.json",
		{"district.id":$j("#districtId",panel).val()},{
		success:function (data) {
			loxia.unlockPage();
			if(data.enable=="false"){
				jumbo.showMsg(i18("CANNOT_FOTBID"));//该库区有库存,不能被禁用！
				$j(obj).val("true");
			}else{
				$j.each($j(".district table tbody:eq(0) tr",panel),function(i,item){
					$j(this).find("td:eq(8) input").attr("checked",false);
				});
			}
		}
	});
}
$j(document).ready(function (){
 	var $tabs = $j("#district-container").tabs();		
	$tabs.tabs({
		cache: true,
		load: function( event, ui ) {
			$j.each($tabs.find("li:not(.ui-state-active) a"),function(i,e){
					$j($j(this).attr("href")).addClass("ui-tabs-hide");
				});
			$j("div.district input:not(:checkbox)", ui.panel).addClass("ui-loxia-default");
			loxia.initContext($j(ui.panel));
			var status=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.json",{"categoryCode":"status"});
			var container = $j("#district-container").tabs(),districtId=container.find(".ui-tabs-selected").attr("district");
			var tbl="tbl-locations-"+districtId;
			$j("#"+tbl).jqGrid({
					url:$j("body").attr("contextpath")+"/json/locationsofdistrictjson.json?district.id="+districtId,
					datatype: "json",
					colNames: ["ID","库位编码","手工编制库位编码","系统编制库位编码预览","行标识（X）","行标识（Y）","行标识（Z）","单元格（C）",
						"库位条码","使用状态","弹出口编码","备注"],
					colModel: [
					           {name: "id", index: "id", hidden: true},	
					           {name: "code", index: "code"},
					           {name: "manualCode", index: "manualCode"},		         
					           {name: "sysCompileCode", index: "sysCompileCode"},		         
					           {name: "dimX", index: "dimX", width: 60},		         
					           {name: "dimY", index: "dimY", width: 60},
					           {name: "dimZ", index: "dimZ", width: 60},
					           {name: "dimC", index: "dimC", width: 60},
					           {name: "barCode", index: "barCode"},
					           {name: "isAvailable", index: "isAvailable", width: 60, formatter:'select',editoptions:status},
					           {name: "popUpAreaCode", index: "popUpAreaCode"},
					           {name: "memo", index: "memo"}
					         ],
					caption: "库位列表",
			   	sortname: 'code',
			    multiselect: true,
				sortorder: "desc",
				width:"auto",
				height:jumbo.getHeight(),
				viewrecords: true,
   				rownumbers:true,
				rowNum: jumbo.getPageSize(),
				rowList:jumbo.getPageSizeList(),
				postData:{"columns":"id,code,manualCode,sysCompileCode,dimX,dimY,dimZ,dimC,barCode,isAvailable,popUpAreaCode,memo"},
			   	pager:"#"+tbl+"-pager",
				jsonReader: { repeatitems : false, id: "0" }
				});
			$j("div.district table tbody:eq(0)", ui.panel).find("tr").each(function(idx){
					var _tr=$j(this);
					$j(this).find("td:eq(3) input,td:eq(4) input,td:eq(5) input,td:eq(6) input").bind("blur",function(e){
						validateCode(_tr,idx);
					});
					$j(this).find("td:eq(8) input").bind("click",function(e){
						if(!$j(this).attr("checked")){
							loxia.lockPage()
							loxia.asyncXhrPost($j("body").attr("contextpath") + "/checkfordisablelocation.json",
								{"district.id":$j(this).parents("tr").find("td:eq(10) input:eq(1)").val()},{
								success:function (data) {
									loxia.unlockPage();
									if(data.enable=="false"){
										jumbo.showMsg(i18("CANNOT_FOTBID"));//该库位有库存,不能被禁用！
										_tr.find("td:eq(8) input").attr("checked",true);
									}
								}
							});
					}
				});
			});
			$j("#isAvailable",ui.panel).change(function(){
				if($j(this).val()=="false"){
					if(window.confirm(i18("SURE_FOTBID"))){//您确定要禁用当前库区吗？
						checkIsAvailable($j(this),ui.panel);
					}else{
						$j(this).val("true");
					}
					
				}
			});
			$j("#save",ui.panel).click(function(){
				//validate and save data here
				if(jumbo.max($j("#districtName",ui.panel),i18("LOCATION_NAME"),true)&&window.confirm(i18("SURE_SAVE"))){//您确定要保存当前库区的操作吗？
					var confuse=false;
				    if($j("#isAvailable",ui.panel).val()=="false"){
				    	$j.each($j(".district table tbody:eq(0) tr",ui.panel),function(i,item){
				    		if($j(this).find("td:eq(8) input").attr("checked")){
				    			confuse=true;
				    		}
							return false;
						});
				    	if(confuse){
				    		jumbo.showMsg(i18("MANIPULATE_AGAIN"));//您禁用了当前库区,但是当前库区仍然有正在使用的库位,请修正之后再操作！
				    		return false;
				    	}
				    }
				var errMsg = [], codeMap = {}, otherCodeMap=getCodeMap(),barCodeMap={},otherBarCodeMap=getBarCodeMap();
				var s=true;
				$j("div.district table tbody:eq(0)", ui.panel).find("tr").each(function(idx){
					if(!validateCode($j(this),idx)){
						s=false;
						return s;
					}
					var $cc = $j("td:eq(1) input", this),
					$v = $j("td:eq(2) input", this);
					if($j.trim($cc.val()) == "" && $j.trim($v.val()) == ""){
						errMsg.push(i18("CODE_INSTALL",(idx+1)));//行"+(idx+1)+":库位编码未设置
						$j(this).addClass("error");
					}else if($j.trim($cc.val()) != "" && $j.trim($v.val()) != ""){
						errMsg.push(i18("SYNCH_ERROR",(idx+1)));//行"+(idx+1)+":库位编码设置方式错误：不能同时设置自定义和系统库位编码
						$j(this).addClass("error");
					}else{
						var c = $j.trim($cc.val())==""?$v.val():$j.trim($cc.val());
						if(c in codeMap){
							errMsg.push(i18("LOCATION_CODE_ERROR",[(idx+1),c]));//行"+(idx+1)+":库位编码码设置错误：'" + c + "' 已存在
							$j(this).addClass("error");
						}else if(c in otherCodeMap){
							errMsg.push(i18("LOCATION_CODE_EXISTS",[(idx+1),c]));//行"+(idx+1)+":库位编码设置错误：'" + c + "' 在其他库区已存在
							$j(this).addClass("error");
						}else{
							codeMap[c] = c;
							$j(this).removeClass("error");
							$j("td:eq(10) input:last", this).val(c);
						}							
					}
					var barCode=$j.trim($j("td:eq(7) input", this).val());
					if(barCode!=""){
						if(barCode in barCodeMap){
							errMsg.push(i18("BARCODE_EXISTS_ERROR",[(idx+1),barCode]));//行"+(idx+1)+":库位条码设置错误：'" + barCode + "' 已存在
							$j(this).addClass("error");
						}else if(barCode in otherBarCodeMap){
							errMsg.push(i18("LOCATION_EXISTS_ERROR",[(idx+1),barCode]));//行"+(idx+1)+":库位条码设置错误：'" + barCode + "' 在其他库区已存在
							$j(this).addClass("error");
						}else{
							barCodeMap[barCode] = barCode;
							$j(this).removeClass("error");
						}	
					}
				});
				if(!s){
					return false;
				}else if(errMsg.length > 0){
					jumbo.showMsg(errMsg);
				}else{
					//reload after success
					loxia.lockPage();
					$j.each($j(".district table tr.add",ui.panel),function(i,item){
						$j(this).find(":input").each(function(){
							$j(this).attr("name",$j(this).attr("name").replace(/\(.*\)/ig,"["+i+"]"));
						});
					});
					var upateForm=loxia._ajaxFormToObj("updateForm"+$j(this).attr("targetId"));
					var updaetIndex=-1,addIndex=-1;
					$j.each($j("div.district table tbody:eq(0) tr",ui.panel),function(i,item){
						if($j(this).find("td:eq(0) input").length>0){
							addIndex+=1;
							upateForm['locations['+addIndex+'].isAvailable']=true;//$j(this).find("td:eq(8) input").attr("checked");
						}else{
							updaetIndex+=1;
							upateForm['district.locations['+updaetIndex+'].isAvailable']=true;//$j(this).find("td:eq(8) input").attr("checked");
						}
					});
					loxia.asyncXhrPost($j("body").attr("contextpath") + "/updatedistrict.json",
						upateForm,{
					success:function (data) {
						$tabs.tabs("url",ui.index,loxia.getTimeUrl($j("body").attr("contextpath")+"/warehouse/locationsofdistrict.do?district.id=" + upateForm["district.id"]));
						$tabs.tabs("load",ui.index);
						loxia.unlockPage();
						if(data&&data.msg){
//							var err=data.errors.split(";"),arr=[];
//							for(var i=0;i<err.length;i++){
//								var codes=err[i].split(",");
//								arr.push(i18("CODE") +codes[0]+i18("BARCODE")+codes[1]);//库位编码   ,库位条码
//							}
//							jumbo.showMsg(i18("UPDATE_PART_SUCCESS",arr.join(";")));//更新库区成功,但是有些库位编码/条码有重复,’"+arr.join(";")+"’,这些库位的操作失效！
							jumbo.showMsg(data.msg);
						}else{
							jumbo.showMsg(i18("UPDATE_SUCCESS"));//更新库区成功！
						}
					   },
					error:function (data){
						loxia.unlockPage(); 
			  			jumbo.showMsg(i18("UPDATE_FAILED"));//更新库区失败,可能的原因是库位编码在该仓库有重复！
					}
					});
					
				} 
				}
			});
			$j("#disable",ui.panel).click(function(){
				var districtId=$j(this).attr("targetId"),tbl="tbl-locations-"+districtId;
				var locationIds=$j("#"+tbl).jqGrid('getGridParam','selarrrow');
				var postData={};
				if(locationIds&&locationIds==0){
					jumbo.showMsg(i18("MSG_LOCATION_ID"));
				}else{
					$j.each(locationIds,function(i,e){
						postData['locations['+i+"].id"]=e;
					});
					loxia.lockPage();
					loxia.asyncXhrPost($j("body").attr("contextpath") + "/disablelocations.json",
						postData,{
					success:function (data) {
						$tabs.tabs("url",ui.index,loxia.getTimeUrl($j("body").attr("contextpath")+"/warehouse/locationsofdistrict.do?district.id=" + districtId));
						$tabs.tabs("load",ui.index);
						loxia.unlockPage();
						if(data&&data.msg){
							jumbo.showMsg(data.msg);
						}else{
							jumbo.showMsg(i18("MSG_SUCCESS"));
						}
					   },
					error:function (data){
						loxia.unlockPage(); 
			  			jumbo.showMsg(i18("MSG_FAILURE"));
					}
					});
				}
			});
			$j("div.district button[action='delete']",ui.panel).click(function(){
				if($j("div.district table tbody:eq(0) tr.ui-state-highlight", ui.panel).length==0){
					jumbo.showMsg(i18("DELETE_SELECT"));//请选择要删除的数据！
					return false;
				}
				return true;
			});
			$j("#import",ui.panel).click(function(){
				var file=$j.trim($j("#districtFile",ui.panel).val()),errors=[];
				if(file==""||file.indexOf("xls")==-1){
					errors.push(i18("INVALID_MAND",i18("ENTITY_EXCELFILE")));
				}
				if(errors.length>0){
					jumbo.showMsg(errors.join("<br/>"));
				}else{
					$j("#importForm",ui.panel).attr("action",
						loxia.getTimeUrl($j("body").attr("contextpath") 
							+ "/warehouse/locationimport.do?district.id="+$j("#districtId",ui.panel).val()));
					loxia.submitForm($j("#importForm",ui.panel));
				}
			});
			
			$j("#importPopUp").click(function(){
				var file=$j.trim($j("#importPopUpFile").val()),errors=[];
				if(file==""||file.indexOf("xls")==-1){
					errors.push(i18("INVALID_MAND",i18("ENTITY_EXCELFILE")));
				}
				if(errors.length>0){
					jumbo.showMsg(errors.join("<br/>"));
				}else{
					$j("#importPopUpForm").attr("action",
						loxia.getTimeUrl($j("body").attr("contextpath") 
							+ "/auto/locationAndPopUpImport.do"));
					loxia.submitForm($j("#importPopUpForm"));
				}
			});
			
	$j("#export",ui.panel).click(function(){
		var iframe = document.createElement("iframe");
		iframe.src = loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/locationtplexport.do");
		iframe.style.display = "none";
		$j("#download").append($j(iframe));
	});
		}
	});
	loxia.asyncXhrGet($j("body").attr("contextpath") + "/districtlist.json",{},{
		success:function (data) {
			openDistricts(data);
			if($j("#targetTab").val().length>0){
				$j.each($j("#district-container ul li span"),function(i,e){
					if($j(this).html()==$j("#targetTab").val()){
						$tabs.tabs("select", i);
						return false;
					}
				});
			}
		   }
		});
	$j("#btn-district-create").click(function(){
		createDistrict();
	});
});