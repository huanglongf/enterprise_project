/*$(function(){
	$("#date").daterangepicker(null, function(start, end, label) {
       	console.log(start.toISOString(), end.toISOString(), label);
       	
	});});*/
  function updateRule(){
	  //if($('#id_update').val()){alert("");return;}
	  if($('#warningtype_code_update').val()==''||$('#warningtype_code_update').val()==-1){alert("预警类型不能为空");return;}
	  if($('#initial_level_update').val()==''||$('#initial_level_update').val()==-1){alert("预警级别不能为空");return;}
	  if($('#ew_flag_update').val()==''||$('#ew_flag_update').val()==-1){alert("启用不能为空");return;}
	  if($('#wk_type_update').val()==''||$('#wk_type_update').val()==-1){alert("工单类型不能为空");return;}
	  if($('#wk_level_update').val()==''||$('#wk_level_update').val()==-1){alert("工单级别不能为空");return;}
		 
	  $.post(root+'/controller/workOder/update.do?id='
    			+$('#id_update').val()+'&ew_type_code='
    			+$('#warningtype_code_update').val()+'&ew_level='
    			+$('#initial_level_update').val()+'&ew_flag='
    			+$('#ew_flag_update').val()+'&wk_type='+
    			$('#wk_type_update').val()+'&wk_level='+
    			$('#wk_level_update').val(),
		function(data){
		  if(typeof(data)=='string'){
				if(data.indexOf('window.top.location.href')!=-1&&data.indexOf('BT-LMIS')!=-1){
					window.top.location.href='/BT-LMIS';
					return;
				}}
		
		if(data.code==1){alert('更新成功！');
			$("#rule_update").modal("hide");
//			findData();
			tablesearch('','');
			
		}else{
			alert('更新操作失败！！');
		}
		}); 
		
	}

function addRecord(){
	$("#rule_new_form").modal();
	// 隐藏遮罩
	$(".modal-backdrop").hide();
}


