                               
                   
 function queryUri(url){
	 
	 var parser = document.createElement('a');
	 parser.href = url;
	 
	 return parser.pathname;
	 
 }

wms.addReadyFunc(function(){
        $(".tag-filter").tagfilter({
            filters: filtersData,				//过滤条件集合列表(通过过滤器获取)
            criterias: criteriasData,			//过滤条件选项(具体功能开发人员进行配置)
            callBack: {
            	/**
            	 * 添加过滤条件
            	 * 这里使用ajax的方式进行担交(必须为同步)
            	 * 如果 return {result: false, errorCode: 100, resultParams: ["李不能动"]} 表示添加失败 
            	 * 如果 return {result: true, resultCode: null, resultParams:[]}; 表示添加成功
            	 */
            	"addFilter": function(filter){
            		console.log("your customized adding operation...")
            		console.log(filter);
            		console.log("do your own operation...");
            		console.log("filter added.");
            		
            		
            		console.log(filter);
            		var filters=JSON.stringify(filter.conditions);
            		var url=queryUri(window.location.href);
            		var params={
            				name:filter.name,
            				url:url,
            				filters:filters
            		};
            		var result=syncXhrPost(pagebase+"/general/filter/save",params,{});
            		if(result.isSuccess){
            			return {result: true, resultCode: 101, resultParams:["XXX","XXXX"]};
            		}
            		else{
            			return {result: false, resultCode: 101, resultParams:[]};
            		}
            	},
                /*"removeFilter": function(filter){
                    if(filter == "李姓族人")
                        return {result: false, errorCode: 100, resultParams: ["李不能动"]}
                }*/
                "removeFilter": function(filter){
                    //做事情的异步回调中用下面这个
                    $(".tag-filter").trigger("filterRemoved", {filter:{name:filter}, timeExpended: 10}) ;
                	console.log(filter);
                	
            		var params={
            				name:filter
            		};
            		var result=syncXhrPost(pagebase+"/general/filter/remove",params,{});
            		
            		if(result.isSuccess){
            			console.log("success");
            			return {result: true, resultCode: 101, resultParams:["XXX","XXXX"]};
            		}
            		else{
            			return {result: false, resultCode: 101, resultParams:[]};
            		}
                }
            },
            //设置哪些回调是否为同步，默认为同步
            syncCallBack: {
                "removeFilter": true
            }
        });
        	
        /**
         * 当用户手动切换了过滤条件
         * 当用户保存，复制，删除条件时;
         * 都会触发这个方法
         * 
         * 这个方法的内容是使用当前的搜索条件进行提交
         */
        $(".tag-filter").on("tagChanged", function(e, obj){
        	
        	//切换过滤条件时，清空全局过滤条件
        	$("input[name='q_string_inputCommSearch']").val("");
        	
        	//如果为空，清空条件,提交表单
        	if(obj.toFilter == null){
        		$("#generalConditionsDiv").empty();
        		$("form[id='queryForm']").submit();
        		return ;
        	}
        	var generalName = obj.toFilter.name;
        	if(typeof(generalName) == "undefined"){
        		return ;
        	}
        	//页签查询条件
        	var generalConditions = "";
        	for(var i=0;i<filtersData.length;i++){
        		if(filtersData[i].name == generalName){
        			generalConditions = filtersData[i].conditions;
        		}
        	}
        	//将搜索条件加入到隐藏域中
        	var generalConditionsData = "<input type='hidden' name='filtersName' value='"+generalName+"'/>";
        	for(var p=0; p<generalConditions.length; p++){
        		var generalOp = generalConditions[p].op;
        		var generalField = generalConditions[p].field;
        		//包含
        		if("contains" == generalOp){
        			var pp = generalField.indexOf("q_string_");
        			if(pp > -1){
        				generalField = "q_sl"+generalField.substring(8);
        			}
        		}
        		//开始于
        		if("start" == generalOp){
        			var startp = generalField.indexOf("q_string_");
        			if(startp > -1){
        				generalField = "q_sll"+generalField.substring(8);
        			}
        		}
        		//结束于
        		if("end" == generalOp){
        			var endp = generalField.indexOf("q_string_");
        			if(endp > -1){
        				//TODO 
        				//generalField = "" + generalField.substring(8);
        			}
        		}
//        		if(generalConditions[p].criteriaClass.indexOf("date")>0){
//        			
//        		}else{
        			generalConditionsData += "<input type='hidden' name='"+generalField+"' value='"+generalConditions[p].values+"'/>";
//        		}
        	}
        	//先清空，再加入对应条件
        	$("#generalConditionsDiv").empty();
        	$("#generalConditionsDiv").html(generalConditionsData);
        	$("form[id='queryForm']").submit();
            console.log("TagChanged");
            console.log(obj);
        });

        $(".tag-filter").on("tagRemoved", function(e, obj){
            console.log("TagRemoved");
            console.log(obj);
        });

        $(".tag-filter").on("tagRemoveFailed", function(e, obj){
            for(var i= 0,s; s = obj.resultParams[i]; i++)
                console.log(s);
        });
        
        $(".tag-filter").on("filterAdded", function(e, obj){
            console.log("Filter Added within " + obj.timeExpended + "ms");
        });
        $(".tag-filter").on("filterAddedFailed", function(e, obj){
        	//use obj to display error message
            console.log(obj);
        });
        
        //搜索按钮
        $(".input-group-addon i.glyphicon-search").click(function(){
//        	$("input[name='inputCommSearch']").val("12332");
        	$("form[id='queryForm']").submit();
        });
        
        //搜索页签选中及搜索条件显示
        if(filtersName.length>0){
        	//将搜索条件加入到隐藏域中
        	var generalConditions1 = "<input type='hidden' name='filtersName' value='"+filtersName+"'/>";
        	for(var pp=0; pp<filtersData.length;pp++){
        		if(filtersName == filtersData[pp].name){
        			for(var tt=0; tt<filtersData[pp].conditions.length; tt++){
        				var generalOp = filtersData[pp].conditions[tt].op;
                		var generalField = filtersData[pp].conditions[tt].field;
                		if("contains" == generalOp){
                			var ppp = generalField.indexOf("q_string_");
                			if(ppp > -1){
                				generalField = "q_sl"+generalField.substring(8);
                			}
                		}
//                		if(filtersData[pp].conditions[tt].criteriaClass.indexOf("date")>0){
//                			
//                		}else{
                			generalConditions1 += "<input type='hidden' name='"+generalField+"' value='"+filtersData[pp].conditions[tt].values+"'/>";
//                		}
        			}
        		}
        	}
        	//先清空，再加入对应条件
        	$("#generalConditionsDiv").empty();
        	$("#generalConditionsDiv").html(generalConditions1);
        	//搜索页签样式
        	$(".tag-filter .nav-tabs li").each(function(){
        		//搜索页签
        		if(filtersName!="undefined"){
	        		if(filtersName == $(this).attr("data-filter")){
	        			$(this).addClass("active");
	        		}else{
	        			$(this).removeClass("active");
	        		}
        		}
        	});
        	//搜索条件样式
        	$(".filter-content", "div.tag-filter").each(function(){
        		if(filtersName == $(this).attr("data-filter")){
        			$(this).show();
        		}else{
        			$(this).hide();
        		}
        	});
        }
        
        //todo
        //如果做整体页面刷新，还需要在初始时，根据url参数，将filtersData中具体哪一项设置为激活状态
        //$(".tag-filter").trigger("tagChanged", {filter:{name:filter}, timeExpended: 1})
        
    });