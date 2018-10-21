$j(document).ready(function (){
	window.setInterval("checkWebPing()",2000);
	window.setInterval("checkServicePing()",2000);
	
//	var rs = loxia.syncXhr($j("body").attr("contextpath")+ "/json/checkWebPing.json");
//	if (rs && rs.result && rs.result == "success") {
//		alert(rs.memo);
//	}
});

function checkWebPing(){
	var sTime = new Date().getTime();
	$j.ajax({
        url : $j("body").attr("contextpath")+ "/json/checkWebPing.json",
        timeout : 15000, //超时时间设置，单位毫秒
        data:{},
        cache : false,
        async : true,
        type : "POST",
        dataType : 'json',
        success : function (result){
        	var eTime = new Date().getTime();
        	var t = $j("#webPing").html();
        	$j('#webPing').html(t+"<b style='color: red;'>【"+result.version+"】</b>"+result.memo+" 耗时: "+(eTime-sTime)/1000+"<br/>");
        },
        complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
    	if(status=='timeout'){//超时,status还有success,error等值的情况
        		var t = $j("#webPing").html();
            	$j('#webPing').html(t+"超时<br/>");
    		}
        }
    });
}

function checkServicePing(){
	var sTime = new Date().getTime();
	$j.ajax({
		url : $j("body").attr("contextpath")+ "/json/checkServicePing.json",
		timeout : 15000, //超时时间设置，单位毫秒
		data:{},
		cache : false,
		async : true,
		type : "POST",
		dataType : 'json',
		success : function (result){
			var eTime = new Date().getTime();
			var t = $j("#servicePing").html();
			$j('#servicePing').html(t+"<b style='color: red;'>【"+result.version+"】</b>"+result.memo+" 耗时: "+(eTime-sTime)/1000+"<br/>");
		},
		complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
			if(status=='timeout'){//超时,status还有success,error等值的情况
				var t = $j("#servicePing").html();
				$j('#servicePing').html(t+"超时<br/>");
			}
		}
	});
}