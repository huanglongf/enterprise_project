$j(document).ready(function(){
	$j("#dialog_addHbqd").dialog({title: "创建合并渠道", modal:true,closeOnEscape:false, autoOpen: false, width: 610});//弹窗属性设置
	$j("#dialog_updateHbqd").dialog({title: "编辑合并渠道", modal:true,closeOnEscape:false, autoOpen: false, width: 610});//弹窗属性设置
	var baseUrl = $j("body").attr("contextpath");
	//根据运营中心仓库查询
	var resultopc = loxia.syncXhrPost($j("body").attr("contextpath")+"/selectbyopc.json");
	for(var idx in resultopc.warelist){
		$j("<option value='" + resultopc.warelist[idx].id + "'>"+ resultopc.warelist[idx].name +"</option>").appendTo($j("#selTransOpc"));
	}
	document.getElementById('one').style.display = "";
	$j("#tbl-channel").jqGrid({
		url:baseUrl + "/findChannelRef.json",
		datatype: "json",
		colNames: ["ID","仓库名称","渠道名称","过期时间","渠道ID","创建时间","编码","类型","仓库ID"],
		colModel: [
		           			{name:"id",index:"id",width:80,resizable:true,hidden:true},
							{name:"warehName",index:"warehName",width:180,resizable:true},
							{name:"channName",index:"channName",width:175,resizable:true},
							{name:"expTime",index:"expTime",width:157,resizable:true},
							{name:"hbChId",index:"hbChId",width:140,resizable:true,hidden:true},
							{name:"createTime",index:"createTime",width:140,resizable:true,hidden:true},
							{name:"channCode",index:"channCode",width:80,resizable:true,hidden:true},
							{name:"chType",index:"chType",width:50,resizable:true,hidden:true},
							{name:"whId",index:"whId",width:50,resizable:true,hidden:true}
				 		],
     	caption: "合并发货渠道信息列表",
    	sortname: 'createTime',
	   	sortorder: "desc",
	   	multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pager1",
	   	height:jumbo.getHeight(),
		viewrecords: false,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		subGrid : true,
		subGridRowExpanded: function(subgrid_id, row_id) {
			var postData = {};
			var whId =  $j("#tbl-channel #" + row_id +" td[aria-describedby='tbl-channel_whId']").html();
			var hbChId =  $j("#tbl-channel #" + row_id +" td[aria-describedby='tbl-channel_hbChId']").html();
			postData["list.whId"] = whId;
			postData["list.hbChId"] = hbChId;
		    var subgrid_table_id;
		    subgrid_table_id = subgrid_id+"_t";
		      $j("#"+subgrid_id).html("<table id='"+subgrid_table_id+"'></table>");
		      $j("#"+subgrid_table_id).jqGrid({
		          url: baseUrl + "/findChildChannelRef.json",
		          postData : postData,
		          datatype: "json",
		          colNames:["ID","仓库名称","子渠道名称","过期时间","子渠道ID"],
		          colModel: [
								{name:"id",index:"id",width:150,resizable:true,hidden:true},
								{name:"warehName",index:"warehName",width:180,resizable:true},
								{name:"name",index:"name",width:175,resizable:true},
								{name:"expTime",index:"expTime",width:155,resizable:true},
								{name:"childChannelIdList",index:"childChannelIdList",width:155,resizable:true,hidden:true}
		            	],
		        	sortname: 'id',
		        	height: "100%",
		            multiselect: false,
		        	sortorder: "desc",
		        	rowNum:-1,
		        	jsonReader: { repeatitems : false, id: "0" }
		       });}
	});
	
	$j("#threeLeft").jqGrid({
		//url:baseUrl + "/findChannelRef.json",
		datatype: "json",
		colNames: ["ID","渠道编码","渠道名称"],
		colModel: [
							{name: "id", hidden: true},
							{name:"code",index:"code", width:80,resizable:true,hidden: true},
							{name:"name",index:"name",width:165,resizable:true},
				 		],
     	caption: "已选择渠道",
    	sortname: 'id',
    	multiselect: true,
    	height:175,
    	width:200,
	   	sortorder: "desc",
		shrinkToFit:false,
		viewrecords: false,
		jsonReader: {repeatitems : false, id: "0" }
	});
	
	$j("#fourSingleTable").jqGrid({
		datatype: "json",
		colNames: ["ID","渠道编码","渠道名称"],
		colModel: [
							{name: "id", index:"id",hidden: true,width:80,resizable:true},
							{name:"code",index:"code", width:80,resizable:true,hidden: true},
							{name:"name",index:"name",width:250,resizable:true}
				 		],
     	caption: "已选择渠道",
    	sortname: 'id',
    	multiselect: false,
    	height:175,
    	width:290,
	   	sortorder: "desc",
		shrinkToFit:false,
		viewrecords: false,
		rownumbers:true,
		jsonReader: {repeatitems : false, id: "0" }
	});
	
	$j("#fourMoreTable").jqGrid({
		datatype: "json",
		colNames: ["ID","渠道编码","渠道名称","默认数据(选中行)"],
		colModel: [
							{name: "id", index:"id",hidden: true,width:80,resizable:true},
							{name:"code",index:"code", width:80,resizable:true,hidden: true},
							{name:"name",index:"name",width:240,resizable:true},
							 { 
				                name: 'MY_ID',
				                index: 'MY_ID',
				                sortable: false,
				                align:'center',
				                width: 105,
				                formatter:function(){
				                    return "<input type='radio' name='myId' />";
				                }
				            }
				 		],
     	caption: "已选择渠道",
    	sortname: 'id',
    	multiselect: false,
    	height:175,
    	width:385,
	   	sortorder: "desc",
		shrinkToFit:false,
		viewrecords: false,
		rownumbers:true,
		jsonReader: {repeatitems : false, id: "0" },
		onSelectRow: function(ids) {
            $j($j(this)[0]).find("input[name='myId']")[ids - 1].checked = true;
        }
	});

	//查询
	$j("#search").click(function(){
			var whId = $j("#selTransOpc").val();
			var postData = loxia._ajaxFormToObj("channelForm");
			$j("#threeRight").jqGrid('setGridParam',{
				url:window.$j("body").attr("contextpath")+"/findBiChannelListByType.json?whouid="+whId,//根据仓库查找渠道
				postData:postData,
				page:1
			}).trigger("reloadGrid");
	});
	
	$j("#addHbqd").click(function(){ //创建合并渠道
		$j("#dialog_addHbqd").removeClass("hidden");
		//$j("#dialog_addHbqd").prev().find("a").addClass("hidden");
		$j("#dialog_addHbqd").dialog("open");
	});
	
	$j("#selTransOpc").change(function(){
		$j("#threeLeft").clearGridData(false);
	});
	
	$j("#back").click(function(){
		var oneDiv =document.getElementById("one").style.display;// 获取div隐藏熟悉 none:隐藏
		var twoDiv =document.getElementById("two").style.display;
		var threeDiv =document.getElementById("three").style.display;
		var fourDiv =document.getElementById("fourSingle").style.display;
		var fiveDiv =document.getElementById("fourMore").style.display;
		if(oneDiv == ""){
			$j("#dialog_addHbqd").dialog("close");
		}else if(twoDiv == ""){
			$j('#back').text("取消");
			document.getElementById('one').style.display = "";
			document.getElementById('two').style.display = "none";
		}else if(threeDiv == ""){
			document.getElementById('two').style.display = "";
			document.getElementById('three').style.display = "none";
		}else if(fourDiv == ""){
			document.getElementById('three').style.display = "";
			document.getElementById('fourSingle').style.display = "none";
			var ids = $j("#fourSingleTable").jqGrid('getDataIDs');
			$j('#next').text("下一步");
			$j("#next").css("width","90px"); 
	         for(var i = 1 ; i <= ids.length; i++){
	        	 var id= $j("#fourSingleTable").getCell(i,"id");
	        	 hashMap2.remove(id);
	        	 $j("#fourSingleTable").find("tr[id="+i+"]").remove(); 
	        	 $j("#fourSingleTable").trigger("reloadGrid"); 
	         }  
		}else if(fiveDiv == ""){
			document.getElementById('three').style.display = "";
			document.getElementById('fourMore').style.display = "none";
			$j('#next').text("下一步");
			$j("#next").css("width","90px"); 
			hashMap2.removeAll();
			var ids = $j("#fourMoreTable").jqGrid('getDataIDs'); 
			for(var i = 0 ; i < ids.length; i++){
				 $j("#fourMoreTable").find("tr[id="+ids[i]+"]").remove(); 
			}
		}
	});
	
	var hashMap2 = new HashMap();
	$j("#next").click(function(){
		var oneDiv =document.getElementById("one").style.display;// 获取div隐藏熟悉 none:隐藏
		var twoDiv =document.getElementById("two").style.display;
		var threeDiv =document.getElementById("three").style.display;
		var fourDiv =document.getElementById("fourSingle").style.display;
		var fiveDiv =document.getElementById("fourMore").style.display;
		var obj = document.getElementById("selTransOpc");
		var whName = obj.options[obj.selectedIndex].text;//仓库名称
		var xzqd =  document.getElementsByName("xzqd");//选择渠道radio
		var whId = $j("#selTransOpc").val();//仓库Id
		$j('#back').text("返回");
		if(obj.selectedIndex == 0){
			jumbo.showMsg("仓库不能为空");
		}else{
			//加载渠道列表
			if(oneDiv == ""){
				$j("#whNameLabel").html(whName);
				document.getElementById('one').style.display = "none";
				document.getElementById('two').style.display = "";
				$j("#threeRight").jqGrid({
					url:baseUrl + "/findBiChannelListByType.json?whouid="+whId,//根据仓库查找渠道
					datatype: "json",
					colNames: ["ID","渠道编码","渠道名称"],
					colModel: [
										{name: "id", hidden: true},
										{name:"code",index:"code", width:80,resizable:true,hidden: true},
										{name:"name",index:"name",width:230,resizable:true}
							 		],
					caption: "渠道信息列表",
				   	sortname: 'id',
				   	multiselect: true,
					sortorder: "desc",
					pager:"#pager2",
					//height:150,
					width:280,
				   	shrinkToFit:false,
					//height:"auto",
					rowNum: jumbo.getPageSize(),
					rowList:jumbo.getPageSizeList(),
					viewrecords: false,
					jsonReader: {repeatitems : false, id: "0"}
				});
			}else if(twoDiv == ""){
				if(xzqd[0].checked){
					$j("#qdLabel").html("单渠道--选择子渠道");
					$j("#fourSingleLebel").html(whName+" --单渠道");
				}else{
					$j("#qdLabel").html("多渠道--选择子渠道");
					$j("#fourMoreLebel").html(whName+" --多渠道");
				}
				document.getElementById('two').style.display = "none";
				document.getElementById('three').style.display = "";
				$j("#channelName").attr("value","");
				var postData = loxia._ajaxFormToObj("channelForm");
				$j("#threeRight").jqGrid('setGridParam',{
					url:window.$j("body").attr("contextpath")+"/findBiChannelListByType.json?whouid="+whId,//根据仓库查找渠道
					postData:postData,
					page:1
				}).trigger("reloadGrid");
				setTimeout(function(){
					var ids = $j("#threeRight").jqGrid('getDataIDs');  
					if(ids == null || ids.length < 1){   
				        jumbo.showMsg("该仓库下没有绑定渠道，请返回重新选择仓库！");
				    }
				},500);
			}else if(threeDiv == ""){
				var ids = $j("#threeLeft").jqGrid('getDataIDs');  
			    if(ids==null || ids.length==0){   
			        jumbo.showMsg("渠道不能为空");   
			        return;   
			    }else if(ids.length == 1 && !xzqd[0].checked){
			    	jumbo.showMsg("多渠道选择子渠道不能少于两条");   
			        return;   
			    }else{
					if(xzqd[0].checked){
				    	document.getElementById('three').style.display = "none";
						document.getElementById('fourSingle').style.display = "";
			    	}else{
			    		document.getElementById('three').style.display = "none";
						document.getElementById('fourMore').style.display = "";
			    	}
					$j("#next").css("width","75px"); 
					$j('#next').text("完成");
					$j("#threeLeft tbody tr").each(function (i,tr){
						if(i > 0){
							--i;
							var obj = {
									id: $j(tr).find(":gt(1)").html(),
									name:$j(tr).find(":gt(3)").html()
							};
							if(hashMap2.get(obj.id) == null){
								hashMap2.put(obj.id,obj.name);
								if(xzqd[0].checked){
									copySingleRow(obj);
								}else{
									copyMoreRow(obj);
								}
								
							}
						}
					});
			    }
			}else if(fourDiv == ""){
				var expTime = $j("#expTime").val();
				var chId = "";
				if(expTime.replace(/[ ]/g,"") == "" ){
					jumbo.showMsg("过期时间不能为空！");
					return;
				}
				$j("#fourSingleTable tbody tr").each(function (i,tr){
					if(i > 0){
						--i;
						chId +=$j(tr).find(":gt(0)").html()+"/";
					}
				});
				var pastDate = {};
				pastDate["list.whId"] = whId;
				pastDate["list.expDate"] = expTime;
				pastDate["list.childChannelIdList"] = chId;
				var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/saveSingelChannelsMaintain.json",pastDate);
				if(rs && rs.msg == 'success'){
					//执行成功
					jumbo.showMsg("操作成功！");
					$j("#dialog_addHbqd").dialog("close");
					window.location.reload();
				}else{
					if(rs.errMsg != null){
						var msg = rs.errMsg;
						var s = '';
						for(var x in msg){
							s +=msg[x] + '<br/>';
						}
							jumbo.showMsg(s);
						}else{
							jumbo.showMsg(rs.msg);
						}
				}
			}else if(fiveDiv == ""){
				var expTime = $j("#expTime2").val();
				var name = $j("#name").val();
				var code = $j("#code").val();
				var zxShopName = $j("#zxShopName").val();
				var ydShopName = $j("#ydShopName").val();
				var selectedId = $j("#fourMoreTable").jqGrid("getGridParam", "selrow");
				var rowdata = $j("#fourMoreTable").jqGrid("getRowData", selectedId);
				if(selectedId == null || selectedId == ""){
					jumbo.showMsg("默认数据不能为空！");
					return;
				}
				if(name == null || name == ""){
					jumbo.showMsg("店铺名称不能为空！");
					return;
				}
				if(code == null || code == ""){
					jumbo.showMsg("店铺编码不能为空！");
					return;
				}
				if(expTime.replace(/[ ]/g,"") == "" ){
					jumbo.showMsg("过期时间不能为空！");
					return;
				}
				var chId = "";
				$j("#fourMoreTable tbody tr").each(function (i,tr){
					if(i > 0){
						--i;
						chId +=$j(tr).find(":gt(0)").html()+"/";
					}
				});
				var pastDate = {};
				pastDate["list.hbChId"] = rowdata.id; //默认数据ID
				pastDate["list.whId"] = whId;//仓库ID
				pastDate["list.expDate"] = expTime;//过期时间
				pastDate["list.channCode"] = code;//渠道唯一对接码
				pastDate["list.channName"] = name;//渠道名称
				pastDate["list.childChannelIdList"] = chId;//子渠道ID集合
				pastDate["list.zxShopName"] = zxShopName; //装箱店铺名称
				pastDate["list.ydShopName"] = ydShopName;//运单店铺名称
				var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/saveMoreChannelsMaintain.json",pastDate);
				if(rs && rs.msg == 'success'){
					//执行成功
					document.getElementById("infoForm").reset();
					jumbo.showMsg("操作成功！");
					$j("#dialog_addHbqd").dialog("close");
					window.location.reload();
				}else{
					if(rs.errMsg != null){
						var msg = rs.errMsg;
						var s = '';
						for(var x in msg){
							s +=msg[x] + '<br/>';
						}
							jumbo.showMsg(s);
						}else{
							jumbo.showMsg(rs.msg);
						}
				}
			}
		}
	});
	
	var hashMap = new HashMap();
	var updateHashMap = new HashMap();
	$j("#add").click(function(){
		var ids = $j("#threeRight").jqGrid('getGridParam',"selarrrow");
		if(ids == null || ids.length==0){
			jumbo.showMsg("请选择要添加的数据");
			return;
		}else{
			for(var i = 0;i<ids.length;i++){
				var obj = $j("#threeRight").jqGrid('getRowData',ids[i]);
				if(hashMap.get(obj.id) == null){
					hashMap.put(obj.id,obj.name);
					addRow(obj);
				}
			}
		}
	});
		
	$j("#delete").click(function(){
		var ids=$j("#threeLeft").jqGrid("getGridParam", "selarrrow");
	    if(ids==null || ids.length==0){   
	        jumbo.showMsg("请选择要删除的数据");   
	        return;   
	    }else{
	         for(var i = 0 ; i < ids.length; i++){
	        	 var id= $j("#threeLeft").getCell(ids[i],"id");
	        	 hashMap.remove(id);
	        	 $j("#threeLeft").find("tr[id="+ids[i]+"]").remove(); 
	        	 $j("#threeLeft").trigger("reloadGrid"); 
	         }  
	    }
	});
	

	$j("#dialog_addHbqd").prev().find("a").click(function(){
//		document.getElementById('one').style.display = "";
//		document.getElementById('two').style.display = "none";
//		document.getElementById('three').style.display = "none";
//		document.getElementById('fourSingle').style.display = "none";
//		document.getElementById('fourMore').style.display = "none";
//		$j("#selTransOpc").attr("value","");
//		$j("#channelName").attr("value","");
//		$j("#expTime").attr("value","");
//		$j("#threeLeft").clearGridData(false);
//		$j("#fourSingleTable").clearGridData(false);
//		$j("#fourMoreTable").clearGridData(false);
//		document.getElementById("infoForm").reset(); 
//		hashMap.removeAll();
//		hashMap2.removeAll();
		window.location.reload();
	});
	
	var addIsClick = false;
	var deleteIsClick = false;
	
	$j("#updateAdd").click(function(){
		var ids = $j("#updatethreeRight").jqGrid('getGridParam',"selarrrow");
		if(ids == null || ids.length==0){
			jumbo.showMsg("请选择要添加的数据");
			return;
		}else{
			for(var i = 0;i<ids.length;i++){
				var obj = $j("#updatethreeRight").jqGrid('getRowData',ids[i]);
				if(updateHashMap.get(obj.id) == null){
					updateHashMap.put(obj.id,obj.name);
					var obj2 = {
							name:obj.name,
							childChannelIdList:obj.id
					};
					updateAddRow(obj2);
				}
			}
			addIsClick = true;
		}
	});
		
	$j("#updateDelete").click(function(){
		var ids=$j("#updatethreeLeft").jqGrid("getGridParam", "selarrrow");   
	    if(ids==null || ids.length==0){   
	        jumbo.showMsg("请选择要删除的数据");   
	        return;   
	    }else{   
	         for(var i = 0 ; i < ids.length; i++){
	        	 var obj = $j("#updatethreeLeft").jqGrid('getRowData',ids[i]);
	        	 updateHashMap.remove(obj.childChannelIdList);
	        	 $j("#updatethreeLeft").find("tr[id="+ids[i]+"]").remove(); 
	        	 $j("#updatethreeLeft").resetSelection();
	        	 //$j("#updatethreeLeft").trigger("reloadGrid"); 
	         }   
	         deleteIsClick = true;
	    }
	});

	$j("#updateHbqd").click(function(){
		var selectedId = $j("#tbl-channel").jqGrid("getGridParam","selrow");
		var channCode= $j("#tbl-channel").getCell(selectedId,"channCode");
		var channName= $j("#tbl-channel").getCell(selectedId,"channName");
		var warehName= $j("#tbl-channel").getCell(selectedId,"warehName");
		var expTime= $j("#tbl-channel").getCell(selectedId,"expTime");
		var type= $j("#tbl-channel").getCell(selectedId,"chType");
		var hbChId= $j("#tbl-channel").getCell(selectedId,"hbChId");
		var whId= $j("#tbl-channel").getCell(selectedId,"whId");
		var xzhbfh =  document.getElementsByName("xzhbfh");//radio选择事件
		var warehId = null;
		if(selectedId == null){
			jumbo.showMsg("请选择要编辑的主数据");
		}else{
			var postData = {};
			postData["list.hbChId"] = hbChId;
			postData["list.whId"] = whId;
			$j("#updatethreeLeft").jqGrid({
				url: baseUrl + "/findChildChannelRef.json",
				postData : postData,
				datatype: "json",
				colNames: ["ID","渠道编码","渠道名称","子渠道ID"],
				colModel: [
								{name: "id", hidden: true},
								{name:"code",index:"code", width:80,resizable:true,hidden: true},
								{name:"name",index:"name",width:165,resizable:true},
								{name:"childChannelIdList",index:"childChannelIdList",width:30,resizable:true,hidden:true}
						 	],
		     	caption: "已选择渠道",
		    	sortname: 'id',
		    	multiselect: true,
		    	height:175,
		    	width:200,
			   	sortorder: "desc",
				shrinkToFit:false,
				viewrecords: false,
				jsonReader: {repeatitems : false, id: "0" }
			});
			
			$j("#updatethreeRight").jqGrid({
				url:baseUrl + "/findBiChannelListByType.json?whouid="+whId,//根据仓库查找渠道
				datatype: "json",
				colNames: ["ID","渠道编码","渠道名称"],
				colModel: [
									{name: "id", hidden: true},
									{name:"code",index:"code", width:80,resizable:true,hidden: true},
									{name:"name",index:"name",width:230,resizable:true}
						 		],
				caption: "渠道信息列表",
			   	sortname: 'id',
			   	multiselect: true,
				sortorder: "desc",
				pager:"#pager3",
				//height:150,
				width:280,
			   	shrinkToFit:false,
				//height:"auto",
				rowNum: jumbo.getPageSize(),
				rowList:jumbo.getPageSizeList(),
				viewrecords: false,
				jsonReader: {repeatitems : false, id: "0"}
			});
	
			$j("#updateSearch").click(function(){
				var postData = loxia._ajaxFormToObj("updateChannelForm");
				var whIds = null;
				if(warehId == null){
					whIds = whId;
				}else{
					whIds = warehId;
				}
				$j("#updatethreeRight").jqGrid('setGridParam',{
					url:window.$j("body").attr("contextpath")+"/findBiChannelListByType.json?whouid="+whIds,//根据仓库查找渠道
					postData:postData,
					page:1
				}).trigger("reloadGrid");
			});
			
			setTimeout(function(){
				var id =$j("#updatethreeLeft").jqGrid('getDataIDs');
				for(var i = 0;i<id.length;i++){
					var obj = $j("#updatethreeLeft").jqGrid('getRowData',id[i]);
					updateHashMap.put(obj.childChannelIdList,obj.name);
				}
			},200);
			
			$j("#dialog_updateHbqd").prev().find("a").addClass("hidden");
			$j("#dialog_updateHbqd").dialog("open");
			for(var idx in resultopc.warelist){
				$j("<option value='" + resultopc.warelist[idx].id + "'>"+ resultopc.warelist[idx].name +"</option>").appendTo($j("#updateSelTransOpc"));
			}
			$j("#updateCode").html(channCode);
			$j("#updateName").html(channName);
			$j("#updateWhNameLabel").html(warehName);
			$j("#updateexpTime").val(expTime);
			$j("#change :radio").change(function() {   
				if(!xzhbfh[0].checked){
					$j("#showMsg").html("注意：否会删除渠道合并信息！");
				}else{
					$j("#showMsg").html("");
				}
			});
			
			$j("#updateSelTransOpc").change(function(){ //下拉框选择事件
				var whName = $j("select[name=upWhOuId]").find("option:selected").text();
				var whNameLable = $j("#updateWhNameLabel").html();
				var postData = loxia._ajaxFormToObj("updateChannelForm");
				if(whNameLable != whName){
					$j("#updatethreeLeft").clearGridData(false);
					$j("#updateWhNameLabel").html(whName);
					$j("#updateChannelName").attr("value","");
				    warehId = $j("#updateSelTransOpc").val();
					$j("#updatethreeRight").jqGrid('setGridParam',{
						url:window.$j("body").attr("contextpath")+"/findBiChannelListByType.json?whouid="+warehId,//根据仓库查找渠道
						postData:postData,
						page:1
					}).trigger("reloadGrid");
					
					updateHashMap.removeAll();
				}
			}) ;
			if(type == 5){// 判断渠道是否虚拟
				$j("#updateTwo").removeClass("hidden");
				$j("#updateqdLabel").html("多渠道--编辑子渠道");
			}else{
				$j("#updateTwo").addClass("hidden");
			}
		}
	});
	
	$j("#cancel").click(function(){
		$j("#dialog_updateHbqd").dialog("close");
		$j("#updatethreeLeft").trigger("reloadGrid"); 
		$j("input[name=xzhbfh]:eq(0)").attr("checked",'checked'); 
		$j("#showMsg").html("");
		$j("#updatethreeLeft").trigger("reloadGrid"); 
		updateHashMap.removeAll();
	});
	
	$j("#save").click(function(){
		var selectedId = $j("#tbl-channel").jqGrid("getGridParam","selrow");
		var hbChId= $j("#tbl-channel").getCell(selectedId,"hbChId");//获取选中数据
		var type= $j("#tbl-channel").getCell(selectedId,"chType");
		var id= $j("#tbl-channel").getCell(selectedId,"id");
		var whId2= $j("#tbl-channel").getCell(selectedId,"whId"); //表格传过来的仓库ID
		var expTime= $j("#tbl-channel").getCell(selectedId,"expTime");
		var expTime2 = $j("#updateexpTime").val();
		var xzhbfh =  document.getElementsByName("xzhbfh");//radio选择事件
		var whId = $j("#updateSelTransOpc").val();//下拉框仓库ID
		var pastDate = {};
		if(expTime2.replace(/[ ]/g,"") == "" ){
			jumbo.showMsg("过期时间不能为空！");
			return;
		}
		pastDate["list.expDate"] = expTime2;//过期时间
		pastDate["list.hbChId"] = hbChId; //主渠道ID
		pastDate["list.id"] = id;//id
		if(whId == ""){//判断仓库是否改动
			pastDate["list.whId"] = whId2;
		}else{ 
			pastDate["list.whId"] = whId;
		}
		if(xzhbfh[0].checked){ //判断是否合并
			pastDate["list.isCombine"] = 1; 
		}else{ //不合并，则删除数据
			pastDate["list.isCombine"] = 0;
		}
		if(type == 5){ //判断是否虚拟渠道  5 Y
			if(whId == "" && expTime == expTime2 && xzhbfh[0].checked && !addIsClick &&  !deleteIsClick){
				jumbo.showMsg("没有需要保存的数据");
				return;
			}
			var chId = "";
			var ids =$j("#updatethreeLeft").jqGrid('getDataIDs');
		    if(ids==null || ids.length==0){   
		        jumbo.showMsg("渠道不能为空");   
		        return;   
		    }else if(ids.length == 1){
		    	jumbo.showMsg("渠道不能少于两条");   
		        return;   
		    }
			for(var i = 0; i < ids.length; i++){
				var testId =  $j("#updatethreeLeft #" + ids[i] +" td[aria-describedby='updatethreeLeft_childChannelIdList']").html();
				chId +=  testId + "/";
			}
			pastDate["list.childChannelIdList"] = chId;
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/updateMoreChannelsMaintain.json",pastDate);
			if(rs && rs.msg == 'success'){
				//执行成功
				//updateHashMap.removeAll();
				jumbo.showMsg("操作成功！");
				$j("#dialog_updateHbqd").dialog("close");
				$j("input[name=xzhbfh]:eq(0)").attr("checked",'checked'); 
				window.location.reload();
			}else{
				jumbo.showMsg("操作失败！");
			}
		}else{//非虚拟渠道
			if(whId == "" && expTime == expTime2 && xzhbfh[0].checked){
				jumbo.showMsg("没有需要保存的数据");
				return;
			}
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/updateSingelChannelsMaintain.json",pastDate);
			if(rs && rs.msg == 'success'){
				//执行成功
				jumbo.showMsg("操作成功！");
				$j("#dialog_updateHbqd").dialog("close");
				$j("input[name=xzhbfh]:eq(0)").attr("checked",'checked'); 
				window.location.reload();
			}else{
				jumbo.showMsg("操作失败！");
			}
		}
	});
});

