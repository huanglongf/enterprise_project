(function($){;var self = this;

this.wms = {
	
	RESULT: "result",
		
	SUCCESSES:"1",
	
	NONE: "0",
	
	ERRORS: "-1",
	
	MSG: "msg",
	
	LOGID: "logId",
	
	async: function(url, data, args, submitType){
        var options = _ajaxOptions(url, data, args);
        $.extend(options, {
            success: function( data, textStatus, jqXHR){
            	wms.asyncContextHandler(data);
                if(this.successHandler){
                    hitch(this,"successHandler")(data, textStatus, jqXHR);
                }else{
                    console.log("no handler defined for success");
                }
            },

            error: function(jqXHR, textStatus, errorThrown){
                if(this.errorHandler){
                    hitch(this,"errorHandler")(jqXHR, textStatus, errorThrown);
                }else{
                	console.log("no handler defined for error");
                }
            }
        });
        if(submitType){
            options.type = submitType;
        }
        $.ajax(options);
    },

    asyncGet: function(url, data, args){
    	var formData=$.extend({},data,{"orgTypeId":orgTypeId});
        this.async(url,formData,args,"GET");
    },

    asyncPost: function(url, data, args){
    	var formData=$.extend({},data,{"orgTypeId":orgTypeId});
        this.async(url,formData,args,"POST");
    },
    
    asyncContextHandler: function(data){
    	var _result = (undefined == data.isSuccess ? false : data.isSuccess);
    	var _logId = (undefined == data.logId ? "" : data.logId);
    	var _message = (undefined == data.msg ? "" : data.msg);
    	var _isTip =  wcontext.asyncTip;
    	this.contextHandler(_result, _logId, _message, _isTip, true);
    	wcontext.asyncHandler(_result);
    },
    
    syncContextHandler: function(){
    	var _isValidate = wcontext.syncTip;
    	this.contextHandler(result, logId, msg, _isValidate, false);
    	wcontext.syncHandler(result);
    },
    
    contextHandler:function(result, logId, msg, isTip, isAsync){
    	wcontext.coreHandler(result, logId, msg, isTip, isAsync);
    }
};

$(document).ready(function(){
	wms.syncContextHandler();
});
var wapp = wms.app = {
};;var wcontext = wms.app.context = {
		
		syncTip : true,
		
		asyncTip : false,
		
		result: result,
		
		msg: msg,
		
		logId: logId,
		
		init:function(result, logId, msg){
			if((wms.SUCCESSES == $.trim(result)) || ("true" == $.trim(result) || true == $.trim(result))){
				this.result = wms.SUCCESSES;
			}else if(wms.NONE == $.trim(result) || ("" == $.trim(result))){
				this.result = wms.NONE;
			}else{
				this.result = wms.ERRORS;
			}
			this.logId = logId;
			this.msg = msg;
			this.initWebContext(this.result, this.logId, this.msg);
		},
		
		initWebContext: function(result, logId, msg){
			$("body").attr(wms.RESULT,"");
			$("body").attr(wms.LOGID,"");
			$("body").attr(wms.MSG,"");
			$("body").attr(wms.RESULT, result);
			$("body").attr(wms.LOGID, logId);
			$("body").attr(wms.MSG, msg);
		},
		
		validateContextResult: function(){
			if(wms.ERRORS == $.trim($("body").attr(wms.RESULT))){
				wms.frame.notifyError(this.msg);
			}
		},
		
		showMsg:function(){
			if(wms.ERRORS == $.trim($("body").attr(wms.RESULT))){
				wms.frame.notifyError(this.msg);
			}else{
				wms.frame.notifySuccess(this.msg);
			}
		},
		
		syncHandler:function(result){
			
		},
		
		asyncHandler:function(result){
			
		},
		
		coreHandler:function(result, logId, msg, isTip, isAsync){
	    	this.init(result, logId, msg);
	    	if(isAsync){
	    		if(isTip){
	        		this.showMsg();
	        	}
	    	}else{
	    		if(isTip){
	        		this.validateContextResult();
	        	}
	    	}
	    }
};;var wfm = wms.frame = {
		
		clearNotify: function(){
			$("#msg").removeAttr("color").html("");
		},
		
	    notifyAlert: function(text){
	    	text = (undefined == text ? "" : text);
	    	if("" != text){
	    		alert(text);
	    	}
	    },
	    
	    notifyError: function(text){
	    	text = (undefined == text ? "" : text);
	    	$("#msg").attr("color", "red").html(text);
	    },

	    notifyWarning: function(text){
	    	text = (undefined == text ? "" : text);
	    	$("#msg").attr("color", "orange").html(text);
	    },

	    notifyInfo: function(text){
	    	text = (undefined == text ? "" : text);
	    	$("#msg").attr("color", "green").html(text);
	    },

	    notifySuccess: function(text){
	    	$("#msg").attr("color", "green").html(text);
	    }
};;})(jQuery);

