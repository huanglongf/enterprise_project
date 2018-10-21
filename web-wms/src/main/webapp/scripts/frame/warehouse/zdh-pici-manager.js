$j(document).ready(function (){
	$j("#import").click(function(){//批导入
		loxia.lockPage();
		$j("#importForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/zdhPiciImport.do")
		);
			loxia.submitForm("importForm");
			
			setTimeout(function(){
				var baseUrl = $j("body").attr("contextpath");
				var url = baseUrl + "/getHistoricalCodeList.json";
				$j("#tbl-zdhList").jqGrid('setGridParam',{url:url,datatype: "json",}).trigger("reloadGrid");
			},100);
	});
	
	$j("#tbl-zdhList").jqGrid({
		url:$j("body").attr("contextpath") + "/getHistoricalCodeList.json",
		datatype: "json",
		colNames: ["ID","批次号","状态","操作人","创建时间","当前节点"],
		colModel: [
							{name: "id", index: "id", hidden: true},
							{name:"code", index:"code", formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, width:100, resizable:true},
							{name:"statusName",index:"statusName",width:100,resizable:true},
							{name:"userName",index:"userName",width:100,resizable:true},
							{name:"createTime",index:"createTime",width:100,resizable:true},
							{name:"moveStatus",index:"moveStatus",hidden: true}
					 	],
		caption: '批次号列表',
	   	sortname: 'create_time',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#pager",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
	});
	
	$j("#selectOption").change(function(){
		var selectOption=$j("#selectOption").val();
		if(0==selectOption){
			jumbo.showMsg("请选择本次操作");
			return;
		}
		if(1==selectOption){
			$j("#IM").removeClass("hidden");
			$j("#wms4").addClass("hidden");
			$j("#restore").addClass("hidden");

		}
		if(2==selectOption){
			$j("#IM").addClass("hidden");
			$j("#wms4").removeClass("hidden");
			$j("#restore").addClass("hidden");
		}
		
		if(5==selectOption){
			$j("#IM").addClass("hidden");
			$j("#wms4").addClass("hidden");
			$j("#restore").removeClass("hidden");
		}
	});
	
	$j("#zdh_7").click(function(){
	    var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/insertToIMById.json",{"zdhId":$j("#zdhId").val()});
	    if(null!=rs){
	    	if("unavailable"==rs.result){
	    		jumbo.showMsg("此批次号无效");
				return;
	    	}
	    	if("job"==rs.result){
	    		jumbo.showMsg("仓库正在操作");
				return;
	    	}
	    	
	    	if("error"==rs.result){
	    		jumbo.showMsg("此批次号未发现对应的店铺及仓库");
				return;
	    	}
	    	if("success"==rs.result){
	    		jumbo.showMsg("成功");
				return;
	    	}
	    }
	});
	
	$j("#zdh_1").click(function(){
		 var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/createInvTxt.json",{"zdhId":$j("#zdhId").val()});
		 if(null!=rs){
			 if("unavailable"==rs.result){
		    		jumbo.showMsg("此批次号无效");
					return;
		     }
			 if("occupied"==rs.result){
				 jumbo.showMsg("仓库还有占用的数据");
					return;
			 }
			 if("statusError"==rs.result){
		    		jumbo.showMsg("此操作无效");
					return;
		     }
			 
		     if("txtError"==rs.result){
		    		jumbo.showMsg("此批次店铺无数据");
					return;
		     }
		     if("error"==rs.result){
		    		jumbo.showMsg("未找到此批次号");
					return;
		     }
		     if("uploadError"==rs.result){
		    		jumbo.showMsg("上传失败");
					return;
		     }
		     if("success"==rs.result){
		    		jumbo.showMsg("生成txt文件成功");
		    		$j("#zdh_1").attr("disabled", true); 
		    		$j("#zdh_1").css("background-color","yellow");
					return;
		     }
		 }
	});
	
	$j("#zdh_1_1").click(function(){
		window.location.href = $j("body").attr("contextpath") + "/warehouse/downloadBackUpInv.do?zdhId="+$j("#zdhId").val()+"&code="+$j("#code").text();
		$j("#zdh_1_1").css("background-color","yellow");
	});
	
	$j("#zdh_2").click(function(){
		 var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/backUpInv.json",{"zdhId":$j("#zdhId").val()});
         if(null!=rs){
        	 if("unavailable"==rs.result){
		    		jumbo.showMsg("此批次号无效");
					return;
		     }
			 if("statusError"==rs.result){
		    		jumbo.showMsg("此操作无效");
					return;
		     }
			 if("success"==rs.result){
		    		jumbo.showMsg("备份库存成功");
		    		$j("#zdh_2").attr("disabled", true); 
		    		$j("#zdh_2").css("background-color","yellow");
					return;
		     }
         }
	});
	
	$j("#zdh_3").click(function(){
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/backUpSta.json",{"zdhId":$j("#zdhId").val()});
        if(null!=rs){
       	 if("unavailable"==rs.result){
		    		jumbo.showMsg("此批次号无效");
					return;
		     }
			 if("statusError"==rs.result){
		    		jumbo.showMsg("此操作无效");
					return;
		     }
			 if("success"==rs.result){
		    		jumbo.showMsg("备份单据成功");
		    		$j("#zdh_3").attr("disabled", true); 
		    		$j("#zdh_3").css("background-color","yellow");
					return;
		     }
        }
	});
	
	$j("#zdh_4").click(function(){
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/cleanInvByCode.json",{"zdhId":$j("#zdhId").val()});
        if(null!=rs){
       	     if("unavailable"==rs.result){
		    		jumbo.showMsg("此批次号无效");
					return;
		     }else if("statusError"==rs.result){
		    		jumbo.showMsg("此操作无效");
					return;
		     }else if("success"==rs.result){
		    		jumbo.showMsg("清理库存成功");
		    		$j("#zdh_4").attr("disabled", true); 
		    		$j("#zdh_4").css("background-color","yellow");
					return;
		     }else if("errorSku"==rs.result) {
		    	 jumbo.showMsg(rs.errMsg);
				 return;
		     }
        }
	});
	$j("#zdh_5").click(function(){
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/checkStatusById.json",{"zdhId":$j("#zdhId").val()});
		if(null!=rs){
      	     if("unavailable"==rs.result){
		    		jumbo.showMsg("此批次号无效");
					return;
		     }else if("statusError"==rs.result){
		    		jumbo.showMsg("此操作无效");
					return;
		     }else if("success"==rs.result){
		    	var url=$j("body").attr("contextpath") + "/json/importStaByOwner.do?zdhId="+$j("#zdhId").val();
		 		$j("#exportFrame").attr("src",url);
		 		$j("#zdh_5").attr("disabled", true); 
				$j("#zdh_5").css("background-color","yellow");
		     }else {
		    	 jumbo.showMsg("系统异常");
				 return;
		     }
       }
		
		
		/*jumbo.showMsg("文件生成成功");
		$j("#zdh_5").attr("disabled", true); 
		$j("#zdh_5").css("background-color","yellow");*/
		
        /*if(null!=rs){
       	     if("unavailable"==rs.result){
		    		jumbo.showMsg("此批次号无效");
					return;
		     }else if("statusError"==rs.result){
		    		jumbo.showMsg("此操作无效");
					return;
		     }else if("success"==rs.result){
		    		jumbo.showMsg("清理库存成功");
		    		$j("#zdh_5").attr("disabled", true); 
		    		$j("#zdh_5").css("background-color","yellow");
					return;
		     }else if("none"==rs.result) {
		    	 jumbo.showMsg("未发现此批次号");
				 return;
		     }
        }*/
	});
	
	$j("#zdh_6").click(function(){
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/backUpStaStatus.json",{"zdhId":$j("#zdhId").val()});
        if(null!=rs){
       	     if("unavailable"==rs.result){
		    		jumbo.showMsg("此批次号无效");
					return;
		     }else if("statusError"==rs.result){
		    		jumbo.showMsg("此操作无效");
					return;
		     }else if("success"==rs.result){
		    		jumbo.showMsg("关闭单据成功");
		    		$j("#zdh_6").attr("disabled", true); 
		    		$j("#zdh_6").css("background-color","yellow");
					return;
		     }else if("none"==rs.result) {
		    	 jumbo.showMsg("未发现此批次号");
				 return;
		     }
        }
	});
	
	$j("#back").click(function(){
		$j("#tbl-detail").addClass("hidden");
		$j("#tbl-list").removeClass("hidden");
	});
});



