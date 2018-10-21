/**
 * 将JS的任意对象输出为json格式字符串
 * @param {Object} _obj: 需要输出为string的对象
 */
var obj2String = function(_obj) {
	var t = typeof (_obj);
    if (t != 'object' || _obj === null) {
    	// simple data type
    	if (t == 'string') {
    		_obj = '"' + _obj + '"';
    	}
    	return String(_obj);
    } else {
    	if ( _obj instanceof Date) {
    		return _obj.toLocaleString();
    	}
    	// recurse array or object
    	var n, v, json = [], arr = (_obj && _obj.constructor == Array);
    	for (n in _obj) {
    		v = _obj[n];
    		t = typeof (v);
    		if (t == 'string') {
    			v = '"' + v + '"';
    		} else if (t == "object" && v !== null) {
    			v = this.obj2String(v);
    		}
    		json.push(( arr ? '' : '"' + n + '":') + String(v));
    	}
    	return ( arr ? '[' : '{') + String(json) + ( arr ? ']' : '}');
    }
};

/**
 * 设置固定表头
 */
function init_td(titleId,contendId){
 $("#"+titleId).scrollLeft($("#"+contendId).scrollLeft());
}

//获取鼠标点击区域在Html绝对位置坐标
function mouseCoords(event){
    if(event.pageX || event.pageY){
        return {x:event.pageX, y:event.pageY};
    }
    return{
        x:event.clientX + document.body.scrollLeft - document.body.clientLeft,
        y:event.clientY + document.body.scrollTop - document.body.clientTop
    };
}

// 针对My97控件readonly状态下点击不允许显示日历问题
function showCalendar(current) {
	if(!current.prop("readonly")) {
		WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});
		
	}
	
}

// 载入旋转效果
/**
 * 
 * 遮盖
 * @returns
 */
