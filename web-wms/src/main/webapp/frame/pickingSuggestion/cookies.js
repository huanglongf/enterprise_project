//JS操作cookies方法! 
//写cookies 
function setCookieBZ(name,value) 
{ 
  var Days = 365; //设置默认过期时间
  var exp = new Date(); 
  exp.setTime(exp.getTime() + Days*24*60*60*1000); 
  window.top.document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); 
} 

//读取cookies 
function getCookieBZ(name) 
{ 
  var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
  if(arr=window.top.document.cookie.match(reg))
      return unescape(arr[2]); 
  else 
      return null; 
} 

//删除cookies 
function delCookieByName(name) 
{ 
  var exp = new Date(); 
  exp.setTime(exp.getTime() - 1); 
  var cval=getCookie(name); 
  if(cval!=null) 
      document.cookie= name + "="+cval+";expires="+exp.toGMTString(); 
} 
//设定自定义过期时间 
//s是指秒，20秒则：s20
//h是指小时，12小时则：h12 
//d是天数，30天则：d30 
function setCookieExpTime(name,value,time)
{ 
  var strsec = getsec(time); 
  var exp = new Date(); 
  exp.setTime(exp.getTime() + strsec*1); 
  document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); 
} 

function getsec(str)
{ 
 var str1=str.substring(1,str.length)*1; 
 var str2=str.substring(0,1); 
 if (str2=="s")
 { 
      return str1*1000; 
 }
 else if (str2=="h")
 { 
     return str1*60*60*1000; 
 }
 else if (str2=="d")
 { 
     return str1*24*60*60*1000; 
 } 
} 

//指定打印机名称打印
//url1:打印装箱清单,url2:物流面单
function printByPrinterName(url1,pickingListPrinterName){
	//获取可用打印机服务名称
	var printers = window.top.document.printApplet.getAllPrinterNames();
	//TODO 解析打印机名称list
	//alert(printers);
	var pickingListKey=getCookieBZ("pickingListKey");
	//var logisticsKey=getCookieBZ("logisticsKey");
	//TODO 如果有值 需要判断已存在值是否可用
	if((pickingListKey==null)&&(url1!=null)){
		//	TODO弹框设置装箱单打印
		setCookieBZ("pickingListKey",pickingListPrinterName); 
	}
	/*if((logisticsKey==null)&&(url2!=null)){
		
	   //	TODO弹框设置装箱单打印
		setCookieBZ("logisticsKey","aaaa"); 
	}*/
	//调用打印机
	if(url1!=null&&pickingListPrinterName!=null){
		printDf(url1,pickingListPrinterName);
	}
	/*if(url2!=null&&logisticsPrinterName!=null){
		printDf(url2,logisticsPrinterName);
	}*/
}

//清除设置
function clearCookieByName(name) 
{ 
	delCookieByName("pickingListKey");
	delCookieByName("logisticsKey");
	jumbo.showMsg("清除打印机设置成功！");
} 