function addRow(obj){//添加行
	var ids = $j("#threeLeft").jqGrid('getDataIDs');   
	var $firstTrRole = $j("#threeLeft").find("tr").eq(1).attr("role");  
	var rowid = $firstTrRole == "row" ? Math.max.apply(Math,ids):0;  //如果jqgrid中没有数据 定义最大行号为0 ，否则取当前最大行号   
	var newrowid = parseInt(rowid)+1;
	$j("#threeLeft").jqGrid("addRowData", newrowid, obj, "last");//将新添加的行插入到最后一列
}

function updateAddRow(obj){//添加行
	var ids = $j("#updatethreeLeft").jqGrid('getDataIDs');   
	var $firstTrRole = $j("#updatethreeLeft").find("tr").eq(1).attr("role");  
	var rowid = $firstTrRole == "row" ? Math.max.apply(Math,ids):0;  //如果jqgrid中没有数据 定义最大行号为0 ，否则取当前最大行号   
	var newrowid = parseInt(rowid)+1;
	$j("#updatethreeLeft").jqGrid("addRowData", newrowid, obj, "last");//将新添加的行插入到最后一列
}

function copySingleRow(obj){//复制单渠道数据
	var ids = $j("#fourSingleTable").jqGrid('getDataIDs');   
	var $firstTrRole = $j("#fourSingleTable").find("tr").eq(1).attr("role");  
	var rowid = $firstTrRole == "row" ? Math.max.apply(Math,ids):0;  
	var newrowid = parseInt(rowid)+1;
	$j("#fourSingleTable").jqGrid("addRowData", newrowid, obj, "last");//将新添加的行插入到最后一列
}

function copyMoreRow(obj){ //复制多渠道数据
	var ids = $j("#fourMoreTable").jqGrid('getDataIDs');   
	var $firstTrRole = $j("#fourMoreTable").find("tr").eq(1).attr("role");  
	var rowid = $firstTrRole == "row" ? Math.max.apply(Math,ids):0;  
	var newrowid = parseInt(rowid)+1;
	$j("#fourMoreTable").jqGrid("addRowData", newrowid, obj, "last");//将新添加的行插入到最后一列
}


//自定义HashMap
function HashMap(){ 
	this.map = {};
} 
HashMap.prototype = {
		put : function(key , value){
			this.map[key] = value;
		},
		get : function(key){
			if(this.map.hasOwnProperty(key)){
				return this.map[key];
			}
			return null;
		}, 
		remove : function(key){
			if(this.map.hasOwnProperty(key)){
				return delete this.map[key];
			}
			return false;
		}, 
		removeAll : function(){
			this.map = {};
		},
		keySet : function(){
			var _keys = [];
			for(var i in this.map){
				_keys.push(i);
			} 
			return _keys;
		}
	};
HashMap.prototype.constructor = HashMap;  