function cover(){
	if($("#background") != undefined || $("#background") != null){
		$("#background").remove();
		
	}
	// 背景元素
	var body= document.body;
	var div = document.createElement("div");
	div.id = "background";
	body.appendChild(div);
	// 添加样式
	$("#background").addClass("cover-bg");
	$("#background").css('z-index', 1040 + (10 * $('.modal:visible').length));
	// 指定坐标
	$("#background").css("height", window.screen.availHeight);
	$("#background").css("display", "block");
	// 页面禁止滚动
	$(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
	
}

function loadingStyle(){
	// 覆盖屏幕
	cover();
	// 加载旋转效果
	var div = document.createElement("div");
	div.id = "loadingCircle";
	$("#background").append(div);
	$("#loadingCircle").addClass("loading-circle");
	$("#loadingCircle").shCircleLoader({
		duration: 2,
		color: '#0ff',
		dots: 24,
		dotsRadius: 8,
		keyframes:
			'0%   {background: #0ff;    {prefix}transform: scale(1)}\
			20%   {background: #0bf; 	{prefix}transform: scale(.4)}\
			40%   {background: #0ff;    {prefix}transform: scale(0)}\
			50%   {background: #0ff;    {prefix}transform: scale(1)}\
			70%   {background: #0bf; 	{prefix}transform: scale(.4)}\
			90%   {background: #0ff;    {prefix}transform: scale(0)}\
			100%  {background: #0ff;    {prefix}transform: scale(1)}'
			
	});
	
}

function cancelLoadingStyle(){
	$("#loadingCircle").remove();
	$("#background").remove();
	$(document.body).css({"overflow-x": "auto", "overflow-y": "auto"});
	
}

/*
       自定义jquery函数，完成将form 数据转换为 json格式
*/
(function($){
	$.fn.serializeJson=function(){
		var serializeObj={}; // 目标对象
	    var array=this.serializeArray(); // 转换数组格式
	    // var str=this.serialize();
	    $(array).each(function(){ // 遍历数组的每个元素 {name : xx , value : xxx}
	    	if(serializeObj[this.name]){ // 判断对象中是否已经存在 name，如果存在name
	    		if($.isArray(serializeObj[this.name])){
	    			serializeObj[this.name].push(this.value); // 追加一个值 hobby : ['音乐','体育']
	    			
	    		}else{
	    			// 将元素变为 数组 ，hobby : ['音乐','体育']
	    			serializeObj[this.name]=[serializeObj[this.name],this.value];
	    			
	    		}
	    		
	    	}else{
	    		serializeObj[this.name]=this.value; // 如果元素name不存在，添加一个属性 name:value
	    		
	    	}
	    	
	    });
	    return serializeObj;
	
	};
	
})(jQuery);

function isNull(data) {
	if(data == "" || data == undefined || data == null) {
		return true;
		
	}
	return false;
	
}

/**
 * 初始化带模糊搜索select控件显示
 * @param id
 * @returns
 */
function initializeSelect(id) {
	$("#" + id).next().val("");
	$("#" + id).next().attr("placeholder", "---请选择---");
	$("#" + id).siblings("ul").children(":first").addClass("m-list-item-active");
	$("#" + id).val("");
	
}

/**
 * 初始化带模糊搜索select控件显示
 * @param selector
 * @returns
 */
function initSelectFilter(selector) {
	$(selector).next().val("");
	$(selector).next().attr("placeholder", "---请选择---");
	$(selector).siblings("ul").children(":first").addClass("m-list-item-active");
	$(selector).val("");
}

/**
 * 省市区县街道级联模糊查询
 * @param level
 * @param provinceId
 * @param cityId
 * @param stateId
 * @param streetId
 * @returns
 */
function shiftArea(level, provinceId, cityId, stateId, streetId){
	if(level < 3){
		if(level == 1){
			pCSSCascadeFuzzyQuery(provinceId, cityId);
			
		}
		pCSSCascadeFuzzyQuery(cityId, stateId);
		
	}
	pCSSCascadeFuzzyQuery(stateId, streetId);
	
}

/**
 * 上下级级联
 * @param upper
 * @param lower
 * @returns
 */
function pCSSCascadeFuzzyQuery(upper, lower){
	upper= "#" +upper;
	lower= "#" +lower;
	var area_code= $(upper).val();
	if(area_code == ""){
		$(lower).children(":first").siblings().remove();
		$(lower).next().attr("disabled", "disabled");
		
	} else {
		$.ajax({
			url: root+ "/control/lmis/areaController/getArea.do",
			type: "post",
			data: {
				"area_code" : area_code
				
			},
			dataType: "json",
			async: false, 
			success: function(result) {
				$(lower).next().attr("disabled", false);
				$(lower).children(":first").siblings().remove();
				$(lower).siblings("ul").children(":first").siblings().remove();
				var content1= '';
				var content2= '';
				for(var i= 0; i< result.area.length; i++){
					content1+= 
						'<option value="' 
						+ result.area[i].area_code 
						+ '">'
						+result.area[i].area_name 
						+'</option>'
					content2+= 
						'<li class="m-list-item" data-value="'
						+ result.area[i].area_code
						+ '">'
						+ result.area[i].area_name
						+ '</li>'
						
				}
				$(lower+ " option:eq(0)").after(content1);
				$(lower+ " option:eq(0)").attr("selected", true);
				$(lower).siblings("ul").children(":first").after(content2);
				$(lower).siblings("ul").children(":first").addClass("m-list-item-active");
				
			},
			error: function(result) {
	            if(result.status==200 || result.statusText == 'ok'){
	            	window.location= '/BT-LMIS';
	            	return;
	            	
	            }
				alert(result);
			
			}
			
		});
		
	}
	$(lower).next().val("");
	$(lower).next().attr("placeholder", "---请选择---");
	
}

/**
 * checkbox(全选&反选)
 * items 复选框的name
 */
function inverseCkb(items) {
	var checklist = document.getElementsByName (items);
	if(document.getElementById("checkAllTable").checked){
	   	for(var i=0;i<checklist.length;i++){
	      checklist[i].checked = 1;
	  } 
	}else{
	  for(var j=0;j<checklist.length;j++){
	     checklist[j].checked = 0;
	  }
	}
	/*$('[name=' + items + ']:checkbox').each(function() {
		this.checked = !this.checked;
		
	});*/
	
}

function openIdDiv(id, url){
	$("#" + id).load(url);
	
};

function checkedOrNot(id){
	if ($("[id=" + id + "]:checkbox").prop('checked')) {
		return 0;
		
	} else {
		return 1;
		
	}
	
}

/**
 * 是否被选中
 * @param id
 * @returns {Boolean}
 */
function isChecked(id) {
	if ($("[id=" + id + "]:checkbox").prop('checked')) {
		return true;
		
	} else {
		return false;
		
	}
	
}
	
/**
 * 获取区间价格
 * @param marks1
 * @param marks2
 * @param marks3
 * @param agrss1
 * @param agrss2
 * @returns
 */
function getSection(marks1,marks2,marks3,agrss1,agrss2){
	var mark1=$("#"+marks1).val();
	var mark2=$("#"+marks2).val();
    var mark3=$("#"+marks3).val();
    var agrs1=$("#"+agrss1).val();
    var agrs2=$("#"+agrss2).val();
    var result;
    if(isEmpty(agrss1) && isEmpty(agrss2)){
    	alert("区间值不能为空!");
	    return false;
    }
    if(!isEmpty(agrss1)){
    	if(isNNum(agrss1)){
    		alert("区间值必须为数字!");
    		return false;
		}
	}
    if(!isEmpty(agrss2)){
    	if(isNNum(agrss2)){
    		alert("区间值必须为数字!");
			return false;
		}
	}	
    if(mark2=="0"){
    	result=mark1+agrs1+","+mark3+agrs2 ;
    } else {
		if(agrs1=="" || agrs1==undefined){
			result=mark3+agrs2;
			return result;
		}
		if(agrs2=="" || agrs2==undefined){
			result=mark1+agrs1;
			return result;
		}
		if(mark1==">=" || mark1=="<="){
			result="["+agrs1;
		} else {
			result="("+agrs1;
		}
		if(mark3==">=" || mark3=="<="){
			result=result+","+agrs2+"]";
		} else {
			result=result+","+agrs2+")";
		}
	}
    if(parseFloat(agrs2)<parseFloat(agrs1)){
    	alert("区间价填写错误,请检查！");
    	return false;
    }		   
    return result;
}
	
/**
 * id的值是否为空
 * @param divId
 * @returns {Boolean}
 */
function isEmpty(divId){
	if($("#"+divId).val()=="" || $("#"+divId).val()==undefined || $("#"+divId).val()==null ){
		return true;
	}else{
		return false;
	}
}

function isEmptyInfo(divId,info){
	if($("#"+divId).val()=="" || $("#"+divId).val()==undefined || $("#"+divId).val()==null ){
		alert(info+"不能为空");
		return true;
	}else{
		return false;
	}
}

/**
 * 是否为大于或等于0的数字验证，不是返回true,否则返回false
 * @param divId
 * @returns {Boolean}
 */
function isNNum(divId){
	if(isNaN($("#"+divId).val())){
		return true;
	}else{
	  if( $("#"+divId).val()<0){
			return true;
		}
		return false;
	}
}

function not_num_or_empty(id,info){
	if(isEmpty(id)){
		alert(info+"不能为空");
	    return true;
    }
    if(isNNum(id)){
    	alert(info+"必须为大于或等于0的数字");
	    return true;
    }  
    return false;
}


function text_long_check(id,length,info){
	if($("#"+id).val().length>parseInt(length)){
		alert(info+"长度不能超过"+length+"个字符");
		return false;
	}
    return true;
}

function checkForm(id,info){
	 var patrn=/[`~!@#$%^&*()_+<>?:"{},.\;'[\]]/im;
	 if(patrn.test($("#"+id).val())){  
	        alert(info+",输入的数据含有非法字符！");  
	        return false;     
	    }     
	 return true;
}

/**
 * 去掉字符串中所有的空格信息
 * @param str
 * @param is_global
 * @returns
 */
function Trim(str,is_global)
{
    var result;
    result= str.replace(/(^\s+)|(\s+$)/g,"");
    if(is_global.toLowerCase()=="g")
    {
    	result = result.replace(/\s/g,"");
    	
    }
    return result;
    
}