function showDetail(obj){
	
	$j("#tbl-list").addClass("hidden");
	$j("#tbl-detail").removeClass("hidden");
	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");
	var pl=$j("#tbl-zdhList").jqGrid("getRowData",id);
	$j("#zdhId").val(id);
	var baseUrl = $j("body").attr("contextpath");
	$j("#code").text(pl["code"]);
	var moveStatus=pl["moveStatus"];
	if(moveStatus==2){
		$j("#zdh_1").attr("disabled", true); 
		$j("#zdh_1").css("background-color","yellow");
		$j("#zdh_1_1").css("background-color","yellow");
	}
	if(moveStatus==3){
		$j("#zdh_1").attr("disabled", true); 
		$j("#zdh_1").css("background-color","yellow");
		$j("#zdh_2").attr("disabled", true); 
		$j("#zdh_2").css("background-color","yellow");
		$j("#zdh_1_1").css("background-color","yellow");
	}
	if(moveStatus==4){
		$j("#zdh_1").attr("disabled", true); 
		$j("#zdh_1").css("background-color","yellow");
		$j("#zdh_2").attr("disabled", true); 
		$j("#zdh_2").css("background-color","yellow");
		$j("#zdh_3").attr("disabled", true); 
		$j("#zdh_3").css("background-color","yellow");
		$j("#zdh_1_1").css("background-color","yellow");
	}
	if(moveStatus==5){
		$j("#zdh_1").attr("disabled", true); 
		$j("#zdh_1").css("background-color","yellow");
		$j("#zdh_2").attr("disabled", true); 
		$j("#zdh_2").css("background-color","yellow");
		$j("#zdh_3").attr("disabled", true); 
		$j("#zdh_3").css("background-color","yellow");
		$j("#zdh_4").attr("disabled", true); 
		$j("#zdh_4").css("background-color","yellow");
		$j("#zdh_1_1").css("background-color","yellow");
	}
	if(moveStatus==6){
		$j("#zdh_1").attr("disabled", true); 
		$j("#zdh_1").css("background-color","yellow");
		$j("#zdh_2").attr("disabled", true); 
		$j("#zdh_2").css("background-color","yellow");
		$j("#zdh_3").attr("disabled", true); 
		$j("#zdh_3").css("background-color","yellow");
		$j("#zdh_4").attr("disabled", true); 
		$j("#zdh_4").css("background-color","yellow");
		$j("#zdh_5").attr("disabled", true); 
		$j("#zdh_5").css("background-color","yellow");
		$j("#zdh_1_1").css("background-color","yellow");
	}
	if(moveStatus==7){
		$j("#zdh_1").attr("disabled", true); 
		$j("#zdh_1").css("background-color","yellow");
		$j("#zdh_2").attr("disabled", true); 
		$j("#zdh_2").css("background-color","yellow");
		$j("#zdh_3").attr("disabled", true); 
		$j("#zdh_3").css("background-color","yellow");
		$j("#zdh_4").attr("disabled", true); 
		$j("#zdh_4").css("background-color","yellow");
		$j("#zdh_5").attr("disabled", true); 
		$j("#zdh_5").css("background-color","yellow");
		$j("#zdh_6").attr("disabled", true); 
		$j("#zdh_6").css("background-color","yellow");
		$j("#zdh_1_1").css("background-color","yellow");
	}
	
	$j("#tbl-zdh-detail").jqGrid({
		url:baseUrl + "/getHistoricalCodeListLine.json?zdhId="+id,
		datatype: "json",
		colNames: ["ID","店铺","仓库"],
		colModel: [
							{name: "id", index: "id", hidden: true},
							{name:"shopName",index:"shopName",width:300,resizable:true},
							{name:"ouName",index:"ouName",width:300,resizable:true}
					 	],
		caption: '批次号详细列表',
	   	sortname: 'id',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#pagerzdh",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
	});
	
	
		////////////////////run////////////////////////
		$j("#restore_1").click(function(){//库存数据恢复
		var plCode= $j("#code").text();
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/zdhReStoreData.json",{"plCode":plCode});
		if(result !=null){
			if("1" ==result["brand"] ){
				jumbo.showMsg("执行ok");
			}else{
				jumbo.showMsg(result["msg"]);
			}
		}else{
			jumbo.showMsg("系统异常");
		}
		});
		
		$j("#restore_2").click(function(){//单据状态恢复
			var plCode= $j("#code").text();
			var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/zdhReStoreStatus.json",{"plCode":plCode});
			if(result !=null){
				if("1" ==result["brand"] ){
					jumbo.showMsg("执行ok");
				}else{
					jumbo.showMsg(result["msg"]);
				}
			}else{
				jumbo.showMsg("系统异常");
			}
		});
	
}