//查询
function tablesearch(column, sort) {
	$(".search-table").load("/BT-LMIS/controller/workOder2/workOrderRulQueryPage.do?tableName=wk_generation_rule&pageName=table&startRow="
		+ $("#startRow").val()
		+ "&endRow="
		+ $("#startRow").val()
		+ "&page="
		+ $("#pageIndex").val()
		+ "&pageSize="
		+ $("#pageSize").val()
		+ "&sortColumn="
		+ column
		+ "&sort="
		+ sort
		+ "&"
		+ $(".search_form").serialize()
	);
}
//双击事件
function tableAction(currentRow, tableFunctionConfig) {
	if(tableFunctionConfig.dbclickTr == true) {
		var id = currentRow.children(":first").children(":first").val();
//		alert();
		$.post(root+'/controller/workOder/toUpdate.do?id='+id,function(data){
			   $("#rule_update").modal("show");
			// 隐藏遮罩
				$(".modal-backdrop").hide();
			   if(data.code=1){
				     $('#id_update').val(data.rule.id);
				     $("#warningtype_code_update option").each(function(index,item){
				    	if($(this).text()==data.rule.ew_type_name){
				    		$("#warningtype_code_update").next().val(data.rule.ew_type_name);
							$("#warningtype_code_update").next().attr(data.rule.ew_type_code);
							$("#warningtype_code_update" + " option:eq("+index+")").attr("selected", true);
				    	}
				     });
				     $("#wk_type_update option").each(function(index,item){
					    	if($(this).text()==data.rule.wk_type_name){
					    		   $("#wk_type_update").next().val(data.rule.wk_type_name);
								   $("#wk_type_update").next().attr(data.rule.wk_type_code);
								$("#wk_type_update" + " option:eq("+index+")").attr("selected", true);
					    	}
					     });
					   if(data.rule.ew_flag==1){
						   $("#ew_flag_update").next().val('是');
						   $("#ew_flag_update").next().attr(data.rule.wk_type_code);
						   $("#ew_flag_update option:eq(1)").attr("selected", true);
						   
					   }else{
						   $("#ew_flag_update").next().val('否');
						   $("#ew_flag_update").next().attr(data.rule.wk_type_code);
						   $("#ew_flag_update option:eq(2)").attr("selected", true);
						    
					   }
		             var content1='';
		             var content2='';
		              for(var i =0; i < data.WarninglevelList.length; i++){
							content1 += 
								'<option value="' 
								+ data.WarninglevelList[i].levelup_level 
								+ '">'
								+data.WarninglevelList[i].levelup_level 
								+'</option>'
							content2 += 
								'<li class="m-list-item" data-value="'
								+ data.WarninglevelList[i].levelup_level
								+ '">'
								+ data.WarninglevelList[i].levelup_level
								+ '</li>'
						}
					$("#initial_level_update" + " option:eq(0)").after(content1);
					$("#initial_level_update").siblings("ul").html('<li class="m-list-item" data-value="-1">---请选择---</li>');
					$("#initial_level_update").siblings("ul").children(":first").after(content2);
					//$("#initial_level_update").siblings("ul").children(":first").addClass("m-list-item-active");
					$("#initial_level_update option").each(function(index,item){
						if(data.rule.ew_level==$(this).text()){
							 $("#initial_level_update").next().val(data.rule.ew_level);
								$("#initial_level_update").next().attr(data.rule.ew_level);
								$("#initial_level_update" + " option:eq("+index+")").attr("selected", true);
						}
					});  
					var content3='';
		            var content4='';
					 for(var i =0; i < data.wk_level_list.length; i++){
							content3 += 
								'<option value="' 
								+ data.wk_level_list[i].level_code 
								+ '">'
								+data.wk_level_list[i].level_name 
								+'</option>'
							content4 += 
								'<li class="m-list-item" data-value="'
								+ data.wk_level_list[i].level_code
								+ '">'
								+ data.wk_level_list[i].level_name
								+ '</li>'
						}
					$("#wk_level_update" + " option:eq(0)").after(content3);
					$("#wk_level_update").siblings("ul").html('<li class="m-list-item" data-value="-1">---请选择---</li>');
					$("#wk_level_update").siblings("ul").children(":first").after(content4);
					//$("#wk_level_update").siblings("ul").children(":first").addClass("m-list-item-active");
					$("#wk_level_update option").each(function (index,item){
						if(data.rule.wk_level==$(this).text()){
		                    $("#wk_level_update").next().val(data.rule.wk_level);
							$("#wk_level_update").next().attr(data.rule.wk_level_code);
							$("#wk_level_update" + " option:eq("+index+")").attr("selected", true);
						}
					});
			   }else{
				   alert('操作失败');
			   }
		   })
	}
}
/*function findData(){
  var warningtype_code=$('#warningtype_code').val();
   if(warningtype_code==-1)warningtype_code='';
    var initial_level=$('#initial_level').val();
    if(initial_level==-1)initial_level='';
   var ew_flag=$('#ew_flag').val();
   if(ew_flag==-1)ew_flag='';
   var wk_type_query=$('#wk_type_query').val();
   if(wk_type_query==-1)wk_type_query='';
  var  wk_level_query=$('#wk_level_query').val();
  if(wk_level_query==-1)wk_level_query=''; 
openDiv(root+'/controller/workOder/workOrderRulQuery.do?ew_type_code=' 
		+ warningtype_code + 
		'&ew_level='+initial_level 
		+ '&ew_flag='+ew_flag
		+'&wk_type='+wk_type_query
		+'&wk_level='+wk_level_query
	
);
 $.ajax({
		type: "POST",
    	url:root+'/controller/workOder/workOrderRulQueryPage.do?ew_type_code='+ warningtype_code + 
		'&ew_level='+initial_level 
		+ '&ew_flag='+ew_flag
		+'&wk_type='+wk_type_query
		+'&wk_level='+wk_level_query,
    	dataType: "text",
    	data:'',
		cache:false,
		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
    	success: function (data){
       $("#page_view").html(data);
       $("#load_load").css("display","none");
    	}
	});

}*/

/*function upStatus(id, status) {
	var text= "";
	if(status == "false") {
		text= "禁用";
		
	} else {
		text= "启用";
		
	}
	if(!confirm("是否" + text + "所选工时？")) {
		return;
		
	}
	$.ajax({
		type: "POST",  
       	url: "/BT-LMIS/control/manhoursController/updateStatus.do?",
       	dataType: "json",
       	data:  {
       		"id": id,
       		"status": status
       		
       	},
       	success: function (result) {
       		alert(result.result_content)
       		if(result.result_code == "SUCCESS"){
       			find();
       			
       		}
     	}
	});
}*/