var disableFormList=new Array();

//开启功能控钮
function enableForm(){
	
	for(var i=0;i<disableFormList.length;i++){
		$(disableFormList[i]).attr("disabled",false);
		
	}
	
	disableFormList=new Array();
}

//禁用功能控钮
function disableForm(){
	
	$(".body-all .input-element").each(function(){ 
		
		
		if($(this).attr("disabled")==undefined||$(this).attr("disabled")==false){
			$(this).attr("disabled",true);
			disableFormList.push(this);
		}
	}); 
	
	$(".body-all button").each(function(){ 
		
		
		if($(this).attr("disabled")==undefined||$(this).attr("disabled")==false){
			$(this).attr("disabled",true);
			disableFormList.push(this);
		}
	}); 
}

function closeDisableForm(){
	
	$(".body-all .input-element").each(function(){ 
			$(this).attr("disabled",false);
			disableFormList.push(this);
	}); 
	
	$(".body-all button").each(function(){ 
			$(this).attr("disabled",false);
			disableFormList.push(this);
	}); 
}


/**
 * 播放声音
 * @param num
 */
function playSound(num){

	var frame=document.getElementById("play-frame-"+num);
	//init();
	
	var objPlay='<embed id="play-sound-'+num+'"  allowFullScreen="true" width="0" height="0" loop="false" autostart="false" quality="high" src="'+staticbase+'/pda/images/'+num+'.wav?'+version+'"></embed>';
	frame.innerHTML=objPlay;
	var player = document.getElementById("play-sound-"+num);
	//palyer.src = wav + ".wav";
	//player.play();
	
}


function playMusic(){
	var box = document.getElementById('play-frame-2');
	var str = "<embed allowFullScreen='true' id='embedid' hidden='true' quality='high'  src='"+staticbase+"/pda/images/1.wav?"+version+"' ></embed>";
	box.innerHTML = str;
};

function play(){
	try {
		playMusic();
//		var palyer = document.getElementById("embedid").play();
		document.getElementById("embedid").play();
		//palyer.src = wav + ".wav";
//		palyer.play();
	} catch (e) {
		if (window["console"]){
			console.log("not support audio play!");	
		}
	}
};

/**
 * 显示浮层
 * title 标题
 * content 内容
 */
function showDialog(title,content,successfunc,failfunc,type){
	
	var left=$(".body-all")[0].offsetLeft;
	
	$(".am-modal-title").html(title);
	$(".am-modal-content").html(content);
	
	
	$("#am-modal-2xntz").css("left",left);
	//$(".mask-layer").css("left",left);
	$("#am-modal-2xntz").show();
	//$(".mask-layer").show();
	
	$("#am-modal-2xntz [data-am-modal-confirm]").one("click",successfunc);
	$("#am-modal-2xntz [data-am-modal-cancel]").one("click",failfunc);
	
	disableForm();
	
}


/**
 * 显示浮层
 * title 标题
 * content 内容
 * width 宽度
 * height 高度
 */
function hideDialog(){
	$("#am-modal-2xntz").hide();
	//$(".mask-layer").hide();
	enableForm();
}


function showTip(content){
	
	var left=$(".body-all")[0].offsetLeft;
	

	$(".am-modal-bd").html(content);
	
	
	$("#am-footer-modal").css("left",left);
	//$(".mask-layer").css("left",left);
	$("#am-footer-modal").show();
	//$(".mask-layer").show();
	disableForm();
}
//打开loading
function showLoading(){
	
	var left=$(".body-all")[0].offsetLeft;
	
	
	$("#am-footer-modal-loading").css("left",left);
	//$(".mask-layer").css("left",left);
	$("#am-footer-modal-loading").show();
	//$(".mask-layer").show();
//	disableForm();
}