function pageJump() {
//	find();
	tablesearch('','');
}
function refresh() {
//	$("#wor_form select").each(function(){
//		$(this).val('');
//		
//	})
	initializeSelect("warningtype_code");
	initializeSelect("initial_level");
	initializeSelect("ew_flag");
	initializeSelect("wk_type_query");
	initializeSelect("wk_level_query");
//	find();
	tablesearch('','');
}	
function find() {
/*	var warningtype_code=$('#warningtype_code').val();
	   if(warningtype_code==-1)warningtype_code='';
	    var initial_level=$('#initial_level').val();
	    if(initial_level==-1)initial_level='';
	   var ew_flag=$('#ew_flag').val();
	   if(ew_flag==-1)ew_flag='';
	   var wk_type_query=$('#wk_type_query').val();
	   if(wk_type_query==-1)wk_type_query='';
	  var  wk_level_query=$('#wk_level_query').val();
	  if(wk_level_query==-1)wk_level_query=''; */
	
	  tablesearch("","");
	
}
/*$("#warningtype_code").change(function(){
	if(this.value==-1){
		$('#initial_level').html("<option value=-1>---请先选择预警类型---</option>");
		return ;
	}
	$.post('../controller/workOder/findWarningTypeLevel.do?warningtype_code='+this.value,function(data){
		if(data.toString()=='[object XMLDocument]'){
			  window.location='/BT-LMIS';
				return;
		  }
		var  dataStr=data.data;
		alert(dataStr);
		var htmlStr='<option value= -1>---请选择---</option><option value= 1>1</option>';
		$.each(dataStr,function(index,item){
			//console.log(item);
			item.levelup_level;
			htmlStr=htmlStr+'<option value='+item.levelup_level+'>'+item.levelup_level+'</option>';
		//	alert(item);
		});
		$('#initial_level').html(htmlStr);
		});*
	alert(123);
	warningLevelCodeChange("warningtype_code","initial_level",this.value);
	
});*/

function warningLevelCodeChange(upper, lower,value){
	upper = "#" + upper;
	lower = "#" +lower;
	var transport_code = $(upper).val();
	if(transport_code == ""){
		$(lower).children(":first").siblings().remove();
		$(lower).val("");
		$(lower).next().val("");
		$(lower).next().attr("placeholder", "---请选择---");
		$(lower).next().attr("disabled", "disabled");
	} else {
		$.ajax({
			url : root + "/controller/workOder/findWarningTypeLevel.do",
			type : "post",
			data : {
				"warningtype_code" : transport_code
			},
			dataType : "json",
			async : false, 
			success : function(result) {
				$(lower).next().attr("disabled", false);
				$(lower).children(":first").siblings().remove();
				$(lower).siblings("ul").children(":first").siblings().remove();
				var content1 = '';
				var content2 = '';
				for(var i =0; i < result.data.length; i++){
					content1 += 
						'<option value="' 
						+ result.data[i].levelup_level 
						+ '">'
						+result.data[i].levelup_level 
						+'</option>'
					content2 += 
						'<li class="m-list-item" data-value="'
						+ result.data[i].levelup_level
						+ '">'
						+ result.data[i].levelup_level
						+ '</li>'
				}
				$(lower).next().val("");
				$(lower).next().attr("placeholder", "---请选择---");
			
				$(lower + " option:eq(0)").after(content1);
				$(lower + " option:eq(0)").attr("selected", true);
				$(lower).siblings("ul").children(":first").after(content2);
				$(lower).siblings("ul").children(":first").addClass("m-list-item-active");
			},
			error : function(result) {
				alert(result);
			}
		});
	}
}