/**
 * ajax session过期跳转页面
 * @param data
 */
function sessionError(data){
	if(data == 'sessionError'){
		window.location.href=pagebase+"/login";
	}
}

//关闭
function closeLoading(){
	$("#am-footer-modal-loading").hide();
//	closeDisableForm();
}


function hideTip(){
	$("#am-footer-modal").hide();
	//$(".mask-layer").hide();
	enableForm();
}

//获得当前页面的Locale,或者设置Locale
function pageLocale(locale){
    if(locale === undefined)
        return $("body").attr("locale")||"zh-CN";
    else
        $("body").attr("locale",locale);
}

//首字母大写
function capitaliseFirstLetter(str) {
    return str.charAt(0).toUpperCase() + str.slice(1);
}

//判断对象是否是字符串
function isString(obj){
    return typeof obj === "string" || obj instanceof String;
}

//根据名称获得一个函数。函数名中可以用‘.’去调用context中子对象的方法，context默认是页面对象
function getFunction(funcname, context){
    context = context || _self;
    var namespaces = funcname.split(".");
    for(var i=0; i< namespaces.length; i++){
        context = context(namespaces[i]);
        if(context == undefined)
            throw "Function is undefined:" + funcname;
    }
    return context;
}

function integerV(value){
    var v = parseInt(value,10);
    return isNaN(v)? null : v;
}

function floatV(value){
    var v = parseFloat(value);
    return isNaN(v)? null : v;
}

//调用方法
function hitch(scope, method){
    if(!method){
        method = scope;
        scope = null;
    }
    if(isString(method)){
        scope = scope || _self;
        if(!scope[method]){ throw(['hitch: scope["', method, '"] is null (scope="', scope, '")'].join('')); }
        return function(){ return scope[method].apply(scope, arguments || []); }; // Function
    }
    return !scope ? method : function(){ return method.apply(scope, arguments || []); };
}

//获取对象值
function getObject(propName, context){
    context = context || _self;
    var parts = propName.split(".");
    for(var i=0, pn; context &&(pn = parts[i]); i++){
        context = (pn in context ? context[pn] : undefined);
    }
    return context;
}

//设置对象值
function setObject(propName, value, context){
    context = context || _self;
    var parts = propName.split(".");
    var p = parts.pop();
    for(var i=0, pn; context &&(pn = parts[i]); i++){
        context = (pn in context ? context[pn] : context[pn]={});
    }
    return (context && p ? (context[p]=value) : undefined);
}

//返回延时调用函数
function debounce(func, wait, immediate) {
    var timeout, result;
    return function() {
        var args = arguments;
        var later = function() {
            timeout = null;
            if (!immediate) result = func(args);
        };
        var callNow = immediate && !timeout;
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
        if (callNow) result = func(args);
        return result;
    };
}

//为URL加上时间戳，时间戳的名称可以自己定义，默认是timeflag
function tURL(url, flag){
    flag=flag||"timeflag";
    var iTime=(new Date()).getTime(), pattern=new RegExp(flag+"=\\d{13}");
    if (url.indexOf(flag + "=") >= 0 ){
        url = url.replace(pattern, flag+"="+iTime.toString());
        return url ;
    }
    url+=(/\?/.test(url)) ? "&" : "?";
    return (url+flag+"="+iTime.toString());
}

//encode URL
function encURL(url, flag){
    var index = url.indexOf("?");
    if (index === -1)
        return flag? tURL(url,flag): url;

    var result = url.substring(0, index + 1),
        params = url.substring(index + 1).split("&");

    for (var i=0; i < params.length; i++){
        if (i > 0) result += "&";
        var param = params[i].split("=");
        result += param[0] + "=" + encodeURIComponent(param[1]);
    }

    return flag? tURL(result,flag): result;
}