function workorderLevelCodeChange(upper, lower,value){
	upper = "#" + upper;
	lower = "#" +lower;
	var transport_code = $(upper).val();
	if(transport_code == ""){
		$(lower).children(":first").siblings().remove();
		$(lower).val("");
		$(lower).next().val("");
		$(lower).next().attr("placeholder", "---请选择---");
		$(lower).next().attr("disabled", "disabled");
	} else {
		$.ajax({
			url : root + "/controller/workOder/findWkTypeLevel.do",
			type : "post",
			data : {
				"wk_type" : transport_code
			},
			dataType : "json",
			async : false, 
			success : function(result) {
				$(lower).next().attr("disabled", false);
				$(lower).children(":first").siblings().remove();
				$(lower).siblings("ul").children(":first").siblings().remove();
				var content1 = '';
				var content2 = '';
				for(var i =0; i < result.data.length; i++){
					content1 += 
						'<option value="' 
						+ result.data[i].level_code 
						+ '">'
						+result.data[i].level_name 
						+'</option>'
					content2 += 
						'<li class="m-list-item" data-value="'
						+ result.data[i].level_code
						+ '">'
						+ result.data[i].level_name
						+ '</li>'
				}
			

				$(lower).next().val("");
				$(lower).next().attr("placeholder", "---请选择---");
			
				$(lower + " option:eq(0)").after(content1);
				$(lower + " option:eq(0)").attr("selected", true);
				$(lower).siblings("ul").children(":first").after(content2);
				$(lower).siblings("ul").children(":first").addClass("m-list-item-active");
			},
			error : function(result) {
				alert(result);
			}
		});
	}
}

function toAdd(){
	var wk_level_new =$('#wk_level_new').val();

	if(wk_level_new==-1||wk_level_new==''){
		alert("工单级别不能为空！！");
		return;
	}
	var wk_type_new=$('#wk_type_new').val();
	if(wk_type_new==-1||wk_type_new==''){
		alert("工单类型不能为空！！");
		return;
	}
	var ew_flag_new=$('#ew_flag_new').val();
	if(ew_flag_new==-1||ew_flag_new==''){
		alert('是否启用不能为空！！');
		return;
	}
	var  initial_level_new=$('#initial_level_new').val();
	if(initial_level_new==-1||initial_level_new==''){
		alert('预警级别不能为空');
		return;
	}
	var  warn_type_new=$('#warningtype_code_new').val();
	if(warn_type_new==-1||warn_type_new==''){
		alert('预警类型不能为空');
		return;
	}
	jQuery('#rule_new_form').modal('hide');
	loadingStyle();
	$.post(root+'/controller/workOder/add.do?ew_type_code=' + warn_type_new + '&ew_level='+initial_level_new + '&ew_flag='+ew_flag_new+'&wk_type='+wk_type_new+'&wk_level='+wk_level_new,function(data){
		if(typeof(data)=='string'){
		if(data.indexOf('window.top.location.href')!=-1&&data.indexOf('BT-LMIS')!=-1){
			window.top.location.href='/BT-LMIS';
			return;
		}}
			
		if(data.code==1){alert('添加成功！')
			$("#manhours_form").modal("hide");
//		    findData();
			tablesearch('','');
			cancelLoadingStyle();
		}else{
			alert('添加失败！！'+data.msg);
			cancelLoadingStyle();
			//jQuery('#herModal').modal('show');
		}
	});

}

function del(){
	if($("input[name='ckb']:checked").length>=1){
		if($("input[name='ckb']:checked").length>1){
			alert("只能选择一行!");
	  	}else{
	  		 var result=  	confirm('是否删除！'); 
				if(result){
					loadingStyle();
	  	 $.post(root+'/controller/workOder/del.do?id='+$("input[name='ckb']:checked").val(),
	        		function(data){
	  		if(typeof(data)=='string'){
	  			if(data.indexOf('window.top.location.href')!=-1&&data.indexOf('BT-LMIS')!=-1){
	  				window.top.location.href='/BT-LMIS';
	  				return;
	  			}} 
	        	            if(data.code==1){
	        	            	alert("操作成功！");
//	        	            	findData();
	        	            	tablesearch('','');
	        	            	cancelLoadingStyle();
	        	            }else if(data.code==0){
	        	            	alert("操作失败！"+data.msg);
	        	            	cancelLoadingStyle();
	        	             }
	        	            
	                        }
	  		      ); 
				}
	  	}
	}else{
		
		alert("请选择一行!");
	}
}