//数据Ajax提交相关方法
/*used in building ajax data object from one form*/
function ajaxFormToObj(form){
    if(!form) return {};
    form = isString(form) ? $("#" + form).get(0) : form;
    var ret = {},
        exclude = "file|submit|image|reset|button|";

    function _ajaxSetValue(obj, name, value){
        if(value === null) return;
        var val = obj[name];
        if(isString(val)){
            obj[name] = [val, value];
        }else if($.isArray(val)){
            obj[name].push(value);
        }else{
            obj[name] = value;
        }
    }

    function _ajaxFieldValue(domNode){
        var ret = null,
            type = (domNode.type||"").toLowerCase();
        if(domNode.name && type && !domNode.disabled){
            if(type === "radio" || type === "checkbox"){
                if(domNode.checked){ ret = domNode.value }
            }else if(domNode.multiple){
                ret = [];
                $("option",domNode).each(function(){
                    if(this.selected)
                        ret.push(this.value);
                });
            }else{
                ret = domNode.value;
            }
        }
        return ret;
    }

    $.each(form.elements,function(i,e){
        var name = e.name,
            type = (e.type||"").toLowerCase();
        if(name && type && exclude.indexOf(type) === -1 && !e.disabled){
            _ajaxSetValue(ret, name, _ajaxFieldValue(e));
        }
    });
    return ret;
}

/*compose ajax call options*/
function _ajaxOptions(url, data, args){
    var options = {};
    if(arguments.length === 1)
        options = url;
    else{
        options = args || {};
        options["url"] = url;
        if(data){
            if(isString(data)){
                //data is a form id
                $.extend(options, {data: ajaxFormToObj(data)});
            }else
                $.extend(options,{data: data});
        }
    }
    //console.dir(options);
    return options;
}

/*ajax call
 * url ajax call url
 * data data object or form id
 * args other options*/
function asyncXhr(url, data, args){
    return $.ajax(_ajaxOptions(url, data, args));
}

/*ajax call with GET type*/
function asyncXhrGet(url, data, args){
    return $.ajax($.extend({"type":"GET"}, _ajaxOptions(url, data, args)));

}

/*ajax call with POST type*/
function asyncXhrPost(url, data, args){
    return $.ajax($.extend({"type":"POST"}, _ajaxOptions(url, data, args)));
}

/*ajax sync call*/
function syncXhr(url, data, args){
    var _data, options = _ajaxOptions(url, data, args);
    $.extend(options,{
        async : false,
        success : function(data, textStatus){
            _data = data;
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){
            _data = {};
            var exception = {};
            exception["message"] = "Error occurs when fetching data from url:" + this.url;
            exception["cause"] = textStatus? textStatus : errorThrown;
            _data["exception"] = exception;
        }
    });
    $.ajax(options);
    //console.dir(_data);
    return _data;
}

/*ajax sync call with GET type*/
function syncXhrGet(url, data, args){
    if(arguments.length === 1)
        url["type"] = "GET";
    else{
        args = $.extend({},args,{type:"GET"});
    }
    return syncXhr(url, data, args);
}

/*ajax sync call with POST type*/
function syncXhrPost(url, data, args){
    if(arguments.length === 1)
        url["type"] = "POST";
    else{
        args = $.extend({},args,{type:"POST"});
    }
    return this.syncXhr(url, data, args);
}

function ct(ptime){
    if(ptime == undefined)
        return new Date().getTime();
    return new Date().getTime() - ptime;
}
/**
 * 显示浮层
 * title 标题
 * content 内容
 */
function showDialog(title,content,successfunc,failfunc,type){
	
	var left=$(".body-all")[0].offsetLeft;
	
	$(".am-modal-title").html(title);
	$(".am-modal-content").html(content);
	
	
	$("#am-modal-2xntz").css("left",left);
	//$(".mask-layer").css("left",left);
	$("#am-modal-2xntz").show();
	//$(".mask-layer").show();
	disableForm();
	//playSound(1);
	
	
}

$(function(){
	
	
	$("#am-modal-2xntz .am-btn-cacnel").click(function(e){
		hideDialog();
	})
	$("#am-modal-2xntz .am-btn-ok").click(function(e){
		hideDialog();
	})
	
	$(".tip-close").click(function(e){
		hideTip();
	})
	
});

(function($){
$.fn.extend({
	serializeObject : function(){
	    var a, o, h, i, e;
	    a = this.serializeArray();
	    o = {};
	    h = o.hasOwnProperty;
	    for (i = 0; i < a.length; i++) {
	        e = a[i];
	        if (!h.call(o, e.name)) {
	            o[e.name] = e.value;
	        }
	    }
	    return o;
	}
});
})(jQuery);

